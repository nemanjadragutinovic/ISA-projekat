package show.isaBack.repository.drugsRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import show.isaBack.model.drugs.DrugRequest;

public interface DrugRequestRepository extends JpaRepository<DrugRequest, UUID>{
	@Query(value = "SELECT d FROM DrugRequest d WHERE d.pharmacy.id = ?1")
	List<DrugRequest> findAllForPharmacy(UUID pharmacyId);
}
