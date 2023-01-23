package payroll.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import payroll.DAO.ConnectionManager;
import payroll.DAO.ShiftHandler;
import payroll.Model.ShiftBean;
import payroll.Model.extra_duty_paymentBean;

/**
 * Servlet implementation class ShiftServlet
 */
@WebServlet("/ShiftServlet")
public class ShiftServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShiftServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action=request.getParameter("action")!=null?request.getParameter("action"):"";
		ShiftHandler shifthand1=new ShiftHandler();
		HttpSession session = request.getSession();
		
		if(action.equalsIgnoreCase("showtimesheet"))
		{
			ArrayList<ShiftBean> shftlist=new ArrayList<ShiftBean>();
			shftlist=shifthand1.getTimesheet();
			session.setAttribute("timesheet", shftlist);
			response.sendRedirect("timeSheet.jsp?action=showsheetlist");
		
		}
		if(action.equalsIgnoreCase("show"))
		{
			ArrayList<ShiftBean>ovtlist=new ArrayList<>(); 
			ovtlist=shifthand1.getList();
			session.setAttribute("ovtlist1", ovtlist);
		    response.sendRedirect("overtime.jsp?action=ovetimelist");
		}
		if(action.equalsIgnoreCase("showcalmast"))
		{
			ArrayList<ShiftBean> callist=new ArrayList<>(); 
			callist=shifthand1.getCalmastList();
			session.setAttribute("calmastlist", callist);
		    response.sendRedirect("calmast.jsp?action=mastlist");
		}
		
		if(action.equalsIgnoreCase("showshift"))
		{
			ShiftHandler sh = new ShiftHandler();
			ArrayList<ShiftBean> shiftlist = new ArrayList<ShiftBean>();
			shiftlist = sh.getshiftvalues();
			session.setAttribute("shiftlist",shiftlist);
			response.sendRedirect("shift.jsp?action=list");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		ShiftBean sb = new ShiftBean();
		ShiftHandler sh = new ShiftHandler();
		String action=request.getParameter("action")==null?"":request.getParameter("action");
		
		if(action.equalsIgnoreCase("insert"))
		{
	  	sb.setShift(request.getParameter("shiftcode")!=null?request.getParameter("shiftcode"):"");
		sb.setStartTime(request.getParameter("starttime")!=null?request.getParameter("starttime"):"");
		sb.setEndTime(request.getParameter("endtime")!=null?request.getParameter("endtime"):"");
		sb.setStatus(request.getParameter("status")!=null?request.getParameter("status"):"");
		
		sh.insertValue(sb);
		ArrayList<ShiftBean> shiftlist = new ArrayList<ShiftBean>();
		shiftlist = sh.getshiftvalues();
		session.setAttribute("shiftlist",shiftlist);
		response.sendRedirect("shift.jsp?action=list");
		}
		
		
		ShiftHandler shifthand=new  ShiftHandler();
		if(action.equalsIgnoreCase("updateshift"))
		{
			
			boolean flag=false;
			int srno=Integer.parseInt(request.getParameter("srno"));
			ShiftBean shbean=new ShiftBean();
			String shiftcode=request.getParameter("shiftcode");
			String starttime =request.getParameter("starttime");
			String endtime=request.getParameter("endtime");
			String status=request.getParameter("status");
			shbean.setSrno(srno);
			shbean.setShift(shiftcode);
			shbean.setStartTime(starttime);
			shbean.setEndTime(endtime);
			shbean.setStatus(status);
			flag=shifthand.update(shbean);
			ArrayList<ShiftBean> shiftlist = new ArrayList<ShiftBean>();
			shiftlist = sh.getshiftvalues();
			session.setAttribute("shiftlist",shiftlist);
			if(flag)
			{
				response.sendRedirect("shift.jsp?action=list");
			}
			else
			{
				//System.out.println("error in record inserting");
			}
		}
		ArrayList<ShiftBean> ovtlist=new ArrayList<ShiftBean>();
		if(action.equalsIgnoreCase("addovertime"))
		{
			boolean flag=false;
			ShiftBean shbean1=new ShiftBean();
			shbean1.setEmptype(Integer.parseInt(request.getParameter("EmpType")));
			shbean1.setGrade(Integer.parseInt(request.getParameter("grade")));
			shbean1.setRate(Integer.parseInt(request.getParameter("rate")));
			shbean1.setShiftcode(Integer.parseInt(request.getParameter("shiftcode")));
			flag=shifthand.insertOvertime(shbean1);	
			ovtlist=shifthand.getList();
			session.setAttribute("ovtlist1", ovtlist);
			if(flag)
		    {
		    	response.sendRedirect("overtime.jsp?action=ovetimelist");
		    }
		    else{
		    	System.out.println("error in record inserting");
		    }
		}
		if(action.equalsIgnoreCase("updateovertime"))
		{
			
			ShiftBean shbean=new ShiftBean();
			boolean flag=false;
			
			shbean.setEmptype( Integer.parseInt(request.getParameter("EmpType")));
			shbean.setShiftcode(Integer.parseInt(request.getParameter("shiftcode")));
			shbean.setGrade(Integer.parseInt(request.getParameter("grade")));
			shbean.setRate(Integer.parseInt(request.getParameter("rate")));
			shbean.setSrno(Integer.parseInt(request.getParameter("srno")));
			flag=shifthand.UpdateOvertime(shbean);
			ovtlist=shifthand.getList();
			session.setAttribute("ovtlist1", ovtlist);
			if( flag )
			{	
				response.sendRedirect("overtime.jsp?action=ovetimelist&flag="+flag);
			}
			else
			{
				response.sendRedirect("overtime.jsp?action=ovetimelist");
			}
		}
		
		if(action.equalsIgnoreCase("addcalmast"))
		{
			boolean flag=false;
			
			ShiftBean shbean2=new ShiftBean();
			shbean2.setDay(request.getParameter("day"));
			shbean2.setDaydate(request.getParameter("daydate"));
			shbean2.setDaytype(request.getParameter("daytype"));
			shbean2.setEmptype(Integer.parseInt(request.getParameter("EmpType")));
			shbean2.setHoliday(request.getParameter("holiday"));
			shbean2.setDesc(request.getParameter("desc"));
			flag=shifthand.addCallmast(shbean2);
			if(flag)
			{  
				ArrayList<ShiftBean> callist=new ArrayList<>();
				callist=shifthand.getCalmastList();
				session.setAttribute("calmastlist", callist);
			    response.sendRedirect("calmast.jsp?action=mastlist");
			}
		}
		if(action.equalsIgnoreCase("updatecalmast"))
		{
            boolean flag=false;	
			ShiftBean shbean3=new ShiftBean();
			shbean3.setDay(request.getParameter("day1"));
			shbean3.setDaydate(request.getParameter("daydate1"));
			shbean3.setDaytype(request.getParameter("daytype1"));
			shbean3.setEmptype(Integer.parseInt(request.getParameter("EmpType1")));
			shbean3.setHoliday(request.getParameter("holiday1"));
			shbean3.setDesc(request.getParameter("desc1"));
			
			flag=shifthand.updateCalmast(shbean3);
			ArrayList<ShiftBean> callist=new ArrayList<>(); 	
		    callist=shifthand.getCalmastList();
		    session.setAttribute("calmastlist", callist);
			if(flag)
			{
				 response.sendRedirect("calmast.jsp?action=mastlist");
			}
			else{
				 response.sendRedirect("calmast.jsp?action=mastlist");
				}
		}
		if(action.equalsIgnoreCase("addtimesheet"))
		{
			boolean flag=false;
			ShiftBean shfbean=new ShiftBean();
            String EMPNO=request.getParameter("EMPNO");
			StringTokenizer st = new StringTokenizer(EMPNO,":");
		    while(st.hasMoreTokens()){EMPNO=st.nextToken();}
		    int empno=Integer.parseInt(EMPNO);
			shfbean.setEMPNO(empno);
			shfbean.setShift((request.getParameter("shiftcode")));
			shfbean.setDaydate(request.getParameter("daydate"));
			shfbean.setCheckin(request.getParameter("checkin"));
			shfbean.setCheckout(request.getParameter("checkout"));
			shfbean.setTotal(request.getParameter("total"));
			flag=shifthand.addTimesheet(shfbean);
			ArrayList<ShiftBean> shftlist1=new ArrayList<ShiftBean>();
			shftlist1=shifthand.getTimesheet();
			session.setAttribute("timesheet", shftlist1);
	       if(flag)
			{
				response.sendRedirect("timeSheet.jsp?action=showsheetlist");
			}
			else
			{
				response.sendRedirect("timeSheet.jsp?action=sheetlist");
			}
		}
		if(action.equals("updatetimesht"))
		{
			boolean flag=false;
			ShiftBean shfbean2=new ShiftBean();
			shfbean2.setEMPNO(Integer.parseInt(request.getParameter("EMPNO1")));
			shfbean2.setSrno(Integer.parseInt(request.getParameter("srno")));
			shfbean2.setShift((request.getParameter("shiftcode1")));
			shfbean2.setDaydate(request.getParameter("daydate1"));
			shfbean2.setCheckin(request.getParameter("checkin1"));
			shfbean2.setCheckout(request.getParameter("checkout1"));
			shfbean2.setTotal(request.getParameter("total1"));
			flag=shifthand.UpdateTimesheet(shfbean2);
			ArrayList<ShiftBean> shftlist3=new ArrayList<ShiftBean>();
		    shftlist3=shifthand.getTimesheet();
			session.setAttribute("timesheet", shftlist3);
			if(flag)
			{
				response.sendRedirect("timeSheet.jsp?action=showsheetlist");
			}
			else
			{
				response.sendRedirect("timeSheet.jsp?action=showsheetlist");
			}
			
		}
		
		// For Extra Duty Payment... 06 - 09-2017
		if(action.equals("addextra_duty_time"))
		{
			
			String empno	=	request.getParameter("empno")==null?"0":request.getParameter("empno");
			String date		=	request.getParameter("date")==null?"0":request.getParameter("date");
			String month	=	request.getParameter("month")==null?"0":request.getParameter("month");
			String basic	=	request.getParameter("basic")==null?"0":request.getParameter("basic");
			String da		=	request.getParameter("da")==null?"0":request.getParameter("da");
			String vda		=	request.getParameter("vda")==null?"0":request.getParameter("vda");
			String day		=	request.getParameter("day")==null?"0":request.getParameter("day");
			String calamt	=	request.getParameter("calamt")==null?"0":request.getParameter("calamt");
				//date=ReportDAO.EOM(date);
				System.out.println("i am in addextra_duty_time for empno..."+empno+"..date is..."+date);
		    ShiftHandler shifthandler=new  ShiftHandler();
		    //extra_duty_paymentBean DB = new extra_duty_paymentBean();
		    String flags = "";
		    
		    try
			   {
				   flags=shifthandler.save_Extra_Payment(date,empno,basic,da,vda,day,calamt,month);
				   PrintWriter out1 = response.getWriter();
					response.setContentType("text/html");
					out1.write(flags.toString());
		    } 
			   	catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
		}
		
		
		if(action.equalsIgnoreCase("PostExtraPayment"))
		{
			 ShiftHandler shifthandler=new  ShiftHandler();
			/*String date=request.getParameter("date");*/
			 String checkempno=request.getParameter("checkempno");
			
			//checkempno.replace(",",":");
			//String checkempno1[]=checkempno.split(":");
			//int emp[]=Integer.parseInt(checkempno.replace(",",""));
			//System.out.println("inside posting extra payment....for checked empo........check lenghth.."+checkempno);
			ArrayList<extra_duty_paymentBean> checkEmplist =new ArrayList<extra_duty_paymentBean>();
			String uid = session.getAttribute("UID").toString();
			
			/*for ( j=0;j<'\0';j=j+(b.indexOf(",")+1));
			{
				if((b.charAt(j))==',')
				{
					
				}
				a[j]=Integer.parseInt(b.substring(j,b.indexOf(",")-1));
				System.out.println("heyyyy aniket...."+a[j]);
			}*/
			
			/*for( i=0;i<checkempno1.length;i++)
			{
				System.out.println("wwwwwwwwwww......"+checkempno1[i]);
				extra_duty_paymentBean eb=new extra_duty_paymentBean();
				eb.setEMPNO(Integer.parseInt(checkempno1[i]));
			
				System.out.println("empooooooooooo...."+Integer.parseInt(checkempno1[i]));
				checkEmplist.add(eb);
			}*/
			ArrayList<extra_duty_paymentBean> Emplist = (ArrayList<extra_duty_paymentBean>)session.getAttribute("EMPLIST");
			//System.out.println("emplist which is in session & its size...."+Emplist.size());
			 try
			   {
				 String flag=shifthandler.Post_Extra_Payment(checkempno,Emplist,uid);
				 PrintWriter out1 = response.getWriter();
				 response.setContentType("text/html");
				 out1.write(flag.toString());
			   } 
		   	catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		
		if(action.equalsIgnoreCase("getdate"))
		{
			String empid=request.getParameter("empno");
			String  empno[]=empid.split(":");
			ResultSet rs3=null;
			Statement st2= null;
			String PaytranDate="";
			try{
				
			
			Connection con=ConnectionManager.getConnection();
			st2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs3= st2.executeQuery("select distinct TRNDT from paytran WHERE  trncd = 101 and EMPNO ="+empno[2]);
			while(rs3.next())
			{
				PaytranDate = rs3.getString("TRNDT");
				//System.out.println("PaytranDate for employee "+PaytranDate);
			}
			 PrintWriter out1 = response.getWriter();
			 response.setContentType("text/html");
			 out1.write(PaytranDate.toString());
			}
			catch (Exception e) 
	     	{
				e.printStackTrace();
			}
			
			
		}
		
		
		if(action.equalsIgnoreCase("checkdate"))
		{
			String empid=request.getParameter("empno");
			String  empno[]=empid.split(":");
			String date=request.getParameter("date");
			ResultSet rs3=null;
			Statement st2= null;
			String flag="";
			try{
				
			
			Connection con=ConnectionManager.getConnection();
			st2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs3= st2.executeQuery("select * from Extra_Duty_Payment WHERE  EMPNO ="+empno[2]);
			if(rs3.next())
			{
				rs3.previous();
				while(rs3.next())
				{
						if(rs3.getString("TRNDT").contains(date))
						{
							flag="present";
							System.out.println("date is present..."+date);
							break;
						}
						else
						{
							flag="nopresent";
							System.out.println("date is not present..."+date);
						}
				}
			}
			else
			{
				flag="nopresent";
				System.out.println("date is not present..."+date);
			}
			 PrintWriter out1 = response.getWriter();
			 response.setContentType("text/html");
			 out1.write(flag.toString());
			}
			catch (Exception e) 
	     	{
				e.printStackTrace();
			}
			
			
		}
		
		if(action.equalsIgnoreCase("checkrecord"))
		{
			String empid=request.getParameter("empno");
			//String  empno[]=empid.split(":");
			//String date=request.getParameter("date");
			ResultSet rs3=null;
			Statement st2= null;
			String flag="";
			
			System.out.println("checkrecord....."+empid);
			try{
				
			
			Connection con=ConnectionManager.getConnection();
			st2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs3= st2.executeQuery("select * from Extra_Duty_Payment WHERE  EMPNO ="+empid+" AND STATUS='C' ");
			if(rs3.next())
			{
				
					flag="present";
					System.out.println("checkrecord....."+empid +" present calculated....");
				
			}
			else
			{
				flag="nopresent";
			}
			 PrintWriter out1 = response.getWriter();
			 response.setContentType("text/html");
			 out1.write(flag.toString());
			}
			catch (Exception e) 
	     	{
				e.printStackTrace();
			}
			
			
		}
		
		
		if(action.equalsIgnoreCase("DeleteExtraPayment"))
		{
			String empid=request.getParameter("checkempno");
			String  empno[]=empid.split(",");
			//String date=request.getParameter("date");
			ResultSet rs3=null;
			Statement st2= null;
			String flag="";
			
			
			try{
				
			
			Connection con=ConnectionManager.getConnection();
			st2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for(int i=0;i<empno.length;i++)
			{
				st2.execute("DELETE FROM Extra_Duty_Payment WHERE  EMPNO="+empno[i]+" AND STATUS='C' ");
			
				flag="DELETE";
				System.out.println("DeleteExtraPayment....."+empno[i]);
			}
			 PrintWriter out1 = response.getWriter();
			 response.setContentType("text/html");
			 out1.write(flag.toString());
			}
			catch (Exception e) 
	     	{
				e.printStackTrace();
			}
			
			
		}
		if(action.equalsIgnoreCase("lwpamount_post"))
		{

			
			String empno	=	request.getParameter("empno")==null?"0":request.getParameter("empno");
			//String date		=	request.getParameter("date")==null?"0":request.getParameter("date");
			String month	=	request.getParameter("month")==null?"0":request.getParameter("month");
			String basic	=	request.getParameter("basic")==null?"0":request.getParameter("basic");
			String da		=	request.getParameter("da")==null?"0":request.getParameter("da");
			String vda		=	request.getParameter("vda")==null?"0":request.getParameter("vda");
		String day		=	request.getParameter("day")==null?"0":request.getParameter("day");
			String calamt	=	request.getParameter("cal_amt")==null?"0":request.getParameter("cal_amt");
			String pfcalamt	=	request.getParameter("pfcal_amt")==null?"0":request.getParameter("pfcal_amt");
			String trntype	=	request.getParameter("trntype")==null?"0":request.getParameter("trntype");
			String reason	=	request.getParameter("reason1")==null?"0":request.getParameter("reason1");
			String HRA		=	request.getParameter("HRA")==null?"0":request.getParameter("HRA");
			String MEDALLOW	=	request.getParameter("MEDALLOW")==null?"0":request.getParameter("MEDALLOW");
			String EDUALLOW	=	request.getParameter("EDUALLOW")==null?"0":request.getParameter("EDUALLOW");
			String CONVEYANCE=	request.getParameter("CONVEYANCE")==null?"0":request.getParameter("CONVEYANCE");
			String SPECIAL	=	request.getParameter("SPECIAL")==null?"0":request.getParameter("SPECIAL");
				
			//date=ReportDAO.EOM(date);
				System.out.println("i am in addextra_duty_time for empno..."+request.getParameter("HRA")+  " "+HRA+" "+ MEDALLOW+" "+ EDUALLOW+" "+   CONVEYANCE+" "+ SPECIAL);
		    ShiftHandler shifthandler=new  ShiftHandler();
		    //extra_duty_paymentBean DB = new extra_duty_paymentBean();
		    String flags = "";
		    
		    try
			   {
				   flags=shifthandler.save_lwpamount(empno,basic,da,vda,day,calamt,month,pfcalamt,trntype,reason,HRA ,MEDALLOW,EDUALLOW ,CONVEYANCE,SPECIAL);
				   PrintWriter out1 = response.getWriter();
					response.setContentType("text/html");
					out1.write(flags.toString());
		    } 
			   	catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
		
		}
		
		
		
	}

}
