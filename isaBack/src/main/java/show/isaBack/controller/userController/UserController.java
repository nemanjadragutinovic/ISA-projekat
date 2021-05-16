package show.isaBack.controller.userController;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import show.isaBack.DTO.drugDTO.AllergenDTO;
import show.isaBack.DTO.userDTO.ChangePasswordDTO;
import show.isaBack.DTO.userDTO.PatientDTO;
import show.isaBack.DTO.userDTO.PatientsAllergenDTO;
import show.isaBack.DTO.userDTO.UserChangeInfoDTO;
import show.isaBack.model.drugs.Allergen;
import show.isaBack.security.TokenUtils;
import show.isaBack.service.userService.UserService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;






@RestController
@RequestMapping(value = "/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	 @Autowired
	 private TokenUtils tokenUtils;
	
	
	@GetMapping("/patient")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<UnspecifiedDTO<PatientDTO>> getLogedPatient(HttpServletRequest request) {
		
		/*Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(currentUser.getName() + "trenutni user je");
		String token = tokenUtils.getToken(request);
		System.out.println(token);		
		
		if (token == null || token.equals("") ) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
		
		String username  = tokenUtils.getUsernameFromToken(token);*/
		
		String authortity = tokenUtils.getAuthHeaderFromHeader(request);
		System.out.println( "authority je " + authortity );
		
		try {
			UnspecifiedDTO<PatientDTO> patient = userService.getLoggedPatient();
			
			for (UnspecifiedDTO<AllergenDTO> a : patient.EntityDTO.getAllergens()) {
				System.out.println(a.EntityDTO.getName());
				
			}
			
			return new ResponseEntity<>(patient,HttpStatus.OK); 
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} 
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		
	}
	
	
	@PutMapping("/patient") 
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<?> updatePatientInformation(@RequestBody UserChangeInfoDTO userInfoChangeDTO ) {
	  
		try {
			userService.updatePatient(userInfoChangeDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	
	@PostMapping("/changePassword")
	@PreAuthorize("hasRole('ROLE_PATIENT') ")
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
		
		try {
			userService.changePassword(changePasswordDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch (BadCredentialsException e){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/addPatientsAllergen") 
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<?> addAllergenForPatient(@RequestBody PatientsAllergenDTO patientsAllergenDTO) {
	  
		try {
			userService.addAllergenForPatient(patientsAllergenDTO);
				
			return new ResponseEntity<>(HttpStatus.OK); 
							
		}catch (IllegalArgumentException e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	
	@PutMapping("/removePatientsAllergen") 
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<?> removeAllergenForPatient(@RequestBody PatientsAllergenDTO patientsAllergenDTO) {
		
		try {
			userService.removeAllergenForPatient(patientsAllergenDTO);
				
			return new ResponseEntity<>(HttpStatus.OK); 
							
		}catch (IllegalArgumentException e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	
	
	@GetMapping("getAllPatientsAllergens") 
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<UnspecifiedDTO<AllergenDTO>>> getAllPatientsAllergens() {
	  
		try {
			List<UnspecifiedDTO<AllergenDTO>> allergens = userService.getAllPatientsAllergens();
			return new ResponseEntity<>(allergens,HttpStatus.OK); 
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	
	
	

}
