package payroll.DAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import payroll.Model.DeductBean;
import payroll.Core.ReportDAO;
import payroll.Core.Utility;
public class DeductHandler {
	
	
	public boolean addDeduction(DeductBean DB,String uid)
	{
		boolean result=false;
		String TrDate ="",TrDate1="";
		int TRNCODE;
		Connection con=ConnectionManager.getConnection();
		try
		{
					
			Statement st0 = con.createStatement();
			Statement st = con.createStatement();
			
				System.out.println("i am in other Trncd");
			String sql="INSERT INTO DEDMAST" +
					//"([EMPNO],[TRNCD],[SRNO],[AMOUNT],[SUBSYS_CD],[AC_NO],[BODSANCNO],[SANC_DATE],[SANC_AMT],[INT_RATE],[REPAY_START],[END_DATE],[CUMUYN],[ACTYN],[TOTAL_INSTLMNTS],[CREATED_BY],[CREATED_DATE])" +
					"([EMPNO],[TRNCD],[SRNO],[AMOUNT],[SUBSYS_CD],[AC_NO],[BODSANCNO],[SANC_DATE],[SANC_AMT],[INT_RATE],[REPAY_START],[END_DATE],[CUMUYN],[ACTYN],[TOTAL_INSTLMNTS],[CREATED_BY],[CREATED_DATE], [PRJ_SRNO])" +
					" VALUES("
						+DB.getEMPNO()+","
						+DB.getTRNCD()+","
						+DB.getSRNO()+","
						+DB.getAMOUNT()+","
						+"'"+DB.getSUBSYS_CD()+"',"
						+DB.getAC_NO()+","
						+"'"+DB.getBODSANCNO()+"',"
						+"'"+DB.getSANC_DATE()+"',"
						+DB.getSANC_AMT()+","
						+DB.getINT_RATE()+","
						+"'"+DB.getREPAY_START()+"',"
						+"'"+DB.getEND_DATE()+"',"
						+"'"+DB.getCUMUYN()+"',"
						+"'"+DB.getACTYN()+"',"
						+DB.getNo_Of_Installment()
					//	+","+uid+",'"+ReportDAO.getServerDate()+"')";
						+","+uid+",'"+ReportDAO.getServerDate()+"',"+DB.getPrj_srno()+")";
			System.out.println("insert to DEDMAST   "+sql);
			st.execute(sql);
			
			
//  GET Paytran record..
			
			Connection Cn = ConnectionManager.getConnection();
			Statement st2 = Cn.createStatement();
			String TrnDate = "SELECT distinct TRNDT FROM PAYTRAN WHERE TRNCD=101 AND EMPNO="+ DB.getEMPNO();
			ResultSet rs1 = st2.executeQuery(TrnDate);
			System.out.println("TRNDT  "+TrnDate);
			
			if (rs1.next()) {
				
				TrDate = rs1.getString("TRNDT");
				}
			
			Statement st1 = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String TrnStr = "SELECT * FROM PAYTRAN WHERE EMPNO="+ DB.getEMPNO()+" And TRNCD = "+ DB.getTRNCD()+"And TRNDT = '"+TrDate+"'";
			String empno,trndt,in_amt,usercode,upddt,arr_amt,adj_amt,cal_amt,net_amt,trncd;
			float Add_Input= 0.00f,input_amt = 0.00f,installment_amt = 0.00f;
			System.out.println("TrnStr   "+TrnStr);
			ResultSet rset = st1.executeQuery(TrnStr);
			
			if (!rset.next()) 
			{
				
	// For Insert ino Paytran...  
				
				Statement st3 = con.createStatement();
				
				String sql1=("INSERT INTO PAYTRAN (TRNDT, EMPNO, TRNCD, INP_AMT, USRCODE, UPDDT, ARR_AMT, ADJ_AMT, CAL_AMT,NET_AMT, SRNO, CF_SW,STATUS) values("
						+ "'"
						+ TrDate
						+ "',"
						+ DB.getEMPNO()
						+ ","
						+ DB.getTRNCD()
						+ ","
						+ DB.getAMOUNT()
						+ ",'"
						+ uid
						+ "','" + ReportDAO.getServerDate() + "',0.0,0.0,0.0,0.0,0,'*','A')");
				
				System.out.println(sql1);
				System.out.println("i am in Insert into Paytran");
				st3.execute(sql1);
			}
			rset.beforeFirst();
			
		while (rset.next())
			{		
				System.out.println("i am in update into Paytran");
				empno= rset.getString("EMPNO");
				trndt= rset.getString("TRNDT");
				in_amt= rset.getString("INP_AMT");
				usercode= rset.getString("USRCODE");
				upddt= rset.getString("UPDDT");
				arr_amt=rset.getString("ARR_AMT");
				adj_amt= rset.getString("ADJ_AMT");
				cal_amt= rset.getString("CAL_AMT");
				net_amt= rset.getString("NET_AMT");
				trncd= rset.getString("TRNCD");
				
				//sanction_amt = DB.getSANC_AMT();
				installment_amt = DB.getAMOUNT();
				input_amt = rset.getFloat("INP_AMT");
				Add_Input =(input_amt + installment_amt);
				
	// update paytran
				Statement st4 = con.createStatement();
				String sqlUpdate=("UPDATE PAYTRAN SET CAL_AMT=0,SRNO=0,USRCODE="+uid 
						+ ",ARR_AMT=0, ADJ_AMT=0, NET_AMT=0,INP_AMT="+Add_Input 
						+ ",TRNDT='" + trndt + "',CF_SW='*',UPDDT='" + ReportDAO.getServerDate()
						+ "',STATUS='A' WHERE TRNCD="+trncd+" AND EMPNO=" + empno);
				
				System.out.println(sqlUpdate);
				System.out.println("i am in Update into Paytran..");
				st4.execute(sqlUpdate);
			}
		
			
			
			result=true;
			con.close();
	}
		catch(SQLException e)
		{
			e.printStackTrace();
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
	
	public boolean updateDeduction(int empno, int trncd, int srno, DeductBean DB,String uid)
	{
		boolean result=false;
		Connection con=ConnectionManager.getConnection();
		String TrDate ="";
		String AMOUNT="";
		String emp_no="",trndt="",in_amt="",usercode="",upddt="",arr_amt,adj_amt,cal_amt,net_amt,Trncd="";
		float Add_Input= 0.00f,input_amt = 0.00f,sanction_amt = 0.00f,Sub_InpAmt=0.00f;
		try
		{
			con.setAutoCommit(false);
			Statement st = con.createStatement();
			String sql="";
			if((trncd==205) && (DB.getACTYN().equalsIgnoreCase("N")))
			{
				System.out.println("if akshay ");
				 sql="begin   UPDATE DEDMAST SET "
						+"AMOUNT="+DB.getAMOUNT()+", "
						+"SUBSYS_CD='"+DB.getSUBSYS_CD()+"', "
						+"AC_NO="+DB.getAC_NO()+", "
						+"BODSANCNO='"+DB.getBODSANCNO()+"', "
						+"SANC_DATE='"+DB.getSANC_DATE()+"', "
						+"SANC_AMT="+DB.getSANC_AMT()+", "
						+"TOTAL_INSTLMNTS="+DB.getNo_Of_Installment()+", "
						+"REPAY_START='"+DB.getREPAY_START()+"', "
						+"END_DATE=CONVERT(DATE,GETDATE()), "
						+"CUMUYN='"+DB.getCUMUYN()+"', "
						+"ACTYN='"+DB.getACTYN()+"'," +
						"UPDATED_BY="+uid+"," +
						//"Updated_Date=GETDATE()"
						"Updated_Date=GETDATE(), PRJ_SRNO="+DB.getPrj_srno()
						+"WHERE EMPNO="+empno+" AND TRNCD="+trncd+" AND SRNO="+srno+" "+
						"  END";
			}
			else
			{
				System.out.println("ellseakshay ");
			 sql="begin   UPDATE DEDMAST SET "
						+"AMOUNT="+DB.getAMOUNT()+", "
						+"SUBSYS_CD='"+DB.getSUBSYS_CD()+"', "
						+"AC_NO="+DB.getAC_NO()+", "
						+"BODSANCNO='"+DB.getBODSANCNO()+"', "
						+"SANC_DATE='"+DB.getSANC_DATE()+"', "
						+"SANC_AMT="+DB.getSANC_AMT()+", "
						+"TOTAL_INSTLMNTS="+DB.getNo_Of_Installment()+", "
						+"REPAY_START='"+DB.getREPAY_START()+"', "
						+"END_DATE='"+DB.getEND_DATE()+"', "
						+"CUMUYN='"+DB.getCUMUYN()+"', "
						+"ACTYN='"+DB.getACTYN()+"'," +
						"UPDATED_BY="+uid+"," +
						//"Updated_Date=GETDATE()"
						"Updated_Date=GETDATE(), PRJ_SRNO="+DB.getPrj_srno()
						+"WHERE EMPNO="+empno+" AND TRNCD="+trncd+" AND SRNO="+srno+" "+
						"  END";
			}
			System.out.println("----"+sql);
			st.executeUpdate(sql);
			
			
//  For Paytran Month Date..			
			Statement st2 = con.createStatement();
			String TrnDate = "SELECT distinct TRNDT FROM PAYTRAN WHERE TRNCD=101 AND EMPNO="+ empno;
			ResultSet rs1 = st2.executeQuery(TrnDate);
			System.out.println("TRNDT  "+TrnDate);
			
			if (rs1.next()) {
				
				TrDate = rs1.getString("TRNDT");
				}
			
			
// Get 	PAYTRAN Records..		
			Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String TrnStr = "SELECT * FROM PAYTRAN WHERE EMPNO="+ empno+" And TRNCD = "+trncd +" And TRNDT = '"+TrDate+"'";
			
			ResultSet rset = st1.executeQuery(TrnStr);
			System.out.println("TrnStr   "+TrnStr);
			
			if(rset.next())
			{		
				System.out.println("i am in update into Paytran");
				emp_no= rset.getString("EMPNO");
				trndt= rset.getString("TRNDT");
				in_amt= rset.getString("INP_AMT");
				usercode= rset.getString("USRCODE");
				upddt= rset.getString("UPDDT");
				arr_amt=rset.getString("ARR_AMT");
				adj_amt= rset.getString("ADJ_AMT");
				cal_amt= rset.getString("CAL_AMT");
				net_amt= rset.getString("NET_AMT");
				Trncd= rset.getString("TRNCD");
			}
			
			
    //	Get sum of sanction amount from dedmast.. 		
			Statement st7 = con.createStatement();
			//String SanctionAmt = "select SUM(AMOUNT) as AMOUNT from DEDMAST where  ACTYN ='Y' and EMPNO="+ empno+" And TRNCD = "+trncd;
			String SanctionAmt = "select SUM(AMOUNT) as AMOUNT from DEDMAST where  ACTYN ='Y' and EMPNO="+ empno+" And TRNCD = "+trncd+" AND REPAY_START<='"+trndt+"'";
			System.out.println("SanctionAmt Query : "+SanctionAmt);
			ResultSet rs7 = st7.executeQuery(SanctionAmt);
			System.out.println("SanctionAmt  "+SanctionAmt);
			
			if (rs7.next()) {
				
			//	SANCTION_AMT = rs7.getString("AMOUNT");
				AMOUNT = rs7.getString("AMOUNT");
				System.out.println("AMOUNT  "+AMOUNT);
				}
			
				Statement st4 = con.createStatement();
				String sqlUpdate=("UPDATE PAYTRAN SET CAL_AMT=0,SRNO=0,USRCODE="+uid 
						+ ",ARR_AMT=0, ADJ_AMT=0, NET_AMT=0,INP_AMT="+AMOUNT 
						+ ",TRNDT='" + trndt + "',CF_SW='*',UPDDT='" + ReportDAO.getServerDate()
						+ "',STATUS='A' WHERE TRNCD="+trncd+" AND EMPNO=" + empno);
				
				System.out.println(sqlUpdate);
				System.out.println("i am in Update into Paytran for active Yes");
				st4.execute(sqlUpdate);
	
			
			con.commit();
			result=true;
			con.close();
			
		} 
		catch(SQLException e)
		{
			e.printStackTrace();
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
	
	public ArrayList<DeductBean> getDeduction(int empno)
	{
		ArrayList<DeductBean> result = new ArrayList<DeductBean>();
		Connection con=ConnectionManager.getConnection();
		try
		{
			Statement st = con.createStatement();
			System.out.println("SELECT * FROM DEDMAST WHERE EMPNO="+empno+" ORDER BY TRNCD , SRNO DESC");
			ResultSet rs = st.executeQuery("SELECT * FROM DEDMAST WHERE EMPNO="+empno+" ORDER BY TRNCD , SRNO DESC");
			
			while(rs.next())
			{
				System.out.println("into loop");
				DeductBean	DB = new DeductBean();
				DB.setEMPNO(rs.getString("EMPNO")==null?0:rs.getInt("EMPNO"));
				DB.setTRNCD(rs.getString("TRNCD")==null?0:rs.getInt("TRNCD"));
				DB.setSRNO(rs.getString("SRNO")==null?0:rs.getInt("SRNO"));
				DB.setAMOUNT(rs.getString("AMOUNT")==null?0:rs.getFloat("AMOUNT"));
				DB.setSUBSYS_CD(rs.getString("SUBSYS_CD")==null?"":rs.getString("SUBSYS_CD"));
				DB.setAC_NO(rs.getString("AC_NO")==null?0:rs.getLong("AC_NO"));
				DB.setBODSANCNO(rs.getString("BODSANCNO")==null?"":rs.getString("BODSANCNO"));
				DB.setSANC_DATE(rs.getDate("SANC_DATE")==null?"":Utility.dateFormat(rs.getDate("SANC_DATE")));
				DB.setSANC_AMT(rs.getString("SANC_AMT")==null?0:rs.getInt("SANC_AMT"));
				DB.setINT_RATE(rs.getString("INT_RATE")==null?0:rs.getFloat("INT_RATE"));
				DB.setActual_amt_paid(rs.getString("ACTUAL_TOTAL_AMT")==null?0:rs.getFloat("ACTUAL_TOTAL_AMT"));
				DB.setREPAY_START(rs.getDate("REPAY_START")==null?"":Utility.dateFormat(rs.getDate("REPAY_START")));
				DB.setEND_DATE(rs.getDate("END_DATE")==null?"":Utility.dateFormat(rs.getDate("END_DATE")));
				DB.setCUMUYN(rs.getString("CUMUYN")==null?"":rs.getString("CUMUYN"));
				DB.setACTYN(rs.getString("ACTYN")==null?"":rs.getString("ACTYN"));
				DB.setNo_Of_Installment(rs.getString("TOTAL_INSTLMNTS")==null?0:rs.getInt("TOTAL_INSTLMNTS"));
				result.add(DB);
				
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			
			try 
			{
				con.close();
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		System.out.println("ARRAYLIST size in handlr===="+result.size());
		return result;
	}
	
	public int getMaxSrno(int empno, int trncd)
	{
		int max=0;
		Connection con=ConnectionManager.getConnection();
		try
		{
			Statement st=con.createStatement();
			ResultSet rs= st.executeQuery("SELECT MAX(SRNO) FROM DEDMAST WHERE EMPNO ="+empno+" AND TRNCD="+trncd);
			if(rs.next())
			{
				max= rs.getInt(1);
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
		}
		return max;
	}
	public boolean delDedction(int empno, int trncd, int srno)
	{
		boolean flag = false;
		Connection con=ConnectionManager.getConnection();
		try
		{
			Statement st=con.createStatement();
			st.execute("DELETE FROM DEDMAST WHERE EMPNO="+empno+" AND TRNCD="+trncd+" AND SRNO="+srno);
			flag=true;
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
		return flag;
	}
	public DeductBean getSingleDed(int empno, int trncd, int srno)
	{
		DeductBean DB=new DeductBean();
		Connection con=ConnectionManager.getConnection();
		try
		{
			Statement st=con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM DEDMAST WHERE EMPNO="+empno+" AND TRNCD="+trncd+" AND SRNO="+srno);
			while(rs.next())
			{
				DB.setEMPNO(rs.getString("EMPNO")==null?0:rs.getInt("EMPNO"));
				DB.setTRNCD(rs.getString("TRNCD")==null?0:rs.getInt("TRNCD"));
				DB.setSRNO(rs.getString("SRNO")==null?0:rs.getInt("SRNO"));
				DB.setAMOUNT(rs.getString("AMOUNT")==null?0:rs.getInt("AMOUNT"));
				DB.setSUBSYS_CD(rs.getString("SUBSYS_CD")==null?"":rs.getString("SUBSYS_CD"));
				DB.setAC_NO(rs.getString("AC_NO")==null?0:rs.getLong("AC_NO"));
				DB.setBODSANCNO(rs.getString("BODSANCNO")==null?"":rs.getString("BODSANCNO"));
				DB.setSANC_DATE(rs.getDate("SANC_DATE")==null?"":Utility.dateFormat(rs.getDate("SANC_DATE")));
				DB.setSANC_AMT(rs.getString("SANC_AMT")==null?0:rs.getInt("SANC_AMT"));
				DB.setINT_RATE(rs.getString("INT_RATE")==null?0:rs.getFloat("INT_RATE"));
				DB.setREPAY_START(rs.getDate("REPAY_START")==null?"":Utility.dateFormat(rs.getDate("REPAY_START")));
				DB.setEND_DATE(rs.getDate("END_DATE")==null?"":Utility.dateFormat(rs.getDate("END_DATE")));
				DB.setCUMUYN(rs.getString("CUMUYN")==null?"":rs.getString("CUMUYN"));
				DB.setACTYN(rs.getString("ACTYN")==null?"":rs.getString("ACTYN"));
				DB.setNo_Of_Installment(rs.getString("TOTAL_INSTLMNTS")==null?0:rs.getInt("TOTAL_INSTLMNTS"));
				
				DB.setPrj_srno(rs.getString("PRJ_SRNO")==null?0:rs.getInt("PRJ_SRNO"));
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			try 
			{
				con.close();
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		return DB;
	}
	
	
	
	public DeductBean getDeductionForCalculation(int empno, int trncd, String date)
	{
		DeductBean DB=new DeductBean();
		Connection con=ConnectionManager.getConnection();
		try
		{
			Statement st=con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM DEDMAST WHERE EMPNO="+empno+" AND TRNCD="+trncd+" AND '"+date+"' between REPAY_START and END_DATE and ACTYN='Y' ");
			while(rs.next())
			{
				DB.setEMPNO(rs.getString("EMPNO")==null?0:rs.getInt("EMPNO"));
				DB.setTRNCD(rs.getString("TRNCD")==null?0:rs.getInt("TRNCD"));
				DB.setSRNO(rs.getString("SRNO")==null?0:rs.getInt("SRNO"));
				DB.setAMOUNT(rs.getString("AMOUNT")==null?0:rs.getInt("AMOUNT"));
				DB.setSUBSYS_CD(rs.getString("SUBSYS_CD")==null?"":rs.getString("SUBSYS_CD"));
				DB.setAC_NO(rs.getString("AC_NO")==null?0:rs.getLong("AC_NO"));
				DB.setBODSANCNO(rs.getString("BODSANCNO")==null?"":rs.getString("BODSANCNO"));
				DB.setSANC_DATE(rs.getDate("SANC_DATE")==null?"":Utility.dateFormat(rs.getDate("SANC_DATE")));
				DB.setSANC_AMT(rs.getString("SANC_AMT")==null?0:rs.getInt("SANC_AMT"));
				DB.setINT_RATE(rs.getString("INT_RATE")==null?0:rs.getFloat("INT_RATE"));
				DB.setREPAY_START(rs.getDate("REPAY_START")==null?"":Utility.dateFormat(rs.getDate("REPAY_START")));
				DB.setEND_DATE(rs.getDate("END_DATE")==null?"":Utility.dateFormat(rs.getDate("END_DATE")));
				DB.setCUMUYN(rs.getString("CUMUYN")==null?"":rs.getString("CUMUYN"));
				DB.setACTYN(rs.getString("ACTYN")==null?"":rs.getString("ACTYN"));
				DB.setNo_Of_Installment(rs.getString("TOTAL_INSTLMNTS")==null?0:rs.getInt("TOTAL_INSTLMNTS"));
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			try 
			{
				con.close();
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		return DB;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
