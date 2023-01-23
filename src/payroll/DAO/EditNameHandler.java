package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import payroll.Model.EditNameBean;
import payroll.Model.Lookup;

public class EditNameHandler {
	Connection conn;
	public Boolean editName(EditNameBean enb){
		boolean flag = false;
		Statement st=null;
		ResultSet rs=null;
		int srno=0;
		LookupHandler lh=new LookupHandler();
		Lookup lkhp = new Lookup();
		lkhp = lh.getLookup("SALUTE-"+enb.getSALUTE());
		String salute=lkhp.getLKP_DESC();
		try
		{
			conn = ConnectionManager.getConnection();
			st = conn.createStatement();
			 rs = st.executeQuery("select max(SRNO) from EDITNAME");
			if(rs.next())
			{
				srno = rs.getInt(1);		
			}
			srno = srno+1;
			Statement stmt = conn.createStatement();
			Statement stemp = conn.createStatement();
			Statement stlkp = conn.createStatement();
			String sql="INSERT INTO EDITNAME VALUES("
						+srno+","
						+enb.getEMPNO()+","
						+"'"+enb.getCHANGEDATE()+"',"
						+"'"+enb.getOLDNAME()+"',"
						+"'"+enb.getFNAME()+"',"
						+"'"+enb.getMNAME()+"',"
						+"'"+enb.getLNAME()+"',"
						+"'"+enb.getUPDATEBY()+"'"
						+")"; 
			String updateempmast = "update EMPMAST set FNAME = '"+enb.getFNAME()+"',LNAME = '"+enb.getLNAME()+"', MNAME = '"+enb.getMNAME()+"' where empno = "+enb.getEMPNO();
			String desc=salute+""+enb.getFNAME()+" "+enb.getMNAME()+" "+enb.getLNAME();
			String updatelookup = "update LOOKUP set LKP_DISC = '"+desc+"' where LKP_CODE = 'ET' and LKP_SRNO = "+enb.getEMPNO();
			System.out.println("inser "+sql);
			System.out.println("update mast "+updateempmast);
			System.out.println("update Lookup "+updatelookup);
			stmt.execute(sql);
			stlkp.executeUpdate(updatelookup);
			stemp.executeUpdate(updateempmast);
			flag = true;
			conn.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return flag;
	}
}
