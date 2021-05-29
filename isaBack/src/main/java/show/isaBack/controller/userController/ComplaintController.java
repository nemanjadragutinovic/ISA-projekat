package show.isaBack.controller.userController;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import show.isaBack.DTO.userDTO.ComplaintPharmacyDTO;
import show.isaBack.DTO.userDTO.ComplaintStaffDTO;
import show.isaBack.serviceInterfaces.IAppointmentService;
import show.isaBack.serviceInterfaces.IComplaintService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

@RestController
@RequestMapping(value = "complaint")
public class ComplaintController {
	
	
	
	@Autowired
	private IComplaintService complaintService;
	
	@Autowired
	private IAppointmentService appointmentService;
	
	
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
	
	@PostMapping("/pharmacy")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<UUID> createComplaintPharmacy(@RequestBody ComplaintPharmacyDTO complaintPharmacyDTO) {
		System.out.println("usao u complaint pharmacy");
		
		if(!appointmentService.canPatientReportPharmacy(complaintPharmacyDTO.getPharmacyId())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		try {
			return new ResponseEntity<UUID>(complaintService.createPharmacyComplaint(complaintPharmacyDTO),HttpStatus.CREATED);
		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getStaffComplaints")
	@PreAuthorize("hasRole('SYSADMIN')")
	public ResponseEntity<List<UnspecifiedDTO<ComplaintStaffDTO>>> getStaffComplaints() {
		System.out.println("usao u get complaints");
		
		
		try {
			return new ResponseEntity<>(complaintService.findAllStaffComplaints(),HttpStatus.CREATED);
		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/replyToStaffComplaint")
	@PreAuthorize("hasRole('SYSADMIN')")
	public ResponseEntity<UUID> replyToStaffComplaint(@RequestBody ComplaintStaffDTO complaintStaffDTO) {
		System.out.println("usao u reply complaints");
		System.out.println(complaintStaffDTO.getReply());
		System.out.println(complaintStaffDTO.getComplaintId());
		
		try {
			return new ResponseEntity<>(complaintService.replyToPatient(complaintStaffDTO),HttpStatus.CREATED);
		
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
