package com.admission.view;

import com.admission.constants.RoleConstant;
import com.admission.controller.AuthController;
import com.admission.dto.LoginResponse;
import com.admission.utils.CurrentUserLogin;
import com.admission.view.admin.AdminLayout;
import com.admission.view.user.UserLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import org.apache.commons.lang3.ObjectUtils;

public class Login extends javax.swing.JFrame {

    private final AuthController authController;

    public Login() {
        initComponents();
        this.authController = new AuthController();
        JRootPane rootPane = SwingUtilities.getRootPane(buttonLogin);
        rootPane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonLogin.doClick();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        buttonLogin = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        hidden = new javax.swing.JLabel();
        show = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        fogotPass = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        signup = new javax.swing.JLabel();
        jusername = new javax.swing.JTextField();
        jpassword = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        buttonLogin.setBackground(new java.awt.Color(66, 133, 244));
        buttonLogin.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        buttonLogin.setForeground(new java.awt.Color(255, 255, 255));
        buttonLogin.setText("Đăng nhập");
        buttonLogin.setBorder(null);
        buttonLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        buttonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLoginActionPerformed(evt);
            }
        });
        jPanel2.add(buttonLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 420, 300, 47));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo-haui.png"))); // NOI18N
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(59, 21, -1, -1));

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jLabel1.setText("Tên đăng nhập/CMND/CCCD");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 170, 231, -1));

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jLabel2.setText("Mật khẩu");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 270, 175, -1));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("_________________________________________");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 223, -1, 20));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/lock.png"))); // NOI18N
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 300, -1, 32));

        hidden.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/hidden.png"))); // NOI18N
        hidden.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hiddenMouseClicked(evt);
            }
        });
        jPanel2.add(hidden, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 300, 30, 32));

        show.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/eye.png"))); // NOI18N
        show.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showMouseClicked(evt);
            }
        });
        jPanel2.add(show, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 300, 30, 32));

        jLabel7.setText("_________________________________________");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 326, -1, -1));

        fogotPass.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        fogotPass.setForeground(new java.awt.Color(66, 133, 244));
        fogotPass.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        fogotPass.setText("Quên mật khẩu");
        jPanel2.add(fogotPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 360, -1, -1));

        jLabel8.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 0));
        jLabel8.setText("Bạn quên mật khẩu?");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 360, 117, -1));

        jLabel9.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 0, 0));
        jLabel9.setText("Bạn chưa có tài khoản?");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 390, -1, -1));

        signup.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        signup.setForeground(new java.awt.Color(66, 133, 244));
        signup.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        signup.setText("Đăng ký");
        signup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signupMouseClicked(evt);
            }
        });
        jPanel2.add(signup, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 390, -1, -1));

        jusername.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        jusername.setBorder(null);
        jusername.setOpaque(true);
        jPanel2.add(jusername, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, 260, 50));

        jpassword.setBorder(null);
        jpassword.setOpaque(true);
        jPanel2.add(jpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 290, 260, 50));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/user-solid-24.png"))); // NOI18N
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 200, 30, 32));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(675, 530));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLoginActionPerformed
        String username = jusername.getText();
        String password = String.valueOf(jpassword.getPassword());
        if (ObjectUtils.isEmpty(username)) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập tên đăng nhập!");
            return;
        }
        if (ObjectUtils.isEmpty(password)) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập mật khẩu!");
            return;
        }
        LoginResponse loginResponse = authController.login(username, password);
        if (loginResponse.getStatus().equals(Boolean.TRUE)) {
            this.dispose();
            CurrentUserLogin.user = loginResponse.getUser();
            if (loginResponse.getUser().getRoleName().equals(RoleConstant.ROLE_ADMIN)) {
                AdminLayout adminLayout = new AdminLayout();
                adminLayout.setVisible(true);
            } else {
                UserLayout userLayout = new UserLayout();
                userLayout.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, loginResponse.getMessage());
            return;
        }
    }//GEN-LAST:event_buttonLoginActionPerformed

    private void showMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showMouseClicked
        jpassword.setEchoChar((char) 8226);
        hidden.setVisible(true);
        hidden.setEnabled(true);
        show.setEnabled(false);
        show.setEnabled(false);
    }//GEN-LAST:event_showMouseClicked

    private void hiddenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hiddenMouseClicked
        jpassword.setEchoChar((char) 0);
        hidden.setVisible(false);
        hidden.setEnabled(false);
        show.setEnabled(true);
        show.setEnabled(true);
    }//GEN-LAST:event_hiddenMouseClicked

    private void signupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signupMouseClicked
        this.dispose();
        new SignUp().setVisible(true);
    }//GEN-LAST:event_signupMouseClicked

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonLogin;
    private javax.swing.JLabel fogotPass;
    private javax.swing.JLabel hidden;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jpassword;
    private javax.swing.JTextField jusername;
    private javax.swing.JLabel show;
    private javax.swing.JLabel signup;
    // End of variables declaration//GEN-END:variables
}
