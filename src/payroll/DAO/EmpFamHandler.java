package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import payroll.Model.EmpFamilyBean;
import payroll.Model.Lookup;

public class EmpFamHandler
{
	Connection conn;
	LookupHandler lkhp=new LookupHandler();
	Lookup lookupBean=new Lookup() ;
	
	public ArrayList<EmpFamilyBean> getEmpFamily(String EMPNO)
	{
		ResultSet rs=null;
		Statement st = null;
		ArrayList<EmpFamilyBean> empfamilylist = new ArrayList<EmpFamilyBean>();
		try
		{
			conn = ConnectionManager.getConnection();
			st = conn.createStatement();
			rs=st.executeQuery("select * from FAMILY where empno='"+EMPNO+"'");
			EmpFamilyBean empfamilybean;
			while(rs.next())
			{
				empfamilybean=new EmpFamilyBean();
				empfamilybean.setNAME(rs.getString("NAME")==null?"--":rs.getString("NAME"));
				empfamilybean.setRELATION(rs.getString("RELATION")==null?0:rs.getInt("RELATION"));
				empfamilybean.setSRNO(Integer.parseInt(rs.getString("SRNO")==null?"":rs.getString("SRNO")));
				empfamilybean.setGENDER(rs.getString("GENDER")==null?"--":rs.getString("GENDER"));
				empfamilybean.setDOB(rs.getString("DOB")==null?"--":dateFormat(rs.getDate("DOB")));
				
				lookupBean=lkhp.getLookup("ED-"+rs.getString("QUALI"));
				
				empfamilybean.setQUALI(rs.getString("QUALI")==null?0:rs.getInt("QUALI"));
				empfamilybean.setDEPENDYN(rs.getString("DEPENDYN")==null?"--":rs.getString("DEPENDYN"));
				empfamilybean.setOCCUPATION(rs.getString("OCCUPATION")==null?"--":rs.getString("OCCUPATION"));
				empfamilylist.add(empfamilybean);
			}
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return empfamilylist;
	}
	
	
	public void updateEmpFamily(EmpFamilyBean empFamilybean)
	{
		Statement st = null;
		try
		{
			conn = ConnectionManager.getConnection();
			st = conn.createStatement();
			System.out.println("update FAMILY set RELATION='"+empFamilybean.getRELATION()+"',NAME='"+empFamilybean.getNAME()+"',GENDER='"+empFamilybean.getGENDER()+"',QUALI='"+empFamilybean.getQUALI()+"',DOB='"+empFamilybean.getDOB()+"',DEPENDYN='"+empFamilybean.getDEPENDYN()+"',OCCUPATION='"+empFamilybean.getOCCUPATION()+"' where EMPNO="+empFamilybean.getEMPNO()+" and SRNO="+empFamilybean.getSRNO()+"");
			st.executeUpdate("update FAMILY set RELATION='"+empFamilybean.getRELATION()+"',NAME='"+empFamilybean.getNAME()+"',GENDER='"+empFamilybean.getGENDER()+"',QUALI='"+empFamilybean.getQUALI()+"',DOB='"+empFamilybean.getDOB()+"',DEPENDYN='"+empFamilybean.getDEPENDYN()+"',OCCUPATION='"+empFamilybean.getOCCUPATION()+"' where EMPNO="+empFamilybean.getEMPNO()+" and SRNO="+empFamilybean.getSRNO()+"");
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void insertFamily(EmpFamilyBean addfamilybean)
	{
		Statement st=null;
		ResultSet rs=null;
		int srno=0;
		try
		{
			conn = ConnectionManager.getConnection();
			st = conn.createStatement();
			rs=st.executeQuery("select max(SRNO) from FAMILY where EMPNO="+addfamilybean.getEMPNO()+"");
			while(rs.next())
			{
				srno=rs.getInt(1);
			}
			//"+addfamilybean.getDOB()+",
			
			srno=srno+1;
			String str = "insert into FAMILY values("+addfamilybean.getEMPNO()+","+srno+","+addfamilybean.getRELATION()+",'"+addfamilybean.getNAME()+"','"+addfamilybean.getGENDER()
					+"',CAST('"+addfamilybean.getDOB()+"' AS DATE),"+addfamilybean.getQUALI()+",'"+addfamilybean.getDEPENDYN()+"','"+addfamilybean.getOCCUPATION()+"')"; 
			System.out.println(str);
			st.execute(str);
			
		
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	public static String dateFormat(Date date)
	{
		String result="";
		if(date==null)
		{
			return "";
		}
		SimpleDateFormat format=new SimpleDateFormat("dd-MMM-yyyy");
		result=format.format(date);
		return result;
	}
}
