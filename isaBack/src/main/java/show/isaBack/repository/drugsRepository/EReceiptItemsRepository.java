package show.isaBack.repository.drugsRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import show.isaBack.model.DrugInstance;
import show.isaBack.model.drugs.EReceiptItems;
import show.isaBack.model.drugs.EReceiptItemsId;

public interface EReceiptItemsRepository extends JpaRepository<EReceiptItems, EReceiptItemsId>{
	
	@Query(value = "SELECT e FROM EReceiptItems e where e.eReceiptItemsId.eReceipt.id = ?1")
	List<EReceiptItems> findAllByEReceiptId(UUID id);	
	
	@Query(value = "SELECT DISTINCT e.eReceiptItemsId.drugInstance FROM EReceiptItems e where e.eReceiptItemsId.eReceipt.patient.id = ?1 AND e.eReceiptItemsId.eReceipt.status = 'PROCESSED'") 
	List<DrugInstance> findAllProccessedDistinctDrugsByPatient(UUID patientId);
	
	
	
	@Query(value = "SELECT e FROM EReceiptItems e where e.eReceiptItemsId.eReceipt.patient.id = ?1 AND"
			+ " e.eReceiptItemsId.eReceipt.status = 'PROCESSED' AND e.eReceiptItemsId.drugInstance.id = ?2")
	List<EReceiptItems> findAllEreciptsForDrugByPatient(UUID patientId,UUID drugId );
	
	
	@Query(value = "SELECT e FROM EReceiptItems e where e.eReceiptItemsId.eReceipt.patient.id = ?2 AND e.eReceiptItemsId.drugInstance.id = ?1")
	List<EReceiptItems> findAllEReceiptsByPatiendAndDrug(UUID drugId,UUID patientId);
	
}
