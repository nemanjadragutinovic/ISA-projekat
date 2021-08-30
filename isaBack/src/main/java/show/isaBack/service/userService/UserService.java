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
import org.springframework.data.jpa.repository.JpaRepository;
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
import show.isaBack.DTO.pharmacyDTO.PharmacyDTO;
import show.isaBack.DTO.pharmacyDTO.PharmacyWithGradeAndPriceDTO;
import show.isaBack.DTO.userDTO.AuthorityDTO;
import show.isaBack.DTO.userDTO.ChangePasswordDTO;
import show.isaBack.DTO.userDTO.LoyalityProgramForPatientDTO;
import show.isaBack.DTO.userDTO.NewDermatologistInPharmacyDTO;
import show.isaBack.DTO.userDTO.PatientDTO;
import show.isaBack.DTO.userDTO.PatientsAllergenDTO;

import show.isaBack.DTO.userDTO.PhAdminDTO;
import show.isaBack.DTO.userDTO.DermatologistWithGradeDTO;
import show.isaBack.DTO.userDTO.EmployeeGradeDTO;
import show.isaBack.DTO.userDTO.PharmacistForAppointmentPharmacyGadeDTO;
import show.isaBack.DTO.userDTO.UserChangeInfoDTO;
import show.isaBack.DTO.userDTO.UserDTO;
import show.isaBack.DTO.userDTO.UserRegistrationDTO;

import show.isaBack.Mappers.Pharmacy.UserMapper;

import show.isaBack.DTO.userDTO.WorkTimeDTO;
import show.isaBack.Mappers.Appointmets.AppointmentsMapper;

import show.isaBack.emailService.EmailService;
import show.isaBack.model.Authority;
import show.isaBack.model.Dermatologist;
import show.isaBack.model.Pharmacist;
import show.isaBack.model.Patient;
import show.isaBack.model.Pharmacist;
import show.isaBack.model.Pharmacy;
import show.isaBack.model.PharmacyAdmin;
import show.isaBack.model.Supplier;
import show.isaBack.model.SystemAdmin;
import show.isaBack.model.User;
import show.isaBack.model.UserCharacteristics.WorkTime;
import show.isaBack.model.drugs.Allergen;
import show.isaBack.repository.drugsRepository.AllergenRepository;
import show.isaBack.repository.pharmacyRepository.PharmacyRepository;
import show.isaBack.repository.userRepository.DermatologistRepository;
import show.isaBack.repository.userRepository.PharmacistRepository;
import show.isaBack.repository.userRepository.PatientRepository;
import show.isaBack.repository.userRepository.PharmacistRepository;
import show.isaBack.repository.userRepository.PharmacyAdminRepository;

import show.isaBack.repository.userRepository.SupplierRepository;

