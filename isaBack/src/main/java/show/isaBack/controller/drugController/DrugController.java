package show.isaBack.controller.drugController;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import show.isaBack.DTO.drugDTO.DrugDTO;
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
		
		
		return new ResponseEntity<>(drugService.getAllDrugs() ,HttpStatus.CREATED);
	}
	
	
	@CrossOrigin
	@PostMapping("/searchDrugs" ) 
	public ResponseEntity<List<UnspecifiedDTO<DrugDTO>>> getSearchedDrugs (@RequestBody DrugDTO userRequest) {
		
			
		try {
			
			return new ResponseEntity<>(drugService.getSearchedDrug(userRequest) ,HttpStatus.CREATED);
		}
		catch (Exception e) {
			System.out.println("greskaaa");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
}
