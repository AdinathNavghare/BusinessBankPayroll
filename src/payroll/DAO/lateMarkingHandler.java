
	
	
	package payroll.DAO;

	import java.sql.Connection;
	import java.sql.SQLException;
	import java.sql.Statement;

	import payroll.Core.ReportDAO;

	public class lateMarkingHandler {
		
		
		public boolean addDeduction(String date, String EMPNOLIST,String DeductionCode, String amount) throws SQLException {
			
			boolean flag = false;
			Connection con = ConnectionManager.getConnection();
			

			String Allemp = EMPNOLIST ;
			String[] employee = Allemp.split(",");
			
			for (int i=0;i<employee.length;i++)
			{
				
			String sql="if exists(select * from LateMarking where TRNCD="+DeductionCode+" and EMPNO="+ employee[i] +"  and  TRNDT between '"+ReportDAO.BOM(date)+"' and  '"+ReportDAO.EOM(date)+"' ) "
					+"update PAYTRAN  set  INP_AMT="+amount+"   where  TRNCD="+DeductionCode+" and EMPNO="+employee[i] +"  and  TRNDT between '"+ReportDAO.BOM(date)+"' and  '"+ReportDAO.EOM(date)+"'"
					+" else  "
					+ "INSERT INTO LateMarking VALUES('"+date+"',"+employee[i]+", "+DeductionCode+",0, "+amount+",0,0,0,"+amount+",' ','usercode','"+ReportDAO.getServerDate()+"','P' )";
			System.out.println(sql);
			try {
				Statement stmt = con.createStatement();
					stmt.executeUpdate(sql);
					flag = true;
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			}//System.out.println("value of flag in haNDLER "+flag);
			return flag;
		}

	}

	


