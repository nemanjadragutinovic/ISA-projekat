package show.isaBack.DTO.userDTO;



public class EmployeeGradeDTO {

	private String name;
    
	private String surname;
	
	private String email;
    
	private String address;
	    
	private String phoneNumber;
	    
	private double grade;

	
	
	public EmployeeGradeDTO() {
		super();
	}


	public EmployeeGradeDTO(String name, String surname, String email, String address, String phoneNumber,
			double grade) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.grade = grade;
	}

	
	public EmployeeGradeDTO(String name, String surname, String email, String address, String phoneNumber) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.address = address;
		this.phoneNumber = phoneNumber;
		
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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public double getGrade() {
		return grade;
	}


	public void setGrade(double grade) {
		this.grade = grade;
	}
	
	
	
	
}
