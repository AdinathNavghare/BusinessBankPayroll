package payroll.Model;

import java.io.Serializable;

public class BankStmntBean implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8096462634765195445L;
	
	private String BANK_NAME;
	private String EMPNO;
	private String EMPNAME;
	private String ACCNO;
	private String NETPAY;
	private String IFSCCODE;
	
	
	public String getIFSCCODE() {
		return IFSCCODE;
	}
	public void setIFSCCODE(String iFSCCODE) {
		IFSCCODE = iFSCCODE;
	}
	public String getBANK_NAME() {
		return BANK_NAME;
	}
	public void setBANK_NAME(String bANK_NAME) {
		BANK_NAME = bANK_NAME;
	}
	public String getEMPNO() {
		return EMPNO;
	}
	public void setEMPNO(String eMPNO) {
		EMPNO = eMPNO;
	}
	public String getEMPNAME() {
		return EMPNAME;
	}
	public void setEMPNAME(String eMPNAME) {
		EMPNAME = eMPNAME;
	}
	public String getACCNO() {
		return ACCNO;
	}
	public void setACCNO(String aCCNO) {
		ACCNO = aCCNO;
	}
	public String getNETPAY() {
		return NETPAY;
	}
	public void setNETPAY(String nETPAY) {
		NETPAY = nETPAY;
	}
	
	
	
}
