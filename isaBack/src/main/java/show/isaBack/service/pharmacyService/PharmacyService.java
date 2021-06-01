package show.isaBack.service.pharmacyService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import java.util.Optional;

import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import show.isaBack.DTO.drugDTO.DrugDTO;
import show.isaBack.DTO.drugDTO.PharmacyDrugPriceDTO;
import show.isaBack.DTO.drugDTO.PharmacyERecipeDTO;
import show.isaBack.DTO.pharmacyDTO.PharmacyDTO;
import show.isaBack.DTO.pharmacyDTO.PharmacySearchDTO;
import show.isaBack.DTO.pharmacyDTO.PharmacyWithGradeAndPriceDTO;
import show.isaBack.DTO.userDTO.AuthorityDTO;
import show.isaBack.DTO.userDTO.LoyalityProgramForPatientDTO;
import show.isaBack.Mappers.Pharmacy.PharmacyMapper;
import show.isaBack.emailService.EmailService;
import show.isaBack.model.Drug;
import show.isaBack.model.Patient;
import show.isaBack.model.Pharmacy;

import show.isaBack.model.PharmacyAdmin;
import show.isaBack.repository.pharmacyRepository.PharmacyRepository;
import show.isaBack.repository.userRepository.PharmacyAdminRepository;

import show.isaBack.model.PharmacyGrade;
import show.isaBack.model.drugs.DrugInPharmacy;
import show.isaBack.model.drugs.EReceipt;
import show.isaBack.model.drugs.EReceiptItems;
import show.isaBack.model.drugs.EReceiptStatus;
import show.isaBack.repository.Pharmacy.PharmacyGradeRepository;
import show.isaBack.repository.drugsRepository.DrugInPharmacyRepository;
import show.isaBack.repository.drugsRepository.EReceiptItemsRepository;
import show.isaBack.repository.drugsRepository.EReceiptRepository;
import show.isaBack.repository.pharmacyRepository.PharmacyRepository;
import show.isaBack.repository.userRepository.PatientRepository;
import show.isaBack.service.loyalityService.LoyalityProgramService;

import show.isaBack.serviceInterfaces.IAppointmentService;
import show.isaBack.serviceInterfaces.IPharmacyGradeService;
import show.isaBack.serviceInterfaces.IPharmacyService;
import show.isaBack.serviceInterfaces.IUserInterface;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

@Service
public class PharmacyService implements IPharmacyService{

	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private IAppointmentService appointmentService;
	
	@Autowired
	private IPharmacyGradeService pharmacyGradeService;
	
	@Autowired

	private PharmacyAdminRepository phAdminRepository;

	private PatientRepository patientRepository;
	
	@Autowired
	private IUserInterface userService;
	
	@Autowired
	private EReceiptItemsRepository itemRepository;
	
	@Autowired
	private DrugInPharmacyRepository drugInPharmacyRepository;
	
	@Autowired
	private LoyalityProgramService loyalityService;
	
	@Autowired
	private PharmacyGradeRepository pharmacyGradeRepository;
	
	@Autowired
	private EReceiptRepository eReceiptRepository;
	
	@Autowired
	private EmailService emailService;
	

	
	@Override
	public List<UnspecifiedDTO<PharmacyDTO>> getAllPharmacies() {
		 	
		List<Pharmacy> pharmacies = pharmacyRepository.findAll();
		
		List<UnspecifiedDTO<PharmacyDTO>> pharmacyDTOList = new ArrayList<UnspecifiedDTO<PharmacyDTO>>();
				
		for (Pharmacy currentPharmacy : pharmacies) 
		{	
			double pharmacyGrade= pharmacyGradeService.getAvgGradeForPharmacy(currentPharmacy.getId());
			PharmacyDTO pharmacyDTO= new PharmacyDTO(currentPharmacy,pharmacyGrade);	
			pharmacyDTOList.add(new UnspecifiedDTO<PharmacyDTO>(currentPharmacy.getId(),pharmacyDTO));
		}
		
		return pharmacyDTOList;
	}
	
	
	
