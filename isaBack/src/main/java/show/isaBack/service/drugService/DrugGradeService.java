package show.isaBack.service.drugService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import show.isaBack.DTO.drugDTO.DrugGradeDTO;
import show.isaBack.DTO.userDTO.EmployeeForGradeDTO;
import show.isaBack.DTO.userDTO.EmployeeForGradeRequestDTO;
import show.isaBack.interfaceRepository.drugRepository.DrugInstanceRepository;
import show.isaBack.model.DrugInstance;
import show.isaBack.model.Patient;
import show.isaBack.model.User;
import show.isaBack.model.UserCharacteristics.EmployeeGrade;
import show.isaBack.model.appointment.Appointment;
import show.isaBack.model.drugs.DrugFeedback;
import show.isaBack.model.drugs.DrugReservation;
import show.isaBack.model.drugs.EReceiptItems;
import show.isaBack.repository.drugsRepository.DrugFeedbackRepository;
import show.isaBack.repository.drugsRepository.DrugReservationRepository;
import show.isaBack.repository.drugsRepository.EReceiptItemsRepository;
import show.isaBack.repository.userRepository.PatientRepository;
import show.isaBack.serviceInterfaces.IDrugGradeService;
import show.isaBack.serviceInterfaces.IUserInterface;

@Service
public class DrugGradeService implements IDrugGradeService {

	
	@Autowired
	private DrugFeedbackRepository drugFeedbackRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private IUserInterface userService;
	
	@Autowired
	private DrugReservationRepository drugReservationRepository;
	
	@Autowired
	private EReceiptItemsRepository eReceiptItemsRepository;
	
	@Autowired
	private  DrugInstanceRepository drugInstanceRepository;
	
	@Override
	public DrugGradeDTO findPatientGradeForDrug(UUID drugId) {

		UUID patientId = userService.getLoggedUserId();	
		
		DrugFeedback drugFeedback= drugFeedbackRepository.findByPatientAndDrug(patientId,drugId);
	
		if(drugFeedback==null)
			throw new IllegalArgumentException("This user didn't make grade for this drug");
		
		return new DrugGradeDTO(drugFeedback.getId(),drugFeedback.getGrade());
	}
	
	
	
	
	
	@Override
	public void createDrugGrade(DrugGradeDTO drugGradeDTO) {
		
		UUID patientId = userService.getLoggedUserId();	
		Patient patient = patientRepository.findById(patientId).get();
		
		canPatientCreateGradeForDrug(patient, drugGradeDTO);

		DrugInstance drugInstance = drugInstanceRepository.findById(drugGradeDTO.getDrugId()).get();
			
		DrugFeedback drugFeedback= new DrugFeedback(drugInstance,patient, new Date(),drugGradeDTO.getGrade());
		
		drugFeedbackRepository.save(drugFeedback);

		
	}
	
	public void canPatientCreateGradeForDrug(Patient patient,DrugGradeDTO drugGradeDTO) {
		List<DrugReservation> drugsReservation=
			drugReservationRepository.findAllProccessedDrugsForPatientAndDrugId(patient.getId(),drugGradeDTO.getDrugId());
			
		List<EReceiptItems> eReceipts=
				eReceiptItemsRepository.findAllEReceiptsByPatiendAndDrug(drugGradeDTO.getDrugId(),patient.getId());
		
		if(drugsReservation.size()==0 && eReceipts.size()==0) {
			 throw new IllegalArgumentException("You can't create a grade for drug, because you don't have any  drug reservation and any  drug download "
			 		+ "in the past or you don't have anything about e-receipts for this drug! ");
		}
		
		
	}
	
	
	@Override
	public void updateDrugGrade(DrugGradeDTO drugGradeDTO) {
	
		UUID patientId = userService.getLoggedUserId();	
		Patient patient = patientRepository.findById(patientId).get();

		DrugFeedback drugFeedback= drugFeedbackRepository.findByPatientAndDrug(patientId,drugGradeDTO.getDrugId());
	
		drugFeedback.setGrade(drugGradeDTO.getGrade());
		drugFeedback.setDate(new Date());
		
		drugFeedbackRepository.save(drugFeedback);

		
	}
	
	
}
