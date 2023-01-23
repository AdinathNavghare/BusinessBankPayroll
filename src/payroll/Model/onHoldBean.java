package payroll.Model;

public class onHoldBean {

	int srno,empno,holdby,releaseby;
	String empcode, salmonth,holdate,releasedate,status,hreason,rreason,currentdate, name;
	public int getSrno() {
		return srno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSrno(int srno) {
		this.srno = srno;
	}
	public int getEmpno() {
		return empno;
	}
	public void setEmpno(int empno) {
		this.empno = empno;
	}
	public int getHoldby() {
		return holdby;
	}
	public void setHoldby(int holdby) {
		this.holdby = holdby;
	}
	public int getReleaseby() {
		return releaseby;
	}
	public void setReleaseby(int releaseby) {
		this.releaseby = releaseby;
	}
	public String getEmpcode() {
		return empcode;
	}
	public void setEmpcode(String empcode) {
		this.empcode = empcode;
	}
	public String getSalmonth() {
		return salmonth;
	}
	public void setSalmonth(String salmonth) {
		this.salmonth = salmonth;
	}
	public String getHoldate() {
		return holdate;
	}
	public void setHoldate(String holdate) {
		this.holdate = holdate;
	}
	public String getReleasedate() {
		return releasedate;
	}
	public void setReleasedate(String releasedate) {
		this.releasedate = releasedate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getHreason() {
		return hreason;
	}
	public void setHreason(String hreason) {
		this.hreason = hreason;
	}
	public String getRreason() {
		return rreason;
	}
	public void setRreason(String rreason) {
		this.rreason = rreason;
	}
	public String getCurrentdate() {
		return currentdate;
	}
	public void setCurrentdate(String currentdate) {
		this.currentdate = currentdate;
	}
	
	
}
