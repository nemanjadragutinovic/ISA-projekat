package show.isaBack.DTO.userDTO;

import java.util.Date;
import java.util.UUID;


public class ComplaintStaffDTO {
	
	private UUID staffId;
	
	private Date date;
    
	private String text;

	private String staffName;

	private String staffSurname;
	
	private String profession;
	
	private String reply;
	
	private String email;
	
	private UUID complaintId;
	
	public UUID getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(UUID complaintId) {
		this.complaintId = complaintId;
	}

	public ComplaintStaffDTO() {}
		
	public ComplaintStaffDTO(UUID staffId, Date date, String text, String name, String surname, String profession, String reply, String email) {
		this.staffId= staffId;
		this.date=date;
		this.text=text;
		this.staffName=name;
		this.staffSurname=surname;
		this.profession = profession;
		this.reply = reply;
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UUID getStaffId() {
		return staffId;
	}

	public void setStaffId(UUID staffId) {
		this.staffId = staffId;
	}

	public java.util.Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	
	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffSurname() {
		return staffSurname;
	}

	public void setStaffSurname(String staffSurname) {
		this.staffSurname = staffSurname;
	}
}
