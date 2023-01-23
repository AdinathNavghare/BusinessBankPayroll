package payroll.Model;

public class ProjectReportBean {
	
	String EMPNAME;
	String PROJECTNAME;
	String STATUS;
	String TYPES;
	String  PRIORITY;
	String START_DATE;
	String DUE_DATE;
	String DESRIPTION;
	
	//getter and setter
	public String getEMPNAME() {
		return EMPNAME;
	}
	public void setEMPNAME(String ename) {
		EMPNAME=ename;
	}
	
	public String getPROJECTNAME() {
		return PROJECTNAME;
	}
	public void setPROJECTNAME(String pname) {
		PROJECTNAME=pname;
	}
	
	
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String status) {
		STATUS=status;
	}
	
	public String getTYPES() {
		return TYPES;
	}
	public void setTYPES(String types) {
		TYPES=types;
	}
	
	
	public String getPRIORITY() {
		return PRIORITY;
	}
	public void setPRIORITY(String priority) {
		PRIORITY=priority;
	}
	
	public String getSTART_DATE() {
		return START_DATE;
	}
	public void setSTART_DATE(String stdate) {
		START_DATE=stdate;
	}
	
	
	public String getDUE_DATE() {
		return DUE_DATE;
	}
	public void setDUE_DATE(String ddate) {
		DUE_DATE=ddate;
	
	}
	
	
	
	public String getDESRIPTION() {
		return DESRIPTION;
	}
	public void setDESRIPTION(String dscrp) {
		DESRIPTION=dscrp;
	}
}
