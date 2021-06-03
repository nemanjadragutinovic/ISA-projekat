package show.isaBack.controller.gradeController;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import show.isaBack.DTO.drugDTO.DrugGradeDTO;
import show.isaBack.DTO.userDTO.EmployeeForGradeDTO;
import show.isaBack.DTO.userDTO.EmployeeForGradeRequestDTO;
import show.isaBack.serviceInterfaces.IDrugGradeService;
import show.isaBack.serviceInterfaces.IEmployeeGradeService;
import show.isaBack.serviceInterfaces.IPharmacyGradeService;

@RestController
@RequestMapping(value = "/grade")
public class GradeController {

	
	@Autowired
	private IEmployeeGradeService employeeGradeService;
	
	@Autowired
	private IPharmacyGradeService pharmacyGradeService;
	
	@Autowired
	private IDrugGradeService drugGradeService;
	
	@GetMapping("/employee/{staffId}")
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<EmployeeForGradeDTO> findPatientGradeForEmployee(@PathVariable UUID staffId) {
		System.out.println("1");
		try {
			return new ResponseEntity<>(employeeGradeService.findPatientGradeForEmployee(staffId) ,HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}  catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/employee/createGrade")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<?> createEmployeeGrade(@RequestBody EmployeeForGradeRequestDTO employeeForGradeDTO) {
		System.out.println(employeeForGradeDTO.getGrade());
		try {
			employeeGradeService.createEmployeeGrade(employeeForGradeDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/employee/updateGrade")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<?> updateEmployeeGrade(@RequestBody EmployeeForGradeRequestDTO employeeForGradeDTO) {
		System.out.println(employeeForGradeDTO.getGrade());
		try {
			employeeGradeService.updateEmployeeGrade(employeeForGradeDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	@GetMapping("/drug/{drugId}")
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<DrugGradeDTO> findPatientGradeForDrug(@PathVariable UUID drugId) {
		System.out.println("1");
		try {
			return new ResponseEntity<>(drugGradeService.findPatientGradeForDrug(drugId) ,HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}  catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	@PostMapping("/drug/createGrade")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<?> createDrugGrade(@RequestBody DrugGradeDTO drugGradeDTO) {
		System.out.println(drugGradeDTO.getGrade() + "njeee");
		try {
			drugGradeService.createDrugGrade(drugGradeDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/drug/updateGrade")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<?> updateDrugGrade(@RequestBody DrugGradeDTO drugGradeDTO) {
		System.out.println(drugGradeDTO.getGrade() + "njeee1");
		try {
			drugGradeService.updateDrugGrade(drugGradeDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
