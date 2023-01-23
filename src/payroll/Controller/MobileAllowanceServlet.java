package payroll.Controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import payroll.Core.UtilityDAO;
import payroll.DAO.MobileAllowanceHandler;
import payroll.DAO.TranHandler;
import payroll.Model.MobileBean;

/**
 * Servlet implementation class MobileAllowanceServlet
 */
@WebServlet("/MobileAllowanceServlet")
public class MobileAllowanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MobileAllowanceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		String action=request.getParameter("action");
		HttpSession session=request.getSession();
		MobileAllowanceHandler mab=new MobileAllowanceHandler();
		TranHandler thr=new TranHandler();
		System.out.println("---------------------"+action);
		if(action.equalsIgnoreCase("edit"))
		{
			MobileBean mb=new MobileBean();
		int emp_no=Integer.parseInt( request.getParameter("empno"));
		 int prj_no=Integer.parseInt(request.getParameter("prj"));
		 String eff_date=request.getParameter("date");
		 String mob=request.getParameter("mob");
		 
		 mb.setEmp_no(emp_no);
		 mb.setEff_date(eff_date);
		 mb.setPrj_srno(prj_no);
		 mb.setMobile_no(mob);
		 
		 
		 mb=mab.getEmpMobileAllow(mb);
		 
		 session.setAttribute("editmb",mb);
		 response.sendRedirect("empMobileAllowance.jsp?action=edit");
		 
		}
		else  if(action.equalsIgnoreCase("update"))
		{
		
			MobileBean editmb=new MobileBean();
			editmb=(MobileBean)session.getAttribute("editmb");
			int emp_no;
			 int prj_no;
			 String empname=request.getParameter("emp");
			 int connection_type=Integer.parseInt(request.getParameter("connection_type"));
			 String prjname=request.getParameter("prj_no");
			 int  service_provider=Integer.parseInt(request.getParameter("service_provider"));
			 String mobile_no=request.getParameter("mobile_no");
			 String eff_date=request.getParameter("eff_date");
			 float charges=Float.parseFloat(request.getParameter("charges"));
			 String status=request.getParameter("status");
			 String EMPNO[]=empname.split(":");
			 emp_no=Integer.parseInt(EMPNO[2].trim());
			 String PRJNO[]=prjname.split(":");
			 prj_no=Integer.parseInt(PRJNO[2].trim());
			
				
				MobileBean mb=new MobileBean();
				mb.setEmp_no(emp_no);
				mb.setEff_date(eff_date);
				mb.setPrj_srno(prj_no);
				mb.setMobile_no(mobile_no);
				mb.setService_provider(service_provider);
				mb.setConnection_type(connection_type);
				mb.setCharges(charges);
				mb.setStatus(status);
				
		boolean flag=mab.updateEmpMobileAllow(editmb, mb);
		
		if(flag)
			{
			response.sendRedirect("empMobileAllowance.jsp?flag=3");
			}
			else
			{
				response.sendRedirect("empMobileAllowance.jsp?action=2");
			}
			
			
			
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		
		
	String action=request.getParameter("action");
	HttpSession session=request.getSession();
	MobileAllowanceHandler mab=new MobileAllowanceHandler();
	TranHandler thr=new TranHandler();
	if(action.equalsIgnoreCase("insert"))
	{
	
	int emp_no;
	 int prj_no;
	 String empname=request.getParameter("emp");
	 int connection_type=Integer.parseInt(request.getParameter("connection_type"));
	 String prjname=request.getParameter("prj_no");
	 int  service_provider=Integer.parseInt(request.getParameter("service_provider"));
	 String mobile_no=request.getParameter("mobile_no");
	 String eff_date=request.getParameter("eff_date");
	 float charges=Float.parseFloat(request.getParameter("charges"));
	 String status=request.getParameter("status");
	 String EMPNO[]=empname.split(":");
	 emp_no=Integer.parseInt(EMPNO[2].trim());
	 String PRJNO[]=prjname.split(":");
	 prj_no=Integer.parseInt(PRJNO[2].trim());
	
		
		MobileBean mb=new MobileBean();
		mb.setEmp_no(emp_no);
		mb.setEff_date(eff_date);
		mb.setPrj_srno(prj_no);
		mb.setMobile_no(mobile_no);
		mb.setService_provider(service_provider);
		mb.setConnection_type(connection_type);
		mb.setCharges(charges);
		mb.setStatus(status);
		
		MobileBean new_mb=mab.setEmpMobAllowance(mb);
		session.setAttribute("mob_bean", new_mb);
		if(new_mb.getEff_date()==""||new_mb.getEff_date().equals(null))
		{
			
			response.sendRedirect("empMobileAllowance.jsp?action=2");
		}
		else
		{
			
			response.sendRedirect("empMobileAllowance.jsp?flag=1");
		}
		
		
	}
	else if(action.equalsIgnoreCase("insert_Mdeduction"))
	{
		
		String[] ded_values = request.getParameterValues("tranValue");
		String[] allow_values = request.getParameterValues("allow");
		String[] bill_values = request.getParameterValues("bill");
		String[] empno_values = request.getParameterValues("emnp");
		String[] mob_values = request.getParameterValues("mob");
		   
		   String empno[]= new  String[empno_values.length];
		   String mob[]= new  String[mob_values.length];
		   Float allow[] = new Float[allow_values.length];
		   Float bill[] = new Float[bill_values.length];
		   Float ded[] = new Float[ded_values.length];
		   Float ded_new[] = new Float[ded_values.length];
		   String empno_new[]= new  String[empno_values.length];
		   for(int i=0; i<empno_values.length; i++)
		   {
			  
		    allow[i] = Float.parseFloat(allow_values[i].trim());
		    bill[i] = Float.parseFloat(bill_values[i].trim());
		    ded[i] = Float.parseFloat(ded_values[i].trim());
		    empno[i]= empno_values[i].trim();
		    mob[i]= mob_values[i].trim();
		   }
		   
		   boolean flag1=mab.setMobileBillAmt(empno,bill,mob);
		   
		   
		   if(flag1)
		   {
		   int count=0;
			   for(int i=0; i<ded.length; i++)
			   {
				   
				   ded_new[i]= mab.getDeductAmt(empno[i]);  
				   empno_new[i]=empno[i];
				   
			   }
			  
			   
		   boolean flag =thr.updateEmpTran_mob(223, ded_new,empno_new);
			if(flag)
			{
				response.sendRedirect("MobNo11.jsp?action=afterEdit&flag=3");
			}
			else
			{
				response.sendRedirect("MobNo11.jsp?flag=4");
			}
		   }
		   else
		   {
			   response.sendRedirect("MobNo11.jsp?flag=4");
		   }
		   
	}
	else if(action.equalsIgnoreCase("deductionReport"))
	{
		
		MobileAllowanceHandler MAH=new MobileAllowanceHandler();
		String month=request.getParameter("month");
		String year=request.getParameter("year");
		final int BUFSIZE = 4096;
		String date="01-"+month+"-"+year;
		String filePath = getServletContext().getRealPath("")+ File.separator + "Mobile_Deduction_For_"+month+""+year+".pdf";
		String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.jpg");
		MAH.getMob_Ded_Reports(date,filePath, imagepath);
		
		String pdfname="Mobile_Deduction_For_"+month+""+year+".pdf";
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

}
