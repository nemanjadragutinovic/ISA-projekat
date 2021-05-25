package show.isaBack.service.pharmacyService;

import java.util.UUID;

import org.springframework.aop.AopInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import show.isaBack.repository.Pharmacy.PharmacyGradeRepository;
import show.isaBack.serviceInterfaces.IPharmacyGradeService;

@Service
public class PharmacyGradeService implements IPharmacyGradeService {
	
	@Autowired
	PharmacyGradeRepository PharmacyGradeRepository;
	
	
	@Override
	public double getAvgGradeForPharmacy(UUID pharmacyId) {
		
		double avgGrade;
		
		try {
			avgGrade = PharmacyGradeRepository.getAvgGradeForPharmacy(pharmacyId);
		} catch (Exception e) {
			avgGrade = 0.0;
		}
		
		return avgGrade;
	}
	
	
}
