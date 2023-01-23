package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import payroll.Model.Overtimebean;

public class OvertimeHandler
{
	public boolean addOvertime(Overtimebean overtime)
	{
		boolean flag =false;
		Connection con=ConnectionManager.getConnection();
		String sql="INSERT INTO OVERTIMEMTN VALUES('"+overtime.getEMPNO()+"','"+overtime.getOtdate()+"','"+overtime.getShiftcode()+"','"+overtime.getFromtime()+"','"+overtime.getTotime()+"','"+overtime.getHOURS()+"')";
		try
		{
			Statement st=con.createStatement();
			st.executeUpdate(sql);
			flag=true;
			con.close();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	public ArrayList<Overtimebean> getovertimeList(int empno)
	{
		Connection con=ConnectionManager.getConnection();
		ArrayList<Overtimebean> list=new ArrayList<>();
		String query="select * from overtimemtn where empno='"+empno+"' order by otdate desc";
		String query2="select * from overtimemtn order by otdate desc";
		int EMPNO=empno;
		try
		{
			Statement st=con.createStatement();
			ResultSet rs=null;
			if(EMPNO!=0)
			{	
				rs=st.executeQuery(query);
			}
			else
			{
				rs=st.executeQuery(query2);
			}
			while(rs.next())
			{
				Overtimebean obj=new Overtimebean();
				obj.setEMPNO(rs.getString(1)!=null?rs.getInt(1):0);
				obj.setOtdate(rs.getString(2)!=null?LeaveMasterHandler.dateFormat(rs.getDate(2)):"");
				obj.setShiftcode(rs.getString(3)!=null?rs.getString(3):"");
				obj.setFromtime(rs.getString(4)!=null?rs.getString(4):"");
				obj.setTotime(rs.getString(5)!=null?rs.getString(5):"");
				obj.setHOURS(rs.getString(6)!=null?rs.getFloat(6):0);
				list.add(obj);
			}
			con.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return list;
	}

	public boolean deleteOTrecord(Overtimebean otbean) 
	{
		 boolean flag=false;
		 Connection con=ConnectionManager.getConnection();
		 String delete="delete from overtimemtn where empno='"+otbean.getEMPNO()+"' and otdate='"+otbean.getOtdate()+"' and shiftcode='"+otbean.getShiftcode()+"'";
		 try
		 {
			Statement st=con.createStatement();
		    st.executeUpdate(delete);
			flag=true;
			con.close();
		 }
		 catch (SQLException e) 
		 {
			e.printStackTrace();
		 }
		return flag;
	}
}