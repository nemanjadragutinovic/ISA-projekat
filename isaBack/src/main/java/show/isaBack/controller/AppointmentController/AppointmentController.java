package show.isaBack.controller.AppointmentController;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import show.isaBack.DTO.AppointmentDTO.DermatologistAppointmentDTO;
import show.isaBack.DTO.AppointmentDTO.IdDTO;
import show.isaBack.DTO.AppointmentDTO.ReservationConsultationDTO;
import show.isaBack.model.appointment.AppointmentType;
import show.isaBack.serviceInterfaces.IAppointmentService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;




@RestController
@RequestMapping(value = "/appointment")
public class AppointmentController {

	
	@Autowired
	private IAppointmentService appointmentService;
	
	
	
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	@GetMapping("/dermatologist/allAppointmentsForchosenPharmacy/{pharmacyId}")
	public ResponseEntity<List<UnspecifiedDTO<DermatologistAppointmentDTO>>> findAllFreeAppointmentsByPharmacyAndAppointmentType(@PathVariable UUID pharmacyId) {
		
		
		try {
			return new ResponseEntity<>(appointmentService.findAllFreeAppointmentsForPharmacyAndForAppointmentType(pharmacyId, AppointmentType.EXAMINATION) ,HttpStatus.OK);
		} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
		
	}
	
	
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	@PostMapping("/dermatologist/reserveFreeDermatologistAppointment")
	public ResponseEntity<?>  reserveFreeDermatologistAppointment(@RequestBody IdDTO appointmentId) {
		
		try {
			appointmentService.reserveDermatologistAppointment(appointmentId.getId());
			return new ResponseEntity<>(appointmentId,HttpStatus.OK);
		
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
			
		
	}
	
	
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	@GetMapping("/dermatologist/sortByPriceAscending/{pharmacyId}")
	public ResponseEntity<List<UnspecifiedDTO<DermatologistAppointmentDTO>>> sortByPriceAscendingAllFreeDermatologistAppointments(@PathVariable UUID pharmacyId) {
		
		try {
			return new ResponseEntity<>(appointmentService.sortByPriceAscendingAllFreeDermatologistAppointments(pharmacyId, AppointmentType.EXAMINATION) ,HttpStatus.OK);
		} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	@GetMapping("/dermatologist/sortByPriceDescending/{pharmacyId}")
	public ResponseEntity<List<UnspecifiedDTO<DermatologistAppointmentDTO>>> sortByPriceDescendingAllFreeDermatologistAppointments(@PathVariable UUID pharmacyId) {
		
		try {
			return new ResponseEntity<>(appointmentService.sortByPriceDescendingAllFreeDermatologistAppointments(pharmacyId, AppointmentType.EXAMINATION) ,HttpStatus.OK);
		} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	@GetMapping("/dermatologist/sortByDermatologistGradeAscending/{pharmacyId}")
	public ResponseEntity<List<UnspecifiedDTO<DermatologistAppointmentDTO>>> sortByDermatologistGradeAscendingAllFreeDermatologistAppointments(@PathVariable UUID pharmacyId) {
		
		try {
			return new ResponseEntity<>(appointmentService.sortByDermatologistGradeAscendingAllFreeDermatologistAppointments(pharmacyId, AppointmentType.EXAMINATION) ,HttpStatus.OK);
		} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	
	
	
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	@GetMapping("/dermatologist/sortByDermatologistGradeDescending/{pharmacyId}")
	public ResponseEntity<List<UnspecifiedDTO<DermatologistAppointmentDTO>>> sortByDermatologistGradeDescendingAllFreeDermatologistAppointments(@PathVariable UUID pharmacyId) {
		
		try {
			return new ResponseEntity<>(appointmentService.sortByDermatologistGradeDescendingAllFreeDermatologistAppointments(pharmacyId, AppointmentType.EXAMINATION) ,HttpStatus.OK);
		} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	@GetMapping("/dermatologist/findAllFuturePatientsAppointmets")
	public ResponseEntity<List<UnspecifiedDTO<DermatologistAppointmentDTO>>> findAllFuturePatientsAppointmets() {
		System.out.println("njee");
		try {
			return new ResponseEntity<>(appointmentService.findAllFuturePatientsAppointmets(AppointmentType.EXAMINATION) ,HttpStatus.OK);
		} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	@PostMapping("/dermatologist/cancelAppointment")
	public ResponseEntity<?>  cancelDermatologistAppointment(@RequestBody IdDTO appointmentId) {
		
		try {
			appointmentService.cancelDermatologistAppointment(appointmentId.getId());
			return new ResponseEntity<>(appointmentId,HttpStatus.OK);
		
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
			
		
	}
	
	
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	@GetMapping("/dermatologist/findAllHistoryPatientsAppointmets")
	public ResponseEntity<List<UnspecifiedDTO<DermatologistAppointmentDTO>>> findAllHistoryPatientsAppointmets() {
		System.out.println("njee");
		try {
			return new ResponseEntity<>(appointmentService.findAllHistoryPatientsAppointmets(AppointmentType.EXAMINATION) ,HttpStatus.OK);
		} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	@PostMapping("/reserveConsulationBySelectedPharmacist")
	public ResponseEntity<?> reserveConsulationBySelectedPharmacist(@RequestBody ReservationConsultationDTO reservationRequestDTO) {
			Date neki= new Date(reservationRequestDTO.getStartDate());
			System.out.println(reservationRequestDTO.getPharmacistId() + "    "+  neki);
		try {
			appointmentService.reserveConsulationBySelectedPharmacist(reservationRequestDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
			
		
	}
	
	
}
