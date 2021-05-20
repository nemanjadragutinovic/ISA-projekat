package show.isaBack.service.drugService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import show.isaBack.DTO.drugDTO.DrugKindIdDTO;
import show.isaBack.DTO.drugDTO.ManufacturerDTO;
import show.isaBack.DTO.userDTO.AuthorityDTO;
import show.isaBack.interfaceRepository.drugRepository.DrugKindRepository;
import show.isaBack.model.Manufacturer;
import show.isaBack.model.drugs.DrugKindId;
import show.isaBack.serviceInterfaces.IDrugKindIdService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

@Service
public class DrugKindIdService implements IDrugKindIdService {
	
	
	@Autowired
	private  DrugKindRepository  drugKindRepository;
	
	
	@Override
	public List<UnspecifiedDTO<DrugKindIdDTO>> findAllDrugKinds() {
		
		List<UnspecifiedDTO<DrugKindIdDTO>> dkDTO = new ArrayList<UnspecifiedDTO<DrugKindIdDTO>>();
		dkDTO = getAllDrugkids();
		
		return dkDTO;
	}


		private List<UnspecifiedDTO<DrugKindIdDTO>> getAllDrugkids() {
			
			List<DrugKindId> drugkinds = drugKindRepository.findAll();
			List<UnspecifiedDTO<DrugKindIdDTO>> drugKindDTO = new ArrayList<UnspecifiedDTO<DrugKindIdDTO>>();
					
			
			for (DrugKindId currentDrugKind : drugkinds) 
			{
				DrugKindIdDTO dkDTO= new DrugKindIdDTO(currentDrugKind.getType());				
				drugKindDTO.add(new UnspecifiedDTO<DrugKindIdDTO>(currentDrugKind.getId(),dkDTO));
			}
			
			
			return drugKindDTO;
		}

	@Override
	public UnspecifiedDTO<DrugKindIdDTO> findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID create(DrugKindIdDTO entityDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(DrugKindIdDTO entityDTO, UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public List<UnspecifiedDTO<AuthorityDTO>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
