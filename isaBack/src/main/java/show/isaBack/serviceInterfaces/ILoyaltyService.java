package show.isaBack.serviceInterfaces;

import java.util.UUID;

import show.isaBack.DTO.userDTO.LoyalityProgramForPatientDTO;
import show.isaBack.DTO.userDTO.LoyaltyProgramDTO;
import show.isaBack.model.LoyalityProgram;
import show.isaBack.model.Patient;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public interface ILoyaltyService extends IService<LoyaltyProgramDTO, UnspecifiedDTO<LoyaltyProgramDTO>> {
	
	public UnspecifiedDTO<LoyaltyProgramDTO> findAllLoyalityPrograms(UUID id);
	public void update(LoyaltyProgramDTO entityDTO);
	
	
	public LoyalityProgramForPatientDTO getLoyalityProgramForPatient(Patient patient);
	public double getDiscountPriceForDrugForPatient(UUID patientId, double standarPrice);
	public double getDiscountPriceForExaminationAppointmentForPatient(UUID patientId, double standardPrice);
	public double getDiscountPriceForConsultationAppointmentForPatient(UUID patientId, double standardPrice);
	LoyalityProgram get();
}
