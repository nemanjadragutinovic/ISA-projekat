package show.isaBack.repository.userRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import show.isaBack.model.FreeDays;

public interface AbsenceRepository extends JpaRepository<FreeDays, UUID>{
	@Query(value = "SELECT a from FreeDays a WHERE a.user.id = ?1 ORDER BY a.startDate DESC")
	List<FreeDays> findAllAbsencesByStaff(UUID userId);
	
	@Query(value = "SELECT a from FreeDays a WHERE a.pharmacy.id=?1 AND a.freeDaysStatus = 'WAIT'")
	List<FreeDays> findAbsenceWaiting(UUID pharmacyId);
}
