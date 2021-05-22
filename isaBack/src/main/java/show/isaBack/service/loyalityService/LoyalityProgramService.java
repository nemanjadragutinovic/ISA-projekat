package show.isaBack.service.loyalityService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import show.isaBack.DTO.pharmacyDTO.PharmacyDTO;
import show.isaBack.DTO.userDTO.AuthorityDTO;
import show.isaBack.DTO.userDTO.LoyaltyProgramDTO;
import show.isaBack.model.LoyalityProgram;
import show.isaBack.model.Pharmacy;
import show.isaBack.repository.userRepository.LoyalityProgramRepository;
import show.isaBack.serviceInterfaces.ILoyaltyService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

@Service
public class LoyalityProgramService implements ILoyaltyService {
	
	
	@Autowired
	private LoyalityProgramRepository loyaltyProgramRepository;
	
	private final UUID LOYALITY_PROGRAM_ID = UUID.fromString("8c834328-9b5a-42c2-9e04-a1acc75f881d");
	
	
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
			
			LoyalityProgram loyaltyProgram = loyaltyProgramRepository.getOne(LOYALITY_PROGRAM_ID);
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

}
