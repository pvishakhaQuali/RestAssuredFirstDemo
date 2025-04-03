package RestLibrary.RestAssuredDemoProject;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.*;

import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.User;

public class DeserilizationJsonResponse {

	private static final String URL = "https://fakestoreapi.com";

	// Deserilization using Object Mapper
	@Test
	public void getDataUsingDeserilization() throws JsonMappingException, JsonProcessingException {
		Response response = RestAssured
				.given().pathParam("id", 1).baseUri(URL)
				.when().get("/users/{id}")
				.then().statusCode(200).log().all()
				.extract().response();
		
		ObjectMapper objM = new ObjectMapper();
		
		User userObj = objM.readValue(response.asString(), User.class);
		
		assertThat(userObj.getId(), equalTo(1));
		assertThat(userObj.getUsername(),equalTo("johnd"));
		assertThat(userObj.getEmailAdress(),equalTo("john@gmail.com"));
		assertThat(userObj.getPhone(),equalTo("1-570-236-7033"));

	}
	
	// Deserilization using as
	@Test
	public void getNestedDataUsingDeserilization() throws JsonMappingException, JsonProcessingException {
		User userObj =	RestAssured
				.given().pathParam("id", 1).baseUri(URL)
				.when().get("/users/{id}").as(User.class);
		
		// Normal acess
		assertThat(userObj.getId(), equalTo(1));
		assertThat(userObj.getUsername(),equalTo("johnd"));
		assertThat(userObj.getEmailAdress(),equalTo("john@gmail.com"));
		assertThat(userObj.getPhone(),equalTo("1-570-236-7033"));
		
		//Nested Access
		assertThat(userObj.getAdress().getCity(), equalTo("kilcoole"));
		assertThat(userObj.getAdress().getStreet(), equalTo("new road"));
		assertThat(userObj.getAdress().getNumber(), equalTo(7682));
		assertThat(userObj.getAdress().getZipcode(), equalTo("12926-3874"));
		
		assertThat(userObj.getFirstname(), equalTo("john"));
		assertThat(userObj.getLastname(), equalTo("doe"));

		System.out.println("-----------------"+userObj.getAdress().getLat());
		assertThat(userObj.getAdress().getLat(), equalTo("-37.3159"));

 
		//{"address":{"geolocation":{"lat":"-37.3159","long":"81.1496"},"city":"kilcoole","street":"new road","number":7682,"zipcode":"12926-3874"},"id":1,"email":"john@gmail.com","username":"johnd","password":"m38rmF$","name":{"firstname":"john","lastname":"doe"},"phone":"1-570-236-7033","__v":0}
	}
}
