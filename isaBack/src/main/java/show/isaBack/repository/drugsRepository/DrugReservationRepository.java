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
	

	@Query(value = "SELECT d FROM DrugReservation d WHERE d.patient.id = ?1 AND d.drugInstance.id = ?2  AND d.drugReservationStatus = 'PROCESSED'  ")
	List<DrugReservation> findAllProccessedDrugsForPatientAndDrugId(UUID patientId, UUID drugId);
	
	
	@Query(value = "SELECT d FROM DrugReservation d WHERE d.patient.id = ?1 AND d.pharmacy.id = ?2 AND d.drugReservationStatus = 'PROCESSED'  ")
	List<DrugReservation> findAllProccessedDrugsForPatientAndPharmacyId(UUID patientId, UUID pharmacyId);

	@Query(value = "SELECT d FROM DrugReservation d WHERE d.patient.id = ?1  AND d.pharmacy.id = ?2 ")
	List<DrugReservation> findAllhistoryDrugsReservationwithPatientAndPharmacy(UUID patientId,UUID pharmacyId);
	
	@Query(value = "SELECT d FROM DrugReservation d WHERE d.patient.id = ?1 AND d.endDate < CURRENT_TIMESTAMP AND d.drugReservationStatus = 'ACTIVE' ")
	List<DrugReservation> getAllPatientsActiveDrugReservationThatHaveExpired(UUID patientId);
	
	@Query(value = "SELECT d FROM DrugReservation d WHERE  d.endDate < CURRENT_TIMESTAMP AND d.drugReservationStatus = 'ACTIVE' ")
	List<DrugReservation> getAllActiveDrugReservationThatHaveExpired();
	
	@Query(value = "SELECT d FROM DrugReservation d WHERE d.drugInstance.id = ?1 AND d.pharmacy.id = ?2 AND d.endDate > CURRENT_TIMESTAMP AND d.drugReservationStatus = 'ACTIVE' ")
	List<DrugReservation> findAllFutureDrugsReservation(UUID drugId,UUID pharmacyId);

	@Query(value = "SELECT d FROM DrugReservation d WHERE d.id = ?1 AND d.pharmacy.id = ?2 AND d.drugReservationStatus = 'ACTIVE'")
	List<DrugReservation> findByStatusAndIdAndPharmacy(UUID reservationId, UUID pharmacyId);
	
}
