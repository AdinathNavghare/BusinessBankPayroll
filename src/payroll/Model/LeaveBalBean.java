package payroll.Model;

public class LeaveBalBean {
	
	private int EMPNO;
	private int SALUTE; 
	private String FNAME;
	private String MNAME;
	private String LNAME;
	private float Leaves;
	private String EMPCODE;
	
	public String getEMPCODE() {
		return EMPCODE;
	}
	public void setEMPCODE(String eMPCODE) {
		EMPCODE = eMPCODE;
	}
	public int getEMPNO() {
		return EMPNO;
	}
	public void setEMPNO(int eMPNO) {
		EMPNO = eMPNO;
	}
	public int getSALUTE() {
		return SALUTE;
	}
	public void setSALUTE(int sALUTE) {
		SALUTE = sALUTE;
	}
	public String getFNAME() {
		return FNAME;
	}
	public void setFNAME(String fNAME) {
		FNAME = fNAME;
	}
	public String getMNAME() {
		return MNAME;
	}
	public void setMNAME(String mNAME) {
		MNAME = mNAME;
	}
	public String getLNAME() {
		return LNAME;
	}
	public void setLNAME(String lNAME) {
		LNAME = lNAME;
	}
	public float getLeaves() {
		return Leaves;
	}
	public void setLeaves(float leaves) {
		Leaves = leaves;
	}
	
}
