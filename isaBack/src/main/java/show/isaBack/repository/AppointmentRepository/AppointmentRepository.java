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
import show.isaBack.model.appointment.AppointmentStatus;
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
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.appointmentStatus = 'FINISHED' "
			+ " AND a.patient.id = ?1 ")
	List<Appointment> findAllFinishedAppointmentsForPatient(UUID patientId);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.appointmentStatus = 'FINISHED' "
			+ " AND a.patient.id = ?1 AND a.pharmacy.id = ?2")
	List<Appointment> findAllFinishedAppointmentsForPatientinPharmacy(UUID patientId, UUID pharmacyId);
	

	@Query(value = "SELECT a FROM Appointment a WHERE a.employee.id = ?1  AND a.pharmacy.id = ?2 AND (CAST(a.startDateTime as date) = CAST(?3 as date))"
			+ " AND (a.appointmentStatus = 'FREE' OR a.appointmentStatus = 'SCHEDULED')")
	List<Appointment> getCreatedAppoitntmentsByDermatologistByDate(UUID dermatologistId, UUID pharmacyId,Date date);

	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1"
			+ " AND a.employee.id = ?2  AND a.appointmentStatus = 'FINISHED' ")
	List<Appointment> findAllFinishedAppointmentsForPatientinAndEmployee(UUID patientId, UUID employeeID);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?2"
			+ " AND a.appointmentStatus = ?4 AND a.pharmacy.id = ?1 AND a.appointmentType = ?3")
	List<Appointment> findAllFinishedAppointmentsByPatiendAndPharmacy(UUID pharmacyId,UUID patientId,AppointmentType appointmentType,AppointmentStatus appointmentStatus);
	
	
	@Query(value = "SELECT a FROM Appointment a WHERE  a.appointmentStatus = 'SCHEDULED' AND a.endDateTime < CURRENT_TIMESTAMP")
	List<Appointment> findAllScheduledAppointmentThatHaveExpired();

	@Query(value = "SELECT a FROM Appointment a WHERE a.employee.id = ?1"
			+ " AND (a.appointmentStatus = 'CREATED' OR a.appointmentStatus = 'SCHEDULED') AND a.appointmentType = 'EXAMINATION' AND a.pharmacy.id=?2")
	List<Appointment> getCalendarDermatologistAppointmentsForPharamacy(UUID dermatologistId, UUID pharmacyId);

	@Query(value = "SELECT a FROM Appointment a WHERE a.employee.id = ?1"
			+ " AND a.appointmentStatus = 'SCHEDULED' AND a.appointmentType = 'CONSULTATION' AND a.pharmacy.id=?2")
	List<Appointment> getCalendarAppointmentsByPharmacist(UUID loggedUserId, UUID pharmacyId);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?2 AND a.employee.id = ?1"
			+ " AND a.appointmentStatus = 'FINISHED'")
	List<Appointment> getFinishedAppointmentsForEmployeeForPatient(UUID employeeId, UUID patientId);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1"
			+ " AND a.appointmentStatus = 'SCHEDULED' AND a.employee.id = ?2 AND a.pharmacy.id = ?3 AND a.appointmentType = 'EXAMINATION' ORDER BY a.startDateTime DESC")
	List<Appointment> getScheduledDermatologistAppointmentsByPatient(UUID patientId, UUID employeeId, UUID pharmacyId);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1"
			+ " AND a.appointmentStatus = 'FINISHED' AND a.appointmentType = 'EXAMINATION' ORDER BY a.startDateTime DESC")
	List<Appointment> getFinishedDermatologistAppointmentsByPatient(UUID patientId, UUID employeeId, UUID pharmacyId);
	
	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1"
			+ " AND a.appointmentStatus = 'SCHEDULED' AND a.employee.id = ?2 AND a.pharmacy.id = ?3 AND a.appointmentType = 'CONSULTATION' ORDER BY a.startDateTime DESC")
	List<Appointment> getScheduledPharmacistAppointmentsByPatient(UUID patientId, UUID employeeId, UUID pharmacyId);

	@Query(value = "SELECT a FROM Appointment a WHERE a.patient.id = ?1"
			+ " AND a.appointmentStatus = 'FINISHED' AND a.appointmentType = 'CONSULTATION' ORDER BY a.startDateTime DESC")
	List<Appointment> getFinishedPharmacistAppointmentsByPatient(UUID patientId, UUID employeeId, UUID pharmacyId);


}
