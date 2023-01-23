package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import payroll.Core.ReportDAO;
import payroll.Model.OnAmtBean;

public class OnAmtHandler {
	//-------- Methd for Inserting new record to ONAMT Table -----------------
	public boolean addOnAmt(OnAmtBean OAB)
	{
		boolean result=false;
		Connection con=null;
		try
		{
			con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			String str="INSERT INTO ONAMT VALUES("
						+OAB.getEMP_CAT()+","
						+OAB.getTRNCD()+","
						+OAB.getSRNO()+","
						+OAB.getONAMTCD()+","
						+"'"+OAB.getAMT_TYPE()+"')";
			st.execute(str);
			con.close();
			result=true;
		}
		catch (SQLException e)
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
	
	//-------- Method for Inserting Multiple Employee Details in ONAMT Table -----------------
	public boolean addOnAmt2(OnAmtBean OAB, String list)
	{
		boolean result=false;
		Connection con=null;
		String str="";
		try
		{  
			con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			
				String []elist = list.split(",");
				for(String empno : elist){
					str="INSERT INTO ONAMT VALUES("
							+Integer.parseInt(empno)+","
							+OAB.getTRNCD()+","
							+OAB.getSRNO()+","
							+OAB.getONAMTCD()+","
							+"'"+OAB.getAMT_TYPE()+"')";
					st.execute(str);
				}
			st.close();
			con.close();
			result=true;
		}
		catch (SQLException e)
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
	//-------- Method For Retrieving Multiple records from ONAMT Table ---------
	
	public ArrayList<OnAmtBean> getOnAmtList(int empCat, int trCode)
	{
		ArrayList<OnAmtBean> result =new ArrayList<OnAmtBean>();
		
		Connection con=null;
		try
		{
			con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM ONAMT WHERE EMP_CAT="+empCat+" AND TRNCD="+trCode+" ORDER BY ONAMTCD");
			while(rs.next())
			{
				OnAmtBean OAB = new OnAmtBean();
				OAB.setEMP_CAT(rs.getString("EMP_CAT")!=null?rs.getInt("EMP_CAT"):0);
				OAB.setTRNCD(rs.getString("TRNCD")!=null?rs.getInt("TRNCD"):0);
				OAB.setSRNO(rs.getString("SRNO")!=null?rs.getInt("SRNO"):0);
				OAB.setONAMTCD(rs.getString("ONAMTCD")!=null?rs.getInt("ONAMTCD"):0);
				OAB.setAMT_TYPE(rs.getString("AMT_TYPE")!=null?rs.getString("AMT_TYPE"):"");
				result.add(OAB);
			}
			con.close();
		}catch (SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}

//-------- Method For Retrieving Single record from ONAMT Table ---------
	
	public OnAmtBean getOnAmt(int empCat, int trCode, int srno)
	{
		Connection con=null;
		OnAmtBean OAB = new OnAmtBean();
		try
		{
			con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM ONAMT WHERE EMP_CAT="+empCat+" AND TRNCD="+trCode+" AND SRNO="+srno+"");
			while(rs.next())
			{
				OAB.setEMP_CAT(rs.getString("EMP_CAT")!=null?rs.getInt("EMP_CAT"):0);
				OAB.setTRNCD(rs.getString("TRNCD")!=null?rs.getInt("TRNCD"):0);
				OAB.setSRNO(rs.getString("SRNO")!=null?rs.getInt("SRNO"):0);
				OAB.setONAMTCD(rs.getString("ONAMTCD")!=null?rs.getInt("ONAMTCD"):0);
				OAB.setAMT_TYPE(rs.getString("AMT_TYPE")!=null?rs.getString("AMT_TYPE"):"");
			}
			con.close();
		
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return OAB;
	}
	
	public boolean deleteOnAmt(int E, int T,int S)
	{
		Connection con=null;
		boolean result=false;
		try
		{
			con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			String sql = "";
			if(S == -1)
			{
				sql = "DELETE FROM ONAMT WHERE EMP_CAT="+E+" AND TRNCD="+T;
			}
			else
			{
				sql = "DELETE FROM ONAMT WHERE EMP_CAT="+E+" AND TRNCD="+T+" AND SRNO="+S+"";
			}
			st.execute(sql);
			result=true;
			con.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<Integer> getOnAmtValues(int empno,int trncd)
	{
		ArrayList<Integer> result = new ArrayList<Integer>();
		try
		{
			Connection con = ConnectionManager.getConnection();
			ArrayList<OnAmtBean> depend = getOnAmtList(0, trncd);
			Statement st = con.createStatement();
			ResultSet rs = null;
			Statement st1 = con.createStatement();
			ResultSet rs1 = null;
			for(OnAmtBean OAB : depend)
			{
				rs = st.executeQuery("SELECT * FROM PAYTRAN WHERE EMPNO="+empno+" AND TRNCD="+OAB.getONAMTCD());
				if(rs.next())
				{
					result.add(rs.getString("INP_AMT")==null?rs.getInt("CAL_AMT"):rs.getInt("INP_AMT"));
				}
				else
				{
					rs1=st1.executeQuery("SELECT TOP 1 * FROM YTDTRAN WHERE EMPNO="+empno+" AND TRNCD="+OAB.getONAMTCD()+" ORDER BY TRNDT DESC");
					if(rs1.next())
					{
						result.add(rs1.getString("INP_AMT")==null?rs1.getInt("CAL_AMT"):rs1.getInt("INP_AMT"));
					}
					else
					{
						result.add(0);
					}
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
	public static void ctc_change_OnAmt(int empno){
		String sql_insert = "INSERT INTO CTC_CHANGE_ONAMT (EMP_CAT,TRNCD,SRNO,ONAMTCD,AMT_TYPE,CHANGE_DATE) " +
					 "SELECT EMP_CAT,TRNCD,SRNO,ONAMTCD,AMT_TYPE,'"+ReportDAO.getSysDate()+"' " +
					 "FROM ONAMT where EMP_CAT = "+empno;
		String sql_delete = "delete from ONAMT where EMP_CAT = "+empno;
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
}
