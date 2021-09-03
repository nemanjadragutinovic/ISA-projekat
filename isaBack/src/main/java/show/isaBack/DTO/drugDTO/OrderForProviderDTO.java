package show.isaBack.DTO.drugDTO;

import java.util.Date;
import java.util.List;

import show.isaBack.DTO.pharmacyDTO.PharmacyDTO;
import show.isaBack.DTO.userDTO.PharmacyAdminDTO;
import show.isaBack.model.drugs.OrderStatus;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public class OrderForProviderDTO {
	
	private UnspecifiedDTO<PharmacyDTO> pharmacy;
	
	private UnspecifiedDTO<PharmacyAdminDTO> pharmacyAdmin;
	
    private List<UnspecifiedDTO<DrugOrderDTO>> order;
	
	private Date date;
	
	private List<UnspecifiedDTO<OfferDTO>> offers;
	
	private OrderStatus orderStatus;

    
    
	public OrderForProviderDTO() {
		super();
	}

	public OrderForProviderDTO(UnspecifiedDTO<PharmacyDTO> pharmacy, UnspecifiedDTO<PharmacyAdminDTO> pharmacyAdmin, List<UnspecifiedDTO<DrugOrderDTO>> order, Date date,
			OrderStatus orderStatus) {
		super();
		this.pharmacy = pharmacy;
		this.pharmacyAdmin = pharmacyAdmin;
		this.order = order;
		this.date = date;
		this.orderStatus = orderStatus;
	}
	
	public OrderForProviderDTO(UnspecifiedDTO<PharmacyDTO> pharmacy, UnspecifiedDTO<PharmacyAdminDTO> pharmacyAdmin, List<UnspecifiedDTO<DrugOrderDTO>> order, Date date,
			OrderStatus orderStatus, List<UnspecifiedDTO<OfferDTO>> offers) {
		super();
		this.pharmacy = pharmacy;
		this.pharmacyAdmin = pharmacyAdmin;
		this.order = order;
		this.date = date;
		this.orderStatus = orderStatus;
		this.offers = offers;
	}

	public UnspecifiedDTO<PharmacyDTO> getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(UnspecifiedDTO<PharmacyDTO> pharmacy) {
		this.pharmacy = pharmacy;
	}

	public UnspecifiedDTO<PharmacyAdminDTO> getPharmacyAdmin() {
		return pharmacyAdmin;
	}

	public void setPharmacyAdmin(UnspecifiedDTO<PharmacyAdminDTO> pharmacyAdmin) {
		this.pharmacyAdmin = pharmacyAdmin;
	}

	public List<UnspecifiedDTO<DrugOrderDTO>> getOrder() {
		return order;
	}

	public void setOrder(List<UnspecifiedDTO<DrugOrderDTO>> order) {
		this.order = order;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<UnspecifiedDTO<OfferDTO>> getOffers() {
		return offers;
	}

	public void setOffers(List<UnspecifiedDTO<OfferDTO>> offers) {
		this.offers = offers;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
}