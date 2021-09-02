package show.isaBack.service.AppointmentService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.hibernate.boot.registry.classloading.spi.ClassLoaderService.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;


import show.isaBack.DTO.AppointmentDTO.AppointmentDTO;
import show.isaBack.DTO.AppointmentDTO.AppointmentReportDTO;
import show.isaBack.DTO.AppointmentDTO.DermatologistAppointmentDTO;
import show.isaBack.DTO.AppointmentDTO.FormAppointmentDTO;
import show.isaBack.DTO.AppointmentDTO.FreeAppointmentPeriodDTO;
import show.isaBack.DTO.AppointmentDTO.IdDTO;
import show.isaBack.DTO.AppointmentDTO.NewConsultationDTO;
import show.isaBack.DTO.AppointmentDTO.ParamsFromAppointmentDTO;
import show.isaBack.DTO.AppointmentDTO.ReservationConsultationDTO;
import show.isaBack.DTO.userDTO.AuthorityDTO;
import show.isaBack.DTO.userDTO.EmployeeGradeDTO;
import show.isaBack.Mappers.Appointmets.AppointmentsMapper;
import show.isaBack.emailService.EmailService;
import show.isaBack.model.Dermatologist;
import show.isaBack.model.Patient;
import show.isaBack.model.Pharmacist;
import show.isaBack.model.Pharmacy;
import show.isaBack.model.User;
import show.isaBack.model.UserCharacteristics.UserType;
import show.isaBack.model.UserCharacteristics.WorkTime;
import show.isaBack.model.appointment.Appointment;
import show.isaBack.model.appointment.AppointmentNotScheduledException;
import show.isaBack.model.appointment.AppointmentReport;
import show.isaBack.model.appointment.AppointmentStatus;
import show.isaBack.model.appointment.AppointmentTimeOutofWorkTimeRange;
import show.isaBack.model.appointment.AppointmentTimeOverlappingWithOtherAppointmentException;
import show.isaBack.model.appointment.AppointmentType;
import show.isaBack.model.drugs.DrugReservation;
import show.isaBack.model.drugs.EReceipt;
import show.isaBack.repository.AppointmentRepository.AppointmentReportRepository;
import show.isaBack.repository.AppointmentRepository.AppointmentRepository;

import show.isaBack.repository.pharmacyRepository.PharmacyRepository;

import show.isaBack.repository.drugsRepository.DrugReservationRepository;
import show.isaBack.repository.drugsRepository.EReceiptRepository;

import show.isaBack.repository.userRepository.DermatologistRepository;
import show.isaBack.repository.userRepository.PatientRepository;
import show.isaBack.repository.userRepository.PharmacistRepository;
import show.isaBack.repository.userRepository.UserRepository;
import show.isaBack.repository.userRepository.WorkTimeRepository;
import show.isaBack.service.loyalityService.LoyalityProgramService;
import show.isaBack.serviceInterfaces.IAppointmentService;
import show.isaBack.serviceInterfaces.ILoyaltyService;
import show.isaBack.serviceInterfaces.IService;
import show.isaBack.serviceInterfaces.IUserInterface;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;


@Service
public class AppointmentService implements IAppointmentService{

	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private AppointmentReportRepository appointmentReportRepository;
	
	@Autowired
	private DermatologistRepository dermatologistRepository;
	
	
	private AppointmentsMapper appointmentsMapper= new AppointmentsMapper();
	
	@Autowired
	private IUserInterface userService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PharmacistRepository pharmacistRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private WorkTimeRepository workTimeRepository;
	
	@Autowired
	private ILoyaltyService loyaltyService;

	@Autowired
	private EReceiptRepository eReceiptRepository;
	
	@Autowired
	private DrugReservationRepository drugReservationRepository;

	@Autowired
	private LoyalityProgramService loyalityProgramService;
	
	
	@Override
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsForPharmacyAndForAppointmentType(UUID pharmacyId,
			AppointmentType appointmentType) {
		
		List<Appointment> appointments = appointmentRepository.findAllFreeAppointmentsForPharmacyAndForAppointmentType(pharmacyId, appointmentType); 
		System.out.println(appointments);
		UUID patientId=userService.getLoggedUserId();
		
		for (Appointment appointment : appointments) {
			appointment.setPrice(loyaltyService.getDiscountPriceForExaminationAppointmentForPatient(patientId,appointment.getPrice()));
		}
		
		
		List<Dermatologist> allDermatologists= dermatologistRepository.findAll();		
		
		List<UnspecifiedDTO<EmployeeGradeDTO>> dermatologistEmployees= new ArrayList<UnspecifiedDTO<EmployeeGradeDTO>>();
		
		for (Dermatologist dermatologist : allDermatologists) {
			double avgGrade = userService.getAvgGradeForEmployee(dermatologist.getId());
			dermatologistEmployees.add(appointmentsMapper.MapDermatologistToEmployeeDTO(dermatologist,avgGrade));
		}
		
		List<UnspecifiedDTO<DermatologistAppointmentDTO>> freeAppointments=  appointmentsMapper.MapAppointmentsToListAppointmentsDTO(appointments,dermatologistEmployees);             
		
		
		return freeAppointments;
		
	}
	
	
	@Override
	@Transactional
	public void reserveDermatologistAppointment(UUID appointmentId) {
		
		UUID patientId=userService.getLoggedUserId();
		Patient patient = patientRepository.findById(patientId).get();
	
		if(patient.getPenalty()>=3)
			throw new IllegalArgumentException("You can't reserve appointment because you have 3 and more penalties! ");
		
		Appointment appointment = appointmentRepository.findById(appointmentId).get();
		appointment.setPrice(loyaltyService.getDiscountPriceForExaminationAppointmentForPatient(patientId, appointment.getPrice()));
		
		canPatientReserveAppointment(appointment, patient);
		
		appointment.setAppointmentStatus(AppointmentStatus.SCHEDULED);
		appointment.setPatient(patient);
		
		appointmentRepository.save(appointment);
		
		try {
			emailService.sendAppointmentReservationNotification(appointment);
		} catch (MessagingException e) {}
		
	}
	
	
	
	
	
