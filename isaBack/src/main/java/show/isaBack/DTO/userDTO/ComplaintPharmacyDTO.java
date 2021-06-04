package show.isaBack.DTO.userDTO;

import java.util.UUID;
import java.util.Date;

public class ComplaintPharmacyDTO {

	private UUID pharmacyId;
	
	private String patientEmail;
	
	private Date Date;
    
	private String text;
	
	private String reply;
	
	public String getPatientEmail() {
		return patientEmail;
	}

	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}

	public ComplaintPharmacyDTO() {}
		
	public ComplaintPharmacyDTO(UUID pharmacyId, Date date, String text,String reply, String patientEmail) {
		this.pharmacyId= pharmacyId;
		this.Date=date;
		this.text=text;
		this.reply = reply;
		this.patientEmail = patientEmail;
	}

	public UUID getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(UUID pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public Date getDate() {
		return Date;
	}

	public void setDate(Date date) {
		Date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}
	

}
