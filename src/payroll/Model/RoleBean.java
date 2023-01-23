package payroll.Model;

public class RoleBean {

	
	private String user_name;
	
	private int ROLEID;
	private String ROLENAME;
	private String ROLEDESC;
	private int USERID;
	int USER_ROLE_ID;
	int RMID;
	int MENUID;
	String MENU_NAME;
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	public String getMENU_NAME() {
		return MENU_NAME;
	}
	public void setMENU_NAME(String mENU_NAME) {
		MENU_NAME = mENU_NAME;
	}
	public int getMENUID() {
		return MENUID;
	}
	public void setMENUID(int mENUID) {
		MENUID = mENUID;
	}
	public int getRMID() {
		return RMID;
	}
	public void setRMID(int rMID) {
		RMID = rMID;
	}
	public int getUSER_ROLE_ID() {
		return USER_ROLE_ID;
	}
	public void setUSER_ROLE_ID(int uSER_ROLE_ID) {
		USER_ROLE_ID = uSER_ROLE_ID;
	}
	public int getUSERID() {
		return USERID;
	}
	public void setUSERID(int uSERID) {
		USERID = uSERID;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getLASTMOD_DATE() {
		return LASTMOD_DATE;
	}
	public void setLASTMOD_DATE(String lASTMOD_DATE) {
		LASTMOD_DATE = lASTMOD_DATE;
	}
	public int getASSIGNED_RU_ID() {
		return ASSIGNED_RU_ID;
	}
	public void setASSIGNED_RU_ID(int aSSIGNED_RU_ID) {
		ASSIGNED_RU_ID = aSSIGNED_RU_ID;
	}
	private String STATUS;
	private String LASTMOD_DATE;
	private int ASSIGNED_RU_ID;
	public int getROLEID() {
		return ROLEID;
	}
	public void setROLEID(int rOLEID) {
		ROLEID = rOLEID;
	}
	public String getROLENAME() {
		return ROLENAME;
	}
	public void setROLENAME(String rOLENAME) {
		ROLENAME = rOLENAME;
	}
	public String getROLEDESC() {
		return ROLEDESC;
	}
	public void setROLEDESC(String rOLEDESC) {
		ROLEDESC = rOLEDESC;
	}
	
	
	
}
