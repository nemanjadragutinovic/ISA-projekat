package show.isaBack.DTO.pharmacyDTO;



import show.isaBack.model.Address;
import show.isaBack.model.Pharmacy;

public class PharmacyDTO {

	
	
		
	private String name;	
	private String address;		
	private String description;
	
	
	
	
	public PharmacyDTO() {
		super();
		
	}
	
	
	public PharmacyDTO(Pharmacy pharmacy) {
		super();
					
		this.name = pharmacy.getName();
		this.address = pharmacy.getAddress();
		this.description = pharmacy.getDescription();
		
	}
	
	
	public PharmacyDTO(String name, String address, String description) {
		super();
		this.name = name;
		this.address = address;
		this.description = description;
	}
	
	
	
	

	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	

	
}
