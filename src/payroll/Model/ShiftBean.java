package payroll.Model;

public class ShiftBean {
	String shift;
	String startTime;
	String endTime;
	String status;
	int srno;
	int shiftcode;
	
	String day;
	String daytype;
	int EMPNO;
	String daydate;
	String holiday;
	String desc;
	String checkin;
	public int getEMPNO() {
		return EMPNO;
	}
	public void setEMPNO(int eMPNO) {
		EMPNO = eMPNO;
	}
	public String getCheckin() {
		return checkin;
	}
	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}
	public String getCheckout() {
		return checkout;
	}
	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	String checkout;
	String total;
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getDaytype() {
		return daytype;
	}
	public void setDaytype(String daytype) {
		this.daytype = daytype;
	}
	public String getDaydate() {
		return daydate;
	}
	public void setDaydate(String daydate) {
		this.daydate = daydate;
	}
	public String getHoliday() {
		return holiday;
	}
	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	public int getShiftcode() {
		return shiftcode;
	}
	public void setShiftcode(int shiftcode) {
		this.shiftcode = shiftcode;
	}
	int emptype;
	public int getEmptype() {
		return emptype;
	}
	public void setEmptype(int emptype) {
		this.emptype = emptype;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	int grade;
	int rate;
	public int getSrno() {
		return srno;
	}
	public void setSrno(int srno) {
		this.srno = srno;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
