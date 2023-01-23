package payroll.DAO;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ArrayList;
import payroll.Model.EmpQualBean;
import payroll.Model.Lookup;

public class EmplQulHandler
{
	Connection conn;
	LookupHandler lkhp=new LookupHandler();
	Lookup lookupBean=new Lookup() ;
	
	public ArrayList<EmpQualBean> getEmpQual(String EMPNO)
	{
		conn=ConnectionManager.getConnection();
		int Degree1=0;
		String Edudesc;
		ResultSet rs=null;
		ResultSet rs1=null;
		Statement st = null;
		ArrayList<EmpQualBean> empqullist = new ArrayList<EmpQualBean>();
		EmpQualBean empbean=null;
		try
		{
			conn = ConnectionManager.getConnection();
			st = conn.createStatement();
			String qual = "select * from Qual where empno='"+EMPNO+"'";
			rs=st.executeQuery(qual);
			while(rs.next())
			{
				empbean =new EmpQualBean();
				Degree1=rs.getInt("DEGREE");
				Edudesc="ED-"+Degree1;
				lookupBean=lkhp.getLookup(Edudesc);
				empbean.setDEGREE(rs.getString("DEGREE")==null?0:rs.getInt("DEGREE"));
				empbean.setSRNO(Integer.parseInt(rs.getString("SRNO")==null?"0":rs.getString("SRNO")));
				empbean.setINST(rs.getString("INST")==null?"- -":rs.getString("INST"));
				empbean.setPASSYEAR(Integer.parseInt(rs.getString("PASSYEAR")==null?"0":rs.getString("PASSYEAR")));
				empbean.setPERCENT(Float.parseFloat(rs.getString("PER")==null?"0.0":rs.getString("PER")));
				empbean.setCLASS(rs.getString("CLASS")==null?"--":rs.getString("CLASS"));
				empbean.setREM(rs.getString("REM")==null?"--":rs.getString("REM"));
				empqullist.add(empbean);
			}
			rs.close();
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return empqullist;
	}
	
	
	public void insertQuali(EmpQualBean empqualbean)
	{
		
		Statement st=null;
		ResultSet rs=null;
		int srno=0;
		try
		{
			conn = ConnectionManager.getConnection();
			st = conn.createStatement();
			rs=st.executeQuery("select max(SRNO) from QUAL where EMPNO="+empqualbean.getEMPNO()+"");
			while(rs.next())
			{
				srno=rs.getInt(1);
			}
			srno=srno+1;
			st.execute("insert into Qual values("+empqualbean.getEMPNO()+","+srno+",'"+empqualbean.getDEGREE()+"','"+empqualbean.getINST()
					+"',"+empqualbean.getPERCENT()+","+empqualbean.getPASSYEAR()+",'"+empqualbean.getCLASS()+"','"+empqualbean.getREM()+"',"+1+")");
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void updateEmpQual(EmpQualBean empQualbean)
	{
		Statement st = null;
		try
		{
			conn = ConnectionManager.getConnection();
			st = conn.createStatement();
			st.executeUpdate("update Qual set DEGREE="+empQualbean.getDEGREE()+",INST='"+empQualbean.getINST()+"',PER="+empQualbean.getPERCENT()+",PASSYEAR="+empQualbean.getPASSYEAR()+",CLASS='"+empQualbean.getCLASS()+"',REM='"+empQualbean.getREM()
				+"' where EMPNO="+empQualbean.getEMPNO()+" and SRNO="+empQualbean.getSRNO()+" ");
			
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}