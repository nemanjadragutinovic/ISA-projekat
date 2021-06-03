package show.isaBack.repository.userRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import show.isaBack.model.UserCharacteristics.EmployeeGrade;

public interface EmployeeGradeRepository  extends JpaRepository<EmployeeGrade, UUID> {

	@Query(value = "SELECT AVG(e.grade) FROM EmployeeGrade e WHERE e.employee.id = ?1")
	double getAvgGradeForEmployee(UUID employeeId);
	
	@Query(value = "SELECT e FROM EmployeeGrade e WHERE e.employee.id = ?1 AND e.patient.id = ?2")
	EmployeeGrade findPatientGradeForEmployee(UUID employeeId,UUID patientId);
}
