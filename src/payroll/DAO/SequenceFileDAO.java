package payroll.DAO;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import payroll.Core.ReportDAO;
import payroll.Model.SequenceFileBean;

public class SequenceFileDAO
{

	
	
	
	
public ArrayList<SequenceFileBean> getAllSubsysCode()
{
	
	
	ArrayList<SequenceFileBean> list=new ArrayList<SequenceFileBean>(); 
	
	try
	{
	Connection con=ConnectionManager.getConnection();
	Statement st1=con.createStatement();
	//ResultSet rs= st1.executeQuery(" select * from CDMAST where AUTOPOST='Y'  and trncd in(227,208,226,253,216,257,212,247,215,248,245,246,205,244,260,261,262) order by trncd ");
	
	ResultSet rs= st1.executeQuery(" select * from CDMAST where AUTOPOST='Y'  and trncd in (244, 245, 246, 247) order by trncd ");
	while(rs.next())
	{
		SequenceFileBean sfBean =new SequenceFileBean();
		sfBean.setTrncd(rs.getInt("TRNCD"));
		sfBean.setDisc(rs.getString("disc"));
		sfBean.setSubsys(rs.getString("subsys"));
		sfBean.setBranch_no(rs.getInt("BRACNO"));
		sfBean.setAc_no(rs.getString("ACNO"));
		
		list.add(sfBean);
	}
	
	
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
return list;	
}

public boolean generateSEQ(String trncd, String filePath, String Date,String branch,String subsys) 
{
 System.out.println("in else:  trncd: "+trncd);
	boolean flag=false;
	try
	{
	 File file = new File(filePath);
		FileWriter Fp;
		
		Fp = new FileWriter(file);
		
		System.out.println(filePath);
		
		Connection con=ConnectionManager.getConnection();
		Statement st1=con.createStatement();
		/*String query=" SELECT REPLICATE('0',6-LEN(RTRIM(EMPNO))) + RTRIM(empno)+SUBSYS_CD+REPLICATE(' ',15-LEN(RTRIM(''))) + RTRIM('')+REPLICATE('0',7-LEN(RTRIM(AC_NO))) + RTRIM(AC_NO)+REPLICATE('0',16-LEN(RTRIM(AMOUNT))) + RTRIM(AMOUNT) from AUTOPOST where trncd="+trncd;
		*/
	
		/*String query=" SELECT L.LoanType +REPLICATE(' ',6-LEN(RTRIM(''))) +   RTRIM('')+REPLICATE(' ',7-LEN(RTRIM(L.loanAccNo))) + " +
				"RTRIM(L.loanAccNo)+   REPLICATE(' ',5-LEN(RTRIM(''))) +  " +
				" RTRIM('') +REPLICATE('0',14-LEN(RTRIM(L.loaninstallment)))+ RTRIM(L.loaninstallment) " +
				"   from loanseq L where trncd=  "+trncd" and    L.empcode in(select empcode from EMPMAST  where " +
				"    EMPNO in (select EMPNO from emptran where prj_srno=1))"; //pass prj_srno from jsp 
*/		
		/*String query=" SELECT L.LoanType +REPLICATE(' ',6-LEN(RTRIM(''))) +   RTRIM('')+REPLICATE(' ',7-LEN(RTRIM(L.loanAccNo))) + " +
				"RTRIM(L.loanAccNo)+   REPLICATE(' ',5-LEN(RTRIM(''))) +  " +
				" RTRIM('') +REPLICATE('0',14-LEN(RTRIM(L.loaninstallment)))+ RTRIM(L.loaninstallment) " +
				"   from loanseq L where trncd=  "+trncd+" and    L.empcode in(select empcode from EMPMAST  where " +
				"    EMPNO in (select EMPNO from emptran where prj_srno="+branch+"))"; //pass prj_srno from jsp
*/		
		
	/*String query="select SUM(p.amount) as net, p.ac_no from DEDMAST p,EMPTRAN T,empmast E   where " +
			" p.trncd="+trncd+" and " +
			"p.EMPNO=T.EMPNO and E.EMPNO = T.EMPNO and t.acno is not null   AND " +
			"T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) " +
			"AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Date)+"') or (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Date)+"' )) " +
			"group by p.ac_no order by p.AC_NO";*/ 
			String query="select SUM(p.amount) as net, p.ac_no from DEDMAST p,EMPTRAN T,empmast E,paytran_stage py    where " +
					" p.trncd="+trncd+" and " +
					" py.trncd="+trncd+" and  py.NET_AMT !=0 and " +
					"p.EMPNO=T.EMPNO and E.EMPNO = T.EMPNO  AND py.empno = T.empno  AND py.TRNDT = '"+ReportDAO.EOM(Date)+"'  and t.acno is not null   AND  p.ACTYN='Y' AND " +
					"T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) and p.REPAY_START <='"+ReportDAO.EOM(Date)+"'  " +
					"AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Date)+"') or (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Date)+"' )) " +
					"group by p.ac_no order by p.AC_NO";
		System.out.println(query);
		ResultSet rs= st1.executeQuery(query);
		
		while(rs.next())
		{
			
			Fp.write("000001"+""+subsys+"-S         "+(rs.getString(2))+"     "+SequenceFileDAO.leftpaddedzero1(rs.getString(1))+"");
			Fp.write("\n");
		}
		Fp.close();
		
		flag=true;
	}
	catch(Exception e)
	{
		System.out.println("Error in make_prn_file Function "+e);
		e.printStackTrace();
		flag=false;
	}
	
	
	return flag;
}	

