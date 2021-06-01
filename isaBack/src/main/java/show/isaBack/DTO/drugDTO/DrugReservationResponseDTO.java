package show.isaBack.DTO.drugDTO;

import java.util.Date;

import show.isaBack.DTO.pharmacyDTO.PharmacyDTO;
import show.isaBack.model.drugs.DrugReservationStatus;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;



public class DrugReservationResponseDTO {

	
		private UnspecifiedDTO<PharmacyDTO> pharmacy;
	    
	    private UnspecifiedDTO<DrugInstanceDTO> drugInstance;
	    
	    private int count;
		
	    private Date startDate;
		
	    private Date endDate;
		
		private double oneDrugPrice;

		private DrugReservationStatus drugReservationStatus;

		public DrugReservationResponseDTO() {
			super();
		}

		public DrugReservationResponseDTO(UnspecifiedDTO<PharmacyDTO> pharmacy,
				UnspecifiedDTO<DrugInstanceDTO> drugInstance, int count, Date startDate, Date endDate,
				double oneDrugPrice, DrugReservationStatus drugReservationStatus) {
			super();
			this.pharmacy = pharmacy;
			this.drugInstance = drugInstance;
			this.count = count;
			this.startDate = startDate;
			this.endDate = endDate;
			this.oneDrugPrice = oneDrugPrice;
			this.drugReservationStatus = drugReservationStatus;
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

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
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

		public double getOneDrugPrice() {
			return oneDrugPrice;
		}

		public void setOneDrugPrice(double oneDrugPrice) {
			this.oneDrugPrice = oneDrugPrice;
		}

		public DrugReservationStatus getDrugReservationStatus() {
			return drugReservationStatus;
		}

		public void setDrugReservationStatus(DrugReservationStatus drugReservationStatus) {
			this.drugReservationStatus = drugReservationStatus;
		}
		
		
		
		
	
}
