package payroll.Model;

public class TranBean {
	 
	 private String TRNDT;
	 private int EMPNO;
	 private int TRNCD;
	 private int SRNO;
	// private int INP_AMT;
	 private float INP_AMT;
	 /*private int CAL_AMT;
	 private int ADJ_AMT;
	 private int ARR_AMT;
	 private int NET_AMT;*/
	 private float CAL_AMT;
	 private float ADJ_AMT;
	 private float ARR_AMT;
	 private float NET_AMT;
	 private float pf;
	 private float pt;
	 private float esic;
	 private String STATUS;
	 private String val;
	 private String Date;
	 private String site_id;
	 
	 public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}
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
	 
	 private String CF_SW;
	 private String USRCODE;
	 String empcode;
	 private String UPDDT;
	 private String EMPNAME;
	 
	public String getEMPNAME() {
		return EMPNAME;
	}
	public void setEMPNAME(String eMPNAME) {
		EMPNAME = eMPNAME;
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
	public String getEmpcode() {
		return empcode;
	}
	public void setEmpcode(String empcode) {
		this.empcode = empcode;
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
	/*public int getINP_AMT() {
		return INP_AMT;
	}
	public void setINP_AMT(int iNP_AMT) {
		INP_AMT = iNP_AMT;
	}*/
	public float getINP_AMT() {
		return INP_AMT;
	}
	public void setINP_AMT(float iNP_AMT) {
		INP_AMT = iNP_AMT;
	}
	/*public int getCAL_AMT() {
		return CAL_AMT;
	}
	public void setCAL_AMT(int cAL_AMT) {
		CAL_AMT = cAL_AMT;
	}
	public int getADJ_AMT() {
		return ADJ_AMT;
	}
	public void setADJ_AMT(int aDJ_AMT) {
		ADJ_AMT = aDJ_AMT;
	}
	public int getARR_AMT() {
		return ARR_AMT;
	}
	public void setARR_AMT(int aRR_AMT) {
		ARR_AMT = aRR_AMT;
	}
	public int getNET_AMT() {
		return NET_AMT;
	}
	public void setNET_AMT(int nET_AMT) {
		NET_AMT = nET_AMT;
	}*/
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
	public float getPf() {
		return pf;
	}
	public void setPf(float pf) {
		this.pf = pf;
	}
	public float getPt() {
		return pt;
	}
	public void setPt(float pt) {
		this.pt = pt;
	}
	public float getEsic() {
		return esic;
	}
	public void setEsic(float esic) {
		this.esic = esic;
	}
}
