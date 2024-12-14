package com.admission.controller;

import com.admission.base.BaseDAO;
import com.admission.constants.AdmissionStatus;
import com.admission.dto.*;
import com.admission.mapper.AdmissionMapper;
import com.admission.model.Admission;
import com.admission.model.Block;
import com.admission.model.Major;
import com.admission.model.Student;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static com.admission.utils.SessionUtil.*;

public class AdmissionController extends BaseDAO {

    private final StudentController studentController;

    private final MajorController majorController;

    private final MajorDetailController majorDetailController;

    private final BlockController blockController;

    private final AdmissionMapper admissionMapper;

    public AdmissionController() {
        this.studentController = new StudentController();
        this.majorController = new MajorController();
        this.majorDetailController = new MajorDetailController();
        this.blockController = new BlockController();
        this.admissionMapper = Mappers.getMapper(AdmissionMapper.class);
    }

    public List<AdmissionResultDTO> getAdmissionResult(AdmissionResultRequest request) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT m.code AS major_name, m.name, s.last_name, s.first_name, s.order_number, s.citizen_identity_number, ");
            sql.append("s.email, s.phone_number, s.gender, s.address, a.orders, b.code, CAST(a.total_score AS DECIMAL(4,2)), a.status ");
            sql.append("FROM admissions a ");
            sql.append("INNER JOIN students s on s.id = a.student_id ");
            sql.append("INNER JOIN blocks b on b.id = a.block_id ");
            sql.append("INNER JOIN majors m on m.id = a.major_id ");
            sql.append("WHERE YEAR(a.created_date) = :year AND m.code = :code ");
            sql.append("AND ((:status IS NULL AND a.status IN (2, 3)) OR a.status = :status)");
            sql.append("AND (COALESCE(:keyword, '') = '' OR s.last_name LIKE CONCAT('%', :keyword, '%') ");
            sql.append("OR s.last_name LIKE CONCAT('%', :keyword, '%') OR s.first_name LIKE CONCAT('%', :keyword, '%')");
            sql.append("OR s.order_number LIKE CONCAT('%', :keyword, '%') OR s.citizen_identity_number LIKE CONCAT('%', :keyword, '%')");
            sql.append("OR s.email LIKE CONCAT('%', :keyword, '%') OR s.phone_number LIKE CONCAT('%', :keyword, '%')) ");
            sql.append("ORDER BY a.total_score DESC ");
            Query query = session.createNativeQuery(sql.toString());
            query.setParameter("year", request.getYear());
            query.setParameter("keyword", request.getKeyword());
            query.setParameter("code", request.getCode());
            query.setParameter("status", request.getStatusAdmission());
            List<Object[]> queryResults = query.getResultList();
            tx.commit();
            return admissionMapper.objectsToAdmissionResultDtos(queryResults);
        } catch (Exception e) {
            rollback(tx);
            e.printStackTrace();
            return null;
        } finally {
            close(session);
        }
    }

    public List<AdmissionResultDTO> getAdmissionResultByYearAndStatus(Integer year, Integer status) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT m.code AS major_name, m.name, s.last_name, s.first_name, s.order_number, s.citizen_identity_number, ");
            sql.append("s.email, s.phone_number, s.gender, s.address, a.orders, b.code, CAST(a.total_score AS DECIMAL(4,2)), a.status ");
            sql.append("FROM admissions a ");
            sql.append("INNER JOIN students s on s.id = a.student_id ");
            sql.append("INNER JOIN blocks b on b.id = a.block_id ");
            sql.append("INNER JOIN majors m on m.id = a.major_id ");
            sql.append("WHERE YEAR(a.created_date) = :year ");
            sql.append("AND ((:status IS NULL AND a.status IN (2, 3)) OR a.status = :status)");
            sql.append("ORDER BY m.name, a.total_score DESC ");
            Query query = session.createNativeQuery(sql.toString());
            query.setParameter("year", year);
            query.setParameter("status", status);
            List<Object[]> queryResults = query.getResultList();
            tx.commit();
            return admissionMapper.objectsToAdmissionResultDtos(queryResults);
        } catch (Exception e) {
            rollback(tx);
            e.printStackTrace();
            return null;
        } finally {
            close(session);
        }
    }

    public List<Admission> getAdmissionsByStudentId(Integer studentId) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            String sql = "SELECT * FROM admissions WHERE student_id = :studentId ORDER BY orders";
            Query query = session.createNativeQuery(sql, Admission.class);
            query.setParameter("studentId", studentId);
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

    public Admission getAdmissionsByStudentIdAndOrders(Integer studentId, Integer orders) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            String sql = "SELECT * FROM admissions WHERE student_id = :studentId AND orders = :orders";
            Query query = session.createNativeQuery(sql, Admission.class);
            query.setParameter("studentId", studentId);
            query.setParameter("orders", orders);
            List<Admission> admissions = query.getResultList();
            if (CollectionUtils.isEmpty(admissions)) {
                return null;
            }
            Admission admission = admissions.get(0);
            tx.commit();
            return admission;
        } catch (Exception e) {
            rollback(tx);
            e.printStackTrace();
            return null;
        } finally {
            close(session);
        }
    }

    public CommonResponse createAdmission(AdmissionCreateDTO admissionCreateDTO) {
        try {
            Student student = studentController.getStudentById(admissionCreateDTO.getStudentId());
            Major major = majorController.getMajorByCode(admissionCreateDTO.getMajorCode());
            List<Admission> admissions = getAdmissionsByStudentId(student.getId());
            for (Admission admission : admissions) {
                if (admission.getOrders().equals(admissionCreateDTO.getOrders())) {
                    return new CommonResponse(Boolean.FALSE, "Bạn đã đăng ký nguyện vọng " + admissionCreateDTO.getOrders());
                }
                if (admission.getMajor().getId().equals(major.getId())) {
                    return new CommonResponse(Boolean.FALSE, "Bạn đã đăng ký ngành " + major.getName());
                }
            }
            Block block = blockController.getBlockByCode(admissionCreateDTO.getBlock());
            Admission admission = new Admission();
            admission.setOrders(admissionCreateDTO.getOrders());
            admission.setStudent(student);
            admission.setMajor(major);
            admission.setBlock(block);
            admission.setStatus(AdmissionStatus.PENDING.getValue());
            save(admission);
            return new CommonResponse(Boolean.TRUE, "Đăng ký nguyện vọng thành công");
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResponse(Boolean.FALSE, "Đăng ký nguyện vọng thất bại!");
        }
    }

    public CommonResponse updateAdmission(AdmissionUpdateDTO admissionUpdateDTO) {
        try {
            List<Admission> admissions = getAdmissionsByStudentId(admissionUpdateDTO.getStudentId());
            for (Admission am : admissions) {
                if (am.getOrders().equals(admissionUpdateDTO.getOrders())
                        && !admissionUpdateDTO.getOldOrders().equals(admissionUpdateDTO.getOrders())) {
                    return new CommonResponse(Boolean.FALSE, "Bạn đã đăng ký nguyện vọng " + admissionUpdateDTO.getOrders());
                }
            }
            Admission admission = getAdmissionsByStudentIdAndOrders(
                    admissionUpdateDTO.getStudentId(), admissionUpdateDTO.getOldOrders());
            Block block = blockController.getBlockByCode(admissionUpdateDTO.getBlock());
            admission.setOrders(admissionUpdateDTO.getOrders());
            admission.setBlock(block);
            admission.setStatus(AdmissionStatus.PENDING.getValue());
            save(admission);
            return new CommonResponse(Boolean.TRUE, "Cập nhật nguyện vọng thành công");
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResponse(Boolean.FALSE, "Cập nhật nguyện vọng thất bại!");
        }
    }

    public CommonResponse adminUpdateAdmission(Admission admission) {
        try {
            save(admission);
            return new CommonResponse(Boolean.TRUE, "Cập nhật nguyện vọng thành công");
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResponse(Boolean.FALSE, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
        }
    }

    public CommonResponse deleteAdmission(Integer studentId, Integer orders) throws Exception {
        Admission admission = getAdmissionsByStudentIdAndOrders(studentId, orders);
        if (ObjectUtils.isEmpty(admission)) {
            return new CommonResponse(Boolean.FALSE, String.format("Không tìm thấy nguyện vọng %s của sinh viên %s!", orders, studentId));
        }
        delete(admission);
        return new CommonResponse(Boolean.TRUE, "Xóa nguyện vọng thành công");
    }

    public void handleAdmissonAndSendMail(int year) {
        try {
            majorDetailController.changePublicMajorDetail();
            List<StudentDTO> students = studentController.getStudents(year, "");
            students.stream().map(student -> {
                StringBuilder threadName = new StringBuilder("Thread-");
                threadName.append(student.getLastName());
                threadName.append("-");
                threadName.append(student.getFirstName());
                threadName.append("-");
                threadName.append(student.getOrderNumber());
                ThreadHandleAdmission thread = new ThreadHandleAdmission(threadName.toString(), year, student);
                return thread;
            }).forEachOrdered(thread -> {
                thread.start();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void randomTotalScoreStudentByBlock(int year) {
        try {
            List<StudentDTO> students = studentController.getStudents(year, "");
            students.stream().map(student -> {
                StringBuilder threadName = new StringBuilder("Thread-Random-Total-Score-");
                threadName.append(student.getLastName());
                threadName.append("-");
                threadName.append(student.getFirstName());
                threadName.append("-");
                threadName.append(student.getOrderNumber());
                ThreadHandleRandomTotalScore thread = new ThreadHandleRandomTotalScore(threadName.toString(), student);
                return thread;
            }).forEachOrdered(thread -> {
                thread.start();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
