package show.isaBack.DTO.userDTO;

import java.util.UUID;

import show.isaBack.model.FreeDayStatus;

import java.util.Date;

public class AbsenceDTO {

	private UUID forEmployee;
	
	private Date startDate;
	
	private Date endDate;
    
	private FreeDayStatus freeDayStatus;
	
	private String rejectReason;
   
	public AbsenceDTO() {
		
	}


	public AbsenceDTO(UUID forEmployee, Date startDate, Date endDate, FreeDayStatus freeDayStatus,String rejectReason) {
		super();
		this.forEmployee= forEmployee;
		this.startDate= startDate;
		this.endDate= endDate;
		this.freeDayStatus=freeDayStatus;
		this.rejectReason= rejectReason;
	}
	
	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public UUID getForEmployee() {
		return forEmployee;
	}

	public void setForEmployee(UUID forEmployee) {
		this.forEmployee = forEmployee;
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

	public FreeDayStatus getFreeDayStatus() {
		return freeDayStatus;
	}

	public void setFreeDayStatus(FreeDayStatus freeDayStatus) {
		this.freeDayStatus = freeDayStatus;
	}
}
