package show.isaBack.service.drugService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import show.isaBack.DTO.AppointmentDTO.IdDTO;
import show.isaBack.DTO.drugDTO.DrugDTO;
import show.isaBack.DTO.drugDTO.DrugEReceiptDTO;
import show.isaBack.DTO.drugDTO.DrugFormatIdDTO;
import show.isaBack.DTO.drugDTO.DrugInstanceDTO;
import show.isaBack.DTO.drugDTO.DrugKindIdDTO;
import show.isaBack.DTO.drugDTO.DrugReservationDTO;
import show.isaBack.DTO.drugDTO.DrugReservationResponseDTO;
import show.isaBack.DTO.drugDTO.DrugWithEreceiptsDTO;
import show.isaBack.DTO.drugDTO.DrugWithPriceDTO;
import show.isaBack.DTO.drugDTO.DrugsWithGradesDTO;
import show.isaBack.DTO.drugDTO.EreceiptDTO;
import show.isaBack.DTO.drugDTO.EreceiptWithPharmacyDTO;
import show.isaBack.DTO.drugDTO.IngredientDTO;
import show.isaBack.DTO.drugDTO.ManufacturerDTO;
import show.isaBack.DTO.pharmacyDTO.PharmacyDTO;
import show.isaBack.DTO.pharmacyDTO.UnspecifiedPharmacyWithDrugAndPrice;
import show.isaBack.DTO.userDTO.AuthorityDTO;

import show.isaBack.DTO.userDTO.PatientDTO;

