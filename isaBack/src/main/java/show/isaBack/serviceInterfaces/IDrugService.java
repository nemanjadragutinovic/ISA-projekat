package show.isaBack.serviceInterfaces;

import java.util.List;
import java.util.UUID;

import show.isaBack.DTO.AppointmentDTO.IdDTO;
import show.isaBack.DTO.drugDTO.AddDrugDTO;
import show.isaBack.DTO.drugDTO.DrugDTO;
import show.isaBack.DTO.drugDTO.DrugInstanceDTO;
import show.isaBack.DTO.drugDTO.DrugReservationDTO;
import show.isaBack.DTO.drugDTO.DrugReservationResponseDTO;
import show.isaBack.DTO.drugDTO.DrugWithEreceiptsDTO;
import show.isaBack.DTO.drugDTO.DrugWithPriceDTO;
import show.isaBack.DTO.drugDTO.DrugsWithGradesDTO;
import show.isaBack.DTO.drugDTO.EditDrugPriceDTO;

import show.isaBack.DTO.drugDTO.EmployeeReservationDrugDTO;

import show.isaBack.DTO.drugDTO.EditStorageDTO;

import show.isaBack.DTO.drugDTO.EreceiptDTO;
import show.isaBack.DTO.drugDTO.IngredientDTO;
import show.isaBack.DTO.drugDTO.ManufacturerDTO;
import show.isaBack.DTO.drugDTO.RemoveDrugDTO;
import show.isaBack.DTO.pharmacyDTO.UnspecifiedPharmacyWithDrugAndPrice;
import show.isaBack.model.drugs.DrugStorageQuantityException;
import show.isaBack.model.drugs.EReceiptStatus;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;



public interface IDrugService extends IService<DrugInstanceDTO, UnspecifiedDTO<DrugInstanceDTO>>{
	
	List<UnspecifiedDTO<DrugDTO>> getAllDrugs();
	List<UnspecifiedDTO<DrugDTO>> getSearchedDrug(DrugDTO searchDrugsRequest);
	public UUID addDrugReplacement(UUID id, UUID replacement_id);
	public List<UnspecifiedDTO<ManufacturerDTO>>  findDrugManufacturers();
	public UUID addDrugManufacturer(UUID id, UUID manufacturerId);
	public UUID addDrugIngredients(UUID id, IngredientDTO entityDTO);
	public List<UnspecifiedDTO<DrugInstanceDTO>> findAllDrugKinds();
	public List<UnspecifiedDTO<DrugsWithGradesDTO>> searchDrugs(String name, double gradeFrom, double gradeTo, String drugKind);
	public double findAvgGradeForDrug(UUID drugId);
	public List<UnspecifiedDTO<DrugsWithGradesDTO>> findDrugsWithGrades();

	public boolean isQrCodeValid(String id);

	public List<UnspecifiedPharmacyWithDrugAndPrice> findPharmaciesByDrugIdWithDrugPrice(UUID drugId);
	public void createDrugReservation(DrugReservationDTO drugReservationDTO);
	public List<UnspecifiedDTO<DrugReservationResponseDTO>> findAllFuturePatientsDrugReservation();
	public void cancelPatientDrugReservation(IdDTO drugIdObject);
	public List<UnspecifiedDTO<DrugReservationResponseDTO>> findAllhistoryPatientsDrugReservation();
	public List<UnspecifiedDTO<EreceiptDTO>> findAllPatientsEreceipts();
	public List<UnspecifiedDTO<EreceiptDTO>> findAllPatientsEreceiptsSortByDateAscending();
	public List<UnspecifiedDTO<EreceiptDTO>> findAllPatientsEreceiptsSortByDateDescending();
	public List<UnspecifiedDTO<EreceiptDTO>> findAllPatientsEreceiptsSortByDateAscendingWithStatus(EReceiptStatus searchStatus);
	public List<UnspecifiedDTO<EreceiptDTO>> findAllPatientsEreceiptsSortByDateDescendingWithStatus(EReceiptStatus searchStatus);
	public List<UnspecifiedDTO<EreceiptDTO>> findAllPatientsEreceiptsWithStatus(EReceiptStatus searchStatus);
	public List<UnspecifiedDTO<DrugWithEreceiptsDTO>> findAllPatientsPRoccesedDrugsFromEreceipts();
	public void refreshPatientDrugsReservations();
	List<UnspecifiedDTO<DrugWithPriceDTO>> findDrugsInPharmacyWithPrice(UUID pharmacyId);
	List<UnspecifiedDTO<DrugWithPriceDTO>> searchDrugsInPharmacy(String name, double gradeFrom, double gradeTo,
			String manufacturer, UUID pharmacyId);

	List<UnspecifiedDTO<DrugInstanceDTO>> findDrugsPatientIsNotAllergicTo(UUID patientId);

	List<UnspecifiedDTO<DrugDTO>> findDrugsWhichArentInPharmacy(UUID pharmacyId);
	void addDrug(AddDrugDTO addDTO);
	boolean editDrugPrice(EditDrugPriceDTO editPriceDTO);

	void isDrugAvailableInPharamcy(UUID drugId, int amount) throws DrugStorageQuantityException;
//	void reduceAmountOfReservedDrug(UUID drugId, UUID pharmacyId, int count);
	



	boolean removeDrugFromPharmacy(RemoveDrugDTO removeDrugDTO);
	boolean editCountDrug(EditStorageDTO editStorageDTO);

	

	
}
