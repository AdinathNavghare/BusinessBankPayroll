package payroll.Model;

public class GraphBean 
{
	String Empname;
	String Prj_Code;
	String totalemp;
	int Empno;
	String designation;
	public String getPrj_Code() {
		return Prj_Code;
	}
	public void setPrj_Code(String prj_Code) {
		Prj_Code = prj_Code;
	}
	
	public String getTotalemp() {
		return totalemp;
	}
	public void setTotalemp(String totalemp) {
		this.totalemp = totalemp;
	}
	public String getEmpname() {
		return Empname;
	}
	public void setEmpname(String empname) {
		Empname = empname;
	}
	public int getEmpno() {
		return Empno;
	}
	public void setEmpno(int empno) {
		Empno = empno;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	

}
