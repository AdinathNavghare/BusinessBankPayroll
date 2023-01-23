package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import payroll.Model.EmployeeBean;
import payroll.Model.RoleBean;


public class RoleDAO 
{
	public ArrayList<RoleBean> getAllRole()
	{
		Connection con = null;
		ArrayList<RoleBean> roleList = new ArrayList<RoleBean>();
		try
		{
			con = ConnectionManager.getConnection();
			Statement st= con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM ROLES");
			while(rs.next())
			{
				RoleBean roleBean = new RoleBean();
				roleBean.setROLEID(rs.getString("ROLEID")!=null?rs.getInt("ROLEID"):0);
			    roleBean.setROLENAME(rs.getString("ROLENAME")!=null?rs.getString("ROLENAME"):"");
				roleBean.setROLEDESC(rs.getString("ROLEDESC")!=null?rs.getString("ROLEDESC"):"");
				roleList.add(roleBean);
			}
			con.close();
		} 
		catch (Exception e)
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
		return roleList;
	}
	
	public boolean addRole(String name,String desc)
	{
		Connection con = null;
		int count = 0;
		boolean flag = false;
		try
		{
			con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
		    String sql = "INSERT INTO ROLES VALUES((SELECT MAX(ROLEID)+1 FROM ROLES),'"+name+"','"+desc+"')";
		    count = st.executeUpdate(sql);
		   	if(count == 1)
			{
				flag = true;
			}
			con.close();
		}
		catch (Exception e) 
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
		return flag;	
	}

	public boolean addUserRole(RoleBean rolebean) 
	{
		 boolean flag=false;
		 
		 int  USER_ID=1;
		 try
		 {
			Connection con=ConnectionManager.getConnection();
			Connection conTech=ConnectionManager.getConnectionTech();
		    Statement st=con.createStatement();
		    Statement stTech=conTech.createStatement();
			ResultSet rs=stTech.executeQuery("SELECT USER_ID FROM USERS WHERE EMP_ID="+rolebean.getUSERID());
		    rs.next();
		    USER_ID=rs.getInt(1);
		   // String userRole="INSERT INTO USERROLES VALUES((select max(USER_ROLE_ID) from USERROLES),'"+USER_ID+"','"+rolebean.getROLEID()+"','"+rolebean.getSTATUS()+"','"+rolebean.getLASTMOD_DATE()+"','"+rolebean.getASSIGNED_RU_ID()+"')";
		    String userRole="INSERT INTO USERROLES VALUES((select max(USER_ROLE_ID)+1 from USERROLES),'"+USER_ID+"','"+rolebean.getROLEID()+"','"+rolebean.getSTATUS()+"','"+rolebean.getLASTMOD_DATE()+"','"+rolebean.getASSIGNED_RU_ID()+"')";
		    st.executeUpdate(userRole);
			flag=true;
			con.close();
		 }
		 catch (SQLException e)
		 {
			e.printStackTrace();
		 }
		return flag;
	}

	public int getRollid(String name)
	{
		String getrollid="select USER_ID from Users where USER_NAME='"+name+"'";
		//String getrollid="select * from USRMAST  where USERID not in (select USERID from USERROLES)"; 
		 int roleid=0;
		 try 
		 {
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs= st.executeQuery(getrollid);
			while(rs.next())
			{
				roleid=rs.getString(1)!=null?rs.getInt(1):0;
			}
			con.close();
		 } 
		 catch (SQLException e) 
		 {
			e.printStackTrace();
		 }
		 return roleid;
	}

