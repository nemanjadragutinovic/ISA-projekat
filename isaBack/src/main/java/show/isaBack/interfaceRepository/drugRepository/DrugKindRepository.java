package show.isaBack.interfaceRepository.drugRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import show.isaBack.model.drugs.DrugKindId;

public interface DrugKindRepository  extends JpaRepository<DrugKindId, UUID>{
	DrugKindId findByType ( String type );
}
