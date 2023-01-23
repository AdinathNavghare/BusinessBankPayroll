package payroll.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import payroll.DAO.ProjectTimesheetHandler;


/**
 * Servlet implementation class ProjectTimesheet
 */
@WebServlet("/ProjectTimesheet")
public class ProjectTimesheet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProjectTimesheet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action").trim();

		ProjectTimesheetHandler projectTimesheetHandler = new ProjectTimesheetHandler();
		JSONObject output = new JSONObject();
		if (action.equalsIgnoreCase("getTaskNames")) {
			JSONArray result = new JSONArray();
			result = projectTimesheetHandler.getTaskNames();
			// output.put("data", result);
			response.setContentType("text/plain");
			response.getWriter().write(result.toString());
		}
		if (action.equalsIgnoreCase("getProjectNames")) {
			JSONArray result = new JSONArray();
			int employeeId = Integer.parseInt(request.getParameter("employeeId"));
			result = projectTimesheetHandler.getProjectNames(employeeId);
			// output.put("data", result);
			response.setContentType("text/plain");
			response.getWriter().write(result.toString());
		}
		if (action.equalsIgnoreCase("getWeekStartDates")) {
			JSONArray dates = new JSONArray();
			String monthName = request.getParameter("monthName").trim();
			dates = projectTimesheetHandler.weeksStartDatebyMonth(monthName);

			response.setContentType("text/plain");
			response.getWriter().write(dates.toString());
		}
		if (action.equalsIgnoreCase("addSheet")) {
			String startDate = request.getParameter("weekStartDate").trim();
			String empID = request.getParameter("empID").trim();
			LocalDate weekStartDate = LocalDate.parse(startDate);
			LocalDate weekEndDate = projectTimesheetHandler.getWeekEndDate(weekStartDate);

			projectTimesheetHandler.addSheetIntoTimeSheet(empID, weekStartDate, weekEndDate);

			response.sendRedirect("List_Project_Timesheet.jsp");
		}
		if (action.equalsIgnoreCase("listWeekTimesheet")) {
			JSONArray lst = new JSONArray();
			JSONObject res = new JSONObject();
			int empID = Integer.parseInt(request.getParameter("empID").trim());
			lst = projectTimesheetHandler.getListWeekDates(empID);
			res.put("data", lst);
			response.setContentType("text/plain");
			response.getWriter().write(res.toString());
		}
		if (action.equalsIgnoreCase("getHeadersProjectAndTaskNames")) {

			JSONObject res = new JSONObject();
			String startDate = request.getParameter("startDate").trim();
			String endDate = request.getParameter("endDate").trim();
			int employeeId = Integer.parseInt(request.getParameter("empID"));

			JSONArray headers = new JSONArray();
			headers = projectTimesheetHandler.getDatesInBetween(startDate, endDate);
			JSONArray taskNames = new JSONArray();
			taskNames = projectTimesheetHandler.getTaskNames();

			JSONArray projectNames = new JSONArray();
			projectNames = projectTimesheetHandler.getProjectNames(employeeId);

			res.put("headers", headers);
			res.put("taskNames", taskNames);
			res.put("projectNames", projectNames);

			response.setContentType("text/plain");
			response.getWriter().write(res.toString());
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
