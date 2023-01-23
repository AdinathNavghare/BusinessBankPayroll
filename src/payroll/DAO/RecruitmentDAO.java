package payroll.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import payroll.Model.RecruitmentBean;

public class RecruitmentDAO {
	static Connection con=null,con1=null;
	static String condition="Active";
	
	public static boolean RecruitDetails(RecruitmentBean Rbean,String s1) {
		
		con = ConnectionManager.getConnection();
		ResultSet rs;
		Boolean flag = false;
		PreparedStatement ps = null;	
		try{
			String str="insert into Recruitment(RecruitDate,ProManName,Desig,Dept,NoOfReq,ReqType,Education ,EmpType ,SalCTCFrom ,SalCTCTo ,BondReq ,DocReq,MinExp ,ProbPeriod ,BondTenure ,Skills ,SpecialRequirement ,RecStatus)VALUES"
					+ "("
					+ "' "
					+ Rbean.getRecruitDate()
					+ "', "
					+ "' "
					+ Rbean.getManagerName()
					+ "' "
					+ " ,'"
					+ Rbean.getDesig()
					+ "' "
					+ " ,'"
					+ Rbean.getDept()
					+ "' "
					+ " ,"
					+ Rbean.getNoOfRequirement()					
					+ " ,'"
					+ Rbean.getReqType()
					+ "' "
					+ " ,'"
					+ Rbean.getEducation()
					+ "' "
					+ " ,'"
					+Rbean.getEmpType()
					+ "',"
					+Rbean.getSalCTCFrom()
					+","
					+Rbean.getSalCTCTo()
					+",'"
					+Rbean.getBondRequire()
					+"','"
					+Rbean.getDocRequire()
					+"',"
					+Rbean.getMinExp()
					+","
					+Rbean.getProbPeriod()
					+","
					+Rbean.getBondTenure()
					+",'"
					+Rbean.getSkills()
					+"','"
					+Rbean.getSpecialReq()
					+"','"
					+ "Active')";
			//System.out.println("Recruitment Table: "+str);
			ps = con.prepareStatement(str);
			ps.executeUpdate();
			System.out.println("Recruitment Table: "+str);
			ps.close();
			
			String str2="select TOP(1) RecId from Recruitment ORDER BY 1 DESC";
			ps = con.prepareStatement(str2);
			rs=ps.executeQuery();
			int RecId = 0;
			while(rs.next())
			{
				 RecId=rs.getInt("RecId");
			}
			System.out.println("llll:"+RecId);
			System.out.println("For RecId "+str2);
			ps.close();
			rs.close();
			
			String[] s=s1.split(",");
			
			String str3="";
			for(int i=0;i<s.length;i++)
			{
				System.out.println("locationsDAO: "+s[i]);
			 str3="insert into JobLocation(RecId,JobLoc) values"
					+ "("
					+ RecId
					+ ",'"
					+Rbean.getJobLocation(s[i])
					+ "')";
			 ps = con.prepareStatement(str3);
				ps.executeUpdate();
			}
			
			
			System.out.println("JobLocation "+str3);
			flag=true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public ArrayList<RecruitmentBean> getRecruitList()
	{
		ArrayList<RecruitmentBean> result = new ArrayList<RecruitmentBean>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select RecId,RecruitDate ,ProManName ,Desig ,ReqType,Education ,SalCTCFrom ,SalCTCTo from Recruitment   where RecStatus ='Active'");
			//ResultSet rs=st.executeQuery("select r.RecId,r.RecruitDate ,r.ProManName ,r.Desig ,r.ReqType,r.Education ,r.SalCTCFrom ,r.SalCTCTo,j.JobLoc from Recruitment r inner join JobLocation j on r.RecId=j.RecId where r.RecStatus ='Active'");
			while(rs.next())
			{
				
				RecruitmentBean Rbean =new RecruitmentBean();
				Rbean.setRecId(rs.getInt("RecId"));
				Rbean.setRecruitDate(rs.getString("RecruitDate")!=null?rs.getString("RecruitDate"):"");
				Rbean.setManagerName(rs.getString("ProManName")!=null?rs.getString("ProManName"):"");
				Rbean.setDesig(rs.getString("Desig")!=null?rs.getString("Desig"):"");
				Rbean.setReqType(rs.getString("ReqType")!=null?rs.getString("ReqType"):"");
				Rbean.setEducation(rs.getString("Education")!=null?rs.getString("Education"):"");
				Rbean.setSalCTCFrom(rs.getInt("SalCTCFrom"));
				Rbean.setSalCTCTo(rs.getInt("SalCTCTo"));
				/*
				 * Rbean.setJobLocation(rs.getString("JobLoc")!=null?rs.getString("JobLoc"):"");
				 */
				
				result.add(Rbean);
			}
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
		
	}
	public static int DeleteRecruit(int rid) {
		System.out.println(rid);
		int flag=0;
		Statement st=null;
		try{
			Connection con=ConnectionManager.getConnection();
			st=con.createStatement();
			
			/*st.executeQuery("update AddProject set PCONDITION='Inactive' where ProjectId="+pid);*/
			
			st.executeQuery("delete from JobLocation where RecId="+rid+"delete from Recruitment where RecId="+rid);
			//"DELETE a.*, b.* FROM Recruitment a LEFT JOIN JobLocation b ON b.RecId = a.RecId WHERE a.RecId ="+rid;
			/*
			 * +"delete from Team1 where ProjectId="+pid+"
			 */			
			flag=1;
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	public ArrayList<RecruitmentBean> RecruitEditJob(int rid)
	{
		ArrayList<RecruitmentBean> result = new ArrayList<RecruitmentBean>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet  rs=st.executeQuery("select LocId,JobLoc from JobLocation  where RecId="+rid);
			//select r.RecId,r.RecruitDate ,r.ProManName ,r.Desig ,r.Dept,r.NoOfReq,r.EmpType,r.ProbPeriod,r.MinExp,r.BondTenure,r.BondReq,r.Skills,r.SpecialRequirement,r.DocReq,r.ReqType,r.Education ,r.SalCTCFrom ,r.SalCTCTo,j.JobLoc from Recruitment r inner join JobLocation j on r.RecId=j.RecId where r.RecStatus ='Active' and r.RecId="+rid
			//RecruitmentBean Rbean =new RecruitmentBean();
						 
			 while(rs.next())
			 {
				 RecruitmentBean Rbean =new RecruitmentBean();	
				 Rbean.setLocId(rs.getString("LocId")!=null?rs.getInt("LocId"):0);
				 Rbean.setJobLocation(rs.getString("JobLoc")!=null?rs.getString("JobLoc"):"");
				 result.add(Rbean);
			 }
			
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
		
	}
	
	
	public ArrayList<RecruitmentBean> RecruitEdit(int rid)
	{
		ArrayList<RecruitmentBean> result = new ArrayList<RecruitmentBean>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select * from Recruitment  where RecStatus ='Active' and RecId="+rid);
			//select r.RecId,r.RecruitDate ,r.ProManName ,r.Desig ,r.Dept,r.NoOfReq,r.EmpType,r.ProbPeriod,r.MinExp,r.BondTenure,r.BondReq,r.Skills,r.SpecialRequirement,r.DocReq,r.ReqType,r.Education ,r.SalCTCFrom ,r.SalCTCTo,j.JobLoc from Recruitment r inner join JobLocation j on r.RecId=j.RecId where r.RecStatus ='Active' and r.RecId="+rid
			
			while(rs.next())
			{
				
				RecruitmentBean Rbean =new RecruitmentBean();
				//Rbean.setPROID((int) (rs.getInt(1)!=0?rs.getInt(1):""));
				Rbean.setRecruitDate(rs.getString("RecruitDate")!=null?rs.getString("RecruitDate"):"");
				Rbean.setManagerName(rs.getString("ProManName")!=null?rs.getString("ProManName"):"");
				//Rbean.setJobLocation(rs.getString("JobLoc")!=null?rs.getString("JobLoc"):"");
				Rbean.setDesig(rs.getString("Desig")!=null?rs.getString("Desig"):"");
				Rbean.setDept(rs.getString("Dept")!=null?rs.getString("Dept"):"");
				Rbean.setNoOfRequirement(rs.getString("NoOfReq")!=null?rs.getInt("NoOfReq"):0);
				Rbean.setReqType(rs.getString("ReqType")!=null?rs.getString("ReqType"):"");
				Rbean.setEducation(rs.getString("Education")!=null?rs.getString("Education"):"");
				Rbean.setEmpType(rs.getString("EmpType")!=null?rs.getString("EmpType"):"");
				Rbean.setSalCTCFrom(rs.getString("SalCTCFrom")!=null?rs.getInt("SalCTCFrom"):0);
				Rbean.setSalCTCTo(rs.getString("SalCTCTo")!=null?rs.getInt("SalCTCTo"):0);
				Rbean.setProbPeriod(rs.getString("ProbPeriod")!=null?rs.getInt("ProbPeriod"):0);
				Rbean.setMinExp(rs.getString("MinExp")!=null?rs.getFloat("MinExp"):0);
				Rbean.setBondTenure(rs.getString("BondTenure")!=null?rs.getFloat("BondTenure"):0);
				Rbean.setBondRequire(rs.getString("BondReq")!=null?rs.getString("BondReq"):"");
				Rbean.setDocRequire(rs.getString("DocReq")!=null?rs.getString("DocReq"):"");
				Rbean.setSkills(rs.getString("Skills")!=null?rs.getString("Skills"):"");
				Rbean.setSpecialReq(rs.getString("SpecialRequirement")!=null?rs.getString("SpecialRequirement"):"");	
			
				result.add(Rbean);
			}
			
			
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
		
	}
	public static boolean RecruitUpdate(RecruitmentBean Rbean,String s1,int id,int Lid)  //For Edit 
	{
			con = ConnectionManager.getConnection();
			con1 = ConnectionManager.getConnection();
			//ResultSet rs,rs1;
			Boolean flag = false;
			PreparedStatement ps = null;
			//Statement st=null;
			
			
			try
			{
				String sql="update Recruitment set RecruitDate='" + Rbean.getRecruitDate()
				+ "',ProManName='" + Rbean.getManagerName()
				+ "',Desig='"+ Rbean.getDesig() 
				+ "',Dept='"+ Rbean.getDept() 
				+ "',NoOfReq=" + Rbean.getNoOfRequirement()
				+ ",ReqType='" + Rbean.getReqType() 
				+ "',Education='"+ Rbean.getEducation() 
				+ "',EmpType='"+ Rbean.getEmpType()
				+ "',SalCTCFrom="+ Rbean.getSalCTCFrom()
				+ ",SalCTCTo="+ Rbean.getSalCTCTo()
				+ ",BondReq='"+ Rbean.getBondRequire()
				+ "',DocReq='"+ Rbean.getDocRequire()
				+ "',MinExp="+ Rbean.getMinExp()
				+ ",ProbPeriod="+ Rbean.getProbPeriod()
				+ ",BondTenure="+ Rbean.getBondTenure()
				+ ",Skills='"+ Rbean.getSkills()
				+ "',SpecialRequirement='"+ Rbean.getSpecialReq()
				+"'where RecId="+id;
				
				ps=con.prepareStatement(sql);
				ps.executeUpdate();
				ps.close();
				
				
				String[] s=s1.split(",");
				
				String str3="";
				for(int i=0;i<s.length;i++)
				{
					System.out.println("locationsDAO: "+Rbean.getJobLocation(s[i]));
					str3="update JobLocation set JobLoc='"+Rbean.getJobLocation(s[1])
				 	   +"'where RecId="+id
				 	  +"and LocId="+Lid;
				 ps = con.prepareStatement(str3);
					ps.executeUpdate();
				}
				
				
				flag=true;
 
				System.out.println("second"+flag);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		return flag;		
	}


}
