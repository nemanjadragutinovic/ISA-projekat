package show.isaBack.serviceInterfaces;

import java.util.List;
import java.util.UUID;

import show.isaBack.DTO.drugDTO.OfferDTO;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public interface IOfferService extends IService<OfferDTO, UnspecifiedDTO<OfferDTO>>{
	
	public boolean checkIfHasDrugs(UUID id) ;
	public List<UnspecifiedDTO<OfferDTO>> findAllOffers() ;

}
