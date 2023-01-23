package payroll.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import payroll.Core.Calculate;
import payroll.Core.ReportDAO;
import payroll.Core.Utility;
import payroll.Model.BranchBean;
import payroll.Model.EmpOffBean;
import payroll.Model.HolidayMasterBean;
import payroll.Model.ShiftBean;


public class HolidayMasterHandler {
	
	

	Connection conn;
	public boolean  addholdmast(HolidayMasterBean hb)
	{
		conn= ConnectionManager.getConnection();
		boolean flag= false; 
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
		 String text="";
		Date date1=new Date(hb.getFromdate());
		Date date2=new Date(hb.getTodate());
	
		if(hb.getBranch().equals("All")){
			while(date1.before(date2)||date1.equals(date2))
			{
				
			String frm=sdf.format(date1);
			try
			{
				conn= ConnectionManager.getConnection();
				Statement st = conn.createStatement();
				String s = hb.getText();
				  if(s.contains("'")){
				   
				     text= s.replace("'","''");
				  }
				  else 
					  text=s;
				  ArrayList<EmpOffBean> list= new ArrayList<EmpOffBean>();
					EmpOffHandler ofh = new EmpOffHandler();
					list=ofh.getprojectCode();
					for(EmpOffBean lkb :list)
					{
				st.execute("insert into HOLDMAST(HOLDNAME,FDATE,TDATE,BRANCH,HTEXT,HTYPE,RepeatHold,HDAY," +
						"HATTENDENCES,HSMS,OptionalHold)" +
						" values('"+hb.getHoldname()+"','"+frm+"' ,'"+frm+"' ,'"+lkb.getPrj_srno()+"','"+text+"','"+hb.getType()+"'," +
						"'"+hb.getRepeathold()+"','"+hb.getDay()+"','"+hb.getAttend()+"','"+hb.getSms()+"','"+hb.getOptionalhold()+"')");
					}
				flag = true;
				conn.close();
			}	
			
			
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			date1=  new Date(date1.getTime()+(24*60*60*1000));
		System.out.println("incremented date	:	"+date1);
			
			}
		}
		
		else {
			while(date1.before(date2)||date1.equals(date2))
		
		{
			
		String frm=sdf.format(date1);
		
		//System.out.println("frm date====="+frm);
		
		try
		{
			conn= ConnectionManager.getConnection();
			Statement st = conn.createStatement();
			System.out.println("in addholdmast method");
			System.out.println("value of text"+hb.getText());
			 String s = hb.getText();
			  if(s.contains("'")){
			   
			     text= s.replace("'","''");
			  }
			  else 
				  text=s;
			st.execute("insert into HOLDMAST(HOLDNAME,FDATE,TDATE,BRANCH,HTEXT,HTYPE,RepeatHold,HDAY,HATTENDENCES,HSMS,OptionalHold) values('"+hb.getHoldname()+"','"+frm+"' ,'"+frm+"' ,'"+hb.getBranch()+"','"+text+"','"+hb.getType()+"','"+hb.getRepeathold()+"','"+hb.getDay()+"','"+hb.getAttend()+"','"+hb.getSms()+"','"+hb.getOptionalhold()+"')");
			flag = true;
			conn.close();
		}	
		
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		date1=  new Date(date1.getTime()+(24*60*60*1000));
	System.out.println("incremented date	:	"+date1);
		
		}
		}
		return flag;

	}

	public ArrayList<HolidayMasterBean> getHoldList(String frmdate, String todate, String branch) 
	