	@Override
	public List<UnspecifiedDTO<PharmacyDTO>> getAllPatientSubscribedPharmacies() {
		 	
		
		UUID logedUserId= userService.getLoggedUserId();
		Patient patient = patientRepository.findById(logedUserId).get();
		
		List<Pharmacy> pharmacies =patient.getPharmacies();
		
		List<UnspecifiedDTO<PharmacyDTO>> pharmacyDTOList = new ArrayList<UnspecifiedDTO<PharmacyDTO>>();
				
		for (Pharmacy currentPharmacy : pharmacies) 
		{	
			double pharmacyGrade= pharmacyGradeService.getAvgGradeForPharmacy(currentPharmacy.getId());
			PharmacyDTO pharmacyDTO= new PharmacyDTO(currentPharmacy,pharmacyGrade);	
			pharmacyDTOList.add(new UnspecifiedDTO<PharmacyDTO>(currentPharmacy.getId(),pharmacyDTO));
		}
		
		return pharmacyDTOList;
	}
	
	
	
	@Override
	public List<UnspecifiedDTO<PharmacyDTO>> getSearchedPharmacies(PharmacySearchDTO searchPharmacyRequest) {
		 				
		List<Pharmacy> pharmacies =new ArrayList<Pharmacy>();
		List<UnspecifiedDTO<PharmacyDTO>> pharmacyDTOList = new ArrayList<UnspecifiedDTO<PharmacyDTO>>();
				
		if(!SearchRequestIsEmpty(searchPharmacyRequest)) {
			pharmacies =pharmacyRepository.getAllSearchedPharmacies(searchPharmacyRequest.getName().toLowerCase(),
					searchPharmacyRequest.getStreet().toLowerCase(),searchPharmacyRequest.getCity().toLowerCase(),
					searchPharmacyRequest.getCountry().toLowerCase());
		}else {
			pharmacies = pharmacyRepository.findAll();
		}
		
		for (Pharmacy currentPharmacy : pharmacies) 
		{
				PharmacyDTO pharmacyDTO= new PharmacyDTO(currentPharmacy);				
				pharmacyDTOList.add(new UnspecifiedDTO<PharmacyDTO>(currentPharmacy.getId(),pharmacyDTO));
		}
		
		
		
		return pharmacyDTOList;
	}
	
	private boolean SearchRequestIsEmpty(PharmacySearchDTO searchRequest) {
		
		if(searchRequest.getName().equals("") && searchRequest.getStreet().equals("")
		  && searchRequest.getCity().equals("") &&  searchRequest.getCountry().equals("") ) {
			     return true;
		}
		return false;
		
	}
	
	public UUID createPharmacy(PharmacyDTO pharmacyDTO) {
		
		Pharmacy pharmacy = createPharmacyFromDTO(pharmacyDTO);
		
		pharmacyRepository.save(pharmacy);
		
		return pharmacy.getId();
	}
	
	private Pharmacy createPharmacyFromDTO(PharmacyDTO pharmacyDTO) {
		
		return new Pharmacy(pharmacyDTO.getName(),pharmacyDTO.getAddress().getCity(),pharmacyDTO.getAddress().getStreet(),pharmacyDTO.getAddress().getCountry(),pharmacyDTO.getAddress().getPostCode(),pharmacyDTO.getAddress().getLongitude(),pharmacyDTO.getAddress().getLatitude(),pharmacyDTO.getDescription(), pharmacyDTO.getConsultationPrice());
	}


	
	@Override
	public List<UnspecifiedDTO<PharmacyWithGradeAndPriceDTO>> findAllPharmaciesWhoHaveFreeAppointmentsForPeriodWithGradesAndPrice(Date startDate){
		
		long startTime = startDate.getTime();
		Date endDate= new Date(startTime + 7200000);
		
		System.out.println( "startno vreme " + startDate + "   " +  " Krajnje vremee " + endDate);
		
		List<UnspecifiedDTO<PharmacyWithGradeAndPriceDTO>> pharmaciesDTO= new ArrayList<UnspecifiedDTO<PharmacyWithGradeAndPriceDTO>>();
		List<Pharmacy>  pharmacies= new ArrayList<Pharmacy>();
		
		pharmacies= appointmentService.findAllPharmaciesForAppointmentTypeAndForDateRange(startDate, endDate);
		
		
		for (Pharmacy pharmacy : pharmacies) {
			pharmaciesDTO.add(convertPharmacyToPharmacyWithGradeAndPriceDTO(pharmacy));
		}
		
				
		return pharmaciesDTO;
		
	}
	
