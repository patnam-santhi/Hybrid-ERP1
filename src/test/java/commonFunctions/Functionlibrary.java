package commonFunctions;

import static org.testng.Assert.assertThrows;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.Assertion;

public class Functionlibrary {
	public static Properties conpro;
	public static WebDriver driver;
	// method for launching browser
	public static WebDriver startBrowser() throws Throwable
	{
		// initiating the object
		conpro=new Properties();
		//loading propertyfile
		conpro.load(new FileInputStream("./PropertyFiles/Environment.Properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
		driver= new ChromeDriver();
		driver.manage().window().maximize();
		}
		else if (conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
			driver= new FirefoxDriver();
			
		}
		else
		{
			Reporter.log("Browser value is not matching",true);
		}
		return driver;
	}
	//method for launching url
	public static void openUrl()
	{
		driver.get(conpro.getProperty("Url"));
	}
	// method for-- wait for element
public static void waitForElement(String LocatorType,String Locatorvalue,String Testdata) 
	{
	WebDriverWait mywait= new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(Testdata)));
	if(LocatorType.equalsIgnoreCase("name"))
	{
		//waitutil element is visible
		mywait.until(ExpectedConditions.invisibilityOfElementLocated(By.name(Locatorvalue)));
	}
	if(LocatorType.equalsIgnoreCase("id"))
	{
		//waitutil element is visible
		mywait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(Locatorvalue)));
	}
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		//waitutil element is visible
		mywait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath (Locatorvalue)));
	}
	}
//method for any textbox
public static void typeAction(String LocatorType, String Locatorvalue,String testdata)
{
	if(LocatorType.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(Locatorvalue)).clear();
		driver.findElement(By.name(Locatorvalue)).sendKeys(testdata);
	}
	if(LocatorType.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(Locatorvalue)).clear();
		driver.findElement(By.id(Locatorvalue)).sendKeys(testdata);
	}
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(Locatorvalue)).clear();
		driver.findElement(By.xpath(Locatorvalue)).sendKeys(testdata);
	}

	}
