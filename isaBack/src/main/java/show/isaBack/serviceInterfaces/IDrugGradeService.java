package show.isaBack.serviceInterfaces;

import java.util.UUID;

import show.isaBack.DTO.drugDTO.DrugGradeDTO;



public interface IDrugGradeService {

	public DrugGradeDTO findPatientGradeForDrug(UUID drugId);
	public void createDrugGrade(DrugGradeDTO drugGradeDTO);
	public void updateDrugGrade(DrugGradeDTO drugGradeDTO);
	
}
