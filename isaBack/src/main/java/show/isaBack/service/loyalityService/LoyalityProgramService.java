package show.isaBack.service.loyalityService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import show.isaBack.DTO.pharmacyDTO.PharmacyDTO;
import show.isaBack.DTO.userDTO.AuthorityDTO;
import show.isaBack.DTO.userDTO.LoyalityProgramForPatientDTO;
import show.isaBack.DTO.userDTO.LoyaltyProgramDTO;
import show.isaBack.model.LoyalityCategory;
import show.isaBack.model.LoyalityProgram;
import show.isaBack.model.Patient;
import show.isaBack.model.Pharmacy;
import show.isaBack.repository.userRepository.LoyalityProgramRepository;
import show.isaBack.repository.userRepository.PatientRepository;
import show.isaBack.serviceInterfaces.ILoyaltyService;
import show.isaBack.serviceInterfaces.IUserInterface;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

@Service
public class LoyalityProgramService implements ILoyaltyService {
	
	
	@Autowired
	private LoyalityProgramRepository loyaltyProgramRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	private final UUID ID_FOR_LOYALITY_PROGRAM = UUID.fromString("8c834328-9b5a-42c2-9e04-a1acc75f881d");
	
	
	@Override
	public UnspecifiedDTO<LoyaltyProgramDTO> findAllLoyalityPrograms(UUID id) {
		 	
		LoyalityProgram program = loyaltyProgramRepository.getOne(id);
		
		UnspecifiedDTO<LoyaltyProgramDTO> loyalityDTO = new UnspecifiedDTO<LoyaltyProgramDTO>(program.getId(), new LoyaltyProgramDTO(program.getPointsForAppointment(), program.getPointsForConsulting(),
				program.getPointsToEnterRegularCathegory(), program.getPointsToEnterLoyalCathegory(), program.getPointsToEnterVipCathegory(), program.getAppointmentDiscountRegular(), program.getDrugDiscountRegular(),
				program.getConsultationDiscountRegular(), program.getAppointmentDiscountLoyal(), program.getDrugDiscountLoyal(), program.getConsultationDiscountLoyal(),
				program.getAppointmentDiscountVip(), program.getDrugDiscountVip(), program.getConsultationDiscountVip()));
				
		return loyalityDTO;
	}
	
	@Override
	public void update(LoyaltyProgramDTO entityDTO) {

		try {
			
			System.out.println(entityDTO.getDrugDiscountVip());
			
			LoyalityProgram loyaltyProgram = loyaltyProgramRepository.getOne(ID_FOR_LOYALITY_PROGRAM);
			System.out.println(loyaltyProgram.getDrugDiscountVip());
			loyaltyProgram.setPointsForAppointment(entityDTO.getPointsForAppointment());
			loyaltyProgram.setPointsForConsulting(entityDTO.getPointsForConsulting());
			loyaltyProgram.setPointsToEnterRegularCathegory(entityDTO.getPointsToEnterRegularCathegory());
			loyaltyProgram.setPointsToEnterLoyalCathegory(entityDTO.getPointsToEnterLoyalCathegory());
			loyaltyProgram.setPointsToEnterVipCathegory(entityDTO.getPointsToEnterVipCathegory());
			
			loyaltyProgram.setAppointmentDiscountRegular(entityDTO.getAppointmentDiscountRegular());
			loyaltyProgram.setDrugDiscountRegular(entityDTO.getDrugDiscountRegular());
			loyaltyProgram.setConsultationDiscountRegular(entityDTO.getConsultationDiscountRegular());

			loyaltyProgram.setAppointmentDiscountLoyal(entityDTO.getAppointmentDiscountLoyal());
			loyaltyProgram.setDrugDiscountLoyal(entityDTO.getDrugDiscountLoyal());
			loyaltyProgram.setConsultationDiscountLoyal(entityDTO.getConsultationDiscountLoyal());
			

			loyaltyProgram.setAppointmentDiscountVip(entityDTO.getAppointmentDiscountVip());
			loyaltyProgram.setDrugDiscountVip(entityDTO.getDrugDiscountVip());
			loyaltyProgram.setConsultationDiscountVip(entityDTO.getConsultationDiscountVip());
			
			System.out.println(loyaltyProgram.getDrugDiscountVip()+"nemanjaaa");
		//	System.out.println();
			
			loyaltyProgramRepository.save(loyaltyProgram);
			
		}catch (Exception e) {
		}
	}

	
	
