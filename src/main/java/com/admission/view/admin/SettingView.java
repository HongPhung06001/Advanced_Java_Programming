package com.admission.view.admin;

import com.admission.components.dialog.ConfirmDialog;
import com.admission.constants.SettingConstant;
import com.admission.controller.SettingController;
import com.admission.controller.StudentController;
import com.admission.dto.CommonResponse;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SettingView extends JPanel {

    private final SettingController settingController;

    private final StudentController studentController;

    private final DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public SettingView() {
        initComponents();
        this.settingController = new SettingController();
        this.studentController = new StudentController();

        setupDateTimePicker();
        loadSettings();
    }

    private void loadSettings() {
        jNgayBatDau.setDateTimePermissive(LocalDateTime.parse(
                settingController.getSettingByKey(SettingConstant.START_TIME_ADMISSION).getValue(), formatter));
        jNgayKetThuc.setDateTimePermissive(LocalDateTime.parse(
                settingController.getSettingByKey(SettingConstant.END_TIME_ADMISSION).getValue(), formatter));
    }

    private void setupDateTimePicker() {
        DatePickerSettings settingDateNgayBatDau = new DatePickerSettings();
        settingDateNgayBatDau.setFormatForDatesCommonEra(formatterDate);
        jNgayBatDau.datePicker.setSettings(settingDateNgayBatDau);
        jNgayBatDau.setDateTimePermissive(LocalDateTime.now());

        DatePickerSettings settingNgayKetThuc = new DatePickerSettings();
        settingNgayKetThuc.setFormatForDatesCommonEra(formatterDate);
        jNgayKetThuc.datePicker.setSettings(settingNgayKetThuc);
        jNgayKetThuc.setDateTimePermissive(LocalDateTime.now().plusDays(1));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorder1 = new com.admission.components.border.PanelBorder();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jSua = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jNgayKetThuc = new com.github.lgooddatepicker.components.DateTimePicker();
        jNgayBatDau = new com.github.lgooddatepicker.components.DateTimePicker();
        jLockStudents = new javax.swing.JButton();

        jLabel2.setText("Ngày bắt đầu mở đăng ký nguyện vọng");
        jLabel2.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N

        jLabel5.setText("Ngày kết thúc đăng ký nguyện vọng");
        jLabel5.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Edit.png"))); // NOI18N
        jSua.setText("Sửa");
        jSua.setBackground(new java.awt.Color(51, 153, 255));
        jSua.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(351, Short.MAX_VALUE)
                .addComponent(jSua, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(353, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSua)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setText("Cài đặt ứng dụng");
        jLabel1.setFont(new java.awt.Font("sansserif", 1, 22)); // NOI18N

        jNgayKetThuc.setBackground(new java.awt.Color(255, 255, 255));
        jNgayKetThuc.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N

        jNgayBatDau.setBackground(new java.awt.Color(255, 255, 255));
        jNgayBatDau.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N

        jLockStudents.setText("Khóa các tài khoản đã kết thúc đợt đăng ký");
        jLockStudents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLockStudentsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jNgayBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                            .addComponent(jNgayKetThuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLockStudents)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(23, 23, 23)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jLockStudents, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, 817, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSuaActionPerformed
        try {
            LocalDateTime selectedStartDateTime = jNgayBatDau.getDateTimeStrict();
            String start = selectedStartDateTime.format(formatter);
            LocalDateTime selectedEndDateTime = jNgayKetThuc.getDateTimeStrict();
            String end = selectedEndDateTime.format(formatter);
            if (selectedStartDateTime.isAfter(selectedEndDateTime)) {
                JOptionPane.showMessageDialog(null, "Thời gian kết thúc phải lớn hơn thời gian bắt đầu!");
                return;
            }

            CommonResponse commonResponse1 = settingController.updateSetting(SettingConstant.START_TIME_ADMISSION, start);
            CommonResponse commonResponse2 = settingController.updateSetting(SettingConstant.END_TIME_ADMISSION, end);
            if (commonResponse1.getStatus().equals(Boolean.TRUE) && commonResponse2.getStatus().equals(Boolean.TRUE)) {
                new ConfirmDialog(null, "Cập nhật thành công", "Cập nhật cài đặt thành công");
                loadSettings();
            } else {
                if (commonResponse1.getStatus().equals(Boolean.FALSE)) {
                    JOptionPane.showMessageDialog(null, commonResponse1.getMessage());
                } else if (commonResponse2.getStatus().equals(Boolean.FALSE)) {
                    JOptionPane.showMessageDialog(null, commonResponse2.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
        }
    }//GEN-LAST:event_jSuaActionPerformed

    private void jLockStudentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLockStudentsActionPerformed
        int option = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn khóa?",
                "Xác nhận khóa", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            try {
                CommonResponse response = studentController.lockStudents();
                if (response.getStatus().equals(Boolean.TRUE)) {
                    new ConfirmDialog(null, "Khóa thành công", response.getMessage());
                    settingController.updateSetting(SettingConstant.CALL_TOTAL_SCORE_STUDENT, "false");
                } else {
                    JOptionPane.showMessageDialog(null, response.getMessage());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jLockStudentsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JButton jLockStudents;
    private com.github.lgooddatepicker.components.DateTimePicker jNgayBatDau;
    private com.github.lgooddatepicker.components.DateTimePicker jNgayKetThuc;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jSua;
    private com.admission.components.border.PanelBorder panelBorder1;
    // End of variables declaration//GEN-END:variables
}
