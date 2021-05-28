package show.isaBack.serviceInterfaces;

import java.util.List;

import show.isaBack.DTO.drugDTO.DrugFormatIdDTO;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public interface IDrugFormatService  extends IService<DrugFormatIdDTO, UnspecifiedDTO<DrugFormatIdDTO>>{
	
	public List<UnspecifiedDTO<DrugFormatIdDTO>> findAllDrugKinds();

}
