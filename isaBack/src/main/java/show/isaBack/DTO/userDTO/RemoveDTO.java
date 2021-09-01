package show.isaBack.DTO.userDTO;

import java.util.UUID;

public class RemoveDTO {
	private UUID pharmacyId;
	private UUID employeeId;
	
	public RemoveDTO() {
		
	}
	
	public RemoveDTO(UUID pharmacyId, UUID employeId) {
		super();
		this.pharmacyId = pharmacyId;
		this.employeeId = employeId;
	}

	public UUID getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(UUID pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public UUID getEmployeeId() {
		return employeeId;
	}

	public void setEmplloyeeId(UUID employeId) {
		this.employeeId = employeId;
	}
}
