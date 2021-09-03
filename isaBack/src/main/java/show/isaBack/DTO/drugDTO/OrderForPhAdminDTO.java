package show.isaBack.DTO.drugDTO;

import java.util.Date;
import java.util.List;

import show.isaBack.model.drugs.OrderStatus;

public class OrderForPhAdminDTO {
private List<DrugOrderDTO> drugs;
	
	private Date dateTo;
	
	private int offersCount;
	
	private String creator;
	
	private OrderStatus orderStatus;


	public OrderForPhAdminDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderForPhAdminDTO(List<DrugOrderDTO> drugs, Date dateTo,int offersCount,String creator, OrderStatus orderStatus) {
		super();
		this.drugs = drugs;
		this.dateTo = dateTo;
		this.offersCount=offersCount;
		this.creator=creator;
		this.orderStatus=orderStatus;
	}

	public List<DrugOrderDTO> getDrugs() {
		return drugs;
	}

	public void setDrugs(List<DrugOrderDTO> drugs) {
		this.drugs = drugs;
	}


	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public int getOffersCount() {
		return offersCount;
	}

	public void setOffersCount(int offersCount) {
		this.offersCount = offersCount;
	}
	

}
