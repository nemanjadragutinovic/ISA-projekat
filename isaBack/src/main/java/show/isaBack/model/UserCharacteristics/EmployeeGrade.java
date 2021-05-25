package show.isaBack.model.UserCharacteristics;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import show.isaBack.model.Patient;
import show.isaBack.model.Pharmacy;
import show.isaBack.model.User;

@Entity
public class EmployeeGrade {

	
	@Id
    @Column(name = "id")
	private UUID id;
	
	
	@ManyToOne(optional = false)
	private User employee;
	
    @ManyToOne(optional = false)
	private Patient patient;
    
	private int grade;
	
	private Date date;

	public EmployeeGrade() {
		super();
	}

	public EmployeeGrade(User user, Patient patient, int grade, Date date) {
		super();
		this.id=UUID.randomUUID();
		this.employee = user;
		this.patient = patient;
		this.grade = grade;
		this.date = date;
	}

	
	
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	

	public User getEmployee() {
		return employee;
	}

	public void setEmployee(User employee) {
		this.employee = employee;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
