package show.isaBack.repository.userRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import show.isaBack.model.Dermatologist;
import show.isaBack.model.Pharmacy;

public interface DermatologistRepository extends JpaRepository<Dermatologist, UUID> {
	
	//@Query(value = "SELECT p.pharmacy FROM Dermatologist p WHERE p.id = ?1")
	//Pharmacy findPharmacyWhereWorksDermatologist(UUID dermatologistId);

}