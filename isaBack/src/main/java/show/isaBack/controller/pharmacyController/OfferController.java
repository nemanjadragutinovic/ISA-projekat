package show.isaBack.controller.pharmacyController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import show.isaBack.DTO.drugDTO.OfferDTO;
import show.isaBack.serviceInterfaces.IOfferService;

@RestController
@RequestMapping(value = "offer")
public class OfferController {
	
	@Autowired
	private IOfferService offerService;
	
	
	
	
	@CrossOrigin
	@PutMapping("/drugsCheck")
	@PreAuthorize("hasRole('SUPPLIER')") 
	public ResponseEntity<?> checkIfCanUpdate(@RequestBody OfferDTO offerDTO) {
		return new ResponseEntity<>(offerService.checkIfHasDrugs(offerDTO.getId()),HttpStatus.OK);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('SUPPLIER')")
	public ResponseEntity<?> create(@RequestBody OfferDTO offerDTO) {
		System.out.println("usao u create");
		return new ResponseEntity<>(offerService.create(offerDTO),HttpStatus.CREATED);
	}

}