	public ArrayList<RoleBean> getrolelist() 
	{
		 String list="SELECT * FROM USERROLES";
		//String list ="SELECT USERID FROM USERROLES WHERE USERID != USERID";
		 
		 //SELECT * FROM Users
		ArrayList<RoleBean> Alist=new ArrayList<RoleBean>();
		 try
		 {
			 EmployeeHandler eh=new EmployeeHandler();
			Connection con=ConnectionManager.getConnection();
			Connection con1=ConnectionManager.getConnectionTech();
			Statement st=con.createStatement();
			ResultSet result=st.executeQuery(list);
			Statement st1=con1.createStatement();
			ResultSet result1=null;
			
			
			while(result.next())
			{
				RoleBean Rbean=new RoleBean();
				Rbean.setUSER_ROLE_ID(result.getString(1)!=null?Integer.parseInt(result.getString(1)):0);
				Rbean.setUSERID(result.getString(2)!=null?Integer.parseInt(result.getString(2)):0);
				Rbean.setROLEID(result.getString(3)!=null?Integer.parseInt(result.getString(3)):0);
				Rbean.setSTATUS(result.getString(4)!=null?result.getString(4):"");
				Rbean.setLASTMOD_DATE(result.getString(5)!=null?dateFormat(result.getDate(5)):"");
				Rbean.setASSIGNED_RU_ID(result.getString(6)!=null?Integer.parseInt(result.getString(6)):0);
				
				
				result1=st1.executeQuery("SELECT * FROM Users where user_id="+result.getString(2)+" ");
				if(result1.next())
				{
				Rbean.setUser_name(result1.getString("user_firstname") +" "+result1.getString("user_lastname"));
					
				}
				else
				{
					Rbean.setUser_name("  ");	
				}
				
				Alist.add(Rbean);
			}
			con.close();
			con1.close();
		 }
		 catch (SQLException e) 
		 {
			e.printStackTrace();
		}
		return Alist;
	}
	
	public static String dateFormat(Date dates)
	{
		String result="";
		if(dates==null)
		{
			result="";
			return result;
		}
		SimpleDateFormat format= new SimpleDateFormat("dd-MMM-yyyy");
		result=format.format(dates);
		return result;
	}
	
