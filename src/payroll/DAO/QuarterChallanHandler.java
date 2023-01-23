package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import payroll.Core.ReportDAO;
import payroll.Model.Quarter_ChallanBean;

public class QuarterChallanHandler {

	public String insertQcDetail(Quarter_ChallanBean qcbean) {
	
		String flag="";
		Connection con=null;
		
		try{
			con=ConnectionManager.getConnection();
			Statement st = con.createStatement();
			Statement stmt = con.createStatement();
			
			 String sql="select * from Quarter_Details where Financial_Year="			    
						+ qcbean.getFinancial_Year()
						+ " and Quarter_No="
						+ qcbean.getQuarter_No()
						+ " ";
		       
			 System.out.println(sql);
			 
			    ResultSet rs=stmt.executeQuery(sql);
			//   System.out.println(" Financial_Year = "+((Quarter_ChallanBean) rs).getFinancial_Year());
			   
				System.out.println("Result set contain : "+rs);
				if(rs.next()){
					
					con=ConnectionManager.getConnection();
					st=con.createStatement();
					System.out.println("update");
					st.executeUpdate("update Quarter_Details set Financial_Year='"
							+ qcbean.getFinancial_Year()
							+ "',Quarter_No='"
							+ qcbean.getQuarter_No()
							+ "',Quarter_Reciept_No='"
							+ qcbean.getQuarter_Reciept_No()
							+ "',Quarter_Challan_Entered_By='"
							+ qcbean.getQuarter_Challan_Entered_By()
							+ "' where Financial_Year="			    
							+ qcbean.getFinancial_Year()
							+ " and Quarter_No="
							+ qcbean.getQuarter_No()
							+ "");
					flag="save";
				}
		      
				else{
			System.out.println("insert");
			String str="insert into Quarter_Details(Financial_Year,Quarter_No,Quarter_Reciept_No,Quarter_Challan_Entered_By) values "
					+ 
					"("
					+"' "
					+ qcbean.getFinancial_Year()
					+ " '"
					+ ", '"
					+ qcbean.getQuarter_No()
					+ "' "
					+ ", '"
					+qcbean.getQuarter_Reciept_No()
					+"' "
					+", '"
					+qcbean.getQuarter_Challan_Entered_By()
					+"')";

			//System.out.println("year "+qcbean.getFinancial_Year());
			
			System.out.println(str);
			stmt.execute(str);
			con.close();
				}
		}catch(Exception e){
			e.printStackTrace();
			flag="error";
		}
		
		return flag;
	}

	public String insertChallanDetail(Quarter_ChallanBean qcbean) throws SQLException {
		
		String flag="";
		//Boolean flag=false;
		try{
			
        Connection con=null;
        con=ConnectionManager.getConnection();
		Statement st = con.createStatement();
		Statement stmt = con.createStatement();
		Statement stmt1 = con.createStatement();
		
       String sql="select * from Challan_Details where Financial_Year="
				+ qcbean.getYear()
				+ " and For_Month='"
				+ ReportDAO.EOM(qcbean.getFor_Month())
				+ "' ";
       System.out.println("Hi");
       System.out.println(sql);
	    ResultSet rs=stmt.executeQuery(sql);
		
		if(rs.next()){
			con=ConnectionManager.getConnection();
			st=con.createStatement();
			
			st.executeUpdate("update Challan_Details set Financial_Year='"
				+ qcbean.getYear()
				+ "',For_Month='"
				+ ReportDAO.EOM(qcbean.getFor_Month())
				+ "',Challan_No='"
				+ qcbean.getChallan_No()
				+ "',Date_Of_Payment='"
				+ qcbean.getDate_Of_Payment()
				+ "',Due_Date_Payment='"
				+ qcbean.getDue_Date_Payment()
				+ "' where Financial_Year="
				+ qcbean.getYear()
				+ " and For_Month='"
				+ ReportDAO.EOM(qcbean.getFor_Month())
				+ "' ");
			flag="save";
			//flag=true;
		}
      
		else{
      
			//System.out.println(qcbean.getFor_Month());
					
			String str="insert into Challan_Details(Financial_Year,For_Month,Challan_No,Date_Of_Payment,Due_Date_Payment) values "
					+ 
					"("
					+"'"
					+ qcbean.getYear()
					+ "'"
					+ ", '"
					+ ReportDAO.EOM(qcbean.getFor_Month())
					+ "' "
					+ ", '"
					+qcbean.getChallan_No()
					+"' "
					+", '"
					+qcbean.getDate_Of_Payment()
					+"' "
					+", '"
					+qcbean.getDue_Date_Payment()
					+"')";

			System.out.println("month is  : "+ReportDAO.EOM(qcbean.getFor_Month()));
			
			System.out.println(str);
			stmt1.execute(str);
			flag="save";
			//flag=true;
			con.close();
		}
		}catch(Exception e){
			e.printStackTrace();
			flag="error";
			//flag=false;
		}
		
		return flag;
		//return flag;
	}
	public String insert_Tds_Payment_Details(Quarter_ChallanBean qcbean) {
		String flag="";
		Connection con=null;
		try{
			con=ConnectionManager.getConnection();
			Statement st = con.createStatement();
			Statement stmt = con.createStatement();
			String sql="select * from TDS_Payment_Details where Financial_Year="+ qcbean.getYear()+ " ";
			ResultSet rs=stmt.executeQuery(sql);
				if(rs.next()){
					con=ConnectionManager.getConnection();
					st=con.createStatement();
					System.out.println("update");
					st.executeUpdate("update TDS_Payment_Details set Branch_Code='"
							+ qcbean.getBSR_Code()
							+ "',Branch_Name='"
							+ qcbean.getBranch_name()
							+ "' where Financial_Year="			    
							+ qcbean.getYear()
							+ "");
					flag="save";
				}
				else{
			System.out.println("insert");
			String str="insert into TDS_Payment_Details(Financial_Year,Branch_Code,Branch_Name) values "
					+ 
					"("
					+"' "
					+ qcbean.getYear()
					+ " '"
					+ ", '"
					+ qcbean.getBSR_Code()
					+ "' "
					+ ", '"
					+qcbean.getBranch_name()
					+"' )";
			System.out.println(str);
			stmt.execute(str);
			con.close();
				}
		}catch(Exception e){
			e.printStackTrace();
			flag="error";
		}
		return flag;
	}
}
