package show.isaBack.serviceInterfaces;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import show.isaBack.DTO.drugDTO.PharmacyDrugPriceDTO;
import show.isaBack.DTO.drugDTO.PharmacyERecipeDTO;
import show.isaBack.DTO.pharmacyDTO.PharmacyDTO;
import show.isaBack.DTO.pharmacyDTO.PharmacySearchDTO;
import show.isaBack.DTO.pharmacyDTO.PharmacyWithGradeAndPriceDTO;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public interface IPharmacyService extends IService<PharmacyDTO, UnspecifiedDTO<PharmacyDTO>>{

	public List<UnspecifiedDTO<PharmacyDTO>> getAllPharmacies();
	public List<UnspecifiedDTO<PharmacyDTO>> getAllPatientSubscribedPharmacies();
	
	public List<UnspecifiedDTO<PharmacyDTO>> getSearchedPharmacies(PharmacySearchDTO searchPharmacyRequest);
	public UUID createPharmacy(PharmacyDTO pharmacyDTO);
	
	public List<UnspecifiedDTO<PharmacyWithGradeAndPriceDTO>> findAllPharmaciesWhoHaveFreeAppointmentsForPeriodWithGradesAndPrice(Date startDate);    
	
	public List<UnspecifiedDTO<PharmacyWithGradeAndPriceDTO>> findAllPharmaciesWhoHaveFreeAppointmentsForPeriodWithGradesAndPriceSortByPriceAscending(Date startDate);
	public List<UnspecifiedDTO<PharmacyWithGradeAndPriceDTO>> findAllPharmaciesWhoHaveFreeAppointmentsForPeriodWithGradesAndPriceSortByPriceDescending(Date startDate);
	public List<UnspecifiedDTO<PharmacyWithGradeAndPriceDTO>> findAllPharmaciesWhoHaveFreeAppointmentsForPeriodWithGradesAndPriceSortByPharmacyGradeAscending(Date startDate);
	public List<UnspecifiedDTO<PharmacyWithGradeAndPriceDTO>> findAllPharmaciesWhoHaveFreeAppointmentsForPeriodWithGradesAndPriceSortByPharmacyGradeDescending(Date startDate);


	public UnspecifiedDTO<PharmacyWithGradeAndPriceDTO> convertPharmacyToPharmacyWithGradeAndPriceDTO(UUID phId);

	void updatePharmacy(UUID phID, PharmacyDTO pharmacyDTO);

	

	

	public List<UnspecifiedDTO<PharmacyDrugPriceDTO>> getAllPharmaciesWithDrugs(UUID id);
	public UUID buyDrugsWithQr(PharmacyERecipeDTO pharmacyERecipeDTO);
	public List<UnspecifiedDTO<PharmacyDrugPriceDTO>> sortQrPharmaciesByName(UUID id);
	public List<UnspecifiedDTO<PharmacyDrugPriceDTO>> sortQrPharmaciesByNameReverse(UUID id);
	public List<UnspecifiedDTO<PharmacyDrugPriceDTO>> sortQrPharmaciesByPrice(UUID id);
	public List<UnspecifiedDTO<PharmacyDrugPriceDTO>> sortQrPharmaciesByPriceReverse(UUID id);
	public List<UnspecifiedDTO<PharmacyDrugPriceDTO>> sortQrPharmaciesByGrade(UUID id);
	public List<UnspecifiedDTO<PharmacyDrugPriceDTO>> sortQrPharmaciesByGradeReverse(UUID id);
	public List<UnspecifiedDTO<PharmacyDrugPriceDTO>> sortQrPharmaciesByAddress(UUID id);
	public List<UnspecifiedDTO<PharmacyDrugPriceDTO>> sortQrPharmaciesByAddressReverse(UUID id);

}
