package show.isaBack.repository.drugsRepository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import show.isaBack.model.drugs.Offers;

public interface OfferRepository extends JpaRepository<Offers, UUID>{

}
