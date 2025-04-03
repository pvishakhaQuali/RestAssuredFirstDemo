package RestLibrary.RestAssuredDemoProject;

import static org.hamcrest.Matchers.*;

import org.json.JSONObject;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static org.hamcrest.MatcherAssert.*;

public class CRUD_Basic {

	private static final String URL = "https://fakestoreapi.com";

	@Test
	public void getData() {
		RestAssured.given().contentType(ContentType.JSON).baseUri(URL).when().get("/products").then().log().all()
				.statusCode(200);
	}

	@Test
	public void postData() throws JsonProcessingException {
		ObjectMapper objM = new ObjectMapper();
		ObjectNode jsonObject = objM.createObjectNode();

		jsonObject.put("id", 0);
		jsonObject.put("title", "Vishustring");
		jsonObject.put("price", 0.1);
		jsonObject.put("description", "string");
		jsonObject.put("category", "string");
		jsonObject.put("image", "http://example.com");

		String jsonString = objM.writeValueAsString(jsonObject);

		int id = RestAssured.given().baseUri(URL).contentType(ContentType.JSON).body(jsonString).when()
				.post("/products").then().statusCode(200).body("id", equalTo(21), "title", equalTo("Vishustring"))
				.extract().path("id");

		System.out.println("Id is -------" + id);
	}

	@Test
	public void putData() throws JsonProcessingException {
		ObjectMapper objM = new ObjectMapper();
		ObjectNode jsonObject = objM.createObjectNode();

		jsonObject.put("id", 1);
		jsonObject.put("title", "VishuPatilstring");
		jsonObject.put("price", 0.11);
		jsonObject.put("description", "string1");
		jsonObject.put("category", "1string");
		jsonObject.put("image", "http://exa1mple.com");

		String jsonString = objM.writeValueAsString(jsonObject);

		RestAssured.given().baseUri(URL).contentType(ContentType.JSON).body(jsonString).when().put("/products/1").then()
				.statusCode(200).log().all().body("title", equalToIgnoringCase("VishuPatilString"));

	}

	@Test
	public void patchData() throws JsonProcessingException {

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("id", 1);
		jsonObject.put("title", "VishuPatilNewstring");

		RestAssured.given().baseUri(URL).contentType(ContentType.JSON).body(jsonObject.toString()).when()
				.patch("/products/1").then().statusCode(200).log().all()
				.body("title", equalToIgnoringCase("VishuPatilNewstring"));

	}

	@Test
	public void deleteData() {
		RestAssured.given().contentType(ContentType.JSON).baseUri(URL).when().delete("/products/1").then().log().all()
				.statusCode(200);
	}
}