// method for click action
public static void clickAction(String Locatortype, String locatorvalue)
{
	if(Locatortype.equalsIgnoreCase("id"))
	{
	driver.findElement(By.id(locatorvalue)).sendKeys(Keys.ENTER);
	}

	if(Locatortype.equalsIgnoreCase("name"))
	{
	driver.findElement(By.name(locatorvalue)).click();
	}
	if(Locatortype.equalsIgnoreCase("xpath"))
	{
	driver.findElement(By.xpath(locatorvalue)).click();
	}
	
}
// method for validating any page title
public static void validate(String Expected_Title)
{
	String Actual_Tiltle= driver.getTitle();
	try {
	Assert.assertEquals(Actual_Tiltle, Expected_Title,"Title is notmatching");
	}catch(AssertionError a)
	{
		System.out.println(a.getMessage());
	}

}
//method to close browser
public static void closeBrowser() {
	driver.quit();
}

  public static String generateDate() 
  { 
	  Date date= new Date();
	  
		
		  DateFormat df= new SimpleDateFormat("YYYY_MM_dd  hh_mm"); 
		  return df.format(date);
		 
  
  }
  
 // Method for dropdown action
  public static void dropDownAction(String LType, String LValue,String TestData)
  {
	  
	  if(LType.equalsIgnoreCase("name"))
	  {
		  int value= Integer.parseInt(TestData);
		  Select element= new Select(driver.findElement(By.name(LValue)));
		  element.selectByIndex(value);
	  }
  		  
		  if(LType.equalsIgnoreCase("id"))
			  
		  {
			  int value= Integer.parseInt(TestData);
			  Select element= new Select(driver.findElement(By.id(LValue)));
			  element.selectByIndex(value);
		  }
			  
			  if(LType.equalsIgnoreCase("xpath"))
			  {
				  int value= Integer.parseInt(TestData);
				  Select element= new Select(driver.findElement(By.xpath(LValue)));
				  element.selectByIndex(value);
			  }
  }
	  //method for capstock: capturing the number in the Notepad in the capture data folder
	  public static void captureStock(String locatorType, String locatorvalue) throws Throwable
	  {
		  String stockNumber="";
		  if(locatorType.equalsIgnoreCase("xpath"))
		  {
			  stockNumber= driver.findElement(By.xpath(locatorvalue)).getAttribute("value");
			  
		  }
		  if(locatorType.equalsIgnoreCase("id"))
		  {
			  stockNumber= driver.findElement(By.id(locatorvalue)).getAttribute("value");
			  
		  }
		  if(locatorType.equalsIgnoreCase("name"))
		  {
			  stockNumber= driver.findElement(By.name(locatorvalue)).getAttribute("value");
			  
		  }
		  //Create a notepad below the CaptureData folder
		  
		  FileWriter fw=new FileWriter("./CaptureData/stocknumber.txt"); // Filewriter is a java class
		 /* fw is an object holding path of the notepad which have only physical memory 
		  and doesn.t have any buffered memory. if we need to perform any action we need to allocate 
		   memory using Buffer class*/
		  BufferedWriter bw= new BufferedWriter(fw);
		  bw.write(stockNumber);
		  bw.flush();
		  bw.close();
	  }
	  //method for stocktable
	  public static void stockTable()throws Throwable
	  {
		  //read stocknumber from notepad
		  FileReader fr= new FileReader("./CaptureData/stocknumber.txt");
		  BufferedReader br= new BufferedReader(fr);
		  String Exp_Data= br.readLine();
		  if(!driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).isDisplayed())
			  driver.findElement(By.xpath(conpro.getProperty("Search-panel"))).click();
		  driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).clear();
		  driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).sendKeys(Exp_Data);
		  driver.findElement(By.xpath(conpro.getProperty("Search-button"))).click();
		  Thread.sleep(3000);
		  String Act_Data= driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		  Reporter.log(Act_Data+"   "+Exp_Data,true);
		  try {
			  Assert.assertEquals(Act_Data, Exp_Data,"Stock Number Not Found in Table");
			  
		  }catch(Throwable t)
		  {
			  System.out.println(t.getMessage());
		  }
		  
	  }
	  //method to capture supplier number into notepad
	  public static void capsup(String Ltype, String Lvalue) throws Throwable 
	  { 
		String supplierNum ="";
		 if(Ltype.equalsIgnoreCase("xpath"))
		  {
			 supplierNum= driver.findElement(By.xpath(Lvalue)).getAttribute("value");
			  
		  }
		  if(Ltype.equalsIgnoreCase("id"))
		  {
			  supplierNum= driver.findElement(By.id(Lvalue)).getAttribute("value");
			  
		  }
		  if(Ltype.equalsIgnoreCase("name"))
		  {
			  supplierNum= driver.findElement(By.name(Lvalue)).getAttribute("value");
			  
		  }
		  FileWriter fw=new FileWriter("./CaptureData/supplierNumber.txt"); // Filewriter is a java class
			 
			  BufferedWriter bw= new BufferedWriter(fw);
			  bw.write(supplierNum);
			  bw.flush();
			  bw.close();
	  
  }
  public static void supplierTable() throws Throwable
  {
	  //read supplierNum from notepad
	  FileReader fr= new FileReader("./CaptureData/supplierNum.txt");
	  BufferedReader br= new BufferedReader(fr);
	  String Exp_Data= br.readLine();
	  if(!driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).isDisplayed())
		  driver.findElement(By.xpath(conpro.getProperty("Search-panel"))).click();
	  driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).clear();
	  driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).sendKeys(Exp_Data);
	  driver.findElement(By.xpath(conpro.getProperty("Search-button"))).click();
	  Thread.sleep(3000);
	  String Act_Data= driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
	  Reporter.log(Act_Data+"   "+Exp_Data,true);
	  try {
		  Assert.assertEquals(Act_Data, Exp_Data,"Supplier Number Not Found in Table");
		  
	  }catch(Throwable t)
	  {
		  System.out.println(t.getMessage());
	  }
	  
	  
  }



}

		
		
		

	



	
