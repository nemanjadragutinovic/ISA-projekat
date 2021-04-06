package show.isaBack.service.drugService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import show.isaBack.DTO.drugDTO.DrugDTO;
import show.isaBack.interfaceRepository.drugInterfaceRepository.DrugRepository;
import show.isaBack.model.Drug;
import show.isaBack.serviceInterfaces.IDrugService;
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
	
	@Override
	public List<UnspecifiedDTO<DrugDTO>> getSearchedDrug(DrugDTO searchDrugsRequest) {
		 				
		List<Drug> drugs =new ArrayList<Drug>();
		List<UnspecifiedDTO<DrugDTO>> drugsDTOList = new ArrayList<UnspecifiedDTO<DrugDTO>>();
				
		if(!SearchDrugsRequestIsEmpty(searchDrugsRequest)) {
			drugs =drugRepository.getAllSearchedDrugs(searchDrugsRequest.getName().toLowerCase(),
					searchDrugsRequest.getProducerName().toLowerCase(),searchDrugsRequest.getFabricCode().toLowerCase());
		}else {
			drugs = drugRepository.findAll();
		}
		
		for (Drug currentDrug : drugs) 
		{
				DrugDTO drugDTO= new DrugDTO(currentDrug);				
				drugsDTOList.add(new UnspecifiedDTO<DrugDTO>(currentDrug.getId(),drugDTO));
		}
		
		
		
		return drugsDTOList;
	}
	
	private boolean SearchDrugsRequestIsEmpty(DrugDTO searchDrugsRequest) {
		
		if(searchDrugsRequest.getName().equals("") && searchDrugsRequest.getProducerName().equals("")
		  && searchDrugsRequest.getFabricCode().equals("") ) {
			     return true;
		}
		return false;
		
	}

}
