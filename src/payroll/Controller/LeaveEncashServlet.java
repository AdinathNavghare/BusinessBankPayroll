package payroll.Controller;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.Core.ExcelDAO;
import payroll.Core.ReportDAO;
import payroll.Core.UtilityDAO;
import payroll.DAO.LeaveEncashmentHandler;
import payroll.Model.LeaveEncashmentBean;

/**
 * Servlet implementation class EmpAttendServlet
 */
@WebServlet("/LeaveEncashServlet")
public class LeaveEncashServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LeaveEncashServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		String action=request.getParameter("action");
		
		if (action.equalsIgnoreCase("leaveEncashReport")) 
		{
			String date = request.getParameter("date");	
			String prjCode1 = request.getParameter("prjCode1");	
		
			System.out.println("date in servlet"+ prjCode1);
			
			String filePath = getServletContext().getRealPath("")+ File.separator + "leaveEncashReport.txt";
			UtilityDAO.leaveEncashReport(date,filePath,prjCode1);  // for new format			
						
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.write("<iframe scrolling=\"auto\" src=\"leaveEncashReport.txt\" height=\"400px\" width=\"100%\"></iframe>");
		}	
		else if(action.equalsIgnoreCase("leaveEncashtentetive")){
			System.out.println("i am in servlet");
			try{
			String filePath = getServletContext().getRealPath("")+ File.separator + "LeaveEncashtentetiveReport.pdf";
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			int year = Calendar.getInstance().get(Calendar.YEAR);
			String s=Integer.toString(year);
			File file = new File(filePath);
			if (file.exists()) 
			{
				file.delete();
			}
			UtilityDAO.TentativeLeaveencashmentList(s,filePath,imagepath);
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.write("<iframe scrolling=\"auto\" src=\"LeaveEncashtentetiveReport.pdf\" height=\"500px\" width=\"100%\"></iframe>");
		}
		catch ( Exception e) 
		{
			e.printStackTrace();
		}
		}
		else if (action.equalsIgnoreCase("leaveEncashReportList")) 
		{System.out.println("i am in leaveEncashReportList");
			String date = request.getParameter("date");	
			System.out.println("date ::  "+ date);
			String prjCode1 = request.getParameter("prjCode1");	
			String Sitename = request.getParameter("selbr");
			System.out.println("Sitename::  "+ Sitename);
//			String[] results = {"EMPCODE","NAME","Encash_Days","Basic_f","Da","Vda","TOTAL_AMT","PF_AMT","NET_PAY"};
			String[] results = {"EMPCODE","NAME","Encash_Days","Basic_f","Da","Vda","TOTAL_AMT","NET_PAY"};
			String SelectedBranch = prjCode1;
			String BranchType = prjCode1.equals("1000")?"1":"2";
			String filePath = getServletContext().getRealPath("")+ File.separator + "leaveEncashReport.xls";
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
		//	ExcelDAO.leaveEncashReport(date,filePath,prjCode1);
			String desigtype="0";
			String RangeFrom="0",RangeTo="0";
			// for new format			
			ExcelDAO.encahlistbranchwise(date,results,BranchType,SelectedBranch,desigtype,RangeFrom,RangeTo,Sitename,filePath,imagepath);		
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.write("<iframe scrolling=\"auto\" src=\"leaveEncashReport.xls\" height=\"400px\" width=\"100%\"></iframe>");
		}	
		
		else if (action.equalsIgnoreCase("leaveEncashReportListBranchWies")) 
		{System.out.println("i am in leaveEncashReportListBranchWies");
			String date = request.getParameter("date");	
			System.out.println("date ::  "+ date);
			String prjCode1 = request.getParameter("prjCode1");	
			String Sitename = request.getParameter("selbr");
			System.out.println("Sitename::  "+ Sitename);
			String[] results = {"Branch_Code", "Branch_Name", "No_of_Emp", "Basic_f","Da","Vda","TOTAL_AMT","NET_PAY"};
			String SelectedBranch = prjCode1;
			String BranchType = prjCode1.equals("1000")?"1":"2";
			String filePath = getServletContext().getRealPath("")+ File.separator + "leaveEncashReportAllBranches.xls";
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			String desigtype="0";
			String RangeFrom="0",RangeTo="0";
			ExcelDAO.encahlistbranchwiseNew(date,results,BranchType,SelectedBranch,desigtype,RangeFrom,RangeTo,Sitename,filePath,imagepath);		
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.write("<iframe scrolling=\"auto\" src=\"leaveEncashReportAllBranches.xls\" height=\"400px\" width=\"100%\"></iframe>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		boolean result = false;
		HttpSession session=request.getSession();
		LeaveEncashmentHandler leaveEncashmentHandler =new LeaveEncashmentHandler();
		String action=request.getParameter("action");
		LeaveEncashmentBean leaveEncashmentBean=new LeaveEncashmentBean(); 
		int loggedEmployeeNo = (Integer)session.getAttribute("EMPNO");
		System.out.println("loggedEmployeeNo:-01  "+loggedEmployeeNo);
	  
		//TO INSERT IN TABLE BY HEMANT
		if(action.equalsIgnoreCase("insert"))   
		{
			String emp=request.getParameter("EMPNO1")==null?"":request.getParameter("EMPNO1");	
			String[] employ = emp.split(":");
		    String employeeNo= (employ[2].trim());
			leaveEncashmentBean.setEmpNo(Integer.parseInt(employeeNo));
			leaveEncashmentBean.setLeaveBal(Float.parseFloat(request.getParameter("lbal")));
			leaveEncashmentBean.setMaxLimit(Float.parseFloat(request.getParameter("limit")));
			leaveEncashmentBean.setEncashApplicable(Float.parseFloat(request.getParameter("encash")));
			leaveEncashmentBean.setLeaveEncashmentSanction(Float.parseFloat(request.getParameter("lsanction")));	
			leaveEncashmentBean.setLeaveEncashmentDate(request.getParameter("edate"));
			leaveEncashmentBean.setFromDate (request.getParameter("edate")==null?"":request.getParameter("edate"));	
			leaveEncashmentBean.setToDate(request.getParameter("edate")==null?"":request.getParameter("edate"));
			leaveEncashmentBean.setMonthlyGross(Float.parseFloat(request.getParameter("gross")));		
			leaveEncashmentBean.setEsicAmt(Float.parseFloat(request.getParameter("esic")));		
			leaveEncashmentBean.setEncashmentAmt(Float.parseFloat(request.getParameter("ENCASHAMT")));
			result = leaveEncashmentHandler.addLeaveEncash(leaveEncashmentBean,loggedEmployeeNo);
			System.out.println("the result is"+result);
			if(result){
				response.sendRedirect("leaveEncashment.jsp?flag1=1");
			}
				else{
					response.sendRedirect("leaveEncashment.jsp?flag1=2");	
			}
		}  //INSERT ACTION ENDS HERE
		
		//BY HRISHI
		else if(action.equalsIgnoreCase("printEncash"))	
		{
			System.out.println("inside printEncash");
			String date="01-"+request.getParameter("date");
			String todate="30-"+request.getParameter("todate");
			todate=ReportDAO.EOM(todate);
			String employeeType=request.getParameter("Employee");
			System.out.println(""+date+""+todate+""+employeeType);
			
			String EMPNO="";
			String Branch="";
			
			if(employeeType.equalsIgnoreCase("one"))
			{
				System.out.println("for single employee");
				String[] employ = request.getParameter("EMPNO").split(":");
			    String EMPCODE = employ[1].trim();
			     EMPNO = employ[2].trim();
			    System.out.println(""+EMPNO);
			}
			
			if(employeeType.equalsIgnoreCase("all"))
			{
				System.out.println("for all employee");
				
			}
			
			if(employeeType.equalsIgnoreCase("branch"))
			{
				System.out.println("for branch wise");
				Branch=request.getParameter("prjemp");
				System.out.println(""+Branch);
				
			}
			
			
			final int BUFSIZE = 4096;
			String filePath = getServletContext().getRealPath("")+ File.separator + "PrintEncash.pdf";
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			
		//	leaveEncashmentHandler.getPrintEncash(filePath,date,todate, imagepath,employeeType,EMPNO,Branch);
			leaveEncashmentHandler.getPrintEncashNew(filePath,date,todate, imagepath,employeeType,EMPNO,Branch); // with basic salary like pay slip
			File file = new File(filePath);
			int length = 0;
			ServletOutputStream outStream = response.getOutputStream();
			ServletContext context = getServletConfig().getServletContext();
			String mimetype = context.getMimeType(filePath);
			if (mimetype == null) 
			{
				mimetype = "application/octet-stream";
			}
			response.setContentType(mimetype);
			response.setContentLength((int) file.length());
			String fileName = (new File(filePath)).getName();
			response.setHeader("Content-Disposition", "attachment; filename=\""	+ fileName + "\"");
			byte[] byteBuffer = new byte[BUFSIZE];
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			while ((in != null) && ((length = in.read(byteBuffer)) != -1)) 
			{
				outStream.write(byteBuffer, 0, length);
			}
			in.close();
			outStream.close();
			if (file.exists())
			{
				file.delete();
			}
	
		} //end of printencash
		
		else if(action.equalsIgnoreCase("insertEncashDetials"))   //TO INSERT IN TABLES BY HARSHAL
		{
			System.out.println("In insertEncashDetials");
			String emp=request.getParameter("EMPNO1")==null?"":request.getParameter("EMPNO1");
			String[] employ = emp.split(":");
		    String employeeNo= (employ[2].trim());
		    
		    leaveEncashmentBean.setEmpNo(Integer.parseInt(employeeNo));
		//	leaveEncashmentBean.setLeaveBal(Float.parseFloat(request.getParameter("lbal")));
			leaveEncashmentBean.setMaxLimit(Float.parseFloat(request.getParameter("limit")));
			leaveEncashmentBean.setEncashApplicable(Float.parseFloat(request.getParameter("lbal")));
		//	leaveEncashmentBean.setLeaveEncashmentSanction(Float.parseFloat(request.getParameter("lsanction")));
			leaveEncashmentBean.setLeaveEncashmentDate(request.getParameter("edate"));
		
		//  todate and from date having diff of leave days...
			leaveEncashmentBean.setFromDate (request.getParameter("edate")==null?"":request.getParameter("edate"));
			leaveEncashmentBean.setToDate(request.getParameter("edate")==null?"":request.getParameter("edate"));
			leaveEncashmentBean.setMonthlyGross(Float.parseFloat(request.getParameter("gross")));
			
		//  ecic amt is zero 
		//	leaveEncashmentBean.setEsicAmt(Float.parseFloat(request.getParameter("esic")));
			
			leaveEncashmentBean.setEncashmentAmt(Float.parseFloat(request.getParameter("ENCASHAMT")));
				
			result = leaveEncashmentHandler.addLeaveEncash1(leaveEncashmentBean,loggedEmployeeNo);
			
			System.out.println("the result is"+result);
			if(result){
				response.sendRedirect("leaveEncashment.jsp?flag1=1");
			}
			else{
				response.sendRedirect("leaveEncashment.jsp?flag1=2");	
			}
		} 
			
		else if(action.equalsIgnoreCase("encashEmpList"))   //Encash leave amt into paytran BY HARSHAL
		{		
			System.out.println("In Leave Encashment..01");
		    String prjCode = request.getParameter("prjCode");
		    String checkbox[] = request.getParameterValues("checklist");
		    LeaveEncashmentBean LEB = new LeaveEncashmentBean();
		  
		    int empno=0;
		    float amtbyemp; 
		    int days; 	
		    float basic; 	
		    float da; 	
		    float vda; 	
		    String leaveEncashmentDate; 	
		   
		    for(int i=0;i<checkbox.length; i++){
		    	
		       empno = Integer.parseInt(checkbox[i]);       
		       amtbyemp = Float.parseFloat(request.getParameter("amt_"+empno));	
		       basic = Float.parseFloat(request.getParameter("basic_"+empno));	
		       days = Integer.parseInt(request.getParameter("days_"+empno));	
		       da = Float.parseFloat(request.getParameter("da_"+empno));	
		       vda = Float.parseFloat(request.getParameter("vda_"+empno));	
		       leaveEncashmentDate = (request.getParameter("date_"+empno));	
		       
		       LEB.setEmpNo(empno);
		       LEB.setEncashmentAmt(amtbyemp);
		       LEB.setDays(days);
		       LEB.setBasic(basic);
		       LEB.setDa(da);
		       LEB.setVda(vda);
		       LEB.setLeaveEncashmentDate(leaveEncashmentDate);
		       System.out.println("loggedEmployeeNo:--"+loggedEmployeeNo);
		       result = leaveEncashmentHandler.encashEmpList(LEB,loggedEmployeeNo);
		       
			}			
		//	result = leaveEncashmentHandler.addLeaveEncash1(leaveEncashmentBean,loggedEmployeeNo);
		    
			System.out.println("the result is"+result);
			if(result){
				response.sendRedirect("EncashmentList.jsp?flag1=1");
			}
			else{
				response.sendRedirect("EncashmentList.jsp?flag1=2");	
			}
		} 
	
		
		
		
	}
	
	
	

}
