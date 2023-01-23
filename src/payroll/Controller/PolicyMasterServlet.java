package payroll.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.DAO.EmpOffHandler;
import payroll.DAO.PolicyHandler;
import payroll.Model.PolicyBean;
import payroll.Model.TranBean;
import payroll.Model.TransactionBean;

/**
 * Servlet implementation class PolicyMasterServlet
 */
@WebServlet("/PolicyMasterServlet")
public class PolicyMasterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PolicyMasterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session= request.getSession();	
		String EMPNO = request.getParameter("no");
		EmpOffHandler emp = new EmpOffHandler();
		TransactionBean trbn = new TransactionBean();
		PolicyHandler ph = new PolicyHandler();
		ArrayList<PolicyBean> policylist = new ArrayList<PolicyBean>();
		trbn=emp.getInfoEmpTran(EMPNO);
		policylist = ph.getPolicyInfo(Integer.parseInt(EMPNO));
		request.setAttribute("empinfobean",trbn); 
		session.setAttribute("policylist",policylist); 
		request.getRequestDispatcher("policy.jsp?action=getdata").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session= request.getSession();	
		String action = request.getParameter("action")==null?"":request.getParameter("action");
		TransactionBean trbn = new TransactionBean();
		EmpOffHandler emp = new EmpOffHandler();
	    PolicyHandler ph = new PolicyHandler();
	    if(action.equalsIgnoreCase("policyinfo"))
	    {
	    	String EMPNO=request.getParameter("EMPNO");
			ArrayList<PolicyBean> policylist = new ArrayList<PolicyBean>();
			StringTokenizer st = new StringTokenizer(EMPNO,":");
		    while(st.hasMoreTokens())
		    {
		    	EMPNO=st.nextToken(); 
		    }
		    trbn=emp.getInfoEmpTran(EMPNO);
		    policylist = ph.getPolicyInfo(Integer.parseInt(EMPNO));
		    request.setAttribute("empinfobean",trbn); 
		    session.setAttribute("policylist",policylist); 
		    request.getRequestDispatcher("policy.jsp?action=getdata").forward(request, response);
	    }
	    if(action.equalsIgnoreCase("addnewPolicy"))
	    {
	    	PolicyBean pb = new PolicyBean();
	    	int flag =0;
	    	pb.setEMPNO(Integer.parseInt(request.getParameter("empno")));
	    	pb.setPOLICY_NAME(request.getParameter("polyname"));
	    	pb.setPOLICY_TYPE(request.getParameter("polytype"));
	    	pb.setPOLICY_AMOUNT(Long.parseLong(request.getParameter("polyamnt")==null?"0":request.getParameter("polyamnt")));
	    	pb.setPOLICY_DATE(request.getParameter("polydate"));
	    	pb.setPREMIUM_AMOUNT(Long.parseLong(request.getParameter("preamnt")==null?"0":request.getParameter("preamnt")));
	    	pb.setPREMIUM_STATUS(request.getParameter("prestatus"));
	    	pb.setMATURITY_AMOUNT(Long.parseLong(request.getParameter("matamnt")==null?"0":request.getParameter("matamnt")));
	    	pb.setMATURITY_DATE(request.getParameter("matdate"));
	    	pb.setDEDUCTION_MONTH(Integer.parseInt(request.getParameter("dedmonth")==null?"0":request.getParameter("dedmonth")));
	    	pb.setINS_COMP_NAME(request.getParameter("compname"));
	    	flag = ph.insertPolicy(pb);
	    	System.out.println(flag);
	    	if(flag == 1)
	    	{
	    		response.sendRedirect("addNewPolicy.jsp?action=close"); 
			}
			else
			{
				response.sendRedirect("addNewPolicy.jsp?action=keep");
			}
	    }
	    if(action.equalsIgnoreCase("updatepolicy"))
	    {
	    	boolean  flag =false;
	    	PolicyBean pb = new PolicyBean();
	    	pb.setEMPNO(Integer.parseInt(request.getParameter("empno")));
	    	pb.setPOLICY_NAME(request.getParameter("polyname"));
	    	pb.setPOLICY_TYPE(request.getParameter("polytype"));
	    	pb.setPOLICY_AMOUNT(Long.parseLong(request.getParameter("polyamnt")==null?"0":request.getParameter("polyamnt")));
	    	pb.setPOLICY_DATE(request.getParameter("polydate"));
	    	pb.setPREMIUM_AMOUNT(Long.parseLong(request.getParameter("preamnt")==null?"0":request.getParameter("preamnt")));
	    	pb.setPREMIUM_STATUS(request.getParameter("prestatus"));
	    	pb.setMATURITY_AMOUNT(Long.parseLong(request.getParameter("matamnt")==null?"0":request.getParameter("matamnt")));
	    	pb.setMATURITY_DATE(request.getParameter("matdate"));
	    	pb.setDEDUCTION_MONTH(Integer.parseInt(request.getParameter("dedmonth")==null?"0":request.getParameter("dedmonth")));
	    	pb.setINS_COMP_NAME(request.getParameter("compname"));
	    	pb.setSRNO(Integer.parseInt(request.getParameter("srno")));
	    	flag=ph.updatePolicy(pb);
	    	if(flag)
	    	{
	    		response.sendRedirect("updatepolicy.jsp?action=close");
	    	}
	    	else
	    	{
	    		response.sendRedirect("updatepolicy.jsp?action=keep");	
	    	}
	    }
	}
}