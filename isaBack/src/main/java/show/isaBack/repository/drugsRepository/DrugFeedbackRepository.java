package show.isaBack.repository.drugsRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import show.isaBack.model.drugs.DrugFeedback;
import show.isaBack.model.drugs.DrugFeedbackId;

public interface DrugFeedbackRepository extends JpaRepository<DrugFeedback, DrugFeedbackId>{

	@Query(value = "SELECT d FROM DrugFeedback d WHERE d.drugFeedbackId.patient.id = ?1 AND d.drugFeedbackId.drug.id = ?2")
	DrugFeedback findByPatientAndDrug(UUID patientId, UUID drugId);
	
	@Query(value = "SELECT AVG(s.grade) FROM DrugFeedback s WHERE s.drugFeedbackId.drug.id = ?1")
	Double findAvgGradeForDrug(UUID drugId);
}
