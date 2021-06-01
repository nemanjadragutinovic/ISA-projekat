package show.isaBack.Mappers.Drugs;

import show.isaBack.DTO.drugDTO.DrugInstanceDTO;
import show.isaBack.model.DrugInstance;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public class DrugInstanceMapper {

	public static UnspecifiedDTO<DrugInstanceDTO> MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(DrugInstance drug){
		if(drug == null) throw new IllegalArgumentException();
				
		return new UnspecifiedDTO<DrugInstanceDTO>(drug.getId(), new DrugInstanceDTO(drug.getName(), drug.getProducerName(), drug.getFabricCode(),drug.getDrugInstanceName(),
																					  drug.getDrugFormat(), drug.getQuantity(), drug.getSideEffects(), drug.getRecommendedAmount(),
																					  drug.getLoyalityPoints(), drug.isOnReciept(), drug.getDrugKind()));
	}
	
}
