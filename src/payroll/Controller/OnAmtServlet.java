package payroll.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.DAO.OnAmtHandler;
import payroll.Model.OnAmtBean;

/**
 * Servlet implementation class OnAmtServlet
 */
@WebServlet("/OnAmtServlet")
public class OnAmtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OnAmtServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession(true);
		PrintWriter out=response.getWriter();
		String action=request.getParameter("action");
		if(action.equalsIgnoreCase("addnew"))
		{
			OnAmtHandler OAH=new OnAmtHandler();
			OnAmtBean OAB=new OnAmtBean();
			boolean flag = false;
			String selectEmp1=request.getParameter("empcat");
			String selectTrn=request.getParameter("select.trncd");
			String page = request.getParameter("pageName");
			String empList = request.getParameter("emplist");
			OAB.setTRNCD(Integer.parseInt(selectTrn));
			OAB.setSRNO(Integer.parseInt(request.getParameter("maxsr")));
			OAB.setONAMTCD(Integer.parseInt(request.getParameter("select.addTrncd")));
			OAB.setAMT_TYPE(request.getParameter("amtType"));
			if(empList.equals("")){
				String selectEmp=request.getParameter("empcat");
				String[] employ= selectEmp.split(":");
				selectEmp = employ[1];
				OAB.setEMP_CAT(Integer.parseInt(selectEmp));
				flag=OAH.addOnAmt(OAB);
				if(flag) {
					//session.setAttribute("emptype", selectEmp1);
					request.getRequestDispatcher(page+"?action=getList&ecat="+selectEmp1+"&trncd="+selectTrn).forward(request, response);
				} else {
					out.write("Error in saving record");
				}
			} else {
				flag=OAH.addOnAmt2(OAB, empList);
				if(flag) {
					String []eno = empList.split(",");
					request.getRequestDispatcher(page+"?action=getList2&elist="+eno[0]+"&trncd="+selectTrn).forward(request, response);
				} else {
					out.write("Error in saving record");
				}
			}	
		}
		else if(action.equalsIgnoreCase("del"))
		{
			String key=request.getParameter("key");
			String[] splitKey=key.split("-");	// [0] Ecat, [1] Trcode, [2] Srno all Integers
			OnAmtHandler OAH=new OnAmtHandler();
			int E=Integer.parseInt(splitKey[0]);
			int T=Integer.parseInt(splitKey[1]);
			int S=Integer.parseInt(splitKey[2]);
			boolean flag=OAH.deleteOnAmt(E, T, S);
			
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
	}
}