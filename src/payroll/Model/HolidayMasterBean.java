package payroll.Model;

public class HolidayMasterBean {
	
	String holdname;
	String fromdate;
	String todate;
	String branch;
	String text;
	String type;
	String repeathold;
	String day;
	String attend;
	String sms;
	String optionalhold;
	int brcd;
	int holino;
	
	
	public int getHolino() {
		return holino;
	}
	public void setHolino(int holino) {
		this.holino = holino;
	}
	public int getBrcd(){
		return brcd;
	}
	public void setBrcd(int brcd){
		this.brcd = brcd;
	}
	public String getBranch(){
		return branch;
	}
	public void setBranch(String branch){
		this.branch = branch;
	}
	public String getHoldname(){
		return holdname;
	}
	public void setHoldname(String holdname){
			this.holdname = holdname;
	}
	public String getFromdate(){
		return fromdate;
	}
	public void setFromdate(String fromdate){
			this.fromdate = fromdate;
	}
	public String getTodate(){
		return todate;
	}
	public void setTodate(String todate){
			this.todate = todate;
	}
	public String getText(){
		return text;
	}
	public void setText(String text){
			this.text = text;
	}
	public String getType(){
		return type;
	}
	public void setType(String type){
			this.type = type;
	}
	public String getRepeathold(){
		return repeathold;
	}
	public void setRepeathold(String repeathold){
			this.repeathold = repeathold;
	}
	public String getDay(){
		return day;
	}
	public void setDay(String day){
			this.day = day;
	}
	public String getAttend(){
		return attend;
	}
	public void setAttend(String attend){
			this.attend = attend;
	}
	public String getSms(){
		return sms;
	}
	public void setSms(String sms){
			this.sms = sms;
	}
	public String getOptionalhold(){
		return optionalhold;
	}
	public void setOptionalhold(String optionalhold){
			this.optionalhold = optionalhold;
	}
}
