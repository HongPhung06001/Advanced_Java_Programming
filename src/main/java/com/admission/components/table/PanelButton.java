package com.admission.components.table;

import com.admission.event.EventButtonTableClick;
import lombok.Getter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Getter
public class PanelButton extends javax.swing.JPanel {

    public PanelButton() {
        initComponents();
    }

    public void initEvent(EventButtonTableClick event, int row) {
        JButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                event.onClick(row);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JButton = new javax.swing.JButton();

        JButton.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        JButton.setText("Button");
        JButton.setBorder(null);
        JButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JButton, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JButton, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JButton;
    // End of variables declaration//GEN-END:variables
}
