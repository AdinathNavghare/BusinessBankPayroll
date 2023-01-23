package payroll.Controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import payroll.DAO.UserMasterHandler;
import payroll.Model.UserMasterBean;

/**
 * Servlet implementation class UserMasterServlet
 */
@WebServlet({ "/UserMasterServlet", "/user" })
public class UserMasterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserMasterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getParameter("action")!=null?request.getParameter("action"):"";
		if(action.equalsIgnoreCase("display")|| action.equalsIgnoreCase("cancel") )
		{
		ArrayList<UserMasterBean> user= new ArrayList<UserMasterBean>();
	
		user= UserMasterHandler.display();
	    HttpSession session = request.getSession(true);
	    session.setAttribute("currentUser",user);
		RequestDispatcher rd = request.getRequestDispatcher("userMaintain.jsp");
		rd.forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	  	String action=request.getParameter("action")!=null?request.getParameter("action"):"";
		
		if(action.equalsIgnoreCase("add"))
		{
			UserMasterBean umb = new UserMasterBean();
			ArrayList<UserMasterBean> user= new ArrayList<UserMasterBean>();
			UserMasterHandler usemast=new UserMasterHandler();
			umb.setUsercode(request.getParameter("usercode")!=null?request.getParameter("usercode"):"");
			umb.setUsername(request.getParameter("username")!=null?request.getParameter("username"):"");
		    umb.setUserpwd(request.getParameter("password")!=null?request.getParameter("password"):"");
		    umb.setStatus(request.getParameter("status")!=null?Integer.parseInt(request.getParameter("status")):0);
			boolean add=usemast.addMaster(umb);
			user=UserMasterHandler.display();
		    if(add)
			{  
		        HttpSession session = request.getSession(true);
		 	    session.setAttribute("currentUser",user);
		    	RequestDispatcher rd = request.getRequestDispatcher("userMaintain.jsp");
				rd.forward(request, response);
			}
			else
			{
				response.sendRedirect("userMaintain.jsp");
			}
		}
		if(action.equalsIgnoreCase("update"))
		{
			UserMasterBean umb1 = new UserMasterBean();
			ArrayList<UserMasterBean> user= new ArrayList<UserMasterBean>();
			UserMasterHandler usemast=new UserMasterHandler();
			umb1.setUsercode(request.getParameter("usercode")!=null?request.getParameter("usercode"):"");
			umb1.setUsername(request.getParameter("username")!=null?request.getParameter("username"):"");
			umb1.setUserpwd(request.getParameter("password")!=null?request.getParameter("password"):"");
			umb1.setStatus(request.getParameter("status")!=null?Integer.parseInt(request.getParameter("status")):0);
			 boolean up=usemast.updateMaster(umb1);
		    if(up)
		    {
		    	user=UserMasterHandler.display();
		    	HttpSession session = request.getSession(true);
			 	session.setAttribute("currentUser",user);
			 	RequestDispatcher rd = request.getRequestDispatcher("userMaintain.jsp?");
				rd.forward(request, response);
			}
		    else
		    {
		    	response.sendRedirect("userMaintain.jsp");
			}
		  }
	}

}
