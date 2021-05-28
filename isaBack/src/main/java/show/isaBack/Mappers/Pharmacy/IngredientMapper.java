package show.isaBack.Mappers.Pharmacy;

import java.util.ArrayList;
import java.util.List;

import show.isaBack.DTO.drugDTO.IngredientDTO;
import show.isaBack.model.Ingredient;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public class IngredientMapper {

	public static UnspecifiedDTO<IngredientDTO> MapIngredientPersistenceToIngredientIdentifiableDTO(Ingredient ingredient){
		if(ingredient == null) throw new IllegalArgumentException();
		
		return new UnspecifiedDTO<IngredientDTO>(ingredient.getId(), new IngredientDTO(ingredient.getName()));
	}
	
	public static List<UnspecifiedDTO<IngredientDTO>> MapIngredientPersistenceListToIngredientIdentifiableDTOList(List<Ingredient> ingredients){
		
		List<UnspecifiedDTO<IngredientDTO>> ingredientsDTO = new ArrayList<UnspecifiedDTO<IngredientDTO>>();
		ingredients.forEach((i) -> ingredientsDTO.add(MapIngredientPersistenceToIngredientIdentifiableDTO(i)));
		return ingredientsDTO;
	}
}
