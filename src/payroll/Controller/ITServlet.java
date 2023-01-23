package payroll.Controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import payroll.Model.ITBean;
import payroll.DAO.ITHandler;



@WebServlet({"/ITServlet"})
public class ITServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ITServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//String action = request.getParameter("action")==null?"":request.getParameter("action");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ITBean  itBean = new ITBean();
		ITHandler itHandler=new ITHandler();  
		HttpSession session = request.getSession();
	//	ArrayList<ITBean> customSearchList= new ArrayList<ITBean>();
		ArrayList<Integer> customSearchList1= new ArrayList<Integer>();
		java.util.Collections.sort(customSearchList1);
		boolean result = false;
		//RequestDispatcher rd;
		String action = request.getParameter("action")==null?"":request.getParameter("action");
		System.out.println("THE ACTION IS  "+action);
		int year=Integer.parseInt(session.getAttribute("year").toString());
		String yearString="";
		String temp=Integer.toString(year+1).toString();
		yearString=Integer.toString(year)+"-"+temp.substring(2,4);
		System.out.println(yearString);
		System.out.println("kkk"+Float.parseFloat(request.getParameter("tgsa")));
		
	
		
		if(action.equalsIgnoreCase("create")){
			System.out.println(request.getParameter("tgsa").toString());
			float grossSalary=Float.parseFloat(request.getParameter("tgsa").toString())+
					Float.parseFloat(request.getParameter("teus"))+
					Float.parseFloat (request.getParameter("tacp"))+
					Float.parseFloat(request.getParameter("tics"))+
					//Float.parseFloat(request.getParameter("tichp"))+
					Float.parseFloat(request.getParameter("tichl"))+
					Float.parseFloat(request.getParameter("ticnr"))+
					Float.parseFloat(request.getParameter("tducvi"))+
					Float.parseFloat(request.getParameter("tduc"));
			itBean.setEmpno(Integer.parseInt(session.getAttribute("empno1").toString()));
				System.out.println(itBean.getEmpno());
				itBean.setYear(yearString);
				itBean.setGrossSalary(grossSalary);
				itBean.setTotalDeduction(itHandler.getDeduction(itBean.getEmpno()));
				itBean.setNetTaxableIncome(Float.parseFloat(request.getParameter("tnti")));
				//itBean.setNetTaxableIncome((Integer.parseInt(session.getAttribute("empno1").toString())));
				itBean.setTaxCredit(year==2015?2000:5000);
				
				itBean.setTaxAfterReducingCredit(Float.parseFloat(request.getParameter("ttoi")));
				itBean.setTaxOnIncome(year==2015?2000+itBean.getTaxAfterReducingCredit()
						:5000+itBean.getTaxAfterReducingCredit());
				itBean.setTotalTaxLiability(Float.parseFloat(request.getParameter("tttl")));
				itBean.setTotalTaxRemaining(Float.parseFloat(request.getParameter("titd")));
				itBean.setRemainingMonths(Float.parseFloat(request.getParameter("trmy")));
				itBean.setFinalizedMonths(12-itBean.getRemainingMonths());
				//itBean.setMonthlyInstallment(Math.round(itBean.getTotalTaxRemaining()/(itHandler.generic("remainingmonths",Integer.toString(year), itBean.getEmpno()))));
			//	System.out.println("value: "+Math.round(itBean.getMonthlyInstallment()));
				itBean.setStatus("PENDING");
				
				result = itHandler.addToIT(itBean);
				
			if(result)
					response.sendRedirect("taxCompute.jsp?flag=1");
					else
						response.sendRedirect("taxCompute.jsp?flag=2");
		}
	}
	}
		
		