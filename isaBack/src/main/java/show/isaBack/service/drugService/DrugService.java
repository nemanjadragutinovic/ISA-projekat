package show.isaBack.service.drugService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import show.isaBack.DTO.drugDTO.DrugDTO;
import show.isaBack.interfaceRepository.drugInterfaceRepository.DrugRepository;
import show.isaBack.interfaceService.drugInterfaceService.IDrugService;
import show.isaBack.model.Drug;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

@Service
public class DrugService implements IDrugService{


	@Autowired
	private DrugRepository drugRepository;
	
	@Override
	public List<UnspecifiedDTO<DrugDTO>> getAllDrugs() {
		 				
		List<Drug> drugs = drugRepository.findAll();
		List<UnspecifiedDTO<DrugDTO>> drugsDTOList = new ArrayList<UnspecifiedDTO<DrugDTO>>();
				
		
		for (Drug currentDrug : drugs) 
		{
				DrugDTO drugDTO= new DrugDTO(currentDrug);				
				drugsDTOList.add(new UnspecifiedDTO<DrugDTO>(currentDrug.getId(),drugDTO));
		}
		
		
		return drugsDTOList;
	}

}
