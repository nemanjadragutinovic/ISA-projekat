package show.isaBack.model.drugs;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import show.isaBack.model.DrugInstance;
import show.isaBack.model.Pharmacy;
@Entity
public class DrugPriceList {
	@Id
	private UUID id;
	
	@ManyToOne(optional = false)
	private Pharmacy pharmacy;
	
	@ManyToOne(optional = false)
	private DrugInstance drugInstance;
		
	@Column(nullable = false)
	private Date dateFrom;
	
	@Column( nullable = false)
	private Date dateTo;
	
	@Column( nullable = false)
	private double price;
	
	
	public DrugPriceList() {
		super();
	}

	public DrugPriceList( Pharmacy pharmacy, DrugInstance drug, Date dateFrom, Date dateTo, double price) {
		super();
		this.id =UUID.randomUUID();
		this.pharmacy = pharmacy;
		this.drugInstance = drug;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.price = price;
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

	public DrugInstance getDrug() {
		return drugInstance;
	}

	public void setDrug(DrugInstance drug) {
		this.drugInstance = drug;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
