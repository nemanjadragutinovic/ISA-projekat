package show.isaBack.controller.AppointmentController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import show.isaBack.DTO.AppointmentDTO.AppointmentReportDTO;
import show.isaBack.serviceInterfaces.IAppointmentReportService;

public class AppointmentReportController {
	@RestController
	@RequestMapping(value = "appointment-report")
	public class TreatmentReportController {
		@Autowired
		private IAppointmentReportService appointmentReportService;
		
		@PostMapping
		@CrossOrigin
		@PreAuthorize("hasRole('DERMATHOLOGIST') or hasRole('PHARMACIST')")
		public ResponseEntity<?> createTreatmentReport(@RequestBody AppointmentReportDTO treatmentReportDTO){
			try {
				if(appointmentReportService.create(treatmentReportDTO) != null)
					return new ResponseEntity<>(HttpStatus.OK);
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
}
}
