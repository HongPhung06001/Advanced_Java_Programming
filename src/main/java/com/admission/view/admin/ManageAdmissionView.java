package com.admission.view.admin;

import com.admission.components.dialog.ConfirmDialog;
import com.admission.components.table.TableHeader;
import com.admission.controller.AdmissionController;
import com.admission.controller.MajorController;
import com.admission.controller.MajorDetailController;
import com.admission.dto.AdmissionResultDTO;
import com.admission.dto.AdmissionResultRequest;
import com.admission.dto.MajorDTO;
import com.admission.utils.ExcelAdmissionUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ItemEvent;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManageAdmissionView extends JPanel {

    private final AdmissionController admissionController = new AdmissionController();

    private final MajorController majorController = new MajorController();

    private final MajorDetailController majorDetailController = new MajorDetailController();

    private final Map<String, String> majorsMap = new HashMap<>();

    public ManageAdmissionView() {
        initComponents();
        loadFilterComboBox();
        spTable.getVerticalScrollBar().setUnitIncrement(9);
        spTable.getHorizontalScrollBar().setUnitIncrement(9);
        spTable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setTableHeader(TableHeader.customTableHeader(table.getTableHeader()));
        loadTable(jSearch.getKeyword().getText());
        jSearch.addEventButtonSearchClick((String text) -> {
            loadTable(text);
        });
        jFilterYear.addItemListener((ItemEvent event) -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                loadTable(jSearch.getKeyword().getText());
            }
        });
        jFilterNganh.addItemListener((ItemEvent event) -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                loadTable(jSearch.getKeyword().getText());
            }
        });
        jFilterStatus.addItemListener((ItemEvent event) -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                loadTable(jSearch.getKeyword().getText());
            }
        });
    }

    private void loadTable(String keyword) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        Integer year = Integer.valueOf(jFilterYear.getSelectedItem().toString());
        Integer status;
        switch (jFilterStatus.getSelectedItem().toString()) {
            case "Đậu":
                status = 2;
                break;
            case "Trượt":
                status = 3;
                break;
            default:
                status = null;
                break;
        }
        String tenNganh = jFilterNganh.getSelectedItem().toString();
        String code = majorsMap.get(tenNganh);
        AdmissionResultRequest request = new AdmissionResultRequest(year, keyword, code, status);
        List<AdmissionResultDTO> admissionResult = admissionController.getAdmissionResult(request);
        int numberRowsOfTable = admissionResult.size();
        model.setRowCount(numberRowsOfTable);
        for (int i = 0; i < numberRowsOfTable; i++) {
            model.setValueAt(i + 1, i, 0);
            model.setValueAt(admissionResult.get(i).getLastName(), i, 1);
            model.setValueAt(admissionResult.get(i).getFirstName(), i, 2);
            model.setValueAt(admissionResult.get(i).getOrderNumber(), i, 3);
            model.setValueAt(admissionResult.get(i).getCitizenIdentityNumber(), i, 4);
            model.setValueAt(admissionResult.get(i).getEmail(), i, 5);
            model.setValueAt(admissionResult.get(i).getPhoneNumber(), i, 6);
            model.setValueAt(admissionResult.get(i).getGender(), i, 7);
            model.setValueAt(admissionResult.get(i).getAddress(), i, 8);
            model.setValueAt(admissionResult.get(i).getOrders(), i, 9);
            model.setValueAt(admissionResult.get(i).getBlock(), i, 10);
            model.setValueAt(admissionResult.get(i).getTotalScore(), i, 11);
        }
    }

    private void loadFilterComboBox() {
        try {
            Integer minYear = majorDetailController.getYearMinMajor();
            Integer maxYear = LocalDate.now().getYear();
            for (int i = maxYear; i >= minYear; i--) {
                jFilterYear.addItem(String.valueOf(i));
            }
            DefaultComboBoxModel<String> modelBlock = (DefaultComboBoxModel<String>) jFilterNganh.getModel();
            modelBlock.removeAllElements();
            List<MajorDTO> majorDTOs = majorController.getMajors();
            majorDTOs.forEach(majorDTO -> {
                jFilterNganh.addItem(majorDTO.getName());
                majorsMap.put(majorDTO.getName(), majorDTO.getCode());
            });
        } catch (Exception ex) {
            Logger.getLogger(ManageAdmissionView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorder1 = new com.admission.components.border.PanelBorder();
        jPanel1 = new JPanel();
        jSearch = new com.admission.components.search.Search();
        jFilterYear = new JComboBox<>();
        jFilterNganh = new JComboBox<>();
        jFilterStatus = new JComboBox<>();
        jExportFile = new JButton();
        jExportFileAll = new JButton();
        jLabel1 = new JLabel();
        spTable = new JScrollPane();
        table = new JTable();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jFilterStatus.setModel(new DefaultComboBoxModel<>(new String[] { "Tất cả", "Đậu", "Trượt" }));

        jExportFile.setText("Export file");
        jExportFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jExportFileActionPerformed(evt);
            }
        });

        jExportFileAll.setText("Export file all");
        jExportFileAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jExportFileAllActionPerformed(evt);
            }
        });

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jSearch, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jFilterYear, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFilterNganh, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jFilterStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jExportFile)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jExportFileAll)
                .addGap(45, 45, 45))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jSearch, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(jExportFileAll, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jExportFile, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFilterStatus, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFilterNganh, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jFilterYear, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)))
                .addGap(1, 1, 1))
        );

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 22)); // NOI18N
        jLabel1.setText("Kết quả tuyển sinh");

        spTable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        table.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        table.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Họ đệm", "Tên", "SBD", "Số CMND/CCCD", "Email", "SĐT", "Giới tính", "Địa chỉ", "Nguyện vọng", "Khối", "Tổng điểm"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table.setRowHeight(40);
        spTable.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(40);
            table.getColumnModel().getColumn(1).setPreferredWidth(100);
            table.getColumnModel().getColumn(2).setPreferredWidth(80);
            table.getColumnModel().getColumn(3).setPreferredWidth(70);
            table.getColumnModel().getColumn(4).setPreferredWidth(130);
            table.getColumnModel().getColumn(5).setPreferredWidth(130);
            table.getColumnModel().getColumn(6).setPreferredWidth(80);
            table.getColumnModel().getColumn(7).setPreferredWidth(70);
            table.getColumnModel().getColumn(8).setPreferredWidth(160);
            table.getColumnModel().getColumn(9).setPreferredWidth(100);
            table.getColumnModel().getColumn(10).setPreferredWidth(40);
            table.getColumnModel().getColumn(11).setPreferredWidth(100);
        }

        GroupLayout panelBorder1Layout = new GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBorder1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, panelBorder1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addGroup(panelBorder1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(spTable)
                            .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 890, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spTable, GroupLayout.PREFERRED_SIZE, 464, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelBorder1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 5, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jExportFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jExportFileActionPerformed
        Integer year = Integer.valueOf(jFilterYear.getSelectedItem().toString());
        Integer status;
        switch (jFilterStatus.getSelectedItem().toString()) {
            case "Đậu":
                status = 2;
                break;
            case "Trượt":
                status = 3;
                break;
            default:
                status = null;
                break;
        }
        String tenNganh = jFilterNganh.getSelectedItem().toString();
        String code = majorsMap.get(tenNganh);
        AdmissionResultRequest request = new AdmissionResultRequest(year, "", code, status);
        String pathFile = String.format("D:/ket-qua-tuyen-sinh-nam-%s-nganh-%s.xlsx" , year, code);
        ExcelAdmissionUtil.exportFile(request, pathFile);
        new ConfirmDialog(null, "Lưu thành công", "File đã được lưu vào " + pathFile);
    }//GEN-LAST:event_jExportFileActionPerformed

    private void jExportFileAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jExportFileAllActionPerformed
        Integer year = Integer.valueOf(jFilterYear.getSelectedItem().toString());
        Integer status;
        switch (jFilterStatus.getSelectedItem().toString()) {
            case "Đậu":
                status = 2;
                break;
            case "Trượt":
                status = 3;
                break;
            default:
                status = null;
                break;
        }
        AdmissionResultRequest request = new AdmissionResultRequest(year, "", null, status);
        String pathFile = String.format("D:/ket-qua-tuyen-sinh-nam-%s.xlsx" , year);
        ExcelAdmissionUtil.exportFileAll(request, pathFile);
        new ConfirmDialog(null, "Lưu thành công", "File đã được lưu vào " + pathFile);
    }//GEN-LAST:event_jExportFileAllActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton jExportFile;
    private JButton jExportFileAll;
    private JComboBox<String> jFilterNganh;
    private JComboBox<String> jFilterStatus;
    private JComboBox<String> jFilterYear;
    private JLabel jLabel1;
    private JPanel jPanel1;
    private com.admission.components.search.Search jSearch;
    private com.admission.components.border.PanelBorder panelBorder1;
    private JScrollPane spTable;
    private JTable table;
    // End of variables declaration//GEN-END:variables
}
