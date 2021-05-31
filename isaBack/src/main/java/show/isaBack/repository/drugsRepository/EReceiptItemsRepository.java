package show.isaBack.repository.drugsRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import show.isaBack.model.drugs.EReceiptItems;
import show.isaBack.model.drugs.EReceiptItemsId;

public interface EReceiptItemsRepository extends JpaRepository<EReceiptItems, EReceiptItemsId>{

}
