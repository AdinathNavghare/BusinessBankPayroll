package payroll.Controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.icu.util.StringTokenizer;

import oracle.net.ns.SessionAtts;

import payroll.DAO.AttendanceHandler;
import payroll.Model.AttendanceBean;

/**
 * Servlet implementation class AttendanceServlet
 */
@WebServlet("/AttendanceServlet")
public class AttendanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AttendanceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		int userid =0;
		boolean sessionFlag=false;
		try
		{
			userid=Integer.parseInt(session.getAttribute("UID").toString());
			sessionFlag=true;
		}
		catch(Exception e)
		{
			response.sendRedirect("login.jsp?action=0");
		}
		if(sessionFlag)
		{
			
			String action = request.getParameter("action");
			AttendanceHandler AH = new AttendanceHandler();
			boolean flag = false;
			if(action.equalsIgnoreCase("checkin"))
			{
				String hostname = request.getRemoteHost();
				String hostName = InetAddress.getByName(hostname).getHostName();
				flag = AH.check_in(userid,hostName);
				if(flag)
				{
					response.sendRedirect("attendanceReport.jsp?action=1");
				}
				else
				{
					response.sendRedirect("attendanceReport.jsp?action=0");
				}
			}	
			if(action.equalsIgnoreCase("checkout"))
			{
				String intime = AH.getcheckinTime(userid)==null?"":AH.getcheckinTime(userid);
				if(!intime.equalsIgnoreCase("NA"))
				{
					int th = 0 ;
					int tm = 0 ;
					int out_time_min = 0;
					int in_time_min = 0;
					int diff_time = 0;
					String delim =":";
					SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
					String check_out = sdf.format(cal.getTime());//Time in hr:min.........
					String out_time =check_out;
					StringTokenizer stn = new StringTokenizer(check_out, delim);
					th = Integer.parseInt(stn.nextToken());//Hr........
					while(stn.hasMoreTokens())
					{
						 tm = Integer.parseInt(stn.nextToken());//Min..........
					}
					out_time_min = th * 60 + tm;
					StringTokenizer stn1 = new StringTokenizer(intime, delim);
					th = Integer.parseInt(stn1.nextToken());//Hr........
					while(stn1.hasMoreTokens())
					{
						 tm = Integer.parseInt(stn1.nextToken());//Min..........
					}
					in_time_min = th * 60 + tm;
					diff_time = out_time_min - in_time_min;
					int wrmins =(diff_time)%60;
					String mins =String.valueOf(wrmins);
					if(wrmins < 10)
					{
						mins = "0"+wrmins;
					}
					String wrkHrs = String.valueOf((diff_time)/60) +":"+mins;
					if(diff_time >= 510)
					{
						AttendanceBean attBean,attBean1;
						attBean = new AttendanceBean();
						attBean.setCHECK_OUT(String.valueOf(check_out));
						attBean.setEARLY_REASON("-");
						attBean.setUSERID(Integer.parseInt(session.getAttribute("UID").toString()));
						attBean.setTOTAL_TIME(wrkHrs);
						attBean1 = new AttendanceBean();
						attBean1 = AH.check_out(attBean);
						if(attBean1!=null)
						{
							request.setAttribute("attbean", attBean1);
							session.setAttribute("timediff", wrkHrs);
							response.sendRedirect("attendanceReport.jsp?action=3");//3.. checkout in time successfull...
						}
						else
						{
							response.sendRedirect("attendanceReport.jsp?action=3");
						}
					}
					else
					{
						session.setAttribute("outtime", out_time);
						session.setAttribute("timediff", wrkHrs);
						response.sendRedirect("attendanceReport.jsp?action=2");
					}
				}
				else
				{
					response.sendRedirect("attendanceReport.jsp?action=4");//check out without check in....
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		AttendanceHandler AH = new AttendanceHandler();
		if(action.equalsIgnoreCase("ealryCheckOut"))
		{
			AttendanceBean attBean,attBean1;
			attBean = new AttendanceBean();
			attBean.setCHECK_OUT(request.getParameter("outtime"));
			attBean.setEARLY_REASON(request.getParameter("reason")==""?"Not Specified":request.getParameter("reason"));
			attBean.setUSERID(Integer.parseInt(session.getAttribute("UID").toString()));
			attBean.setTOTAL_TIME(session.getAttribute("timediff").toString());
			attBean1 = new AttendanceBean();
			attBean1 = AH.check_out(attBean);
			request.setAttribute("attbean", attBean1);
			response.sendRedirect("earlyReason.jsp?action=close");//early check out successfull.....
		}
	}
}
