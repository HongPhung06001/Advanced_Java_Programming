package com.admission.view.admin;

import com.admission.components.dialog.ConfirmDialog;
import com.admission.components.table.TableHeader;
import com.admission.constants.SettingConstant;
import com.admission.controller.AdmissionController;
import com.admission.controller.MajorDetailController;
import com.admission.controller.SettingController;
import com.admission.dto.CommonResponse;
import com.admission.dto.MajorDetailDTO;
import com.admission.dto.ReadFileExcelResponse;
import com.admission.event.EventButtonSearchClick;
import com.admission.job.CongBoDiemChuanJob;
import com.admission.model.MajorDetail;
import com.admission.utils.ExcelMajorUtil;
import com.admission.utils.ResourceUtil;
import com.github.lgooddatepicker.components.DatePickerSettings;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ManageMajorDetailView extends JPanel {

    private final MajorDetailController majorDetailController;
    
    private final AdmissionController admissionController;
    
    private final SettingController settingController;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ManageMajorDetailView() {
        initComponents();
        this.majorDetailController = new MajorDetailController();
        this.admissionController = new AdmissionController();
        this.settingController = new SettingController();
        jImport.setText("Import dữ liệu " + LocalDate.now().getYear());
        tableMajor.setTableHeader(TableHeader.customTableHeader(tableMajor.getTableHeader()));

        loadFilterYearComboBox();
        loadMajors(jSearch.getKeyword().getText());
        handleSelectCellTable();

        jSearch.addEventButtonSearchClick(new EventButtonSearchClick() {
            @Override
            public void onClick(String text) {
                loadMajors(text);
            }
        });

    }

    private void loadFilterYearComboBox() {
        Integer minYear = majorDetailController.getYearMinMajor();
        Integer maxYear = LocalDate.now().getYear();
        for (int i = maxYear; i >= minYear; i--) {
            jFilterYear.addItem(String.valueOf(i));
        }
    }

    private void loadMajors(String keyword) {
        DefaultTableModel model = (DefaultTableModel) tableMajor.getModel();
        Integer year = Integer.valueOf(jFilterYear.getSelectedItem().toString());
        List<MajorDetailDTO> majorDetailDTOs = majorDetailController.getMajorDetailsForAdmin(year, keyword);
        int numberRowsOfTable = majorDetailDTOs.size();
        model.setRowCount(numberRowsOfTable);
        for (int i = 0; i < numberRowsOfTable; i++) {
            model.setValueAt(majorDetailDTOs.get(i).getId(), i, 0);
            model.setValueAt(majorDetailDTOs.get(i).getCode(), i, 1);
            model.setValueAt(majorDetailDTOs.get(i).getName(), i, 2);
            model.setValueAt(majorDetailDTOs.get(i).getBlocks(), i, 3);
            model.setValueAt(majorDetailDTOs.get(i).getAmountStudentReceived(), i, 4);
            model.setValueAt(majorDetailDTOs.get(i).getBenchMark(), i, 5);
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
                    if (ObjectUtils.isNotEmpty(tableMajor.getValueAt(selectedRow, 0))) {
                        Integer studentId = Integer.valueOf(tableMajor.getValueAt(selectedRow, 0).toString());
                        jId.setText(studentId.toString());
                    } else {
                        jId.setText(null);
                    }
                    jMaNganh.setText(tableMajor.getValueAt(selectedRow, 1).toString());
                    jTenNganh.setText(tableMajor.getValueAt(selectedRow, 2).toString());
                    jKhoi.setText(tableMajor.getValueAt(selectedRow, 3).toString());
                    if (ObjectUtils.isNotEmpty(tableMajor.getValueAt(selectedRow, 4))) {
                        jChiTieu.setText(tableMajor.getValueAt(selectedRow, 4).toString());
                    } else {
                        jChiTieu.setText(null);
                    }
                    if (ObjectUtils.isNotEmpty(tableMajor.getValueAt(selectedRow, 5))) {
                        jDiemChuan.setText(tableMajor.getValueAt(selectedRow, 5).toString());
                    } else {
                        jDiemChuan.setText(null);
                    }
                }
            }
        });
    }

    private MajorDetailDTO createMajorDetailDTO() {
        MajorDetailDTO majorDetailDTO = new MajorDetailDTO();
        if (ObjectUtils.isNotEmpty(jId.getText())) {
            majorDetailDTO.setId(Integer.valueOf(jId.getText()));
        }
        majorDetailDTO.setCode(jMaNganh.getText());
        majorDetailDTO.setName(jTenNganh.getText());
        majorDetailDTO.setBlocks(jKhoi.getText());
        if (ObjectUtils.isNotEmpty(jChiTieu.getText())) {
            majorDetailDTO.setAmountStudentReceived(Integer.valueOf(jChiTieu.getText()));
        }
        if (ObjectUtils.isNotEmpty(jDiemChuan.getText())) {
            majorDetailDTO.setBenchMark(Float.valueOf(jDiemChuan.getText()));
        }
        return majorDetailDTO;
    }

    private void clearInput() {
        jId.setText(null);
        jMaNganh.setText(null);
        jTenNganh.setText(null);
        jKhoi.setText(null);
        jChiTieu.setText(null);
        jDiemChuan.setText(null);
    }

    private void setupDialog() throws Exception {
        jChonThoiGian.setTitle("Đăng ký nguyện vọng");
        jChonThoiGian.setSize(470, 175);
        jChonThoiGian.setLocationRelativeTo(null);
        final DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DatePickerSettings settingDateNgayBatDau = new DatePickerSettings();
        settingDateNgayBatDau.setFormatForDatesCommonEra(formatterDate);
        jThoiGianCBD.datePicker.setSettings(settingDateNgayBatDau);
        jThoiGianCBD.setDateTimePermissive(LocalDateTime.now().plusMinutes(1));
        jChonThoiGian.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jChonThoiGian = new JDialog();
        panelBorder2 = new com.admission.components.border.PanelBorder();
        jLabel8 = new JLabel();
        jThoiGianCBD = new com.github.lgooddatepicker.components.DateTimePicker();
        jBtnXacNhan = new JButton();
        panelBorder1 = new com.admission.components.border.PanelBorder();
        jLabel2 = new JLabel();
        jId = new JTextField();
        jLabel3 = new JLabel();
        jMaNganh = new JTextField();
        jLabel4 = new JLabel();
        jTenNganh = new JTextField();
        jLabel5 = new JLabel();
        jKhoi = new JTextField();
        jLabel6 = new JLabel();
        jChiTieu = new JTextField();
        jLabel7 = new JLabel();
        jDiemChuan = new JTextField();
        jPanel1 = new JPanel();
        jSua = new JButton();
        jXoa = new JButton();
        jClear = new JButton();
        jSearch = new com.admission.components.search.Search();
        jFilterYear = new JComboBox<>();
        jExport = new JButton();
        jImport = new JButton();
        jCongBoDiem = new JButton();
        jCapNhatDiemThi = new JButton();
        jLabel1 = new JLabel();
        spTable = new JScrollPane();
        tableMajor = new JTable();

        jLabel8.setText("Chọn thời gian công bố điểm chuẩn");
        jLabel8.setFont(new Font("sansserif", 1, 14)); // NOI18N

        jBtnXacNhan.setText("Xác nhận");
        jBtnXacNhan.setBackground(new Color(66, 133, 244));
        jBtnXacNhan.setFont(new Font("sansserif", 1, 13)); // NOI18N
        jBtnXacNhan.setForeground(new Color(255, 255, 255));
        jBtnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnXacNhanActionPerformed(evt);
            }
        });

        GroupLayout panelBorder2Layout = new GroupLayout(panelBorder2);
        panelBorder2.setLayout(panelBorder2Layout);
        panelBorder2Layout.setHorizontalGroup(
            panelBorder2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder2Layout.createSequentialGroup()
                .addGroup(panelBorder2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder2Layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(jLabel8))
                    .addGroup(panelBorder2Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(jThoiGianCBD, GroupLayout.PREFERRED_SIZE, 296, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(82, Short.MAX_VALUE))
            .addGroup(GroupLayout.Alignment.TRAILING, panelBorder2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jBtnXacNhan, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)
                .addGap(137, 137, 137))
        );
        panelBorder2Layout.setVerticalGroup(
            panelBorder2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jThoiGianCBD, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jBtnXacNhan, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        GroupLayout jChonThoiGianLayout = new GroupLayout(jChonThoiGian.getContentPane());
        jChonThoiGian.getContentPane().setLayout(jChonThoiGianLayout);
        jChonThoiGianLayout.setHorizontalGroup(
            jChonThoiGianLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jChonThoiGianLayout.createSequentialGroup()
                .addComponent(panelBorder2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jChonThoiGianLayout.setVerticalGroup(
            jChonThoiGianLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel2.setText("ID");
        jLabel2.setFont(new Font("sansserif", 1, 12)); // NOI18N

        jId.setFocusable(false);

        jLabel3.setText("Mã ngành");
        jLabel3.setFont(new Font("sansserif", 1, 12)); // NOI18N

        jMaNganh.setFocusable(false);

        jLabel4.setText("Tên ngành");
        jLabel4.setFont(new Font("sansserif", 1, 12)); // NOI18N

        jTenNganh.setFocusable(false);

        jLabel5.setText("Khối");
        jLabel5.setFont(new Font("sansserif", 1, 12)); // NOI18N

        jKhoi.setFocusable(false);

        jLabel6.setText("Chỉ tiêu");
        jLabel6.setFont(new Font("sansserif", 1, 12)); // NOI18N

        jLabel7.setText("Điểm chuẩn");
        jLabel7.setFont(new Font("sansserif", 1, 12)); // NOI18N

        jPanel1.setBackground(new Color(255, 255, 255));

        jSua.setIcon(new ImageIcon(getClass().getResource("/images/icon/Edit.png"))); // NOI18N
        jSua.setText("Tạo/Sửa");
        jSua.setFont(new Font("sansserif", 1, 12)); // NOI18N
        jSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSuaActionPerformed(evt);
            }
        });

        jXoa.setIcon(new ImageIcon(getClass().getResource("/images/icon/Delete.png"))); // NOI18N
        jXoa.setText("Xóa");
        jXoa.setFont(new Font("sansserif", 1, 12)); // NOI18N
        jXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXoaActionPerformed(evt);
            }
        });

        jClear.setIcon(new ImageIcon(getClass().getResource("/images/icon/Refresh.png"))); // NOI18N
        jClear.setText("Làm mới");
        jClear.setFont(new Font("sansserif", 1, 12)); // NOI18N
        jClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jClearActionPerformed(evt);
            }
        });

        jFilterYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFilterYearActionPerformed(evt);
            }
        });

        jExport.setText("Export file");
        jExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jExportActionPerformed(evt);
            }
        });

        jImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jImportActionPerformed(evt);
            }
        });

        jCongBoDiem.setText("Công bố điểm chuẩn");
        jCongBoDiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCongBoDiemActionPerformed(evt);
            }
        });

        jCapNhatDiemThi.setText("Lấy điểm thi của sinh viên");
        jCapNhatDiemThi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCapNhatDiemThiActionPerformed(evt);
            }
        });

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSearch, GroupLayout.PREFERRED_SIZE, 330, GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(jSua, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jXoa, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
                        .addGap(112, 112, 112)
                        .addComponent(jClear))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(343, 343, 343)
                        .addComponent(jFilterYear, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCapNhatDiemThi)
                .addGap(18, 18, 18)
                .addComponent(jCongBoDiem)
                .addGap(18, 18, 18)
                .addComponent(jExport)
                .addGap(18, 18, 18)
                .addComponent(jImport, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(jImport, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jExport, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCongBoDiem)
                        .addComponent(jCapNhatDiemThi)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSearch, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(jFilterYear))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jSua)
                    .addComponent(jClear)
                    .addComponent(jXoa))
                .addContainerGap())
        );

        jLabel1.setText("Quản lý tuyển sinh");
        jLabel1.setFont(new Font("sansserif", 1, 22)); // NOI18N

        tableMajor.setFont(new Font("sansserif", 0, 13)); // NOI18N
        tableMajor.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Mã ngành", "Tên ngành", "Khối", "Chỉ tiêu", "Điểm chuẩn"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false
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
            tableMajor.getColumnModel().getColumn(3).setPreferredWidth(100);
        }

        GroupLayout panelBorder1Layout = new GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spTable)
                .addContainerGap())
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelBorder1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5))
                .addGap(49, 49, 49)
                .addGroup(panelBorder1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jKhoi, GroupLayout.Alignment.LEADING)
                    .addComponent(jId, GroupLayout.Alignment.LEADING))
                .addGap(70, 70, 70)
                .addGroup(panelBorder1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6))
                .addGap(20, 20, 20)
                .addGroup(panelBorder1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jChiTieu)
                    .addComponent(jMaNganh))
                .addGap(70, 70, 70)
                .addGroup(panelBorder1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4))
                .addGap(20, 20, 20)
                .addGroup(panelBorder1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jDiemChuan, GroupLayout.Alignment.LEADING)
                    .addComponent(jTenNganh, GroupLayout.Alignment.LEADING))
                .addGap(30, 30, 30))
            .addGroup(GroupLayout.Alignment.TRAILING, panelBorder1Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(24, 24, 24)
                .addGroup(panelBorder1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jMaNganh, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTenNganh, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(panelBorder1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jKhoi, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jChiTieu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDiemChuan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spTable, GroupLayout.PREFERRED_SIZE, 338, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jFilterYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFilterYearActionPerformed
        loadMajors(jSearch.getKeyword().getText());
    }//GEN-LAST:event_jFilterYearActionPerformed

    private void jExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jExportActionPerformed
        ExcelMajorUtil.exportFileExample(majorDetailController, "D:/example.xlsx");
        new ConfirmDialog(null, "Lưu thành công", "File đã được lưu vào D:/example.xlsx");
    }//GEN-LAST:event_jExportActionPerformed

    private void jImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jImportActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("D:\\"));
        fileChooser.setDialogTitle("Chọn file excel");
        fileChooser.setPreferredSize(new Dimension(700, 500));
        FileNameExtensionFilter imageFilter
                = new FileNameExtensionFilter("Excel files (*.xlsx)", "xlsx");
        fileChooser.setFileFilter(imageFilter);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String extension = ResourceUtil.getFileExtension(selectedFile);
            if (extension == null || !ResourceUtil.isExcelExtension(extension)) {
                JOptionPane.showMessageDialog(this, "File không hợp lệ! Vui lòng chọn file excel");
                return;
            }
            try {
                ReadFileExcelResponse<List<MajorDetailDTO>> readFileResponse = ExcelMajorUtil.readFile(selectedFile.getAbsolutePath());
                if (readFileResponse.getStatus().equals(Boolean.TRUE)) {
                    CommonResponse response = majorDetailController.createOrUpdateMajorDetails(readFileResponse.getResponse());
                    if (response.getStatus().equals(Boolean.TRUE)) {
                        new ConfirmDialog(null, "Cập nhật thành công", response.getMessage());
                        loadMajors(jSearch.getKeyword().getText());
                    } else {
                        JOptionPane.showMessageDialog(null, response.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, readFileResponse.getMessage());
                }
            } catch (HeadlessException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jImportActionPerformed

    private void jCongBoDiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCongBoDiemActionPerformed
        List<MajorDetail> majorDetails = majorDetailController.getMajorDetailsHaveCriteriaNoBenchmarks(LocalDate.now().getYear());
        if (CollectionUtils.isNotEmpty(majorDetails)) {
            JOptionPane.showMessageDialog(this, "Cần cập nhật điểm chuẩn cho các ngành tuyển sinh (Đã có chỉ tiêu tuyển sinh)!");
            return;
        }
        if(settingController.getSettingByKey(SettingConstant.CALL_TOTAL_SCORE_STUDENT).getValue().equals("false")) {
            JOptionPane.showMessageDialog(this, "Cần lấy điểm thi của sinh viên trước khi công bố điểm chuẩn!");
            return;
        }
        try {
            setupDialog();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jCongBoDiemActionPerformed

    private void jClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jClearActionPerformed
        clearInput();
        jSearch.getKeyword().setText(null);
        loadMajors(jSearch.getKeyword().getText());
    }//GEN-LAST:event_jClearActionPerformed

    private void jSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSuaActionPerformed
        MajorDetailDTO majorDetailDTO = createMajorDetailDTO();
        try {
            CommonResponse commonResponse = majorDetailController.createOrUpdateMajorDetail(majorDetailDTO);
            if (commonResponse.getStatus().equals(Boolean.TRUE)) {
                new ConfirmDialog(null, "Cập nhật thành công", commonResponse.getMessage());
                loadMajors(jSearch.getKeyword().getText());
            } else {
                JOptionPane.showMessageDialog(null, commonResponse.getMessage());
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
        }
    }//GEN-LAST:event_jSuaActionPerformed

    private void jXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXoaActionPerformed
        int option = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa chi tiết chuyên ngành này?",
                "Xác nhận xoá", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            try {
                CommonResponse response = majorDetailController.deleteMajorDetailById(Integer.valueOf(jId.getText()));
                if (response.getStatus().equals(Boolean.TRUE)) {
                    new ConfirmDialog(null, "Xóa thành công", response.getMessage());
                    clearInput();
                    loadMajors(jSearch.getKeyword().getText());
                } else {
                    JOptionPane.showMessageDialog(null, response.getMessage());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jXoaActionPerformed

    private void jBtnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnXacNhanActionPerformed
        // Khởi tạo đối tượng Scheduler
        Scheduler scheduler;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
            return;
        }
        // Tạo cron expression từ thời gian được chọn bởi người dùng
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("ss mm HH dd MM ? yyyy");
        LocalDateTime selectedDateTime = jThoiGianCBD.getDateTimeStrict();
        String cronExpression = dateFormat.format(selectedDateTime);
        // Tạo công việc (Job)
        JobDetail job = JobBuilder.newJob(CongBoDiemChuanJob.class)
                .withIdentity("congBoDiemChuanJob", "group1")
                .build();
        // Tạo trigger với cron expression
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("triggerCongBoDiem", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
        // Lập lịch công việc với trigger
        try {
            scheduler.scheduleJob(job, trigger);
            new ConfirmDialog(null, "Thành công", "Tạo lịch công bố điểm thành công");
            jChonThoiGian.dispose();
        } catch (SchedulerException e) {
            e.printStackTrace();
            return;
        }
    }//GEN-LAST:event_jBtnXacNhanActionPerformed

    private void jCapNhatDiemThiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCapNhatDiemThiActionPerformed
        try {
            admissionController.randomTotalScoreStudentByBlock(LocalDate.now().getYear());
            new ConfirmDialog(null, "Thành công", "Lấy điểm thi của sinh viên thành công");
            settingController.updateSetting(SettingConstant.CALL_TOTAL_SCORE_STUDENT, "true");
        } catch(Exception e) {
            e.printStackTrace();
            return;
        } 
    }//GEN-LAST:event_jCapNhatDiemThiActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton jBtnXacNhan;
    private JButton jCapNhatDiemThi;
    private JTextField jChiTieu;
    private JDialog jChonThoiGian;
    private JButton jClear;
    private JButton jCongBoDiem;
    private JTextField jDiemChuan;
    private JButton jExport;
    private JComboBox<String> jFilterYear;
    private JTextField jId;
    private JButton jImport;
    private JTextField jKhoi;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JTextField jMaNganh;
    private JPanel jPanel1;
    private com.admission.components.search.Search jSearch;
    private JButton jSua;
    private JTextField jTenNganh;
    private com.github.lgooddatepicker.components.DateTimePicker jThoiGianCBD;
    private JButton jXoa;
    private com.admission.components.border.PanelBorder panelBorder1;
    private com.admission.components.border.PanelBorder panelBorder2;
    private JScrollPane spTable;
    private JTable tableMajor;
    // End of variables declaration//GEN-END:variables
}
