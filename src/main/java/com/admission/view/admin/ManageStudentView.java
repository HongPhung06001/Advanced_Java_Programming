package com.admission.view.admin;

import com.admission.components.dialog.ConfirmDialog;
import com.admission.components.table.TableHeader;
import com.admission.controller.MajorDetailController;
import com.admission.controller.StudentController;
import com.admission.dto.CommonResponse;
import com.admission.dto.StudentDTO;
import com.admission.event.EventButtonSearchClick;
import com.admission.utils.DateUtils;
import com.admission.utils.ResourceUtil;
import org.apache.commons.lang3.ObjectUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageStudentView extends javax.swing.JPanel {

    private final StudentController studentController;

    private final MajorDetailController majorDetailController;

    private final Map<Integer, String> studentAvatarUrl = new HashMap<>();

    private File currentAvatar;

    public ManageStudentView() {
        initComponents();
        this.studentController = new StudentController();
        this.majorDetailController = new MajorDetailController();

        spTable.getVerticalScrollBar().setUnitIncrement(9);
        spTable.getHorizontalScrollBar().setUnitIncrement(9);
        spTable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        tableStudent.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableStudent.setTableHeader(TableHeader.customTableHeader(tableStudent.getTableHeader()));
        loadFilterYearComboBox();
        loadStudents(jSearch.getKeyword().getText());
        handleSelectCellTable();

        jSearch.addEventButtonSearchClick(new EventButtonSearchClick() {
            @Override
            public void onClick(String text) {
                loadStudents(text);
            }
        });
    }

    private void loadStudents(String keyword) {
        DefaultTableModel model = (DefaultTableModel) tableStudent.getModel();
        Integer year = Integer.valueOf(jFilterYear.getSelectedItem().toString());
        List<StudentDTO> students = studentController.getStudents(year, keyword);
        int numberRowsOfTable = students.size();
        model.setRowCount(numberRowsOfTable);
        for (int i = 0; i < numberRowsOfTable; i++) {
            model.setValueAt(students.get(i).getId(), i, 0);
            model.setValueAt(students.get(i).getLastName(), i, 1);
            model.setValueAt(students.get(i).getFirstName(), i, 2);
            model.setValueAt(students.get(i).getOrderNumber(), i, 3);
            model.setValueAt(students.get(i).getCitizenIdentityNumber(), i, 4);
            model.setValueAt(students.get(i).getCitizenIdentityIssueDate(), i, 5);
            model.setValueAt(students.get(i).getCitizenIdentityIssuedBy(), i, 6);
            model.setValueAt(students.get(i).getEmail(), i, 7);
            model.setValueAt(students.get(i).getPhoneNumber(), i, 8);
            model.setValueAt(students.get(i).getGender(), i, 9);
            model.setValueAt(students.get(i).getBirthDay(), i, 10);
            model.setValueAt(students.get(i).getEthnic(), i, 11);
            model.setValueAt(students.get(i).getAddress(), i, 12);
            studentAvatarUrl.put(students.get(i).getId(), students.get(i).getAvatar());
        }
    }

    private void loadFilterYearComboBox() {
        Integer minYear = majorDetailController.getYearMinMajor();
        Integer maxYear = LocalDate.now().getYear();
        for (int i = maxYear; i >= minYear; i--) {
            jFilterYear.addItem(String.valueOf(i));
        }
    }

    private void handleSelectCellTable() {
        ListSelectionModel cellSelectionModel = tableStudent.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = tableStudent.getSelectedRow();
                if (selectedRow != -1) {
                    Integer studentId = Integer.valueOf(tableStudent.getValueAt(selectedRow, 0).toString());
                    jId.setText(studentId.toString());
                    jHoDem.setText(tableStudent.getValueAt(selectedRow, 1).toString());
                    jTen.setText(tableStudent.getValueAt(selectedRow, 2).toString());
                    if (ObjectUtils.isNotEmpty(tableStudent.getValueAt(selectedRow, 3))) {
                        jSBD.setText(tableStudent.getValueAt(selectedRow, 3).toString());
                    } else {
                        jSBD.setText(null);
                    }
                    jCMND.setText(tableStudent.getValueAt(selectedRow, 4).toString());
                    Date ngayCap = DateUtils.asDate((LocalDate) tableStudent.getValueAt(selectedRow, 5));
                    jNgayCap.setDate(ngayCap);
                    jNguoiCap.setText(tableStudent.getValueAt(selectedRow, 6).toString());
                    jEmail.setText(tableStudent.getValueAt(selectedRow, 7).toString());
                    if (ObjectUtils.isNotEmpty(tableStudent.getValueAt(selectedRow, 8))) {
                        jPhone.setText(tableStudent.getValueAt(selectedRow, 8).toString());
                    } else {
                        jPhone.setText(null);
                    }
                    jGioiTinh.setSelectedItem(tableStudent.getValueAt(selectedRow, 9).toString());
                    if (ObjectUtils.isNotEmpty(tableStudent.getValueAt(selectedRow, 10))) {
                        Date ngaySinh = DateUtils.asDate((LocalDate) tableStudent.getValueAt(selectedRow, 10));
                        jNgaySinh.setDate(ngaySinh);
                    } else {
                        jNgaySinh.setDate(null);
                    }
                    if (ObjectUtils.isNotEmpty(tableStudent.getValueAt(selectedRow, 11))) {
                        jDanToc.setText(tableStudent.getValueAt(selectedRow, 11).toString());
                    } else {
                        jDanToc.setText(null);
                    }
                    if (ObjectUtils.isNotEmpty(tableStudent.getValueAt(selectedRow, 12))) {
                        jDiaChi.setText(tableStudent.getValueAt(selectedRow, 12).toString());
                    } else {
                        jDiaChi.setText(null);
                    }
                    String avatarUrl = studentAvatarUrl.get(studentId);
                    if (ObjectUtils.isNotEmpty(avatarUrl)) {
                        ImageIcon imageIcon = ResourceUtil.getAvatar(avatarUrl);
                        imageIcon.getImage().flush();
                        jAvatar.setIcon(ResourceUtil.getAvatar(avatarUrl));
                    } else {
                        jAvatar.setIcon(null);
                    }
                }
            }
        });
    }

    private boolean validateInput() {
        if (ObjectUtils.isEmpty(jHoDem.getText())) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập họ đệm!");
            return false;
        }
        if (ObjectUtils.isEmpty(jTen.getText())) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập họ tên!");
            return false;
        }
        if (ObjectUtils.isEmpty(jHoDem.getText())) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập họ đệm!");
            return false;
        }
        if (ObjectUtils.isEmpty(jCMND.getText())) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số CMND/CCCD!");
            return false;
        }
        if (ObjectUtils.isEmpty(jNguoiCap.getText())) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập người cấp CMND/CCCD!");
            return false;
        }
        if (ObjectUtils.isEmpty(jNgayCap.getDate())) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập ngày cấp CMND/CCCD!");
            return false;
        }
        if (ObjectUtils.isEmpty(jEmail.getText())) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập email!");
            return false;
        }
        if (ObjectUtils.isEmpty(jPhone.getText())) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số điện thoại!");
            return false;
        }
        return true;
    }

    private void clearInput() {
        jId.setText(null);
        jHoDem.setText(null);
        jTen.setText(null);
        jSBD.setText(null);
        jCMND.setText(null);
        jNgayCap.setDate(null);
        jNguoiCap.setText(null);
        jEmail.setText(null);
        jPhone.setText(null);
        jGioiTinh.setSelectedItem("Nam");
        jNgaySinh.setDate(null);
        jDanToc.setText(null);
        jDiaChi.setText(null);
        jAvatar.setIcon(null);
        currentAvatar = null;
    }

    private StudentDTO createStudentDTO() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setLastName(jHoDem.getText());
        studentDTO.setFirstName(jTen.getText());
        studentDTO.setCitizenIdentityNumber(jCMND.getText());
        LocalDate ngayCap = DateUtils.asLocalDate(jNgayCap.getDate());
        studentDTO.setCitizenIdentityIssueDate(ngayCap);
        studentDTO.setCitizenIdentityIssuedBy(jNguoiCap.getText());
        studentDTO.setEmail(jEmail.getText());
        studentDTO.setGender(jGioiTinh.getSelectedItem().toString());
        if (ObjectUtils.isNotEmpty(jSBD.getText())) {
            studentDTO.setOrderNumber(jSBD.getText());
        }
        if (ObjectUtils.isNotEmpty(jPhone.getText())) {
            studentDTO.setPhoneNumber(jPhone.getText());
        }
        if (ObjectUtils.isNotEmpty(jDanToc.getText())) {
            studentDTO.setEthnic(jDanToc.getText());
        }
        if (ObjectUtils.isNotEmpty(jDiaChi.getText())) {
            studentDTO.setAddress(jDiaChi.getText());
        }
        if (ObjectUtils.isNotEmpty(jNgaySinh.getDate())) {
            LocalDate ngaySinh = DateUtils.asLocalDate(jNgaySinh.getDate());
            studentDTO.setBirthDay(ngaySinh);
        }
        if (currentAvatar != null) {
            String fileName = jCMND.getText();
            studentDTO.setAvatar(ResourceUtil.saveFile(fileName, "upload/avatar", currentAvatar));
        } else {
            if (ObjectUtils.isNotEmpty(jId.getText())) {
                String avatarUrl = studentAvatarUrl.get(Integer.valueOf(jId.getText()));
                if (ObjectUtils.isNotEmpty(avatarUrl)) {
                    studentDTO.setAvatar(avatarUrl);
                }
            }
        }
        return studentDTO;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorder1 = new com.admission.components.border.PanelBorder();
        jLabel2 = new javax.swing.JLabel();
        jId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jCMND = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jEmail = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jHoDem = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jNguoiCap = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPhone = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTen = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jDanToc = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jDiaChi = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jThem = new javax.swing.JButton();
        jSua = new javax.swing.JButton();
        jXoa = new javax.swing.JButton();
        jClear = new javax.swing.JButton();
        jSearch = new com.admission.components.search.Search();
        jFilterYear = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jNgayCap = new com.toedter.calendar.JDateChooser();
        jGioiTinh = new javax.swing.JComboBox<>();
        spTable = new JScrollPane();
        tableStudent = new JTable();
        jPanel2 = new javax.swing.JPanel();
        jAvatar = new javax.swing.JLabel();
        jUpload = new javax.swing.JButton();
        jNgaySinh = new com.toedter.calendar.JDateChooser();
        jLabel15 = new javax.swing.JLabel();
        jSBD = new javax.swing.JTextField();

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel2.setText("ID");

        jId.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jId.setFocusable(false);

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel3.setText("CMND/CCCD");

        jLabel4.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel4.setText("Email");

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel5.setText("Họ đệm");

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel6.setText("Người cấp");

        jLabel7.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel7.setText("SĐT");

        jLabel8.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel8.setText("Tên");

        jLabel9.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel9.setText("Ngày cấp");

        jLabel12.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel12.setText("SBD");

        jLabel13.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel13.setText("Dân tộc");

        jLabel14.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel14.setText("Giới tính");

        jLabel10.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel10.setText("Địa chỉ");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jThem.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jThem.setIcon(new ImageIcon(getClass().getResource("/images/icon/Add.png"))); // NOI18N
        jThem.setText("Thêm");
        jThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jThemActionPerformed(evt);
            }
        });

        jSua.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jSua.setIcon(new ImageIcon(getClass().getResource("/images/icon/Edit.png"))); // NOI18N
        jSua.setText("Sửa");
        jSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSuaActionPerformed(evt);
            }
        });

        jXoa.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jXoa.setIcon(new ImageIcon(getClass().getResource("/images/icon/Delete.png"))); // NOI18N
        jXoa.setText("Xóa");
        jXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXoaActionPerformed(evt);
            }
        });

        jClear.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jClear.setIcon(new ImageIcon(getClass().getResource("/images/icon/Refresh.png"))); // NOI18N
        jClear.setText("Làm mới");
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 19, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addComponent(jFilterYear, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSua)
                    .addComponent(jThem))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jClear)
                    .addComponent(jXoa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jFilterYear, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 22)); // NOI18N
        jLabel1.setText("Quản lý sinh viên");

        jNgayCap.setBorder(null);
        jNgayCap.setDateFormatString("yyyy-MM-dd");
        jNgayCap.setMinimumSize(new java.awt.Dimension(13, 28));
        jNgayCap.setPreferredSize(new java.awt.Dimension(13, 28));

        jGioiTinh.setBackground(new java.awt.Color(255, 255, 255));
        jGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));
        jGioiTinh.setMinimumSize(new java.awt.Dimension(13, 28));
        jGioiTinh.setPreferredSize(new java.awt.Dimension(13, 28));

        tableStudent.setFont(new java.awt.Font("sansserif", 0, 13)); // NOI18N
        tableStudent.setModel(new DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Họ đệm", "Tên", "SBD", "CMND/CCCD", "Ngày cấp", "Người cấp", "Email", "SĐT", "Giới tính", "Ngày sinh", "Dân tộc", "Địa chỉ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableStudent.setRowHeight(30);
        spTable.setViewportView(tableStudent);
        if (tableStudent.getColumnModel().getColumnCount() > 0) {
            tableStudent.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableStudent.getColumnModel().getColumn(1).setPreferredWidth(110);
            tableStudent.getColumnModel().getColumn(2).setPreferredWidth(70);
            tableStudent.getColumnModel().getColumn(3).setPreferredWidth(60);
            tableStudent.getColumnModel().getColumn(4).setPreferredWidth(150);
            tableStudent.getColumnModel().getColumn(5).setPreferredWidth(80);
            tableStudent.getColumnModel().getColumn(6).setPreferredWidth(180);
            tableStudent.getColumnModel().getColumn(7).setPreferredWidth(150);
            tableStudent.getColumnModel().getColumn(8).setPreferredWidth(100);
            tableStudent.getColumnModel().getColumn(9).setPreferredWidth(60);
            tableStudent.getColumnModel().getColumn(10).setPreferredWidth(80);
            tableStudent.getColumnModel().getColumn(11).setPreferredWidth(70);
            tableStudent.getColumnModel().getColumn(12).setPreferredWidth(250);
        }

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jAvatar.setText("                      Ảnh 3x4");
        jAvatar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jUpload.setText("Chọn ảnh");
        jUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jUploadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 40, Short.MAX_VALUE)
                .addComponent(jAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jUpload)
                    .addComponent(jAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        jNgaySinh.setDateFormatString("yyyy-MM-dd");
        jNgaySinh.setMinimumSize(new java.awt.Dimension(13, 28));
        jNgaySinh.setPreferredSize(new java.awt.Dimension(13, 28));

        jLabel15.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel15.setText("Ngày sinh");

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spTable)
                .addContainerGap())
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(356, 356, 356)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(328, 328, 328))
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8)
                    .addComponent(jLabel12)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTen)
                    .addComponent(jHoDem)
                    .addComponent(jId)
                    .addComponent(jSBD, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(70, 70, 70)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9)
                    .addComponent(jLabel13))
                .addGap(20, 20, 20)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jNguoiCap)
                    .addComponent(jDanToc)
                    .addComponent(jCMND)
                    .addComponent(jNgayCap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(70, 70, 70)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4)
                    .addComponent(jLabel10))
                .addGap(20, 20, 20)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPhone, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDiaChi, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jEmail, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jGioiTinh, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCMND, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jHoDem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jNguoiCap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jNgayCap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDanToc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSBD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spTable, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
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
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jUploadActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn ảnh");
        fileChooser.setPreferredSize(new java.awt.Dimension(700, 500));
        FileNameExtensionFilter imageFilter
                = new FileNameExtensionFilter("Image files (*.png, *.jpg, *.jpeg)", "jpg", "jpeg", "png");
        fileChooser.setFileFilter(imageFilter);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String extension = ResourceUtil.getFileExtension(selectedFile);
            if (extension == null || !ResourceUtil.isImageExtension(extension)) {
                JOptionPane.showMessageDialog(this, "File không hợp lệ! Vui lòng chọn file ảnh");
                return;
            }
            currentAvatar = fileChooser.getSelectedFile();
            jAvatar.setIcon(new ImageIcon(selectedFile.getAbsolutePath()));
        }
    }//GEN-LAST:event_jUploadActionPerformed

    private void jClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jClearActionPerformed
        clearInput();
        jSearch.getKeyword().setText(null);
        loadStudents(jSearch.getKeyword().getText());
    }//GEN-LAST:event_jClearActionPerformed

    private void jThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jThemActionPerformed
        if (!validateInput()) {
            return;
        }
        StudentDTO studentDTO = createStudentDTO();
        try {
            CommonResponse commonResponse = studentController.createStudent(studentDTO);
            if (commonResponse.getStatus().equals(Boolean.TRUE)) {
                new ConfirmDialog(null, "Thêm thành công", commonResponse.getMessage());
                loadStudents(jSearch.getKeyword().getText());
            } else {
                JOptionPane.showMessageDialog(null, commonResponse.getMessage());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
        }
    }//GEN-LAST:event_jThemActionPerformed

    private void jSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSuaActionPerformed
        if (!validateInput()) {
            return;
        }
        StudentDTO studentDTO = createStudentDTO();
        studentDTO.setId(Integer.valueOf(jId.getText()));
        try {
            CommonResponse commonResponse = studentController.updateStudent(studentDTO);
            if (commonResponse.getStatus().equals(Boolean.TRUE)) {
                new ConfirmDialog(null, "Cập nhật thành công", commonResponse.getMessage());
                loadStudents(jSearch.getKeyword().getText());
            } else {
                JOptionPane.showMessageDialog(null, commonResponse.getMessage());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
        }
    }//GEN-LAST:event_jSuaActionPerformed

    private void jXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXoaActionPerformed
        int option = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa sinh viên này?", "Xác nhận xoá", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            try {
                CommonResponse commonResponse = studentController.deleteStudentById(Integer.valueOf(jId.getText()));
                if (commonResponse.getStatus().equals(Boolean.TRUE)) {
                    new ConfirmDialog(null, "Xóa thành công", commonResponse.getMessage());
                    clearInput();
                    loadStudents(jSearch.getKeyword().getText());
                } else {
                    JOptionPane.showMessageDialog(null, commonResponse.getMessage());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Hệ thống đã xảy ra lỗi. Vui lòng quay lại sau!");
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jXoaActionPerformed

    private void jFilterYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFilterYearActionPerformed
        loadStudents(jSearch.getKeyword().getText());
    }//GEN-LAST:event_jFilterYearActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jAvatar;
    private javax.swing.JTextField jCMND;
    private javax.swing.JButton jClear;
    private javax.swing.JTextField jDanToc;
    private javax.swing.JTextField jDiaChi;
    private javax.swing.JTextField jEmail;
    private javax.swing.JComboBox<String> jFilterYear;
    private javax.swing.JComboBox<String> jGioiTinh;
    private javax.swing.JTextField jHoDem;
    private javax.swing.JTextField jId;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private com.toedter.calendar.JDateChooser jNgayCap;
    private com.toedter.calendar.JDateChooser jNgaySinh;
    private javax.swing.JTextField jNguoiCap;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jPhone;
    private javax.swing.JTextField jSBD;
    private com.admission.components.search.Search jSearch;
    private javax.swing.JButton jSua;
    private javax.swing.JTextField jTen;
    private javax.swing.JButton jThem;
    private javax.swing.JButton jUpload;
    private javax.swing.JButton jXoa;
    private com.admission.components.border.PanelBorder panelBorder1;
    private JScrollPane spTable;
    private JTable tableStudent;
    // End of variables declaration//GEN-END:variables
}
