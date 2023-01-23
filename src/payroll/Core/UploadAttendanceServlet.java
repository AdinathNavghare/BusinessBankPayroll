package payroll.Core;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import payroll.DAO.AttendanceHandler;
import payroll.DAO.LMH;
import payroll.DAO.TranHandler;
import payroll.DAO.UploadAttendanceDAO;
import payroll.Model.UploadAttendanceBean;

 
@WebServlet("/UploadAttendanceServlet")
@MultipartConfig
public class UploadAttendanceServlet extends HttpServlet {
	
    
    public UploadAttendanceServlet() {
        super();
        
    }

	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*response.getWriter().append("Served at: ").append(request.getContextPath());*/
	}

	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 String action = request.getParameter("action");
	 
	 
	 HttpSession session= request.getSession();	
		if(action.equalsIgnoreCase("uploadAttendance"))
		{
			
			System.out.println("AM HERE IN UPLOAD Attendance");
			String Adate	=	request.getParameter("Adate");
			/*String sddate	=	request.getParameter("date");*/
			System.out.println("THIS IS Adate==>"+Adate );
			//String UID	=	session.getAttribute("UID").toString();
		
			InputStream inputStream = null; // input stream of the upload file
	         
	        // obtains the upload file part in this multipart request
	        Part filePart = request.getPart("file");
	        if (filePart != null) {
	            // prints out some information for debugging
	            System.out.println("FILENAME==>"+filePart.getName());
	            System.out.println("FILE SIZE==>"+filePart.getSize());
	            System.out.println("FILE CONTENTTYPE==>"+filePart.getContentType());
	             
	            // obtains input stream of the upload file
	            inputStream = filePart.getInputStream();
	            
	        }
	    	 
		  /*ArrayList<UploadTransBean> list = new ArrayList<UploadTransBean>();
		  UploadTransBean udb = null;
		  UploadAllTransDAO uddao  = new  UploadAllTransDAO();*/ 
	        
	        ArrayList<UploadAttendanceBean> list = new ArrayList<UploadAttendanceBean>();
	        UploadAttendanceDAO uddao  = new  UploadAttendanceDAO();
	        UploadAttendanceBean udb = null;
	        
	        
		  
		  
		  int cnt=1;
		  int deduction_colum;
		
		  try {
			  
			  if(inputStream!=null) {
				  System.out.println("THIS IS 4==>");
			  deduction_colum=19;
			  System.out.println("THIS IS THE COLUNM==>"+deduction_colum);
			  POIFSFileSystem fs = new POIFSFileSystem(inputStream);
			  	HSSFWorkbook workbook = new  HSSFWorkbook(fs);
			  	HSSFSheet sheet=workbook.getSheetAt(0);
				HSSFRow row;
				Adate = "1-"+Adate;
				
				String date1 = ReportDAO.EOM(Adate);
				int  dayss = Integer.parseInt(date1.substring(0,2)); 
				String dt="";
				
				HSSFCell EMP_CODE;
				
				
				HSSFCell DATE = null ;
				HSSFCell days1 = null;
				HSSFCell days2 = null;
				HSSFCell days3 = null;
				HSSFCell days4 = null;
				HSSFCell days5 = null;
				HSSFCell days6 = null;
				HSSFCell days7 = null;
				HSSFCell days8 = null;
				HSSFCell days9 = null;
				HSSFCell days10 = null;
				HSSFCell days11 = null;
				HSSFCell days12 = null;
				HSSFCell days13 = null;
				HSSFCell days14 = null;
				HSSFCell days15 = null;
				HSSFCell days16 = null;
				HSSFCell days17 = null;
				HSSFCell days18 = null;
				HSSFCell days19 = null;
				HSSFCell days20 = null;
				HSSFCell days21 = null;
				HSSFCell days22 = null;
				HSSFCell days23 = null;
				HSSFCell days24 = null;
				HSSFCell days25 = null;
				HSSFCell days26 = null;
				HSSFCell days27 = null;
				HSSFCell days28 = null;
				HSSFCell days29 = null;
				HSSFCell days30 = null;
				HSSFCell days31 = null;


					HSSFCell TOTAL = null;
				

				DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
				 
				Iterator rows = sheet.rowIterator();
				rows.next();
				
				
			/*	String str130="";
				String str129="";
				String str132="";
				String str133="";
				String str142="";
				String str210="";
				String str211="";
				String str213="";
				String str216="";
				String str264="";
				*/
				String sdays1 = "";
				String sdays2 = "";
				String sdays3 = "";
				String sdays4 = "";
				String sdays5 = "";
				String sdays6 = "";
				String sdays7 = "";
				String sdays8 = "";
				String sdays9 = "";
				String sdays10 = "";
				String sdays11 = "";
				String sdays12 = "";
				String sdays13 = "";
				String sdays14 = "";
				String sdays15 = "";
				String sdays16 = "";
				String sdays17 = "";
				String sdays18 = "";
				String sdays19 = "";
				String sdays20 = "";
				String sdays21 = "";
				String sdays22 = "";
				String sdays23 = "";
				String sdays24 = "";
				String sdays25 = "";
				String sdays26 = "";
				String sdays27 = "";
				String sdays28 = "";
				String sdays29 = "";
				String sdays30 = "";
				String sdays31 = "";


				
				
				while (rows.hasNext())
				{
					

					
					 udb = new UploadAttendanceBean();
					row=(HSSFRow) rows.next();
						
						/*processing_date	=	row.getCell((short) 1);*/
						/*empno = row.getCell((short) (3));
						mob_ded = row.getCell((short) deduction_colum);
						
						INCENTIVES	=	row.getCell((short) 8);
						OTHALLW	=	row.getCell((short) 9);
						ANYOTHADD1	=	row.getCell((short) 10);
						ANYOTHADD2 =	row.getCell((short) 11);
						ARREARS	=	row.getCell((short) 12);
						ACCOMODATION	=	row.getCell((short) 13);
						FOOD	=	row.getCell((short) 14);
						SOFTFURN	=	row.getCell((short) 15);
						ANYOTHDED	=	row.getCell((short) 16);
						WO_MOBDED	=	row.getCell((short) 17);*/
					
					EMP_CODE = row.getCell((short) (1));
					DATE = row.getCell((short) (3));
					
					
					

					 
					days1 =row.getCell((short) 4);
					days2 =row.getCell((short) 5);
					days3 =row.getCell((short) 6);
					days4 =row.getCell((short) 7);
					days5 =row.getCell((short) 8);
					days6 =row.getCell((short) 9);
					days7 =row.getCell((short) 10);
					days8 =row.getCell((short) 11);
					days9 =row.getCell((short) 12);
					days10 =row.getCell((short) 13);
					days11 =row.getCell((short) 14);
					days12 =row.getCell((short) 15);
					days13 =row.getCell((short) 16);
					days14 =row.getCell((short) 17);
					days15 =row.getCell((short) 18);
					days16 =row.getCell((short) 19);
					days17 =row.getCell((short) 20);
					days18 =row.getCell((short) 21);
					days19 =row.getCell((short) 22);
					days20 =row.getCell((short) 23);
					days21 =row.getCell((short) 24);
					days22 =row.getCell((short) 25);
					days23 =row.getCell((short) 26);
					days24 =row.getCell((short) 27);
					days25 =row.getCell((short) 28);
					days26 =row.getCell((short) 29);
					days27 =row.getCell((short) 30);
					
					if(dayss==28)
					{
						days28 =row.getCell((short) 31);
						TOTAL =row.getCell((short) 32);
					}
					else
					if(dayss==29)
					{
						days28 =row.getCell((short) 31);
						days29 =row.getCell((short) 32);
						TOTAL =row.getCell((short) 33);
					}
					else if(dayss==30)
					{
						days28 =row.getCell((short) 31);
						days29 =row.getCell((short) 32);
						days30 =row.getCell((short) 33);
						TOTAL =row.getCell((short) 34);
					}
					else
					{
						days28 =row.getCell((short) 31);
						days29 =row.getCell((short) 32);
						days30 =row.getCell((short) 33);
						days31 =row.getCell((short) 34);
						TOTAL =row.getCell((short) 35);
					}
					
/*					str264=String.valueOf(String.valueOf(WO_MOBDED.getCellType())==null?0:WO_MOBDED.getCellType()==1?(WO_MOBDED.getStringCellValue()==null?"0":WO_MOBDED.getStringCellValue()):(String.valueOf(WO_MOBDED.getNumericCellValue())==null?0:String.valueOf(WO_MOBDED.getNumericCellValue())==null?"0":WO_MOBDED.getNumericCellValue()));*/
					
					sdays1	=String.valueOf(String.valueOf(	days1.getCellType())==null?0:days1.getCellType()==1?(	days1.getStringCellValue()==null?"0":days1.getStringCellValue()):(String.valueOf(	days1.getNumericCellValue())==null?0:String.valueOf(	days1.getNumericCellValue())==null?"0":days1.getNumericCellValue()));
					sdays2	=String.valueOf(String.valueOf(	days2.getCellType())==null?0:days2.getCellType()==1?(	days2.getStringCellValue()==null?"0":days2.getStringCellValue()):(String.valueOf(	days2.getNumericCellValue())==null?0:String.valueOf(	days2.getNumericCellValue())==null?"0":days2.getNumericCellValue()));
					sdays3	=String.valueOf(String.valueOf(	days3.getCellType())==null?0:days3.getCellType()==1?(	days3.getStringCellValue()==null?"0":days3.getStringCellValue()):(String.valueOf(	days3.getNumericCellValue())==null?0:String.valueOf(	days3.getNumericCellValue())==null?"0":days3.getNumericCellValue()));
					sdays4	=String.valueOf(String.valueOf(	days4.getCellType())==null?0:days4.getCellType()==1?(	days4.getStringCellValue()==null?"0":days4.getStringCellValue()):(String.valueOf(	days4.getNumericCellValue())==null?0:String.valueOf(	days4.getNumericCellValue())==null?"0":days4.getNumericCellValue()));
					sdays5	=String.valueOf(String.valueOf(	days5.getCellType())==null?0:days5.getCellType()==1?(	days5.getStringCellValue()==null?"0":days5.getStringCellValue()):(String.valueOf(	days5.getNumericCellValue())==null?0:String.valueOf(	days5.getNumericCellValue())==null?"0":days5.getNumericCellValue()));
					sdays6	=String.valueOf(String.valueOf(	days6.getCellType())==null?0:days6.getCellType()==1?(	days6.getStringCellValue()==null?"0":days6.getStringCellValue()):(String.valueOf(	days6.getNumericCellValue())==null?0:String.valueOf(	days6.getNumericCellValue())==null?"0":days6.getNumericCellValue()));
					sdays7	=String.valueOf(String.valueOf(	days7.getCellType())==null?0:days7.getCellType()==1?(	days7.getStringCellValue()==null?"0":days7.getStringCellValue()):(String.valueOf(	days7.getNumericCellValue())==null?0:String.valueOf(	days7.getNumericCellValue())==null?"0":days7.getNumericCellValue()));
					sdays8	=String.valueOf(String.valueOf(	days8.getCellType())==null?0:days8.getCellType()==1?(	days8.getStringCellValue()==null?"0":days8.getStringCellValue()):(String.valueOf(	days8.getNumericCellValue())==null?0:String.valueOf(	days8.getNumericCellValue())==null?"0":days8.getNumericCellValue()));
					sdays9	=String.valueOf(String.valueOf(	days9.getCellType())==null?0:days9.getCellType()==1?(	days9.getStringCellValue()==null?"0":days9.getStringCellValue()):(String.valueOf(	days9.getNumericCellValue())==null?0:String.valueOf(	days9.getNumericCellValue())==null?"0":days9.getNumericCellValue()));
					sdays10	=String.valueOf(String.valueOf(	days10.getCellType())==null?0:days10.getCellType()==1?(	days10.getStringCellValue()==null?"0":days10.getStringCellValue()):(String.valueOf(	days10.getNumericCellValue())==null?0:String.valueOf(	days10.getNumericCellValue())==null?"0":days10.getNumericCellValue()));
					sdays11	=String.valueOf(String.valueOf(	days11.getCellType())==null?0:days11.getCellType()==1?(	days11.getStringCellValue()==null?"0":days11.getStringCellValue()):(String.valueOf(	days11.getNumericCellValue())==null?0:String.valueOf(	days11.getNumericCellValue())==null?"0":days11.getNumericCellValue()));
					sdays12	=String.valueOf(String.valueOf(	days12.getCellType())==null?0:days12.getCellType()==1?(	days12.getStringCellValue()==null?"0":days12.getStringCellValue()):(String.valueOf(	days12.getNumericCellValue())==null?0:String.valueOf(	days12.getNumericCellValue())==null?"0":days12.getNumericCellValue()));
					sdays13	=String.valueOf(String.valueOf(	days13.getCellType())==null?0:days13.getCellType()==1?(	days13.getStringCellValue()==null?"0":days13.getStringCellValue()):(String.valueOf(	days13.getNumericCellValue())==null?0:String.valueOf(	days13.getNumericCellValue())==null?"0":days13.getNumericCellValue()));
					sdays14	=String.valueOf(String.valueOf(	days14.getCellType())==null?0:days14.getCellType()==1?(	days14.getStringCellValue()==null?"0":days14.getStringCellValue()):(String.valueOf(	days14.getNumericCellValue())==null?0:String.valueOf(	days14.getNumericCellValue())==null?"0":days14.getNumericCellValue()));
					sdays15	=String.valueOf(String.valueOf(	days15.getCellType())==null?0:days15.getCellType()==1?(	days15.getStringCellValue()==null?"0":days15.getStringCellValue()):(String.valueOf(	days15.getNumericCellValue())==null?0:String.valueOf(	days15.getNumericCellValue())==null?"0":days15.getNumericCellValue()));
					sdays16	=String.valueOf(String.valueOf(	days16.getCellType())==null?0:days16.getCellType()==1?(	days16.getStringCellValue()==null?"0":days16.getStringCellValue()):(String.valueOf(	days16.getNumericCellValue())==null?0:String.valueOf(	days16.getNumericCellValue())==null?"0":days16.getNumericCellValue()));
					sdays17	=String.valueOf(String.valueOf(	days17.getCellType())==null?0:days17.getCellType()==1?(	days17.getStringCellValue()==null?"0":days17.getStringCellValue()):(String.valueOf(	days17.getNumericCellValue())==null?0:String.valueOf(	days17.getNumericCellValue())==null?"0":days17.getNumericCellValue()));
					sdays18	=String.valueOf(String.valueOf(	days18.getCellType())==null?0:days18.getCellType()==1?(	days18.getStringCellValue()==null?"0":days18.getStringCellValue()):(String.valueOf(	days18.getNumericCellValue())==null?0:String.valueOf(	days18.getNumericCellValue())==null?"0":days18.getNumericCellValue()));
					sdays19	=String.valueOf(String.valueOf(	days19.getCellType())==null?0:days19.getCellType()==1?(	days19.getStringCellValue()==null?"0":days19.getStringCellValue()):(String.valueOf(	days19.getNumericCellValue())==null?0:String.valueOf(	days19.getNumericCellValue())==null?"0":days19.getNumericCellValue()));
					sdays20	=String.valueOf(String.valueOf(	days20.getCellType())==null?0:days20.getCellType()==1?(	days20.getStringCellValue()==null?"0":days20.getStringCellValue()):(String.valueOf(	days20.getNumericCellValue())==null?0:String.valueOf(	days20.getNumericCellValue())==null?"0":days20.getNumericCellValue()));
					sdays21	=String.valueOf(String.valueOf(	days21.getCellType())==null?0:days21.getCellType()==1?(	days21.getStringCellValue()==null?"0":days21.getStringCellValue()):(String.valueOf(	days21.getNumericCellValue())==null?0:String.valueOf(	days21.getNumericCellValue())==null?"0":days21.getNumericCellValue()));
					sdays22	=String.valueOf(String.valueOf(	days22.getCellType())==null?0:days22.getCellType()==1?(	days22.getStringCellValue()==null?"0":days22.getStringCellValue()):(String.valueOf(	days22.getNumericCellValue())==null?0:String.valueOf(	days22.getNumericCellValue())==null?"0":days22.getNumericCellValue()));
					sdays23	=String.valueOf(String.valueOf(	days23.getCellType())==null?0:days23.getCellType()==1?(	days23.getStringCellValue()==null?"0":days23.getStringCellValue()):(String.valueOf(	days23.getNumericCellValue())==null?0:String.valueOf(	days23.getNumericCellValue())==null?"0":days23.getNumericCellValue()));
					sdays24	=String.valueOf(String.valueOf(	days24.getCellType())==null?0:days24.getCellType()==1?(	days24.getStringCellValue()==null?"0":days24.getStringCellValue()):(String.valueOf(	days24.getNumericCellValue())==null?0:String.valueOf(	days24.getNumericCellValue())==null?"0":days24.getNumericCellValue()));
					sdays25	=String.valueOf(String.valueOf(	days25.getCellType())==null?0:days25.getCellType()==1?(	days25.getStringCellValue()==null?"0":days25.getStringCellValue()):(String.valueOf(	days25.getNumericCellValue())==null?0:String.valueOf(	days25.getNumericCellValue())==null?"0":days25.getNumericCellValue()));
					sdays26	=String.valueOf(String.valueOf(	days26.getCellType())==null?0:days26.getCellType()==1?(	days26.getStringCellValue()==null?"0":days26.getStringCellValue()):(String.valueOf(	days26.getNumericCellValue())==null?0:String.valueOf(	days26.getNumericCellValue())==null?"0":days26.getNumericCellValue()));
					sdays27	=String.valueOf(String.valueOf(	days27.getCellType())==null?0:days27.getCellType()==1?(	days27.getStringCellValue()==null?"0":days27.getStringCellValue()):(String.valueOf(	days27.getNumericCellValue())==null?0:String.valueOf(	days27.getNumericCellValue())==null?"0":days27.getNumericCellValue()));
					
					if(dayss==28)
					{
						sdays28	=String.valueOf(String.valueOf(	days28.getCellType())==null?0:days28.getCellType()==1?(	days28.getStringCellValue()==null?"0":days28.getStringCellValue()):(String.valueOf(	days28.getNumericCellValue())==null?0:String.valueOf(	days28.getNumericCellValue())==null?"0":days28.getNumericCellValue()));
						sdays29="NA";
						sdays30="NA";
						sdays31="NA";
					}
					else if(dayss==29)
					{
						sdays28	=String.valueOf(String.valueOf(	days28.getCellType())==null?0:days28.getCellType()==1?(	days28.getStringCellValue()==null?"0":days28.getStringCellValue()):(String.valueOf(	days28.getNumericCellValue())==null?0:String.valueOf(	days28.getNumericCellValue())==null?"0":days28.getNumericCellValue()));
						sdays29	=String.valueOf(String.valueOf(	days29.getCellType())==null?0:days29.getCellType()==1?(	days29.getStringCellValue()==null?"0":days29.getStringCellValue()):(String.valueOf(	days29.getNumericCellValue())==null?0:String.valueOf(	days29.getNumericCellValue())==null?"0":days29.getNumericCellValue()));
						sdays30="NA";
								sdays31="NA";
					}
					else if(dayss==30)
					{
						sdays28	=String.valueOf(String.valueOf(	days28.getCellType())==null?0:days28.getCellType()==1?(	days28.getStringCellValue()==null?"0":days28.getStringCellValue()):(String.valueOf(	days28.getNumericCellValue())==null?0:String.valueOf(	days28.getNumericCellValue())==null?"0":days28.getNumericCellValue()));
						sdays29	=String.valueOf(String.valueOf(	days29.getCellType())==null?0:days29.getCellType()==1?(	days29.getStringCellValue()==null?"0":days29.getStringCellValue()):(String.valueOf(	days29.getNumericCellValue())==null?0:String.valueOf(	days29.getNumericCellValue())==null?"0":days29.getNumericCellValue()));
						sdays30	=String.valueOf(String.valueOf(	days30.getCellType())==null?0:days30.getCellType()==1?(	days30.getStringCellValue()==null?"0":days30.getStringCellValue()):(String.valueOf(	days30.getNumericCellValue())==null?0:String.valueOf(	days30.getNumericCellValue())==null?"0":days30.getNumericCellValue()));
						sdays31="NA";
					}
					else{
						sdays28	=String.valueOf(String.valueOf(	days28.getCellType())==null?0:days28.getCellType()==1?(	days28.getStringCellValue()==null?"0":days28.getStringCellValue()):(String.valueOf(	days28.getNumericCellValue())==null?0:String.valueOf(	days28.getNumericCellValue())==null?"0":days28.getNumericCellValue()));
						sdays29	=String.valueOf(String.valueOf(	days29.getCellType())==null?0:days29.getCellType()==1?(	days29.getStringCellValue()==null?"0":days29.getStringCellValue()):(String.valueOf(	days29.getNumericCellValue())==null?0:String.valueOf(	days29.getNumericCellValue())==null?"0":days29.getNumericCellValue()));
						sdays30	=String.valueOf(String.valueOf(	days30.getCellType())==null?0:days30.getCellType()==1?(	days30.getStringCellValue()==null?"0":days30.getStringCellValue()):(String.valueOf(	days30.getNumericCellValue())==null?0:String.valueOf(	days30.getNumericCellValue())==null?"0":days30.getNumericCellValue()));
						sdays31	=String.valueOf(String.valueOf(	days31.getCellType())==null?0:days31.getCellType()==1?(	days31.getStringCellValue()==null?"0":days31.getStringCellValue()):(String.valueOf(	days31.getNumericCellValue())==null?0:String.valueOf(	days31.getNumericCellValue())==null?"0":days31.getNumericCellValue()));
					}
					
					
						
						 System.out.println("EXCEL sdays1"+sdays1);
						//udb.setEmpcode(empno.getStringCellValue().replaceAll("\\s",""));
						
						   dt=DATE.getStringCellValue().replaceAll("\\s","").toUpperCase();
						 System.out.println("EXCEL dt"+dt);
							String formatedDate=ReportDAO.EOM(dt);
							
							
						 udb.setDateG(formatedDate);
						 udb.setEMPCODE(EMP_CODE.getStringCellValue().replaceAll("\\s",""));
						 
						 udb.setDays1(sdays1);
						 udb.setDays2(sdays2);
						 udb.setDays3(sdays3);
						 udb.setDays4(sdays4);
						 udb.setDays5(sdays5);
						 udb.setDays6(sdays6);
						 udb.setDays7(sdays7);
						 udb.setDays8(sdays8);
						 udb.setDays9(sdays9);
						 udb.setDays10(sdays10);
						 udb.setDays11(sdays11);
						 udb.setDays12(sdays12);
						 udb.setDays13(sdays13);
						 udb.setDays14(sdays14);
						 udb.setDays15(sdays15);
						 udb.setDays16(sdays16);
						 udb.setDays17(sdays17);
						 udb.setDays18(sdays18);
						 udb.setDays19(sdays19);
						 udb.setDays20(sdays20);
						 udb.setDays21(sdays21);
						 udb.setDays22(sdays22);
						 udb.setDays23(sdays23);
						 udb.setDays24(sdays24);
						 udb.setDays25(sdays25);
						 udb.setDays26(sdays26);
						 udb.setDays27(sdays27);
						 udb.setDays28(sdays28);
						 udb.setDays29(sdays29);
						 udb.setDays30(sdays30);
						 udb.setDays31(sdays31);

						 
						 
						 /*udb.setAmount130(str130);								
						 udb.setAmount129(str129);
						 udb.setAmount132(str132);
						 udb.setAmount133(str133);
						 udb.setAmount142(str142);
						 udb.setAmount210(str210);
						 udb.setAmount211(str211);
						 udb.setAmount213(str213);
						 udb.setAmount216(str216);
						 udb.setAmount264(str264);*/
						 
						 
						 list.add(udb);
						
				
				}
				
				if(date1.equalsIgnoreCase(dt))
				{
					 System.out.println("THIS IS THE LIST SIZE==>"+list.size());
				      int flg_cnt	=	uddao.uploadAttend(list);
				      System.out.println("cnt G:"+cnt);
					 // int flg_cnt	=	1;
				       if(flg_cnt==cnt) {
				    	   response.sendRedirect("UploadAttendance.jsp?flag=1");
				    	   
				       }else {
				    	   response.sendRedirect("UploadAttendance.jsp?flag=2");
				       }
				       
				  }else {
					  response.sendRedirect("UploadAttendance.jsp?flag=3");
				  }
			  }
			  else {
				  response.sendRedirect("UploadAttendance.jsp?flag=3");
			  }
		  }catch(Exception e)
		  {
			  response.sendRedirect("UploadAttendance.jsp?flag=2");
			  e.printStackTrace();
		  }
	
	
		}
		else if(action.equalsIgnoreCase("approveAttendance")){
			
			String user = session.getAttribute("name").toString();
			String date = request.getParameter("sancDate");
			String emplist = request.getParameter("empList");
			UploadAttendanceDAO TH = new UploadAttendanceDAO();
AttendanceHandler ahand = new AttendanceHandler();
			String act= "allEmp";
			int flag1 = ahand.checkAttendance(emplist,act,date);
			boolean  flag =  false;
			System.out.println(" flag 1 : "+flag1);
			
			if(flag1 == 1)
			{
				 flag = TH.finalizeAttendance(emplist,date,user);
			}
			else
			{
				flag = false;
			}
			
			/* flag = TH.finalizeAttendance(emplist,date,user);*/
			
			
			if(flag ){
			response.sendRedirect("FinalizeAttendance.jsp?flag=1");
			}
			else
			{	response.sendRedirect("FinalizeAttendance.jsp?flag=2");
			}
			
			
		}
		
		  else if(action.equalsIgnoreCase("chkAttendDAte"))
		  { 
			  
			  String date = request.getParameter("date");
			  System.out.println("chkAttendDAte Servlet :"+date);
			  UploadAttendanceDAO uad = new UploadAttendanceDAO(); 
			  int flag = uad.chkAttendDate(date);
			  
			  System.out.println("aprroveAttendance " + flag);
				response.setContentType("text/html");
				response.getWriter().write(String.valueOf(flag));
		  
		  }
		 
		
		if(action.equalsIgnoreCase("TakeAttendance"))
		{
			System.out.println("HI TakeAttendance..............");
			
			  String date = request.getParameter("date");
			  
			  System.out.println("SERVLET DATE  :"+date);
			  
				  
			  UploadAttendanceDAO uad = new UploadAttendanceDAO(); 
			  int flag = uad.takeAttendance(date);
			  
			  System.out.println("aprroveAttendance " + flag);
				response.setContentType("text/html");
				response.getWriter().write(String.valueOf(flag));
		System.out.println("STATENMENT :"+(String.valueOf(flag)));
		response.sendRedirect("UploadAttendance.jsp");
		
		
		}
		
		
		
		 if(action.equalsIgnoreCase("attendanceDate"))
		 {

				System.out.println("In attendanceDate");
				  String FrmDate = "";
				  String ToDate = "";
				System.out.println("In betDate LMS");
				System.out.println("DATE CHECK : "+request.getParameter("date"));
				
				
				String Frdt ="01-"+request.getParameter("date");
				String Todt ="01-"+request.getParameter("date");
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			    SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
			 
			    try {
					 FrmDate = output.format(sdf.parse(Frdt));
				     ToDate = output.format(sdf.parse(Todt));
				} catch (java.text.ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			   
				System.out.println("In LMS 01:");
			System.out.println("FrmDate : "+FrmDate);
				
				int appn = Integer.parseInt(request.getParameter("appNo"));
				System.out.println("In LMS 001:"+appn);
				Integer flags = null;
				//int flag = 0;
				 //LMH lmh4=new LMH();
				UploadAttendanceDAO lmh4=new UploadAttendanceDAO();
				   try
				   {
					   flags=lmh4.chk_Attendance_month(FrmDate,ToDate,appn);
					   PrintWriter out1 = response.getWriter();
						response.setContentType("text/html");
						out1.write(flags.toString());
						 System.out.println("betDate leave flg:-  "+flags);
			    } 
				   	catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    
		 }
		
		 
		 
	}

}
