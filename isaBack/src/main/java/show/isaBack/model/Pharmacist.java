package show.isaBack.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import show.isaBack.model.UserCharacteristics.UserType;



@Entity
public class Pharmacist extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@ManyToOne
	private Pharmacy pharmacy;

	public Pharmacist() {
		super();
	}

	public Pharmacist(String email, String password, String name, String surname, String address, String phoneNumber,
			boolean active, UserType userType) {
		super(email, password, name, surname, address, phoneNumber, active, userType);
		// TODO Auto-generated constructor stub
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	
	
	
	
	
}
