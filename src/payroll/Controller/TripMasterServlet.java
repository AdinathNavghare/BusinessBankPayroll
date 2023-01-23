package payroll.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.DAO.ConnectionManager;
import payroll.DAO.EmpOffHandler;
import payroll.DAO.TranHandler;
import payroll.DAO.TripHandler;
import payroll.Model.TranBean;
import payroll.Model.TransactionBean;
import payroll.Model.TripBean;

/**
 * Servlet implementation class TripMasterServlet
 */
@WebServlet("/TripMasterServlet")
public class TripMasterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TripMasterServlet() {
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
		
		String action = request.getParameter("action")==null?"":request.getParameter("action");
		TransactionBean trbn = new TransactionBean();
		EmpOffHandler emp = new EmpOffHandler();
		TranHandler trn = new TranHandler();
		TripBean tb = new TripBean();
		TripHandler trh = new TripHandler();
		HttpSession session= request.getSession();	
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
	 	if(action.equalsIgnoreCase("tripEmpInfo")){
	 		
	 		String EMPNO;
			EMPNO=request.getParameter("EMPNO");
			String[] employ= EMPNO.split(":");
		 	EMPNO=employ[2];
			    
	 		
	 		ArrayList<TranBean> trlist = new ArrayList<TranBean>();
			 trbn=emp.getInfoEmpTran(EMPNO);
		    trlist=trn.getTranInfo(EMPNO,"tran");// tran :-fire query to tran table
		    
		    
		    request.setAttribute("trbn",trbn); 
		    request.setAttribute("empno1",EMPNO); 
		 
		    session.setAttribute("trlist",trlist);	
		    request.getRequestDispatcher("Trip.jsp?action=getdata").forward(request, response);
		    
	 		
	 	}
	 	

	 	
	 	else if(action.equalsIgnoreCase("addtraveladmin"))
		{
	
			System.out.println("in addtraveladmin method");
						
			tb.setEMPNO(Integer.parseInt(request.getParameter("treno")));
			tb.setENAME(request.getParameter("trename")!=null?request.getParameter("trename"):"");
			tb.setTOURRPT(request.getParameter("trname")!=null?request.getParameter("trname"):"");
			tb.setAPPDATE(request.getParameter("appdate")!=null?request.getParameter("appdate"):"");
			tb.setSTARTDATE(request.getParameter("startdate")!=null?request.getParameter("startdate"):"");
			tb.setENDDATE(request.getParameter("enddate")!=null?request.getParameter("enddate"):"");
			tb.setSTARTTIME(request.getParameter("starttime")!=null?request.getParameter("starttime"):"");
			tb.setENDTIME(request.getParameter("endtime")!=null?request.getParameter("endtime"):"");
						
			tb.setTRCODE(trh.addTravelAdmin(tb));
			 
			session.setAttribute("tbean", tb);
			
			request.getRequestDispatcher("TripDetails.jsp").forward(request, response);
			
			
		}
	 	else if(action.equalsIgnoreCase("addjourneydetails"))
		{
	
			System.out.println("in addjourneydetails method");
			boolean flag=false;	

			tb.setEMPNO(Integer.parseInt(request.getParameter("jreno")));
			tb.setTRCODE(Integer.parseInt(request.getParameter("jrtrcode")));
			tb.setDDATE(request.getParameter("jDate")!=null?request.getParameter("jDate"):"");
			tb.setDTMODE(Integer.parseInt(request.getParameter("jMode")));
			tb.setDFROM(request.getParameter("jFrom")!=null?request.getParameter("jFrom"):"");
			tb.setDTO(request.getParameter("jTo")!=null?request.getParameter("jTo"):"");
			tb.setDCLASS(Integer.parseInt(request.getParameter("jClass")));
			tb.setDFAIRDP(Integer.parseInt(request.getParameter("jFair")));
			tb.setDAMT(Float.parseFloat(request.getParameter("jAmount")));
			
			flag=trh.addJourneyDetails(tb);
			
			if(flag == true)
	    	{
	    		response.sendRedirect("TripInfo.jsp?action=close"); 
			}
			else
			{
				response.sendRedirect("TripInfo.jsp?action=keep");
			}
			
		}
	 	else if(action.equalsIgnoreCase("addlocexpdetails"))
		{
	
			System.out.println("in addlocexpdetails method");
			boolean flag=false;
			tb.setEMPNO(Integer.parseInt(request.getParameter("lceno")));
			tb.setTRCODE(Integer.parseInt(request.getParameter("lctrcode")));
			tb.setDDATE(request.getParameter("lcDate")!=null?request.getParameter("lcDate"):"");
			tb.setDTMODE(Integer.parseInt(request.getParameter("lcModeLc")));
			tb.setDFROM(request.getParameter("lcFrom")!=null?request.getParameter("lcFrom"):"");
			tb.setDTO(request.getParameter("lcTo")!=null?request.getParameter("lcTo"):"");
			tb.setDFAIRDP(Integer.parseInt(request.getParameter("lcDist")));
			tb.setDAMT(Float.parseFloat(request.getParameter("lcAmount")));
			
			flag=trh.addLocalConvExpDetails(tb);
			if(flag){
				response.sendRedirect("TripInfo.jsp?action=close");
			}
			else
			{
				response.sendRedirect("TripInfo.jsp?action=keep");
			}
			
		}
	 	else if(action.equalsIgnoreCase("addfoodexpdetails"))
		{
	
			System.out.println("in addfoodexpdetails method");
			boolean flag=false;
			tb.setEMPNO(Integer.parseInt(request.getParameter("feeno")));
			tb.setTRCODE(Integer.parseInt(request.getParameter("fetrcode")));
			tb.setDDATE(request.getParameter("feDate")!=null?request.getParameter("feDate"):"");
			tb.setDFAIRDP(Integer.parseInt(request.getParameter("fePerDay")));
			tb.setNOFDAY(Integer.parseInt(request.getParameter("feNday")));
			tb.setDFROM(request.getParameter("feFrom")!=null?request.getParameter("feFrom"):"");
			tb.setDTO(request.getParameter("feTo")!=null?request.getParameter("feTo"):"");
			tb.setDAMT(Float.parseFloat(request.getParameter("feAmount")));
			
			flag=trh.addFoodExpDetails(tb);
			if(flag){
				response.sendRedirect("TripInfo.jsp?action=close");
			}
			else
			{
				response.sendRedirect("TripInfo.jsp?action=keep");
			}
			
		}
	 	else if(action.equalsIgnoreCase("addothermiscexpdetails"))
		{
	
			System.out.println("in addothermiscexpdetails method");
			boolean flag=false;
			tb.setEMPNO(Integer.parseInt(request.getParameter("omeeno")));
			tb.setTRCODE(Integer.parseInt(request.getParameter("ometrcode")));
			tb.setDDATE(request.getParameter("omeDate")!=null?request.getParameter("omeDate"):"");
			tb.setDBILLNO(request.getParameter("omeBillNo")!=null?request.getParameter("omeBillNo"):"");
			tb.setDPARTI(request.getParameter("omeParticulars")!=null?request.getParameter("omeParticulars"):"");
			tb.setDPNAME(request.getParameter("omePartyName")!=null?request.getParameter("omePartyName"):"");
			tb.setDAMT(Float.parseFloat(request.getParameter("omeAmount")));
			
			flag=trh.addOtherMiscExpDetails(tb);
			if(flag){
				response.sendRedirect("TripInfo.jsp?action=close");
			}
			else
			{
				response.sendRedirect("TripInfo.jsp?action=keep");
			}
			
		}
	 	else if(action.equalsIgnoreCase("addmiscexpdetails"))
		{
	
			System.out.println("in addmiscexpdetails method");
			boolean flag=false;
			tb.setEMPNO(Integer.parseInt(request.getParameter("meeno")));
			tb.setTRCODE(Integer.parseInt(request.getParameter("metrcode")));
			tb.setDDATE(request.getParameter("miscDate")!=null?request.getParameter("miscDate"):"");
			tb.setDPARTI(request.getParameter("miscParticulars")!=null?request.getParameter("miscParticulars"):"");
			tb.setDLOCATION(request.getParameter("miscLocation")!=null?request.getParameter("miscLocation"):"");
			tb.setDAMT(Float.parseFloat(request.getParameter("miscAmount")));
			
			flag=trh.addMiscExpDetails(tb);
			if(flag){
				response.sendRedirect("TripInfo.jsp?action=close");
			}
			else
			{
				response.sendRedirect("TripInfo.jsp?action=keep");
			}
			
		}
	 	else if(action.equalsIgnoreCase("addacimprestdetails"))
		{
	
			System.out.println("in addacimprestdetails method");
			boolean flag=false;
			tb.setEMPNO(Integer.parseInt(request.getParameter("aceno")));
			tb.setTRCODE(Integer.parseInt(request.getParameter("actrcode")));
			tb.setACDATE(request.getParameter("acDetails")!=null?request.getParameter("acDetails"):"");
			tb.setACPARTI(request.getParameter("acPar")!=null?request.getParameter("acPar"):"");
			tb.setACREPTNO(request.getParameter("acReceipt")!=null?request.getParameter("acReceipt"):"");
			tb.setACPAYMNT(request.getParameter("acPayment")!=""?Float.parseFloat(request.getParameter("acPayment")):0.0f);
			tb.setACAMT(request.getParameter("acAmount")!=""?Float.parseFloat(request.getParameter("acAmount")):0.0f);
			
			tb.setTOURRPT(request.getParameter("acTrReport")!=null?request.getParameter("acTrReport"):"");
			flag=trh.addAcimprestDetails(tb);
			
			tb.setEMPNO(Integer.parseInt(request.getParameter("aceno")));
			tb.setTRCODE(Integer.parseInt(request.getParameter("actrcode")));
			tb.setACDATE(request.getParameter("acDetails1")!=null?request.getParameter("acDetails1"):"");
			tb.setACPARTI(request.getParameter("acPar1")!=null?request.getParameter("acPar1"):"");
			tb.setACREPTNO(request.getParameter("acReceipt1")!=null?request.getParameter("acReceipt1"):"");
			tb.setACPAYMNT(request.getParameter("acPayment1")!=""?Float.parseFloat(request.getParameter("acPayment1")):0.0f);
			tb.setACAMT(request.getParameter("acAmount1")!=""?Float.parseFloat(request.getParameter("acAmount1")):0.0f);
			
			tb.setTOURRPT(request.getParameter("acTrReport")!=null?request.getParameter("acTrReport"):"");
			flag=trh.addAcimprestDetails(tb);
			
			tb.setEMPNO(Integer.parseInt(request.getParameter("aceno")));
			tb.setTRCODE(Integer.parseInt(request.getParameter("actrcode")));
			tb.setACDATE(request.getParameter("acDetails2")!=null?request.getParameter("acDetails2"):"");
			tb.setACPARTI(request.getParameter("acPar2")!=null?request.getParameter("acPar2"):"");
			tb.setACREPTNO(request.getParameter("acReceipt2")!=null?request.getParameter("acReceipt2"):"");
			tb.setACPAYMNT(request.getParameter("acPayment2")!=""?Float.parseFloat(request.getParameter("acPayment2")):0.0f);
			tb.setACAMT(request.getParameter("acAmount2")!=""?Float.parseFloat(request.getParameter("acAmount2")):0.0f);
			
			tb.setTOURRPT(request.getParameter("acTrReport")!=null?request.getParameter("acTrReport"):"");
			flag=trh.addAcimprestDetails(tb);
			
			tb.setEMPNO(Integer.parseInt(request.getParameter("aceno")));
			tb.setTRCODE(Integer.parseInt(request.getParameter("actrcode")));
			tb.setACDATE(request.getParameter("acDetails3")!=null?request.getParameter("acDetails3"):"");
			tb.setACPARTI(request.getParameter("acPar3")!=null?request.getParameter("acPar3"):"");
			tb.setACREPTNO(request.getParameter("acReceipt3")!=null?request.getParameter("acReceipt3"):"");
			tb.setACPAYMNT(request.getParameter("acPayment3")!=""?Float.parseFloat(request.getParameter("acPayment3")):0.0f);
			tb.setACAMT(request.getParameter("acAmount3")!=""?Float.parseFloat(request.getParameter("acAmount3")):0.0f);
			
			tb.setTOURRPT(request.getParameter("acTrReport")!=null?request.getParameter("acTrReport"):"");
			flag=trh.addAcimprestDetails(tb);
			
			tb.setEMPNO(Integer.parseInt(request.getParameter("aceno")));
			tb.setTRCODE(Integer.parseInt(request.getParameter("actrcode")));
			tb.setACDATE(request.getParameter("acDetails4")!=null?request.getParameter("acDetails4"):"");
			tb.setACPARTI(request.getParameter("acPar4")!=null?request.getParameter("acPar4"):"");
			tb.setACREPTNO(request.getParameter("acReceipt4")!=null?request.getParameter("acReceipt4"):"");
			tb.setACPAYMNT(request.getParameter("acPayment4")!=""?Float.parseFloat(request.getParameter("acPayment4")):0.0f);
			tb.setACAMT(request.getParameter("acAmount4")!=""?Float.parseFloat(request.getParameter("acAmount4")):0.0f);
			
			tb.setTOURRPT(request.getParameter("acTrReport")!=null?request.getParameter("acTrReport"):"");
			
			flag=trh.addAcimprestDetails(tb);
			session.removeAttribute("tbean");
			if(flag){
			response.sendRedirect("TravelDetails.jsp");
			}
			
		}
	 	
