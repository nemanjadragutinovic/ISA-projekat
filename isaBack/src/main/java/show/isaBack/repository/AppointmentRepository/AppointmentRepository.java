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
			+ " AND a.appointmentStatus = 'FREE' AND a.appointmentType = ?2")
	List<Appointment> findAllFreeAppointmentsForPharmacyAndForAppointmentType(UUID pharmacyId, AppointmentType appointmentType);          

	
	@Query(value = "SELECT a FROM Appointment a WHERE NOT (a.startDateTime >= ?3 OR a.endDateTime <= ?2)"
			+ " AND a.appointmentStatus = 'SCHEDULED' AND a.patient.id = ?1")
	List<Appointment> findAllAppointmentsInGivenDateTimeForGivenTypeForPatient(UUID patientId, Date startDateTime, Date endDateTime);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
