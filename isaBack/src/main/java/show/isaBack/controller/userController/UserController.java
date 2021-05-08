package show.isaBack.controller.userController;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import show.isaBack.DTO.userDTO.PatientDTO;
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
			return new ResponseEntity<>(patient,HttpStatus.OK); 
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} 
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		
	}
	
	
	

}
