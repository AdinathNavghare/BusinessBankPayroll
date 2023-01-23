package payroll.DAO;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;

import org.jfree.ui.Align;

import payroll.Core.Calculate;
import payroll.Core.DottedLineCell;
import payroll.Core.HeaderAndFooter;
import payroll.Core.ReportDAO;
import payroll.Core.UtilityDAO;
import payroll.Core.UtilityDAO.Footer;
import payroll.Core.UtilityDAO.Footer1;
import payroll.DAO.BankStmntHandler;
import payroll.DAO.BranchDAO;
import payroll.DAO.CodeMasterHandler;
import payroll.DAO.ConnectionManager;
import payroll.DAO.EmpOffHandler;
import payroll.DAO.GradeHandler;
import payroll.DAO.LookupHandler;
import payroll.DAO.TranHandler;
import payroll.Model.BankStmntBean;
import payroll.Model.BranchBean;
import payroll.Model.PFExcelBean;
import payroll.Model.RepoartBean;
import payroll.Model.TranBean;
import payroll.Model.TransactionBean;

import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.util.Calendar;
import com.lowagie.text.Chunk;
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

import junit.awtui.Logo;
public class NonContribute extends PdfPageEventHelper
{

	public static void nonContribute(String date, String filePath, String imgpath, String empno) {
		Document doc = new Document();
		try
		{	
			Connection con = ConnectionManager.getConnection();
			SimpleDateFormat fromf = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat tof = new SimpleDateFormat("yyyy-MM-dd");
			String date1="01-"+date;
			String date2="31-"+date;
			
			System.out.println("date G "+date);
			Date dt = fromf.parse(date);
			String dt1 = tof.format(dt);
			System.out.println("dt1"+dt1);
			/*dt = fromf.parse(date2);
			String dt2 = tof.format(dt);*/
			Date sysdate = new Date();
			String sys = fromf.format(sysdate);
			
			
			
			/*String sqll="select LKP_DISC from LOOKUP where LKP_CODE='DESIG' and LKP_SRNO=(select desig from EMPTRAN where EMPNO= "+empno+" and"+
					" SRNO=(select MAX(srno) from EMPTRAN where EMPNO="+empno+"))";*/
			/*
			String sqll="select * from CredentialNew";
		Statement stt=con.createStatement();
		ResultSet rss=stt.executeQuery(sqll);
		String desig="";
		if(rss.next())
		{
			desig=rss.getString("LKP_DISC");
		}
			*/
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(filePath));
			doc.open();
			doc.setPageSize(PageSize.A4);
						
			PdfPTable tab1 = new PdfPTable(1);
			tab1.setWidthPercentage(new float[]{100},new Rectangle(100,100));
			PdfPCell cell = new PdfPCell();
			cell.setBorderWidth(0.7F);
			Font f_under = new Font(Font.HELVETICA,14,Font.BOLD|Font.UNDERLINE);
			Font fG_under = new Font(Font.HELVETICA,12,Font.ITALIC);
			Font f1 = new Font(Font.HELVETICA,8);
			Font f2 = new Font(Font.HELVETICA,7.5F);
			
			Font f = new Font(Font.TIMES_ROMAN,8,Font.BOLD);

			
			
		
			cell.addElement(createPara("Date :- " +sys,f1, Element.ALIGN_RIGHT));
			cell.addElement(Chunk.NEWLINE);
			
			Image image1 = Image.getInstance(imgpath);
			image1.setAlignment(Element.ALIGN_CENTER);


			//Phrase title = new Phrase("THE BUSINESS CO.OP BANK LTD.",new Font(f.TIMES_ROMAN,10,Font.BOLD));
			cell.addElement(createPara("THE BUSINESS CO.OP BANK LTD.",new Font(f.TIMES_ROMAN,10,Font.BOLD), Element.ALIGN_CENTER));
			/*Paragraph para = new Paragraph();
			para.setAlignment(Element.ALIGN_TOP);
			para.setSpacingBefore(10);*/
			

			image1.scaleAbsolute(80f, 80f);
			image1.setAbsolutePosition(36f, 710f);

			doc.add(image1);
			//doc.add(para);


		
			cell.addElement(createPara("                     Rajan Complex Arcade, Datta Mandir Circle, Nashik Road, Nashik - 422101",new Font(Font.TIMES_ROMAN,10), Element.ALIGN_CENTER));
			

			
			
			cell.addElement(createPara("Tel : 0253-2406100, 2469545",new Font(Font.TIMES_ROMAN,10), Element.ALIGN_CENTER));
			
			
//			cell.addElement(createPara("Email : hrd@namcobank.in",new Font(Font.TIMES_ROMAN,10), Element.ALIGN_CENTER));
			String dtg[]=date.split("-");
			
			String sqlg="";
			String name="";
			String DOJ="";
			String DOL="";
			String reason="";
			String pfno="";
			String left_reason="";
			int dateg=Integer.parseInt(dtg[2]);
			Statement st=con.createStatement();
			ResultSet rsg=null;
			String sqlg1="select DOL from Empmast where empno="+empno;
			ResultSet rsg2=st.executeQuery(sqlg1);
			/*if(rsg2.next())
			{
				String temdol=rsg2.getString("DOL");
				System.out.println("tremdol"+temdol);
				if(temdol==null )
				{
						
					sqlg="select RTRIM(fname)+' '+RTRIM(mname)+' '+RTRIM(lname) as Name,DOJ,DOL,PFNO from empmast WHERE EMPNO="+empno;
					System.out.println(" sqlg 2"+sqlg);
					rsg=st.executeQuery(sqlg);
					while(rsg.next())
					{
						name=rsg.getString("NAME");
						DOJ=rsg.getString("DOJ");
						DOL=rsg.getString("DOL");
						pfno=rsg.getString("PFNO");
						
					}
					
				}
				else
				{

					sqlg="select R.left_reason, E.PFNO, E.DOJ,E.DOL,rtrim(E.FNAME)+' '+RTRIM(E.MNAME)+' '+RTRIM(E.LNAME) as NAME,R.REASON from empmast E, RELIEVEINFO R where E.EMPNO=R.EMPNO and E.empno="+empno;
					System.out.println(" sqlg 1"+sqlg);
					rsg=st.executeQuery(sqlg);
					while(rsg.next())
					{
						name=rsg.getString("NAME");
						DOJ=rsg.getString("DOJ");
						DOL=rsg.getString("DOL");
						reason=rsg.getString("REASON");
						pfno=rsg.getString("PFNO");
						left_reason=rsg.getString("left_reason");
					}
					
				}
				
				
			}*/
			
			
			
			//sqlg="select R.left_reason, E.PFNO, E.DOJ,E.DOL,rtrim(E.FNAME)+' '+RTRIM(E.MNAME)+' '+RTRIM(E.LNAME) as NAME,R.REASON from empmast E, RELIEVEINFO R where E.EMPNO=R.EMPNO and E.empno="+empno;
			sqlg="select R.left_reason, E.PFNO, E.DOJ,E.DOL,rtrim(E.FNAME)+' '+RTRIM(E.MNAME)+' '+RTRIM(E.LNAME) as NAME,R.REASON from empmast E "
					+ "LEFT JOIN RELIEVEINFO R ON E.EMPNO=R.EMPNO where E.empno="+empno;
			System.out.println(" sqlg 1"+sqlg);
			rsg=st.executeQuery(sqlg);
			while(rsg.next())
			{
				name=rsg.getString("NAME");
				DOJ=rsg.getString("DOJ");
				DOL=rsg.getString("DOL");
				reason=rsg.getString("REASON");
				pfno=rsg.getString("PFNO");
				left_reason=rsg.getString("left_reason");
			}
			
			System.out.println(" govi :;"+name + reason + DOL + DOJ);
			String lkp_disc="";
			String left_reasonsql="select LKP_DISC from LOOKUP where  LKP_SRNO=(select LEFT_REASON from RELIEVEINFO where EMPNO="+empno+" ) and LKP_CODE='LFT_PURP'";
			ResultSet rsg1=st.executeQuery(left_reasonsql);
			while(rsg1.next())
			{
				
				lkp_disc=rsg1.getString("LKP_DISC");
			}
			
			
			
			
			cell.addElement(Chunk.NEWLINE);
			cell.addElement(createPara("   Ref.No.Ho/Admin./      /"+dtg[2]+"-"+(dateg+1)+"                                                    DATE :- "+sys,fG_under, Element.ALIGN_CENTER));
			cell.addElement(Chunk.NEWLINE);
			cell.addElement(createPara("Certificate",f_under, Element.ALIGN_CENTER));
			cell.addElement(Chunk.NEWLINE);
			System.out.println("EMPNO"+empno);
		
			cell.addElement(createPara("			Members Full Name :-  "+name+"                         ",fG_under, Element.ALIGN_LEFT));
			cell.addElement(Chunk.NEWLINE);
			/*cell.addElement(createPara("Certificate",f_under, Element.ALIGN_CENTER));
			cell.addElement(Chunk.NEWLINE);*/
			cell.addElement(createPara("			PF A/c. No.:- MH/13525/"+pfno+"                                     			DOJ:-"+DOJ,fG_under, Element.ALIGN_LEFT));
			cell.addElement(Chunk.NEWLINE);
			
			if(lkp_disc.equalsIgnoreCase("") || lkp_disc.equalsIgnoreCase("LEFT REASON"))
			{
					cell.addElement(createPara("			Date of Leaving :-"+DOL+"                                           		Reason:- "+reason,fG_under, Element.ALIGN_LEFT));
					cell.addElement(Chunk.NEWLINE);
			}
			else
			{
					cell.addElement(createPara("			Date of Leaving :-"+DOL+"                                           		Reason:- "+lkp_disc,fG_under, Element.ALIGN_LEFT));
					cell.addElement(Chunk.NEWLINE);
			}
			cell.addElement(createPara("Details of period of non-contributory service",f_under, Element.ALIGN_CENTER));
			cell.addElement(Chunk.NEWLINE);
			
			
			String empNo[]=empno.split(":");
			
			
			
			// SALARY STRUCTURE
			cell.addElement(salarystructure(empno,date,DOJ));
			
			cell.addElement(Chunk.NEWLINE);
			
			
			//tab1.addCell(createPara("This is to certify that amount of earning is correct and computed from " , f1, Element.ALIGN_CENTER));
			/*cell.addElement(createPara("salary Record of above Employee.", f1, Element.ALIGN_CENTER));*/
			cell.addElement(Chunk.NEWLINE);
			
			/*cell.addElement(createPara("For,THE BUSINESS CO.OP BANK LTD.", f1, Element.ALIGN_RIGHT));
			cell.addElement(Chunk.NEWLINE);*/
			cell.addElement(Chunk.NEWLINE);
			cell.addElement(createPara("     Deputy General Manager",fG_under, Element.ALIGN_LEFT));
			cell.addElement(Chunk.NEWLINE);
			
			//cell.addElement(footer());
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
	
	public static PdfPTable salarystructure(String empno,String d1,String d2)
	{
		PdfPTable tab3 = new PdfPTable(4);
		Font f1 = new Font(Font.HELVETICA,10);
		Font fb = new Font(Font.HELVETICA,9,Font.BOLD);
	
		try
		{		
				Connection con = ConnectionManager.getConnection();
				Statement st = con.createStatement();
				SimpleDateFormat fromf = new SimpleDateFormat("dd-MMM-yyyy");
				SimpleDateFormat tof = new SimpleDateFormat("yyyy-MM-dd");
				
				int td=Integer.parseInt("2019");
				String dt[]=d1.split("-");
				String dojDt[]=d2.split("-");
				Date dtt = fromf.parse(d1);
				String dt1 = tof.format(dtt);
				String dtd[] = dt1.split("-");
				int fd=Integer.parseInt(dojDt[0]);
				int toDate=0;
				
				if(Integer.parseInt(dtd[1]) > 3){
					toDate=Integer.parseInt(dt[2])+1;
				}else{
					toDate=Integer.parseInt(dt[2]);
				}
				
				tab3.setWidthPercentage(new float[]{20,30,20,30},new Rectangle(100,100));
				PdfPCell cell = new PdfPCell();
				cell.setBorder(0);
				int total=0;
				tab3.addCell((createCell("Year ", fb, Element.ALIGN_CENTER,0.5f)));
				tab3.addCell((createCell("No of days for which no weges were earned ", fb, Element.ALIGN_CENTER,0.5f)));
				tab3.addCell((createCell("Year ", fb, Element.ALIGN_CENTER,0.5f)));
				tab3.addCell((createCell("No of days for which no weges were earned ", fb, Element.ALIGN_CENTER,0.5f)));
				System.out.println("d12 :" +d1);
				String yearArr1[]=new String[17];
				String yearArr2[]=new String[17];
				int counter=0;
				int m=0,n=20;
				
				if(fd<1990)
				{
					for(int i=1990,j=0;i<toDate;i++,j++)
					{
						counter+=1;
						if(j<17)
						{
							yearArr1[j]=(i)+"-"+((i+1));
						}
						else
						{
							if(m<17)
							{
								yearArr2[m]=(i)+"-"+((i+1));
								m=m+1;
							}
						}
					}
				}
				else
				{
					for(int i=fd,j=0;i<toDate;i++,j++)
					{
						counter+=1;
						if(j<17)
						{
							yearArr1[j]=(i)+"-"+((i+1));
						}
						else
						{
							if(m<17)
							{
								yearArr2[m]=(i)+"-"+((i+1));
								m=m+1;
							}
						}
					}
				}
				int count=0;
				String totalArr[]=new String [40];
				ArrayList<String> totalArrList=new ArrayList<String>();
				
				if(fd<1990)
				{
					for(int i=1990,j=0;i<toDate;i++,j++)
					{
						counter=counter+1;
						int year;
						year=i+1;
						String sql;
						if(year % 4 == 0)
						{
							sql = "SELECT SUM(NET_AMT) as total FROM PAYTRAN_STAGE WHERE EMPNO="+empno	+" AND TRNDT BETWEEN '"+i+"-03-01' AND '"+(i+1)+"-02-29' AND TRNCD=301";
						}
						else
						{
							sql = "SELECT SUM(NET_AMT) as total FROM PAYTRAN_STAGE WHERE EMPNO="+empno	+" AND TRNDT BETWEEN '"+i+"-03-01' AND '"+(i+1)+"-02-28' AND TRNCD=301";	
						}
						ResultSet rs2 = st.executeQuery(sql);
						if(rs2.next())
						{
							totalArr[j]=rs2.getString("total");
							totalArrList.add(rs2.getString("total"));
						}
					}
				}
				else
				{
					for(int i=fd,j=0;i<toDate;i++,j++)
					{
						counter=counter+1;
						int year;
						year=i+1;
					
						String sql;
						
						if(year % 4 == 0)
						{
							  	sql = "SELECT SUM(NET_AMT) as total FROM PAYTRAN_STAGE WHERE EMPNO="+empno	+" AND TRNDT BETWEEN '"+i+"-03-01' AND '"+(year)+"-02-29' AND TRNCD=301";
						}
						else
						{
								sql = "SELECT SUM(NET_AMT) as total FROM PAYTRAN_STAGE WHERE EMPNO="+empno	+" AND TRNDT BETWEEN '"+i+"-03-01' AND '"+(year)+"-02-28' AND TRNCD=301";
						}
						ResultSet rs2 = st.executeQuery(sql);
						
						if(rs2.next())
						{
							totalArr[j]=rs2.getString("total");
							totalArrList.add(rs2.getString("total"));
						}
					}
				}
				 int yarr1=yearArr1.length;
				 int yarr2=yearArr2.length;
				 if(yarr1<yarr2)
				 {
					 count=yarr2;
				 }
				 else{
					 count=yarr1;
				 }
				 m=17;n=15;
				 int flag=0;
				 
				 int countArrList=totalArrList.size();
				 counter=0;
				for(int i=0;i<17;i++)
				{
					int YearForCheckValue=0;
					try 
					{
						String YearForCheck = yearArr2[i].substring(0,4);
						YearForCheckValue = Integer.parseInt(YearForCheck);
					}
					catch (Exception e) {
						// TODO: handle exception
					}
					
					if(i<17)
					{
						String Temp1="";
						String Temp2="";
						
						Temp1=totalArr[i]=="0" || totalArr[i]=="0.00" || totalArr[i]==null?"--":totalArr[i];
						
						if(YearForCheckValue>=2017) 
						{
							Temp2=totalArr[m]=="0" || totalArr[m]=="0.00" || totalArr[m]==null?"0.00":totalArr[m];
						}
						else 
						{
							Temp2=totalArr[m]=="0" || totalArr[m]=="0.00" || totalArr[m]==null?"--":totalArr[m];
						}
						
						tab3.addCell((createCell(yearArr1[i], f1, Element.ALIGN_CENTER,0.5f)));
						tab3.addCell((createCell(Temp1,  new Font(Font.ITALIC,10), Element.ALIGN_CENTER,00.5f)));
						tab3.addCell((createCell(yearArr2[i], f1, Element.ALIGN_CENTER,0.5f)));
						tab3.addCell((createCell(" "+Temp2,  new Font(Font.ITALIC,10), Element.ALIGN_CENTER,00.5f)));
						
						m+=1;
					}
					else
					{
						String Temp2=totalArr[m]=="0" || totalArr[m]=="0.00" || totalArr[m]==null?"--":totalArr[m];
						
						tab3.addCell((createCell("", f1, Element.ALIGN_CENTER,0.5f)));
						tab3.addCell((createCell(" ",  new Font(Font.ITALIC,10), Element.ALIGN_CENTER,00.5f)));
						tab3.addCell((createCell(yearArr2[flag], f1, Element.ALIGN_CENTER,0.5f)));
						tab3.addCell((createCell(" "+Temp2,  new Font(Font.ITALIC,10), Element.ALIGN_CENTER,00.5f)));
						n+=1;
						flag+=1;
					}
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
				tab3.addCell((createCell("Duputy General Manager", fb, Element.ALIGN_LEFT,0)));
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
