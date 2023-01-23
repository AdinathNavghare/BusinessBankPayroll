package payroll.Core;

import java.sql.Connection;
import java.sql.PreparedStatement;

import payroll.DAO.ConnectionManager;

public class MiscPrograms 
{

	ErrorLog el=new ErrorLog();
	// revert the finalize salary month program
	
	public void revertSalMonth(String salmonth)
	{
		
		
	System.out.println("REveting Salary for the month of "+salmonth);	
	try
	{
	Connection cn=ConnectionManager.getConnection();
	cn.setAutoCommit(false);
	
	PreparedStatement pst1=cn.prepareStatement("");
	
	
	
	
	
	
	
	
	cn.commit();
	}
	catch(Exception e)
	{
	System.out.println("Error in class MiscPrograms -public void revertSalMonth(String salmonth)-----\n "+e);	
		
	el.errorLog("Error in class MiscPrograms -public void revertSalMonth(String salmonth)", e.toString())	;
		
	}
	}
	
	
	
	
	
	
	
	
	
	
	
}
