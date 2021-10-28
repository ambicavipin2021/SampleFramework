package com.selenium.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.common.TestBaseAllure;
import com.utilities.TestAllureListener;

import io.qameta.allure.Step;
@Listeners({TestAllureListener.class})
public class loginAllureReportTest extends TestBaseAllure {

	public WebDriver driver;
	
	public loginAllureReportTest(){
		super();
		
	}
	
	@BeforeMethod
	public void setUp(){
		driver = initialization();
	}
	
	@Step(" validateLogo ")
	@Test(priority = 0)
	public void validateLogo(){
	
	Boolean logoStatus = driver.findElement(By.xpath("//a/img[@alt='nopCommerce demo store']")).isDisplayed();
		Assert.assertTrue(logoStatus);
	}
	@Step(" validateLogo 1 ")
	@Test(priority = 1)
	public void validateLogo1(){
	
	Boolean logoStatus = driver.findElement(By.xpath("//a/img[@alt='nopCommerce demo stoe']")).isDisplayed();
		Assert.assertFalse(logoStatus);
	}


}
