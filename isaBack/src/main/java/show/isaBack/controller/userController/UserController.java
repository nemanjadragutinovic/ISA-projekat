package show.isaBack.controller.userController;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import show.isaBack.DTO.drugDTO.AllergenDTO;
import show.isaBack.DTO.pharmacyDTO.PharmacyDTO;
import show.isaBack.DTO.userDTO.ChangePasswordDTO;
import show.isaBack.DTO.userDTO.EmployeeGradeDTO;
import show.isaBack.DTO.userDTO.NewDermatologistInPharmacyDTO;
import show.isaBack.DTO.userDTO.PatientDTO;
import show.isaBack.DTO.userDTO.PatientsAllergenDTO;
import show.isaBack.DTO.userDTO.PhAdminDTO;
import show.isaBack.DTO.userDTO.PharmacistForAppointmentPharmacyGadeDTO;
import show.isaBack.DTO.userDTO.RemoveDermatologistDTO;
import show.isaBack.DTO.userDTO.UserChangeInfoDTO;
import show.isaBack.DTO.userDTO.UserDTO;
import show.isaBack.DTO.userDTO.WorkTimeDTO;
import show.isaBack.security.TokenUtils;
import show.isaBack.service.userService.UserService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;






@RestController
@RequestMapping(value = "/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	 @Autowired
	 private TokenUtils tokenUtils;
	
	
	@GetMapping("/patient")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<UnspecifiedDTO<PatientDTO>> getLogedPatient(HttpServletRequest request) {
		
		String authortity = tokenUtils.getAuthHeaderFromHeader(request);
		System.out.println( "authority je " + authortity );
		
		try {
			UnspecifiedDTO<PatientDTO> patient = userService.getLoggedPatient();
			
			for (UnspecifiedDTO<AllergenDTO> a : patient.EntityDTO.getAllergens()) {
				System.out.println(a.EntityDTO.getName());
				
			}
			
			return new ResponseEntity<>(patient,HttpStatus.OK); 
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} 
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		
	}
	

	@GetMapping("/phadmin")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<UnspecifiedDTO<PhAdminDTO>> getLogedPhAdimn(HttpServletRequest request) {
		try {
			UnspecifiedDTO<PhAdminDTO> phAdmin = userService.getLoggedPhAdmin();
			
			return new ResponseEntity<>(phAdmin,HttpStatus.OK);  
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} 
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		
	}

	@GetMapping("/supplier")
	@PreAuthorize("hasRole('ROLE_SUPPLIER')")
	public ResponseEntity<UserDTO> getLoggedSupplier(HttpServletRequest request) {
		
	
		System.out.println("usao u suppliera");
		
		try {
			UserDTO supp = userService.getLoggedSupplier();
			
			
			System.out.println(supp.getName());
			return new ResponseEntity<>(supp,HttpStatus.OK); 
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} 
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}			
	}
	
	@GetMapping("/dermathologist")
	@PreAuthorize("hasRole('ROLE_DERMATHOLOGIST')")
	public ResponseEntity<UserDTO> getLoggedDermathologist(HttpServletRequest request) {
		
	
		System.out.println("usao u dermatologa");
		
		try {
			UserDTO derm = userService.getLoggedDermathologist();
			
			
			System.out.println(derm.getName());
			return new ResponseEntity<>(derm,HttpStatus.OK); 
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} 
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}			
	}
	
	@GetMapping("/pharmacist")
	@PreAuthorize("hasRole('ROLE_PHARMACIST')")
	public ResponseEntity<UserDTO> getLoggedPharmacist(HttpServletRequest request) {
		
	
		System.out.println("usao u farmaceuta");
		
		try {
			UserDTO phar = userService.getLoggedPharmacist();
			
			
			System.out.println(phar.getName());
			return new ResponseEntity<>(phar,HttpStatus.OK); 
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} 
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}			
	}
	
	@PutMapping("/phadmin") 
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> updatePhAdminInfo(@RequestBody UserChangeInfoDTO userInfoChangeDTO ) {
	  
		try {
			userService.updatePhAdmin(userInfoChangeDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	

	
	@PutMapping("/patient") 
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<?> updatePatientInformation(@RequestBody UserChangeInfoDTO userInfoChangeDTO ) {
	  
		try {
			userService.updatePatient(userInfoChangeDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@PutMapping("/supplier") 
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_SUPPLIER')")
	public ResponseEntity<?> updateSupplierInformation(@RequestBody UserChangeInfoDTO userInfoChangeDTO ) {
	  
		try {
			userService.updateSupplier(userInfoChangeDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@PutMapping("/dermathologist") 
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_DERMATHOLOGIST')")
	public ResponseEntity<?> updateDermathologistInformation(@RequestBody UserChangeInfoDTO userInfoChangeDTO ) {
	  
		try {
			userService.updateDermathologist(userInfoChangeDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@PutMapping("/pharmacist") 
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_PHARMACIST')")
	public ResponseEntity<?> updatePharmacistInformation(@RequestBody UserChangeInfoDTO userInfoChangeDTO ) {
	  
		try {
			userService.updatePharmacist(userInfoChangeDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@PostMapping("/changePassword")
	@PreAuthorize("hasRole('ROLE_PATIENT') or hasRole('ROLE_SUPPLIER')")
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
		
		try {
			userService.changePassword(changePasswordDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch (BadCredentialsException e){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/changeFirstPassword")
	public ResponseEntity<?> changeFirstPassword(@RequestBody PasswordChanger passwordChanger) {
		
		try {
			userService.changeFirstPassword(passwordChanger.oldPassword, passwordChanger.newPassword);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (BadCredentialsException e){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}
	
	
	@PostMapping("/addPatientsAllergen") 
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<?> addAllergenForPatient(@RequestBody PatientsAllergenDTO patientsAllergenDTO) {
	  
		try {
			userService.addAllergenForPatient(patientsAllergenDTO);
				
			return new ResponseEntity<>(HttpStatus.OK); 
							
		}catch (IllegalArgumentException e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	
	@PutMapping("/removePatientsAllergen") 
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<?> removeAllergenForPatient(@RequestBody PatientsAllergenDTO patientsAllergenDTO) {
		
		try {
			userService.removeAllergenForPatient(patientsAllergenDTO);
				
			return new ResponseEntity<>(HttpStatus.OK); 
							
		}catch (IllegalArgumentException e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	
	
	@GetMapping("getAllPatientsAllergens") 
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<List<UnspecifiedDTO<AllergenDTO>>> getAllPatientsAllergens() {
	  
		try {
			List<UnspecifiedDTO<AllergenDTO>> allergens = userService.getAllPatientsAllergens();
			return new ResponseEntity<>(allergens,HttpStatus.OK); 
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	
	@GetMapping("/freePharmacistsForSelectedPharmacyInDataRange/{pharmacyId}/{DateTime}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	public ResponseEntity<List<UnspecifiedDTO<PharmacistForAppointmentPharmacyGadeDTO>>> fidnAllFreePharmacistsForSelectedPharmacyInDataRange(@PathVariable UUID pharmacyId, @PathVariable long DateTime){
		
		System.out.println(pharmacyId);
		System.out.println(DateTime + "    datuuum");
		
		
		Date startDate= new Date(DateTime);
		try {		
			return new ResponseEntity<>(userService.fidnAllFreePharmacistsForSelectedPharmacyInDataRange(startDate,pharmacyId),HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	
	@GetMapping("/freePharmacistsForSelectedPharmacyInDataRange/sortByGradeAscending/{pharmacyId}/{DateTime}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	public ResponseEntity<List<UnspecifiedDTO<PharmacistForAppointmentPharmacyGadeDTO>>> fidnAllFreePharmacistsForSelectedPharmacyInDataRangeSortByGradeAscending(@PathVariable UUID pharmacyId, @PathVariable long DateTime){
		
		System.out.println(pharmacyId);
		System.out.println(DateTime + "    datuuum");
		
		
		Date startDate= new Date(DateTime);
		try {		
			return new ResponseEntity<>(userService.fidnAllFreePharmacistsForSelectedPharmacyInDataRangeSortByGradeAscending(startDate,pharmacyId),HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	
	@GetMapping("/freePharmacistsForSelectedPharmacyInDataRange/sortByGradeDescending/{pharmacyId}/{DateTime}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@CrossOrigin
	public ResponseEntity<List<UnspecifiedDTO<PharmacistForAppointmentPharmacyGadeDTO>>> fidnAllFreePharmacistsForSelectedPharmacyInDataRangeSortByGradeDescending(@PathVariable UUID pharmacyId, @PathVariable long DateTime){
		
		System.out.println(pharmacyId);
		System.out.println(DateTime + "    datuuum");
		
		
		Date startDate= new Date(DateTime);
		try {		
			return new ResponseEntity<>(userService.fidnAllFreePharmacistsForSelectedPharmacyInDataRangeSortByGradeDescending(startDate,pharmacyId),HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@GetMapping("/subscribeToPharmacy/") 
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<?> subscribeToPharmacy(@RequestParam UUID pharmacyId ) {

		System.out.println("usao");
	  
		try {
			userService.subscribeToPharmacy(pharmacyId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@GetMapping("/unsubscribeFromPharmacy") 
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<?> unsubscribeToPharmacy(@RequestParam UUID pharmacyId ) {

		System.out.println("usao22");
		try {
			userService.unsubscribeFromPharmacy(pharmacyId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@CrossOrigin
	@GetMapping("/getIsPatientSubscribed") 
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<?> canPatientUseQR(@RequestParam UUID id) {
		System.out.println("is patint subed");
		System.out.println(userService.isPatientSubscribedToPharmacy(id));
		return new ResponseEntity<>(userService.isPatientSubscribedToPharmacy(id) ,HttpStatus.CREATED);
	}
	
	@GetMapping("/phIdForAdmin")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')") 
	public ResponseEntity<UUID> getPharmacyIdForPharmacyAdmin() {
		
		try {
			
			UUID phId = userService.getPhIdForPhAdmin();
			return new ResponseEntity<>(phId,HttpStatus.OK); 
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@GetMapping("/dermatologistsInPharmacy/{phId}")
	//@PreAuthorize("hasRole('PHARMACYADMIN')")
	@CrossOrigin
	public ResponseEntity<List<UnspecifiedDTO<EmployeeGradeDTO>>> getDermatologists(@PathVariable UUID phId){
		
		System.out.println(phId);
		
		try {		
			return new ResponseEntity<>(userService.findDermatologistsinPharmacy(phId),HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@GetMapping("/dermatologistsNotInPharmacy/{phId}")
	//@PreAuthorize("hasRole('PHARMACYADMIN')")
	@CrossOrigin
	public ResponseEntity<List<UnspecifiedDTO<EmployeeGradeDTO>>> getDermatologistsForPgarmacy(@PathVariable UUID phId){
		
		System.out.println(phId);
		
		try {		
			return new ResponseEntity<>(userService.findDermatologistsWhoDontWorkInPharmacy(phId),HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@PutMapping("/addDermatologistInPharmacy") 
	@PreAuthorize("hasRole('PHARMACYADMIN')")
	@CrossOrigin
	public ResponseEntity<?> addDermatologistInPharmacy(@RequestBody NewDermatologistInPharmacyDTO newDTO) {
		try {
			if(userService.addDermatologistInPharmacy(newDTO))
				return new ResponseEntity<>(HttpStatus.OK); 
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);	
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@PutMapping("/addWorkTime") 
	@PreAuthorize("hasRole('PHARMACYADMIN')")
	@CrossOrigin
	public ResponseEntity<?> addWorkTime(@RequestBody WorkTimeDTO workTimeDTO) {
		try {
			System.out.println("usao u work time");
			if(userService.addWorkTimeForEmployee(workTimeDTO)!=null)
				return new ResponseEntity<>(HttpStatus.OK); 
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);	
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	
	@GetMapping("/pharmacistsInPharmacy/{phId}")
	//@PreAuthorize("hasRole('PHARMACYADMIN')")
	@CrossOrigin
	public ResponseEntity<List<UnspecifiedDTO<EmployeeGradeDTO>>> getPharmacists(@PathVariable UUID phId){
		
		System.out.println(phId);
		
		try {		
			return new ResponseEntity<>(userService.findPharmacistsinPharmacy(phId),HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@GetMapping("/search") 
	@CrossOrigin
	//@PreAuthorize("hasRole('PHARMACIST') or hasRole('DERMATHOLOGIST')")
	public ResponseEntity<List<UnspecifiedDTO<UserDTO>>> findPatientByNameAndSurname(@RequestParam String name,@RequestParam String surname) {
		try {
			List<UnspecifiedDTO<UserDTO>> users = userService.findPatientByNameAndSurname(name, surname);
			return new ResponseEntity<>(users,HttpStatus.OK); 
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	

			@GetMapping("/refreshPatientPenalty") 
			@CrossOrigin
			@PreAuthorize("hasRole('ROLE_PATIENT')")
			public ResponseEntity<?> refreshPatientPenalty() {
				System.out.println("penalii");
				try {
					userService.refreshPatientPenalty();
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);  
				} catch (Exception e) {
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
				}
			}
			
			@GetMapping("/scheduleForEmployee/{id}") 	
			@PreAuthorize("hasRole('PHARMACYADMIN')")
			@CrossOrigin
			public ResponseEntity<List<UnspecifiedDTO<WorkTimeDTO>>> getWorkTimeForStaff(@PathVariable UUID id) {
			  
				try {
					List<UnspecifiedDTO<WorkTimeDTO>> schedule = userService.getScheduleForEmployee(id);
					return new ResponseEntity<>(schedule,HttpStatus.OK); 
				} catch (EntityNotFoundException e) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
				} catch (Exception e) {
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
				}
			}
			
			@PutMapping("/removeDermatologistFromPharmacy") 
			//@PreAuthorize("hasRole('PHARMACYADMIN')")
			@CrossOrigin
			public ResponseEntity<?> removeDermatologistFromPharmacy(@RequestBody RemoveDermatologistDTO removeDTO) {
				System.out.println("EJ ALO BIDIBOU");
				try {
					
					if(userService.removeDermatologistFromPharmacy(removeDTO.getDermatologistId(),removeDTO.getPharmacyId()))
						return new ResponseEntity<>(HttpStatus.OK); 
					
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);	
				} catch (Exception e) {
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
				}
			}
			
			@GetMapping("/dermatologistspharmacies/{dermatologistId}") 
			//@PreAuthorize("hasRole('PHARMACYADMIN') or hasRole('PATIENT')")
			@CrossOrigin
			public ResponseEntity<List<UnspecifiedDTO<PharmacyDTO>>> getAllPharmaciesbydermatologistId(@PathVariable UUID dermatologistId) {
			  
				try {
					List<UnspecifiedDTO<PharmacyDTO>> pharmacies = userService.findAllPharmaciesByDermatologistId(dermatologistId);
					return new ResponseEntity<>(pharmacies,HttpStatus.OK); 
				} catch (EntityNotFoundException e) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
				} catch (Exception e) {
					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
				}
			}
	
}
