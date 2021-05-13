package show.isaBack.repository.userRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import show.isaBack.model.Pharmacy;

public interface PharmacyRepository extends JpaRepository<Pharmacy, UUID>{

	
}
