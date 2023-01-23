package payroll.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.Core.ReportDAO;
import payroll.Core.VDA_Calculation;
import payroll.DAO.VdaDAO;
import payroll.Model.VdaBean;

/**
 * Servlet implementation class BranchServlet
 */
@WebServlet("/VdaServlet")
public class VdaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VdaServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action")==null?"":request.getParameter("action");
		
		if(action.equalsIgnoreCase("calculateVda")){
		System.out.println("in calculateVda");
	    String daApplicableDate=request.getParameter("daApplicableDate");

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MMM-yyyy"); 
			Date tempDate=new Date();
		 
			try {
				tempDate=simpleDateFormat.parse(daApplicableDate);
				daApplicableDate=outputDateFormat.format(tempDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
			}
			
			System.out.println("daApplicableDate  : "+daApplicableDate);
			
	  /* if(!daApplicableDate.equals("01-Jan-2016")){
		VDA_Calculation.vda_Calc_new1(daApplicableDate, "desig");
			//	VDA_Calculation.vda_Calc_latest(daApplicableDate,"desig");
			System.out.println("..........EXECUTED SUCCESSFULY FOR GRADE wise....");
			
			
			VDA_Calculation.vda_Calc_new1(daApplicableDate, "emp");	
			System.out.println("..........EXECUTED SUCCESSFULY FOR EMPLOYEEWISE....");
	   }
	   else
	   {*/
		 //  VDA_Calculation.vda_Calc_latest(daApplicableDate,"desig");
			  VDA_Calculation.vda_Calc_latest(daApplicableDate);
		   System.out.println("..........EXECUTED SUCCESSFULY FOR GRADE wise  ....");
		   System.out.println("..........akshay 00000000 @123  ....");
	   
		//	VDA_Calculation.vda_Calc_latest(daApplicableDate,"emp");
			System.out.println("..........EXECUTED SUCCESSFULY FOR EMPLOYEEWISE  ....");
			System.out.println("..........akshay 00000000 @123  6789 ....");
	  /* }	*/
			int flag=1;
			//	System.out.println("flag on vdaservlet"+flag);
				
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				switch(flag)
				{
					case 1:
							out.write("Vda calculation done successfully");
							break;
				
					default:
						out.write("Error in Vda calculation ");
				}
		
		
		}

		if(action.equalsIgnoreCase("finalize")){
			System.out.println("in finalize");
			String date=request.getParameter("date")==null?"":request.getParameter("date");
			date="01-"+date;
			VdaDAO vdaDAO=new VdaDAO();
			boolean flag = false;
			try {
				flag = vdaDAO.finalizeTheSlab(date);
				System.out.println("the flag is"+flag);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(flag){
				response.sendRedirect("VDA_Posting.jsp?flag=5");
				
			}else{
				response.sendRedirect("VDA_Posting.jsp?flag=6");
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action")==null?"":request.getParameter("action");
		ArrayList<VdaBean> vdaList = new ArrayList<VdaBean>();
		HttpSession session = request.getSession();
		VdaDAO vdaDAO  = new VdaDAO();
		String todaysDate=request.getParameter("todaysDate");


		int loggedEmployee=Integer.parseInt(request.getParameter("loggedEmployee"));
		if(action.equalsIgnoreCase("AddVda")){
			VdaBean vdaBean = new VdaBean();
			vdaBean.setDaApplicableDate(request.getParameter("daApplyDate")==null?"":request.getParameter("daApplyDate"));
			String daApplyDate=vdaBean.getDaApplicableDate();
			SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
			Date temp=new Date();
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			  
			Date tempDate=new Date();
			Date tempDate1=new Date();
			try {
				temp=format.parse(daApplyDate);
			
				daApplyDate=simpleDateFormat.format(temp);
				
				tempDate=simpleDateFormat.parse(daApplyDate);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		  Calendar calendar = Calendar.getInstance();
		  calendar.setTime(tempDate);
		  calendar.set(Calendar.DAY_OF_MONTH, 1);
		  vdaBean.setDaApplicableDate( simpleDateFormat.format(calendar.getTime()));	 
			
			System.out.println("BASIC  : "+request.getParameter("fixBasic"));
			
/*			vdaBean.setFixBasic(request.getParameter("fixBasic")==null?0.0f:Float.parseFloat(request.getParameter("fixBasic")));
			vdaBean.setDaDivisor(request.getParameter("daDivisor")==null?0.0f:Float.parseFloat(request.getParameter("daDivisor")));
			vdaBean.setCPIValue(request.getParameter("CPIValue")==null?0.0f:Float.parseFloat(request.getParameter("CPIValue")));
*/	
			vdaBean.setFixBasic(Float.parseFloat("500"));
			vdaBean.setDaDivisor(Float.parseFloat("3"));
			vdaBean.setCPIValue(Float.parseFloat("4.93"));

			
			vdaBean.setVdaDate (request.getParameter("vdaDate")==null?"":request.getParameter("vdaDate"));
			String vdaDate=vdaBean.getDaApplicableDate();
			 simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			  
			 tempDate=new Date();
			
			try {
				tempDate=simpleDateFormat.parse(vdaDate);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		   calendar = Calendar.getInstance();
		  calendar.setTime(tempDate);
		  calendar.set(Calendar.DAY_OF_MONTH, 1);
		  vdaBean.setVdaDate( simpleDateFormat.format(calendar.getTime()));	 
			
			
			vdaBean.setMonth(request.getParameter("firstMonthVda")==null?"":request.getParameter("firstMonthVda"));
			String firstMonthVda=vdaBean.getMonth();

			
		
			try {
				temp=format.parse(firstMonthVda);
				
				firstMonthVda=simpleDateFormat.format(temp);
				
				tempDate=simpleDateFormat.parse(firstMonthVda);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		   calendar = Calendar.getInstance();
		  calendar.setTime(tempDate);
		  calendar.set(Calendar.DAY_OF_MONTH, 1);
		  vdaBean.setMonth( simpleDateFormat.format(calendar.getTime()));	 
			
		System.out.println("INDEX : "+request.getParameter("firstMonthIndex"));	
			vdaBean.setIndex(request.getParameter("firstMonthIndex")==null?0.0f:Float.parseFloat(request.getParameter("firstMonthIndex")));

     
/*String thirdMonthVda=request.getParameter("thirdMonthVda");

 simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	 tempDate=new Date();
  try {
	tempDate=simpleDateFormat.parse(thirdMonthVda);
} catch (ParseException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}*/ 
  Calendar c = Calendar.getInstance();
  c.setTime(tempDate);
  c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));

  System.out.println("DATE CHECK2 : "+firstMonthVda);

  try {
	tempDate1=simpleDateFormat.parse(firstMonthVda);
} catch (ParseException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
  Calendar c1 = Calendar.getInstance();
  c1.setTime(tempDate1);
  c1.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));

  String endDate1= simpleDateFormat.format(c1.getTime());
  System.out.println("DATE CHECK : "+endDate1);
  
  String endDate= simpleDateFormat.format(c.getTime());
System.out.println("DATE CHECK : "+vdaDate);
System.out.println("DATE CHECK1 : "+ReportDAO.EOM1(vdaDate));
  
		//	vdaBean.setStatus(request.getParameter("status")==null?"":request.getParameter("status"));	
			//vdaBean.setEndDate(endDate);
vdaBean.setEndDate(endDate1);
			
	//		vdaBean.setCFPIValue (request.getParameter("CFPIValue")==null?0.0f:Float.parseFloat(request.getParameter("CFPIValue")));
			vdaBean.setCFPIValue (Float.parseFloat("4.63"));

			if(vdaDAO.getMaxBatchId()!=0){
				vdaBean.setBatchId(vdaDAO.getMaxBatchId()+1);
			}
			else{
				vdaBean.setBatchId(1);
			}
			boolean flagOne= vdaDAO.addVda(vdaBean,todaysDate,loggedEmployee);
			vdaBean.setMonth(request.getParameter("secondMonthVda")==null?"":request.getParameter("secondMonthVda"));

			vdaBean.setIndex(request.getParameter("secondMonthIndex")==null?0.0f:Float.parseFloat(request.getParameter("secondMonthIndex")));
			//boolean flagTwo= vdaDAO.addVda(vdaBean,todaysDate,loggedEmployee);
			vdaBean.setMonth(request.getParameter("thirdMonthVda")==null?"":request.getParameter("thirdMonthVda"));	
			vdaBean.setIndex(request.getParameter("thirdMonthIndex")==null?0.0f:Float.parseFloat(request.getParameter("thirdMonthIndex")));	
			//boolean flagThree= vdaDAO.addVda(vdaBean,todaysDate,loggedEmployee);
			//	vdaList = vdaDAO.getBranchDetails();
			session.setAttribute("vdaList", vdaList);
			//if(flagOne && flagTwo && flagThree)
			if(flagOne)
			{
				response.sendRedirect("VDA.jsp?flag1=1");
			}
			else
			{
				response.sendRedirect("VDA.jsp?flag1=2");
			}


		}

	}

}
