package payroll.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import payroll.Model.ExprInterviewBean1;
import payroll.Model.ExprInterviewBean6;
import payroll.Model.RecruitmentBean;

public class ExprInterviewDao {
	int s=0;

	public boolean AddExprInfo1(ExprInterviewBean1 expr) { 
		// TODO Auto-generated method stub
		Boolean flag=false;
		Connection con=null;
		ArrayList<ExprInterviewBean1> result = new ArrayList<ExprInterviewBean1>();
		String DOBDT = null; 
		String CRRDT = null;
		String PASSDT = null;
		try{
			System.out.println("DOB : "+expr.getDOB());
			//if(expr.getDOB()==null) {DOBDT = null;}else {DOBDT = "'"+expr.getDOB()+"'";}
			//if(expr.getCrrDate()==null) {CRRDT = null;}else {CRRDT = "'"+expr.getCrrDate()+"'";}
			if(expr.getPassportValid()== null) 
			{
				PASSDT = null;
			}else
			{
				PASSDT = "'"+expr.getPassportValid()+"'";
			}
			System.out.println("DOB1 : "+PASSDT);
			 con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			String str="insert into ExprPersonalInfo(PName,DOB,Address1,Address2,Email,MobileNo,MaritalStatus,PassportNo,PassportValid,CurrentDate,Hobbies) VALUES"
					+ "('"
					+ expr.getPName()
					+ "','"
					+expr.getDOB()
					+ "','"
					+ expr.getAddress1()
					+ "','"
					+ expr.getAddress2()
					+ "','"
					+ expr.getEmail()
					+ "','"
					+ expr.getMobileNo()
					+ "','"
					+ expr.getMaritalStatus()
					+ "','"
					+ expr.getPassportNo()
					+ "',"
					+ PASSDT 
					+",'"
					+expr.getCrrDate()
					+"','" 
					+expr.getHobbies()
//					+"', '"
//					+expr.getType()
					+ "')";
			
			System.out.println(str);
			st.execute(str);
			 
			
			
			ExprInterviewBean1 Rbean =new ExprInterviewBean1();
			String str2="SELECT TOP(1) Exprid FROM ExprPersonalInfo ORDER BY 1 DESC";
			ResultSet rs= st.executeQuery(str2);
			rs.next();
			 s=rs.getInt("Exprid");
			
			//PersonalRec(s);
			
			System.out.println(s+"llllll");
			Rbean.setExprid(s);
			result.add(Rbean);
			flag=true;
			con.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	
	}

	public boolean AddLanguages(ExprInterviewBean1 expr) {
		// TODO Auto-generated method stub
		Boolean flag=false;
		Connection con=null;
		
		try{
			con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			String str="insert into ExprLanguage(LangName,Speaks,Reads,Writes,Exprid)VALUES"
					+ "('"
					+ expr.getLanguage()
					+ "','"
					+ expr.getSpeak()
					+ "','"
					+ expr.getRead()
					+ "','"
					+ expr.getWrite()
					+ "',"
					+ "( SELECT TOP(1) Exprid FROM ExprPersonalInfo ORDER BY 1 DESC)"
					+ ")";
			
			System.out.println(str);
			st.execute(str);
			con.close();
			flag=true;
		}
		catch (Exception e) {
				e.printStackTrace();
			}
			return flag;																			
	}
	public boolean AddCompLiteracy(ExprInterviewBean1 expr) {
		// TODO Auto-generated method stub
		Boolean flag=false;
		Connection con=null;
		
		try{
			con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			String str="insert into Literacy(LitType,LitName,Proficient,Average,Elementary,Exprid)VALUES"
					+ "('"
					+ expr.getLitType()
					+ "','"
					+ expr.getLitName()
					+ "','"
					+ expr.getProf()
					+ "','"
					+ expr.getAvg()
					+ "','"
					+ expr.getElementary()
					+ "',"
					+ "( SELECT TOP(1) Exprid FROM ExprPersonalInfo ORDER BY 1 DESC)"
					+ ")";
			
			System.out.println(str);
			st.execute(str);
			con.close();
			flag=true;
		}
		catch (Exception e) {
				e.printStackTrace();
			}
			return flag;																			
	}
	public static boolean AddEmployRec(ExprInterviewBean1 expr) {
		// TODO Auto-generated method stub
		Boolean flag=false;
		Connection con=null;
		
		try{
			con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			String str="insert into Employment(Exprid,OrgName,FromDate,ToDate,StartPos,LeavPos,LeavReas,SalCTC,JobDes)VALUES"
					+ "("
					+ "( SELECT TOP(1) Exprid FROM ExprPersonalInfo ORDER BY 1 DESC)"
					+",'"
					+ expr.getOrgName()
					+ "','"
					+ expr.getFromDate()
					+ "','"
					+ expr.getToDate()
					+ "','"
					+ expr.getStartPos()
					+ "','"
					+ expr.getLeavPos()
					+ "','"
					+ expr.getLeavReas()
					+ "',"
					+ expr.getSalCTC()
					+ ",'"
					+ expr.getJobDes()
					+ "')";
			
			System.out.println(str);
			st.execute(str);
			
			flag=true;
			con.close();
		}
		catch (Exception e) {
				e.printStackTrace();
			}
			return flag;																			
	}

	public boolean AddFamilyDetails(ExprInterviewBean1 expr){
		boolean flag=false;
		Connection con=null;
		Statement st=null;
		
		try{
			con=ConnectionManager.getConnection();
			st=con.createStatement();
			String sql="insert into ExprFamily(Relation,RName,RAge,ROccupation,exprid) values"
			+ "('"
			+expr.getRelation()
			+"','"
			+expr.getRelName()
			+"',"
			+expr.getAge()
			+",'"
			+expr.getOccupation()
			+"',"
			+ "( SELECT TOP(1) Exprid FROM ExprPersonalInfo ORDER BY 1 DESC))";
			
			System.out.println("relation sql :"+sql);
			flag=st.execute(sql);
	
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return flag;
	}
	public boolean AddReference(ExprInterviewBean1 expr)
	{
		boolean flag=false;
		Connection con=null;
		Statement st=null,st2=null;
		
		try{
			con=ConnectionManager.getConnection();
			st=con.createStatement();
			st2=con.createStatement();
			
			//first reference
			String sql1="insert into ExprReference(RefName,refDesig,refOrganization,refMobile,refMail,exprid) values"
			+ "('"
			+expr.getRefName()
			+"','"
			+expr.getRefDesig()
			+"','"
			+expr.getRefOrganization()
			+"',"
			+expr.getRefMobile()
			+",'"
			+expr.getRefMail()
			+"',"
			+ "( SELECT TOP(1) Exprid FROM ExprPersonalInfo ORDER BY 1 DESC))";
			
			System.out.println("reference sql 1 :"+sql1);
			//for second reference
			String sql2="insert into ExprReference(RefName,refDesig,refOrganization,refMobile,refMail,exprid) values"
					+ "('"
					+expr.getRefName2()
					+"','"
					+expr.getRefDesig2()
					+"','"
					+expr.getRefOrganization2()
					+"',"
					+expr.getRefMobile2() 
					+",'"
					+expr.getRefMail2()
					+"'," 
					+ "( SELECT TOP(1) Exprid FROM ExprPersonalInfo ORDER BY 1 DESC))";
					
					System.out.println("reference sql 2 :"+sql2);
					
					
					
		st.execute(sql1);
		flag=st2.execute(sql2);
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return flag;
	}
	public boolean AddQualifications(ExprInterviewBean1 expr) {
		// TODO Auto-generated method stub
		boolean flag=false;
		Connection con=null;
		Statement st=null;
		
		try{
			con=ConnectionManager.getConnection();
			st=con.createStatement();
			String str="insert into ExprEducation(Qualification,Degree,Collage,Specialization,YearOfPass,Std,Marks,Exprid) VALUES"
					+ "('"
					+ expr.getQuali()
			        + "','"
			        + expr.getDegree()
			        + "','"
			        + expr.getCollage()
			        + "','"
			        + expr.getSpecial()
			        + "','"
			        + expr.getYear()
			        + "','"
			        + expr.getStd()
			        + "',"
			        + expr.getMarks()
			        + ","
					+ "( SELECT TOP(1) Exprid FROM ExprPersonalInfo ORDER BY 1 DESC)"
					+ ")";
			
			System.out.println(str);
			st.execute(str);
			flag=true;
			con.close();
			
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
		
	}
	public static int DeletePersonal(int exprid) {
		System.out.println(exprid);
		int flag=0;
		Statement st=null;
		try{
			Connection con=ConnectionManager.getConnection();
			st=con.createStatement();
			
			/*st.executeQuery("update AddProject set PCONDITION='Inactive' where ProjectId="+pid);*/
			
			st.executeQuery("delete from ExprPersonalInfo where Exprid="+exprid);
				
			flag=1;
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	public static int DeleteEmploy(int employid) {
		System.out.println(employid);
		int flag=0;
		Statement st=null;
		try{
			Connection con=ConnectionManager.getConnection();
			st=con.createStatement();
			
			/*st.executeQuery("update AddProject set PCONDITION='Inactive' where ProjectId="+pid);*/
			
			st.executeQuery("delete from Employment where EmployId="+employid);
				
			flag=1;
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	public ArrayList<ExprInterviewBean1> PersonalRec()
	{
		ArrayList<ExprInterviewBean1> result = new ArrayList<ExprInterviewBean1>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet  rs1=st.executeQuery("select Top(1) Exprid from ExprPersonalInfo order by 1 DESC");
			rs1.next();
			int id=rs1.getInt("Exprid");
			int p=id+1;
			System.out.println("dddd "+p);
			
			
			
			ResultSet  rs=st.executeQuery("select  * from ExprPersonalInfo where Exprid="+id);
			//select  * from ExprPersonalInfo where Exprid="+id		
			System.out.println(rs+"sssssss");
			 while(rs.next())
			 {
				 ExprInterviewBean1 ebean =new ExprInterviewBean1();	
				 ebean.setExprid(rs.getString("Exprid")!=null?rs.getInt("Exprid"):0);
				 ebean.setPName(rs.getString("PName")!=null?rs.getString("PName"):"");
				 ebean.setDOB(rs.getString("DOB")!=null?rs.getString("DOB"):"");
				 ebean.setEmail(rs.getString("Email")!=null?rs.getString("Email"):"");
				 ebean.setMobileNo(rs.getString("MobileNo")!=null?rs.getInt("MobileNo"):0);
				 result.add(ebean);
			 }
			
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
		
	}
	public ArrayList<ExprInterviewBean1> EmployRec()
	{
		ArrayList<ExprInterviewBean1> result = new ArrayList<ExprInterviewBean1>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			/*
			 * ResultSet rs1=st.
			 * executeQuery("select Top(1) Exprid from ExprPersonalInfo order by 1 DESC");
			 * rs1.next(); int id=rs1.getInt("Exprid"); int p=id+1;
			 * System.out.println("dddd "+p);
			 */
			
			
			ResultSet  rs=st.executeQuery("select * from Employment where Exprid=(SELECT TOP(1) Exprid FROM ExprEducation ORDER BY 1 DESC)");
			//select  * from ExprPersonalInfo where Exprid="+id		
			System.out.println(rs+"sssssss");
			 while(rs.next())
			 {
				 ExprInterviewBean1 ebean =new ExprInterviewBean1();	
				 ebean.setEmployId(rs.getString("EmployId")!=null?rs.getInt("EmployId"):0);
				 ebean.setOrgName(rs.getString("OrgName")!=null?rs.getString("OrgName"):"");
				 ebean.setFromDate(rs.getString("FromDate")!=null?rs.getString("FromDate"):"");
				 ebean.setToDate(rs.getString("ToDate")!=null?rs.getString("ToDate"):"");
				 ebean.setStartPos(rs.getString("StartPos")!=null?rs.getString("StartPos"):"");
				 ebean.setLeavPos(rs.getString("LeavPos")!=null?rs.getString("LeavPos"):"");
				 ebean.setSalCTC(rs.getString("SalCTC")!=null?rs.getInt("SalCTC"):0);
				 result.add(ebean);
			 }
			
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
		
	}
	public static boolean UpdatePersonal(ExprInterviewBean1 ebean,int id)  //For Edit 
	{
			Connection con = ConnectionManager.getConnection();
			//con1 = ConnectionManager.getConnection();
			//ResultSet rs,rs1;
			Boolean flag = false;
			PreparedStatement ps = null;
			//Statement st=null;
			String PASSDT;
			if(ebean.getPassportValid()== null) 
			{
				PASSDT = null;
			}else
			{
				PASSDT = "'"+ebean.getPassportValid()+"'";
			}
			try
			{
				//PName,DOB,Address1,Address2,Email,MobileNo,MaritalStatus,PassportNo,PassportValid
				String sql="update ExprPersonalInfo set PName='" + ebean.getPName()
				+ "',DOB='" + ebean.getDOB()
				+ "',Address1='"+ ebean.getAddress1() 
				+ "',Address2='"+ ebean.getAddress2() 
				+ "',Email='" + ebean.getEmail()
				+ "',MobileNo=" + ebean.getMobileNo()
				+ ",MaritalStatus='"+ ebean.getMaritalStatus() 
				+ "',PassportNo='"+ ebean.getPassportNo()
				+ "',PassportValid="+ PASSDT
				+ ",CurrentDate='"+ ebean.getCrrDate()
				+ "',Hobbies='"+ ebean.getHobbies()
				+"'where Exprid="+id;
				
				ps=con.prepareStatement(sql);
				ps.executeUpdate();
				ps.close();
				
				flag=true;
 
				System.out.println("second"+flag);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		return flag;		
	}
	public static boolean UpdateEmploy(ExprInterviewBean1 ebean,int id)  //For Edit 
	{
			Connection con = ConnectionManager.getConnection();
			//con1 = ConnectionManager.getConnection();
			//ResultSet rs,rs1;
			Boolean flag = false;
			PreparedStatement ps = null;
			//Statement st=null;
			
			try
			{
				//PName,DOB,Address1,Address2,Email,MobileNo,MaritalStatus,PassportNo,PassportValid
				String sql="update Employment set OrgName='" + ebean.getOrgName()
				+ "',FromDate='" + ebean.getFromDate()
				+ "',ToDate='"+ ebean.getToDate() 
				+ "',StartPos='"+ ebean.getStartPos() 
				+ "',LeavPos='" + ebean.getLeavPos()
				+ "',LeavReas='" + ebean.getLeavReas()
				+ "',SalCTC="+ ebean.getSalCTC()
				+ ",JobDes='"+ ebean.getJobDes()
				+"'where EmployId="+id;
				
				ps=con.prepareStatement(sql);
				ps.executeUpdate();
				ps.close();
				
				flag=true;
 
				System.out.println("second"+flag);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		return flag;		
	}
	
	public ArrayList<ExprInterviewBean1> EditPersonal( int exprid)
	{
		ArrayList<ExprInterviewBean1> result = new ArrayList<ExprInterviewBean1>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			
			
			
			
			ResultSet  rs=st.executeQuery("select * from ExprPersonalInfo where Exprid="+exprid );		//select  * from ExprPersonalInfo where Exprid="+id		 
			 while(rs.next())
			 {
				 ExprInterviewBean1 ebean =new ExprInterviewBean1();	
				 ebean.setExprid(rs.getString("Exprid")!=null?rs.getInt("Exprid"):0);
				 ebean.setPName(rs.getString("PName")!=null?rs.getString("PName"):"");
				 ebean.setDOB(rs.getString("DOB")!=null?rs.getString("DOB"):"");
				 ebean.setEmail(rs.getString("Email")!=null?rs.getString("Email"):"");
				 ebean.setMobileNo(rs.getString("MobileNo")!=null?rs.getInt("MobileNo"):0);
				 ebean.setMaritalStatus(rs.getString("MaritalStatus")!=null?rs.getString("MaritalStatus"):"");
				 ebean.setPassportNo(rs.getString("PassportNo")!=null?rs.getString("PassportNo"):"");
				 ebean.setPassportValid(rs.getString("PassportValid")!=null?rs.getString("PassportValid"):"");
				 ebean.setCrrDate(rs.getString("CurrentDate")!=null?rs.getString("CurrentDate"):"");
				 ebean.setHobbies(rs.getString("Hobbies")!=null?rs.getString("Hobbies"):"");
				 ebean.setAddress1(rs.getString("Address1")!=null?rs.getString("Address1"):"");
				 ebean.setAddress2(rs.getString("Address2")!=null?rs.getString("Address2"):"");
				 result.add(ebean);
			 }
			
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
		
	}
	public ArrayList<ExprInterviewBean1> EditEmploy( int employid)
	{
		ArrayList<ExprInterviewBean1> result = new ArrayList<ExprInterviewBean1>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			
			
			
			
			ResultSet  rs=st.executeQuery("select * from Employment where EmployId="+employid );		//select  * from ExprPersonalInfo where Exprid="+id		 
			 while(rs.next())
			 {
				 ExprInterviewBean1 ebean =new ExprInterviewBean1();	
				 ebean.setExprid(rs.getString("Exprid")!=null?rs.getInt("Exprid"):0);
				 ebean.setOrgName(rs.getString("OrgName")!=null?rs.getString("OrgName"):"");
				 ebean.setFromDate(rs.getString("FromDate")!=null?rs.getString("FromDate"):"");
				 ebean.setToDate(rs.getString("ToDate")!=null?rs.getString("ToDate"):"");
				 ebean.setStartPos(rs.getString("StartPos")!=null?rs.getString("StartPos"):"");
				 ebean.setLeavPos(rs.getString("LeavPos")!=null?rs.getString("LeavPos"):"");
				 ebean.setLeavReas(rs.getString("LeavReas")!=null?rs.getString("LeavReas"):"");
				 ebean.setSalCTC(rs.getString("SalCTC")!=null?rs.getFloat("SalCTC"):0);
				 ebean.setJobDes(rs.getString("JobDes")!=null?rs.getString("JobDes"):"");
				 result.add(ebean);
			 }
			
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
		
	}
	public ArrayList<ExprInterviewBean1> getQualificationDetail()
	{
		ArrayList<ExprInterviewBean1> result = new ArrayList<ExprInterviewBean1>();
		
		Connection con=null;
		Statement st=null;
		
		try{
			con=ConnectionManager.getConnection();
			st=con.createStatement();
			String str="select * from ExprEducation where Exprid=(SELECT TOP(1) Exprid FROM ExprEducation ORDER BY 1 DESC)";
			ResultSet rs=st.executeQuery(str);
			while(rs.next()){
				ExprInterviewBean1 e1=new ExprInterviewBean1();
				e1.setEid((int)(rs.getInt(1)!=0?rs.getInt(1):""));
				e1.setQuali(rs.getString(2)!=null?rs.getString(2):"");
				e1.setDegree(rs.getString(3)!=null?rs.getString(3):"");
				e1.setCollage(rs.getString(4)!=null?rs.getString(4):"");
				e1.setSpecial(rs.getString(5)!=null?rs.getString(5):"");
				e1.setYear(rs.getString(6)!=null?rs.getString(6):"");
				e1.setStd(rs.getString(7)!=null?rs.getString(7):"");
				e1.setMarks((float) (rs.getFloat(8)!=0.0?rs.getFloat(8):""));
				
			
				result.add(e1);
			}
			
			con.close();
			
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
public static int DeleteEducation(int tt) {
		
		System.out.println("tt in dao "+tt);
		int flag=0;
		PreparedStatement st=null;
		
		try{
			Connection con=ConnectionManager.getConnection();
			
			String sql="delete from ExprEducation where Eid="+tt;
			st=con.prepareStatement(sql);
			
			System.out.println(sql);
			st.execute();
			flag=1;
			con.close();		
	}catch (Exception e) {
		e.printStackTrace();
    }
		return flag;	
	}
	public ArrayList<ExprInterviewBean1> EditEducation(int eid)
	{
		ArrayList<ExprInterviewBean1> result = new ArrayList<ExprInterviewBean1>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select * from ExprEducation where Eid="+eid);
			while(rs.next())
			{
				
			
				ExprInterviewBean1 ebin =new ExprInterviewBean1();
				ebin.setEid((int) (rs.getInt(1)!=0?rs.getInt(1):""));
				ebin.setQuali(rs.getString(2)!=null?rs.getString(2):"");
				ebin.setDegree(rs.getString(3)!=null?rs.getString(3):"");
				ebin.setCollage(rs.getString(4)!=null?rs.getString(4):"");
				ebin.setSpecial(rs.getString(5)!=null?rs.getString(5):"");
				ebin.setYear(rs.getString(6)!=null?rs.getString(6):"");
				ebin.setStd(rs.getString(7)!=null?rs.getString(7):"");
				ebin.setMarks((float) (rs.getFloat(8)!=0?rs.getFloat(8):""));
				
				result.add(ebin);
			}
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
		
	}

	
	public ArrayList<ExprInterviewBean1> getEducationEditDetail(int eid)
	{
		ArrayList<ExprInterviewBean1> result = new ArrayList<ExprInterviewBean1>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select * from ExprEducation where Eid="+eid);
			while(rs.next())
			{
				
			
				ExprInterviewBean1 ebin =new ExprInterviewBean1();
				ebin.setEid((int) (rs.getInt(1)!=0?rs.getInt(1):""));
				ebin.setQuali(rs.getString(2)!=null?rs.getString(2):"");
				ebin.setDegree(rs.getString(3)!=null?rs.getString(3):"");
				ebin.setCollage(rs.getString(4)!=null?rs.getString(4):"");
				ebin.setSpecial(rs.getString(5)!=null?rs.getString(5):"");
				ebin.setYear(rs.getString(6)!=null?rs.getString(6):"");
				ebin.setStd(rs.getString(7)!=null?rs.getString(7):"");
				ebin.setMarks((float) (rs.getFloat(8)!=0?rs.getFloat(8):""));
				
				result.add(ebin);
			}
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
		
	}

	public static boolean AddAppraisal(ExprInterviewBean6 b6) {
		System.out.println(b6.getPresentCTC()+"getPresentCTC");
		boolean flag=false;
		Connection con=null;
		Statement st=null;
		
		try{
			con=ConnectionManager.getConnection();
			st=con.createStatement();
			//Aid,Name,Age,Quali,Experience,PresentCTC,ExpectedCTC,Position,Score,InterviewerComments,
			//InterviewerName,InterviewDate,Result,Grade,ApruSalaryMonthly,ApruCTC,PM_Name,HR_Name,MD_Name
			String sql="insert into InterviewAppraisal VALUES"
					+ "('"
					+ b6.getAName()
					+ "','"
					+ b6.getAge()
					+ "','"
					+ b6.getQuali()
					+ "','"
					+ b6.getExperience()
					+ "','"
					+ b6.getPresentCTC()
					+ "','"
					+ b6.getExpectedCTC()
					+ "','"
					+ b6.getPosition()
					+ "','"
					+ b6.getScore()
					+ "','"
					+ b6.getInterviewerComments()
					+ "','"
					+ b6.getInterviewerName()
					+ "','"
					+ b6.getInterviewDate()
					+ "','"
					+ b6.getResult()
					+ "','"
					+ b6.getGrade()
					+ "','"
					+ b6.getApruSalaryMonthly()
					+ "','"
					+ b6.getApruCTC()
					+ "','"
					+ b6.getPM_Name()
					+ "','"
					+ b6.getHR_Name()
					+ "','"
					+ b6.getMD_Name()
					+ "','"
					+ b6.getPM_Date()
					+ "','"
					+ b6.getHR_Date()
					+ "','"
					+ b6.getMD_Date()
					+ "',"
					+ "( SELECT TOP(1) Exprid FROM ExprPersonalInfo ORDER BY 1 DESC)"
					+ ")";
					
			
			
			System.out.println("sql");
			st.execute(sql);
			flag=true;
			con.close();
			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return flag;
		
	}
	public int UpdateQualifications(ExprInterviewBean1 expr, int tt) {
		// TODO Auto-generated method stub
		System.out.println("tt in dao"+tt);
		int flag=0;
		ResultSet rs = null;
		Statement st=null;
		try{
			
			
			Connection con=ConnectionManager.getConnection();                       
			st=con.createStatement();
			
			st.executeUpdate("update ExprEducation set Qualification='"+expr.getQuali()
			        + "',Degree='" + expr.getDegree()
			        + "',Collage='"+ expr.getCollage()
			        + "',Specialization='"+ expr.getSpecial()
			        + "',YearOfPass='"+ expr.getYear()
			        + "',Std='"+ expr.getStd()
			        + "',Marks='"+ expr.getMarks() +"'"
		            + "where Eid="+tt);
			
			
			System.out.println("update ExprEducation set Qualification='"+expr.getQuali()
	        + "',Degree='" + expr.getDegree()
	        + "',Collage='"+ expr.getCollage()
	        + "',Specialization='"+ expr.getSpecial()
	        + "',YearOfPass='"+ expr.getYear()
	        + "',Std='"+ expr.getStd()
	        + "',Marks='"+ expr.getMarks() +"'"
            + "where Eid="+tt);
			
					 flag = 1;
					    con.close();
				}
		catch (Exception e) {
					e.printStackTrace();
			}		
		return flag;
	}
	
}
