package show.isaBack.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class FreeDays {
	@Id
    @Column(name = "id")
	private UUID id;
	
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private User user;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Pharmacy pharmacy;

	@Column(name = "startDate")
	private Date startDate;
	
    @Column(name = "endDate")
	private Date endDate;
    
    @Enumerated(EnumType.STRING)
	@Column(name="freeDaysStatus")
	private FreeDayStatus freeDaysStatus;
 
	
    @Column(name = "rejectReason")
	private String rejectReason;
    
    public FreeDays() {}
	
	public FreeDays(User user, Pharmacy pharmacy, Date startDate, Date endDate) {
		super();
		this.id = UUID.randomUUID();
		this.user= user;
		this.pharmacy = pharmacy;
		this.startDate= startDate;
		this.endDate= endDate;
		this.freeDaysStatus=FreeDayStatus.WAITING;
		this.rejectReason= "";
		
	}
	
	public FreeDays(UUID id, User user, Pharmacy pharmacy, Date startDate, Date endDate, FreeDayStatus freeDaysStatus, String rejectReason) {
		super();
		this.id = id;
		this.user= user;
		this.pharmacy = pharmacy;
		this.startDate= startDate;
		this.endDate= endDate;
		this.freeDaysStatus=freeDaysStatus;
		this.rejectReason= rejectReason;
	}
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public FreeDayStatus getStatus() {
		return freeDaysStatus;
	}

	public void setStatus(FreeDayStatus freeDaysStatus) {
		this.freeDaysStatus = freeDaysStatus;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	
    public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
}
