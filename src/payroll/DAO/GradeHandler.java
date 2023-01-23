package payroll.DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

import payroll.Core.ReportDAO;
import payroll.Core.Utility;

import payroll.Model.*;

public class GradeHandler 
{

	static int cnt = 0;
	
	public boolean addGrade(GradeBean GB)
	{
		boolean result=false;
		Connection con =null;
		try
		{
			con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			String sql="INSERT INTO GRADE VALUES("
						+GB.getPOSTCD()+","
						+"'"+GB.getALFACD()+"',"
						+"'"+GB.getDISC()+"',"
						+"'"+GB.getEFFDT()+"',"
						+GB.getBASIC()+","
						+GB.getINCR1()+","
						+GB.getNOY1()+","
						+GB.getINCR2()+","
						+GB.getNOY2()+","
						+GB.getINCR3()+","
						+GB.getNOY3()+","
						+GB.getINCR4()+","
						+GB.getNOY4()+","
						+GB.getINCR5()+","
						+GB.getNOY5()+","
						+GB.getINCR6()+","
						+GB.getNOY6()+","
						+GB.getEXG()+","
						+GB.getMED()+","
						+GB.getEDU()+","
						+GB.getLTC()+","
						+GB.getCLOSING()+","
						+GB.getCONV()+","
						+GB.getCASH()+","
						+GB.getCLG()+","
						+GB.getWASHING()+","
						+GB.getFLDWRK()
						+")";
			st.execute(sql);
			result=true;
			con.close();
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
		}
		return result;
	}
	
