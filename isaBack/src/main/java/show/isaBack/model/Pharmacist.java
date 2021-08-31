package show.isaBack.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import show.isaBack.model.UserCharacteristics.UserType;



@Entity
public class Pharmacist extends User {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Pharmacy pharmacy;

	public Pharmacist() {
		super();
	}

	public Pharmacist(String email, String password, String name, String surname, String address, String phoneNumber,
			boolean active) {
		super(email, password, name, surname, address, phoneNumber, active);
		// TODO Auto-generated constructor stub
	}

	public Pharmacist(String email, String password, String name, String surname, String address, String phoneNumber,Pharmacy pharmacy) {
		super(email, password, name, surname, address, phoneNumber, true,UserType.PHARMACIST);
		//OVO TREBA IZMENITI KADA SE NAMESTI AKTIVACIJA!!!!!!!!!!!!!!!!!!!!
		
		this.pharmacy= pharmacy;
		
	}
	public Pharmacist(String email, String password, String name, String surname, String address, String phoneNumber,Pharmacy pharmacy,UserType userType ) {
		super(email, password, name, surname, address, phoneNumber, true,userType);
		//OVO TREBA IZMENITI KADA SE NAMESTI AKTIVACIJA!!!!!!!!!!!!!!!!!!!!
		
		this.pharmacy= pharmacy;
		
	}
	
	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	
	
}
	
	
	

