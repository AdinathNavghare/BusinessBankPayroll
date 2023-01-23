package payroll.Model;

public class UsrMast {
	
	private int USERID;
	private String  EMPID;
	private String UNAME;
	private String UPWD;
	private String UCREATEDATE;
	private String UMODDATE;
	private String UMODUID;
	private String USTATUS;
	private String mail;
	public int getUSERID() {
		return USERID;
	}
	public void setUSERID(int uSERID) {
		USERID = uSERID;
	}
	public String getEMPID() {
		return EMPID;
	}
	public void setEMPID(String string) {
		EMPID = string;
	}
	public String getUNAME() {
		return UNAME;
	}
	public void setUNAME(String uNAME) {
		UNAME = uNAME;
	}
	public String getUPWD() {
		return UPWD;
	}
	public void setUPWD(String uPWD) {
		UPWD = uPWD;
	}
	public String getUCREATEDATE() {
		return UCREATEDATE;
	}
	public void setUCREATEDATE(String uCREATEDATE) {
		UCREATEDATE = uCREATEDATE;
	}
	public String getUMODDATE() {
		return UMODDATE;
	}
	public void setUMODDATE(String uMODDATE) {
		UMODDATE = uMODDATE;
	}
	public String getUMODUID() {
		return UMODUID;
	}
	public void setUMODUID(String uMODUID) {
		UMODUID = uMODUID;
	}
	public String getUSTATUS() {
		return USTATUS;
	}
	public void setUSTATUS(String uSTATUS) {
		USTATUS = uSTATUS;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}

}