	public GradeBean getGrade(int postCode, String effDate)
	{
		GradeBean GB = null;
		Connection con=null;
		try
		{
			con = ConnectionManager.getConnection();
			Statement st= con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM GRADE WHERE POSTCD="+postCode+" AND EFFDT='"+effDate+"'");
			if(rs.next())
			{
				GB = new GradeBean();
				GB.setPOSTCD(rs.getString("POSTCD")==null?0:rs.getInt("POSTCD"));
				GB.setALFACD(rs.getString("ALFACD")==null?"":rs.getString("ALFACD"));
				GB.setDISC(rs.getString("DISC")==null?"":rs.getString("DISC"));
				GB.setEFFDT(rs.getDate("EFFDT")==null?"":Utility.dateFormat(rs.getDate("EFFDT")));
				GB.setBASIC(rs.getString("BASIC")==null?0:rs.getInt("BASIC"));
				GB.setINCR1(rs.getString("INCR1")==null?0:rs.getInt("INCR1"));
				GB.setNOY1(rs.getString("NOY1")==null?0:rs.getInt("NOY1"));
				GB.setINCR2(rs.getString("INCR2")==null?0:rs.getInt("INCR2"));
				GB.setNOY2(rs.getString("NOY2")==null?0:rs.getInt("NOY2"));
				GB.setINCR3(rs.getString("INCR3")==null?0:rs.getInt("INCR3"));
				GB.setNOY3(rs.getString("NOY3")==null?0:rs.getInt("NOY3"));
				GB.setINCR4(rs.getString("INCR4")==null?0:rs.getInt("INCR4"));
				GB.setNOY4(rs.getString("NOY4")==null?0:rs.getInt("NOY4"));
				GB.setINCR5(rs.getString("INCR5")==null?0:rs.getInt("INCR5"));
				GB.setNOY5(rs.getString("NOY5")==null?0:rs.getInt("NOY5"));
				GB.setINCR6(rs.getString("INCR6")==null?0:rs.getInt("INCR6"));
				GB.setNOY6(rs.getString("NOY6")==null?0:rs.getInt("NOY6"));
				GB.setEXG(rs.getString("EXG")==null?0:rs.getInt("EXG"));
				GB.setMED(rs.getString("MED")==null?0:rs.getInt("MED"));
				GB.setEDU(rs.getString("EDU")==null?0:rs.getInt("EDU"));
				GB.setLTC(rs.getString("LTC")==null?0:rs.getInt("LTC"));
				GB.setCLOSING(rs.getString("CLOSING")==null?0:rs.getInt("CLOSING"));
				GB.setCONV(rs.getString("CONV")==null?0:rs.getInt("CONV"));
				GB.setCASH(rs.getString("CASH")==null?0:rs.getInt("CASH"));
				GB.setCLG(rs.getString("CLG")==null?0:rs.getInt("CLG"));
				GB.setWASHING(rs.getString("WASHING")==null?0:rs.getInt("WASHING"));
				GB.setFLDWRK(rs.getString("FLDWRK")==null?0:rs.getInt("FLDWRK"));
			}
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
		}
		return GB;
	}
	
	
	public boolean addGradeData(GradeBean gradeBean)
	{
		boolean result=false;
		Connection connection =null;
		try
		{

			connection=ConnectionManager.getConnection();
			Statement statement=connection.createStatement();


			
/*
			String query=("INSERT INTO GRADE_MASTER (SRNO,GRADE_CODE,START_DATE,END_DATE,BASIC,DA,HRA,CREATION_DATE,GRADE_STATUS,DA_TYPE,HRA_TYPE,DA_VAL,HRA_VAL,INCREMENT)VALUES("+gradeBean.getSerialNumber()+","+
					gradeBean.getGradeCode()+",'"+
					gradeBean.getStartDate()+"','"+
					gradeBean.getEndDate()+"',"+
					gradeBean.getBasic()+","+
					gradeBean.getDaPercentOrFixedValue()+","+
					gradeBean.getHraPercentOrFixedValue()+","+
				
					gradeBean.getCreationDate()+"',0,"+
					gradeBean.getDaValueType() +","+
					gradeBean.getHraValueType()+","+
					
					gradeBean.getDaValue()+","+
					gradeBean.getHraValue()+","+		
					gradeBean.getIncrement()+")");	*/



			//System.out.println("find  it "+query);


						String sql=("INSERT INTO GRADE_MASTER (SRNO,GRADE_CODE,START_DATE,END_DATE,BASIC,DA,HRA,CREATION_DATE,GRADE_STATUS,DA_TYPE,HRA_TYPE,DA_VAL,HRA_VAL,INCREMENT)VALUES("+gradeBean.getSerialNumber()+","+
						gradeBean.getGradeCode()+",'"+
						gradeBean.getStartDate()+"', '2098-12-31' , "+
						gradeBean.getBasic()+","+
						gradeBean.getDaPercentOrFixedValue()+","+
						gradeBean.getHraPercentOrFixedValue()+",'"+
						gradeBean.getCreationDate()+"',0,"+
					
						gradeBean.getDaValueType() +","+
						gradeBean.getHraValueType()+","+
						gradeBean.getDaValue()+","+
						gradeBean.getHraValue()+","+
						gradeBean.getIncrement()+ ")");

			statement.execute(sql);
			
			result=true;
			connection.close();
		}
		catch (SQLException e)
		{
			
				e.printStackTrace();
			}

		
		return result;
	}

