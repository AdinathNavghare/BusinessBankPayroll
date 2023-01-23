package payroll.Model;

public class DAGradeBean {
	
	private int serialNumber;
	private int gradeCode;
	private String startDate;
	private String endDate;
	private float basic;
	private String creationDate; 
	private int gradeStatus;
	
	private int daValueType;
	private float daValue;
	private float da;
	public int getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	public int getGradeCode() {
		return gradeCode;
	}
	public void setGradeCode(int gradeCode) {
		this.gradeCode = gradeCode;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public float getBasic() {
		return basic;
	}
	public void setBasic(float basic) {
		this.basic = basic;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public int getGradeStatus() {
		return gradeStatus;
	}
	public void setGradeStatus(int gradeStatus) {
		this.gradeStatus = gradeStatus;
	}
	public int getDaValueType() {
		return daValueType;
	}
	public void setDaValueType(int daValueType) {
		this.daValueType = daValueType;
	}
	public float getDaValue() {
		return daValue;
	}
	public void setDaValue(float daValue) {
		this.daValue = daValue;
	}
	public float getDa() {
		return da;
	}
	public void setDa(float da) {
		this.da = da;
	}
	

}
