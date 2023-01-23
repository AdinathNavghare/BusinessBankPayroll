package payroll.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.DAO.EmployeeHandler;
import payroll.DAO.LoanHandler;
import payroll.DAO.TranHandler;
import payroll.Model.EmployeeBean;
import payroll.Model.LoanBean;
import payroll.Model.TranBean;

/**
 * Servlet implementation class LoanServlet
 */
@WebServlet("/LoanServlet")
public class LoanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoanServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session= request.getSession();	
		String action = request.getParameter("action")==null?"":request.getParameter("action");
		LoanHandler loanhandler = new LoanHandler();
		
		LoanBean lb = new LoanBean();
		if(action.equalsIgnoreCase("getdata"))
		{
			 
			ArrayList<LoanBean> trlist = new ArrayList<LoanBean>();
			 
			String flag=request.getParameter("flag")==null?"0":request.getParameter("flag");
			String EMPNO=request.getParameter("EMPNO");
			String EMpno="";
			StringTokenizer st = new StringTokenizer(EMPNO,":");
		    while(st.hasMoreTokens())
		    {
		    	EMpno=st.nextToken().trim();
		    }
		    //int empno=Integer.parseInt(EMPNO);
		    
		    trlist=loanhandler.getTranInfo(EMpno);
		    
 		    session.setAttribute("trlist",trlist);
 		    session.setAttribute("empno",EMpno);
 		    session.setAttribute("empNameNO",EMPNO);
 		    
 		    if(session.getAttribute("loan_app").toString().equalsIgnoreCase("1"))
 		    {
 		    	 request.getRequestDispatcher("loan_Application.jsp?action=getdata&flag="+flag).forward(request, response);
 		    }
 		    else
 		    {
		    request.getRequestDispatcher("loan_master.jsp?action=getdata&flag="+flag).forward(request, response);
 		    }
		       
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session= request.getSession();	
		String action = request.getParameter("action")==null?"":request.getParameter("action");
		LoanHandler loanhandler = new LoanHandler();
		LoanBean lb ;
		/*if(action.equalsIgnoreCase("addTran"))
		{
			System.out.println(action);
			lb = new LoanBean();
			int flag=0;
			String EMPNO= request.getParameter("empno");
            System.out.println("empno is"+EMPNO);
            StringTokenizer st1 = new StringTokenizer(EMPNO,":");
 		    while(st1.hasMoreTokens())
 		    {
 		    	EMPNO=st1.nextToken();
 		    }
			
 		    lb.setLoan_no(request.getParameter("loan_no")!=null?Integer.parseInt(request.getParameter("loan_no")):0);
 		    lb.setLoan_code(request.getParameter("loan_code")!=null?Integer.parseInt(request.getParameter("loan_code")):0);
 		    lb.setEMPNO(Integer.parseInt(EMPNO));
 		    lb.setLoan_amt(request.getParameter("loan_amt")!=null?Integer.parseInt(request.getParameter("loan_amt")):0);
 		    lb.setLoan_per(request.getParameter("loan_per")!=null?Float.parseFloat(request.getParameter("loan_per")):0);
			lb.setStart_date(request.getParameter("startDate")!=null?request.getParameter("startDate"):"");
			lb.setEnd_date(request.getParameter("EndDate")!=null?request.getParameter("EndDate"):"");
			lb.setMonthly_install(request.getParameter("month_inst")!=null?Integer.parseInt(request.getParameter("month_inst")):0);
			lb.setBank_name(request.getParameter("bank_name")!=null?request.getParameter("bank_name"):"");
			lb.setSanctionby(request.getParameter("sancBy")!=null?request.getParameter("sancBy"):"");
			lb.setACTIVE(request.getParameter("active")!=null?request.getParameter("active"):"");
			flag = loanhandler.addTransaction(lb);
			ArrayList<LoanBean> trlist = new ArrayList<LoanBean>();
			trlist=loanhandler.getTranInfo(EMPNO);
	 		 session.setAttribute("trlist",trlist);
	 		
	 		 System.out.print("Yes");
			if(flag==1)
			{
				response.sendRedirect("loan_master.jsp?action=getdata&flag="+flag); 
				System.out.println("query executed successfully");
			}
			else
			{
				System.out.println("error in record inserting");	
				response.sendRedirect("loan_master.jsp?action=getdata&flag="+flag); 
			}
			
		       
		}
		*/
		
		
		if(action.equalsIgnoreCase("addTran"))
		{
			System.out.println("in addTran method");
			boolean flag=false;	
			String EMPNO= request.getParameter("empno");
			lb = new LoanBean();
			lb.setLoan_no(request.getParameter("loan_no")!=null?Integer.parseInt(request.getParameter("loan_no")):0);
 		    lb.setLoan_code(request.getParameter("loan_code")!=null?Integer.parseInt(request.getParameter("loan_code")):0);
 		    lb.setEMPNO(Integer.parseInt(EMPNO));
 		    lb.setLoan_amt(request.getParameter("loan_amt")!=null?Float.parseFloat(request.getParameter("loan_amt")):0);
 		    lb.setLoan_per(request.getParameter("loan_per")!=null?Float.parseFloat(request.getParameter("loan_per")):0);
 		    lb.setTotal_month(request.getParameter("pay_month")!=null?Integer.parseInt(request.getParameter("pay_month")):0);
			lb.setStart_date(request.getParameter("startDate")!=null?request.getParameter("startDate"):"");
			lb.setEnd_date(request.getParameter("EndDate")!=null?request.getParameter("EndDate"):"");
			lb.setMonthly_install((float) (request.getParameter("month_inst")!=null?Float.parseFloat(request.getParameter("month_inst")):0.00));
			lb.setBank_name(request.getParameter("bank_name")!=null?request.getParameter("bank_name"):"");
			lb.setSanctionby(request.getParameter("sancBy")!=null?request.getParameter("sancBy"):"");
			lb.setACTIVE(request.getParameter("active")!=null?request.getParameter("active"):"");
			lb.setActual_pay((request.getParameter("month_inst")!=null?Float.parseFloat(request.getParameter("month_inst")):0.00)*(request.getParameter("pay_month")!=null?Integer.parseInt(request.getParameter("pay_month")):0));
			lb.setAction_by(session.getAttribute("EMPNO").toString());
			
			
			int result=loanhandler.getActiveLoan(EMPNO,lb.getStart_date(),lb.getEnd_date());
			if(result>0)
			{
			flag=loanhandler.addTransaction(lb);

			if(flag == true)
	    	{
				
				session.setAttribute("empno",EMPNO);
				 EmployeeHandler eh=new EmployeeHandler();
				 EmployeeBean eb=eh.getEmployeeInformation(EMPNO);
				 
				 session.setAttribute("empNameNO",eb.getFNAME()+" "+eb.getLNAME()+":"+eb.getEMPCODE()+":"+EMPNO);
				 
				
				response.sendRedirect("LoanServlet?EMPNO="+session.getAttribute("empNameNO")+"&action=getdata&flag=1"); 
				System.out.println("query executed successfully"); 
			}
			else
			{
				System.out.println("error in record inserting");	
				session.setAttribute("empno",EMPNO);
				 EmployeeHandler eh=new EmployeeHandler();
				 EmployeeBean eb=eh.getEmployeeInformation(EMPNO);
				 
				 session.setAttribute("empNameNO",eb.getFNAME()+" "+eb.getLNAME()+":"+eb.getEMPCODE()+":"+EMPNO);
				 
				
				response.sendRedirect("LoanServlet?EMPNO="+session.getAttribute("empNameNO")+"&action=getdata&flag=4"); 
				System.out.println("query executed successfully");
			}
			}
			else
			{
				System.out.println("date already exist");	
				session.setAttribute("empno",EMPNO);
				 EmployeeHandler eh=new EmployeeHandler();
				 EmployeeBean eb=eh.getEmployeeInformation(EMPNO);
				 
				 session.setAttribute("empNameNO",eb.getFNAME()+" "+eb.getLNAME()+":"+eb.getEMPCODE()+":"+EMPNO);
				 
				
				response.sendRedirect("LoanServlet?EMPNO="+session.getAttribute("empNameNO")+"&action=getdata&flag=-1"); 
				
			}
		}
		
		if(action.equalsIgnoreCase("update"))
		{
		
			ArrayList<LoanBean> trlist = new ArrayList<LoanBean>();
			boolean flag=false;
			LoanBean lb1 =new LoanBean();
			String EMPNO=request.getParameter("empno");
			lb1.setEMPNO(Integer.parseInt(request.getParameter("empno")));
			lb1.setLoan_amt(request.getParameter("loan_amt")!=null?Float.parseFloat(request.getParameter("loan_amt")):0);
	 		lb1.setLoan_no(Integer.parseInt(request.getParameter("loan_no")));
			lb1.setTotal_month(Integer.parseInt(request.getParameter("pay_month")));
			lb1.setStart_date(request.getParameter("startDate"));
			lb1.setEnd_date(request.getParameter("EndDate"));
			lb1.setLoan_code(Integer.parseInt(request.getParameter("loan_code")));
			lb1.setMonthly_install(Float.parseFloat(request.getParameter("month_inst")));
			lb1.setLoan_per(Float.parseFloat(request.getParameter("loan_per")));
			lb1.setSanctionby(request.getParameter("sancBy")!=null?request.getParameter("sancBy"):"");
			lb1.setACTIVE(request.getParameter("active"));
			lb1.setAction_by(session.getAttribute("EMPNO").toString());
			flag=loanhandler.updateLoanDtail(lb1);			
			if(flag)
			{
				System.out.println("record updated successfully");
				session.setAttribute("empno",EMPNO);
				 EmployeeHandler eh=new EmployeeHandler();
				 EmployeeBean eb=eh.getEmployeeInformation(EMPNO);
				 
				 session.setAttribute("empNameNO",eb.getFNAME()+" "+eb.getLNAME()+":"+eb.getEMPCODE()+":"+EMPNO);
				 
				
				response.sendRedirect("LoanServlet?EMPNO="+session.getAttribute("empNameNO")+"&action=getdata&flag=3");
			}
			else
			{
				System.out.println("Error in updating record");
				response.sendRedirect("LoanServlet?EMPNO="+session.getAttribute("empNameNO")+"&action=getdata&flag=4");
				
			}
			
		}
		
		
	}

}
