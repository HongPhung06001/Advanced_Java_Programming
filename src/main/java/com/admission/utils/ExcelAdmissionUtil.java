package com.admission.utils;

import com.admission.controller.AdmissionController;
import com.admission.dto.AdmissionResultDTO;
import com.admission.dto.AdmissionResultRequest;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class ExcelAdmissionUtil {

    private static final AdmissionController admissionController = new AdmissionController();

    public static void exportFile(AdmissionResultRequest request, String excelFilePath) {
        // Tạo workbook mới
        try (Workbook workbook = new XSSFWorkbook()) {
            // Tạo một trang tính mới
            Sheet sheet = workbook.createSheet("Kết quả tuyển sinh");

            //Tạo header
            writeHeader(sheet);

            //Tạo nội dung
            List<AdmissionResultDTO> admissionResultDTOs = admissionController.getAdmissionResult(request);
            writeContent(sheet, admissionResultDTOs);

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

    public static void exportFileAll(AdmissionResultRequest request, String excelFilePath) {
        // Tạo workbook mới
        try (Workbook workbook = new XSSFWorkbook()) {
            // Tạo một trang tính mới
            Sheet sheet = workbook.createSheet("Kết quả tuyển sinh");

            //Tạo header
            writeHeader(sheet);

            //Tạo nội dung
            List<AdmissionResultDTO> admissionResultDTOs = admissionController.getAdmissionResultByYearAndStatus(
                    request.getYear(), request.getStatusAdmission());
            writeContent(sheet, admissionResultDTOs);

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
        String[] headers = {"STT", "Mã ngành", "Tên ngành", "Họ đệm", "Tên", "SBD",
            "Số CMND/CCCD", "Email", "SĐT", "Giới tính", "Địa chỉ", "Nguyện vọng", "Khối", "Tổng điểm", "Trạng thái"};
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

    private static void writeContent(Sheet sheet, List<AdmissionResultDTO> admissionResultDTOs) {
        Font contentFont = sheet.getWorkbook().createFont();
        contentFont.setFontName("Times New Roman");
        contentFont.setFontHeightInPoints((short) 13);
        contentFont.setColor(IndexedColors.BLACK.getIndex());
        CellStyle contentCellStyle = sheet.getWorkbook().createCellStyle();
        contentCellStyle.setFont(contentFont);

        // Đặt các giá trị dữ liệu từ danh sách kết quả vào các ô
        int rowNum = 1;
        int size = admissionResultDTOs.size();
        for (int i = 0; i < size; i++) {
            AdmissionResultDTO result = admissionResultDTOs.get(i);
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(result.getMajorCode());
            row.createCell(2).setCellValue(result.getMajorName());
            row.createCell(3).setCellValue(result.getLastName());
            row.createCell(4).setCellValue(result.getFirstName());
            row.createCell(5).setCellValue(result.getOrderNumber());
            row.createCell(6).setCellValue(result.getCitizenIdentityNumber());
            row.createCell(7).setCellValue(result.getEmail());
            row.createCell(8).setCellValue(result.getPhoneNumber());
            row.createCell(9).setCellValue(result.getGender());
            row.createCell(10).setCellValue(result.getAddress());
            row.createCell(11).setCellValue(result.getOrders());
            row.createCell(12).setCellValue(result.getBlock());

            BigDecimal value = new BigDecimal(result.getTotalScore());
            BigDecimal roundedValue = value.setScale(2, BigDecimal.ROUND_HALF_UP);
            CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
            cellStyle.setDataFormat(sheet.getWorkbook().getCreationHelper().createDataFormat().getFormat("0.00"));
            row.createCell(13).setCellValue(roundedValue.doubleValue());
            row.getCell(13).setCellStyle(cellStyle);
            if (result.getStatus().equals(2)) {
                row.createCell(14).setCellValue("Đậu");
            } else if (result.getStatus().equals(3)) {
                row.createCell(14).setCellValue("Trượt");
            } else {
                row.createCell(14, CellType.BLANK);
            }
            // Áp dụng kiểu chữ nội dung cho hàng dữ liệu
            int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
            for (int j = 0; j < numberOfColumn; j++) {
                row.getCell(j).setCellStyle(contentCellStyle);
            }
        }
    }

}
