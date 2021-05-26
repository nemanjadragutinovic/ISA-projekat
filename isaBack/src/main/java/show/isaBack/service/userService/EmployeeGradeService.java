package show.isaBack.service.userService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import show.isaBack.repository.userRepository.EmployeeGradeRepository;
import show.isaBack.serviceInterfaces.IEmployeeGradeService;

@Service
public class EmployeeGradeService implements IEmployeeGradeService{

	@Autowired
	EmployeeGradeRepository employeeGradeRepository;
	
	@Override
	public double getAvgGradeForEmployee(UUID employeeId) {
		double avgGrade;
		System.out.println("Boze");
		try {
			avgGrade = employeeGradeRepository.getAvgGradeForEmployee(employeeId);
		} catch (Exception e) {
			avgGrade = 0.0;
		}
		
		return avgGrade;
	}

	
	
}
