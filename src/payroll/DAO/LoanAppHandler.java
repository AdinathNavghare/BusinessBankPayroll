package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import payroll.Core.ReportDAO;
import payroll.Model.AdvanceBean;
import payroll.Model.LoanAppBean;
import payroll.Model.LoanBean;

public class LoanAppHandler {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	EmpAttendanceHandler EAH=new EmpAttendanceHandler();
	String currentdate=EAH.getServerDate();

		
	public float getCalAmount(int empno, String date)
	{
		Connection connection = ConnectionManager.getConnection();
	
		System.out.println(date);
		float netAmount = 0;
		try
		{
			 Statement st = connection.createStatement(); 
			 ResultSet rs1;
			String query="select CAL_AMT from PAYTRAN_STAGE where EMPNO="+empno+" and TRNDT between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' and trncd=999";
			System.out.println("query"+query); 
			rs1=st.executeQuery(query);
			 if(rs1.next())
			 {
				 netAmount=rs1.getFloat(1);
				
			 }
			
			 connection.close(); 
		} 
	
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		System.out.println("calAmount"+ netAmount);
		return netAmount;
		}
	
	
	public float getAdjAmount(int empno, String date)
	{
		
		Connection connection = ConnectionManager.getConnection();
		System.out.println(date);
		float netAmount = 0;
		try
		{
			 Statement st = connection.createStatement(); 
			 ResultSet rs1;
			String query="select ADJ_AMT from PAYTRAN_STAGE where EMPNO="+empno+" and TRNDT between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' and trncd=999";
			System.out.println("query"+query); 
			rs1=st.executeQuery(query);
			 if(rs1.next())
			 {
				 netAmount=rs1.getFloat(1);
				
			 }
			
			 connection.close(); 
		} 
	
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		System.out.println("adjAmount"+ netAmount);
		return netAmount;
		}
	
	public float getNetAmount(int empno, String date)
	{
		Connection connection = ConnectionManager.getConnection();
		
		System.out.println(date);
		float netAmount = 0;
		try
		{
			 Statement st = connection.createStatement(); 
			 ResultSet rs1;
			String query="select NET_AMT from PAYTRAN_STAGE where EMPNO="+empno+" and TRNDT between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' and trncd=999";
			System.out.println("query"+query); 
			rs1=st.executeQuery(query);
			 if(rs1.next())
			 {
				 netAmount=rs1.getFloat(1);
				
			 }
			
			 connection.close(); 
		} 
	
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		System.out.println("netAmount"+ netAmount);
		return netAmount;
		}
	
	public float getLastLoanAmount(int empno, String date, int loancode)
	{
		Connection connection = ConnectionManager.getConnection();
		
		System.out.println(date);
		float netAmount = 0;
		try
		{
			 Statement st = connection.createStatement(); 
			 ResultSet rs1;
			String query="select NET_AMT from PAYTRAN_STAGE where EMPNO="+empno+" and TRNDT between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' and trncd="+loancode+"";
			System.out.println("query"+query); 
			rs1=st.executeQuery(query);
			 if(rs1.next())
			 {
				 netAmount=rs1.getFloat(1);
				
			 }
			
			 connection.close(); 
		} 
	
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		System.out.println("netAmount"+ netAmount);
		return netAmount;
		}
	
	public int getMaxLoanNo()
	{
		
		Connection connection = ConnectionManager.getConnection();
		
		 int maxLoannum = 0;
		try
		{
			 Statement st = connection.createStatement(); 
			 ResultSet rs1;
			
			 rs1=st.executeQuery("select count(DISTINCT(loan_no)) from loan_detail");
			 if(rs1.next())
			 {
				 maxLoannum=rs1.getInt(1);
				 maxLoannum++;
			 }
			 else
			 {
				 maxLoannum=1;
			 }
			 connection.close(); 
		} 
	
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return maxLoannum;
		}
	
