package payroll.Core;
import java.awt.font.TextAttribute;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.AttributedString;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import payroll.DAO.CodeMasterHandler;
import payroll.DAO.ConnectionManager;
import payroll.DAO.EmpOffHandler;
import payroll.DAO.LookupHandler;
import payroll.DAO.TranHandler;
import payroll.Model.RepoartBean;
import payroll.Model.TranBean;
import payroll.Model.TransactionBean;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
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
import com.lowagie.text.pdf.PdfWriter;

public class SalaryCertificateDAO extends PdfPageEventHelper
{
	 
	public static void getCertificate(String date11,String date22,int empno,String filepath,String imgpath) 
		{
		
		
		
		Properties prop = new Properties();
		try {

			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream stream = classLoader.getResourceAsStream("constant.properties");
			prop.load(stream);
		} catch (Exception e) {
			System.out.println("Error in constant properties Manager " + e);
		}

			Document doc = new Document();
			try
			{	
				Connection con = ConnectionManager.getConnection();
				SimpleDateFormat fromf = new SimpleDateFormat("dd-MMM-yyyy");
				SimpleDateFormat tof = new SimpleDateFormat("yyyy-MM-dd");
				String date1="01-Apr-"+date11;
				String date2="31-Mar-"+date22;
				Date dt = fromf.parse(date1);
				String dt1 = tof.format(dt);
				dt = fromf.parse(date2);
				String dt2 = tof.format(dt);
				Date sysdate = new Date();
				String sys = fromf.format(sysdate);
				
				String sqll="select LKP_DISC from LOOKUP where LKP_CODE='DESIG' and LKP_SRNO=(select desig from EMPTRAN where EMPNO= "+empno+" and"+
						" SRNO=(select MAX(srno) from EMPTRAN where EMPNO="+empno+"))";
			Statement stt=con.createStatement();
			ResultSet rss=stt.executeQuery(sqll);
			String desig="";
			if(rss.next())
			{
				desig=rss.getString("LKP_DISC");
			}
				
				PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(filepath));
				doc.open();
				doc.setPageSize(PageSize.A4);
							
				PdfPTable tab1 = new PdfPTable(1);
				tab1.setWidthPercentage(new float[]{100},new Rectangle(100,100));
				PdfPCell cell = new PdfPCell();
				cell.setBorderWidth(0.7F);
				Font f_under = new Font(Font.HELVETICA,14,Font.BOLD|Font.UNDERLINE);
				Font f1 = new Font(Font.HELVETICA,8);
				Font f2 = new Font(Font.HELVETICA,7.5F);
				
				Font f = new Font(Font.TIMES_ROMAN,8,Font.BOLD);

				
				
			
				cell.addElement(createPara("Date :- " +sys,f1, Element.ALIGN_RIGHT));
				cell.addElement(Chunk.NEWLINE);
				
				Image image1 = Image.getInstance(imgpath);
				image1.setAlignment(Element.ALIGN_CENTER);


				//Phrase title = new Phrase("THE BUSINESS CO.OP BANK LTD.",new Font(f.TIMES_ROMAN,10,Font.BOLD));
				cell.addElement(createPara(prop.getProperty("bankName2"),new Font(f.TIMES_ROMAN,10,Font.BOLD), Element.ALIGN_CENTER));
				/*Paragraph para = new Paragraph();
				para.setAlignment(Element.ALIGN_TOP);
				para.setSpacingBefore(10);*/
				

				image1.scaleAbsolute(80f, 80f);
				image1.setAbsolutePosition(70f, 710f);

				doc.add(image1);
				//doc.add(para);


			
				cell.addElement(createPara("                     "+prop.getProperty("addressForReport"),new Font(Font.TIMES_ROMAN,10), Element.ALIGN_CENTER));
				

				
				
				cell.addElement(createPara(prop.getProperty("contactForReport"),new Font(Font.TIMES_ROMAN,10), Element.ALIGN_CENTER));
				
				
				cell.addElement(createPara(prop.getProperty("mailForReport"),new Font(Font.TIMES_ROMAN,10), Element.ALIGN_CENTER));
				
				
				cell.addElement(Chunk.NEWLINE);
				cell.addElement(createPara("Certificate",f_under, Element.ALIGN_CENTER));
				cell.addElement(Chunk.NEWLINE);
				cell.addElement(createDirectorPhrase("Subject :- "," To Whom so ever it may be concern.",Element.ALIGN_CENTER));
				cell.addElement(Chunk.NEWLINE);
				
				
				
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM EMPMAST WHERE EMPNO="+empno);
				String gender="";
				if(rs.next())
				{ 
					LookupHandler lkph = new LookupHandler();
					
					String doj=rs.getString("DOJ");
					
					String doj1[]=doj.split("-");
					
					if(rs.getString("GENDER").equalsIgnoreCase("M"))
					{
						gender="His";
					}
					else
					{
						gender="Her";
					}
					
					String name = lkph.getLKP_Desc("SALUTE", rs.getInt("SALUTE"))+" "+rs.getString("FNAME")+" "+rs.getString("MNAME")+" "+rs.getString("LNAME");
						
					cell.addElement(createPara("           This is to certify that "+name+" is  working  with  our " +
							"bank on the post of "+desig +" since "+doj1[0]+"."
							,f, Element.ALIGN_CENTER));
					cell.addElement(createPara("we re issuing this letter on the specific request of employee.", f, Element.ALIGN_CENTER));
					
					/*cell.addElement(createPara("This is to certify that "+name+" is  working  with  our", f, Element.ALIGN_CENTER));
					//cell.addElement(Chunk.NEWLINE);
					cell.addElement(createPara("organization/company under the title of "+desig +" since "+doj1[0]+". we fount this gentleman fully ",f, Element.ALIGN_CENTER));
					//cell.addElement(Chunk.NEWLINE);
					cell.addElement(createPara("committed to "+gender+" job and totally sincere toward this organization/company.",f, Element.ALIGN_CENTER));
					//cell.addElement(Chunk.NEWLINE);
					cell.addElement(createPara("we re issuing this letter on the specific request of employee.", f, Element.ALIGN_CENTER));
					cell.addElement(Chunk.NEWLINE);*/
				}
				
				cell.addElement(createPara(""+gender+" Salary Structure  From  "+date1+"  To  "+date2, new Font(Font.TIMES_ROMAN,9), Element.ALIGN_CENTER));
				cell.addElement(Chunk.NEWLINE);
				
				// SALARY STRUCTURE
				cell.addElement(salarystructure(empno,date11,date22));
				
				cell.addElement(Chunk.NEWLINE);
				
				tab1.addCell(createPara("This is to certify that amount of earning is correct and computed from " , f1, Element.ALIGN_CENTER));
				/*cell.addElement(createPara("salary Record of above Employee.", f1, Element.ALIGN_CENTER));*/
				cell.addElement(Chunk.NEWLINE);
				
				cell.addElement(createPara("For,THE BUSINESS CO.OP BANK LTD.", f1, Element.ALIGN_RIGHT));
				cell.addElement(Chunk.NEWLINE);
				cell.addElement(Chunk.NEWLINE);
				cell.addElement(footer());
				cell.addElement(Chunk.NEWLINE);
				cell.addElement(Chunk.NEWLINE);
				
				tab1.addCell(cell);
				doc.add(tab1);
				
			}
			catch(Exception e)
			{
				doc.close();
				e.printStackTrace();
			}
			doc.close();
		}
		public static Paragraph createPara(String value,Font f,int alignment)
		{
			Paragraph para = new Paragraph(value,f);
			para.setAlignment(alignment);
			para.setLeading(15);
			para.setSpacingAfter(000.01f);
			para.setSpacingBefore(000.01f);
			return para;
			
		}
		 public static Phrase createDirectorPhrase(String arg1,String arg2,int alig){
		       
			  Font f_under = new Font(Font.HELVETICA,10,Font.BOLD|Font.UNDERLINE);
				Font f1 = new Font(Font.HELVETICA,10,Font.BOLD);
				Paragraph para = new Paragraph();
				Phrase director = new Phrase();
				director.add(new Chunk(new String(""+arg1),f_under));
			    director.add(new Chunk(""+arg2, f1));
			    para.add(director);
			    para.setAlignment(alig);
			    return para;
			    }
		
		public static PdfPTable salarystructure(int empno,String d1,String d2)
		{
			PdfPTable tab3 = new PdfPTable(2);
			Font f1 = new Font(Font.HELVETICA,10);
			Font fb = new Font(Font.HELVETICA,9,Font.BOLD);
			
			try
			{		
					Connection con = ConnectionManager.getConnection();
					Statement st = con.createStatement();
					
					int fd=Integer.parseInt(d1);
					int td=Integer.parseInt(d2);
					
					tab3.setWidthPercentage(new float[]{30,30},new Rectangle(100,100));
					PdfPCell cell = new PdfPCell();
					cell.setBorder(0);
					int total=0;
					tab3.addCell((createCell("YEAR ", fb, Element.ALIGN_CENTER,0.5f)));
					tab3.addCell((createCell("TOTAL INCOME ", fb, Element.ALIGN_CENTER,0.5f)));
					
					for(int i=0;i<(td-fd);i++)
					{
						String sql = ""
								+ "select SUM(NET_AMT) as total  from YTDTRAN where EMPNO="+empno+" and TRNDT between '"+(fd+i)+"-04-1' and '"+(fd+(i+1))+"-03-31' "
								+ "and TRNCD between 101 and 197 AND TRNCD <> 136";
						
						System.out.println("Yearly Total :: "+sql);
						ResultSet rs2 = st.executeQuery(sql);
						if(rs2.next())
						{
							total=rs2.getInt("total");
						}
					tab3.addCell((createCell((fd+i)+"-"+(fd+(i+1)), f1, Element.ALIGN_CENTER,0.5f)));
					tab3.addCell((createCell(""+total, f1, Element.ALIGN_CENTER,00.5f)));
					}
					
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return tab3;
		}
		
		private static PdfPTable footer() {
			PdfPTable tab3 = new PdfPTable(2);
			Font fb = new Font(Font.HELVETICA,10,Font.BOLD);
			try
			{
					tab3.setWidthPercentage(new float[]{50,50},new Rectangle(100,100));
					PdfPCell cell = new PdfPCell();
					cell.setBorder(0);
					tab3.addCell((createCell(" ", fb, Element.ALIGN_LEFT,0)));
					tab3.addCell((createCell("Head Of Department", fb, Element.ALIGN_RIGHT,0)));
					tab3.addCell((createCell(" ", fb, Element.ALIGN_LEFT,0)));
					tab3.addCell((createCell("        Admin & HRD Department   ", fb, Element.ALIGN_RIGHT,0)));
					
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return tab3;
		}
		
		
	
		
		public static PdfPCell createCell(String value,Font f,int alignment,float w)
		{
			PdfPCell cell = new PdfPCell(new Phrase(value,f));
			cell.addElement(createPara(value, f, alignment));
			cell.setBorderWidth(w);
			
			return cell;
		}
		
		public void xyz(PdfWriter writer, Document document)
		{
			Font f = new Font(Font.HELVETICA,8,Font.BOLD, new GrayColor(0.60f));
			Font FONT = new Font(Font.HELVETICA, 52, Font.BOLD, new GrayColor(0.90f));
			Rectangle rect = writer.getBoxSize("art");
			try
			{
				ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("HARSH CONSTRUCTION PVT. LTD.",FONT), 297.5f, 421, 45);
				ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("| Page : "+writer.getPageNumber(),f), rect.getRight()-30, rect.getBottom()-30, 0);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	
}



