package show.isaBack.serviceInterfaces;

import java.io.NotActiveException;
import java.util.List;
import java.util.UUID;

import show.isaBack.DTO.userDTO.ComplaintPharmacyDTO;
import show.isaBack.DTO.userDTO.ComplaintStaffDTO;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public interface IComplaintService {
	public UUID create(ComplaintStaffDTO entityDTO);
	public UUID createPharmacyComplaint(ComplaintPharmacyDTO entityDTO);
	public List<UnspecifiedDTO<ComplaintStaffDTO>> findAllStaffComplaints();
	
	

}
