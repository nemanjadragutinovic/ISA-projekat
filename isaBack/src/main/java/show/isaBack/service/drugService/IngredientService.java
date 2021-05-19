package show.isaBack.service.drugService;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import show.isaBack.DTO.drugDTO.IngredientDTO;
import show.isaBack.DTO.userDTO.AuthorityDTO;
import show.isaBack.interfaceRepository.drugRepository.IngredientRepository;
import show.isaBack.model.Ingredient;
import show.isaBack.serviceInterfaces.IIngredientService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

@Service
public class IngredientService implements IIngredientService{
	
	@Autowired
	private IngredientRepository ingredientRepository;

	@Override
	public List<UnspecifiedDTO<AuthorityDTO>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnspecifiedDTO<IngredientDTO> findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID create(IngredientDTO entityDTO) {
		if(findByIngredientName(entityDTO.getName()) != null)
			update(entityDTO,findByIngredientName(entityDTO.getName()).getId());
		Ingredient ingredient = CreateIngredientFromDTO(entityDTO);
		ingredientRepository.save(ingredient);
		return ingredient.getId();
	}
	
	private Ingredient findByIngredientName(String name) {
		return ingredientRepository.findByName(name);
	}
	private Ingredient CreateIngredientFromDTO(IngredientDTO ingredientDTO) {
		return new Ingredient(ingredientDTO.getName());
	}

	@Override
	public void update(IngredientDTO entityDTO, UUID id) {
		Ingredient ingredient = CreateIngredientFromDTO(entityDTO, id);
		ingredientRepository.save(ingredient);
	}
	
	private Ingredient CreateIngredientFromDTO(IngredientDTO ingredientDTO, UUID id) {
		return new Ingredient(id, ingredientDTO.getName());
	}

	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UnspecifiedDTO<IngredientDTO> findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