	public UnspecifiedDTO<PharmacyWithGradeAndPriceDTO> convertPharmacyToPharmacyWithGradeAndPriceDTO (Pharmacy pharmacy ){
		
		double pharmacyGrade= pharmacyGradeService.getAvgGradeForPharmacy(pharmacy.getId());
		
		return new UnspecifiedDTO<PharmacyWithGradeAndPriceDTO>(pharmacy.getId() , new  PharmacyWithGradeAndPriceDTO(pharmacy.getName(), pharmacy.getAddress(),pharmacyGrade, pharmacy.getConsultationPrice()));
	}
	
	public UnspecifiedDTO<PharmacyWithGradeAndPriceDTO> convertPharmacyToPharmacyWithGradeAndPriceDTO (UUID phId ){
		
		double pharmacyGrade= pharmacyGradeService.getAvgGradeForPharmacy(phId);
		Pharmacy pharmacy= pharmacyRepository.getOne(phId);
		return new UnspecifiedDTO<PharmacyWithGradeAndPriceDTO>(phId , new  PharmacyWithGradeAndPriceDTO(pharmacy.getName(), pharmacy.getAddress(),pharmacyGrade, pharmacy.getConsultationPrice(),pharmacy.getDescription()));
	}
	
	
	
	
	@Override
	public List<UnspecifiedDTO<PharmacyWithGradeAndPriceDTO>> findAllPharmaciesWhoHaveFreeAppointmentsForPeriodWithGradesAndPriceSortByPriceAscending(Date startDate){
		
		List<UnspecifiedDTO<PharmacyWithGradeAndPriceDTO>> pharmaciesDTOSortedByPriceAscending=findAllPharmaciesWhoHaveFreeAppointmentsForPeriodWithGradesAndPrice(startDate);
				
		Collections.sort(pharmaciesDTOSortedByPriceAscending, (pharmacy1, pharmacy2) -> Double.compare(pharmacy1.EntityDTO.getPrice(), pharmacy2.EntityDTO.getPrice()));
		
		return pharmaciesDTOSortedByPriceAscending;
		
	}
	
	
	
	
	@Override
	public List<UnspecifiedDTO<PharmacyWithGradeAndPriceDTO>> findAllPharmaciesWhoHaveFreeAppointmentsForPeriodWithGradesAndPriceSortByPriceDescending(Date startDate){
		
		List<UnspecifiedDTO<PharmacyWithGradeAndPriceDTO>> pharmaciesDTOSortedByPriceDescending=findAllPharmaciesWhoHaveFreeAppointmentsForPeriodWithGradesAndPrice(startDate);
				
		Collections.sort(pharmaciesDTOSortedByPriceDescending, (pharmacy1, pharmacy2) -> Double.compare(pharmacy1.EntityDTO.getPrice(), pharmacy2.EntityDTO.getPrice()));
		Collections.reverse(pharmaciesDTOSortedByPriceDescending);
		
		
		return pharmaciesDTOSortedByPriceDescending;
		
	}
	
	
	
	
	@Override
	public List<UnspecifiedDTO<PharmacyWithGradeAndPriceDTO>> findAllPharmaciesWhoHaveFreeAppointmentsForPeriodWithGradesAndPriceSortByPharmacyGradeAscending(Date startDate){
		
		List<UnspecifiedDTO<PharmacyWithGradeAndPriceDTO>> pharmaciesDTOSortedByPharmacyGradeAscending=findAllPharmaciesWhoHaveFreeAppointmentsForPeriodWithGradesAndPrice(startDate);
				
		Collections.sort(pharmaciesDTOSortedByPharmacyGradeAscending, (pharmacy1, pharmacy2) -> Double.compare(pharmacy1.EntityDTO.getGrade(), pharmacy2.EntityDTO.getGrade()));
		
		
		return pharmaciesDTOSortedByPharmacyGradeAscending;
		
	}
	
	
	@Override
	public void updatePharmacy(UUID phID, PharmacyDTO pharmacyDTO) {
		Pharmacy pharmacy = pharmacyRepository.getOne(phID);		
		System.out.println("ph id je   "+phID);
		pharmacy.setAddress(pharmacyDTO.getAddress());
		pharmacy.setName(pharmacyDTO.getName());
		pharmacy.setDescription(pharmacyDTO.getDescription());
		pharmacy.setConsultationPrice(pharmacyDTO.getConsultationPrice());
		System.out.println(pharmacy);
		pharmacyRepository.save(pharmacy);
		
		Pharmacy pharmacy1 = pharmacyRepository.getOne(phID);	
		System.out.println("sacuvana "+ pharmacy1.getName() );
	}

	
	@Override
	public List<UnspecifiedDTO<PharmacyWithGradeAndPriceDTO>> findAllPharmaciesWhoHaveFreeAppointmentsForPeriodWithGradesAndPriceSortByPharmacyGradeDescending(Date startDate){
		
		List<UnspecifiedDTO<PharmacyWithGradeAndPriceDTO>> pharmaciesDTOSortedByPharmacyGradeDescending=findAllPharmaciesWhoHaveFreeAppointmentsForPeriodWithGradesAndPrice(startDate);
				
		Collections.sort(pharmaciesDTOSortedByPharmacyGradeDescending, (pharmacy1, pharmacy2) -> Double.compare(pharmacy1.EntityDTO.getGrade(), pharmacy2.EntityDTO.getGrade()));
		Collections.reverse(pharmaciesDTOSortedByPharmacyGradeDescending);
		
		
		return pharmaciesDTOSortedByPharmacyGradeDescending;
		
	}
	
