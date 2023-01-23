package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import payroll.Core.Utility;
import payroll.Model.VpayBean;

public class VpayHandler
{
	public boolean addVpay(VpayBean VP)
	{
		boolean flag = false;
		String maxsr = "(SELECT NVL(MAX(SRNO),0)+1 FROM VPAYDECLARE)";
		try
		{
			Connection cn = ConnectionManager.getConnection();
			Statement st = cn.createStatement();
			st.executeUpdate("INSERT INTO VPAYDECLARE VALUES("
							  +maxsr+","
							  +VP.getEMPNO()+"," 
							  +"'"+VP.getDECL_DATE()+"',"
							  +VP.getTRNCD()+","
							  +VP.getAMT_DECLARE()+","
							  +VP.getAMT_CLAIMED()+""
							  +")");
			flag = true;
			cn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}
	
	public ArrayList<VpayBean> getVpay(int empno)
	{
		ArrayList<VpayBean> result = new ArrayList<VpayBean>();
		try
		{
			Connection cn = ConnectionManager.getConnection();
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM VPAYDECLARE WHERE EMPNO = "+empno);
			VpayBean VP =null;
			while(rs.next())
			{
				VP = new VpayBean();
				VP.setSRNO(rs.getString("SRNO")==null?0:rs.getInt("SRNO"));
				VP.setEMPNO(rs.getString("EMPNO")==null?0:rs.getInt("EMPNO"));
				VP.setTRNCD(rs.getString("trncd")==null?0:rs.getInt("TRNCD"));
				VP.setDECL_DATE(rs.getString("DECL_DATE")==null?"":Utility.dateFormat(rs.getDate("DECL_DATE")));
				VP.setAMT_DECLARE(rs.getString("AMT_DECLARE")==null?0:rs.getInt("AMT_DECLARE"));
				VP.setAMT_CLAIMED(rs.getString("AMT_CLAIMED")==null?0:rs.getInt("AMT_CLAIMED"));
				result.add(VP);
			}
			cn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean updateVpay(int srno,int amt)
	{
		boolean flag = false;
		try
		{
			Connection cn = ConnectionManager.getConnection();
			Statement st = cn.createStatement();
			st.executeUpdate("UPDATE VPAYDECLARE SET AMT_DECLARE="+amt+" WHERE SRNO="+srno);
			flag = true;
			cn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}
	
}