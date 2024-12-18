package com.admission.view.admin;

import com.admission.event.EventMenuSelected;
import com.admission.view.Login;

import javax.swing.*;

public class AdminLayout extends javax.swing.JFrame {

    public AdminLayout() {
        setTitle("Admission System");
        setPreferredSize(new java.awt.Dimension(1300, 700));
        initComponents();

        jScrollPane1.getVerticalScrollBar().setUnitIncrement(12);

        menuAdmin.addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) {
                switch (index) {
                    case 0:
                        setForm(new ManageStudentView());
                        break;
                    case 1:
                        setForm(new ManageMajorView());
                        break;
                    case 2:
                        setForm(new ManageMajorDetailView());
                        break;
                    case 3:
                        setForm(new ManageBlockView());
                        break;
                    case 4:
                        setForm(new ManageSubjectView());
                        break;
                    case 5:
                        setForm(new ManageAdmissionView());
                        break;
                    case 6:
                        setForm(new StatisticView());
                        break;
                    case 7:
                        setForm(new SettingView());
                        break;
                    case 8:
                        setVisible(false);
                        new Login().setVisible(true);
                        break;
                    default:
                        break;
                }
            }
        });
        //  set when system open start with home form
        setForm(new ManageStudentView());
    }

    private void setForm(JComponent com) {
        mainPanel.removeAll();
        mainPanel.add(com);
        mainPanel.repaint();
        mainPanel.revalidate();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuAdmin = new com.admission.components.menu.MenuAdmin();
        jScrollPane1 = new javax.swing.JScrollPane();
        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setBorder(null);

        mainPanel.setOpaque(false);
        mainPanel.setLayout(new java.awt.BorderLayout());
        jScrollPane1.setViewportView(mainPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(menuAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 748, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1)
                .addGap(10, 10, 10))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminLayout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminLayout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminLayout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminLayout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminLayout().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    private com.admission.components.menu.MenuAdmin menuAdmin;
    // End of variables declaration//GEN-END:variables

}
