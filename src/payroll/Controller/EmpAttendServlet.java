package payroll.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.Core.ReportDAO;
import payroll.DAO.EmpAttendanceHandler;

import payroll.Model.CompBean;
import payroll.Model.TranBean;

/**
 * Servlet implementation class EmpAttendServlet
 */
@WebServlet("/EmpAttendServlet")
public class EmpAttendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmpAttendServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session=request.getSession();
		String action=request.getParameter("action");
		String date=request.getParameter("date");
		EmpAttendanceHandler EAH=new EmpAttendanceHandler();
		int eno=0;
		if(session.getAttribute("Prj_Srno")!=null)
		 eno = (Integer)session.getAttribute("EMPNO");
		int site_id=0;
		if(session.getAttribute("Prj_Srno")!=null || request.getParameter("prj")!=null)
		 site_id=request.getParameter("prj")==null?(Integer)session.getAttribute("Prj_Srno"):Integer.parseInt(request.getParameter("prj"));
		ArrayList<TranBean> tran = new ArrayList<TranBean>();
		
		tran=(ArrayList<TranBean>)session.getAttribute("emplist");
		if(action.equalsIgnoreCase("approval"))
		{
			
			
			String status="pending";
					status=EAH.setAttendanceStatus(date,site_id,status,eno);
			if(status.equalsIgnoreCase("pending"))
			{
				
				response.sendRedirect("attendanceMain.jsp?status=pending");
			}
			else if(status.equalsIgnoreCase("rejected"))
			{	
				request.getRequestDispatcher("EmpPresentSeat.jsp?date="+date+"&flag=5").forward(request, response);
			}
			else
			{
				request.getRequestDispatcher("EmpPresentSeat.jsp?date="+date+"&flag=3").forward(request, response);
			}
		}
		
		if(action.equalsIgnoreCase("approved"))
		{
			
			
			String status="approved";
					status=EAH.setAttendanceStatus(date,site_id,status,eno);
			if(status.equalsIgnoreCase("approved"))
			{
				
				response.sendRedirect("approveAttendance.jsp?date="+date+"&flag=1");
			}
			if(status.equalsIgnoreCase("notMatch"))
			{
				
				response.sendRedirect("approveAttendance.jsp?date="+date+"&flag=100");
			}
			
		}
		if(action.equalsIgnoreCase("reject"))
		{
			
			
			String status="rejected";
					status=EAH.setAttendanceStatus(date,site_id,status,eno);
			
			if(status.equalsIgnoreCase("rejected"))
			{	
				response.sendRedirect("approveAttendance.jsp?date="+date+"&flag=2");
			}
			
		}
		
		
	
		if(action.equalsIgnoreCase("getChecked"))
		{
			System.out.println("inside getChecked");
			String emp=request.getParameter("empNo")==null?"":request.getParameter("empNo");
			String[] employ = emp.split(":");
		    int empNo = Integer.parseInt(employ[2].trim());
		    System.out.println("the employee"+empNo);
			try {
			ArrayList<TranBean> Emp_bean = new ArrayList<TranBean>();
				Emp_bean=EAH.getAssignedSitesList(empNo);
				request.setAttribute("ProjectList", Emp_bean);
				 request.getRequestDispatcher("AssignSite.jsp?action=display&empno="+empNo).forward(request,response);
				//response.sendRedirect("AssignSite.jsp?action=display&empno="+empNo);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		
		if(action.equalsIgnoreCase("getAssignWeek"))
		{
			System.out.println("inside getChecked");
			
			
			
			String prjCode =request.getParameter("prj")==null?"":request.getParameter("prj");
		   
			try {
			ArrayList<CompBean> Emp_bean = new ArrayList<CompBean>();
				Emp_bean=EAH.getAssignweek(prjCode);
				request.setAttribute("AssignWeek", Emp_bean);
				 request.getRequestDispatcher("WeekOffAssign.jsp?action=display&prj="+prjCode).forward(request,response);
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
		
		if(action.equalsIgnoreCase("deleteAssignSite"))
		{
			System.out.println("inside getChecked");
			String emp=request.getParameter("empNo")==null?"":request.getParameter("empNo");
			String[] employ = emp.split(":");
		    int empNo = Integer.parseInt(employ[2].trim());
		    System.out.println("the employee"+empNo);
			try {
	
			boolean flag=EAH.deleteAssignedSitesList(empNo);
				
				if(flag){
					 request.getRequestDispatcher("AssignSite.jsp?action=display&empno="+empNo+"&flag=2").forward(request,response);
						
					}
					else
					{
						request.getRequestDispatcher("AssignSite.jsp?&flag=4").forward(request, response);
					}
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			}

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
		HttpSession session=request.getSession();
		EmpAttendanceHandler EAH=new EmpAttendanceHandler();
		String action=request.getParameter("action");
		String date=request.getParameter("adddate");
		int eno = (Integer)session.getAttribute("EMPNO");
		//int site_id=(Integer)session.getAttribute("Prj_Srno");
		ArrayList<TranBean> tran = new ArrayList<TranBean>();
		
		tran=(ArrayList<TranBean>)session.getAttribute("emplist");
		
		if(action.equalsIgnoreCase("insert"))
		{
			
			String st=request.getParameter("st")==null?"saved":request.getParameter("st");
			System.out.println(st);
			String empno="";
			ArrayList  Emp_al=new ArrayList();

			int index=0;
			for (TranBean tbean : tran) 
			{
				String	date1=ReportDAO.BOM(date);
				ArrayList<TranBean> Emp_bean=new ArrayList<TranBean>();
				String vals[] = request.getParameterValues("_"+tbean.getEMPNO());
				
				for(int i=0;i<vals.length;i++)
				{
					TranBean ab=new TranBean();
					ab.setEMPNO(tbean.getEMPNO());
					//ab.setSite_id(Integer.toString(site_id));
					ab.setDate(date1);
					ab.setVal(vals[i].toUpperCase());
					Emp_bean.add(ab);
					empno=""+tbean.getEMPNO();
					SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
					Calendar c = Calendar.getInstance();    
					c.setTime(format.parse(date1));
					c.add(Calendar.DATE, 1);
					date1=format.format(c.getTime());
				
				}
				
				Emp_al.add(Emp_bean);
			}
			
			
			boolean flag=EAH.insertEmpAttendance(Emp_al,eno);
			//String status="saved";
			String status=st;
					//status=EAH.setAttendanceStatus(date,site_id,status,eno);
			if(flag)
			{
				//request.getRequestDispatcher("EmpPresentSeat.jsp?date="+date+"&EMPNO="+empno+"").include(request, response);
				//response.sendRedirect("attendanceMain.jsp?status="+st);
				response.sendRedirect("LatePresentSeat.jsp?date="+date+"&flag=1&EMPNO="+empno+"");
			}
			else
			{
				request.getRequestDispatcher("LatePresentSeat.jsp?date="+date+"&EMPNO="+empno+"&flag=2").include(request, response);
			}
			
		}
		
		
		if(action.equalsIgnoreCase("getWeek"))
		{
			System.out.println("inside getweek");
			CompBean bn =new CompBean();
			boolean result = false;
			String prj=request.getParameter("pp")==null?"":request.getParameter("pp");
			
			String[] p= prj.split(":");
			String prjCode = p[3];
			
			bn.setCname(prjCode);
			
			String[] offday = request.getParameterValues("offday");
			String days = "";
			if(offday != null){
				for( String day: offday){
					days = days+" "+day;
				 }
			}
			bn.setWoffday(days);
			String[] alterSat = request.getParameterValues("altsatday");
			String week = "";
			if(alterSat != null){
				for( String day: alterSat){
					week = week+" "+day;
				 }
			}
			bn.setAltsat(week);
			result = EAH.weekoff(bn);
			if(result)
				response.sendRedirect("WeekOffAssign.jsp?flag=1");
				else
					response.sendRedirect("WeekOffAssign.jsp?flag=4");
			
		    System.out.println("the code"+prj);
			
			}

		
		if(action.equalsIgnoreCase("assignSite"))
		{
			System.out.println("inside assignSite");
			ArrayList Emp_tran =new ArrayList();
			
			String emp=request.getParameter("EMPNO")==null?"":request.getParameter("EMPNO");
			String[] employ = emp.split(":");
		    int EmpNo = Integer.parseInt(employ[2].trim());
			

			
				
					ArrayList<TranBean> tranList=new ArrayList<TranBean>();
					String checkbox[]=request.getParameterValues("checkList");
					
					
				for(int i=0;i<checkbox.length; i++){
					TranBean tranBean=new TranBean();
		 
					tranBean.setSite_id(checkbox[i].substring(0,2));
					
					tranBean.setEMPNO(EmpNo);

					tranList.add(tranBean);

		 
		}
				Emp_tran.add(tranList);
			boolean flag=EAH.assignSite(Emp_tran, eno);
				
			if(flag)
				{
				ArrayList<TranBean> Emp_bean = new ArrayList<TranBean>();
				Emp_bean=EAH.getAssignedSitesList(EmpNo);
				System.out.println("//////////"+Emp_bean.size());
				request.setAttribute("ProjectList", Emp_bean);
				System.out.println("54233255555            :"+EmpNo);
				 request.getRequestDispatcher("AssignSite.jsp?action=display&empno="+EmpNo+"&flag=1").forward(request,response);
					//response.sendRedirect("AssignSite.jsp?action=display&empno="+EmpNo+"&flag1=1");
				}
				else
				{
					request.getRequestDispatcher("AssignSite.jsp?&flag=4").forward(request, response);
				}
				
			
			
			
			}


		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			request.getRequestDispatcher("EmpPresentSeat.jsp?flag=3").include(request, response);
		}
		
		
		
		
	}

}
