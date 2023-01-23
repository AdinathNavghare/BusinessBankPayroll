package payroll.Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.servlet.RequestDispatcher;
import payroll.Core.ReportDAO;
import payroll.DAO.EmpOffHandler;
import payroll.DAO.EmplQulHandler;
import payroll.DAO.TranHandler;
import payroll.DAO.TripHandler;

import payroll.DAO.ShiftHandler;
import payroll.Model.EmpQualBean;
import payroll.Model.HolidayMasterBean;
import payroll.Model.PolicyBean;
import payroll.Model.ShiftBean;
import payroll.Model.TranBean;
import payroll.Model.TransactionBean;
import payroll.Model.TripBean;

/**
 * Servlet implementation class TravelReportServlet
 */
@WebServlet("/TravelReportServlet")
public class TravelReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TravelReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String action=request.getParameter("action")!=null?request.getParameter("action"):"";
		HttpSession session = request.getSession();
		TripHandler triphand = new TripHandler();
		TransactionBean trbn = new TransactionBean();
		EmpOffHandler emp = new EmpOffHandler();
		TranHandler trn = new TranHandler();
		TripBean tb  = new TripBean();
		
		
		if(action.equalsIgnoreCase("showinfo")){
	 		System.out.println("in report servalet");
	 		String EMPNO1;
			EMPNO1=request.getParameter("EMPNO");
			String[] employ= EMPNO1.split(":");
		 	int EMPNO=Integer.parseInt(employ[2]);
			request.setAttribute("trbn",trbn); 
		    request.setAttribute("empno1",EMPNO); 
		    TripHandler hmh1=new  TripHandler();
			ArrayList<TripBean> callist=new ArrayList<TripBean>();
			callist=hmh1.getTripInfo(EMPNO);
			request.setAttribute("list", callist);
			
			String EMP;
			String list=request.getParameter("list")==null?"":request.getParameter("list");
			EMP=request.getParameter("EMPNO");
			String[] empl= EMP.split(":");
		 	EMP=empl[2];
			    
	 		
	 		ArrayList<TranBean> trlist = new ArrayList<TranBean>();
			 trbn=emp.getInfoEmpTran(EMP);
		    trlist=trn.getTranInfo(EMP,"tran");// tran :-fire query to tran table
		    
		    
		    request.setAttribute("trbn",trbn); 
		    request.setAttribute("empno1",EMPNO); 
		 
		    session.setAttribute("trlist",trlist);
			
			
		   // session.setAttribute("trlist",trlist);	
		    System.out.println("report servalet  "+EMPNO);
		    request.getRequestDispatcher("TravelReport.jsp?action=getdata").forward(request, response);
		    //response.sendRedirect("TravelReport.jsp?action=getdata");
		    
	 		
	 	}
		
		if(action.equalsIgnoreCase("getDetails")){
	 		System.out.println("in report servalet");
	 		String EMPNO1;
			EMPNO1=request.getParameter("EMPNO");
			String[] employ= EMPNO1.split(":");
		 	int EMPNO=Integer.parseInt(employ[2]);
			request.setAttribute("trbn",trbn); 
		    request.setAttribute("empno1",EMPNO); 
		    TripHandler hmh1=new  TripHandler();
			ArrayList<TripBean> callist=new ArrayList<TripBean>();
			callist=hmh1.getTripInfo(EMPNO);
			request.setAttribute("list", callist);
			
			String EMP;
			
			EMP=request.getParameter("EMPNO");
			String[] empl= EMP.split(":");
		 	EMP=empl[2];
			    
	 		
	 		ArrayList<TranBean> trlist = new ArrayList<TranBean>();
			 trbn=emp.getInfoEmpTran(EMP);
		    trlist=trn.getTranInfo(EMP,"tran");// tran :-fire query to tran table
		    
		    
		    request.setAttribute("trbn",trbn); 
		    request.setAttribute("empno1",EMPNO); 
		 
		    session.setAttribute("trlist",trlist);
			
			
		   // session.setAttribute("trlist",trlist);	
		    System.out.println("report servalet  "+EMPNO);
		    request.getRequestDispatcher("TravelDetails.jsp?action=getdata").forward(request, response);
		    //response.sendRedirect("TravelReport.jsp?action=getdata");
		    
	 		
	 	}
		
	}

}
