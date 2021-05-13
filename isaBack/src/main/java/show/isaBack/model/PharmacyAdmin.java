package show.isaBack.model;

import java.util.UUID;

public class PharmacyAdmin extends User {
	
	private static final long serialVersionUID = 1L;
	
	public PharmacyAdmin() {
		super();
	}

	public PharmacyAdmin(String email, String password, String name, String surname, String address, String phoneNumber) {
		super(email, password, name, surname, address, phoneNumber, true);
		//OVO TREBA IZMENITI KADA SE NAMESTI AKTIVACIJA!!!!!!!!!!!!!!!!!!!!
		
		
	}
	
	

	public PharmacyAdmin(UUID id, String email, String password, String name, String surname, String address,
			String phoneNumber, boolean active) {
		super(id, email, password, name, surname, address, phoneNumber, active);

		
	}

}
