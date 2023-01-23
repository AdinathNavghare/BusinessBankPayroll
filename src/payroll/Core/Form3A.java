package payroll.Core;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import payroll.DAO.ConnectionManager;
import payroll.DAO.LookupHandler;
import payroll.Model.RepoartBean;

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
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class Form3A extends PdfPageEventHelper
{
	public static void getForm3A(String date1,String date2,int empno,String filepath) 
		{
			Document doc = new Document();
			try
			{
				SimpleDateFormat fromf = new SimpleDateFormat("dd-MMM-yyyy");
				SimpleDateFormat tof = new SimpleDateFormat("yyyy-MM-dd");
				Date dt = fromf.parse(date1);
				String dt1 = tof.format(dt);
				dt = fromf.parse(date2);
				String dt2 = tof.format(dt);
				PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(filepath));
				writer.setPageEvent(new Form3A());
				writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
				doc.open();
				doc.setPageSize(PageSize.A4);
							
				PdfPTable tab1 = new PdfPTable(1);
				tab1.setWidthPercentage(new float[]{100},new Rectangle(100,100));
				PdfPCell cell = new PdfPCell();
				cell.setBorderWidth(0.7F);
				Font f1 = new Font(Font.HELVETICA,8);
				Font f2 = new Font(Font.HELVETICA,7.5F);
				cell.addElement(createPara("FORM - 3 A",new Font(Font.HELVETICA,10,Font.BOLD), Element.ALIGN_CENTER));
				cell.addElement(createPara("( Unexempted Establishment Only )", f1, Element.ALIGN_CENTER));
				cell.addElement(createPara("THE EMPLOYEE'S PROVIDENT FUND SCHEME, 1952", f1, Element.ALIGN_CENTER));
				cell.addElement(createPara("( Paras 35 and 42 ) and", f1, Element.ALIGN_CENTER));
				cell.addElement(createPara("THE EMPLOYEES PENSION SCHEME, 1995", f1, Element.ALIGN_CENTER));
				cell.addElement(createPara("Para 20", f1, Element.ALIGN_CENTER));
				cell.addElement(Chunk.NEWLINE);
				cell.addElement(createPara("Contribution Card for the currency period from "+date1.substring(3)+" to "+date2.substring(3), new Font(Font.HELVETICA,7.5F,Font.BOLD), Element.ALIGN_LEFT));
				tab1.addCell(cell);
				
				cell = new PdfPCell();cell.setBorderWidth(0.7F);
				cell.addElement(createMainInfoTab(empno));
				tab1.addCell(cell);
				doc.add(tab1);
				
				doc.add(createDataTable(empno, date1, date2));
				
				PdfPTable tab2 = new PdfPTable(1);
				tab2.setWidthPercentage(new float[]{100},new Rectangle(100,100));
				cell = new PdfPCell();cell.setBorderWidth(0.7F);
				cell.addElement(createPara("Certified That the difference between the total of the contribution shown under the columns (3) & (4) of the above table and that arrived at on the total", f2, Element.ALIGN_LEFT));
				cell.addElement(createPara("Wages shown in Column (2) at the Prescribed rate is soley due to the rounding of the contributions to the nearest rupee under the rule.", f2, Element.ALIGN_LEFT));
				cell.addElement(createPara("* Contribution for the month of March paid in April", f1, Element.ALIGN_CENTER));
				cell.addElement(Chunk.NEWLINE);
				cell.addElement(createPara("IN THE CASE OF AN EMPLOYEE WHO HAS LEFT THE SERVICE OF THE COMPANY EARLIER, HIS CONTRIBUTION HAS BEEN", f2, Element.ALIGN_LEFT));
				cell.addElement(createPara("SHOWN IN THE SUBSEQUENT MONTH WHICH HIS FINAL SETTLEMENT OF SALARY HAS BEEN MADE.", f2, Element.ALIGN_LEFT));
				
				PdfPTable tab3 = new PdfPTable(2);
				tab3.setWidthPercentage(new float[]{70,30},new Rectangle(100,100));
				PdfPCell c1 = new PdfPCell();
				c1.setBorderWidth(0);
				c1.addElement(createPara("Date : ", f2,Element.ALIGN_LEFT));
				tab3.addCell(c1);
				c1 = new PdfPCell();
				c1.setBorderWidth(0);
				c1.addElement(createPara("Signature of Employer", f2, Element.ALIGN_CENTER));
				c1.addElement(createPara("(Office Seal)", f2, Element.ALIGN_CENTER));
				tab3.addCell(c1);
				tab3.setSpacingAfter(5);
				tab3.setSpacingBefore(8);
				cell.addElement(tab3);
				tab2.addCell(cell);
				cell = new PdfPCell();cell.setBorderWidth(0.7F);
				cell.addElement(createPara("Note : 1) In respect of the Form (3A) sent to the Regional office during the course of the currency period for the purpose of final", f2, Element.ALIGN_LEFT));
				cell.addElement(createPara("Settelment of the accounts of the members who had left service details of date and reasons for leaving service and", f2, Element.ALIGN_LEFT));
				cell.addElement(createPara("certificate as shown in the remark column should be added.", f2, Element.ALIGN_LEFT));
				cell.addElement(createPara("2) If there is no period of NCS, the word \"NIL\" to be mentioned against the total column.", f2, Element.ALIGN_LEFT));
				cell.addElement(createPara("3) Wherever no wages are shown against any month the period should be shown as NCS under Col 6.", f2,Element.ALIGN_LEFT));
				tab2.addCell(cell);
				
				doc.add(tab2);
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
					
					PdfPTable tab3 = new PdfPTable(2);
					tab3.setWidthPercentage(new float[]{15,50},new Rectangle(65,100));
					PdfPCell c1 = new PdfPCell();
					c1.setBorderWidth(0);
					c1.addElement(createPara("2. Name/Surname: ", f1,Element.ALIGN_LEFT));
					c1.setHorizontalAlignment(Element.ALIGN_LEFT);
					tab3.addCell(c1);
					c1 = new PdfPCell();
					c1.setBorderWidth(0);
					c1.addElement(createPara(name, new Font(Font.HELVETICA,8,Font.BOLD), Element.ALIGN_LEFT));
					tab3.addCell(c1);
					cell.addElement(tab3);
					
					cell.addElement(createPara("3. Father / Husband's Name: "+(rs.getString("FHNAME")==null?"":rs.getString("FHNAME")), f1, Element.ALIGN_LEFT));
					cell.addElement(createPara("4. Name & Address of the Establishment : ", f1, Element.ALIGN_LEFT));
					cell.addElement(createPara("THE NASIK MARCHANT'S CO-OP BANK LTD.", new Font(Font.HELVETICA,9,Font.BOLD),Element.ALIGN_LEFT));
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
				con.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return tab;
		}
		
		public static PdfPTable createDataTable(int empno,String from, String to)
		{
			PdfPTable tab = new PdfPTable(9);
			Font f1 = new Font(Font.HELVETICA,8,Font.BOLD);
			Font f2 = new Font(Font.HELVETICA,8);
			NumberFormat format = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));
			String Encash_date="";
			int leave_encash=0;
			try 
			{
				tab.setWidthPercentage(new float[]{15,10,10,10,10,10.6F,10,10,14.4F}, new Rectangle(100,100));
				tab.addCell(createCell("Month", f1, Element.ALIGN_CENTER));
				tab.addCell(createCell("Amount of Wages Rs.", f1, Element.ALIGN_CENTER));
				tab.addCell(createCell("Worker's Share EPF", f1, Element.ALIGN_CENTER));
				tab.addCell(createMiddleCell());
				tab.addCell(createCell("Refund of Advances", f1, Element.ALIGN_CENTER));
				tab.addCell(createCell("Non cont. services From..... To....", f1, Element.ALIGN_CENTER));
				tab.addCell(createCell("Remarks", f1, Element.ALIGN_CENTER));
				
				tab.addCell(createCell("1", f1, Element.ALIGN_CENTER));
				tab.addCell(createCell("2", f1, Element.ALIGN_CENTER));
				tab.addCell(createCell("3", f1, Element.ALIGN_CENTER));
				tab.addCell("");
				tab.addCell(createCell("4", f1, Element.ALIGN_CENTER));
				tab.addCell("");
				tab.addCell(createCell("5", f1, Element.ALIGN_CENTER));
				tab.addCell(createCell("6", f1, Element.ALIGN_CENTER));
				tab.addCell(createCell("7", f1, Element.ALIGN_CENTER));
				
				Connection con = ConnectionManager.getConnection();
				Statement st = con.createStatement();
				Statement st1 = con.createStatement();
				Statement st2 = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT DATENAME(MONTH, convert(date,DATEADD(month,1,trndt))) + ' ' + DATENAME(YEAR, convert(date,DATEADD(month,1,trndt))) as mon,CAL_AMT FROM YTDTRAN " +
											   " WHERE EMPNO = "+empno+" AND TRNCD=136 AND TRNDT BETWEEN convert(date,DATEADD(month,-1,'"+ReportDAO.BOM(from)+"')) AND '"+ReportDAO.BOM(to)+"' ORDER BY TRNDT ");
				
				ResultSet rs1 = st1.executeQuery("SELECT DATENAME(MONTH, max(encashdate)) + ' ' + DATENAME(YEAR, max(encashdate)) as encashdate from encashment");
				if(rs1.next()){
				Encash_date = rs1.getString("encashdate");
				ResultSet rs3 = st1.executeQuery("select y1.INP_AMT from YTDTRAN y1 where y1.TRNCD=145 and y1.EMPNO = "+empno+" and y1.TRNDT=(select convert(date,DATEADD(s,-1,DATEADD(mm, DATEDIFF(m,0,max(encashdate))+1,0)))as dt from encashment) ");
				if(rs3.next())
				leave_encash = rs3.getInt("INP_AMT");
				}
				
				int totalAmt=0,total3=0,total4a=0,total4b=0;
				int val3=0,val4a=0,val4b=0;
				boolean flag=false;
				int cnt=0;
				while(rs.next())
				{
					int amt =0;
					if(Encash_date.equalsIgnoreCase(rs.getString("MON")))
					{
						flag=true;
						
					}
					
					if(flag&&cnt>0)
					{
						amt = rs.getInt("CAL_AMT")+leave_encash;
						flag=false;
						cnt=0;
					}else{
					amt = rs.getInt("CAL_AMT");
					}
					if(amt>=15000)
					{
						int amt1=15000;
					val3 = (int)Math.round(amt * 12.00 / 100);
					val4a = (int) Math.round(amt * 3.67 / 100);
					val4b = (int) Math.round(amt1 * 8.33 / 100);
					val4a+=(int) Math.round((amt*8.33/100)-val4b);
					}
					else
					{
						val3 = (int)Math.round(amt * 12.00 / 100);
						val4a = (int) Math.round(amt * 3.67 / 100);
						val4b = (int) Math.round(amt * 8.33 / 100);
					}
							
					tab.addCell(createCell(rs.getString("MON"), f2, Element.ALIGN_LEFT));
					tab.addCell(createCell(format.format(amt).substring(4), f2, Element.ALIGN_RIGHT));
					tab.addCell(createCell(format.format(val3).substring(4), f2, Element.ALIGN_RIGHT));
					tab.addCell(createCell(format.format(val4a).substring(4), f2, Element.ALIGN_RIGHT));
					tab.addCell(createCell(format.format(val4b).substring(4), f2, Element.ALIGN_RIGHT));
					tab.addCell(createCell(format.format(val3).substring(4), f2, Element.ALIGN_RIGHT));
					tab.addCell(createCell("", f2, Element.ALIGN_RIGHT));
					tab.addCell(createCell("", f2, Element.ALIGN_RIGHT));
					tab.addCell(createCell("", f2, Element.ALIGN_LEFT));
					
					totalAmt+= amt;
					total3+= val3;
					total4a+= val4a;
					total4b+= val4b;
					if(flag)
						cnt++;
				}
				tab.addCell(createCell("Total", f1, Element.ALIGN_CENTER));
				tab.addCell(createCell(format.format(totalAmt).substring(4), f1, Element.ALIGN_RIGHT));
				tab.addCell(createCell(format.format(total3).substring(4), f1, Element.ALIGN_RIGHT));
				tab.addCell(createCell(format.format(total4a).substring(4), f1, Element.ALIGN_RIGHT));
				tab.addCell(createCell(format.format(total4b).substring(4), f1, Element.ALIGN_RIGHT));
				tab.addCell(createCell(format.format(total3).substring(4), f1, Element.ALIGN_RIGHT));
				tab.addCell(createCell("", f1, Element.ALIGN_RIGHT));
				tab.addCell(createCell("", f1, Element.ALIGN_RIGHT));
				tab.addCell(createCell("", f1, Element.ALIGN_LEFT));
				con.close();
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
			cell.setBorderWidth(0.5F);
			
			return cell;
		}
		public static PdfPCell createMiddleCell()
		{
			PdfPCell cell = new PdfPCell();
			cell.setColspan(3);
			Font f = new Font(Font.HELVETICA,8);
			cell.addElement(createPara("Employer Share", new Font(Font.HELVETICA,8,Font.BOLD), Element.ALIGN_CENTER));
			try
			{
				PdfPTable tab = new PdfPTable(3);
				tab.setSpacingBefore(5);
				tab.setSpacingAfter(0);
				tab.setWidthPercentage(new float[]{10,10,10.8F}, new Rectangle(30,30));
				tab.addCell(createCell("EPF", f, Element.ALIGN_CENTER));
				tab.addCell(createCell("EPS", f, Element.ALIGN_CENTER));
				tab.addCell(createCell("TOTAL", f, Element.ALIGN_CENTER));
				tab.addCell(createCell("a", f, Element.ALIGN_CENTER));
				tab.addCell(createCell("b", f, Element.ALIGN_CENTER));
				tab.addCell(createCell("c", f, Element.ALIGN_CENTER));
				cell.addElement(tab);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return cell;
		}
		
		@Override
		public void onEndPage(PdfWriter writer, Document document)
		{
			Font f = new Font(Font.HELVETICA,8,Font.BOLD, new GrayColor(0.60f));
			Font FONT = new Font(Font.HELVETICA, 52, Font.BOLD, new GrayColor(0.90f));
			Rectangle rect = writer.getBoxSize("art");
			try
			{
				ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Ch.Shahu Hospital",FONT), 297.5f, 421, 45);
				ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("| Page : "+writer.getPageNumber(),f), rect.getRight()-30, rect.getBottom()-30, 0);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		public static void getForm3ATXT(String date1,String date2,int empno,String filepath) 
		{
			

			RepoartBean repBean  = new RepoartBean();
			String EmpSql = "";
			NumberFormat format = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));
			Connection Cn = null;
			ResultSet emp = null;
			ResultSet EmpRecord = null;
			String Encash_date="";
			int leave_encash=0;
			boolean flag=false;
			int cnt=0;
			FileWriter Fp;
			int[] arr = {10,11,11,9,10,9,14,12};
			
			 try
			    {
			ReportDAO.inithead(repBean);
			//Initvar                       *****already variable are initialised  not need of initvar
		    ReportDAO.make_prn_file(filepath,repBean);
		    ReportDAO.println("", 55, 5, false, "BANK",repBean);
		    ReportDAO.println("Form No. 3-A", 65, 1, false, "BANK",repBean);
		    ReportDAO.println("(For unexemted establishment)", 60, 2, false, "BANK",repBean);
		    ReportDAO.println("E.P.F. SCHEME, 1952 ( Paras |5 & 42 ) F.P.F. SCHEME, 1995 ( Para 19 )", 30, 1, false, "BANK",repBean);
		    ReportDAO.println("Contribution for the period : "+ReportDAO.BOM(date1)+" - "+ReportDAO.EOM(date2) , 30, 2, false, "BANK",repBean);
		    
		    EmpSql="SELECT * FROM EMPMAST WHERE EMPNO="+empno+" ";
		    ReportDAO.OpenCon("", "", "",repBean);
		    Cn = repBean.getCn();
		    Statement st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		    Statement st1 = Cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		    Statement st2 = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		    Statement st3 = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		    emp=st.executeQuery(EmpSql);
		    emp.next();
		    LookupHandler lkph = new LookupHandler();
		    ReportDAO.println("1. Account No.:"+(emp.getString("PFNO")==null?"":emp.getString("PFNO"))+"3\t"+"2. Name/Surname: "+lkph.getLKP_Desc("SALUTE", emp.getInt("SALUTE"))+" "+emp.getString("FNAME")+" "+emp.getString("MNAME")+" "+emp.getString("LNAME"), 30, 1, false, "BANK",repBean);
		   
		    //ReportDAO.println("2. Name/Surname: "+lkph.getLKP_Desc("SALUTE", emp.getInt("SALUTE"))+" "+emp.getString("FNAME")+" "+emp.getString("MNAME")+" "+emp.getString("LNAME"), 25, 1, false, "BANK",repBean);
		    ReportDAO.println("3. Father's/Husband's Name:"+(emp.getString("MNAME")==null?"":emp.getString("MNAME"))+" \t"+"4. Statutory rate of contribution: 12 %", 30, 1, false, "BANK",repBean);
		    //ReportDAO.println("4. Statutory rate of contribution: 12 %", 20, 1, false, "BANK",repBean);
		    ReportDAO.println("5. Name & Address of the Est.: THE NASIK MERCHANTS' CO-OP. BANK LTD., NASIK", 30, 1, false, "BANK",repBean);
		    ReportDAO.println("|---------------------------------------------------------------------------------------------|", 30, 1, false, "BANK",repBean);
		    ReportDAO.println("| MONTH    |      WORKER'S SHARE   |  EMPLOYER'S SHARE  | REFUND  | BREAK IN     |   REMARKS  |", 30, 1, false, "BANK",repBean);
		    ReportDAO.println("|          |--------------------------------------------|    OF 	| MEMBERSHIP   |            |", 30, 1, false, "BANK",repBean);
		    ReportDAO.println("|          |AMOUNT OF  |   E.P.F.  |  E.P.F. |  F.P.F.  | ADVANCE | RECKONABLE   |            |", 30, 1, false, "BANK",repBean);
		    ReportDAO.println("|          |   WAGES   |   12 %    |  3.67 % |  8.33 %  |         | SERVICE (6)  |            |", 30, 1, false, "BANK",repBean);
		    ReportDAO.println("|   (1)    |   (2)     |    (3)    |  (4A)   |   (4B)   |    (5)  | FROM |   TO  |    (7)     |", 30, 1, false, "BANK",repBean);
		    ReportDAO.println("|---------------------------------------------------------------------------------------------|", 30, 1, false, "BANK",repBean);
		    ReportDAO.println("|---------------------------------------------------------------------------------------------|", 30, 1, false, "BANK",repBean);

		    EmpRecord = st1.executeQuery("SELECT UPPER(convert(char(3),DATENAME(MONTH, convert(date,DATEADD(month,1,trndt))),0) + ' ' + DATENAME(YEAR, convert(date,DATEADD(month,1,trndt)))) as mon,CAL_AMT FROM YTDTRAN " +
		    											" WHERE EMPNO = "+empno+" AND TRNCD=136 AND TRNDT BETWEEN convert(date,DATEADD(month,-1,'"+ReportDAO.BOM(date1)+"')) AND '"+ReportDAO.BOM(date2)+"' ORDER BY TRNDT ");
			java.sql.ResultSetMetaData xtra=EmpRecord.getMetaData();
			int columns = xtra.getColumnCount();
		    int totalAmt=0,total3=0,total4a=0,total4b=0;
			int val3=0,val4a=0,val4b=0;
			ResultSet rs1 = st2.executeQuery("SELECT UPPER(convert(char(3),DATENAME(MONTH, max(encashdate)),0) + ' ' + DATENAME(YEAR, max(encashdate)))  as encashdate from encashment");
			if(rs1.next()){
			Encash_date = rs1.getString("encashdate");
			ResultSet rs3 = st3.executeQuery("select y1.INP_AMT from YTDTRAN y1 where y1.TRNCD=145 and y1.EMPNO = "+empno+" and y1.TRNDT=(select convert(date,DATEADD(s,-1,DATEADD(mm, DATEDIFF(m,0,max(encashdate))+1,0)))as dt from encashment) ");
			if(rs3.next())
			leave_encash = rs3.getInt("INP_AMT");
			}
			while(EmpRecord.next())
			{
			int i=1;
			int k=0;
			String printstring="";
			int amt =0;
			if(Encash_date.equalsIgnoreCase(EmpRecord.getString("MON")))
			{
				flag=true;
				
			}
			
			if(flag&&cnt>0)
			{
				amt = EmpRecord.getInt("CAL_AMT")+leave_encash;
				//EmpRecord.updateString("CAL_AMT", String.valueOf((EmpRecord.getInt("CAL_AMT")+leave_encash)));
				//EmpRecord.updateRow();
				//flag=false;
				//cnt=0;
			}else{
			amt = EmpRecord.getInt("CAL_AMT");
			}
			
			if(amt>=15000)
			{
				int amt1=15000;
			val3 = (int)Math.round(amt * 12.00 / 100);
			val4a = (int) Math.round(amt * 3.67 / 100);
			val4b = (int) Math.round(amt1 * 8.33 / 100);
			val4a+=(int) Math.round((amt*8.33/100)-val4b);
			}
			else
			{
				val3 = (int)Math.round(amt * 12.00 / 100);
				val4a = (int) Math.round(amt * 3.67 / 100);
				val4b = (int) Math.round(amt * 8.33 / 100);
			}
			totalAmt+= amt;
			total3+= val3;
			total4a+= val4a;
			total4b+= val4b;
			//UtilityDAO.trans(val3, "99,99,999.99", "", false, false);
			List<Integer> str1=new ArrayList<Integer>(8);
			str1.add(val3);str1.add(val4a);str1.add(val4b);
			str1.add(0);str1.add(0);str1.add(0);str1.add(0);str1.add(0);
			

			
			
			
			//List<String> str=new ArrayList<String>(arr.length);
			while(i<=arr.length)
			{
				String abc="";
				String str="";
				if(i<=columns)
				{
					if(flag&&cnt>0&&i>1)
					{
						str=String.valueOf((format.format(EmpRecord.getInt("CAL_AMT")+leave_encash).substring(4).replace(",","")));
						flag=false;
						cnt=0;
					}else{
						str=EmpRecord.getString(i);
					}
					
					for (int j=str.length();j<arr[i==0?0:i-1];j++)
					{
						abc=abc.concat(" ");
					}
					printstring=printstring.concat("|"+str+abc+(i==1?"":"|"));
					
				}
				else
				{
					for (int j=String.valueOf(str1.get(k)).length();j<arr[i==0?0:i-1];j++)
					{
						abc=abc.concat(" ");
					}
					if(str1.get(k)==0)
					{
						abc=abc.concat(" ");
					}
					printstring=printstring.concat(String.valueOf(str1.get(k)==0?"":str1.get(k))+abc+"|");

					k++;
				}
					//str.add(abc);
					
				i++;
				
			}
			
			//ReportDAO.println("|"+EmpRecord.getString("MON")+"  |"+EmpRecord.getString("CAL_AMT")+"   |"+val3+"       |"+(String.valueOf(val4a).length()>3?val4a+"     |":val4a+"      |")+val4b+"      |         |              |            |", 30, 1, false, "BANK",repBean);
			ReportDAO.println(printstring, 30, 1, false, "BANK",repBean);
			ReportDAO.println("|          |           |           |         |          |         |              |            |", 30, 1, false, "BANK",repBean);
			if(flag)
				cnt++;
			}
			//here taken for adjusting space for lst row.. logic remain to do for it
			List<String> str2=new ArrayList<String>(7);
			str2.add(format.format(totalAmt).substring(4));str2.add(format.format(total3).substring(4));str2.add(format.format(total4a).substring(4));
			str2.add(format.format(total4b).substring(4));str2.add(format.format(0).substring(4));str2.add(format.format(0).substring(4));str2.add(format.format(0).substring(4));
			int x=0;
			String tottal="| TOTAL:   |";
			while(x<str2.size())
			{
				String abc="";
				
					for (int j=str2.get(x).length();j<arr[x+1];j++)
					{
						abc=abc.concat(" ");
					}
					if(str2.get(x).equalsIgnoreCase("0"))
					{
						abc=abc.concat(" ");
					}
					tottal=tottal.concat(str2.get(x).equalsIgnoreCase("0")?"":str2.get(x)+abc+"|");
					x++;
			}
			
			ReportDAO.println("|---------------------------------------------------------------------------------------------|", 30, 1, false, "BANK",repBean);
		    //ReportDAO.println("| TOTAL    |"+format.format(totalAmt).substring(4)+"|"+format.format(total3).substring(4)+"  |"+format.format(total4a).substring(4)+" |"+format.format(total4b).substring(4)+" |         |              |            |", 30, 1, false, "BANK",repBean);
		    ReportDAO.println(tottal, 30, 1, false, "BANK",repBean);
		    ReportDAO.println("|---------------------------------------------------------------------------------------------|", 30, 2, false, "BANK",repBean);

		    ReportDAO.println("Certified that the total amount of contribution (both shares) indicated in this card i.e."+
		    		 					" Rs. ", 30, 1, false, "BANK",repBean);
		    ReportDAO.println(""+format.format(total3+total4a+total4b).substring(4)+" has already been remitted in full EPF A/c No.1 & pension fund A/c no. 10 ", 30, 1, false, "BANK",repBean);
		    ReportDAO.println("(EPS contribution A/c) vide note below.", 30, 2, false, "BANK",repBean);
		     
		    ReportDAO.println("Certified that the difference between the contribution shown under Cols. (3) & 4A & 4B of "+ 
		    							"the ", 30, 1, false, "BANK",repBean);
		    ReportDAO.println("above table and that arrived at on the total wages shown in col.(2) at the prescribed "+
		    							"rate is ", 30, 1, false, "BANK",repBean);
		    ReportDAO.println("solely due to the rounding of contribution to the nearest rupee under the rules.", 30, 2, false, "BANK",repBean);
		    
		   ReportDAO.println("Dated:   /  /    ", 30, 4, false, "BANK",repBean);
		   ReportDAO.println("C.E.O. / GENERAL MANAGER / MANAGER", 75, 1, false, "BANK",repBean);
		   ReportDAO.println("THE BUSINESS CO.OP BANK LTD, Nashik.", 70, 2, false, "BANK",repBean);
		   ReportDAO.println("NOTE: In respect of the Form (3A) sent to the Regional Office during the  course  of the ", 30, 1, false, "BANK",repBean);
		   ReportDAO.println("currency period for the purpose of final settelment of the  A/c's of the members who had "+
				   					"left", 30, 1, false, "BANK",repBean);
		   ReportDAO.println("service, details of date & reasons for leaving service  & also certificate shown in "+
				   					"the REMARKS", 30, 1, false, "BANK",repBean);
		   ReportDAO.println("Col. should \n be added.", 30, 1, false, "BANK",repBean);
		  
		    repBean.setPageNo(1);
		    repBean.setLineLen(80);
		    repBean.setPageLen(65);
		    repBean.setLnCount(0);
		    repBean.setLineSpace(0);
		   
				
				Fp = repBean.getFp();
				Fp.close();
				Cn.close();
			} catch (Exception e)
			{
				e.printStackTrace();
				// TODO: handle exception
			}
		    
		
			
		
			
		}

	
}


