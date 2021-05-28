package show.isaBack.serviceInterfaces;

import java.io.NotActiveException;
import java.util.UUID;

import show.isaBack.DTO.userDTO.ComplaintPharmacyDTO;
import show.isaBack.DTO.userDTO.ComplaintStaffDTO;

public interface IComplaintService {
	public UUID create(ComplaintStaffDTO entityDTO);
	public UUID createPharmacyComplaint(ComplaintPharmacyDTO entityDTO);
	
	

}
