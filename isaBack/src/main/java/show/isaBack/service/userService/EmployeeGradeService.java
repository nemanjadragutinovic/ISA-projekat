package show.isaBack.service.userService;


import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import show.isaBack.DTO.userDTO.EmployeeForGradeDTO;
import show.isaBack.DTO.userDTO.EmployeeForGradeRequestDTO;
import show.isaBack.model.Patient;
import show.isaBack.model.User;
import show.isaBack.model.UserCharacteristics.EmployeeGrade;
import show.isaBack.model.appointment.Appointment;
import show.isaBack.repository.AppointmentRepository.AppointmentRepository;
import show.isaBack.repository.userRepository.EmployeeGradeRepository;
import show.isaBack.repository.userRepository.PatientRepository;
import show.isaBack.repository.userRepository.UserRepository;
import show.isaBack.serviceInterfaces.IEmployeeGradeService;
import show.isaBack.serviceInterfaces.IUserInterface;



@Service
public class EmployeeGradeService implements IEmployeeGradeService{

	@Autowired
	EmployeeGradeRepository employeeGradeRepository;
	
	@Autowired
	IUserInterface userService;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public double getAvgGradeForEmployee(UUID employeeId) {
		double avgGrade;
		try {
			avgGrade = employeeGradeRepository.getAvgGradeForEmployee(employeeId);
		} catch (Exception e) {
			avgGrade = 0.0;
		}
		
		return avgGrade;
	}
	
	@Override
	public EmployeeForGradeDTO findPatientGradeForEmployee(UUID employeeId) {

		UUID patientId = userService.getLoggedUserId();	
		
		EmployeeGrade employeeGrade= employeeGradeRepository.findPatientGradeForEmployee(employeeId,patientId);
	
		if(employeeGrade==null)
			throw new IllegalArgumentException("This user didn't make grade for this employee");
		
		return new EmployeeForGradeDTO(employeeGrade.getEmployee(),employeeGrade.getGrade());
	}
	
	
	@Override
	public void createEmployeeGrade(EmployeeForGradeRequestDTO employeeForGradeDTO) {

		UUID patientId = userService.getLoggedUserId();	
		Patient patient = patientRepository.findById(patientId).get();
		
		canPatientCreateGradeForEmployee(patient, employeeForGradeDTO);
		
		User employee=userRepository.findById(employeeForGradeDTO.getEmployeeId()).get();
		
		EmployeeGrade employeeGrade= new EmployeeGrade(employee,patient,employeeForGradeDTO.getGrade(),new Date());
		employeeGradeRepository.save(employeeGrade);
		
	}
	
	public void canPatientCreateGradeForEmployee(Patient patient,EmployeeForGradeRequestDTO employeeForGradeDTO) {
		List<Appointment> appointments=
		appointmentRepository.findAllFinishedAppointmentsForPatientinAndEmployee(patient.getId(),employeeForGradeDTO.getEmployeeId());
			
		if(appointments.size()==0)
			throw new IllegalArgumentException("You can't create a grade for employee, because you don't have appointments in the past! ");
		
		
	}
	
	@Override
	public void updateEmployeeGrade(EmployeeForGradeRequestDTO employeeForGradeDTO) {

		UUID patientId = userService.getLoggedUserId();	
		Patient patient = patientRepository.findById(patientId).get();
				
		EmployeeGrade employeeGrade= employeeGradeRepository.findPatientGradeForEmployee(employeeForGradeDTO.getEmployeeId(),patient.getId());
		employeeGrade.setGrade(employeeForGradeDTO.getGrade());
		employeeGrade.setDate(new Date());
		
		employeeGradeRepository.save(employeeGrade);
		
	}
	
}
