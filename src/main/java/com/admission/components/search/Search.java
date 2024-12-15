package com.admission.components.search;

import com.admission.event.EventButtonSearchClick;
import lombok.Getter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Getter
public class Search extends javax.swing.JPanel {

    public Search() {
        initComponents();
    }

    public void addEventButtonSearchClick(EventButtonSearchClick event) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                event.onClick(keyword.getText());
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        button = new javax.swing.JButton();
        keyword = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(515, 35));

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(127, 127, 127));
        jLabel2.setText("Tìm kiếm:");

        button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/search.png"))); // NOI18N
        button.setBorder(null);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });

        keyword.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        keyword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                keywordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(5, 5, 5)
                .addComponent(keyword, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(button, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(keyword, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)))
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_buttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonActionPerformed

    private void keywordActionPerformed(ActionEvent evt) {//GEN-FIRST:event_keywordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_keywordActionPerformed

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 0, 0);
        g2.fillRect(0, 0, 25, getHeight());
        g2.fillRect(getWidth() - 25, getHeight() - 25, getWidth(), getHeight());
        super.paintComponent(grphcs);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField keyword;
    // End of variables declaration//GEN-END:variables
}
