package show.isaBack.model.drugs;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Version;

import show.isaBack.model.DrugInstance;

@Entity
public class SupplierDrugStorage {
	
	@EmbeddedId
	private SupplierDrugStorageId drugStorageId;
	
	@Column(name = "count", nullable = false)
	private int count;

	@Version
	private Long version;
	
	public SupplierDrugStorage() {}
	
	public SupplierDrugStorage(DrugInstance drugInstance, int count) {
		super();
		drugStorageId = new SupplierDrugStorageId(drugInstance);
		this.count = count;
	}

	public DrugInstance getDrugInstance() {
		return drugStorageId.getDrugInstance();
	}

	public void setDrugInstance(DrugInstance drugInstance) {
		this.drugStorageId.setDrugInstance(drugInstance);
	}
	
	

	public SupplierDrugStorageId getDrugStorageId() {
		return drugStorageId;
	}

	public void setDrugStorageId(SupplierDrugStorageId drugStorageId) {
		this.drugStorageId = drugStorageId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public void addAmount(int amount) {
		this.count += amount;
	}
	
	public void reduceAmount(int amount) {
		if(this.count - amount < 0)
			throw new IllegalArgumentException("Not enough drugs in storage.");
		else
			this.count -= amount;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}
