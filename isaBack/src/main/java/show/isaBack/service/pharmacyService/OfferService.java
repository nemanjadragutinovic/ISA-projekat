package show.isaBack.service.pharmacyService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import show.isaBack.DTO.drugDTO.OfferDTO;
import show.isaBack.DTO.pharmacyDTO.PharmacyDTO;
import show.isaBack.DTO.userDTO.AuthorityDTO;
import show.isaBack.model.Pharmacy;
import show.isaBack.model.drugs.DrugOrder;
import show.isaBack.model.drugs.OfferStatus;
import show.isaBack.model.drugs.Offers;
import show.isaBack.model.drugs.Order;
import show.isaBack.model.drugs.SupplierDrugStorage;
import show.isaBack.repository.drugsRepository.OfferRepository;
import show.isaBack.repository.drugsRepository.OrderRepository;
import show.isaBack.repository.drugsRepository.SupplierDrugStorageRepository;
import show.isaBack.repository.userRepository.SupplierRepository;
import show.isaBack.service.userService.UserService;
import show.isaBack.serviceInterfaces.IOfferService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

@Service
public class OfferService implements IOfferService{
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private SupplierDrugStorageRepository supplierDrugStorageRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	SupplierRepository supplierRepository;
	
	@Autowired
	OfferRepository offerRepository;
	
	private boolean checkIfExist(DrugOrder o) {

		for(SupplierDrugStorage storage: supplierDrugStorageRepository.findAll()){
			if(userService.getLoggedUserId().equals(storage.getDrugStorageId().getSupplier().getId())) {
				if(storage.getDrugInstance().getId().equals(o.getDrugInstance().getId())) {
					if(storage.getCount() >= o.getAmount())
						return true;
				}
			}
		}	
		
		return false;
	}

	
	public boolean checkIfHasDrugs(UUID id) {
		
		Order order = orderRepository.getOne(id);
		
		System.out.println(order.getId());
		
		List<DrugOrder> drugOrders = order.getOrder();
		for(DrugOrder o: drugOrders) {
			System.out.println(o.getId() + "drug order id XDDXDXDXDXDDDXDDDDDD");
			if(checkIfExist(o)) {
				System.out.println(checkIfExist(o));
				continue;
			}else
				return false;
		}
		
		return true;
	}
	
	
	@Override
	public List<UnspecifiedDTO<AuthorityDTO>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<UnspecifiedDTO<OfferDTO>> findAllOffers() {
		
		List<UnspecifiedDTO<OfferDTO>> offers = new ArrayList<UnspecifiedDTO<OfferDTO>>();
		
		List<Offers> offersForSupplier = new ArrayList<Offers>();
		
		
		
		for (Offers offs : offerRepository.findAll()) {
			System.out.println(offs.getId()+"-----1");
			if(userService.getLoggedUserId().equals(offs.getSupplier().getId())) {
				offersForSupplier.add(offs);
				System.out.println(offs.getId() + "-----2");
			}
		}
		
		for (Offers offs : offersForSupplier) 
		{
			OfferDTO offDTO= new OfferDTO(offs.getDateToDelivery(),offs.getPrice(),offs.getOfferStatus(),offs.getId());	
			offers.add(new UnspecifiedDTO<OfferDTO>(offs.getId(),offDTO));
		}
		
		
		return offers;
	}

	@Override
	public UnspecifiedDTO<OfferDTO> findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID create(OfferDTO entityDTO) {
		Offers offer = CreateOfferInstanceFromDTO(entityDTO);
		offer.setSupplier(supplierRepository.getOne(userService.getLoggedUserId()));
		offer.setOfferStatus(OfferStatus.WAITING);
		Order order = orderRepository.getOne(entityDTO.getId());
		offerRepository.save(offer);
		order.addOffer(offer);
		orderRepository.save(order);
		return offer.getId();
	}
	
	private Offers CreateOfferInstanceFromDTO(OfferDTO offerDTO) {
		return new Offers(offerDTO.getDateToDelivery(), offerDTO.getPrice(), offerDTO.getOfferStatus());
	}

	
	
	@Override
	public void update(OfferDTO entityDTO, UUID id) {
		Offers offer = offerRepository.getOne(id);
		offer.setPrice(entityDTO.getPrice());
		if(entityDTO.getDateToDelivery() != null)
			offer.setDateToDelivery(entityDTO.getDateToDelivery());
		offerRepository.save(offer);
	}

	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public List<UnspecifiedDTO<OfferDTO>> findAllAccepted() {
		List<UnspecifiedDTO<OfferDTO>> offers = new ArrayList<UnspecifiedDTO<OfferDTO>>();
		
		for (UnspecifiedDTO<OfferDTO> offerDTO : findAllOffers()) {
			if(offerDTO.EntityDTO.getOfferStatus().equals(OfferStatus.ACCEPTED)) {
				offers.add(offerDTO);
			}
		}
		
		
		return offers;
	}

	@Override
	public List<UnspecifiedDTO<OfferDTO>> findAllRejected() {
		List<UnspecifiedDTO<OfferDTO>> offers = new ArrayList<UnspecifiedDTO<OfferDTO>>();
		
		for (UnspecifiedDTO<OfferDTO> offerDTO : findAllOffers()) {
			if(offerDTO.EntityDTO.getOfferStatus().equals(OfferStatus.REJECTED)) {
				offers.add(offerDTO);
			}
		}
		
		
		return offers;	
	}

	@Override
	public List<UnspecifiedDTO<OfferDTO>> findAllWaiting() {
		List<UnspecifiedDTO<OfferDTO>> offers = new ArrayList<UnspecifiedDTO<OfferDTO>>();
		
		for (UnspecifiedDTO<OfferDTO> offerDTO : findAllOffers()) {
			if(offerDTO.EntityDTO.getOfferStatus().equals(OfferStatus.WAITING)) {
				offers.add(offerDTO);
			}
		}
		
		
		return offers;
	}
	
	@Override
	public boolean checkIfCanUpdate(UUID id) {
		Date todayDate = new Date();
		List<Order> orders = orderRepository.findAll();
		for(Order o: orders){
			System.out.println(o.getId());
			if(o.getOffers() == null)
				continue;
			
			if(findIfThereIsOffer(o.getOffers(), id)) {
				if( todayDate.before(o.getDate()) )
					return true;
				else 
					return false;
			}
		}
		
		return false;
	}
	
	private boolean findIfThereIsOffer(List<Offers> offers, UUID id) {
		for(Offers o: offers) {
			System.out.println(o.getId());
			if(o.getId().equals(id))
				return true;
		}
		
		return false;
		
	}

}
