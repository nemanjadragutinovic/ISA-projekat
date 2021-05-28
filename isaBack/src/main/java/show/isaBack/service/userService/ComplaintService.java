package show.isaBack.service.userService;

import java.io.NotActiveException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import show.isaBack.DTO.userDTO.ComplaintStaffDTO;
import show.isaBack.model.ComplaintStaff;
import show.isaBack.model.Patient;
import show.isaBack.model.User;
import show.isaBack.repository.AppointmentRepository.AppointmentRepository;
import show.isaBack.repository.userRepository.ComplaintRepository;
import show.isaBack.repository.userRepository.DermatologistRepository;
import show.isaBack.repository.userRepository.PatientRepository;
import show.isaBack.repository.userRepository.UserRepository;
import show.isaBack.serviceInterfaces.IComplaintService;

@Service
public class ComplaintService implements IComplaintService{
	
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DermatologistRepository dermatologistRepository;
	
	@Autowired
	private ComplaintRepository complaintRepository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Override
	public UUID create(ComplaintStaffDTO entityDTO){
		
		
		UUID patientId = userService.getLoggedUserId();
		
		Patient patient = patientRepository.findById(patientId).get();
		
		System.out.println(patient.getName());
		
			
		User user = userRepository.findById(entityDTO.getStaffId()).get();
		
		String profession ="";
		if(dermatologistRepository.existsById(user.getId())) {
			profession="dermatologist";
		}else {
			profession="pharmacist";
		}
		
		ComplaintStaff complaintStaff = new ComplaintStaff(user,patient, entityDTO.getText(), user.getName(),user.getSurname(), profession, patient.getEmail());
		complaintRepository.save(complaintStaff);
		
		return complaintStaff.getId();
		
	}
	


		

}
