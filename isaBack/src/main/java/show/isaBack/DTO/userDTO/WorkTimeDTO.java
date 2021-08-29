package show.isaBack.DTO.userDTO;

import java.util.Date;
import java.util.UUID;

public class WorkTimeDTO {
    private UUID pharmacyId;
	
	private UUID employee;
		
	private String pharmacyName;
	
	private Date startDate;
	
	private Date endDate;
    
    private int startTime;
	
    private int endTime;
    
    public WorkTimeDTO() {}
    
    public WorkTimeDTO(UUID pharmacyId,UUID employee, Date startDate, Date endDate, int startTime, int endTime,String pharmacyName) {
		super();
		this.pharmacyId= pharmacyId;
		this.employee= employee;
		this.startDate= startDate;
		this.endDate= endDate;
		this.startTime=startTime;
		this.endTime=endTime;
		this.pharmacyName=pharmacyName;
	}
    
    public WorkTimeDTO(UUID pharmacyId,UUID employee, Date startDate, Date endDate, int startTime, int endTime) {
		super();
		this.pharmacyId= pharmacyId;
		this.employee= employee;
		this.startDate= startDate;
		this.endDate= endDate;
		this.startTime=startTime;
		this.endTime=endTime;
		this.pharmacyName="";
	}


	public UUID getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(UUID pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public UUID getEmployee() {
		return employee;
	}

	public void setEmployee(UUID employee) {
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


	public String getPharmacyName() {
		return pharmacyName;
	}

	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}
}
