package payroll.Model;

public class EmpOffBean {

	
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
    private	String site_name;
    private String site_feild3;
    public String getSite_feild3() {
		return site_feild3;
	}
	public void setSite_feild3(String site_feild3) {
		this.site_feild3 = site_feild3;
	}
	private int managerId;
	
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	private  int prj_srno;
	public int getPrj_srno() {
		return prj_srno;
	}
	public void setPrj_srno(int prj_srno) {
		this.prj_srno = prj_srno;
	}
	public String getPrj_code() {
		return Prj_code;
	}
	public void setPrj_code(String prj_code) {
		Prj_code = prj_code;
	}
	public String getPrj_name() {
		return prj_name;
	}
	public void setPrj_name(String prj_name) {
		this.prj_name = prj_name;
	}
	private String Prj_code ;
    private	String prj_name;  
	private int EMPNO; 
	private String EFFDATE; 
	private int TRNCD;
    private int SRNO; 
	private String ORDER_NO; 
	private String ORDER_DT; 
	private String MDESC; 
	private int BRANCH;
	
	private String ACNO; 
	private int GRADE;
	
	private int DESIG;
	
	private int DEPT; 
	
	private int STATUS; 
	private String OPRCD; 
	private String ENT_DT; 
	private String MOD_USR; 
	private String MOD_DT;
	private String Site_id;
	
	
	public String getSite_id() {
		return Site_id;
	}
	public void setSite_id(String site_id) {
		Site_id = site_id;
	}
	public int getEMPNO() {
		return EMPNO;
	}
	public void setEMPNO(int eMPNO) {
		EMPNO = eMPNO;
	}
	public String getEFFDATE() {
		return EFFDATE;
	}
	public void setEFFDATE(String eFFDATE) {
		EFFDATE = eFFDATE;
	}
	public int getTRNCD() {
		return TRNCD;
	}
	public void setTRNCD(int tRNCD) {
		TRNCD = tRNCD;
	}
	public int getSRNO() {
		return SRNO;
	}
	public void setSRNO(int sRNO) {
		SRNO = sRNO;
	}
	public String getORDER_NO() {
		return ORDER_NO;
	}
	public void setORDER_NO(String oRDER_NO) {
		ORDER_NO = oRDER_NO;
	}
	public String getORDER_DT() {
		return ORDER_DT;
	}
	public void setORDER_DT(String oRDER_DT) {
		ORDER_DT = oRDER_DT;
	}
	public String getMDESC() {
		return MDESC;
	}
	public void setMDESC(String mDESC) {
		MDESC = mDESC;
	}
	public int getBRANCH() {
		return BRANCH;
	}
	public void setBRANCH(int bRANCH) {
		BRANCH = bRANCH;
	}
	
	public String getACNO() {
		return ACNO;
	}
	public void setACNO(String aCNO) {
		ACNO = aCNO;
	}
	public int getGRADE() {
		return GRADE;
	}
	public void setGRADE(int gRADE) {
		GRADE = gRADE;
	}
	
	public int getDESIG() {
		return DESIG;
	}
	public void setDESIG(int dESIG) {
		DESIG = dESIG;
	}
	
	public int getDEPT() {
		return DEPT;
	}
	public void setDEPT(int dEPT) {
		DEPT = dEPT;
	}
	
	public int getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(int sTATUS) {
		STATUS = sTATUS;
	}
	public String getOPRCD() {
		return OPRCD;
	}
	public void setOPRCD(String oPRCD) {
		OPRCD = oPRCD;
	}
	public String getENT_DT() {
		return ENT_DT;
	}
	public void setENT_DT(String eNT_DT) {
		ENT_DT = eNT_DT;
	}
	public String getMOD_USR() {
		return MOD_USR;
	}
	public void setMOD_USR(String mOD_USR) {
		MOD_USR = mOD_USR;
	}
	public String getMOD_DT() {
		return MOD_DT;
	}
	public void setMOD_DT(String mOD_DT) {
		MOD_DT = mOD_DT;
	}
	
	private int BANK_NAME;
	
	public int getBANK_NAME(){
		return BANK_NAME;
	}
	public void setBANK_NAME(int bANK_NAME){
		BANK_NAME = bANK_NAME;
	}
		
}
