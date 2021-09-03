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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import show.isaBack.DTO.AppointmentDTO.AppointmentDTO;
import show.isaBack.DTO.AppointmentDTO.AppointmentReportDTO;
import show.isaBack.DTO.AppointmentDTO.CreateAppointmentDTO;
import show.isaBack.DTO.AppointmentDTO.DermatologistAppointmentDTO;
import show.isaBack.DTO.AppointmentDTO.DermatologistCreateAppointmentDTO;
import show.isaBack.DTO.AppointmentDTO.FormAppointmentDTO;
import show.isaBack.DTO.AppointmentDTO.FreeAppointmentPeriodDTO;
import show.isaBack.DTO.AppointmentDTO.IdDTO;
import show.isaBack.DTO.AppointmentDTO.NewConsultationDTO;
import show.isaBack.DTO.AppointmentDTO.ParamsFromAppointmentDTO;
import show.isaBack.DTO.AppointmentDTO.ReservationConsultationDTO;
import show.isaBack.model.appointment.Appointment;
import show.isaBack.model.appointment.AppointmentTimeOverlappingWithOtherAppointmentException;
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
			appointmentService.cancelAppointment(appointmentId.getId());
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
	@PostMapping("/pharmacist/cancelAppointment")
	public ResponseEntity<?>  cancelPharmacistAppointment(@RequestBody IdDTO appointmentId) {
		
		try {
			appointmentService.cancelAppointment(appointmentId.getId());
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
		
		try {
			return new ResponseEntity<>(appointmentService.findAllHistoryPatientsAppointmets(AppointmentType.EXAMINATION) ,HttpStatus.OK);
		} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	@GetMapping("/dermatologist/findAllHistoryPatientsAppointmets/sortByPriceAscending/{appointmentType}")
	public ResponseEntity<List<UnspecifiedDTO<DermatologistAppointmentDTO>>> findAllHistoryPatientsAppointmetsSortByPriceAscending(@PathVariable AppointmentType appointmentType) {
			
		try {				
				return new ResponseEntity<>(appointmentService.findAllHistoryPatientsAppointmetsSortByPriceAscending(appointmentType) ,HttpStatus.OK);
			
		} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	@GetMapping("/dermatologist/findAllHistoryPatientsAppointmets/sortByPriceDescending/{appointmentType}")
	public ResponseEntity<List<UnspecifiedDTO<DermatologistAppointmentDTO>>> findAllHistoryPatientsAppointmetsSortByPriceDescending(@PathVariable AppointmentType appointmentType) {
		
		try {
			return new ResponseEntity<>(appointmentService.findAllHistoryPatientsAppointmetsSortByPriceDescending(appointmentType) ,HttpStatus.OK);
			
		} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	@GetMapping("/dermatologist/findAllHistoryPatientsAppointmets/sortByDateAscending/{appointmentType}")
	public ResponseEntity<List<UnspecifiedDTO<DermatologistAppointmentDTO>>> findAllHistoryPatientsAppointmetsSortByDateAscending(@PathVariable AppointmentType appointmentType) {
		
		try {
			return new ResponseEntity<>(appointmentService.findAllHistoryPatientsAppointmetsSortByDateAscending(appointmentType) ,HttpStatus.OK);
			
		} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	@GetMapping("/dermatologist/findAllHistoryPatientsAppointmets/sortByDateDescending/{appointmentType}")
	public ResponseEntity<List<UnspecifiedDTO<DermatologistAppointmentDTO>>> findAllHistoryPatientsAppointmetsSortByDateDescending(@PathVariable AppointmentType appointmentType) {
		
		try {
			return new ResponseEntity<>(appointmentService.findAllHistoryPatientsAppointmetsSortByDateDescending(appointmentType) ,HttpStatus.OK);
			
		} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	@GetMapping("/dermatologist/findAllHistoryPatientsAppointmets/sortByDurationAscending/{appointmentType}")
	public ResponseEntity<List<UnspecifiedDTO<DermatologistAppointmentDTO>>> findAllHistoryPatientsAppointmetsSortByDurationAscending(@PathVariable AppointmentType appointmentType) {
		
		try {
			return new ResponseEntity<>(appointmentService.findAllHistoryPatientsAppointmetsSortByDurationAscending(appointmentType) ,HttpStatus.OK);
			
		} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	@GetMapping("/dermatologist/findAllHistoryPatientsAppointmets/sortByDurationDescending/{appointmentType}")
	public ResponseEntity<List<UnspecifiedDTO<DermatologistAppointmentDTO>>> findAllHistoryPatientsAppointmetsSortByDurationDescending(@PathVariable AppointmentType appointmentType) {
		
		try {
			return new ResponseEntity<>(appointmentService.findAllHistoryPatientsAppointmetsSortByDurationDescending(appointmentType) ,HttpStatus.OK);
			
		} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	@GetMapping("/pharmacist/findAllHistoryPatientsAppointmets/sortByPriceAscending/{appointmentType}")
	public ResponseEntity<List<UnspecifiedDTO<DermatologistAppointmentDTO>>> findAllHistoryPatientsConsultationsSortByPriceAscending(@PathVariable AppointmentType appointmentType) {
			System.out.println("njee");
		try {				
				return new ResponseEntity<>(appointmentService.findAllHistoryPatientsConsultationsSortByPriceAscending(appointmentType) ,HttpStatus.OK);
			
		} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	@GetMapping("/pharmacist/findAllHistoryPatientsAppointmets/sortByPriceDescending/{appointmentType}")
	public ResponseEntity<List<UnspecifiedDTO<DermatologistAppointmentDTO>>> findAllHistoryPatientsConsultationsSortByPriceDescending(@PathVariable AppointmentType appointmentType) {
		
		try {
			return new ResponseEntity<>(appointmentService.findAllHistoryPatientsConsultationsSortByPriceDescending(appointmentType) ,HttpStatus.OK);
			
		} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	@GetMapping("/pharmacist/findAllHistoryPatientsAppointmets/sortByDateAscending/{appointmentType}")
	public ResponseEntity<List<UnspecifiedDTO<DermatologistAppointmentDTO>>> findAllHistoryPatientsConsultationsSortByDateAscending(@PathVariable AppointmentType appointmentType) {
			
		try {				
				return new ResponseEntity<>(appointmentService.findAllHistoryPatientsConsultationsSortByDateAscending(appointmentType) ,HttpStatus.OK);
			
		} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	@GetMapping("/pharmacist/findAllHistoryPatientsAppointmets/sortByDateDescending/{appointmentType}")
	public ResponseEntity<List<UnspecifiedDTO<DermatologistAppointmentDTO>>> findAllHistoryPatientsConsultationsSortByDateDescending(@PathVariable AppointmentType appointmentType) {
			
		try {				
				return new ResponseEntity<>(appointmentService.findAllHistoryPatientsConsultationsSortByDateDescending(appointmentType) ,HttpStatus.OK);
			
		} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	@GetMapping("/pharmacist/findAllHistoryPatientsAppointmets/sortByDurationAscending/{appointmentType}")
	public ResponseEntity<List<UnspecifiedDTO<DermatologistAppointmentDTO>>> findAllHistoryPatientsConsultationsSortByDurationAscending(@PathVariable AppointmentType appointmentType) {
			
		try {				
				return new ResponseEntity<>(appointmentService.findAllHistoryPatientsConsultationsSortByDurationAscending(appointmentType) ,HttpStatus.OK);
			
		} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	@GetMapping("/pharmacist/findAllHistoryPatientsAppointmets/sortByDurationDescending/{appointmentType}")
	public ResponseEntity<List<UnspecifiedDTO<DermatologistAppointmentDTO>>> findAllHistoryPatientsConsultationsSortByDurationDescending(@PathVariable AppointmentType appointmentType) {
			
		try {				
				return new ResponseEntity<>(appointmentService.findAllHistoryPatientsConsultationsSortByDurationDescending(appointmentType) ,HttpStatus.OK);
			
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
	
	
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	@GetMapping("/pharmacist/findAllFuturePatientsAppointmets")
	public ResponseEntity<List<UnspecifiedDTO<DermatologistAppointmentDTO>>> findAllFuturePatientsConsultations() {
		System.out.println("njee");
		try {
			return new ResponseEntity<>(appointmentService.findAllFuturePatientsConsultations(AppointmentType.CONSULTATION) ,HttpStatus.OK);
		} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	@GetMapping("/pharmacist/findAllHistoryPatientsAppointmets")
	public ResponseEntity<List<UnspecifiedDTO<DermatologistAppointmentDTO>>> findAllHistoryPatientsConsultations() {
		System.out.println("njee");
		try {
			return new ResponseEntity<>(appointmentService.findAllHistoryPatientsConsultations(AppointmentType.CONSULTATION) ,HttpStatus.OK);
		} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	
	@PostMapping("/createDermatologistsExaminatoin")
	@CrossOrigin
	//@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<Boolean> createDermatologistsExamination(@RequestBody FormAppointmentDTO appointmentDTO) {
		System.out.println("dfsadfsdfsdafsdfsdfsd");
		try {
		    return new ResponseEntity<>(appointmentService.createDermatologistsAppointment(appointmentDTO),HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/generateSuggestionsForTimePeriod")
	//@PreAuthorize("hasRole('PHARMACYADMIN')")
	@CrossOrigin
	public ResponseEntity<List<FreeAppointmentPeriodDTO>> getFreePeriods(@RequestBody ParamsFromAppointmentDTO paramsAAA) {
		try {
			System.out.println("aa"+paramsAAA.getDate());
			System.out.println("eeee"+paramsAAA.getDuration());
			System.out.println("Bidibou"+paramsAAA.getDermatologistId());
			System.out.println("Oijo"+paramsAAA.getPhId());
			return new ResponseEntity<>(appointmentService.generateListFreePeriods(paramsAAA),HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/dermatologist/calendar-for-pharmacy/{pharmacyId}")
	@PreAuthorize("hasRole('DERMATHOLOGIST')")
	public ResponseEntity<List<UnspecifiedDTO<AppointmentDTO>>> getCalendarAppointmentsByDermatologist(@PathVariable UUID pharmacyId) {
		return new ResponseEntity<>(appointmentService.getCalendarAppointmentsByDermatologist(pharmacyId),HttpStatus.OK);
	}
	
	@GetMapping("/pharmacist/calendar/{pharmacyId}")
	@PreAuthorize("hasRole('PHARMACIST')")
	public ResponseEntity<List<UnspecifiedDTO<AppointmentDTO>>> getCalendarAppointmentsByPharmacist(@PathVariable UUID pharmacyId) {
		return new ResponseEntity<>(appointmentService.getCalendarAppointmentsByPharmacist(pharmacyId),HttpStatus.OK);
	}
	
	@GetMapping("/patient/{patientId}")
	@PreAuthorize("hasRole('DERMATHOLOGIST') or hasRole('PHARMACIST')")
	public ResponseEntity<List<UnspecifiedDTO<AppointmentDTO>>> getAppointmentsByPatientAsEmpolyee(@PathVariable UUID patientId) {
		HttpStatus status = appointmentService.hasExaminedPatient(patientId) ? HttpStatus.CREATED : HttpStatus.OK;
		return new ResponseEntity<>(appointmentService.getAppointmentsByPatientAsEmpolyee(patientId), status);
	}
	
	@PutMapping("/patient-did-not-come")
	@PreAuthorize("hasRole('DERMATHOLOGIST') or hasRole('PHARMACIST')")
	@CrossOrigin
	public ResponseEntity<?> didNotShowUpToAppointment(@RequestBody IdDTO appointmentId) {
		try {
			appointmentService.didNotShowUpToAppointment(appointmentId.getId());
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/dermatologist-created-appointment")
	@PreAuthorize("hasRole('DERMATHOLOGIST')")
	public ResponseEntity<List<UnspecifiedDTO<AppointmentDTO>>> getAllAppointmentsByDermatologist() {
		return new ResponseEntity<>(appointmentService.getCreatedAppointmentsByDermatologist(),HttpStatus.OK);
	}
	
	@GetMapping("/{appointmentId}")
	@PreAuthorize("hasRole('DERMATHOLOGIST') or hasRole('PHARMACIST')")
	public ResponseEntity<?> getAppointment(@PathVariable UUID appointmentId) {
		try {
			return new ResponseEntity<>(appointmentService.getAppointment(appointmentId),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PutMapping("/finish")
	@PreAuthorize("hasRole('DERMATHOLOGIST') or hasRole('PHARMACIST')")
	@CrossOrigin
	public ResponseEntity<?> finishAppointment(@RequestBody IdDTO appointmentId) {
		try {
			appointmentService.finishAppointment(appointmentId.getId());
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/pharmacist/new/")
	@PreAuthorize("hasRole('PHARMACIST')")
	@CrossOrigin
	public ResponseEntity<?> newConsultationAppointment(@RequestBody NewConsultationDTO newConsultationDTO){
		try {
			System.out.println("PRVERA== ZA PACIJENTA");
			UUID appointmentId = appointmentService.newConsultation(newConsultationDTO);
			return new ResponseEntity<>(appointmentId, HttpStatus.CREATED);
		} catch (AppointmentTimeOverlappingWithOtherAppointmentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (AuthorizationServiceException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
		
	}
	
	@PostMapping("/dermatologist/new/")
	@PreAuthorize("hasRole('DERMATHOLOGIST')")
	@CrossOrigin
	public ResponseEntity<?> newExamination(@RequestBody  NewConsultationDTO newConsultationDTO){
		try {
			System.out.println("PRVERA== ZA PACIJENTA");
			UUID appointmentId = appointmentService.newExamination(newConsultationDTO);
			return new ResponseEntity<>(appointmentId, HttpStatus.CREATED);
		} //catch (AppointmentTimeOverlappingWithOtherAppointmentException e) {
			//return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		//}
	catch (AuthorizationServiceException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
		
	}
	
	@PostMapping("/schedule-appointment")
	@PreAuthorize("hasRole('DERMATHOLOGIST')")
	@CrossOrigin
	public ResponseEntity<?> scheduleAppointment(@RequestBody CreateAppointmentDTO createAppointmentDTO) {
		System.out.println("PRVERA== ZA PACIJENTA");
		boolean isSuccesfull = appointmentService.scheduleAppointment(createAppointmentDTO.getPatientId(), createAppointmentDTO.getAppointmentId());
		
		if(isSuccesfull) return new ResponseEntity<>(createAppointmentDTO,HttpStatus.CREATED);
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/free-periods-dermatologist")
	@PreAuthorize("hasRole('DERMATHOLOGIST')")
	@CrossOrigin
	public ResponseEntity<List<FreeAppointmentPeriodDTO>> getFreePeriodsDermatologist(@RequestParam long datetime,@RequestParam int duration) {
		return new ResponseEntity<>(appointmentService.getFreePeriodsDermatologist(new Date(datetime), duration),HttpStatus.OK);
	}
	
	@PostMapping("/create-and-schedule-appointment")
	@CrossOrigin
	@PreAuthorize("hasRole('DERMATHOLOGIST')")
	public ResponseEntity<?> createAppointment(@RequestBody DermatologistCreateAppointmentDTO appointmentDTO ) {
		try {
			if(appointmentService.createAndScheduleAppointment(appointmentDTO)!=null)
				return new ResponseEntity<>(HttpStatus.OK);
			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
