package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import payroll.Model.UserRoleBean;

public class UserRoleHandler 
{
	public boolean addUserRole(UserRoleBean uRole) 
	{
		boolean flag = false;
		Connection con = null;
		try
		{
			con = ConnectionManager.getConnection();
			Statement st = con .createStatement();
			String maxURID = "(SELECT MAX( USER_ROLE_ID)+1 FROM USERROLES)";
			String sql = "INSERT INTO USERROLES VALUES( "
						+maxURID+", "
						+uRole.getUSERID()+", "
						+uRole.getROLEID()+", '"
						+uRole.getSTATUS()+"', "
						+"'"+uRole.getLASTMOD_DATE()+"', "
						+uRole.getASSIGNED_RU_ID()
						+")";
			st.execute(sql);
			flag = true;
			con.close();
		}
		catch(Exception e)
		{
			flag = false;
			try 
			{
				con.close();
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return flag;
	}
	
	public UserRoleBean getUserRole(int UID)
	{
		UserRoleBean result = new UserRoleBean();
		Connection con = null;
		try
		{
			con = ConnectionManager.getConnection();
			Statement st = con .createStatement();
			String sql = "SELECT * FROM USERROLES WHERE USERID = "+UID;
			ResultSet rs = st.executeQuery(sql);
			if(rs.next())
			{
				result.setUSER_ROLE_ID(rs.getString("USER_ROLE_ID")!=null?rs.getInt("USER_ROLE_ID"):0);
				result.setUSERID(rs.getString("USERID")!=null?rs.getInt("USERID"):0);
				result.setASSIGNED_RU_ID(rs.getString("ASIGNED_RU_ID")!=null?rs.getInt("ASIGNED_RU_ID"):0);
				result.setROLEID(rs.getString("ROLEID")!=null?rs.getInt("ROLEID"):0);
				result.setSTATUS(rs.getString("STATUS")!=null?rs.getString("STATUS"):"");
				result.setLASTMOD_DATE(rs.getString("LASTMOD_DATE")!=null?rs.getString("LASTMOD_DATE"):"");
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
			e.printStackTrace();
		}
		return result;
	}
}
