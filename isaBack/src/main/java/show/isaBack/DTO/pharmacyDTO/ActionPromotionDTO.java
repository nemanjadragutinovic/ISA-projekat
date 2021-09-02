package show.isaBack.DTO.pharmacyDTO;

import java.util.Date;
import show.isaBack.model.ActionType;

public class ActionPromotionDTO {
	
	private Date dateFrom;
	
	private Date dateTo;
    
	private int discount;
	
	private ActionType actionType;

	public ActionPromotionDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ActionPromotionDTO(Date dateFrom, Date dateTo, int discount,
			ActionType actionType) {
		super();
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.discount = discount;
		this.actionType = actionType;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}


	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}


}

