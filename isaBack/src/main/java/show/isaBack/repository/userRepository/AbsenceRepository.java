package show.isaBack.repository.userRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import show.isaBack.model.FreeDays;

public interface AbsenceRepository extends JpaRepository<FreeDays, UUID>{
	@Query(value = "SELECT a from FreeDays a WHERE a.user.id = ?1 ORDER BY a.startDate DESC")
	List<FreeDays> findAllAbsencesByStaff(UUID userId);
	
	@Query(value = "SELECT a FROM FreeDays a WHERE a.user.id = ?1 AND DATE(a.startDate) <= ?2 AND DATE(a.endDate) >= ?2 AND a.pharmacy.id =?3 AND a.freeDaysStatus = 'ACCEPTED'")
	List<FreeDays> getAbsenceForDermatologistForDateForPharmacy(UUID staff, Date startDateTime, UUID pharmacy);
	
	@Query(value = "SELECT a from FreeDays a WHERE a.user.id = ?1 AND a.startDate <= ?2 AND a.endDate >= ?2 AND a.freeDaysStatus = 'ACCEPTED'")
	List<FreeDays> findPharmacistAbsenceByStaffIdAndDate(UUID staffId, Date date);

}
