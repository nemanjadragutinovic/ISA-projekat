package show.isaBack.controller.pharmacyController;

import java.util.List;

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

import show.isaBack.DTO.drugDTO.NewOrderDTO;
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
	
	@PostMapping("/aaaa")
	@PreAuthorize("hasRole('PHARMACYADMIN')")
	public ResponseEntity<?> create(@RequestBody NewOrderDTO createOrderDTO) {
		try {
			System.out.println("aaaaaaaaaaaaaaa");
			return new ResponseEntity<>(orderService.createNewOreder(createOrderDTO),HttpStatus.CREATED);

		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	

}
