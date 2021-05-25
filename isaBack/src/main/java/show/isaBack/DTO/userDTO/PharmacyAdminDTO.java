package show.isaBack.DTO.userDTO;

import java.util.List;

import show.isaBack.model.Authority;

public class PharmacyAdminDTO extends UserDTO{
	
	public PharmacyAdminDTO(String email, String name, String surname, String address, String phoneNumber, boolean active,
			List<Authority> userAuthorities) {
		super(email, name, surname, address, phoneNumber, active, userAuthorities);
	}

}
