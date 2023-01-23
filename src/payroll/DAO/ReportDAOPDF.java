package payroll.DAO;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.jfree.ui.Align;

import payroll.Core.ReportDAO;
import payroll.Model.RepoartBean;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
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

public class ReportDAOPDF {
	static String lable1="";
	static String label2="";
	static String imagepath="";
	static String date="";
	
	public class Footer extends PdfPageEventHelper {
		
		protected Phrase footer;
		protected Phrase header;
		private  Font footerFont = new Font(Font.TIMES_ROMAN, 12, Font.BOLD);
		
		PdfTemplate total;

		public void onOpenDocument(PdfWriter writer, Document document) {
			total = writer.getDirectContent().createTemplate(20, 30);
		}
		
		public Footer(String lbl)
		{
			ReportDAOPDF.lable1=lbl;
		}
		
		
		public void onStartPage(PdfWriter writer, Document doc){
			try
		    {	
				int pageNumber = writer.getPageNumber();
				if(pageNumber ==1){
					Font fbold = new Font(Font.TIMES_ROMAN,12,Font.BOLD);
					Font fsmall = new Font(Font.TIMES_ROMAN,10);
					Font FONT = new Font(Font.TIMES_ROMAN, 40, Font.NORMAL, new GrayColor(0.75f));
					Font  fontcolor = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(6, 8, 193));
					
			      
					Properties prop = new Properties();
				    try
				    {			
							ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
						    InputStream stream = classLoader.getResourceAsStream("constant.properties");
						    prop.load(stream);
					}
				    catch (Exception e)  {System.out.println("Error in constant properties Manager "+e);}
					
					
					Phrase title = new Phrase(prop.getProperty("bankName2"),fbold);
					Paragraph para = new Paragraph(title);
					para.setAlignment(Element.ALIGN_CENTER);
					doc.add(para);
					
					para = new Paragraph(new Phrase(prop.getProperty("bankName3"),fsmall));
					para.setAlignment(Element.ALIGN_CENTER);
					doc.add(para);
		
					para = new Paragraph(new Phrase(prop.getProperty("addressForReport"),fsmall));
					para.setAlignment(Element.ALIGN_CENTER);
					doc.add(para);
					
					para = new Paragraph(new Phrase(prop.getProperty("contactForReport"),fsmall));
					para.setAlignment(Element.ALIGN_CENTER);
					doc.add(para);
					
					para = new Paragraph(new Phrase(prop.getProperty("mailForReport"),fontcolor));
					para.setAlignment(Element.ALIGN_CENTER);
					doc.add(para);
					
				}
		    }
		    catch (Exception e)  {e.printStackTrace();}
			
			
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
				table.addCell(new Phrase(lable1+"( "+ReportDAO.getSysDate()+"   "+String.format("Page %d ", writer.getPageNumber())+"   )"    ,footerFont));
				table.addCell(new Phrase(String.format("Page %d ", writer.getPageNumber()),footerFont));
				PdfPCell cell = new PdfPCell(Image.getInstance(total));
				cell.setBorder(0);
				cell.setHorizontalAlignment(Align.LEFT);
				//cell.setFixedHeight(72f);
				table.addCell(cell);
				table.writeSelectedRows(0, -1, 2, 35, writer.getDirectContent());
			} 
			catch(DocumentException de){
				throw new ExceptionConverter(de);
			}

		}

		public void onCloseDocument(PdfWriter writer, Document document) {
			ColumnText.showTextAligned(total, Element.ALIGN_BOTTOM, new Phrase(String.valueOf(writer.getPageNumber()-1),footerFont),15,20,0);
		}

	}

	public static void EmployeeList(String date, String type, String filePath, String imagepath){
		System.out.println("I am in EmployeeList " + type);
	}
	
	public static void AgeWiseEmployee(String date, String type, String filePath, String imagepath,String from_age,String to_age,String desig_type,String desig_value1,String desig_value2,String branch_type,String branch_value1,String branch_value2){
		
		System.out.println("I am in AgeWiseEmployee " + type);
		RepoartBean repBean  = new RepoartBean();
		LookupHandler lkhp = new LookupHandler();
		try
		{
			Connection Cn = null;
			Statement st = null;
			ResultSet rs = null;

			ReportDAO.OpenCon("", "", "",repBean);
			Cn = repBean.getCn();
		
			String Query = "";
			int totalEmployee = 0;
			int Employee = 0;
			
			Document doc = new Document();
			PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filePath));

			
			ReportDAOPDF dao = new ReportDAOPDF();
			ReportDAOPDF.lable1="Date : ";
			Footer ftr = dao.new Footer(lable1);
			writer.setPageEvent(ftr);
			doc.open();
			doc.setPageSize(PageSize.A4);
			
			Font fbold = new Font(Font.TIMES_ROMAN,12,Font.BOLD);
			Font fsmall = new Font(Font.TIMES_ROMAN,10);
			Font FONT = new Font(Font.TIMES_ROMAN, 40, Font.NORMAL, new GrayColor(0.75f));
			Font  fontcolor = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(6, 8, 193));
			
			ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Business Bank",FONT), 297.5f, 421, 45);
			
			Image image1 = Image.getInstance(imagepath);
			image1.scaleAbsolute(80f, 80f);
			image1.setAbsolutePosition(10f, 740f);
			doc.add(image1);
			
		

			Paragraph para = new Paragraph(new Phrase("Age Wise Employee List For :-" + date.substring(3, 11),fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			doc.add(para);
			Paragraph para1 = new Paragraph(new Phrase("",fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			doc.add(para1);
			
			Rectangle rec = new  Rectangle(128,100);
			PdfPTable table = null;

			table= new PdfPTable(7);	
			table.setSpacingBefore(10);
			table.setWidthPercentage(new float[]{10,13,50,15,10,15,15}, rec);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			if(desig_type.equalsIgnoreCase("all")&&branch_type.equalsIgnoreCase("all"))
			{
				Query = " select emp.empcode,emp.salute,rtrim(emp.fname)+' '+rtrim(emp.mname)+' '+rtrim(emp.lname) " +
						" name , l.lkp_disc as desig,p.SITE_NAME as site,EMP.DOB,EMP.DOJ,DATEDIFF(YEAR,EMP.DOB,GETDATE()) as Age from empmast emp,emptran et,lookup l,Project_Sites p " +
						" where emp.empno=et.empno and emp.status = 'A' and l.LKP_SRNO=et.desig and lkp_code='DESIG' and  et.srno=(Select MAX(srno) from EMPTRAN e where e.EMPNO=et.EMPNO) and p.SITE_ID=et.PRJ_SRNO and DATEDIFF(YEAR,EMP.DOB,GETDATE()) between '"+from_age+"' and '"+to_age+"' order by age desc,emp.empcode ";
			}
			else if(desig_type.equalsIgnoreCase("all")&&branch_type.equalsIgnoreCase("specific"))
			{
				Query = "select emp.empcode,emp.salute, "+
							"rtrim(emp.fname)+' '+rtrim(emp.mname)+' '+rtrim(emp.lname)  name , l.lkp_disc as desig,p.SITE_NAME as site, "+
							"EMP.DOB,EMP.DOJ,DATEDIFF(YEAR,EMP.DOB,GETDATE()) as Age  "+
							"from empmast emp,EMPTRAN et ,lookup l,Project_Sites p where emp.empno=et.empno and emp.status = 'A' and l.LKP_SRNO=et.desig and lkp_code='DESIG' and p.SITE_ID=et.PRJ_SRNO and  "+ 
							"et.srno=(Select MAX(srno) from EMPTRAN e where e.EMPNO=et.EMPNO) and et.PRJ_SRNO='"+branch_value1+"' and  "+
							"DATEDIFF(YEAR,EMP.DOB,GETDATE()) between '"+from_age+"' and '"+to_age+"'   "+
							"order by age desc,emp.empcode";
			}
			else if(desig_type.equalsIgnoreCase("all")&&branch_type.equalsIgnoreCase("rangewise"))
			{
				Query = "select emp.empcode,emp.salute, "+
						"rtrim(emp.fname)+' '+rtrim(emp.mname)+' '+rtrim(emp.lname)  name , l.lkp_disc as desig,p.SITE_NAME as site, "+
						"EMP.DOB,EMP.DOJ,DATEDIFF(YEAR,EMP.DOB,GETDATE()) as Age  "+
						"from empmast emp,EMPTRAN et ,lookup l,Project_Sites p where emp.empno=et.empno and emp.status = 'A' and l.LKP_SRNO=et.desig and lkp_code='DESIG' and p.SITE_ID=et.PRJ_SRNO and  "+ 
						"et.srno=(Select MAX(srno) from EMPTRAN e where e.EMPNO=et.EMPNO) and et.PRJ_SRNO between '"+branch_value1+"' and '"+branch_value2+"' and  "+
						"DATEDIFF(YEAR,EMP.DOB,GETDATE()) between '"+from_age+"' and '"+to_age+"'   "+
						"order by age desc,emp.empcode";
			}
			else if(desig_type.equalsIgnoreCase("specific")&&branch_type.equalsIgnoreCase("all"))
			{
				Query = "select emp.empcode,emp.salute, "+
						"rtrim(emp.fname)+' '+rtrim(emp.mname)+' '+rtrim(emp.lname)  name , l.lkp_disc as desig,p.SITE_NAME as site, "+
						"EMP.DOB,EMP.DOJ,DATEDIFF(YEAR,EMP.DOB,GETDATE()) as Age  "+
						"from empmast emp,EMPTRAN et ,lookup l,Project_Sites p where emp.empno=et.empno and emp.status = 'A' and l.LKP_SRNO=et.desig and lkp_code='DESIG' and p.SITE_ID=et.PRJ_SRNO and  "+ 
						"et.srno=(Select MAX(srno) from EMPTRAN e where e.EMPNO=et.EMPNO) and et.desig='"+desig_value1+"' and  "+
						"DATEDIFF(YEAR,EMP.DOB,GETDATE()) between '"+from_age+"' and '"+to_age+"'   "+
						"order by age desc,emp.empcode";
			}
			else if(desig_type.equalsIgnoreCase("specific")&&branch_type.equalsIgnoreCase("specific"))
			{
				Query = "select emp.empcode,emp.salute, "+
						"rtrim(emp.fname)+' '+rtrim(emp.mname)+' '+rtrim(emp.lname)  name , l.lkp_disc as desig,p.SITE_NAME as site, "+
						"EMP.DOB,EMP.DOJ,DATEDIFF(YEAR,EMP.DOB,GETDATE()) as Age  "+
						"from empmast emp,EMPTRAN et ,lookup l,Project_Sites p where emp.empno=et.empno and emp.status = 'A' and l.LKP_SRNO=et.desig and lkp_code='DESIG' and p.SITE_ID=et.PRJ_SRNO and  "+ 
						"et.srno=(Select MAX(srno) from EMPTRAN e where e.EMPNO=et.EMPNO) and et.desig='"+desig_value1+"' and et.PRJ_SRNO = '"+branch_value1+"'  and  "+
						"DATEDIFF(YEAR,EMP.DOB,GETDATE()) between '"+from_age+"' and '"+to_age+"'   "+
						"order by age desc,emp.empcode";
			}
			else if(desig_type.equalsIgnoreCase("specific")&&branch_type.equalsIgnoreCase("rangewise"))
			{
				Query = "select emp.empcode,emp.salute, "+
						"rtrim(emp.fname)+' '+rtrim(emp.mname)+' '+rtrim(emp.lname)  name , l.lkp_disc as desig,p.SITE_NAME as site, "+
						"EMP.DOB,EMP.DOJ,DATEDIFF(YEAR,EMP.DOB,GETDATE()) as Age  "+
						"from empmast emp,EMPTRAN et ,lookup l,Project_Sites p where emp.empno=et.empno and emp.status = 'A' and l.LKP_SRNO=et.desig and lkp_code='DESIG' and p.SITE_ID=et.PRJ_SRNO and  "+ 
						"et.srno=(Select MAX(srno) from EMPTRAN e where e.EMPNO=et.EMPNO) and et.desig='"+desig_value1+"' and et.PRJ_SRNO between '"+branch_value1+"' and '"+branch_value2+"' and  "+
						"DATEDIFF(YEAR,EMP.DOB,GETDATE()) between '"+from_age+"' and '"+to_age+"'   "+
						"order by age desc,emp.empcode";
			}
			else if(desig_type.equalsIgnoreCase("rangewise")&&branch_type.equalsIgnoreCase("all"))
			{
				Query = "select emp.empcode,emp.salute, "+
						"rtrim(emp.fname)+' '+rtrim(emp.mname)+' '+rtrim(emp.lname)  name , l.lkp_disc as desig,p.SITE_NAME as site, "+
						"EMP.DOB,EMP.DOJ,DATEDIFF(YEAR,EMP.DOB,GETDATE()) as Age  "+
						"from empmast emp,EMPTRAN et ,lookup l,Project_Sites p where emp.empno=et.empno and emp.status = 'A' and l.LKP_SRNO=et.desig and lkp_code='DESIG' and p.SITE_ID=et.PRJ_SRNO and  "+ 
						"et.srno=(Select MAX(srno) from EMPTRAN e where e.EMPNO=et.EMPNO) and et.desig between '"+desig_value1+"' and  '"+desig_value2+"' and  "+
						"DATEDIFF(YEAR,EMP.DOB,GETDATE()) between '"+from_age+"' and '"+to_age+"'   "+
						"order by age desc,emp.empcode";
			}
			else if(desig_type.equalsIgnoreCase("rangewise")&&branch_type.equalsIgnoreCase("specific"))
			{
				Query = "select emp.empcode,emp.salute, "+
						"rtrim(emp.fname)+' '+rtrim(emp.mname)+' '+rtrim(emp.lname)  name , l.lkp_disc as desig,p.SITE_NAME as site, "+
						"EMP.DOB,EMP.DOJ,DATEDIFF(YEAR,EMP.DOB,GETDATE()) as Age  "+
						"from empmast emp,EMPTRAN et ,lookup l,Project_Sites p where emp.empno=et.empno and emp.status = 'A' and l.LKP_SRNO=et.desig and lkp_code='DESIG' and p.SITE_ID=et.PRJ_SRNO and  "+ 
						"et.srno=(Select MAX(srno) from EMPTRAN e where e.EMPNO=et.EMPNO) and et.desig between '"+desig_value1+"' and  '"+desig_value2+"' and et.PRJ_SRNO = '"+branch_value1+"'  and  "+
						"DATEDIFF(YEAR,EMP.DOB,GETDATE()) between '"+from_age+"' and '"+to_age+"'   "+
						"order by age desc,emp.empcode";
			}
			else if(desig_type.equalsIgnoreCase("rangewise")&&branch_type.equalsIgnoreCase("rangewise"))
			{
				Query = "select emp.empcode,emp.salute, "+
						"rtrim(emp.fname)+' '+rtrim(emp.mname)+' '+rtrim(emp.lname)  name , l.lkp_disc as desig,p.SITE_NAME as site, "+
						"EMP.DOB,EMP.DOJ,DATEDIFF(YEAR,EMP.DOB,GETDATE()) as Age  "+
						"from empmast emp,EMPTRAN et ,lookup l,Project_Sites p where emp.empno=et.empno and emp.status = 'A' and l.LKP_SRNO=et.desig and lkp_code='DESIG' and p.SITE_ID=et.PRJ_SRNO and  "+ 
						"et.srno=(Select MAX(srno) from EMPTRAN e where e.EMPNO=et.EMPNO) and et.desig between '"+desig_value1+"' and  '"+desig_value2+"' and et.PRJ_SRNO between '"+branch_value1+"' and '"+branch_value2+"' and  "+
						"DATEDIFF(YEAR,EMP.DOB,GETDATE()) between '"+from_age+"' and '"+to_age+"'   "+
						"order by age desc,emp.empcode";
			}
			/*Query = " select emp.empcode,emp.salute,rtrim(emp.fname)+' '+rtrim(emp.mname)+' '+rtrim(emp.lname) " +
					" name ,EMP.DOB,EMP.DOJ,DATEDIFF(YEAR,EMP.DOB,GETDATE()) as Age from empmast emp" +
					" where emp.status = 'A' order by age desc,emp.empcode ";*/
			
			System.out.println("hi"+Query);
			st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(Query);
			
			if(rs.next())
			{			
				
				/*rs.beforeFirst();
				while(rs.next())
				{
					String Age = rs.getString("Age");*/
					
					/*para = new Paragraph(new Phrase("Employee's List for Age : "+rs.getString("Age"),fbold));
					para.setAlignment(Element.ALIGN_CENTER);
					para.setSpacingBefore(10);
					para.setSpacingAfter(5);
					doc.add(para);*/
					
					//............................ Code To print Heading start here...................................
					PdfPTable main = new PdfPTable(7);
					//main.setSpacingBefore(10);
					
					String str[] = new String[7];
					
					str[0]="SRNO";				str[1]="EMPCODE";						str[2]="EMPLOYEE NAME";			str[3]="DESIG";
					str[4]="AGE";					str[5]="DOB";							str[6]="BRANCH";		
					
					main.setWidthPercentage(new float[]{10,18,40,15,15,15,15f}, rec);
					main.setHorizontalAlignment(Element.ALIGN_CENTER);
					main.setSpacingBefore(10);
					PdfPCell maincell ;	
					
					for(int i=0; i<str.length;i++ )
					{					
						maincell = new PdfPCell(new Phrase(str[i],fbold));
						maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
						main.addCell(maincell);
					}	
					doc.add(main);
					//......................... Code To print Heading Ends here......................................
					
					//......................... Code To print DATA Starts here......................................
					rs.beforeFirst();
					int srno=1;
					while(rs.next())
					{
					//while (Age.equals(rs.getString("Age")))
					//{					
						main = new PdfPTable(7);
						main.setWidthPercentage(new float[]{10,18,40,15,15,15,15f}, rec);
						main.setHorizontalAlignment(Element.ALIGN_CENTER); 
						
						PdfPCell cell11 = new PdfPCell(new Phrase(""+srno,fsmall)); 
						main.setHorizontalAlignment(Element.ALIGN_CENTER); 
						main.addCell(cell11);
						
						PdfPCell cell1 = new PdfPCell(new Phrase(""+rs.getString("EMPCODE"),fsmall)); 
						main.setHorizontalAlignment(Element.ALIGN_CENTER); 
						main.addCell(cell1);
						
						PdfPCell cell2 = new PdfPCell(new Phrase(""+lkhp.getLKP_Desc("SALUTE", rs.getInt("SALUTE"))+" "+rs.getString("name"),fsmall));
						main.setHorizontalAlignment(Element.ALIGN_LEFT); 
						main.addCell(cell2);
						
						PdfPCell cell22 = new PdfPCell(new Phrase(""+rs.getString("desig"),fsmall));
						main.setHorizontalAlignment(Element.ALIGN_LEFT); 
						main.addCell(cell22);
						
						PdfPCell cell3 = new PdfPCell(new Phrase(""+rs.getString("Age"),fsmall));
						main.setHorizontalAlignment(Element.ALIGN_CENTER); 
						main.addCell(cell3);
						
						PdfPCell cell4 = new PdfPCell(new Phrase(""+EmpOffHandler.dateFormat(rs.getDate("DOB")) ,fsmall));
						main.setHorizontalAlignment(Element.ALIGN_CENTER); 
						main.addCell(cell4);
						
						PdfPCell cell5 = new PdfPCell(new Phrase(""+rs.getString("site"),fsmall));
						main.setHorizontalAlignment(Element.ALIGN_CENTER); 
						main.addCell(cell5);

						doc.add(main);
				
						
						/*if(!Age.equals(rs.getString("Age"))){
							rs.previous();
							Employee+=1;	
							break;
						}*/
						
						
					//}
					
					//......................... Code To print DATA Starts here......................................
					/*para = new Paragraph(new Phrase("No Of Employee :- "+Employee,fsmall));
					para.setAlignment(Element.ALIGN_LEFT);
					para.setSpacingAfter(10);
					doc.add(para);*/
					
					totalEmployee ++;
					//Employee = 0;
					srno++;
					if (!rs.next()) {
						//Employee+=1;	
						break;
					}else
					{
						rs.previous();
						continue;
					}
				}
				para = new Paragraph(new Phrase("Total No Of Employee :- "+totalEmployee,fbold));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(10);
				doc.add(para);
			}
			else
			{
				System.out.println("no record found");
				PdfPCell c1 = new PdfPCell(new Phrase("Record is Not found for the Month "+date.substring(3),fsmall));  
				c1.setColspan(5);
				table.setSpacingBefore(40);
				table.addCell(c1);
				doc.add(table);
			}
			doc.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}

	}
    public static void DojWise(String fdate,String tdate, String type, String filePath, String imagepath){
		
		System.out.println("I am in DojWise " + type);
		RepoartBean repBean  = new RepoartBean();
		try
		{
			Connection Cn = null;
			Statement st = null;
			ResultSet rs = null;

			ReportDAO.OpenCon("", "", "",repBean);
			Cn = repBean.getCn();
			
			int totalEmployee =0;
			String Query1 = "";
			
			LookupHandler lkhp = new LookupHandler();
			Rectangle rec = new  Rectangle(181,181);
			Document doc = new Document();
			PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filePath));
			
			ReportDAOPDF dao = new ReportDAOPDF();
			ReportDAOPDF.lable1="Date : ";
			Footer ftr = dao.new Footer(lable1);
			writer.setPageEvent(ftr);
			
			
			doc.open();
			doc.setPageSize(PageSize.A4);
			//doc.rightMargin();
			
			Font fbold = new Font(Font.TIMES_ROMAN,9,Font.BOLD);
			Font fsmall = new Font(Font.TIMES_ROMAN,7);
			Font FONT = new Font(Font.TIMES_ROMAN, 40, Font.NORMAL, new GrayColor(0.75f));
			Font  fontcolor = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(6, 8, 193));
			
			ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Business Bank",FONT), 297.5f, 421, 45);
			Image image = Image.getInstance(imagepath);
			image.scaleAbsolute(80f, 80f);
			image.setAbsolutePosition(10f, 740f);
			doc.add(image);
			
			
			Paragraph para = new Paragraph(new Phrase("Date of Joining Wise Employee List "));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);
			doc.add(para);
			int srno = 1;
			Query1  = ""
					+ "SELECT e.empno, "
					+ "       e.empcode, "
					+ "       e.doj, "
					+ "       Rtrim(e.fname) + ' ' + Rtrim(e.mname) + ' ' "
					+ "       + Rtrim(e.lname)                     AS NAME, "
					+ "	    (SELECT lkp_disc "
					+ "        FROM   lookup "
					+ "        WHERE  lkp_code = 'CASTE' "
					+ "               AND lkp_srno = e.castcd)     AS caste, "
					+ "       (SELECT lkp_disc "
					+ "        FROM   lookup "
					+ "        WHERE  lkp_code = 'CATE' "
					+ "               AND lkp_srno = e.categorycd) AS CATE, "
					+ "       (SELECT lkp_disc "
					+ "        FROM   lookup "
					+ "        WHERE  lkp_code = 'DESIG' "
					+ "               AND lkp_srno = e1.desig)     AS DESIG, "
					+ "       p.site_name, "
					+ "       e.FILENUMBER "
					+ "FROM   empmast e, "
					+ "       emptran e1, "
					+ "       project_sites p "
					+ "WHERE  e.empno = e1.empno "
					+ "       AND e1.srno = (SELECT Max(srno) "
					+ "                      FROM   emptran "
					+ "                      WHERE  empno = e.empno) "
					+ "       AND p.site_id = e1.prj_srno "
					+ "       AND e.DOJ BETWEEN '"+fdate+"' AND '"+tdate+"' "
					+ "ORDER  BY e.DOJ";
			
			
			/*if(type.equalsIgnoreCase("Doj"))
			{
				Query1 = " SELECT E.EMPNO,E.EMPCODE ,E.SALUTE ,rtrim(E.fname)+' '+rtrim(E.mname)+' '+rtrim(E.lname) as name ,E.DOJ FROM EMPMAST E " +
						" WHERE E.DOJ BETWEEN  '"+ReportDAO.BOM(date)+"' AND '"+ReportDAO.EOM(date)+"' ORDER BY E.DOJ,E.EMPNO";
				Query1 = " SELECT E.EMPNO,E.EMPCODE ,E.SALUTE ,rtrim(E.fname)+' '+rtrim(E.mname)+' '+rtrim(E.lname) as name ,E.DOJ FROM EMPMAST E " +
						" WHERE E.DOJ BETWEEN  '"+fdate+"' AND '"+tdate+"' ORDER BY E.DOJ,E.EMPNO";
				
				System.out.println("-----"+Query1);
			}
			else if(type.equalsIgnoreCase("Seniority"))
			{
				Query1 = " SELECT E.EMPNO,E.EMPCODE ,E.SALUTE ,rtrim(E.fname)+' '+rtrim(E.mname)+' '+rtrim(E.lname) as name ,E.DOJ FROM EMPMAST E " +
						" WHERE E.DOJ <=  '"+ReportDAO.EOM(date)+"' and E.STATUS='A'  ORDER BY E.DOJ";
			}*/
			
			st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(Query1);
			System.out.println("-----"+Query1);
			
			if(rs.next()){
				/*if(!rs.next()){
					System.out.println("Recordset EMpty");
				}*/
				PdfPTable main = new PdfPTable(9);
				main.setSpacingBefore(10);
				
				String str[] = new String[9];
				
				str[0]="Srno";			str[1]="Emp code";			str[2]="Name";
				str[3]="Branch Name";	str[4]="Desig";	str[5]="DOJ"; str[6]="File No";
				str[7]="Category";	str[8]="Caste";	
				
				main.setWidthPercentage(new float[]{13,16,42,26,18,14,15,15,22}, rec);
				main.setHorizontalAlignment(Element.ALIGN_CENTER);
				PdfPCell maincell ;	
				
				for(int i=0; i<str.length;i++ )
				{					
					maincell = new PdfPCell(new Phrase(str[i],fbold));
					maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
					main.addCell(maincell);
				}	
				doc.add(main);
				rs.beforeFirst();
				int cnt =0;
				
		//		if(rs.next()){
				while(rs.next())
				{
					PdfPTable main1 = new PdfPTable(9);
					main1.setWidthPercentage(new float[]{13,16,42,26,18,14,15,15,22}, rec);
					main1.setHorizontalAlignment(Element.ALIGN_CENTER);	
					
					PdfPCell cell1 = new PdfPCell(new Phrase(""+srno,fsmall));  
					cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
					main1.addCell(cell1);
					
					PdfPCell cell2 = new PdfPCell(new Phrase((rs.getString("EMPCODE")==null?"":rs.getString("EMPCODE")),fsmall));	
					cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
					main1.addCell(cell2);
					
					PdfPCell cell3 = new PdfPCell(new Phrase(""+rs.getString("name")==null?"":rs.getString("name"),fsmall));
					cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
					main1.addCell(cell3);
					
					PdfPCell cell4 = new PdfPCell(new Phrase((rs.getString("SITE_NAME")==null?"":rs.getString("SITE_NAME")),fsmall));
					cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
					main1.addCell(cell4);
					
					PdfPCell cell5 = new PdfPCell(new Phrase(""+(rs.getString("DESIG")==null?"":rs.getString("DESIG")),fsmall));
					cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
					main1.addCell(cell5);
					
					PdfPCell cell6 = new PdfPCell(new Phrase((rs.getString("DOJ")==null?"":rs.getString("DOJ")),fsmall));
					cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
					main1.addCell(cell6);
					
					PdfPCell cell7 = new PdfPCell(new Phrase(""+(rs.getString("FILENUMBER")==null?"":rs.getString("FILENUMBER")),fsmall));
					cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
					main1.addCell(cell7);
					
					PdfPCell cell51 = new PdfPCell(new Phrase(""+(rs.getString("cate")==null?"":rs.getString("cate")),fsmall));
					cell51.setHorizontalAlignment(Element.ALIGN_CENTER);
					main1.addCell(cell51);
					PdfPCell cell41 = new PdfPCell(new Phrase((rs.getString("caste")==null?"":rs.getString("caste")),fsmall));
					cell41.setHorizontalAlignment(Element.ALIGN_CENTER);
					main1.addCell(cell41);
					
					doc.add(main1);
					srno++;
					totalEmployee+=1;
					
				}		
				para = new Paragraph(new Phrase("Total Employee : "+totalEmployee,new Font(Font.TIMES_ROMAN,8,Font.BOLD)));  
				para.setAlignment(Element.ALIGN_LEFT);
				para.setSpacingAfter(10);
				doc.add(para);
                  }else{
					System.out.println("no record found");
					para = new Paragraph(new Phrase("Record not found",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
					para.setAlignment(Element.ALIGN_CENTER);
					para.setSpacingAfter(5);
					doc.add(para);
				}
				doc.close();
				Cn.close();
			}
			catch(Exception e) 
			{
				e.printStackTrace();
			}


	
	}
	
	// doj seniority list...
	public static void DojWiseseniority(String date, String type, String filePath, String imagepath){
		
		System.out.println("I am in DojWise " + type);
		RepoartBean repBean  = new RepoartBean();
		try
		{
			Connection Cn = null;
			Statement st = null;
			ResultSet rs = null;

			ReportDAO.OpenCon("", "", "",repBean);
			Cn = repBean.getCn();
			
			int totalEmployee =0;
			String Query1 = "";
			
			LookupHandler lkhp = new LookupHandler();
			Rectangle rec = new  Rectangle(100,100);
			Document doc = new Document();
			PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filePath));
			
			ReportDAOPDF dao = new ReportDAOPDF();
			ReportDAOPDF.lable1="Date : ";
			Footer ftr = dao.new Footer(lable1);
			writer.setPageEvent(ftr);
			
			
			doc.open();
			doc.setPageSize(PageSize.A4);
			//doc.rightMargin();
			
			Font fbold = new Font(Font.TIMES_ROMAN,12,Font.BOLD);
			Font fsmall = new Font(Font.TIMES_ROMAN,10);
			Font FONT = new Font(Font.TIMES_ROMAN, 40, Font.NORMAL, new GrayColor(0.75f));
			Font  fontcolor = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(6, 8, 193));
			
			ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Business Bank",FONT), 297.5f, 421, 45);
			Image image = Image.getInstance(imagepath);
			image.scaleAbsolute(80f, 80f);
			image.setAbsolutePosition(10f, 740f);
			doc.add(image);
			
			
			Paragraph para = new Paragraph(new Phrase("Date of Joining Wise Employee List "+date.substring(3),fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);
			doc.add(para);
			/*if(type.equalsIgnoreCase("Doj"))
			{
				Query1 = " SELECT E.EMPNO,E.EMPCODE ,E.SALUTE ,rtrim(E.fname)+' '+rtrim(E.mname)+' '+rtrim(E.lname) as name ,E.DOJ FROM EMPMAST E " +
						" WHERE E.DOJ BETWEEN  '"+ReportDAO.BOM(date)+"' AND '"+ReportDAO.EOM(date)+"' ORDER BY E.DOJ,E.EMPNO";
				Query1 = " SELECT E.EMPNO,E.EMPCODE ,E.SALUTE ,rtrim(E.fname)+' '+rtrim(E.mname)+' '+rtrim(E.lname) as name ,E.DOJ FROM EMPMAST E " +
						" WHERE E.DOJ BETWEEN  '"+fdate+"' AND '"+tdate+"' ORDER BY E.DOJ,E.EMPNO";
				
				System.out.println("-----"+Query1);
			}*/
			 if(type.equalsIgnoreCase("Seniority"))
			{
				Query1 = " SELECT E.EMPNO,E.EMPCODE ,E.SALUTE ,rtrim(E.fname)+' '+rtrim(E.mname)+' '+rtrim(E.lname) as name ,E.DOJ FROM EMPMAST E " +
						" WHERE E.DOJ <=  '"+ReportDAO.EOM(date)+"' and E.STATUS='A'  ORDER BY E.DOJ";
			}
			
			st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(Query1);
			System.out.println("-----"+Query1);
			
			if(!rs.next()){
				System.out.println("Recordset EMpty");
			}
			PdfPTable main = new PdfPTable(4);
			main.setSpacingBefore(10);
			
			String str[] = new String[4];
			
			str[0]="EMPNO";				str[1]="EMPCODE";			str[2]="EMPLOYEE NAME";
			str[3]="DOJ";						
			
			main.setWidthPercentage(new float[]{15,15,50,15f}, rec);
			main.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell maincell ;	
			
			for(int i=0; i<str.length;i++ )
			{					
				maincell = new PdfPCell(new Phrase(str[i],fbold));
				maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
				main.addCell(maincell);
			}	
			doc.add(main);
			rs.beforeFirst();
			while(rs.next())
			{
				PdfPTable main1 = new PdfPTable(4);
				main1.setWidthPercentage(new float[]{15,15,50,15f}, rec);
				main1.setHorizontalAlignment(Element.ALIGN_CENTER);	
				
				PdfPCell cell1 = new PdfPCell(new Phrase(rs.getString("EMPNO"),fsmall));  
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				main1.addCell(cell1);
				
				PdfPCell cell2 = new PdfPCell(new Phrase((rs.getString("EMPCODE")),fsmall));
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				main1.addCell(cell2);
				
				PdfPCell cell3 = new PdfPCell(new Phrase(lkhp.getLKP_Desc("SALUTE", rs.getInt("SALUTE"))+" "+rs.getString("name"),fsmall));
				cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
				main1.addCell(cell3);
				
				PdfPCell cell4 = new PdfPCell(new Phrase(""+EmpOffHandler.dateFormat(rs.getDate("DOJ")),fsmall));
				cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
				main1.addCell(cell4);
				
				doc.add(main1);
				
				totalEmployee+=1;
			}		
			//......................... Code To print data In Table Ends here...............................
			
			para = new Paragraph(new Phrase("No Of Employee :- "+totalEmployee,fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(10);
			doc.add(para);			
		
			//doc.newPage();
			doc.close();
			Cn.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}


	
	}
	
	public static void DolWise(String date, String type, String filePath, String imagepath){
		
		System.out.println("I am in DolWise " + type);
		RepoartBean repBean  = new RepoartBean();
		try
		{
			Connection Cn = null;
			Statement st = null;
			ResultSet rs = null;

			ReportDAO.OpenCon("", "", "",repBean);
			Cn = repBean.getCn();
			
			int totalEmployee =0;
			String Query = "";
			
			LookupHandler lkhp = new LookupHandler();
			Rectangle rec = new  Rectangle(100,100);
			Document doc = new Document();
			PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filePath));
			
			ReportDAOPDF dao = new ReportDAOPDF();
			ReportDAOPDF.lable1="Date : ";
			Footer ftr = dao.new Footer(lable1);
			writer.setPageEvent(ftr);
			
			
			doc.open();
			doc.setPageSize(PageSize.A4);
			//doc.rightMargin();
			
			Font fbold = new Font(Font.TIMES_ROMAN,12,Font.BOLD);
			Font fsmall = new Font(Font.TIMES_ROMAN,10);
			Font FONT = new Font(Font.TIMES_ROMAN, 40, Font.NORMAL, new GrayColor(0.75f));
			//to set font color in pdf
			//Font  fontcolor = FontFactory.getFont(FontFactory.HELVETICA, 30, Font.BOLD, new Color(0xFF, 0x00, 0x00));
			Font  fontcolor = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(6, 8, 193));
			
			ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Business Bank",FONT), 297.5f, 421, 45);
			Image image = Image.getInstance(imagepath);
			image.scaleAbsolute(80f, 80f);
			image.setAbsolutePosition(10f, 740f);
			doc.add(image);
			
			Paragraph para = new Paragraph(new Phrase("Date of Leaving Wise Employee List "+date.substring(3),fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);
			doc.add(para);
			
			Query = " SELECT E.EMPNO,E.EMPCODE ,E.SALUTE ,rtrim(E.fname)+' '+rtrim(E.mname)+' '+rtrim(E.lname) as NAME,E.DOJ,E.DOL FROM EMPMAST E " +
					" WHERE E.STATUS='N' AND E.DOL BETWEEN  '"+ReportDAO.BOM(date)+"' AND '"+ReportDAO.EOM(date)+"'ORDER BY E.DOJ,E.EMPNO";
			
			st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(Query);
			
			System.out.println("-----"+Query);
			
			if(!rs.next()){
				System.out.println("Recordset EMpty");
			}
			
			//............................ Code To print Heading start here...................................
			PdfPTable main = new PdfPTable(5);
			main.setSpacingBefore(10);
			
			String str[] = new String[5];
			
			str[0]="EMPNO";				str[1]="EMPCODE";			str[2]="EMPLOYEE NAME";
			str[3]="DOJ";				str[4]="DOL";		
			
			main.setWidthPercentage(new float[]{15,15,40,15,15f}, rec);
			main.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell maincell ;	
			
			for(int i=0; i<str.length;i++ )
			{					
				maincell = new PdfPCell(new Phrase(str[i],fbold));
				maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
				main.addCell(maincell);
			}	
			doc.add(main);
			//......................... Code To print Heading Ends here......................................
			
			//......................... Code To print data In Table Start here...............................
			rs.beforeFirst();
			while(rs.next())
			{
				PdfPTable main1 = new PdfPTable(5);
				main1.setWidthPercentage(new float[]{15,15,40,15,15f}, rec);
				main1.setHorizontalAlignment(Element.ALIGN_CENTER);	
				//PdfPCell maincell1 ;	
		
				PdfPCell cell1 = new PdfPCell(new Phrase(rs.getString("EMPNO"),fsmall));  
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				main1.addCell(cell1);
				
				PdfPCell cell2 = new PdfPCell(new Phrase((rs.getString("EMPCODE")),fsmall));
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				main1.addCell(cell2);
				
				// Calling getLKP_Desc() Method for Salutation Description.
				PdfPCell cell3 = new PdfPCell(new Phrase(lkhp.getLKP_Desc("SALUTE", rs.getInt("SALUTE"))+" "+rs.getString("name"),fsmall));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				main1.addCell(cell3);
				
				PdfPCell cell4 = new PdfPCell(new Phrase(""+EmpOffHandler.dateFormat(rs.getDate("DOJ")),fsmall));
				cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
				main1.addCell(cell4);
				
				PdfPCell cell5 = new PdfPCell(new Phrase(""+EmpOffHandler.dateFormat(rs.getDate("DOL")),fsmall));
				cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
				main1.addCell(cell5);
				
				doc.add(main1);
				
				totalEmployee+=1;
			}		
			//......................... Code To print data In Table Ends here...............................
			
			para = new Paragraph(new Phrase("No Of Employee :- "+totalEmployee,fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(10);
			doc.add(para);			
		
			//doc.newPage();
			doc.close();
			Cn.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}


	
	}

	public static void DesignationWise(String date, String type, String filePath, String imagepath,String desig){
		
		System.out.println("I am in Designation Wise" + type + " and  "+desig);
		RepoartBean repBean  = new RepoartBean();
		try
		{
			Connection Cn = null;
			Statement st = null;
			ResultSet rs = null;
			ResultSet rs1 = null;

			ReportDAO.OpenCon("", "", "",repBean);
			Cn = repBean.getCn();
			
			int totalEmployee =0;
			int Employee =0;
			String Query = "";
			String Query1 = "";
			
			LookupHandler lkhp = new LookupHandler();
			Rectangle rec = new  Rectangle(100,100);
			Document doc = new Document();
			PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filePath));
			
			ReportDAOPDF dao = new ReportDAOPDF();
			ReportDAOPDF.lable1="Date : ";
			Footer ftr = dao.new Footer(lable1);
			writer.setPageEvent(ftr);
			
			
			doc.open();
			doc.setPageSize(PageSize.A4);
			//doc.rightMargin();
			
			Font fbold = new Font(Font.TIMES_ROMAN,12,Font.BOLD);
			Font fsmall = new Font(Font.TIMES_ROMAN,10);
			Font FONT = new Font(Font.TIMES_ROMAN, 40, Font.NORMAL, new GrayColor(0.75f));
			Font  fontcolor = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(6, 8, 193));
			
			ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Business Bank",FONT), 297.5f, 421, 45);
			Image image = Image.getInstance(imagepath);
			image.scaleAbsolute(80f, 80f);
			image.setAbsolutePosition(10f, 740f);
			doc.add(image);
			
			Paragraph para = new Paragraph(new Phrase("Designation Wise Employee List "+date.substring(3),fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);
			doc.add(para);
			if(desig.equalsIgnoreCase("All")){
				Query1 = "select * from LOOKUP where LKP_CODE ='desig' and LKP_SRNO not in (0) ";
				
			}
			/*Query1 = "select lkp_srno from LOOKUP where LKP_CODE = 'desig' and LKP_RECR = "+emp.getInt("LKP_SRNO");*/
			else{
				//Query1 = "select * from LOOKUP where LKP_CODE ='desig' and LKP_SRNO =" +desig+ "  and LKP_SRNO not in (0) ";
				Query1 = "select * from LOOKUP where LKP_CODE ='desig' and  LKP_SRNO not in (0) ";
			}
	       // System.out.println("hello ak here is "+Query1 );
			st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs1 = st.executeQuery(Query1);	
			
			while(rs1.next())
			{		
				
				para = new Paragraph(new Phrase("Employee List for "+new Phrase(lkhp.getLKP_Desc("DESIG", rs1.getInt("LKP_SRNO")),fbold)));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingBefore(10);
				doc.add(para);
				
				//............................ Code To print Heading start here...................................
				PdfPTable main = new PdfPTable(5);
				main.setSpacingBefore(5);
				
				String str[] = new String[5];
				
				str[0]="EMPCODE";			str[1]="EMPLOYEE NAME";			str[2]="Designation";
				str[3]="DOB";				str[4]="DOJ";		
				
				main.setWidthPercentage(new float[]{15,45,15,15,15f}, rec);
				main.setHorizontalAlignment(Element.ALIGN_CENTER);
				PdfPCell maincell ;	
				
				for(int i=0; i<str.length;i++ )
				{					
					maincell = new PdfPCell(new Phrase(str[i],fbold));
					maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
					main.addCell(maincell);
				}	
				
				doc.add(main);
				//......................... Code To print Heading Ends here......................................
				
				//......................... Code To print data In Table Start here...............................
				
				Query = " Select emp.empcode,emp.salute,emp.empno,rtrim(emp.fname)+' '+rtrim(emp.mname)+' '+rtrim(emp.lname) name ," +
						" EMP.DOB,EMP.DOJ,t.DESIG, t.DEPT " + 
						" from empmast emp,emptran t where emp.status = 'A'  and emp.empno = t.empno and t.desig ="+rs1.getInt("LKP_SRNO")+"  and emp.empno = t.empno AND t.SRNO = (SELECT MAX(E2.SRNO) FROM EMPTRAN E2 " +
						" WHERE E2.EMPNO = emp.EMPNO AND E2.EFFDATE <= '"+ReportDAO.EOM(date)+"')  and t.SRNO =(select MAX(SRNO ) FROM EMPTRAN E3 WHERE E3 .EMPNO = emp.EMPNO) order by t.desig,emp.empcode" ;
				
				st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				rs = st.executeQuery(Query);
				System.out.println("-----"+Query);
				
				if(!rs.next()){
					System.out.println("Recordset EMpty");
				}
				
				rs.beforeFirst(); // to get result from 1 row......
				while(rs.next()){
					
					PdfPTable main1 = new PdfPTable(5);
					main1.setWidthPercentage(new float[]{15,45,15,15,15f}, rec);
					main1.setHorizontalAlignment(Element.ALIGN_CENTER);	
			
					PdfPCell cell1 = new PdfPCell(new Phrase(rs.getString("EMPCODE"),fsmall));  
					cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
					main1.addCell(cell1);
					
					// Calling getLKP_Desc() Method for Salutation Description.
					PdfPCell cell3 = new PdfPCell(new Phrase(lkhp.getLKP_Desc("SALUTE", rs.getInt("SALUTE"))+" "+rs.getString("name"),fsmall));
					cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
					main1.addCell(cell3);
					
					PdfPCell cell4 = new PdfPCell(new Phrase(lkhp.getLKP_Desc("DESIG", rs1.getInt("LKP_SRNO")),fsmall));
					cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
					main1.addCell(cell4);
					
					PdfPCell cell5 = new PdfPCell(new Phrase(""+EmpOffHandler.dateFormat(rs.getDate("DOB")),fsmall));
					cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
					main1.addCell(cell5);
					
					PdfPCell cell2 = new PdfPCell(new Phrase(""+EmpOffHandler.dateFormat(rs.getDate("DOJ")),fsmall));
					cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
					main1.addCell(cell2);
					
					doc.add(main1);
					Employee+=1;
				}
				
				para = new Paragraph(new Phrase("No Of Employee :- "+Employee,fsmall));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(10);
				doc.add(para);	
				
				totalEmployee = totalEmployee+Employee;
				Employee =0;
			}		
			//......................... Code To print data In Table Ends here...............................
			
			para = new Paragraph(new Phrase("Total Number Of Employee :- "+totalEmployee,fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(10);
			doc.add(para);			
		
			//doc.newPage();
			doc.close();
			Cn.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}


	
	}

	public static void ProjectWise(String date, String type, String filePath, String imagepath,String prj_srno){
		
		//System.out.println("I am in Project Wise" + type + "and " + prj_srno);
		RepoartBean repBean  = new RepoartBean();
		try
		{
			Connection Cn = null;
			Statement st = null;
			ResultSet rs = null;
			ResultSet rs1 = null;

			ReportDAO.OpenCon("", "", "",repBean);
			Cn = repBean.getCn();
			
			int totalEmployee =0;
			int Employee = 0;
			String Query = "";
			String Query1 = "";
			
			LookupHandler lkhp = new LookupHandler();
			Rectangle rec = new  Rectangle(100,100);
			Document doc = new Document();
			PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filePath));
			
			ReportDAOPDF dao = new ReportDAOPDF();
			ReportDAOPDF.lable1="Date : ";
			Footer ftr = dao.new Footer(lable1);
			writer.setPageEvent(ftr);
			
			
			doc.open();
			doc.setPageSize(PageSize.A4);
			//doc.rightMargin();
			
			Font fbold = new Font(Font.TIMES_ROMAN,12,Font.BOLD);
			Font fsmall = new Font(Font.TIMES_ROMAN,10);
			Font FONT = new Font(Font.TIMES_ROMAN, 40, Font.NORMAL, new GrayColor(0.75f));
			//to set font color in pdf
			//Font  fontcolor = FontFactory.getFont(FontFactory.HELVETICA, 30, Font.BOLD, new Color(0xFF, 0x00, 0x00));
			Font  fontcolor = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(6, 8, 193));
			
			ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Business Bank",FONT), 297.5f, 421, 45);
			Image image = Image.getInstance(imagepath);
			image.scaleAbsolute(80f, 80f);
			image.setAbsolutePosition(10f, 740f);
			doc.add(image);
			
			Paragraph para = new Paragraph(new Phrase("Project Wise Employee List "+date.substring(3),fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);
			doc.add(para);
			if(prj_srno.equalsIgnoreCase("All"))
					{
				Query = "select * from Project_Sites";
			}
			else{
		     	Query = "select * from Project_Sites where SITE_ID =" +prj_srno ;
			}
			//System.out.println("Query is@@@ " +Query);
			st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(Query);	
			
			String site_id ="";
			String site_name ="";
			String Project_Code ="";
			
			while(rs.next())
			{
				site_id = rs.getString("Site_ID");
				site_name = rs.getString("site_name");
				Project_Code = rs.getString("Project_Code");
				//Not want current employee to that site...
				/*Query1 =  " select emp.empcode,emp.salute,emp.empno,rtrim(emp.fname)+' '+rtrim(emp.mname)+' '+rtrim(emp.lname) name ," +
						  " EMP.DOB,EMP.DOJ, EMP.gender,t.DESIG,t.PRJ_SRNO " + 
						  " from empmast emp,emptran t where emp.status = 'A' AND t.PRJ_SRNO = "+site_id+"  AND t.EFFDATE = (SELECT MAX(E2.EFFDATE) FROM EMPTRAN E2 " +
						  " WHERE E2.EMPNO = emp.EMPNO AND E2.EFFDATE <= '"+ReportDAO.EOM(date)+"')  and emp.empno = t.empno  and t.SRNO =(select MAX(SRNO ) FROM EMPTRAN E3 WHERE E3 .EMPNO = emp.EMPNO) order by t.PRJ_SRNO,t.DESIG";
				*/
				//Want actual employee who are there for that site in that month......
				Query1 =  " select emp.empcode,emp.salute,emp.empno,rtrim(emp.fname)+' '+rtrim(emp.mname)+' '+rtrim(emp.lname) name ," +
						  " EMP.DOB,EMP.DOJ, EMP.gender,t.DESIG,t.PRJ_SRNO " + 
						  " from empmast emp,emptran t where emp.status = 'A' AND t.PRJ_SRNO = "+site_id+"  AND t.SRNO = (SELECT MAX(E2.SRNO) FROM EMPTRAN E2 " +
						  " WHERE E2.EMPNO = emp.EMPNO AND E2.EFFDATE <= '"+ReportDAO.EOM(date)+"')  and emp.empno = t.empno   order by t.PRJ_SRNO,t.DESIG";
				
				System.out.println("Query1 is @@@ " +Query1);
				st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				rs1 = st.executeQuery(Query1);	
				
				//......................... Code To print data In Table Ends here..............................
				/*if(!rs1.next()){ //no employee found for this site.......
					System.out.println("Do Nothing if site is not active");
				}
				else
				{*/
					para = new Paragraph(new Phrase("Employee List For Project Site is :- "+( site_name + Project_Code ),fbold));
					para.setAlignment(Element.ALIGN_CENTER);
					para.setSpacingAfter(10);
					doc.add(para);
					
					//............................ Code To print Heading start here...................................
					PdfPTable main = new PdfPTable(7);
					String str[] = new String[7];
					
					str[0]="CODE";			str[1]="EMPLOYEE NAME";		str[2]="Designation";
					str[3]="DOB";			str[4]="DOJ";				str[5]="Gender"; 		str[6]="Signature";	
					
					main.setWidthPercentage(new float[]{10,40,15,14,14,10,13f}, rec);
					main.setHorizontalAlignment(Element.ALIGN_CENTER);
					PdfPCell maincell ;	
					
					for(int i=0; i<str.length;i++ )
					{					
						maincell = new PdfPCell(new Phrase(str[i],fbold));
						maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
						main.addCell(maincell);
					}	
					
					doc.add(main);
					//......................... Code To print Heading Ends here......................................
					
					rs1.beforeFirst();
					
					while(rs1.next())
					{
						PdfPTable main1 = new PdfPTable(7);
						main1.setWidthPercentage(new float[]{10,40,15,14,14,10,13f}, rec);
						main1.setHorizontalAlignment(Element.ALIGN_CENTER);	
						
						PdfPCell cell1 = new PdfPCell(new Phrase(rs1.getString("empcode"),fsmall));  
						cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell1);
						
						// Calling getLKP_Desc() Method for Salutation Description.
						PdfPCell cell3 = new PdfPCell(new Phrase(lkhp.getLKP_Desc("SALUTE", rs1.getInt("SALUTE"))+" "+rs1.getString("name"),fsmall));
						cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
						main1.addCell(cell3);
						
						PdfPCell cell4 = new PdfPCell(new Phrase(lkhp.getLKP_Desc("DESIG", rs1.getInt("DESIG")),fsmall));
						//PdfPCell cell4 = new PdfPCell(new Phrase("",fsmall));
						cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell4);
						
						PdfPCell cell5 = new PdfPCell(new Phrase(""+EmpOffHandler.dateFormat(rs1.getDate("DOB")),fsmall));
						cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell5);
						
						PdfPCell cell2 = new PdfPCell(new Phrase(""+EmpOffHandler.dateFormat(rs1.getDate("DOJ")),fsmall));
						cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell2);
						
						PdfPCell cell6 = new PdfPCell(new Phrase(""+rs1.getString("gender"),fsmall));
						cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell6);
						
						PdfPCell cell7 = new PdfPCell(new Phrase(" ",fsmall));
						cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell7);
						
						doc.add(main1);
						
						Employee+=1;	
					}     
					
				
				//}
				
				para = new Paragraph(new Phrase("Number Of Employee :- "+Employee,fsmall));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(20);
				doc.add(para);	
				
				
				
				totalEmployee = totalEmployee+ Employee;	
				Employee = 0;
				//......................... Code To print data In Table Ends here...............................
			}		
			
			para = new Paragraph(new Phrase("Total Number Of Employee :- "+totalEmployee,fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(10);
			doc.add(para);			
		
			//doc.newPage();
			doc.close();
			Cn.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}


	
	}

