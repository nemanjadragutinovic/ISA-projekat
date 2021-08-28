package show.isaBack.DTO.AppointmentDTO;

import java.util.Date;
import java.util.UUID;

public class ParamsFromAppointmentDTO {


	private UUID dermatologistId;
	
	private UUID phId;

	private Date date;
    
    private int duration;
    

        
    public ParamsFromAppointmentDTO() {}

	public ParamsFromAppointmentDTO(UUID dermatologistId,UUID pharmacyId, Date date, int duration) {
		super();
		this.dermatologistId = dermatologistId;
		this.date = date;
		this.duration = duration;
		
		this.phId=pharmacyId;
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

	public void setPhId(UUID pharmacyId) {
		this.phId = pharmacyId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getDuration() {
		return duration;
	}

}
