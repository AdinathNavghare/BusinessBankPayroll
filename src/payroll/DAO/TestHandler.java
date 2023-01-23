package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import payroll.Model.TranBean;
import payroll.Model.testBean;

public class TestHandler {

	public ArrayList<testBean> getAllLeave(String empno)
	{
		ArrayList<testBean> trlist = new ArrayList<testBean>();
		Connection con= ConnectionManager.getConnection();
		System.out.println("inside get all leave");
		testBean lb;
		try
		{
			Statement st = con.createStatement();
			Statement st1 = con.createStatement();
			Statement st2 = con.createStatement();
			Statement st3 = con.createStatement();
			ResultSet rs= null;
			ResultSet rs2= null;
			ResultSet rs1= null;
			String query="Select * from leavetran order by empno,leavecd,FRMDT";
			System.out.println(query);
			rs= st.executeQuery(query);
			while(rs.next())
			{
				lb = new testBean();
				lb.setBaldt(rs.getString("trndate")!=null?rs.getString("trndate"):"");
				lb.setEmpno(rs.getString("empno")==null?0:rs.getInt("empno"));
				lb.setLeavecd(rs.getString("leavecd")==null?0:rs.getInt("leavecd"));
				lb.setTotdr(rs.getFloat("Days"));
				
				rs1=st1.executeQuery("select * from leavebal where empno="+rs.getString("empno")+" and leavecd="+rs.getString("leavecd")+
						" and baldt=(select max(baldt) from leavebal where empno="+rs.getString("empno")+" and leavecd="+rs.getString("leavecd")+") order by baldt");
				
				System.out.println("----------select * from leavebal where empno="+rs.getString("empno")+" and leavecd="+rs.getString("leavecd")+
						" and baldt=(select max(baldt) from leavebal where empno="+rs.getString("empno")+" and leavecd="+rs.getString("leavecd")+") order by baldt");
				if(rs1.next())
				{
					
					
					System.out.println("BAL DATE======"+rs.getString("frmdt"));
					st2.execute("insert into leavebal (BALDT,EMPNO,LEAVECD,BAL,TOTCR,TOTDR) " +
								"values('"+rs.getString("frmdt")+"',"+rs.getString("empno")+"," +
								""+rs.getString("leavecd")+","+(rs1.getInt("BAL")- rs.getFloat("Days"))+","+rs1.getInt("TOTCR")+","+rs.getFloat("Days")+")");
					
				}
				
				trlist.add(lb);
			}
			con.close();
		}
		catch(Exception e)
		{
			try
			{
				con.close();
			} 
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		
		
		
		return trlist;
	}
	
	
	public  boolean insertLeave(String EMPNO){
		
		 ArrayList<testBean> LeaveList = new ArrayList<testBean>();
		    TestHandler th= new TestHandler();
		    LeaveList=th.getAllLeave(EMPNO);
		    System.out.println("inside insert leave");
		    
		    for(testBean tb : LeaveList)
		    {
		    	
		    	System.out.println("bal date       "+tb.getBaldt());
		    	System.out.println("leave code  "+tb.getLeavecd());
		    	System.out.println("empno       "+tb.getEmpno());
		    	System.out.println("used         "+tb.getTotdr());
		    	
		    	
		    	
		    }
		    
		    
		    
		    
		
		
		return true;
		
		
		
		
	}

	

}