public boolean generateSALNET(String trncd, String filePath, String Date,String branch) 
{

	System.out.println("BRANCH 1 :"+branch);
	boolean flag=false;
	try
	{
	 File file = new File(filePath);
		FileWriter Fp;
		
		Fp = new FileWriter(file);
		
		System.out.println("sal AJ");
		
		Connection con=ConnectionManager.getConnection();
		Statement st1=con.createStatement();
	//	String query=" SELECT REPLICATE('0',6-LEN(RTRIM(EMPNO))) + RTRIM(empno)+SUBSYS_CD+REPLICATE(' ',15-LEN(RTRIM(''))) + RTRIM('')+REPLICATE('0',7-LEN(RTRIM(AC_NO))) + RTRIM(AC_NO)+REPLICATE('0',16-LEN(RTRIM(AMOUNT))) + RTRIM(AMOUNT) from AUTOPOST where trncd="+trncd;
	
		String branchnew = ""; 
		
		if(branch.equalsIgnoreCase("ALL"))
		{
			branchnew="";
		}
		else
		{
			branchnew = "AND T.branch = " +branch;
		}
		
	String query="select SUM(p.Net_amt) as net, t.acno, t.branch from PAYTRAN_STAGE p,EMPTRAN T,empmast E   where " +
			" p.trncd=999 and p.trndt between '"+ReportDAO.BOM(Date)+"' and '"+ReportDAO.EOM(Date)+"' and " +
			"p.EMPNO=T.EMPNO and E.EMPNO = T.EMPNO and t.acno is not null   AND " +
			"T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) " +
			//"AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Date)+"') or (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Date)+"' ))and p.Net_amt!=0  " +
			"AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Date)+"') or (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Date)+"' ))and p.Net_amt!=0 "+ branchnew+
			" group by t.acno, t.branch  order by ACNO ";
		/*String query=" SELECT L.LoanType +REPLICATE(' ',6-LEN(RTRIM(''))) +   RTRIM('')+REPLICATE(' ',7-LEN(RTRIM(L.loanAccNo))) + " +
				"RTRIM(L.loanAccNo)+   REPLICATE(' ',5-LEN(RTRIM(''))) +  " +
				" RTRIM('') +REPLICATE('0',14-LEN(RTRIM(L.loaninstallment)))+ RTRIM(L.loaninstallment) " +
				"   from loanseq L where trncd=  "+trncd" and    L.empcode in(select empcode from EMPMAST  where " +
				"    EMPNO in (select EMPNO from emptran where prj_srno=1))"; //pass prj_srno from jsp 
*/		
		/*String query=" SELECT L.LoanType +REPLICATE(' ',6-LEN(RTRIM(''))) +   RTRIM('')+REPLICATE(' ',7-LEN(RTRIM(L.loanAccNo))) + " +
				"RTRIM(L.loanAccNo)+   REPLICATE(' ',5-LEN(RTRIM(''))) +  " +
				" RTRIM('') +REPLICATE('0',14-LEN(RTRIM(L.loaninstallment)))+ RTRIM(L.loaninstallment) " +
				"   from loanseq L where trncd=  "+trncd+" and    L.empcode in(select empcode from EMPMAST  where " +
				"    EMPNO in (select EMPNO from emptran where prj_srno="+branch+"))"; //pass prj_srno from jsp
*/		
		System.out.println("SALNET QUERY : "+query);
		ResultSet rs= st1.executeQuery(query);
		
		
		
		while(rs.next())
		{
		
			String account_No =rs.getString(2);
			String total_account = "00000000";
			int k = 8;
	        String newString = new String(); 
	        
				if(account_No.length()!=k)
				{
					int j = k-account_No.length();
					for(int i=1; i<=j; i++)
					{
						newString +=total_account.charAt(i);
					}
				}
				account_No = newString +""+account_No;
			
			String salay_amt =rs.getString(1);
			String total_salary = "0000000000000";
			int x = 16;
	        String newString1 = new String(); 
	        
				if(salay_amt.length()!=x)
				{
					int j = x-salay_amt.length();
					for(int i=1; i<=j; i++)
					{
						newString1 +=total_salary.charAt(i);
					}
				}
				salay_amt = newString1 +""+salay_amt;
			
			//Fp.write("00000"+rs.getString(3)+"SB              "+"000" +(rs.getString(2))+"00000000"+(rs.getString(1))+"");
			Fp.write("00000"+rs.getString(3)+"SB              "+account_No+salay_amt+"");
			Fp.write("\n");
		}
		Fp.close();
		
		flag=true;
	}
	catch(Exception e)
	{
		System.out.println("Error in make_prn_file Function "+e);
		e.printStackTrace();
		flag=false;
	}
	
	
	return flag;
}	


public boolean generateBRDR(String trncd, String filePath,String Date,String branch) 
{

	boolean flag=false;
	try
	{
	 File file = new File(filePath);
		FileWriter Fp;
		
		Fp = new FileWriter(file);
		
		System.out.println(filePath);
		
		Connection con=ConnectionManager.getConnection();
		Statement st1=con.createStatement();
		Statement st2=con.createStatement();
		ResultSet rs= st1.executeQuery("select Distinct SITE_ID from Project_Sites ");
		String site="";
		
		while(rs.next())
		{
			site=rs.getString(1);
			String query=" select SUM(inp_amt) from PAYTRAN_STAGE where trncd="+trncd+" and trndt between '"+ReportDAO.BOM(Date)+"' and '"+ReportDAO.EOM(Date)+"' and EMPNO in (SELECT E.EMPNO" +
					" FROM EMPMAST E,EMPTRAN T  WHERE E.EMPNO = T.EMPNO AND T.PRJ_SRNO = '"+rs.getString(1)+"'"+
					" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)" +
					" AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Date)+"') or" +
					" (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Date)+"' ))  )";
			
			System.out.println("ak query"+query);
			
			ResultSet total=st2.executeQuery(query);
			
			while(total.next())
			{
				if(site.equalsIgnoreCase("999"))
				{
					Fp.write("000099PL    "+SequenceFileDAO.leftpadblank("5001")+""+SequenceFileDAO.leftpaddedzero(total.getString(1))+"    D");
					
					Fp.write("\n");
				}
				else{
					Fp.write("000099BRANCH"+SequenceFileDAO.leftpadblank(site)+""+SequenceFileDAO.leftpaddedzero(total.getString(1))+"    D");
		
					Fp.write("\n");
				}
			}
			}
		Fp.close();
		
		flag=true;
	}
	catch(Exception e)
	{
		System.out.println("Error in make_prn_file Function "+e);
		e.printStackTrace();
		flag=false;
	}
	
	
	return flag;
}

