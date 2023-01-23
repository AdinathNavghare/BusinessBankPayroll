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
import com.lowagie.text.Image;
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

public class Form6A extends PdfPageEventHelper
{
	public static void getForm6A(String filePath, String frmdate, String todate, String imagepath) throws IOException, SQLException, DocumentException 
	{
	 
	OutputStream file = new FileOutputStream(new File(filePath));
    Document document = new Document();//new Rectangle(842, 1191));
	 
    try {
    	PdfWriter.getInstance(document, file);
		document.open();
	    Image image1 = Image.getInstance(imagepath);
        image1.scaleAbsolute(80f, 80f);
		image1.setAbsolutePosition(258f, 730f); 
		document.add(image1);
		
			Phrase title = new Phrase("(FOR UNEXEMPTED ESTABLISHMENTS' ONLY)",new Font(Font.TIMES_ROMAN,8,Font.BOLD));
			Paragraph para = new Paragraph(title);
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingBefore(80);
			document.add(para);
			
			para = new Paragraph(new Phrase("FORM 6A",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
			para.setAlignment(Element.ALIGN_CENTER);			
			document.add(para);
			
			para = new Paragraph(new Phrase("THE EMPLOYEES' PROVIDENT FUNDS SCHEME, 1952",new Font(Font.TIMES_ROMAN,11,Font.BOLD)));
			para.setAlignment(Element.ALIGN_CENTER);			
			document.add(para);
			
			para = new Paragraph(new Phrase("(PARAGRAPH 43)",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
			para.setAlignment(Element.ALIGN_CENTER);			
			document.add(para);
			
			para = new Paragraph(new Phrase("AND",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
			para.setAlignment(Element.ALIGN_CENTER);			
			document.add(para);
			
			para = new Paragraph(new Phrase("AND THE EMPLOYEES' PENSION SCHEME, 1995",new Font(Font.TIMES_ROMAN,11,Font.BOLD)));
			para.setAlignment(Element.ALIGN_CENTER);
			document.add(para);
			
			para = new Paragraph(new Phrase("[PARAGRAPH 20 (4)]",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(15);			
			document.add(para);
		
			para = new Paragraph(new Phrase("Annual statement of contribution for the Currency period from   1st "+frmdate.substring(3,11)+"   to   31st "+todate.substring(3,11),new Font(Font.TIMES_ROMAN,9)));
 			para.setAlignment(Element.ALIGN_LEFT);
 			// para.setSpacingBefore(30.0f);
 			para.setSpacingAfter(5);
 			document.add(para);
 			
 			para = new Paragraph(new Phrase("Name & Address of the Establishment__________________________________________ Satutory rate of Contribution_______________",new Font(Font.TIMES_ROMAN,9)));
 			para.setAlignment(Element.ALIGN_LEFT);
 			para.setSpacingAfter(5);
 			document.add(para);
			
 			para = new Paragraph(new Phrase("Code No. of the Establishment__________________________ No. of members voluntarily contributing at a higher rate_______________",new Font(Font.TIMES_ROMAN,9)));
 			para.setAlignment(Element.ALIGN_LEFT);
 			para.setSpacingAfter(0);
 			document.add(para);
			
 			PdfPTable table=new PdfPTable(10);
			table.setSpacingBefore(10.0f);
			table.setWidthPercentage(new float[]{5,13,12,14,12,11,9,8,8,8},new Rectangle(100,100));
			PdfPCell cell1=new PdfPCell(new Phrase("SR NO",new Font(Font.TIMES_ROMAN,10)));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell1);
			PdfPCell cell2=new PdfPCell(new Phrase("Account No.",new Font(Font.TIMES_ROMAN,10)));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell2);
			PdfPCell cell3=new PdfPCell(new Phrase("Name of Member (in block letters)",new Font(Font.TIMES_ROMAN,10)));
			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell3);
			PdfPCell cell4=new PdfPCell(new Phrase("Wages, retaining allowance (if any) & DA including cash value of food cuncession paid during the currency period.",new Font(Font.TIMES_ROMAN,10)));
			cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell4);
			PdfPCell cell5=new PdfPCell(new Phrase("Amount of Worker's contribution deducted from the wages   (EPF)",new Font(Font.TIMES_ROMAN,10)));
			cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell5);
			PdfPCell cell6 = nestedTable();
			cell6.setColspan(2);
			cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell6);
			PdfPCell cell7=new PdfPCell(new Phrase("Refund of Advance",new Font(Font.TIMES_ROMAN,10)));
			cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell7);
			PdfPCell cell8=new PdfPCell(new Phrase("Rate of higher voluntary contribution (if any)",new Font(Font.TIMES_ROMAN,10)));
			cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell8);
			PdfPCell cell9=new PdfPCell(new Phrase("Remarks",new Font(Font.TIMES_ROMAN,10)));
			cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell9);
 			
			for(int i =0 ; i<25 ; i++){
				
				PdfPCell cell10=new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
				cell10.setFixedHeight(15);
				cell10.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell10);
				PdfPCell cell11=new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell11);
				PdfPCell cell12=new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
				cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell12);
				PdfPCell cell13=new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
				cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell13);
				PdfPCell cell14=new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
				cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell14);
				PdfPCell cell15=new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
				cell15.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell15);
				PdfPCell cell16=new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
				cell16.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell16);
				PdfPCell cell17=new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
				cell17.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell17);
				PdfPCell cell18=new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
				cell18.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell18);
				PdfPCell cell19=new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
				cell19.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell19);
				PdfPCell cell20=new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
				cell20.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell20);
			}
			
			document.add(table);
			document.newPage();
			
			PdfPTable nested2 = new PdfPTable(2);
		    nested2.setWidthPercentage(new float[]{78,22},new Rectangle(96,100));
		    PdfPCell nes = new PdfPCell();
		    nes.setBorderWidth(0);
		    nested2.addCell(nest3());
		    nested2.addCell(nest4());
		    document.add(nested2);
			
		    para = new Paragraph(new Phrase("Reconciliation of Remittances",new Font(Font.TIMES_ROMAN,10)));
		    para.setSpacingBefore(30);
			para.setAlignment(Element.ALIGN_LEFT);
			para.setSpacingAfter(0);
			document.add(para);
			
			para = new Paragraph(new Phrase("Signature of Employer (with office seal)",new Font(Font.TIMES_ROMAN,10)));
			para.setAlignment(Element.ALIGN_RIGHT);
			para.setSpacingAfter(10);			
			document.add(para);
			
			para = new Paragraph(new Phrase("NOTE :-  (1)  The names of all members, including those who had left service during the currency period, should be included in this statement. " +
											"              		  Where the Form 3-A in respect of such members had left service, the fact should be stated against the members in the 'Remarks' column.",new Font(Font.TIMES_ROMAN,9)));
 			para.setAlignment(Element.ALIGN_LEFT);
 			para.setSpacingAfter(5);
 			document.add(para);
			
 			para = new Paragraph(new Phrase("         (2)  In case of substantial variation in the wages/contribution of any members as compared to those shown in previous months statement, " +
 											"              		  the reason should be explained adequately in the 'Remarks' column.",new Font(Font.TIMES_ROMAN,9)));
 			para.setAlignment(Element.ALIGN_LEFT);
 			para.setSpacingAfter(5);
 			document.add(para);
 			
 			para = new Paragraph(new Phrase("         (3)  In respect of those members who have not opted for Pension Fund their entire employers contribution @ 81/3% or 10% as the case " +
 											"              			may be shown under column No. 6.",new Font(Font.TIMES_ROMAN,9)));
 			para.setAlignment(Element.ALIGN_LEFT);
 			para.setSpacingAfter(5);
 			document.add(para);
 			
 			
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
			PdfPTable tab = new PdfPTable(2);
			tab.setWidthPercentage(new float[]{55,45}, new Rectangle(100,100));
			
			PdfPCell cell1 = new PdfPCell(new Phrase("Employer's Contribution w.e.f.  16-11-1995",new Font(Font.TIMES_ROMAN,10)));
			cell1.setColspan(2);
			tab.addCell(cell1);
			cell.setBorder(1);
			
			PdfPCell cell2 = new PdfPCell(new Phrase("EPF difference between 10% &    81/3 %",new Font(Font.TIMES_ROMAN,10)));
			tab.addCell(cell2);
			
			PdfPCell cell3 = new PdfPCell(new Phrase("Pension Fund 10% &    81/3 % ",new Font(Font.TIMES_ROMAN,10)));
			tab.addCell(cell3);
			
			cell.addElement(tab);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return cell;
	}
	
	public static PdfPCell nestedTable2(){
		PdfPCell cell = new PdfPCell();
		//Font f = new Font(Font.HELVETICA,8);
		//Font f1 = new Font(Font.HELVETICA,10,Font.BOLD);
		
		try
		{
			PdfPTable tab = new PdfPTable(4);
			tab.setWidthPercentage(new float[]{27,27,23,23}, new Rectangle(100,100));
			
			PdfPCell cell1 = new PdfPCell(new Phrase("Amount remitted",new Font(Font.TIMES_ROMAN,10)));
			cell1.setColspan(2);
			tab.addCell(cell1);
			cell.setBorder(1);
			
			PdfPCell cell2 = new PdfPCell(new Phrase("(Admn Charges Rs. at 0.65% of wages)",new Font(Font.TIMES_ROMAN,10)));
			cell2.setColspan(2);
			tab.addCell(cell2);
			
			PdfPCell cell3 = new PdfPCell(new Phrase("EPF Contributions including refund of advances A/c No. 1",new Font(Font.TIMES_ROMAN,10)));
			tab.addCell(cell3);
			
			PdfPCell cell4 = new PdfPCell(new Phrase("Pension Fund Contributions A/c No. 10",new Font(Font.TIMES_ROMAN,10)));
			tab.addCell(cell4);
			
			PdfPCell cell5 = new PdfPCell(new Phrase("DLI Contributions A/c No. 21",new Font(Font.TIMES_ROMAN,10)));
			tab.addCell(cell5);
			
			PdfPCell cell6 = new PdfPCell(new Phrase("Admn Charges A/c No. 21",new Font(Font.TIMES_ROMAN,10)));
			tab.addCell(cell6);
			
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
		
		try
		{
			PdfPTable table=new PdfPTable(8);
			table.setSpacingBefore(10.0f);
			table.setWidthPercentage(new float[]{6,12,16,16,14,14,11,11},new Rectangle(100,100));
			
			PdfPCell cell1=new PdfPCell(new Phrase("SR NO",new Font(Font.TIMES_ROMAN,10)));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell1);
			PdfPCell cell2=new PdfPCell(new Phrase("Month",new Font(Font.TIMES_ROMAN,10)));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell2);
			PdfPCell cell3 = nestedTable2();
			cell3.setColspan(4);
			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell3);
			PdfPCell cell4=new PdfPCell(new Phrase("EDLI. ADM Charges 0.01% A/c No. 22",new Font(Font.TIMES_ROMAN,10)));
			cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell4);
			PdfPCell cell5=new PdfPCell(new Phrase("Cols 5,6,7 Rs  Aggregate() contributions()",new Font(Font.TIMES_ROMAN,10)));
			cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell5);
			
			PdfPCell cell10=new PdfPCell(new Phrase("1.",new Font(Font.TIMES_ROMAN,10)));
			cell10.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell10);
			PdfPCell cell11=new PdfPCell(new Phrase("Mar. Paid in Apr.",new Font(Font.TIMES_ROMAN,10)));
			cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell11);
			PdfPCell cell12=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell12.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell12);
			PdfPCell cell13=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell13.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell13);
			PdfPCell cell14=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell14.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell14);
			PdfPCell cell15=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell15.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell15);
			PdfPCell cell16=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell16.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell16);
			PdfPCell cell17=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell17.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell17);
			
			PdfPCell cell20=new PdfPCell(new Phrase("2.",new Font(Font.TIMES_ROMAN,10)));
			cell20.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell20);
			PdfPCell cell21=new PdfPCell(new Phrase("May",new Font(Font.TIMES_ROMAN,10)));
			cell21.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell21);
			PdfPCell cell22=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell22.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell22);
			PdfPCell cell23=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell23.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell23);
			PdfPCell cell24=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell24.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell24);
			PdfPCell cell25=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell25.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell25);
			PdfPCell cell26=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell26.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell26);
			PdfPCell cell27=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell27.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell27);
			
			PdfPCell cell30=new PdfPCell(new Phrase("3.",new Font(Font.TIMES_ROMAN,10)));
			cell30.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell30);
			PdfPCell cell31=new PdfPCell(new Phrase("June",new Font(Font.TIMES_ROMAN,10)));
			cell31.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell31);
			PdfPCell cell32=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell32.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell32);
			PdfPCell cell33=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell33.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell33);
			PdfPCell cell34=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell34.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell34);
			PdfPCell cell35=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell35.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell35);
			PdfPCell cell36=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell36.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell36);
			PdfPCell cell37=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell37.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell37);
			
			PdfPCell cell40=new PdfPCell(new Phrase("4.",new Font(Font.TIMES_ROMAN,10)));
			cell40.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell40);
			PdfPCell cell41=new PdfPCell(new Phrase("July",new Font(Font.TIMES_ROMAN,10)));
			cell41.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell41);
			PdfPCell cell42=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell42.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell42);
			PdfPCell cell43=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell43.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell43);
			PdfPCell cell44=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell44.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell44);
			PdfPCell cell45=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell45.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell45);
			PdfPCell cell46=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell46.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell46);
			PdfPCell cell47=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell47.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell47);
			
			PdfPCell cell50=new PdfPCell(new Phrase("5.",new Font(Font.TIMES_ROMAN,10)));
			cell50.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell50);
			PdfPCell cell51=new PdfPCell(new Phrase("August",new Font(Font.TIMES_ROMAN,10)));
			cell51.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell51);
			PdfPCell cell52=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell52.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell52);
			PdfPCell cell53=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell53.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell53);
			PdfPCell cell54=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell54.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell54);
			PdfPCell cell55=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell55.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell55);
			PdfPCell cell56=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell56.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell56);
			PdfPCell cell57=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell57.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell57);
			
			PdfPCell cell60=new PdfPCell(new Phrase("6.",new Font(Font.TIMES_ROMAN,10)));
			cell60.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell60);
			PdfPCell cell61=new PdfPCell(new Phrase("Sepember",new Font(Font.TIMES_ROMAN,10)));
			cell61.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell61);
			PdfPCell cell62=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell62.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell62);
			PdfPCell cell63=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell63.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell63);
			PdfPCell cell64=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell64.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell64);
			PdfPCell cell65=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell65.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell65);
			PdfPCell cell66=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell66.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell66);
			PdfPCell cell67=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell67.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell67);
			
			PdfPCell cell70=new PdfPCell(new Phrase("7.",new Font(Font.TIMES_ROMAN,10)));
			cell70.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell70);
			PdfPCell cell71=new PdfPCell(new Phrase("October",new Font(Font.TIMES_ROMAN,10)));
			cell71.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell71);
			PdfPCell cell72=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell72.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell72);
			PdfPCell cell73=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell73.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell73);
			PdfPCell cell74=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell74.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell74);
			PdfPCell cell75=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell75.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell75);
			PdfPCell cell76=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell76.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell76);
			PdfPCell cell77=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell77.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell77);
			
			PdfPCell cell80=new PdfPCell(new Phrase("8.",new Font(Font.TIMES_ROMAN,10)));
			cell80.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell80);
			PdfPCell cell81=new PdfPCell(new Phrase("November",new Font(Font.TIMES_ROMAN,10)));
			cell81.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell81);
			PdfPCell cell82=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell82.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell82);
			PdfPCell cell83=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell83.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell83);
			PdfPCell cell84=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell84.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell84);
			PdfPCell cell85=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell85.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell85);
			PdfPCell cell86=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell86.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell86);
			PdfPCell cell87=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell87.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell87);
			
			PdfPCell cell90=new PdfPCell(new Phrase("9.",new Font(Font.TIMES_ROMAN,10)));
			cell90.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell90);
			PdfPCell cell91=new PdfPCell(new Phrase("December",new Font(Font.TIMES_ROMAN,10)));
			cell91.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell91);
			PdfPCell cell92=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell92.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell92);
			PdfPCell cell93=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell93.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell93);
			PdfPCell cell94=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell94.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell94);
			PdfPCell cell95=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell95.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell95);
			PdfPCell cell96=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell96.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell96);
			PdfPCell cell97=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell97.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell97);
			
			PdfPCell cell100=new PdfPCell(new Phrase("10.",new Font(Font.TIMES_ROMAN,10)));
			cell100.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell100);
			PdfPCell cell101=new PdfPCell(new Phrase("January",new Font(Font.TIMES_ROMAN,10)));
			cell101.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell101);
			PdfPCell cell102=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell102.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell102);
			PdfPCell cell103=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell103.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell103);
			PdfPCell cell104=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell104.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell104);
			PdfPCell cell105=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell105.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell105);
			PdfPCell cell106=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell106.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell106);
			PdfPCell cell107=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell107.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell107);
			
			PdfPCell cell110=new PdfPCell(new Phrase("11.",new Font(Font.TIMES_ROMAN,10)));
			cell110.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell110);
			PdfPCell cell111=new PdfPCell(new Phrase("February",new Font(Font.TIMES_ROMAN,10)));
			cell111.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell111);
			PdfPCell cell112=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell112.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell112);
			PdfPCell cell113=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell113.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell113);
			PdfPCell cell114=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell114.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell114);
			PdfPCell cell115=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell115.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell115);
			PdfPCell cell116=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell116.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell116);
			PdfPCell cell117=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell117.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell117);
			
			PdfPCell cell120=new PdfPCell(new Phrase("12.",new Font(Font.TIMES_ROMAN,10)));
			cell120.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell120);
			PdfPCell cell121=new PdfPCell(new Phrase("Feb. Paid in Mar.",new Font(Font.TIMES_ROMAN,10)));
			cell121.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell121);
			PdfPCell cell122=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell122.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell122);
			PdfPCell cell123=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell123.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell123);
			PdfPCell cell124=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell124.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell124);
			PdfPCell cell125=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell125.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell125);
			PdfPCell cell126=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell126.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell126);
			PdfPCell cell127=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell127.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell127);
			
			PdfPCell cell130=new PdfPCell(new Phrase("13.",new Font(Font.TIMES_ROMAN,10)));
			cell130.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell130);
			PdfPCell cell131=new PdfPCell(new Phrase("Arrear if any",new Font(Font.TIMES_ROMAN,10)));
			cell131.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell131);
			PdfPCell cell132=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell132.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell132);
			PdfPCell cell133=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell133.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell133);
			PdfPCell cell134=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell134.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell134);
			PdfPCell cell135=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell135.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell135);
			PdfPCell cell136=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell136.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell136);
			PdfPCell cell137=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell137.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell137);
			
			PdfPCell cell140=new PdfPCell(new Phrase("14.",new Font(Font.TIMES_ROMAN,10)));
			cell140.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell140);
			PdfPCell cell141=new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
			cell141.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell141);
			PdfPCell cell142=new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
			cell142.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell142);
			PdfPCell cell143=new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
			cell143.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell143);
			PdfPCell cell144=new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
			cell144.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell144);
			PdfPCell cell145=new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
			cell145.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell145);
			PdfPCell cell146=new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
			cell146.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell146);
			PdfPCell cell147=new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
			cell147.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell147);
			
			PdfPCell cell150=new PdfPCell(new Phrase("Total",new Font(Font.TIMES_ROMAN,10)));
			cell150.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell150);
			PdfPCell cell151=new PdfPCell(new Phrase("",new Font(Font.TIMES_ROMAN,10)));
			cell151.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell151);
			PdfPCell cell152=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell152.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell152);
			PdfPCell cell153=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell153.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell153);
			PdfPCell cell154=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell154.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell154);
			PdfPCell cell155=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell155.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell155);
			PdfPCell cell156=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell156.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell156);
			PdfPCell cell157=new PdfPCell(new Phrase("Rs.",new Font(Font.TIMES_ROMAN,10)));
			cell157.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell157);
			
			cell.addElement(table);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return cell;
	}
	
	public static PdfPCell nest4(){
		PdfPCell cell = new PdfPCell();
		Font f = new Font(Font.HELVETICA,9);
		try
		{
			PdfPTable tab = new PdfPTable(1);
			tab.setWidthPercentage(new float[]{100}, new Rectangle(100,100));
			cell.addElement(createPara("1.   Total number of contribution cards eclosed (Form 3A____________) \t\t", f, Element.ALIGN_LEFT));
			cell.setBorderWidth(0);
			cell.addElement(tab);
			
			cell.addElement(createPara("2.   Certified that Form 3A duly completed, of all the members listed in this statement are enclosed. The F3A already sent during the course of the currency period of the final settlement" +
										" of the concerned member's account vide 'Remarks' furnished against the names of the respective member's above, are also enclosed.", f, Element.ALIGN_LEFT));
			cell.setBorderWidth(0);
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
