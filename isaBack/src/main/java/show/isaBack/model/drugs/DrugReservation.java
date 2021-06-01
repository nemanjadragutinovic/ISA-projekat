package show.isaBack.model.drugs;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import show.isaBack.model.DrugInstance;
import show.isaBack.model.Patient;
import show.isaBack.model.Pharmacy;



@Entity
public class DrugReservation {

	@Id
	private UUID id;
    
	@ManyToOne
    private Patient patient;
	
    @ManyToOne
    private Pharmacy pharmacy;

	@ManyToOne
    private DrugInstance drugInstance;
    
    
	@Column( nullable = false)
    private int count;
	
	@Column(nullable = false)
    private Date startDate;
	
	@Column( nullable = false)
    private Date endDate;
	
	@Column( nullable = false)
	private double priceForOneDrug;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "drugReservationStatus", nullable = false)
	private DrugReservationStatus drugReservationStatus;

	public DrugReservation() {
		super();
	}

	public DrugReservation(UUID id, Patient patient, Pharmacy pharmacy, DrugInstance drugInstance, int count,
			Date startDate, Date endDate, double priceForOneDrug, DrugReservationStatus drugReservationStatus) {
		super();
		this.id = id;
		this.patient = patient;
		this.pharmacy = pharmacy;
		this.drugInstance = drugInstance;
		this.count = count;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priceForOneDrug = priceForOneDrug;
		this.drugReservationStatus = drugReservationStatus;
	}
	
	
	public DrugReservation( Patient patient, Pharmacy pharmacy, DrugInstance drugInstance, int count,
			Date endDate, double priceForOneDrug) {
		super();
		this.id = UUID.randomUUID();
		this.patient = patient;
		this.pharmacy = pharmacy;
		this.drugInstance = drugInstance;
		this.count = count;
		this.startDate = new Date();
		this.endDate = endDate;
		this.priceForOneDrug = priceForOneDrug;
		this.drugReservationStatus = drugReservationStatus.ACTIVE;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public DrugInstance getDrugInstance() {
		return drugInstance;
	}

	public void setDrugInstance(DrugInstance drugInstance) {
		this.drugInstance = drugInstance;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
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

	public double getPriceForOneDrug() {
		return priceForOneDrug;
	}

	public void setPriceForOneDrug(double priceForOneDrug) {
		this.priceForOneDrug = priceForOneDrug;
	}

	public DrugReservationStatus getDrugReservationStatus() {
		return drugReservationStatus;
	}

	public void setDrugReservationStatus(DrugReservationStatus drugReservationStatus) {
		this.drugReservationStatus = drugReservationStatus;
	}
	
	
	
	
	
}
