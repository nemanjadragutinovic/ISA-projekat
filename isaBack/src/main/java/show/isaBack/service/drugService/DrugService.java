package show.isaBack.service.drugService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import show.isaBack.DTO.drugDTO.DrugDTO;
import show.isaBack.DTO.drugDTO.DrugFormatIdDTO;
import show.isaBack.DTO.drugDTO.DrugInstanceDTO;
import show.isaBack.DTO.drugDTO.DrugKindIdDTO;
import show.isaBack.DTO.drugDTO.DrugsWithGradesDTO;
import show.isaBack.DTO.drugDTO.IngredientDTO;
import show.isaBack.DTO.drugDTO.ManufacturerDTO;
import show.isaBack.DTO.pharmacyDTO.PharmacyDTO;
import show.isaBack.DTO.pharmacyDTO.UnspecifiedPharmacyWithDrugAndPrice;
import show.isaBack.DTO.userDTO.AuthorityDTO;
import show.isaBack.Mappers.Pharmacy.DrugsWithGradesMapper;
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
import show.isaBack.model.drugs.DrugInPharmacy;
import show.isaBack.model.drugs.DrugKindId;
import show.isaBack.model.drugs.EReceipt;
import show.isaBack.repository.drugsRepository.DrugFeedbackRepository;
import show.isaBack.repository.drugsRepository.EReceiptRepository;
import show.isaBack.repository.drugsRepository.DrugInPharmacyRepository;
import show.isaBack.service.loyalityService.LoyalityProgramService;
import show.isaBack.serviceInterfaces.IDrugService;
import show.isaBack.serviceInterfaces.ILoyaltyService;
import show.isaBack.serviceInterfaces.IUserInterface;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

@Service
public class DrugService implements IDrugService{


	@Autowired
	private DrugRepository drugRepository;
	
	@Autowired
	private DrugInstanceRepository drugInstanceRepository;
	
	@Autowired
	private DrugFeedbackRepository drugFeedbackRepository;
	
	@Autowired
	private ManufacturerRepository manufacturerRepository;
	
	@Autowired
	private IngredientRepository ingredientRepository;
	
	@Autowired

	private EReceiptRepository eReceiptRepository;

	private IUserInterface userService;
	
	@Autowired
	private DrugInPharmacyRepository drugInPharmacyRepository;
	
	@Autowired
	private ILoyaltyService loyalityProgramService;

	
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
	public List<UnspecifiedDTO<DrugsWithGradesDTO>> searchDrugs(String name, double gradeFrom, double gradeTo, String drugKind) {
		List<DrugInstance> drugsWithName;
		if(!name.equals("")) {
			drugsWithName = drugInstanceRepository.findByName(name.toLowerCase());
		}else
			drugsWithName = drugInstanceRepository.findAll();
		
		List<DrugInstance> drugsWithKind = sreachDrugKind(drugsWithName, drugKind);
		List<UnspecifiedDTO<DrugsWithGradesDTO>> drugsWithGrades = checkDrugGrades(drugsWithKind, gradeFrom, gradeTo);
		
		return drugsWithGrades;
	}
	
	private List<DrugInstance> sreachDrugKind(List<DrugInstance> drugs, String Kind){
		
		if(Kind.equals(""))
			return drugs;
		
		List<DrugInstance> drugsWithKind = new ArrayList<DrugInstance>();
		for (DrugInstance drugInstance : drugs) {
			if(drugInstance.getDrugKind().toString().equals(Kind)) {
				drugsWithKind.add(drugInstance);
			}
		}
		
		return drugsWithKind;
	}
	
	private List<UnspecifiedDTO<DrugsWithGradesDTO>> checkDrugGrades(List<DrugInstance> drugsWithKind, double gradeFrom, double gradeTo){
		List<UnspecifiedDTO<DrugsWithGradesDTO>> drugsWithGrades = new ArrayList<UnspecifiedDTO<DrugsWithGradesDTO>>();
		double grade;
		
		for (DrugInstance var : drugsWithKind) 
		{ 
			grade = findAvgGradeForDrug(var.getId());
			
			if(!(gradeFrom == -1.0 || gradeTo == -1.0)) {
				if(grade >= gradeFrom && grade <= gradeTo) {
					drugsWithGrades.add(DrugsWithGradesMapper.MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(var, grade));
				}
			}else {
				if(gradeFrom == -1.0 & gradeTo != -1.0) {
					if(grade <= gradeTo)
						drugsWithGrades.add(DrugsWithGradesMapper.MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(var, grade));
				}else if (gradeTo == -1.0 & gradeFrom != -1.0){
					if(grade >= gradeFrom)
						drugsWithGrades.add(DrugsWithGradesMapper.MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(var, grade));
				}else {
					drugsWithGrades.add(DrugsWithGradesMapper.MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(var, grade));
				}
			}
			
		}
		
		return drugsWithGrades;
	}
	
