package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import payroll.Model.BranchBean;
import payroll.Model.EmpAddressBean;
import payroll.Model.Lookup;
import payroll.Model.RoleBean;

public class BranchDAO {

	Connection connection = null;
	Statement statement=null;
	ResultSet resultSet=null;
	Boolean flag=false;
	public boolean addBranch(BranchBean branchBean,String todaysDate,int loggedEmployee){
		try
		{
			
			connection = ConnectionManager.getConnection();
			statement= connection.createStatement();
			statement.execute("INSERT INTO Project_Sites(SITE_ID,SITE_NAME,SITE_LOCATION,ADDR1,ADDR2,ADDR3,SITE_CITY,SITE_STATE,PINCD,PHONE1,PHONE2,FAX,START_DT,EDATE,CREATED_BY,CREATED_DATE," +
					"I_SHIFT_F,I_SHIFT_T,II_SHIFT_F,II_SHIFT_T,I_CASH_F,I_CASH_T,II_CASH_F,II_CASH_T,WEEK_OFF,ALTER_SAT,SITE_STATUS,SITE_ISDELETED) VALUES("+branchBean.getBranchCode()+" , '"+branchBean.getBranchName()+"' , '"+branchBean.getZone()+"' , '" 
					+branchBean.getAddress1()+"' , '"+branchBean.getAddress2()+"' , '"+branchBean.getAddress3()+"' , "
					+branchBean.getCity()+" , "+branchBean.getState()+", "+branchBean.getPincode()+" , '"+branchBean.getPhone1()+"' , '"
					+branchBean.getPhone2()+"' , '"+branchBean.getFax()+"' , '"+branchBean.getStartDate()+"' , '"+branchBean.getEffectiveDate()+"' , "
					+loggedEmployee+" , '"+todaysDate+"' , "+branchBean.getFirstShiftFrom()+","+branchBean.getFirstShiftTo()+"," 
					+branchBean.getSecondShiftFrom()+","+branchBean.getSecondShiftTo()+","+branchBean.getFirstCashFrom()+", "
					+branchBean.getFirstCashTo()+","+branchBean.getSecondCashFrom()+" , "+branchBean.getSecondCashTo()+",'" 
					+branchBean.getWoffday()+"','"+branchBean.getAltersat()+"', '"+branchBean.getSiteStatus()+"',"+branchBean.getIsDeleted()+" )");
     flag=true;
			connection.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return flag ;
	}
	public boolean updateBranchDetails(BranchBean branchBean,String todaysDate,int loggedEmployee,int siteIsdeleted)
	{
		Statement statement = null;
		connection = ConnectionManager.getConnection();
        Boolean flag=false;
		try
		{
			statement = connection.createStatement();
			statement.executeUpdate("update Project_Sites set SITE_NAME='"+branchBean.getBranchName()+"',ADDR1='"+branchBean.getAddress1()+"',ADDR2='"+branchBean.getAddress2()+"',ADDR3='"+branchBean.getAddress3()+"',SITE_CITY= "
					+branchBean.getCity()+",SITE_STATE="+branchBean.getState()+",PINCD="+branchBean.getPincode()+"" +
					",PHONE1='"+branchBean.getPhone1()+"' ,PHONE2='"+branchBean.getPhone2()+"' ,FAX='"+branchBean.getFax()+"' ,START_DT='"
					+branchBean.getStartDate()+"',EDATE='"+branchBean.getEffectiveDate()+"',UPDATED_BY="	+loggedEmployee+",UPDATED_DATE='"
					+todaysDate+"',I_SHIFT_F="+branchBean.getFirstShiftFrom()+",I_SHIFT_T="+branchBean.getFirstShiftTo()+",II_SHIFT_F=" 
							+branchBean.getSecondShiftFrom()+",II_SHIFT_T="+branchBean.getSecondShiftTo()+",I_CASH_F="+branchBean.getFirstCashFrom()+",I_CASH_T= "
							+branchBean.getFirstCashTo()+",II_CASH_F="+branchBean.getSecondCashFrom()+" ,II_CASH_T= "+branchBean.getSecondCashTo()+",WEEK_OFF='" 
							+branchBean.getWoffday()+"',ALTER_SAT='"+branchBean.getAltersat()+"',SITE_STATUS='"+branchBean.getSiteStatus()+"',SITE_ISDELETED="+siteIsdeleted+"  " +
							" where SITE_ID= "+branchBean.getBranchCode()+" ");
			 flag=true;
			connection.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	/*public ArrayList<BranchBean> getBranchDetails()
	{
		connection=ConnectionManager.getConnection();
		Statement statement= null;
		ArrayList<BranchBean> branList = new ArrayList<BranchBean>();
		try
		{
			statement= connection.createStatement();
			resultSet=statement.executeQuery("select  top  1 * from Project_Sites order by CREATED_DATE DESC");
			BranchBean branchBean;
			while(resultSet.next())
			{
				branchBean=new BranchBean();
				branchBean.setBranchCode(resultSet.getInt("SITE_ID"));
				branchBean.setBranchName(resultSet.getString("SITE_NAME"));
				branchBean.setZone(resultSet.getString("SITE_LOCATION"));
				branchBean.setAddress1(resultSet.getString("ADDR1"));
				branchBean.setAddress2(resultSet.getString("ADDR2"));
				branchBean.setAddress3(resultSet.getString("ADDR3"));
				branchBean.setCity(resultSet.getInt("SITE_CITY"));
				branchBean.setState(resultSet.getInt("SITE_STATE"));
				branchBean.setPincode(resultSet.getInt("PINCD"));
				branchBean.setPhone1(resultSet.getString("PHONE1"));
				branchBean.setPhone2(resultSet.getString("PHONE2"));
				branchBean.setFax(resultSet.getString("FAX"));
				branchBean.setStartDate(resultSet.getString("START_DT"));
				branchBean.setEffectiveDate(resultSet.getString("EDATE"));
				branchBean.setFirstShiftFrom(resultSet.getDouble("I_SHIFT_F"));
				branchBean.setFirstShiftTo(resultSet.getDouble("I_SHIFT_T"));
				branchBean.setSecondShiftFrom(resultSet.getDouble("II_SHIFT_F"));
				branchBean.setSecondShiftTo(resultSet.getDouble("II_SHIFT_T"));
				branchBean.setFirstCashFrom(resultSet.getDouble("I_CASH_F"));
				branchBean.setFirstCashTo(resultSet.getDouble("I_CASH_T"));
				branchBean.setSecondCashFrom(resultSet.getDouble("II_CASH_F"));
				branchBean.setSecondCashTo(resultSet.getDouble("II_CASH_T"));
				branchBean.setWoffday(resultSet.getString("WEEK_OFF"));
				branchBean.setAltersat(resultSet.getString("ALTER_SAT"));

				branList.add(branchBean);
			}
			connection.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return branList;
	}
	*/
	public ArrayList<BranchBean> getBranchList() 
	{
		 String list="SELECT * FROM Project_Sites";
		//String list ="SELECT USERID FROM USERROLES WHERE USERID != USERID";
		 
		 //SELECT * FROM Users
		ArrayList<BranchBean> branchList=new ArrayList<BranchBean>();
		 try
		 {
			 
			Connection con=ConnectionManager.getConnection();
			
			Statement statement=con.createStatement();
			ResultSet resultSet=statement.executeQuery(list);
			
		
			
			
			while(resultSet.next())
			{
				BranchBean branchBean=new BranchBean();
				branchBean.setBranchCode(resultSet.getInt("SITE_ID"));
				branchBean.setBranchName(resultSet.getString("SITE_NAME"));
				branchBean.setZone(resultSet.getString("SITE_LOCATION"));
				branchBean.setCity(resultSet.getInt("SITE_CITY"));
				branchBean.setState(resultSet.getInt("SITE_STATE"));
				
				
				
				
			
				
				
				branchList.add(branchBean);
			}
			con.close();
			
		 }
		 catch (SQLException e) 
		 {
			e.printStackTrace();
		}
		return branchList;
	}
	
	public BranchBean getParticularBranchDetails(int branchCode)
	{
		connection=ConnectionManager.getConnection();
		Statement statement= null;
		BranchBean branchBean = null;
		try
		{
			statement= connection.createStatement();
			resultSet=statement.executeQuery("select   * from Project_Sites where SITE_ID="+branchCode+"");
			
			while(resultSet.next())
			{
				branchBean=new BranchBean();
				branchBean.setBranchCode(resultSet.getInt("SITE_ID"));
				branchBean.setBranchName(resultSet.getString("SITE_NAME"));
				branchBean.setZone(resultSet.getString("SITE_LOCATION"));
				branchBean.setAddress1(resultSet.getString("ADDR1"));
				branchBean.setAddress2(resultSet.getString("ADDR2"));
				branchBean.setAddress3(resultSet.getString("ADDR3"));
				branchBean.setCity(resultSet.getInt("SITE_CITY"));
				branchBean.setState(resultSet.getInt("SITE_STATE"));
				branchBean.setPincode(resultSet.getInt("PINCD"));
				branchBean.setPhone1(resultSet.getString("PHONE1"));
				branchBean.setPhone2(resultSet.getString("PHONE2"));
				branchBean.setFax(resultSet.getString("FAX"));
				branchBean.setStartDate(resultSet.getString("START_DT"));
				branchBean.setEffectiveDate(resultSet.getString("EDATE"));
				branchBean.setFirstShiftFrom(resultSet.getDouble("I_SHIFT_F"));
				branchBean.setFirstShiftTo(resultSet.getDouble("I_SHIFT_T"));
				branchBean.setSecondShiftFrom(resultSet.getDouble("II_SHIFT_F"));
				branchBean.setSecondShiftTo(resultSet.getDouble("II_SHIFT_T"));
				branchBean.setFirstCashFrom(resultSet.getDouble("I_CASH_F"));
				branchBean.setFirstCashTo(resultSet.getDouble("I_CASH_T"));
				branchBean.setSecondCashFrom(resultSet.getDouble("II_CASH_F"));
				branchBean.setSecondCashTo(resultSet.getDouble("II_CASH_T"));
				branchBean.setWoffday(resultSet.getString("WEEK_OFF"));
				branchBean.setAltersat(resultSet.getString("ALTER_SAT"));
               branchBean.setSiteStatus(resultSet.getString("SITE_STATUS"));
			
			}
			connection.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return branchBean;
	}
	
	
	
	
	
	public ArrayList<Lookup> getSTATES(String key)
	{
		ArrayList<Lookup> result=new ArrayList<Lookup>();
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM LOOKUP WHERE LKP_CODE='"+key+"' AND LKP_SRNO!=0  and LKP_SRNO in (select distinct site_state from Project_Sites )order by 2 asc");
			while(rs.next())
			{
				Lookup lkp=new Lookup();
				lkp.setLKP_CODE(rs.getString(1)!=null?rs.getString(1):"");
				lkp.setLKP_SRNO(rs.getString(2)!=null?rs.getInt(2):0);
				lkp.setLKP_DESC(rs.getString(3)!=null?rs.getString(3):"");
			//	lkp.setLKP_RECR(rs.getString(4)!=null?rs.getInt(4):0);
				result.add(lkp);
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public static String checkNull(String val,String res)
	{
		
		if(val.equals(null))
		{
			return "";
		}
		else
		{
			return res;
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
