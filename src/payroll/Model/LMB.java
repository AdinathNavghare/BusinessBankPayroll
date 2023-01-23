package payroll.Model;

public class LMB {

	
		int LEAVECD;
		int CANCELLEAVE;
		String TRNDATE;
		int EMPNO;
		
		String serachtype;
		String EFFDATE;
	    String FREQUENCY;
	    int MAXLEAVECF;
	    
	    int CRLIM;
	    int MAXIMUM;
	    int MINIMUM;
	    String LEAVEDES;
	    String LEAVEENCASH;
	    String APPLNO;
		String LTYPE;
		String FNAME;
		int SRNO;
		int SUBSTCD;
		int BRCODE;
		int LEAVEPURP;
		String LREASON;
		String LADDR;
		long  LTELNO;
		String APPLDT;
		String FRMDT;
		String TODT;
		String SANCAUTH;
		String OPR_CD;
		String OFF_CD;
		String STATUS;
		String SUBSTITUTE;
		String BALDT;
		float BAL;
		float NODAYS;
		char TRNTYPE;
		float TOTCR;
		float TOTDR;
		String HALFDAY;
		String prj_srno;
		int created_by;
		String created_date;
		String empCode;
		int flag_editSanction;
		
	     public int getFlag_editSanction() {
			return flag_editSanction;
		}
		public void setFlag_editSanction(int flag_editSanction) {
			this.flag_editSanction = flag_editSanction;
		}
		public String getPrj_srno() {
			return prj_srno;
		}
		public void setPrj_srno(String prj_srno) {
			this.prj_srno = prj_srno;
		}
		
		
	     public String getHALFDAY() {
			return HALFDAY;
		}
		public void setHALFDAY(String hALFDAY) {
			HALFDAY = hALFDAY;
		}
		public int getMAXLEAVECF() {
			return MAXLEAVECF;
		}
		public void setMAXLEAVECF(int mAXLEAVECF) {
			MAXLEAVECF = mAXLEAVECF;
		}
		public String getEFFDATE() {
			return EFFDATE;
		}
		public void setEFFDATE(String eFFDATE) {
			EFFDATE = eFFDATE;
		}
		public String getFREQUENCY() {
			return FREQUENCY;
		}
		public void setFREQUENCY(String fREQUENCY) {
			FREQUENCY = fREQUENCY;
		}
		public int getCRLIM() {
			return CRLIM;
		}
		public void setCRLIM(int cRLIM) {
			CRLIM = cRLIM;
		}
		public int getMAXIMUM() {
			return MAXIMUM;
		}
		public void setMAXIMUM(int mAXIMUM) {
			MAXIMUM = mAXIMUM;
		}
		public int getMINIMUM() {
			return MINIMUM;
		}
		public void setMINIMUM(int mINIMUM) {
			MINIMUM = mINIMUM;
		}
		public String getLEAVEDES() {
			return LEAVEDES;
		}
		public void setLEAVEDES(String lEAVEDES) {
			LEAVEDES = lEAVEDES;
		}
		public String getLEAVEENCASH() {
			return LEAVEENCASH;
		}
		public void setLEAVEENCASH(String lEAVEENCASH) {
			LEAVEENCASH = lEAVEENCASH;
		}
		public String getSerachtype() {
			return serachtype;
		}
		public void setSerachtype(String serachtype) {
			this.serachtype = serachtype;
		}
		int EMPNO2;
		public int getEMPNO2() {
			return EMPNO2;
		}
		public void setEMPNO2(int eMPNO2) {
			EMPNO2 = eMPNO2;
		}
		
		public int getSRNO() {
			return SRNO;
		}
		public void setSRNO(int sRNO) {
			SRNO = sRNO;
		}
		public int getSUBSTCD() {
			return SUBSTCD;
		}
		public void setSUBSTCD(int sUBSTCD) {
			SUBSTCD = sUBSTCD;
		}
		
		public String getFNAME() {
			return FNAME;
		}
		public void setFNAME(String fNAME) {
			FNAME = fNAME;
		}
		public String getLNAME() {
			return LNAME;
		}
		public void setLNAME(String lNAME) {
			LNAME = lNAME;
		}
		String LNAME;
		 
		public String getLTYPE() {
			return LTYPE;
		}
		public void setLTYPE(String lTYPE) {
			LTYPE = lTYPE;
		}
		
