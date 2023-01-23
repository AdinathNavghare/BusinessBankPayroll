package payroll.DAO;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import payroll.Core.ReportDAO;
import payroll.Model.AttendanceBean;

import com.ibm.icu.util.StringTokenizer;

public class AttendanceHandler 
{

	public boolean check_in(int userid, String hname)//hostname...........
	{
		boolean flag = false;
		try
		{
			Connection conn = ConnectionManager.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs =null;
			ResultSet rs1 =null;
			ResultSet rs2 =null;
			Date dt1 = new Date();
			int time_min = 0;
			String delim =":";
			int tm = 0;
			int th = 0;
			int empno = 0;
			SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
			Calendar cal = Calendar.getInstance();
			
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			String check_in = sdf.format(cal.getTime());//Time in hr:min.........
			String chDate = format.format(dt1);//date........
			 
			StringTokenizer stn = new StringTokenizer(check_in, delim);
			th = Integer.parseInt(stn.nextToken());//Hr........
			while(stn.hasMoreTokens())
			{
				 tm = Integer.parseInt(stn.nextToken());//Min..........
			}
			time_min = th * 60 + tm;
			String maxAttid = "(select count(*)+1 from ATTENDANCE)";
			rs = st.executeQuery("select EMPID from USRMAST where userid="+userid);
			
			while(rs.next())
			{
				empno = rs.getInt("EMPID");
			}
			String sql ="select * from ATTENDANCE where userid="+userid+" and ATTD_DATE ='"+chDate+"' ";
			rs2 = st.executeQuery(sql);
			int i =0;
			if(!(rs2.next()))
			{
				String insert = "insert into  ATTENDANCE values("+maxAttid+","+empno+",'"+check_in+"','-','"+chDate+"','"+hname+"','-','-',"+userid+",'-')";
				i = st.executeUpdate(insert);
				if(i!=0)
				{
					flag = true;
				}	
			}
			conn.close();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}
	public String getcheckinTime(int userid)
	{
		Connection conn;
		String intime = "";
		try
		{
			Date dt1 = new Date();
			conn = ConnectionManager.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs=null;
			SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
			String chDate = format.format(dt1);//date........
			String time = "select CHECK_IN from ATTENDANCE where USERID="+userid+" and ATTD_DATE='"+chDate+"' ";
			rs = st.executeQuery(time);
			if(rs.next())
			{
				intime = rs.getString("CHECK_IN");
			}
			else
			{
				intime ="NA";//NO checkin data in table
			}
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return intime;
	}
	
	public AttendanceBean check_out(AttendanceBean upattBean)
	{
		boolean flag = false;
		Connection conn = ConnectionManager.getConnection();;
		AttendanceBean getattBean = new AttendanceBean();
		Date dt1 = new Date();
		ResultSet rslt=null;
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
		String chDate = format.format(dt1);//date........
		try
		{
			Statement st = conn.createStatement();
			String updatsql ="update ATTENDANCE set CHECK_OUT='"+upattBean.getCHECK_OUT()+"',EARLY_REASON ='"+upattBean.getEARLY_REASON()+"', TOTAL_TIME='"+upattBean.getTOTAL_TIME()+"' where USERID="+upattBean.getUSERID()+" and ATTD_DATE='"+chDate+"'";
			int i = st.executeUpdate(updatsql);
			if(i!=0)
			{
				rslt = st.executeQuery("select * from ATTENDANCE where USERID="+upattBean.getUSERID()+" and ATTD_DATE='"+chDate+"'");
				while(rslt.next())
				{
					getattBean.setATTD_ID(rslt.getString(1)==null?0:rslt.getInt(1));
					getattBean.setEMPNO(rslt.getString(2)==null?0:rslt.getInt(2));
					getattBean.setCHECK_IN(rslt.getString(3)==null?"":rslt.getString(3));
					getattBean.setCHECK_OUT(rslt.getString(4)==null?"":rslt.getString(4));
					getattBean.setATTD_DATE(rslt.getString(5)==null?"":rslt.getString(5));
					getattBean.setPC_NAME(rslt.getString(6)==null?"":rslt.getString(6));
					getattBean.setLATE_REASON(rslt.getString(7)==null?"":rslt.getString(7));
					getattBean.setEARLY_REASON(rslt.getString(8)==null?"":rslt.getString(8));
					getattBean.setUSERID(rslt.getString(9)==null?0:rslt.getInt(9));
				}
			}
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return getattBean;
	}
	
	public ArrayList<AttendanceBean> getAttendanceList(int userid,String frmdate,String todate)
	{
		ArrayList<AttendanceBean> list =new ArrayList<AttendanceBean>();
		try 
		{
	    	 Connection conn=ConnectionManager.getConnection();
	    	 String attenlist="select * from ATTENDANCE WHERE USERID='"+userid+"' and ATTD_DATE between '"+frmdate+"'AND '"+todate+"'";
	    	 Statement stmt=conn.createStatement();
			 ResultSet rslt=stmt.executeQuery(attenlist);
			 while(rslt.next())
			 {
				 AttendanceBean Abean=new AttendanceBean();	
				 Abean.setATTD_ID(rslt.getString(1)==null?0:rslt.getInt(1));
				 Abean.setEMPNO(rslt.getString(2)==null?0:rslt.getInt(2));
				 Abean.setCHECK_IN(rslt.getString(3)==null?"":rslt.getString(3));
				 Abean.setCHECK_OUT(rslt.getString(4)==null?"-":rslt.getString(4));
				 Abean.setATTD_DATE(LeaveMasterHandler.dateFormat(rslt.getDate(5)));
				 Abean.setPC_NAME(rslt.getString(6)==null?"":rslt.getString(6));
				 Abean.setLATE_REASON(rslt.getString(7)==null?"":rslt.getString(7));
				 Abean.setEARLY_REASON(rslt.getString(8)==null?"":rslt.getString(8));
				 Abean.setUSERID(rslt.getString(9)==null?0:rslt.getInt(9));
				 Abean.setTOTAL_TIME(rslt.getString(10)==null?"":rslt.getString(10));
				 list.add(Abean);
			 }
			 conn.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	   return list; 
	}
	
	public ArrayList<AttendanceBean> getEmpAttendanceList(int userid,String frmdate,String todate)
	{
		ArrayList<AttendanceBean> list =new ArrayList<AttendanceBean>();
	   try
	   {
	    	 Connection conn=ConnectionManager.getConnection();
	    	 String attenlist="select a.empno,a.UserID,count(distinct(a.attd_date)) as diff,datediff(day,cast('"+frmdate+"' as date), cast('"+todate+"' as date))+1 as total,e.fname,e.lname from attendance a inner join empmast e on e.empno = a.empno WHERE  ATTD_DATE between '"+frmdate+"'AND '"+todate+"' group by a.empno,a.USERID,e.fname,e.lname";
	    	 Statement stmt=conn.createStatement();
	    	 ResultSet rslt=stmt.executeQuery(attenlist);
			 AttendanceBean Abean;
			 while(rslt.next())
			 {
				 Abean=new AttendanceBean();	
				 Abean.setEMPNO(rslt.getString(1)==null?0:rslt.getInt(1));
				 Abean.setUSERID(rslt.getString(2)==null?0:rslt.getInt(2));
				 Abean.setTOTAL_DAYS(rslt.getString(3)==null?"":rslt.getString(3));
				 Abean.setDIFF(rslt.getString(4)==null?"":rslt.getString(4));
				 Abean.setEMPNAME(rslt.getString(5)+" "+rslt.getString(6));
				 list.add(Abean);
			 }
			 conn.close();
	    } 
	   	catch (SQLException e) 
	   	{
		   e.printStackTrace();
		}
	   return list; 
	}
	
	
public int checkAttendance(String emplist,String act,String date) {
		
		try
		{
			
		
		Connection con = ConnectionManager.getConnection();
		Statement st = con.createStatement();
		ResultSet rs =null;
		String sql = "";
			/* date = "1-"+date; */
		if(act.equalsIgnoreCase("allEmp"))
		{
			date = "1-"+date;
				/*
				 * sql =
				 * "select * from employee_attendance where empcode in( (select empcode from empmast where empno in ("
				 * +emplist+")) ) and attend_date='"+date+"'" ;
				 */
			
			
			sql = "select * from employee_attendance where empcode in( (select empcode from empmast where empno in ("+emplist+")) ) and attend_date between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"'" ;
			
			
		}
		else if(act.equalsIgnoreCase("singleEmp"))
		{
				/*
				 * sql = "select * from employee_attendance where empcode='"
				 * +emplist+"' and attend_date='"+date+"'" ;
				 */
			
			sql = "select * from employee_attendance where empcode='"+emplist+"' and attend_date ='"+date+"'" ;
		}
		 
		System.out.println(" check sql  : "+sql);
		rs = st.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		System.out.println("columnCount :"+columnCount);
		
		int k = 1;
		
		
		while(rs.next())
		{
			for(int i = 0;i<columnCount;i++)
			{
					/* System.out.println(" data : "+rs.getString(rsmd.getColumnName(k))); */
				if((rs.getString(rsmd.getColumnName(k)).equalsIgnoreCase(" ")) || (rs.getString(rsmd.getColumnName(k)).equalsIgnoreCase("")) || (rs.getString(rsmd.getColumnName(k)).equalsIgnoreCase("NULL")) )
				{
					//System.out.println(" data : "+rs.getString(rsmd.getColumnName(k)));
					System.out.println("Empty data found...");
					return 2;
				}
				k++;
			}
		}
		return 1;
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}
}