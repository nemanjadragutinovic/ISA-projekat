package show.isaBack.model.drugs;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import show.isaBack.model.DrugInstance;

@Entity
public class DrugOrder {
	@Id
	private UUID id;
	
	@ManyToOne
	private DrugInstance drugInstance;
	
	@Column(name = "amount", nullable = false)
	int amount;

	
	public DrugOrder() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DrugOrder(UUID id, DrugInstance drugInstance, int amount) {
		super();
		this.id = id;
		this.drugInstance = drugInstance;
		this.amount = amount;
	}
	
	public DrugOrder(DrugInstance drugInstance, int amount) {
		super();
		this.id = UUID.randomUUID();
		this.drugInstance = drugInstance;
		this.amount = amount;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public DrugInstance getDrugInstance() {
		return drugInstance;
	}

	public void setDrugInstance(DrugInstance drugInstance) {
		this.drugInstance = drugInstance;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
}