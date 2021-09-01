package show.isaBack.DTO.drugDTO;

import java.util.UUID;

public class AddDrugDTO {
	

	private UUID drugId;
	
	private UUID pharmacyId;
	
	private int count;
	
	private double price;
	
	public AddDrugDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AddDrugDTO(UUID drugId,UUID pharmacyId, int count, double price) {
		super();
		this.drugId = drugId;
		this.pharmacyId = pharmacyId;
		this.count = count;
		this.price = price;
	}

	public UUID getDrugId() {
		return drugId;
	}

	public void setDrugId(UUID drugId) {
		this.drugId = drugId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public UUID getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(UUID pharmacyId) {
		this.pharmacyId = pharmacyId;
	}




}
