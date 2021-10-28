package AllureExamples;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.qameta.allure.Feature;


public class SampleAllureTest {
	
	@Test
	public void TC1(){
		Assert.assertTrue(false);
	}
	@Test
	public void TC2(){
		Assert.assertTrue(true);
	}
}
