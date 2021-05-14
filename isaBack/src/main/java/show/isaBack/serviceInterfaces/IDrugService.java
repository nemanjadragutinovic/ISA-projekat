package show.isaBack.serviceInterfaces;

import java.util.List;
import java.util.UUID;

import show.isaBack.DTO.drugDTO.DrugDTO;
import show.isaBack.DTO.drugDTO.DrugInstanceDTO;
import show.isaBack.DTO.drugDTO.IngredientDTO;
import show.isaBack.DTO.drugDTO.ManufacturerDTO;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;



public interface IDrugService extends IService<DrugInstanceDTO, UnspecifiedDTO<DrugInstanceDTO>>{
	
	List<UnspecifiedDTO<DrugDTO>> getAllDrugs();
	List<UnspecifiedDTO<DrugDTO>> getSearchedDrug(DrugDTO searchDrugsRequest);
	public UUID addDrugReplacement(UUID id, UUID replacement_id);
	public List<UnspecifiedDTO<ManufacturerDTO>>  findDrugManufacturers();
	public UUID addDrugManufacturer(UUID id, UUID manufacturerId);
	public UUID addDrugIngredients(UUID id, IngredientDTO entityDTO);

}
