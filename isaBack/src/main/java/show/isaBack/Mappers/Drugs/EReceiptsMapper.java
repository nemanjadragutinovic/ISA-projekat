package show.isaBack.Mappers.Drugs;

import java.util.ArrayList;
import java.util.List;

import show.isaBack.DTO.drugDTO.DrugEReceiptDTO;
import show.isaBack.DTO.drugDTO.EreceiptDTO;
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
	
	
}
