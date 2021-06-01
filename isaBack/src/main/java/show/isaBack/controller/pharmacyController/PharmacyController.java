package show.isaBack.controller.pharmacyController;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import show.isaBack.DTO.drugDTO.DrugDTO;
import show.isaBack.DTO.pharmacyDTO.PharmacyDTO;
import show.isaBack.DTO.pharmacyDTO.PharmacySearchDTO;
import show.isaBack.DTO.pharmacyDTO.PharmacyWithGradeAndPriceDTO;
import show.isaBack.security.TokenUtils;
import show.isaBack.service.userService.UserService;
import show.isaBack.serviceInterfaces.IPharmacyService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

@RestController
@RequestMapping(value = "/pharmacy")
public class PharmacyController {
	
	
	
	@Autowired
	private IPharmacyService pharmacyService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	
	@CrossOrigin
	@GetMapping("/allPharmacies") 
	public ResponseEntity<List<UnspecifiedDTO<PharmacyDTO>>> getAllPharmacies() {
		
		try {
		return new ResponseEntity<>(pharmacyService.getAllPharmacies() ,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

		
	@CrossOrigin
	@PostMapping("/searchPharmacies" ) 
	public ResponseEntity<List<UnspecifiedDTO<PharmacyDTO>>> getSearchedPharmacies (@RequestBody PharmacySearchDTO userRequest) {
					
		try {
			
			return new ResponseEntity<>(pharmacyService.getSearchedPharmacies(userRequest) ,HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_SYSADMIN')")
	public ResponseEntity<UUID> addPharmacy(@RequestBody PharmacyDTO pharmacyDTO) {

		UUID pharmacyID = pharmacyService.createPharmacy(pharmacyDTO);
		return new ResponseEntity<>(pharmacyID, HttpStatus.CREATED);
	}
	
	
	
	@GetMapping("/getAllFreePharmacyAppointmetsForSelectedDate/{DateTime}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<UnspecifiedDTO<PharmacyWithGradeAndPriceDTO>>> findAllPharmaciesWhoHaveFreeAppointmentsForPeriodWithGradesAndPrice(@PathVariable long DateTime){
		
		Date startDate= new Date(DateTime);
		
		
		try {		
			return new ResponseEntity<>(pharmacyService.findAllPharmaciesWhoHaveFreeAppointmentsForPeriodWithGradesAndPrice(startDate),HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	@GetMapping("/getAllFreePharmacyAppointmetsForSelectedDate/SortByPriceAscending/{DateTime}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<UnspecifiedDTO<PharmacyWithGradeAndPriceDTO>>> findAllPharmaciesWhoHaveFreeAppointmentsForPeriodWithGradesAndPriceSortByPriceAscending(@PathVariable long DateTime){
		
		Date startDate= new Date(DateTime);
			
		try {		
			return new ResponseEntity<>(pharmacyService.findAllPharmaciesWhoHaveFreeAppointmentsForPeriodWithGradesAndPriceSortByPriceAscending(startDate),HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getAllFreePharmacyAppointmetsForSelectedDate/SortByPriceDescending/{DateTime}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<UnspecifiedDTO<PharmacyWithGradeAndPriceDTO>>> findAllPharmaciesWhoHaveFreeAppointmentsForPeriodWithGradesAndPriceSortByPriceDescending(@PathVariable long DateTime){
		
		Date startDate= new Date(DateTime);
			
		try {		
			return new ResponseEntity<>(pharmacyService.findAllPharmaciesWhoHaveFreeAppointmentsForPeriodWithGradesAndPriceSortByPriceDescending(startDate),HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getAllFreePharmacyAppointmetsForSelectedDate/SortByPharmacyGradeAscending/{DateTime}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<UnspecifiedDTO<PharmacyWithGradeAndPriceDTO>>> findAllPharmaciesWhoHaveFreeAppointmentsForPeriodWithGradesAndPriceSortByPharmacyGradeAscending(@PathVariable long DateTime){
		
		Date startDate= new Date(DateTime);
			
		try {		
			return new ResponseEntity<>(pharmacyService.findAllPharmaciesWhoHaveFreeAppointmentsForPeriodWithGradesAndPriceSortByPharmacyGradeAscending(startDate),HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

	
	@GetMapping("/getAllFreePharmacyAppointmetsForSelectedDate/SortByPharmacyGradeDescending/{DateTime}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<UnspecifiedDTO<PharmacyWithGradeAndPriceDTO>>> findAllPharmaciesWhoHaveFreeAppointmentsForPeriodWithGradesAndPriceSortByPharmacyGradeDescending(@PathVariable long DateTime){
		
		Date startDate= new Date(DateTime);
				
		try {		
			return new ResponseEntity<>(pharmacyService.findAllPharmaciesWhoHaveFreeAppointmentsForPeriodWithGradesAndPriceSortByPharmacyGradeDescending(startDate),HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@CrossOrigin
	@PutMapping("/editPharmacy/{phId}")
	@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<?> updatePharmacy(@PathVariable UUID phId,@RequestBody PharmacyDTO pharmacyDTO) {
		try {
			pharmacyService.updatePharmacy(phId,pharmacyDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@CrossOrigin
	@GetMapping("/pharmacyInfo/{phId}")
	@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<UnspecifiedDTO<PharmacyWithGradeAndPriceDTO>> getPharmacyInfo(@PathVariable UUID phId) {
		try {
			
			return new ResponseEntity<>(pharmacyService.convertPharmacyToPharmacyWithGradeAndPriceDTO(phId),HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	

}
