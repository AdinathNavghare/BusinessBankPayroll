package payroll.Model;

public class LeaveEncashmentBean {
	int empNo;
	String empCode;
	int site_id;
	float leaveBal;
	float maxLimit;
	float encashApplicable;
	float monthlyGross;
	float esicAmt;
	float encashmentAmt;
	float encashpf_3_67;
	float encashpf_8_33;
	float encashpf;
	float encashnet;

	public float getBasic() {
		return basic;
	}
	public void setBasic(float basic) {
		this.basic = basic;
	}
	public float getDa() {
		return da;
	}
	public void setDa(float da) {
		this.da = da;
	}
	public float getVda() {
		return vda;
	}
	public void setVda(float vda) {
		this.vda = vda;
	}
	String status;
	int encashmentApplNo;
	float leaveEncashmentSanction;
	String leaveEncashmentDate;
	String fromDate;
	String toDate;
	String ename;
	int days;
	
	float basic;
	float da;
	float vda;
	
	
	
	
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public String getLeaveEncashmentDate() {
		return leaveEncashmentDate;
	}
	public void setLeaveEncashmentDate(String leaveEncashmentDate) {
		this.leaveEncashmentDate = leaveEncashmentDate;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public float getLeaveEncashmentSanction() {
		return leaveEncashmentSanction;
	}
	public void setLeaveEncashmentSanction(float leaveEncashmentSanction) {
		this.leaveEncashmentSanction = leaveEncashmentSanction;
	}
	public int getEmpNo() {
		return empNo;
	}
	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}
	public int getSite_id() {
		return site_id;
	}
	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}
	public float getLeaveBal() {
		return leaveBal;
	}
	public void setLeaveBal(float leaveBal) {
		this.leaveBal = leaveBal;
	}
	public float getMaxLimit() {
		return maxLimit;
	}
	public void setMaxLimit(float maxLimit) {
		this.maxLimit = maxLimit;
	}
	public float getEncashApplicable() {
		return encashApplicable;
	}
	public void setEncashApplicable(float encashApplicable) {
		this.encashApplicable = encashApplicable;
	}
	public float getMonthlyGross() {
		return monthlyGross;
	}
	public void setMonthlyGross(float monthlyGross) {
		this.monthlyGross = monthlyGross;
	}
	public float getEsicAmt() {
		return esicAmt;
	}
	public void setEsicAmt(float esicAmt) {
		this.esicAmt = esicAmt;
	}
	public float getEncashmentAmt() {
		return encashmentAmt;
	}
	public void setEncashmentAmt(float encashmentAmt) {
		this.encashmentAmt = encashmentAmt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getEncashmentApplNo() {
		return encashmentApplNo;
	}
	public void setEncashmentApplNo(int encashmentApplNo) {
		this.encashmentApplNo = encashmentApplNo;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public float getEncashpf_3_67() {
		return encashpf_3_67;
	}
	public void setEncashpf_3_67(float encashpf_3_67) {
		this.encashpf_3_67 = encashpf_3_67;
	}
	public float getEncashpf_8_33() {
		return encashpf_8_33;
	}
	public void setEncashpf_8_33(float encashpf_8_33) {
		this.encashpf_8_33 = encashpf_8_33;
	}
	public float getEncashpf() {
		return encashpf;
	}
	public void setEncashpf(float encashpf) {
		this.encashpf = encashpf;
	}
	public float getEncashnet() {
		return encashnet;
	}
	public void setEncashnet(float encashnet) {
		this.encashnet = encashnet;
	}
	
}
