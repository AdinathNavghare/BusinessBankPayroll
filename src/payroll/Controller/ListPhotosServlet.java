package payroll.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import payroll.DAO.ConnectionManager;

/**
 * Servlet implementation class ListPhotosServlet
 */
@WebServlet("/ListPhotosServlet")
public class ListPhotosServlet extends HttpServlet {
	   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String result="";
        PrintWriter out = response.getWriter();
        try {


            Connection con = ConnectionManager.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from photos ");
            ResultSet rs = ps.executeQuery();
            out.println("<h1>Photos</h1>");
            while ( rs.next()) {
                  //out.println("<h4>" + rs.getString("title") + "</h4>");
                  //out.println("<img width='200' height='200' src=displayphoto?id=" +  rs.getString("id") + "></img> <p/>");
                  result=result+"<img width='200' height='200' src=displayphoto?id=" +  rs.getString("EMPNO") + "></img> <p/>";
                  
            }

            con.close();
        }
        catch(Exception ex) {

        }
        finally { 
            out.close();
        }
        PrintWriter out1=response.getWriter();
		response.setContentType("text/html");
		out1.write(result);
    } 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
}
