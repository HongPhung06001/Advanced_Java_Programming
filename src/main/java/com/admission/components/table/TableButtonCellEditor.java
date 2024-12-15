package com.admission.components.table;

import com.admission.event.EventButtonTableClick;

import javax.swing.*;
import java.awt.*;

public class TableButtonCellEditor extends DefaultCellEditor {

    private String text;

    private EventButtonTableClick event;

    public TableButtonCellEditor(EventButtonTableClick event, String text) {
        super(new JCheckBox());
        this.event = event;
        this.text = text;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        Component com = super.getTableCellEditorComponent(table, value, isSelected, row, column);
        PanelButton panelRegister = new PanelButton();
        panelRegister.initEvent(event, row);
        panelRegister.getJButton().setText(text);
        panelRegister.setBackground(table.getSelectionBackground());
        panelRegister.getJButton().setBackground(table.getSelectionBackground());
        panelRegister.getJButton().setForeground(table.getSelectionForeground());
        return panelRegister;
    }
}
