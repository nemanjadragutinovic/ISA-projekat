package show.isaBack.model.drugs;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import show.isaBack.model.Patient;
import antlr.collections.List;

@Entity
public class Allergen {

	@Id
	private UUID id;
	
	@Column(name = "name", nullable = false, unique = true)
	private String name;
	
	
	
	public Allergen() {}
	
	public Allergen(String name) {
		this(UUID.randomUUID(), name);
	}
	
	public Allergen(UUID id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
