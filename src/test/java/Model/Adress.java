package Model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Adress {

	private String city;
	
	private String street;
	
	private int number;
	
	private String zipcode;
	
	 private String lat;
	   
	@JsonProperty("geolocation")
	public void getDataFromGeoLocation(Map<String,?>mapGeo)
	{
		lat = (String) mapGeo.get("lat");
	}
	
	/*
	 * public String getLat() { return lat; }
	 * 
	 * public void setLat(String lat) { this.lat = lat; }
	 * 
	 * public String getCity() { return city; }
	 * 
	 * public void setCity(String city) { this.city = city; }
	 * 
	 * public String getStreet() { return street; }
	 * 
	 * public void setStreet(String street) { this.street = street; }
	 * 
	 * public int getNumber() { return number; }
	 * 
	 * public void setNumber(int number) { this.number = number; }
	 * 
	 * public String getZipcode() { return zipcode; }
	 * 
	 * public void setZipcode(String zipcode) { this.zipcode = zipcode; }
	 * 
	 */
}
