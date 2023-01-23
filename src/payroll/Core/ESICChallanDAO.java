package payroll.Core;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
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

public class ESICChallanDAO extends PdfPageEventHelper
{
	private static float emp_esic=0;
	private static float empr_esic=0;
	private static String month="";

	public static void getesicchallan(String date1,String filepath,String table_name) 
		{
		
			Document doc = new Document();
			try
			{
				table_name=table_name.equalsIgnoreCase("before")?"PAYTRAN":"PAYTRAN_STAGE";
				Connection con =null,conn=null;
				Statement st = null;
				ResultSet rs = null;
				//11-mar-2015
				month=(String) date1.subSequence(3, 11);
				int total_subs=0;
				float tot_wages=0;
				conn = ConnectionManager.getConnection();
				st=conn.createStatement();
				rs=st.executeQuery("select Count(Distinct(empno)) from "+table_name+" where trncd=221 and trndt between '"+ReportDAO.BOM(date1)+"' and '"+ReportDAO.EOM(date1)+"' ");
				while(rs.next())
				{
					total_subs=rs.getInt(1);
				}
				ResultSet rs2=st.executeQuery("select trncd,Sum(cal_amt) from "+table_name+" where trncd in(199,221,236) and trndt between '"+ReportDAO.BOM(date1)+"'" +
						" and '"+ReportDAO.EOM(date1)+"' and empno in(select DISTINCT(empno) from "+table_name+" where trncd=221 and trndt between '"+ReportDAO.BOM(date1)+"' and '"+ReportDAO.EOM(date1)+"') group by TRNCD ");
				while(rs2.next())
				{
					if(rs2.getInt("trncd")==199)
					{
						tot_wages=(int)rs2.getFloat(2);
					}
					else if(rs2.getInt("trncd")==221)
					{
						emp_esic=(int)rs2.getFloat(2);
					}

					else if(rs2.getInt("trncd")==236)
					{
						empr_esic=(int)rs2.getFloat(2);
					}
				
				
				}
				
				
				
				
				SimpleDateFormat fromf = new SimpleDateFormat("dd-MMM-yyyy");
				SimpleDateFormat tof = new SimpleDateFormat("yyyy-MM-dd");
				Date dt = fromf.parse(date1);
				String dt1 = tof.format(dt);
				//dt = fromf.parse(date2);
				String dt2 = tof.format(dt);
				Font f1 = new Font(Font.HELVETICA,9,Font.BOLD);
				Font f3 = new Font(Font.HELVETICA,9,Font.BOLD|Font.UNDERLINE);
				Font f2 = new Font(Font.HELVETICA,9);
				PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(filepath));
				//writer.setPageEvent(new Form3A());
				//writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
				doc.open();
				doc.setPageSize(PageSize.A4);
							
				PdfPTable tab1 = new PdfPTable(1);
				tab1.setWidthPercentage(new float[]{100},new Rectangle(100,100));
				PdfPCell cell = new PdfPCell();
				
				
				
				PdfPTable nestedTable = new PdfPTable(2);
				nestedTable.setWidthPercentage(new float[]{45,55},new Rectangle(100,100));
			    PdfPCell c2;
				c2 = new PdfPCell(new Phrase("E.S.I.C",f1));  
				c2.setHorizontalAlignment(Element.ALIGN_LEFT);
				c2.setBorderWidth(0);
				nestedTable.addCell(c2);
				
				c2 = new PdfPCell(new Phrase("Challan No.",f1));  
				c2.setHorizontalAlignment(Element.ALIGN_LEFT);
				c2.setBorderWidth(0);
				nestedTable.addCell(c2);
			    
			    cell.addElement(nestedTable);
				
				cell.addElement(createPara("ORIGINAL", f1, Element.ALIGN_LEFT));
				cell.addElement(createPara("DUPLICATE", f1, Element.ALIGN_LEFT));
				cell.addElement(createPara("TRIPLICATE", f1, Element.ALIGN_LEFT));
				cell.addElement(createPara("QUADRIPLICATE", f1, Element.ALIGN_LEFT));
				cell.addElement(createPara("( For Depositor to be attached with return of contributions )", f1, Element.ALIGN_LEFT));
				cell.addElement(createPara("EMPLOYEE'S STATE INSURANCE FUND ACCOUNT NO.1", f3, Element.ALIGN_CENTER));
				cell.addElement(createPara("PAY - IN - SLIP FOR CONTRIBUTION ", f1, Element.ALIGN_CENTER));
				cell.setBorderWidth(0);
				tab1.addCell(cell);
				tab1.setSpacingAfter(15);
				doc.add(tab1);
				
				
				PdfPTable tab2 = new PdfPTable(1);
				tab2.setWidthPercentage(new float[]{100},new Rectangle(100,100));
				PdfPCell cell1 = new PdfPCell();
				
				PdfPTable nestedTable1 = new PdfPTable(2);
				nestedTable1.setWidthPercentage(new float[]{50,50},new Rectangle(100,100));
			    PdfPCell c3;
			    c3 = new PdfPCell(new Phrase("Station :",f1));  
			    c3.setHorizontalAlignment(Element.ALIGN_LEFT);
			    c3.setBorderWidth(0);
				nestedTable1.addCell(c3);
				
				c3 = new PdfPCell(new Phrase("Dated : -   "+date1,f1));  
				c3.setHorizontalAlignment(Element.ALIGN_RIGHT);
				c3.setBorderWidth(0);
				nestedTable1.addCell(c3);
			    
				cell1.addElement(nestedTable1);
			    cell1.setBorderWidth(0);
			    
			    tab2.addCell(cell1);
			    
			    PdfPTable nested2 =new PdfPTable(2);
			    nested2.setWidthPercentage(new float[]{40,60},new Rectangle(100,100));
			    PdfPCell nes = new PdfPCell();
			    nested2.addCell(nest1());
			    nes.setBorderWidth(0);
			    nested2.addCell(nest2());
			    nes.setBorderWidth(0);
			    tab2.addCell(nested2);
			    tab2.setSpacingAfter(15);
			    doc.add(tab2);
			    
			    
				doc.add(createPara("Employer's Code :", f1, Element.ALIGN_LEFT));
				
				PdfPTable tab3 =new PdfPTable(2);
				tab3.setSpacingBefore(15);
				tab3.setWidthPercentage(new float[]{50,50},new Rectangle(100,100));
				PdfPCell c6;
				c6 = new PdfPCell(new Phrase("Name & Address of Factory/Establishment",f1));  
				c6.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab3.addCell(c6);
				
				c6 = new PdfPCell(new Phrase("Deposited By :THE NASIK MARCHANT'S CO-OP BANK LTD.",f1));  
				c6.setHorizontalAlignment(Element.ALIGN_LEFT);
				c6.setBorderWidth(0);
				tab3.addCell(c6);
				
				
				PdfPTable nesting = new PdfPTable(1);
				nesting.setWidthPercentage(new float[]{100},new Rectangle(100,100));
				PdfPCell nt;
				nt= new PdfPCell(createPara("Rajan Complex Arcade, Datta Mandir, Nashik Road, Nashik - 422101", f1, Element.ALIGN_LEFT));
				nt.setBorderWidthBottom(0);
				nesting.addCell(nt);
				nt=new PdfPCell(createPara("Tel : +91-20 26812190", f1, Element.ALIGN_LEFT));
				nt.setBorderWidthTop(0);
				nt.setBorderWidthBottom(0);
				nesting.addCell(nt);
				nt=new PdfPCell(createPara("Email : adm@namcobank.in", f1, Element.ALIGN_LEFT));
				nt.setBorderWidthTop(0);
				nesting.addCell(nt);
				c6.addElement(nesting);
				tab3.addCell(c6);
				
				c6 = new PdfPCell(new Phrase("AUTHORIZED SIGNATURE.",f1));
				c6.setHorizontalAlignment(Element.ALIGN_RIGHT);
				c6.setVerticalAlignment(Element.ALIGN_BOTTOM);
				
				c6.setBorderWidth(0);
				tab3.addCell(c6);
			    
			    
				doc.add(tab3);
				
				
				PdfPTable tab4 =new PdfPTable(2);
				tab4.setWidthPercentage(new float[]{50,50},new Rectangle(100,100));
				PdfPCell cell4 ;
				tab4.setSpacingBefore(10);
				cell4=new PdfPCell(createPara("No. of Employee's :", f1, Element.ALIGN_RIGHT));
				cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell4.setBorderWidthLeft(0);
				cell4.setBorderWidthTop(1);
				cell4.setBorderWidthRight(0);
				cell4.setBorderWidthBottom(0);
				tab4.addCell(cell4);
				cell4=new PdfPCell(createPara(""+total_subs, f1, Element.ALIGN_LEFT));
				cell4.setBorderWidthLeft(0);
				cell4.setBorderWidthBottom(0);
				cell4.setBorderWidthRight(0);
				cell4.setBorderWidthTop(1);
				cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab4.addCell(cell4);
				cell4=new PdfPCell(createPara("Total wages:", f1, Element.ALIGN_RIGHT));
				cell4.setBorderWidth(0);
				cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tab4.addCell(cell4);
				cell4=new PdfPCell(createPara(""+tot_wages, f1, Element.ALIGN_LEFT));
				cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell4.setBorderWidth(0);
				tab4.addCell(cell4);
				cell4=new PdfPCell(createPara("Employee's Contribution (1.75 %) Rs.:", f1, Element.ALIGN_RIGHT));
				cell4.setBorderWidth(0);
				cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tab4.addCell(cell4);
				cell4=new PdfPCell(createPara(""+emp_esic, f1, Element.ALIGN_LEFT));
				cell4.setBorderWidth(0);
				cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab4.addCell(cell4);
				cell4=new PdfPCell(createPara("Employer's Contribution (4.75 %) Rs.:", f1, Element.ALIGN_RIGHT));
				cell4.setBorderWidth(0);
				cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tab4.addCell(cell4);
				cell4=new PdfPCell(createPara(""+empr_esic, f1, Element.ALIGN_LEFT));
				cell4.setBorderWidth(0);
				cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab4.addCell(cell4);
				cell4=new PdfPCell(createPara("Total Rs.:", f1, Element.ALIGN_LEFT));
				cell4.setBorderWidthBottom(1);
				cell4.setBorderWidthRight(0);
				cell4.setBorderWidthTop(0);
				cell4.setBorderWidthLeft(0);
				cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
				tab4.addCell(cell4);
				cell4=new PdfPCell(createPara(""+(emp_esic+empr_esic), f1, Element.ALIGN_LEFT));
				cell4.setBorderWidthBottom(1);
				cell4.setBorderWidthRight(0);
				cell4.setBorderWidthTop(0);
				cell4.setBorderWidthLeft(0);
				cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab4.addCell(cell4);
				doc.add(tab4);

				PdfPTable tab5 = new PdfPTable(1);
				tab5.setWidthPercentage(new float[]{100},new Rectangle(100,100));
				PdfPCell cell5 =new PdfPCell();
				tab5.setSpacingBefore(10);
				
				PdfPTable inter = new PdfPTable(3);
				inter.setWidthPercentage(new float[]{33,33,34},new Rectangle(100,100));
				PdfPCell in;
				inter.setSpacingBefore(10);
				in = new PdfPCell(createPara("(For use in Bank)", f1, Element.ALIGN_LEFT));
				in.setHorizontalAlignment(Element.ALIGN_CENTER);
				in.setBorderWidth(0);
				inter.addCell(in);
				
				in = new PdfPCell(createPara("ACKNOWLEDGEMENT", f1, Element.ALIGN_LEFT));
				in.setHorizontalAlignment(Element.ALIGN_CENTER);
				in.setBorderWidth(0);
				inter.addCell(in);
				
				in = new PdfPCell(createPara("( TO BE FILLED BY DEPOSITOR )", f1, Element.ALIGN_LEFT));
				in.setHorizontalAlignment(Element.ALIGN_CENTER);
				in.setBorderWidth(0);
				inter.addCell(in);
				
				cell5.addElement(inter);
				cell5.addElement(createPara("Received payment with Cash/Cheque/Draft No. dated  for Rs. "+(emp_esic+empr_esic)+" Drawan on The (Bank) in favour of Employee's State Insurance Fund Account No.1SI.No. in Bank's Scroll ______________", f2, Element.ALIGN_JUSTIFIED));
				cell5.setBorderWidth(0);
				tab5.addCell(cell5);
				doc.add(tab5);
				
				PdfPTable tab6 = new PdfPTable(2);
				tab6.setWidthPercentage(new float[]{50,50},new Rectangle(100,100));
				PdfPCell cell6 = new PdfPCell();
				tab6.setSpacingBefore(50);
				
				cell6=new PdfPCell(createPara("Dated :_______________", f1, Element.ALIGN_LEFT));
				cell6.setBorderWidth(0);
				tab6.addCell(cell6);
				cell6.addElement(createPara("Authorised signatory of the receiving Bank", f1, Element.ALIGN_RIGHT));
				cell6.setBorderWidth(0);
				
				tab6.addCell(cell6);
				doc.add(tab6);
				
				
			}
			catch(Exception e)
			{
				doc.close();
				e.printStackTrace();
			}
			doc.close();
					
