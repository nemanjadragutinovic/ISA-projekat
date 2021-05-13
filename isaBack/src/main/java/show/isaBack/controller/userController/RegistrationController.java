package show.isaBack.controller.userController;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import show.isaBack.DTO.userDTO.UserDTO;
import show.isaBack.DTO.userDTO.UserRegistrationDTO;
import show.isaBack.service.userService.UserService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

@RestController
@RequestMapping(value = "/reg")
public class RegistrationController {
	
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/patsignup")
	public ResponseEntity<UUID> addUser(@RequestBody UserRegistrationDTO patientRegistrationDTO) {
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
	
	@PostMapping("/signup-dermathologist")
	//@PreAuthorize("hasRole('SYSADMIN')")
	public ResponseEntity<UUID> addDermathologist(@RequestBody UserRegistrationDTO userRequest, UriComponentsBuilder ucBuilder) {

		
		if (this.userService.existByEmail(userRequest.getEmail())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		UUID userId = userService.createDermatologist(userRequest);
		
		return new ResponseEntity<>(userId, HttpStatus.CREATED);
	}
	
	@PostMapping("/signup-sysadmin")
	public ResponseEntity<UUID> addSysadmin(@RequestBody UserRegistrationDTO userRequest, UriComponentsBuilder ucBuilder) {

		if (this.userService.existByEmail(userRequest.getEmail())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		UUID userId = userService.createAdmin(userRequest);
		
		return new ResponseEntity<>(userId, HttpStatus.CREATED);
	}

}
