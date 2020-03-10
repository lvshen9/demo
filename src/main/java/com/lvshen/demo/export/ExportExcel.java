package com.lvshen.demo.export;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/6/28 10:03
 * @since JDK 1.8
 */
@Slf4j
public class ExportExcel {

    public static void main(String[] args) {
        String exportPath = "E:\\work\\ExportExcel.xlsx";
        exportExcel(exportPath);
        log.info("导出成功!!!");
    }

	/**
	 * 生成Excel
	 */
	public static void exportExcel(String exportPath) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("测试");
		for (int i = 0; i < 9; i++) {
			sheet.setColumnWidth(i, 4300);
		}
		setTitleStyle(sheet, workbook);

		setExcelFooterName("test", 0, workbook);
		exportOutPutExcel(exportPath, workbook);

	}

	/**
	 * 单元格 标题样式
	 * 
	 * @param workbook
	 */
	public static void setTitleStyle(XSSFSheet sheet, XSSFWorkbook workbook) {
		// 单元格样式
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 上下居中
		// 标题样式
		XSSFFont titleFont = workbook.createFont();
		titleFont.setFontHeight(24);
		titleFont.setBold(true);
		CellStyle titleCellStyle = workbook.createCellStyle();
		titleCellStyle.setBorderTop(BorderStyle.THIN);
		titleCellStyle.setBorderBottom(BorderStyle.THIN);
		titleCellStyle.setBorderLeft(BorderStyle.THIN);
		titleCellStyle.setBorderRight(BorderStyle.THIN);
		titleCellStyle.setTopBorderColor(HSSFColor.BLACK.index);
		titleCellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		titleCellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		titleCellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		titleCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 上下居中
		titleCellStyle.setFont(titleFont);

		setRowAndCellTitle(sheet, cellStyle, titleCellStyle);
	}

	public static void setRowAndCellTitle(XSSFSheet sheet, CellStyle cellStyle, CellStyle titleCellStyle) {
		// 主 标题 在这里插入主标题
		Row titleRow;
		Cell titleCell;
		sheet.addMergedRegion(new CellRangeAddress((short) 0, (short) 2, (short) 0, (short) 8));
		for (int i = 0; i <= 2; i++) {
			titleRow = sheet.createRow(i);
			for (int j = 0; j < 9; j++) {
				titleCell = titleRow.createCell(j);
				titleCell.setCellType(CellType.STRING);
				titleCell.setCellStyle(titleCellStyle);
				titleCell.setCellValue("POI导出Excel测试");
			}
		}

		// 列 标题 在这里插入标题
		Row rowLabel;
		Cell cellLabel;
		for (int i = 3; i < 4; i++) {
			rowLabel = sheet.createRow(i);
			for (int j = 0; j < 9; j++) {
				cellLabel = rowLabel.createCell(j);
				cellLabel.setCellType(CellType.STRING);
				cellLabel.setCellStyle(cellStyle);
				cellLabel.setCellValue("测试标题列【" + (j + 1) + "】");
			}
		}

		// 列 数据 在这里插入数据
		Row rowCheck;
		Cell cellCheck;
		for (int i = 3; i < 2000; i++) {
			rowCheck = sheet.createRow((i + 1));
			for (int j = 0; j < 9; j++) {
				cellCheck = rowCheck.createCell(j);
				cellCheck.setCellType(CellType.STRING);
				cellCheck.setCellStyle(cellStyle);
				cellCheck.setCellValue("测试 - 0" + (i - 2));
			}
		}
	}

	/**
	 * 设置Excel页脚
	 */
	public static void setExcelFooterName(String customExcelFooterName, int setExcelFooterNumber, XSSFWorkbook wb) {
		wb.setSheetName(setExcelFooterNumber, customExcelFooterName);
	}

	/**
	 * 输出流 导出Excel到桌面
	 */
	public static void exportOutPutExcel(String exportPositionPath, XSSFWorkbook wb) {
		try {
			File file = new File(exportPositionPath);
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			wb.write(fileOutputStream);
			fileOutputStream.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
