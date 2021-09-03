package show.isaBack.DTO.drugDTO;

import java.util.Date;

import show.isaBack.DTO.pharmacyDTO.PharmacyDTO;
import show.isaBack.model.drugs.ReservationStatus;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public class DrugReservation2DTO {
	private UnspecifiedDTO<PharmacyDTO> pharmacy;
    
    private UnspecifiedDTO<DrugInstanceDTO> drugInstance;
    
    private int amount;
	
    private Date startDate;
	
    private Date endDate;
	
	private double drugPeacePrice;

	private ReservationStatus reservationStatus;

	public DrugReservation2DTO() {}
	
	public DrugReservation2DTO(UnspecifiedDTO<PharmacyDTO> pharmacy, UnspecifiedDTO<DrugInstanceDTO> drugInstance, int amount, Date startDate,
			Date endDate, double drugPeacePrice, ReservationStatus reservationStatus) {
		super();
		this.pharmacy = pharmacy;
		this.drugInstance = drugInstance;
		this.amount = amount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.drugPeacePrice = drugPeacePrice;
		this.reservationStatus = reservationStatus;
	}

	public UnspecifiedDTO<PharmacyDTO> getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(UnspecifiedDTO<PharmacyDTO> pharmacy) {
		this.pharmacy = pharmacy;
	}

	public UnspecifiedDTO<DrugInstanceDTO> getDrugInstance() {
		return drugInstance;
	}

	public void setDrugInstance(UnspecifiedDTO<DrugInstanceDTO> drugInstance) {
		this.drugInstance = drugInstance;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
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

	public double getDrugPeacePrice() {
		return drugPeacePrice;
	}

	public void setDrugPeacePrice(double drugPeacePrice) {
		this.drugPeacePrice = drugPeacePrice;
	}

	public ReservationStatus getReservationStatus() {
		return reservationStatus;
	}

	public void setReservationStatus(ReservationStatus reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	
	
}


