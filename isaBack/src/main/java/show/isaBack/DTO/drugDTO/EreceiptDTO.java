package show.isaBack.DTO.drugDTO;

import java.util.Date;
import java.util.List;


import show.isaBack.model.drugs.EReceiptStatus;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public class EreceiptDTO {

	private Date creationDate;
	
	private double price;
	
	private EReceiptStatus status;
	
	private List<UnspecifiedDTO<DrugEReceiptDTO>> drugs;
	

	public EreceiptDTO() {
		super();
	}


	public EreceiptDTO(Date creationDate, double price, EReceiptStatus status,
			List<UnspecifiedDTO<DrugEReceiptDTO>> drugs) {
		super();
		this.creationDate = creationDate;
		this.price = price;
		this.status = status;
		this.drugs = drugs;
	}


	public Date getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public EReceiptStatus getStatus() {
		return status;
	}


	public void setStatus(EReceiptStatus status) {
		this.status = status;
	}


	public List<UnspecifiedDTO<DrugEReceiptDTO>> getDrugs() {
		return drugs;
	}


	public void setDrugs(List<UnspecifiedDTO<DrugEReceiptDTO>> drugs) {
		this.drugs = drugs;
	}

	

	
	
	
}