	public boolean addloan(LoanAppBean lb)
	{
		
		Connection connection = ConnectionManager.getConnection();
		LoanAppHandler lh=new LoanAppHandler();
		boolean flag = false;
		 int maxLoannum=lh.getMaxLoanNo();
		try
		{
			 Statement st = connection.createStatement(); 
			// ResultSet rs;
			System.out.println("in handler"+lb.getMonthly_install());
			 String loan="INSERT INTO Loan_detail (loan_no,empno,prj_srno,loan_amt,start_date,end_date,monthly_install,loan_per,bank_name,active,loan_code,loan_month,createdby,createddate)" +
				 		" VALUES(" +maxLoannum+","+lb.getEMPNO()+", " +lb.getSite_id()+", "+lb.getLoan_amt()+"," +
							"'"+lb.getStart_date()+"','"+lb.getEnd_date()+"',"+lb.getMonthly_install()+","+lb.getLoan_per()+"," +
							"'"+lb.getBank_name()+"' ,'Pending',"+lb.getLoan_code()+", "+lb.getTotal_month()+","+lb.getAction_by()+",GETDATE())";
			 System.out.println("+++++ "+loan);
			 st.execute(loan);
			 flag = true;
			 connection.close();
		 
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return flag;		
	}
	
	
	public ArrayList<LoanAppBean> loanDisplay(int empno1, String loanType,String site,String roleId) {
		Connection connection = ConnectionManager.getConnection();
		ResultSet rs = null;
		System.out.println("SITE ID    :"+site);
		System.out.println("Loan Type    :"+loanType);
		ArrayList<LoanAppBean> List1 = new ArrayList<LoanAppBean>();
		try {
			String query = "";
			
			int site_id=Integer.parseInt(site);
			
		
			Statement st = connection.createStatement();
					
		{
			if (loanType.equalsIgnoreCase("PENDING")) {
				query = "SELECT * FROM  loan_detail   WHERE active = 'PENDING' and " +
						"PRJ_SRNO ="+site_id+" order by loan_no desc";
			}
			
			
			else if (loanType.equalsIgnoreCase("SANCTION")) {
				query = "SELECT * FROM loan_detail   WHERE  active = 'SANCTION' and " +
						"PRJ_SRNO ="+site_id+" order by loan_no desc";
			} 
			else if (loanType.equalsIgnoreCase("CANCEL")) {
				query = "SELECT * FROM loan_detail   WHERE  active = 'CANCEL' and " +
						"PRJ_SRNO ="+site_id+" order by loan_no desc";
			}
			else if (loanType.equalsIgnoreCase("APPROVED")) {
				query = "SELECT * FROM loan_detail   WHERE active = 'APPROVED' and " +
						"PRJ_SRNO ="+site_id+" order by loan_no desc";
			}
			else if (loanType.equalsIgnoreCase("NIL")) {
				query = "SELECT * FROM loan_detail   WHERE active = 'NIL' and " +
						"PRJ_SRNO ="+site_id+" order by loan_no desc";
			}
			else
				query = "SELECT * FROM loan_detail   WHERE "
						
						+ " active !='1' and PRJ_SRNO ="+site_id+" order by loan_no desc";
		}		
			rs = st.executeQuery(query);

			while (rs.next()) {
				LoanAppBean lb = new LoanAppBean();
				
				lb.setEMPNO(rs.getInt("empno"));
				lb.setSite_id(rs.getInt("prj_srno"));
				lb.setLoan_amt(rs.getFloat("loan_amt"));
				lb.setStart_date(rs.getString("start_date") == null ? "" : sdf.format(rs.getDate("start_date")));
				lb.setEnd_date(rs.getString("end_date") == null ? "" : sdf.format(rs.getDate("end_date")));
				lb.setMonthly_install(rs.getFloat("monthly_install"));
				lb.setLoan_per(rs.getFloat("loan_per"));
				lb.setBank_name(rs.getString("bank_name") == null ? "" : rs.getString("bank_name"));
				lb.setSanctionby(rs.getString("sanctionby") == null ? "" : rs.getString("sanctionby"));
				lb.setSanctiondate(rs.getString("sanctiondate") == null ? "" : sdf.format(rs.getDate("sanctiondate")));
				lb.setLoan_code(rs.getInt("loan_code"));
				lb.setACTIVE(rs.getString("active") == null ? "" : rs.getString("active"));
				lb.setActual_pay(rs.getFloat("actual_pay"));
				lb.setLoan_no(rs.getInt("loan_no"));
			
			  
		
			
				List1.add(lb);

			}

			rs.close();
			st.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return List1;
	}
	
	
	public ArrayList<LoanAppBean> getLoanAppList(LoanAppBean lBean,String searchType) {
		Connection connection = ConnectionManager.getConnection();

		ArrayList<LoanAppBean> list = new ArrayList<LoanAppBean>();
		String searchQuery = null;
		

System.out.println("TYPE "   +searchType);
		  if(searchType.equalsIgnoreCase("custom")){ 
			  String status = "";
			 
		  if(lBean.getACTIVE().equalsIgnoreCase("ALL")){
		 

		
		   searchQuery= "SELECT  *  FROM Loan_detail WHERE EMPNO = " +
		  " '"+lBean.getEMPNO()+"'   AND  START_DATE " +
		  " between '"+lBean.getStart_date()+"' AND '"+lBean.getEnd_date()+"' order by loan_no desc ";
		   }
		 
		  else
		  {
			  
		  status = " '"+lBean.getACTIVE()+"'"; 
		  searchQuery=  "SELECT * FROM Loan_detail WHERE " +
		  				"EMPNO ="+lBean.getEMPNO()+" " +
		  				" AND  START_DATE  between '"+lBean.getStart_date()+"' AND '"+lBean.getEnd_date()+"' and Active = "+status+"  order by loan_no desc ";
		  
	//	  System.out.println("else"); System.out.println(searchQuery);
		  }
		  }
		 else {
			 System.out.println("++++"+lBean.getACTIVE());
		 System.out.println("if else part of the custom advance search"); 
		  searchQuery="SELECT * FROM Loan_detail  WHERE active='"+lBean.getACTIVE()+"' order by loan_no desc " ;
		  
		  System.out.println(searchQuery);

		  }
		   
		try {
			
			Statement statement1 = connection.createStatement();
		
			ResultSet rslist1 = null;
			
			rslist1 = statement1.executeQuery(searchQuery);
			LoanAppBean loanBean = null;

			while (rslist1.next()) {
				loanBean = new LoanAppBean();
				
				loanBean.setEMPNO(rslist1.getInt("empno"));
				loanBean.setSite_id(rslist1.getInt("prj_srno"));
				loanBean.setLoan_amt(rslist1.getFloat("loan_amt"));
				loanBean.setStart_date(rslist1.getString("start_date") == null ? "" : sdf.format(rslist1.getDate("start_date")));
				loanBean.setEnd_date(rslist1.getString("end_date") == null ? "" : sdf.format(rslist1.getDate("end_date")));
				loanBean.setMonthly_install(rslist1.getFloat("monthly_install"));
				loanBean.setLoan_per(rslist1.getFloat("loan_per"));
				loanBean.setBank_name(rslist1.getString("bank_name") == null ? "" : rslist1.getString("bank_name"));
				loanBean.setSanctionby(rslist1.getString("sanctionby") == null ? "" : rslist1.getString("sanctionby"));
				loanBean.setSanctiondate(rslist1.getString("sanctiondate") == null ? "" : sdf.format(rslist1.getDate("sanctiondate")));
				loanBean.setLoan_code(rslist1.getInt("loan_code"));
				loanBean.setTotal_month(rslist1.getInt("loan_month"));
				loanBean.setACTIVE(rslist1.getString("active") == null ? "" : rslist1.getString("active"));
				loanBean.setActual_pay(rslist1.getFloat("actual_pay"));
				loanBean.setLoan_no(rslist1.getInt("loan_no"));
			
			  
		
	
				list.add(loanBean);

			}
			rslist1.close();
			statement1.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
public ArrayList<LoanAppBean> getEmpList(String prjCode){
	Connection connection = ConnectionManager.getConnection();
  SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy");
		ArrayList<LoanAppBean> list= new ArrayList<LoanAppBean>();
		
		ResultSet rs = null;
		
		//String query = "SELECT EMPNO FROM EMPTRAN WHERE PRJ_CODE='"+prjCode+"'";
		//String query = "select emp.EMPNO from EMPMAST emp inner join EMPTRAN etn on emp.EMPNO = etn.EMPNO where emp.STATUS = 'A'  and etn.PRJ_SRNO="+prjCode;
		
//		String query = "select emp.EMPNO from EMPMAST emp inner join EMPTRAN etn on emp.EMPNO = etn.EMPNO where emp.STATUS = 'A' and etn.PRJ_SRNO='"+prjCode+"' and etn.SRNO = (select MAX(en.SRNO) from EMPTRAN en where en.PRJ_SRNO = '"+prjCode+"' and etn.EMPNO = en.EMPNO )";
		String query = "select distinct(emp.EMPNO) as empno ,emp.EMPCODE as empcode, emp.FNAME+' '+emp.MNAME+' '+emp.LNAME as NAME ,p.trndt  from EMPMAST" +
				" emp inner join EMPTRAN etn on emp.EMPNO = etn.EMPNO left outer join paytran p on p.EMPNO=etn.EMPNO" +
				" where emp.STATUS = 'A' and etn.PRJ_SRNO="+prjCode+"  AND etn.SRNO=(SELECT MAX(ET.SRNO) FROM EMPTRAN " +
						"ET WHERE ET.EMPNO=emp.EMPNO)  and p.TRNDT=(select MIN(trndt) from PAYTRAN where EMPNO=etn.EMPNO)  " +
						" order by emp.EMPNO";
		
		
		try
		{
			Statement st = connection.createStatement();
			rs = st.executeQuery(query);
			while(rs.next())
			{
				LoanAppBean adbn = new LoanAppBean();
				adbn.setEMPNO(rs.getInt("empno"));
				adbn.setEmpcode(rs.getInt("empcode"));
				adbn.setName(rs.getString("NAME"));	
				adbn.setCurrentSalaryMonth(ReportDAO.BOM(simpleDateFormat.format(rs.getDate("trndt"))));
			
				list.add(adbn);
			}
			System.out.println("sizeeeeeeeeeeeee of list is"+list.size());
			rs.close();
			st.close();
			connection.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return list;
	}


public boolean setSanction(int empno, int appNo, float amount,int eno, String ForMonth, int loancode) throws SQLException {
	System.out.println("for month"+ForMonth);
	
	Connection connection = ConnectionManager.getConnection();

		
		

		String sql = "UPDATE  loan_detail SET active='SANCTION' ,SANCTIONBY="+eno+", " +
				"SANCTIONDATE= '"+currentdate+"', Actual_pay = "+amount+" * loan_month , " +
				"updatedby = "+eno+" , updateddate = '"+currentdate+"' WHERE loan_no=" + appNo;
	
		
	/*	float calAmount=getCalAmount(empno, ForMonth);
		float adjAmount=getAdjAmount(empno, ForMonth);
		float netAmount=getNetAmount(empno, ForMonth);*/
		float lastLoanAmount=getLastLoanAmount(empno, ForMonth, loancode);
     	float total_loan_Amt=lastLoanAmount+amount;
		
		/*float total_cal_Amt= calAmount+amount;
		float total_adj_Amt=  adjAmount-amount;
		float total_net_Amt=  netAmount-amount;*/
		
		
		
		
		String paytranStage="if exists(select * from PAYTRAN where EMPNO="+empno+" and TRNDT between '"+ForMonth+"' and ' "+ReportDAO.EOM(ForMonth)+"' and trncd="+loancode+") " +
				" update PAYTRAN set srno=0 ,inp_amt="+total_loan_Amt+" , cal_amt= "+total_loan_Amt+" , Net_amt = "+total_loan_Amt+"," +
						"CF_SW='*' , USRCODE="+eno+" , UPDDT= getdate() where trncd="+loancode+" and empno="+empno+" " +
						" and TRNDT between '"+ForMonth+"' and ' "+ReportDAO.EOM(ForMonth)+"' "+
				"else INSERT INTO PAYTRAN (TRNDT,empno, TRNCD,SRNO, INP_AMT, CAL_AMT,ADJ_AMT,ARR_AMT, NET_AMT,CF_SW,USRCODE,UPDDT,STATUS) " +
				"VALUES('"+ReportDAO.EOM(ForMonth)+"',"+empno+",  "+loancode+" , 0, "+amount+" , "+amount+", 0.00, 0.00, "+amount+",'*',  "+eno+",GETDATE() , 'A' )";
			
		//String payTran_CAL="update PAYTRAN set cal_amt="+total_cal_Amt+", adj_amt="+total_adj_Amt+", net_amt="+total_net_Amt+" , USRCODE="+eno+" , UPDDT= getdate()  where empno="+empno+" " +
			              //   	" and trncd=999 and trndt between  '"+ForMonth+"' and ' "+ReportDAO.EOM(ForMonth)+"'   ";
		
			System.out.println("query to be "+paytranStage);
		
		
		
		
		
		
		boolean flag = false;

		try {
			Statement statement = connection.createStatement();
			
			Statement statement1 = connection.createStatement();
			Statement statement2 = connection.createStatement();
				statement.executeUpdate(sql);
				
				statement1.executeUpdate(paytranStage);
			
	
				connection.close();
				
				
				

				// for inserting into fianance module 
				
				String notification= " select  usr.[USER_ID] as id,isnull(User_Firstname,'')+' '+isnull(User_Lastname,'') as name from Users usr" +
						"   left join User_Role usrrol  on usr.[User_Id]=usrrol.[USer_Id]" +
						"   left join Roles rol   on usrrol.Role_Id=rol.Role_Id  where User_Isdeleted=0" +
						"   and (isnull(User_Firstname,'') +' '+isnull(User_Lastname,'')!=' ')" +
						"   and usr.IsCheck =1 and usr.IsApprove =1  and usr.IsPublish =1 and rol.Role_Name='GM Accounts' ";
				
				
				ResultSet rs11=null;
				
				Connection conTech =  ConnectionManager.getConnectionTech();
				Statement stmt3 = conTech.createStatement();
				
				
				rs11=stmt3.executeQuery(notification);
				while(rs11.next())
				{
				
					System.out.println("user ids for fianance : "+rs11.getInt("id"));
					
					
				String insert= " INSERT INTO [ReminderTask]  ([UserID] ,[Message] ,[Status] ) VALUES" +
						       "      ("+rs11.getInt("id")+" ,' Approval for Loan Requisition Bill Id :- "+appNo+" '  ,'Open'  )" +
						       "     Declare @reminder_id int  set @reminder_id =SCOPE_IDENTITY(); " +
						       

	     					   "    INSERT INTO [ReminderModule]  ([ModuleName] , [ModuleID] ,[ReminderMessageID])" +
						       "    VALUES ('Loan_Request' ,"+appNo+" , @reminder_id )      ";	
				
				
				
				System.out.println("insert  query" +insert);
				
				Statement InsertNotification = conTech.createStatement();
				
		         InsertNotification.executeUpdate(insert);
								
					
				} // ends here
				
				
				
				flag = true;
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}

	public boolean cancelLoanApp(int empno, int appNo,int eno) {
		Connection connection = ConnectionManager.getConnection();

		boolean flag = false;
	
		
		
		
		String sql = "UPDATE LOAN SET active='CANCEL' ,SANCTION_BY="+eno+", " +
				"SANCTIONDATE= '"+currentdate+"', updatedby = "+eno+" , updateddate = '"+currentdate+"' " +
				" WHERE loan_no=" + appNo;
		Statement statement;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(sql);

			statement.close();
			connection.close();

			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}	
	public String getMaximumTransactionDate() throws SQLException {
		Connection connection = ConnectionManager.getConnection();

		String date="";
		try{
		connection = ConnectionManager.getConnection();
		Statement statement;
		statement= connection.createStatement();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MMM-yyyy"); 
		Date tempDate=new Date();
		
         
		ResultSet resultSet=statement.executeQuery("SELECT MAX(TRNDT) AS TRNDT FROM PAYTRAN_STAGE ");
		
		while (resultSet.next())
		{

			date=resultSet.getString("TRNDT");
			tempDate=simpleDateFormat.parse(date);  
			date= outputDateFormat.format(tempDate);
	
	}
		System.out.println("date issssssssssss"+date);
		statement.close();
		connection.close();
		}catch(Exception e){
			e.printStackTrace();
		}

	return date;
	}


}
