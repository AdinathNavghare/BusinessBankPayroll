package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import payroll.Core.ErrorLog;
import payroll.Core.ReportDAO;
import payroll.Model.DAGradeBean;
import payroll.Model.VdaBean;
/*import payroll.Model.BranchBean;
import payroll.Model.EmpAddressBean;
import payroll.Model.RoleBean;*/

public class VdaDAO {
	ErrorLog errorLog=new ErrorLog();
	Connection connection = null;
	Statement statement=null;
	ResultSet resultSet=null;
	Boolean flag=false;
	public boolean addVda(VdaBean vdaBean,String todaysDate,int loggedEmployee){
		try
		{
			
			connection = ConnectionManager.getConnection();
			statement= connection.createStatement();
			statement.execute("INSERT INTO DAMAST(DA_APPLICABLE_DATE,FIX_BASIC,DA_DIVISOR,CPI_INDEX" +
					",DA_DATE,MONTH,INDEX_FOR," +
					"CREATED_BY,CREATED_DATE,END_DATE,CFPI_INDEX,BATCH_ID) VALUES('"+vdaBean.getDaApplicableDate()+"' ," 
							+vdaBean.getFixBasic()+" , "+vdaBean.getDaDivisor()+" , " 
					+vdaBean.getCPIValue()+" , '"+vdaBean.getVdaDate()+"' , '"+vdaBean.getMonth()+"' , "
					+vdaBean.getIndex()+" , "+loggedEmployee+" , '"+todaysDate+"' ,  '"+vdaBean.getEndDate()+"' , "
					+vdaBean.getCFPIValue()+","+vdaBean.getBatchId()+" )");
     flag=true;
     addDAGradeMaster(todaysDate);
			connection.close();
		}
		catch(Exception e)
		{
			System.out.println("inside catch");
			e.printStackTrace();
			
			errorLog.errorLog("VdaDAO: ERROR INSERTING IN DAMAST METHOD: ADDVDA. FOR PAGE: VDA.jsp", e.toString());
		}
		
		return flag ;
	}
	
	
	public boolean addDAGradeMaster(String date) throws SQLException {
		boolean flagForQuery=false;
		try{
		connection = ConnectionManager.getConnection();
		statement= connection.createStatement();
		String StartDate = ReportDAO.BOM(date);
		int SerialNumber=0;
		
		ResultSet resultSet= statement.executeQuery("SELECT MAX(SRNO) SRNO FROM DA_GRADE_MASTER ");
		if(resultSet.next()){
			SerialNumber=resultSet.getInt("SRNO");
		}
		SerialNumber=SerialNumber+1;
			String sql=("INSERT INTO DA_GRADE_MASTER (SRNO,GRADE_CODE,START_DATE,END_DATE,BASIC,CREATION_DATE,GRADE_STATUS,DA,DA_TYPE,DA_VAL) VALUES" +
					"("+SerialNumber+",1,'"+StartDate+"', '2098-12-31' , 0, '"+date+"',0,0,0,0)");
			
			System.out.println(sql);
		   statement.execute(sql);
		   flagForQuery=true;  
			
	statement.close();
	connection.close();
	}catch(Exception e)
	{
		e.printStackTrace();
		errorLog.errorLog("VdaDAO: ERROR FINALIZING THE SLAB IN DAMAST METHOD: finalizeTheSlab(). FOR PAGE: VDA_Posting.jsp", e.toString());
	} 
		return flagForQuery;
	}

	public int getMaxBatchId(){

		Connection connection =null;
		int batchId=0;
		try
		{

			connection=ConnectionManager.getConnection();
			Statement statement=connection.createStatement();
			ResultSet resultSet= statement.executeQuery("SELECT MAX(BATCH_ID)  BATCH_ID FROM DAMAST ");
			if(resultSet.next()){
				batchId=resultSet.getInt("BATCH_ID");
				connection.close();
			}
			else 
				return 0;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			
			errorLog.errorLog("VdaDAO: ERROR  IN GETTING MAX BATCHID FROM DAMAST METHOD: getMaxBatchId(). FOR PAGE: VDA.jsp", e.toString());
		}
		System.out.println("batchid"+batchId);
		return batchId;

	}
	