import show.isaBack.repository.userRepository.UserRepository;
import show.isaBack.repository.userRepository.WorkTimeRepository;
import show.isaBack.serviceInterfaces.IAppointmentService;
import show.isaBack.serviceInterfaces.IEmployeeGradeService;
import show.isaBack.serviceInterfaces.ILoyaltyService;
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
    private PharmacyAdminRepository phAdminRepository;	
	@Autowired
	private PharmacistRepository pharmacistRepository;
	@Autowired
	private AllergenRepository allergenRepository;
	
	@Autowired
	private DermatologistRepository dermatologistRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	private AppointmentsMapper appointmentsMapper= new AppointmentsMapper();
	
	@Autowired
	private IAppointmentService appointmentService;
	
	@Autowired
	private IEmployeeGradeService employeeGradeService;
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	@Autowired
	private DermatologistRepository dermathologistRepository;
	
	
	
	@Autowired
	private WorkTimeRepository workTimeRepository;
	
	@Autowired
	private ILoyaltyService loyaltyProgramService;
	
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
		
		LoyalityProgramForPatientDTO patientLoyalityProgramDTO=loyaltyProgramService.getLoyalityProgramForPatient(patient);
		
		return new UnspecifiedDTO<PatientDTO>(patientId , new PatientDTO(patient.getEmail(), patient.getName(), patient.getSurname(), patient.getAddress(),
				patient.getPhoneNumber(), patient.isActive(), patient.getUserAuthorities(),MapAllergenToAllergenDTO(patient.getAllergens()), patient.getPenalty(),
				patient.getPoints(), patientLoyalityProgramDTO));
	}
	
	@Override
	public UserDTO getLoggedSupplier() {	
		
		
		
		UUID suppID = getLoggedUserId();
		Supplier supp= supplierRepository.getOne(suppID);
		
		if(supp==null) {
			System.out.println("pacijent je null");
		}

		return new UserDTO(supp.getEmail(), supp.getName(), supp.getSurname(), supp.getAddress(), supp.getPhoneNumber(), false, null);
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
	
	public UserDTO getLoggedDermathologist() {	
		
		
		
		UUID suppID = getLoggedUserId();
		Dermatologist derm= dermathologistRepository.getOne(suppID);
		
		if(derm==null) {
			System.out.println("dermatolog je null");
		}

		return new UserDTO(derm.getEmail(), derm.getName(), derm.getSurname(), derm.getAddress(), derm.getPhoneNumber(), false, null);
	}
	
	public UserDTO getLoggedPharmacist() {	
		
		
		
		UUID suppID = getLoggedUserId();
		Pharmacist phar= pharmacistRepository.getOne(suppID);
		
		if(phar==null) {
			System.out.println("farmaceut je null");
		}

		return new UserDTO(phar.getEmail(), phar.getName(), phar.getSurname(), phar.getAddress(), phar.getPhoneNumber(), false, null);
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
	public UUID createPharmacist(UserRegistrationDTO entityDTO) {
		Pharmacist pharmacist = CreatePharmacistFromDTO(entityDTO);
		pharmacist.setPassword(passwordEncoder.encode(pharmacist.getId().toString()));
		pharmacist.setFirstLogin(true);
		UnspecifiedDTO<AuthorityDTO> authority = authorityService.findByName("ROLE_PHARMACIST");
		List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(new Authority(authority.Id,authority.EntityDTO.getName()));
		pharmacist.setUserAuthorities(authorities);
		
		userRepository.save(pharmacist);
		
		return pharmacist.getId();
	}
	
	private Pharmacist CreatePharmacistFromDTO(UserRegistrationDTO patientDTO) {
		Pharmacy pharmacy= new Pharmacy();
		return new Pharmacist(patientDTO.getEmail(), passwordEncoder.encode(patientDTO.getPassword()), patientDTO.getName(), patientDTO.getSurname(), patientDTO.getAddress(), patientDTO.getPhoneNumber(),pharmacy);
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

	public void updatePhAdmin(UserChangeInfoDTO phadminInfoChangeDTO) {
		
		UUID logedId= getLoggedUserId();
		PharmacyAdmin phadmin = phAdminRepository.getOne(logedId);		
		
		phadmin.setName(phadminInfoChangeDTO.getName());
		phadmin.setSurname(phadminInfoChangeDTO.getSurname());
		phadmin.setAddress(phadminInfoChangeDTO.getAddress());
		phadmin.setPhoneNumber(phadminInfoChangeDTO.getPhoneNumber());
			
		phAdminRepository.save(phadmin);
	}

	@Override
	public void updateSupplier(UserChangeInfoDTO supplierInfoChangeDTO) {
		
		UUID logedId= getLoggedUserId();
		Supplier supp = supplierRepository.getOne(logedId);		
		
		supp.setName(supplierInfoChangeDTO.getName());
		supp.setSurname(supplierInfoChangeDTO.getSurname());
		supp.setAddress(supplierInfoChangeDTO.getAddress());
		supp.setPhoneNumber(supplierInfoChangeDTO.getPhoneNumber());
			
		supplierRepository.save(supp);
	}
	
	@Override
	public void updateDermathologist(UserChangeInfoDTO dermathologistInfoChangeDTO) {
		
		UUID logedId= getLoggedUserId();
		Dermatologist derm = dermathologistRepository.getOne(logedId);		
		
		derm.setName(dermathologistInfoChangeDTO.getName());
		derm.setSurname(dermathologistInfoChangeDTO.getSurname());
		derm.setAddress(dermathologistInfoChangeDTO.getAddress());
		derm.setPhoneNumber(dermathologistInfoChangeDTO.getPhoneNumber());
			
		dermathologistRepository.save(derm);
	}

	@Override
	public void updatePharmacist(UserChangeInfoDTO pharmacistInfoChangeDTO) {
		
		UUID logedId= getLoggedUserId();
		Pharmacist phar = pharmacistRepository.getOne(logedId);		
		
		phar.setName(pharmacistInfoChangeDTO.getName());
		phar.setSurname(pharmacistInfoChangeDTO.getSurname());
		phar.setAddress(pharmacistInfoChangeDTO.getAddress());
		phar.setPhoneNumber(pharmacistInfoChangeDTO.getPhoneNumber());
			
		pharmacistRepository.save(phar);
	}
	
	@Override
	public void changePassword(ChangePasswordDTO changePasswordDTO) {
		
		UUID id = getLoggedUserId();
		User user = userRepository.getOne(id);
		System.out.println(user.getEmail());
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
	
	@Override

	public UUID addWorkTimeForEmployee(WorkTimeDTO workTimeDTO) {
		System.out.println("asdasdfasdfdsfdasdasdBidibou");
		if(isEmployeeWorksAtThatTime(workTimeDTO)) {
			return null;
		}
		System.out.println(workTimeDTO.getPharmacyId());
		System.out.println(workTimeDTO.getEmployee());
		User employee = userRepository.getOne(workTimeDTO.getEmployee());
		Pharmacy pharmacy = pharmacyRepository.getOne(workTimeDTO.getPharmacyId());
		System.out.println("BIDIBOU");
			
		WorkTime newWorkTime = new WorkTime(pharmacy,employee,workTimeDTO.getStartDate(),workTimeDTO.getEndDate(),workTimeDTO.getStartTime(),workTimeDTO.getEndTime());
		
		//if(!(newWorkTime.getEndDate().before(newWorkTime.getStartDate()) || newWorkTime.getStartTime()>=newWorkTime.getEndTime())) {
			workTimeRepository.save(newWorkTime);
			return newWorkTime.getId();
		//}
		
		//return null;
	}
	
	private boolean isEmployeeWorksAtThatTime(WorkTimeDTO workTimeDTO) {
			List<WorkTime> allWorkTimes= workTimeRepository.findAll();
		
		for(WorkTime workTime : allWorkTimes) {
			if((workTime.getEmployee().getId().equals(workTimeDTO.getEmployee()) && isDateOccupied(workTimeDTO,workTime)))
				return true;
		}
		
		return false;
		
		
	}
	
	private boolean isDateOccupied(WorkTimeDTO workTimeDTO,WorkTime workTime) {
		 if( workTimeDTO.getStartDate().before(workTime.getEndDate())  && workTime.getStartDate().before(workTimeDTO.getEndDate())
		 || (workTime.getEndDate().getDay() == workTimeDTO.getStartDate().getDay() && workTime.getEndDate().getMonth() == workTimeDTO.getStartDate().getMonth() )
		 || (workTime.getStartDate().getDay() == workTimeDTO.getEndDate().getDay() && workTime.getStartDate().getMonth() == workTimeDTO.getEndDate().getMonth() )
		 ){
		    	
		    	if(workTime.getStartTime()<=workTimeDTO.getEndTime() && workTimeDTO.getStartTime()<=workTime.getEndTime()) {
		    		return true;
		    	}
		    	
		    	if(workTime.getPharmacy().getId().equals(workTimeDTO.getPharmacyId
		    			()))
		    		return true;
		    }
			
		    return false;
	}
	@Override
	public List<UnspecifiedDTO<EmployeeGradeDTO>> findDermatologistsinPharmacy(UUID phId){
		
	
		List<UnspecifiedDTO<EmployeeGradeDTO>> dermatologistsForPharmacy= new ArrayList<UnspecifiedDTO<EmployeeGradeDTO>>();  
		List<Dermatologist>  allDermatologists=dermatologistRepository.findAll();
		
	 System.out.println("sassssssss: "+phId);
		
		for (Dermatologist dermatologist : allDermatologists) {
			if(isDermatologistInPharmacy(dermatologist,phId)) {
			double avgGrade = getAvgGradeForEmployee(dermatologist.getId());
			
			dermatologistsForPharmacy.add(appointmentsMapper.MapDermatologistToEmployeeDTO(dermatologist,avgGrade));
			}
		}
		
		return dermatologistsForPharmacy;
		
	}


	public boolean isDermatologistInPharmacy(Dermatologist d,UUID phId) {
		for(Pharmacy p : d.getPharmacies()) {
			if(p.getId().equals(phId)) {
				return true;
			}
		}
		return false;
		
	}
	
	@Override
	public List<UnspecifiedDTO<EmployeeGradeDTO>> findDermatologistsWhoDontWorkInPharmacy(UUID phId) {
		
		List<Dermatologist> allDermatologists = dermatologistRepository.findAll();
		List<UnspecifiedDTO<EmployeeGradeDTO>> retVal = new ArrayList<UnspecifiedDTO<EmployeeGradeDTO>>();
		
		for(Dermatologist dermatologist : allDermatologists) {
			if(!isDermatologistInPharmacy(dermatologist,phId)) {
               double avgGrade = getAvgGradeForEmployee(dermatologist.getId());
			
			   retVal.add(appointmentsMapper.MapDermatologistToEmployeeDTO(dermatologist,avgGrade));
		   }
		}	
		
		return retVal;
	}
	
	
	@Override
	public boolean addDermatologistInPharmacy(NewDermatologistInPharmacyDTO newDTO) {
		    
			Pharmacy pharmacy = pharmacyRepository.getOne(newDTO.getPharmacyId());
			Dermatologist dermatologist = dermatologistRepository.getOne(newDTO.getDermatologistId());
			
			WorkTimeDTO workTimeDTO = new WorkTimeDTO(newDTO.getPharmacyId(),newDTO.getDermatologistId(), newDTO.getStartDate(), newDTO.getEndDate(), newDTO.getStartTime(), newDTO.getEndTime());
			
			if(addWorkTimeForEmployee(workTimeDTO)==null)
				return false;
			
			dermatologist.addPharmacy(pharmacy);
			dermatologistRepository.save(dermatologist);
			
			return true;
	
	}
	
	@Override
	public List<UnspecifiedDTO<EmployeeGradeDTO>> findPharmacistsinPharmacy(UUID phId){
		
	
		List<UnspecifiedDTO<EmployeeGradeDTO>> pharmacistsForPharmacy= new ArrayList<UnspecifiedDTO<EmployeeGradeDTO>>();  
		
		List<Pharmacist>  allPharmacists=pharmacistRepository.findAll();
		
	
		
		for (Pharmacist pharmacist : allPharmacists) {
			if(pharmacist.getPharmacy().getId().equals(phId)) {
			double avgGrade = getAvgGradeForEmployee(pharmacist.getId());
			
			pharmacistsForPharmacy.add(appointmentsMapper.MapPharmacistsToEmployeeDTO(pharmacist,avgGrade));
			}
		}
		
		return pharmacistsForPharmacy;
		
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
	public boolean subscribeToPharmacy(UUID pharmacyId) {
		try {
			UUID loggedUser= this.getLoggedUserId();

			Patient patient = patientRepository.getOne(loggedUser);
			Pharmacy pharmacy = pharmacyRepository.getOne(pharmacyId);
			patient.addSubscribeToPharmacy(pharmacy);
			
			patientRepository.save(patient);
			return true;
		} 
		catch (EntityNotFoundException e) { return false; } 
		catch (IllegalArgumentException e) { return false; }		
	}
	
	@Override
	public boolean unsubscribeFromPharmacy(UUID pharmacyId) {
		// TODO Auto-generated method stub
		try {
			UUID loggedUser= this.getLoggedUserId();

			Patient patient = patientRepository.getOne(loggedUser);
			patient.removeSubscribeFromPharmacy(pharmacyId);
			
			patientRepository.save(patient);
			return true;
		} 
		catch (EntityNotFoundException e) { return false; } 
		catch (IllegalArgumentException e) { return false; }	
	}
	
	@Override
	public boolean isPatientSubscribedToPharmacy(UUID pharmacyId) {
		
		UUID loggedUser= this.getLoggedUserId();
		Patient patient = patientRepository.getOne(loggedUser);
		
		return patient.isPatientSubscribedToPharmacy(pharmacyId);
	}
	
	
	@Override
	public boolean removeDermatologistFromPharmacy(UUID dermatologistId,UUID phId) {
			if(!appointmentService.isFutureAppointmentExists(dermatologistId,phId)) {
				Dermatologist dermatologist = dermatologistRepository.getOne(dermatologistId);
				dermatologist.removePharmacy(phId);
				dermatologistRepository.save(dermatologist);
				
				
			    List<WorkTime> workTimesForDermatologist = workTimeRepository. getDermatologistsWorkTimesForPharmacy(dermatologistId,phId);
				workTimeRepository.deleteAll(workTimesForDermatologist);
				
				return true;
			}else {
				return false;
			}
	
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

	@Override
	public UnspecifiedDTO<PhAdminDTO> getLoggedPhAdmin() {

		
		
		UUID phAdminId = getLoggedUserId();
		PharmacyAdmin phAdmin= phAdminRepository.getOne(phAdminId);
		
		
		if(phAdmin==null) {
			System.out.println("Admin apoteke je null");
		}

		return new UnspecifiedDTO<PhAdminDTO>(phAdminId , new PhAdminDTO(phAdmin.getEmail(), phAdmin.getName(), phAdmin.getSurname(), phAdmin.getAddress(),
				phAdmin.getPhoneNumber(), phAdmin.isActive(), phAdmin.getUserAuthorities()));
	}

	
	@Override
	public UUID getPhIdForPhAdmin() {
		UUID phAdmin=getLoggedUserId();
		
		PharmacyAdmin pharmacyAdmin = phAdminRepository.getOne(phAdmin);
		
		return pharmacyAdmin.getPharmacy().getId();
	}

	
	
	@Override
	public void refreshPatientPenalty() {
		UUID patientID=getLoggedUserId();
		Patient patient = patientRepository.getOne(patientID);
				
		int currentDate=new Date().getDate();
			
		if(currentDate==1 && !patient.isRefreshPenalties()) {
			patient.setPenalty(0);
			patient.setRefreshPenalties(true);
			
			
		}
			   
		if(currentDate!=1 ) {
			patient.setRefreshPenalties(false);
			
		}
		
	}
	
	@Override
	public void refreshPatientsPenalties() {
		
		List<Patient> patients= patientRepository.findAll();
		
		for (Patient patient : patients) {
			patient.setPenalty(0);
			patientRepository.save(patient);
		}
		
	}
	
	public Pharmacy getPharmacyForLoggedDermatologist() {
		UUID dermatologistId = getLoggedUserId();
		Pharmacy pharmacy = null;
		List<WorkTime> workTimes = workTimeRepository.findWorkTimesForDeramtologistAndCurrentDate(dermatologistId);
		Date currentDateTime = new Date();
		int currentHours = currentDateTime.getHours();
		for(WorkTime wt : workTimes){
			if(wt.getStartTime() <= currentHours && wt.getEndTime() >= currentHours)
				pharmacy = wt.getPharmacy();
		}
		if(pharmacy == null)
			for(WorkTime wt : workTimes)
					pharmacy = wt.getPharmacy();
		if(pharmacy == null)
			throw new IllegalArgumentException("Dermatologist doesn't work in any pharamcy at current hours");
		return pharmacy;
	}
	
	@Override

	public List<UnspecifiedDTO<UserDTO>> findPatientByNameAndSurname(String name, String surname) {
		List<UnspecifiedDTO<UserDTO>> users = new ArrayList<UnspecifiedDTO<UserDTO>>();
		patientRepository.findPatientByNameAndSurname(name.toLowerCase(), surname.toLowerCase()).forEach((u) -> users.add(UserMapper.MapUserPersistenceToUserUnspecifiedDTO(u)));
		return users;
	}
	

	public List<UnspecifiedDTO<WorkTimeDTO>> getScheduleForEmployee(UUID id) {
		List<UnspecifiedDTO<WorkTimeDTO>> retWorkTimes = new ArrayList<UnspecifiedDTO<WorkTimeDTO>>();
		List <WorkTime> workTimes=workTimeRepository.findAll();
		for(WorkTime workTime : workTimes) {
			if(workTime.getEmployee().getId().equals(id))
				retWorkTimes.add(new UnspecifiedDTO<WorkTimeDTO>(workTime.getId(), new WorkTimeDTO(workTime.getPharmacy().getId(),workTime.getEmployee().getId(), workTime.getStartDate(), workTime.getEndDate(), workTime.getStartTime(),workTime.getEndTime(),workTime.getPharmacy().getName())));
		}
		
		return retWorkTimes;
	}

	
	@Override
	public List<UnspecifiedDTO<PharmacyDTO>> findAllPharmaciesByDermatologistId(UUID dermatologistId) {
		
			Dermatologist dermatologist = dermatologistRepository.getOne(dermatologistId);
			
			List<UnspecifiedDTO<PharmacyDTO>> pharmaciesDTO = new ArrayList<UnspecifiedDTO<PharmacyDTO>>();
			
			
			
			for (Pharmacy ph : dermatologist.getPharmacies()) 
			{	
				PharmacyDTO pharmacyDTO= new PharmacyDTO(ph);	
				pharmaciesDTO.add(new UnspecifiedDTO<PharmacyDTO>(ph.getId(),pharmacyDTO));
			}
			
			return pharmaciesDTO;
	
	} 
	
	
}
