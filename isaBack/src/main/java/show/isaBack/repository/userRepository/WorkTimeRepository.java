package show.isaBack.repository.userRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import show.isaBack.model.UserCharacteristics.WorkTime;



public interface WorkTimeRepository extends JpaRepository<WorkTime, UUID>{
	
	@Query(value = "SELECT w from WorkTime w WHERE w.employee.userType = 'PHARMACIST'"
			+ " AND NOT(w.startDate >= ?2 OR w.endDate <= ?1) AND NOT(w.startTime >= ?4 OR w.endTime <= ?3) ")
	List<WorkTime> findAllWorkTimesInDateRange(Date startDate,Date endDate, int startHour,  int endHour);

	
	@Query(value = "SELECT w from WorkTime w WHERE w.employee.userType = 'PHARMACIST' AND w.pharmacy.id = ?5"
			+ " AND NOT(w.startDate >= ?2 OR w.endDate <= ?1) AND NOT(w.startTime >= ?4 OR w.endTime <= ?3) ")
	List<WorkTime> findAllWorkTimesInDateRangeForPharmacy(Date startDate,Date endDate, int startHour,  int endHour, UUID pharmacyId);
	
	
	@Query(value = "SELECT w from WorkTime w WHERE w.employee.userType = 'PHARMACIST' AND w.employee.id = ?5"
			+ " AND NOT(w.startDate >= ?2 OR w.endDate <= ?1) AND NOT(w.startTime >= ?4 OR w.endTime <= ?3) ")
	List<WorkTime> findAllWorkTimesInDateRangeForPharmacist(Date startDate,Date endDate, int startHour,  int endHour, UUID pharmacistId);
	
	
}
