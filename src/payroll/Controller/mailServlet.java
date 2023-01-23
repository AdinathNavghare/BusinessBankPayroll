package payroll.Controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import payroll.Core.ReportDAO;
import payroll.Core.UtilityDAO;
import payroll.DAO.EmpAddrHandler;
import payroll.DAO.EmpOffHandler;
import payroll.Model.EmpAddressBean;
import payroll.Model.TranBean;


/**
 * Servlet implementation class mailServlet
 */
@WebServlet("/mailServlet")
public class mailServlet extends HttpServlet {

	
	public static String Status="";
	public static String host = "smtp.gmail.com";
	public static String port = "587";
	public static final String mailFrom = "testharsh0@gmail.com";
	public static final String password = "testharsh";

    // message info
	public static String mailTo = "";
	public static  String subject = "Pay Slip For Month ";
	public static String message = "This is System generated Email,payslip . ";
	public static String date = "";
	public static  int size=0;
	public static String filePath[]=new String[10];
	static ServletContext context = null;
    public static String empno="";
    public static String todate="";
    public static int k=0;
    SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException 
	{
		
		context = getServletConfig().getServletContext();
		date =request.getParameter("date");
		date="01-"+date;
		todate=request.getParameter("todate");
		todate="01-"+todate;
		
		
		Date d1 = new Date(date); 
		Date d2 = new Date(todate);
		size=0;
		while(d1.before(d2)|| d1.equals(d2))
		{
			Calendar cal = Calendar.getInstance();
		    cal.setTime(d1);
		    cal.add(Calendar.MONTH, 1);
		    d1=cal.getTime();
		    size++;
		}
		 d1 = new Date(date); 
		 d2 = new Date(todate);
		
		String who = request.getParameter("all");
		if(who.equalsIgnoreCase("one"))
		{
			k=0;
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			String [] emp=request.getParameter("EMPNO").split(":");
			String empno= emp[2];
			
			//String emailTo = request.getParameter("toemailid");
			while(d1.before(d2)|| d1.equals(d2))
			{
				
				if(k<size)
				{
			
			filePath[k] =""+getServletContext().getRealPath("")+ File.separator + empno+"-"+sdf.format(d1)+"-PaySlip.pdf";
			
			mailTo = request.getParameter("toemailid");
			UtilityDAO.PaySlip(sdf.format(d1), empno, filePath[k], imagepath);
			 Calendar cal = Calendar.getInstance();
			    cal.setTime(d1);
			    cal.add(Calendar.MONTH, 1);
			    d1=cal.getTime();
			   
			    k++;
				}else
				{
					break;
				}
			}
			sendEmail(request, response);
			
		}
		else if(who.equalsIgnoreCase("all"))
		{
			d1 = new Date(date); 
			 d2 = new Date(todate);
			String []emailid = new EmpAddrHandler().getEmailId();
			
			for(int i=1;i<=emailid.length;i++)
			{
				String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
				
				
				if(!emailid[i].equalsIgnoreCase(""))
				{
					empno = ""+i;
					k=0;
					while(d1.before(d2)|| d1.equals(d2))
					{
					filePath[k] = getServletContext().getRealPath("")+ File.separator + i+"-"+sdf.format(d1)+"-PaySlip.pdf";
					mailTo = emailid[i];
				UtilityDAO.PaySlip(sdf.format(d1), ""+i, filePath[k], imagepath);
				Calendar cal = Calendar.getInstance();
			    cal.setTime(d1);
			    cal.add(Calendar.MONTH, 1);
			    d1=cal.getTime();
			    k++;
					}
				sendEmail(request, response);
				}
			}
			 
				 
		}
		else if(who.equalsIgnoreCase("prjmail"))
		{
			d1 = new Date(date); 
			 d2 = new Date(todate);
			//String [] emp = request.getParameter("EMPNO").split(":");
			//String empno1 = emp[1];
			String prjCode = request.getParameter("prjemp");
			
			ArrayList<TranBean> tbean = new ArrayList<TranBean>();
			
			EmpOffHandler empOff = new EmpOffHandler();
			
			tbean = empOff.getEmpList(prjCode);
			String empNos = "0";
			mailTo = request.getParameter("toemailid");
			for( TranBean tb : tbean) {
				empNos = empNos+","+ Integer.toString(tb.getEMPNO());
				
			}
			System.out.println("EMPLIST="+empNos);
			String imagepath =getServletContext().getRealPath("/images/BusinessBankLogo.png");
			k=0;
			while(d1.before(d2)|| d1.equals(d2))
			{
			filePath[k] = getServletContext().getRealPath("")+ File.separator + prjCode+"-"+sdf.format(d1)+"-PaySlip.pdf";
			
			File file = new File(filePath[k]);
			if (file.exists()) 
			{
				file.delete();
			}
			UtilityDAO.PaySlip(sdf.format(d1), empNos, filePath[k],imagepath);
			Calendar cal = Calendar.getInstance();
		    cal.setTime(d1);
		    cal.add(Calendar.MONTH, 1);
		    d1=cal.getTime();
		    k++;
			}
			sendEmail(request, response);
		}
		/**/
		System.out.println("out   "+(new Date().getTime()-d1.getTime())/1000);	
		response.sendRedirect("mail.jsp?Status="+Status);
	  
    }

