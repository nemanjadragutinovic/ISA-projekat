package show.isaBack.model;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import show.isaBack.model.drugs.Allergen;





@Entity
public class Patient extends User {
	
	private static final long serialVersionUID = 1L;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "patient_allergen",
            joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "allergen_id", referencedColumnName = "id"))
    private List<Allergen> allergens;
	
	
	public Patient() {
		super();
	}

	public Patient(String email, String password, String name, String surname, String address, String phoneNumber) {
		super(email, password, name, surname, address, phoneNumber, true);
		//OVO TREBA IZMENITI KADA SE NAMESTI AKTIVACIJA!!!!!!!!!!!!!!!!!!!!
		this.allergens = new ArrayList<Allergen>();
		
	}
	
	

	public Patient(UUID id, String email, String password, String name, String surname, String address,
			String phoneNumber, boolean active) {
		super(id, email, password, name, surname, address, phoneNumber, active);

		
	}
	
	public void addAllergen(Allergen allergen) {
		
		if(allergens == null)
			this.allergens = new ArrayList<Allergen>();
		
		this.allergens.add(allergen);
	}

}
