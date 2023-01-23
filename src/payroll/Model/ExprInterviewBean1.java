package payroll.Model;

public class ExprInterviewBean1 {
    
	int Eid;
	int Exprid;
	String PName;
	String DOB;
	String CurrDate;
	String Address1;
	String Address2;
	String Email;	
	long MobileNo;
	String MaritalStatus;
	String PassportNo;
	String PassportValid;
	String hobbies;
	String type;
	
	
	public int getEid() {
		return Eid;
	}
	public void setEid(int eid) {
		Eid = eid;
	}
	
	public int getExprid() {
		return Exprid;
	}
	public void setExprid(int exprid) {
		Exprid = exprid;
	}
	public String getPName() {
		return PName;
	}
	public void setPName(String pName) {
		PName = pName;
	}
	
	public String getHobbies() {
		return hobbies;
	}
	public void setHobbies(String hob) {
		hobbies = hob;
	}
	
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	
	public String getCrrDate() {
		return CurrDate;
	}
	public void setCrrDate(String crrDate) {
		CurrDate = crrDate;
	}
	public String getAddress1() {
		return Address1;
	}
	public void setAddress1(String address1) {
		Address1 = address1;
	}
	public String getAddress2() {
		return Address2;
	}
	public void setAddress2(String address2) {
		Address2 = address2;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	
	public long getMobileNo() {
		return MobileNo;
	}
	public void setMobileNo(long mobileNo) {
		MobileNo = mobileNo;
	}
	public String getMaritalStatus() {
		return MaritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		MaritalStatus = maritalStatus;
	}
	public String getPassportNo() {
		return PassportNo;
	}
	public void setPassportNo(String passportNo) {
		PassportNo = passportNo;
	}
	public String getPassportValid() {
		return PassportValid;
	}
	public void setPassportValid(String passportValid) {
		PassportValid = passportValid;
	}
	
	
	//new added
	public String getType() {
		return type;
	}
	public void setType(String type1) {
		type = type1;
	}
	
	//for family
	 String relation;
	 String rName;
	 String occupation;
	 int age;
	 
	 public String getRelation(){
		 return relation;
	 }
	 public void setRelation(String rel)
	 {
		 relation=rel;
	 }
	
	 public String getRelName()
	 {
		 return rName;
	 }
	 public void setRelName(String rname){
		 rName=rname;
	 }
	 
	 public int getAge()
	 {
		 return age;
	 }
	 public void setAge(int ag){
		 age=ag;
	 }
	 public String getOccupation(){
		 return occupation;
	 }
	 public void setOccupation(String occ)
	 {
		 occupation=occ;
	 }
	
	// For Language Known
	String Language;
	String Speak;
	String Read;
	String Write;

	public String getLanguage() {
		return Language;
	}
	public void setLanguage(String language) {
		Language = language;
	}
	public String getSpeak() {
		return Speak;
	}
	public void setSpeak(String speak) {
		Speak = speak;
	}
	public String getRead() {
		return Read;
	}
	public void setRead(String read) {
		Read = read;
	}
	public String getWrite() {
		return Write;
	}
	public void setWrite(String write) {
		Write = write;
	}
	
	
	// For Qualification 
	String Quali;
	String Degree;
	String Collage;
	String Special;
	String Year;
	String Std;
	float Marks;

	public String getQuali() {
		return Quali;
	}
	public void setQuali(String quali) {
		Quali = quali;
	}
	public String getDegree() {
		return Degree;
	}
	public void setDegree(String degree) {
		Degree = degree;
	}
	public String getCollage() {
		return Collage;
	}
	public void setCollage(String collage) {
		Collage = collage;
	}
	public String getSpecial() {
		return Special;
	}
	public void setSpecial(String special) {
		Special = special;
	}
	public String getYear() {
		return Year;
	}
	public void setYear(String year) {
		Year = year;
	}
	
	public String getStd() {
		return Std;
	}
	public void setStd(String std) {
		Std = std;
	}
	public float getMarks() {
		return Marks;
	}
	public void setMarks(float marks) {
		Marks = marks;
	}
	
	//For Employment Details
	int EmployId;
	String OrgName;
	String FromDate;
	String ToDate;
	String StartPos;
	String LeavPos;
	String LeavReas;
	String JobDes;
	float SalCTC;
	
	public int getEmployId()
	{
		return EmployId;
	}
	public void setEmployId(int employId)
	{
		EmployId=employId;
	}
	public String getOrgName()
	{
		return OrgName;
	}
	public void setOrgName(String orgName)
	{
		OrgName=orgName;
	}
	public String getFromDate()
	{
		return FromDate;
	}
	public void setFromDate(String fromDate)
	{
		FromDate=fromDate;
	}
	public String getToDate()
	{
		return ToDate;
	}
	public void setToDate(String toDate)
	{
		ToDate=toDate;
	}
	public String getStartPos()
	{
		return StartPos;
	}
	public void setStartPos(String startPos)
	{
		StartPos=startPos;
	}
	public String getLeavPos()
	{
		return LeavPos;
	}
	public void setLeavPos(String leavPos)
	{
		LeavPos=leavPos;
	}
	public String getLeavReas()
	{
		return LeavReas;
	}
	public void setLeavReas(String leavReas)
	{
		LeavReas=leavReas;
	}
	public String getJobDes()
	{
		return JobDes;
	}
	public void setJobDes(String jobDes)
	{
		JobDes=jobDes;
	}
	public float getSalCTC()
	{
		return SalCTC;
	}
	public void setSalCTC(float salCTC)
	{
		SalCTC=salCTC;
	}
	
	//Computer Literacy
	
	int LitId;
	String LitName;
	String LitType;
	String Prof;
	String Avg;
	String Elementary;
	
	public int getLitId()
	{
		return LitId;
	}
	public void setLitId(int litId)
	{
		LitId=litId;
	}
	public String getLitName()
	{
		return LitName;
	}
	public void setLitName(String litName)
	{
		LitName=litName;
	}
	
	public String getLitType()
	{
		return LitType;
	}
	public void setLitType(String litType)
	{
		LitType=litType;
	}
	
	public String getProf()
	{
		return Prof;
	}
	public void setProf(String prof)
	{
		Prof=prof;
	}
	public String getAvg()
	{
		return Avg;
	}
	public void setAvg(String avg)
	{
		Avg=avg;
	}
	public String getElementary()
	{
		return Elementary;
	}
	public void setElementary(String elementary)
	{
		Elementary=elementary;
	}
	
	
	//for reference 1
	
	String refName;
	String refDesig;
	String refOrg;
	long refMob;
		String refEmail;
		public String getRefName()
		{
			return refName;
			
		}
		public void setRefName(String refname)
		{
			refName=refname;
		}
		
		public String getRefDesig()
		{
			return refDesig;
			
		}
		
		public void setRefDesig(String refdesig)
		{
			refDesig=refdesig;
		}
		
		public String getRefOrganization()
		{
			return refOrg;
		}
		public void setRefOrganization(String reforg)
		{
			refOrg=reforg;
		}
		
		
		public long getRefMobile(){
			return refMob;
		}
			
		public void setRefMobile(long mob){
			refMob=mob;
		}
		
		public String getRefMail(){
			return refEmail;
		}
		public void setRefMail(String mail)
		{
			refEmail=mail;
		}
		
		//for reference 1
		
		String refName2;
		String refDesig2;
		String refOrg2;
		long refMob2;
			String refEmail2;
			public String getRefName2()
			{
				return refName;
				
			}
			public void setRefName2(String refname)
			{
				refName=refname;
			}
			
			public String getRefDesig2()
			{
				return refDesig;
				
			}
			
			public void setRefDesig2(String refdesig)
			{
				refDesig=refdesig;
			}
			
			public String getRefOrganization2()
			{
				return refOrg;
			}
			public void setRefOrganization2(String reforg)
			{
				refOrg=reforg;
			}
			
			
			public long getRefMobile2(){
				return refMob;
			}
				
			public void setRefMobile2(long mob){
				refMob=mob;
			}
			
			public String getRefMail2(){
				return refEmail;
			}
			public void setRefMail2(String mail)
			{
				refEmail=mail;
			}
}
