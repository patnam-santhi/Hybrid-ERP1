package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileUtils1 {
	XSSFWorkbook wb; //(global OBJECT)

	//write constructors to read excel path
	
	/*
	 * constructor looks like a method but not a method... Constructors are used for
	 * Initializing the OBJECT for a class in order to Invoke those class methods ..
	 * it should not contain any return type methods like void , string,boolean
	 * etc., it can have an arguments...
	 */
	// here the constructor is used to invoke the methods in this current class
	//or any other class of the project..
	
	public ExcelFileUtils1(String ExcelPath)throws Throwable
	{
	FileInputStream fi = new FileInputStream(ExcelPath);
	wb = new XSSFWorkbook(fi);
	
	}
	// method for counting no of rows in a sheet
	public int rowCount(String SheetName)
	{
		return wb.getSheet(SheetName).getLastRowNum();
		
	}
	//method for reading cell data
	
	public String getCellData(String SheetName, int row, int column)
	{
	  String data="";//(null declaration)
	  if(wb.getSheet(SheetName).getRow(row).getCell(column).getCellType()==CellType.NUMERIC)
      {
    	  int celldata= (int) wb.getSheet(SheetName).getRow(row).getCell(column).getNumericCellValue();
    	  data=String.valueOf(celldata);
      }
	  else
	  {
		  data= wb.getSheet(SheetName).getRow(row).getCell(column).getStringCellValue();
	  }
	  return data;
	  
}
	public void setCellData(String sheetName,int row,int column, String status, String WriteExcel)throws Throwable 
	{
		//get sheet from workbook
		XSSFSheet ws= wb.getSheet(sheetName);
		//get row from sheet
		XSSFRow rowNum= ws.getRow(row);
	// create cell
		XSSFCell cell= rowNum.createCell(column);
		cell.setCellValue(status);
		if(status.equalsIgnoreCase("Pass"))
		{
			XSSFCellStyle style= wb.createCellStyle();
			XSSFFont font = wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
	        font.setBold(true);
	        style.setFont(font);
	        rowNum.getCell(column).setCellStyle(style);
	        
		}
		else if(status.equalsIgnoreCase("Fail"))
		{
			XSSFCellStyle style= wb.createCellStyle();
			XSSFFont font = wb.createFont();
			font.setColor(IndexedColors.RED.getIndex());
	        font.setBold(true);
	        style.setFont(font);
	        rowNum.getCell(column).setCellStyle(style);
			
		}
		else 
		{
			XSSFCellStyle style= wb.createCellStyle();
			XSSFFont font = wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
	        font.setBold(true);
	        style.setFont(font);
	        rowNum.getCell(column).setCellStyle(style);
		}
		FileOutputStream fo = new FileOutputStream(WriteExcel);
		wb.write(fo);
		
	}
}

