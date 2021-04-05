package show.isaBack.repository.userRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import show.isaBack.model.Patient;
import show.isaBack.model.User;
public interface PatientRepository extends JpaRepository<Patient, UUID> {
	
	

}
