package com.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class ExcelHandler {

	// item to hold the path of workbook
	String excelWorkBook;

	// constructor to hold the current object
	public ExcelHandler(String workBookPath) {
		this.excelWorkBook = workBookPath;
	}

	// method to read a single cell from external source(i.e. excel)
	public String readFromExcel(String sheetName, int rowNum, int colNum)
			throws InvalidFormatException, IOException {
		String retVal;
		FileInputStream fileinputstream = new FileInputStream(excelWorkBook);
		Workbook workbook = WorkbookFactory.create(fileinputstream);
		Sheet sheet = workbook.getSheet(sheetName);
		Row row = sheet.getRow(rowNum - 1);
		Cell cell = row.getCell(colNum - 1);
		retVal = cell.getStringCellValue();
		fileinputstream.close();
		return retVal;
	}

	// method to get the total number of rows present in a sheet
	public int getRowCount(String sheetName) throws InvalidFormatException,
			IOException {
		int retVal;
		FileInputStream fileinputstream = new FileInputStream(excelWorkBook);
		Workbook wb = WorkbookFactory.create(fileinputstream);
		Sheet s = wb.getSheet(sheetName);
		retVal = s.getLastRowNum() + 1;
		fileinputstream.close();
		return retVal;
	}
	 
	// method to write the result back to the excel.
	public void writeExcelInfo(String sheetName, int rowNum, int colNum,
			String data) {
		try {
			FileInputStream fileinputstream = new FileInputStream(excelWorkBook);
			Workbook workbook = WorkbookFactory.create(fileinputstream);
			Sheet sheet = workbook.getSheet(sheetName);
			Row row = sheet.getRow(rowNum - 1);
			Cell cell = row.createCell(colNum - 1);
			cell.setCellType(CellType.STRING);
			cell.setCellValue(data);
			
			// Write the output to a file
			FileOutputStream fileOut = new FileOutputStream(excelWorkBook);
			workbook.write(fileOut);
			fileOut.close();
			fileinputstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// method to write the result back to the excel.
		public void writeExcelGlobalUtils(String sheetName, int rowNum, int colNum, String data) {
			try {
				Row row = null;
				FileInputStream fileinputstream = new FileInputStream(excelWorkBook);
				Workbook workbook = WorkbookFactory.create(fileinputstream);
				Sheet sheet = workbook.getSheet(sheetName);
				row = sheet.getRow(rowNum-1);
				if(row==null){
					row = sheet.createRow(rowNum-1);
				}
				Cell cell = row.createCell(colNum - 1);
				cell.setCellType(CellType.STRING);
				cell.setCellValue(data);
				// Write the output to a file
				FileOutputStream fileOut = new FileOutputStream(excelWorkBook);
				workbook.write(fileOut);
				fileOut.close();
				fileinputstream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public String[][] getTableData(String sheetName) throws Exception {

			FileInputStream in = new FileInputStream(excelWorkBook);
			Workbook wb = WorkbookFactory.create(in);
			Sheet sheet = wb.getSheet(sheetName);

			int firstRow = sheet.getFirstRowNum();
			int lastRow = sheet.getLastRowNum();
			int firstColumn = sheet.iterator().next().getFirstCellNum();
			int lastColumn = sheet.iterator().next().getLastCellNum();

			String[][] data = new String[lastRow - firstRow+1][lastColumn - firstColumn];

			for (int i = firstRow; i <= lastRow; i++) {
				Row row = sheet.getRow(i);				
				for(int j=0; j<lastColumn; j++){
					Cell cell = row.getCell(j, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					if(cell == null)
						data[i][j] = "";
					else if(cell.getCellType() == CellType.STRING)
						data[i][j] = cell.getStringCellValue();
				}
			}
			in.close();
			return data;
		}

		public ArrayList<String> getColumnData(String sheetName, int colNo)
				throws Exception {

			FileInputStream in = new FileInputStream(excelWorkBook);
			Workbook wb = WorkbookFactory.create(in);
			Sheet sheet = wb.getSheet(sheetName);

			ArrayList<String> data = new ArrayList<String>();

			Iterator<Row> rows = sheet.iterator();

			while (rows.hasNext()) {
				Row row = rows.next();

				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					if (cell.getColumnIndex() == colNo - 1
							&& cell.getCellType() == CellType.STRING)
						data.add(cell.getStringCellValue());
				}
			}
			in.close();
			data.add(0, "");
			return data;
		}

		public ArrayList<String> getRowData(String sheetName, int rowNo)
				throws Exception {

			FileInputStream in = new FileInputStream(excelWorkBook);
			Workbook wb = WorkbookFactory.create(in);
			Sheet sheet = wb.getSheet(sheetName);
			int lastColumn = sheet.iterator().next().getLastCellNum();

			ArrayList<String> data = new ArrayList<String>();

			Iterator<Row> rows = sheet.iterator();

			while (rows.hasNext()) {
				Row row = rows.next();

				if (row.getRowNum() == rowNo - 1) {
					
					for(int i=0; i<lastColumn; i++){
						Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						if(cell == null)
							data.add("");
						else if(cell.getCellType() == CellType.STRING)
							data.add(cell.getStringCellValue());
					}
					break;
				}
			}
			in.close();
			return data;
		}
		
		public void writeIntoExcel(String sheetName, int rowNo, ArrayList<String> headers, Map<String, String> result) throws Exception{
			FileInputStream in = new FileInputStream(excelWorkBook);
			Workbook wb = WorkbookFactory.create(in);
			Sheet sheet = wb.getSheet(sheetName);
			
			Row row = sheet.getRow(rowNo - 1);

			Set<String> resultTags = result.keySet();
			for (String tag : resultTags) {
				for (int j = 0; j < headers.size(); j++) {
					if (headers.get(j).equals(tag)) {
						Cell cell = row.createCell(j);
						cell.setCellType(CellType.STRING);
						cell.setCellValue(result.get(tag));
					}
				}
			}
			
			FileOutputStream fileOut = new FileOutputStream(excelWorkBook);
			wb.write(fileOut);
			fileOut.close();
			in.close();
		}
		
		
}