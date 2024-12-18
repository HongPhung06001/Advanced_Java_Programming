package com.admission.view.user;

import com.admission.components.dialog.ConfirmDialog;
import com.admission.constants.CommonConstant;
import com.admission.controller.StudentController;
import com.admission.dto.CommonResponse;
import com.admission.dto.StudentDTO;
import com.admission.utils.CurrentUserLogin;
import com.admission.utils.DateUtils;
import com.admission.utils.ResourceUtil;
import java.io.File;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.lang3.ObjectUtils;

public class ProfileView extends javax.swing.JPanel {

    private final StudentController studentController;

    private File currentAvatar;

    public ProfileView() {
        initComponents();
        studentController = new StudentController();
        loadInfo();
    }

    private void loadInfo() {
        StudentDTO student = studentController.getInfoStudent(CurrentUserLogin.user.getStudentId());
        if (ObjectUtils.isNotEmpty(student)) {
            jHoDem.setText(student.getLastName());
            jTen.setText(student.getFirstName());
            jSoCMND.setText(student.getCitizenIdentityNumber());
            jNgayCap.setDate(DateUtils.asDate(student.getCitizenIdentityIssueDate()));
            jNguoiCap.setText(student.getCitizenIdentityIssuedBy());
            jSBD.setText(student.getOrderNumber());
            jPhone.setText(student.getPhoneNumber());
            jEmail.setText(student.getEmail());
            jDanToc.setText(student.getEthnic());
            jDiaChi.setText(student.getAddress());
            if (ObjectUtils.isNotEmpty(student.getBirthDay())) {
                jNgaySinh.setDate(DateUtils.asDate(student.getBirthDay()));
            }
            if (ObjectUtils.isNotEmpty(student.getGender())) {
                if (student.getGender().equals("Nam")) {
                    jNam.setSelected(true);
                } else {
                    jNu.setSelected(true);
                }
            }
            if (ObjectUtils.isNotEmpty(student.getAvatar())) {
                ImageIcon imageIcon = ResourceUtil.getAvatar(student.getAvatar());
                imageIcon.getImage().flush();
                currentAvatar = ResourceUtil.getFileByPath(student.getAvatar());
                jAvatar.setIcon(ResourceUtil.getAvatar(student.getAvatar()));
            }
        }
    }

