package payroll.Controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import payroll.Core.UtilityDAO;
import payroll.DAO.CommonDedHandler;

/**
 * Servlet implementation class CommonDedServlet
 */
@WebServlet("/CommonDedServlet")
public class CommonDedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommonDedServlet() {
        super();
        // TODO Auto-generated constructor stub
        
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		
		if (action.equalsIgnoreCase("Add")) 
		{
			String date = request.getParameter("date");
			String EMPNOLIST = request.getParameter("list");
			String DeductionCode=request.getParameter("deduction");
			String amount=request.getParameter("amount");
			CommonDedHandler CDH= new CommonDedHandler();
			
			try {
				boolean flag=CDH.addDeduction(date,EMPNOLIST,DeductionCode,amount);
				
				
				
				
				
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
