package show.isaBack.service.userService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import show.isaBack.DTO.drugDTO.AllergenDTO;
import show.isaBack.DTO.pharmacyDTO.PharmacyWithGradeAndPriceDTO;
import show.isaBack.DTO.userDTO.AuthorityDTO;
import show.isaBack.DTO.userDTO.ChangePasswordDTO;
import show.isaBack.DTO.userDTO.PatientDTO;
import show.isaBack.DTO.userDTO.PatientsAllergenDTO;
import show.isaBack.DTO.userDTO.PharmacistForAppointmentPharmacyGadeDTO;
import show.isaBack.DTO.userDTO.UserChangeInfoDTO;
import show.isaBack.DTO.userDTO.UserDTO;
import show.isaBack.DTO.userDTO.UserRegistrationDTO;
import show.isaBack.emailService.EmailService;
import show.isaBack.model.Authority;
import show.isaBack.model.Dermatologist;
import show.isaBack.model.Patient;
import show.isaBack.model.Pharmacy;
import show.isaBack.model.PharmacyAdmin;
import show.isaBack.model.Supplier;
import show.isaBack.model.SystemAdmin;
import show.isaBack.model.User;
import show.isaBack.model.drugs.Allergen;
import show.isaBack.repository.drugsRepository.AllergenRepository;
import show.isaBack.repository.pharmacyRepository.PharmacyRepository;
import show.isaBack.repository.userRepository.DermatologistRepository;
import show.isaBack.repository.userRepository.PatientRepository;
import show.isaBack.repository.userRepository.UserRepository;
import show.isaBack.serviceInterfaces.IAppointmentService;
import show.isaBack.serviceInterfaces.IEmployeeGradeService;
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
	private PharmacyRepository pharmacyRepository;
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AllergenRepository allergenRepository;
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private IAppointmentService appointmentService;
	
	@Autowired
	private IEmployeeGradeService employeeGradeService;
	
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
			e.printStackTrace();
		} catch (MessagingException e) {			
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

		return new UnspecifiedDTO<PatientDTO>(patientId , new PatientDTO(patient.getEmail(), patient.getName(), patient.getSurname(), patient.getAddress(),
				patient.getPhoneNumber(), patient.isActive(), patient.getUserAuthorities(),MapAllergenToAllergenDTO(patient.getAllergens())));
	}
	
	
	public List<UnspecifiedDTO<AllergenDTO>> MapAllergenToAllergenDTO(List<Allergen> allergens){
		
		List<UnspecifiedDTO<AllergenDTO>> allergeListDTO = new ArrayList<UnspecifiedDTO<AllergenDTO>>();
				
		for (Allergen allergen : allergens) {
			if(allergen == null) throw new IllegalArgumentException();
			
			allergeListDTO.add(new UnspecifiedDTO<AllergenDTO>(allergen.getId(), new AllergenDTO(allergen.getName())));
						
		}
		
		return allergeListDTO;
	}
	
	
	
	@Override
	public List<UnspecifiedDTO<AllergenDTO>> getAllPatientsAllergens() {
		
		UnspecifiedDTO<PatientDTO> patient = getLoggedPatient();
				
		return patient.EntityDTO.getAllergens();
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
		dermatologist.setFirstLogin(true);
		UnspecifiedDTO<AuthorityDTO> authority = authorityService.findByName("ROLE_DERMATHOLOGIST");
		List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(new Authority(authority.Id,authority.EntityDTO.getName()));
		dermatologist.setUserAuthorities(authorities);
		
		userRepository.save(dermatologist);
		
		return dermatologist.getId();
	}
	
	private Dermatologist CreateDermathologistFromDTO(UserRegistrationDTO patientDTO) {
		List<Pharmacy> pharmacies= new ArrayList<Pharmacy>();
		return new Dermatologist(patientDTO.getEmail(), passwordEncoder.encode(patientDTO.getPassword()), patientDTO.getName(), patientDTO.getSurname(), patientDTO.getAddress(), patientDTO.getPhoneNumber(),pharmacies);
	}
	

	@Override
	public UUID createAdmin(UserRegistrationDTO entityDTO) {
		SystemAdmin systemAdmin = CreateAdminFromDTO(entityDTO);
		systemAdmin.setPassword(passwordEncoder.encode(systemAdmin.getId().toString()));
		systemAdmin.setFirstLogin(true);
		System.out.println("prvi login = " + systemAdmin.isFirstLogin());
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
		pharmacyAdmin.setFirstLogin(true);
		
		UnspecifiedDTO<AuthorityDTO> authority = authorityService.findByName("ROLE_PHARMACYADMIN");
		List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(new Authority(authority.Id,authority.EntityDTO.getName()));
		pharmacyAdmin.setUserAuthorities(authorities);
		
		userRepository.save(pharmacyAdmin);
		
		return pharmacyAdmin.getId();
	}

	
	@Override
	public void updatePatient(UserChangeInfoDTO patientInfoChangeDTO) {
		
		UUID logedId= getLoggedUserId();
		Patient patient = patientRepository.getOne(logedId);		
		
		patient.setName(patientInfoChangeDTO.getName());
		patient.setSurname(patientInfoChangeDTO.getSurname());
		patient.setAddress(patientInfoChangeDTO.getAddress());
		patient.setPhoneNumber(patientInfoChangeDTO.getPhoneNumber());
			
		patientRepository.save(patient);
	}
	
	
	@Override
	public void changePassword(ChangePasswordDTO changePasswordDTO) {
		
		UUID id = getLoggedUserId();
		User user = userRepository.getOne(id);
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), changePasswordDTO.getOldPassword()));
		
		if(changePasswordDTO.getNewPassword().isEmpty())
			throw new IllegalArgumentException("Invalid new password");
		
		user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
		userRepository.save(user);
		
	}
	
	public void changeFirstPassword(String oldPassword, String newPassword) {
		
		User user = userRepository.findById(UUID.fromString(oldPassword)).get();
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), oldPassword));
		
		if(newPassword.isEmpty())
			throw new IllegalArgumentException("Invalid new password");
		
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setFirstLogin(false);
		userRepository.save(user);
		
	}
	
	

	private PharmacyAdmin CreatePharmacyAdminFromDTO(UserRegistrationDTO staffDTO,Pharmacy pharmacy) {
		return new PharmacyAdmin(staffDTO.getEmail(), passwordEncoder.encode(staffDTO.getPassword()), staffDTO.getName(), staffDTO.getSurname(), staffDTO.getAddress(), staffDTO.getPhoneNumber(),pharmacy);
	}
	
	
	@Override
	public void addAllergenForPatient(PatientsAllergenDTO patientsAllergenDTO) {
		
			Patient patient = patientRepository.getOne(patientsAllergenDTO.getPatientId());
			
			 		
			if(patientsAllergenDTO.getAllergenName().isEmpty())
				throw new IllegalArgumentException("Invalid allergen name");
			
			
			Allergen newAllergen = new Allergen(patientsAllergenDTO.getAllergenName());	
			allergenRepository.save(newAllergen);
			
			patient.addAllergen(newAllergen);		
			patientRepository.save(patient);
			
			
		
		
	}
	
	
	
	@Override
	public void removeAllergenForPatient(PatientsAllergenDTO patientsAllergenDTO) {

			Patient patient = patientRepository.getOne(patientsAllergenDTO.getPatientId());
				 		
			if(patientsAllergenDTO.getAllergenName().isEmpty())
				throw new IllegalArgumentException("Invalid allergen name");

			patient.removeAllergen(patientsAllergenDTO.getAllergenName());

			patientRepository.save(patient);

		
		
	}
	
	
	@Override
	public UUID createSupplier(UserRegistrationDTO entityDTO) {
		Supplier supp = CreateSupplierFromDTO(entityDTO);
		supp.setPassword(passwordEncoder.encode(supp.getId().toString()));
		supp.setFirstLogin(true);
		
		UnspecifiedDTO<AuthorityDTO> authority = authorityService.findByName("ROLE_SUPPLIER");
		List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(new Authority(authority.Id,authority.EntityDTO.getName()));
		supp.setUserAuthorities(authorities);
		
		userRepository.save(supp);
		
		return supp.getId();
	}
	
	private Supplier CreateSupplierFromDTO(UserRegistrationDTO patientDTO) {
		return new Supplier(patientDTO.getEmail(), passwordEncoder.encode(patientDTO.getPassword()), patientDTO.getName(), patientDTO.getSurname(), patientDTO.getAddress(), patientDTO.getPhoneNumber());
	}
	
	
	
	@Override
	public List<UnspecifiedDTO<PharmacistForAppointmentPharmacyGadeDTO>> fidnAllFreePharmacistsForSelectedPharmacyInDataRange(Date startDate, UUID pharmacyId){
		
		long startTime = startDate.getTime();
		Date endDate= new Date(startTime + 7200000);
		
		System.out.println( "startno vreme " + startDate + "   " +  " Krajnje vremee " + endDate);
		
		List<UnspecifiedDTO<PharmacistForAppointmentPharmacyGadeDTO>> pharmacistDTO= new ArrayList<UnspecifiedDTO<PharmacistForAppointmentPharmacyGadeDTO>>();  
		List<User>  pharmaciest= new ArrayList<User>();
		pharmaciest= appointmentService.fidnAllFreePharmacistsForSelectedPharmacyInDataRange(startDate,endDate,pharmacyId);
		
		for (User user : pharmaciest) {
			pharmacistDTO.add(convertPharmacistToPharmacistWithGradeDTO(user));
		}
		
	
		return pharmacistDTO;
		
		
	}
	
	
	
	public UnspecifiedDTO<PharmacistForAppointmentPharmacyGadeDTO> convertPharmacistToPharmacistWithGradeDTO(User pharmacist){
		
		double avgGrade = employeeGradeService.getAvgGradeForEmployee(pharmacist.getId());
		
		
		return new UnspecifiedDTO<PharmacistForAppointmentPharmacyGadeDTO>(pharmacist.getId() ,
				new PharmacistForAppointmentPharmacyGadeDTO(pharmacist.getName(),pharmacist.getSurname(),avgGrade));
		
		
	}
	
	@Override
	public double getAvgGradeForEmployee(UUID employeeID){
		
		return employeeGradeService.getAvgGradeForEmployee(employeeID);
		
		}
	
	
	
	@Override
	public List<UnspecifiedDTO<PharmacistForAppointmentPharmacyGadeDTO>> fidnAllFreePharmacistsForSelectedPharmacyInDataRangeSortByGradeAscending(Date startDate, UUID pharmacyId){
		
		List<UnspecifiedDTO<PharmacistForAppointmentPharmacyGadeDTO>> pharmacistDTOSorterByGradeAscending=fidnAllFreePharmacistsForSelectedPharmacyInDataRange(startDate,pharmacyId);
		Collections.sort(pharmacistDTOSorterByGradeAscending, (pharmacist1, pharmacist2) -> Double.compare(pharmacist1.EntityDTO.getGrade(), pharmacist2.EntityDTO.getGrade()));
		
		
	
		return pharmacistDTOSorterByGradeAscending;
		
		
	}
	
	@Override
	public List<UnspecifiedDTO<PharmacistForAppointmentPharmacyGadeDTO>> fidnAllFreePharmacistsForSelectedPharmacyInDataRangeSortByGradeDescending(Date startDate, UUID pharmacyId){
		
		List<UnspecifiedDTO<PharmacistForAppointmentPharmacyGadeDTO>> pharmacistDTOSorterByGradeDescending=fidnAllFreePharmacistsForSelectedPharmacyInDataRange(startDate,pharmacyId);
		Collections.sort(pharmacistDTOSorterByGradeDescending, (pharmacist1, pharmacist2) -> Double.compare(pharmacist1.EntityDTO.getGrade(), pharmacist2.EntityDTO.getGrade()));
		Collections.reverse(pharmacistDTOSorterByGradeDescending);
		
	
		return pharmacistDTOSorterByGradeDescending;
		
		
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
