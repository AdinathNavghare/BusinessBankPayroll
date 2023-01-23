package payroll.Model;

public class RecruitmentBean {

	int RecId;
	int LocId;
	float MinExp;
	int ProbPeriod;
	float BondTenure;
	int SalCTCFrom;
	int SalCTCTo;
	int NoOfRequirement;
	
	String RecruitDate;
	String ManagerName;
	String JobLocation;
	String Desig;
	String Dept;
	String RequirementType;
	String Education;
	String EmpType;
	String BondRequirement;
	String DocRequire;
	String Skills;
	String SpecialRequirement;
	

	public float getMinExp() {
		return MinExp;
	}
	public void setMinExp(float minExp) {
		this.MinExp = minExp;
	}
	
	public int getRecId() {
		return RecId;
	}
	public void setRecId(int recId) {
		this.RecId = recId;
	}
	
	public int getLocId() {
		return LocId;
	}
	public void setLocId(int locId) {
		this.LocId = locId;
	}
	public int getProbPeriod() {
		return ProbPeriod;
	}
	public void setProbPeriod(int probPeriod) {
		this.ProbPeriod = probPeriod;
	}
	
	public float getBondTenure() {
		return BondTenure;
	}
	public void setBondTenure(Float bondTenure) {
		this.BondTenure = bondTenure;
	}
	
	public int getSalCTCFrom() {
		return SalCTCFrom;
	}
	public void setSalCTCFrom(int salFrom) {
		this.SalCTCFrom = salFrom;
	}
    
	public int getSalCTCTo() {
		return SalCTCTo;
	}
	public void setSalCTCTo(int salTo) {
		this.SalCTCTo = salTo;
	}
	
	public int getNoOfRequirement() {
		return NoOfRequirement;
	}
	public void setNoOfRequirement(int noOfRequire) {
		this.NoOfRequirement = noOfRequire;
	}
	
	public String getRecruitDate() {
			return RecruitDate;
	}
	public void setRecruitDate(String recruitDate) {
			this.RecruitDate = recruitDate;
	}
	
	public String getManagerName() {
		return ManagerName;
	}
	public void setManagerName(String managerName) {
		this.ManagerName = managerName;
	}
	
	public String getJobLocation1() {
		return JobLocation;
	}
	public String getJobLocation(String s) {
		return s;
	}
	public void setJobLocation(String jobLocation) {
		this.JobLocation = jobLocation;
	}
	
	public String getDesig() {
		return Desig;
	}
	public void setDesig(String desig) {
		this.Desig = desig;
	}
	
	public String getDept() {
		return Dept;
	}
	public void setDept(String dept) {
		this.Dept = dept;
	}
	
	public String getReqType() {
		return RequirementType;
	}
	public void setReqType(String reqType) {
		this.RequirementType = reqType;
	}
	
	public String getEducation() {
		return Education;
	}
	public void setEducation(String educate) {
		this.Education = educate;
	}
	
	public String getEmpType() {
		return EmpType;
	}
	public void setEmpType(String empType) {
		this.EmpType = empType;
	}
	
	public String getBondRequire() {
		return BondRequirement;
	}
	public void setBondRequire(String bondRequire) {
		this.BondRequirement = bondRequire;
	}
	
	public String getDocRequire() {
		return DocRequire;
	}
	public void setDocRequire(String docRequire) {
		this.DocRequire = docRequire;
	}
	
	public String getSkills() {
		return Skills;
	}
	public void setSkills(String skills) {
		this.Skills = skills;
	}
	
	public String getSpecialReq() {
		return SpecialRequirement;
	}
	public void setSpecialReq(String specialReq) {
		this.SpecialRequirement = specialReq;
	}
}

