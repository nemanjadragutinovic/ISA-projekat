package show.isaBack.DTO.AppointmentDTO;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class FreeAppointmentPeriodDTO {
	private Date startDate;
	private Date endDate;
	
	public FreeAppointmentPeriodDTO() {}

	public FreeAppointmentPeriodDTO(LocalDateTime startDate, LocalDateTime endDate) {
		super();
		this.startDate =  java.util.Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant());
		this.endDate = java.util.Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());
	}

	public LocalDateTime getStartDate() {
		return startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
