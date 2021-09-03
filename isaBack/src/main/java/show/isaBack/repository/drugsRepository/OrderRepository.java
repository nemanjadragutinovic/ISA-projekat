package show.isaBack.repository.drugsRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import show.isaBack.model.drugs.Order;

public interface OrderRepository extends JpaRepository<Order, UUID>{
	@Query(value = "SELECT o from Order o WHERE o.pharmacy.id = ?1")
	List<Order> findPharmacyOrders(UUID pharmacyId);

	@Query(value = "SELECT o from Order o WHERE o.pharmacy.id = ?1 AND o.orderStatus='CREATED'")
	List<Order> findCreatedOrders(UUID pharmacyId);

	@Query(value = "SELECT o from Order o WHERE o.pharmacy.id = ?1 AND o.orderStatus='PROCESSED'")
	List<Order> findProcessedOredrs(UUID pharmacyId);
}
