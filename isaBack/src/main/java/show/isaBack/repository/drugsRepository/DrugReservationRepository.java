package show.isaBack.repository.drugsRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import show.isaBack.model.drugs.DrugReservation;

public interface DrugReservationRepository extends JpaRepository<DrugReservation, UUID> {

	@Query(value = "SELECT d FROM DrugReservation d WHERE d.patient.id = ?1 AND d.endDate > CURRENT_TIMESTAMP AND d.drugReservationStatus = 'ACTIVE' ")
	List<DrugReservation> findAllFutureDrugsReservationForPatients(UUID patientId);
	
	@Query(value = "SELECT d FROM DrugReservation d WHERE d.patient.id = ?1  AND d.drugReservationStatus = 'PROCESSED' ")
	List<DrugReservation> findAllhistoryDrugsReservationForPatients(UUID patientId);
	
}