	@Override
	public List<UnspecifiedDTO<PharmacyDrugPriceDTO>> getAllPharmaciesWithDrugs(UUID id) {
	
		List<EReceiptItems> items = itemRepository.findAllByEReceiptId(id);
		List<Pharmacy> allPharmacies = pharmacyRepository.findAll();
		List<UnspecifiedDTO<PharmacyDrugPriceDTO>> pharmacies = new ArrayList<UnspecifiedDTO<PharmacyDrugPriceDTO>>();
		
		double price;
		double grade;
		
		for (Pharmacy pha : allPharmacies) {
			
			if(doesPharmacyHaveAllItems(items,pha)!=-1) {
				price = doesPharmacyHaveAllItems(items,pha);
				grade = avgGrade(pha);
				pharmacies.add(PharmacyMapper.MapPharmacyPersistenceToPharmacyDrugPriceIdentifiableDTO(pha, grade, price));
				
				
			}
			
		}
		

		
		
		return pharmacies;
	}
	
	private double doesPharmacyHaveAllItems(List<EReceiptItems> items, Pharmacy pha) {
		
		UUID patientID = userService.getLoggedUserId();
		
		Patient patient = patientRepository.findById(patientID).get();
		
		LoyalityProgramForPatientDTO lp = loyalityService.getLoyalityProgramForPatient(patient);
		
		System.out.println(lp.getDrugDiscount() + "-drug diskaunt");
		
		boolean found;
		
		double price = 0;
		
		for (EReceiptItems i : items) {
			found = false;
			for (DrugInPharmacy drugInPharmacy : drugInPharmacyRepository.findAll()) {
				if(drugInPharmacy.getPharmacy().getId().equals(pha.getId())) {
					if(i.getDrugInstance().getId().equals(drugInPharmacy.getDrug().getId()) && drugInPharmacy.getCount()>=i.getAmount()) {
						found = true;
						price += drugInPharmacy.getPrice() * i.getAmount();
						
					}
				}
				
			}
			if(found==false)
				return -1;
		}
		
		price = ((100 - lp.getDrugDiscount()) * price)/100;
		
		return price;
	}
	
