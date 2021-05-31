package show.isaBack.DTO.pharmacyDTO;

import java.util.UUID;

import show.isaBack.model.Address;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public class UnspecifiedPharmacyWithDrugAndPrice extends UnspecifiedDTO<PharmacyDTO>{

	private double drugPrice;
	
	private int availableDrugCount;
	
	
	
	public UnspecifiedPharmacyWithDrugAndPrice() {
		super();
	}


	public UnspecifiedPharmacyWithDrugAndPrice(UUID id, String name, Address address, String description, double consultationPrice, double price, int count) {
		super(id, new PharmacyDTO(name, address,description, consultationPrice));
		this.drugPrice = price;
		this.availableDrugCount = count;
		
	}


	public double getDrugPrice() {
		return drugPrice;
	}


	public void setDrugPrice(double drugPrice) {
		this.drugPrice = drugPrice;
	}


	public int getAvailableDrugCount() {
		return availableDrugCount;
	}


	public void setAvailableDrugCount(int availableDrugCount) {
		this.availableDrugCount = availableDrugCount;
	}
	
	
	
	
}