	public GradeBean getGradeData(String  gradeCode,int index)
	{
		GradeBean gradeBean = new GradeBean();;
		Connection connection=null;
		//   System.out.println("inside grade data");
		try
		{
		
			connection = ConnectionManager.getConnection();
			Statement statement= connection.createStatement();
			//	System.out.println("the grade is"+gradeCode);
			System.out.println("SELECT * FROM GRADE_MASTER WHERE GRADE_CODE='"+gradeCode+"' and SRNO="+index+" AND GRADE_STATUS=0  " );
			ResultSet resultSet = statement.executeQuery("SELECT * FROM GRADE_MASTER WHERE GRADE_CODE='"+gradeCode+"' and SRNO="+index+" AND GRADE_STATUS=0  " );
			while(resultSet.next())
			{
         
				gradeBean.setSerialNumber (resultSet.getInt("SRNO"));
				gradeBean.setGradeCode(resultSet.getInt("GRADE_CODE"));
				gradeBean.setStartDate(resultSet.getString("START_DATE"));
				gradeBean.setEndDate (resultSet.getString("END_DATE"));
				gradeBean.setBasic(resultSet.getFloat("BASIC"));
				gradeBean.setDaPercentOrFixedValue(resultSet.getFloat("DA"));
				gradeBean.setHraPercentOrFixedValue(resultSet.getFloat("HRA"));
			
				gradeBean.setGradeStatus(resultSet.getInt("GRADE_STATUS"));
				gradeBean.setDaValueType(resultSet.getInt("DA_TYPE"));
				gradeBean.setHraValueType(resultSet.getInt("HRA_TYPE"));
			
				gradeBean.setDaValue(resultSet.getFloat("DA_VAL"));
				gradeBean.setHraValue(resultSet.getFloat("HRA_VAL"));
				gradeBean.setVdaValue(resultSet.getFloat("VDA_VAL"));
				gradeBean.setIncrement(resultSet.getFloat("INCREMENT"));
			
			}
	
			connection.close();
		}
		catch(SQLException e)
		{
			try 
			{
				connection.close();
			}
			catch (SQLException e1)
			{
				e.printStackTrace();
			}
		}
		return gradeBean;
	}

	public boolean deleteGrade(int post, String effdt)
	{
		boolean flag = false;
		Connection con=null;
		try
		{
			con = ConnectionManager.getConnection();
			Statement st= con.createStatement();
			st.execute("DELETE FROM GRADE WHERE POSTCD="+post+" AND EFFDT='"+effdt+"'");
			flag = true;
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
		}
		return flag;
	}


