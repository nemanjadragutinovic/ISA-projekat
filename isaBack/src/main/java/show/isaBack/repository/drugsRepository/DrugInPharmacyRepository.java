package show.isaBack.repository.drugsRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import show.isaBack.model.drugs.DrugInPharmacy;




public interface DrugInPharmacyRepository extends JpaRepository<DrugInPharmacy, UUID>{
	
	
	@Query(value = "SELECT d FROM DrugInPharmacy d WHERE d.drugInstance.id = ?1 ")
	List<DrugInPharmacy> getAllPharmaciesForDrug(UUID drugId);
	
	@Query(value = "SELECT d.count FROM DrugInPharmacy d WHERE d.drugInstance.id = ?1 AND d.pharmacy.id = ?2")
	int getCountForDrugInpharmacy(UUID drugId, UUID pharmacyId);
	
	@Query(value = "SELECT d FROM DrugInPharmacy d WHERE d.drugInstance.id = ?1 AND d.pharmacy.id = ?2")
	DrugInPharmacy getDrugInPharmacy(UUID drugId, UUID pharmacyId);

	@Query(value = "SELECT d FROM DrugInPharmacy d WHERE d.drugInstance.id = ?1 AND d.pharmacy.id = ?2")
	DrugInPharmacy findByDrugIdAndPharmacyId(UUID drugId, UUID pharmacyId);

	
	
}
