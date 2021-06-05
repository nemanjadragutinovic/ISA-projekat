package show.isaBack.repository.Pharmacy;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import show.isaBack.model.PharmacyGrade;



public interface PharmacyGradeRepository extends JpaRepository<PharmacyGrade, UUID> {
	
	
	@Query(value = "SELECT AVG(p.grade) FROM PharmacyGrade p WHERE p.pharmacy.id = ?1")
	double getAvgGradeForPharmacy(UUID pharmacyId);

	@Query(value = "SELECT p FROM PharmacyGrade p WHERE p.patient.id = ?1 AND p.pharmacy.id = ?2 ")
	PharmacyGrade findByPatientAndPharmacy(UUID patientId, UUID pharmacyId);
	
}
