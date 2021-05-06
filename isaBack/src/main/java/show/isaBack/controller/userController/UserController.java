package show.isaBack.controller.userController;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import show.isaBack.DTO.userDTO.PatientDTO;
import show.isaBack.service.userService.UserService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;






@RestController
@RequestMapping(value = "/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	
	@GetMapping("/patient")
	
	public ResponseEntity<UnspecifiedDTO<PatientDTO>> getLogedPatient() {
		
		System.out.println("Usao u vracanje pacijenta start ");
		try {
			UnspecifiedDTO<PatientDTO> patient = userService.getLoggedPatient();
			System.out.println("treba da vrati pacijenta ");
			return new ResponseEntity<>(patient,HttpStatus.OK); 
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} 
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	
	

}
