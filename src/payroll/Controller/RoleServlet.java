package payroll.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import payroll.DAO.RoleDAO;
import payroll.Model.RoleBean;

/**
 * Servlet implementation class RoleServlet
 */
@WebServlet("/RoleServlet")
public class RoleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RoleServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action") != null ? request
				.getParameter("action") : "";
		RoleDAO rlDAO = new RoleDAO();
	    
		if (action.equals("GetAllRole")) {
			ArrayList<RoleBean> arrList = new ArrayList<RoleBean>();
			arrList = rlDAO.getAllRole();
			request.setAttribute("roleList", arrList);
			RequestDispatcher rd = request
					.getRequestDispatcher("rolesManagement.jsp?action=showRole");
			rd.forward(request, response);

		}
		if(action.equalsIgnoreCase("showfirst"))
		{
			 ArrayList<RoleBean> rollist=new ArrayList<RoleBean>();
			 rollist=rlDAO.getrolelist();
			 HttpSession session=request.getSession();
			 session.setAttribute("UroleList", rollist);
			 response.sendRedirect("addUserRole.jsp?action=showroleList");
			
		}
		
		if(action.equalsIgnoreCase("sort_role"))
		{
			 ArrayList<RoleBean> rollist=new ArrayList<RoleBean>();
			 rollist=rlDAO.getrolelist();
			 HttpSession session=request.getSession();
			// String sort_rname=request.getParameter("sort_rname")==null?"":request.getParameter("sort_rname");
			 String username=request.getParameter("username")==null?"":request.getParameter("username");
			// System.out.println("/////"+request.getParameter("sort_rname"));
			// System.out.println("*****"+request.getParameter("username"));
			 
			 ArrayList<RoleBean> tran_sort = new ArrayList<RoleBean>();
			   
			 String rolename;
			 int i=0;
			 RoleDAO roledao=new RoleDAO();
			 
			 for (RoleBean Rlbean:rollist) {
			 	/*
			 	i=Rlbean.getROLEID();
			 	 rolename=roledao.getroleName(i);*/
			
			  if(!username.equals("")  && Rlbean.getUser_name().toLowerCase().contains(username.toLowerCase()))
				 {tran_sort.add(Rlbean);}
			 	}
			if(tran_sort.size()<=0)
			 {
				 tran_sort=rollist;
			 }
			 
			 
			 session.setAttribute("UroleList", tran_sort);
			 response.sendRedirect("addUserRole.jsp?action=showroleList");
			
		}
		
		
		
		if(action.equalsIgnoreCase("menuList"))
		{
			ArrayList<RoleBean> Menulist1=new ArrayList<RoleBean>();
			Menulist1=rlDAO.getMenulist();
			request.setAttribute("menu", Menulist1);
			RequestDispatcher rd=request.getRequestDispatcher("assignMenu.jsp?action=newmenulist");
			rd.forward(request, response);	
		}
		if(action.equalsIgnoreCase("showRoleMenu"))
		{
			ArrayList<RoleBean> menu=new ArrayList<>();
			int roleid=Integer.parseInt(request.getParameter("rolename"));
			menu=rlDAO.getroleMenu(roleid);
			request.setAttribute("mlist", menu);
			request.setAttribute("rid", roleid);
			if(menu.size()!=0)
			{
				RequestDispatcher rd=request.getRequestDispatcher("showRoleMenu.jsp?action=menulist");
				rd.forward(request, response);
			}
			else
			{
				RequestDispatcher rd=request.getRequestDispatcher("showRoleMenu.jsp?action=newmenulist");
				rd.forward(request, response);	
			}
		}
		if(action.equalsIgnoreCase("loadLists"))
		{
			
			int roleid = Integer.parseInt(request.getParameter("rid"));
			ArrayList<RoleBean> assignlist = rlDAO.getroleMenu(roleid);
			ArrayList<RoleBean> notAssigned = rlDAO.getNotAssignedMenuList(roleid);
			StringBuilder str = new StringBuilder();
			str.append("<select name=\"notAssign\" id=\"notAssign\" size=\"15\" multiple style=\"width:150px\">");
            for(RoleBean r0:notAssigned)
            {
            	str.append("<option value=\""+r0.getMENUID()+"\">"+r0.getMENU_NAME()+"</option>");
            }
			str.append("</select>");
			str.append("$");
			str.append("<select name=\"assign\" id=\"assign\" size=\"15\" multiple style=\"width:150px\">");
			for(RoleBean r2:assignlist)
            {
            	str.append("<option value=\""+r2.getMENUID()+"\">"+r2.getMENU_NAME()+"</option>");
            }
			str.append("</select> $ ");
			
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.write(str.toString());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
			{
		// TODO Auto-generated method stub
		String action = request.getParameter("action") != null ? request
				.getParameter("action") : "";

		RoleDAO rlDAO = new RoleDAO();
		if (action.equals("addRole"))

		{
			boolean flag = false;
			String rolename = request.getParameter("rolename") != null ? request
					.getParameter("rolename") : "";
			String rlDesc = request.getParameter("roledesc") != null ? request
					.getParameter("roledesc") : "";
			flag = rlDAO.addRole(rolename, rlDesc);
			if (flag) {
				ArrayList<RoleBean> arrList = new ArrayList<RoleBean>();
				arrList = rlDAO.getAllRole();
				request.removeAttribute("roleList");
				request.setAttribute("roleList", arrList);
				RequestDispatcher rd = request
						.getRequestDispatcher("rolesManagement.jsp?action=showRole");
				rd.forward(request, response);
			}
		}
		
		
		if(action.equalsIgnoreCase("adduserRole"))
		{
			boolean flag=false;
			RoleBean rolebean=new RoleBean();
			String[] employ = request.getParameter("userid").split(":");
			String EMPNO = employ[2].trim();
			
			rolebean.setUSERID(Integer.parseInt(EMPNO));
			rolebean.setROLEID(Integer.parseInt(request.getParameter("role")));
			rolebean.setSTATUS(request.getParameter("status"));
			HttpSession session=request.getSession();
			String name=(String)session.getAttribute("name");
			int rollid=rlDAO.getRollid(name);
			rolebean.setASSIGNED_RU_ID(rollid);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Date date = new Date();
			String currentdate = dateFormat.format(date); 
			rolebean.setLASTMOD_DATE(currentdate);
			
			flag=rlDAO.addUserRole(rolebean);
			ArrayList<RoleBean> rollist=new ArrayList<>();
			rollist=rlDAO.getrolelist();
			session.setAttribute("UroleList", rollist);
			if(flag)
			{
				response.sendRedirect("addUserRole.jsp?action=showroleList");
			}
			else
			{
				response.sendRedirect("addUserRole.jsp?action=showroleList");
			}
		}

		if(action.equalsIgnoreCase("EdituserRole"))
		{
		
			boolean flag=false;
			RoleBean rolebean1=new RoleBean();
			rolebean1.setUSER_ROLE_ID(Integer.parseInt(request.getParameter("uid")));
			rolebean1.setUSERID(Integer.parseInt(request.getParameter("userid")));
			rolebean1.setROLEID(Integer.parseInt(request.getParameter("role")));
			rolebean1.setSTATUS(request.getParameter("status"));
		    HttpSession session=request.getSession();
			
			flag=rlDAO.updateUserRole(rolebean1);
			ArrayList<RoleBean> rollist=new ArrayList<>();
			rollist=rlDAO.getrolelist();
			session.setAttribute("UroleList", rollist);
			if(flag)
			{
				response.sendRedirect("addUserRole.jsp?action=showroleList");
			}
			else
			{
				response.sendRedirect("addUserRole.jsp?action=showroleList");
			}
		}
		if(action.equalsIgnoreCase("aasignMenu"))
		{
			int roleid=Integer.parseInt(request.getParameter("role"));
			String Menu[]=request.getParameterValues("assign");
			boolean flag = rlDAO.deleteRoleMenus(roleid);
			if(flag)
			{
				try
				{
				flag=rlDAO.assignMenu(Menu,roleid);
				if(flag)
				{
					response.sendRedirect("assignMenu.jsp?action=1");
				}
				}
				catch(Exception e)
				{
					if(flag)
					{
						response.sendRedirect("assignMenu.jsp?action=1");
					}
					if(!flag)
					{
						response.sendRedirect("assignMenu.jsp?action=-1");
					}
				}
				 
			}
			if(!flag)
			{
				response.sendRedirect("assignMenu.jsp?action=-1");
			}
		}
		if(action.equalsIgnoreCase("createNewRole"))
		{
					
			boolean flag=false;
			String rolename=request.getParameter("rolename");
			String desc=request.getParameter("desc");
			flag=rlDAO.createRole(rolename,desc);
			if(flag)
			{
				response.sendRedirect("RoleServlet?action=menuList");
			}
			else
			{
				response.sendRedirect("CreateNewRole.jsp");
			}
		}
	}
}