import show.isaBack.Mappers.Drugs.DrugReservationMapper;
import show.isaBack.Mappers.Drugs.EReceiptsMapper;
import show.isaBack.Mappers.Pharmacy.DrugsWithGradesMapper;
import show.isaBack.emailService.EmailService;
import show.isaBack.interfaceRepository.drugRepository.DrugInstanceRepository;
import show.isaBack.interfaceRepository.drugRepository.DrugRepository;
import show.isaBack.interfaceRepository.drugRepository.IngredientRepository;
import show.isaBack.interfaceRepository.drugRepository.ManufacturerRepository;
import show.isaBack.model.Drug;
import show.isaBack.model.DrugInstance;
import show.isaBack.model.Ingredient;
import show.isaBack.model.Manufacturer;
import show.isaBack.model.Patient;
import show.isaBack.model.Pharmacy;
import show.isaBack.model.drugs.DrugFormatId;
import show.isaBack.model.drugs.DrugInPharmacy;
import show.isaBack.model.drugs.DrugKindId;
import show.isaBack.model.drugs.DrugReservation;
import show.isaBack.model.drugs.DrugReservationStatus;
import show.isaBack.model.drugs.EReceipt;
import show.isaBack.model.drugs.EReceiptItems;
import show.isaBack.model.drugs.EReceiptStatus;
import show.isaBack.repository.drugsRepository.DrugFeedbackRepository;
import show.isaBack.repository.drugsRepository.EReceiptRepository;
import show.isaBack.repository.pharmacyRepository.PharmacyRepository;
import show.isaBack.repository.userRepository.PatientRepository;
import show.isaBack.repository.userRepository.UserRepository;
import show.isaBack.repository.drugsRepository.DrugInPharmacyRepository;
import show.isaBack.repository.drugsRepository.DrugReservationRepository;
import show.isaBack.repository.drugsRepository.EReceiptItemsRepository;
import show.isaBack.service.loyalityService.LoyalityProgramService;
import show.isaBack.service.userService.UserService;
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

	
	@Autowired
	private IUserInterface userService;

	
	@Autowired
	private DrugInPharmacyRepository drugInPharmacyRepository;
	
	@Autowired
	private ILoyaltyService loyalityProgramService;

	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private DrugReservationRepository drugReservationRepository;
	

	@Autowired
	private EmailService emailService;
	

	
	@Autowired
	private EReceiptItemsRepository eReceiptItemsRepository;
	
	int MAX_PENALTY=3;
	
	
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
	
	@Override
	
	public List<UnspecifiedDTO<DrugWithPriceDTO>> searchDrugsInPharmacy(String name, double gradeFrom, double gradeTo, String manufacturer,UUID pharmacyId) {
		List<DrugInstance> drugsWithName;
		System.out.println("eja alooooooooooooooo");
		if(!name.equals("")|| !manufacturer.equals("")) {
			drugsWithName = drugInstanceRepository.findByNameAndManufacturer(name.toLowerCase(),manufacturer.toLowerCase());
		}else
			drugsWithName = drugInstanceRepository.findAll();
		List<DrugInstance> drugsWithNameinPharmacy=new ArrayList<DrugInstance>();
		for(DrugInstance dr:drugsWithName) {
			if(drugInPharmacyRepository.getDrugInPharmacy(dr.getId(), pharmacyId)!=null) {
				drugsWithNameinPharmacy.add(dr);
			}
		}
			
			
		List<UnspecifiedDTO<DrugWithPriceDTO>> drugs = checkDrugGrades1(drugsWithNameinPharmacy, gradeFrom, gradeTo,pharmacyId);
		for(UnspecifiedDTO<DrugWithPriceDTO> dr:drugs)
			System.out.println(dr.Id);
		return drugs;
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
	
	private List<UnspecifiedDTO<DrugWithPriceDTO>> checkDrugGrades1(List<DrugInstance> drugsWithName, double gradeFrom, double gradeTo,UUID pharmacyId){
		List<UnspecifiedDTO<DrugWithPriceDTO>> drugs = new ArrayList<UnspecifiedDTO<DrugWithPriceDTO>>();
		double grade;
		double price=0;
		for (DrugInstance var : drugsWithName) 
		{ 
			grade = findAvgGradeForDrug(var.getId());
			System.out.println(grade);
			Double price1=drugInPharmacyRepository.getCurrentPriceForDrugInPharmacy(var.getId(), pharmacyId);
			DrugInPharmacy pomDrug=drugInPharmacyRepository.getDrugInPharmacy(var.getId(), pharmacyId);
			if(price1!=null)
				price=price1;
			
			if(!(gradeFrom == -1.0 || gradeTo == -1.0)) {
				if(grade >= gradeFrom && grade <= gradeTo) {
					drugs.add(DrugsWithGradesMapper.MapDrugInstancePersistenceToDrugWithPriceDTO(var, grade,price,pomDrug.getCount()));
				}
			}else {
				if(gradeFrom == -1.0 & gradeTo != -1.0) {
					if(grade <= gradeTo)
						drugs.add(DrugsWithGradesMapper.MapDrugInstancePersistenceToDrugWithPriceDTO(var, grade,price,pomDrug.getCount()));
				}else if (gradeTo == -1.0 & gradeFrom != -1.0){
					if(grade >= gradeFrom)
						drugs.add(DrugsWithGradesMapper.MapDrugInstancePersistenceToDrugWithPriceDTO(var, grade,price,pomDrug.getCount()));
				}else {
					drugs.add(DrugsWithGradesMapper.MapDrugInstancePersistenceToDrugWithPriceDTO(var, grade,price,pomDrug.getCount()));
				}
			}
			
		}
		
		return drugs;
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
		
		UUID patientId = userService.getLoggedUserId();
		
		
	
		
		if(id==null) {
			return false;
		}
		
		try {
			UUID QrID = UUID.fromString(id);
		if(eReceiptRepository.existsById(QrID)) {
			if(eReceiptRepository.findById(QrID).get().getStatus().toString().equals("NEW") && eReceiptRepository.findById(QrID).get().getPatient().getId().equals(patientId) ) {
				System.out.println("KAKO JE OVDE USAO ????????????????????????????");
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
			return count;
		} catch (Exception e) {
			int count = 0;
			return count;
		}
	}
	
	
	@Override	
	public void createDrugReservation(DrugReservationDTO drugReservationDTO) {

		UUID Id = userService.getLoggedUserId();
		Patient patient = patientRepository.getOne(Id);

		Pharmacy pharmacy=pharmacyRepository.getOne(drugReservationDTO.getPharmacyId());
		DrugInstance drugInstance=drugInstanceRepository.getOne(drugReservationDTO.getDrugId());

		DrugReservation drugReservation = new DrugReservation(patient,pharmacy,drugInstance,
				drugReservationDTO.getDrugsCount(), drugReservationDTO.getEndDate(), drugReservationDTO.getPriceForOneDrug());
		
		IsReservationValid(drugReservation.getEndDate(),drugReservation.getStartDate(),patient.getPenalty());
		
	
		reduceDrugsInPharmacyCount( drugInstance.getId(),pharmacy.getId(),drugReservationDTO.getDrugsCount());
		
			
		drugReservationRepository.save(drugReservation);
	
		sendEmail(drugReservation);
		
	}
	
	
	public void sendEmail(DrugReservation drugReservation) {
		
		try {
			emailService.sendNotificationForDrugReservation(drugReservation);
		} catch (MailException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void reduceDrugsInPharmacyCount(UUID drugId, UUID pharmacyId, int reduceCount) {
		
		DrugInPharmacy drugInPharmacy= drugInPharmacyRepository.getDrugInPharmacy(drugId, pharmacyId);
		
		drugInPharmacy.reduceCount(reduceCount);
		
		drugInPharmacyRepository.save(drugInPharmacy);
		
	}

	public void IsReservationValid(Date endDate,Date startDate, int patientPenalty) {
			
		if(endDate.compareTo(new Date())<0 || endDate.compareTo(startDate)<0)
			throw new IllegalArgumentException("Bad request with dates");
		
		if(patientPenalty >= MAX_PENALTY)
			throw new IllegalArgumentException("Patient has 3 or more penalties");
		
	}
	

	@Override
	public List<UnspecifiedDTO<DrugReservationResponseDTO>> findAllFuturePatientsDrugReservation(){
		UUID patientId = userService.getLoggedUserId();
		List<DrugReservation> drugReservations= new ArrayList<DrugReservation>();
		drugReservations= drugReservationRepository.findAllFutureDrugsReservationForPatients(patientId);

		return DrugReservationMapper.mapDrugsReservationToDrugsReseervationDTO(drugReservations);
		
		
	}
	
	@Override
	public List<UnspecifiedDTO<DrugReservationResponseDTO>> findAllhistoryPatientsDrugReservation(){
		UUID patientId = userService.getLoggedUserId();
		List<DrugReservation> drugReservations= new ArrayList<DrugReservation>();
		drugReservations= drugReservationRepository.findAllhistoryDrugsReservationForPatients(patientId);
	
		return DrugReservationMapper.mapDrugsReservationToDrugsReseervationDTO(drugReservations);
		
		
	}
	
	@Override
	public void cancelPatientDrugReservation(IdDTO drugIdObject) {
		
		DrugReservation drugReservation= drugReservationRepository.getOne(drugIdObject.getId());
		chechForCancelDrugReservation(drugReservation);
			
		drugReservation.setDrugReservationStatus(DrugReservationStatus.CANCELED);
		DrugInPharmacy drugInPharmacy= drugInPharmacyRepository.getDrugInPharmacy(drugReservation.getDrugInstance().getId(), drugReservation.getPharmacy().getId());
		drugInPharmacy.addCount(drugReservation.getCount());
		
		drugReservationRepository.save(drugReservation);
		drugInPharmacyRepository.save(drugInPharmacy);
	}
	
	public void chechForCancelDrugReservation(DrugReservation drugReservation) {
		
		Date endDadateOneDayBefore=new Date(drugReservation.getEndDate().getTime() - 86400000);
		Date nowDate= new Date();
		
		if(nowDate.compareTo(endDadateOneDayBefore) > 0)
			throw new IllegalArgumentException("End date of reservation is after current date - 24h");
		
		
	}
	
	
	@Override
	public List<UnspecifiedDTO<EreceiptDTO>> findAllPatientsEreceipts(){
		UUID patientId = userService.getLoggedUserId();
		List<UnspecifiedDTO<EreceiptDTO>> EreceiptsDTOlist= new ArrayList<UnspecifiedDTO<EreceiptDTO>>();
		List<EReceipt> EReceiptList=eReceiptRepository.findAllEReceiptsForPatient(patientId);
		
		for (EReceipt eReceipt : EReceiptList) {				
			
			List<UnspecifiedDTO<DrugEReceiptDTO>> listDrugdDTO=
					EReceiptsMapper.mapEReceiptItemsToEreceiptsDrugDTO(eReceiptItemsRepository.findAllByEReceiptId(eReceipt.getId()));           
			
			UnspecifiedDTO<EreceiptDTO> eReceiptDTO= EReceiptsMapper.mapEreceiptToEreceiptDTO(eReceipt,listDrugdDTO); 
			EreceiptsDTOlist.add(eReceiptDTO);
			
		}
		
		return EreceiptsDTOlist;
		
	}
	
	
	
	@Override
	public List<UnspecifiedDTO<EreceiptDTO>> findAllPatientsEreceiptsSortByDateAscending(){
		
		UUID patientId = userService.getLoggedUserId();
		List<UnspecifiedDTO<EreceiptDTO>> EreceiptsDTOlist= new ArrayList<UnspecifiedDTO<EreceiptDTO>>();
		List<EReceipt> EReceiptList=eReceiptRepository.findAllEReceiptsForPatientSortByDateAscending(patientId);
		
		for (EReceipt eReceipt : EReceiptList) {				
			
			List<UnspecifiedDTO<DrugEReceiptDTO>> listDrugdDTO=
					EReceiptsMapper.mapEReceiptItemsToEreceiptsDrugDTO(eReceiptItemsRepository.findAllByEReceiptId(eReceipt.getId()));           
			
			UnspecifiedDTO<EreceiptDTO> eReceiptDTO= EReceiptsMapper.mapEreceiptToEreceiptDTO(eReceipt,listDrugdDTO); 
			EreceiptsDTOlist.add(eReceiptDTO);
			
		}
		return EreceiptsDTOlist;
		
	}
	
	
	@Override
	public List<UnspecifiedDTO<EreceiptDTO>> findAllPatientsEreceiptsSortByDateDescending(){
		
		UUID patientId = userService.getLoggedUserId();
		List<UnspecifiedDTO<EreceiptDTO>> EreceiptsDTOlist= new ArrayList<UnspecifiedDTO<EreceiptDTO>>();
		List<EReceipt> EReceiptList=eReceiptRepository.findAllEReceiptsForPatientSortByDateDescending(patientId);
		
		for (EReceipt eReceipt : EReceiptList) {				
			
			List<UnspecifiedDTO<DrugEReceiptDTO>> listDrugdDTO=
					EReceiptsMapper.mapEReceiptItemsToEreceiptsDrugDTO(eReceiptItemsRepository.findAllByEReceiptId(eReceipt.getId()));           
			
			UnspecifiedDTO<EreceiptDTO> eReceiptDTO= EReceiptsMapper.mapEreceiptToEreceiptDTO(eReceipt,listDrugdDTO); 
			EreceiptsDTOlist.add(eReceiptDTO);
			
		}
		return EreceiptsDTOlist;
		
	}
	
	
	@Override
	public List<UnspecifiedDTO<EreceiptDTO>> findAllPatientsEreceiptsSortByDateAscendingWithStatus(EReceiptStatus searchStatus){
		
		UUID patientId = userService.getLoggedUserId();
		List<UnspecifiedDTO<EreceiptDTO>> EreceiptsDTOlist= new ArrayList<UnspecifiedDTO<EreceiptDTO>>();
		List<EReceipt> EReceiptList=eReceiptRepository.findAllEReceiptsForPatientSortByDateAscescendingWithStatus(patientId,searchStatus);
		
		for (EReceipt eReceipt : EReceiptList) {				
			
			List<UnspecifiedDTO<DrugEReceiptDTO>> listDrugdDTO=
					EReceiptsMapper.mapEReceiptItemsToEreceiptsDrugDTO(eReceiptItemsRepository.findAllByEReceiptId(eReceipt.getId()));           
			
			UnspecifiedDTO<EreceiptDTO> eReceiptDTO= EReceiptsMapper.mapEreceiptToEreceiptDTO(eReceipt,listDrugdDTO); 
			EreceiptsDTOlist.add(eReceiptDTO);
			
		}
		return EreceiptsDTOlist;
		
	}
	
	
	@Override
	public List<UnspecifiedDTO<EreceiptDTO>> findAllPatientsEreceiptsSortByDateDescendingWithStatus(EReceiptStatus searchStatus){
		
		UUID patientId = userService.getLoggedUserId();
		List<UnspecifiedDTO<EreceiptDTO>> EreceiptsDTOlist= new ArrayList<UnspecifiedDTO<EreceiptDTO>>();
		List<EReceipt> EReceiptList=eReceiptRepository.findAllEReceiptsForPatientSortByDateDescendingWitStatus(patientId,searchStatus);
		
		for (EReceipt eReceipt : EReceiptList) {				
			
			List<UnspecifiedDTO<DrugEReceiptDTO>> listDrugdDTO=
					EReceiptsMapper.mapEReceiptItemsToEreceiptsDrugDTO(eReceiptItemsRepository.findAllByEReceiptId(eReceipt.getId()));           
			
			UnspecifiedDTO<EreceiptDTO> eReceiptDTO= EReceiptsMapper.mapEreceiptToEreceiptDTO(eReceipt,listDrugdDTO); 
			EreceiptsDTOlist.add(eReceiptDTO);
			
		}
		return EreceiptsDTOlist;
		
	}
	
	@Override
	public List<UnspecifiedDTO<EreceiptDTO>> findAllPatientsEreceiptsWithStatus(EReceiptStatus searchStatus){
		
		UUID patientId = userService.getLoggedUserId();
		List<UnspecifiedDTO<EreceiptDTO>> EreceiptsDTOlist= new ArrayList<UnspecifiedDTO<EreceiptDTO>>();
		List<EReceipt> EReceiptList=eReceiptRepository.findAllEReceiptsForPatientWithStatus(patientId,searchStatus);
		
		for (EReceipt eReceipt : EReceiptList) {				
			
			List<UnspecifiedDTO<DrugEReceiptDTO>> listDrugdDTO=
					EReceiptsMapper.mapEReceiptItemsToEreceiptsDrugDTO(eReceiptItemsRepository.findAllByEReceiptId(eReceipt.getId()));           
			
			UnspecifiedDTO<EreceiptDTO> eReceiptDTO= EReceiptsMapper.mapEreceiptToEreceiptDTO(eReceipt,listDrugdDTO); 
			EreceiptsDTOlist.add(eReceiptDTO);
			
		}
		return EreceiptsDTOlist;
		
	}
	
	
	@Override
	public List<UnspecifiedDTO<DrugWithEreceiptsDTO>> findAllPatientsPRoccesedDrugsFromEreceipts(){
		
		UUID patientId = userService.getLoggedUserId();
		List<UnspecifiedDTO<DrugWithEreceiptsDTO>> drugsWithEreceipts= new ArrayList<UnspecifiedDTO<DrugWithEreceiptsDTO>>();
		
		List<DrugInstance> processedDrugsList=eReceiptItemsRepository.findAllProccessedDistinctDrugsByPatient(patientId);
		
		System.out.println("1");
		System.out.println(processedDrugsList);
		
		for (DrugInstance drugInstance : processedDrugsList) {
			List<UnspecifiedDTO<EreceiptWithPharmacyDTO>> eReceiptsWithPharmacyForDrug= 
				EReceiptsMapper.mapEReceiptItemsToEreceiptsWithPharmacyDrugDTO(eReceiptItemsRepository.findAllEreciptsForDrugByPatient(patientId,drugInstance.getId()));         
		
			System.out.println("2");
			
			drugsWithEreceipts.add(EReceiptsMapper.mapProccessedDrugWithEreceiptsForHimToDrugWithEreceiptsDTO(drugInstance,eReceiptsWithPharmacyForDrug));
			
		}
			
		
		return drugsWithEreceipts;
		
	}
	
	
	@Override
	public void refreshPatientDrugsReservations() {
		
		
		
;		List<DrugReservation> activeDrugReservationListThatHaveExpired= drugReservationRepository.getAllActiveDrugReservationThatHaveExpired();
	
		if(activeDrugReservationListThatHaveExpired.size()!=0) {
			
			for (DrugReservation drugReservation : activeDrugReservationListThatHaveExpired) {
				drugReservation.setDrugReservationStatus(DrugReservationStatus.EXPIRED);
				Patient patient=patientRepository.findById(drugReservation.getPatient().getId()).get();
				patient.addPenalties(1);
				patientRepository.save(patient);
			}
			
				
			drugReservationRepository.saveAll(activeDrugReservationListThatHaveExpired);
					
			addCountOfDrugInPhamracy(activeDrugReservationListThatHaveExpired);
			
		}
		
		
	}
	
	
	
	public void addCountOfDrugInPhamracy(List<DrugReservation> activeDrugReservationListThatHaveExpired) {
		
		for (DrugReservation drugReservation : activeDrugReservationListThatHaveExpired) {
			
			DrugInPharmacy drugInPharmacy= drugInPharmacyRepository.getDrugInPharmacy(drugReservation.getDrugInstance().getId(), drugReservation.getPharmacy().getId());
			System.out.println(drugInPharmacy);
			drugInPharmacy.addCount(drugReservation.getCount());
			System.out.println("pred kraj");
			drugInPharmacyRepository.save(drugInPharmacy);
			System.out.println("kraj");
			
		}
	}
	
	
	@Override
	public List<UnspecifiedDTO<DrugWithPriceDTO>> findDrugsInPharmacyWithPrice(UUID pharmacyId) {
		List<UnspecifiedDTO<DrugWithPriceDTO>> drugs = new ArrayList<UnspecifiedDTO<DrugWithPriceDTO>>();
		
		for(DrugInstance drugInstance : drugInstanceRepository.findAll()) {
			double price=0;
			DrugInPharmacy drug=drugInPharmacyRepository.getDrugInPharmacy(drugInstance.getId(), pharmacyId);
			if(drugInPharmacyRepository.getDrugInPharmacy(drugInstance.getId(), pharmacyId)!=null) {
				double grade = findAvgGradeForDrug(drugInstance.getId());
				Double price1=drugInPharmacyRepository.getCurrentPriceForDrugInPharmacy(drugInstance.getId(), pharmacyId);
				if(price1!=null)
					price=price1;
				drugs.add(new UnspecifiedDTO<DrugWithPriceDTO>(drugInstance.getId(),new DrugWithPriceDTO(drugInstance.getName(),drugInstance.getManufacturer().getName(),drugInstance.getDrugInstanceName(),drugInstance.getDrugFormat(),drugInstance.getQuantity(),grade,price,drug.getCount())));
			}
				
		}
		
		return drugs;
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
