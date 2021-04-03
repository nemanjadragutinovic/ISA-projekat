package show.isaBack.interfaceRepository.drugInterfaceRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import show.isaBack.model.Drug;



public interface DrugRepository extends JpaRepository<Drug, UUID> {
	
	

}
