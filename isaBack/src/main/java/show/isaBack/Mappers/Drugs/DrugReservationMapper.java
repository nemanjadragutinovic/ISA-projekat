package show.isaBack.Mappers.Drugs;

import java.util.ArrayList;
import java.util.List;


import show.isaBack.DTO.drugDTO.DrugReservationResponseDTO;
import show.isaBack.Mappers.Pharmacy.PharmacyMapper;
import show.isaBack.model.drugs.DrugReservation;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public class DrugReservationMapper {

	
	public static List<UnspecifiedDTO<DrugReservationResponseDTO>> mapDrugsReservationToDrugsReseervationDTO(List<DrugReservation> drugReservations){
		
		List<UnspecifiedDTO<DrugReservationResponseDTO>> drugReservationListDTO= new ArrayList<UnspecifiedDTO<DrugReservationResponseDTO>>();
		
		for (DrugReservation drugReservation : drugReservations) {
			drugReservationListDTO.add(mappCurrentDrugReservationToDTO(drugReservation));
		}
		
		return drugReservationListDTO;
	}
	
	public static UnspecifiedDTO<DrugReservationResponseDTO> mappCurrentDrugReservationToDTO(DrugReservation drugReservation){
		
		return new UnspecifiedDTO<DrugReservationResponseDTO>(drugReservation.getId(),
				   new DrugReservationResponseDTO(PharmacyMapper.MapPharmacyPersistenceToPharmacyUnspecifiedDTO(drugReservation.getPharmacy()),
				   DrugInstanceMapper.MapDrugInstancePersistenceToDrugInstanceUnspecifiedDTO(drugReservation.getDrugInstance()),
				   drugReservation.getCount(), drugReservation.getStartDate(), drugReservation.getEndDate(),
				   drugReservation.getPriceForOneDrug(), drugReservation.getDrugReservationStatus()));
	}
	
}
