package payroll.Controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.itextpdf.text.TabStop.Alignment;
import com.lowagie.text.DocumentException;

import payroll.DAO.AddProjectDAO;
import payroll.DAO.ConnectionManager;
import payroll.DAO.EmpExperHandler;
import payroll.DAO.ProjectReportDAO;
import payroll.Model.AddProjectBean;
import payroll.Model.ProjectReportBean;

/**
 * Servlet implementation class AddReportServlet
 */
@WebServlet("/AddReportServlet")
public class AddReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddReportServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ProjectReportBean prb = new ProjectReportBean();
		AddProjectBean apb1 = new AddProjectBean();
		AddProjectDAO apd = new AddProjectDAO();

		String action = request.getParameter("action");
		if (action.equals("gettypes")) {
			
			response.setContentType("txt/html");
			PrintWriter out = response.getWriter();

			String types = request.getParameter("types");
			ArrayList<AddProjectBean> list = apd.getTypes(types);
			System.out.println("list data" + list);
			if (types.equals("Pstatus")) {
				for (AddProjectBean apb : list) {
					out.write(apb.getPROSTATUS() + ",");
					System.out.println(apb.getPROSTATUS());
				}
			} else if (types.equals("PPriority")) {
				for (AddProjectBean apb : list) {
					out.write(apb.getPROPRIORITY() + ",");
					System.out.println(apb.getPROSTATUS());
				}
			} else {
				for (AddProjectBean apb : list) {
					out.write(apb.getPROSTATUS() + "," + apb.getPROPRIORITY() + ",");
					System.out.println(apb.getPROPRIORITY());
				}

			}

		}
		/*
		 * else if(action.equals("AddProjectReport")) {
		 * 
		 * prb.setEMPNAME(request.getParameter("ename"));
		 * prb.setPROJECTNAME(request.getParameter("pnm"));
		 * prb.setSTATUS(request.getParameter("status"));
		 * prb.setTYPES(request.getParameter("type"));
		 * prb.setPRIORITY(request.getParameter("priority"));
		 * prb.setSTART_DATE(request.getParameter("startdate"));
		 * prb.setDUE_DATE(request.getParameter("duedate"));
		 * prb.setDESRIPTION(request.getParameter("description")); boolean
		 * flag=ProjectReportDAO.addProjectReport(prb); if(flag) {
		 * response.sendRedirect("ProFilter.jsp?action=addedrp"); } else {
		 * response.sendRedirect("AddProjectReport.jsp"); } }
		 */
		else if (action.equals("generatereport")) {
			
			
			/*
			 * String fromDate=request.getParameter("fromdate"); String
			 * toDate=request.getParameter("todate"); String
			 * type=request.getParameter("type");//priority and status and all String
			 * subtype=request.getParameter("subtype"); String
			 * fileType=request.getParameter("filetype"); String type1="";
			 * 
			 * if(type.equals("status")) { type1="Pstatus"; } else
			 * if(type.equals("priority")) { type1="PPriority"; } else { type1="All"; } if()
			 * ProjectReportDAO.reportProject(fileType,type1,subtype,fromDate,toDate);
			 * 
			 * 
			 * AddProjectBean apb=new AddProjectBean(); ArrayList<AddProjectBean>
			 * list=apd.getReport(type1,subtype,fromDate,toDate);
			 */

			String fromDate=request.getParameter("fromdate"); String
			 toDate=request.getParameter("todate"); 
			String type=request.getParameter("type");//priority and status and all 
			String subtype=request.getParameter("subtype"); 
			 String fileType=request.getParameter("filetype");
			 String query="",q1="",q2="";
			
		
			if(fileType.equals("xls"))
			{
				String filePath1 = getServletContext().getRealPath("")+ File.separator + "ProjectList.pdf";
				String filepath2=getServletContext().getRealPath("")+ File.separator + "Project_List.xls";
				String imagepath =getServletContext().getRealPath("/images/plogo.png");
				
				
			try{
				
				  if(type.equals("Pstatus"))
				  {
					  query="select P.projectId,P.ProjectName,P.PType,P.Pstatus,P.PPriority,P.StartDate,P.EndDate,T.TASK_NAME,T.TASK_ID,E.Name from AddProject P\r\n" + 
					  		"	inner join Task t on P.ProjectId=T.projectId inner join SubTask ST on T.TASK_ID=ST.TASK_ID inner join Assigner A on ST.Sub_ID=A.Sub_Id inner join Employee_PM E on A.Emp_Id=E.Emp_Id\r\n" + 
					  		"	where   P.StartDate between '"+fromDate+"' and '"+toDate+"'  P.Pstatus='"+subtype+"' and P.PCONDITION='Active' order by P.StartDate";
				  }
				  else if(type.equals("PPriority"))
				  {
					  query="select P.projectId,P.ProjectName,P.PType,P.Pstatus,P.PPriority,P.StartDate,P.EndDate,T.TASK_NAME,T.TASK_ID,E.Name from AddProject P\r\n" + 
					  		"	inner join Task t on P.ProjectId=T.projectId inner join SubTask ST on T.TASK_ID=ST.TASK_ID inner join Assigner A on ST.Sub_ID=A.Sub_Id inner join Employee_PM E on A.Emp_Id=E.Emp_Id\r\n" + 
					  		"	where   P.StartDate between '"+fromDate+"' and '"+toDate+"'  P.PPriority='"+subtype+"' and P.PCONDITION = 'Active' order by P.StartDate";
				  }
				  else
				  {
					  query="select P.projectId,P.ProjectName,P.PType,P.Pstatus,P.PPriority,P.StartDate,P.EndDate,T.TASK_NAME,T.TASK_ID,E.Name from AddProject P\r\n" + 
						  		"	inner join Task t on P.ProjectId=T.projectId inner join SubTask ST on T.TASK_ID=ST.TASK_ID inner join Assigner A on ST.Sub_ID=A.Sub_Id inner join Employee_PM E on A.Emp_Id=E.Emp_Id where  P.StartDate between '"+fromDate+"' and '"+toDate+"' and P.PCONDITION = 'Active' order by StartDate";
				  }
				  HSSFWorkbook hwb=new HSSFWorkbook();
					HSSFSheet sheet =  hwb.createSheet("Project Report List");
						
					HSSFRow rowtitle=   sheet.createRow((short)0);
					rowtitle.createCell((short) 0).setCellValue("PROJECT LIST: ");
					//rowtitle.createCell((short) 1).setCellValue("PFLIST : "+rdt);
					HSSFFont blueFont = hwb.createFont();
					blueFont.setColor(HSSFColor.BLUE.index);
					
					HSSFCellStyle style = hwb.createCellStyle();
					style.setFont(blueFont);
					style.setFillForegroundColor(HSSFColor.BLUE.index);

				HSSFRow rowheadspace=   sheet.createRow((short)1);
				
				HSSFRow rowhead=   sheet.createRow((short)1);
				
				rowhead.createCell((short) 0).setCellValue("SR NO");
				rowhead.createCell((short) 1).setCellValue("PROJECT ID");
				rowhead.createCell((short) 2).setCellValue("PROJECT NAME");
				rowhead.createCell((short) 3).setCellValue("PROJECT TYPE");
				rowhead.createCell((short) 4).setCellValue("PROJECT STATUS");
				rowhead.createCell((short) 5).setCellValue("PROJECT PRIORITY");
				rowhead.createCell((short) 6).setCellValue("START DATE");
				rowhead.createCell((short) 7).setCellValue("END DATE");
				rowhead.createCell((short) 8).setCellValue("TASK ID");
				rowhead.createCell((short) 9).setCellValue("TASK NAME");
				rowhead.createCell((short) 10).setCellValue("EMP NAME");
				
			//Class.forName("oracle.jdbc.driver.OracleDriver"); //com.mysql.jdbc.Driver
				Connection con = ConnectionManager.getConnection();
				Statement st=con.createStatement();
				
				ResultSet rs=st.executeQuery(query);
				int i=2;
				int srno=0;
				while(rs.next()){
				HSSFRow row=   sheet.createRow((short)i);
				srno++;
				
				row.createCell((short) 0).setCellValue(srno);
				row.createCell((short) 1).setCellValue(Integer.toString(rs.getInt("projectId")));
				row.createCell((short) 2).setCellValue( rs.getString("ProjectName"));
				row.createCell((short) 3).setCellValue(rs.getString("PType"));
				row.createCell((short) 4).setCellValue(rs.getString("Pstatus"));
				row.createCell((short) 5).setCellValue(rs.getString("PPriority"));
				row.createCell((short) 6).setCellValue(rs.getString("StartDate"));
				row.createCell((short) 7).setCellValue(rs.getString("EndDate"));
				System.out.println("111");
					row.createCell((short) 8).setCellValue(rs.getString("TASK_ID"));
					row.createCell((short) 9).setCellValue(rs.getString("TASK_NAME"));
					row.createCell((short) 10).setCellValue(rs.getString("NAME"));
				i++;
			}
				FileOutputStream fileOut =  new FileOutputStream(filepath2);
				hwb.write(fileOut);
				fileOut.close();
				final int BUFSIZE = 4096;
				File file = new File(filepath2);
				int length = 0;
				ServletOutputStream outStream = response.getOutputStream();
				ServletContext context = getServletConfig().getServletContext();
				String mimetype = context.getMimeType(filepath2);
				if (mimetype == null) 
				{
					mimetype = "application/octet-stream";
				}
				response.setContentType(mimetype);
				response.setContentLength((int) file.length());
				String fileName = (new File(filepath2)).getName();
				response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + "\"");
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
				Runtime.getRuntime()
				   .exec("rundll32 url.dll,FileProtocolHandler " + filepath2);
			System.out.println("Your excel file has been generated!");

			} catch ( Exception ex ) {
			    System.out.println(ex);

			}
			}
			else if(fileType.equals("pdf"))
			{
				
				String filePath1 = getServletContext().getRealPath("")+ File.separator + "ProjectList.pdf";
				String filepath1=getServletContext().getRealPath("")+ File.separator + "Bonus_List.xls";
				String imagepath =getServletContext().getRealPath("/images/plogo.png");
				
				try {
					ProjectReportDAO.reportProject(filePath1,imagepath,fileType, type, subtype, fromDate, toDate);
					final int BUFSIZE = 4096;
					File file = new File(filePath1);
					int length = 0;
					ServletOutputStream outStream = response.getOutputStream();
					ServletContext context = getServletConfig().getServletContext();
					String mimetype = context.getMimeType(filePath1);
					if (mimetype == null) 
					{
						mimetype = "application/octet-stream";
					}
					response.setContentType(mimetype);
					response.setContentLength((int) file.length());
					String fileName = (new File(filePath1)).getName();
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
				} catch (DocumentException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

		}
	}

}
