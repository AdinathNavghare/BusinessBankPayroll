package payroll.Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import payroll.DAO.ConnectionManager;
import payroll.DAO.menuDAO;
import payroll.Model.menuBean;

/**
 * Servlet implementation class Dmenu
 */
@WebServlet("/Dmenu")
public class Dmenu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dmenu() {
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
		
			System.out.println("In servlet");
			String uname = request.getParameter("username");
			String pass = request.getParameter("pass");
			boolean flag = false;
			int UID = 0;
			try
			{
				menuDAO m = new menuDAO();
				ArrayList<menuBean> menuList = new ArrayList<menuBean>();
				//HashMap<Integer,String> usrRightMap = new HashMap<Integer, String>();
				Connection con =  ConnectionManager.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("Select * from USRMAST where UNAME='"+uname+"' and UPWD='"+pass+"'");
				if(rs.next())
				{
					System.out.println(rs.getInt("USERID"));
					UID = rs.getInt("USERID");
					menuList = m.getUserMenu(UID);
					//usrRightMap = m.GetUserRights(UID);
					HttpSession session = request.getSession();
					//session.setAttribute("usrRightMap", usrRightMap);
					session.setAttribute("name", uname);
					session.setAttribute("UID", UID);
					//session.setAttribute("mList", menuList);
					String result = getMenuList(menuList);
					session.setAttribute("myMenu", result);
					response.sendRedirect("index.jsp");
					//response.sendRedirect("index2.jsp");
				}
				else
				{
					response.sendRedirect("index.jsp?action=0");
				}				
			}
			catch(Exception e)
			{
					e.printStackTrace();
			}
	}
	public static String getMenuList(ArrayList<menuBean> menu)
	{
		String result="<ul class=\"select\">";	
		for(menuBean m:menu)
		{
			if(m.getMENU_PARENT_ID()==0)
			{
				result+="<li><a href=\""+m.getMENU_URL()+"\"><b>"+m.getMENU_NAME()+"</b></a>";
				result+=getSubMenus(menu,m.getMENUID());
			}
		}
		result+="</ul>";
		return result;	
	}
	public static String getSubMenus(ArrayList<menuBean> menu,int MID)
	{
		String result="<div class=\"select_sub\"><ul class=\"sub\">";	
		for(menuBean m:menu)
		{
			if(m.getMENU_PARENT_ID()==MID)
			{
				result+="<li><a href=\""+m.getMENU_URL()+"\" >"+m.getMENU_NAME()+"</a></li>";
			}
		}
		result+="</ul></div></li>";
		return result;
	}
}