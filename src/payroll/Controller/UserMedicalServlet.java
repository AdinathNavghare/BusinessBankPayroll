package payroll.Controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import payroll.DAO.UserMedicalHandler;
import payroll.Model.UserMedicalBean;

/**
 * Servlet implementation class UserMedicalServlet
 */
@WebServlet({ "/UserMedicalServlet", "/medical" })
public class UserMedicalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserMedicalServlet() {
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
		// TODO Auto-generated method stub
		
		String action=request.getParameter("action")!=null?request.getParameter("action"):"";
		UserMedicalHandler medihandler=new UserMedicalHandler();
		ArrayList<UserMedicalBean> addlist=new ArrayList<UserMedicalBean>();
		if(action.equalsIgnoreCase("addbill"))
		{
			UserMedicalBean mbean=new UserMedicalBean();
			boolean flag=false;
			mbean.setEMPNO(request.getParameter("EMPNO")!=null?Integer.parseInt(request.getParameter("EMPNO")):0);
			mbean.setRELATION(request.getParameter("relation")!=null?request.getParameter("relation"):"");
			mbean.setTRANDATE(request.getParameter("trandate")!=null?request.getParameter("trandate"):"");
			mbean.setFROMDATE(request.getParameter("frmdate")!=null?request.getParameter("frmdate"):"");
			mbean.setAMOUNT(request.getParameter("amount")!=null?Integer.parseInt(request.getParameter("amount")):0);
			mbean.setTODATE(request.getParameter("todate")!=null?request.getParameter("todate"):"");
			mbean.setDESCRIPTION(request.getParameter("desc")!=null?request.getParameter("desc"):"");
			flag=medihandler.addMedicalbill(mbean);
			addlist=medihandler.getMedicalList(mbean);
			HttpSession session =request.getSession();
			session.setAttribute("medicallist", addlist);
			if(flag)
			{
				response.sendRedirect("userMedical.jsp?action=showaddbill");
			}
			else
			{
				response.sendRedirect("userMedical.jsp?action=error");
			}
		}
	}

}
