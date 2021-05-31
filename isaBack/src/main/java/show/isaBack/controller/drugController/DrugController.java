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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import show.isaBack.DTO.drugDTO.DrugDTO;
import show.isaBack.DTO.drugDTO.DrugFormatIdDTO;
import show.isaBack.DTO.drugDTO.DrugInstanceDTO;
import show.isaBack.DTO.drugDTO.DrugKindIdDTO;
import show.isaBack.DTO.drugDTO.DrugManufacturerDTO;
import show.isaBack.DTO.drugDTO.DrugsWithGradesDTO;
import show.isaBack.DTO.drugDTO.IngredientDTO;
import show.isaBack.DTO.drugDTO.ManufacturerDTO;
import show.isaBack.DTO.drugDTO.ReplaceDrugIdDTO;
import show.isaBack.serviceInterfaces.IDrugFormatService;
import show.isaBack.serviceInterfaces.IDrugKindIdService;
import show.isaBack.serviceInterfaces.IDrugService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;



@RestController
@RequestMapping(value = "/drug", produces = MediaType.APPLICATION_JSON_VALUE)

public class DrugController {

	@Autowired
	private IDrugService drugService;
	
	@Autowired
	private IDrugKindIdService drugKindIdService;
	@Autowired
	private IDrugFormatService drugFormatService;
	
	
	@CrossOrigin
	@GetMapping("/allDrugs") 
	public ResponseEntity<List<UnspecifiedDTO<DrugDTO>>> getAllDrugs() {
		
		try {
		return new ResponseEntity<>(drugService.getAllDrugs() ,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
/*	@CrossOrigin
	@PostMapping("/searchDrugs" ) 
	public ResponseEntity<List<UnspecifiedDTO<DrugDTO>>> getSearchedDrugs (@RequestBody DrugDTO userRequest) {
					
		try {
			
			return new ResponseEntity<>(drugService.getSearchedDrug(userRequest) ,HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} */
	
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
	
	@CrossOrigin
	@GetMapping("/drugkind")
	public ResponseEntity<List<UnspecifiedDTO<DrugKindIdDTO>>> findAllDrugKinds() {
		
		System.out.println("usao u drug kind");
		
		return new ResponseEntity<>(drugKindIdService.findAllDrugKinds() ,HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/drugformat")
	public ResponseEntity<List<UnspecifiedDTO<DrugFormatIdDTO>>> findAllDrugFormats() {
		
		return new ResponseEntity<>(drugFormatService.findAllDrugKinds() ,HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<UnspecifiedDTO<DrugInstanceDTO>>> findAll() {
		System.out.println("usao u get drug");
		return new ResponseEntity<>(drugService.findAllDrugKinds(),HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/searchDrugs") 
	public ResponseEntity<List<UnspecifiedDTO<DrugsWithGradesDTO>>> searchDrugs(@RequestParam String name, @RequestParam double gradeFrom, @RequestParam double gradeTo, @RequestParam String drugKind) {
		System.out.println("usao u pretragu");
		return new ResponseEntity<>(drugService.searchDrugs(name, gradeFrom, gradeTo, drugKind) ,HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@GetMapping("/getDrugsWithGrade") 
	public ResponseEntity<List<UnspecifiedDTO<DrugsWithGradesDTO>>> findDrugsWithGrades() {
		System.out.println("usao u get drugs");
		return new ResponseEntity<>(drugService.findDrugsWithGrades() ,HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@GetMapping("/canPatientUseQR") 
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<?> canPatientUseQR(@RequestParam String id) {
		System.out.println("can patient use qr");
		System.out.println(id + "xixixi");
		return new ResponseEntity<>(drugService.isQrCodeValid(id) ,HttpStatus.CREATED);
	}
	
	
	
	
	
}
