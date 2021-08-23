package show.isaBack.model.drugs;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import show.isaBack.model.DrugInstance;
import show.isaBack.model.Patient;

@Entity
public class DrugFeedback {

	
	@Id
    @Column(name = "id")
	private UUID id;
	
		
	@ManyToOne(optional = false)
	private DrugInstance drug;
	
    @ManyToOne(optional = false)
	private Patient patient;
	
	@Column(name = "date")
	private Date date;
    
	@Column(name="grade")
	private int grade;
	
	public DrugFeedback() {}

	public DrugFeedback(DrugInstance drug, Patient patient, Date date, int grade) {
		super();
		this.id=UUID.randomUUID();
		this.drug = drug;
		this.patient = patient;
		this.date = date;
		this.grade = grade;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public DrugInstance getDrug() {
		return drug;
	}

	public void setDrug(DrugInstance drug) {
		this.drug = drug;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	
	
	
}
