package payroll.Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.DAO.EmpOffHandler;
import payroll.DAO.TranHandler;
import payroll.Model.TranBean;
import payroll.Model.TransactionBean;

/**
 * Servlet implementation class LICServlet
 */
@WebServlet("/LICServlet")
public class LICServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LICServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String action = request.getParameter("action")==null?"":request.getParameter("action");
		TransactionBean trbn = new TransactionBean();
		TranBean tb = new TranBean();
		EmpOffHandler emp = new EmpOffHandler();
	    TranHandler trn = new TranHandler();
	    HttpSession session= request.getSession();	
		
		 if(action.equalsIgnoreCase("trnlist"))
		{
	
			String EMPNO;
			String list=request.getParameter("list")==null?"":request.getParameter("list");
			if(list.equalsIgnoreCase(""))
			{
				String[] employ = request.getParameter("EMPNO").split(":");
			    EMPNO = employ[2].trim();
			}	
			else if(list.equalsIgnoreCase("negative"))
			{
				EMPNO=request.getParameter("EMPNO");
			}
			else 
			{
				EMPNO=request.getParameter("EMPNO");
				String[] employ= EMPNO.split(":");
			 	EMPNO=employ[2];
			}
			//old before if else
			//EMPNO=request.getParameter("EMPNO");
			//String[] employ= EMPNO.split(":");
		 	//EMPNO=employ[2];
			    
			
			ArrayList<TranBean> trlist = new ArrayList<TranBean>();
			 trbn=emp.getInfoEmpTran(EMPNO);
		    trlist=trn.getTranInfo(EMPNO,"tran");// tran :-fire query to tran table
		    
		    
		    request.setAttribute("trbn",trbn); 
		    request.setAttribute("empno1",EMPNO); 
		 
		    session.setAttribute("trlist",trlist);
		    request.getRequestDispatcher("tranMaintainences.jsp?action=getdata").forward(request, response);
		    
		   // response.sendRedirect("tranMaintainences.jsp?action=getdata");
		       
		}
		// TODO Auto-generated method stub
	}

}
