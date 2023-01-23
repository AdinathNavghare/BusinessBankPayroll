package payroll.DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import payroll.Model.LeaveMassBean;
import payroll.Model.LeaveMasterBean;
import payroll.Model.Lookup;

public class LeaveMasterHandler {

	public  boolean addLeave(LeaveMasterBean lbean) 
	{
		boolean result=false;
		try
		{
			Connection con=ConnectionManager.getConnection();
			String insertquery="INSERT INTO  LEAVETRAN values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement Pstat=(PreparedStatement) con.prepareStatement(insertquery);
			Pstat.setInt(1,lbean.getLEAVECD());
			Pstat.setInt(2,lbean.getEMPNO());
		    Pstat.setString(3, lbean.getTRNDATE());
		    Pstat.setString(4, lbean.getTRNTYPE());
		    Pstat.setString(5, lbean.getAPPLNO());
		    Pstat.setInt(6, lbean.getBRCODE());
		    Pstat.setInt(7, lbean.getLEAVEPURP());
		    Pstat.setString(8, lbean.getLREASON());
		    Pstat.setString(9, lbean.getLADDR());
		    Pstat.setDouble(10, lbean.getLTELNO());
		    Pstat.setString(11, lbean.getAPPLDT());
		    Pstat.setString(12, lbean.getFRMDT());
		    Pstat.setString(13, lbean.getTODT());
		    Pstat.setString(14, lbean.getSANCAUTH());
		    Pstat.setString(15, lbean.getOPR_CD());
		    Pstat.setString(16, lbean.getOFF_CD());
		    Pstat.setInt(17, lbean.getSTATUS());
		    Pstat.setString(18, lbean.getSUBSTITUTE());
		    Pstat.setFloat(19, lbean.getNODAYS());
		    Pstat.executeUpdate();
		    result=true;
		   con.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return result;
	}

	public  ArrayList<LeaveMasterBean> leaveDisplay(int empno1)
	{
		ResultSet rs=null;  
		ArrayList<LeaveMasterBean> List1 =new ArrayList<LeaveMasterBean>();
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			LookupHandler objDesc=new LookupHandler();
			String Result1;
			String query="SELECT * FROM LEAVETRAN   WHERE empno='"+empno1+"' and TRNTYPE<>'C' AND LEAVEPURP<>5 order by trndate desc";
			rs=st.executeQuery(query);
			while(rs.next())
			{
				LeaveMasterBean lbean1=new LeaveMasterBean();
				lbean1.setLEAVECD(rs.getString(1)==null?0:rs.getInt(1));
				lbean1.setEMPNO(rs.getString(2)==null?0:rs.getInt(2));
				lbean1.setTRNDATE(rs.getDate(3)==null?"":dateFormat(rs.getDate(3)));
				lbean1.setTRNTYPE(rs.getString(4)==null?"":rs.getString(4));
				lbean1.setAPPLNO(rs.getString(5)==null?"":rs.getString(5));
				lbean1.setBRCODE(rs.getString(6)==null?0:rs.getInt(6));
				lbean1.setLEAVEPURP(rs.getString(7)==null?0:rs.getInt(7));
				lbean1.setLREASON(rs.getString(8)==null?"":rs.getString(8));
				lbean1.setLADDR(rs.getString(9)==null?"":rs.getString(9));
				lbean1.setLTELNO(rs.getString(10)==null?0:rs.getLong(10));
				lbean1.setAPPLDT(rs.getDate(11)==null?"":dateFormat(rs.getDate(11)));
				lbean1.setFRMDT(rs.getDate(12)==null?"":dateFormat(rs.getDate(12)));
				lbean1.setTODT(rs.getDate(13)==null?"":dateFormat(rs.getDate(13)));
				
				Lookup lkhp = new Lookup();
				lkhp = objDesc.getLookup("SAUTH-"+rs.getInt(14));
				Result1 = lkhp.getLKP_DESC();
				lbean1.setSANCAUTH(Result1);
				lbean1.setOPR_CD(rs.getString(15)==null?"":rs.getString(15));
				lbean1.setOFF_CD(rs.getString(16)==null?"":rs.getString(16));
				lbean1.setSTATUS(rs.getString(17)==null?0:rs.getInt(17));
				lbean1.setSUBSTITUTE(rs.getString(18)==null?"":rs.getString(18));
				lbean1.setNODAYS(rs.getString(19)==null?0:rs.getFloat(19));
				List1.add(lbean1);
			}
			con.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}	
		return List1;
	}

