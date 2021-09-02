package show.isaBack.DTO.drugDTO;

import java.util.UUID;

public class EditStorageDTO {
	private UUID drugInstanceId;
	private UUID pharmacyId;
	private int count;
	
	public EditStorageDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EditStorageDTO(UUID drugInstanceId, UUID pharmacyId, int count) {
		super();
		this.drugInstanceId = drugInstanceId;
		this.pharmacyId = pharmacyId;
		this.count = count;
	}

	public UUID getDrugInstanceId() {
		return drugInstanceId;
	}
	public void setDrugInstanceId(UUID drugInstanceId) {
		this.drugInstanceId = drugInstanceId;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public UUID getPharmacyId() {
		return pharmacyId;
	}
	public void setPharmacyId(UUID pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

}
