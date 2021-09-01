package show.isaBack.controller.drugController;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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


import show.isaBack.DTO.AppointmentDTO.IdDTO;
import show.isaBack.DTO.drugDTO.AddDrugDTO;
import show.isaBack.DTO.drugDTO.DrugDTO;
import show.isaBack.DTO.drugDTO.DrugFormatIdDTO;
import show.isaBack.DTO.drugDTO.DrugInstanceDTO;
import show.isaBack.DTO.drugDTO.DrugKindIdDTO;
import show.isaBack.DTO.drugDTO.DrugManufacturerDTO;
import show.isaBack.DTO.drugDTO.DrugReservationDTO;
import show.isaBack.DTO.drugDTO.DrugReservationResponseDTO;
import show.isaBack.DTO.drugDTO.DrugWithEreceiptsDTO;
import show.isaBack.DTO.drugDTO.DrugWithPriceDTO;
import show.isaBack.DTO.drugDTO.DrugsWithGradesDTO;
import show.isaBack.DTO.drugDTO.EditDrugPriceDTO;
import show.isaBack.DTO.drugDTO.EditStorageDTO;
import show.isaBack.DTO.drugDTO.EreceiptDTO;
import show.isaBack.DTO.drugDTO.IngredientDTO;
import show.isaBack.DTO.drugDTO.ManufacturerDTO;
import show.isaBack.DTO.drugDTO.ReplaceDrugIdDTO;
import show.isaBack.model.drugs.DrugStorageQuantityException;
import show.isaBack.model.drugs.EReceiptStatus;
import show.isaBack.serviceInterfaces.IDrugFormatService;
import show.isaBack.serviceInterfaces.IDrugKindIdService;
import show.isaBack.serviceInterfaces.IDrugService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;


@RestController
@RequestMapping(value = "/drug", produces = MediaType.APPLICATION_JSON_VALUE)

public class DrugController {

	@Autowired
	private IDrugService drugService;
	
