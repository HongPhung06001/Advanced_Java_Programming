package com.admission.controller;

import com.admission.base.BaseDAO;
import com.admission.constants.AdmissionStatus;
import com.admission.constants.MajorDetailsStatus;
import com.admission.dto.CommonResponse;
import com.admission.dto.MajorDetailDTO;
import com.admission.dto.StatisticMajorDetailDTO;
import com.admission.mapper.MajorDetailMapper;
import com.admission.model.Major;
import com.admission.model.MajorDetail;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.List;

import static com.admission.utils.SessionUtil.*;

public class MajorDetailController extends BaseDAO {

    private final Logger logger = LogManager.getLogger(MajorDetailController.class);

    private final MajorController majorController;

    private final MajorDetailMapper majorDetailMapper;

    public MajorDetailController() {
        this.majorController = new MajorController();
        this.majorDetailMapper = Mappers.getMapper(MajorDetailMapper.class);
    }

    public List<MajorDetailDTO> getMajorDetailsForAdmin(Integer year, String keyword) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT md.id, m.code, m.name, GROUP_CONCAT(b.code SEPARATOR ',') AS block_codes, ");
            sql.append("CAST(md.bench_mark AS DECIMAL(4,2)), md.amount_student_received, m.id AS major_id  FROM majors m ");
            sql.append("LEFT JOIN major_details md ON m.id = md.major_id AND YEAR(md.created_date) = :year ");
            sql.append("LEFT JOIN major_block mb ON m.id = mb.major_id  ");
            sql.append("LEFT JOIN blocks b ON mb.block_id = b.id  ");
            sql.append("WHERE (COALESCE(:keyword, '') = '' OR m.code LIKE CONCAT('%', :keyword, '%') ");
            sql.append("OR m.name LIKE CONCAT('%', :keyword, '%'))");
            sql.append("GROUP BY m.id ");
            Query query = session.createNativeQuery(sql.toString());
            query.setParameter("year", year, IntegerType.INSTANCE);
            query.setParameter("keyword", keyword, StringType.INSTANCE);
            List<Object[]> queryResults = query.getResultList();
            tx.commit();
            return majorDetailMapper.objectsToMajorDetailDtos(queryResults);
        } catch (Exception e) {
            rollback(tx);
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            close(session);
        }
        return null;
    }

    public List<MajorDetailDTO> getMajorDetails(Integer year, String keyword) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT md.id, m.code, m.name, GROUP_CONCAT(b.code SEPARATOR ',') AS block_codes, ");
            sql.append("IF(md.status = 'PUBLIC', CAST(md.bench_mark AS DECIMAL(4,2)), NULL) AS bench_mark, ");
            sql.append("md.amount_student_received, m.id AS major_id FROM majors m ");
            sql.append("LEFT JOIN major_block mb ON m.id = mb.major_id  ");
            sql.append("LEFT JOIN blocks b ON mb.block_id = b.id  ");
            sql.append("INNER JOIN major_details md ON m.id = md.major_id ");
            sql.append("WHERE YEAR(md.created_date) = :year ");
            sql.append("AND (COALESCE(:keyword, '') = '' OR m.code LIKE CONCAT('%', :keyword, '%') ");
            sql.append("OR m.name LIKE CONCAT('%', :keyword, '%'))");
            sql.append("GROUP BY m.id ");
            Query query = session.createNativeQuery(sql.toString());
            query.setParameter("year", year, IntegerType.INSTANCE);
            query.setParameter("keyword", keyword, StringType.INSTANCE);
            List<Object[]> queryResults = query.getResultList();
            tx.commit();
            return majorDetailMapper.objectsToMajorDetailDtos(queryResults);
        } catch (Exception e) {
            rollback(tx);
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            close(session);
        }
        return null;
    }
    
    public List<MajorDetail> getMajorDetailsHaveCriteriaNoBenchmarks(int year){
         Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM major_details md ");
            sql.append("WHERE YEAR(md.created_date) = :year ");
            sql.append("AND (md.amount_student_received is not null and md.bench_mark is null)");
            Query query = session.createNativeQuery(sql.toString(), MajorDetail.class);
            query.setParameter("year", year, IntegerType.INSTANCE);
            tx.commit();
            return query.getResultList();
        } catch (Exception e) {
            rollback(tx);
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            close(session);
        }
        return null;
    }
   
    public Integer getYearMinMajor() {
    Session session = getSession();
    Transaction tx = session.beginTransaction();
    try {
        Query query = session.createNativeQuery("SELECT YEAR(MIN(md.created_date)) FROM major_details md");
        List<?> result = query.getResultList();
        tx.commit();
        
        if (result.isEmpty() || result.get(0) == null) {
            return 2016;
        } else {
            Object yearObject = result.get(0);
            if (yearObject instanceof Number) {
                return ((Number) yearObject).intValue();
            } else if (yearObject instanceof java.sql.Date) {
                java.sql.Date date = (java.sql.Date) yearObject;
                return date.toLocalDate().getYear();
            } else {
                throw new ClassCastException("Unexpected result type: " + yearObject.getClass().getName());
            }
        }
    } catch (Exception e) {
        rollback(tx);
        logger.error(e.getMessage());
        e.printStackTrace();
    } finally {
        close(session);
    }
    return null;
}


    public MajorDetail getMajorDetailNowByMajorId(Integer majorId) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            Integer yearNow = LocalDate.now().getYear();
            String queryString = "SELECT * FROM major_details "
                    + "WHERE major_id = :majorId AND YEAR(created_date) = :yearNow";
            Query query = session.createNativeQuery(queryString, MajorDetail.class);
            query.setParameter("majorId", majorId);
            query.setParameter("yearNow", yearNow);
            List<MajorDetail> majors = query.getResultList();
            tx.commit();
            if (CollectionUtils.isEmpty(majors)) {
                return null;
            }
            MajorDetail major = majors.get(0);
            return major;
        } catch (Exception e) {
            rollback(tx);
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            close(session);
        }
    }

    public MajorDetail getMajorDetailByMajorIdAndYear(Integer majorId, Integer year) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            Integer yearNow = LocalDate.now().getYear();
            String queryString = "SELECT * FROM major_details "
                    + "WHERE major_id = :majorId AND YEAR(created_date) = :year";
            Query query = session.createNativeQuery(queryString, MajorDetail.class);
            query.setParameter("majorId", majorId);
            query.setParameter("year", year);
            List<MajorDetail> majors = query.getResultList();
            tx.commit();
            if (CollectionUtils.isEmpty(majors)) {
                return null;
            }
            MajorDetail major = majors.get(0);
            return major;
        } catch (Exception e) {
            rollback(tx);
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            close(session);
        }
    }

    public CommonResponse createOrUpdateMajorDetails(List<MajorDetailDTO> majorDetailDTOs) {
        try {
            majorDetailDTOs.forEach(majorDetailDTO -> {
                createOrUpdateMajorDetail(majorDetailDTO);
            });
            return new CommonResponse(Boolean.TRUE, "Cập nhật thông tin chi tiết chuyên ngành thành công");
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return new CommonResponse(Boolean.FALSE, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
        }
    }

    public CommonResponse createOrUpdateMajorDetail(MajorDetailDTO majorDetailDTO) {
        try {
            Major major = majorController.getMajorByCode(majorDetailDTO.getCode());
            MajorDetail majorDetail = getMajorDetailNowByMajorId(majorDetailDTO.getMajorId());
            if (ObjectUtils.isEmpty(majorDetail)) {
                majorDetail = majorDetailMapper.toMajorDetail(majorDetailDTO);
            } else {
                if (ObjectUtils.isNotEmpty(majorDetailDTO.getAmountStudentReceived())
                        || ObjectUtils.isNotEmpty(majorDetailDTO.getBenchMark())) {
                    majorDetailMapper.updateMajorDetailFromDTO(majorDetailDTO, majorDetail);
                } else {
                    delete(majorDetail);
                }
            }
            majorDetail.setStatus(MajorDetailsStatus.IMPORT);
            majorDetail.setMajor(major);
            if (ObjectUtils.isNotEmpty(majorDetail.getAmountStudentReceived())
                    || ObjectUtils.isNotEmpty(majorDetail.getBenchMark())) {
                save(majorDetail);
            }
            return new CommonResponse(Boolean.TRUE, "Cập nhật thông tin chi tiết chuyên ngành thành công");
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return new CommonResponse(Boolean.FALSE, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
        }
    }

    public CommonResponse deleteMajorDetailById(Integer majorDetailId) throws Exception {
        MajorDetail majorDetail = (MajorDetail) findById(MajorDetail.class, majorDetailId);
        if (ObjectUtils.isEmpty(majorDetail)) {
            return new CommonResponse(Boolean.FALSE, "Không tìm thấy chi tiết chuyên ngành có id " + majorDetailId);
        }
        delete(majorDetail);
        return new CommonResponse(Boolean.TRUE, "Xóa chi tiết chuyên ngành thành công");
    }

    public List<StatisticMajorDetailDTO> statisticMajorDetail(Integer year, String keyword) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT m.code, m.name, md.amount_student_received, md.bench_mark, ");
            sql.append("COUNT(a.id) AS number_of_students_registered, ");
            sql.append("COUNT(CASE WHEN a.status = :pass THEN a.id END) AS number_of_students_passed, ");
            sql.append("COUNT(CASE WHEN a.status = :fail THEN a.id END) AS number_of_students_failed ");
            sql.append("FROM majors m ");
            sql.append("INNER JOIN major_details md ON m.id = md.major_id ");
            sql.append("INNER JOIN admissions a ON a.major_id = m.id ");
            sql.append("WHERE YEAR(md.created_date) = :year AND YEAR(a.created_date) = :year ");
            sql.append("AND (COALESCE(:keyword, '') = '' OR m.code LIKE CONCAT('%', :keyword, '%') ");
            sql.append("OR m.name LIKE CONCAT('%', :keyword, '%')) ");
            sql.append("GROUP BY m.id ");
            sql.append("ORDER BY m.name ");
            Query query = session.createNativeQuery(sql.toString());
            query.setParameter("pass", AdmissionStatus.PASS.getValue(), IntegerType.INSTANCE);
            query.setParameter("fail", AdmissionStatus.FAIL.getValue(), IntegerType.INSTANCE);
            query.setParameter("year", year, IntegerType.INSTANCE);
            query.setParameter("keyword", keyword, StringType.INSTANCE);
            List<Object[]> queryResults = query.getResultList();
            tx.commit();
            return majorDetailMapper.objectsToStatisticMajorDetailDtos(queryResults);
        } catch (Exception e) {
            rollback(tx);
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            close(session);
        }
        return null;
    }

    public void changePublicMajorDetail() {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            String sql = "UPDATE major_details SET status = 'PUBLIC' WHERE YEAR(created_date) = :nowYear";
            Query query = session.createNativeQuery(sql);
            query.setParameter("nowYear", LocalDate.now().getYear(), IntegerType.INSTANCE);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

}