    private class SMTPAuthenticator extends Authenticator {

        private PasswordAuthentication authentication;

        public SMTPAuthenticator(String login, String password) {
            authentication = new PasswordAuthentication(login, password);
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return authentication;
        }
    }

    protected void doGet(HttpServletRequest request, 
                         HttpServletResponse response)
                   throws ServletException, IOException {
	    processRequest(request, response);
      
    }

    protected void doPost(HttpServletRequest request, 
                          HttpServletResponse response)
                   throws ServletException, IOException {
    	
    	PrintWriter out = response.getWriter();
    	response.setContentType("text/html");
    	//String empno = request.getParameter("empno");
    	String []empno = request.getParameter("empno").split(":");
    	String empno1 = empno[2];
    	EmpAddrHandler empAdd = new EmpAddrHandler();
    	ArrayList<EmpAddressBean> list = new ArrayList<EmpAddressBean>();
    	list = empAdd.getEmpAddress(empno1);
    	for(EmpAddressBean bean : list){
    		out.write(bean.getADDR3());
    		break;
    	}
    }
    
    

    public static void sendEmail(HttpServletRequest request,HttpServletResponse response) throws IOException
    {
    	
    	try
    	{
    	File file = new File(filePath[0]);
		 String mimetype = context.getMimeType(filePath[0]);
		 if (mimetype == null) 
		 {
			 mimetype = "application/octet-stream";
		 }
		 response.setContentType(mimetype);
		 response.setContentLength((int) file.length());
		 
		 //System.out.println(filePath);
		 // attachments
		 
		 // sets SMTP server properties
		 Properties properties = new Properties();
		 properties.put("mail.smtp.host", host);
		 properties.put("mail.smtp.port", port);
		 properties.put("mail.smtp.auth", "true");
		 properties.put("mail.smtp.starttls.enable", "true");
		 properties.put("mail.user", mailFrom);
		 properties.put("mail.password", password);
		 	
		        // creates a new session with an authenticator
		 Authenticator auth = new Authenticator() 
		 {
			 public PasswordAuthentication getPasswordAuthentication() 
			 {
				 return new PasswordAuthentication(mailFrom, password);
			 }
		 };
		 Session session = Session.getInstance(properties, auth);
		 
		 // creates a new e-mail message
		 Message msg = new MimeMessage(session);
		 try
		 {
			 msg.setFrom(new InternetAddress(password));
			 InternetAddress[] toAddresses = { new InternetAddress(mailTo) };
			 msg.setRecipients(Message.RecipientType.TO, toAddresses);
			 msg.setSubject(subject+date.substring(3));
			 msg.setSentDate(new Date());
			 
			 // creates message part
			 MimeBodyPart messageBodyPart = new MimeBodyPart();
			 messageBodyPart.setContent(message+empno, "text/html");
			 	
			 // creates multi-part
			 Multipart multipart = new MimeMultipart();
			 multipart.addBodyPart(messageBodyPart);
			 
			 // adds attachments
			 for(int l=0;l<size;l++)
			 {
				
				 if(!filePath[l].equals("")||filePath[l]!=null||!filePath[l].isEmpty())
				 {
			
					 
			 String fileName = (new File(filePath[l])).getName();
			 MimeBodyPart attachPart = new MimeBodyPart();
			 DataSource source = new FileDataSource(filePath[l]);
			 attachPart.setDataHandler(new DataHandler(source));
			 attachPart.setFileName(fileName);  
			 multipart.addBodyPart(attachPart);
				 }else
				 {
					 break;
				 }
			 }
			 // sets the multi-part as e-mail's content
			 msg.setContent(multipart);
		        
			 // sends the e-mail
			//System.out.println(" Transport.send(msg) is Commented.....");
			Transport.send(msg);
			 if(file.exists())
			 {
				 file.delete();
			 }
		       
			
			 Status = "Pay-Slip  Sending Successfully ";
			 
			 k=0;
			 size=0;
			date="";todate="";
		 }
		 catch (Exception ex) 
		 {
		     Status = "Error in Pay-Slip  Sending  ";
			 ex.printStackTrace();
		 }
    	}
    	catch(Exception c)
    	{
    		Status = "Error in Pay-Slip  Sending  ";
			 c.printStackTrace();
    	}
		 System.gc();
    }

}
