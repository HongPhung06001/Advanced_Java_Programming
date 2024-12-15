package com.admission.controller;

import com.admission.base.BaseDAO;
import com.admission.dto.BlockDTO;
import com.admission.dto.CommonResponse;
import com.admission.mapper.BlockMapper;
import com.admission.model.Block;
import com.admission.model.Subject;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.mapstruct.factory.Mappers;

public class BlockController extends BaseDAO {

    private final SubjectController subjectController;

    private final BlockMapper blockMapper;

    public BlockController() {
        this.subjectController = new SubjectController();
        this.blockMapper = Mappers.getMapper(BlockMapper.class);
    }

    public List<Block> getBlocks() throws Exception {
        return (List<Block>) findAll(Block.class);
    }

    public List<BlockDTO> getBlocks(String keyword) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT b.id, b.code, GROUP_CONCAT(s.name SEPARATOR ',') AS subject_names ");
            sql.append("FROM blocks b ");
            sql.append("LEFT JOIN block_subject bs on bs.block_id = b.id ");
            sql.append("LEFT JOIN subjects s on s.id = bs.subject_id  ");
            sql.append("WHERE (COALESCE(:keyword, '') = '' OR code LIKE CONCAT('%', :keyword, '%')) ");
            sql.append("GROUP BY b.id ");
            Query query = session.createNativeQuery(sql.toString());
            query.setParameter("keyword", keyword);
            List<Object[]> queryResults = query.getResultList();
            tx.commit();
            return blockMapper.objectToBlockDtos(queryResults);
        } catch (Exception e) {
            rollback(tx);
            e.printStackTrace();
            return null;
        } finally {
            close(session);
        }
    }

    public List<BlockDTO> getBlocksByMajorCode(String majorCode) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM blocks b ");
            sql.append("INNER JOIN major_block mb ON mb.block_id = b.id ");
            sql.append("INNER JOIN majors m ON m.id = mb.major_id ");
            sql.append("WHERE m.code = :majorCode");
            Query query = session.createNativeQuery(sql.toString(), Block.class);
            query.setParameter("majorCode", majorCode);
            tx.commit();
            return blockMapper.toBlockDTOs(query.getResultList());
        } catch (Exception e) {
            rollback(tx);
            e.printStackTrace();
            return null;
        } finally {
            close(session);
        }
    }

    public List<Block> getBlocksByCode(List<String> codes) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            Query query = session.createQuery("FROM Block WHERE code IN :codes", Block.class);
            query.setParameter("codes", codes);
            tx.commit();
            return query.getResultList();
        } catch (Exception e) {
            rollback(tx);
            e.printStackTrace();
            return null;
        } finally {
            close(session);
        }
    }

    public Block getBlockByCode(String code) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            Query query = session.createQuery("FROM Block WHERE code = :code", Block.class);
            query.setParameter("code", code);
            List<Block> blocks = query.getResultList();
            tx.commit();
            if (CollectionUtils.isEmpty(blocks)) {
                return null;
            }
            Block block = blocks.get(0);
            return block;
        } catch (Exception e) {
            rollback(tx);
            e.printStackTrace();
            return null;
        } finally {
            close(session);
        }
    }

    public CommonResponse createBlock(BlockDTO blockDTO) {
        List<String> names = Arrays.stream(blockDTO.getSubjects().split(",")).collect(Collectors.toList());
        try {
            if (ObjectUtils.isNotEmpty(getBlockByCode(blockDTO.getCode()))) {
                return new CommonResponse(Boolean.FALSE, "Mã khối này đã tồn tại!");
            }
            List<Subject> subjects = subjectController.getSubjectsByName(names);
            Block block = blockMapper.toBlock(blockDTO);
            block.setSubjects(new HashSet<>(subjects));
            save(block);
            return new CommonResponse(Boolean.TRUE, "Tạo chuyên ngành thành công");
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResponse(Boolean.FALSE, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
        }
    }

    public CommonResponse updateMajor(BlockDTO blockDTO) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        List<String> names = Arrays.stream(blockDTO.getSubjects().split(",")).collect(Collectors.toList());
        try {
            Block findBlock = getBlockByCode(blockDTO.getCode());
            if (ObjectUtils.isNotEmpty(findBlock) && !findBlock.getId().equals(blockDTO.getId())) {
                return new CommonResponse(Boolean.FALSE, "Mã khối này đã tồn tại!");
            }
            List<Subject> subjects = subjectController.getSubjectsByName(names);
            Block block = session.load(Block.class, blockDTO.getId());
            blockMapper.updateBlockFromDTO(blockDTO, block);
            block.setSubjects(new HashSet<>(subjects));
            session.saveOrUpdate(block);
            tx.commit();
            return new CommonResponse(Boolean.TRUE, "Cập nhật khối thành công");
        } catch (Exception e) {
            rollback(tx);
            e.printStackTrace();
            return new CommonResponse(Boolean.FALSE, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
        } finally {
            close(session);
        }
    }

    public CommonResponse deleteBlockById(Integer blockId) throws Exception {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            Block block = (Block) findById(Block.class, blockId);
            if (ObjectUtils.isEmpty(block)) {
                return new CommonResponse(Boolean.FALSE, "Không tìm thấy khối có id " + blockId);
            }
            String sql = "DELETE FROM major_block WHERE block_id = :blockId";
            Query query = session.createNativeQuery(sql);
            query.setParameter("blockId", blockId);
            query.executeUpdate();
            tx.commit();
            delete(block);
            return new CommonResponse(Boolean.TRUE, "Xóa khối thành công");
        } catch (Exception e) {
            rollback(tx);
            e.printStackTrace();
            return new CommonResponse(Boolean.FALSE, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
        } finally {
            close(session);
        }
    }

}
