package payroll.Controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.Core.ReportDAO;
import payroll.DAO.CommonDedHandler;

/**
 * Servlet implementation class lateMarking
 */
@WebServlet("/lateMarkingServlet")
public class lateMarkingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public lateMarkingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		
		if (action.equalsIgnoreCase("Add")) 
		{
			System.out.println("hello we are in nadd of latemarkserv");
			String date = request.getParameter("date"); 
			String EMPNOLIST = request.getParameter("list");
			String DeductionCode=request.getParameter("deduction");
			int sessionEmployee=(Integer)session.getAttribute("EMPNO");
			String today=ReportDAO.getServerDate();
			CommonDedHandler CDH= new CommonDedHandler();
			
			try {
				boolean flag=CDH.addLatemark(date,EMPNOLIST,DeductionCode,sessionEmployee,today);
				
				
				
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
