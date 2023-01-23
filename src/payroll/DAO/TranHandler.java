package payroll.DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import payroll.Core.ErrorLog;
import payroll.Core.ReportDAO;
import payroll.Core.Utility;
import payroll.Model.ItStandardBean;
import payroll.Model.SAL_Details;
import payroll.Model.TranBean;

public class TranHandler
{
	public String addTransaction(TranBean TB)
	{
		String result= "false";
	
		Connection con= ConnectionManager.getConnection();
		try
		{
			Statement st1 = con.createStatement();
			Statement st11 = con.createStatement();
			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String check = "select trncd from paytran where trncd = "+TB.getTRNCD()+" and  empno = "+TB.getEMPNO();
			String sql="INSERT INTO PAYTRAN VALUES("
					+"'"+ReportDAO.EOM(TB.getTRNDT()) +"', "
						+TB.getEMPNO()+","
						+TB.getTRNCD()+","
						+TB.getSRNO()+","
						+TB.getINP_AMT()+","
						+TB.getCAL_AMT()+","
						+TB.getADJ_AMT()+","
						+TB.getARR_AMT()+","
						+TB.getNET_AMT()+","
						+"'"+TB.getCF_SW()+"',"
						+"'"+TB.getUSRCODE()+"',"
						+"'"+TB.getUPDDT()+"',"
						+"'"+TB.getSTATUS()+"'"
						+")"; 
			ResultSet chk = st.executeQuery(check);
			//System.out.println(sql);
			if(chk.first()){
				result = "present";
			}
			else{
				st1.execute(sql);
				System.out.println(sql);
				result = "true";
				
					
					
				
				
			}
			con.close();
		}
		catch(SQLException e)
		{
			try
			{
				con.close();
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return result;		
	}
	
	
	public void updatedesig(TranBean TB,int desig)
	{
		
		ResultSet rs=null;
		Connection con= ConnectionManager.getConnection();
		try
		{
			Statement st1 = con.createStatement();
			Statement st11 = con.createStatement();
		
					rs=st1.executeQuery("select max(srno) as srno from emptran where empno ="+TB.getEMPNO()+" ");
					rs.next();
					
					System.out.println("grade"+rs.getString("srno"));
					System.out.println("empno for grade"+TB.getEMPNO());
					
					st11.executeUpdate("update emptran set desig="+desig+",grade="+desig+" where empno="+TB.getEMPNO()+" and "+
					"srno="+rs.getString("srno")+" ");
					System.out.println("update emptran set desig="+desig+" where empno="+TB.getEMPNO()+" and "+
							"srno="+rs.getString("srno")+"");
					rs.close();
				
				
			
			con.close();
		}
		catch(SQLException e)
		{
			try
			{
				con.close();
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
			
	}
	
	
	
	
	public String addCTCDISPLAY(TranBean TB)
	{
		String result= "false";
		Connection con= ConnectionManager.getConnection();
		try
		{
			Statement st1 = con.createStatement();
			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			Statement st2 = con.createStatement();
			String check = "select * from CTCDISPLAY where empno = "+TB.getEMPNO()+" and TRNCD = "+TB.getTRNCD();
			String sql="INSERT INTO CTCDISPLAY VALUES("
						+TB.getEMPNO()+","
						+TB.getTRNCD()+","
						+TB.getSRNO()+","
						+TB.getINP_AMT()+","
						+TB.getADJ_AMT()+","
						+TB.getNET_AMT()+","
						+TB.getPf()+","
						+TB.getEsic()+","
						+TB.getPt()
						+")"; 
			String updte = "update CTCDISPLAY set VALUETYPE = "+TB.getSRNO()+" ," +
					" VALUE = "+TB.getINP_AMT()+ "," +
					" DEPENDON = "+TB.getADJ_AMT()+"," +
					" INP_AMT = "+TB.getNET_AMT()+"," +
					" PF = "+TB.getPf()+"," +
					" PT = "+TB.getPt()+"," +
					" ESIC = "+TB.getEsic()+"where empno = "+TB.getEMPNO()+" and TRNCD = "+TB.getTRNCD();
      
			ResultSet chk = st.executeQuery(check);
			if(chk.next()){
				st2.executeUpdate(updte);
				System.out.println(updte);
				result = "present";
			}
			else{
				st1.execute(sql);
				System.out.println(sql);
				result = "true";
			}
			
			con.close();
		}
		catch(SQLException e)
		{
			try
			{
				con.close();
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return result;		
	}
	
	public  ArrayList<TranBean> getCTCDISPLAY(int empno)
	{
		ArrayList<TranBean> trlist = new ArrayList<TranBean>();
		Connection con= ConnectionManager.getConnection();
		TranBean tb;
		try
		{
			Statement st = con.createStatement();
			ResultSet rs= null;
			rs = st.executeQuery("SELECT * FROM CTCDISPLAY  WHERE empno = "+empno);
			System.out.println("akk  "+"SELECT * FROM CTCDISPLAY  WHERE empno = "+empno);
			while(rs.next())
			{
				tb = new TranBean();
				tb.setEMPNO(rs.getInt("EMPNO"));
				tb.setTRNCD(rs.getInt("TRNCD"));
				tb.setSRNO(rs.getInt("VALUETYPE"));
				tb.setINP_AMT(rs.getFloat("VALUE"));
				tb.setADJ_AMT(rs.getFloat("DEPENDON"));
				tb.setNET_AMT(rs.getFloat("INP_AMT"));
				tb.setPf(rs.getFloat("PF"));
				tb.setPt(rs.getFloat("PT"));
				tb.setEsic(rs.getFloat("ESIC"));
				trlist.add(tb);
			}
			con.close();
		}
		catch(Exception e)
		{
			try
			{
				con.close();
				e.printStackTrace();
			} 
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		return trlist;
	}
	

	
	public  ArrayList<TranBean> getPaytranValue(int empno)
	{
		ArrayList<TranBean> trlist = new ArrayList<TranBean>();
		Connection con= ConnectionManager.getConnection();
		TranBean tb;
		try
		{
			Statement st = con.createStatement();
			ResultSet rs= null;
			rs = st.executeQuery("SELECT * FROM PAYTRAN WHERE empno = "+empno);
			System.out.println("akk  "+"SELECT * FROM PAYTRAN  WHERE empno = "+empno);
			while(rs.next())
			{
				tb = new TranBean();
				tb.setEMPNO(rs.getInt("EMPNO"));
				tb.setTRNCD(rs.getInt("TRNCD"));
				tb.setCAL_AMT(rs.getFloat("CAL_AMT"));
				trlist.add(tb);
			}
			con.close();
		}
		catch(Exception e)
		{
			try
			{
				con.close();
				e.printStackTrace();
			} 
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		return trlist;
	}

	

	

	public boolean checkAndInsertTran(TranBean TB)
	{
		boolean result=false;
		Connection con= ConnectionManager.getConnection();
		try
		{
			Statement st1 = con.createStatement();
			Statement st2 = con.createStatement();
			ResultSet rs = st2.executeQuery("SELECT * FROM PAYTRAN WHERE EMPNO="+TB.getEMPNO()+" AND TRNCD="+TB.getTRNCD());
			if(rs.next())
			{	
				st1.executeUpdate("UPDATE PAYTRAN SET SRNO="+TB.getSRNO()+", INP_AMT="+TB.getINP_AMT()+"," +
								" CAL_AMT="+TB.getCAL_AMT()+",ADJ_AMT="+TB.getADJ_AMT()+",ARR_AMT="+TB.getARR_AMT()+"," +
								" NET_AMT="+TB.getNET_AMT()+",CF_SW='"+TB.getCF_SW()+"',TRNDT=GETDATE(),UPDDT=GETDATE(),USRCODE='"+TB.getUSRCODE()+"'" +
								" WHERE EMPNO="+TB.getEMPNO()+" AND TRNCD="+TB.getTRNCD());
			}
			else
			{
				String sql="INSERT INTO PAYTRAN VALUES("
						+"GETDATE(),"
						+TB.getEMPNO()+","
						+TB.getTRNCD()+","
						+TB.getSRNO()+","
						+TB.getINP_AMT()+","
						+TB.getCAL_AMT()+","
						+TB.getADJ_AMT()+","
						+TB.getARR_AMT()+","
						+TB.getNET_AMT()+","
						+"'"+TB.getCF_SW()+"',"
						+"'"+TB.getUSRCODE()+"',"
						+"GETDATE()"
						+")"; 
			st1.execute(sql);
			}	
			result = true;
			con.close();
		}
		catch(SQLException e)
		{
			try
			{
				con.close();
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return result;		
	}
	// i want some changes in method..... 
	
	public int addNewTransaction(TranBean TB)
	{
		int result=0;
		Connection con= ConnectionManager.getConnection();
		int srnomax=0;
		try
		{
			Statement st2 = con.createStatement();
			ResultSet rs1=st2.executeQuery("select * from PAYTRAN where EMPNO='"+TB.getEMPNO()+"' and TRNCD='"+TB.getTRNCD()+"'");
			if(rs1.next())
			{
				result=2;	
			}
			else
			{
				Statement st1 = con.createStatement();
				String srno ="select max(srno) from paytran";
				ResultSet rs=st1.executeQuery(srno);
				if(rs.next())
				{
					srnomax=rs.getInt(1);
				}
				srnomax=srnomax+1;
				String sql="INSERT INTO PAYTRAN VALUES("
							+"'"+TB.getTRNDT()+"',"
							+TB.getEMPNO()+","
							+TB.getTRNCD()+","
							+srnomax+","
							+TB.getINP_AMT()+","
							+TB.getCAL_AMT()+","
							+TB.getADJ_AMT()+","
							+TB.getARR_AMT()+","
							+TB.getNET_AMT()+","
							+"'"+TB.getCF_SW()+"',"
							+"'"+TB.getUSRCODE()+"',"
							+"'"+TB.getUPDDT()+"',"
							+"'"+TB.getSTATUS()+"'"
							+")"; 
				st1.execute(sql);
				result=1;
				con.close();
			}
		}
		catch(SQLException e)
		{
			try
			{
				con.close();
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return result;		
	}
	
	
	//--------- Method for adding transaction to YTDTRAN table----
	public boolean addToYTDTran(int empno)
	{
		boolean result=false;
		Connection con= ConnectionManager.getConnection();
		try
		{
			Statement st= con.createStatement();
			String sql="INSERT INTO YTDTRAN VALUE(SELECT * FROM PAYTRAN WHERE EMPNO ="+empno+")"; 
			st.execute(sql);
			st.executeUpdate("DELETE FROM PAYTRAN WHERE EMPNO="+empno+" AND NVL(CF_SW,0) <>'*'");
			result=true;
			con.close();
		}
		catch(SQLException e)
		{
			try
			{
				con.close();
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		return result;		
	}
	
	//------ Method for adding all transactions from TRAN to YTDTRAN--------
	
	public boolean addAllTranToYTDTRAN()
	{
		boolean result=false;
		Connection con= ConnectionManager.getConnection();
		try
		{
			Statement st= con.createStatement();
			String sql="INSERT INTO YTDTRAN VALUE(SELECT * FROM PAYTRAN )"; 
			st.execute(sql);
			result=true;
			con.close();
		}
		catch(SQLException e)
		{
			try
			{
				con.close();
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		return result;		
	}
	
	public boolean addInvestTran(TranBean tb)
	{
		boolean result=false;
		Connection con= ConnectionManager.getConnection();
		ResultSet rs=null;
		int srno = 0;
		try
		{
			Statement st= con.createStatement();
			rs=st.executeQuery("select max(SRNO) from INVTRAN where EMPNO="+tb.getEMPNO()+"");
			while(rs.next())
			{
				srno=rs.getString(1)!=null?rs.getInt(1):0;
			}
			srno = srno+1;
			String sql="INSERT INTO INVTRAN (TRNDT,EMPNO,TRNCD,INP_AMT,SRNO) VALUES ('"+tb.getTRNDT()+"',"+tb.getEMPNO()+","+tb.getTRNCD()+",'"+tb.getINP_AMT()+"',"+srno+")"; 
			st.execute(sql);
			result=true;
			con.close();
		}
		catch(SQLException e)
		{
			try
			{
				con.close();
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return result;		
	}
	
	public boolean DeleteInvestTran(int empno,int srno)
	{
		boolean result=false;
		Connection con= ConnectionManager.getConnection();
		try
		{
			Statement st = con.createStatement();
			String sql = "delete from INVTRAN where EMPNO = "+empno+" and SRNO = "+srno+"";
			st.execute(sql);
			result=true;
			con.close();
		}
		catch(SQLException e)
		{
			try
			{
				con.close();
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return result;		
	}
	
	public ArrayList<TranBean> getTranInfo(String Empno,String tran)
	{
		ArrayList<TranBean> trlist = new ArrayList<TranBean>();
		Connection con= ConnectionManager.getConnection();
		int empno = Integer.parseInt(Empno);
		TranBean tb;
		try
		{
			Statement st = con.createStatement();
			ResultSet rs= null;
			if(tran.equalsIgnoreCase("tran"))
			{
				rs= st.executeQuery("Select * from PAYTRAN Where EMPNO="+empno+" ORDER BY TRNCD,UPDDT DESC");
			}
			else
			{
				rs= st.executeQuery("Select * from INVTRAN Where EMPNO="+empno+" ORDER BY SRNO ASC");
			}
			while(rs.next())
			{
				tb = new TranBean();
				tb.setADJ_AMT(rs.getString("ADJ_AMT")!=null?rs.getFloat("ADJ_AMT"):0);
				tb.setARR_AMT(rs.getString("ARR_AMT")!=null?rs.getFloat("ARR_AMT"):0);
				tb.setCAL_AMT(rs.getString("CAL_AMT")!=null?rs.getFloat("CAL_AMT"):0);
				tb.setCF_SW(rs.getString("CF_SW")==null?"--":rs.getString("CF_SW"));
				tb.setEMPNO(rs.getString("EMPNO")!=null?rs.getInt("EMPNO"):0);
				tb.setINP_AMT(rs.getString("INP_AMT")!=null?rs.getFloat("INP_AMT"):0);
				tb.setNET_AMT(rs.getString("NET_AMT")!=null?rs.getFloat("NET_AMT"):0);
				tb.setSRNO(rs.getString("SRNO")!=null?rs.getInt("SRNO"):0);
				tb.setTRNCD(rs.getString("TRNCD")!=null?rs.getInt("TRNCD"):0);
				tb.setTRNDT(rs.getString("TRNDT")!=null?EmpOffHandler.dateFormat(rs.getDate("TRNDT")):"");
				tb.setUPDDT(rs.getString("UPDDT")!=null?EmpOffHandler.dateFormat(rs.getDate("UPDDT")):"");
				tb.setUSRCODE(rs.getString("USRCODE")!=null?rs.getString("USRCODE"):"");
				trlist.add(tb);
			}
			con.close();
		}
		catch(Exception e)
		{
			try
			{
				con.close();
			} 
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		return trlist;
	}

	public TranBean getSingleTranInfo(int Empno,int trncd,int srno,String trndate)//empno,trncd,srno,trndate
	{
		Connection con= ConnectionManager.getConnection();//TRNDT EMPNO SRNO TRNCD
		TranBean tb = new TranBean();
		try
		{
			Statement st = con.createStatement();
			String sql="Select * from PAYTRAN Where EMPNO="+Empno+" and SRNO="+srno+" and TRNCD="+trncd+" and TRNDT <='"+trndate+"'";
			ResultSet rs= st.executeQuery(sql);
			while(rs.next())
			{
				tb.setADJ_AMT(rs.getString("ADJ_AMT")!=null?rs.getFloat("ADJ_AMT"):0);
				tb.setARR_AMT(rs.getString("ARR_AMT")!=null?rs.getFloat("ARR_AMT"):0);
				tb.setCAL_AMT(rs.getString("CAL_AMT")!=null?rs.getFloat("CAL_AMT"):0);
				tb.setCF_SW(rs.getString("CF_SW")==null?"-":rs.getString("CF_SW"));
				tb.setEMPNO(rs.getString("EMPNO")!=null?rs.getInt("EMPNO"):0);
				tb.setINP_AMT(rs.getString("INP_AMT")!=null?rs.getFloat("INP_AMT"):0);
				tb.setNET_AMT(rs.getString("NET_AMT")!=null?rs.getFloat("NET_AMT"):0);
				tb.setSRNO(rs.getString("SRNO")!=null?rs.getInt("SRNO"):0);
				tb.setTRNCD(rs.getString("TRNCD")!=null?rs.getInt("TRNCD"):0);
				tb.setTRNDT(EmpOffHandler.dateFormat(rs.getDate("TRNDT")));
				tb.setUPDDT(EmpOffHandler.dateFormat(rs.getDate("UPDDT")));
				tb.setUSRCODE(rs.getString("USRCODE")!=null?rs.getString("USRCODE"):"");
			}
			con.close();
		}
		catch(Exception e)
		{
			try
			{
				con.close();
			}
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
		}
		return tb;
	}
	
	public TranBean getPaySlipTran(int Empno,int trncd,int srno,String trndate,String frmTable)//empno,trncd,srno,trndate
	{
		
		Connection con= ConnectionManager.getConnection();//TRNDT EMPNO SRNO TRNCD
		TranBean tb = new TranBean();
		ResultSet rs;
		try
		{
			Statement st = con.createStatement();
			String paytrsql="Select * from "+frmTable+" Where EMPNO="+Empno+" and SRNO="+srno+" and TRNCD="+trncd+" and TRNDT ='"+trndate+"'";
			rs = st.executeQuery(paytrsql);
			while(rs.next())
			{
				tb.setADJ_AMT(rs.getString("ADJ_AMT")!=null?rs.getFloat("ADJ_AMT"):0);
				tb.setARR_AMT(rs.getString("ARR_AMT")!=null?rs.getFloat("ARR_AMT"):0);
				tb.setCAL_AMT(rs.getString("CAL_AMT")!=null?rs.getFloat("CAL_AMT"):0);
				tb.setCF_SW(rs.getString("CF_SW")==null?"-":rs.getString("CF_SW"));
				tb.setEMPNO(rs.getString("EMPNO")!=null?rs.getInt("EMPNO"):0);
				tb.setINP_AMT(rs.getString("INP_AMT")!=null?rs.getFloat("INP_AMT"):0);
				tb.setNET_AMT(rs.getString("NET_AMT")!=null?rs.getFloat("NET_AMT"):0);
				tb.setSRNO(rs.getString("SRNO")!=null?rs.getInt("SRNO"):0);
				tb.setTRNCD(rs.getString("TRNCD")!=null?rs.getInt("TRNCD"):0);
				tb.setTRNDT(EmpOffHandler.dateFormat(rs.getDate("TRNDT")));
				tb.setUPDDT(EmpOffHandler.dateFormat(rs.getDate("UPDDT")));
				tb.setUSRCODE(rs.getString("USRCODE")!=null?rs.getString("USRCODE"):"");
			}
			con.close();
		}
		catch(Exception e)
		{
			try
			{
				con.close();
			}
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return tb;
	}
	

	public boolean UpdateTransaction(TranBean tb)
	{
		boolean result=false;
		Connection con= ConnectionManager.getConnection();
		Date dt1 = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
		String dt = format.format(dt1);
		try
		{
			Statement st = con.createStatement();
			Statement st1 = con.createStatement();
			String sql1="INSERT TRANLOG SELECT * FROM PAYTRAN P WHERE P.TRNDT='"+tb.getTRNDT()+"' AND P.TRNCD="+tb.getTRNCD()+" AND P.SRNO="+tb.getSRNO()+" AND P.EMPNO="+tb.getEMPNO();
			st1.execute(sql1);
			String sql="UPDATE PAYTRAN SET "
						+"ADJ_AMT="+tb.getADJ_AMT()+", "
						+"INP_AMT="+tb.getINP_AMT()+", "
						+"NET_AMT="+tb.getNET_AMT()+",CAL_AMT="+tb.getCAL_AMT()+", "
						+"CF_SW='"+tb.getCF_SW()+"', "
						+"UPDDT='"+dt+"',STATUS='N' "
					    +"WHERE TRNDT='"+tb.getTRNDT()+"' AND TRNCD="+tb.getTRNCD()+" AND SRNO="+tb.getSRNO()+" AND EMPNO="+tb.getEMPNO();
			st.executeUpdate(sql);
			result=true;
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	public boolean old_finalizeTran(String emplist,String dt,String user)
	{
		boolean flag = false;
		Connection con = null;
		try
		{
			con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			String sql = "INSERT INTO YTDTRAN (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,UPDDT) ";
			sql = sql + "SELECT TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,TRNDT FROM PAYTRAN WHERE EMPNO IN ("+emplist+") ORDER BY EMPNO, TRNCD";
			st.executeUpdate(sql);
			st.executeUpdate("DELETE FROM PAYTRAN WHERE EMPNO IN ("+emplist+") AND CF_SW <> '*'");
			st.executeUpdate("UPDATE PAYTRAN SET TRNDT=DATEADD(MM,1,TRNDT),UPDDT=DATEADD(MM,1,TRNDT),CAL_AMT=0,NET_AMT=0,ADJ_AMT=0,ARR_AMT=0 WHERE EMPNO IN ("+emplist+")");
			Sal_DetailsHandler SDH = new Sal_DetailsHandler();
			SAL_Details salData = new SAL_Details();
			salData.setEMPNO(0);
			salData.setSAL_MONTH(dt);
			salData.setSAL_STATUS("FINALIZED");
			salData.setFINALIZED_BY(user);
			SDH.updateSalDetails(emplist, salData, con);
			flag = true;
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}
	public boolean finalizeTran(String emplist,String dt,String user)
	{
		boolean flag = false;
		Connection con = null;
		try
		{
			con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			String flag_st = "update paytran set STATUS = 'F' where empno in ("+emplist+") and empno not in (select distinct empno from paytran where trncd=999 and net_amt<0)";
			st.executeUpdate(flag_st);
			String sql = "INSERT INTO PAYTRAN_STAGE (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,UPDDT,STATUS) ";
			sql = sql + "SELECT TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,TRNDT,STATUS FROM PAYTRAN WHERE EMPNO IN ("+emplist+") and empno not in (select distinct empno from paytran where trncd=999 and net_amt<0) ORDER BY EMPNO, TRNCD";
			st.executeUpdate(sql);
			st.executeUpdate("DELETE FROM PAYTRAN WHERE EMPNO IN ("+emplist+") AND CF_SW <> '*' and TRNCD not in (select distinct TRNCD from CDMAST where TRNCD between 201 and 299 and PSLIPYN like 'Y') and empno not in (select distinct empno from paytran where trncd=999 and net_amt<0) ");
			String sql_temp = "INSERT INTO PAYTRAN_TEMP (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,UPDDT,STATUS) ";
			sql_temp = sql_temp + "SELECT TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,TRNDT,STATUS FROM PAYTRAN WHERE EMPNO IN ("+emplist+") and empno not in (select distinct empno from paytran where trncd=999 and net_amt<0)  ORDER BY EMPNO, TRNCD";
			st.executeUpdate(sql_temp);
			st.executeUpdate("DELETE FROM PAYTRAN WHERE EMPNO IN ("+emplist+") and empno not in (select distinct empno from paytran where trncd=999 and net_amt<0) ; update PAYTRAN_TEMP set INP_AMT=0.0,CAL_AMT=0.0,ADJ_AMT=0.0,ARR_AMT=0.0,NET_AMT=0.0 where CF_SW<> '*' and empno in ("+emplist+") and empno not in (select distinct empno from paytran where trncd=999 and net_amt<0) ");
			System.out.println("-----------------update on temp completed------------------------");
			
			Sal_DetailsHandler SDH = new Sal_DetailsHandler();
			System.out.println("this is 01");
			SAL_Details salData = new SAL_Details();
			salData.setEMPNO(0);
			salData.setSAL_MONTH(dt);
			salData.setSAL_STATUS("FINALIZED");
			salData.setFINALIZED_BY(user);
			System.out.println("this is 02");

			SDH.updateSalDetails(emplist, salData, con);
			System.out.println("this is 03");

			//insert_deduction(emplist,con,dt);// need to check this process
//flag = true;
			
			System.out.println("-----------------finalise completed------------------------");
			
			String date1 = "25-"+dt;
			SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
			Date dd = sdf.parse(date1);
			System.out.println("this is "+date1);

			
			Calendar cal = Calendar.getInstance();
			cal.setTime(dd);
			cal.add(Calendar.MONTH, 1);
			String [] datesplit=sdf.format(cal.getTime()).split("-");
			String next_date=datesplit[1]+"-"+datesplit[2];
			System.out.println("this is 05"+next_date);

			String paytran_check = "select * from paytran where STATUS not in ('F','N') and EMPNO IN ("+emplist+") and empno not in (select distinct empno from paytran where trncd=999 and net_amt<0) ";
			ResultSet rs = st.executeQuery(paytran_check);
			if(rs.next()==false){
				System.out.println("-----------------IMPORT CHECK------------------------");
				
				String sql1 = "select distinct empno from PAYTRAN_TEMP where EMPNO not in(select EMPNO from EMPMAST where " +
						"DATEPART(mm,DOL)=DATEPART(mm,CONVERT(datetime,  REPLACE('"+dt+"', '-', ' '), 106))and " +
						"DATEPART(YYYY,DOL)=DATEPART(YYYY,CONVERT(datetime,  REPLACE('"+dt+"', '-', ' '), 106))) and EMPNO IN ("+emplist+")  " ;
				//System.out.println(sql1);
				Statement st1 = con.createStatement();
				Statement st2 = con.createStatement();
				Statement st3 = con.createStatement();
				
				ResultSet pay_inst = st1.executeQuery(sql1);
				while(pay_inst.next()){
					System.out.println("-----------------IMPORT into paytran------------------------");
					Statement st4 = con.createStatement();
					ResultSet  empleft=st4.executeQuery("select * from empmast where EMPNO="+pay_inst.getInt("EMPNO")+" and (DOL<>'' or DOL<>NULL or STATUS='N') ");
					if(empleft.next())
					{
						System.out.println(" This emp is Going to left so not insert for paytran...."+empleft.getInt("EMPNO"));
					}
					else
					{
					String sql_temp1 = "INSERT INTO PAYTRAN (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,UPDDT,STATUS) ";
					//sql_temp1 = sql_temp1 + "SELECT DATEADD(MM,1,TRNDT),EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,DATEADD(MM,1,TRNDT),'P' FROM PAYTRAN_TEMP WHERE EMPNO = "+pay_inst.getInt("EMPNO")+" ORDER BY EMPNO, TRNCD";
					sql_temp1 = sql_temp1 + "SELECT  CONVERT(VARCHAR(25),DATEADD(dd,-(DAY(DATEADD(mm,1,DATEADD(MM,1,TRNDT)))),DATEADD(mm,1,DATEADD(MM,1,TRNDT))),21),EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,0,ARR_AMT,NET_AMT,CF_SW,CONVERT(VARCHAR(25),DATEADD(dd,-(DAY(DATEADD(mm,1,DATEADD(MM,1,TRNDT)))),DATEADD(mm,1,DATEADD(MM,1,TRNDT))),21),'P' FROM PAYTRAN_TEMP WHERE EMPNO = "+pay_inst.getInt("EMPNO")+" ORDER BY EMPNO, TRNCD";
					System.out.println("this is 06");

					st2.executeUpdate(sql_temp1);
					 SDH = new Sal_DetailsHandler();
					SDH.addSalDetails(pay_inst.getInt("EMPNO"),next_date,sdf.format(cal.getTime()), "AutoInst", con);
					}
				}				
				System.out.println("this is 07");

				/*String dateupdate = "update SALARY_MONTH SET SALDATE = '"+sdf.format(cal.getTime())+"'";
				System.out.println("-----------------IMPORT update sal month------------------------");
				
				st3.execute(dateupdate);
				*/st3.execute("delete from PAYTRAN_TEMP where EMPNO IN ("+emplist+")");
			
				System.out.println("-----------------IMPORT COMPLETED------------------------");
				flag = true;

			}
			
			con.close();
			finalizeTheSlabAfterSalary(date1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}
	
	
	
	
	public boolean finalizeTheSlabAfterSalary(String date) throws SQLException {
		ErrorLog errorLog=new ErrorLog();
		boolean flagForQuery=false;
		try{
		Connection  connection = ConnectionManager.getConnection();
		Statement statement= connection.createStatement();
		date=ReportDAO.BOM(date);
		String query="update DAMAST set FINALIZE_FLAG=1 where DA_APPLICABLE_DATE='"+date+"'";  
		 statement.executeUpdate(query);
		
	statement.close();
	connection.close();
	}catch(Exception e)
	{
		e.printStackTrace();
		errorLog.errorLog("VdaDAO: ERROR FINALIZING THE SLAB IN DAMAST METHOD: finalizeTheSlab(). FOR PAGE: VDA_Posting.jsp", e.toString());
	} 
		flagForQuery=true;
		return flagForQuery;
	}


	
	
	public void insert_deduction(String emplist,Connection con,String date){
		try
		{
			Statement st = con.createStatement();
			
			String sql = "select distinct EMPNO,TRNDT from PAYTRAN_STAGE where EMPNO in ("+emplist+")";
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				Statement st_adv = con.createStatement();
				Statement st_mob = con.createStatement();
				Statement st_oth = con.createStatement();
				Statement st_tds = con.createStatement();
				Statement st_loan = con.createStatement();
				String sql_adv = "select * from PAYTRAN_STAGE where EMPNO = "+rs.getInt("EMPNO")+" and trncd = 225" ;
				ResultSet rs_adv = st_adv.executeQuery(sql_adv);
				if(rs_adv.next()== false){
					String insert_adv = "INSERT INTO PAYTRAN_STAGE values ('"+rs.getString("TRNDT")+"',"+rs.getInt("EMPNO")+",225,0,0,0,0,0,0,'','','"+rs.getString("TRNDT")+"','F')";
					st_adv.execute(insert_adv);
				}
				String sql_mob = "select * from PAYTRAN_STAGE where EMPNO = "+rs.getInt("EMPNO")+" and trncd = 223" ;
				ResultSet rs_mob = st_mob.executeQuery(sql_mob);
				if(rs_mob.next()== false){
					String insert_adv = "INSERT INTO PAYTRAN_STAGE values ('"+rs.getString("TRNDT")+"',"+rs.getInt("EMPNO")+",223,0,0,0,0,0,0,'','','"+rs.getString("TRNDT")+"','F')";
					st_adv.execute(insert_adv);
				}
				String sql_oth = "select * from PAYTRAN_STAGE where EMPNO = "+rs.getInt("EMPNO")+" and trncd = 212" ;
				ResultSet rs_oth = st_oth.executeQuery(sql_oth);
				if(rs_oth.next()== false){
					String insert_oth = "INSERT INTO PAYTRAN_STAGE values ('"+rs.getString("TRNDT")+"',"+rs.getInt("EMPNO")+",212,0,0,0,0,0,0,'','','"+rs.getString("TRNDT")+"','F')";
					st_adv.execute(insert_oth);
				}
				String sql_tds = "select * from PAYTRAN_STAGE where EMPNO = "+rs.getInt("EMPNO")+" and trncd = 228" ;
				ResultSet rs_tds = st_tds.executeQuery(sql_tds);
				if(rs_tds.next()== false){
					String insert_tds = "INSERT INTO PAYTRAN_STAGE values ('"+rs.getString("TRNDT")+"',"+rs.getInt("EMPNO")+",228,0,0,0,0,0,0,'','','"+rs.getString("TRNDT")+"','F')";
					st_adv.execute(insert_tds);
				}
				String sql_loan = "select * from PAYTRAN_STAGE where EMPNO = "+rs.getInt("EMPNO")+" and trncd = 226" ;
				ResultSet rs_loan = st_loan.executeQuery(sql_loan);
				if(rs_loan.next()== false){	
					String insert_loan = "INSERT INTO PAYTRAN_STAGE values ('"+rs.getString("TRNDT")+"',"+rs.getInt("EMPNO")+",226,0,0,0,0,0,0,'','','"+rs.getString("TRNDT")+"','F')";
					st_adv.execute(insert_loan);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public static int nextMonthImport(String date,String next_date){
		Connection con = null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			Statement st = con.createStatement();
			Statement st1 = con.createStatement();
			Statement st2 = con.createStatement();
			Statement st3 = con.createStatement();
			String date1 = "25-"+date;
			Date dd = sdf.parse(date1);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dd);
			cal.add(Calendar.MONTH, 1);
			String paytran_check = "select * from paytran where STATUS not in ('F','N') ";
			ResultSet rs = st.executeQuery(paytran_check);
			if(rs.next()==false){
				String sql = "select distinct empno from PAYTRAN_TEMP where EMPNO not in(select EMPNO from EMPMAST where " +
						"DATEPART(mm,DOL)=DATEPART(mm,CONVERT(datetime,  REPLACE('"+date+"', '-', ' '), 106))and " +
						"DATEPART(YYYY,DOL)=DATEPART(YYYY,CONVERT(datetime,  REPLACE('"+date+"', '-', ' '), 106)))" ;
				System.out.println(sql);
				ResultSet pay_inst = st1.executeQuery(sql);
				while(pay_inst.next()){
					String sql_temp = "INSERT INTO PAYTRAN (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,UPDDT,STATUS) ";
					sql_temp = sql_temp + "SELECT DATEADD(MM,1,TRNDT),EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,DATEADD(MM,1,TRNDT),'P' FROM PAYTRAN_TEMP WHERE EMPNO = "+pay_inst.getInt("EMPNO")+" ORDER BY EMPNO, TRNCD";
					st2.executeUpdate(sql_temp);
					Sal_DetailsHandler SDH = new Sal_DetailsHandler();
					SDH.addSalDetails(pay_inst.getInt("EMPNO"),next_date,sdf.format(cal.getTime()), "AutoInst", con);
				}				
				String dateupdate = "update SALARY_MONTH SET SALDATE = '"+sdf.format(cal.getTime())+"'";
				st3.execute(dateupdate);
				st3.execute("delete from PAYTRAN_TEMP");
				return  -0;
			}
			else{
				return  -2;
			}
			
		}
		catch(Exception e){
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally{
			try {
				con.commit();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 99;
	}
	
	public String getFinalizeStatus()
	{
		String result="";
		Connection con = null;
		try
		{
			con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM FINALIZE");			
			if(rs.next())
			{
				result = Utility.dateFormat(rs.getDate(1)) + ":" +rs.getString(2);
			}
			else
			{
				result = "Nothing:TRUE";
			}
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public void updateFinalizeStatus(String date)
	{
		Connection con = null;
		try
		{
			con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			st.executeUpdate("DELETE FROM FINALIZE");
			st.executeUpdate("INSERT INTO FINALIZE VALUES('"+date+"','FALSE')");
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public  ArrayList<TranBean> getTranDetail(int key)
	{
		ArrayList<TranBean> trlist = new ArrayList<TranBean>();
		Connection con= ConnectionManager.getConnection();
		TranBean tb;
		try
		{
			Statement st = con.createStatement();
			ResultSet rs= null;
			Statement st1 = con.createStatement();
			ResultSet rs1=null;
			rs1 = st1.executeQuery("SELECT EMPNO FROM EMPMAST WHERE STATUS='A' ORDER BY EMPNO");
			/*rs= st.executeQuery("Select * from PAYTRAN Where TRNCD="+key+"");*/
			while(rs1.next())
			{                        // Select p.*,e.empcode from PAYTRAN p ,empmast e where e.EMPNO=p.EMPNO
			   	rs= st.executeQuery("Select p.*,e.empcode from PAYTRAN p,empmast e Where p.EMPNO="+rs1.getInt("EMPNO")+" AND e.EMPNO=p.EMPNO and  p.TRNCD="+key+ "order by e.empcode ");
				tb = new TranBean();
				if(rs.next())
				{
					tb.setADJ_AMT(rs.getFloat("ADJ_AMT"));
					tb.setARR_AMT(rs.getFloat("ARR_AMT"));
					tb.setCAL_AMT(rs.getFloat("CAL_AMT"));
					tb.setCF_SW(rs.getString("CF_SW")==null?"-":rs.getString("CF_SW"));
					tb.setEMPNO(rs.getInt("EMPNO"));
					tb.setEmpcode(rs.getString("empcode"));
					tb.setINP_AMT(rs.getFloat("INP_AMT"));
					tb.setNET_AMT(rs.getFloat("NET_AMT"));
					tb.setSRNO(rs.getInt("SRNO"));
					tb.setTRNCD(rs.getInt("TRNCD"));
					tb.setTRNDT(EmpOffHandler.dateFormat(rs.getDate("TRNDT")));
					tb.setUPDDT(EmpOffHandler.dateFormat(rs.getDate("UPDDT")));
					tb.setUSRCODE(rs.getString("USRCODE"));
				}
				else
				{
					tb.setEMPNO(rs1.getInt("EMPNO"));
					tb.setTRNCD(key);
				}
				trlist.add(tb);
			}
			con.close();
		}
		catch(Exception e)
		{
			try
			{
				con.close();
				e.printStackTrace();
			} 
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		return trlist;
	}
	
	public  ArrayList<TranBean> getTranByEmpno(int empno,int trncd)
	{
		ArrayList<TranBean> alist=new ArrayList<>();
		Connection con= ConnectionManager.getConnection();
		TranBean tb;
		try
		{
			Statement st = con.createStatement();
			ResultSet rs= null;
			String query="Select * from PAYTRAN Where EMPNO="+empno+" AND TRNCD="+trncd+" ORDER BY TRNCD";
			rs= st.executeQuery(query);
			while(rs.next())
			{
				tb = new TranBean();
				tb.setADJ_AMT(rs.getFloat("ADJ_AMT"));
				tb.setARR_AMT(rs.getFloat("ARR_AMT"));
				tb.setCAL_AMT(rs.getFloat("CAL_AMT"));
				tb.setCF_SW(rs.getString("CF_SW")==null?"-":rs.getString("CF_SW"));
				tb.setEMPNO(rs.getInt("EMPNO"));
				tb.setINP_AMT(rs.getFloat("INP_AMT"));
				tb.setNET_AMT(rs.getFloat("NET_AMT"));
				tb.setSRNO(rs.getInt("SRNO"));
				tb.setTRNCD(rs.getInt("TRNCD"));
				tb.setTRNDT(EmpOffHandler.dateFormat(rs.getDate("TRNDT")));
				tb.setUPDDT(EmpOffHandler.dateFormat(rs.getDate("UPDDT")));
				tb.setUSRCODE(rs.getString("USRCODE"));
				alist.add(tb);
			}
			con.close();
		}
		catch(Exception e)
		{
			try
			{
				con.close();
			} 
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		return alist;
	}
	
	//To get the empbean according to emp & trncd
	public  TranBean getTranByEmpno1(int empno,int trncd)
	{
		Connection con= ConnectionManager.getConnection();
		TranBean tb = null;
		try
		{
			Statement st = con.createStatement();
			ResultSet rs= null;
			
			 // Select p.*,e.empcode from PAYTRAN p ,empmast e where e.EMPNO=p.EMPNO
			String query="Select p.*,e.empcode from PAYTRAN p ,empmast e where e.EMPNO=p.EMPNO and p.EMPNO="+empno+" AND p.TRNCD="+trncd+ "order by e.empcode";
			System.out.println("fgbehdbfjhfbhd"+query);
			rs= st.executeQuery(query);
			if(rs.next())
			{
				tb = new TranBean();
				tb.setADJ_AMT(rs.getFloat("ADJ_AMT"));
				tb.setARR_AMT(rs.getFloat("ARR_AMT"));
				tb.setCAL_AMT(rs.getFloat("CAL_AMT"));
				tb.setCF_SW(rs.getString("CF_SW")==null?"-":rs.getString("CF_SW"));
				//tb.setEMPNO(rs.getInt("EMPNO"));
				tb.setEmpcode(rs.getString("empcode"));

				tb.setEMPNO(empno);
				tb.setINP_AMT(rs.getFloat("INP_AMT"));
				tb.setNET_AMT(rs.getFloat("NET_AMT"));
				tb.setSRNO(rs.getInt("SRNO"));
				//tb.setTRNCD(rs.getInt("TRNCD"));
				tb.setTRNCD(trncd);
				tb.setTRNDT(EmpOffHandler.dateFormat(rs.getDate("TRNDT")));
				tb.setUPDDT(EmpOffHandler.dateFormat(rs.getDate("UPDDT")));
				tb.setUSRCODE(rs.getString("USRCODE"));
			}
			else
			{
				tb = new TranBean();
				tb.setEMPNO(empno);
				tb.setTRNCD(trncd);
			}
			con.close();
		}
		catch(Exception e)
		{
			try
			{
				con.close();
			} 
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		return tb;
	}

	
	public boolean updatetranAmount(TranBean tb1)
	{
		Connection con= ConnectionManager.getConnection();
		boolean result=false;
		try
		{
			Statement st = con.createStatement();
			String update="UPDATE PAYTRAN set INP_AMT="+tb1.getINP_AMT()+", UPDDT = '"+tb1.getTRNDT()+"',STATUS='A' where EMPNO="+tb1.getEMPNO()+" and TRNCD="+tb1.getTRNCD()+" ";
			st.executeUpdate(update);
			result=true;
			con.close();
		}	
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean getAvailability(int empno, int trncd)
	{
		Connection con= ConnectionManager.getConnection();
		boolean result=false;
		try
		{
			Statement st = con.createStatement();
			String sql = "SELECT * FROM PAYTRAN WHERE EMPNO="+empno+" AND TRNCD ="+trncd;
			ResultSet rs = st.executeQuery(sql);
			if(rs.next())
			{
				result = true;
			}
			con.close();
		}	
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	public ArrayList<ItStandardBean> getITStandardList()
	{
		ArrayList<ItStandardBean>  Itstdlist = new ArrayList<ItStandardBean>();
		ItStandardBean itsbean;
		Connection con=null;
		try
		{
			con=ConnectionManager.getConnection();
			Statement st =con.createStatement();
			ResultSet rs = st.executeQuery("select * from ITSTANDARD");
			while(rs.next())
			{
				itsbean = new ItStandardBean();
				itsbean.setTRCD(rs.getInt("TRNCD"));
				itsbean.setAMOUNT(rs.getInt("AMOUNT"));
				Itstdlist.add(itsbean);
			}
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return Itstdlist;
	}
	
	public int[] getITSList()
	{
		int result[] = null;
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT DISTINCT(TRNCD) FROM ITSTANDARD ORDER BY TRNCD");
			if(rs.next())
			{
				rs.last();
				result = new int[rs.getRow()];
				rs.beforeFirst();
				int i=0;
				while(rs.next())
				{
					result[i] = rs.getInt(1);
					i++;
				}
			}
			else
			{
				result = new int[0];
			}
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	public static ArrayList<TranBean> getNagtiveSalaryList(String dt)
	{
		ArrayList<TranBean> nslist = new ArrayList<TranBean>();
		Connection con =ConnectionManager.getConnection();
		Statement st;
		ResultSet rs;
		TranBean trbn;
		try
		{
			st=con.createStatement();
			rs=st.executeQuery("select P.EMPNO,E.FNAME,E.MNAME,E.LNAME from EMPMAST E,PAYTRAN P where P.EMPNO=E.EMPNO and trncd=999 and NET_AMT<0 and TRNDT <='"+dt+"' ");
			while(rs.next())
			{
				trbn= new TranBean();
				trbn.setEMPNO(rs.getInt("EMPNO"));
				trbn.setEMPNAME(rs.getString("FNAME")+" "+rs.getString("LNAME"));
				nslist.add(trbn);
			}
			rs.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return nslist;
	}
	
	public static StringBuilder getNagtiveSalaryMonth()
	{
		Connection con =ConnectionManager.getConnection();
		Statement st,st1;
		ResultSet rs,rs1 = null;
		StringBuilder result=new StringBuilder();
		try
		{
			st=con.createStatement();
			st1=con.createStatement();
			String query="select count(EMPNO)as NO_EMP ,SAL_MONTH  from SAL_DETAILS where SAL_STATUS='Negative' group by SAL_MONTH";
			rs=st.executeQuery(query);
			result.append("<ul id=\"atree\"><li style=\"cursor: pointer;\"><b>Negative List</b>");
			while(rs.next())
			{
				result.append("<ul ><li style=\"cursor: pointer;\"><b>"+rs.getString("SAL_MONTH").toUpperCase()+"("+rs.getString("NO_EMP")+")</b>");
				String q1="select E.FNAME,E.LNAME,E.EMPNO from EMPMAST E,SAL_DETAILS S where S.EMPNO=E.EMPNO and SAL_STATUS='Negative' and SAL_MONTH='"+rs.getString("SAL_MONTH")+"'";
				rs1=st1.executeQuery(q1);
				result.append("<ul>");
				while(rs1.next())
				{
					result.append("<li style=\"cursor: pointer;\"><font color=\"#4583EC\"><a onclick=\"getTrnsation("+rs1.getInt("EMPNO")+")\">"+rs1.getString("FNAME")+" "+rs1.getString("LNAME")+"</a></font></li>");
				}
				result.append("</ul>");
				result.append("</li></ul>");
				rs1.close();
			}
			result.append("</ul>");
			rs.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static StringBuilder getNoCTCEmp()
	{
		Connection con =ConnectionManager.getConnection();
		Statement st,st1;
		ResultSet rs,rs1 = null;
		StringBuilder result=new StringBuilder();
		try
		{
			st=con.createStatement();
			st1=con.createStatement();
			/*String query="select count(EMPNO)as NO_EMP ,SAL_MONTH  from SAL_DETAILS where SAL_STATUS='Negative' group by SAL_MONTH";
			System.out.println(query);
			rs=st.executeQuery(query);*/
			result.append("<ul id=\"atree\"><li style=\"cursor: pointer;\">> CTC NOT AVAILABLE<ul>");
			/*while(rs.next())
			{*/
				result.append("");//+rs.getString("SAL_MONTH").toUpperCase()+"("+rs.getString("NO_EMP")+")</b>");
				String q1="select fname,lname,empno from EMPMAST where status ='A' " +
							"and  DOL is null and" +
							" empno not in(select distinct(empno) from CTCDISPLAY)";
				
				rs1=st1.executeQuery(q1);
				result.append("<ul>");
				while(rs1.next())
				{
					result.append("<li style=\"cursor: pointer;\"><font size='2' color=\"#4583EC\"><a href='CTC.jsp'>"+rs1.getString("FNAME")+" "+rs1.getString("LNAME")+"</a></font></li>");
				}
				result.append("</ul>");
				result.append("</li></ul>");
				rs1.close();
			/*}*/
			result.append("</ul>");
			/*rs.close();*/
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
	public static StringBuilder getNotFinalized()
	{
		Connection con =ConnectionManager.getConnection();
		Statement st,st1;
		ResultSet rs,rs1 = null;
		StringBuilder result=new StringBuilder();
		try
		{
			st=con.createStatement();
			st1=con.createStatement();
			String query="select count(EMPNO)as NO_EMP ,SAL_MONTH  from SAL_DETAILS where SAL_STATUS in ('PROCESSED','AutoInst') group by SAL_MONTH";
			rs=st.executeQuery(query);
			result.append("<ul id=\"atree\"><li style=\"cursor: pointer;\"><b>Not Finalized </b>");
			while(rs.next())
			{
				result.append("<ul ><li style=\"cursor: pointer;\"><b>"+rs.getString("SAL_MONTH").toUpperCase()+"("+rs.getString("NO_EMP")+")</b>");
				String q1="select E.FNAME,E.LNAME,E.EMPNO from EMPMAST E,SAL_DETAILS S where S.EMPNO=E.EMPNO and SAL_STATUS in ('PROCESSED','AutoInst') and SAL_MONTH='"+rs.getString("SAL_MONTH")+"'";
				rs1=st1.executeQuery(q1);
				result.append("<ul>");
				while(rs1.next())
				{
					result.append("<li style=\"cursor: pointer;\"><font color=\"#4583EC\"><a onclick=\"getTrnsation("+rs1.getInt("EMPNO")+")\">"+rs1.getString("FNAME")+" "+rs1.getString("LNAME")+"</a></font></li>");
				}
				result.append("</ul>");
				result.append("</li></ul>");
				rs1.close();
			}
			result.append("</ul>");
			rs.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<String> getCTCTranValues(int empno,int type)
	{
		ArrayList<String> result = new ArrayList<String>();
		result.add("");
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT C.TRNCD,(SELECT CASE WHEN P.INP_AMT=0 THEN P.CAL_AMT WHEN P.INP_AMT IS NULL THEN P.CAL_AMT ELSE P.INP_AMT END FROM PAYTRAN P WHERE P.TRNCD=C.TRNCD AND P.EMPNO="+empno+")" +
											" FROM CDMAST C WHERE C.CDTYPE="+type+" ORDER BY C.TRNCD");
			
			String temp="";
			while(rs.next())
			{
				temp = rs.getString(2);
				result.add(temp!=null?temp.substring(0, temp.indexOf('.')):temp);
			}
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;    
	}
	
	
	public boolean updateEmpTran(int trncd, Float[] values, String [] values1)
	{
		Connection con = ConnectionManager.getConnection();
		boolean result = false;
		int i = 0;
		
		try 
		{
			Statement st = con.createStatement();
			Statement st1 = con.createStatement();
			Statement st2 = con.createStatement();
			ResultSet rs1;
			for(int k=0;k<values1.length;k++)
			{
			ResultSet rs = st1.executeQuery("SELECT EMPNO FROM EMPMAST WHERE STATUS='A' and empno="+values1[k]+" ORDER BY EMPNO");
			while(rs.next())
				
			{
				
				if(values[k]>=0)
				{
						rs1 = st2.executeQuery("SELECT * FROM PAYTRAN WHERE EMPNO="+rs.getInt("EMPNO")+" AND TRNCD="+trncd);
						if(rs1.next())
						{
							String update = "UPDATE PAYTRAN set INP_AMT="+values[k]+", UPDDT = GETDATE(),STATUS = 'A' where EMPNO = "+rs.getInt("EMPNO")+" and TRNCD="+trncd+" ";
							st.executeUpdate(update);
						}
						else
						{
							String update = "INSERT INTO PAYTRAN(TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,UPDDT,STATUS) " +
									"VALUES (GETDATE(), "+rs.getInt("EMPNO")+","+trncd+",0,"+values[k]+",0,0,0,0,0, GETDATE(),'A')";
							st.executeUpdate(update);
						}
				}
			
				
			}
			}
			result = true;
			con.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public boolean updateEmpTranNew1(int trncd, Float[] values, ArrayList<TranBean> projEmpNolist)
	{
		Connection con = ConnectionManager.getConnection();
		boolean result = false;
		int i = 0;
		
		try 
		{
			Statement st = con.createStatement();
			Statement st2 = con.createStatement();
			ResultSet rs1 = null;
			for(TranBean tbn : projEmpNolist)
			{ 
				if(values[i] >= 0)
				{
						rs1 = st2.executeQuery("SELECT * FROM PAYTRAN WHERE EMPNO="+tbn.getEMPNO()+" AND TRNCD="+trncd);
						if(rs1.next())
						{
							String update = "UPDATE PAYTRAN set INP_AMT="+values[i]+",CF_SW='*', UPDDT = GETDATE(),STATUS = 'A' where EMPNO = "+tbn.getEMPNO()+" and TRNCD="+trncd+" ";
							st.executeUpdate(update);
						}
						else
						{
							String update = "INSERT INTO PAYTRAN(TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,UPDDT,STATUS) " +
									"VALUES (GETDATE(), "+tbn.getEMPNO()+","+trncd+",0,"+values[i]+",0,0,0,0,'*', GETDATE(),'A')";
							st.executeUpdate(update);
						}
				}
				i++;
			}
			result = true;
			rs1.close();
			st2.close();
			st.close();
			con.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	//public boolean updateSiteEmpTranNew(Float[] mobded, Float[] lopded, ArrayList<TranBean> projEmpNolist, String updatedBy)
	public boolean updateSiteEmpTranNew(Float[] lopded, ArrayList<TranBean> projEmpNolist, String updatedBy)
	{
		Connection con = ConnectionManager.getConnection();
		boolean result = false;
		int i = 0;
		
		try 
		{
			Statement st = con.createStatement();
			Statement st2 = con.createStatement();
			ResultSet rs1 = null;
			for(TranBean tbn : projEmpNolist)
			{ 
				/*if(mobded[i] >= 0)
				{
						rs1 = st2.executeQuery("SELECT * FROM PAYTRAN WHERE EMPNO="+tbn.getEMPNO()+" AND TRNCD=223");
						if(rs1.next())
						{
							String update = "UPDATE PAYTRAN set INP_AMT="+mobded[i]+", UPDDT = GETDATE(), USRCODE='"+updatedBy+"' where EMPNO = "+tbn.getEMPNO()+" and TRNCD=223 ";
							st.executeUpdate(update);
						}
						else
						{
							String update = "INSERT INTO PAYTRAN(TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,UPDDT) " +
									"VALUES (GETDATE(), "+tbn.getEMPNO()+",223,0,"+mobded[i]+",0,0,0,0,'"+updatedBy+"', GETDATE())";		
							st.executeUpdate(update);
						}
				}*/
				if(lopded[i] >= 0)
				{
						rs1 = st2.executeQuery("SELECT * FROM PAYTRAN WHERE EMPNO="+tbn.getEMPNO()+" AND TRNCD=301");
						if(rs1.next())
						{
							String update = "UPDATE PAYTRAN set INP_AMT="+lopded[i]+", UPDDT = GETDATE(), USRCODE='"+updatedBy+"',STATUS='A' where EMPNO = "+tbn.getEMPNO()+" and TRNCD=301 ";
							st.executeUpdate(update);
						}
						else
						{
							String update = "INSERT INTO PAYTRAN(TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,UPDDT,STATUS) " +
									"VALUES (GETDATE(), "+tbn.getEMPNO()+",301,0,"+lopded[i]+",0,0,0,0,'"+updatedBy+"', GETDATE(),'A')";
							st.executeUpdate(update);
						}
				}
				i++;
			}
			result = true;
			rs1.close();
			st2.close();
			st.close();
			con.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String getSalaryDate()
	{
		SimpleDateFormat format=new SimpleDateFormat("dd-MMM-yyyy");
		
		Connection con = ConnectionManager.getConnection();
		String dt="";
		
		try 
		{
			Statement st = con.createStatement();
			
			ResultSet rs1 = st.executeQuery("SELECT CONVERT(varchar(10),min (SALDATE),120) from SALARY_MONTH");
			
			while(rs1.next())
			{
				dt=format.format(rs1.getDate(1));
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return dt;
	}
	public static void ctc_change_PayTran(int empno){
		String sql_insert = "INSERT INTO CTC_CHANGE_PAYTRAN (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,USRCODE,UPDDT,CHANGE_DATE,STATUS) " +
					 "SELECT TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,USRCODE,UPDDT,'"+ReportDAO.getSysDate()+"',STATUS " +
					 "FROM PAYTRAN where EMPNO = "+empno;
		String sql_delete = "delete from PAYTRAN where trncd in(101,102,103,104,105,108,129,138,140,141,199)and EMPNO = "+empno;
		try{
		Connection con = ConnectionManager.getConnection();
		Statement st =con.createStatement();
		st.execute(sql_insert);
		st.execute(sql_delete);
		//System.out.println(sql_insert);
		
		//System.out.println(sql_delete);
		st.close();
		con.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	public  ArrayList getTranforSalalryStruct(int empno, int year)
	{
		//System.out.println("into getTranforSalalryStruct() of Tran Handlr");
		ArrayList list=new ArrayList();
		ArrayList list_in=new ArrayList();
		ArrayList list_ded=new ArrayList();
		//float[] array =new float[12];
		ArrayList<Float> total_in=new ArrayList<Float>(Collections.nCopies(12,0.0f));
		ArrayList<Float> total_ded=new ArrayList<Float>(Collections.nCopies(12, 0.0f));
	//	ArrayList<Integer> cds= new ArrayList<Integer>(Arrays.asList(101,108,103,104,105,106,126,128,107,130,131,132,133,202,201,228,205,212,225));
		ArrayList<Integer> cds= new ArrayList<Integer>(Arrays.asList(101,102,103,104,105,108,129,138,140,141,142,201,202,206,207,211,228,231,232,242,243,244,245,246,247,248,249,250,251));
		
		Date dd=new Date();
		String yy[]=ReportDAO.getSysDate().split("-");
		int yyyy=Integer.parseInt(yy[2]);
		int mm=dd.getMonth();
		String startdate ="",enddate="",tempdate="";
		startdate="1-Apr-"+year; 
		enddate="31-Mar-"+(year+1);
		tempdate=startdate;
		float tempval=0;
		
		TranBean tb;
		
		try
		{
			
			Connection con= ConnectionManager.getConnection();
			/*Statement ss=con.createStatement();
			ResultSet rr=ss.executeQuery("select distinct(trncd) from paytran where empno="+empno+" order by trncd");
			int index=0;
			System.out.println("size of cds= "+cds.size());
			while(rr.next())
			{
				cds.add(rr.getInt(1));				
			}
			System.out.println("size of cds= "+cds.size());*/
			for(int i=0;i<cds.size();i++)
			{
				tempval=0;
				int count=0;
			ArrayList<TranBean> alist=new ArrayList<>();
			
		/*	if(mm>=4)
			{
				 startdate=yyyy+"-04-01";enddate=(yyyy+1)+"-03-31";tempdate=startdate;
			}
			else
			{
				startdate=(yyyy-1)+"-04-01";enddate=(yyyy+1)+"-03-31";tempdate=startdate;
			}
			*/
			int cd=cds.get(i);
			Statement st = con.createStatement();
			String monthForCheck="";
			ResultSet rs= null;
			//String query="SELECT trncd,trndt,net_amt FROM ytdtran  WHERE empno = "+empno+" and trncd="+cd+" and trndt BETWEEN '"+startdate+"' AND '"+enddate+"'  order by trndt";
			String query=" SELECT trncd,month_name,net_amt FROM ( select y.trncd, disc," +
					"  sum(case datepart(MM,trndt) when 04 then net_amt else  0 end) apr," +
					"  sum(case datepart(MM,trndt) when 05 then net_amt else  0 end) may," +
					"  sum(case datepart(MM,trndt) when 06 then net_amt else  0 end) jun," +
					"  sum(case datepart(MM,trndt) when 07 then net_amt else  0 end) jul," +
					"  sum(case datepart(MM,trndt) when 08 then net_amt else  0 end) aug," +
					"  sum(case datepart(MM,trndt) when 09 then net_amt else  0 end) sep," +
					"  sum(case datepart(MM,trndt) when 10 then net_amt else  0 end) oct," +
					"  sum(case datepart(MM,trndt) when 11 then net_amt else  0 end) nov," +
					"  sum(case datepart(MM,trndt) when 12 then net_amt else  0 end) dec," +
					"  sum(case datepart(MM,trndt) when 01 then net_amt else  0 end) jan," +
					"  sum(case datepart(MM,trndt) when 02 then net_amt else  0 end) feb," +
					"  sum(case datepart(MM,trndt) when 03 then net_amt else  0 end) mar" +
					" from empmast e,CDMAST c,paytran_stage y where e.empno = "+empno+" and e.empno = y.empno " +
					"and y.trncd = c.trncd and y.trndt BETWEEN '"+startdate+"' AND '"+enddate+"'  " +
					"and y.trncd="+cd+"  " +
					"group by  e.empno,fname,lname,disc,y.trncd ) p UNPIVOT " +
					"   ( net_amt FOR month_name  IN      ( apr,may,jun,jul,aug,sep,oct,nov,dec,jan,feb,mar ) )AS unpvt ";
			
			
			/*String query=" SELECT trncd,month_name,net_amt FROM ( select y.trncd, disc, " +
					"   case when datepart(MM,y.trndt)='04' then net_amt" +
					"   else (select INP_AMT from CTCDISPLAY where EMPNO="+empno+" and trncd="+cd+")  end as apr ," +
					"   case when datepart(MM,y.trndt)='05' then net_amt" +
					"   else (select INP_AMT from CTCDISPLAY where EMPNO="+empno+" and trncd="+cd+") end as may , " +
					"   case when datepart(MM,y.trndt)='06' then net_amt" +
					"   else (select INP_AMT from CTCDISPLAY where EMPNO="+empno+" and trncd="+cd+")  end as jun  ," +
					"   case when datepart(MM,y.trndt)='07' then net_amt" +
					"   else (select INP_AMT from CTCDISPLAY where EMPNO="+empno+" and trncd="+cd+") end as jul  ," +
					"   case when datepart(MM,y.trndt)='08' then net_amt  " +
					"   else (select INP_AMT from CTCDISPLAY where EMPNO="+empno+" and trncd="+cd+") end as Aug  ," +
					"   case when datepart(MM,y.trndt)='09' then net_amt" +
					"   else (select INP_AMT from CTCDISPLAY where EMPNO="+empno+" and trncd="+cd+")  end as sep ," +
					"   case when datepart(MM,y.trndt)='10' then net_amt" +
					"   else (select INP_AMT from CTCDISPLAY where EMPNO="+empno+" and trncd="+cd+")  end as oct  ," +
					"   case when datepart(MM,y.trndt)='11' then net_amt" +
					"   else (select INP_AMT from CTCDISPLAY where EMPNO="+empno+" and trncd="+cd+")  end as nov  ," +
					"   case when datepart(MM,y.trndt)='12' then net_amt" +
					"   else (select INP_AMT from CTCDISPLAY where EMPNO="+empno+" and trncd="+cd+")  end as dec ," +
					"   case when datepart(MM,y.trndt)='01' then net_amt" +
					"   else (select INP_AMT from CTCDISPLAY where EMPNO="+empno+" and trncd="+cd+")  end as jan ," +
					"   case when datepart(MM,y.trndt)='02' then net_amt" +
					"   else (select INP_AMT from CTCDISPLAY where EMPNO="+empno+" and trncd="+cd+")   end as feb  ," +
					"   case when datepart(MM,y.trndt)='03' then net_amt" +
					"     else (select INP_AMT from CTCDISPLAY where EMPNO="+empno+" and trncd="+cd+")  end as mar" +
						
					" from empmast e,CDMAST c,paytran_stage y where e.empno = "+empno+" and e.empno = y.empno " +
					"and y.trncd = c.trncd and y.trndt BETWEEN '"+startdate+"' AND '"+enddate+"'  " +
					"and y.trncd="+cd+"  " +
					" group by  e.empno,fname,lname,disc,y.trncd" +
					" ) p UNPIVOT " +
					"   ( net_amt FOR month_name  IN      ( apr,may,jun,jul,aug,sep,oct,nov,dec,jan,feb,mar ) )AS unpvt ";*/
			
		//	System.out.println("inside tran handler query for tds "+query);
			rs= st.executeQuery(query);
			
			
			while(rs.next())
			{
				tb = new TranBean();
				/*//tb.setADJ_AMT(rs.getFloat("ADJ_AMT"));
				tb.setARR_AMT(rs.getFloat("ARR_AMT"));
				tb.setCAL_AMT(rs.getFloat("CAL_AMT"));
				tb.setCF_SW(rs.getString("CF_SW")==null?"-":rs.getString("CF_SW"));*/
				//tb.setEMPNO(rs.getInt("EMPNO"));
				//tb.setINP_AMT(rs.getFloat("INP_AMT"));
				tb.setNET_AMT(rs.getFloat("NET_AMT"));
				tempval=rs.getFloat("NET_AMT");
				//tb.setSRNO(rs.getInt("SRNO"));
				tb.setTRNCD(rs.getInt("TRNCD"));
				//tb.setTRNDT(EmpOffHandler.dateFormat(rs.getDate("month_name")));
				//tempdate=rs.getString("month_name");
				//monthForCheck=rs.getString("month_name").substring(5,7);
				//System.out.println("monthForCheck value++++++++"+monthForCheck);
				
				//tb.setUPDDT(EmpOffHandler.dateFormat(rs.getDate("UPDDT")));
				//tb.setUSRCODE(rs.getString("USRCODE"));
				if(cd>=101 && cd<=200 || cd==522)
				{
					total_in.set(count, (total_in.get(count) + tempval));
					//System.out.println("monthForCheck count value ++++++++"+count+"   ");
					count++;
					
				}
				if(cd>=201 && cd!=522)
				{
					total_ded.set(count, (total_ded.get(count) + tempval));
					count++;
				}
				alist.add(tb);
			}
			
			
			
			/*while(rs.next())
			{
				tb = new TranBean();
				//tb.setADJ_AMT(rs.getFloat("ADJ_AMT"));
				tb.setARR_AMT(rs.getFloat("ARR_AMT"));
				tb.setCAL_AMT(rs.getFloat("CAL_AMT"));
				tb.setCF_SW(rs.getString("CF_SW")==null?"-":rs.getString("CF_SW"));
				//tb.setEMPNO(rs.getInt("EMPNO"));
				//tb.setINP_AMT(rs.getFloat("INP_AMT"));
				tb.setNET_AMT(rs.getFloat("NET_AMT"));
				tempval=rs.getFloat("NET_AMT");
				//tb.setSRNO(rs.getInt("SRNO"));
				tb.setTRNCD(rs.getInt("TRNCD"));
				tb.setTRNDT(EmpOffHandler.dateFormat(rs.getDate("TRNDT")));
				tempdate=rs.getString("TRNDT");
				monthForCheck=rs.getString("TRNDT").substring(5,7);
				//System.out.println("monthForCheck value++++++++"+monthForCheck);
				
				//tb.setUPDDT(EmpOffHandler.dateFormat(rs.getDate("UPDDT")));
				//tb.setUSRCODE(rs.getString("USRCODE"));
				if(cd>=101 && cd<=200 || cd==522)
				{
					total_in.set(count, (total_in.get(count) + tempval));
					//System.out.println("monthForCheck count value ++++++++"+count+"   ");
					count++;
					
				}
				if(cd>=201 && cd!=522)
				{
					total_ded.set(count, (total_ded.get(count) + tempval));
					count++;
				}
				alist.add(tb);
			}*/
			
			if(alist.size()<12)
			{
				//System.out.println("into else part");
				 rs= null;
				 query="SELECT trncd,trndt,net_amt FROM paytran  WHERE empno = "+empno+" and trncd="+cd+" and trndt BETWEEN '"+tempdate+"' AND '"+enddate+"'  order by trndt";
				rs= st.executeQuery(query);
				while(rs.next())
				{
					tb = new TranBean();
					//tb.setADJ_AMT(rs.getFloat("ADJ_AMT"));
					//tb.setARR_AMT(rs.getFloat("ARR_AMT"));
					//tb.setCAL_AMT(rs.getFloat("CAL_AMT"));
					////tb.setCF_SW(rs.getString("CF_SW")==null?"-":rs.getString("CF_SW"));
					//tb.setEMPNO(rs.getInt("EMPNO"));
					//tb.setINP_AMT(rs.getFloat("INP_AMT"));
					tb.setNET_AMT(rs.getFloat("NET_AMT"));
					tempval=rs.getFloat("NET_AMT");
					//tb.setSRNO(rs.getInt("SRNO"));
					tb.setTRNCD(rs.getInt("TRNCD"));
					tb.setTRNDT(EmpOffHandler.dateFormat(rs.getDate("TRNDT")));
					//tb.setUPDDT(EmpOffHandler.dateFormat(rs.getDate("UPDDT")));
					//tb.setUSRCODE(rs.getString("USRCODE"));
					
					if(cd>=101 && cd<=200 || cd==522)
					{
						total_in.set(count, (total_in.get(count) + tempval));
						count++;
					}
					if(cd>=201 && cd!=522)
					{
						total_ded.set(count, (total_ded.get(count) + tempval));
						count++;
					}
					
					
					alist.add(tb);
				}
			
				while(alist.size()<12)	
				{
				//	System.out.println("into empty else");
					tb = new TranBean();
					if(cds.get(i)==228||cds.get(i)==130||cds.get(i)==131||cds.get(i)==132||cds.get(i)==133||cds.get(i)==225)
					{
						tb.setNET_AMT(0);
						tempval=0;
					}
					else
					{
					tb.setNET_AMT(tempval);
					}
					tb.setTRNCD(cds.get(i));
					tb.setTRNDT("");
					if(cd>=101 && cd<=200 || cd==522)
					{
						total_in.set(count, (total_in.get(count) + tempval));
						count++;
					}
					if(cd>=201 && cd!=522)
					{
						total_ded.set(count, (total_ded.get(count) + tempval));
						count++;
					}
					alist.add(tb);
				}
				
			}
			if(cd<=200 ||cd==522 ){list_in.add(alist);}
			if(cd>200 && cd!=522 ){list_ded.add(alist);}
		
			}
			con.close();
			
			}
		catch(Exception e)
		{
				e.printStackTrace();
			
		}
	
	ArrayList<TranBean> alist=new ArrayList<>();
	ArrayList<TranBean> arlist=new ArrayList<>();
	ArrayList<TranBean> arllist=new ArrayList<>();
	float totalforremain=0;
	for(int c=0;c<total_in.size();c++)
	{
		TranBean b=new TranBean();
		TranBean d=new TranBean();
		TranBean e=new TranBean();
		b.setNET_AMT(total_in.get(c));
		if(b.getNET_AMT()!=0)
		{
			totalforremain=b.getNET_AMT();
			//System.out.println("inside if "+totalforremain);
		}
		else
		{
			//System.out.println("inside else"+totalforremain);
			b.setNET_AMT(totalforremain);
		}
		d.setNET_AMT(total_ded.get(c));
		e.setNET_AMT(total_in.get(c)-total_ded.get(c));
		alist.add(b);
		arlist.add(d);
		arllist.add(e);
		
	}

	list_in.add(alist);
	list_ded.add(arlist);
	list_ded.add(arllist);
	list.add(list_in);
	list.add(list_ded);
	
	return list;
	}
	
	public boolean setAdvncInPAYTRAN(String date)
	{
		boolean flag=false;
		try
		{
		Connection con= ConnectionManager.getConnection();
		Statement st=con.createStatement();
		Statement st1=con.createStatement();
		Statement st2=con.createStatement();
		System.out.println("INTO setAdvncInPAYTRAN()");
		ResultSet rs=st.executeQuery("SELECT empno,SUM(INP_AMT) from PAYTRAN_ADVANCE where trndt between '"+ReportDAO.BOM(date)+"' and  '"+ReportDAO.EOM(date)+"' and status='erp' group by empno");
			while(rs.next())
			{
				int empno=rs.getInt(1);
				float amt=rs.getFloat(2);
				
				st1.executeUpdate("update PAYTRAN_ADVANCE set status='in' where empno="+empno+" and trndt between '"+ReportDAO.BOM(date)+"' and  '"+ReportDAO.EOM(date)+"' and status='erp' ");
				System.out.println("update into ADVNCE");
				st2.executeUpdate("insert into  PAYTRAN values('"+date+"',"+empno+",225,0,"+amt+",0.00,0.00,0.00,0.00,0,NULL,'"+ReportDAO.getSysDate()+"','A')");
				System.out.println("insert into PAYTRAN");
				
			}
		st.close();
		con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		return flag;
	}
	

	

	public static String currentSalMonth(String empno)
	{
		Connection con= ConnectionManager.getConnection();
		String month = "";
		try
		{
			Statement st = con.createStatement();
			String sql="Select max(SAL_PAID_DATE) FROM SAL_DETAILS where EMPNO ="+empno+" and SAL_STATUS ='finalized' ";
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) {
				month = rs.getString(1);
			}
			con.close();
		}	
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return month;
	}
	
	public static ArrayList<TranBean> getReleaseSal(String date, String prno, int range)
	{
		ArrayList<TranBean> result = new ArrayList<TranBean>();
		TranBean trbn;
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = null;
			String actDate[] = date.split("-");
			LookupHandler lkp = new LookupHandler();
			if(prno.equalsIgnoreCase("all")){
				rs = st.executeQuery("select * from PAYTRAN_STAGE where TRNDT between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"'  and TRNCD =999 and status='F'" +
						" order by empno, net_amt");
				String str1="select * from PAYTRAN_STAGE where TRNDT between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"'  and TRNCD =999 and status='F'" +
						" order by empno, net_amt";
				System.out.println("str1"+str1);
			}else{
				rs = st.executeQuery("select * from PAYTRAN_STAGE p, EMPTRAN e where p.empno=e.empno and" +
						" e.prj_srno="+prno+" and p.TRNDT between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' and p.TRNCD =999 and p.status='F'" +
						" and e.EFFDATE = (SELECT MAX(E2.EFFDATE) FROM EMPTRAN E2 WHERE E2.EMPNO = p.EMPNO AND " +
						"E2.EFFDATE <= '"+ReportDAO.EOM(date)+"') order by p.empno,p.net_amt");
				String str2="select * from PAYTRAN_STAGE p, EMPTRAN e where p.empno=e.empno and" +
						" e.prj_srno="+prno+" and p.TRNDT between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' and p.TRNCD =999 and p.status='F'" +
						" and e.EFFDATE = (SELECT MAX(E2.EFFDATE) FROM EMPTRAN E2 WHERE E2.EMPNO = p.EMPNO AND " +
						"E2.EFFDATE <= '"+ReportDAO.EOM(date)+"') order by p.empno,p.net_amt";
				System.out.println("str2"+str2);
			}						
			while(rs.next())
			{
				if(range==10 && rs.getInt("net_amt")<=10000){
					trbn = new TranBean();
					trbn.setEMPNO(rs.getInt("empno"));
					trbn.setEMPNAME(lkp.getLKP_Desc("ET", rs.getInt("empno")));
					trbn.setINP_AMT(rs.getFloat("inp_amt"));
					trbn.setCAL_AMT(rs.getFloat("cal_amt"));
					trbn.setNET_AMT(rs.getFloat("net_amt"));
					result.add(trbn);
				}else if(range==20 && rs.getInt("net_amt")>10000 && rs.getInt("net_amt")<=20000){
					trbn = new TranBean();
					trbn.setEMPNO(rs.getInt("empno"));
					trbn.setEMPNAME(lkp.getLKP_Desc("ET", rs.getInt("empno")));
					trbn.setINP_AMT(rs.getFloat("inp_amt"));
					trbn.setCAL_AMT(rs.getFloat("cal_amt"));
					trbn.setNET_AMT(rs.getFloat("net_amt"));
					result.add(trbn);
				}else if(range==30 && rs.getInt("net_amt")>20000 && rs.getInt("net_amt")<=30000){
					trbn = new TranBean();
					trbn.setEMPNO(rs.getInt("empno"));
					trbn.setEMPNAME(lkp.getLKP_Desc("ET", rs.getInt("empno")));
					trbn.setINP_AMT(rs.getFloat("inp_amt"));
					trbn.setCAL_AMT(rs.getFloat("cal_amt"));
					trbn.setNET_AMT(rs.getFloat("net_amt"));
					result.add(trbn);
				}else if(range==40 && rs.getInt("net_amt")>30000 && rs.getInt("net_amt")<=40000){
					trbn = new TranBean();
					trbn.setEMPNO(rs.getInt("empno"));
					trbn.setEMPNAME(lkp.getLKP_Desc("ET", rs.getInt("empno")));
					trbn.setINP_AMT(rs.getFloat("inp_amt"));
					trbn.setCAL_AMT(rs.getFloat("cal_amt"));
					trbn.setNET_AMT(rs.getFloat("net_amt"));
					result.add(trbn);
				}else if(range==50 && rs.getInt("net_amt")>40000 && rs.getInt("net_amt")<=50000){
					trbn = new TranBean();
					trbn.setEMPNO(rs.getInt("empno"));
					trbn.setEMPNAME(lkp.getLKP_Desc("ET", rs.getInt("empno")));
					trbn.setINP_AMT(rs.getFloat("inp_amt"));
					trbn.setCAL_AMT(rs.getFloat("cal_amt"));
					trbn.setNET_AMT(rs.getFloat("net_amt"));
					result.add(trbn);
				}else if(range==51 && rs.getInt("net_amt")>50000){
					trbn = new TranBean();
					trbn.setEMPNO(rs.getInt("empno"));
					trbn.setEMPNAME(lkp.getLKP_Desc("ET", rs.getInt("empno")));
					trbn.setINP_AMT(rs.getFloat("inp_amt"));
					trbn.setCAL_AMT(rs.getFloat("cal_amt"));
					trbn.setNET_AMT(rs.getFloat("net_amt"));
					result.add(trbn);
				}else if(range==0){
					trbn = new TranBean();
					trbn.setEMPNO(rs.getInt("empno"));
					trbn.setEMPNAME(lkp.getLKP_Desc("ET", rs.getInt("empno")));
					trbn.setINP_AMT(rs.getFloat("inp_amt"));
					trbn.setCAL_AMT(rs.getFloat("cal_amt"));
					trbn.setNET_AMT(rs.getFloat("net_amt"));
					result.add(trbn);
				}
			}
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;    
	}
	public boolean releaseSalary(String eno, String date) {
		boolean flag = false;
		try
		{
			Connection con = ConnectionManager.getConnection();
			con.setAutoCommit(false);
			Statement st = con.createStatement();
			
String empno[]=eno.split(",");	
			
			for (int x=0;x<empno.length;x++)
	         {	
	         Statement st4=con.createStatement();
	         ResultSet rs3=st4.executeQuery("select * from DEDMAST where empno="+empno[x]+" and ACTYN='Y'");
	         
	         while(rs3.next())
	         {
	        	 
	        	 if(rs3.getFloat("SANC_AMT")==rs3.getFloat("ACTUAL_TOTAL_AMT_PAID") || rs3.getFloat("SANC_AMT")<rs3.getFloat("ACTUAL_TOTAL_AMT_PAID")  )
	        	 {
	        		 Statement st5=con.createStatement(); 
	        		 Statement st6=con.createStatement();
	        		 Statement st7=con.createStatement();
	        		 st5.execute("update dedmast set ACTYN='N' where empno="+empno[x]+" and TRNCD="+rs3.getInt("TRNCD")+" and SRNO="+rs3.getInt("SRNO"));
	        		 st6.execute("UPDATE PAYTRAN_STAGE set CF_SW='0' where TRNCD="+rs3.getInt("TRNCD")+" and  trndt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' and empno ="+empno[x]+" ");
	        		 st7.execute("update dedmast set ACTUAL_TOTAL_AMT_PAID=(ACTUAL_TOTAL_AMT_PAID +(SELECT NET_AMT from PAYTRAN_STAGE  where trndt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' and TRNCD="+rs3.getInt("TRNCD")+" and empno ="+empno[x]+" )) where empno="+empno[x]+" and TRNCD="+rs3.getInt("TRNCD")+" and SRNO="+rs3.getInt("SRNO"));
	        		 System.out.println("into if update dedmast set ACTUAL_TOTAL_AMT_PAID=(ACTUAL_TOTAL_AMT_PAID +(SELECT NET_AMT from PAYTRAN_STAGE  where trndt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' and TRNCD="+rs3.getInt("TRNCD")+" and empno ="+empno[x]+" ) where empno="+empno[x]+" and TRNCD="+rs3.getInt("TRNCD")+" and SRNO="+rs3.getInt("SRNO"));     		 
	        	 }
	        	 else
	        	 {
	        		 System.out.println("into else "+"update dedmast set ACTUAL_TOTAL_AMT_PAID=(ACTUAL_TOTAL_AMT_PAID +(SELECT NET_AMT from PAYTRAN_STAGE  where trndt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' and TRNCD="+rs3.getInt("TRNCD")+" and empno ="+empno[x]+" ) where empno="+empno[x]+" and TRNCD="+rs3.getInt("TRNCD")+" and SRNO="+rs3.getInt("SRNO"));
	        		 Statement st7=con.createStatement(); 
	        		 st7.execute("update dedmast set ACTUAL_TOTAL_AMT_PAID=(ACTUAL_TOTAL_AMT_PAID +(SELECT NET_AMT from PAYTRAN_STAGE  where trndt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' and TRNCD="+rs3.getInt("TRNCD")+" and empno ="+empno[x]+" ) ) where empno="+empno[x]+" and TRNCD="+rs3.getInt("TRNCD")+" and SRNO="+rs3.getInt("SRNO"));
	        	 }
	        	 
	         }
	         
	         }	
			
			
			String actDate[] = date.split("-");
			//st.executeUpdate("delete from PAYTRAN_STAGE where INP_AMT= 0 and CAL_AMT= 0 and NET_AMT = 0 and CF_SW <> '*' and empno in("+eno+") and trndt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' ");
			flag = st.execute("Update PAYTRAN_STAGE set status='R' where trndt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' and empno in("+eno+")");
			//System.out.println("Update PAYTRAN_STAGE set status='R' where trndt='25-"+actDate[1]+"-"+actDate[2]+"' and empno in("+eno+")");
			String sql_temp = "INSERT INTO YTDTRAN (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,UPDDT,STATUS) ";
			sql_temp = sql_temp + "SELECT TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,TRNDT,'F' FROM PAYTRAN_STAGE WHERE EMPNO IN ("+eno+") and trndt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' ORDER BY EMPNO, TRNCD";
			//System.out.println(sql_temp);
			
			st.execute(sql_temp);
			
			int batchid=1;
			Statement st3=con.createStatement();
			ResultSet rs=st3.executeQuery("select isnull(max(BATCHNO),0)+1 from RELEASE_BATCH");
			if(rs.next())
			{
				batchid=rs.getInt(1);
			}
		
		
         Statement st2=con.createStatement();
         
         for (int x=0;x<empno.length;x++)
         {
         st2.execute("insert into RELEASE_BATCH (empno,TRNDT,UPDDT,USRCODE,BATCHNO)"+
         			  " values("+empno[x]+",'"+ReportDAO.EOM(date)+"',GETDATE(),0,"+batchid+")");
         }
  			
			
			con.commit();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}
	public static ArrayList<TranBean> getSalChange(String empno,String date)
	{
		ArrayList<TranBean> result = new ArrayList<TranBean>();
		TranBean trbn;
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			String actDate[] = date.split("-");
			//ResultSet rs = st.executeQuery("select * from PAYTRAN_STAGE where TRNDT ='25-"+actDate[1]+"-"+actDate[2]+"' and TRNCD not in (127,135) and empno ="+empno+" order by trncd");
			ResultSet rs = st.executeQuery("select ps.* from PAYTRAN_STAGE ps left outer join CDMAST cm on ps.TRNCD = cm.TRNCD  where ps.TRNDT  between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' and cm.PSLIPYN ='Y' and ps.TRNCD not in (127,135) and ps.empno ="+empno+" order by ps.trncd");
			
			while(rs.next())
			{
				trbn = new TranBean();
				trbn.setTRNDT(rs.getString("TRNDT"));
				trbn.setTRNCD(rs.getInt("trncd"));
				trbn.setINP_AMT(rs.getFloat("inp_amt"));
				trbn.setCAL_AMT(rs.getFloat("cal_amt"));
				trbn.setADJ_AMT(rs.getFloat("ADJ_AMT"));
				trbn.setNET_AMT(rs.getFloat("net_amt"));		
				result.add(trbn);
			}
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;    
	}
	public boolean updateSalChange(String empno, String date, ArrayList<TranBean> updated, float ded, float net)
	{
		boolean flag = false;
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			String actDate[] = date.split("-");
			//System.out.println("insert into PAYTRAN_STAGE_HISTORY (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,USRCODE,UPDDT,CHANGE_DATE) (select TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,USRCODE,UPDDT,'"+ReportDAO.getSysDate()+"' from PAYTRAN_STAGE where TRNDT ='25-"+actDate[1]+"-"+actDate[2]+"' and empno ="+empno+")");
			st.execute("insert into PAYTRAN_STAGE_HISTORY (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,USRCODE,UPDDT,CHANGE_DATE) (select TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,USRCODE,UPDDT,'"+ReportDAO.getSysDate()+"' from PAYTRAN_STAGE where TRNDT  between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' and empno ="+empno+")");
			for(TranBean tbn : updated){
				//System.out.println("update PAYTRAN_STAGE set INP_amt="+tbn.getINP_AMT()+",cal_amt="+tbn.getCAL_AMT()+",net_amt="+tbn.getNET_AMT()+" where trncd="+tbn.getTRNCD()+" and TRNDT ='25-"+actDate[1]+"-"+actDate[2]+"' and empno ="+empno);
				st.executeUpdate("update PAYTRAN_STAGE set INP_amt="+tbn.getINP_AMT()+",cal_amt="+tbn.getCAL_AMT()+",net_amt="+tbn.getNET_AMT()+" where trncd="+tbn.getTRNCD()+" and TRNDT  between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' and empno ="+empno);
			}
			//System.out.println("update PAYTRAN_STAGE set cal_amt="+ded+",adj_amt="+net+",net_amt="+net+" where trncd=999 and TRNDT ='25-"+actDate[1]+"-"+actDate[2]+"' and empno ="+empno);
			st.executeUpdate("update PAYTRAN_STAGE set cal_amt="+ded+",adj_amt="+net+",net_amt="+net+" where trncd=999 and TRNDT between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' and empno ="+empno);
			flag=true;
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;    
	}
	
	
	public boolean updateEmpTran(int trncd, Float[] values)
	{
		Connection con = ConnectionManager.getConnection();
		boolean result = false;
		int i = 0;
		
		try 
		{
			Statement st = con.createStatement();
			Statement st1 = con.createStatement();
			Statement st2 = con.createStatement();
			ResultSet rs1;
			ResultSet rs = st1.executeQuery("SELECT EMPNO FROM EMPMAST WHERE STATUS='A' ORDER BY EMPNO");
			while(rs.next())
			{
				if(values[i]>=0)
				{
						rs1 = st2.executeQuery("SELECT * FROM PAYTRAN WHERE EMPNO="+rs.getInt("EMPNO")+" AND TRNCD="+trncd);
						if(rs1.next())
						{
							String update = "UPDATE PAYTRAN set INP_AMT="+values[i]+",CF_SW='*', UPDDT = GETDATE(),STATUS = 'A' where EMPNO = "+rs.getInt("EMPNO")+" and TRNCD="+trncd+" ";
							st.executeUpdate(update);
						}
						else
						{
							String update = "INSERT INTO PAYTRAN(TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,UPDDT,STATUS) " +
									"VALUES (GETDATE(), "+rs.getInt("EMPNO")+","+trncd+",0,"+values[i]+",0,0,0,0,'*', GETDATE(),'A')";
							st.executeUpdate(update);
						}
				}
				i++;
			}
			result = true;
			con.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	


	public boolean updateEmpTran_mob(int trncd, Float[] values, String [] values1)
	{
		Connection con = ConnectionManager.getConnection();
		boolean result = false;
		int i = 0;
		
		try 
		{
			Statement st = con.createStatement();
			Statement st1 = con.createStatement();
			Statement st2 = con.createStatement();
			ResultSet rs1;
			for(int k=0;k<values1.length;k++)
			{
			ResultSet rs = st1.executeQuery("SELECT EMPNO FROM EMPMAST WHERE STATUS='A' and empno="+values1[k]+" ORDER BY EMPNO");
			while(rs.next())
				
			{
				
				if(values[k]>=0)
				{
						rs1 = st2.executeQuery("SELECT * FROM PAYTRAN WHERE EMPNO="+rs.getInt("EMPNO")+" AND TRNCD="+trncd);
						if(rs1.next())
						{
							String update = "UPDATE PAYTRAN set INP_AMT="+values[k]+", UPDDT = GETDATE(),STATUS = 'P' where EMPNO = "+rs.getInt("EMPNO")+" and TRNCD="+trncd+" ";
							st.executeUpdate(update);
						}
						else
						{
							String update = "INSERT INTO PAYTRAN(TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,UPDDT,STATUS) " +
									"VALUES (GETDATE(), "+rs.getInt("EMPNO")+","+trncd+",0,"+values[k]+",0,0,0,0,0, GETDATE(),'P')";
							st.executeUpdate(update);
						}
				}
			
				
			}
			}
			result = true;
			con.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}


	public static int LastSalaryFortdsDisplay(int emp, int code)
	{

		//System.out.println("emp no         qq "+emp+"   badshah code "+code);
		int value=0;
		
		Connection con = ConnectionManager.getConnection();
		/*if(code==0)
		{
			code=999;
		}*/
		
		try 
		{
			Statement st = con.createStatement();
			
			String query="select sum(net_amt) from PAYTRAN_STAGE where EMPNO="+emp+" and trncd="+code+" and TRNDT=(select MAX(trndt) from PAYTRAN_STAGE where EMPNO="+emp+"  )";
			//System.out.println("query for LastSalaryFortdsDisplay "+query);
			ResultSet rs1 = st.executeQuery(query);
			
			while(rs1.next())
			{
				value= rs1.getInt(1);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return value;

		
		
		
		
		
		
	}


	public String getSalaryDate(String empno)
	{
		SimpleDateFormat format=new SimpleDateFormat("dd-MMM-yyyy");
		
		Connection con = ConnectionManager.getConnection();
		String dt="";
		
		try 
		{
			Statement st = con.createStatement();
			
			ResultSet rs1 = st.executeQuery("SELECT CONVERT(varchar(10),max (trndt),120) from paytran_stage where empno="+empno+"");
			
			while(rs1.next())
			{
				dt=format.format(rs1.getDate(1)==null?"2015-01-01":rs1.getDate(1));
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return dt;
	}

	public String SalaryDifferenceUpdate(String FromDate,String ToDate,String itype)
	{
		 
		Connection con = ConnectionManager.getConnection();
		Statement st=null;
		Statement st3=null;
		Statement st2=null;
		ResultSet rs1=null;
		ResultSet rs2=null;
		String flag="";
		// ToDate="30-Jun-2017";
		try 
		{    st3=con.createStatement();
		     st2=con.createStatement();
			 st = con.createStatement();
			 
			 
			 st.executeUpdate("insert into increament_diff_history  select * from paytran_increament_diff");
			 st.executeUpdate("truncate table paytran_increament_diff");
			// System.out.println("insert into paytran_increament_diff  select * from paytran_stage where trndt between '"+ReportDAO.BOM(FromDate)+"' and '"+ReportDAO.BOM(ToDate)+"' ");
			st.executeUpdate("insert into paytran_increament_diff  select * from paytran_stage where trndt between '"+ReportDAO.BOM(FromDate)+"' and '"+ReportDAO.BOM(ToDate)+"' ");
			 		//		System.out.println("Record insertted in paytran_increament_diff from..."+ReportDAO.BOM(FromDate)+" to "+ReportDAO.BOM(ToDate));
			 
			 st.executeUpdate("update paytran_increament_diff set cal_amt=0.00,net_amt=0.00,adj_amt=0.00");
			 	//			System.out.println("paytran_increament_diff is updated with set cal_amt=0.00,net_amt=0.00,adj_amt=0.00..");
			 
			st.executeUpdate("update paytran_increament_diff  set  cal_amt =(select p.inp_amt from paytran p where  p.TRNCD=paytran_increament_diff.trncd and p.EMPNO = paytran_increament_diff.empno)");
			 		//		System.out.println("paytran_increament_diff is updated with  cal_amt from paytrans inp_amt");
			 
			 st.executeUpdate("update paytran_increament_diff  set net_amt= abs(cal_amt - inp_amt),adj_amt=abs(cal_amt - inp_amt)");
			 			//	System.out.println("paytran_increament_diff is updated with set net_amt=(cal_amt - inp_amt)");
			 
			rs1=st.executeQuery(" select distinct(TRNDT)from paytran_increament_diff ");
						//	System.out.println("distinct(TRNDT)...");
			while(rs1.next()) 
			{
				Statement st1=con.createStatement();
				String fdate=rs1.getString("TRNDT");
				//System.out.println("distinct(TRNDT)..."+fdate);
				//String date=ReportDAO.EOM(fdate);
				String [] month=fdate.split("-");
				int days=Integer.parseInt(month[2]);
				String startdate=month[0]+ "-" +month[1]+"-01";
				String enddate=month[0]+ "-" +month[1]+"-"+month[2];
				
			
				st1.executeUpdate("with absent_days as (select SUM(inp_amt) adays, empno ,max(trndt) trndt "+
																	"from paytran_increament_diff "+
    																"where TRNCD in(301,302) "+ 
    																"and TRNDT between '"+startdate+ "' and '"+enddate+ "' "+
    																"group by empno,TRNDT  ) "+
														"update p set adj_amt = ( case when a.adays >= 0 then round( ( net_amt - ( net_amt / "+days+" * a.adays) ),0) "+
														"when a.adays = 0 then ( net_amt ) end  ) "+ 
																			"from  paytran_increament_diff p , absent_days a "+                           
																			"where a.EMPNO = p.EMPNO "+
																			"and p.TRNDT between '"+startdate+ "' and '"+enddate+ "'  ");
			
			
						flag="done";
			
			}
			
 
			
			st3.executeUpdate(" delete from paytran where trncd =142 and empno  in (select distinct(empno)from paytran_increament_diff where trndt between '"+ReportDAO.BOM(FromDate)+"' and '"+ReportDAO.BOM(ToDate)+"' )");
			
			
			rs2=st2.executeQuery(" select distinct(empno)from paytran_increament_diff   where trndt between '"+ReportDAO.BOM(FromDate)+"' and '"+ReportDAO.BOM(ToDate)+"' ");
			rs1=st.executeQuery(" select distinct(TRNDT)from paytran_increament_diff ");
			
 
			
while(rs2.next())
{
	st.executeUpdate("INSERT INTO  paytran ([TRNDT],[EMPNO],[TRNCD],[SRNO],[INP_AMT],[CAL_AMT],[ADJ_AMT],[ARR_AMT]  "+
					         ",[NET_AMT],[CF_SW] ,[USRCODE] ,[UPDDT] ,[STATUS])  "+
					         " VALUES ( '"+ReportDAO.EOM(ToDate)+"' ,"+rs2.getInt("EMPNO")+",142 ,0 ,0,0,0,0,0,0,'','"+ReportDAO.EOM(ToDate)+"','N') " );
			
			 
			
}

 if(itype.equalsIgnoreCase("Increment"))
 {

			st.executeUpdate("with sarr as( "+
					"select empno, SUM(adj_amt) arramt from paytran_increament_diff "+
					"where TRNCD in( 101,102,103,138) "+
					"group by EMPNO) "+
					"update p set INP_AMT = abs(sarr.arramt) "+ 
					"from sarr , paytran p "+
					"where p.EMPNO = sarr.empno and p.TRNCD = 142 ");
			


 }
 
 else
 {

	 st.executeUpdate("with sarr as( "+
				"select empno, SUM(adj_amt) arramt from paytran_increament_diff "+
				"where TRNCD in( 138) "+
				"group by EMPNO) "+
				"update p set INP_AMT = abs(sarr.arramt) "+ 
				"from sarr , paytran p "+
				"where p.EMPNO = sarr.empno and p.TRNCD = 142 ");
		


 }

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
///

	public static ArrayList<TranBean> getNagtiveSalaryList1(String dt,String empLst)
	{
		ArrayList<TranBean> nslist = new ArrayList<TranBean>();
		Connection con =ConnectionManager.getConnection();
		Statement st;
		ResultSet rs=null;
		TranBean trbn;
		try
		{
			
			String emplist1[] =new String[1];
			if(empLst.contains(","))
			{
					 emplist1=empLst.split(",");
			}
			else
			{
				 emplist1[0]=empLst;
			}
				for(int c=0;c<emplist1.length;c++)
				{
					String	empList=emplist1[c];	
					System.out.println("999 neg checking for empno..."+empList);
					st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					rs=st.executeQuery("select P.EMPNO,E.EMPCODE,E.FNAME,E.MNAME,E.LNAME "+ 
												"from EMPMAST E,PAYTRAN P " + 
												"where P.EMPNO=E.empno and P.EMPNO="+empList+" and "+
												"trncd=999 and NET_AMT<0 and TRNDT <='"+dt+"' ");
					if(rs.next())
					{
						trbn= new TranBean();
						trbn.setEmpcode(rs.getString("EMPCODE"));
						trbn.setEMPNO(rs.getInt("EMPNO"));
						trbn.setEMPNAME(rs.getString("FNAME")+" "+rs.getString("LNAME"));
						nslist.add(trbn);
					}
					else
					{
						st.executeUpdate("IF EXISTS(select * from negative_paytran where empno="+empList+" and trndt='"+dt+"' ) " +
												"delete from  negative_paytran where empno="+empList+" and trndt='"+dt+"'  ") ;
						System.out.println("delete from  neg_paytran bcz it goes positive  for empno..."+empList);
					}
				}
				rs.close();
				con.close();
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//System.out.println("trnhandlist neg emp sizeeeeeeeee......"+nslist.size());
		return nslist;
	}
	
	public void addtoEmptran(TranBean TB,int desig,String LKP_TRNCD,String ORDERNO)
	{
		ResultSet rs=null;
		ResultSet rs1 = null;
		Connection con= ConnectionManager.getConnection();
		try
		{
			Statement st1 = con.createStatement();
			Statement st11 = con.createStatement();
			int srno=0;
			String PRJCODE = "";
			String Query ="";
			String Query1 ="";
		    Query1 = ""
					+ "select ps.Project_code from Project_Sites ps ,EMPTRAN e "
					+ "where ps.SITE_ID = e.PRJ_SRNO "
					+ "and e.EMPNO="+TB.getEMPNO()+" "
					+ "and  e.SRNO = (select MAX(srno) from emptran b where b.EMPNO = e.EMPNO )";
		    		rs1= st1.executeQuery(Query1);
		    		while(rs1.next())
					{
		    			PRJCODE = rs1.getString("Project_code");
					}
					Query	="SELECT * from EMPTRAN WHERE empno ="+TB.getEMPNO()+" and SRNO =(select max(srno) from emptran where EMPNO="+TB.getEMPNO()+")";
					System.out.println("rs::  "+Query);
				    rs= st1.executeQuery(Query);
				    rs.next();
				    srno=rs.getInt("srno");
					String SQL ="";
					if(srno ==1)
					{
						SQL =     "update emptran set desig="+desig+",grade="+desig+",EFFDATE='"+TB.getTRNDT()+"',ORDER_DT='"+TB.getTRNDT()+"',ORDER_NO='"+ORDERNO+"'  where empno="+TB.getEMPNO()+" and "+
								  "srno="+rs.getString("srno")+"";
					}
					else{
						 		 srno=srno+1;
						SQL =    "INSERT INTO EMPTRAN (EMPNO,EFFDATE,TRNCD,SRNO,ORDER_NO,ORDER_DT,PRJ_SRNO,ACNO,BRANCH,GRADE,DESIG,DEPT,PRJ_CODE,BANK_NAME,CREATED_DATE)" 
								 +" values("+TB.getEMPNO()+",'"+TB.getTRNDT()+"','"+LKP_TRNCD+"',"+srno+",'"+ORDERNO+"','"+TB.getTRNDT()+"',"+rs.getString("PRJ_SRNO")+","+rs.getString("ACNO")+","+rs.getString("PRJ_SRNO")+","+desig+","+desig+","+rs.getString("DEPT")+",'"+PRJCODE+"',1,'"+ReportDAO.getSysDate()+"')";
					}
								 System.out.println("10: "+SQL);
					             st11.executeUpdate(SQL);
					rs1.close();             
					rs.close();
					con.close();
				}
		catch(SQLException e)
		{
			try
			{
				con.close();
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
			
	}
	
	
	
}