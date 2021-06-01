package show.isaBack.DTO.pharmacyDTO;



import show.isaBack.model.Address;
import show.isaBack.model.Pharmacy;

public class PharmacyDTO {

	
	
	
	private String name;	
	private Address address;		
	private String description;
	private double consultationPrice;
	private double grade;
	
	
	public PharmacyDTO() {
		super();
		
	}
	
	
	public PharmacyDTO(Pharmacy pharmacy) {
		super();
					
		this.name = pharmacy.getName();
		this.address = pharmacy.getAddress();
		this.description = pharmacy.getDescription();
		this.consultationPrice= pharmacy.getConsultationPrice();
	}
	
	

	public PharmacyDTO(Pharmacy pharmacy,	double pharmacyGrade) {
		super();
					
		this.name = pharmacy.getName();
		this.address = pharmacy.getAddress();
		this.description = pharmacy.getDescription();
		this.consultationPrice= pharmacy.getConsultationPrice();
		this.grade=pharmacyGrade;
	}
	
	
	
	
public PharmacyDTO(String name, String city, String street, String country, String postCode,double latitude, double longitude, String description,double consultationPrice) {
		super();
		this.name = name;
		this.address = new Address(city,street,country,postCode,longitude,latitude);
		this.description = description;
		this.consultationPrice= consultationPrice;
	}
	
	
	
	public PharmacyDTO( String name, Address address, String description,double consultationPrice) {
		super();
		this.name = name;
		this.address = address;
		this.description = description;
		this.consultationPrice= consultationPrice;
	}

	
	
	
	
	

	public double getGrade() {
		return grade;
	}


	public void setGrade(double grade) {
		this.grade = grade;
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


	public double getConsultationPrice() {
		return consultationPrice;
	}


	public void setConsultationPrice(double consultationPrice) {
		this.consultationPrice = consultationPrice;
	}
	
	

	
}