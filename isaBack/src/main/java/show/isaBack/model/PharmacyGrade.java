package show.isaBack.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
public class PharmacyGrade {
	
	

	@Id
    @Column(name = "id")
	private UUID id;
	
	
	@ManyToOne(optional = false)
	private Pharmacy pharmacy;
	
    @ManyToOne(optional = false)
	private Patient patient;
    
	private int grade;
	
	private Date date;

	public PharmacyGrade() {
		super();
	}

	public PharmacyGrade(Pharmacy pharmacy, Patient patient, int grade, Date date) {
		super();
		this.id=UUID.randomUUID();
		this.pharmacy = pharmacy;
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

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
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
