package show.isaBack.Mappers.Pharmacy;

import java.util.ArrayList;
import java.util.List;

import show.isaBack.DTO.drugDTO.OfferDTO;
import show.isaBack.model.drugs.Offers;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public class OfferMapper {
		
		public static UnspecifiedDTO<OfferDTO> MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(Offers offer){
			if(offer == null) throw new IllegalArgumentException();
					
			return new UnspecifiedDTO<OfferDTO>(offer.getId(), new OfferDTO(offer.getDateToDelivery(), offer.getPrice(), offer.getOfferStatus()));
		}
		
		public static List<UnspecifiedDTO<OfferDTO>> MapListDrugOrderPersistenceToListDrugOrderIdentifiableDTO(List<Offers> offers){
			List<UnspecifiedDTO<OfferDTO>> offer = new ArrayList<UnspecifiedDTO<OfferDTO>>();
			
			offers.forEach((o) -> offer.add(MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(o)));
			return offer;
		}
		
	}
