package payroll.Model;

public class AttendStatusBean {
	int site_id;
	String att_month,appr_status, appr_DATE,submit_date;
	int empno;
	String tranEvent;

	public int getEmpno() {
		return empno;
	}

	public void setEmpno(int empno) {
		this.empno = empno;
	}

	public String getTranEvent() {
		return tranEvent;
	}

	public void setTranEvent(String tranEvent) {
		this.tranEvent = tranEvent;
	}

	public String getSubmit_date() {
		return submit_date;
	}

	public void setSubmit_date(String submit_date) {
		this.submit_date = submit_date;
	}

	public String getAppr_DATE() {
		return appr_DATE;
	}

	public void setAppr_DATE(String appr_DATE) {
		this.appr_DATE = appr_DATE;
	}

	public String getAppr_status() {
		return appr_status;
	}

	public void setAppr_status(String appr_status) {
		this.appr_status = appr_status;
	}

	public String getAtt_month() {
		return att_month;
	}

	public void setAtt_month(String att_month) {
		this.att_month = att_month;
	}

	public int getSite_id() {
		return site_id;
	}

	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}

}
