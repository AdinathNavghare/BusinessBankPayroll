package payroll.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import payroll.Core.Calculate;
import payroll.DAO.TranHandler;
import payroll.Model.TranBean;

/**
 * Servlet implementation class CoreServlet
 */
@WebServlet("/EmployeeListServlet")
public class EmployeeListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeListServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String action = request.getParameter("action");
		if(action.equalsIgnoreCase("calc"))
		{	
			String empList = request.getParameter("list");
			/*int[] empList = new int[vals.length];
			for(int i = 0; i<vals.length; i++)
			{
				empList[i] = Integer.parseInt(vals[i]);
			}*/
			String dt = request.getParameter("date");
			dt = "25-"+dt;
			HttpSession session = request.getSession();
			String uid = session.getAttribute("UID").toString();
			System.out.println("in emplist serv");
			int flag =Calculate.pay_cal(dt, empList,uid);
			
		
			
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			switch(flag)
			{
				case -2:out.write("Some Error has occurred while Calculation, Please try again!");
						break;
				case -1:out.write("No Employees found for Calculation");
						break;
				case -0:out.write("Payroll Calculation is done Successfully");
						break;
				default:
						out.write("Salary of Employee Number "+flag+" has gone Negative, Please correct it and Process the Calculation again!"); 
			
			}
			
		}
		else if(action.equalsIgnoreCase("negtvsal"))
		{
			String dt = request.getParameter("date");
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");//yyyy-MM-dd'T'HH:mm:ss
		    SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
		   
			
			String result="";
			try
			{
				 Date data = sdf.parse(dt);
				
				 ArrayList<TranBean> nslist = TranHandler.getNagtiveSalaryList( output.format(data));
				// result=result+"VIJAY KULKARNI "+"51  <a onclick=\"getTrnsation(51)\" style=\"cursor: pointer;\"/><font color=\"red\">Get Detail<font></a><br>";
			for(TranBean trbn: nslist)
			{
				
				result=result+trbn.getEMPNO()+" "+trbn.getEMPNAME()+"  <a onclick=\"getTrnsation("+trbn.getEMPNO()+")\" style=\"cursor: pointer;\"/><font color=\"red\">Get Detail<font></a><br>";
			}
			
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.write(result);
			}
			catch(Exception e){e.printStackTrace();}
		}
		else if(action.equalsIgnoreCase("negtvsalmnth"))
		{
			try
			{
				StringBuilder result=TranHandler.getNagtiveSalaryMonth();
				
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				out.write(result.toString());
			}catch(Exception e){e.printStackTrace();}
		}
		
	}
}