	public  ArrayList<LeaveMasterBean> getList(int empno) throws SQLException
	{
		ResultSet rs1=null; 
		ArrayList<LeaveMasterBean> leavelist=new ArrayList<LeaveMasterBean>();
		Connection con=ConnectionManager.getConnection();
		Statement st=null;
		String query="select top 1  * from leavebal where empno="+empno+" and baldt <= getdate() order by baldt desc";
     	try
     	{
			st = con.createStatement();
			rs1=st.executeQuery(query);
			while(rs1.next())
			{
				LeaveMasterBean bean=new LeaveMasterBean();
				bean.setBALDT(rs1.getDate(1)==null?"":dateFormat(rs1.getDate(1)));
				bean.setEMPNO(rs1.getString(2)==null?0:rs1.getInt(2));
				bean.setLEAVECD(rs1.getString(3)==null?0:rs1.getInt(3));
				bean.setBAL(rs1.getString(4)==null?0:rs1.getInt(4));
				bean.setTOTCR(rs1.getString(5)==null?0:rs1.getInt(5));
				bean.setTOTDR(rs1.getString(6)==null?0:rs1.getInt(6));
				leavelist.add(bean);
			}
			con.close();
		}
     	catch (SQLException e) 
     	{
			e.printStackTrace();
		}
		return leavelist;
	}

