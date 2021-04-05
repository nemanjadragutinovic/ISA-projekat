package show.isaBack.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="AUTHORITY")
public class Authority implements GrantedAuthority {
	
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id")
	private UUID id;


    @Column(name="name", unique=true)
    String name;

	public Authority() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Authority(UUID id, String name) {
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return null;
	}

}
