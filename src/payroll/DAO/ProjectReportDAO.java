package payroll.DAO;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import javax.servlet.ServletContext;
import org.jfree.ui.Align;
/*import org.w3c.dom.svg.GetSVGDocument;*/

import payroll.Core.Calculate;
import payroll.Core.ReportDAO;
import payroll.Core.UtilityDAO;
import payroll.Core.originalNumToLetter;
import payroll.Core.UtilityDAO.Footer;
import payroll.Model.RepoartBean;
import payroll.Model.TranBean;
import payroll.Model.TransactionBean;

import com.ibm.icu.text.NumberFormat;
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

import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProjectReportDAO {

	static Connection con = null;

	/*
	 * public static boolean addProjectReport(ProjectReportBean prb) { boolean
	 * flag=false; ResultSet rs=null; int pid=0; String
	 * sql1="Select projectId from AddProject where ProjectName=? and PCONDITION='Active'"
	 * ; String
	 * sql="insert into project_report( empName,projectName,statuss,typess,prioritys,descriptipon,projectId) values(?,?,?,?,?,?,?)"
	 * ; java.sql.PreparedStatement pst1=null; java.sql.PreparedStatement pst2=null;
	 * try { con=ConnectionManager.getConnection(); pst2=con.prepareStatement(sql1);
	 * pst2.setString(1,prb.getPROJECTNAME()); rs=pst2.executeQuery();
	 * while(rs.next()) { pid=rs.getInt(1); }
	 * 
	 * pst1=con.prepareStatement(sql); pst1.setString(1, prb.getEMPNAME());
	 * pst1.setString(2, prb.getPROJECTNAME()); pst1.setString(3, prb.getSTATUS());
	 * pst1.setString(4, prb.getTYPES()); pst1.setString(5,prb.getPRIORITY());
	 * pst1.setString(6,prb.getDESRIPTION()); pst1.setInt(7,pid); int
	 * n=pst1.executeUpdate(); System.out.println("n :"+n); if(n>0) { flag=true; }
	 * pst1.close(); pst2.close(); rs.close(); con.close(); } catch(SQLException se)
	 * { se.printStackTrace(); } return flag; }
	 */
	public static void reportProject(String filePath, String imagepath,String fileType, String type1, String subtype, String fromDate, String toDate)
			throws DocumentException, MalformedURLException, IOException, SQLException {
				
			
		System.out.println("i am in handler.."+filePath);
		

		System.out.println("i am in BonusList 01");
		OutputStream file = new FileOutputStream(new File(filePath));
		Document document = new Document();
		String table_name="";
		String prj_srno="";
		Connection con=null;
		con=ConnectionManager.getConnection();
		Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		Statement stmt1=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rspt = null;
		ResultSet rspt0 = null;
		String lable1="Report date : ";
		int srno=0;
		try {
		//	System.out.println("i am in BonusList 02");
			UtilityDAO dao = new UtilityDAO();
			PdfWriter writer =PdfWriter.getInstance(document, new FileOutputStream(filePath));

			Footer ftr = dao.new Footer(lable1);
			writer.setPageEvent(ftr);
			document.open();
			Image image1 = Image.getInstance(imagepath);
			image1.scaleAbsolute(80f, 80f);
			image1.setAbsolutePosition(40f, 730f); 
			document.add(image1);
			Phrase title = new Phrase("THE BUSINESS CO.OP BANK LTD.",new Font(Font.TIMES_ROMAN,9,Font.BOLD));
			Paragraph para = new Paragraph(title);
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingBefore(0);
			document.add(para);

			para = new Paragraph(new Phrase("Rajan Complex Arcade, Datta Mandir Circle, Nashik Road, Nashik - 422101",new Font(Font.TIMES_ROMAN,9)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);

			document.add(para);
			para = new Paragraph(new Phrase("Tel : 0253-2406100, 2469545",new Font(Font.TIMES_ROMAN,9)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);
	//		System.out.println("i am in BonusList 03");
			document.add(para);
			para = new Paragraph(new Phrase("PROJECT LIST ",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);
			document.add(para);
			para = new Paragraph(new Phrase("For The Year Of ",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);
			document.add(para);
			
			
			
			String getProject = "";  
			if(type1.equals("Pstatus"))
			{
				getProject="select P.projectId, P.ProjectName,P.PType,P.Pstatus,P.PPriority,P.StartDate,P.EndDate,T.TASK_NAME,T.TASK_ID,E.Name from AddProject P\r\n" + 
						"	inner join Task t on P.ProjectId=T.projectId inner join SubTask ST on T.TASK_ID=ST.TASK_ID inner join Assigner A on ST.Sub_ID=A.Sub_Id inner join Employee_PM E on A.Emp_Id=E.Emp_Id\r\n" + 
						"	where P.Pstatus='"+subtype+"' PCONDITION='Active'";
			}
			else if(type1.equals("PPriority"))
			{
				getProject="select P.projectId, P.ProjectName,P.PType,P.Pstatus,P.PPriority,P.StartDate,P.EndDate,T.TASK_NAME,T.TASK_ID,E.Name from AddProject P\r\n" + 
						"	inner join Task t on P.ProjectId=T.projectId inner join SubTask ST on T.TASK_ID=ST.TASK_ID inner join Assigner A on ST.Sub_ID=A.Sub_Id inner join Employee_PM E on A.Emp_Id=E.Emp_Id\r\n" + 
						"	where P.PPriority='"+subtype+"' and PCONDITION='Active'";
			}
			else
			{
				getProject="select P.projectId,P.ProjectName,P.PType,P.Pstatus,P.PPriority,P.StartDate,P.EndDate,T.TASK_NAME,T.TASK_ID,E.Name from AddProject P\r\n" + 
						"	inner join Task t on P.ProjectId=T.projectId inner join SubTask ST on T.TASK_ID=ST.TASK_ID inner join Assigner A on ST.Sub_ID=A.Sub_Id inner join Employee_PM E on A.Emp_Id=E.Emp_Id where P.PCONDITION='Active'";
			}
			
			rspt0=stmt.executeQuery(getProject);
			System.out.println("getProject "+getProject);
			
			
			
			/*String sql1 ="with a as(SELECT e.empno,"+
                    "e.empcode,"+
                    "Rtrim(e.fname) + ' ' + Rtrim(e.mname) + ' '"+
                   "+ Rtrim(e.lname) AS NAME,"+
                   "b.amtforbonus,"+
                   "b.bonus "+
                   "FROM   empmast e,"+
                   "bonuscal b "+
                   "WHERE  b.empno = e.empno "+
                   "AND b.status = 'P' and e.STATUS='A')"+
                   "select a.*,t.prj_srno from a, EMPTRAN t where "+
                   "t.EMPNO=a.EMPNO and t.SRNO=(select MAX(srno) "+
                   "from EMPTRAN where EMPNO =a.EMPNO) order by convert(int,t.PRJ_SRNO), a.EMPCODE";*/
		
			/*
			 * String sql1 ="with a as"+
			 * "(SELECT e.empno,e.empcode,Rtrim(e.fname) + ' ' + Rtrim(e.mname) + ' '+ Rtrim(e.lname) "
			 * + "AS NAME,b.amtforbonus,b.bonus,b.year FROM   empmast e,bonuscal b "+
			 * "WHERE  b.empno = e.empno AND b.status = 'P' ) "+
			 * "select a.*,t.prj_srno,t.DESIG,(select lkp_disc from LOOKUP where LKP_SRNO=t.DESIG and LKP_CODE='DESIG') as DESIG_DISC "
			 * + "from a, EMPTRAN t  where t.EMPNO=a.EMPNO "+
			 * "and t.SRNO=(select MAX(srno) from EMPTRAN where EMPNO =a.EMPNO) "+
			 * "and t.DESIG =(select DESIG  from emptran t2 where srno "+
			 * "= (select MAX(srno) from EMPTRAN t1 where t1.EMPNO =a.EMPNO)and t2.EMPNO =a.EMPNO) "
			 * + "order by convert(int,t.PRJ_SRNO), a.EMPCODE";
			 */
		
			/* System.out.println("query is  "+sql1); */
//		System.out.println("i am in BonusList 05");
		rspt = stmt1.executeQuery(getProject);
		
			while(rspt0.next())
			{
			
				/*
				 * para = new Paragraph(new
				 * Phrase("Employee's Bonus List For Project Site :  "+rspt0.getString(
				 * "ProjectName"),new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
				 * para.setAlignment(Element.ALIGN_CENTER); para.setSpacingAfter(0);
				 * document.add(para);
				 */
				/*
				 * if(rspt0.getInt("BRANCH")==999){
				 * System.out.println("i am in list 0000  "+rspt0.getInt("BRANCH"));}
				 */
			PdfPTable table=new PdfPTable(11);
			table.setSpacingBefore(10.0f);
			table.setWidthPercentage(new float[]{5,5,9,9,9,9,9,9,9,9,9},new Rectangle(100,100));
			PdfPCell cell1=new PdfPCell(new Phrase("SR NO",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell1);
			
			PdfPCell cell2=new PdfPCell(new Phrase("PR ID",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell2);
			
			PdfPCell cell3=new PdfPCell(new Phrase("PROJECT NAME",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell3);
			
			PdfPCell cell4=new PdfPCell(new Phrase("TYPE",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
			cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell4);
			
			PdfPCell cell5=new PdfPCell(new Phrase("STATUS",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
			cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell5);
			
			PdfPCell cell6=new PdfPCell(new Phrase("PRIORITY",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
			cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell6);
			
			PdfPCell cell7=new PdfPCell(new Phrase("START DATE",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
			cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell7);
			
			PdfPCell cell8=new PdfPCell(new Phrase("END DATE",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
			cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell8);
			
			PdfPCell cell9=new PdfPCell(new Phrase("TASK ID",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
			cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell9);
			
			PdfPCell cell10=new PdfPCell(new Phrase("TASK NAME",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
			cell10.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell10);
			
			PdfPCell cell11=new PdfPCell(new Phrase("EMP NAME",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell11);
			
			
			
			table.setHeaderRows(1);
			System.out.println("i am in list 00  "+rspt0.getInt("projectId"));
			
			
	//		System.out.println("i am in BonusList 05");
			
			double totatlBonus=0;
			int i = 0;
			while(rspt.next())
			{
				srno++;
				
				/*if(rspt0.getInt("projectId") == rspt.getInt("prj_srno"))
				{
				i++;
				if(rspt0.getInt("BRANCH")==999){
				System.out.println("i am in list 01  "+rspt0.getInt("BRANCH"));}
				srno++;
				
				prj_srno = rspt.getString("PRJ_SRNO");
*/				
				PdfPCell cell1data1=new PdfPCell(new Phrase(""+srno,new Font(Font.TIMES_ROMAN,9)));
				cell1data1.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell1data1);
				
				PdfPCell cell2data=new PdfPCell(new Phrase(""+rspt.getString("projectId"),new Font(Font.TIMES_ROMAN,9)));
				cell2data.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell2data);
				
				//String empname=rspt.getString("fname")+" "+rspt.getString("mname")+" "+rspt.getString("lname");
				PdfPCell cell3data3=new PdfPCell(new Phrase(""+rspt.getString("ProjectName"),new Font(Font.TIMES_ROMAN,9)));
				cell3data3.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell3data3);
				
				PdfPCell cell4data4=new PdfPCell(new Phrase(""+rspt.getString("PType"),new Font(Font.TIMES_ROMAN,9)));
				cell4data4.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell4data4);
				
				PdfPCell cell5data5=new PdfPCell(new Phrase(""+rspt.getString("Pstatus"),new Font(Font.TIMES_ROMAN,9)));
				cell5data5.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell5data5);
		
				PdfPCell cell6data6=new PdfPCell(new Phrase(""+rspt.getString("PPriority"),new Font(Font.TIMES_ROMAN,9)));
				cell6data6.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell6data6);
				
				PdfPCell cell7data7=new PdfPCell(new Phrase(""+rspt.getString("StartDate"),new Font(Font.TIMES_ROMAN,9)));
				cell7data7.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell7data7);
				
				PdfPCell cell8data8=new PdfPCell(new Phrase(""+rspt.getString("EndDate"),new Font(Font.TIMES_ROMAN,9)));
				cell8data8.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell8data8);
				
				PdfPCell cell9data9=new PdfPCell(new Phrase(""+rspt.getString("TASK_ID"),new Font(Font.TIMES_ROMAN,9)));
				cell9data9.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell9data9);
				
				PdfPCell cell10data10=new PdfPCell(new Phrase(""+rspt.getString("TASK_NAME"),new Font(Font.TIMES_ROMAN,9)));
				cell10data10.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell10data10);
				
				PdfPCell cell11data11=new PdfPCell(new Phrase(""+rspt.getString("Name"),new Font(Font.TIMES_ROMAN,9)));
				cell11data11.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell11data11);
				

				//totatlBonus=totatlBonus + rspt.getInt("projectId");
					/*
					 * }
					 * 
					 * 
					 * else { rspt.previous(); break; }
					 */
				
			}
			//footer
				/*
				 * PdfPTable table1=new PdfPTable(6); table1.setSpacingBefore(0);
				 * table1.setWidthPercentage(new float[]{6,12,35,25,13,13},new
				 * Rectangle(100,100)); PdfPCell cell11=new PdfPCell(new
				 * Phrase("Total projectId:   "+i,new Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				 * cell11.setHorizontalAlignment(Element.ALIGN_LEFT); cell11.setColspan(3);
				 * table1.addCell(cell11);
				 * 
				 * 
				 * PdfPCell cell21=new PdfPCell(new Phrase("Total Project",new
				 * Font(Font.TIMES_ROMAN,10,Font.BOLD)));
				 * cell21.setHorizontalAlignment(Element.ALIGN_LEFT); cell21.setColspan(2);
				 * table1.addCell(cell21);
				 * 
				 * PdfPCell totaldata=new PdfPCell(new Phrase(""+totatlBonus,new
				 * Font(Font.TIMES_ROMAN,9,Font.BOLD)));
				 * cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * table1.addCell(totaldata);
				 */
			
			
				
				  PdfPCell totemp = new PdfPCell(new Phrase("Total Employee:",new
				  Font(Font.TIMES_ROMAN,9,Font.BOLD)));
				  totemp.setHorizontalAlignment(Element.ALIGN_CENTER); totemp.setColspan(2);
				  table.addCell(totemp); PdfPCell totempdisp = new PdfPCell(new Phrase(""+i,new
				  Font(Font.TIMES_ROMAN,9,Font.BOLD)));
				  totemp.setHorizontalAlignment(Element.ALIGN_CENTER);
				  table.addCell(totempdisp); PdfPCell total=new PdfPCell(new
				  Phrase("Total Bonus",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
				  total.setHorizontalAlignment(Element.ALIGN_CENTER); table.addCell(total);
				  PdfPCell totaldata=new PdfPCell(new Phrase(""+totatlBonus,new
				  Font(Font.TIMES_ROMAN,9,Font.BOLD)));
				  totaldata.setHorizontalAlignment(Element.ALIGN_CENTER);
				  table.addCell(totaldata);
				 
			document.add(table);
			
			//footer Table
				/* document.add(table1); */
			
			if(!rspt0.next())
			{
				break;
			}
			else{
				rspt0.previous();
				continue;
			}
				 
		}
			document.close();
			file.close();
			con.close();

		} catch (Exception e)
		{		
			e.printStackTrace();
		}
		finally
		{
			Runtime.getRuntime()
			   .exec("rundll32 url.dll,FileProtocolHandler " + filePath);
			file.close();
			
		}

	
	
	
	}
}