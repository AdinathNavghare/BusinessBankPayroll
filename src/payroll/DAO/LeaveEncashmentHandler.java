package payroll.DAO;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.jfree.ui.Align;

import payroll.Core.Calculate;
import payroll.Core.ErrorLog;
import payroll.Core.ReportDAO;
import payroll.Core.UtilityDAO;
import payroll.Core.UtilityDAO.Footer1;
import payroll.Core.originalNumToLetter;
import payroll.Model.EmpOffBean;
import payroll.Model.LMB;
import payroll.Model.LeaveEncashmentBean;
import payroll.Model.RepoartBean;
import payroll.Model.TranBean;
import payroll.Model.TransactionBean;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.GrayColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

public class LeaveEncashmentHandler {

	static String lable1="";
	static String label2="";
	ErrorLog errorLog=new ErrorLog();
	public class Footer extends PdfPageEventHelper {

		protected Phrase footer;
		protected Phrase header;
		private  Font footerFont = new Font(Font.TIMES_ROMAN, 12,Font.BOLD);
		
		PdfTemplate total;
		public void onOpenDocument(PdfWriter writer, Document document) {
			total = writer.getDirectContent().createTemplate(20, 30);
		}

		public Footer(String lbl)
		{
			LeaveEncashmentHandler.lable1=lbl;
		}

		public void onEndPage(PdfWriter writer, Document document) {

			PdfPTable table = new PdfPTable(3);

			try{
				table.setWidths(new int[]{24,24,2});
				table.setTotalWidth(880);
				table.setLockedWidth(true);
				table.getDefaultCell().setFixedHeight(30);
				table.getDefaultCell().setBorder(Rectangle.BOTTOM);
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(new Phrase(lable1+"( "+ReportDAO.getSysDate()+" )",footerFont));
				table.addCell(new Phrase(String.format("Page %d of  ", writer.getPageNumber()),footerFont));
				PdfPCell cell = new PdfPCell(Image.getInstance(total));
				cell.setBorder(0);
				cell.setHorizontalAlignment(Align.LEFT);
				//cell.setFixedHeight(72f);
				table.addCell(cell);
				table.writeSelectedRows(0, -1, 2, 35, writer.getDirectContent());

			} catch(DocumentException de){ throw new ExceptionConverter(de);}

		}

		public void onCloseDocument(PdfWriter writer, Document document) {
			ColumnText.showTextAligned(total, Element.ALIGN_BOTTOM, new Phrase(String.valueOf(writer.getPageNumber()-1),footerFont),15,20,0);
		}


	}
	
	EmpAttendanceHandler empAttendanceHandler=new EmpAttendanceHandler();
	String currentdate = empAttendanceHandler.getServerDate();
	
	 //BY HEMANT
	public boolean addLeaveEncash(LeaveEncashmentBean leaveEncashmentBean,int loggedEmployeeNo) {

		boolean result = false;
	    boolean first,second,third=false;
		
		
		
			String query = "select PRJ_SRNO from EMPTRAN where EMPNO="+leaveEncashmentBean.getEmpNo()+"" +
					"and  EFFDATE=(select MAX (effdate) from EMPTRAN where EMPNO="+leaveEncashmentBean.getEmpNo()+") ";
				Connection connection = ConnectionManager.getConnection();
				ResultSet resultSet = null;
				int site=0;
				try
				{
					Statement statement = connection.createStatement();
					resultSet = statement.executeQuery(query);
						
						
			
				if(resultSet.next())
				{
					site =	(resultSet.getInt("PRJ_SRNO"));
					
				}	
					
						
					//INSERTING INTO LEAVE_ENCASHMENT 
			
			String insertQuery = "INSERT INTO LEAVE_ENCASHMENT (EMPNO,SITE_ID,LEAVE_BAL,MAX_LIMIT," +
					                          "ENCASH_APPLICABLE,MONTHLY_GROSS,ESIC_AMT,ENCASHMENT_AMT," +
					                          " CREATED_BY,CREATED_DATE,STATUS, "+
					                          "LEAVE_ENCASHMENT_SANCTION,LEAVE_ENCASHMENT_DATE,"+
					                          "FROM_DATE,TO_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setInt(1, leaveEncashmentBean.getEmpNo());
			preparedStatement.setInt(2, site);
			preparedStatement.setFloat(3, leaveEncashmentBean.getLeaveBal());
			preparedStatement.setFloat(4,leaveEncashmentBean.getMaxLimit());
			preparedStatement.setFloat(5,leaveEncashmentBean.getEncashApplicable());
			preparedStatement.setFloat(6, leaveEncashmentBean.getMonthlyGross());
			preparedStatement.setFloat(7, leaveEncashmentBean.getEsicAmt());
			preparedStatement.setFloat(8, leaveEncashmentBean.getEncashmentAmt());
			preparedStatement.setInt(9, loggedEmployeeNo);
			preparedStatement.setString (10, currentdate);
			preparedStatement.setString (11,"SANCTION" );
			preparedStatement.setFloat(12, leaveEncashmentBean.getLeaveEncashmentSanction());
			preparedStatement.setString (13, leaveEncashmentBean.getLeaveEncashmentDate());
			preparedStatement.setString (14, leaveEncashmentBean.getFromDate());
			preparedStatement.setString (15, leaveEncashmentBean.getToDate());
			/*preparedStatement.setString(9, requestMacId);*/
			preparedStatement.executeUpdate();
	
			first=true;
			//INSERTING INTO LEAVETRAN
			
			ResultSet rs2 = null;
			rs2 = statement.executeQuery("SELECT MAX(APPLNO)+1 FROM LEAVETRAN");
			int applno = 0;
			if(rs2.next()){
				applno = rs2.getInt(1);
			}
			insertQuery="INSERT INTO LEAVETRAN (EMPNO,LEAVECD,TRNDATE,TRNTYPE,APPLNO," +
					   "FRMDT,TODT,LREASON,LADDR,LTELNO,DAYS,STATUS) values(?,?,?,?,?,?,?,?,?,?,?,?)";
			
			preparedStatement = connection.prepareStatement(insertQuery);
 			LMB lbean=new LMB();
			preparedStatement.setInt(1,leaveEncashmentBean.getEmpNo());
			preparedStatement.setInt(2,5); //5 for LEAVE ENCASHMENT
			preparedStatement.setString(3, leaveEncashmentBean.getLeaveEncashmentDate());
		    preparedStatement.setString(4,"D");
		    preparedStatement.setInt(5, applno);
		    preparedStatement.setString(6,leaveEncashmentBean.getFromDate());
		    preparedStatement.setString(7,  leaveEncashmentBean.getToDate());
		    preparedStatement.setString(8, "SANC. DTD."+leaveEncashmentBean.getLeaveEncashmentDate()+"-AS PER ADM");
		    preparedStatement.setString(9,lbean.getLADDR());
		    preparedStatement.setLong(10,lbean.getLTELNO());
		    preparedStatement.setFloat(11, leaveEncashmentBean.getLeaveEncashmentSanction());
		    preparedStatement.setString(12, "SANCTION");
		   
		    preparedStatement.executeUpdate();
		    
		    second=true;
		    
			//UPDATING LEAVEBAL
			String updateQuery="update LEAVEBAL set BALDT='"+leaveEncashmentBean.getLeaveEncashmentDate()+"'," +
					"BAL=BAL-"+leaveEncashmentBean.getLeaveEncashmentSanction()+"," +
							"TOTDR=TOTDR+"+leaveEncashmentBean.getLeaveEncashmentSanction()+"" +
									"where LEAVECD=1 and EMPNO="+leaveEncashmentBean.getEmpNo();
			preparedStatement = connection.prepareStatement(updateQuery);
			preparedStatement.executeUpdate();
			
			third=true;
			if(first && second&& third){
			result = true;
			}
			preparedStatement.close();

	
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		
			 errorLog.errorLog("LeaveEncashmentHandler: ERROR INSERTING IN LEAVE_ENCASHMENT METHOD: addLeaveEncash. FOR PAGE: leaveEncashment.jsp", e.toString());
		}
		return result;
		
	}