	@Override
	public List<UnspecifiedDTO<DrugsWithGradesDTO>> findDrugsWithGrades() {
		List<DrugInstance> drugs = drugInstanceRepository.findAll();
		List<UnspecifiedDTO<DrugsWithGradesDTO>> drugsWithGrades = new ArrayList<UnspecifiedDTO<DrugsWithGradesDTO>>();
		double grade;
		for (DrugInstance var : drugs) 
		{ 
			System.out.println(var.getDrugInstanceName());
			grade = findAvgGradeForDrug(var.getId());
			drugsWithGrades.add(DrugsWithGradesMapper.MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(var, grade));
			
		}
	
		return drugsWithGrades;
	}
	
	@Override
	public double findAvgGradeForDrug(UUID drugId) {
		try {
			double retVal = drugFeedbackRepository.findAvgGradeForDrug(drugId);
			
			return  retVal;
		}catch (Exception e) {
			return 0;
		}
	}

	@Override
	public boolean isQrCodeValid(String id) {
		
	
		
		if(id==null) {
			return false;
		}
		
		try {
			UUID QrID = UUID.fromString(id);
		if(eReceiptRepository.existsById(QrID)) {
			if(eReceiptRepository.findById(QrID).get().getStatus().toString().equals("NEW")) {
				return true;
				
			}
		}
		}catch (Exception e) {
			return false;
		}
		
		
		return false;
	}

	
	
	@Override
	public List<UnspecifiedPharmacyWithDrugAndPrice> findPharmaciesByDrugIdWithDrugPrice(UUID drugId) {
		
			
		UUID patientId = userService.getLoggedUserId();

		
		List<DrugInPharmacy> availablePharmaciesWithDrug = new ArrayList<DrugInPharmacy>();
		availablePharmaciesWithDrug= drugInPharmacyRepository.getAllPharmaciesForDrug(drugId);
		
		
		List<UnspecifiedPharmacyWithDrugAndPrice> returnPharmacies = new ArrayList<UnspecifiedPharmacyWithDrugAndPrice>();
		returnPharmacies=convertAvailablePharmaciesToDTO(availablePharmaciesWithDrug);
		
		for (UnspecifiedPharmacyWithDrugAndPrice currentPharmacy : returnPharmacies) {
				
				int count= getCoutForDrugInPharmacy(drugId, currentPharmacy.Id);
				
				if(count != 0) {
					currentPharmacy.setAvailableDrugCount(count);
					currentPharmacy.setDrugPrice(loyalityProgramService.getDiscountPriceForDrugForPatient(patientId, currentPharmacy.getDrugPrice()));
				
					System.out.println("Name " + currentPharmacy.EntityDTO.getName() + "price " + currentPharmacy.getDrugPrice() + "count " + count);
				}
				
		}

		return returnPharmacies;
	}
	
	
	public List<UnspecifiedPharmacyWithDrugAndPrice> convertAvailablePharmaciesToDTO(List<DrugInPharmacy> availablePharmaciesWithDrug ){
		
		List<UnspecifiedPharmacyWithDrugAndPrice> retVal = new ArrayList<UnspecifiedPharmacyWithDrugAndPrice>();
		
		for (DrugInPharmacy drugInPharmacy : availablePharmaciesWithDrug) {
			
			Pharmacy pharmacy= drugInPharmacy.getPharmacy();
			retVal.add(new UnspecifiedPharmacyWithDrugAndPrice(pharmacy.getId(), pharmacy.getName(),pharmacy.getAddress(),
					pharmacy.getDescription(), pharmacy.getConsultationPrice(), drugInPharmacy.getPrice(),0));
		
		}
		
		return retVal;
	}
	
	private int getCoutForDrugInPharmacy(UUID drugId, UUID pharmacyId) {
		
		try {
			int count = drugInPharmacyRepository.getCountForDrugInpharmacy(drugId, pharmacyId);
			System.out.println("COUNT" + count);
			return count;
		} catch (Exception e) {
			System.out.println("ZERO");
			return 0;
		}
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
