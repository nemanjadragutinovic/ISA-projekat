package show.isaBack.serviceInterfaces;

import java.util.List;
import java.util.UUID;

import show.isaBack.DTO.AppointmentDTO.DermatologistAppointmentDTO;
import show.isaBack.DTO.AppointmentDTO.IdDTO;
import show.isaBack.model.appointment.AppointmentType;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public interface IAppointmentService extends IService<DermatologistAppointmentDTO, UnspecifiedDTO<DermatologistAppointmentDTO>> {

	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsForPharmacyAndForAppointmentType(UUID pharmacyId,AppointmentType appointmentType);         
	
	public void reserveDermatologistAppointment(UUID uuid);
}
