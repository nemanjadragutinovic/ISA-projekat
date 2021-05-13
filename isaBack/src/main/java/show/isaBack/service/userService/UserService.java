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
import show.isaBack.DTO.userDTO.UserDTO;
import show.isaBack.DTO.userDTO.UserRegistrationDTO;
import show.isaBack.emailService.EmailService;
import show.isaBack.model.Authority;
import show.isaBack.model.Dermatologist;
import show.isaBack.model.Patient;
import show.isaBack.model.Pharmacy;
import show.isaBack.model.PharmacyAdmin;
import show.isaBack.model.SystemAdmin;
import show.isaBack.model.User;
import show.isaBack.repository.pharmacyRepository.PharmacyRepository;
import show.isaBack.repository.userRepository.DermatologistRepository;
import show.isaBack.repository.userRepository.PatientRepository;
import show.isaBack.repository.userRepository.UserRepository;
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
	private DermatologistRepository dermatologistRepository;
	@Autowired
	private PharmacyRepository pharmacyRepository;
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	
	public UUID createPatient(UserRegistrationDTO patientRegistrationDTO) {
		
		
		
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
	
	
	private Patient PatientCreation(UserRegistrationDTO patientDTO) {
		return new Patient(patientDTO.getEmail(), passwordEncoder.encode(patientDTO.getPassword()), patientDTO.getName(), patientDTO.getSurname(), patientDTO.getAddress(), patientDTO.getPhoneNumber());
	}
	
	
	
	@Override
	public UnspecifiedDTO<PatientDTO> getLoggedPatient() {	
		
		
		
		UUID patientId = getLoggedUserId();
		Patient patient= patientRepository.getOne(patientId);
		
		if(patient==null) {
			System.out.println("pacijent je null");
		}
		System.out.println(patient.getEmail().toString());
		
		return new UnspecifiedDTO<PatientDTO>(patientId , new PatientDTO(patient.getEmail(), patient.getName(), patient.getSurname(), patient.getAddress(),
				patient.getPhoneNumber(), patient.isActive(), patient.getUserAuthorities()));
	}
	
	
	@Override
	public UUID getLoggedUserId() {
				
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(currentUser.getName() + " Pronadjem pacijent");
		String email = currentUser.getName();
		User user = userRepository.findByEmail(email);
		
		return user.getId();
	}
	
	public boolean existByEmail(String email) {
		if(userRepository.findByEmail(email)==null)
			return false;
		return true;
	}
	
	@Override
	public UUID createDermatologist(UserRegistrationDTO entityDTO) {
		Dermatologist dermatologist = CreateDermathologistFromDTO(entityDTO);
		dermatologist.setPassword(passwordEncoder.encode(dermatologist.getId().toString()));
		UnspecifiedDTO<AuthorityDTO> authority = authorityService.findByName("ROLE_DERMATHOLOGIST");
		List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(new Authority(authority.Id,authority.EntityDTO.getName()));
		dermatologist.setUserAuthorities(authorities);
		
		userRepository.save(dermatologist);
		
		return dermatologist.getId();
	}
	
	private Dermatologist CreateDermathologistFromDTO(UserRegistrationDTO patientDTO) {
		return new Dermatologist(patientDTO.getEmail(), passwordEncoder.encode(patientDTO.getPassword()), patientDTO.getName(), patientDTO.getSurname(), patientDTO.getAddress(), patientDTO.getPhoneNumber());
	}
	
	@Override
	public UUID createAdmin(UserRegistrationDTO entityDTO) {
		SystemAdmin systemAdmin = CreateAdminFromDTO(entityDTO);
		systemAdmin.setPassword(passwordEncoder.encode(systemAdmin.getId().toString()));
		UnspecifiedDTO<AuthorityDTO> authority = authorityService.findByName("ROLE_SYSADMIN");
		List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(new Authority(authority.Id,authority.EntityDTO.getName()));
		systemAdmin.setUserAuthorities(authorities);
		
		userRepository.save(systemAdmin);
		
		return systemAdmin.getId();
	}
	
	private SystemAdmin CreateAdminFromDTO(UserRegistrationDTO patientDTO) {
		return new SystemAdmin(patientDTO.getEmail(), passwordEncoder.encode(patientDTO.getPassword()), patientDTO.getName(), patientDTO.getSurname(), patientDTO.getAddress(), patientDTO.getPhoneNumber());
	}
	
	@Override
	public UUID createPharmacyAdmin(UserRegistrationDTO entityDTO, UUID pharmacyId) {
		Pharmacy pharmacy = pharmacyRepository.getOne(pharmacyId);
		PharmacyAdmin pharmacyAdmin = CreatePharmacyAdminFromDTO(entityDTO, pharmacy);
		pharmacyAdmin.setPassword(passwordEncoder.encode(pharmacyAdmin.getId().toString()));
		UnspecifiedDTO<AuthorityDTO> authority = authorityService.findByName("ROLE_PHARMACYADMIN");
		List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(new Authority(authority.Id,authority.EntityDTO.getName()));
		pharmacyAdmin.setUserAuthorities(authorities);
		
		userRepository.save(pharmacyAdmin);
		
		return pharmacyAdmin.getId();
	}

	private PharmacyAdmin CreatePharmacyAdminFromDTO(UserRegistrationDTO staffDTO,Pharmacy pharmacy) {
		return new PharmacyAdmin(staffDTO.getEmail(), passwordEncoder.encode(staffDTO.getPassword()), staffDTO.getName(), staffDTO.getSurname(), staffDTO.getAddress(), staffDTO.getPhoneNumber(),pharmacy);
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