	public void canPatientReserveAppointment(Appointment appointment, Patient patient) {
		if(doesPatientHaveAppointmentInChosenTime(appointment,patient))
			throw new IllegalArgumentException("As same tame you have already other appointment ");
		
		if (!(appointment.getStartDateTime().after(new Date())))
			throw new IllegalArgumentException("This term has expired");
		
	}
	
	
	
	private boolean doesPatientHaveAppointmentInChosenTime(Appointment appointment,Patient patient) {
		return appointmentRepository.findAllAppointmentsInGivenDateTimeForGivenTypeForPatient(patient.getId(),appointment.getStartDateTime(), appointment.getEndDateTime()).size() > 0;
	}
	
	
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> sortByPriceAscendingAllFreeDermatologistAppointments(UUID pharmacyId,AppointmentType appointmentType){
		
		List<Appointment> appointments = appointmentRepository.sortByPriceDescendingAllFreeDermatologistAppointments(pharmacyId, appointmentType); 
		List<Dermatologist> allDermatologists= dermatologistRepository.findAll();		
		
		List<UnspecifiedDTO<EmployeeGradeDTO>> dermatologistEmployees= new ArrayList<UnspecifiedDTO<EmployeeGradeDTO>>();
		
		for (Dermatologist dermatologist : allDermatologists) {
			double avgGrade = userService.getAvgGradeForEmployee(dermatologist.getId());
			dermatologistEmployees.add(appointmentsMapper.MapDermatologistToEmployeeDTO(dermatologist,avgGrade));
		}
		
		List<UnspecifiedDTO<DermatologistAppointmentDTO>> freeAppointments=  appointmentsMapper.MapAppointmentsToListAppointmentsDTO(appointments,dermatologistEmployees);             
		
		
		return freeAppointments;
		
		
	}
	
	
	
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> sortByPriceDescendingAllFreeDermatologistAppointments(UUID pharmacyId,AppointmentType appointmentType){
		
		List<Appointment> appointments = appointmentRepository.sortByPriceAscendingAllFreeDermatologistAppointments(pharmacyId, appointmentType); 
		List<Dermatologist> allDermatologists= dermatologistRepository.findAll();		
		
		List<UnspecifiedDTO<EmployeeGradeDTO>> dermatologistEmployees= new ArrayList<UnspecifiedDTO<EmployeeGradeDTO>>();
		
		for (Dermatologist dermatologist : allDermatologists) {
			double avgGrade = userService.getAvgGradeForEmployee(dermatologist.getId());
			dermatologistEmployees.add(appointmentsMapper.MapDermatologistToEmployeeDTO(dermatologist,avgGrade));
		}
		
		List<UnspecifiedDTO<DermatologistAppointmentDTO>> freeAppointments=  appointmentsMapper.MapAppointmentsToListAppointmentsDTO(appointments,dermatologistEmployees);             
		
		
		return freeAppointments;
		
		
	}
	
	
	
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> sortByDermatologistGradeAscendingAllFreeDermatologistAppointments(UUID pharmacyId,AppointmentType appointmentType){
		
		List<UnspecifiedDTO<DermatologistAppointmentDTO>> employeeWithGradeInDermatologistAppointment= new ArrayList<UnspecifiedDTO<DermatologistAppointmentDTO>>();
		employeeWithGradeInDermatologistAppointment= findAllFreeAppointmentsForPharmacyAndForAppointmentType(pharmacyId,appointmentType);
		
		Collections.sort(employeeWithGradeInDermatologistAppointment, (appointment1, appointment2) -> Double.compare(appointment1.EntityDTO.getEmployee().EntityDTO.getGrade(), appointment2.EntityDTO.getEmployee().EntityDTO.getGrade()));
		
		return employeeWithGradeInDermatologistAppointment;
		
		
	}
	
	
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> sortByDermatologistGradeDescendingAllFreeDermatologistAppointments(UUID pharmacyId,AppointmentType appointmentType){
		
		List<UnspecifiedDTO<DermatologistAppointmentDTO>> employeeWithGradeInDermatologistAppointment= new ArrayList<UnspecifiedDTO<DermatologistAppointmentDTO>>();
		employeeWithGradeInDermatologistAppointment= findAllFreeAppointmentsForPharmacyAndForAppointmentType(pharmacyId,appointmentType);
		
		Collections.sort(employeeWithGradeInDermatologistAppointment, (appointment1, appointment2) -> Double.compare(appointment1.EntityDTO.getEmployee().EntityDTO.getGrade(), appointment2.EntityDTO.getEmployee().EntityDTO.getGrade()));
		Collections.reverse(employeeWithGradeInDermatologistAppointment);
		
		return employeeWithGradeInDermatologistAppointment;
		
		
	}
	
	
	
	
	@Override
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllFuturePatientsAppointmets(AppointmentType appointmentType) {
		
		UUID logedPatiendID= userService.getLoggedUserId();
		List<Appointment> appointments = appointmentRepository.findAllFuturePatientsAppointmets(logedPatiendID, appointmentType); 
		System.out.println(appointments);
		List<Dermatologist> allDermatologists= dermatologistRepository.findAll();		
		
		List<UnspecifiedDTO<EmployeeGradeDTO>> dermatologistEmployees= new ArrayList<UnspecifiedDTO<EmployeeGradeDTO>>();
		
		for (Dermatologist dermatologist : allDermatologists) {
			double avgGrade = userService.getAvgGradeForEmployee(dermatologist.getId());
			dermatologistEmployees.add(appointmentsMapper.MapDermatologistToEmployeeDTO(dermatologist,avgGrade));
		}
		
		List<UnspecifiedDTO<DermatologistAppointmentDTO>> freeAppointments=  appointmentsMapper.MapAppointmentsToListAppointmentsDTO(appointments,dermatologistEmployees);             
		
		
		return freeAppointments;
		
	}
	
	
	
	
	@Override
	public void cancelAppointment(UUID appointmentId) {
		
		Appointment appointment = appointmentRepository.findById(appointmentId).get();
		

		if (appointment==null)
			throw new EntityNotFoundException("Appointment doesn't exist");
		
		if (appointment.getPatient()==null)
			throw new IllegalArgumentException("Appointment doesn't have patient");
		
		appointment.setAppointmentStatus(AppointmentStatus.FREE);
		appointment.setPatient(null);
		
		appointmentRepository.save(appointment);
		
		
	}
	
	
	@Override
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsAppointmets(AppointmentType appointmentType) {
		
		UUID logedPatiendID= userService.getLoggedUserId();
		List<Appointment> appointments = appointmentRepository.findAllHistoryPatientsAppointmets(logedPatiendID, appointmentType); 
		System.out.println(appointments);
		List<Dermatologist> allDermatologists= dermatologistRepository.findAll();		
		
		List<UnspecifiedDTO<EmployeeGradeDTO>> dermatologistEmployees= new ArrayList<UnspecifiedDTO<EmployeeGradeDTO>>();
		
		for (Dermatologist dermatologist : allDermatologists) {
			double avgGrade = userService.getAvgGradeForEmployee(dermatologist.getId());
			dermatologistEmployees.add(appointmentsMapper.MapDermatologistToEmployeeDTO(dermatologist,avgGrade));
		}
		
		
		List<UnspecifiedDTO<DermatologistAppointmentDTO>> freeAppointments=  appointmentsMapper.MapAppointmentsToListAppointmentsDTO(appointments,dermatologistEmployees);             
		
		
		return freeAppointments;
		
	}
	
	
	@Override
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllFuturePatientsConsultations(AppointmentType appointmentType) {
		
		UUID logedPatiendID= userService.getLoggedUserId();
		List<Appointment> appointments = appointmentRepository.findAllFuturePatientsAppointmets(logedPatiendID, appointmentType); 
		System.out.println(appointments);
		List<Pharmacist> allPharmacists= pharmacistRepository.findAll();		
		
		List<UnspecifiedDTO<EmployeeGradeDTO>> pharmacistEmployees= new ArrayList<UnspecifiedDTO<EmployeeGradeDTO>>();
		
		for (Pharmacist pharmacist : allPharmacists) {
			double avgGrade = userService.getAvgGradeForEmployee(pharmacist.getId());
			pharmacistEmployees.add(appointmentsMapper.MapPharmacistsToEmployeeDTO(pharmacist,avgGrade));
		}
		
		
		
		List<UnspecifiedDTO<DermatologistAppointmentDTO>> freeAppointments=  appointmentsMapper.MapAppointmentsToListAppointmentsDTO(appointments,pharmacistEmployees);             
		
		
		return freeAppointments;
		
	}
	
	
	
