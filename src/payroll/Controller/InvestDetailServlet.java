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
import payroll.DAO.TranHandler;
import payroll.Model.TranBean;
import payroll.Model.TransactionBean;

/**
 * Servlet implementation class InvestDetailServlet
 */
@WebServlet("/InvestDetailServlet")
public class InvestDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InvestDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session= request.getSession();	
		TransactionBean trbn = new TransactionBean();
		TranBean tb = new TranBean();
		EmpOffHandler emp = new EmpOffHandler();
	    TranHandler trn = new TranHandler();
	    ArrayList<TranBean> trlist = new ArrayList<TranBean>();
	    String[] key= request.getParameter("str").split(":");
		String EMPNO = key[0];
		int srno = Integer.parseInt(key[1]);
		trn.DeleteInvestTran(Integer.parseInt(EMPNO), srno);
	    trbn=emp.getInfoEmpTran(EMPNO);
	    trlist=trn.getTranInfo(EMPNO,"invtran");
		request.setAttribute("trbn",trbn); 
		request.setAttribute("empno1",EMPNO);
		session.setAttribute("trlist",trlist);
		request.getRequestDispatcher("investDetails.jsp?action=getdata").forward(request, response);
	}	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		HttpSession session= request.getSession();	
		String action = request.getParameter("action")==null?"":request.getParameter("action");
		TransactionBean trbn = new TransactionBean();
		TranBean tb = new TranBean(); 
		EmpOffHandler emp = new EmpOffHandler();
	    TranHandler trn = new TranHandler();
	    String EMPNO = "";
	    ArrayList<TranBean> trlist = new ArrayList<TranBean>();
		if(action.equalsIgnoreCase("add"))
		{
			tb.setEMPNO(Integer.parseInt(request.getParameter("empno2")));
			EMPNO = request.getParameter("empno2");
			tb.setTRNCD(Integer.parseInt(request.getParameter("trancd")));
			tb.setTRNDT(request.getParameter("trndate"));
			tb.setINP_AMT(Integer.parseInt(request.getParameter("invstamnt")));
			trn.addInvestTran(tb);
		}
		else
		{
			EMPNO = request.getParameter("EMPNO");
			StringTokenizer st = new StringTokenizer(EMPNO,":");
			while(st.hasMoreTokens())
			{
				EMPNO=st.nextToken();  
			}
		}
	    trbn = emp.getInfoEmpTran(EMPNO);
	    trlist = trn.getTranInfo(EMPNO,"invtran");//invtran :-fire query to invtran table
	    request.setAttribute("trbn",trbn); 
	    request.setAttribute("empno1",EMPNO); 
	    session.setAttribute("trlist",trlist);
	    request.getRequestDispatcher("investDetails.jsp?action=getdata").forward(request, response);
	}
}