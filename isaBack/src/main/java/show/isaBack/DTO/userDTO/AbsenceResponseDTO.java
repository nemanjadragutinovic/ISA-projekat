package show.isaBack.DTO.userDTO;

import java.sql.Date;

public class AbsenceResponseDTO {
	
	private String forEmployee;
	
	private Date startDate;
	
	private Date endDate;
    	
   
	public AbsenceResponseDTO() {
		
	}

	public AbsenceResponseDTO(String forEmployee, Date startDate, Date endDate) {
		super();
		this.forEmployee= forEmployee;
		this.startDate= startDate;
		this.endDate= endDate;
	}


	public String getForEmployee() {
		return forEmployee;
	}

	public void setForEmployee(String forEmployee) {
		this.forEmployee = forEmployee;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}

