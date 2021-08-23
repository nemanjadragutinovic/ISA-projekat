package show.isaBack.service.pharmacyService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.aop.AopInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import show.isaBack.DTO.drugDTO.DrugGradeDTO;
import show.isaBack.DTO.pharmacyDTO.PharmacyGradeDTO;
import show.isaBack.model.DrugInstance;
import show.isaBack.model.Patient;
import show.isaBack.model.Pharmacy;
import show.isaBack.model.PharmacyGrade;
import show.isaBack.model.appointment.Appointment;
import show.isaBack.model.appointment.AppointmentStatus;
import show.isaBack.model.appointment.AppointmentType;
import show.isaBack.model.drugs.DrugFeedback;
import show.isaBack.model.drugs.DrugReservation;
import show.isaBack.model.drugs.EReceipt;
import show.isaBack.model.drugs.EReceiptItems;
import show.isaBack.repository.AppointmentRepository.AppointmentRepository;
import show.isaBack.repository.Pharmacy.PharmacyGradeRepository;
import show.isaBack.repository.drugsRepository.DrugReservationRepository;
import show.isaBack.repository.drugsRepository.EReceiptItemsRepository;
import show.isaBack.repository.drugsRepository.EReceiptRepository;
import show.isaBack.repository.pharmacyRepository.PharmacyRepository;
import show.isaBack.repository.userRepository.PatientRepository;
import show.isaBack.serviceInterfaces.IPharmacyGradeService;
import show.isaBack.serviceInterfaces.IUserInterface;

@Service
public class PharmacyGradeService implements IPharmacyGradeService {
	
	@Autowired
	PharmacyGradeRepository pharmacyGradeRepository;
	
	@Autowired
	private IUserInterface userService;

	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private DrugReservationRepository drugReservationRepository;
	
	
	@Autowired
	private EReceiptRepository eReceiptRepository;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Override
	public double getAvgGradeForPharmacy(UUID pharmacyId) {
		
		double avgGrade;
		
		try {
			avgGrade = pharmacyGradeRepository.getAvgGradeForPharmacy(pharmacyId);
		} catch (Exception e) {
			avgGrade = 0.0;
		}
		
		return avgGrade;
	}
	
	
	@Override
	public PharmacyGradeDTO findPatientGradeForPharmacy(UUID pharmacyId) {

		UUID patientId = userService.getLoggedUserId();	
		
		PharmacyGrade pharmacyGrade= pharmacyGradeRepository.findByPatientAndPharmacy(patientId,pharmacyId);
	
		if(pharmacyGrade==null)
			throw new IllegalArgumentException("This user didn't make grade for this drug");
		
		return new PharmacyGradeDTO(pharmacyGrade.getId(),pharmacyGrade.getGrade());
	}
	
	
	@Override
	public void updatePharmacyGrade(PharmacyGradeDTO pharmacyGradeDTO) {
	
		UUID patientId = userService.getLoggedUserId();	
		Patient patient = patientRepository.findById(patientId).get();

		PharmacyGrade pharmacyGrade= pharmacyGradeRepository.findByPatientAndPharmacy(patientId,pharmacyGradeDTO.getPharmacyId());
	
		pharmacyGrade.setGrade(pharmacyGradeDTO.getGrade());
		pharmacyGrade.setDate(new Date());
		
		pharmacyGradeRepository.save(pharmacyGrade);
		
		
	}
	
	
	@Override
	public void createPharmacyGrade(PharmacyGradeDTO pharmacyGradeDTO) {
	
		UUID patientId = userService.getLoggedUserId();	
		Patient patient = patientRepository.findById(patientId).get();
		
		canPatientCreateGradeForPharmacy(patient, pharmacyGradeDTO);

		Pharmacy pharmacy = pharmacyRepository.findById(pharmacyGradeDTO.getPharmacyId()).get();
			
		PharmacyGrade pharmacyGrade= new PharmacyGrade(pharmacy,patient, pharmacyGradeDTO.getGrade(),new Date());
		
		pharmacyGradeRepository.save(pharmacyGrade);
		
		
	}
	
	
	public void canPatientCreateGradeForPharmacy(Patient patient,PharmacyGradeDTO pharmacyGradeDTO) {
		List<DrugReservation> drugsReservation=
			drugReservationRepository.findAllProccessedDrugsForPatientAndPharmacyId(patient.getId(),pharmacyGradeDTO.getPharmacyId());
			
		List<EReceipt> eReceipts=
				eReceiptRepository.findAllEReceiptsByPatiendAndPharmacy(pharmacyGradeDTO.getPharmacyId(),patient.getId());
		
		List<Appointment> appointmentsConsulations=
				appointmentRepository.findAllFinishedAppointmentsByPatiendAndPharmacy(pharmacyGradeDTO.getPharmacyId(),patient.getId(),AppointmentType.CONSULTATION,AppointmentStatus.FINISHED);
		
		List<Appointment> appointmentsExaminations=
				appointmentRepository.findAllFinishedAppointmentsByPatiendAndPharmacy(pharmacyGradeDTO.getPharmacyId(),patient.getId(),AppointmentType.EXAMINATION,AppointmentStatus.FINISHED);
		
		if(drugsReservation.size()==0 && eReceipts.size()==0 && appointmentsConsulations.size()==0 && appointmentsExaminations.size()==0) {
			
			 throw new IllegalArgumentException("You can't create a grade for pharmacy, because you don't have any  drug reservation and drug download in the past "
			 		+ "or you don't have anything about e-receipts in this pharmacy or you don't have any finished appointment in this pharmacy  ");
		}
		
		
	}
	
	
}