//	For Employee Signature Report
	
public static void EmpSign(String date, String type, String filePath, String imagepath,String prj_srno)
{
	
	RepoartBean repBean  = new RepoartBean();
	try
	{
		Connection Cn = null;
		Statement st = null;
		ResultSet rs = null;
		ResultSet rs1 = null;

		ReportDAO.OpenCon("", "", "",repBean);
		Cn = repBean.getCn();
		
		int totalEmployee =0;
		int Employee = 0;
		String Query = "";
		String Query1 = "";
		
		LookupHandler lkhp = new LookupHandler();
		Rectangle rec = new  Rectangle(100,100);
		Document doc = new Document();
		PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filePath));
		
		ReportDAOPDF dao = new ReportDAOPDF();
		ReportDAOPDF.lable1="Date : ";
		Footer ftr = dao.new Footer(lable1);
		writer.setPageEvent(ftr);
		
		
		doc.open();
		doc.setPageSize(PageSize.A4);
		
		Font fbold = new Font(Font.TIMES_ROMAN,12,Font.BOLD);
		Font fsmall = new Font(Font.TIMES_ROMAN,10);
		Font FONT = new Font(Font.TIMES_ROMAN, 40, Font.NORMAL, new GrayColor(0.75f));
		
		Font  fontcolor = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(6, 8, 193));
		
		Properties prop = new Properties();
		
		Paragraph para ;
		if(prj_srno.equalsIgnoreCase("All"))
				{
			Query = "select * from Project_Sites";
		}
		else{
	     	Query = "select * from Project_Sites where SITE_ID =" +prj_srno ;
		}
		
		st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		rs = st.executeQuery(Query);	
		
		String site_id ="";
		String site_name ="";
		String Project_Code ="";
		
		while(rs.next())
		{
			site_id = rs.getString("Site_ID");
			site_name = rs.getString("site_name");
			Project_Code = rs.getString("Project_Code");
			//Not want current employee to that site...
			/*Query1 =  " select emp.empcode,emp.salute,emp.empno,rtrim(emp.fname)+' '+rtrim(emp.mname)+' '+rtrim(emp.lname) name ," +
					  " EMP.DOB,EMP.DOJ, EMP.gender,t.DESIG,t.PRJ_SRNO " + 
					  " from empmast emp,emptran t where emp.status = 'A' AND t.PRJ_SRNO = "+site_id+"  AND t.EFFDATE = (SELECT MAX(E2.EFFDATE) FROM EMPTRAN E2 " +
					  " WHERE E2.EMPNO = emp.EMPNO AND E2.EFFDATE <= '"+ReportDAO.EOM(date)+"')  and emp.empno = t.empno  and t.SRNO =(select MAX(SRNO ) FROM EMPTRAN E3 WHERE E3 .EMPNO = emp.EMPNO) order by t.PRJ_SRNO,t.DESIG";
			*/
			//Want actual employee who are there for that site in that month......
			Query1 =  " select emp.empcode,emp.salute,emp.empno,rtrim(emp.fname)+' '+rtrim(emp.mname)+' '+rtrim(emp.lname) name ," +
					  " EMP.DOB,EMP.DOJ, EMP.gender,t.DESIG,t.PRJ_SRNO " + 
					  " from empmast emp,emptran t where emp.status = 'A' AND t.PRJ_SRNO = "+site_id+"  AND t.SRNO = (SELECT MAX(E2.SRNO) FROM EMPTRAN E2 " +
					  " WHERE E2.EMPNO = emp.EMPNO AND E2.EFFDATE <= '"+ReportDAO.EOM(date)+"')  and emp.empno = t.empno   order by t.PRJ_SRNO,t.DESIG";
			
			System.out.println("Query1 is @@@ " +Query1);
			st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs1 = st.executeQuery(Query1);	
			
			//......................... Code To print data In Table Ends here..............................
			/*if(!rs1.next()){ //no employee found for this site.......
				System.out.println("Do Nothing if site is not active");
			}
			else
			{*/
			int pageNumber = writer.getPageNumber();
			if(pageNumber!=1)
			{
			
			 try
			    {			
						ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
					    InputStream stream = classLoader.getResourceAsStream("constant.properties");
					    prop.load(stream);
				}
			    catch (Exception e)  {System.out.println("Error in constant properties Manager "+e);}
				
			
			Phrase title = new Phrase(prop.getProperty("bankName2"),fbold);
			para = new Paragraph(title);
			para.setAlignment(Element.ALIGN_CENTER);
			doc.add(para);
			
			para = new Paragraph(new Phrase(prop.getProperty("bankName3"),fsmall));
			para.setAlignment(Element.ALIGN_CENTER);
			doc.add(para);

			para = new Paragraph(new Phrase(prop.getProperty("addressForReport"),fsmall));
			para.setAlignment(Element.ALIGN_CENTER);
			doc.add(para);
			
			para = new Paragraph(new Phrase(prop.getProperty("contactForReport"),fsmall));
			para.setAlignment(Element.ALIGN_CENTER);
			doc.add(para);
			
			para = new Paragraph(new Phrase(prop.getProperty("mailForReport"),fontcolor));
			para.setAlignment(Element.ALIGN_CENTER);
			doc.add(para);
			}
				
				ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Business Bank",FONT), 297.5f, 421, 45);
				Image image = Image.getInstance(imagepath);
				image.scaleAbsolute(80f, 80f);
				image.setAbsolutePosition(10f, 740f);
				doc.add(image);
				para= new Paragraph(new Phrase("Project Wise Employee List "+date.substring(3),fbold));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(0);
				doc.add(para);
				para = new Paragraph(new Phrase("Employee List For Project Site is :- "+( site_name + Project_Code ),fbold));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(10);
				doc.add(para);
				
				//............................ Code To print Heading start here...................................
				PdfPTable main = new PdfPTable(6);
				String str[] = new String[6];
				
				str[0]="Sr No."; str[1]="CODE";			str[2]="EMPLOYEE NAME";		str[3]="Designation";
							str[4]="Gender"; 		str[5]="Signature";	
				
				main.setWidthPercentage(new float[]{8,10,40,15,10,13f}, rec);
				main.setHorizontalAlignment(Element.ALIGN_CENTER);
				PdfPCell maincell ;	
				
				for(int i=0; i<str.length;i++ )
				{					
					maincell = new PdfPCell(new Phrase(str[i],fbold));
					maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
					main.addCell(maincell);
				}	
				
				doc.add(main);
				//......................... Code To print Heading Ends here......................................
				
				rs1.beforeFirst();
				int count1=1;
				while(rs1.next())
				{
					PdfPTable main1 = new PdfPTable(6);
					main1.setWidthPercentage(new float[]{8,10,40,15,10,13f}, rec);
					main1.setHorizontalAlignment(Element.ALIGN_CENTER);	
					
					PdfPCell cell1 = new PdfPCell(new Phrase(String.valueOf(count1),fsmall));  
					cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
					main1.addCell(cell1);
					
					PdfPCell cell2 = new PdfPCell(new Phrase(rs1.getString("empcode"),fsmall));  
					cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
					main1.addCell(cell2);
					
					// Calling getLKP_Desc() Method for Salutation Description.
					PdfPCell cell3 = new PdfPCell(new Phrase(lkhp.getLKP_Desc("SALUTE", rs1.getInt("SALUTE"))+" "+rs1.getString("name"),fsmall));
					cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
					main1.addCell(cell3);
					
					PdfPCell cell4 = new PdfPCell(new Phrase(lkhp.getLKP_Desc("DESIG", rs1.getInt("DESIG")),fsmall));
					//PdfPCell cell4 = new PdfPCell(new Phrase("",fsmall));
					cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
					main1.addCell(cell4);
					
					/*PdfPCell cell5 = new PdfPCell(new Phrase(""+EmpOffHandler.dateFormat(rs1.getDate("DOB")),fsmall));
					cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
					main1.addCell(cell5);
					
					PdfPCell cell2 = new PdfPCell(new Phrase(""+EmpOffHandler.dateFormat(rs1.getDate("DOJ")),fsmall));
					cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
					main1.addCell(cell2);*/
					
					PdfPCell cell6 = new PdfPCell(new Phrase(""+rs1.getString("gender"),fsmall));
					cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
					main1.addCell(cell6);
					
					PdfPCell cell7 = new PdfPCell(new Phrase(" ",fsmall));
					cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
					main1.addCell(cell7);
					
					doc.add(main1);
					
					
					
					Employee+=1;	
					count1++;
				}     
				
			
			//}
			
			para = new Paragraph(new Phrase("Number Of Employee :- "+Employee,fsmall));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(20);
			doc.add(para);	
			count1=1;
			doc.newPage();  
			
			
		
			totalEmployee = totalEmployee+ Employee;	
			Employee = 0;
			//......................... Code To print data In Table Ends here...............................
		}		
		
	/*	para = new Paragraph(new Phrase("Total Number Of Employee :- "+totalEmployee,fbold));
		para.setAlignment(Element.ALIGN_CENTER);
		para.setSpacingAfter(10);
		doc.add(para);			
	*/
		
		doc.close();
		Cn.close();
	}
	catch(Exception e) 
	{
		e.printStackTrace();
	}



}
	
	
	public static void GradeWise(String date, String type, String filePath, String imagepath){
		
		System.out.println("I am in Grade Wise" + type);
		RepoartBean repBean  = new RepoartBean();
		try
		{
			Connection Cn = null;
			Statement st = null;
			ResultSet rs = null;
			ResultSet rs1 = null;

			ReportDAO.OpenCon("", "", "",repBean);
			Cn = repBean.getCn();
			
			int totalEmployee = 0;
			int Employee = 0;
			String Query = "";
			String Query1 = "";
			
			LookupHandler lkhp = new LookupHandler();
			Rectangle rec = new  Rectangle(100,100);
			Document doc = new Document();
			PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filePath));
			
			ReportDAOPDF dao = new ReportDAOPDF();
			ReportDAOPDF.lable1="Date : ";
			Footer ftr = dao.new Footer(lable1);
			writer.setPageEvent(ftr);
			
			
			doc.open();
			doc.setPageSize(PageSize.A4);
			//doc.rightMargin();
			
			Font fbold = new Font(Font.TIMES_ROMAN,12,Font.BOLD);
			Font fsmall = new Font(Font.TIMES_ROMAN,10);
			Font FONT = new Font(Font.TIMES_ROMAN, 40, Font.NORMAL, new GrayColor(0.75f));
			Font  fontcolor = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(6, 8, 193));
			
			ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Business Bank",FONT), 297.5f, 421, 45);
			Image image = Image.getInstance(imagepath);
			image.scaleAbsolute(80f, 80f);
			image.setAbsolutePosition(10f, 740f);
			doc.add(image);
		
			Paragraph para = new Paragraph(new Phrase("Grade Wise Employee List "+date.substring(3),fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);
			doc.add(para);
			
			/*Query1 = "select lkp_srno from LOOKUP where LKP_CODE = 'desig' and LKP_RECR = "+emp.getInt("LKP_SRNO");*/
			Query1 = "select * from LOOKUP where LKP_CODE ='gd' and LKP_SRNO not in (0) ";
			
			st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs1 = st.executeQuery(Query1);	
			
			while(rs1.next())
			{				
				//............................ Code To print Heading start here...................................
				PdfPTable main = new PdfPTable(5);
				main.setSpacingBefore(10);
				
				String str[] = new String[5];
				
				str[0]="EMPCODE";			str[1]="EMPLOYEE NAME";			str[2]="GRADE";
				str[3]="DOB";				str[4]="DOJ";		
				
				main.setWidthPercentage(new float[]{15,45,15,15,15f}, rec);
				main.setHorizontalAlignment(Element.ALIGN_CENTER);
				PdfPCell maincell ;	
				
				for(int i=0; i<str.length;i++ )
				{					
					maincell = new PdfPCell(new Phrase(str[i],fbold));
					maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
					main.addCell(maincell);
				}	
				
				doc.add(main);
				//......................... Code To print Heading Ends here......................................
				
				//......................... Code To print data In Table Start here...............................
				
				Query = " Select emp.empcode,emp.salute,emp.empno,rtrim(emp.fname)+' '+rtrim(emp.mname)+' '+rtrim(emp.lname) name ," +
						" EMP.DOB,EMP.DOJ,t.DESIG, t.DEPT " + 
						" from empmast emp,emptran t where emp.status = 'A'  and emp.empno = t.empno and t.desig ="+rs1.getInt("LKP_SRNO")+"  and emp.empno = t.empno AND t.SRNO = (SELECT MAX(E2.SRNO) FROM EMPTRAN E2 " +
						" WHERE E2.EMPNO = emp.EMPNO AND E2.EFFDATE <= '"+ReportDAO.EOM(date)+"') order by t.desig,emp.empcode" ;
				
				st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				rs = st.executeQuery(Query);
				System.out.println("-----"+Query);
				
				if(!rs.next()){
					System.out.println("Recordset EMpty");
				}
				
				rs.beforeFirst(); // to get result from 1 row......
				while(rs.next()){
					
					PdfPTable main1 = new PdfPTable(5);
					main1.setWidthPercentage(new float[]{15,45,15,15,15f}, rec);
					main1.setHorizontalAlignment(Element.ALIGN_CENTER);	
			
					PdfPCell cell1 = new PdfPCell(new Phrase(rs.getString("EMPCODE"),fsmall));  
					cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
					main1.addCell(cell1);
					
					// Calling getLKP_Desc() Method for Salutation Description.
					PdfPCell cell3 = new PdfPCell(new Phrase(lkhp.getLKP_Desc("SALUTE", rs.getInt("SALUTE"))+" "+rs.getString("name"),fsmall));
					cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
					main1.addCell(cell3);
					
					PdfPCell cell4 = new PdfPCell(new Phrase(lkhp.getLKP_Desc("DESIG", rs1.getInt("LKP_SRNO")),fsmall));
					cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
					main1.addCell(cell4);
					
					PdfPCell cell5 = new PdfPCell(new Phrase(""+EmpOffHandler.dateFormat(rs.getDate("DOB")),fsmall));
					cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
					main1.addCell(cell5);
					
					PdfPCell cell2 = new PdfPCell(new Phrase(""+EmpOffHandler.dateFormat(rs.getDate("DOJ")),fsmall));
					cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
					main1.addCell(cell2);
					
					doc.add(main1);
					
					Employee+=1;
				}
				
				para = new Paragraph(new Phrase("No Of Employee :- "+Employee,fbold));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(10);
				doc.add(para);			
				
				totalEmployee = totalEmployee + Employee;
				Employee =0;
			}		
			//......................... Code To print data In Table Ends here...............................
			para = new Paragraph(new Phrase("Total Number Of Employee :- "+totalEmployee,fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(10);
			doc.add(para);			
		
			//doc.newPage();
			doc.close();
			Cn.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}


	
	}

	public static void DepartmentWise(String date, String type, String filePath, String imagepath,String dept){
		
		System.out.println("I am in DepartmentWise Wise" + type + "ak " +dept);
		RepoartBean repBean  = new RepoartBean();
		try
		{
			Connection Cn = null;
			Statement st = null;
			ResultSet rs = null;
			ResultSet rs1 = null;

			ReportDAO.OpenCon("", "", "",repBean);
			Cn = repBean.getCn();
			
			int totalEmployee =0;
			int Employee = 0;
			String Query = "";
			String Query1 = "";
			
			
			LookupHandler lkhp = new LookupHandler();
			Rectangle rec = new  Rectangle(100,100);
			Document doc = new Document();
			PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filePath));
			
			ReportDAOPDF dao = new ReportDAOPDF();
			ReportDAOPDF.lable1="Date : ";
			Footer ftr = dao.new Footer(lable1);
			writer.setPageEvent(ftr);
			
			doc.open();
			doc.setPageSize(PageSize.A4);
			
			Font fbold = new Font(Font.TIMES_ROMAN,12,Font.BOLD);
			Font fsmall = new Font(Font.TIMES_ROMAN,10);
			Font FONT = new Font(Font.TIMES_ROMAN, 40, Font.NORMAL, new GrayColor(0.75f));
			//to set font color in pdf
			//Font  fontcolor = FontFactory.getFont(FontFactory.HELVETICA, 30, Font.BOLD, new Color(0xFF, 0x00, 0x00));
			Font  fontcolor = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(6, 8, 193));
			
			ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Business Bank",FONT), 297.5f, 421, 45);
			Image image = Image.getInstance(imagepath);
			image.scaleAbsolute(80f, 80f);
			image.setAbsolutePosition(10f, 740f);
			doc.add(image);
			
			Paragraph para = new Paragraph(new Phrase("Department Wise Employee List "+date.substring(3),fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);
			doc.add(para);
			if(dept.equalsIgnoreCase("All"))
			{
			Query = "select * from LOOKUP where LKP_CODE='dept' and LKP_SRNO not in (0) ";
			}
			else
			{
		    Query = "select * from LOOKUP where LKP_CODE='dept' and LKP_SRNO =" +dept+ " and LKP_SRNO not in (0) ";
			}
			System.out.println("Query is@@@ " +Query);
			st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(Query);	
			
			String department ="";
			
			while(rs.next())
			{
				department = rs.getString("LKP_DISC");
				Query1 = " Select emp.empcode,emp.salute,emp.empno,rtrim(emp.fname)+' '+rtrim(emp.mname)+' '+rtrim(emp.lname) name ," +
						" EMP.DOB,EMP.DOJ,t.DESIG, t.DEPT " + 
						" from empmast emp,emptran t where emp.status = 'A'  and emp.empno = t.empno and t.DEPT ="+rs.getInt("LKP_SRNO")+"  and emp.empno = t.empno AND t.SRNO = (SELECT MAX(E2.SRNO) FROM EMPTRAN E2 " +
						" WHERE E2.EMPNO = emp.EMPNO AND E2.EFFDATE <= '"+ReportDAO.EOM(date)+"')  and t.SRNO =(select MAX(SRNO ) FROM EMPTRAN E3 WHERE E3 .EMPNO = emp.EMPNO) order by t.DEPT,emp.empcode" ;
				
				System.out.println("Query1 is @@@ " +Query1);
				st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				rs1 = st.executeQuery(Query1);	
				
				//......................... Code To print data In Table Ends here..............................
				/*if(!rs1.next()){ //no employee found for this site.......
					System.out.println("Do Nothing if site isw not active");
				}
				else
				{*/
					para = new Paragraph(new Phrase("Employee List For Department Wise is :- "+( department ),fbold));
					para.setAlignment(Element.ALIGN_CENTER);
					para.setSpacingAfter(10);
					doc.add(para);
					
					//............................ Code To print Heading start here...................................
					PdfPTable main = new PdfPTable(5);
					String str[] = new String[5];
					
					str[0]="EMPCODE";			str[1]="EMPLOYEE NAME";			str[2]="DEPARTMENT";
					str[3]="DOB";				str[4]="DOJ";		
					
					main.setWidthPercentage(new float[]{12,45,20,13,13f}, rec);
					main.setHorizontalAlignment(Element.ALIGN_CENTER);
					PdfPCell maincell ;	
					
					for(int i=0; i<str.length;i++ )
					{					
						maincell = new PdfPCell(new Phrase(str[i],fbold));
						maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
						main.addCell(maincell);
					}	
					
					doc.add(main);
					//......................... Code To print Heading Ends here......................................
					
					rs1.beforeFirst();
					while(rs1.next())
					{
						PdfPTable main1 = new PdfPTable(5);
						main1.setWidthPercentage(new float[]{12,45,20,13,13f}, rec);
						main1.setHorizontalAlignment(Element.ALIGN_CENTER);	
						
						PdfPCell cell1 = new PdfPCell(new Phrase(rs1.getString("empcode"),fsmall));  
						cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell1);
						
						// Calling getLKP_Desc() Method for Salutation Description.
						PdfPCell cell3 = new PdfPCell(new Phrase(lkhp.getLKP_Desc("SALUTE", rs1.getInt("SALUTE"))+" "+rs1.getString("name"),fsmall));
						cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell3);
						
						PdfPCell cell4 = new PdfPCell(new Phrase(lkhp.getLKP_Desc("DEPT", rs1.getInt("DEPT")),fsmall));
						cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell4);
						
						PdfPCell cell5 = new PdfPCell(new Phrase(""+EmpOffHandler.dateFormat(rs1.getDate("DOB")),fsmall));
						cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell5);
						
						PdfPCell cell2 = new PdfPCell(new Phrase(""+EmpOffHandler.dateFormat(rs1.getDate("DOJ")),fsmall));
						cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell2);
						
						doc.add(main1);
						
						Employee+=1;	
					}     
					
				
				//}
				
				para = new Paragraph(new Phrase("Number Of Employee :- "+Employee,fsmall));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(20);
				doc.add(para);	
				
				totalEmployee = totalEmployee+ Employee;	
				Employee = 0;
				//......................... Code To print data In Table Ends here...............................
			}		
			
			para = new Paragraph(new Phrase("Total Number Of Employee :- "+totalEmployee,fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(10);
			doc.add(para);			
		
			//doc.newPage();
			doc.close();
			Cn.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}


	
	}

	public static void HandicapWise(String date, String type, String filePath, String imagepath){
		
		System.out.println("I am in HandicapWise Wise" + type);
		RepoartBean repBean  = new RepoartBean();
		try
		{
			Connection Cn = null;
			Statement st = null;
			ResultSet rs = null;

			ReportDAO.OpenCon("", "", "",repBean);
			Cn = repBean.getCn();
		
			int Employee = 0;
			String Query = "";
			
			LookupHandler lkhp = new LookupHandler();
			Rectangle rec = new  Rectangle(100,100);
			Document doc = new Document();
			PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filePath));
			
			ReportDAOPDF dao = new ReportDAOPDF();
			ReportDAOPDF.lable1="Date : ";
			Footer ftr = dao.new Footer(lable1);
			writer.setPageEvent(ftr);
			
			doc.open();
			doc.setPageSize(PageSize.A4);
			
			Font fbold = new Font(Font.TIMES_ROMAN,12,Font.BOLD);
			Font fsmall = new Font(Font.TIMES_ROMAN,10);
			Font FONT = new Font(Font.TIMES_ROMAN, 40, Font.NORMAL, new GrayColor(0.75f));
			
			Font  fontcolor = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(6, 8, 193));
			
			ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Business Bank",FONT), 297.5f, 421, 45);
			Image image = Image.getInstance(imagepath);
			image.scaleAbsolute(80f, 80f);
			image.setAbsolutePosition(10f, 740f);
			doc.add(image);
			
			Paragraph para = new Paragraph(new Phrase("Handicap Employee List "+date.substring(3),fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(10);
			doc.add(para);
			
			Query = " select EM.*, rtrim(EM.fname)+' '+rtrim(EM.mname)+' '+rtrim(EM.lname) NAME  , ET.DESIG,ET.DEPT from EMPMAST EM ,emptran ET " +
					" where EM.DISABILYN = 'Y' AND ET.EMPNO = EM.EMPNO order by GENDER, empno";
			
			System.out.println("Query is@@@ " +Query);
			st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(Query);	
	
			//............................ Code To print Heading start here...................................
					PdfPTable main = new PdfPTable(6);
					String str[] = new String[6];
					
					str[0]= "EMPCODE";			str[1]= "EMPLOYEE NAME";	str[2]= "GENDER";
					str[3]= "DESIGNATION";			str[4]= "DEPARTMENT";				str[5] = "DOJ";
					
					main.setWidthPercentage(new float[]{12,36,12,15,15,13f}, rec);
					main.setHorizontalAlignment(Element.ALIGN_CENTER);
					PdfPCell maincell ;	
					
					for(int i=0; i<str.length;i++ )
					{					
						maincell = new PdfPCell(new Phrase(str[i],fbold));
						maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
						main.addCell(maincell);
					}	
					
					doc.add(main);
		   //......................... Code To print Heading Ends here......................................
		   //......................... Code To print data In Table Ends here..............................	
					while(rs.next())
					{
						PdfPTable main1 = new PdfPTable(6);
						main1.setWidthPercentage(new float[]{12,36,12,15,15,13f}, rec);
						main1.setHorizontalAlignment(Element.ALIGN_CENTER);	
						
						PdfPCell cell1 = new PdfPCell(new Phrase(rs.getString("EMPCODE"),fsmall));  
						cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell1);
						
						// Calling getLKP_Desc() Method for Salutation Description.
						PdfPCell cell3 = new PdfPCell(new Phrase(lkhp.getLKP_Desc("SALUTE", rs.getInt("SALUTE"))+" "+rs.getString("name"),fsmall));
						cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell3);
						
						PdfPCell cell6 = new PdfPCell(new Phrase(rs.getString("GENDER"),fsmall));  
						cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell6);
						
						PdfPCell cell4 = new PdfPCell(new Phrase(lkhp.getLKP_Desc("DESIG", rs.getInt("DESIG")),fsmall));
						cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell4);
						
						PdfPCell cell5 = new PdfPCell(new Phrase(lkhp.getLKP_Desc("DEPT", rs.getInt("DEPT")),fsmall));
						cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell5);
						
						PdfPCell cell2 = new PdfPCell(new Phrase(""+EmpOffHandler.dateFormat(rs.getDate("DOJ")),fsmall));
						cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell2);
						
						doc.add(main1);
						
						Employee+=1;	
					}     
					
				para = new Paragraph(new Phrase("Number Of Employee :- "+Employee,fsmall));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(20);
				doc.add(para);				
		//......................... Code To print data In Table Ends here..............................
			doc.close();
			Cn.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}


	
	}

	public static void BloodGroupWise(String date, String type, String filePath, String imagepath){
		
		System.out.println("I am in BloodGroup Wise" + type);
		RepoartBean repBean  = new RepoartBean();
		try
		{
			Connection Cn = null;
			Statement st = null;
			ResultSet rs = null;
			ResultSet rs1 = null;

			ReportDAO.OpenCon("", "", "",repBean);
			Cn = repBean.getCn();
			
			int totalEmployee =0;
			int Employee = 0;
			String Query = "";
			String Query1 = "";
			
			LookupHandler lkhp = new LookupHandler();
			Rectangle rec = new  Rectangle(100,100);
			Document doc = new Document();
			PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filePath));
			
			ReportDAOPDF dao = new ReportDAOPDF();
			ReportDAOPDF.lable1="Date : ";
			Footer ftr = dao.new Footer(lable1);
			writer.setPageEvent(ftr);
			
			
			doc.open();
			doc.setPageSize(PageSize.A4);
			
			Font fbold = new Font(Font.TIMES_ROMAN,12,Font.BOLD);
			Font fsmall = new Font(Font.TIMES_ROMAN,10);
			Font FONT = new Font(Font.TIMES_ROMAN, 40, Font.NORMAL, new GrayColor(0.75f));
			Font  fontcolor = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(6, 8, 193));
			
			ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Business Bank",FONT), 297.5f, 421, 45);
			Image image = Image.getInstance(imagepath);
			image.scaleAbsolute(80f, 80f);
			image.setAbsolutePosition(10f, 740f);
			doc.add(image);
			
			
			Paragraph para = new Paragraph(new Phrase("BloodGroup Wise Employee List "+date.substring(3),fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);
			doc.add(para);
			
			Query = "select * from LOOKUP where LKP_CODE like 'blood_GRP'";
			//System.out.println("Query is@@@ " +Query);
			st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(Query);	
			
			int LKP_SRNO = 0;
			String LKP_DISC = "";
			
			while(rs.next())
			{
				LKP_SRNO = rs.getInt("LKP_SRNO");
				LKP_DISC = rs.getString("LKP_DISC");
				Query1 =  " select EM.*, rtrim(EM.fname)+' '+rtrim(EM.mname)+' '+rtrim(EM.lname) NAME , ET.DESIG" +
						  " from EMPMAST EM ,EMPTRAN ET where EM.BGRP = "+LKP_SRNO+" AND EM.STATUS = 'A' and   " +
						  " et.SRNO = (select max(SRNO) from EMPTRAN et1 where  et1.EMPNO = EM.EMPNO) and ET.EMPNO = EM.EMPNO";
				
				
				System.out.println("Query1 is @@@ " +Query1);
				st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				rs1 = st.executeQuery(Query1);	
				
					para = new Paragraph(new Phrase("Employee List For :- "+ ( LKP_DISC ),fbold));
					para.setAlignment(Element.ALIGN_CENTER);
					para.setSpacingAfter(10);
					doc.add(para);
					
					//............................ Code To print Heading start here...................................
					PdfPTable main = new PdfPTable(5);
					String str[] = new String[5];
					
					str[0]="EMPCODE";			str[1]="EMPLOYEE NAME";			str[2]="Designation";
					str[3]="DOB";				str[4]="DOJ";		
					
					main.setWidthPercentage(new float[]{15,45,15,15,15f}, rec);
					main.setHorizontalAlignment(Element.ALIGN_CENTER);
					PdfPCell maincell ;	
					
					for(int i=0; i<str.length;i++ )
					{					
						maincell = new PdfPCell(new Phrase(str[i],fbold));
						maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
						main.addCell(maincell);
					}	
					
					doc.add(main);
					//......................... Code To print Heading Ends here......................................
					//......................... Code To print data Start here........................................
					rs1.beforeFirst();
					while(rs1.next())
					{
						PdfPTable main1 = new PdfPTable(5);
						main1.setWidthPercentage(new float[]{15,45,15,15,15f}, rec);
						main1.setHorizontalAlignment(Element.ALIGN_CENTER);	
						
						PdfPCell cell1 = new PdfPCell(new Phrase(rs1.getString("empcode"),fsmall));  
						cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell1);
						
						// Calling getLKP_Desc() Method for Salutation Description.
						PdfPCell cell3 = new PdfPCell(new Phrase(lkhp.getLKP_Desc("SALUTE", rs1.getInt("SALUTE"))+" "+rs1.getString("name"),fsmall));
						cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell3);
						
						PdfPCell cell4 = new PdfPCell(new Phrase(lkhp.getLKP_Desc("DESIG", rs1.getInt("DESIG")),fsmall));
						//PdfPCell cell4 = new PdfPCell(new Phrase("",fsmall));
						cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell4);
						
						PdfPCell cell5 = new PdfPCell(new Phrase(""+EmpOffHandler.dateFormat(rs1.getDate("DOB")),fsmall));
						cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell5);
						
						PdfPCell cell2 = new PdfPCell(new Phrase(""+EmpOffHandler.dateFormat(rs1.getDate("DOJ")),fsmall));
						cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell2);
						
						doc.add(main1);
						
						Employee+=1;	
					}     
					//......................... Code To print data In Table Ends here..............................
				para = new Paragraph(new Phrase("Number Of Employee :- "+Employee,fsmall));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(20);
				doc.add(para);	
				
				totalEmployee = totalEmployee+ Employee;	
				Employee = 0;
				
			}		
			
			para = new Paragraph(new Phrase("Total Number Of Employee :- "+totalEmployee,fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(10);
			doc.add(para);			
		
			//doc.newPage();
			doc.close();
			Cn.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}


	
	}

	public static void QualificationWise(String date, String type, String filePath, String imagepath){
		
		System.out.println("I am in Qualification Wise" + type);
		RepoartBean repBean  = new RepoartBean();
		try
		{
			Connection Cn = null;
			Statement st = null;
			ResultSet rs = null;
			ResultSet rs1 = null;

			ReportDAO.OpenCon("", "", "",repBean);
			Cn = repBean.getCn();
			
			int totalEmployee =0;
			int Employee = 0;
			String Query = "";
			String Query1 = "";
			
			LookupHandler lkhp = new LookupHandler();
			Rectangle rec = new  Rectangle(100,100);
			Document doc = new Document();
			PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filePath));
			
			ReportDAOPDF dao = new ReportDAOPDF();
			ReportDAOPDF.lable1="Date : ";
			Footer ftr = dao.new Footer(lable1);
			writer.setPageEvent(ftr);
			
			
			doc.open();
			doc.setPageSize(PageSize.A4);
			
			Font fbold = new Font(Font.TIMES_ROMAN,12,Font.BOLD);
			Font fsmall = new Font(Font.TIMES_ROMAN,10);
			Font FONT = new Font(Font.TIMES_ROMAN, 40, Font.NORMAL, new GrayColor(0.75f));
			Font  fontcolor = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(6, 8, 193));
			
			ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Business Bank",FONT), 297.5f, 421, 45);
			Image image = Image.getInstance(imagepath);
			image.scaleAbsolute(80f, 80f);
			image.setAbsolutePosition(10f, 740f);
			doc.add(image);
		
			Paragraph para = new Paragraph(new Phrase("Qualification Wise Employee List "+date.substring(3),fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);
			doc.add(para);
			
			Query = "select * from LOOKUP where LKP_CODE like 'ed'";
			//System.out.println("Query is@@@ " +Query);
			st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(Query);	
			
			int LKP_SRNO = 0;
			String LKP_DISC = "";
			
			while(rs.next())
			{
				LKP_SRNO = rs.getInt("LKP_SRNO");
				LKP_DISC = rs.getString("LKP_DISC");
				
			/*	Query1 =  " select EM.*, rtrim(EM.fname)+' '+rtrim(EM.mname)+' '+rtrim(EM.lname) NAME , ET.DESIG" +
						  " from EMPMAST EM ,EMPTRAN ET where EM.BGRP = "+LKP_SRNO+" AND EM.STATUS = 'A' AND ET.EMPNO = EM.EMPNO ";
				*/
				
				Query1="select e.empcode as empcode ,e.salute,rtrim(e.fname)+'  '+rtrim(e.mname)+' '+ " +
						"rtrim(e.lname) as name ,e.dob,e.doj,et.desig " +
						"from empmast e,QUAL q, EMPTRAN et  where " +
						" e.empno=q.empno and e.empno=et.empno and et.empno=q.empno and   q.PASSYEAR=(select max(PASSYEAR) from" +
						" qual where empno=q.empno ) and et.desig="+LKP_SRNO+" and e.EMPNO in " +
						"(select distinct  EMPNO from EMPMAST where STATUS='A') and " +
						"et.SRNO=(select MAX(srno) from EMPTRAN where EMPNO=e.EMPNO)  order by	EMPCODE";


			   
			
				System.out.println("Query1 is @@@ " +Query1);
				st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				rs1 = st.executeQuery(Query1);	
				
					para = new Paragraph(new Phrase("Employee List For :- "+ ( LKP_DISC ),fbold));
					para.setAlignment(Element.ALIGN_CENTER);
					para.setSpacingAfter(10);
					doc.add(para);
					
					//............................ Code To print Heading start here...................................
					PdfPTable main = new PdfPTable(5);
					String str[] = new String[5];
					
					str[0]="EMPCODE";			str[1]="EMPLOYEE NAME";			str[2]="Designation";
					str[3]="DOB";				str[4]="DOJ";		
					
					main.setWidthPercentage(new float[]{15,45,15,15,15f}, rec);
					main.setHorizontalAlignment(Element.ALIGN_CENTER);
					PdfPCell maincell ;	
					
					for(int i=0; i<str.length;i++ )
					{					
						maincell = new PdfPCell(new Phrase(str[i],fbold));
						maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
						main.addCell(maincell);
					}	
					
					doc.add(main);
					//......................... Code To print Heading Ends here......................................
					//......................... Code To print data Start here........................................
					rs1.beforeFirst();
					while(rs1.next())
					{
						PdfPTable main1 = new PdfPTable(5);
						main1.setWidthPercentage(new float[]{15,45,15,15,15f}, rec);
						main1.setHorizontalAlignment(Element.ALIGN_CENTER);	
						
						PdfPCell cell1 = new PdfPCell(new Phrase(rs1.getString("empcode"),fsmall));  
						cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell1);
						
						// Calling getLKP_Desc() Method for Salutation Description.
						PdfPCell cell3 = new PdfPCell(new Phrase(lkhp.getLKP_Desc("SALUTE", rs1.getInt("SALUTE"))+" "+rs1.getString("name"),fsmall));
						cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell3);
						
						PdfPCell cell4 = new PdfPCell(new Phrase(lkhp.getLKP_Desc("DESIG", rs1.getInt("DESIG")),fsmall));
						//PdfPCell cell4 = new PdfPCell(new Phrase("",fsmall));
						cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell4);
						
						PdfPCell cell5 = new PdfPCell(new Phrase(""+EmpOffHandler.dateFormat(rs1.getDate("DOB")),fsmall));
						cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell5);
						
						PdfPCell cell2 = new PdfPCell(new Phrase(""+EmpOffHandler.dateFormat(rs1.getDate("DOJ")),fsmall));
						cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell2);
						
						doc.add(main1);
						
						Employee+=1;	
					}     
					//......................... Code To print data In Table Ends here..............................
				para = new Paragraph(new Phrase("Number Of Employee :- "+Employee,fsmall));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(20);
				doc.add(para);	
				
				totalEmployee = totalEmployee+ Employee;	
				Employee = 0;
				
			}		
			
			para = new Paragraph(new Phrase("Total Number Of Employee :- "+totalEmployee,fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(10);
			doc.add(para);			
		
			//doc.newPage();
			doc.close();
			Cn.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}


	
	}

	public static void CategoryWise(String date, String type,String Category, String filePath, String imagepath){
		
		System.out.println("I am in Category Wise   " + type);
		RepoartBean repBean  = new RepoartBean();
		try
		{
			Connection Cn = null;
			Statement st = null;
			ResultSet rs = null;
			ResultSet rs1 = null;

			ReportDAO.OpenCon("", "", "",repBean);
			Cn = repBean.getCn();
			
			int totalEmployee =0;
			int Employee = 0;
			String Query = "";
			String Query1 = "";
			
			LookupHandler lkhp = new LookupHandler();
			
			Rectangle rec = new  Rectangle(105,100);
			Document doc = new Document();
			PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filePath));
			
			ReportDAOPDF dao = new ReportDAOPDF();
			ReportDAOPDF.lable1="Date : ";
			Footer ftr = dao.new Footer(lable1);
			writer.setPageEvent(ftr);
			
			doc.open();
			doc.setPageSize(PageSize.A4);
			
			Font fbold = new Font(Font.TIMES_ROMAN,8,Font.BOLD);
			Font fsmall = new Font(Font.TIMES_ROMAN,7);
			Font FONT = new Font(Font.TIMES_ROMAN, 35, Font.NORMAL, new GrayColor(0.75f));
			Font  fontcolor = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(6, 8, 193));
			
			ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Business Bank",FONT), 297.5f, 421, 45);
			Image image = Image.getInstance(imagepath);
			image.scaleAbsolute(50f, 50f);
			image.setAbsolutePosition(10f, 740f);
			doc.add(image);
			
			Paragraph para = new Paragraph(new Phrase("Category Wise Employee List "+date.substring(3),fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);
			doc.add(para);
			
			st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			int srno=0;
			
				if(Category.equalsIgnoreCase("All")){
				 Query1 = ""
						 + "select e.empcode,e.salute,rtrim(e.fname)+'  '+rtrim(e.mname)+' '+ "
						 + "rtrim(e.lname) as name ,e.DOJ,et.desig,l.LKP_DISC as qualification,lk.LKP_DISC as caste "
						 + "from empmast e,QUAL q, EMPTRAN et,LOOKUP l,LOOKUP lk "
						 + "where "
						 + " e.empno=q.empno "
						 + " and e.empno=et.empno "
						 + " and et.empno=q.empno "
						 + " and   q.SRNO=(select max(qq.SRNO) from qual qq  where qq.empno=q.empno) "
						 + "and e.STATUS='A' "
						 + "and et.SRNO=(select MAX(ee.srno) from EMPTRAN ee where ee.EMPNO=e.EMPNO) "
						 + " and l.LKP_SRNO = q.DEGREE "
						 + " and l.LKP_CODE = 'ED' "
						 + " and lk.LKP_SRNO = e.CATEGORYCD "
						 + " and lk.LKP_CODE = 'CATE' "
						 + "order by e.categorycd";
				}
				else{
					Query1 = ""
							+ "select e.empcode,e.salute,rtrim(e.fname)+'  '+rtrim(e.mname)+' '+ "
							+ "rtrim(e.lname) as name ,e.DOJ,et.desig,l.LKP_DISC as qualification,lk.LKP_DISC as caste "
							+ "from empmast e,QUAL q, EMPTRAN et,LOOKUP l,LOOKUP lk "
							+ "where "
							+ " e.empno=q.empno "
							+ " and e.empno=et.empno "
							+ " and et.empno=q.empno "
							+ " and   q.SRNO=(select max(qq.SRNO) from qual qq  where qq.empno=q.empno) "
							+ " and e.STATUS='A' "
							+ "and e.CATEGORYCD ="+Category+" "
							+ "and et.SRNO=(select MAX(ee.srno) from EMPTRAN ee where ee.EMPNO=e.EMPNO) "
							+ " and l.LKP_SRNO = q.DEGREE "
							+ " and l.LKP_CODE = 'ED' "
							+ " and lk.LKP_SRNO = e.CATEGORYCD "
							+ " and lk.LKP_CODE = 'CATE' "
							+ "order by e.categorycd";
				}
				
					System.out.println("Query1 is @@@ " +Query1);
					st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					rs1 = st.executeQuery(Query1);	
				
					para = new Paragraph(new Phrase("",fbold));
					para.setAlignment(Element.ALIGN_CENTER);
					para.setSpacingAfter(10);
					doc.add(para);
					
					if(rs1.next()){
					PdfPTable main = new PdfPTable(7);
					String str[] = new String[7];
					
					str[0]="SR.NO"; str[1]="EmpCode";	str[2]="Employee Name";			str[3]="Designation";
					str[4]="DOJ";	str[5]="Caste";	    str[6]="Qualification";		
					
					main.setWidthPercentage(new float[]{7,9,33,18,10,10,18f}, rec);
					main.setHorizontalAlignment(Element.ALIGN_CENTER);
					PdfPCell maincell ;	
					
					for(int i=0; i<str.length;i++ )
					{					
						maincell = new PdfPCell(new Phrase(str[i],fbold));
						maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
						main.addCell(maincell);
					}	
					
					doc.add(main);
					rs1.beforeFirst();
					while(rs1.next())
					{
						srno++;
						PdfPTable main1 = new PdfPTable(7);
						main1.setWidthPercentage(new float[]{7,9,33,18,10,10,18f}, rec);
						main1.setHorizontalAlignment(Element.ALIGN_LEFT);	
						
						PdfPCell cell0 = new PdfPCell(new Phrase(""+srno,fsmall));  
						cell0.setHorizontalAlignment(Element.ALIGN_LEFT);
						main1.addCell(cell0);
						
						PdfPCell cell1 = new PdfPCell(new Phrase(rs1.getString("empcode"),fsmall));  
						cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
						main1.addCell(cell1);
						
						PdfPCell cell3 = new PdfPCell(new Phrase(lkhp.getLKP_Desc("SALUTE", rs1.getInt("SALUTE"))+" "+rs1.getString("name"),fsmall));
						cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
						main1.addCell(cell3);
						
						PdfPCell cell4 = new PdfPCell(new Phrase(lkhp.getLKP_Desc("DESIG", rs1.getInt("DESIG")),fsmall));
						cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
						main1.addCell(cell4);
						
						PdfPCell cell5 = new PdfPCell(new Phrase(""+EmpOffHandler.dateFormat(rs1.getDate("DOJ")),fsmall));
						cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
						main1.addCell(cell5);
						
						PdfPCell cell2 = new PdfPCell(new Phrase(rs1.getString("caste"),fsmall));
						cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
						main1.addCell(cell2);
						
						PdfPCell cell21 = new PdfPCell(new Phrase(rs1.getString("qualification"),fsmall));
						cell21.setHorizontalAlignment(Element.ALIGN_LEFT);
						main1.addCell(cell21);
						
						doc.add(main1);
						
						Employee+=1;	
					}     
				para = new Paragraph(new Phrase("Total Number Of Employee :- "+Employee,fbold));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(20);
				doc.add(para);	
				
				totalEmployee = totalEmployee+ Employee;	
				Employee = 0;
		}
					else{
						System.out.println("no record found");
						para = new Paragraph(new Phrase("Record not found",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
						para.setAlignment(Element.ALIGN_CENTER);
						para.setSpacingAfter(5);
						doc.add(para);
					}
			
			doc.close();
			Cn.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void GenderWise(String date, String type, String filePath, String imagepath, String genderType,String EmpDesig,String EmpBranch,String EmpType){
		System.out.println("EmpDesig1: "+EmpDesig+"  EmpBranch1: "+EmpBranch+"  EmpType1: "+EmpType);
		System.out.println("I am in Gender Wise" + type);
		RepoartBean repBean  = new RepoartBean();
		try
		{
			Connection Cn = null;
			Statement st = null;
			ResultSet rs = null;
			ResultSet rs1 = null;

			ReportDAO.OpenCon("", "", "",repBean);
			Cn = repBean.getCn();
			
			int totalEmployee =0;
			int Employee = 0;
			String Query = "";
			String Query1 = "";
			
			LookupHandler lkhp = new LookupHandler();
			
			Rectangle rec = new  Rectangle(190,190);
			Document doc = new Document();
			PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filePath));
			
			ReportDAOPDF dao = new ReportDAOPDF();
			ReportDAOPDF.lable1="Date : ";
			Footer ftr = dao.new Footer(lable1);
			writer.setPageEvent(ftr);
			
			doc.open();
			doc.setPageSize(PageSize.A4);

			Font fbold = new Font(Font.TIMES_ROMAN,12,Font.BOLD);
			Font fsmall = new Font(Font.TIMES_ROMAN,8);
			Font FONT = new Font(Font.TIMES_ROMAN, 40, Font.NORMAL, new GrayColor(0.75f));
			Font  fontcolor = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(6, 8, 193));
			
			ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Business Bank",FONT), 297.5f, 421, 45);
			Image image = Image.getInstance(imagepath);
			image.scaleAbsolute(80f, 80f);
			image.setAbsolutePosition(10f, 740f);
			doc.add(image);
			
			Paragraph para = new Paragraph(new Phrase("Gender Wise Employee List "+date.substring(3),fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);
			doc.add(para);
			String constant="",gender="";
			if(genderType.equalsIgnoreCase("all") && EmpType.equalsIgnoreCase("0")){
				constant="";
				gender="Male-Female";
			}
			else if(genderType.equalsIgnoreCase("male") && EmpType.equalsIgnoreCase("0")){
				constant=" AND EM.GENDER = 'M' ";
				gender="Male";
			}else if(genderType.equalsIgnoreCase("female") && EmpType.equalsIgnoreCase("0")){
				constant=" AND EM.GENDER = 'F' ";
				gender="Female";
			}else if(genderType.equalsIgnoreCase("male") && EmpType.equalsIgnoreCase("1")){
				constant=" AND EM.GENDER = 'M' and ET.DESIG="+EmpDesig+" ";
				gender="Male Designation Wise";
			}else if(genderType.equalsIgnoreCase("female") && EmpType.equalsIgnoreCase("1")){
				constant=" AND EM.GENDER = 'F' and ET.DESIG="+EmpDesig+" ";
				gender="Female Designation Wise";
			}else if(genderType.equalsIgnoreCase("male") && EmpType.equalsIgnoreCase("2")){
				constant=" AND EM.GENDER = 'M' and ET.PRJ_SRNO="+EmpBranch+" ";
				gender="Male Branch Wise";
			}else if(genderType.equalsIgnoreCase("female") && EmpType.equalsIgnoreCase("2")){
				constant=" AND EM.GENDER = 'F' and ET.PRJ_SRNO="+EmpBranch+" ";
				gender="Female Branch Wise";
			}else if(genderType.equalsIgnoreCase("all") && EmpType.equalsIgnoreCase("1")){
				constant=" and ET.DESIG="+EmpDesig+"";
				gender=" Designation Wise";
			}else if(genderType.equalsIgnoreCase("all") && EmpType.equalsIgnoreCase("2")){
				constant="and ET.PRJ_SRNO="+EmpBranch+"";
				gender=" Branch Wise";
			}
			
			
			
	//		if(genderType.equalsIgnoreCase("all") && EmpType.equalsIgnoreCase("0")){
				//for all Male/female
				Query1 = " select EM.*, rtrim(EM.fname)+' '+rtrim(EM.mname)+' '+rtrim(EM.lname) NAME , ET.DESIG, p.SITE_NAME," 
						+ "         (SELECT TOP 1 lkp_disc "
						+ "         FROM   lookup lkp1 "
						+ "         WHERE  lkp1.lkp_srno = (SELECT q1.degree FROM   qual q1 WHERE  q1.srno = (SELECT TOP 1 q2.srno "
						+ "         FROM   qual q2 WHERE  q2.empno = q1.empno) "
						+ "         AND q1.empno = EM.empno) AND lkp1.lkp_code = 'ED') AS Qualification1 , "
						+ "         (SELECT TOP 1 lkp_disc "
						+ "         FROM   lookup lkp "
						+ "         WHERE  lkp.lkp_srno = (SELECT TOP 1 q1.degree FROM   qual q1 WHERE  q1.srno = (SELECT Max(q2.srno) "
						+ "         FROM   qual q2 WHERE  q2.empno = q1.empno) "
						+ "         AND q1.empno = EM.empno) AND lkp.lkp_code = 'ED') AS Qualification2"
						+"          from EMPMAST EM ,EMPTRAN ET,Project_Sites p " 
						+"          where  EM.STATUS = 'A' AND ET.EMPNO = EM.EMPNO  " +constant+ "  " 
						+"          and ET.SRNO=(select MAX(srno) from EMPTRAN where EMPNO=ET.empno AND ET.EFFDATE <= '"+ReportDAO.EOM(date)+"')" 
						+"          and ET.PRJ_SRNO=p.SITE_ID "
						+"          AND (( EM.STATUS='A' AND EM.DOJ <= '"+ReportDAO.EOM(date)+"')  or (EM.STATUS ='N' And  EM.DOL>='"+ReportDAO.BOM(date)+"' ))"
						+"          order by EM.EMPCODE";
			/*}else{
				String g = "";
				if(genderType.equalsIgnoreCase("female")){
					g="F";
				}else{
					g="M";
				} 
				Query1 =  " select EM.*, rtrim(EM.fname)+' '+rtrim(EM.mname)+' '+rtrim(EM.lname) NAME , ET.DESIG " +
						 " from EMPMAST EM ,EMPTRAN ET 		" +
						 " where EM.GENDER = '"+g+"'  AND EM.STATUS = 'A' AND ET.EMPNO = EM.EMPNO "+
						 " and ET.SRNO=(select MAX(srno) from EMPTRAN where EMPNO=ET.empno)";
			}
			*/
			
			
			
			System.out.println("Query is @@@ " +Query1);
			st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(Query1);	
			
			while(rs.next())
			{
				/*if(genderType.equalsIgnoreCase("all") && EmpType.equalsIgnoreCase("1") && genderType.equalsIgnoreCase("all") && EmpType.equalsIgnoreCase("1")){
					System.out.println("no record found");
					para = new Paragraph(new Phrase("Record not found",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
					para.setAlignment(Element.ALIGN_CENTER);
					para.setSpacingAfter(5);
					doc.add(para);
				}else{*/
				
			//	String gender = rs.getString("gender");  //=="F"?"Female":rs.getString("gender")=="M"?"Male":"";
				/*if(genderType.equalsIgnoreCase("all")){
					gender = "Male-Female";
				}
				else if(gender.equalsIgnoreCase("M")){
					gender = "Male";
				}else if(gender.equalsIgnoreCase("F")) {
					gender = "Female";
				}*/
					para = new Paragraph(new Phrase("Employee List For :- "+gender,fbold));
					para.setAlignment(Element.ALIGN_CENTER);
					para.setSpacingAfter(10);
					doc.add(para);
					
					//............................ Code To print Heading start here...................................
					PdfPTable main = new PdfPTable(7);
					String str[] = new String[7];
					
					str[0]="Empcode";			str[1]="Employee Name";	str[2]="Site Name";		str[3]="Designation";
					str[4]="Qualification"; str[5]="Dob";				str[6]="Doj";		
					
					main.setWidthPercentage(new float[]{21,50,25,27,27,20,20f}, rec);
					main.setHorizontalAlignment(Element.ALIGN_CENTER);
					PdfPCell maincell ;	
					
					for(int i=0; i<str.length;i++ )
					{					
						maincell = new PdfPCell(new Phrase(str[i],fbold));
						maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
						main.addCell(maincell);
					}	
					
					doc.add(main);
					//......................... Code To print Heading Ends here......................................
					
					rs.beforeFirst();
					
					while(rs.next()){
						
						//......................... Code To print data Start here........................................
						
						PdfPTable main1 = new PdfPTable(7);
						main1.setWidthPercentage(new float[]{21,50,25,27,27,20,20f}, rec);
						main1.setHorizontalAlignment(Element.ALIGN_CENTER);	
						
						PdfPCell cell1 = new PdfPCell(new Phrase(rs.getString("empcode"),fsmall));  
						cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell1);
						
						// Calling getLKP_Desc() Method for Salutation Description.
						PdfPCell cell3 = new PdfPCell(new Phrase(lkhp.getLKP_Desc("SALUTE", rs.getInt("SALUTE"))+" "+rs.getString("name"),fsmall));
						cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
						main1.addCell(cell3);
						
						PdfPCell cell4 = new PdfPCell(new Phrase(""+rs.getString("SITE_NAME"),fsmall));
						cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
						main1.addCell(cell4);
						
						PdfPCell cell41 = new PdfPCell(new Phrase(lkhp.getLKP_Desc("DESIG", rs.getInt("DESIG")),fsmall));
						//PdfPCell cell4 = new PdfPCell(new Phrase("",fsmall));
						cell41.setHorizontalAlignment(Element.ALIGN_LEFT);
						main1.addCell(cell41);
						
					if(rs.getString("Qualification1")==null||rs.getString("Qualification2")==null)
					{
						PdfPCell cell5 = new PdfPCell(new Phrase("",fsmall));
						cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
						main1.addCell(cell5);
						
						PdfPCell cell6 = new PdfPCell(new Phrase("",fsmall));
						cell6.setHorizontalAlignment(Element.ALIGN_LEFT);
						main1.addCell(cell6);
					}
					else{
						String q = rs.getString("Qualification1")==""?"":rs.getString("Qualification1");
						
						String qq = rs.getString("Qualification2")==""?"EDUCATION":rs.getString("Qualification2");
						
						if((q.equals(qq))){
							PdfPCell cell5 = new PdfPCell(new Phrase(""+(rs.getString("Qualification1")),fsmall));
							cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
							main1.addCell(cell5);
						}else{
							PdfPCell cell5 = new PdfPCell(new Phrase(""+(rs.getString("Qualification1")+" , "+(rs.getString("Qualification2"))),fsmall));
							cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
							main1.addCell(cell5);
						}
						
					}	
						PdfPCell cell51 = new PdfPCell(new Phrase(""+EmpOffHandler.dateFormat(rs.getDate("DOB")),fsmall));
						cell51.setHorizontalAlignment(Element.ALIGN_LEFT);
						main1.addCell(cell51);
						
						PdfPCell cell2 = new PdfPCell(new Phrase(""+EmpOffHandler.dateFormat(rs.getDate("DOJ")),fsmall));
						cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
						main1.addCell(cell2);
						
						doc.add(main1);
						
						Employee+=1;	
					   
					//......................... Code To print data In Table Ends here..............................
					}	
				para = new Paragraph(new Phrase("Number Of Employee :- "+Employee,fsmall));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(20);
				doc.add(para);	
				
				totalEmployee = totalEmployee+ Employee;	
				Employee = 0;
				
			}		
			
			para = new Paragraph(new Phrase("Total Number Of Employee :- "+totalEmployee,fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(10);
			doc.add(para);			
			
			//doc.newPage();
			doc.close();
			Cn.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}


	
	}
	
	

	/*public static void GenderWise(String date, String type, String filePath, String imagepath, String genderType,String EmpDesig,String EmpBranch,String EmpType){
		System.out.println("EmpDesig1: "+EmpDesig+"  EmpBranch1: "+EmpBranch+"  EmpType1: "+EmpType);
		System.out.println("I am in Gender Wise" + type);
		RepoartBean repBean  = new RepoartBean();
		try
		{
			Connection Cn = null;
			Statement st = null;
			ResultSet rs = null;
			ResultSet rs1 = null;

			ReportDAO.OpenCon("", "", "",repBean);
			Cn = repBean.getCn();
			
			int totalEmployee =0;
			int Employee = 0;
			String Query = "";
			String Query1 = "";
			
			LookupHandler lkhp = new LookupHandler();
			
			Rectangle rec = new  Rectangle(100,100);
			Document doc = new Document();
			PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filePath));
			
			ReportDAOPDF dao = new ReportDAOPDF();
			ReportDAOPDF.lable1="Date : ";
			Footer ftr = dao.new Footer(lable1);
			writer.setPageEvent(ftr);
			
			doc.open();
			doc.setPageSize(PageSize.A4);

			Font fbold = new Font(Font.TIMES_ROMAN,12,Font.BOLD);
			Font fsmall = new Font(Font.TIMES_ROMAN,10);
			Font FONT = new Font(Font.TIMES_ROMAN, 40, Font.NORMAL, new GrayColor(0.75f));
			Font  fontcolor = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(6, 8, 193));
			
			ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Business Bank",FONT), 297.5f, 421, 45);
			Image image = Image.getInstance(imagepath);
			image.scaleAbsolute(80f, 80f);
			image.setAbsolutePosition(10f, 740f);
			doc.add(image);
			
			Paragraph para = new Paragraph(new Phrase("Gender Wise Employee List "+date.substring(3),fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);
			doc.add(para);
			String constant="",gender="";
			if(genderType.equalsIgnoreCase("all") && EmpType.equalsIgnoreCase("0")){
				constant="";
				gender="Male-Female";
			}
			else if(genderType.equalsIgnoreCase("male") && EmpType.equalsIgnoreCase("0")){
				constant=" AND EM.GENDER = 'M' ";
				gender="Male";
			}else if(genderType.equalsIgnoreCase("female") && EmpType.equalsIgnoreCase("0")){
				constant=" AND EM.GENDER = 'F' ";
				gender="Female";
			}else if(genderType.equalsIgnoreCase("male") && EmpType.equalsIgnoreCase("1")){
				constant=" AND EM.GENDER = 'M' and ET.DESIG="+EmpDesig+" ";
				gender="Male Designation Wise";
			}else if(genderType.equalsIgnoreCase("female") && EmpType.equalsIgnoreCase("1")){
				constant=" AND EM.GENDER = 'F' and ET.DESIG="+EmpDesig+" ";
				gender="Female Designation Wise";
			}else if(genderType.equalsIgnoreCase("male") && EmpType.equalsIgnoreCase("2")){
				constant=" AND EM.GENDER = 'M' and ET.PRJ_SRNO="+EmpBranch+" ";
				gender="Male Branch Wise";
			}else if(genderType.equalsIgnoreCase("female") && EmpType.equalsIgnoreCase("2")){
				constant=" AND EM.GENDER = 'F' and ET.PRJ_SRNO="+EmpBranch+" ";
				gender="Female Branch Wise";
			}else if(genderType.equalsIgnoreCase("all") && EmpType.equalsIgnoreCase("1")){
				constant=" and ET.DESIG="+EmpDesig+"";
				gender=" Designation Wise";
			}else if(genderType.equalsIgnoreCase("all") && EmpType.equalsIgnoreCase("2")){
				constant="and ET.PRJ_SRNO="+EmpBranch+"";
				gender=" Branch Wise";
			}
			
			
			
	//		if(genderType.equalsIgnoreCase("all") && EmpType.equalsIgnoreCase("0")){
				//for all Male/female
				Query1 = " select EM.*, rtrim(EM.fname)+' '+rtrim(EM.mname)+' '+rtrim(EM.lname) NAME , ET.DESIG " +
						" from EMPMAST EM ,EMPTRAN ET " +
						" where  EM.STATUS = 'A' AND ET.EMPNO = EM.EMPNO  " +constant+ "  " +
						"and ET.SRNO=(select MAX(srno) from EMPTRAN where EMPNO=ET.empno)" +
						" order by EM.EMPCODE";
			}else{
				String g = "";
				if(genderType.equalsIgnoreCase("female")){
					g="F";
				}else{
					g="M";
				} 
				Query1 =  " select EM.*, rtrim(EM.fname)+' '+rtrim(EM.mname)+' '+rtrim(EM.lname) NAME , ET.DESIG " +
						 " from EMPMAST EM ,EMPTRAN ET 		" +
						 " where EM.GENDER = '"+g+"'  AND EM.STATUS = 'A' AND ET.EMPNO = EM.EMPNO "+
						 " and ET.SRNO=(select MAX(srno) from EMPTRAN where EMPNO=ET.empno)";
			}
			
			
			
			
			System.out.println("Query is @@@ " +Query1);
			st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(Query1);	
			
			while(rs.next())
			{
				if(genderType.equalsIgnoreCase("all") && EmpType.equalsIgnoreCase("1") && genderType.equalsIgnoreCase("all") && EmpType.equalsIgnoreCase("1")){
					System.out.println("no record found");
					para = new Paragraph(new Phrase("Record not found",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
					para.setAlignment(Element.ALIGN_CENTER);
					para.setSpacingAfter(5);
					doc.add(para);
				}else{
				
			//	String gender = rs.getString("gender");  //=="F"?"Female":rs.getString("gender")=="M"?"Male":"";
				if(genderType.equalsIgnoreCase("all")){
					gender = "Male-Female";
				}
				else if(gender.equalsIgnoreCase("M")){
					gender = "Male";
				}else if(gender.equalsIgnoreCase("F")) {
					gender = "Female";
				}
					para = new Paragraph(new Phrase("Employee List For :- "+gender,fbold));
					para.setAlignment(Element.ALIGN_CENTER);
					para.setSpacingAfter(10);
					doc.add(para);
					
					//............................ Code To print Heading start here...................................
					PdfPTable main = new PdfPTable(5);
					String str[] = new String[5];
					
					str[0]="EMPCODE";			str[1]="EMPLOYEE NAME";			str[2]="Designation";
					str[3]="DOB";				str[4]="DOJ";		
					
					main.setWidthPercentage(new float[]{15,45,15,15,15f}, rec);
					main.setHorizontalAlignment(Element.ALIGN_CENTER);
					PdfPCell maincell ;	
					
					for(int i=0; i<str.length;i++ )
					{					
						maincell = new PdfPCell(new Phrase(str[i],fbold));
						maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
						main.addCell(maincell);
					}	
					
					doc.add(main);
					//......................... Code To print Heading Ends here......................................
					
					rs.beforeFirst();
					while(rs.next()){
						
						//......................... Code To print data Start here........................................
						
						PdfPTable main1 = new PdfPTable(5);
						main1.setWidthPercentage(new float[]{15,45,15,15,15f}, rec);
						main1.setHorizontalAlignment(Element.ALIGN_CENTER);	
						
						PdfPCell cell1 = new PdfPCell(new Phrase(rs.getString("empcode"),fsmall));  
						cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell1);
						
						// Calling getLKP_Desc() Method for Salutation Description.
						PdfPCell cell3 = new PdfPCell(new Phrase(lkhp.getLKP_Desc("SALUTE", rs.getInt("SALUTE"))+" "+rs.getString("name"),fsmall));
						cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell3);
						
						PdfPCell cell4 = new PdfPCell(new Phrase(lkhp.getLKP_Desc("DESIG", rs.getInt("DESIG")),fsmall));
						//PdfPCell cell4 = new PdfPCell(new Phrase("",fsmall));
						cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell4);
						
						PdfPCell cell5 = new PdfPCell(new Phrase(""+EmpOffHandler.dateFormat(rs.getDate("DOB")),fsmall));
						cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell5);
						
						PdfPCell cell2 = new PdfPCell(new Phrase(""+EmpOffHandler.dateFormat(rs.getDate("DOJ")),fsmall));
						cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell2);
						
						doc.add(main1);
						
						Employee+=1;	
					   
					//......................... Code To print data In Table Ends here..............................
					}	
				para = new Paragraph(new Phrase("Number Of Employee :- "+Employee,fsmall));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(20);
				doc.add(para);	
				
				totalEmployee = totalEmployee+ Employee;	
				Employee = 0;
				
			}		
			
			para = new Paragraph(new Phrase("Total Number Of Employee :- "+totalEmployee,fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(10);
			doc.add(para);			
			
			//doc.newPage();
			doc.close();
			Cn.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}


	
	}*/
	
	
	
	/*public static void Extra_Duty_Payment_Report(String val, String type, String filePath, String imagepath){
		
		System.out.println("I am in Extra_Duty_Payment_Report " + type);
		RepoartBean repBean  = new RepoartBean();
		LookupHandler lkhp = new LookupHandler();
		try
		{
			Connection Cn = null;
			Statement st = null;
			ResultSet rs = null;

			ReportDAO.OpenCon("", "", "",repBean);
			Cn = repBean.getCn();
		
			String Query = "";
			int totalEmployee = 0;
			int Employee = 0;
			String empno[];
			double totalamount=0;
			double alltotal=0;
			
			Document doc = new Document();
			PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filePath));

			
			ReportDAOPDF dao = new ReportDAOPDF();
			ReportDAOPDF.lable1="Date : ";
			Footer ftr = dao.new Footer(lable1);
			writer.setPageEvent(ftr);
			doc.open();
			doc.setPageSize(PageSize.A4);
			
			Font fbold = new Font(Font.TIMES_ROMAN,12,Font.BOLD);
			Font fsmall = new Font(Font.TIMES_ROMAN,10);
			Font FONT = new Font(Font.TIMES_ROMAN, 40, Font.NORMAL, new GrayColor(0.75f));
			Font  fontcolor = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(6, 8, 193));
			
			ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Business Bank",FONT), 297.5f, 421, 45);
			
			Image image1 = Image.getInstance(imagepath);
			image1.scaleAbsolute(80f, 80f);
			image1.setAbsolutePosition(10f, 740f);
			doc.add(image1);
			
		

			Paragraph para = new Paragraph(new Phrase("Extra Duty Payment Report For :-" +val,fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			doc.add(para);

			Rectangle rec = new  Rectangle(135,100);
			PdfPTable table = null;

			table= new PdfPTable(6);	
			table.setSpacingBefore(6);
			table.setWidthPercentage(new float[]{17,40,35,10,16,17}, rec);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			if(type.equalsIgnoreCase("Empwise"))
			{
				empno=val.split(":");
				Query=("SELECT e.SALUTE,RTRIM(e.FNAME)+' '+RTRIM(e.MNAME)+' '+RTRIM(e.lname) AS NAME,e.EMPCODE,e1.* from EMPMAST e ,Extra_Duty_Payment e1 "+
							" WHERE e.EMPNO=e1.EMPNO AND e1.EMPNO="+empno[2]+" AND e1.STATUS='P' ORDER BY e.EMPCODE");
			}
			else
			{
				Query=("SELECT e.SALUTE,RTRIM(e.FNAME)+' '+RTRIM(e.MNAME)+' '+RTRIM(e.lname) AS NAME,e.EMPCODE,e1.* from EMPMAST e ,Extra_Duty_Payment e1 "+
						" WHERE e.EMPNO=e1.EMPNO AND CREATED_DATE BETWEEN '"+ReportDAO.BOM("01-"+val)+"' AND '"+ReportDAO.EOM("01-"+val)+"' AND e1.STATUS='P' ORDER BY e.EMPCODE");
			}
			System.out.println("hi...."+Query);
			st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(Query);
			
			if(rs.next())
			{			
				
				rs.previous();
				while(rs.next())
				{
					String empcode=rs.getString("EMPCODE");
					
					para = new Paragraph(new Phrase("EMPLOYEE NAME: "+rs.getString("NAME"),fsmall));
					para.setAlignment(Element.ALIGN_CENTER);
					para.setSpacingBefore(10);
					para.setSpacingAfter(5);
					doc.add(para);
					
					//............................ Code To print Heading start here...................................
					PdfPTable main = new PdfPTable(6);
					//main.setSpacingBefore(10);
					
					String str[] = new String[6];
					
					str[0]="EMPCODE";			str[1]="EMPLOYEE NAME";			str[2]="FOR DATE";
					str[3]="DAYS";				str[4]="AMOUNT";						str[5]="STATUS";		
					
					main.setWidthPercentage(new float[]{17,40,35,10,16,17}, rec);
					main.setHorizontalAlignment(Element.ALIGN_CENTER);
					PdfPCell maincell ;	
					
					for(int i=0; i<str.length;i++ )
					{					
						maincell = new PdfPCell(new Phrase(str[i],fbold));
						maincell.setHorizontalAlignment(Element.ALIGN_LEFT);
						main.addCell(maincell);
					}	
					doc.add(main);
					//......................... Code To print Heading Ends here......................................
					
					//......................... Code To print DATA Starts here......................................
					totalamount=0;
					while (empcode.equals(rs.getString("EMPCODE")))
					{					
						main = new PdfPTable(6);
						main.setWidthPercentage(new float[]{17,40,35,10,16,17}, rec);
						main.setHorizontalAlignment(Element.ALIGN_CENTER); 
						
						PdfPCell cell1 = new PdfPCell(new Phrase(""+rs.getString("EMPCODE"),fsmall)); 
						main.setHorizontalAlignment(Element.ALIGN_CENTER); 
						main.addCell(cell1);
						
						PdfPCell cell2 = new PdfPCell(new Phrase(""+lkhp.getLKP_Desc("SALUTE", rs.getInt("SALUTE"))+" "+rs.getString("NAME"),fsmall));
						main.setHorizontalAlignment(Element.ALIGN_LEFT); 
						main.addCell(cell2);
						
						PdfPCell cell3 = new PdfPCell(new Phrase(""+rs.getString("TRNDT"),fsmall));
						main.setHorizontalAlignment(Element.ALIGN_LEFT); 
						main.addCell(cell3);
						
						PdfPCell cell4 = new PdfPCell(new Phrase(""+rs.getString("TOTAL_DAYS") ,fsmall));
						main.setHorizontalAlignment(Element.ALIGN_CENTER); 
						main.addCell(cell4);
						
						PdfPCell cell5 = new PdfPCell(new Phrase(""+rs.getString("CALCULATED_AMOUNT"),fsmall));
						main.setHorizontalAlignment(Element.ALIGN_CENTER); 
						main.addCell(cell5);
						
						String status=rs.getString("STATUS").equalsIgnoreCase("P")?"POST":"CALCULATE";
						PdfPCell cell6 = new PdfPCell(new Phrase(""+status,fsmall));
						main.setHorizontalAlignment(Element.ALIGN_CENTER); 
						main.addCell(cell6);
						
						doc.add(main);
				
						totalamount+=Double.parseDouble(rs.getString("CALCULATED_AMOUNT"));
						alltotal+=Double.parseDouble(rs.getString("CALCULATED_AMOUNT"));
						if (!rs.next()) {
							//Employee+=1;	
							break;
						}
						if(!empcode.equals(rs.getString("EMPCODE"))){
							rs.previous();
							//Employee+=1;	
							break;
						}
						
							
					}
					Employee+=1;
					//......................... Code To print DATA Starts here......................................
					para = new Paragraph(new Phrase("Total_Amount :- "+totalamount,fsmall));
					para.setAlignment(Element.ALIGN_LEFT);
					para.setSpacingAfter(10);
					doc.add(para);
					
					//totalEmployee = totalEmployee + Employee;
					//Employee = 0;
				}
				para = new Paragraph(new Phrase("Total No Of Employee :- "+Employee,fbold));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(10);
				doc.add(para);
				
				para = new Paragraph(new Phrase("Total Amount :- "+alltotal,fbold));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(10);
				doc.add(para);
			}
			else
			{
				System.out.println("no record found");
				PdfPCell c1 = new PdfPCell(new Phrase("Record is Not found for "+val,fsmall));  
				c1.setColspan(6);
				
				PdfPTable main = new PdfPTable(6);
				main.setWidthPercentage(new float[]{17,40,35,10,16,17}, rec);
				main.setHorizontalAlignment(Element.ALIGN_CENTER);
				main.addCell(c1);
				main.setSpacingBefore(10);
				doc.add(main);
			}
			doc.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}

	}*/

public static void Extra_Duty_Payment_Report(String val, String type, String filePath, String imagepath){
		
		System.out.println("I am in Extra_Duty_Payment_Report " + type);
		RepoartBean repBean  = new RepoartBean();
		LookupHandler lkhp = new LookupHandler();
		try
		{
			Connection Cn = null;
			Statement st = null;
			ResultSet rs = null;

			ReportDAO.OpenCon("", "", "",repBean);
			Cn = repBean.getCn();
		
			String Query = "";
			int totalEmployee = 0;
			int Employee = 0;
			String empno[];
			double totalamount=0;
			double alltotal=0;
			
			Document doc = new Document();
			PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filePath));

			
			ReportDAOPDF dao = new ReportDAOPDF();
			ReportDAOPDF.lable1="Date : ";
			Footer ftr = dao.new Footer(lable1);
			writer.setPageEvent(ftr);
			doc.open();
			doc.setPageSize(PageSize.A4);
			
			Font fbold = new Font(Font.TIMES_ROMAN,12,Font.BOLD);
			Font fsmall = new Font(Font.TIMES_ROMAN,10);
			Font FONT = new Font(Font.TIMES_ROMAN, 40, Font.NORMAL, new GrayColor(0.75f));
			Font  fontcolor = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(6, 8, 193));
			
			ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Business Bank",FONT), 297.5f, 421, 45);
			
			Image image1 = Image.getInstance(imagepath);
			image1.scaleAbsolute(80f, 80f);
			image1.setAbsolutePosition(10f, 740f);
			doc.add(image1);
			
		

			Paragraph para = new Paragraph(new Phrase("Extra Duty Payment Report For :-" +val,fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			doc.add(para);

			Rectangle rec = new  Rectangle(135,100);
			PdfPTable table = null;

			table= new PdfPTable(6);	
			table.setSpacingBefore(6);
			table.setWidthPercentage(new float[]{17,40,35,10,16,17}, rec);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			if(type.equalsIgnoreCase("Empwise"))
			{
				empno=val.split(":");
				Query=("SELECT e.SALUTE,RTRIM(e.FNAME)+' '+RTRIM(e.MNAME)+' '+RTRIM(e.lname) AS NAME,e.EMPCODE,e1.* from EMPMAST e ,Extra_Duty_Payment e1 "+
							" WHERE e.EMPNO=e1.EMPNO AND e1.EMPNO="+empno[2]+" AND e1.STATUS='P' ORDER BY e.EMPCODE");
			}
			else
			{
				Query=("SELECT e.SALUTE,RTRIM(e.FNAME)+' '+RTRIM(e.MNAME)+' '+RTRIM(e.lname) AS NAME,e.EMPCODE,e1.* from EMPMAST e ,Extra_Duty_Payment e1 "+
						" WHERE e.EMPNO=e1.EMPNO AND CREATED_DATE BETWEEN '"+ReportDAO.BOM("01-"+val)+"' AND '"+ReportDAO.EOM("01-"+val)+"' AND e1.STATUS='P' ORDER BY e.EMPCODE");
			}
			System.out.println("hi...."+Query);
			st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(Query);
			
			if(rs.next())
			{			
				
				rs.previous();
				while(rs.next())
				{
					String empcode=rs.getString("EMPCODE");
					
					para = new Paragraph(new Phrase("EMPLOYEE NAME: "+rs.getString("NAME"),fsmall));
					para.setAlignment(Element.ALIGN_CENTER);
					para.setSpacingBefore(10);
					para.setSpacingAfter(5);
					doc.add(para);
					
					//............................ Code To print Heading start here...................................
					PdfPTable main = new PdfPTable(6);
					//main.setSpacingBefore(10);
					
					String str[] = new String[6];
					
					str[0]="EMPCODE";			str[1]="EMPLOYEE NAME";			str[2]="FOR DATE";
					str[3]="DAYS";				str[4]="AMOUNT";						str[5]="STATUS";		
					
					main.setWidthPercentage(new float[]{17,40,35,10,16,17}, rec);
					main.setHorizontalAlignment(Element.ALIGN_CENTER);
					PdfPCell maincell ;	
					
					for(int i=0; i<str.length;i++ )
					{					
						maincell = new PdfPCell(new Phrase(str[i],fbold));
						maincell.setHorizontalAlignment(Element.ALIGN_LEFT);
						main.addCell(maincell);
					}	
					doc.add(main);
					//......................... Code To print Heading Ends here......................................
					
					//......................... Code To print DATA Starts here......................................
					totalamount=0;
					while (empcode.equals(rs.getString("EMPCODE")))
					{					
						main = new PdfPTable(6);
						main.setWidthPercentage(new float[]{17,40,35,10,16,17}, rec);
						main.setHorizontalAlignment(Element.ALIGN_CENTER); 
						
						PdfPCell cell1 = new PdfPCell(new Phrase(""+rs.getString("EMPCODE"),fsmall)); 
						main.setHorizontalAlignment(Element.ALIGN_CENTER); 
						main.addCell(cell1);
						
						PdfPCell cell2 = new PdfPCell(new Phrase(""+lkhp.getLKP_Desc("SALUTE", rs.getInt("SALUTE"))+" "+rs.getString("NAME"),fsmall));
						main.setHorizontalAlignment(Element.ALIGN_LEFT); 
						main.addCell(cell2);
						
						PdfPCell cell3 = new PdfPCell(new Phrase(""+rs.getString("TRNDT"),fsmall));
						main.setHorizontalAlignment(Element.ALIGN_LEFT); 
						main.addCell(cell3);
						
						PdfPCell cell4 = new PdfPCell(new Phrase(""+rs.getString("TOTAL_DAYS") ,fsmall));
						main.setHorizontalAlignment(Element.ALIGN_CENTER); 
						main.addCell(cell4);
						
						PdfPCell cell5 = new PdfPCell(new Phrase(""+rs.getString("CALCULATED_AMOUNT"),fsmall));
						main.setHorizontalAlignment(Element.ALIGN_CENTER); 
						main.addCell(cell5);
						
						String status=rs.getString("STATUS").equalsIgnoreCase("P")?"POST":"CALCULATE";
						PdfPCell cell6 = new PdfPCell(new Phrase(""+status,fsmall));
						main.setHorizontalAlignment(Element.ALIGN_CENTER); 
						main.addCell(cell6);
						
						doc.add(main);
				
						totalamount+=Double.parseDouble(rs.getString("CALCULATED_AMOUNT"));
						alltotal+=Double.parseDouble(rs.getString("CALCULATED_AMOUNT"));
						if (!rs.next()) {
							//Employee+=1;	
							break;
						}
						if(!empcode.equals(rs.getString("EMPCODE"))){
							rs.previous();
							//Employee+=1;	
							break;
						}
						
							
					}
					Employee+=1;
					//......................... Code To print DATA Starts here......................................
					para = new Paragraph(new Phrase("Total_Amount :- "+totalamount,fsmall));
					para.setAlignment(Element.ALIGN_LEFT);
					para.setSpacingAfter(10);
					doc.add(para);
					
					//totalEmployee = totalEmployee + Employee;
					//Employee = 0;
				}
				para = new Paragraph(new Phrase("Total No Of Employee :- "+Employee,fbold));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(10);
				doc.add(para);
				
				para = new Paragraph(new Phrase("Total Amount :- "+alltotal,fbold));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(10);
				doc.add(para);
			}
			else
			{
				System.out.println("no record found");
				PdfPCell c1 = new PdfPCell(new Phrase("Record is Not found for "+val,fsmall));  
				c1.setColspan(6);
				
				PdfPTable main = new PdfPTable(6);
				main.setWidthPercentage(new float[]{17,40,35,10,16,17}, rec);
				main.setHorizontalAlignment(Element.ALIGN_CENTER);
				main.addCell(c1);
				main.setSpacingBefore(10);
				doc.add(main);
			}
			doc.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}

	}

public static void BranchEmpSummery(String date, String type, String filePath, String imagepath)
{
		
		System.out.println("I am branchempsummery report Dao" + type);
		RepoartBean repBean  = new RepoartBean();
		try
		{
			Connection Cn = null;
			Statement st = null;
			Statement st1=null;
			ResultSet rs = null;
			ResultSet rs1 = null;

			ReportDAO.OpenCon("", "", "",repBean);
			Cn = repBean.getCn();
			
			int a=0;
			int b=0;
			int c=0;
			int d=0;
			int e=0;
			int f=0;
			int g=0;
			int h=0;
			int ii=0;
			int jj=0;
			int k=0;
			int l=0;
			int m=0;
			
			int totalEmployee =0;
			int Employee = 0;
			String Query = "";
			String Query1 = "";
			
			LookupHandler lkhp = new LookupHandler();
			
			Rectangle rec = new  Rectangle(295,300);
			Document doc = new Document();
			PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filePath));
			
			ReportDAOPDF dao = new ReportDAOPDF();
			ReportDAOPDF.lable1="Date : ";
			Footer ftr = dao.new Footer(lable1);
			writer.setPageEvent(ftr);
			
			doc.open();
			doc.setPageSize(PageSize.A4);

			Font fbold = new Font(Font.TIMES_ROMAN,8,Font.BOLD);
			Font fsmall = new Font(Font.TIMES_ROMAN,8);
			Font FONT = new Font(Font.TIMES_ROMAN, 40, Font.NORMAL, new GrayColor(0.75f));
			Font  fontcolor = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(6, 8, 193));
			
			ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Business Bank",FONT), 297.5f, 421, 45);
			Image image = Image.getInstance(imagepath);
			image.scaleAbsolute(80f, 80f);
			image.setAbsolutePosition(10f, 740f);
			doc.add(image);
			
			Paragraph para = new Paragraph(new Phrase("Designation Wise Summary Position In Month :- "+ReportDAO.EOM(date),fsmall));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(5);
			doc.add(para);
			
			
			Query1 =  "SELECT  LKP_DISC FROM LOOKUP WHERE LKP_CODE='DESIG' and LKP_SRNO<>0 order by LKP_SRNO";
			
			
			System.out.println("Query is @@@ " +Query1);
			st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(Query1);
			
			int totrow=0;
			while(rs.next())
			{
				
				totrow++;
				
			}
			int srno=0;
			String desig[] = new String[totrow];
			
				//............................ Code To print Heading start here...................................
					PdfPTable main = new PdfPTable(3+totrow);
					
					String str[] = new String[2];
					
					str[0]="BR.CD";			str[1]="BRANCH NAME";			
					main.setWidthPercentage(new float[]{17,62,16,16,16,16,16,16,22,22,22,21,15,15,23,22f}, rec);
					main.setHorizontalAlignment(Element.ALIGN_CENTER);
					PdfPCell maincell ;	
					
					for(int i=0; i<str.length;i++ )
					{			
						maincell = new PdfPCell(new Phrase(str[i],fbold));
						maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
						main.addCell(maincell);
					}	
					rs.beforeFirst();
					while(rs.next())
					{
						String headname=rs.getString("LKP_DISC").equalsIgnoreCase("C.E.O.")?"C.E.O":
													rs.getString("LKP_DISC").equalsIgnoreCase("GENERAL MANAGER")?"G.M":
														rs.getString("LKP_DISC").equalsIgnoreCase("CHIEF MANAGER")?"C.M":
															rs.getString("LKP_DISC").equalsIgnoreCase("MANAGER")?"MGR":
																rs.getString("LKP_DISC").equalsIgnoreCase("ASSISTANT MANAGER")?"A.M":
																	rs.getString("LKP_DISC").equalsIgnoreCase("ASSISTANT")?"ASSI":
																		rs.getString("LKP_DISC").equalsIgnoreCase("OFFICE ASSISTANT")?"O.ASSI":
																			rs.getString("LKP_DISC").equalsIgnoreCase("OFFICE ASSISTANT DRIVER")?"O.A.DRI":
																				rs.getString("LKP_DISC").equalsIgnoreCase("OFFICE ASSISTANT WATCHMAN")?"O.A.WAT":
																					rs.getString("LKP_DISC").equalsIgnoreCase("DEPUTY GENERAL MANAGER")?"D.G.M":
																						rs.getString("LKP_DISC").equalsIgnoreCase("ASSISTANT GENERAL MANAGER")?"A.G.M":rs.getString("LKP_DISC");
						maincell = new PdfPCell(new Phrase(headname,fbold));
						maincell.setHorizontalAlignment(Element.ALIGN_LEFT);
						main.addCell(maincell);
						
					}
					
					maincell = new PdfPCell(new Phrase("TOTAL",fbold));
					maincell.setHorizontalAlignment(Element.ALIGN_LEFT);
					main.addCell(maincell);
					
					doc.add(main);
					//......................... Code To print Heading Ends here......................................
					
					String summeryquery=("DECLARE @cols AS NVARCHAR(MAX),@query  AS NVARCHAR(MAX)  " +
							" select @cols  = STUFF((SELECT ','+QUOTENAME(lkp_disc) from lookup " +
							" where LKP_CODE  = 'DESIG' and LKP_SRNO <>0 order by LKP_SRNO " +
							" FOR XML PATH(''), TYPE).value('.', 'NVARCHAR(MAX)') ,1,1,'') " +
							
							" set @query = 'SELECT SITE_ID,SITE_NAME,' + @cols + ' from " +
							" (select SITE_ID,SITE_NAME, LKP_DISC ,count(*) as xcount " +
							" from EMPTRAN  e ,LOOKUP  l,Project_Sites p , empmast m " +
							" where l.LKP_SRNO = e.DESIG and l.LKP_CODE  = ''DESIG'' " +
							" and e.PRJ_SRNO = p.SITE_ID and m.EMPNO = e.EMPNO  " +
			//				" and e.PRJ_SRNO = p.SITE_ID and m.EMPNO = e.EMPNO and m.STATUS = ''A'' " +
							" AND (( m.STATUS=''A'' AND m.DOJ <= ''"+ReportDAO.EOM(date)+"'')  or (m.STATUS =''N'' And  m.DOL>=''"+ReportDAO.BOM(date)+"'' ))" +
							" and e.SRNO = ( select MAX(srno) from EMPTRAN e2 where e2.EMPNO = e.EMPNO AND E2.EFFDATE <= ''"+ReportDAO.EOM(date)+"'') " +
							" group by p.SITE_ID,p.SITE_NAME, l.LKP_DISC " +
							" ) x " +
							
							" pivot " +
							" ( " +
							" sum(xcount) " +
							" for LKP_DISC in (' + @cols + ') " +
							" ) p order by SITE_ID ' " +
							
							" execute(@query);");
					st1 = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					rs1 = st1.executeQuery(summeryquery);
					System.out.println("summeryquery:  "+summeryquery);
					while(rs1.next()){
						
						//......................... Code To print data Start here........................................
						int sitetot=0;
						PdfPTable main1 = new PdfPTable(3+totrow);
						
						main1.setWidthPercentage(new float[]{17,62,16,16,16,16,16,16,22,22,22,21,15,15,23,22f}, rec);
						main1.setHorizontalAlignment(Element.ALIGN_CENTER);	
						
						PdfPCell cell1 = new PdfPCell(new Phrase(rs1.getString("SITE_ID"),fsmall));  
						cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(cell1);
						
						// Calling getLKP_Desc() Method for Salutation Description.
						PdfPCell cell3 = new PdfPCell(new Phrase(rs1.getString("SITE_NAME"),fsmall));
						cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
						main1.addCell(cell3);
						
						rs.beforeFirst();
						while(rs.next())
						{
							PdfPCell cell5 = new PdfPCell(new Phrase(""+rs1.getString(rs.getString("LKP_DISC"))==null?"":rs1.getString(rs.getString("LKP_DISC")),fsmall));
							cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
							main1.addCell(cell5);
							if(rs.getString("LKP_DISC").equalsIgnoreCase("C.E.O."))
							{
								a+=rs1.getInt(rs.getString("LKP_DISC"));
							}else if(rs.getString("LKP_DISC").equalsIgnoreCase("GENERAL MANAGER"))
							{
								b+=rs1.getInt(rs.getString("LKP_DISC"));
							}else if(rs.getString("LKP_DISC").equalsIgnoreCase("CHIEF MANAGER"))
							{
								c+=rs1.getInt(rs.getString("LKP_DISC"));
							}else if(rs.getString("LKP_DISC").equalsIgnoreCase("MANAGER"))
							{
								d+=rs1.getInt(rs.getString("LKP_DISC"));
							}else if(rs.getString("LKP_DISC").equalsIgnoreCase("ASSISTANT MANAGER"))
							{
								e+=rs1.getInt(rs.getString("LKP_DISC"));
							}else if(rs.getString("LKP_DISC").equalsIgnoreCase("ASSISTANT"))
							{
								f+=rs1.getInt(rs.getString("LKP_DISC"));
							}else if(rs.getString("LKP_DISC").equalsIgnoreCase("OFFICE ASSISTANT"))
							{
								g+=rs1.getInt(rs.getString("LKP_DISC"));
							}else if(rs.getString("LKP_DISC").equalsIgnoreCase("OFFICE ASSISTANT DRIVER"))
							{
								h+=rs1.getInt(rs.getString("LKP_DISC"));
							}else if(rs.getString("LKP_DISC").equalsIgnoreCase("OFFICE ASSISTANT WATCHMAN"))
							{
								ii+=rs1.getInt(rs.getString("LKP_DISC"));
							}else if(rs.getString("LKP_DISC").equalsIgnoreCase("DEPUTY GENERAL MANAGER"))
							{
								jj+=rs1.getInt(rs.getString("LKP_DISC"));
							}else if(rs.getString("LKP_DISC").equalsIgnoreCase("ASSISTANT GENERAL MANAGER"))
							{
								k+=rs1.getInt(rs.getString("LKP_DISC"));
							}
							
							sitetot+=rs1.getInt(rs.getString("LKP_DISC"));
							
						}
						totalEmployee+=sitetot;
						l+=sitetot;
						PdfPCell celltot = new PdfPCell(new Phrase(""+sitetot,fsmall));  
						celltot.setHorizontalAlignment(Element.ALIGN_CENTER);
						main1.addCell(celltot);
						
						doc.add(main1);
						
							
					   
					//......................... Code To print data In Table Ends here..............................
					}	
					
					
					PdfPTable maintot = new PdfPTable(3+totrow);
					maintot.setWidthPercentage(new float[]{17,62,16,16,16,16,16,16,22,22,22,22,22,15,15,22f}, rec);
					
					maintot.setHorizontalAlignment(Element.ALIGN_CENTER);	
					maintot.setSpacingBefore(5);
					
					PdfPCell cell1 = new PdfPCell(new Phrase("TOTAL - ",fsmall));  
					cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell1.setColspan(2);
					maintot.addCell(cell1);
					
					PdfPCell cella = new PdfPCell(new Phrase(""+a,fsmall));  
					cella.setHorizontalAlignment(Element.ALIGN_CENTER);
					maintot.addCell(cella);
					
					PdfPCell cellb = new PdfPCell(new Phrase(""+b,fsmall));  
					cellb.setHorizontalAlignment(Element.ALIGN_CENTER);
					maintot.addCell(cellb);
					
					PdfPCell cellc = new PdfPCell(new Phrase(""+c,fsmall));  
					cellc.setHorizontalAlignment(Element.ALIGN_CENTER);
					maintot.addCell(cellc);
					
					PdfPCell celld = new PdfPCell(new Phrase(""+d,fsmall));  
					celld.setHorizontalAlignment(Element.ALIGN_CENTER);
					maintot.addCell(celld);
					
					PdfPCell celle = new PdfPCell(new Phrase(""+e,fsmall));  
					celle.setHorizontalAlignment(Element.ALIGN_CENTER);
					maintot.addCell(celle);
					
					PdfPCell cellf = new PdfPCell(new Phrase(""+f,fsmall));  
					cellf.setHorizontalAlignment(Element.ALIGN_CENTER);
					maintot.addCell(cellf);
					
					PdfPCell cellg = new PdfPCell(new Phrase(""+g,fsmall));  
					cellg.setHorizontalAlignment(Element.ALIGN_CENTER);
					maintot.addCell(cellg);
					
					PdfPCell cellh = new PdfPCell(new Phrase(""+h,fsmall));  
					cellh.setHorizontalAlignment(Element.ALIGN_CENTER);
					maintot.addCell(cellh);
					
					PdfPCell cellii = new PdfPCell(new Phrase(""+ii,fsmall));  
					cellii.setHorizontalAlignment(Element.ALIGN_CENTER);
					maintot.addCell(cellii);
					
					PdfPCell celljj = new PdfPCell(new Phrase(""+jj,fsmall));  
					celljj.setHorizontalAlignment(Element.ALIGN_CENTER);
					maintot.addCell(celljj);
					
					PdfPCell cellk = new PdfPCell(new Phrase(""+k,fsmall));  
					cellk.setHorizontalAlignment(Element.ALIGN_CENTER);
					maintot.addCell(cellk);
					
					
					PdfPCell celll = new PdfPCell(new Phrase(""+l,fsmall));  
					celll.setHorizontalAlignment(Element.ALIGN_CENTER);
					maintot.addCell(celll);
					doc.add(maintot);
					
				/*para = new Paragraph(new Phrase("Total Number Of Employee :- "+totalEmployee,fbold));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingBefore(10);
				doc.add(para);	*/
				
				/*totalEmployee = totalEmployee+ Employee;	
				Employee = 0;*/
			doc.newPage();
			doc.close();
			Cn.close();
			}		
			
			/*para = new Paragraph(new Phrase("Total Number Of Employee :- "+totalEmployee,fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(10);
			doc.add(para);*/			
		
			
		
		catch(Exception e) 
		{
			e.printStackTrace();
		}

	}
