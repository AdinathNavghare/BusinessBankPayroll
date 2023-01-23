package payroll.Core;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import payroll.DAO.ConnectionManager;


public  class Programability
{

	SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
	
	public static boolean update_paytran_history(String usercode,String change_from)
	{
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = null;
		st.execute("UPDATE PAYTRAN_HISTORY SET " +
				"BATCH_NO=(select(case when (SELECT MAX(BATCH_NO) FROM PAYTRAN_HISTORY) IS NULL	" +
				"then  1 else MAX(BATCH_NO)+1 end) from paytran_history)," +
				"USER_UPDDT=GETDATE() ," +
				"CHANGE_BY='"+usercode+"'," +
				"CHANGE_FROM='"+change_from+"'" +
				"WHERE BATCH_NO IS NULL");
		rs.close();
		st.close();
		con.close();
		}
		catch(Exception e){
			e.printStackTrace();
			return false;	
		}
		return true;
		
	}
	
	
	
}
