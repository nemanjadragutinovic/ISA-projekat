package show.isaBack.DTO.pharmacyDTO;

public class PharmacySearchDTO {

	private String name;
	private String street;
	private String city;
	private String country;
	
	
	
	
	
	public PharmacySearchDTO() {
		super();
	}

	public PharmacySearchDTO(String name, String street, String city, String country) {
		super();
		this.name = name;
		this.street = street;
		this.city = city;
		this.country = country;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	
	
	
}
