package com.admission.view;

import com.admission.components.dialog.ConfirmDialog;
import com.admission.constants.CommonConstant;
import com.admission.controller.AuthController;
import com.admission.dto.SignUpRequest;
import com.admission.utils.DateUtils;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import org.apache.commons.lang3.ObjectUtils;

public class SignUp extends javax.swing.JFrame {

    private final AuthController authController;

    public SignUp() {
        initComponents();
        authController = new AuthController();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jlastName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jfirstName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jcmndNumber = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jpassword = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jcmndIssuedBy = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jcmndIssueDate = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jemail = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jphone = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jlogin = new javax.swing.JLabel();
        jButtonSignup = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo-haui.png"))); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(59, 21, -1, -1));

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jLabel1.setText("Họ Đệm");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, 231, -1));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("_________________________________________");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, -1, 20));

        jlastName.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        jlastName.setBorder(null);
        jlastName.setOpaque(true);
        jPanel1.add(jlastName, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 172, 262, 45));

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jLabel2.setText("Tên");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 150, 231, -1));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("_________________________________________");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 200, -1, 20));

        jfirstName.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        jfirstName.setBorder(null);
        jfirstName.setOpaque(true);
        jPanel1.add(jfirstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 172, 262, 45));

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jLabel6.setText("Số CMND/CCCD");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, 231, -1));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("_________________________________________");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 290, -1, 20));

        jcmndNumber.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        jcmndNumber.setBorder(null);
        jcmndNumber.setOpaque(true);
        jPanel1.add(jcmndNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, 262, 45));

        jLabel8.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jLabel8.setText("Mật khẩu");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 240, 231, -1));

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("_________________________________________");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 290, -1, 20));

        jpassword.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        jpassword.setBorder(null);
        jpassword.setOpaque(true);
        jPanel1.add(jpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 260, 262, 45));

        jLabel10.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jLabel10.setText("Người cấp CMND/CCCD");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 330, 231, -1));

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("_________________________________________");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 380, -1, 20));

        jcmndIssuedBy.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        jcmndIssuedBy.setBorder(null);
        jcmndIssuedBy.setOpaque(true);
        jPanel1.add(jcmndIssuedBy, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 350, 262, 45));

        jLabel12.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jLabel12.setText("Ngày cấp CMND/CCCD");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 330, 231, -1));

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("_________________________________________");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 380, -1, 20));

        jcmndIssueDate.setBackground(new java.awt.Color(255, 255, 255));
        jcmndIssueDate.setDateFormatString("yyyy-MM-dd");
        jPanel1.add(jcmndIssueDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 350, 260, 45));

        jLabel14.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jLabel14.setText("Email");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, 231, -1));

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("_________________________________________");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 470, -1, 20));

        jemail.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        jemail.setBorder(null);
        jemail.setOpaque(true);
        jPanel1.add(jemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 440, 262, 45));

        jLabel16.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jLabel16.setText("Số điện thoại");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 420, 231, -1));

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("_________________________________________");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 470, -1, 20));

        jphone.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        jphone.setBorder(null);
        jphone.setOpaque(true);
        jPanel1.add(jphone, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 440, 262, 45));

        jLabel18.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 0, 0));
        jLabel18.setText("Bạn đã có tài khoản?");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 510, 130, -1));

        jlogin.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jlogin.setForeground(new java.awt.Color(66, 133, 244));
        jlogin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlogin.setText("Đăng nhập");
        jlogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jloginMouseClicked(evt);
            }
        });
        jPanel1.add(jlogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 510, -1, -1));

        jButtonSignup.setBackground(new java.awt.Color(66, 133, 244));
        jButtonSignup.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jButtonSignup.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSignup.setText("Đăng ký");
        jButtonSignup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSignupActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonSignup, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 540, 300, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jloginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jloginMouseClicked
        this.dispose();
        new Login().setVisible(true);
    }//GEN-LAST:event_jloginMouseClicked

    private void jButtonSignupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSignupActionPerformed
        String lastName = jlastName.getText();
        String firstName = jfirstName.getText();
        String cmnd = jcmndNumber.getText();
        String password = jpassword.getText();
        String cmndBy = jcmndIssuedBy.getText();
        String email = jemail.getText();
        String phoneNumber = jphone.getText();
        if (ObjectUtils.isEmpty(lastName)) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập họ đệm!");
            return;
        }
        if (ObjectUtils.isEmpty(firstName)) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập tên!");
            return;
        }
        if (ObjectUtils.isEmpty(cmnd)) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập số CMND/CCCD!");
            return;
        }
        if (ObjectUtils.isEmpty(password)) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập mật khẩu!");
            return;
        }
        Pattern patternPass = Pattern.compile(CommonConstant.REGEX_PASSWORD);
        Matcher matcherPass = patternPass.matcher(password);
        if(!matcherPass.matches()) {
            JOptionPane.showMessageDialog(null, "Mật khẩu không hợp lệ! Mật khẩu phải bao gồm:"
                    + "\nTối thiểu 8 ký tự \nCó ít nhất một chữ số "
                    + "\nCó ít nhất một chữ cái viết thường "
                    + "\nKhông được chứa khoảng trắng và ký tự trống");
            return;
        }
        if (ObjectUtils.isEmpty(jcmndIssueDate.getDate())) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn ngày cấp CMND/CCCD!");
            return;
        }
        if (ObjectUtils.isEmpty(cmndBy)) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập người cấp CMND/CCCD!");
            return;
        }
        if (ObjectUtils.isEmpty(email)) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập email!");
            return;
        }
        Pattern patternEmail = Pattern.compile(CommonConstant.REGEX_EMAIL);
        Matcher matcherEmail = patternEmail.matcher(email);
        if(!matcherEmail.matches()) {
            JOptionPane.showMessageDialog(null, "Địa chỉ email không hợp lệ!");
            return;
        }
        if (ObjectUtils.isEmpty(phoneNumber)) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập số điện thoại!");
            return;
        }
        Pattern patternPhone = Pattern.compile(CommonConstant.REGEX_PHONE);
        Matcher matcherPhone = patternPhone.matcher(phoneNumber);
        if(!matcherPhone.matches()) {
            JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ!");
            return;
        }
        LocalDate cmndDate = DateUtils.asLocalDate(jcmndIssueDate.getDate());
        SignUpRequest signUpRequest = new SignUpRequest(cmnd, cmndDate, cmndBy, password,
                firstName, lastName, email, phoneNumber);
        String response = authController.signup(signUpRequest);
        if (response.equals(CommonConstant.SUCCESS)) {
            ConfirmDialog confirmDialog = new ConfirmDialog(null, "Đăng ký thành công", "Đăng ký tài khoản thành công");
            if (confirmDialog.isOkClicked()) {
                this.dispose();
                new Login().setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, response);
            return;
        }
    }//GEN-LAST:event_jButtonSignupActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SignUp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonSignup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private com.toedter.calendar.JDateChooser jcmndIssueDate;
    private javax.swing.JTextField jcmndIssuedBy;
    private javax.swing.JTextField jcmndNumber;
    private javax.swing.JTextField jemail;
    private javax.swing.JTextField jfirstName;
    private javax.swing.JTextField jlastName;
    private javax.swing.JLabel jlogin;
    private javax.swing.JTextField jpassword;
    private javax.swing.JTextField jphone;
    // End of variables declaration//GEN-END:variables
}
