package payroll.Core;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import org.jfree.data.Value;
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
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;

import payroll.DAO.LookupHandler;
import payroll.Model.RepoartBean;

public class TravelReportDAO {
	
	static Font F10Bold = new Font(Font.TIMES_ROMAN,10,Font.BOLD);
	public static void PDFReport(int feno1,int trcode,String filepath,String imgpath){
	//public static void PDFReport(int TRCODE,String filepath,String imgpath){
		
		System.out.println("in final pdf module printing started");
		
		RepoartBean repBean  = new RepoartBean();
		Connection con =null;
		Statement st = null;
		ResultSet rs = null;
		ResultSet trn = null;
		try{
		ReportDAO.OpenCon("", "", "",repBean);
		con = repBean.getCn();	
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//yyyy-MM-dd'T'HH:mm:ss
	    SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");
	    
	
		LookupHandler lh = new LookupHandler();
		Document doc = new Document();
		PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(filepath));
		doc.open();
		doc.setPageSize(PageSize.A4);
		
		Font FONT = new Font(Font.HELVETICA, 32, Font.NORMAL, new GrayColor(0.75f));
		ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Ch.Shahu Hospital",FONT), 297.5f, 421, 45);
		
		Image image1 = Image.getInstance(imgpath);
		
		Phrase title = new Phrase("THE BUSINESS CO.OP BANK LTD.",new Font(FONT.TIMES_ROMAN,14,Font.BOLD));
		Paragraph para = new Paragraph(title);
		para.setAlignment(Element.ALIGN_CENTER);
		para.setSpacingBefore(0);
		
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
		para = new Paragraph(new Phrase("Email : adm@namcobank.in",new Font(Font.TIMES_ROMAN,10)));
		para.setAlignment(Element.ALIGN_CENTER);
		para.setSpacingAfter(0);
		
		doc.add(para);
		para = new Paragraph(new Phrase("Tour Expenses Form ",new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
		para.setAlignment(Element.ALIGN_CENTER);
		para.setSpacingAfter(0);
		
		doc.add(para);
		//main table start

			st=con.createStatement();
			System.out.println("emp no is "+feno1);
			System.out.println("travelcode is "+trcode);
			
			rs = st.executeQuery("select c.lkp_disc ,m.fname,m.mname,m.lname,a.LKP_disc as desgn,b.lkp_disc as dept," +
					"m.empcode,t.tourrpt,t.startdate,t.starttime,t.enddate,t.endtime from emptran as e,travelmaster t," +
					"lookup a,lookup b,lookup c,empmast m where a.LKP_CODE='desig' and e.desig = a.LKP_SRNO and " +
					"b.LKP_CODE='dept' and e.dept = b.LKP_SRNO and c.LKP_CODE = 'salute'and m.salute = c.LKP_SRNO and" +
					" e.empno ="+feno1+" and t.empno="+feno1+" and m.empno ="+feno1+"and t.trcode="+trcode);
		  if(rs.next()){
			Rectangle rec = new  Rectangle(100,100);
			PdfPTable maintab = new PdfPTable(4);
			PdfPTable subtab = new PdfPTable(1);
			subtab.setWidthPercentage(new float[]{100},rec);
			subtab.setSpacingBefore(20);
			maintab.setSpacingBefore(10);
			maintab.setWidthPercentage(new float[]{25,25,25,25}, rec);
		
			PdfPCell c1;
			c1 = new PdfPCell(new Phrase("Name of Employee",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));  
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			maintab.addCell(c1);
			c1 = new PdfPCell(new Phrase(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			maintab.addCell(c1);
			c1 = new PdfPCell(new Phrase("Designation/Grade",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			maintab.addCell(c1);
			c1 = new PdfPCell(new Phrase(rs.getString(5),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			maintab.addCell(c1);
			c1 = new PdfPCell(new Phrase("Department ",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));  
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			maintab.addCell(c1);
			c1 = new PdfPCell(new Phrase(rs.getString(6),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			maintab.addCell(c1);
			c1 = new PdfPCell(new Phrase("Employee No.",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			maintab.addCell(c1);
			c1 = new PdfPCell(new Phrase(""+rs.getInt("empcode"),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			maintab.addCell(c1);
			
			c1 = new PdfPCell(new Phrase("Tour Report.",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			maintab.addCell(c1);
			c1 = new PdfPCell(new Phrase(rs.getString("TOURRPT"),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));	
			c1.setColspan(3);
			maintab.addCell(c1);
			
			
			c1 = new PdfPCell(new Phrase("Date of Departure/Journey",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			maintab.addCell(c1);
			c1 = new PdfPCell(new Phrase("Time",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			maintab.addCell(c1);
			c1 = new PdfPCell(new Phrase("Date of Arrival",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			maintab.addCell(c1);
			c1 = new PdfPCell(new Phrase("Time",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			maintab.addCell(c1);
	     	c1 = new PdfPCell(new Phrase(""+output.format(sdf.parse(rs.getString("STARTDATE"))),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
	     	c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	     	maintab.addCell(c1);
	     	c1 = new PdfPCell(new Phrase(rs.getString("STARTTIME"),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
	     	c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	     	maintab.addCell(c1);
	     	c1 = new PdfPCell(new Phrase(""+output.format(sdf.parse(rs.getString("ENDDATE"))),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
	     	c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	     	maintab.addCell(c1);
	     	c1 = new PdfPCell(new Phrase(rs.getString("ENDTIME"),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
	     	c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	     	maintab.addCell(c1);
	     	subtab.addCell(maintab);
	     	doc.add(subtab);
		  
		//main table end
	     
		//start table 1
	     
	     PdfPTable tab1 = new PdfPTable(7);
	     PdfPCell subcell;
	      tab1.setSpacingBefore(5);
			tab1.setWidthPercentage(new float[]{12,15,15,15,15,13,15},new Rectangle(100,100));
			
			subcell = new PdfPCell(new Phrase("A) Journey Details (Ticket must be enclosed)",F10Bold));
			subcell.setLeft(15);
			subcell.setRight(15);
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setColspan(7);
			tab1.addCell(subcell);
			
			subcell = new PdfPCell(new Phrase("Date",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("From",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("To",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("Mode of Travel",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("Class",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell = new PdfPCell(new Phrase("Fair",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("Amount",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);
			
			try{
			     Statement stmt=con.createStatement();
			     //trn = stmt.executeQuery("select DDATE, DFROM, DTO, DTMODE, DCLASS, DFAIRDP, DAMT from TRDETAILS where dtyp='A' AND EMPNO = "+feno1);
			     //trn= stmt.executeQuery("select t.DDATE,t.DFROM,t.DTO,l.LKP_DISC,t.DCLASS,t.DFAIRDP,t.DAMT from TRDETAILS as t join LOOKUP as l on t.DTMODE=l.LKP_SRNO where (t.DTYP = 'a' and l.LKP_CODE ='tm'and t.EMPNO="+feno1+")");
			    /* trn=stmt.executeQuery("select t.DDATE,t.DFROM,t.DTO,b.LKP_DISC as mode,a.LKP_DISC as class,t.DFAIRDP,t.DAMT from TRDETAILS as t, " +
			     		"LOOKUP a, LOOKUP b where t.DTYP = 'a' and  a.LKP_CODE='tc' and t.DCLASS = a.LKP_SRNO and b.LKP_CODE='tm'and t.DTMODE = b.LKP_SRNO and t.empno = "+feno1+")");
			     */
			     trn =stmt.executeQuery("select t.DDATE,t.DFROM,t.DTO,b.LKP_DISC as mode,a.LKP_DISC as class," +
			     						"t.DFAIRDP,t.DAMT from TRDETAILS as t, LOOKUP a, LOOKUP b where t.DTYP ='a'" +
			     						"and  a.LKP_CODE='tc' and t.DCLASS = a.LKP_SRNO and b.LKP_CODE='tm' " +
			     						"and t.DTMODE = b.LKP_SRNO and t.empno = "+feno1+"and t.trcode="+trcode);
			     float amt=0.0f;
			   
			    // trn1 = stmt.executeQuery("select LKP_disc from LOOKUP where LKP_CODE = 'tm' ANd LKP_SRNO="+trn.getInt(4));
				while(trn.next()){	
					
					subcell = new PdfPCell(new Phrase(""+output.format(sdf.parse(trn.getString(1))),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
					subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tab1.addCell(subcell);
					subcell = new PdfPCell(new Phrase(trn.getString(2),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
					subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tab1.addCell(subcell);
					subcell = new PdfPCell(new Phrase(trn.getString(3),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
					subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tab1.addCell(subcell);
					subcell = new PdfPCell(new Phrase(trn.getString(4),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
					subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tab1.addCell(subcell);
					subcell = new PdfPCell(new Phrase(trn.getString(5),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
					subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tab1.addCell(subcell);
					subcell = new PdfPCell(new Phrase(""+trn.getInt(6),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
					subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tab1.addCell(subcell);
	
					subcell = new PdfPCell(new Phrase(""+trn.getFloat(7),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
					subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tab1.addCell(subcell);
					amt = amt + trn.getFloat(7);

				}
			subcell = new PdfPCell(new Phrase("Total:",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
		    subcell.setLeft(15);
		    subcell.setRight(15);
		    subcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		    subcell.setColspan(6);
		    tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(""+amt,new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);
			doc.add(tab1);
	     }
	     catch(Exception e){
	    	 e.printStackTrace();
	     }
		//end table 1
			
			//start table 2
	     
			PdfPTable tab2 = new PdfPTable(6);
		     PdfPCell subcell2;
		     tab2.setSpacingBefore(5);
		     tab2.setWidthPercentage(new float[]{12,19,19,20,15,15},new Rectangle(100,100));
				
		     subcell2 = new PdfPCell(new Phrase("B) Local Conv. Exp. Details",F10Bold));
		     subcell2.setLeft(15);
		     subcell2.setRight(15);
		     subcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
		     subcell2.setColspan(6);
		     tab2.addCell(subcell2);
				
		     subcell2 = new PdfPCell(new Phrase("Date",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
		     subcell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		     tab2.addCell(subcell2);
			 subcell2 = new PdfPCell(new Phrase("From",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			 subcell2.setHorizontalAlignment(Element.ALIGN_CENTER);	
			 tab2.addCell(subcell2);
			 subcell2 = new PdfPCell(new Phrase("To",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			 subcell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			 tab2.addCell(subcell2);
			 subcell2 = new PdfPCell(new Phrase("Mode of Local Conv.",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			 subcell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			 tab2.addCell(subcell2);
			 subcell2 = new PdfPCell(new Phrase("Distance",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			 subcell2.setHorizontalAlignment(Element.ALIGN_CENTER);	
			 tab2.addCell(subcell2);
			 subcell2 = new PdfPCell(new Phrase("Amount",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			 subcell2.setHorizontalAlignment(Element.ALIGN_CENTER);	
			 tab2.addCell(subcell2);
			 
			 
			 try{
				 
			     Statement stmt=con.createStatement();
			     //trn = stmt.executeQuery("select DDATE, DFROM, DTO, DTMODE, DFAIRDP, DAMT from TRDETAILS where dtyp='B' AND EMPNO = "+feno1);
			     trn= stmt.executeQuery("select t.DDATE,t.DFROM,t.DTO,l.LKP_DISC,t.DFAIRDP,t.DAMT from TRDETAILS " +
			     		"as t join LOOKUP as l on t.DTMODE=l.LKP_SRNO where (t.DTYP = 'B' and l.LKP_CODE ='tm'and " +
			     		"t.EMPNO="+feno1+")and t.trcode="+trcode);
			     float amt=0.0f;
			     
				while(trn.next()){
					
						subcell2 = new PdfPCell(new Phrase(""+output.format(sdf.parse(trn.getString(1))),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
						subcell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						tab2.addCell(subcell2);
						subcell2 = new PdfPCell(new Phrase(trn.getString(2),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
						subcell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						tab2.addCell(subcell2);
						subcell2 = new PdfPCell(new Phrase(trn.getString(3),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
						subcell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						tab2.addCell(subcell2);
						subcell2 = new PdfPCell(new Phrase(trn.getString(4),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
						subcell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						tab2.addCell(subcell2);
						subcell2 = new PdfPCell(new Phrase(""+trn.getInt(5),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
						subcell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						tab2.addCell(subcell2);
						subcell2 = new PdfPCell(new Phrase(""+trn.getFloat(6),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
						amt = amt + trn.getFloat(6);
						subcell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						tab2.addCell(subcell2);
				}
			 
			 subcell2 = new PdfPCell(new Phrase("Total:",F10Bold));
			 subcell2.setLeft(15);
			 subcell2.setRight(15);
			 subcell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 subcell2.setColspan(5);
			 tab2.addCell(subcell2);
			 subcell2 = new PdfPCell(new Phrase(""+amt,new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			 subcell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			 tab2.addCell(subcell2);
			 doc.add(tab2);
			 }
			 catch(Exception e){
					e.printStackTrace();	
			 }
			 
			//end table 2
				
			//start table 3
				
			 PdfPTable tab3 = new PdfPTable(6);
			 PdfPCell subcell3;
			 tab3.setSpacingBefore(5);
			 tab3.setWidthPercentage(new float[]{12,19,19,20,15,15},new Rectangle(100,100));
			 subcell3 = new PdfPCell(new Phrase("C)Food Exp. Details",F10Bold));
			 subcell3.setLeft(15);
			 subcell3.setRight(15);
			 subcell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			 subcell3.setColspan(6);
			 tab3.addCell(subcell3);
			 subcell3 = new PdfPCell(new Phrase("Date",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			 subcell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			 tab3.addCell(subcell3);
			 subcell3 = new PdfPCell(new Phrase("From",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			 subcell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			 tab3.addCell(subcell3);
			 subcell3 = new PdfPCell(new Phrase("To",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			 subcell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			 tab3.addCell(subcell3);
			 subcell3 = new PdfPCell(new Phrase("No of Days",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			 subcell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			 tab3.addCell(subcell3);		
			 subcell3 = new PdfPCell(new Phrase("Per Day",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			 subcell3.setHorizontalAlignment(Element.ALIGN_CENTER);		
			 tab3.addCell(subcell3);
			 subcell3 = new PdfPCell(new Phrase("Amount",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			 subcell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			 tab3.addCell(subcell3);
			 
			 try{
				 
				 Statement stmt=con.createStatement();
			     trn = stmt.executeQuery("select DDATE, DFROM, DTO,NOOFDAY, DFAIRDP, DAMT from TRDETAILS where " +
			     		"dtyp='C' AND EMPNO = "+feno1+"and trcode="+trcode);
			     float amt=0.0f;
			     
				 while(trn.next()){
					 
					 subcell3 = new PdfPCell(new Phrase(""+output.format(sdf.parse(trn.getString(1))),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
					 subcell3.setHorizontalAlignment(Element.ALIGN_CENTER);
					 tab3.addCell(subcell3);
					 subcell3 = new PdfPCell(new Phrase(""+trn.getString(2),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
					 subcell3.setHorizontalAlignment(Element.ALIGN_CENTER);
					 tab3.addCell(subcell3);
					 subcell3 = new PdfPCell(new Phrase(""+trn.getString(3),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
					 subcell3.setHorizontalAlignment(Element.ALIGN_CENTER);
					 tab3.addCell(subcell3);
					 subcell3 = new PdfPCell(new Phrase(""+trn.getInt(4),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
					 subcell3.setHorizontalAlignment(Element.ALIGN_CENTER);
					 tab3.addCell(subcell3);
					 subcell3 = new PdfPCell(new Phrase(""+trn.getInt(5),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
					 subcell3.setHorizontalAlignment(Element.ALIGN_CENTER);
					 tab3.addCell(subcell3);
					 subcell3 = new PdfPCell(new Phrase(""+trn.getFloat(6),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
					 subcell3.setHorizontalAlignment(Element.ALIGN_CENTER);
					 tab3.addCell(subcell3);
					 amt = amt + trn.getFloat(6);
				}
				 
			 subcell3 = new PdfPCell(new Phrase("Total:",F10Bold));
			 subcell3.setLeft(15);
			 subcell3.setRight(15);
			 subcell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 subcell3.setColspan(5);
			 tab3.addCell(subcell3);
			 subcell3 = new PdfPCell(new Phrase(""+amt,new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			 subcell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			 tab3.addCell(subcell3);
			 doc.add(tab3);
			 }
			 catch(Exception e){
				 e.printStackTrace();
			 }
			 
			//end table 3
				
				//start table 4
					
			 PdfPTable tab4 = new PdfPTable(5);
			 PdfPCell subcell4;
			 tab4.setSpacingBefore(5);
			 tab4.setWidthPercentage(new float[]{10,25,25,20,20},new Rectangle(100,100));
			 subcell4 = new PdfPCell(new Phrase("D)Other Misc. Exp. Details(Bills enclosed) ",F10Bold));
			 subcell4.setLeft(15);
			 subcell4.setRight(15);
			 subcell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			 subcell4.setColspan(5);
			 tab4.addCell(subcell4);
			 subcell4 = new PdfPCell(new Phrase("Sr. No.",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			 subcell4.setHorizontalAlignment(Element.ALIGN_CENTER);			
			 tab4.addCell(subcell4);
			 subcell4 = new PdfPCell(new Phrase("Particulars",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			 subcell4.setHorizontalAlignment(Element.ALIGN_CENTER);			
			 tab4.addCell(subcell4);
			 subcell4 = new PdfPCell(new Phrase("Name of the Party",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			 subcell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			 tab4.addCell(subcell4);
			 subcell4 = new PdfPCell(new Phrase("Bill No & Date",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			 subcell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			 tab4.addCell(subcell4);
			 subcell4 = new PdfPCell(new Phrase("Amount",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			 subcell4.setHorizontalAlignment(Element.ALIGN_CENTER);			
			 tab4.addCell(subcell4);
			 	try{
				 Statement stmt=con.createStatement();
			     trn = stmt.executeQuery("select DPARTI, DPNAME, DBILLNO, DDATE, DAMT from TRDETAILS where " +
			     		"dtyp='D' AND EMPNO = "+feno1+"and trcode="+trcode);
			     float amt=0.0f;
			     int srn=1;
			     
				 while(trn.next()){
			 subcell4 = new PdfPCell(new Phrase(""+srn,new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			 subcell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			 tab4.addCell(subcell4);
			
			 srn+=srn;
			 subcell4 = new PdfPCell(new Phrase(trn.getString(1),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			 subcell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			 tab4.addCell(subcell4);
			 subcell4 = new PdfPCell(new Phrase(trn.getString(2),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			 subcell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			 tab4.addCell(subcell4);
			 subcell4 = new PdfPCell(new Phrase(trn.getString(3)+"/"+output.format(sdf.parse(trn.getString(4))),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			 subcell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			 tab4.addCell(subcell4);
			 subcell4 = new PdfPCell(new Phrase(""+trn.getFloat(5),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			 subcell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			 tab4.addCell(subcell4);
			 amt = amt + trn.getFloat(5);
				 }
			 subcell4 = new PdfPCell(new Phrase("Total:",F10Bold));
			 subcell4.setLeft(15);
			 subcell4.setRight(15);
			 subcell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 subcell4.setColspan(4);
			 tab4.addCell(subcell4);
			 subcell4 = new PdfPCell(new Phrase(""+amt,new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			 subcell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			 tab4.addCell(subcell4);
			 doc.add(tab4);
			 	}
					catch (Exception e) {
						e.printStackTrace();
					}	
			//end table 4
				
				//start table 5
			 
			PdfPTable tab5 = new PdfPTable(4);
			PdfPCell subcell5;
			tab5.setSpacingBefore(5);
			tab5.setWidthPercentage(new float[]{10,35,35,20},new Rectangle(100,100));
			subcell5 = new PdfPCell(new Phrase("E) Misc. Exp. Details (Withot Bill)",F10Bold));
			subcell5.setLeft(15);
			subcell5.setRight(15);
			subcell5.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell5.setColspan(4);
			tab5.addCell(subcell5);
			subcell5 = new PdfPCell(new Phrase("Sr. No.",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			subcell5.setHorizontalAlignment(Element.ALIGN_CENTER);	
			tab5.addCell(subcell5);
			subcell5 = new PdfPCell(new Phrase("Particulars",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			subcell5.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab5.addCell(subcell5);							
			subcell5 = new PdfPCell(new Phrase("Location",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			subcell5.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab5.addCell(subcell5);
			subcell5 = new PdfPCell(new Phrase("Amount",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			subcell5.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab5.addCell(subcell5);
			try{
				 Statement stmt=con.createStatement();
			     trn = stmt.executeQuery("select DPARTI, DLOCATION, DAMT from TRDETAILS where dtyp='E' " +
			     		"AND EMPNO = "+feno1+"and trcode="+trcode);
			     float amt=0.0f;
			     int srn=1;
			     
				 while(trn.next()){
			subcell5 = new PdfPCell(new Phrase(""+srn,new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			subcell5.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab5.addCell(subcell5);
			srn+=srn;
			subcell5 = new PdfPCell(new Phrase(trn.getString(1),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			subcell5.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab5.addCell(subcell5);
			subcell5 = new PdfPCell(new Phrase(trn.getString(2),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			subcell5.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab5.addCell(subcell5);
			subcell5 = new PdfPCell(new Phrase(""+trn.getFloat(3),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			subcell5.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab5.addCell(subcell5);
			amt=amt+trn.getFloat(3);
				 }
			subcell5 = new PdfPCell(new Phrase("Total:",F10Bold));
			subcell5.setLeft(15);
			subcell5.setRight(15);
			subcell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
			subcell5.setColspan(3);
			tab5.addCell(subcell5);
			subcell5 = new PdfPCell(new Phrase(""+amt,new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			subcell5.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab5.addCell(subcell5);
			doc.add(tab5);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			//end table 5
			
			//start table 6
							
							
			PdfPTable tab6 = new PdfPTable(5);
			PdfPCell subcell6;
			tab6.setSpacingBefore(5);
			tab6.setWidthPercentage(new float[]{12,33,25,15,15},new Rectangle(100,100));
			subcell6 = new PdfPCell(new Phrase("F) A/C of Imprest Details",F10Bold));
			subcell6.setLeft(15);
			subcell6.setRight(15);
			subcell6.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell6.setColspan(5);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase("Date",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase("Particulars",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase("Receipt",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase("Payment(Rs)",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase("Amount",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			try{
				 Statement stmt=con.createStatement();
			     trn = stmt.executeQuery("select ACDATE,ACPARTI, ACREPTNO,ACPAYMNT, ACAMT from TRACCOUNT " +
			     		"where EMPNO = "+feno1+"and trcode="+trcode);
			     float amt=0.0f;
			     while(trn.next()){
			    	 
			subcell6 = new PdfPCell(new Phrase(""+output.format(sdf.parse(trn.getString(1))),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase(trn.getString(2),new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase(trn.getString(3),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase(trn.getString(4),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase(""+trn.getFloat(5),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			amt=amt+trn.getFloat(5);
			/*subcell6 = new PdfPCell(new Phrase("",new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase("Add:- Imprest/Advance taken for tour",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase("",new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase("",new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase("",new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase("",new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase("Add:- Advance taken from site",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase("",new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase("",new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase("",new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase("",new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase("Less:- Tour Bill Submited",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase("",new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase("",new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase("",new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase("",new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase("Cash Returned/Refund",new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase("",new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase("",new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase("",new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			*/}
				 
			subcell6 = new PdfPCell(new Phrase("Total:",F10Bold));
			subcell6.setLeft(15);
			subcell6.setRight(15);
			subcell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
			subcell6.setColspan(4);
			tab6.addCell(subcell6);
			subcell6 = new PdfPCell(new Phrase(""+amt,new Font(FONT.TIMES_ROMAN,10,Font.BOLD)));
			subcell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab6.addCell(subcell6);
			doc.add(tab6);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			//end table 6
			
			//start table 7
							
			PdfPTable tab7 = new PdfPTable(1);
			PdfPCell subcell7;
			tab7.setSpacingBefore(5);
			tab7.setWidthPercentage(new float[]{100},new Rectangle(100,100));
			subcell7 = new PdfPCell(new Phrase("Tour Report",F10Bold));
			subcell7.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab7.addCell(subcell7);
			try{
				 Statement stmt=con.createStatement();
			     trn = stmt.executeQuery("select TOURRPT from TRACCOUNT where ACPARTI = 'Opening Balance' " +
			     			"and EMPNO = "+feno1+"and trcode="+trcode);
			     while (trn.next()){
			subcell7 = new PdfPCell(new Phrase(trn.getString(1),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
			tab7.addCell(subcell7);
			}
			     tab7.setSpacingAfter(80);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			doc.add(tab7);
			//end table 7
							
			para = new Paragraph((new Phrase("Signature Of Employee                    Signature Of Tour Co-Ordinator                    Signature Of M.D. ",new Font(Font.TIMES_ROMAN,10))));
			para.setAlignment(Element.ALIGN_CENTER);
			doc.add(para);
							
							
							
		con.close();
		doc.close();
		
		  }
		  else {
			  try{
					 Statement stmt=con.createStatement();
				     trn = stmt.executeQuery("select FNAME,LNAME from EMPMAST where EMPNO = "+feno1);
				     if (trn.next()){
				    	/* PdfPTable tab7= new PdfPTable(1);
				    	 PdfPCell subcell7;
				    	 tab7.setSpacingBefore(30);
				subcell7 = new PdfPCell(new Phrase(trn.getString(1)+" "+trn.getString(2),new Font(FONT.TIMES_ROMAN,10,Font.NORMAL)));
				tab7.addCell(subcell7);
				doc.add(tab7);*/
				    para.setSpacingBefore(40);
				    Rectangle rec = new  Rectangle(100,100);
					PdfPTable maintab = new PdfPTable(1);
					PdfPTable subtab = new PdfPTable(1);
					subtab.setWidthPercentage(new float[]{100},rec);
					subtab.setSpacingBefore(20);
					maintab.setSpacingBefore(10);
					maintab.setWidthPercentage(new float[]{100}, rec);
				
					PdfPCell c1;
					c1 = new PdfPCell((new Phrase("No Record Found For Employee "+trn.getString(1)+" "+trn.getString(2),new Font(Font.TIMES_ROMAN,15))));
					c1.setHorizontalAlignment(Element.ALIGN_CENTER);
					maintab.addCell(c1);
			     	subtab.addCell(maintab);
			     	doc.add(subtab);
				}
			  }
				catch(Exception e){
					e.printStackTrace();
				}
			 
				
			  con.close();
			  doc.close();
	
		  }
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	
}

}