	{
		ArrayList<HolidayMasterBean> list=new ArrayList<HolidayMasterBean>();
		
		Connection conn=ConnectionManager.getConnection();
		
		String search="";
		
		 if(branch.equalsIgnoreCase("All")){
			 search="SELECT * FROM HOLDMAST  WHERE FDATE between '"+frmdate+"'AND '"+todate+"' order by FDATE";
		 }
		 else{
	
			 search="SELECT * FROM HOLDMAST  WHERE BRANCH='"+branch+"' AND FDATE between '"+frmdate+"'AND '"+todate+"' order by FDATE" ;
		 }
		 
	
		try
		{
			Statement st=conn.createStatement();
			
			ResultSet rslt=st.executeQuery(search);
			while(rslt.next())
			{
				HolidayMasterBean hb=new HolidayMasterBean();
				hb.setHoldname(rslt.getString(1)!=null?rslt.getString(1):"");
				
				hb.setFromdate(rslt.getString(2)!=null?Utility.dateFormat(rslt.getDate(2)):"");
				hb.setTodate(rslt.getString(3)!=null?Utility.dateFormat(rslt.getDate(3)):"");
				hb.setBranch(rslt.getString(4)!=null?rslt.getString(4):"");
				hb.setText(rslt.getString(5)!=null?rslt.getString(5):"");
				hb.setType(rslt.getString(6)!=null?rslt.getString(6):"");
				hb.setRepeathold(rslt.getString(7)!=null?rslt.getString(7):"");
				hb.setDay(rslt.getString(8)!=null?rslt.getString(8):"");
				hb.setAttend(rslt.getString(9)!=null?rslt.getString(9):"");
				hb.setSms(rslt.getString(10)!=null?rslt.getString(10):"");
				hb.setOptionalhold(rslt.getString(11)!=null?rslt.getString(11):"");
				hb.setHolino(rslt.getInt(12));
				list.add(hb);
			}
			st.close();
			conn.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return list;
		
	}
	
	
	public ArrayList<String> getHoldmast(String date,int prjCode)//for getting string directly specially  for grid view
	{
		int count=0;
		ArrayList<String> arrayList= new ArrayList<String>();
		Connection con=ConnectionManager.getConnection();
	
		SimpleDateFormat format= new SimpleDateFormat("dd-MMM-yyyy");   
		try
		{	
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT Distinct fdate from HOLDMAST WHERE branch='"+prjCode+"'  and fdate between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date) +"'");
			System.out.println("SELECT Distinct fdate from HOLDMAST WHERE branch='"+prjCode+"'  and fdate between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date) +"'");
			while(rs.next())
			
			{ 	
				HolidayMasterBean bean=new HolidayMasterBean();
				
				date=format.format(rs.getDate(1));
				String dates=date.toString();
		
				bean.setFromdate(rs.getDate(1)!=null?Utility.dateFormat(rs.getDate(1)):"");
			
				arrayList.add(date);
				
			}
			
			st.close();
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return arrayList;
	}
	public boolean deleteHoliday(int id )
	{
		Connection con=null;
		boolean result=false;
		System.out.println("the name of the branch is"+id);
		try
		{
			con=ConnectionManager.getConnection();
			String sql = "DELETE FROM HOLDMAST WHERE id = '"+id+"'";
			PreparedStatement st=con.prepareStatement(sql);

			st.executeUpdate();
			
			result=true;
			
			con.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return result;
		
	}
		
	public ArrayList<String> getweekoff(String date)//for getting string directly specially  for grid view
	{
		int count=0;
		ArrayList<String> arrayList= new ArrayList<String>();
		Connection con=ConnectionManager.getConnection();
		System.out.println("one parameter method");
		SimpleDateFormat format= new SimpleDateFormat("dd-MMM-yyyy");   
		try
		{	
			Statement st=con.createStatement();
			System.out.println("DATE in get get week off"+date);
			ResultSet rs=st.executeQuery("SELECT Distinct fdate from HOLDMAST WHERE fdate between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date) +"'" +
					"AND BRANCH= 'ALL'  AND HTYPE='Weekly Off'" );
			while(rs.next())
			
			{ 	
				HolidayMasterBean bean=new HolidayMasterBean();
				
				date=format.format(rs.getDate(1));
				//String type=rs.getType(2);
				String dates=date.toString();
				
			
		
				bean.setFromdate(rs.getDate(1)!=null?Utility.dateFormat(rs.getDate(1)):"");
							arrayList.add(date);
			}
			
			st.close();
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return arrayList;
	}	
	public ArrayList<String> getweekoff(String date, int prjcode )//for getting string directly specially  for grid view
	{
		int count=0;
		ArrayList<String> arrayList= new ArrayList<String>();
		Connection con=ConnectionManager.getConnection();
	System.out.println("two parameter method");
		SimpleDateFormat format= new SimpleDateFormat("dd-MMM-yyyy");   
		try
		{	
			Statement st=con.createStatement();
			System.out.println("SELECT Distinct fdate from HOLDMAST WHERE fdate between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date) +"' " +
					" AND BRANCH= '"+ prjcode+"' AND HTYPE='Weekly Off'");
			/*ResultSet rs=st.executeQuery("SELECT Distinct fdate from HOLDMAST WHERE fdate between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date) +"' " +
					" AND BRANCH= '"+ prjcode+"' AND HTYPE='Weekly Off'" );*/
			ResultSet rs=st.executeQuery("SELECT Distinct fdate from HOLDMAST WHERE fdate between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date) +"' " +
					"  AND HTYPE='Weekly Off'" );
			while(rs.next())
			
			{ 	
				HolidayMasterBean bean=new HolidayMasterBean();
				
				date=format.format(rs.getDate(1));
				//String type=rs.getType(2);
				String dates=date.toString();
				
			
		
				bean.setFromdate(rs.getDate(1)!=null?Utility.dateFormat(rs.getDate(1)):"");
							arrayList.add(date);
			}
			
			st.close();
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return arrayList;
	}
	
	public ArrayList<String> getholiday(String date) {
		int count=0;
		ArrayList<String> arrayList= new ArrayList<String>();
		Connection con=ConnectionManager.getConnection();
		System.out.println("one parameter method");
		SimpleDateFormat format= new SimpleDateFormat("dd-MMM-yyyy");   
		try
		{	
			Statement st=con.createStatement();
			System.out.println("DATE in get get week off"+date);
			String hold = "SELECT Distinct fdate from HOLDMAST WHERE fdate between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date) +"'"
					+ " AND HTYPE<>'Weekly Off'" ;
			System.out.println(" hold:"+hold);
			ResultSet rs=st.executeQuery(hold );
			 
			while(rs.next())
			
			{ 	
				HolidayMasterBean bean=new HolidayMasterBean();
				
				date=format.format(rs.getDate(1));
				String dates=date.toString();
				bean.setFromdate(rs.getDate(1)!=null?Utility.dateFormat(rs.getDate(1)):"");
							arrayList.add(date);
			}
			
			st.close();
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return arrayList;
	}
	

	
}
