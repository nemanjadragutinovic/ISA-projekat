package show.isaBack.DTO.userDTO;

public class ChangePasswordDTO {
	
	public String oldPassword;
	public String newPassword;
	
	
	public ChangePasswordDTO() {
		super();
		
	}
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	

}
