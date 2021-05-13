package show.isaBack.repository.userRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import show.isaBack.model.SystemAdmin;

public interface SystemAdminRepository extends JpaRepository<SystemAdmin, UUID> {
	
	

}