	@Override
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsConsultations(AppointmentType appointmentType) {
		
		UUID logedPatiendID= userService.getLoggedUserId();
		List<Appointment> appointments = appointmentRepository.findAllHistoryPatientsAppointmets(logedPatiendID, appointmentType); 
		System.out.println(appointments);
		List<Pharmacist> allPharmacists= pharmacistRepository.findAll();		
		
		List<UnspecifiedDTO<EmployeeGradeDTO>> pharmacistEmployees= new ArrayList<UnspecifiedDTO<EmployeeGradeDTO>>();
		
		for (Pharmacist pharmacist : allPharmacists) {
			double avgGrade = userService.getAvgGradeForEmployee(pharmacist.getId());
			pharmacistEmployees.add(appointmentsMapper.MapPharmacistsToEmployeeDTO(pharmacist,avgGrade));
		}
		
		
		
		List<UnspecifiedDTO<DermatologistAppointmentDTO>> freeAppointments=  appointmentsMapper.MapAppointmentsToListAppointmentsDTO(appointments,pharmacistEmployees);             
		
		
		return freeAppointments;
		
	}
	
	
	@Override
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsAppointmetsSortByPriceAscending(AppointmentType appointmentType) throws Exception {
				
		List<UnspecifiedDTO<DermatologistAppointmentDTO>> sorterdAppointments=  findAllHistoryPatientsAppointmets(appointmentType);             
		
		if(sorterdAppointments==null)
			throw new Exception(" The list is null");
		
		Collections.sort(sorterdAppointments, (appointment1, appointment2) -> Double.compare(appointment1.EntityDTO.getPrice(), appointment2.EntityDTO.getPrice()));
		
		
		return sorterdAppointments;
		
	}
	
	
	@Override
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsAppointmetsSortByPriceDescending(AppointmentType appointmentType) throws Exception {
				
		List<UnspecifiedDTO<DermatologistAppointmentDTO>> sorterdAppointments=  findAllHistoryPatientsAppointmets(appointmentType);             
		
		if(sorterdAppointments==null)
			throw new Exception(" The list is null");
		
		Collections.sort(sorterdAppointments, (appointment1, appointment2) -> Double.compare(appointment1.EntityDTO.getPrice(), appointment2.EntityDTO.getPrice()));
		Collections.reverse(sorterdAppointments);
		
		return sorterdAppointments;
		
	}
	
	@Override
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsAppointmetsSortByDateAscending(AppointmentType appointmentType) throws Exception {
				
		List<UnspecifiedDTO<DermatologistAppointmentDTO>> sorterdAppointments=  findAllHistoryPatientsAppointmets(appointmentType);             
		
		if(sorterdAppointments==null)
			throw new Exception(" The list is null");
		
		Collections.sort(sorterdAppointments, (appointment1, appointment2) -> Double.compare(appointment1.EntityDTO.getStartDateTime().getTime(), appointment2.EntityDTO.getStartDateTime().getTime()));
		
		
		return sorterdAppointments;
		
	}
	
	@Override
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsAppointmetsSortByDateDescending(AppointmentType appointmentType) throws Exception {
				
		List<UnspecifiedDTO<DermatologistAppointmentDTO>> sorterdAppointments=  findAllHistoryPatientsAppointmets(appointmentType);             
		
		if(sorterdAppointments==null)
			throw new Exception(" The list is null");
		
		Collections.sort(sorterdAppointments, (appointment1, appointment2) -> Double.compare(appointment1.EntityDTO.getStartDateTime().getTime(), appointment2.EntityDTO.getStartDateTime().getTime()));
		Collections.reverse(sorterdAppointments);
		
		return sorterdAppointments;
		
	}
	
	
	