	public ArrayList<VdaBean> getAllAddedVdaDetails(){
		VdaBean vdaBean=null;
		ArrayList<VdaBean> vdaList=new ArrayList<VdaBean>();
		Connection connection =null;
		Statement statement=null;
		ResultSet resultSet=null;
		try {
		 
		 connection=ConnectionManager.getConnection();
			 statement=connection.createStatement();
			 resultSet= statement.executeQuery("SELECT BATCH_ID,DA_APPLICABLE_DATE,MONTH,INDEX_FOR FROM DAMAST WHERE CALC_FLAG IS NULL" +
			 		" ORDER BY MONTH DESC");
			while(resultSet.next()){
				vdaBean=new VdaBean();
				vdaBean.setBatchId(resultSet.getInt("BATCH_ID"));		
				vdaBean.setDaApplicableDate(resultSet.getString("DA_APPLICABLE_DATE"));
				vdaBean.setMonth(resultSet.getString("MONTH"));
				vdaBean.setIndex(resultSet.getFloat("INDEX_FOR"));
				vdaList.add(vdaBean);

			}
			connection.close();
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		
		errorLog.errorLog("VdaDAO: ERROR  IN GETTING RECORDS OF ALL ADDED VDA DAMAST METHOD: getAllAddedVdaDetails(). FOR PAGE: VDA.jsp", e.toString());
	}
		
	
		return vdaList;
		
	}
	
	
	public VdaBean getLastAddedVdaDetails(){
		VdaBean vdaBean=null;
		
		Connection connection =null;
		Statement statement=null;
		ResultSet resultSet=null;
		try {
		 
		 connection=ConnectionManager.getConnection();
			 statement=connection.createStatement();
			 resultSet= statement.executeQuery("SELECT * FROM DAMAST WHERE MONTH=(SELECT MAX(MONTH) FROM DAMAST)");
			while(resultSet.next()){
				vdaBean=new VdaBean();
				vdaBean.setBatchId(resultSet.getInt("BATCH_ID"));			
				vdaBean.setVdaDate(resultSet.getString("DA_DATE"));
				vdaBean.setMonth(resultSet.getString("MONTH"));
				vdaBean.setDaApplicableDate(resultSet.getString("DA_APPLICABLE_DATE"));
				vdaBean.setEndDate(resultSet.getString("END_DATE"));
				
				
				vdaBean.setIndex(resultSet.getFloat("INDEX_FOR"));

			}
			connection.close();
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		
		errorLog.errorLog("VdaDAO: ERROR  IN GETTING RECORDS OF LAST ADDED VDA DAMAST METHOD: getLastAddedVdaDetails(). FOR PAGE: VDA.jsp", e.toString());
	}
		
	
		return vdaBean;
		
	}
	
	public VdaBean getTOP3Vda(String todaysDate)
	{
		
		VdaBean vdaBean=new VdaBean();
		connection = ConnectionManager.getConnection();
		try
		{
			double temp=0.0;
			double temp1=0.0;
			statement= connection.createStatement();
			ResultSet rs=statement.executeQuery("SELECT TOP 3 * from DAMAST where finalize_flag=1 order by MONTH desc ");
			
			while(rs.next())
			{	
			vdaBean.setDaApplicableDate(rs.getString("DA_APPLICABLE_DATE")); 
			vdaBean.setFixBasic(rs.getFloat("FIX_BASIC"));
			vdaBean.setDaDivisor(rs.getFloat("DA_DIVISOR"));
			vdaBean.setCPIValue(rs.getFloat("CPI_INDEX"));
			vdaBean.setVdaDate(rs.getString("DA_DATE"));
			vdaBean.setMonth(rs.getString("MONTH"));
			vdaBean.setIndex(rs.getFloat("INDEX_FOR"));
			temp=temp+rs.getFloat("INDEX_FOR");
			vdaBean.setStatus(rs.getString("STATUS"));
			vdaBean.setEndDate(rs.getString("END_DATE"));
			vdaBean.setCFPIValue(rs.getFloat("CFPI_INDEX"));
			}
			
			vdaBean.setPrev_vdaIndexAvg(temp/3);
			
		
			connection.close();
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
		
			errorLog.errorLog("VdaDAO :ERROR SELECTING TOP 3 RECORDS FROM DAMAST METHOD: getTOP3Vda(). FOR PAGE: VDA.jsp", e.toString());
		}
		return vdaBean;
		
		
	}
	
