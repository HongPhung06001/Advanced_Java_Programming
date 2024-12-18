package com.admission.view.admin;

import com.admission.components.dialog.ConfirmDialog;
import com.admission.components.table.TableHeader;
import com.admission.controller.BlockController;
import com.admission.controller.SubjectController;
import com.admission.dto.BlockDTO;
import com.admission.dto.CommonResponse;
import com.admission.event.EventButtonSearchClick;
import com.admission.model.Subject;
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

public class ManageBlockView extends JPanel {

    private final BlockController blockController;
    
    private final SubjectController subjectController;

    public ManageBlockView() {
        initComponents();
        this.blockController = new BlockController();
        this.subjectController = new SubjectController();

        tableBlock.setTableHeader(TableHeader.customTableHeader(tableBlock.getTableHeader()));
        
        loadBlocks(jSearch.getKeyword().getText());
        handleSelectCellTable();
        jSearch.addEventButtonSearchClick(new EventButtonSearchClick() {
            @Override
            public void onClick(String text) {
                loadBlocks(text);
            }
        });
    }

    private void loadBlocks(String keyword) {
        DefaultTableModel model = (DefaultTableModel) tableBlock.getModel();
        List<BlockDTO> blocks = blockController.getBlocks(keyword);
        int numberRowsOfTable = blocks.size();
        model.setRowCount(numberRowsOfTable);
        for (int i = 0; i < numberRowsOfTable; i++) {
            model.setValueAt(blocks.get(i).getId(), i, 0);
            model.setValueAt(blocks.get(i).getCode(), i, 1);
            model.setValueAt(blocks.get(i).getSubjects(), i, 2);
        }
    }
    
