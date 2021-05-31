package show.isaBack.repository.drugsRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import show.isaBack.model.drugs.EReceiptItems;
import show.isaBack.model.drugs.EReceiptItemsId;

public interface EReceiptItemsRepository extends JpaRepository<EReceiptItems, EReceiptItemsId>{
	
	@Query(value = "SELECT e FROM EReceiptItems e where e.eReceiptItemsId.eReceipt.id = ?1")
	List<EReceiptItems> findAllByEReceiptId(UUID id);	

}
