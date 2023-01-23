package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import payroll.Model.EmpExperBean;

public class EmpExperHandler {
	Connection con=null;
	Statement st=null;
	ResultSet rs=null;
	public void insertExper(EmpExperBean addexperbean)
	{
		int srno=0;
		try
		{
			con = ConnectionManager.getConnection();
			st = con.createStatement();
			rs=st.executeQuery("select max(SRNO) from EXPERIENCE where EMPNO="+addexperbean.getEMPNO()+"");
			while(rs.next())
			{
				srno=rs.getInt(1);
			}
			srno=srno+1;
			//st.execute("insert into EXPERIENCE values("+addexperbean.getEMPNO()+","+srno+",'"+addexperbean.getORGNAME()+"',CAST('"+addexperbean.getFROMDT()+"' AS DATE),CAST('"+addexperbean.getTODT()+"' AS DATE),'"+addexperbean.getPOST()+"','"+addexperbean.getSALARY()+"')");
			st.execute("insert into EXPERIENCE(EMPNO,SRNO,ORGNAME,FROMDT,TODT,SALARY,POST)values("+addexperbean.getEMPNO()+","+srno+",'"+addexperbean.getORGNAME()+"',CAST('"+addexperbean.getFROMDT()+"' AS DATE),CAST('"+addexperbean.getTODT()+"' AS DATE),"+addexperbean.getSALARY()+",'"+addexperbean.getPOST()+"')");
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void updateEmpExp(EmpExperBean empExpbean)
	{
		Statement st = null;
		try
		{
			con = ConnectionManager.getConnection();
			st = con.createStatement();
			st.executeUpdate("update EXPERIENCE set ORGNAME='"+empExpbean.getORGNAME()+"', FROMDT ='"+empExpbean.getFROMDT()+"',TODT='"+empExpbean.getTODT()+"',POST='"+empExpbean.getPOST()+"', SALARY="+empExpbean.getSALARY()+"  where SRNO="+empExpbean.getSRNO()+" and EMPNO="+empExpbean.getEMPNO()+"");
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public ArrayList<EmpExperBean> getEmpExper(String EMPNO)
	{
		ResultSet rs=null;
		Statement st = null;
		ArrayList<EmpExperBean> empexperlist = new ArrayList<EmpExperBean>();
		try
		{
			con = ConnectionManager.getConnection();
			st = con.createStatement();
			rs=st.executeQuery("select * from EXPERIENCE where empno='"+EMPNO+"'");
			EmpExperBean empexperbean;
			while(rs.next())
			{
				empexperbean= new EmpExperBean();
				empexperbean.setSRNO(Integer.parseInt(rs.getString("SRNO")==null?"":rs.getString("SRNO")));
				empexperbean.setORGNAME(rs.getString("ORGNAME")==null?"--":rs.getString("ORGNAME"));
				empexperbean.setPOST(rs.getString("POST")==null?"--":rs.getString("POST"));
				empexperbean.setFROMDT(dateFormat(rs.getDate("FROMDT")));
				empexperbean.setTODT(dateFormat( rs.getDate("TODT")));
				//empexperbean.setSALARY(Integer.parseInt(rs.getString("SALARY")==null?"":rs.getString("SALARY")));
				empexperbean.setSALARY(rs.getString("SALARY")==null?"":rs.getString("SALARY"));
				/*
				empexperbean.setSRNO(rs.getInt(1));
				empexperbean.setORGNAME(rs.getString(2));
				empexperbean.setPOST(rs.getString(3));
				empexperbean.setFROMDT(rs.getString(4));
				empexperbean.setTODT(rs.getString(5));
				empexperbean.setSALARY(rs.getInt(6));
				*/
				empexperlist.add(empexperbean);
			}
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return empexperlist;
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