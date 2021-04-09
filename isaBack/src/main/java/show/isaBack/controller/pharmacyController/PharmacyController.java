package show.isaBack.controller.pharmacyController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import show.isaBack.DTO.drugDTO.DrugDTO;
import show.isaBack.DTO.pharmacyDTO.PharmacyDTO;
import show.isaBack.DTO.pharmacyDTO.PharmacySearchDTO;
import show.isaBack.serviceInterfaces.IPharmacyService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

@RestController
@RequestMapping(value = "/pharmacy")
public class PharmacyController {
	
	
	
	@Autowired
	private IPharmacyService pharmacyService;
	
	
	@CrossOrigin
	@GetMapping("/allPharmacies") 
	public ResponseEntity<List<UnspecifiedDTO<PharmacyDTO>>> getAllPharmacies() {
		
		try {
		return new ResponseEntity<>(pharmacyService.getAllPharmacies() ,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@CrossOrigin
	@PostMapping("/searchPharmacies" ) 
	public ResponseEntity<List<UnspecifiedDTO<PharmacyDTO>>> getSearchedPharmacies (@RequestBody PharmacySearchDTO userRequest) {
					
		try {
			
			return new ResponseEntity<>(pharmacyService.getSearchedPharmacies(userRequest) ,HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
