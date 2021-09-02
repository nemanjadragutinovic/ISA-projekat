package show.isaBack.DTO.drugDTO;

import java.util.UUID;

public class RemoveDrugDTO {
	UUID pharmacyId;
	UUID drugId;
	
	public RemoveDrugDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RemoveDrugDTO(UUID pharmacyId, UUID drugId) {
		super();
		this.pharmacyId = pharmacyId;
		this.drugId = drugId;
	}
	
	public UUID getPharmacyId() {
		return pharmacyId;
	}
	public void setPharmacyId(UUID pharmacyId) {
		this.pharmacyId = pharmacyId;
	}
	public UUID getDrugId() {
		return drugId;
	}
	public void setDrugId(UUID drugId) {
		this.drugId = drugId;
	}

	
}
