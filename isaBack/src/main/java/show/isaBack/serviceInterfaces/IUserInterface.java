package show.isaBack.serviceInterfaces;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import show.isaBack.DTO.drugDTO.AllergenDTO;
import show.isaBack.DTO.userDTO.ChangePasswordDTO;
import show.isaBack.DTO.userDTO.PatientDTO;
import show.isaBack.DTO.userDTO.PatientsAllergenDTO;

import show.isaBack.DTO.userDTO.PhAdminDTO;

import show.isaBack.DTO.userDTO.PharmacistForAppointmentPharmacyGadeDTO;

import show.isaBack.DTO.userDTO.UserChangeInfoDTO;
import show.isaBack.DTO.userDTO.DermatologistWithGradeDTO;
import show.isaBack.DTO.userDTO.EmployeeGradeDTO;
import show.isaBack.DTO.userDTO.UserDTO;
import show.isaBack.DTO.userDTO.UserRegistrationDTO;

import show.isaBack.model.Pharmacy;
import show.isaBack.DTO.pharmacyDTO.PharmacyDTO;
import show.isaBack.DTO.userDTO.WorkTimeDTO;

import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public interface IUserInterface extends IService<UserDTO, UnspecifiedDTO<UserDTO>> { 
	
	boolean activatingAccountForPatient(UUID id);
	public UUID createPatient(UserRegistrationDTO patientRegistrationDTO);
	public UnspecifiedDTO<PatientDTO> getLoggedPatient();
	public UnspecifiedDTO<PhAdminDTO> getLoggedPhAdmin();
	public UUID getLoggedUserId(); 

	public UUID createDermatologist(UserRegistrationDTO entityDTO);
	public UUID createPharmacist(UserRegistrationDTO entityDTO);
	public UUID createAdmin(UserRegistrationDTO entityDTO);
	public UUID createPharmacyAdmin(UserRegistrationDTO entityDTO, UUID pharmacyId);
	

	public void updatePatient(UserChangeInfoDTO patientInfoChangeDTO);
	public void changePassword(ChangePasswordDTO changePasswordDTO);
	public void updatePhAdmin(UserChangeInfoDTO phadminInfoChangeDTO);
	
	public void addAllergenForPatient(PatientsAllergenDTO patientsAllergenDTO);
	public void removeAllergenForPatient(PatientsAllergenDTO patientsAllergenDTO);
	public List<UnspecifiedDTO<AllergenDTO>> getAllPatientsAllergens();
	

	public UUID createSupplier(UserRegistrationDTO entityDTO);
	
	public List<UnspecifiedDTO<PharmacistForAppointmentPharmacyGadeDTO>> fidnAllFreePharmacistsForSelectedPharmacyInDataRange(Date startDate, UUID pharmacyId);     

	public List<UnspecifiedDTO<PharmacistForAppointmentPharmacyGadeDTO>> fidnAllFreePharmacistsForSelectedPharmacyInDataRangeSortByGradeAscending(Date startDate, UUID pharmacyId);
	public List<UnspecifiedDTO<PharmacistForAppointmentPharmacyGadeDTO>> fidnAllFreePharmacistsForSelectedPharmacyInDataRangeSortByGradeDescending(Date startDate, UUID pharmacyId);
	public UserDTO getLoggedSupplier();
	public void updateSupplier(UserChangeInfoDTO supplierInfoChangeDTO);
	
	
	public double getAvgGradeForEmployee(UUID employeeID);
	public UUID getPhIdForPhAdmin();

	public List<UnspecifiedDTO<EmployeeGradeDTO>> findDermatologistsinPharmacy(UUID pharmacyId);
	public List<UnspecifiedDTO<EmployeeGradeDTO>> findPharmacistsinPharmacy(UUID phId);

	public void refreshPatientPenalty();

	public boolean isPatientSubscribedToPharmacy(UUID pharmacyId);
	public boolean subscribeToPharmacy(UUID pharmacyId);
	public boolean unsubscribeFromPharmacy(UUID pharmacyId);
	public void refreshPatientsPenalties();
	public void updateDermathologist(UserChangeInfoDTO dermathologistInfoChangeDTO);
	public UserDTO getLoggedDermathologist();
	public UserDTO getLoggedPharmacist();
	public void updatePharmacist(UserChangeInfoDTO pharmacistInfoChangeDTO);

	Pharmacy getPharmacyForLoggedDermatologist();
	public List<UnspecifiedDTO<UserDTO>> findPatientByNameAndSurname(String name, String surname);

	List<UnspecifiedDTO<WorkTimeDTO>>getScheduleForEmployee(UUID id);
	public List<UnspecifiedDTO<PharmacyDTO>> getPharmacies();
	public UnspecifiedDTO<PharmacyDTO> getPharmacy();
	UnspecifiedDTO<UserDTO> getPatientById(UUID patientId);



	
}
