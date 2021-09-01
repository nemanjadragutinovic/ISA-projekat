package show.isaBack.DTO.drugDTO;

import java.util.Date;
import java.util.UUID;

public class EditDrugPriceDTO {
	
	private UUID pharmacyId;
	private UUID drugInstanceId;
	private Date startDate;
	private Date endDate;
	private double price;
	
	public EditDrugPriceDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EditDrugPriceDTO(UUID pharmacyId, UUID drugInstanceId,Date startDate, Date endDate, double price) {
		super();
		this.pharmacyId = pharmacyId;
		this.drugInstanceId = drugInstanceId;
		this.price = price;
		this.startDate= startDate;
		this.endDate=endDate;
	}
	public UUID getDrugInstanceId() {
		return drugInstanceId;
	}
	public void setDrugInstanceId(UUID drugInstanceId) {
		this.drugInstanceId = drugInstanceId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
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
	public UUID getPharmacyId() {
		return pharmacyId;
	}
	public void setPharmacyId(UUID pharmacyId) {
		this.pharmacyId = pharmacyId;
	}
	

}
