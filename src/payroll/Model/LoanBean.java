package payroll.Model;

public class LoanBean {
	private int EMPNO;
	private int loan_no; 
	private float loan_amt;
	private int loan_code;
	private String start_date;
	private String end_date; 
	private float monthly_install; 
	private float loan_per; 
	private String bank_name; 
	private String sanctionby;
	private String ACTIVE;
	private int total_month;
	private double actual_pay;
	private String action_by; 
	public String getAction_by() {
		return action_by;
	}
	public void setAction_by(String action_by) {
		this.action_by = action_by;
	}
	public double getActual_pay() {
		return actual_pay;
	}
	public void setActual_pay(double d) {
		this.actual_pay = d;
	}
	public int getEMPNO() {
		return EMPNO;
	}
	public void setEMPNO(int eMPNO) {
		EMPNO = eMPNO;
	}
	public int getLoan_no() {
		return loan_no;
	}
	public void setLoan_no(int loan_no) {
		this.loan_no = loan_no;
	}
	public float getLoan_amt() {
		return loan_amt;
	}
	public void setLoan_amt(float loan_amt) {
		this.loan_amt = loan_amt;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public float getMonthly_install() {
		return monthly_install;
	}
	public void setMonthly_install(float monthly_install) {
		this.monthly_install = monthly_install;
	}
	public float getLoan_per() {
		return loan_per;
	}
	public void setLoan_per(float loan_per) {
		this.loan_per = loan_per;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getSanctionby() {
		return sanctionby;
	}
	public void setSanctionby(String sanctionby) {
		this.sanctionby = sanctionby;
	}
	public String getACTIVE() {
		return ACTIVE;
	}
	public void setACTIVE(String aCTIVE) {
		ACTIVE = aCTIVE;
	}
	public int getLoan_code() {
		return loan_code;
	}
	public void setLoan_code(int loan_code) {
		this.loan_code = loan_code;
	} 
	
	public int getTotal_month() {
		return total_month;
	}
	public void setTotal_month(int total_month) {
		this.total_month = total_month;
	}

	
	
}
