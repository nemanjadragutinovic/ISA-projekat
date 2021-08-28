package show.isaBack.DTO.AppointmentDTO;

import java.util.Date;
import java.util.UUID;

import show.isaBack.DTO.userDTO.UserDTO;
import show.isaBack.model.appointment.AppointmentStatus;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public class AppointmentDTO {
	
	private UnspecifiedDTO<UserDTO> employee;
	
	private UnspecifiedDTO<UserDTO> patient;
	
	public UnspecifiedDTO<UserDTO> getPatient() {
		return patient;
	}

	public void setPatient(UnspecifiedDTO<UserDTO> patient) {
		this.patient = patient;
	}

	private Date startDateTime;
    
    private Date endDateTime;
    
    private double price;
    
    private AppointmentStatus appointmentStatus;
   

	public AppointmentDTO() {}

	public AppointmentDTO(UnspecifiedDTO<UserDTO> employee, UnspecifiedDTO<UserDTO> patient, AppointmentStatus appointmentStatus, Date startDateTime, Date endDateTime, double price) {
		super();
		this.patient = patient;
		this.employee = employee;
		this.appointmentStatus = appointmentStatus;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.price = price;
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

	public UnspecifiedDTO<UserDTO> getEmpolee() {
		return employee;
	}

	public void setEmpolee(UnspecifiedDTO<UserDTO> employee) {
		this.employee = employee;
	}

	public AppointmentStatus getAppointmentStatus() {
		return appointmentStatus;
	}

	public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}
	

}

