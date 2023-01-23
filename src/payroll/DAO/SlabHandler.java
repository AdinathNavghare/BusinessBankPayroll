package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import payroll.Core.ReportDAO;
import payroll.Core.Utility;

import payroll.Model.SlabBean;

public class SlabHandler {

	public boolean addSlab(SlabBean SB)
	{
		boolean result=false;
		Connection con=null;
		try
		{
			con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			String sql="INSERT INTO SLAB VALUES("
						+"'"+SB.getEFFDATE()+"',"
						+SB.getEMP_CAT()+","
						+SB.getTRNCD()+","
						+SB.getSRNO()+","
						+SB.getFRMAMT()+","
						+SB.getTOAMT()+","
						+SB.getPER()+","
						+SB.getMINAMT()+","
						+SB.getMAXAMT()+","
						+SB.getFIXAMT()+","
						+SB.getON_AMT_CD()+""
						+")";
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
	
	public ArrayList<SlabBean> getSlabs(int Ecat, int trncd)
	{
		ArrayList<SlabBean> Slist= new ArrayList<SlabBean>();
		Connection con=ConnectionManager.getConnection();
		try
		{
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM SLAB WHERE EMP_CAT="+Ecat+" AND TRNCD="+trncd+" ORDER BY EFFDATE DESC");
			while(rs.next())
			{
				SlabBean slab=new SlabBean();
				slab.setEFFDATE(rs.getString("EFFDATE")!=null?Utility.dateFormat(rs.getDate("EFFDATE")):"");
				slab.setEMP_CAT(rs.getString("EMP_CAT")!=null?rs.getInt("EMP_CAT"):0);
				slab.setTRNCD(rs.getString("TRNCD")!=null?rs.getInt("TRNCD"):0);
				slab.setSRNO(rs.getString("SRNO")!=null?rs.getInt("SRNO"):0);
				slab.setFRMAMT(rs.getString("FRMAMT")!=null?rs.getInt("FRMAMT"):0);
				slab.setTOAMT(rs.getString("TOAMT")!=null?rs.getInt("TOAMT"):0);
				slab.setPER(rs.getString("PER")!=null?rs.getFloat("PER"):0);
				slab.setMINAMT(rs.getString("MINAMT")!=null?rs.getInt("MINAMT"):0);
				slab.setMAXAMT(rs.getString("MAXAMT")!=null?rs.getInt("MAXAMT"):0);
				slab.setFIXAMT(rs.getString("FIXAMT")!=null?rs.getInt("FIXAMT"):0);
				slab.setON_AMT_CD(rs.getString("ON_AMT_CD")!=null?rs.getInt("ON_AMT_CD"):0);
				Slist.add(slab);
			}
			con.close();
		}
		catch (Exception e)
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
		return Slist;
	}
	//----------- Method to End the Slab --------------
	
	public boolean endSlab(int ecat, int trncd, int srno)
	{
		boolean result=false;
		Connection con = ConnectionManager.getConnection();
		try
		{			
			Statement st = con.createStatement();
			ResultSet rs= st.executeQuery("select GETDATE()");
			String date="";
			while(rs.next())
			{
				date = Utility.dateFormat(rs.getDate(1));
			}
			String sql="UPDATE SLAB SET EFFDATE='"+date+"' WHERE EMP_CAT="+ecat+" AND TRNCD="+trncd+" AND SRNO="+srno+"";
			st.executeUpdate(sql);
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
		

	public int checkSlabPresent(int ecat, int trncd)
	{
		int srno=-1;
		Connection con=ConnectionManager.getConnection();
		try
		{
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT SRNO FROM SLAB WHERE EMP_CAT="+ecat+" AND TRNCD="+trncd+" AND EFFDATE ='31-DEC-2099'");
			if(rs.next())
			{
				srno = rs.getInt(1);
			}
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return srno;
	}
	public static void ctc_change_Slab(int empno){
		String sql_insert = "INSERT INTO CTC_CHANGE_SLAB (EFFDATE,EMP_CAT,TRNCD,SRNO,FRMAMT,TOAMT,PER,MINAMT,MAXAMT,FIXAMT,ON_AMT_CD,CHANGE_DATE) " +
					 "SELECT EFFDATE,EMP_CAT,TRNCD,SRNO,FRMAMT,TOAMT,PER,MINAMT,MAXAMT,FIXAMT,ON_AMT_CD,'"+ReportDAO.getSysDate()+"' " +
					 "FROM SLAB where EMP_CAT = "+empno;
		String sql_delete = "delete from SLAB where EMP_CAT = "+empno;
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
