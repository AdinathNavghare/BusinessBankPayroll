package payroll.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import payroll.Core.ReportDAO;
import payroll.Model.EmpOffBean;
import payroll.Model.Lookup;
import payroll.Model.Retirement_ext_leave_credit;
import payroll.Model.TranBean;
import payroll.Model.TransactionBean;

public class EmpOffHandler
{
	Connection con=null;
	Statement st=null;
	PreparedStatement prst=null;
	LookupHandler lkhp=new LookupHandler();
	Lookup lookupBean=new Lookup() ;
	
	public void insertOfficInfo(EmpOffBean empoffbean,String uid)
	{
		System.out.println("insertOfficInfo : insertOfficInfos");
		
		ResultSet rs=null;
		int srno=0;
		
		try
		{
			con = ConnectionManager.getConnection();
			st = con.createStatement();
			Statement st1 = con.createStatement();
			Statement st2 = con.createStatement();
			
			int PRJ_SRNO=00;
			String state="Empty";
			String Handicap="";
			rs=st.executeQuery("select max(SRNO) from EMPTRAN where EMPNO="+empoffbean.getEMPNO()+"");
			while(rs.next())
			{
				srno=rs.getInt(1);
			}
			srno=srno+1;
			Connection conn = ConnectionManager.getConnectionTech();
			Statement stmt = conn.createStatement();
			ResultSet rst = stmt.executeQuery("SELECT Project_Code FROM Project_Sites WHERE Site_isdeleted = 0 and SITE_ID='"+empoffbean.getPrj_name()+"'");
			
			int updtsrno = srno-1;
			String update = "update EMPTRAN set STATUS=0 where EMPNO= "+empoffbean.getEMPNO()+" and SRNO = "+updtsrno;
			Statement updt =con.createStatement();
			updt.executeUpdate(update);
					
			prst = con.prepareStatement("insert into EMPTRAN(EMPNO,EFFDATE,SRNO,ORDER_NO,ORDER_DT,MDESC,PRJ_SRNO,ACNO,GRADE,DESIG,DEPT,STATUS,TRNCD,PRJ_CODE,BANK_NAME,MNGR_ID,CREATED_BY,CREATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			prst.setInt(1,empoffbean.getEMPNO() );
			prst.setString(2,empoffbean.getEFFDATE());
			prst.setInt(3,srno);
			prst.setString(4,empoffbean.getORDER_NO());
			prst.setString(5,empoffbean.getORDER_DT());
			prst.setString(6,empoffbean.getMDESC());
			prst.setInt(7,Integer.parseInt(empoffbean.getPrj_name()));
			/*if(rst.next()){
				prst.setInt(7,rst.getInt(1));
			} conn.close();*/
			prst.setString(8, empoffbean.getACNO());
			prst.setInt(9,empoffbean.getGRADE());
			prst.setInt(10,empoffbean.getDESIG());
			prst.setInt(11,empoffbean.getDEPT());
			prst.setInt(12,empoffbean.getSTATUS());
			prst.setInt(13,empoffbean.getTRNCD());
			//prst.setString(14,empoffbean.getPrj_name());
			if(rst.next()){
				prst.setString(14,rst.getString(1));
			} conn.close();
			prst.setInt(15, empoffbean.getBANK_NAME());
			prst.setInt(16, empoffbean.getManagerId());
			prst.setString(17,uid);
			prst.setString(18, ReportDAO.getSysDate());
			prst.execute();
			// added by akshay  for  changing slab of 202 according to transsfer 
			ResultSet  rst1 = st1.executeQuery("select PRJ_SRNO,(select isnull(DISABILYN,'N') from empmast where EMPNO="+empoffbean.getEMPNO()+" ) as HANDICAP,(select LKP_DISC   from LOOKUP where LKP_CODE ='STATE' and LKP_SRNO =  Site_State) as state from EMPTRAN emp " +
					" left join Project_Sites prj on emp.PRJ_SRNO=Prj.SITE_ID where  " +
					" SRNO=(SELECT MAX(E2.SRNO)FROM EMPTRAN E2 WHERE E2.EMPNO ="+empoffbean.getEMPNO()+")and EMPNO="+empoffbean.getEMPNO()+" ");
		
			
			System.out.println("select PRJ_SRNO,(select LKP_DISC   from LOOKUP where LKP_CODE ='STATE' and LKP_SRNO =  Site_State) as state from EMPTRAN emp " +
					" left join Project_Sites prj on emp.PRJ_SRNO=Prj.SITE_ID where  " +
					" SRNO=(SELECT MAX(E2.SRNO)FROM EMPTRAN E2 WHERE E2.EMPNO ="+empoffbean.getEMPNO()+")and EMPNO="+empoffbean.getEMPNO()+" ");
			while(rst1.next())
			{
				PRJ_SRNO =rst1.getInt("PRJ_SRNO");
            	state=rst1.getString("state");
            	Handicap=rst1.getString("HANDICAP");
			}
		
			if(state.equalsIgnoreCase("Maharashtra") && !state.equalsIgnoreCase("Empty") && !state.equalsIgnoreCase("null") &&( Handicap.equalsIgnoreCase("N")||Handicap.equals("") ))
			{
				ResultSet  rst2 = st2.executeQuery("select * from slab where EMP_CAT ="+empoffbean.getEMPNO()+" and  TRNCD =202 and FRMAMT <=0 and MINAMT <=0 " +
	                               " and maxamt <=0 and fixamt<=0 and EFFDATE >= '"+ ReportDAO.getSysDate()+"'  ");
				System.out.println("select * from slab where EMP_CAT ="+empoffbean.getEMPNO()+" and  TRNCD =202 and FRMAMT <=0 and MINAMT <=0 " +
	                               " and maxamt <=0 and fixamt<=0 and EFFDATE >= '"+ ReportDAO.getSysDate()+"'  ");
				if(rst2.next())
    			        {
					
            	    	st2.executeUpdate("delete from slab where EMP_CAT ="+empoffbean.getEMPNO()+" and  TRNCD =202 and FRMAMT <=0 and MINAMT <=0 " +
	                                     " and maxamt <=0 and fixamt<=0 and EFFDATE >= '"+ ReportDAO.getSysDate()+"' " );
            	    	System.out.println("delete from slab where EMP_CAT ="+empoffbean.getEMPNO()+" and  TRNCD =202 and FRMAMT <=0 and MINAMT <=0 " +
	                                     " and maxamt <=0 and fixamt<=0 and EFFDATE >= '"+ ReportDAO.getSysDate()+"' " );
    			        }
            	 
            	
            	
            	
			}
			else
			{
				
				String query =	" IF NOT  EXISTS( select * from slab where EMP_CAT ="+empoffbean.getEMPNO()+" and  TRNCD =202 and FRMAMT <=0 and MINAMT <=0 " +
	                            " and maxamt <=0 and fixamt<=0 and EFFDATE >= ' "+ ReportDAO.getSysDate()+" ')  " +
					 			
					        	" INSERT INTO SLAB  " +
                                "([EFFDATE] " +
                                ",[EMP_CAT]  " +
                                ",[TRNCD]  " +
                                ",[SRNO]  " +
                                ",[FRMAMT]  " +
                                ",[TOAMT]  " +
                                ",[PER]  " +
                                " ,[MINAMT]  " +
                                " ,[MAXAMT]  " +
                                " ,[FIXAMT]  " +
                                " ,[ON_AMT_CD])  " +
                                " VALUES  " +
                                " ('2099-12-31',"+empoffbean.getEMPNO()+",202,	(select ISNULL((MAX (SRNO)+1),1) as  srno from slab where EMP_CAT ="+empoffbean.getEMPNO()+ 
                                " ),0.00,9999999.00,0.00,0.00,0.00,0.00,0 )" ;
                				st2.execute(query);		
                				System.out.println(query);
						}
			
			//
			
			
			//String updateSalaryBranch = "update EMPTRAN set STATUS=0 where EMPNO= "+empoffbean.getEMPNO()+" and SRNO = "+updtsrno;
			
			String updateSalaryBranch = "UPDATE EMPTRAN SET BRANCH=(SELECT BRANCH FROM EMPTRAN WHERE EMPNO="+empoffbean.getEMPNO()+" "
					+ "AND SRNO=(SELECT MAX(SRNO)-1 FROM EMPTRAN WHERE EMPNO="+empoffbean.getEMPNO()+")) WHERE EMPNO="+empoffbean.getEMPNO()+" "
							+ "AND SRNO=(SELECT MAX(SRNO) FROM EMPTRAN WHERE EMPNO="+empoffbean.getEMPNO()+")";
			Statement updtSalBr =con.createStatement();
			updtSalBr.executeUpdate(updateSalaryBranch);
					
			
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	public ArrayList<EmpOffBean> getEmpOfficInfo(String EMPNO)
	{
		ResultSet rs=null;
		Statement st = null;
		ArrayList<EmpOffBean> empofficlist = new ArrayList<EmpOffBean>();
		try
		{
			con = ConnectionManager.getConnection();
			st = con.createStatement();
			rs=st.executeQuery("select * from EMPTRAN where empno='"+EMPNO+"' order by srno");
			//rs=st.executeQuery("select * from emptran where empno = '"+EMPNO+"' and SRNO = (select MAX(SRNO) from emptran where empno = '"+EMPNO+"')");
			EmpOffBean empoffbean;
			while(rs.next())
			{
				String Prj_Name = null;
				String Prj_Code = null;
				con = ConnectionManager.getConnectionTech();
				Statement stmt = con.createStatement();
			//	ResultSet rs1 = stmt.executeQuery("SELECT Site_Name, Project_Code from Project_Sites where Project_Code='"+rs.getString("PRJ_CODE")+"'");
				ResultSet rs1 = stmt.executeQuery("SELECT Site_Name, Project_Code from Project_Sites where Site_isdeleted = 0 and Site_ID='"+rs.getString("PRJ_SRNO")+"'");
				while(rs1.next()){
					Prj_Name = rs1.getString(1);
					Prj_Code = rs1.getString(2);
				}
				rs1.close();
				stmt.close();
				
				empoffbean= new EmpOffBean();
				empoffbean.setACNO(rs.getString("ACNO")==null?"":rs.getString("ACNO"));
				empoffbean.setDESIG(rs.getString("DESIG")==null?0:rs.getInt("DESIG"));
				empoffbean.setDEPT(rs.getString("DEPT")==null?0:rs.getInt("DEPT"));
				empoffbean.setEFFDATE(rs.getDate("EFFDATE")==null?"":dateFormat(rs.getDate("EFFDATE")));
				empoffbean.setEMPNO(rs.getString("EMPNO")==null?0:rs.getInt("EMPNO"));
				empoffbean.setBRANCH(rs.getString("BRANCH")==null?0:rs.getInt("BRANCH"));
				empoffbean.setGRADE(rs.getString("GRADE")==null?0:rs.getInt("GRADE"));
				empoffbean.setORDER_DT(rs.getDate("ORDER_DT")==null?"":dateFormat(rs.getDate("ORDER_DT")));
				empoffbean.setORDER_NO(rs.getString("ORDER_NO")==null?"":rs.getString("ORDER_NO"));
				empoffbean.setSRNO(rs.getString("SRNO")==null?0:rs.getInt("SRNO"));
				empoffbean.setSTATUS(rs.getString("STATUS")==null?0:rs.getInt("STATUS"));
				empoffbean.setTRNCD(rs.getString("TRNCD")==null?0:rs.getInt("TRNCD"));
				empoffbean.setMDESC(rs.getString("MDESC")==null?"":rs.getString("MDESC"));
				empoffbean.setPrj_code(Prj_Code);
				empoffbean.setPrj_name(Prj_Name);
				empoffbean.setBANK_NAME(rs.getString("BANK_NAME")==null?0:rs.getInt("BANK_NAME"));
				empofficlist.add(empoffbean);
			}
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return empofficlist;
	}
	
	public void updateOffinfo(EmpOffBean empoffbean)
	{
		Statement st = null;
		con = ConnectionManager.getConnection();
		try
		{
			st = con.createStatement();
			st.executeUpdate("update EMPTRAN set EFFDATE='"+empoffbean.getEFFDATE()+"',TRNCD="+empoffbean.getTRNCD()+",ORDER_NO='"+empoffbean.getORDER_NO()+"',ORDER_DT='"+empoffbean.getORDER_DT()+"',MDESC='"+empoffbean.getMDESC()+"',PRJ_SRNO="+empoffbean.getPrj_srno()+",ACNO='"+empoffbean.getACNO()+"',GRADE="+empoffbean.getGRADE()+",DESIG="+empoffbean.getDESIG()+",DEPT="+empoffbean.getDEPT()+",BANK_NAME="+empoffbean.getBANK_NAME()+",PRJ_CODE='"+empoffbean.getPrj_code()+"' where EMPNO="+empoffbean.getEMPNO()+" and SRNO="+empoffbean.getSRNO()+"");
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static String dateFormat(Date date)
	{
		String result="";
		if(date==null)
		{
			return "";
		}
		SimpleDateFormat format=new SimpleDateFormat("dd-MMM-yyyy");
		result=format.format(date);
		return result;
	}
	
	public String getDescrption(String Column,String Table,String Condition1,int Condition2)
	{
		Statement st=null;
		String descrption="";
		ResultSet rs=null;
		try
		{
			con = ConnectionManager.getConnection();
			st = con.createStatement();
			rs=st.executeQuery("select "+Column+" from "+Table+" where "+Condition1+"="+Condition2+"");
			while(rs.next())
			{
				descrption=rs.getString(1);
			}
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return descrption;
	}
	
	public ArrayList<Lookup> getGradeBranchList(String tab_name)
	{
		
		ArrayList<Lookup> result=new ArrayList<Lookup>();
		Statement st=null;
		ResultSet rs=null;
		try
		{
			con = ConnectionManager.getConnection();
			st = con.createStatement();
			rs=st.executeQuery("select * from "+tab_name+"  order by 3 ASC ");
			while(rs.next())
			{
				Lookup lkp=new Lookup();
				lkp.setLKP_DESC(rs.getString(2));
				lkp.setLKP_SRNO(rs.getInt(1));
				result.add(lkp);
			}
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public TransactionBean getInfoEmpTran(String EMPNO)
	{
		TransactionBean trbn = new TransactionBean();
		try
		{
			con = ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs = null;
			rs=st.executeQuery("select e.salute,e.fname,e.mname,e.lname,e.pfno,e.panno,et.branch,et.acno,et.grade,et.desig,et.dept,e.doj,e.empcode, et.prj_code  from emptran et inner join empmast e on et.empno=e.empno where srno=(select max(srno) from emptran where empno="+EMPNO+") and e.empno="+EMPNO+"");
			while(rs.next())
			{
				trbn.setEmpname(new LookupHandler().getLKP_Desc("SALUTE",rs.getInt(1))+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getString(4));
				trbn.setEmpno(Integer.parseInt(EMPNO));
				trbn.setPfno(rs.getString(5)==null?"":rs.getString(5));
				trbn.setPanno(rs.getString(6)==null?"":rs.getString(6));
				trbn.setBranch(Integer.parseInt(rs.getString(7)==null?"0":rs.getString(7)));
				trbn.setAccno(rs.getString(8)==null?"":rs.getString(8));
				trbn.setGrade(Integer.parseInt(rs.getString(9)==null?"0":rs.getString(9)));
				trbn.setDesg(Integer.parseInt(rs.getString(10)==null?"0":rs.getString(10)));
				trbn.setDept(Integer.parseInt(rs.getString(11)==null?"0":rs.getString(11)));
				trbn.setDOJ(rs.getString(12)==null?"":dateFormat(rs.getDate(12)));
				trbn.setEMPCODE(rs.getString(13)==null?"":rs.getString(13));
				trbn.setPrj_Code(rs.getString(14)==null?"":rs.getString(14));
			}
			con.close();
		}
		catch(Exception e)
		{
			try
			{
				con.close();
			} catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
		}
		return trbn;
	}
	
	public ArrayList<EmpOffBean> getprojectCode()//param for getting projcet code list
	{
		LookupHandler lh=new LookupHandler();
		ArrayList<EmpOffBean> list= new ArrayList<EmpOffBean>();
		Connection con=null;
		ResultSet rs = null;

		//String query="SELECT * FROM Prj";
		//String query="SELECT * FROM Project_Sites order by Site_Name";
	//	String query="select rtrim(project_code)+'  '+rtrim(Site_City) as site_Add,* from Project_Sites order by Project_Code";
		String query="select isnull(rtrim(Site_City),'0') as city, Site_Name as site_Add,* from Project_Sites where Site_isdeleted = 0 order by Site_id ASC";
		con = ConnectionManager.getConnectionTech();

		
		try
		{
			Statement st=con.createStatement();
			rs=st.executeQuery(query);
			while(rs.next())
			{
				EmpOffBean emp=new EmpOffBean();
			    String city=lh.getLKP_Desc("CITY", rs.getInt("city"));	
				emp.setPrj_srno(rs.getInt("Site_ID"));
				emp.setPrj_code(rs.getString("Project_Code"));
				emp.setPrj_name(/*city+" - "+*/rs.getString("site_Add"));
				emp.setSite_name(rs.getString("Site_Name"));
				emp.setSite_id(rs.getString("SITE_ID"));
				
				
				/*System.out.print(rs.getInt("Site_ID"));
				System.out.print(rs.getString("Project_Code"));
				System.out.println(rs.getString("Site_Name"));*/

				
				list.add(emp);
			}
			con.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TranBean> getEmpList(String prjCode){
		
		ArrayList<TranBean> list= new ArrayList<TranBean>();
		Connection con = null;
		ResultSet rs = null;
		
		//String query = "SELECT EMPNO FROM EMPTRAN WHERE PRJ_CODE='"+prjCode+"'";
		//String query = "select emp.EMPNO from EMPMAST emp inner join EMPTRAN etn on emp.EMPNO = etn.EMPNO where emp.STATUS = 'A'  and etn.PRJ_SRNO="+prjCode;
		
//		String query = "select emp.EMPNO from EMPMAST emp inner join EMPTRAN etn on emp.EMPNO = etn.EMPNO where emp.STATUS = 'A' and etn.PRJ_SRNO='"+prjCode+"' and etn.SRNO = (select MAX(en.SRNO) from EMPTRAN en where en.PRJ_SRNO = '"+prjCode+"' and etn.EMPNO = en.EMPNO )";
		String query = "select emp.EMPNO from EMPMAST emp inner join EMPTRAN etn on emp.EMPNO = etn.EMPNO where emp.STATUS = 'A' and etn.PRJ_SRNO="+prjCode+"  AND etn.SRNO=(SELECT MAX(ET.SRNO) FROM EMPTRAN ET WHERE ET.EMPNO=emp.EMPNO) order by emp.EMPNO";
		
		String queryall = "select emp.EMPNO from EMPMAST emp inner join EMPTRAN etn on emp.EMPNO = etn.EMPNO where emp.STATUS = 'A'  AND etn.SRNO=(SELECT MAX(ET.SRNO) FROM EMPTRAN ET WHERE ET.EMPNO=emp.EMPNO) order by emp.empcode";
		
		con = ConnectionManager.getConnection();
		try
		{
			Statement st = con.createStatement();
			
			
			if(prjCode.equalsIgnoreCase("1000")){
				System.out.println("Query for all project"+queryall);
				rs = st.executeQuery(queryall);
			}
			else{
				System.out.println("Query for Single  project"+query);
				rs = st.executeQuery(query);
			}
			//rs = st.executeQuery(query);
			while(rs.next())
			{
				TranBean trbn = new TranBean();
				trbn.setEMPNO(rs.getInt(1));
				list.add(trbn);
			}
			rs.close();
			st.close();
			con.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	public ArrayList<TranBean> getLateEmpList(String empno){
		
		ArrayList<TranBean> list= new ArrayList<TranBean>();
		Connection con = null;
		ResultSet rs = null;
		
		//String query = "SELECT EMPNO FROM EMPTRAN WHERE PRJ_CODE='"+prjCode+"'";
		//String query = "select emp.EMPNO from EMPMAST emp inner join EMPTRAN etn on emp.EMPNO = etn.EMPNO where emp.STATUS = 'A'  and etn.PRJ_SRNO="+prjCode;
		
//		String query = "select emp.EMPNO from EMPMAST emp inner join EMPTRAN etn on emp.EMPNO = etn.EMPNO where emp.STATUS = 'A' and etn.PRJ_SRNO='"+prjCode+"' and etn.SRNO = (select MAX(en.SRNO) from EMPTRAN en where en.PRJ_SRNO = '"+prjCode+"' and etn.EMPNO = en.EMPNO )";
		String query = "select emp.EMPNO from EMPMAST emp inner join EMPTRAN etn on emp.EMPNO = etn.EMPNO where emp.STATUS = 'A' and etn.empno="+empno+"  AND etn.SRNO=(SELECT MAX(ET.SRNO) FROM EMPTRAN ET WHERE ET.EMPNO=emp.EMPNO) order by emp.EMPNO";
		
		System.out.println(query);;
		con = ConnectionManager.getConnection();
		try
		{
			Statement st = con.createStatement();
			rs = st.executeQuery(query);
			while(rs.next())
			{
				TranBean trbn = new TranBean();
				trbn.setEMPNO(rs.getInt(1));
				list.add(trbn);
			}
			rs.close();
			st.close();
			con.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return list;
	}
	
	
public ArrayList<TranBean> getEmpList_Status(String prjCode,String month){
		
		ArrayList<TranBean> list= new ArrayList<TranBean>();
		Connection con = null;
		ResultSet rs = null;
		String dt="25-"+month;
		/*String query = "select emp.EMPNO from EMPMAST emp inner join EMPTRAN etn on " +
				       "emp.EMPNO = etn.EMPNO join SAL_DETAILS s on emp.EMPNO = s.EMPNO where emp.STATUS = 'A'" +
				       " and etn.PRJ_SRNO='"+prjCode+"' " +
				       "and etn.STATUS=1 and s.SAL_STATUS <> 'FINALIZED' and s.SAL_MONTH = '"+month+"'";*/
		
		String query = " SELECT E.EMPNO FROM EMPMAST E,EMPTRAN T  WHERE E.EMPNO = T.EMPNO AND T.PRJ_SRNO ="+prjCode+
						" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)"+
						" AND (( E.STATUS='A' AND E.DOJ <= dateadd(dd,-(DAY(DATEADD(mm,1,CONVERT(datetime,  REPLACE('"+month+"', '-', ' '), 106))))," +
						" DATEADD(mm,1,CONVERT(datetime,  REPLACE('"+month+"', '-', ' '), 106)))  ) or" +
						" (E.STATUS ='N' And DATEPART(year,  E.DOL)=DATEPART(year,CONVERT(datetime,  REPLACE('"+month+"', '-', ' '), 106)) " +
						" And DATEPART(mm,  E.DOL)=DATEPART(mm,CONVERT(datetime,  REPLACE('"+month+"', '-', ' '), 106)))) AND  " +
						" E.EMPNO not in (select Empno from sal_details S where S.SAL_STATUS = 'FINALIZED' AND S.SAL_MONTH='"+month+"')"+
						"   and E.EMPNO in (select distinct empno from paytran where trndt between '"+ReportDAO.BOM(dt)+"' and '"+ReportDAO.EOM(dt)+"' ) ORDER BY E.EMPNO";
		
		//System.out.println(query);
		
		con = ConnectionManager.getConnection();
		try
		{
			Statement st = con.createStatement();
			rs = st.executeQuery(query);
			while(rs.next())
			{
				TranBean trbn = new TranBean();
				trbn.setEMPNO(rs.getInt(1));
				list.add(trbn);
			}
			rs.close();
			st.close();
			con.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	


public ArrayList<TranBean> getEmpList1(int prjCode,String month){
	
	ArrayList<TranBean> list= new ArrayList<TranBean>();
	Connection con = null;
	ResultSet rs = null;
	
	/*String query = "select emp.EMPNO from EMPMAST emp inner join EMPTRAN etn on " +
			       "emp.EMPNO = etn.EMPNO join SAL_DETAILS s on emp.EMPNO = s.EMPNO where emp.STATUS = 'A'" +
			       " and etn.PRJ_SRNO='"+prjCode+"' " +
			       "and etn.STATUS=1 and s.SAL_STATUS <> 'FINALIZED' and s.SAL_MONTH = '"+month+"'";*/
	
	String query = "select emp.EMPNO from EMPMAST emp inner join EMPTRAN etn on emp.EMPNO = etn.EMPNO where emp.STATUS = 'A' and etn.PRJ_SRNO='"+prjCode+"' and etn.STATUS=1 order by emp.EMPNO";
	
	
	
	
	con = ConnectionManager.getConnection();
	try
	{
		Statement st = con.createStatement();
		rs = st.executeQuery(query);
		while(rs.next())
		{
			TranBean trbn = new TranBean();
			trbn.setEMPNO(rs.getInt(1));
			list.add(trbn);
		}
		rs.close();
		st.close();
		con.close();
	}
	catch (SQLException e) 
	{
		e.printStackTrace();
	}
	return list;
}


	
	public EmpOffBean getEmpOfficAddInfo(String EMPNO)
	{
		ResultSet rs=null;
		Statement st = null;
		EmpOffBean empaddofficinfo = new EmpOffBean();
		try
		{
			con = ConnectionManager.getConnection();
			st = con.createStatement();
			rs=st.executeQuery("select * from emptran where empno = '"+EMPNO+"' and SRNO = (select MAX(SRNO) from emptran where empno = '"+EMPNO+"')");
		//	rs=st.executeQuery("select * from emptran where empno = '"+EMPNO+"'");
			
			while(rs.next())
			{
				String Prj_Name = null;
				String Prj_Code = null;
				con = ConnectionManager.getConnectionTech();
				Statement stmt = con.createStatement();
			//	ResultSet rs1 = stmt.executeQuery("SELECT Site_Name, Project_Code from Project_Sites where Project_Code='"+rs.getString("PRJ_CODE")+"'");
				ResultSet rs1 = stmt.executeQuery("SELECT Site_Name, Project_Code from Project_Sites where Site_isdeleted = 0 and Site_ID='"+rs.getString("PRJ_SRNO")+"'");
				while(rs1.next()){
					Prj_Name = rs1.getString(1);
					Prj_Code = rs1.getString(2);
				}
				rs1.close();
				stmt.close();
			
			
			/*while(rs.next())
			{*/
				empaddofficinfo.setACNO(rs.getString("ACNO")==null?"":rs.getString("ACNO"));
				empaddofficinfo.setDESIG(rs.getString("DESIG")==null?0:rs.getInt("DESIG"));
				empaddofficinfo.setDEPT(rs.getString("DEPT")==null?0:rs.getInt("DEPT"));
				empaddofficinfo.setEFFDATE(rs.getDate("EFFDATE")==null?"":dateFormat(rs.getDate("EFFDATE")));
				empaddofficinfo.setEMPNO(rs.getString("EMPNO")==null?0:rs.getInt("EMPNO"));
				empaddofficinfo.setBRANCH(rs.getString("BRANCH")==null?0:rs.getInt("BRANCH"));
				empaddofficinfo.setGRADE(rs.getString("GRADE")==null?0:rs.getInt("GRADE"));
				empaddofficinfo.setORDER_DT(rs.getDate("ORDER_DT")==null?"":dateFormat(rs.getDate("ORDER_DT")));
				empaddofficinfo.setORDER_NO(rs.getString("ORDER_NO")==null?"":rs.getString("ORDER_NO"));
				empaddofficinfo.setSRNO(rs.getString("SRNO")==null?0:rs.getInt("SRNO"));
				empaddofficinfo.setSTATUS(rs.getString("STATUS")==null?0:rs.getInt("STATUS"));
				empaddofficinfo.setTRNCD(rs.getString("TRNCD")==null?0:rs.getInt("TRNCD"));
				empaddofficinfo.setMDESC(rs.getString("MDESC")==null?"":rs.getString("MDESC"));
				empaddofficinfo.setPrj_srno(rs.getString("PRJ_SRNO")==null?0:rs.getInt("PRJ_SRNO"));
				empaddofficinfo.setBANK_NAME(rs.getString("BANK_NAME")==null?0:rs.getInt("BANK_NAME"));
				empaddofficinfo.setPrj_code(Prj_Code);
				empaddofficinfo.setPrj_name(Prj_Name);
				
			}
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return empaddofficinfo;
	}
	
	
	public void insertInitialTran(EmpOffBean eob)
	{
		try
		{
			con = ConnectionManager.getConnection();
			st = con.createStatement();
			
			st.executeUpdate("insert into emptran (EMPNO,EFFDATE,SRNO,ORDER_DT,PRJ_SRNO,STATUS,MNGR_ID)" +
					" values("+eob.getEMPNO()+",'"+eob.getEFFDATE()+"',1,'"+eob.getEFFDATE()+"',"+eob.getPrj_srno()+",1,0)");
			con.close();
			System.out.println("INSERTED SUCCESSFULY........");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		
	}
	
public ArrayList<Retirement_ext_leave_credit> getEmployeeList(){
		System.out.println("Aj1..");
		ArrayList<Retirement_ext_leave_credit> list= new ArrayList<Retirement_ext_leave_credit>();
		Connection con = null;
		ResultSet rs = null;
		
		String SQL = ""
				+ "select e.empno,e.EMPCODE,rtrim(e.FNAME)+' '+rtrim(e.MNAME)+' '+rtrim(e.LNAME) as NAME,e.retirementdate,l.BALDT, (select convert(date, DATEADD(yy, DATEDIFF(yy, 0, l.BALDT) + 1, -1))) as untildate,"
				+ "((select CRLIM  from leavemass   where LEAVECD =3 and  STATUS ='A')- l.TOTCR ) as leavedays "
				+ " ,l.TOTCR as credited   from empmast e , leavebal l "
				+ "where "
				+ "e.retirementdate < e.RETIREMENT_EXT_PERIOD "
				+ "and e.STATUS ='A' "
				+ "and e.EMPNO =l.EMPNO and l.LEAVECD =3 and l.BALDT= "
				+ "(select  convert(date,  DATEADD(yy, DATEDIFF(yy, 0, e.retirementdate), 0))) "
				+ "and l.srno = "
				+ "(select max(l1.srno ) from leavebal l1 "
				+ "where l1.BALDT =l.BALDT and l1.EMPNO =l.EMPNO and l1.LEAVECD =l.LEAVECD and  l.EMPNO not in (select EMPNO  from  leavetran lt1  "
				+" where lt1.LEAVECD =3 and lt1.LEAVEPURP ='5' and lt1.EMPNO =l.EMPNO and lt1.TRNDATE =e.retirementdate ))";
		
		con = ConnectionManager.getConnection();
		try
		{
			Statement st = con.createStatement();
			
				rs = st.executeQuery(SQL);
			
			while(rs.next())
			{
				Retirement_ext_leave_credit trbn = new Retirement_ext_leave_credit();
				trbn.setEmpno(rs.getString("empno"));
				trbn.setEmpcode(rs.getString("empcode"));
				trbn.setName(rs.getString("name"));
				trbn.setRetirementDate(rs.getString("retirementdate"));
				trbn.setBalDate(rs.getString("untildate"));
				trbn.setLeaveDays(rs.getString("leavedays"));
				trbn.setCredited(rs.getString("credited"));
				
				list.add(trbn);
			}
			rs.close();
			st.close();
			con.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return list;
	}
}