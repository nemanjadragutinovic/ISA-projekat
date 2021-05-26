package show.isaBack.DTO.userDTO;

public class PharmacistForAppointmentPharmacyGadeDTO {

	private String name;
    
	private String surname;
    	    	    
	private double grade;
	
	private String pharmacyName;

   
	public PharmacistForAppointmentPharmacyGadeDTO() {}
	
	public PharmacistForAppointmentPharmacyGadeDTO(String name, String surname, double grade) {
		super();
		this.name = name;
		this.surname = surname;
		this.grade=grade;
	}

	
	public PharmacistForAppointmentPharmacyGadeDTO(String name, String surname, double grade,String pharmacyName) {
		super();
		this.name = name;
		this.surname = surname;
		this.grade=grade;
		this.pharmacyName=pharmacyName;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	public String getPharmacyName() {
		return pharmacyName;
	}

	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}
	
}
