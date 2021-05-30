package show.isaBack.model.drugs;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import show.isaBack.model.DrugInstance;
import show.isaBack.model.Supplier;

@Embeddable
public class SupplierDrugStorageId implements Serializable{
	
	

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	private DrugInstance drugInstance;
	
	@ManyToOne(optional = false)
	private Supplier supplier;

	public SupplierDrugStorageId() {}
	
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	public SupplierDrugStorageId(DrugInstance drugInstance) {
		super();
		this.drugInstance = drugInstance;	
	}

	public DrugInstance getDrugInstance() {
		return drugInstance;
	}

	public void setDrugInstance(DrugInstance drugInstance) {
		this.drugInstance = drugInstance;
	}

}
