package payroll.Controller;
 
import java.io.BufferedReader;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import payroll.Core.ESICChallanDAO;
import payroll.Core.ExcelDAO;
import payroll.Core.Form12A;
import payroll.Core.Form3A;
import payroll.Core.Form6A;
import payroll.Core.PfChallanDAO;
import payroll.Core.ReportDAO;
import payroll.Core.SalaryCertificateDAO;
import payroll.Core.SalaryRegisterDAO;
import payroll.Core.TaxCalculation;
import payroll.Core.TravelReportDAO;
import payroll.Core.UtilityDAO;
import payroll.Core.YearlyPDFReport;
import payroll.Core.YearlyPDFReport_New;
import payroll.DAO.CodeMasterHandler;
import payroll.DAO.NonContribute;
import payroll.DAO.ConnectionManager;
import payroll.DAO.PostingHandler;
import payroll.DAO.ReportDAOPDF;
import payroll.DAO.UploadAttendanceDAO;
import payroll.Model.PFExcelBean;
import payroll.Model.RepoartBean;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.StringTokenizer;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
/**
 * Servlet implementation class ReportServlet
 */
@WebServlet("/ReportServlet")
public class ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public ReportServlet() 
	{
		super();
		
	}

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		String action = request.getParameter("action");
		String type1 = request.getParameter("type")==null?"":request.getParameter("type");
		 
		 	if(action.equalsIgnoreCase("pdfpayslip") && type1.equalsIgnoreCase("new"))
		{

				try{
					String date = request.getParameter("date");
						String empList = request.getParameter("list");
						String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
						String concat = ReportDAO.concation();
						String filePath = getServletContext().getRealPath("")+ File.separator + "PaySlip_"+concat+"_"+date+".pdf";
						File file = new File(filePath);
						if (file.exists()) 
						{
							file.delete();
						}
						UtilityDAO.PaySlip(date, empList, filePath, imagepath);
						PrintWriter out = response.getWriter();
						response.setContentType("text/html");
						out.write("<iframe scrolling=\"auto\" src=\"PaySlip_"+concat+"_"+date+".pdf\" height=\"500px\" width=\"100%\"></iframe>");
					
						
					}
					catch ( Exception e) 
					{
						e.printStackTrace();
					}
				
				
		}
		 	
			if(action.equalsIgnoreCase("pdfpayslip") && type1.equalsIgnoreCase("OnePage"))
			{
				System.out.println("PaySlip OnePage");

					try{
						String date = request.getParameter("date");
							String empList = request.getParameter("list");
							String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
							String concat = ReportDAO.concation();
							String filePath = getServletContext().getRealPath("")+ File.separator + "PaySlip_"+concat+"_"+date+".pdf";
							File file = new File(filePath);
							if (file.exists()) 
							{
								file.delete();
							}
							//UtilityDAO.PaySlip_OnePage(date, empList, filePath, imagepath);
							UtilityDAO.PaySlip_OnePage_Test(date, empList, filePath, imagepath);
							PrintWriter out = response.getWriter();
							response.setContentType("text/html");
							out.write("<iframe scrolling=\"auto\" src=\"PaySlip_"+concat+"_"+date+".pdf\" height=\"500px\" width=\"100%\"></iframe>");
						
							
						}
						catch ( Exception e) 
						{
							e.printStackTrace();
						}
					
					
			}
			
		else if(action.equalsIgnoreCase("pdfpayslip") && type1.equalsIgnoreCase("old"))
		{


			String date = request.getParameter("date");
			String empList = request.getParameter("list");
			String imagepath =getServletContext().getRealPath("/images/plogo.png");
			String concat = ReportDAO.concation();
			String filePath = getServletContext().getRealPath("")+ File.separator + "PaySlip_"+concat+"_"+date+".pdf";
			File file = new File(filePath);
			if (file.exists()) 
			{
				file.delete();
			}
			UtilityDAO.PaySlipPDF(date, empList, filePath,imagepath);
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.write("<iframe scrolling=\"auto\" src=\"PaySlip_"+concat+"_"+date+".pdf\" height=\"500px\" width=\"100%\"></iframe>");
		
		}
		else if (action.equalsIgnoreCase("payreg")) 
		{
			String date = request.getParameter("date");
			String Desgn = request.getParameter("Desgn");
			String Branch = request.getParameter("Branch");
			String designation = request.getParameter("dewise");
			String report = request.getParameter("report");
			String salaryType=request.getParameter("salaryType");
			
			String filePath ="";
			
			String imagepath =getServletContext().getRealPath("/images/plogo.png");
			System.out.println("Branch "+Branch);
			
			/*String filePath = getServletContext().getRealPath("")+ File.separator + "PayReg_"+date.substring(3,6)+"_"+date.substring(7, 11)+".txt";*/
			if(report.equalsIgnoreCase("TXT"))
			{
				 filePath = getServletContext().getRealPath("")+ File.separator + "PayReg_"+date.substring(3,6)+"_"+date.substring(7, 11)+".txt";
			}
			else if(report.equalsIgnoreCase("PDF"))
			{
				 filePath = getServletContext().getRealPath("")+ File.separator + "PayReg_"+date.substring(3,6)+"_"+date.substring(7, 11)+".pdf";
			}
			else
			{
				filePath = getServletContext().getRealPath("")+ File.separator + "PayReg_"+date.substring(3,6)+"_"+date.substring(7, 11)+".xls";
			}
			//String filePath = getServletContext().getRealPath("")+ File.separator + "PayReg.txt";
			File file = new File(filePath);
			if (file.exists()) 
			{
				file.delete();
			}
			
			if(report.equalsIgnoreCase("EXCEL"))
			{
				System.out.println("i am in Excel");
				ExcelDAO.newpayregExcelG(date,imagepath, filePath,salaryType,Branch,Desgn,designation);
			}
			else if(report.equalsIgnoreCase("PDF"))
			{
				System.out.println("i am in pdf");
				UtilityDAO.newpayregPDFG(date,imagepath, filePath,salaryType,Branch,Desgn,designation);
			}
			else
			{		
			
			UtilityDAO.payreg(date,Desgn, filePath,Branch,designation);    // without branch total
			}
			
			
			
		
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			
			if(report.equalsIgnoreCase("EXCEL")){
				out.write("<iframe scrolling=\"auto\" src=\"PayReg_"+date.substring(3,6)+"_"+date.substring(7, 11)+".xls\" height=\"400px\" width=\"100%\"></iframe>");
			}
			else if(report.equalsIgnoreCase("PDF"))
			{
				out.write("<iframe scrolling=\"auto\" src=\"PayReg_"+date.substring(3,6)+"_"+date.substring(7, 11)+".pdf\" height=\"400px\" width=\"100%\"></iframe>");
			}
			else{
				out.write("<iframe scrolling=\"auto\" src=\"PayReg_"+date.substring(3,6)+"_"+date.substring(7, 11)+".txt\" height=\"400px\" width=\"100%\"></iframe>");
			}
			}
		 	
		 	
		else if (action.equalsIgnoreCase("payregBranchWise")) 
		{  
			
			String date = request.getParameter("date");
			System.out.println("dfsafsaf"+date.substring(3,11));
			String table = request.getParameter("table");
			//G new 
			String report=request.getParameter("report");
			
			/*String filePath = getServletContext().getRealPath("")+ File.separator + "PayRegBranchWise_"+date.substring(3,11)+".txt";*/
			
			//newG
			String imagepath =getServletContext().getRealPath("/images/plogo.png");
			String filePath = null;
			
			System.out.println("G Report type"+report);
			if(report.equalsIgnoreCase("TXT")){
				filePath = getServletContext().getRealPath("")+ File.separator + "PayRegBranchWise_"+date.substring(3,11)+".txt";
			}else if(report.equalsIgnoreCase("PDF")){
				filePath = getServletContext().getRealPath("")+ File.separator + "PayRegBranchWise_"+date.substring(3,11)+".pdf";
			}else{
				filePath = getServletContext().getRealPath("")+ File.separator + "PayRegBranchWise_"+date.substring(3,11)+".xls";
			}
			
			
			File file = new File(filePath);
			
			if (file.exists()) 
			{
				file.delete();
			}
	
			/*UtilityDAO.payregWithBranch(date,table, filePath);*/
			
			if(report.equalsIgnoreCase("TXT")){
				UtilityDAO.payregWithBranch(date,table, filePath);
			}else if(report.equalsIgnoreCase("PDF")){
				UtilityDAO.payregWithBranchPdf(date,table, filePath, imagepath);
			}else{
				ExcelDAO.payregWithBranchXls(date,imagepath, filePath,table);
			}
			
			
			
			/*String filePath1 = getServletContext().getRealPath("")+ File.separator + "PayReg.pdf";
			try
			{
				Document doc  = new Document();
				PdfWriter.getInstance(doc, new FileOutputStream(filePath1));
				BufferedReader inp = new BufferedReader(new FileReader(filePath));
				String line;
				doc.setPageSize(PageSize.A2);
				
				doc.open();
				while((line = inp.readLine())!=null)
				{
					Paragraph ph = new Paragraph(line,new Font(Font.COURIER,8));
					ph.setAlignment(Element.ALIGN_LEFT);
					doc.add(ph);
					
				}
				doc.close();
				inp.close();
			}
			catch ( Exception e) 
			{
				e.printStackTrace();
			}*/
			/*PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.write("<iframe scrolling=\"auto\" src=\"PayRegBranchWise_"+date.substring(3,11)+".txt\" height=\"400px\" width=\"100%\"></iframe>");*/
			
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			if(report.equalsIgnoreCase("TXT")){
				
				System.out.println("G Report 1"+report);
				out.write("<iframe scrolling=\"auto\" src=\"PayRegBranchWise_"+date.substring(3,11)+".txt\" height=\"400px\" width=\"100%\"></iframe>");
			}else if(report.equalsIgnoreCase("PDF")){
				
				System.out.println("G Report 2"+report);
				out.write("<iframe scrolling=\"auto\" src=\"PayRegBranchWise_"+date.substring(3,11)+".pdf\" height=\"400px\" width=\"100%\"></iframe>");
			}else{
				
				System.out.println("G Report 3"+report);
				out.write("<iframe scrolling=\"auto\" src=\"PayRegBranchWise_"+date.substring(3,11)+".xls\" height=\"400px\" width=\"100%\"></iframe>");
			}
		}	
		 	
		if (action.equalsIgnoreCase("txtpayslip")) 
		{
			String date = request.getParameter("date");
			String EMPNOLIST = request.getParameter("list");
			
			String filePath = getServletContext().getRealPath("")+ File.separator + "PaySlip.txt";
			//UtilityDAO.paysliptxt(date, EMPNOLIST, filePath); //for previous format
			UtilityDAO.paysliptxtnew(date, EMPNOLIST, filePath);  // for new format			
					
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.write("<iframe scrolling=\"auto\" src=\"PaySlip.txt\" height=\"400px\" width=\"100%\"></iframe>");
			
		}
		else if(action.equalsIgnoreCase("NonContributeServ"))
		{
			
			
			System.out.println("  in no NonContributeServ");
				String date = request.getParameter("date");
				
				String empno = request.getParameter("empno");
				
				String Empno[]=empno.split(":");
				
			System.out.println("empno ::"+empno);
				String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
				String filePath = getServletContext().getRealPath("")+ File.separator + "NoContribute.pdf";
				//String filePath = getServletContext().getRealPath("")+ File.separator + "PayReg.txt";
				File file = new File(filePath);
				if (file.exists()) 
				{
					file.delete();
				}
				NonContribute.nonContribute(date,filePath,imagepath,Empno[2]);    // without branch total
				//UtilityDAO.payregWithBranchTotal(date,Desgn, filePath);
				
			
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				out.write("<iframe scrolling=\"auto\" src=\"NoContribute.pdf\" height=\"400px\" width=\"100%\"></iframe>");
		}
		
		else if (action.equalsIgnoreCase("societyEmpList")) 
		{
			String date = request.getParameter("date");
			date="01-"+date;
			String reporttype = request.getParameter("reporttype");
			
			if(reporttype.equalsIgnoreCase("textfile"))
			{
				String filePath = getServletContext().getRealPath("")+ File.separator + "SocietyEmployeeList.pdf";
				
				UtilityDAO.SocietyEmpListPDF(date, filePath);
				
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
		}
		
		
		
		else if (action.equalsIgnoreCase("TransferRegionWise"))
		{
			String date = request.getParameter("date");
			date="01-"+date;
			
			String reporttype = request.getParameter("reporttype");
			String reporttyp = request.getParameter("reporttyp");
			System.out.println("reporttyp : "+reporttyp);
			String EMPNO = request.getParameter("EMPNO");
			String valuetype = "";
			String[] parts = EMPNO.split(":");
			//String empnumber = parts[2];
			String Branch = request.getParameter("Branch");
			String design = request.getParameter("design");
			
			if(reporttyp.equalsIgnoreCase("1")){
				valuetype = "0";	
			}
			else if(reporttyp.equalsIgnoreCase("2")){
				valuetype = "ET.EMPNO="+parts[2];	
			}
			else if(reporttyp.equalsIgnoreCase("3")){
				valuetype = "ET.PRJ_SRNO="+Branch;	
			}
			else if(reporttyp.equalsIgnoreCase("4")){
				valuetype = "ET.DESIG="+design;	
			}
			
			System.out.println("valuetype : "+valuetype);
			
			String table_name=request.getParameter("before")==null?"after":"before";
			if((reporttype.equalsIgnoreCase("excelfile")))
			{
				String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
				String filepath=getServletContext().getRealPath("")+ File.separator + "TransferRegionWise.xls";
			 //ExcelDAO.TransferRegionwiseReportexcel(date,reporttype, filepath,imagepath);
				ExcelDAO.TransferRegionwiseReportexcel(date,reporttype, filepath,imagepath,valuetype);
			 try
				{
				 
				
					final int BUFSIZE = 4096;
					File file = new File(filepath);
					int length = 0;
					ServletOutputStream outStream = response.getOutputStream();
					ServletContext context = getServletConfig().getServletContext();
					String mimetype = context.getMimeType(filepath);
					if (mimetype == null) 
					{
						mimetype = "application/octet-stream";
					}
					response.setContentType(mimetype);
					response.setContentLength((int) file.length());
					String fileName = (new File(filepath)).getName();
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
				catch ( Exception e) 
				{
					e.printStackTrace();
				}
			}
}



		else if (action.equalsIgnoreCase("pflist")) 
		{
			System.out.println("in pf list");
			String date = request.getParameter("date");
			date="01-"+date;
			
			String reporttype = request.getParameter("reporttype");
			//System.out.println("reporttype"+reporttype);
			String table_name=request.getParameter("before")==null?"after":"before";
			if(reporttype.equalsIgnoreCase("textfile"))
			{
				System.out.println("Report type is textfile...");
				String filePath = getServletContext().getRealPath("")+ File.separator + "PFLIST.pdf";
				UtilityDAO.PFLISTPDF(date, filePath,table_name);
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
			/*if(reporttype.equalsIgnoreCase("excelfile"))
			{
				try
				{
					Date date1 = new Date() ;
					SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
					String dt = format.format(date1);
					
					SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd");
					fromformat.setLenient(false);
					HSSFWorkbook hwb=new HSSFWorkbook();
					HSSFSheet sheet =  hwb.createSheet("PFLIST");
					
					HSSFRow rowtitle=   sheet.createRow((short)0);
					rowtitle.createCell((short) 0).setCellValue("THE NASIK MARCHANT'S CO-OP BANK LTD. PFLIST : "+date);
					//rowtitle.createCell((short) 1).setCellValue("PFLIST : "+rdt);
					HSSFFont blueFont = hwb.createFont();
					blueFont.setColor(HSSFColor.BLUE.index);
					
					HSSFCellStyle style = hwb.createCellStyle();
					//style.setFont(blueFont);
					style.setFillForegroundColor(HSSFColor.BLUE.index);
					
					HSSFRow rowhead=   sheet.createRow((short)1);
					rowhead.createCell((short) 0).setCellValue("Branch");
					rowhead.createCell((short) 1).setCellValue("Employee No");
					rowhead.createCell((short) 2).setCellValue("Name");
					rowhead.createCell((short) 3).setCellValue("PFNO");
					rowhead.createCell((short) 4).setCellValue("Basic");
					rowhead.createCell((short) 5).setCellValue("DA");
					rowhead.createCell((short) 6).setCellValue("Gross");
					rowhead.createCell((short) 7).setCellValue("PF");
					rowhead.createCell((short) 8).setCellValue("@8.33%");
					rowhead.createCell((short) 9).setCellValue("@3.67%");
							
					//System.out.println("CAlling arraylist!");
					
					ArrayList<PFExcelBean> pflist = (ArrayList<PFExcelBean>) (UtilityDAO.PFExcel(date)==null?"":UtilityDAO.PFExcel(date));
					
				
					
					String branch="";
					double Basictotal = 0;
					double Grosstotal = 0;
					double PFtotal = 0;
					double DAtotal = 0;
					double Gr8total = 0;
					double Gr3total = 0;
					int i=2;
					
					for (PFExcelBean pfExcelBean : pflist) 
					{
						
						HSSFRow row = sheet.createRow((short)i);
						if(i==2)
						{
							branch=pfExcelBean.getBRANCH();
						}
						if(branch.equalsIgnoreCase(pfExcelBean.getBRANCH()))
						{
							row.createCell((short) 0).setCellValue(pfExcelBean.getBRANCH());
							row.createCell((short) 1).setCellValue(pfExcelBean.getEMPNo());
							row.createCell((short) 2).setCellValue(pfExcelBean.getEMPNAME());
							row.createCell((short) 3).setCellValue(pfExcelBean.getPFNO());
							row.createCell((short) 4).setCellValue(pfExcelBean.getBASIC());
							row.createCell((short) 5).setCellValue(pfExcelBean.getDA());
							row.createCell((short) 6).setCellValue(pfExcelBean.getGROSS());
							row.createCell((short) 7).setCellValue(pfExcelBean.getPF());
							row.createCell((short) 8).setCellValue(pfExcelBean.getGROSS8());
							row.createCell((short) 9).setCellValue(pfExcelBean.getGROSS3());
							branch=pfExcelBean.getBRANCH();
						}
						else
						{
							row.createCell((short) 0).setCellValue(pfExcelBean.getBRANCHTOTAL());
							row.createCell((short) 1).setCellValue("");
							row.createCell((short) 2).setCellValue("");
							row.createCell((short) 3).setCellValue("");
							row.createCell((short) 4).setCellValue(pfExcelBean.getBASICTOTAL());
							Basictotal= Basictotal+ Double.parseDouble( pfExcelBean.getBASICTOTAL());
							row.createCell((short) 5).setCellValue(pfExcelBean.getDATOTAL());
							DAtotal=DAtotal+ Double.parseDouble( pfExcelBean.getDATOTAL());
							row.createCell((short) 6).setCellValue(pfExcelBean.getGROSSTOTAL());
							Grosstotal=Grosstotal+ Double.parseDouble( pfExcelBean.getGROSSTOTAL());
							row.createCell((short) 7).setCellValue(pfExcelBean.getPFTOTAL());
							PFtotal=PFtotal+ Double.parseDouble( pfExcelBean.getPFTOTAL());
							row.createCell((short) 8).setCellValue(pfExcelBean.getGR8TOTAL());
							Gr8total=Gr8total+ Double.parseDouble( pfExcelBean.getGR8TOTAL());
							row.createCell((short) 9).setCellValue(pfExcelBean.getGR3TOTAL());
							Gr3total=Gr3total+ Double.parseDouble( pfExcelBean.getGR3TOTAL());
							
							branch=pfExcelBean.getBRANCH();
						}
						i++;
					}
					HSSFRow rowTotal = sheet.createRow((short)i);
					rowTotal.createCell((short) 0).setCellValue("ALL BRANCH TOTAL");
					rowTotal.createCell((short) 1).setCellValue("");
					rowTotal.createCell((short) 2).setCellValue("");
					rowTotal.createCell((short) 3).setCellValue("");
					rowTotal.createCell((short) 4).setCellValue(Basictotal);
					rowTotal.createCell((short) 5).setCellValue(DAtotal);
					rowTotal.createCell((short) 6).setCellValue(Grosstotal);
					rowTotal.createCell((short) 7).setCellValue(PFtotal);
					rowTotal.createCell((short) 8).setCellValue(Gr8total);
					rowTotal.createCell((short) 9).setCellValue(Gr3total);
					
					String filePath1 = getServletContext().getRealPath("")+ File.separator + "Namco_PFList.xls";
					
					FileOutputStream fileOut =  new FileOutputStream(filePath1);//"D:\\PFList.xls"
					hwb.write(fileOut);
					fileOut.close();
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
					System.out.println("Your excel file has been generated!");
				} 
				catch (Exception ex ) 
				{
					ex.printStackTrace();
				}
			}*/
			
			if((reporttype.equalsIgnoreCase("excelfile")))
			{
				
				String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.jpg");
				String filepath=getServletContext().getRealPath("")+ File.separator + "PF_List.xls";
			 ExcelDAO.pflistexcel(date,reporttype, filepath,imagepath);
			 try
				{
				
					final int BUFSIZE = 4096;
					File file = new File(filepath);
					int length = 0;
					ServletOutputStream outStream = response.getOutputStream();
					ServletContext context = getServletConfig().getServletContext();
					String mimetype = context.getMimeType(filepath);
					if (mimetype == null) 
					{
						mimetype = "application/octet-stream";
					}
					response.setContentType(mimetype);
					response.setContentLength((int) file.length());
					String fileName = (new File(filepath)).getName();
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
				catch ( Exception e) 
				{
					e.printStackTrace();
				}
			}
			
			if (reporttype.equalsIgnoreCase("ecs")) 
			{
				//System.out.println("Report type is ecs...");
				String filePath = getServletContext().getRealPath("")+ File.separator + "PF_List.txt";
				//UtilityDAO.paysliptxt(date, EMPNOLIST, filePath); //for previous format
				UtilityDAO.PFlisttxtnew(date,reporttype, filePath);  // for new format			
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
			
		}
		else if(action.equalsIgnoreCase("ecr")){
			//System.out.println("Report type is ecr...");
			
			String date = request.getParameter("date");
			date="01-"+date;
			String []dt = date.split("-");
			String reporttype = request.getParameter("reporttype");
			String table_name=request.getParameter("before")==null?"after":"before";
			if(reporttype.equalsIgnoreCase("pdffile")){
				
				final int BUFSIZE = 4096;
				String filePath = getServletContext().getRealPath("")+ File.separator + "ECR-Report_"+dt[1]+""+dt[2]+".pdf";

				try {
					UtilityDAO.EcrReport(date,filePath,table_name);
					
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
				}
			}
			if(reporttype.equalsIgnoreCase("excelfile"))
			{
				try
				{
					if(table_name.equalsIgnoreCase("before")){
						table_name="PAYTRAN";
					}
					else
					{
						table_name="PAYTRAN_STAGE";
					}
					Connection Cn = null;
					ResultSet emp = null;
					String EmpSql = "";
					int epf_total = 0;
					int eps_total = 0;
					int epf_due_total = 0;
					int eps_due_total = 0;
					int diff_due_total = 0;
					int ncp_total = 0;
					int refund_total = 0;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
					SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
					SimpleDateFormat fromformat = new SimpleDateFormat("MM/yyyy");
					fromformat.setLenient(false);
					RepoartBean repBean  = new RepoartBean();
					HSSFWorkbook hwb=new HSSFWorkbook();
					HSSFSheet sheet =  hwb.createSheet("ECR Report");
					Calendar currentMonth = Calendar.getInstance();
			        currentMonth.setTime(format.parse(date));
			        currentMonth.add(Calendar.MONTH, 1);
			      
	                HSSFCellStyle my_style = hwb.createCellStyle();
	                HSSFFont my_font=hwb.createFont();
	                my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	                my_style.setFont(my_font);
	                
					HSSFRow rowtitle=   sheet.createRow((short)0);
					HSSFCell cell = rowtitle.createCell((short) 5);
					cell.setCellValue("EMPLOYEE'S PROVIDENT FUND ORGANISATION, NASHIK");
					cell.setCellStyle(my_style);
					HSSFRow rowtitle1=   sheet.createRow((short)1);
					HSSFCell cell1 = rowtitle1.createCell((short) 5);
					cell1.setCellValue("ELECTRONIC CHALLAN CUM RETURN (ECR)");
					cell1.setCellStyle(my_style);
					HSSFRow rowtitle2=   sheet.createRow((short)2);
					HSSFCell cell2 = rowtitle2.createCell((short) 5);
					cell2.setCellValue("FOR THE WAGE MONTH OF ("+fromformat.format(format.parse(date))+") AND RETURN MONTH ("+fromformat.format(currentMonth.getTime())+")");
					cell2.setCellStyle(my_style);
					HSSFRow rowtitle31=   sheet.createRow((short)3);
					rowtitle31.createCell((short) 0).setCellValue("");
					HSSFRow rowtitle3=   sheet.createRow((short)4);
					rowtitle3.createCell((short) 0).setCellValue("ESTABLISHMENT ID              : ");
					HSSFRow rowtitle4=   sheet.createRow((short)5);
					rowtitle4.createCell((short) 0).setCellValue("NAME OF ESTABLISHMENT  : THE BUSINESS CO.OP BANK LTD.");
					HSSFRow rowtitle5=   sheet.createRow((short)6);
					rowtitle5.createCell((short) 0).setCellValue("TRRN                                    :");
					
					HSSFFont blueFont = hwb.createFont();
					blueFont.setColor(HSSFColor.BLUE.index);
					
					HSSFCellStyle style = hwb.createCellStyle();
					//style.setFont(blueFont);
					style.setFillForegroundColor(HSSFColor.BLUE.index);
					
					HSSFRow head=   sheet.createRow((short)7);
					head.createCell((short) 0).setCellValue("");
					HSSFRow heading=   sheet.createRow((short)8);
					HSSFCell cell3 = heading.createCell((short) 0); 
					cell3.setCellValue("PART A-MEMBER'S WAGE DETAILS");
					cell3.setCellStyle(my_style);
					HSSFRow head1=   sheet.createRow((short)9);
					head1.createCell((short) 0).setCellValue("");
					HSSFRow rowhead=   sheet.createRow((short)10);
					rowhead.createCell((short) 0).setCellValue("Sr No.");
					rowhead.createCell((short) 1).setCellValue("Member Id");
					rowhead.createCell((short) 3).setCellValue("Member Name");
					rowhead.createCell((short) 6).setCellValue("EPF Wages");
					rowhead.createCell((short) 7).setCellValue("EPS Wages");
					rowhead.createCell((short) 8).setCellValue("EPF Contribution (EE Share) due");
					rowhead.createCell((short) 9).setCellValue("EPF Contribution (EE Share) being remitted");
					rowhead.createCell((short) 10).setCellValue("EPS Contribution (EE Share) due");
					rowhead.createCell((short) 11).setCellValue("EPS Contribution (EE Share) being remitted");
					rowhead.createCell((short) 12).setCellValue("Diff EPF and EPS Contribution (ER Share) due");
					rowhead.createCell((short) 13).setCellValue("Diff EPF and EPS Contribution (ER Share) being remitted");
					rowhead.createCell((short) 14).setCellValue("NCP Days");
					rowhead.createCell((short) 15).setCellValue("Refund of Advances");
					
					ReportDAO.OpenCon("", "", "",repBean);
					Cn = repBean.getCn();
					
					EmpSql = "select e.salute ,e.doj,e.pfno, e.empno,e.empcode,e.fname,e.mname,e.lname, " +
							"(select cal_amt from "+table_name+" where trncd=136 and empno=e.empno and trndt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"') basic," +
							"(select cal_amt from "+table_name+" where trncd=137 and empno=e.empno and trndt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"') EPSWAGES," +
							"(select cal_amt from "+table_name+" where trncd=102 and empno=e.empno and trndt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"') da, " +
							"(select cal_amt from "+table_name+" where trncd=201 and empno=e.empno and trndt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"') PF, " +
							"(select cal_amt from "+table_name+" where trncd=231 and empno=e.empno and trndt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"') EEPF, " +
							"(select cal_amt from "+table_name+" where trncd=232 and empno=e.empno and trndt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"') EEPS, " +
							"(select cal_amt from "+table_name+" where trncd=233 and empno=e.empno and trndt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"') EDLI, " +
							"(select cal_amt from "+table_name+" where trncd=234 and empno=e.empno and trndt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"') EEPFADMIN, " +
							"(select cal_amt from "+table_name+" where trncd=235 and empno=e.empno and trndt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"') EDLIADMIN " +
							"from empmast e ,SAL_DETAILS s where s.EMPNO = e.EMPNO  and s.SAL_MONTH = '"+dt[1]+"-"+dt[2]+"' and e.doj <='"+ReportDAO.EOM(date)+"' " +
									"and  e.EMPNO in(select distinct EMPNO from "+table_name+" where TRNCD=201) order by e.empno";
					Statement st;
					st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					emp = st.executeQuery(EmpSql);
					if(!emp.next()){
						HSSFRow row = sheet.createRow((short)12);
						HSSFCell cell11 = row.createCell((short) 5);
						cell11.setCellValue("Salary hasn't been finalized for the month "+dt[1]+"-"+dt[2]);
						cell11.setCellStyle(my_style);
					} else {
					int i=11;
					int count = 1;
					emp.previous();
					float ac22=0,ac21=0,ac10=0,ac02=0;
					while(emp.next()){
						
						HSSFRow row = sheet.createRow((short)i++);
						if(emp.getInt("basic")<=0)
						{
						emp.next();
						}
						
						row.createCell((short) 0).setCellValue(""+count++);
						int epf_wages = emp.getInt("basic")+emp.getInt("da");
						int eps_wages = emp.getInt("EPSWAGES");
						ac02+=Math.round(emp.getFloat("EEPFADMIN"));
						ac10+=Math.round(emp.getFloat("EEPS"));
						ac22+=Math.round(emp.getFloat("EDLIADMIN"));
						ac21+=Math.round(emp.getFloat("EDLI"));
						
						String []pfno = null;
						if(!emp.getString("pfno").equals("") ) {
							//pfno = emp.getString("pfno").split("/");
							row.createCell((short) 1).setCellValue(""+String.format("%06d" , emp.getInt("pfno")));
						} else {
							row.createCell((short) 1).setCellValue("");
						}
						if(emp.getString("MNAME").equals(null) || emp.getString("MNAME").equals(""))
						{
							row.createCell((short) 2).setCellValue(emp.getString("FNAME")+" "+emp.getString("MNAME")+" "+emp.getString("LNAME"));
						}
						else
						{
							row.createCell((short) 2).setCellValue(emp.getString("FNAME")+" "+emp.getString("MNAME")+" "+emp.getString("LNAME"));
						}
												
						row.createCell((short) 6).setCellValue(epf_wages);
						epf_total += epf_wages;
						
						float eps_cont = 0;
						row.createCell((short) 7).setCellValue(eps_wages);
							eps_total += eps_wages;
							eps_cont = Math.round(emp.getInt("EEPS"));
						
						float epf_cont = Math.round(emp.getInt("PF"));
						epf_due_total += epf_cont;
						
						row.createCell((short) 8).setCellValue(epf_cont);
						row.createCell((short) 9).setCellValue(epf_cont);
						eps_due_total += eps_cont;
						row.createCell((short) 10).setCellValue(eps_cont);
						row.createCell((short) 11).setCellValue(eps_cont);
						diff_due_total += epf_cont-eps_cont;
						row.createCell((short) 12).setCellValue(epf_cont-eps_cont);
						row.createCell((short) 13).setCellValue(epf_cont-eps_cont);
						row.createCell((short) 14).setCellValue(0);
						row.createCell((short) 15).setCellValue(0);
						
					}
					
					HSSFRow rowTotal = sheet.createRow((short)i);
					rowTotal.createCell((short) 0).setCellValue("");
					rowTotal.createCell((short) 1).setCellValue("");
					rowTotal.createCell((short) 2).setCellValue("");
					rowTotal.createCell((short) 3).setCellValue("");
					HSSFCell cell4 = rowTotal.createCell((short) 4); 
					cell4.setCellValue("GRAND TOTAL :");
					cell4.setCellStyle(my_style);
					rowTotal.createCell((short) 6).setCellValue(epf_total);
					rowTotal.createCell((short) 7).setCellValue(eps_total);
					rowTotal.createCell((short) 8).setCellValue(epf_due_total);
					rowTotal.createCell((short) 9).setCellValue(epf_due_total);
					rowTotal.createCell((short) 10).setCellValue(eps_due_total);
					rowTotal.createCell((short) 11).setCellValue(eps_due_total);
					rowTotal.createCell((short) 12).setCellValue(diff_due_total);
					rowTotal.createCell((short) 13).setCellValue(diff_due_total);
					rowTotal.createCell((short) 14).setCellValue(ncp_total);
					rowTotal.createCell((short) 15).setCellValue(refund_total);
					
					HSSFRow Totals = sheet.createRow((short)i+3);
					Totals.createCell((short) 0).setCellValue("");
					Totals.createCell((short) 4).setCellValue("A/C 01 EE + Refund of Advance");
					Totals.createCell((short) 7).setCellValue("A/C 01 ER");
					Totals.createCell((short) 8).setCellValue("A/C 02");
					Totals.createCell((short) 9).setCellValue("A/C 10");
					Totals.createCell((short) 10).setCellValue("A/C 21");
					Totals.createCell((short) 11).setCellValue("A/C 22");
					Totals.createCell((short) 12).setCellValue("TOTAL");
					for(int j=4; j<6; j++){
						HSSFRow Total1 = sheet.createRow((short)i+j);
						HSSFCell cell5 = Total1.createCell((short) 0); 
						if(j==4){
							cell5.setCellValue("TOTAL DUES AS PER ECR");
						} else {
							cell5.setCellValue("TOTAL AMOUNT BEING REMITTED");
						}
						cell5.setCellStyle(my_style);
						Total1.createCell((short) 6).setCellValue(epf_due_total);
						Total1.createCell((short) 7).setCellValue(diff_due_total);
						
						Total1.createCell((short) 8).setCellValue((int)ac02);
						Total1.createCell((short) 9).setCellValue(eps_due_total);
						
						Total1.createCell((short) 10).setCellValue((int)ac21);
						
						Total1.createCell((short) 11).setCellValue((int)ac22);
						float total = epf_due_total+diff_due_total+ac02+eps_due_total+ac21+ac22;
						Total1.createCell((short) 12).setCellValue((int)total);
					}
					
					String EmpSql1 = "select e.salute ,e.dob,e.gender,e.pfno, e.empno,e.empcode,e.fname,e.mname,e.lname " +
							"from empmast e where e.status = 'A' and e.doj between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' order by e.empno";
					Statement st1;
					st1 = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					ResultSet emp1 = st1.executeQuery(EmpSql1);
					if(!emp1.next()){
						System.out.println("No Any Record Found");
					}
					HSSFRow heading2=   sheet.createRow((short)i+8);
					HSSFCell cell6 = heading2.createCell((short) 0); 
					cell6.setCellValue("PART B-NEW MEMBER'S DETAILS");
					cell6.setCellStyle(my_style);
					HSSFRow rowhead2=   sheet.createRow((short)i+10);
					rowhead2.createCell((short) 0).setCellValue("Sr No.");
					rowhead2.createCell((short) 1).setCellValue("Member Id");
					rowhead2.createCell((short) 3).setCellValue("Member Name");
					rowhead2.createCell((short) 6).setCellValue("Father's/Spouse Name");
					rowhead2.createCell((short) 8).setCellValue("Relationship with the Member");
					rowhead2.createCell((short) 9).setCellValue("Date of Birth");
					rowhead2.createCell((short) 10).setCellValue("Gender");
					rowhead2.createCell((short) 11).setCellValue("Date of Joining EPF");
					rowhead2.createCell((short) 13).setCellValue("Date of Joining EPS");
					int count1 = 1;
					i=i+11;
					emp1.previous();
										
					while(emp1.next()){
						
						HSSFRow row = sheet.createRow((short)i++);
						
						row.createCell((short) 0).setCellValue(""+count1++);
				
						String []pfno = null;
						if(!emp1.getString("pfno").equals("") ) {
							pfno = emp1.getString("pfno").split("/");
							row.createCell((short) 1).setCellValue("000"+pfno[3]);
						} else {
							row.createCell((short) 1).setCellValue("");
						}
						if(emp1.getString("MNAME").equals(null) || emp1.getString("MNAME").equals(""))
						{
							row.createCell((short) 2).setCellValue(emp1.getString("FNAME")+" "+emp1.getString("MNAME")+" "+emp1.getString("LNAME"));
						}
						else
						{
							row.createCell((short) 2).setCellValue(emp1.getString("FNAME")+" "+emp1.getString("MNAME")+" "+emp1.getString("LNAME"));
						}
												
						row.createCell((short) 6).setCellValue(emp1.getString("MNAME"));
						if(!emp1.getString("MNAME").equals("")) {
							row.createCell((short) 8).setCellValue("Father");
						} else {	
							row.createCell((short) 8).setCellValue("");
						}
						Date dob = sdf.parse(emp1.getString("dob"));
						row.createCell((short) 9).setCellValue(format1.format(dob));
						if(emp1.getString("gender").contains("M")){
							row.createCell((short) 10).setCellValue("Male");
						} else {
							row.createCell((short) 10).setCellValue("Female");
						}
						Date jdate = format.parse(ReportDAO.BOM(date));						
						row.createCell((short) 11).setCellValue(format1.format(jdate));
						row.createCell((short) 13).setCellValue(format1.format(jdate));
						
					}
					
					String EmpSql2 = "select e.salute ,e.dob,e.dol,e.gender,e.pfno, e.empno,e.empcode,e.fname,e.mname,e.lname " +
							"from empmast e where e.dol between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' order by e.empno";
					System.out.println("Vrushali...."+EmpSql2);
					Statement st2;
					st2 = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					ResultSet emp2 = st2.executeQuery(EmpSql2);
					if(!emp2.next()){
						System.out.println("No Any Record Found");
					}
					HSSFRow heading3=   sheet.createRow((short)i+2);
					HSSFCell cell7 = heading3.createCell((short) 0); 
					cell7.setCellValue("PART C-EXITING MEMBER'S DETAILS");
					cell7.setCellStyle(my_style);
					HSSFRow rowhead3=   sheet.createRow((short)i+4);
					rowhead3.createCell((short) 0).setCellValue("Sr No.");
					rowhead3.createCell((short) 1).setCellValue("Member Id");
					rowhead3.createCell((short) 3).setCellValue("Member Name");
					rowhead3.createCell((short) 6).setCellValue("Father's/Spouse Name");
					rowhead3.createCell((short) 8).setCellValue("Relationship with the Member");
					rowhead3.createCell((short) 9).setCellValue("Date of Birth");
					rowhead3.createCell((short) 10).setCellValue("Gender");
					rowhead3.createCell((short) 11).setCellValue("Date of Exit from EPF");
					rowhead3.createCell((short) 13).setCellValue("Date of Exit from EPS");
					rowhead3.createCell((short) 15).setCellValue("Reason for leaving");
					int count2 = 1;
					i=i+5;
					emp2.previous();
										
					while(emp2.next()){
						
						HSSFRow row = sheet.createRow((short)i++);
						
						row.createCell((short) 0).setCellValue(""+count2++);
				
						String []pfno = null;
						if(!emp2.getString("pfno").equals("") ) {
							pfno = emp2.getString("pfno").split("/");
							row.createCell((short) 1).setCellValue("000"+pfno[3]);
						} else {
							row.createCell((short) 1).setCellValue("");
						}
						if(emp2.getString("MNAME").equals(null) || emp2.getString("MNAME").equals(""))
						{
							row.createCell((short) 2).setCellValue(emp2.getString("FNAME")+" "+emp2.getString("MNAME")+" "+emp2.getString("LNAME"));
						}
						else
						{
							row.createCell((short) 2).setCellValue(emp2.getString("FNAME")+" "+emp2.getString("MNAME")+" "+emp2.getString("LNAME"));
						}
												
						row.createCell((short) 6).setCellValue(emp2.getString("MNAME"));
						if(!emp2.getString("MNAME").equals("")) {
							row.createCell((short) 8).setCellValue("Father");
						} else {	
							row.createCell((short) 8).setCellValue("");
						}	
						Date dob = sdf.parse(emp2.getString("dob"));
						row.createCell((short) 9).setCellValue(format1.format(dob));
						if(emp2.getString("gender").contains("M")){
							row.createCell((short) 10).setCellValue("Male");
						} else {
							row.createCell((short) 10).setCellValue("Female");
						}
						Date ldate = sdf.parse(emp2.getString("dol"));						
						row.createCell((short) 11).setCellValue(format1.format(ldate));
						row.createCell((short) 13).setCellValue(format1.format(ldate));
						row.createCell((short) 15).setCellValue("");
						
					}
					
					HSSFRow heading4=   sheet.createRow((short)i+2);
					HSSFCell cell8 = heading4.createCell((short) 0); 
					cell8.setCellValue("PART D-MEMBER'S ARREAR DETAILS");
					cell8.setCellStyle(my_style);
					HSSFRow rowhead4=   sheet.createRow((short)i+4);
					rowhead4.createCell((short) 2).setCellValue("--- NIL ---");
					}					
					String filePath1 = getServletContext().getRealPath("")+ File.separator + "ECR-Report_"+dt[1]+""+dt[2]+".xls";
					FileOutputStream fileOut =  new FileOutputStream(filePath1);//"D:\\PFList.xls"
					hwb.write(fileOut);
					fileOut.close();
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
					//System.out.println("Your excel file has been generated!");
				} 
				catch (Exception ex ) 
				{
					ex.printStackTrace();
				}
			}
		}
		else if(action.equalsIgnoreCase("excelpf"))
		{
			//System.out.println("Report type is excelpf...");
			String rdt = request.getParameter("date");
			try
			{
				Date date1 = new Date() ;
				SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
				String dt = format.format(date1);
				
				SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd");
				fromformat.setLenient(false);
				HSSFWorkbook hwb=new HSSFWorkbook();
				HSSFSheet sheet =  hwb.createSheet("PFLIST");
					
				HSSFRow rowtitle=   sheet.createRow((short)0);
				rowtitle.createCell((short) 0).setCellValue("Namco Bank PFLIST : "+rdt);
				//rowtitle.createCell((short) 1).setCellValue("PFLIST : "+rdt);
				HSSFFont blueFont = hwb.createFont();
				blueFont.setColor(HSSFColor.BLUE.index);
				
				HSSFCellStyle style = hwb.createCellStyle();
				//style.setFont(blueFont);
				style.setFillForegroundColor(HSSFColor.BLUE.index);
					
				HSSFRow rowhead=   sheet.createRow((short)1);
				rowhead.createCell((short) 0).setCellValue("Branch");
				rowhead.createCell((short) 1).setCellValue("Employee No");
				rowhead.createCell((short) 2).setCellValue("Name");
				rowhead.createCell((short) 3).setCellValue("PFNO");
				rowhead.createCell((short) 4).setCellValue("Basic");
				rowhead.createCell((short) 5).setCellValue("DA");
				rowhead.createCell((short) 6).setCellValue("Gross");
				rowhead.createCell((short) 7).setCellValue("PF");
				rowhead.createCell((short) 8).setCellValue("@8.33%");
				rowhead.createCell((short) 9).setCellValue("@3.67%");
				
				
				
				ArrayList<PFExcelBean> pflist = UtilityDAO.PFExcel(rdt);
				
				
				String branch="";
				double Basictotal = 0;
				double Grosstotal = 0;
				double PFtotal = 0;
				double DAtotal = 0;
				double Gr8total = 0;
				double Gr3total = 0;
				int i=2;
					
				for (PFExcelBean pfExcelBean : pflist) 
				{
					HSSFRow row = sheet.createRow((short)i);
					if(i==2)
					{
						branch=pfExcelBean.getBRANCH();
					}
					if(branch.equalsIgnoreCase(pfExcelBean.getBRANCH()))
					{
						row.createCell((short) 0).setCellValue(pfExcelBean.getBRANCH());
						row.createCell((short) 1).setCellValue(pfExcelBean.getEMPNo());
						row.createCell((short) 2).setCellValue(pfExcelBean.getEMPNAME());
						row.createCell((short) 3).setCellValue(pfExcelBean.getPFNO());
						row.createCell((short) 4).setCellValue(pfExcelBean.getBASIC());
						row.createCell((short) 5).setCellValue(pfExcelBean.getDA());
						row.createCell((short) 6).setCellValue(pfExcelBean.getGROSS());
						row.createCell((short) 7).setCellValue(pfExcelBean.getPF());
						row.createCell((short) 8).setCellValue(pfExcelBean.getGROSS8());
						row.createCell((short) 9).setCellValue(pfExcelBean.getGROSS3());
						branch=pfExcelBean.getBRANCH();
					}
					else
					{
						row.createCell((short) 0).setCellValue(pfExcelBean.getBRANCHTOTAL());
						row.createCell((short) 1).setCellValue("");
						row.createCell((short) 2).setCellValue("");
						row.createCell((short) 3).setCellValue("");
						row.createCell((short) 4).setCellValue(pfExcelBean.getBASICTOTAL());
						Basictotal= Basictotal+ Double.parseDouble( pfExcelBean.getBASICTOTAL());
						row.createCell((short) 5).setCellValue(pfExcelBean.getDATOTAL());
						DAtotal=DAtotal+ Double.parseDouble( pfExcelBean.getDATOTAL());
						row.createCell((short) 6).setCellValue(pfExcelBean.getGROSSTOTAL());
						Grosstotal=Grosstotal+ Double.parseDouble( pfExcelBean.getGROSSTOTAL());
						row.createCell((short) 7).setCellValue(pfExcelBean.getPFTOTAL());
						PFtotal=PFtotal+ Double.parseDouble( pfExcelBean.getPFTOTAL());
						row.createCell((short) 8).setCellValue(pfExcelBean.getGR8TOTAL());
						Gr8total=Gr8total+ Double.parseDouble( pfExcelBean.getGR8TOTAL());
						row.createCell((short) 9).setCellValue(pfExcelBean.getGR3TOTAL());
						Gr3total=Gr3total+ Double.parseDouble( pfExcelBean.getGR3TOTAL());
						
						branch=pfExcelBean.getBRANCH();
					}
					i++;
				}
				HSSFRow rowTotal = sheet.createRow((short)i);
				rowTotal.createCell((short) 0).setCellValue("ALL BRANCH TOTAL");
				rowTotal.createCell((short) 1).setCellValue("");
				rowTotal.createCell((short) 2).setCellValue("");
				rowTotal.createCell((short) 3).setCellValue("");
				rowTotal.createCell((short) 4).setCellValue(Basictotal);
				rowTotal.createCell((short) 5).setCellValue(DAtotal);
				rowTotal.createCell((short) 6).setCellValue(Grosstotal);
				rowTotal.createCell((short) 7).setCellValue(PFtotal);
				rowTotal.createCell((short) 8).setCellValue(Gr8total);
				rowTotal.createCell((short) 9).setCellValue(Gr3total);
				
				String filePath = getServletContext().getRealPath("")+ File.separator + "Namco_Bank_PFList.xls";
				
				FileOutputStream fileOut =  new FileOutputStream(filePath);//"D:\\PFList.xls"
				hwb.write(fileOut);
				fileOut.close();
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
				System.out.println("Your excel file has been generated!");
				//request.getRequestDispatcher("pfList.jsp").forward(request, response);
				
			}
			catch (Exception ex ) 
			{
				ex.printStackTrace();
			}
		}
		
		// New Code for emplist by Harshal
				else if(action.equalsIgnoreCase("emplist")){
					//System.out.println("Report type is emplist...");
					String type = request.getParameter("type");
					String date ="";
					String date1 ="";
					if(type!="PromotionList"){
					 date = request.getParameter("date");
					 date="01-"+date;
					 date1 = request.getParameter("date1");
					 date1="01-"+date1;
					}
					String from_age="";
					String to_age="";
					String desig_type="";
					String desig_value1="";
					String desig_value2="";
					String branch_type="";
					String branch_value1="";
					String branch_value2="";
					
					String BranchType ="";
					String Per_Branch ="";
					String Br_rangeFrom ="";
					String Br_rangeTo ="";
					String FrmDate = "";
					String ToDate = "";
					String fromdate ="";
					String todate = "";
					String Category = "";
					String EmpBranch = "";
					String EmpDesig = "";
					String EmpType ="";
					
					SimpleDateFormat sdf;
					SimpleDateFormat output ;
					
					if(type.equalsIgnoreCase("Age"))
					{
						System.out.println("in the age...");
						 from_age	=	request.getParameter("agefrom");
						 to_age	=	request.getParameter("ageto");
						 desig_type=request.getParameter("desigtype");
						 branch_type=request.getParameter("branchtype");
						
						if(desig_type.equalsIgnoreCase("ALL"))
						{
							desig_value1="ALL";
							
						}else if(desig_type.equalsIgnoreCase("Specific")){
							desig_value1=request.getParameter("dewise_from");
						}else if(desig_type.equalsIgnoreCase("rangewise")){
							desig_value1=request.getParameter("dewise_from");
							desig_value2=request.getParameter("dewise_to");
						}
						System.out.println("from age--->"+from_age+".... to age---->"+to_age);
						System.out.println("desig_type..."+desig_type+"...desig_value1-->"+desig_value1+"....desig_value2--->"+desig_value2);
						if(branch_type.equalsIgnoreCase("ALL"))
						{
							branch_value1="ALL";
							
						}else if(branch_type.equalsIgnoreCase("specific")){
							branch_value1=request.getParameter("Branch_from");
						}else if(branch_type.equalsIgnoreCase("rangewise")){
							branch_value1=request.getParameter("Branch_from");
							branch_value2=request.getParameter("Branch_to");
						}
						System.out.println("branch_type..."+branch_type+"...branch_value1-->"+branch_value1+"....branch_value2--->"+branch_value2);

					}
					
					if(type.equalsIgnoreCase("PromotionList"))
				{
					BranchType =request.getParameter("branchtyp");
					Per_Branch = request.getParameter("Branch");
					Br_rangeFrom = request.getParameter("rangeFrom");
					Br_rangeTo = request.getParameter("rangeTo");
					
					 fromdate=request.getParameter("fromdate");
					 todate=request.getParameter("tdate");
					System.out.println("fromdate::  "+fromdate+"  todate:: "+todate);
				}
					if(type.equalsIgnoreCase("Category")){
					 Category = request.getParameter("category");
					}
					if(type.equalsIgnoreCase("Gender")){
						EmpBranch=request.getParameter("EmpBranch");
						EmpDesig=request.getParameter("dewise");
						EmpType=request.getParameter("Desigbranc");
					}
					String gender = request.getParameter("gender");
					String prj_srno = request.getParameter("Branch1");
					String desig = request.getParameter("dewise");
					String dept = request.getParameter("depwise");
					
					String filePath = getServletContext().getRealPath("")+ File.separator + "EmpList.txt";
					String filePath1 = getServletContext().getRealPath("")+ File.separator + "Emplist.pdf";
					String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
					
					if((type.equalsIgnoreCase("general")))
					{						
						try
						{
							System.out.println("hello ak this is general");
								String filepath=getServletContext().getRealPath("")+ File.separator + "Generalised_Emplist.xls";
								//Method Call for Pdf Report.....
								ExcelDAO.EMPLIST(date,date1, type, filepath,imagepath);
								final int BUFSIZE = 4096;
								File file = new File(filepath);
								int length = 0;
								ServletOutputStream outStream = response.getOutputStream();
								ServletContext context = getServletConfig().getServletContext();
								String mimetype = context.getMimeType(filepath);
								
								if (mimetype == null) 
								{
									mimetype = "application/octet-stream";
								}
								
								response.setContentType(mimetype);
								response.setContentLength((int) file.length());
								String fileName = (new File(filepath)).getName();
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
								
							}catch ( Exception e) 	{	e.printStackTrace();	}
					}
					else
					{				
						try
						{	
							//	Method Calls for Pdf Report by type selected .....
							if(type.equalsIgnoreCase("Age")){		
								ReportDAOPDF.AgeWiseEmployee(date, type, filePath1,imagepath,from_age,to_age,desig_type,desig_value1,desig_value2,branch_type,branch_value1,branch_value2);
							}
							else if(type.equalsIgnoreCase("Branch")){
								//ReportDAOPDF.BranchWise(date, type, filePath1,imagepath);
							}
							else if(type.equalsIgnoreCase("C")){  
							//	ReportDAOPDF.EmployeeListWise(date, type, filePath1,imagepath);
							}
							else if(type.equalsIgnoreCase("Designation")){ 
								ReportDAOPDF.DesignationWise(date, type, filePath1,imagepath,desig);
							}
							else if(type.equalsIgnoreCase("Doj")){  
									 fromdate=request.getParameter("fromdate");
									 todate=request.getParameter("tdate");
									System.out.println("fromdate::  "+fromdate+"  todate:: "+todate);
								
								ReportDAOPDF.DojWise(fromdate,todate, type, filePath1,imagepath);
							}
							else if(type.equalsIgnoreCase("Dol")){  
								ReportDAOPDF.DolWise(date, type, filePath1,imagepath);
							}
							else if(type.equalsIgnoreCase("Project")){  
								ReportDAOPDF.ProjectWise(date, type, filePath1,imagepath,prj_srno);
							}
							else if(type.equalsIgnoreCase("EmpSign")){  
								ReportDAOPDF.EmpSign(date, type, filePath1,imagepath,prj_srno);
							}
							else if(type.equalsIgnoreCase("Department")){  
								ReportDAOPDF.DepartmentWise(date, type, filePath1,imagepath,dept);
							}
							else if(type.equalsIgnoreCase("Grade")){  
								ReportDAOPDF.GradeWise(date, type, filePath1,imagepath);
							}
							else if(type.equalsIgnoreCase("Handicap")){  
								ReportDAOPDF.HandicapWise(date, type, filePath1,imagepath);
							}
							else if(type.equalsIgnoreCase("BloodGroup")){  
								ReportDAOPDF.BloodGroupWise(date, type, filePath1,imagepath);
							}
							else if(type.equalsIgnoreCase("Qualification")){  
								ReportDAOPDF.QualificationWise(date, type, filePath1,imagepath);
							}
							else if(type.equalsIgnoreCase("Category")){  
								ReportDAOPDF.CategoryWise(date, type,Category,filePath1,imagepath);
							}
							else if(type.equalsIgnoreCase("Gender")){  
								
								System.out.println("EmpDesig: "+EmpDesig+"  EmpBranch: "+EmpBranch+"  EmpType: "+EmpType);
								ReportDAOPDF.GenderWise(date, type, filePath1,imagepath,gender,EmpDesig,EmpBranch,EmpType);
								
							}
							else if(type.equalsIgnoreCase("Branchempsummery")){  
									ReportDAOPDF.BranchEmpSummery(date, type, filePath1,imagepath);
							}
							
							else if(type.equalsIgnoreCase("PromotionList")){  
								ReportDAOPDF.Desig_Wise_PromotionList(fromdate,todate, BranchType,Per_Branch,Br_rangeFrom,Br_rangeTo,filePath1,imagepath);
							}
							else if(type.equalsIgnoreCase("Seniority")){  
								ReportDAOPDF.DojWiseseniority(date, type, filePath1,imagepath);
								
							}
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
						}catch ( Exception e){		e.printStackTrace();	}
					}
					
					
				}
	
		
		/*Old Code for emplist report*/
		/*else if (action.equalsIgnoreCase("emplist")) 
		{
			String date = request.getParameter("date");
			date="01-"+date;
			String date1 = request.getParameter("date1");
			date1="01-"+date1;
			String type = request.getParameter("type");
			
			String filePath = getServletContext().getRealPath("")+ File.separator + "EmpList.txt";
			String filePath1 = getServletContext().getRealPath("")+ File.separator + "Emplist.pdf";
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			
			if((type.equalsIgnoreCase("general")))
			{						
				try
				{
						String filepath=getServletContext().getRealPath("")+ File.separator + "Generalised_Emplist.xls";
						//Method Call for Pdf Report.....
						ExcelDAO.EMPLIST(date,date1, type, filepath,imagepath);
						final int BUFSIZE = 4096;
						File file = new File(filepath);
						int length = 0;
						ServletOutputStream outStream = response.getOutputStream();
						ServletContext context = getServletConfig().getServletContext();
						String mimetype = context.getMimeType(filepath);
						
						if (mimetype == null) 
						{
							mimetype = "application/octet-stream";
						}
						
						response.setContentType(mimetype);
						response.setContentLength((int) file.length());
						String fileName = (new File(filepath)).getName();
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
						
					}catch ( Exception e) 	{	e.printStackTrace();	}
			}
			else
			{				
				try
				{	
					//Method Call for Pdf Report.....
					UtilityDAO.EMPLIST(date,date1, type, filePath1,imagepath);
					
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
				}catch ( Exception e){		e.printStackTrace();	}
			}
		}*/
		else if (action.equalsIgnoreCase("incDedRpt")) 
		{
			String date = request.getParameter("date");
			date="01-"+date;
			String code = request.getParameter("trncd");
			String table = request.getParameter("tname");
			String fname = request.getParameter("fname");
			System.out.println("table = "+table);
			System.out.println("trncd = "+code);
			String type = request.getParameter("branchtyp");
			String branch= "";
			String fromrng= "";
			String torng= "";
			System.out.println("date is "+date);
			System.out.println("desgn id "+type);
			if(type.equalsIgnoreCase("2"))
			{
				branch = request.getParameter("Branch");
			}
			else if(type.equalsIgnoreCase("3"))
			{
				fromrng = request.getParameter("rangeFrom");
				torng	= request.getParameter("rangeTo");
			}
			
			String filePath = getServletContext().getRealPath("")+ File.separator +fname+"_"+request.getParameter("date")+".pdf";
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			
			UtilityDAO.IncDedRpt(date, filePath,imagepath,code,table,type,branch,fromrng,torng);
			try
			{
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
			catch ( Exception e) 
			{
				e.printStackTrace();
			}
		} 	
		else if (action.equalsIgnoreCase("leaveReport")) 
		{
			String frmdate = request.getParameter("fromdate");
			String todate = request.getParameter("todate");
			String empbrn = request.getParameter("empbrn");
			int days = Integer.parseInt(request.getParameter("days"));
			String filePath = getServletContext().getRealPath("")+ File.separator + "leaveReport.txt";
			UtilityDAO.Leave_report(frmdate, todate, empbrn, days, filePath);
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
		else if (action.equalsIgnoreCase("Incometaxreport")) 
		{
			String date = request.getParameter("date");
			date="01-"+date;
			String sheet = request.getParameter("sheet");
			String list=request.getParameter("list");
			
			/*String frmEMPNO = request.getParameter("EMPNO");
			StringTokenizer st = new StringTokenizer(frmEMPNO, ":");
			while (st.hasMoreTokens()) 
			{
				frmEMPNO = st.nextToken();
			}
			int FromEMPNO = Integer.parseInt(frmEMPNO);
			String toEMPNO = request.getParameter("EMPNO1");
			StringTokenizer st1 = new StringTokenizer(toEMPNO, ":");
			while (st1.hasMoreTokens()) 
			{
				toEMPNO = st1.nextToken();
			}
			int ToEMPNO = Integer.parseInt(toEMPNO);*/
			String filePath = getServletContext().getRealPath("")+ File.separator + "IncomeTax"+sheet+".pdf";
			if(!sheet.equalsIgnoreCase("yearlyearning"))
			{
					YearlyPDFReport.Incometax_Report(list, date, sheet,filePath);
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
			else
			{
				System.out.println("only inserting records.........yearlyearning....");
				YearlyPDFReport.Incometax_Report(list, date, sheet,filePath);
				response.sendRedirect("taxSheet.jsp");
			}
		}
		
		else if (action.equalsIgnoreCase("Incometaxreport_New")) 
		{
			String date = request.getParameter("date");
			date="01-"+date;
			System.out.println("Date Check New Function:"+date);
			String sheet = request.getParameter("sheet");
			String list=request.getParameter("list");
			String filePath = getServletContext().getRealPath("")+ File.separator + "IncomeTax"+sheet+".pdf";
			if(!sheet.equalsIgnoreCase("yearlyearning"))
			{
					YearlyPDFReport_New.Incometax_Report(list, date, sheet,filePath);
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
			else
			{
				System.out.println("only inserting records.........yearlyearning....");
				YearlyPDFReport_New.Incometax_Report(list, date, sheet,filePath);
				response.sendRedirect("taxSheet.jsp");
			}
		}

		
		else if(action.equalsIgnoreCase("ANN_12BAreport"))
		{
			String date = request.getParameter("date");
			String frmEMPNO = request.getParameter("EMPNO");
			StringTokenizer st = new StringTokenizer(frmEMPNO, ":");
			while (st.hasMoreTokens()) 
			{
				frmEMPNO = st.nextToken();
			}
			int FromEMPNO = Integer.parseInt(frmEMPNO);
			String toEMPNO = request.getParameter("EMPNO1");
			StringTokenizer st1 = new StringTokenizer(toEMPNO, ":");
			while (st1.hasMoreTokens()) 
			{
				toEMPNO = st1.nextToken();
			}
			int ToEMPNO = Integer.parseInt(toEMPNO);
			String filePath = getServletContext().getRealPath("")+ File.separator + "ANN_12BA_Report.txt";
			TaxCalculation.ANN_12BA(FromEMPNO, ToEMPNO, date, filePath);
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
		else if(action.equalsIgnoreCase("pform24_report"))
		{
			String year = request.getParameter("year");
			String quarter = request.getParameter("quarter");
			String emptype = request.getParameter("emptype");
			String branchtyp = request.getParameter("branchtyp");
			String Prj_no ="";
			if(!branchtyp.equalsIgnoreCase("All"))
				Prj_no = request.getParameter("Branch_no");
			
			String filePath = getServletContext().getRealPath("")+ File.separator + "pform24Report.xls";
			//old code... Commented BY  Aniket
			//TaxCalculation.pform24(FromEMPNO, ToEMPNO, date,filePath);
			
			//New Form 24 BY Aniket
			TaxCalculation.form24(year, quarter, emptype,branchtyp,Prj_no,filePath);

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
		}
		else if(action.equalsIgnoreCase("bankStmnt"))
		{
			String date=request.getParameter("date");
			
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			String filePath = getServletContext().getRealPath("")+ File.separator + "Bank_Statment.pdf";
			File file = new File(filePath);
			if (file.exists()) 
			{
				file.delete();
			}
			UtilityDAO.BankStatement(date, imagepath, filePath);
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.write("<iframe scrolling=\"auto\" src=\"Bank_Statment.pdf\" height=\"400px\" width=\"100%\"></iframe>");
		

		
			
		}
		
		else if (action.equalsIgnoreCase("pflist")) 
		{
			System.out.println("in pl list");
			String date = request.getParameter("date");
			String date1 = request.getParameter("date1");
			String type = request.getParameter("type");
			String filePath = getServletContext().getRealPath("")+ File.separator + "EmpList.txt";
			String filePath1 = getServletContext().getRealPath("")+ File.separator + "emplist.pdf";
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			if(!(type.equalsIgnoreCase("J")||type.equalsIgnoreCase("L")||type.equalsIgnoreCase("C")))
			{
				UtilityDAO.EMPLIST(date,date1 ,type, filePath,imagepath);
			}
			else
			{
				UtilityDAO.EMPLIST(date,date1,type, filePath1,imagepath);
			}
			try
			{
				System.out.println("in try block of pflist on servlet..");
				if(!(type.equalsIgnoreCase("J")||type.equalsIgnoreCase("L")||type.equalsIgnoreCase("C")))
				{
				Document doc  = new Document();
				PdfWriter.getInstance(doc, new FileOutputStream(filePath1));
				BufferedReader inp = new BufferedReader(new FileReader(filePath));
				String line;
				doc.setPageSize(PageSize.A4.rotate());
				
				doc.open();
				while((line = inp.readLine())!=null)
				{
					if(line.contains("AUTHORISED"))
					{
						doc.add(Chunk.NEWLINE);
						//doc.add(Chunk.NEWLINE);
					}
			
					Paragraph ph = new Paragraph(line,new Font(Font.COURIER,9));
					doc.add(ph);
					if(line.contains("P&A Dept. H.O"))
					{
						doc.newPage();
					}
				}
				doc.close();
				inp.close();
				}
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
				
			}
			catch ( Exception e) 
			{
				e.printStackTrace();
			}
		}	
		else if (action.equalsIgnoreCase("salaryreg")) 
		{
			final int BUFSIZE = 4096;
			System.out.println("report printing ");
			String date = request.getParameter("date");
			String Desgn = request.getParameter("type");
			System.out.println("date is "+date);
			System.out.println("desgn id "+Desgn);
			String filePath = getServletContext().getRealPath("")+ File.separator + "SalaryRegister.pdf";
			try {
				
				SalaryRegisterDAO.getSalRegister(date, Desgn,filePath);
				
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
			}	
		}
		else if (action.equalsIgnoreCase("newpayreg")) 
		{


			System.out.println("in new pay reg serv");
			String date = request.getParameter("date");
			String type = request.getParameter("typ");
			String format=request.getParameter("frmt");
			String Reportformat=request.getParameter("reptype");
			String Emptype = request.getParameter("emptype");
			String BranchCode = request.getParameter("branch");
			
			System.out.println("EMPLOYEE BranchCode :"+BranchCode);
			
			/*System.out.println("tt "+type);*/
			String filename="";
			
			if(format.equalsIgnoreCase("txt")){
				filename="NewPayReg.txt";
			}			
			
			else{
				filename=format.equalsIgnoreCase("xls")?"NewPayReg.xls":"NewPayReg.pdf";
			}
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			String filePath = getServletContext().getRealPath("")+ File.separator + ""+filename;
		
				File file = new File(filePath);
				
				if (file.exists())
				{
					file.delete();
				}
				try
				{
					Document doc  = new Document();
					PdfWriter.getInstance(doc, new FileOutputStream(filePath));
					
				}catch ( Exception e) 
				{
					e.printStackTrace();
				}
			
			if(format.equalsIgnoreCase("xls"))
			{
			//ExcelDAO.newpayreg11(date,imagepath, filePath,type);
			//ExcelDAO.newpayreg11(date,imagepath, filePath,type,Emptype);
				ExcelDAO.newpayreg11(date,imagepath, filePath,type,Emptype, BranchCode);
			}
			else if(format.equalsIgnoreCase("txt"))
			{
				//UtilityDAO.payreg(date,"0", filePath);
				UtilityDAO.payregWithBranchTotal(date,"0", filePath);
					
			}
			
			else{
				//UtilityDAO.newpayregwithNewFormat(date,imagepath, filePath,type,Reportformat);
				//UtilityDAO.newpayregwithNewFormat(date,imagepath, filePath,type,Reportformat,Emptype);
				UtilityDAO.newpayregwithNewFormat(date,imagepath, filePath,type,Reportformat,Emptype, BranchCode);
			}
			
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.write("<iframe scrolling=\"auto\" src=\""+filename+"\" height=\"450px\" width=\"100%\"></iframe>");
		
		
		}
		
		
		
		
		else if (action.equalsIgnoreCase("sequentialFile_Branches")) 
		{


			System.out.println("in new sequentialFile_Branches");
			String date = request.getParameter("date");
			String type = request.getParameter("typ");
			String format=request.getParameter("frmt");
			String Reportformat=request.getParameter("reptype");
			String Emptype = request.getParameter("emptype");
			String BranchCode = request.getParameter("branch");
			
			System.out.println("EMPLOYEE BranchCode :"+BranchCode);
			
			/*System.out.println("tt "+type);*/
			String filename="";
			
			if(format.equalsIgnoreCase("txt")){
				filename="NewPayReg.txt";
			}			
			
			else{
				filename=format.equalsIgnoreCase("xls")?"NewPayReg.xls":"NewPayReg.pdf";
			}
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			String filePath = getServletContext().getRealPath("")+ File.separator + ""+filename;
		
				File file = new File(filePath);
				
				if (file.exists())
				{
					file.delete();
				}
				try
				{
					Document doc  = new Document();
					PdfWriter.getInstance(doc, new FileOutputStream(filePath));
					
				}catch ( Exception e) 
				{
					e.printStackTrace();
				}
			
			if(format.equalsIgnoreCase("xls"))
			{
			//ExcelDAO.newpayreg11(date,imagepath, filePath,type);
			//ExcelDAO.newpayreg11(date,imagepath, filePath,type,Emptype);
			//ExcelDAO.newpayreg11(date,imagepath, filePath,type,Emptype, BranchCode);
				ExcelDAO.sequentialFile_Branches(date,imagepath, filePath,type,Emptype, BranchCode);
			}
			else if(format.equalsIgnoreCase("txt"))
			{
				//UtilityDAO.payreg(date,"0", filePath);
				UtilityDAO.payregWithBranchTotal(date,"0", filePath);
					
			}
			
			else{
				//UtilityDAO.newpayregwithNewFormat(date,imagepath, filePath,type,Reportformat);
				//UtilityDAO.newpayregwithNewFormat(date,imagepath, filePath,type,Reportformat,Emptype);
				UtilityDAO.newpayregwithNewFormat(date,imagepath, filePath,type,Reportformat,Emptype, BranchCode);
			}
			
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.write("<iframe scrolling=\"auto\" src=\""+filename+"\" height=\"450px\" width=\"100%\"></iframe>");
		
		
		}

		
		
		else if (action.equalsIgnoreCase("newpayregaccount")){

			System.out.println("in new newpayregaccount");
			String date = request.getParameter("date");
			String type = request.getParameter("typ");
			String format=request.getParameter("frmt");
			String Reportformat=request.getParameter("reptype");
			String Emptype = request.getParameter("emptype");
			String BranchCode = request.getParameter("branch");
			
			System.out.println("EMPLOYEE BranchCode :"+BranchCode);
			
			/*System.out.println("tt "+type);*/
			String filename="";
			
			if(format.equalsIgnoreCase("txt")){
				filename="NewPayReg.txt";
			}			
			
			else{
				filename=format.equalsIgnoreCase("xls")?"NewPayReg.xls":"NewPayReg.pdf";
			}
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			String filePath = getServletContext().getRealPath("")+ File.separator + ""+filename;
		
				File file = new File(filePath);
				
				if (file.exists())
				{
					file.delete();
				}
				try
				{
					Document doc  = new Document();
					PdfWriter.getInstance(doc, new FileOutputStream(filePath));
					
				}catch ( Exception e) 
				{
					e.printStackTrace();
				}
			
			if(format.equalsIgnoreCase("xls"))
			{
			//ExcelDAO.newpayreg11(date,imagepath, filePath,type);
			//ExcelDAO.newpayreg11(date,imagepath, filePath,type,Emptype);
				ExcelDAO.newpayreg11(date,imagepath, filePath,type,Emptype, BranchCode);
			}
			else if(format.equalsIgnoreCase("txt"))
			{
				//UtilityDAO.payreg(date,"0", filePath);
				UtilityDAO.payregWithBranchTotal(date,"0", filePath);
					
			}
			
			else{
				//UtilityDAO.newpayregwithNewFormat(date,imagepath, filePath,type,Reportformat);
				//UtilityDAO.newpayregwithNewFormat(date,imagepath, filePath,type,Reportformat,Emptype);
				UtilityDAO.newpayregAccountwise(date,imagepath, filePath,type,Reportformat,Emptype, BranchCode);
			}
			
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.write("<iframe scrolling=\"auto\" src=\""+filename+"\" height=\"450px\" width=\"100%\"></iframe>");
			
		}
		
		
		else if (action.equalsIgnoreCase("salaryCodeWiseReg")) 
		{
			String date = request.getParameter("date");
			String format=request.getParameter("frmt");
			String TransactionCode = request.getParameter("transactioncode");
			String filename="";
			filename=format.equalsIgnoreCase("xls")?"NewPayReg.xls":"NewPayReg.pdf";
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			String filePath = getServletContext().getRealPath("")+ File.separator + ""+filename;
		
				File file = new File(filePath);
				if (file.exists())
				{
					file.delete();
				}
				try
				{
					Document doc  = new Document();
					PdfWriter.getInstance(doc, new FileOutputStream(filePath));
					
				}catch ( Exception e) 
				{
					e.printStackTrace();
				}
			
			ExcelDAO.salaryCodeWiseRegister(date,imagepath, filePath,TransactionCode);
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.write("<iframe scrolling=\"auto\" src=\""+filename+"\" height=\"450px\" width=\"100%\"></iframe>");
		}

		
		//leave ledger according to namco in txt format by Hrishi starts here
		else if (action.equalsIgnoreCase("leaveLedgertxt")) 
		{
		//	System.out.println("In leaveLedgertxt");
			
			
			String[] employ = request.getParameter("empno").split(":");
		    int frmEmpNo = Integer.parseInt(employ[2].trim());
		//	System.out.println("In leaveLedgertxt"+list);
			
			String frmdate = request.getParameter("frmdate");
			
			frmdate="01-Jan-"+frmdate; 	
			System.out.println("In frmdate "+frmdate);
			//frmdate=ReportDAO.BOYForJanToDec(frmdate);
			
			String todate = request.getParameter("todate");
			//todate=ReportDAO.EOM(todate);
			
			System.out.println("In todate "+todate);
			
			String filePath = getServletContext().getRealPath("")+ File.separator + "LeaveLedger.txt";
		
			UtilityDAO.leaveLedgertxtnew(frmdate,todate,frmEmpNo, filePath);  // for new format
		
						
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.write("<iframe scrolling=\"auto\" src=\"LeaveLedger.txt\" height=\"400px\" width=\"100%\"></iframe>");
			
		
		}	//leave ledger according to namco in txt format by Hrishi ends here
		
	
	}

	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		String action = request.getParameter("action");
		System.out.println("action= "+action);
		RequestDispatcher rd=null;
		HttpSession session= request.getSession();	
	
	
		if (action.equalsIgnoreCase("leaveLedger")) 
		{
			String list=request.getParameter("list");
			String frmdate = request.getParameter("frmdate");
			frmdate="01-"+frmdate;
			String todate = request.getParameter("todate");
			todate="01-"+todate;
			/*String frmEMPNO = request.getParameter("EMPNO");
			StringTokenizer st = new StringTokenizer(frmEMPNO, ":");
			while (st.hasMoreTokens()) 
			{
				frmEMPNO = st.nextToken();
			}
			int FromEMPNO = Integer.parseInt(frmEMPNO);
			String toEMPNO = request.getParameter("EMPNO1");
			StringTokenizer st1 = new StringTokenizer(toEMPNO, ":");
			while (st1.hasMoreTokens()) 
			{
				toEMPNO = st1.nextToken();
			}
			int ToEMPNO = Integer.parseInt(toEMPNO);*/
			String filePath = getServletContext().getRealPath("")+ File.separator + "leaveLedgerReport.pdf";
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			UtilityDAO.Leave_Ledger_ReportPDF(list, frmdate, todate,filePath,imagepath);
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
		else if (action.equalsIgnoreCase("castwiseReport")) 
		{
			String date=request.getParameter("date");
			String EmpBRN = request.getParameter("empbrn");
			System.out.println(""+EmpBRN);
			String filePath = getServletContext().getRealPath("")+ File.separator + "CastWiseEmpReport.txt";
			if(EmpBRN.equalsIgnoreCase("C"))
			{
				UtilityDAO.caste_cate_EMPLIST(date, "C", filePath);
			}
			else
			{
				UtilityDAO.caste_cate_EMPLIST(date, "D", filePath);	
			}
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
		else if (action.equalsIgnoreCase("otherList")) 
		{
			String date=request.getParameter("date");
			int trancode=Integer.parseInt(request.getParameter("trancode"));
			String desc=CodeMasterHandler.getCDesc(trancode);
			String empbrn=request.getParameter("empbrn");
			String filePath = getServletContext().getRealPath("")+ File.separator + "OterEmpList.txt";
			UtilityDAO.OtherLIST(date, trancode, desc, empbrn, filePath);
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
	 
		else if(action.equalsIgnoreCase("professionTaxStmnt"))
		{
		
			final int BUFSIZE = 4096;
			String filePath = getServletContext().getRealPath("")+ File.separator + "professiontax.pdf";
			String date=request.getParameter("date");
			date="01-"+date;
			String table_name=request.getParameter("table").equalsIgnoreCase("after")?"after":"before";
			String state=request.getParameter("state");
//System.out.println("-------------------"+table_name);
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			try {
				UtilityDAO.getProfessionTaxStatment(filePath,date,imagepath,table_name,state);
				
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
			} catch (SQLException | DocumentException e) {
				
				e.printStackTrace();
			}
			 
		}
		
	
		else if(action.equalsIgnoreCase("challanTaxStmnt"))
		{
			
			final int BUFSIZE = 4096;
			String filePath = getServletContext().getRealPath("")+ File.separator + "challan.pdf";
			String date=request.getParameter("date2");
			date="01-"+date;
			String table_name=request.getParameter("table").equalsIgnoreCase("after")?"after":"before";
			String states=request.getParameter("states");
//System.out.println("-------------------"+table_name);
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			try {
				UtilityDAO.getChallanTaxStatment(filePath,date,imagepath,table_name,states);
				
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
			} catch (SQLException | DocumentException e) {
				
				e.printStackTrace();
			}
			 
		}
		
		
		
		
		
		else if(action.equalsIgnoreCase("getSalDetails"))
		{
			final int BUFSIZE = 4096;
			YearlyPDFReport yearly = new YearlyPDFReport();
			String date = request.getParameter("date");
			date="01-"+date;
			String list=request.getParameter("list");
			System.out.println("action==="+request.getParameter("action"));
			System.out.println("date==="+request.getParameter("date"));
			System.out.println("list==="+request.getParameter("list"));
			
			String imgpath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			String filePath = getServletContext().getRealPath("")+ File.separator + "YearlySalaryReport-"+date+".pdf";
			try {
				
				yearly.printReport(list, date, imgpath, filePath);
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
				//UsrHandler.senderrormail(e,"ReportServlet.getSalDetails");
				e.printStackTrace();
			}
		}
		else if(action.equalsIgnoreCase("getCtcReport"))
		{
			final int BUFSIZE = 4096;
			YearlyPDFReport yearly = new YearlyPDFReport();
			String list=request.getParameter("list");
			String date = "01-Jun-"+request.getParameter("date");
			System.out.println("DATE"+date);
			String ab=date;
			String imgpath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			String filePath = getServletContext().getRealPath("")+ File.separator + "YearlyCTCReport_"+date+".pdf";
			try {
				yearly.printCtcReport(list, ab, imgpath, filePath);
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
				//UsrHandler.senderrormail(e,"ReportServlet.getSalDetails");
				e.printStackTrace();
			}
		}
		else if(action.equalsIgnoreCase("form10"))
		{
			
			final int BUFSIZE = 4096;
			String filePath = getServletContext().getRealPath("")+ File.separator + "Form10.pdf";
			String date=request.getParameter("date");
			date="01-"+date;
			String date1=request.getParameter("date1");
			date1="01-"+date1;

			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			try {
				UtilityDAO.getForm10(filePath,date,date1,imagepath);
				
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
			} catch (SQLException | DocumentException e) {
				
				e.printStackTrace();
			}
			 
		}
		else if(action.equalsIgnoreCase("form12A"))
		{
			final int BUFSIZE = 4096;
			String fromdate=request.getParameter("frmdate");
			String todate=request.getParameter("todate");
			//String emp = request.getParameter("EMPNO");
			//int empno = Integer.parseInt(emp.split(":")[1].trim());
			String filePath = getServletContext().getRealPath("")+ File.separator + "FORM-12A.pdf";
			try {
				//UtilityDAO.getProfessionTaxStatment(filePath,date,imagepath);
				Form12A.getForm12A(fromdate, todate, filePath);
				
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
			}catch (Exception e) {	
				e.printStackTrace();
			}
		}
		
		else if(action.equalsIgnoreCase("form6A"))
		{
			final int BUFSIZE = 4096;
			String fromdate=request.getParameter("frmdate");
			String todate=request.getParameter("todate");
			//String emp = request.getParameter("EMPNO");
			//int empno = Integer.parseInt(emp.split(":")[1].trim());
			String imagepath =getServletContext().getRealPath("/images/Form6A.jpg");
			String filePath = getServletContext().getRealPath("")+ File.separator + "FORM-6A.pdf";
			try {
				Form6A.getForm6A(filePath,fromdate,todate,imagepath);
				
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
			}catch (Exception e) {	
				e.printStackTrace();
			}
		}
		
		else if(action.equalsIgnoreCase("esiclist"))
		{
			final int BUFSIZE = 4096;
			String filePath = getServletContext().getRealPath("")+ File.separator + "esiclist.pdf";
			String date=request.getParameter("date");
			date="01-"+date;
			String table_name=request.getParameter("before")==null?"after":"before";
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			try {
				UtilityDAO.getESICLIST(filePath,date,imagepath,table_name);
				
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
			} catch (SQLException | DocumentException e) {
				
				e.printStackTrace();
			}
			 
		}
		else if(action.equalsIgnoreCase("from3a"))
		{
			final int BUFSIZE = 4096;
			String fromdate=request.getParameter("frmdate");
			fromdate="01-"+fromdate;
			String todate=request.getParameter("todate");
			todate="01-"+todate;
			System.out.println("fromdate===="+fromdate+"todate===="+todate);
			String emp = request.getParameter("EMPNO");
			String reportype = request.getParameter("reportype");
			System.out.println("reportype==="+reportype);
			int empno = Integer.parseInt(emp.split(":")[2].trim());
			try {
				String filePath = getServletContext().getRealPath("")+ File.separator + "FORM3A-"+empno+(reportype.equalsIgnoreCase("PDF")?".pdf":".txt");
				if(reportype.equalsIgnoreCase("PDF"))
				{
				//UtilityDAO.getProfessionTaxStatment(filePath,date,imagepath);
				Form3A.getForm3A(fromdate, todate, empno, filePath);
				}
				
				else if(reportype.equalsIgnoreCase("TXT"))
				{
					Form3A.getForm3ATXT(fromdate, todate, empno, filePath);
					//UtilityDAO.PFLISTPDF(date, filePath,table_name);
				}
				
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
			}
		}
		
		
		else if (action.equalsIgnoreCase("travelreport")) 
		{
			System.out.println("report printing ");
			String trcode = request.getParameter("trvCode");
			String EMPNO = request.getParameter("EMPNO");
			String EMPNO1 = request.getParameter("EMPNO1");
			
			
			System.out.println("emp no "+EMPNO);
			System.out.println("name "+EMPNO1);
			System.out.println("travel code "+trcode);
			int emp = Integer.parseInt(EMPNO);
			int trc = Integer.parseInt(trcode);
			
			String filePath = getServletContext().getRealPath("")+ File.separator + "TravelReport.pdf";
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			
			TravelReportDAO.PDFReport(emp, trc,filePath, imagepath);
			
			
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
		
		else if(action.equalsIgnoreCase("newAttendSheet"))
		{

			System.out.println("in new Attendsheet  serv");
			String date = request.getParameter("date");
			date="01-"+date;
		
			
			
			
			
			String filename="";
			filename="NewAttendReport.pdf";
			String filePath = getServletContext().getRealPath("")+ File.separator + ""+filename;
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			
			UtilityDAO.newAttendSheet(date,imagepath, filePath);	
			
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
		
		
		else if (action.equalsIgnoreCase("pfchallan")) 
		{
		System.out.println("in pl list");
		String date = request.getParameter("date");
		date="01-"+date;
		String type = request.getParameter("type");
		String table=request.getParameter("before")==null?"after":"before";
		
		String filePath1 = getServletContext().getRealPath("")+ File.separator + "pfchallan.pdf";
		String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
		/*if(!(type.equalsIgnoreCase("J")||type.equalsIgnoreCase("L")||type.equalsIgnoreCase("C")))
		{
			PfChallanDAO.PfChallan(date, type, filePath,imagepath);
		}
		else
		{
			PfChallanDAO.PfChallan(date, type, filePath1,imagepath);
		}*/
		
		PfChallanDAO.PfChallanNew(date, type, filePath1,imagepath,table);
		
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
			}
			
		else if(action.equalsIgnoreCase("esicchallan"))
		{
			final int BUFSIZE = 4096;
			String fromdate=request.getParameter("frmdate");
			fromdate="01-"+fromdate;
			String table_name=request.getParameter("before")==null?"after":"before";
		//	String todate=request.getParameter("todate");
			//String emp = request.getParameter("EMPNO");
			//int empno = Integer.parseInt(emp.split(":")[1].trim());
			String filePath = getServletContext().getRealPath("")+ File.separator + "esicchallan.pdf";
			try {
				//UtilityDAO.getProfessionTaxStatment(filePath,date,imagepath);
				ESICChallanDAO.getesicchallan(fromdate, filePath,table_name);
				
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
			}
		}
		
		/*
		 * else if(action.equalsIgnoreCase("salarycerti")) { final int BUFSIZE = 4096;
		 * String fromdate=request.getParameter("frmdate"); String
		 * todate=request.getParameter("todate"); String emp =
		 * request.getParameter("EMPNO"); int empno =
		 * Integer.parseInt(emp.split(":")[2].trim()); String filePath =
		 * getServletContext().getRealPath("")+ File.separator +
		 * "SalaryCerti-"+empno+".pdf"; try {
		 * 
		 * SalaryCertificateDAO.getCertificate(fromdate, todate, empno, filePath);
		 * 
		 * File file = new File(filePath); int length = 0; ServletOutputStream outStream
		 * = response.getOutputStream(); ServletContext context =
		 * getServletConfig().getServletContext(); String mimetype =
		 * context.getMimeType(filePath); if (mimetype == null) { mimetype =
		 * "application/octet-stream"; } response.setContentType(mimetype);
		 * response.setContentLength((int) file.length()); String fileName = (new
		 * File(filePath)).getName(); response.setHeader("Content-Disposition",
		 * "attachment; filename=\"" + fileName + "\""); byte[] byteBuffer = new
		 * byte[BUFSIZE]; DataInputStream in = new DataInputStream(new
		 * FileInputStream(file)); while ((in != null) && ((length =
		 * in.read(byteBuffer)) != -1)) { outStream.write(byteBuffer, 0, length); }
		 * in.close(); outStream.close(); if (file.exists()) { file.delete(); } } catch
		 * (Exception e) {
		 * 
		 * e.printStackTrace(); } }
		 */
		
		
		else if(action.equalsIgnoreCase("salarycerti"))
		{
			final int BUFSIZE = 4096;
			String fromdate=request.getParameter("frmdate");
			String todate=request.getParameter("todate");
			String emp = request.getParameter("EMPNO");
			int empno = Integer.parseInt(emp.split(":")[2].trim());
			String imgpath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			String filePath = getServletContext().getRealPath("")+ File.separator + "SalaryCerti-"+empno+".pdf";
			try {
				
				SalaryCertificateDAO.getCertificate(fromdate, todate, empno, filePath,imgpath);
				
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
			}
		}
		else if(action.equalsIgnoreCase("ECR"))
		{
			
			final int BUFSIZE = 4096;
			String filePath = getServletContext().getRealPath("")+ File.separator + "ECR-Report.pdf";
			String date=request.getParameter("date");

			try {
				System.out.println("----------------------ECR-----------------------");
				UtilityDAO.EcrReport(date,filePath,"before");
				
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
			}
			 
		}
		
		
		
		
		else if(action.equalsIgnoreCase("mlwfchallan"))
		{
		     
			final int BUFSIZE = 4096;
			String filePath = getServletContext().getRealPath("")+ File.separator + "Mlwfchallan.pdf";
			String date=request.getParameter("date");
			date="01-"+date;
			String table_name=request.getParameter("before")==null?"after":"before";

			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			try {
				UtilityDAO.getMlwfchallan(filePath,date,imagepath,table_name);
				
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
			} catch (SQLException | DocumentException e) {
				
				e.printStackTrace();
			}
			 
		}
		
		
		else if(action.equalsIgnoreCase("reliffund"))
		{
			final int BUFSIZE = 4096;
			String filePath = getServletContext().getRealPath("")+ File.separator + "esiclist.pdf";
			String date=request.getParameter("date");
			date="01-"+date;
			String table_name=request.getParameter("before")==null?"after":"before";
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			try {
				UtilityDAO.getRelif_Fund(filePath,date,imagepath,table_name);
				
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
			} catch (SQLException | DocumentException e) {
				
				e.printStackTrace();
			}
			 
		}
		
		
		else if(action.equalsIgnoreCase("form5"))
		{
			
			final int BUFSIZE = 4096;
			String filePath = getServletContext().getRealPath("")+ File.separator + "Form5.pdf";
			String date=request.getParameter("date");
			date="01-"+date;
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			try {
				UtilityDAO.getForm5(filePath,date,imagepath);
				
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
			} catch (SQLException | DocumentException e) {
				
				e.printStackTrace();
			}
			 
		}
		else if(action.equalsIgnoreCase("vdachart"))
		{
			final int BUFSIZE = 4096;
			String filePath = getServletContext().getRealPath("")+ File.separator + "VDAChart.pdf";
			String date=request.getParameter("date");
			date="01-"+date;
		
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			UtilityDAO.getVdaList(filePath, date, imagepath);
	
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
		else if (action.equalsIgnoreCase("postvda"))
		{
		
			PostingHandler obj=new PostingHandler();
			boolean flag =obj.vda_post();
			
			if(flag)
			{
				
				response.sendRedirect("VDA_Posting.jsp?flag=1");
				
			}
			else
			{
				response.sendRedirect("VDA_Posting.jsp?flag=2");
			}
			
		}
		
		else if (action.equalsIgnoreCase("postvdadiff"))
		{
			PostingHandler obj=new PostingHandler();
			String date1="01-"+request.getParameter("date1");
			String date2="01-"+request.getParameter("date2");
			boolean flag =obj.vda_Diff_Post(date1,date2);
			
			if(flag)
			{
				
				response.sendRedirect("VDA_Posting.jsp?flag=3");
				
			}
			else
			{
				response.sendRedirect("VDA_Posting.jsp?flag=4");
			}
		
		}
		else if(action.equalsIgnoreCase("getBonusReport"))
		{
			String date = "01-Sep-"+request.getParameter("date"); //****by shivaji
		/*	String reporttype = request.getParameter("reporttype");
			String status = request.getParameter("active")==null?"nonactive":"active";*/
			
	
				try 
				{
					final int BUFSIZE = 4096;
					YearlyPDFReport yearly = new YearlyPDFReport();
					
					String imgpath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
					String filePath = getServletContext().getRealPath("")+ File.separator + "YearlyBonusReport_"+date+".pdf";
					// Method Call..
					yearly.printBonusReport( date, filePath);
					
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
				} catch (Exception e) {e.printStackTrace();}
			
			/*if(reporttype.equalsIgnoreCase("excel"))
			{
				try
				{
					if(status.equalsIgnoreCase("active")){
						status="A";
					}
					else
					{
						status="N";
					}
					//System.out.println("*****"+status);
					Connection cn = ConnectionManager.getConnection();
					Statement st = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					
					ResultSet rs = null;
					SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
					
					HSSFWorkbook hwb=new HSSFWorkbook();
					HSSFSheet sheet =  hwb.createSheet("Bonus Report");
					 sheet.createFreezePane( 0, 11, 0, 11 );
					
					    HSSFCellStyle my_style = hwb.createCellStyle();
		                HSSFFont my_font=hwb.createFont();
		                my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		                my_style.setFont(my_font);

		
						
						HSSFRow rowtitle=   sheet.createRow((short)0);
						HSSFCell cell = rowtitle.createCell((short) 7);
						cell.setCellValue("THE BUSINESS CO.OP BANK LTD.");
						cell.setCellStyle(my_style);
						HSSFRow rowtitle1=   sheet.createRow((short)1);
						HSSFCell cell1 = rowtitle1.createCell((short) 5);
						cell1.setCellValue("Rajan Complex Arcade, Datta Mandir, Nashik Road, Nashik - 422101");
						cell1.setCellStyle(my_style);
						HSSFRow rowtitle2=   sheet.createRow((short)2);
						HSSFCell cell2 = rowtitle2.createCell((short) 7);
						cell2.setCellValue("Tel : +91-20 26812190");
						cell2.setCellStyle(my_style);
						HSSFRow rowtitle3=   sheet.createRow((short)3);
						HSSFCell cell3 = rowtitle3.createCell((short) 7);
						cell3.setCellValue("Email : adm@namcobank.in");
						cell3.setCellStyle(my_style);
						HSSFRow rowtitle4=   sheet.createRow((short)4);
						HSSFCell cell4 = rowtitle4.createCell((short) 5);
						cell4.setCellValue("Employee's Yearly Bonus Report for Financial Year ("+ReportDAO.Boy(date).substring(7)+" - "+ReportDAO.Eoy(date).substring(7));
						cell4.setCellStyle(my_style);
						
						
						
						sheet.setColumnWidth((short)0, (short)3000);
						sheet.setColumnWidth((short)1, (short)3000);
						sheet.setColumnWidth((short)2, (short)7000);
						sheet.setColumnWidth((short)3, (short)4000);
						sheet.setColumnWidth((short)4, (short)4000);
						sheet.setColumnWidth((short)5, (short)4000);
						sheet.setColumnWidth((short)6, (short)4000);
						sheet.setColumnWidth((short)7, (short)4000);
						sheet.setColumnWidth((short)8, (short)4000);
						sheet.setColumnWidth((short)9, (short)4000);
						sheet.setColumnWidth((short)10, (short)4000);
						sheet.setColumnWidth((short)11, (short)4000);
						sheet.setColumnWidth((short)12, (short)4000);
						sheet.setColumnWidth((short)13, (short)4000);
						sheet.setColumnWidth((short)14, (short)4000);
						sheet.setColumnWidth((short)15, (short)4000);
						sheet.setColumnWidth((short)16, (short)4000);
						sheet.setColumnWidth((short)17, (short)6000);
						sheet.setColumnWidth((short)18, (short)4000);
						
						HSSFRow head1=   sheet.createRow((short)9);
						head1.createCell((short) 0).setCellValue("");
						HSSFRow rowhead=   sheet.createRow((short)10);
						rowhead.createCell((short) 0).setCellValue("Sr No.");
						rowhead.createCell((short) 1).setCellValue("Emp Code");
						rowhead.createCell((short) 2).setCellValue("Employee Name");
						rowhead.createCell((short) 3).setCellValue("DOJ");
						rowhead.createCell((short) 4).setCellValue("DOL");
						rowhead.createCell((short) 5).setCellValue("Apr");
						rowhead.createCell((short) 6).setCellValue("May");
						rowhead.createCell((short) 7).setCellValue("Jun");
						rowhead.createCell((short) 8).setCellValue("Jul");
						rowhead.createCell((short) 9).setCellValue("Aug");
						rowhead.createCell((short) 10).setCellValue("sep");
						rowhead.createCell((short) 11).setCellValue("oct");
						rowhead.createCell((short) 12).setCellValue("Nov");
						rowhead.createCell((short) 13).setCellValue("Dec");
						rowhead.createCell((short) 14).setCellValue("Jan");
						rowhead.createCell((short) 15).setCellValue("Feb");
						rowhead.createCell((short) 16).setCellValue("Mar");
						rowhead.createCell((short) 17).setCellValue("Total");
						rowhead.createCell((short) 18).setCellValue("Bouns");

						
						int i=11;

						int srno=1; 
						  double totatlbonus=0;
					
							
					String sql2="with bonus as(select y.trncd,disc,e.empno, sum(case datepart(MM,trndt) when 04 then NET_AMT else  0 end) apr, " +
							"sum(case datepart(MM,trndt) when 05 then NET_AMT else  0 end) may, " +
							"sum(case datepart(MM,trndt) when 06 then NET_AMT else  0 end) jun, " +
							"sum(case datepart(MM,trndt) when 07 then NET_AMT else  0 end) jul, " +
							"sum(case datepart(MM,trndt) when 08 then NET_AMT else  0 end) aug, " +
							"sum(case datepart(MM,trndt) when 09 then NET_AMT else  0 end) sep, " +
							"sum(case datepart(MM,trndt) when 10 then NET_AMT else  0 end) oct, " +
							"sum(case datepart(MM,trndt) when 11 then NET_AMT else  0 end) nov, " +
							"sum(case datepart(MM,trndt) when 12 then NET_AMT else  0 end) dec, " +
							"sum(case datepart(MM,trndt) when 01 then NET_AMT else  0 end) jan, " +
							"sum(case datepart(MM,trndt) when 02 then NET_AMT else  0 end) feb, " +
							"sum(case datepart(MM,trndt) when 03 then NET_AMT else  0 end) mar  " +
							"from empmast e, cdmast c, ytdtran y where e.empno = y.empno " +
							"and y.trncd = c.trncd and e.status='"+status+"' and y.trndt between '"+ReportDAO.Boy(date)+"' and '"+ReportDAO.Eoy(date)+"' " +
							"and y.trncd in(select onamtcd from onamt where trncd=143) and c.trncd in(select onamtcd from onamt where trncd=143) " +
							"group by  e.empno,fname,lname,disc,y.trncd ) " +
							"select b.empno,rtrim(e.fname)+' '+rtrim(e.lname) name,E.DOJ,E.DOL,e.empcode,SUM (b.apr) as apramt ," +
							"SUM (b.may) as mayamt, SUM (b.jun) as junamt, SUM (b.jul) as julamt,SUM (b.aug) as augamt, " +
							"SUM (b.sep) as sepamt ,SUM (b.oct) as octamt ,SUM (b.nov) as novamt ,SUM (b.dec) as decamt , " +
							"SUM (b.jan) as janamt,SUM (b.feb) as febamt ,SUM (b.mar) as maramt, " +
							"SUM(b.apr+b.may+b.jun+b.jul+b.aug+b.sep+b.oct+b.nov+b.dec+b.jan+b.feb+b.mar) as grand_total, " +
							"((SUM(b.apr+b.may+b.jun+b.jul+b.aug+b.sep+b.oct+b.nov+b.dec+b.jan+b.feb+b.mar))/100*( select per from slab where trncd=143)) as bonus_amt " +
							"from bonus b ,EMPMAST E where b.EMPNO=E.empno group by b.empno,E.fname,E.lname,E.DOJ,E.DOL,empcode order by e.empcode "; 
							
							rs = st.executeQuery(sql2);
							 System.out.println("excel quary   "+sql2);
								while(rs.next())
								{
									
									HSSFRow row = sheet.createRow((short)i++);
						       
									row.createCell((short) 0).setCellValue(""+srno);
									
									row.createCell((short) 1).setCellValue(""+rs.getString("empcode"));
									
									row.createCell((short) 2).setCellValue(""+rs.getString("name"));
									
									row.createCell((short) 3).setCellValue(""+rs.getDate("DOJ")==null?"":format.format(rs.getDate("DOJ")));

						            String dol=  rs.getString("DOL")==null || rs.getString("DOL")==""?"31-Dec-2099":format.format(rs.getDate("DOL"));
						            
						            dol=dol.equalsIgnoreCase("31-Dec-2099")?"":dol;

									row.createCell((short) 4).setCellValue(dol);
									row.createCell((short) 5).setCellValue(rs.getString("apramt"));
									row.createCell((short) 6).setCellValue(rs.getString("mayamt"));
									row.createCell((short) 7).setCellValue(rs.getString("junamt"));
									row.createCell((short) 8).setCellValue(rs.getString("julamt"));
									row.createCell((short) 9).setCellValue(rs.getString("augamt"));
									row.createCell((short) 10).setCellValue(rs.getString("sepamt"));
									row.createCell((short) 11).setCellValue(rs.getString("octamt"));
									row.createCell((short) 12).setCellValue(rs.getString("novamt"));
									row.createCell((short) 13).setCellValue(rs.getString("decamt"));
									row.createCell((short) 14).setCellValue(rs.getString("janamt"));
									row.createCell((short) 15).setCellValue(rs.getString("febamt"));
									row.createCell((short) 16).setCellValue(rs.getString("maramt"));
									row.createCell((short) 17).setCellValue(rs.getString("grand_total"));
									row.createCell((short) 18).setCellValue( Math.round(rs.getInt("bonus_amt")));
								
									 totatlbonus=totatlbonus + Math.round(rs.getInt("bonus_amt"));
									srno++;				
				}                
								HSSFRow row = sheet.createRow((short)i++);
								row.createCell((short) 17).setCellValue("TOTAL:-");
								row.createCell((short) 18).setCellValue(""+totatlbonus);
								String filePath1 = getServletContext().getRealPath("")+ File.separator + "YearlyBonusReport_"+date+".xls";
								FileOutputStream fileOut =  new FileOutputStream(filePath1);//"D:\\PFList.xls"
								hwb.write(fileOut);
								fileOut.close();
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
							}
			
				catch (Exception ex ) 
				{
					ex.printStackTrace();
				}
			
		   }
		*/
		}
		
		else if(action.equalsIgnoreCase("CtcReportNew"))
		{
			
			System.out.println("Inside CtcReportNew");
			final int BUFSIZE = 4096;
			YearlyPDFReport yearly = new YearlyPDFReport();
			String list=request.getParameter("list");
			String date = request.getParameter("date");
			System.out.println("DATE"+date);
			String Format= request.getParameter("format");
			
			System.out.println("format  "+Format);
			String imgpath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			String filePath="";
			
			 try {
				 if(Format.equalsIgnoreCase("Pdf")){
					filePath = getServletContext().getRealPath("")+ File.separator + "CTCReport_"+date+".pdf";
				yearly.CtcReportNew(list, date, imgpath, filePath);
				
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
				
				 // for excel report
				 else{
					 
						Connection cn = ConnectionManager.getConnection();
						Statement st = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
						
						ResultSet rs = null;
						SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
						
						
					
						
						HSSFWorkbook hwb=new HSSFWorkbook();
						HSSFSheet sheet =  hwb.createSheet("CTC_REPORT");
						
					
						sheet.setColumnWidth((short)0, (short)3000);
						sheet.setColumnWidth((short)1, (short)3000);
						sheet.setColumnWidth((short)2, (short)7000);
						sheet.setColumnWidth((short)3, (short)4000);
						sheet.setColumnWidth((short)4, (short)4000);
						sheet.setColumnWidth((short)5, (short)4000);
						sheet.setColumnWidth((short)6, (short)4000);
						sheet.setColumnWidth((short)7, (short)4000);
						sheet.setColumnWidth((short)8, (short)4000);
						sheet.setColumnWidth((short)9, (short)4000);
						sheet.setColumnWidth((short)10, (short)4000);
						sheet.setColumnWidth((short)11, (short)4000);
						sheet.setColumnWidth((short)12, (short)4000);
						sheet.setColumnWidth((short)13, (short)4000);
						sheet.setColumnWidth((short)14, (short)4000);
						sheet.setColumnWidth((short)15, (short)4000);
						sheet.setColumnWidth((short)16, (short)4000);
						sheet.setColumnWidth((short)17, (short)4000);
						sheet.setColumnWidth((short)18, (short)4000);
						




				        HSSFCellStyle my_style = hwb.createCellStyle();
				        HSSFCellStyle my_style1 = hwb.createCellStyle();

				        HSSFFont my_font=hwb.createFont();
				        my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				        my_style.setFont(my_font);
				      
				        
				        HSSFRow rowtitle=   sheet.createRow((short)0);
						HSSFCell cell = rowtitle.createCell((short) 7);
						cell.setCellValue("  HARSH CONSTRUCTION PVT.LTD.");
						cell.setCellStyle(my_style);
						HSSFRow rowtitle1=   sheet.createRow((short)1);
						HSSFCell cell1 = rowtitle1.createCell((short) 5);
						cell1.setCellValue("  Rajan Complex Arcade, Datta Mandir, Nashik Road, Nashik - 422101");
						cell1.setCellStyle(my_style);
						HSSFRow rowtitle2=   sheet.createRow((short)2);
						HSSFCell cell2 = rowtitle2.createCell((short) 7);
						cell2.setCellValue("Tel : 0253-2317143,Fax : 0253-2317621");
						cell2.setCellStyle(my_style);
						HSSFRow rowtitle3=   sheet.createRow((short)3);
						HSSFCell cell3 = rowtitle3.createCell((short) 7);
						cell3.setCellValue("    Email : info@hcplnsk.com");
						cell3.setCellStyle(my_style);
						HSSFRow rowtitle4=   sheet.createRow((short)5);
						HSSFCell cell5 = rowtitle4.createCell((short) 6);
						cell5.setCellValue("          Employee's CTC Report for Employees "+ReportDAO.getSysDate()+" ");
						cell5.setCellStyle(my_style);
					
								
						HSSFRow rowtitle5=   sheet.createRow((short)4);
						cell2=rowtitle5.createCell((short) 2);
						cell2.setCellValue("");
						cell2.setCellStyle(my_style);
						HSSFRow rowtitle6=   sheet.createRow((short)5);
						rowtitle6.createCell((short) 0).setCellValue("");
						HSSFRow rowtitle7=   sheet.createRow((short)6);
						rowtitle7.createCell((short) 0).setCellValue("");
						
						HSSFFont blueFont = hwb.createFont();
						blueFont.setColor(HSSFColor.BLUE.index);
						
						HSSFCellStyle style = hwb.createCellStyle();
						//style.setFont(blueFont);
						style.setFillForegroundColor(HSSFColor.BLUE.index);
						
						
						
						
						HSSFRow head=   sheet.createRow((short)7);
						head.createCell((short) 0).setCellValue("");
						HSSFRow heading=   sheet.createRow((short)8);
						HSSFCell cell4 = heading.createCell((short) 0); 

						cell4.setCellValue("");
						cell4.setCellStyle(my_style1);
						HSSFRow rowhead=   sheet.createRow((short)9);
				        sheet.createFreezePane( 0, 10, 0, 10 );
				       
				        my_style1.setAlignment((short) 2);
				        my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				        my_style1.setFont(my_font);
				        
				        cell4=rowhead.createCell((short) 0);
				        cell4.setCellValue("SR.NO");
				        cell4.setCellStyle(my_style1);
				        cell4=rowhead.createCell((short) 1);
				        cell4.setCellValue("Emp Code");
				        cell4.setCellStyle(my_style1);
				        cell4=rowhead.createCell((short) 2);
				        cell4.setCellValue("Employee Name");
				        cell4.setCellStyle(my_style1);
				        cell4=rowhead.createCell((short) 3);
				        cell4.setCellValue("DOJ");
				        cell4.setCellStyle(my_style1);
				        cell4=rowhead.createCell((short) 4);
				        cell4.setCellValue("Basic");
				        cell4.setCellStyle(my_style1);
				        cell4=rowhead.createCell((short) 5);
				        cell4.setCellValue("DA");
				        cell4.setCellStyle(my_style1);
				        cell4=rowhead.createCell((short) 6);
				        cell4.setCellValue("HRA");
				        cell4.setCellStyle(my_style1);
				        cell4=rowhead.createCell((short) 7);
				        cell4.setCellValue("Medical");
				        cell4.setCellStyle(my_style1);
				        cell4=rowhead.createCell((short)8);
				        cell4.setCellValue("Education");
				        cell4.setCellStyle(my_style1);

				        cell4=rowhead.createCell((short) 9);
				        cell4.setCellValue("OtherAllowance");
				        cell4.setCellStyle(my_style1);
				        cell4=rowhead.createCell((short) 10);
				        cell4.setCellValue("Bonus");
				        cell4.setCellStyle(my_style1);
						
				        cell4=rowhead.createCell((short) 11);
						cell4.setCellValue("Vda");
						cell4.setCellStyle(my_style1);
						cell4=rowhead.createCell((short) 12);
						cell4.setCellValue("Closing");
						cell4.setCellStyle(my_style1);
						cell4=rowhead.createCell((short) 13);
						cell4.setCellValue("AreaBDV");
						cell4.setCellStyle(my_style1);
						cell4=rowhead.createCell((short) 14);
						cell4.setCellValue("AREAALL");
						cell4.setCellStyle(my_style1);
						cell4=rowhead.createCell((short) 15);
						cell4.setCellValue("ARREARS");
						cell4.setCellStyle(my_style1);
						cell4=rowhead.createCell((short) 16);
						cell4.setCellValue("PF");
						cell4.setCellStyle(my_style1);
						cell4=rowhead.createCell((short) 17);
						cell4.setCellValue("PT");
						cell4.setCellStyle(my_style1);
						cell4=rowhead.createCell((short) 18);
						cell4.setCellValue("ESIC");
						cell4.setCellStyle(my_style1);
						
						
							int i=11;

							int srno=1; 
							 
						
								
					/*	String sql2="select e.empcode as empcode, e.doj as DOJ, e.empno as empno,rtrim(e.fname)+' '+rtrim(e.mname)+' '+rtrim(e.lname)as name, " +
								" p1.inp_amt as Basic,p2.inp_amt as Medical,p3.inp_amt as HRA, p4.inp_amt as Edu, " +
								" p5.inp_amt as Min_ins, p6.inp_amt as PF, p7.inp_amt as Add_less, p8.inp_amt as Col, " +
								" p9.inp_amt as Conv, p10.inp_amt as special_allow, p11.inp_amt as Bonus," +
								" p11.pf as pfv, p11.pt as pt, p11.esic as esic from EMPMAST e join ctcdisplay p1 on e.EMPNO=p1.empno " +
								" join  ctcdisplay p2 on e.EMPNO = p2.EMPNO " +
								" join  ctcdisplay p3 on e.EMPNO = p3.EMPNO " +
								" join  ctcdisplay p4 on e.EMPNO = p4.EMPNO " +
								" join  ctcdisplay p5 on e.EMPNO = p5.EMPNO " +
								" join  ctcdisplay p6 on e.EMPNO = p6.EMPNO " +
								" join  ctcdisplay p7 on e.EMPNO = p7.EMPNO " +
								" join  ctcdisplay p8 on e.EMPNO = p8.EMPNO " +
								" join  ctcdisplay p9 on e.EMPNO = p9.EMPNO " +
								" join  ctcdisplay p10 on e.EMPNO =p10.EMPNO " +
								" join  ctcdisplay p11 on e.EMPNO =p11.EMPNO " +
								" where p1.TRNCD = 101 and  p2.trncd=104 and " +
								" p3.trncd=103 and  p4.trncd=105 and " +
								" p5.trncd=126 and  p6.trncd =201 and " +
								" p7.trncd=127 and  p8.trncd=128 and " +
								" p9.trncd=108 and  p10.trncd=107 and" +
								" p11.trncd=135 and e.empno in("+list+") ";
								
								rs = st.executeQuery(sql2);
								 */
							String sql2="select e.empcode as empcode, e.doj as DOJ, e.empno as empno,rtrim(e.fname)+' '+rtrim(e.mname)+' '+rtrim(e.lname)as name, " +
									"	 p1.inp_amt as BASIC,p2.inp_amt as DA,p3.inp_amt as HRA, p4.inp_amt as MEDICAL, " +
									"	 p5.inp_amt as EDUCATION, p6.inp_amt as OTHERALLOWANCE, p7.inp_amt as BONUS, p8.inp_amt as VDA, " +
									"    p9.inp_amt as CLOSING, p10.inp_amt as AREABDV, p11.inp_amt as AREAALL,P12.inp_amt as ARREARS," +
									"	 p11.pf as pfv, p11.pt as pt, p11.esic as esic from EMPMAST e join ctcdisplay p1 on e.EMPNO=p1.empno " +
									"		 join  ctcdisplay p2 on e.EMPNO = p2.EMPNO " +
									"		 join  ctcdisplay p3 on e.EMPNO = p3.EMPNO " +
									"		 join  ctcdisplay p4 on e.EMPNO = p4.EMPNO " +
									"		 join  ctcdisplay p5 on e.EMPNO = p5.EMPNO " +
									"		 join  ctcdisplay p6 on e.EMPNO = p6.EMPNO " +
									"		 join  ctcdisplay p7 on e.EMPNO = p7.EMPNO " +
									"		 join  ctcdisplay p8 on e.EMPNO = p8.EMPNO " +
									"		 join  ctcdisplay p9 on e.EMPNO = p9.EMPNO " +
									"		 join  ctcdisplay p10 on e.EMPNO =p10.EMPNO " +
									"		 join  ctcdisplay p11 on e.EMPNO =p11.EMPNO " +
									"		 join  ctcdisplay p12 on e.EMPNO =p12.EMPNO " +
									"		 where p1.TRNCD = 101 and  p2.trncd=102 and " +
									"		 p3.trncd=103 and  p4.trncd=104 and " +
									"		 p5.trncd=105 and  p6.trncd =129 and " +
									"		 p7.trncd=135 and  p8.trncd=138 and " +
									"		 p9.trncd=139 and  p10.trncd=140 and" +
									"		 p11.trncd=141 and p12.trncd=142 and  e.empno in("+list+")  ";

								 System.out.println("excel quary   "+sql2);
									rs = st.executeQuery(sql2);
								HSSFRow row = sheet.createRow((short)i++);
								while(rs.next())
									{
										
										 row = sheet.createRow((short)i++);
							       
										row.createCell((short) 0).setCellValue(""+srno);
										
										row.createCell((short) 1).setCellValue(""+rs.getString("empcode"));
										
										row.createCell((short) 2).setCellValue(""+rs.getString("name"));
										
										row.createCell((short) 3).setCellValue(""+rs.getDate("DOJ")==null?"":format.format(rs.getDate("DOJ")));

							       
										row.createCell((short) 4).setCellValue(""+rs.getString("Basic"));
										row.createCell((short) 5).setCellValue(""+rs.getString("DA"));
										row.createCell((short) 6).setCellValue(""+rs.getString("HRA"));
										row.createCell((short) 7).setCellValue(""+rs.getString("MEDICAL"));
										row.createCell((short) 8).setCellValue(""+rs.getString("EDUCATION"));
										row.createCell((short) 9).setCellValue(""+rs.getString("OTHERALLOWANCE"));
										row.createCell((short) 10).setCellValue(""+rs.getString("BONUS"));
										row.createCell((short) 11).setCellValue(""+rs.getString("VDA"));
										row.createCell((short) 12).setCellValue(""+rs.getString("CLOSING"));
										row.createCell((short) 13).setCellValue(""+rs.getString("AREABDV"));
										row.createCell((short) 14).setCellValue(""+rs.getString("AREAALL"));
										row.createCell((short) 15).setCellValue(""+rs.getString("ARREARS"));
										if(rs.getString("pfv").equals("1")){
										row.createCell((short) 16).setCellValue("Applicable");
										}
										else{
											row.createCell((short) 16).setCellValue("Not Applicable");
										}
										
										if(rs.getString("pt").equals("1")){
											row.createCell((short) 17).setCellValue("Applicable");
											}
											else{
												row.createCell((short) 17).setCellValue("Not Applicable");
											}
										
										if(rs.getString("esic").equals("1")){
											row.createCell((short) 18).setCellValue("Applicable");
											}
											else{
												row.createCell((short) 18).setCellValue("Not Applicable");
											}
										//row.createCell((short) 16).setCellValue(rs.getString("pt"));
										//row.createCell((short) 17).setCellValue( rs.getInt("esic"));
									
										
										srno++;				
					}    
							
										//HSSFRow row = sheet.createRow((short)i++);
							//}				
									//row.createCell((short) 17).setCellValue("TOTAL:-");
									//row.createCell((short) 18).setCellValue(""+totatlbonus);
									String filePath1 = getServletContext().getRealPath("")+ File.separator + "CTCReport_"+date+".xls";
									FileOutputStream fileOut =  new FileOutputStream(filePath1);//"D:\\PFList.xls"
									hwb.write(fileOut);
									fileOut.close();
									
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
								
					 
					 
					 
				 }
			
			} catch (Exception e) {
				//UsrHandler.senderrormail(e,"ReportServlet.getSalDetails");
				e.printStackTrace();
			}
		}
		
		else if(action.equalsIgnoreCase("tdslist"))
		{

			System.out.println("in new Attendsheet  serv");
			String year = request.getParameter("year");
			String endyear = request.getParameter("endyear");
			year="01-Apr-"+year;
			endyear="31-Mar-"+endyear;
		
			
			
			
			
			String filename="";
			filename="NewTDSReport.pdf";
			String filePath = getServletContext().getRealPath("")+ File.separator + ""+filename;
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			
			UtilityDAO.tdsList(year,endyear,imagepath, filePath);	
			
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
		
		
		else if (action.equalsIgnoreCase("transferreport")) //@ni
		{
			System.out.println("i am in transferreport ");
			String Type =request.getParameter("reporttyp");
			System.out.println("Type::  "+Type);
			String filePath = getServletContext().getRealPath("")+ File.separator + "EmpTransferReport.pdf";
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			String fromdate="";
			String todate = "";
			
		try{
			
			if(Type.equalsIgnoreCase("1")){
			UtilityDAO.transferreport("","",Type,filePath,imagepath);
			}
			else if(Type.equalsIgnoreCase("2")){
				String FrmDate = "";
				String ToDate = "";
				 fromdate =request.getParameter("fromdate");
				 todate =request.getParameter("tdate");
				 SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
				 SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
				 
				    try {
						 FrmDate = output.format(sdf.parse(fromdate));
					     ToDate = output.format(sdf.parse(todate));
					} catch (java.text.ParseException e1) {
						e1.printStackTrace();
					}
				 
				 System.out.println("fromdate ::  "+FrmDate+"  todate::   "+ToDate);
				 UtilityDAO.transferreport(FrmDate,ToDate,Type,filePath,imagepath);
			}
			else if(Type.equalsIgnoreCase("3")){
				String empNo = request.getParameter("EMPNO");
				String[] parts = empNo.split(":");
				String empnumber = parts[2];
				UtilityDAO.transferreportofSpecificEmp(empnumber,Type,filePath,imagepath);
			}
			else if(Type.equalsIgnoreCase("4")){
				String branchtype = request.getParameter("branchtyp");
				String  branchName = request.getParameter("Branch")==null?"0":request.getParameter("Branch"); 
				String  branchfrom = request.getParameter("rangeFrom")==null?"0":request.getParameter("rangeFrom"); 
				String  branchto = request.getParameter("rangeTo")==null?"0":request.getParameter("rangeTo"); 
				String Year = request.getParameter("transferYear");
				UtilityDAO.BranchWisetransferreport(Type,branchtype,branchName,branchfrom,branchto,Year,filePath,imagepath);
			}
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
			
			
		} catch (SQLException | DocumentException e) {
				
				e.printStackTrace();
			}
			/*PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.write("<iframe scrolling=\"auto\" src=\"EmpTransferReport.pdf\" height=\"400px\" width=\"100%\"></iframe>");
			*/
			
		} 	
		
		else if(action.equalsIgnoreCase("checktransferlist"))
		{
			String Type =request.getParameter("type");
			String EmpSql="";
			String flag="";
			
			try{
			Connection conn=ConnectionManager.getConnection();
			Statement st= conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			if(Type.equalsIgnoreCase("1")){
			 EmpSql=("select p.site_name,m.EMPNO,m.EMPCODE,m.LNAME,m.FNAME ,m.MNAME,l.LKP_DISC ,  h.site_Name "+
										"from empmast m , EMPTRAN e , Project_Sites p , EMPTRAN k , Project_Sites h , LOOKUP l "+
										"where m.EMPNO = e.EMPNO and e.PRJ_SRNO = p.SITE_ID and k.empno = e.EMPNO and h.SITE_ID = k.PRJ_SRNO "+
										"and k.SRNO = (select MAX(srno) - 1 from emptran c where c.EMPNO = m.EMPNO  ) "+
										"and l.LKP_SRNO = e.DESIG   and l.LKP_CODE = 'DESIG' "+
										"and e.SRNO = (select MAX(srno) from emptran b where b.EMPNO = m.EMPNO  ) "+
										"and m.STATUS = 'A' "+
										" order by m.empcode ") ;
			}
			else if(Type.equalsIgnoreCase("2")){
				String FrmDate = "";
				String ToDate = "";
				String fromdate=request.getParameter("fromdate");
				String todate=request.getParameter("tdate");
				System.out.println("fromdate::  "+fromdate+"  todate:: "+todate);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
			    SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
			 
			    try {
					 FrmDate = output.format(sdf.parse(fromdate));
				     ToDate = output.format(sdf.parse(todate));
				     System.out.println("FrmDate::  "+FrmDate+"  ToDate:: "+ToDate); 
			    } catch (java.text.ParseException e1) {
					e1.printStackTrace();
				}
				
				EmpSql=("select p.site_name,m.EMPNO,m.EMPCODE,m.LNAME,m.FNAME ,m.MNAME,l.LKP_DISC ,  h.site_Name "+
						"from empmast m , EMPTRAN e , Project_Sites p , EMPTRAN k , Project_Sites h , LOOKUP l "+
						"where m.EMPNO = e.EMPNO and e.PRJ_SRNO = p.SITE_ID and k.empno = e.EMPNO and h.SITE_ID = k.PRJ_SRNO "+
						"and k.SRNO = (select MAX(srno) - 1 from emptran c where c.EMPNO = m.EMPNO  ) "+
						"and l.LKP_SRNO = e.DESIG   and l.LKP_CODE = 'DESIG' "+
						"and e.SRNO = (select MAX(srno) from emptran b where b.EMPNO = m.EMPNO  ) "+
						"and m.STATUS = 'A' "+
						"and e.oRDER_DT  between '"+FrmDate+"' and '"+ToDate+"' order by m.empcode ") ;
			}
			    
			else if(Type.equalsIgnoreCase("3")){
				String empNo = request.getParameter("empno");
				String[] parts = empNo.split(":");
				String empnumber = parts[2];
				 EmpSql = ""
						+ "select p.site_name,m.EMPNO,m.EMPCODE,m.LNAME,m.FNAME ,m.MNAME,l.LKP_DISC "
						+ "		,e.EFFDATE,e.ORDER_DT "
						+ "from	empmast m , "
						+ "		EMPTRAN e , "
						+ "		Project_Sites p , "
						+ "		LOOKUP l "
						+ "where m.EMPNO = e.EMPNO "
						+ "and e.PRJ_SRNO = p.SITE_ID "
						+ "and e.empno = m.EMPNO "
						+ "and m.EMPNO = "+empnumber+" "
						+ " and l.LKP_SRNO = e.DESIG "
						+ "and l.LKP_CODE = 'DESIG' "
						+ "and m.STATUS = 'A' "
						+ "order by e.EFFDATE";

			}
			else if(Type.equalsIgnoreCase("4")){
				EmpSql=("select p.site_name,m.EMPNO,m.EMPCODE,m.LNAME,m.FNAME ,m.MNAME,l.LKP_DISC ,  h.site_Name "+
						"from empmast m , EMPTRAN e , Project_Sites p , EMPTRAN k , Project_Sites h , LOOKUP l "+
						"where m.EMPNO = e.EMPNO and e.PRJ_SRNO = p.SITE_ID and k.empno = e.EMPNO and h.SITE_ID = k.PRJ_SRNO "+
						"and k.SRNO = (select MAX(srno) - 1 from emptran c where c.EMPNO = m.EMPNO  ) "+
						"and l.LKP_SRNO = e.DESIG   and l.LKP_CODE = 'DESIG' "+
						"and e.SRNO = (select MAX(srno) from emptran b where b.EMPNO = m.EMPNO  ) "+
						"and m.STATUS = 'A' "+
						" order by m.empcode ") ;
			}
			ResultSet emp = st.executeQuery(EmpSql);

			if(!emp.next())
			{
				flag="false";
			}
			else
			{
				flag="true";
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
			
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.write(flag);
	}
		
		
	// For Lic list 21.08..
		
		else if(action.equalsIgnoreCase("liclist"))
		{
			System.out.println("i am in liclist");
			final int BUFSIZE = 4096;
			String filePath = getServletContext().getRealPath("")+ File.separator + "liclist.pdf";
			String date=request.getParameter("date");
			date="01-"+date;
			String status=request.getParameter("licstatus");
			System.out.println("stststtattata....."+status);
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			try {
				
			UtilityDAO.getLICList(filePath,date,imagepath,status);
				
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
			} catch (SQLException | DocumentException e) {
				
				e.printStackTrace();
			}
			 
		}
		
		
//   For Bonus Report...
		else if(action.equalsIgnoreCase("bonus"))
		{
			System.out.println("i am in bonusReport");
			String type = request.getParameter("type");
			System.out.println("type :  "+type);
			int year=0,nextYear=0;
			
			
			String filePath = getServletContext().getRealPath("")+ File.separator + "bonuslist.pdf";
			String filepath=getServletContext().getRealPath("")+ File.separator + "Bonus_List.xls";
			String FilePath=getServletContext().getRealPath("")+ File.separator + "Bonus_Tentative_List.xls";
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			
			
			
			try {
				
				if(type.equalsIgnoreCase("Summary")){	
					System.out.println("i am in Bonus Summary");
						UtilityDAO.SummaryBonusList(filePath,imagepath);
					
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
			}
			catch (SQLException | DocumentException e) {
					
					e.printStackTrace();
				}
			
			
			if((type.equalsIgnoreCase("Details")))
			{
			 String  reportType = request.getParameter("branchtyp");	
			 String branch = request.getParameter("Branch");
			 String desig = request.getParameter("dewise");
			 String fromyear = request.getParameter("bonusfrom");
			 String toyear = request.getParameter("bonusTo");
			 String Year = fromyear+"-"+toyear;
			 ExcelDAO.BonusListExcel(type,reportType,branch,desig,Year,filepath,imagepath);
			 try
				{
				
					final int BUFSIZE = 4096;
					File file = new File(filepath);
					int length = 0;
					ServletOutputStream outStream = response.getOutputStream();
					ServletContext context = getServletConfig().getServletContext();
					String mimetype = context.getMimeType(filepath);
					if (mimetype == null) 
					{
						mimetype = "application/octet-stream";
					}
					response.setContentType(mimetype);
					response.setContentLength((int) file.length());
					String fileName = (new File(filepath)).getName();
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
				catch ( Exception e) 
				{
					e.printStackTrace();
				}
			}
			else if((type.equalsIgnoreCase("Tentative")))
			{
				String stringYear=(request.getParameter("date")==null?(String)session.getAttribute("year").toString():request.getParameter("date"));
				year=Integer.parseInt(stringYear);
				nextYear=year+1; 
				float percent=Float.parseFloat(request.getParameter("percent")==null?"0.0":request.getParameter("percent"));
			    ExcelDAO.BonusTentativeReport(type,year,nextYear,percent,FilePath,imagepath);
			 try
				{
				
					final int BUFSIZE = 4096;
					File file = new File(FilePath);
					int length = 0;
					ServletOutputStream outStream = response.getOutputStream();
					ServletContext context = getServletConfig().getServletContext();
					String mimetype = context.getMimeType(filepath);
					if (mimetype == null) 
					{
						mimetype = "application/octet-stream";
					}
					response.setContentType(mimetype);
					response.setContentLength((int) file.length());
					String fileName = (new File(filepath)).getName();
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
				catch ( Exception e) 
				{
					e.printStackTrace();
				}
			}
			else if((type.equalsIgnoreCase("EX_GRACIA")))
			{
			 String  reportType = request.getParameter("branchtyp");	
			 String branch = request.getParameter("Branch");
			 String desig = request.getParameter("dewise");
			 float percent=Float.parseFloat(request.getParameter("percent")==null?"0.0":request.getParameter("percent"));
			 String fromyear = request.getParameter("bonusfrom");
			 String toyear = request.getParameter("bonusTo");
			 String Year = fromyear+"-"+toyear;
			 String Month = request.getParameter("month");
			 System.out.println("Month:: "+Month);
			 String[] parts = Month.split(":");
			 Month = parts[1]; 
			 String part2 = parts[1];
			 String MonthDT="01-"+part2+"-"+toyear;
			 	 SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
			 		 try {
						df.parse(MonthDT);
						System.out.println("Date is: "+df.parse(MonthDT));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			 
			 ExcelDAO.Ex_Gracia(type,reportType,branch,desig,Year,Month,MonthDT,percent,filepath,imagepath);
			 try
				{
				
					final int BUFSIZE = 4096;
					File file = new File(filepath);
					int length = 0;
					ServletOutputStream outStream = response.getOutputStream();
					ServletContext context = getServletConfig().getServletContext();
					String mimetype = context.getMimeType(filepath);
					if (mimetype == null) 
					{
						mimetype = "application/octet-stream";
					}
					response.setContentType(mimetype);
					response.setContentLength((int) file.length());
					String fileName = (new File(filepath)).getName();
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
				catch ( Exception e) 
				{
					e.printStackTrace();
				}
			}
			
		}
		
		else if(action.equalsIgnoreCase("extradutypaymentreport"))
		{
			String type=request.getParameter("type");
			String val=type.equalsIgnoreCase("Empwise")?request.getParameter("EMPNO"):request.getParameter("date1");
			System.out.println("report type..."+type);
			System.out.println("val..."+val);
			try
			{	
				//	Method Calls for Pdf Report by type selected .....
				String filePath1 = getServletContext().getRealPath("")+ File.separator + "Extra_Duty_Payment_"+type+".pdf";
				String imagepath =getServletContext().getRealPath("/images/plogo.png");
				ReportDAOPDF.Extra_Duty_Payment_Report(val, type, filePath1,imagepath);
				
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
			}catch ( Exception e){		e.printStackTrace();	}
			
		
		}
		
		else if (action.equalsIgnoreCase("salaryreport")) 
		{
			String date = request.getParameter("date");
			date="01-"+date;
			String code = request.getParameter("trncd");
			String ptcode=request.getParameter("pttrncd")==""?"0":request.getParameter("pttrncd");
			String table = request.getParameter("tname");
			String fname = request.getParameter("fname");
			System.out.println("table = "+table);
			System.out.println("trncd = "+code);
			System.out.println("pttrncd = "+ptcode);
			String filePath = getServletContext().getRealPath("")+ File.separator +fname+"_"+request.getParameter("date")+".pdf";
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			
			if(code.equalsIgnoreCase("GI")){
				System.out.println("AJ..");
				UtilityDAO.GroupInsuranceReport(date,table,filePath,imagepath);
			}else if(code.equalsIgnoreCase("BDVabove1500")){
				UtilityDAO.BDVabove1500(date,table,filePath,imagepath);
			}
			else{
			      UtilityDAO.SalaryReport(date, filePath,imagepath,code,table,ptcode);
				}
			try
			{
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
			catch ( Exception e) 
			{
				e.printStackTrace();
			}
		} 	
		
		
		else if (action.equalsIgnoreCase("leavereport")) 
		{
			String empno = request.getParameter("getempno");
			int leavestatus = Integer.parseInt(request.getParameter("leavestatus"));
			String frmdate="01-"+request.getParameter("frmdate");
			String todate ="01-"+ request.getParameter("todate");
			String status = leavestatus==01?"ALL":leavestatus==02?"SANCTION":leavestatus==03?"PENDING":
								  leavestatus==04?"CANCEL":leavestatus==05?"NEGATIVE":"L-MARK";
			System.out.println("leavereport empno = "+empno);
			System.out.println("leavestatus = "+leavestatus);
			System.out.println("frmdate = "+frmdate);
			System.out.println("todate = "+todate);
			String filePath = getServletContext().getRealPath("")+ File.separator +"LeaveReport_"+status+".pdf";
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			
			UtilityDAO.LeaveReport(empno,frmdate,todate,status,filePath,imagepath);
			try
			{
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
			catch ( Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		else if (action.equalsIgnoreCase("lesssalary25report")) 
		{
		
			String frmdate="01-"+request.getParameter("frmdate");
			
			String filePath = getServletContext().getRealPath("")+ File.separator +"SalaryLess25%Report_"+frmdate+".pdf";
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			
			UtilityDAO.LessSalaryReport(frmdate,filePath,imagepath);
			try
			{
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
			catch ( Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		
		else if (action.equalsIgnoreCase("staffsalaryreport")) 
		{
		
			//String empno = request.getParameter("getempno");
			String enamecode=request.getParameter("EMPNO");
			String empcode[]=enamecode.split(":");
			String empno=empcode[2];
			String frmdate="01-"+request.getParameter("frmdate");
			String filePath = getServletContext().getRealPath("")+ File.separator +"StaffSalaryDetails_"+empcode[1]+"_"+frmdate+".pdf";
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			
			UtilityDAO.StaffSalaryDetailsReport(empno,enamecode,frmdate,filePath,imagepath);
			try
			{
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
			catch ( Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		// Leave Reports New..
		
		else if (action.equalsIgnoreCase("leaveReportnew")) 
		{
			System.out.println("i am in ajax...");
			String type="";
			 type=request.getParameter("check")==null?"0":request.getParameter("check");
			
			PrintWriter out=new PrintWriter("text");
			if(type.equalsIgnoreCase("balanceLeaveList"))
			{
				String filePath = getServletContext().getRealPath("")+ File.separator +"Balance Leave List.pdf";
				String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
		
				String Branch1 = request.getParameter("branchtyp")==null?"0":request.getParameter("branchtyp");
				String SelectedBranch1 = request.getParameter("Branch")==null?"0":request.getParameter("Branch");
				String rangefrom1 = request.getParameter("rangeFrom")==null?"0":request.getParameter("rangeFrom");
				String rangeTo1 = request.getParameter("rangeTo")==null?"0":request.getParameter("rangeTo"); 
			
					UtilityDAO.BalanceLeaveList(Branch1,SelectedBranch1,rangefrom1,rangeTo1,filePath,imagepath);
					try
					{
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
					catch ( Exception e) 
					{
						e.printStackTrace();
					}
					}
			
			 if(type.equalsIgnoreCase("LeaveAvailedList")){
			//	date="01-"+date;
				System.out.println("i am in LeaveAvailedList");
				String filePath1 = getServletContext().getRealPath("")+ File.separator +"Leave Availed List.pdf";
				String imagepath1 =getServletContext().getRealPath("/images/BusinessBankLogo.png");
				
				String frmdate1 = request.getParameter("frmdate")==null?"0":request.getParameter("frmdate");
				String todate1 = request.getParameter("todate")==null?"0":request.getParameter("todate");
				String Branch1 = request.getParameter("branchtyp")==null?"0":request.getParameter("branchtyp");
				String SelectedBranch1 = request.getParameter("Branch")==null?"0":request.getParameter("Branch");
				String rangefrom1 = request.getParameter("rangeFrom")==null?"0":request.getParameter("rangeFrom");
				String rangeTo1 = request.getParameter("rangeTo")==null?"0":request.getParameter("rangeTo"); 
				
				UtilityDAO.LeaveAvailedList(Branch1,SelectedBranch1,rangefrom1,rangeTo1,frmdate1,todate1,filePath1,imagepath1);
				
				try
				{
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
				}
				catch ( Exception e) 
				{
					e.printStackTrace();
				}
			}
				
	//  Leave Availed Detail List...
			 if(type.equalsIgnoreCase("LeaveAvailedDetailList")){
				System.out.println("i am in LeaveAvailedDetailList");
				String filePath2 = getServletContext().getRealPath("")+ File.separator +"Leave Availed Detail List.pdf";
				String imagepath2 =getServletContext().getRealPath("/images/BusinessBankLogo.png");
				
				String frmdate1 = request.getParameter("frmdate")==null?"0":request.getParameter("frmdate");
				String todate1 = request.getParameter("todate")==null?"0":request.getParameter("todate");
				String LType1 = request.getParameter("leavetyp")==null?"0":request.getParameter("leavetyp");
				String leaveType1 = request.getParameter("Leave")==null?"0":request.getParameter("Leave");
				String EType1 = request.getParameter("emptyp")==null?"0":request.getParameter("emptyp");
				String Branch1 = request.getParameter("branchtyp")==null?"0":request.getParameter("branchtyp");
				String SelectedBranch1 = request.getParameter("Branch")==null?"0":request.getParameter("Branch");
				String rangefrom1 = request.getParameter("rangeFrom")==null?"0":request.getParameter("rangeFrom");
				String rangeTo1 = request.getParameter("rangeTo")==null?"0":request.getParameter("rangeTo"); 
				String SelectType = request.getParameter("empBranch")==null?"0":request.getParameter("empBranch");
				String empno1="";
				
				
				if(EType1.equalsIgnoreCase("2")){
				String emp = request.getParameter("empno");
				String empno[]=emp.split(":");
			    empno1= empno[2];
				}
				
				UtilityDAO.LeaveAvailedDetailList(Branch1,SelectedBranch1,rangefrom1,rangeTo1,LType1,frmdate1,todate1,empno1,
						                   		  EType1,leaveType1,SelectType,filePath2,imagepath2);
				
				try
				{
					final int BUFSIZE = 4096;
					File file = new File(filePath2);
					int length = 0;
					ServletOutputStream outStream = response.getOutputStream();
					ServletContext context = getServletConfig().getServletContext();
					String mimetype = context.getMimeType(filePath2);
					if (mimetype == null) 
					{
						mimetype = "application/octet-stream";
					}
					response.setContentType(mimetype);
					response.setContentLength((int) file.length());
					String fileName = (new File(filePath2)).getName();
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
				catch ( Exception e) 
				{
					e.printStackTrace();
				}
			}
			
			// Cancel Leave List...
			
			 if(type.equalsIgnoreCase("cancelLeaveList")){
				System.out.println("i am in cancelLeaveList");
				
				String filePath3 = getServletContext().getRealPath("")+ File.separator +"cancel Leave List.pdf";
				String imagepath3 =getServletContext().getRealPath("/images/BusinessBankLogo.png");
				PrintWriter out1=new PrintWriter("text");
				
				String frmdate1 = request.getParameter("frmdate")==null?"0":request.getParameter("frmdate");
				String todate1 = request.getParameter("todate")==null?"0":request.getParameter("todate");
				String Branch1 = request.getParameter("branchtyp")==null?"0":request.getParameter("branchtyp");
				String SelectedBranch1 = request.getParameter("Branch")==null?"0":request.getParameter("Branch");
				String rangefrom1 = request.getParameter("rangeFrom")==null?"0":request.getParameter("rangeFrom");
				String rangeTo1 = request.getParameter("rangeTo")==null?"0":request.getParameter("rangeTo"); 
				
				UtilityDAO.CancelLeaveList(Branch1,SelectedBranch1,rangefrom1,rangeTo1,frmdate1,todate1,filePath3,imagepath3);
				try
				{
					final int BUFSIZE = 4096;
					File file = new File(filePath3);
					int length = 0;
					ServletOutputStream outStream = response.getOutputStream();
					ServletContext context = getServletConfig().getServletContext();
					String mimetype = context.getMimeType(filePath3);
					if (mimetype == null) 
					{
						mimetype = "application/octet-stream";
					}
					response.setContentType(mimetype);
					response.setContentLength((int) file.length());
					String fileName = (new File(filePath3)).getName();
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
				catch ( Exception e) 
				{
					e.printStackTrace();
				}
			}
			
			// Negative Balance Leave List..
		 if(type.equalsIgnoreCase("negativeBalLeaveList")){
				System.out.println("i am in negativeBalLeaveList");
				String filePath4 = getServletContext().getRealPath("")+ File.separator +"Negative Bal Leave List.pdf";
				String imagepath4 =getServletContext().getRealPath("/images/BusinessBankLogo.png");
				PrintWriter out1=new PrintWriter("text");
				
				String frmdate1 = request.getParameter("frmdate")==null?"0":request.getParameter("frmdate");
				String todate1 = request.getParameter("todate")==null?"0":request.getParameter("todate");
				String Branch1 = request.getParameter("branchtyp")==null?"0":request.getParameter("branchtyp");
				String SelectedBranch1 = request.getParameter("Branch")==null?"0":request.getParameter("Branch");
				String rangefrom1 = request.getParameter("rangeFrom")==null?"0":request.getParameter("rangeFrom");
				String rangeTo1 = request.getParameter("rangeTo")==null?"0":request.getParameter("rangeTo"); 
				
				UtilityDAO.NegativebalanceLeaveList(Branch1,SelectedBranch1,rangefrom1,rangeTo1,frmdate1,todate1,filePath4,imagepath4);
				try
				{
					final int BUFSIZE = 4096;
					File file = new File(filePath4);
					int length = 0;
					ServletOutputStream outStream = response.getOutputStream();
					ServletContext context = getServletConfig().getServletContext();
					String mimetype = context.getMimeType(filePath4);
					if (mimetype == null) 
					{
						mimetype = "application/octet-stream";
					}
					response.setContentType(mimetype);
					response.setContentLength((int) file.length());
					String fileName = (new File(filePath4)).getName();
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
				catch ( Exception e) 
				{
					e.printStackTrace();
				}
			}
			
			// Not Sanction Leave List
		 if(type.equalsIgnoreCase("notSanctionLeaveList")){
				System.out.println("i am in notSanctionLeaveList");
				String filePath5 = getServletContext().getRealPath("")+ File.separator +"Not Sanction LeaveList.pdf";
				String imagepath5 =getServletContext().getRealPath("/images/BusinessBankLogo.png");
				PrintWriter out5=new PrintWriter("text");
				
				String frmdate1 = request.getParameter("frmdate")==null?"0":request.getParameter("frmdate");
				String todate1 = request.getParameter("todate")==null?"0":request.getParameter("todate");
				String EType1 = request.getParameter("emptyp")==null?"0":request.getParameter("emptyp");
				String Branch1 = request.getParameter("branchtyp")==null?"0":request.getParameter("branchtyp");
				String SelectedBranch1 = request.getParameter("Branch")==null?"0":request.getParameter("Branch");
				String rangefrom1 = request.getParameter("rangeFrom")==null?"0":request.getParameter("rangeFrom");
				String rangeTo1 = request.getParameter("rangeTo")==null?"0":request.getParameter("rangeTo"); 
				String SelectType = request.getParameter("empBranch")==null?"0":request.getParameter("empBranch");
				String empno1="";
				
				if(EType1.equalsIgnoreCase("2")){
				String emp1 = request.getParameter("empno");
				String empno[]=emp1.split(":");
			    empno1= empno[2];
				}
				System.out.println("EType1:  "+EType1);
			    
				UtilityDAO.NotSanctionLeaveList(Branch1,SelectedBranch1,rangefrom1,rangeTo1,frmdate1,todate1,empno1,
         		                                EType1,SelectType,filePath5,imagepath5);
				try
				{
					final int BUFSIZE = 4096;
					File file = new File(filePath5);
					int length = 0;
					ServletOutputStream outStream = response.getOutputStream();
					ServletContext context = getServletConfig().getServletContext();
					String mimetype = context.getMimeType(filePath5);
					if (mimetype == null) 
					{
						mimetype = "application/octet-stream";
					}
					response.setContentType(mimetype);
					response.setContentLength((int) file.length());
					String fileName = (new File(filePath5)).getName();
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
				catch ( Exception e) 
				{
					e.printStackTrace();
				}
			}
				// App. DateWise Leave List..
						 if(type.equalsIgnoreCase("appDateWiseLeaveList")){
							System.out.println("i am in appDateWiseLeaveList");
							String filePath6 = getServletContext().getRealPath("")+ File.separator +"Application Date Wise Leave List.pdf";
							String imagepath6 =getServletContext().getRealPath("/images/BusinessBankLogo.png");
							PrintWriter out1=new PrintWriter("text");
							
							String frmdate7 = request.getParameter("frmdate")==null?"0":request.getParameter("frmdate");
							String todate7 = request.getParameter("todate")==null?"0":request.getParameter("todate");
							String LType7 = request.getParameter("leavetyp")==null?"0":request.getParameter("leavetyp");
							String leaveType7 = request.getParameter("Leave")==null?"0":request.getParameter("Leave");
							String EType7 = request.getParameter("emptyp")==null?"0":request.getParameter("emptyp");
							String Branch7 = request.getParameter("branchtyp")==null?"0":request.getParameter("branchtyp");
							String SelectedBranch7 = request.getParameter("Branch")==null?"0":request.getParameter("Branch");
							String rangefrom7 = request.getParameter("rangeFrom")==null?"0":request.getParameter("rangeFrom");
							String rangeTo7 = request.getParameter("rangeTo")==null?"0":request.getParameter("rangeTo"); 
							String SelectType = request.getParameter("empBranch")==null?"0":request.getParameter("empBranch");
							String empno7="";
							
							if(EType7.equalsIgnoreCase("2")){
							String emp2 = request.getParameter("empno");
							String empno1[]=emp2.split(":");
						    empno7= empno1[2];
							}
							UtilityDAO.AppDateWiseLeaveList(Branch7,SelectedBranch7,rangefrom7,rangeTo7,frmdate7,todate7,empno7,EType7,LType7,leaveType7,SelectType,filePath6,imagepath6);
							try
							{
								final int BUFSIZE = 4096;
								File file = new File(filePath6);
								int length = 0;
								ServletOutputStream outStream = response.getOutputStream();
								ServletContext context = getServletConfig().getServletContext();
								String mimetype = context.getMimeType(filePath6);
								if (mimetype == null) 
								{
									mimetype = "application/octet-stream";
								}
								response.setContentType(mimetype);
								response.setContentLength((int) file.length());
								String fileName = (new File(filePath6)).getName();
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
							catch ( Exception e) 
							{
								e.printStackTrace();
							}
						 }
						 // For Lwp Greater than 25 List..
						 if(type.equalsIgnoreCase("LwpGreater25List")){
							 System.out.println("LwpGreater25List");
							 
							    String filePath = getServletContext().getRealPath("")+ File.separator +"Lwp Greater than 25 List.pdf";
								String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
								PrintWriter out1=new PrintWriter("text");
								
								String frmdate1 = request.getParameter("frmdate")==null?"0":request.getParameter("frmdate");
								String todate1 = request.getParameter("todate")==null?"0":request.getParameter("todate");
								String Branch1 = request.getParameter("branchtyp")==null?"0":request.getParameter("branchtyp");
								String SelectedBranch1 = request.getParameter("Branch")==null?"0":request.getParameter("Branch");
								String rangefrom1 = request.getParameter("rangeFrom")==null?"0":request.getParameter("rangeFrom");
								String rangeTo1 = request.getParameter("rangeTo")==null?"0":request.getParameter("rangeTo"); 
								
								UtilityDAO.LwpGreater25List(Branch1,SelectedBranch1,rangefrom1,rangeTo1,frmdate1,todate1,filePath,imagepath);
								try
								{
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
								catch ( Exception e) 
								{
									e.printStackTrace();
								}
						 }
						 
						// For Lwp List..
						 if(type.equalsIgnoreCase("LwpList")){

								System.out.println("i am in LwpList");
								String filePath = getServletContext().getRealPath("")+ File.separator +"LWP List.pdf";
								String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
								PrintWriter out5=new PrintWriter("text");
								
								String frmdate1 = request.getParameter("frmdate")==null?"0":request.getParameter("frmdate");
								String todate1 = request.getParameter("todate")==null?"0":request.getParameter("todate");
								String EType1 = request.getParameter("emptyp")==null?"0":request.getParameter("emptyp");
								String Branch1 = request.getParameter("branchtyp")==null?"0":request.getParameter("branchtyp");
								String SelectedBranch1 = request.getParameter("Branch")==null?"0":request.getParameter("Branch");
								String rangefrom1 = request.getParameter("rangeFrom")==null?"0":request.getParameter("rangeFrom");
								String rangeTo1 = request.getParameter("rangeTo")==null?"0":request.getParameter("rangeTo"); 
								String SelectType = request.getParameter("empBranch")==null?"0":request.getParameter("empBranch");
								String empno1="";
								
								if(EType1.equalsIgnoreCase("2")){
								String emp1 = request.getParameter("empno");
								String empno[]=emp1.split(":");
							    empno1= empno[2];
								}
								System.out.println("EType1:  "+EType1);
							    
								UtilityDAO.LWPList(Branch1,SelectedBranch1,rangefrom1,rangeTo1,frmdate1,todate1,empno1,
				         		                                EType1,SelectType,filePath,imagepath);
								try
								{
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
								catch ( Exception e) 
								{
									e.printStackTrace();
								}
							
						 }
						 // For Late Marking...
						 if(type.equalsIgnoreCase("lMarking")){
								System.out.println("i am in lMarking");
								
								String filePath3 = getServletContext().getRealPath("")+ File.separator +"Late Marking.pdf";
								String imagepath3 =getServletContext().getRealPath("/images/BusinessBankLogo.png");
								PrintWriter out1=new PrintWriter("text");
								
								String frmdate1 = request.getParameter("frmdate")==null?"0":request.getParameter("frmdate");
								String todate1 = request.getParameter("todate")==null?"0":request.getParameter("todate");
								String Branch1 = request.getParameter("branchtyp")==null?"0":request.getParameter("branchtyp");
								String SelectedBranch1 = request.getParameter("Branch")==null?"0":request.getParameter("Branch");
								String rangefrom1 = request.getParameter("rangeFrom")==null?"0":request.getParameter("rangeFrom");
								String rangeTo1 = request.getParameter("rangeTo")==null?"0":request.getParameter("rangeTo"); 
								
								UtilityDAO.LateMarkList(Branch1,SelectedBranch1,rangefrom1,rangeTo1,frmdate1,todate1,filePath3,imagepath3);
								try
								{
									final int BUFSIZE = 4096;
									File file = new File(filePath3);
									int length = 0;
									ServletOutputStream outStream = response.getOutputStream();
									ServletContext context = getServletConfig().getServletContext();
									String mimetype = context.getMimeType(filePath3);
									if (mimetype == null) 
									{
										mimetype = "application/octet-stream";
									}
									response.setContentType(mimetype);
									response.setContentLength((int) file.length());
									String fileName = (new File(filePath3)).getName();
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
								catch ( Exception e) 
								{
									e.printStackTrace();
								}
							}
						 
						 if(type.equalsIgnoreCase("CreditBalance")){
								System.out.println("i am in CreditBalance");
								
								String filePath3 = getServletContext().getRealPath("")+ File.separator +"LeaveCreditBal.pdf";
								String imagepath3 =getServletContext().getRealPath("/images/BusinessBankLogo.png");
								PrintWriter out1=new PrintWriter("text");
								
								String frmdate1 = request.getParameter("frmdate")==null?"0":request.getParameter("frmdate");
								String todate1 = request.getParameter("todate")==null?"0":request.getParameter("todate");
								String Branch1 = request.getParameter("branchtyp")==null?"0":request.getParameter("branchtyp");
								String SelectedBranch1 = request.getParameter("Branch")==null?"0":request.getParameter("Branch");
								String rangefrom1 = request.getParameter("rangeFrom")==null?"0":request.getParameter("rangeFrom");
								String rangeTo1 = request.getParameter("rangeTo")==null?"0":request.getParameter("rangeTo"); 
								
								UtilityDAO.LeaveCreditBalanceList(Branch1,SelectedBranch1,rangefrom1,rangeTo1,frmdate1,todate1,filePath3,imagepath3);
								try
								{
									final int BUFSIZE = 4096;
									File file = new File(filePath3);
									int length = 0;
									ServletOutputStream outStream = response.getOutputStream();
									ServletContext context = getServletConfig().getServletContext();
									String mimetype = context.getMimeType(filePath3);
									if (mimetype == null) 
									{
										mimetype = "application/octet-stream";
									}
									response.setContentType(mimetype);
									response.setContentLength((int) file.length());
									String fileName = (new File(filePath3)).getName();
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
								catch ( Exception e) 
								{
									e.printStackTrace();
								}
							}

						 
						 if(type.equalsIgnoreCase("BalanceLeave")){
								System.out.println("i am in BalanceLeave");
								
								String filePath3 = getServletContext().getRealPath("")+ File.separator +"BalanceLeaveListDatWise.pdf";
								String imagepath3 =getServletContext().getRealPath("/images/BusinessBankLogo.png");
								PrintWriter out1=new PrintWriter("text");
								
								String frmdate1 = request.getParameter("frmdate")==null?"0":request.getParameter("frmdate");
								String todate1 = request.getParameter("todate")==null?"0":request.getParameter("todate");
								String Branch1 = request.getParameter("branchtyp")==null?"0":request.getParameter("branchtyp");
								String SelectedBranch1 = request.getParameter("Branch")==null?"0":request.getParameter("Branch");
								String rangefrom1 = request.getParameter("rangeFrom")==null?"0":request.getParameter("rangeFrom");
								String rangeTo1 = request.getParameter("rangeTo")==null?"0":request.getParameter("rangeTo"); 
								
								UtilityDAO.BalanceLeaveDateWiseList(Branch1,SelectedBranch1,rangefrom1,rangeTo1,frmdate1,todate1,filePath3,imagepath3);
								try
								{
									final int BUFSIZE = 4096;
									File file = new File(filePath3);
									int length = 0;
									ServletOutputStream outStream = response.getOutputStream();
									ServletContext context = getServletConfig().getServletContext();
									String mimetype = context.getMimeType(filePath3);
									if (mimetype == null) 
									{
										mimetype = "application/octet-stream";
									}
									response.setContentType(mimetype);
									response.setContentLength((int) file.length());
									String fileName = (new File(filePath3)).getName();
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
								catch ( Exception e) 
								{
									e.printStackTrace();
								}
							}

						 
						 // For Leave Encashment List..
						 if(type.equalsIgnoreCase("LeaveEncashList")){
								System.out.println("i am in LeaveEncashList");
								
								String filePath3 = getServletContext().getRealPath("")+ File.separator +"Leave Encashment List.pdf";
								String imagepath3 =getServletContext().getRealPath("/images/BusinessBankLogo.png");
								PrintWriter out1=new PrintWriter("text");
								
								String frmdate1 = request.getParameter("frmdate")==null?"0":request.getParameter("frmdate");
								String todate1 = request.getParameter("todate")==null?"0":request.getParameter("todate");
								String EType1 = request.getParameter("emptyp")==null?"0":request.getParameter("emptyp");
								String Branch1 = request.getParameter("branchtyp")==null?"0":request.getParameter("branchtyp");
								String SelectedBranch1 = request.getParameter("Branch")==null?"0":request.getParameter("Branch");
								String rangefrom1 = request.getParameter("rangeFrom")==null?"0":request.getParameter("rangeFrom");
								String rangeTo1 = request.getParameter("rangeTo")==null?"0":request.getParameter("rangeTo"); 
								String SelectType = request.getParameter("empBranch")==null?"0":request.getParameter("empBranch");
								String empno1="";
								
								if(EType1.equalsIgnoreCase("2")){
								String emp1 = request.getParameter("empno");
								String empno[]=emp1.split(":");
							    empno1= empno[2];
								}
								
								UtilityDAO.LeaveEncashList(Branch1,SelectedBranch1,rangefrom1,rangeTo1,frmdate1,todate1,empno1,
 		                                EType1,SelectType,filePath3,imagepath3);
								try
								{
									final int BUFSIZE = 4096;
									File file = new File(filePath3);
									int length = 0;
									ServletOutputStream outStream = response.getOutputStream();
									ServletContext context = getServletConfig().getServletContext();
									String mimetype = context.getMimeType(filePath3);
									if (mimetype == null) 
									{
										mimetype = "application/octet-stream";
									}
									response.setContentType(mimetype);
									response.setContentLength((int) file.length());
									String fileName = (new File(filePath3)).getName();
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
								catch ( Exception e) 
								{
									e.printStackTrace();
								}
							}
		}
		
		else if (action.equalsIgnoreCase("leavetolic")) 
		{
			
			
			String reporttype = request.getParameter("reporttype");
			
			if(reporttype.equalsIgnoreCase("textfile"))
			{
				String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
				String filePath = getServletContext().getRealPath("")+ File.separator + "LeaveToLic.pdf";
				UtilityDAO.leavetolicPDF(filePath,imagepath);
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
			
			
			if((reporttype.equalsIgnoreCase("excelfile")))
			{
				String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.jpg");
				String filepath=getServletContext().getRealPath("")+ File.separator + "LeaveToLic.xls";
				ExcelDAO.leavetolicexcel(reporttype, filepath,imagepath);
			 try
				{
				
					final int BUFSIZE = 4096;
					File file = new File(filepath);
					int length = 0;
					ServletOutputStream outStream = response.getOutputStream();
					ServletContext context = getServletConfig().getServletContext();
					String mimetype = context.getMimeType(filepath);
					if (mimetype == null) 
					{
						mimetype = "application/octet-stream";
					}
					response.setContentType(mimetype);
					response.setContentLength((int) file.length());
					String fileName = (new File(filepath)).getName();
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
				catch ( Exception e) 
				{
					e.printStackTrace();
				}
			}
		}
		
		else if (action.equalsIgnoreCase("GratuityReport")) 
		{
			
			String emptype = request.getParameter("employeetype");
			String reporttype = request.getParameter("reporttype");
			System.out.println("emptype:  "+emptype);
			String PolicyNum = request.getParameter("policy_num");
			System.out.println("PolicyNum:  "+PolicyNum);
			if((reporttype.equalsIgnoreCase("excelfile")))
			{
				String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.jpg");
				String filepath=getServletContext().getRealPath("")+ File.separator + "Gratuity_Report.xls";
				ExcelDAO.GratuityReportexcel(emptype,reporttype,PolicyNum, filepath,imagepath);
		//		ExcelDAO.GratuityReportexcel(emptype,reporttype, filepath,imagepath);
			 try
				{
				
					final int BUFSIZE = 4096;
					File file = new File(filepath);
					int length = 0;
					ServletOutputStream outStream = response.getOutputStream();
					ServletContext context = getServletConfig().getServletContext();
					String mimetype = context.getMimeType(filepath);
					if (mimetype == null) 
					{
						mimetype = "application/octet-stream";
					}
					response.setContentType(mimetype);
					response.setContentLength((int) file.length());
					String fileName = (new File(filepath)).getName();
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
				catch ( Exception e) 
				{
					e.printStackTrace();
				}
			}
		}
		// Employee Reports New..
		
		else if (action.equalsIgnoreCase("newempAllReport")) 
		{
			int i ;
			System.out.println("i am in employee report new");
			String[] results = request.getParameterValues("chk");
			for ( i = 0; i < results.length; i++) {
			    System.out.println(results[i]); 
			}
			
			System.out.println("size is: "+results.length);
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.jpg");
			String filepath=getServletContext().getRealPath("")+ File.separator + "Employee_List.xls";
			String SelectedBranch = request.getParameter("Branch")==null?"0":request.getParameter("Branch");
			String BranchType = request.getParameter("branchtyp")==null?"0":request.getParameter("branchtyp");
			String desigtype = request.getParameter("dewise")==null?"0":request.getParameter("dewise");
			String RangeFrom = request.getParameter("rangeFrom")==null?"0":request.getParameter("rangeFrom");
			String RangeTo = request.getParameter("rangeTo")==null?"0":request.getParameter("rangeTo");
			String DeptType = request.getParameter("deptwise")==null?"0":request.getParameter("deptwise");
			if(BranchType.equalsIgnoreCase("3")|| BranchType.equalsIgnoreCase("5")){
				ExcelDAO.employee_desigWiselistexcel(results,BranchType,SelectedBranch,desigtype,DeptType,filepath,imagepath);
			}
			else{
				ExcelDAO.employeelistexcel(results,BranchType,SelectedBranch,desigtype,RangeFrom,RangeTo,filepath,imagepath);
			}
		//	  ExcelDAO.employeelistexcel(results,filepath,imagepath);
		 try
			{
			
				final int BUFSIZE = 4096;
				File file = new File(filepath);
				int length = 0;
				ServletOutputStream outStream = response.getOutputStream();
				ServletContext context = getServletConfig().getServletContext();
				String mimetype = context.getMimeType(filepath);
				if (mimetype == null) 
				{
					mimetype = "application/octet-stream";
				}
				response.setContentType(mimetype);
				response.setContentLength((int) file.length());
				String fileName = (new File(filepath)).getName();
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
			catch ( Exception e) 
			{
				e.printStackTrace();
			}
			
		}
		
		else if (action.equalsIgnoreCase("YearlyEarDedReport")) 
		{
			
			System.out.println("i am here now.....");
			String dt="01-"+request.getParameter("date");
			String employeetype = request.getParameter("employeetype");
			String rpttype = request.getParameter("rpttype");
			String reporttype = request.getParameter("reporttype");
			System.out.println(dt+"..."+employeetype+"..."+rpttype+"..."+reporttype);
			if((reporttype.equalsIgnoreCase("excelfile")))
			{
				String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.jpg");
				String filepath=getServletContext().getRealPath("")+ File.separator +"YearlyEarDedReport.xls";
				ExcelDAO.YearlyEarDedReportexcel(dt,employeetype,rpttype,filepath,imagepath);
			 try
				{
				
					final int BUFSIZE = 4096;
					File file = new File(filepath);
					int length = 0;
					ServletOutputStream outStream = response.getOutputStream();
					ServletContext context = getServletConfig().getServletContext();
					String mimetype = context.getMimeType(filepath);
					if (mimetype == null) 
					{
						mimetype = "application/octet-stream";
					}
					response.setContentType(mimetype);
					response.setContentLength((int) file.length());
					String fileName = (new File(filepath)).getName();
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
				catch ( Exception e) 
				{
					e.printStackTrace();
				}
			}
		}
		
		
		else if (action.equalsIgnoreCase("YearlyEarBreakUp")) 
		{
			
			System.out.println("i am here now YearlyEarBreakUp");
			String dt="01-"+request.getParameter("date");
			String fromDate = request.getParameter("frmdate");
			String toDate = request.getParameter("todate");
			
				String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.jpg");
				String filepath=getServletContext().getRealPath("")+ File.separator +"YearlyEarningReport_WithMonthly_BreakUp.xls";
				ExcelDAO.YearlyEarningReport_WithMonthly_BreakUp(fromDate,toDate,filepath,imagepath);
			 try
				{
				
					final int BUFSIZE = 4096;
					File file = new File(filepath);
					int length = 0;
					ServletOutputStream outStream = response.getOutputStream();
					ServletContext context = getServletConfig().getServletContext();
					String mimetype = context.getMimeType(filepath);
					if (mimetype == null) 
					{
						mimetype = "application/octet-stream";
					}
					response.setContentType(mimetype);
					response.setContentLength((int) file.length());
					String fileName = (new File(filepath)).getName();
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
				catch ( Exception e) 
				{
					e.printStackTrace();
				}
			}
			else if (action.equalsIgnoreCase("LWPCreditDebitReport")){
				String Type =request.getParameter("reporttyp");
				String transactionType =request.getParameter("transtype");
				System.out.println("Type::  "+Type);
				String filePath = getServletContext().getRealPath("")+ File.separator + "LWPCreditDebitReport.pdf";
				String imagepath =getServletContext().getRealPath("/images/plogo.png");
				String fromdate="";
				String todate = "";
				String FrmDate = "";
				String ToDate = "";
				
				try{
					
					if(Type.equalsIgnoreCase("2") ){
						
						 fromdate =request.getParameter("fromdate");
						 todate =request.getParameter("tdate");
						 SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
						 SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
						 
						    try {
								 FrmDate = output.format(sdf.parse(fromdate));
							     ToDate = output.format(sdf.parse(todate));
							} catch (java.text.ParseException e1) {
								e1.printStackTrace();
							}
					//	 UtilityDAO.LWPCreditDebitReport(Type,transactionType,FrmDate,ToDate,filePath,imagepath);
						 System.out.println("fromdate ::  "+FrmDate+"  todate::   "+ToDate);
						}
						UtilityDAO.LWPCreditDebitReport(Type,transactionType,FrmDate,ToDate,filePath,imagepath);
					
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
		
		
	} catch (SQLException | DocumentException e) {
			
			e.printStackTrace();
		}
				
			
			}
			else if (action.equalsIgnoreCase("RetirementCreditLeave")) 
			{/*
				String fromDate = request.getParameter("frmdate");
				String toDate = request.getParameter("todate");
				
					String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.jpg");
					String filepath=getServletContext().getRealPath("")+ File.separator +"Retirement Extension Leave Credit.xls";
					ExcelDAO.Retirement_ext_Credit_Leave(fromDate,toDate,filepath,imagepath);
				 try
					{
					
						final int BUFSIZE = 4096;
						File file = new File(filepath);
						int length = 0;
						ServletOutputStream outStream = response.getOutputStream();
						ServletContext context = getServletConfig().getServletContext();
						String mimetype = context.getMimeType(filepath);
						if (mimetype == null) 
						{
							mimetype = "application/octet-stream";
						}
						response.setContentType(mimetype);
						response.setContentLength((int) file.length());
						String fileName = (new File(filepath)).getName();
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
					catch ( Exception e) 
					{
						e.printStackTrace();
					}
				*/
				

				String fromDate = request.getParameter("frmdate");
				String toDate = request.getParameter("todate");
				String format=request.getParameter("format");
			
				
				if(format.equalsIgnoreCase("Excel"))
						{
					String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.jpg");
					String filepath=getServletContext().getRealPath("")+ File.separator +"Retirement Extension Leave Credit.xls";
					ExcelDAO.Retirement_ext_Credit_Leave(fromDate,toDate,filepath,imagepath);
					
				
				 try
					{
					
						final int BUFSIZE = 4096;
						File file = new File(filepath);
						int length = 0;
						ServletOutputStream outStream = response.getOutputStream();
						ServletContext context = getServletConfig().getServletContext();
						String mimetype = context.getMimeType(filepath);
						if (mimetype == null) 
						{
							mimetype = "application/octet-stream";
						}
						response.setContentType(mimetype);
						response.setContentLength((int) file.length());
						String fileName = (new File(filepath)).getName();
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
					catch ( Exception e) 
					{
						e.printStackTrace();
					}
				}
				
				/*if(format.equalsIgnoreCase("Pdf"))*/
				else
				{
					String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
					String filepath1=getServletContext().getRealPath("")+ File.separator +"Retirement Extension Leave Credit.pdf";
					ReportDAOPDF.RetirementExtCreditLeave(fromDate,toDate,filepath1,imagepath);
					
					
					/*String imagepath =getServletContext().getRealPath("/images/plogo.png");				
					String filepath1=getServletContext().getRealPath("")+ File.separator +"Retirement_Extension_Leave_Credit.pdf";
					ReportDAOPDF.RetirementExtCreditLeave(fromDate,toDate,filepath1,imagepath);*/
					
					try
					{	System.out.println("aj: 1");
						//	Method Calls for Pdf Report by type selected .....
					
					
						final int BUFSIZE = 4096;
						File file = new File(filepath1);
						int length = 0;
						ServletOutputStream outStream = response.getOutputStream();
						ServletContext context = getServletConfig().getServletContext();
						String mimetype = context.getMimeType(filepath1);
						if (mimetype == null) 
						{
							mimetype = "application/octet-stream";
						}
						
						response.setContentType(mimetype);
						response.setContentLength((int) file.length());
						String fileName = (new File(filepath1)).getName();
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
					
						
						/*System.out.println("hi.......");
						response.setContentType(mimetype);
						response.setContentLength((int) file.length());
						String fileName = (new File(filepath1)).getName();
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
						}*/
					}catch ( Exception e){		e.printStackTrace();	}
					
					
				}
			
			
			}
			else if (action.equalsIgnoreCase("LeftEmployeeReason")) 
			{
				String fromDate = request.getParameter("frmdate");
				String toDate = request.getParameter("todate");
				String type = request.getParameter("reporttyp");
				String Branch = request.getParameter("Branch");
				String Desig = request.getParameter("dewise");
				String LeftRepType = request.getParameter("LeftRep");
				String format = request.getParameter("format");
				
				if(type.equalsIgnoreCase("10")){
					String filePath = getServletContext().getRealPath("")+ File.separator +"Employee Retiring List.pdf";
					String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
					UtilityDAO.EmployeeRetiringList(fromDate,toDate,Branch,Desig,LeftRepType, filePath,imagepath);
					try
					{
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
					catch ( Exception e) 
					{
						e.printStackTrace();
					}
				}
				else{
					
					if(format.equalsIgnoreCase("Excel"))
					{
					
					String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.jpg");
					String filepath=getServletContext().getRealPath("")+ File.separator +"Left Employee Report.xls";
					ExcelDAO.Left_Employee_Report(fromDate,toDate,type,Branch,Desig,LeftRepType,filepath,imagepath);
					
				 try
					{
					
						final int BUFSIZE = 4096;
						File file = new File(filepath);
						int length = 0;
						ServletOutputStream outStream = response.getOutputStream();
						ServletContext context = getServletConfig().getServletContext();
						String mimetype = context.getMimeType(filepath);
						if (mimetype == null) 
						{
							mimetype = "application/octet-stream";
						}
						response.setContentType(mimetype);
						response.setContentLength((int) file.length());
						String fileName = (new File(filepath)).getName();
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
					catch ( Exception e) 
					{
						e.printStackTrace();
					}
				}
				 else{
					 	
				    String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
					String filepath1=getServletContext().getRealPath("")+ File.separator +"Left Employee Report.pdf";
					ReportDAOPDF.Left_Employee_Report(fromDate,toDate,type,Branch,Desig,LeftRepType,filepath1,imagepath);
					
				 try
					{
					
						final int BUFSIZE = 4096;
						File file = new File(filepath1);
						int length = 0;
						ServletOutputStream outStream = response.getOutputStream();
						ServletContext context = getServletConfig().getServletContext();
						String mimetype = context.getMimeType(filepath1);
						if (mimetype == null) 
						{
							mimetype = "application/octet-stream";
						}
						response.setContentType(mimetype);
						response.setContentLength((int) file.length());
						String fileName = (new File(filepath1)).getName();
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
					catch ( Exception e) 
					{
						e.printStackTrace();
					}
				}
					
					
					
				}
				}
			else if (action.equalsIgnoreCase("LoanDeductionReport")) 
			{
				String date = request.getParameter("date");
				date="01-"+date;
				String code = request.getParameter("trncd");
				String ptcode=request.getParameter("pttrncd")==""?"0":request.getParameter("pttrncd");
				String table = request.getParameter("tname");
				String fname = request.getParameter("fname");
				System.out.println("table = "+table);
				System.out.println("trncd = "+code);
				System.out.println("pttrncd = "+ptcode);
				String filePath = getServletContext().getRealPath("")+ File.separator +fname+"_"+request.getParameter("date")+".pdf";
				String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
				
				
				      UtilityDAO.LoanDeductionReport(date, filePath,imagepath,code,table,ptcode);
				
				try
				{
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
				catch ( Exception e) 
				{
					e.printStackTrace();
				}
			} 	
			else if(action.equalsIgnoreCase("leaveEncashtentetive")){
				System.out.println("i am in servlet");
			}
	else if(action.equalsIgnoreCase("codeWiseSalaryReort")){
				
				String frmDate =request.getParameter("frmdate");
				String toDate =request.getParameter("todate");
				String Trncd =request.getParameter("trncd");
				int trncode = Integer.parseInt(Trncd);
				String uid = session.getAttribute("EMPNO").toString();
				System.out.println("uid:  "+uid);
				String TrntoDate = "";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			    SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");
			 
			    /*try {
			    	TrntoDate = output.format(sdf.parse(frmDate));
			    	System.out.println("TrntoDate: "+TrntoDate);
			    	
				} catch (java.text.ParseException e1) {
					e1.printStackTrace();
				}*/

				final int BUFSIZE = 4096;
				YearlyPDFReport yearly = new YearlyPDFReport();
				String date = request.getParameter("date");
				date="01-"+date;
				
				
				String imgpath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
				String filePath = getServletContext().getRealPath("")+ File.separator + "Code Wise Salary Report.pdf";
				try {
					
					yearly.codeWiseSalaryReort(frmDate, toDate,trncode, imgpath, filePath);
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
				}
				
			}
		
	else if(action.equalsIgnoreCase("NextIncrementReport")){
		System.out.println("i am in NextIncrementReport");
		
		String type=request.getParameter("LeftRep");
		String Desig=request.getParameter("dewise");
		String Branch=request.getParameter("Branch");
		String Reptype=request.getParameter("Reptype");
		
		
		System.out.println("val..."+Desig);
		String imagepath =getServletContext().getRealPath("/images/plogo.png");
		try
		{
			System.out.println("hello ak");
				String filePath1=getServletContext().getRealPath("")+ File.separator + "Next Increment Report.xls";
				//Method Call for Pdf Report.....
				ExcelDAO.NextIncrementReport(type,Desig,Branch,Reptype, filePath1,imagepath);
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
				
			}catch ( Exception e) 	{	e.printStackTrace();	}
		}
		
	/*	
	else if(action.equalsIgnoreCase("attendanceList"))
	{
		
		System.out.println(" G attendanceList");
				String date	=	request.getParameter("date_download");
				String type	=	request.getParameter("type");
				//String TRNCD=	request.getParameter("dedtype");
				String Emptype="0";
				String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
				
				String filepath=getServletContext().getRealPath("")+ File.separator + type+"_Emplist_"+date+".xls";
				
				System.out.println(" G attendanceList  : "+date);
				
					try
					{
			 UploadAttendanceDAO ud = new UploadAttendanceDAO();  
			int stat = ud.chkAttendDate(date);
			System.out.println("CHECK stat : "+stat);
			 
			 //if(stat==1)
			if(stat==0)
			 {
						//ExcelDAO.getAttendanceAllEMPLIST(TRNCD,type,date,filepath);
				
				ExcelDAO.getAttendanceAllEMPLIST(date,imagepath, filepath,type,Emptype);
				 //ExcelDAO.getAttendanceAllEMPLIST(type,date,filepath);
						final int BUFSIZE = 4096;
						File file = new File(filepath);
						int length = 0;
						ServletOutputStream outStream = response.getOutputStream();
						ServletContext context = getServletConfig().getServletContext();
						String mimetype = context.getMimeType(filepath);
						if (mimetype == null) 
						{
							mimetype = "application/octet-stream";
						}
						response.setContentType(mimetype);
						response.setContentLength((int) file.length());
						String fileName = (new File(filepath)).getName();
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
			 }else {
				// response.sendRedirect("UploadAttendance.jsp?stat="+stat);
				 response.sendRedirect("UploadAttendance.jsp");
			 }
					}
					catch ( Exception e) 
					{
						e.printStackTrace();
					}
	}*/
		
	else if (action.equalsIgnoreCase("attendanceList")) 
	{


		System.out.println("in new pay reg serv");
		String date = request.getParameter("date");
		String type = request.getParameter("typ");
		String format=request.getParameter("frmt");
		String Reportformat=request.getParameter("reptype");
		String Emptype = request.getParameter("emptype");
		String BranchCode = request.getParameter("branch");
		
		System.out.println("EMPLOYEE TYPE CHECK :"+Emptype);
		
		/*System.out.println("tt "+type);*/
		String filename="";
		
		if(format.equalsIgnoreCase("txt")){
			filename="NewPayReg.txt";
		}			
		
		else{
			filename=format.equalsIgnoreCase("xls")?"NewPayReg.xls":"NewPayReg.pdf";
		}
		String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
		String filePath = getServletContext().getRealPath("")+ File.separator + ""+filename;
	
			File file = new File(filePath);
			
			if (file.exists())
			{
				file.delete();
			}
			try
			{
				Document doc  = new Document();
				PdfWriter.getInstance(doc, new FileOutputStream(filePath));
				
			}catch ( Exception e) 
			{
				e.printStackTrace();
			}
		
		if(format.equalsIgnoreCase("xls"))
		{
		//ExcelDAO.newpayreg11(date,imagepath, filePath,type);
		ExcelDAO.getAttendanceAllEMPLIST(date,imagepath, filePath,type,Emptype);
		}
		else if(format.equalsIgnoreCase("txt"))
		{
			//UtilityDAO.payreg(date,"0", filePath);
			UtilityDAO.payregWithBranchTotal(date,"0", filePath);
				
		}
		
		else{
			//UtilityDAO.newpayregwithNewFormat(date,imagepath, filePath,type,Reportformat);
			UtilityDAO.newpayregwithNewFormat(date,imagepath, filePath,type,Reportformat,Emptype, BranchCode);
		}
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.write("<iframe scrolling=\"auto\" src=\""+filename+"\" height=\"450px\" width=\"100%\"></iframe>");
	
	}
	
		
	}

	
	
	
}