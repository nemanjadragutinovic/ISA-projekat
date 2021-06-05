package show.isaBack.DTO.userDTO;

import java.util.UUID;

public class EmployeeForGradeRequestDTO {

	private UUID employeeId;
	private int grade;
	
	public EmployeeForGradeRequestDTO() {
		super();
	}

	public EmployeeForGradeRequestDTO(UUID employeeId, int grade) {
		super();
		this.employeeId = employeeId;
		this.grade = grade;
	}

	public UUID getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(UUID employeeId) {
		this.employeeId = employeeId;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	
	
	
}
