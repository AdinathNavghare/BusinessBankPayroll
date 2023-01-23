package payroll.Model;

public class GradeBean {

	private int serialNumber;
	private int gradeCode;
	private String startDate;
	private String endDate;
	private float basic;
	private float increment;
	private float daPercentOrFixedValue;
	private float hraPercentOrFixedValue;
	
	private float amt1;
	private float amt2;
	private String creationDate; 
	private int gradeStatus;
	private int daValueType;
	private int hraValueType;
	private int vdaValueType;
	private float daValue;
	private float hraValue;
   private float vdaValue;
  private float vda;
	 private int POSTCD;
	 private String ALFACD;
	 private String DISC;
	 private String EFFDT;
	 private int BASIC;
	 private int INCR1;
	 private int NOY1;
	 private int INCR2;
	 private int NOY2;
	 private int INCR3;
	 private int NOY3;
	 private int INCR4;
	 private int NOY4;
	 private int INCR5;
	 private int NOY5;
	 
	 private float med_all;
	 private float edu_all;
	 private float conv_all;
	 
	 public int getPOSTCD() {
		return POSTCD;
	}
	public void setPOSTCD(int pOSTCD) {
		POSTCD = pOSTCD;
	}
	public String getALFACD() {
		return ALFACD;
	}
	public void setALFACD(String aLFACD) {
		ALFACD = aLFACD;
	}
	public String getDISC() {
		return DISC;
	}
	public void setDISC(String dISC) {
		DISC = dISC;
	}
	public String getEFFDT() {
		return EFFDT;
	}
	public void setEFFDT(String eFFDT) {
		EFFDT = eFFDT;
	}
	public int getBASIC() {
		return BASIC;
	}
	public void setBASIC(int bASIC) {
		BASIC = bASIC;
	}
	public int getINCR1() {
		return INCR1;
	}
	public void setINCR1(int iNCR1) {
		INCR1 = iNCR1;
	}
	public int getNOY1() {
		return NOY1;
	}
	public void setNOY1(int nOY1) {
		NOY1 = nOY1;
	}
	public int getINCR2() {
		return INCR2;
	}
	public void setINCR2(int iNCR2) {
		INCR2 = iNCR2;
	}
	public int getNOY2() {
		return NOY2;
	}
	public void setNOY2(int nOY2) {
		NOY2 = nOY2;
	}
	public int getINCR3() {
		return INCR3;
	}
	public void setINCR3(int iNCR3) {
		INCR3 = iNCR3;
	}
	public int getNOY3() {
		return NOY3;
	}
	public void setNOY3(int nOY3) {
		NOY3 = nOY3;
	}
	public int getINCR4() {
		return INCR4;
	}
	public void setINCR4(int iNCR4) {
		INCR4 = iNCR4;
	}
	public int getNOY4() {
		return NOY4;
	}
	public void setNOY4(int nOY4) {
		NOY4 = nOY4;
	}
	public int getINCR5() {
		return INCR5;
	}
	public void setINCR5(int iNCR5) {
		INCR5 = iNCR5;
	}
	public int getNOY5() {
		return NOY5;
	}
	public void setNOY5(int nOY5) {
		NOY5 = nOY5;
	}
	public int getINCR6() {
		return INCR6;
	}
	public void setINCR6(int iNCR6) {
		INCR6 = iNCR6;
	}
	public int getNOY6() {
		return NOY6;
	}
	public void setNOY6(int nOY6) {
		NOY6 = nOY6;
	}
	public int getEXG() {
		return EXG;
	}
	public void setEXG(int eXG) {
		EXG = eXG;
	}
	public int getMED() {
		return MED;
	}
	public void setMED(int mED) {
		MED = mED;
	}
	public int getEDU() {
		return EDU;
	}
	public void setEDU(int eDU) {
		EDU = eDU;
	}
	public int getLTC() {
		return LTC;
	}
	public void setLTC(int lTC) {
		LTC = lTC;
	}
	public int getCLOSING() {
		return CLOSING;
	}
	public void setCLOSING(int cLOSING) {
		CLOSING = cLOSING;
	}
	public int getCONV() {
		return CONV;
	}
	public void setCONV(int cONV) {
		CONV = cONV;
	}
	public int getCASH() {
		return CASH;
	}
	public void setCASH(int cASH) {
		CASH = cASH;
	}
	public int getCLG() {
		return CLG;
	}
	public void setCLG(int cLG) {
		CLG = cLG;
	}
	public int getWASHING() {
		return WASHING;
	}
	public void setWASHING(int wASHING) {
		WASHING = wASHING;
	}
	public int getFLDWRK() {
		return FLDWRK;
	}
	public void setFLDWRK(int fLDWRK) {
		FLDWRK = fLDWRK;
	}
	private int INCR6;
	 private int NOY6;
	 private int EXG;
	 private int MED;
	 private int EDU;
	 private int LTC;
	 private int CLOSING;
	 private int CONV;
	 private int CASH;
	 private int CLG;
	 private int WASHING;
	 private int FLDWRK;
	



