package show.isaBack.DTO.AppointmentDTO;


import java.util.Date;
import java.util.UUID;

public class ReservationConsultationDTO {
	
	private UUID pharmacistId;
	
	private Long startDate;

	public ReservationConsultationDTO() {
		super();
	}

	public ReservationConsultationDTO(UUID pharmacistId, Long startDate) {
		super();
		this.pharmacistId = pharmacistId;
		this.startDate = startDate;
	}

	public UUID getPharmacistId() {
		return pharmacistId;
	}

	public void setPharmacistId(UUID pharmacistId) {
		this.pharmacistId = pharmacistId;
	}

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}
	
	
}
