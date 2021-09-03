package show.isaBack.repository.pharmacyRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import show.isaBack.model.ActionPromotion;
import show.isaBack.model.ActionType;

public interface ActionPromotionRepository extends JpaRepository<ActionPromotion, UUID>{

	@Query(value = "SELECT a from ActionPromotion a WHERE a.pharmacy.id = ?1")
	List<ActionPromotion> getAllActionsForPharmacy(UUID pharmacyId);
	
	@Query(value = "SELECT a from ActionPromotion a WHERE a.pharmacy.id = ?1 "
			 + "AND a.actionType = ?2  AND DATE(?3) <= DATE(a.dateTo) AND DATE(a.dateFrom) <= DATE(?4)")
	List<ActionPromotion> getAllActionsInPeriod(UUID pharmacyId,ActionType actionType, Date dateFrom, Date dateTo);
	
	@Query(value = "SELECT a from ActionPromotion a WHERE a.pharmacy.id = ?1 AND"
			+ " a.dateFrom <= CURRENT_TIMESTAMP AND a.dateTo >= CURRENT_TIMESTAMP AND a.actionType = ?2")
	ActionPromotion findCurrentActionAndPromotionForPharmacyForActionType(UUID pharmacyId, ActionType actionType);
}