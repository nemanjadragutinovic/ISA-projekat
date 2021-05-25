package show.isaBack.model.UserCharacteristics;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import show.isaBack.model.Pharmacy;
import show.isaBack.model.User;



@Entity
public class WorkTime {
	@Id
    @Column(name = "id")
	private UUID id;
	
	@ManyToOne
	private Pharmacy pharmacy;
	
	@ManyToOne
	private User employee;
		
	private Date startDate;
	  
	private Date endDate;
    
    private int startTime;
	
    private int endTime;
    
    public WorkTime() {}
	
	public WorkTime(Pharmacy pharmacy,User employee, Date startDate, Date endDate, int startTime, int endTime) {
		this(UUID.randomUUID(), employee,startDate,endDate,startTime,endTime,pharmacy);
	}
	
	public WorkTime(UUID id, User employee, Date startDate, Date endDate, int startTime, int endTime,Pharmacy pharmacy) {
		super();
		this.id = id;
		this.employee= employee;
		this.startDate= startDate;
		this.endDate= endDate;
		this.startTime=startTime;
		this.endTime=endTime;
		this.pharmacy = pharmacy;

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

	public User getEmployee() {
		return employee;
	}

	public void setEmployee(User employee) {
		this.employee = employee;
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

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	
	
	

}