	 //BY HARSHAL
	public boolean addLeaveEncash1(LeaveEncashmentBean leaveEncashmentBean,int loggedEmployeeNo) {

			boolean result = false;
		    boolean first;

			String query = "select PRJ_SRNO from EMPTRAN where EMPNO="+leaveEncashmentBean.getEmpNo()+"" +
						   "and  EFFDATE=(select MAX (effdate) from EMPTRAN where EMPNO="+leaveEncashmentBean.getEmpNo()+") ";
			
			Connection connection = ConnectionManager.getConnection();
			ResultSet resultSet = null;
			int site=0;
			try
			{
				Statement statement = connection.createStatement();
				resultSet = statement.executeQuery(query);
		
				if(resultSet.next())
				{
					site =	(resultSet.getInt("PRJ_SRNO"));		
				}	
	
				//INSERTING INTO LEAVE_ENCASHMENT 
				String insertQuery = "INSERT INTO LEAVE_ENCASHMENT (EMPNO,SITE_ID,MAX_LIMIT," +
						                          "ENCASH_APPLICABLE,MONTHLY_GROSS,ESIC_AMT,ENCASHMENT_AMT," +
						                          " CREATED_BY,CREATED_DATE, "+
						                          "LEAVE_ENCASHMENT_DATE,"+
						                          "FROM_DATE,TO_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?)";
				
				PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
				
				preparedStatement.setInt(1, leaveEncashmentBean.getEmpNo());
				preparedStatement.setInt(2, site);
		//		preparedStatement.setFloat(3, leaveEncashmentBean.getLeaveBal());
				preparedStatement.setFloat(3,leaveEncashmentBean.getMaxLimit());
				preparedStatement.setFloat(4,leaveEncashmentBean.getEncashApplicable());
				preparedStatement.setFloat(5, leaveEncashmentBean.getMonthlyGross());			
		//		preparedStatement.setFloat(7, leaveEncashmentBean.getEsicAmt()); 
		//	 	ESIC AMT IS SET TO BE ZERO
				preparedStatement.setFloat(6,0);
				preparedStatement.setFloat(7, leaveEncashmentBean.getEncashmentAmt());				
				preparedStatement.setInt(8, loggedEmployeeNo);	
				preparedStatement.setString (9, leaveEncashmentBean.getLeaveEncashmentDate());
		//		preparedStatement.setString (11,"SANCTION" );
		//		preparedStatement.setFloat(12, leaveEncashmentBean.getLeaveEncashmentSanction());
				preparedStatement.setString (10, leaveEncashmentBean.getLeaveEncashmentDate());		
				preparedStatement.setString (11, leaveEncashmentBean.getFromDate());
				preparedStatement.setString (12, leaveEncashmentBean.getToDate());		
				/*preparedStatement.setString(9, requestMacId);*/
				
				first = true;
				
				preparedStatement.executeUpdate();
				preparedStatement.close();
				connection.close();
				
				if(first){
					result = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				ErrorLog errorLog=new ErrorLog();
				errorLog.errorLog("LeaveEncashmentHandler: ERROR INSERTING IN LEAVE_ENCASHMENT METHOD: addLeaveEncash. FOR PAGE: leaveEncashment.jsp", e.toString());
			}
			return result;
			
		}


	public LeaveEncashmentBean enCashAmount(int empno)//for getting string directly specially  for grid view
	{
		LeaveEncashmentBean  leaveEncashmentBean  = new LeaveEncashmentBean();
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("  SELECT ENCASHMENT_AMT, LEAVE_ENCASHMENT_DATE FROM LEAVE_ENCASHMENT where empno="+empno+" and srno= ( select max(SRNO)from LEAVE_ENCASHMENT where EMPNO="+empno+") ");
			while(rs.next())
			{
				leaveEncashmentBean.setEncashmentAmt(rs.getFloat("ENCASHMENT_AMT"));
				leaveEncashmentBean.setLeaveEncashmentDate(rs.getString("LEAVE_ENCASHMENT_DATE"));
				
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return leaveEncashmentBean;
	}
	
	//for print the encashment of  employees by hrishi
	public void getPrintEncash(String filePath,String date, String todate, String imagepath, String employeeType, String EMPNO, String Branch)
	{

			System.out.println("in getPrintEncash");

			RepoartBean repBean  = new RepoartBean();
			UtilityDAO dao = new UtilityDAO();
			Connection con =null;
			
			

			EmpOffHandler eoffhdlr = new EmpOffHandler();
			EmpOffBean eoffbn = new EmpOffBean();
			
		
			
			String EmpSql = ""; 
			
				try
			{

				ReportDAO.OpenCon("", "", "",repBean);
				con = repBean.getCn();	
				Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
				System.out.println("in encashment report ");
				Document doc = new Document(new Rectangle(2384,3370));
				PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filePath));
				Footer1 ftr = dao.new Footer1(lable1);
				writer.setPageEvent(ftr);
				doc.open();
				Font FONT = new Font(Font.HELVETICA,190, Font.NORMAL, new GrayColor(0.85f));
				ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Namco Bank",FONT), 1300f, 1830f, 45);			
				Image image1 = Image.getInstance(imagepath);		
				Phrase title = new Phrase("THE BUSINESS CO.OP BANK LTD.",new Font(FONT.TIMES_ROMAN,38,Font.BOLD));
				Paragraph para = new Paragraph(title);
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingBefore(0);
				image1.scaleAbsolute(310f, 120f);
				image1.setAbsolutePosition(60f, 3200f);
				doc.add(image1);
				doc.add(para);
				para = new Paragraph(new Phrase("Rajan Complex Arcade, Datta Mandir, Nashik Road, Nashik - 422101",new Font(Font.TIMES_ROMAN,28)));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(0);

				doc.add(para);
				para = new Paragraph(new Phrase("Tel : +91-20 26812190",new Font(Font.TIMES_ROMAN,28)));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(0);

				doc.add(para);
				para = new Paragraph(new Phrase("Email : adm@namcobank.in",new Font(Font.TIMES_ROMAN,28)));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(50);

				doc.add(para);

				Font f = new Font(Font.HELVETICA,30);
				Font f1 = new Font(Font.HELVETICA,25);
		


				para = new Paragraph(new Phrase("Enacashment List for the Month Of :- "+date+" to "+todate+" ",new Font(Font.TIMES_ROMAN,25,Font.BOLD)));

				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(20);

				doc.add(para);

				Rectangle rec = new  Rectangle(100,100);


				PdfPTable datatab;
				PdfPTable datatot;
				PdfPTable main = new PdfPTable(7);
				main.setSpacingBefore(20);
				main.setWidthPercentage(new float[]{9,20,30,9,9,9,9f}, rec);

				PdfPCell maincell ;

				maincell = new PdfPCell(new Phrase("EMP CODE",f));
				maincell.setFixedHeight(50);
				maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
				main.addCell(maincell);

				maincell = new PdfPCell(new Phrase("EMPLOYEE NAME",f));
				maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
				main.addCell(maincell);

				maincell = new PdfPCell(new Phrase("Branch Name",f));
				maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
				main.addCell(maincell);

				maincell = new PdfPCell(new Phrase("Leave Encash Date",f));
				maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
				main.addCell(maincell);

				maincell = new PdfPCell(new Phrase("Leave Encash Amount",f));
				maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
				main.addCell(maincell);

				maincell = new PdfPCell(new Phrase("Monthly Gross",f));
				maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
				main.addCell(maincell);


				maincell = new PdfPCell(new Phrase("Total Amount",f));
				maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
				main.addCell(maincell);

				doc.add(main);

				if(employeeType.equalsIgnoreCase("one"))
				{
					int empno= Integer.parseInt(EMPNO);
					
				 EmpSql="SELECT e.EMPCODE ,e.empno ,rtrim(e.fname)+' '+rtrim(e.mname)+' '+rtrim(e.lname)as  name, " +
						"  l.leave_encashment_date,l.ENCASHMENT_AMT,l.MONTHLY_GROSS" +
						"  from EMPMAST e join LEAVE_ENCASHMENT l on e.empno= "+empno+" where " +
					/*  "  l.srno= ( select max(SRNO)from LEAVE_ENCASHMENT where empno= "+empno+" ) and " +*/
						"   l.EMPNO= "+empno+" and "+
						"  l.LEAVE_ENCASHMENT_DATE between '"+date+"' and '"+todate+"' ";

				
				System.out.println(EmpSql);
				ResultSet rs = st.executeQuery(EmpSql);
				
				while(rs.next()){	
				
				
				
				datatab = new PdfPTable(7);

				datatab.setWidthPercentage(new float[]{9,20,30,9,9,9,9f}, rec);

				datatab.setHorizontalAlignment(Element.ALIGN_CENTER);

				PdfPCell cell1 = new PdfPCell(new Phrase(""+rs.getString("EMPCODE"),f1));
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setFixedHeight(50);
				datatab.addCell(cell1);

				PdfPCell cell2 = new PdfPCell(new Phrase(""+rs.getString("name"),f1));
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				datatab.addCell(cell2);

				
				eoffbn = eoffhdlr.getEmpOfficAddInfo(rs.getString("empno"));
				String site_name = eoffbn.getPrj_name();
				
				PdfPCell cell3 = new PdfPCell(new Phrase(""+site_name,f1));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				datatab.addCell(cell3);


				
				PdfPCell cell4 = new PdfPCell(new Phrase(""+rs.getString("LEAVE_ENCASHMENT_DATE"),f1));
				cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
				datatab.addCell(cell4);

			
				PdfPCell cell5 = new PdfPCell(new Phrase(""+rs.getFloat("ENCASHMENT_AMT"),f1));
				cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
				datatab.addCell(cell5);

				
				PdfPCell cell6 = new PdfPCell(new Phrase(""+rs.getFloat("MONTHLY_GROSS"),f1));
				cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
				datatab.addCell(cell6);


				
				PdfPCell cell7 = new PdfPCell(new Phrase(""+(rs.getFloat("ENCASHMENT_AMT")+rs.getFloat("MONTHLY_GROSS")),f1));
				cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
				datatab.addCell(cell7);
				
				
				doc.add(datatab);
					
					
					
				}
					
			}	
				
				if(employeeType.equalsIgnoreCase("all"))
				{
					
					System.out.println("inside all");
				EmpSql="SELECT e.EMPCODE ,e.empno ,rtrim(e.fname)+' '+rtrim(e.lname)as  name, " +
						"  l.leave_encashment_date,l.ENCASHMENT_AMT,l.MONTHLY_GROSS" +
						"  from EMPMAST e join LEAVE_ENCASHMENT l on e.empno= l.EMPNO where " +
					/*  "  l.srno= ( select max(SRNO)from LEAVE_ENCASHMENT where empno= l.EMPNO ) and " +*/
					   	"  l.LEAVE_ENCASHMENT_DATE between '"+date+"' and '"+todate+"' ";

				
				
				ResultSet rs = st.executeQuery(EmpSql);
				
				while(rs.next()){	
				
				
					System.out.println(EmpSql);
				datatab = new PdfPTable(7);

				datatab.setWidthPercentage(new float[]{9,20,30,9,9,9,9f}, rec);

				datatab.setHorizontalAlignment(Element.ALIGN_CENTER);

				PdfPCell cell1 = new PdfPCell(new Phrase(""+rs.getString("EMPCODE"),f1));
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setFixedHeight(50);
				datatab.addCell(cell1);

				PdfPCell cell2 = new PdfPCell(new Phrase(""+rs.getString("name"),f1));
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				datatab.addCell(cell2);

				
				eoffbn = eoffhdlr.getEmpOfficAddInfo(rs.getString("empno"));
				String site_name = eoffbn.getPrj_name();
				
				PdfPCell cell3 = new PdfPCell(new Phrase(""+site_name,f1));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				datatab.addCell(cell3);


				
				PdfPCell cell4 = new PdfPCell(new Phrase(""+rs.getString("LEAVE_ENCASHMENT_DATE"),f1));
				cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
				datatab.addCell(cell4);

			
				PdfPCell cell5 = new PdfPCell(new Phrase(""+rs.getFloat("ENCASHMENT_AMT"),f1));
				cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
				datatab.addCell(cell5);

				
				PdfPCell cell6 = new PdfPCell(new Phrase(""+rs.getFloat("MONTHLY_GROSS"),f1));
				cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
				datatab.addCell(cell6);


				
				PdfPCell cell7 = new PdfPCell(new Phrase(""+(rs.getFloat("ENCASHMENT_AMT")+rs.getFloat("MONTHLY_GROSS")),f1));
				cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
				datatab.addCell(cell7);
				
				
				doc.add(datatab);
					
					
					
				}
					
			}
				
				
				if(employeeType.equalsIgnoreCase("branch"))
				{
					
					
					System.out.println("inside branch");
					if(Branch.equalsIgnoreCase("ALL"))
					{
						EmpSql="SELECT e.EMPCODE ,e.empno ,rtrim(e.fname)+' '+rtrim(e.mname)+' '+rtrim(e.lname)as  name, " +
					 	         "  l.leave_encashment_date,l.ENCASHMENT_AMT,l.MONTHLY_GROSS" +
					 	         "  from EMPMAST e join LEAVE_ENCASHMENT l on e.empno= l.EMPNO where " +
					 	     /*  "  l.srno= ( select max(SRNO)from LEAVE_ENCASHMENT where empno= l.EMPNO ) and " +*/
					 	     	 "  l.LEAVE_ENCASHMENT_DATE between '"+date+"' and '"+todate+"' ";
					}
					else{
						  int branchNO=Integer.parseInt(Branch);
					      EmpSql="SELECT e.EMPCODE ,e.empno ,rtrim(e.fname)+' '+rtrim(e.mname)+' '+rtrim(e.lname)as  name, " +
					 	         "  l.leave_encashment_date,l.ENCASHMENT_AMT,l.MONTHLY_GROSS" +
					 	         "  from EMPMAST e join LEAVE_ENCASHMENT l on e.empno= l.EMPNO where " +
					 	     /*  "  l.srno= ( select max(SRNO)from LEAVE_ENCASHMENT where empno= l.EMPNO ) and " +*/
					 	     	 "  l.LEAVE_ENCASHMENT_DATE between '"+date+"' and '"+todate+"' and l.SITE_ID= "+branchNO+" ";
					    }
				
				
				ResultSet rs = st.executeQuery(EmpSql);
				
				while(rs.next()){	
				
				
				System.out.println(EmpSql);
				datatab = new PdfPTable(7);

				datatab.setWidthPercentage(new float[]{9,20,30,9,9,9,9f}, rec);

				datatab.setHorizontalAlignment(Element.ALIGN_CENTER);

				PdfPCell cell1 = new PdfPCell(new Phrase(""+rs.getString("EMPCODE"),f1));
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setFixedHeight(50);
				datatab.addCell(cell1);

				PdfPCell cell2 = new PdfPCell(new Phrase(""+rs.getString("name"),f1));
				cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				datatab.addCell(cell2);

				
				eoffbn = eoffhdlr.getEmpOfficAddInfo(rs.getString("empno"));
				String site_name = eoffbn.getPrj_name();
				
				PdfPCell cell3 = new PdfPCell(new Phrase(""+site_name,f1));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				datatab.addCell(cell3);


				
				PdfPCell cell4 = new PdfPCell(new Phrase(""+rs.getString("LEAVE_ENCASHMENT_DATE"),f1));
				cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
				datatab.addCell(cell4);

			
				PdfPCell cell5 = new PdfPCell(new Phrase(""+rs.getFloat("ENCASHMENT_AMT"),f1));
				cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
				datatab.addCell(cell5);

				
				PdfPCell cell6 = new PdfPCell(new Phrase(""+rs.getFloat("MONTHLY_GROSS"),f1));
				cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
				datatab.addCell(cell6);


				
				PdfPCell cell7 = new PdfPCell(new Phrase(""+(rs.getFloat("ENCASHMENT_AMT")+rs.getFloat("MONTHLY_GROSS")),f1));
				cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
				datatab.addCell(cell7);
				
				
				doc.add(datatab);
					
					
					
				}
					
			}
				

				st.close();
				con.close();
				doc.close();



			}



			catch(Exception e)
			{
				e.printStackTrace();
			}

		}// ends here
		