public boolean generatePLDR(String trncd, String filePath,String Date,String branch) 
{

	boolean flag=false;
	try
	{
	 File file = new File(filePath);
		FileWriter Fp;
		
		Fp = new FileWriter(file);
		
		System.out.println(filePath);
		
		Connection con=ConnectionManager.getConnection();
		Statement st1=con.createStatement();
		Statement st2=con.createStatement();
		ResultSet rs= st1.executeQuery("select Distinct SITE_ID from Project_Sites ");
		String site="";
		
		while(rs.next())
		{
			site=rs.getString(1);
			String query=" select SUM(inp_amt) from PAYTRAN_STAGE where trncd="+trncd+" and trndt between '"+ReportDAO.BOM(Date)+"' and '"+ReportDAO.EOM(Date)+"' and EMPNO in (SELECT E.EMPNO" +
					" FROM EMPMAST E,EMPTRAN T  WHERE E.EMPNO = T.EMPNO AND T.PRJ_SRNO = '"+rs.getString(1)+"'"+
					" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)" +
					" AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Date)+"') or" +
					" (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Date)+"' ))  )";
			System.out.println("ak query"+query);
			
			ResultSet total=st2.executeQuery(query);
			
			while(total.next())
			{
		
				if(!site.equalsIgnoreCase("999"))
				{
			Fp.write(SequenceFileDAO.leftpadSite(site)+"PL"+SequenceFileDAO.leftpadblank("")+"5001"+SequenceFileDAO.leftpaddedzero(total.getString(1))+"    D");
		
			Fp.write("\n");
				}
			}
			}
		Fp.close();
		
		flag=true;
	}
	catch(Exception e)
	{
		System.out.println("Error in make_prn_file Function "+e);
		e.printStackTrace();
		flag=false;
	}
	
	
	return flag;
}


public boolean generateHOCR(String trncd, String filePath,String Date,String branch) 
{

	boolean flag=false;
	try
	{
	 File file = new File(filePath);
		FileWriter Fp;
		
		Fp = new FileWriter(file);
		
		System.out.println(filePath);
		
		Connection con=ConnectionManager.getConnection();
		Statement st1=con.createStatement();
		Statement st2=con.createStatement();
		ResultSet rs= st1.executeQuery("select Distinct SITE_ID from Project_Sites ");
		String site="";
		
		while(rs.next())
		{
			site=rs.getString(1);
			String query=" select SUM(inp_amt) from PAYTRAN_STAGE where trncd="+trncd+" and trndt between '"+ReportDAO.BOM(Date)+"' and '"+ReportDAO.EOM(Date)+"' and EMPNO in (SELECT E.EMPNO" +
					" FROM EMPMAST E,EMPTRAN T  WHERE E.EMPNO = T.EMPNO AND T.PRJ_SRNO = '"+rs.getString(1)+"'"+
					" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)" +
					" AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Date)+"') or" +
					" (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Date)+"' ))  )";
			System.out.println("ak query"+query);
			
			ResultSet total=st2.executeQuery(query);
			
			while(total.next())
			{
		
				if(!site.equalsIgnoreCase("999"))
				{
			Fp.write(SequenceFileDAO.leftpadSite(site)+"HO"+SequenceFileDAO.leftpadblank("")+"0000"+SequenceFileDAO.leftpaddedzero(total.getString(1))+"    C");
		
			Fp.write("\n");
				}
			}
			}
		Fp.close();
		
		flag=true;
	}
	catch(Exception e)
	{
		System.out.println("Error in make_prn_file Function "+e);
		e.printStackTrace();
		flag=false;
	}
	
	
	return flag;
}


public static String leftpadblank(String unpadded)
{
	
	//System.out.println("unpadded      "+unpadded);
	String padded = "                  ".substring(unpadded.length()) + unpadded;
	//String padded="00000000".substring(unpadded.length())+unpadded;
	//System.out.println(padded);
	
	return padded;	
}

public static String leftpadSite(String unpadded)
{
	
	//System.out.println("unpadded      "+unpadded);
	String padded = "000000".substring(unpadded.length()) + unpadded;
	//String padded="00000000".substring(unpadded.length())+unpadded;
	//System.out.println(padded);
	
	return padded;	
}

public static String leftpaddedzero(String unpadded)
{
	
	//System.out.println("unpadded      "+unpadded);
	String padded = "000000000000000000".substring(unpadded.length()) + unpadded;
	//String padded="00000000".substring(unpadded.length())+unpadded;
	//System.out.println(padded);
	
	return padded;	
}


public static String leftpaddedzero1(String unpadded)
{
	
	String padded = "0000000000000000".substring(unpadded.length()) + unpadded;
	//String padded="00000000".substring(unpadded.length())+unpadded;
	//System.out.println(padded);
	
	return padded;	
}

