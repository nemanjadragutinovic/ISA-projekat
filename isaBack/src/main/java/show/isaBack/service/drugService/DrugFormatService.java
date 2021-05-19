package show.isaBack.service.drugService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import show.isaBack.DTO.drugDTO.DrugFormatIdDTO;
import show.isaBack.DTO.drugDTO.DrugKindIdDTO;
import show.isaBack.DTO.userDTO.AuthorityDTO;
import show.isaBack.interfaceRepository.drugRepository.DrugFormatRepository;
import show.isaBack.model.drugs.DrugFormatId;
import show.isaBack.model.drugs.DrugKindId;
import show.isaBack.serviceInterfaces.IDrugFormatService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

@Service
public class DrugFormatService implements IDrugFormatService {

	@Autowired
	private  DrugFormatRepository  drugFormatRepository;
	
	@Override
	public List<UnspecifiedDTO<DrugFormatIdDTO>> findAllDrugKinds() {
		
		List<UnspecifiedDTO<DrugFormatIdDTO>> dkDTO = new ArrayList<UnspecifiedDTO<DrugFormatIdDTO>>();
		dkDTO = getAllDrugFormats();
		
		return dkDTO;
	}
	
	private List<UnspecifiedDTO<DrugFormatIdDTO>> getAllDrugFormats() {
		
		List<DrugFormatId> drugkinds = drugFormatRepository.findAll();
		List<UnspecifiedDTO<DrugFormatIdDTO>> drugKindDTO = new ArrayList<UnspecifiedDTO<DrugFormatIdDTO>>();
				
		
		for (DrugFormatId currentDrugKind : drugkinds) 
		{
			DrugFormatIdDTO dkDTO= new DrugFormatIdDTO(currentDrugKind.getType());				
			drugKindDTO.add(new UnspecifiedDTO<DrugFormatIdDTO>(currentDrugKind.getId(),dkDTO));
		}
		
		
		return drugKindDTO;
	}

	@Override
	public List<UnspecifiedDTO<AuthorityDTO>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnspecifiedDTO<DrugFormatIdDTO> findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID create(DrugFormatIdDTO entityDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(DrugFormatIdDTO entityDTO, UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
	
	
