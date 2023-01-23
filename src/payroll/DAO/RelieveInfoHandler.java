package payroll.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import payroll.Core.ReportDAO;
import payroll.Model.RelieveInfoBean;

public class RelieveInfoHandler {

	public boolean  insertreliev(RelieveInfoBean relbn)
	{
		Connection conn;
		conn= ConnectionManager.getConnection();
		boolean flag= false; 
		try
		{
			conn= ConnectionManager.getConnection();
			Statement st = conn.createStatement();
			st.execute("insert into RELIEVEINFO(EMPNO,LEFT_DATE,RESGN_DATE,RESGN_ACCTD_DATE,REASON,NTC_PERIOD,TERMINATE,DEATH,LEFT_REASON) values('"+relbn.getEMPNO()+"','"+relbn.getLEFT_DATE()+"','"+relbn.getRESGN_DATE()+"','"+relbn.getRESGN_ACCTD_DATE()+"','"+relbn.getREASON()+"','"+relbn.getNTC_PERIOD()+"','"+relbn.getTERMINATE()+"','"+relbn.getDEATH()+"',"+relbn.getLEFT_REASON()+")");
			Statement stupdate = conn.createStatement();
			stupdate.executeUpdate("update EMPMAST SET DOL = '"+relbn.getLEFT_DATE()+"',STATUS = 'N', UMODDATE = (select GETDATE()) where EMPNO ='"+relbn.getEMPNO()+"'");
			//@niket..to delete next paytran record
			stupdate.executeUpdate("IF EXISTS(SELECT * FROM PAYTRAN WHERE "+
                                            " TRNDT > '"+ReportDAO.EOM(relbn.getLEFT_DATE())+"'   "+
                                            "  AND EMPNO='"+relbn.getEMPNO()+"' ) "+
                                            " DELETE FROM PAYTRAN WHERE EMPNO='"+relbn.getEMPNO()+"'  AND TRNDT > '"+ReportDAO.EOM(relbn.getLEFT_DATE())+"'   ");
			
			CallableStatement callableStatement = null;
			 
			/*String updateUsersTableOnLeft = "{call SPC_Users_Update(?)}";
	 
			try {
				
				callableStatement = conn.prepareCall(updateUsersTableOnLeft);
	 			callableStatement.setInt(1, relbn.getEMPNO());
				// execute SPC_Users_Update store procedure
				callableStatement.executeUpdate();
	 
				 
			} catch (SQLException e) {
	 				System.out.println(e.getMessage());
	 		} 
			finally {	 
				if (callableStatement != null) {
					callableStatement.close();
				}	 
				if (conn != null) {
					conn.close();
				}
	 		}*/
			flag = true;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}
	
	public RelieveInfoBean getRelievInfo(String EMPNO)
	{
		ResultSet rs=null;
		Statement st = null;
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
		RelieveInfoBean relbn = new RelieveInfoBean();;
		try
		{
			Connection con;
			con = ConnectionManager.getConnection();
			st = con.createStatement();
			rs=st.executeQuery("select * from RELIEVEINFO where EMPNO="+EMPNO);
			
			
			while(rs.next())
			{
				
				relbn.setEMPNO(rs.getInt("EMPNO"));
				relbn.setRESGN_DATE(rs.getString("RESGN_DATE")==null?"":sdf.format(rs.getDate("RESGN_DATE")));
				relbn.setRESGN_ACCTD_DATE(rs.getString("RESGN_ACCTD_DATE")==null?"":sdf.format(rs.getDate("RESGN_ACCTD_DATE")));
				relbn.setREASON(rs.getString("REASON")==null?"":rs.getString("REASON"));
				relbn.setNTC_PERIOD(rs.getString("NTC_PERIOD")==null?"":rs.getString("NTC_PERIOD"));
				relbn.setTERMINATE(rs.getString("TERMINATE")==null?"":rs.getString("TERMINATE"));
				relbn.setDEATH(rs.getString("DEATH")==null?"":rs.getString("DEATH"));
				relbn.setLEFT_DATE(rs.getString("LEFT_DATE")==null?"":sdf.format(rs.getDate("LEFT_DATE")));
				relbn.setLEFT_REASON(rs.getString("LEFT_REASON")==null?"":rs.getString("LEFT_REASON"));
				
			}
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return relbn;
	}
	
	public boolean  updateRelinfo(RelieveInfoBean relbn)
	{
		Connection conn;
		conn= ConnectionManager.getConnection();
		boolean flag= false; 
		try
		{
			conn= ConnectionManager.getConnection();
			Statement st = conn.createStatement();
			
			st.execute("update RELIEVEINFO set LEFT_DATE='"+relbn.getLEFT_DATE()+"'," +
					" RESGN_DATE='"+relbn.getRESGN_DATE()+"'," +
					" RESGN_ACCTD_DATE='"+relbn.getRESGN_ACCTD_DATE()+"'," +
					" REASON='"+relbn.getREASON()+"'," +
					" NTC_PERIOD='"+relbn.getNTC_PERIOD()+"'," +
					" TERMINATE='"+relbn.getTERMINATE()+"'," +
					" DEATH='"+relbn.getDEATH()+"'," +
					" LEFT_REASON="+relbn.getLEFT_REASON()+"" +
					" where EMPNO="+relbn.getEMPNO()+"");
			
			System.out.println("in relinfo method1:  `  update RELIEVEINFO set LEFT_DATE='"+relbn.getLEFT_DATE()+"'," +
					" RESGN_DATE='"+relbn.getRESGN_DATE()+"'," +
					" RESGN_ACCTD_DATE='"+relbn.getRESGN_ACCTD_DATE()+"'," +
					" REASON='"+relbn.getREASON()+"'," +
					" NTC_PERIOD='"+relbn.getNTC_PERIOD()+"'," +
					" TERMINATE='"+relbn.getTERMINATE()+"'," +
					" DEATH='"+relbn.getDEATH()+"'," +
					" LEFT_REASON="+relbn.getLEFT_REASON()+"" +
					" where EMPNO="+relbn.getEMPNO()+"");
			
			Statement stupdate = conn.createStatement();
			stupdate.executeUpdate("update EMPMAST SET DOL = '"+relbn.getLEFT_DATE()+"',STATUS = 'N', UMODDATE = (select GETDATE()) where EMPNO ='"+relbn.getEMPNO()+"'");
			//@niket... to delete next month paytran from emp if present
			
			stupdate.executeUpdate("IF EXISTS(SELECT * FROM PAYTRAN WHERE "+
                    " TRNDT > '"+ReportDAO.EOM(relbn.getLEFT_DATE())+"'   "+
                    "  AND EMPNO='"+relbn.getEMPNO()+"' ) "+
                    " DELETE FROM PAYTRAN WHERE EMPNO='"+relbn.getEMPNO()+"'  AND TRNDT > '"+ReportDAO.EOM(relbn.getLEFT_DATE())+"'   ");
			
			flag = true;
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}
}
