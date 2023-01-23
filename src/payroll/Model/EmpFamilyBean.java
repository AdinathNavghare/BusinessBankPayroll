package payroll.Model;

public class EmpFamilyBean {
	private String FAMILY; 
	  private int  EMPNO; 
		private int SRNO;
		private int RELATION; 
		//private String RELATIONS;
		
		private String NAME; 
		private String GENDER; 
		private String DOB; 
		//private String QUALIS; 
		private int QUALI;
		private String DEPENDYN; 
		private String OCCUPATION;
		
		public String getFAMILY() {
			return FAMILY;
		}
		public void setFAMILY(String fAMILY) {
			FAMILY = fAMILY;
		}
		public int getEMPNO() {
			return EMPNO;
		}
		public void setEMPNO(int eMPNO) {
			EMPNO = eMPNO;
		}
		public int getSRNO() {
			return SRNO;
		}
		public void setSRNO(int sRNO) {
			SRNO = sRNO;
		}
		public int getRELATION() {
			return RELATION;
		}
		public void setRELATION(int rELATION) {
			RELATION = rELATION;
		}
		public String getNAME() {
			return NAME;
		}
		public void setNAME(String nAME) {
			NAME = nAME;
		}
		public String getGENDER() {
			return GENDER;
		}
		public void setGENDER(String gENDER) {
			GENDER = gENDER;
		}
		public String getDOB() {
			return DOB;
		}
		public void setDOB(String dOB) {
			DOB = dOB;
		}
		
		public int getQUALI() {
			return QUALI;
		}
		public void setQUALI(int qUALI) {
			QUALI = qUALI;
		}
		public String getDEPENDYN() {
			return DEPENDYN;
		}
		public void setDEPENDYN(String dEPENDYN) {
			DEPENDYN = dEPENDYN;
		}
		public String getOCCUPATION() {
			return OCCUPATION;
		}
		public void setOCCUPATION(String oCCUPATION) {
			OCCUPATION = oCCUPATION;
		} 
		
		
		/*public String getQUALIS() {
			return QUALIS;
		}
		public void setQUALIS(String qUALIs) {
			QUALIS = qUALIs;
		}
		
		
		public String getRELATIONS() {
			return RELATIONS;
		}
		public void setRELATIONS(String rELATIONS) {
			RELATIONS = rELATIONS;
		}*/
}
