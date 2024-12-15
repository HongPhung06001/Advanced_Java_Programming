package com.admission.components.dialog;

import com.admission.utils.ResourceUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ConfirmDialog extends JDialog {

    private boolean okClicked;

    public ConfirmDialog(Frame owner, String title, String message) {
        super(owner, title, true);
        // create components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelIcon = new JLabel(ResourceUtil.getImageIcon("icon/check.png"));
        labelIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(labelIcon);

        JLabel labelMess = new JLabel(message);
        labelMess.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        labelMess.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(10));
        panel.add(labelMess);

        JButton okButton = new JButton("Ok");
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        okButton.addActionListener((ActionEvent e) -> {
            okClicked = true;
            dispose();
        });
        panel.add(Box.createVerticalStrut(10));
        panel.add(okButton);
        // add components to dialog
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public boolean isOkClicked() {
        return okClicked;
    }
}