	public  ArrayList<LeaveMasterBean> searchLeave(LeaveMasterBean searchbean) 
	{
		ResultSet rs4=null;
		String Result2;
		LookupHandler objDesc1=new LookupHandler();
		int leavecode=searchbean.getLEAVECD();
		ArrayList<LeaveMasterBean> searchlist=new ArrayList<LeaveMasterBean>();
		Connection con=ConnectionManager.getConnection();
		try
		{
		     Statement st=con.createStatement();
		     String searchquery="SELECT * FROM LEAVETRAN WHERE EMPNO='"+searchbean.getEMPNO()+"' AND LEAVECD = '"+searchbean.getLEAVECD()+"' AND TRNDATE  BETWEEN '"+searchbean.getFRMDT()+"' AND '"+searchbean.getTODT()+"' AND TRNTYPE<>'C' ORDER BY TRNDATE DESC";
	         String  searchquery1="SELECT * FROM LEAVETRAN WHERE EMPNO='"+searchbean.getEMPNO()+"' AND TRNDATE  BETWEEN '"+searchbean.getFRMDT()+"' AND '"+searchbean.getTODT()+"' AND TRNTYPE<>'C' ORDER BY TRNDATE DESC";
	         if(leavecode == 0)
		     {
		    	 rs4=st.executeQuery(searchquery1);
		     }
		     else
		     {
		    	 rs4=st.executeQuery(searchquery); 
		     }
		     while(rs4.next())
	         {
	        	 LeaveMasterBean lbean2=new LeaveMasterBean();
	        	 lbean2.setLEAVECD(rs4.getString(1)==null?0:rs4.getInt(1));
	        	 lbean2.setEMPNO(rs4.getString(2)==null?0:rs4.getInt(2));
	        	 lbean2.setTRNDATE(rs4.getDate(3)==null?"":dateFormat(rs4.getDate(3)));
	        	 lbean2.setTRNTYPE(rs4.getString(4)==null?"":rs4.getString(4));
	        	 lbean2.setAPPLNO(rs4.getString(5)==null?"":rs4.getString(5));
	        	 lbean2.setBRCODE(rs4.getString(6)==null?0:rs4.getInt(6));
	        	 lbean2.setLEAVEPURP(rs4.getString(7)==null?0:rs4.getInt(7));
	        	 lbean2.setLREASON(rs4.getString(8)==null?"":rs4.getString(8));
	        	 lbean2.setLADDR(rs4.getString(9)==null?"":rs4.getString(9));
	        	 lbean2.setLTELNO(rs4.getString(10)==null?0:rs4.getLong(10));
	        	 lbean2.setAPPLDT(rs4.getDate(11)==null?"":dateFormat(rs4.getDate(11)));
	        	 lbean2.setFRMDT(rs4.getDate(12)==null?"":dateFormat(rs4.getDate(12)));
	        	 lbean2.setTODT(rs4.getDate(13)==null?"":dateFormat(rs4.getDate(13)));
	        	 Lookup lkhp = new Lookup();
	        	 lkhp = objDesc1.getLookup("SAUTH-"+rs4.getInt(14));
	        	 Result2 = lkhp.getLKP_DESC();
	        	 lbean2.setSANCAUTH(Result2);
	        	 lbean2.setSANCAUTH(Result2);
	        	 lbean2.setOPR_CD(rs4.getString(15)==null?"":rs4.getString(15));
	        	 lbean2.setOFF_CD(rs4.getString(16)==null?"":rs4.getString(16));
	        	 lbean2.setSTATUS(rs4.getString(17)==null?0:rs4.getInt(17));
	        	 lbean2.setSUBSTITUTE(rs4.getString(18)==null?"":rs4.getString(18));
	        	 lbean2.setNODAYS(rs4.getString(19)==null?0:rs4.getFloat(19));
	        	 searchlist.add(lbean2);
	          }
		     con.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return searchlist;
	}

	public static String dateFormat(Date dates)
	{
		String result="";
		if(dates==null)
		{
			result="";
			return result;
		}
		SimpleDateFormat format= new SimpleDateFormat("dd-MMM-yyyy");
		result=format.format(dates);
		return result;
	}

	public ArrayList<LeaveMasterBean> getlast(int empno) 
	{
		ArrayList<LeaveMasterBean> listc=new ArrayList<LeaveMasterBean>();
		Connection con=ConnectionManager.getConnection();
		ResultSet rs5;
		String Result3;
		LookupHandler objDesc1=new LookupHandler();
		String query="SELECT distinct * from LEAVETRAN a where a.EMPNO='"+empno+"' AND TRNDATE=(select distinct max(TRNDATE) from LEAVETRAN b where EMPNO='"+empno+"' AND LEAVECD=a.LEAVECD) AND STATUS = 41 AND STATUS <> 98";
		try
		{
			Statement st=con.createStatement();
			rs5=st.executeQuery(query);
			while(rs5.next())
			{
				LeaveMasterBean bean3=new LeaveMasterBean();
				bean3.setLEAVECD(rs5.getString(1)==null?0:rs5.getInt(1));
				bean3.setEMPNO(rs5.getString(2)==null?0:rs5.getInt(2));
				bean3.setTRNDATE(rs5.getDate(3)==null?"":dateFormat(rs5.getDate(3)));
				bean3.setTRNTYPE(rs5.getString(4)==null?"":rs5.getString(4));
				bean3.setAPPLNO(rs5.getString(5)==null?"":rs5.getString(5));
				bean3.setBRCODE(rs5.getString(6)==null?0:rs5.getInt(6));
				bean3.setLEAVEPURP(rs5.getString(7)==null?0:rs5.getInt(7));
				bean3.setLREASON(rs5.getString(8)==null?"":rs5.getString(8));
				bean3.setLADDR(rs5.getString(9)==null?"":rs5.getString(9));
				bean3.setLTELNO(rs5.getString(10)==null?0:rs5.getLong(10));
				bean3.setAPPLDT(rs5.getDate(11)==null?"":dateFormat(rs5.getDate(11)));
				bean3.setFRMDT(rs5.getDate(12)==null?"":dateFormat(rs5.getDate(12)));
				bean3.setTODT(rs5.getDate(13)==null?"":dateFormat(rs5.getDate(13)));
				Lookup lkhp = new Lookup();
				lkhp = objDesc1.getLookup("SAUTH-"+rs5.getInt(14));
				Result3 = lkhp.getLKP_DESC();
				bean3.setSANCAUTH(Result3);
				bean3.setOPR_CD(rs5.getString(15)==null?"":rs5.getString(15));
				bean3.setOFF_CD(rs5.getString(16)==null?"":rs5.getString(16));
				bean3.setSTATUS(rs5.getString(17)==null?0:rs5.getInt(17));
				bean3.setSUBSTITUTE(rs5.getString(18)==null?"":rs5.getString(18));
				bean3.setNODAYS(rs5.getString(19)==null?0:rs5.getFloat(19));
				listc.add(bean3);	 
			}
			con.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return listc;
	}

	public  boolean upDateStatus(LeaveMasterBean cancelbean) 
	{
		boolean flag=false;
		Connection con=ConnectionManager.getConnection();
		ResultSet rs7=null;
		ResultSet rs8=null;
		try
		{
			String update="UPDATE LEAVETRAN SET STATUS='"+cancelbean.getSTATUS()+"' WHERE EMPNO ='"+cancelbean.getEMPNO()+"' AND LEAVECD='"+cancelbean.getLEAVECD()+"' AND APPLNO='"+cancelbean.getAPPLNO()+"'";
			String noofday="SELECT DATEDIFF(day,'"+cancelbean.getFRMDT()+"','"+cancelbean.getTODT()+"') AS DiffDate";
			String getballeave="SELECT distinct * from LEAVEBAL a where a.EMPNO='"+cancelbean.getEMPNO()+"' AND BALDT=(select distinct max(BALDT) from LEAVEBAL b where EMPNO='"+cancelbean.getEMPNO() +"' AND LEAVECD='"+cancelbean.getLEAVECD()+"')";
			Statement st3=con.createStatement();
			Statement st1=con.createStatement();
			int p=st1.executeUpdate(update);
			rs7=st3.executeQuery(noofday);
			rs7.next();
			int canceldays=Integer.parseInt(rs7.getString(1));
			rs8=st1.executeQuery(getballeave);
			int bal=0;
			int  tocr=0;
			int todr = 0;
			while(rs8.next())
			{
				String  baldate=rs8.getString(1);
				bal = Integer.parseInt(rs8.getString(4)==null?"0":rs8.getString(4));
				tocr=Integer.parseInt(rs8.getString(5)==null?"0":rs8.getString(5));
			    todr=Integer.parseInt(rs8.getString(6)==null?"0":rs8.getString(6));
			}
			bal=bal+canceldays;
			todr=todr-canceldays;
			String updateLeavBal="UPDATE LEAVEBAL  SET BAL='"+bal+"',TOTDR='"+todr+"' WHERE EMPNO='"+cancelbean.getEMPNO()+"' AND LEAVECD='"+cancelbean.getLEAVECD()+"' AND BALDT=(select distinct max(BALDT) from LEAVEBAL b where EMPNO='"+cancelbean.getEMPNO()+"' AND LEAVECD='"+cancelbean.getLEAVECD()+"')";
			st3.executeUpdate(updateLeavBal);
			if(p==1)
			{
				flag=true;
			}
			con.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return flag;
	}

	public  boolean addSubstitute(LeaveMasterBean subBean) 
	{
		boolean flag2=false;
		String query="INSERT INTO SUBSTITUTE VALUES(?,?,?,?,?,?,?) ";
		Connection con=ConnectionManager.getConnection();
		try
		{
			PreparedStatement ps=con.prepareStatement(query);
			Statement st = con.createStatement();
			int srno =0;
			String maxsrno="select max(srno) from substitute where applno='"+subBean.getAPPLNO()+"'";
			ResultSet rs = st.executeQuery(maxsrno);
			while(rs.next())
			{
				srno = rs.getInt(1);
			}
        	srno=srno+1;
			ps.setInt(1,subBean.getEMPNO());
			ps.setString(2,subBean.getAPPLNO());
			ps.setInt(3,srno);
			ps.setInt(4, subBean.getSUBSTCD());
			ps.setString(5, subBean.getFRMDT());
			ps.setString(6, subBean.getTODT());
			ps.setInt(7, subBean.getSTATUS());
			ps.executeUpdate();
			flag2=true;
			con.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return flag2;
	}
	
	////for empcode  and transactiondate by akshay
	
	public String getempCode (int empno) 
	{
	Connection con=ConnectionManager.getConnection();
	String sqlName="SELECT EMPCODE FROM EMPMAST WHERE EMPNO='"+empno+"'";
	ResultSet rs9=null;
	String empcd="";
	//System.out.println(sqlName);
	try
	{
		Statement st=con.createStatement();
		rs9=st.executeQuery(sqlName);
		rs9.next();
		//empname=(rs9.getString("FNAME"))+" "+(rs9.getString("LNAME"));
		empcd=(rs9.getString("EMPCODE"));
		con.close();
	}
	catch( SQLException e)
	{
		e.printStackTrace();
	}
	return empcd;

	
}
	
	public String gettrndt (int empno) 
	{
	Connection con=ConnectionManager.getConnection();
	String sqlName="SELECT TRNDT FROM PAYTRAN WHERE EMPNO='"+empno+"'";
	ResultSet rs9=null;
	String trndt="";
	//System.out.println(sqlName);
	try
	{
		Statement st=con.createStatement();
		rs9=st.executeQuery(sqlName);
		rs9.next();
		//empname=(rs9.getString("FNAME"))+" "+(rs9.getString("LNAME"));
		trndt=(rs9.getString("TRNDT"));
		con.close();
	}
	catch( SQLException e)
	{
		e.printStackTrace();
	}
	return trndt;

	
}
	
	
	////

	public String getempName(int empno) 
	{
		Connection con=ConnectionManager.getConnection();
		String sqlName="SELECT FNAME, MNAME, LNAME FROM EMPMAST WHERE EMPNO='"+empno+"'";
		ResultSet rs9=null;
		String empname="";
		//System.out.println(sqlName);
		try
		{
			Statement st=con.createStatement();
			rs9=st.executeQuery(sqlName);
			rs9.next();
			//empname=(rs9.getString("FNAME"))+" "+(rs9.getString("LNAME"));
			empname=(rs9.getString("FNAME"))+" "+(rs9.getString("MNAME"))+" "+(rs9.getString("LNAME"));
			con.close();
		}
		catch( SQLException e)
		{
			e.printStackTrace();
		}
		return empname;
	}

	public ArrayList<LeaveMasterBean> getsubsList(LeaveMasterBean subBean1) 
	{
		LeaveMasterBean subBean=new LeaveMasterBean();
		subBean=subBean1;
		ArrayList<LeaveMasterBean> substList=new ArrayList<LeaveMasterBean>();
		String sql="SELECT * FROM SUBSTITUTE WHERE EMPNO='"+subBean.getEMPNO()+"' AND APPLNO='"+ subBean.getAPPLNO()+"' AND STATUS <>98";
		ResultSet Rs=null;
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			Rs=st.executeQuery(sql);
			while(Rs.next())
			{
				LeaveMasterBean sub =new LeaveMasterBean();
				sub.setEMPNO(Rs.getString(1)==null?0:Rs.getInt(1));
				sub.setAPPLNO(Rs.getString(2)==null?"":Rs.getString(2));
				sub.setSRNO(Rs.getString(3)==null?0:Rs.getInt(3));
				sub.setSUBSTCD(Rs.getString(4)==null?0:Rs.getInt(4));
				sub.setFRMDT(Rs.getDate(5)==null?"":dateFormat(Rs.getDate(5)));
				sub.setTODT(Rs.getDate(6)==null?"":dateFormat(Rs.getDate(6)));
				sub.setSTATUS(Rs.getString(7)==null?0:Rs.getInt(7));
				substList.add(sub);
			}
			con.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return substList;
	}

	public boolean editSubstitute(LeaveMasterBean editbean) 
	{
		boolean flag=false;
		String updateQuery="UPDATE SUBSTITUTE SET SUBSTCD='"+editbean.getSUBSTCD()+"',FROMDATE='"+editbean.getFRMDT()+"',TODATE='"+ editbean.getTODT()+"' WHERE EMPNO='"+editbean.getEMPNO()+"'AND APPLNO='"+editbean.getAPPLNO()+"' AND SRNO='"+editbean.getSRNO()+"'";
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			st.executeUpdate(updateQuery);
			flag=true;
			con.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	public boolean deleteSubstitute( LeaveMasterBean dlbean)
	{
		boolean f=false;
		Connection con=ConnectionManager.getConnection();
		String deleteQuery="UPDATE SUBSTITUTE SET STATUS='"+dlbean.getSTATUS()+"' WHERE EMPNO='"+dlbean.getEMPNO()+"' AND SRNO='"+dlbean.getSRNO()+"' AND SUBSTCD='"+dlbean.getSUBSTCD()+"'";
		try
		{
			Statement st=con.createStatement();
			st.executeUpdate(deleteQuery);
			f=true;
			con.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return f;
	}

	public ArrayList<LeaveMasterBean> getSanctionList() 
	{
		ArrayList<LeaveMasterBean> sanctionlist=new ArrayList<>();
		String sanctionquery="SELECT * FROM LEAVETRAN WHERE STATUS=1 AND ROWNUM <= 20";
		Connection conn=ConnectionManager.getConnection();
		try
		{
			Statement sts=conn.createStatement();
			ResultSet rss=sts.executeQuery(sanctionquery);
			while(rss.next())
			{
				LeaveMasterBean sancbean= new LeaveMasterBean();
				sancbean.setLEAVECD(rss.getString(1)==null?0:rss.getInt(1));
				sancbean.setEMPNO(rss.getString(2)==null?0:rss.getInt(2));
				sancbean.setTRNDATE(rss.getDate(3)==null?"":dateFormat(rss.getDate(3)));
				sancbean.setTRNTYPE(rss.getString(4)==null?"":rss.getString(4));
				sancbean.setAPPLNO(rss.getString(5)==null?"":rss.getString(5));
				sancbean.setBRCODE(rss.getString(6)==null?0:rss.getInt(6));
				sancbean.setLEAVEPURP(rss.getString(7)==null?0:rss.getInt(7));
				sancbean.setLREASON(rss.getString(8)==null?"":rss.getString(8));
				sancbean.setLADDR(rss.getString(9)==null?"":rss.getString(9));
				sancbean.setLTELNO(rss.getString(10)==null?0:rss.getLong(10));
				sancbean.setAPPLDT(rss.getDate(11)==null?"":dateFormat(rss.getDate(11)));
				sancbean.setFRMDT(rss.getDate(12)==null?"":dateFormat(rss.getDate(12)));
				sancbean.setTODT(rss.getDate(13)==null?"":dateFormat(rss.getDate(13)));
				String Result1;
				Lookup lkhp = new Lookup();
				LookupHandler objDesc1=new LookupHandler();
				lkhp = objDesc1.getLookup("SAUTH-"+rss.getString(14));
				Result1 = lkhp.getLKP_DESC();
				sancbean.setSANCAUTH(Result1);
				sancbean.setOPR_CD(rss.getString(15)==null?"":rss.getString(15));
				sancbean.setOFF_CD(rss.getString(16)==null?"":rss.getString(16));
				sancbean.setSTATUS(rss.getString(17)==null?0:rss.getInt(17));
				sancbean.setSUBSTITUTE(rss.getString(18)==null?"":rss.getString(18));
				sancbean.setNODAYS(rss.getString(19)==null?0:rss.getFloat(19));
				sanctionlist.add(sancbean);
			} 
			conn.close();
		}
		 catch( Exception e)
		 {
			 e.printStackTrace();
		}
		return sanctionlist;
	  }

	
	/* THIS method is write for sanctioning leave application it update status and also entry into leavebal table*/
	public boolean setSanction(String team) throws SQLException 
	{
		String key[] = team.split(":");
		String applno= key[0];
		int empno1 =Integer.parseInt(key[1]);
		boolean flag=false;
		Connection conn=ConnectionManager.getConnection();
		conn.setAutoCommit(false);
		String sql="UPDATE LEAVETRAN SET STATUS=41 WHERE EMPNO='"+empno1+"' AND APPLNO='"+applno+"'";
		String selectdetail="select * from leavetran where EMPNO='"+empno1+"' AND APPLNO='"+applno+"'";
		String fromdate="";
		String todate="";
		String leavecd="";
		try
		{
			Statement stmt=conn.createStatement();
			stmt.executeUpdate(sql);
			ResultSet rslt=stmt.executeQuery(selectdetail);
			while(rslt.next())
			{
				leavecd=rslt.getString("LEAVECD");
				fromdate=dateFormat(rslt.getDate("FRMDT"));
				todate=dateFormat(rslt.getDate("TODT"));
			}
			Statement stmt6=conn.createStatement(); 
			String days = "SELECT DATEDIFF(day,'"+fromdate+"','"+todate+"') AS DiffDate";
			ResultSet rs1;
			int day = 0;
			rs1=stmt6.executeQuery(days);
			rs1.next();
			if (rs1 != null)
			{
				day =Integer.parseInt( rs1.getString(1))+1;
			} 
			ResultSet rs3;
			String querybal="SELECT distinct bal,totdr,TOTCR FROM LEAVEBAL a where a.EMPNO='"+empno1+"' AND BALDT=(select distinct max(BALDT) from LEAVEBAL b where EMPNO='"+empno1+"' AND LEAVECD ='"+leavecd+"')";
			rs3=stmt6.executeQuery(querybal);
			int bal=0;
			int totdr=0;
			int tcredit=50;
			while(rs3.next())
			{
				bal=Integer.parseInt(rs3.getString(1)==null?"0":rs3.getString(1));
				totdr=Integer.parseInt(rs3.getString(2)==null?"0":rs3.getString(2));
				tcredit=Integer.parseInt(rs3.getString(3)==null?"0":rs3.getString(3));
			}
			day=day+totdr;
			bal=tcredit-day;
			String Baldate=getDateMethod();
			String query2="INSERT INTO LEAVEBAL VALUES('"+Baldate+"','"+empno1+"','"+leavecd+"','"+bal+"','"+tcredit+"','"+day+"')";
			stmt6.executeUpdate(query2);
			conn.commit();
			flag=true;
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	public ArrayList<LeaveMasterBean> getSanctionedList()
	{
		ArrayList<LeaveMasterBean> sanclist =new ArrayList<>();
        String sanctionquery="SELECT * FROM LEAVETRAN WHERE STATUS=41 AND ROWNUM <= 20";
        Connection conn=ConnectionManager.getConnection();
        try
        {
        	Statement sts=conn.createStatement();
        	ResultSet rss1=sts.executeQuery(sanctionquery);
			while(rss1.next())
			{
				LeaveMasterBean sancbean2= new LeaveMasterBean();
				sancbean2.setLEAVECD(rss1.getString(1)==null?0:rss1.getInt(1));
				sancbean2.setEMPNO(rss1.getString(2)==null?0:rss1.getInt(2));
				sancbean2.setTRNDATE(rss1.getDate(3)==null?"":dateFormat(rss1.getDate(3)));
				sancbean2.setTRNTYPE(rss1.getString(4)==null?"":rss1.getString(4));
				sancbean2.setAPPLNO(rss1.getString(5)==null?"":rss1.getString(5));
				sancbean2.setBRCODE(rss1.getString(6)==null?0:rss1.getInt(6));
				sancbean2.setLEAVEPURP(rss1.getString(7)==null?0:rss1.getInt(7));
				sancbean2.setLREASON(rss1.getString(8)==null?"":rss1.getString(8));
				sancbean2.setLADDR(rss1.getString(9)==null?"":rss1.getString(9));
				sancbean2.setLTELNO(rss1.getString(10)==null?0:rss1.getLong(10));
				sancbean2.setAPPLDT(rss1.getDate(11)==null?"":dateFormat(rss1.getDate(11)));
				sancbean2.setFRMDT(rss1.getDate(12)==null?"":dateFormat(rss1.getDate(12)));
				sancbean2.setTODT(rss1.getDate(13)==null?"":dateFormat(rss1.getDate(13)));
				String Result1;
				Lookup lkhp = new Lookup();
				LookupHandler objDesc1=new LookupHandler();
				lkhp = objDesc1.getLookup("SAUTH-"+rss1.getString(14));
				Result1 = lkhp.getLKP_DESC();
				sancbean2.setSANCAUTH(Result1);
				sancbean2.setOPR_CD(rss1.getString(15)==null?"":rss1.getString(15));
				sancbean2.setOFF_CD(rss1.getString(16)==null?"":rss1.getString(16));
				sancbean2.setSTATUS(rss1.getString(17)==null?0:rss1.getInt(17));
				sancbean2.setSUBSTITUTE(rss1.getString(18)==null?"":rss1.getString(18));
				sanclist.add(sancbean2);
			}
			conn.close();
        }
        catch( Exception e)
        {
        	e.printStackTrace();
		}
        return sanclist;
	}

	public ArrayList<LeaveMasterBean> getSearch(LeaveMasterBean sancBean1) 
	{
		ArrayList<LeaveMasterBean> list3 =new ArrayList<>();
		String type=sancBean1.getSerachtype();
		Connection con=ConnectionManager.getConnection();
		String searchAll="SELECT * FROM LEAVETRAN  WHERE EMPNO between '"+sancBean1.getEMPNO()+"' AND '"+sancBean1.getEMPNO2()+"' AND FRMDT between '"+sancBean1.getFRMDT()+"'AND '"+sancBean1.getTODT()+"' AND  TODT between '"+sancBean1.getFRMDT()+"'AND '"+sancBean1.getTODT()+"'";
		String searchSanction="SELECT * FROM LEAVETRAN  WHERE EMPNO between '"+sancBean1.getEMPNO()+"' AND '"+sancBean1.getEMPNO2()+"' AND FRMDT between '"+sancBean1.getFRMDT()+"'AND '"+sancBean1.getTODT()+"' AND  TODT between '"+sancBean1.getFRMDT()+"'AND '"+sancBean1.getTODT()+"' AND  STATUS=41";
		String searchPending="SELECT * FROM LEAVETRAN  WHERE EMPNO between '"+sancBean1.getEMPNO()+"' AND '"+sancBean1.getEMPNO2()+"' AND FRMDT between '"+sancBean1.getFRMDT()+"'AND '"+sancBean1.getTODT()+"' AND  TODT between '"+sancBean1.getFRMDT()+"'AND '"+sancBean1.getTODT()+"' AND  STATUS=1";
		try
		{
			ResultSet rslist=null;
			Statement stmt1=con.createStatement();
			if(type.equals("All"))
			{
				rslist=stmt1.executeQuery(searchAll) ;
			}
			if(type.equals("pending"))
			{
				rslist=stmt1.executeQuery(searchPending);  
			}
			if(type.equals("sanction"))
			{
				 rslist=stmt1.executeQuery(searchSanction) ; 
			}
			while(rslist.next())
			{
				LeaveMasterBean listbean3=new LeaveMasterBean();
				listbean3.setLEAVECD(rslist.getString(1)==null?0:rslist.getInt(1));
				listbean3.setEMPNO(rslist.getString(2)==null?0:rslist.getInt(2));
				listbean3.setTRNDATE(rslist.getDate(3)==null?"":dateFormat(rslist.getDate(3)));
				listbean3.setTRNTYPE(rslist.getString(4)==null?"":rslist.getString(4));
				listbean3.setAPPLNO(rslist.getString(5)==null?"":rslist.getString(5));
				listbean3.setBRCODE(rslist.getString(6)==null?0:rslist.getInt(6));
				listbean3.setLEAVEPURP(rslist.getString(7)==null?0:rslist.getInt(7));
				listbean3.setLREASON(rslist.getString(8)==null?"":rslist.getString(8));
				listbean3.setLADDR(rslist.getString(9)==null?"":rslist.getString(9));
				listbean3.setLTELNO(rslist.getString(10)==null?0:rslist.getLong(10));
				listbean3.setAPPLDT(rslist.getDate(11)==null?"":dateFormat(rslist.getDate(11)));
				listbean3.setFRMDT(rslist.getDate(12)==null?"":dateFormat(rslist.getDate(12)));
				listbean3.setTODT(rslist.getDate(13)==null?"":dateFormat(rslist.getDate(13)));
				String Result1;
				Lookup lkhp = new Lookup();
				LookupHandler objDesc1=new LookupHandler();
				lkhp = objDesc1.getLookup("SAUTH-"+rslist.getInt(14));
				Result1 = lkhp.getLKP_DESC();
				listbean3.setSANCAUTH(Result1);
				listbean3.setOPR_CD(rslist.getString(15)==null?"":rslist.getString(15));
				listbean3.setOFF_CD(rslist.getString(16)==null?"":rslist.getString(16));
				listbean3.setSTATUS(rslist.getString(17)==null?0:rslist.getInt(17));
				listbean3.setSUBSTITUTE(rslist.getString(18)==null?"":rslist.getString(18));
				list3.add(listbean3);
			}
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list3;
	}
	
	public String getDateMethod()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date date = new Date();
		String yourDate = dateFormat.format(date);
		return yourDate;
	}
	
	public boolean addleavecdDetail(LeaveMassBean objbean)
	{
		boolean flag=false;
		Connection con = ConnectionManager.getConnection();
		Statement st;
		int SRNO=1;
		
		String insert="INSERT INTO LEAVEMASS values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,'A')";
		try 
		{
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select max(SRNO) from LEAVEMASS");
			if(rs.next())
			{
				SRNO = rs.getInt(1) + 1;		
			}
			PreparedStatement pstmt=con.prepareStatement(insert);
			pstmt.setString(1,objbean.getEFFDATE());
			pstmt.setInt(2, objbean.getLEAVECD());
			pstmt.setString(3, objbean.getFREQUENCY());
		    pstmt.setInt(4, objbean.getCRLIM());
			pstmt.setInt(5,objbean.getMAXCUMLIM());
			pstmt.setInt(6,objbean.getMAXCF());
			pstmt.setInt(7,objbean.getMINLIM());
			pstmt.setString(8, objbean.getFBEGINDATE());
			pstmt.setString(9, objbean.getFENDDATE());
			pstmt.setString(10, objbean.getLEAVEDES());
			pstmt.setString(11, objbean.getCONS_HOLIDAYS());
			pstmt.setString(12, objbean.getLEAVEINCASH());
			pstmt.setInt(13, SRNO);
			pstmt.setString(14, objbean.getWEEKOFF());
			
			pstmt.executeUpdate();
			flag=true;
			con.close();
		}
		catch(Exception e)
		{
	        e.printStackTrace();
		}
		return flag;
	}

	public ArrayList<LeaveMassBean> getleavetypeList(String sql)
	{
	    ArrayList<LeaveMassBean> alist=new ArrayList<>();
	    Connection con = ConnectionManager.getConnection();
	    try
	    {
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				LeaveMassBean lbean=new LeaveMassBean();
				lbean.setEFFDATE(rs.getDate(1)==null?"":dateFormat(rs.getDate(1)));
				lbean.setLEAVECD(rs.getString(2)==null?0:rs.getInt(2));
				lbean.setFREQUENCY(rs.getString(3)==null?"":rs.getString(3));
				lbean.setCRLIM(rs.getString(4)==null?0:rs.getInt(4));
				lbean.setMAXCUMLIM(rs.getString(5)==null?0:rs.getInt(5));
				lbean.setMAXCF(rs.getString(6)==null?0:rs.getInt(6));
				lbean.setMINLIM(rs.getString(7)==null?0:rs.getInt(7));
				lbean.setFBEGINDATE(rs.getString(8)==null?"":dateFormat(rs.getDate(8)));
				lbean.setFENDDATE(rs.getString(9)==null?"":dateFormat( rs.getDate(9)));
				lbean.setLEAVEDES(rs.getString(10)==null?"":rs.getString(10));
				lbean.setCONS_HOLIDAYS(rs.getString(11)==null?"":rs.getString(11));
				lbean.setLEAVEINCASH(rs.getString(12)==null?"":rs.getString(12));
				lbean.setSRNO(rs.getString(13)==null?0:rs.getInt(13));
				lbean.setWEEKOFF(rs.getString(14)==null?"":rs.getString(14));
				alist.add(lbean);
			}
			con.close();
	    } 
	    catch (SQLException e) 
	    {
			e.printStackTrace();
		}
		return alist;
	}

	public boolean editLeavetype(LeaveMassBean objbean)
	{
		boolean flag=false;
		Connection con=ConnectionManager.getConnection();
		String update="update leavemass set " +
				"EFFDATE='"+objbean.getEFFDATE()+"'," +
				"FREQUENCY='"+objbean.getFREQUENCY() +"'," +
				"CRLIM='"+objbean.getCRLIM()+"'," +
				"MAXCUMLIM='"+objbean.getMAXCUMLIM()+"'," +
				"MAXCF='"+objbean.getMAXCF() +"'," +
				"MINLIM='"+objbean.getMINLIM() +"'," +
				"FBEGINDATE='"+objbean.getFBEGINDATE() +"'," +
				"FENDDATE='"+objbean.getFENDDATE() +"'," +
				"LEAVEDES='"+objbean.getLEAVEDES()+"'," +
				"LEAVEINCASH='"+objbean.getLEAVEINCASH()+"'," +
				"CONS_HOLIDAYS='"+objbean.getCONS_HOLIDAYS()+"'," +
				"CONS_WEEK_OFF='"+objbean.getWEEKOFF()+"'" +
				"where SRNO='"+objbean.getSRNO()+"'";
		try
		{
			Statement stmt=con.createStatement();
			stmt.executeUpdate(update);
			flag=true;
			con.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return flag;
	}
	public boolean dataCheckExist(int lcode, String bgdate, String enddate)
	{
		boolean flag=false;
		Connection con=ConnectionManager.getConnection();
		ResultSet rs;
		try
		{
			Statement st = con.createStatement();
			String query="Select * from LEAVEMASS where  LEAVECD= "+lcode ; 
					//" and  FBEGINDATE  between '"+bgdate+"' and '"+enddate+"'";
			rs=st.executeQuery(query);
			if(rs.next())
			{
				flag=true;
			}
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}
}
