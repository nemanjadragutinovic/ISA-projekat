package show.isaBack.serviceInterfaces;

import java.util.UUID;

import show.isaBack.DTO.userDTO.LoyaltyProgramDTO;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public interface ILoyaltyService extends IService<LoyaltyProgramDTO, UnspecifiedDTO<LoyaltyProgramDTO>> {
	
	public UnspecifiedDTO<LoyaltyProgramDTO> findAllLoyalityPrograms(UUID id);
	public void update(LoyaltyProgramDTO entityDTO);

}