		emp_esic=0;empr_esic=0;
		month="";
				
		}
		
		public static PdfPCell nest1(){
			PdfPCell cell = new PdfPCell();
			Font f = new Font(Font.HELVETICA,8);
			Font f1 = new Font(Font.HELVETICA,10,Font.BOLD);
			
			try
			{
				PdfPTable tab = new PdfPTable(2);
				tab.setWidthPercentage(new float[]{50,50}, new Rectangle(100,100));
				tab.addCell(createCell("Perticulars of Cash /Cheque No", f1, Element.ALIGN_CENTER));
				cell.setBorderWidth(0);
				tab.addCell(createCell("Amount Rs.", f1, Element.ALIGN_CENTER));
				cell.setBorderWidth(0);
				tab.addCell(createCell("Cash", f1, Element.ALIGN_CENTER));
				cell.setBorderWidth(0);
				tab.addCell(createCell(""+(emp_esic+empr_esic), f, Element.ALIGN_CENTER));
				cell.setBorderWidth(0);
				tab.addCell(createCell("TOTAL:-", f1, Element.ALIGN_CENTER));
				cell.setBorderWidth(0);
				tab.addCell(createCell(""+(emp_esic+empr_esic), f, Element.ALIGN_CENTER));
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
				cell.addElement(createPara("Paid into the credit of the Employee's State insurance Fund A/c.No.1 Rs.", f, Element.ALIGN_LEFT));
				cell.setBorderWidth(0);
				cell.addElement(tab);
				tab.setSpacingBefore(5);
				tab.setSpacingAfter(5);
				cell.addElement(createPara("", f, Element.ALIGN_LEFT));
				cell.addElement(createPara("In Cash/by Cheque (on realisation ) for payment of contribution as per details given below under the Employee's State Insurance Act,1948 for Period :"+month, f, Element.ALIGN_LEFT));
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


