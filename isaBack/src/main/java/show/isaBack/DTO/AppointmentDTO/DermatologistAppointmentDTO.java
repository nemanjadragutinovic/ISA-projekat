package show.isaBack.DTO.AppointmentDTO;

import java.util.Date;

import show.isaBack.DTO.userDTO.EmployeeGradeDTO;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public class DermatologistAppointmentDTO {

	
	private UnspecifiedDTO<EmployeeGradeDTO> employee;
	
	private Date startDateTime;
	    
    private Date endDateTime;
	    
    private double price;

	public DermatologistAppointmentDTO() {
		super();
	}

	public DermatologistAppointmentDTO(UnspecifiedDTO<EmployeeGradeDTO> employee, Date startDateTime, Date endDateTime,
			double price) {
		super();
		this.employee = employee;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.price = price;
	}

	public UnspecifiedDTO<EmployeeGradeDTO> getEmployee() {
		return employee;
	}

	public void setEmployee(UnspecifiedDTO<EmployeeGradeDTO> employee) {
		this.employee = employee;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
    
    
	
	
}
