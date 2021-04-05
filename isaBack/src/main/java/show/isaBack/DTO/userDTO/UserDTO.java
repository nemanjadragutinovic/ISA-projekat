package show.isaBack.DTO.userDTO;

import java.util.List;

import show.isaBack.model.Authority;

public class UserDTO {
	
	private String email;

	private String password;

	private String name;
    
	private String surname;
    
	private String address;
	    
	private String phoneNumber;
	
    private boolean active;
    
    private List<Authority> authorities;

    
	public UserDTO(String email, String name, String surname, String address, String phoneNumber, boolean active,
			List<Authority> authorities) {
		super();
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.active = active;
		this.authorities = authorities;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

}
