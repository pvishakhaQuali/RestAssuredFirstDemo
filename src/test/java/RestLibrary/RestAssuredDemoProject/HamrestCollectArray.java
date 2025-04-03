package RestLibrary.RestAssuredDemoProject;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class HamrestCollectArray {

	@Test
	public void testIfArrayFunction()
	{
		String strArray[]= {"A","B","C","D"};
		
		assertThat(strArray,arrayWithSize(4));   // size
		
		assertThat(strArray,hasItemInArray("A"));
		
		assertThat(strArray,arrayContainingInAnyOrder("C","D","A","B"));
		
		assertThat(strArray,arrayContaining("A","B","C","D"));
		
	}
	
	@Test
	public void testIfMapFunction()
	{
	  Map<String,Integer>hm = new HashMap<String,Integer>();
	  hm.put("A", 1);
	  hm.put("B", 2);
	  hm.put("C", 3);
	  
	  assertThat(hm,hasKey("A"));
	  assertThat(hm,hasValue(1));
	  assertThat(hm,hasEntry("A",1));
	}
	
	@Test
	public void testIfListFunction()
	{
		List<String> strVal = new ArrayList<>();
		List<Integer> lstValue = Arrays.asList(1,2,3,4,5);
		List<String> strValue = Arrays.asList("Vishu","Vish","Vi");

		assertThat(strVal,empty());
		
		assertThat(lstValue,hasSize(5));
		
		assertThat(lstValue,hasItem(1));
		
		assertThat(lstValue,hasItems(1,2));

		assertThat(lstValue,contains(1,2,3,4,5));
		
		assertThat(lstValue,containsInAnyOrder(2,5,1,3,4));

		assertThat(strValue,everyItem(startsWith("V")));
	}
	
}
