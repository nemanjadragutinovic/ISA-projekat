package show.isaBack.repository.userRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import show.isaBack.model.Patient;
import show.isaBack.model.User;
public interface PatientRepository extends JpaRepository<Patient, UUID> {
	
	@Query(value = "SELECT p from Patient p WHERE LOWER(p.name) LIKE %?1% AND LOWER(p.surname) LIKE %?2%")
    List<User> findPatientByNameAndSurname(String name, String surname);

}
