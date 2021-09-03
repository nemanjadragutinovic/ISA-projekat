package show.isaBack.controller.userController;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import show.isaBack.DTO.userDTO.AbsenceDTO;
import show.isaBack.DTO.userDTO.RequestAbsenceDTO;
import show.isaBack.service.userService.AbsenceService;
import show.isaBack.serviceInterfaces.IAbsenceService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

@RestController
@RequestMapping(value = "/absence")
public class AbsenceController {
	
	@Autowired
	private AbsenceService absenceService;
	
	@PostMapping("/request")
	@CrossOrigin
	@PreAuthorize("hasRole('DERMATHOLOGIST') or hasRole('PHARMACIST')")
	public ResponseEntity<?>requestAbsence(@RequestBody RequestAbsenceDTO requestAbsenceDTO) {
		try {
			absenceService.createAbsence(requestAbsenceDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PreAuthorize("hasRole('DERMATHOLOGIST') or hasRole('PHARMACIST')")
	@CrossOrigin
	@GetMapping
	public ResponseEntity<?>getAbsences() {
	  
		List<UnspecifiedDTO<AbsenceDTO>> absences = absenceService.getAbsencesAsEmployee();
	  
		return new ResponseEntity<>(absences,HttpStatus.OK); 
	}
	
}
