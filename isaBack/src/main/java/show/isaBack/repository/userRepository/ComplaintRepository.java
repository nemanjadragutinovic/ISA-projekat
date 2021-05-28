package show.isaBack.repository.userRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import show.isaBack.model.ComplaintStaff;

public interface ComplaintRepository extends JpaRepository<ComplaintStaff, UUID>{

}
