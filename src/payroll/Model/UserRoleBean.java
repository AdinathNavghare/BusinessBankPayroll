package payroll.Model;

public class UserRoleBean {
	private int USER_ROLE_ID;
	private int USERID;
	private int ROLEID;
	private String STATUS;
	private String LASTMOD_DATE;
	private int ASSIGNED_RU_ID;
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
	public int getROLEID() {
		return ROLEID;
	}
	public void setROLEID(int rOLEID) {
		ROLEID = rOLEID;
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
	
}