	 	if(action.equalsIgnoreCase("UpdateJourneyDetails"))
		{
	
			System.out.println("in UpdateJourneyDetails method");
			boolean flag=false;	
			int srno = Integer.parseInt(request.getParameter("srno"));
			tb.setEMPNO(Integer.parseInt(request.getParameter("jreno")));
			tb.setTRCODE(Integer.parseInt(request.getParameter("jrtrcode")));
			tb.setDDATE(request.getParameter("jDate")!=null?request.getParameter("jDate"):"");
			tb.setDTMODE(Integer.parseInt(request.getParameter("jMode")));
			tb.setDFROM(request.getParameter("jFrom")!=null?request.getParameter("jFrom"):"");
			tb.setDTO(request.getParameter("jTo")!=null?request.getParameter("jTo"):"");
			tb.setDCLASS(Integer.parseInt(request.getParameter("jClass")));
			tb.setDFAIRDP(Integer.parseInt(request.getParameter("jFair")));
			tb.setDAMT(Float.parseFloat(request.getParameter("jAmount")));
			
			flag=trh.updateJourneyDetails(tb,srno);
			
			if(flag == true)
	    	{
	    		response.sendRedirect("EditTripInfo.jsp?action=close"); 
			}
			else
			{
				response.sendRedirect("EditTripInfo.jsp?action=keep");
			}
		}
	 	
