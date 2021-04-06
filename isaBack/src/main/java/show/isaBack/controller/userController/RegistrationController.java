package show.isaBack.controller.userController;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import show.isaBack.DTO.userDTO.PatientRegistrationDTO;
import show.isaBack.service.userService.UserService;

@RestController
@RequestMapping(value = "/reg")
public class RegistrationController {
	
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/patsignup")
	public ResponseEntity<UUID> addUser(@RequestBody PatientRegistrationDTO patientRegistrationDTO) {
		try {
			System.out.println("usao u /patsignup" + patientRegistrationDTO.getName());
			System.out.println(patientRegistrationDTO.getEmail()+" AXAXAXAXA");
			UUID userId = userService.createPatient(patientRegistrationDTO);
			return new ResponseEntity<UUID>(userId, HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/activeAccountForPatient/{patientId}")
	public ResponseEntity<?> activatePatient(@PathVariable UUID patientId) {
		
		try {
			if (userService.activatingAccountForPatient(patientId))
				return new ResponseEntity<>(HttpStatus.OK);
			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}

}