	public String getroleName(int i)
	{
		Connection conn=null; 
		String rname="";
		String list="SELECT * FROM ROLES where ROLEID='"+i+"'";
		try
		{
			 conn=ConnectionManager.getConnection();
			 Statement st=conn.createStatement();
			 ResultSet result=st.executeQuery(list);
			 while(result.next())
			 {
				 rname= result.getString("ROLENAME")!=null?result.getString("ROLENAME"):"";
			 }
			 conn.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return rname;
	}

	public boolean updateUserRole(RoleBean rolebean1) 
	{
		boolean flag=false;	
		String update="UPDATE USERROLES SET USERID='"+rolebean1.getUSERID()+"',ROLEID='"+rolebean1.getROLEID()+"',STATUS='"+rolebean1.getSTATUS()+"' WHERE USER_ROLE_ID='"+rolebean1.getUSER_ROLE_ID()+"'" +
					  " UPDATE USERS SET HR_STATUS='"+rolebean1.getSTATUS()+"'  WHERE USER_ID="+rolebean1.getUSERID()+" ";
				      
		System.out.println(update);
		try
		{
			Connection conn=ConnectionManager.getConnection();
			Statement stmt=conn.createStatement();
			stmt.executeUpdate(update);
			flag=true;
			conn.close();	
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return flag;
	}

	public ArrayList<RoleBean> getroleMenu(int roleid) 
	{
		ArrayList<RoleBean> menulst=new ArrayList<>();
		String querymenu="SELECT DISTINCT MENUID FROM rolemenu WHERE ROLEID="+roleid+"";
		try
		{
			Connection conn=ConnectionManager.getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rts=stmt.executeQuery(querymenu);
			ResultSet rts1=null;
			int menuid;
			while(rts.next())
			{
				Statement stmt1=conn.createStatement();
				menuid=rts.getString(1)!=null?rts.getInt(1):0;
				rts1=stmt1.executeQuery("select * from menu where MENUID="+menuid+" ORDER BY MENUID");
				while(rts1.next())
				{
					RoleBean bean=new RoleBean();
					bean.setMENUID(rts1.getString(1)!=null?rts1.getInt(1):0);
					bean.setMENU_NAME(rts1.getString(2)!=null?rts1.getString(2):"");
					menulst.add(bean);
				}
			}
			conn.close();	
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return menulst;
	}

	public ArrayList<RoleBean> getMenulist() 
	{
		ArrayList<RoleBean> menulst2=new ArrayList<>();
		String menulist="select * from MENU order by MENUID";
		ResultSet rts1=null;
		try
		{
			Connection conn=ConnectionManager.getConnection();
			Statement stmt3=conn.createStatement();
			rts1=stmt3.executeQuery(menulist);
			while(rts1.next())
			{
				RoleBean Menubean=new RoleBean();
				Menubean.setMENUID(rts1.getString(1)!=null?rts1.getInt(1):0);
				Menubean.setMENU_NAME(rts1.getString(2)!=null?rts1.getString(2):"");
				menulst2.add(Menubean);
			}
			conn.close();		  
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return menulst2;
	}

	public boolean assignMenu(String[] Menu, int roleid) 
	{
		boolean flag=false;
		String Status="Active";
		try
		{
			Connection conn=ConnectionManager.getConnection();
			Statement stmts=conn.createStatement();
			String maxrmid="(select max(RMID)+1 from ROLEMENU)";
			for(int i=0; i<Menu.length; i++)
			{
				String sql="INSERT INTO ROLEMENU VALUES("+maxrmid+",'"+roleid+"',"+Integer.parseInt(Menu[i])+",'"+Status+"')";
				stmts.executeUpdate(sql);
			}
			flag=true;
			conn.close();
		}
		catch (SQLException e) 
		{
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean deleteRoleMenus(int roleid)
	{
		boolean flag = false;
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			st.executeUpdate("DELETE FROM ROLEMENU WHERE ROLEID = "+roleid);
			flag = true;
			con.close();
		}
		catch(Exception e)
		{
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	public boolean createRole(String rolename, String desc)
	{
		boolean flag=false;
		int roleid=1;
		String maxroleid="SELECT max(ROLEID) FROM ROLES";
		ResultSet rsmax=null;
		try 
		{
			Connection conn=ConnectionManager.getConnection();
			Statement stmtcr=conn.createStatement();
			rsmax=stmtcr.executeQuery(maxroleid);
			if(rsmax.next())
			{
				roleid=rsmax.getInt(1)+1;
			}
			String rolecreate="INSERT INTO ROLES VALUES('"+roleid+"','"+rolename+"','"+desc+"')";
			stmtcr.executeUpdate(rolecreate);
			flag=true;
			conn.close();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return flag;
	}
	
	
	public ArrayList<RoleBean> getNotAssignedMenuList(int roleid)
	{
		ArrayList<RoleBean> result = new ArrayList<RoleBean>();
		try 
		{
			Connection conn=ConnectionManager.getConnection();
			
			
			
			Statement stmt=conn.createStatement();
			ResultSet rts1 = stmt.executeQuery("select * from menu where menuid not in (select menuid from rolemenu where roleid="+roleid+") order by menuid");
			while(rts1.next())
			{
				RoleBean bean=new RoleBean();
				bean.setMENUID(rts1.getString(1)!=null?rts1.getInt(1):0);
				bean.setMENU_NAME(rts1.getString(2)!=null?rts1.getString(2):"");
				result.add(bean);
			}
			conn.close();	
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}	
		return result;
	}
	public String getrole(int i)
	{
		Connection conn=null; 
		String rname="";
		String list="SELECT ROLEID FROM USERROLES WHERE USERID="+i+"";
		try
		{
			 conn=ConnectionManager.getConnection();
			 Statement st=conn.createStatement();
			 ResultSet result=st.executeQuery(list);
			 while(result.next())
			 {
				 rname= result.getString("ROLEID")!=null?result.getString("ROLEID"):"";
			 }
			 conn.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		System.out.println("****************************"+rname);
		return rname;
	}
	
}