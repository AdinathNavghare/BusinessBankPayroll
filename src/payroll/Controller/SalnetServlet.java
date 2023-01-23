package payroll.Controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import payroll.Core.UtilityDAO;
import payroll.DAO.SalnetHandler;

/**
 * Servlet implementation class SalnetServlet
 */
@WebServlet("/SalnetServlet")
public class SalnetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public SalnetServlet() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action=request.getParameter("action");
		System.out.println("action is "+ action);
		
		// for generating the seq file in txt format by hrishi
		if(action.equalsIgnoreCase("SequenceFile")){
			
			String reportType=request.getParameter("reportType");
			String date="";
			//if(reportType.equals("BRBD"))
			//{
			 date=request.getParameter("date");
			//}
			System.out.println("reportType is "+reportType);
			
			String filePath = getServletContext().getRealPath("")+ File.separator + "salnet.txt";
			String filePath1= getServletContext().getRealPath("")+ File.separator + "BRBD.txt";
			System.out.println("path "+filePath);
			
			
			File file = new File(filePath);
			if (file.exists()) 
			{
				file.delete();
			}
			
			
			SalnetHandler.salNet(reportType,filePath);
			SalnetHandler.BRBD(reportType,filePath1,date);
			
			
			
			
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.write("<iframe scrolling=\"auto\" src=\"salnet.txt\" height=\"400px\" width=\"100%\"></iframe>");
			out.write("<iframe scrolling=\"auto\" src=\"BRBD.txt\" height=\"400px\" width=\"100%\"></iframe>");

		}//ends here
		
		
		
	}

}
