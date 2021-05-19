package show.isaBack.service.drugService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import show.isaBack.DTO.drugDTO.DrugDTO;
import show.isaBack.DTO.drugDTO.DrugFormatIdDTO;
import show.isaBack.DTO.drugDTO.DrugInstanceDTO;
import show.isaBack.DTO.drugDTO.DrugKindIdDTO;
import show.isaBack.DTO.drugDTO.IngredientDTO;
import show.isaBack.DTO.drugDTO.ManufacturerDTO;
import show.isaBack.DTO.pharmacyDTO.PharmacyDTO;
import show.isaBack.DTO.userDTO.AuthorityDTO;
import show.isaBack.interfaceRepository.drugRepository.DrugInstanceRepository;
import show.isaBack.interfaceRepository.drugRepository.DrugRepository;
import show.isaBack.interfaceRepository.drugRepository.IngredientRepository;
import show.isaBack.interfaceRepository.drugRepository.ManufacturerRepository;
import show.isaBack.model.Drug;
import show.isaBack.model.DrugInstance;
import show.isaBack.model.Ingredient;
import show.isaBack.model.Manufacturer;
import show.isaBack.model.Pharmacy;
import show.isaBack.model.drugs.DrugFormatId;
import show.isaBack.model.drugs.DrugKindId;
import show.isaBack.serviceInterfaces.IDrugService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

@Service
public class DrugService implements IDrugService{


	@Autowired
	private DrugRepository drugRepository;
	
	@Autowired
	private DrugInstanceRepository drugInstanceRepository;
	
	@Autowired
	private ManufacturerRepository manufacturerRepository;
	
	@Autowired
	private IngredientRepository ingredientRepository;
	
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
	
	@Override
	public UUID create(DrugInstanceDTO entityDTO) {
		DrugInstance drugInstance = CreateDrugInstanceFromDTO(entityDTO);
		drugInstanceRepository.save(drugInstance);
		return drugInstance.getId();
	}
	
	private DrugInstance CreateDrugInstanceFromDTO(DrugInstanceDTO drugInstanceDTO) {
		return new DrugInstance(drugInstanceDTO.getName(), drugInstanceDTO.getProducerName(), drugInstanceDTO.getCode(), drugInstanceDTO.getDrugInstanceName(), drugInstanceDTO.getDrugFormat(), drugInstanceDTO.getQuantity(),  drugInstanceDTO.getSideEffects(), drugInstanceDTO.getRecommendedAmount(),
				drugInstanceDTO.getLoyalityPoints(), drugInstanceDTO.isOnReciept(), drugInstanceDTO.getDrugKind());
	}
	
	@Override
	public UUID addDrugReplacement(UUID id, UUID replacement_id) {
		
		DrugInstance drugInstance = drugInstanceRepository.getOne(id);
		DrugInstance drugReplacementInstance = drugInstanceRepository.getOne(replacement_id);
		
		drugInstance.addReplaceDrug(drugReplacementInstance);
		
		drugInstanceRepository.save(drugInstance);
		
		return id;
	}
	
	@Override
	public List<UnspecifiedDTO<ManufacturerDTO>>  findDrugManufacturers() {
			
			List<UnspecifiedDTO<ManufacturerDTO>> manufacturers = new ArrayList<UnspecifiedDTO<ManufacturerDTO>>();
			manufacturers = getAllManufacturer();
			
			return manufacturers;
	}
	
	private List<UnspecifiedDTO<ManufacturerDTO>> getAllManufacturer() {
			
		List<Manufacturer> man = manufacturerRepository.findAll();
		List<UnspecifiedDTO<ManufacturerDTO>> manDTO = new ArrayList<UnspecifiedDTO<ManufacturerDTO>>();
				
		
		for (Manufacturer currentMan : man) 
		{
			ManufacturerDTO pharmacyDTO= new ManufacturerDTO(currentMan.getName());				
			manDTO.add(new UnspecifiedDTO<ManufacturerDTO>(currentMan.getId(),pharmacyDTO));
		}
		
		
		return manDTO;
	}
	
	@Override
	public UUID addDrugManufacturer(UUID id, UUID manufacturerId) {
		DrugInstance drugInstance = drugInstanceRepository.getOne(id);
		Manufacturer manufacturer = manufacturerRepository.getOne(manufacturerId);
		
		drugInstance.setManufacturer(manufacturer);
		
		drugInstanceRepository.save(drugInstance);
		
		return id;
	}
	
	@Override
	public UUID addDrugIngredients(UUID id, IngredientDTO entityDTO) {
		DrugInstance drugInstance = drugInstanceRepository.getOne(id);
		Ingredient ingredient = ingredientRepository.findByName(entityDTO.getName());
		drugInstance.addIngredient(ingredient);
		
		drugInstanceRepository.save(drugInstance);
		
		return id;
	}
	
	@Override
	public List<UnspecifiedDTO<DrugInstanceDTO>> findAllDrugKinds() {
		
		List<UnspecifiedDTO<DrugInstanceDTO>> dkDTO = new ArrayList<UnspecifiedDTO<DrugInstanceDTO>>();
		dkDTO = getAllDrugInstancesDTO();
		
		return dkDTO;
	}
	
	private List<UnspecifiedDTO<DrugInstanceDTO>> getAllDrugInstancesDTO() {
		
		List<DrugInstance> drugkinds = drugInstanceRepository.findAll();
		List<UnspecifiedDTO<DrugInstanceDTO>> drugKindDTO = new ArrayList<UnspecifiedDTO<DrugInstanceDTO>>();
				
		
		for (DrugInstance currentDrugKind : drugkinds) 
		{
			DrugInstanceDTO dkDTO= new DrugInstanceDTO(currentDrugKind.getName(),currentDrugKind.getProducerName(),currentDrugKind.getFabricCode(),currentDrugKind.getDrugInstanceName(),currentDrugKind.getDrugFormat(),currentDrugKind.getQuantity(),currentDrugKind.getSideEffects(),currentDrugKind.getRecommendedAmount(),currentDrugKind.getLoyalityPoints(),currentDrugKind.isOnReciept(),currentDrugKind.getDrugKind());				
			drugKindDTO.add(new UnspecifiedDTO<DrugInstanceDTO>(currentDrugKind.getId(),dkDTO));
		}
		
		
		return drugKindDTO;
	}

	@Override
	public List<UnspecifiedDTO<AuthorityDTO>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnspecifiedDTO<DrugInstanceDTO> findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public void update(DrugInstanceDTO entityDTO, UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

}
