package payroll.Controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.DocumentException;

import payroll.Core.ErrorLog;
import payroll.Core.UtilityDAO;
import payroll.DAO.GratuityDAO;

/**
 * Servlet implementation class Gratuity
 */
@WebServlet("/Gratuity")
public class Gratuity extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	ErrorLog el=new ErrorLog();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Gratuity() {
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
		String action=request.getParameter("action");
		
		
		
		
		
		if(action.equalsIgnoreCase("postgratuity"))
		{
		
		String empno=	request.getParameter("empno");
		GratuityDAO ob=new GratuityDAO(); 
		ob. getGratuityAMt(empno,"P"); //P for poast into calculation else "" or nnull	
		
		request.getRequestDispatcher("Gratuity.jsp?flag=1").forward(request, response);
		
		
		
		
		}
		
		else if(action.equalsIgnoreCase("postgratuityforALL"))
		{
		
		
		
		
		
		final int BUFSIZE = 4096;
		String filePath = getServletContext().getRealPath("")+ File.separator + "GratuityforAllEmployees.pdf";
		String date=request.getParameter("date");
		date="01-"+date;
		String table_name=request.getParameter("before")==null?"after":"before";

		String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
		try {
			
			GratuityDAO ob=new GratuityDAO(); 
			ob. setGratuityAMtforAll(filePath,imagepath);	
			
			File file = new File(filePath);
			int length = 0;
			ServletOutputStream outStream = response.getOutputStream();
			ServletContext context = getServletConfig().getServletContext();
			String mimetype = context.getMimeType(filePath);
			if (mimetype == null) 
			{
				mimetype = "application/octet-stream";
			}
			response.setContentType(mimetype);
			response.setContentLength((int) file.length());
			String fileName = (new File(filePath)).getName();
			response.setHeader("Content-Disposition", "attachment; filename=\""	+ fileName + "\"");
			byte[] byteBuffer = new byte[BUFSIZE];
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			while ((in != null) && ((length = in.read(byteBuffer)) != -1)) 
			{
				outStream.write(byteBuffer, 0, length);
			}
			in.close();
			outStream.close();
			if (file.exists())
			{
				file.delete();
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			el.errorLog("IN GratuityServlet---POST---postgratuityforALL--ACTION---", e.toString());
		}
		 
		
		
		
		
		
		
		
		
		
		}
		
		
		
		
				
	}

}
