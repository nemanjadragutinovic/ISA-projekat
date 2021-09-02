package show.isaBack.model.drugs;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import show.isaBack.model.DrugInstance;
import show.isaBack.model.Pharmacy;
import show.isaBack.model.User;

@Entity
public class DrugRequest {
	@Id
	private UUID id;
	@ManyToOne
    private Pharmacy pharmacy;
    @ManyToOne
    private DrugInstance drugInstance;
    @ManyToOne
    private User user;
    @Column
    private Date dateTime;

	public DrugRequest() {
		super();
	}
	public DrugRequest(UUID id, Pharmacy pharmacy, DrugInstance drugInstance, User user) {
		super();
		this.id = id;
		this.pharmacy = pharmacy;
		this.drugInstance = drugInstance;
		this.user = user;
		dateTime = new Date();
	}
	public DrugRequest(Pharmacy pharmacy, DrugInstance drugInstance, User user) {
		this(UUID.randomUUID(), pharmacy, drugInstance, user);
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public Pharmacy getPharmacy() {
		return pharmacy;
	}
	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	public DrugInstance getDrugInstance() {
		return drugInstance;
	}
	public void setDrugInstance(DrugInstance drugInstance) {
		this.drugInstance = drugInstance;
	}
	public User getStaff() {
		return user;
	}
	public void setStaff(User user) {
		this.user = user;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	
}

