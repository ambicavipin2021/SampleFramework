package com.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Xls_Reader {

	public String path;
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;

	public Xls_Reader(String path) {

		this.path = path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// returns the row count in a sheet

	public int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum();
			return number;
		}

	}

	public int getCellCount(String sheetName, int rownum) throws IOException{

		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(index);
			XSSFRow number = sheet.getRow(rownum);
			int cellcount = number.getLastCellNum();
			return cellcount;
		}


	}
	// returns the data from a cell
		public String getCellData(String sheetName, int colNum, int rowNum) {
			try {
				if (rowNum <= 0)
					return "";

				int index = workbook.getSheetIndex(sheetName);

				if (index == -1)
					return "";

				sheet = workbook.getSheetAt(index);
				row = sheet.getRow(rowNum);
				if (row == null)
					return "";
				cell = row.getCell(colNum);
				if (cell == null)
					return "";

				//
				if (cell.getCellType().name().equals("STRING"))
					return cell.getStringCellValue();

				//
				// if (cell.getCellType().STRING != null)
				// return cell.getStringCellValue();
				else if ((cell.getCellType().name().equals("NUMERIC")) || (cell.getCellType().name().equals("FORMULA"))) {

					String cellText = String.valueOf(cell.getNumericCellValue());
				/*	if (HSSFDateUtil.isCellDateFormatted(cell)) {
						// format in form of M/D/YY
						double d = cell.getNumericCellValue();

						Calendar cal = Calendar.getInstance();
						cal.setTime(HSSFDateUtil.getJavaDate(d));
						cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
						cellText = cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;

						// System.out.println(cellText);

					}*/

					return cellText;
				} else if (cell.getCellType().BLANK != null)
					return "";
				else
					return String.valueOf(cell.getBooleanCellValue());
			} catch (Exception e) {

				e.printStackTrace();
				return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
			}
		}

	public List<HashMap<String, String>> getDataList(String sheetname) throws EncryptedDocumentException, InvalidFormatException, IOException{
		List<HashMap<String, String>> excelData = new ArrayList<HashMap<String, String>>();
		//  File file = new File(filePathname);
		//  FileInputStream inputStream = new FileInputStream( file );
		//  Workbook workbook = WorkbookFactory.create( inputStream );

		XSSFSheet sheet = workbook.getSheet(sheetname);
		int rowCount = sheet.getLastRowNum(); 
		for(int i=1;i<=rowCount;i++){
			XSSFRow row = sheet.getRow(0);
			Map<String,String> rowData = new HashMap<String,String>();
			for(int j=0;j<row.getLastCellNum();j++) {
				String key=row.getCell(j).getStringCellValue();
				String value=sheet.getRow(i).getCell(j).getStringCellValue();
				rowData.put(key, value);
			}
			excelData.add((HashMap<String, String>) rowData);
		}
		return excelData;
	}
	public List<HashMap<String, String>> getDataList(String sheetname, boolean flag) throws EncryptedDocumentException, InvalidFormatException, IOException{
		List<HashMap<String, String>> excelData = new ArrayList<HashMap<String, String>>();
		//  File file = new File(filePathname);
		//  FileInputStream inputStream = new FileInputStream( file );
		//   Workbook workbook = WorkbookFactory.create( inputStream );

		XSSFSheet sheet = workbook.getSheet(sheetname);
		int rowCount = sheet.getLastRowNum(); 
		for(int i=1;i<=rowCount;i++){
			XSSFRow row = sheet.getRow(0);
			Map<String,String> rowData = new HashMap<String,String>();
			for(int j=0;j<row.getLastCellNum();j++) {
				String key=row.getCell(j).getStringCellValue();
				String value=sheet.getRow(i).getCell(j).getStringCellValue();
				if(key.equalsIgnoreCase("Runmode") && value.equalsIgnoreCase("FALSE") && flag == true){
					break;	
				}else{
					rowData.put(key, value);
				}
			}
			if(rowData.size()>0){
				excelData.add((HashMap<String, String>) rowData);
			}
		}
		return excelData;
	}


}
