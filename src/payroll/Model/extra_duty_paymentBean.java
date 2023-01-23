package payroll.Model;

public class extra_duty_paymentBean {
	
	String Basic;
	String DA;
    String VDA;
    float Basic_lwp;
    float DA_lwp;
    float VDA_lwp;
    float HRA_lwp;
    float MEDALLOW_lwp;
    float EDUALLOW_lwp;
    float CONVEYANCE_lwp;
    float SPECIAL_lwp;
    double Total;
    int  pf_amount;
	private int EMPNO;
	String TRNDT ="";
	String month ="";
	String STATUS ="";
	String NAME="";
	String EMPCODE="";
	public String getTOTALDAYS() {
		return TOTALDAYS;
	}
	public void setTOTALDAYS(String tOTALDAYS) {
		TOTALDAYS = tOTALDAYS;
	}
	String TOTALDAYS="";
	public String getEMPCODE() {
		return EMPCODE;
	}
	public void setEMPCODE(String eMPCODE) {
		EMPCODE = eMPCODE;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getSTATUS() {
			return STATUS;
		}
		public void setSTATUS(String sTATUS) {
			STATUS = sTATUS;
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
	public double getTotal() {
		return Total;
	}
	public void setTotal(double total) {
		Total = total;
	}
	public String getBasic() {
		return Basic;
	}
	public void setBasic(String basic) {
		Basic = basic;
	}
	public String getDA() {
		return DA;
	}
	public void setDA(String dA) {
		DA = dA;
	}
	public String getVDA() {
		return VDA;
	}
	public void setVDA(String vDA) {
		VDA = vDA;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getPf_amount() {
		return pf_amount;
	}
	public void setPf_amount(int pf_amount) {
		this.pf_amount = pf_amount;
	}
	public float getBasic_lwp() {
		return Basic_lwp;
	}
	public void setBasic_lwp(float basic_lwp) {
		Basic_lwp = basic_lwp;
	}
	public float getDA_lwp() {
		return DA_lwp;
	}
	public void setDA_lwp(float dA_lwp) {
		DA_lwp = dA_lwp;
	}
	public float getVDA_lwp() {
		return VDA_lwp;
	}
	public float getHRA_lwp() {
		return HRA_lwp;
	}
	public void setHRA_lwp(float hRA_lwp) {
		HRA_lwp = hRA_lwp;
	}
	public float getMEDALLOW_lwp() {
		return MEDALLOW_lwp;
	}
	public void setMEDALLOW_lwp(float mEDALLOW_lwp) {
		MEDALLOW_lwp = mEDALLOW_lwp;
	}
	public float getEDUALLOW_lwp() {
		return EDUALLOW_lwp;
	}
	public void setEDUALLOW_lwp(float eDUALLOW_lwp) {
		EDUALLOW_lwp = eDUALLOW_lwp;
	}
	public float getCONVEYANCE_lwp() {
		return CONVEYANCE_lwp;
	}
	public void setCONVEYANCE_lwp(float cONVEYANCE_lwp) {
		CONVEYANCE_lwp = cONVEYANCE_lwp;
	}
	public float getSPECIAL_lwp() {
		return SPECIAL_lwp;
	}
	public void setSPECIAL_lwp(float sPECIAL_lwp) {
		SPECIAL_lwp = sPECIAL_lwp;
	}
	public void setVDA_lwp(float vDA_lwp) {
		VDA_lwp = vDA_lwp;
	}
	
	
}

