package show.isaBack.DTO.drugDTO;

import java.util.Date;

import show.isaBack.model.drugs.EReceiptStatus;

public class EreceiptWithPharmacyDTO {

private Date creationDate;
	
	private double price;
	
	private EReceiptStatus status;
	
	private String pharmacyName;

	public EreceiptWithPharmacyDTO() {
		super();
	}

	public EreceiptWithPharmacyDTO(Date creationDate, double price, EReceiptStatus status, String pharmacyName) {
		super();
		this.creationDate = creationDate;
		this.price = price;
		this.status = status;
		this.pharmacyName = pharmacyName;
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

	public String getPharmacyName() {
		return pharmacyName;
	}

	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}
	
	
	
}
