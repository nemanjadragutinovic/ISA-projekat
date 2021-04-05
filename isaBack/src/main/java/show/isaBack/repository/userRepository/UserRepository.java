package show.isaBack.repository.userRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import show.isaBack.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail ( String email );
}