	@Override
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsAppointmetsSortByDurationAscending(AppointmentType appointmentType) throws Exception {
				
		List<UnspecifiedDTO<DermatologistAppointmentDTO>> sorterdAppointments=  findAllHistoryPatientsAppointmets(appointmentType);             
		
		if(sorterdAppointments==null)
			throw new Exception(" The list is null");
		
		Collections.sort(sorterdAppointments, (appointment1, appointment2) -> Double.compare(appointment1.EntityDTO.getEndDateTime().getTime() - appointment1.EntityDTO.getStartDateTime().getTime(),       
					appointment2.EntityDTO.getEndDateTime().getTime() - appointment2.EntityDTO.getStartDateTime().getTime()));
		
		return sorterdAppointments;
		
	}
	
	
	@Override
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsAppointmetsSortByDurationDescending(AppointmentType appointmentType) throws Exception {
				
		List<UnspecifiedDTO<DermatologistAppointmentDTO>> sorterdAppointments=  findAllHistoryPatientsAppointmets(appointmentType);             
		
		if(sorterdAppointments==null)
			throw new Exception(" The list is null");
		
		Collections.sort(sorterdAppointments, (appointment1, appointment2) -> Double.compare(appointment1.EntityDTO.getEndDateTime().getTime() - appointment1.EntityDTO.getStartDateTime().getTime(),       
					appointment2.EntityDTO.getEndDateTime().getTime() - appointment2.EntityDTO.getStartDateTime().getTime()));
		
		Collections.reverse(sorterdAppointments);
		
		return sorterdAppointments;
		
	}
	
	
	
	
	@Override
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsConsultationsSortByPriceAscending(AppointmentType appointmentType) throws Exception {
				
		List<UnspecifiedDTO<DermatologistAppointmentDTO>> sorterdAppointments=  findAllHistoryPatientsConsultations(appointmentType);             
		
		if(sorterdAppointments==null)
			throw new Exception(" The list is null");
		
		Collections.sort(sorterdAppointments, (appointment1, appointment2) -> Double.compare(appointment1.EntityDTO.getPrice(), appointment2.EntityDTO.getPrice()));
		
		
		return sorterdAppointments;
		
	}
	
	@Override
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsConsultationsSortByPriceDescending(AppointmentType appointmentType) throws Exception {
		
		List<UnspecifiedDTO<DermatologistAppointmentDTO>> sorterdAppointments=  findAllHistoryPatientsConsultations(appointmentType);             
		
		if(sorterdAppointments==null)
			throw new Exception(" The list is null");
		
		Collections.sort(sorterdAppointments, (appointment1, appointment2) -> Double.compare(appointment1.EntityDTO.getPrice(), appointment2.EntityDTO.getPrice()));
		Collections.reverse(sorterdAppointments);
		
		return sorterdAppointments;
		
	}
	
	
	
	@Override
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsConsultationsSortByDateAscending(AppointmentType appointmentType) throws Exception {
				
		List<UnspecifiedDTO<DermatologistAppointmentDTO>> sorterdAppointments=  findAllHistoryPatientsConsultations(appointmentType);             
		
		if(sorterdAppointments==null)
			throw new Exception(" The list is null");
		
		Collections.sort(sorterdAppointments, (appointment1, appointment2) -> Double.compare(appointment1.EntityDTO.getStartDateTime().getTime(), appointment2.EntityDTO.getStartDateTime().getTime()));
		
		
		return sorterdAppointments;
		
	}
	
	@Override
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsConsultationsSortByDateDescending(AppointmentType appointmentType) throws Exception {
		
		List<UnspecifiedDTO<DermatologistAppointmentDTO>> sorterdAppointments=  findAllHistoryPatientsConsultations(appointmentType);             
		
		if(sorterdAppointments==null)
			throw new Exception(" The list is null");
		
		Collections.sort(sorterdAppointments, (appointment1, appointment2) -> Double.compare(appointment1.EntityDTO.getStartDateTime().getTime(), appointment2.EntityDTO.getStartDateTime().getTime()));
		Collections.reverse(sorterdAppointments);
		
		return sorterdAppointments;
		
	}
	
	
	
	@Override
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsConsultationsSortByDurationAscending(AppointmentType appointmentType) throws Exception {
				
		List<UnspecifiedDTO<DermatologistAppointmentDTO>> sorterdAppointments=  findAllHistoryPatientsConsultations(appointmentType);             
		
		if(sorterdAppointments==null)
			throw new Exception(" The list is null");
		
		Collections.sort(sorterdAppointments, (appointment1, appointment2) -> Double.compare(appointment1.EntityDTO.getEndDateTime().getTime() - appointment1.EntityDTO.getStartDateTime().getTime(),       
				appointment2.EntityDTO.getEndDateTime().getTime() - appointment2.EntityDTO.getStartDateTime().getTime()));
		
		
		return sorterdAppointments;
		
	}
	
	@Override
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsConsultationsSortByDurationDescending(AppointmentType appointmentType) throws Exception {
		
		List<UnspecifiedDTO<DermatologistAppointmentDTO>> sorterdAppointments=  findAllHistoryPatientsConsultations(appointmentType);             
		
		if(sorterdAppointments==null)
			throw new Exception(" The list is null");
		
		Collections.sort(sorterdAppointments, (appointment1, appointment2) -> Double.compare(appointment1.EntityDTO.getEndDateTime().getTime() - appointment1.EntityDTO.getStartDateTime().getTime(),       
				appointment2.EntityDTO.getEndDateTime().getTime() - appointment2.EntityDTO.getStartDateTime().getTime()));
		Collections.reverse(sorterdAppointments);
		
		return sorterdAppointments;
		
	}
	
	
	
	
	
	@Override	
	public List<Pharmacy> findAllPharmaciesForAppointmentTypeAndForDateRange(Date startDate, Date endDate) {

		List<WorkTime> WorkTimesInDateRange= new ArrayList<WorkTime>();
		
		WorkTimesInDateRange= workTimeRepository.findAllWorkTimesInDateRange(startDate,endDate, startDate.getHours(), endDate.getHours());
				
		List<Appointment> busyConsultationsInDataRange= appointmentRepository.findAllBusyConsultationsInDataRange(startDate,endDate);
					
		List<Pharmacy> pharmacyWithFreeConsulations= findPharmacyWithFreeConsultatins(WorkTimesInDateRange,busyConsultationsInDataRange);
		
				
		return pharmacyWithFreeConsulations;
	}
	
	
	