	 	else if(action.equalsIgnoreCase("updateLocExpDetails"))
		{
	
			System.out.println("in updateLocExpDetails method");
			boolean flag=false;
			int srno = Integer.parseInt(request.getParameter("srno"));
			tb.setEMPNO(Integer.parseInt(request.getParameter("lceno")));
			tb.setTRCODE(Integer.parseInt(request.getParameter("lctrcode")));
			tb.setDDATE(request.getParameter("lcDate")!=null?request.getParameter("lcDate"):"");
			tb.setDTMODE(Integer.parseInt(request.getParameter("lcModeLc")));
			tb.setDFROM(request.getParameter("lcFrom")!=null?request.getParameter("lcFrom"):"");
			tb.setDTO(request.getParameter("lcTo")!=null?request.getParameter("lcTo"):"");
			tb.setDFAIRDP(Integer.parseInt(request.getParameter("lcDist")));
			tb.setDAMT(Float.parseFloat(request.getParameter("lcAmount")));
			
			flag=trh.updateLocExpDetails(tb, srno);
			if(flag){
				response.sendRedirect("TripInfo.jsp?action=close");
			}
			else
			{
				response.sendRedirect("TripInfo.jsp?action=keep");
			}
		}
	 	
	 	else if(action.equalsIgnoreCase("updateFoodExpDetails"))
		{
	
			System.out.println("in updateFoodExpDetails method");
			boolean flag=false;
			int srno = Integer.parseInt(request.getParameter("srno"));
			tb.setEMPNO(Integer.parseInt(request.getParameter("feeno")));
			tb.setTRCODE(Integer.parseInt(request.getParameter("fetrcode")));
			tb.setDDATE(request.getParameter("feDate")!=null?request.getParameter("feDate"):"");
			tb.setDFAIRDP(Integer.parseInt(request.getParameter("fePerDay")));
			tb.setNOFDAY(Integer.parseInt(request.getParameter("feNday")));
			tb.setDFROM(request.getParameter("feFrom")!=null?request.getParameter("feFrom"):"");
			tb.setDTO(request.getParameter("feTo")!=null?request.getParameter("feTo"):"");
			tb.setDAMT(Float.parseFloat(request.getParameter("feAmount")));
			
			flag = trh.updateFoodExpDetails(tb, srno);
			
			if(flag){
				response.sendRedirect("TripInfo.jsp?action=close");
			}
			else
			{
				response.sendRedirect("TripInfo.jsp?action=keep");
			}
		}
	 	
