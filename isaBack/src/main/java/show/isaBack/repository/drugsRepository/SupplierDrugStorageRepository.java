package show.isaBack.repository.drugsRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import show.isaBack.model.drugs.SupplierDrugStorage;
import show.isaBack.model.drugs.SupplierDrugStorageId;

public interface SupplierDrugStorageRepository extends JpaRepository<SupplierDrugStorage, SupplierDrugStorageId>{

}
