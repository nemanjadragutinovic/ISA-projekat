package show.isaBack.interfaceService.drugInterfaceService;

import java.util.List;

import show.isaBack.DTO.drugDTO.DrugDTO;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;



public interface IDrugService {
	
	List<UnspecifiedDTO<DrugDTO>> getAllDrugs();

}
