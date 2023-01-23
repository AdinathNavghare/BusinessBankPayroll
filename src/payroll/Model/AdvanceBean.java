package payroll.Model;

public class AdvanceBean {


	
	String name;
	int voucher_No;
	
	int empNo;
	int empNo2;
	int empcode;
	int site_id;
	
	String forMonth;
	
	
	
	String requestDate;
	String requestDate2;
	
    int advanceAmtRequested;
    
    int sanctionAmt;
    int sanctionBy;
    String sanctionDate;
    String postDateInAcc;
    String requestStatus;
    float payable;
    
  
   
	int applNo;
	
	public int getVoucher_No() {
		return voucher_No;
	}
	public void setVoucher_No(int voucher_No) {
		this.voucher_No = voucher_No;
	}
    
	 public String getForMonth() {
			return forMonth;
		}
		public void setForMonth(String forMonth) {
			this.forMonth = forMonth;
		}
		public float getPayable() {
			return payable;
		}
		public void setPayable(float payable) {
			this.payable = payable;
		}

	public int getEmpcode() {
		return empcode;
	}
	public void setEmpcode(int empcode) {
		this.empcode = empcode;
	}
	public int getSite_id() {
		return site_id;
	}
	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}
    
    public String getRequestDate2() {
		return requestDate2;
	}
	public void setRequestDate2(String requestDate2) {
		this.requestDate2 = requestDate2;
	}
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
    public int getApplNo() {
		return applNo;
	}
	public void setApplNo(int applNo) {
		this.applNo = applNo;
	}
	public int getEmpNo2() {
		return empNo2;
	}
	public void setEmpNo2(int empNo2) {
		this.empNo2 = empNo2;
	}
	public int getEmpNo() {
		return empNo;
	}
	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public int getAdvanceAmtRequested() {
		return advanceAmtRequested;
	}
	public void setAdvanceAmtRequested(int advanceAmtRequested) {
		this.advanceAmtRequested = advanceAmtRequested;
	}
	public int getSanctionAmt() {
		return sanctionAmt;
	}
	public void setSanctionAmt(int sanctionAmt) {
		this.sanctionAmt = sanctionAmt;
	}
	public int getSanctionBy() {
		return sanctionBy;
	}
	public void setSanctionBy(int sanctionBy) {
		this.sanctionBy = sanctionBy;
	}
	public String getSanctionDate() {
		return sanctionDate;
	}
	public void setSanctionDate(String sanctionDate) {
		this.sanctionDate = sanctionDate;
	}
	public String getPostDateInAcc() {
		return postDateInAcc;
	}
	public void setPostDateInAcc(String postDateInAcc) {
		this.postDateInAcc = postDateInAcc;
	}
	public String getRequestStatus() {
		return requestStatus;
	}
	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}
	
	
}
