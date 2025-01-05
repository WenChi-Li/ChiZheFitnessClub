package tw.mygym.model.dto;

public class PasswordUpdateDto {

	private String oldPassword; 
    private String newPassword;
    
    
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
	@Override
	public String toString() {
		return "PasswordUpdateDto [oldPassword=" + oldPassword + ", newPassword=" + newPassword + "]";
	} 
}
