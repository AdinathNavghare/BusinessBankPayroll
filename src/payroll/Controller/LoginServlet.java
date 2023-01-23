package payroll.Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.DAO.ConnectionManager;
import payroll.DAO.UsrHandler;
import payroll.DAO.menuDAO;
import payroll.Model.UserMasterBean;
import payroll.Model.menuBean;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet({ "/LoginServlet" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
    public LoginServlet() {
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
		String action = request.getParameter("action")==null?"":request.getParameter("action");
		UsrHandler usrHandler = new UsrHandler();
		if(action.equalsIgnoreCase("login")){
			String uname = request.getParameter("username");
			String pass = request.getParameter("pass");
			
		
		
		int UID = 0;
			try
			{
				menuDAO m = new menuDAO();
				ArrayList<menuBean> menuList = new ArrayList<menuBean>();
				Connection con =  ConnectionManager.getConnectionTech();
				//Statement st = con.createStatement();
				
				pass = usrHandler.encryptBASE64(pass.getBytes("UTF-8")).trim();
				
				//ResultSet rs = st.executeQuery("Select * from Users where HR_Status = 'Active' AND User_Name='"+uname+"' and User_Password='"+pass+"' ");
				String sql = "SELECT * FROM Users where HR_Status='Active' and User_Name=? AND User_Password=?";
				PreparedStatement st = con.prepareStatement(sql);
				st.setString(1, uname);
				st.setString(2, pass);
				ResultSet rs = st.executeQuery();
				
				if(rs.next())
				{
					int empid = rs.getInt("Emp_ID");
					UID = rs.getInt("User_ID");
					menuList = m.getUserMenu(UID);
					HttpSession session = request.getSession();
					session.setMaxInactiveInterval(1*60*60);
					session.setAttribute("name", uname);
					session.setAttribute("EMPNO", empid);
					session.setAttribute("UID",UID);
					UsrHandler UH = new UsrHandler();
					UH.addUserLog(String.valueOf(UID), session.getId());
					String result = getMenuList(menuList);
					session.setAttribute("myMenu", result);
					ArrayList<String> urls = new ArrayList<String>();
					for(menuBean M:menuList)
					{
						urls.add(M.getMENU_URL());
					}
					session.setAttribute("urls", urls);
					response.sendRedirect("index.jsp");
				}
				else
				{
					response.sendRedirect("login.jsp?action=2");	//	Wrong Username Or Password
				}
				con.close();
			}
			catch(Exception e)
			{
					e.printStackTrace();
			}
		}
		
		if(action.equalsIgnoreCase("forgotpwd")){
			String usrname = request.getParameter("UserName");
			//String email = request.getParameter("emailId");
			String empname = request.getParameter("EMPNO");
			String EMPNO ="";
			if(empname.equals(""))
			{
				 EMPNO ="";
			}
			else {
				String EMPNO1[] = empname.split(":");
				EMPNO =  EMPNO1[2];
			}
			
			String status = null;
			UserMasterBean userBean = new UserMasterBean();
			//userBean = usrHandler.updatePwdOnForgot(usrname, email);
			userBean = usrHandler.newUpdatePwdOnForgot(usrname, EMPNO);
			
			if(userBean.isIsValid()){
				status = usrHandler.sendEmailForgotPwd(userBean);	
			}
			response.sendRedirect("forgotpwd.jsp?status="+status);
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
				result+="<li><a href=\""+m.getMENU_URL()+"\" >"+m.getMENU_NAME()+"</a>";
				result+=getSub_subMenus	(menu,m.getMENUID());
			}
		}
		result+="</ul></div></li>";
		return result;
	}
	
	public static String getSub_subMenus(ArrayList<menuBean> menu,int MID)
	{
		String result="<div class=\"select_sub_sub\"><ul class=\"sub_sub\">";
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