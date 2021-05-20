package show.isaBack.controller.drugController;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import show.isaBack.DTO.drugDTO.IngredientDTO;
import show.isaBack.security.TokenUtils;
import show.isaBack.service.drugService.IngredientService;

@RestController
@RequestMapping(value = "/ingredients", produces = MediaType.APPLICATION_JSON_VALUE)
public class IngredientController {
	
	@Autowired
	private IngredientService ingredientService;
	
	
	@CrossOrigin
	@PostMapping(consumes = "application/json")
	//@PreAuthorize("hasRole('SYSADMIN')")
	public ResponseEntity<UUID> create(@RequestBody IngredientDTO entityDTO) {
		
		
		
		
		return new ResponseEntity<>(ingredientService.create(entityDTO), HttpStatus.CREATED);
	}

}