	// new method to print encashment like payslip by hrishi
	public void getPrintEncashNew(String filePath,String date, String todate, String imagepath, String employeeType, String EMPNO, String Branch)
	{

			RepoartBean repBean  = new RepoartBean();
		
			try
			{
				Connection Cn = null;
				 ReportDAO.OpenCon("", "", "",repBean);
				    Cn = repBean.getCn();
				    LookupHandler lkp=new LookupHandler();
				ResultSet rs = null;
				ResultSet rs1 = null;
				
				
				String EmpSql = "";
				
				
				float total_amntIN = 0.0f;
				float total_arrIN = 0.0f;
				float total_totIN = 0.0f;
				
				float total_amntDED = 0.0f;
				float total_arrDED = 0.0f;
				float total_totDED = 0.0f;
				float absent = 0.0f;
				float gross = 0.0f;
				float nodays = 0.0f;
				float paiddays = 0.0f;
				float monthygross = 0.0f;
				float cl = 0;
				float pl = 0;
				float sl = 0;
				float ml = 0;
				nodays = Calculate.getDays(date);
				
				String slrydt=date;
				
			String dt1="";
			
			
			Statement st3 = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			
			/*EmpSql = "SELECT emp.*,t.ACNO,t.BRANCH,t.GRADE,t.BANK_NAME FROM EMPMAST emp,EMPTRAN t WHERE t.EMPNO = emp.EMPNO" +
					" AND emp.EMPNO = "+Integer.parseInt(EMPNO)+" AND t.EFFDATE = (SELECT MAX(E2.EFFDATE) FROM EMPTRAN E2 " +
					" WHERE E2.EMPNO = emp.EMPNO AND E2.EFFDATE <= '"+ReportDAO.EOM(date)+"') ORDER BY emp.EMPNO";*/
			if(employeeType.equalsIgnoreCase("one"))
			{
			EmpSql= "   select emp.*,t.ACNO,t.BRANCH,t.GRADE,t.BANK_NAME,l.ENCASHMENT_AMT,l.leave_encashment_date,l.encash_applicable" +
					"   FROM EMPMAST emp,EMPTRAN t,LEAVE_ENCASHMENT l WHERE  l.EMPNO = "+Integer.parseInt(EMPNO)+" " +
					"   AND   l.EMPNO=emp.EMPNO AND  t.EMPNO = emp.EMPNO and " +
					"   t.EFFDATE = (SELECT MAX(E2.EFFDATE) FROM EMPTRAN E2  " +
					"   WHERE E2.EMPNO = emp.EMPNO AND E2.EFFDATE <= '"+ReportDAO.EOM(date)+"') " +
					"   and l.SRNO=(select MAX(srno) from LEAVE_ENCASHMENT l where EMPNO= "+Integer.parseInt(EMPNO)+") ORDER BY emp.EMPNO ";
			}
			
			if(employeeType.equalsIgnoreCase("all"))
			{
			EmpSql= "   select emp.*,t.ACNO,t.BRANCH,t.GRADE,t.BANK_NAME,l.ENCASHMENT_AMT,l.leave_encashment_date,l.encash_applicable" +
					"   FROM EMPMAST emp,EMPTRAN t,LEAVE_ENCASHMENT l WHERE  l.EMPNO = emp.empno " +
					"   AND   l.EMPNO=emp.EMPNO AND  t.EMPNO = emp.EMPNO and " +
					"   t.EFFDATE = (SELECT MAX(E2.EFFDATE) FROM EMPTRAN E2  " +
					"   WHERE E2.EMPNO = emp.EMPNO AND E2.EFFDATE <= '"+ReportDAO.EOM(date)+"') " +
					"   and l.SRNO=(select MAX(srno) from LEAVE_ENCASHMENT l where EMPNO= emp.empno) ORDER BY emp.EMPNO ";
			}
			
			
			if(employeeType.equalsIgnoreCase("branch"))
			{
				if(Branch.equals("ALL"))
				{
					
					EmpSql= "   select emp.*,t.ACNO,t.BRANCH,t.GRADE,t.BANK_NAME,l.ENCASHMENT_AMT,l.leave_encashment_date,l.encash_applicable" +
							"   FROM EMPMAST emp,EMPTRAN t,LEAVE_ENCASHMENT l WHERE  l.EMPNO = emp.empno " +
							"   AND   l.EMPNO=emp.EMPNO AND  t.EMPNO = emp.EMPNO and " +
							"   t.EFFDATE = (SELECT MAX(E2.EFFDATE) FROM EMPTRAN E2  " +
							"   WHERE E2.EMPNO = emp.EMPNO AND E2.EFFDATE <= '"+ReportDAO.EOM(date)+"') " +
							"   and l.SRNO=(select MAX(srno) from LEAVE_ENCASHMENT l where EMPNO= emp.empno) ORDER BY emp.EMPNO ";
					
				}
				
				else{
			        EmpSql= "   select emp.*,t.ACNO,t.BRANCH,t.GRADE,t.BANK_NAME,l.ENCASHMENT_AMT,l.leave_encashment_date,l.encash_applicable" +
					"   FROM EMPMAST emp,EMPTRAN t,LEAVE_ENCASHMENT l WHERE  l.EMPNO = emp.empno " +
					"   AND   l.EMPNO=emp.EMPNO AND  t.EMPNO = emp.EMPNO and " +
					"   t.EFFDATE = (SELECT MAX(E2.EFFDATE) FROM EMPTRAN E2  " +
					"   WHERE E2.EMPNO = emp.EMPNO AND E2.EFFDATE <= '"+ReportDAO.EOM(date)+"') " +
					"   and l.SRNO=(select MAX(srno) from LEAVE_ENCASHMENT l where EMPNO= emp.empno) and l.SITE_ID="+Integer.parseInt(Branch)+" ORDER BY emp.EMPNO ";
				}
			}
			
			ResultSet rs3 = st3.executeQuery(EmpSql);
			
			
			System.out.println("---------------"+EmpSql);
			
			if(!rs3.next()){
				System.out.println("Recordset EMpty");
			}
			
			Document doc = new Document();
			PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filePath));
			doc.open();
			doc.setPageSize(PageSize.A4);
			
			
			rs3.beforeFirst();
			while(rs3.next())
			{
				
					total_amntIN = 0.0f;
					total_arrIN = 0.0f;
					total_totIN = 0.0f;

					total_amntDED = 0.0f;
					total_arrDED = 0.0f;
					total_totDED = 0.0f;
					absent = 0.0f;
					gross = 0;
					//nodays = 0.0f;
					paiddays = 0.0f;
					monthygross = 0;

				
				paiddays = nodays;
				
			
			
			Font FONT = new Font(Font.HELVETICA, 52, Font.NORMAL, new GrayColor(0.75f));
			ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("HARSH CONSTRUCTION",FONT), 297.5f, 421, 45);
			
			
						
