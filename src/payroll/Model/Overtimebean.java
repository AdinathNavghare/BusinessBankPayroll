package payroll.Model;

public class Overtimebean {
	
	String otdate ;
	 int EMPNO;
	String shiftcode; 
	public String getOtdate() {
		return otdate;
	}
	public void setOtdate(String otdate) {
		this.otdate = otdate;
	}
	public int getEMPNO() {
		return EMPNO;
	}
	public void setEMPNO(int eMPNO) {
		EMPNO = eMPNO;
	}
	public String getShiftcode() {
		return shiftcode;
	}
	public void setShiftcode(String shiftcode) {
		this.shiftcode = shiftcode;
	}
	public String getFromtime() {
		return fromtime;
	}
	public void setFromtime(String fromtime) {
		this.fromtime = fromtime;
	}
	public String getTotime() {
		return totime;
	}
	public void setTotime(String totime) {
		this.totime = totime;
	}
	 
	String fromtime ;
	String totime ;
	float HOURS;
	public float getHOURS() {
		return HOURS;
	}
	public void setHOURS(float hOURS) {
		HOURS = hOURS;
	}
	 
	 
}
