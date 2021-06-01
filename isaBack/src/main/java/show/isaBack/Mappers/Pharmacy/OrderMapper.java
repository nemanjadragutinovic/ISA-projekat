package show.isaBack.Mappers.Pharmacy;

import java.util.ArrayList;
import java.util.List;

import show.isaBack.DTO.drugDTO.DrugOrderDTO;
import show.isaBack.DTO.drugDTO.OrderForProviderDTO;
import show.isaBack.model.drugs.DrugOrder;
import show.isaBack.model.drugs.Order;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public class OrderMapper {

	public static UnspecifiedDTO<OrderForProviderDTO> MapOrderInstancePersistenceToOrderInstanceIdentifiableDTO(Order order){
		if(order == null) throw new IllegalArgumentException();
				
		return new UnspecifiedDTO<OrderForProviderDTO>(order.getId(), new OrderForProviderDTO(PharmacyMapper.MapPharmacyPersistenceToPharmacyUnspecifiedDTO(order.getPharmacy()), UserMapper.MapPharmacyAdminPersistenceToPharmacyAdminIdentifiableDTO(order.getPharmacyAdmin()), MapListDrugOrderPersistenceToListDrugOrderIdentifiableDTO(order.getOrder()) ,order.getDate(), order.getOrderStatus()
											, OfferMapper.MapListDrugOrderPersistenceToListDrugOrderIdentifiableDTO(order.getOffers())));
	}
	
	
	public static List<UnspecifiedDTO<DrugOrderDTO>> MapListDrugOrderPersistenceToListDrugOrderIdentifiableDTO(List<DrugOrder> drugs){
		List<UnspecifiedDTO<DrugOrderDTO>> returnDrugs = new ArrayList<UnspecifiedDTO<DrugOrderDTO>>();
		
		drugs.forEach((drug) -> returnDrugs.add(MapListDrugOrderPersistenceToDrugOrderIdentifiableDTO(drug)));
		return returnDrugs;
	}
	
	public static UnspecifiedDTO<DrugOrderDTO> MapListDrugOrderPersistenceToDrugOrderIdentifiableDTO(DrugOrder drugOrder){
		if(drugOrder == null) throw new IllegalArgumentException();
		
		return new UnspecifiedDTO<DrugOrderDTO>(drugOrder.getId(), new DrugOrderDTO(drugOrder.getDrugInstance().getId(), drugOrder.getAmount()));
	}
	
	
}