			Image image1 = Image.getInstance(imagepath);
			
			
			Phrase title = new Phrase("THE BUSINESS CO.OP BANK LTD.",new Font(FONT.TIMES_ROMAN,10,Font.BOLD));
			Paragraph para = new Paragraph(title);
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingBefore(10);
			
		
			
			
			image1.scaleAbsolute(80f, 80f);
			image1.setAbsolutePosition(40f, 730f);
			
			doc.add(image1);
			doc.add(para);
			
			
			para = new Paragraph(new Phrase("Rajan Complex Arcade, Datta Mandir, Nashik Road, Nashik - 422101",new Font(Font.TIMES_ROMAN,10)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);
			
			doc.add(para);
			para = new Paragraph(new Phrase("Tel : +91-20 26812190",new Font(Font.TIMES_ROMAN,10)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);
			
			doc.add(para);
			para = new Paragraph(new Phrase("Email : adm@namco.in",new Font(Font.TIMES_ROMAN,10)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);
			
			doc.add(para);
			para = new Paragraph(new Phrase("Encashment Report For "+slrydt.substring(3),new Font(Font.TIMES_ROMAN,10)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);
			
			doc.add(para);
			
		
			Rectangle rec = new  Rectangle(100,100);
			
			PdfPTable tab = new PdfPTable(1);
			tab.setSpacingBefore(40);
			tab.setWidthPercentage(new float[]{100}, rec);
			tab.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			
			PdfPTable tabt = new PdfPTable(1);
			tabt.setWidthPercentage(new float[]{100}, rec);
			tabt.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			PdfPTable tabsec = new PdfPTable(2);
			tabsec.setWidthPercentage(new float[]{50,50}, new Rectangle(100,100));
			tabsec.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			
			
			PdfPCell cell = new PdfPCell();
			PdfPCell cellt = new PdfPCell();
			
			
			PdfPTable tab1 = new PdfPTable(4);
			PdfPTable tab2 = new PdfPTable(4);
			
			
			PdfPTable tab3 = new PdfPTable(2);
			PdfPTable tab4 = new PdfPTable(2);
			
			PdfPTable tab5 = new PdfPTable(4);
			PdfPTable tab6 = new PdfPTable(4);
			
			PdfPTable tab7 = new PdfPTable(4);
			PdfPTable tab8 = new PdfPTable(4);
			PdfPTable tab9 = new PdfPTable(4);
			PdfPTable tab10 = new PdfPTable(2);
			
			
			PdfPCell cell3 = new PdfPCell();
			PdfPCell cell4 = new PdfPCell();

			PdfPCell cell5 = new PdfPCell();
			PdfPCell cell6 = new PdfPCell();
			PdfPCell cell7 = new PdfPCell();
		//PdfPCell cell8 = new PdfPCell();
			
			  Statement st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				Statement st1 = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			boolean isRecord = false;	
			String frmTable = "";
			String Query="select trndt  from PAYTRAN where empno="+rs3.getInt("EMPNO")+" and TRNDT between '"+date+"' and '"+todate+"' order by trncd";
			System.out.println("query "+ Query);
			rs = st.executeQuery(Query);
			
			if(rs.next())
			{
				frmTable = "PAYTRAN";
				isRecord = true;
				dt1=rs.getString("TRNDT");
				String Query2="select distinct(pay.trncd)  from PAYTRAN pay,cdmast cd where empno="+rs3.getInt("EMPNO")+" and TRNDT  ='"+dt1+"' and pay.trncd = cd.trncd and cd.pslipyn = 'Y' order by trncd";
				System.out.println("Query2 "+Query2);
				rs1 = st1.executeQuery(Query2);
			
			}	
			else
			{
				rs = st.executeQuery("select trndt  from YTDTRAN where empno="+rs3.getInt("EMPNO")+" and TRNDT  LIKE '"+date.substring(0,7)+"%' order by trncd");
				if(rs.next())
				{
					frmTable = "PAYTRAN";
					isRecord = true;
					dt1=rs.getString("TRNDT");
					rs1 = st1.executeQuery("select distinct(pay.trncd)  from PAYTRAN pay,cdmast cd where empno="+rs3.getInt("EMPNO")+" and TRNDT  ='"+dt1+"' and pay.trncd = cd.trncd and cd.pslipyn = 'Y' order by trncd");
					
				}
				else
				{
					rs = st.executeQuery("select trndt  from PAYTRAN where empno="+rs3.getInt("EMPNO")+" and TRNDT  LIKE '"+date.substring(0,7)+"%' order by trncd");
					if(rs.next())
					frmTable = "PAYTRAN";
					isRecord = true;
					dt1=rs.getString("TRNDT");
					rs1 = st1.executeQuery("select distinct(pay.trncd)  from PAYTRAN pay,cdmast cd where empno="+rs3.getInt("EMPNO")+" and TRNDT  ='"+dt1+"' and pay.trncd = cd.trncd and cd.pslipyn = 'Y' order by trncd");
					
				}
				
			}
			
			
			ArrayList<TranBean> trblist= new ArrayList<TranBean>();
			TranBean tb;
			TranHandler th = new TranHandler();
			TransactionBean trbn = new EmpOffHandler().getInfoEmpTran(rs3.getString("EMPNO"));
			
			if(isRecord){
				while(rs1.next())
				{
					
					tb = new TranBean();
					tb=th.getPaySlipTran(rs3.getInt("EMPNO"), rs1.getInt(1), 0, dt1,frmTable);
					trblist.add(tb);
					
					
				}
				
				
				String monthDate = date;
				//	System.out.println(monthDate);
				
				ResultSet rs7 = null;
				Statement statement = repBean.getCn().createStatement();
				rs7=statement.executeQuery("select "+Calculate.getDays(monthDate)+" - DAY(DOJ)+1 from empmast where empno ="+rs3.getInt("EMPNO")+" and doj between cast('"+ReportDAO.BOM(monthDate)+"' as DATE) and cast('"+ReportDAO.EOM(monthDate)+"' as DATE)");
				if(rs7.next())
				{
					paiddays = rs7.getFloat(1);
				}
				//=== For Date of Leaving in same Month 
				rs7=statement.executeQuery("select "+paiddays+" - (DAY(cast('"+ReportDAO.EOM(monthDate)+"' as DATE))- DAY(DOL)) from empmast where empno ="+rs3.getInt("EMPNO")+" and dol between cast('"+ReportDAO.BOM(monthDate)+"' as DATE) and cast('"+ReportDAO.EOM(monthDate)+"' as DATE)");
				if(rs7.next())
				{
					/*int day = rs7.getInt(1);
					paiddays = day;*/
					paiddays = rs7.getFloat(1);
				}
				
				tab3.setWidthPercentage(new float[]{50,50},new Rectangle(100,100));
				PdfPCell subcell3 = new PdfPCell(new Phrase("INCOME ",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				subcell3.setColspan(2);
				subcell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				tab3.addCell(subcell3);
				
				tab4.setWidthPercentage(new float[]{50,50},new Rectangle(100,100));
				PdfPCell subcell5 = new PdfPCell(new Phrase("ENCASHMENT Details ",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				subcell5.setColspan(2);
				subcell5.setHorizontalAlignment(Element.ALIGN_CENTER);
				tab4.addCell(subcell5);
				
					tab5.setWidthPercentage(new float[]{55,15,15,15},new Rectangle(100,100));
					PdfPCell subcell4 = new PdfPCell(new Phrase("Salary & Allowances ",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
					subcell4.setHorizontalAlignment(Element.ALIGN_CENTER);
					tab5.addCell(subcell4);
					
					
					PdfPCell subcellin = new PdfPCell(new Phrase("Amount.(Rs)",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
					subcellin.setHorizontalAlignment(Element.ALIGN_CENTER);
					tab5.addCell(subcellin);
					
					
					PdfPCell subcellin1 = new PdfPCell(new Phrase("Arrears ",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
					subcellin1.setHorizontalAlignment(Element.ALIGN_CENTER);
					tab5.addCell(subcellin1);
					
					
					PdfPCell subcellin2 = new PdfPCell(new Phrase("Total ",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
					subcellin2.setHorizontalAlignment(Element.ALIGN_CENTER);
					tab5.addCell(subcellin2);
				
				
				tab6.setWidthPercentage(new float[]{55,15,15,15},new Rectangle(100,100));
				PdfPCell subcellout1 = new PdfPCell(new Phrase("Encashment date ",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				subcellout1.setHorizontalAlignment(Element.ALIGN_CENTER);
				tab6.addCell(subcellout1);
				
				tab6.setWidthPercentage(new float[]{55,15,15,15},new Rectangle(100,100));
				PdfPCell subcellout2 = new PdfPCell(new Phrase("Amount.(Rs) ",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				subcellout2.setHorizontalAlignment(Element.ALIGN_CENTER);
				tab6.addCell(subcellout2);
				
				tab6.setWidthPercentage(new float[]{55,15,15,15},new Rectangle(100,100));
				PdfPCell subcellout3 = new PdfPCell(new Phrase("Encash Applicable ",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				subcellout3.setHorizontalAlignment(Element.ALIGN_CENTER);
				tab6.addCell(subcellout3);
				
				tab6.setWidthPercentage(new float[]{55,15,15,15},new Rectangle(100,100));
				PdfPCell subcellout4 = new PdfPCell(new Phrase("Total ",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				subcellout4.setHorizontalAlignment(Element.ALIGN_CENTER);
				tab6.addCell(subcellout4);
				
				
					for(TranBean trbn1 : trblist )
					{
						if(trbn1.getTRNCD()<199 && trbn1.getTRNCD()!=127  )
						{
							
							tab5.setWidthPercentage(new float[]{55,15,15,15},new Rectangle(100,100));
							PdfPCell subcells = new PdfPCell(new Phrase(""+CodeMasterHandler.getCDesc(trbn1.getTRNCD()),new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
							subcells.setHorizontalAlignment(Element.ALIGN_LEFT);
							tab5.addCell(subcells);
							
							tab5.setWidthPercentage(new float[]{55,15,15,15},new Rectangle(100,100));
							PdfPCell subcells1 = new PdfPCell(new Phrase(""+trbn1.getCAL_AMT(),new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
							subcells1.setHorizontalAlignment(Element.ALIGN_RIGHT);
							tab5.addCell(subcells1);
							
							tab5.setWidthPercentage(new float[]{55,15,15,15},new Rectangle(100,100));
							PdfPCell subcells2 = new PdfPCell(new Phrase(""+trbn1.getARR_AMT(),new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
							subcells2.setHorizontalAlignment(Element.ALIGN_RIGHT);
							tab5.addCell(subcells2);
							
							tab5.setWidthPercentage(new float[]{55,15,15,15},new Rectangle(100,100));
							PdfPCell subcells3 = new PdfPCell(new Phrase(""+trbn1.getNET_AMT(),new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
							subcells3.setHorizontalAlignment(Element.ALIGN_RIGHT);
							tab5.addCell(subcells3);
							
							total_amntIN = total_amntIN + trbn1.getCAL_AMT() ;
							total_arrIN = total_arrIN + trbn1.getARR_AMT();
						    total_totIN = total_totIN + trbn1.getNET_AMT();
							
							
							
						}
						
						/*else if(300 > trbn1.getTRNCD() && trbn1.getTRNCD()>200 )
						{
							
							tab6.setWidthPercentage(new float[]{55,15,15,15},new Rectangle(100,100));
							PdfPCell subcells = new PdfPCell(new Phrase(""+rs3.getDate("leave_encashment_date"),new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
							subcells.setHorizontalAlignment(Element.ALIGN_LEFT);
							tab6.addCell(subcells);
							
							tab6.setWidthPercentage(new float[]{55,15,15,15},new Rectangle(100,100));
							PdfPCell subcells1 = new PdfPCell(new Phrase(""+rs3.getInt("ENCASHMENT_AMT"),new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
							subcells1.setHorizontalAlignment(Element.ALIGN_RIGHT);
							tab6.addCell(subcells1);
							
							tab6.setWidthPercentage(new float[]{55,15,15,15},new Rectangle(100,100));
							PdfPCell subcells2 = new PdfPCell(new Phrase(""+rs3.getInt("encash_applicable"),new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
							subcells2.setHorizontalAlignment(Element.ALIGN_RIGHT);
							tab6.addCell(subcells2);
							
							tab6.setWidthPercentage(new float[]{55,15,15,15},new Rectangle(100,100));
							PdfPCell subcells3 = new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
							subcells3.setHorizontalAlignment(Element.ALIGN_RIGHT);
							tab6.addCell(subcells3);
							
							total_amntDED = total_amntDED + trbn1.getCAL_AMT();
							total_arrDED = total_arrDED + trbn1.getARR_AMT();
						    total_totDED = total_totDED + trbn1.getNET_AMT();
						}*/
						if(trbn1.getTRNCD()==301)
						{
							absent = trbn1.getCAL_AMT();
							paiddays =paiddays-absent;
						}
						if(trbn1.getTRNCD()==199)
						{
							gross = trbn1.getINP_AMT();
						}
						
						 
						 
						 monthygross = (int)gross-Math.round(((gross/nodays)*absent));
								
						
					}
					
					// for encashmnent details
					total_amntDED=rs3.getFloat("ENCASHMENT_AMT");
					total_arrDED=rs3.getInt("encash_applicable");
					
					tab6.setWidthPercentage(new float[]{35,20,30,15},new Rectangle(100,100));
					PdfPCell subcells = new PdfPCell(new Phrase(""+rs3.getDate("leave_encashment_date"),new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
					subcells.setHorizontalAlignment(Element.ALIGN_LEFT);
					tab6.addCell(subcells);
					
					tab6.setWidthPercentage(new float[]{35,20,30,15},new Rectangle(100,100));
					PdfPCell subcells1 = new PdfPCell(new Phrase(""+rs3.getFloat("ENCASHMENT_AMT"),new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
					subcells1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tab6.addCell(subcells1);
					
					tab6.setWidthPercentage(new float[]{35,20,30,15},new Rectangle(100,100));
					PdfPCell subcells2 = new PdfPCell(new Phrase(""+rs3.getInt("encash_applicable"),new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
					subcells2.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tab6.addCell(subcells2);
					
					tab6.setWidthPercentage(new float[]{35,20,30,15},new Rectangle(100,100));
					PdfPCell subcells3 = new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
					subcells3.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tab6.addCell(subcells3);
					//  ends here
					
					
					
					
					
					
					tab7.setWidthPercentage(new float[]{55,15,15,15},new Rectangle(100,100));
					PdfPCell subcelltin = new PdfPCell(new Phrase("Gross Salary",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
					subcelltin.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tab7.addCell(subcelltin);
					
					tab7.setWidthPercentage(new float[]{55,15,15,15},new Rectangle(100,100));
					PdfPCell subcelltin1 = new PdfPCell(new Phrase(""+total_amntIN,new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
					subcelltin1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tab7.addCell(subcelltin1);
					
					tab7.setWidthPercentage(new float[]{55,15,15,15},new Rectangle(100,100));
					PdfPCell subcelltin2 = new PdfPCell(new Phrase(""+total_arrIN,new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
					subcelltin2.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tab7.addCell(subcelltin2);
					
					tab7.setWidthPercentage(new float[]{55,15,15,15},new Rectangle(100,100));
					PdfPCell subcelltin3 = new PdfPCell(new Phrase(""+total_totIN,new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
					subcelltin3.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tab7.addCell(subcelltin3);
					
					
					
					tab8.setWidthPercentage(new float[]{35,20,30,15},new Rectangle(100,100));
					PdfPCell subcelltded = new PdfPCell(new Phrase("TOTAL ENCASH",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
					subcelltded.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tab8.addCell(subcelltded);
					
					tab8.setWidthPercentage(new float[]{35,20,30,15},new Rectangle(100,100));
					PdfPCell subcelltded1 = new PdfPCell(new Phrase(""+total_amntDED,new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
					subcelltded1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tab8.addCell(subcelltded1);
					
					tab8.setWidthPercentage(new float[]{35,20,30,15},new Rectangle(100,100));
					PdfPCell subcelltded2 = new PdfPCell(new Phrase(""+total_arrDED,new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
					subcelltded2.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tab8.addCell(subcelltded2);
					
					tab8.setWidthPercentage(new float[]{35,20,30,15},new Rectangle(100,100));
					PdfPCell subcelltded3 = new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
					subcelltded3.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tab8.addCell(subcelltded3);
				
					tab9.setWidthPercentage(new float[]{55,15,15,15},new Rectangle(100,100));
					PdfPCell subcellts = new PdfPCell(new Phrase("Net Salary",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
					subcellts.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tab9.addCell(subcellts);
					
					tab9.setWidthPercentage(new float[]{55,15,15,15},new Rectangle(100,100));
					PdfPCell subcellts1 = new PdfPCell(new Phrase(""+( total_totIN-total_totDED),new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
					subcellts1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tab9.addCell(subcellts1);
					
					tab9.setWidthPercentage(new float[]{55,15,15,15},new Rectangle(100,100));
					PdfPCell subcellts2 = new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
					subcellts2.setColspan(2);
					subcellts2.setHorizontalAlignment(Element.ALIGN_RIGHT);
					tab9.addCell(subcellts2);
					
					tab10.setWidthPercentage(new float[]{50,50},new Rectangle(100,100));
					PdfPCell subcellt = new PdfPCell(new Phrase("Net Salary In Words :- "+originalNumToLetter.getInWords(""+(int)(total_totIN - total_totDED)),new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
					subcellt.setHorizontalAlignment(Element.ALIGN_LEFT);
					subcellt.setColspan(2);
					tab10.addCell(subcellt);
				
				
				
				tab1.setWidthPercentage(new float[]{25,25,25,25},new Rectangle(100,100));
				PdfPCell subcell = new PdfPCell(new Phrase("Employee Number ",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);

				subcell = new PdfPCell(new Phrase(""+trbn.getEMPCODE(),new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				
				subcell = new PdfPCell(new Phrase("Employee Name ",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				
				
				subcell = new PdfPCell(new Phrase(""+trbn.getEmpname(),new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				
				subcell = new PdfPCell(new Phrase("Department ",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				
				
				subcell = new PdfPCell(new Phrase(""+new LookupHandler().getLKP_Desc("DEPT", trbn.getDept()),new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				
				subcell = new PdfPCell(new Phrase("Designation ",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				
				
				subcell = new PdfPCell(new Phrase(""+new LookupHandler().getLKP_Desc("DESIG", trbn.getDesg()),new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				
				subcell = new PdfPCell(new Phrase("Bank Name ",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				
				
				subcell = new PdfPCell(new Phrase(""+lkp.getLKP_Desc("BANK",rs3.getInt("BANK_NAME")),new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				
				subcell = new PdfPCell(new Phrase("A/No. ",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				
				
				subcell = new PdfPCell(new Phrase(""+trbn.getAccno(),new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				
				subcell = new PdfPCell(new Phrase("PF No ",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				
				
				subcell = new PdfPCell(new Phrase(""+trbn.getPfno(),new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				
				subcell = new PdfPCell(new Phrase("PAN No. ",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				
				
				subcell = new PdfPCell(new Phrase(""+trbn.getPanno(),new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				
				subcell = new PdfPCell(new Phrase("ESI No. ",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				
				
				subcell = new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
							
				String lesql="SELECT SUM(TOTDR) AS TOTDED,LEAVECD FROM LEAVEBAL WHERE BALDT BETWEEN '"+ReportDAO.BOM(slrydt)+"' AND '"+ReportDAO.EOM(slrydt)+"' AND LEAVECD=1 AND EMPNO="+rs3.getInt("EMPNO")+" GROUP BY LEAVECD";
				cl=0;
				pl=0;
				sl=0;
				ml=0;
				ResultSet rsleave = st1.executeQuery(lesql);
				while(rsleave.next())
				{
					String leave = new LookupHandler().getLKP_Desc("LEAVE",rsleave.getInt(2));
							
					if(leave.equalsIgnoreCase("PL")){
						pl = rsleave.getFloat("TOTDED");
					}
					
				}
				tab2.setWidthPercentage(new float[]{25,25,25,25},new Rectangle(100,100));
				PdfPCell subcell2 = new PdfPCell(new Phrase("Date Of Joining ",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				subcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab2.addCell(subcell2);

				subcell2 = new PdfPCell(new Phrase(""+trbn.getDOJ(),new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
				subcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab2.addCell(subcell2);
				
				subcell2 = new PdfPCell(new Phrase("Paid Leave ",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				subcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab2.addCell(subcell2);
				
				
				subcell2 = new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
				subcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab2.addCell(subcell2);
				
				subcell2 = new PdfPCell(new Phrase("Gross Amount / Rate (Day)",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				subcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab2.addCell(subcell2);
				
				
				subcell2 = new PdfPCell(new Phrase(""+gross,new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
				subcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab2.addCell(subcell2);
				
				
				
				
				subcell2 = new PdfPCell(new Phrase("Sick Leave",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				subcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab2.addCell(subcell2);
				
				subcell2 = new PdfPCell(new Phrase(""+sl,new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
				subcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab2.addCell(subcell2);
				
				

				
				subcell2 = new PdfPCell(new Phrase("Absent",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				subcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab2.addCell(subcell2);
				
				
				subcell2 = new PdfPCell(new Phrase(""+absent,new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
				subcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab2.addCell(subcell2);
				

				
				
				subcell2 = new PdfPCell(new Phrase("Priviledge Leave",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				subcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab2.addCell(subcell2);
				
				
				subcell2 = new PdfPCell(new Phrase(""+pl,new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
				subcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab2.addCell(subcell2);
				
				
				
				
				subcell2 = new PdfPCell(new Phrase("Monthly Gross Payable Amount",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				subcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab2.addCell(subcell2);
				
				
				subcell2 = new PdfPCell(new Phrase(""+monthygross,new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
				subcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab2.addCell(subcell2);
				
				
				subcell2 = new PdfPCell(new Phrase("Casual Leave",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				subcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab2.addCell(subcell2);
				
				subcell2 = new PdfPCell(new Phrase(""+cl,new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
				subcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab2.addCell(subcell2);
				
				
				subcell2 = new PdfPCell(new Phrase("Paid Days",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				subcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab2.addCell(subcell2);
				
				
				subcell2 = new PdfPCell(new Phrase(""+paiddays,new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
				subcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab2.addCell(subcell2);
				
				
				subcell2 = new PdfPCell(new Phrase("Maternity Leave",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				subcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab2.addCell(subcell2);
				
				subcell2 = new PdfPCell(new Phrase(""+ml,new Font(Font.TIMES_ROMAN,10,Font.NORMAL)));
				subcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab2.addCell(subcell2);
				
				
				tab1.addCell(subcell);
				cell.addElement(tab1);
				tab.addCell(cell);
				
				tab2.addCell(subcell2);
				cellt.addElement(tab2);
				tabt.addCell(cellt);
				
				
					
				
				//for loop for getting label and values for payslip income
				//for loop for getting label and values for payslip Deduction
				
				cell3.addElement(tab3);
				cell3.addElement(tab5);
				cell5.addElement(tab7);
				cell5.addElement(tab9);
				cell7.addElement(tab10);
				cell7.setColspan(2);
				
				
				cell4.addElement(tab4);
				cell4.addElement(tab6);
				cell6.addElement(tab8);
				
				
				
				tabsec.addCell(cell3);
				tabsec.addCell(cell4);
				tabsec.addCell(cell5);
				tabsec.addCell(cell6);
				tabsec.addCell(cell7);
			
				
				
					
				
				doc.add(tab);
				doc.add(tabt);
				doc.add(tabsec);
				
				doc.add(Chunk.NEWLINE);
				
				doc.add(Chunk.NEWLINE);
				doc.add(Chunk.NEWLINE);
				
				
				
				
				para = new Paragraph(new Phrase("For                                                                           ",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				para.setAlignment(Element.ALIGN_RIGHT);
				para.setSpacingBefore(40);
				doc.add(para);
				para = new Paragraph(new Phrase("NAMCO.                              ",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				para.setAlignment(Element.ALIGN_RIGHT);
				para.setSpacingAfter(0);
				doc.add(para);
				
				
				doc.add(Chunk.NEWLINE);
				doc.add(Chunk.NEWLINE);
				
				para = new Paragraph(new Phrase("                                            ",new Font(Font.TIMES_ROMAN,10)));
				para.setAlignment(Element.ALIGN_RIGHT);
				para.setSpacingAfter(0);
				doc.add(para);
				para = new Paragraph(new Phrase("Authorised Signture & Stamp                              ",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				para.setAlignment(Element.ALIGN_RIGHT);
				para.setSpacingAfter(0);
				doc.add(para);
			
				
				}
			doc.newPage();
			}
			doc.close();
			
			
			Cn.close();

			}
			catch(Exception e) 
			{
				e.printStackTrace();
				}
		
			
		
		}// ends here

	public boolean checkForRecordInSameYear (String empno) throws SQLException{
			boolean flag=true;
			Connection connection = ConnectionManager.getConnection();
		    EmpAttendanceHandler empAttendanceHandler=new EmpAttendanceHandler();
		    String dateString=empAttendanceHandler.getServerDate();
			try{
		String sql="SELECT * FROM LEAVE_ENCASHMENT WHERE EMPNO='"+empno+" '" +
				"and LEAVE_ENCASHMENT_DATE BETWEEN  '" +
				ReportDAO.BOYForJanToDec(dateString)+" 'AND '" +ReportDAO.EOYForJanToDec(dateString)+"' ";
				System.out.println(sql);
				Statement statement= connection.createStatement();
			ResultSet resultSet=statement.executeQuery(sql);
		
			if(resultSet.next())
			{
				
					flag=false;
				}
		
			connection.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				
				errorLog.errorLog("LeaveEncashmentHandler :ERROR CHECKING REPETITIVE RECORDS IN LEAVE_ENCASHMENT METHOD: checkForRecordInSameYear(). FOR PAGE: leaveEncashment.jsp", e.toString());
			}
			return flag;
			
		}
	
	//BY HEMANT TO SHOW DETAILS ON leaveEncashment.jsp
	public ArrayList<LeaveEncashmentBean> getEncashmentRecords (String empNo) {
			LeaveEncashmentBean  leaveEncashmentBean  =null;
           ArrayList<LeaveEncashmentBean> leaveEncashList=new ArrayList<LeaveEncashmentBean>();
			try
			{
			        
				Connection connection = ConnectionManager.getConnection();
				Statement statement=connection.createStatement();
				ResultSet resultSet = null;
		
				String query="SELECT EMPNO,LEAVE_ENCASHMENT_DATE,LEAVE_ENCASHMENT_SANCTION,ENCASHMENT_AMT from LEAVE_ENCASHMENT WHERE EMPNO='"+empNo+"' ";
				resultSet=statement.executeQuery(query);
				while(resultSet.next())
				{	
					leaveEncashmentBean=new LeaveEncashmentBean();
					leaveEncashmentBean.setEmpNo(resultSet.getInt("EMPNO"));
					leaveEncashmentBean.setLeaveEncashmentDate(resultSet.getString("LEAVE_ENCASHMENT_DATE"));
					leaveEncashmentBean.setLeaveEncashmentSanction(resultSet.getFloat("LEAVE_ENCASHMENT_SANCTION"));
					leaveEncashmentBean.setEncashmentAmt(resultSet.getFloat("ENCASHMENT_AMT"));
					leaveEncashList.add(leaveEncashmentBean);
		   		}
				
				connection.close();
			}
			catch(Exception e)
			{
				 errorLog.errorLog("LeaveEncashmentHandler: ERROR GETTING DATA FROM VARIOUS LEAVE_ENCASHMENT METHOD: getEncashmentRecords. FOR PAGE: leaveEncashment.jsp", e.toString());
			}
			return leaveEncashList;
		
		}
	
	
	// To get the  list of employee for Encashment details by Harshal
	public ArrayList<LeaveEncashmentBean> getEmpList(String prjCode, String date){
		//here date is extra parameter not in use..
		// trncd code is 999 for only testing purpose need to change as selected.
		//System.out.println("in Leave Handler @@@@@@@@@"+ prjCode);
		//System.out.println("in Leave Handler"+ date);
		
		
		
		//System.out.println("After Split"+ date);
		ArrayList<LeaveEncashmentBean> list= new ArrayList<LeaveEncashmentBean>();
		Connection con = null;
		ResultSet rs = null;	
		EmpAttendanceHandler EAH = new EmpAttendanceHandler();
	//String serverDate = EAH.getServerDate();
		System.out.println(date);
		int year = Integer.parseInt(date.substring(7,11));
		
		//System.out.println("Server Date is:"+ year);
		
		// replace 999 with prjCode value i.e '"+prjCode+"'
		String query = "SELECT E.EMPNO,E.EMPCODE,CONVERT(NVARCHAR(100),E.FNAME+' '+ E.LNAME) AS NAME,E.EMPCODE, l.days ,l.FRMDT"+
				" FROM EMPMAST E , LEAVETRAN l "+
				" where E.EMPNO=l.empno and l.TRNTYPE ='D' and l.LEAVEPURP= '4' and l.status ='1' and l.TRNDATE between '01-jan-"+(year)+"' and '31-dec-"+(year)+"' and l.EMPNO "+
				" in (SELECT E.EMPNO FROM EMPMAST E , EMPTRAN T "+
				" WHERE E.EMPNO = T.EMPNO AND T.PRJ_SRNO = '"+prjCode+"' "+
				" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO)  "+
				" AND (( E.STATUS='A' AND E.DOJ <= '31-dec-"+(year)+"') or (E.STATUS ='N' And  E.DOL>='01-jan-"+(year)+"' )) " +
				" and e.EMPNO = t.EMPNO )"+
				" ORDER BY E.EMPNO";
		System.out.println("query  "+query);
		// queryAll for all project 
		String queryAll = "SELECT E.EMPNO,E.EMPCODE,CONVERT(NVARCHAR(100),E.FNAME+' '+ E.LNAME) AS NAME,E.EMPCODE, l.days ,l.FRMDT"+
				" FROM EMPMAST E , LEAVETRAN l "+
				" where E.EMPNO=l.empno and l.TRNTYPE ='D' and l.LEAVEPURP= '4' and l.status ='1' and l.TRNDATE between '01-jan-"+year+"' and '31-dec-"+year+"' and l.EMPNO "+
				" in (SELECT E.EMPNO FROM EMPMAST E , EMPTRAN T "+
				" WHERE E.EMPNO = T.EMPNO "+
				" AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) " +
				" AND (( E.STATUS='A' AND E.DOJ <= '31-dec-"+(year)+"') or (E.STATUS ='N' And  E.DOL>='01-jan-"+(year)+"' )) " +
				" and e.EMPNO = t.EMPNO )"+
				" ORDER BY E.EMPNO";
		
	//	System.out.println("queryAll"+queryAll);
		
		con = ConnectionManager.getConnection();
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			Statement st = con.createStatement();
			
			// for all project use code 1000
			if(prjCode.equalsIgnoreCase("1000")){
				System.out.println("Query for all project"+queryAll);
				rs = st.executeQuery(queryAll);
			}
			else{
				System.out.println("Query for Single  project"+query);
				rs = st.executeQuery(query);
			}
			while(rs.next())
			{
				LeaveEncashmentBean lbean = new LeaveEncashmentBean();
				lbean.setEmpNo(rs.getInt("EMPNO"));
				lbean.setEmpCode(rs.getString("EMPCODE"));
				lbean.setEname(rs.getString("NAME"));
				lbean.setDays(rs.getInt("days"));
				lbean.setLeaveEncashmentDate(sdf.format( rs.getDate("FRMDT")));
				
			//	lbean.setEncashmentAmt();

				list.add(lbean);
			}
			rs.close();
			st.close();
			con.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return list;
	}
		
	// To get Encash Amount by Month by harshal
	public static ArrayList<Float> getEncashAmtByMonth(int empno, int days, String date)
		{
			
			Connection con = null;
			con = ConnectionManager.getConnection();
			ResultSet rs = null;	
			ArrayList<Float> list = new ArrayList<Float>();

			float  amount =0;
			float  basic =0;
			float  da =0;
			float  vda =0;
			//String newDate="1-mar-"+(Integer.parseInt(date.substring(7,11))-1);
			String newDate="1-dec-"+(Integer.parseInt(date.substring(7,11))-1);
			
			
		//	date = "01-"+date;
			/*String query = "select net_amt As NET_AMT from PAYTRAN_STAGE where empno= "+empno+"  and trndt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"'" +
					" And trncd in (101,102,103)  ";
			*/			
			
			String query =	"select sum(inp_amt) As NET_AMT, " +
							"(select inp_amt from PAYTRAN_STAGE where empno = "+empno+" and trndt between '"+ReportDAO.BOM(newDate)+"' and '"+ReportDAO.EOM(newDate)+"' And trncd =101) as BASIC ," +
							"(select inp_amt from PAYTRAN_STAGE where empno = "+empno+"  and trndt between '"+ReportDAO.BOM(newDate)+"' and '"+ReportDAO.EOM(newDate)+"' And trncd =102) as DA ," +
							"(select inp_amt from PAYTRAN_STAGE where empno = "+empno+"  and trndt between '"+ReportDAO.BOM(newDate)+"' and '"+ReportDAO.EOM(newDate)+"' And trncd =138) as VDA " +
							"from PAYTRAN_STAGE " +
							"where empno = "+empno+"  and trndt between '"+ReportDAO.BOM(newDate)+"' and '"+ReportDAO.EOM(newDate)+"' And trncd in (101,102,138)";
			
			System.out.println(" Test Date:-"+newDate);	
			System.out.println(" Test SQL Query:-"+query);						
			try
			{
				Statement st = con.createStatement();
				rs = st.executeQuery(query);
				while(rs.next())
				{
					
					amount =rs.getFloat("NET_AMT");
					
					//System.out.println("amount is :"+ amount);
					basic =rs.getFloat("BASIC");
					da =rs.getFloat("DA");
					vda =rs.getFloat("VDA");
				
				}
				rs.close();
				st.close();
				con.close();	
				
			float monthdays = Calculate.getDays(date);	
			
			if(amount !=0){
				amount = amount/30; 			
				amount =Math.round(amount*days);
			    
				list.add(basic);
				list.add(da);
				list.add(vda);	
				list.add(amount);
			
			}
			
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			return list;
			
		}
		
	//To Encash Employee amount in paytran... for 145 By harshal
	// 
	public boolean encashEmpList(LeaveEncashmentBean leaveEncashmentBean,int loggedEmployeeNo) {
				
		System.out.println("In Leave Encashment..02");
			boolean result = false;
		    boolean first;
			Connection connection = ConnectionManager.getConnection();
			EmpAttendanceHandler EAH = new EmpAttendanceHandler();
			String srvDate = leaveEncashmentBean.getLeaveEncashmentDate();
			String year = srvDate.substring(7,11);
			float inputAmount=0.0f;
			float calculatedAmount=0.0f;
			float netAmount=0.0f;
			try
			{	
				//System.out.println("In Leave Encashment..03");
				//insert encash record to Encashment table for histroy
				String EncashQuery ="insert into encashment (empno,basic,DA,vda, days , amount ," +
						"encashpf_3_67,encashpf_8_33,encashpf,encashnet," +
						"status,encashdate,currentdate ,user_login) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				//System.out.println("insertQuery"+EncashQuery);
				inputAmount=Math.round(leaveEncashmentBean.getEncashmentAmt());
				//calculatedAmount=Math.round(leaveEncashmentBean.getEncashmentAmt()*12/100);
				calculatedAmount=0;
				netAmount=inputAmount-calculatedAmount;
				
				PreparedStatement insertSt1 = connection.prepareStatement(EncashQuery);
				insertSt1.setInt(1,leaveEncashmentBean.getEmpNo());
				//System.out.println("EmpNo:-"+leaveEncashmentBean.getEmpNo());
				insertSt1.setFloat(2,leaveEncashmentBean.getBasic());
				//System.out.println("Basic:-"+leaveEncashmentBean.getBasic());
				insertSt1.setFloat(3, leaveEncashmentBean.getDa());
				//System.out.println("DA:-"+leaveEncashmentBean.getDa());
				insertSt1.setFloat(4, leaveEncashmentBean.getVda());
				//System.out.println("VDA:-"+leaveEncashmentBean.getVda());
				insertSt1.setFloat(5, leaveEncashmentBean.getDays());
				//System.out.println("DAYS:-"+leaveEncashmentBean.getDays());
				insertSt1.setFloat(6, inputAmount);
				//System.out.println("inputAmount:-"+inputAmount);
				//insertSt1.setFloat(7, Math.round(inputAmount*3.67/100));
				insertSt1.setFloat(7, 0);
				//System.out.println("encashpf_3_67:-"+Math.round(inputAmount*3.67/100));
				//insertSt1.setFloat(8, (calculatedAmount-Math.round(inputAmount*3.67/100)));
				insertSt1.setFloat(8, 0);
				//System.out.println("encashpf_8_33:-"+ (calculatedAmount-Math.round(inputAmount*3.67/100)));
				insertSt1.setFloat(9, calculatedAmount);
				//System.out.println("calculatedAmount:-"+calculatedAmount);
				insertSt1.setFloat(10,netAmount);
				//System.out.println("netAmount:-"+netAmount);
				insertSt1.setString(11,"P");
				insertSt1.setString(12, leaveEncashmentBean.getLeaveEncashmentDate());
				//System.out.println("encashdate:-"+leaveEncashmentBean.getLeaveEncashmentDate());
				insertSt1.setString(13, srvDate);
				//System.out.println("srvDate:-"+srvDate);
				insertSt1.setInt(14, loggedEmployeeNo);
				//System.out.println("loggedEmployeeNo:-"+loggedEmployeeNo);
				
				insertSt1.executeUpdate();
				
				
		/*		String EncashQuery ="insert into encashment (empno,basic,DA,vda, days , amount ," +
									"encashdate,currentdate ,user_login) values (?,?,?,?,?,?,?,?,?)";
				System.out.println("insertQuery"+EncashQuery);
				inputAmount=Math.round(leaveEncashmentBean.getEncashmentAmt());
				calculatedAmount=Math.round(leaveEncashmentBean.getEncashmentAmt()*12/100);
				netAmount=inputAmount-calculatedAmount;
				
				PreparedStatement insertSt1 = connection.prepareStatement(EncashQuery);
				insertSt1.setInt(1,leaveEncashmentBean.getEmpNo());
				System.out.println("EmpNo:-"+leaveEncashmentBean.getEmpNo());
				insertSt1.setFloat(2,leaveEncashmentBean.getBasic());
				System.out.println("Basic:-"+leaveEncashmentBean.getBasic());
				insertSt1.setFloat(3, leaveEncashmentBean.getDa());
				System.out.println("DA:-"+leaveEncashmentBean.getDa());
				insertSt1.setFloat(4, leaveEncashmentBean.getVda());
				System.out.println("VDA:-"+leaveEncashmentBean.getVda());
				insertSt1.setFloat(5, leaveEncashmentBean.getDays());
				System.out.println("DAYS:-"+leaveEncashmentBean.getDays());
				insertSt1.setFloat(6, inputAmount);
				System.out.println("inputAmount:-"+inputAmount);
				insertSt1.setFloat(7, Math.round(inputAmount*3.67/100));
				System.out.println("encashpf_3_67:-"+Math.round(inputAmount*3.67/100));
				insertSt1.setFloat(8, (calculatedAmount-Math.round(inputAmount*3.67/100)));
				System.out.println("encashpf_8_33:-"+ (calculatedAmount-Math.round(inputAmount*3.67/100)));
				insertSt1.setFloat(9, calculatedAmount);
				System.out.println("calculatedAmount:-"+calculatedAmount);
				insertSt1.setFloat(10,netAmount);
				System.out.println("netAmount:-"+netAmount);
				insertSt1.setString(11,"P");
				insertSt1.setString(7, leaveEncashmentBean.getLeaveEncashmentDate());
				System.out.println("encashdate:-"+leaveEncashmentBean.getLeaveEncashmentDate());
				insertSt1.setString(8, srvDate);
				System.out.println("srvDate:-"+srvDate);
				insertSt1.setInt(9, loggedEmployeeNo);
				System.out.println("loggedEmployeeNo:-"+loggedEmployeeNo);
				
				insertSt1.executeUpdate();
				*/
				
				
				
				//insert Into Paytran for encash amt by code 145
				
				
				String insertQuery = "INSERT INTO PAYTRAN_stage (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,USRCODE,UPDDT,STATUS) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
				System.out.println("insertQuery"+insertQuery);
				
				PreparedStatement insertSt = connection.prepareStatement(insertQuery);
			//	insertSt.setString(1,ReportDAO.EOM("01-jan-"+year));
				insertSt.setString(1,ReportDAO.EOM(leaveEncashmentBean.getLeaveEncashmentDate()));
				insertSt.setInt(2,leaveEncashmentBean.getEmpNo()); 
				insertSt.setInt(3,145); // transaction code for leave Encashment
				insertSt.setInt(4,0);
				insertSt.setFloat(5,inputAmount);			
				insertSt.setFloat(6,calculatedAmount);
				insertSt.setFloat(7,0);				
				insertSt.setFloat(8,0);	
				insertSt.setFloat (9,netAmount);
				insertSt.setString (10,"");		
				insertSt.setInt (11,loggedEmployeeNo );
				insertSt.setString (12,srvDate);
				insertSt.setString (13,"F");	
				insertSt.executeUpdate();
				
				 //leave encash PF  (Leave Encash PF - trncd = 259)
				insertQuery = "INSERT INTO PAYTRAN_stage (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,USRCODE,UPDDT,STATUS) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
				System.out.println("insertQuery"+insertQuery);
				
				 insertSt = connection.prepareStatement(insertQuery);
			//	insertSt.setString(1,ReportDAO.EOM("01-jan-"+year));
				insertSt.setString(1,ReportDAO.EOM(leaveEncashmentBean.getLeaveEncashmentDate()));
				insertSt.setInt(2,leaveEncashmentBean.getEmpNo()); 
				insertSt.setInt(3,259); // transaction code for leave Encashment
				insertSt.setInt(4,0);
				insertSt.setFloat(5,calculatedAmount);			
				insertSt.setFloat(6,0);
				insertSt.setFloat(7,0);				
				insertSt.setFloat(8,0);	
				insertSt.setFloat (9, calculatedAmount);
				insertSt.setString (10,"");		
				insertSt.setInt (11,loggedEmployeeNo );
				insertSt.setString (12,srvDate);
				insertSt.setString (13,"F");	
				insertSt.executeUpdate();
				
				
				
				//update status in after encash(insert) into paytran Leavtran 
				String updatequery = "update LEAVETRAN set STATUS = 'ENCASHED' " +
							   		 "where EMPNO = "+leaveEncashmentBean.getEmpNo()+" and STATUS = '1' and TRNTYPE ='D' and LEAVEPURP= '4' and " +
							         "TRNDATE between '1-jan-"+year+"' and '31-jan-"+year+"' ";
				System.out.println("updatequery"+updatequery);
				
				PreparedStatement updateSt = connection.prepareStatement(updatequery);
				updateSt.executeUpdate();
				
				first = true;
				
				insertSt.close();
				updateSt.close();
				connection.close();
				
				if(first){
					result = true;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				ErrorLog errorLog=new ErrorLog();
				errorLog.errorLog("LeaveEncashmentHandler: ERROR INSERTING IN paytran METHOD: encashEmpList. FOR PAGE: Encashmentlist.jsp", e.toString());
			}
			return result;
			
		}

	
		
	
	
	
	//BY Harshal TO SHOW DETAILS ON leaveEncashment.jsp
	public LeaveEncashmentBean getEmployeeInfoNew(String empNo) {
		
		LeaveEncashmentBean  leaveEncashmentBean  = new LeaveEncashmentBean();
		try
		{
			Connection connection = ConnectionManager.getConnection();
			connection= ConnectionManager.getConnection();
			Statement statement=connection.createStatement();
			
			ResultSet resultSet = null;
			
			//cal_amt based on DA, BASIC, VDA 
			String qurey = "select  sum(p.cal_amt) as cal_amt ,replace(right(convert(varchar, l.FRMDT, 106), 11), ' ', '-') AS FRMDT, l.days,  lm.MAXCUMLIM " +
							"from   paytran p, LEAVETRAN l, LEAVEMASS lm " +
							"where p.TRNCD In ( 101,102,138) and p.EMPNO ="+empNo+" and l.EMPNO = "+empNo+" and l.TRNTYPE = 'EC' " +
							" and l.FRMDT between '"+ReportDAO.BOYForJanToDec(ReportDAO.getSysDate())+"' and  '"+ReportDAO.EOYForJanToDec(ReportDAO.getSysDate())+"' " +
							"and lm.LEAVECD=1 " +
							"group by l.FRMDT, l.days, lm.MAXCUMLIM";
			
			System.out.println(qurey);
			resultSet = statement.executeQuery(qurey);
			String  FRMDT="";
			
			while(resultSet.next())
			{				
				 FRMDT = resultSet.getString("FRMDT") == null ? "": resultSet.getString("FRMDT") ;

				 leaveEncashmentBean.setMonthlyGross(resultSet.getFloat("CAL_AMT"));
				 leaveEncashmentBean.setMaxLimit(resultSet.getFloat("MAXCUMLIM"));
				 leaveEncashmentBean.setLeaveBal(resultSet.getFloat("days"));
	   		}
			
				leaveEncashmentBean.setFromDate(FRMDT);
			
			connection.close();
		}
		catch(Exception e){e.printStackTrace();}
		
		return leaveEncashmentBean;
	
	}

	 //BY HEMANT TO SHOW DETAILS ON leaveEncashment.jsp
		public LeaveEncashmentBean getEmployeeInfo(String empNo) {
			LeaveEncashmentBean  leaveEncashmentBean  = new LeaveEncashmentBean();

			try
			{
			        
				Connection connection = ConnectionManager.getConnection();
				connection= ConnectionManager.getConnection();
				Statement statement=connection.createStatement();
				ResultSet resultSet = null;
		
				
				resultSet=statement.executeQuery("SELECT BAL FROM LEAVEBAL WHERE EMPNO='"+empNo+"' AND BALDT=(SELECT MAX(BALDT) FROM LEAVEBAL WHERE EMPNO='"+empNo+"' ) ");
				while(resultSet.next())
				{	
				leaveEncashmentBean.setLeaveBal(resultSet.getFloat("BAL"));
		   		}
				resultSet=statement.executeQuery("SELECT CAL_AMT FROM PAYTRAN WHERE TRNCD=199 AND  EMPNO="+empNo+"  ");
				while(resultSet.next())
				{				
				leaveEncashmentBean.setMonthlyGross(resultSet.getFloat("CAL_AMT"));
		   		}
				resultSet=statement.executeQuery("SELECT CAL_AMT FROM PAYTRAN WHERE TRNCD=221 AND  EMPNO="+empNo+"  ");
				while(resultSet.next())
				{				
				leaveEncashmentBean.setEsicAmt(resultSet.getFloat("CAL_AMT"));
		   		}
				//MAX LIMIT
				resultSet=statement.executeQuery("select MAXCUMLIM from LEAVEMASS where LEAVECD=1");
				while(resultSet.next())
				{				
				leaveEncashmentBean.setMaxLimit(resultSet.getFloat("MAXCUMLIM"));
		   		}
				//TODAYS LEAVE BALANCE
				resultSet=statement.executeQuery("select BAL from LEAVEBAL where  EMPNO="+empNo+"  and LEAVECD=1 and " +
						"BALDT=(select max(BALdt) from LEAVEBAL where  EMPNO="+empNo+"  and LEAVECD=1 )  ");
				while(resultSet.next())
				{				
				leaveEncashmentBean.setLeaveBal(resultSet.getFloat("BAL"));
		   		}
				connection.close();
			}
			catch(Exception e)
			{
				 errorLog.errorLog("LeaveEncashmentHandler: ERROR GETTING DATA FROM VARIOUS TABLES METHOD: getEmployeeInfo. FOR PAGE: leaveEncashment.jsp", e.toString());
			}
			return leaveEncashmentBean;
		
		}
		


}
