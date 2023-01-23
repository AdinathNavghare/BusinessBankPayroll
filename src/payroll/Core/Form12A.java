package payroll.Core;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.collections.SetUtils;

import payroll.Controller.AddPhotoServlet;
import payroll.DAO.ConnectionManager;
import payroll.DAO.LookupHandler;
import com.ibm.icu.text.NumberFormat;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.GrayColor;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class Form12A extends PdfPageEventHelper
{
	public static void getForm12A(String frmDate, String Todate,String filePath) throws IOException, SQLException, DocumentException 
	{
		OutputStream file = new FileOutputStream(new File(filePath));
		Document document = new Document();
     
		try {
			PdfWriter.getInstance(document, file);
			document.open();
			
			Phrase title = new Phrase("FORM-12A (Revised)",new Font(Font.TIMES_ROMAN,9,Font.BOLD));
 			Paragraph para = new Paragraph(title);
 			para.setAlignment(Element.ALIGN_CENTER);
 			para.setSpacingBefore(0);
 			document.add(para);
 			
 			para = new Paragraph(new Phrase("EMPLOYEE'S PROVIDENT FUND AND MISC. PROVISIONS ACT, 1952",new Font(Font.TIMES_ROMAN,9)));
 			para.setAlignment(Element.ALIGN_CENTER);
 			para.setSpacingAfter(0);			
 			document.add(para);
 			
 			para = new Paragraph(new Phrase("EMPLOYEE'S PENSION SCHEME, 1995 [PARAGRAPH 20 (4)]               (To be filled in by the EPFO)",new Font(Font.TIMES_ROMAN,9)));
 			para.setAlignment(Element.ALIGN_RIGHT);
 			para.setSpacingAfter(0);
 			document.add(para);
 			
 			para = new Paragraph(new Phrase("Only for Un exempted Establishments",new Font(Font.TIMES_ROMAN,9)));
 			para.setAlignment(Element.ALIGN_LEFT);
 			para.setSpacingAfter(7);
 			document.add(para);
 			
 			 			
 			para = new Paragraph(new Phrase("Name & address of the Estt.                      Currency Period from 1st "+frmDate.substring(3,11)+"  to 31st "+Todate.substring(3, 11)+"             Establishment Status________",new Font(Font.TIMES_ROMAN,9)));
 			para.setAlignment(Element.ALIGN_LEFT);
 			para.setSpacingAfter(0);
 			document.add(para);
 			
 			para = new Paragraph(new Phrase("M/s______________________________",new Font(Font.TIMES_ROMAN,9)));
 			para.setAlignment(Element.ALIGN_LEFT);
 			para.setSpacingAfter(0);
 			document.add(para);
 			
 			para = new Paragraph(new Phrase("_________________________________",new Font(Font.TIMES_ROMAN,9)));
 			para.setAlignment(Element.ALIGN_LEFT);
 			para.setSpacingAfter(0);
 			document.add(para);
 			
 			para = new Paragraph(new Phrase("_________________________________              Statement of contribution for the Month of_______________          Group Code ________",new Font(Font.TIMES_ROMAN,9)));
 			para.setAlignment(Element.ALIGN_LEFT);
 			para.setSpacingAfter(5);
 			document.add(para);
 			
 			para = new Paragraph(new Phrase("Code No.___________________                     Statutory rate of Contribution ______________",new Font(Font.TIMES_ROMAN,9)));
 			para.setAlignment(Element.ALIGN_LEFT);
 			para.setSpacingAfter(5);
 			document.add(para);
 			
 			
 			
 			
 			PdfPTable table = new PdfPTable(9);
 			table.setSpacingBefore(10.0f);
			table.setWidthPercentage(new float[]{10,11,8,8,8,8,10,10,10},new Rectangle(80,100));
			
			PdfPCell cell1 = new PdfPCell(new Phrase("Particulars",new Font(Font.TIMES_ROMAN,10)));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell1);
			PdfPCell cell2 = new PdfPCell(new Phrase("Wages on which Contributions are Payable",new Font(Font.TIMES_ROMAN,10)));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell2);
			
			PdfPCell cell3 = nestedTable();
			cell3.setColspan(4);
			table.addCell(cell3);
			
			PdfPCell cell5 = new PdfPCell(new Phrase("Amount of Administrative Charges due",new Font(Font.TIMES_ROMAN,10)));
			cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell5);
			PdfPCell cell6 = new PdfPCell(new Phrase("Amount of Administrative Charges remitted",new Font(Font.TIMES_ROMAN,10)));
			cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell6);
			PdfPCell cell7 = new PdfPCell(new Phrase("Date of Remittance (enclose triplicate copies of Challan)",new Font(Font.TIMES_ROMAN,10)));
			cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell7);
			
			
			PdfPCell cell8 = new PdfPCell(new Phrase("E.P.F. A/c No. 01",new Font(Font.TIMES_ROMAN,10)));
			cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell8);
			PdfPCell cell9 = new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
			cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell9);
			PdfPCell cell10 = new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
			cell10.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell10);
			PdfPCell cell11 = new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell11);
			PdfPCell cell12 = new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell12);
			PdfPCell cell13 = new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell13);
			PdfPCell cell14 = new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell14);
			PdfPCell cell15 = new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
			cell15.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell15);
			PdfPCell cell16 = new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
			cell16.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell16);
			
			
			PdfPCell cell17 = new PdfPCell(new Phrase("Pension Fund A/c No. 10",new Font(Font.TIMES_ROMAN,10)));
			cell17.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell17);
			PdfPCell cell18 = new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
			cell18.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell18);
			PdfPCell cell19 = new PdfPCell(new Phrase("NIL",new Font(Font.TIMES_ROMAN,10)));
			cell19.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell19);
			PdfPCell cell20 = new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
			cell20.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell20);
			PdfPCell cell21 = new PdfPCell(new Phrase("NIL",new Font(Font.TIMES_ROMAN,10)));
			cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell21);
			PdfPCell cell22 = new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
			cell22.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell22);
			PdfPCell cell23 = new PdfPCell(new Phrase("NIL",new Font(Font.TIMES_ROMAN,10)));
			cell23.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell23);
			PdfPCell cell24 = new PdfPCell(new Phrase("NIL",new Font(Font.TIMES_ROMAN,10)));
			cell24.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell24);
			PdfPCell cell25 = new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
			cell25.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell25);
			
			
			PdfPCell cell26 = new PdfPCell(new Phrase("D.L.I. A/c No. 21",new Font(Font.TIMES_ROMAN,10)));
			cell26.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell26);
			PdfPCell cell27 = new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
			cell27.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell27);
			PdfPCell cell28 = new PdfPCell(new Phrase("NIL",new Font(Font.TIMES_ROMAN,10)));
			cell28.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell28);
			PdfPCell cell29 = new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
			cell29.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell29);
			PdfPCell cell30 = new PdfPCell(new Phrase("NIL",new Font(Font.TIMES_ROMAN,10)));
			cell30.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell30);
			PdfPCell cell31 = new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
			cell31.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell31);
			PdfPCell cell32 = new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
			cell32.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell32);
			PdfPCell cell33 = new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
			cell33.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell33);
			PdfPCell cell34 = new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
			cell34.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell34);
			table.setSpacingAfter(10);
			document.add(table);
			
			
			PdfPTable nested2 =new PdfPTable(2);
		    nested2.setWidthPercentage(new float[]{30,70},new Rectangle(96,100));
		    PdfPCell nes = new PdfPCell();
		    nested2.addCell(nest1());
		    nes.setBorderWidth(0);
		    nested2.addCell(nest2());
		    nes.setBorderWidth(0);
		    nested2.setSpacingAfter(10);
		    document.add(nested2);
			
		    PdfPTable nested3 = new PdfPTable(2);
		    nested3.setWidthPercentage(new float[]{72,28},new Rectangle(96,100));
		    PdfPCell nes1 = new PdfPCell();
		    nested3.addCell(nest3());
		    nes1.setBottom(10);
		    nested3.addCell(nest4());
		    nes1.setBottom(5);
		    document.add(nested3);
					
				
			document.close();
            file.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		document.open();	
	}
	
	public static PdfPCell nestedTable(){
		PdfPCell cell = new PdfPCell();
		//Font f = new Font(Font.HELVETICA,8);
		//Font f1 = new Font(Font.HELVETICA,10,Font.BOLD);
		
		try
		{
			PdfPTable tab = new PdfPTable(4);
			tab.setWidthPercentage(new float[]{25,25,25,25}, new Rectangle(100,100));
			
			PdfPCell cell1 = new PdfPCell(new Phrase("Amount of Contribution",new Font(Font.TIMES_ROMAN,10)));
			cell1.setColspan(2);
			tab.addCell(cell1);
			cell.setBorder(1);
			
			PdfPCell cell2 = new PdfPCell(new Phrase("Amount of Contribution remitted",new Font(Font.TIMES_ROMAN,10)));
			cell2.setColspan(2);
			tab.addCell(cell2);
			
			PdfPCell cell3 = new PdfPCell(new Phrase("Recovered from the workers",new Font(Font.TIMES_ROMAN,10)));
			
			tab.addCell(cell3);
			PdfPCell cell4 = new PdfPCell(new Phrase("Payable by the Employer",new Font(Font.TIMES_ROMAN,10)));
			
			tab.addCell(cell4);
			PdfPCell cell5 = new PdfPCell(new Phrase("Worker's share",new Font(Font.TIMES_ROMAN,10)));
			
			tab.addCell(cell5);
			PdfPCell cell6 = new PdfPCell(new Phrase("Employer's share",new Font(Font.TIMES_ROMAN,10)));
			
			tab.addCell(cell6);
			cell.addElement(tab);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return cell;
	}
	
	public static PdfPCell nest1(){
		PdfPCell cell = new PdfPCell();
		Font f = new Font(Font.HELVETICA,8);
		Font f1 = new Font(Font.HELVETICA,10);
		
		try
		{
			PdfPTable tab = new PdfPTable(2);
			tab.setWidthPercentage(new float[]{50,50}, new Rectangle(100,100));
			tab.addCell(createCell("Total No. of Employees :", f1, Element.ALIGN_LEFT));
			cell.setBorderWidth(0);
			tab.addCell(createCell("", f1, Element.ALIGN_LEFT));
			cell.setBorderWidth(0);
			tab.addCell(createCell("(a) Contract", f1, Element.ALIGN_LEFT));
			cell.setBorderWidth(0);
			tab.addCell(createCell("", f, Element.ALIGN_LEFT));
			cell.setBorderWidth(0);
			tab.addCell(createCell("(b) Rest", f1, Element.ALIGN_LEFT));
			cell.setBorderWidth(0);
			tab.addCell(createCell("", f, Element.ALIGN_LEFT));
			cell.setBorderWidth(0);
			tab.addCell(createCell("(c) Total", f1, Element.ALIGN_LEFT));
			cell.setBorderWidth(0);
			tab.addCell(createCell("", f, Element.ALIGN_LEFT));
			cell.setBorderWidth(0);
			cell.addElement(tab);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return cell;
	}
	
	public static PdfPCell nest2(){
		PdfPCell cell = new PdfPCell();
		Font f = new Font(Font.HELVETICA,8);
		try
		{
			PdfPTable tab = new PdfPTable(1);
			tab.setWidthPercentage(new float[]{100}, new Rectangle(100,100));
			cell.addElement(createPara("Name & Address of the   ___________________________________________", f, Element.ALIGN_CENTER));
			cell.setBorderWidth(0);
			cell.addElement(tab);
			cell.addElement(createPara("bank in which the amount __________________________________________", f, Element.ALIGN_CENTER));
			cell.setBorderWidth(0);
			cell.addElement(createPara("is remitted.                       _________________________________________", f, Element.ALIGN_CENTER));
			cell.setBorderWidth(0);
			cell.addElement(tab);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return cell;
	}
	
	public static PdfPCell nest3(){
		PdfPCell cell = new PdfPCell();
		Font f = new Font(Font.HELVETICA,8);
		Font f1 = new Font(Font.HELVETICA,10);
		
		try
		{
			PdfPTable tab = new PdfPTable(4);
			tab.setWidthPercentage(new float[]{55,15,15,15}, new Rectangle(100,100));
			tab.addCell(createCell("Details of Subscribers", f1, Element.ALIGN_LEFT));
			cell.setBorderWidth(0);
			tab.addCell(createCell("E.P.F.", f1, Element.ALIGN_CENTER));
			cell.setBorderWidth(0);
			tab.addCell(createCell("Pension Fund", f1, Element.ALIGN_CENTER));
			cell.setBorderWidth(0);
			tab.addCell(createCell("E.D.L.I.", f1, Element.ALIGN_CENTER));
			cell.setBorderWidth(0);
			tab.addCell(createCell("No. of Subscribers as per last month", f1, Element.ALIGN_LEFT));
			cell.setBorderWidth(0);
			tab.addCell(createCell("", f, Element.ALIGN_CENTER));
			cell.setBorderWidth(0);
			tab.addCell(createCell("", f, Element.ALIGN_CENTER));
			cell.setBorderWidth(0);
			tab.addCell(createCell("", f, Element.ALIGN_CENTER));
			cell.setBorderWidth(0);
			tab.addCell(createCell("No. of new Subscribers (Vide Form 5)", f1, Element.ALIGN_LEFT));
			cell.setBorderWidth(0);
			tab.addCell(createCell("", f, Element.ALIGN_CENTER));
			cell.setBorderWidth(0);
			tab.addCell(createCell("", f, Element.ALIGN_CENTER));
			cell.setBorderWidth(0);
			tab.addCell(createCell("", f, Element.ALIGN_CENTER));
			cell.setBorderWidth(0);
			tab.addCell(createCell("No. of Subscribers left Service (Vide Form 10)", f1, Element.ALIGN_LEFT));
			cell.setBorderWidth(0);
			tab.addCell(createCell("", f, Element.ALIGN_CENTER));
			cell.setBorderWidth(0);
			tab.addCell(createCell("", f, Element.ALIGN_CENTER));
			cell.setBorderWidth(0);
			tab.addCell(createCell("", f, Element.ALIGN_CENTER));
			cell.setBorderWidth(0);
			tab.addCell(createCell("(Nett.) Total No. of Subscribers", f1, Element.ALIGN_LEFT));
			cell.setBorderWidth(0);
			tab.addCell(createCell("", f, Element.ALIGN_CENTER));
			cell.setBorderWidth(0);
			tab.addCell(createCell("", f, Element.ALIGN_CENTER));
			cell.setBorderWidth(0);
			tab.addCell(createCell("", f, Element.ALIGN_CENTER));
			cell.setBorderWidth(0);
			cell.addElement(tab);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return cell;
	}
	
	public static PdfPCell nest4(){
		PdfPCell cell = new PdfPCell();
		Font f = new Font(Font.HELVETICA,10,Font.BOLD);
		try
		{
			PdfPTable tab = new PdfPTable(1);
			tab.setWidthPercentage(new float[]{100}, new Rectangle(100,100));
			cell.addElement(createPara("Signature of the Employer", f, Element.ALIGN_RIGHT));
			cell.setBorderWidth(0);
			cell.setBottom(0);
			cell.addElement(tab);
			cell.addElement(createPara("    with official (Seal)  ", f, Element.ALIGN_CENTER));
			cell.setBorderWidth(0);
			cell.setBottom(0);
			cell.addElement(tab);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return cell;
	}
	
	public static Paragraph createPara(String value,Font f,int alignment)
	{
		Paragraph para = new Paragraph(value,f);
		para.setAlignment(alignment);
		para.setSpacingAfter(0);
		para.setSpacingBefore(0);
		return para;
		
	}
	
	public static PdfPCell createCell(String value,Font f,int alignment)
	{
		PdfPCell cell = new PdfPCell(new Phrase(value,f));
		cell.addElement(createPara(value, f, alignment));
		cell.setBorderWidth(0.5F);
		
		return cell;
	}
}
