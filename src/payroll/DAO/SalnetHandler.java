package payroll.DAO;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import payroll.Core.ErrorLog;
import payroll.Core.ReportDAO;
import payroll.Core.originalNumToLetter;
import payroll.Model.BranchBean;
import payroll.Model.RepoartBean;

public class SalnetHandler {

	// padding method by hrishi 
	 public static String fillLeft(String text) {
	        
	        	String padded="00000000000".substring(text.length())+text;
	        	//System.out.println("right "+padded);
	        	
	        	return padded;
	    }
	
	 public static String fillRight(String text) {
	       
		
	        String padded=text+("000000000000".substring(text.length())); 
	       // System.out.println("left "+padded);	
	       
		 return padded;
	    }
	 
	 public static String fillBRBD(String text) {
	       
			
		    String padded="000000000000000000".substring(text.length())+text;
	       // System.out.println("fillBRBD "+padded);	
	       
		 return padded;
	    }
	
	 public static String fillcountSpace(String text) {
	       
			
		    String padded="                  ".substring(text.length())+text;
	       // System.out.println("fillcountSpace "+padded);	
	       
		 return padded;
	    }
	 // ends here 000000000000000000
	 
	
	 public static void salNet(String reportType, String filepath)
	{
     System.out.println("salnet handler");
		

		RepoartBean repBean  = new RepoartBean();
		Connection Cn = null;

		String sql = "";
		
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
	
		FileWriter Fp;
		
		ReportDAO.inithead(repBean);
		
	    ReportDAO.make_prn_file(filepath,repBean);
	    
	    ReportDAO.println(String.valueOf((char)18), 0, 1, false, "BANK",repBean);//          ***** P H PATIL Sir
	    
	    
	    
	    sql = "select * from AUTOPOST WHERE SUBSYS_CD='sb'";
	   
	    //DoEvents              
	    ReportDAO.OpenCon("", "", "",repBean);
	    Cn = repBean.getCn();
	    try
	    {
			Statement st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(sql);
			


					while(rs.next())
					{
						
						ReportDAO.println("000001SB               "+fillRight(rs.getString("AC_NO"))+""+fillLeft(rs.getString("AMOUNT")), 0, 1, false, "BANK",repBean);
						
					}
					
				
	
			
			Fp = repBean.getFp();
			Fp.close();
			Cn.close();
		} catch (Exception e)
		{
			e.printStackTrace();
			ErrorLog errorLog=new ErrorLog();
			 errorLog.errorLog("SalnethandlerHandler: ERROR In Printing Report  IN salNet METHOD:  FOR PAGE: SALNET.jsp", e.toString());
		}
	    
	   
	    
	    
	
		
	
	}
	 
	 public static void BRBD(String reportType, String filepath, String date)
		{
	     System.out.println("salnet handler BRBD method");
			

			RepoartBean repBean  = new RepoartBean();
			Connection Cn = null;

			String sql = "";
			
			ResultSet rs = null;
			ResultSet rs1 = null;
			ResultSet rs2 = null;
			ResultSet rs3 = null;
			ResultSet rs4 = null;
		
			FileWriter Fp;
			
			ReportDAO.inithead(repBean);
			
		    ReportDAO.make_prn_file(filepath,repBean);
		    
		    ReportDAO.println(String.valueOf((char)18), 0, 1, false, "BANK",repBean);//          ***** P H PATIL Sir
		    
		    
		    
		    sql = "select * from AUTOPOST WHERE SUBSYS_CD='sb'";
		   
		    //DoEvents              
		    ReportDAO.OpenCon("", "", "",repBean);
		    Cn = repBean.getCn();

	    	String Branch = "select distinct Branch from emptran where branch is not null order by branch ";
			   
		    //DoEvents              
		    ReportDAO.OpenCon("", "", "",repBean);
		    Cn = repBean.getCn();
		    
		    try
		    {
				Statement st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				rs = st.executeQuery(Branch);
				Integer count=0;


						while(rs.next())
						{
							count++;
							//sql=" select sum(NET_AMT) from PAYTRAN_STAGE  where TRNDT between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' and trncd=198";
							sql=" select SUM(p.NET_AMT) as NET_AMT from PAYTRAN_STAGE p join EMPTRAN e on e.EMPNO=p.EMPNO " +
								" and e.EMPNO in(select EMPNO from EMPTRAN where BRANCH='"+rs.getString("Branch")+"') and p.TRNDT between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' and p.TRNCD=198";
							
							System.out.println(sql);
							Statement st1 = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
							rs1 = st1.executeQuery(sql);
							
							
							while(rs1.next())
							
							{
								if(rs.getString("Branch").equals("999"))
								{
									 ReportDAO.println("000099PL    "+fillcountSpace(rs.getString("Branch"))+""+fillBRBD(rs1.getString("NET_AMT"))+"    D", 0, 1, false, "BANK",repBean);
								}
								else{
							         ReportDAO.println("000099BRANCH"+fillcountSpace(rs.getString("Branch"))+""+fillBRBD(rs1.getString("NET_AMT"))+"    D", 0, 1, false, "BANK",repBean);
								}
							}
						
						}
						
					
		
				
				Fp = repBean.getFp();
				Fp.close();
				Cn.close();
			} catch (Exception e)
			{
				e.printStackTrace();
				ErrorLog errorLog=new ErrorLog();
				 errorLog.errorLog("SalnethandlerHandler: ERROR In Printing Report  IN salNet METHOD:  FOR PAGE: SALNET.jsp", e.toString());
			}
	    
	    
		   
		    
		    
		
			
		
		}
	
	 
	 

}
