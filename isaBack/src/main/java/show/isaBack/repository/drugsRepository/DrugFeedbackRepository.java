package show.isaBack.repository.drugsRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import show.isaBack.model.UserCharacteristics.EmployeeGrade;
import show.isaBack.model.drugs.DrugFeedback;


public interface DrugFeedbackRepository extends JpaRepository<DrugFeedback, UUID> {

	@Query(value = "SELECT d FROM DrugFeedback d WHERE d.patient.id = ?1 AND d.drug.id = ?2")
	DrugFeedback findByPatientAndDrug(UUID patientId, UUID drugId);
	
	@Query(value = "SELECT AVG(s.grade) FROM DrugFeedback s WHERE s.drug.id = ?1")
	Double findAvgGradeForDrug(UUID drugId);
}
