package RestLibrary.RestAssuredDemoProject;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestPathAndQueryParam {

	private static final String URL = "https://fakestoreapi.com/{Component}/{id}";
	private static final String URLSecond = "https://fakestoreapi.com/products";

	@Test
	public void TestProduts() // Path Param
	{
		RestAssured.given().pathParam("Component", "products").pathParam("id", 1).when().get(URL).then().statusCode(200)
				.body("id", equalTo(1));
	}

	@Test
	public void TestPathCarts()// Path Param
	{
		RestAssured.given().pathParam("Component", "carts").pathParam("id", 1).when().get(URL).then().statusCode(200)
				.body("id", equalTo(1));
	}

	@Test
	public void TestQueryCarts()// Query Param
	{
		RestAssured.given()
				// .queryParam("Id", 1)
				.queryParam("limit", 4).queryParam("sort", "desc")
				// .queryParam("sort","asc")
				.queryParam("category", "men's clothing").when().get(URLSecond).prettyPeek().then().statusCode(200)
				.body("size()", equalTo(4));
	}

}
