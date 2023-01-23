package payroll.Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.DAO.EmpAttendanceHandler;
import payroll.DAO.onHoldHandler;
import payroll.Model.IncrementBean;
import payroll.Model.onHoldBean;

/**
 * Servlet implementation class OnholdServlet
 */
@WebServlet("/OnholdServlet")
public class OnholdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OnholdServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session=request.getSession();
		int loggedEmployeeNo = (Integer)session.getAttribute("EMPNO");
		
		EmpAttendanceHandler empAttendanceHandler=new EmpAttendanceHandler();
		String currentdate = empAttendanceHandler.getServerDate();
		
		String action = request.getParameter("action")==null?"":request.getParameter("action");
		boolean result =false;
		
		
		if(action.equalsIgnoreCase("releaseEmployee")){
			
			System.out.println("In servlet"+ request.getParameter("empno"));
			
			int empno = Integer.parseInt(request.getParameter("empno"));
			onHoldBean hBean = new onHoldBean();
			hBean.setEmpno(empno);
			hBean.setStatus("R");
			hBean.setReleasedate(currentdate);
			hBean.setReleaseby(loggedEmployeeNo);
			hBean.setRreason(null);
			hBean.setCurrentdate(currentdate);
			
			result = onHoldHandler.releaseOnHoldList(hBean);
	
			System.out.println("the result is"+result);
			if(result){
				response.sendRedirect("OnHold.jsp?action=firsttime&flag=3");
			}
			else{	
				response.sendRedirect("OnHold.jsp?flag=4");	
			}	 
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		int loggedEmployeeNo = (Integer)session.getAttribute("EMPNO");
		
		EmpAttendanceHandler empAttendanceHandler=new EmpAttendanceHandler();
		String currentdate = empAttendanceHandler.getServerDate();
		
		String action = request.getParameter("action")==null?"":request.getParameter("action");
		boolean result =false;
		
		
		if(action.equalsIgnoreCase("onHold")){
			
			System.out.println("In servlet");
			
			String empno = request.getParameter("EMPNO");
			String date = request.getParameter("date");
			//date = "01-"+date;
			String hreason = request.getParameter("hreason");
			String emp[] = empno.split(":");
			
			onHoldBean hBean = new onHoldBean();
			
			hBean.setEmpno(Integer.parseInt(emp[2]));
			hBean.setEmpcode(emp[1]);
			hBean.setSalmonth(date);
			hBean.setHoldate(currentdate);
			hBean.setHreason(hreason);
			hBean.setHoldby(loggedEmployeeNo);
			hBean.setStatus("H");
			hBean.setCurrentdate(currentdate);
			
			result = onHoldHandler.insertOnHoldList(hBean);
	
			System.out.println("the result is"+result);
			if(result){
				response.sendRedirect("OnHold.jsp?action=firsttime&flag=1");
			}
			else{	
				response.sendRedirect("OnHold.jsp?flag=2");	
			}	 
		}
		
		
	}

}
