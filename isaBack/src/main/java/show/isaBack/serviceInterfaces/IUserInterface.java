package show.isaBack.serviceInterfaces;

import java.util.UUID;

import show.isaBack.DTO.userDTO.ChangePasswordDTO;
import show.isaBack.DTO.userDTO.PatientDTO;


import show.isaBack.DTO.userDTO.UserChangeInfoDTO;

import show.isaBack.DTO.userDTO.UserDTO;
import show.isaBack.DTO.userDTO.UserRegistrationDTO;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public interface IUserInterface extends IService<UserDTO, UnspecifiedDTO<UserDTO>> { 
	
	boolean activatingAccountForPatient(UUID id);
	public UUID createPatient(UserRegistrationDTO patientRegistrationDTO);
	public UnspecifiedDTO<PatientDTO> getLoggedPatient();
	public UUID getLoggedUserId(); 

	public UUID createDermatologist(UserRegistrationDTO entityDTO);
	public UUID createAdmin(UserRegistrationDTO entityDTO);
	public UUID createPharmacyAdmin(UserRegistrationDTO entityDTO, UUID pharmacyId);
	

	public void updatePatient(UserChangeInfoDTO patientInfoChangeDTO);
	public void changePassword(ChangePasswordDTO changePasswordDTO);
	public UUID createSupplier(UserRegistrationDTO entityDTO);


}
