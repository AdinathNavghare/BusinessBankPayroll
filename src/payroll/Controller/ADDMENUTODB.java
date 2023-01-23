package payroll.Controller;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import payroll.DAO.menuDAO;
import payroll.Model.menuBean;

/**
 * Servlet implementation class ADDMENUTODB
 * @auther : Vijay Malusare
 * @created : 07-July-2015
 * 
 * 
 */
@WebServlet(name = "ADDMENUTODatabese", urlPatterns = { "/ADDMENUTODatabese" })
public class ADDMENUTODB extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ADDMENUTODB() {
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

		
		String action=request.getParameter("action");
		
		menuDAO md=new menuDAO();
		if(action.equalsIgnoreCase("addmenu"))
		{
			String mstr=request.getParameter("main");
			String menu_name=request.getParameter("dname");
			String link=request.getParameter("linkname");
			String status=request.getParameter("status");
			
			
			menuBean mb=new menuBean();
			
			mb.setMENU_NAME(menu_name);
			mb.setMENU_URL(link);
			mb.setMENU_PARENT_ID(Integer.parseInt(mstr));
			mb.setMENU_TYPE(link.equalsIgnoreCase("#")?"menu":"smenu");
			mb.setMENU_STATUS(status);
			
			boolean flag=md.addMenuinDB(mb);
			if(flag)
			{
				response.sendRedirect("ADDMENUTODB.jsp?flag=1");
			}
			else
			{
				response.sendRedirect("ADDMENUTODB.jsp?flag=4");
			}
			
			
		}
		
		
		
		if(action.equalsIgnoreCase("updatemenu"))
		{
			String mstr=request.getParameter("main1");
			String status=request.getParameter("status1");
			
			
			menuBean mb=new menuBean();
			
			mb.setMENU_NAME(mstr);
			mb.setMENU_STATUS(status);
			
			boolean flag=md.updateMenuinDB(mb);
			if(flag)
			{
				response.sendRedirect("ADDMENUTODB.jsp?flag=2");
			}
			else
			{
				response.sendRedirect("ADDMENUTODB.jsp?flag=4");
			}
			
			
		}
		
		
	}

}
