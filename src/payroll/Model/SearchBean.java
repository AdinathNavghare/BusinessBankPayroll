package payroll.Model;

public class SearchBean {
	
	private String MON_DAY;
	private String ID;
	private String month;
	private int empno;
	private String PAID_DAYS;
	private Float LOP;
	private String LVDAYS;
	private Float HOL_DAYS;
	private String DOJ;
	
	public String getDOJ() {
		return DOJ;
	}
	public void setDOJ(String dOJ) {
		DOJ = dOJ;
	}
	public Float getHOL_DAYS() {
		return HOL_DAYS;
	}
	public void setHOL_DAYS(Float hOL_DAYS) {
		HOL_DAYS = hOL_DAYS;
	}
	public String getLVDAYS() {
		return LVDAYS;
	}
	public void setLVDAYS(String lVDAYS) {
		LVDAYS = lVDAYS;
	}
	public Float getLOP() {
		return LOP;
	}
	public void setLOP(Float lOP) {
		LOP = lOP;
	}
	public String getPAID_DAYS() {
		return PAID_DAYS;
	}
	public void setPAID_DAYS(String pAID_DAYS) {
		PAID_DAYS = pAID_DAYS;
	}
	public int getEmpno() {
		return empno;
	}
	public void setEmpno(int empno) {
		this.empno = empno;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getMON_DAY() {
		return MON_DAY;
	}
	public void setMON_DAY(String mON_DAY) {
		MON_DAY = mON_DAY;
	}
	public String getSPR_CNT() {
		return SPR_CNT;
	}
	public void setSPR_CNT(String sPR_CNT) {
		SPR_CNT = sPR_CNT;
	}
	public String getABS_CNT() {
		return ABS_CNT;
	}
	public void setABS_CNT(String aBS_CNT) {
		ABS_CNT = aBS_CNT;
	}
	public String getWO_CNT() {
		return WO_CNT;
	}
	public void setWO_CNT(String wO_CNT) {
		WO_CNT = wO_CNT;
	}
	public String getEO_CNT() {
		return EO_CNT;
	}
	public void setEO_CNT(String eO_CNT) {
		EO_CNT = eO_CNT;
	}
	public String getEO_HRS() {
		return EO_HRS;
	}
	public void setEO_HRS(String eO_HRS) {
		EO_HRS = eO_HRS;
	}
	public String getOT_CNT() {
		return OT_CNT;
	}
	public void setOT_CNT(String oT_CNT) {
		OT_CNT = oT_CNT;
	}
	public String getOT_HRS() {
		return OT_HRS;
	}
	public void setOT_HRS(String oT_HRS) {
		OT_HRS = oT_HRS;
	}
	public String getWRK_HRS() {
		return WRK_HRS;
	}
	public void setWRK_HRS(String wRK_HRS) {
		WRK_HRS = wRK_HRS;
	}
	private String SPR_CNT;
	private String ABS_CNT;
	private String WO_CNT;
	private String EO_CNT;
	private String EO_HRS;
	private String OT_CNT;
	private String OT_HRS;
	private String WRK_HRS;
}
