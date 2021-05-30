package show.isaBack.Mappers.Pharmacy;

import java.util.ArrayList;
import java.util.List;

import show.isaBack.DTO.drugDTO.AllergenDTO;
import show.isaBack.model.drugs.Allergen;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public class AllergenMapper {

	public static UnspecifiedDTO<AllergenDTO> MapAllergenPersistenceToAllergenIdentifiableDTO(Allergen allergen){
		if(allergen == null) throw new IllegalArgumentException();
		
		return new UnspecifiedDTO<AllergenDTO>(allergen.getId(), new AllergenDTO(allergen.getName()));
	}
	
	public static List<UnspecifiedDTO<AllergenDTO>> MapAllergenPersistenceListToAllergenIdentifiableDTOList(List<Allergen> allergens){
		
		List<UnspecifiedDTO<AllergenDTO>> allergeListDTO = new ArrayList<UnspecifiedDTO<AllergenDTO>>();
		allergens.forEach((a) -> allergeListDTO.add(MapAllergenPersistenceToAllergenIdentifiableDTO(a)));
		return allergeListDTO;
	}
}
