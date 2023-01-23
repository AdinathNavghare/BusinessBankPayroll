package payroll.Controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.DAO.QuarterChallanHandler;
import payroll.Model.Quarter_ChallanBean;

/**
 * Servlet implementation class QuarterChallanServlet
 */
@WebServlet("/QuarterChallanServlet")
public class QuarterChallanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuarterChallanServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		System.out.println("in controller");
		
			HttpSession session = request.getSession();
			String action = request.getParameter("action");
			
			if (action.equalsIgnoreCase("adddetail")){
			
			String action1 = request.getParameter("selecteddetail");

			System.out.println("action is   :  "+action);
			System.out.println("action1  is  : "+action1);
			
			if (action1.equalsIgnoreCase("1")){
				System.out.println("For Add Quarter");
				QuarterChallanHandler qchandler=new QuarterChallanHandler();
				Quarter_ChallanBean qcbean=new Quarter_ChallanBean();
				
				qcbean.setFinancial_Year(request.getParameter("Financial_Year")== null ?"":request.getParameter("Financial_Year"));
				qcbean.setQuarter_No(Integer.parseInt(request.getParameter("quar")==null ? "":request.getParameter("quar")));

				System.out.println(Integer.parseInt(request.getParameter("quar")));
								
				qcbean.setQuarter_Reciept_No(request.getParameter("rno")==null?"":request.getParameter("rno"));
				qcbean.setQuarter_Challan_Entered_By(request.getParameter("cname")==null?"":request.getParameter("cname"));
				
				System.out.println(request.getParameter("Financial_Year"));
				
				String quarnumber = qchandler.insertQcDetail(qcbean);
				
				if (quarnumber.equalsIgnoreCase("save")) {
					response.sendRedirect("Quarter_ChallanDetails.jsp?flag=1");
				} else {
					response.sendRedirect("Quarter_ChallanDetails.jsp?flag=2");
				}
							
			}
			else if(action1.equalsIgnoreCase("2")){
				
				QuarterChallanHandler qchandler=new QuarterChallanHandler();
				Quarter_ChallanBean qcbean=new Quarter_ChallanBean();

				qcbean.setYear(request.getParameter("year")== null ?"":request.getParameter("year"));
				qcbean.setFor_Month("01-"+(request.getParameter("month")==null ? "":request.getParameter("month")));
				qcbean.setChallan_No(Integer.parseInt(request.getParameter("cno")==null?"":request.getParameter("cno")));
				qcbean.setDate_Of_Payment(request.getParameter("dateofpay")==null?"":request.getParameter("dateofpay"));
				qcbean.setDue_Date_Payment(request.getParameter("duedateofpay")==null?"":request.getParameter("duedateofpay"));
				
				System.out.println("01-"+request.getParameter("month"));
				//System.out.println(request.getParameter("month")+"-01");
				
				try {
					String chalnumber = qchandler.insertChallanDetail(qcbean);
					
					if (chalnumber.equalsIgnoreCase("save")) {
						response.sendRedirect("Quarter_ChallanDetails.jsp?flag=1");
					} else {
						response.sendRedirect("Quarter_ChallanDetails.jsp?flag=2");
					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	

			}
			else if (action1.equalsIgnoreCase("3")){
				System.out.println("For TDS_Payment_Details");
				QuarterChallanHandler qchandler=new QuarterChallanHandler();
				Quarter_ChallanBean qcbean=new Quarter_ChallanBean();
				qcbean.setBSR_Code(request.getParameter("branchcd")== null ?"":request.getParameter("branchcd"));
				qcbean.setBranch_name(request.getParameter("branchname")== null?"":request.getParameter("branchname"));
				qcbean.setYear(request.getParameter("year")== null ?"":request.getParameter("year"));
				
				String quarnumber = qchandler.insert_Tds_Payment_Details(qcbean);
				if (quarnumber.equalsIgnoreCase("save")) {
					response.sendRedirect("Quarter_ChallanDetails.jsp?flag=1");
				} else {
					response.sendRedirect("Quarter_ChallanDetails.jsp?flag=2");
				}
			}
			
		}

	}
}


