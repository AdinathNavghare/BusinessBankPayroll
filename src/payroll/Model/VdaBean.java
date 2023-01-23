package payroll.Model;

public class VdaBean {

	private String daApplicableDate;
	
	private String vdaDate;
	private String month;
	private float index;
	private float fixBasic;
	private float daDivisor;
	
	private String status;
	private String endDate;
	
	private int batchId;
	private double vdaIndexAvg;
	private double prev_vdaIndexAvg;

	private float CFPIValue;
	private float CPIValue;


	public String getDaApplicableDate() {
		return daApplicableDate;
	}
	public void setDaApplicableDate(String daApplicableDate) {
		this.daApplicableDate = daApplicableDate;
	}
	public String getVdaDate() {
		return vdaDate;
	}
	public void setVdaDate(String vdaDate) {
		this.vdaDate = vdaDate;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public float getIndex() {
		return index;
	}
	public void setIndex(float index) {
		this.index = index;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getBatchId() {
		return batchId;
	}
	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}
	public double getVdaIndexAvg() {
		return vdaIndexAvg;
	}
	public void setVdaIndexAvg(double vdaIndexAvg) {
		this.vdaIndexAvg = vdaIndexAvg;
	}
	public double getPrev_vdaIndexAvg() {
		return prev_vdaIndexAvg;
	}
	public void setPrev_vdaIndexAvg(double prev_vdaIndexAvg) {
		this.prev_vdaIndexAvg = prev_vdaIndexAvg;
	}
	public float getCPIValue() {
		return CPIValue;
	}
	public void setCPIValue(float cPIValue) {
		CPIValue = cPIValue;
	}
	public float getCFPIValue() {
		return CFPIValue;
	}
	public void setCFPIValue(float cFPIValue) {
		CFPIValue = cFPIValue;
	}
	public float getFixBasic() {
		return fixBasic;
	}
	public void setFixBasic(float fixBasic) {
		this.fixBasic = fixBasic;
	}
	public float getDaDivisor() {
		return daDivisor;
	}
	public void setDaDivisor(float daDivisor) {
		this.daDivisor = daDivisor;
	}

	
	
}
