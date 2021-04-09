package show.isaBack.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
public class Pharmacy {

	

	@Id
	private UUID id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Embedded
	private Address address;
	
	
	@Column(name = "description", nullable = false)
	private String description;


	
	public Pharmacy() {
		super();
		
	}
	
	
	
	public Pharmacy(UUID id, String name, String city, String street, String country, String postCode, String description) {
		super();
		this.id = id;
		this.name = name;
		this.address = new Address(city,street,country,postCode);
		this.description = description;
	}
	
	
	
	public Pharmacy(UUID id, String name, Address address, String description) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
	}


	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Address getAddress() {
		return address;
	}


	public void setAddress(Address address) {
		this.address = address;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
	
	
	
}
