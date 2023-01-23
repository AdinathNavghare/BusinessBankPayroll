package payroll.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import payroll.Core.ErrorLog;
import payroll.Core.ReportDAO;
import payroll.Model.ITBean;

public class ITHandler {
	ErrorLog errorLog=new ErrorLog();
	Connection connection = null;
	Statement statement=null;
	ResultSet resultSet=null;
	Boolean flag=false;
	
	public float getDeduction(int empno)
	{
		
		//ITBean itBean=new ITBean();
		connection = ConnectionManager.getConnection();
		float temp=0.0f;
		try
		{
			
			statement= connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT sum(net_amt) as deduction" +
					" from PAYTRAN_STAGE where trncd in (202,535) and empno="+empno+" ");
			
			while(resultSet.next())
			{	
			temp=resultSet.getFloat("deduction");	
			}
			connection.close();
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
		
			errorLog.errorLog("getDeduction :ERROR getting deduction from paytran_stage", e.toString());
		}
		return temp;
		
		
	}
	
	public float generic(String column,String year,int empno)
	{
		
		//ITBean itBean=new ITBean();
		connection = ConnectionManager.getConnection();
		float temp=0.0f;
		try
		{
			
			statement= connection.createStatement();
			String query="SELECT "+column+" as result " + 
					"from incometax where year like '%"+year+"%' and empno="+empno+"  ";
			System.out.println(query);
			ResultSet resultSet=statement.executeQuery(query);
			
			while(resultSet.next())
			{	
			temp=resultSet.getFloat("result");	
			}
			connection.close();
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
		
			errorLog.errorLog("VdaDAO :ERROR SELECTING TOP 3 RECORDS FROM DAMAST METHOD: getTOP3Vda(). FOR PAGE: VDA.jsp", e.toString());
		}
		System.out.println(temp);
		return temp;
		
		
	}
	public boolean addToIT(ITBean itBean) {
		boolean result = false;
	    
		System.out.println("addToIT");
		
		
		try {
			 connection = ConnectionManager.getConnection();
		
			
    
			String insertOrUpdateQuery = "if exists (select * from INCOMETAX where" +
					" EMPNO="+itBean.getEmpno()+" and YEAR='"+itBean.getYear()+"') "+
					"update INCOMETAX set grosssalary="+itBean.getGrossSalary()+" , " +
					" TOTALDEDUCTION="+itBean.getTotalDeduction()+" "+
					",NETTAXABLEINCOME="+itBean.getNetTaxableIncome()+","+
					"TAXONINCOME="+itBean.getTaxOnIncome()+"," +
					" TAXAFTERREDUCINGCREDIT="+itBean.getTaxAfterReducingCredit()+"," +
					" TOTALTAXLIABLITIY="+itBean.getTotalTaxLiability()+","+
					"TOTALTAXREMAINING="+itBean.getTotalTaxRemaining()+"," +
					"MONTHLYINSTALLMENT="+itBean.getMonthlyInstallment()+"" +
					",UPDATEDDATE='"+ReportDAO.getSysDate()+"' else "+							
					"insert into INCOMETAX (empno,YEAR,grosssalary,Totaldeduction," +
					"NETTAXABLEINCOME,TAXONINCOME,TAXCREDIT,TAXAFTERREDUCINGCREDIT," +
					"TOTALTAXLIABLITIY,TOTALTAXPAID,TOTALTAXREMAINING,REMAININGMONTHS," +
					"FINALIZEDMONTHS,MONTHLYINSTALLMENT,STATUS," +"UPDATEDDATE )" +
					" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
 /*("+itBean.getEmpno()+",'"+itBean.getYear()+"'" +
 		","+itBean.getGrossSalary()+","+itBean.getTotalDeduction()+","+
 ""+itBean.getNetTaxableIncome()+","+itBean.getTaxOnIncome()+","+
 		itBean.getTaxCredit()+","+itBean.getTaxAfterReducingCredit()+"," +
 		""+itBean.getTotalTaxLiability()+","+itBean.getTotalTaxPaid()+"," +
 		""+itBean.getTotalTaxRemaining()+","+itBean.getRemainingMonths()+"," +
 		""+itBean.getFinalizedMonths()+","+itBean.getMonthlyInstallment()+"," +
 		"'"+itBean.getStatus()+"'+,'"+ReportDAO.getSysDate()+"')";*/
					
					//"PRJ_SRNO,FOR_MONTH,CREATED_BY,CREATED_DATE) values(?,?,?,?,?,?,?,?)";
			System.out.println(insertOrUpdateQuery);
			PreparedStatement Pstat = connection.prepareStatement(insertOrUpdateQuery);
			Pstat.setInt(1, itBean.getEmpno());
			Pstat.setString(2, itBean.getYear());
			Pstat.setFloat(3, itBean.getGrossSalary());
			Pstat.setFloat(4, itBean.getTotalDeduction());
			Pstat.setFloat(5, itBean.getNetTaxableIncome());
			Pstat.setFloat(6, itBean.getTaxOnIncome());
			Pstat.setFloat(7, itBean.getTaxCredit());
			Pstat.setFloat(8, itBean.getTaxAfterReducingCredit());
			Pstat.setFloat(9, itBean.getTotalTaxLiability());
			Pstat.setFloat(10, itBean.getTotalTaxPaid());
			Pstat.setFloat(11, itBean.getTotalTaxRemaining());
			Pstat.setFloat(12, itBean.getRemainingMonths());
			Pstat.setFloat(13, itBean.getFinalizedMonths());
			Pstat.setFloat(14, itBean.getMonthlyInstallment());
			Pstat.setString(15, itBean.getStatus());
			Pstat.setString(16, ReportDAO.getSysDate());
			
	
		
		
			Pstat.executeUpdate();
			 String updateQuery=""; 
			 Statement statement = connection.createStatement();
		/*	ResultSet resultSet=statement.executeQuery("select  ((TOTALTAXPAID-TOTALTAXREMAINING)/remainingMonths) " +
					"as TDS from incometax where empno="+itBean.getEmpno()+" ");*/
			 System.out.println("if ( (select "+
					" ((TOTALTAXREMAINING-TOTALTAXPAID)/remainingMonths)"+
					 "from incometax where empno="+itBean.getEmpno()+" and YEAR like '"+itBean.getYear()+"' ) >0)"+
					 "select  ((TOTALTAXREMAINING-TOTALTAXPAID)/remainingMonths) as TDS "+
					 	"from incometax where empno="+itBean.getEmpno()+" and YEAR like '"+itBean.getYear()+"'"+	
					 		" else select 0	as TDS");
			 ResultSet resultSet=statement.executeQuery("if ( (select "+
					" ((TOTALTAXREMAINING-TOTALTAXPAID)/remainingMonths)"+
					 "from incometax where empno="+itBean.getEmpno()+" and YEAR like '"+itBean.getYear()+"' ) >0)"+
					 "select  ((TOTALTAXREMAINING-TOTALTAXPAID)/remainingMonths) as TDS "+
					 	"from incometax where empno="+itBean.getEmpno()+" and YEAR like '"+itBean.getYear()+"'"+	
					 		" else select 0	as TDS"  );
			
			 while(resultSet.next())
				{ 
			 
			System.out.println("tds : "+resultSet.getFloat("TDS"));
			float tds=Math.round(resultSet.getFloat("TDS"));
			updateQuery="update incometax  set" +
					" monthlyinstallment="+tds+
					"where  empno="+itBean.getEmpno()+" and YEAR like '"+itBean.getYear()+"'";
			Pstat = connection.prepareStatement(updateQuery);
			Pstat.executeUpdate();
			
			
			updateQuery="update paytran set inp_amt="+tds+" where" +
					" empno="+itBean.getEmpno()+" and trncd=228";
			Pstat = connection.prepareStatement(updateQuery);
			Pstat.executeUpdate();
				}
			result = true;
			Pstat.close();

	
			
			
			
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			 result=false;
		}
		return result;

	}
	
	
	
}
