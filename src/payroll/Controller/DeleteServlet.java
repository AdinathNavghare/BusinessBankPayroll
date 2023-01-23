package payroll.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import payroll.DAO.ConnectionManager;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteServlet() {
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
		String action1 = request.getParameter("action1");
		//String action2 = request.getParameter("action2");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		if(action1.equalsIgnoreCase("emp")){
			if(session!=null)
			{	
				try
				{
					Connection con = ConnectionManager.getConnection();
					String action = request.getParameter("action");
					String srno = request.getParameter("srno");
					String empno = request.getParameter("empno");
					String sql = "";
					boolean flag = false;
					if(action.equalsIgnoreCase("qual"))
					{
						sql = "DELETE FROM QUAL WHERE EMPNO="+empno+" AND SRNO="+srno;
					}
					else if(action.equalsIgnoreCase("addr"))
					{
						sql = "DELETE FROM EMPAUX WHERE EMPNO="+empno+" AND ADDRTYPE="+srno;
					}
					else if(action.equalsIgnoreCase("family"))
					{
						sql = "DELETE FROM FAMILY WHERE EMPNO="+empno+" AND SRNO="+srno;
					}
					else if(action.equalsIgnoreCase("expr"))
					{
						sql = "DELETE FROM EXPERIENCE WHERE EMPNO="+empno+" AND SRNO="+srno;
					}
					else if(action.equalsIgnoreCase("office"))
					{
						Statement stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery("SELECT * FROM YTDTRAN WHERE EMPNO="+empno);
						if(rs.next()){
							out.write("false");
							flag = true;
						}
						else{
							sql = "DELETE FROM EMPTRAN WHERE EMPNO="+empno+" AND SRNO="+srno;
						}
						rs.close();
						stmt.close();
					}
					else if(action.equalsIgnoreCase("award"))
					{
						sql = "DELETE FROM EMPAWARD WHERE EMPNO="+empno+" AND SRNO="+srno;
					}
					if(flag == false){
						Statement st = con.createStatement();
						st.execute(sql);
						out.write("true");
						st.close();
					}
					con.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
					out.write("false");
				}
			}
			else
			{
				response.sendRedirect("login.jsp?action=0");
			}
		}
		if(action1.equalsIgnoreCase("delete")){
			if(session!= null){
				try{
					String action = request.getParameter("action");
					int srno = Integer.parseInt(request.getParameter("srno"));
					String hdname = request.getParameter("hdname")!=null?request.getParameter("hdname"):"";
					String query = "";
					if(action.equalsIgnoreCase("journey")){
						query = "DELETE FROM TRDETAILS WHERE DTYP='A' AND SRNO="+srno+"";
					}
					else if(action.equalsIgnoreCase("localExp")){
						query = "DELETE FROM TRDETAILS WHERE DTYP='B' AND SRNO="+srno+"";
					}
					else if(action.equalsIgnoreCase("foodExp")){
						query = "DELETE FROM TRDETAILS WHERE DTYP='C' AND SRNO="+srno+"";
					}
					else if(action.equalsIgnoreCase("otherExp")){
						query = "DELETE FROM TRDETAILS WHERE DTYP='D' AND SRNO="+srno+"";
					}
					else if(action.equalsIgnoreCase("miscExp")){
						query = "DELETE FROM TRDETAILS WHERE DTYP='E' AND SRNO="+srno+"";
					}
					else if(action.equalsIgnoreCase("holiday")){
						query = "DELETE FROM HOLDMAST WHERE HOLDNAME="+"('"+hdname+"')";
					}
					Connection con = ConnectionManager.getConnection();
					Statement st = con.createStatement();
					st.execute(query);
					out.write("true");
					st.close();
					con.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
					out.write("false");
				}
			}
			else
			{
				response.sendRedirect("login.jsp?action=0");
			}
		}
	}
}