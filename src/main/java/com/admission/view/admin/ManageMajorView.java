package com.admission.view.admin;

import com.admission.components.dialog.ConfirmDialog;
import com.admission.components.table.TableHeader;
import com.admission.controller.BlockController;
import com.admission.controller.MajorController;
import com.admission.dto.CommonResponse;
import com.admission.dto.MajorDTO;
import com.admission.event.EventButtonSearchClick;
import com.admission.model.Block;
import org.apache.commons.lang3.ObjectUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ManageMajorView extends JPanel {

    private final MajorController majorController;

    private final BlockController blockController;

    public ManageMajorView() {
        initComponents();
        this.majorController = new MajorController();
        this.blockController = new BlockController();

        tableMajor.setTableHeader(TableHeader.customTableHeader(tableMajor.getTableHeader()));

        loadMajors(jSearch.getKeyword().getText());
        handleSelectCellTable();

        jSearch.addEventButtonSearchClick(new EventButtonSearchClick() {
            @Override
            public void onClick(String text) {
                loadMajors(text);
            }
        });

    }

    private void loadMajors(String keyword) {
        DefaultTableModel model = (DefaultTableModel) tableMajor.getModel();
        List<MajorDTO> majorDTOs = majorController.getMajorsForAdmin(keyword);
        int numberRowsOfTable = majorDTOs.size();
        model.setRowCount(numberRowsOfTable);
        for (int i = 0; i < numberRowsOfTable; i++) {
            model.setValueAt(majorDTOs.get(i).getId(), i, 0);
            model.setValueAt(majorDTOs.get(i).getCode(), i, 1);
            model.setValueAt(majorDTOs.get(i).getName(), i, 2);
            model.setValueAt(majorDTOs.get(i).getBlocks(), i, 3);
        }
    }

    private void handleSelectCellTable() {
        ListSelectionModel cellSelectionModel = tableMajor.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = tableMajor.getSelectedRow();
                if (selectedRow != -1) {
                    Integer studentId = Integer.valueOf(tableMajor.getValueAt(selectedRow, 0).toString());
                    jId.setText(studentId.toString());
                    jMaNganh.setText(tableMajor.getValueAt(selectedRow, 1).toString());
                    jTenNganh.setText(tableMajor.getValueAt(selectedRow, 2).toString());
                    jKhoi.setText(tableMajor.getValueAt(selectedRow, 3).toString());
                }
            }
        });
    }

    private MajorDTO createMajorDTO() {
        MajorDTO majorDTO = new MajorDTO();
        majorDTO.setCode(jMaNganh.getText());
        majorDTO.setName(jTenNganh.getText());
        majorDTO.setBlocks(jKhoi.getText());
        return majorDTO;
    }

    private boolean validateInput() {
        if (ObjectUtils.isEmpty(jMaNganh.getText())) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập mã chuyên ngành!");
            return false;
        }
        if (ObjectUtils.isEmpty(jTenNganh.getText())) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập tên chuyên ngành!");
            return false;
        }
        if (ObjectUtils.isEmpty(jKhoi.getText())) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn khối của chuyên ngành!");
            return false;
        }
        return true;
    }

    private void clearInput() {
        jId.setText(null);
        jMaNganh.setText(null);
        jTenNganh.setText(null);
        jKhoi.setText(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorder1 = new com.admission.components.border.PanelBorder();
        jLabel2 = new javax.swing.JLabel();
        jId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jMaNganh = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTenNganh = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jKhoi = new javax.swing.JTextField();
        jPanel1 = new JPanel();
        jThem = new javax.swing.JButton();
        jSua = new javax.swing.JButton();
        jXoa = new javax.swing.JButton();
        jClear = new javax.swing.JButton();
        jSearch = new com.admission.components.search.Search();
        jLabel1 = new javax.swing.JLabel();
        spTable = new javax.swing.JScrollPane();
        tableMajor = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel2.setText("ID");

        jId.setFocusable(false);

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel3.setText("Mã ngành");

        jLabel4.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel4.setText("Tên ngành");

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel5.setText("Khối");

        jKhoi.setFocusable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jThem.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Add.png"))); // NOI18N
        jThem.setText("Thêm");
        jThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jThemActionPerformed(evt);
            }
        });

        jSua.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Edit.png"))); // NOI18N
        jSua.setText("Sửa");
        jSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSuaActionPerformed(evt);
            }
        });

        jXoa.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Delete.png"))); // NOI18N
        jXoa.setText("Xóa");
        jXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXoaActionPerformed(evt);
            }
        });

        jClear.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Refresh.png"))); // NOI18N
        jClear.setText("Làm mới");
        jClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(jThem, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89)
                        .addComponent(jSua, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(107, 107, 107)
                .addComponent(jXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jClear)
                .addGap(75, 75, 75))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jThem)
                    .addComponent(jSua)
                    .addComponent(jClear)
                    .addComponent(jXoa))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 22)); // NOI18N
        jLabel1.setText("Quản lý chuyên ngành");

        tableMajor.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        tableMajor.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Mã ngành", "Tên ngành", "Khối"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableMajor.setRowHeight(40);
        spTable.setViewportView(tableMajor);
        if (tableMajor.getColumnModel().getColumnCount() > 0) {
            tableMajor.getColumnModel().getColumn(0).setPreferredWidth(60);
            tableMajor.getColumnModel().getColumn(1).setPreferredWidth(100);
            tableMajor.getColumnModel().getColumn(2).setPreferredWidth(250);
        }

        jButton1.setText("Chọn khối");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(356, 356, 356)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(298, 298, 298))
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spTable)
                .addContainerGap())
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addGap(49, 49, 49)
                        .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jKhoi, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jId, javax.swing.GroupLayout.Alignment.LEADING))))
                .addGap(70, 70, 70)
                .addComponent(jLabel3)
                .addGap(20, 20, 20)
                .addComponent(jMaNganh)
                .addGap(70, 70, 70)
                .addComponent(jLabel4)
                .addGap(25, 25, 25)
                .addComponent(jTenNganh)
                .addGap(30, 30, 30))
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGap(20, 20, 20)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTenNganh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jKhoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spTable, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            JPanel al = new JPanel();
            for (Block block : blockController.getBlocks()) {
                JCheckBox box = new JCheckBox(block.getCode());
                al.add(box);
            }

            if (ObjectUtils.isNotEmpty(jKhoi.getText())) {
                List<String> checked = Arrays.stream(jKhoi.getText().split(",")).collect(Collectors.toList());
                Component[] components = al.getComponents();
                for (Component component : components) {
                    JCheckBox checkBox = (JCheckBox) component;
                    if (checked.contains(checkBox.getText())) {
                        checkBox.setSelected(true);
                    }
                }
            }
            int option = JOptionPane.showConfirmDialog(null, al, "Chọn khối cho chuyên ngành", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                List<String> selected = new LinkedList<>();
                Component[] components = al.getComponents();
                for (Component component : components) {
                    JCheckBox checkBox = (JCheckBox) component;
                    if (checkBox.isSelected()) {
                        selected.add(checkBox.getText());
                    }
                }
                jKhoi.setText(selected.stream().collect(Collectors.joining(",")));
            }
        } catch (Exception ex) {
            Logger.getLogger(ManageMajorView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jThemActionPerformed
        if (!validateInput()) {
            return;
        }
        MajorDTO majorDTO = createMajorDTO();
        try {
            CommonResponse commonResponse = majorController.createMajor(majorDTO);
            if (commonResponse.getStatus().equals(Boolean.TRUE)) {
                new ConfirmDialog(null, "Thêm thành công", commonResponse.getMessage());
                loadMajors(jSearch.getKeyword().getText());
            } else {
                JOptionPane.showMessageDialog(null, commonResponse.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
        }
    }//GEN-LAST:event_jThemActionPerformed

    private void jSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSuaActionPerformed
        if (!validateInput()) {
            return;
        }
        MajorDTO majorDTO = createMajorDTO();
        majorDTO.setId(Integer.valueOf(jId.getText()));
        try {
            CommonResponse commonResponse = majorController.updateMajor(majorDTO);
            if (commonResponse.getStatus().equals(Boolean.TRUE)) {
                new ConfirmDialog(null, "Cập nhật thành công", commonResponse.getMessage());
                loadMajors(jSearch.getKeyword().getText());
            } else {
                JOptionPane.showMessageDialog(null, commonResponse.getMessage());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
        }

    }//GEN-LAST:event_jSuaActionPerformed

    private void jXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXoaActionPerformed
        int option = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa chuyên ngành này?", 
                "Xác nhận xoá", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            try {
                CommonResponse commonResponse = majorController.deleteMajorById(Integer.valueOf(jId.getText()));
                if (commonResponse.getStatus().equals(Boolean.TRUE)) {
                    new ConfirmDialog(null, "Xóa thành công", commonResponse.getMessage());
                    clearInput();
                    loadMajors(jSearch.getKeyword().getText());
                } else {
                    JOptionPane.showMessageDialog(null, commonResponse.getMessage());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jXoaActionPerformed

    private void jClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jClearActionPerformed
        clearInput();
        jSearch.getKeyword().setText(null);
        loadMajors(jSearch.getKeyword().getText());
    }//GEN-LAST:event_jClearActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jClear;
    private javax.swing.JTextField jId;
    private javax.swing.JTextField jKhoi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField jMaNganh;
    private JPanel jPanel1;
    private com.admission.components.search.Search jSearch;
    private javax.swing.JButton jSua;
    private javax.swing.JTextField jTenNganh;
    private javax.swing.JButton jThem;
    private javax.swing.JButton jXoa;
    private com.admission.components.border.PanelBorder panelBorder1;
    private javax.swing.JScrollPane spTable;
    private javax.swing.JTable tableMajor;
    // End of variables declaration//GEN-END:variables
}
