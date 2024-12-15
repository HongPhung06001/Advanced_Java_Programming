package com.admission.controller;

import com.admission.base.BaseDAO;
import com.admission.dto.CommonResponse;
import com.admission.dto.MajorDTO;
import com.admission.mapper.MajorMapper;
import com.admission.model.Block;
import com.admission.model.Major;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.type.StringType;
import org.mapstruct.factory.Mappers;

public class MajorController extends BaseDAO {

    private final Logger logger = LogManager.getLogger(MajorController.class);

    private final BlockController blockController;

    private final MajorMapper majorMapper;

    public MajorController() {
        this.majorMapper = Mappers.getMapper(MajorMapper.class);
        this.blockController = new BlockController();
    }

    public List<MajorDTO> getMajors() throws Exception {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            String sql = "SELECT * FROM majors ORDER BY name";
            Query query = session.createNativeQuery(sql, Major.class);
            List<Major> queryResults = query.getResultList();
            tx.commit();
            return majorMapper.toMajorDTOs(queryResults);
        } catch (Exception e) {
            rollback(tx);
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            close(session);
        }
        return null;
    }

    public List<MajorDTO> getMajorsForAdmin(String keyword) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            StringBuilder stringQuery = new StringBuilder();
            stringQuery.append("SELECT m.id, m.code, m.name, GROUP_CONCAT(b.code SEPARATOR ',') AS block_codes FROM majors m ");
            stringQuery.append("LEFT JOIN major_block mb ON m.id = mb.major_id  ");
            stringQuery.append("LEFT JOIN blocks b ON mb.block_id = b.id  ");
            stringQuery.append("WHERE (COALESCE(:keyword, '') = '' OR m.code LIKE CONCAT('%', :keyword, '%') OR m.name LIKE CONCAT('%', :keyword, '%')) ");
            stringQuery.append("GROUP BY m.id ");
            Query query = session.createNativeQuery(stringQuery.toString());
            query.setParameter("keyword", keyword, StringType.INSTANCE);
            List<Object[]> queryResults = query.getResultList();
            tx.commit();
            return majorMapper.objectsToMajorDtos(queryResults);
        } catch (Exception e) {
            rollback(tx);
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            close(session);
        }
        return null;
    }

    public Major getMajorByCode(String code) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            Query query = session.createNativeQuery("SELECT * FROM majors WHERE code = :code", Major.class
            );
            query.setParameter("code", code);
            List<Major> majors = query.getResultList();
            if (CollectionUtils.isEmpty(majors)) {
                return null;
            }
            Major major = majors.get(0);
            tx.commit();
            return major;
        } catch (Exception e) {
            rollback(tx);
            e.printStackTrace();
            logger.error(e.getMessage());
            return null;
        } finally {
            close(session);
        }
    }

    public CommonResponse createMajor(MajorDTO majorDTO) {
        List<String> codes = Arrays.stream(majorDTO.getBlocks().split(",")).collect(Collectors.toList());
        try {
            if (ObjectUtils.isNotEmpty(getMajorByCode(majorDTO.getCode()))) {
                return new CommonResponse(Boolean.FALSE, "Mã chuyên ngành này đã tồn tại!");
            }
            List<Block> blocks = blockController.getBlocksByCode(codes);
            Major major = majorMapper.toMajor(majorDTO);
            major.setBlocks(new HashSet<>(blocks));
            save(major);
            return new CommonResponse(Boolean.TRUE, "Tạo chuyên ngành thành công");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new CommonResponse(Boolean.FALSE, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
        }
    }

    public CommonResponse updateMajor(MajorDTO majorDTO) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        List<String> codes = Arrays.stream(majorDTO.getBlocks().split(",")).collect(Collectors.toList());
        try {
            Major findMajor = getMajorByCode(majorDTO.getCode());
            if (ObjectUtils.isNotEmpty(findMajor) && !findMajor.getId().equals(majorDTO.getId())) {
                return new CommonResponse(Boolean.FALSE, "Mã chuyên ngành này đã tồn tại!");
            }
            List<Block> blocks = blockController.getBlocksByCode(codes);
            Major major = session.load(Major.class,
                    majorDTO.getId());
            majorMapper.updateMajorFromDTO(majorDTO, major);
            major.setBlocks(new HashSet<>(blocks));
            session.saveOrUpdate(major);
            tx.commit();
            return new CommonResponse(Boolean.TRUE, "Cập nhật chuyên ngành thành công");
        } catch (Exception e) {
            rollback(tx);
            e.printStackTrace();
            logger.error(e.getMessage());
            return new CommonResponse(Boolean.FALSE, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
        } finally {
            close(session);
        }
    }

    public CommonResponse deleteMajorById(Integer majorId) throws Exception {
        Major major = (Major) findById(Major.class,
                majorId);
        if (ObjectUtils.isEmpty(major)) {
            return new CommonResponse(Boolean.FALSE, "Không tìm thấy chuyên ngành có id " + majorId);
        }
        delete(major);
        return new CommonResponse(Boolean.TRUE, "Xóa chuyên ngành thành công");
    }

}
