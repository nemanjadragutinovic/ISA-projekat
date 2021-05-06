package show.isaBack.controller.userController;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.internal.build.AllowPrintStacktrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import show.isaBack.DTO.userDTO.UserTokenDTO;
import show.isaBack.model.User;
import show.isaBack.security.auth.AuthRequest;
import show.isaBack.secutiry.TokenUtils;
import show.isaBack.service.userService.UserService;

@RestController
@RequestMapping(value = "/log")
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/login")
	@CrossOrigin
	public ResponseEntity<UserTokenDTO> createAuthenticationToken(@RequestBody AuthRequest authRequest,
			HttpServletResponse response) {

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
		} catch (Exception e) {
			System.out.println("neki eksepsn");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		

		System.out.println("prosao do kraja");

		return new ResponseEntity<UserTokenDTO>(new UserTokenDTO(jwt, expiresIn, roles), HttpStatus.OK);
	}

}
