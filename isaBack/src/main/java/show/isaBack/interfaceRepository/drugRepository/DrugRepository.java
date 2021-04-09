package show.isaBack.interfaceRepository.drugRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import show.isaBack.model.Drug;



public interface DrugRepository extends JpaRepository<Drug, UUID> {
	
	@Query(value = "SELECT dr from Drug dr WHERE LOWER(dr.name) LIKE  %?1% AND LOWER(dr.producerName) LIKE %?2% AND LOWER(dr.fabricCode) LIKE %?3%  ")
	List<Drug> getAllSearchedDrugs(String name, String producerName,String fabricCode);

}
