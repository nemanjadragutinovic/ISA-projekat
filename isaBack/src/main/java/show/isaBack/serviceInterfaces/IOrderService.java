package show.isaBack.serviceInterfaces;

import java.util.List;
import java.util.UUID;

import show.isaBack.DTO.drugDTO.DrugOrderDTO;
import show.isaBack.DTO.drugDTO.EditOrderDTO;
import show.isaBack.DTO.drugDTO.NewOrderDTO;
import show.isaBack.DTO.drugDTO.OrderForPhAdminDTO;
import show.isaBack.DTO.drugDTO.OrderForProviderDTO;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public interface IOrderService {
	
	public  List<UnspecifiedDTO<OrderForProviderDTO>> findAllOrdersForSupplier();

	UUID createNewOreder(NewOrderDTO newOrderDTO);

	List<UnspecifiedDTO<OrderForPhAdminDTO>> getAllOrdersForPharmacy(UUID pharmacyId);

	boolean removeOrder(UUID orderId);

	void updateOrder(EditOrderDTO editOrderDTO);

	List<DrugOrderDTO> getAllOrderDrugs(UUID orderId);

	List<DrugOrderDTO> getAllAddDrugs(UUID orderId);

	List<UnspecifiedDTO<OrderForPhAdminDTO>> getProcessedOrders(UUID pharmacyId);

	List<UnspecifiedDTO<OrderForPhAdminDTO>> getCreatedOrders(UUID pharmacyId);

}
