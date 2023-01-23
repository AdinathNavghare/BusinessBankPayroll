package payroll.Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import payroll.Core.ReportDAO;
import payroll.DAO.BillHandler;
import payroll.Model.BillBean;

@WebServlet("/BillServlet")
public class BillServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BillServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	try
	{
		String action = request.getParameter("action")==null?"":request.getParameter("action");
		HttpSession session = request.getSession();
		int userEno = (Integer)session.getAttribute("EMPNO");
		BillBean billBean = new BillBean();
		BillHandler billHandler = new BillHandler();
		
		if(action.equalsIgnoreCase("deleteDetails"))
		{
			int billid = Integer.parseInt(request.getParameter("billid"));
			int empno = Integer.parseInt(request.getParameter("empno"));
			int trncd = Integer.parseInt(request.getParameter("trncd")==null?"0":request.getParameter("trncd"));
			boolean check = billHandler.deleteBillDetals(billid,empno,userEno);
			if(check){
				response.sendRedirect("addNewBill.jsp?action=1&empno="+empno+"&trncd="+billBean.getTrncd()+"&amt="+billBean.getAmount());		
			}
			else{
				response.sendRedirect("addNewBill.jsp?action=0&empno="+empno+"&trncd="+trncd);
			}
		}
				
	}catch(Exception e){e.printStackTrace();}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
		BillBean billBean = new BillBean();
		BillHandler billHandler=new BillHandler();
		HttpSession session = request.getSession();
		boolean result = false;
		String action = request.getParameter("action")==null?"":request.getParameter("action");
		int userEno = (Integer)session.getAttribute("EMPNO");

		if(action.equalsIgnoreCase("add"))
		{
			billBean.setEmpNo(Integer.parseInt(request.getParameter("empNo")));
			billBean.setTrncd(Integer.parseInt(request.getParameter("trncd")));
			billBean.setBill_tel_no(Integer.parseInt(request.getParameter("billNo")));
			billBean.setForWhom(Integer.parseInt(request.getParameter("forWhom")==null?"0":request.getParameter("forWhom")));
			billBean.setFrom_Date(request.getParameter("fromDate"));
			billBean.setTo_Date(request.getParameter("toDate"));
			billBean.setAmount(Float.parseFloat(request.getParameter("amount")));
			billBean.setCreatedBy(userEno);
			billBean.setCreatedOn(ReportDAO.getSysDate());
			
			result = billHandler.addBillDetails(billBean);
			if(result)
				response.sendRedirect("addNewBill.jsp?action=2&empno="+billBean.getEmpNo()+"&trncd="+billBean.getTrncd()+"&amt="+billBean.getAmount());
			else
				response.sendRedirect("addNewBill.jsp?action=0&empno="+billBean.getEmpNo()+"&trncd="+billBean.getTrncd()+"&amt="+billBean.getAmount());			
		
		}
		else if(action.equalsIgnoreCase("updateDetails"))
		{

			System.out.println("in bill servlet  and action is updateDetails");
		/*	int billid = Integer.parseInt(request.getParameter("billid"));
			int empno = Integer.parseInt(request.getParameter("empNo"));
			int trncd = Integer.parseInt(request.getParameter("trncd")==null?"0":request.getParameter("trncd"));*/
			
			billBean.setBillId(Integer.parseInt(request.getParameter("billid")));
			System.out.println("billid"   +request.getParameter("billid"));
			
			billBean.setEmpNo(Integer.parseInt(request.getParameter("empNo")));
			billBean.setTrncd(Integer.parseInt(request.getParameter("trncd")));
			billBean.setBill_tel_no(Integer.parseInt(request.getParameter("billNo")));
			billBean.setForWhom(Integer.parseInt(request.getParameter("forWhom")==null?"0":request.getParameter("forWhom")));
			billBean.setFrom_Date(request.getParameter("fromDate"));
			billBean.setTo_Date(request.getParameter("toDate"));
			
			System.out.println("Ammount"   +request.getParameter("amount"));
			billBean.setAmount(Float.parseFloat(request.getParameter("amount")));
			billBean.setCreatedBy(userEno);
			billBean.setCreatedOn(ReportDAO.getSysDate());
			
			
			
			boolean check = billHandler.updateDetails(billBean);
			
			if(check){
				response.sendRedirect("addNewBill.jsp?action=3&empno="+billBean.getEmpNo()+"&trncd="+billBean.getTrncd()+"&amt="+billBean.getAmount());		
			}
			else{
				response.sendRedirect("addNewBill.jsp?action=0&empno="+billBean.getEmpNo()+"&trncd="+billBean.getTrncd()+"&amt="+billBean.getAmount());
			}
		}

	}catch(Exception e){e.printStackTrace();}

	}	
}