	public List<Pharmacy> findPharmacyWithFreeConsultatins(List<WorkTime> WorkTimesInDateRange, List<Appointment> busyConsultationsInDataRange){
		
		List<Pharmacy> pharmacyWithFreeConsulations= new ArrayList<Pharmacy>();
		
		for (WorkTime currentWorkTime : WorkTimesInDateRange) {
				
				boolean scheduled= false;			
				
				for (Appointment currentAppointment : busyConsultationsInDataRange) {
					if(currentWorkTime.getEmployee().getId().equals(currentAppointment.getEmployee().getId())) {
						scheduled = true;
						break;
					}
					
				}
				
				if(scheduled==false)
					pharmacyWithFreeConsulations.add(currentWorkTime.getPharmacy());
			
		}
		
		
		
		return throwOutDuplicatesPharmacies(pharmacyWithFreeConsulations);
		
		
	}
	
	public List<Pharmacy> throwOutDuplicatesPharmacies(List<Pharmacy> pharmacyWithFreeConsulations){
		
		List<Pharmacy> filteredPharmacies= new ArrayList<Pharmacy>();
		
		for (Pharmacy pharmacy : pharmacyWithFreeConsulations) {
			
				boolean alreadyAdded= false;
				for(Pharmacy curentFilteredPharmacy : filteredPharmacies) {
					if(pharmacy.getId().equals(curentFilteredPharmacy.getId())) {
						alreadyAdded = true;
						break;
					}
				}
				
				if(alreadyAdded==false)
					filteredPharmacies.add(pharmacy);
					
		}
		
		
		return filteredPharmacies;
		
		
	}
	
	
	
	@Override
	public List<User> fidnAllFreePharmacistsForSelectedPharmacyInDataRange(Date startDate, Date endDate, UUID pharmacyId) {

		List<WorkTime> WorkTimesInDateRange= new ArrayList<WorkTime>();
		
		WorkTimesInDateRange= workTimeRepository.findAllWorkTimesInDateRangeForPharmacy(startDate,endDate, startDate.getHours(), endDate.getHours(), pharmacyId);
			
		List<Appointment> busyConsultationsInDataRange= appointmentRepository.findAllBusyConsultationsInDataRangeForPharmacy(startDate,endDate,pharmacyId);
					
		List<User> pharmacist= findPharmacistWithFreeConsultatinsForPharmacy(WorkTimesInDateRange,busyConsultationsInDataRange);
		
				
		return pharmacist;
	}
	
	
	
	
	public List<User> findPharmacistWithFreeConsultatinsForPharmacy(List<WorkTime> WorkTimesInDateRange, List<Appointment> busyConsultationsInDataRange) {

		List<User> pharmacistWithFreeConsulations= new ArrayList<User>();
		
		for (WorkTime currentWorkTime : WorkTimesInDateRange) {
				
				boolean scheduled= false;			
				
				for (Appointment currentAppointment : busyConsultationsInDataRange) {
					if(currentWorkTime.getEmployee().getId().equals(currentAppointment.getEmployee().getId())) {
						scheduled = true;
						break;
					}
					
				}
				
				if(scheduled==false)
					pharmacistWithFreeConsulations.add(currentWorkTime.getEmployee());
			
		}
		
		
		
		return throwOutDuplicatesPharmacist(pharmacistWithFreeConsulations);
	}
	
	
	
	public List<User> throwOutDuplicatesPharmacist(List<User> pharmacistWithFreeConsulations){
		
		List<User> filteredPharmacist= new ArrayList<User>();
		
		for (User user : pharmacistWithFreeConsulations) {
			
				boolean alreadyAdded= false;
				for(User currentFilteredUser : filteredPharmacist) {
					if(user.getId().equals(currentFilteredUser.getId())) {
						alreadyAdded = true;
						break;
					}
				}
				
				if(alreadyAdded==false)
					filteredPharmacist.add(user);
					
		}
		
		
		return filteredPharmacist;
		
		
	}
	
	@Override
	@Transactional
	public void reserveConsulationBySelectedPharmacist(ReservationConsultationDTO reservationRequestDTO){
		
		Date startDate= new Date(reservationRequestDTO.getStartDate());
		Date endDate= new Date(reservationRequestDTO.getStartDate() + 7200000);
		
		System.out.println( "startno vreme " + startDate + "   " +  " Krajnje vremee " + endDate);
		
	
		anyBusyConsultationsAndFreeWorkTimeInDataRange(reservationRequestDTO,startDate,endDate);
		
		Appointment appointment= createAppointment(reservationRequestDTO,startDate,endDate);
		
		appointmentRepository.save(appointment);
		
		
		try {
			emailService.sendConsultationAppointmentReservationNotification(appointment);
		} catch (MessagingException e) {}
		
		
	}
	
	
	
	public void anyBusyConsultationsAndFreeWorkTimeInDataRange(ReservationConsultationDTO reservationRequestDTO,Date startDate, Date endDate  ){
		
		if(appointmentRepository.findAllBusyConsultationsInDataRangeForPharmacist(startDate,endDate,reservationRequestDTO.getPharmacistId()).size()>0)
			throw new IllegalArgumentException("Pharmacist has alredy appointment time in selected data range");
		
		if(!( workTimeRepository.findAllWorkTimesInDateRangeForPharmacist(startDate,endDate, startDate.getHours(), endDate.getHours(), reservationRequestDTO.getPharmacistId()).size() > 0))
			throw new IllegalArgumentException("Pharmacist doesn't work in selected data range");
		
	}
	
	

	public Appointment createAppointment(ReservationConsultationDTO reservationRequestDTO,Date startDate, Date endDate  ){
		
		UUID patientId = userService.getLoggedUserId();
		Patient patient = patientRepository.findById(patientId).get();
		
		if(patient.getPenalty()>=3)
			throw new IllegalArgumentException("You can't reserve appointment because you have 3 and more penalties! ");
		
		User eployee = userRepository.findById(reservationRequestDTO.getPharmacistId()).get();
		Pharmacy pharmacy = pharmacistRepository.findPharmacyWhereWorksPharmacist(reservationRequestDTO.getPharmacistId());
		pharmacy.setConsultationPrice(loyaltyService.getDiscountPriceForConsultationAppointmentForPatient(patientId,pharmacy.getConsultationPrice()));
		
		Appointment appointment= new Appointment( eployee,pharmacy, startDate, endDate, pharmacy.getConsultationPrice(),patient, AppointmentType.CONSULTATION, AppointmentStatus.SCHEDULED);
		
		isAppointmentValid(appointment);
		
		return appointment;
		
	}
	
