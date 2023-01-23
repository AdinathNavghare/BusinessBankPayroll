package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import payroll.Model.SAL_Details;

public class Sal_DetailsHandler
{
	public boolean addSalDetails(int empno,String month,String EOMonth,String status,Connection con)
	{
		boolean flag =false;
		try
		{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM SAL_DETAILS WHERE EMPNO="+empno+" AND SAL_MONTH='"+month+"'");
			if(!rs.next())
				st.execute("INSERT INTO SAL_DETAILS(EMPNO,SAL_MONTH,SAL_STATUS,SAL_PAID_DATE) VALUES("+empno+",'"+month+"','"+status+"','"+EOMonth+"')");
				//st.execute("INSERT INTO SAL_DETAILS(EMPNO,SAL_MONTH,SAL_STATUS) VALUES("+empno+",'"+month+"','"+status+"')");
			else
			   {
			    st.executeUpdate("UPDATE SAL_DETAILS SET SAL_STATUS='"+status+"' WHERE EMPNO="+empno+" AND SAL_MONTH='"+month+"'");
			   }
			flag = true;
			rs.close();
			st.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean updateSalDetails(String emplist,SAL_Details salData,Connection con)
	{
		boolean flag = false;
		try
		{
			Statement st = con.createStatement();
			String sql = "UPDATE SAL_DETAILS SET ";
			
			sql += (salData.getSAL_STATUS()==null?"":" SAL_STATUS='"+salData.getSAL_STATUS()+"',");
			
			sql += (salData.getSAL_PAID_DATE()==null?"":" SAL_PAID_DATE='"+salData.getSAL_PAID_DATE()+"',");
			
			sql += (salData.getVOC_NUM()==null?"":" VOC_NUM='"+salData.getVOC_NUM()+"',");
			
			sql += (salData.getAUTHORIZED_BY()==null?"":" AUTHORIZED_BY='"+salData.getAUTHORIZED_BY()+"',");
			
			sql += (salData.getFINALIZED_BY()==null?"":" FINALIZED_BY='"+salData.getFINALIZED_BY()+"'");
			
			sql = sql.trim();
			char charat = sql.charAt(sql.length()-1);
			if(charat == ',')
			{
				sql = sql.substring(0, sql.length()-1);
			}
			sql += " WHERE EMPNO IN ("+emplist+") AND SAL_MONTH='"+salData.getSAL_MONTH()+"'";
			st.executeUpdate(sql);
			flag = true;
			st.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}
	
	
	public ArrayList<String> getBonusMonths(int empno,String who)
	{
		ArrayList<String> result = new ArrayList<String>();
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			String sql ="";
			if(who.equalsIgnoreCase("one"))
				sql="SELECT  distinct SUBSTRING (convert(varchar, trndt, 100),1,3) +'-'+ SUBSTRING (convert(varchar, trndt, 100),8,4 ) as months FROM paytran ";
			else if(who.equalsIgnoreCase("all"))
				sql="SELECT  distinct SUBSTRING (convert(varchar, trndt, 100),1,3) +'-'+ SUBSTRING (convert(varchar, trndt, 100),8,4 ) as months FROM paytran  ";
			ResultSet rs = st.executeQuery(sql);
			while(rs.next())
			{
				result.add(rs.getString(1));
			}
			rs.close();
			st.close();
			con.close();
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
		return result;
	}
	
	
}