public static String leftpadBranch(String unpadded)
{
	
	System.out.println("unpadded      "+unpadded);
	String padded = "000000".substring(unpadded.length()) + unpadded;
	//String padded="00000000".substring(unpadded.length())+unpadded;
	System.out.println(padded);
	
	return padded;	
}
public boolean generateBONUSSEQ(String trncd, String filePath, String Date,String branch) 
{

	boolean flag=false;
	try
	{
	 File file = new File(filePath);
		FileWriter Fp;
		
		Fp = new FileWriter(file);
		
		System.out.println(filePath);
		
		Connection con=ConnectionManager.getConnection();
		Statement st1=con.createStatement();
		
	String query="select SUM(p.Net_amt) as net, t.acno from PAYTRAN_STAGE p,EMPTRAN T,empmast E   where " +
			" p.trncd=135 and p.trndt between '"+ReportDAO.BOM(Date)+"' and '"+ReportDAO.EOM(Date)+"' and " +
			"p.EMPNO=T.EMPNO and E.EMPNO = T.EMPNO and t.acno is not null   AND " +
			"T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) " +
			"AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Date)+"') or (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Date)+"' ))and p.Net_amt!=0  " +
			"group by t.acno order by ACNO";
		
		System.out.println(query);
		ResultSet rs= st1.executeQuery(query);
		
		while(rs.next())
		{
			
			Fp.write("000001SB    "+SequenceFileDAO.leftpadblank(rs.getString(2))+""+SequenceFileDAO.leftpaddedzero1(rs.getString(1))+"");
			Fp.write("\n");
		}
		Fp.close();
		
		flag=true;
	}
	catch(Exception e)
	{
		System.out.println("Error in make_prn_file Function "+e);
		e.printStackTrace();
		flag=false;
	}
	
	
	return flag;
}
public boolean generateADVMED(String filePath, String Date,String branch,String subsys) 
{
	boolean flag=false;
	try
	{
	 File file = new File(filePath);
		FileWriter Fp;
		
		Fp = new FileWriter(file);
		
		System.out.println(filePath);
		
		Connection con=ConnectionManager.getConnection();
		Statement st1=con.createStatement();
		
	String query=""
			+ "select Sum(d.amount) AS net,d.ac_no "
			+ "from PAYTRAN_STAGE p,DEDMAST d, "
			+ " emptran t, empmast e "
			+ "where p.EMPNO=d.EMPNO and p.TRNCD =212 "
			+ "and d.ACTYN= 'Y' and d.SUBSYS_CD = 'ADVMED-S' "
			+ "and p.NET_AMT <> 0 and p.TRNDT = '"+ReportDAO.EOM(Date)+"' "
			+ "and e.empno=p.EMPNO "
			+ "and e.EMPNO=t.EMPNO "
			+ "and t.acno IS NOT NULL "
			+ "and t.srno = (SELECT Max(srno) FROM   emptran T1 WHERE  T1.empno = e.empno) "
			+ "AND d.repay_start <= '"+ReportDAO.EOM(Date)+"' "
			+ "AND ( ( e.status = 'A' AND e.doj <= '"+ReportDAO.EOM(Date)+"' ) OR ( e.status = 'N' "
			+ "AND e.dol >= '"+ReportDAO.BOM(Date)+"' ) ) "
			+ "GROUP  BY d.ac_no "
			+ "ORDER  BY d.ac_no";
		System.out.println(query);
		ResultSet rs= st1.executeQuery(query);
		
		while(rs.next())
		{
			Fp.write("000001"+""+subsys+"         "+(rs.getString(2))+"      0000000"+(rs.getString(1))+"");
			Fp.write("\n");
		}
		Fp.close();
		
		flag=true;
	}
	catch(Exception e)
	{
		System.out.println("Error in make_prn_file Function "+e);
		e.printStackTrace();
		flag=false;
	}
	return flag;
}
public boolean generateADVGS(String filePath, String Date,String branch,String subsys) 
{
	boolean flag=false;
	try
	{
	 File file = new File(filePath);
		FileWriter Fp;
		
		Fp = new FileWriter(file);
		
		System.out.println(filePath);
		
		Connection con=ConnectionManager.getConnection();
		Statement st1=con.createStatement();
		
	String query=""
			+ "select Sum(d.amount) AS net,d.ac_no "
			+ "from PAYTRAN_STAGE p,DEDMAST d, "
			+ " emptran t, empmast e "
			+ "where p.EMPNO=d.EMPNO and p.TRNCD =212 "
			+ "and d.ACTYN= 'Y' and d.SUBSYS_CD = 'ADVGS-S' "
			+ "and p.NET_AMT <> 0 and p.TRNDT = '"+ReportDAO.EOM(Date)+"' "
			+ "and e.empno=p.EMPNO "
			+ "and e.EMPNO=t.EMPNO "
			+ "and t.acno IS NOT NULL "
			+ "and t.srno = (SELECT Max(srno) FROM   emptran T1 WHERE  T1.empno = e.empno) "
			+ "AND d.repay_start <= '"+ReportDAO.EOM(Date)+"' "
			+ "AND ( ( e.status = 'A' AND e.doj <= '"+ReportDAO.EOM(Date)+"' ) OR ( e.status = 'N' "
			+ "AND e.dol >= '"+ReportDAO.BOM(Date)+"' ) ) "
			+ "GROUP  BY d.ac_no "
			+ "ORDER  BY d.ac_no";
		System.out.println(query);
		ResultSet rs= st1.executeQuery(query);
		
	    int val=0;
		while(rs.next())
		{
			val = String.valueOf(rs.getInt(1)).length();
			/*Fp.write("000001"+""+subsys+"          "+(rs.getString(2))+"      00000000"+(rs.getString(1))+"");
			Fp.write("\n");*/
			
			if(val == 1){
				Fp.write("000001"+""+subsys+"          "+(rs.getString(2))+"     0000000000"+(rs.getString(1))+"");
				Fp.write("\n");
			}
			else if(val == 2){
				Fp.write("000001"+""+subsys+"          "+(rs.getString(2))+"     000000000"+(rs.getString(1))+"");
				Fp.write("\n");
			}
			else if(val == 3){
				Fp.write("000001"+""+subsys+"          "+(rs.getString(2))+"      00000000"+(rs.getString(1))+"");
				Fp.write("\n");
			}
			else if(val == 4){
				Fp.write("000001"+""+subsys+"          "+(rs.getString(2))+"     0000000"+(rs.getString(1))+"");
				Fp.write("\n");
			}
		}
		Fp.close();
		
		flag=true;
	}
	catch(Exception e)
	{
		System.out.println("Error in make_prn_file Function "+e);
		e.printStackTrace();
		flag=false;
	}
	return flag;
}
public boolean generateLNOTH(String filePath, String Date,String branch,String subsys) 
{
	boolean flag=false;
	try
	{
	 File file = new File(filePath);
		FileWriter Fp;
		
		Fp = new FileWriter(file);
		
		System.out.println(filePath);
		
		Connection con=ConnectionManager.getConnection();
		Statement st1=con.createStatement();
		
	String query=""
			+ "select Sum(d.amount) AS net,d.ac_no "
			+ "from PAYTRAN_STAGE p,DEDMAST d, "
			+ " emptran t, empmast e "
			+ "where p.EMPNO=d.EMPNO and p.TRNCD =212 "
			+ "and d.ACTYN= 'Y' and d.SUBSYS_CD = 'LNOTH-S' "
			+ "and p.NET_AMT <> 0 and p.TRNDT = '"+ReportDAO.EOM(Date)+"' "
			+ "and e.empno=p.EMPNO "
			+ "and e.EMPNO=t.EMPNO "
			+ "and t.acno IS NOT NULL "
			+ "and t.srno = (SELECT Max(srno) FROM   emptran T1 WHERE  T1.empno = e.empno) "
			+ "AND d.repay_start <= '"+ReportDAO.EOM(Date)+"' "
			+ "AND ( ( e.status = 'A' AND e.doj <= '"+ReportDAO.EOM(Date)+"' ) OR ( e.status = 'N' "
			+ "AND e.dol >= '"+ReportDAO.BOM(Date)+"' ) ) "
			+ "GROUP  BY d.ac_no "
			+ "ORDER  BY d.ac_no";
		System.out.println(query);
		ResultSet rs= st1.executeQuery(query);
		
		while(rs.next())
		{
			Fp.write("000001"+""+subsys+"            "+(rs.getString(2))+"     "+SequenceFileDAO.leftpaddedzero1(rs.getString(1))+"");
			Fp.write("\n");
		}
		Fp.close();
		
		flag=true;
	}
	catch(Exception e)
	{
		System.out.println("Error in make_prn_file Function "+e);
		e.printStackTrace();
		flag=false;
	}
	return flag;
}

