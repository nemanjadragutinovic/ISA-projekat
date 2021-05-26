package show.isaBack.DTO.pharmacyDTO;

import show.isaBack.model.Address;

public class PharmacyWithGradeAndPriceDTO {

	private String name;	
	
	private Address address;
	
	private double grade;
	
	private double price;

	public PharmacyWithGradeAndPriceDTO() {
		super();
	}

	public PharmacyWithGradeAndPriceDTO(String name, Address address, double grade, double price) {
		super();
		this.name = name;
		this.address = address;
		this.grade = grade;
		this.price = price;
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

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
	
	
}
