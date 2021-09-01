package show.isaBack.DTO.userDTO;

import java.util.Date;
import java.util.UUID;

public class NewDermatologistInPharmacyDTO {
private UUID pharmacyId;
	
	private UUID dermatologistId;
	
	private Date startDate;
	
	private Date endDate;
    
    private int startTime;
	
    private int endTime;
	
	public NewDermatologistInPharmacyDTO() {
		
	}
	
	public NewDermatologistInPharmacyDTO(UUID pharmacyId, UUID dermatologistId, Date startDate, Date endDate, int startTime, int endTime) {
		super();
		this.pharmacyId = pharmacyId;
		this.dermatologistId = dermatologistId;
		this.startDate= startDate;
		this.endDate= endDate;
		this.startTime=startTime;
		this.endTime=endTime;
	}

	public UUID getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(UUID pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public UUID getDermatologistId() {
		return dermatologistId;
	}

	public void setDermatologistId(UUID dermatologistId) {
		this.dermatologistId = dermatologistId;
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
