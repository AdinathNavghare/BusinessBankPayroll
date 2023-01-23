package payroll.Model;

public class RelieveInfoBean {
	
	private int EMPNO;
	private String RESGN_DATE;
	private String RESGN_ACCTD_DATE;
	private String REASON;
	private String NTC_PERIOD;
	private String TERMINATE;
	private String DEATH;
	private String LEFT_DATE;
	private String LEFT_REASON;
	
	
	public int getEMPNO(){
		return EMPNO;
	}
	public void setEMPNO(int eMPNO){
		EMPNO = eMPNO;
	}
	public String getRESGN_DATE() {
		return RESGN_DATE;
	}
	public void setRESGN_DATE(String rESGN_DATE) {
		RESGN_DATE = rESGN_DATE;
	}
	public String getRESGN_ACCTD_DATE() {
		return RESGN_ACCTD_DATE;
	}
	public void setRESGN_ACCTD_DATE(String rESGN_ACCTD_DATE) {
		RESGN_ACCTD_DATE = rESGN_ACCTD_DATE;
	}
	public String getREASON() {
		return REASON;
	}
	public void setREASON(String rEASON) {
		REASON = rEASON;
	}
	public String getNTC_PERIOD() {
		return NTC_PERIOD;
	}
	public void setNTC_PERIOD(String nTC_PERIOD) {
		NTC_PERIOD = nTC_PERIOD;
	}
	public String getTERMINATE() {
		return TERMINATE;
	}
	public void setTERMINATE(String tERMINATE) {
		TERMINATE = tERMINATE;
	}
	public String getDEATH() {
		return TERMINATE;
	}
	public void setDEATH(String dEATH) {
		DEATH = dEATH;
	}
	public String getLEFT_DATE() {
		return LEFT_DATE;
	}
	public void setLEFT_DATE(String lEFT_DATE) {
		LEFT_DATE = lEFT_DATE;
	}
	public String getLEFT_REASON() {
		return LEFT_REASON;
	}
	public void setLEFT_REASON(String lEFT_REASON) {
		LEFT_REASON = lEFT_REASON;
	}

}
