package show.isaBack.service.AppointmentService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import show.isaBack.DTO.AppointmentDTO.DermatologistAppointmentDTO;
import show.isaBack.DTO.AppointmentDTO.IdDTO;
import show.isaBack.DTO.userDTO.AuthorityDTO;
import show.isaBack.DTO.userDTO.EmployeeGradeDTO;
import show.isaBack.Mappers.Appointmets.AppointmentsMapper;
import show.isaBack.emailService.EmailService;
import show.isaBack.model.Dermatologist;
import show.isaBack.model.Patient;
import show.isaBack.model.appointment.Appointment;
import show.isaBack.model.appointment.AppointmentStatus;
import show.isaBack.model.appointment.AppointmentType;
import show.isaBack.repository.AppointmentRepository.AppointmentRepository;
import show.isaBack.repository.userRepository.DermatologistRepository;
import show.isaBack.repository.userRepository.PatientRepository;
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
	private EmailService emailService;
	
	@Override
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsForPharmacyAndForAppointmentType(UUID pharmacyId,
			AppointmentType appointmentType) {
		
		List<Appointment> appointments = appointmentRepository.findAllFreeAppointmentsForPharmacyAndForAppointmentType(pharmacyId, appointmentType); 
		System.out.println(appointments);
		List<Dermatologist> allDermatologist= dermatologistRepository.findAll();		
		
		List<UnspecifiedDTO<EmployeeGradeDTO>> dermatologistEmployees= new ArrayList<UnspecifiedDTO<EmployeeGradeDTO>>();
		
		allDermatologist.forEach((dermatologist) -> dermatologistEmployees.add(appointmentsMapper.MapDermatologistToEmployeeDTO(dermatologist)));
		
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
		List<Dermatologist> allDermatologist= dermatologistRepository.findAll();		
		
		List<UnspecifiedDTO<EmployeeGradeDTO>> dermatologistEmployees= new ArrayList<UnspecifiedDTO<EmployeeGradeDTO>>();
		
		allDermatologist.forEach((dermatologist) -> dermatologistEmployees.add(appointmentsMapper.MapDermatologistToEmployeeDTO(dermatologist)));
		
		List<UnspecifiedDTO<DermatologistAppointmentDTO>> freeAppointments=  appointmentsMapper.MapAppointmentsToListAppointmentsDTO(appointments,dermatologistEmployees);             
		
		
		return freeAppointments;
		
		
	}
	
	
	
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> sortByPriceDescendingAllFreeDermatologistAppointments(UUID pharmacyId,AppointmentType appointmentType){
		
		List<Appointment> appointments = appointmentRepository.sortByPriceAscendingAllFreeDermatologistAppointments(pharmacyId, appointmentType); 
		List<Dermatologist> allDermatologist= dermatologistRepository.findAll();		
		
		List<UnspecifiedDTO<EmployeeGradeDTO>> dermatologistEmployees= new ArrayList<UnspecifiedDTO<EmployeeGradeDTO>>();
		
		allDermatologist.forEach((dermatologist) -> dermatologistEmployees.add(appointmentsMapper.MapDermatologistToEmployeeDTO(dermatologist)));
		
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