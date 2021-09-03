package show.isaBack.DTO.drugDTO;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class NewOrderDTO {
  UUID pharmacyId;
  List<DrugOrderDTO> listOfDrugs;
  Date dateTo;
   
  
  public NewOrderDTO() {
	super();
	// TODO Auto-generated constructor stub
}


public NewOrderDTO(UUID pharmacyId, List<DrugOrderDTO> listOfDrugs, Date dateTo) {
	super();
	this.pharmacyId = pharmacyId;
	this.listOfDrugs = listOfDrugs;
	this.dateTo = dateTo;
}


public UUID getPharmacyId() {
	return pharmacyId;
}


public void setPharmacyId(UUID pharmacyId) {
	this.pharmacyId = pharmacyId;
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
