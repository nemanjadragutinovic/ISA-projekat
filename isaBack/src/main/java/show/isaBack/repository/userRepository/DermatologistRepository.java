package show.isaBack.repository.userRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import show.isaBack.model.Dermatologist;

public interface DermatologistRepository extends JpaRepository<Dermatologist, UUID> {
	
	

}