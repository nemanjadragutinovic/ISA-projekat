package show.isaBack.serviceInterfaces;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import show.isaBack.DTO.AppointmentDTO.DermatologistAppointmentDTO;
import show.isaBack.DTO.AppointmentDTO.FormAppointmentDTO;
import show.isaBack.DTO.AppointmentDTO.FreeAppointmentPeriodDTO;
import show.isaBack.DTO.AppointmentDTO.ParamsFromAppointmentDTO;
import show.isaBack.DTO.AppointmentDTO.ReservationConsultationDTO;
import show.isaBack.model.Pharmacy;
import show.isaBack.model.User;
import show.isaBack.model.appointment.Appointment;
import show.isaBack.model.appointment.AppointmentType;
import show.isaBack.DTO.AppointmentDTO.AppointmentDTO;
import show.isaBack.DTO.AppointmentDTO.AppointmentReportDTO;
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
	
	
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsAppointmetsSortByPriceAscending(AppointmentType appointmentType) throws Exception;
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsAppointmetsSortByPriceDescending(AppointmentType appointmentType) throws Exception;
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsAppointmetsSortByDateAscending(AppointmentType appointmentType) throws Exception; 
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsAppointmetsSortByDateDescending(AppointmentType appointmentType) throws Exception;
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsAppointmetsSortByDurationAscending(AppointmentType appointmentType) throws Exception;
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsAppointmetsSortByDurationDescending(AppointmentType appointmentType) throws Exception;
	
	
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsConsultationsSortByPriceAscending(AppointmentType appointmentType) throws Exception;
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsConsultationsSortByPriceDescending(AppointmentType appointmentType) throws Exception;
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsConsultationsSortByDateAscending(AppointmentType appointmentType) throws Exception;
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsConsultationsSortByDateDescending(AppointmentType appointmentType) throws Exception;
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsConsultationsSortByDurationAscending(AppointmentType appointmentType) throws Exception;
	public List<UnspecifiedDTO<DermatologistAppointmentDTO>> findAllHistoryPatientsConsultationsSortByDurationDescending(AppointmentType appointmentType) throws Exception;
	
	public boolean canPatientReportPharmacy(UUID pharmacyId);


	public boolean createDermatologistsAppointment(FormAppointmentDTO appointmentDTO);

	List<FreeAppointmentPeriodDTO> generateListFreePeriods(ParamsFromAppointmentDTO paramsFromAppointmentDTO);

	public void refreshPatientsAppointments();


	public List<UnspecifiedDTO<AppointmentDTO>> getCalendarAppointmentsByDermatologist(UUID pharmacyId);

	public List<UnspecifiedDTO<AppointmentDTO>> getCalendarAppointmentsByPharmacist(UUID pharmacyId);

	boolean hasExaminedPatient(UUID patientId);

	public List<UnspecifiedDTO<AppointmentDTO>> getAppointmentsByPatientAsEmpolyee(UUID patientId);




	boolean isFutureAppointmentExists(UUID dermatologistId, UUID phId);



	
}
