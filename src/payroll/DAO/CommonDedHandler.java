package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import payroll.Core.ReportDAO;

public class CommonDedHandler {
	
	
	
	
	
	public boolean addDeduction(String date, String EMPNOLIST,String DeductionCode, String amount) throws SQLException {
		
			boolean flag = false;
			Connection con = ConnectionManager.getConnection();
			

			String Allemp = EMPNOLIST ;
			String[] employee = Allemp.split(",");
			
			for (int i=0;i<employee.length;i++)
			{
				
			String sql="if exists(select * from PAYTRAN where TRNCD="+DeductionCode+" and EMPNO="+ employee[i] +"  and  TRNDT between '"+ReportDAO.BOM(date)+"' and  '"+ReportDAO.EOM(date)+"' ) "
					+"update PAYTRAN  set  INP_AMT="+amount+"   where  TRNCD="+DeductionCode+" and EMPNO="+employee[i] +"  and  TRNDT between '"+ReportDAO.BOM(date)+"' and  '"+ReportDAO.EOM(date)+"'"
					+" else  "
					+ "INSERT INTO PAYTRAN VALUES('"+date+"',"+employee[i]+", "+DeductionCode+",0, "+amount+",0,0,0,"+amount+",' ','usercode','"+ReportDAO.getServerDate()+"','P' )";
			System.out.println(sql);
			try {
				Statement stmt = con.createStatement();
					stmt.executeUpdate(sql);
					flag = true;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			}//System.out.println("value of flag in haNDLER "+flag);
			return flag;
		}
	
//	public boolean addLatemark(String date, String EMPNOLIST,String DeductionCode,int sessionEmployee,String today) throws SQLException {
	public boolean addLatemark(String date, String EMPNOLIST,String DeductionCode,int sessionEmployee,String today) throws SQLException {
		
		boolean flag = false;
		Connection con = ConnectionManager.getConnection();
		Connection conn = ConnectionManager.getConnection();
		
		Statement st3 = con.createStatement();
		ResultSet rs2 = null;
		rs2 = st3.executeQuery("select isnull((SELECT MAX(APPLNO)+1 FROM LEAVETRAN),1)");
		int applno = 0;
		if(rs2.next()){
			applno = rs2.getInt(1);
		}
		
		String Allemp = EMPNOLIST ;
		String[] employee = Allemp.split(",");
		Statement st2 = conn.createStatement();
		Statement st1 = conn.createStatement();
		ResultSet rs = st2.executeQuery("select distinct empno from EMP_ATTENDANCE where ATTD_DATE between '"
				+ ReportDAO.BOM(date)
				+ "' and '"
				+ ReportDAO.EOM(date)
				+ "' " +
				" and empno in ("+EMPNOLIST+") order by EMPNO ");

while (rs.next()) {
	float totDed = 0;
	int lateday=0;
	float totPl = 0;
	float totCr = 0;
	float bal = 0;
	ResultSet rs1 = st1.executeQuery("SELECT EMPNO,ATTD_DATE,ATTD_VAL FROM EMP_ATTENDANCE where ATTD_DATE between '"
					+ ReportDAO.BOM(date)
					+ "' and '"
					+ ReportDAO.EOM(date)
					+ "' and EMPNO="
					+ rs.getInt("empno")
					+ " order by ATTD_DATE");
	while (rs1.next()) {
	
		 if (rs1.getString("ATTD_VAL").equalsIgnoreCase("L")) {
			 lateday++;

			 
			 if(lateday==3)
			 {

				 lateday=lateday/3;
					Statement st = conn.createStatement();
					ResultSet rs5 = null;
					
					rs5 = st.executeQuery("SELECT * FROM LEAVEBAL WHERE LEAVECD=3 AND " +
							" EMPNO="	+ rs.getInt("EMPNO")+"    ORDER BY srno DESC");
					
					if (rs5.next()) {
						Statement st4 = conn.createStatement();
						
						if(rs5.getFloat("BAL")==0)
						{
							

							
							ResultSet rs6 = null;
							rs6 = st.executeQuery("SELECT TOP 1 * FROM LEAVEBAL WHERE LEAVECD=1 AND " +
									" EMPNO="	+ rs.getInt("EMPNO")+"  ORDER BY srno DESC");	
							if (rs6.next()) {
							Statement st5 = conn.createStatement();
							
							
							if(rs6.getFloat("BAL")==0)
							{
								
								
								
								String leave = "insert into LEAVETRAN (LEAVECD,EMPNO,TRNDATE,TRNTYPE,APPLDT,FRMDT,TODT,STATUS,DAYS,LEAVEPURP,applno,lreason,created_by,created_date) VALUES (7,"
										+ rs.getInt("EMPNO")
										+ ",'"
										+ rs1.getString("ATTD_DATE")
										+ "','D','"
										+ rs1.getString("ATTD_DATE")
										+ "','"
										+ rs1.getString("ATTD_DATE")
										+ "','"
										+ rs1.getString("ATTD_DATE")
										//+ "','SANCTION',"+lateday+",185,"+applno+",'AS PER ADMIN RULE, LATE MARK OF,"+rs1.getString("ATTD_DATE")+"')";
										+ "','Pending',"+lateday+",185,"+applno+",'AS PER ADMIN RULE, LATE MARK OF,"+rs1.getString("ATTD_DATE")+"')";

								System.out.println("late mark querrrrrrrrrrrrrryyyyyyyyyyyyyy for lwp"+leave);
								st5.execute(leave);
								
								ResultSet rs7 = null;
								rs7 = st.executeQuery("SELECT TOP 1 * FROM LEAVEBAL WHERE LEAVECD=7 AND " +
										" EMPNO="	+ rs.getInt("EMPNO")+"    ORDER BY srno DESC");	
								if (rs7.next()) {
								
								// to insert lwp in leavebal
								bal = rs7.getString("BAL")==""?0:rs7.getFloat("BAL");
								totCr = rs7.getFloat("TOTCR");
								//String leave2="insert into LEAVEBAL VALUES ('"+rs1.getString("ATTD_DATE")+"',"+rs7.getInt("EMPNO")+",7,"+rs7.getFloat("BAL")+"-1,"+rs7.getFloat("TOTCR")+","+rs7.getFloat("TOTDR")+"+1)";
								//System.out.println("leave2"+ leave2);
								// st4.execute(leave2);
								}
						
							
							}
							else{
							String leave = "insert into LEAVETRAN (LEAVECD,EMPNO,TRNDATE,TRNTYPE,APPLDT,FRMDT,TODT,STATUS,DAYS,LEAVEPURP,applno,lreason) VALUES (1,"
									+ rs.getInt("EMPNO")
									+ ",'"
									+ rs1.getString("ATTD_DATE")
									+ "','D','"
									+ rs1.getString("ATTD_DATE")
									+ "','"
									+ rs1.getString("ATTD_DATE")
									+ "','"
									+ rs1.getString("ATTD_DATE")
									//+ "','SANCTION',"+lateday+",185,"+applno+",'AS PER ADMIN RULE, LATE MARK OF,"+rs1.getString("ATTD_DATE")+"')";
									+ "','Pending',"+lateday+",185,"+applno+",'AS PER ADMIN RULE, LATE MARK OF,"+rs1.getString("ATTD_DATE")+"')";
							
							System.out.println("late mark querrrrrrrrrrrrrryyyyyyyyyyyyyy for pl"+leave);
							st5.execute(leave);
							
							bal = rs6.getFloat("BAL");
							totCr = rs6.getFloat("TOTCR");
							//String leave2="insert into LEAVEBAL VALUES ('"+rs1.getString("ATTD_DATE")+"',"+rs6.getInt("EMPNO")+",1,"+rs6.getFloat("BAL")+"-1,"+rs6.getFloat("TOTCR")+","+rs6.getFloat("TOTDR")+"+1)";
							//System.out.println("leave2"+ leave2);
							 //st4.execute(leave2);
							
							}
							
							}
							
						
							
						
						}
						
						else{
						
						String leave = "insert into LEAVETRAN (LEAVECD,EMPNO,TRNDATE,TRNTYPE,APPLDT,FRMDT,TODT,STATUS,DAYS,LEAVEPURP,applNO,lreason) VALUES (3,"
								+ rs.getInt("EMPNO")
								+ ",'"
								+ rs1.getString("ATTD_DATE")
								+ "','D','"
								+ rs1.getString("ATTD_DATE")
								+ "','"
								+ rs1.getString("ATTD_DATE")
								+ "','"
								+ rs1.getString("ATTD_DATE")
								//+ "','SANCTION',"+lateday+",185,"+applno+",'AS PER ADMIN RULE, LATE MARK OF,"+rs1.getString("ATTD_DATE")+"')";
								+ "','Pending',"+lateday+",185,"+applno+",'AS PER ADMIN RULE, LATE MARK OF,"+rs1.getString("ATTD_DATE")+"')";
						
						System.out.println("late mark querrrrrrrrrrrrrryyyyyyyyyyyyyy for cl"+leave);
						st4.execute(leave);
						
						bal = rs5.getFloat("BAL");
						totCr = rs5.getFloat("TOTCR");
						//String leave2="insert into LEAVEBAL VALUES ('"+rs1.getString("ATTD_DATE")+"',"+rs5.getInt("EMPNO")+",3,"+rs5.getFloat("BAL")+"-1,"+rs5.getFloat("TOTCR")+","+rs5.getFloat("TOTDR")+"+1)";
						//System.out.println("leave2"+ leave2);
						//st4.execute(leave2);
						}	 
						 
					} 

					lateday=0;
				 
			 }
			 
		}
	 
	}
	

	
	
		Statement st4 = conn.createStatement();
	String query="update emp_attendance set emp_status='POST' where empno="+rs.getInt("empno")+" and ATTD_DATE between ' "
					+ ReportDAO.BOM(date)+"' and '"+ ReportDAO.EOM(date)+" '  ";
	
	System.out.println("emp_attendance queryyyyy "+query);
		 st3.execute(query);
		

}
return flag;

}

}
