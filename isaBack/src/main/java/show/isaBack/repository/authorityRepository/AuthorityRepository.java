package show.isaBack.repository.authorityRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import show.isaBack.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, UUID> {
	
	Authority findByName ( String name );

}
