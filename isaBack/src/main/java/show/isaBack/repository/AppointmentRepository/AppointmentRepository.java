package show.isaBack.repository.AppointmentRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import show.isaBack.model.appointment.Appointment;
import show.isaBack.model.appointment.AppointmentType;



public interface AppointmentRepository extends PagingAndSortingRepository<Appointment, UUID> {

	@Query(value = "SELECT a FROM Appointment a WHERE a.pharmacy.id = ?1 AND a.startDateTime > CURRENT_TIMESTAMP"
			+ "  AND a.appointmentType = ?2 AND a.appointmentStatus = 'FREE' ")
	List<Appointment> findAllFreeAppointmentsForPharmacyAndForAppointmentType(UUID pharmacyId, AppointmentType appointmentType);          

	
	@Query(value = "SELECT a FROM Appointment a WHERE NOT (a.startDateTime >= ?3 OR a.endDateTime <= ?2)"
			+ " AND a.appointmentStatus = 'SCHEDULED' AND a.patient.id = ?1")
	List<Appointment> findAllAppointmentsInGivenDateTimeForGivenTypeForPatient(UUID patientId, Date startDateTime, Date endDateTime);
	
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.pharmacy.id = ?1 AND a.startDateTime > CURRENT_TIMESTAMP"
			+ " AND a.appointmentStatus = 'FREE'  AND a.appointmentType = ?2 ORDER BY a.price ASC")
	List<Appointment> sortByPriceAscendingAllFreeDermatologistAppointments(UUID pharmacyId, AppointmentType appointmentType);
	
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.pharmacy.id = ?1 AND a.startDateTime > CURRENT_TIMESTAMP"
			+ " AND a.appointmentStatus = 'FREE'  AND a.appointmentType = ?2 ORDER BY a.price DESC")
	List<Appointment> sortByPriceDescendingAllFreeDermatologistAppointments(UUID pharmacyId, AppointmentType appointmentType);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1 AND a.startDateTime > CURRENT_TIMESTAMP"
			+ "  AND a.appointmentType = ?2 AND a.appointmentStatus = 'SCHEDULED' ")
	List<Appointment> findAllFuturePatientsAppointmets(UUID userId, AppointmentType appointmentType);  
	
	
	@Query(value = "SELECT a FROM Appointment a WHERE (a.patient.id = ?1 AND a.appointmentStatus = 'FINISHED'"   
			+ "  AND a.appointmentType = ?2) OR ( a.patient.id = ?1  AND a.appointmentType = ?2 AND a.startDateTime < CURRENT_TIMESTAMP) ")
	List<Appointment> findAllHistoryPatientsAppointmets(UUID userId, AppointmentType appointmentType); 
	
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.appointmentType = 'CONSULTATION' AND a.appointmentStatus = 'SCHEDULED' "
			+ " AND NOT (a.startDateTime >= ?2 OR a.endDateTime <= ?1) ")
	List<Appointment> findAllBusyConsultationsInDataRange(Date startDate, Date endDate);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.appointmentType = 'CONSULTATION' AND a.appointmentStatus = 'SCHEDULED' "
			+ " AND a.pharmacy.id = ?3 AND NOT (a.startDateTime >= ?2 OR a.endDateTime <= ?1) ")
	List<Appointment> findAllBusyConsultationsInDataRangeForPharmacy(Date startDate, Date endDate,UUID pharmacyId);
	
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.appointmentType = 'CONSULTATION' AND a.appointmentStatus = 'SCHEDULED' "
			+ " AND a.employee.id = ?3 AND NOT (a.startDateTime >= ?2 OR a.endDateTime <= ?1) ")
	List<Appointment> findAllBusyConsultationsInDataRangeForPharmacist(Date startDate, Date endDate,UUID pharmacistId);
	
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.appointmentStatus = 'SCHEDULED' "
			+ " AND a.patient.id = ?3 AND NOT (a.startDateTime >= ?2 OR a.endDateTime <= ?1) ")
	List<Appointment> findAllSheduledAppointmentsForPatientsInDataRange(Date startDate, Date endDate,UUID patientId);
	
	
}