	public boolean checkVdaCalculatedFlag (String date) throws SQLException{
		boolean flag=false;


		connection = ConnectionManager.getConnection();
	  System.out.println("DATE"+date);
		try{
	
			statement= connection.createStatement();
		
			
			
		ResultSet resultSet=statement.executeQuery("SELECT CALC_FLAG FROM DAMAST WHERE MONTH='"+date+"   '     ");
	
		if(resultSet.next())
		{
			int result=resultSet.getInt("CALC_FLAG");
		
			if(result==1)
				flag=true;
			}
	
		connection.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
			errorLog.errorLog("VdaDAO :ERROR CHECKING FOR  RECORDS IN DAMAST METHOD: checkVdaCalculatedFlag(). FOR PAGE: VDA.jsp", e.toString());
		}
		return flag;
		
	}
	
	  
	  public boolean checkForRecordsInGradeFlag (String date) throws SQLException{
			boolean flag=false;

			
			connection = ConnectionManager.getConnection();
		
			try{
		
				statement= connection.createStatement();		
				
			ResultSet resultSet=statement.executeQuery("select DA_APPLICABLE_DATE from DAMAST where '"+date+"' in (select start_date from DA_GRADE_MASTER)	   ");
			System.out.println("helloo     select DA_APPLICABLE_DATE from DAMAST where '"+date+"' in (select start_date from DA_GRADE_MASTER)	"    );
			if(resultSet.next())
			{
				flag=true;
			}
			
			connection.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			
				errorLog.errorLog("VdaDAO:ERROR CHECKING FOR  RECORDS IN DA_GRADE_MASTER METHOD: checkForRecordsInGradeFlag(). FOR PAGE: VDA.jsp", e.toString());
			}
			return flag;
			
		}
	  
	  
	  public ArrayList<VdaBean> checkForFinalizedRecords() throws SQLException{
			
			VdaBean vdaBean=null;
			ArrayList<VdaBean> vdaList= new ArrayList<VdaBean> ();

			
			connection = ConnectionManager.getConnection();
		
			try{
		
				statement= connection.createStatement();
			
			ResultSet resultSet=statement.executeQuery("select MONTH,DA_APPLICABLE_DATE,BATCH_ID, INDEX_FOR from DAMAST where finalize_flag is NULL ");
		
			while(resultSet.next())
			{
				vdaBean=new VdaBean();
				vdaBean.setMonth(resultSet.getString("MONTH"));
				vdaBean.setBatchId(resultSet.getInt("BATCH_ID"));
				vdaBean.setDaApplicableDate(resultSet.getString("DA_APPLICABLE_DATE"));
				
				vdaBean.setIndex(resultSet.getFloat("INDEX_FOR"));
				
				vdaList.add(vdaBean);
			}
			
			connection.close();
			}
			catch(Exception e)
			{				
				e.printStackTrace();
				
				errorLog.errorLog("VdaDAO: ERROR CHECKING FOR FINALIZE RECORDS IN DAMAST METHOD: checkForFinalizedRecords(). FOR PAGE: VDA.jsp", e.toString());
			}
			return vdaList;
			
		}
	  
	  //FOR GETTING VDA RECORDS FOR EDITING
	  
	  public  ArrayList<VdaBean> getVdaDetailsForEditing (String date) throws SQLException{


			VdaBean vdaBean=null;
					
			ArrayList<VdaBean> vdaList= new ArrayList<VdaBean> ();
			connection = ConnectionManager.getConnection();
			try{
		
				statement= connection.createStatement();
			
			ResultSet resultSet=statement.executeQuery("select *  from DAMAST where DA_APPLICABLE_DATE='"+date+"'  ");
		
			while (resultSet.next())
			{
				vdaBean= new VdaBean();
				vdaBean.setDaApplicableDate(resultSet.getString("DA_APPLICABLE_DATE")); 
				vdaBean.setFixBasic(resultSet.getFloat("FIX_BASIC"));
				vdaBean.setDaDivisor(resultSet.getFloat("DA_DIVISOR"));
				vdaBean.setCPIValue(resultSet.getFloat("CPI_INDEX"));
				vdaBean.setVdaDate(resultSet.getString("DA_DATE"));
				vdaBean.setMonth(resultSet.getString("MONTH"));
				vdaBean.setIndex(resultSet.getFloat("INDEX_FOR"));
			//	temp=temp+rs.getFloat("INDEX_FOR");
				vdaBean.setStatus(resultSet.getString("STATUS"));
				vdaBean.setEndDate(resultSet.getString("END_DATE"));
				vdaBean.setCFPIValue(resultSet.getFloat("CFPI_INDEX"));
				vdaList.add(vdaBean);
			}
		
			statement.close();
			connection.close();
			}
			catch(Exception e)
			{
				
				e.printStackTrace();
				
				errorLog.errorLog("VdaDAO:ERROR GETTING DATA FROM DAMAST METHOD: getVdaDetailsForEditing(). FOR PAGE: VDA.jsp", e.toString());
			}
			return vdaList;
			
		}
	  
