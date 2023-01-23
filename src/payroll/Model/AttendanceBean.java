package payroll.Model;

public class AttendanceBean {
	
	int ATTD_ID;
    int EMPNO;
    String CHECK_IN;	
	String CHECK_OUT;
	String PC_NAME;
	String LATE_REASON;
	String EARLY_REASON;
	String TOTAL_TIME;
	String TOTAL_DAYS;
	String DIFF;
	String EMPNAME;
	
    int USERID;
	
	public String getEMPNAME() {
		return EMPNAME;
	}
	public void setEMPNAME(String eMPNAME) {
		EMPNAME = eMPNAME;
	}
	public String getTOTAL_DAYS() {
		return TOTAL_DAYS;
	}
	public void setTOTAL_DAYS(String tOTAL_DAYS) {
		TOTAL_DAYS = tOTAL_DAYS;
	}
	public String getDIFF() {
		return DIFF;
	}
	public void setDIFF(String dIFF) {
		DIFF = dIFF;
	}
	public String getTOTAL_TIME() {
		return TOTAL_TIME;
	}
	public void setTOTAL_TIME(String tOTAL_TIME) {
		TOTAL_TIME = tOTAL_TIME;
	}
	public int getUSERID() {
		return USERID;
	}
	public void setUSERID(int uSERID) {
		USERID = uSERID;
	}
	String ATTD_DATE;
	public int getATTD_ID() {
		return ATTD_ID;
	}
	public void setATTD_ID(int aTTD_ID) {
		ATTD_ID = aTTD_ID;
	}
	public int getEMPNO() {
		return EMPNO;
	}
	public void setEMPNO(int eMPNO) {
		EMPNO = eMPNO;
	}
	public String getCHECK_IN() {
		return CHECK_IN;
	}
	public void setCHECK_IN(String cHECK_IN) {
		CHECK_IN = cHECK_IN;
	}
	public String getCHECK_OUT() {
		return CHECK_OUT;
	}
	public void setCHECK_OUT(String cHECK_OUT) {
		CHECK_OUT = cHECK_OUT;
	}
	public String getPC_NAME() {
		return PC_NAME;
	}
	public void setPC_NAME(String pC_NAME) {
		PC_NAME = pC_NAME;
	}
	public String getLATE_REASON() {
		return LATE_REASON;
	}
	public void setLATE_REASON(String lATE_REASON) {
		LATE_REASON = lATE_REASON;
	}
	public String getEARLY_REASON() {
		return EARLY_REASON;
	}
	public void setEARLY_REASON(String eARLY_REASON) {
		EARLY_REASON = eARLY_REASON;
	}
	
	public String getATTD_DATE() {
		return ATTD_DATE;
	}
	public void setATTD_DATE(String aTTD_DATE) {
		ATTD_DATE = aTTD_DATE;
	}
	
}
