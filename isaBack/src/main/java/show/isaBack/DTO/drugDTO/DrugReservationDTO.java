package show.isaBack.DTO.drugDTO;

import java.util.Date;
import java.util.UUID;

public class DrugReservationDTO {

	private UUID drugId;
	
	private UUID pharmacyId;
	
	private int drugsCount;
	
	private double priceForOneDrug;
	
	private Date endDate;

	public DrugReservationDTO() {
		super();
	}

	public DrugReservationDTO(UUID drugId, UUID pharmacyId, int drugsCount, double priceForOneDrug, Date endDate) {
		super();
		this.drugId = drugId;
		this.pharmacyId = pharmacyId;
		this.drugsCount = drugsCount;
		this.priceForOneDrug = priceForOneDrug;
		this.endDate = endDate;
	}

	public UUID getDrugId() {
		return drugId;
	}

	public void setDrugId(UUID drugId) {
		this.drugId = drugId;
	}

	public UUID getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(UUID pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public int getDrugsCount() {
		return drugsCount;
	}

	public void setDrugsCount(int drugsCount) {
		this.drugsCount = drugsCount;
	}

	public double getPriceForOneDrug() {
		return priceForOneDrug;
	}

	public void setPriceForOneDrug(double priceForOneDrug) {
		this.priceForOneDrug = priceForOneDrug;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
	
	
}
