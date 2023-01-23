package payroll.Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.StyledEditorKit.BoldAction;

import payroll.DAO.RetirementHandler;
import payroll.Model.RetirementBean;

/**
 * Servlet implementation class RetirementServlet
 */
@WebServlet("/RetirementServlet")
public class RetirementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RetirementServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try{
			String action = request.getParameter("action")==null?"":request.getParameter("action");
			HttpSession session=request.getSession();
			int loggedEmployeeNo = (Integer)session.getAttribute("EMPNO");
			
			// To encash Leave Amount for retirement employee
			if(action.equalsIgnoreCase("encashEmpList")){
				
				String checkbox[] = request.getParameterValues("checklist");
				RetirementBean rbean = new RetirementBean();
	 			RetirementHandler eHandler =  new RetirementHandler();
				ArrayList<RetirementBean> list= new ArrayList<RetirementBean>();
				
				
				 int empno=0;
				 float amtbyemp; 
				 float days; 	
				 float basic; 	
				 float da; 	
				 float vda; 	
				 String retirementDate; 
				 boolean result = false;
				 
				for(int i=0;i<checkbox.length; i++){
				    	
					       empno = Integer.parseInt(checkbox[i]);       
					       amtbyemp = Float.parseFloat(request.getParameter("amt_"+empno));	
					       basic = Float.parseFloat(request.getParameter("basic_"+empno));
					       
					      // System.out.println("@@@@@@@@@@"+Integer.parseInt(request.getParameter("days_"+empno)));
					       
					       days = Float.parseFloat(request.getParameter("days_"+empno));	
					       
					       da = Float.parseFloat(request.getParameter("da_"+empno));	
					       vda = Float.parseFloat(request.getParameter("vda_"+empno));	
					       retirementDate = (request.getParameter("date_"+empno));	
					       
					       rbean.setEmpno(empno);
					       rbean.setAmount(amtbyemp);
					       rbean.setDays(days);
					       rbean.setBasic(basic);
					       rbean.setDa(da);
					       rbean.setVda(vda);
					       rbean.setRetirmentDate(retirementDate);
					       
					       result = eHandler.encashEmpListforRetirement(rbean,loggedEmployeeNo);
					       
						}								    
						System.out.println("the result is"+result);
						if(result){
							response.sendRedirect("RetirementEncashList.jsp?flag1=1");
						}
						else{
							response.sendRedirect("RetirementEncashList.jsp?flag1=2");	
						}	 
			}
		}catch(Exception e){e.printStackTrace();}
		
		
		
		
		
		
		
	}

}