	 	else if(action.equalsIgnoreCase("updateOtherMiscExpDetails"))
		{
			boolean flag=false;
			int srno = Integer.parseInt(request.getParameter("srno"));
			tb.setEMPNO(Integer.parseInt(request.getParameter("omeeno")));
			tb.setTRCODE(Integer.parseInt(request.getParameter("ometrcode")));
			tb.setDDATE(request.getParameter("omeDate")!=null?request.getParameter("omeDate"):"");
			tb.setDBILLNO(request.getParameter("omeBillNo")!=null?request.getParameter("omeBillNo"):"");
			tb.setDPARTI(request.getParameter("omeParticulars")!=null?request.getParameter("omeParticulars"):"");
			tb.setDPNAME(request.getParameter("omePartyName")!=null?request.getParameter("omePartyName"):"");
			tb.setDAMT(Float.parseFloat(request.getParameter("omeAmount")));
			
			flag=trh.updateOtherMiscExpDetails(tb, srno);
			if(flag){
				response.sendRedirect("TripInfo.jsp?action=close");
			}
			else
			{
				response.sendRedirect("TripInfo.jsp?action=keep");
			}	
		}
	 	
	 	else if(action.equalsIgnoreCase("updateMiscExpDetails"))
		{
	
			System.out.println("in updateMiscExpDetails method");
			boolean flag=false;
			int srno = Integer.parseInt(request.getParameter("srno"));
			tb.setEMPNO(Integer.parseInt(request.getParameter("meeno")));
			tb.setTRCODE(Integer.parseInt(request.getParameter("metrcode")));
			tb.setDDATE(request.getParameter("miscDate")!=null?request.getParameter("miscDate"):"");
			tb.setDPARTI(request.getParameter("miscParticulars")!=null?request.getParameter("miscParticulars"):"");
			tb.setDLOCATION(request.getParameter("miscLocation")!=null?request.getParameter("miscLocation"):"");
			tb.setDAMT(Float.parseFloat(request.getParameter("miscAmount")));
			
			flag=trh.updateMiscExpDetails(tb,srno);
			if(flag){
				response.sendRedirect("TripInfo.jsp?action=close");
			}
			else
			{
				response.sendRedirect("TripInfo.jsp?action=keep");
			}
			
		}
	 	
