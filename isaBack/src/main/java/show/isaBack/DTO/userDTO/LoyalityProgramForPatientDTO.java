package show.isaBack.DTO.userDTO;

import show.isaBack.model.LoyalityCategory;

public class LoyalityProgramForPatientDTO {

	private LoyalityCategory loyalityCategory;
	
	private int examinationDiscount;
	
	private int consultationDiscount;

	private int drugDiscount;

	public LoyalityProgramForPatientDTO() {
		super();
	}

	public LoyalityProgramForPatientDTO(LoyalityCategory loyalityCategory, int examinationDiscount,
			int consultationDiscount, int drugDiscount) {
		super();
		this.loyalityCategory = loyalityCategory;
		this.examinationDiscount = examinationDiscount;
		this.consultationDiscount = consultationDiscount;
		this.drugDiscount = drugDiscount;
	}

	public LoyalityCategory getLoyalityCategory() {
		return loyalityCategory;
	}

	public void setLoyalityCategory(LoyalityCategory loyalityCategory) {
		this.loyalityCategory = loyalityCategory;
	}

	public int getExaminationDiscount() {
		return examinationDiscount;
	}

	public void setExaminationDiscount(int examinationDiscount) {
		this.examinationDiscount = examinationDiscount;
	}

	public int getConsultationDiscount() {
		return consultationDiscount;
	}

	public void setConsultationDiscount(int consultationDiscount) {
		this.consultationDiscount = consultationDiscount;
	}

	public int getDrugDiscount() {
		return drugDiscount;
	}

	public void setDrugDiscount(int drugDiscount) {
		this.drugDiscount = drugDiscount;
	}
	
	
	
	
}
