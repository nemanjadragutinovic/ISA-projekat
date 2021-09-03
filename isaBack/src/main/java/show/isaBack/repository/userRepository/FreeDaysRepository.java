package show.isaBack.repository.userRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import show.isaBack.model.FreeDays;

public interface FreeDaysRepository  extends JpaRepository<FreeDays, UUID>{

	@Query(value = "SELECT fd FROM FreeDays fd WHERE fd.user.id = ?1 AND DATE(fd.startDate) <= ?2 AND DATE(fd.endDate) >= ?2 AND fd.pharmacy.id =?3 AND fd.freeDaysStatus = 'ACCEPTED'")
	List<FreeDays> getFreeDaysFromStartDateForDermatologistInPharmacy(UUID user, Date startDateTime, UUID pharmacy);
}
