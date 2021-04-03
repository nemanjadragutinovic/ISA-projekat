package show.isaBack.unspecifiedDTO;

import java.util.UUID;

public class UnspecifiedDTO<T> {

	public UUID Id;
	public T EntityDTO;
	
	public UnspecifiedDTO() {
		
	}
	
	public UnspecifiedDTO(UUID id, T entityDTO) {
		super();
		Id = id;
		EntityDTO = entityDTO;
	}
	
}
