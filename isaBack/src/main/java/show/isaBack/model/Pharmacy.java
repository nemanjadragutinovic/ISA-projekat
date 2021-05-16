package show.isaBack.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;



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
	
	
	
	public Pharmacy() {}
	
	public Pharmacy(UUID id, String name, String description, Address address) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.address = address;
		
	}
	
	public Pharmacy(String name, String description, Address address) {
		this(UUID.randomUUID(), name, description, address);
	}
	public Pharmacy(String name, String city, String street,String country,String postcode,String description) {
		this(UUID.randomUUID(), name, description, new Address(city,street,country,postcode));
		
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	

	
	
	
	
	
	
	
}
