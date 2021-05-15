package show.isaBack.interfaceRepository.drugRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import show.isaBack.model.Manufacturer;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, UUID>{
	Manufacturer findByName ( String name );
}
