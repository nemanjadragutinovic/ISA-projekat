package show.isaBack.controller.pharmacyController;

import java.util.List;
import java.util.UUID;

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

import show.isaBack.DTO.AppointmentDTO.IdDTO;
import show.isaBack.DTO.drugDTO.DrugOrderDTO;
import show.isaBack.DTO.drugDTO.EditOrderDTO;
import show.isaBack.DTO.drugDTO.NewOrderDTO;
import show.isaBack.DTO.drugDTO.OrderForPhAdminDTO;
import show.isaBack.DTO.drugDTO.OrderForProviderDTO;
import show.isaBack.serviceInterfaces.IOrderService;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

@RestController
@RequestMapping(value = "order")
public class OrderController {
	
	@Autowired
	private IOrderService orderService;
	
	
	@CrossOrigin
	@GetMapping("/getAllOrders")
	@PreAuthorize("hasRole('SUPPLIER')") 
	public ResponseEntity<List<UnspecifiedDTO<OrderForProviderDTO>>> findAllForProvider() {
		return new ResponseEntity<>(orderService.findAllOrdersForSupplier(),HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/getAllOrdersForPharmacy/{pharmacyId}")
	//@PreAuthorize("hasRole('PHARMACYADMIN')") 
	public ResponseEntity<List<UnspecifiedDTO<OrderForPhAdminDTO>>> findAllForPhAdmin(@PathVariable UUID pharmacyId) {
		return new ResponseEntity<>(orderService.getAllOrdersForPharmacy(pharmacyId),HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/getAllDrugsInOrder/{orderId}")
	//@PreAuthorize("hasRole('PHARMACYADMIN')") 
	public ResponseEntity<List<DrugOrderDTO>> getDrugsInOrder(@PathVariable UUID orderId) {
		return new ResponseEntity<>(orderService.getAllOrderDrugs(orderId),HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping("/getAllAddDrugs/{orderId}")
	//@PreAuthorize("hasRole('PHARMACYADMIN')") 
	public ResponseEntity<List<DrugOrderDTO>> getDrugsAdd(@PathVariable UUID orderId) {
		return new ResponseEntity<>(orderService.getAllAddDrugs(orderId),HttpStatus.OK);
	}
	
	@CrossOrigin
	@PostMapping("/addNewOrder")
	@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<?> create(@RequestBody NewOrderDTO createOrderDTO) {
		try {
			System.out.println("aaaaaaaaaaaaaaa");
			return new ResponseEntity<>(orderService.createNewOreder(createOrderDTO),HttpStatus.CREATED);

		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin
	@PutMapping("/removeOrder")
	//@PreAuthorize("hasRole('PHARMACYADMIN')") 
	public ResponseEntity<?> removeOrder(@RequestBody IdDTO orderId) {
		if(orderService.removeOrder(orderId.getId()))
			return new ResponseEntity<>(HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@CrossOrigin
	@PutMapping("/updateOrder")
	@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<?> update(@RequestBody EditOrderDTO editOrderDTO) {
		try {
			orderService.updateOrder(editOrderDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin
	@GetMapping("/findProccesedOrders/{pharmacyId}")
	@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<List<UnspecifiedDTO<OrderForPhAdminDTO>>> getProcessedOrders(@PathVariable UUID pharmacyId) {
		try {
			return new ResponseEntity<>(orderService.getProcessedOrders(pharmacyId),HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin
	@GetMapping("/findCreatedOrders/{pharmacyId}")
	@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<List<UnspecifiedDTO<OrderForPhAdminDTO>>> getCreatedOrders(@PathVariable UUID pharmacyId) {
		try {
			return new ResponseEntity<>(orderService.getCreatedOrders(pharmacyId),HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

}
