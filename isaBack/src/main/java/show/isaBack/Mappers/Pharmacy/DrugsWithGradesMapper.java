package show.isaBack.Mappers.Pharmacy;

import java.util.ArrayList;
import java.util.List;

import show.isaBack.DTO.drugDTO.DrugWithPriceDTO;
import show.isaBack.DTO.drugDTO.DrugsWithGradesDTO;
import show.isaBack.DTO.drugDTO.ManufacturerDTO;
import show.isaBack.DTO.drugDTO.ReplaceDrugDTO;
import show.isaBack.model.DrugInstance;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public class DrugsWithGradesMapper {
	public static UnspecifiedDTO<DrugsWithGradesDTO> MapDrugInstancePersistenceToDrugInstanceIdentifiableDTO(DrugInstance drug, double grade){
		if(drug == null) throw new IllegalArgumentException();
				
		return new UnspecifiedDTO<DrugsWithGradesDTO>(drug.getId(), new DrugsWithGradesDTO(drug.getName(), drug.getProducerName(), drug.getFabricCode(), drug.getDrugInstanceName(),
																					  ManufacturerMapper.MapManufacturerPersistenceToManufacturerIdentifiableDTO(drug.getManufacturer()),
																					  drug.getDrugFormat(), drug.getQuantity(), drug.getSideEffects(), drug.getRecommendedAmount(),
																					  MapListReplaceDugsPersistenceToListReplaceDrugsIdentifiableDTO(drug.getReplacingDrugs()),
																					  AllergenMapper.MapAllergenPersistenceListToAllergenIdentifiableDTOList(drug.getAllergens()),
																					  IngredientMapper.MapIngredientPersistenceListToIngredientIdentifiableDTOList(drug.getIngredients()),
																					  drug.getLoyalityPoints(), drug.isOnReciept(), drug.getDrugKind(), grade));
	}
	
	public static List<UnspecifiedDTO<ReplaceDrugDTO>> MapListReplaceDugsPersistenceToListReplaceDrugsIdentifiableDTO(List<DrugInstance> drugs){
		List<UnspecifiedDTO<ReplaceDrugDTO>> returnDrugs = new ArrayList<UnspecifiedDTO<ReplaceDrugDTO>>();
		
		drugs.forEach((drug) -> returnDrugs.add(MapDrugInstancePersistenceToReplaceDugIdentifiableDTO(drug)));
		return returnDrugs;
	}
	
	public static UnspecifiedDTO<ReplaceDrugDTO> MapDrugInstancePersistenceToReplaceDugIdentifiableDTO(DrugInstance drug){
		if(drug == null) throw new IllegalArgumentException();
		
		return new UnspecifiedDTO<ReplaceDrugDTO>(drug.getId(), new ReplaceDrugDTO(drug.getName(), drug.getFabricCode(), drug.getDrugInstanceName(), new ManufacturerDTO(drug.getManufacturer().getName())));
	}
	
	public static UnspecifiedDTO<DrugWithPriceDTO> MapDrugInstancePersistenceToDrugWithPriceDTO(DrugInstance drug,double grade,double price){
		if(drug == null) throw new IllegalArgumentException();
		
		return new UnspecifiedDTO<DrugWithPriceDTO>(drug.getId(),new DrugWithPriceDTO(drug.getName(),drug.getManufacturer().getName(),drug.getDrugInstanceName(),drug.getDrugFormat(),drug.getQuantity(),drug.isOnReciept(),grade,price));
	}
	
	
}
