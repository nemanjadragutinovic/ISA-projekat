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
import show.isaBack.DTO.drugDTO.PharmacyDrugPriceDTO;
import show.isaBack.DTO.drugDTO.PharmacyERecipeDTO;
import show.isaBack.DTO.pharmacyDTO.ActionPromotionDTO;
import show.isaBack.DTO.pharmacyDTO.PharmacyDTO;
import show.isaBack.DTO.pharmacyDTO.PharmacySearchDTO;
import show.isaBack.DTO.pharmacyDTO.PharmacyWithGradeAndPriceDTO;
import show.isaBack.DTO.pharmacyDTO.UnspecifiedPharmacyWithDrugAndPrice;
import show.isaBack.security.TokenUtils;

import show.isaBack.service.userService.UserService;

import show.isaBack.serviceInterfaces.IDrugService;

import show.isaBack.serviceInterfaces.IPharmacyService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

@RestController
@RequestMapping(value = "/pharmacy")
public class PharmacyController {
	
	
	
	@Autowired
	private IPharmacyService pharmacyService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	IDrugService drugService;
	
	
	@CrossOrigin
	@GetMapping("/allPharmacies") 
	public ResponseEntity<List<UnspecifiedDTO<PharmacyDTO>>> getAllPharmacies() {
		
		try {
		return new ResponseEntity<>(pharmacyService.getAllPharmacies() ,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
////////////////////////////////////////////////////////	
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@GetMapping("/allPharmacies/sortByNameAscending") 
	public ResponseEntity<List<UnspecifiedDTO<PharmacyDTO>>> getAllPharmaciesSortByNameAscending() {
		
		try {
		return new ResponseEntity<>(pharmacyService.getAllPharmaciesSortByNameAscending() ,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@GetMapping("/allPharmacies/sortByNameDescending") 
	public ResponseEntity<List<UnspecifiedDTO<PharmacyDTO>>> getAllPharmaciesSortByNameDescending() {
		
		try {
		return new ResponseEntity<>(pharmacyService.getAllPharmaciesSortByNameDescending() ,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@GetMapping("/allPharmacies/sortByCityAscending") 
	public ResponseEntity<List<UnspecifiedDTO<PharmacyDTO>>> getAllPharmaciesSortByCityAscending() {
		
		try {
		return new ResponseEntity<>(pharmacyService.getAllPharmaciesSortByCityAscending() ,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@GetMapping("/allPharmacies/sortByCityDescending") 
	public ResponseEntity<List<UnspecifiedDTO<PharmacyDTO>>> getAllPharmaciesSortByCityDescending() {
		
		try {
		return new ResponseEntity<>(pharmacyService.getAllPharmaciesSortByCityDescending() ,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@GetMapping("/allPharmacies/sortByGradeAscending") 
	public ResponseEntity<List<UnspecifiedDTO<PharmacyDTO>>> getAllPharmaciesSortByGradeAscending() {
		
		try {
		return new ResponseEntity<>(pharmacyService.getAllPharmaciesSortByGradeAscending() ,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@GetMapping("/allPharmacies/sortByGradeDescending") 
	public ResponseEntity<List<UnspecifiedDTO<PharmacyDTO>>> getAllPharmaciesSortByGradeDescending() {
		
		try {
		return new ResponseEntity<>(pharmacyService.getAllPharmaciesSortByGradeDescending() ,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
	/////////////////////////////////////////////////
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@GetMapping("/allPatientsSubscribedPharmacies") 
	public ResponseEntity<List<UnspecifiedDTO<PharmacyDTO>>> getAllPatientSubscribedPharmacies() {
		
		try {
		return new ResponseEntity<>(pharmacyService.getAllPatientSubscribedPharmacies() ,HttpStatus.OK);
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
	//@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<UnspecifiedDTO<PharmacyWithGradeAndPriceDTO>> getPharmacyInfo(@PathVariable UUID phId) {
		try {
			
			return new ResponseEntity<>(pharmacyService.convertPharmacyToPharmacyWithGradeAndPriceDTO(phId),HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	

	@GetMapping("/findPharmaciesByDrugIdWithDrugPrice/{drugId}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<UnspecifiedPharmacyWithDrugAndPrice>> findPharmaciesByDrugIdWithDrugPrice(@PathVariable UUID drugId) {
		try {
			
			return new ResponseEntity<>(drugService.findPharmaciesByDrugIdWithDrugPrice(drugId),HttpStatus.OK);
		
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/qrPharmacieswithDrugs/{id}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<UnspecifiedDTO<PharmacyDrugPriceDTO>>> getAllPharmaciesWithDrugs(@PathVariable UUID id) {
		System.out.println("qr1231234");
		return new ResponseEntity<List<UnspecifiedDTO<PharmacyDrugPriceDTO>>>(pharmacyService.getAllPharmaciesWithDrugs(id),HttpStatus.OK);
		
	}
	
	@PostMapping("/buyDrugsWithQr")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<?> buyDrugsWithQr(@RequestBody PharmacyERecipeDTO pharmacyERecipeDTO) {
		
		System.out.println("by qr");
		
		return new ResponseEntity<>(pharmacyService.buyDrugsWithQr(pharmacyERecipeDTO),HttpStatus.CREATED);
		
	}
	
	@GetMapping("/qrSort-name/{id}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<UnspecifiedDTO<PharmacyDrugPriceDTO>>> sortQrPharmaciesByName(@PathVariable UUID id){
	
		List<UnspecifiedDTO<PharmacyDrugPriceDTO>> pharmacies = pharmacyService.sortQrPharmaciesByName(id);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}
	
	@GetMapping("/qrSort-nameReverse/{id}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<UnspecifiedDTO<PharmacyDrugPriceDTO>>> sortQrPharmaciesByNameReverse(@PathVariable UUID id){
	
		List<UnspecifiedDTO<PharmacyDrugPriceDTO>> pharmacies = pharmacyService.sortQrPharmaciesByNameReverse(id);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}
	@GetMapping("/qrSort-price/{id}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<UnspecifiedDTO<PharmacyDrugPriceDTO>>> sortQrPharmaciesByPrice(@PathVariable UUID id){
	
		List<UnspecifiedDTO<PharmacyDrugPriceDTO>> pharmacies = pharmacyService.sortQrPharmaciesByPrice(id);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}
	@GetMapping("/qrSort-priceReverse/{id}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<UnspecifiedDTO<PharmacyDrugPriceDTO>>> sortQrPharmaciesByPriceReverse(@PathVariable UUID id){
	
		List<UnspecifiedDTO<PharmacyDrugPriceDTO>> pharmacies = pharmacyService.sortQrPharmaciesByPriceReverse(id);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}
	@GetMapping("/qrSort-grade/{id}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<UnspecifiedDTO<PharmacyDrugPriceDTO>>> sortQrPharmaciesByGrade(@PathVariable UUID id){
	
		List<UnspecifiedDTO<PharmacyDrugPriceDTO>> pharmacies = pharmacyService.sortQrPharmaciesByGrade(id);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}
	@GetMapping("/qrSort-gradeReverse/{id}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<UnspecifiedDTO<PharmacyDrugPriceDTO>>> sortQrPharmaciesByGradeReverse(@PathVariable UUID id){
	
		List<UnspecifiedDTO<PharmacyDrugPriceDTO>> pharmacies = pharmacyService.sortQrPharmaciesByGradeReverse(id);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}
	@GetMapping("/qrSort-address/{id}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<UnspecifiedDTO<PharmacyDrugPriceDTO>>> sortQrPharmaciesByAddress(@PathVariable UUID id){
	
		List<UnspecifiedDTO<PharmacyDrugPriceDTO>> pharmacies = pharmacyService.sortQrPharmaciesByAddress(id);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}
	
	
	@GetMapping("/qrSort-addressReverse/{id}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<UnspecifiedDTO<PharmacyDrugPriceDTO>>> sortQrPharmaciesByAddressReverse(@PathVariable UUID id){
	
		List<UnspecifiedDTO<PharmacyDrugPriceDTO>> pharmacies = pharmacyService.sortQrPharmaciesByAddressReverse(id);
		
		return new ResponseEntity<>(pharmacies, HttpStatus.OK);
	}

	

	@PostMapping("/addNewActionPromotion/{pharmacyId}")
	@CrossOrigin
	@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<?> createActionAndPromotion(@PathVariable UUID pharmacyId,@RequestBody ActionPromotionDTO actionPromotionDTO) {
		try {
			System.out.println("Usao je");
			if(pharmacyService.createNewActionPromtion(pharmacyId,actionPromotionDTO))
				return new ResponseEntity<>(HttpStatus.CREATED);
			else
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin
	@GetMapping("/getAllActionsInPharmacy/{pharmacyId}")
	//@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<List<UnspecifiedDTO<ActionPromotionDTO>>> getAllActionAndPromotionInPharmacy(@PathVariable UUID pharmacyId) {
		try {
			return new ResponseEntity<>(pharmacyService.getAllActionsInPharmacy(pharmacyId),HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
