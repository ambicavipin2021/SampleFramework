package com.api.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONObject;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.common.TestBaseAllure;

import io.restassured.RestAssured;
import io.restassured.http.Method;

public class sampleApiTest extends TestBaseAllure {
	String path = System.getProperty("user.dir")+"/Data/empData1.xlsx";
	List<HashMap<String,String>> getData = new ArrayList<HashMap<String,String>>();
	
	@SuppressWarnings("unchecked")
	@Test(dataProvider ="createEmpDataProvider")

	public void createEmployee(String name,String salary,String age) throws InterruptedException {
	   
		SoftAssert softassert = new SoftAssert();
		RestAssured.baseURI ="https://dummy.restapiexample.com";
	    httpRequest = RestAssured.given();
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", name);
		requestParams.put("salary", salary);
		requestParams.put("age", age);
		httpRequest.header("Content-Type","application/json");
		System.out.println("requestParams.toJSONString()" +requestParams.toJSONString());
		System.out.println("requestParams.toJSONString()" +requestParams.toJSONString());
		httpRequest.body(requestParams.toJSONString());
		response = httpRequest.request(Method.POST,"/api/v1/create");
		Thread.sleep(7000);
		String resp = response.getBody().asString();
		
		System.out.println(" resp "+resp);
		//logger.info(" RESPONSE "+resp);
		//logger.info(" RESPONSE Message "+response.jsonPath().get("message"));
		//logger.info(" RESPONSE Data "+response.jsonPath().get("data"));
		softassert.assertEquals(response.jsonPath().get("message"), "Successfully! Record has been added.");
		softassert.assertEquals(response.jsonPath().get("status"), "success");
		softassert.assertEquals(response.jsonPath().get("message"),"Successfully! Record has been added.","Success message is not shown correctly");
		softassert.assertEquals(response.jsonPath().get("status"),"success","Status is not shown as success but is shown as " +response.jsonPath().get("status"));
		softassert.assertEquals(response.getStatusCode(),200);
		softassert.assertEquals(resp.contains(name),true);
	
		softassert.assertEquals(response.jsonPath().get("data").toString().contains(name),true);
		softassert.assertEquals(response.jsonPath().get("data").toString().contains(salary),true);
		softassert.assertEquals(response.jsonPath().get("data").toString().contains(age),true);
		softassert.assertAll();
	
	
	
		
}
    @DataProvider(name="createEmpDataProvider")
        Object[][] getEmpData() throws IOException{
    	
       /*	Xls_Reader xls_Reader = new Xls_Reader(path);
        int rowCount = xls_Reader.getRowCount("createEmp");
    	System.out.println("ROW COUNT "+rowCount);
    	int cellCount = xls_Reader.getCellCount("createEmp", 1);
    	System.out.println("CELL COUNT "+cellCount);
    	Object[][] empdata = new Object[rowCount][cellCount];
		//String empdata[rowCount][cellCount];
    	for(int i =1;i<=rowCount;i++){
    		for(int j =0;j<cellCount;j++){
    			empdata[i-1][j] = xls_Reader.getCellData("createEmp", j, i);
    		}
    	}
    	*/
    	
    	
    //	String empadata[][]={{"dff","2534255","52"},{"gfgfd","43645654","19"},{"hgfjghj","54654654","56"}};
    	
    	    	
	return getData("createEmp", path);
    	
    }
}
