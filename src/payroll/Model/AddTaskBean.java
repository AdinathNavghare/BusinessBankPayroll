package payroll.Model;

public class AddTaskBean {
	
	private String PROJECT;
	private String TYPE;
	private String NAME; 
	private String STATUS; 
	private String PRIORITY; 
	private String SUBTASK; 
	private String ASSIGNED_TO;
	private String DISCRIPTION; 
	private String CREATED_BY;
	
	private String ESTIMATED_TIME;
	private String START_DATE;
	private String DUE_DATE;
	private int PROGRESS;
	private int TASK_ID;
	private int PROJECT_ID;
	
	private String DETAIL;
	private String ATTACHMENT;
	
	
	public String getPROJECT() {
		return PROJECT;
	}
	public void setPROJECT(String pROJECT) {
		PROJECT = pROJECT;
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getPRIORITY() {
		return PRIORITY;
	}
	public void setPRIORITY(String pRIORITY) {
		PRIORITY = pRIORITY;
	}
	
	public String getASSIGNED_TO() {
		return ASSIGNED_TO;
	}
	public void setASSIGNED_TO(String aSSIGNED_TO) {
		ASSIGNED_TO = aSSIGNED_TO;
	}
	public String getDISCRIPTION() {
		return DISCRIPTION;
	}
	public void setDISCRIPTION(String dISCRIPTION) {
		DISCRIPTION = dISCRIPTION;
	}
	public String getCREATED_BY() {
		return CREATED_BY;
	}
	public void setCREATED_BY(String cREATED_BY) {
		CREATED_BY = cREATED_BY;
	}
	public String getESTIMATED_TIME() {
		return ESTIMATED_TIME;
	}
	public void setESTIMATED_TIME(String eSTIMATED_TIME) {
		ESTIMATED_TIME = eSTIMATED_TIME;
	}
	public String getSTART_DATE() {
		return START_DATE;
	}
	public void setSTART_DATE(String sTART_DATE) {
		START_DATE = sTART_DATE;
	}
	public String getDUE_DATE() {
		return DUE_DATE;
	}
	public void setDUE_DATE(String dUE_DATE) {
		DUE_DATE = dUE_DATE;
	}
	public int getPROGRESS() {
		return PROGRESS;
	}
	public void setPROGRESS(int pROGRESS) {
		PROGRESS = pROGRESS;
	}
	public String getATTACHMENT() {
		return ATTACHMENT;
	}
	public void setATTACHMENT(String aTTACHMENT) {
		ATTACHMENT = aTTACHMENT;
	}
	public String getSUBTASK() {
		return SUBTASK;
	}
	public void setSUBTASK(String sUBTASK) {
		SUBTASK = sUBTASK;
	}
	public int getTASK_ID() {
		return TASK_ID;
	}
	public void setTASK_ID(int tASK_ID) {
		TASK_ID = tASK_ID;
	}
	public int getPROJECT_ID() {
		return PROJECT_ID;
	}
	public void setPROJECT_ID(int pROJECT_ID) {
		PROJECT_ID = pROJECT_ID;
	}
	
	
}
