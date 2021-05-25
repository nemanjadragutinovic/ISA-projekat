package show.isaBack.DTO.userDTO;

import java.util.List;


import show.isaBack.model.Authority;


public class PhAdminDTO extends UserDTO{

	public PhAdminDTO(String email, String name, String surname, String address, String phoneNumber, boolean active,
			List<Authority> authorities) {
		super(email, name, surname, address, phoneNumber, active, authorities);
		
	}

	
}