public boolean generateCODS(String filePath, String Date,String branch,String subsys) 
{
	boolean flag=false;
	try
	{
	 File file = new File(filePath);
		FileWriter Fp;
		
		Fp = new FileWriter(file);
		
		System.out.println(filePath);
		
		Connection con=ConnectionManager.getConnection();
		Statement st1=con.createStatement();
		
	String query=""
			+ "select Sum(d.amount) AS net,d.ac_no "
			+ "from PAYTRAN_STAGE p,DEDMAST d, "
			+ " emptran t, empmast e "
			+ "where p.EMPNO=d.EMPNO and d.trncd = 216  and p.TRNCD =216 "
			+ "and d.ACTYN= 'Y' "
			+ "and p.INP_AMT <> 0 and p.TRNDT = '"+ReportDAO.EOM(Date)+"' "
			+ "and e.empno=p.EMPNO "
			+ "and e.EMPNO=t.EMPNO "
			+ "and t.acno IS NOT NULL "
			+ "and t.srno = (SELECT Max(srno) FROM   emptran T1 WHERE  T1.empno = e.empno) "
			+ "AND d.repay_start <= '"+ReportDAO.EOM(Date)+"' "
			+ "AND ( ( e.status = 'A' AND e.doj <= '"+ReportDAO.EOM(Date)+"' ) OR ( e.status = 'N' "
			+ "AND e.dol >= '"+ReportDAO.BOM(Date)+"' ) ) "
			+ "GROUP  BY d.ac_no "
			+ "ORDER  BY d.ac_no";
		System.out.println(query);
		ResultSet rs= st1.executeQuery(query);
		String AcNum="";
		while(rs.next())
		{
			AcNum = rs.getString(2);
			if(AcNum.length()==1){
				Fp.write("000001"+""+subsys+"            "+(rs.getString(2))+"        0000000"+(rs.getString(1))+"");
				Fp.write("\n");
			}
			else if(AcNum.length()==2){
				Fp.write("000001"+""+subsys+"            "+(rs.getString(2))+"       0000000"+(rs.getString(1))+"");
				Fp.write("\n");
			}
			else if(AcNum.length()==3){
				Fp.write("000001"+""+subsys+"            "+(rs.getString(2))+"      0000000"+(rs.getString(1))+"");
				Fp.write("\n");
			}
			else if(AcNum.length()==4){
			Fp.write("000001"+""+subsys+"            "+(rs.getString(2))+"     0000000"+(rs.getString(1))+"");
			Fp.write("\n");
		}
			/*else{
			Fp.write("000001"+""+subsys+"            "+(rs.getString(2))+"     "+SequenceFileDAO.leftpaddedzero1(rs.getString(1))+"");
			Fp.write("\n");
		}*/
		}
		Fp.close();
		
		flag=true;
	}
	catch(Exception e)
	{
		System.out.println("Error in make_prn_file Function "+e);
		e.printStackTrace();
		flag=false;
	}
	return flag;
}

