package show.isaBack.repository.userRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


import show.isaBack.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
	
	

}
