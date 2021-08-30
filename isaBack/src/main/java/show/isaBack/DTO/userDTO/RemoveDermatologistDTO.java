package show.isaBack.DTO.userDTO;

import java.util.UUID;

public class RemoveDermatologistDTO {
	private UUID pharmacyId;
	private UUID dermatologistId;
	
	public RemoveDermatologistDTO() {
		
	}
	
	public RemoveDermatologistDTO(UUID pharmacyId, UUID dermatologistId) {
		super();
		this.pharmacyId = pharmacyId;
		this.dermatologistId = dermatologistId;
	}

	public UUID getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(UUID pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public UUID getDermatologistId() {
		return dermatologistId;
	}

	public void setDermatologistId(UUID dermatologistId) {
		this.dermatologistId = dermatologistId;
	}
}
