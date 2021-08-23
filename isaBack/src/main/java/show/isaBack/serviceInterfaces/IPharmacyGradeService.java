package show.isaBack.serviceInterfaces;

import java.util.UUID;

import show.isaBack.DTO.pharmacyDTO.PharmacyGradeDTO;

public interface IPharmacyGradeService {

	public double getAvgGradeForPharmacy(UUID pharmacyId);
	public PharmacyGradeDTO findPatientGradeForPharmacy(UUID pharmacyId);
	public void updatePharmacyGrade(PharmacyGradeDTO pharmacyGradeDTO);
	public void createPharmacyGrade(PharmacyGradeDTO pharmacyGradeDTO);
	
}
