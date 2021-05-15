package show.isaBack.interfaceRepository.drugRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import show.isaBack.model.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID>{

	Ingredient findByName ( String name );

}