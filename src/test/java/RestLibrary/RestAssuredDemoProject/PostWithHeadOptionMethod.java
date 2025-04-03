package RestLibrary.RestAssuredDemoProject;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONObject;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.emptyOrNullString;

public class PostWithHeadOptionMethod {

	private static final String URL = "https://httpbin.org/";

	private static final String POST_URL = "https://reqres.in/api";

	//head is used to extract head data
	@Test
	public void testHeadMethod() {
		RestAssured.head(URL).prettyPeek().then().statusCode(200).header("Content-Type", not(emptyOrNullString()));
	}
	
	//options used to extract allow methods
	@Test
	public void testOptionsMethod() {
		RestAssured.options(URL).prettyPeek().then().statusCode(200)
		.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS")
		.header("Allow", allOf((containsString("GET")),containsString("HEAD")));
	}
	
	//Normal Post
	@Test
	public void testPostUsers()
	{
		
		String requestBody = "{\r\n"
				+ "    \"name\": \"Vishu\",\r\n"
				+ "    \"job\": \"Vishleaderu\"\r\n"
				+ "}";
		
		RestAssured.baseURI = POST_URL;
		
		String id = RestAssured.given().contentType(ContentType.JSON)
		.body(requestBody).log().all().baseUri(POST_URL)
		.when().post("/users")
		.then().statusCode(201).log().all()
		.body("name", equalTo("Vishu") , 
				"job", equalTo("Vishleaderu")).log().all().extract().path("id");
		
		System.out.println("Id is --" + id);
		
		int newId = 2;
		RestAssured.given().contentType(ContentType.JSON)
		.pathParam("newId1", newId)
		.body(requestBody).log().all().baseUri(POST_URL)
		.when().get("/users/{newId1}").then()
		.statusCode(200)
		.body("data.id",equalTo(2)).log().all();
		
	}
	
    //	Best when you need to construct JSON dynamically
	@Test
	public void testPostUsersUsingJSONObject()
	{
        JSONObject json = new JSONObject();
        json.put("name", "Vish");
        json.put("job", "Vishleader");

        RestAssured.given()
        .header("Content-Type", "application/json").baseUri(POST_URL)
        .body(json.toString())  // Convert to JSON string
        .post("/users")
        .then()
        .statusCode(201).log().all();
	}
	
	//Best for working with Java objects instead of raw JSON
	@Test
	public void testPostUsersUsingOnjectMapper() throws JsonProcessingException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		
		ObjectNode objNode = objectMapper.createObjectNode();
		
		objNode.put("name","Vishakha");
		objNode.put("job", "QA");
		
		String stringObjNode = objectMapper.writeValueAsString(objNode);
		System.out.println("Object node is ---" + objNode.toPrettyString());
		System.out.println("Object node is ---" + stringObjNode);

	    RestAssured.given()
        .header("Content-Type", "application/json").baseUri(POST_URL)
        .body(stringObjNode)  // Convert to JSON string
        .post("/users")
        .then()
        .statusCode(201).log().all();
	}

}
