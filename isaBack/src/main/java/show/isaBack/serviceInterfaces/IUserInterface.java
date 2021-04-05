package show.isaBack.serviceInterfaces;

import java.util.UUID;

import show.isaBack.DTO.userDTO.PatientRegistrationDTO;
import show.isaBack.DTO.userDTO.UserDTO;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public interface IUserInterface extends IService<UserDTO, UnspecifiedDTO<UserDTO>> { 
	
	boolean activatingAccountForPatient(UUID id);
	public UUID createPatient(PatientRegistrationDTO patientRegistrationDTO);

}