	public static String GetGrade(int grade,String alpha,RepoartBean repBean)
	{
		String result = "";
		ResultSet resultSet = null;
		Statement st;
		try
		{
			st= repBean.getCn().createStatement();
			resultSet = st.executeQuery("SELECT *  FROM GRADE WHERE POSTCD="+grade);
			if(resultSet.next())
			{
				if(alpha.equalsIgnoreCase("Y"))
				{
					result = resultSet.getString("ALFACD");
				}
				else
				{
					result = resultSet.getString("DISC");
				}
			}
			else
			{
				result = "NOT FOUND";
			}
			resultSet.close();
			st.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	public String getMaxEndDate(String type){

		Connection connection =null;
		String date="";
		try
		{

			connection=ConnectionManager.getConnection();
			Statement statement=connection.createStatement();
			ResultSet resultSet= statement.executeQuery("SELECT MAX(END_DATE)  END_DATE FROM GRADE_MASTER WHERE GRADE_CODE='"+type+ "' ");
			if(resultSet.next()){
				date=resultSet.getString("END_DATE");
				connection.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return date;

	}

	public boolean saveEndDate(String endDate,String startDate,int gradeCode) {
		Connection connection =null;
		boolean flag=false;
		int status=1;
		try
		{

			connection=ConnectionManager.getConnection();
			Statement statement=connection.createStatement();
			
		
		/*statement.executeUpdate("UPDATE GRADE_MASTER SET END_DATE='"+endDate+"' , GRADE_STATUS="+status+" WHERE START_DATE='"+startDate+"' AND   GRADE_CODE="+gradeCode+" ");*/
			statement.executeUpdate("UPDATE GRADE_MASTER SET END_DATE='"+endDate+"'  WHERE START_DATE='"+startDate+"' AND   GRADE_CODE="+gradeCode+" ");
			flag=true;
			connection.close();
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	//	System.out.println("the flag value is "+flag);
		return flag;
	}
	
	
	
	public ArrayList<DAGradeBean> getGradeDetails() throws ParseException
	{
		ArrayList<DAGradeBean> gradeList= new ArrayList<DAGradeBean>();
		
		Connection connection=null;
		 int count=0;
		try
		{
			connection = ConnectionManager.getConnection();
			Statement statement= connection.createStatement();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			 SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MMM-yyyy"); 
			 String newStartDate="";
			ResultSet resultSet = statement.executeQuery("SELECT * FROM GRADE_MASTER WHERE GRADE_STATUS=0 order by srno ");
			while(resultSet.next())
			{
				DAGradeBean gradeBean = new DAGradeBean();
				gradeBean.setSerialNumber (resultSet.getInt("SRNO"));
				gradeBean.setGradeCode(resultSet.getInt("GRADE_CODE"));
			//	gradeBean.setStartDate(resultSet.getString("START_DATE"));
		
				 Date date =simpleDateFormat.parse(resultSet.getString("START_DATE")); 
					

				 Calendar calendar = Calendar.getInstance();
				 calendar.setTime(date);
				 
				 calendar.add(Calendar.MONTH, 3);
				 date= calendar.getTime();
				 
				 newStartDate=  outputDateFormat.format(date);
				 
				 gradeBean.setStartDate(newStartDate);
				 //System.out.println("the new start date"+newStartDate);
				 gradeBean.setEndDate("31-12-2098");
				
				gradeBean.setBasic(resultSet.getFloat("BASIC"));
				gradeBean.setCreationDate(ReportDAO.getServerDate());
				gradeBean.setGradeStatus(0);
				gradeBean.setDaValueType(resultSet.getInt("DA_TYPE"));
				gradeBean.setDaValue(resultSet.getFloat("DA_VAL"));
				gradeBean.setDa(0);
				gradeList.add(gradeBean);
				count++;
			}
				System.out.println("count"+count);
			connection.close();
		}
		catch(SQLException e)
		{
			try 
			{
				connection.close();
				e.printStackTrace();
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
	
	
		return gradeList;
	
	
	
	}
	

	public boolean checkForCalculated(String month) {
		Connection connection =null;
		boolean flag=false;
		
		//System.out.println("month is :-"+month);
		try
		{

			connection=ConnectionManager.getConnection();
			Statement statement=connection.createStatement();
			String query="SELECT VDA_VAL FROM GRADE_MASTER WHERE START_DATE='"+month+" WHERE GRADE_CODE=1 '";
			ResultSet resultSet= statement.executeQuery(query);
			int vdaValue=0;
			vdaValue=resultSet.getInt("VDA_VAL");
			if(vdaValue!=0)
			flag=true;
			System.out.println("flag"+flag);
			connection.close();
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	//	System.out.println("the flag value is "+flag);
		return flag;
	}
	
	public ArrayList<DAGradeBean> getGradeDetails(String basic)
	{
		ArrayList<DAGradeBean> gradeList= new ArrayList<DAGradeBean>();
		
		Connection connection=null;
		 int count=0;
		try
		{
			connection = ConnectionManager.getConnection();
			Statement statement= connection.createStatement();
		
			ResultSet resultSet = statement.executeQuery("SELECT * FROM DA_GRADE_MASTER WHERE GRADE_CODE='"+basic+"' AND GRADE_STATUS=0 order by srno ");
			System.out.println("SELECT * FROM DA_GRADE_MASTER WHERE GRADE_CODE='"+basic+"' AND GRADE_STATUS=0 order by srno ");
			while(resultSet.next())
			{
				DAGradeBean gradeBean = new DAGradeBean();
				gradeBean.setSerialNumber (resultSet.getInt("SRNO"));
				gradeBean.setGradeCode(resultSet.getInt("GRADE_CODE"));
				gradeBean.setStartDate(resultSet.getString("START_DATE"));
				gradeBean.setEndDate (resultSet.getString("END_DATE"));
				gradeBean.setBasic(resultSet.getFloat("BASIC"));
				gradeBean.setGradeStatus(resultSet.getInt("GRADE_STATUS"));
				gradeBean.setDaValueType(resultSet.getInt("DA_TYPE"));
				gradeBean.setDaValue(resultSet.getFloat("DA_VAL"));
			
				gradeList.add(gradeBean);
				count++;
			}
				//System.out.println("count"+count);
			connection.close();
		}
		catch(SQLException e)
		{
			try 
			{
				connection.close();
				e.printStackTrace();
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
	
	
		return gradeList;
	
	
	
	}
	

}