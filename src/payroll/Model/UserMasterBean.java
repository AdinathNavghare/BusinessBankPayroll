package payroll.Model;

public class UserMasterBean {
	String usercode;
	String username;
	boolean  IsValid;
	String User_Email_ID;
	String flag;
	
	
	
	public String getUser_Email_ID() {
		return User_Email_ID;
	}
	public void setUser_Email_ID(String user_Email_ID) {
		User_Email_ID = user_Email_ID;
	}
	public boolean isIsValid() {
		return IsValid;
	}
	public void setIsValid(boolean isValid) {
		IsValid = isValid;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpwd() {
		return userpwd;
	}
	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	String userpwd;
	int status;

}
