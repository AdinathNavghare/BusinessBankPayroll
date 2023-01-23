	package payroll.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;



import payroll.DAO.ConnectionManager;

/**
 * Servlet implementation class AddPhotoServlet
 */
@WebServlet("/AddPhotoServlet")
public class AddPhotoServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
    	
    //	String action=request.getParameter("action");
    	response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session=request.getSession();
        String empno =(String) session.getAttribute("empno");
        String   type="";
        System.out.println("empno is"+empno);
        String column=(String) (session.getAttribute("column")==null?"NA":session.getAttribute("column"));
        try {
            // Apache Commons-Fileupload library classes
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload sfu  = new ServletFileUpload(factory);

            if (! ServletFileUpload.isMultipartContent(request)) 
            {
                System.out.println("sorry. No file uploaded");
                return;
            }

            // parse request
            List items = sfu.parseRequest(request);
            FileItem  id = (FileItem) items.get(0);
            FileItem title = (FileItem) items.get(1);
            System.out.println("(FileItem) items.get(0) : "+(FileItem) items.get(0)+" (FileItem) items.get(1)  : "+(FileItem) items.get(1)+" title.getSize()  ::"+title.getSize());
            FileItem file;
            FileItem file2 = null;
            if(title.getSize()!=0)
            {
            	 file = (FileItem) items.get(2);
            	 type=title.getString();
            	 System.out.println("type is "+type);
            	 
            }
            else
            {
            	 file = (FileItem) items.get(2);
                 file2 = (FileItem) items.get(3);
            }
            // get uploaded file
            

            // Connect to Oracle
            
            
            
            Connection con = ConnectionManager.getConnection();
            con.setAutoCommit(false);
            Statement st = con.createStatement();
		     
            
            if(title.getSize()!=0)
            {
            	System.out.println(""+type);
            	ResultSet rs = st.executeQuery("select IMG from CREDENTIALNEW where type='"+type+"' and empno="+empno);
            	if(rs.next())
            	{
            		try
            		{
            		System.out.println(""+type);
            			String sql ="delete  from CREDENTIALNEW where type='"+type+"' and empno="+empno;
            			int i = st.executeUpdate(sql);
            			con.commit();
            			PreparedStatement ps1 = con.prepareStatement("insert into CREDENTIALNEW (EMPNO,TYPE,IMG) values(?,?,?)");
                    	ps1.setString(1, empno);
                    	ps1.setString(2, type);
                    	ps1.setBinaryStream(3, file.getInputStream(), (int) file.getSize());
                    	ps1.execute();
                    	con.commit();
                   	 	con.close();
                    	if(type.equalsIgnoreCase("photo"))
                    	{
                    	request.getRequestDispatcher("uploadImage.jsp?action=close").forward(request, response);
                    	}
                    	else
                    	{
                    		request.getRequestDispatcher("uploadSign.jsp?action=close").forward(request, response);	
                    	}
                    	System.out.println("file is deleted  "+i);
            		}catch(Exception e)
            		{
            			e.printStackTrace();
            		}
            	}
            	PreparedStatement ps1 = con.prepareStatement("insert into CREDENTIALNEW (EMPNO,TYPE,IMG) values(?,?,?)");
            	ps1.setString(1, empno);//
            	ps1.setString(2, type);
            	ps1.setBinaryStream(3, file.getInputStream(), (int) file.getSize());
            	ps1.execute();
            	con.commit();
            	 con.close();
            	 if(type.equalsIgnoreCase("photo"))
             	{
            		 System.out.println("inserting images.....");
             	request.getRequestDispatcher("uploadImage.jsp?action=close").forward(request, response);
             	}
             	else
             	{
             		request.getRequestDispatcher("uploadSign.jsp?action=close").forward(request, response);	
             	}
            }
            else
            {
            	
            	System.out.println("inserting new image");
            	
            	
            	 PreparedStatement ps2 = con.prepareStatement("delete from CredentialNew where EMPNO="+empno);
           
            PreparedStatement ps = con.prepareStatement("insert into CREDENTIALNEW (EMPNO,TYPE,IMG) values(?,?,?)");
            
           /* PreparedStatement ps = con.prepareStatement("UPDATE CREDENTIALNEW SET EMPNO=?, TYPE=? , IMG=?");*/
           
            ps.setString(1, empno);//pass here employee id
            ps.setString(2, "PHOTO");
            // size must be converted to int otherwise it results in error
            ps.setBinaryStream(3, file.getInputStream(), (int) file.getSize());
            PreparedStatement ps1 = con.prepareStatement("insert into CREDENTIALNEW(EMPNO,TYPE,IMG) values(?,?,?)");
            ps1.setString(1, empno);//pass here employee id
            ps1.setString(2, "SIGN");
            ps1.setBinaryStream(3, file2.getInputStream(),(int)file2.getSize());
          
          ps2.executeUpdate();
            ps.executeUpdate();
            ps1.executeUpdate();
            System.out.println("file is new images Inserted");
            con.commit();
   				
            con.close();
            /*request.getRequestDispatcher("Attachment.jsp?action=editemp&status=Photo and Signture uploaded Successfullyy").forward(request, response);*/
            String key=(String) session.getAttribute("key");
            if(key!=null)
            {
				if(key.equalsIgnoreCase("addemp") || key.equalsIgnoreCase("editemp"))
				{
	            response.sendRedirect("Attachment.jsp?action=editemp&list=show");
				}
				else
				{
					response.sendRedirect("Attachment.jsp?action=showemp&list=show");
				}
	            }
            else
            {
            	response.sendRedirect("Attachment.jsp?action=editemp&list=show");
            }
            }
        }
        catch(Exception ex) {
            out.println( "Error --> " + ex.getMessage());
            ex.printStackTrace();
        }
    }
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException
    {}
}
