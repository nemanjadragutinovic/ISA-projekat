package show.isaBack.repository.userRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import show.isaBack.model.ComplaintPharmacy;
import show.isaBack.model.ComplaintStaff;

public interface ComplaintPharmacyRepository extends JpaRepository<ComplaintPharmacy, UUID>{

}
