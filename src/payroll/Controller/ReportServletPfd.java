package payroll.Controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import payroll.Core.UtilityDAO;
import payroll.DAO.ReportDAOPDF;

/**
 * Servlet implementation class ReportServletPfd
 */
@WebServlet("/ReportServletPfd")
public class ReportServletPfd extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public ReportServletPfd() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String action = request.getParameter("action");
		if(action.equalsIgnoreCase("emplist")){
			
			String type = request.getParameter("type");
			String date = request.getParameter("date");
			date="01-"+date;
			
			String filePath = getServletContext().getRealPath("")+ File.separator + "Emplist.pdf";
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			try
			{
				if(type.equalsIgnoreCase("genral")){			
					
					//Method Call for Pdf Report.....
					ReportDAOPDF.EmployeeList(date,type, filePath,imagepath);
					
					//rspdf.GenrateReport(filePath,response);
					
					final int BUFSIZE = 4096;
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
					
					
				}
				else if(type.equalsIgnoreCase("A")){
					
				}
				else if(type.equalsIgnoreCase("B")){
					
				}else if(type.equalsIgnoreCase("C")){
					
				}else if(type.equalsIgnoreCase("D")){
					
				}else if(type.equalsIgnoreCase("E")){
					
				}
			
			}catch(Exception e){e.printStackTrace();}
			
		}
		
	}	
	

	
}
