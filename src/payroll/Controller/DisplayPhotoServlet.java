package payroll.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.DAO.ConnectionManager;
import payroll.DAO.LookupHandler;

/**
 * Servlet implementation class DisplayPhotoServlet
 */
@WebServlet("/DisplayPhotoServlet")
public class DisplayPhotoServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, SQLException {
    	//HttpSession session=request.getSession();
    	String type = request.getParameter("type");
    	//String empno = (String) session.getAttribute("empno");
    	String empno =	request.getParameter("empno");
    	Blob  b = null;
    	Connection con = null ;
        try {
            
            con = ConnectionManager.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select IMG from CREDENTIALNEW where empno ="+empno+" and type='"+type+"' ");
           if(rs.next())
           {
              b = rs.getBlob(1);
           }
           else 
           {
        	  
        	   ResultSet rs1 = st.executeQuery("select IMG from CREDENTIALNEW where empno =0 and type='"+type+"' ");
               rs1.next();
               b = rs1.getBlob(1);
           }
            response.setContentType("image/jpeg");
            response.setContentLength( (int) b.length());
            InputStream is = b.getBinaryStream();
            OutputStream os = response.getOutputStream();
           
            byte buf[] = new byte[(int) b.length()];
            is.read(buf);
           
            os.write(buf);
           os.close();
        }
        catch(Exception ex) {
             System.out.println(ex.getMessage());
             ex.printStackTrace();
        }
        finally{
        	con.close();
        }
    
    
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	System.out.println("In get method");
    	String action = request.getParameter("action");
        try {
			processRequest(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	//String action = request.getParameter("action");
       // processRequest(request, response,action);
    	

		Connection con =null;
		String result="";
		ResultSet rs = null;
		
		try
		{
			con=ConnectionManager.getConnection();
		Statement st =con.createStatement();
		String action = request.getParameter("action");
		System.out.println(action);
		if(action.equalsIgnoreCase("pie"))
		{
		 rs=st.executeQuery("select count(e.empno) as totalemp ,p.prj_code from empmast e,emptran t,prj p where e.empno=t.empno and p.prj_srno=t.prj_srno and e.dol is null group by p.prj_code order by count(e.empno) asc");
		}
		if(action.equalsIgnoreCase("bar"))
		{
			rs=st.executeQuery("select count(e.empno) as totalemp ,t.prj_code from empmast e,emptran t where e.empno=t.empno and t.desig=(select desig from emptran  where empno=e.empno)  and e.dol is null group by t.desig");
		}
		result="<root><data>";
		LookupHandler lkhp = new LookupHandler();
		while(rs.next())
		{
			
			if(action.equalsIgnoreCase("bar"))
			{
				
				result+="<prjname>"+rs.getString(2)+"</prjname>";
				//result+="<prjname>"+lkhp.getLKP_Desc("DESIG", Integer.parseInt(rs.getString(2)==null?"0":rs.getString(2)))+"</prjname>";
				//System.out.println(lkhp.getLKP_Desc("DESIG", Integer.parseInt(rs.getString(2)==null?"0":rs.getString(2))));
			}
			else
			{
				result+="<prjname>"+rs.getString(2)+"</prjname>";
			}
			result+="<emp>"+rs.getString(1)+"</emp>";
		}
		
		result+="</data></root>";
		System.out.println(result);
		response.setContentType("text/xml");
		PrintWriter out =response.getWriter();
		out.write(result);
			
		}catch(Exception e){e.printStackTrace();
			}
		finally
		{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

    	
    	
    	
    	
    } 
}
