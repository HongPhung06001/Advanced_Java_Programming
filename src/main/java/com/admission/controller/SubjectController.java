package com.admission.controller;

import com.admission.base.BaseDAO;
import com.admission.dto.CommonResponse;
import com.admission.dto.SubjectDTO;
import com.admission.mapper.SubjectMapper;
import com.admission.model.Subject;
import static com.admission.utils.SessionUtil.close;
import static com.admission.utils.SessionUtil.getSession;
import static com.admission.utils.SessionUtil.rollback;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.mapstruct.factory.Mappers;

public class SubjectController extends BaseDAO {

    private final SubjectMapper subjectMapper;

    public SubjectController() {
        this.subjectMapper = Mappers.getMapper(SubjectMapper.class);
    }

    public List<Subject> getSubjects() throws Exception {
        return (List<Subject>) findAll(Subject.class);
    }

    public List<Subject> getSubjects(String keyword) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            String queryString = "SELECT * FROM subjects WHERE (COALESCE(:keyword, '') = '' OR name LIKE CONCAT('%', :keyword, '%'))";
            Query query = session.createNativeQuery(queryString, Subject.class);
            query.setParameter("keyword", keyword);
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

    public List<Subject> getSubjectsByName(List<String> names) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            Query query = session.createQuery("FROM Subject WHERE name IN :names", Subject.class);
            query.setParameter("names", names);
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

    public Subject getSubjectByName(String name) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            Query query = session.createQuery("FROM Subject WHERE name = :name", Subject.class);
            query.setParameter("name", name);
            List<Subject> subjects = query.getResultList();
            tx.commit();
            if (CollectionUtils.isEmpty(subjects)) {
                return null;
            }
            Subject subject = subjects.get(0);
            return subject;
        } catch (Exception e) {
            rollback(tx);
            e.printStackTrace();
            return null;
        } finally {
            close(session);
        }
    }

    public CommonResponse createSubject(SubjectDTO subjectDTO) {
        try {
            if (ObjectUtils.isNotEmpty(getSubjectByName(subjectDTO.getName()))) {
                return new CommonResponse(Boolean.FALSE, "Môn học này đã tồn tại!");
            }
            Subject subject = subjectMapper.toSubject(subjectDTO);
            save(subject);
            return new CommonResponse(Boolean.TRUE, "Tạo môn học thành công");
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResponse(Boolean.FALSE, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
        }
    }

    public CommonResponse updateSubject(SubjectDTO subjectDTO) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            if (ObjectUtils.isNotEmpty(getSubjectByName(subjectDTO.getName()))) {
                return new CommonResponse(Boolean.FALSE, "Môn học này đã tồn tại!");
            }
            Subject subject = session.load(Subject.class, subjectDTO.getId());
            subjectMapper.updateSubjectFromDTO(subjectDTO, subject);
            session.saveOrUpdate(subject);
            tx.commit();
            return new CommonResponse(Boolean.TRUE, "Tạo môn học thành công");
        } catch (Exception e) {
            rollback(tx);
            e.printStackTrace();
            return new CommonResponse(Boolean.FALSE, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
        } finally {
            close(session);
        }
    }

    public CommonResponse deleteSubjectById(Integer subjectId) throws Exception {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            Subject subject = (Subject) findById(Subject.class, subjectId);
            if (ObjectUtils.isEmpty(subject)) {
                return new CommonResponse(Boolean.FALSE, "Không tìm thấy môn học có id " + subjectId);
            }
            String sql = "DELETE FROM block_subject WHERE subject_id = :subjectId";
            Query query = session.createNativeQuery(sql);
            query.setParameter("subjectId", subjectId);
            query.executeUpdate();
            tx.commit();
            delete(subject);
            return new CommonResponse(Boolean.TRUE, "Xóa môn học thành công");
        } catch (Exception e) {
            rollback(tx);
            e.printStackTrace();
            return new CommonResponse(Boolean.FALSE, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
        } finally {
            close(session);
        }
    }

}
