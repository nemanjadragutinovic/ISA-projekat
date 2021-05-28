package show.isaBack.repository.userRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import show.isaBack.model.LoyalityProgram;

public interface LoyalityProgramRepository extends JpaRepository<LoyalityProgram, UUID>{
	
}
