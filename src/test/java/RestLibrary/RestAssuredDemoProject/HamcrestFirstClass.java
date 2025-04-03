package RestLibrary.RestAssuredDemoProject;

import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

public class HamcrestFirstClass {
	
	@Test
	public void TestEqual()
	{
		assertThat(200,equalTo(200));
	}

	@Test
	public void TestGreaterThan()
	{
		assertThat(200,greaterThan(100));
	}
	
	@Test
	public void TestLessThan()
	{
		assertThat(20,lessThan(100));
	}
	
	@Test
	public void TestLessThanEqual()
	{
		assertThat(100,lessThanOrEqualTo(100));
	}
	
	@Test
	public void TestGreaterThanEqual()
	{
		assertThat(100,greaterThanOrEqualTo(100));
	}
	
	@Test
	public void TestBetweek()
	{
		assertThat(150,allOf(greaterThanOrEqualTo(100),lessThan(200)));
	}
	
	@Test
	public void TestEither()
	{
		assertThat(100,either(equalTo(100)).or(equalTo(200)));
	}
	
	@Test
	public void TestCloseTo()
	{
		assertThat(9.5,closeTo(10,0.5));
	}
	
	@Test
	public void TestStringFunctions()
	{
		String strOne = "Vishakha Patil";
		String strTwo = "Vishakha   Patil  ";
		String strEmpty = "";
		String strBlank = "    ";
		String strEmail = "test90@gmail.com";

		assertThat(strOne,equalTo("Vishakha Patil"));
		
		assertThat(strOne,equalToIgnoringCase("VISHAKHa Patil"));
		
		assertThat(strOne,containsString("Vishakha"));
		
		assertThat(strOne,containsStringIgnoringCase("ViShakha"));
		
		assertThat(strOne,startsWith("Vishakha"));
		
		assertThat(strOne,startsWithIgnoringCase("VISHAKHA"));

		assertThat(strOne,endsWith("Patil"));
		
		assertThat(strOne,endsWithIgnoringCase("PATIl"));
		
		assertThat(strOne,startsWith("Vishakha"));
		
		assertThat(strTwo,equalToCompressingWhiteSpace("Vishakha Patil"));
		
		assertThat(strEmpty,is(emptyString()));
		
		assertThat(strBlank, is(blankString())); 

		assertThat(strEmail,matchesPattern("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9_]+\\.[a-zA-Z]{2,}"));
	}
	
}
