package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import payroll.Model.UserMasterBean;

public class UserMasterHandler 
{
	public boolean addMaster(UserMasterBean umb) 
	{
		boolean result=false;
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			String addquery="INSERT INTO USERMAST VALUES ('"+umb.getUsercode()+"','"+umb.getUsername()+"','"+umb.getUserpwd()+"','"+umb.getStatus()+"' )";
            st.executeUpdate(addquery);
            result =true;
            con.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return result;
	}

	public static ArrayList<UserMasterBean> display() 
	{
		ArrayList<UserMasterBean> alist= new ArrayList<UserMasterBean>();
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=null;
			String query=" SELECT * FROM USERMAST ";
			rs= st.executeQuery(query);
            while(rs.next())
            {
            	UserMasterBean ubean = new UserMasterBean();
        	    ubean.setUsercode(rs.getString("usercode")!=null?rs.getString("usercode"):"");
        	    ubean.setUsername(rs.getString("username")!=null?rs.getString("username"):"");
            	ubean.setUserpwd(rs.getString("userpwd")!=null?rs.getString("userpwd"):"");
            	ubean.setStatus(rs.getString("status")!=null?rs.getInt("status"):0);
            	alist.add(ubean);
            }
            con.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return alist;
	}

	public boolean updateMaster(UserMasterBean umb1) 
	{
		boolean result=false;
		Connection con=ConnectionManager.getConnection();
		String queryUpdate=" UPDATE USERMAST SET userpwd='"+umb1.getUserpwd()+"' WHERE usercode='"+umb1.getUsercode()+"'";
		try
		{
			Statement st=con.createStatement();
			st.executeUpdate(queryUpdate);
			result=true;
			con.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return result;
	}

	public int updatePassword(String uname, String pass, String newPass, String empid)
	{
		int flag = 0;
		Connection con=ConnectionManager.getConnection();
		try
		{
			Statement st=con.createStatement();
			Statement stUP=con.createStatement();
			ResultSet rs = st.executeQuery("Select * from USRMAST where UNAME='"+uname+"' and UPWD='"+pass+"'");
			if(rs.next())
			{
				stUP.executeUpdate("UPDATE USRMAST SET UPWD='"+newPass+"' WHERE UNAME='"+uname+"' AND EMPID="+empid+"");
				flag = 1;	//Success
			}
			else
			{
				flag = 2;	// Wrong Current Password
			}
			con.close();
		} 
		catch (SQLException e) 
		{
			flag = 3;	//Error
			e.printStackTrace();
		}
		return flag;
	}
	
	public static UserMasterBean login(UserMasterBean logbean) 
	{
		Connection con=ConnectionManager.getConnection();
		String uname=logbean.getUsername();
		String passw=logbean.getUserpwd();
		String querylog="SELECT * FROM USERMAST WHERE USERNAME='"+ uname +"' AND USERPWD='"+ passw +"'";
		ResultSet rs=null;
		try
		{
			Statement st=con.createStatement();
			rs=st.executeQuery (querylog);
			boolean userExists = rs.next();
			if (!userExists) 
			{
				logbean.setIsValid(false);
			}
			else if (userExists) 
			{
				String username = rs.getString("username")!=null?rs.getString("username"):"";
				String usercode = rs.getString("usercode")!=null?rs.getString("usercode"):"";
				String pass=rs.getString("userpwd")!=null?rs.getString("userpwd"):"";
				int status=rs.getString("status")!=null?rs.getInt("status"):0;
				logbean.setUsername(username);
				logbean.setUsercode(usercode);
				logbean.setUserpwd(pass);
				logbean.setStatus(status);	
				logbean.setIsValid(true);
			}
			con.close();
		}
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
		return logbean ;
	}
}