package payroll.Model;

public class CodeMasterBean {

	private int TRNCD; 
	private String DISC; 
	private String PSLIPYN; 
	private String TAXABLE; 
	private String AUTOPOST; 
	private String SUBSYS; 
	private int ACNO; 
	private int MAXLIM; 
	private int MINLIM; 
	private String GROSS_YN; 
	private String BANK_LOAN; 
	private String FREQUENCY; 
	private int FREQNO; 
	private String BRSUBSYS; 
	private int BRACNO; 
	private String DRSUBSYS; 
	private int DRACNO; 
	private String CF_SW; 
	private String SDESC; 
	private String CUMU_YN; 
	private String CONVYN; 
	private String ITGRP; 
	private String PROJYN; 
	private String PLUSMINUS; 
	private String ITGRP2; 
	private String CHKSLB; 
	private int CDTYPE;
	private int SRNO;
	private int EMPTYPE;
	private int printsequnce;
	
	
	public CodeMasterBean()
	{
		TRNCD = 0;
		DISC = "-";
		PSLIPYN = "-";
		TAXABLE = "-";
		AUTOPOST = "-";
		SUBSYS = "-";
		ACNO = 0;
		MAXLIM = 0;
		MINLIM = 0;
		GROSS_YN = "N";
		BANK_LOAN = "-";
		FREQUENCY = "-";
		FREQNO = 0;
		BRSUBSYS = "-";
		BRACNO = 0;
		DRSUBSYS = "-";
		DRACNO = 0;
		CF_SW = "-";
		SDESC = "-";
		CUMU_YN = "-";
		CONVYN = "-";
		ITGRP = "-";
		PROJYN = "-";
		PLUSMINUS = "-";
		ITGRP2 = "-";
		CHKSLB = "-";
		CDTYPE = -1;
	}
	public int getTRNCD() {
		return TRNCD;
	}
	public void setTRNCD(int tRNCD) {
		TRNCD = tRNCD;
	}
	public String getDISC() {
		return DISC;
	}
	public void setDISC(String dISC) {
		DISC = dISC;
	}
	public String getPSLIPYN() {
		return PSLIPYN;
	}
	public void setPSLIPYN(String pSLIPYN) {
		PSLIPYN = pSLIPYN;
	}
	public String getTAXABLE() {
		return TAXABLE;
	}
	public void setTAXABLE(String tAXABLE) {
		TAXABLE = tAXABLE;
	}
	public String getAUTOPOST() {
		return AUTOPOST;
	}
	public void setAUTOPOST(String aUTOPOST) {
		AUTOPOST = aUTOPOST;
	}
	public String getSUBSYS() {
		return SUBSYS;
	}
	public void setSUBSYS(String sUBSYS) {
		SUBSYS = sUBSYS;
	}
	public int getACNO() {
		return ACNO;
	}
	public void setACNO(int aCNO) {
		ACNO = aCNO;
	}
	public int getMAXLIM() {
		return MAXLIM;
	}
	public void setMAXLIM(int mAXLIM) {
		MAXLIM = mAXLIM;
	}
	public int getMINLIM() {
		return MINLIM;
	}
	public void setMINLIM(int mINLIM) {
		MINLIM = mINLIM;
	}
	public String getGROSS_YN() {
		return GROSS_YN;
	}
	public void setGROSS_YN(String gROSS_YN) {
		GROSS_YN = gROSS_YN;
	}
	public String getBANK_LOAN() {
		return BANK_LOAN;
	}
	public void setBANK_LOAN(String bANK_LOAN) {
		BANK_LOAN = bANK_LOAN;
	}
	public String getFREQUENCY() {
		return FREQUENCY;
	}
	public void setFREQUENCY(String fREQUENCY) {
		FREQUENCY = fREQUENCY;
	}
	public int getFREQNO() {
		return FREQNO;
	}
	public void setFREQNO(int fREQNO) {
		FREQNO = fREQNO;
	}
	public String getBRSUBSYS() {
		return BRSUBSYS;
	}
	public void setBRSUBSYS(String bRSUBSYS) {
		BRSUBSYS = bRSUBSYS;
	}
	public int getBRACNO() {
		return BRACNO;
	}
	public void setBRACNO(int bRACNO) {
		BRACNO = bRACNO;
	}
	public String getDRSUBSYS() {
		return DRSUBSYS;
	}
	public void setDRSUBSYS(String dRSUBSYS) {
		DRSUBSYS = dRSUBSYS;
	}
	public int getDRACNO() {
		return DRACNO;
	}
	public void setDRACNO(int dRACNO) {
		DRACNO = dRACNO;
	}
	public String getCF_SW() {
		return CF_SW;
	}
	public void setCF_SW(String cF_SW) {
		CF_SW = cF_SW;
	}
	public String getSDESC() {
		return SDESC;
	}
	public void setSDESC(String sDESC) {
		SDESC = sDESC;
	}
	public String getCUMU_YN() {
		return CUMU_YN;
	}
	public void setCUMU_YN(String cUMU_YN) {
		CUMU_YN = cUMU_YN;
	}
	public String getCONVYN() {
		return CONVYN;
	}
	public void setCONVYN(String cONVYN) {
		CONVYN = cONVYN;
	}
	public String getITGRP() {
		return ITGRP;
	}
	public void setITGRP(String iTGRP) {
		ITGRP = iTGRP;
	}
	public String getPROJYN() {
		return PROJYN;
	}
	public void setPROJYN(String pROJYN) {
		PROJYN = pROJYN;
	}
	public String getPLUSMINUS() {
		return PLUSMINUS;
	}
	public void setPLUSMINUS(String pLUSMINUS) {
		PLUSMINUS = pLUSMINUS;
	}
	public String getITGRP2() {
		return ITGRP2;
	}
	public void setITGRP2(String iTGRP2) {
		ITGRP2 = iTGRP2;
	}
	public String getCHKSLB() {
		return CHKSLB;
	}
	public void setCHKSLB(String cHKSLB) {
		CHKSLB = cHKSLB;
	}
	public int getCDTYPE() {
		return CDTYPE;
	}
	public void setCDTYPE(int cDTYPE) {
		CDTYPE = cDTYPE;
	} 	
	public int getSRNO() {
		return SRNO;
	}
	public void setSRNO(int sRNO) {
		SRNO = sRNO;
	}
	public int getEMPTYPE() {
		return EMPTYPE;
	}
	public void setEMPTYPE(int eMPTYPE) {
		EMPTYPE = eMPTYPE;
	}
	public int getPrintsequnce() {
		return printsequnce;
	}
	public void setPrintsequnce(int printsequnce) {
		this.printsequnce = printsequnce;
	}
}