	public int getGradeCode() {
		return gradeCode;
	}
	public void setGradeCode(int gradeCode) {
		this.gradeCode = gradeCode;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public float getBasic() {
		return basic;
	}
	public void setBasic(float basic) {
		this.basic = basic;
	}

	public float getAmt1() {
		return amt1;
	}
	public void setAmt1(float amt1) {
		this.amt1 = amt1;
	}
	public float getAmt2() {
		return amt2;
	}
	public void setAmt2(float amt2) {
		this.amt2 = amt2;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public int getGradeStatus() {
		return gradeStatus;
	}
	public void setGradeStatus(int gradeStatus) {
		this.gradeStatus = gradeStatus;
	}

	public float getDaValue() {
		return daValue;
	}
	public void setDaValue(float daValue) {
		this.daValue = daValue;
	}
	public float getHraValue() {
		return hraValue;
	}
	public void setHraValue(float hraValue) {
		this.hraValue = hraValue;
	}
	public float getIncrement() {
		return increment;
	}
	public void setIncrement(float increment) {
		this.increment = increment;
	}
	public int getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	public float getDaPercentOrFixedValue() {
		return daPercentOrFixedValue;
	}
	public void setDaPercentOrFixedValue(float daPercentOrFixedValue) {
		this.daPercentOrFixedValue = daPercentOrFixedValue;
	}
	public float getHraPercentOrFixedValue() {
		return hraPercentOrFixedValue;
	}
	public void setHraPercentOrFixedValue(float hraPercentOrFixedValue) {
		this.hraPercentOrFixedValue = hraPercentOrFixedValue;
	}
	public int getDaValueType() {
		return daValueType;
	}
	public void setDaValueType(int daValueType) {
		this.daValueType = daValueType;
	}
	public int getHraValueType() {
		return hraValueType;
	}
	public void setHraValueType(int hraValueType) {
		this.hraValueType = hraValueType;
	}
	public float getVdaValue() {
		return vdaValue;
	}
	public void setVdaValue(float vdaValue) {
		this.vdaValue = vdaValue;
	}
	public float getMed_all() {
		return med_all;
	}
	public void setMed_all(float med_all) {
		this.med_all = med_all;
	}
	public float getEdu_all() {
		return edu_all;
	}
	public void setEdu_all(float edu_all) {
		this.edu_all = edu_all;
	}
	public float getConv_all() {
		return conv_all;
	}
	public void setConv_all(float conv_all) {
		this.conv_all = conv_all;
	}
	public int getVdaValueType() {
		return vdaValueType;
	}
	public void setVdaValueType(int vdaValueType) {
		this.vdaValueType = vdaValueType;
	}
	public float getVda() {
		return vda;
	}
	public void setVda(float vda) {
		this.vda = vda;
	}


}
