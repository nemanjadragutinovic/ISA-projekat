package show.isaBack.serviceInterfaces;

import show.isaBack.DTO.drugDTO.IngredientDTO;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public interface IIngredientService extends IService<IngredientDTO, UnspecifiedDTO<IngredientDTO>>{

	UnspecifiedDTO<IngredientDTO> findByName(String name);

}

