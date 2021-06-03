package show.isaBack.DTO.AppointmentDTO;

import java.util.Date;
import java.util.UUID;

public class FormAppointmentDTO {
	
	private UUID dermatologistId;
	
	private UUID phId;
	
	

	private Date startDateTime;
    
    private Date endDateTime;
    
	private double price;
	
	public FormAppointmentDTO() { super();}
    
    public FormAppointmentDTO(UUID dermatologistId, UUID phId, Date startDateTime, Date endDateTime,
			double price) {
		super();
		this.dermatologistId = dermatologistId;
		this.phId = phId;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.price = price;
	}


    
    

	public UUID getDermatologistId() {
		return dermatologistId;
	}

	public void setDermatologistId(UUID dermatologistId) {
		this.dermatologistId = dermatologistId;
	}

	public UUID getPhId() {
		return phId;
	}

	public void setPhId(UUID phId) {
		this.phId = phId;
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
