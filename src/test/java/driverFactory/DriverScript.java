package driverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.Functionlibrary;
import utilities.ExcelFileUtils1;

public class DriverScript {
	public static WebDriver driver;
	String inputpath= "./FileInput/controller1.xlsx";
	String outputpath="./FileOutput/HybridResults.xlsx";
	String TCSheet= "MasterTestCases";
	String TCModule="";
	ExtentReports reports;
	ExtentTest logger;
	private String locatorv;
	public void startTest() throws Throwable
	{
		String Module_status="";
		String Module_new="";
		//create object for ExcelFileUtils1
		
		ExcelFileUtils1 xl= new ExcelFileUtils1(inputpath);
		//iterate all rows in TCSheet
		for(int i=1;i<=xl.rowCount(TCSheet);i++)
		{
			if(xl.getCellData(TCSheet, i, 2).equalsIgnoreCase("Y"))
			{
				//define path for html report
				reports= new ExtentReports("./target/ExtentReports/"+TCModule+Functionlibrary.generateDate()+".html");
				logger = reports.startTest(TCModule);
				logger.assignAuthor("Priya");
				//store corresponding sheet or test cases into TCmodule
				TCModule= xl.getCellData(TCSheet, i, 1);
				//iterate corresponding sheet 
				for(int j= 1; j<=xl.rowCount(TCModule);j++) 
				{
					String Description= xl.getCellData(TCModule, j, 0);
					String ObjectType= xl.getCellData(TCModule, j, 1);
					String Ltype= xl.getCellData(TCModule, j, 2);
					String Lvalue= xl.getCellData(TCModule, j, 3);
					String Tdata= xl.getCellData(TCModule, j, 4);
					try {
						if(ObjectType.equalsIgnoreCase("startBrowser"))
						{
							driver= Functionlibrary.startBrowser();
							logger.log(LogStatus.INFO, Description);
							
						}
						if(ObjectType.equalsIgnoreCase("openUrl"))
						{
							Functionlibrary.openUrl();
							logger.log(LogStatus.INFO, Description);

						}
						if(ObjectType.equalsIgnoreCase("waitForElement"))
						{
							Functionlibrary.waitForElement(Ltype, Lvalue, Tdata);
							logger.log(LogStatus.INFO, Description);

						}
						if(ObjectType.equalsIgnoreCase("typeAction"))
							
						{
							Functionlibrary.typeAction(Ltype, Lvalue, Tdata);
							logger.log(LogStatus.INFO, Description);

						}
						if(ObjectType.equalsIgnoreCase("clickAction"))
						{
							Functionlibrary.clickAction(Ltype, Lvalue);
							logger.log(LogStatus.INFO, Description);

						}
						if(ObjectType.equalsIgnoreCase("validate"))
						{
							Functionlibrary.validate(Tdata);
							logger.log(LogStatus.INFO, Description);

						}
						if(ObjectType.equalsIgnoreCase("closeBrowser"))
						{
							Functionlibrary.closeBrowser();
							logger.log(LogStatus.INFO, Description);

						}
						if(ObjectType.equalsIgnoreCase("dropDownAction"))
						{
							Functionlibrary.dropDownAction(Ltype, Lvalue, Tdata);
							logger.log(LogStatus.INFO, Description);

						}
						if(ObjectType.equalsIgnoreCase("captureStock"))
						{
							Functionlibrary.captureStock(Ltype, Lvalue);
									logger.log(LogStatus.INFO, Description);

						}
						if(ObjectType.equalsIgnoreCase("stockTable"))
						{
							Functionlibrary.stockTable();
							logger.log(LogStatus.INFO, Description);

						}
						
					
						if(ObjectType.equalsIgnoreCase("capsup"))
						{
							Functionlibrary.capsup(Ltype, Lvalue);
							logger.log(LogStatus.INFO, Description);

						}
						if(ObjectType.equalsIgnoreCase("supplierTable"))
						{
							Functionlibrary.supplierTable();
							logger.log(LogStatus.INFO, Description);

						}
						
						
								//write status as pass in cell of TCModule
						xl.setCellData(TCModule, j, 5, "Pass", outputpath);
						logger.log(LogStatus.PASS, Description);

						Module_status="True";
						
					}catch(Exception e)
					{
						System.out.println(e.getMessage());
						//write status as Fail in cell of TCModule
						xl.setCellData(TCModule, j, 5, "Fail", outputpath);
						logger.log(LogStatus.FAIL, Description);

						Module_new= "False";
						File screen= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						FileUtils.copyFile(screen, new File("./target/Screenshot/"+TCModule+Description+Functionlibrary.generateDate()+".png"));

					}
					reports.endTest(logger);
					reports.flush();
					if(Module_status.equalsIgnoreCase("True"))
					{
						//write as Pass in TCSheet
						xl.setCellData(TCSheet, i,3, "Pass", outputpath);
					}
					if(Module_new.equalsIgnoreCase("False"))
					{
						xl.setCellData(TCSheet, i, 3, "False", outputpath);

					}
			
				}
			}
			else
			{//write as blocked into status in TCSheet
				xl.setCellData(TCSheet, i, 3, "Blocked", outputpath);

			}
		}
	}
	
	//
	
	
			

}
