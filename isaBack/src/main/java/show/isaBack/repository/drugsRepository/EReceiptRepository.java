package show.isaBack.repository.drugsRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import show.isaBack.model.appointment.Appointment;
import show.isaBack.model.drugs.EReceipt;
import show.isaBack.model.drugs.EReceiptStatus;

public interface EReceiptRepository extends JpaRepository<EReceipt, UUID> {


	@Query(value = "SELECT e FROM EReceipt e WHERE e.patient.id = ?1 AND e.pharmacy.id = ?2")
	List<EReceipt> findAllEReceiptsWithPatientAndPharmacy(UUID patientId, UUID pharmacyId);

	@Query(value = "SELECT e FROM EReceipt e where e.patient.id = ?1")
	List<EReceipt> findAllEReceiptsForPatient(UUID patientId);
	
	@Query(value = "SELECT e FROM EReceipt e where e.patient.id = ?1 ORDER BY e.creationDate ASC")
	List<EReceipt> findAllEReceiptsForPatientSortByDateAscending(UUID patientId);
	
	
	@Query(value = "SELECT e FROM EReceipt e where e.patient.id = ?1 ORDER BY e.creationDate DESC")
	List<EReceipt> findAllEReceiptsForPatientSortByDateDescending(UUID patientId);
	
	@Query(value = "SELECT e FROM EReceipt e where e.patient.id = ?1 AND e.status = ?2 ")
	List<EReceipt> findAllEReceiptsForPatientWithStatus(UUID patientId, EReceiptStatus searchStatus);
	
	@Query(value = "SELECT e FROM EReceipt e where e.patient.id = ?1 AND e.status = ?2 ORDER BY e.creationDate DESC")
	List<EReceipt> findAllEReceiptsForPatientSortByDateDescendingWitStatus(UUID patientId,EReceiptStatus searchStatus);
	
	@Query(value = "SELECT e FROM EReceipt e where e.patient.id = ?1 AND e.status = ?2 ORDER BY e.creationDate ASC")
	List<EReceipt> findAllEReceiptsForPatientSortByDateAscescendingWithStatus(UUID patientId,EReceiptStatus searchStatus);
	
	@Query(value = "SELECT e FROM EReceipt e WHERE e.pharmacy.id = ?1 AND e.patient.id = ?2")
	List<EReceipt> findAllEReceiptsByPatiendAndPharmacy(UUID pharmacyId ,UUID patientId);

}