		public int getLEAVECD() {
			return LEAVECD;
		}
		public void setLEAVECD(int lEAVECD) {
			LEAVECD = lEAVECD;
		}
		public String getTRNDATE() {
			return TRNDATE;
		}
		public void setTRNDATE(String tRNDATE) {
			TRNDATE = tRNDATE;
		}
		public int getEMPNO() {
			return EMPNO;
		}
		public void setEMPNO(int empno2) {
			EMPNO = empno2;
		}
		public char getTRNTYPE() {
			return TRNTYPE;
		}
		public void setTRNTYPE(char tRNTYPE) {
			TRNTYPE = tRNTYPE;
		}
		public String getAPPLNO() {
			return APPLNO;
		}
		public void setAPPLNO(String aPPLNO) {
			APPLNO = aPPLNO;
		}
		public int getBRCODE() {
			return BRCODE;
		}
		public void setBRCODE(int branchcode) {
			BRCODE = branchcode;
		}
		public int getLEAVEPURP() {
			return LEAVEPURP;
		}
		public void setLEAVEPURP(int leavepurpose) {
			LEAVEPURP = leavepurpose;
		}
		public String getLREASON() {
			return LREASON;
		}
		public void setLREASON(String lREASON) {
			LREASON = lREASON;
		}
		public String getLADDR() {
			return LADDR;
		}
		public void setLADDR(String lADDR) {
			LADDR = lADDR;
		}
		public long getLTELNO() {
			return LTELNO;
		}
		public void setLTELNO(long telephoneno) {
			LTELNO = telephoneno;
		}
		public String getAPPLDT() {
			return APPLDT;
		}
		public void setAPPLDT(String aPPLDT) {
			APPLDT = aPPLDT;
		}
		public String getFRMDT() {
			return FRMDT;
		}
		public void setFRMDT(String fromdate1) {
			FRMDT = fromdate1;
		}
		public String getTODT() {
			return TODT;
		}
		public void setTODT(String todate) {
			TODT = todate;
		}
		public String getSANCAUTH() {
			return SANCAUTH;
		}
		public void setSANCAUTH(String sacnctionby) {
			SANCAUTH = sacnctionby;
		}
		public String getOPR_CD() {
			return OPR_CD;
		}
		public void setOPR_CD(String oPR_CD) {
			OPR_CD = oPR_CD;
		}
		public String getOFF_CD() {
			return OFF_CD;
		}
		public void setOFF_CD(String oFF_CD) {
			OFF_CD = oFF_CD;
		}
		public String getSTATUS() {
			return STATUS;
		}
		public void setSTATUS(String sTATUS) {
			STATUS = sTATUS;
		}
		public String getSUBSTITUTE() {
			return SUBSTITUTE;
		}
		public void setSUBSTITUTE(String sUBSTITUTE) {
			SUBSTITUTE = sUBSTITUTE;
		}
		
		public String getBALDT() {
			return BALDT;
		}
		public void setBALDT(String bALDT) {
			BALDT = bALDT;
		}
		public float getBAL() {
			return BAL;
		}
		public void setBAL(float bAL) {
			BAL = bAL;
		}
		public float getTOTCR() {
			return TOTCR;
		}
		public void setTOTCR(float tOTCR) {
			TOTCR = tOTCR;
		}
		public float getTOTDR() {
			return TOTDR;
		}
		public void setTOTDR(float tOTDR) {
			TOTDR = tOTDR;
		}
		
		public float getNODAYS() {
			return NODAYS;
		}
		public void setNODAYS(float nODAYS) {
			NODAYS = nODAYS;
		}
		public int getCreated_by() {
			return created_by;
		}
		public void setCreated_by(int created_by) {
			this.created_by = created_by;
		}
		public String getCreated_date() {
			return created_date;
		}
		public void setCreated_date(String created_date) {
			this.created_date = created_date;
		}
		public String getEmpCode() {
			return empCode;
		}
		public void setEmpCode(String empCode) {
			this.empCode = empCode;
		}
		
		public int getCANCELLEAVE() {
			return CANCELLEAVE;
		}
		public void setCANCELLEAVE(int cANCELLEAVE) {
			CANCELLEAVE = cANCELLEAVE;
		}
		
}
