package show.isaBack.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;



@Entity
public class Dermatologist extends User {
	
	private static final long serialVersionUID = 1L;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "dermatologist_pharmacy",
            joinColumns = @JoinColumn(name = "dermatologist_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "pharmacy_id", referencedColumnName = "id"))
    private List<Pharmacy> pharmacies;
	
	public Dermatologist() {
		super();
	}

	public Dermatologist(String email, String password, String name, String surname, String address, String phoneNumber,List<Pharmacy> pharmacies) {
		super(email, password, name, surname, address, phoneNumber, true);
		//OVO TREBA IZMENITI KADA SE NAMESTI AKTIVACIJA!!!!!!!!!!!!!!!!!!!!
		
		this.pharmacies= pharmacies;
		
	}
	
	

	public Dermatologist(UUID id, String email, String password, String name, String surname, String address,
			String phoneNumber, boolean active) {
		super(id, email, password, name, surname, address, phoneNumber, active);

		
	}

	public List<Pharmacy> getPharmacies() {
		return pharmacies;
	}

	public void setPharmacies(List<Pharmacy> pharmacies) {
		this.pharmacies = pharmacies;
	}

	public void addPharmacy(Pharmacy pharmacy) {
		if(this.pharmacies==null) {
			this.pharmacies=new ArrayList<Pharmacy>();
		}
		this.pharmacies.add(pharmacy);
	}
	
	public void removePharmacy(UUID pharmacyId) {
		if(this.pharmacies == null)
			return;
		
		for(Pharmacy pharmacy : pharmacies) {
			if(pharmacyId.equals(pharmacy.getId())) {
				pharmacies.remove(pharmacy);
				return;
			}
		}
	}
	

}
