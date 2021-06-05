package show.isaBack.serviceInterfaces;

import java.util.UUID;

import show.isaBack.DTO.userDTO.EmployeeForGradeDTO;
import show.isaBack.DTO.userDTO.EmployeeForGradeRequestDTO;

public interface IEmployeeGradeService {
	
	public double getAvgGradeForEmployee(UUID employeeId);
	public EmployeeForGradeDTO findPatientGradeForEmployee(UUID employeeId);
	public void createEmployeeGrade(EmployeeForGradeRequestDTO employeeForGradeDTO); 
	public void updateEmployeeGrade(EmployeeForGradeRequestDTO employeeForGradeDTO);
	
}
