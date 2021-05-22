package show.isaBack.controller.loyalityController;

import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import show.isaBack.DTO.userDTO.LoyaltyProgramDTO;
import show.isaBack.service.loyalityService.LoyalityProgramService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

@RestController
@RequestMapping(value = "loyaltyProgram")
public class LoyalityProgramController {
	
	@Autowired
	private LoyalityProgramService loyaltyProgramService;
	
	
	@CrossOrigin
	@GetMapping("/{loyaltyProgramId}")
	public ResponseEntity<UnspecifiedDTO<LoyaltyProgramDTO>> getLoyaltyProgramById(@PathVariable UUID loyaltyProgramId) {
		
		try {
				
			UnspecifiedDTO<LoyaltyProgramDTO> loyaltyProgram = loyaltyProgramService.findById(loyaltyProgramId);
			return new ResponseEntity<>(loyaltyProgram,HttpStatus.OK); 
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@CrossOrigin
	@PutMapping("/update")
	@PreAuthorize("hasRole('SYSADMIN')")
	public ResponseEntity<?> updateLoyaltyProgramInformation(@RequestBody LoyaltyProgramDTO loyaltyProgramDTO ) {
	  
		try {
			loyaltyProgramService.update(loyaltyProgramDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}

}
