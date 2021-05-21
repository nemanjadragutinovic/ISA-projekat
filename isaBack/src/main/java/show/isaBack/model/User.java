package show.isaBack.model;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name="USERS")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id")
	private UUID id;
	
    @Column(name = "email", nullable = false, unique = true)
	private String email;
    
    @JsonIgnore
    @Column(name = "password")
	private String password;
    
    @Column(name = "name", nullable = false)
	private String name;
    
    @Column(name = "surname", nullable = false)
	private String surname;
        
    @Column(name = "phoneNumber")
	private String phoneNumber;
	
    @Column(name = "active")
    private boolean active;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "firstLogin")
    private boolean firstLogin;
    
    
    public boolean isFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}

	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;
    	
    @Version
	private Long version;
    
	public User() {}
	
	public User(String email, String password, String name, String surname, String address,
			String phoneNumber, boolean active) {
		this(UUID.randomUUID(), email, password, name, surname, address, phoneNumber, active);
	}
	
	public User(UUID id, String email, String password, String name, String surname, String address,
			String phoneNumber, boolean active) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.active = active;
	}
	
	public UUID getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
	
	
    public List<Authority> getUserAuthorities() {
        return this.authorities;
    }

    
	
    public void setUserAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }
    
	@Override
	public String getUsername() {
		return this.email;
	}

	@JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

	@Override
    public boolean isEnabled() {
        return this.active;
    }

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
		
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}
