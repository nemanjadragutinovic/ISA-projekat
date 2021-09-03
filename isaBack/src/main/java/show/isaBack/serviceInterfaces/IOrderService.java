package show.isaBack.serviceInterfaces;

import java.util.List;
import java.util.UUID;

import show.isaBack.DTO.drugDTO.NewOrderDTO;
import show.isaBack.DTO.drugDTO.OrderForProviderDTO;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public interface IOrderService {
	
	public  List<UnspecifiedDTO<OrderForProviderDTO>> findAllOrdersForSupplier();

	UUID createNewOreder(NewOrderDTO newOrderDTO);

}
