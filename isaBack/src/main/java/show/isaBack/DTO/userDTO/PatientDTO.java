package show.isaBack.DTO.userDTO;

import java.util.List;

import show.isaBack.DTO.drugDTO.AllergenDTO;
import show.isaBack.model.Authority;
import show.isaBack.model.drugs.Allergen;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;



public class PatientDTO extends UserDTO {

	private int penalty;
	
	private int points;
	
	private List<UnspecifiedDTO<AllergenDTO>> allergens;
	
	private LoyalityProgramForPatientDTO loyalityProgramForPatientDTO;
	
	public PatientDTO(String email, String name, String surname, String address, String phoneNumber, boolean active,
			List<Authority> authorities,List<UnspecifiedDTO<AllergenDTO>>  allergens, int penalty, int points, LoyalityProgramForPatientDTO loyalityProgramForPatientDTO ) {
		
		super(email, name, surname, address, phoneNumber, active, authorities);
		
		this.allergens=allergens;
		this.penalty=penalty;
		this.points=points;
		this.loyalityProgramForPatientDTO=loyalityProgramForPatientDTO;
		
	}

	public int getPenalty() {
		return penalty;
	}

	public void setPenalty(int penalty) {
		this.penalty = penalty;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public List<UnspecifiedDTO<AllergenDTO>> getAllergens() {
		return allergens;
	}

	public void setAllergens(List<UnspecifiedDTO<AllergenDTO>> allergens) {
		this.allergens = allergens;
	}

	public LoyalityProgramForPatientDTO getLoyalityProgramForPatientDTO() {
		return loyalityProgramForPatientDTO;
	}

	public void setLoyalityProgramForPatientDTO(LoyalityProgramForPatientDTO loyalityProgramForPatientDTO) {
		this.loyalityProgramForPatientDTO = loyalityProgramForPatientDTO;
	}
	
	
	
	

	
	
	
	
	
}
