package payroll.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.mysql.jdbc.Connection;

import payroll.Core.ReportDAO;
import payroll.DAO.ConnectionManager;
import payroll.DAO.EmpOffHandler;
import payroll.DAO.TranHandler;
import payroll.Model.TranBean;
import payroll.Model.TransactionBean;

/**
 * Servlet implementation class TransctionServlet
 */
@WebServlet("/TransactionServlet")
public class TransactionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
   
		
		HttpSession session= request.getSession();	
		TransactionBean trbn = new TransactionBean();
		TranBean tb = new TranBean();
		EmpOffHandler emp = new EmpOffHandler();
	    TranHandler trn = new TranHandler();
	    ArrayList<TranBean> trlist = new ArrayList<TranBean>();
	    String EMPNO = request.getParameter("no");
	   
		trbn=emp.getInfoEmpTran(EMPNO);
	    trlist=trn.getTranInfo(EMPNO,"tran");//tran :-fire query to tran table
	    
	    System.out.println("trbn set"+trbn.getEmpno());
	    request.setAttribute("empno", EMPNO);
	    request.setAttribute("trbn",trbn); 
	    session.setAttribute("trlist",trlist);
		request.getRequestDispatcher("tranMaintainences.jsp?action=getdata").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session= request.getSession();	
		
		String action = request.getParameter("action")==null?"":request.getParameter("action");
		TransactionBean trbn = new TransactionBean();
		TranBean tb = new TranBean();
		EmpOffHandler emp = new EmpOffHandler();
	    TranHandler trn = new TranHandler();
		if(action.equalsIgnoreCase("update"))//TRNDT EMPNO SRNO TRNCD 
		{
			int empno=Integer.parseInt(request.getParameter("empno"));
			int trncd =Integer.parseInt(request.getParameter("trncd"));
			int srno =Integer.parseInt(request.getParameter("srno"));
			String trdate = request.getParameter("trndate");
			System.out.println("tr date is "+trdate);
			tb.setADJ_AMT(Float.parseFloat(request.getParameter("adjamt")==null?"0":request.getParameter("adjamt")));
			//tb.setUPDDT(request.getParameter(""));
			tb.setCF_SW(request.getParameter("cryfwd"));
			tb.setINP_AMT(Float.parseFloat(request.getParameter("inpamnt")==null?"0":request.getParameter("inpamnt")));
			tb.setNET_AMT(Float.parseFloat(request.getParameter("netamnt")==null?"0":request.getParameter("netamnt")));
			tb.setCAL_AMT(Float.parseFloat(request.getParameter("calamt")==null?"0":request.getParameter("calamt")));
			
		    tb.setEMPNO(empno);
		    tb.setSRNO(srno);
		    tb.setTRNCD(trncd);
		    tb.setTRNDT(trdate);
			boolean flag = trn.UpdateTransaction(tb);
			if(flag==true)
			{
				System.out.println("Record Modified " + request.getParameter("select.trncd"));
				response.sendRedirect("updateTransaction.jsp?action=close&key="+empno+":"+trncd+":"+srno+":"+trdate);
			}
			else
			{
				System.out.println("Error in modifying Record");
				response.sendRedirect("updateTransaction.jsp?action=keep&key="+empno+":"+trncd+":"+srno+":"+trdate);
			}
			
			
		}
		else if(action.equalsIgnoreCase("addnewTran"))
		{

			System.out.println("now in addnewtran");
			String flag = null;
			TranHandler tranhandler=new TranHandler();
			TranBean tranobj=new TranBean();
			tranobj.setTRNDT(request.getParameter("trandate"));
			tranobj.setTRNCD(Integer.parseInt(request.getParameter("trancd"))==0?Integer.parseInt(request.getParameter("trancd1")):Integer.parseInt(request.getParameter("trancd")));
            String EMPNO= request.getParameter("EMPNO");
            System.out.println("empno is"+EMPNO);
            StringTokenizer st1 = new StringTokenizer(EMPNO,":");
 		    while(st1.hasMoreTokens())
 		    {
 		    	EMPNO=st1.nextToken();
 		    }
			
			tranobj.setEMPNO(Integer.parseInt(EMPNO));
			//tranobj.setINP_AMT(Integer.parseInt(request.getParameter("inpamnt")));
			tranobj.setINP_AMT(Float.parseFloat(request.getParameter("inpamnt")));
			tranobj.setADJ_AMT(Integer.parseInt("0"));
			tranobj.setNET_AMT(Integer.parseInt("0"));
			tranobj.setARR_AMT(Integer.parseInt("0"));
			tranobj.setCF_SW(request.getParameter("cryfwd"));
			tranobj.setCAL_AMT(Integer.parseInt("0"));
		
			String uid=session.getAttribute("UID").toString();
			tranobj.setUSRCODE(uid);
			tranobj.setUPDDT(request.getParameter("trandate"));
			tranobj.setSTATUS("A");
			flag = tranhandler.addTransaction(tranobj);
			if(flag.equals("true"))
			{
				// request.getRequestDispatcher("addNewTransaction.jsp?action=close").forward(request, response);
			    response.sendRedirect("addNewTransaction.jsp?action=close"); 
				System.out.println("query executed successfully");
			}
			else if (flag.equals("present"))
			{
				response.sendRedirect("addNewTransaction.jsp?action=present");
				System.out.println("Record Alredy Present");	
			}
			else if(flag.equals("false"))
			{
				//response.sendRedirect("addNewTransaction.jsp?action=keep");
				System.out.println("error in record inserting");	
			}
			else 
			{
				//response.sendRedirect("addNewTransaction.jsp?action=keep");
				System.out.println("error in record inserting");	
			}
			
			
		
		}
		else if(action.equalsIgnoreCase("trnlist"))
		{
	
			String EMPNO;
			String list=request.getParameter("list")==null?"":request.getParameter("list");
			if(list.equalsIgnoreCase(""))
			{
				String[] employ = request.getParameter("EMPNO").split(":");
			    EMPNO = employ[2].trim();
			}	
			else if(list.equalsIgnoreCase("negative"))
			{
				EMPNO=request.getParameter("EMPNO");
			}
			else 
			{
				EMPNO=request.getParameter("EMPNO");
				String[] employ= EMPNO.split(":");
			 	EMPNO=employ[2];
			}
			//old before if else
			//EMPNO=request.getParameter("EMPNO");
			//String[] employ= EMPNO.split(":");
		 	//EMPNO=employ[2];
			    
			
			ArrayList<TranBean> trlist = new ArrayList<TranBean>();
			 trbn=emp.getInfoEmpTran(EMPNO);
		    trlist=trn.getTranInfo(EMPNO,"tran");// tran :-fire query to tran table
		    
		    
		    request.setAttribute("trbn",trbn); 
		    request.setAttribute("empno1",EMPNO); 
		 
		    session.setAttribute("trlist",trlist);
		    request.getRequestDispatcher("tranMaintainences.jsp?action=getdata").forward(request, response);
		    
		   // response.sendRedirect("tranMaintainences.jsp?action=getdata");
		       
		}
		//additional method for negative salary disp
		/*else if(action.equalsIgnoreCase("nlist"))
		{
	
			String EMPNO;
			EMPNO=request.getParameter("EMPNO");
			
			ArrayList<TranBean> trlist = new ArrayList<TranBean>();
			 trbn=emp.getInfoEmpTran(EMPNO);
		    trlist=trn.getTranInfo(EMPNO,"tran");// tran :-fire query to tran table
		    
		    
		    request.setAttribute("trbn",trbn); 
		    request.setAttribute("empno1",EMPNO); 
		 
		    session.setAttribute("trlist",trlist);
		    request.getRequestDispatcher("tranMaintainences.jsp?action=getdata").forward(request, response);
		    
		   // response.sendRedirect("tranMaintainences.jsp?action=getdata");
		       
		}	*/	
		if(action.equalsIgnoreCase("addtran"))
		{
			
			System.out.println("now in addnewtran");
			TranHandler tranhandler=new TranHandler();
			TranBean tranobj=new TranBean();
			
			
			  DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		       //get current date time with Date()
		       Date date = new Date();
		       String dt= dateFormat.format(date);
			  System.out.println("date is"+dt);
			//***************************
			
			//boolean flag=false;
			int flag=0;
			
            String EMPNO= request.getParameter("EMPNO");
            System.out.println("empno is"+EMPNO);
            StringTokenizer st1 = new StringTokenizer(EMPNO,":");
 		    while(st1.hasMoreTokens())
 		    {
 		    	EMPNO=st1.nextToken();
 		    	System.out.println("empno is"+EMPNO);
 		    }
 		   
			
 		    
 		    
 		    tranobj.setTRNDT(ReportDAO.BOM(dt));
 		    String trncd=request.getParameter("trancd"+dt+"  "+ (ReportDAO.BOM(dt)));
 		    System.out.println("transaction code"+trncd);
 		   System.out.println("date BOM"+ReportDAO.BOM(dt));
 		    tranobj.setTRNCD(Integer.parseInt(request.getParameter("trancd")));
			tranobj.setEMPNO(Integer.parseInt(EMPNO));
			tranobj.setINP_AMT(Integer.parseInt(request.getParameter("amount")));
			tranobj.setADJ_AMT(0);
			tranobj.setNET_AMT(0);
			tranobj.setARR_AMT(0);
			tranobj.setCF_SW("*");
			tranobj.setCAL_AMT(0);
		
			String uid=session.getAttribute("UID").toString();
			tranobj.setUSRCODE(uid);
			tranobj.setUPDDT(ReportDAO.BOM(dt));
			tranobj.setSTATUS("A");
			flag = tranhandler.addNewTransaction(tranobj);
		   if(flag==1)
		   {
			  System.out.println("Record inserted successfully");
			  response.sendRedirect("empTranDetails.jsp?flag="+flag);
			   
		   }
		   else
		   {
			      System.out.println("Error in record inserting");
				  response.sendRedirect("empTranDetails.jsp?flag="+flag);
		   }
			//**************************
			
			
		}
		
		if(action.equalsIgnoreCase("postTran"))
		{
			int empno = Integer.parseInt(request.getParameter("empno").toString());
			TranHandler TH = new TranHandler();
			boolean flag = TH.addToYTDTran(empno);
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.write(String.valueOf(flag));
		}
		if(action.equalsIgnoreCase("finalize"))
		{
			String user = session.getAttribute("name").toString();
			String date = request.getParameter("sancDate");
			String emplist = request.getParameter("empList");
			TranHandler TH = new TranHandler();
			boolean flag = TH.finalizeTran(emplist,date,user);
			if(flag){
			response.sendRedirect("FinalizeTran.jsp?flag=1");
			}
			else
			{	response.sendRedirect("FinalizeTran.jsp?flag=2");
			}
		}
		
		if(action.equalsIgnoreCase("edittran"))
		{
			boolean flag=false;
			int empno=Integer.parseInt(request.getParameter("empno"));
			int trncd=Integer.parseInt(request.getParameter("trncd"));
			int amount =Integer.parseInt(request.getParameter("amount"));
			TranBean tb1=new TranBean();
			TranHandler TH = new TranHandler();
			tb1.setEMPNO(empno);
			tb1.setTRNCD(trncd);
			tb1.setINP_AMT(amount);
			flag=TH.updatetranAmount(tb1);
			System.out.println("flag after edit"+flag);
			if(flag)
			{
			  response.sendRedirect("empTranDetails.jsp?flag=3");	
			}
			else
			{
				response.sendRedirect("empTranDetails.jsp?flag=4");
				
			}
		}
		
		else if (action.equalsIgnoreCase("editTranValues")) 
		  {
		   int trncd = Integer.parseInt(request.getParameter("trncd"));
		   
		   String[] values = request.getParameterValues("tranValue");
		   Float vals[] = new Float[values.length];
		   for(int i=0; i<values.length; i++)
		   {
		    vals[i] = Float.parseFloat(values[i].trim());
		   }
		   TranHandler thr = new TranHandler();
		   boolean flag= thr.updateEmpTran(trncd, vals);
		   if(flag)
		   {
		    response.sendRedirect("empTranDetails.jsp?flag=3");
		   }
		   else
		   {
		    response.sendRedirect("empTranDetails.jsp?flag=4");
		   }
		  }
		
		else if (action.equalsIgnoreCase("editTranValues1")) 
		{
			int trncd = Integer.parseInt(request.getParameter("trncd"));
	
			String[] values = request.getParameterValues("tranValue");
			Float vals[] = new Float[values.length];
			for(int i=0; i<values.length; i++)
			{
				vals[i] = Float.parseFloat(values[i].trim());
			}
			TranHandler thr = new TranHandler();
			//boolean flag = thr.updateEmpTran(trncd, vals);
			@SuppressWarnings("unchecked")
			boolean flag = thr.updateEmpTranNew1(trncd, vals, (ArrayList<TranBean>)session.getAttribute("projEmpNolist"));
			if(flag)
			{
				response.sendRedirect("EmpTranNew.jsp?action=afterEdit&flag=3");
			}
			else
			{
				response.sendRedirect("EmpTranNew.jsp?flag=4");
			}
		}
		else if (action.equalsIgnoreCase("editSiteTranValues")) 
		{
			/*String[] mobded = request.getParameterValues("mobded");
			Float values[] = new Float[mobded.length];
			for(int i=0; i<mobded.length; i++)
			{
				values[i] = Float.parseFloat(mobded[i].trim());
			}*/
			
			String[] lopded = request.getParameterValues("lopded");
			Float vals[] = new Float[lopded.length];
			for(int i=0; i<lopded.length; i++)
			{
				vals[i] = Float.parseFloat(lopded[i].trim());
			}
			
			TranHandler thr = new TranHandler();
			String uid = session.getAttribute("UID").toString();
			String user = session.getAttribute("name").toString();
			String empno = session.getAttribute("EMPNO").toString();
			String updatename = empno+"-"+uid+"-"+user;
			System.out.println("name"+updatename);
			@SuppressWarnings("unchecked")
			//boolean flag = thr.updateSiteEmpTranNew(values, vals, (ArrayList<TranBean>)session.getAttribute("emplist"), updatename);
			boolean flag = thr.updateSiteEmpTranNew(vals, (ArrayList<TranBean>)session.getAttribute("emplist"), updatename);
			if(flag)
			{
				response.sendRedirect("SiteEmpTran.jsp?action=afterEdit&flag=3");
			}
			else
			{
				response.sendRedirect("SiteEmpTran.jsp?flag=4");
			}
		}
		else if (action.equalsIgnoreCase("editMobileTranValues")) 
		{
			int trncd = Integer.parseInt(request.getParameter("trncd"));
				
				String[] values = request.getParameterValues("tranValue");
			   String[] values1 = request.getParameterValues("emnp");
			   String vals1[]= new  String[values1.length];
			   Float vals[] = new Float[values.length];
			   for(int i=0; i<values.length; i++)
			   {
				  
			    vals[i] = Float.parseFloat(values[i].trim());
			    vals1[i]= values1[i].trim();
			   }
			   
			   
			   
			   TranHandler thr = new TranHandler();
			   boolean flag = thr.updateEmpTran_mob(trncd, vals,vals1);
			if(flag)
			{
				response.sendRedirect("MobNo.jsp?action=afterEdit&flag=3");
			}
			else
			{
				response.sendRedirect("MobNo.jsp?flag=4");
			}
		}
		else if (action.equalsIgnoreCase("release")) 
		{
			   TranHandler thr = new TranHandler();
			   String date = request.getParameter("date");
			   int range = request.getParameter("salrange")==null?0:Integer.parseInt(request.getParameter("salrange"));
			   String proj = request.getParameter("projlist");
			   proj = proj.replaceAll("&", " and ");
			   String[] values = request.getParameterValues("chk");
			   String vals = "";
			   for(int i=0; i<values.length; i++)
			   {
				   if(i==(values.length)-1) {
					   vals += values[i];
				   } else {
					   vals += values[i]+",";
				   }
			   }
			   thr.releaseSalary(vals,date);
			   response.sendRedirect("SalaryDetails.jsp?action=details&proj="+proj+"&date="+date+"&rng="+range);
			   
		}
		else if (action.equalsIgnoreCase("updateSal"))
		{
			ArrayList<TranBean> result = new ArrayList<TranBean>();
			ArrayList<TranBean> updated = new ArrayList<TranBean>();
			TranHandler hdlr = new TranHandler();
			TranBean bean;
			float ded = 0;
			int count =(Integer)session.getAttribute("deduct_counter");
		   String date = request.getParameter("month");
		   String empno = request.getParameter("empno");
		   float totDed = Float.parseFloat(request.getParameter("total_deduct"));
		   float netPay = Float.parseFloat(request.getParameter("net_pay"));
		   //System.out.println(date+" "+empno);
		   result = (ArrayList<TranBean>)session.getAttribute("list");
		   for(TranBean tbn : result) {
			   if(tbn.getTRNCD()==999) {
				   ded = tbn.getCAL_AMT();
			   }
			   /*if(tbn.getTRNCD()>200 && tbn.getTRNCD()<300 && tbn.getTRNCD()!=999){
				   count++;
			   }*/
		   }
		   session.removeAttribute("list");
	
		   for(int i=0;i<count;i++){
			   //System.out.println(request.getParameter("deduct"+i)+""+request.getParameter("dedcode"+i));
			   bean = new TranBean();

			   bean.setINP_AMT(request.getParameter("deduct"+i)==""?0:Float.parseFloat(request.getParameter("deduct"+i)));
			   bean.setCAL_AMT(request.getParameter("deduct"+i)==""?0:Float.parseFloat(request.getParameter("deduct"+i)));
			   bean.setNET_AMT(request.getParameter("deduct"+i)==""?0:Float.parseFloat(request.getParameter("deduct"+i)));
			   bean.setTRNCD(Integer.parseInt(request.getParameter("dedcode"+i)));
			   updated.add(bean);
		   }
		   session.removeAttribute("deduct_counter");
		   /*if(ded==totDed) {
			   response.sendRedirect("salaryChange.jsp?action=nochange&eno="+empno+"&date="+date);
		   } else {*/
			   boolean flag = hdlr.updateSalChange(empno, date, updated, totDed, netPay);
			   if(flag){
				   response.sendRedirect("salaryChange.jsp?action=close&eno="+empno+"&date="+date);
			   }else{
				   response.sendRedirect("salaryChange.jsp?action=keep&eno="+empno+"&date="+date);
			   }
		   /*}*/
		   		   
		}
		else if (action.equalsIgnoreCase("salarydiff")) 
		{
			TranHandler th=new TranHandler();
			 String cuurentdate = request.getParameter("date");
			 String salary_increment_date ="01-" + request.getParameter("incrmntmonth");
			 salary_increment_date=ReportDAO.EOM(salary_increment_date);
			 cuurentdate = ReportDAO.EOM(cuurentdate);
			String itype =request.getParameter("incrmnttype");
			String fg= th.SalaryDifferenceUpdate(salary_increment_date,cuurentdate,itype);
			System.out.println("fg........."+fg);
			if(fg.equalsIgnoreCase("done"))
			{
				 response.sendRedirect("SalaryDiff.jsp?Updated="+fg);
			}
			else
			{
				 response.sendRedirect("SalaryDiff.jsp?Updated=notupdated");
			}
			
		}
		
		
		
	

}
}
