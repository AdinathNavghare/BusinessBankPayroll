
package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import payroll.Model.HolidayMasterBean;
import payroll.Model.LoanBean;

public class LoanHandler 
{
	public ArrayList<LoanBean> getTranInfo(String Empno)
	{
		ArrayList<LoanBean> trlist = new ArrayList<LoanBean>();
		Connection con= ConnectionManager.getConnection();
		LoanBean lb;
		try
		{
			Statement st = con.createStatement();
			ResultSet rs= null;
			System.out.println("Select * from loan_detail where empno="+Empno+"");
			rs= st.executeQuery("Select * from loan_detail where empno="+Empno+"");
			while(rs.next())
			{
				lb = new LoanBean();
				lb.setLoan_no(rs.getString("loan_no")!=null?rs.getInt("loan_no"):0);
				lb.setEMPNO(rs.getString(2)==null?0:rs.getInt(2));
				lb.setLoan_amt(rs.getString(3)==null?0:rs.getInt(3));
				lb.setStart_date(rs.getDate(4)==null?"":EmpOffHandler.dateFormat(rs.getDate(4)));
				lb.setEnd_date((rs.getDate(5)==null?"":EmpOffHandler.dateFormat(rs.getDate(5))));
				lb.setMonthly_install(rs.getString(6)==null?0:rs.getInt(6));
				lb.setLoan_per(rs.getString(7)==null?0:rs.getInt(7));
				lb.setBank_name(rs.getString(8)==null?"":rs.getString(8));
				lb.setSanctionby(rs.getString(9)==null?"":rs.getString(9));
				lb.setACTIVE(rs.getString(10)==null?"":rs.getString(10));
				lb.setLoan_code(rs.getString(11)==null?0:rs.getInt(11));
				lb.setTotal_month(rs.getString(12)==null?0:rs.getInt(12));
				lb.setActual_pay(rs.getString(13)==null?0:rs.getDouble(13));
				trlist.add(lb);
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
	
	
	/*public int addTransaction(LoanBean LB)
	{
		int result=0;
		Connection con= ConnectionManager.getConnection();
		try
		{
			 Statement st1 = con.createStatement(); 
			 ResultSet rs = st1.executeQuery("select * from loan_detail where loan_code="+LB.getLoan_code()+"");
			 if(rs.next())
			 {
				 result=2;
			 }
			 else
			 {
				 Statement st2 = con.createStatement();
				 String sql="INSERT INTO LOAN_DETAIL VALUES("+LB.getLoan_no()+","+LB.getEMPNO()+","+LB.getLoan_amt()+"," +
						"'"+LB.getStart_date()+"','"+LB.getEnd_date()+"',"+LB.getMonthly_install()+","+LB.getLoan_per()+"," +
						"'"+LB.getBank_name()+"','"+LB.getSanctionby()+"','"+LB.getACTIVE()+"',"+LB.getLoan_code()+")"; 
				 int i= st2.executeUpdate(sql);
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
*/

	public boolean addTransaction(LoanBean LB)
	{
		
		Connection con= ConnectionManager.getConnection();
		LoanHandler lh=new LoanHandler();
		boolean flag = false;
		try
		{
			 Statement st = con.createStatement(); 
			// ResultSet rs;
			 int maxLoannum=lh.getMaxLoanNo();
			 
			 
			 st.execute("INSERT INTO LOAN_DETAIL (loan_no,empno,loan_amt,start_date,end_date,monthly_install,loan_per,bank_name,sanctionby,Active,loan_code,loan_month,Actual_pay,createdby,createddate) VALUES("+maxLoannum+","+LB.getEMPNO()+","+LB.getLoan_amt()+"," +
						"'"+LB.getStart_date()+"','"+LB.getEnd_date()+"',"+LB.getMonthly_install()+","+LB.getLoan_per()+"," +
						"'"+LB.getBank_name()+"','"+LB.getSanctionby()+"','"+LB.getACTIVE()+"',"+LB.getLoan_code()+", "+LB.getTotal_month()+","+LB.getActual_pay()+","+LB.getAction_by()+",GETDATE())");
			 flag = true;
			 con.close();
		 
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return flag;		
	}

	
	
	
	
	public int getMaxLoanNo()
	{
		
		Connection con= ConnectionManager.getConnection();
		
		 int maxLoannum = 0;
		try
		{
			 Statement st = con.createStatement(); 
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
			 con.close(); 
		} 
	
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return maxLoannum;
		}
	
	
	
	
	
	
	
	
	
	
	public boolean updateLoanDtail(LoanBean lb1) 
	{
		boolean result=false;
		Connection con= ConnectionManager.getConnection();
		try
		{
			Statement st1 = con.createStatement();
			String updateQuery="UPDATE LOAN_DETAIL set loan_code="+lb1.getLoan_code()+", start_date='"+lb1.getStart_date()+"', " +
					" end_date='"+lb1.getEnd_date()+"',loan_amt="+lb1.getLoan_amt()+", " +
							"  monthly_install='"+lb1.getMonthly_install()+"',sanctionby='"+lb1.getSanctionby()+"',loan_month="+lb1.getTotal_month()+"," +
							" loan_per='"+lb1.getLoan_per()+"',Active='"+lb1.getACTIVE()+"',updatedby="+lb1.getAction_by()+", updateddate=GETDATE()  "+
							"  where empno='"+lb1.getEMPNO()+"' and loan_no='"+lb1.getLoan_no()+"'";
			
			st1.executeUpdate(updateQuery);
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
	
	
	
	public LoanBean getEmpLoan(String Empno,String date)
	{
		LoanBean lb = new LoanBean();
		Connection con= ConnectionManager.getConnection();
		
		try
		{
			Statement st = con.createStatement();
			ResultSet rs= null;
			rs= st.executeQuery("Select * from loan_detail where empno="+Empno+" and '"+date+"' between start_date and end_date and Active='Y' and loan_code=226 and VOUCHER_ID !=0 ");
			while(rs.next())
			{
				
				lb.setLoan_no(rs.getString("loan_no")!=null?rs.getInt("loan_no"):0);
				lb.setEMPNO(rs.getString(2)==null?0:rs.getInt(2));
				lb.setLoan_amt(rs.getString(3)==null?0:rs.getInt(3));
				lb.setStart_date(rs.getDate(4)==null?"":EmpOffHandler.dateFormat(rs.getDate(4)));
				lb.setEnd_date((rs.getDate(5)==null?"":EmpOffHandler.dateFormat(rs.getDate(5))));
				lb.setMonthly_install(rs.getString(6)==null?0:rs.getInt(6));
				lb.setLoan_per(rs.getString(7)==null?0:rs.getInt(7));
				lb.setBank_name(rs.getString(8)==null?"":rs.getString(8));
				lb.setSanctionby(rs.getString(9)==null?"":rs.getString(9));
				lb.setACTIVE(rs.getString(10)==null?"":rs.getString(10));
				lb.setLoan_code(rs.getString(11)==null?0:rs.getInt(11));
				lb.setTotal_month(rs.getString(12)==null?0:rs.getInt(12));
				lb.setActual_pay(rs.getString(13)==null?0:rs.getDouble(13));
				
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
		return lb;
	}
	
	public ArrayList<LoanBean> getAllLoan()
	{
		ArrayList<LoanBean> trlist = new ArrayList<LoanBean>();
		Connection con= ConnectionManager.getConnection();
		LoanBean lb;
		try
		{
			Statement st = con.createStatement();
			ResultSet rs= null;
			rs= st.executeQuery("Select * from loan_detail order by Active desc, loan_no");
			while(rs.next())
			{
				lb = new LoanBean();
				lb.setLoan_no(rs.getString("loan_no")!=null?rs.getInt("loan_no"):0);
				lb.setEMPNO(rs.getString(2)==null?0:rs.getInt(2));
				lb.setLoan_amt(rs.getString(3)==null?0:rs.getInt(3));
				lb.setStart_date(rs.getDate(4)==null?"":EmpOffHandler.dateFormat(rs.getDate(4)));
				lb.setEnd_date((rs.getDate(5)==null?"":EmpOffHandler.dateFormat(rs.getDate(5))));
				lb.setMonthly_install(rs.getString(6)==null?0:rs.getInt(6));
				lb.setLoan_per(rs.getString(7)==null?0:rs.getInt(7));
				lb.setBank_name(rs.getString(8)==null?"":rs.getString(8));
				lb.setSanctionby(rs.getString(9)==null?"":rs.getString(9));
				lb.setACTIVE(rs.getString(10)==null?"":rs.getString(10));
				lb.setLoan_code(rs.getString(11)==null?0:rs.getInt(11));
				lb.setTotal_month(rs.getString(12)==null?0:rs.getInt(12));
				lb.setActual_pay(rs.getString(13)==null?0:rs.getDouble(13));
				trlist.add(lb);
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


	public int getActiveLoan(String eMPNO, String start_date, String end_date) 
	{
		
		int result=1;
		Connection con= ConnectionManager.getConnection();
		LoanBean lb;
		try
		{
			Statement st = con.createStatement();
			ResultSet rs= null;
			rs= st.executeQuery("Select * from loan_detail where empno="+eMPNO+" and '"+start_date+"' between start_date and end_date");
			while(rs.next())
			{
				result= -1;
			}
			
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
			result=-1;
		}
		return result;
	}
	
}