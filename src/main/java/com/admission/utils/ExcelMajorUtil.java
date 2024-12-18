package com.admission.utils;

import com.admission.controller.MajorDetailController;
import com.admission.dto.MajorDetailDTO;
import com.admission.dto.ReadFileExcelResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ExcelMajorUtil {

    public static void exportFileExample(MajorDetailController majorDetailController, String excelFilePath) {
        // Tạo workbook mới
        try (Workbook workbook = new XSSFWorkbook()) {
            // Tạo một trang tính mới
            Sheet sheet = workbook.createSheet("Major List");

            //Tạo header
            writeHeader(sheet);

            //Tạo nội dung
            writeContent(sheet, majorDetailController);

            // Tự động điều chỉnh kích thước cột cho phù hợp với nội dung
            int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
            for (int i = 0; i < numberOfColumn; i++) {
                sheet.autoSizeColumn(i);
            }

            // Ghi workbook vào file
            try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeHeader(Sheet sheet) {
        // Tạo hàng đầu tiên (tiêu đề)
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID ngành", "Mã ngành", "Tên ngành", "Khối", "Chỉ tiêu", "Điểm chuẩn"};

        // Tạo kiểu chữ cho header
        Font headerFont = sheet.getWorkbook().createFont();
        headerFont.setFontName("Times New Roman");
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 13);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        CellStyle headerCellStyle = sheet.getWorkbook().createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Đặt các giá trị tiêu đề vào các ô trong hàng đầu tiên
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerCellStyle);
        }
    }

    private static void writeContent(Sheet sheet, MajorDetailController majorDetailController) {
        Font contentFont = sheet.getWorkbook().createFont();
        contentFont.setFontName("Times New Roman");
        contentFont.setFontHeightInPoints((short) 13);
        contentFont.setColor(IndexedColors.BLACK.getIndex());
        CellStyle contentCellStyle = sheet.getWorkbook().createCellStyle();
        contentCellStyle.setFont(contentFont);

        // Đặt các giá trị dữ liệu từ danh sách Major vào các ô
        int rowNum = 1;
        List<MajorDetailDTO> majorDetailDTOs = majorDetailController.getMajorDetailsForAdmin(LocalDate.now().getYear(), null);
        for (MajorDetailDTO majorDetail : majorDetailDTOs) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(majorDetail.getMajorId());
            row.createCell(1).setCellValue(majorDetail.getCode());
            row.createCell(2).setCellValue(majorDetail.getName());
            row.createCell(3, CellType.BLANK);
            if (ObjectUtils.isNotEmpty(majorDetail.getBlocks())) {
                row.createCell(3).setCellValue(majorDetail.getBlocks());
            }
            row.createCell(4, CellType.BLANK);
            if (ObjectUtils.isNotEmpty(majorDetail.getAmountStudentReceived())) {
                row.createCell(4).setCellValue(majorDetail.getAmountStudentReceived());
            }
            row.createCell(5, CellType.BLANK);
            if (ObjectUtils.isNotEmpty(majorDetail.getBenchMark())) {
                row.createCell(5).setCellValue(majorDetail.getBenchMark());
            }
            // Áp dụng kiểu chữ nội dung cho hàng dữ liệu
            int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
            for (int i = 0; i < numberOfColumn; i++) {
                row.getCell(i).setCellStyle(contentCellStyle);
            }
        }
    }

    public static ReadFileExcelResponse<List<MajorDetailDTO>> readFile(String excelFilePath) {
        List<MajorDetailDTO> majorDetailDTOs = new LinkedList<>();
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(excelFilePath))) {
            // Lấy trang tính đầu tiên
            Sheet sheet = workbook.getSheetAt(0);

            // Đọc dữ liệu từ các hàng
            for (Row row : sheet) {
                // Bỏ qua hàng đầu tiên (tiêu đề)
                if (row.getRowNum() == 0) {
                    continue;
                }

                // Đọc giá trị từ các ô
                int idNganh = (int) row.getCell(0).getNumericCellValue();
                String maNganh = row.getCell(1).getStringCellValue();
                String tenNganh = row.getCell(2).getStringCellValue();
                String khoi = row.getCell(3).getStringCellValue();
                
                Integer chiTieu = null;
                Cell cell4 = row.getCell(4);
                if(cell4 != null && cell4.getCellType() != CellType.BLANK){
                    chiTieu = (int) cell4.getNumericCellValue();
                }
                
                Float diemChuan = null;
                Cell cell5 = row.getCell(5);
                if(cell5 != null && cell5.getCellType() != CellType.BLANK){
                    diemChuan = (float) cell5.getNumericCellValue();
                }

                MajorDetailDTO majorDetailDTO = new MajorDetailDTO();
                majorDetailDTO.setMajorId(idNganh);
                majorDetailDTO.setCode(maNganh);
                majorDetailDTO.setName(tenNganh);
                majorDetailDTO.setBlocks(khoi);
                majorDetailDTO.setAmountStudentReceived(chiTieu);
                majorDetailDTO.setBenchMark(diemChuan);
                majorDetailDTOs.add(majorDetailDTO);
            }
            return new ReadFileExcelResponse<>(Boolean.TRUE, "", new ArrayList<>(majorDetailDTOs));
        } catch (Exception e) {
            e.printStackTrace();
            return new ReadFileExcelResponse<>(Boolean.FALSE, "Định dạng file không hợp lệ!", Collections.emptyList());
        }
    }

}
