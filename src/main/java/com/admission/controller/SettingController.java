package com.admission.controller;

import com.admission.base.BaseDAO;
import com.admission.dto.CommonResponse;
import com.admission.model.Setting;
import static com.admission.utils.SessionUtil.close;
import static com.admission.utils.SessionUtil.getSession;
import static com.admission.utils.SessionUtil.rollback;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class SettingController extends BaseDAO {

    public Setting getSettingByKey(String key) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            Query<Setting> query = session.createNativeQuery("SELECT * FROM settings WHERE setting_key = :key", Setting.class);
            query.setParameter("key", key);
            List<Setting> settings = query.getResultList();
            if (CollectionUtils.isEmpty(settings)) {
                return null;
            }
            Setting setting = settings.get(0);
            tx.commit();
            return setting;
        } catch (Exception e) {
            rollback(tx);
            e.printStackTrace();
            return null;
        } finally {
            close(session);
        }
    }

    public CommonResponse updateSetting(String key, String value) {
        try {
            Setting setting = getSettingByKey(key);
            if (ObjectUtils.isEmpty(setting)) {
                return new CommonResponse(Boolean.FALSE, "Key này chưa tồn tại!");
            }
            setting.setValue(value);
            save(setting);
            return new CommonResponse(Boolean.TRUE, "Cập nhật giá trị key thành công");
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResponse(Boolean.FALSE, "Cập nhật giá trị key thất bại!");
        }
    }

}