	@Override
	public LoyalityProgramForPatientDTO getLoyalityProgramForPatient(Patient patient) {
		
		LoyalityProgram loyalityProgram= loyaltyProgramRepository.findById(ID_FOR_LOYALITY_PROGRAM).get();
		LoyalityProgramForPatientDTO loyalityProgramForPatientDTO= createPatientLoyalityProgram(loyalityProgram,patient );
		
		return loyalityProgramForPatientDTO;
		
	}
	
	
	public LoyalityProgramForPatientDTO createPatientLoyalityProgram( LoyalityProgram loyalityProgram , Patient patient) {
		
		if(patient.getPoints()  < loyalityProgram.getPointsToEnterLoyalCathegory()) {
			return new LoyalityProgramForPatientDTO(LoyalityCategory.REGULAR, loyalityProgram.getAppointmentDiscountRegular(), loyalityProgram.getConsultationDiscountRegular(), loyalityProgram.getDrugDiscountRegular());           
		}else if(patient.getPoints() >= loyalityProgram.getPointsToEnterLoyalCathegory() && patient.getPoints() < loyalityProgram.getPointsToEnterVipCathegory()) {
			return new LoyalityProgramForPatientDTO(LoyalityCategory.LOYAL, loyalityProgram.getAppointmentDiscountLoyal(), loyalityProgram.getConsultationDiscountLoyal(), loyalityProgram.getDrugDiscountLoyal());
		}else{
			return new LoyalityProgramForPatientDTO(LoyalityCategory.VIP, loyalityProgram.getAppointmentDiscountVip(), loyalityProgram.getConsultationDiscountVip(), loyalityProgram.getDrugDiscountVip());  
		}
		
	}
	
	
	@Override
	public double getDiscountPriceForDrugForPatient(UUID patientId, double standardPrice) {
		
		Patient patient = patientRepository.getOne(patientId);
		LoyalityProgramForPatientDTO loyalityProgramForPatientDTO = getLoyalityProgramForPatient(patient);
		double discountPrice= ((100 - loyalityProgramForPatientDTO.getDrugDiscount()) / 100.0 ) * standardPrice;
		
		return discountPrice;
	}
	
	
	@Override
	public double getDiscountPriceForExaminationAppointmentForPatient(UUID patientId, double standardPrice) {
		
		Patient patient = patientRepository.getOne(patientId);
		LoyalityProgramForPatientDTO loyalityProgramForPatientDTO = getLoyalityProgramForPatient(patient);
		double discountPrice= ((100 - loyalityProgramForPatientDTO.getExaminationDiscount()) / 100.0 ) * standardPrice;
		
		return discountPrice;
	}
	
	@Override
	public double getDiscountPriceForConsultationAppointmentForPatient(UUID patientId, double standardPrice) {
		
		Patient patient = patientRepository.getOne(patientId);
		LoyalityProgramForPatientDTO loyalityProgramForPatientDTO = getLoyalityProgramForPatient(patient);
		double discountPrice= ((100 - loyalityProgramForPatientDTO.getConsultationDiscount()) / 100.0 ) * standardPrice;
		
		return discountPrice;
	}
	
	@Override
	public List<UnspecifiedDTO<AuthorityDTO>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnspecifiedDTO<LoyaltyProgramDTO> findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID create(LoyaltyProgramDTO entityDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(LoyaltyProgramDTO entityDTO, UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LoyalityProgram get() {
		// TODO Auto-generated method stub
		return null;
	}

}
