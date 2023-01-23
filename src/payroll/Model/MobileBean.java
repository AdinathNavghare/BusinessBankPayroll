package payroll.Model;

public class MobileBean {
	private int emp_no;
	private int connection_type;
	private int prj_srno;
	private int  service_provider;
	private String mobile_no;
	private String eff_date;
	private float charges;
	private float billcharges;
	private String status;
	
	
	
	public float getBillcharges() {
		return billcharges;
	}
	public void setBillcharges(float billcharges) {
		this.billcharges = billcharges;
	}
	public int getEmp_no() {
		return emp_no;
	}
	public void setEmp_no(int emp_no) {
		this.emp_no = emp_no;
	}
	public int getConnection_type() {
		return connection_type;
	}
	public void setConnection_type(int connection_type) {
		this.connection_type = connection_type;
	}
	public int getPrj_srno() {
		return prj_srno;
	}
	public void setPrj_srno(int prj_srno) {
		this.prj_srno = prj_srno;
	}
	public int getService_provider() {
		return service_provider;
	}
	public void setService_provider(int service_provider) {
		this.service_provider = service_provider;
	}
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	public String getEff_date() {
		return eff_date;
	}
	public void setEff_date(String eff_date) {
		this.eff_date = eff_date;
	}
	public float getCharges() {
		return charges;
	}
	public void setCharges(float charges) {
		this.charges = charges;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	 

}