	@Autowired
	private IDrugKindIdService drugKindIdService;
	@Autowired
	private IDrugFormatService drugFormatService;
	
	
	@CrossOrigin
	@GetMapping("/allDrugs") 
	public ResponseEntity<List<UnspecifiedDTO<DrugDTO>>> getAllDrugs() {
		
		try {
		return new ResponseEntity<>(drugService.getAllDrugs() ,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
/*	@CrossOrigin
	@PostMapping("/searchDrugs" ) 
	public ResponseEntity<List<UnspecifiedDTO<DrugDTO>>> getSearchedDrugs (@RequestBody DrugDTO userRequest) {
					
		try {
			
			return new ResponseEntity<>(drugService.getSearchedDrug(userRequest) ,HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} */
	
	@PutMapping("/add")
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_SYSADMIN')")
	public ResponseEntity<UUID> addDrugInstance(@RequestBody DrugInstanceDTO drugInstanceDTO) {
		System.out.println("usao u kontroler1");
		UUID drugInstanceId = drugService.create(drugInstanceDTO);
		
		return new ResponseEntity<>(drugInstanceId ,HttpStatus.CREATED);
	}
	
	
	
	@PutMapping("/replacement") 
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_SYSADMIN')")
	public ResponseEntity<UUID> addDrugReplacement(@RequestBody ReplaceDrugIdDTO replaceDrugIdDTO) {
		
		
		UUID drugInstanceId = drugService.addDrugReplacement(replaceDrugIdDTO.getId(), replaceDrugIdDTO.getReplacement_id());
		
		return new ResponseEntity<>(drugInstanceId ,HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@GetMapping("/manufacturers")
	public ResponseEntity<List<UnspecifiedDTO<ManufacturerDTO>>> findAllManufacturers() {
		return new ResponseEntity<>(drugService.findDrugManufacturers(),HttpStatus.OK);
	}
	
	@PutMapping("/manufacturer") 
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_SYSADMIN')")
	public ResponseEntity<UUID> addDrugManufacturer(@RequestBody DrugManufacturerDTO drugManufacturerDTO) {
		
		UUID drugInstanceId = drugService.addDrugManufacturer(drugManufacturerDTO.getDrug_id(), drugManufacturerDTO.getManufacturer_id());
		
		return new ResponseEntity<>(drugInstanceId ,HttpStatus.CREATED);
	}
	
	@PutMapping("/ingredient") 
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_SYSADMIN')")
	public ResponseEntity<UUID> addDrugIngredient(@RequestBody IngredientDTO ingredientDTO) {
		
		UUID drugInstanceId = drugService.addDrugIngredients(ingredientDTO.getId(), ingredientDTO);
		
		return new ResponseEntity<>(drugInstanceId ,HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@GetMapping("/drugkind")
	public ResponseEntity<List<UnspecifiedDTO<DrugKindIdDTO>>> findAllDrugKinds() {
		
		System.out.println("usao u drug kind");
		
		return new ResponseEntity<>(drugKindIdService.findAllDrugKinds() ,HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/drugformat")
	public ResponseEntity<List<UnspecifiedDTO<DrugFormatIdDTO>>> findAllDrugFormats() {
		
		return new ResponseEntity<>(drugFormatService.findAllDrugKinds() ,HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<UnspecifiedDTO<DrugInstanceDTO>>> findAll() {
		System.out.println("usao u get drug");
		return new ResponseEntity<>(drugService.findAllDrugKinds(),HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/searchDrugs") 
	public ResponseEntity<List<UnspecifiedDTO<DrugsWithGradesDTO>>> searchDrugs(@RequestParam String name, @RequestParam double gradeFrom, @RequestParam double gradeTo, @RequestParam String drugKind) {
		System.out.println("usao u pretragu");
		return new ResponseEntity<>(drugService.searchDrugs(name, gradeFrom, gradeTo, drugKind) ,HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@GetMapping("/searchDrugsInPharmacy") 
	public ResponseEntity<List<UnspecifiedDTO<DrugWithPriceDTO>>> searchDrugsInPharmacy(@RequestParam String name, @RequestParam double gradeFrom, @RequestParam double gradeTo, @RequestParam String manufacturer, @RequestParam UUID pharmacyId) {
		System.out.println("usao u pretragu");
		return new ResponseEntity<>(drugService.searchDrugsInPharmacy(name, gradeFrom, gradeTo, manufacturer,pharmacyId) ,HttpStatus.CREATED);
	}
	
	
	@CrossOrigin
	@GetMapping("/getDrugsWithGrade") 
	public ResponseEntity<List<UnspecifiedDTO<DrugsWithGradesDTO>>> findDrugsWithGrades() {
		System.out.println("usao u get drugs");
		return new ResponseEntity<>(drugService.findDrugsWithGrades() ,HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@GetMapping("/canPatientUseQR") 
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<?> canPatientUseQR(@RequestParam String id) {
		System.out.println("can patient use qr");
		System.out.println(id + "xixixi");
		return new ResponseEntity<>(drugService.isQrCodeValid(id) ,HttpStatus.CREATED);
	}
	
	@PostMapping("/reserveDrug")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<?> createDrugReservation(@RequestBody DrugReservationDTO drugReservationDTO) {
		try {
			drugService.createDrugReservation(drugReservationDTO); 
			return new ResponseEntity<>(HttpStatus.CREATED);
			
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/futurePatientsDrugsReservation")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<UnspecifiedDTO<DrugReservationResponseDTO>>> findAllFuturePatientsDrugReservation() {
		
		return new ResponseEntity<>(drugService.findAllFuturePatientsDrugReservation() ,HttpStatus.OK);
	}
	
	
	@GetMapping("/historyPatientsDrugsReservations")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<UnspecifiedDTO<DrugReservationResponseDTO>>> findAllhistoryPatientsDrugReservation() {
		
		return new ResponseEntity<>(drugService.findAllhistoryPatientsDrugReservation() ,HttpStatus.OK);
	}
	
	
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	@PostMapping("/cancelDrugReservation")
	public ResponseEntity<?>  cancelPatientDrugReservation(@RequestBody IdDTO drugIdObject) {
		
		try {
			drugService.cancelPatientDrugReservation(drugIdObject);
			return new ResponseEntity<>(HttpStatus.OK);
		
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
			
		
	}
	
	
	@GetMapping("/refreshPatientDrugsReservations") 
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<?> refreshPatientDrugsReservations() {
		System.out.println("drugRezervacije");
		try {
			drugService.refreshPatientDrugsReservations();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);  
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	
	
	@GetMapping("/all-patients-eReceipts")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<UnspecifiedDTO<EreceiptDTO>>> findAllPatientsEreceipts() {
		
		return new ResponseEntity<>(drugService.findAllPatientsEreceipts() ,HttpStatus.OK);
	}
	
	@GetMapping("/all-patients-eReceipts/SortByDateAscending")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<UnspecifiedDTO<EreceiptDTO>>> findAllPatientsEreceiptsSortByDateAscending() {
		
		return new ResponseEntity<>(drugService.findAllPatientsEreceiptsSortByDateAscending() ,HttpStatus.OK);
	}
	
	
	@GetMapping("/all-patients-eReceipts/SortByDateDescending")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<UnspecifiedDTO<EreceiptDTO>>> findAllPatientsEreceiptsSortByDateDescending() {
		
		return new ResponseEntity<>(drugService.findAllPatientsEreceiptsSortByDateDescending() ,HttpStatus.OK);
	}
	
	
	@GetMapping("/all-patients-eReceipts/SortByDateAscending/search/{searchStatus}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<UnspecifiedDTO<EreceiptDTO>>> findAllPatientsEreceiptsSortByDateAscendingWithStatus(@PathVariable String searchStatus) {
		
		if(searchStatus.equals(EReceiptStatus.NEW.toString())){
			
			return new ResponseEntity<>(drugService.findAllPatientsEreceiptsSortByDateAscendingWithStatus(EReceiptStatus.NEW) ,HttpStatus.OK);     
		}else if(searchStatus.equals(EReceiptStatus.REJECTED.toString())){
			return new ResponseEntity<>(drugService.findAllPatientsEreceiptsSortByDateAscendingWithStatus(EReceiptStatus.REJECTED) ,HttpStatus.OK);   
		}else {
			return new ResponseEntity<>(drugService.findAllPatientsEreceiptsSortByDateAscendingWithStatus(EReceiptStatus.PROCESSED) ,HttpStatus.OK);
		}
		
			
	}
	
	@GetMapping("/all-patients-eReceipts/SortByDateDescending/search/{searchStatus}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<UnspecifiedDTO<EreceiptDTO>>> findAllPatientsEreceiptsSortByDateDescendingWithStatus(@PathVariable String searchStatus) {
		
		if(searchStatus.equals(EReceiptStatus.NEW.toString())){
			
			return new ResponseEntity<>(drugService.findAllPatientsEreceiptsSortByDateDescendingWithStatus(EReceiptStatus.NEW) ,HttpStatus.OK);     
		}else if(searchStatus.equals(EReceiptStatus.REJECTED.toString())){
			return new ResponseEntity<>(drugService.findAllPatientsEreceiptsSortByDateDescendingWithStatus(EReceiptStatus.REJECTED) ,HttpStatus.OK);   
		}else {
			return new ResponseEntity<>(drugService.findAllPatientsEreceiptsSortByDateDescendingWithStatus(EReceiptStatus.PROCESSED) ,HttpStatus.OK);
		}
		
			
	}
	
	@GetMapping("/all-patients-eReceipts/search/{searchStatus}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<UnspecifiedDTO<EreceiptDTO>>> findAllPatientsEreceiptsWithStatus(@PathVariable String searchStatus) {
		
		if(searchStatus.equals(EReceiptStatus.NEW.toString())){
			
			return new ResponseEntity<>(drugService.findAllPatientsEreceiptsWithStatus(EReceiptStatus.NEW) ,HttpStatus.OK);     
		}else if(searchStatus.equals(EReceiptStatus.REJECTED.toString())){
			return new ResponseEntity<>(drugService.findAllPatientsEreceiptsWithStatus(EReceiptStatus.REJECTED) ,HttpStatus.OK);   
		}else {
			return new ResponseEntity<>(drugService.findAllPatientsEreceiptsWithStatus(EReceiptStatus.PROCESSED) ,HttpStatus.OK);
		}
		
			
	}
	
	
	@GetMapping("/patientsProccessedDrugs-eReceipts")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<UnspecifiedDTO<DrugWithEreceiptsDTO>>> findAllPatientsPRoccesedDrugsFromEreceipts() {
		
		return new ResponseEntity<>(drugService.findAllPatientsPRoccesedDrugsFromEreceipts() ,HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/drugsInPharmacy/{pharmacyId}")
	//@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<List<UnspecifiedDTO<DrugWithPriceDTO>>> findDrugsFromPharmacyWithPrice(@PathVariable UUID pharmacyId) {
		System.out.println("ALooo");
		return new ResponseEntity<>(drugService.findDrugsInPharmacyWithPrice(pharmacyId),HttpStatus.OK);
	}
	

	@GetMapping("/not-allergic/{patientId}")
	@PreAuthorize("hasRole('DERMATHOLOGIST') or hasRole('PHARMACIST')")
	public ResponseEntity<List<UnspecifiedDTO<DrugInstanceDTO>>> findDrugsPatientIsNotAllergicTo(@PathVariable UUID patientId) {
		try {
			return new ResponseEntity<>(drugService.findDrugsPatientIsNotAllergicTo(patientId) ,HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@CrossOrigin
	@GetMapping("/drugsWhichArentInPharmacy/{pharmacyId}")
	//@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<List<UnspecifiedDTO<DrugDTO>>> findDrugsWhichArentInPharmacy(@PathVariable UUID pharmacyId) {
		System.out.println("ALooo BidiBouuu");
		return new ResponseEntity<>(drugService.findDrugsWhichArentInPharmacy(pharmacyId),HttpStatus.OK);
	}
	
	@PutMapping("/addDrugInPharmacy")
	//@PreAuthorize("hasRole('PHARMACYADMIN')")
	@CrossOrigin
	public ResponseEntity<?> addDrug(@RequestBody AddDrugDTO addDTO) {
		System.out.println("ALooo");
		System.out.println(addDTO.getDrugId());
		try {
			drugService.addDrug(addDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	
	

	@CrossOrigin
	@PutMapping("/editPharmacyStorage")
	//@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<?> editPharmacyStorage(@RequestBody EditStorageDTO editStorageDTO) {
		try {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		/*	if(drugService.editPriceForDrug(editStorageAmountForDrugDTO))
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}*/
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin
	@PutMapping("/editDrugPriceInPharmacy")
	//@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<?> editDrugPriceInPharmacy(@RequestBody EditDrugPriceDTO editPriceDTO) {
		try {
			System.out.println("Efsdfgsdaffafsaf bidibouuuu");
			drugService.editDrugPrice(editPriceDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
			/*
			if(drugPriceInPharmacyService.editPriceForDrug(editPriceForDrugDTO))
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}*/
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