	public void isAppointmentValid(Appointment appointment ){
		
		if (!(appointment.getStartDateTime().after(new Date())))
			throw new IllegalArgumentException("Start date time can't be before current date time");
		
		if(appointmentRepository.findAllSheduledAppointmentsForPatientsInDataRange(appointment.getStartDateTime(), appointment.getEndDateTime(), appointment.getPatient().getId()).size() > 0)
				throw new IllegalArgumentException("Patient has alredy sheduled appointment in selected data range");
	}
	
	public boolean canPatientReportPharmacy(UUID pharmacyId){
		UUID patientId = userService.getLoggedUserId();
		
		List<Appointment> appointments = appointmentRepository.findAllFinishedAppointmentsForPatientinPharmacy(patientId,pharmacyId);
		
		if(appointments.size()>0) {
			return true;
		}
		List<EReceipt> ereceipts = eReceiptRepository.findAllEReceiptsWithPatientAndPharmacy(patientId, pharmacyId);
		
		if(ereceipts.size()>0) {
			return true;
		}
		List<DrugReservation> drugs = drugReservationRepository.findAllhistoryDrugsReservationwithPatientAndPharmacy(patientId, pharmacyId);
		
		if(drugs.size()>0) {
			return true;
		}
		
		return false;
		
	}
	
	
	
	@Override
	public void refreshPatientsAppointments(){
		
		List<Appointment> appointmentsScheduledThasHaveExpired= appointmentRepository.findAllScheduledAppointmentThatHaveExpired();
		
		for (Appointment appointment : appointmentsScheduledThasHaveExpired) {
				appointment.setAppointmentStatus(AppointmentStatus.EXPIRED);		
				Patient patient = patientRepository.findById(appointment.getPatient().getId()).get();
				patient.addPenalties(1);
				patientRepository.save(patient);
				appointmentRepository.save(appointment);
				
		}
		
	}
	
	@Override
	public boolean createDermatologistsAppointment(FormAppointmentDTO appointmentDTO) {
		System.out.println("ALO BIDIBOU");
			User dermatologist = userRepository.getOne(appointmentDTO.getDermatologistId());
			Pharmacy pharmacy = pharmacyRepository.getOne(appointmentDTO.getPhId());
			
			Appointment appointment= new Appointment( dermatologist,pharmacy, appointmentDTO.getStartDateTime(), appointmentDTO.getEndDateTime(), appointmentDTO.getPrice(),null, AppointmentType.EXAMINATION, AppointmentStatus.FREE);
			appointmentRepository.save(appointment);
			if(appointment.getId() !=null) {
				return true;
			}else {
				return false;
			}
			
			
		
	}
	
	
	@Override
	public List<FreeAppointmentPeriodDTO> generateListFreePeriods(ParamsFromAppointmentDTO paramsFromAppointmentDTO) {
		WorkTime workTime = workTimeRepository.getDermatologistsWorkTimeForPharmacy(paramsFromAppointmentDTO.getDermatologistId(),paramsFromAppointmentDTO.getPhId(),paramsFromAppointmentDTO.getDate());
		List<Appointment> existingAppointments = appointmentRepository.getCreatedAppoitntmentsByDermatologistByDate(paramsFromAppointmentDTO.getDermatologistId(),paramsFromAppointmentDTO.getPhId(),paramsFromAppointmentDTO.getDate());
		List<FreeAppointmentPeriodDTO> suggestionsForAppointment = new ArrayList<FreeAppointmentPeriodDTO>();
		Date startWorkTime = paramsFromAppointmentDTO.getDate();
		startWorkTime.setHours(workTime.getStartTime());
		startWorkTime.setMinutes(0);
		startWorkTime.setSeconds(0);
		
		LocalDateTime startTime=startWorkTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		Date endWorkTime =  paramsFromAppointmentDTO.getDate();
		endWorkTime.setHours(workTime.getEndTime());
		startWorkTime.setMinutes(0);
		endWorkTime.setSeconds(0);
		LocalDateTime endTime;
		int duration=paramsFromAppointmentDTO.getDuration();
		
		if(workTime==null) {System.out.println("null jjjeee");}
		Collections.sort(existingAppointments, (o1,o2)-> {return o1.getStartDateTime().compareTo(o2.getStartDateTime());});
		
		for(Appointment a: existingAppointments) {
			System.out.println("id termina "+a.getEndDateTime());
		}
		
		
		if(existingAppointments!=null) {
        	for (Appointment appointment : existingAppointments)
            {
                 endTime = convertToLocalDateTime(appointment.getStartDateTime());
                 suggestionsForAppointment.addAll(generateIntervals(startTime, endTime,duration));
                 startTime = convertToLocalDateTime(appointment.getEndDateTime());
                
            }
        }
        
        endTime = convertToLocalDateTime(endWorkTime);
        suggestionsForAppointment.addAll(generateIntervals(startTime, endTime,duration));
		
		
		return suggestionsForAppointment;
			
		}
	

	private List<FreeAppointmentPeriodDTO>generateIntervals(LocalDateTime startTime, LocalDateTime endTime,int durationOfFreeAppointment) {
		List<FreeAppointmentPeriodDTO> suggestionsForAppointment = new ArrayList<FreeAppointmentPeriodDTO>();
		
		while (Duration.between(startTime, endTime).toMinutes() >= Duration.ofMinutes(durationOfFreeAppointment).toMinutes())
        {
            FreeAppointmentPeriodDTO freePeriod = new FreeAppointmentPeriodDTO(convertToDate(startTime),convertToDate(startTime.plusMinutes(durationOfFreeAppointment)));
            startTime = convertToLocalDateTime(freePeriod.getEndDate());
            suggestionsForAppointment.add(freePeriod);
        }
        return suggestionsForAppointment;
	}


