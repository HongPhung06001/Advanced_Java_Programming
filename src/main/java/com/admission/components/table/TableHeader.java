package com.admission.components.table;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class TableHeader {

    public static JTableHeader customTableHeader(JTableHeader header) {
        header.setFont(new Font("sansserif", 1, 13));
        header.setForeground(new Color(102, 102, 102));
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        return header;
    }

}
