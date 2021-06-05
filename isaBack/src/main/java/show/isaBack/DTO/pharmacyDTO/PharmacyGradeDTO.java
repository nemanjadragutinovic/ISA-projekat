package show.isaBack.DTO.pharmacyDTO;


import java.util.UUID;

public class PharmacyGradeDTO {

	private UUID pharmacyId;
	
	private int grade;

	public PharmacyGradeDTO() {
		super();
	}

	public PharmacyGradeDTO(UUID pharmacyId, int grade) {
		super();
		this.pharmacyId = pharmacyId;
		this.grade = grade;
	}

	public UUID getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(UUID pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	
	
	
}
