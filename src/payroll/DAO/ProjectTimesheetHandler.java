package payroll.DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import payroll.Model.BranchBean;
import payroll.Model.Lookup;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;

public class ProjectTimesheetHandler {

	
	public JSONArray getTaskNames()
	{
		JSONArray result=new JSONArray();
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select distinct Task_Name from Task");
			while(rs.next())
			{
				/*JSONObject task = new JSONObject();
				task.put("taskName",rs.getString(1)!=null?rs.getString(1):"");*/
				
				result.add(rs.getString(1)!=null?rs.getString(1):"");
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONArray getProjectNames(int employeeId)
	{
		JSONArray result=new JSONArray();
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select P.ProjectName from AddProject P,Project_Allocation PA where P.ProjectId=PA.Project_Id and PA.Employee_Id="+employeeId);
			while(rs.next())
			{
				/*JSONObject projectName = new JSONObject();
				projectName.put("projectName",rs.getString(1)!=null?rs.getString(1):"");*/
				
				result.add(rs.getString(1)!=null?rs.getString(1):"");
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	
	
	
	
	public  JSONArray weeksStartDatebyMonth(String monthName) {
		 Month month1 = Month.valueOf(monthName.toUpperCase());
		 JSONArray dates=new JSONArray();
	        
		 
		 System.out.printf("For the month of %s:%n", month1);
	        LocalDate date = Year.now().atMonth(month1).atDay(1).
	        		with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
	        Month mi = date.getMonth();
	        while (mi == month1) {
	            System.out.printf("%s%n", date);
	            dates.add(date.toString());
	            date = date.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
	            mi = date.getMonth();
	            //dates.add(date);
	        }
	        System.out.println(dates.toString());
	        return dates;
	}

	public LocalDate getWeekEndDate(LocalDate weekStartDate) {
		LocalDate weekEndDate=weekStartDate;
		while (weekEndDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
			weekEndDate = weekEndDate.plusDays(1);
		   }
		return weekEndDate;
	}

	public void addSheetIntoTimeSheet(String empID, LocalDate weekStartDate, LocalDate weekEndDate) {
		try
		{
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			String Sql="INSERT INTO TimeSheet([EmployeeId],[StartDate],[EndDate],[Status],[LastUpdatedBy])VALUES('"+empID+"','"+weekStartDate+"','"+weekEndDate+"','C',getdate())";
			//System.out.println(Sql);
			st.executeUpdate(Sql);
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	public JSONArray getListWeekDates(int empID) {
		String listSql="select id,EmployeeId,startDate,EndDate,Status from TimeSheet where EmployeeId='"+empID+"' and Status='C'";

		JSONArray list=new JSONArray();
		//String list ="SELECT USERID FROM USERROLES WHERE USERID != USERID";
		 
		 //SELECT * FROM Users
		
		 try
		 {
			 
			Connection con=ConnectionManager.getConnection();
			
			Statement statement=con.createStatement();
			ResultSet resultSet=statement.executeQuery(listSql);
			
		
			
			
			while(resultSet.next())
			{
				JSONObject res=new JSONObject();
				res.put("id", resultSet.getInt("id"));
				res.put("EmployeeId", resultSet.getInt("EmployeeId"));
				res.put("startDate", resultSet.getString("startDate"));
				res.put("EndDate", resultSet.getString("EndDate"));
				res.put("Status", resultSet.getString("Status"));
				
				list.add(res);
			}
			con.close();
			
		 }
		 catch (SQLException e) 
		 {
			e.printStackTrace();
		}
	
		return list;
	}
	
public JSONArray getDatesInBetween(String startDate,String endDate){
		
		JSONArray lst=new JSONArray();
		LocalDate start = LocalDate.parse(startDate);
		LocalDate end = LocalDate.parse(endDate);
		
		
		//List<String> totalDates = new ArrayList<String>();
		while (!start.isAfter(end)) {
			lst.add(start.getDayOfMonth()+"  "+start.getDayOfWeek().name().substring(0,3));
		    start = start.plusDays(1);
		}
		return lst;
	}
	
	
}
