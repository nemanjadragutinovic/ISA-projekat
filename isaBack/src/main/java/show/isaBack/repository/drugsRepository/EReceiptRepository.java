package show.isaBack.repository.drugsRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import show.isaBack.model.drugs.EReceipt;

public interface EReceiptRepository extends JpaRepository<EReceipt, UUID> {

}
