package payroll.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ADDMENUTODBDAO 
{

	
	
	
	public boolean addMenuinDB()
	{
	
		boolean flag=false;
	
	try {
		Connection con = ConnectionManager.getConnection();
		Statement st1=con.createStatement();
		
		st1.execute("" +
				"" +
				"" +
				"" +
				"");
		
		
		
	
		flag=true;
		con.close();
	} catch (Exception e) {
		e.printStackTrace();
		 
	}
	
	return flag;
	
	}
}
