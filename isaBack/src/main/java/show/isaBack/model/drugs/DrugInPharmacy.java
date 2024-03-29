package show.isaBack.model.drugs;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import show.isaBack.model.DrugInstance;
import show.isaBack.model.Pharmacy;

@Entity
public class DrugInPharmacy implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private UUID id;
	
	@ManyToOne(optional = false)
	private Pharmacy pharmacy;
	
	@ManyToOne(optional = false)
	private DrugInstance drugInstance;
		

	
	@Column( nullable = false)
	private int count;

	public DrugInPharmacy() {
		super();
	}

	public DrugInPharmacy( Pharmacy pharmacy, DrugInstance drug,int count) {
		super();
		this.id =UUID.randomUUID();
		this.pharmacy = pharmacy;
		this.drugInstance=drug;
		this.count = count;
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

	

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
	public void reduceCount(int reduceCount) {
		if(this.count - reduceCount < 0)
			throw new IllegalArgumentException("Not enough drugs in storage.");
		else
			this.count=this.count - reduceCount;
	}
	
	public void addCount(int addCount) {
			this.count=this.count + addCount;
	}
	
}
