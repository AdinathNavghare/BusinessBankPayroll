package payroll.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.Core.ReportDAO;
import payroll.DAO.ConnectionManager;
import payroll.DAO.LMH;
import payroll.DAO.LookupHandler;
import payroll.Model.LMB;


/**
 * Servlet implementation class LMS
 */
@WebServlet("/LMS")
public class LMS extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LMS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LMB leaveBean = new LMB();
		LMH leaveHandler=new LMH();
		String action = request.getParameter("action")==null?"":request.getParameter("action");
		
		if(action.equalsIgnoreCase("sanctionLeaveApp")){	
			
			int flag = 0;
			int empno = request.getParameter("empno")==null?0:Integer.parseInt(request.getParameter("empno"));
			int appNo = request.getParameter("appNo")==null?0:Integer.parseInt(request.getParameter("appNo"));
			try {
				
				flag = leaveHandler.setSanction(empno, appNo);
			
				if(flag == 0){
					//response.sendRedirect("sanctionLeave.jsp?flag=0");
					response.sendRedirect("sanctionpopup.jsp?flag=0");
					
				}else if(flag == 1){
					//response.sendRedirect("sanctionLeave.jsp?flag=1");
					response.sendRedirect("sanctionpopup.jsp?flag=1");
					
				}
				else if(flag == 2){
					//response.sendRedirect("sanctionLeave.jsp?flag=2");
					response.sendRedirect("sanctionpopup.jsp?flag=2");
					
				}
				
				
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			//leaveBean = (LMB)session.getAttribute("leaveSearchFilter");
			//customSearchList = leaveHandler.getLeaveAppList(leaveBean, "custom");
			//request.setAttribute("customSearchList", customSearchList);
			
			/*if(flag == false){
				 rd = request.getRequestDispatcher("sanctionLeave.jsp?action=customSearch&error=Please Check Leave Balance");
			}else{
				rd = request.getRequestDispatcher("sanctionLeave.jsp?action=customSearch&error=Leave Sanctioned Successfully");
			}
			rd.forward(request, response);*/
		}
		
		else if(action.equalsIgnoreCase("cancelLeave")){
			int applno=Integer.parseInt(request.getParameter("applno"));
			try {
				boolean result= leaveHandler.setCancel(applno,0);
				if(result){
					//response.sendRedirect("sanctionLeave.jsp?flag=3");
					response.sendRedirect("sanctionpopup.jsp?flag=3");
				}
				else 
				{
					//response.sendRedirect("sanctionLeave.jsp?flag=4");
					response.sendRedirect("sanctionpopup.jsp?flag=4");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		else if(action.equalsIgnoreCase("checkLeaveStatus")){
			int appno=Integer.parseInt(request.getParameter("appno"));
			int empno=Integer.parseInt(request.getParameter("empno"));
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
				boolean result= leaveHandler.checkLeaveStatus(appno,empno);
				if(result){
					out.write("EXIST");
				}
				else 
				{
					out.write("NOTEXIST");
				}
			 
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		LMB leaveBean = new LMB();
		LMH leaveHandler=new LMH();
		
		HttpSession session = request.getSession();
		LookupHandler lookupHandler=new LookupHandler();
		ArrayList<LMB> customSearchList= new ArrayList<LMB>();
		
		RequestDispatcher rd=null;
		String action = request.getParameter("action")==null?"":request.getParameter("action");
	//	System.out.println("ACt  "+action);
		PrintWriter out = response.getWriter();
		
		//System.out.println("leave code is:"+leaveCode);
		
		
		/*  
	select COUNT(*) from LEAVETRAN where LEAVECD=2 and EMPNO=128
 	and FRMDT between '1-jan-2016' and '31-dec-2016'
 	and todt between '1-jan-2016' and '31-dec-2016'
		*/
		if(action.equalsIgnoreCase("addLeave")){

			String result="";
			int leaveCode=(request.getParameter("leavecode").equalsIgnoreCase("PL"))?1:
				  (request.getParameter("leavecode").equalsIgnoreCase("SL"))?2:
				  (request.getParameter("leavecode").equalsIgnoreCase("CL"))?3:
			      (request.getParameter("leavecode").equalsIgnoreCase("COFF_L"))?4:
			      (request.getParameter("leavecode").equalsIgnoreCase("LATE_L"))?6:
			      (request.getParameter("leavecode").equalsIgnoreCase("LWP"))?7:
			      (request.getParameter("leavecode").equalsIgnoreCase("MAT_L"))?8:
			     // (request.getParameter("leavecode").equalsIgnoreCase("TRF_L"))?9:
			    	  (request.getParameter("leavecode").equalsIgnoreCase("COVID19_L"))?9:
			    (request.getParameter("leavecode").equalsIgnoreCase("SUSPE_L"))?10:  0;
	 		String leavePurpose=request.getParameter("lreason");
	 		Float noDays =Float.parseFloat(request.getParameter("days"));
			int purposeCode=lookupHandler.getSRNO("LPURP-"+leavePurpose);
			//System.out.println("purposeCode"+purposeCode);
			//System.out.println("request.getParameter(reasonDetails):"+request.getParameter("reasonDetails"));
			leaveBean.setLREASON(request.getParameter("reasonDetails")==""?
					lookupHandler.getLKP_Desc("LPURP", purposeCode)
					:request.getParameter("reasonDetails"));
			//System.out.println("leaveBean.getLREASON() : "+leaveBean.getLREASON());
			leaveBean.setTRNDATE(request.getParameter("tdate")==null?"":request.getParameter("tdate"));
			leaveBean.setLEAVECD(leaveCode);
			leaveBean.setFRMDT(request.getParameter("frmdate")==null?"":request.getParameter("frmdate"));
			leaveBean.setTODT(request.getParameter("todate")==null?"":request.getParameter("todate"));
			leaveBean.setLEAVEPURP(purposeCode);
/*			leaveBean.setLADDR(request.getParameter("addDuringleave")==""?"---":request.getParameter("addDuringleave"));
*/			leaveBean.setLTELNO(request.getParameter("telephone")==""?0:Long.parseLong(request.getParameter("telephone")));
			leaveBean.setHALFDAY(request.getParameter("halfday")==null?"":request.getParameter("halfday"));
			leaveBean.setEMPNO(request.getParameter("empno")==null?0:Integer.parseInt(request.getParameter("empno")));
			leaveBean.setPrj_srno(request.getParameter("prj_srno")==null?"":request.getParameter("prj_srno"));
			leaveBean.setCreated_by((Integer)session.getAttribute("EMPNO"));
			leaveBean.setNODAYS(noDays);
			leaveBean.setCreated_date(ReportDAO.getServerDate());
			if(leaveCode!=0){
				int appno=00;
			result = leaveHandler.addLeave(leaveBean,appno);
			//System.out.println("result is : "+result );
			}
			
			if(result.equals("0")){
				rd = request.getRequestDispatcher("leave.jsp?flag=0");
				rd.forward(request, response);
			}
			else if (result.equals("1")){
				rd = request.getRequestDispatcher("leave.jsp?flag=1");
				rd.forward(request, response);
			}
			else if (result.equals("8")){
				rd = request.getRequestDispatcher("leave.jsp?flag=8");
				rd.forward(request, response);
			}
		/*	else if (result.equals("3")){
				rd = request.getRequestDispatcher("leave.jsp?flag=3");
				rd.forward(request, response);
			}*/
			else {
			//	System.out.println("hiiiiiiiiiiii"+result);
				response.sendRedirect("leave.jsp?flag="+result);
				//("leave.jsp?flag="+result+"");
				
			}
			
			
		
		}
		if(action.equalsIgnoreCase("editleave")){
			System.out.println("i am in editleave LMS ");
			String result ="";
			leaveBean.setTRNDATE(request.getParameter("tdate")==null?"":request.getParameter("tdate"));
			leaveBean.setLEAVECD(request.getParameter("leavecode")==null?0:Integer.parseInt(request.getParameter("leavecode")));
			leaveBean.setFRMDT(request.getParameter("frmdate")==null?"":request.getParameter("frmdate"));
			leaveBean.setTODT(request.getParameter("todate")==null?"":request.getParameter("todate"));
			leaveBean.setLREASON(request.getParameter("lreason")==null?"--":request.getParameter("lreason"));
/*			leaveBean.setLADDR(request.getParameter("addDuringleave")==null?"--":request.getParameter("addDuringleave"));
*/			leaveBean.setLTELNO(request.getParameter("telephone")==""?0:Long.parseLong(request.getParameter("telephone")));
			leaveBean.setHALFDAY(request.getParameter("halfday")==null?"":request.getParameter("halfday"));
			leaveBean.setEMPNO(request.getParameter("empno")==null?0:Integer.parseInt(request.getParameter("empno")));
			leaveBean.setAPPLNO(request.getParameter("appNo")==null?"":request.getParameter("appNo"));
			leaveBean.setPrj_srno(request.getParameter("prj_srno")==null?"":request.getParameter("prj_srno"));
			leaveBean.setCreated_by((Integer)session.getAttribute("EMPNO"));
			leaveBean.setCreated_date(ReportDAO.getServerDate());
//			result = leaveHandler.editLeave(leaveBean);
					int appno =Integer.parseInt(leaveBean.getAPPLNO());
					System.out.println("App no "+appno);
					
					try {
						boolean result1;
						System.out.println("01");
						result1 = leaveHandler.setCancel(appno,469);
						System.out.println("02: "+result1);
					
						if(result1)
						{
							System.out.println("03: "+result1);
							result = leaveHandler.editLeave(leaveBean,appno);
							System.out.println("04: "+result);
							if(result.equalsIgnoreCase("0")){
								 rd = request.getRequestDispatcher("leave.jsp?flag=0");
								 rd.forward(request, response);	
							}
							else if(result.equalsIgnoreCase("1")){
								System.out.println("this is 1");
								rd = request.getRequestDispatcher("leave.jsp?flag=1");
								rd.forward(request, response);}
							else if(result.equalsIgnoreCase("8")){
								System.out.println("this is 8");
								rd = request.getRequestDispatcher("leave.jsp?flag=8");
								rd.forward(request, response);}
							/*else{
								System.out.println("this is 9");
							rd = request.getRequestDispatcher("leave.jsp");
							}*/
						}
						else
						{
							response.sendRedirect("leave.jsp?flag=4");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		 
		}
		
		
	/****************************************************************************************/
	// For Sanction Edit Leave	by
		if(action.equalsIgnoreCase("editleave1")){
			int result = 0;
			String leave_code = request.getParameter("leavecode1");
			int leaveCode=Integer.parseInt(leave_code);
			
			/*int leaveCode=(request.getParameter("leavecode1").equalsIgnoreCase("PL"))?1:
				  (request.getParameter("leavecode1").equalsIgnoreCase("SL"))?2:
				  (request.getParameter("leavecode1").equalsIgnoreCase("CL"))?3:
			      (request.getParameter("leavecode1").equalsIgnoreCase("COFF_L"))?4:
			      (request.getParameter("leavecode1").equalsIgnoreCase("LATE_L"))?6:
			      (request.getParameter("leavecode1").equalsIgnoreCase("LWP"))?7:
			      (request.getParameter("leavecode1").equalsIgnoreCase("MAT_L"))?8:
			      (request.getParameter("leavecode1").equalsIgnoreCase("TRF_L"))?9:
			    (request.getParameter("leavecode1").equalsIgnoreCase("SUSPE_L"))?10:  0;*/
	 		String leavePurpose=request.getParameter("lreason");
			int purposeCode=lookupHandler.getSRNO("LPURP-"+leavePurpose);
			//System.out.println("purposeCode"+purposeCode);
			//System.out.println("request.getParameter(reasonDetails):"+request.getParameter("reasonDetails"));
			leaveBean.setFlag_editSanction(468);// 468 - for checking edit after sanction. 
			leaveBean.setLREASON(request.getParameter("reasonDetails")==""?
					lookupHandler.getLKP_Desc("LPURP", purposeCode)
					:request.getParameter("reasonDetails"));
			
			leaveBean.setTRNDATE(request.getParameter("tdate")==null?"":request.getParameter("tdate"));
			//leaveBean.setLEAVECD(request.getParameter("leavecode1")==null?0:Integer.parseInt(request.getParameter("leavecode1")));
			leaveBean.setLEAVECD(leaveCode);
			System.out.println("leaveCode  "+leaveCode);
			leaveBean.setFRMDT(request.getParameter("frmdate1")==null?"":request.getParameter("frmdate1"));
			System.out.println("frmdate  "+request.getParameter("frmdate1"));
			leaveBean.setTODT(request.getParameter("todate1")==null?"":request.getParameter("todate1"));
			System.out.println("todate "+request.getParameter("todate1"));
			leaveBean.setLREASON(request.getParameter("lreason")==null?"--":request.getParameter("lreason"));
	//		leaveBean.setLEAVEPURP(purposeCode);
			leaveBean.setLEAVEPURP(purposeCode);
/*			leaveBean.setLADDR(request.getParameter("addDuringleave")==null?"--":request.getParameter("addDuringleave"));
*/			leaveBean.setLTELNO(request.getParameter("telephone")==""?0:Long.parseLong(request.getParameter("telephone")));
			leaveBean.setHALFDAY(request.getParameter("halfday")==null?"":request.getParameter("halfday"));
			leaveBean.setEMPNO(request.getParameter("empno")==null?0:Integer.parseInt(request.getParameter("empno")));
			leaveBean.setAPPLNO(request.getParameter("appNo")==null?"":request.getParameter("appNo"));
			leaveBean.setPrj_srno(request.getParameter("prj_srno")==null?"":request.getParameter("prj_srno"));
			leaveBean.setCreated_by((Integer)session.getAttribute("EMPNO"));
			leaveBean.setCreated_date(ReportDAO.getServerDate());
			
			int appno =Integer.parseInt(leaveBean.getAPPLNO());
			
			try {
				boolean result1;
				System.out.println("00");
				result1 = leaveHandler.setCancel(appno,468);
				System.out.println("01: "+result1);
			if(result1)
			{
				System.out.println("01: "+result1);
				  String addEmpleave = leaveHandler.addLeave(leaveBean,appno);
				  System.out.println("02: "+addEmpleave);
				  if(addEmpleave.equalsIgnoreCase("1")){
						//response.sendRedirect("sanctionLeave.jsp?flag=1");
					  response.sendRedirect("sanctionpopup.jsp?flag=1");
						
					}else if(addEmpleave.equalsIgnoreCase("2")){
						//response.sendRedirect("sanctionLeave.jsp?flag=2");
						response.sendRedirect("sanctionpopup.jsp?flag=2");
						
					}
					else if(addEmpleave.equalsIgnoreCase("8")){
						//response.sendRedirect("sanctionLeave.jsp?flag=8");
						response.sendRedirect("sanctionpopup.jsp?flag=8");
						
					}
			}
			else
			{
				//response.sendRedirect("sanctionLeave.jsp?flag=4");
				response.sendRedirect("sanctionpopup.jsp?flag=4");
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		/*//	result = leaveHandler.editSanctionLeave(leaveBean);
			System.out.println("App num in LMS:- "+request.getParameter("appNo"));
			if(result == 0){
				 rd = request.getRequestDispatcher("sanctionLeave.jsp?error=Please Check Your Leave Balance");
			}
			else{
			rd = request.getRequestDispatcher("sanctionLeave.jsp");
			}
			rd.forward(request, response);*/
			
		}
		
		
		
		
		
		
		if(action.equalsIgnoreCase("ExtraLeave")){
			
			ArrayList<LMB> leaveBalList = new ArrayList<LMB>();
			boolean result = false;
		    int EMPNO =Integer.parseInt(session.getAttribute("empno").toString());
		    //System.out.println(EMPNO);
		    leaveBean.setEMPNO(EMPNO);
			leaveBean.setTRNDATE(request.getParameter("tdate")==null?"":request.getParameter("tdate"));
			leaveBean.setLEAVECD(request.getParameter("leavecode")==null?0:Integer.parseInt(request.getParameter("leavecode")));
			leaveBean.setFRMDT(request.getParameter("frmdate")==null?"":request.getParameter("frmdate"));
			leaveBean.setTODT(request.getParameter("todate")==null?"":request.getParameter("todate"));
			leaveBean.setLREASON(request.getParameter("lreason")==""?"---":request.getParameter("lreason"));
			leaveBean.setTRNTYPE(request.getParameter("leave").charAt(0));
			leaveBean.setHALFDAY(request.getParameter("halfday")==null?"":request.getParameter("halfday"));
			
			result = leaveHandler.addExtraLeave(leaveBean);
			leaveBalList = leaveHandler.getList(EMPNO);
			request.setAttribute("leaveBalList", leaveBalList);
			if(result){
				request.getRequestDispatcher("ExtraLeaves.jsp?action=getData").forward(request, response);
			}
		}
		if(action.equalsIgnoreCase("getLeaveApp")){
			int empno = request.getParameter("empno")==null?0:Integer.parseInt(request.getParameter("empno"));
			int appNo = request.getParameter("appNo")==null?0:Integer.parseInt(request.getParameter("appNo"));
			ArrayList<LMB> leaveApp = new ArrayList<LMB>();
			leaveApp = leaveHandler.getLeaveApp(empno, appNo);
			//System.out.println("lapp="+leaveApp.toString());
			request.setAttribute("leaveApp", leaveApp);
			rd = request.getRequestDispatcher("leave.jsp?action=editLeaveApp");
		    rd.forward(request, response);
		}
		
		
		if(action.equalsIgnoreCase("cancelLeaveApp")){
			
			int empno = request.getParameter("empno")==null?0:Integer.parseInt(request.getParameter("empno"));
			int appNo = request.getParameter("appNo")==null?0:Integer.parseInt(request.getParameter("appNo"));
			
			leaveHandler.cancelLeaveApp(empno, appNo);
			out.write("true");
			leaveBean = (LMB) session.getAttribute("leaveSearchFilter");
			customSearchList = leaveHandler.getLeaveAppList(leaveBean, "custom");
			request.setAttribute("customSearchList", customSearchList);
			
			/*rd = request.getRequestDispatcher("sanctionLeave.jsp?action=customSearch&error=Leave Canceled");
		    rd.forward(request, response);*/
		}
		if(action.equalsIgnoreCase("customSearch")){
			
			String type=request.getParameter("type")==null?"":request.getParameter("type");
			String fromemp=request.getParameter("EMPNO")==null?"":request.getParameter("EMPNO");//display on next page
			String toemp=request.getParameter("EMPNO1")==null?"":request.getParameter("EMPNO1");//display on next page
			String[] employ = fromemp.split(":");
		    int frmEmpNo = Integer.parseInt(employ[2].trim());
		    String empCode= employ[1].trim();
		    employ = toemp.split(":");
		//    int toEmpNo = Integer.parseInt(employ[2].trim());
			String fromdate=request.getParameter("frmdate")==null?"":request.getParameter("frmdate");
			String todate=request.getParameter("todate")==null?"":request.getParameter("todate");
			leaveBean.setEMPNO(frmEmpNo);
		//	leaveBean.setEMPNO2(toEmpNo);
			leaveBean.setFRMDT(fromdate);
			leaveBean.setTODT(todate);
			leaveBean.setSTATUS(type);
			leaveBean.setEmpCode(empCode);
			session.setAttribute("leaveSearchFilter", leaveBean);
			customSearchList = leaveHandler.getLeaveAppList(leaveBean, "custom");
			request.setAttribute("customSearchList", customSearchList);
			//rd = request.getRequestDispatcher("sanctionLeave.jsp?action=customSearch");
			rd = request.getRequestDispatcher("sanctionpopup.jsp?action=customSearch");
		    rd.forward(request, response);
		}
		if(action.equalsIgnoreCase("getLeave")){
			
			int EMPNO;
			ArrayList<LMB> leaveBalList = new ArrayList<LMB>();
			String list=request.getParameter("list")==null?"":request.getParameter("list");
			//System.out.println(list);
			if(list.equalsIgnoreCase(""))
			{
				String[] employ = request.getParameter("EMPNO").split(":");
			    EMPNO = Integer.parseInt(employ[2].trim());
			}	
			else
			{
				EMPNO= Integer.parseInt(request.getParameter("EMPNO"));
			}
			session.setAttribute("empno", EMPNO);
			leaveBalList = leaveHandler.getList(EMPNO);
			request.setAttribute("leaveBalList", leaveBalList);
			
			rd = request.getRequestDispatcher("ExtraLeaves.jsp?action=getData");
		    rd.forward(request, response);
		}
		if(action.equalsIgnoreCase("DeleteLeave")){
			ArrayList<LMB> leaveBalList = new ArrayList<LMB>();
			int empno = Integer.parseInt(request.getParameter("empno"));
			int leavecd = Integer.parseInt(request.getParameter("leavecd"));
			Connection con = ConnectionManager.getConnection();
			try{
				Statement st = con.createStatement();
				st.execute("DELETE FROM LEAVEBAL WHERE EMPNO="+empno+"AND LEAVECD="+leavecd);
				st.execute("DELETE FROM LEAVETRAN WHERE EMPNO="+empno+"AND LEAVECD="+leavecd);
				
				st.close();
				con.close();
				
				leaveBalList = leaveHandler.getList(empno);
				request.setAttribute("leaveBalList", leaveBalList);
				rd = request.getRequestDispatcher("ExtraLeaves.jsp?action=getData");
			    rd.forward(request, response);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}	
		
	// For Maternity Leave.	
		if(action.equalsIgnoreCase("matLeave"))
		{
		System.out.println("In matLeave of LMS");
			int empno = Integer.parseInt(request.getParameter("empno"));
			Integer flag = null;
			//int flag = 0;
			 LMH lmh=new LMH();
			   try
			   {
				   flag=lmh.getMatLeave(empno);
				   
				   
				    PrintWriter out1 = response.getWriter();
					response.setContentType("text/html");
					out1.write(flag.toString());
					 System.out.println("falg12  "+flag);
		    } 
			   	catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   
		}
		
		
	// For Consecutive Leave.	
		if(action.equalsIgnoreCase("consecutive_CL"))
		{
			System.out.println("In consecutive_CL LMH");
			  String FrmDate = "";
			  String ToDate = "";
			System.out.println("In consecutive_CL LMS");
			String Frdt =request.getParameter("frmdate");
			String Todt =request.getParameter("todate");
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		    SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
		 
		    try {
				 FrmDate = output.format(sdf.parse(Frdt));
			     ToDate = output.format(sdf.parse(Todt));
			} catch (java.text.ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		   
			System.out.println("In LMS 01:");
			int empno = Integer.parseInt(request.getParameter("empno"));
			Integer flags = null;
			//int flag = 0;
			 LMH lmh1=new LMH();
			   try
			   {
				   flags=lmh1.getConsecutive_CL_Leave(empno,FrmDate,ToDate);
				   PrintWriter out1 = response.getWriter();
					response.setContentType("text/html");
					out1.write(flags.toString());
					 System.out.println("cosecutive leave flg:-  "+flags);
		    } 
			   	catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   }
		
	
				 if(action.equalsIgnoreCase("chkLeaveYear"))
							   {
								   System.out.println("In chkLeaveYear LMS");
								   
								   String FrmDate = "";
									  String ToDate = "";
									System.out.println("In chkLeaveYear LMS");
									String Frdt =request.getParameter("frmdate");
									String Todt =request.getParameter("todate");
									
									SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
								    SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
								 
								    try {
										 FrmDate = output.format(sdf.parse(Frdt));
									     ToDate = output.format(sdf.parse(Todt));
									} catch (java.text.ParseException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								    
								    Integer flags = null;
									//int flag = 0;
									 LMH lmh2=new LMH();
									   try
									   {
										   flags=lmh2.checkLeaveYear(FrmDate,ToDate);
										   PrintWriter out1 = response.getWriter();
											response.setContentType("text/html");
											out1.write(flags.toString());
											 System.out.println("chkLeaveYear flg:-  "+flags);
								    } 
									   	catch (java.text.ParseException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
							   }
				 
				 if(action.equalsIgnoreCase("openyear"))
					{
					System.out.println("In openyear of LMS");
						String flag = "";
						 LMH lmh=new LMH();
						   try
						   {
							   
							 String Date =  ReportDAO.BOYForJanToDec1( ReportDAO.getSysDate());
							 System.out.println(Date);
							   flag=lmh.getOpenYear(Date);
							   
						   /* PrintWriter out1 = response.getWriter();
								response.setContentType("text/html");
								out1.write(flag.toString());*/
					    } 
						   	catch (java.text.ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						   if(flag.equalsIgnoreCase("OPEN"))
						   {
						   response.sendRedirect("Leave_Auto.jsp");
						   }
						   else
						   {
							   response.sendRedirect("leaveTypeDetails.jsp?flag=6");
						   }
						   }
				 if(action.equalsIgnoreCase("endyear"))
					{
					System.out.println("In endyear of LMS");
						String flag = "";
						 LMH lmh=new LMH();
						   try
						   {
							   flag=lmh.getEndYear();
					    } 
						   	catch (java.text.ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						 
							   response.sendRedirect("leaveTypeDetails.jsp?flag=7");
						   
						   }
				 
				 if(action.equalsIgnoreCase("betDate"))
					{
						System.out.println("In betDate LMS");
						  String FrmDate = "";
						  String ToDate = "";
						System.out.println("In betDate LMS");
						String Frdt =request.getParameter("frmdate");
						String Todt =request.getParameter("todate");
						
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
					    SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
					 
					    try {
							 FrmDate = output.format(sdf.parse(Frdt));
						     ToDate = output.format(sdf.parse(Todt));
						} catch (java.text.ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					   
						System.out.println("In LMS 01:");
						int empno = Integer.parseInt(request.getParameter("empno"));
						int appn = Integer.parseInt(request.getParameter("appNo"));
						System.out.println("In LMS 001:"+appn);
						Integer flags = null;
						//int flag = 0;
						 LMH lmh4=new LMH();
						   try
						   {
							   flags=lmh4.chk_Leave_betDate(empno,FrmDate,ToDate,appn);
							   PrintWriter out1 = response.getWriter();
								response.setContentType("text/html");
								out1.write(flags.toString());
								 System.out.println("betDate leave flg:-  "+flags);
					    } 
						   	catch (java.text.ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						   }
				 
				 if(action.equalsIgnoreCase("chkappdt"))
					{
						int empno = Integer.parseInt(request.getParameter("empno"));
						String Trndt =request.getParameter("tdate");
						String TrnDate = "";
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
					    SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
					 
					    try {
					    	TrnDate = output.format(sdf.parse(Trndt));
						} catch (java.text.ParseException e1) {
							e1.printStackTrace();
						}
						Integer flag = null;
						 LMH lmh=new LMH();
						   try
						   {
							   flag=lmh.chkApplDate(empno,Trndt);
							   PrintWriter out1 = response.getWriter();
							   response.setContentType("text/html");
							   out1.write(flag.toString());
							   System.out.println("falg12  "+flag);
					    } 
						   	catch (java.text.ParseException e) {
								e.printStackTrace();
							}
					}
				 
		
		}
}
