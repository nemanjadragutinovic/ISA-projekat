package show.isaBack.service.drugService;

import java.util.Date;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import show.isaBack.DTO.drugDTO.EmployeeReservationDrugDTO;
import show.isaBack.emailService.EmailService;
import show.isaBack.interfaceRepository.drugRepository.DrugInstanceRepository;
import show.isaBack.interfaceRepository.drugRepository.DrugRepository;
import show.isaBack.interfaceRepository.drugRepository.IngredientRepository;
import show.isaBack.interfaceRepository.drugRepository.ManufacturerRepository;
import show.isaBack.model.ActionPromotion;
import show.isaBack.model.ActionType;
import show.isaBack.model.DrugInstance;
import show.isaBack.model.Patient;
import show.isaBack.model.Pharmacy;
import show.isaBack.model.User;
import show.isaBack.model.UserCharacteristics.UserType;
import show.isaBack.model.drugs.DrugInPharmacy;
import show.isaBack.model.drugs.DrugReservation;
import show.isaBack.repository.drugsRepository.DrugFeedbackRepository;
import show.isaBack.repository.drugsRepository.DrugInPharmacyRepository;
import show.isaBack.repository.drugsRepository.DrugPriceListRepository;
import show.isaBack.repository.drugsRepository.DrugRequestRepository;
import show.isaBack.repository.drugsRepository.DrugReservationRepository;
import show.isaBack.repository.drugsRepository.EReceiptItemsRepository;
import show.isaBack.repository.drugsRepository.EReceiptRepository;
import show.isaBack.repository.pharmacyRepository.ActionPromotionRepository;
import show.isaBack.repository.pharmacyRepository.PharmacyRepository;
import show.isaBack.repository.userRepository.PatientRepository;
import show.isaBack.repository.userRepository.PharmacistRepository;
import show.isaBack.repository.userRepository.UserRepository;
import show.isaBack.serviceInterfaces.IDrugReservationService;
import show.isaBack.serviceInterfaces.ILoyaltyService;
import show.isaBack.serviceInterfaces.IUserInterface;



@Service
public class DrugReservationService implements IDrugReservationService {
	@Autowired
	private DrugRepository drugRepository;
	
	@Autowired
	private DrugInstanceRepository drugInstanceRepository;
	
	@Autowired
	private DrugFeedbackRepository drugFeedbackRepository;
	
	@Autowired
	private ManufacturerRepository manufacturerRepository;
	
	@Autowired
	private ActionPromotionRepository actionPromotionRepository;
	
	@Autowired
	private IngredientRepository ingredientRepository;
	
	@Autowired
	private EReceiptRepository eReceiptRepository;
	
	@Autowired
	private DrugPriceListRepository drugPriceListRepository;
	
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
	private PharmacistRepository pharmacistRepository;

	@Autowired
	private Environment env;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DrugRequestRepository drugRequestRepository;
	
	
	@Autowired
	private EReceiptItemsRepository eReceiptItemsRepository;
	
	
	@Override
	@Transactional
	public UUID reserveDrugAsEmployee(EmployeeReservationDrugDTO employeeReservationDrugDTO) {
		//TODO: validation and exceptions
		UUID userId = userService.getLoggedUserId();
		Patient patient = patientRepository.getOne(employeeReservationDrugDTO.getPatientId());
		User user = userRepository.getOne(userId);
		Pharmacy pharmacy;
		if(user.getUserType() == UserType.DERMATOLOGIST)
			pharmacy = userService.getPharmacyForLoggedDermatologist();
		else
			pharmacy = pharmacistRepository.getOne(userId).getPharmacy();
		DrugInstance drugInstance = drugInstanceRepository.getOne(employeeReservationDrugDTO.getDrugInstanceId());
		int count = employeeReservationDrugDTO.getAmount();
		Integer price = drugPriceListRepository.findCurrentDrugPrice(drugInstance.getId(), pharmacy.getId());

		double priceDiscounted = loyalityProgramService.getDiscountPriceForDrugForPatient(patient.getId(),price);
		ActionPromotion action = actionPromotionRepository.findCurrentActionAndPromotionForPharmacyForActionType(pharmacy.getId(), ActionType.DRUGDISCOUNT);
		if(action != null) {
			priceDiscounted -= (action.getDiscount()/ 100.0) * priceDiscounted;
		}
			
		long drugReservationDuration = Integer.parseInt(env.getProperty("drug_reservation_duration"));
		long currentTime = new Date().getTime();
		Date endDate = new Date(currentTime + (1000 * 60 * 60 * 24 * drugReservationDuration));
		DrugReservation drugReservation = new DrugReservation(patient,pharmacy,drugInstance,count, endDate, priceDiscounted);
		CanReserveDrug(drugReservation, patient);
		reduceAmountOfReservedDrug(drugInstance.getId(), pharmacy.getId(), count);
		drugReservationRepository.save(drugReservation);

		try {
			emailService.sendNotificationForDrugReservation(drugReservation);
		} catch (MailException e) {
			e.printStackTrace();
		} //catch (InterruptedException e) {
			//e.printStackTrace();
		//}
		catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return drugReservation.getId();
	}
	
	@Override
	public void reduceAmountOfReservedDrug(UUID drugId, UUID pharmacyId, int count) {
		DrugInPharmacy drugInPharmacy = drugInPharmacyRepository.findByDrugIdAndPharmacyId(drugId, pharmacyId);
		drugInPharmacy.reduceCount(count);
		drugInPharmacyRepository.save(drugInPharmacy);	
	}
	
	private void CanReserveDrug(DrugReservation drugReservation,Patient patient) {
		
		if(!(drugReservation.getEndDate().compareTo(new Date()) > 0 && drugReservation.getEndDate().compareTo(drugReservation.getStartDate()) > 0))
			throw new IllegalArgumentException("Invalid date argument.");

		if(!(patient.getPenalty() < Integer.parseInt(env.getProperty("max_penalty_count"))))
			throw new AuthorizationServiceException("Too many penalty points.");
		
	}
}
