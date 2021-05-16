package show.isaBack.model.appointment;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import show.isaBack.model.Patient;
import show.isaBack.model.Pharmacy;
import show.isaBack.model.User;



@Entity
public class Appointment {

	@Id
    @Column(name = "id")
	private UUID id;
	
	@ManyToOne
	private User employee;
	
	@ManyToOne
	private Pharmacy pharmacy;
	
	
    @Column(name = "startDateTime")
	private Date startDateTime;
	    
	@Column(name = "endDateTime")
	private Date endDateTime;
	
    @Column(name = "price")
    private double price;
    
    @ManyToOne(optional = true)
	private Patient patient;
    
    @Enumerated(EnumType.STRING)
  	@Column(name = "appointmentType", nullable = false)
  	private AppointmentType appointmentType;
      
    @Enumerated(EnumType.STRING)
  	@Column(name = "appointmentStatus", nullable = false)
  	private AppointmentStatus appointmentStatus;

	
    
    
    public Appointment() {
		super();
		
	}


	public Appointment(User employee,Pharmacy pharmacy, Date startDateTime, Date endDateTime, double price, Patient patient,
			AppointmentType appointmentType, AppointmentStatus appointmentStatus) {
		super();
		this.id = UUID.randomUUID();
		this.employee = employee;
		this.pharmacy=pharmacy;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.price = price;
		this.patient = patient;
		this.appointmentType = appointmentType;
		this.appointmentStatus = appointmentStatus;
	}




	public Appointment(UUID id, User employee,Pharmacy pharmacy, Date startDateTime, Date endDateTime, double price, Patient patient,
			AppointmentType appointmentType, AppointmentStatus appointmentStatus) {
		super();
		this.id = id;
		this.employee = employee;
		this.pharmacy=pharmacy;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.price = price;
		this.patient = patient;
		this.appointmentType = appointmentType;
		this.appointmentStatus = appointmentStatus;
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


	
	public Pharmacy getPharmacy() {
		return pharmacy;
	}


	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
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


	public Patient getPatient() {
		return patient;
	}


	public void setPatient(Patient patient) {
		this.patient = patient;
	}


	public AppointmentType getAppointmentType() {
		return appointmentType;
	}


	public void setAppointmentType(AppointmentType appointmentType) {
		this.appointmentType = appointmentType;
	}


	public AppointmentStatus getAppointmentStatus() {
		return appointmentStatus;
	}


	public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}

    
    
	
    
    
	
	
	
	
}
