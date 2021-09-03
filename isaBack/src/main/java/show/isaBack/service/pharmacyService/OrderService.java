package show.isaBack.service.pharmacyService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import show.isaBack.DTO.drugDTO.DrugOrderDTO;
import show.isaBack.DTO.drugDTO.DrugWithPriceDTO;
import show.isaBack.DTO.drugDTO.EditOrderDTO;
import show.isaBack.DTO.drugDTO.NewOrderDTO;
import show.isaBack.DTO.drugDTO.OrderForPhAdminDTO;
import show.isaBack.DTO.drugDTO.OrderForProviderDTO;
import show.isaBack.Mappers.Pharmacy.OrderMapper;
import show.isaBack.interfaceRepository.drugRepository.DrugInstanceRepository;
import show.isaBack.model.DrugInstance;
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
import show.isaBack.service.drugService.DrugService;
import show.isaBack.service.userService.UserService;
import show.isaBack.serviceInterfaces.IDrugService;
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
	@Autowired
	private IDrugService drugService;
	
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
			System.out.println(de.getFabricCode());
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

	
	@Override
	public List<UnspecifiedDTO<OrderForPhAdminDTO>> getAllOrdersForPharmacy(UUID pharmacyId) {
		List<Order> orders = orderRepository.findPharmacyOrders(pharmacyId);
		
		
		List<UnspecifiedDTO<OrderForPhAdminDTO>> orderList = new ArrayList<UnspecifiedDTO<OrderForPhAdminDTO>>();
		for(Order order:orders) {
			orderList.add(MapOrderToDTO(order));
			
		}
	;

		return orderList;
	}


	private UnspecifiedDTO<OrderForPhAdminDTO> MapOrderToDTO(Order o) {
		
		return new UnspecifiedDTO<OrderForPhAdminDTO>(o.getId(),new OrderForPhAdminDTO(this.mapListOfDrugOrderToListOfDrugOrderDTO(o.getOrder()),o.getDate(),o.getOffers().size(),o.getPharmacyAdmin().getName()+ " " + o.getPharmacyAdmin().getSurname(),o.getOrderStatus()));
	}


	private List<DrugOrderDTO> mapListOfDrugOrderToListOfDrugOrderDTO(List<DrugOrder> drugOrder) {
		 List<DrugOrderDTO> retOrderList = new ArrayList<DrugOrderDTO>();
		 
		for(DrugOrder dr:drugOrder) {
			retOrderList.add(new DrugOrderDTO(dr.getDrugInstance().getId(),dr.getDrugInstance().getDrugInstanceName(),dr.getDrugInstance().getName(),dr.getDrugInstance().getProducerName(),dr.getDrugInstance().getQuantity(),dr.getAmount()));
		}
		 
		 return retOrderList;
	}


	@Override
	public boolean removeOrder(UUID orderId) {
		
		Order order = orderRepository.getOne(orderId);
		
		if(order.getOffers().size()==0) {
			orderRepository.delete(order);
			return true;
		}
		
		return false;
	}

	
	@Override
	public void updateOrder(EditOrderDTO editOrderDTO) {
		Order order= orderRepository.getOne(editOrderDTO.getOrderId());
		if(order.getOffers().size()==0) {
		List<DrugOrder> orderDrugs = new ArrayList<DrugOrder>();
		
		for(DrugOrderDTO drugOrderDTO : editOrderDTO.getListOfDrugs()) {
			DrugOrder newDrugOrder = new DrugOrder(drugInstanceRepository.getOne(drugOrderDTO.getDrugInstanceId()),drugOrderDTO.getAmount());
			drugOrderRepository.save(newDrugOrder);
			orderDrugs.add(newDrugOrder);
		}
		order.setOrder(orderDrugs);
		order.setDate(editOrderDTO.getDateTo());

		orderRepository.save(order);
		}
		
	}
	
	@Override
	public List<DrugOrderDTO> getAllOrderDrugs(UUID orderId) {
		Order order= orderRepository.getOne(orderId);
		List<DrugOrderDTO> drugOrderList = new ArrayList<DrugOrderDTO>();
		
	     drugOrderList.addAll(mapListOfDrugOrderToListOfDrugOrderDTO(order.getOrder()));
	     
		return drugOrderList;
	}


	@Override
	public List<DrugOrderDTO> getAllAddDrugs(UUID orderId) {
		Order order= orderRepository.getOne(orderId);
		List<DrugOrderDTO> drugOrderList = new ArrayList<DrugOrderDTO>();

		
		for(UnspecifiedDTO<DrugWithPriceDTO> dr: drugService.findDrugsInPharmacyWithPrice(order.getPharmacy().getId())) {
			if(!hasDrugInOrder(dr,order)) {
				drugOrderList.add(new DrugOrderDTO(dr.Id,dr.EntityDTO.getName(),dr.EntityDTO.getFabricCode(),dr.EntityDTO.getProducerName(),dr.EntityDTO.getQuantity(),0));
			}
			}
		
		return drugOrderList;
	}
	
	private boolean hasDrugInOrder(UnspecifiedDTO<DrugWithPriceDTO> drugDTO, Order order) {
		for(DrugOrder drugOrder : order.getOrder()) {
			if(drugDTO.Id.equals(drugOrder.getDrugInstance().getId()))
				return true;
		}
		return false;
	} 
	
	
	@Override
	public List<UnspecifiedDTO<OrderForPhAdminDTO>> getCreatedOrders(UUID pharmacyId) {
		List<Order> drugOrders = orderRepository.findCreatedOrders(pharmacyId);
			
		List<UnspecifiedDTO<OrderForPhAdminDTO>> orderList = new ArrayList<UnspecifiedDTO<OrderForPhAdminDTO>>();
		for(Order order:drugOrders) {
			orderList.add(MapOrderToDTO(order));
			
		}

		return orderList;
	}


	
	@Override
	public List<UnspecifiedDTO<OrderForPhAdminDTO>> getProcessedOrders(UUID pharmacyId) {
		List<Order> drugOrders = orderRepository.findProcessedOredrs(pharmacyId);
		
		List<UnspecifiedDTO<OrderForPhAdminDTO>> orderList = new ArrayList<UnspecifiedDTO<OrderForPhAdminDTO>>();
		
		for(Order order:drugOrders) {
			orderList.add(MapOrderToDTO(order));
		}
			return orderList;
	      
	}

}