public boolean generateLEAVEencash(String filePath, String Date,String branch,String subsys) 
{
	
		boolean flag=false;
		try
		{
		 File file = new File(filePath);
			FileWriter Fp;
			
			Fp = new FileWriter(file);
			
			System.out.println(filePath);
			
			Connection con=ConnectionManager.getConnection();
			Statement st1=con.createStatement();
			
		String query=""
				+ "SELECT Sum(p.NET_AMT) AS net, "
				+ "       t.ACNO, t.branch "
				+ "FROM   paytran_stage p, "
				+ "       emptran t, "
				+ "       empmast e "
				+ "WHERE "
				+ "        p.trncd = 145 "
				+ "        AND p.trndt ='"+ReportDAO.EOM(Date)+"' "
				+ "       AND e.empno = p.empno "
				+ "       AND e.empno = t.empno "
				+ "       AND t.acno IS NOT NULL "
				+ "       AND t.srno = (SELECT Max(srno) "
				+ "                     FROM   emptran T1 "
				+ "                     WHERE  T1.empno = e.empno) "
				+ "       "
				+ "       AND ( ( e.status = 'A' "
				+ "               AND e.doj <= '"+ReportDAO.EOM(Date)+"' ) "
				+ "              OR ( e.status = 'N' "
				+ "                   AND e.dol >= '"+ReportDAO.EOM(Date)+"' ) )  "
				+ "GROUP  BY t.acno, t.branch "
				+ "ORDER  BY t.acno";
			
			System.out.println(query);
			ResultSet rs= st1.executeQuery(query);
			
			while(rs.next())
			{
				
				//Fp.write("000001SB    "+SequenceFileDAO.leftpadblank(rs.getString(2))+""+SequenceFileDAO.leftpaddedzero1(rs.getString(1))+"");
				Fp.write("00000"+rs.getString(3)+"SB    "+SequenceFileDAO.leftpadblank(rs.getString(2))+""+SequenceFileDAO.leftpaddedzero1(rs.getString(1))+"");
				//Fp.write("00000"+rs.getString(3)+"SB              "+account_No+salay_amt+"");
				Fp.write("\n");
			}
			Fp.close();
			
			flag=true;
		
		
	}
	catch(Exception e)
	{
		System.out.println("Error in make_prn_file Function "+e);
		e.printStackTrace();
		flag=false;
	}
	return flag;
	
	}
//CCINST..
public boolean generateCCINST(String trncd, String filePath, String Date,String branch,String subsys) 
{System.out.println("generateCCINST");
	boolean flag=false;
	try
	{
	 File file = new File(filePath);
		FileWriter Fp;
		
		Fp = new FileWriter(file);
		
		System.out.println(filePath);
		
		Connection con=ConnectionManager.getConnection();
		Statement st1=con.createStatement();
		 
			String query="select SUM(p.amount) as net, p.ac_no from DEDMAST p,EMPTRAN T,empmast E,paytran_stage py    where " +
					" p.trncd="+trncd+" and " +
					" py.trncd="+trncd+" and  py.NET_AMT !=0 and " +
					"p.EMPNO=T.EMPNO and E.EMPNO = T.EMPNO  AND py.empno = T.empno  AND py.TRNDT = '"+ReportDAO.EOM(Date)+"'  and t.acno is not null   AND  p.ACTYN='Y' AND " +
					"T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) and p.REPAY_START <='"+ReportDAO.EOM(Date)+"'  " +
					"AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Date)+"') or (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Date)+"' )) " +
					"group by p.ac_no order by p.AC_NO";
		System.out.println(query);
		ResultSet rs= st1.executeQuery(query);
		
		while(rs.next())
		{
			
			Fp.write("000001"+""+subsys+"         "+(rs.getString(2))+"      0000000"+(rs.getString(1))+"");
			Fp.write("\n");
		}
		Fp.close();
		
		flag=true;
	}
	catch(Exception e)
	{
		System.out.println("Error in make_prn_file Function "+e);
		e.printStackTrace();
		flag=false;
	}
	
	
	return flag;
}
public boolean generateLNHYP(String trncd, String filePath, String Date,String branch,String subsys) 
{System.out.println("generateLNHYP");
	boolean flag=false;
	try
	{
	 File file = new File(filePath);
		FileWriter Fp;
		
		Fp = new FileWriter(file);
		
		System.out.println(filePath);
		
		Connection con=ConnectionManager.getConnection();
		Statement st1=con.createStatement();
		 
			String query="select SUM(p.amount) as net, p.ac_no from DEDMAST p,EMPTRAN T,empmast E,paytran_stage py    where " +
					" p.trncd="+trncd+" and " +
					" py.trncd="+trncd+" and  py.NET_AMT !=0 and " +
					"p.EMPNO=T.EMPNO and E.EMPNO = T.EMPNO  AND py.empno = T.empno  AND py.TRNDT = '"+ReportDAO.EOM(Date)+"'  and t.acno is not null   AND  p.ACTYN='Y' AND " +
					"T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) and p.REPAY_START <='"+ReportDAO.EOM(Date)+"'  " +
					"AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Date)+"') or (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Date)+"' )) " +
					"group by p.ac_no order by p.AC_NO";
		System.out.println(query);
		ResultSet rs= st1.executeQuery(query);
		
		while(rs.next())
		{
			
			String AcNum="";
	//		Fp.write("000001"+""+subsys+"-S          "+(rs.getString(2))+"     0000000"+(rs.getString(1))+"");
	//		Fp.write("\n");
			AcNum = rs.getString(2);
			if(AcNum.length()==1){
				Fp.write("000001"+""+subsys+"          "+(rs.getString(2))+"     0000000000"+(rs.getString(1))+"");
				Fp.write("\n");
			}
			else if(AcNum.length()==2){
				Fp.write("000001"+""+subsys+"          "+(rs.getString(2))+"     000000000"+(rs.getString(1))+"");
				Fp.write("\n");
			}
			else if(AcNum.length()==3){
				Fp.write("000001"+""+subsys+"          "+(rs.getString(2))+"     00000000"+(rs.getString(1))+"");
				Fp.write("\n");
			}
			else if(AcNum.length()==4){
			Fp.write("000001"+""+subsys+"          "+(rs.getString(2))+"     0000000"+(rs.getString(1))+"");
			Fp.write("\n");
		}
		}
		Fp.close();
		
		flag=true;
	}
	catch(Exception e)
	{
		System.out.println("Error in make_prn_file Function "+e);
		e.printStackTrace();
		flag=false;
	}
	
	
	return flag;
}