	private double avgGrade(Pharmacy pha) {
		double finalGrade=0;
		int numberOfGrades=0;
		for (PharmacyGrade pGrade : pharmacyGradeRepository.findAll()) {
			
			if(pGrade.getPharmacy().getId().equals(pha.getId())) {
				finalGrade += pGrade.getGrade();
				numberOfGrades++;
			}
		}
		
		if(numberOfGrades==0) {
			return 0;
		}
		
		return finalGrade/numberOfGrades;
	}
	
	@Override
	@Transactional
	public UUID buyDrugsWithQr(PharmacyERecipeDTO pharmacyERecipeDTO) {
		EReceipt eReceipt = eReceiptRepository.findById(pharmacyERecipeDTO.geteRecipeId()).get();
		List<EReceiptItems> items = itemRepository.findAllByEReceiptId(pharmacyERecipeDTO.geteRecipeId());
		
		for (EReceiptItems eReceiptItems : items) {
			DrugInPharmacy drugInPharmacy = drugInPharmacyRepository.findByDrugIdAndPharmacyId(eReceiptItems.getDrugInstance().getId(), pharmacyERecipeDTO.getPharmacyId());
			System.out.println(drugInPharmacy.getDrug().getDrugInstanceName());
			System.out.println(eReceiptItems.getAmount());
			drugInPharmacy.setCount(drugInPharmacy.getCount()-eReceiptItems.getAmount());
			drugInPharmacyRepository.save(drugInPharmacy);
		}
		
		eReceipt.setStatus(EReceiptStatus.PROCESSED);
		eReceipt.setPharmacy(pharmacyRepository.getOne(pharmacyERecipeDTO.getPharmacyId()));
		eReceipt.setPrice(pharmacyERecipeDTO.getPrice());
		eReceiptRepository.save(eReceipt);
		
		UUID patientID = userService.getLoggedUserId();
		Patient patient = patientRepository.findById(patientID).get();
		
		try {
			emailService.sendEmailAfterQRPurchase(patient, items);
		} catch (MailException | InterruptedException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return pharmacyERecipeDTO.geteRecipeId();
	}
	
	@Override
	public List<UnspecifiedDTO<PharmacyDrugPriceDTO>> sortQrPharmaciesByName(UUID id) {
		List<UnspecifiedDTO<PharmacyDrugPriceDTO>> pharmacies = getAllPharmaciesWithDrugs(id);

		Collections.sort(pharmacies, (p1, p2) -> (p1.EntityDTO.getPharmacy().EntityDTO.getName().compareTo(p2.EntityDTO.getPharmacy().EntityDTO.getName())));
		Collections.reverse(pharmacies);

		return pharmacies;
	}
	
	@Override
	public List<UnspecifiedDTO<PharmacyDrugPriceDTO>> sortQrPharmaciesByNameReverse(UUID id) {
		List<UnspecifiedDTO<PharmacyDrugPriceDTO>> pharmacies = getAllPharmaciesWithDrugs(id);

		Collections.sort(pharmacies, (p1, p2) -> (p1.EntityDTO.getPharmacy().EntityDTO.getName().compareTo(p2.EntityDTO.getPharmacy().EntityDTO.getName())));
		

		return pharmacies;
	}
	@Override
	public List<UnspecifiedDTO<PharmacyDrugPriceDTO>> sortQrPharmaciesByPrice(UUID id) {
		List<UnspecifiedDTO<PharmacyDrugPriceDTO>> pharmacies = getAllPharmaciesWithDrugs(id);

		Collections.sort(pharmacies, (p1, p2) -> Double.compare(p1.EntityDTO.getPharmacy().EntityDTO.getConsultationPrice(),p2.EntityDTO.getPharmacy().EntityDTO.getConsultationPrice()));
		Collections.reverse(pharmacies);

		return pharmacies;
	}
	@Override
	public List<UnspecifiedDTO<PharmacyDrugPriceDTO>> sortQrPharmaciesByPriceReverse(UUID id) {
		List<UnspecifiedDTO<PharmacyDrugPriceDTO>> pharmacies = getAllPharmaciesWithDrugs(id);

		Collections.sort(pharmacies, (p1, p2) -> Double.compare(p1.EntityDTO.getPharmacy().EntityDTO.getConsultationPrice(),p2.EntityDTO.getPharmacy().EntityDTO.getConsultationPrice()));
		

		return pharmacies;
	}
	
	@Override
	public List<UnspecifiedDTO<PharmacyDrugPriceDTO>> sortQrPharmaciesByGrade(UUID id) {
		List<UnspecifiedDTO<PharmacyDrugPriceDTO>> pharmacies = getAllPharmaciesWithDrugs(id);

		Collections.sort(pharmacies, (p1, p2) -> Double.compare(p1.EntityDTO.getPharmacy().EntityDTO.getGrade(),p2.EntityDTO.getPharmacy().EntityDTO.getGrade()));
		Collections.reverse(pharmacies);

		return pharmacies;
	}
	@Override
	public List<UnspecifiedDTO<PharmacyDrugPriceDTO>> sortQrPharmaciesByGradeReverse(UUID id) {
		List<UnspecifiedDTO<PharmacyDrugPriceDTO>> pharmacies = getAllPharmaciesWithDrugs(id);

		Collections.sort(pharmacies, (p1, p2) -> Double.compare(p1.EntityDTO.getPharmacy().EntityDTO.getGrade(),p2.EntityDTO.getPharmacy().EntityDTO.getGrade()));
		

		return pharmacies;
	}
	
	@Override
	public List<UnspecifiedDTO<PharmacyDrugPriceDTO>> sortQrPharmaciesByAddress(UUID id) {
		List<UnspecifiedDTO<PharmacyDrugPriceDTO>> pharmacies = getAllPharmaciesWithDrugs(id);

		Collections.sort(pharmacies, (p1, p2) -> (p1.EntityDTO.getPharmacy().EntityDTO.getAddress().getStreet().compareTo(p2.EntityDTO.getPharmacy().EntityDTO.getAddress().getStreet())));
		

		return pharmacies;
	}
	
	@Override
	public List<UnspecifiedDTO<PharmacyDrugPriceDTO>> sortQrPharmaciesByAddressReverse(UUID id) {
		List<UnspecifiedDTO<PharmacyDrugPriceDTO>> pharmacies = getAllPharmaciesWithDrugs(id);

		Collections.sort(pharmacies, (p1, p2) -> (p1.EntityDTO.getPharmacy().EntityDTO.getAddress().getStreet().compareTo(p2.EntityDTO.getPharmacy().EntityDTO.getAddress().getStreet())));
		Collections.reverse(pharmacies);

		return pharmacies;
	}
	
	@Override
	public List<UnspecifiedDTO<AuthorityDTO>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public UnspecifiedDTO<PharmacyDTO> findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public UUID create(PharmacyDTO entityDTO) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void update(PharmacyDTO entityDTO, UUID id) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
}
