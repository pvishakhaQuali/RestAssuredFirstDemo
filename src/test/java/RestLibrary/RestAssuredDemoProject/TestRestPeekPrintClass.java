package RestLibrary.RestAssuredDemoProject;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TestRestPeekPrintClass {

	@Test
	public void getTest()
	{
		
		RestAssured.get("https://reqres.in/api/users/2").then().statusCode(200);
	}
	
	@Test
	public void peekTest()
	{

		Response response = RestAssured.get("https://httpbin.org/get");
		System.out.println("-----------PeekTest---------------");
		response.peek();
		Assert.assertTrue(response.peek() instanceof Response);
	}
	
	
	@Test
	public void prettyPeekTest()
	{
		Response response = RestAssured.get("https://httpbin.org/get");
		System.out.println("-----------PrettyPeekTest---------------");
		response.prettyPeek();
		Assert.assertTrue(response.prettyPeek() instanceof Response);
	}
	
	@Test
	public void printTest()
	{
		Response response = RestAssured.get("https://httpbin.org/get");
		System.out.println("-----------PrintTest---------------");
		response.print();
		Assert.assertTrue(response.print() instanceof String);

	}
	@Test
	public void prettyPrintTest()
	{
		Response response = RestAssured.get("https://httpbin.org/get");
		System.out.println("-----------PrettyPrintTest---------------");
		response.prettyPrint();
		Assert.assertTrue(response.prettyPrint() instanceof String);
	}
}
