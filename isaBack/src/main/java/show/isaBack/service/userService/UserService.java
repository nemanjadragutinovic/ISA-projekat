package show.isaBack.service.userService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import show.isaBack.DTO.userDTO.AuthorityDTO;
import show.isaBack.DTO.userDTO.PatientDTO;
import show.isaBack.DTO.userDTO.PatientRegistrationDTO;
import show.isaBack.DTO.userDTO.UserDTO;
import show.isaBack.emailService.EmailService;
import show.isaBack.model.Authority;
import show.isaBack.model.Patient;
import show.isaBack.model.User;
import show.isaBack.repository.userRepository.PatientRepository;
import show.isaBack.repository.userRepository.UserRepository;
import show.isaBack.security.auth.AuthRequest;
import show.isaBack.serviceInterfaces.IUserInterface;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;


@Service
public class UserService implements IUserInterface{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	
	public UUID createPatient(PatientRegistrationDTO patientRegistrationDTO) {
		
		
		
		Patient patient = PatientCreation(patientRegistrationDTO);
		System.out.println("pacijent: " + patient.getName());
		UnspecifiedDTO<AuthorityDTO> authority = authorityService.findByName("ROLE_PATIENT");
		List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(new Authority(authority.Id,authority.EntityDTO.getName()));
		patient.setUserAuthorities(authorities);
		
		patientRepository.save(patient);
		
		try {
			emailService.sendSignUpNotificaitionAsync(patient);
		} catch (MailException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return patient.getId();
	}
	
	@Override
	public boolean activatingAccountForPatient(UUID id) {
		try {
			Patient patient = patientRepository.getOne(id);
			patient.setActive(true);
			patientRepository.save(patient);
			return true;
		}
		catch (EntityNotFoundException e) { return false; } 
		catch (IllegalArgumentException e) { return false; }
	}
	
	
	private Patient PatientCreation(PatientRegistrationDTO patientDTO) {
		return new Patient(patientDTO.getEmail(), passwordEncoder.encode(patientDTO.getPassword()), patientDTO.getName(), patientDTO.getSurname(), patientDTO.getAddress(), patientDTO.getPhoneNumber());
	}
	
	
	
	@Override
	public UnspecifiedDTO<PatientDTO> getLoggedPatient() {	
		
		System.out.println("Usao u servis za pacijenta");
		
		UUID patientId = getLoggedUserId();
		Patient patient= patientRepository.getOne(patientId);
		
		if(patient==null) {
			System.out.println("pacijent je null");
		}
		System.out.println(patient.getName().toString());
		
		return new UnspecifiedDTO<PatientDTO>(patientId , new PatientDTO(patient.getEmail(), patient.getName(), patient.getSurname(), patient.getAddress(),
				patient.getPhoneNumber(), patient.isActive(), patient.getUserAuthorities()));
	}
	
	
	@Override
	public UUID getLoggedUserId() {
		
		System.out.println("Usao logovanogDaNadjeID");
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		
		
		
		System.out.println("izvukao curentUSera");
		System.out.println(currentUser);
		
		String email = "";
						
		if(currentUser!= null) {
			email = currentUser.getName();
			System.out.println("curewnt user nije jednak null");
		}else {
			System.out.println("current user je jednak null");
		}
		
		
		
		User user = userRepository.findByEmail(email);
		
		return user.getId();
	}
	
	

	@Override
	public List<UnspecifiedDTO<AuthorityDTO>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnspecifiedDTO<UserDTO> findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID create(UserDTO entityDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(UserDTO entityDTO, UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	

}
