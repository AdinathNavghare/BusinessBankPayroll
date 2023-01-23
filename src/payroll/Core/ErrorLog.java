package payroll.Core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import payroll.DAO.ConnectionManager;

public class ErrorLog 
{

	
	
	
	
	public  void errorLog(String page,String msg)
	{
		
		try
		{
			Connection con=ConnectionManager.getConnection();
			PreparedStatement  pst=con.prepareStatement("insert into error_logs (page,disc,LOGDATE) values(?,?,((select GETDATE())))");

			pst.setString(1, page);
			pst.setString(2, msg);

			pst.executeUpdate();


		}
		catch(Exception e)
		{
			try
			{
			String routePath = this.getClass().getClassLoader().getResource(File.separator).getPath();
			System.out.println(routePath);

			/*for finding the path*/
			String newLine = System.getProperty("line.separator");
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(routePath+File.separator+".."+File.separator+"ERROR_LOGSS.txt"), true));
			
			PrintWriter pw = new PrintWriter(bw);
		    pw.println("-------"+e);
			  
			
			  pw.close();
			
			}
			catch(Exception e1)
			{
			}
			}
		}
		
	
	
	
	
	
	
	
}
