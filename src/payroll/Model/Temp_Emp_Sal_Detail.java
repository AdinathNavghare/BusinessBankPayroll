package payroll.Model;

public class Temp_Emp_Sal_Detail {

	int Empno;
	public int getEmpno() {
		return Empno;
	}
	public void setEmpno(int empno) {
		Empno = empno;
	}
	public String getTrndt() {
		return Trndt;
	}
	public void setTrndt(String trndt) {
		Trndt = trndt;
	}
	public String getSalary() {
		return Salary;
	}
	public void setSalary(String salary) {
		Salary = salary;
	}
	public String getDeduction() {
		return Deduction;
	}
	public void setDeduction(String deduction) {
		Deduction = deduction;
	}
	public String getPTAmt() {
		return PTAmt;
	}
	public void setPTAmt(String pTAmt) {
		PTAmt = pTAmt;
	}
	public String getNet_Amt() {
		return Net_Amt;
	}
	public void setNet_Amt(String net_Amt) {
		Net_Amt = net_Amt;
	}
	String Trndt;
	String Salary;
	String Deduction;
	int AbsentDay;
	public int getAbsentDay() {
		return AbsentDay;
	}
	public void setAbsentDay(int absentDay) {
		AbsentDay = absentDay;
	}
	String PTAmt;
	String Net_Amt;
	String ITax;
	public String getITax() {
		return ITax;
	}
	public void setITax(String iTax) {
		ITax = iTax;
	}
	
	
}
