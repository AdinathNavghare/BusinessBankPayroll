package payroll.Model;

public class ITBean {
   int empno;
   String year;
   float grossSalary;
   float totalDeduction;
   float netTaxableIncome;
   float taxOnIncome;
   float taxCredit;
   float taxAfterReducingCredit;
   float totalTaxLiability;
   float totalTaxPaid;
   float totalTaxRemaining;
   float remainingMonths;
   float finalizedMonths;
   float monthlyInstallment;
   String status;
   
   
   
public int getEmpno() {
	return empno;
}
public void setEmpno(int empno) {
	this.empno = empno;
}
public String getYear() {
	return year;
}
public void setYear(String year) {
	this.year = year;
}
public float getTotalDeduction() {
	return totalDeduction;
}
public void setTotalDeduction(float totalDeduction) {
	this.totalDeduction = totalDeduction;
}
public float getNetTaxableIncome() {
	return netTaxableIncome;
}
public void setNetTaxableIncome(float netTaxableIncome) {
	this.netTaxableIncome = netTaxableIncome;
}
public float getTaxOnIncome() {
	return taxOnIncome;
}
public void setTaxOnIncome(float taxOnIncome) {
	this.taxOnIncome = taxOnIncome;
}
public float getTaxCredit() {
	return taxCredit;
}
public void setTaxCredit(float taxCredit) {
	this.taxCredit = taxCredit;
}
public float getTaxAfterReducingCredit() {
	return taxAfterReducingCredit;
}
public void setTaxAfterReducingCredit(float taxAfterReducingCredit) {
	this.taxAfterReducingCredit = taxAfterReducingCredit;
}
public float getTotalTaxLiability() {
	return totalTaxLiability;
}
public void setTotalTaxLiability(float totalTaxLiability) {
	this.totalTaxLiability = totalTaxLiability;
}
public float getTotalTaxPaid() {
	return totalTaxPaid;
}
public void setTotalTaxPaid(float totalTaxPaid) {
	this.totalTaxPaid = totalTaxPaid;
}
public float getTotalTaxRemaining() {
	return totalTaxRemaining;
}
public void setTotalTaxRemaining(float totalTaxRemaining) {
	this.totalTaxRemaining = totalTaxRemaining;
}
public float getRemainingMonths() {
	return remainingMonths;
}
public void setRemainingMonths(float remainingMonths) {
	this.remainingMonths = remainingMonths;
}
public float getFinalizedMonths() {
	return finalizedMonths;
}
public void setFinalizedMonths(float finalizedMonths) {
	this.finalizedMonths = finalizedMonths;
}
public float getMonthlyInstallment() {
	return monthlyInstallment;
}
public void setMonthlyInstallment(float monthlyInstallment) {
	this.monthlyInstallment = monthlyInstallment;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public float getGrossSalary() {
	return grossSalary;
}
public void setGrossSalary(float grossSalary) {
	this.grossSalary = grossSalary;
}
}
