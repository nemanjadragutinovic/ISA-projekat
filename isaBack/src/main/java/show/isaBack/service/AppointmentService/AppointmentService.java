package show.isaBack.service.AppointmentService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import show.isaBack.DTO.AppointmentDTO.DermatologistAppointmentDTO;
import show.isaBack.DTO.AppointmentDTO.IdDTO;
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
import show.isaBack.model.UserCharacteristics.WorkTime;
import show.isaBack.model.appointment.Appointment;
import show.isaBack.model.appointment.AppointmentStatus;
import show.isaBack.model.appointment.AppointmentType;
import show.isaBack.repository.AppointmentRepository.AppointmentRepository;
import show.isaBack.repository.userRepository.DermatologistRepository;
import show.isaBack.repository.userRepository.PatientRepository;
import show.isaBack.repository.userRepository.PharmacistRepository;
import show.isaBack.repository.userRepository.UserRepository;
import show.isaBack.repository.userRepository.WorkTimeRepository;
import show.isaBack.serviceInterfaces.IAppointmentService;
import show.isaBack.serviceInterfaces.IService;
import show.isaBack.serviceInterfaces.IUserInterface;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;


@Service
public class AppointmentService implements IAppointmentService{

	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private DermatologistRepository dermatologistRepository;
	
	
	private AppointmentsMapper appointmentsMapper= new AppointmentsMapper();
	
	@Autowired
	private IUserInterface userService;
	

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
	
	
	
	@Override
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsForPharmacyAndForAppointmentType(UUID pharmacyId,
			AppointmentType appointmentType) {
		
		List<Appointment> appointments = appointmentRepository.findAllFreeAppointmentsForPharmacyAndForAppointmentType(pharmacyId, appointmentType); 
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
	public void reserveDermatologistAppointment(UUID appointmentId) {
		
		UUID patientId=userService.getLoggedUserId();
		Patient patient = patientRepository.findById(patientId).get();
	
		Appointment appointment = appointmentRepository.findById(appointmentId).get();
		
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
		User eployee = userRepository.findById(reservationRequestDTO.getPharmacistId()).get();
		Pharmacy pharmacy = pharmacistRepository.findPharmacyWhereWorksPharmacist(reservationRequestDTO.getPharmacistId());
		
		
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
