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

import payroll.DAO.SequenceFileDAO;

/**
 * Servlet implementation class SequenceFileServlet
 */
@WebServlet("/SequenceFileServlet")
 public class SequenceFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SequenceFileServlet() {
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
		
		
		if(action.equalsIgnoreCase("seqfile"))
		{
			
		String[] ac=request.getParameter("ac").split(":");
		String trncd=ac[0].trim();
		System.out.println("action --:  "+ac[0]);
		String subsys="";
		String date=request.getParameter("date")==null?" ":request.getParameter("date");
		String branch=request.getParameter("branch")==null?" ":request.getParameter("branch");
		String filename=subsys+".SEQ";
		String filePath = "";
		SequenceFileDAO SF=new SequenceFileDAO();
		boolean flag=false;
		
		String filetype=request.getParameter("ac");
		System.out.println("filetype --:  "+filetype);
		if(filetype.equalsIgnoreCase("BRDR"))
		{
			subsys="BRDR";
			filePath=getServletContext().getRealPath("")+ File.separator +subsys+".SEQ";
			flag= SF.generateBRDR("999",filePath,date,branch);
		}
		
		else if(filetype.equalsIgnoreCase("HOCR")){
			subsys="HOCR";
			filePath=getServletContext().getRealPath("")+ File.separator +subsys+".SEQ";
			flag= SF.generateHOCR("999",filePath,date,branch);
		}
		
		else if(filetype.equalsIgnoreCase("PLDR")){
			subsys="PLDR";
			filePath=getServletContext().getRealPath("")+ File.separator +subsys+".SEQ";
			flag= SF.generatePLDR("999",filePath,date,branch);
		}
		
		else if(filetype.equalsIgnoreCase("SALNET"))
		{
			System.out.println("in SALNET");
			System.out.println("BRANCH :"+branch);
			subsys="SALNET";
			//filePath=getServletContext().getRealPath("")+ File.separator +subsys+".SEQ";
			filePath=getServletContext().getRealPath("")+ File.separator +subsys+".TXT";
			flag= SF.generateSALNET("999",filePath,date,branch);
		}
		//
		else if(filetype.equalsIgnoreCase("BONUS"))
		{
			subsys="BONUS";
			filePath=getServletContext().getRealPath("")+ File.separator +subsys+".SEQ";
			flag= SF.generateBONUSSEQ("135",filePath,date,branch);
		}
		// for encahment by akshay 
		else if(filetype.equalsIgnoreCase("BRDRE"))
		{
			subsys="BRDR";
			filePath=getServletContext().getRealPath("")+ File.separator +subsys+"Encash.SEQ";
			flag= SF.generateBRDR("145",filePath,date,branch);
		}
		
		else if(filetype.equalsIgnoreCase("HOCRE")){
			subsys="HOCR";
			filePath=getServletContext().getRealPath("")+ File.separator +subsys+"Encash.SEQ";
			flag= SF.generateHOCR("145",filePath,date,branch);
		}
		
		else if(filetype.equalsIgnoreCase("PLDRE")){
			subsys="PLDR";
			filePath=getServletContext().getRealPath("")+ File.separator +subsys+"Encash.SEQ";
			flag= SF.generatePLDR("145",filePath,date,branch);
		}
		
		else if(filetype.equalsIgnoreCase("SALNETE"))
		{System.out.println("in SALNETE");
			subsys="SALNET";
			filePath=getServletContext().getRealPath("")+ File.separator +subsys+"Encash.SEQ";
			flag= SF.generateSALNET("145",filePath,date,branch);
		}
		// For LeaveEncashment
				else if(filetype.equalsIgnoreCase("LEAVEENCASH")){
				subsys="LEAVE-ENCASH";
				filePath=getServletContext().getRealPath("")+ File.separator +subsys+".SEQ";
				flag= SF.generateLEAVEencash(filePath,date,branch,subsys);
				}
				else if(filetype.equalsIgnoreCase("PMJJBY_PMSBY")){
					subsys="PMJJ12_330YER";
					filePath=getServletContext().getRealPath("")+ File.separator +subsys+".SEQ";
					flag= SF.generatePMJJBYPMSBY(filePath,date,branch,subsys);
					}
		
				else if(filetype.equalsIgnoreCase("LOANALL"))
				{
					String date1= date.substring(3, 6);
					String date2= date.substring(7, 11);
				
					subsys="LOANALL_"+date1+"_"+date2;
					filePath=getServletContext().getRealPath("")+ File.separator +subsys+".TXT";
					flag= SF.generateLOANALLINST(trncd,filePath,date,branch,subsys);
				}

		// For ADVMED-S
		else	if(ac[1].equalsIgnoreCase("ADVMED-S"))
		{
			subsys="ADVMED-S";
			filePath=getServletContext().getRealPath("")+ File.separator +subsys+".SEQ";
			flag= SF.generateADVMED(filePath,date,branch,subsys);
		}
		// For ADVGS-S
		else if(ac[1].equalsIgnoreCase("ADVGS-S"))
		{
		subsys="ADVGS-S";
		filePath=getServletContext().getRealPath("")+ File.separator +subsys+".SEQ";
		flag= SF.generateADVGS(filePath,date,branch,subsys);
		}
		// For LNOTH-S
		else if(ac[1].equalsIgnoreCase("LNOTH-S"))
		{
		subsys="LNOTH-S";
		filePath=getServletContext().getRealPath("")+ File.separator +subsys+".SEQ";
		flag= SF.generateLNOTH(filePath,date,branch,subsys);
		}
		// For CODS
	
				else if(ac[1].equalsIgnoreCase("CODS"))
				{
				subsys="COD-S";
				filePath=getServletContext().getRealPath("")+ File.separator +subsys+".SEQ";
				flag= SF.generateCODS(filePath,date,branch,subsys);
				}
				else if(ac[1].equalsIgnoreCase("CCINST"))
				{
				subsys="CCINST-S";
				filePath=getServletContext().getRealPath("")+ File.separator +subsys+".SEQ";
				flag= SF.generateCCINST(trncd,filePath,date,branch,subsys);
				}
				else if(ac[1].equalsIgnoreCase("LNHYP"))
				{
				subsys="LNHYP-S";
				filePath=getServletContext().getRealPath("")+ File.separator +subsys+".SEQ";
				flag= SF.generateLNHYP(trncd,filePath,date,branch,subsys);
				}
		
		
				else if(ac[1].equalsIgnoreCase("HSGLINST") || ac[1].equalsIgnoreCase("VCLLINST") || ac[1].equalsIgnoreCase("WVCLLINST") || ac[1].equalsIgnoreCase("PSNLINST"))
				{
					String date1= date.substring(3, 6);
					String date2= date.substring(7, 11);
				
					if(ac[0].equalsIgnoreCase("244")){
							subsys="HSGLINST_"+date1+"_"+date2;
						}
					else if(ac[0].equalsIgnoreCase("245")){
						subsys="VCLLINST_"+date1+"_"+date2;
					}
					else if(ac[0].equalsIgnoreCase("246")){
						subsys="WVCLLINST_"+date1+"_"+date2;
					}
					else if(ac[0].equalsIgnoreCase("247")){
						subsys="PSNLINST_"+date1+"_"+date2;
					}
					else{
					subsys="LOANALL_"+date1+"_"+date2;
					}
					
					filePath=getServletContext().getRealPath("")+ File.separator +subsys+".TXT";
					flag= SF.generateLOANINST(trncd,filePath,date,branch,subsys);
				}
		
		
					
		
		
				else if(ac[1].equalsIgnoreCase("ADVFST"))
				{
				subsys="ADVFST";
				filePath=getServletContext().getRealPath("")+ File.separator +subsys+".SEQ";
				flag= SF.generateADVFST(trncd,filePath,date,branch,subsys);
				}
		///
		else{System.out.println("i am else");
			subsys=(ac[1].trim()).toUpperCase();
			filePath=getServletContext().getRealPath("")+ File.separator +subsys+".SEQ";
		flag= SF.generateSEQ(trncd,filePath,date,branch,subsys);
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
			//response.sendRedirect("Seq_File.jsp?flag=1");
		
		}
		
		
		
		
		
	}

}
