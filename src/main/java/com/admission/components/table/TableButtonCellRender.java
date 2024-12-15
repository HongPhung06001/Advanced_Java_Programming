package com.admission.components.table;

import lombok.AllArgsConstructor;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

@AllArgsConstructor
public class TableButtonCellRender extends DefaultTableCellRenderer {

    private String text;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        PanelButton panelRegister = new PanelButton();
        panelRegister.getJButton().setForeground(com.getForeground());
        panelRegister.getJButton().setText(text);
        if (!isSelected && row % 2 == 0) {
            panelRegister.setBackground(Color.WHITE);
            panelRegister.getJButton().setBackground(Color.WHITE);
        } else {
            panelRegister.getJButton().setBackground(com.getBackground());
            panelRegister.setBackground(com.getBackground());
        }
        return panelRegister;
    }

}