	 	else if(action.equalsIgnoreCase("updateACImprestDetails"))
	 	{
	 		boolean flag=false;
	 		int trcode = Integer.parseInt(request.getParameter("idtrcode"));
	 		String acparti = request.getParameter("idParticulars");
			tb.setEMPNO(Integer.parseInt(request.getParameter("ideno")));
			tb.setTRCODE(Integer.parseInt(request.getParameter("idtrcode")));
			tb.setACDATE(request.getParameter("iddate")!=null?request.getParameter("iddate"):"");
			tb.setACPARTI(request.getParameter("idParticulars")!=null?request.getParameter("idParticulars"):"");
			tb.setACREPTNO(request.getParameter("idreceipt")!=null?request.getParameter("idreceipt"):"");
			tb.setACPAYMNT(request.getParameter("idpayment")!=null?Float.parseFloat(request.getParameter("idpayment")):0);
			tb.setACAMT(request.getParameter("idamount")!=null?Float.parseFloat(request.getParameter("idamount")):0);
			
			flag = trh.updateACImprestDetails(tb, trcode, acparti);
			if(flag){
				response.sendRedirect("TripInfo.jsp?action=close");
			}
			else
			{
				response.sendRedirect("TripInfo.jsp?action=keep");
			}	
	 	}
	 	if(action.equalsIgnoreCase("settlement")){
	 		
	 		if(session != null){
	 			
	 			try{
	 				Connection con = ConnectionManager.getConnection();
	 				Statement st = con.createStatement();	 		 		
	 		 		int trcode = Integer.parseInt(request.getParameter("trcode"));
	 		 		
	 		 		st.executeUpdate("UPDATE TRACCOUNT SET CHEKSTATE=41 WHERE TRCODE="+trcode);
	 		 		//st.executeUpdate("UPDATE TRDETAILS SET CHEKSTATE=41 WHERE TRCODE="+trcode);
	 		 		out.write("true");
	 		 		con.close();
	 			}
	 			catch(Exception e){
	 				e.printStackTrace();
	 				out.write("false");
	 			}
	 		}
	 	}
	}
}


