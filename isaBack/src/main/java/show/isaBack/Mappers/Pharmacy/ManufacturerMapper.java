package show.isaBack.Mappers.Pharmacy;

import show.isaBack.DTO.drugDTO.ManufacturerDTO;
import show.isaBack.model.Manufacturer;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public class ManufacturerMapper {

	public static UnspecifiedDTO<ManufacturerDTO> MapManufacturerPersistenceToManufacturerIdentifiableDTO(Manufacturer manufacturer){
		if(manufacturer == null) throw new IllegalArgumentException();
		
		return new UnspecifiedDTO<ManufacturerDTO>(manufacturer.getId(), new ManufacturerDTO(manufacturer.getName()));
	}
}
