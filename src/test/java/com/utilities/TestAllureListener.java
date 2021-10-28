package com.utilities;
import java.io.ByteArrayInputStream;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.common.TestBaseAllure;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.Attachment;
import io.qameta.allure.listener.StepLifecycleListener;
public class TestAllureListener implements ITestListener{



		private static String getTestMethodName(ITestResult iTestReult){
			return iTestReult.getMethod().getConstructorOrMethod().getName();
		}
		
		/*@Attachment(value = "Web Page Screenshot", type = "image/png")
		public byte[] saveScreeshot(WebDriver driver){
			return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
			
		}*/
		
		/*@Attachment(value="Page Screenshot",type="image/png")
		private static File saveScreeshot (WebDriver driver) throws IOException  {
			
			 
			   
		    SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		    Date date = new Date();
		    String fileName = sdf.format(date);
		    File SrcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		    FileUtils.copyFile(SrcFile, new File(System.getProperty("user.dir")+"//allure-results//"+fileName+".png"));
		    return SrcFile;
		}*/
		public void embedScreenshot(WebDriver driver,String name) {
			
		 
	        try {
	            Allure.addAttachment(name, new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
		@Attachment(value="{0}",type="text/plain")
		public static String saveTextLog(String message){
			return message;
			
		}

		
		@Attachment(value="{0}",type="text/html")
		public static String attachHtml(String html){
				return html;
				
			}


		@Override
		public void onTestStart(ITestResult result) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTestSuccess(ITestResult result) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTestFailure(ITestResult result) {
		Object testClass= result.getInstance();
		WebDriver driver = TestBaseAllure.getDriver();
		if(driver instanceof WebDriver){
			System.out.println("$$$$$$$$$$$$$$$$$$$$"+getTestMethodName(result));
			//saveScreeshot(driver);
			embedScreenshot(driver,getTestMethodName(result));
			//saveScreeshot(driver);
			saveTextLog(getTestMethodName(result)+" failed and text log taken");
			attachHtml(getTestMethodName(result)+" failed and html taken");
		}
			
		}

		@Override
		public void onTestSkipped(ITestResult result) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStart(ITestContext context) {
			System.out.println("Test case " + context.getName());
			context.setAttribute("WebDriver", TestBaseAllure.getDriver());
		}

		@Override
		public void onFinish(ITestContext context) {
			// TODO Auto-generated method stub
			
		}

}
