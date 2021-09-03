package show.isaBack.DTO.AppointmentDTO;

import java.util.UUID;

public class IdDTO {

private UUID id;
	
	
	public IdDTO() {
	//super();
	}	

	
	
	public IdDTO(UUID id) {
		super();
		this.id = id;
	}



	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
	
}
