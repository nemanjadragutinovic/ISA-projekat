package show.isaBack.serviceInterfaces;

import show.isaBack.DTO.userDTO.AuthorityDTO;
import show.isaBack.unspecifiedDTO.UnspecifiedDTO;

public interface IAuthorityService extends IService<AuthorityDTO, UnspecifiedDTO<AuthorityDTO>> {
	
	UnspecifiedDTO<AuthorityDTO> findByName ( String name );

}
