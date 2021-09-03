package show.isaBack.repository.userRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import show.isaBack.model.Dermatologist;
import show.isaBack.model.Pharmacist;
import show.isaBack.model.Pharmacy;



public interface PharmacistRepository extends JpaRepository<Pharmacist, UUID>{

	@Query(value = "SELECT p.pharmacy FROM Pharmacist p WHERE p.id = ?1")
	Pharmacy findPharmacyWhereWorksPharmacist(UUID pharmacistId);
	
	@Query(value = "SELECT p from Pharmacist p WHERE LOWER(p.name) LIKE %?1% AND LOWER(p.surname) LIKE %?2%")
	List<Pharmacist> findByNameAndSurname(String name, String surname);
	
	
}
