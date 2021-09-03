package show.isaBack.serviceInterfaces;

import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import show.isaBack.DTO.AppointmentDTO.IdDTO;
import show.isaBack.DTO.userDTO.AbsenceDTO;
import show.isaBack.DTO.userDTO.AbsenceResponseDTO;
import show.isaBack.DTO.userDTO.RejectAbsenceDTO;
import show.isaBack.DTO.userDTO.RequestAbsenceDTO;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public interface IAbsenceService {
	UUID createAbsence(RequestAbsenceDTO requestAbsenceDTO);
	List<UnspecifiedDTO<AbsenceDTO>> getAbsencesForStaff(UUID staffId);
	List<UnspecifiedDTO<AbsenceResponseDTO>> getAbsencesForPharmacy(UUID pharmacyId);
	boolean approveAbsence(IdDTO pharmacyIdDTO) throws MessagingException;
	void rejectAbsence(RejectAbsenceDTO rejectAbsenceDTO) throws MessagingException;
	public List<UnspecifiedDTO<AbsenceDTO>> getAbsencesAsEmployee();
}
