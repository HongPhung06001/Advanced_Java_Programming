package com.admission.view.user;

import com.admission.components.dialog.ConfirmDialog;
import com.admission.components.table.TableButtonCellEditor;
import com.admission.components.table.TableButtonCellRender;
import com.admission.components.table.TableHeader;
import com.admission.constants.SettingConstant;
import com.admission.controller.AdmissionController;
import com.admission.controller.MajorDetailController;
import com.admission.controller.SettingController;
import com.admission.controller.StudentController;
import com.admission.dto.AdmissionCreateDTO;
import com.admission.dto.CommonResponse;
import com.admission.dto.MajorDetailDTO;
import com.admission.event.EventButtonSearchClick;
import com.admission.event.EventButtonTableClick;
import com.admission.model.Setting;
import com.admission.utils.CurrentUserLogin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MajorView extends javax.swing.JPanel {

    private final MajorDetailController majorDetailController;

    private final AdmissionController admissionController;

    private final StudentController studentController;

    private final SettingController settingController;

    public MajorView() {
        initComponents();
        this.majorDetailController = new MajorDetailController();
        this.admissionController = new AdmissionController();
        this.studentController = new StudentController();
        this.settingController = new SettingController();

        table.setTableHeader(TableHeader.customTableHeader(table.getTableHeader()));
        EventButtonTableClick registerEvent = new EventButtonTableClick() {
            @Override
            public void onClick(int row) {
                if (checkMajorRegistor()) {
                    setupDialog(row);
                }
            }
        };
        table.getColumnModel().getColumn(5).setCellRenderer(new TableButtonCellRender("Đăng ký"));
        table.getColumnModel().getColumn(5).setCellEditor(new TableButtonCellEditor(registerEvent, "Đăng ký"));

        loadFilterYearComboBox();
        loadMajor(search1.getKeyword().getText());

        search1.addEventButtonSearchClick(new EventButtonSearchClick() {
            @Override
            public void onClick(String text) {
                loadMajor(text);
            }
        });
    }

    private void loadFilterYearComboBox() {
        Integer minYear = majorDetailController.getYearMinMajor();
        Integer maxYear = LocalDate.now().getYear();
        for (int i = maxYear; i >= minYear; i--) {
            filterYear.addItem(String.valueOf(i));
        }
    }

    private void loadMajor(String keyword) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        Integer year = Integer.valueOf(filterYear.getSelectedItem().toString());
        List<MajorDetailDTO> majorDetailDTOs = majorDetailController.getMajorDetails(year, keyword);
        int numberRowsOfTable = majorDetailDTOs.size();
        model.setRowCount(numberRowsOfTable);
        for (int i = 0; i < numberRowsOfTable; i++) {
            model.setValueAt(majorDetailDTOs.get(i).getCode(), i, 0);
            model.setValueAt(majorDetailDTOs.get(i).getName(), i, 1);
            model.setValueAt(majorDetailDTOs.get(i).getBlocks(), i, 2);
            model.setValueAt(majorDetailDTOs.get(i).getAmountStudentReceived(), i, 3);
            model.setValueAt(majorDetailDTOs.get(i).getBenchMark(), i, 4);
        }
    }

    private boolean checkMajorRegistor() {
        CommonResponse checkInfoResponse = studentController.checkInfoStudent(CurrentUserLogin.user.getStudentId());
        if (checkInfoResponse.getStatus().equals(Boolean.FALSE)) {
            JOptionPane.showMessageDialog(null, checkInfoResponse.getMessage());
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        Integer year = Integer.valueOf(filterYear.getSelectedItem().toString());
        if (!year.equals(now.getYear())) {
            JOptionPane.showMessageDialog(null, "Thời gian đăng ký ngành này đã kết thúc!");
            return false;
        }
        Setting startTimeSetting = settingController.getSettingByKey(SettingConstant.START_TIME_ADMISSION);
        Setting endTimeSetting = settingController.getSettingByKey(SettingConstant.END_TIME_ADMISSION);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(startTimeSetting.getValue(), dateTimeFormatter);
        LocalDateTime endTime = LocalDateTime.parse(endTimeSetting.getValue(), dateTimeFormatter);
        if (now.isBefore(startTime)) {
            JOptionPane.showMessageDialog(null, "Thời gian đăng ký chưa bắt đầu!");
            return false;
        }
        if (now.isAfter(endTime)) {
            JOptionPane.showMessageDialog(null, "Thời gian đăng ký đã kết thúc!");
            return false;
        }
        return true;
    }

    private void setupDialog(int row) {
        jDangKyNV.setTitle("Đăng ký nguyện vọng");
        jDangKyNV.setSize(805, 285);
        jDangKyNV.setLocationRelativeTo(null);

        jMaNganhDK.setText(table.getValueAt(row, 0).toString());
        jTenNganhDK.setText(table.getValueAt(row, 1).toString());
        DefaultComboBoxModel<String> modelBlock = (DefaultComboBoxModel<String>) jKhoiDK.getModel();
        modelBlock.removeAllElements();
        List<String> blocks = Arrays.stream(table.getValueAt(row, 2).toString().split(",")).collect(Collectors.toList());
        blocks.forEach(block -> {
            jKhoiDK.addItem(block);
        });
        jDangKyNV.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDangKyNV = new javax.swing.JDialog();
        panelBorder2 = new com.admission.components.border.PanelBorder();
        jBtnDK = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jMaNganhDK = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTenNganhDK = new javax.swing.JTextField();
        jKhoiDK = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jNvDK = new javax.swing.JComboBox<>();
        panelBorder1 = new com.admission.components.border.PanelBorder();
        spTable = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        filterYear = new javax.swing.JComboBox<>();
        search1 = new com.admission.components.search.Search();

        jDangKyNV.setBackground(new java.awt.Color(255, 255, 255));

        panelBorder2.setPreferredSize(new java.awt.Dimension(800, 500));

        jBtnDK.setBackground(new java.awt.Color(66, 133, 244));
        jBtnDK.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jBtnDK.setForeground(new java.awt.Color(255, 255, 255));
        jBtnDK.setText("Đăng ký");
        jBtnDK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnDKActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jLabel1.setText("Mã ngành");

        jMaNganhDK.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        jMaNganhDK.setFocusable(false);

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jLabel2.setText("Tên ngành");

        jTenNganhDK.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        jTenNganhDK.setFocusable(false);

        jKhoiDK.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jLabel3.setText("Nguyện vọng");

        jLabel4.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jLabel4.setText("Khối");

        jNvDK.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        jNvDK.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        javax.swing.GroupLayout panelBorder2Layout = new javax.swing.GroupLayout(panelBorder2);
        panelBorder2.setLayout(panelBorder2Layout);
        panelBorder2Layout.setHorizontalGroup(
            panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jKhoiDK, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jMaNganhDK, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                .addGap(55, 55, 55)
                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(29, 29, 29)
                        .addComponent(jTenNganhDK, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBorder2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jNvDK, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(45, Short.MAX_VALUE))
            .addGroup(panelBorder2Layout.createSequentialGroup()
                .addGap(250, 250, 250)
                .addComponent(jBtnDK, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBorder2Layout.setVerticalGroup(
            panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jMaNganhDK, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTenNganhDK, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jKhoiDK, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jNvDK, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addComponent(jBtnDK, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDangKyNVLayout = new javax.swing.GroupLayout(jDangKyNV.getContentPane());
        jDangKyNV.getContentPane().setLayout(jDangKyNVLayout);
        jDangKyNVLayout.setHorizontalGroup(
            jDangKyNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDangKyNVLayout.setVerticalGroup(
            jDangKyNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder2, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
        );

        table.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã ngành", "Tên ngành", "Khối", "Chỉ tiêu", "Điểm chuẩn", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setRowHeight(40);
        spTable.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(40);
            table.getColumnModel().getColumn(1).setPreferredWidth(250);
            table.getColumnModel().getColumn(2).setPreferredWidth(100);
            table.getColumnModel().getColumn(3).setPreferredWidth(30);
            table.getColumnModel().getColumn(4).setPreferredWidth(40);
            table.getColumnModel().getColumn(5).setPreferredWidth(30);
        }

        filterYear.setBackground(new java.awt.Color(153, 204, 255));
        filterYear.setEditable(true);
        filterYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterYearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addComponent(search1, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spTable, javax.swing.GroupLayout.DEFAULT_SIZE, 805, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(filterYear, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(search1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterYear, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(spTable, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
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
            .addComponent(panelBorder1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void filterYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterYearActionPerformed
        loadMajor(search1.getKeyword().getText());
    }//GEN-LAST:event_filterYearActionPerformed

    private void jBtnDKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDKActionPerformed
        AdmissionCreateDTO admissionCreateDTO = new AdmissionCreateDTO();
        admissionCreateDTO.setOrders(Integer.valueOf(jNvDK.getSelectedItem().toString()));
        admissionCreateDTO.setStudentId(CurrentUserLogin.user.getStudentId());
        admissionCreateDTO.setMajorCode(jMaNganhDK.getText());
        admissionCreateDTO.setBlock(jKhoiDK.getSelectedItem().toString());
        try {
            CommonResponse commonResponse = admissionController.createAdmission(admissionCreateDTO);
            if (commonResponse.getStatus().equals(Boolean.TRUE)) {
                new ConfirmDialog(null, "Đăng ký thành công", commonResponse.getMessage());
                jDangKyNV.dispose();
            } else {
                JOptionPane.showMessageDialog(null, commonResponse.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
        }
    }//GEN-LAST:event_jBtnDKActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> filterYear;
    private javax.swing.JButton jBtnDK;
    private javax.swing.JDialog jDangKyNV;
    private javax.swing.JComboBox<String> jKhoiDK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField jMaNganhDK;
    private javax.swing.JComboBox<String> jNvDK;
    private javax.swing.JTextField jTenNganhDK;
    private com.admission.components.border.PanelBorder panelBorder1;
    private com.admission.components.border.PanelBorder panelBorder2;
    private com.admission.components.search.Search search1;
    private javax.swing.JScrollPane spTable;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
