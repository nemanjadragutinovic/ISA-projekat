package show.isaBack.repository.userRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import show.isaBack.model.UserCharacteristics.WorkTime;



public interface WorkTimeRepository extends JpaRepository<WorkTime, UUID>{
	
	

	@Query(value = "SELECT w from WorkTime w WHERE w.employee.userType = 'PHARMACIST'"
			+ " AND w.startDate <= ?1 AND w.endDate >= ?2 AND w.startTime <= ?3 AND w.startTime <= ?4 AND w.endTime >= ?3 AND w.endTime >= ?4 ")
	List<WorkTime> findAllWorkTimesInDateRange(Date startDate,Date endDate, int startHour,  int endHour);
	
	
	@Query(value = "SELECT w from WorkTime w WHERE w.employee.userType = 'PHARMACIST' AND w.pharmacy.id = ?5"
			+ " AND w.startDate <= ?1 AND w.endDate >= ?2 AND w.startTime <= ?3 AND w.startTime <= ?4 AND w.endTime >= ?3 AND w.endTime >= ?4  ")
	List<WorkTime> findAllWorkTimesInDateRangeForPharmacy(Date startDate,Date endDate, int startHour,  int endHour, UUID pharmacyId);
	
	
	@Query(value = "SELECT w from WorkTime w WHERE w.employee.userType = 'PHARMACIST' AND w.employee.id = ?5"
			+ " AND w.startDate <= ?1 AND w.endDate >= ?2 AND w.startTime <= ?3 AND w.startTime <= ?4 AND w.endTime >= ?3 AND w.endTime >= ?4  ")
	List<WorkTime> findAllWorkTimesInDateRangeForPharmacist(Date startDate,Date endDate, int startHour,  int endHour, UUID pharmacistId);
	

	@Query(value = "SELECT w FROM WorkTime w WHERE w.employee.id = ?1 AND w.startDate <= CURRENT_TIMESTAMP AND w.endDate >= CURRENT_TIMESTAMP")
	List<WorkTime> findWorkTimesForDeramtologistAndCurrentDate(UUID dermatologistId);

	@Query(value = "SELECT w FROM WorkTime w WHERE w.employee.id = ?1 AND w.pharmacy.id =?2  AND DATE(w.startDate)<=?3 AND DATE(w.endDate)>= ?3 ")
	WorkTime getDermatologistsWorkTimeForPharmacy(UUID dermatologistId, UUID pharmacyId ,Date date);
	
	@Query(value = "SELECT w FROM WorkTime w WHERE w.employee.id = ?1 AND w.pharmacy.id =?2")
	List<WorkTime> getDermatologistsWorkTimesForPharmacy(UUID dermatologistId, UUID pharmacyId);

}
