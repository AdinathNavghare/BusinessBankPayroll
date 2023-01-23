package payroll.Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import payroll.DAO.EditNameHandler;
import payroll.Model.EditNameBean;

/**
 * Servlet implementation class EditNameServlet
 */
@WebServlet("/EditNameServlet")
public class EditNameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public EditNameServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session= request.getSession();	
		String action = request.getParameter("action")==null?"":request.getParameter("action");
		if(action.equalsIgnoreCase("editName"))
		{

			System.out.println("now in editname serv");
			boolean flag = false;
			EditNameHandler enh = new EditNameHandler();
			EditNameBean enb = new EditNameBean();
			enb.setCHANGEDATE(request.getParameter("cdate"));
			enb.setEMPNO(Integer.parseInt(request.getParameter("EMPNO")));
			enb.setOLDNAME(request.getParameter("EMPNAME")==null?"":request.getParameter("EMPNAME"));
			enb.setFNAME(request.getParameter("Fname")==null?"":request.getParameter("Fname"));
			enb.setMNAME(request.getParameter("Mname")==null?"":request.getParameter("Mname"));
			enb.setLNAME(request.getParameter("Lname")==null?"":request.getParameter("Lname"));
			String uid=session.getAttribute("UID").toString();
			String user = session.getAttribute("name").toString();
			String empno = session.getAttribute("EMPNO").toString();
			String updatename = empno+"-"+uid+"-"+user;
			//System.out.println(updatename);
			enb.setUPDATEBY(updatename);
			enb.setSALUTE(Integer.parseInt(request.getParameter("salute")));
			flag = enh.editName(enb);
			if(flag)
			{
			    response.sendRedirect("editName.jsp?action=close"); 
				System.out.println("query executed successfully");
			}
			else 
			{
				response.sendRedirect("editName.jsp?action=keep");
				System.out.println("error in record inserting");	
			}
		}
	}
}