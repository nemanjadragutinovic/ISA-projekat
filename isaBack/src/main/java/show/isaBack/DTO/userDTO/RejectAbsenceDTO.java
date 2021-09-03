package show.isaBack.DTO.userDTO;

import java.util.UUID;

public class RejectAbsenceDTO {
	private UUID id;
	private String reason;
	
	public RejectAbsenceDTO() {
		super();
		
	}
	public RejectAbsenceDTO(UUID id, String reason) {
		super();
		this.id = id;
		this.reason = reason;
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
}