public static void Desig_Wise_PromotionList(String fromdate,String todate,String BranchType,String per_branch,String branchfrom,String branchto,String filepath,String imagepath)throws IOException, SQLException, DocumentException
{
	 long millis=System.currentTimeMillis();  
	 java.sql.Date date=new java.sql.Date(millis);  
	 System.out.println("date..."+fromdate);
	 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	 String reportDate = df.format(date);
	 String FrmDate = "";
	 String ToDate = "";
	 RepoartBean repBean  = new RepoartBean();
	 Document doc = new Document();
	try
	{
		 SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		 SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
		 
		    try {
				 FrmDate = output.format(sdf.parse(fromdate));
			     ToDate = output.format(sdf.parse(todate));
			     System.out.println("fromdate..."+FrmDate+" todate: "+ToDate);
			} catch (java.text.ParseException e1) {
				e1.printStackTrace();
			}
		int totalemp=0;
		Connection Cn = null;
		Statement st = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet emp = null;

		ReportDAO.OpenCon("", "", "",repBean);
		Cn = repBean.getCn();
		
		int totalEmployee =0;
		int Employee = 0;
		String Query = "";
		String Query1 = "";
		
		LookupHandler lkhp = new LookupHandler();
		Rectangle rec = new  Rectangle(119,100);
		PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filepath));
		
		doc.open();
		doc.setPageSize(PageSize.A4);
		
		Font fbold = new Font(Font.TIMES_ROMAN,8,Font.BOLD);
		Font fsmall = new Font(Font.TIMES_ROMAN,7);
		Font FONT = new Font(Font.TIMES_ROMAN, 27, Font.NORMAL, new GrayColor(0.75f));
		Font  fontcolor = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(6, 8, 193));
		
		ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Business Bank",FONT), 297.5f, 421, 45);
		Image image1 = Image.getInstance(imagepath);
		Phrase title = new Phrase("THE BUSINESS CO.OP BANK LTD.",new Font(FONT.TIMES_ROMAN,8,Font.BOLD));
		Paragraph para = new Paragraph(title);
		para.setAlignment(Element.ALIGN_CENTER);
		para.setSpacingBefore(0);

		image1.scaleAbsolute(60f,50f);
		image1.setAbsolutePosition(40f, 750f);
		doc.add(image1);
		doc.add(para);
		para = new Paragraph(new Phrase("Rajan Complex Arcade, Datta Mandir, Nashik Road, Nashik - 422101",new Font(Font.TIMES_ROMAN,8)));
		para.setAlignment(Element.ALIGN_CENTER);
		para.setSpacingAfter(0);

		doc.add(para);
		para = new Paragraph(new Phrase("Tel : +91-20 26812190",new Font(Font.TIMES_ROMAN,8)));
		para.setAlignment(Element.ALIGN_CENTER);
		para.setSpacingAfter(0);

		doc.add(para);
		para = new Paragraph(new Phrase("Email :",new Font(Font.TIMES_ROMAN,8)));
		para.setAlignment(Element.ALIGN_CENTER);
		para.setSpacingAfter(0);

		doc.add(para);
		para = new Paragraph(new Phrase("Employees Promotion List " ,new Font(Font.TIMES_ROMAN,8)));
		para.setAlignment(Element.ALIGN_CENTER);
		para.setSpacingAfter(0);
		doc.add(para);
		para = new Paragraph(new Phrase("Date:-" + reportDate,new Font(Font.TIMES_ROMAN,8)));
		para.setAlignment(Element.ALIGN_CENTER);
		para.setSpacingAfter(0);
		doc.add(para);
		int srno = 0;
		stmt = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		/*String GetBranch = "select SITE_ID as branch,SITE_NAME  from Project_Sites";
		ResultSet rspt0 = stmt.executeQuery(GetBranch);
		System.out.println("GetBranch "+GetBranch);*/
		
		if(BranchType.equalsIgnoreCase("1"))
		{
			Query1 = ""
					+ "SELECT e1.empno,T.TRNCD, "
					+ "  e1.empcode, "
					+ "  e1.fname, "
					+ "  e1.mname, "
					+ "  e1.lname, "
					+ "  T.prj_srno, "
					+ "  p.site_name, "
					+ "  T.effdate, "
					+ "  l.lkp_disc, "
					+ "  T.desig, "
					+ "  T.srno "
					+ "  FROM   empmast e1, "
					+ "  emptran T, "
					+ " project_sites p, "
					+ " lookup l "
					+ "  WHERE  T.empno = e1.empno "
					+ "  AND T.prj_srno = p.site_id "
					+ "  AND e1.status = 'A' "
					+ "  AND l.lkp_srno = T.desig "
					+ "  AND l.lkp_code = 'DESIG' "
					+ "  AND T.srno = (SELECT Max(srno) "
					+ "  FROM   emptran c "
					+ "  WHERE  c.empno = e1.empno AND c.trncd = 50) "
					+ "  AND T.effdate BETWEEN '"+FrmDate+"' and '"+ToDate+"' "
					+ "  ORDER  BY t.PRJ_SRNO,t.DESIG";
	//				Query1 = ""
						/*	+ "with promotion as "
							+ " (select e1.EMPNO,e1.empcode,e1.fname,e1.MNAME,e1.LNAME, T.PRJ_SRNO,p.site_name,T.EFFDATE,l.LKP_DISC,T.DESIG,T.SRNO "
							+ "FROM   empmast e1,emptran T,Project_Sites p ,LOOKUP l "
							+ "where  T.empno = e1.empno "
							+ "and T.PRJ_SRNO = p.SITE_ID and e1.STATUS = 'A' "
							+ "and l.LKP_SRNO = T.DESIG "
							+ "and l.LKP_CODE = 'DESIG' "
							+ "and T.SRNO = (select MAX(SRNO) from emptran c where c.EMPNO = T.EMPNO ) "
							+ "and T.EFFDATE between '"+FrmDate+"' and '"+ToDate+"' "
							+ "), "
							+ "trans as "
							+ "(select e1.EMPNO,e1.empcode,T.DESIG "
							+ "FROM   empmast e1,emptran T "
							+ "where  T.empno = e1.empno "
							+ "and T.SRNO = (select MAX(SRNO)-1  from emptran c where c.EMPNO = T.EMPNO) )"
							+ "select t.empcode,t.fname,t.MNAME,t.LNAME,t.site_name,t.EFFDATE,t.LKP_DISC,t.desig,t.EMPNO, t.PRJ_SRNO,"
							+ " l.desig from promotion t ,trans l "
							+ " where t.desig!=l.desig and t.EMPNO = l.EMPNO ORDER BY t.PRJ_SRNO,t.DESIG";*/	
							
		 }
		else if(BranchType.equalsIgnoreCase("2"))
		{
			Query1 = ""
					+ "SELECT e1.empno,T.TRNCD, "
					+ "  e1.empcode, "
					+ "  e1.fname, "
					+ "  e1.mname, "
					+ "  e1.lname, "
					+ "  T.prj_srno, "
					+ "  p.site_name, "
					+ "  T.effdate, "
					+ "  l.lkp_disc, "
					+ "  T.desig, "
					+ "  T.srno "
					+ "  FROM   empmast e1, "
					+ "  emptran T, "
					+ " project_sites p, "
					+ " lookup l "
					+ "  WHERE  T.empno = e1.empno "
					+ "  AND T.prj_srno = p.site_id "
					+ "  AND e1.status = 'A' "
					+ "  AND l.lkp_srno = T.desig "
					+ "  AND l.lkp_code = 'DESIG' "
					+ "  AND T.srno = (SELECT Max(srno) "
					+ "  FROM   emptran c "
					+ "  WHERE  c.empno = e1.empno AND c.trncd = 50) "
					+ "  AND T.effdate BETWEEN '"+FrmDate+"' and '"+ToDate+"' "
					+ "  AND T.DESIG="+per_branch+"  "
					+ "  ORDER  BY t.PRJ_SRNO,t.DESIG";
			/*Query1 = ""
					+ "with promotion as "
					+ " (select e1.EMPNO,e1.empcode,e1.fname,e1.MNAME,e1.LNAME, T.PRJ_SRNO,p.site_name,T.EFFDATE,l.LKP_DISC,T.DESIG,T.SRNO "
					+ "FROM   empmast e1,emptran T,Project_Sites p ,LOOKUP l "
					+ "where  T.empno = e1.empno "
					+ "and T.PRJ_SRNO = p.SITE_ID and e1.STATUS = 'A' "
					+ "and l.LKP_SRNO = T.DESIG "
					+ "and l.LKP_CODE = 'DESIG' "
					+ "and T.SRNO = (select MAX(SRNO) from emptran c where c.EMPNO = T.EMPNO ) "
					+ "and T.EFFDATE between '"+FrmDate+"' and '"+ToDate+"' "
					+ "and T.DESIG="+per_branch+"  "
					+ "), "
					+ "trans as "
					+ "(select e1.EMPNO,e1.empcode,T.DESIG "
					+ "FROM   empmast e1,emptran T "
					+ "where  T.empno = e1.empno "
					+ "and T.SRNO = (select MAX(SRNO)-1  from emptran c where c.EMPNO = T.EMPNO) )"
					+ "select t.empcode,t.fname,t.MNAME,t.LNAME,t.site_name,t.EFFDATE,t.LKP_DISC,t.desig,t.EMPNO,t.PRJ_SRNO, "
					+ " l.desig from promotion t ,trans l "
					+ " where t.desig!=l.desig and t.EMPNO = l.EMPNO ORDER BY t.PRJ_SRNO,t.DESIG";*/
		}
       else if(BranchType.equalsIgnoreCase("3"))
		{
    	   Query1 = ""
					+ "SELECT e1.empno,T.TRNCD, "
					+ "  e1.empcode, "
					+ "  e1.fname, "
					+ "  e1.mname, "
					+ "  e1.lname, "
					+ "  T.prj_srno, "
					+ "  p.site_name, "
					+ "  T.effdate, "
					+ "  l.lkp_disc, "
					+ "  T.desig, "
					+ "  T.srno "
					+ "  FROM   empmast e1, "
					+ "  emptran T, "
					+ " project_sites p, "
					+ " lookup l "
					+ "  WHERE  T.empno = e1.empno "
					+ "  AND T.prj_srno = p.site_id "
					+ "  AND e1.status = 'A' "
					+ "  AND l.lkp_srno = T.desig "
					+ "  AND l.lkp_code = 'DESIG' "
					+ "  AND T.srno = (SELECT Max(srno) "
					+ "  FROM   emptran c "
					+ "  WHERE  c.empno = e1.empno AND c.trncd = 50) "
					+ "  AND T.effdate BETWEEN '"+FrmDate+"' and '"+ToDate+"' "
					+ "  AND T.DESIG between "+branchfrom+" and  "+branchto+"  "
					+ "  ORDER  BY t.PRJ_SRNO,t.DESIG";
			/*Query1 =  ""
					+ "with promotion as "
					+ " (select e1.EMPNO,e1.empcode,e1.fname,e1.MNAME,e1.LNAME, T.PRJ_SRNO,p.site_name,T.EFFDATE,l.LKP_DISC,T.DESIG,T.SRNO "
					+ "FROM   empmast e1,emptran T,Project_Sites p ,LOOKUP l "
					+ "where  T.empno = e1.empno "
					+ "and T.PRJ_SRNO = p.SITE_ID and e1.STATUS = 'A' "
					+ "and l.LKP_SRNO = T.DESIG "
					+ "and l.LKP_CODE = 'DESIG' "
					+ "and T.SRNO = (select MAX(SRNO) from emptran c where c.EMPNO = T.EMPNO ) "
					+ "and T.EFFDATE between '"+FrmDate+"' and '"+ToDate+"' "
					+ "and T.DESIG between "+branchfrom+" and  "+branchto+"  "
					+ "), "
					+ "trans as "
					+ "(select e1.EMPNO,e1.empcode,T.DESIG "
					+ "FROM   empmast e1,emptran T "
					+ "where  T.empno = e1.empno "
					+ "and T.SRNO = (select MAX(SRNO)-1  from emptran c where c.EMPNO = T.EMPNO) )"
					+ "select t.empcode,t.fname,t.MNAME,t.LNAME,t.site_name,t.EFFDATE,t.LKP_DISC,t.desig,t.EMPNO, t.PRJ_SRNO,"
					+ " l.desig from promotion t ,trans l "
					+ " where t.desig!=l.desig and t.EMPNO = l.EMPNO ORDER BY t.PRJ_SRNO,t.DESIG";*/		
		}
		System.out.println("Query1 is @@@ " +Query1);
		st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		emp = st.executeQuery(Query1);	
	
		int tot_no_emp = 0;
		int tnoemp =0;
		/*if(emp.next()){
		
		emp.previous();
		while(rspt0.next())
		{
			int srno1=1;
			emp.next();
			if(rspt0.getInt("BRANCH") == emp.getInt("prj_srno"))
			{
		para = new Paragraph(new Phrase("Employee's Promotion List For Project Site :  "+rspt0.getString("SITE_NAME"),new Font(Font.TIMES_ROMAN,7,Font.BOLD)));
		para.setAlignment(Element.ALIGN_CENTER);
		para.setSpacingAfter(5);
		doc.add(para);
			}emp.previous();
		PdfPTable table1=new PdfPTable(6);
		table1.setSpacingBefore(10.0f);
		table1.setWidthPercentage(new float[]{6,12,32,16,20,20},new Rectangle(106,100));
		PdfPCell c1 = new PdfPCell(new Phrase("Srno",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));  
		table1.addCell(c1);
		PdfPCell c2 = new PdfPCell(new Phrase("Emp Code ",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
		table1.addCell(c2);
		PdfPCell c3 = new PdfPCell(new Phrase("Employee Name",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
		table1.addCell(c3); 
		PdfPCell c4 = new PdfPCell(new Phrase("Promotion Date",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
		table1.addCell(c4); 
		PdfPCell c6 = new PdfPCell(new Phrase("Branch Name",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
		table1.addCell(c6); 
		PdfPCell c7 = new PdfPCell(new Phrase("Current Designation",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
		table1.addCell(c7); 
		table1.setHeaderRows(1);
		
		//PdfPTable datatab;
		while(emp.next())
		{
			
			System.out.println("BRANCH===>"+rspt0.getInt("BRANCH")+"EMP BRANCH===>"+emp.getInt("PRJ_SRNO"));
			if(rspt0.getInt("BRANCH") == emp.getInt("PRJ_SRNO"))
			{
				System.out.println("Branch name:  "+rspt0.getInt("BRANCH")+"  PRJ_SRNO:"+emp.getInt("PRJ_SRNO"));
				PdfPCell c71 = new PdfPCell(new Phrase(""+srno1,new Font(Font.TIMES_ROMAN,7)));  
				table1.addCell(c71);
				
			PdfPCell c711 = new PdfPCell(new Phrase(""+emp.getString("EMPCODE"),new Font(Font.TIMES_ROMAN,7)));  
			table1.addCell(c711);
			PdfPCell c8 = new PdfPCell(new Phrase(""+emp.getString("fname")+" "+emp.getString("MNAME")+" "+emp.getString("LNAME"),new Font(Font.TIMES_ROMAN,7)));
			table1.addCell(c8);
			
			PdfPCell c9= new PdfPCell(new Phrase(""+emp.getString("EFFDATE"),new Font(Font.TIMES_ROMAN,7)));
			table1.addCell(c9);
			
			PdfPCell c10 = new PdfPCell(new Phrase(""+emp.getString("site_name"),new Font(Font.TIMES_ROMAN,6)));
			table1.addCell(c10);
			
			PdfPCell c11= new PdfPCell(new Phrase(""+emp.getString("LKP_DISC"),new Font(Font.TIMES_ROMAN,6)));
			table1.addCell(c11);
			tot_no_emp = tot_no_emp +1;
			srno1++;
			totemp++;
			tnoemp++;
			}
			else{
				emp.previous();
				break;
			}
			
		}
		PdfPTable table2=new PdfPTable(6);
		if(tot_no_emp>0){
		
		table2.setSpacingBefore(0f);
		table2.setWidthPercentage(new float[]{6,12,32,16,20,20},new Rectangle(106,100));
		PdfPCell tot = new PdfPCell(new Phrase("No. of Employee:",new Font(Font.TIMES_ROMAN,7,Font.BOLD)));
		tot.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tot.setColspan(3);
		table2.addCell(tot);
		PdfPCell tot1 = new PdfPCell(new Phrase(""+totemp,new Font(Font.TIMES_ROMAN,7,Font.BOLD)));
		tot1.setColspan(4);
		table2.addCell(tot1);
		
		}
		pretotincded1=0;
		pretotincded=0;
		totincded1=0;
		totincded=0;
		doc.add(table1);
		doc.add(table2);
		
		if(!rspt0.next()||!emp.next())
		{
			break;
		}
		else{
			tot_no_emp=0;
			totemp=0;
			rspt0.previous();
			emp.previous();
			continue;
		}
			 
	}//doc.add(headtab);
		
		
		para = new Paragraph(new Phrase("TOTAL SUMMERY :-",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
		para.setAlignment(Element.ALIGN_CENTER);
		para.setSpacingBefore(5);
		doc.add(para);
		
		
		
		PdfPTable headtab=new PdfPTable(2);
		headtab.setSpacingBefore(5.0f);
		headtab.setWidthPercentage(new float[]{50,50},new Rectangle(100,100));
		PdfPCell tot = new PdfPCell(new Phrase("Total Employee :",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
		tot.setHorizontalAlignment(Element.ALIGN_RIGHT);
		//*tot.setColspan(3);
		headtab.addCell(tot);
		PdfPCell tot1 = new PdfPCell(new Phrase(""+tnoemp,new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
		headtab.addCell(tot1);
		doc.add(headtab);

	}*/
		
		if(emp.next()){
			emp.previous();
			int srno1=1;
			PdfPTable table1=new PdfPTable(6);
			table1.setSpacingBefore(10.0f);
			table1.setWidthPercentage(new float[]{6,12,32,16,20,20},new Rectangle(106,100));
			PdfPCell c1 = new PdfPCell(new Phrase("Srno",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));  
			table1.addCell(c1);
			PdfPCell c2 = new PdfPCell(new Phrase("Emp Code ",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
			table1.addCell(c2);
			PdfPCell c3 = new PdfPCell(new Phrase("Employee Name",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
			table1.addCell(c3); 
			PdfPCell c4 = new PdfPCell(new Phrase("Promotion Date",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
			table1.addCell(c4); 
			PdfPCell c6 = new PdfPCell(new Phrase("Branch Name",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
			table1.addCell(c6); 
			PdfPCell c7 = new PdfPCell(new Phrase("Current Designation",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
			table1.addCell(c7); 
			table1.setHeaderRows(1);
			
			while(emp.next())
			{
				
				PdfPCell c71 = new PdfPCell(new Phrase(""+srno1,new Font(Font.TIMES_ROMAN,7)));  
				table1.addCell(c71);
					
				PdfPCell c711 = new PdfPCell(new Phrase(""+emp.getString("EMPCODE"),new Font(Font.TIMES_ROMAN,7)));  
				table1.addCell(c711);
				PdfPCell c8 = new PdfPCell(new Phrase(""+emp.getString("fname")+" "+emp.getString("MNAME")+" "+emp.getString("LNAME"),new Font(Font.TIMES_ROMAN,7)));
				table1.addCell(c8);
				
				PdfPCell c9= new PdfPCell(new Phrase(""+emp.getString("EFFDATE"),new Font(Font.TIMES_ROMAN,7)));
				table1.addCell(c9);
				
				PdfPCell c10 = new PdfPCell(new Phrase(""+emp.getString("site_name"),new Font(Font.TIMES_ROMAN,6)));
				table1.addCell(c10);
				
				PdfPCell c11= new PdfPCell(new Phrase(""+emp.getString("LKP_DISC"),new Font(Font.TIMES_ROMAN,6)));
				table1.addCell(c11);
				tot_no_emp = tot_no_emp +1;
				srno1++;
				tnoemp++;
				
			}
			doc.add(table1);
				 
			PdfPTable headtab=new PdfPTable(2);
			headtab.setSpacingBefore(0.0f);
			headtab.setWidthPercentage(new float[]{50,50},new Rectangle(100,100));
			PdfPCell tot = new PdfPCell(new Phrase("Total Employee :",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
			tot.setHorizontalAlignment(Element.ALIGN_RIGHT);
			//*tot.setColspan(3);
			headtab.addCell(tot);
			PdfPCell tot1 = new PdfPCell(new Phrase(""+tnoemp,new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
			headtab.addCell(tot1);
			doc.add(headtab);

		}
		
	else{
		System.out.println("no record found");
		para = new Paragraph(new Phrase("Record not found",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
		para.setAlignment(Element.ALIGN_CENTER);
		para.setSpacingAfter(5);
		doc.add(para);
	}
		doc.close();
		Cn.close();
	}
	catch(Exception e) 
	{
		e.printStackTrace();
	}


	
}

public static void RetirementExtCreditLeave(String fromDate, String toDate, String filepath1, String imagepath) {

	
	System.out.println("I am in RETIRMENT  " + fromDate);
	String [] year = fromDate.split("-");
	
	int Nxtyr=0;
	RepoartBean repBean  = new RepoartBean();
	LookupHandler lkhp = new LookupHandler();
	try
	{
		Connection Cn = null;
		Statement st = null;
		ResultSet rs = null;
		
		Statement st1 = null;
		ResultSet rs1 = null;
		ReportDAO.OpenCon("", "", "",repBean);
		Cn = repBean.getCn();
	
		String Query = "";
		String Query1 = "";
		int totalEmployee = 0;
		int Employee = 0;
		String empno[];
		double totalamount=0;
		double alltotal=0;
		
		Document doc = new Document();
		PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filepath1));
		
		
		ReportDAOPDF dao = new ReportDAOPDF();
		ReportDAOPDF.lable1="Date : ";
		Footer ftr = dao.new Footer(lable1);
		writer.setPageEvent(ftr);
		doc.open();
		doc.setPageSize(PageSize.A4);
	
		Font fbold = new Font(Font.TIMES_ROMAN,8,Font.BOLD);
		Font FONT1 = new Font(Font.TIMES_ROMAN, 7, Font.NORMAL);
		Font FONT = new Font(Font.TIMES_ROMAN, 12, Font.NORMAL, new GrayColor(0.75f));
		Font fsmall = new Font(Font.TIMES_ROMAN,5);
		Font  fontcolor = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(6, 8, 193));
		
		ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Business Bank",FONT), 297.5f, 421, 45);
		
		//Image image1 = Image.getInstance(imagepath2);	
		Image image1 = Image.getInstance(imagepath);     
		image1.scaleAbsolute(80f, 80f);
		image1.setAbsolutePosition(9f, 740f);
		doc.add(image1);	
		String ty ="";
		
		int oldyear=(Integer.parseInt(year[2])-1);	
		Nxtyr = (Integer.parseInt(year[2])+1);
		Paragraph para = new Paragraph(new Phrase("Employee_Appraisal_Report For Increment Release:-"+ty+"   For Year:- 01-04-"+year[2]+"  To  31-03-"+Nxtyr ,fbold));
		para.setAlignment(Element.ALIGN_CENTER);
		para.setSpacingAfter(20);
		doc.add(para);

		Rectangle rec = new  Rectangle(180,100);
		PdfPTable table = null;
		
		table= new PdfPTable(11);	
		table.setSpacingBefore(20);
		table.setWidthPercentage(new float[]{14,40,28,20,15,15,15,15,15,15,15}, rec);
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		String constant="";
		
		
			Query=""
				+ "select e.EMPNO ,e.EMPCODE,rtrim(e.FNAME)+' '+rtrim(e.MNAME)+' '+rtrim(e.LNAME) as NAME ,e.STATUS ,e.DOB , "
				+ "e.DOJ ,e.retirementdate ,e.RETIREMENT_EXT_PERIOD "
				+ " ,(select LKP_DISC  from LOOKUP where LKP_CODE ='CASTE' and LKP_SRNO =  e.CASTCD) as caste "
				+ ",(select LKP_DISC  from LOOKUP where LKP_CODE ='CATE' and LKP_SRNO = e.CATEGORYCD) as CATE "
				+ " ,(select LKP_DISC  from LOOKUP where LKP_CODE ='DESIG' and LKP_SRNO = e1.DESIG) as DESIG "
				+ " ,p.SITE_NAME  "
				+ "from empmast e, emptran e1 ,Project_Sites p where e.empno  =e1.EMPNO "
				+ " and e1.SRNO =(select MAX(srno) from EMPTRAN where EMPNO =e.EMPNO ) "
				+ " and p.SITE_ID =e1.PRJ_SRNO  and e.retirementdate < e.RETIREMENT_EXT_PERIOD "
				+ " and e.retirementdate between '"+fromDate+"' and  '"+toDate+"' ";
			
		System.out.println("hi...."+Query);
		st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);   
		rs = st.executeQuery(Query);
		String StartDT="";
		int SRNO=0,Grade=0;
		if(rs.next())
		{			
				PdfPTable main = new PdfPTable(12);
				table.setSpacingBefore(20);
				String str[] = new String[12];
				
				str[0]="SR NO";str[1]="EMPCODE";str[2]="EMPP NAME";str[3]="BRANCH";	str[4]="DESIG";	
				str[5]="STATUS";str[6]="DOB";str[7]="DOJ";str[8]="RETIREMENT DATE";
				str[9]="RETIREMENT EXT. DATE";str[10]="CATAGORY";str[11]="CASTE";
				
				main.setWidthPercentage(new float[]{14,20,28,20,15,13,13,13,13,13,13,13}, rec);
				main.setHorizontalAlignment(Element.ALIGN_CENTER);
				PdfPCell maincell ;	
				
				for(int i=0; i<str.length;i++ )
				{					
					maincell = new PdfPCell(new Phrase(str[i],fbold));
					maincell.setHorizontalAlignment(Element.ALIGN_LEFT);
					main.addCell(maincell);
				}	
				doc.add(main);
				String nbasic="",nda="",nhra="",nvda="";
				totalamount=0;
				rs.previous();
				int srno=0;
				while (rs.next())
				{		
					srno++;
					
					
					main = new PdfPTable(12);
					main.setWidthPercentage(new float[]{14,20,28,20,15,13,13,13,13,13,13,13}, rec);
					main.setHorizontalAlignment(Element.ALIGN_CENTER); 
					System.out.println("empname ::"+rs.getString("NAME"));
					PdfPCell cell1 = new PdfPCell(new Phrase(""+srno,FONT1)); 
					main.setHorizontalAlignment(Element.ALIGN_CENTER); 
					main.addCell(cell1);
					
					PdfPCell cell2 = new PdfPCell(new Phrase(""+rs.getString("EMPCODE"),FONT1));
					main.setHorizontalAlignment(Element.ALIGN_LEFT); 
					main.addCell(cell2);
					
					PdfPCell cell3 = new PdfPCell(new Phrase(""+rs.getString("NAME"),FONT1));
					main.setHorizontalAlignment(Element.ALIGN_LEFT); 
					main.addCell(cell3);
					
					PdfPCell cell5 = new PdfPCell(new Phrase(""+rs.getString("SITE_NAME"),FONT1));
					main.setHorizontalAlignment(Element.ALIGN_CENTER); 
					main.addCell(cell5);
					/*PdfPCell cell4 = new PdfPCell(new Phrase(""+rs.getString("APPARAISAL_DATE") ,fsmall));
					main.setHorizontalAlignment(Element.ALIGN_CENTER); 
					main.addCell(cell4);*/
					PdfPCell cell31 = new PdfPCell(new Phrase(""+rs.getString("DESIG"),FONT1));
					main.setHorizontalAlignment(Element.ALIGN_LEFT); 
					main.addCell(cell31);
				
					PdfPCell cell6 = new PdfPCell(new Phrase(""+rs.getString("STATUS"),FONT1));
					main.setHorizontalAlignment(Element.ALIGN_CENTER); 
					main.addCell(cell6);
					PdfPCell cell7 = new PdfPCell(new Phrase(""+rs.getString("DOB"),FONT1));
					main.setHorizontalAlignment(Element.ALIGN_CENTER); 
					main.addCell(cell7);
					PdfPCell cell9 = new PdfPCell(new Phrase(""+rs.getString("DOJ"),FONT1));
					main.setHorizontalAlignment(Element.ALIGN_CENTER); 
					main.addCell(cell9);
					PdfPCell cell8 = new PdfPCell(new Phrase(""+rs.getString("retirementdate"),FONT1));
					main.setHorizontalAlignment(Element.ALIGN_CENTER); 
					main.addCell(cell8);
					
					PdfPCell cell61 = new PdfPCell(new Phrase(""+rs.getString("RETIREMENT_EXT_PERIOD"),FONT1));
					main.setHorizontalAlignment(Element.ALIGN_CENTER); 
					main.addCell(cell61);
					PdfPCell cell71 = new PdfPCell(new Phrase(""+rs.getString("cate"),FONT1));
					main.setHorizontalAlignment(Element.ALIGN_CENTER); 
					main.addCell(cell71);
					PdfPCell cell10 = new PdfPCell(new Phrase(""+rs.getString("caste"),FONT1));
					main.setHorizontalAlignment(Element.ALIGN_CENTER); 
					main.addCell(cell10);
					
					
					
					
					doc.add(main);
			
					Employee+=1;	
					
				}
				
				
			para = new Paragraph(new Phrase("Total No Of Employee :- "+Employee,fbold));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(10);
			doc.add(para);
			
		}
		else
		{
			System.out.println("no record found");
			PdfPCell c1 = new PdfPCell(new Phrase("Record is Not found for ",fsmall));  
			c1.setColspan(6);
			
			PdfPTable main = new PdfPTable(11);
			main.setWidthPercentage(new float[]{14,40,28,20,15,15,15,15,15,15,15}, rec);
			main.setHorizontalAlignment(Element.ALIGN_CENTER);
			main.addCell(c1);
			main.setSpacingBefore(10);
			doc.add(main);
		}
		doc.close();
	}
	catch(Exception e) 
	{
		e.printStackTrace();
	}


	
}

public static void Left_Employee_Report(String fromDate, String toDate, String type, String branch, String desig, String leftRepType,String filepath, String imagepath2) {
	
	
	System.out.println("I am in EmployeeRetiringList All Type " );
	long millis=System.currentTimeMillis();  
	 java.sql.Date date1=new java.sql.Date(millis);  
	 System.out.println("date..."+date1);
	 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	 String reportDate = df.format(date1);
	RepoartBean repBean  = new RepoartBean();
	try
	{
		Connection Cn = null;
		Statement st = null;
		ResultSet rs = null;

		ReportDAO.OpenCon("", "", "",repBean);
		Cn = repBean.getCn();
		
		int totalEmployee =0;
		String Query1 = "";
		
		LookupHandler lkhp = new LookupHandler();
		Rectangle rec = new  Rectangle(190,100);
		Document doc = new Document();
		
		PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filepath));
	    ReportDAOPDF dao = new ReportDAOPDF();
		ReportDAOPDF.lable1="Date : ";
		Footer ftr = dao.new Footer(lable1);
		writer.setPageEvent(ftr);
		doc.open();
		doc.setPageSize(PageSize.A4);
		
		Font fbold = new Font(Font.TIMES_ROMAN,8,Font.BOLD);
		Font fsmall = new Font(Font.TIMES_ROMAN,7);
		
		Font fsmall1 = new Font(Font.TIMES_ROMAN,7,com.itextpdf.text.Font.NORMAL);
		Font FONT = new Font(Font.TIMES_ROMAN, 20, Font.NORMAL, new GrayColor(0.75f));
		Font  fontcolor = FontFactory.getFont(FontFactory.HELVETICA, 9, new Color(6, 8, 193));
		
		ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Business Bank",FONT), 297.5f, 421, 45);
		Image image1 = Image.getInstance(imagepath2);
		Phrase title = new Phrase("THE BUSINESS CO.OP BANK LTD.",new Font(FONT.TIMES_ROMAN,8,Font.BOLD));
		Paragraph para = new Paragraph(title);
		para.setAlignment(Element.ALIGN_CENTER);
		para.setSpacingBefore(0);

		image1.scaleAbsolute(60f,50f);
		image1.setAbsolutePosition(30f, 750f);
		doc.add(image1);
//		doc.add(para);
		/*para = new Paragraph(new Phrase("Rajan Complex Arcade, Datta Mandir Circle, Nashik Road, Nashik - 422101",new Font(Font.TIMES_ROMAN,8)));
		para.setAlignment(Element.ALIGN_CENTER);
		para.setSpacingAfter(0);

		doc.add(para);
		para = new Paragraph(new Phrase("Tel : 0253-2406100, 2469545",new Font(Font.TIMES_ROMAN,8)));
		para.setAlignment(Element.ALIGN_CENTER);
		para.setSpacingAfter(0);

		doc.add(para);
		para = new Paragraph(new Phrase("Email : hrd@namcobank.in",new Font(Font.TIMES_ROMAN,8)));
		para.setAlignment(Element.ALIGN_CENTER);
		para.setSpacingAfter(0);

		doc.add(para);
		para = new Paragraph(new Phrase("Employee Retiring List " ,new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
		para.setAlignment(Element.ALIGN_CENTER);
		para.setSpacingAfter(0);
		doc.add(para);*/
		
		String constant="";
		
		if(leftRepType.equals("1")){
			constant =" and e1.prj_srno="+branch;
		}else if(leftRepType.equals("2")){
			constant =" and e1.DESIG="+desig;
		}else if(leftRepType.equals("0")){
			constant =" ";
		}
		String Query="";
		int srno = 1;
		if(type.equals("8")){
			System.out.println("i am in type 1 constant ::"+constant);
			 Query = ""
			+ "SELECT e.empno, "
			+ "       e.empcode, "
			+ "       Rtrim(e.fname) + ' ' + Rtrim(e.mname) + ' ' "
			+ "       + Rtrim(e.lname)                     AS NAME, "
			+ "       e.status, "
			+ "	   e.retirementdate, "
			+ "	   (SELECT lkp_disc "
			+ "        FROM   lookup "
			+ "        WHERE  lkp_code = 'CASTE' "
			+ "               AND lkp_srno = e.castcd)     AS caste, "
			+ "       (SELECT lkp_disc "
			+ "        FROM   lookup "
			+ "        WHERE  lkp_code = 'CATE' "
			+ "               AND lkp_srno = e.categorycd) AS CATE, "
			+ "       (SELECT lkp_disc "
			+ "        FROM   lookup "
			+ "        WHERE  lkp_code = 'DESIG' "
			+ "               AND lkp_srno = e1.desig)     AS DESIG, "
			+ "        p.site_name, "
			+ "        (SELECT lkp_disc FROM   lookup WHERE  lkp_code = 'LFT_PURP' AND lkp_srno = ri.LEFT_REASON) AS LEFT_REASON ,e.FILENUMBER,ri.LEFT_DATE,e.retirementdate  "
			+ "FROM   empmast e, "
			+ "       emptran e1, "
			+ "       project_sites p ,relieveinfo ri "
			+ "WHERE  e.empno = e1.empno "
			+ "       AND e1.srno = (SELECT Max(srno) "
			+ "                      FROM   emptran "
			+ "                      WHERE  empno = e.empno) "
			+ "       AND p.site_id = e1.prj_srno "
			+ "      AND ri.LEFT_DATE BETWEEN '"+fromDate+"' and  '"+toDate+"' "
			+ "      AND ri.EMPNO = e.EMPNO  "+constant+"  order by e1.desig";
		}
		else if(type.equals("9")){
		 Query = ""
						+ "SELECT e.empno, "
						+ "       e.empcode, "
						+ "       Rtrim(e.fname) + ' ' + Rtrim(e.mname) + ' ' "
						+ "       + Rtrim(e.lname)                     AS NAME, "
						+ "       e.status, "
						+ "	   e.retirementdate, "
						+ "	   (SELECT lkp_disc "
						+ "        FROM   lookup "
						+ "        WHERE  lkp_code = 'CASTE' "
						+ "               AND lkp_srno = e.castcd)     AS caste, "
						+ "       (SELECT lkp_disc "
						+ "        FROM   lookup "
						+ "        WHERE  lkp_code = 'CATE' "
						+ "               AND lkp_srno = e.categorycd) AS CATE, "
						+ "       (SELECT lkp_disc "
						+ "        FROM   lookup "
						+ "        WHERE  lkp_code = 'DESIG' "
						+ "               AND lkp_srno = e1.desig)     AS DESIG, "
						+ "        p.site_name, "
						+ "        (SELECT lkp_disc FROM   lookup WHERE  lkp_code = 'LFT_PURP' AND lkp_srno = ri.LEFT_REASON) AS LEFT_REASON,e.FILENUMBER,ri.LEFT_DATE,e.retirementdate  "
						+ "FROM   empmast e, "
						+ "       emptran e1, "
						+ "       project_sites p ,relieveinfo ri "
						+ "WHERE  e.empno = e1.empno "
						+ "       AND e1.srno = (SELECT Max(srno) "
						+ "                      FROM   emptran "
						+ "                      WHERE  empno = e.empno) "
						+ "       AND p.site_id = e1.prj_srno "
						+ "      AND ri.LEFT_DATE BETWEEN '"+fromDate+"' and  '"+toDate+"' "
						+ "      AND ri.EMPNO = e.EMPNO  "+constant+"  order by e1.desig";
		              }
		else{
			 Query = ""
					 + "SELECT e.empno, "
						+ "       e.empcode, "
						+ "       Rtrim(e.fname) + ' ' + Rtrim(e.mname) + ' ' "
						+ "       + Rtrim(e.lname)                     AS NAME, "
						+ "       e.status, "
						+ "	   e.retirementdate, "
						+ "	   (SELECT lkp_disc "
						+ "        FROM   lookup "
						+ "        WHERE  lkp_code = 'CASTE' "
						+ "               AND lkp_srno = e.castcd)     AS caste, "
						+ "       (SELECT lkp_disc "
						+ "        FROM   lookup "
						+ "        WHERE  lkp_code = 'CATE' "
						+ "               AND lkp_srno = e.categorycd) AS CATE, "
						+ "       (SELECT lkp_disc "
						+ "        FROM   lookup "
						+ "        WHERE  lkp_code = 'DESIG' "
						+ "               AND lkp_srno = e1.desig)     AS DESIG, "
						+ "        p.site_name, "
						+ "        (SELECT lkp_disc FROM   lookup WHERE  lkp_code = 'LFT_PURP' AND lkp_srno = ri.LEFT_REASON) AS LEFT_REASON,e.FILENUMBER,ri.LEFT_DATE,e.retirementdate  "
						+ "FROM   empmast e, "
						+ "       emptran e1, "
						+ "       project_sites p ,relieveinfo ri "
						+ "WHERE  e.empno = e1.empno "
						+ "       AND e1.srno = (SELECT Max(srno) "
						+ "                      FROM   emptran "
						+ "                      WHERE  empno = e.empno) "
						+ "       AND p.site_id = e1.prj_srno "
						+ "      AND ri.EMPNO = e.EMPNO"
						+ "      AND ri.LEFT_DATE BETWEEN '"+fromDate+"' and  '"+toDate+"' "
			 			+ "  AND ri.LEFT_REASON= "+type+" "+constant+"  order by e1.desig";
		}
			 System.out.println("-----"+Query);
		st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		rs = st.executeQuery(Query);
		
		
		if(rs.next()){
		/*if(!rs.next()){
			System.out.println("Recordset EMpty");
		}*/
		PdfPTable main = new PdfPTable(11);
		main.setSpacingBefore(10);
		
		String str[] = new String[11];
		
		str[0]="SRNO";			str[1]="EMPCODE";			str[2]="EMP NAME";
		str[3]="BRANCH NAME";	str[4]="DESIG";	str[5]="RETIREMENT DATE"; str[6]="LEFT DATE";
		str[7]="P FILE";	str[8]="CATAGORY";	str[9]="CASTE"; str[10]="REASON";
		
		main.setWidthPercentage(new float[]{11,14,38,26,18,15,15,15,18,18,18}, rec);
		main.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell maincell ;	
		
		for(int i=0; i<str.length;i++ )
		{					
			maincell = new PdfPCell(new Phrase(str[i],fbold));
			maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
			main.addCell(maincell);
		}	
		doc.add(main);
		rs.beforeFirst();
		int cnt =0;
		
//		if(rs.next()){
		while(rs.next())
		{
			PdfPTable main1 = new PdfPTable(11);
			main1.setWidthPercentage(new float[]{11,14,38,26,18,15,15,15,18,18,18}, rec);
			main1.setHorizontalAlignment(Element.ALIGN_CENTER);	
			
			PdfPCell cell1 = new PdfPCell(new Phrase(""+srno,fsmall1));  
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			main1.addCell(cell1);
			
			PdfPCell cell2 = new PdfPCell(new Phrase((rs.getString("EMPCODE")),fsmall1));	
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			main1.addCell(cell2);
			
			PdfPCell cell3 = new PdfPCell(new Phrase(""+rs.getString("name"),fsmall1));
			cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			main1.addCell(cell3);
			
			PdfPCell cell4 = new PdfPCell(new Phrase((rs.getString("SITE_NAME")),fsmall1));
			cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			main1.addCell(cell4);
			
			PdfPCell cell5 = new PdfPCell(new Phrase(""+(rs.getString("DESIG")),fsmall1));
			cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
			main1.addCell(cell5);
			
			PdfPCell cell6 = new PdfPCell(new Phrase((rs.getString("retirementdate")),fsmall1));
			cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			main1.addCell(cell6);
			
			PdfPCell cell7 = new PdfPCell(new Phrase(""+(rs.getString("LEFT_DATE")),fsmall1));
			cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
			main1.addCell(cell7);
			PdfPCell cell71 = new PdfPCell(new Phrase(""+(rs.getString("FILENUMBER")),fsmall1));
			cell71.setHorizontalAlignment(Element.ALIGN_CENTER);
			main1.addCell(cell71);
			PdfPCell cell61 = new PdfPCell(new Phrase((rs.getString("caste")),fsmall1));
			cell61.setHorizontalAlignment(Element.ALIGN_CENTER);
			main1.addCell(cell61);
			PdfPCell cell51 = new PdfPCell(new Phrase(""+(rs.getString("cate")),fsmall1));
			cell51.setHorizontalAlignment(Element.ALIGN_CENTER);
			main1.addCell(cell51);
			PdfPCell cell41 = new PdfPCell(new Phrase((rs.getString("LEFT_REASON")),fsmall1));
			cell41.setHorizontalAlignment(Element.ALIGN_CENTER);
			main1.addCell(cell41);
			
			doc.add(main1);
			srno++;
			totalEmployee+=1;
			
		}		
		para = new Paragraph(new Phrase("Total Employee : "+totalEmployee,new Font(Font.TIMES_ROMAN,8,Font.BOLD)));  
		para.setAlignment(Element.ALIGN_LEFT);
		para.setSpacingAfter(10);
		doc.add(para);
          }else{
			System.out.println("no record found");
			para = new Paragraph(new Phrase("Record not found",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(5);
			doc.add(para);
		}
		doc.close();
		Cn.close();
	}
	catch(Exception e) 
	{
		e.printStackTrace();
	}
	
	
}
	 







}
