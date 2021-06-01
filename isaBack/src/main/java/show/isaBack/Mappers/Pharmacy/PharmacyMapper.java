package show.isaBack.Mappers.Pharmacy;

import show.isaBack.DTO.drugDTO.PharmacyDrugPriceDTO;
import show.isaBack.DTO.pharmacyDTO.PharmacyDTO;
import show.isaBack.model.Pharmacy;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public class PharmacyMapper {

	public static UnspecifiedDTO<PharmacyDTO> MapPharmacyPersistenceToPharmacyIdentifiableDTO(Pharmacy pharmacy){
		if(pharmacy == null) throw new IllegalArgumentException();

		return new UnspecifiedDTO<PharmacyDTO>(pharmacy.getId(), new PharmacyDTO(pharmacy.getName(), pharmacy.getAddress(), pharmacy.getDescription(),pharmacy.getConsultationPrice()));

	}
	
	public static UnspecifiedDTO<PharmacyDrugPriceDTO> MapPharmacyPersistenceToPharmacyDrugPriceIdentifiableDTO(Pharmacy pharmacy, double grade, double price){
		if(pharmacy == null) throw new IllegalArgumentException();

		return new UnspecifiedDTO<PharmacyDrugPriceDTO>(pharmacy.getId(), new PharmacyDrugPriceDTO(MapPharmacyPersistenceToPharmacyIdentifiableDTO(pharmacy),grade, price));

	}
	
	
}