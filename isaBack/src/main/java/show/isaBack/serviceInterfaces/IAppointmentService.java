package show.isaBack.serviceInterfaces;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import show.isaBack.DTO.AppointmentDTO.DermatologistAppointmentDTO;
import show.isaBack.DTO.AppointmentDTO.ReservationConsultationDTO;
import show.isaBack.model.Pharmacy;
import show.isaBack.model.User;
import show.isaBack.model.appointment.AppointmentType;

import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public interface IAppointmentService extends IService<DermatologistAppointmentDTO, UnspecifiedDTO<DermatologistAppointmentDTO>> {

	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllFreeAppointmentsForPharmacyAndForAppointmentType(UUID pharmacyId,AppointmentType appointmentType);         
	
	public void reserveDermatologistAppointment(UUID uuid);
	
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> sortByPriceAscendingAllFreeDermatologistAppointments(UUID pharmacyId,AppointmentType appointmentType); 
	
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> sortByPriceDescendingAllFreeDermatologistAppointments(UUID pharmacyId,AppointmentType appointmentType); 
	
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> sortByDermatologistGradeAscendingAllFreeDermatologistAppointments(UUID pharmacyId,AppointmentType appointmentType);
	
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> sortByDermatologistGradeDescendingAllFreeDermatologistAppointments(UUID pharmacyId,AppointmentType appointmentType);
	
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllFuturePatientsAppointmets(AppointmentType appointmentType);
	
	public void cancelAppointment(UUID appointmentId);
	
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsAppointmets(AppointmentType appointmentType);
	
	public List<Pharmacy> findAllPharmaciesForAppointmentTypeAndForDateRange(Date startDate, Date endDate);
	
	public List<User> fidnAllFreePharmacistsForSelectedPharmacyInDataRange(Date startDate, Date endDate, UUID pharmacyId);
	
	public void reserveConsulationBySelectedPharmacist(ReservationConsultationDTO reservationRequestDTO);
	
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsConsultations(AppointmentType appointmentType);
	
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllFuturePatientsConsultations(AppointmentType appointmentType);
}
