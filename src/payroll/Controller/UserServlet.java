package payroll.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.DAO.ConnectionManager;
import payroll.DAO.UserMasterHandler;
import payroll.DAO.UsrHandler;
import payroll.Model.UsrMast;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
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
		try
		{ 
			UsrHandler UH = new UsrHandler();
			HttpSession session = request.getSession();
			String EMPSession="";
			boolean sessionFlag = true;
			try
			{
				EMPSession = session.getAttribute("EMPNO").toString();
			}
			catch(Exception e)
			{
				sessionFlag = false;
			}
			if(sessionFlag)
			{
				String action = request.getParameter("action")==null?"":request.getParameter("action");
			    String submit1 = request.getParameter("Submit1")==null?"":request.getParameter("Submit1");
			    if(action.equalsIgnoreCase("addnew"))
				{	
			    	java.util.Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
					String dt = sdf.format(date);
					UsrMast user = new UsrMast();
					
					
					String[] employ = request.getParameter("empid").split(":");
				    String EMPNO = employ[2].trim();
				    int empid=Integer.parseInt(EMPNO.trim());
					
				    user.setEMPID(request.getParameter("empid"));
					user.setUNAME(request.getParameter("uname")!=null?request.getParameter("uname"):"");
					user.setUPWD(request.getParameter("pass")!=null?request.getParameter("pass"):"");
					user.setMail(request.getParameter("eLine3")!=null?request.getParameter("eLine3"):"info@dts3.net");
					user.setUCREATEDATE(dt);
					user.setUMODDATE(dt);
					user.setUMODUID(EMPSession);
					user.setUSTATUS(request.getParameter("status")!=null?request.getParameter("status"):"");
					
					boolean flag = UH.addUser(user);
					if(flag)
					{
						
						if(submit1.equals("Save"))
						{
							response.sendRedirect("userRoles.jsp?action=1");
						} // User Saved Successfully
						else
						{
							response.sendRedirect("RoleServlet?action=showfirst");	// User Saved Successfully	
						}
					}
					else
					{
						response.sendRedirect("userRoles.jsp?action=0");	// Error in saving User
					}
				  }
			    //For Chaeck UserName
			    if(action.equalsIgnoreCase("check")){
					response.setContentType("text/html;charset=UTF-8");
					String uname = request.getParameter("uname")!=null?request.getParameter("uname"):"";
			        PrintWriter out = response.getWriter();
			        try {

			        	Connection con =  ConnectionManager.getConnection();
			        	//System.out.println("uname==="+uname);
			            PreparedStatement ps = con.prepareStatement("select user_name from USERS where user_name=?"); // users is the table containg used usernemes
			            ps.setString(1,uname);
			            
			            ResultSet rs = ps.executeQuery();
			             
			            if (!rs.next()) {
			            	out.println("1");
			            }
			            else{
			            out.println("0");
			            
			            }
			            out.println();
			            con.close();



			        } catch (Exception ex) {

			            out.println("Error ->" + ex.getMessage());

			        } finally {
			            out.close();
			        }

				}
			    
			    //For Check User ID
			    if(action.equalsIgnoreCase("checkID")){
					response.setContentType("text/html;charset=UTF-8");
				System.out.println(request.getParameter("empid"));
					String[] employ = request.getParameter("empid").split(":");
				    String EMPCODE = employ[1].trim();
				    String EMPNO = employ[2].trim();
			        PrintWriter out = response.getWriter();
			        try {

			        	Connection con =  ConnectionManager.getConnection();
			        	
			            PreparedStatement ps = con.prepareStatement("select * from USERS where EMP_ID=?"); // users is the table containg used usernemes
			            ps.setString(1,EMPNO);
			            
			            ResultSet rs = ps.executeQuery();
			             
			            if (!rs.next()) {
			            	out.println("1");
			            }
			            else{
			            out.println("0");
			            
			            }
			            out.println();
			            con.close();



			        } catch (Exception ex) {

			            out.println("Error ->" + ex.getMessage());

			        } finally {
			            out.close();
			        }

				}
			    
			    if(action.equalsIgnoreCase("changePass"))
			    {
			    	
			    	try
			    	{
			    		String uname = session.getAttribute("name").toString();
			    		String empid = session.getAttribute("EMPNO").toString();
			    		String pass = request.getParameter("curPass");
			    		String newPass = request.getParameter("newPass");
			    		int flag = UH.updatePassword(uname, pass, newPass, empid);
			    		if(flag == 1)
			    		{
			    			request.getRequestDispatcher("ChangePassword.jsp?action="+flag).forward(request, response);
			    		}
			    		else if(flag == 2)
			    		{
			    			request.getRequestDispatcher("ChangePassword.jsp?action="+flag).forward(request, response);
			    		}
			    		else
			    		{
			    			request.getRequestDispatcher("ChangePassword.jsp?action="+flag).forward(request, response);
			    		}
			    			
			    	}
			    	catch(Exception e)
			    	{
			    		response.sendRedirect("login.jsp?action=0");
			    	}
			    }
			    
			}
			else
			{
				response.sendRedirect("login.jsp?action=0");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.sendRedirect("userRoles.jsp?action=0");
		}
	}

}
