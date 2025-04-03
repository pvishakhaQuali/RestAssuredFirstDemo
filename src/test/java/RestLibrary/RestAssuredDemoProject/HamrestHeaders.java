package RestLibrary.RestAssuredDemoProject;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;

public class HamrestHeaders {
	
	private static final String POST_URL ="https://jsonplaceholder.typicode.com/posts";
	@Test
	public void checkHeaders()
	{
		RestAssured.get(POST_URL).then()
		.statusCode(200)
		.statusCode(lessThan(400))
		.statusCode(greaterThan(199))
		.statusCode(allOf(greaterThanOrEqualTo(200),lessThanOrEqualTo(300)))
		.time(greaterThan(17L),TimeUnit.MILLISECONDS)
		.header("Content-Type", "application/json; charset=utf-8")
		.header("Connection", containsStringIgnoringCase("Keep"))
		.header("X-Ratelimit-Remaining","999")
		.header("X-Ratelimit-Remaining", value -> Integer.parseInt(value), equalTo(999))
		.header("server-timing",containsString("cfL4"))
		.header(POST_URL, isEmptyOrNullString());
	}
	
	@Test
	public void checkJsonHeader()
	{
		ObjectMapper objectMapper = new ObjectMapper();
		
		RestAssured.get(POST_URL).then().header("Report-To",(s)->{
			try {
				return objectMapper.readTree(s).get("max_age").asInt();
			}catch(JsonProcessingException e)
			{
				throw new RuntimeException(e);
			}
		},equalTo(3600));
	}
	
	@Test
    public void checkJsonsHeader() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        // 🔹 Extract "Report-To" header from response
        String reportToHeader = RestAssured.get(POST_URL)
                .then()
                .extract()
                .header("Report-To");

        // 🔹 Parse JSON only if the header exists
        if (reportToHeader != null) {
            JsonNode jsonNode = objectMapper.readTree(reportToHeader);

            // Extract "group"
            String group = jsonNode.get("group").asText();
            System.out.println("Group: " + group); // Debugging Output

            // Extract "endpoints" (as an array)
            JsonNode endpointsArray = jsonNode.get("endpoints");

            // Validate "group" and "endpoints" are present
            assertThat(group, equalTo("heroku-nel"));
            assertThat(endpointsArray.isArray(), is(true));
            assertThat(endpointsArray.size(), greaterThan(0));

            // Extract first endpoint URL
            String endpointUrl = endpointsArray.get(0).get("url").asText();
            System.out.println("Endpoint URL: " + endpointUrl); // Debugging Output

            // Validate extracted endpoint URL
            assertThat(endpointUrl, containsString("https://nel.heroku.com/reports"));
        } else {
            throw new AssertionError("Header 'Report-To' not found in response");
        }
    }

	@Test
	public void checkDateHeaders()
	{
		RestAssured.get(POST_URL).then()
		.header("Date",
				(dateString)->
		
			LocalDateTime.parse(dateString,DateTimeFormatter.RFC_1123_DATE_TIME).toLocalDate()
		
				, equalTo(LocalDate.now()));
	}
	
	
	/*or -  RestAssured.get(POST_URL).then()
    .header("Date", date -> ZonedDateTime.parse(date, DateTimeFormatter.RFC_1123_DATE_TIME)
            .toLocalDate(), 
equalTo(LocalDate.now()));*/

}
