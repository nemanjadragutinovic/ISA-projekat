package show.isaBack.repository.drugsRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import show.isaBack.model.appointment.Appointment;
import show.isaBack.model.drugs.EReceipt;

public interface EReceiptRepository extends JpaRepository<EReceipt, UUID> {

	@Query(value = "SELECT e FROM EReceipt e WHERE e.patient.id = ?1 AND e.pharmacy.id = ?2")
	List<EReceipt> findAllEReceiptsWithPatientAndPharmacy(UUID patientId, UUID pharmacyId);
}
