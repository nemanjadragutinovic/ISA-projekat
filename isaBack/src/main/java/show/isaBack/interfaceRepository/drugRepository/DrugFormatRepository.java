package show.isaBack.interfaceRepository.drugRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import show.isaBack.model.drugs.DrugFormatId;

public interface DrugFormatRepository  extends JpaRepository<DrugFormatId, UUID>{
	DrugFormatId findByType ( String type );
}
