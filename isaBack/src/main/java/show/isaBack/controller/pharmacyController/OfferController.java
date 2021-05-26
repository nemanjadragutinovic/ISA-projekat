package show.isaBack.controller.pharmacyController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import show.isaBack.DTO.drugDTO.OfferDTO;
import show.isaBack.serviceInterfaces.IOfferService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

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
	
	@CrossOrigin
	@GetMapping
	@PreAuthorize("hasRole('SUPPLIER')") 
	public ResponseEntity<List<UnspecifiedDTO<OfferDTO>>> findAll() {
		System.out.println("usao u find all");
		return new ResponseEntity<>(offerService.findAllOffers(),HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/accepted")
	@PreAuthorize("hasRole('SUPPLIER')") 
	public ResponseEntity<List<UnspecifiedDTO<OfferDTO>>> findAllAccepted() {
		System.out.println("usao u accepted");
		return new ResponseEntity<>(offerService.findAllAccepted(),HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/rejected")
	@PreAuthorize("hasRole('SUPPLIER')") 
	public ResponseEntity<List<UnspecifiedDTO<OfferDTO>>> findAllRejected() {
		return new ResponseEntity<>(offerService.findAllRejected(),HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/waiting")
	@PreAuthorize("hasRole('SUPPLIER')") 
	public ResponseEntity<List<UnspecifiedDTO<OfferDTO>>> findAllWaiting() {
		return new ResponseEntity<>(offerService.findAllWaiting(),HttpStatus.OK);
	}

}
