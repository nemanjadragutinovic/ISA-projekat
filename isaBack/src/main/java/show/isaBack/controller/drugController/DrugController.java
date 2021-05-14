package show.isaBack.controller.drugController;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import show.isaBack.DTO.drugDTO.DrugDTO;
import show.isaBack.DTO.drugDTO.DrugInstanceDTO;
import show.isaBack.DTO.drugDTO.DrugManufacturerDTO;
import show.isaBack.DTO.drugDTO.IngredientDTO;
import show.isaBack.DTO.drugDTO.ManufacturerDTO;
import show.isaBack.DTO.drugDTO.ReplaceDrugIdDTO;
import show.isaBack.serviceInterfaces.IDrugService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;



@RestController
@RequestMapping(value = "/drug", produces = MediaType.APPLICATION_JSON_VALUE)

public class DrugController {

	@Autowired
	private IDrugService drugService;
	
	
	@CrossOrigin
	@GetMapping("/allDrugs") 
	public ResponseEntity<List<UnspecifiedDTO<DrugDTO>>> getAllDrugs() {
		
		try {
		return new ResponseEntity<>(drugService.getAllDrugs() ,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@CrossOrigin
	@PostMapping("/searchDrugs" ) 
	public ResponseEntity<List<UnspecifiedDTO<DrugDTO>>> getSearchedDrugs (@RequestBody DrugDTO userRequest) {
					
		try {
			
			return new ResponseEntity<>(drugService.getSearchedDrug(userRequest) ,HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping
	@CrossOrigin
	@PreAuthorize("hasRole('SYSADMIN')")
	public ResponseEntity<UUID> addDrugInstance(@RequestBody DrugInstanceDTO drugInstanceDTO) {
		
		UUID drugInstanceId = drugService.create(drugInstanceDTO);
		
		return new ResponseEntity<>(drugInstanceId ,HttpStatus.CREATED);
	}
	
	@PutMapping("/replacement") 
	@CrossOrigin
	@PreAuthorize("hasRole('SYSADMIN')")
	public ResponseEntity<UUID> addDrugReplacement(@RequestBody ReplaceDrugIdDTO replaceDrugIdDTO) {
		
		
		UUID drugInstanceId = drugService.addDrugReplacement(replaceDrugIdDTO.getId(), replaceDrugIdDTO.getReplacement_id());
		
		return new ResponseEntity<>(drugInstanceId ,HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@GetMapping("/manufacturers")
	public ResponseEntity<List<UnspecifiedDTO<ManufacturerDTO>>> findAllManufacturers() {
		return new ResponseEntity<>(drugService.findDrugManufacturers(),HttpStatus.OK);
	}
	
	@PutMapping("/manufacturer") 
	@CrossOrigin
	@PreAuthorize("hasRole('SYSADMIN')")
	public ResponseEntity<UUID> addDrugManufacturer(@RequestBody DrugManufacturerDTO drugManufacturerDTO) {
		
		UUID drugInstanceId = drugService.addDrugManufacturer(drugManufacturerDTO.getDrug_id(), drugManufacturerDTO.getManufacturer_id());
		
		return new ResponseEntity<>(drugInstanceId ,HttpStatus.CREATED);
	}
	
	@PutMapping("/ingredient") 
	@CrossOrigin
	@PreAuthorize("hasRole('SYSADMIN')")
	public ResponseEntity<UUID> addDrugIngredient(@RequestBody IngredientDTO ingredientDTO) {
		
		UUID drugInstanceId = drugService.addDrugIngredients(ingredientDTO.getId(), ingredientDTO);
		
		return new ResponseEntity<>(drugInstanceId ,HttpStatus.CREATED);
	}
	
	
	
}
