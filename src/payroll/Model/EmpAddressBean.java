package payroll.Model;

public class EmpAddressBean 
{
	private int EMPNO; 
	private String AUXCD; 
	private String ADDR1; 
	private String ADDR2; 
	private String ADDR3; 
	private int CITY;
	private int STATE;
	private int PIN; 
	private long TELNO;
	private String ADDRTYPE;
	private int ID;
	public int getID() {
		return ID;
	}



	public void setID(int iD) {
		ID = iD;
	}



	public EmpAddressBean()
	{
		ADDR1="-"; 
		ADDR2="-"; 
		ADDR3="-"; 
		CITY=0;
		STATE=0;
		PIN=0; 
		TELNO=0;
		ADDRTYPE="-";
		ID=0;
	}
	
	
	
	public String getADDRTYPE() {
		return ADDRTYPE;
	}
	public void setADDRTYPE(String aDDRTYPE) {
		ADDRTYPE = aDDRTYPE;
	}
	public int getEMPNO() {
		return EMPNO;
	}
	public void setEMPNO(int eMPNO) {
		EMPNO = eMPNO;
	}
	public String getAUXCD() {
		return AUXCD;
	}
	public void setAUXCD(String aUXCD) {
		AUXCD = aUXCD;
	}
	public String getADDR1() {
		return ADDR1;
	}
	public void setADDR1(String aDDR1) {
		ADDR1 = aDDR1;
	}
	public String getADDR2() {
		return ADDR2;
	}
	public void setADDR2(String aDDR2) {
		ADDR2 = aDDR2;
	}
	public String getADDR3() {
		return ADDR3;
	}
	public void setADDR3(String aDDR3) {
		ADDR3 = aDDR3;
	}
	
	
	
	
	public int getCITY() {
		return CITY;
	}


	public void setCITY(int cITY) {
		CITY = cITY;
	}


	public int getSTATE() {
		return STATE;
	}


	public void setSTATE(int sTATE) {
		STATE = sTATE;
	}


	public int getPIN() {
		return PIN;
	}
	public void setPIN(int pIN) {
		PIN = pIN;
	}
	public long getTELNO() {
		return TELNO;
	}
	public void setTELNO(long tELNO) {
		TELNO = tELNO;
	} 
	
}
