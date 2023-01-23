package payroll.Model;

public class EmpExperBean {
	
	private String ORGNAME;
	private String FROMDT; 
	private String TODT; 
	private String POST; 
	//private int SALARY;
	private String SALARY;
	
	private int SRNO;
	private int EMPNO;
	
	/*public int getSALARY() {
		return SALARY;
	}
	public void setSALARY(int sALARY) {
		SALARY = sALARY;
	}
	*/
	public String getSALARY() {
		return SALARY;
	}
	public void setSALARY(String sALARY) {
		SALARY = sALARY;
	}
	public int getEMPNO() {
		return EMPNO;
	}
	public void setEMPNO(int eMPNO) {
		EMPNO = eMPNO;
	}
	public String getORGNAME() {
		return ORGNAME;
	}
	public int getSRNO() {
		return SRNO;
	}
	public void setSRNO(int sRNO) {
		SRNO = sRNO;
	}
	public void setORGNAME(String oRGNAME) {
		ORGNAME = oRGNAME;
	}
	public String getFROMDT() {
		return FROMDT;
	}
	public void setFROMDT(String rOMDT) {
		FROMDT = rOMDT;
	}
	public String getTODT() {
		return TODT;
	}
	public void setTODT(String tODT) {
		TODT = tODT;
	}
	public String getPOST() {
		return POST;
	}
	public void setPOST(String pOST) {
		POST = pOST;
	}
	
}