	  //FOR DELETEING VDA RECORDS 
	  public  void deleteLastSavedVdaRecords (String date) throws SQLException{
		try{
			connection = ConnectionManager.getConnection();
			statement= connection.createStatement();
		statement.executeUpdate("delete DAMAST where DA_APPLICABLE_DATE='"+date+"'  ");
		statement.close();
		connection.close();
		}catch(Exception e)
		{
			
			e.printStackTrace();
			
			errorLog.errorLog("VdaDAO: ERROR DELETING DATA FROM DAMAST METHOD: deleteLastSavedVdaRecords(). FOR PAGE: VDA.jsp", e.toString());
		}
		
		
	  }
	public boolean finalizeTheSlab(String date) throws SQLException {
		boolean flagForQuery=false;
		try{
		connection = ConnectionManager.getConnection();
		statement= connection.createStatement();
		System.out.println("date : "+date);
		String query="update DAMAST set FINALIZE_FLAG=1 where DA_APPLICABLE_DATE='"+date+"'";  
		 statement.executeUpdate(query);
		ArrayList<DAGradeBean> gradeList= new ArrayList<DAGradeBean>();
	
		GradeHandler gradeHandler=new GradeHandler();
		
		gradeList=gradeHandler.getGradeDetails();
		//changed by ak nikam./
		System.out.println("UPDATEdf/lkj " );
		System.out.println("UPDATE DA_GRADE_MASTER SET  END_DATE=DATEADD(day,-1,(DATEADD(MONTH ,3,'"+date+"'))), GRADE_STATUS=1 WHERE GRADE_STATUS=0");
		 statement.executeUpdate("UPDATE DA_GRADE_MASTER SET  END_DATE=DATEADD(day,-1,(DATEADD(MONTH ,3,'"+date+"'))), GRADE_STATUS=1 WHERE GRADE_STATUS=0");
		for (DAGradeBean gradeBean :gradeList)
		{
			

			String sql=("INSERT INTO DA_GRADE_MASTER (SRNO,GRADE_CODE,START_DATE,END_DATE,BASIC,CREATION_DATE,GRADE_STATUS,DA,DA_TYPE,DA_VAL) VALUES" +
					"("+gradeBean.getSerialNumber()+","+gradeBean.getGradeCode()+",'"+gradeBean.getStartDate()+"'," +
							" '2098-12-31' , "+gradeBean.getBasic()+","+gradeBean.getCreationDate()+"',0,"+gradeBean.getDa()+","+
							gradeBean.getDaValueType()+","+gradeBean.getDaValue()+")");
			
			System.out.println(sql);
		   statement.addBatch(sql);
		  
		}
	
		
	statement.executeBatch();	
	statement.close();
	connection.close();
	}catch(Exception e)
	{
		
		e.printStackTrace();
		
		errorLog.errorLog("VdaDAO: ERROR FINALIZING THE SLAB IN DAMAST METHOD: finalizeTheSlab(). FOR PAGE: VDA_Posting.jsp", e.toString());
	} 
		flagForQuery=true;
		return flagForQuery;
	}
	  
	
		public VdaBean getTOP3VdaForLatest(String todaysDate)
	{
		
		VdaBean vdaBean=new VdaBean();
		connection = ConnectionManager.getConnection();
		try
		{
			double temp=0.0;
			double temp1=0.0;
			statement= connection.createStatement();
			//ResultSet rs=statement.executeQuery("SELECT TOP 3 * from DAMAST where finalize_flag IS NULL order by MONTH desc ");
			ResultSet rs=statement.executeQuery("SELECT TOP 1 * from DAMAST where finalize_flag IS NULL order by MONTH desc ");
			
			while(rs.next())
			{	
			vdaBean.setDaApplicableDate(rs.getString("DA_APPLICABLE_DATE")); 
			vdaBean.setFixBasic(rs.getFloat("FIX_BASIC"));
			vdaBean.setDaDivisor(rs.getFloat("DA_DIVISOR"));
			vdaBean.setCPIValue(rs.getFloat("CPI_INDEX"));
			vdaBean.setVdaDate(rs.getString("DA_DATE"));
			vdaBean.setMonth(rs.getString("MONTH"));
			vdaBean.setIndex(rs.getFloat("INDEX_FOR"));
			temp=temp+rs.getFloat("INDEX_FOR");
			vdaBean.setStatus(rs.getString("STATUS"));
			vdaBean.setEndDate(rs.getString("END_DATE"));
			vdaBean.setCFPIValue(rs.getFloat("CFPI_INDEX"));
			}
			
			//vdaBean.setPrev_vdaIndexAvg(temp/3);
			vdaBean.setPrev_vdaIndexAvg(temp);
			
		
			connection.close();
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
		
			errorLog.errorLog("VdaDAO :ERROR SELECTING TOP 3 RECORDS FROM DAMAST METHOD: getTOP3Vda(). FOR PAGE: VDA.jsp", e.toString());
		}
		return vdaBean;
		
		
	}
	
		
}