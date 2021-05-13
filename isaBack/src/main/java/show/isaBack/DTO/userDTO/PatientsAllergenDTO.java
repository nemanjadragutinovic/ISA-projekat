package show.isaBack.DTO.userDTO;

import java.util.UUID;

public class PatientsAllergenDTO {

	private UUID patientId;	
	private String allergenName;
	
	public PatientsAllergenDTO() {
		super();
		
	}

	public UUID getPatientId() {
		return patientId;
	}

	public void setPatientId(UUID patientId) {
		this.patientId = patientId;
	}

	public String getAllergenName() {
		return allergenName;
	}

	public void setAllergenName(String allergenName) {
		this.allergenName = allergenName;
	}
	
	
	
	
	
}	
