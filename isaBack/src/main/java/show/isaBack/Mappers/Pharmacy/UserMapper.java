package show.isaBack.Mappers.Pharmacy;

//import org.apache.catalina.User;

import show.isaBack.DTO.userDTO.PharmacyAdminDTO;
import show.isaBack.DTO.userDTO.UserDTO;
import show.isaBack.model.PharmacyAdmin;
import show.isaBack.model.User;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public class UserMapper {
	
	public static UnspecifiedDTO<PharmacyAdminDTO> MapPharmacyAdminPersistenceToPharmacyAdminIdentifiableDTO(
			PharmacyAdmin pharmacyAdmin) {
		if(pharmacyAdmin == null) throw new IllegalArgumentException();

		return new UnspecifiedDTO<PharmacyAdminDTO>(pharmacyAdmin.getId(), new PharmacyAdminDTO(pharmacyAdmin.getEmail(), pharmacyAdmin.getName(), pharmacyAdmin.getSurname(), pharmacyAdmin.getAddress(),
				pharmacyAdmin.getPhoneNumber(), pharmacyAdmin.isActive(), pharmacyAdmin.getUserAuthorities()));
	}
	
	public static UnspecifiedDTO<UserDTO> MapUserPersistenceToUserUnspecifiedDTO(User user){
		if(user == null) throw new IllegalArgumentException();

		
		return new UnspecifiedDTO<UserDTO>(user.getId(), new UserDTO(user.getEmail(), user.getName(), user.getSurname(), user.getAddress(),
				user.getPhoneNumber(), user.isActive(), user.getUserAuthorities()));

	}
	
	public static UnspecifiedDTO<UserDTO> MapEmployeePersistenceToEmployeeIdentifiableDTO(User user) {
		if(user == null) throw new IllegalArgumentException();
		
		return new UnspecifiedDTO<UserDTO>(user.getId(), new UserDTO(user.getEmail(), user.getName(), user.getSurname(), user.getAddress(),
				user.getPhoneNumber(), user.isActive(), user.getUserAuthorities()));

	}

}
