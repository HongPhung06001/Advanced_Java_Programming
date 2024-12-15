package com.admission.controller;

import com.admission.base.BaseDAO;
import com.admission.constants.RoleConstant;
import com.admission.constants.SettingConstant;
import com.admission.dto.CommonResponse;
import com.admission.dto.StudentDTO;
import com.admission.mapper.StudentMapper;
import com.admission.model.Setting;
import com.admission.model.Student;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.mapstruct.factory.Mappers;

import java.util.List;
import org.apache.commons.lang3.ObjectUtils;

public class StudentController extends BaseDAO {

    private final Logger logger = LogManager.getLogger(StudentController.class);

    private final SettingController settingController;

    private final StudentMapper studentMapper;

    public StudentController() {
        this.settingController = new SettingController();
        this.studentMapper = Mappers.getMapper(StudentMapper.class);
    }

    public List<StudentDTO> getStudents(Integer year, String keyword) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM students ");
            sql.append("WHERE YEAR(created_date) = :year ");
            sql.append("AND (COALESCE(:keyword, '') = '' OR first_name LIKE CONCAT('%', :keyword, '%') ");
            sql.append("OR last_name LIKE CONCAT('%', :keyword, '%') OR citizen_identity_number LIKE CONCAT('%', :keyword, '%')");
            sql.append("OR email LIKE CONCAT('%', :keyword, '%') OR phone_number LIKE CONCAT('%', :keyword, '%')");
            sql.append("OR order_number LIKE CONCAT('%', :keyword, '%'))");
            Query query = session.createNativeQuery(sql.toString(), Student.class);
            query.setParameter("year", year);
            query.setParameter("keyword", keyword);
            List<Student> queryResults = query.getResultList();
            tx.commit();
            return studentMapper.toStudentDTOs(queryResults);
        } catch (Exception e) {
            rollback(tx);
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            close(session);
        }
        return null;
    }

    public StudentDTO getInfoStudent(Integer studentId) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            Query query = session.createNativeQuery("SELECT * FROM students WHERE id = :id", Student.class);
            query.setParameter("id", studentId);
            List<Student> students = query.getResultList();
            Student student = students.get(0);
            tx.commit();
            return studentMapper.toStudentDTO(student);
        } catch (Exception e) {
            rollback(tx);
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            close(session);
        }
    }

    public Student getStudentById(Integer studentId) throws Exception {
        return (Student) findById(Student.class, studentId);
    }

    public CommonResponse createStudent(StudentDTO studentDTO) throws Exception {
        List<Student> students = (List<Student>) findAll(Student.class);
        for (Student s : students) {
            if (s.getEmail().equals(studentDTO.getEmail())) {
                return new CommonResponse(Boolean.FALSE, "Email này đã được đăng ký!");
            } else if (s.getCitizenIdentityNumber().equals(studentDTO.getCitizenIdentityNumber())) {
                return new CommonResponse(Boolean.FALSE, "Số CMND/CCCD này đã được đăng ký!");
            } else if (ObjectUtils.isNotEmpty(studentDTO.getOrderNumber()) && s.getOrderNumber().equals(studentDTO.getOrderNumber())) {
                return new CommonResponse(Boolean.FALSE, "SBD này đã trùng với người khác!");
            }
        }
        Student student = studentMapper.toStudent(studentDTO);
        save(student);
        return new CommonResponse(Boolean.TRUE, "Thêm sinh viên thành công");
    }

    public CommonResponse updateStudent(StudentDTO studentDTO) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            List<Student> students = (List<Student>) findAll(Student.class);
            for (Student s : students) {
                if (s.getEmail().equals(studentDTO.getEmail()) && !s.getId().equals(studentDTO.getId())) {
                    return new CommonResponse(Boolean.FALSE, "Email này đã được đăng ký!");
                } else if (s.getCitizenIdentityNumber().equals(studentDTO.getCitizenIdentityNumber())
                        && !s.getId().equals(studentDTO.getId())) {
                    return new CommonResponse(Boolean.FALSE, "Số CMND/CCCD này đã được đăng ký!");
                } else if (ObjectUtils.isNotEmpty(studentDTO.getOrderNumber()) && ObjectUtils.isNotEmpty(s.getOrderNumber())
                        && s.getOrderNumber().equals(studentDTO.getOrderNumber()) && !s.getId().equals(studentDTO.getId())) {
                    return new CommonResponse(Boolean.FALSE, "SBD này đã trùng với người khác!");
                }
            }
            Student student = session.load(Student.class, studentDTO.getId());
            studentMapper.updateStudentFromDTO(studentDTO, student);
            session.saveOrUpdate(student);
            tx.commit();
            return new CommonResponse(Boolean.TRUE, "Cập nhật thông tin cá nhân thành công");
        } catch (Exception e) {
            rollback(tx);
            logger.error(e.getMessage());
            e.printStackTrace();
            return new CommonResponse(Boolean.FALSE, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
        } finally {
            close(session);
        }
    }

    public CommonResponse deleteStudentById(Integer studentId) throws Exception {
        Student student = (Student) findById(Student.class, studentId);
        if (ObjectUtils.isEmpty(student)) {
            return new CommonResponse(Boolean.FALSE, "Không tìm thấy sinh viên có id " + studentId);
        }
        delete(student);
        return new CommonResponse(Boolean.TRUE, "Xóa sinh viên thành công");
    }

    public CommonResponse checkInfoStudent(Integer studentId) {
        try {
            Student student = (Student) findById(Student.class, studentId);
            if (ObjectUtils.isEmpty(student)) {
                return new CommonResponse(Boolean.FALSE, "Không tìm thấy sinh viên có id " + studentId);
            }
            boolean check;
            if (ObjectUtils.isEmpty(student.getOrderNumber())) {
                check = false;
            } else if (ObjectUtils.isEmpty(student.getAvatar())) {
                check = false;
            } else if (ObjectUtils.isEmpty(student.getAddress())) {
                check = false;
            } else if (ObjectUtils.isEmpty(student.getGender())) {
                check = false;
            } else if (ObjectUtils.isEmpty(student.getBirthDay())) {
                check = false;
            } else if (ObjectUtils.isEmpty(student.getEthnic())) {
                check = false;
            } else {
                check = true;
            }
            if (check) {
                return new CommonResponse(Boolean.TRUE, "");
            } else {
                return new CommonResponse(Boolean.FALSE, "Vui lòng cập nhật đầy đủ thông tin cá nhân!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResponse(Boolean.FALSE, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
        }
    }

    public CommonResponse lockStudents() {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            Setting setting = settingController.getSettingByKey(SettingConstant.END_TIME_ADMISSION);
            String sql = "UPDATE users SET is_locked = 1 WHERE created_date < :end_time_admission AND role_name = :roleName";
            Query query = session.createNativeQuery(sql);
            query.setParameter("end_time_admission", setting.getValue());
            query.setParameter("roleName", RoleConstant.ROLE_USER.toString());
            query.executeUpdate();
            tx.commit();
            return new CommonResponse(Boolean.TRUE, "Khóa các tài khoản đã kết thúc đợt đăng ký thành công");
        } catch (Exception e) {
            rollback(tx);
            logger.error(e.getMessage());
            e.printStackTrace();
            return new CommonResponse(Boolean.FALSE, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
        } finally {
            close(session);
        }
    }

}
