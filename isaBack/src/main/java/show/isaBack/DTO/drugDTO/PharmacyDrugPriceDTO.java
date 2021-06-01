package show.isaBack.DTO.drugDTO;

import show.isaBack.DTO.pharmacyDTO.PharmacyDTO;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public class PharmacyDrugPriceDTO {
	
	private UnspecifiedDTO<PharmacyDTO> pharmacy;
	
	private double price;
	
	private double grade;

	public PharmacyDrugPriceDTO() {}
	
	public PharmacyDrugPriceDTO(UnspecifiedDTO<PharmacyDTO> pharmacy, double grade, double price) {
		super();
		this.pharmacy = pharmacy;
		this.price = price;
		this.grade = grade;
	}

	public UnspecifiedDTO<PharmacyDTO> getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(UnspecifiedDTO<PharmacyDTO> pharmacy) {
		this.pharmacy = pharmacy;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
	
}