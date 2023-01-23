
package payroll.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.Core.ReportDAO;
import payroll.DAO.BonusHandler;
import payroll.DAO.ConnectionManager;
import payroll.Model.BonusBean;

/**
 * Servlet implementation class BonusServlet
 */
@WebServlet("/BonusServlet")
public class BonusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BonusServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session= request.getSession();	
		PrintWriter out = response.getWriter();
		int loggedEmployeeNo = (Integer)session.getAttribute("EMPNO");

		String action = request.getParameter("action")==null?"":request.getParameter("action");
		String user = session.getAttribute("EMPNO").toString();
		// New Method BY Harshal......
		if(action.equalsIgnoreCase("ApplyBonus"))
		{		
			/*Connection cn = ConnectionManager.getConnection();
			try
			{
				boolean flag = false;
				int f = -1;
				String date = request.getParameter("date");
				float percent = Float.parseFloat(request.getParameter("percent"));
				String empList = request.getParameter("list");
				String emp[]=empList.split(",");
					
				System.out.println("Number of Employee " + emp.length);
				// Method using Thread
				//flag = BonusHandler.postBonus(empList, percent,date);
				flag = BonusHandler.applyBonus(emp, percent,date,user);
				
				if (flag){
					 f = 0;
				}
				response.setContentType("text/html");
				switch(f)
				{	
					case -1	:out.write("No Employees found for Calculation");
							break;
					case  0	:out.write("Bonus Applyed Successfully");
							break;
					default	:out.write("Some Error has occurred while Processing Bonus, Please try again!");
				}	
				
			} catch (Exception e) {
				e.printStackTrace();
			}*/
			
		}else if(action.equalsIgnoreCase("PostBonus"))
		{		
			Connection connection = ConnectionManager.getConnection();
			try
			{
				boolean flag = false;
				int f = -1;
				String date = request.getParameter("date");
				System.out.println("the date is : "+date);
				String empList = request.getParameter("list");
				String emp[]=empList.split(",");
				
				System.out.println("Number of Employee " + emp.length);
				// Method using Thread
				//flag = BonusHandler.postBonus(empList, percent,date);
				Statement statement = connection.createStatement();	
				//statement.execute("insert into bonus values () ");
				flag = BonusHandler.postBonus(emp,date,user);
			
				if (flag){
					 f = 0;
				}
				response.setContentType("text/html");
				switch(f)
				{	
					case -1	:out.write("No Employees found for Calculation");
							break;
					case  0	:out.write("Bonus Applied Successfully");
							break;
					default	:out.write("Some Error has occurred while Processing Bonus, Please try again!");
				}	
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		else if(action.equalsIgnoreCase("saveDetails")){
			
			BonusBean bonusBean = new BonusBean();
			BonusHandler bonusHandler= new BonusHandler();
			ArrayList<BonusBean> bonusList=new ArrayList<BonusBean>();
			String checkbox[] = request.getParameterValues("checklist");
			 int year=Integer.parseInt(ReportDAO.getServerDate().substring(7, 11));
			 System.out.println("the year is "+checkbox.length);
			 int empno = 0;
			 int grade = 0;
			 boolean result = false;
			 
			for(int i=0;i<checkbox.length; i++){
		    	
			       empno = Integer.parseInt(checkbox[i]);       
			      
			       
			       bonusBean.setEmpno(empno);
			       bonusBean.setEmpName(request.getParameter("name_"+empno));
			       bonusBean.setEmpCode(request.getParameter("code_"+empno));
			       bonusBean.setBonus(Float.parseFloat(request.getParameter("bon_"+empno)));
			       bonusBean.setAmtForBonus( Float.parseFloat(request.getParameter("amt_"+empno)) );
			       
			       try {
					result = bonusHandler.applyBonus(bonusBean,loggedEmployeeNo,year);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			      // System.out.println("result @@@@@"+ result);
				}		
			//System.out.println("the result is"+result);
			if(result){
				response.sendRedirect("ApplyBonus.jsp?flag=1");
			}
			else{	
				response.sendRedirect("ApplyBonus.jsp?flag=2");	
			}	 
		
		}

		// for thread
		/*if(action.equalsIgnoreCase("postBonus"))
		{		
			Connection cn = ConnectionManager.getConnection();
			try
			{
				boolean flag = false;
				int f = -1;
				String date = request.getParameter("date");
				int percent = Integer.parseInt(request.getParameter("percent"));
				String empList = request.getParameter("list");
				String emp[] = empList.split(",");
					
				System.out.println("Number of Employee In servlet " + emp.length);
				// Method using Thread
				flag = BonusHandler.postBonus(empList, percent,date,user);
	
				if (flag)
				{
					Statement st = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);		
					st.execute("insert into paytran Select * from bonustran where trndt  between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"'   ");
					//st1.execute("delete from bonustran ");
					 f = 0;
				}
				
				response.setContentType("text/html");
				switch(f)
				{	
					case -1	:out.write("No Employees found for Calculation");
							break;
					case  0	:out.write("Payroll Calculation is done Successfully");
							break;
					default	:out.write("Some Error has occurred while Calculation, Please try again!");
				}	
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		*/
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// Old Method for Bonus
		/*String action = request.getParameter("action")==null?"":request.getParameter("action");
		if(action.equalsIgnoreCase("postBonus"))
		{
			String totalemp="";
			//System.out.println("inside post in bonus servlet");
			String checkbox[]=request.getParameterValues("list");
		
			for(int i=0;i<checkbox.length; i++){
			       totalemp += checkbox[i]+",";
				}
			
			
		     
		  	 if (totalemp.length() > 0 && totalemp.charAt(totalemp.length()-1)==',') {
		  		totalemp = totalemp.substring(0, totalemp.length()-1);
			    }
			
		  	 System.out.println("total employee "+totalemp);
		  	 
			Connection cn = ConnectionManager.getConnection();
			try {
				Statement st = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				Statement st1 = cn.createStatement();
				
				
				st.execute("insert into paytran Select * from bonustran where  empno in ("+totalemp+") ");
		        st1.execute("delete from bonustran where empno in ("+totalemp+") ");		
			    response.sendRedirect("YearlyBonusReport.jsp?flag=2");
				
				
	        	} catch (SQLException e) {
				response.sendRedirect("YearlyBonusReport.jsp?flag=3");
				e.printStackTrace();
	   	}
			
		}
		*/
		
		else if(action.equalsIgnoreCase("bonusCal")){
			BonusBean bonusBean = new BonusBean();
			BonusHandler bonusHandler= new BonusHandler();
			String [] checkbox = request.getParameterValues("checklist");
			 int year=Integer.parseInt(ReportDAO.getServerDate().substring(7, 11));
			 int empno = 0;
			
			 boolean result = false;
			for(int i=0;i<checkbox.length; i++){
		    	//System.out.println(i);
			       empno = Integer.parseInt(checkbox[i]);       
			      //empno=716;
			       
			       bonusBean.setEmpno(empno);
			       
			      // bonusBean.setEmpName(request.getParameter("name_"+empno));
			       //System.out.println(bonusBean.getEmpno());
			       bonusBean.setEmpCode(request.getParameter("code_"+empno));
			     //  System.out.println(bonusBean.getEmpCode());
			       bonusBean.setApramt(Float.parseFloat(request.getParameter("apr_"+empno)));
			      // System.out.println(bonusBean.getApramt());
			       bonusBean.setMayamt(Float.parseFloat(request.getParameter("may_"+empno)));
			       bonusBean.setJunamt(Float.parseFloat(request.getParameter("jun_"+empno)));
			  /*     if(empno==635)
			    	   System.out.println("bonusBean.getJunamt : "+bonusBean.getJunamt());*/
			      bonusBean.setJulamt(Float.parseFloat(request.getParameter("jul_"+empno)));
			       
			    //   System.out.println("request.getParameter(jul)"+request.getParameter("jul_"+empno));
			    //   bonusBean.setJulamt(1000);
			       bonusBean.setAugamt(Float.parseFloat(request.getParameter("aug_"+empno)));
			       bonusBean.setSepamt(Float.parseFloat(request.getParameter("sep_"+empno)));
			       bonusBean.setOctamt(Float.parseFloat(request.getParameter("oct_"+empno)));
			       bonusBean.setNovamt(Float.parseFloat(request.getParameter("nov_"+empno)));
			       bonusBean.setDecamt(Float.parseFloat(request.getParameter("dec_"+empno)));
			       bonusBean.setJanamt(Float.parseFloat(request.getParameter("jan_"+empno)));
			       bonusBean.setFebamt(Float.parseFloat(request.getParameter("feb_"+empno)));
			       bonusBean.setMaramt(Float.parseFloat(request.getParameter("mar_"+empno)));
			       bonusBean.setAmtForBonus(Float.parseFloat(request.getParameter("amt_"+empno)));
			       bonusBean.setPercent(Float.parseFloat(request.getParameter("percent")));
			       bonusBean.setBonus(Float.parseFloat(request.getParameter("bon_"+empno)));
			    
			     //  bonusBean.setSite_id(Integer.parseInt(request.getParameter("site_"+empno)));
			      
			       try {
					result = bonusHandler.addToBonusCal(bonusBean,loggedEmployeeNo,year);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			       //System.out.println("result @@@@@"+ result);
				}		
			//System.out.println("the result is"+result);
			if(result){
				response.sendRedirect("YearlyBonusReport.jsp?flag=1");
			}
			else{	
				response.sendRedirect("YearlyBonusReport.jsp?flag=2");	
			}	 
		}
	
	}

}