    private boolean validateInput() {
        if (ObjectUtils.isEmpty(jHoDem.getText())) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập họ đệm!");
            return false;
        }
        if (ObjectUtils.isEmpty(jTen.getText())) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập họ tên!");
            return false;
        }
        if (ObjectUtils.isEmpty(jSoCMND.getText())) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số CMND/CCCD!");
            return false;
        }
        if (ObjectUtils.isEmpty(jNguoiCap.getText())) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập người cấp CMND/CCCD!");
            return false;
        }
        if (ObjectUtils.isEmpty(jNgayCap.getDate())) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập ngày cấp CMND/CCCD!");
            return false;
        }
        if (ObjectUtils.isEmpty(jEmail.getText())) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập email!");
            return false;
        }
        Pattern patternEmail = Pattern.compile(CommonConstant.REGEX_EMAIL);
        Matcher matcherEmail = patternEmail.matcher(jEmail.getText());
        if(!matcherEmail.matches()) {
            JOptionPane.showMessageDialog(null, "Địa chỉ email không hợp lệ!");
            return false;
        }
        if (ObjectUtils.isEmpty(jPhone.getText())) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số điện thoại!");
            return false;
        }
        Pattern patternPhone = Pattern.compile(CommonConstant.REGEX_PHONE);
        Matcher matcherPhone = patternPhone.matcher(jPhone.getText());
        if(!matcherPhone.matches()) {
            JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ!");
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorder1 = new com.admission.components.border.PanelBorder();
        jUpload = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSBD = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jHoDem = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jDanToc = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jSoCMND = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTen = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jDiaChi = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jNguoiCap = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jEmail = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPhone = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jNgayCap = new com.toedter.calendar.JDateChooser();
        jNgaySinh = new com.toedter.calendar.JDateChooser();
        jNu = new javax.swing.JRadioButton();
        jNam = new javax.swing.JRadioButton();
        jAvatar = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        panelBorder1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jUpload.setText("Chọn ảnh");
        jUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jUploadActionPerformed(evt);
            }
        });
        panelBorder1.add(jUpload, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 350, -1, -1));

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(127, 127, 127));
        jLabel1.setText("Thông tin cá nhân");
        panelBorder1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, -1, 38));

        jLabel4.setFont(new java.awt.Font("sansserif", 0, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Dân tộc");
        panelBorder1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 390, 60, -1));

        jSBD.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSBD.setBorder(null);
        panelBorder1.add(jSBD, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 260, 260, 30));

        jLabel5.setFont(new java.awt.Font("sansserif", 0, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Họ Đệm");
        panelBorder1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 75, 60, -1));

        jHoDem.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jHoDem.setBorder(null);
        panelBorder1.add(jHoDem, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 100, 260, 30));

        jLabel3.setText("________________________________________");
        panelBorder1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(321, 115, -1, -1));

        jLabel6.setFont(new java.awt.Font("sansserif", 0, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Ngày sinh");
        panelBorder1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 470, 150, -1));

        jLabel8.setFont(new java.awt.Font("sansserif", 0, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Số CMND/CCCD");
        panelBorder1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 155, 180, -1));

        jDanToc.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jDanToc.setBorder(null);
        panelBorder1.add(jDanToc, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 410, 260, 30));

        jLabel7.setText("________________________________________");
        panelBorder1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(321, 425, -1, -1));

        jSoCMND.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jSoCMND.setBorder(null);
        panelBorder1.add(jSoCMND, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 180, 260, 30));

        jLabel9.setText("________________________________________");
        panelBorder1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(321, 195, -1, -1));

        jLabel10.setFont(new java.awt.Font("sansserif", 0, 15)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Tên");
        panelBorder1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 70, 90, -1));

        jTen.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jTen.setBorder(null);
        panelBorder1.add(jTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 100, 260, 30));

        jLabel11.setText("________________________________________");
        panelBorder1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(671, 115, -1, -1));

        jLabel12.setFont(new java.awt.Font("sansserif", 0, 15)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("Ngày cấp CMND/CCCD");
        panelBorder1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 155, 170, -1));

        jLabel14.setFont(new java.awt.Font("sansserif", 0, 15)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText("Email");
        panelBorder1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 315, 60, -1));

        jDiaChi.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jDiaChi.setBorder(null);
        panelBorder1.add(jDiaChi, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 410, 260, 30));

        jLabel15.setText("________________________________________");
        panelBorder1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(671, 425, -1, -1));

        jLabel16.setFont(new java.awt.Font("sansserif", 0, 15)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("Người cấp CMND/CCCD");
        panelBorder1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 235, 180, -1));

        jNguoiCap.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jNguoiCap.setBorder(null);
        panelBorder1.add(jNguoiCap, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 260, 260, 30));

        jLabel17.setText("________________________________________");
        panelBorder1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(321, 275, -1, -1));

        jLabel18.setFont(new java.awt.Font("sansserif", 0, 15)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 51, 51));
        jLabel18.setText("Địa chỉ");
        panelBorder1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 390, 150, -1));

        jLabel19.setFont(new java.awt.Font("sansserif", 0, 15)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 51, 51));
        jLabel19.setText("Số điện thoại");
        panelBorder1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 315, 180, -1));

        jEmail.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jEmail.setBorder(null);
        panelBorder1.add(jEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 340, 260, 30));

        jLabel23.setText("________________________________________");
        panelBorder1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(671, 355, -1, -1));

        jLabel20.setText("________________________________________");
        panelBorder1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(671, 275, -1, -1));

        jPhone.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jPhone.setBorder(null);
        panelBorder1.add(jPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 340, 260, 30));

        jLabel21.setText("________________________________________");
        panelBorder1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(321, 355, -1, -1));

        jLabel22.setFont(new java.awt.Font("sansserif", 0, 15)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(51, 51, 51));
        jLabel22.setText("Số báo danh");
        panelBorder1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 235, 90, -1));

        jLabel24.setFont(new java.awt.Font("sansserif", 0, 15)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(51, 51, 51));
        jLabel24.setText("Giới tính");
        panelBorder1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 470, 170, -1));

        jNgayCap.setBorder(null);
        jNgayCap.setDateFormatString("yyyy-MM-dd");
        panelBorder1.add(jNgayCap, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 180, 260, 32));

        jNgaySinh.setBorder(null);
        jNgaySinh.setDateFormatString("yyyy-MM-dd");
        panelBorder1.add(jNgaySinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 500, 260, 32));

        jNu.setBackground(new java.awt.Color(255, 255, 255));
        jNu.setText("Nữ");
        jNu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNuActionPerformed(evt);
            }
        });
        panelBorder1.add(jNu, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 500, -1, -1));

        jNam.setBackground(new java.awt.Color(255, 255, 255));
        jNam.setText("Nam");
        jNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNamActionPerformed(evt);
            }
        });
        panelBorder1.add(jNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 500, -1, -1));

        jAvatar.setText("                      Ảnh 3x4");
        jAvatar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelBorder1.add(jAvatar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 180, 235));

        jButton1.setBackground(new java.awt.Color(66, 133, 244));
        jButton1.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Cập nhật");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        panelBorder1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 560, 240, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jUploadActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn ảnh");
        fileChooser.setPreferredSize(new java.awt.Dimension(700, 500));
        FileNameExtensionFilter imageFilter
                = new FileNameExtensionFilter("Image files (*.png, *.jpg, *.jpeg)", "jpg", "jpeg", "png");
        fileChooser.setFileFilter(imageFilter);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String extension = ResourceUtil.getFileExtension(selectedFile);
            if (extension == null || !ResourceUtil.isImageExtension(extension)) {
                JOptionPane.showMessageDialog(this, "File không hợp lệ! Vui lòng chọn file ảnh");
                return;
            }
            currentAvatar = fileChooser.getSelectedFile();
            jAvatar.setIcon(new ImageIcon(selectedFile.getAbsolutePath()));
        }
    }//GEN-LAST:event_jUploadActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (!validateInput()) {
            return;
        }
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(CurrentUserLogin.user.getStudentId());
        studentDTO.setLastName(jHoDem.getText());
        studentDTO.setFirstName(jTen.getText());
        studentDTO.setCitizenIdentityNumber(jSoCMND.getText());
        LocalDate ngayCap = DateUtils.asLocalDate(jNgayCap.getDate());
        studentDTO.setCitizenIdentityIssueDate(ngayCap);
        studentDTO.setCitizenIdentityIssuedBy(jNguoiCap.getText());
        studentDTO.setEmail(jEmail.getText());
        studentDTO.setPhoneNumber(jPhone.getText());
        if (ObjectUtils.isNotEmpty(jSBD.getText())) {
            studentDTO.setOrderNumber(jSBD.getText());
        }
        if (ObjectUtils.isNotEmpty(jDanToc.getText())) {
            studentDTO.setEthnic(jDanToc.getText());
        }
        if (ObjectUtils.isNotEmpty(jDiaChi.getText())) {
            studentDTO.setAddress(jDiaChi.getText());
        }
        if (ObjectUtils.isNotEmpty(jNgaySinh.getDate())) {
            LocalDate ngaySinh = DateUtils.asLocalDate(jNgaySinh.getDate());
            studentDTO.setBirthDay(ngaySinh);
        }
        if (jNam.isSelected()) {
            studentDTO.setGender("Nam");
        } else if (jNu.isSelected()) {
            studentDTO.setGender("Nữ");
        }
        if (currentAvatar != null) {
            String fileName = CurrentUserLogin.user.getUsername();
            studentDTO.setAvatar(ResourceUtil.saveFile(fileName, "upload/avatar", currentAvatar));
        }
        try {
            CommonResponse commonResponse = studentController.updateStudent(studentDTO);
            if (commonResponse.getStatus().equals(Boolean.TRUE)) {
                new ConfirmDialog(null, "Cập nhật thành công", commonResponse.getMessage());
            } else {
                JOptionPane.showMessageDialog(null, commonResponse.getMessage());
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
            return;
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNamActionPerformed
        jNu.setSelected(false);
    }//GEN-LAST:event_jNamActionPerformed

    private void jNuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNuActionPerformed
        jNam.setSelected(false);
    }//GEN-LAST:event_jNuActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jAvatar;
    private javax.swing.JButton jButton1;
    private javax.swing.JTextField jDanToc;
    private javax.swing.JTextField jDiaChi;
    private javax.swing.JTextField jEmail;
    private javax.swing.JTextField jHoDem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton jNam;
    private com.toedter.calendar.JDateChooser jNgayCap;
    private com.toedter.calendar.JDateChooser jNgaySinh;
    private javax.swing.JTextField jNguoiCap;
    private javax.swing.JRadioButton jNu;
    private javax.swing.JTextField jPhone;
    private javax.swing.JTextField jSBD;
    private javax.swing.JTextField jSoCMND;
    private javax.swing.JTextField jTen;
    private javax.swing.JButton jUpload;
    private com.admission.components.border.PanelBorder panelBorder1;
    // End of variables declaration//GEN-END:variables
}
