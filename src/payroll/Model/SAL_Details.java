package payroll.Model;

public class SAL_Details {

	private int  EMPNO;
	private String  SAL_MONTH;
	private String SAL_STATUS; 
	private String SAL_PAID_DATE; 
	private String VOC_NUM;
	private String AUTHORIZED_BY;
	private String FINALIZED_BY;
	public int getEMPNO() {
		return EMPNO;
	}
	public void setEMPNO(int eMPNO) {
		EMPNO = eMPNO;
	}
	public String getSAL_MONTH() {
		return SAL_MONTH;
	}
	public void setSAL_MONTH(String sAL_MONTH) {
		SAL_MONTH = sAL_MONTH;
	}
	public String getSAL_STATUS() {
		return SAL_STATUS;
	}
	public void setSAL_STATUS(String sAL_STATUS) {
		SAL_STATUS = sAL_STATUS;
	}
	public String getSAL_PAID_DATE() {
		return SAL_PAID_DATE;
	}
	public void setSAL_PAID_DATE(String sAL_PAID_DATE) {
		SAL_PAID_DATE = sAL_PAID_DATE;
	}
	public String getVOC_NUM() {
		return VOC_NUM;
	}
	public void setVOC_NUM(String vOC_NUM) {
		VOC_NUM = vOC_NUM;
	}
	public String getAUTHORIZED_BY() {
		return AUTHORIZED_BY;
	}
	public void setAUTHORIZED_BY(String aUTHORIZED_BY) {
		AUTHORIZED_BY = aUTHORIZED_BY;
	}
	public String getFINALIZED_BY() {
		return FINALIZED_BY;
	}
	public void setFINALIZED_BY(String fINALIZED_BY) {
		FINALIZED_BY = fINALIZED_BY;
	}
	
	
}
