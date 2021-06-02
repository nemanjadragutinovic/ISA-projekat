package show.isaBack.Mappers.Drugs;

import java.util.ArrayList;
import java.util.List;

import show.isaBack.DTO.drugDTO.DrugEReceiptDTO;
import show.isaBack.DTO.drugDTO.DrugWithEreceiptsDTO;
import show.isaBack.DTO.drugDTO.EreceiptDTO;
import show.isaBack.DTO.drugDTO.EreceiptWithPharmacyDTO;
import show.isaBack.model.DrugInstance;
import show.isaBack.model.drugs.EReceipt;
import show.isaBack.model.drugs.EReceiptItems;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;



public class EReceiptsMapper {

	
	public static UnspecifiedDTO<EreceiptDTO> mapEreceiptToEreceiptDTO(EReceipt eReceipt, List<UnspecifiedDTO<DrugEReceiptDTO>> eReceiptItems){
		
		if(eReceipt == null) 
		throw new IllegalArgumentException();
		
		return new UnspecifiedDTO<EreceiptDTO>(eReceipt.getId(), 
				new EreceiptDTO(eReceipt.getCreationDate(),eReceipt.getPrice(),eReceipt.getStatus(),eReceiptItems));
		
	}
	
	
	
	
	public static List<UnspecifiedDTO<DrugEReceiptDTO>> mapEReceiptItemsToEreceiptsDrugDTO(List<EReceiptItems> eReceiptItems){
		
		List<UnspecifiedDTO<DrugEReceiptDTO>> eReceptsDrugsDTO = new ArrayList<UnspecifiedDTO<DrugEReceiptDTO>>();
			
		for (EReceiptItems eReceiptItem : eReceiptItems) {
			
			eReceptsDrugsDTO.add(new UnspecifiedDTO<DrugEReceiptDTO>(eReceiptItem.getDrugInstance().getId(),
					   new DrugEReceiptDTO(eReceiptItem.getDrugInstance().getDrugInstanceName(),eReceiptItem.getAmount())));
			
		}
		
		return eReceptsDrugsDTO;
	}
	
	public static List<UnspecifiedDTO<EreceiptWithPharmacyDTO>> mapEReceiptItemsToEreceiptsWithPharmacyDrugDTO(List<EReceiptItems> eReceiptItems){
		
		List<UnspecifiedDTO<EreceiptWithPharmacyDTO>> eReceptsWithPharmacyDrugsDTO = new ArrayList<UnspecifiedDTO<EreceiptWithPharmacyDTO>>();
			
		for (EReceiptItems eReceiptItem : eReceiptItems) {
			
			eReceptsWithPharmacyDrugsDTO.add(new UnspecifiedDTO<EreceiptWithPharmacyDTO>(eReceiptItem.geteReceipt().getId(),
					   new EreceiptWithPharmacyDTO(eReceiptItem.geteReceipt().getCreationDate(),eReceiptItem.geteReceipt().getPrice(),
							   eReceiptItem.geteReceipt().getStatus(),eReceiptItem.geteReceipt().getPharmacy().getName())));
			
		}
		
		return eReceptsWithPharmacyDrugsDTO;
	}
	
	
	
	public static UnspecifiedDTO<DrugWithEreceiptsDTO> mapProccessedDrugWithEreceiptsForHimToDrugWithEreceiptsDTO(DrugInstance drugInstance, List<UnspecifiedDTO<EreceiptWithPharmacyDTO>> eReceiptItemsForDrug){           
		
		if(drugInstance == null) 
		throw new IllegalArgumentException();
		
		return new UnspecifiedDTO<DrugWithEreceiptsDTO>(drugInstance.getId(), 
				new DrugWithEreceiptsDTO(drugInstance.getName(),drugInstance.getDrugFormat(),eReceiptItemsForDrug));
		
	}
	
	
}
