package payroll.Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.DAO.AdvanceHandler;
import payroll.DAO.EmpOffHandler;
import payroll.Model.AdvanceBean;
import payroll.Model.EmpOffBean;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;



@WebServlet({"/AdvanceServlet"})
public class AdvanceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdvanceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action")==null?"":request.getParameter("action");
		if(action.equalsIgnoreCase("newAdvance"))
		{

			System.out.println("in Advance servlet");
			String date = request.getParameter("date");
			String month= request.getParameter("month");
			
			String Table_name = request.getParameter("btn");
			String type = request.getParameter("typ");
			String format=request.getParameter("frmt");
			
			String filename="";
			filename=format.equalsIgnoreCase("xls")?"NewAdvanceReport.xls":"NewAdvanceReport.pdf";
			
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			String filePath = getServletContext().getRealPath("")+ File.separator + ""+filename;
		
			
				File file = new File(filePath);
				
				if (file.exists())
				{
					file.delete();
				}
				try
				{
					FileOutputStream out1 = new FileOutputStream(new File(filePath));
				    
					Document doc  = new Document();
					PdfWriter.getInstance(doc, new FileOutputStream(filePath));
					
				}
				catch ( Exception e) 
				{
					e.printStackTrace();
				}
			
			
			{
				AdvanceHandler.newAdvance(date,Table_name,imagepath, filePath,type);	
			}
			
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.write("<iframe scrolling=\"auto\" src=\""+filename+"\" height=\"450px\" width=\"100%\"></iframe>");
		
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		AdvanceBean advanceBean = new AdvanceBean();
		AdvanceHandler advanceHandler=new AdvanceHandler();
		HttpSession session = request.getSession();
		ArrayList<AdvanceBean> customSearchList= new ArrayList<AdvanceBean>();
		boolean result = false;
		RequestDispatcher rd;
		String action = request.getParameter("action")==null?"":request.getParameter("action");
		
		
		PrintWriter out = response.getWriter();
		
		if(action.equalsIgnoreCase("add")){
			int eno = (Integer)session.getAttribute("EMPNO");
			
			EmpOffBean eoffbn = new EmpOffBean();
			EmpOffHandler eoffhdlr = new EmpOffHandler();
			eoffbn = eoffhdlr.getEmpOfficAddInfo(Integer.toString(eno));
			
			String projectname = request.getParameter("project");
	
			
		//System.out.println("PROJECT   :   "+projectname);
	
		String prj[]=projectname.split("::");
		
		String code=prj[1];
//System.out.println("1"+a1);
//System.out.println("CODE is      "+code);
		//System.out.println("SPLITTED        "+projectname.split("::", 1) );
		
		
	
			
			advanceBean.setSite_id(Integer.parseInt(code));
			String date1=request.getParameter("date");
			String date2="01-"+request.getParameter("month")+"-"+date1;
		
			advanceBean.setEmpNo(request.getParameter("emplist")==null?0:Integer.parseInt(request.getParameter("emplist")));
			advanceBean.setRequestDate(request.getParameter("tdate")==null?"":(request.getParameter("tdate")));
			advanceBean.setAdvanceAmtRequested(request.getParameter("amount")==null?0:Integer.parseInt(request.getParameter("amount")));
			advanceBean.setForMonth(date2);
			advanceBean.setSanctionBy(eno);
		//	advanceBean.setCreatedDate(request.getParameter("tdate")==null?"":(request.getParameter("tdate")));
			result = advanceHandler.addAdvance(advanceBean);
			
				//rd = request.getRequestDispatcher("Advance.jsp");
		//		response.sendRedirect("Advance.jsp?flag=3");
		//	System.out.println("RESULT"+result);
			if(result)
			response.sendRedirect("Advance.jsp?action=getdetails&prj="+code+"&flag=3");
			else
				response.sendRedirect("Advance.jsp?action=getdetails&prj="+code+"&flag=4");
			//rd.forward(request, response);
			
		}
		
		
		if(action.equalsIgnoreCase("sanctionAdvanceApp")){	
			boolean flag = false;
			int empno = request.getParameter("empno")==null?0:Integer.parseInt(request.getParameter("empno"));
			int appNo = request.getParameter("appNo")==null?0:Integer.parseInt(request.getParameter("appNo"));
			int amount= request.getParameter("amt")==null?0:Integer.parseInt(request.getParameter("amt"));
			int eno= request.getParameter("ENO")==null?0:Integer.parseInt(request.getParameter("ENO"));
		
			/*System.out.println("number"+empno);
			System.out.println("number11111111111111"+appNo);
			System.out.println("amount....----------"+amount);
			System.out.println("EMPNO OF USER WHO HAVE LOGGED IN....----------"+eno);*/
			try {
				flag = advanceHandler.setSanction(empno, appNo, amount,eno);
			//	System.out.println("value of flag after return of method.."+flag);
				
				if(flag == true){
					out.write("true");
				}				
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			advanceBean = (AdvanceBean)session.getAttribute("advanceSearchFilter");
			customSearchList = advanceHandler.getAdvanceAppList(advanceBean, "custom");
			request.setAttribute("customSearchList", customSearchList);
		//	System.out.println("valueeeeeeeeeeeeeeeeeeeeeeeeeeee"+flag);
			/*if(flag == false){
				 rd = request.getRequestDispatcher("sanctionAdvance.jsp?action=customSearch&error=Please Check Leave Balance");
			}else{
				rd = request.getRequestDispatcher("sanctionAdvance.jsp?action=customSearch&error=Advanced Sanctioned Successfully");
			}
			rd.forward(request, response);*/
		}
		
		if(action.equalsIgnoreCase("cancelAdvanceApp")){
			
			int empno = request.getParameter("empno")==null?0:Integer.parseInt(request.getParameter("empno"));
			int appNo = request.getParameter("appNo")==null?0:Integer.parseInt(request.getParameter("appNo"));
			int eno= request.getParameter("ENO")==null?0:Integer.parseInt(request.getParameter("ENO"));
			
			advanceHandler.cancelAdvanceApp(empno, appNo, eno);
			out.write("true");
			advanceBean = (AdvanceBean) session.getAttribute("advanceSearchFilter");
			customSearchList = advanceHandler.getAdvanceAppList(advanceBean, "custom");
			request.setAttribute("customSearchList", customSearchList);
			
			//rd = request.getRequestDispatcher("sanctionLeave.jsp?action=customSearch&error=Leave Canceled");
		    //rd.forward(request, response);
		}
		
		if(action.equalsIgnoreCase("customSearch")){
			
			String type=request.getParameter("type")==null?"":request.getParameter("type");
			String fromemp=request.getParameter("EMPNO")==null?"":request.getParameter("EMPNO");//display on next page
			//String toemp=request.getParameter("EMPNO1")==null?"":request.getParameter("EMPNO1");//display on next page
			String[] employ = fromemp.split(":");
		    int frmEmpNo = Integer.parseInt(employ[2].trim());
		 //   employ = toemp.split(":");
		//    int toEmpNo = Integer.parseInt(employ[2].trim());
			String fromdate=request.getParameter("frmdate")==null?"":request.getParameter("frmdate");
			String todate=request.getParameter("todate")==null?"":request.getParameter("todate");
			advanceBean.setEmpNo(frmEmpNo);
		//	advanceBean.setEmpNo2(toEmpNo);
			advanceBean.setRequestDate(fromdate);
			advanceBean.setRequestDate2(todate);
			advanceBean.setRequestStatus(type);
			session.setAttribute("advanceSearchFilter", advanceBean);
			customSearchList = advanceHandler.getAdvanceAppList(advanceBean, "custom");
			request.setAttribute("customSearchList", customSearchList);
			rd = request.getRequestDispatcher("sanctionAdvance.jsp?action=customSearch");
		    rd.forward(request, response);
		}
		
	}

}
