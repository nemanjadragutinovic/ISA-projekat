package show.isaBack.DTO.userDTO;

import java.util.List;

import show.isaBack.DTO.drugDTO.AllergenDTO;
import show.isaBack.model.Authority;
import show.isaBack.model.drugs.Allergen;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;



public class PatientDTO extends UserDTO {

	
	
	private List<UnspecifiedDTO<AllergenDTO>> allergens;
	
	public PatientDTO(String email, String name, String surname, String address, String phoneNumber, boolean active,
			List<Authority> authorities,List<UnspecifiedDTO<AllergenDTO>>  allergens ) {
		
		super(email, name, surname, address, phoneNumber, active, authorities);
		
		this.allergens=allergens;
		
	}

	public List<UnspecifiedDTO<AllergenDTO>> getAllergens() {
		return allergens;
	}

	public void setAllergens(List<UnspecifiedDTO<AllergenDTO>> allergens) {
		this.allergens = allergens;
	}

	
	
	
	
	
}
