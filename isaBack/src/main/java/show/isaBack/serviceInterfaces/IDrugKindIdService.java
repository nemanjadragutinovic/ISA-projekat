package show.isaBack.serviceInterfaces;

import java.util.List;

import show.isaBack.DTO.drugDTO.DrugKindIdDTO;

import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public interface IDrugKindIdService extends IService<DrugKindIdDTO, UnspecifiedDTO<DrugKindIdDTO>>{
	
	public List<UnspecifiedDTO<DrugKindIdDTO>> findAllDrugKinds();
	

}
