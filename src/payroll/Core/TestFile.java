package payroll.Core;


import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import payroll.DAO.ConnectionManager;
import payroll.DAO.LookupHandler;
import payroll.Model.Lookup;

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
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;

import com.lowagie.text.pdf.PdfWriter;

public class TestFile{
	static FileWriter FW;
	static FileWriter FW1;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("IN");
		Document doc = new Document();
		try
		{
			
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("d:/Form-3A.pdf"));
			doc.open();
			doc.setMargins(10, 72, 108, 180);
			
			doc.setPageSize(PageSize.A4);
			Font FONT = new Font(Font.HELVETICA, 52, Font.BOLD, new GrayColor(0.75f));
			ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("THE NASIK MARCHANT'S CO-OP BANK LTD.",FONT), 297.5f, 421, 45);
			
			PdfPTable tab = new PdfPTable(1);
			tab.setWidthPercentage(new float[]{100},new Rectangle(100, 100));
			PdfPCell cell = new PdfPCell();
			
			Font f1 = new Font(Font.HELVETICA,7);
			cell.addElement(createPara("FORM - 3A",new Font(Font.HELVETICA,10,Font.BOLD), Element.ALIGN_CENTER));
			cell.addElement(createPara("( Unexempted Establishment Only )", f1, Element.ALIGN_CENTER));
			cell.addElement(createPara("THE EMPLOYEE'S PROVIDENT FUND SCHEME, 1952", f1, Element.ALIGN_CENTER));
			cell.addElement(createPara("( Paras 35 and 42 ) and", f1, Element.ALIGN_CENTER));
			cell.addElement(createPara("THE EMPLOYEES PENSION SCHEME, 1995", f1, Element.ALIGN_CENTER));
			cell.addElement(createPara("Para 20", f1, Element.ALIGN_CENTER));
			cell.addElement(Chunk.NEWLINE);
			cell.addElement(createPara("Contribution Card for the currency period from ", new Font(Font.HELVETICA,7,Font.BOLD), Element.ALIGN_LEFT));
			tab.addCell(cell);
			cell = new PdfPCell();
			cell.addElement(createMainInfoTab(1));
			tab.addCell(cell);
			cell = new PdfPCell(createDataTable());
			tab.addCell(cell);
			cell = new PdfPCell();
			cell.addElement(createPara("Certified That the difference between the total of the contribution shown under the columns (3) % (4) of the above table and that arrived at on the total", f1, Element.ALIGN_LEFT));
			cell.addElement(createPara("Wages shown in Column (2) at the Prescribed rate is soley due to the rounding of the contributions to the nearest rupee under the rule.", f1, Element.ALIGN_LEFT));
			cell.addElement(createPara("* Contribution for the month of March paid in April", f1, Element.ALIGN_CENTER));
			cell.addElement(Chunk.NEWLINE);
			cell.addElement(createPara("IN THE CASE OF AN EMPLOYEE WHO HAS LEFT THE SERVICE OF THE COMPANY EARLIER, HIS CONTRIBUTION HAS BEEN", f1, Element.ALIGN_LEFT));
			cell.addElement(createPara("SHOWN IN THE SUBSEQUENT MONTH WHICH HIS FINAL SETTLEMENT OF SALARY HAS BEEN MADE.", f1, Element.ALIGN_LEFT));
			cell.addElement(Chunk.NEWLINE);
			cell.addElement(Chunk.NEWLINE);
			cell.addElement(createPara("Date : ", f1,Element.ALIGN_LEFT));
			cell.addElement(createPara("Signature of Employer", f1, Element.ALIGN_RIGHT));
			cell.addElement(createPara("(Office Seal)", f1, Element.ALIGN_RIGHT));
			tab.addCell(cell);
			cell = new PdfPCell();
			cell.addElement(createPara("Note : 1) In respect of the Form (3A) sent to the Regional office during the course of the currency period for the purpose od final", f1, Element.ALIGN_LEFT));
			cell.addElement(createPara("Settelment of the accounts of the members who had left service details of date and reasons for leaving service and", f1, Element.ALIGN_LEFT));
			cell.addElement(createPara("certificate as shown in the remark column should be added.", f1, Element.ALIGN_LEFT));
			cell.addElement(createPara("2) If there is no period of NCS, the word \"NIL\" to be mentioned against the total column.", f1, Element.ALIGN_LEFT));
			cell.addElement(createPara("3) Wherever no wages are shown against any month the period should be shown as NCS under Col 6.", f1,Element.ALIGN_LEFT));
			tab.addCell(cell);
			doc.add(tab);
		}
		catch(Exception e)
		{
			doc.close();
			e.printStackTrace();
		}
		doc.close();
		System.out.println("OUT");
	}
	
	public static Paragraph createPara(String value,Font f,int alignment)
	{
		Paragraph para = new Paragraph(value,f);
		para.setAlignment(alignment);
		para.setSpacingAfter(0);
		para.setSpacingBefore(0);
		return para;
		
	}
	
	public static PdfPTable createMainInfoTab(int empno)
	{
		PdfPTable tab = new PdfPTable(2);
		Font f1 = new Font(Font.HELVETICA,8);
		Connection con = ConnectionManager.getConnection();
		try
		{
			tab.setWidthPercentage(new float[]{65,35},new Rectangle(100, 100));
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM EMPMAST WHERE EMPNO="+empno);
			if(rs.next())
			{
				PdfPCell cell = new PdfPCell();
				cell.setBorder(0);
				cell.addElement(createPara("1. Account No.: "+rs.getString("PFNO"), f1, Element.ALIGN_LEFT));
				LookupHandler lkph = new LookupHandler();
				String name = lkph.getLKP_Desc("SALUTE", rs.getInt("SALUTE"))+" "+rs.getString("FNAME")+" "+rs.getString("MNAME")+" "+rs.getString("LNAME");
				cell.addElement(createPara("2. Name/Surname: "+name, f1, Element.ALIGN_LEFT));
				cell.addElement(createPara("3. Father / Husbands Name: "+(rs.getString("FHNAME")==null?"":rs.getString("FHNAME")), f1, Element.ALIGN_LEFT));
				cell.addElement(createPara("4. Name & Address of the Establishment : ", f1, Element.ALIGN_LEFT));
				cell.addElement(createPara("THE BUSINESS CO.OP BANK LTD.", new Font(Font.HELVETICA,9,Font.BOLD),Element.ALIGN_LEFT));
				cell.addElement(createPara("Rajan Complex Arcade, Datta Mandir, Nashik Road, Nashik - 422101", f1, Element.ALIGN_LEFT));
				cell.addElement(createPara("Tel : +91-20 26812190", f1, Element.ALIGN_LEFT));
				cell.addElement(createPara("Email : adm@namcobank.in", f1, Element.ALIGN_LEFT));
				tab.addCell(cell);
				
				cell = new PdfPCell();
				cell.setBorder(0);
				cell.addElement(createPara("5. Statutory Rate of Contribution : 12.00%", f1, Element.ALIGN_LEFT));
				cell.addElement(createPara("6. Voluntary higher rate od Employees Contribution, if any", f1, Element.ALIGN_LEFT));
				cell.addElement(createPara("7. Whether, opted to contribute on full salary for pension? Yes / No", f1, Element.ALIGN_LEFT));
				tab.addCell(cell);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return tab;
	}
	
	public static PdfPTable createDataTable()
	{
		PdfPTable tab = new PdfPTable(7);
		Font f1 = new Font(Font.HELVETICA,8,Font.BOLD);
		try 
		{
			tab.setWidthPercentage(new float[]{15,10,10,30,10,10,15}, new Rectangle(100,100));
			tab.addCell(createCell("Month", f1, Element.ALIGN_CENTER));
			tab.addCell(createCell("Amount of Wages Rs.", f1, Element.ALIGN_CENTER));
			tab.addCell(createCell("Worker's Share EPF", f1, Element.ALIGN_CENTER));
			tab.addCell(createCell("Employer Share", f1, Element.ALIGN_CENTER));
			tab.addCell(createCell("Refund of Advances", f1, Element.ALIGN_CENTER));
			tab.addCell(createCell("Non cont. services From..... To....", f1, Element.ALIGN_CENTER));
			tab.addCell(createCell("Remarks", f1, Element.ALIGN_CENTER));
			
			tab.addCell(createCell("1", f1, Element.ALIGN_CENTER));
			tab.addCell(createCell("2", f1, Element.ALIGN_CENTER));
			tab.addCell(createCell("3", f1, Element.ALIGN_CENTER));
			tab.addCell(createCell("4", f1, Element.ALIGN_CENTER));
			tab.addCell(createCell("5", f1, Element.ALIGN_CENTER));
			tab.addCell(createCell("6", f1, Element.ALIGN_CENTER));
			tab.addCell(createCell("7", f1, Element.ALIGN_CENTER));
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return tab;
	}
	
	public static PdfPCell createCell(String value,Font f,int alignment)
	{
		PdfPCell cell = new PdfPCell(new Phrase(value,f));
		cell.addElement(createPara(value, f, alignment));
		return cell;
	}
}
