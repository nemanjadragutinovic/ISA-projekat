package show.isaBack.service.pharmacyService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import show.isaBack.DTO.drugDTO.DrugOrderDTO;
import show.isaBack.DTO.drugDTO.NewOrderDTO;
import show.isaBack.DTO.drugDTO.OrderForProviderDTO;
import show.isaBack.Mappers.Pharmacy.OrderMapper;
import show.isaBack.interfaceRepository.drugRepository.DrugInstanceRepository;
import show.isaBack.model.Pharmacy;
import show.isaBack.model.PharmacyAdmin;
import show.isaBack.model.drugs.DrugInPharmacy;
import show.isaBack.model.drugs.DrugOrder;
import show.isaBack.model.drugs.Offers;
import show.isaBack.model.drugs.Order;
import show.isaBack.model.drugs.OrderStatus;
import show.isaBack.repository.drugsRepository.DrugInPharmacyRepository;
import show.isaBack.repository.drugsRepository.DrugOrderRepository;
import show.isaBack.repository.drugsRepository.OrderRepository;
import show.isaBack.repository.pharmacyRepository.PharmacyRepository;
import show.isaBack.repository.userRepository.PharmacyAdminRepository;
import show.isaBack.service.userService.UserService;
import show.isaBack.serviceInterfaces.IOrderService;
import show.isaBack.serviceInterfaces.IPharmacyService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

@Service
public class OrderService implements IOrderService {
	
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PharmacyAdminRepository phAdminRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private DrugInstanceRepository drugInstanceRepository;
	
	@Autowired
	private DrugOrderRepository drugOrderRepository;
	
	
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
	
	@Override
	public UUID createNewOreder(NewOrderDTO newOrderDTO) {
		for (DrugOrderDTO de:newOrderDTO.getListOfDrugs())
		{
			System.out.println("aewreatfras");
			System.out.println(de.getDrugInstanceId());
			System.out.println(de.getAmount());
		}
		PharmacyAdmin pharmacyAdmin = phAdminRepository.getOne(userService.getLoggedUserId());
		Pharmacy pharmacy = pharmacyRepository.getOne(newOrderDTO.getPharmacyId());
		List<DrugOrder> orderDrugs = new ArrayList<DrugOrder>();
		
		for(DrugOrderDTO drugOrderDTO : newOrderDTO.getListOfDrugs()) {
			DrugOrder newDrugOrder = new DrugOrder(drugInstanceRepository.getOne(drugOrderDTO.getDrugInstanceId()),drugOrderDTO.getAmount());
			drugOrderRepository.save(newDrugOrder);
			orderDrugs.add(newDrugOrder);
		}
		
		Order newOrder = new Order(pharmacy,pharmacyAdmin,orderDrugs,newOrderDTO.getDateTo(),null, OrderStatus.CREATED);

		orderRepository.save(newOrder);
		return newOrder.getId();
	}




}
