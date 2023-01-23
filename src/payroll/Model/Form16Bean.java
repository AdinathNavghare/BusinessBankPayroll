package payroll.Model;

public class Form16Bean {

	 private String TRNDT;
	 private int EMPNO;
	 private int TRNCD;
	 private int SRNO;
	 private float INP_AMT;
	 private float CAL_AMT;
	 private float ADJ_AMT;
	 private float ARR_AMT;
	 private float NET_AMT;
	 private String CF_SW;
	 private String USRCODE;
	 private String UPDDT;
	 private String STATUS;
	 
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public float getADJ_AMT() {
		return ADJ_AMT;
	}
	public void setADJ_AMT(float aDJ_AMT) {
		ADJ_AMT = aDJ_AMT;
	}
	public float getARR_AMT() {
		return ARR_AMT;
	}
	public void setARR_AMT(float aRR_AMT) {
		ARR_AMT = aRR_AMT;
	}
	public float getNET_AMT() {
		return NET_AMT;
	}
	public void setNET_AMT(float nET_AMT) {
		NET_AMT = nET_AMT;
	}
	public float getCAL_AMT() {
		return CAL_AMT;
	}
	public void setCAL_AMT(float cAL_AMT) {
		CAL_AMT = cAL_AMT;
	}
	public String getTRNDT() {
		return TRNDT;
	}
	public void setTRNDT(String tRNDT) {
		TRNDT = tRNDT;
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
	public float getINP_AMT() {
		return INP_AMT;
	}
	public void setINP_AMT(float iNP_AMT) {
		INP_AMT = iNP_AMT;
	}
	public String getCF_SW() {
		return CF_SW;
	}
	public void setCF_SW(String cF_SW) {
		CF_SW = cF_SW;
	}
	public String getUSRCODE() {
		return USRCODE;
	}
	public void setUSRCODE(String uSRCODE) {
		USRCODE = uSRCODE;
	}
	public String getUPDDT() {
		return UPDDT;
	}
	public void setUPDDT(String uPDDT) {
		UPDDT = uPDDT;
	}

}
