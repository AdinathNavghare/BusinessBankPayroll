package payroll.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.Core.ReportDAO;
import payroll.DAO.EmplQulHandler;
import payroll.DAO.HolidayMasterHandler;
import payroll.DAO.ShiftHandler;
import payroll.Model.EmpQualBean;
import payroll.Model.HolidayMasterBean;
import payroll.Model.ShiftBean;

/**
 * Servlet implementation class HolidayMasterServlet
 */
@WebServlet("/HolidayMasterServlet")
public class HolidayMasterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final HolidayMasterBean String = null;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HolidayMasterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action=request.getParameter("action")!=null?request.getParameter("action"):"";
		HolidayMasterHandler holidayhand=new HolidayMasterHandler();
		HttpSession session = request.getSession();
		PrintWriter out=response.getWriter();
		/*if(action.equalsIgnoreCase("showsholiday"))
		{	
			HolidayMasterHandler hmh = new HolidayMasterHandler();
			ArrayList<HolidayMasterBean> holidaylist = new ArrayList<HolidayMasterBean>();
			HolidayMasterBean hbean = new HolidayMasterBean();
			holidaylist =hmh .getHoldList(hbean);
			request.setAttribute("holidaylist",holidaylist);
			RequestDispatcher rd = request.getRequestDispatcher("ShowHolidayMaster.jsp?action=list");
			rd.forward(request, response);		
		}*/

		
		
		if(action.equalsIgnoreCase("del"))
		{
		//System.out.println("the value of key is"+key);
		//String[] splitKey = key.split("-"); // [0] Ecat, [1] Trcode, [2]
											// Srno all Integer
		/*String fdate = (key.substring(0, 11));
		String tdate = (key.substring(12, 23));
		String type = (splitKey[6]);
		String branch = (splitKey[7]);*/ // to delete with name and type date and other
		
		int key = Integer.parseInt(request.getParameter("key"));
		HolidayMasterHandler Hm = new HolidayMasterHandler();
		int id=key;
		boolean flag = Hm.deleteHoliday(id);
		response.setContentType("text/html");
		response.setContentType("text/html");
		if (flag) {
			out.write("true");
		} else {
			out.write("false");
		}
			
	  }
		
		
 }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		HolidayMasterBean hb = new HolidayMasterBean();
		HolidayMasterHandler hmh = new HolidayMasterHandler();
		String action=request.getParameter("action")==null?"":request.getParameter("action");
		//String type = request.getParameter("type");
		
		
		
		if(action.equalsIgnoreCase("addholdmast"))
		{		
			boolean flag=false;
			hb.setHoldname(request.getParameter("hdname")!=null?request.getParameter("hdname"):"");
			
			hb.setFromdate(request.getParameter("frmdate")!=null?request.getParameter("frmdate"):"");
			hb.setTodate(request.getParameter("todate")!=null?request.getParameter("todate"):"");
			hb.setBranch(request.getParameter("branch")!=null?request.getParameter("branch"):"");
			hb.setText(request.getParameter("text")!=null?request.getParameter("text"):"");
			hb.setType(request.getParameter("type")!=null?request.getParameter("type"):"");
			hb.setRepeathold(request.getParameter("annually")!=null?request.getParameter("annually"):"");
			hb.setDay(request.getParameter("day")!=null?request.getParameter("day"):"");
			hb.setAttend(request.getParameter("attend")!=null?request.getParameter("attend"):"");
			hb.setSms(request.getParameter("sms")!=null?request.getParameter("sms"):"");
			hb.setOptionalhold(request.getParameter("optional")!=null?request.getParameter("optional"):"");

			flag=hmh.addholdmast(hb);
			if(flag)
			{  
				//HolidayMasterHandler hmh1=new  HolidayMasterHandler();
				//ArrayList<HolidayMasterBean> callist=new ArrayList<HolidayMasterBean>();
				//callist=hmh1.getHoldList(ReportDAO.BOM(hb.getFromdate()), ReportDAO.EOM(hb.getFromdate()), hb.getBranch());
				
			    response.sendRedirect("ShowHolidayMaster.jsp?action=show&frmdate="+hb.getFromdate()+"&todate="+hb.getTodate()+"&branch="+hb.getBranch());
			}
		}	
		
	}
}