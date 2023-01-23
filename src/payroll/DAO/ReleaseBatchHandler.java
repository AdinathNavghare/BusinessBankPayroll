package payroll.DAO;

import java.sql.*;
import java.util.ArrayList;

import payroll.Core.ErrorLog;
import payroll.Core.ReportDAO;


public class ReleaseBatchHandler {

	public ArrayList<Integer> getReleaseBatchList(String date)
	{
		ArrayList<Integer> releaseBatchList= new ArrayList<Integer>();
		
		try
		{
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			System.out.println("date inside getReleaseBatchList "+date);
			
			ResultSet resultSet = statement.executeQuery("select BATCHNO from RELEASE_BATCH " +
					"where TRNDT between '"+ReportDAO.BOM(date)+"' and  '"+ReportDAO.EOM(date)+"'   ");
			
			int batchNumber=0;
			while(resultSet.next())
			{
		batchNumber=resultSet.getString("BATCHNO")==null?0:resultSet.getInt("BATCHNO");
			releaseBatchList.add(batchNumber);
			}
			connection.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			ErrorLog errorLog=new ErrorLog();
			errorLog.errorLog("ERROR GETTING FROM RELEASE_BATCH  METHOD: GETRELEASEMETHOD. FOR PAGE: BANKSTATEMENT.jsp", e.toString());
		}
		return releaseBatchList;
	}
}