	@Override
	public List<UnspecifiedDTO<AppointmentDTO>> getCalendarAppointmentsByDermatologist(UUID pharmacyId) {
		List<Appointment> appointments = appointmentRepository.getCalendarDermatologistAppointmentsForPharamacy(userService.getLoggedUserId(), pharmacyId);
		
		List<UnspecifiedDTO<AppointmentDTO>> returnAppointments = AppointmentsMapper.MapAppointmentPersistenceListToAppointmentUnspecifiedDTOList (appointments);
		
		return returnAppointments;
	}
	
	@Override
	public List<UnspecifiedDTO<AppointmentDTO>> getCalendarAppointmentsByPharmacist(UUID pharmacyId) {
		List<Appointment> appointments = appointmentRepository.getCalendarAppointmentsByPharmacist(userService.getLoggedUserId(), pharmacyId);
		
		List<UnspecifiedDTO<AppointmentDTO>> returnAppointments = AppointmentsMapper.MapAppointmentPersistenceListToAppointmentUnspecifiedDTOList(appointments);
		
		return returnAppointments;
	}
	
	@Override
	public boolean hasExaminedPatient(UUID patientId) {
		return appointmentRepository.getFinishedAppointmentsForEmployeeForPatient(userService.getLoggedUserId(), patientId).size() > 0;
	}
	
	private void concatenateTreatmentReport(List<UnspecifiedDTO<AppointmentDTO>> appointments) {
		for (int i = 0; i < appointments.size(); i++) {
			UnspecifiedDTO<AppointmentDTO> appointment = appointments.get(i);
			if(appointment.EntityDTO.getAppointmentStatus() == AppointmentStatus.FINISHED) {
				try {
					AppointmentReport appointmentReport = appointmentReportRepository.findByAppointmentId(appointment.Id);
					AppointmentReportDTO appointmentReportDTO = new AppointmentReportDTO(appointmentReport.getAnamnesis(), appointmentReport.getDiagnosis(), appointmentReport.getTherapy(), appointmentReport.getId());
					appointment.EntityDTO.setAppointmentReportDTO(appointmentReportDTO);
					appointments.set(i, appointment);
				} catch (Exception e) {
					continue;
				}
			}
		}
	}
	
	@Override
	public List<UnspecifiedDTO<AppointmentDTO>> getAppointmentsByPatientAsEmpolyee(UUID patientId) {
		UUID userId = userService.getLoggedUserId();
		User user = userRepository.getOne(userId);
		List<Appointment> appointments = new ArrayList<Appointment>();
		if(user.getUserType() == UserType.DERMATOLOGIST) {
			Pharmacy pharmacy = userService.getPharmacyForLoggedDermatologist();
			List<Appointment> scheduledAppointments = appointmentRepository.getScheduledDermatologistAppointmentsByPatient(patientId, userId, pharmacy.getId());
			List<Appointment> finishedAppointments = new ArrayList<Appointment>();
			if (hasExaminedPatient(patientId))
				finishedAppointments = appointmentRepository.getFinishedDermatologistAppointmentsByPatient(patientId, userId, pharmacy.getId());
			appointments = Stream.concat(scheduledAppointments.stream(), finishedAppointments.stream()).collect(Collectors.toList());
		} else {
			Pharmacist pharmacist = pharmacistRepository.getOne(userId);
			List<Appointment> scheduledAppointments = appointmentRepository.getScheduledPharmacistAppointmentsByPatient(patientId, userId, pharmacist.getPharmacy().getId());
			List<Appointment> finishedAppointments = new ArrayList<Appointment>();
			if (hasExaminedPatient(patientId))
				finishedAppointments = appointmentRepository.getFinishedPharmacistAppointmentsByPatient(patientId, userId, pharmacist.getPharmacy().getId());
			appointments = Stream.concat(scheduledAppointments.stream(), finishedAppointments.stream()).collect(Collectors.toList());
		}
		List<UnspecifiedDTO<AppointmentDTO>> returnAppointments = AppointmentsMapper.MapAppointmentPersistenceListToAppointmentUnspecifiedDTOList(appointments);
		concatenateTreatmentReport(returnAppointments);
		return returnAppointments;
	}
	private LocalDateTime convertToLocalDateTime(Date dateToConvert) {
	    return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

	}
	
	private Date convertToDate(LocalDateTime dateToConvert) {
	    return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
	    }

	@Override
	public boolean isFutureAppointmentExists(UUID dermatologistId, UUID phId ) {
		List<Appointment> allFutureAppointments = appointmentRepository.findAppointmentForDermatologistInPharmacy(dermatologistId,phId);
		if(allFutureAppointments.size()>0)
			return true;
		
		return false;
	}
	
	@Override
	@Transactional
	public void didNotShowUpToAppointment(UUID id) {
		Appointment appointment = appointmentRepository.findById(id).get();
		appointment.setAppointmentStatus(AppointmentStatus.EXPIRED);
		Patient patient = patientRepository.getOne(appointment.getPatient().getId());
		patient.setPenalty(patient.getPenalty() + 1); 
		patientRepository.save(patient);
		appointmentRepository.save(appointment);
	}
	
	@Override
	public List<UnspecifiedDTO<AppointmentDTO>> getCreatedAppointmentsByDermatologist() {
		List<Appointment> appointments = appointmentRepository.getCreatedAppointmentsByDermatologist(userService.getLoggedUserId(), userService.getPharmacyForLoggedDermatologist().getId());
		
		List<UnspecifiedDTO<AppointmentDTO>> returnAppointments = AppointmentsMapper.MapAppointmentPersistenceListToAppointmentUnspecifiedDTOList(appointments);
		
		return returnAppointments;
	}
	
	public UnspecifiedDTO<AppointmentDTO> getAppointment(UUID appointmentId) {
		// current pharmacy?
		User user = userRepository.getOne(userService.getLoggedUserId());
		Pharmacy pharmacy;
		if (user.getUserType() == UserType.DERMATOLOGIST)
			pharmacy = userService.getPharmacyForLoggedDermatologist();
		else
			pharmacy = pharmacistRepository.getOne(user.getId()).getPharmacy();
		Appointment appointment = appointmentRepository.findById(appointmentId).get();
		if (appointment.getEmployee().getId() != user.getId() || appointment.getPharmacy().getId() != pharmacy.getId())
			throw new IllegalArgumentException("Can't access appointment not scheduled for given staff and pharmacy");
		return AppointmentsMapper.MapAppointmentPersistenceToAppointmentUnspecifiedDTO(appointment);
	}
	
