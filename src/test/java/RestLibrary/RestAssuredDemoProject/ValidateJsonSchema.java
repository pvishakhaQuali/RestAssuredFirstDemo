package RestLibrary.RestAssuredDemoProject;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ValidateJsonSchema {

	private static final String URL = "https://jsonplaceholder.typicode.com/todos/{id}";

	private static final String Array_URL = "https://jsonplaceholder.typicode.com/todos";
	
	private static final String Nested_URL = "https://reqres.in/api/users/{id}";

	@Test
	public void validateSchema()
	{
		RestAssured.given()
		.pathParam("id",5)
		.when().get(URL).prettyPeek()
		.then()
		.body(matchesJsonSchemaInClasspath("Todo.json"));
	}

	@Test
	public void validateNestedSchema()
	{
		RestAssured.given().pathParam("id", 2)
		.when().get(Nested_URL)
		.then()
		.body(matchesJsonSchemaInClasspath("NestedJson.json"));
	}
	
	@Test
	public void validateArraySchema()
	{
		RestAssured.given()
		.when().get(Array_URL)
		.then()
		.body(matchesJsonSchemaInClasspath("ArrayJson.json"));
	}
}
