package payroll.Model;

public class BillBean {
	int billId;
	int empNo;
	int trncd;
	int forWhom;
	float amount;
	int createdBy;
	int bill_tel_no;
	String from_Date;
	String to_Date;
	String createdOn;
	public int getBillId() {
		return billId;
	}
	public void setBillId(int billId) {
		this.billId = billId;
	}
	public int getEmpNo() {
		return empNo;
	}
	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}
	public int getTrncd() {
		return trncd;
	}
	public void setTrncd(int trncd) {
		this.trncd = trncd;
	}
	public int getForWhom() {
		return forWhom;
	}
	public void setForWhom(int forWhom) {
		this.forWhom = forWhom;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public int getBill_tel_no() {
		return bill_tel_no;
	}
	public void setBill_tel_no(int bill_tel_no) {
		this.bill_tel_no = bill_tel_no;
	}
	public String getFrom_Date() {
		return from_Date;
	}
	public void setFrom_Date(String from_Date) {
		this.from_Date = from_Date;
	}
	public String getTo_Date() {
		return to_Date;
	}
	public void setTo_Date(String to_Date) {
		this.to_Date = to_Date;
	}
	
}
