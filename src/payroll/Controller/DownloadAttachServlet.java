package payroll.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import payroll.DAO.ConnectionManager;

/**
 * Servlet implementation class DownloadAttachServlet
 */
@WebServlet("/download")
public class DownloadAttachServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadAttachServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		 String filepath="";
		 HttpSession session = request.getSession();
		 try
		 {
			 Connection con =ConnectionManager.getConnection();
			 Statement st = con.createStatement();   
		String act = request.getParameter("act"); 	
		 if(act.equalsIgnoreCase("emp")){	
		 int srno = Integer.parseInt(request.getParameter("f"));
		 ResultSet rs = st.executeQuery("select ATTACHPATH from ATTACHMENT where EMPNO="+session.getAttribute("empno")+" and srno="+srno);
		 
		if(rs.next())
		{
		filepath = rs.getString("ATTACHPATH");
		System.out.println("filepath is "+filepath);
		}
		 } 
		 else if(act.equalsIgnoreCase("site")){
			 int srno = Integer.parseInt(request.getParameter("f"));
			 ResultSet rs = st.executeQuery("select ATTACHPATH from SiteWise_File_Upload where PRJ_SRNO="+Integer.parseInt(session.getAttribute("Prj_Srno").toString())+" and srno="+srno);
			 
			if(rs.next())
			{
			filepath = rs.getString("ATTACHPATH");
			System.out.println("filepath is "+filepath);
			} 
		 }
		 else if(act.equalsIgnoreCase("adminsite")){
			 int srno = Integer.parseInt(request.getParameter("f"));
			 int prj_srno = Integer.parseInt(request.getParameter("p"));
			 ResultSet rs = st.executeQuery("select ATTACHPATH from SiteWise_File_Upload where PRJ_SRNO="+prj_srno+" and srno="+srno);
			 
			if(rs.next())
			{
			filepath = rs.getString("ATTACHPATH");
			System.out.println("filepath is "+filepath);
			} 
		 }
	    File f = new File (filepath);
	    String filename=f.getName();
	    String type=getMimeType("file:"+filepath);

	    response.setContentType (type);
	    response.setHeader ("Content-Disposition", "attachment;     filename=\""+filename+"\"");

	    String name = f.getName().substring(f.getName().lastIndexOf("/") + 1,f.getName().length());
	    InputStream in = new FileInputStream(f);
	        ServletOutputStream outs = response.getOutputStream();

	        int bit = 256;
	        int i = 0;
	            try {
	                    while ((bit) >= 0) {
	                        bit = in.read();
	                        outs.write(bit);
	                    }
	                        } catch (IOException ioe) {
	                        ioe.printStackTrace(System.out);
	                    }
	                        outs.flush();
	                    outs.close();
	                    in.close(); 
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 
	}

	public static String getMimeType(String fileUrl)
		    throws java.io.IOException, MalformedURLException 
		  {
		    String type = null;
		    URL u = new URL(fileUrl);
		    URLConnection uc = null;
		    uc = u.openConnection();
		    type = uc.getContentType();
		    return type;
		  }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
