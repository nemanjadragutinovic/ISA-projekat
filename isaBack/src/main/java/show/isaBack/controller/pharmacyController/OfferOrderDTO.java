package show.isaBack.controller.pharmacyController;

import java.util.UUID;

public class OfferOrderDTO {
	UUID orderId;
	UUID offerId;
	public OfferOrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OfferOrderDTO(UUID orderId, UUID offerId) {
		super();
		this.orderId = orderId;
		this.offerId = offerId;
	}
	public UUID getOrderId() {
		return orderId;
	}
	public void setOrderId(UUID orderId) {
		this.orderId = orderId;
	}
	public UUID getOfferId() {
		return offerId;
	}
	public void setOfferId(UUID offerId) {
		this.offerId = offerId;
	}
	
	
}
