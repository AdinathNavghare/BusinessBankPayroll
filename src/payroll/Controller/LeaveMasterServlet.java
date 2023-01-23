package payroll.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.Core.Utility;
import payroll.DAO.LeaveMasterHandler;
import payroll.Model.LeaveMassBean;
import payroll.Model.LeaveMasterBean;

/**
 * Servlet implementation class LeaveMasterServlet
 */
@WebServlet({ "/LeaveMasterServlet", "/leave" })
public class LeaveMasterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LeaveMasterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		LeaveMasterHandler obj_SubL1=new LeaveMasterHandler();
		String action = request.getParameter("action");
		HttpSession session = request.getSession(true);
		if (action.equalsIgnoreCase("display1"))
		{
			ArrayList<LeaveMasterBean> llist = new ArrayList<LeaveMasterBean>();
			int empno1 = (Integer) session.getAttribute("sempno");
			llist = obj_SubL1.leaveDisplay(empno1);
			session.setAttribute("leaveList", llist);
			RequestDispatcher rd = request.getRequestDispatcher("leaveDetails.jsp");
			rd.forward(request, response);
		}
		else if(action.equalsIgnoreCase("sanctionleave"))
		{
			ArrayList<LeaveMasterBean> sanctionList= new ArrayList<>();
			sanctionList=obj_SubL1.getSanctionList();
			session.setAttribute("sancleavelist", sanctionList);
			RequestDispatcher rd = request.getRequestDispatcher("sanction.jsp?action=sanclist");
			rd.forward(request, response);
		}
		else if(action.equalsIgnoreCase("sanction"))
		{
			ArrayList<LeaveMasterBean> sanctionList2= new ArrayList<>();
			boolean flag=false;
			String fromdate=request.getParameter("fromdate");
			String  todate=request.getParameter("todate");
			String fromemp=request.getParameter("fromemp");
			String toemp=request.getParameter("toemp");
			String team[] = request.getParameterValues("chkAppNo");
			for(int i=0;i<team.length;i++)
			{
				try
				{
					flag=obj_SubL1.setSanction(team[i]);
				}
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
			LeaveMasterBean sancBean2= new LeaveMasterBean();
			if(flag)
			{
				sancBean2= (LeaveMasterBean)session.getAttribute("beanobject");
				sanctionList2=obj_SubL1.getSearch(sancBean2);//this list is for showing for no of leave application
				session.setAttribute("sanctionsearch", sanctionList2);
				request.setAttribute("fromdate", fromdate);
				request.setAttribute("todate", todate);
				request.setAttribute("fromemp", fromemp);
				request.setAttribute("toemp", toemp);
				RequestDispatcher rd = request.getRequestDispatcher("sanction.jsp?action=searchpending");
				rd.forward(request, response);
			}
			else
			{
			}
		}	
		
		else if(action.equalsIgnoreCase("statusChange"))
		{
			ArrayList<LeaveMasterBean> listc = new ArrayList<LeaveMasterBean>();
			PrintWriter out =response.getWriter();
			String user = request.getParameter("user")==null?"": request.getParameter("user");
			LeaveMasterBean cancelbean = new LeaveMasterBean();
			String[] str1= request.getParameter("cancelString").split(":");
			int  empno1=Integer.parseInt(str1[0]);
			int leavecode=Integer.parseInt(str1[1]==null?"0":str1[1]);
			String applno=str1[2];
			String tdate=str1[3];
			String frmdate=str1[4];
			String todate=str1[5];
			int  status=98; 
			cancelbean.setEMPNO(empno1);
			cancelbean.setLEAVECD(leavecode);
			cancelbean.setTODT(tdate);
			cancelbean.setFRMDT(frmdate);
			cancelbean.setTODT(todate);
			cancelbean.setAPPLNO(applno);
			cancelbean.setSTATUS(status); 
			boolean flag;
			flag = obj_SubL1.upDateStatus(cancelbean);
			listc=obj_SubL1.getlast(empno1);
			session.setAttribute("listcancel",listc);
			if(flag)
			{
				if(user.equalsIgnoreCase("employee"))
				{
					response.sendRedirect("empLeaveCancel.jsp");
				}
				else
				{
					response.sendRedirect("leaveCancel.jsp?action=listleave");
				}
			}
			else
			{
				out.println("<html><body> <p>error in record inserting</p> </body></html>");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		LeaveMasterHandler obj_SubL=new LeaveMasterHandler();
		String action = request.getParameter("action");
		if (action.equalsIgnoreCase("addleave"))
		{
			String transDate = request.getParameter("tdate");
			String type = request.getParameter("type");
			String user = request.getParameter("user")== null?"":request.getParameter("user");
			int empno = Integer.parseInt(request.getParameter("empno"));
			int leavecode = Integer.parseInt(request.getParameter("leavecode")==null?"0":request.getParameter("leavecode"));
			String fromdate = request.getParameter("frmdate");
			String todate = request.getParameter("todate");
			int branchcode = Integer.parseInt(request.getParameter("branchcode"));
			int leavepurpose = Integer.parseInt(request.getParameter("leavepurpose"));
			String leavereason = request.getParameter("lreason");
			String sacnctionby = request.getParameter("sanctionby")==null?"":request.getParameter("sanctionby");
			String subsitute = request.getParameter("subsitute")== null?"":request.getParameter("subsitute");
			String Appno = request.getParameter("appno");
			String appdate = request.getParameter("appdate");
			String addduringleave = request.getParameter("addDuringleave");
			float nodays = Float.parseFloat(request.getParameter("nodays")==null?"0.0":request.getParameter("nodays"));
			long telephoneno = Long.parseLong(request.getParameter("telephone"));
			String ltype = request.getParameter("ltype");
			int status = 1;
			String Opr_code = "OP101";
			String Off_code = "OF101";
			boolean test;
			LeaveMasterBean lbean = new LeaveMasterBean();
			lbean.setTRNDATE(transDate);
			lbean.setTRNTYPE(type==null?"D":type);
			lbean.setEMPNO(empno);
			lbean.setLEAVECD(leavecode);
			lbean.setLTYPE(ltype);
			lbean.setFRMDT(fromdate);
			lbean.setTODT(todate);
			lbean.setBRCODE(branchcode);
			lbean.setLEAVEPURP(leavepurpose);
			lbean.setLREASON(leavereason);
			lbean.setSANCAUTH(sacnctionby==null?"":sacnctionby);
			lbean.setSUBSTITUTE(subsitute==null?"N":subsitute);
			lbean.setAPPLNO(Appno);
			lbean.setAPPLDT(appdate);
			lbean.setSTATUS(status);
			lbean.setLADDR(addduringleave);
			lbean.setLTELNO(telephoneno);
			lbean.setLTYPE(ltype);
			lbean.setOFF_CD(Off_code);
			lbean.setOPR_CD(Opr_code);
			lbean.setNODAYS(nodays);
			
			test =obj_SubL.addLeave(lbean);
			ArrayList<LeaveMasterBean> llist = new ArrayList<LeaveMasterBean>();
			HttpSession session = request.getSession(true);
			llist = obj_SubL.leaveDisplay(empno);
			session.setAttribute("leaveList", llist);
			if (test) 
			{
				if(subsitute.equalsIgnoreCase("Y"))
				{
					session.setAttribute("subbean", lbean);
					response.sendRedirect("substitute.jsp?action=true");
				}
				else
				{
					if(user.equalsIgnoreCase("employee"))
					{
						response.sendRedirect("empLeaveApply.jsp?action=true");
					}
					else
					{
						response.sendRedirect("leaveDetails.jsp?action=true");
					}
				}
			}
			else
			{
				RequestDispatcher rd = request.getRequestDispatcher("leaveDetails.jsp?action= false");
				rd.forward(request, response);
			}
		}
		
		else if(action.equalsIgnoreCase("cancel"))
		{
			ArrayList<LeaveMasterBean> listc = new ArrayList<LeaveMasterBean>();
			String EMPNO=request.getParameter("EMPNO");
			String sempno=request.getParameter("EMPNO");
			String[] employ = request.getParameter("EMPNO").split(":");
		    int empno = Integer.parseInt(employ[1].trim());
		    
		    listc=obj_SubL.getlast(empno);
			HttpSession session = request.getSession();
			session.setAttribute("empno", sempno);
			session.setAttribute("listcancel",listc );
			response.sendRedirect("leaveCancel.jsp?action=listleave");
		}
		
		else if (action.equalsIgnoreCase("leave1")) 
		{
			ArrayList<LeaveMasterBean> lbal = new ArrayList<LeaveMasterBean>();
			String empName="";
			String EMPNO2 =request.getParameter("EMPNO");
			//String EMPNO =request.getParameter("EMPNO");
			String[] employ = request.getParameter("EMPNO").split(":");
		    int empno = Integer.parseInt(employ[1].trim());
		    try 
		    {
				empName=obj_SubL.getempName(empno);
				HttpSession session = request.getSession();
				session.setAttribute("sempno", empno);
				session.setAttribute("ename", empName);
				lbal = obj_SubL.getList(empno);
				session.setAttribute("leavevbal", lbal);
				request.setAttribute("empno", EMPNO2);//for display purpose
				RequestDispatcher rd = request.getRequestDispatcher("showLeaveDetails.jsp?action=BalLeave");
		        rd.forward(request, response); 
		    } 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		
		else if(action.equalsIgnoreCase("search"))
		{
			ArrayList<LeaveMasterBean> slist=new ArrayList<LeaveMasterBean>();
			LeaveMasterBean searchbean = new LeaveMasterBean();
			String user = request.getParameter("user")==null?"":request.getParameter("user");
			String EMPNO="";
			String empno1=request.getParameter("EMPNO");
			if(user.equalsIgnoreCase("employee"))
			{
				 EMPNO=request.getParameter("EMPNO");
			}
			else
			{
				String[] employ = request.getParameter("EMPNO").split(":");
				EMPNO = employ[1].trim();
			}
			int empnosearch =Integer.parseInt(EMPNO);
			String frmdate =request.getParameter("frmdate");
			String todate =request.getParameter("todate");
			int lcode1=Integer.parseInt(request.getParameter("lcode")==null?"0":request.getParameter("lcode"));
			searchbean.setEMPNO(empnosearch);
			searchbean.setFRMDT(frmdate);
			searchbean.setTODT(todate);
			request.setAttribute("frmdate", frmdate);
			request.setAttribute("todate", todate);
			request.setAttribute("empno", empno1);
			searchbean.setLEAVECD(lcode1);
			request.setAttribute("lcode1", lcode1);
			slist = obj_SubL.searchLeave(searchbean);
			HttpSession session = request.getSession();
			session.setAttribute("searchlist", slist);
			if(user.equalsIgnoreCase("employee"))
			{
				RequestDispatcher rd = request.getRequestDispatcher("empLeaveHistory.jsp?action=transaction");
				rd.forward(request, response); 
			}
			else
			{
			  RequestDispatcher rd = request.getRequestDispatcher("searchTransaction.jsp?action=transaction");
			  rd.forward(request, response);  
			}
		}
		
		else if(action.equalsIgnoreCase("addsubstitue"))
		{
			boolean flag1;
			ArrayList<LeaveMasterBean> subsList= new ArrayList<LeaveMasterBean>();
			LeaveMasterBean subBean=new LeaveMasterBean();
			String EMPNO= request.getParameter("EMPNO");
			subBean.setEMPNO(Integer.parseInt(EMPNO));
			subBean.setAPPLNO(request.getParameter("applno"));
			int srno=1;
			subBean.setSRNO(srno);
			String EMPNO2="";
			String[] employ = request.getParameter("EMPNO").split(":");
			EMPNO2 = employ[1].trim();
			subBean.setSUBSTCD(Integer.parseInt(EMPNO2));
			subBean.setFRMDT(request.getParameter("fromdate"));
			subBean.setTODT(request.getParameter("todate"));
			int status=1;
			subBean.setSTATUS(status);
			flag1 =obj_SubL.addSubstitute(subBean);
			if(flag1)
			{
				subsList=obj_SubL.getsubsList(subBean);
				HttpSession session =request.getSession();
				session.setAttribute("substituteList",subsList);
				response.sendRedirect("substitute.jsp?action=showSubList");
			}
			else
			{
				PrintWriter out = response.getWriter();
				RequestDispatcher rd = request.getRequestDispatcher("substitute.jsp");
				out.println("<font color=red>Error in record inserting</font>");
				rd.forward(request, response);
			}
		}
		else if(action.equalsIgnoreCase("deleteSubst"))
		{
			ArrayList<LeaveMasterBean> subsList= new ArrayList<LeaveMasterBean>();
			LeaveMasterBean dlbean=new LeaveMasterBean();
			boolean flag=false;
			dlbean.setEMPNO( Integer.parseInt(request.getParameter("EMPNO")));
			dlbean.setAPPLNO(request.getParameter("applno"));
			dlbean.setSRNO(Integer.parseInt(request.getParameter("srno")));
			dlbean.setSUBSTCD(Integer.parseInt(request.getParameter("EMPNO1")));
			dlbean.setFRMDT(request.getParameter("frmdate"));
			dlbean.setTODT(request.getParameter("todate"));
			dlbean.setSTATUS(Integer.parseInt(request.getParameter("status")));
			flag=obj_SubL.deleteSubstitute(dlbean);
			if(flag)
			{
				subsList=obj_SubL.getsubsList(dlbean);
				HttpSession session =request.getSession();
				session.setAttribute("substituteList",subsList);
				response.sendRedirect("substitute.jsp?action=showSubList");
			}
		}
		else if(action.equalsIgnoreCase("modifySubst"))
		{
			ArrayList<LeaveMasterBean> subsList= new ArrayList<LeaveMasterBean>();
			boolean flag6=false;
			LeaveMasterBean editbean= new LeaveMasterBean();
			int EMPNO= Integer.parseInt( request.getParameter("EMPNO"));
			String applno=request.getParameter("applno");
			int srno=Integer.parseInt(request.getParameter("srno"));
			String fromdate= request.getParameter("frmdate");
			String todate=request.getParameter("todate");
			int sucstitutecd= Integer.parseInt(request.getParameter("EMPNO1"));
			editbean.setEMPNO(EMPNO);
			editbean.setAPPLNO(applno);
			editbean.setSRNO(srno);
			editbean.setSUBSTCD(sucstitutecd);
			editbean.setFRMDT(fromdate);
			editbean.setTODT(todate);
			flag6=obj_SubL.editSubstitute(editbean);
			if(flag6)
			{  
				subsList=obj_SubL.getsubsList(editbean);
				HttpSession session =request.getSession();
				session.setAttribute("substituteList",subsList);
				response.sendRedirect("substitute.jsp?action=showSubList");
			}
			else
			{
				PrintWriter out = response.getWriter();
				RequestDispatcher rd = request.getRequestDispatcher("substitute.jsp");
		        out.println("<font color=red>Error in record inserting</font>");
				rd.forward(request, response);
			}
		}
		else if(action.equalsIgnoreCase("searchappln"))
		{
			ArrayList<LeaveMasterBean> listdisplay= new ArrayList<LeaveMasterBean>();
			LeaveMasterBean sancBean1=new LeaveMasterBean();
			String type=request.getParameter("type");
			String fromemp=request.getParameter("EMPNO");//display on next page
			String toemp=request.getParameter("EMPNO1");//display on next page
			String EMPNO =request.getParameter("EMPNO");
			String[] employ = request.getParameter("EMPNO").split(":");
		    int empno = Integer.parseInt(employ[1].trim());
		    String EMPNO2 =request.getParameter("EMPNO1");
		    employ = request.getParameter("EMPNO1").split(":");
		    int EMPNO3 = Integer.parseInt(employ[1].trim());
			String fromdate=request.getParameter("frmdate");
			String todate=request.getParameter("todate");
			sancBean1.setSerachtype(type);
			sancBean1.setEMPNO(empno);
			sancBean1.setEMPNO2(EMPNO3);
			sancBean1.setFRMDT(fromdate);
			sancBean1.setTODT(todate);
		    listdisplay=obj_SubL.getSearch(sancBean1); 
			HttpSession session=request.getSession();
			request.setAttribute("fromdate",fromdate);
			request.setAttribute("todate", todate);
			request.setAttribute("fromemp",fromemp);
			request.setAttribute("toemp", toemp);
			session.setAttribute("beanobject", sancBean1);
			session.setAttribute("sanctionsearch", listdisplay);
			request.setAttribute("bean", sancBean1); // i have set this bean object in session for next search transaction on same criteria 
			session.setAttribute("beansanction", sancBean1); 
			if(type.equals("All"))
			{
				RequestDispatcher rd=request.getRequestDispatcher("sanction.jsp?action=searchAll");
				rd.forward(request, response);
				
			}
			else if(type.equals("pending"))
			{
				RequestDispatcher rd=request.getRequestDispatcher("sanction.jsp?action=searchpending");
				rd.forward(request, response);
			}
			else
			{
				request.getRequestDispatcher("sanction.jsp?action=searchsanction").forward(request, response);
			}
		}
		else if(action.equalsIgnoreCase("InsertLeavetype"))
		{

			LeaveMassBean lmbean = new LeaveMassBean();
			LeaveMasterHandler lmh = new LeaveMasterHandler();
			boolean flag=false;
			lmbean.setLEAVECD(Integer.parseInt(request.getParameter("leavecode")==null?"0":request.getParameter("leavecode")));
			lmbean.setLEAVEDES(request.getParameter("ldescrptn"));
			lmbean.setFBEGINDATE(request.getParameter("lbgndate"));
			lmbean.setEFFDATE(request.getParameter("Efftdate"));
			lmbean.setFENDDATE(request.getParameter("lenddate"));
			lmbean.setFREQUENCY(request.getParameter("frequency"));
			lmbean.setCONS_HOLIDAYS(request.getParameter("holiday"));
			lmbean.setCRLIM(Integer.parseInt(request.getParameter("crlimit")==null?"0":request.getParameter("crlimit")));
			lmbean.setMINLIM(Integer.parseInt(request.getParameter("minlimit")==null?"0":request.getParameter("minlimit")));
			lmbean.setMAXCUMLIM(Integer.parseInt(request.getParameter("maxcultv")==null?"0":request.getParameter("maxcultv")));
			lmbean.setMAXCF(Integer.parseInt(request.getParameter("maxcf")==null?"0":request.getParameter("maxcf")));
			lmbean.setLEAVEINCASH(request.getParameter("leaveincash"));
			lmbean.setWEEKOFF(request.getParameter("weekoff"));
			
			flag=lmh.addleavecdDetail(lmbean);
			System.out.println(flag);
			if(flag)
			{
				response.sendRedirect("leaveTypeDetails.jsp?flag=1");	
			}
			else
			{
				response.sendRedirect("leaveTypeDetails.jsp?flag=2");	
			}
			
		
			
			}
		else if(action.equalsIgnoreCase("EditLeavetype"))
		{
			LeaveMassBean lmbean= new LeaveMassBean();
			boolean flag=false;
			lmbean.setLEAVECD(Integer.parseInt(request.getParameter("leavecode1")==null?"0":request.getParameter("leavecode1")));
			lmbean.setLEAVEDES(request.getParameter("ldescrptn1"));
			lmbean.setFBEGINDATE(request.getParameter("lbgndate1"));
			lmbean.setEFFDATE(request.getParameter("Efftdate1"));
			lmbean.setFENDDATE(request.getParameter("lenddate1"));
			lmbean.setFREQUENCY(request.getParameter("frequency1"));
			lmbean.setCONS_HOLIDAYS(request.getParameter("holiday1"));
			lmbean.setCRLIM(Integer.parseInt(request.getParameter("crlimit1")==null?"0":request.getParameter("crlimit1")));
			lmbean.setMINLIM(Integer.parseInt(request.getParameter("minlimit1")==null?"0":request.getParameter("minlimit1")));
			lmbean.setMAXCUMLIM(Integer.parseInt(request.getParameter("maxcultv1")==null?"0":request.getParameter("maxcultv1")));
			lmbean.setMAXCF(Integer.parseInt(request.getParameter("maxcf1")==null?"0":request.getParameter("maxcf1")));
			lmbean.setLEAVEINCASH(request.getParameter("leaveincash1"));
			lmbean.setSRNO(Integer.parseInt(request.getParameter("srno")==null?"0":request.getParameter("srno")));
			lmbean.setWEEKOFF(request.getParameter("weekoff1"));
			flag=obj_SubL.editLeavetype(lmbean);
			if(flag)
			{
				response.sendRedirect("leaveTypeDetails.jsp?flag=3");	
			}
			else
			{
				response.sendRedirect("leaveTypeDetails.jsp?flag=4");	
			}
		}
		else if(action.equalsIgnoreCase("checkentry"))
		{
			boolean flag=false;
			String[] key=request.getParameter("key").split(":");
			int lcode= Integer.parseInt(key[0]==null?"0":key[0]);
			try
			{
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			    SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
			 
			    String bgdate = output.format(sdf.parse(key[1]));
			    String enddate = output.format(sdf.parse(key[2]));
			    flag=obj_SubL.dataCheckExist(lcode,bgdate,enddate);
				PrintWriter out=response.getWriter();
				response.setContentType("text/html");
				if(flag)
				{
					out.write("true");
				}
				else
				{
					out.write("false");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else if(action.equalsIgnoreCase("autoCredit"))
		{
			String emplist = request.getParameter("emplist");
			int lSrno = Integer.parseInt(request.getParameter("type"));
			String crdMonth = request.getParameter("crdmonth");
			System.out.println("autoCredit Servlet "+lSrno+ "   "+crdMonth);
			Utility util = new Utility();
			util.autoLeaveCredit(emplist,lSrno,crdMonth);
			String act = "Leaves Credited Successfully";
			request.setAttribute("action", act);
			request.getRequestDispatcher("Leave_Auto.jsp").forward(request, response);
		}
		else if(action.equalsIgnoreCase("autoDebit"))
		{
			String emplist = request.getParameter("emplist");
			int lSrno = Integer.parseInt(request.getParameter("type"));
			Utility util = new Utility();
			util.autoLeaveDebit(emplist,lSrno);
			String act = "Leaves Ended Successfully";
			request.setAttribute("action", act);
			request.getRequestDispatcher("Leave_End_Year.jsp").forward(request, response);
		}
		else if(action.equalsIgnoreCase("retireExtLeave"))
		{
			String empNo = request.getParameter("empno");
			String leaveDay = request.getParameter("noofLeave");
			String retireDate = request.getParameter("retirementDate");
			String leaveCredited = request.getParameter("credited");
		      HttpSession session = request.getSession();
		      int crdby = (int) session.getAttribute("EMPNO");
			System.out.println("crdby:  "+crdby);
			Utility util = new Utility();
			String flags = "";
			try {
				flags =util.retirement_extLeaveCredit(empNo,leaveDay,retireDate,crdby,leaveCredited);
				PrintWriter out1 = response.getWriter();
				response.setContentType("text/html");
				out1.write(flags.toString());
				 System.out.println("retirement leave flg:-  "+flags);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
}