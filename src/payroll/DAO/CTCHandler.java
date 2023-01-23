package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import payroll.Core.ReportDAO;
import payroll.Model.EmployeeBean;

public class CTCHandler 
{

	
	
public String  getMAXDateinPaytran(String empno)
{
	String temp_date = "";
	SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
	Connection con= ConnectionManager.getConnection();
	try
	{
		Statement st1 = con.createStatement();
		Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs=null;
		String check = "select max(trndt) from paytran where empno = "+empno;
		rs=st1.executeQuery(check);
		if(rs.next())
		{
			System.out.println("-----------------------");
			if(rs.getDate(1)==null)
			{
				EmployeeHandler EH=new EmployeeHandler();
				EmployeeBean EBean=new EmployeeBean();
				EBean=EH.getEmployeeInformation(empno);
				temp_date=ReportDAO.EOM(EBean.getDOJ());	
			}
			else
			{
			temp_date=sdf.format(rs.getDate(1));
			System.out.println(temp_date);
			}
		}
		else
		{
			EmployeeHandler EH=new EmployeeHandler();
			EmployeeBean EBean=new EmployeeBean();
			EBean=EH.getEmployeeInformation(empno);
			temp_date=ReportDAO.EOM(EBean.getDOJ());
			
		}
	
	}
	catch(Exception e)
	{
		
		e.printStackTrace();
		System.out.println("Exception in CTCHandler");
		EmployeeHandler EH=new EmployeeHandler();
		EmployeeBean EBean=new EmployeeBean();
		EBean=EH.getEmployeeInformation(empno);
		temp_date=ReportDAO.EOM(EBean.getDOJ());
	
	}
		
	return temp_date;
}	



public String  getMINDateinPaytran(String empno)
{
	String temp_date = "";
	SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
	Connection con= ConnectionManager.getConnection();
	try
	{
		Statement st1 = con.createStatement();
		Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs=null;
		String check = "select MIN(trndt) from paytran where empno = "+empno;
		rs=st1.executeQuery(check);
		if(rs.next())
		{
			
			if(rs.getDate(1)!=null)
			temp_date=ReportDAO.BOM(sdf.format(rs.getDate(1)));
			else
			{
				EmployeeHandler EH=new EmployeeHandler();
				EmployeeBean EBean=new EmployeeBean();
				EBean=EH.getEmployeeInformation(empno);
				temp_date=EBean.getDOJ();
			}
		}
		else
		{
			EmployeeHandler EH=new EmployeeHandler();
			EmployeeBean EBean=new EmployeeBean();
			EBean=EH.getEmployeeInformation(empno);
			temp_date=EBean.getDOJ();
			
		}
	
	}
	catch(Exception e)
	{
		
		e.printStackTrace();
		System.out.println("Exception in CTCHandler");
		EmployeeHandler EH=new EmployeeHandler();
		EmployeeBean EBean=new EmployeeBean();
		EBean=EH.getEmployeeInformation(empno);
		temp_date=EBean.getDOJ();
	
	}
		
	return temp_date;
}









}
