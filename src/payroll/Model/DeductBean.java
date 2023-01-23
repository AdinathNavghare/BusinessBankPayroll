package payroll.Model;

public class DeductBean {
	
	private int EMPNO;
	private int TRNCD;
	private int SRNO;
	private float AMOUNT;
	private String SUBSYS_CD;
	private long AC_NO;
	private String BODSANCNO;
	private String SANC_DATE;
	private int SANC_AMT;
	private float INT_RATE;
	private String REPAY_START;
	private String END_DATE;
	private String CUMUYN;
	private String ACTYN;
	private int No_Of_Installment;
	private int type;
	private int prj_srno;
	private int desig; 
	private float actual_amt_paid;
	
	public int getNo_Of_Installment() {
		return No_Of_Installment;
	}
	public void setNo_Of_Installment(int no_Of_Installment) {
		No_Of_Installment = no_Of_Installment;
	}
	public int getEMPNO() {
		return EMPNO;
	}
	public void setEMPNO(int eMPNO) {
		EMPNO = eMPNO;
	}
	public int getTRNCD() {
		return TRNCD;
	}
	public void setTRNCD(int tRNCD) {
		TRNCD = tRNCD;
	}
	public int getSRNO() {
		return SRNO;
	}
	public void setSRNO(int sRNO) {
		SRNO = sRNO;
	}
	public float getAMOUNT() {
		return AMOUNT;
	}
	public void setAMOUNT(float aMOUNT) {
		AMOUNT = aMOUNT;
	}
	public String getSUBSYS_CD() {
		return SUBSYS_CD;
	}
	public void setSUBSYS_CD(String sUBSYS_CD) {
		SUBSYS_CD = sUBSYS_CD;
	}
	public long getAC_NO() {
		return AC_NO;
	}
	public void setAC_NO(long aC_NO) {
		AC_NO = aC_NO;
	}
	public String getBODSANCNO() {
		return BODSANCNO;
	}
	public void setBODSANCNO(String bODSANCNO) {
		BODSANCNO = bODSANCNO;
	}
	public String getSANC_DATE() {
		return SANC_DATE;
	}
	public void setSANC_DATE(String sANC_DATE) {
		SANC_DATE = sANC_DATE;
	}
	public int getSANC_AMT() {
		return SANC_AMT;
	}
	public void setSANC_AMT(int sANC_AMT) {
		SANC_AMT = sANC_AMT;
	}
	public float getINT_RATE() {
		return INT_RATE;
	}
	public void setINT_RATE(float iNT_RATE) {
		INT_RATE = iNT_RATE;
	}
	public String getREPAY_START() {
		return REPAY_START;
	}
	public void setREPAY_START(String rEPAY_START) {
		REPAY_START = rEPAY_START;
	}
	public String getEND_DATE() {
		return END_DATE;
	}
	public void setEND_DATE(String eND_DATE) {
		END_DATE = eND_DATE;
	}
	public String getCUMUYN() {
		return CUMUYN;
	}
	public void setCUMUYN(String cUMUYN) {
		CUMUYN = cUMUYN;
	}
	public String getACTYN() {
		return ACTYN;
	}
	public void setACTYN(String aCTYN) {
		ACTYN = aCTYN;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getPrj_srno() {
		return prj_srno;
	}
	public void setPrj_srno(int prj_srno) {
		this.prj_srno = prj_srno;
	}
	public int getDesig() {
		return desig;
	}
	public void setDesig(int desig) {
		this.desig = desig;
	}
	public float getActual_amt_paid() {
		return actual_amt_paid;
	}
	public void setActual_amt_paid(float actual_amt_paid) {
		this.actual_amt_paid = actual_amt_paid;
	}


}
