package com.admission.base;

import com.admission.utils.SessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public abstract class BaseDAO extends SessionUtil {

    private Session session;

    private Transaction tx;

    public BaseDAO() {
        get();
    }

    public final Object runQuery(Query<?> q) throws Exception {
        int ret = -1;
        try {
            startOperation();
            ret = q.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            handleException(e);
            throw new Exception(e);
        } finally {
            close(session);
        }
        return ret;
    }

    public final List<?> runReadQuery(Query<?> q) throws Exception {
        List<?> list;
        try {
            startOperation();
            list = q.list();
        } catch (Exception e) {
            handleException(e);
            throw new Exception(e);
        } finally {
            close(session);
        }
        return list;
    }

    public final Object findById(Class<?> clazz, Integer id) throws Exception {
        Object obj;
        try {
            startOperation();
            obj = session.load(clazz, id);
        } catch (Exception e) {
            handleException(e);
            throw new Exception(e);
        } finally {
            close(session);
        }
        return obj;
    }

    public final List<?> findAll(Class<?> clazz) throws Exception {
        List<?> objects;
        try {
            session = getSession();
            Query<?> query = session.createQuery("from " + clazz.getName());
            objects = query.list();
        } catch (Exception e) {
            handleException(e);
            throw new Exception(e);
        } finally {
            close(session);
        }
        return objects;
    }

    public void save(Object obj) throws Exception {
        try {
            startOperation();
            session.saveOrUpdate(obj);
            tx.commit();
        } catch (Exception e) {
            handleException(e);
            throw new Exception(e);
        } finally {
            close(session);
        }
    }

    public final void delete(Object obj) throws Exception {
        try {
            startOperation();
            session.delete(obj);
            tx.commit();
        } catch (Exception e) {
            handleException(e);
            throw new Exception(e);
        } finally {
            close(session);
        }
    }

    public final void handleException(Exception e) {
        System.out.println("Transaction rollback due to " + e.getMessage());
        rollback(tx);
    }

    public final void startOperation() {
        session = getSession();
        tx = session.beginTransaction();
    }
}
