package payroll.Model;

public class TransactionBean {

String empname;
String panno;
String pfno;
String accno;
int empno;
int grade;
int desg;
int dept;
int branch;
String DOJ;
String EMPCODE;
String Prj_Code;

public String getPrj_Code() {
	return Prj_Code;
}
public void setPrj_Code(String prj_Code) {
	Prj_Code = prj_Code;
}
public String getDOJ() {
	return DOJ;
}
public void setDOJ(String dOJ) {
	DOJ = dOJ;
}
public String getEmpname() {
	return empname;
}
public void setEmpname(String empname) {
	this.empname = empname;
}
public String getPanno() {
	return panno;
}
public void setPanno(String panno) {
	this.panno = panno;
}
public String getPfno() {
	return pfno;
}
public void setPfno(String pfno) {
	this.pfno = pfno;
}
public String getAccno() {
	return accno;
}
public void setAccno(String accno) {
	this.accno = accno;
}
public int getEmpno() {
	return empno;
}
public void setEmpno(int empno) {
	this.empno = empno;
}
public int getGrade() {
	return grade;
}
public void setGrade(int grade) {
	this.grade = grade;
}
public int getDesg() {
	return desg;
}
public void setDesg(int desg) {
	this.desg = desg;
}
public int getDept() {
	return dept;
}
public void setDept(int dept) {
	this.dept = dept;
}
public int getBranch() {
	return branch;
}
public void setBranch(int branch) {
	this.branch = branch;
}

public String getEMPCODE() {
	return EMPCODE;
}
public void setEMPCODE(String eMPCODE) {
	EMPCODE = eMPCODE;
}


}
