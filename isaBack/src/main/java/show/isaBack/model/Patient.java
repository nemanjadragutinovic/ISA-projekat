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
	
	@ManyToMany
	@JoinTable(name = "patient_pharmacy_subscribe")
    private List<Pharmacy> pharmacies;
	
	
	

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
	
	
	public List<Allergen> getAllergens() {
		return allergens;
	}

	public void setAllergens(List<Allergen> allergens) {
		this.allergens = allergens;
	}
	
	public void addAllergen(Allergen allergen) {
		
		if(allergens == null)
			this.allergens = new ArrayList<Allergen>();
		
		this.allergens.add(allergen);
	}
	
	public void removeAllergen(String allergenName) {
		

		if(allergens == null)
			return;
		
		for (Allergen currentAllergen : this.allergens) {
			if(currentAllergen.getName().equals(allergenName)) {
				this.allergens.remove(currentAllergen);
				break;
			}
		}
	}
	
public void addSubscribeToPharmacy(Pharmacy pharmacy) {
		
		if(pharmacies == null)
			this.pharmacies = new ArrayList<Pharmacy>();
		
		this.pharmacies.add(pharmacy);
	}

	public void removeSubscribeFromPharmacy(UUID pharmacyId) {
		
		if(pharmacies == null)
			return;
		
		for (Pharmacy pharmacy : this.pharmacies) {
			System.out.println(pharmacy.getId());
			if(pharmacy.getId().equals(pharmacyId)) {
				System.out.println("brisem" + pharmacy.getId());
				this.pharmacies.remove(pharmacy);
				break;
			}
		}
	}
	
	public boolean isPatientSubscribedToPharmacy(UUID pharmacyId) {
		if(pharmacies == null)
			return false;
		
		for (Pharmacy pharmacy : this.pharmacies) {
			if(pharmacy.getId().equals(pharmacyId)) 
				return true;
		}
		
		return false;
	}

	public List<Pharmacy> getPharmacies() {
		return pharmacies;
	}

}
