package show.isaBack.serviceInterfaces;

import java.util.List;
import java.util.UUID;

import show.isaBack.DTO.userDTO.AuthorityDTO;
import show.isaBack.DTO.userDTO.UserDTO;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;
import show.isaBack.DTO.AppointmentDTO.AppointmentReportDTO;

public interface IService<T, IdentifiableT> {

	List<UnspecifiedDTO<AuthorityDTO>> findAll();
	IdentifiableT findById(UUID id);
	UUID create(T entityDTO);
	void update(T entityDTO, UUID id);
    boolean delete(UUID id);
}

