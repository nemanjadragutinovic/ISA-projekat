package show.isaBack.controller.userController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.internal.build.AllowPrintStacktrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import show.isaBack.DTO.userDTO.UserRegistrationDTO;
import show.isaBack.DTO.userDTO.UserTokenDTO;
import show.isaBack.model.Authority;
import show.isaBack.model.User;
import show.isaBack.repository.userRepository.UserRepository;
import show.isaBack.security.TokenUtils;
import show.isaBack.security.auth.JwtAuthenticationRequest;
import show.isaBack.service.userService.UserService;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/login")
	@CrossOrigin
	public ResponseEntity<UserTokenDTO> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authRequest,
			HttpServletResponse response) throws AuthenticationException, IOException {

		String jwt;
		int expiresIn;
		List<String> roles = new ArrayList<String>();
		
		System.out.println(passwordEncoder.encode(authRequest.getPassword()));

		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

			System.out.println("prosao autentifikaciju");
			SecurityContextHolder.getContext().setAuthentication(authentication);
			User user = (User) authentication.getPrincipal();
			
			Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
			
			System.out.println("sokovii");
			System.out.println(currentUser.getName());
			
			jwt = tokenUtils.generateToken(user.getUsername());
			expiresIn = tokenUtils.getExpiredIn();
			user.getUserAuthorities().forEach((a) -> roles.add(a.getName()));
			
			System.out.println(authRequest.getUsername() + authRequest.getPassword() + "AUTENTIFIKACIJA");
			
			

		} catch (BadCredentialsException e) {
			System.out.println("bad credentials");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (DisabledException e) {
			System.out.println("nije aktiviran ");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		
		} catch (Exception e) {
			System.out.println("neki eksepsn");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		User user = userRepository.findByEmail(authRequest.getUsername());
		if(user.isFirstLogin()) {
			System.out.println("gas gas gas gas");
			return new ResponseEntity<>(HttpStatus.FOUND);
		}
		
		
		
		
	//	if (IsFirstPassword(authRequest))
		//	return new ResponseEntity<>(HttpStatus.FOUND);

		System.out.println("prosao do kraja");

		return new ResponseEntity<UserTokenDTO>(new UserTokenDTO(jwt, expiresIn, roles), HttpStatus.OK);
	}
	
	
	public boolean IsFirstPassword(JwtAuthenticationRequest authenticationRequest) {
		User user = userRepository.findByEmail(authenticationRequest.getUsername());
		if (passwordEncoder.matches(user.getId().toString(), user.getPassword()))
			return true;
		else
			return false;
	}
	
	
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

}
