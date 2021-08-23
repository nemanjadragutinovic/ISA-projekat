package show.isaBack.repository.pharmacyRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import show.isaBack.model.Drug;
import show.isaBack.model.Pharmacy;

public interface PharmacyRepository extends JpaRepository<Pharmacy, UUID>{

	
	@Query(value = "SELECT p from Pharmacy p WHERE LOWER(p.name) LIKE  %?1% AND LOWER(p.address.street) LIKE %?2% AND LOWER(p.address.city) LIKE %?3% AND LOWER(p.address.country) LIKE %?4% ")
	List<Pharmacy> getAllSearchedPharmacies(String name, String street,String city, String county );
	
	@Query(value = "SELECT p from Pharmacy p ORDER BY p.name ASC")
	List<Pharmacy> getAllPharmaciesSortByNameAscending();
	
	@Query(value = "SELECT p from Pharmacy p ORDER BY p.name DESC")
	List<Pharmacy> getAllPharmaciesSortByNameDescending();
	
	
	@Query(value = "SELECT p from Pharmacy p ORDER BY p.address.city ASC")
	List<Pharmacy> getAllPharmaciesSortByCityAscending();
	
	@Query(value = "SELECT p from Pharmacy p ORDER BY p.address.city DESC")
	List<Pharmacy> getAllPharmaciesSortByCityDescending();
	

}



