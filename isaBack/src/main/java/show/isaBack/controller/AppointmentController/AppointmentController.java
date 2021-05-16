package show.isaBack.controller.AppointmentController;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import show.isaBack.DTO.AppointmentDTO.DermatologistAppointmentDTO;
import show.isaBack.model.appointment.AppointmentType;
import show.isaBack.serviceInterfaces.IAppointmentService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;




@RestController
@RequestMapping(value = "/appointment")
public class AppointmentController {

	
	@Autowired
	private IAppointmentService appointmentService;
	
	
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@GetMapping("/dermatologist/allAppointmentsForchosenPharmacy/{pharmacyId}")
	public ResponseEntity<List<UnspecifiedDTO<DermatologistAppointmentDTO>>> findAllFreeAppointmentsByPharmacyAndAppointmentType(@PathVariable UUID pharmacyId) {
		
		System.out.println("1");
		System.out.println(pharmacyId);
		
		try {
			return new ResponseEntity<>(appointmentService.findAllFreeAppointmentsForPharmacyAndForAppointmentType(pharmacyId, AppointmentType.EXAMINATION) ,HttpStatus.OK);
		} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		
		
	}
	
	
}
