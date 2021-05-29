package show.isaBack.service.userService;

import java.io.NotActiveException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import show.isaBack.DTO.pharmacyDTO.PharmacyDTO;
import show.isaBack.DTO.userDTO.ComplaintPharmacyDTO;
import show.isaBack.DTO.userDTO.ComplaintStaffDTO;
import show.isaBack.DTO.userDTO.PharmacistForAppointmentPharmacyGadeDTO;
import show.isaBack.model.ComplaintPharmacy;
import show.isaBack.model.ComplaintStaff;
import show.isaBack.model.Patient;
import show.isaBack.model.Pharmacy;
import show.isaBack.model.User;
import show.isaBack.repository.AppointmentRepository.AppointmentRepository;
import show.isaBack.repository.pharmacyRepository.PharmacyRepository;
import show.isaBack.repository.userRepository.ComplaintPharmacyRepository;
import show.isaBack.repository.userRepository.ComplaintRepository;
import show.isaBack.repository.userRepository.DermatologistRepository;
import show.isaBack.repository.userRepository.PatientRepository;
import show.isaBack.repository.userRepository.UserRepository;
import show.isaBack.serviceInterfaces.IComplaintService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

@Service
public class ComplaintService implements IComplaintService{
	
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DermatologistRepository dermatologistRepository;
	
	@Autowired
	private ComplaintRepository complaintRepository;
	
	@Autowired
	private ComplaintPharmacyRepository complaintPharmacyRepository;
	
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
	
	@Override
	public UUID createPharmacyComplaint(ComplaintPharmacyDTO entityDTO){
		
		UUID patientId = userService.getLoggedUserId();
		
		Patient patient = patientRepository.findById(patientId).get();
		
		System.out.println(patient.getName());
		
		
		
		Pharmacy pharmacy = pharmacyRepository.findById(entityDTO.getPharmacyId()).get();
		
		ComplaintPharmacy complaintPharmacy = new ComplaintPharmacy(pharmacy,patient,entityDTO.getText(),entityDTO.getName());
		
		complaintPharmacyRepository.save(complaintPharmacy);
		
		return complaintPharmacy.getId();
		
	}
	@Override
	public List<UnspecifiedDTO<ComplaintStaffDTO>> findAllStaffComplaints(){
		
		List<UnspecifiedDTO<ComplaintStaffDTO>> staffList = new ArrayList<UnspecifiedDTO<ComplaintStaffDTO>>();
		for (ComplaintStaff staff : complaintRepository.findAll()) {
			ComplaintStaffDTO staffDTO = new ComplaintStaffDTO(staff.getId(), staff.getDate(), staff.getText(), staff.getStaffName(),staff.getStaffSurname(), staff.getProfession(),staff.getReply(), staff.getEmail());
			staffList.add(new UnspecifiedDTO<ComplaintStaffDTO>(staff.getId(),staffDTO));
		}
		
		return staffList;
	}
	


		

}