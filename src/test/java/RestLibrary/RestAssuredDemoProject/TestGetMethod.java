package RestLibrary.RestAssuredDemoProject;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class TestGetMethod {

	private static final String API_URL = "https://httpbin.org/get";

	@Test
	public void checkResult()
	{
		RestAssured.get(API_URL).then()
		.statusCode(200)
		.contentType("application/json")
		.header("Content-Length", "324")
		.header("Server", "gunicorn/19.9.0")
		.header("Access-Control-Allow-Origin","*");
	}
}
