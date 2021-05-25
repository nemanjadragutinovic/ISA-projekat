package show.isaBack.service.pharmacyService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import show.isaBack.DTO.drugDTO.OrderForProviderDTO;
import show.isaBack.Mappers.Pharmacy.OrderMapper;
import show.isaBack.model.drugs.Offers;
import show.isaBack.model.drugs.Order;
import show.isaBack.repository.drugsRepository.OrderRepository;
import show.isaBack.service.userService.UserService;
import show.isaBack.serviceInterfaces.IOrderService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

@Service
public class OrderService implements IOrderService {
	
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserService userService;
	
	
	@Override
	public  List<UnspecifiedDTO<OrderForProviderDTO>> findAllOrdersForSupplier() {
		boolean alreadyOffered = false;
		List<UnspecifiedDTO<OrderForProviderDTO>> orders = new ArrayList<UnspecifiedDTO<OrderForProviderDTO>>();
		
		for(Order ord: orderRepository.findAll()) {
			for(Offers of: ord.getOffers()) {
				if(of.getSupplier().getId().equals(userService.getLoggedUserId()))
					alreadyOffered = true;
			}
			if(!alreadyOffered)
			
				orders.add(OrderMapper.MapOrderInstancePersistenceToOrderInstanceIdentifiableDTO(ord));
				alreadyOffered = false;
		}
		
		return orders;
	}

}
