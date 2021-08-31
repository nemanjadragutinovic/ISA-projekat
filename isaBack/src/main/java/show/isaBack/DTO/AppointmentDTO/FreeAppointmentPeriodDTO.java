package show.isaBack.DTO.AppointmentDTO;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class FreeAppointmentPeriodDTO {
	private Date startDate;
	private Date endDate;
	
	public FreeAppointmentPeriodDTO() {}

	/*public FreeAppointmentPeriodDTO(LocalDateTime startDate, LocalDateTime endDate) {
		super();
		this.startDate =  java.util.Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant());
		this.endDate = java.util.Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());
	}*/

	public FreeAppointmentPeriodDTO(Date startDate, Date endDate) {
		super();
		this.startDate =  startDate;
		this.endDate = endDate;
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
}
