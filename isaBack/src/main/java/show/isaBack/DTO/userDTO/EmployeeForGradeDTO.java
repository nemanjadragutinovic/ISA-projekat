package show.isaBack.DTO.userDTO;

import java.util.UUID;

import show.isaBack.model.User;

public class EmployeeForGradeDTO {

	private User employee;
	
	private int grade;

	public EmployeeForGradeDTO() {
		super();
	}

	public EmployeeForGradeDTO(User employee, int grade) {
		super();
		this.employee = employee;
		this.grade = grade;
	}

	public User getEmployee() {
		return employee;
	}

	public void setEmployee(User employee) {
		this.employee = employee;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	
	
	
	
	
}
