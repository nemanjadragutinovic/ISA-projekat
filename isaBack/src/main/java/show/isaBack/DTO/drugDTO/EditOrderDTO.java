package show.isaBack.DTO.drugDTO;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class EditOrderDTO {
	  UUID orderId;
	  List<DrugOrderDTO> listOfDrugs;
	  Date dateTo;
	   
	  
	  public EditOrderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public EditOrderDTO(UUID orderId, List<DrugOrderDTO> listOfDrugs, Date dateTo) {
		super();
		this.orderId = orderId;
		this.listOfDrugs = listOfDrugs;
		this.dateTo = dateTo;
	}


	public UUID getOrderId() {
		return orderId;
	}


	public void setOrderId(UUID orderId) {
		this.orderId = orderId;
	}


	public List<DrugOrderDTO> getListOfDrugs() {
		return listOfDrugs;
	}


	public void setListOfDrugs(List<DrugOrderDTO> listOfDrugs) {
		this.listOfDrugs = listOfDrugs;
	}


	public Date getDateTo() {
		return dateTo;
	}


	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	  

}
