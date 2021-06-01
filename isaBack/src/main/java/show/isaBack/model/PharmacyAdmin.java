package show.isaBack.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class PharmacyAdmin extends User {
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Pharmacy pharmacy;
	
	public PharmacyAdmin() {
		super();
	}

	public PharmacyAdmin(String email, String password, String name, String surname, String address, String phoneNumber, Pharmacy pharmacy) {
		super(email, password, name, surname, address, phoneNumber, true);
		this.pharmacy= pharmacy;
		//OVO TREBA IZMENITI KADA SE NAMESTI AKTIVACIJA!!!!!!!!!!!!!!!!!!!!
		
		
	}
	
	

	public PharmacyAdmin(UUID id, String email, String password, String name, String surname, String address,
			String phoneNumber, boolean active, Pharmacy pharmacy) {
		super(id, email, password, name, surname, address, phoneNumber, active);
		this.pharmacy= pharmacy;

		
	}
	
	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	

}
