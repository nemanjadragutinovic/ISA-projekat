package show.isaBack.repository.userRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import show.isaBack.model.PharmacyAdmin;


public interface PharmacyAdminRepository extends JpaRepository<PharmacyAdmin, UUID> {
	
	

}
