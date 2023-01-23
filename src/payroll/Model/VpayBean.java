package payroll.Model;

public class VpayBean 
{
	private int SRNO;
	private int EMPNO;
	private String DECL_DATE; 
	private int TRNCD;
	private int AMT_DECLARE;
	private int AMT_CLAIMED;
	
	public int getSRNO() {
		return SRNO;
	}
	public void setSRNO(int sRNO) {
		SRNO = sRNO;
	}
	public int getEMPNO() {
		return EMPNO;
	}
	public void setEMPNO(int eMPNO) {
		EMPNO = eMPNO;
	}
	public String getDECL_DATE() {
		return DECL_DATE;
	}
	public void setDECL_DATE(String dECL_DATE) {
		DECL_DATE = dECL_DATE;
	}
	public int getTRNCD() {
		return TRNCD;
	}
	public void setTRNCD(int tRNCD) {
		TRNCD = tRNCD;
	}
	
	public int getAMT_DECLARE() {
		return AMT_DECLARE;
	}
	public void setAMT_DECLARE(int aMT_DECLARE) {
		AMT_DECLARE = aMT_DECLARE;
	}
	public int getAMT_CLAIMED() {
		return AMT_CLAIMED;
	}
	public void setAMT_CLAIMED(int aMT_CLAIMED) {
		AMT_CLAIMED = aMT_CLAIMED;
	}
	
}
