package payroll.Core;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import payroll.DAO.ConnectionManager;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
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

public class SalaryRegisterDAO extends PdfPageEventHelper
{
	 
	public static void getSalRegister(String date,String dsgn,String filepath) 
		{
			Document doc = new Document();
			try
			{	
				System.out.println("in Salary register");
				SimpleDateFormat fromf = new SimpleDateFormat("dd-MMM-yyyy");
				SimpleDateFormat tof = new SimpleDateFormat("yyyy-MM-dd");
				Date dt = fromf.parse(date);
				String dt1 = tof.format(dt);
				Date sysdate = new Date();
				String sys = fromf.format(sysdate);
				Font f_under = new Font(Font.HELVETICA,8,Font.BOLD|Font.UNDERLINE);
				Font f1 = new Font(Font.HELVETICA,10,Font.BOLD);
				Font f2 = new Font(Font.HELVETICA,7.5F);
				PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(filepath));
				writer.setPageEvent(new SalaryRegisterDAO()); //for writing in the background
				writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
				doc.open();
				doc.setPageSize(PageSize.A4);
				
				Phrase title = new Phrase("HARSH CONSTRUCTION PVT.LTD.",new Font(Font.TIMES_ROMAN,14,Font.BOLD));
				Paragraph para = new Paragraph(title);
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingBefore(0);
				
				
				doc.add(para);
				
				para = new Paragraph(new Phrase("   1 Sanskruti Apartment Murkute Lane, New Pandit Coloney,Gangapur Road,Nashik -422005",new Font(Font.TIMES_ROMAN,10)));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(0);
				
				doc.add(para);
				para = new Paragraph(new Phrase("Tel : 0253-2317143,Fax : 0253-2317621",new Font(Font.TIMES_ROMAN,10)));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(0);
				
				doc.add(para);
				para = new Paragraph(new Phrase("Salary Register ",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(5);
				
				doc.add(para);
				
				PdfPTable tab1 = new PdfPTable(1);
				tab1.setWidthPercentage(new float[]{100},new Rectangle(100,100));
				PdfPCell cell = new PdfPCell();
				cell.setBorderWidth(0.7F);
				
				
				cell.addElement(createPara("EMP Type:- "+dsgn,f_under, Element.ALIGN_RIGHT));
				cell.addElement(createPara("Office Staff",f1, Element.ALIGN_CENTER));
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
			para.setSpacingAfter(0);
			para.setSpacingBefore(0);
			return para;
			
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
				ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Harsh Construction PVT. LTD.",FONT), 297.5f, 421, 45);
				ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("| Page : "+writer.getPageNumber(),f), rect.getRight()-30, rect.getBottom()-30, 0);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	
}



