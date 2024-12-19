package com.admission.view.user;

import com.admission.components.dialog.ConfirmDialog;
import com.admission.components.table.TableHeader;
import com.admission.constants.SettingConstant;
import com.admission.controller.*;
import com.admission.dto.*;
import com.admission.model.Admission;
import com.admission.model.Major;
import com.admission.model.MajorDetail;
import com.admission.model.Setting;
import com.admission.utils.CurrentUserLogin;
import org.apache.commons.lang3.ObjectUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdmissionView extends javax.swing.JPanel {

    private final AdmissionController admissionController;

    private final StudentController studentController;

    private final MajorDetailController majorDetailController;

    private final BlockController blockController;

    private final SettingController settingController;

    private final Map<String, MajorDetailDTO> majorDtos;

    private Integer admissionOrdersSelected = null;

    public AdmissionView() {
        initComponents();
        this.admissionController = new AdmissionController();
        this.studentController = new StudentController();
        this.majorDetailController = new MajorDetailController();
        this.blockController = new BlockController();
        this.settingController = new SettingController();
        this.majorDtos = new HashMap<>();

        table.setTableHeader(TableHeader.customTableHeader(table.getTableHeader()));
        jThoiGianBdDk.setText(settingController.getSettingByKey(SettingConstant.START_TIME_ADMISSION).getValue());
        jThoiGianNgungDk.setText(settingController.getSettingByKey(SettingConstant.END_TIME_ADMISSION).getValue());
        loadAdmissions();
        handleSelectCellTable();
    }

    private void loadAdmissions() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        List<Admission> admissions = admissionController.getAdmissionsByStudentId(CurrentUserLogin.user.getStudentId());
        int numberRowsOfTable = admissions.size();
        model.setRowCount(numberRowsOfTable);
        for (int i = 0; i < numberRowsOfTable; i++) {
            Admission admission = admissions.get(i);
            Major major = admission.getMajor();
            model.setValueAt(admission.getOrders(), i, 0);
            model.setValueAt(major.getCode(), i, 1);
            model.setValueAt(major.getName(), i, 2);
            model.setValueAt(admission.getBlock().getCode(), i, 3);
            model.setValueAt(admission.getTotalScore(), i, 4);
            MajorDetail majorDetail = majorDetailController.getMajorDetailByMajorIdAndYear(
                    major.getId(), admission.getCreatedDate().getYear());
             model.setValueAt(majorDetail.getBenchMark(), i, 5);
            switch (admission.getStatus()) {
                case 1:
                    model.setValueAt("Đang chờ", i, 6);
                    break;
                case 2:
                    model.setValueAt("Đậu", i, 6);
                    break;
                case 3:
                    model.setValueAt("Trượt", i, 6);
                    break;
                case 4:
                    model.setValueAt(null, i, 6);
                    break;
                default:
                    break;
            }
        }
    }

    private void loadBlockOfMajor(String majorCode) {
        List<BlockDTO> blocks = blockController.getBlocksByMajorCode(majorCode);
        DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) jKhoi.getModel();
        model.removeAllElements();
        blocks.forEach(block -> jKhoi.addItem(block.getCode()));
    }

    private void setupDialog() throws Exception {
        jDangKyNV.setTitle("Đăng ký nguyện vọng");
        jDangKyNV.setSize(805, 285);
        jDangKyNV.setLocationRelativeTo(null);

        DefaultComboBoxModel<String> modelTenNganh = (DefaultComboBoxModel<String>) jTenNganhDK.getModel();
        modelTenNganh.removeAllElements();
        List<MajorDetailDTO> majorDetailDTOs = majorDetailController.getMajorDetails(LocalDate.now().getYear(), "");
        majorDetailDTOs.forEach(majorDetailDTO -> {
            majorDtos.put(majorDetailDTO.getName(), majorDetailDTO);
            jTenNganhDK.addItem(majorDetailDTO.getName());
        });

        jMaNganhDK.setText(majorDtos.get(jTenNganhDK.getSelectedItem().toString()).getCode());

        DefaultComboBoxModel<String> modelBlock = (DefaultComboBoxModel<String>) jKhoiDK.getModel();
        modelBlock.removeAllElements();
        List<BlockDTO> blocks = blockController.getBlocksByMajorCode(jMaNganhDK.getText());
        blocks.forEach(block -> {
            jKhoiDK.addItem(block.getCode());
        });
        jDangKyNV.setVisible(true);
    }

    private void handleSelectCellTable() {
        ListSelectionModel cellSelectionModel = table.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Integer nguyenVong = Integer.valueOf(table.getValueAt(selectedRow, 0).toString());
                    admissionOrdersSelected = nguyenVong;
                    jNguyenVong.setSelectedItem(nguyenVong.toString());
                    String maNganh = table.getValueAt(selectedRow, 1).toString();
                    jMaNganh.setText(maNganh);
                    jTenNganh.setText(table.getValueAt(selectedRow, 2).toString());
                    loadBlockOfMajor(maNganh);
                    if (ObjectUtils.isNotEmpty(table.getValueAt(selectedRow, 4))) {
                        jTongDiem.setText(table.getValueAt(selectedRow, 4).toString());
                    }
                    if (ObjectUtils.isNotEmpty(table.getValueAt(selectedRow, 6))) {
                        jTrangThai.setText(table.getValueAt(selectedRow, 6).toString());
                    }
                }
            }
        });
    }

    private void clearInput() {
        jNguyenVong.setSelectedIndex(0);
        jMaNganh.setText(null);
        jTenNganh.setText(null);
        jKhoi.setSelectedItem(null);
        jTongDiem.setText(null);
        jTrangThai.setText(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDangKyNV = new javax.swing.JDialog();
        panelBorder2 = new com.admission.components.border.PanelBorder();
        jBtnDK = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jMaNganhDK = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jKhoiDK = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jNvDK = new javax.swing.JComboBox<>();
        jTenNganhDK = new javax.swing.JComboBox<>();
        panelBorder = new com.admission.components.border.PanelBorder();
        spTable = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jPanelButton = new javax.swing.JPanel();
        jThem = new javax.swing.JButton();
        jSua = new javax.swing.JButton();
        jXoa = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jThoiGianBdDk = new javax.swing.JLabel();
        jThoiGianNgungDk = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTongDiem = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTrangThai = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jMaNganh = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTenNganh = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jNguyenVong = new javax.swing.JComboBox<>();
        jKhoi = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();

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

        jLabel8.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jLabel8.setText("Mã ngành");

        jMaNganhDK.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        jMaNganhDK.setFocusable(false);

        jLabel9.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jLabel9.setText("Tên ngành");

        jKhoiDK.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N

        jLabel10.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jLabel10.setText("Nguyện vọng");

        jLabel11.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
        jLabel11.setText("Khối");

        jNvDK.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        jNvDK.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        jTenNganhDK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTenNganhDKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBorder2Layout = new javax.swing.GroupLayout(panelBorder2);
        panelBorder2.setLayout(panelBorder2Layout);
        panelBorder2Layout.setHorizontalGroup(
            panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder2Layout.createSequentialGroup()
                        .addComponent(jTenNganhDK, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jMaNganhDK, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47))
                    .addGroup(panelBorder2Layout.createSequentialGroup()
                        .addComponent(jKhoiDK, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jNvDK, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTenNganhDK, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jMaNganhDK, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jKhoiDK, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jNvDK, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                "Nguyện vọng", "Mã ngành", "Tên ngành", "Khối", "Tổng điểm", "Điểm chuẩn", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setRowHeight(40);
        spTable.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(40);
            table.getColumnModel().getColumn(1).setPreferredWidth(60);
            table.getColumnModel().getColumn(2).setPreferredWidth(250);
            table.getColumnModel().getColumn(3).setPreferredWidth(40);
            table.getColumnModel().getColumn(4).setPreferredWidth(40);
            table.getColumnModel().getColumn(5).setPreferredWidth(50);
            table.getColumnModel().getColumn(6).setPreferredWidth(100);
        }

        jPanelButton.setBackground(new java.awt.Color(255, 255, 255));

        jThem.setBackground(new java.awt.Color(51, 153, 255));
        jThem.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Add.png"))); // NOI18N
        jThem.setText("Thêm nguyện vọng");
        jThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jThemActionPerformed(evt);
            }
        });

        jSua.setBackground(new java.awt.Color(51, 153, 255));
        jSua.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Edit.png"))); // NOI18N
        jSua.setText("Sửa nguyện vọng");
        jSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSuaActionPerformed(evt);
            }
        });

        jXoa.setBackground(new java.awt.Color(51, 153, 255));
        jXoa.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon/Delete.png"))); // NOI18N
        jXoa.setText("Xóa nguyện vọng");
        jXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelButtonLayout = new javax.swing.GroupLayout(jPanelButton);
        jPanelButton.setLayout(jPanelButtonLayout);
        jPanelButtonLayout.setHorizontalGroup(
            jPanelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelButtonLayout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(jThem)
                .addGap(79, 79, 79)
                .addComponent(jSua)
                .addGap(75, 75, 75)
                .addComponent(jXoa)
                .addContainerGap(115, Short.MAX_VALUE))
        );
        jPanelButtonLayout.setVerticalGroup(
            jPanelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelButtonLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jThem)
                    .addComponent(jSua)
                    .addComponent(jXoa))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel1.setText("Thời gian bắt đầu đăng ký nguyện vọng:");

        jThoiGianBdDk.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N

        jThoiGianNgungDk.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel6.setText("Tổng điểm");

        jTongDiem.setFocusable(false);

        jLabel7.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel7.setText("Trạng thái");

        jTrangThai.setFocusable(false);

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel2.setText("Nguyện vọng");

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel3.setText("Mã ngành");

        jMaNganh.setFocusable(false);

        jLabel4.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel4.setText("Tên ngành");

        jTenNganh.setFocusable(false);

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel5.setText("Khối");

        jNguyenVong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        jNguyenVong.setMinimumSize(new java.awt.Dimension(36, 28));
        jNguyenVong.setPreferredSize(new java.awt.Dimension(36, 28));

        jKhoi.setMinimumSize(new java.awt.Dimension(36, 28));
        jKhoi.setPreferredSize(new java.awt.Dimension(36, 28));

        jLabel12.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel12.setText("Thời gian ngừng đăng ký nguyện vọng:");

        javax.swing.GroupLayout panelBorderLayout = new javax.swing.GroupLayout(panelBorder);
        panelBorder.setLayout(panelBorderLayout);
        panelBorderLayout.setHorizontalGroup(
            panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorderLayout.createSequentialGroup()
                        .addComponent(spTable)
                        .addContainerGap())
                    .addGroup(panelBorderLayout.createSequentialGroup()
                        .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanelButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panelBorderLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel2))
                                .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelBorderLayout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jKhoi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(panelBorderLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jNguyenVong, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(70, 70, 70)
                                .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel6))
                                .addGap(10, 10, 10)
                                .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTongDiem)
                                    .addComponent(jMaNganh))
                                .addGap(70, 70, 70)
                                .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel4))
                                .addGap(10, 10, 10)
                                .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTrangThai, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTenNganh, javax.swing.GroupLayout.Alignment.LEADING))))
                        .addGap(15, 15, 15))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panelBorderLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jThoiGianNgungDk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelBorderLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jThoiGianBdDk, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBorderLayout.setVerticalGroup(
            panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jThoiGianBdDk, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jThoiGianNgungDk, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(32, 32, 32)
                .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jMaNganh, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTenNganh, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jNguyenVong, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorderLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTongDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorderLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jKhoi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(spTable, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

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
                loadAdmissions();
            } else {
                JOptionPane.showMessageDialog(null, commonResponse.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
        }
    }//GEN-LAST:event_jBtnDKActionPerformed

    private void jXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXoaActionPerformed
        int option = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa nguyện vọng này?",
                "Xác nhận xoá", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            try {
                CommonResponse commonResponse = admissionController.deleteAdmission(
                        CurrentUserLogin.user.getStudentId(), admissionOrdersSelected);
                if (commonResponse.getStatus().equals(Boolean.TRUE)) {
                    new ConfirmDialog(null, "Xóa thành công", commonResponse.getMessage());
                    clearInput();
                    loadAdmissions();
                } else {
                    JOptionPane.showMessageDialog(null, commonResponse.getMessage());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jXoaActionPerformed

    private void jSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSuaActionPerformed
        AdmissionUpdateDTO admissionUpdateDTO = new AdmissionUpdateDTO();
        admissionUpdateDTO.setOldOrders(admissionOrdersSelected);
        admissionUpdateDTO.setOrders(Integer.valueOf(jNguyenVong.getSelectedItem().toString()));
        admissionUpdateDTO.setStudentId(CurrentUserLogin.user.getStudentId());
        admissionUpdateDTO.setBlock(jKhoi.getSelectedItem().toString());
        try {

            CommonResponse commonResponse = admissionController.updateAdmission(admissionUpdateDTO);
            if (commonResponse.getStatus().equals(Boolean.TRUE)) {
                new ConfirmDialog(null, "Cập nhật thành công", commonResponse.getMessage());
                loadAdmissions();
            } else {
                JOptionPane.showMessageDialog(null, commonResponse.getMessage());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
        }
    }//GEN-LAST:event_jSuaActionPerformed

    private void jThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jThemActionPerformed
        try {
            CommonResponse checkInfoResponse = studentController.checkInfoStudent(CurrentUserLogin.user.getStudentId());
            if (checkInfoResponse.getStatus().equals(Boolean.FALSE)) {
                JOptionPane.showMessageDialog(null, checkInfoResponse.getMessage());
                return;
            }
            Setting startTimeSetting = settingController.getSettingByKey(SettingConstant.START_TIME_ADMISSION);
            Setting endTimeSetting = settingController.getSettingByKey(SettingConstant.END_TIME_ADMISSION);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime startTime = LocalDateTime.parse(startTimeSetting.getValue(), dateTimeFormatter);
            LocalDateTime endTime = LocalDateTime.parse(endTimeSetting.getValue(), dateTimeFormatter);
            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(startTime)) {
                JOptionPane.showMessageDialog(null, "Thời gian đăng ký nguyện vọng chưa bắt đầu!");
                return;
            }
            if (now.isAfter(endTime)) {
                JOptionPane.showMessageDialog(null, "Thời gian đăng ký nguyện vọng đã kết thúc!");
                return;
            }
            setupDialog();
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(AdmissionView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jThemActionPerformed

    private void jTenNganhDKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTenNganhDKActionPerformed
        jMaNganhDK.setText(majorDtos.get(jTenNganhDK.getSelectedItem().toString()).getCode());
    }//GEN-LAST:event_jTenNganhDKActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnDK;
    private javax.swing.JDialog jDangKyNV;
    private javax.swing.JComboBox<String> jKhoi;
    private javax.swing.JComboBox<String> jKhoiDK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jMaNganh;
    private javax.swing.JTextField jMaNganhDK;
    private javax.swing.JComboBox<String> jNguyenVong;
    private javax.swing.JComboBox<String> jNvDK;
    private javax.swing.JPanel jPanelButton;
    private javax.swing.JButton jSua;
    private javax.swing.JTextField jTenNganh;
    private javax.swing.JComboBox<String> jTenNganhDK;
    private javax.swing.JButton jThem;
    private javax.swing.JLabel jThoiGianBdDk;
    private javax.swing.JLabel jThoiGianNgungDk;
    private javax.swing.JTextField jTongDiem;
    private javax.swing.JTextField jTrangThai;
    private javax.swing.JButton jXoa;
    private com.admission.components.border.PanelBorder panelBorder;
    private com.admission.components.border.PanelBorder panelBorder2;
    private javax.swing.JScrollPane spTable;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables

    private void setValueAt(Integer orders, int i, int i0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
