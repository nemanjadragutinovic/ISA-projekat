package show.isaBack.serviceInterfaces;

import java.io.NotActiveException;
import java.util.UUID;

import show.isaBack.DTO.userDTO.ComplaintStaffDTO;

public interface IComplaintService {
	public UUID create(ComplaintStaffDTO entityDTO);
	

}
