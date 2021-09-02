package show.isaBack.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class ActionPromotion {

	
	@Id
	private UUID id;
	
    @Column(name = "dateFrom")
	private Date dateFrom;
	
    @Column(name = "dateTo")
	private Date dateTo;
	
	@Column(name="discount")
	private int discount;
	
   
	@Enumerated(EnumType.STRING)
	@Column(name = "Type", nullable = false)
	private ActionType actionType;
	
	@ManyToOne
	private Pharmacy pharmacy;

	public ActionPromotion() {
		super();
	}

	public ActionPromotion(UUID id,Pharmacy pharmacy, Date dateFrom, Date dateTo, int discount, ActionType actionType
			) {
		super();
		this.id = id;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.discount = discount;
		this.actionType = actionType;
		this.pharmacy = pharmacy;
	}

	
	public ActionPromotion(Pharmacy pharmacy,Date dateFrom, Date dateTo,int discount,ActionType actionType) {
		super();
		this.id = UUID.randomUUID();
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.pharmacy=pharmacy;
		this.discount = discount;
		this.actionType = actionType;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public int getDiscount() {
			return discount;
		}


	public void setDiscount(int discount) {
			this.discount = discount;
		}



	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	
	

}
