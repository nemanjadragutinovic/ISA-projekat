package show.isaBack.serviceInterfaces;

import java.util.List;

import show.isaBack.DTO.drugDTO.OrderForProviderDTO;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public interface IOrderService {
	
	public  List<UnspecifiedDTO<OrderForProviderDTO>> findAllOrdersForSupplier();

}
