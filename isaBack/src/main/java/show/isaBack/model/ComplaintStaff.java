package show.isaBack.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class ComplaintStaff {

	@Id
	private UUID id;
	
	@Column
	private StaffFeedbackId staffComplaintId;
	
    @Column(name = "date")
	private Date date;

	@Column(name="text")
	private String text;

	@Column(name="staff_name")
	private String staffName;

	@Column(name="staff_surname")
	private String staffSurname;

	@Column(name="reply")
	private String reply;

	@Column(name="email")
	private String email;
	
	private String profession;
	
	@Version
	private Long version;
	
	public ComplaintStaff() {}
	
	public ComplaintStaff(User user, Patient patient, String text, String name, String surname, String profession, String email) {
		this(UUID.randomUUID(),user, patient, text, new Date(), "", name, surname, profession, email);
	}
	
	public ComplaintStaff(UUID id,User user, Patient patient, String text, Date date, String reply, String name, String surname, String profession, String email) {
		super();
		this.id = id;
		this.staffComplaintId = new StaffFeedbackId(user, patient);
		this.date=date;
		this.text=text;
		this.reply=reply;
		this.staffName=name;
		this.staffSurname=surname;
		this.profession = profession;
		this.email = email;
	}

	public UUID getId() {
		return id;
	}
	
	public Long getVersion() {
		return version;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public User getUser() {
		return staffComplaintId.getUser();
	}

	public void setUser(User user) {
		staffComplaintId.setUser(user);
	}
	
	public Patient getPatient() {
		return staffComplaintId.getPatient();
	}

	public void setPatient(Patient patient) {
		staffComplaintId.setPatient(patient);
	}

	public Date getDate() {
		return date;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public void setDate(Date date) {
		this.date = date;
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
