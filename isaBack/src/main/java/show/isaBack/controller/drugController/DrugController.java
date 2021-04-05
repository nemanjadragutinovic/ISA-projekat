package show.isaBack.controller.drugController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import show.isaBack.DTO.drugDTO.DrugDTO;
import show.isaBack.serviceInterfaces.IDrugService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;



@RestController
@RequestMapping(value = "/drug")

public class DrugController {

	@Autowired
	private IDrugService drugService;
	
	
	@CrossOrigin
	@GetMapping("/allDrugs") 
	public ResponseEntity<List<UnspecifiedDTO<DrugDTO>>> getAllDrugs() {
		
		return new ResponseEntity<>(drugService.getAllDrugs() ,HttpStatus.CREATED);
	}
	
	
	
	
}
