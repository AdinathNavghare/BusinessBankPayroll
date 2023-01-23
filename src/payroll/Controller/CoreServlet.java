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
import payroll.Core.CalculateByThread;
import payroll.Core.ReportDAO;
import payroll.DAO.TranHandler;
import payroll.Model.TranBean;

/**
 * Servlet implementation class CoreServlet
 */
@WebServlet("/CoreServlet")
public class CoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CoreServlet() {
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
			String date=ReportDAO.EOM(dt);
			HttpSession session = request.getSession();
			String uid = session.getAttribute("UID").toString();
			
			int flag =Calculate.pay_cal(date, empList,uid);
			
			//int flag = CalculateByThread.pay_cal(date, empList,uid);
		
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			
			ArrayList<TranBean> nslist = TranHandler.getNagtiveSalaryList1(date,empList);
			String negList="";
			for(TranBean tb:nslist)
			{
			negList +="\n|"+tb.getEmpcode()+" --- "+tb.getEMPNAME()+"|,";
			}
			//out.write("Salary of Employee(s)  "+negList+" \n has gone Negative, Please correct it and Process the Calculation again!");
			System.out.println("negative sal list size..."+nslist.size());
			
			
			switch(flag)
			{
				case -2:out.write("Some Error has occurred while Calculation, Please try again!");
						break;
				case -1:out.write("No Employees found for Calculation");
						break;
				case -0:
					if(nslist.size()==0)
						{
						out.write("Payroll Calculation is done Successfully");
						}
					else
					{
						out.write("Payroll Calculation is done Successfully But....\n Salary of Employee(s)  "+negList+" \n has gone Negative, Please correct it and Process the Calculation again!");
					}
						break;
				default:
					/*ArrayList<TranBean> nslist = TranHandler.getNagtiveSalaryList(date);
					String negList="";
					for(TranBean tb:nslist)
					{
					negList +="\n"+tb.getEMPNAME()+",";
					}
					out.write("Salary of Employee(s)  "+negList+" \n has gone Negative, Please correct it and Process the Calculation again!");
					*/
			}	
			
			
			/* old code for breake calculation when negative salary occurss
			  
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
					ArrayList<TranBean> nslist = TranHandler.getNagtiveSalaryList(date);
					String negList="";
					for(TranBean tb:nslist)
					{
					negList +="\n"+tb.getEMPNAME()+",";
					}
					out.write("Salary of Employee(s)  "+negList+" \n has gone Negative, Please correct it and Process the Calculation again!");
					
			}	*/
			
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
		else if(action.equalsIgnoreCase("notfinalized"))
		{
			try
			{
				StringBuilder result=TranHandler.getNotFinalized();
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				out.write(result.toString());
			}catch(Exception e){e.printStackTrace();}
		}
		if(action.equalsIgnoreCase("import"))
		{	
			String dt = request.getParameter("date");
			String dt1 = request.getParameter("next");
			int flag = TranHandler.nextMonthImport(dt,dt1);
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			switch(flag)
			{
				case -2:out.write("Salary Of all employees has not been Finalize Please try again!");
						break;
				case -1:out.write("No Employees found for Calculation");
						break;
				case -0:out.write("Data Imported Successfully");
						break;
				default:
						out.write("Salary of Employee Number "+flag+" has gone Negative, Please correct it and Process the Calculation again!"); 
			}	
		}
	}
}