package show.isaBack.controller.userController;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import show.isaBack.DTO.userDTO.ComplaintStaffDTO;
import show.isaBack.serviceInterfaces.IComplaintService;

@RestController
@RequestMapping(value = "complaint")
public class ComplaintController {
	
	
	
	@Autowired
	private IComplaintService complaintService;
	
	
	@PostMapping
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<UUID> createComplaintStuff(@RequestBody ComplaintStaffDTO complaintStaffDTO) {
		System.out.println("usao u complaint");
		System.out.println(complaintStaffDTO.getStaffName());
		
		try {
			return new ResponseEntity<UUID>(complaintService.create(complaintStaffDTO),HttpStatus.CREATED);
		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
