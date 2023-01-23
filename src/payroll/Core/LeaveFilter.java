package payroll.Core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import payroll.DAO.ConnectionManager;
import payroll.Model.FilterValues;

public class LeaveFilter {

	public ArrayList<FilterValues> getAlphabeticalList(String key, String forWhat,String date, String type)
	{
		
		//date="Jan-2015";
		
		String Full_Date="25-"+date;  // full_date ="25-Jan-2015";
		
		ArrayList<FilterValues> result = new ArrayList<FilterValues>();
		String sql ="";
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			if(forWhat.equalsIgnoreCase("Leave"))
			{
				
				if(type.equalsIgnoreCase("3"))
				{
					sql = "SELECT EMPNO,CONVERT(NVARCHAR(100),LNAME+' '+ FNAME+' '+MNAME) AS NAME,EMPCODE FROM EMPMAST WHERE LNAME LIKE '"+key+"%' AND STATUS='A' " +
							/*" and DOJ <= '"+ReportDAO.getPastdate()+"' " +*/
									"and EMPNO not in(select distinct empno from leavebal where baldt between '"+ReportDAO.BOYForJanToDec(date)+"' and '"+ReportDAO.EOYForJanToDec(date)+"' and leavecd="+type+") " +
									" ORDER BY NAME";
						
				}
				
				else{
					sql = "SELECT EMPNO,CONVERT(NVARCHAR(100),LNAME+' '+ FNAME+' '+MNAME) AS NAME,EMPCODE FROM EMPMAST WHERE LNAME LIKE '"+key+"%' AND STATUS='A' " +
					/*" and DOJ <= '"+ReportDAO.getPastdate()+"' " +*/
							"and EMPNO not in(select distinct empno from leavebal where baldt between '"+ReportDAO.BOYForJanToDec(date)+"' and '"+ReportDAO.EOYForJanToDec(date)+"' and leavecd="+type+") " +
							
							"   and DOJ not between '"+ReportDAO.BOYForJanToDec(date)+"' and '"+ReportDAO.EOYForJanToDec(date)+"'        ORDER BY NAME";
				}
		
			}
			
			else
			{	
				sql = "SELECT EMPNO,CONVERT(NVARCHAR(100),LNAME+' '+ FNAME+' '+MNAME) AS NAME,EMPCODE FROM EMPMAST WHERE LNAME LIKE '"+key+"%' AND STATUS='A' ORDER BY NAME";
			}
			System.out.println("SQL 123 : "+sql);
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
	
	public ArrayList<FilterValues> getDeptWiseList(int key, String forWhat,String date, String type)
	{  
		ArrayList<FilterValues> result = new ArrayList<FilterValues>();
		String sql = "";
		//date="Jan-2015";
		
				String Full_Date="25-"+date;  // full_date ="25-Jan-2015";
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			if(forWhat.equalsIgnoreCase("Leave"))
			{
				
				if(type.equalsIgnoreCase("3"))
				{
				
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
						" FROM EMPMAST E,EMPTRAN T "+
							" WHERE E.EMPNO = T.EMPNO AND T.DEPT = "+key+
								" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) AND E.STATUS='A' " +
								/*"and E.DOJ <= '"+ReportDAO.getPastdate()+"' " +*/
										"and E.EMPNO not in(select distinct empno from leavebal where baldt between '"+ReportDAO.BOYForJanToDec(date)+"' and '"+ReportDAO.EOYForJanToDec(date)+"' and leavecd="+type+") ORDER BY E.EMPNO";
				}
				
				else{

					
					sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
							" FROM EMPMAST E,EMPTRAN T "+
								" WHERE E.EMPNO = T.EMPNO AND T.DEPT = "+key+
									" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) AND E.STATUS='A' " +
								/*	"and E.DOJ <= '"+ReportDAO.getPastdate()+"' " +*/
											"and E.EMPNO not in(select distinct empno from leavebal where baldt between '"+ReportDAO.BOYForJanToDec(date)+"' and '"+ReportDAO.EOYForJanToDec(date)+"' and leavecd="+type+")" +
											" and DOJ not between '"+ReportDAO.BOYForJanToDec(date)+"' and '"+ReportDAO.EOYForJanToDec(date)+"'  ORDER BY E.EMPNO";
					
				}
				
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
	
	public ArrayList<FilterValues> getDesigWiseList(int key,String forWhat,String date, String type)
	{
		System.out.println("M I READY! getDesigWiseList");
		ArrayList<FilterValues> result = new ArrayList<FilterValues>();
		String sql = "";
		//date="Jan-2015";
		
				String Full_Date="25-"+date;  // full_date ="25-Jan-2015";
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			if(forWhat.equalsIgnoreCase("Leave"))
			{
				
				if(type.equalsIgnoreCase("3"))
				{
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
						" FROM EMPMAST E,EMPTRAN T "+
							" WHERE E.EMPNO = T.EMPNO AND T.DESIG = "+key+
								" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) AND E.STATUS='A'  " +
								/*"and E.DOJ <= '"+ReportDAO.getPastdate()+"' " +*/
										"and E.EMPNO not in(select distinct empno from leavebal where baldt between '"+ReportDAO.BOYForJanToDec(date)+"' and '"+ReportDAO.EOYForJanToDec(date)+"' and leavecd="+type+") ORDER BY E.EMPNO";
				}
				
				else
				{
					sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
							" FROM EMPMAST E,EMPTRAN T "+
								" WHERE E.EMPNO = T.EMPNO AND T.DESIG = "+key+
									" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) AND E.STATUS='A'  " +
									/*"and E.DOJ <= '"+ReportDAO.getPastdate()+"' " +*/
											"and E.EMPNO not in(select distinct empno from leavebal where baldt between '"+ReportDAO.BOYForJanToDec(date)+"' and '"+ReportDAO.EOYForJanToDec(date)+"' and leavecd="+type+")" +
											" and DOJ not between '"+ReportDAO.BOYForJanToDec(date)+"' and '"+ReportDAO.EOYForJanToDec(date)+"'   ORDER BY E.EMPNO";
					}
				
				
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
	
	public ArrayList<FilterValues> getGradeWiseList(int key,String forWhat,String date, String type)
	{
		 System.out.println("M I READY!");
		ArrayList<FilterValues> result = new ArrayList<FilterValues>();
		String sql = "";
		//date="Jan-2015";
		
				String Full_Date="25-"+date;  // full_date ="25-Jan-2015";
		try
		{  System.out.println("M I READY!.0");
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			FilterValues vals = null;
			//ResultSet rst = st.executeQuery("select lkp_srno from LOOKUP where LKP_CODE = 'desig'");

			ResultSet rst = st.executeQuery("select lkp_srno from LOOKUP where LKP_CODE = 'desig' and LKP_RECR = "+key);
			 System.out.println("M I READY!.01");
			while(rst.next()){
				
				 System.out.println("M I READY!.02");
				
				if(forWhat.equalsIgnoreCase("Leave"))
				{
					
					 System.out.println("M I READY! 1");
					if(type.equalsIgnoreCase("3"))
					{
						 System.out.println("M I READY! 2");
					sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE FROM EMPMAST E,EMPTRAN T "+
							" WHERE E.EMPNO = T.EMPNO AND T.DESIG = "+rst.getInt("LKP_SRNO")+
							" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) AND E.STATUS='A'" +
						/*	" and E.DOJ <= '"+ReportDAO.getPastdate()+"'  " +*/
									"and E.EMPNO not in(select distinct empno from leavebal where baldt between '"+ReportDAO.BOYForJanToDec(date)+"' and '"+ReportDAO.EOYForJanToDec(date)+"' and leavecd="+type+") ORDER BY E.EMPNO";
				}
				
					else{
						 System.out.println("M I READY! 3");
						sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE FROM EMPMAST E,EMPTRAN T "+
								" WHERE E.EMPNO = T.EMPNO AND T.DESIG = "+rst.getInt("LKP_SRNO")+
								" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) AND E.STATUS='A' " +
								/*"and E.DOJ <= '"+ReportDAO.getPastdate()+"' " +*/
										"and E.EMPNO not in(select distinct empno from leavebal where baldt between '"+ReportDAO.BOYForJanToDec(date)+"' and '"+ReportDAO.EOYForJanToDec(date)+"' and leavecd="+type+") " +
										" and DOJ not between '"+ReportDAO.BOYForJanToDec(date)+"' and '"+ReportDAO.EOYForJanToDec(date)+"'  ORDER BY E.EMPNO";
					
						
						
					}
					
				}
				else
				{					 System.out.println("M I READY! 4");
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
			 System.out.println("M I READY!.003");
			st.close();
			con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<FilterValues> getProjectWiseList(int key,String forWhat,String date, String type)
	{
		ArrayList<FilterValues> result = new ArrayList<FilterValues>();
		String sql = "";
		//date="Jan-2015";
		
				String Full_Date="25-"+date;  // full_date ="25-Jan-2015";
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			
		
			if(forWhat.equalsIgnoreCase("Leave"))
			{	
				
				
				if(type.equalsIgnoreCase("3"))
				{
				sql = " SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
							" FROM EMPMAST E,EMPTRAN T "+
								" WHERE E.EMPNO = T.EMPNO AND T.PRJ_SRNO = '"+key+"'"+
									" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) AND E.STATUS='A' " +
									/*"and E.DOJ <= '"+ReportDAO.getPastdate()+"'" +*/
											" and E.EMPNO not in(select distinct empno from leavebal where baldt between '"+ReportDAO.BOYForJanToDec(date)+"' and '"+ReportDAO.EOYForJanToDec(date)+"' and leavecd="+type+")  ORDER BY E.EMPNO";
			}
				
				else{
					sql = " SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
							" FROM EMPMAST E,EMPTRAN T "+
								" WHERE E.EMPNO = T.EMPNO AND T.PRJ_SRNO = '"+key+"'"+
									" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) AND E.STATUS='A' " +
									/*"and E.DOJ <= '"+ReportDAO.getPastdate()+"'" +*/
											" and E.EMPNO not in(select distinct empno from leavebal where baldt between '"+ReportDAO.BOYForJanToDec(date)+"' and '"+ReportDAO.EOYForJanToDec(date)+"' and leavecd="+type+") " +
											"and DOJ not between '"+ReportDAO.BOYForJanToDec(date)+"' and '"+ReportDAO.EOYForJanToDec(date)+"'   ORDER BY E.EMPNO";
			
					
				}
			}
			
			
			else{
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
	
	public ArrayList<FilterValues> getAllEmpList(String forWhat,String date, String type)
	{	System.out.println("getAllEmpList");
		ArrayList<FilterValues> result = new ArrayList<FilterValues>();
		String sql = "";
		//date="Jan-2015";
		
				String Full_Date="25-"+date;  // full_date ="25-Jan-2015";
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			
			 if(forWhat.equalsIgnoreCase("Leave"))
			{	
				 
				 if(type.equalsIgnoreCase("3"))
				 {
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
							" FROM EMPMAST E WHERE E.STATUS='A' " +
							/*" and E.DOJ <= ' 30-Jun-2016' " +*/
							"and E.EMPNO not in(select distinct empno from leavebal where baldt between '"+ReportDAO.BOYForJanToDec(date)+"' and '"+ReportDAO.EOYForJanToDec(date)+"' and leavecd="+type+") ORDER BY E.EMPCODE";
				 }
				 else{
					 sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
								" FROM EMPMAST E WHERE E.STATUS='A' " +
								/*" and E.DOJ <= ' 30-Jun-2016'  " +*/
								"and E.EMPNO not in(select distinct empno from leavebal where baldt between '"+ReportDAO.BOYForJanToDec(date)+"' and '"+ReportDAO.EOYForJanToDec(date)+"' and leavecd="+type+") " +
										" and DOJ not between '"+ReportDAO.BOYForJanToDec(date)+"' and '"+ReportDAO.EOYForJanToDec(date)+"'  ORDER BY E.EMPCODE";
										 
				 }
				 
			}
			
			else
			{	
				sql = "SELECT E.EMPNO,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME),E.EMPCODE"+
							" FROM EMPMAST E WHERE E.STATUS='A' ORDER BY E.EMPCODE";
			}
			 System.out.println("selecting employee----"+date);
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
