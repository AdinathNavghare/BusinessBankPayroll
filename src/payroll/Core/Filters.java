package payroll.Core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import payroll.DAO.ConnectionManager;
import payroll.Model.FilterValues;

public class Filters {

	public ArrayList<FilterValues> getAlphabeticalList(String key, String forWhat,String date)
	{
		
		//date="Jan-2015";
		
		String Full_Date="25-"+date;  // full_date ="25-Jan-2015";
		
		ArrayList<FilterValues> result = new ArrayList<FilterValues>();
		String sql ="";
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			if(forWhat.equalsIgnoreCase("toFinalizeCal"))
			{
				//DATEPART(mm,  E.DOL)>=DATEPART(mm,CONVERT(datetime,  REPLACE('"+date+"', '-', ' '), 106))
				
				sql="SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+E.MNAME) AS NAME,E.EMPCODE FROM EMPMAST E,SAL_DETAILS S " +
					" WHERE E.LNAME LIKE '"+key+"%' AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					" (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND (S.SAL_STATUS='PROCESSED' ) AND S.SAL_MONTH='"+date+"' AND E.EMPNO=S.EMPNO and E.EMPNO not in(select distinct empno from onhold where status='H') ORDER BY NAME";
			}
			else if(forWhat.equalsIgnoreCase("toPaySlip"))
			{
				sql="SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+E.MNAME) AS NAME,E.EMPCODE	FROM EMPMAST E,SAL_DETAILS S " +
					" WHERE E.LNAME LIKE '"+key+"%' AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					" (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  (S.SAL_STATUS='PROCESSED' OR S.SAL_STATUS='NEGATIVE' OR S.SAL_STATUS='FINALIZED') AND S.SAL_MONTH='"+date+"' AND E.EMPNO=S.EMPNO ORDER BY NAME";
				
			}
			
			else if(forWhat.equalsIgnoreCase("deduction"))
			{
				sql="SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+E.MNAME) AS NAME,E.EMPCODE	FROM EMPMAST E,SAL_DETAILS S " +
					" WHERE E.LNAME LIKE '"+key+"%' AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					" (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  (S.SAL_STATUS='PROCESSED' OR S.SAL_STATUS='NEGATIVE' OR S.SAL_STATUS='AutoInst') AND S.SAL_MONTH='"+date+"' AND E.EMPNO=S.EMPNO ORDER BY NAME";
				
			}
			
			else if(forWhat.equalsIgnoreCase("toPayCal"))
			{
				
				/*sql="SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+E.MNAME) AS NAME,E.EMPCODE	FROM EMPMAST E " +
						" WHERE E.LNAME LIKE '"+key+"%' AND E.STATUS='A' AND E.EMPNO NOT IN (SELECT S.EMPNO FROM SAL_DETAILS S WHERE S.SAL_STATUS = 'FINALIZED' AND S.SAL_MONTH='"+date+"') ORDER BY NAME";*/
			
				/*sql="SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+E.MNAME) AS NAME, "+
						"E.EMPCODE	FROM EMPMAST E  WHERE E.LNAME LIKE '"+key+"%' AND E.STATUS='A' AND "+
						""+getMonth(date)+" >= DATEPART(MONTH, doj) and  "+getYear(date)+" >= datepart(year,doj) and "+
						"E.EMPNO NOT IN (SELECT S.EMPNO FROM SAL_DETAILS S WHERE S.SAL_STATUS = 'FINALIZED' "+ 
						"AND S.SAL_MONTH='"+date+"') ORDER BY NAME";*/ 
				/*sql= "SELECT E.EMPNO, CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+E.MNAME) AS NAME, E.EMPCODE, E.DOJ FROM EMPMAST E " +
						" WHERE E.LNAME LIKE '"+key+"%' AND E.STATUS='A' AND E.EMPNO NOT IN (SELECT S.EMPNO FROM SAL_DETAILS S " +
						"WHERE S.SAL_STATUS = 'FINALIZED' AND S.SAL_MONTH='"+date+"' ) AND " +
						"E.DOJ <= dateadd(dd,-(DAY(DATEADD(mm,1,CONVERT(datetime,  REPLACE('"+date+"', '-', ' '), 106)))), " +
						"DATEADD(mm,1,CONVERT(datetime,  REPLACE('"+date+"', '-', ' '), 106))) ORDER BY NAME";*/                 
				/*
				 (E.STATUS ='N' And DATEPART(year,  E.DOL)>=DATEPART(year,CONVERT(datetime,  REPLACE('"+date+"', '-', ' '), 106)) " +
					" And DATEPART(mm,  E.DOL)>=DATEPART(mm,CONVERT(datetime,  REPLACE('"+date+"', '-', ' '), 106))) 
				 
				*/
				
				
				
				sql="SELECT E.EMPNO, CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+E.MNAME) AS NAME, E.EMPCODE FROM EMPMAST E " +
					"WHERE E.LNAME LIKE '"+key+"%' AND E.EMPNO NOT IN (SELECT S.EMPNO FROM SAL_DETAILS S WHERE S.SAL_STATUS = 'FINALIZED' AND S.SAL_MONTH='"+date+"' ) " +
					"AND(( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					" (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' ))" +
							" and E.empno in (select Distinct empno from paytran where trndt between '"+ReportDAO.BOM(Full_Date)+"' and '"+ReportDAO.EOM(Full_Date)+"' ) ORDER BY NAME";
					 
				//System.out.println("---------"+sql);	 
					 
			
			}
			else if(forWhat.equalsIgnoreCase("toPostBonus"))
			{
				sql="SELECT E.EMPNO, CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+E.MNAME) AS NAME, E.EMPCODE FROM EMPMAST E " +
					"WHERE E.LNAME LIKE '"+key+"%' AND E.EMPNO NOT IN (SELECT S.EMPNO FROM SAL_DETAILS S WHERE S.SAL_STATUS = 'FINALIZED' AND S.SAL_MONTH='"+date+"' ) " +
					"AND(( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					" (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' ))" +
							" and E.empno in (select Distinct empno from bonustran where  STATUS ='P' and trndt between '"+ReportDAO.BOM(Full_Date)+"' and '"+ReportDAO.EOM(Full_Date)+"' ) ORDER BY NAME";
			
			}
			
			
			else if(forWhat.equalsIgnoreCase("lateMarkPost"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
						" FROM EMPMAST E,EMPTRAN T  WHERE E.EMPNO = T.EMPNO AND E.LNAME LIKE '"+key+"%'   " +
								" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) " +
						" and E.EMPNO  in (select distinct Empno from Emp_attendance S " +
						"where S.ATTD_DATE between '"+ReportDAO.BOM(Full_Date)+"' and	'"+ReportDAO.EOM(Full_Date)+"' and s.emp_status is null )" +
			" ORDER BY E.EMPNO";
			}
			else
			{	
				sql = "SELECT EMPNO,CONVERT(NVARCHAR(100),LNAME+' '+ FNAME+' '+MNAME) AS NAME,EMPCODE FROM EMPMAST WHERE LNAME LIKE '"+key+"%' AND STATUS='A' ORDER BY NAME";
			}
			ResultSet rs = st.executeQuery(sql);
			FilterValues vals = null;
			while(rs.next())
			{
				vals = new FilterValues();
				vals.setEMPNO(rs.getInt(1));
				vals.setNAME(rs.getString(2));
				vals.setEMPCODE(rs.getString(3));
				result.add(vals);
			}
			con.close();
			rs.close();
			st.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<FilterValues> getDeptWiseList(int key, String forWhat,String date)
	{
		ArrayList<FilterValues> result = new ArrayList<FilterValues>();
		String sql = "";
		//date="Jan-2015";
		
				String Full_Date="25-"+date;  // full_date ="25-Jan-2015";
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			if(forWhat.equalsIgnoreCase("toFinalizeCal"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE" +
					" FROM EMPMAST E,EMPTRAN T,SAL_DETAILS S  WHERE E.EMPNO = T.EMPNO AND T.DEPT = "+key+
					" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)" +
					" AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					" (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  (S.SAL_STATUS='PROCESSED' ) AND S.SAL_MONTH='"+date+"' AND E.EMPNO=S.EMPNO and E.EMPNO not in(select distinct empno from onhold where status='H') ORDER BY E.EMPNO ";
			}
			else if(forWhat.equalsIgnoreCase("toPaySlip"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE" +
					" FROM EMPMAST E,EMPTRAN T,SAL_DETAILS S  WHERE E.EMPNO = T.EMPNO AND T.DEPT = "+key+
					" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)" +
					" AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					" (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' ))AND  (S.SAL_STATUS='PROCESSED' OR S.SAL_STATUS='NEGATIVE' OR S.SAL_STATUS='FINALIZED') AND S.SAL_MONTH='"+date+"' AND E.EMPNO=S.EMPNO ORDER BY E.EMPNO ";
			}
			
			else if(forWhat.equalsIgnoreCase("deduction"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE" +
					" FROM EMPMAST E,EMPTRAN T,SAL_DETAILS S  WHERE E.EMPNO = T.EMPNO AND T.DEPT = "+key+
					" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)" +
					" AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					" (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' ))AND  (S.SAL_STATUS='PROCESSED' OR S.SAL_STATUS='NEGATIVE' OR S.SAL_STATUS='AutoInst') AND S.SAL_MONTH='"+date+"' AND E.EMPNO=S.EMPNO ORDER BY E.EMPNO ";
			}
			else if(forWhat.equalsIgnoreCase("toPayCal"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
						" FROM EMPMAST E,EMPTRAN T  WHERE E.EMPNO = T.EMPNO AND T.DEPT ="+key+
						" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)"+
						" AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					" (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  E.EMPNO not in (select Empno from sal_details S where S.SAL_STATUS = 'FINALIZED' AND S.SAL_MONTH='"+date+"')"+
						"  and E.empno in (select Distinct empno from paytran where trndt between '"+ReportDAO.BOM(Full_Date)+"' and '"+ReportDAO.EOM(Full_Date)+"' )  ORDER BY E.EMPNO";
			}
			
			else if(forWhat.equalsIgnoreCase("toPostBonus"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
						" FROM EMPMAST E,EMPTRAN T  WHERE E.EMPNO = T.EMPNO AND T.DEPT ="+key+
						" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)"+
						" AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					" (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  E.EMPNO not in (select Empno from sal_details S where S.SAL_STATUS = 'FINALIZED' AND S.SAL_MONTH='"+date+"')"+
						"  and E.empno in (select Distinct empno from Bonustran where status ='P' and trndt between '"+ReportDAO.BOM(Full_Date)+"' and '"+ReportDAO.EOM(Full_Date)+"' )  ORDER BY E.EMPNO";
			}
			else if(forWhat.equalsIgnoreCase("lateMarkPost"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
						" FROM EMPMAST E,EMPTRAN T  WHERE E.EMPNO = T.EMPNO AND T.DEPT ="+key+
						" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) " +
						" and E.EMPNO  in (select distinct Empno from Emp_attendance" +
			"S where S.ATTD_DATE between '"+ReportDAO.BOM(Full_Date)+"' and	'"+ReportDAO.EOM(Full_Date)+"' and s.emp_status is null )" +
			" ORDER BY E.EMPNO";
			}
			
			else
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
							" FROM EMPMAST E,EMPTRAN T "+
								" WHERE E.EMPNO = T.EMPNO AND T.DEPT = "+key+
									" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) AND E.STATUS='A' ORDER BY E.EMPNO";
			}
			ResultSet rs = st.executeQuery(sql);
			FilterValues vals = null;
			while(rs.next())
			{
				vals = new FilterValues();
				vals.setEMPNO(rs.getInt(1));
				vals.setNAME(rs.getString(2));
				vals.setEMPCODE(rs.getString(3));
				result.add(vals);
			}
			con.close();
			rs.close();
			st.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<FilterValues> getDesigWiseList(int key,String forWhat,String date)
	{
		ArrayList<FilterValues> result = new ArrayList<FilterValues>();
		String sql = "";
		//date="Jan-2015";
		
				String Full_Date="25-"+date;  // full_date ="25-Jan-2015";
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			if(forWhat.equalsIgnoreCase("toFinalizeCal"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE" +
					" FROM EMPMAST E,EMPTRAN T,SAL_DETAILS S  WHERE E.EMPNO = T.EMPNO AND T.DESIG = "+key+
					" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)" +
					" AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					" (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  (S.SAL_STATUS='PROCESSED') AND S.SAL_MONTH='"+date+"' AND E.EMPNO=S.EMPNO and E.EMPNO not in(select distinct empno from onhold where status='H') ORDER BY E.EMPNO";
			}
			else if(forWhat.equalsIgnoreCase("toPaySlip"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE" +
					" FROM EMPMAST E,EMPTRAN T,SAL_DETAILS S  WHERE E.EMPNO = T.EMPNO AND T.DESIG = "+key+
					" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)" +
					" AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					" (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  (S.SAL_STATUS='PROCESSED' OR S.SAL_STATUS='NEGATIVE' OR S.SAL_STATUS='AutoInst') AND S.SAL_MONTH='"+date+"' AND E.EMPNO=S.EMPNO ORDER BY E.EMPNO";
			}
			
			else if(forWhat.equalsIgnoreCase("deduction"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE" +
					" FROM EMPMAST E,EMPTRAN T,SAL_DETAILS S  WHERE E.EMPNO = T.EMPNO AND T.DESIG = "+key+
					" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)" +
					" AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					" (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  (S.SAL_STATUS='PROCESSED' OR S.SAL_STATUS='NEGATIVE' OR S.SAL_STATUS='FINALIZED') AND S.SAL_MONTH='"+date+"' AND E.EMPNO=S.EMPNO ORDER BY E.EMPNO";
			}
			else if(forWhat.equalsIgnoreCase("toPayCal"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
						" FROM EMPMAST E,EMPTRAN T  WHERE E.EMPNO = T.EMPNO AND T.DESIG ="+key+
						" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)"+
						" AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					"(E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  E.EMPNO not in (select Empno from sal_details S where S.SAL_STATUS = 'FINALIZED' AND S.SAL_MONTH='"+date+"')"+
						"   and E.empno in (select Distinct empno from paytran where trndt between '"+ReportDAO.BOM(Full_Date)+"' and '"+ReportDAO.EOM(Full_Date)+"' )   ORDER BY E.EMPNO";
			}
			
			else if(forWhat.equalsIgnoreCase("toPostBonus"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
						" FROM EMPMAST E,EMPTRAN T  WHERE E.EMPNO = T.EMPNO AND T.DESIG ="+key+
						" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)"+
						" AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					"(E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  E.EMPNO not in (select Empno from sal_details S where S.SAL_STATUS = 'FINALIZED' AND S.SAL_MONTH='"+date+"')"+
						"   and E.empno in (select Distinct empno from Bonustran where status ='P' and trndt between '"+ReportDAO.BOM(Full_Date)+"' and '"+ReportDAO.EOM(Full_Date)+"' )   ORDER BY E.EMPNO";
			}
			
			
			else if(forWhat.equalsIgnoreCase("lateMarkPost"))
			{
				sql="		SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE" +
						" FROM EMPMAST E,EMPTRAN T  WHERE E.EMPNO = T.EMPNO AND T.DESIG ="+key+" " +
								"AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) " +
								"AND  E.EMPNO  in (select distinct Empno from Emp_attendance" +
								"S where S.ATTD_DATE between '"+ReportDAO.BOM(Full_Date)+"' and	'"+ReportDAO.EOM(Full_Date)+"' and s.emp_status is null )" +
								" ORDER BY E.EMPNO";
			}
			
			else
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
							" FROM EMPMAST E,EMPTRAN T "+
								" WHERE E.EMPNO = T.EMPNO AND T.DESIG = "+key+
									" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) AND E.STATUS='A'  ORDER BY E.EMPNO";
			}
			ResultSet rs = st.executeQuery(sql);
			FilterValues vals = null;
			while(rs.next())
			{
				vals = new FilterValues();
				vals.setEMPNO(rs.getInt(1));
				vals.setNAME(rs.getString(2));
				vals.setEMPCODE(rs.getString(3));
				result.add(vals);
			}
			con.close();
			rs.close();
			st.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<FilterValues> getGradeWiseList(int key,String forWhat,String date)
	{
		ArrayList<FilterValues> result = new ArrayList<FilterValues>();
		String sql = "";
		//date="Jan-2015";
		
				String Full_Date="25-"+date;  // full_date ="25-Jan-2015";
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			FilterValues vals = null;
			ResultSet rst = st.executeQuery("select lkp_srno from LOOKUP where LKP_CODE = 'desig' and LKP_RECR = "+key);
			while(rst.next()){
				
				if(forWhat.equalsIgnoreCase("toFinalizeCal"))
				{
					sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE" +
						" FROM EMPMAST E,EMPTRAN T,SAL_DETAILS S  WHERE E.EMPNO = T.EMPNO AND T.DESIG = "+rst.getInt("LKP_SRNO")+
						" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)" +
						" AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					"(E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  (S.SAL_STATUS='PROCESSED') AND S.SAL_MONTH='"+date+"' AND E.EMPNO=S.EMPNO and E.EMPNO not in(select distinct empno from onhold where status='H') ORDER BY E.EMPNO";
				}
				else if(forWhat.equalsIgnoreCase("toPaySlip"))
				{
					sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE" +
						" FROM EMPMAST E,EMPTRAN T,SAL_DETAILS S  WHERE E.EMPNO = T.EMPNO AND T.DESIG = "+rst.getInt("LKP_SRNO")+
						" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)" +
						" AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					"(E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  (S.SAL_STATUS='PROCESSED' OR S.SAL_STATUS='NEGATIVE' OR S.SAL_STATUS='FINALIZED') AND S.SAL_MONTH='"+date+"' AND E.EMPNO=S.EMPNO ORDER BY E.EMPNO";
				}
				
				
				else if(forWhat.equalsIgnoreCase("deduction"))
				{
					sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE" +
						" FROM EMPMAST E,EMPTRAN T,SAL_DETAILS S  WHERE E.EMPNO = T.EMPNO AND T.DESIG = "+rst.getInt("LKP_SRNO")+
						" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)" +
						" AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					"(E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  (S.SAL_STATUS='PROCESSED' OR S.SAL_STATUS='NEGATIVE' OR S.SAL_STATUS='AutoInst') AND S.SAL_MONTH='"+date+"' AND E.EMPNO=S.EMPNO ORDER BY E.EMPNO";
				}
				
				else if(forWhat.equalsIgnoreCase("lateMarkPost"))
				{
					sql = "SELECT distinct E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE" +
							" FROM EMPMAST E,EMPTRAN T,Emp_attendance S  WHERE E.EMPNO = T.EMPNO AND T.DESIG = "+rst.getInt("LKP_SRNO")+
							" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)" +
							"   AND S.ATTD_DATE between  '"+ReportDAO.BOM(Full_Date)+"' and '"+ReportDAO.EOM(Full_Date)+"' AND E.EMPNO=S.EMPNO and s.emp_status is null ORDER BY E.EMPNO";
				
				}
				
				else if(forWhat.equalsIgnoreCase("toPayCal"))
				{
					sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
							" FROM EMPMAST E,EMPTRAN T  WHERE E.EMPNO = T.EMPNO AND T.DESIG ="+rst.getInt("LKP_SRNO")+
							" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)"+
							" AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					"(E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  E.EMPNO not in (select Empno from sal_details S where S.SAL_STATUS = 'FINALIZED' AND S.SAL_MONTH='"+date+"')"+
							"   and E.empno in (select Distinct empno from paytran where trndt between '"+ReportDAO.BOM(Full_Date)+"' and '"+ReportDAO.EOM(Full_Date)+"' )   ORDER BY E.EMPNO";
				}
				
				else if(forWhat.equalsIgnoreCase("toPostBonus"))
				{
					sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
							" FROM EMPMAST E,EMPTRAN T  WHERE E.EMPNO = T.EMPNO AND T.DESIG ="+rst.getInt("LKP_SRNO")+
							" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)"+
							" AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					"(E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  E.EMPNO not in (select Empno from sal_details S where S.SAL_STATUS = 'FINALIZED' AND S.SAL_MONTH='"+date+"')"+
							"   and E.empno in (select Distinct empno from bonustran where status = 'P' and trndt between '"+ReportDAO.BOM(Full_Date)+"' and '"+ReportDAO.EOM(Full_Date)+"' )   ORDER BY E.EMPNO";
				}
				else
				{
					sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE FROM EMPMAST E,EMPTRAN T "+
							" WHERE E.EMPNO = T.EMPNO AND T.DESIG = "+rst.getInt("LKP_SRNO")+
							" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) AND E.STATUS='A'  ORDER BY E.EMPNO";
				}
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				while(rs.next()) {
					vals = new FilterValues();
					vals.setEMPNO(rs.getInt(1));
					vals.setNAME(rs.getString(2));
					vals.setEMPCODE(rs.getString(3));
					result.add(vals);
				}
				rs.close();
				stmt.close();
			}
			st.close();
			con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<FilterValues> getProjectWiseList(int key,String forWhat,String date)
	{
		ArrayList<FilterValues> result = new ArrayList<FilterValues>();
		String sql = "";
		//date="Jan-2015";
		
				String Full_Date="25-"+date;  // full_date ="25-Jan-2015";
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			if(forWhat.equalsIgnoreCase("toFinalizeCal"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE" +
					" FROM EMPMAST E,EMPTRAN T,SAL_DETAILS S  WHERE E.EMPNO = T.EMPNO AND T.PRJ_SRNO = '"+key+"'"+
					" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)" +
					" AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					" (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND (S.SAL_STATUS='PROCESSED') AND S.SAL_MONTH='"+date+"' AND E.EMPNO=S.EMPNO and E.EMPNO not in(select distinct empno from onhold where status='H') ORDER BY E.EMPNO";
			}
			else if(forWhat.equalsIgnoreCase("toPaySlip"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE" +
					" FROM EMPMAST E,EMPTRAN T,SAL_DETAILS S  WHERE E.EMPNO = T.EMPNO AND T.PRJ_SRNO = '"+key+"'"+
					" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)" +
					" AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					"(E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  (S.SAL_STATUS='PROCESSED' or S.SAL_STATUS='NEGATIVE' OR S.SAL_STATUS='FINALIZED') AND S.SAL_MONTH='"+date+"' AND E.EMPNO=S.EMPNO ORDER BY E.EMPNO";
			}
			
			else if(forWhat.equalsIgnoreCase("deduction"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE" +
					" FROM EMPMAST E,EMPTRAN T,SAL_DETAILS S  WHERE E.EMPNO = T.EMPNO AND T.PRJ_SRNO = '"+key+"'"+
					" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)" +
					" AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					"(E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  (S.SAL_STATUS='PROCESSED' or S.SAL_STATUS='NEGATIVE' OR S.SAL_STATUS='AutoInst') AND S.SAL_MONTH='"+date+"' AND E.EMPNO=S.EMPNO ORDER BY E.EMPNO";
			}
			else if(forWhat.equalsIgnoreCase("toPayCal"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
						" FROM EMPMAST E,EMPTRAN T  WHERE E.EMPNO = T.EMPNO AND T.PRJ_SRNO ="+key+
						" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)"+
						" AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					" (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  E.EMPNO not in (select Empno from sal_details S where S.SAL_STATUS = 'FINALIZED' AND S.SAL_MONTH='"+date+"')"+
						"   and E.empno in (select Distinct empno from paytran where trndt between '"+ReportDAO.BOM(Full_Date)+"' and '"+ReportDAO.EOM(Full_Date)+"' )   ORDER BY E.EMPNO";
			}
			else if(forWhat.equalsIgnoreCase("toPostBonus"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
						" FROM EMPMAST E,EMPTRAN T  WHERE E.EMPNO = T.EMPNO AND T.PRJ_SRNO ="+key+
						" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)"+
						" AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					" (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  E.EMPNO not in (select Empno from sal_details S where S.SAL_STATUS = 'FINALIZED' AND S.SAL_MONTH='"+date+"')"+
						"   and E.empno in (select Distinct empno from BONUSTRAN where status ='P' and trndt between '"+ReportDAO.BOM(Full_Date)+"' and '"+ReportDAO.EOM(Full_Date)+"' )   ORDER BY E.EMPNO";
			}
			else if(forWhat.equalsIgnoreCase("lateMarkPost"))
			{
			sql="SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE " +
					"FROM EMPMAST E,EMPTRAN T  WHERE E.EMPNO = T.EMPNO AND T.PRJ_SRNO ="+key+" " +
					"AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) AND  E.EMPNO  in " +
					" (select distinct Empno from EMP_ATTENDANCE S where S.ATTD_DATE between '"+ReportDAO.BOM(Full_Date)+"' and '"+ReportDAO.EOM(Full_Date)+"' and s.emp_status is null)";
			}
			else
			{	
				sql = " SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
							" FROM EMPMAST E,EMPTRAN T "+
								" WHERE E.EMPNO = T.EMPNO AND T.PRJ_SRNO = '"+key+"'"+
									" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) AND E.STATUS='A'  ORDER BY E.EMPNO";
			}
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);
			FilterValues vals = null;
			while(rs.next())
			{
				vals = new FilterValues();
				vals.setEMPNO(rs.getInt(1));
				vals.setNAME(rs.getString(2));
				vals.setEMPCODE(rs.getString(3));
				result.add(vals);
			}
			con.close();
			rs.close();
			st.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<FilterValues> getAllEmpList(String forWhat,String date)
	{
		ArrayList<FilterValues> result = new ArrayList<FilterValues>();
		String sql = "";
		//date="Jan-2015";
		
				String Full_Date="25-"+date;  // full_date ="25-Jan-2015";
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			if(forWhat.equalsIgnoreCase("toFinalizeCal"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE" +
					" FROM EMPMAST E,SAL_DETAILS S WHERE (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					" (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND (S.SAL_STATUS='PROCESSED') AND S.SAL_MONTH='"+date+"' AND E.EMPNO=S.EMPNO and E.EMPNO not in(select distinct empno from onhold where status='H') ORDER BY E.EMPCODE";
			}
			else if(forWhat.equalsIgnoreCase("toPaySlip"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE" +
					" FROM EMPMAST E,SAL_DETAILS S WHERE (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					"(E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  (S.SAL_STATUS='PROCESSED' OR S.SAL_STATUS='NEGATIVE' OR S.SAL_STATUS='FINALIZED') AND S.SAL_MONTH='"+date+"' AND E.EMPNO=S.EMPNO ORDER BY E.EMPCODE";
			}
			
			else if(forWhat.equalsIgnoreCase("deduction"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE" +
					" FROM EMPMAST E,SAL_DETAILS S WHERE (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					"(E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  (S.SAL_STATUS='PROCESSED' OR S.SAL_STATUS='NEGATIVE' OR S.SAL_STATUS='AutoInst') AND S.SAL_MONTH='"+date+"' AND E.EMPNO=S.EMPNO ORDER BY E.EMPCODE";
			}
			else if(forWhat.equalsIgnoreCase("toPayCal"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE" +
					" FROM EMPMAST E WHERE (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					" (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  E.EMPNO not in " +
					" (select Empno from sal_details S where S.SAL_STATUS = 'FINALIZED' AND S.SAL_MONTH='"+date+"')" +
							"   and E.empno in (select Distinct empno from paytran where trndt between '"+ReportDAO.BOM(Full_Date)+"' and '"+ReportDAO.EOM(Full_Date)+"' )   ORDER BY E.EMPCODE";
			}
			
			else if(forWhat.equalsIgnoreCase("toPostBonus"))
			{
				/*sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE" +
					" FROM EMPMAST E WHERE (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					" (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  E.EMPNO not in " +
					" (select Empno from sal_details S where S.SAL_STATUS = 'FINALIZED' AND S.SAL_MONTH='"+date+"')" +
							"   and E.empno in (select Distinct empno from bonustran where status ='p' and trndt between '"+ReportDAO.BOM(Full_Date)+"' and '"+ReportDAO.EOM(Full_Date)+"' )   ORDER BY E.EMPCODE";*/
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE" +
						" FROM EMPMAST E WHERE "+
								"   E.empno in (select Distinct empno from bonustran where status ='p' )" ;
			}
			
			else if(forWhat.equalsIgnoreCase("lateMarkPost"))
			{
				
				sql="SELECT Distinct E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE " +
						"FROM EMPMAST E,EMP_ATTENDANCE S WHERE " +
						" S.ATTD_DATE between '"+ReportDAO.BOM(Full_Date)+"' and '"+ReportDAO.EOM(Full_Date)+"' AND E.EMPNO=S.EMPNO" +
						" and S.EMP_STATUS is null ORDER BY E.EMPCODE";
			}
				
		/*	else if(forWhat.equalsIgnoreCase("toCTC_Report"))
			{
				Full_Date="31-Jan-2016"; 
				
				sql = "SELECT distinct E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE FROM EMPMAST E , " +
						"EMPTRAN T  WHERE  E.empno in (select Distinct empno from paytran_stage where trndt = '31-dec-2015' )  ORDER BY E.EMPNO";
			}*/
			
			
			else if(forWhat.equalsIgnoreCase("Leave"))
			{	
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
							" FROM EMPMAST E WHERE E.STATUS='A' and DOJ <= '"+ReportDAO.getPastdate()+"' and empno not in(select distinct empno from leavebal where baldt between '"+ReportDAO.BOYForJanToDec(date)+"' and '"+ReportDAO.EOYForJanToDec(date)+"') ORDER BY E.EMPCODE";
				
			}
			
			else
			{	
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
							" FROM EMPMAST E WHERE E.STATUS='A' ORDER BY E.EMPCODE";
			}
			System.out.println("selecting employee----"+sql);
			ResultSet rs = st.executeQuery(sql);
			FilterValues vals = null;
			while(rs.next())
			{
				vals = new FilterValues();
				vals.setEMPNO(rs.getInt(1));
				vals.setNAME(rs.getString(2));
				vals.setEMPCODE(rs.getString(3));
				result.add(vals);
			}
			con.close();
			rs.close();
			st.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//System.out.println(sql);
		return result;
	}
	
	
	//here these methods are for taxreport filters only
	public ArrayList<FilterValues> getAlphabeticalListtaxreport(String key, String forWhat,String date,String type)
	{
		
		//date="Jan-2015";
		
		String Full_Date="25-"+date;  // full_date ="25-Jan-2015";
		
		ArrayList<FilterValues> result = new ArrayList<FilterValues>();
		String sql ="";
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			if(forWhat.equalsIgnoreCase("toIncometaxreport"))
			{	
				if(type.equalsIgnoreCase("PrintTaxSheet"))
				{
					sql = "SELECT EMPNO,CONVERT(NVARCHAR(100),LNAME+' '+ FNAME+' '+MNAME) AS NAME,EMPCODE FROM EMPMAST WHERE LNAME LIKE '"+key+"%' AND (STATUS='A' OR DOL BETWEEN '"+ReportDAO.BoFinancialy(Full_Date)+"' AND  '"+ReportDAO.EoFinancialy(Full_Date)+"' )  ORDER BY NAME";
				}
				else if(type.equalsIgnoreCase("F16"))
				{
					sql = "SELECT EMPNO,CONVERT(NVARCHAR(100),LNAME+' '+ FNAME+' '+MNAME) AS NAME,EMPCODE FROM EMPMAST WHERE LNAME LIKE '"+key+"%' AND EMPNO NOT IN(select DISTINCT EMPNO from empmast where STATUS='N' and empno not in( "+
							" select distinct empno from ytdtran where  trncd=101) ) AND (STATUS='A' OR DOL>='"+ReportDAO.BoFinancialy(Full_Date)+"') ORDER BY NAME";
				}
				else
				{
					sql = "SELECT EMPNO,CONVERT(NVARCHAR(100),LNAME+' '+ FNAME+' '+MNAME) AS NAME,EMPCODE FROM EMPMAST WHERE LNAME LIKE '"+key+"%' AND (STATUS='A' OR DOL BETWEEN '"+ReportDAO.BoFinancialy(Full_Date)+"' AND  '"+ReportDAO.EoFinancialy(Full_Date)+"' ) AND EMPNO NOT IN(select DISTINCT EMPNO from empmast where STATUS='N' and empno not in( "+
							" select distinct empno from ytdtran where  trncd=101) ) AND (STATUS='A' OR DOL>='"+ReportDAO.BoFinancialy(Full_Date)+"') ORDER BY NAME";
				}
			}
			ResultSet rs = st.executeQuery(sql);
			FilterValues vals = null;
			while(rs.next())
			{
				vals = new FilterValues();
				vals.setEMPNO(rs.getInt(1));
				vals.setNAME(rs.getString(2));
				vals.setEMPCODE(rs.getString(3));
				result.add(vals);
			}
			con.close();
			rs.close();
			st.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<FilterValues> getDeptWiseListtaxreport(int key, String forWhat,String date,String type)
	{
		ArrayList<FilterValues> result = new ArrayList<FilterValues>();
		String sql = "";
		//date="Jan-2015";
		
				String Full_Date="25-"+date;  // full_date ="25-Jan-2015";
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			
			
			if(forWhat.equalsIgnoreCase("toIncometaxreport"))
			{
				if(type.equalsIgnoreCase("PrintTaxSheet"))
				{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
							" FROM EMPMAST E,EMPTRAN T "+
								" WHERE E.EMPNO = T.EMPNO AND T.DEPT = "+key+
									" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) AND (E.STATUS='A' OR E.DOL BETWEEN '"+ReportDAO.BoFinancialy(Full_Date)+"' AND  '"+ReportDAO.EoFinancialy(Full_Date)+"' ) ORDER BY E.EMPCODE";
				}
				else if(type.equalsIgnoreCase("F16"))
				{
					sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
							" FROM EMPMAST E,EMPTRAN T "+
								" WHERE E.EMPNO = T.EMPNO AND T.DEPT = "+key+
									" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) AND E.EMPNO NOT IN(select DISTINCT EMPNO from empmast where STATUS='N' and empno not in( "+
							" select distinct empno from ytdtran where  trncd=101) )  AND (E.STATUS='A' OR E.DOL>='"+ReportDAO.BoFinancialy(Full_Date)+"') ORDER BY E.EMPCODE";
				
				}
				else
				{
					sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
							" FROM EMPMAST E,EMPTRAN T "+
								" WHERE E.EMPNO = T.EMPNO AND T.DEPT = "+key+
									" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) AND (E.STATUS='A' OR E.DOL BETWEEN '"+ReportDAO.BoFinancialy(Full_Date)+"' AND  '"+ReportDAO.EoFinancialy(Full_Date)+"' )  AND E.EMPNO NOT IN(select DISTINCT EMPNO from empmast where STATUS='N' and empno not in( "+
							" select distinct empno from ytdtran where  trncd=101) )  AND (E.STATUS='A' OR E.DOL>='"+ReportDAO.BoFinancialy(Full_Date)+"') ORDER BY E.EMPCODE";
				
				}
			}
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);
			FilterValues vals = null;
			while(rs.next())
			{
				vals = new FilterValues();
				vals.setEMPNO(rs.getInt(1));
				vals.setNAME(rs.getString(2));
				vals.setEMPCODE(rs.getString(3));
				result.add(vals);
			}
			con.close();
			rs.close();
			st.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<FilterValues> getDesigWiseListtaxreport(int key,String forWhat,String date,String type)
	{
		ArrayList<FilterValues> result = new ArrayList<FilterValues>();
		String sql = "";
		//date="Jan-2015";
		
				String Full_Date="25-"+date;  // full_date ="25-Jan-2015";
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			if(forWhat.equalsIgnoreCase("toIncometaxreport"))
			{
				if(type.equalsIgnoreCase("PrintTaxSheet"))
				{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
							" FROM EMPMAST E,EMPTRAN T "+
								" WHERE E.EMPNO = T.EMPNO AND T.DESIG = "+key+
									" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) AND (E.STATUS='A' OR E.DOL BETWEEN '"+ReportDAO.BoFinancialy(Full_Date)+"' AND  '"+ReportDAO.EoFinancialy(Full_Date)+"' )  ORDER BY E.EMPCODE";
				}
				else if(type.equalsIgnoreCase("F16"))
				{
					sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
							" FROM EMPMAST E,EMPTRAN T "+
								" WHERE E.EMPNO = T.EMPNO AND T.DESIG = "+key+
									" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) AND E.EMPNO NOT IN(select DISTINCT EMPNO from empmast where STATUS='N' and empno not in( "+
							" select distinct empno from ytdtran where  trncd=101) )  AND (E.STATUS='A' OR E.DOL>='"+ReportDAO.BoFinancialy(Full_Date)+"') ORDER BY E.EMPCODE";
				
				}
				else
				{
					sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
							" FROM EMPMAST E,EMPTRAN T "+
								" WHERE E.EMPNO = T.EMPNO AND T.DESIG = "+key+
									" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) AND (E.STATUS='A' OR E.DOL BETWEEN '"+ReportDAO.BoFinancialy(Full_Date)+"' AND  '"+ReportDAO.EoFinancialy(Full_Date)+"' )  AND E.EMPNO NOT IN(select DISTINCT EMPNO from empmast where STATUS='N' and empno not in( "+
							" select distinct empno from ytdtran where  trncd=101) )  AND (E.STATUS='A' OR E.DOL>='"+ReportDAO.BoFinancialy(Full_Date)+"') ORDER BY E.EMPCODE";
				
				}
			}
			ResultSet rs = st.executeQuery(sql);
			FilterValues vals = null;
			while(rs.next())
			{
				vals = new FilterValues();
				vals.setEMPNO(rs.getInt(1));
				vals.setNAME(rs.getString(2));
				vals.setEMPCODE(rs.getString(3));
				result.add(vals);
			}
			con.close();
			rs.close();
			st.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<FilterValues> getGradeWiseListtaxreport(int key,String forWhat,String date,String type)
	{
		ArrayList<FilterValues> result = new ArrayList<FilterValues>();
		String sql = "";
		//date="Jan-2015";
		
				String Full_Date="25-"+date;  // full_date ="25-Jan-2015";
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			FilterValues vals = null;
			ResultSet rst = st.executeQuery("select lkp_srno from LOOKUP where LKP_CODE = 'desig' and LKP_RECR = "+key);
			while(rst.next()){
				
				if(forWhat.equalsIgnoreCase("toIncometaxreport"))
				{
					if(type.equalsIgnoreCase("PrintTaxSheet"))
					{
					sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE FROM EMPMAST E,EMPTRAN T "+
							" WHERE E.EMPNO = T.EMPNO AND T.DESIG = "+rst.getInt("LKP_SRNO")+
							" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) AND (E.STATUS='A' OR E.DOL BETWEEN '"+ReportDAO.BoFinancialy(Full_Date)+"' AND  '"+ReportDAO.EoFinancialy(Full_Date)+"' )  ORDER BY E.EMPCODE";
					}
					else if(type.equalsIgnoreCase("F16"))
					{
						sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE FROM EMPMAST E,EMPTRAN T "+
								" WHERE E.EMPNO = T.EMPNO AND T.DESIG = "+rst.getInt("LKP_SRNO")+
								" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)  AND E.EMPNO NOT IN(select DISTINCT EMPNO from empmast where STATUS='N' and empno not in( "+
							" select distinct empno from ytdtran where  trncd=101) ) AND (E.STATUS='A' OR E.DOL>='"+ReportDAO.BoFinancialy(Full_Date)+"')  ORDER BY E.EMPCODE";
						
					}
					else
					{
						sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE FROM EMPMAST E,EMPTRAN T "+
								" WHERE E.EMPNO = T.EMPNO AND T.DESIG = "+rst.getInt("LKP_SRNO")+
								" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) AND (E.STATUS='A' OR E.DOL BETWEEN '"+ReportDAO.BoFinancialy(Full_Date)+"' AND  '"+ReportDAO.EoFinancialy(Full_Date)+"' )  AND E.EMPNO NOT IN(select DISTINCT EMPNO from empmast where STATUS='N' and empno not in( "+
							" select distinct empno from ytdtran where  trncd=101) )  AND (E.STATUS='A' OR E.DOL>='"+ReportDAO.BoFinancialy(Full_Date)+"') ORDER BY E.EMPCODE";
						
					}
				}
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				while(rs.next()) {
					vals = new FilterValues();
					vals.setEMPNO(rs.getInt(1));
					vals.setNAME(rs.getString(2));
					vals.setEMPCODE(rs.getString(3));
					result.add(vals);
				}
				rs.close();
				stmt.close();
			}
			st.close();
			con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<FilterValues> getProjectWiseListtaxreport(int key,String forWhat,String date,String type)
	{
		ArrayList<FilterValues> result = new ArrayList<FilterValues>();
		String sql = "";
		//date="Jan-2015";
		
				String Full_Date="25-"+date;  // full_date ="25-Jan-2015";
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			if(forWhat.equalsIgnoreCase("toIncometaxreport"))
			{	
				if(type.equalsIgnoreCase("PrintTaxSheet"))
				{
				sql = " SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
							" FROM EMPMAST E,EMPTRAN T "+
								" WHERE E.EMPNO = T.EMPNO AND T.PRJ_SRNO = '"+key+"'"+
									" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) AND (E.STATUS='A' OR E.DOL BETWEEN '"+ReportDAO.BoFinancialy(Full_Date)+"' AND  '"+ReportDAO.EoFinancialy(Full_Date)+"' )  ORDER BY E.EMPCODE";
				}
				else if(type.equalsIgnoreCase("F16"))
				{
					sql = " SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
							" FROM EMPMAST E,EMPTRAN T "+
								" WHERE E.EMPNO = T.EMPNO AND T.PRJ_SRNO = '"+key+"'"+
									" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)  AND E.EMPNO NOT IN(select DISTINCT EMPNO from empmast where STATUS='N' and empno not in( "+
							" select distinct empno from ytdtran where  trncd=101) )  AND (E.STATUS='A' OR E.DOL>='"+ReportDAO.BoFinancialy(Full_Date)+"') ORDER BY E.EMPCODE";
				
				}
				else
				{
					sql = " SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
							" FROM EMPMAST E,EMPTRAN T "+
								" WHERE E.EMPNO = T.EMPNO AND T.PRJ_SRNO = '"+key+"'"+
									" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) AND (E.STATUS='A' OR E.DOL BETWEEN '"+ReportDAO.BoFinancialy(Full_Date)+"' AND  '"+ReportDAO.EoFinancialy(Full_Date)+"' )  AND E.EMPNO NOT IN(select DISTINCT EMPNO from empmast where STATUS='N' and empno not in( "+
							" select distinct empno from ytdtran where  trncd=101) )  AND (E.STATUS='A' OR E.DOL>='"+ReportDAO.BoFinancialy(Full_Date)+"') ORDER BY E.EMPCODE";
				
				}
			}
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);
			FilterValues vals = null;
			while(rs.next())
			{
				vals = new FilterValues();
				vals.setEMPNO(rs.getInt(1));
				vals.setNAME(rs.getString(2));
				vals.setEMPCODE(rs.getString(3));
				result.add(vals);
			}
			con.close();
			rs.close();
			st.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<FilterValues> getAllEmpListtaxreport(String forWhat,String date,String type)
	{
		ArrayList<FilterValues> result = new ArrayList<FilterValues>();
		String sql = "";
		//date="Jan-2015";
		
				String Full_Date="25-"+date;  // full_date ="25-Jan-2015";
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			if(type.equalsIgnoreCase("PrintTaxSheet"))
			{	
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
							" FROM EMPMAST E WHERE (E.STATUS='A' OR E.DOL BETWEEN '"+ReportDAO.BoFinancialy(Full_Date)+"' AND  '"+ReportDAO.EoFinancialy(Full_Date)+"' ) ORDER BY E.EMPCODE";
			}
			else if(type.equalsIgnoreCase("F16"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
						" FROM EMPMAST E  WHERE E.EMPNO NOT IN(select DISTINCT EMPNO from empmast where STATUS='N' and empno not in( "+
							" select distinct empno from ytdtran where  trncd=101) )  AND (E.STATUS='A' OR E.DOL>='"+ReportDAO.BoFinancialy(Full_Date)+"') ORDER BY E.EMPCODE";
			}
			else
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
						" FROM EMPMAST E WHERE (E.STATUS='A' OR E.DOL BETWEEN '"+ReportDAO.BoFinancialy(Full_Date)+"' AND  '"+ReportDAO.EoFinancialy(Full_Date)+"' ) AND E.EMPNO NOT IN(select DISTINCT EMPNO from empmast where STATUS='N' and empno not in( "+
							" select distinct empno from ytdtran where  trncd=101) )  AND (E.STATUS='A' OR E.DOL>='"+ReportDAO.BoFinancialy(Full_Date)+"') ORDER BY E.EMPCODE";
			}
			System.out.println("selecting employee----"+sql);
			ResultSet rs = st.executeQuery(sql);
			FilterValues vals = null;
			while(rs.next())
			{
				vals = new FilterValues();
				vals.setEMPNO(rs.getInt(1));
				vals.setNAME(rs.getString(2));
				vals.setEMPCODE(rs.getString(3));
				result.add(vals);
			}
			con.close();
			rs.close();
			st.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//System.out.println(sql);
		return result;
	}


	
	
	public ArrayList<FilterValues> getAllEmpListCategorytaxreport(int key,String forWhat,String date,String type)
	{
		ArrayList<FilterValues> result = new ArrayList<FilterValues>();
		String sql = "";
		//date="Jan-2015";
		
				String Full_Date="25-"+date;  // full_date ="25-Jan-2015";
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			if(type.equalsIgnoreCase("PrintTaxSheet"))
			{	
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
							" FROM EMPMAST E WHERE (E.STATUS='A' OR E.DOL BETWEEN '"+ReportDAO.BoFinancialy(Full_Date)+"' AND  '"+ReportDAO.EoFinancialy(Full_Date)+"' ) AND E.EMPLOYEE_TYPE="+key+" ORDER BY E.EMPCODE";
			}
			else if(type.equalsIgnoreCase("F16"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
						" FROM EMPMAST E  WHERE E.EMPNO NOT IN(select DISTINCT EMPNO from empmast where STATUS='N' and empno not in( "+
							" select distinct empno from ytdtran where  trncd=101) )  AND (E.STATUS='A' OR E.DOL>='"+ReportDAO.BoFinancialy(Full_Date)+"') AND E.EMPLOYEE_TYPE="+key+" ORDER BY E.EMPCODE";
			}
			else
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
						" FROM EMPMAST E WHERE (E.STATUS='A' OR E.DOL BETWEEN '"+ReportDAO.BoFinancialy(Full_Date)+"' AND  '"+ReportDAO.EoFinancialy(Full_Date)+"' ) AND E.EMPNO NOT IN(select DISTINCT EMPNO from empmast where STATUS='N' and empno not in( "+
							" select distinct empno from ytdtran where  trncd=101) )  AND (E.STATUS='A' OR E.DOL>='"+ReportDAO.BoFinancialy(Full_Date)+"') AND E.EMPLOYEE_TYPE="+key+" ORDER BY E.EMPCODE";
			}
			System.out.println("selecting employee----"+sql);
			ResultSet rs = st.executeQuery(sql);
			FilterValues vals = null;
			while(rs.next())
			{
				vals = new FilterValues();
				vals.setEMPNO(rs.getInt(1));
				vals.setNAME(rs.getString(2));
				vals.setEMPCODE(rs.getString(3));
				result.add(vals);
			}
			con.close();
			rs.close();
			st.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//System.out.println(sql);
		return result;
	}


	
	public ArrayList<FilterValues> getAllEmpListCategory(int key,String forWhat,String date)
	{
		ArrayList<FilterValues> result = new ArrayList<FilterValues>();
		String sql = "";
		//date="Jan-2015";
		
		System.out.println("Check Employee Type :"+key);
		
				String Full_Date="25-"+date;  // full_date ="25-Jan-2015";
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			if(forWhat.equalsIgnoreCase("toFinalizeCal"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE" +
					" FROM EMPMAST E,SAL_DETAILS S WHERE (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					" (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND (S.SAL_STATUS='PROCESSED') AND S.SAL_MONTH='"+date+"' AND E.EMPNO=S.EMPNO and E.EMPNO not in(select distinct empno from onhold where status='H') AND E.EMPLOYEE_TYPE="+key+" ORDER BY E.EMPCODE";
			}
			else if(forWhat.equalsIgnoreCase("toPaySlip"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE" +
					" FROM EMPMAST E,SAL_DETAILS S WHERE (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					"(E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  (S.SAL_STATUS='PROCESSED' OR S.SAL_STATUS='NEGATIVE' OR S.SAL_STATUS='FINALIZED') AND S.SAL_MONTH='"+date+"' AND E.EMPNO=S.EMPNO AND E.EMPLOYEE_TYPE="+key+" ORDER BY E.EMPCODE";
			}
			
			else if(forWhat.equalsIgnoreCase("deduction"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE" +
					" FROM EMPMAST E,SAL_DETAILS S WHERE (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					"(E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  (S.SAL_STATUS='PROCESSED' OR S.SAL_STATUS='NEGATIVE' OR S.SAL_STATUS='AutoInst') AND S.SAL_MONTH='"+date+"' AND E.EMPNO=S.EMPNO AND E.EMPLOYEE_TYPE="+key+" ORDER BY E.EMPCODE";
			}
			else if(forWhat.equalsIgnoreCase("toPayCal"))
			{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE" +
					" FROM EMPMAST E WHERE (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					" (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  E.EMPNO not in " +
					" (select Empno from sal_details S where S.SAL_STATUS = 'FINALIZED' AND S.SAL_MONTH='"+date+"')" +
							"   and E.empno in (select Distinct empno from paytran where trndt between '"+ReportDAO.BOM(Full_Date)+"' and '"+ReportDAO.EOM(Full_Date)+"' )  AND E.EMPLOYEE_TYPE="+key+"  ORDER BY E.EMPCODE";
			}
			
			else if(forWhat.equalsIgnoreCase("toPostBonus"))
			{
				/*sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE" +
					" FROM EMPMAST E WHERE (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(Full_Date)+"') or" +
					" (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(Full_Date)+"' )) AND  E.EMPNO not in " +
					" (select Empno from sal_details S where S.SAL_STATUS = 'FINALIZED' AND S.SAL_MONTH='"+date+"')" +
							"   and E.empno in (select Distinct empno from bonustran where status ='p' and trndt between '"+ReportDAO.BOM(Full_Date)+"' and '"+ReportDAO.EOM(Full_Date)+"' )   ORDER BY E.EMPCODE";*/
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE" +
						" FROM EMPMAST E WHERE "+
								"   E.empno in (select Distinct empno from bonustran where status ='p' ) AND E.EMPLOYEE_TYPE="+key+" " ;
			}
			
			else if(forWhat.equalsIgnoreCase("lateMarkPost"))
			{
				
				sql="SELECT Distinct E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE " +
						"FROM EMPMAST E,EMP_ATTENDANCE S WHERE " +
						" S.ATTD_DATE between '"+ReportDAO.BOM(Full_Date)+"' and '"+ReportDAO.EOM(Full_Date)+"' AND E.EMPNO=S.EMPNO" +
						" and S.EMP_STATUS is null AND E.EMPLOYEE_TYPE="+key+"  ORDER BY E.EMPCODE";
			}
				
		/*	else if(forWhat.equalsIgnoreCase("toCTC_Report"))
			{
				Full_Date="31-Jan-2016"; 
				
				sql = "SELECT distinct E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE FROM EMPMAST E , " +
						"EMPTRAN T  WHERE  E.empno in (select Distinct empno from paytran_stage where trndt = '31-dec-2015' )  ORDER BY E.EMPNO";
			}*/
			
			
			else if(forWhat.equalsIgnoreCase("Leave"))
			{	
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
							" FROM EMPMAST E WHERE E.STATUS='A' and DOJ <= '"+ReportDAO.getPastdate()+"' and empno not in(select distinct empno from leavebal where baldt between '"+ReportDAO.BOYForJanToDec(date)+"' and '"+ReportDAO.EOYForJanToDec(date)+"') AND E.EMPLOYEE_TYPE="+key+" ORDER BY E.EMPCODE";
				
			}
			
			else
			{	
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
							" FROM EMPMAST E WHERE E.STATUS='A' AND E.EMPLOYEE_TYPE="+key+" ORDER BY E.EMPCODE";
			}
			System.out.println("selecting employee----"+sql);
			ResultSet rs = st.executeQuery(sql);
			FilterValues vals = null;
			while(rs.next())
			{
				vals = new FilterValues();
				vals.setEMPNO(rs.getInt(1));
				vals.setNAME(rs.getString(2));
				vals.setEMPCODE(rs.getString(3));
				result.add(vals);
			}
			con.close();
			rs.close();
			st.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//System.out.println(sql);
		return result;
	}
	
	

}
