package show.isaBack.repository.drugsRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import show.isaBack.model.drugs.DrugPriceList;

public interface DrugPriceListRepository extends JpaRepository<DrugPriceList, UUID>{
	
		@Query(value = "SELECT d.price FROM DrugPriceList d WHERE d.drugInstance.id = ?1 AND d.pharmacy.id = ?2 AND DATE(d.dateFrom) <= CURRENT_DATE AND DATE(d.dateTo) >= CURRENT_DATE")
		Double getCurrentPriceForDrugInPharmacy(UUID drugId, UUID pharmacyId);

		@Query(value = "SELECT d FROM DrugPriceList d WHERE d.drugInstance.id = ?1 AND d.pharmacy.id = ?2 "
				 + "AND DATE(d.dateFrom) < DATE(?4) AND DATE(?3) < DATE(d.dateTo)")
		DrugPriceList findDrugPriceForPeriod(UUID drugInstanceId, UUID pharmacyId, Date dateFrom, Date dateTo);
		
		@Query(value = "SELECT d FROM DrugPriceList d WHERE d.drugInstance.id = ?1 AND d.pharmacy.id = ?2")
		List<DrugPriceList> findDrugPricePhId(UUID drugInstanceId, UUID pharmacyId);
		

		@Query(value = "SELECT d.price FROM DrugPriceList d WHERE d.drugInstance.id = ?1 AND d.pharmacy.id = ?2 "
				 + "AND DATE(d.dateFrom) <= CURRENT_DATE AND DATE(d.dateTo) >= CURRENT_DATE")
		Integer findCurrentDrugPrice(UUID drugInstanceId, UUID pharmacyId);
	

		@Query(value = "SELECT d FROM DrugPriceList d WHERE d.drugInstance.id = ?1 AND d.pharmacy.id = ?2 AND DATE(d.dateFrom) <= CURRENT_DATE AND DATE(d.dateTo) >= CURRENT_DATE")
		DrugPriceList getCDrugPriceInPharmacy(UUID drugId, UUID pharmacyId);

}
