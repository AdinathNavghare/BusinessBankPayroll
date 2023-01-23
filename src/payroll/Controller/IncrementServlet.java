package payroll.Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.DAO.IncrementHandler;
import payroll.Model.IncrementBean;
import sun.reflect.generics.visitor.Reifier;

/**
 * Servlet implementation class IncrementServlet
 */
@WebServlet("/IncrementServlet")
public class IncrementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IncrementServlet() {
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
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		int loggedEmployeeNo = (Integer)session.getAttribute("EMPNO");
		String action = request.getParameter("action")==null?"":request.getParameter("action");
		String action1 = request.getParameter("action1")==null?"":request.getParameter("action1");
		
		if(action.equalsIgnoreCase("saveDetails")){
			
			IncrementBean bean = new IncrementBean();
			IncrementHandler inhandler = new IncrementHandler();
			
			String checkbox[] = request.getParameterValues("checklist");
			 
			 int empno = 0;
			 int grade = 0;
			 boolean result = false;
			 
			for(int i=0;i<checkbox.length; i++){
		    	
			       empno = Integer.parseInt(checkbox[i]);       
			       grade = Integer.parseInt(request.getParameter("grade"));
			       
			       System.out.println("@@@@@@@@@@@@@@@@@@@"+request.getParameter("code_"+empno));
			       
			       bean.setEmpno(Integer.parseInt(checkbox[i]));
			       bean.setEmpName(request.getParameter("name_"+empno));
			       bean.setEmpCode(request.getParameter("code_"+empno));
			       bean.setDa(Float.parseFloat(request.getParameter("da_"+empno)));
			       bean.setBasic( Float.parseFloat(request.getParameter("basic_"+empno)) );
			       bean.setHra(Float.parseFloat(request.getParameter("hra_"+empno)));
			       bean.setVda(Float.parseFloat(request.getParameter("vda_"+empno))  );
			       bean.setGrade(Integer.parseInt(request.getParameter("grade")));
			       bean.setNewBasic(Float.parseFloat(request.getParameter("newBasic_"+empno)));
			       bean.setStage(Integer.parseInt(request.getParameter("stage_"+empno)) );
			       System.out.println("ak11  "+request.getParameter("stage_"+empno));

			       bean.setNewstage( Integer.parseInt(request.getParameter("newStage_"+empno)));
			       System.out.println("ak11  "+request.getParameter("newStage_"+empno));

			     
			       result = inhandler.insertIncrementSaved(bean,loggedEmployeeNo);
			       System.out.println("result @@@@@"+ result);
				}		
			System.out.println("the result is"+result);
			if(result){
				response.sendRedirect("Increament.jsp?action=saveDetails&grade="+grade+"&flag=1");
			}
			else{	
				response.sendRedirect("Increament.jsp?flag=2");	
			}	 
		
		}
		// action1 is for processDetials use if block
		if(action1.equalsIgnoreCase("processDetials")){
			
			System.out.println("i am in processDetials @@@");
			IncrementBean bean = new IncrementBean();
			IncrementBean bean1 = new IncrementBean();
			IncrementHandler inhandler = new IncrementHandler();
			
			String checkbox[] = request.getParameterValues("checklist");
			
			ArrayList<IncrementBean> list = new ArrayList<IncrementBean>();
			 int empno = 0;
			 int grade = 0;
			 boolean result = false;
			 boolean result1 = false;
			 
			for(int i=0;i<checkbox.length; i++){
		    	   
				empno = Integer.parseInt(checkbox[i]);       
			    grade = Integer.parseInt(request.getParameter("grade"));
			       
			       bean.setEmpno(empno);
			       bean.setEmpName(request.getParameter("name_"+empno));
			       bean.setEmpCode(request.getParameter("code_"+empno));
			       bean.setDa(Float.parseFloat(request.getParameter("da_"+empno)));
			       bean.setBasic( Float.parseFloat(request.getParameter("basic_"+empno)) );
			       bean.setHra(Float.parseFloat(request.getParameter("hra_"+empno)));
			       bean.setVda(Float.parseFloat(request.getParameter("vda_"+empno))  );
			       bean.setGrade(Integer.parseInt(request.getParameter("grade")));
			 //      bean.setNewBasic(Float.parseFloat(request.getParameter("newBasic_"+empno)));
			       bean.setStage(Integer.parseInt(request.getParameter("stage_"+empno)) );
			       
			       System.out.println("vkkk111  "+request.getParameter("stage_"+empno));
			       bean.setNewstage( Integer.parseInt(request.getParameter("newStage_"+empno)));
			       System.out.println("kkkk1225 "+request.getParameter("newStage_"+empno));
			       
			       // get data from grade_master table for new stage of increment using (grade & stage)
			       bean1   = inhandler.getProcessedEmpDetails(bean);
			
			       bean.setNewBasic(bean1.getNewBasic());
			       bean.setDa(bean1.getDa());
			       bean.setHra(bean1.getHra());
			       bean.setVda(bean1.getVda());
			       bean.setMed(bean1.getMed());
			       bean.setEdu(bean1.getEdu());
			       bean.setConv(bean1.getConv());
			       
			       //insert data into increment for Processed employee... History
			       result = inhandler.insertIncrementProcessed(bean,loggedEmployeeNo);
			       System.out.println("result @@@@@"+ result);
			       
			       System.out.println("@@@@@@@@@@@@loggedEmployeeNo @@@@@@@@@@"+loggedEmployeeNo);
			       // update Salary Structure here......
			       result1 = inhandler.processIncrementDetails(bean,loggedEmployeeNo);
			       System.out.println("result @@@@@"+ result1);
				}		
		
			if(result == true && result1 ==true ){
				response.sendRedirect("Increament.jsp?action=getDtails&grade="+grade+"&flag=3");
			}
			else{	
				response.sendRedirect("Increament.jsp?flag=4");	
			}	 
		
		}
		
	}

}