public boolean generateLOANINST(String trncd, String filePath, String Date,String branch,String subsys) 
{
	boolean flag=false;
	try
	{
		File file = new File(filePath);
		FileWriter Fp;
		Fp = new FileWriter(file);
		
		Connection con=ConnectionManager.getConnection();
		Statement st1=con.createStatement();
		
		String branchnew = ""; 
		
		if(branch.equalsIgnoreCase("ALL"))
		{
			branchnew="";
		}
		else
		{
			branchnew = "AND T.branch = " +branch;
		}
	
		String query="select SUM(p.amount) as net, p.ac_no, p.prj_srno, p.trncd from DEDMAST p,EMPTRAN T,empmast E,paytran_stage py    where " +
				" p.trncd="+trncd+" and " +
				" py.trncd="+trncd+" and  py.NET_AMT !=0 and " +
				"p.EMPNO=T.EMPNO and E.EMPNO = T.EMPNO  AND py.empno = T.empno  AND py.TRNDT = '"+ReportDAO.EOM(Date)+"'  and t.acno is not null   AND  p.ACTYN='Y' AND " +
				"T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) and p.REPAY_START <='"+ReportDAO.EOM(Date)+"'  " +
				"AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Date)+"') or (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Date)+"' )) "+branchnew +
				" group by p.ac_no, p.prj_srno, p.trncd order by p.AC_NO";
		
		System.out.println(query);
		ResultSet rs= st1.executeQuery(query);
		String AcNum="";
		
		while(rs.next())
		{
			String product_code="";
			
			if(trncd.equalsIgnoreCase("244")){
				product_code="HSG     ";	
			}
			else if(trncd.equalsIgnoreCase("245")){
				product_code="VL      ";	
			}
			else if(trncd.equalsIgnoreCase("246")){
				product_code="WVL     ";	
			}
			else if(trncd.equalsIgnoreCase("247")){
				product_code="PLN     ";	
			}

			String account_No =rs.getString(2);
			String total_account = "00000000";
			int k = 8;
	        String newString = new String(); 
	        
				if(account_No.length()!=k)
				{
					int j = k-account_No.length();
					for(int i=1; i<=j; i++)
					{
						newString +=total_account.charAt(i);
					}
				}
				account_No = newString +""+account_No;
			
			String salay_amt =rs.getString(1);
			String total_salary = "0000000000000";
			int x = 16;
	        String newString1 = new String(); 
	        
				if(salay_amt.length()!=x)
				{
					int j = x-salay_amt.length();
					for(int i=1; i<=j; i++)
					{
						newString1 +=total_salary.charAt(i);
					}
				}
				salay_amt = newString1 +""+salay_amt;

				Fp.write("00000"+rs.getString(3)+""+product_code+"        "+account_No+salay_amt+"");
			Fp.write("\n");
		}
		Fp.close();
		flag=true;
	}
	catch(Exception e)
	{
		System.out.println("Error in make_prn_file Function "+e);
		e.printStackTrace();
		flag=false;
	}
	return flag;
}


public boolean generateLOANALLINST(String trncd, String filePath, String Date,String branch,String subsys)
{
	boolean flag=false;
	try
	{
		File file = new File(filePath);
		FileWriter Fp;
		Fp = new FileWriter(file);
		
		Connection con=ConnectionManager.getConnection();
		Statement st1=con.createStatement();
		Statement st2=con.createStatement();
		
		String branchnew = ""; 
		
		if(branch.equalsIgnoreCase("ALL"))
		{
			branchnew="";
		}
		else
		{
			branchnew = "AND T.branch = " +branch;
		}
	
		String TrancodeQuery ="SELECT TRNCD FROM CDMAST WHERE TRNCD IN (244, 245, 246, 247) ";
		ResultSet rs2= st2.executeQuery(TrancodeQuery);
		while(rs2.next()) {
			String trncdnew = rs2.getString("TRNCD");
			System.out.println("trncdnew : "+trncdnew);
		
		String query="select SUM(p.amount) as net, p.ac_no, p.prj_srno, p.trncd from DEDMAST p,EMPTRAN T,empmast E,paytran_stage py    where " +
				" p.trncd="+trncdnew+" and " +
				" py.trncd="+trncdnew+" and  py.NET_AMT !=0 and " +
				"p.EMPNO=T.EMPNO and E.EMPNO = T.EMPNO  AND py.empno = T.empno  AND py.TRNDT = '"+ReportDAO.EOM(Date)+"'  and t.acno is not null   AND  p.ACTYN='Y' AND " +
				"T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) and p.REPAY_START <='"+ReportDAO.EOM(Date)+"'  " +
				"AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Date)+"') or (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Date)+"' )) "+branchnew +
				" group by p.ac_no, p.prj_srno, p.trncd order by p.trncd, p.ac_no";
		
		System.out.println(query);
		ResultSet rs= st1.executeQuery(query);
		String AcNum="";
		
		while(rs.next())
		{
			String code=rs.getString(4);
			String product_code="";
			
			if(code.equalsIgnoreCase("244")){
				product_code="HSG     ";	
			}
			else if(code.equalsIgnoreCase("245")){
				product_code="VL      ";	
			}
			else if(code.equalsIgnoreCase("246")){
				product_code="WVL     ";	
			}
			else if(code.equalsIgnoreCase("247")){
				product_code="PLN     ";	
			}
			
			String account_No =rs.getString(2);
			String total_account = "00000000";
			int k = 8;
	        String newString = new String(); 
	        
				if(account_No.length()!=k)
				{
					int j = k-account_No.length();
					for(int i=1; i<=j; i++)
					{
						newString +=total_account.charAt(i);
					}
				}
				account_No = newString +""+account_No;
			
			String salay_amt =rs.getString(1);
			String total_salary = "0000000000000";
			int x = 16;
	        String newString1 = new String(); 
	        
				if(salay_amt.length()!=x)
				{
					int j = x-salay_amt.length();
					for(int i=1; i<=j; i++)
					{
						newString1 +=total_salary.charAt(i);
					}
				}
				salay_amt = newString1 +""+salay_amt;
			
				Fp.write("00000"+rs.getString(3)+""+product_code+"        "+account_No+salay_amt+"");
			Fp.write("\n");
		}
	}
		Fp.close();
		flag=true;
	}
	catch(Exception e)
	{
		System.out.println("Error in make_prn_file Function "+e);
		e.printStackTrace();
		flag=false;
	}
	return flag;
}




