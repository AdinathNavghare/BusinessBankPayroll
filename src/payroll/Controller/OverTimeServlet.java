package payroll.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import payroll.DAO.OvertimeHandler;
import payroll.Model.Overtimebean;

/**
 * Servlet implementation class OverTimeServlet
 */
@WebServlet("/OverTimeServlet")
public class OverTimeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OverTimeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		String action = request.getParameter("action");
		OvertimeHandler overthand = new OvertimeHandler();
		if (action.equalsIgnoreCase("deleteOT")) 
		{
			boolean flag = false;
			Overtimebean otbean = new Overtimebean();
			String[] str1 = request.getParameter("key").split(":");
			int empno = Integer.parseInt(str1[0]);
			String otdate = str1[1];
			String shiftcode = str1[2];
			otbean.setEMPNO(empno);
			otbean.setOtdate(otdate);
			otbean.setShiftcode(shiftcode);
			flag = overthand.deleteOTrecord(otbean);
			if (flag)
			{
				response.sendRedirect("overtimeMaintainance.jsp?empno=" + empno	+ "&flag=3");
			}
			else 
			{
				response.sendRedirect("overtimeMaintainance.jsp?empno=" + empno	+ "&flag=4");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		String action = request.getParameter("action");
		OvertimeHandler overthand = new OvertimeHandler();
		if (action.equalsIgnoreCase("addovertime")) 
		{
			Overtimebean overtime = new Overtimebean();
			String EMPNO = request.getParameter("EMPNO");
			StringTokenizer st = new StringTokenizer(EMPNO, ":");
			while (st.hasMoreTokens()) 
			{
				EMPNO = st.nextToken();
			}
			int empno = Integer.parseInt(EMPNO);
			String otdate = request.getParameter("otdate");
			String shiftcode = request.getParameter("shiftcode");
			String fromhrs = request.getParameter("fromhrs");
			String frommin = request.getParameter("fromMin");
			String fromtime = fromhrs + ":" + frommin;
			String tohrs = request.getParameter("tohrs");
			String tomin = request.getParameter("tomin");
			String totime = tohrs + ":" + tomin;
			float hours = Float.parseFloat(request.getParameter("hours"));
			overtime.setEMPNO(empno);
			overtime.setOtdate(otdate);
			overtime.setShiftcode(shiftcode);
			overtime.setTotime(totime);
			overtime.setFromtime(fromtime);
			overtime.setHOURS(hours);
			boolean flag = false;
			flag = overthand.addOvertime(overtime);
			if (flag) 
			{
				response.sendRedirect("overtimeMaintainance.jsp?empno=" +empno+ "&flag=1");
			}
			else
			{
				response.sendRedirect("overtimeMaintainance.jsp?flag=0");
			}
		}
	}
}