	@Override
	@Transactional
	public void finishAppointment(UUID id) {
		Appointment appointment = appointmentRepository.findById(id).get();
		Patient patient = appointment.getPatient();
		if(appointment.getAppointmentType() == AppointmentType.EXAMINATION)
			patient.setPoints(patient.getPoints() + loyaltyService.get().getPointsForAppointment());
		else
			patient.setPoints(patient.getPoints() + loyaltyService.get().getPointsForConsulting());
		
		patientRepository.save(patient);
		appointment.setAppointmentStatus(AppointmentStatus.FINISHED);
		appointmentRepository.save(appointment);
	}
	@Override
	@Transactional
	public UUID newConsultation(NewConsultationDTO newConsultationDTO) throws AppointmentNotScheduledException, AppointmentTimeOverlappingWithOtherAppointmentException, AppointmentTimeOutofWorkTimeRange{
		long time = newConsultationDTO.getStartDateTime().getTime();
		Date endDateTime= new Date(time + (Integer.parseInt(env.getProperty("consultation_time")) * 60000));
		Patient patient = patientRepository.findById(newConsultationDTO.getPatientId()).get();
		UUID userId = userService.getLoggedUserId();
		Pharmacist pharmacist = pharmacistRepository.findById(userId).get();
		Pharmacy pharmacy = pharmacist.getPharmacy();
		double discountPrice = loyalityProgramService.getDiscountAppointmentPriceForPatient(pharmacy.getConsultationPrice(), AppointmentType.CONSULTATION, newConsultationDTO.getPatientId());
		Appointment appointment = new Appointment(AppointmentStatus.SCHEDULED, AppointmentType.CONSULTATION,endDateTime,discountPrice, newConsultationDTO.getStartDateTime(),pharmacist,patient,pharmacy);		
		CanCreateConsultationAllRestrictions(appointment);	
		appointmentRepository.save(appointment);
		pharmacistRepository.save(pharmacist);
		try {
			emailService.sendAppointmentReservationNotification(appointment);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return appointment.getId();
	}
	
private void CanCreateConsultationAllRestrictions(Appointment appointment) throws AppointmentTimeOverlappingWithOtherAppointmentException, AppointmentTimeOutofWorkTimeRange {
		
		if(doesStaffHasAppointmentInDesiredTime(appointment, appointment.getEmployee()))
			throw new AppointmentTimeOverlappingWithOtherAppointmentException("Cannot reserve appointment at same time as other appointment");
		
		if(!isInWorkTimeRange(appointment, appointment.getEmployee()))
			throw new AppointmentTimeOutofWorkTimeRange("Cannot reserve appointment out of work time range");
		
		if(doesPatientHaveAppointmentInDesiredTime(appointment, appointment.getPatient()))
			throw new AppointmentTimeOverlappingWithOtherAppointmentException("Cannot reserve appointment at same time as other appointment");
		
		if(appointment.getPatient().getPenalty() >= Integer.parseInt(env.getProperty("max_penalty_count")))
			throw new AuthorizationServiceException("Too many penalty points");
	
		if (!(appointment.getStartDateTime().after(new Date())))
				throw new IllegalArgumentException("Bad request");
		
		//if(isAbsent(appointment))
			//throw new IllegalArgumentException("Cannot reserve appointment at date dermatologist is absent");
	}
	
private boolean doesPatientHaveAppointmentInDesiredTime(Appointment appointment, Patient patient) {
	return appointmentRepository.findAllAppointmentsByAppointmentTimeAndPatient(appointment.getStartDateTime(), appointment.getEndDateTime(), patient.getId()).size() > 0;
}

/*private boolean isAbsent(Appointment appointment) {
	User user = appointment.getEmployee();
	if (user.getUserType() == UserType.DERMATOLOGIST)
		return absenceRepository.getAbsenceForDermatologistForDateForPharmacy(staff.getId(), appointment.getStartDateTime(), appointment.getPharmacy().getId()).size() > 0;
	else
		return absenceRepository.findPharmacistAbsenceByStaffIdAndDate(staff.getId(), appointment.getStartDateTime()).size() > 0;
}*/

private boolean doesStaffHasAppointmentInDesiredTime(Appointment appointment, User user) {
	return appointmentRepository.findAllAppointmentsByAppointmentTimeAndEmployee(appointment.getStartDateTime(), appointment.getEndDateTime(), user.getId()).size() > 0;
}	

@SuppressWarnings("deprecation")
private boolean isInWorkTimeRange(Appointment appointment, User user) {
	List<WorkTime> workTimes = workTimeRepository.findWorkTimesForStaff(user.getId());
	Date appointmentDate = new Date(appointment.getStartDateTime().getYear(), appointment.getStartDateTime().getMonth(), appointment.getStartDateTime().getDate(),0,0,0);
	for (WorkTime wt : workTimes) {
		Date startDate = new Date(wt.getStartDate().getYear(), wt.getStartDate().getMonth(), wt.getStartDate().getDate(),0,0,0);
		Date endDate = new Date(wt.getEndDate().getYear(), wt.getEndDate().getMonth(), wt.getEndDate().getDate(),0,0,0);	
		if(appointmentDate.before(startDate) || appointmentDate.after(endDate))
			continue;
		Date startDateTime = new Date(appointment.getStartDateTime().getYear(), appointment.getStartDateTime().getMonth(), appointment.getStartDateTime().getDate(),wt.getStartTime(),0,0);
		Date endDateTime = new Date(appointment.getStartDateTime().getYear(), appointment.getStartDateTime().getMonth(), appointment.getStartDateTime().getDate(),wt.getEndTime(),0,0);
		if((appointment.getStartDateTime().after(startDateTime) || appointment.getStartDateTime().equals(startDateTime)) && (appointment.getEndDateTime().before(endDateTime) || appointment.getEndDateTime().equals(endDateTime)))
			return true;
	}
	return false;
}


	
	@Override
	public List<UnspecifiedDTO<AuthorityDTO>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnspecifiedDTO<DermatologistAppointmentDTO> findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID create(DermatologistAppointmentDTO entityDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(DermatologistAppointmentDTO entityDTO, UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	
}
