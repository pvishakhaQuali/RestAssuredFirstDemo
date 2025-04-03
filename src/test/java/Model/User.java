package Model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

	private int id;

	@JsonProperty("email")
	private String emailAdress;

	private String username;
	
	private String phone;
		
	private String firstname;
	
	private String lastname;

	@JsonProperty("address")
	private Adress Adress;
	
	@JsonProperty("name")
	public void deserelizeAndPopulateName(Map<String,?>nameMap)
	{
		firstname = (String) nameMap.get("firstname");
		lastname = (String) nameMap.get("lastname");

	}
	
	/*
	 * public Adress getAdress() { return Adress; }
	 * 
	 * public void setAdress(Adress adress) { Adress = adress; }
	 * 
	 * public int getId() { return id; }
	 * 
	 * public void setId(int id) { this.id = id; }
	 * 
	 * @JsonProperty("email") public String getEmailAdress() { return emailAdress; }
	 * 
	 * public void setEmailAdress(String emailAdress) { this.emailAdress =
	 * emailAdress; }
	 * 
	 * public String getUsername() { return username; }
	 * 
	 * public void setUsername(String username) { this.username = username; }
	 * 
	 * public String getPhone() { return phone; }
	 * 
	 * public void setPhone(String phone) { this.phone = phone; }
	 */

	
	/*
	 * public String getFirstname() { return firstname; }
	 * 
	 * public void setFirstname(String firstname) { this.firstname = firstname; }
	 * 
	 * public String getLastname() { return lastname; }
	 * 
	 * public void setLastname(String lastname) { this.lastname = lastname; }
	 */
}