    private void handleSelectCellTable() {
        ListSelectionModel cellSelectionModel = tableBlock.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = tableBlock.getSelectedRow();
                if (selectedRow != -1) {
                    Integer studentId = Integer.valueOf(tableBlock.getValueAt(selectedRow, 0).toString());
                    jId.setText(studentId.toString());
                    jMaKhoi.setText(tableBlock.getValueAt(selectedRow, 1).toString());
                    if(ObjectUtils.isNotEmpty(tableBlock.getValueAt(selectedRow, 2))) {
                        jMonHoc.setText(tableBlock.getValueAt(selectedRow, 2).toString());
                    } else {
                        jMonHoc.setText(null);
                    }
                }
            }
        });
    }

    
    public boolean validateInput() {
        if (ObjectUtils.isEmpty(jMaKhoi.getText())) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập mã khối!");
            return false;
        }
        if (ObjectUtils.isEmpty(jMonHoc.getText())) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn môn học của khối!");
            return false;
        }
        return true;
    }
    
    private void clearInput() {
        jId.setText(null);
        jMonHoc.setText(null);
        jMonHoc.setText(null);
    }
    
    private BlockDTO createBlockDTO() {
        BlockDTO blockDTO = new BlockDTO();
        blockDTO.setCode(jMaKhoi.getText());
        blockDTO.setSubjects(jMonHoc.getText());
        return blockDTO;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorder1 = new com.admission.components.border.PanelBorder();
        jLabel2 = new javax.swing.JLabel();
        jId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jMonHoc = new javax.swing.JTextField();
        jPanel1 = new JPanel();
        jThem = new javax.swing.JButton();
        jSua = new javax.swing.JButton();
        jXoa = new javax.swing.JButton();
        jClear = new javax.swing.JButton();
        jSearch = new com.admission.components.search.Search();
        jLabel1 = new javax.swing.JLabel();
        spTable = new javax.swing.JScrollPane();
        tableBlock = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jMaKhoi = new javax.swing.JTextField();
        jChonMon = new javax.swing.JButton();

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel2.setText("ID");

        jId.setFocusable(false);

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel3.setText("Môn học");

        jMonHoc.setFocusable(false);

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
                .addGap(88, 88, 88)
                .addComponent(jThem, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76)
                .addComponent(jSua, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addComponent(jXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addComponent(jClear)
                .addGap(58, 58, 58))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jThem)
                    .addComponent(jSua)
                    .addComponent(jClear)
                    .addComponent(jXoa))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 22)); // NOI18N
        jLabel1.setText("Quản lý khối");

        tableBlock.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        tableBlock.setModel(new DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Mã khối", "Môn học"
            }
        ));
        tableBlock.setRowHeight(30);
        spTable.setViewportView(tableBlock);
        if (tableBlock.getColumnModel().getColumnCount() > 0) {
            tableBlock.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableBlock.getColumnModel().getColumn(1).setPreferredWidth(180);
        }

        jLabel4.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel4.setText("Mã khối");

        jChonMon.setText("Chọn môn");
        jChonMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChonMonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spTable)
                .addContainerGap())
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder1Layout.createSequentialGroup()
                .addGap(348, 348, 348)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(360, 360, 360))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder1Layout.createSequentialGroup()
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jChonMon, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel2)
                        .addGap(63, 63, 63)
                        .addComponent(jId)
                        .addGap(70, 70, 70)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jMaKhoi)
                        .addGap(70, 70, 70)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jMonHoc)))
                .addGap(64, 64, 64))
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jMonHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jMaKhoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jChonMon)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spTable, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                .addContainerGap())
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

    private void jThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jThemActionPerformed
       if (!validateInput()) {
            return;
        }
        BlockDTO blockDTO = createBlockDTO();
        try {
            CommonResponse commonResponse = blockController.createBlock(blockDTO);
            if (commonResponse.getStatus().equals(Boolean.TRUE)) {
                new ConfirmDialog(null, "Thêm thành công", commonResponse.getMessage());
                loadBlocks(jSearch.getKeyword().getText());
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
        BlockDTO blockDTO = createBlockDTO();
        blockDTO.setId(Integer.valueOf(jId.getText()));
        try {
            CommonResponse commonResponse = blockController.updateMajor(blockDTO);
            if (commonResponse.getStatus().equals(Boolean.TRUE)) {
                new ConfirmDialog(null, "Cập nhật thành công", commonResponse.getMessage());
                loadBlocks(jSearch.getKeyword().getText());
            } else {
                JOptionPane.showMessageDialog(null, commonResponse.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
        }
    }//GEN-LAST:event_jSuaActionPerformed

    private void jXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXoaActionPerformed
        int option = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa khối này?", 
                "Xác nhận xoá", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            try {
                CommonResponse commonResponse = blockController.deleteBlockById(Integer.valueOf(jId.getText()));
                if (commonResponse.getStatus().equals(Boolean.TRUE)) {
                    new ConfirmDialog(null, "Xóa thành công", commonResponse.getMessage());
                    clearInput();
                    loadBlocks(jSearch.getKeyword().getText());
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
        loadBlocks(jSearch.getKeyword().getText());
    }//GEN-LAST:event_jClearActionPerformed

    private void jChonMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChonMonActionPerformed
        try {
            JPanel al = new JPanel();
            for (Subject subject : subjectController.getSubjects()) {
                JCheckBox box = new JCheckBox(subject.getName());
                al.add(box);
            }

            if (ObjectUtils.isNotEmpty(jMonHoc.getText())) {
                List<String> checked = Arrays.stream(jMonHoc.getText().split(",")).collect(Collectors.toList());
                Component[] components = al.getComponents();
                for (Component component : components) {
                    JCheckBox checkBox = (JCheckBox) component;
                    if (checked.contains(checkBox.getText())) {
                        checkBox.setSelected(true);
                    }
                }
            }
            int option = JOptionPane.showConfirmDialog(null, al, "Chọn môn học cho khối", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                List<String> selected = new LinkedList<>();
                Component[] components = al.getComponents();
                for (Component component : components) {
                    JCheckBox checkBox = (JCheckBox) component;
                    if (checkBox.isSelected()) {
                        selected.add(checkBox.getText());
                    }
                }
                if(selected.size() > 3) {
                    JOptionPane.showMessageDialog(null, "Một khối chỉ được chọn 3 môn học!");
                    return;
                }
                jMonHoc.setText(selected.stream().collect(Collectors.joining(",")));
            }
        } catch (Exception ex) {
            Logger.getLogger(ManageMajorView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jChonMonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jChonMon;
    private javax.swing.JButton jClear;
    private javax.swing.JTextField jId;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField jMaKhoi;
    private javax.swing.JTextField jMonHoc;
    private JPanel jPanel1;
    private com.admission.components.search.Search jSearch;
    private javax.swing.JButton jSua;
    private javax.swing.JButton jThem;
    private javax.swing.JButton jXoa;
    private com.admission.components.border.PanelBorder panelBorder1;
    private javax.swing.JScrollPane spTable;
    private javax.swing.JTable tableBlock;
    // End of variables declaration//GEN-END:variables
}
