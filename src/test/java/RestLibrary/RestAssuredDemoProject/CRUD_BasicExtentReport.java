package RestLibrary.RestAssuredDemoProject;

import static org.hamcrest.Matchers.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static org.hamcrest.MatcherAssert.*;

public class CRUD_BasicExtentReport {

	private static final String URL = "https://fakestoreapi.com";

	RequestSpecification requestSpec;
	private static ExtentReports extent;
	ExtentTest test;

	@BeforeSuite
	public void setup() {

		String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
		String reportPath = System.getProperty("user.dir") + "/Reports/ExtentReport_" + timestamp + ".html";

		ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
		extent = new ExtentReports();
		extent.attachReporter(spark);

		extent.setSystemInfo("OS", "Windows");
		extent.setSystemInfo("Tester", "Vishakha");

		RestAssured.baseURI = "https://fakestoreapi.com";
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON)
				.addHeader("Content-Type", "application/json").setAccept(ContentType.JSON).build();
	}

	@AfterSuite
	public void tearDown() {
		extent.flush();
	}

	@Test
	public void getData() {
		test = extent.createTest("Demo getData Test Case");
		RestAssured.given().spec(requestSpec).when().get("/products").then().log().all().statusCode(200);
		test.log(Status.PASS, "Starting the test and get all data");
	}

	@Test
	public void postData() throws JsonProcessingException {
		test = extent.createTest("Demo post Test Case");
		ObjectMapper objM = new ObjectMapper();
		ObjectNode jsonObject = objM.createObjectNode();

		jsonObject.put("id", 0);
		jsonObject.put("title", "Vishustring");
		jsonObject.put("price", 0.1);
		jsonObject.put("description", "string");
		jsonObject.put("category", "string");
		jsonObject.put("image", "http://example.com");

		String jsonString = objM.writeValueAsString(jsonObject);

		int id = RestAssured.given().spec(requestSpec).body(jsonString).when().post("/products").then().statusCode(200)
				.body("id", equalTo(21), "title", equalTo("Vishustring")).extract().path("id");

		System.out.println("Id is -------" + id);
		test.log(Status.PASS, "Starting the test and post all data");

	}

	@Test
	public void putData() throws JsonProcessingException {
		test = extent.createTest("Demo put Test Case");
		ObjectMapper objM = new ObjectMapper();
		ObjectNode jsonObject = objM.createObjectNode();

		jsonObject.put("id", 1);
		jsonObject.put("title", "VishuPatilstring");
		jsonObject.put("price", 0.11);
		jsonObject.put("description", "string1");
		jsonObject.put("category", "1string");
		jsonObject.put("image", "http://exa1mple.com");

		String jsonString = objM.writeValueAsString(jsonObject);

		RestAssured.given().spec(requestSpec).body(jsonString).when().put("/products/1").then().statusCode(200).log()
				.all().body("title", equalToIgnoringCase("VishuPatilString"));
		test.log(Status.PASS, "Starting the test and put all data");

	}

	@Test
	public void patchData() throws JsonProcessingException {
		test = extent.createTest("Demo patch Test Case");

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("id", 1);
		jsonObject.put("title", "VishuPatilNewstring");

		RestAssured.given().spec(requestSpec).body(jsonObject.toString()).when().patch("/products/1").then()
				.statusCode(200).log().all().body("title", equalToIgnoringCase("VishuPatilNewstring"));
		test.log(Status.PASS, "Starting the test and patch all data");

	}

	@Test
	public void deleteData() {
		test = extent.createTest("Demo delete Test Case");
		RestAssured.given().spec(requestSpec).when().delete("/products/1").then().log().all().statusCode(200);
		test.log(Status.PASS, "Starting the test and delete all data");

	}
}
