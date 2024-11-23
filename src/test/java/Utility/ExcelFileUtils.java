package Utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtils {
	public static FileInputStream fi;
	public static Workbook wb;
	public static String inputFilePath = "FileInput/DataEngine.xlsx";
	public static String outputFilePath = "FileOutput/DataEngine_Result.xlsx";
	
	
	public ExcelFileUtils(String inputfilePath) throws Throwable {
		fi = new FileInputStream(inputfilePath);
		wb = WorkbookFactory.create(fi);
	}
	
//	count Rows
	public int rowCount(String SheetName) {
		return wb.getSheet(SheetName).getLastRowNum();
	}
	
//	getCellData
	public String getCellData(String SheetName, int row, int col) {
		String data = null;
		if(wb.getSheet(SheetName).getRow(row).getCell(col).getCellType()==CellType.NUMERIC) {
			int val = (int)wb.getSheet(SheetName).getRow(row).getCell(col).getNumericCellValue();
			data=String.valueOf(val);		
		}
		else {
			data=wb.getSheet(SheetName).getRow(row).getCell(col).getStringCellValue();
		}
		return data;
	}
//	setCellData
	public void setCellData(String SheetName, int row, int col, String Status, String ouputFilePath) throws Throwable {
		wb.getSheet(SheetName).getRow(row).createCell(col).setCellValue(Status);
		if(Status.equalsIgnoreCase("Pass")) {
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			font.setItalic(true);
			style.setFont(font);
			wb.getSheet(SheetName).getRow(row).getCell(col).setCellStyle(style);
		}
		else if(Status.equalsIgnoreCase("Fail")) {
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			font.setItalic(true);
			style.setFont(font);
			wb.getSheet(SheetName).getRow(row).getCell(col).setCellStyle(style);
		}
		else {
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			font.setItalic(true);
			style.setFont(font);
			wb.getSheet(SheetName).getRow(row).getCell(col).setCellStyle(style);
		}
		FileOutputStream fo = new FileOutputStream(ouputFilePath);
		wb.write(fo);
	}

//	public static void main(String[] args) throws Throwable {
//		ExcelFileUtils obj = new ExcelFileUtils(inputFilePath);
//		int rowsQty=obj.rowCount(ExcelFileUtils.wb.getSheetAt(0).getSheetName());
//		String sheetName=ExcelFileUtils.wb.getSheetAt(0).getSheetName();
//		System.out.println("Number of rows in Sheet-'"+sheetName+"': "+rowsQty+" No's");
//		System.out.println("Feature: "+obj.getCellData(sheetName, 1, 1));
//		obj.setCellData(sheetName, 1, 4, "Blocked", "FileOutput/DataEngine_Result.xlsx");
//	}
}
