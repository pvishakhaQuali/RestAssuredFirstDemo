package RestLibrary.RestAssuredDemoProject;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Map;
public class TestJsonPathJResponse {
	
	private static final String URL = "https://fakestoreapi.com/Users/{id}";
	
	private static final String Coll_URL = "https://fakestoreapi.com/products/category/electronics";
	
	private static final String URL_First = "https://reqres.in/api/users?page=1";

	private static final String URL_Ex1 ="https://jsonplaceholder.typicode.com/posts";
	
	private static final String URL_Ex2 ="https://jsonplaceholder.typicode.com/posts/{id}/comments";

	@Test
	public void testUsingString()
	{
		ResponseBody<?> responseBody = RestAssured.given()
		.pathParam("id", 1)
		.when()
		.get(URL);
		
		String responseString = responseBody.asString();
		assertThat(responseString.contains("address"), equalTo(true));
	}
	
	//it is difficult to make string so used jsonpath
	@Test
	public void testUsingJsonPath()
	{
	
		ResponseBody<?> responseBody1 = RestAssured.given()
				.pathParam("id", 1)
				.when()
				.get(URL);
		
			JsonPath js = responseBody1.jsonPath();
			
			Map<String , ?> m1 = js.get();
			System.out.println("Overall is --------- "+m1);
			
			Map<String , ?> m2 = js.get("address");
			System.out.println("Adress is --------- "+m2);
			
			Map<String , ?> m3 = js.get("address.geolocation");
			System.out.println("geolocation is --------- "+m3);
			
			String s1 = js.getString("address.geolocation.lat");
			System.out.println("lat is --------- "+s1);

			assertThat(js.get("id"),equalTo(1));
			
			assertThat(js.get("username"),equalTo("johnd"));
			
			assertThat(js.get("name.firstname"),equalTo("john"));

	}
	
	//Jsonpath  required more get method
	@Test
	public void testUsingJsonNested()
	{
		RestAssured.given()
		.pathParam("id", 1)
		.when()
		.get(URL).then()
		.body("address.city", equalTo("kilcoole"))
		.body("address.street", equalTo("new road"))
		.body("address.number", equalTo(7682))
		.body("address.city", equalTo("kilcoole"))
		.body("address.geolocation.lat", equalTo("-37.3159"))
		.body("name.firstname", equalTo("john"))
		.body("name.lastname", equalTo("doe"))
		.body("phone", equalTo("1-570-236-7033"));

	}

	//Jsonpath nested contains dublicate code,  required more get method
		@Test
		public void testUsingRootJsonNested()
		{
			RestAssured.given()
			.pathParam("id", 1)
			.when()
			.get(URL).then()
			.rootPath("address")
			.body("city", equalTo("kilcoole"))
			.body("street", equalTo("new road"))
			.body("number", equalTo(7682))
			.body("geolocation.lat", equalTo("-37.3159"))
			.rootPath("name")
			.body("firstname", equalTo("john"))
			.body("lastname", equalTo("doe"))
			.noRootPath()
			.body("phone", equalTo("1-570-236-7033"));

		}
		
		
		//For list / collection for particular item
		@Test
		public void testForCollectionAcess()
		{
			RestAssured.get(Coll_URL).then()
			.body("[0].id",equalTo(9))
			.body("[0]",notNullValue())
			.body("[1].title", containsString("SanDisk SSD"))
			.body("[1].price",equalTo(109));
			
			RestAssured.get(Coll_URL).then()
			.body("id[0]",equalTo(9))
			.body("[0]",notNullValue())
			.body("title[1]", containsString("SanDisk SSD"))
			.body("price[1]",equalTo(109));
			
			RestAssured.get(Coll_URL).then()
			.rootPath("[0]")
			.body("id",equalTo(9))
			//.body("[0]",notNullValue())
			.rootPath("[1]")
			.body("title", containsString("SanDisk SSD"))
			.body("price",equalTo(109));
			
		}
		
		//For list / collection for all items
				@Test
				public void testForCollectionAllFilterAcess()
				{
					RestAssured.get(Coll_URL)
					.then()
					.body("id", hasItems(9,10,11,12))
					.body("id",containsInAnyOrder(9,10,11,12,13,14))
					.body("image", everyItem(endsWith("jpg")))
					.body("title", hasItem(containsString("SSD")))
					.body("category", everyItem(equalTo("electronics")))
					.body("rating.rate",everyItem(allOf(greaterThan(0f),lessThan(5f))));
				}

		// extract the response one api and use in another

		@Test
		public void testExtractOneToAnother()
		{
			RestAssured.get(URL_First).prettyPeek().then().body("data.email[0]",response -> containsStringIgnoringCase(response.body().jsonPath().get("data.first_name[0]")));
		
			Integer postId = RestAssured.get(URL_Ex1).then().body("size()",equalTo( 100)).extract().path("id[2]");
		 
			RestAssured.given().pathParam("id", postId).get(URL_Ex2).prettyPeek().then().statusCode(200).body("size()",equalTo(5));
		}

}


