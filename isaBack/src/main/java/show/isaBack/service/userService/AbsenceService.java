package show.isaBack.service.userService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import show.isaBack.DTO.AppointmentDTO.IdDTO;
import show.isaBack.DTO.userDTO.AbsenceDTO;
import show.isaBack.DTO.userDTO.AbsenceResponseDTO;
import show.isaBack.DTO.userDTO.RejectAbsenceDTO;
import show.isaBack.DTO.userDTO.RequestAbsenceDTO;
import show.isaBack.Mappers.Appointmets.AbsenceMapper;
import show.isaBack.emailService.EmailService;
import show.isaBack.model.FreeDayStatus;
import show.isaBack.model.FreeDays;
import show.isaBack.model.Pharmacy;
import show.isaBack.model.User;
import show.isaBack.model.UserCharacteristics.UserType;
import show.isaBack.model.appointment.Appointment;
import show.isaBack.repository.AppointmentRepository.AppointmentRepository;
import show.isaBack.repository.userRepository.AbsenceRepository;
import show.isaBack.repository.userRepository.PharmacistRepository;
import show.isaBack.repository.userRepository.UserRepository;
import show.isaBack.serviceInterfaces.IAbsenceService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

@Service
public class AbsenceService implements IAbsenceService{
	@Autowired
	private AbsenceRepository absenceRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PharmacistRepository pharmacistRepository;
	@Autowired
	private UserService userService;
	@Autowired 
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private EmailService emailService;
	@Override
	public UUID createAbsence(RequestAbsenceDTO requestAbsenceDTO) {
		UUID userId = userService.getLoggedUserId();
		User user =userRepository.getOne(userId);	
		Pharmacy pharmacy = null;
		if(user.getUserType() == UserType.PHARMACIST)
			pharmacy = pharmacistRepository.getOne(userId).getPharmacy();
		else if (user.getUserType() == UserType.DERMATOLOGIST)
			pharmacy = userService.getPharmacyForLoggedDermatologist();
		FreeDays freeDays = new FreeDays(user,pharmacy,getDateWithoutTime(requestAbsenceDTO.getStartDate()),getDateWithoutTime(requestAbsenceDTO.getEndDate()));
		absenceRepository.save(freeDays);
		return freeDays.getId();
	}

	private Date getDateWithoutTime(Date date) {
		return new Date(date.getYear(), date.getMonth(), date.getDate(),0,0,0);
	}

	
	@Override
	public List<UnspecifiedDTO<AbsenceDTO>> getAbsencesForStaff(UUID staffId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UnspecifiedDTO<AbsenceResponseDTO>> getAbsencesForPharmacy(UUID pharmacyId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<UnspecifiedDTO<AbsenceDTO>> getAbsencesAsEmployee() {
		UUID userId = userService.getLoggedUserId();
		List<FreeDays> absences = absenceRepository.findAllAbsencesByStaff(userId);
		return AbsenceMapper.MapAbsencePersistenceListToAbsenceIdentifiableDTOList(absences);
	}
	
	@Override
	
	public  List<UnspecifiedDTO<AbsenceResponseDTO>> getAbsencesPharmacy(UUID pharmacyId) {
		List<FreeDays> listOfAbsences = absenceRepository.findAbsenceWaiting(pharmacyId);
		List<UnspecifiedDTO<AbsenceResponseDTO>> retVal = new ArrayList<UnspecifiedDTO<AbsenceResponseDTO>>();
		
		for(FreeDays fd:listOfAbsences) {
			retVal.add(new UnspecifiedDTO<AbsenceResponseDTO>(fd.getId(),new AbsenceResponseDTO("Dr " + fd.getUser().getName()+" "+ fd.getUser().getSurname(),fd.getStartDate(),fd.getEndDate())));
		}
		
		return retVal;
	}
	
	@Override
	public boolean acceptAbsence(IdDTO absenceIdDTO) throws MessagingException {
		FreeDays absence =absenceRepository.getOne(absenceIdDTO.getId());
		
		if(!hasEmployeeAppointment(absence)) {
			absence.setStatus(FreeDayStatus.ACCEPTED);
			absenceRepository.save(absence);
			emailService.sendMailForApprovedAbsence(absence);
			return true;
		}
		
		return false;
	}
	
	
        private boolean hasEmployeeAppointment(FreeDays absence) {
		
		List<Appointment> appointments = appointmentRepository.findAllAppointments(absence.getStartDate(), absence.getEndDate(), absence.getPharmacy().getId(), absence.getUser().getId());
		
		if(appointments.size()>0)
			return true;
		
		return false;
	}
	
	@Override
	public void rejectAbsence(RejectAbsenceDTO rejectAbsenceDTO) throws MessagingException {
		FreeDays absence =absenceRepository.getOne(rejectAbsenceDTO.getId());
		
		absence.setStatus(FreeDayStatus.REJECTED);
		absence.setRejectReason(rejectAbsenceDTO.getReason());
		absenceRepository.save(absence);
		
		emailService.sendMailForRejectAbsence(absence);
	}

}
