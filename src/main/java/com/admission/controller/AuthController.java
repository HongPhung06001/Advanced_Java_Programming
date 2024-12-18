package com.admission.controller;

import com.admission.base.BaseDAO;
import com.admission.constants.CommonConstant;
import com.admission.constants.RoleConstant;
import com.admission.dto.LoginResponse;
import com.admission.dto.SignUpRequest;
import com.admission.lib.security.BCryptPasswordEncoder;
import com.admission.lib.security.PasswordEncoder;
import com.admission.mapper.StudentMapper;
import com.admission.mapper.UserMapper;
import com.admission.model.Student;
import com.admission.model.User;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.mapstruct.factory.Mappers;

public class AuthController extends BaseDAO {

    private final Logger logger = LogManager.getLogger(AuthController.class);

    private final PasswordEncoder passwordEncoder;

    private final StudentMapper studentMapper;

    private final UserMapper userMapper;

    public AuthController() {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.studentMapper = Mappers.getMapper(StudentMapper.class);
        this.userMapper = Mappers.getMapper(UserMapper.class);
    }

    public LoginResponse login(String username, String password) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        LoginResponse loginResponse = new LoginResponse();
        try {
            NativeQuery<User> query = session.createNativeQuery("SELECT * FROM users WHERE username = :username", User.class);
            query.setParameter("username", username);
            List<User> users = query.getResultList();
            if (CollectionUtils.isEmpty(users)) {
                loginResponse.setStatus(Boolean.FALSE);
                loginResponse.setMessage("Tài khoản không tồn tại!");
                return loginResponse;
            }
            User user = users.get(0);
            tx.commit();
            if(user.getIsLocked()) {
                loginResponse.setStatus(Boolean.FALSE);
                loginResponse.setMessage("Tài khoản này đã hết hạn không thể đăng nhập!");
                return loginResponse;
            }
            if (passwordEncoder.matches(password, user.getPassword())) {
                loginResponse.setStatus(Boolean.TRUE);
                loginResponse.setMessage(CommonConstant.SUCCESS);
                loginResponse.setUser(userMapper.toUserDTO(user));
                return loginResponse;
            } else {
                loginResponse.setStatus(Boolean.FALSE);
                loginResponse.setMessage("Mật khẩu không chính xác!");
                return loginResponse;
            }
        } catch (Exception e) {
            rollback(tx);
            logger.error(e.getMessage());
            e.printStackTrace();
            loginResponse.setStatus(Boolean.FALSE);
            loginResponse.setMessage("Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
            return loginResponse;
        } finally {
            close(session);
        }
    }

    public String signup(SignUpRequest signUpRequest) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            NativeQuery<User> queryUser = session.createNativeQuery("SELECT * FROM users WHERE username = :username", User.class);
            queryUser.setParameter("username", signUpRequest.getCitizenIdentityNumber());
            List<User> users = queryUser.getResultList();
            if (CollectionUtils.isNotEmpty(users)) {
                return "Số CMND/CCCD này đã được đăng ký!";
            }
            NativeQuery<Student> queryStudent = session.createNativeQuery("SELECT * FROM students WHERE email = :email", Student.class);
            queryStudent.setParameter("email", signUpRequest.getEmail());
            List<Student> students = queryStudent.getResultList();
            if (CollectionUtils.isNotEmpty(students)) {
                return "Email này đã được đăng ký!";
            }
            Student student = studentMapper.signUpRequestToStudent(signUpRequest);
            session.saveOrUpdate(student);
            User user = new User();
            user.setUsername(signUpRequest.getCitizenIdentityNumber());
            user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            user.setRoleName(RoleConstant.ROLE_USER);
            user.setIsLocked(Boolean.FALSE);
            user.setStudent(student);
            session.saveOrUpdate(user);
            tx.commit();
            return CommonConstant.SUCCESS;
        } catch (Exception e) {
            rollback(tx);
            logger.error(e.getMessage());
            e.printStackTrace();
            return "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!";
        } finally {
            close(session);
        }
    }

}
