package com.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.utilities.Xls_Reader;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestBaseAllure {
	public Logger logger;
	public static RequestSpecification httpRequest;
	public static Response response;

	public static WebDriver driver;
	public static Properties prop;
	public WebDriverWait wait;
	public static ThreadLocal<WebDriver> tDriver = new ThreadLocal<WebDriver>();




	public TestBaseAllure(){
		prop = new Properties();

		FileInputStream fi;
		try {
			fi = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/config/config.properties");
			prop.load(fi);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public static WebDriver initialization(){
		String browser = prop.getProperty("browser");
		if(browser.equalsIgnoreCase("chrome")){
			//System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/Driver/chromedriver.exe");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();

		}else if (browser.equalsIgnoreCase("ie")){
			System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+"/Driver/IEDriverServer.exe");
			driver = new InternetExplorerDriver();

		}else if (browser.equalsIgnoreCase("firefox")){
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/Driver/geckodriver.exe");
			driver = new FirefoxDriver(); 

		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);


		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get(prop.getProperty("url"));
		tDriver.set(driver);
		return getDriver();

	}
	
	public static synchronized WebDriver getDriver(){
		return tDriver.get();
	}
	
	
	/*@BeforeClass
	public void setup(){


		//	WebDriverManager.chromedriver().setup();
		//	driver = new ChromeDriver();

		//	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//	driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		//	wait= new WebDriverWait(driver, 40);
		//	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("")));
		//driver.get("http://demo.nopcommerce.com");
		//driver.manage().window().maximize();

		logger = Logger.getLogger("EmployeeRestAPITests");
		PropertyConfigurator.configure("Log4j.properties");
		logger.setLevel(Level.DEBUG);
	}*/
	/*	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getExpectedData(String sheetname, String filePathname) throws IOException{
		Object[][] allData = null;
		//String path = System.getProperty("user.dir")+"/data/empData1.xlsx";
	//	String path = System.getProperty("user.dir")+filePathname;
    	Xls_Reader xls_Reader = new Xls_Reader(filePathname);
       	int rowCount = xls_Reader.getRowCount(sheetname);
    	System.out.println("ROW COUNT "+rowCount);
    	int cellCount = xls_Reader.getCellCount(sheetname, 1);
    	System.out.println("CELL COUNT "+cellCount);
    	 allData = new String[rowCount][cellCount];

    	for(int i =1;i<=rowCount;i++){
    		for(int j =0;j<cellCount;j++){
    			allData[i-1][j] = xls_Reader.getCellData(sheetname, j, i);
    		}
    	}



      List<Map<String,String>> datalist = new ArrayList<Map<String,String>>();
      for(int row =0; row< allData.length;row ++){

		Map<String,String> rowMap = (Map<String,String>)allData[row][0];
    	datalist.add(rowMap);
      }
      return datalist;


	}*/
	public List<HashMap<String, String>> getExpectedData(String sheetname, String filePathname) throws IOException, EncryptedDocumentException, InvalidFormatException{
		Xls_Reader xls_Reader = new Xls_Reader(filePathname);
		return xls_Reader.getDataList(sheetname);
	}
	public List<HashMap<String, String>> getExpectedData(String sheetname, String filePathname, boolean flag) throws IOException, EncryptedDocumentException, InvalidFormatException{
		Xls_Reader xls_Reader = new Xls_Reader(filePathname);
		return xls_Reader.getDataList(sheetname,flag);
	}
	public Object[][] getData(String sheetname, String filePathname) throws IOException {
		Xls_Reader xls_Reader = new Xls_Reader(filePathname);
		int rowCount = xls_Reader.getRowCount(sheetname);
		System.out.println("ROW COUNT "+rowCount);
		int cellCount = xls_Reader.getCellCount(sheetname, 1);
		System.out.println("CELL COUNT "+cellCount);
		Object[][] data = new Object[rowCount][cellCount];
		//String empdata[rowCount][cellCount];
		for(int i =1;i<=rowCount;i++){
			for(int j =0;j<cellCount;j++){
				data[i-1][j] = xls_Reader.getCellData(sheetname, j, i);
				System.out.println("DATA " +data[i-1][j]);
			}
		}
		System.out.println("DATA   "+data);
		return data;
	}
}

