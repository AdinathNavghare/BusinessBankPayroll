package payroll.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import payroll.Core.ReportDAO;
import payroll.Model.AdvanceBean;
import payroll.Model.BillBean;

public class BillHandler {
	
	public int getMaxBillId()
	{
	Connection connection = ConnectionManager.getConnection();
	
	 int maxBillId = 0;
	try
	{
		 Statement st = connection.createStatement(); 
		 ResultSet rs1;
		
		 rs1=st.executeQuery("select max(billId) from billdetails");
		 if(rs1.next())
		 {
			 maxBillId=rs1.getInt(1);
			 maxBillId++;
		 }
		 else
		 {
			 maxBillId=1;
		 }
		 connection.close(); 
	} 

	catch(SQLException e)
	{
		e.printStackTrace();
	}
	return maxBillId;
}
	
	public boolean addBillDetails(BillBean billBean) {
		boolean result = false;
		try {
			Connection con = ConnectionManager.getConnection();
		/*	String insertquery = "IF EXISTS (select * from BillDetails where billid ="+billBean.getBillId()+" and trncd="+billBean.getTrncd()+" and empno = "+billBean.getEmpNo()+" )" +
								 "UPDATE BillDetails SET BILL_TEL_NO = "+billBean.getBill_tel_no()+" , FROM_DATE ='"+billBean.getFrom_Date()+"' ," +
								 " TO_DATE ='"+billBean.getTo_Date()+"' , FORWHOM = "+billBean.getForWhom()+" ," +
								 " AMT ="+billBean.getAmount()+" , CREATEDBY = "+billBean.getCreatedBy()+" ," +
								 " CREATEDON ='"+billBean.getCreatedOn()+"'" +
								 " where billid ="+billBean.getBillId()+" and trncd="+billBean.getTrncd()+" and empno = "+billBean.getEmpNo()+" " +
								 "   ELSE " +
								 "insert into BillDetails  (EMPNO ,trncd , bill_tel_no,from_Date, to_Date ,forwhom, amt, createdBy, createdOn)" +
					                          " values(?,?,?,?,?,?,?,?,?)";*/
			
			String insertquery = "insert into BillDetails  (EMPNO ,trncd , bill_tel_no,from_Date, to_Date ,forwhom, amt, createdBy, createdOn)" +
		                          " values(?,?,?,?,?,?,?,?,?)";
			
			System.out.println("SQL Query"+insertquery);
			
			PreparedStatement Pstat = con.prepareStatement(insertquery);
			Pstat.setInt(1, billBean.getEmpNo());
			Pstat.setInt(2, billBean.getTrncd());
			Pstat.setInt(3, billBean.getBill_tel_no());
			Pstat.setString(4, billBean.getFrom_Date());
			Pstat.setString(5, billBean.getTo_Date());
			Pstat.setInt(6, billBean.getForWhom());
			Pstat.setFloat(7,billBean.getAmount());
			Pstat.setInt(8,billBean.getCreatedBy());
			Pstat.setString(9, billBean.getCreatedOn());
			
			Pstat.executeUpdate();
			
			Pstat.close();
			con.close();
			
			result = true;
			
		} catch (Exception e) {
			result=false;
			e.printStackTrace();
			
		}
		return result;

	}
	public ArrayList<BillBean> getBillDatails(int trncd, int year)
	{
		ArrayList<BillBean> result=new ArrayList<BillBean>();
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM BillDetails where trncd = "+trncd+" and createdOn between '1-apr-"+year+"' and '31-march-"+(year+1)+"' ");
			while(rs.next())
			{
				BillBean billbean = new BillBean();
				
				billbean.setBillId(rs.getInt("billid"));
				billbean.setEmpNo(rs.getInt("empno"));
				billbean.setTrncd(rs.getInt("trncd"));
				billbean.setForWhom(rs.getInt("forwhom"));
				billbean.setAmount(rs.getInt("amt"));
				billbean.setCreatedOn(sdf.format(rs.getDate("createdOn")));
				
				result.add(billbean);
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public BillBean getBillIdDetails(int billid ,int empno)
	{
		BillBean billbean = new BillBean();
		SimpleDateFormat sdf =new SimpleDateFormat("dd-MMM-yyyy");
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM BillDetails where billid = "+billid+" and empno ="+empno+" ");
			while(rs.next())
			{
				billbean.setBillId(rs.getInt("billid"));
				billbean.setEmpNo(rs.getInt("empno"));
				billbean.setTrncd(rs.getInt("trncd"));
				billbean.setBill_tel_no(rs.getInt("bill_tel_no"));
				billbean.setFrom_Date(sdf.format(rs.getDate("from_date")));
				billbean.setTo_Date(sdf.format(rs.getDate("to_date")));
				billbean.setForWhom(rs.getInt("forwhom"));
				billbean.setAmount(rs.getInt("amt"));
				billbean.setCreatedOn(sdf.format(rs.getDate("createdOn")));
			
				System.out.println("In Bill handler");
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return billbean;
	}

	
	public boolean deleteBillDetals(int  billid,int eno, int updateby){
		boolean result = false;
		try {
			Connection con = ConnectionManager.getConnection();
			String deleteQuery = "delete from BillDetails where EMPNO = "+eno+"  and billId ="+billid+"  ";
			PreparedStatement Pstat = con.prepareStatement(deleteQuery);
			Pstat.executeUpdate();
			
			//to update delete record if dont remove record.
		/*	String updateQuery = "update  BillDetails set updatedby = "+updateby+", updatedDate=GETDATE() where EMPNO = "+eno+"  and billId ="+billid+"  ";
			Pstat = con.prepareStatement(updateQuery);
			Pstat.executeUpdate();*/
				
			Pstat.close();
			con.close();
			
			result = true;
			
		} catch (Exception e) {
			result=false;
			e.printStackTrace();
			
		}
		return result;
	}
	
	
	public boolean updateDetails(BillBean billBean){
		boolean result = false;
		try {
			Connection con = ConnectionManager.getConnection();
			
			String editQuery = "UPDATE BillDetails SET BILL_TEL_NO = "+billBean.getBill_tel_no()+" , FROM_DATE ='"+billBean.getFrom_Date()+"' ," +
								" TO_DATE ='"+billBean.getTo_Date()+"' , FORWHOM = "+billBean.getForWhom()+" ," +
								" AMT ="+billBean.getAmount()+" , CREATEDBY = "+billBean.getCreatedBy()+", " +
								" CREATEDON ='"+billBean.getCreatedOn()+"' " +
								"WHERE BILLID = "+billBean.getBillId()+" AND EMPNO = "+billBean.getEmpNo()+" AND TRNCD = "+billBean.getTrncd()+" ";
			
			
			System.out.println(editQuery);
			PreparedStatement Pstat = con.prepareStatement(editQuery);
			
			Pstat.executeUpdate();
			
			Pstat.close();
			con.close();
			
			result = true;
			
		} catch (Exception e) {
			result=false;
			e.printStackTrace();
			
		}
		return result;
	}
	
	
	
}
