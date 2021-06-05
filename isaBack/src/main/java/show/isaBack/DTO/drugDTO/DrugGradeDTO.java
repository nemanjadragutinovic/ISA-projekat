package show.isaBack.DTO.drugDTO;

import java.util.Date;
import java.util.UUID;

public class DrugGradeDTO {

	private UUID drugId;
 
	private int grade;

	public DrugGradeDTO() {
		super();
	}

	public DrugGradeDTO(UUID drugId, int grade) {
		super();
		this.drugId = drugId;
		this.grade = grade;
	}

	public UUID getDrugId() {
		return drugId;
	}

	public void setDrugId(UUID drugId) {
		this.drugId = drugId;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	
	
}