public boolean generatePMJJBYPMSBY(String filePath, String Date,String branch,String subsys) 
{
	System.out.println("in generatePMJJBYPMSBY");
		boolean flag=false;
		try
		{
		 File file = new File(filePath);
			FileWriter Fp;
			
			Fp = new FileWriter(file);
			
			System.out.println(filePath);
			
			Connection con=ConnectionManager.getConnection();
			Statement st1=con.createStatement();
			
		String query=""
				+ "SELECT Sum(p.INP_AMT) AS net, "
				+ "       t.ACNO "
				+ "FROM   paytran_stage p, "
				+ "       emptran t, "
				+ "       empmast e "
				+ "WHERE "
				+ "        p.trncd = 220 "
				+ "        AND p.trndt ='"+ReportDAO.EOM(Date)+"' "
				+ "       AND e.empno = p.empno "
				+ "       AND e.empno = t.empno "
				+ "       AND t.acno IS NOT NULL "
				+ "       AND t.srno = (SELECT Max(srno) "
				+ "                     FROM   emptran T1 "
				+ "                     WHERE  T1.empno = e.empno) "
				+ "       "
				+ "       AND ( ( e.status = 'A' "
				+ "               AND e.doj <= '"+ReportDAO.EOM(Date)+"' ) "
				+ "              OR ( e.status = 'N' "
				+ "                   AND e.dol >= '"+ReportDAO.EOM(Date)+"' ) )  "
				+ "GROUP  BY t.acno "
				+ "ORDER  BY t.acno";
			
			System.out.println(query);
			ResultSet rs= st1.executeQuery(query);
			float AcNum=0;
			while(rs.next())
			{
				
				
				AcNum = rs.getFloat(1);
				if(AcNum == 12.0){
					Fp.write("000001SB               "+(rs.getString(2))+"0000000000000"+(rs.getString(1))+"    D");
					Fp.write("\n");
				}
				else if(AcNum == 342.0){
					Fp.write("000001SB               "+(rs.getString(2))+"000000000000"+(rs.getString(1))+"    D");
					Fp.write("\n");
				}
				
	//			Fp.write("000001SB    "+SequenceFileDAO.leftpadblank(rs.getString(2))+""+SequenceFileDAO.leftpaddedzero1(rs.getString(1))+"");
				
			}
			Fp.close();
			
			flag=true;
		
		
	}
	catch(Exception e)
	{
		System.out.println("Error in make_prn_file Function "+e);
		e.printStackTrace();
		flag=false;
	}
	return flag;
	
	}
public boolean generateADVFST(String trncd, String filePath, String Date,String branch,String subsys) 
{System.out.println("AJ:   generateADVFST");
	boolean flag=false;
	try
	{
	 File file = new File(filePath);
		FileWriter Fp;
		
		Fp = new FileWriter(file);
		
		System.out.println(filePath);
		
		Connection con=ConnectionManager.getConnection();
		Statement st1=con.createStatement();
		 
			String query="select SUM(p.amount) as net, p.ac_no from DEDMAST p,EMPTRAN T,empmast E,paytran_stage py    where " +
					" p.trncd="+trncd+" and " +
					" py.trncd="+trncd+" and  py.NET_AMT !=0 and " +
					"p.EMPNO=T.EMPNO and E.EMPNO = T.EMPNO  AND py.empno = T.empno  AND py.TRNDT = '"+ReportDAO.EOM(Date)+"'  and t.acno is not null   AND  p.ACTYN='Y' AND " +
					"T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) and p.REPAY_START <='"+ReportDAO.EOM(Date)+"'  " +
					"AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Date)+"') or (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Date)+"' )) " +
					"group by p.ac_no order by p.AC_NO";
		System.out.println(query);
		ResultSet rs= st1.executeQuery(query);
		float AcNum = 0;
		double value= 0;
        int val=0;
		while(rs.next())
		{
			
			val = String.valueOf(rs.getInt(1)).length();
	
			value = rs.getFloat(1);

			if(val == 1){
				Fp.write("000001"+""+subsys+"-S         "+(rs.getString(2))+"     000000000000"+(rs.getString(1))+"");
				Fp.write("\n");
			}
			else if(val == 2){
				Fp.write("000001"+""+subsys+"-S         "+(rs.getString(2))+"     00000000000"+(rs.getString(1))+"");
				Fp.write("\n");
			}
			else if(val == 3){
				Fp.write("000001"+""+subsys+"-S         "+(rs.getString(2))+"     0000000000"+(rs.getString(1))+"");
				Fp.write("\n");
			}
			else if(val == 4){
				Fp.write("000001"+""+subsys+"-S         "+(rs.getString(2))+"     000000000"+(rs.getString(1))+"");
				Fp.write("\n");
			}
		}
		Fp.close();
		
		flag=true;
	}
	catch(Exception e)
	{
		System.out.println("Error in make_prn_file Function "+e);
		e.printStackTrace();
		flag=false;
	}
	
	
	return flag;
}
}

