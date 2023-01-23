package payroll.Core;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import payroll.Core.UtilityDAO.Footer;
import payroll.DAO.CodeMasterHandler;
import payroll.DAO.ConnectionManager;
import payroll.DAO.EmployeeHandler;
import payroll.DAO.GradeHandler;
import payroll.DAO.LookupHandler;
import payroll.Model.EmpOffBean;
import payroll.Model.RepoartBean;
import payroll.Model.TaxReportBean;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
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
import com.lowagie.text.pdf.PdfWriter;

public class YearlyPDFReport {

	/**
	 * @author Nakul Patil & Sachin Mesare Disha Technologies
	 */

	static Font F10Normal = new Font(Font.TIMES_ROMAN, 8, Font.NORMAL);
	static Font F10Bold = new Font(Font.TIMES_ROMAN, 9, Font.BOLD);

	public static Paragraph createPara(String value, Font f, int alignment) {
		Paragraph para = new Paragraph(value, f);
		para.setAlignment(alignment);
		para.setSpacingAfter(0);
		para.setSpacingBefore(0);
		return para;

	}

	public static Paragraph createParaWithPhrase(Phrase value, int alignment,
			float setSpaceAfter, float setSpaceBefore) {
		Paragraph para = new Paragraph(value);
		para.setAlignment(alignment);
		para.setSpacingAfter(setSpaceAfter);
		para.setSpacingBefore(setSpaceBefore);
		return para;

	}

	public static PdfPCell createCell(String value, Font f, int alignment) {
		PdfPCell cell = new PdfPCell(new Phrase(value, f));
		cell.addElement(createPara(value, f, alignment));
		cell.setBorderWidth(0.5F);

		return cell;
	}

	public void printReport(String list, String date, String imgpath,
			String filepath) {
		try {
			Connection cn = ConnectionManager.getConnection();
			Statement st = cn.createStatement();
			Statement st1 = cn.createStatement();
			ResultSet rs = null;

			// For oracle
			/*
			 * String sql =
			 * "select y.trncd,disc,sum(decode(to_char(trndt,'mm'),04,net_amt,0)) apr,"
			 * + "sum(decode(to_char(trndt,'mm'),05,net_amt,0)) may, "+
			 * "sum(decode(to_char(trndt,'mm'),06,net_amt,0)) jun, "+
			 * "sum(decode(to_char(trndt,'mm'),07,net_amt,0)) jul, "+
			 * "sum(decode(to_char(trndt,'mm'),08,net_amt,0)) aug, "+
			 * "sum(decode(to_char(trndt,'mm'),09,net_amt,0)) sep, "+
			 * "sum(decode(to_char(trndt,'mm'),10,net_amt,0)) oct, "+
			 * "sum(decode(to_char(trndt,'mm'),11,net_amt,0)) nov, "+
			 * "sum(decode(to_char(trndt,'mm'),12,net_amt,0)) dec, "+
			 * "sum(decode(to_char(trndt,'mm'),01,net_amt,0)) jan, "+
			 * "sum(decode(to_char(trndt,'mm'),02,net_amt,0)) feb, "+
			 * "sum(decode(to_char(trndt,'mm'),03,net_amt,0)) mar "+
			 * "from empmast e,CDMAST c,ytdtran y "+ "where e.empno = "+empno+
			 * " and e.empno = y.empno "+ "and y.trncd = c.trncd "+
			 * "and y.trndt between '"
			 * +ReportDAO.Boy(date)+"' and '"+ReportDAO.Eoy(date)+"' "+
			 * "and ( y.trncd < 300 or y.trncd = 999 ) "+
			 * "group by  e.empno,fname,lname,disc,y.trncd "+
			 * "order by y.trncd";
			 */

			Document doc = new Document();
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(
					filepath));
			// writer.setEncryption(userPass.getBytes(), ownerPass.getBytes(),
			// PdfWriter.ALLOW_PRINTING,PdfWriter.ENCRYPTION_AES_128);
			doc.open();
			// doc.setMargins(10, 72, 108, 180);
			doc.setPageSize(PageSize.A4);
			Image image1 = Image.getInstance(imgpath);

			String sql2 = "select empno from empmast where empno in (" + list + ")";
			Statement st2 = cn.createStatement();
			ResultSet rs2 = st2.executeQuery(sql2);
			while (rs2.next()) {

				/*String sql = " select y.trncd,disc,"
						+ "sum(case datepart(MM,trndt) when 04 then (CASE WHEN Y.TRNCD IN (198,199) THEN cal_amt ELSE net_amt END) else  0 end) apr,"
						+ "sum(case datepart(MM,trndt) when 05 then (CASE WHEN Y.TRNCD IN (198,199) THEN cal_amt ELSE net_amt END) else  0 end) may,"
						+ "sum(case datepart(MM,trndt) when 06 then (CASE WHEN Y.TRNCD IN (198,199) THEN cal_amt ELSE net_amt END) else  0 end) jun,"
						+ "sum(case datepart(MM,trndt) when 07 then (CASE WHEN Y.TRNCD IN (198,199) THEN cal_amt ELSE net_amt END) else  0 end) jul,"
						+ "sum(case datepart(MM,trndt) when 08 then (CASE WHEN Y.TRNCD IN (198,199) THEN cal_amt ELSE net_amt END) else  0 end) aug,"
						+ "sum(case datepart(MM,trndt) when 09 then (CASE WHEN Y.TRNCD IN (198,199) THEN cal_amt ELSE net_amt END) else  0 end) sep,"
						+ "sum(case datepart(MM,trndt) when 10 then (CASE WHEN Y.TRNCD IN (198,199) THEN cal_amt ELSE net_amt END) else  0 end) oct,"
						+ "sum(case datepart(MM,trndt) when 11 then (CASE WHEN Y.TRNCD IN (198,199) THEN cal_amt ELSE net_amt END) else  0 end) nov,"
						+ "sum(case datepart(MM,trndt) when 12 then (CASE WHEN Y.TRNCD IN (198,199) THEN cal_amt ELSE net_amt END) else  0 end) dec,"
						+ "sum(case datepart(MM,trndt) when 01 then (CASE WHEN Y.TRNCD IN (198,199) THEN cal_amt ELSE net_amt END) else  0 end) jan,"
						+ "sum(case datepart(MM,trndt) when 02 then (CASE WHEN Y.TRNCD IN (198,199) THEN cal_amt ELSE net_amt END) else  0 end) feb,"
						+ "sum(case datepart(MM,trndt) when 03 then (CASE WHEN Y.TRNCD IN (198,199) THEN cal_amt ELSE net_amt END) else  0 end) mar,"
						+ "Sum(CASE WHEN Y.TRNCD IN (198,199) THEN CAL_AMT ELSE NET_AMT END) AS Total "
						+ "from empmast e,CDMAST c,ytdtran y "
						+ "where e.empno = " + rs2.getInt(1)
						+ " and e.empno = y.empno " + "and y.trncd = c.trncd "
						+ "and y.trndt between '" + ReportDAO.Boy(date)
						+ "' and '" + ReportDAO.Eoy(date) + "' "
						+ "and ( y.trncd < 300 or y.trncd = 999 ) and y.trncd <> 199"
						+ "group by  e.empno,fname,lname,disc,y.trncd "
						+ "order by y.trncd";*/
				
				/*String sql = "SELECT y.trncd, disc, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 04 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) apr, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 05 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) may, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 06 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) jun, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 07 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) jul, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 08 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) aug, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 09 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) + case when Y.trncd=198 then (isnull((select sum(case when cal_amt>0 then cal_amt else net_amt end ) from ytdtran st where st.empno= e.empno AND st.TRNDT BETWEEN '"+ ReportDAO.Boy(date) +"' AND '" + ReportDAO.Eoy(date) + "' and st.TRNCD=135),0) ) else 0 end sep, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 10 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) oct, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 11 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) nov, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 12 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) dec, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 01 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) + case when Y.trncd=198 then (isnull((select sum(case when cal_amt>0 then cal_amt else net_amt end ) from ytdtran st where st.empno= e.empno AND st.TRNDT BETWEEN '"+ ReportDAO.Boy(date) +"' AND '" + ReportDAO.Eoy(date) + "' and st.TRNCD=145),0) ) else 0 end jan, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 02 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) feb, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 03 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) mar, "
						+ "       Sum(CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END) + case when Y.trncd=198 then  isnull((select sum(case when cal_amt>0 then cal_amt else net_amt end ) from ytdtran st where st.empno= e.empno AND st.TRNDT BETWEEN '"+ ReportDAO.Boy(date) +"' AND '" + ReportDAO.Eoy(date) + "' and st.TRNCD =135),0) else 0 end + case when Y.trncd=198 then  isnull((select sum(case when cal_amt>0 then cal_amt else net_amt end ) from ytdtran st where st.empno= e.empno AND st.TRNDT BETWEEN '"+ ReportDAO.Boy(date) +"' AND '" + ReportDAO.Eoy(date) + "' and st.TRNCD =145),0) else 0 end AS Total "
						+ "FROM   empmast e, cdmast c, ytdtran y "
						+ "WHERE  e.empno = " + rs2.getInt(1)
						+ "       AND e.empno = y.empno "
						+ "       AND y.trncd = c.trncd "
						+ "       AND y.trndt BETWEEN '"+ ReportDAO.Boy(date) +"' AND '" + ReportDAO.Eoy(date) + "' "
						+ "       AND ( y.trncd < 300 OR y.trncd = 999 ) "
						+ "       AND y.trncd <> 199 "
						+ "GROUP  BY e.empno, fname, lname, disc, y.trncd ORDER  BY y.trncd";
				*/
				
				String startyeardate = "";
				String endyeardate = "";
				
				startyeardate = ReportDAO.Boy(date).substring(7);
				endyeardate = ReportDAO.Eoy(date).substring(7);
				
				System.out.println("startyeardate : "+startyeardate);
				System.out.println("endyeardate : "+endyeardate);
				
				//Check Leap Year for update Feb Date
				int leapdate;
				
				
				int checkleapyear = Integer.parseInt(endyeardate);
				
				if(checkleapyear % 4 == 0)
		        {
		            if( checkleapyear % 100 == 0)
		            {
		                if ( checkleapyear % 400 == 0)
		                {
		                	leapdate=29;
		                }
		                else
		                	leapdate = 28;
		            }
		            else
		            	leapdate=29;
		        }
		        else 
		        {
		        	leapdate = 28;
		        }
				
				
/*				String sql = "SELECT y.trncd, disc, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 04 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '01-APR-"+startyeardate+"' AND '30-APR-"+startyeardate+"' AND st.trncd=111), 0) ) ELSE 0 END apr, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 05 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '01-MAY-"+startyeardate+"' AND '31-MAY-"+startyeardate+"' AND st.trncd=111), 0) ) ELSE 0 END may, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 06 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '01-JUN-"+startyeardate+"' AND '30-JUN-"+startyeardate+"' AND st.trncd=111), 0) ) ELSE 0 END jun, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 07 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '01-JUL-"+startyeardate+"' AND '31-JUL-"+startyeardate+"' AND st.trncd=111), 0) ) ELSE 0 END jul, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 08 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '01-AUG-"+startyeardate+"' AND '31-AUG-"+startyeardate+"' AND st.trncd=111), 0) ) ELSE 0 END aug, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 09 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) + case when Y.trncd=198 then (isnull((select sum(case when cal_amt>0 then cal_amt else net_amt end ) from ytdtran st where st.empno= e.empno AND st.TRNDT BETWEEN '"+ ReportDAO.Boy(date) +"' AND '" + ReportDAO.Eoy(date) + "' and st.TRNCD=135),0) ) else 0 end + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '01-SEP-"+startyeardate+"' AND '30-SEP-"+startyeardate+"' AND st.trncd=111), 0) ) ELSE 0 END sep, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 10 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '01-OCT-"+startyeardate+"' AND '31-OCT-"+startyeardate+"' AND st.trncd=111), 0) ) ELSE 0 END oct, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 11 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '01-NOV-"+startyeardate+"' AND '30-NOV-"+startyeardate+"' AND st.trncd=111), 0) ) ELSE 0 END nov, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 12 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '01-DEC-"+startyeardate+"' AND '31-DEC-"+startyeardate+"' AND st.trncd=111), 0) ) ELSE 0 END dec, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 01 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) + case when Y.trncd=198 then (isnull((select sum(case when cal_amt>0 then cal_amt else net_amt end ) from ytdtran st where st.empno= e.empno AND st.TRNDT BETWEEN '"+ ReportDAO.Boy(date) +"' AND '" + ReportDAO.Eoy(date) + "' and st.TRNCD=145),0) ) else 0 end + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '01-JAN-"+endyeardate+"' AND '31-JAN-"+endyeardate+"' AND st.trncd=111), 0) ) ELSE 0 END jan, "
						//+ "       Sum(CASE Datepart(mm, trndt) WHEN 02 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '01-FEB-"+endyeardate+"' AND '29-FEB-"+endyeardate+"' AND st.trncd=111), 0) ) ELSE 0 END feb, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 02 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '01-FEB-"+endyeardate+"' AND '"+leapdate+"-FEB-"+endyeardate+"' AND st.trncd=111), 0) ) ELSE 0 END feb, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 03 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '01-MAR-"+endyeardate+"' AND '31-MAR-"+endyeardate+"' AND st.trncd=111), 0) ) ELSE 0 END mar, "
						+ "       Sum(CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END) + case when Y.trncd=198 then  isnull((select sum(case when cal_amt>0 then cal_amt else net_amt end ) from ytdtran st where st.empno= e.empno AND st.TRNDT BETWEEN '"+ ReportDAO.Boy(date) +"' AND '" + ReportDAO.Eoy(date) + "' and st.TRNCD =135),0) else 0 end + case when Y.trncd=198 then  isnull((select sum(case when cal_amt>0 then cal_amt else net_amt end ) from ytdtran st where st.empno= e.empno AND st.TRNDT BETWEEN '"+ ReportDAO.Boy(date) +"' AND '" + ReportDAO.Eoy(date) + "' and st.TRNCD =145),0) else 0 end + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '"+ ReportDAO.Boy(date) +"' AND '"+ ReportDAO.Eoy(date) +"' AND st.trncd=111), 0) ) ELSE 0 END AS Total "
						+ "FROM   empmast e, cdmast c, ytdtran y "
						+ "WHERE  e.empno = " + rs2.getInt(1)
						+ "       AND e.empno = y.empno "
						+ "       AND y.trncd = c.trncd "
						+ "       AND y.trndt BETWEEN '"+ ReportDAO.Boy(date) +"' AND '" + ReportDAO.Eoy(date) + "' "
						+ "       AND ( y.trncd < 300 OR y.trncd = 999 ) "
						+ "       AND y.trncd <> 199 "
						+ "GROUP  BY e.empno, fname, lname, disc, y.trncd ORDER  BY y.trncd";
*/				
				System.out.println("startyeardate NEW :"+startyeardate);
				System.out.println("endyeardate NEW :"+endyeardate);
			
				String sql="";
					sql = "SELECT y.trncd, disc, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 04 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN 0 ELSE net_amt END ) ELSE 0 END) + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '01-APR-"+startyeardate+"' AND '30-APR-"+startyeardate+"' AND st.trncd=111), 0) ) + (CASE WHEN Y.trncd=198 THEN (SELECT SUM(INP_AMT) FROM YTDTRAN st1 WHERE TRNCD=999 AND e.EMPNO=st1.EMPNO AND TRNDT BETWEEN '01-APR-"+startyeardate+"' AND '30-APR-"+startyeardate+"' ) ELSE 0 END) ELSE 0 END apr, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 05 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN 0 ELSE net_amt END ) ELSE 0 END) + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '01-MAY-"+startyeardate+"' AND '31-MAY-"+startyeardate+"' AND st.trncd=111), 0) ) + (CASE WHEN Y.trncd=198 THEN (SELECT SUM(INP_AMT) FROM YTDTRAN st1 WHERE TRNCD=999 AND e.EMPNO=st1.EMPNO AND TRNDT BETWEEN '01-MAY-"+startyeardate+"' AND '31-MAY-"+startyeardate+"' ) ELSE 0 END) ELSE 0 END may, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 06 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN 0 ELSE net_amt END ) ELSE 0 END) + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '01-JUN-"+startyeardate+"' AND '30-JUN-"+startyeardate+"' AND st.trncd=111), 0) ) + (CASE WHEN Y.trncd=198 THEN (SELECT SUM(INP_AMT) FROM YTDTRAN st1 WHERE TRNCD=999 AND e.EMPNO=st1.EMPNO AND TRNDT BETWEEN '01-JUN-"+startyeardate+"' AND '30-JUN-"+startyeardate+"' ) ELSE 0 END) ELSE 0 END jun, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 07 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN 0 ELSE net_amt END ) ELSE 0 END) + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '01-JUL-"+startyeardate+"' AND '31-JUL-"+startyeardate+"' AND st.trncd=111), 0) ) + (CASE WHEN Y.trncd=198 THEN (SELECT SUM(INP_AMT) FROM YTDTRAN st1 WHERE TRNCD=999 AND e.EMPNO=st1.EMPNO AND TRNDT BETWEEN '01-JUL-"+startyeardate+"' AND '31-JUL-"+startyeardate+"' ) ELSE 0 END) ELSE 0 END jul, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 08 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN 0 ELSE net_amt END ) ELSE 0 END) + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '01-AUG-"+startyeardate+"' AND '31-AUG-"+startyeardate+"' AND st.trncd=111), 0) ) + (CASE WHEN Y.trncd=198 THEN (SELECT SUM(INP_AMT) FROM YTDTRAN st1 WHERE TRNCD=999 AND e.EMPNO=st1.EMPNO AND TRNDT BETWEEN '01-AUG-"+startyeardate+"' AND '31-AUG-"+startyeardate+"' ) ELSE 0 END) ELSE 0 END aug, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 09 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN 0 ELSE net_amt END ) ELSE 0 END) + case when Y.trncd=198 then (isnull((select sum(case when cal_amt>0 then cal_amt else net_amt end ) from ytdtran st where st.empno= e.empno AND st.TRNDT BETWEEN '"+ ReportDAO.Boy(date) +"' AND '" + ReportDAO.Eoy(date) + "' and st.TRNCD=135),0) ) else 0 end + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '01-SEP-"+startyeardate+"' AND '30-SEP-"+startyeardate+"' AND st.trncd=111), 0) ) + (CASE WHEN Y.trncd=198 THEN (SELECT SUM(INP_AMT) FROM YTDTRAN st1 WHERE TRNCD=999 AND e.EMPNO=st1.EMPNO AND TRNDT BETWEEN '01-SEP-"+startyeardate+"' AND '30-SEP-"+startyeardate+"' ) ELSE 0 END) ELSE 0 END sep, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 10 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN 0 ELSE net_amt END ) ELSE 0 END) + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '01-OCT-"+startyeardate+"' AND '31-OCT-"+startyeardate+"' AND st.trncd=111), 0) ) + (CASE WHEN Y.trncd=198 THEN (SELECT SUM(INP_AMT) FROM YTDTRAN st1 WHERE TRNCD=999 AND e.EMPNO=st1.EMPNO AND TRNDT BETWEEN '01-OCT-"+startyeardate+"' AND '31-OCT-"+startyeardate+"' ) ELSE 0 END) ELSE 0 END oct, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 11 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN 0 ELSE net_amt END ) ELSE 0 END) + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '01-NOV-"+startyeardate+"' AND '30-NOV-"+startyeardate+"' AND st.trncd=111), 0) ) + (CASE WHEN Y.trncd=198 THEN (SELECT SUM(INP_AMT) FROM YTDTRAN st1 WHERE TRNCD=999 AND e.EMPNO=st1.EMPNO AND TRNDT BETWEEN '01-NOV-"+startyeardate+"' AND '30-NOV-"+startyeardate+"' ) ELSE 0 END) ELSE 0 END nov, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 12 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN 0 ELSE net_amt END ) ELSE 0 END) + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '01-DEC-"+startyeardate+"' AND '31-DEC-"+startyeardate+"' AND st.trncd=111), 0) ) + (CASE WHEN Y.trncd=198 THEN (SELECT SUM(INP_AMT) FROM YTDTRAN st1 WHERE TRNCD=999 AND e.EMPNO=st1.EMPNO AND TRNDT BETWEEN '01-DEC-"+startyeardate+"' AND '31-DEC-"+startyeardate+"' ) ELSE 0 END) ELSE 0 END dec, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 01 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN 0 ELSE net_amt END ) ELSE 0 END) + case when Y.trncd=198 then (isnull((select sum(case when cal_amt>0 then net_amt else net_amt end ) from ytdtran st where st.empno= e.empno AND st.TRNDT BETWEEN '"+ ReportDAO.Boy(date) +"' AND '" + ReportDAO.Eoy(date) + "' and st.TRNCD=145),0) ) else 0 end + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '01-JAN-"+endyeardate+"' AND '31-JAN-"+endyeardate+"' AND st.trncd=111), 0) ) + (CASE WHEN Y.trncd=198 THEN (SELECT SUM(INP_AMT) FROM YTDTRAN st1 WHERE TRNCD=999 AND e.EMPNO=st1.EMPNO AND TRNDT BETWEEN '01-JAN-"+endyeardate+"' AND '31-JAN-"+endyeardate+"' ) ELSE 0 END) ELSE 0 END jan, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 02 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN 0 ELSE net_amt END ) ELSE 0 END) + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '01-FEB-"+endyeardate+"' AND '"+leapdate+"-FEB-"+endyeardate+"' AND st.trncd=111), 0) ) + (CASE WHEN Y.trncd=198 THEN (SELECT SUM(INP_AMT) FROM YTDTRAN st1 WHERE TRNCD=999 AND e.EMPNO=st1.EMPNO AND TRNDT BETWEEN '01-FEB-"+endyeardate+"' AND '"+leapdate+"-FEB-"+endyeardate+"' ) ELSE 0 END) ELSE 0 END feb, "
						+ "       Sum(CASE Datepart(mm, trndt) WHEN 03 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN 0 ELSE net_amt END ) ELSE 0 END) + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '01-MAR-"+endyeardate+"' AND '31-MAR-"+endyeardate+"' AND st.trncd=111), 0) ) + (CASE WHEN Y.trncd=198 THEN (SELECT SUM(INP_AMT) FROM YTDTRAN st1 WHERE TRNCD=999 AND e.EMPNO=st1.EMPNO AND TRNDT BETWEEN '01-MAR-"+endyeardate+"' AND '31-MAR-"+endyeardate+"' ) ELSE 0 END) ELSE 0 END mar, "
						+ "       Sum(CASE WHEN Y.trncd IN ( 198, 199 ) THEN 0 ELSE net_amt END) + case when Y.trncd=198 then  isnull((select sum(case when cal_amt>0 then cal_amt else net_amt end ) from ytdtran st where st.empno= e.empno AND st.TRNDT BETWEEN '"+ ReportDAO.Boy(date) +"' AND '" + ReportDAO.Eoy(date) + "' and st.TRNCD =135),0) else 0 end + case when Y.trncd=198 then  isnull((select sum(case when cal_amt>0 then net_amt else net_amt end ) from ytdtran st where st.empno= e.empno AND st.TRNDT BETWEEN '"+ ReportDAO.Boy(date) +"' AND '" + ReportDAO.Eoy(date) + "' and st.TRNCD =145),0) else 0 end + CASE WHEN Y.trncd=198 THEN (Isnull((SELECT Sum(CASE WHEN cal_amt>0 THEN cal_amt ELSE net_amt END ) FROM ytdtran st WHERE st.empno= e.empno AND st.trndt BETWEEN '"+ ReportDAO.Boy(date) +"' AND '"+ ReportDAO.Eoy(date) +"' AND st.trncd=111), 0) ) + (CASE WHEN Y.trncd=198 THEN (SELECT SUM(INP_AMT) FROM YTDTRAN st1 WHERE TRNCD=999 AND e.EMPNO=st1.EMPNO AND TRNDT BETWEEN '"+ ReportDAO.Boy(date) +"' AND '"+ ReportDAO.Eoy(date) +"' ) ELSE 0 END) ELSE 0 END AS Total "
						+ "FROM   empmast e, cdmast c, ytdtran y "
						+ "WHERE  e.empno = " + rs2.getInt(1)
						+ "       AND e.empno = y.empno "
						+ "       AND y.trncd = c.trncd "
						+ "       AND y.trndt BETWEEN '"+ ReportDAO.Boy(date) +"' AND '" + ReportDAO.Eoy(date) + "' "
						+ "       AND ( y.trncd < 300 OR y.trncd = 999 ) "
						//+ "       AND y.trncd <> 199 "
						+ "       AND y.trncd NOT IN (136, 137, 199) "
						+ "GROUP  BY e.empno, fname, lname, disc, y.trncd ORDER  BY y.trncd";

				
				System.out.println("sql ==> "+sql);
				rs = st.executeQuery(sql);
				

				String empname = "select empcode,salute,empno,rtrim(fname)+' '+rtrim(mname)+' '+rtrim(lname) name,doj from empmast "
						+ "Where empno=" + rs2.getInt(1);
				ResultSet rs1 = st1.executeQuery(empname);

				Font FONT = new Font(Font.HELVETICA, 40, Font.NORMAL,
						new GrayColor(0.75f));
				ColumnText.showTextAligned(writer.getDirectContentUnder(),
						Element.ALIGN_CENTER, new Phrase("Ch.Shahu Hospital", FONT),
						297.5f, 421, 45);

				Phrase title = new Phrase(
						"Rajeshri Chatrapati Shahu Maharaj hospital,Jalgaon", new Font(
								Font.TIMES_ROMAN, 12, Font.BOLD));
				Paragraph para = new Paragraph(title);
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingBefore(0);

				image1.scaleAbsolute(60f, 50f);
				image1.setAbsolutePosition(40f, 750f);

				doc.add(image1);
				doc.add(para);

				para = new Paragraph(
						new Phrase(
								"Rajeshri Chatrapati Shahu Maharaj hospital, Shahu Nagar, Jalgaon-425001",
								new Font(Font.TIMES_ROMAN, 8)));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(0);

				doc.add(para);
				para = new Paragraph(new Phrase(
						"Tel : 0257 2223301",
						new Font(Font.TIMES_ROMAN, 8)));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(0);

				doc.add(para);
//				para = new Paragraph(new Phrase("Email : adm@namcobank.in",
//						new Font(Font.TIMES_ROMAN, 8)));
//				para.setAlignment(Element.ALIGN_CENTER);
//				para.setSpacingAfter(0);
//
//				doc.add(para);

				para = new Paragraph(new Phrase(
						"Employee's Yearly Salary Report for Financial Year "
								+ ReportDAO.Boy(date).substring(7) + " - "
								+ ReportDAO.Eoy(date).substring(7), new Font(
								Font.TIMES_ROMAN, 10)));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(10);

				doc.add(para);

				PdfPTable tab = new PdfPTable(15);
				tab.setWidthPercentage(new float[] { 3.5f, 18, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6.5f}, new Rectangle(100, 100));
				tab.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell cell1 = new PdfPCell();
				cell1.setColspan(15);

				LookupHandler lkhp = new LookupHandler();
				if (rs1.next()) {

					PdfPTable tab1 = new PdfPTable(2);
					tab1.setWidthPercentage(new float[] { 70, 30 },
							new Rectangle(100, 100));

					PdfPCell subcell = new PdfPCell(new Phrase(
							"Employee Number : " + rs1.getString("empcode"),
							new Font(Font.TIMES_ROMAN, 10, Font.BOLD)));
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tab1.addCell(subcell);
					subcell = new PdfPCell(new Phrase("Date : " + date,
							new Font(Font.TIMES_ROMAN, 10, Font.BOLD)));
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tab1.addCell(subcell);

					subcell = new PdfPCell(new Phrase("Employee Name : "
							+ lkhp.getLKP_Desc("SALUTE", rs1.getInt("SALUTE"))
							+ " " + rs1.getString("name"), new Font(
							Font.TIMES_ROMAN, 10, Font.BOLD)));
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tab1.addCell(subcell);
					subcell = new PdfPCell(new Phrase("D.O.J : "
							+ EmployeeHandler.dateFormat(rs1.getDate("DOJ")),
							new Font(Font.TIMES_ROMAN, 10, Font.BOLD)));
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tab1.addCell(subcell);

					cell1.addElement(tab1);
					tab.addCell(cell1);
				}

				PdfPCell[] cell = null;
				cell = new PdfPCell[15];
				cell[0] = new PdfPCell(new Phrase("SR.NO", new Font(
						Font.TIMES_ROMAN, 8, Font.BOLD)));
				cell[1] = new PdfPCell(new Phrase("DESCRIPTION", new Font(
						Font.TIMES_ROMAN, 8, Font.BOLD)));
				cell[2] = new PdfPCell(new Phrase("APR", new Font(
						Font.TIMES_ROMAN, 8, Font.BOLD)));
				cell[3] = new PdfPCell(new Phrase("MAY", new Font(
						Font.TIMES_ROMAN, 8, Font.BOLD)));
				cell[4] = new PdfPCell(new Phrase("JUN", new Font(
						Font.TIMES_ROMAN, 8, Font.BOLD)));
				cell[5] = new PdfPCell(new Phrase("JUL", new Font(
						Font.TIMES_ROMAN, 8, Font.BOLD)));
				cell[6] = new PdfPCell(new Phrase("AUG", new Font(
						Font.TIMES_ROMAN, 8, Font.BOLD)));
				cell[7] = new PdfPCell(new Phrase("SEP", new Font(
						Font.TIMES_ROMAN, 8, Font.BOLD)));
				cell[8] = new PdfPCell(new Phrase("OCT", new Font(
						Font.TIMES_ROMAN, 8, Font.BOLD)));
				cell[9] = new PdfPCell(new Phrase("NOV", new Font(
						Font.TIMES_ROMAN, 8, Font.BOLD)));
				cell[10] = new PdfPCell(new Phrase("DEC", new Font(
						Font.TIMES_ROMAN, 8, Font.BOLD)));
				cell[11] = new PdfPCell(new Phrase("JAN", new Font(
						Font.TIMES_ROMAN, 8, Font.BOLD)));
				cell[12] = new PdfPCell(new Phrase("FEB", new Font(
						Font.TIMES_ROMAN, 8, Font.BOLD)));
				cell[13] = new PdfPCell(new Phrase("MAR", new Font(
						Font.TIMES_ROMAN, 8, Font.BOLD)));
				cell[14] = new PdfPCell(new Phrase("Total", new Font(
						Font.TIMES_ROMAN, 8, Font.BOLD)));
				for (int i = 0; i < 15; i++) {
					cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					tab.addCell(cell[i]);
				}
				int cnt = 1;
				while (rs.next()) {
					cell = new PdfPCell[15];
					for (int i = 0; i < 15; i++) {
						if (i == 0) {
							cell[i] = new PdfPCell(new Phrase("" + cnt,
									new Font(Font.TIMES_ROMAN, 7)));
							cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
							cell[i].setFixedHeight(16);
						} else {
							cell[i] = new PdfPCell(new Phrase(
									rs.getString(i + 1), new Font(
											Font.TIMES_ROMAN, 7)));
							}
						if (i > 1) {
							cell[i].setHorizontalAlignment(Element.ALIGN_RIGHT);
							}
						
						tab.addCell(cell[i]);
					}
					cnt++;
				}
				doc.add(tab);
				doc.newPage();
				doc.close();
			}
			//doc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void printCtcReport(String list, String date, String imgpath,
			String filepath) {
		try {
			Connection cn = ConnectionManager.getConnection();
			Statement st = cn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Statement st1 = cn.createStatement();
			ResultSet rs = null;

			// For oracle
			/*
			 * String sql =
			 * "select y.trncd,disc,sum(decode(to_char(trndt,'mm'),04,net_amt,0)) apr,"
			 * + "sum(decode(to_char(trndt,'mm'),05,net_amt,0)) may, "+
			 * "sum(decode(to_char(trndt,'mm'),06,net_amt,0)) jun, "+
			 * "sum(decode(to_char(trndt,'mm'),07,net_amt,0)) jul, "+
			 * "sum(decode(to_char(trndt,'mm'),08,net_amt,0)) aug, "+
			 * "sum(decode(to_char(trndt,'mm'),09,net_amt,0)) sep, "+
			 * "sum(decode(to_char(trndt,'mm'),10,net_amt,0)) oct, "+
			 * "sum(decode(to_char(trndt,'mm'),11,net_amt,0)) nov, "+
			 * "sum(decode(to_char(trndt,'mm'),12,net_amt,0)) dec, "+
			 * "sum(decode(to_char(trndt,'mm'),01,net_amt,0)) jan, "+
			 * "sum(decode(to_char(trndt,'mm'),02,net_amt,0)) feb, "+
			 * "sum(decode(to_char(trndt,'mm'),03,net_amt,0)) mar "+
			 * "from empmast e,CDMAST c,ytdtran y "+ "where e.empno = "+empno+
			 * " and e.empno = y.empno "+ "and y.trncd = c.trncd "+
			 * "and y.trndt between '"
			 * +ReportDAO.Boy(date)+"' and '"+ReportDAO.Eoy(date)+"' "+
			 * "and ( y.trncd < 300 or y.trncd = 999 ) "+
			 * "group by  e.empno,fname,lname,disc,y.trncd "+
			 * "order by y.trncd";
			 */

			Document doc = new Document();
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(
					filepath));
			// writer.setEncryption(userPass.getBytes(), ownerPass.getBytes(),
			// PdfWriter.ALLOW_PRINTING,PdfWriter.ENCRYPTION_AES_128);
			doc.open();
			// doc.setMargins(10, 72, 108, 180);
			doc.setPageSize(PageSize.A4);
			Image image1 = Image.getInstance(imgpath);

			Font FONT = new Font(Font.HELVETICA, 40, Font.NORMAL,
					new GrayColor(0.75f));
			ColumnText.showTextAligned(writer.getDirectContentUnder(),
					Element.ALIGN_CENTER, new Phrase("Ch.Shahu Hospital", FONT),
					297.5f, 421, 45);

			Phrase title = new Phrase("Rajeshri Chatrapati Shahu Maharaj hospital,Jalgaon",
					new Font(Font.TIMES_ROMAN, 12, Font.BOLD));
			Paragraph para = new Paragraph(title);
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingBefore(0);

			image1.scaleAbsolute(60f, 50f);
			image1.setAbsolutePosition(40f, 750f);

			doc.add(image1);
			doc.add(para);

			para = new Paragraph(
					new Phrase(
							"Rajeshri Chatrapati Shahu Maharaj hospital, Shahu Nagar, Jalgaon-425001",
							new Font(Font.TIMES_ROMAN, 8)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);

			doc.add(para);
			para = new Paragraph(new Phrase(
					"Tel : 0257 2223301",
					new Font(Font.TIMES_ROMAN, 8)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);

			doc.add(para);
//			para = new Paragraph(new Phrase("Email : adm@namcobank.in",
//					new Font(Font.TIMES_ROMAN, 8)));
//			para.setAlignment(Element.ALIGN_CENTER);
//			para.setSpacingAfter(0);
//
//			doc.add(para);

			para = new Paragraph(new Phrase(
					"Employee's Yearly CTC Report for Financial Year "
							+ ReportDAO.Boy(date).substring(7,11) + " - "
							+ ReportDAO.Eoy(date).substring(7,11), new Font(
							Font.TIMES_ROMAN, 10)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(10);

			doc.add(para);

			String sql2 = "select empno from empmast where empno in (" + list
					+ ")";
			Statement st2 = cn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs2 = st2.executeQuery(sql2);
			while (rs2.next()) {

				String sql = "if EXISTS      (select y.trncd,disc,sum(case datepart(MM,trndt) when 04 then inp_amt else  0 end) apr,sum(case datepart(MM,trndt) when 05 then inp_amt else  0 end) may,sum(case datepart(MM,trndt) when 06 then inp_amt else  0 end) jun,sum(case datepart(MM,trndt) when 07 then inp_amt else  0 end) jul,sum(case datepart(MM,trndt) when 08 then inp_amt else  0 end) aug,sum(case datepart(MM,trndt) when 09 then inp_amt else  0 end) sep,sum(case datepart(MM,trndt) when 10 then inp_amt else  0 end) oct,sum(case datepart(MM,trndt) when 11 then inp_amt else  0 end) nov,sum(case datepart(MM,trndt) when 12 then inp_amt else  0 end) dec,sum(case datepart(MM,trndt) when 01 then inp_amt else  0 end) jan,sum(case datepart(MM,trndt) when 02 then inp_amt else  0 end) feb,sum(case datepart(MM,trndt) when 03 then inp_amt else  0 end) mar from empmast e, cdmast c, ytdtran y where e.empno = "
						+ rs2.getInt(1)
						+ " and e.empno = y.empno and y.trncd = c.trncd and y.trndt between '"
						+ ReportDAO.Boy(date)
						+ "' and '"
						+ ReportDAO.Eoy(date)
						+ "' and y.trncd = 199 and c.trncd=199 group by  e.empno,fname,lname,disc,y.trncd)select y.trncd,disc,sum(case datepart(MM,trndt) when 04 then inp_amt else  0 end) apr,sum(case datepart(MM,trndt) when 05 then inp_amt else  0 end) may,sum(case datepart(MM,trndt) when 06 then inp_amt else  0 end) jun,sum(case datepart(MM,trndt) when 07 then inp_amt else  0 end) jul,sum(case datepart(MM,trndt) when 08 then inp_amt else  0 end) aug,sum(case datepart(MM,trndt) when 09 then inp_amt else  0 end) sep,sum(case datepart(MM,trndt) when 10 then inp_amt else  0 end) oct,sum(case datepart(MM,trndt) when 11 then inp_amt else  0 end) nov,sum(case datepart(MM,trndt) when 12 then inp_amt else  0 end) dec,sum(case datepart(MM,trndt) when 01 then inp_amt else  0 end) jan,sum(case datepart(MM,trndt) when 02 then inp_amt else  0 end) feb,sum(case datepart(MM,trndt) when 03 then inp_amt else  0 end) mar from empmast e, cdmast c, ytdtran y where e.empno = "
						+ rs2.getInt(1)
						+ " and e.empno = y.empno and y.trncd = c.trncd and y.trndt between '"
						+ ReportDAO.Boy(date)
						+ "' and '"
						+ ReportDAO.Eoy(date)
						+ "' and y.trncd = 199 and c.trncd=199 group by  e.empno,fname,lname,disc,y.trncd else if EXISTS      (select y.trncd,disc,sum(case datepart(MM,trndt) when 04 then inp_amt else  0 end) apr,sum(case datepart(MM,trndt) when 05 then inp_amt else  0 end) may,sum(case datepart(MM,trndt) when 06 then inp_amt else  0 end) jun,sum(case datepart(MM,trndt) when 07 then inp_amt else  0 end) jul,sum(case datepart(MM,trndt) when 08 then inp_amt else  0 end) aug,sum(case datepart(MM,trndt) when 09 then inp_amt else  0 end) sep,sum(case datepart(MM,trndt) when 10 then inp_amt else  0 end) oct,sum(case datepart(MM,trndt) when 11 then inp_amt else  0 end) nov,sum(case datepart(MM,trndt) when 12 then inp_amt else  0 end) dec,sum(case datepart(MM,trndt) when 01 then inp_amt else  0 end) jan,sum(case datepart(MM,trndt) when 02 then inp_amt else  0 end) feb,sum(case datepart(MM,trndt) when 03 then inp_amt else  0 end) mar from empmast e, cdmast c, Paytran_Stage y where e.empno = "
						+ rs2.getInt(1)
						+ " and e.empno = y.empno and y.trncd = c.trncd and y.trndt between '"
						+ ReportDAO.Boy(date)
						+ "' and '"
						+ ReportDAO.Eoy(date)
						+ "' and y.trncd = 199 and c.trncd=199 group by  e.empno,fname,lname,disc,y.trncd)select y.trncd,disc,sum(case datepart(MM,trndt) when 04 then inp_amt else  0 end) apr,sum(case datepart(MM,trndt) when 05 then inp_amt else  0 end) may,sum(case datepart(MM,trndt) when 06 then inp_amt else  0 end) jun,sum(case datepart(MM,trndt) when 07 then inp_amt else  0 end) jul,sum(case datepart(MM,trndt) when 08 then inp_amt else  0 end) aug,sum(case datepart(MM,trndt) when 09 then inp_amt else  0 end) sep,sum(case datepart(MM,trndt) when 10 then inp_amt else  0 end) oct,sum(case datepart(MM,trndt) when 11 then inp_amt else  0 end) nov,sum(case datepart(MM,trndt) when 12 then inp_amt else  0 end) dec,sum(case datepart(MM,trndt) when 01 then inp_amt else  0 end) jan,sum(case datepart(MM,trndt) when 02 then inp_amt else  0 end) feb,sum(case datepart(MM,trndt) when 03 then inp_amt else  0 end) mar from empmast e, cdmast c, Paytran_Stage y where e.empno = "
						+ rs2.getInt(1)
						+ " and e.empno = y.empno and y.trncd = c.trncd and y.trndt between '"
						+ ReportDAO.Boy(date)
						+ "' and '"
						+ ReportDAO.Eoy(date)
						+ "' and y.trncd = 199 and c.trncd=199 group by  e.empno,fname,lname,disc,y.trncd else SELECT 199, 'COST TO COMPANY', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,0";
				rs = st.executeQuery(sql);

				String empname = "select empcode,salute,empno,rtrim(fname)+' '+rtrim(mname)+' '+rtrim(lname) name,doj from empmast "
						+ "Where empno=" + rs2.getInt(1);
				ResultSet rs1 = st1.executeQuery(empname);

				PdfPTable tab = new PdfPTable(14);
				tab.setWidthPercentage(new float[] { 4, 18.5f, 6.5f, 6.5f,
						6.5f, 6.5f, 6.5f, 6.5f, 6.5f, 6.5f, 6.5f, 6.5f, 6.5f,
						6.5f }, new Rectangle(100, 100));
				tab.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell cell1 = new PdfPCell();
				cell1.setColspan(14);

				LookupHandler lkhp = new LookupHandler();
				PdfPCell[] cell = null;
				cell = new PdfPCell[14];
				if (rs1.next() && rs.next()) {

					PdfPTable tab1 = new PdfPTable(2);
					tab1.setWidthPercentage(new float[] { 70, 30 },
							new Rectangle(100, 100));

					PdfPCell subcell = new PdfPCell(new Phrase(
							"Employee Number : " + rs1.getString("empcode"),
							new Font(Font.TIMES_ROMAN, 10, Font.BOLD)));
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tab1.addCell(subcell);
					subcell = new PdfPCell(new Phrase("Date : " + date,
							new Font(Font.TIMES_ROMAN, 10, Font.BOLD)));
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tab1.addCell(subcell);

					subcell = new PdfPCell(new Phrase("Employee Name : "
							+ lkhp.getLKP_Desc("SALUTE", rs1.getInt("SALUTE"))
							+ " " + rs1.getString("name"), new Font(
							Font.TIMES_ROMAN, 10, Font.BOLD)));
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tab1.addCell(subcell);
					subcell = new PdfPCell(new Phrase("D.O.J : "
							+ EmployeeHandler.dateFormat(rs1.getDate("DOJ")),
							new Font(Font.TIMES_ROMAN, 10, Font.BOLD)));
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tab1.addCell(subcell);

					cell1.addElement(tab1);
					tab.addCell(cell1);

					cell[0] = new PdfPCell(new Phrase("SR.NO", new Font(
							Font.TIMES_ROMAN, 8, Font.BOLD)));
					cell[1] = new PdfPCell(new Phrase("DESCRIPTION", new Font(
							Font.TIMES_ROMAN, 8, Font.BOLD)));
					cell[2] = new PdfPCell(new Phrase("APR", new Font(
							Font.TIMES_ROMAN, 8, Font.BOLD)));
					cell[3] = new PdfPCell(new Phrase("MAY", new Font(
							Font.TIMES_ROMAN, 8, Font.BOLD)));
					cell[4] = new PdfPCell(new Phrase("JUN", new Font(
							Font.TIMES_ROMAN, 8, Font.BOLD)));
					cell[5] = new PdfPCell(new Phrase("JUL", new Font(
							Font.TIMES_ROMAN, 8, Font.BOLD)));
					cell[6] = new PdfPCell(new Phrase("AUG", new Font(
							Font.TIMES_ROMAN, 8, Font.BOLD)));
					cell[7] = new PdfPCell(new Phrase("SEP", new Font(
							Font.TIMES_ROMAN, 8, Font.BOLD)));
					cell[8] = new PdfPCell(new Phrase("OCT", new Font(
							Font.TIMES_ROMAN, 8, Font.BOLD)));
					cell[9] = new PdfPCell(new Phrase("NOV", new Font(
							Font.TIMES_ROMAN, 8, Font.BOLD)));
					cell[10] = new PdfPCell(new Phrase("DEC", new Font(
							Font.TIMES_ROMAN, 8, Font.BOLD)));
					cell[11] = new PdfPCell(new Phrase("JAN", new Font(
							Font.TIMES_ROMAN, 8, Font.BOLD)));
					cell[12] = new PdfPCell(new Phrase("FEB", new Font(
							Font.TIMES_ROMAN, 8, Font.BOLD)));
					cell[13] = new PdfPCell(new Phrase("MAR", new Font(
							Font.TIMES_ROMAN, 8, Font.BOLD)));

					for (int i = 0; i < 14; i++) {
						cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
						tab.addCell(cell[i]);
					}
				}
				int cnt = 1;
				rs.beforeFirst();
				while (rs.next()) {
					cell = new PdfPCell[14];
					for (int i = 0; i < 14; i++) {
						if (i == 0) {
							cell[i] = new PdfPCell(new Phrase("" + cnt,
									new Font(Font.TIMES_ROMAN, 7)));
							cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
							cell[i].setFixedHeight(16);
						} else {
							cell[i] = new PdfPCell(new Phrase(
									rs.getString(i + 1), new Font(
											Font.TIMES_ROMAN, 7)));
						}
						if (i > 1) {
							cell[i].setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
						tab.addCell(cell[i]);
					}
					cnt++;
				}
				doc.add(tab);
				doc.add(Chunk.NEWLINE);
				// doc.newPage();
			}
			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printform16(int peno, String prdt, String pename,
			int desigcd, String PAN_NO, String SEX1, java.sql.Date dob,
			RepoartBean repBean, TaxReportBean taxRepBean) {
		String grd = "";
		int grdint = 0;

		taxRepBean.setC_ul0(0);
		taxRepBean.setC_ul1(40);
		taxRepBean.setC_ul2(53);
		taxRepBean.setC_ul3(66);
		taxRepBean.setC_ul4(79);
		taxRepBean.setC_rs0(31);
		taxRepBean.setC_rs1(41);
		taxRepBean.setC_rs2(54);
		taxRepBean.setC_rs3(67);
		taxRepBean.setC_am0(34);
		taxRepBean.setC_am1(44);
		taxRepBean.setC_am2(57);
		taxRepBean.setC_am3(70);
		grdint = desigcd;
		grd = GradeHandler.GetGrade(grdint, "Y", repBean);
		try {
			repBean.setHdg1("Form 16");
			repBean.setHdg2("");

			// ***************REMAIN ING********************

			print_f16_head(peno, prdt, pename, grdint, PAN_NO, repBean,
					taxRepBean);
			printgrp1(peno, prdt, pename, grd, PAN_NO, SEX1, dob, repBean,
					taxRepBean);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void print_f16_head(int peno, String prdt, String pename,
			int dsigcd, String PAN_NO, RepoartBean repBean,
			TaxReportBean taxRepBean) {

		repBean.setHdg1("Form 16");
		String str1 = "";
		ResultSet rsTmp = null;
		String vITWard = "";
		String Q1_CNO = "";
		String Q2_CNO = "";
		String Q3_CNO = "";
		String Q4_CNO = "";
		String msql = "";
		String total1 = "";
		long total2 = 0;
		int i = 0;
		String year = ReportDAO.BoFinancialy(prdt);
		System.out.println("BoFinancialy : "+ReportDAO.BoFinancialy(prdt));
		System.out.println("this is BOY..." + year);
		System.out.println("only year...." + year.substring(7, 11));
		msql = "select disc from param_tab where code = 'ITWARD' AND STATUS = 1";
		try {
			Statement st = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Statement st1 = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Statement st22 = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Statement st33 = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Statement st44 = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			rsTmp = st.executeQuery(msql);

			if (rsTmp.next()) {
				vITWard = rsTmp.getString("DISC");
			} else {
				System.out
						.println("INCOME TAX WARD OF BANK NOT FOUNF IN PARAMTER");
				return;
			}
			msql = "select disc from param_tab where code = 'Q1_CNO' AND STATUS = 1";
			rsTmp = st.executeQuery(msql);
			if (rsTmp.next()) {
				Q1_CNO = rsTmp.getString("DISC");
			} else {
				System.out
						.println("1ST QUARTER ACKNOWLEDGEMENT NO. not found in parameter");
				return;
			}

			msql = "select disc from param_tab where code = 'Q2_CNO' AND STATUS = 1";
			rsTmp = st.executeQuery(msql);
			if (rsTmp.next()) {
				Q2_CNO = rsTmp.getString("DISC");
			} else {
				System.out
						.println("2ND QUARTER ACKNOWLEDGEMENT NO. not found in parameter");
				return;
			}

			msql = "select disc from param_tab where code = 'Q3_CNO' AND STATUS = 1";
			rsTmp = st.executeQuery(msql);
			if (rsTmp.next()) {
				Q3_CNO = rsTmp.getString("DISC");
			} else {
				System.out
						.println("3RD QUARTER ACKNOWLEDGEMENT NO. not found in parameter");
				return;
			}
			msql = "select disc from param_tab where code = 'Q4_CNO' AND STATUS = 1";
			rsTmp = st.executeQuery(msql);
			if (rsTmp.next()) {
				Q4_CNO = rsTmp.getString("DISC");
			} else {
				System.out
						.println("4TH QUARTER ACKNOWLEDGEMENT NO. not found in parameter");
				return;
			}
			rsTmp = null;
			rsTmp = st1.executeQuery("SELECT * FROM ORG_DETAILS");
			rsTmp.next();
			Phrase title = new Phrase("FORM 16", F10Bold);
			taxRepBean.getDoc().add(
					createParaWithPhrase(title, Element.ALIGN_CENTER, 0, 0));

			title = new Phrase("[See rule 31(1)(a)]", F10Normal);
			taxRepBean.getDoc().add(
					createParaWithPhrase(title, Element.ALIGN_CENTER, 0, 0));

			title = new Phrase("PART A ", F10Bold);
			taxRepBean.getDoc().add(
					createParaWithPhrase(title, Element.ALIGN_CENTER, 5, 5));
			/*
			 * title = new Phrase(
			 * "Certificate under section 203 of the Income-tax Act, 1961 for tax deducted at source"
			 * ,F10Bold);
			 * taxRepBean.getDoc().add(createParaWithPhrase(title,Element
			 * .ALIGN_CENTER,0,0));
			 * 
			 * title = new
			 * Phrase("from income chargeable under the head “Salaries”"
			 * ,F10Bold);
			 * taxRepBean.getDoc().add(createParaWithPhrase(title,Element
			 * .ALIGN_CENTER,10,0));
			 */
			PdfPTable tab11 = new PdfPTable(1);
			tab11.setWidthPercentage(new float[] { 100 }, new Rectangle(100,
					100));
			PdfPCell subcell1 = new PdfPCell(
					new Phrase(
							"Certificate under section 203 of the Income-tax Act, 1961 for tax deducted at source on salary",
							F10Bold));
			// subcell1.setBorderWidthLeft(0);
			tab11.setSpacingBefore(7);
			// subcell1.setBackgroundColor(new GrayColor(0.90f));
			subcell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab11.addCell(subcell1);
			taxRepBean.getDoc().add(tab11);

			PdfPTable tab1 = new PdfPTable(2);
			tab1.setWidthPercentage(new float[] { 50, 50 }, new Rectangle(100,
					100));

			PdfPCell subcell2 = new PdfPCell(new Phrase("Certificate No.",
					F10Bold));
			// subcell2.setBorderWidthLeft(0);
			// subcell2.setBackgroundColor(new GrayColor(0.90f));
			subcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab1.addCell(subcell2);
			subcell2 = new PdfPCell(new Phrase("Last updated on", F10Bold));
			// subcell2.setBorderWidthRight(0);
			// subcell2.setBackgroundColor(new GrayColor(0.90f));
			subcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab1.addCell(subcell2);

			PdfPCell subcell3 = new PdfPCell(new Phrase("--", F10Normal));
			// subcell2.setBorderWidthLeft(0);
			subcell3.setBackgroundColor(new GrayColor(0.90f));
			// subcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab1.addCell(subcell3);
			PdfPCell subcell4 = new PdfPCell(new Phrase("--", F10Normal));
			// subcell2.setBorderWidthLeft(0);
			subcell4.setBackgroundColor(new GrayColor(0.90f));
			// subcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab1.addCell(subcell4);

			PdfPCell subcell = new PdfPCell(new Phrase(
					"Name and address of the Employer", F10Bold));
			// subcell.setBorderWidthLeft(0);
			// subcell.setBackgroundColor(new GrayColor(0.90f));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(
					"Name and designation of the Employee", F10Bold));
			// subcell.setBorderWidthRight(0);
			// subcell.setBackgroundColor(new GrayColor(0.90f));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);
			subcell = new PdfPCell(
					new Phrase(
							"THE BUSINESS CO.OP BANK LTD., Rajan Complex Arcade, Datta Mandir Circle, Nashik Road, Nashik - 422101.",
							F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);

			LookupHandler lookupHandler = new LookupHandler();
			EmpOffBean efbn = new EmpOffBean();

			subcell = new PdfPCell(new Phrase(pename + "  "
					+ lookupHandler.getLKP_Desc("DESIG", dsigcd), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);
			taxRepBean.getDoc().add(tab1);

			tab1 = new PdfPTable(2);
			// tab1.setSpacingBefore(2);
			tab1.setWidthPercentage(new float[] { 50, 50 }, new Rectangle(100,
					100));

			PdfPTable tab2 = new PdfPTable(2);
			tab2.setWidthPercentage(new float[] { 50, 50 }, new Rectangle(100,
					100));

			subcell = new PdfPCell(new Phrase("PAN No. of \n the Deductor",
					F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab2.addCell(subcell);
			subcell = new PdfPCell(new Phrase("TAN No. of \n the Deductor",
					F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab2.addCell(subcell);

			PdfPCell subcellpan = new PdfPCell(new Phrase(""
					+ rsTmp.getString("Org_PAN"), F10Normal));
			subcellpan.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcellpan.setMinimumHeight(25);
			tab2.addCell(subcellpan);
			PdfPCell subcelltan = new PdfPCell(new Phrase(""
					+ rsTmp.getString("Org_TAN"), F10Normal));
			subcelltan.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcelltan.setMinimumHeight(25);
			tab2.addCell(subcelltan);

			subcell.addElement(tab2);

			tab1.addCell(subcell);

			tab2 = new PdfPTable(2);
			tab2.setWidthPercentage(new float[] { 50, 50 }, new Rectangle(100,
					100));
			subcell = new PdfPCell(new Phrase("PAN No. of the Employee",
					F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab2.addCell(subcell);
			subcell = new PdfPCell(
					new Phrase(
							"Employee Reference No.provided by the Employer(If available)",
							F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab2.addCell(subcell);

			PdfPCell subcellpano = new PdfPCell(new Phrase(""
					+ ((PAN_NO == null || PAN_NO == "") ? "--" : PAN_NO),
					F10Normal));
			subcellpano.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab2.addCell(subcellpano);
			PdfPCell subcellref = new PdfPCell(new Phrase("--", F10Normal));
			subcellref.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab2.addCell(subcellref);

			subcell.addElement(tab2);

			tab1.addCell(subcell);

			taxRepBean.getDoc().add(tab1);
			tab1 = new PdfPTable(5);
			// tab1.setSpacingBefore(2);
			tab1.setWidthPercentage(new float[] { 25, 25, 15, 15, 20 },
					new Rectangle(100, 100));

			/*
			 * subcell = new PdfPCell(new Phrase(
			 * "Acknowledgement Nos. of all quarterly statements of TDS under sub-section (3) of section 200 as provided by TIN Facilitation"
			 * + "Centre or NSDL web-site",F10Normal));
			 */
			subcell = new PdfPCell(
					new Phrase(
							"CIT (TDS) \n\n Address:-  3rd Floor, PMT Building SWARGATE, PUNE               \n                      \n City:-  PUNE                    \n\n Pin code:-              ",
							F10Bold));
			subcell.setLeft(15);
			subcell.setRight(15);
			subcell.setTop(40);
			subcell.setBorderWidthBottom(0);
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setColspan(2);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase("Period", F10Bold));
			subcell.setColspan(2);
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase("Assessment year", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab1.addCell(subcell);

			/*
			 * subcell = new PdfPCell(new Phrase("Quarter",F10Bold));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * tab1.addCell(subcell);
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("Acknowledgement No.",F10Bold));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * tab1.addCell(subcell);
			 */

			subcell = new PdfPCell(new Phrase("", F10Bold));
			// subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setColspan(2);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase("From", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase("To", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			/*
			 * subcell = new PdfPCell(new Phrase("Quarter 1",F10Bold));
			 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tab1.addCell(subcell);
			 * 
			 * subcell = new PdfPCell(new Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tab1.addCell(subcell);
			 */
			subcell = new PdfPCell(new Phrase("", F10Bold));
			// subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			// subcell.setBorderWidthBottom(0);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setColspan(2);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase(" Apr "
					+ (ReportDAO.getMonth(prdt) > 2 ? prdt.substring(7, 11)
							: Integer.parseInt(prdt.substring(7, 11)) - 1),
					F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase(ReportDAO.EoFinancialy(prdt)
					.substring(3, 6)
					+ " "
					+ (ReportDAO.getMonth(prdt) > 2 ? Integer.parseInt(prdt
							.substring(7, 11)) + 1 : Integer.parseInt(prdt
							.substring(7, 11))), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);

			subcell = new PdfPCell(
					new Phrase(
							(ReportDAO.getMonth(prdt) < 3 ? prdt.substring(7,
									11) : Integer.parseInt(prdt
									.substring(7, 11)) + 1)
									+ " - "
									+ (ReportDAO.getMonth(prdt) < 3 ? Integer
											.parseInt(prdt.substring(7, 11)) + 1
											: Integer.parseInt(prdt.substring(
													7, 11)) + 2), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			taxRepBean.getDoc().add(tab1);
			PdfPTable tab111 = new PdfPTable(1);
			tab111.setWidthPercentage(new float[] { 100 }, new Rectangle(100,
					100));
			PdfPCell subcells = new PdfPCell(
					new Phrase(
							"Summary of amount paid/credited and tax deducted at source thereon in respect of the employee \n",
							F10Bold));
			// subcell1.setBorderWidthLeft(0);
			// tab111.setSpacingBefore(5);
			// subcell1.setBackgroundColor(new GrayColor(0.90f));
			subcells.setHorizontalAlignment(Element.ALIGN_CENTER);

			tab111.addCell(subcells);
			taxRepBean.getDoc().add(tab111);

			tab1 = new PdfPTable(5);
			// tab1.setSpacingBefore(2);
			tab1.setWidthPercentage(new float[] { 25, 25, 15, 15, 20 },
					new Rectangle(100, 100));

			subcell = new PdfPCell(new Phrase("Quarter(s)", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthTop(2);
			tab1.addCell(subcell);

			subcell = new PdfPCell(
					new Phrase(
							"Receipt Numbers of original quarterly statements of TDS under sub-section (3) of section 200",
							F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthTop(2);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase("Amount paid/credited", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthTop(2);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase(
					"Amount of tax deducted \n (Rs. )", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthTop(2);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase(
					"Amount of tax deposited/remitted \n (Rs. )", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthTop(2);
			tab1.addCell(subcell);

			taxRepBean.getDoc().add(tab1);

			/*ResultSet quarter = st22
					.executeQuery(" SELECT * FROM QUARTER_DETAILS WHERE FINANCIAL_YEAR='"
							+ year.substring(7, 11) + "' order by Quarter_No");
			*/
			
			
			/*ResultSet quarter = st22
					.executeQuery("SELECT * FROM QUARTER_DETAILS WHERE Financial_Year=2019 AND Quarter_No>1 "
							+ "UNION ALL SELECT * FROM QUARTER_DETAILS WHERE Financial_Year=2020 AND Quarter_No<=1");*/
			

			
			String year1=year.substring(7, 11);
		int year2=Integer.parseInt(year1);
		year2=year2+1;
			System.out.println("year2 :"+year2);
			
/*			ResultSet quarter = st22.executeQuery("WITH A AS(SELECT * FROM QUARTER_DETAILS WHERE Financial_Year='"+ year.substring(7, 11) + "' AND Quarter_No>1 "
							+ "UNION ALL SELECT * FROM QUARTER_DETAILS WHERE Financial_Year='"+ year2 + "' AND Quarter_No<=1 ) "
							+ "SELECT * FROM A ORDER BY CASE  WHEN Quarter_No=2 THEN 1 "
							+ "WHEN Quarter_No=3 THEN 2 WHEN Quarter_No=4 THEN 3 WHEN Quarter_No=1 THEN 4 ELSE 0 END ");
*/			
			ResultSet quarter = st22.executeQuery("WITH A AS(SELECT * FROM QUARTER_DETAILS WHERE Financial_Year='"+ year.substring(7, 11) + "' AND Quarter_No>=1 "
					+ "UNION ALL SELECT * FROM QUARTER_DETAILS WHERE Financial_Year='"+ year2 + "' AND Quarter_No<=4 ) "
					+ "SELECT * FROM A ORDER BY quarter_no");
			
			
			
			System.out.println("Quarter Query : WITH A AS(SELECT * FROM QUARTER_DETAILS WHERE Financial_Year='"+ year.substring(7, 11) + "' AND Quarter_No>=1 "
							+ "UNION ALL SELECT * FROM QUARTER_DETAILS WHERE Financial_Year='"+ year2 + "' AND Quarter_No<=4 ) "
							+ "SELECT * FROM A ORDER BY quarter_no");
			
			ResultSet quarter_amount = st33
					.executeQuery("select (select isnull(SUM(net_amt),0)as b from YTDTRAN where TRNDT between '"
							+ year.substring(7, 11)
							+ "-04-01' and '"
							+ year.substring(7, 11)
							+ "-06-30' and TRNCD=228 and empno="
							+ peno
							+ " )  as Q1, "
							+ " (select isnull(SUM(net_amt),0)as c from YTDTRAN where TRNDT between '"
							+ year.substring(7, 11)
							+ "-07-01' and '"
							+ year.substring(7, 11)
							+ "-09-30' and TRNCD=228 and empno="
							+ peno
							+ ") as Q2, "
							+ " (select isnull(SUM(net_amt),0)as d from YTDTRAN where TRNDT between '"
							+ year.substring(7, 11)
							+ "-10-01' and '"
							+ year.substring(7, 11)
							+ "-12-31' and TRNCD=228 and empno="
							+ peno
							+ ") as Q3, "
							+ " (select isnull(SUM(net_amt),0)as e from YTDTRAN where TRNDT between '"
							+ String.valueOf(Integer.parseInt(year.substring(7,
									11)) + 1)
							+ "-01-01' and '"
							+ String.valueOf(Integer.parseInt(year.substring(7,
									11)) + 1)
							+ "-03-31' and TRNCD=228 and empno="
							+ peno
							+ ") as Q4,"
							+ " (select isnull(SUM(net_amt),0)as f from YTDTRAN where TRNDT between '"
							+ year.substring(7, 11)
							+ "-04-01' and '"
							+ String.valueOf(Integer.parseInt(year.substring(7,
									11)) + 1)
							+ "-03-31' and TRNCD=228 and empno="
							+ peno
							+ ") as total   ");

			quarter_amount.next();
			while (quarter.next()) {
				// this is no of Quarter value.later it bind dynamically
				tab1 = new PdfPTable(5);
				// tab1.setSpacingBefore(2);
				tab1.setWidthPercentage(new float[] { 25, 25, 15, 15, 20 },
						new Rectangle(100, 100));
				
				
/*				String QuarterNo=quarter.getString(2);
				
				if(QuarterNo.equalsIgnoreCase("2")){
					subcell = new PdfPCell(new Phrase("Q" + 1, F10Bold));
				}
				else if(QuarterNo.equalsIgnoreCase("3")){
					subcell = new PdfPCell(new Phrase("Q" + 2, F10Bold));
				}
				else if(QuarterNo.equalsIgnoreCase("4")){
					subcell = new PdfPCell(new Phrase("Q" + 3, F10Bold));
				}
				else{
					subcell = new PdfPCell(new Phrase("Q" + 4, F10Bold));
				}
					
*/				subcell = new PdfPCell(new Phrase("Q" + quarter.getString(2), F10Bold));
				
				
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tab1.addCell(subcell);

				subcell = new PdfPCell(
						new Phrase(""
								+ ((quarter.getString(3) == null || quarter
										.getString(3) == "") ? "--" : quarter
										.getString(3)), F10Bold));
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tab1.addCell(subcell);

				total1 = quarter_amount.getString(5);// this is total amount;
				subcell = new PdfPCell(new Phrase(""
						+ quarter_amount.getString(quarter.getInt(2)), F10Bold));
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tab1.addCell(subcell);

				subcell = new PdfPCell(new Phrase(""
						+ quarter_amount.getString(quarter.getInt(2)), F10Bold));
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tab1.addCell(subcell);

				subcell = new PdfPCell(new Phrase(""
						+ quarter_amount.getString(quarter.getInt(2)), F10Bold));
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tab1.addCell(subcell);

				taxRepBean.getDoc().add(tab1);
			}
			// this is Total of Quarter value.
			tab1 = new PdfPTable(5);
			// tab1.setSpacingBefore(2);
			tab1.setWidthPercentage(new float[] { 25, 25, 15, 15, 20 },
					new Rectangle(100, 100));

			subcell = new PdfPCell(new Phrase("Total (Rs)", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase("--", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase("" + total1, F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase("" + total1, F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase("" + total1, F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);

			taxRepBean.getDoc().add(tab1);

			tab1 = new PdfPTable(5);
			// tab1.setSpacingBefore(2);
			tab1.setWidthPercentage(new float[] { 25, 25, 15, 15, 20 },
					new Rectangle(100, 100));

			subcell = new PdfPCell(new Phrase(
					"I. DETAILS OF TAX DEDUCTED AND DEPOSITED IN THE CENTRAL",
					F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(5);
			// subcell.setBorderWidthTop(2);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase(
					"GOVERNMENT ACCOUNT THROUGH BOOK ADJUSTMENT", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			// subcell.setBorderWidthTop(2);
			subcell.setColspan(5);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase(
					"(The deductor to provide payment wise details of tax",
					F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			// subcell.setBorderWidthTop(2);
			subcell.setColspan(5);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase(
					"deducted and deposited with respect to the deductee)",
					F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			// subcell.setBorderWidthTop(2);
			subcell.setColspan(5);
			subcell.setBorderWidthTop(0);
			// subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			taxRepBean.getDoc().add(tab1);

			PdfPTable tabb = new PdfPTable(6);
			// tabb.setSpacingBefore(2);
			tabb.setWidthPercentage(new float[] { 20, 20, 15, 15, 15, 15 },
					new Rectangle(100, 100));
			subcell = new PdfPCell(new Phrase("Sl. No.", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(1);
			subcell.setBorderWidthTop(2);
			subcell.setBorderWidthBottom(0);
			tabb.addCell(subcell);

			subcell = new PdfPCell(new Phrase(
					"Tax Deposited in respect of the deductee (Rs. )", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(1);
			subcell.setBorderWidthTop(2);
			subcell.setBorderWidthBottom(0);
			tabb.addCell(subcell);

			subcell = new PdfPCell(new Phrase(
					"Book Identification Number (BIN)", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(4);
			subcell.setBorderWidthTop(2);
			tabb.addCell(subcell);

			subcell = new PdfPCell(new Phrase("", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(1);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthRight(1);
			// subcell.setBorderWidthBottom(0);
			tabb.addCell(subcell);

			subcell = new PdfPCell(new Phrase("", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(1);
			subcell.setBorderWidthLeft(0);
			subcell.setBorderWidthTop(0);
			// subcell.setBorderWidthBottom(0);
			tabb.addCell(subcell);

			subcell = new PdfPCell(new Phrase(
					"Receipt numbers of Form No. 24G", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(1);
			subcell.setBorderWidthTop(0);
			// subcell.setBorderWidthBottom(0);
			tabb.addCell(subcell);

			subcell = new PdfPCell(new Phrase(
					"DDO serial number in Form No. 24G", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(1);
			subcell.setBorderWidthTop(0);
			// subcell.setBorderWidthBottom(0);
			tabb.addCell(subcell);

			subcell = new PdfPCell(new Phrase(
					"Date of transfer voucher dd/mm/yyyy", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(1);
			subcell.setBorderWidthTop(0);
			// subcell.setBorderWidthBottom(0);
			tabb.addCell(subcell);

			subcell = new PdfPCell(new Phrase(
					"Status of matching with Form No. 24G", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(1);
			subcell.setBorderWidthTop(0);
			// subcell.setBorderWidthBottom(0);
			tabb.addCell(subcell);

			subcell = new PdfPCell(new Phrase("", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(1);
			// subcell.setBorderWidthTop(0);
			// subcell.setBorderWidthRight(0);
			// subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tabb.addCell(subcell);

			subcell = new PdfPCell(new Phrase("", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(1);
			// subcell.setBorderWidthLeft(0);
			// subcell.setBorderWidthTop(0);
			// subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tabb.addCell(subcell);

			subcell = new PdfPCell(new Phrase("", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(1);
			// subcell.setBorderWidthTop(0);
			// subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tabb.addCell(subcell);

			subcell = new PdfPCell(new Phrase("", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(1);
			// subcell.setBorderWidthTop(0);
			// subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tabb.addCell(subcell);

			subcell = new PdfPCell(new Phrase("", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(1);
			// subcell.setBorderWidthTop(0);
			// subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tabb.addCell(subcell);

			subcell = new PdfPCell(new Phrase("", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(1);
			// subcell.setBorderWidthTop(0);
			// subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tabb.addCell(subcell);

			subcell = new PdfPCell(new Phrase("Total ( Rs. )", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(1);
			// subcell.setBorderWidthTop(0);
			// subcell.setBorderWidthRight(0);
			// subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tabb.addCell(subcell);

			subcell = new PdfPCell(new Phrase("", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(1);
			// subcell.setBorderWidthLeft(0);
			// subcell.setBorderWidthTop(0);
			// subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tabb.addCell(subcell);

			subcell = new PdfPCell(new Phrase("", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(4);
			// subcell.setBorderWidthTop(0);
			// subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tabb.addCell(subcell);

			taxRepBean.getDoc().add(tabb);

			tab1 = new PdfPTable(5);
			// tab1.setSpacingBefore(2);
			tab1.setWidthPercentage(new float[] { 25, 25, 15, 15, 20 },
					new Rectangle(100, 100));

			subcell = new PdfPCell(
					new Phrase(
							"II. DETAILS OF TAX DEDUCTED AND DEPOSITED IN THE",
							F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(5);
			// subcell.setBorderWidthTop(2);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase(
					"CENTRAL GOVERNMENT ACCOUNT THROUGH CHALLAN", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			// subcell.setBorderWidthTop(2);
			subcell.setColspan(5);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			subcell = new PdfPCell(
					new Phrase(
							"(The deductor to provide payment wise details of tax deducted",
							F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			// subcell.setBorderWidthTop(2);
			subcell.setColspan(5);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase(
					"and deposited with respect to the deductee)", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			// subcell.setBorderWidthTop(2);
			subcell.setColspan(5);
			subcell.setBorderWidthTop(0);
			// subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			taxRepBean.getDoc().add(tab1);

			PdfPTable tabb1 = new PdfPTable(6);
			// tabb.setSpacingBefore(2);
			tabb1.setWidthPercentage(new float[] { 20, 20, 15, 15, 15, 15 },
					new Rectangle(100, 100));
			subcell = new PdfPCell(new Phrase("Sl. No.", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(1);
			subcell.setBorderWidthTop(2);
			subcell.setBorderWidthBottom(0);
			tabb1.addCell(subcell);

			subcell = new PdfPCell(new Phrase(
					"Tax Deposited in respect of the deductee (Rs. )", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(1);
			subcell.setBorderWidthTop(2);
			subcell.setBorderWidthBottom(0);
			tabb1.addCell(subcell);

			subcell = new PdfPCell(new Phrase(
					"Challan Identification Number (CIN)", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(4);
			subcell.setBorderWidthTop(2);
			tabb1.addCell(subcell);

			subcell = new PdfPCell(new Phrase("", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(1);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthRight(1);
			// subcell.setBorderWidthBottom(0);
			tabb1.addCell(subcell);

			subcell = new PdfPCell(new Phrase("", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(1);
			subcell.setBorderWidthLeft(0);
			subcell.setBorderWidthTop(0);
			// subcell.setBorderWidthBottom(0);
			tabb1.addCell(subcell);

			subcell = new PdfPCell(new Phrase("BSR Code of the Bank Branch",
					F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(1);
			subcell.setBorderWidthTop(0);
			// subcell.setBorderWidthBottom(0);
			tabb1.addCell(subcell);

			subcell = new PdfPCell(new Phrase(
					"Date on which tax deposited (dd/mm/yyyy)", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(1);
			subcell.setBorderWidthTop(0);
			// subcell.setBorderWidthBottom(0);
			tabb1.addCell(subcell);

			subcell = new PdfPCell(new Phrase("Challan Serial Number", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(1);
			subcell.setBorderWidthTop(0);
			// subcell.setBorderWidthBottom(0);
			tabb1.addCell(subcell);

			subcell = new PdfPCell(new Phrase("Status of matching with OLTAS",
					F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(1);
			subcell.setBorderWidthTop(0);
			// subcell.setBorderWidthBottom(0);
			tabb1.addCell(subcell);
					/*String Challan="select Y.TRNDT,c.challan_No,t.branch_code, isnull(Y.net_amt,0) as AMOUNT from YTDTRAN Y, "
							+ " Challan_Details c,TDS_Payment_Details t "
							+ " where Y.TRNDT between '"
							+ year.substring(7, 11)
							+ "-04-01' and '"
							+ String.valueOf(Integer.parseInt(year.substring(7,
									11)) + 1)
							+ "-03-31' and "
							+ " Y.TRNCD=228 and Y.EMPNO="
							+ peno
							+ " and Y.trndt=c.for_month and c.Financial_Year='"
							+ year.substring(7, 11)
							+ "' "
							+ " and t.Financial_Year='"
							+ year.substring(7, 11)
							+ "' "
							+ " group by Y.TRNDT,c.challan_No,Y.net_amt,t.branch_code ";*/
			
			
			String Challan="select Y.TRNDT,c.challan_No,C.BSR_CODE, isnull(Y.net_amt,0) as AMOUNT from YTDTRAN Y, "
					+ " Challan_Details c "
					+ " where Y.TRNDT between '"
					+ year.substring(7, 11)
					+ "-04-01' and '"
					+ String.valueOf(Integer.parseInt(year.substring(7,
							11)) + 1)
					+ "-03-31' and "
					+ " Y.TRNCD=228 and Y.EMPNO="
					+ peno
					+ " and Y.trndt=c.for_month and c.Financial_Year='"
					+ year.substring(7, 11)
					+ "' "
					+ " group by Y.TRNDT,c.challan_No,Y.net_amt,C.BSR_CODE ";
			
					System.out.println(" Challan  "+Challan);
			ResultSet challan_details = st44.executeQuery(Challan);
			while (challan_details.next()) {
				subcell = new PdfPCell(new Phrase("" + ++i, F10Bold));
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				subcell.setColspan(1);
				// subcell.setBorderWidthTop(0);
				// subcell.setBorderWidthRight(0);
				// subcell.setBorderWidthBottom(0);
				subcell.setMinimumHeight(15);
				tabb1.addCell(subcell);

				total2 += challan_details.getInt("AMOUNT");
				subcell = new PdfPCell(new Phrase(""
						+ challan_details.getString("AMOUNT"), F10Bold));
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				subcell.setColspan(1);
				// subcell.setBorderWidthLeft(0);
				// subcell.setBorderWidthTop(0);
				// subcell.setBorderWidthBottom(0);
				subcell.setMinimumHeight(15);
				tabb1.addCell(subcell);

				subcell = new PdfPCell(new Phrase(""
						/*+ challan_details.getString("branch_code"), F10Bold));*/
						+ challan_details.getString("bsr_code"), F10Bold));
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				subcell.setColspan(1);
				// subcell.setBorderWidthTop(0);
				// subcell.setBorderWidthBottom(0);
				subcell.setMinimumHeight(15);
				tabb1.addCell(subcell);

				subcell = new PdfPCell(new Phrase(""
						+ challan_details.getString("TRNDT"), F10Bold));
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				subcell.setColspan(1);
				// subcell.setBorderWidthTop(0);
				// subcell.setBorderWidthBottom(0);
				subcell.setMinimumHeight(15);
				tabb1.addCell(subcell);

				subcell = new PdfPCell(new Phrase(""
						+ challan_details.getString("challan_No"), F10Bold));
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				subcell.setColspan(1);
				// subcell.setBorderWidthTop(0);
				// subcell.setBorderWidthBottom(0);
				subcell.setMinimumHeight(15);
				tabb1.addCell(subcell);

				subcell = new PdfPCell(new Phrase(" ", F10Bold));
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				subcell.setColspan(1);
				// subcell.setBorderWidthTop(0);
				// subcell.setBorderWidthBottom(0);
				subcell.setMinimumHeight(15);
				tabb1.addCell(subcell);
			}
			subcell = new PdfPCell(new Phrase("Total ( Rs. )", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(1);
			// subcell.setBorderWidthTop(0);
			// subcell.setBorderWidthRight(0);
			// subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tabb1.addCell(subcell);

			BigDecimal bd = new BigDecimal(total2);
			// bd.setScale(2, BigDecimal.ROUND_HALF_UP); bd.setScale does not
			// change bd
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			// System.out.println(bd);

			subcell = new PdfPCell(new Phrase("" + bd, F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(1);
			// subcell.setBorderWidthLeft(0);
			// subcell.setBorderWidthTop(0);
			// subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tabb1.addCell(subcell);

			subcell = new PdfPCell(new Phrase("", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setColspan(4);
			// subcell.setBorderWidthTop(0);
			// subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tabb1.addCell(subcell);

			taxRepBean.getDoc().add(tabb1);

			/*
			 * PdfPTable tablast = new PdfPTable(4);
			 * tablast.setWidthPercentage(new float[]{25,25,25,25},new
			 * Rectangle(100,100)); subcell = new PdfPCell(new
			 * Phrase("Verification",F10Bold));
			 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * //subcell.setBorderWidthTop(0); //subcell.setMinimumHeight(15);
			 * subcell.setColspan(4); tablast.addCell(subcell);
			 * 
			 * rsTmp.beforeFirst(); rsTmp.next(); subcell = new PdfPCell(new
			 * Phrase
			 * ("I "+rsTmp.getString("DEDUCTY_FNAME")+" son/daughter of "+rsTmp
			 * .getString
			 * ("DEDUCTY_MNAME")+" "+rsTmp.getString("DEDUCTY_LNAME")+
			 * " working in " +
			 * " the capacity of "+rsTmp.getString("DEDUCTY_DESIG")+
			 * "  do hereby certify that a sum of Rs. "
			 * +bd+" [Rs.(in words) "+originalNumToLetter
			 * .getInWords(String.valueOf(total2))+ " ] "+
			 * "has been deducted and deposited to the credit of the Central Government. I further certify that "
			 * + "the information"+
			 * " given above is true, complete and correct and is based on the books of account, documents, TDS "
			 * + " statements, and other available records.",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setColspan(4); tablast.addCell(subcell);
			 * 
			 * subcell = new PdfPCell(new Phrase("Place: NASHIK ",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setColspan(4); tablast.addCell(subcell);
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("Date: "+ReportDAO.getSysDate(),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setColspan(2); tablast.addCell(subcell);
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("(Signature of person responsible for deduction of tax)"
			 * ,F10Normal)); subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setColspan(2); tablast.addCell(subcell);
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("Designation: "+rsTmp.getString(
			 * "DEDUCTY_DESIG"),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setColspan(2); tablast.addCell(subcell);
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("Full Name: "+rsTmp.getString("DEDUCTY_FNAME"
			 * )+" "+rsTmp.getString
			 * ("DEDUCTY_MNAME")+" "+rsTmp.getString("DEDUCTY_LNAME"
			 * ),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setColspan(2); tablast.addCell(subcell);
			 * 
			 * taxRepBean.getDoc().add(tablast);
			 */

			title = new Phrase("\nPART B (Annexure)", F10Bold);
			taxRepBean.getDoc().add(
					createParaWithPhrase(title, Element.ALIGN_CENTER, 5, 5));

			tab1 = new PdfPTable(1);
			// tab1.setSpacingBefore(2);
			tab1.setWidthPercentage(new float[] { 100 },
					new Rectangle(100, 100));

			subcell = new PdfPCell(
					new Phrase(
							"DETAILS OF SALARY PAID AND ANY OTHER INCOME AND TAX DEDUCTED",
							F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab1.setSpacingBefore(5);
			tab1.addCell(subcell);
			taxRepBean.getDoc().add(tab1);
			/*
			 * title= new
			 * Phrase("DETAILS OF SALARY PAID AND ANY OTHER INCOME AND TAX DEDUCTED"
			 * ,F10Bold);
			 * taxRepBean.getDoc().add(createParaWithPhrase(title,Element
			 * .ALIGN_CENTER,5,5));
			 */

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void buildsql(int peno, String prdt, String itgrpcd,
			TaxReportBean taxRepBean) {
		
		System.out.println("Check Prasad prdt :"+prdt);
		
		
		
		/*
		 * //Oracle Query taxRepBean.setYtdstr(
		 * " select regular.trncd,c.disc ,net_amt,net_amt1,c.plusminus,c.itgrp,c.itgrp2,C.CHKSLB  from "
		 * + " (SELECT Y.TRNCD," +
		 * " sum(decode(y.trncd,108, (case  when net_Amt > 800 then net_amt - 800 else net_amt end),net_amt)) net_amt "
		 * + " FROM YTDTRAN Y,CDMAST C " + " Where Y.empno = "+peno +
		 * " AND Y.TRNDT BETWEEN '" + ReportDAO.Boy(prdt)+ "'  AND '" +
		 * ReportDAO.EOM(prdt) +"' " +
		 * " AND Y.TRNCD = C.TRNCD AND ( C.ITGRP LIKE '" + itgrpcd +
		 * "' or C.ITGRP2 LIKE '" + itgrpcd + "' ) " +
		 * " GROUP BY y.TRNCD ) regular , " +
		 * " ( SELECT Y.TRNCD, sum(decode(y.trncd,108, (case  when net_Amt > 800 then net_amt - 800 else net_amt end),net_amt))* "
		 * + taxRepBean.getProjMonth() + " net_amt1 " +
		 * " FROM YTDTRAN Y,CDMAST C " + " Where Y.empno = " + peno +
		 * " AND Y.TRNDT BETWEEN '" +ReportDAO.BOM(prdt)+ "'  AND '"
		 * +ReportDAO.EOM(prdt) +"' "+
		 * " AND Y.TRNCD = C.TRNCD AND ( C.ITGRP LIKE '" + itgrpcd +
		 * "' or C.ITGRP2 LIKE '" + itgrpcd + "' ) " + " and projyn = 'Y' " +
		 * " GROUP BY y.TRNCD ) projection ,cdmast c " +
		 * " where projection.trncd(+) = regular.trncd and " +
		 * " C.trncd = regular.trncd  order by c.itgrp");
		 * System.out.println(taxRepBean.getYtdstr()); //Oracle Query End
		 */

		/****** SQL QUERY START *****************/
		/*
		 * taxRepBean.setYtdstr(
		 * " SELECT regular.trncd, c.disc, net_amt, net_amt1, c.plusminus, c.itgrp, c.itgrp2, C.CHKSLB FROM "
		 * + "(	SELECT Y.TRNCD, " +
		 * " SUM( CASE y.trncd WHEN 108 THEN (case when net_Amt  > 800 then net_amt - 800	 else net_amt end)ELSE net_amt END) * "
		 * + taxRepBean.getProjMonth() + " net_amt1" +
		 * " FROM  YTDTRAN Y, CDMAST C " + " WHERE Y.empno = "+peno +
		 * " AND Y.TRNDT BETWEEN '" +ReportDAO.BOM(prdt)+ "'  AND '"
		 * +ReportDAO.EOM(prdt) +"'" +
		 * " AND Y.TRNCD = C.TRNCD AND	(C.ITGRP  LIKE '" + itgrpcd +
		 * "' OR	C.ITGRP2  LIKE '" + itgrpcd + "')" +
		 * " AND projyn = 'Y' GROUP BY  y.TRNCD ) projection " +
		 * " RIGHT OUTER JOIN (SELECT  Y.TRNCD, SUM(CASE y.trncd WHEN 108 " +
		 * " THEN (case when net_Amt  > 800 then net_amt - 800	 else net_amt end) ELSE net_amt	END) net_amt "
		 * + " FROM YTDTRAN Y, CDMAST C WHERE Y.empno = "+peno
		 * +" AND Y.TRNDT  BETWEEN '" + ReportDAO.Boy(prdt)+ "'  AND '" +
		 * ReportDAO.EOM(prdt) +"' " +
		 * "  AND	Y.TRNCD  = C.TRNCD  AND	(C.ITGRP  LIKE '" + itgrpcd +
		 * "' OR	C.ITGRP2  LIKE '" + itgrpcd + "') GROUP BY " +
		 * " y.TRNCD ) regular  ON  projection.trncd  = regular.trncd ," +
		 * " cdmast c 	WHERE	 C.trncd  = regular.trncd	ORDER BY c.itgrp ");
		 */

		// for getting last month value to calculate the projected
		// values...apply max trndt from paytran_stage
		taxRepBean
				.setYtdstr(" SELECT regular.trncd, c.disc, net_amt, net_amt1, c.plusminus, c.itgrp, c.itgrp2, C.CHKSLB FROM "
						+ "(	SELECT Y.TRNCD, "
						+ " SUM( CASE y.trncd WHEN 108 THEN (case when net_Amt  > 3000 then net_amt - 3000	 else net_amt end)ELSE inp_amt END) * "
						+ taxRepBean.getProjMonth()
						+ " net_amt1"
						+ " FROM  PAYTRAN_STAGE Y, CDMAST C "
						+ " WHERE Y.empno = "
						+ peno
						+ " AND Y.TRNDT =(SELECT MAX(TRNDT) FROM PAYTRAN_STAGE WHERE EMPNO ="
						+ peno
						+ " and TRNCD=101) "
						+ " AND Y.TRNCD = C.TRNCD AND	(C.ITGRP  LIKE '"
						+ itgrpcd 
						//+ "' OR	C.ITGRP2  LIKE '"
						+ "' AND C.ITGRP<>'A1I' OR	C.ITGRP2  LIKE '"
						+ itgrpcd
						+ "')"
						+ " AND projyn = 'Y' GROUP BY  y.TRNCD ) projection "
						+ " RIGHT OUTER JOIN (SELECT  Y.TRNCD, SUM(CASE y.trncd WHEN 108 "
						+ " THEN (case when net_Amt  > 3000 then net_amt - 3000	 else net_amt end) ELSE net_amt	END) net_amt "
						+ " FROM YTDTRAN Y, CDMAST C WHERE Y.empno = "
						+ peno
						+ " AND Y.TRNDT  BETWEEN '"
						+ ReportDAO.BoFinancialy(prdt)
						+ "'  AND '"
						+ ReportDAO.EoFinancialy(prdt)
						+ "' "
						+ "  AND	Y.TRNCD  = C.TRNCD  AND	(C.ITGRP  LIKE '"
						+ itgrpcd
						//+ "' OR	C.ITGRP2  LIKE '"
						+ "' AND C.ITGRP<>'A1I' OR	C.ITGRP2  LIKE '"
						+ itgrpcd
						+ "') GROUP BY "
						+ " y.TRNCD ) regular  ON  projection.trncd  = regular.trncd ,"
						+ " cdmast c 	WHERE	 C.trncd  = regular.trncd	ORDER BY c.itgrp ");
		System.out.println(" SDSSDFFFFFFFFDD SELECT regular.trncd, c.disc, net_amt, net_amt1, c.plusminus, c.itgrp, c.itgrp2, C.CHKSLB FROM "
				+ "(	SELECT Y.TRNCD, "
				+ " SUM( CASE y.trncd WHEN 108 THEN (case when net_Amt  > 3000 then net_amt - 3000	 else net_amt end)ELSE inp_amt END) * "
				+ taxRepBean.getProjMonth()
				+ " net_amt1"
				+ " FROM  PAYTRAN_STAGE Y, CDMAST C "
				+ " WHERE Y.empno = "
				+ peno
				+ " AND Y.TRNDT =(SELECT MAX(TRNDT) FROM PAYTRAN_STAGE WHERE EMPNO ="
				+ peno
				+ " and TRNCD=101) "
				+ " AND Y.TRNCD = C.TRNCD AND	(C.ITGRP  LIKE '"
				+ itgrpcd 
				//+ "' OR	C.ITGRP2  LIKE '"
				+ "' AND C.ITGRP<>'A1I' OR	C.ITGRP2  LIKE '"
				+ itgrpcd
				+ "')"
				+ " AND projyn = 'Y' GROUP BY  y.TRNCD ) projection "
				+ " RIGHT OUTER JOIN (SELECT  Y.TRNCD, SUM(CASE y.trncd WHEN 108 "
				+ " THEN (case when net_Amt  > 3000 then net_amt - 3000	 else net_amt end) ELSE net_amt	END) net_amt "
				+ " FROM YTDTRAN Y, CDMAST C WHERE Y.empno = "
				+ peno
				+ " AND Y.TRNDT  BETWEEN '"
				+ ReportDAO.BoFinancialy(prdt)
				+ "'  AND '"
				+ ReportDAO.EoFinancialy(prdt)
				+ "' "
				+ "  AND	Y.TRNCD  = C.TRNCD  AND	(C.ITGRP  LIKE '"
				+ itgrpcd
				//+ "' OR	C.ITGRP2  LIKE '"
				+ "' AND C.ITGRP<>'A1I' OR	C.ITGRP2  LIKE '"
				+ itgrpcd
				+ "') GROUP BY "
				+ " y.TRNCD ) regular  ON  projection.trncd  = regular.trncd ,"
				+ " cdmast c 	WHERE	 C.trncd  = regular.trncd	ORDER BY c.itgrp ");
		//System.out.println("form 16...ITGRP--->" + itgrpcd + "...."+ taxRepBean.getYtdstr());
		/******************** SQL QUERY END *********************/
		/*
		 * System.out.println("aniket...4..."+
		 * " SELECT regular.trncd, c.disc, net_amt, net_amt1, c.plusminus, c.itgrp, c.itgrp2, C.CHKSLB FROM "
		 * + "(SELECT Y.TRNCD, " +
		 * " SUM( CASE y.trncd WHEN 108 THEN (case when net_Amt  > 1600 then net_amt - 1600	 else net_amt end)ELSE net_amt END) * "
		 * + taxRepBean.getProjMonth() + " net_amt1" +
		 * " FROM  PAYTRAN_STAGE Y, CDMAST C " + " WHERE Y.empno = "+peno +
		 * " AND Y.TRNDT =(SELECT MAX(TRNDT) FROM PAYTRAN_STAGE WHERE EMPNO ="
		 * +peno+") " + " AND Y.TRNCD = C.TRNCD AND	(C.ITGRP  LIKE '" + itgrpcd
		 * + "' OR	C.ITGRP2  LIKE '" + itgrpcd + "')" +
		 * " AND projyn = 'Y' GROUP BY  y.TRNCD ) projection " +
		 * " RIGHT OUTER JOIN (SELECT  Y.TRNCD, SUM(CASE y.trncd WHEN 108 " +
		 * " THEN (case when net_Amt  > 1600 then net_amt - 1600	 else net_amt end) ELSE net_amt	END) net_amt "
		 * + " FROM YTDTRAN Y, CDMAST C WHERE Y.empno = "+peno
		 * +" AND Y.TRNDT  BETWEEN '" + ReportDAO.Boy(prdt)+ "'  AND '" +
		 * ReportDAO.EOM(prdt) +"' " +
		 * "  AND	Y.TRNCD  = C.TRNCD  AND	(C.ITGRP  LIKE '" + itgrpcd +
		 * "' OR	C.ITGRP2  LIKE '" + itgrpcd + "') GROUP BY " +
		 * " y.TRNCD ) regular  ON  projection.trncd  = regular.trncd ," +
		 * " cdmast c 	WHERE	 C.trncd  = regular.trncd	ORDER BY c.itgrp ");
		 */
	}

	public static void printgrp(int ppeno, String pprdt, RepoartBean repBean,
			TaxReportBean taxRepBean) {

		String pitgrp = "";
		String pitgrp2 = "";
		ResultSet slb = null;
		ResultSet ytd = null;
		String slbstr = "";
		int reftot = 0;
		int ActlAmt = 0;
		int ProjAmt = 0;
		try {
			Statement st = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ytd = st.executeQuery(taxRepBean.getYtdstr());
			PdfPCell subcell = null;
			PdfPTable tab1 = new PdfPTable(5);
			tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
					new Rectangle(100, 100));

			while (ytd.next()) {
				pitgrp = ytd.getString("ITGRP") != null ? ytd
						.getString("ITGRP") : "";
				pitgrp2 = ytd.getString("ITGRP2") != null ? ytd
						.getString("ITGRP2") : "";
				taxRepBean.setTotamt(0);
				while (ytd.getString("ITGRP").equals(pitgrp)) {
					subcell = new PdfPCell(new Phrase("" + ytd.getInt("TRNCD"),
							F10Normal));
					subcell.setBorderWidthLeft(0);
					subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tab1.addCell(subcell);
					if (ytd.getString("PLUSMINUS").equalsIgnoreCase("M")) {
						subcell = new PdfPCell(new Phrase("Less "
								+ ytd.getString("DISC"), F10Normal));
						subcell.setBorderWidthRight(0);
						subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tab1.addCell(subcell);
					} else {
						subcell = new PdfPCell(new Phrase(
								ytd.getString("DISC"), F10Normal));
						subcell.setBorderWidthRight(0);
						subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tab1.addCell(subcell);
					}
					if (ytd.getInt("TRNCD") == 202) {
						if ((ytd.getString("NET_AMT") != null ? ytd
								.getInt("NET_AMT") : 0)
								+ (ytd.getString("NET_AMT1") != null ? ytd
										.getInt("NET_AMT1") : 0) == 2400) {
							ActlAmt = (ytd.getString("NET_AMT") != null ? ytd
									.getInt("NET_AMT") : 0);
							ProjAmt = (ytd.getString("NET_AMT1") != null ? ytd
									.getInt("NET_AMT1") : 0) + 100;
							subcell = new PdfPCell(new Phrase("" + ActlAmt,
									F10Normal));
							subcell.setBorderWidthRight(0);
							subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
							tab1.addCell(subcell);
							subcell = new PdfPCell(new Phrase("" + ProjAmt,
									F10Normal));
							subcell.setBorderWidthRight(0);
							subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
							tab1.addCell(subcell);
							taxRepBean.setTotamt(taxRepBean.getTotamt()
									+ ActlAmt + ProjAmt);
							if (ytd.getString("PLUSMINUS")
									.equalsIgnoreCase("M")) {
								taxRepBean.setC3tot(taxRepBean.getC3tot()
										- ActlAmt);
								taxRepBean.setC4tot(taxRepBean.getC4tot()
										- ProjAmt);
							} else {
								taxRepBean.setC3tot(taxRepBean.getC3tot()
										+ ActlAmt);
								taxRepBean.setC4tot(taxRepBean.getC4tot()
										+ ProjAmt);
							}

						} else {
							ActlAmt = (ytd.getString("NET_AMT") != null ? ytd
									.getInt("NET_AMT") : 0);
							ProjAmt = (ytd.getString("NET_AMT1") != null ? ((taxRepBean
									.getProjMonth() <= 1) ? ((ytd
									.getInt("NET_AMT1") - 100) < 0 ? 0 : (ytd
									.getInt("NET_AMT1") - 100)) : (taxRepBean
									.getProjMonth() == 2 ? (ytd
									.getInt("NET_AMT1") - 100) : ytd
									.getInt("NET_AMT1"))) : 0);// +
																// (taxRepBean.getProjMonth()<=1?0:100);
							ProjAmt = ((ActlAmt + ProjAmt) == 2400) ? ProjAmt + 100
									: ProjAmt;
							subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
									ytd.getString("NET_AMT") != null ? ActlAmt
											: 0, "99,99,999.99", "", false,
									false), F10Normal));
							subcell.setBorderWidthRight(0);
							subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
							tab1.addCell(subcell);
							subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
									ytd.getString("NET_AMT1") != null ? ProjAmt
											: 0, "99,99,999.99", "", false,
									false), F10Normal));
							subcell.setBorderWidthRight(0);
							subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
							tab1.addCell(subcell);

							taxRepBean
									.setTotamt(taxRepBean.getTotamt()
											+ (ytd.getString("NET_AMT") != null ? ActlAmt
													: 0)
											+ (ytd.getString("NET_AMT1") != null ? ProjAmt
													: 0));
							if (ytd.getString("PLUSMINUS")
									.equalsIgnoreCase("M")) {
								taxRepBean
										.setC3tot(taxRepBean.getC3tot()
												- (ytd.getString("NET_AMT") != null ? ActlAmt
														: 0));
								taxRepBean
										.setC4tot(taxRepBean.getC4tot()
												- (ytd.getString("NET_AMT1") != null ? ProjAmt
														: 0));
							} else {
								taxRepBean
										.setC3tot(taxRepBean.getC3tot()
												+ (ytd.getString("NET_AMT") != null ? ActlAmt
														: 0));
								taxRepBean
										.setC4tot(taxRepBean.getC4tot()
												+ (ytd.getString("NET_AMT1") != null ? ProjAmt
														: 0));
							}
						}
						Statement st1 = repBean.getCn().createStatement();
						st1.executeUpdate("UPDATE F16 SET PT = " + ActlAmt
								+ " WHERE EMPNO = " + ppeno);
						st1.close();
					} else {

						subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
								ytd.getString("NET_AMT") != null ? ytd
										.getInt("NET_AMT") : 0, "99,99,999.99",
								"", false, false), F10Normal));
						subcell.setBorderWidthRight(0);
						subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tab1.addCell(subcell);
						subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
								ytd.getString("NET_AMT1") != null ? ytd
										.getInt("NET_AMT1") : 0,
								"99,99,999.99", "", false, false), F10Normal));
						subcell.setBorderWidthRight(0);
						subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tab1.addCell(subcell);

						if (ytd.getString("PLUSMINUS").equalsIgnoreCase("M")) {
							taxRepBean.setC3tot(taxRepBean.getC3tot()
									- (ytd.getString("NET_AMT") != null ? ytd
											.getInt("NET_AMT") : 0));
							taxRepBean.setC4tot(taxRepBean.getC4tot()
									- (ytd.getString("NET_AMT1") != null ? ytd
											.getInt("NET_AMT1") : 0));
							taxRepBean.setTotamt(taxRepBean.getTotamt()
									- (ytd.getString("NET_AMT") != null ? ytd
											.getInt("NET_AMT") : 0)
									- (ytd.getString("NET_AMT1") != null ? ytd
											.getInt("NET_AMT1") : 0));
						} else {
							if (ytd.getInt("TRNCD") == 535) {// if (535)home
																// loan interest
																// >200000 then
																// deduct 200000
																// only..so
																// added..@ni
								taxRepBean
										.setC3tot(taxRepBean.getC3tot()
												+ (ytd.getString("NET_AMT") != null ? ytd
														.getInt("NET_AMT") > 200000 ? 200000
														: ytd.getInt("NET_AMT")
														: 0));
								taxRepBean
										.setC4tot(taxRepBean.getC4tot()
												+ (ytd.getString("NET_AMT1") != null ? ytd
														.getInt("NET_AMT1") > 200000 ? 200000
														: ytd.getInt("NET_AMT1")
														: 0));
								taxRepBean
										.setTotamt(taxRepBean.getTotamt()
												+ (ytd.getString("NET_AMT") != null ? ytd
														.getInt("NET_AMT") > 200000 ? 200000
														: ytd.getInt("NET_AMT")
														: 0)
												+ (ytd.getString("NET_AMT1") != null ? ytd
														.getInt("NET_AMT1") > 200000 ? 200000
														: ytd.getInt("NET_AMT1")
														: 0));

							} else {
								taxRepBean
										.setC3tot(taxRepBean.getC3tot()
												+ (ytd.getString("NET_AMT") != null ? ytd
														.getInt("NET_AMT") : 0));
								taxRepBean
										.setC4tot(taxRepBean.getC4tot()
												+ (ytd.getString("NET_AMT1") != null ? ytd
														.getInt("NET_AMT1") : 0));
								taxRepBean
										.setTotamt(taxRepBean.getTotamt()
												+ (ytd.getString("NET_AMT") != null ? ytd
														.getInt("NET_AMT") : 0)
												+ (ytd.getString("NET_AMT1") != null ? ytd
														.getInt("NET_AMT1") : 0));
							}

						}
					}

					if (ytd.getString("ITGRP").equalsIgnoreCase(pitgrp)) {
						ReportDAO.println("", taxRepBean.getC5(), 0, false,
								"BANK", repBean);
					}
					if (!ytd.next()) {
						break;
					}

				}

				ytd.previous();
				if (ytd.getInt("TRNCD") == 811) {
					taxRepBean.setTotamt(calhlint(taxRepBean.getTotamt(),
							ppeno, repBean, taxRepBean));
				} else {
					if ((ytd.getString("CHKSLB") == null ? "N" : ytd
							.getString("CHKSLB")).equalsIgnoreCase("Y")) {
						slbstr = "SELECT * FROM SLAB WHERE TRNCD = "
								+ ytd.getInt("TRNCD");
						Statement st1 = repBean.getCn().createStatement();
						slb = st1.executeQuery(slbstr);
						if (slb.next()) {
							reftot = taxRepBean.getTotamt();
							taxRepBean.setTotamt((int) Calculate.checkSlab(
									ytd.getInt("TRNCD"), pprdt, reftot, 1,
									ppeno, repBean.getCn()));
						}
						st1.close();
					}
				}
				taxRepBean.setC5tot(taxRepBean.getC5tot()
						+ taxRepBean.getTotamt());
				subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
						taxRepBean.getTotamt(), "99,99,999.99", "", false,
						false), F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tab1.addCell(subcell);

			}
			taxRepBean.getDoc().add(tab1);
			st.close();

			tab1 = new PdfPTable(5);
			tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
					new Rectangle(100, 100));
			if (taxRepBean.getGnm().equalsIgnoreCase("A1")) {
				subcell = new PdfPCell(new Phrase("", F10Normal));
				subcell.setBorderWidthLeft(0);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				subcell = new PdfPCell(new Phrase("     GROSS SALARY INCOME ",
						F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tab1.addCell(subcell);

			}
			subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
					taxRepBean.getC3tot(), "99,99,999.99", "", false, false),
					F10Normal));
			subcell.setBorderWidthRight(0);
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
					taxRepBean.getC4tot(), "99,99,999.99", "", false, false),
					F10Normal));
			subcell.setBorderWidthRight(0);
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
					taxRepBean.getC5tot(), "99,99,999.99", "", false, false),
					F10Normal));
			subcell.setBorderWidthRight(0);
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);

			taxRepBean.getDoc().add(tab1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void Incometax_Report(String list, String rdt,
			String f16orwsheet, String filepath) {

		Properties prop = new Properties();
		try {

			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();
			InputStream stream = classLoader
					.getResourceAsStream("constant.properties");
			prop.load(stream);
		} catch (Exception e) {

			System.out.println("Error in constant properties Manager " + e);
		}
		TaxReportBean taxRepBean = new TaxReportBean();
		RepoartBean repBean = new RepoartBean();
		FileWriter Fp;
		// SimpleDateFormat frmFormat = new SimpleDateFormat("MM/DD/yyyy");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

		// String owner = "";
		// String MNEXT = "";
		// String hdg_g = "";
		// String dash_13 = "";
		String empsql = "";
		// String CmDSQL = "";
		// ResultSet CDM = null;
		ResultSet DTR = null;
		ResultSet emp = null;

		String ename = "";
		String str1 = "";
		String PAN_NO = "";
		// String dtrsql = "";

		taxRepBean.setMaxhlint1(100000);// maximum housing loan interest
										// repayment loan taken before 01/4/1999
		taxRepBean.setMaxhlint2(150000);// maximum housing loan interest
										// repayment loan taken after 01/4/1999

		ReportDAO.inithead(repBean);

		repBean.setHdg1(String.valueOf((char) 14) + String.valueOf((char) 15)
				+ "INCOME TAX WORKSHEET AS ON " + rdt
				+ String.valueOf((char) 18));
		// 'min_pay = 50000
		// 'min_pay = 100000

		// 'min_pay = 150000 'tmp. change for printing of f16 of some emp.
		taxRepBean.setMin_pay(50000);
		taxRepBean
				.setHdg_11("                                ...Actual Amt     Projected Amt  ....Total Amt  ");
		taxRepBean
				.setHdg_12("                                    (Rs.)             (Rs.)           (Rs.)     ");
		taxRepBean
				.setHdg_13("                               ----------------- --------------- ---------------");
		taxRepBean.setDline(UtilityDAO.stringOfSize(80, '-'));
		taxRepBean.setC1(0);
		taxRepBean.setC2(4);
		taxRepBean.setC3(35);
		taxRepBean.setC4(51);
		taxRepBean.setC4(67);
		// MNEXT = "P";
		ReportDAO.inithead(repBean);
		repBean.setLnCount(0);
		repBean.setPageNo(1);
		repBean.setLineLen(80);
		repBean.setPageLen(65);
		repBean.setFoot_Fl(true);
		repBean.setBrnNo(9999);

		ReportDAO.OpenCon("", "", "", repBean);

		// aniket code here changes...
		/*
		 * try{ Statement dtst1 =
		 * repBean.getCn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE
		 * ,ResultSet.CONCUR_READ_ONLY); //this is for calculating the
		 * projected_number_of_month values...so no_of_month is cal then cal is
		 * done....@ni ResultSet dtchk=dtst1.executeQuery(
		 * "select max(TRNDT) TRNDT from ytdtran where empno="
		 * +emp.getInt("EMPNO")+" and trncd=101 "); String maxtrndt="";
		 * if(dtchk.next()) { maxtrndt=dtchk.getString("TRNDT");
		 * 
		 * } String mname=getmonthname(Integer.parseInt(maxtrndt.substring(5,
		 * 7))); String
		 * maxdt=maxtrndt.substring(8,10)+"-"+mname+"-"+maxtrndt.substring(0,4);
		 * //System.out.println("max dat....monnthno..."+maxtrndt.substring(5,
		 * 7)+"..its name...."+mname+"...this is date..."+maxdt);
		 * //System.out.println
		 * ("YTDTRAN maxtrndtmonthno....date is..."+maxtrndt+
		 * "...."+String.valueOf(ReportDAO.getMonth(maxdt)+1)); String
		 * userdate_month
		 * =(String.valueOf((ReportDAO.getMonth(maxdt)+1)==12?12:(ReportDAO
		 * .getMonth(maxdt)+1)));
		 * System.out.println("USERMONTH...."+userdate_month); rdt=maxdt; String
		 * u_date[]=rdt.split("-");
		 * 
		 * String rsteom=ReportDAO.EOM(rdt);
		 * //System.out.println("EOM(rdt)...."+rsteom); //String
		 * u_EOM=ReportDAO.EOM(usrdate); String lastdate[]=rsteom.split("-");
		 * userdate_month
		 * =Integer.parseInt(userdate_month)>9?userdate_month:"0"+userdate_month
		 * ; String usrdate=u_date[2]+"-"+userdate_month+"-"+lastdate[0]; String
		 * cyear[]=maxtrndt.split("-");
		 * //System.out.println("EOM(USERDATE)...."+usrdate);
		 * 
		 * 
		 * if(!f16orwsheet.equalsIgnoreCase("F16")) {
		 * 
		 * if(usrdate.equalsIgnoreCase(maxtrndt)) {
		 * 
		 * //System.out.println((" Taxsheet date are equls ..."));
		 * 
		 * 
		 * if(Integer.parseInt(u_date[2])>Integer.parseInt(cyear[2])) {
		 * //System.out.println(" year greater ...");
		 * taxRepBean.setProjMonth(((12 - Integer.parseInt(cyear[1]))+3)); }
		 * else { //System.out.println(" current year  ...");
		 * taxRepBean.setProjMonth(((12 - Integer.parseInt(userdate_month))+3));
		 * } } else {
		 * //System.out.println((" Taxsheet date are  not equls ...")); String
		 * setdate[]=maxtrndt.split("-"); String
		 * monthname=getmonthname(Integer.parseInt(setdate[1]));
		 * //rdt="01-"+monthname+"-"+setdate[0]; //taxRepBean.setProjMonth(((12
		 * - Integer.parseInt(userdate_month))+3));
		 * if(Integer.parseInt(u_date[2])>=Integer.parseInt(cyear[0])) {
		 * //System.out.println(" year greater ...");
		 * taxRepBean.setProjMonth(((12 - Integer.parseInt(cyear[1]))+3)); }
		 * else { //System.out.println(" current year  ...");
		 * taxRepBean.setProjMonth(((12 - Integer.parseInt(userdate_month))+3));
		 * } }
		 * 
		 * }
		 * 
		 * else { //System.out.println(("date are  not equls ...")); //
		 * rdt=maxtrndt;
		 * //System.out.println(("date are  not equls so date become ..."+rdt));
		 * /
		 * /taxRepBean.setProjMonth((ReportDAO.getMonth(ReportDAO.BoFinancialy(
		 * rdt ))-1) - Math.abs(ReportDAO.getMonth(rdt)));
		 * 
		 * 
		 * if(usrdate.equalsIgnoreCase(maxtrndt)) {
		 * 
		 * //System.out.println((" Taxsheet date are equls ..."));
		 * 
		 * 
		 * if(Integer.parseInt(u_date[2])>Integer.parseInt(cyear[2])) {
		 * //System.out.println(" year greater ...");
		 * taxRepBean.setProjMonth(((12 - Integer.parseInt(cyear[1]))+3)); }
		 * else { //System.out.println(" current year  ...");
		 * taxRepBean.setProjMonth(((12 - Integer.parseInt(userdate_month))+3));
		 * } } else {
		 * //System.out.println((" Taxsheet date are  not equls ...")); String
		 * setdate[]=maxtrndt.split("-"); String
		 * monthname=getmonthname(Integer.parseInt(setdate[1]));
		 * //rdt="01-"+monthname+"-"+setdate[0]; //taxRepBean.setProjMonth(((12
		 * - Integer.parseInt(userdate_month))+3));
		 * if((Integer.parseInt(u_date[2])<=Integer.parseInt(cyear[0])) &&
		 * (Integer.parseInt(userdate_month)<=3)) {
		 * //System.out.println(" year greater ...");
		 * //taxRepBean.setProjMonth(((12 - Integer.parseInt(cyear[1]))+3));
		 * taxRepBean.setProjMonth((3-(Integer.parseInt(userdate_month)))); }
		 * else { //System.out.println(" current year  ...");
		 * //taxRepBean.setProjMonth(((12 -
		 * Integer.parseInt(userdate_month))+3)); taxRepBean.setProjMonth(((12-
		 * Integer.parseInt(userdate_month)+3))); } } } } catch (Exception e) {
		 * // TODO Auto-generated catch block e.printStackTrace(); }
		 */
		/*
		 * if(ReportDAO.getMonth(rdt) > 3){ taxRepBean.setProjMonth((12 -
		 * (ReportDAO.getMonth(rdt)-3))-1); System.out.println("hi out here "+
		 * (12 - (ReportDAO.getMonth(rdt)-3))+"  "+taxRepBean.getProjMonth());
		 * }else{
		 * taxRepBean.setProjMonth((ReportDAO.getMonth(ReportDAO.BoFinancialy
		 * (rdt))-1) - Math.abs(ReportDAO.getMonth(rdt))); }
		 */
		/* if(feno != teno){ */
		empsql = "select emp.*, t.acno, t.branch, t.grade ,emp.panno from empmast emp, emptran t where t.empno = emp.empno  AND "
				+ "  t.SRNO =(select max (e1.SRNO) from emptran e1 where e1.empno = emp.empno) "
				+ " and  emp.empno in(  " + list + ") order by emp.empno ";
		/*
		 * }else{ empsql =
		 * "select emp.*, t.acno, t.branch, t.grade ,emp.panno from empmast emp, emptran t where t.empno = emp.empno  AND "
		 * +
		 * " t.effdate = ( SELECT MAX(E2.EFFDATE) FROM EMPTRAN E2 WHERE e2.empno = emp.empno and E2.EFFDATE <= '"
		 * +rdt+ "')" + " and emp.empno between  " + feno + " and " + teno +
		 * " order by emp.empno "; }
		 */

		try {
			Statement st = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			emp = st.executeQuery(empsql);

			//System.out.println("aniket....1..." + empsql);
			if (!emp.next()) {
				System.out.println("Employee RecordSet is empty");
				return;
			}

			if (!f16orwsheet.equalsIgnoreCase("F16")) {
				Statement st1 = repBean.getCn().createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				st1.executeUpdate("DELETE FROM F16");
				// ReportDAO.make_prn_file(filepath,repBean);
				ReportDAO.page_head(repBean.getBrnNo(), repBean.getHdg1(), "",
						"", "", "", "", "", repBean);
			} else {
				// ReportDAO.make_prn_file(filepath,repBean);
			}

			// System.out.println(filepath);
			Document doc = new Document();
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(
					filepath));
			// writer.setEncryption(userPass.getBytes(), ownerPass.getBytes(),
			// PdfWriter.ALLOW_PRINTING,PdfWriter.ENCRYPTION_AES_128);
			doc.open();
			// doc.setMargins(10, 72, 108, 180);
			// doc.setPageSize(PageSize.A4);

			Font FONT = new Font(Font.HELVETICA, 52, Font.BOLD, new GrayColor(
					0.75f));
			ColumnText.showTextAligned(writer.getDirectContentUnder(),
					Element.ALIGN_CENTER,
					new Phrase(prop.getProperty("watermark"), FONT), 297.5f,
					421, 45);

			taxRepBean.setDoc(doc);

			emp.beforeFirst();

			while (emp.next()) {
				int empno = emp.getInt("empno");
				String status = emp.getString("Status");
				System.out.println("record found for emp");
				String dt = setprojectedmonth(taxRepBean, repBean, rdt, empno,
						f16orwsheet);
				if (status.equalsIgnoreCase("N")) {
					taxRepBean.setProjMonth(0);
				}
				
				//System.out.println("old rdt...." + rdt);
				
				if (!f16orwsheet.equalsIgnoreCase("F16")) {
					rdt = dt;
				}

				//System.out.println("new rdt..." + rdt);
				
				/*
				 * str1 = "select sum(net_amt) + sum(net_amt1) from " +
				 * " (SELECT Y.TRNCD,SUM(NET_AMT) net_amt  " +
				 * " FROM YTDTRAN Y, CDMAST C " + " Where Y.empno = " +
				 * emp.getInt("EmpNO") +" and " + " Y.TRNDT BETWEEN '"
				 * +ReportDAO.BoFinancialy(rdt)+ "'  AND  '"
				 * +ReportDAO.EOM(rdt)+ "' and " +
				 * " Y.TRNCD = C.TRNCD AND C.ITGRP LIKE 'A1%' " +
				 * " GROUP BY y.TRNCD) regular, " +
				 * "(SELECT Y.TRNCD,SUM(NET_AMT) * " + taxRepBean.getProjMonth()
				 * + " net_amt1 " + " FROM YTDTRAN Y,CDMAST C " +
				 * " Where Y.empno = " + emp.getInt("EmpNO") + " and " +
				 * " Y.TRNDT BETWEEN '" +ReportDAO.BOM(rdt)+ "'  AND  '"
				 * +ReportDAO.EOM(rdt)+ "' and " +
				 * " Y.TRNCD = C.TRNCD AND C.ITGRP LIKE 'A1%' and " +
				 * " c.projyn = 'Y' " + " GROUP BY y.TRNCD ) projection " +
				 * " where projection.trncd(+) = regular.trncd ";
				 */
				/*
				 * str1 =
				 * "SELECT SUM(CONVERT(FLOAT, net_amt)) + SUM(CONVERT(FLOAT, net_amt1))FROM (SELECT Y.TRNCD, "
				 * + " SUM(CONVERT(FLOAT, CONVERT(FLOAT, NET_AMT))) * " +
				 * taxRepBean.getProjMonth() +
				 * " net_amt1 FROM  YTDTRAN Y, CDMAST C WHERE Y.empno = " +
				 * emp.getInt("EmpNO") + "" + " AND	Y.TRNDT  BETWEEN '"
				 * +ReportDAO.BoFinancialy(rdt)+ "'  AND  '"
				 * +ReportDAO.EoFinancialy(rdt)+
				 * "' AND Y.TRNCD  = C.TRNCD AND C.ITGRP  LIKE 'A1%'" +
				 * " AND	c.projyn  = 'Y'	GROUP BY  y.TRNCD) projection  RIGHT OUTER JOIN (SELECT"
				 * +
				 * " Y.TRNCD, SUM(CONVERT(FLOAT, CONVERT(FLOAT, NET_AMT))) net_amt	FROM  YTDTRAN Y, CDMAST C"
				 * + " WHERE	 Y.empno  = " + emp.getInt("EmpNO")
				 * +" AND Y.TRNDT  BETWEEN '" +ReportDAO.BoFinancialy(rdt)+
				 * "'  AND  '" +ReportDAO.EoFinancialy(rdt)+
				 * "' AND Y.TRNCD  = C.TRNCD	" +
				 * " AND C.ITGRP  LIKE 'A1%' GROUP BY  y.TRNCD) regular  ON  projection.trncd  = regular.trncd"
				 * ;
				 */

				str1 = "select SUM(s.net_amt) as net_amt from"
						+ "(SELECT SUM(CONVERT(FLOAT, CONVERT(FLOAT, NET_AMT)))"
						+ "net_amt	FROM  YTDTRAN Y, CDMAST C WHERE Y.empno  =  "
						+ emp.getInt("EmpNO")
						+ " AND Y.TRNDT"
						+ " between '"
						+ ReportDAO.BoFinancialy(rdt)
						+ "'  AND  '"
						+ ReportDAO.EoFinancialy(rdt)
						+ "'  AND "
						+ " Y.TRNCD  = C.TRNCD	 AND C.ITGRP LIKE 'A1%' GROUP BY  y.TRNCD )s ";

				//System.out.println("str1" + rdt);
				//System.out.println("f16 taxsheet query   " + str1);

				Statement st1 = repBean.getCn().createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				DTR = st1.executeQuery(str1);
				//System.out.println("aniket....2..." + str1);
				if (!DTR.next()) {
					// MNEXT = "P";
				} else {
					// And (emp!EmpNo <> 2019 And rdt <> "31-mar-2010") is added
					// only for fy 2009-2010 to be remove afterwards
					// 'If DTR.Fields(0) < min_pay And (emp!EmpNo <> 2019 And
					// rdt <> "31-mar-2010") Then

					ename = emp.getString("LNAME") + " "
							+ emp.getString("FNAME") + " "
							+ emp.getString("MNAME");
					if (f16orwsheet.equalsIgnoreCase("yearlyearning")) {
						Statement st2 = repBean.getCn().createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);

						// st2.executeUpdate("INSERT INTO F16(EMPNO,NAME,PANNO,DOB,SEX) VALUES ("+emp.getInt("EMPNO")+",'"+ename+"','"+emp.getString("PANNO")+"','"+dateFormat.format(emp.getDate("DOB"))+"','"+emp.getString("GENDER")+"')");
						// System.out.println("aniket...3..."+"INSERT INTO F16(EMPNO,NAME,PANNO,DOB,SEX) VALUES ("+emp.getInt("EMPNO")+",'"+ename+"','"+emp.getString("PANNO")+"','"+dateFormat.format(emp.getDate("DOB"))+"','"+emp.getString("GENDER")+"')");
						YearlyEarning(emp.getString("empcode"),
								emp.getInt("empno"), rdt, ename,
								emp.getString("GENDER"), repBean, taxRepBean);
						// st2.close();

					} else if (f16orwsheet.equalsIgnoreCase("PrintTaxSheet")) {
						Statement st2 = repBean.getCn().createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);

						st2.executeUpdate("INSERT INTO F16(EMPNO,NAME,PANNO,DOB,SEX) VALUES ("
								+ emp.getInt("EMPNO")
								+ ",'"
								+ ename
								+ "','"
								+ emp.getString("PANNO")
								+ "','"
								+ dateFormat.format(emp.getDate("DOB"))
								+ "','"
								+ emp.getString("GENDER") + "')");
						System.out
								.println("aniket...3..."
										+ "INSERT INTO F16(EMPNO,NAME,PANNO,DOB,SEX) VALUES ("
										+ emp.getInt("EMPNO") + ",'" + ename
										+ "','" + emp.getString("PANNO")
										+ "','"
										+ dateFormat.format(emp.getDate("DOB"))
										+ "','" + emp.getString("GENDER")
										+ "')");
						PrintTaxSheet(emp.getString("empcode"),
								emp.getInt("empno"), rdt, ename,
								emp.getString("GENDER"), repBean, taxRepBean);
						st2.close();
					} else {
						System.out
								.println("now its form 16....................");
						// if(emp.getInt("DA_SCHEME") > 0 || emp.getInt("EMPNO")
						// == 2014 || emp.getInt("EMPNO") == 4039){
						repBean.setPageLen(68);
						taxRepBean.setGENO(emp.getInt("EMPNO"));
						PAN_NO = emp.getString("PANNO") == null ? "" : emp
								.getString("PANNO");
						printform16(emp.getInt("EMPNO"), rdt, ename,
								emp.getInt("GRADE"), PAN_NO,
								emp.getString("GENDER"), emp.getDate("DOB"),
								repBean, taxRepBean);

						// }
					}

				}System.out.println("YEALYEARNING EMPNO : "+emp.getInt("EMPNO"));
				checkSlabYearlyEarning(empno, dt, empno, empno, empno, null);
			}
			taxRepBean.getDoc().newPage();
			
			System.out.println("YEALYEARNING DONE");
			
			taxRepBean.getDoc().close();
			Fp = repBean.getFp();
			repBean.getCn().close();
			// Fp.close();
			
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
	
	
	/*******************************************************************************Slab Start*******************************************************************************************************/
	
	public static float checkSlabYearlyEarning(int trncd, String dt, int WrkAmt,
			int empType, int empno, Connection Cn1){
		
		System.out.println("Hello checkSlabYearlyEarning");
		ErrorLog EL =new ErrorLog();
		String v_gender="";
		float result = 0.00f;
		float slabAmt = 0.00f;
		int yearFeb=Integer.parseInt(dt.substring(7,11));
		String vempType = "" + empType;
		Connection Cn = ConnectionManager.getConnection();
		Statement sttc = null;
		ResultSet rstc = null;

		try {
			//String TransactionCode = "SELECT * FROM YEARLYEARNING WHERE TRNCD BETWEEN 101 AND 197 AND TRNDT='"+ReportDAO.EoFinancialy(dt)+"' AND EMPNO="+empno;
			//String TransactionCode = "SELECT * FROM YEARLYEARNING WHERE ((TRNCD BETWEEN 101 AND 197) OR (TRNCD IN (202, 228))) AND TRNDT='"+ReportDAO.EoFinancialy(dt)+"' AND EMPNO="+empno;
			String TransactionCode = "SELECT * FROM YEARLYEARNING WHERE ((TRNCD BETWEEN 101 AND 197) OR (TRNCD IN (228))) AND TRNDT='"+ReportDAO.EoFinancialy(dt)+"' AND EMPNO="+empno;
			System.out.println("TransactionCode : "+TransactionCode);

			sttc = Cn.createStatement();
			rstc = sttc.executeQuery(TransactionCode);
			String checkYearlyearningNew="";
			int i=0;
			while(rstc.next()){
				Statement sttc1 = Cn.createStatement();
				String TRANCODES = rstc.getString("TRNCD"); 
				String YN_ReleaseAmt = rstc.getString("RELEASE_AMT");
				String YN_PrjAmt = rstc.getString("PROJ_AMT");
				String YN_TotalAmt = rstc.getString("TOTAL_AMT");
				
				System.out.println("TRANCODES : "+TRANCODES);
				
				 checkYearlyearningNew = "IF EXISTS(SELECT * FROM YEARLYEARNING_NEW WHERE TRNDT='"+ReportDAO.EoFinancialy(dt)+"' AND EMPNO="+empno+" AND TRNCD="+TRANCODES+") "
						+ "UPDATE YEARLYEARNING_NEW SET RELEASE_AMT="+YN_ReleaseAmt+", PROJ_AMT="+YN_PrjAmt+", TOTAL_AMT="+YN_TotalAmt+" WHERE TRNCD="+TRANCODES+" AND EMPNO="+empno+" AND TRNDT='"+ReportDAO.EoFinancialy(dt)+"' "
						+ "ELSE INSERT INTO YEARLYEARNING_NEW SELECT * FROM YEARLYEARNING WHERE TRNCD="+TRANCODES+" AND EMPNO="+empno+" AND TRNDT='"+ReportDAO.EoFinancialy(dt)+"'";
			
				 sttc1.executeUpdate(checkYearlyearningNew);
				 i++;
			}

			Statement sttcrem = null;
			ResultSet rstcrem = null;

			//String TransactionCodeRem = "SELECT * FROM YEARLYEARNING WHERE ((TRNCD>=201) AND (TRNCD NOT IN (202, 228))) AND TRNDT='"+ReportDAO.EoFinancialy(dt)+"' AND EMPNO="+empno;
			String TransactionCodeRem = "SELECT * FROM YEARLYEARNING WHERE ((TRNCD>=201) AND (TRNCD NOT IN (228))) AND TRNDT='"+ReportDAO.EoFinancialy(dt)+"' AND EMPNO="+empno;
			
			sttcrem = Cn.createStatement();
			rstcrem = sttcrem.executeQuery(TransactionCodeRem);
			String checkYearlyearningRem="";
			int rem=0;
			while(rstcrem.next()){
				Statement sttcrem1 = Cn.createStatement();
				String TRANCODESrem = rstcrem.getString("TRNCD"); 
				String YN_ReleaseAmtrem = rstcrem.getString("RELEASE_AMT");
				String YN_PrjAmtrem = rstcrem.getString("PROJ_AMT");
				String YN_TotalAmtrem = rstcrem.getString("TOTAL_AMT");
				
				YN_ReleaseAmtrem="0";
				YN_PrjAmtrem="0";
				YN_TotalAmtrem="0";
				
				checkYearlyearningRem = "IF EXISTS(SELECT * FROM YEARLYEARNING_NEW WHERE TRNDT='"+ReportDAO.EoFinancialy(dt)+"' AND EMPNO="+empno+" AND TRNCD="+TRANCODESrem+") "
						+ "UPDATE YEARLYEARNING_NEW SET RELEASE_AMT="+YN_ReleaseAmtrem+", PROJ_AMT="+YN_PrjAmtrem+", TOTAL_AMT="+YN_TotalAmtrem+" WHERE TRNCD="+TRANCODESrem+" AND EMPNO="+empno+" AND TRNDT='"+ReportDAO.EoFinancialy(dt)+"' "
						+ "ELSE INSERT INTO YEARLYEARNING_NEW SELECT EMPNO, TRNCD, 0, 0, 0, TRNDT FROM YEARLYEARNING WHERE TRNCD="+TRANCODESrem+" AND EMPNO="+empno+" AND TRNDT='"+ReportDAO.EoFinancialy(dt)+"'";
			
				sttcrem1.executeUpdate(checkYearlyearningRem);
				 rem++;
			}

			
			String grosssTaxValue = "SELECT SUM(TOTAL_AMT) AS GROSSAMT FROM YEARLYEARNING_NEW WHERE EMPNO="+empno+" AND TRNDT='"+ReportDAO.EoFinancialy(dt)+"' "
					+ "AND TRNCD BETWEEN 101 AND 197";
			String checkGrossValue="";
			int GrossWrkAmt=0;
			Statement stgross = Cn.createStatement();
			ResultSet rsgross = stgross.executeQuery(grosssTaxValue);
			if(!rsgross.next()){
				
			}else{
				checkGrossValue=rsgross.getString("GROSSAMT");
				System.out.println("checkGrossValue : "+checkGrossValue);
				GrossWrkAmt= Integer.parseInt(checkGrossValue);	
			}
			
			Statement stgrossincome = Cn.createStatement();
			
			String GrossIncome = "IF EXISTS (SELECT * FROM YEARLYEARNING_NEW WHERE TRNCD=516 AND EMPNO="+empno+" AND TRNDT='"+ReportDAO.EoFinancialy(dt)+"') "
					+ "UPDATE YEARLYEARNING_NEW SET RELEASE_AMT="+GrossWrkAmt+", TOTAL_AMT="+GrossWrkAmt+" WHERE TRNCD=516 AND EMPNO="+empno+" AND TRNDT='"+ReportDAO.EoFinancialy(dt)+"' "
					+ "ELSE  INSERT INTO YEARLYEARNING_NEW VALUES ("+empno+", 516, "+GrossWrkAmt+", 0, "+GrossWrkAmt+", '"+ReportDAO.EoFinancialy(dt)+"')";
			
			stgrossincome.executeUpdate(GrossIncome);
		
		
	/*		String dedTaxValue = "SELECT SUM(TOTAL_AMT) AS DEDAMT FROM YEARLYEARNING_NEW WHERE EMPNO="+empno+" AND TRNDT='"+ReportDAO.EoFinancialy(dt)+"' "
					+ "AND TRNCD IN (202) ";
			String checkDedValue="";
			int DedWrkAmt=0;
			Statement stded = Cn.createStatement();
			ResultSet rsded = stded.executeQuery(dedTaxValue);
			if(!rsded.next()){
				
			}else{System.out.println();
				checkDedValue=rsded.getString("DEDAMT");
				System.out.println("checkDedValue : "+checkDedValue);
				DedWrkAmt= Integer.parseInt(checkDedValue);	
			}
			
			Statement stdedamt = Cn.createStatement();
			
			String DedAmt = "IF EXISTS (SELECT * FROM YEARLYEARNING_NEW WHERE TRNCD=517 AND EMPNO="+empno+" AND TRNDT='"+ReportDAO.EoFinancialy(dt)+"') "
					+ "UPDATE YEARLYEARNING_NEW SET RELEASE_AMT="+DedWrkAmt+", TOTAL_AMT="+DedWrkAmt+" WHERE TRNCD=517 AND EMPNO="+empno+" AND TRNDT='"+ReportDAO.EoFinancialy(dt)+"' "
					+ "ELSE  INSERT INTO YEARLYEARNING_NEW VALUES ("+empno+", 517, "+DedWrkAmt+", 0, "+DedWrkAmt+", '"+ReportDAO.EoFinancialy(dt)+"')";
			
			stdedamt.executeUpdate(DedAmt);
		
	*/		
			
			

	/*		String taxValue = "SELECT( (SELECT SUM(TOTAL_AMT) FROM YEARLYEARNING_NEW WHERE EMPNO="+empno+" "
					+ "AND TRNDT='"+ReportDAO.EoFinancialy(dt)+"' AND TRNCD=516)-(SELECT SUM(TOTAL_AMT) FROM YEARLYEARNING_NEW "
							+ "WHERE EMPNO="+empno+" AND TRNDT='"+ReportDAO.EoFinancialy(dt)+"' AND TRNCD=517)) AS TAXABLEAMT";
	*/		
			String taxValue = "SELECT SUM(TOTAL_AMT) AS TAXABLEAMT FROM YEARLYEARNING_NEW WHERE EMPNO="+empno+" "
					+ "AND TRNDT='"+ReportDAO.EoFinancialy(dt)+"' AND TRNCD=516 ";
	
			String checkTaxValue="";
			
			Statement sttaxable = Cn.createStatement();
			ResultSet rstaxable = sttaxable.executeQuery(taxValue);
			if(!rstaxable.next()){
				
			}else{
				checkTaxValue=rstaxable.getString("TAXABLEAMT");
				System.out.println("checkTaxValue : "+checkTaxValue);
				WrkAmt= Integer.parseInt(checkTaxValue);	
			}
			
			System.out.println("ROUNDED OFF WrkAmt : "+WrkAmt);
			
			int a = (WrkAmt / 10) * 10;
			int b = a + 10;
			WrkAmt= (WrkAmt - a > b - WrkAmt)? b : a;
			System.out.println("ROUNDED OFF AFTER WrkAmt : "+WrkAmt);
			
			Statement sttaxableincome = Cn.createStatement();
			
			String TaxableIncome = "IF EXISTS (SELECT * FROM YEARLYEARNING_NEW WHERE TRNCD=515 AND EMPNO="+empno+" AND TRNDT='"+ReportDAO.EoFinancialy(dt)+"') "
					+ "UPDATE YEARLYEARNING_NEW SET RELEASE_AMT="+WrkAmt+", TOTAL_AMT="+WrkAmt+" WHERE TRNCD=515 AND EMPNO="+empno+" AND TRNDT='"+ReportDAO.EoFinancialy(dt)+"' "
					+ "ELSE  INSERT INTO YEARLYEARNING_NEW VALUES ("+empno+", 515, "+WrkAmt+", 0, "+WrkAmt+", '"+ReportDAO.EoFinancialy(dt)+"')";
			
			sttaxableincome.executeUpdate(TaxableIncome);
			
				
		} 
		catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
/*		
		String workingamount = "SELECT SUM(TOTAL_AMT) AS AMT FROM YEARLYEARNING_NEW WHERE EMPNO=736 AND TRNDT='"+ReportDAO.EoFinancialy(dt)+"' AND TRNCD BETWEEN 101 AND 199";
		String checkvalue="";
		System.out.println("workingamount : "+workingamount);
		try {
			Statement stnew = Cn.createStatement();
			ResultSet rsnew = stnew.executeQuery(workingamount);
			
			if(!rsnew.next()){
				
			}else{
				checkvalue=rsnew.getString("AMT");
				System.out.println("checkvalue : "+checkvalue);
				WrkAmt= Integer.parseInt(checkvalue);	
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
*/
	//	String workingamount = "5000";
	//	WrkAmt=Integer.parseInt(workingamount);
		
		System.out.println("Hello WrkAmt : "+WrkAmt);


		String SlbStr = "SELECT * FROM SLAB WHERE EMP_CAT =0 AND TRNCD = 520 AND EFFDATE = ( SELECT MIN(S.EFFDATE) FROM SLAB S WHERE S.EMP_CAT =0 AND TRNCD = 520 and effdate = '2099-12-31' ) and "
				+   WrkAmt + "BETWEEN  FRMAMT AND TOAMT ORDER BY SRNO ";

		
		try {
			Statement st0 = Cn.createStatement();
			ResultSet rs = st0.executeQuery(SlbStr);
			if (!rs.next()) {System.out.println("SLAB IF");
				st0.close();

			} else {System.out.println("SLAB ELSE");
				vempType = "" + empno;

			}
		} catch (Exception e) {
			e.printStackTrace();
			EL.errorLog("Calculate.java -- checkSlab()", e.toString());
		}

		
/*		SlbStr = "SELECT * FROM SLAB WHERE EMP_CAT =0 AND TRNCD = 520 AND EFFDATE = ( SELECT MIN(S.EFFDATE) FROM SLAB S WHERE S.EMP_CAT = 0 AND TRNCD = 520 and effdate = '2099-12-31' ) and " 
				+ WrkAmt + " BETWEEN  FRMAMT AND TOAMT ORDER BY SRNO ";
*/
		if (WrkAmt == 0) {
			return 0;
		}
		
		
		System.out.println("Actually WrkAmt NEW NEW : "+WrkAmt);
		System.out.println("Actually WrkAmt : "+WrkAmt);

		try {
				System.out.println("2 TRY");
				Statement st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				//ResultSet Slb = st.executeQuery(SlbStr);
				
			//	Slb.beforeFirst();
			//	if (Slb.next()) 
				//{
			float WrkAmtSlab=0.0f;
			float WrkAmtRem=0.0f;
			float WrkAmtCalculate=0.0f;
			
				if(WrkAmt>=1500000) {
					WrkAmtCalculate=187500;
					WrkAmtRem=WrkAmt-1500000;
					WrkAmtSlab = (WrkAmtRem * 30)/100;
				}
				else if (WrkAmt>=1250000 && WrkAmt<1500000) {
					WrkAmtCalculate=125000;
					WrkAmtRem=WrkAmt-1250000;
					WrkAmtSlab = (WrkAmtRem * 25)/100;
				}
				else if (WrkAmt>=1000000 && WrkAmt<1250000) {
					WrkAmtCalculate=75000;
					WrkAmtRem=WrkAmt-1000000;
					WrkAmtSlab = (WrkAmtRem * 20)/100;
				}
				else if (WrkAmt>=750000 && WrkAmt<1000000) {
					WrkAmtCalculate=37500;
					WrkAmtRem=WrkAmt-750000;
					WrkAmtSlab = (WrkAmtRem * 15)/100;
				}
				else if (WrkAmt>=500000 && WrkAmt<750000) {
					WrkAmtCalculate=12500;
					WrkAmtRem=WrkAmt-500000;
					WrkAmtSlab = (WrkAmtRem * 10)/100;
				}
/*				else if (WrkAmt>=250000 && WrkAmt<500000) {
					WrkAmtCalculate=0;
					WrkAmtRem=WrkAmt-250000;
					WrkAmtSlab = (WrkAmtRem * 5)/100;
				}
*/				else {
					WrkAmtCalculate=0;
					WrkAmtSlab = 0;
				}
					
				System.out.println("WrkAmtCalculate LATEST : "+WrkAmtCalculate);
				System.out.println("WrkAmtSlab LATEST : "+WrkAmtSlab);
					
				slabAmt = WrkAmtCalculate+WrkAmtSlab;
				
				System.out.println("slabAmt LATEST : "+slabAmt);
				System.out.println("TaxWithCess LATEST : "+((slabAmt*4)/100));
			
				/*************************************ADD TAXABLE AMT AND CODE HERE*****************************************************************************/
				Statement sttaxnew = Cn.createStatement();
				
				String TaxaNew = "IF EXISTS (SELECT * FROM YEARLYEARNING_NEW WHERE TRNCD=520 AND EMPNO="+empno+" AND TRNDT='"+ReportDAO.EoFinancialy(dt)+"') "
						+ "UPDATE YEARLYEARNING_NEW SET RELEASE_AMT="+slabAmt+", TOTAL_AMT="+slabAmt+" WHERE TRNCD=520 AND EMPNO="+empno+" AND TRNDT='"+ReportDAO.EoFinancialy(dt)+"' "
						+ "ELSE  INSERT INTO YEARLYEARNING_NEW VALUES ("+empno+", 520, "+slabAmt+", 0, "+slabAmt+", '"+ReportDAO.EoFinancialy(dt)+"')";
				
				sttaxnew.executeUpdate(TaxaNew);
				
				/*************************************ADD TAXABLE AMT AND CODE HERE*****************************************************************************/
				
				
				/*************************************ADD TDS AMT AND CODE HERE*****************************************************************************/
	
				String tdsValueProj_Amt = "SELECT SUM(PROJ_AMT) AS TDSPROJ_AMT FROM YEARLYEARNING_NEW WHERE EMPNO="+empno+" "
						+ "AND TRNDT='"+ReportDAO.EoFinancialy(dt)+"' AND TRNCD=228 ";
		
				String tdsProj_AmtValue="";
				Float tdsProjAmt = 0.0f;
				Float TdsValue = 0.0f;
							
				Statement sttdsproamt = Cn.createStatement();
				ResultSet rstdsproamt = sttdsproamt.executeQuery(tdsValueProj_Amt);
				if(!rstdsproamt.next()){
					
				}else{
					tdsProj_AmtValue=rstdsproamt.getString("TDSPROJ_AMT");
					tdsProjAmt = Float.parseFloat(tdsProj_AmtValue);	
				}
		
				
				Statement sttaxtds = Cn.createStatement();
				TdsValue = slabAmt + ((slabAmt*4)/100);
				
				Float tdsTotal_Amt = TdsValue - tdsProjAmt;
				
				String TaxaTds = "IF EXISTS (SELECT * FROM YEARLYEARNING_NEW WHERE TRNCD=228 AND EMPNO="+empno+" AND TRNDT='"+ReportDAO.EoFinancialy(dt)+"') "
						+ "UPDATE YEARLYEARNING_NEW SET RELEASE_AMT="+TdsValue+", TOTAL_AMT="+tdsTotal_Amt+" WHERE TRNCD=228 AND EMPNO="+empno+" AND TRNDT='"+ReportDAO.EoFinancialy(dt)+"' "
						+ "ELSE  INSERT INTO YEARLYEARNING_NEW VALUES ("+empno+", 228, "+TdsValue+", "+tdsProjAmt+", "+tdsTotal_Amt+", '"+ReportDAO.EoFinancialy(dt)+"')";
				
				sttaxtds.executeUpdate(TaxaTds);
				
				/*************************************ADD TDS AMT AND CODE HERE*****************************************************************************/
				
				
				
				result = slabAmt;	
				System.out.println("checkSlabYearlyEarning result : "+ result);
			//}
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
			EL.errorLog("Calculate.java -- chkSlab()", e.toString());
		}
		
		return result;
		
	}
	
	/*******************************************************************************Slab End********************************************************************************************************/
	
	
	public static void buildsqlF16(int peno, String prdt, String itgrpcd,
			TaxReportBean taxRepBean) {
		/*
		 * taxRepBean.setYtdstr(
		 * " select regular.trncd,c.disc ,net_amt,c.plusminus,c.itgrp,c.itgrp2,C.CHKSLB  from "
		 * +
		 * " ( SELECT Y.TRNCD, SUM(CASE y.trncd WHEN 108 THEN (case when net_Amt  > 800 then net_amt - 800 else net_amt end) ELSE net_amt "
		 * + "   END) net_amt" + " FROM YTDTRAN Y,CDMAST C " +
		 * " Where Y.empno = " + peno + " AND Y.TRNDT BETWEEN '"
		 * +ReportDAO.BoFinancialy(prdt)+ "'  AND '"+
		 * ReportDAO.EoFinancialy(prdt) +"' "+
		 * " AND Y.TRNCD = C.TRNCD AND ( C.ITGRP LIKE '" + itgrpcd +
		 * "' or C.ITGRP2 LIKE '" + itgrpcd + "' ) " +
		 * " GROUP BY y.TRNCD ) regular , cdmast c " +
		 * " where C.trncd = regular.trncd order by c.itgrp");
		 * System.out.println("form16..."+
		 * "select regular.trncd, c.disc, net_amt, c.plusminus, c.itgrp, c.itgrp2, C.CHKSLB  from "
		 * +
		 * " ( SELECT Y.TRNCD, SUM(CASE y.trncd WHEN 108 THEN (case when net_Amt  > 800 then net_amt - 800 else net_amt end) ELSE net_amt "
		 * + "   END) net_amt" + " FROM YTDTRAN Y,CDMAST C " +
		 * " Where Y.empno = " + peno + " AND Y.TRNDT BETWEEN '"
		 * +ReportDAO.BoFinancialy(prdt)+ "'  AND '"+
		 * ReportDAO.EoFinancialy(prdt) +"' "+
		 * " AND Y.TRNCD = C.TRNCD AND ( C.ITGRP LIKE '" + itgrpcd +
		 * "' or C.ITGRP2 LIKE '" + itgrpcd + "' ) " +
		 * " GROUP BY y.TRNCD ) regular , cdmast c " +
		 * " where C.trncd = regular.trncd order by c.itgrp");
		 */
		// this is old query but now change... this is same as taxsheet's query
		// ...@niket..
		taxRepBean
				.setYtdstr(" SELECT regular.trncd, c.disc, net_amt, net_amt1, c.plusminus, c.itgrp, c.itgrp2, C.CHKSLB FROM "
						+ "(	SELECT Y.TRNCD, "
						+ " SUM( CASE y.trncd WHEN 108 THEN (case when net_Amt  > 3000 then net_amt - 3000	 else net_amt end)ELSE inp_amt END) * "
						+ taxRepBean.getProjMonth()
						+ " net_amt1"
						+ " FROM  PAYTRAN_STAGE Y, CDMAST C "
						+ " WHERE Y.empno = "
						+ peno
						+ " AND Y.TRNDT =(SELECT MAX(TRNDT) FROM PAYTRAN_STAGE WHERE EMPNO ="
						+ peno
						+ " and TRNCD=101) "
						+ " AND Y.TRNCD = C.TRNCD AND	(C.ITGRP  LIKE '"
						+ itgrpcd
						+ "' OR	C.ITGRP2  LIKE '"
						+ itgrpcd
						+ "')"
						+ " AND projyn = 'Y' GROUP BY  y.TRNCD ) projection "
						+ " RIGHT OUTER JOIN (SELECT  Y.TRNCD, SUM(CASE y.trncd WHEN 108 "
						+ " THEN (case when net_Amt  > 3000 then net_amt - 3000	 else net_amt end) ELSE net_amt	END) net_amt "
						+ " FROM YTDTRAN Y, CDMAST C WHERE Y.empno = "
						+ peno
						+ " AND Y.TRNDT  BETWEEN '"
						+ ReportDAO.BoFinancialy(prdt)
						+ "'  AND '"
						+ ReportDAO.EoFinancialy(prdt)
						+ "' "
						+ "  AND	Y.TRNCD  = C.TRNCD  AND	(C.ITGRP  LIKE '"
						+ itgrpcd
						+ "' OR	C.ITGRP2  LIKE '"
						+ itgrpcd
						+ "') GROUP BY "
						+ " y.TRNCD ) regular  ON  projection.trncd  = regular.trncd ,"
						+ " cdmast c 	WHERE	 C.trncd  = regular.trncd	ORDER BY c.itgrp ");

		System.out.println("this is form 16..." + taxRepBean.getYtdstr());

	}

	public static void printgrpF16(int ppeno, String pprdt,
			RepoartBean repBean, TaxReportBean taxRepBean) {
		// System.out.println("printgrpF16");
		String pitgrp = "";
		String pitgrp2 = "";
		ResultSet slb = null;
		ResultSet ytd = null;
		String slbstr = "";
		int reftot = 0;
		Statement st;
		try {
			st = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			// System.out.println("hrishi "+taxRepBean.getYtdstr());
			ytd = st.executeQuery(taxRepBean.getYtdstr());
			while (ytd.next()) {
				pitgrp = ytd.getString("itgrp") != null ? ytd
						.getString("itgrp") : "";
				taxRepBean.setTotamt(0);
				while (ytd.getString("ITGRP").equalsIgnoreCase(pitgrp)) {
					taxRepBean.setTotamt(taxRepBean.getTotamt()
							+ ytd.getString("NET_AMT") != null ? ytd
							.getInt("NET_AMT") : 0);

					if (ytd.getString("PLUSMINUS").equalsIgnoreCase("M")) {
						taxRepBean.setC3tot(taxRepBean.getC3tot()
								- (ytd.getString("NET_AMT") != null ? ytd
										.getInt("NET_AMT") : 0));
					} else {
						taxRepBean.setC3tot(taxRepBean.getC3tot()
								+ (ytd.getString("NET_AMT") != null ? ytd
										.getInt("NET_AMT") : 0));
					}
					if (!ytd.next()) {
						break;
					}
				}
				ytd.previous();
				if (ytd.getInt("TRNCD") == 811) {
					taxRepBean.setTotamt(calhlint(taxRepBean.getTotamt(),
							ppeno, repBean, taxRepBean));
				} else {

					if ((ytd.getString("CHKSLB") == null ? "N" : ytd
							.getString("CHKSLB")).equalsIgnoreCase("Y")) {
						slbstr = "SELECT * FROM SLAB WHERE TRNCD = "
								+ ytd.getInt("TRNCD");

						Statement st1 = repBean.getCn().createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_UPDATABLE);
						slb = st1.executeQuery(slbstr);

						if (slb.next()) {
							reftot = taxRepBean.getTotamt();
							if (ytd.getInt("TRNCD") != 803
									&& ytd.getInt("TRNCD") != 209) {
								taxRepBean.setTotamt(Math.round(Calculate
										.checkSlab(ytd.getInt("TRNCD"), pprdt,
												reftot, 1, ppeno,
												repBean.getCn()))); // ***************REMAINING***********

							}
						}
						st1.close();
					}
				}
				if (ytd.getString("PLUSMINUS").equalsIgnoreCase("M")) {
					taxRepBean.setC5tot(taxRepBean.getC5tot()
							- taxRepBean.getTotamt());
				} else {
					taxRepBean.setC5tot(taxRepBean.getC5tot()
							+ taxRepBean.getTotamt());
				}
			}
			st.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static float calHraExemption(int peno, String prdt,
			RepoartBean repBean, TaxReportBean taxRepBean) {
		Connection con = repBean.getCn();
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rsTmp = null;
		ResultSet rsTmp1 = null;
		float basicAmt = 0;
		float hraAmt = 0;
		float metroCal = 0;
		float rentCal = 0;
		float hraExmpt = 0;
		try {

			buildsql(peno, prdt, "A1A", taxRepBean);
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(taxRepBean.getYtdstr());
			if (rs.next()) {
				basicAmt = rs.getFloat("net_amt");
			}
			buildsql(peno, prdt, "A1C", taxRepBean);
			Statement stmt1 = con.createStatement();
			rs1 = stmt1.executeQuery(taxRepBean.getYtdstr());
			if (rs1.next()) {
				hraAmt = rs1.getFloat("net_amt");
			}

			Statement st = con.createStatement();
			rsTmp = st.executeQuery("select INP_AMT  from ytdtran where EMPNO="
					+ peno + " and TRNCD =564");
			if (rsTmp.next()) {
				if (rsTmp.getFloat(1) == 1.0) {
					metroCal = basicAmt * 5 / 10;
				} else if (rsTmp.getFloat(1) == 2.0) {
					metroCal = basicAmt * 4 / 10;
				}
			}
			Statement st1 = con.createStatement();
			rsTmp1 = st1
					.executeQuery("select INP_AMT  from ytdtran where EMPNO="
							+ peno + " and TRNCD =565");
			if (rsTmp1.next()) {
				rentCal = rsTmp1.getFloat(1) - (basicAmt / 10);
			}
			System.out.println("hra=" + hraAmt + ",metro=" + metroCal
					+ ",rent=" + rentCal);
			hraExmpt = ((metroCal <= hraAmt) && (metroCal <= rentCal)) ? metroCal
					: (hraAmt <= rentCal) ? hraAmt : rentCal;
			Statement st2 = con.createStatement();
			rsTmp1 = st2.executeQuery("select * from ytdtran where EMPNO="
					+ peno + " and TRNCD =563");
			if (rsTmp1.next()) {
				Statement st3 = con.createStatement();
				st3.execute("update ytdtran set inp_amt=" + hraExmpt
						+ ", cal_amt=" + hraExmpt + ", net_amt=" + hraExmpt
						+ ", upddt='" + ReportDAO.getSysDate()
						+ "' where EMPNO=" + peno + " and TRNCD =563");
			} else {
				Statement st3 = con.createStatement();
				st3.execute("insert into ytdtran (EMPNO,TRNCD,srno,inp_amt,cal_amt,net_amt,trndt,upddt) values("
						+ peno
						+ ",563,0,"
						+ hraExmpt
						+ ","
						+ hraExmpt
						+ ","
						+ hraExmpt
						+ ",'2014-05-15','"
						+ ReportDAO.getSysDate()
						+ "')");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hraExmpt;
	}

	public static int calhlint(int ptotamt, int ppeno, RepoartBean repBean,
			TaxReportBean taxRepBean) {
		ResultSet ded = null;
		String dedstr = "SELECT * FROM DEDMAST WHERE TRNCD = 209 AND EMPNO ="
				+ ppeno;
		try {
			Statement st = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ded = st.executeQuery(dedstr);
			if (ded.next()) {
				if (ptotamt > taxRepBean.getMaxhlint2()) {
					ptotamt = taxRepBean.getMaxhlint2();
				}
			}
			st.executeUpdate("UPDATE F16 SET HSG_INT = " + ptotamt
					+ " WHERE EMPNO = " + ppeno);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ptotamt;
	}

	public static void Chk_reb88_Slab(int trncd, String dt, int WrkAmt,
			int WrkAmt1, int emp_type, RepoartBean repBean,
			TaxReportBean taxRepBean) {
		String slbstr = "";
		ResultSet slb = null;
		int slbamt = 0;
		switch (emp_type) {
		case 4:
			slbstr = " select * from slab where emp_cat = 0 and trncd = "
					+ trncd
					+ " and effdate = "
					+ "( select min(s.effdate) from slab s where s.emp_cat = 0 and  trncd = "
					+ trncd + " and effdate >= '" + dt + "' ) and " + +WrkAmt1
					+ " between  frmamt and toamt order by srno ";
			break;
		case 1:
			slbstr = " select * from slab where emp_cat = 1 and trncd = "
					+ trncd
					+ " and effdate = "
					+ "( select min(s.effdate) from slab s where  s.emp_cat = 1 and trncd = "
					+ trncd + " and effdate >= '" + dt + "' ) and " + +WrkAmt1
					+ " between  frmamt and toamt order by srno ";
			break;
		case 2:
			slbstr = " select * from slab where emp_cat = 2 and trncd = "
					+ trncd
					+ " and effdate = "
					+ "( select min(s.effdate) from slab s where  s.emp_cat = 2 and  trncd = "
					+ trncd + " and effdate >= '" + dt + "' ) and " + +WrkAmt1
					+ " between  frmamt and toamt order by srno ";
			break;
		case 3:
			slbstr = " select * from slab where emp_cat = 3 and trncd = "
					+ trncd
					+ " and effdate = "
					+ "( select min(s.effdate) from slab s where  s.emp_cat = 3 and  trncd = "
					+ trncd + " and effdate >= '" + dt + "' ) and " + +WrkAmt1
					+ " between  frmamt and toamt order by srno ";
			break;
		default:
			break;
		}
		try {
			Statement st = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			slb = st.executeQuery(slbstr);
			if (!slb.next()) {
				System.out.println("NO SLAB FOUND FOR " + trncd);

			}

			taxRepBean.setReb88_per(slb.getString("PER") != null ? slb
					.getInt("PER") : 0);
			if (WrkAmt > 100000) {
				if (WrkAmt1 < 100000) {
					if (slb.getInt("PER") > 20) {
						taxRepBean.setReb88_per(20);

					}
				}
			}
			taxRepBean.setReb88_max_amt(slb.getInt("MAXAMT"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void PrintTaxSheet(String empcode, int peno, String prdt,
			String pename, String sex, RepoartBean repBean,
			TaxReportBean taxRepBean) {

		int totamt = 0;
		int OTHER_DED = 0;
		int tot499 = 0;
		int DED80 = 0;
		int reb88d = 0;
		String slbstr = "";
		int reb88 = 0;
		ResultSet slb = null;
		int totinc = 0;
		int totded = 0;
		int gross_tax_amt = 0;
		int tax_amtiii = 0;
		int tax88_rebate = 0;
		int N = 0;
		taxRepBean.setYtd(null);
		try {
			Phrase title = new Phrase("Tax Sheet", F10Bold);
			Paragraph para = new Paragraph(title);
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingBefore(0);
			taxRepBean.getDoc().add(para);

			para = new Paragraph(new Phrase(
					"Financial  Year "
							+ ReportDAO.Boy(ReportDAO.getSysDate()).substring(
									7, 11)
							+ " - "
							+ ReportDAO.EoFinancialy(
									ReportDAO.Boy(ReportDAO.getSysDate()))
									.substring(7, 11)
							+ " Assessment Year "
							+ ReportDAO.Boy(ReportDAO.Eoy(ReportDAO
									.getSysDate()) + 10)
							+ " - "
							+ ReportDAO.Eoy(ReportDAO.Eoy(ReportDAO
									.getSysDate()) + 10), F10Bold));
			para.setAlignment(Element.ALIGN_CENTER);

			taxRepBean.getDoc().add(para);
			para = new Paragraph(new Phrase("Employee Code : " + empcode
					+ " Name :" + pename, F10Bold));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(10);

			taxRepBean.getDoc().add(para);

			PdfPTable tab1 = new PdfPTable(5);
			tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
					new Rectangle(100, 100));
			PdfPCell subcell = new PdfPCell(new Phrase("Trn Code ", F10Normal));
			subcell.setBorderWidthLeft(0);
			subcell.setBackgroundColor(new GrayColor(0.90f));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("(I) INCOME FROM SALARY  :- ",
					F10Normal));
			subcell.setBorderWidthLeft(0);
			subcell.setBackgroundColor(new GrayColor(0.90f));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("Actual Amt(Rs)", F10Normal));
			subcell.setBorderWidthRight(0);
			subcell.setBackgroundColor(new GrayColor(0.90f));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("Projected Amt(Rs)", F10Normal));
			subcell.setBorderWidthRight(0);
			subcell.setBackgroundColor(new GrayColor(0.90f));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("Total Amt(Rs)", F10Normal));
			subcell.setBorderWidthRight(0);
			subcell.setBackgroundColor(new GrayColor(0.90f));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);
			taxRepBean.getDoc().add(tab1);

			taxRepBean.setC4tot(0);
			taxRepBean.setC5tot(0);
			taxRepBean.setC3tot(0);
			taxRepBean.setTotamt(0);
			taxRepBean.setGnm("A1");
			buildsql(peno, prdt, "A1%", taxRepBean);
			printgrp(peno, prdt, repBean, taxRepBean);

			tax_amtiii = 0;
			taxRepBean.setGnm("");
			totinc = taxRepBean.getC5tot();
			tax_amtiii = totinc;
			int leaveencashamt = 0;
			int leavebalnce = 0;
			int old_encash_day = 0;
			//System.out.println("start leave encash...");
			Statement stleave = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Statement encashst = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Statement encashst1 = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rsleave1 = null;
			ResultSet rsleave2 = null;
			ResultSet rsleave3 = null;
			String EncashBal =""
					+ "(select SUM(DAYS) as DAYS from leavetran where empno="+peno+" "
					+ "and LEAVECD=1 and LREASON='Leave encash' and TRNTYPE='D' and LEAVEPURP=4 "
					+ "and TRNDATE between (SELECT (convert(nvarchar(5), YEAR(getdate()))+'-01-01')) and (SELECT (convert(nvarchar(5), YEAR(getdate()))+'-12-31')))";
			rsleave3 = encashst.executeQuery(EncashBal);
			if (rsleave3.next()) {
				leavebalnce = rsleave3.getInt("DAYS");
			}
			else{
			String encash_days = (" SELECT Ceiling ((SELECT crlim FROM   leavemass "
					+ " WHERE  leavecd = 1 AND status = 'A') - (SELECT CASE when Isnull(Sum(net_amt),0)<24 "
					+ " then 0 else Isnull(Sum(net_amt),0) "
					+ " end as Net_amt FROM   paytran_stage "
					+ " WHERE  trncd IN ( 301, 302 ) "
					+ "AND trndt BETWEEN   '01-Jan-"+ReportDAO.Boy(prdt).substring(7, 11)+"' AND  '31-Dec-"+ReportDAO.Boy(prdt).substring(7, 11)+"' "
					+ " AND empno = " + peno + " ) * 2.5 * 12  / 365)  AS finalDays ");
			System.out.println("creadit old days..." + encash_days);
			rsleave1 = encashst.executeQuery(encash_days);
			if (rsleave1.next()) {
				old_encash_day = rsleave1.getInt("finalDays");
				
			} else {
				old_encash_day = 0;
			}
			System.out.println("TO be creadited=="+old_encash_day);
			// chnged month here.... make it 01-01-year later when creadit leave
			// here taken new encashed days and then minus leaves taken in that current year
			/*String checkbal = ("if exists (select EMPNO,DAYS as encash_days from leavetran "
					+ "where LEAVECD=1 and TRNDATE between '"
					+ ReportDAO.Boy(prdt).substring(7, 11)
					+ "-01-01' and '"
					+ ReportDAO.Boy(prdt).substring(7, 11)
					+ "-12-31' "
					+ "and LEAVEPURP=4 and STATUS='ENCASHED' and TRNTYPE='D' and empno="
					+ peno
					+ ") "
					+ " select EMPNO,DAYS-(SELECT Isnull(Sum(days), 0) AS usedleave " + 
					"FROM   leavetran " + 
					"WHERE  empno = "+peno+" " + 
					"AND TRNDATE between '"+ReportDAO.Boy(prdt).substring(7, 11)+"-01-01' and '"+ReportDAO.Boy(prdt).substring(7, 11)+"-12-31' " +
					"AND leavecd = 1 " + 
					"AND status = 'SANCTION' " + 
					"AND trntype = 'D' ) as encash_days from leavetran "
					+ " where LEAVECD=1 and TRNDATE between '"
					+ ReportDAO.Boy(prdt).substring(7, 11)
					+ "-01-01' and '"
					+ ReportDAO.Boy(prdt).substring(7, 11)
					+ "-12-31' "
					+ " and LEAVEPURP=4 and STATUS='ENCASHED' and TRNTYPE='D' and empno="
					+ peno + " " + " else " + " (select 0 as encash_days)");*/
			/*
			 * String checkbal=(
			 * "select isnull(SUM(bal),0) as leavebalance from leavebal where EMPNO="
			 * +peno+" and SRNO = "+
			 * "(select MAX(SRNO) from leavebal where EMPNO="
			 * +peno+" and LEAVECD=1 ) and  "+
			 * " BALDT between '"+ReportDAO.BoFinancialy
			 * (prdt)+"' and '"+ReportDAO.EoFinancialy(prdt)+"' and LEAVECD=1");
			 */
			/*System.out.println("checking leavebalnce...." + checkbal);
			ResultSet leavebal = stleave.executeQuery(checkbal);
			if (leavebal.next()) {
				leavebalnce = leavebal.getInt("encash_days") > 90 ? leavebal
						.getInt("encash_days") - 90 : leavebal
						.getInt("encash_days");

				if (leavebalnce == 0) {*/
					String oldbalcheck = ("SELECT bal+" + old_encash_day
							+ " AS encash_days " + "FROM   leavebal "
							+ " WHERE  empno = " + peno + " "
							+ " AND leavecd = 1 "
							+ "and srno = ( SELECT top 1 (srno) "
							+ "FROM   leavebal " + "WHERE  empno = " + peno
							+ " " + " AND leavecd = 1 order by baldt desc,srno desc)");
					rsleave2 = encashst1.executeQuery(oldbalcheck);
					if (rsleave2.next()) {
						leavebalnce = rsleave2.getInt("encash_days") > 90 ? rsleave2
								.getInt("encash_days") - 90 : 0;
					} else {
						leavebalnce = 0;
					}
			}	
				/*} else if(leavebalnce>0) {
					leavebalnce = leavebal.getInt("encash_days") > 90 ? leavebal
							.getInt("encash_days") - 90 : leavebal
							.getInt("encash_days");
				}else {
					leavebalnce = 0;
				}

			} else {
				leavebalnce = 0;
			}*/
			//System.out.println("Encash leavebalnce......" + leavebalnce);
			//System.out.println("system year..." + ReportDAO.getServerDate());
			//System.out.println("prdt year..."+ (Integer.parseInt(prdt.substring(7, 11))));
			String format = "dd-MM-yyyy";
			String curdt = ReportDAO.getServerDate();
			String month1[] = curdt.split("-");
			System.out.println("month...." + month1[1]);
			int monthno = ReportDAO.getMonth(ReportDAO.getServerDate());
			System.out.println("month no...." + monthno);
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date dateObj3 = sdf.parse(month1[0] + "-" + String.valueOf(monthno)
					+ "-" + month1[2]);
			Date dateObj4 = sdf.parse("31-03-" + prdt.substring(7, 11));
			String bsicdavda_year = "";
			if (dateObj3.getTime() > dateObj4.getTime()) {
				bsicdavda_year = String.valueOf((Integer.parseInt(prdt
						.substring(7, 11))));
			} else {
				/*bsicdavda_year = String.valueOf((Integer.parseInt(prdt
						.substring(7, 11)) - 1));*/
				bsicdavda_year = String.valueOf((Integer.parseInt(prdt
						.substring(7, 11)) - 1));
			}
			
			String datechecker="";
			datechecker=String.valueOf((Integer.parseInt(bsicdavda_year) + 1));
			System.out.println("datechecker : "+datechecker);
			
			// this is for taking basic da vda of mar of acadmic start
			// year...@ni//this is logic is also working but above makes it
			// simple
			/*
			 * String bsicdavda_year =
			 * String.valueOf((Integer.parseInt(ReportDAO
			 * .getServerDate().substring(7, 11))) > (Integer
			 * .parseInt(prdt.substring(7, 11))) ? (Integer
			 * .parseInt(ReportDAO.getServerDate().substring(7, 11)) - 1) :
			 * (Integer.parseInt(prdt.substring(7, 11))));
			 */
			
			//System.out.println("this is the encash date....." + "31-MAR-"+ bsicdavda_year);
			
/*			String leave_encsh = ("if exists (select * from paytran where TRNCD in(101,102,138) and EMPNO="
					+ peno
					+ " and trndt = '"
					+ ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"
							+ bsicdavda_year))
					+ "' ) "
					+ "(select round(((SUM(INP_AMT)/30)*"
					+ leavebalnce
					+ "),0) as encash from paytran where TRNCD in(101,102,138) and EMPNO="
					+ peno
					+ " and trndt = '"
					+ ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"
							+ bsicdavda_year))
					+ "') "
					+ "else (select round(((SUM(INP_AMT)/30)*"
					+ leavebalnce
					+ "),0) as encash from PAYTRAN_STAGE where TRNCD in(101,102,138) and trndt = '"
					+ ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"
							+ bsicdavda_year)) + "' and EMPNO=" + peno + ")");
*/
			
		/*	String leave_encsh = ("if exists (select * from paytran where TRNCD in(101,102,138) and EMPNO="
					+ peno
					+ " and trndt = '"
					+ ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"
							+ bsicdavda_year))
					+ "' ) "
					+ "(select round(((SUM(INP_AMT)/30)*"
					//+"(((select top 1 bal from leavebal where EMPNO= "+peno+ "and LEAVECD=1 ORDER BY BALDT DESC)+30)-90)"
					+"(((select top 1 bal from leavebal where EMPNO= "+peno+ "and LEAVECD=1 ORDER BY srno DESC)+30)-90)"
					+ "),0) as encash from paytran where TRNCD in(101,102,138) and EMPNO="
					+ peno
					+ " and trndt = '"
					+ ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"
							+ bsicdavda_year))
					+ "') "
					+ "else (select round(((SUM(INP_AMT)/30)*"
					//+ "(((select top 1 bal from leavebal where EMPNO= " +peno+ " and LEAVECD=1 ORDER BY BALDT DESC)+30)-90)"
					+ "(((select top 1 bal from leavebal where EMPNO= " +peno+ " and LEAVECD=1 ORDER BY srno DESC)+30)-90)"
					+ "),0) as encash from PAYTRAN_STAGE where TRNCD in(101,102,138) and trndt = '"
					+ ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"
							+ bsicdavda_year)) + "' and EMPNO=" + peno + ")");
*/
			
			/*String leave_encsh = ("if exists (SELECT encashnet AS encash FROM   encashment WHERE  empno ="+peno+ "and encashdate LIKE '%"+datechecker+"%')"
					+ "(SELECT encashnet AS encash FROM   encashment WHERE  empno ="+peno+ "and encashdate LIKE '%"+datechecker+"%') "
					+ "else (select round(((SUM(INP_AMT)/30)*"
					+ "(((select top 1 bal from leavebal where EMPNO= " +peno+ " and LEAVECD=1 ORDER BY srno DESC)+30)-90)"
					+ "),0) as encash from PAYTRAN_STAGE where TRNCD in(101,102,138) and trndt = '"
					+ ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"
							+ bsicdavda_year)) + "' and EMPNO=" + peno + ")");*/
			
			String leave_encsh = ("if exists (SELECT encashnet AS encash FROM   encashment WHERE  empno ="+peno+ "and encashdate LIKE '%"+datechecker+"%')"
					+ "(SELECT encashnet AS encash FROM   encashment WHERE  empno ="+peno+ "and encashdate LIKE '%"+datechecker+"%') "
					+ "else (SELECT TOTAL_AMT as encash from YEARLYEARNING where TRNCD=145 and EMPNO=" + peno + " AND  TRNDT LIKE '%"+datechecker+"%')");

			
					
			// System.out.println("this is date@#@#@#@#@#@@#@#@.."+prdt+"..."+ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"+prdt.substring(7,11))));
			System.out.println("query leaveencash 1..." + leave_encsh);
			Connection con = ConnectionManager.getConnection();

			ResultSet rsleave = stleave.executeQuery(leave_encsh);
			if (rsleave.next()) {
				
			// leaveencashamt = rsleave.getInt("encash");
				
				
				//govi
				if(rsleave.getInt("encash")<=0)
				{
					leaveencashamt = 0;
				}
				else
				{
					leaveencashamt = rsleave.getInt("encash");
				}
				
				
				
				
				
				/*
				 * System.out.println("this march date encashment..."+leaveencashamt
				 * );
				 * 
				 * //take 28-FEB_YEAR's BASIC ,DA, VDA if 31-MAR-YEAR's
				 * BASIC,DA,VDA is 0... in case if emp is absent all month
				 * if(leaveencashamt==0) { System.out.println(
				 * "may be no salalry of mar.. so checking for ffeb..."
				 * +leaveencashamt); String leave_encsh1=(
				 * "if exists (select * from paytran where TRNCD in(101,102,138) and EMPNO="
				 * +
				 * peno+" and trndt = ( select CONVERT(varchar,dateadd(d,-(day('"
				 * +
				 * ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"+bsicdavda_year
				 * ))+"')),'"+ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"+
				 * bsicdavda_year))+"'),106) ) "+
				 * "(select round(((SUM(Net_amt)/30)*"+leavebalnce+
				 * "),0) as encash from paytran where TRNCD in(101,102,138) and EMPNO="
				 * +
				 * peno+" and trndt = ( select CONVERT(varchar,dateadd(d,-(day('"
				 * +
				 * ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"+bsicdavda_year
				 * ))+"')),'"+ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"+
				 * bsicdavda_year))+"'),106) )) "+
				 * "else (select round(((SUM(Net_amt)/30)*"+leavebalnce+
				 * "),0) as encash from PAYTRAN_STAGE where TRNCD in(101,102,138) and trndt = ( select CONVERT(varchar,dateadd(d,-(day('"
				 * +
				 * ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"+bsicdavda_year
				 * ))+"')),'"+ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"+
				 * bsicdavda_year))+"'),106) ) and EMPNO="+peno+")");
				 * System.out.println(
				 * "this taking basic+da+vda from feb-28...may be bcz march is empty sal.."
				 * +leave_encsh1);
				 * rsleave1=stleavefeb.executeQuery(leave_encsh1);
				 * if(rsleave1.next()) { leaveencashamt=
				 * rsleave1.getInt("encash"); } else { leaveencashamt= 0; } }
				 */
			}

			String checkleave = ("if exists(select * from YTDTRAN where TRNCD=344 "
					+ "and TRNDT between '"
					+ ReportDAO.BoFinancialy(prdt)
					+ "' and '"
					+ ReportDAO.EoFinancialy(prdt)
					+ "' and EMPNO="
					+ peno
					+ ") "
					+ "update ytdtran set inp_amt="
					+ leaveencashamt
					+ ",cal_amt="
					+ leaveencashamt
					+ ",net_amt="
					+ leaveencashamt
					+ ",upddt=(select CONVERT(date,GETDATE())) "
					+ "where EMPNO="
					+ peno
					+ " and TRNCD=344 "
					+ "else insert into YTDTRAN values((select MAX(trndt) from YTDTRAN where EMPNO="
					+ peno
					+ "), "
					+ " "
					+ peno
					+ ",344,0,"
					+ leaveencashamt
					+ "," + leaveencashamt + ",0,0," + leaveencashamt + ",'','',(select CONVERT(date,GETDATE())),'F' )");
			Statement stleave1 = con.createStatement();
			
			//System.out.println("this in update or insert into ytdtran for 344...."+ checkleave);
			
			stleave1.executeUpdate(checkleave);

			tab1 = new PdfPTable(5);
			tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
					new Rectangle(100, 100));
			subcell = new PdfPCell(new Phrase("145", F10Normal));
			subcell.setBorderWidthLeft(0);
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("			LEAVE SALARY ", F10Normal));
			subcell.setBorderWidthRight(0);
			subcell.setColspan(3);
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(UtilityDAO.trans(leaveencashamt,
					"999,99,999.99", "", false, false), F10Normal));

			subcell.setBorderWidthRight(0);
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);
			totinc += leaveencashamt;
			taxRepBean.getDoc().add(tab1);
			
			//System.out.println("total leaveencash..." + leaveencashamt);
			
			Statement st1 = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			buildsqlsumofquery(peno, prdt, "A2%", taxRepBean); // this is for
																// bonus aND
																// ARREARS....ADDING
																// INCOME TO
																// TOTAL INCOME
			// printgrp(peno, prdt,repBean,taxRepBean);
			ResultSet rs = st1.executeQuery(taxRepBean.getYtdstr());

			while (rs.next()) {
				tab1 = new PdfPTable(5);
				tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
						new Rectangle(100, 100));
				subcell = new PdfPCell(new Phrase(rs.getString("TRNCD"),
						F10Normal));
				subcell.setBorderWidthLeft(0);
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tab1.addCell(subcell);
				subcell = new PdfPCell(new Phrase("    		"
						+ rs.getString("DISC"), F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setColspan(3);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				subcell = new PdfPCell(new Phrase(
						UtilityDAO.trans(rs.getInt("NET_AMT"), "999,99,999.99",
								"", false, false), F10Normal));
				// System.out.println(" asd"+totinc+" fesf"+ totded);
				subcell.setBorderWidthRight(0);
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tab1.addCell(subcell);
				tot499 = rs.getInt("NET_AMT");
				// System.out.println("tot this is bonus..."+tot499);
				// System.out.println("tot this is before bonus..."+totinc);
				totinc += tot499;
				// System.out.println("tot this is after bonus..."+totinc);
				taxRepBean.getDoc().add(tab1);
				if (!rs.next()) {
					break;
				} else {
					rs.previous();
					continue;
				}
			}

			tab1 = new PdfPTable(5);
			tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
					new Rectangle(100, 100));
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setBorderWidthLeft(0);
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("       TOTAL  :-       ",
					F10Normal));
			subcell.setBorderWidthRight(0);
			subcell.setColspan(3);
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase(UtilityDAO.trans(totinc,
					"999,99,999.99", "", false, false), F10Normal));
			
			System.out.println("TEST totinc : "+totinc);

			subcell.setBorderWidthRight(0);
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);

			taxRepBean.getDoc().add(tab1);

			tab1 = new PdfPTable(5);
			tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
					new Rectangle(100, 100));
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setBorderWidthLeft(0);
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(
					"        Less : Deductions U/S 16 :", F10Normal));
			subcell.setBorderWidthRight(0);
			subcell.setColspan(4);
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab1.addCell(subcell);
			taxRepBean.getDoc().add(tab1);
			buildsql(peno, prdt, "A5%", taxRepBean);
			taxRepBean.setC4tot(0);
			taxRepBean.setC3tot(0);
			taxRepBean.setC5tot(0);
			taxRepBean.setTotamt(0);
			printgrp(peno, prdt, repBean, taxRepBean);
			totded += taxRepBean.getC5tot();
			//System.out.println("std ddedd...." + totded);
			// why to check slAB FOR deduction..@ni
			// taxRepBean.setC5tot((int)Calculate.checkSlab(500 , prdt,
			// taxRepBean.getC5tot(), 1, peno,repBean.getCn()));
			//System.out.println("0022" + taxRepBean.getC5tot());
			//System.out.println("total ded..." + taxRepBean.getTotamt());
/*			if (taxRepBean.getC5tot() > 0) {
				taxRepBean.setC5tot(0);
				tab1 = new PdfPTable(5);
				tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
						new Rectangle(100, 100));
				subcell = new PdfPCell(new Phrase("500", F10Normal));
				subcell.setBorderWidthLeft(0);
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tab1.addCell(subcell);
				subcell = new PdfPCell(new Phrase("STANDARD DEDUCTION",
						F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setColspan(3);
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tab1.addCell(subcell);
				// this is standard deduction..40000 or salary which ever is
				// less
				taxRepBean.setC5(totinc > 40000 ? 40000 : totinc);
				totded = taxRepBean.getC5();
				subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
						taxRepBean.getC5(), "999,99,999.99", "", false, false),
						F10Normal));
				subcell.setBorderWidthRight(0);

				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tab1.addCell(subcell);
				taxRepBean.getDoc().add(tab1);
				taxRepBean.setC4tot(0);
				taxRepBean.setC3tot(0);
				taxRepBean.setC5tot(0);
				taxRepBean.setTotamt(0);
				printgrp(peno, prdt, repBean, taxRepBean);
				totded += taxRepBean.getC5tot();
				System.out.println("std ddedd...." + totded);
			}*/

			taxRepBean.setC4tot(0);
			taxRepBean.setC3tot(0);
			taxRepBean.setC5tot(0);
			taxRepBean.setTotamt(0);
			
			// commented bcz not to added conveyance in deduction..added std
			// ded..40000
			// buildsql(peno, prdt, "A1U%",taxRepBean);
			// printgrp(peno,prdt,repBean,taxRepBean);
			// System.out.println("std ddedd111...."+taxRepBean.getTotamt());
			// totded+= taxRepBean.getTotamt();

			/*
			 * buildsql(peno, prdt, "A3U%",taxRepBean);
			 * printgrp(peno,prdt,repBean,taxRepBean);
			 */
			// ' Total Income From all
			try {
				taxRepBean.setC4tot(0);
				taxRepBean.setC3tot(0);
				taxRepBean.setC5tot(0);
				taxRepBean.setTotamt(0);
				int amt522 = 0;
				int amt523 = 0;
				int amt524 = 0;
				int amt525 = 0;
				int amt565 = 0;
				int amt588 = 0;
				String A3U = "SELECT * FROM CDMAST WHERE ITGRP LIKE 'A3U%' order by trncd";
				Statement stA3 = repBean.getCn().createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ResultSet rsA3111 = stA3.executeQuery(A3U);
				if (rsA3111.next()) {
					rsA3111.previous();
					while (rsA3111.next()) {
						tab1 = new PdfPTable(5);
						tab1.setWidthPercentage(new float[] { 10, 30, 20, 20,
								20 }, new Rectangle(100, 100));
						subcell = new PdfPCell(new Phrase("", F10Normal));
						subcell.setBorderWidthLeft(0);
						subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tab1.addCell(subcell);
						subcell = new PdfPCell(new Phrase("		"
								+ rsA3111.getString("DISC"), F10Normal));
						subcell.setBorderWidthRight(0);
						subcell.setColspan(3);
						subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						tab1.addCell(subcell);

						Statement stA32 = repBean.getCn().createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);

						buildsqlsumofquery(peno, prdt,
								rsA3111.getString("ITGRP"), taxRepBean);
						ResultSet rs11A3 = stA32.executeQuery(taxRepBean
								.getYtdstr());

						if (rs11A3.next()) {
							rs11A3.previous();

							while (rs11A3.next()) {

								subcell = new PdfPCell(new Phrase(
										UtilityDAO.trans(
												rs11A3.getInt("NET_AMT"),
												"999,99,999.99", "", false,
												false), F10Normal));
								// System.out.println(" asd"+totinc+" fesf"+
								// totded);
								subcell.setBorderWidthRight(0);
								subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								tab1.addCell(subcell);
								if (rsA3111.getInt("TRNCD") == 522)// if
																	// (522)child
																	// edu allow
																	// >2400
																	// then
																	// deduct
																	// 2400
																	// only..so
																	// added..@ni
								{
									amt522 += rs11A3.getInt("NET_AMT");
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ amt522 > 2400 ? 2400 : amt522);
								} else if (rsA3111.getInt("TRNCD") == 523)// if
																			// (523)child
																			// hostel
																			// alllow
																			// >7200
																			// then
																			// deduct
																			// 7200
																			// only..so
																			// added..@ni
								{
									amt523 += rs11A3.getInt("NET_AMT");
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ amt523 > 7200 ? 7200 : amt523);
								} else if (rsA3111.getInt("TRNCD") == 525)// if
																			// (525)LTA
																			// >19200
																			// then
																			// deduct
																			// 19200
																			// only..so
																			// added..@ni
								{
									amt525 += rs11A3.getInt("NET_AMT");
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ amt525 > 19200 ? 19200 : amt525);
								} else if (rsA3111.getInt("TRNCD") == 524)// curently
																			// no
																			// limit
																			// inserted...MEDICAL
																			// BILLS
																			// REIMBURSEMENT
								{
									amt524 += rs11A3.getInt("NET_AMT");
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ amt524);
								} else if (rsA3111.getInt("TRNCD") == 565)// curently
																			// no
																			// limit
																			// inserted...RENT
																			// PAID
								{
									amt565 += rs11A3.getInt("NET_AMT");
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ amt565);
								} else if (rsA3111.getInt("TRNCD") == 588)// curently
																			// no
																			// limit
																			// inserted....SODEXO
																			// COUPONS
																			// REIMBURSEMENT
								{
									amt588 += rs11A3.getInt("NET_AMT");
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ amt588);
								} else {
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ rs11A3.getInt("NET_AMT"));
								}

								// System.out.println("tot this is bonus..."+tot499);
								// System.out.println("tot this is before bonus..."+totinc);
								totded += taxRepBean.getC5tot();
								taxRepBean.setC5tot(0);
								// System.out.println("tot this is after bonus..."+totinc);
								taxRepBean.getDoc().add(tab1);

								if (!rs11A3.next()) {

									break;
								} else {

									rs11A3.previous();
									continue;
								}
							}
						} else {
							subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
									0, "999,99,999.99", "", false, false),
									F10Normal));
							// System.out.println(" asd"+totinc+" fesf"+
							// totded);
							subcell.setBorderWidthRight(0);
							subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
							tab1.addCell(subcell);
							taxRepBean.getDoc().add(tab1);
						}
					}
				}

				Statement st = repBean.getCn().createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				int cnt = st.executeUpdate("UPDATE F16 SET GROSS_INC = "
						+ totinc + " WHERE EMPNO = " + peno);
				// Total Deduction from alll
				// totded+= taxRepBean.getTotamt();
				System.out.println("003" + taxRepBean.getC5tot());
				tab1 = new PdfPTable(5);
				tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
						new Rectangle(100, 100));
				subcell = new PdfPCell(new Phrase("", F10Normal));
				subcell.setBorderWidthLeft(0);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				subcell = new PdfPCell(new Phrase(
						"       Net Salary Income  :-       ", F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setColspan(3);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);

				subcell = new PdfPCell(
						new Phrase(UtilityDAO.trans(totinc - totded,
								"999,99,999.99", "", false, false), F10Normal));
				System.out.println(" asd" + totinc + " fesf" + totded);
				subcell.setBorderWidthRight(0);
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tab1.addCell(subcell);

				taxRepBean.getDoc().add(tab1);
				// carry frwd new total after deducting ded from total income
				// salary
				totinc = (totinc - totded);

				taxRepBean.setC4tot(0);
				taxRepBean.setC5tot(0);
				taxRepBean.setC3tot(0);
				taxRepBean.setC5tot(0);
				taxRepBean.setTotamt(0);
				// buildsql( peno, prdt, "A2A%",taxRepBean);

				// carry forwrd new total by adding bonus value... A2 itgrp type
				// is given for bonus trncd...by @ni
				// System.out.println("tot this is bonus..."+tot499);

				// '------------------------------------------------
				totded = taxRepBean.getC5tot();
				tab1 = new PdfPTable(5);
				tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
						new Rectangle(100, 100));
				subcell = new PdfPCell(new Phrase("", F10Normal));
				subcell.setBorderWidthLeft(0);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				subcell = new PdfPCell(new Phrase(
						" ( II )ANY OTHER INCOME REPORTED BY THE EMPLOYEE :",
						F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setColspan(4);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);

				taxRepBean.getDoc().add(tab1);

				taxRepBean.setC4tot(0);
				taxRepBean.setC5tot(0);
				taxRepBean.setC3tot(0);
				buildsql(peno, prdt, "AA%", taxRepBean);
				printgrp(peno, prdt, repBean, taxRepBean);
				OTHER_DED = (taxRepBean.getC5tot() > 200000 ? 200000
						: taxRepBean.getC5tot());// home interest should be
													// <200000
				System.out.println("THIS IS THE INST ON HOME DEDUCTION...."
						+ OTHER_DED);
				// totamt = (totinc - totded + OTHER_DED + tot499); //bcz bonus
				// is alredy added in totinc.... and agin here adding tot499
				// with totalinc .....so commented it....@ni
				totamt = ((totinc - totded) - OTHER_DED);
				totded = taxRepBean.getC5tot();
				tab1 = new PdfPTable(5);
				tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
						new Rectangle(100, 100));
				subcell = new PdfPCell(new Phrase("", F10Normal));
				subcell.setBorderWidthLeft(0);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				subcell = new PdfPCell(new Phrase(
						" ( III ) AGGREGATE GROSS TAXABLE INCOME         :",
						F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setColspan(3);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				subcell = new PdfPCell(new Phrase(UtilityDAO.trans(totamt,
						"999,99,999.99", "", false, false), F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tab1.addCell(subcell);
				taxRepBean.getDoc().add(tab1);
				tab1 = new PdfPTable(5);
				tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
						new Rectangle(100, 100));
				subcell = new PdfPCell(new Phrase("", F10Normal));
				subcell.setBorderWidthLeft(0);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				subcell = new PdfPCell(new Phrase(
						"            (i.e. (I + II  )            ", F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setColspan(4);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				taxRepBean.getDoc().add(tab1);
				tab1 = new PdfPTable(5);
				tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
						new Rectangle(100, 100));
				subcell = new PdfPCell(new Phrase("", F10Normal));
				subcell.setBorderWidthLeft(0);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				subcell = new PdfPCell(new Phrase(
						" ( IV ) DEDUCTIONS UNDER CHAPTER  VI-A  :", F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setColspan(4);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				taxRepBean.getDoc().add(tab1);
				taxRepBean.setC4tot(0);
				taxRepBean.setC5tot(0);
				taxRepBean.setC3tot(0);
				totded = 0;
				tab1 = new PdfPTable(5);
				tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
						new Rectangle(100, 100));
				subcell = new PdfPCell(new Phrase("", F10Normal));
				subcell.setBorderWidthLeft(0);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				subcell = new PdfPCell(new Phrase(
						"     a) DEDUCTIONS U/S 80 C     ", F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setColspan(4);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				taxRepBean.getDoc().add(tab1);
				taxRepBean.setC5tot(0);
				/* buildsql(peno, prdt, "AC%",taxRepBean); */// here need to
																// print
																// deduction
																// under 80c...
																// so cmnt it
																// and new line
																// added with
																// itgrp AJ----
																// these trncd
																// are undet
																// 80c....@ni
				/*
				 * buildsql(peno, prdt, "80C%",taxRepBean);//worked it...but
				 * need to display evrycode even having 0 amount..@ani
				 * printgrp(peno, prdt,repBean,taxRepBean);
				 */
				String US80c = "SELECT * FROM CDMAST WHERE ITGRP LIKE '80C%' order by trncd";
				Statement st3 = repBean.getCn().createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ResultSet rs111 = st3.executeQuery(US80c);
				if (rs111.next()) {
					rs111.previous();
					while (rs111.next()) {
						tab1 = new PdfPTable(5);
						tab1.setWidthPercentage(new float[] { 10, 30, 20, 20,
								20 }, new Rectangle(100, 100));
						subcell = new PdfPCell(new Phrase("", F10Normal));
						subcell.setBorderWidthLeft(0);
						subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tab1.addCell(subcell);
						subcell = new PdfPCell(new Phrase("		"
								+ rs111.getString("DISC"), F10Normal));
						subcell.setBorderWidthRight(0);
						subcell.setColspan(3);
						subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						tab1.addCell(subcell);

						Statement st2 = repBean.getCn().createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);
						Statement st22 = repBean.getCn().createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);

						buildsqlsumofquery(peno, prdt,
								rs111.getString("ITGRP"), taxRepBean);
						ResultSet rs11 = st2.executeQuery(taxRepBean
								.getYtdstr());

						if (rs11.next()) {
							rs11.previous();

							while (rs11.next()) {

								// int cdamt1=rs11.getInt("NET_AMT");
								if (rs111.getInt("TRNCD") == 201
										|| rs111.getInt("TRNCD") == 205
										|| rs111.getInt("TRNCD") == 207) {
									int cdamt = 0;
									ResultSet licgiproj = st22
											.executeQuery("select net_amt from paytran_stage "
													+ "where trndt =(select max(trndt) from paytran_stage where empno="
													+ peno
													+ " "
													+ "and trncd ="
													+ rs111.getInt("TRNCD")
													+ ") and empno ="
													+ peno
													+ " "
													+ "and trncd="
													+ rs111.getInt("TRNCD")
													+ " ");
									if (licgiproj.next()) {
										cdamt = licgiproj.getInt("net_amt");
									}
									int month = taxRepBean.getProjMonth();
									if (rs111.getInt("TRNCD") == 201) {
										//add ((leaveencashamt * 12) / 100) if they want to calculate pf on ((leaveencashamt ))
										cdamt = ((cdamt * month) + ((0 * 12) / 100));
									
										//System.out.println("this is PF on leave salary..added..."+ ((0 * 12) / 100));
										
									} else {
										cdamt = (cdamt * month);
									}

									//System.out.println("!@#$%^@@@@@@@@this is projected for LIC OR GI.....TRNCD"+ rs111.getInt("TRNCD")+ "......" + cdamt);
									
									subcell = new PdfPCell(new Phrase(
											UtilityDAO.trans(
													rs11.getInt("NET_AMT")
															+ cdamt,
													"999,99,999.99", "", false,
													false), F10Normal));

									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ rs11.getInt("NET_AMT") + cdamt);

								} else {
									subcell = new PdfPCell(new Phrase(
											UtilityDAO.trans(
													rs11.getInt("NET_AMT"),
													"999,99,999.99", "", false,
													false), F10Normal));
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ rs11.getInt("NET_AMT"));
								}// System.out.println(" asd"+totinc+" fesf"+
									// totded);
								subcell.setBorderWidthRight(0);
								subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								tab1.addCell(subcell);

								// System.out.println("tot this is bonus..."+tot499);
								// System.out.println("tot this is before bonus..."+totinc);

								// System.out.println("tot this is after bonus..."+totinc);
								taxRepBean.getDoc().add(tab1);

								if (!rs11.next()) {

									break;
								} else {

									rs11.previous();
									continue;
								}
							}
						} else {
							subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
									0, "999,99,999.99", "", false, false),
									F10Normal));
							// System.out.println(" asd"+totinc+" fesf"+
							// totded);
							subcell.setBorderWidthRight(0);
							subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
							tab1.addCell(subcell);
							taxRepBean.getDoc().add(tab1);
						}
					}
				}
				
				//System.out.println("this is 80c total...."+ taxRepBean.getC5tot());
				
				st.executeUpdate("UPDATE F16 SET TOT_DED = "
						+ taxRepBean.getC5tot() + " WHERE EMPNO = " + peno);
				/*
				 * if( taxRepBean.getC5tot() > 100000 ) {
				 * taxRepBean.setC5tot(100000); }
				 */// new limit for under 80C..... so Changed....@ni
				if (taxRepBean.getC5tot() > 150000) {
					taxRepBean.setC5tot(150000);
				}
				totded += taxRepBean.getC5tot();// here taken section 80C ded
												// amt in totded...& minus max
												// limit if it exceeds

				tab1 = new PdfPTable(5);
				tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
						new Rectangle(100, 100));
				subcell = new PdfPCell(new Phrase("", F10Normal));
				subcell.setBorderWidthLeft(0);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				subcell = new PdfPCell(new Phrase(
						"       Qualifying Deduction u/s 80 C ", F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setColspan(3);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
						taxRepBean.getC5tot(), "999,99,999.99", "", false,
						false), F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setColspan(3);
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tab1.addCell(subcell);
				taxRepBean.getDoc().add(tab1);

				taxRepBean.setC5tot(0);// here again reset to checking next max
										// limitr amt
				tab1 = new PdfPTable(5);
				tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
						new Rectangle(100, 100));
				subcell = new PdfPCell(new Phrase("", F10Normal));
				subcell.setBorderWidthLeft(0);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);

				subcell = new PdfPCell(new Phrase(
						"     b) Infrastructure Investment U/s 80CCF",
						F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setColspan(4);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);

				taxRepBean.getDoc().add(tab1);

				/*
				 * buildsql(peno, prdt, "AK%",taxRepBean);//it works proper but
				 * to display 0 amt evenif not there in acdmic year for that emp
				 * printgrp(peno, prdt,repBean,taxRepBean);
				 */

				String US80CCF = "SELECT * FROM CDMAST WHERE ITGRP LIKE 'AK%' order by trncd";
				Statement st4 = repBean.getCn().createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ResultSet rs1111 = st4.executeQuery(US80CCF);
				if (rs1111.next()) {
					rs1111.previous();
					while (rs1111.next()) {
						tab1 = new PdfPTable(5);
						tab1.setWidthPercentage(new float[] { 10, 30, 20, 20,
								20 }, new Rectangle(100, 100));
						subcell = new PdfPCell(new Phrase("", F10Normal));
						subcell.setBorderWidthLeft(0);
						subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tab1.addCell(subcell);
						subcell = new PdfPCell(new Phrase("		"
								+ rs1111.getString("DISC"), F10Normal));
						subcell.setBorderWidthRight(0);
						subcell.setColspan(3);
						subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						tab1.addCell(subcell);

						Statement st2 = repBean.getCn().createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);

						buildsqlsumofquery(peno, prdt,
								rs1111.getString("ITGRP"), taxRepBean);
						ResultSet rs11 = st2.executeQuery(taxRepBean
								.getYtdstr());

						if (rs11.next()) {
							rs11.previous();

							while (rs11.next()) {

								subcell = new PdfPCell(new Phrase(
										UtilityDAO.trans(
												rs11.getInt("NET_AMT"),
												"999,99,999.99", "", false,
												false), F10Normal));
								// System.out.println(" asd"+totinc+" fesf"+
								// totded);
								subcell.setBorderWidthRight(0);
								subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								tab1.addCell(subcell);
								taxRepBean.setC5tot(taxRepBean.getC5tot()
										+ rs11.getInt("NET_AMT"));
								// System.out.println("tot this is bonus..."+tot499);
								// System.out.println("tot this is before bonus..."+totinc);

								// System.out.println("tot this is after bonus..."+totinc);
								taxRepBean.getDoc().add(tab1);

								if (!rs11.next()) {

									break;
								} else {

									rs11.previous();
									continue;
								}
							}
						} else {
							subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
									0, "999,99,999.99", "", false, false),
									F10Normal));
							// System.out.println(" asd"+totinc+" fesf"+
							// totded);
							subcell.setBorderWidthRight(0);
							subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
							tab1.addCell(subcell);
							taxRepBean.getDoc().add(tab1);
						}
					}
				}

				//System.out.println("Infrastructure Investment..."+ taxRepBean.getC5tot());

				if (taxRepBean.getC5tot() > 20000) {
					taxRepBean.setC5tot(20000);
				}
				totded += taxRepBean.getC5tot();// here taken section 80CCF ded
												// amt in totded...& minus max
												// limit if it exceeds

				taxRepBean.setC5tot(0);// here reset for 80D

				tab1 = new PdfPTable(5);
				tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
						new Rectangle(100, 100));
				subcell = new PdfPCell(new Phrase("", F10Normal));
				subcell.setBorderWidthLeft(0);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);

				subcell = new PdfPCell(new Phrase(
						"     c) Medical Insurance U/S 80 D     ", F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setColspan(4);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);

				taxRepBean.getDoc().add(tab1);
				int medcl = 0;
				/*
				 * buildsql(peno, prdt, "AD%",taxRepBean);//same here above
				 * reason printgrp(peno, prdt,repBean,taxRepBean);
				 */

				String US80D = "SELECT * FROM CDMAST WHERE ITGRP LIKE '80D%' order by trncd";
				Statement st5 = repBean.getCn().createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ResultSet rs11111 = st5.executeQuery(US80D);
				if (rs11111.next()) {
					rs11111.previous();
					int age = 20;
					Statement stt = repBean.getCn().createStatement(
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					ResultSet rss = stt
							.executeQuery("select datediff(year,(select DOB from empmast where empcode='"
									+ empcode
									+ "'),(select convert(date,GETDATE()))) AGE ");
					if (rss.next()) {
						age = rss.getString("AGE") == null ? 20 : rss
								.getInt("AGE");
					}
					while (rs11111.next()) {
						tab1 = new PdfPTable(5);
						tab1.setWidthPercentage(new float[] { 10, 30, 20, 20,
								20 }, new Rectangle(100, 100));
						subcell = new PdfPCell(new Phrase("", F10Normal));
						subcell.setBorderWidthLeft(0);
						subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tab1.addCell(subcell);
						subcell = new PdfPCell(new Phrase("		"
								+ rs11111.getString("DISC"), F10Normal));
						subcell.setBorderWidthRight(0);
						subcell.setColspan(3);
						subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						tab1.addCell(subcell);

						Statement st2 = repBean.getCn().createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);

						buildsqlsumofquery(peno, prdt,
								rs11111.getString("ITGRP"), taxRepBean);
						ResultSet rs11 = st2.executeQuery(taxRepBean
								.getYtdstr());

						if (rs11.next()) {
							rs11.previous();

							while (rs11.next()) {
								int amt550 = 0;
								int amt552 = 0;
								int amt553 = 0;
								subcell = new PdfPCell(new Phrase(
										UtilityDAO.trans(
												rs11.getInt("NET_AMT"),
												"999,99,999.99", "", false,
												false), F10Normal));
								// System.out.println(" asd"+totinc+" fesf"+
								// totded);
								subcell.setBorderWidthRight(0);
								subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								tab1.addCell(subcell);
								if (rs11111.getInt("TRNCD") == 550
										|| rs11111.getInt("TRNCD") == 549) {
									// section 80D limit check
									if (age > 65) {
										amt550 = rs11.getInt("NET_AMT") > 30000 ? 30000
												: rs11.getInt("NET_AMT");
										taxRepBean.setC5tot(taxRepBean
												.getC5tot() + amt550);
									} else {
										amt550 = rs11.getInt("NET_AMT") > 25000 ? 25000
												: rs11.getInt("NET_AMT");
										taxRepBean.setC5tot(taxRepBean
												.getC5tot() + amt550);

									}
									medcl += amt550;

								} else if (rs11111.getInt("TRNCD") == 552) {
									// section 80DD limit check
									amt552 = rs11.getInt("NET_AMT") > 75000 ? 75000
											: rs11.getInt("NET_AMT");
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ amt552);
								}

								else if (rs11111.getInt("TRNCD") == 553) {
									// section 80D limit check
									if (age < 65) {
										amt553 = rs11.getInt("NET_AMT") > 40000 ? 40000
												: rs11.getInt("NET_AMT");
										taxRepBean.setC5tot(taxRepBean
												.getC5tot() + amt553);
									} else if (age > 65 && age < 80) {
										amt553 = rs11.getInt("NET_AMT") > 60000 ? 60000
												: rs11.getInt("NET_AMT");
										taxRepBean.setC5tot(taxRepBean
												.getC5tot() + amt553);
									} else {
										amt553 = rs11.getInt("NET_AMT") > 80000 ? 80000
												: rs11.getInt("NET_AMT");
										taxRepBean.setC5tot(taxRepBean
												.getC5tot() + amt553);

									}
								} else {
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ rs11.getInt("NET_AMT"));
								}
								// System.out.println("tot this is bonus..."+tot499);
								// System.out.println("tot this is before bonus..."+totinc);
								if (rs11111.getInt("TRNCD") == 550
										|| rs11111.getInt("TRNCD") == 549) {
									taxRepBean.setC5tot(0);
								}
								if (rs11111.getInt("TRNCD") != 550
										|| rs11111.getInt("TRNCD") != 549) {

									totded += taxRepBean.getC5tot();// section
																	// wise
																	// limit
																	// added to
																	// totded....
									taxRepBean.setC5tot(0);
								}// again reset for new section
									// System.out.println("tot this is after bonus..."+totinc);
								taxRepBean.getDoc().add(tab1);

								if (!rs11.next()) {

									break;
								} else {

									rs11.previous();
									continue;
								}
							}
						} else {
							subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
									0, "999,99,999.99", "", false, false),
									F10Normal));
							// System.out.println(" asd"+totinc+" fesf"+
							// totded);
							subcell.setBorderWidthRight(0);
							subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
							tab1.addCell(subcell);
							taxRepBean.getDoc().add(tab1);
						}
					}
				}

				//System.out.println("mediclaim..." + taxRepBean.getC5tot());
				totded += medcl > 30000 ? 30000 : medcl;// here taken sum of two
														// code..mediclaim for
														// self and
														// parent..(549) and
														// (550) if>30000..then
														// deduct max 30000

				taxRepBean.setC5tot(0);// here reset again.....
				tab1 = new PdfPTable(5);
				tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
						new Rectangle(100, 100));
				subcell = new PdfPCell(new Phrase("", F10Normal));
				subcell.setBorderWidthLeft(0);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);

				subcell = new PdfPCell(new Phrase("     d) U/S 80E", F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setColspan(4);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				taxRepBean.getDoc().add(tab1);

				String US80e = "SELECT * FROM CDMAST WHERE ITGRP LIKE '80E1' order by trncd";
				Statement st6_80e = repBean.getCn().createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ResultSet rs11111180e = st6_80e.executeQuery(US80e);
				if (rs11111180e.next()) {
					rs11111180e.previous();
					while (rs11111180e.next()) {
						tab1 = new PdfPTable(5);
						tab1.setWidthPercentage(new float[] { 10, 30, 20, 20,
								20 }, new Rectangle(100, 100));
						subcell = new PdfPCell(new Phrase("", F10Normal));
						subcell.setBorderWidthLeft(0);
						subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tab1.addCell(subcell);
						subcell = new PdfPCell(new Phrase("		"
								+ rs11111180e.getString("DISC"), F10Normal));
						subcell.setBorderWidthRight(0);
						subcell.setColspan(3);
						subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						tab1.addCell(subcell);

						Statement st2_80e = repBean.getCn().createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);

						buildsqlsumofquery(peno, prdt,
								rs11111180e.getString("ITGRP"), taxRepBean);
						ResultSet rs1180e = st2_80e.executeQuery(taxRepBean
								.getYtdstr());

						if (rs1180e.next()) {
							rs1180e.previous();

							while (rs1180e.next()) {

								subcell = new PdfPCell(new Phrase(
										UtilityDAO.trans(
												rs1180e.getInt("NET_AMT"),
												"999,99,999.99", "", false,
												false), F10Normal));
								// System.out.println(" asd"+totinc+" fesf"+
								// totded);
								subcell.setBorderWidthRight(0);
								subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								tab1.addCell(subcell);
								taxRepBean.setC5tot(taxRepBean.getC5tot()
										+ rs1180e.getInt("NET_AMT"));
								// System.out.println("tot this is bonus..."+tot499);
								// System.out.println("tot this is before bonus..."+totinc);

								// System.out.println("tot this is after bonus..."+totinc);
								taxRepBean.getDoc().add(tab1);
								totded += taxRepBean.getC5tot();
								taxRepBean.setC5tot(0);
								if (!rs1180e.next()) {

									break;
								} else {

									rs1180e.previous();
									continue;
								}
							}
						} else {
							subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
									0, "999,99,999.99", "", false, false),
									F10Normal));
							// System.out.println(" asd"+totinc+" fesf"+
							// totded);
							subcell.setBorderWidthRight(0);
							subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
							tab1.addCell(subcell);
							taxRepBean.getDoc().add(tab1);
						}
					}
				}

				taxRepBean.setC5tot(0);// here reset again.....
				tab1 = new PdfPTable(5);
				tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
						new Rectangle(100, 100));
				subcell = new PdfPCell(new Phrase("", F10Normal));
				subcell.setBorderWidthLeft(0);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);

				subcell = new PdfPCell(
						new Phrase(
								"     e) DONATION U/S 80 G (CM Relief fund)",
								F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setColspan(4);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				taxRepBean.getDoc().add(tab1);

				/*
				 * buildsql(peno, prdt, "AE1%",taxRepBean);//same here above
				 * reason printgrp(peno, prdt,repBean,taxRepBean);
				 */

				String US80G = "SELECT * FROM CDMAST WHERE ITGRP LIKE 'AE1%' order by trncd";
				Statement st6 = repBean.getCn().createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ResultSet rs111111 = st6.executeQuery(US80G);
				if (rs111111.next()) {
					rs111111.previous();
					while (rs111111.next()) {
						tab1 = new PdfPTable(5);
						tab1.setWidthPercentage(new float[] { 10, 30, 20, 20,
								20 }, new Rectangle(100, 100));
						subcell = new PdfPCell(new Phrase("", F10Normal));
						subcell.setBorderWidthLeft(0);
						subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tab1.addCell(subcell);
						subcell = new PdfPCell(new Phrase("		"
								+ rs111111.getString("DISC"), F10Normal));
						subcell.setBorderWidthRight(0);
						subcell.setColspan(3);
						subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						tab1.addCell(subcell);

						Statement st2 = repBean.getCn().createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);

						buildsqlsumofquery(peno, prdt,
								rs111111.getString("ITGRP"), taxRepBean);
						ResultSet rs11 = st2.executeQuery(taxRepBean
								.getYtdstr());

						if (rs11.next()) {
							rs11.previous();

							while (rs11.next()) {

								subcell = new PdfPCell(new Phrase(
										UtilityDAO.trans(
												rs11.getInt("NET_AMT"),
												"999,99,999.99", "", false,
												false), F10Normal));
								// System.out.println(" asd"+totinc+" fesf"+
								// totded);
								subcell.setBorderWidthRight(0);
								subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								tab1.addCell(subcell);
								taxRepBean.setC5tot(taxRepBean.getC5tot()
										+ rs11.getInt("NET_AMT"));
								// System.out.println("tot this is bonus..."+tot499);
								// System.out.println("tot this is before bonus..."+totinc);

								// System.out.println("tot this is after bonus..."+totinc);
								taxRepBean.getDoc().add(tab1);
								totded += taxRepBean.getC5tot();
								taxRepBean.setC5tot(0);
								if (!rs11.next()) {

									break;
								} else {

									rs11.previous();
									continue;
								}
							}
						} else {
							subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
									0, "999,99,999.99", "", false, false),
									F10Normal));
							// System.out.println(" asd"+totinc+" fesf"+
							// totded);
							subcell.setBorderWidthRight(0);
							subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
							tab1.addCell(subcell);
							taxRepBean.getDoc().add(tab1);
						}
					}
				}

				//System.out.println("DONATION U/S 80 G..."+ taxRepBean.getC5tot());
				
				taxRepBean.setC5tot(0);
				tab1 = new PdfPTable(5);
				tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
						new Rectangle(100, 100));
				subcell = new PdfPCell(new Phrase("", F10Normal));
				subcell.setBorderWidthLeft(0);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);

				subcell = new PdfPCell(new Phrase(
						"     f) DONATION U/S 80 G (Others)", F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setColspan(4);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				taxRepBean.getDoc().add(tab1);

				/*
				 * buildsql(peno, prdt, "AEA%",taxRepBean);//same here above
				 * reason printgrp(peno, prdt,repBean,taxRepBean);
				 */

				int amt551 = 0;
				int amt555 = 0;
				int amt556 = 0;
				int amt557 = 0;
				String US80GOthers = "SELECT * FROM CDMAST WHERE ITGRP LIKE '80G%' order by trncd";
				Statement st7 = repBean.getCn().createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ResultSet rs1111111 = st7.executeQuery(US80GOthers);
				if (rs1111111.next()) {
					rs1111111.previous();
					while (rs1111111.next()) {
						tab1 = new PdfPTable(5);
						tab1.setWidthPercentage(new float[] { 10, 30, 20, 20,
								20 }, new Rectangle(100, 100));
						subcell = new PdfPCell(new Phrase("", F10Normal));
						subcell.setBorderWidthLeft(0);
						subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tab1.addCell(subcell);
						subcell = new PdfPCell(new Phrase("		"
								+ rs1111111.getString("DISC"), F10Normal));
						subcell.setBorderWidthRight(0);
						subcell.setColspan(3);
						subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						tab1.addCell(subcell);

						Statement st2 = repBean.getCn().createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);

						buildsqlsumofquery(peno, prdt,
								rs1111111.getString("ITGRP"), taxRepBean);
						ResultSet rs11 = st2.executeQuery(taxRepBean
								.getYtdstr());

						if (rs11.next()) {
							rs11.previous();

							while (rs11.next()) {

								subcell = new PdfPCell(new Phrase(
										UtilityDAO.trans(
												rs11.getInt("NET_AMT"),
												"999,99,999.99", "", false,
												false), F10Normal));
								// System.out.println(" asd"+totinc+" fesf"+
								// totded);
								subcell.setBorderWidthRight(0);
								subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								tab1.addCell(subcell);
								if (rs1111111.getInt("TRNCD") == 551) {
									// donation 80G limit (currently no)
									amt551 += rs11.getInt("NET_AMT");
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ amt551);

								} else if (rs1111111.getInt("TRNCD") == 555) {
									// section 80GG limit 60000
									amt555 += rs11.getInt("NET_AMT");
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ amt555 > 60000 ? 60000 : amt555);
								} else if (rs1111111.getInt("TRNCD") == 556) {
									// section 80GGA limit you may insert
									// here(currently no)
									amt556 += rs11.getInt("NET_AMT");
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ amt556);
								} else if (rs1111111.getInt("TRNCD") == 557) {
									// section 80GGC limit you may insert
									// here(currently no)
									amt557 += rs11.getInt("NET_AMT");
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ amt557);
								}
								// System.out.println("tot this is bonus..."+tot499);
								// System.out.println("tot this is before bonus..."+totinc);
								totded += taxRepBean.getC5tot();// section wise
																// added.....to
																// totded
								taxRepBean.setC5tot(0);
								// System.out.println("tot this is after bonus..."+totinc);
								taxRepBean.getDoc().add(tab1);

								if (!rs11.next()) {

									break;
								} else {

									rs11.previous();
									continue;
								}
							}
						} else {
							subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
									0, "999,99,999.99", "", false, false),
									F10Normal));
							// System.out.println(" asd"+totinc+" fesf"+
							// totded);
							subcell.setBorderWidthRight(0);
							subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
							tab1.addCell(subcell);
							taxRepBean.getDoc().add(tab1);
						}
					}
				}

				//System.out.println("DONATION U/S 80 G (Others)..."+ taxRepBean.getC5tot());
				
				taxRepBean.setC5tot(0);// here reset again......
				tab1 = new PdfPTable(5);
				tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
						new Rectangle(100, 100));
				subcell = new PdfPCell(new Phrase("", F10Normal));
				subcell.setBorderWidthLeft(0);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);

				subcell = new PdfPCell(new Phrase(
						"     g) Income of Handicapped U/S 80 U", F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setColspan(4);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				taxRepBean.getDoc().add(tab1);

				/*
				 * buildsql(peno, prdt, "AF1%",taxRepBean);//same here above
				 * reason printgrp(peno, prdt,repBean,taxRepBean);
				 */

				String HandicappedUS80U = "SELECT * FROM CDMAST WHERE ITGRP LIKE '80U%' order by trncd";
				Statement st8 = repBean.getCn().createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ResultSet rs11111111 = st8.executeQuery(HandicappedUS80U);
				if (rs11111111.next()) {
					rs11111111.previous();
					while (rs11111111.next()) {
						tab1 = new PdfPTable(5);
						tab1.setWidthPercentage(new float[] { 10, 30, 20, 20,
								20 }, new Rectangle(100, 100));
						subcell = new PdfPCell(new Phrase("", F10Normal));
						subcell.setBorderWidthLeft(0);
						subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tab1.addCell(subcell);
						subcell = new PdfPCell(new Phrase("		"
								+ rs11111111.getString("DISC"), F10Normal));
						subcell.setBorderWidthRight(0);
						subcell.setColspan(3);
						subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						tab1.addCell(subcell);

						Statement st2 = repBean.getCn().createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);

						buildsqlsumofquery(peno, prdt,
								rs11111111.getString("ITGRP"), taxRepBean);
						ResultSet rs11 = st2.executeQuery(taxRepBean
								.getYtdstr());

						if (rs11.next()) {
							rs11.previous();

							while (rs11.next()) {

								subcell = new PdfPCell(new Phrase(
										UtilityDAO.trans(
												rs11.getInt("NET_AMT"),
												"999,99,999.99", "", false,
												false), F10Normal));
								// System.out.println(" asd"+totinc+" fesf"+
								// totded);
								subcell.setBorderWidthRight(0);
								subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
								tab1.addCell(subcell);
								taxRepBean.setC5tot(taxRepBean.getC5tot()
										+ rs11.getInt("NET_AMT"));
								// System.out.println("tot this is bonus..."+tot499);
								// System.out.println("tot this is before bonus..."+totinc);
								totded += taxRepBean.getC5tot();// section wise
																// added.....to
																// totded
								taxRepBean.setC5tot(0);
								// System.out.println("tot this is after bonus..."+totinc);
								taxRepBean.getDoc().add(tab1);

								if (!rs11.next()) {

									break;
								} else {

									rs11.previous();
									continue;
								}
							}
						} else {
							subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
									0, "999,99,999.99", "", false, false),
									F10Normal));
							// System.out.println(" asd"+totinc+" fesf"+
							// totded);
							subcell.setBorderWidthRight(0);
							subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
							tab1.addCell(subcell);
							taxRepBean.getDoc().add(tab1);
						}
					}
				}
				
				//System.out.println("ddddddddddddddddddddddd..."+ taxRepBean.getC5tot());
				
				taxRepBean.setC5tot(0);
				tab1 = new PdfPTable(5);
				tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
						new Rectangle(100, 100));
				subcell = new PdfPCell(new Phrase("", F10Normal));
				subcell.setBorderWidthLeft(0);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				subcell = new PdfPCell(new Phrase(
						"   Qualifying Deduction under chapter VI-A ",
						F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setColspan(3);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				subcell = new PdfPCell(new Phrase(UtilityDAO.trans(totded,
						"999,99,999.99", "", false, false), F10Normal));
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				subcell.setBorderWidthRight(0);
				tab1.addCell(subcell);
				taxRepBean.getDoc().add(tab1);

				// DED80 = taxRepBean.getC5tot();//new totded by section wise
				DED80 = totded;
				tab1 = new PdfPTable(5);
				tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
						new Rectangle(100, 100));
				subcell = new PdfPCell(new Phrase("", F10Normal));
				subcell.setBorderWidthLeft(0);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);

				subcell = new PdfPCell(
						new Phrase(
								" ( V ) NET TAXABLE INCOME <a>before W.O.(i.e. III - IV ) :- ",
								F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setColspan(3);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);

				totamt = totamt - DED80;

				subcell = new PdfPCell(new Phrase(UtilityDAO.trans(totamt,
						"999,99,999.99", "", false, false), F10Normal));
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				subcell.setBorderWidthRight(0);
				tab1.addCell(subcell);
				taxRepBean.getDoc().add(tab1);

				tab1 = new PdfPTable(5);
				tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
						new Rectangle(100, 100));
				subcell = new PdfPCell(new Phrase("", F10Normal));
				subcell.setBorderWidthLeft(0);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);

				subcell = new PdfPCell(
						new Phrase(
								"  (Rounded off to nearest Rs.10) <b>after W.O.           :- ",
								F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setColspan(3);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
						Math.round(totamt), "999,99,999.99", "", false, false),
						F10Normal));
				
				System.out.println("TEST totamt : "+totamt);
				
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				subcell.setBorderWidthRight(0);
				tab1.addCell(subcell);
				taxRepBean.getDoc().add(tab1);

				int totchkamt = 0;
				gross_tax_amt = totamt;
				if (sex.equalsIgnoreCase("F")) // if total income is > 350000
												// then tax is minimum of 2500
												// or tax on income otherwise it
												// would be 5 % if income is
												// <500000....@ni
				{
					// totamt = Math.round(Chk_Slab1(575, prdt, totamt,
					// 2,repBean)); for female /male by emp_category we can diff
					// in slab
					totchkamt = Math.round(Chk_Slab1(599, prdt, totamt, 0,
							repBean)); // so 1st checked income is greather or
										// not ... according it takes slab...
					if (totamt <= totchkamt) {
						totamt = Math.round(Chk_Slab1(575, prdt, totamt, 1,
								repBean));
					} else {
						totamt = Math.round(Chk_Slab1(575, prdt, totamt, 0,
								repBean));
					}
				} else {
					totchkamt = Math.round(Chk_Slab1(599, prdt, totamt, 0,
							repBean));
					if (totamt <= totchkamt) {
						totamt = Math.round(Chk_Slab1(575, prdt, totamt, 1,
								repBean));
					} else {
						totamt = Math.round(Chk_Slab1(575, prdt, totamt, 0,
								repBean));
					}
				}

				// for rebate 2500... dedcted if tax>2500 otherwise 0 tax..
				if (gross_tax_amt <= totchkamt) {
					// totamt=totamt>2500?totamt-2500:0;
				}
				tab1 = new PdfPTable(5);
				tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
						new Rectangle(100, 100));
				subcell = new PdfPCell(new Phrase("", F10Normal));
				subcell.setBorderWidthLeft(0);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);

				subcell = new PdfPCell(
						new Phrase(
								"  ( VI ) INCOME TAX PAYABLE ON [ (V)(b) ] ABOVE          :- ",
								F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setColspan(3);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				subcell = new PdfPCell(new Phrase(UtilityDAO.trans(totamt,
						"999,99,999.99", "", false, false), F10Normal));
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				subcell.setBorderWidthRight(0);
				tab1.addCell(subcell);
				taxRepBean.getDoc().add(tab1);

				taxRepBean.setTax_cal(totamt);

				totamt = Math.round(Calculate.checkSlab(498, prdt,
						gross_tax_amt, 1, peno, repBean.getCn()));
				if (totamt == 100) {
					totamt = taxRepBean.getTax_cal();
				}
				if (totamt == 2240) {
					totamt = taxRepBean.getTax_cal() - (gross_tax_amt - 100000);
				}

				taxRepBean.setC4tot(0);
				taxRepBean.setC5tot(0);
				taxRepBean.setC3tot(0);
				/*
				 * buildsql(peno, prdt, "AJ%",taxRepBean); printgrp(peno,
				 * prdt,repBean,taxRepBean);
				 */// not display here ... needs to deduct from total income...
					// so included under 80c.... itgrp chng AC1,AC2 ect..then
					// will be there in 80C.....@ni

				taxRepBean.setNet_tax(taxRepBean.getTax_cal());
				if (taxRepBean.getNet_tax() < 0) {
					taxRepBean.setNet_tax(0);
				}
				tab1 = new PdfPTable(5);
				tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
						new Rectangle(100, 100));
				subcell = new PdfPCell(new Phrase("", F10Normal));
				subcell.setBorderWidthLeft(0);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);

				subcell = new PdfPCell(new Phrase(
						"  ( X ) NET TAX PAYABLE(i.e. VI - IX  ) 111 ", F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setColspan(3);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
						taxRepBean.getNet_tax(), "999,99,999.99", "", false,
						false), F10Normal));
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				subcell.setBorderWidthRight(0);
				tab1.addCell(subcell);
				
				System.out.println("TEST TAX :"+taxRepBean.getNet_tax());

				if (taxRepBean.getNet_tax() > 0) {
					System.out.println("here is the rebate added if taxble<slab of 599 then");
					System.out.println(gross_tax_amt+"--------"+totchkamt);
					if (gross_tax_amt <= totchkamt) {
						// here is the rebate added if taxble<slab of 599 then
						subcell = new PdfPCell(new Phrase("", new Font(
								Font.TIMES_ROMAN, 10, Font.NORMAL)));
						subcell.setBorderWidthLeft(0);
						subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						tab1.addCell(subcell);

						subcell = new PdfPCell(new Phrase(
								"              Rebate    ", new Font(
										Font.TIMES_ROMAN, 10, Font.NORMAL)));
						subcell.setBorderWidthRight(0);
						subcell.setColspan(3);
						subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						tab1.addCell(subcell);
						subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
								12500, "999,99,999.99", "", false, false),
								new Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
						subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						subcell.setBorderWidthRight(0);
						tab1.addCell(subcell);

						// new total tax with education cess and dedduct the
						// rebate
						taxRepBean
								.setNet_tax(((taxRepBean.getNet_tax()) <= 12500 ? 0
										: (taxRepBean.getNet_tax() - 12500)));
						System.out.println("NET TAX--"+taxRepBean.getNet_tax());
					} else {
						taxRepBean.setNet_tax(taxRepBean.getNet_tax());
					}
				}
				taxRepBean.getDoc().add(tab1);
				st.executeUpdate("UPDATE F16 SET TOT_TAX = "
						+ taxRepBean.getNet_tax() + " WHERE EMPNO = " + peno);
				// '---------------------------------------------------------
				taxRepBean.setSercharge(0);
				if (taxRepBean.getNet_tax() > 0) {
					// education cess is 3% on total tax payble...so cmnt old
					// line & add new line..to calc edu cess by 3%
					// taxRepBean.setSercharge(Math.round(Calculate.checkSlab(901,
					// prdt, taxRepBean.getNet_tax(), 1,
					// peno,repBean.getCn())));
					taxRepBean.setSercharge((int) Math.round(taxRepBean
							.getNet_tax() * 0.02));
					taxRepBean
							.setSercharge(taxRepBean.getSercharge()
									+ ((int) Math.round(taxRepBean.getNet_tax() * 0.01)));
					taxRepBean
							.setSercharge(taxRepBean.getSercharge()
									+ ((int) Math.round(taxRepBean.getNet_tax() * 0.01)));

				}
				tab1 = new PdfPTable(5);
				tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
						new Rectangle(100, 100));
				subcell = new PdfPCell(new Phrase("", F10Normal));
				subcell.setBorderWidthLeft(0);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				subcell = new PdfPCell(new Phrase(
						"          Add Education Cess@4%  ", F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setColspan(3);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
						taxRepBean.getSercharge(), "999,99,999.99", "", false,
						false), F10Normal));
				
				System.out.println("TEST CESS : "+taxRepBean.getSercharge());
				
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				subcell.setBorderWidthRight(0);
				tab1.addCell(subcell);
				taxRepBean.getDoc().add(tab1);

				st.executeUpdate("UPDATE F16 SET ECESS = "
						+ taxRepBean.getSercharge() + " WHERE EMPNO = " + peno);

				if (taxRepBean.getSercharge() > 0) {
					tab1 = new PdfPTable(5);
					tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
							new Rectangle(100, 100));
					subcell = new PdfPCell(new Phrase("", new Font(
							Font.TIMES_ROMAN, 10, Font.NORMAL)));
					subcell.setBorderWidthLeft(0);
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tab1.addCell(subcell);

					subcell = new PdfPCell(new Phrase(
							"              Total    ", new Font(
									Font.TIMES_ROMAN, 10, Font.NORMAL)));
					subcell.setBorderWidthRight(0);
					subcell.setColspan(3);
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tab1.addCell(subcell);
					subcell = new PdfPCell(new Phrase(
							UtilityDAO.trans(taxRepBean.getNet_tax()
									+ taxRepBean.getSercharge(),
									"999,99,999.99", "", false, false),
							new Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
					subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					subcell.setBorderWidthRight(0);
					tab1.addCell(subcell);

					taxRepBean.setNet_tax(taxRepBean.getNet_tax()
							+ taxRepBean.getSercharge());
					taxRepBean.getDoc().add(tab1);

				}
				int tax = taxRepBean.getNet_tax();
				// this is rebate amount if tax >2500
				// taxRepBean.setNet_tax(taxRepBean.getNet_tax()>2500?taxRepBean.getNet_tax()-2500:taxRepBean.getNet_tax());
				/*
				 * if(taxRepBean.getNet_tax()>0){ tab1 = new PdfPTable(5);
				 * tab1.setWidthPercentage(new float[]{10,30,20,20,20},new
				 * Rectangle(100,100)); subcell = new PdfPCell(new Phrase("",
				 * new Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
				 * subcell.setBorderWidthLeft(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell);
				 * 
				 * subcell = new PdfPCell(new Phrase(" Section 87A Rebate", new
				 * Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
				 * subcell.setBorderWidthRight(0); subcell.setColspan(3);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell);
				 * taxRepBean.setNet_tax(taxRepBean.getNet_tax
				 * ()<=2500?0:taxRepBean.getNet_tax()-2500); subcell = new
				 * PdfPCell(new Phrase(UtilityDAO.trans(2500, "999,99,999.99",
				 * "", false, false), new Font(Font.TIMES_ROMAN, 10,
				 * Font.NORMAL)));
				 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * subcell.setBorderWidthRight(0); tab1.addCell(subcell);
				 * taxRepBean.getDoc().add(tab1); }
				 */

				taxRepBean.setC4tot(0);
				taxRepBean.setC5tot(0);
				taxRepBean.setC3tot(0);
				tab1 = new PdfPTable(5);
				tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
						new Rectangle(100, 100));
				subcell = new PdfPCell(new Phrase("", new Font(
						Font.TIMES_ROMAN, 10, Font.NORMAL)));
				subcell.setBorderWidthLeft(0);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				subcell = new PdfPCell(new Phrase(
						"  ( XI ) TDS FROM SALARY / TAX PAID ", new Font(
								Font.TIMES_ROMAN, 10, Font.NORMAL)));
				subcell.setBorderWidthRight(0);
				subcell.setColspan(3);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				int tds = 0;
				buildsqlsumofquery(peno, prdt, "TDS", taxRepBean); // this is
																	// for tds
																	// calculation
																	// in
																	// acadmic
																	// year...228
																	// sum
				// printgrp(peno, prdt,repBean,taxRepBean);
				ResultSet rs1 = st.executeQuery(taxRepBean.getYtdstr());
				if (rs1.next()) {

					rs1.previous();
					while (rs1.next()) {
						tds += rs1.getInt("NET_AMT");
					}

					subcell = new PdfPCell(new Phrase(UtilityDAO.trans(tds,
							"999,99,999.99", "", false, false), F10Normal));
					// System.out.println(" asd"+totinc+" fesf"+ totded);
					subcell.setBorderWidthRight(0);
					subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tab1.addCell(subcell);

					taxRepBean.setTAXPAID(tds);
					// System.out.println("this is tax paid now0000........"+taxRepBean.getTAXPAID());
					taxRepBean.setNet_tax(taxRepBean.getNet_tax()
							- taxRepBean.getTAXPAID());
					// System.out.println("this is final tax0000........"+taxRepBean.getNet_tax());

					// taxRepBean.getDoc().add(tab1);
				} else {
					subcell = new PdfPCell(new Phrase(UtilityDAO.trans(tds,
							"999,99,999.99", "", false, false), new Font(
							Font.TIMES_ROMAN, 10, Font.NORMAL)));
					subcell.setBorderWidthRight(0);
					subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tab1.addCell(subcell);

					taxRepBean.setTAXPAID(tds);
					System.out.println("this is tax paid now9999........"
							+ taxRepBean.getTAXPAID());
					taxRepBean.setNet_tax(taxRepBean.getNet_tax()
							- taxRepBean.getTAXPAID());
					System.out.println("this is final tax9999........"
							+ taxRepBean.getNet_tax());

					// taxRepBean.getDoc().add(tab1);
				}

				taxRepBean.getDoc().add(tab1);

				buildsql(peno, prdt, "BB%", taxRepBean);
				printgrp(peno, prdt, repBean, taxRepBean);

				taxRepBean.setTAXPAID(taxRepBean.getC5tot());

				st.executeUpdate("UPDATE F16 SET tds = "
						+ taxRepBean.getTAXPAID() + " WHERE EMPNO = " + peno);

				tab1 = new PdfPTable(5);
				tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
						new Rectangle(100, 100));
				subcell = new PdfPCell(new Phrase("", new Font(
						Font.TIMES_ROMAN, 10, Font.NORMAL)));
				subcell.setBorderWidthLeft(0);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				subcell = new PdfPCell(
						new Phrase(
								" ( XII ) BALANCE TAX PAYABLE /REFUNDABLE ( X - XI ) :",
								new Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
				subcell.setBorderWidthRight(0);
				subcell.setColspan(3);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				taxRepBean.setTAXPAID(taxRepBean.getNet_tax()
						- (taxRepBean.getTAXPAID() + taxRepBean.getTAXPAID()));
				subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
						taxRepBean.getNet_tax(), "999,99,999.99", "", false,
						false), new Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
				subcell.setColspan(3);
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				subcell.setBorderWidthRight(0);
				tab1.addCell(subcell);
				taxRepBean.getDoc().add(tab1);

				st.executeUpdate("UPDATE F16 SET bal_tax = "
						+ taxRepBean.getNet_tax() + " WHERE EMPNO = " + peno);

				para = new Paragraph(new Phrase("* * *  N O T E S * * *",
						new Font(Font.TIMES_ROMAN, 10)));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingBefore(5);
				taxRepBean.getDoc().add(para);
				para = new Paragraph(
						new Phrase(
								"1) Please send details of investment made or projected along with Xerox copies of the same for the period",
								new Font(Font.TIMES_ROMAN, 10)));
				para.setAlignment(Element.ALIGN_LEFT);
				taxRepBean.getDoc().add(para);

				if (taxRepBean.getNet_tax() > 0) {
					tab1 = new PdfPTable(5);
					tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
							new Rectangle(100, 100));
					subcell = new PdfPCell(new Phrase("", new Font(
							Font.TIMES_ROMAN, 10, Font.NORMAL)));
					subcell.setBorderWidthLeft(0);
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tab1.addCell(subcell);

					subcell = new PdfPCell(
							new Phrase(
									"   Otherwise Tax will be deducted from your salary as under :",
									new Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
					subcell.setBorderWidthRight(0);
					subcell.setColspan(4);
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tab1.addCell(subcell);
					taxRepBean.getDoc().add(tab1);

					tab1 = new PdfPTable(5);
					tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
							new Rectangle(100, 100));
					subcell = new PdfPCell(new Phrase("", new Font(
							Font.TIMES_ROMAN, 10, Font.NORMAL)));
					subcell.setBorderWidthLeft(0);
					subcell.setColspan(2);
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tab1.addCell(subcell);

					subcell = new PdfPCell(new Phrase(
							"  Total Tax Payable Rs.", new Font(
									Font.TIMES_ROMAN, 10, Font.NORMAL)));
					subcell.setBorderWidthRight(0);
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tab1.addCell(subcell);

					subcell = new PdfPCell(new Phrase("No of Months", new Font(
							Font.TIMES_ROMAN, 10, Font.NORMAL)));
					subcell.setBorderWidthRight(0);
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tab1.addCell(subcell);

					subcell = new PdfPCell(new Phrase(
							"Tax to be deducted per Month (Rs.)", new Font(
									Font.TIMES_ROMAN, 10, Font.NORMAL)));
					subcell.setBorderWidthRight(0);
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tab1.addCell(subcell);
					taxRepBean.getDoc().add(tab1);

					tab1 = new PdfPTable(5);
					tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
							new Rectangle(100, 100));
					subcell = new PdfPCell(new Phrase("", new Font(
							Font.TIMES_ROMAN, 10, Font.NORMAL)));
					subcell.setBorderWidthLeft(0);
					subcell.setColspan(2);
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tab1.addCell(subcell);

					subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
							taxRepBean.getNet_tax(), "999,99,999.99", "",
							false, false), new Font(Font.TIMES_ROMAN, 10,
							Font.NORMAL)));
					subcell.setBorderWidthRight(0);
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tab1.addCell(subcell);

					subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
							taxRepBean.getProjMonth(), "999,99,999.99", "",
							false, false), new Font(Font.TIMES_ROMAN, 10,
							Font.NORMAL)));
					subcell.setBorderWidthRight(0);
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tab1.addCell(subcell);

					if (taxRepBean.getProjMonth() > 0) {
						subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
								(taxRepBean.getNet_tax() / taxRepBean
										.getProjMonth()), "999,99,999.99", "",
								false, false), new Font(Font.TIMES_ROMAN, 10,
								Font.NORMAL)));
						subcell.setBorderWidthRight(0);
						subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						tab1.addCell(subcell);

					} else {
						subcell = new PdfPCell(new Phrase("", new Font(
								Font.TIMES_ROMAN, 10, Font.NORMAL)));
						subcell.setBorderWidthRight(0);
						subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						tab1.addCell(subcell);
					}
					taxRepBean.getDoc().add(tab1);
				}

				para = new Paragraph(
						new Phrase(
								"IF YOU HAVE ALREADY SENT DETAILS OF INVESTMENT, PLEASE IGNORE THIS LETTER.        ",
								new Font(Font.TIMES_ROMAN, 10)));
				para.setAlignment(Element.ALIGN_LEFT);
				taxRepBean.getDoc().add(para);
				para = new Paragraph(
						new Phrase(
								"2) There may be increase in amount of tax payable due to DA arrears,"
										+ "Leave Encashment, Leave surrender,Closing allowance etc.",
								new Font(Font.TIMES_ROMAN, 10)));
				para.setAlignment(Element.ALIGN_LEFT);
				taxRepBean.getDoc().add(para);
				para = new Paragraph(
						new Phrase(
								"THE FIG. OF HOUSING INT.IS ON ACCRUED BASIS & IT WORKSHEET IS TENTATIVE.",
								new Font(Font.TIMES_ROMAN, 10)));
				para.setAlignment(Element.ALIGN_LEFT);
				taxRepBean.getDoc().add(para);
				para = new Paragraph(new Phrase("AGM (PERSONNEL)", new Font(
						Font.TIMES_ROMAN, 10)));
				para.setSpacingBefore(5);
				para.setAlignment(Element.ALIGN_RIGHT);
				taxRepBean.getDoc().add(para);

				taxRepBean.getDoc().newPage();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {

		}

	}

	public static float Chk_Slab1(int trncd, String dt, int WrkAmt,
			int emp_type, RepoartBean repBean) {
		float result = 0.00F;
		ResultSet slb = null;
		float slbamt = 0.00F;
		float Cal_Wrk_amt = 0.00f;
		String slbstr = "";

		if (emp_type == 4) {
			emp_type = 0;
		}

		slbstr = " select * from slab where emp_cat = " + emp_type
				+ " and trncd = " + trncd + " and effdate = "
				+ "( select max(s.effdate) from slab s where s.emp_cat = "
				+ emp_type + " and  trncd = " + trncd + " and effdate >= '"
				+ dt + "' )  " + " order by srno ";
		//System.out.println("slbstr:  "+slbstr);
		if (trncd == 101) {
			return WrkAmt;
		}
		if (WrkAmt == 0) {
			return 0;
		}
		try {
			Cal_Wrk_amt = WrkAmt;
			Statement st = repBean.getCn().createStatement();
		
			/***************************add standard deduction*****************************************************************************************/
						//WrkAmt=WrkAmt-50000;
			/***************************add standard deduction*****************************************************************************************/			
			
			System.out.println("aniket...slab...575..workamt" + WrkAmt);

			slb = st.executeQuery(slbstr);
			while (slb.next()) {
				if (slb.getInt("TOAMT") < WrkAmt) {
					slbamt = slbamt + slb.getFloat("FIXAMT");
				} else {
					slbamt += slb.getFloat("FIXAMT")
							+ ((WrkAmt - slb.getFloat("FRMAMT"))
									* slb.getFloat("PER") / 100);
									
				}
				if ((slb.getString("MINAMT") == null ? 0 : slb.getFloat("MINAMT")) != 0) {
					if (slbamt < slb.getFloat("MINAMT")) {
						slbamt = slb.getFloat("MINAMT");
					}
				}
				if ((slb.getString("MAXAMT") == null ? 0 : slb.getFloat("MAXAMT")) != 0) {
					if (slbamt > slb.getFloat("MAXAMT")) {
						slbamt = slb.getFloat("MAXAMT");
					}
				}

				if (slb.getFloat("TOAMT") > WrkAmt)
					
					break;
				System.out.println("aniket...slab...575..slbamt" + slbamt);
				/* Cal_Wrk_amt = Cal_Wrk_amt - slb.getInt("TOAMT"); */
			}
			
			System.out.println("aniket...slab...575..return slbamt" + slbamt);

			result = slbamt;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static void printgrp1(int peno, String prdt, String pename,
			String desigcd, String PAN_NO, String SEX1, java.sql.Date pdob,
			RepoartBean repBean, TaxReportBean taxRepBean) {

		int ptotamt = 0;
		int reb88d = 0;
		int via = 0;
		int finctot = 0;
		int q80cc = 0;
		int q80d = 0;
		int q80e = 0;
		int q80gPM = 0;
		int q80g = 0;
		int q80gG = 0;
		int q80l = 0;
		int q80u = 0;

		int q80ccf = 0;
		int tmpamt = 0;
		int caltax = 0;
		int ftotinc = 0;
		int inctot = 0;
		int b88 = 0;
		int c88 = 0;
		int us89 = 0;
		int tfee = 0;
		int qtfee = 0;
		int pf = 0;
		int ppf = 0;
		int lic = 0;
		int hl = 0;
		int uti = 0;
		int nsc = 0;
		int nss = 0;
		int fpf = 0;
		int nscint = 0;
		int infra = 0;
		int qpf = 0;
		int qppf = 0;
		int qlic = 0;
		int qhl = 0;
		int quti = 0;
		int qnsc = 0;
		int qnss = 0;
		int qfpf = 0;
		int qnscint = 0;
		int us10 = 0;
		int qinfra = 0;
		int tot88 = 0;
		int qtot88 = 0;
		int educess = 0;
		ResultSet rsTmp = null;
		ResultSet trs = null;
		String vPlace = "";
		String msql = "select disc from param_tab where code = 'PLACE' and status = 1";
		try {
			Statement st = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rsTmp = st.executeQuery(msql);
			if (!rsTmp.next()) {
				System.out.println("Place Not Found In Parameter");
				return;
			}

			PdfPTable tab1 = new PdfPTable(4);
			// tab1.setSpacingBefore(2);
			tab1.setWidthPercentage(new float[] { 40, 15, 15, 30 },
					new Rectangle(100, 100));

			PdfPCell subcell = new PdfPCell(new Phrase("1.  Gross salary",
					F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthBottom(0);

			tab1.addCell(subcell);

			taxRepBean.setC4tot(0);
			taxRepBean.setC5tot(0);
			taxRepBean.setC3tot(0);
			taxRepBean.setTotamt(0);
			buildsqlF16(peno, prdt, "A1%", taxRepBean);
			printgrpF16(peno, prdt, repBean, taxRepBean);

			taxRepBean.setTotinc(taxRepBean.getC5tot());
			taxRepBean.setC4tot(0);
			taxRepBean.setC5tot(0);
			taxRepBean.setC3tot(0);
			taxRepBean.setTotamt(0);
			buildsqlF16(peno, prdt, "A2%", taxRepBean);
			printgrpF16(peno, prdt, repBean, taxRepBean);
			System.out.println("this is A22222222 amt===>"+taxRepBean.getC5tot()+" and ttttt==>"+taxRepBean.getTotamt()+" ttttt11==>"+taxRepBean.getC4tot()+" and ttttt222==>"+taxRepBean.getC3tot());
			taxRepBean.setTotinc(taxRepBean.getTotinc()+taxRepBean.getC3tot());
			taxRepBean
					.setStr1(" SELECT Y.TRNCD,SUM(NET_AMT) net_amt,sum(inp_amt) inp_amt "
							+ " FROM YTDTRAN Y "
							+ " Where y.trncd = 120 and  Y.empno = "
							+ peno
							+ " AND Y.TRNDT BETWEEN '"
							+ ReportDAO.Boy(prdt)
							+ "'  AND '"
							+ ReportDAO.EOM(prdt)
							+ "' group by trncd");
			taxRepBean.setVperk_amt(0);
			trs = null;
			Statement st3 = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			trs = st3.executeQuery(taxRepBean.getStr1());
			if (trs.next()) {

				if (trs.getString("net_amt") == null) {
					taxRepBean.setVperk_amt(trs.getInt("net_amt"));
				} else {
					taxRepBean.setVperk_amt(trs.getInt("net_amt"));
				}
			}
			taxRepBean.setTotinc(taxRepBean.getTotinc()
					- taxRepBean.getVperk_amt());

			// this is for adding leave salary into income
			/*taxRepBean
					.setStr1(" SELECT Y.TRNCD,isnull(SUM(NET_AMT),0) net_amt,isnull(sum(inp_amt),0) inp_amt "
							+ " FROM YTDTRAN Y "
							+ " Where y.trncd = 145 and  Y.empno = "
							+ peno
							+ " AND Y.TRNDT BETWEEN '"
							+ ReportDAO.BoFinancialy(prdt)
							+ "'  AND '"
							+ ReportDAO.EoFinancialy(prdt) + "' group by trncd");
			System.out.println("leave sal........." + taxRepBean.getStr1());
			trs = null;
			Statement st31 = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			trs = st31.executeQuery(taxRepBean.getStr1());
			int leave_pf_amt = 0;
			if (trs.next()) {

				if (trs.getString("inp_amt") == null) {
					taxRepBean.setTotinc(taxRepBean.getTotinc() + 0);
				} else {
					taxRepBean.setTotinc(taxRepBean.getTotinc()
							+ trs.getInt("inp_amt"));
					leave_pf_amt = ((trs.getInt("inp_amt") * 12) / 100);
				}
			} else {
				taxRepBean.setTotinc(taxRepBean.getTotinc() + 0);
			}*/
			
			
			int leaveencashamt = 0;
			int leavebalnce = 0;
			int old_encash_day = 0;
			//System.out.println("start leave encash...");
			Statement stleave = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Statement encashst = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Statement encashst1 = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rsleave1 = null;
			ResultSet rsleave2 = null;
			ResultSet rsleave3 = null;
			String EncashBal =""
					+ "(select SUM(DAYS) as DAYS from leavetran where empno="+peno+" "
					+ "and LEAVECD=1 and LREASON='Leave encash' and TRNTYPE='D' and LEAVEPURP=4 "
					+ "and TRNDATE between (SELECT (convert(nvarchar(5), YEAR(getdate()))+'-01-01')) and (SELECT (convert(nvarchar(5), YEAR(getdate()))+'-12-31')))";
			rsleave3 = encashst.executeQuery(EncashBal);
			if (rsleave3.next()) {
				leavebalnce = rsleave3.getInt("DAYS");
			}
			else{
			String encash_days = (" SELECT Ceiling ((SELECT crlim FROM   leavemass "
					+ " WHERE  leavecd = 1 AND status = 'A') - (SELECT CASE when Isnull(Sum(net_amt),0)<24 "
					+ " then 0 else Isnull(Sum(net_amt),0) "
					+ " end as Net_amt FROM   paytran_stage "
					+ " WHERE  trncd IN ( 301, 302 ) "
					+ "AND trndt BETWEEN   '01-Jan-"+ReportDAO.Boy(prdt).substring(7, 11)+"' AND  '31-Dec-"+ReportDAO.Boy(prdt).substring(7, 11)+"' "
					+ " AND empno = " + peno + " ) * 2.5 * 12  / 365)  AS finalDays ");
			System.out.println("creadit old days..." + encash_days);
			rsleave1 = encashst.executeQuery(encash_days);
			if (rsleave1.next()) {
				old_encash_day = rsleave1.getInt("finalDays");
				
			} else {
				old_encash_day = 0;
			}
			System.out.println("TO be creadited=="+old_encash_day);
			// chnged month here.... make it 01-01-year later when creadit leave
			// here taken new encashed days and then minus leaves taken in that current year
			/*String checkbal = ("if exists (select EMPNO,DAYS as encash_days from leavetran "
					+ "where LEAVECD=1 and TRNDATE between '"
					+ ReportDAO.Boy(prdt).substring(7, 11)
					+ "-01-01' and '"
					+ ReportDAO.Boy(prdt).substring(7, 11)
					+ "-12-31' "
					+ "and LEAVEPURP=4 and STATUS='ENCASHED' and TRNTYPE='D' and empno="
					+ peno
					+ ") "
					+ " select EMPNO,DAYS-(SELECT Isnull(Sum(days), 0) AS usedleave " + 
					"FROM   leavetran " + 
					"WHERE  empno = "+peno+" " + 
					"AND TRNDATE between '"+ReportDAO.Boy(prdt).substring(7, 11)+"-01-01' and '"+ReportDAO.Boy(prdt).substring(7, 11)+"-12-31' " +
					"AND leavecd = 1 " + 
					"AND status = 'SANCTION' " + 
					"AND trntype = 'D' ) as encash_days from leavetran "
					+ " where LEAVECD=1 and TRNDATE between '"
					+ ReportDAO.Boy(prdt).substring(7, 11)
					+ "-01-01' and '"
					+ ReportDAO.Boy(prdt).substring(7, 11)
					+ "-12-31' "
					+ " and LEAVEPURP=4 and STATUS='ENCASHED' and TRNTYPE='D' and empno="
					+ peno + " " + " else " + " (select 0 as encash_days)");*/
			/*
			 * String checkbal=(
			 * "select isnull(SUM(bal),0) as leavebalance from leavebal where EMPNO="
			 * +peno+" and SRNO = "+
			 * "(select MAX(SRNO) from leavebal where EMPNO="
			 * +peno+" and LEAVECD=1 ) and  "+
			 * " BALDT between '"+ReportDAO.BoFinancialy
			 * (prdt)+"' and '"+ReportDAO.EoFinancialy(prdt)+"' and LEAVECD=1");
			 */
			/*System.out.println("checking leavebalnce...." + checkbal);
			ResultSet leavebal = stleave.executeQuery(checkbal);
			if (leavebal.next()) {
				leavebalnce = leavebal.getInt("encash_days") > 90 ? leavebal
						.getInt("encash_days") - 90 : leavebal
						.getInt("encash_days");

				if (leavebalnce == 0) {*/
					String oldbalcheck = ("SELECT bal+" + old_encash_day
							+ " AS encash_days " + "FROM   leavebal "
							+ " WHERE  empno = " + peno + " "
							+ " AND leavecd = 1 "
							+ "and srno = ( SELECT top 1 (srno) "
							+ "FROM   leavebal " + "WHERE  empno = " + peno
							+ " " + " AND leavecd = 1 order by baldt desc,srno desc)");
					rsleave2 = encashst1.executeQuery(oldbalcheck);
					if (rsleave2.next()) {
						leavebalnce = rsleave2.getInt("encash_days") > 90 ? rsleave2
								.getInt("encash_days") - 90 : 0;
					} else {
						leavebalnce = 0;
					}
			}	
				/*} else if(leavebalnce>0) {
					leavebalnce = leavebal.getInt("encash_days") > 90 ? leavebal
							.getInt("encash_days") - 90 : leavebal
							.getInt("encash_days");
				}else {
					leavebalnce = 0;
				}

			} else {
				leavebalnce = 0;
			}*/
			//System.out.println("Encash leavebalnce......" + leavebalnce);
			//System.out.println("system year..." + ReportDAO.getServerDate());
			//System.out.println("prdt year..."+ (Integer.parseInt(prdt.substring(7, 11))));
			String format = "dd-MM-yyyy";
			String curdt = ReportDAO.getServerDate();
			String month1[] = curdt.split("-");
			//System.out.println("month...." + month1[1]);
			int monthno = ReportDAO.getMonth(ReportDAO.getServerDate());
			//System.out.println("month no...." + monthno);
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date dateObj3 = sdf.parse(month1[0] + "-" + String.valueOf(monthno)
					+ "-" + month1[2]);
			Date dateObj4 = sdf.parse("31-03-" + prdt.substring(7, 11));
			String bsicdavda_year = "";
			if (dateObj3.getTime() > dateObj4.getTime()) {
				bsicdavda_year = String.valueOf((Integer.parseInt(prdt
						.substring(7, 11))));
			} else {
				bsicdavda_year = String.valueOf((Integer.parseInt(prdt
						.substring(7, 11)) - 1));
			}
			// this is for taking basic da vda of mar of acadmic start
			// year...@ni//this is logic is also working but above makes it
			// simple
			/*
			 * String bsicdavda_year =
			 * String.valueOf((Integer.parseInt(ReportDAO
			 * .getServerDate().substring(7, 11))) > (Integer
			 * .parseInt(prdt.substring(7, 11))) ? (Integer
			 * .parseInt(ReportDAO.getServerDate().substring(7, 11)) - 1) :
			 * (Integer.parseInt(prdt.substring(7, 11))));
			 */
			
			//System.out.println("this is the encash date....." + "31-MAR-"+ bsicdavda_year);
			
			String leave_encsh = ("if exists (select * from paytran where TRNCD in(101,102,138) and EMPNO="
					+ peno
					+ " and trndt = '"
					+ ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"
							+ bsicdavda_year))
					+ "' ) "
					+ "(select round(((SUM(INP_AMT)/30)*"
					+ leavebalnce
					+ "),0) as encash from paytran where TRNCD in(101,102,138) and EMPNO="
					+ peno
					+ " and trndt = '"
					+ ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"
							+ bsicdavda_year))
					+ "') "
					+ "else (select round(((SUM(INP_AMT)/30)*"
					+ leavebalnce
					+ "),0) as encash from PAYTRAN_STAGE where TRNCD in(101,102,138) and trndt = '"
					+ ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"
							+ bsicdavda_year)) + "' and EMPNO=" + peno + ")");

			// System.out.println("this is date@#@#@#@#@#@@#@#@.."+prdt+"..."+ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"+prdt.substring(7,11))));
			//System.out.println("query leaveencash 2..." + leave_encsh);
			Connection con = ConnectionManager.getConnection();

			ResultSet rsleave = stleave.executeQuery(leave_encsh);
			if (rsleave.next()) {
				leaveencashamt = rsleave.getInt("encash");
				/*
				 * System.out.println("this march date encashment..."+leaveencashamt
				 * );
				 * 
				 * //take 28-FEB_YEAR's BASIC ,DA, VDA if 31-MAR-YEAR's
				 * BASIC,DA,VDA is 0... in case if emp is absent all month
				 * if(leaveencashamt==0) { System.out.println(
				 * "may be no salalry of mar.. so checking for ffeb..."
				 * +leaveencashamt); String leave_encsh1=(
				 * "if exists (select * from paytran where TRNCD in(101,102,138) and EMPNO="
				 * +
				 * peno+" and trndt = ( select CONVERT(varchar,dateadd(d,-(day('"
				 * +
				 * ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"+bsicdavda_year
				 * ))+"')),'"+ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"+
				 * bsicdavda_year))+"'),106) ) "+
				 * "(select round(((SUM(Net_amt)/30)*"+leavebalnce+
				 * "),0) as encash from paytran where TRNCD in(101,102,138) and EMPNO="
				 * +
				 * peno+" and trndt = ( select CONVERT(varchar,dateadd(d,-(day('"
				 * +
				 * ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"+bsicdavda_year
				 * ))+"')),'"+ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"+
				 * bsicdavda_year))+"'),106) )) "+
				 * "else (select round(((SUM(Net_amt)/30)*"+leavebalnce+
				 * "),0) as encash from PAYTRAN_STAGE where TRNCD in(101,102,138) and trndt = ( select CONVERT(varchar,dateadd(d,-(day('"
				 * +
				 * ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"+bsicdavda_year
				 * ))+"')),'"+ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"+
				 * bsicdavda_year))+"'),106) ) and EMPNO="+peno+")");
				 * System.out.println(
				 * "this taking basic+da+vda from feb-28...may be bcz march is empty sal.."
				 * +leave_encsh1);
				 * rsleave1=stleavefeb.executeQuery(leave_encsh1);
				 * if(rsleave1.next()) { leaveencashamt=
				 * rsleave1.getInt("encash"); } else { leaveencashamt= 0; } }
				 */
			}
			
			int leave_pf_amt = 0;
			//taxRepBean.setTotinc(taxRepBean.getTotinc() + leaveencashamt);
			
			taxRepBean.setTotinc(taxRepBean.getTotinc());
			//currently no pf on leave encash
			//leave_pf_amt = ((leaveencashamt * 12) / 100);
				
			
			System.out.println("Hello Dear Prasad");

			subcell = new PdfPCell(
					new Phrase(
							"   (a) Salary as per provisions contained in sec.\n        17(1)",
							F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(Math.round(taxRepBean.getTotinc()),
							"9,99,999", "", false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			subcell = new PdfPCell(
					new Phrase(
							"   (b) Value of perquisites u/s 17(2)(as per Form\n        No. 12BA,wherever applicable) ",
							F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(Math.round(taxRepBean.getVperk_amt()),
							"9,99,999", "", false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			subcell = new PdfPCell(
					new Phrase(
							"   (c) Profits in lieu of salary u/s 17(3)(as per Form\n        No. 12BA,wherever applicable)  ",
							F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(Math.round(0), "9,99,999", "", false,
							false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			taxRepBean.setTotinc(taxRepBean.getTotinc()
					+ taxRepBean.getVperk_amt());

			subcell = new PdfPCell(new Phrase("   (d) Total", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(Math.round(taxRepBean.getTotinc()),
							"99,99,999", "", false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "A3%", taxRepBean);
			/* printgrpF16(peno, prdt,repBean,taxRepBean); */
			ResultSet allowance = printgrpF16Result(peno, prdt, repBean,
					taxRepBean);
			us10 = Math.abs(Math.round((taxRepBean.getC5tot())));

			subcell = new PdfPCell(new Phrase(
					"2.  Less: Allowance to the extent exempt u/s 10",
					F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			PdfPTable tab2 = new PdfPTable(2);
			tab2.setWidthPercentage(new float[] { 50, 50 }, new Rectangle(100,
					100));
			int con_allow = 0;

			subcell = new PdfPCell(new Phrase("Allowance", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthLeft(0);
			tab2.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. ", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			// subcell.setBorderWidthRight(0);
			tab2.addCell(subcell);

			if (allowance.next()) {
				allowance.previous();
				while (allowance.next()) {

					PdfPCell subcellpan = new PdfPCell(new Phrase(""
							+ allowance.getString("DISC"), F10Normal));
					subcellpan.setHorizontalAlignment(Element.ALIGN_LEFT);
					// subcellpan.setMinimumHeight(20);
					subcellpan.setBorderWidthLeft(0);
					tab2.addCell(subcellpan);
					PdfPCell subcelltan = new PdfPCell(new Phrase(""
							+ UtilityDAO.trans(
									Math.round(allowance.getInt("NET_AMT")),
									"99,99,999", "", false, false), F10Normal));
					subcelltan.setHorizontalAlignment(Element.ALIGN_CENTER);
					// subcelltan.setMinimumHeight(20);
					// subcell.setBorderWidthRight(0);
					tab2.addCell(subcelltan);
				}
				taxRepBean.setC5tot(0);
				buildsql(peno, prdt, "A1U%", taxRepBean);
				ResultSet con_allowance = printgrpF16Result(peno, prdt,
						repBean, taxRepBean);
				//con_allow = Math.abs(Math.round((taxRepBean.getC5tot()))); // no conveyance as not in taxsheet
				con_allow =0;
				if (con_allowance.next()) {
					con_allowance.previous();
					while (con_allowance.next()) {

						PdfPCell subcellpan = new PdfPCell(new Phrase(""
								+ con_allowance.getString("DISC"), F10Normal));
						subcellpan.setHorizontalAlignment(Element.ALIGN_LEFT);
						// subcellpan.setMinimumHeight(20);
						subcellpan.setBorderWidthLeft(0);
						tab2.addCell(subcellpan);
						PdfPCell subcelltan = new PdfPCell(new Phrase(""
								+ UtilityDAO.trans(Math.round((con_allowance.getInt("trncd")==108?0:con_allowance
										.getInt("NET_AMT"))), "99,99,999", "",
										false, false), F10Normal));
						subcelltan.setHorizontalAlignment(Element.ALIGN_CENTER);
						// subcelltan.setMinimumHeight(20);
						// subcell.setBorderWidthRight(0);
						tab2.addCell(subcelltan);
					}
				} else {

				}

			} else {
				taxRepBean.setC5tot(0);
				buildsql(peno, prdt, "A1U%", taxRepBean);
				ResultSet con_allowance = printgrpF16Result(peno, prdt,
						repBean, taxRepBean);
				//con_allow = Math.abs(Math.round((taxRepBean.getC5tot()))); // no conveyance as not in taxsheet
				con_allow =0;
				if (con_allowance.next()) {
					con_allowance.previous();
					while (con_allowance.next()) {

						PdfPCell subcellpan = new PdfPCell(new Phrase(""
								+ con_allowance.getString("DISC"), F10Normal));
						subcellpan.setHorizontalAlignment(Element.ALIGN_LEFT);
						// subcellpan.setMinimumHeight(20);
						subcellpan.setBorderWidthLeft(0);
						tab2.addCell(subcellpan);
						PdfPCell subcelltan = new PdfPCell(new Phrase(""
								+ UtilityDAO.trans(Math.round((con_allowance.getInt("trncd")==108?0:con_allowance
										.getInt("NET_AMT"))), "99,99,999", "",
										false, false), F10Normal));
						subcelltan.setHorizontalAlignment(Element.ALIGN_CENTER);
						// subcelltan.setMinimumHeight(20);
						// subcell.setBorderWidthRight(0);
						tab2.addCell(subcelltan);
					}
				} else {

					PdfPCell subcellpan2 = new PdfPCell(new Phrase("--",
							F10Normal));
					subcellpan2.setHorizontalAlignment(Element.ALIGN_CENTER);
					// subcellpan2.setMinimumHeight(20);
					subcellpan2.setBorderWidthLeft(0);
					tab2.addCell(subcellpan2);
					PdfPCell subcelltan2 = new PdfPCell(new Phrase("--",
							F10Normal));
					subcelltan2.setHorizontalAlignment(Element.ALIGN_CENTER);
					// subcelltan2.setMinimumHeight(20);
					// subcell.setBorderWidthRight(0);
					tab2.addCell(subcelltan2);
				}
			}
			us10 = us10 + con_allow;
			subcell.addElement(tab2);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			// subcell.setBorderWidthLeft(0);

			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);

			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" ", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(Math.round(us10), "99,99,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(Math.round(us10), "99,99,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			taxRepBean.setTotinc(taxRepBean.getTotinc() - us10);

			subcell = new PdfPCell(
					new Phrase("3.  Balance (1 - 2) ", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(Math.round(taxRepBean.getTotinc()),
							"99,99,999", "", false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			ftotinc = taxRepBean.getTotinc();
			subcell = new PdfPCell(new Phrase("4.  Deductions u/s 16 :  ",
					F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase("   a)Entertainment allowance:",
					F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(Math.round(0), "999,999", "", false,
							false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			taxRepBean.setC4tot(0);
			taxRepBean.setC3tot(0);
			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "A53%", taxRepBean);
			printgrpF16(peno, prdt, repBean, taxRepBean);

			taxRepBean.setOTHER_DED(taxRepBean.getC5tot());
			/*taxRepBean.setTotamt((taxRepBean.getTotinc() - (taxRepBean
					.getTotded() + taxRepBean.getOTHER_DED())));*/
			float PTTTTT=taxRepBean.getC5tot();
			taxRepBean.setC4tot(0);
			taxRepBean.setC3tot(0);
			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "A54%", taxRepBean);
			printgrpF16(peno, prdt, repBean, taxRepBean);
			taxRepBean.setOTHER_DED(taxRepBean.getOTHER_DED()+taxRepBean.getC5tot());
			PTTTTT+=taxRepBean.getC5tot();
			subcell = new PdfPCell(new Phrase("   b)Tax on Employment      :",
					F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(Math.round(PTTTTT),
							"999,999", "", false, false), F10Normal));
			// subcell = new PdfPCell(new Phrase(" Rs.  2,500.00",F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			
			
			taxRepBean.setC4tot(0);
			taxRepBean.setC3tot(0);
			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "A51%", taxRepBean);
			printgrpF16(peno, prdt, repBean, taxRepBean);
			taxRepBean.setOTHER_DED(taxRepBean.getOTHER_DED()+taxRepBean.getC5tot());
			taxRepBean.setTotamt((taxRepBean.getTotinc() - (taxRepBean
					.getTotded() + taxRepBean.getOTHER_DED())));

			subcell = new PdfPCell(new Phrase("   c)Standard Deduction      :",
					F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(Math.round(taxRepBean.getC5tot()),
							"999,999", "", false, false), F10Normal));
			// subcell = new PdfPCell(new Phrase(" Rs.  2,500.00",F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase("5.  Aggregate of 4(a) to (b)",
					F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			// subcell = new PdfPCell(new
			// Phrase(" Rs. "+UtilityDAO.trans(Math.round(taxRepBean.getTotded()+2500),
			// "99,99,999", "", false,false),F10Normal));
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(
							Math.round(taxRepBean.getTotded()
									+ taxRepBean.getOTHER_DED()), "99,99,999",
							"", false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			subcell = new PdfPCell(
					new Phrase(
							"6.  Income chargeble under the Head 'Salaries'\n     (3-5)",
							F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			// subcell = new PdfPCell(new
			// Phrase(" Rs. "+UtilityDAO.trans(Math.round(taxRepBean.getTotinc()-2500),
			// "99,99,999", "", false,false),F10Normal));
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(
							Math.round(taxRepBean.getTotinc()
									- (taxRepBean.getTotded() + taxRepBean
											.getOTHER_DED())), "99,99,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			// after ded employment tax..totalincom=totalincom-emp_tax as
			// emplyment tax is 2500(fix) so - 2500 from total income...@ni
			taxRepBean.setTotinc(taxRepBean.getTotamt());
			// taxRepBean.setTotinc(taxRepBean.getTotinc()-2500);

			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "IC%", taxRepBean);
			printgrpF16(peno, prdt, repBean, taxRepBean);
			float temp_Value = taxRepBean.getC5tot();

			subcell = new PdfPCell(
					new Phrase(
							"7.  Add :Any other income reported  by the \n     employee",
							F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			/*
			 * subcell = new PdfPCell(new
			 * Phrase(" Rs. "+UtilityDAO.trans(Math.round
			 * (Math.abs(taxRepBean.getC5tot())), "9,99,999", "",
			 * false,false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 */
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "AA3%", taxRepBean);
			ResultSet addincome = printgrpF16Result(peno, prdt, repBean,
					taxRepBean);
			temp_Value = temp_Value + taxRepBean.getC5tot();

			/*
			 * subcell = new PdfPCell(new
			 * Phrase("   Add : Income From House Property ",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase(" Rs. "+UtilityDAO
			 * .trans(Math.round(Math.abs(taxRepBean.getC5tot())), "9,99,999",
			 * "", false,false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 * 
			 * subcell = new PdfPCell(new Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 * 
			 * subcell = new PdfPCell(new Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 * 
			 * taxRepBean.setC5tot(0); buildsql(peno, prdt, "HL%",taxRepBean);
			 * printgrpF16(peno, prdt,repBean,taxRepBean); temp_Value =
			 * temp_Value - taxRepBean.getC5tot();
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("   Less: Int.charged on Housing Loan ",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase(" Rs. "+UtilityDAO
			 * .trans(Math.round(Math.abs(taxRepBean.getC5tot())), "9,99,999",
			 * "", false,false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 * 
			 * subcell = new PdfPCell(new Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 * 
			 * taxRepBean.setC5tot(0); buildsql(peno, prdt, "AA%",taxRepBean);
			 * printgrpF16(peno, prdt,repBean,taxRepBean);
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase(" Rs. "+UtilityDAO.trans(Math.round(Math.abs(temp_Value)),
			 * "9,99,999", "", false,false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 */

			PdfPTable tab21 = new PdfPTable(2);
			tab21.setWidthPercentage(new float[] { 50, 50 }, new Rectangle(100,
					100));

			subcell = new PdfPCell(new Phrase("Income", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell.setBorderWidthLeft(0);
			tab21.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. ", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			// subcell.setBorderWidthRight(0);
			tab21.addCell(subcell);

			if (addincome.next()) {
				addincome.previous();
				while (addincome.next()) {

				
/*System.out.println("Chcek Emp No"+peno);
String record = "SELECT L.LKP_DISC AS BANKNAME, O.F5 AS PANCARD FROM OTHERDETAIL O, LOOKUP L  WHERE O.F4=L.LKP_SRNO AND O.EMPNO=373 AND L.LKP_CODE='BANK'";
ResultSet rst =st.executeQuery(record);
if(rst.next()){
	System.out.println("BANKNAME : "+rst.getString("BANKNAME"));
	System.out.println("PANCARD : "+rst.getString("PANCARD"));
}
*/
					//PdfPCell subcellpan1 = new PdfPCell(new Phrase(""
					PdfPCell subcellpan1 = new PdfPCell(new Phrase(""
							+ addincome.getString("DISC"), F10Normal));
					subcellpan1.setHorizontalAlignment(Element.ALIGN_CENTER);
					// subcellpan.setMinimumHeight(20);
					subcellpan1.setBorderWidthLeft(0);
					tab21.addCell(subcellpan1);
					PdfPCell subcelltan1 = new PdfPCell(new Phrase(""
							+ addincome.getString("NET_AMT"), F10Normal));
					subcelltan1.setHorizontalAlignment(Element.ALIGN_CENTER);
					// subcelltan.setMinimumHeight(20);
					// subcell.setBorderWidthRight(0);
					tab21.addCell(subcelltan1);
				}
			} else {
				PdfPCell subcellpan21 = new PdfPCell(
						new Phrase("--", F10Normal));
				subcellpan21.setHorizontalAlignment(Element.ALIGN_CENTER);
				// subcellpan2.setMinimumHeight(20);
				subcellpan21.setBorderWidthLeft(0);
				tab21.addCell(subcellpan21);
				PdfPCell subcelltan21 = new PdfPCell(
						new Phrase("--", F10Normal));
				subcelltan21.setHorizontalAlignment(Element.ALIGN_CENTER);
				// subcelltan2.setMinimumHeight(20);
				// subcell.setBorderWidthRight(0);
				tab21.addCell(subcelltan21);
			}
			subcell.addElement(tab21);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			// subcell.setBorderWidthLeft(0);

			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" ", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			/*
			 * subcell = new PdfPCell(new Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 */

			System.out.println("Chcek Emp No"+peno);
			String record = "SELECT L.LKP_DISC AS BANKNAME, O.F5 AS PANCARD FROM OTHERDETAIL O, LOOKUP L  WHERE O.F4=L.LKP_SRNO AND O.EMPNO="+peno+" AND L.LKP_CODE='BANK'";
			ResultSet rst =st.executeQuery(record);
			if(rst.next()){
			//subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell = new PdfPCell(new Phrase(""+rst.getString("BANKNAME")+"     "+rst.getString("PANCARD"), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			}
			
			else{
				subcell = new PdfPCell(new Phrase("", F10Normal));
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				subcell.setBorderWidthTop(0);
				subcell.setBorderWidthBottom(0);
				subcell.setMinimumHeight(15);
				tab1.addCell(subcell);
			}
			
			subcell = new PdfPCell(new Phrase(" ", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			
			
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(
							Math.round(Math.abs(taxRepBean.getC5tot())),
							"9,99,999", "", false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			taxRepBean.setTotinc(taxRepBean.getTotinc() - (int) temp_Value);
			subcell = new PdfPCell(new Phrase("8.  Gross Total Income (6 + 7)",
					F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(Math.round(taxRepBean.getTotinc()),
							"99,99,999", "", false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			subcell = new PdfPCell(
					new Phrase(
							"9.  Deductions (chapter VI-A) \n    A) Section 80C, 80CCC, and 80CCD",
							F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("Gross Amount", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("Deductable Amount", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "80C%", taxRepBean);
			/* printgrpF16(peno, prdt,repBean,taxRepBean); */
			ResultSet ded_80C = printgrpF16Result(peno, prdt, repBean,
					taxRepBean);
			taxRepBean.setC5tot(taxRepBean.getC5tot() + leave_pf_amt);
			int forgrosscheck = taxRepBean.getC5tot();
			if (taxRepBean.getC5tot() > 150000) {
				q80d = 150000;
			} else {
				q80d = taxRepBean.getC5tot();
			}

			subcell = new PdfPCell(
					new Phrase("   a) Section 80C   ", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			// not to print here total amount
			/*
			 * subcell = new PdfPCell(new Phrase("Rs."+
			 * UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false,
			 * false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("Rs."+UtilityDAO.trans(Math.round(Math.abs(q80d)),
			 * "9,99,999", "", false,false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 */

			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			if (ded_80C.next()) {
				int no = 1;
				ded_80C.previous();
				while (ded_80C.next()) {
					String str = "";
					if (ded_80C.getString("DISC").contains("-")) {
						str = ded_80C.getString("DISC").substring(
								ded_80C.getString("DISC").indexOf("-") + 1,
								ded_80C.getString("DISC").length());
					} else {
						str = ded_80C.getString("DISC");
					}
					StringBuilder sb = new StringBuilder(str);
					String result = "";
					str = str.charAt(0) == ' ' ? result = (sb.deleteCharAt(0))
							.toString() : str;
					subcell = new PdfPCell(
							new Phrase(no < 10 ? "   " + no + ")   " + str
									: " " + no + ")   " + str, F10Normal));
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					subcell.setBorderWidthTop(0);
					subcell.setBorderWidthBottom(0);
					subcell.setMinimumHeight(15);
					tab1.addCell(subcell);
					subcell = new PdfPCell(new Phrase("", F10Normal));
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					subcell.setBorderWidthTop(0);
					subcell.setBorderWidthBottom(0);
					tab1.addCell(subcell);
					// not to print here total amount
					if (ded_80C.getInt("TRNCD") == 201) {
						subcell = new PdfPCell(
								new Phrase(
										" Rs. "
												+ UtilityDAO.trans(
														(ded_80C.getInt("NET_AMT") + leave_pf_amt),
														"999,999", "", false,
														false), F10Normal));
					} else {
						subcell = new PdfPCell(
								new Phrase(" Rs. "
										+ UtilityDAO.trans(
												ded_80C.getInt("NET_AMT"),
												"999,999", "", false, false),
										F10Normal));
					}
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					subcell.setBorderWidthTop(0);
					subcell.setBorderWidthBottom(0);
					tab1.addCell(subcell);
					no++;
					if (!ded_80C.next()) {
						subcell = new PdfPCell(new Phrase("", F10Normal));
						subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						subcell.setBorderWidthTop(0);
						subcell.setBorderWidthBottom(0);
						tab1.addCell(subcell);
						break;
					} else {
						ded_80C.previous();
						subcell = new PdfPCell(new Phrase("", F10Normal));
						subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						subcell.setBorderWidthTop(0);
						subcell.setBorderWidthBottom(0);
						tab1.addCell(subcell);
						continue;
					}

				}
			} else {

			}
			/*
			 * taxRepBean.setC5tot(0); buildsql(peno, prdt, "80C1",taxRepBean);
			 * printgrpF16(peno, prdt,repBean,taxRepBean);
			 * if(taxRepBean.getC5tot() > 0){
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("     - P.F.            ",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new Phrase("Rs." +
			 * UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false,
			 * false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 * 
			 * }
			 * 
			 * 
			 * taxRepBean.setC5tot(0); buildsql(peno, prdt, "ACB%",taxRepBean);
			 * printgrpF16(peno, prdt,repBean,taxRepBean);
			 * if(taxRepBean.getC5tot() > 0){
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("       - P.P.F.           ",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new Phrase("Rs." +
			 * UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false,
			 * false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 * 
			 * }
			 * 
			 * taxRepBean.setC5tot(0); buildsql(peno, prdt, "80C2",taxRepBean);
			 * printgrpF16(peno, prdt,repBean,taxRepBean);
			 * if(taxRepBean.getC5tot() > 0){
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("       - Life Ins. Premium ",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new Phrase("Rs." +
			 * UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false,
			 * false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 * 
			 * }
			 * 
			 * taxRepBean.setC5tot(0); buildsql(peno, prdt, "80C4",taxRepBean);
			 * printgrpF16(peno, prdt,repBean,taxRepBean);
			 * if(taxRepBean.getC5tot() > 0){
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("       - Housing Loan Ded.      ",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new Phrase("Rs." +
			 * UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false,
			 * false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 * 
			 * }
			 * 
			 * taxRepBean.setC5tot(0); buildsql(peno, prdt, "ACE%",taxRepBean);
			 * printgrpF16(peno, prdt,repBean,taxRepBean);
			 * if(taxRepBean.getC5tot() > 0){
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("       - U.T.I            ",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new Phrase("Rs." +
			 * UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false,
			 * false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); }
			 * 
			 * taxRepBean.setC5tot(0); buildsql(peno, prdt, "80C3",taxRepBean);
			 * printgrpF16(peno, prdt,repBean,taxRepBean);
			 * if(taxRepBean.getC5tot() > 0){
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("       - N.S.C            ",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new Phrase("Rs." +
			 * UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false,
			 * false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); }
			 * 
			 * taxRepBean.setC5tot(0); buildsql(peno, prdt,"80C12",taxRepBean);
			 * printgrpF16(peno, prdt,repBean,taxRepBean);
			 * if(taxRepBean.getC5tot() > 0){
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("       - N.S.S            ",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new Phrase("Rs." +
			 * UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false,
			 * false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); } taxRepBean.setC5tot(0); buildsql(peno,
			 * prdt, "ACH%",taxRepBean); printgrpF16(peno,
			 * prdt,repBean,taxRepBean); if(taxRepBean.getC5tot() > 0){
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("       - F.P.F            ",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new Phrase("Rs." +
			 * UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false,
			 * false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 * 
			 * }
			 * 
			 * taxRepBean.setC5tot(0); buildsql(peno, prdt, "80C5",taxRepBean);
			 * printgrpF16(peno, prdt,repBean,taxRepBean);
			 * if(taxRepBean.getC5tot() > 0){
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("       - Tution Fee       ",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new Phrase("Rs." +
			 * UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false,
			 * false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 * 
			 * } taxRepBean.setC5tot(0); buildsql(peno, prdt,
			 * "80C6",taxRepBean); printgrpF16(peno, prdt,repBean,taxRepBean);
			 * if(taxRepBean.getC5tot() > 0){
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("       - E.L.S.S. (Mutual Fund) ",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new Phrase("Rs." +
			 * UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false,
			 * false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 * 
			 * } taxRepBean.setC5tot(0); buildsql(peno, prdt,
			 * "80C7",taxRepBean); printgrpF16(peno, prdt,repBean,taxRepBean);
			 * if(taxRepBean.getC5tot() > 0){
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("       - F.D. (5 years&above) ",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new Phrase("Rs." +
			 * UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false,
			 * false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 * 
			 * } taxRepBean.setC5tot(0); buildsql(peno, prdt,
			 * "80C8",taxRepBean); printgrpF16(peno, prdt,repBean,taxRepBean);
			 * if(taxRepBean.getC5tot() > 0){
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("       - Tax Saving Infra Bonds. ",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new Phrase("Rs." +
			 * UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false,
			 * false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 * 
			 * } taxRepBean.setC5tot(0); buildsql(peno, prdt,
			 * "80C9",taxRepBean); printgrpF16(peno, prdt,repBean,taxRepBean);
			 * if(taxRepBean.getC5tot() > 0){
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("       - Other Invest. ",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new Phrase("Rs." +
			 * UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false,
			 * false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 * 
			 * }
			 */
			/*
			 * taxRepBean.setC5tot(0); buildsql(peno, prdt, "80C10",taxRepBean);
			 * printgrpF16(peno, prdt,repBean,taxRepBean);
			 * if(taxRepBean.getC5tot() > 0){
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("       - Pension Plan. ",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new Phrase("Rs." +
			 * UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false,
			 * false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 * 
			 * } taxRepBean.setC5tot(0); buildsql(peno, prdt,
			 * "80C11",taxRepBean); printgrpF16(peno, prdt,repBean,taxRepBean);
			 * if(taxRepBean.getC5tot() > 0){
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("       - New Pension Scheme Emp. ",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new Phrase("Rs." +
			 * UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false,
			 * false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 * 
			 * }
			 */
			int ded_80c = taxRepBean.getC5tot();
			int ccc = 0;
			int ccd = 0;
			int ccf = 0;
			
			int ccdb = 0;
			
			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "80C10", taxRepBean);
			printgrpF16(peno, prdt, repBean, taxRepBean);
			ccc = taxRepBean.getC5tot();
			subcell = new PdfPCell(new Phrase("   b) Section 80 CCC       ",
					F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(taxRepBean.getC5tot(), "999,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			/* subcell = new PdfPCell(new Phrase("",F10Normal)); */
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(taxRepBean.getC5tot(), "999,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "80C11", taxRepBean);
			printgrpF16(peno, prdt, repBean, taxRepBean);
			int ccd1 = taxRepBean.getC5tot();// this is ccd1 amount
			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "80C15", taxRepBean);// this is ccd2 amount
			printgrpF16(peno, prdt, repBean, taxRepBean);
			taxRepBean.setC5tot(taxRepBean.getC5tot() + ccd1);// both amount
			ccd = taxRepBean.getC5tot();

			subcell = new PdfPCell(new Phrase("   c) Section 80 CCD       ",
					F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(taxRepBean.getC5tot(), "999,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			/* subcell = new PdfPCell(new Phrase("",F10Normal)); */
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(taxRepBean.getC5tot(), "999,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "80C8", taxRepBean);
			printgrpF16(peno, prdt, repBean, taxRepBean);
			q80ccf = (int) Calculate.checkSlab(551, prdt,
					taxRepBean.getC5tot(), 1, peno, repBean.getCn());
			ccf = taxRepBean.getC5tot();
			subcell = new PdfPCell(new Phrase("   d) Section 80 CCF(InfBond) ",
					F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(taxRepBean.getC5tot(), "999,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			/* subcell = new PdfPCell(new Phrase("",F10Normal)); */
			if (taxRepBean.getC5tot() > 20000) {
				taxRepBean.setC5tot(20000);
				ccf = taxRepBean.getC5tot();
			}

			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(taxRepBean.getC5tot(), "999,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			// this is for 80c gross and dedudctble amount checking..
			subcell = new PdfPCell(new Phrase("", F10Normal));

			// subcell.setColspan(4);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans((forgrosscheck + ccf + ccd + ccc),
							"999,999", "", false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(
					new Phrase(
							" Rs. "
									+ UtilityDAO
											.trans((((forgrosscheck + ccf + ccd + ccc) > 150000) ? 150000
													: (forgrosscheck + ccf
															+ ccd + ccc)),
													"999,999", "", false, false),
							F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			
			
			/****************************************************START**********************************************************************************/
			taxRepBean.setC5tot(0);
			//buildsql(peno, prdt, "80C12", taxRepBean);
			buildsql(peno, prdt, "80D5", taxRepBean);
			printgrpF16(peno, prdt, repBean, taxRepBean);
			//ccc = taxRepBean.getC5tot();
			ccdb = taxRepBean.getC5tot();
			subcell = new PdfPCell(new Phrase("   e) Section 80CCD(1B)- PENSION SCHEME (Investment Rs.50000/-)       ",
					F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(taxRepBean.getC5tot(), "999,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			/* subcell = new PdfPCell(new Phrase("",F10Normal)); */
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(taxRepBean.getC5tot(), "999,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			/*****************************************************END*********************************************************************************/
			
			
			
			/*
			 * subcell = new PdfPCell(new Phrase(
			 * "Notes : 1. Aggregate amount deductible under section 80C shall not exceed one lakh rupees."
			 * ,F10Normal));
			 * 
			 * subcell.setColspan(4); subcell.setBorderWidthTop(0);
			 * subcell.setBorderWidthBottom(0);
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * tab1.addCell(subcell);
			 */
			subcell = new PdfPCell(
					new Phrase(
							"\n   Notes : 1. Aggregate amount deductible under\n   the three sections, i.e., 80C, 80CCC and 80CCD,\n   shall not exceed one"
									+ " lakh fifty thousand rupees.", F10Normal));

			// subcell.setColspan(4);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			float medical = 0.0f;
			float medical_parents = 0.0f;
			float sec80d = 0.0f;
			float sec80db = 0.0f;
			float sec80_tot = 0.0f;
			taxRepBean.setC5tot(0);// mediclaim self
			buildsql(peno, prdt, "80D1", taxRepBean);
			printgrpF16(peno, prdt, repBean, taxRepBean);
			if (taxRepBean.getC5tot() >= 25000)
				medical = 25000;
			else
				medical = taxRepBean.getC5tot();

			taxRepBean.setC5tot(0);// mediclaim parent
			buildsql(peno, prdt, "80D2", taxRepBean);
			printgrpF16(peno, prdt, repBean, taxRepBean);
			if (taxRepBean.getC5tot() >= 30000)
				medical_parents = 30000;
			else
				medical_parents = taxRepBean.getC5tot();

			taxRepBean.setC5tot(0);// this is 80dd
			buildsql(peno, prdt, "80D3", taxRepBean);
			printgrpF16(peno, prdt, repBean, taxRepBean);
			sec80d = taxRepBean.getC5tot();

			System.out.println("Check Prasad 1 :"+sec80d);
			
			taxRepBean.setC5tot(0);// this is ddb
			buildsql(peno, prdt, "80D4", taxRepBean);
			printgrpF16(peno, prdt, repBean, taxRepBean);
			sec80db = taxRepBean.getC5tot();
			System.out.println("Check Prasad 1 :"+sec80db);
			//sec80db + sec80d + medical + medical_parents;
			sec80_tot = medical + medical_parents;
			if (sec80_tot > 30000) {
				sec80_tot = 30000;
			}
			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "80D%", taxRepBean);
			printgrpF16(peno, prdt, repBean, taxRepBean);
			q80d = q80d + ccf + ccd + ccc;
			if (q80d > 150000) {
				q80d = 150000;

			}
			System.out.println("Check Prasad 2 :"+sec80d);
			System.out.println("Check Prasad 2 :"+sec80db);
			tmpamt = q80d;
			q80ccf = (int) Calculate.checkSlab(801, prdt,
					taxRepBean.getC5tot(), 1, peno, repBean.getCn());
			subcell = new PdfPCell(
					new Phrase(
							"\n   B) Other Sections(for e.g. 80E, 80G etc) under\n       Chapter VIA",
							F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthBottom(0);
			subcell.setBorderWidthTop(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthBottom(0);
			subcell.setBorderWidthTop(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthBottom(0);
			subcell.setBorderWidthTop(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthBottom(0);
			subcell.setBorderWidthTop(0);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthBottom(0);
			subcell.setBorderWidthTop(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("Gross amount", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthBottom(0);
			subcell.setBorderWidthTop(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("Qualifying amount", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthBottom(0);
			subcell.setBorderWidthTop(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("Deductible amount", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthBottom(0);
			subcell.setBorderWidthTop(0);
			tab1.addCell(subcell);

			subcell = new PdfPCell(
					new Phrase("     a) U/S 80 D    ", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			/* subcell = new PdfPCell(new Phrase("",F10Normal)); */
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(Math.round(medical + medical_parents), "999,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans((int) sec80_tot, "999,999", "", false,
							false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans((int) sec80_tot, "999,999", "", false,
							false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			
			
			subcell = new PdfPCell(
					new Phrase("     b) U/S 80 DD    ", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			/* subcell = new PdfPCell(new Phrase("",F10Normal)); */
			subcell = new PdfPCell(new Phrase(" Rs. "
				//	+ UtilityDAO.trans(Math.round(sec80d), "999,999", "",
					+ UtilityDAO.trans(Math.round(sec80db), "999,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
			//		+ UtilityDAO.trans(Math.round(sec80d), "999,999", "", false,
					+ UtilityDAO.trans(Math.round(sec80db), "999,999", "", false,
							false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
			//		+ UtilityDAO.trans(Math.round(sec80d), "999,999", "", false,
					+ UtilityDAO.trans(Math.round(sec80db), "999,999", "", false,
							false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			// q80d = q80d + tmpamt;
			/*
			 * taxRepBean.setC5tot(0); buildsql(peno, prdt, "80D1",taxRepBean);
			 * printgrpF16(peno, prdt,repBean,taxRepBean);
			 * if(taxRepBean.getC5tot() > 0){
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("     - Medical Ins. Premiums + Health Check    "
			 * ,F10Normal)); subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new Phrase("Rs." +
			 * UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false,
			 * false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("Rs."+UtilityDAO.trans((int)medical, "999,999","", false,
			 * false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 * 
			 * }
			 * 
			 * taxRepBean.setC5tot(0); buildsql(peno, prdt, "80D2",taxRepBean);
			 * printgrpF16(peno, prdt,repBean,taxRepBean);
			 * if(taxRepBean.getC5tot() > 0){
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("     - Medical Ins. Premiums (Parents)            "
			 * ,F10Normal)); subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new Phrase("Rs." +
			 * UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false,
			 * false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("Rs."+UtilityDAO.trans((int)medical_parents, "999,999","",
			 * false, false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 * 
			 * }
			 * 
			 * if(sec80d > 0){
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("     - Section 80DD            ",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new Phrase("Rs."
			 * +UtilityDAO.trans((int)sec80d, "999,999","", false,
			 * false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new Phrase("Rs."
			 * +UtilityDAO.trans((int)sec80d, "999,999","", false,
			 * false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 * 
			 * } if(sec80db > 0){
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("     - Section 80DDB            ",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new Phrase("Rs."
			 * +UtilityDAO.trans((int)sec80db, "999,999","", false,
			 * false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new Phrase("Rs."
			 * +UtilityDAO.trans((int)sec80db, "999,999","", false,
			 * false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 * 
			 * }
			 */

			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "80E1", taxRepBean);
			printgrpF16(peno, prdt, repBean, taxRepBean);
			// q80e = (int)Calculate.checkSlab(802, prdt, taxRepBean.getC5tot(),
			// 1,peno,repBean.getCn());
			q80e = taxRepBean.getC5tot();
			subcell = new PdfPCell(new Phrase("     c) U/S 80 E      ",
					F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			/* subcell = new PdfPCell(new Phrase("",F10Normal)); */
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(taxRepBean.getC5tot(), "999,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(taxRepBean.getC5tot(), "999,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(taxRepBean.getC5tot(), "9,99,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "80G%", taxRepBean);
			printgrpF16(peno, prdt, repBean, taxRepBean);
			// q80gPM = (int)Calculate.checkSlab(807, prdt,
			// taxRepBean.getC5tot(), 1,peno,repBean.getCn());
			q80gPM = taxRepBean.getC5tot();
			subcell = new PdfPCell(new Phrase("     d) U/S 80 G(CMrel)",
					F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			/* subcell = new PdfPCell(new Phrase("",F10Normal)); */
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(taxRepBean.getC5tot(), "999,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(taxRepBean.getC5tot(), "999,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(taxRepBean.getC5tot(), "9,99,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "AEA%", taxRepBean);
			printgrpF16(peno, prdt, repBean, taxRepBean);
			q80g = (int) Calculate.checkSlab(803, prdt, taxRepBean.getC5tot(),
					1, peno, repBean.getCn());

			subcell = new PdfPCell(new Phrase("     e) U/S 80 G(Other)",
					F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			/* subcell = new PdfPCell(new Phrase("",F10Normal)); */
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(taxRepBean.getC5tot(), "999,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(taxRepBean.getC5tot(), "999,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			/* subcell = new PdfPCell(new Phrase("",F10Normal)); */
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(taxRepBean.getC5tot(), "999,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "80G2", taxRepBean);
			printgrpF16(peno, prdt, repBean, taxRepBean);

			q80gG = taxRepBean.getC5tot();
			if (q80gG > 60000) {
				q80gG = 60000;
			}
			subcell = new PdfPCell(new Phrase("     f) U/S 80GG(RentPd)",
					F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			/* subcell = new PdfPCell(new Phrase("",F10Normal)); */
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(taxRepBean.getC5tot(), "999,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(q80gG, "999,999", "", false, false),
					F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			/* subcell = new PdfPCell(new Phrase("",F10Normal)); */
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(q80gG, "999,999", "", false, false),
					F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "80U1", taxRepBean);
			printgrpF16(peno, prdt, repBean, taxRepBean);
			// q80u = (int)Calculate.checkSlab(805, prdt, taxRepBean.getC5tot(),
			// 1,peno,repBean.getCn());
			q80u = taxRepBean.getC5tot();
			subcell = new PdfPCell(new Phrase("     g) U/S 80 U       ",
					F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			/* subcell = new PdfPCell(new Phrase("",F10Normal)); */
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(taxRepBean.getC5tot(), "999,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(taxRepBean.getC5tot(), "999,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(taxRepBean.getC5tot(), "9,99,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			
			// via = q80cc + q80d + q80e + q80gPM + q80g + q80gG + q80l + q80u +
			// q80ccf;
			//via = q80d + (int) sec80_tot + q80e + q80gPM + q80u+Math.round(sec80d);
			//via = q80d + (int) sec80_tot + q80e + q80gPM + q80u+Math.round(sec80d) + ccdb;
			via = q80d + (int) sec80_tot + q80e + q80gPM + q80u+Math.round(sec80db) + ccdb;
			subcell = new PdfPCell(
					new Phrase(
							"10. Aggregate of deductible amount under chapter\n      VI-A ",
							F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(Math.round(via), "9,99,999", "", false,
							false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("11. Total of Income (8 - 10) ",
					F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(
							Math.round(taxRepBean.getTotinc() - via),
							"9,99,999", "", false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			ftotinc = taxRepBean.getTotinc();
			inctot = taxRepBean.getTotinc() - via;
			int totchkamt = 0;
			// here is new code for slab checking accrding to rule.... @ni
			if (SEX1.equalsIgnoreCase("F")) // if total income is > 350000 then
											// tax is minimum of 2500 or tax on
											// income otherwise it would be 5 %
											// if income is <500000....@ni
			{
				// caltax = Math.round(Chk_Slab1(575, prdt, inctot, 0,repBean));
				// //for female /male by emp_category we can diff in slab
				totchkamt = Math
						.round(Chk_Slab1(599, prdt, inctot, 0, repBean)); // so
																			// 1st
																			// checked
																			// income
																			// is
																			// greather
																			// or
																			// not
																			// ...
																			// according
																			// it
																			// takes
																			// slab...
				if (inctot <= totchkamt) {
					caltax = Math
							.round(Chk_Slab1(575, prdt, inctot, 1, repBean));
				} else {
					caltax = Math
							.round(Chk_Slab1(575, prdt, inctot, 0, repBean));
				}
			} else {

				totchkamt = Math
						.round(Chk_Slab1(599, prdt, inctot, 0, repBean));
				if (inctot <= totchkamt) {
					caltax = Math
							.round(Chk_Slab1(575, prdt, inctot, 1, repBean));
				} else {
					caltax = Math
							.round(Chk_Slab1(575, prdt, inctot, 0, repBean));
					
				}
			}System.out.println("caltax: "+caltax);
			int rebate = 0;
			if (inctot > 250000 && inctot <= 500000)
				rebate = 0;// 2000;
			/*
			 * if(SEX1.equalsIgnoreCase("F")){ caltax =
			 * Math.round(TaxCalculation.Chk_Slab1(575, prdt, inctot, 2,
			 * repBean)); } int rebate = 0; if(inctot > 250000 && inctot <=
			 * 500000) rebate = 0;//2000; // CHECK AGE . IF ABOVE 65, THEN PASS
			 * 3 AS CATEGORY CODE int A = 0; A = UtilityDAO.dateDiff("yyyy",
			 * pdob,
			 * java.sql.Date.valueOf("2003-06-28"));//********************Second
			 * date remainning if(A < 65){ //age less than 65 //caltax =
			 * TaxCalculation.income_tax(inctot); //caltax =
			 * Math.round(TaxCalculation.Chk_Slab1(703, prdt, inctot, 1,
			 * repBean)); caltax= Math.round(Chk_Slab1(575, prdt, inctot, 0,
			 * repBean)); }else{ //age greter than 65 //caltax =
			 * Math.round(TaxCalculation.Chk_Slab1(703, prdt, inctot, 3,
			 * repBean)); caltax = Math.round(Chk_Slab1(575, prdt, inctot, 0,
			 * repBean)); }
			 */
			
			taxRepBean.setNet_tax(Math.round(caltax));

			subcell = new PdfPCell(new Phrase("12. Tax on Total Income",
					F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);

			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			/*subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(Math.round(taxRepBean.getNet_tax()),
							"9,99,999", "", false, false), F10Normal));*/
			
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(Math.round(taxRepBean.getNet_tax()),
							"9,99,999", "", false, false), F10Normal));
			System.out.println("12. Tax on Total Income: "+Math.round(taxRepBean.getNet_tax()));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			if (taxRepBean.getNet_tax() > 0) {
				if (inctot <= totchkamt) {
					taxRepBean
							.setNet_tax(taxRepBean.getNet_tax() > 12500 ? taxRepBean
									.getNet_tax() - 12500 : 0);

					subcell = new PdfPCell(new Phrase("      Rebate ",
							F10Normal));
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					subcell.setBorderWidthTop(0);
					subcell.setBorderWidthBottom(0);
					subcell.setMinimumHeight(15);
					tab1.addCell(subcell);
					subcell = new PdfPCell(new Phrase("", F10Normal));
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					subcell.setBorderWidthTop(0);
					subcell.setBorderWidthBottom(0);
					tab1.addCell(subcell);
					subcell = new PdfPCell(new Phrase(" Rs. "
							+ UtilityDAO.trans(Math.round(12500), "9,99,999",
									"", false, false), F10Normal));
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					subcell.setBorderWidthTop(0);
					subcell.setBorderWidthBottom(0);
					tab1.addCell(subcell);
					subcell = new PdfPCell(new Phrase("", F10Normal));
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					subcell.setBorderWidthTop(0);
					subcell.setBorderWidthBottom(0);
					tab1.addCell(subcell);
				} else {
					taxRepBean.setNet_tax(taxRepBean.getNet_tax());
				}
			}
			/*
			 * taxRepBean.setSercharge(0); if(Math.round(taxRepBean.getReb88() +
			 * us89 + b88 + c88 + reb88d) > caltax ){ taxRepBean.setNet_tax(0);
			 * }else{ taxRepBean.setNet_tax(Math.round(caltax -
			 * (taxRepBean.getReb88() + us89 + b88 + c88 + reb88d))); }
			 * 
			 * if(taxRepBean.getNet_tax() > 0){
			 * taxRepBean.setSercharge((int)Calculate.checkSlab(900, prdt,
			 * taxRepBean.getNet_tax(), 1,peno,repBean.getCn())); }
			 * taxRepBean.setNet_tax(taxRepBean.getNet_tax() +
			 * taxRepBean.getSercharge());
			 */

			/*
			 * subcell = new PdfPCell(new
			 * Phrase("13.Rebate U/S 87A",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new Phrase("",new
			 * Font(Font.TIMES_ROMAN,6,Font.NORMAL)));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("Rs."+UtilityDAO.trans(Math.round(rebate), "9,99,999","",
			 * false, false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 */

			// set Education cess here
			/*taxRepBean.setSercharge(taxRepBean.getNet_tax() > 0 ? (Math
					.round((taxRepBean.getNet_tax() * 3) / 100)) : 0);*/
			float Nettax =  (float) taxRepBean.getNet_tax();
			  /*taxRepBean.setSercharge(taxRepBean.getNet_tax() > 0 ? (Math
	/				.round((taxRepBean.getNet_tax() * 4) / 100)) : 0);*/
				taxRepBean.setSercharge(Nettax > 0 ? (Math
					.round((Nettax * 4) / 100)) : 0);
			subcell = new PdfPCell(
					new Phrase(
							"13. Education cess @ 4% (on tax computed at S.\n      No.12)",
							F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(taxRepBean.getSercharge(), "9,99,999",
							"", false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", new Font(Font.TIMES_ROMAN, 6,
					Font.NORMAL)));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			/*
			 * taxRepBean.setNet_tax(Math.round(caltax - rebate)); subcell = new
			 * PdfPCell(new Phrase("14.Total Tax After Rebate ",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new Phrase("",new
			 * Font(Font.TIMES_ROMAN,6,Font.NORMAL)));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("Rs."+UtilityDAO
			 * .trans(Math.round(taxRepBean.getNet_tax()), "9,99,999","", false,
			 * false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 * 
			 * educess = (int)Math.round(taxRepBean.getNet_tax() * 0.03);
			 * subcell = new PdfPCell(new
			 * Phrase("15.Education cess (on tax 12 & 13)",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("Rs."+UtilityDAO.trans(Math.round(educess), "9,99,999","",
			 * false, false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 */

			taxRepBean.setNet_tax(taxRepBean.getNet_tax()
					+ taxRepBean.getSercharge());
			subcell = new PdfPCell(new Phrase("14. Tax payable (12+13)",
					F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(Math.round(taxRepBean.getNet_tax()),
							"9,99,999", "", false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			/*
			 * taxRepBean.setC5tot(0); buildsql(peno, prdt, "AN3%",taxRepBean);
			 * printgrpF16(peno, prdt,repBean,taxRepBean); us89 =
			 * taxRepBean.getC5tot();
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("16. Relief u/s 89 (attach details)",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); // subcell = new PdfPCell(new
			 * Phrase("Rs."+UtilityDAO.trans(Math.round(us89), "9,99,999","",
			 * false, false),new Font(Font.TIMES_ROMAN,6,Font.NORMAL))); subcell
			 * = new PdfPCell(new
			 * Phrase("Rs."+UtilityDAO.trans(Math.round(us89), "9,99,999","",
			 * false, false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 */

			/*
			 * subcell = new PdfPCell(new
			 * Phrase("17. Tax Payable   (13 - 14)",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("Rs."+UtilityDAO
			 * .trans(Math.round(taxRepBean.getNet_tax()), "9,99,999","", false,
			 * false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 */

			/*
			 * taxRepBean.setC5tot(0); buildsql(peno, prdt, "BB%",taxRepBean);
			 * printgrpF16(peno, prdt,repBean,taxRepBean); int taxPaid1 =
			 * taxRepBean.getC5tot();
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("17. Less : (a) TDS  US 192(1)",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("Rs."+UtilityDAO.trans(Math.round(taxPaid1),
			 * "9,99,999","", false, false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 */

			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "TDS%", taxRepBean);
			printgrpF16(peno, prdt, repBean, taxRepBean);
			int taxPaid1 = taxRepBean.getC5tot();

			subcell = new PdfPCell(new Phrase(
					"15. Less: Relief under section 89 (attach details)",
					F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(Math.round(taxPaid1), "9,99,999", "",
							false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setBorderWidthBottom(0);
			tab1.addCell(subcell);

			/*
			 * taxRepBean.setC5tot(0); buildsql(peno, prdt, "TDS",taxRepBean);
			 * printgrpF16(peno, prdt,repBean,taxRepBean); int taxPaid2 =
			 * taxRepBean.getC5tot(); taxRepBean.setTAXPAID(taxPaid1 +
			 * taxPaid2);
			 */
			taxRepBean.setTAXPAID(taxPaid1);
			taxRepBean.setNet_tax(Math.round(taxRepBean.getNet_tax())
					- Math.round(taxRepBean.getTAXPAID()));
			String payRefund = "";
			if (taxRepBean.getNet_tax() <= 0) {
				payRefund = "  Refundable";
			} else {
				payRefund = "  Payable";
			}
			/*
			 * subcell = new PdfPCell(new Phrase(
			 * "       (b) Tax pd.by the empr.on behalf of the emp u/s192(1A)on per.u/s17(2) "
			 * ,F10Normal)); subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("",F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 * 
			 * subcell = new PdfPCell(new
			 * Phrase("Rs."+UtilityDAO.trans(Math.round(taxPaid2),
			 * "9,99,999","", false, false),F10Normal));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * subcell.setBorderWidthTop(0); subcell.setBorderWidthBottom(0);
			 * tab1.addCell(subcell);
			 */

			subcell = new PdfPCell(new Phrase("16. Tax payable (14-15)",
					F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			subcell.setMinimumHeight(15);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);

			subcell.setBorderWidthTop(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase("" + payRefund, F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(" Rs. "
					+ UtilityDAO.trans(Math.round(taxRepBean.getNet_tax()),
							"9,99,999", "", false, false), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setBorderWidthTop(0);
			tab1.addCell(subcell);
			taxRepBean.getDoc().add(tab1);
			/*
			 * Paragraph para = new Paragraph(new Phrase(
			 * "DETAILS OF TAX DEDUCTED AND DEPOSITED INTO CENTRAL GOVERNMENT ACCOUNT"
			 * ,F10Bold)); para.setAlignment(Element.ALIGN_CENTER);
			 * para.setSpacingBefore(5); para.setSpacingAfter(5);
			 * taxRepBean.getDoc().add(para);
			 * 
			 * para = new Paragraph(new Phrase(
			 * "(The Employer is to provide payment-wise details of tax deducted and deposited)"
			 * ,F10Bold)); para.setAlignment(Element.ALIGN_CENTER);
			 * para.setSpacingBefore(2); para.setSpacingAfter(2);
			 * taxRepBean.getDoc().add(para);
			 * 
			 * tab1 = new PdfPTable(9); tab1.setSpacingBefore(2);
			 * tab1.setWidthPercentage(new
			 * float[]{5,11,11,11,12,13,13,12,12},new Rectangle(100,100));
			 * 
			 * subcell = new PdfPCell(new Phrase("S.No.",new
			 * Font(Font.TIMES_ROMAN,5,Font.NORMAL)));
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("TDS Rs.",new Font(Font.TIMES_ROMAN,5,Font.NORMAL)));
			 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("Surcharge Rs.",new
			 * Font(Font.TIMES_ROMAN,5,Font.NORMAL)));
			 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("Education Cess Rs.",new
			 * Font(Font.TIMES_ROMAN,5,Font.NORMAL)));
			 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("Total tax deposited Rs.",new
			 * Font(Font.TIMES_ROMAN,5,Font.NORMAL)));
			 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("Cheque/DD No. (if any)",new
			 * Font(Font.TIMES_ROMAN,5,Font.NORMAL)));
			 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("BSR Code of Bank branch",new
			 * Font(Font.TIMES_ROMAN,5,Font.NORMAL)));
			 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("Date on which tax deposited (dd/mm/yy)",new
			 * Font(Font.TIMES_ROMAN,5,Font.NORMAL)));
			 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("Transfer voucher/Challan Identification No.",new
			 * Font(Font.TIMES_ROMAN,5,Font.NORMAL)));
			 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tab1.addCell(subcell);
			 * 
			 * taxRepBean.getDoc().add(tab1); para = new Paragraph(new Phrase(
			 * "I,     , son/daughter of                   working in the capacity of (designation)           do hereby "
			 * +
			 * "certify that a sum of Rs. "+UtilityDAO.trans(Math.round(taxRepBean
			 * .getTAXPAID()), "9,99,999", "", false,
			 * false)+" [Rupees (in words)] "
			 * +originalNumToLetter.getInWords(String
			 * .valueOf(taxRepBean.getTAXPAID
			 * ()))+" has been deducted at source and paid to the "+
			 * "credit of the Central Government. I further certify that the information given above is true and correct based on the books of account, documents and other available records."
			 * ,new Font(Font.TIMES_ROMAN,10)));
			 * para.setAlignment(Element.ALIGN_LEFT); para.setSpacingBefore(5);
			 * para.setSpacingAfter(5); taxRepBean.getDoc().add(para);
			 * 
			 * para = new Paragraph(new Phrase(
			 * "PLACE                                                                                               ______________________________"
			 * ,new Font(Font.TIMES_ROMAN,10)));
			 * para.setAlignment(Element.ALIGN_LEFT); para.setSpacingBefore(3);
			 * para.setSpacingAfter(3); taxRepBean.getDoc().add(para);
			 * 
			 * para = new Paragraph(new Phrase(
			 * "DATE                                                                                            Signature of the person responsible for deduction of tax"
			 * ,new Font(Font.TIMES_ROMAN,10)));
			 * para.setAlignment(Element.ALIGN_LEFT); para.setSpacingBefore(3);
			 * para.setSpacingAfter(3); taxRepBean.getDoc().add(para);
			 */
			Statement org = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ResultSet rsTmp1 = org.executeQuery("SELECT * FROM ORG_DETAILS");
			rsTmp1.next();
			PdfPTable tablast = new PdfPTable(4);
			tablast.setWidthPercentage(new float[] { 25, 25, 25, 25 },
					new Rectangle(100, 100));
			subcell = new PdfPCell(new Phrase("Verification", F10Bold));
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			// subcell.setBorderWidthTop(0);
			// subcell.setMinimumHeight(15);
			subcell.setColspan(4);
			tablast.addCell(subcell);

			subcell = new PdfPCell(
					new Phrase(
							"I "
									+ rsTmp1.getString("DEDUCTY_FNAME")
									+ " son/daughter of "
									+ rsTmp1.getString("DEDUCTY_MNAME")
									+ " "
									+ rsTmp1.getString("DEDUCTY_LNAME")
									+ " working in "
									+ " the capacity of "
									+ rsTmp1.getString("DEDUCTY_DESIG")
									+ " do hereby certify that the information "
									+ " given above is true, complete and correct and is based on the books of account, documents, TDS "
									+ " statements, and other available records.",
							F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setColspan(4);
			tablast.addCell(subcell);

			subcell = new PdfPCell(new Phrase("Place: NASHIK", F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setColspan(4);
			tablast.addCell(subcell);

			subcell = new PdfPCell(new Phrase(
					"Date: " + ReportDAO.getSysDate(), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setColspan(2);
			tablast.addCell(subcell);

			subcell = new PdfPCell(new Phrase(
					"(Signature of person responsible for deduction of tax)",
					F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setColspan(2);
			tablast.addCell(subcell);

			subcell = new PdfPCell(new Phrase("Designation: "
					+ rsTmp1.getString("DEDUCTY_DESIG"), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setColspan(2);
			tablast.addCell(subcell);

			subcell = new PdfPCell(new Phrase("Full Name : "
					+ rsTmp1.getString("DEDUCTY_FNAME") + " "
					+ rsTmp1.getString("DEDUCTY_MNAME") + " "
					+ rsTmp1.getString("DEDUCTY_LNAME"), F10Normal));
			subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			subcell.setColspan(2);
			tablast.addCell(subcell);

			taxRepBean.getDoc().add(tablast);
			taxRepBean.getDoc().newPage();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void pringapline(RepoartBean repBean, TaxReportBean taxRepBean) {
		ReportDAO.println("", 0, 1, false, "BANK", repBean);
	}

	public static void print1234bar(RepoartBean repBean,
			TaxReportBean taxRepBean) {
		ReportDAO
				.println("|", taxRepBean.getC_ul0(), 0, false, "BANK", repBean);
		ReportDAO
				.println("|", taxRepBean.getC_ul1(), 0, false, "BANK", repBean);
		ReportDAO
				.println("|", taxRepBean.getC_ul2(), 0, false, "BANK", repBean);
		ReportDAO
				.println("|", taxRepBean.getC_ul3(), 0, false, "BANK", repBean);
		ReportDAO
				.println("|", taxRepBean.getC_ul4(), 1, false, "BANK", repBean);
	}

	public static void print234bar(RepoartBean repBean, TaxReportBean taxRepBean) {

		ReportDAO
				.println("|", taxRepBean.getC_ul2(), 0, false, "BANK", repBean);
		ReportDAO
				.println("|", taxRepBean.getC_ul3(), 0, false, "BANK", repBean);
		ReportDAO
				.println("|", taxRepBean.getC_ul4(), 1, false, "BANK", repBean);
	}

	public static void print34bar(RepoartBean repBean, TaxReportBean taxRepBean) {

		ReportDAO
				.println("|", taxRepBean.getC_ul3(), 0, false, "BANK", repBean);
		ReportDAO
				.println("|", taxRepBean.getC_ul4(), 1, false, "BANK", repBean);
	}

	public static float calHraExempt(int peno, float basicAmt, float hraAmt) {
		Connection con = ConnectionManager.getConnection();
		ResultSet rsTmp = null;
		ResultSet rsTmp1 = null;
		float metroCal = 0;
		float rentCal = 0;
		float hraExmpt = 0;
		try {

			Statement st = con.createStatement();
			rsTmp = st.executeQuery("select INP_AMT  from ytdtran where EMPNO="
					+ peno + " and TRNCD =564");
			if (rsTmp.next()) {
				if (rsTmp.getFloat(1) == 1.0) {
					metroCal = basicAmt * 5 / 10;
				} else if (rsTmp.getFloat(1) == 2.0) {
					metroCal = basicAmt * 4 / 10;
				}
			}
			Statement st1 = con.createStatement();
			rsTmp1 = st1
					.executeQuery("select INP_AMT  from ytdtran where EMPNO="
							+ peno + " and TRNCD =565");
			if (rsTmp1.next()) {
				rentCal = rsTmp1.getFloat(1) - (basicAmt / 10);
			}
			System.out.println("hra=" + hraAmt + ",metro=" + metroCal
					+ ",rent=" + rentCal);
			hraExmpt = ((metroCal <= hraAmt) && (metroCal <= rentCal)) ? metroCal
					: (hraAmt <= rentCal) ? hraAmt : rentCal;
			Statement st2 = con.createStatement();
			rsTmp1 = st2.executeQuery("select * from ytdtran where EMPNO="
					+ peno + " and TRNCD =563");
			if (rsTmp1.next()) {
				Statement st3 = con.createStatement();
				st3.execute("update ytdtran set inp_amt=" + hraExmpt
						+ ", cal_amt=" + hraExmpt + ", net_amt=" + hraExmpt
						+ ", upddt='" + ReportDAO.getSysDate()
						+ "' where EMPNO=" + peno + " and TRNCD =563");
			} else {
				Statement st3 = con.createStatement();
				st3.execute("insert into ytdtran (EMPNO,TRNCD,srno,inp_amt,cal_amt,net_amt,trndt,upddt) values("
						+ peno
						+ ",563,0,"
						+ hraExmpt
						+ ","
						+ hraExmpt
						+ ","
						+ hraExmpt
						+ ",'2014-05-15','"
						+ ReportDAO.getSysDate()
						+ "')");
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("hraExmpt" + hraExmpt);
		return hraExmpt;
	}

	public void printBonusReport(String date, String filepath) {
		/* status = status.equalsIgnoreCase("nonactive")?"N":"A"; */
		/**
		 * @author Shivaji Disha Technologies
		 */
		int year = Integer.parseInt(date.substring(7, 11));
		try {
			Connection cn = ConnectionManager.getConnection();
			Statement st = cn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Statement st1 = cn.createStatement();
			ResultSet rs = null;

			Document doc = new Document(new Rectangle(900, 700));

			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(
					filepath));
			doc.open();
			// Image image1 = Image.getInstance(imgPath);
			Phrase title = new Phrase("THE BUSINESS CO.OP BANK LTD.",
					new Font(Font.TIMES_ROMAN, 12, Font.BOLD));
			Paragraph para = new Paragraph(title);
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingBefore(0);

			/*
			 * image1.scaleAbsolute(60f,50f); image1.setAbsolutePosition(40f,
			 * 750f);
			 * 
			 * doc.add(image1);
			 */
			doc.add(para);

			para = new Paragraph(
					new Phrase(
							"Rajan Complex Arcade, Datta Mandir Circle, Nashik Road, Nashik - 422101",
							new Font(Font.TIMES_ROMAN, 8)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);

			doc.add(para);
			para = new Paragraph(new Phrase(
					"Tel : 0253-2406100, 2469545",
					new Font(Font.TIMES_ROMAN, 8)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);

			doc.add(para);
//			para = new Paragraph(new Phrase("Email : adm@namcobank.in",
//					new Font(Font.TIMES_ROMAN, 8)));
//			para.setAlignment(Element.ALIGN_CENTER);
//			para.setSpacingAfter(0);
//
//			doc.add(para);
			para = new Paragraph(new Phrase(
					"Employee's Yearly Bonus Report for Financial Year "
							+ ReportDAO.Boy(date).substring(7) + " - "
							+ ReportDAO.Eoy(date).substring(7), new Font(
							Font.TIMES_ROMAN, 10)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(10);

			SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
			doc.add(para);
			PdfPTable table = new PdfPTable(19);
			table.setSpacingBefore(10.0f);

			table.setWidthPercentage(new float[] { 15, 20, 70, 45, 45, 35, 35,
					35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 45, 39 },
					new Rectangle(650, 100));

			PdfPCell cell1 = new PdfPCell(new Phrase("Sr No.", new Font(
					Font.TIMES_ROMAN, 8)));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell1);

			PdfPCell cell2 = new PdfPCell(new Phrase("Emp Code", new Font(
					Font.TIMES_ROMAN, 8)));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell2);

			PdfPCell cell3 = new PdfPCell(new Phrase("EMP Name", new Font(
					Font.TIMES_ROMAN, 8)));
			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell3);

			PdfPCell cell4 = new PdfPCell(new Phrase("DOJ", new Font(
					Font.TIMES_ROMAN, 8)));
			cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell4);

			PdfPCell cell5 = new PdfPCell(new Phrase("DOL", new Font(
					Font.TIMES_ROMAN, 8)));
			cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell5);

			PdfPCell cell6 = new PdfPCell(new Phrase("Apr", new Font(
					Font.TIMES_ROMAN, 9)));
			cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell6);

			PdfPCell cell7 = new PdfPCell(new Phrase("May", new Font(
					Font.TIMES_ROMAN, 9)));
			cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell7);

			PdfPCell cell8 = new PdfPCell(new Phrase("Jun", new Font(
					Font.TIMES_ROMAN, 9)));
			cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell8);

			PdfPCell cell9 = new PdfPCell(new Phrase("Jul", new Font(
					Font.TIMES_ROMAN, 9)));
			cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell9);

			PdfPCell cell10 = new PdfPCell(new Phrase("Aug", new Font(
					Font.TIMES_ROMAN, 9)));
			cell10.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell10);

			PdfPCell cell11 = new PdfPCell(new Phrase("Sep", new Font(
					Font.TIMES_ROMAN, 9)));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell11);

			PdfPCell cell12 = new PdfPCell(new Phrase("Oct", new Font(
					Font.TIMES_ROMAN, 9)));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell12);

			PdfPCell cell13 = new PdfPCell(new Phrase("Nov", new Font(
					Font.TIMES_ROMAN, 9)));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell13);

			PdfPCell cell14 = new PdfPCell(new Phrase("Dec", new Font(
					Font.TIMES_ROMAN, 9)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell14);

			PdfPCell cell15 = new PdfPCell(new Phrase("Jan", new Font(
					Font.TIMES_ROMAN, 9)));
			cell15.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell15);

			PdfPCell cell16 = new PdfPCell(new Phrase("Feb", new Font(
					Font.TIMES_ROMAN, 9)));
			cell16.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell16);

			PdfPCell cell17 = new PdfPCell(new Phrase("Mar", new Font(
					Font.TIMES_ROMAN, 9)));
			cell17.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell17);

			PdfPCell cell18 = new PdfPCell(new Phrase("Total", new Font(
					Font.TIMES_ROMAN, 9)));
			cell18.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell18);

			PdfPCell cell19 = new PdfPCell(new Phrase("Bonus", new Font(
					Font.TIMES_ROMAN, 9)));
			cell19.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell19);

			table.setHeaderRows(1);

			int srno = 1;
			double totatlbonus = 0;

			/*
			 * String sql2=
			 * "with bonus as(select y.trncd,disc,e.empno, sum(case datepart(MM,trndt) when 04 then NET_AMT else  0 end) apr, "
			 * +
			 * "sum(case datepart(MM,trndt) when 05 then NET_AMT else  0 end) may, "
			 * +
			 * "sum(case datepart(MM,trndt) when 06 then NET_AMT else  0 end) jun, "
			 * +
			 * "sum(case datepart(MM,trndt) when 07 then NET_AMT else  0 end) jul, "
			 * +
			 * "sum(case datepart(MM,trndt) when 08 then NET_AMT else  0 end) aug, "
			 * +
			 * "sum(case datepart(MM,trndt) when 09 then NET_AMT else  0 end) sep, "
			 * +
			 * "sum(case datepart(MM,trndt) when 10 then NET_AMT else  0 end) oct, "
			 * +
			 * "sum(case datepart(MM,trndt) when 11 then NET_AMT else  0 end) nov, "
			 * +
			 * "sum(case datepart(MM,trndt) when 12 then NET_AMT else  0 end) dec, "
			 * +
			 * "sum(case datepart(MM,trndt) when 01 then NET_AMT else  0 end) jan, "
			 * +
			 * "sum(case datepart(MM,trndt) when 02 then NET_AMT else  0 end) feb, "
			 * +
			 * "sum(case datepart(MM,trndt) when 03 then NET_AMT else  0 end) mar  "
			 * + "from empmast e, cdmast c, ytdtran y where e.empno = y.empno "
			 * + "and y.trncd = c.trncd and e.status='"+status+
			 * "' and y.trndt between '"
			 * +ReportDAO.Boy(date)+"' and '"+ReportDAO.Eoy(date)+"' " +
			 * "and y.trncd = c.trncd  and y.trndt between '"
			 * +ReportDAO.Boy(date)+"' and '"+ReportDAO.Eoy(date)+"' " +
			 * "and y.trncd in(select onamtcd from onamt where trncd=143) and c.trncd in(select onamtcd from onamt where trncd=143) "
			 * + "group by  e.empno,fname,lname,disc,y.trncd ) " +
			 * "select b.empno,rtrim(e.fname)+' '+rtrim(e.lname) name,E.DOJ,E.DOL,E.empcode,SUM (b.apr) as apramt ,"
			 * +
			 * "SUM (b.may) as mayamt, SUM (b.jun) as junamt, SUM (b.jul) as julamt,SUM (b.aug) as augamt, "
			 * +
			 * "SUM (b.sep) as sepamt ,SUM (b.oct) as octamt ,SUM (b.nov) as novamt ,SUM (b.dec) as decamt , "
			 * +
			 * "SUM (b.jan) as janamt,SUM (b.feb) as febamt ,SUM (b.mar) as maramt, "
			 * +
			 * "SUM(b.apr+b.may+b.jun+b.jul+b.aug+b.sep+b.oct+b.nov+b.dec+b.jan+b.feb+b.mar) as grand_total, "
			 * +
			 * "((SUM(b.apr+b.may+b.jun+b.jul+b.aug+b.sep+b.oct+b.nov+b.dec+b.jan+b.feb+b.mar))/100*( select per from slab where trncd=143)) as bonus_amt "
			 * +
			 * "from bonus b ,EMPMAST E where b.EMPNO=E.empno group by b.empno,E.fname,E.lname,E.DOJ,E.DOL,E.empcode order by e.empcode "
			 * ;
			 */
			String sql2 = "with bonus as(select e.empno, "
					+ "sum(case datepart(MM,trndt) when 04 then NET_AMT else  0 end) apr, "
					+ "sum(case datepart(MM,trndt) when 05 then NET_AMT else  0 end) may,"
					+ "sum(case datepart(MM,trndt) when 06 then NET_AMT else  0 end) jun, "
					+ "sum(case datepart(MM,trndt) when 07 then NET_AMT else  0 end) jul,"
					+ "sum(case datepart(MM,trndt) when 08 then NET_AMT else  0 end) aug,"
					+ "sum(case datepart(MM,trndt) when 09 then NET_AMT else  0 end) sep,"
					+ "sum(case datepart(MM,trndt) when 10 then NET_AMT else  0 end) oct,"
					+ "sum(case datepart(MM,trndt) when 11 then NET_AMT else  0 end) nov,"
					+ "sum(case datepart(MM,trndt) when 12 then NET_AMT else  0 end) dec,"
					+ "sum(case datepart(MM,trndt) when 01 then NET_AMT else  0 end) jan,"
					+ "sum(case datepart(MM,trndt) when 02 then NET_AMT else  0 end) feb,"
					+ "sum(case datepart(MM,trndt) when 03 then NET_AMT else  0 end) mar"
					+ " from empmast e, cdmast c, paytran_stage y where e.empno = y.empno "
					+ " and y.trncd = c.trncd  and y.trndt between '"
					+ ReportDAO.Boy(date)
					+ "' and '"
					+ ReportDAO.Eoy(date)
					+ "' "
					+ " and y.trncd in(101,102,103,138) group by e.empno) , "
					+ " lwp as (select e.empno, "
					+ "sum(case datepart(MM,trndt) when 04 then NET_AMT else  0 end) aprlwp,"
					+ "sum(case datepart(MM,trndt) when 05 then NET_AMT else  0 end) maylwp,"
					+ "sum(case datepart(MM,trndt) when 06 then NET_AMT else  0 end) junlwp, "
					+ "sum(case datepart(MM,trndt) when 07 then NET_AMT else  0 end) jullwp,"
					+ "sum(case datepart(MM,trndt) when 08 then NET_AMT else  0 end) auglwp,"
					+ "sum(case datepart(MM,trndt) when 09 then NET_AMT else  0 end) seplwp,"
					+ "sum(case datepart(MM,trndt) when 10 then NET_AMT else  0 end) octlwp, "
					+ "sum(case datepart(MM,trndt) when 11 then NET_AMT else  0 end) novlwp,"
					+ "sum(case datepart(MM,trndt) when 12 then NET_AMT else  0 end) declwp,"
					+ "sum(case datepart(MM,trndt) when 01 then NET_AMT else  0 end) janlwp,"
					+ "sum(case datepart(MM,trndt) when 02 then NET_AMT else  0 end) feblwp,"
					+ "sum(case datepart(MM,trndt) when 03 then NET_AMT else  0 end) marlwp"
					+ " from empmast e, cdmast c, paytran_stage y where e.empno = y.empno "
					+ " and y.trncd = c.trncd  and y.trndt between '"
					+ ReportDAO.Boy(date)
					+ "'"
					+ " and '"
					+ ReportDAO.Eoy(date)
					+ "' and y.trncd in (129,140,141,142,243) group by e.empno )  "
					+ " select b.empno,rtrim(e.fname)+' '+rtrim(e.lname) name,"
					+ "E.DOJ,E.DOL,E.empcode, (b.apr-l.aprlwp) as apramt , (b.may-l.maylwp) as mayamt, "
					+ "(b.jun-l.junlwp) as junamt,  (b.jul-l.jullwp) as julamt,"
					+ " (b.aug-l.auglwp) as augamt, (b.sep-l.seplwp) as sepamt , (b.oct-l.octlwp) "
					+ " as octamt , (b.nov-l.novlwp) as novamt , (b.dec-l.declwp) as decamt , "
					+ " (b.jan-l.janlwp) as janamt, (b.feb-l.feblwp) as febamt, (b.mar-l.marlwp)"
					+ " as maramt, "
					+ " (b.apr+b.may+b.jun+b.jul+b.aug+b.sep+b.oct+b.nov+b.dec+b.jan+b.feb+b.mar "
					+ " -l.aprlwp-l.maylwp-l.junlwp-l.jullwp-l.auglwp-l.seplwp- "
					+ " l.octlwp-l.novlwp-l.declwp-l.janlwp-l.feblwp-l.marlwp) as grand_total, "
					+ "(((b.apr+b.may+b.jun+b.jul+b.aug+b.sep+b.oct+b.nov+b.dec+b.jan+b.feb+b.mar "
					+ " -l.aprlwp-l.maylwp-l.junlwp-l.jullwp-l.auglwp-l.seplwp- "
					+ "l.octlwp-l.novlwp-l.declwp-l.janlwp-l.feblwp-l.marlwp)) "
					+ " /100*(select _percentage from bonuspercent where bonusYear='"
					+ year
					+ "-"
					+ (year + 1)
					+ "')) "
					+ " as bonus_amt from bonus  b ,EMPMAST E ,lwp l where b.EMPNO=E.empno"
					+ "  and l.empno=E.empno order by E.empcode  ";

			System.out.println("  " + sql2);
			rs = st.executeQuery(sql2);

			while (rs.next()) {

				table.setSpacingBefore(10.0f);
				table.setWidthPercentage(new float[] { 15, 20, 70, 45, 45, 35,
						35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 39, 39 },
						new Rectangle(650, 100));

				PdfPCell cell1data1 = new PdfPCell(new Phrase(" " + srno,
						new Font(Font.TIMES_ROMAN, 8)));
				cell1data1.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell1data1);

				PdfPCell cell2data2 = new PdfPCell(new Phrase(""
						+ rs.getString("empcode"),
						new Font(Font.TIMES_ROMAN, 8)));
				cell2data2.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell2data2);

				PdfPCell cell3data3 = new PdfPCell(new Phrase(""
						+ rs.getString("name"), new Font(Font.TIMES_ROMAN, 8)));
				cell3data3.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell3data3);

				PdfPCell cell4data4 = new PdfPCell(new Phrase(""
						+ rs.getDate("DOJ") == null ? "" : format.format(rs
						.getDate("DOJ")), new Font(Font.TIMES_ROMAN, 8)));
				cell4data4.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell4data4);

				String dol = rs.getString("DOL") == null
						|| rs.getString("DOL") == "" ? "31-Dec-2099" : format
						.format(rs.getDate("DOL"));

				dol = dol.equalsIgnoreCase("31-Dec-2099") ? "" : dol;
				PdfPCell cell5data5 = new PdfPCell(new Phrase("" + dol,
						new Font(Font.TIMES_ROMAN, 8)));
				cell5data5.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell5data5);

				PdfPCell cell6data6 = new PdfPCell(
						new Phrase(" " + rs.getString("apramt"), new Font(
								Font.TIMES_ROMAN, 8)));
				cell6data6.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell6data6);

				PdfPCell cell7data7 = new PdfPCell(
						new Phrase(" " + rs.getString("mayamt"), new Font(
								Font.TIMES_ROMAN, 8)));
				cell7data7.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell7data7);

				PdfPCell cell8data8 = new PdfPCell(
						new Phrase(" " + rs.getString("junamt"), new Font(
								Font.TIMES_ROMAN, 8)));
				cell8data8.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell8data8);

				PdfPCell cell9data9 = new PdfPCell(
						new Phrase(" " + rs.getString("julamt"), new Font(
								Font.TIMES_ROMAN, 8)));
				cell9data9.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell9data9);

				PdfPCell cell10data10 = new PdfPCell(
						new Phrase(" " + rs.getString("augamt"), new Font(
								Font.TIMES_ROMAN, 8)));
				cell10data10.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell10data10);

				PdfPCell cell11data11 = new PdfPCell(
						new Phrase(" " + rs.getString("sepamt"), new Font(
								Font.TIMES_ROMAN, 8)));
				cell11data11.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell11data11);

				PdfPCell cell12data12 = new PdfPCell(
						new Phrase(" " + rs.getString("octamt"), new Font(
								Font.TIMES_ROMAN, 8)));
				cell12data12.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell12data12);

				PdfPCell cell13data13 = new PdfPCell(
						new Phrase(" " + rs.getString("novamt"), new Font(
								Font.TIMES_ROMAN, 8)));
				cell13data13.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell13data13);

				PdfPCell cell14data14 = new PdfPCell(
						new Phrase(" " + rs.getString("decamt"), new Font(
								Font.TIMES_ROMAN, 8)));
				cell14data14.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell14data14);

				PdfPCell cell15data15 = new PdfPCell(
						new Phrase(" " + rs.getString("janamt"), new Font(
								Font.TIMES_ROMAN, 8)));
				cell15data15.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell15data15);

				PdfPCell cell16data16 = new PdfPCell(
						new Phrase(" " + rs.getString("febamt"), new Font(
								Font.TIMES_ROMAN, 8)));
				cell16data16.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell16data16);

				PdfPCell cell17data17 = new PdfPCell(
						new Phrase(" " + rs.getString("maramt"), new Font(
								Font.TIMES_ROMAN, 8)));
				cell17data17.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell17data17);

				PdfPCell cell18data18 = new PdfPCell(new Phrase(" "
						+ rs.getString("grand_total"), new Font(
						Font.TIMES_ROMAN, 8)));
				cell18data18.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell18data18);

				PdfPCell cell19data19 = new PdfPCell(new Phrase(" "
						+ Math.round(rs.getInt("bonus_amt")), new Font(
						Font.TIMES_ROMAN, 8)));
				cell19data19.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell19data19);

				totatlbonus = totatlbonus + Math.round(rs.getInt("bonus_amt"));

				srno++;
			}

			System.out.println("totatlbonus" + totatlbonus);
			PdfPCell total = new PdfPCell(new Phrase("Total Bonus:", new Font(
					Font.TIMES_ROMAN, 9)));
			total.setHorizontalAlignment(Element.ALIGN_RIGHT);
			total.setColspan(18);
			table.addCell(total);
			PdfPCell totaldata = new PdfPCell(new Phrase("" + totatlbonus,
					new Font(Font.TIMES_ROMAN, 9)));
			totaldata.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(totaldata);
			System.out.println("hiiiii");
			doc.add(table);
			System.out.println("hey");
			// doc.add(Chunk.NEWLINE);
			doc.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void CtcReportNew(String list, String date, String imgpath,
			String filepath) {
		try {
			Connection cn = ConnectionManager.getConnection();
			Statement st = cn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Statement st1 = cn.createStatement();
			ResultSet rs = null;

			Document doc = new Document();
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(
					filepath));
			// writer.setEncryption(userPass.getBytes(), ownerPass.getBytes(),
			// PdfWriter.ALLOW_PRINTING,PdfWriter.ENCRYPTION_AES_128);
			doc.open();
			// doc.setMargins(10, 72, 108, 180);
			doc.setPageSize(PageSize.A4);
			Image image1 = Image.getInstance(imgpath);

			Font FONT = new Font(Font.HELVETICA, 40, Font.NORMAL,
					new GrayColor(0.75f));
			ColumnText.showTextAligned(writer.getDirectContentUnder(),
					Element.ALIGN_CENTER, new Phrase("Ch.Shahu Hospital", FONT),
					297.5f, 421, 45);

			Phrase title = new Phrase("Rajeshri Chatrapati Shahu Maharaj hospital,Jalgaon",
					new Font(Font.TIMES_ROMAN, 12, Font.BOLD));
			Paragraph para = new Paragraph(title);
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingBefore(0);

			image1.scaleAbsolute(60f, 50f);
			image1.setAbsolutePosition(40f, 750f);

			doc.add(image1);
			doc.add(para);

			para = new Paragraph(
					new Phrase(
							"Rajeshri Chatrapati Shahu Maharaj hospital, Shahu Nagar, Jalgaon-425001",
							new Font(Font.TIMES_ROMAN, 8)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);

			doc.add(para);
			para = new Paragraph(new Phrase(
					"Tel : 0257 2223301",
					new Font(Font.TIMES_ROMAN, 8)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);

			doc.add(para);
//			para = new Paragraph(new Phrase("Email : adm@namcobank.in",
//					new Font(Font.TIMES_ROMAN, 8)));
//			para.setAlignment(Element.ALIGN_CENTER);
//			para.setSpacingAfter(0);
//
//			doc.add(para);

			para = new Paragraph(new Phrase("Employee's  CTC Report :- "
					+ ReportDAO.getSysDate(), new Font(Font.TIMES_ROMAN, 10)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(50);

			doc.add(para);

			String sql2 = "select empno from empmast where empno in (" + list
					+ ")";
			Statement st2 = cn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs2 = st2.executeQuery(sql2);
			while (rs2.next()) {

				String sql = " SELECT EMPNO, TRNCD, INP_AMT, PF, PT , ESIC from CTCDISPLAY where empno="
						+ rs2.getInt(1) + " ";
				rs = st.executeQuery(sql);

				String empname = "select empcode,salute,empno,rtrim(fname)+' '+rtrim(mname)+' '+rtrim(lname) name,doj from empmast "
						+ "Where empno=" + rs2.getInt(1);
				ResultSet rs1 = st1.executeQuery(empname);

				PdfPTable tab = new PdfPTable(6);
				tab.setWidthPercentage(new float[] { 6, 22.5f, 15.0f, 15.0f,
						15.0f, 15.0f }, new Rectangle(100, 100));
				tab.setHorizontalAlignment(Element.ALIGN_CENTER);

				PdfPCell cell1 = new PdfPCell();
				cell1.setColspan(6);

				LookupHandler lkhp = new LookupHandler();
				PdfPCell[] cell = null;
				cell = new PdfPCell[6];
				if (rs1.next() && rs.next()) {

					PdfPTable tab1 = new PdfPTable(2);
					tab1.setWidthPercentage(new float[] { 70, 30 },
							new Rectangle(100, 100));

					PdfPCell subcell = new PdfPCell(new Phrase(
							"Employee Code : " + rs1.getString("empcode"),
							new Font(Font.TIMES_ROMAN, 10, Font.BOLD)));
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tab1.addCell(subcell);
					subcell = new PdfPCell(new Phrase("Date : " + date,
							new Font(Font.TIMES_ROMAN, 10, Font.BOLD)));
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tab1.addCell(subcell);

					subcell = new PdfPCell(new Phrase("Employee Name : "
							+ lkhp.getLKP_Desc("SALUTE", rs1.getInt("SALUTE"))
							+ " " + rs1.getString("name"), new Font(
							Font.TIMES_ROMAN, 10, Font.BOLD)));
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tab1.addCell(subcell);
					subcell = new PdfPCell(new Phrase("D.O.J : "
							+ EmployeeHandler.dateFormat(rs1.getDate("DOJ")),
							new Font(Font.TIMES_ROMAN, 10, Font.BOLD)));
					subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tab1.addCell(subcell);

					cell1.addElement(tab1);
					tab.addCell(cell1);

					cell[0] = new PdfPCell(new Phrase("SR.NO", new Font(
							Font.TIMES_ROMAN, 8, Font.BOLD)));
					cell[1] = new PdfPCell(new Phrase("DESCRIPTION", new Font(
							Font.TIMES_ROMAN, 8, Font.BOLD)));
					cell[2] = new PdfPCell(new Phrase("AMOUNT", new Font(
							Font.TIMES_ROMAN, 8, Font.BOLD)));
					cell[3] = new PdfPCell(new Phrase("PF", new Font(
							Font.TIMES_ROMAN, 8, Font.BOLD)));
					cell[4] = new PdfPCell(new Phrase("PT", new Font(
							Font.TIMES_ROMAN, 8, Font.BOLD)));
					cell[5] = new PdfPCell(new Phrase("ESIC", new Font(
							Font.TIMES_ROMAN, 8, Font.BOLD)));

					for (int i = 0; i < 6; i++) {
						cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
						tab.addCell(cell[i]);
					}
				}
				int cnt = 1;
				rs.beforeFirst();
				while (rs.next()) {
					cell = new PdfPCell[6];
					for (int i = 0; i < 6; i++) {
						if (i == 0) {
							cell[i] = new PdfPCell(new Phrase("" + cnt,
									new Font(Font.TIMES_ROMAN, 7)));
							cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
							cell[i].setFixedHeight(16);
						}

						else {
							if (i == 1) {
								cell[i] = new PdfPCell(new Phrase(
										lkhp.getCode_Desc(rs.getString(2)),
										new Font(Font.TIMES_ROMAN, 7)));
							}

							else {
								if (rs.getString(i + 1).equals("1")) {
									cell[i] = new PdfPCell(new Phrase(
											"Applicable", new Font(
													Font.TIMES_ROMAN, 7)));
								}

								else if (rs.getString(i + 1).equals("0")) {
									cell[i] = new PdfPCell(new Phrase(
											"Not Applicable", new Font(
													Font.TIMES_ROMAN, 7)));
								}

								else {
									cell[i] = new PdfPCell(new Phrase(
											rs.getString(i + 1), new Font(
													Font.TIMES_ROMAN, 7)));
								}
							}
						}
						if (i > 1) {
							cell[i].setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
						tab.addCell(cell[i]);

					}
					cnt++;
				}
				tab.setSpacingAfter(50);
				doc.add(tab);
				doc.add(Chunk.NEWLINE);

				if (cnt % 2 == 0) {
					doc.newPage();
					tab.setSpacingAfter(50);

				}
			}
			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getmonthname(int month) {
		String monthname = "";
		if (month == 1)
			monthname = "JAN";
		else if (month == 2)
			monthname = "FEB";
		else if (month == 3)
			monthname = "MAR";
		else if (month == 4)
			monthname = "APR";
		else if (month == 5)
			monthname = "MAY";
		else if (month == 6)
			monthname = "JUN";
		else if (month == 7)
			monthname = "JUL";
		else if (month == 8)
			monthname = "AUG";
		else if (month == 9)
			monthname = "SEP";
		else if (month == 10)
			monthname = "OCT";
		else if (month == 11)
			monthname = "NOV";
		else if (month == 12)
			monthname = "DEC";

		return monthname;

	}

	public static void buildsqlsumofquery(int peno, String prdt,
			String itgrpcd, TaxReportBean taxRepBean) {

		{

			System.out.println("ITGROUP : "+itgrpcd);
			// System.out.println("checking bonmus is yes or not for acdmic year date........"+prdt);
			taxRepBean
					.setYtdstr("SELECT Y.EMPNO,Y.TRNCD,SUM(y.net_amt) NET_AMT,C.DISC FROM YTDTRAN Y,CDMAST C WHERE Y.EMPNO="
							+ peno
							+ " "
							+ "AND Y.TRNCD=C.TRNCD AND C.ITGRP LIKE '"
							+ itgrpcd
							+ "' AND Y.TRNDT BETWEEN "
							+ " '"
							+ ReportDAO.BoFinancialy(prdt)
							+ "' AND '"
							+ ReportDAO.EoFinancialy(prdt)
							+ "'  group by Y.EMPNO,Y.TRNCD,c.DISC");
			
			System.out.println("HELLO PRASAD");
			  System.out.println("SELECT Y.EMPNO,Y.TRNCD,SUM(y.net_amt) NET_AMT,C.DISC FROM YTDTRAN Y,CDMAST C WHERE Y.EMPNO="
						+ peno
						+ " "
						+ "AND Y.TRNCD=C.TRNCD AND C.ITGRP LIKE '"
						+ itgrpcd
						+ "' AND Y.TRNDT BETWEEN "
						+ " '"
						+ ReportDAO.BoFinancialy(prdt)
						+ "' AND '"
						+ ReportDAO.EoFinancialy(prdt)
						+ "'  group by Y.EMPNO,Y.TRNCD,c.DISC");
			 
			  
			//System.out.println("3160");
			
		/*	System.out.println("SELECT Y.EMPNO,Y.TRNCD,SUM(y.net_amt) NET_AMT,C.DISC FROM YTDTRAN Y,CDMAST C WHERE Y.EMPNO="
							+ peno
							+ " "
							+ "AND Y.TRNCD=C.TRNCD AND C.ITGRP LIKE '"
							+ itgrpcd
							+ "' AND Y.TRNDT BETWEEN "
							+ " '"
							+ ReportDAO.BoFinancialy(prdt)
							+ "' AND '"
							+ ReportDAO.EoFinancialy(prdt)
							+ "'  group by Y.EMPNO,Y.TRNCD,c.DISC");
		*/	
		}

	}

	public static void YearlyEarning(String empcode, int peno, String prdt,
			String pename, String sex, RepoartBean repBean,
			TaxReportBean taxRepBean) {

		System.out.println("Welcome in Yearlyearning...............");
		
		int totamt = 0;
		int OTHER_DED = 0;
		int tot499 = 0;
		int DED80 = 0;
		int reb88d = 0;
		String slbstr = "";
		int reb88 = 0;
		ResultSet slb = null;
		int totinc = 0;
		int tax = 0;
		int totded = 0;
		int gross_tax_amt = 0;
		int tax_amtiii = 0;
		int tax88_rebate = 0;
		int N = 0;
		taxRepBean.setYtd(null);
		try {
			Phrase title = new Phrase("Tax Sheet", F10Bold);
			Paragraph para = new Paragraph(title);
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingBefore(0);
			taxRepBean.getDoc().add(para);

			/*
			 * para = new Paragraph(new
			 * Phrase("Financial  Year "+ReportDAO.Boy(prdt
			 * ).substring(7,11)+" - "
			 * +ReportDAO.Eoy(ReportDAO.Boy(prdt)).substring
			 * (7,11)+" Assessment Year "+ReportDAO.Boy(ReportDAO.Eoy(prdt) +
			 * 10) +" - "+ReportDAO.Eoy(ReportDAO.Eoy(prdt) + 10),F10Bold));
			 * para.setAlignment(Element.ALIGN_CENTER);
			 * 
			 * 
			 * taxRepBean.getDoc().add(para); para = new Paragraph(new
			 * Phrase("Employee Code : " +empcode + " Name :" +
			 * pename,F10Bold)); para.setAlignment(Element.ALIGN_CENTER);
			 * para.setSpacingAfter(10);
			 * 
			 * taxRepBean.getDoc().add(para);
			 */

			PdfPTable tab1 = new PdfPTable(5);
			tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
					new Rectangle(100, 100));
			/*
			 * PdfPCell subcell = new PdfPCell(new
			 * Phrase("Trn Code ",F10Normal)); subcell.setBorderWidthLeft(0);
			 * subcell.setBackgroundColor(new GrayColor(0.90f));
			 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("(I) INCOME FROM SALARY  :- ",F10Normal));
			 * subcell.setBorderWidthLeft(0); subcell.setBackgroundColor(new
			 * GrayColor(0.90f));
			 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("Actual Amt(Rs)",F10Normal));
			 * subcell.setBorderWidthRight(0); subcell.setBackgroundColor(new
			 * GrayColor(0.90f));
			 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("Projected Amt(Rs)",F10Normal));
			 * subcell.setBorderWidthRight(0); subcell.setBackgroundColor(new
			 * GrayColor(0.90f));
			 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("Total Amt(Rs)",F10Normal));
			 * subcell.setBorderWidthRight(0); subcell.setBackgroundColor(new
			 * GrayColor(0.90f));
			 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tab1.addCell(subcell); taxRepBean.getDoc().add(tab1);
			 */

			taxRepBean.setC4tot(0);
			taxRepBean.setC5tot(0);
			taxRepBean.setC3tot(0);
			taxRepBean.setTotamt(0);
			taxRepBean.setGnm("A1");
			buildsql(peno, prdt, "A1%", taxRepBean);
			yearlrearningprintgrp(peno, prdt, repBean, taxRepBean);

			tax_amtiii = 0;
			taxRepBean.setGnm("");
			totinc = taxRepBean.getC5tot();
			tax_amtiii = totinc;
			int leaveencashamt = 0;
			int leavebalnce = 0;
			int old_encash_day = 0;
			//System.out.println("start leave encash...");
			Statement stleave = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Statement encashst = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Statement encashst1 = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rsleave1 = null;
			ResultSet rsleave2 = null;
			ResultSet rsleave3 = null;
			String EncashBal =""
					+ "(select SUM(DAYS) as DAYS from leavetran where empno="+peno+" "
					+ "and LEAVECD=1 and LREASON='Leave encash' and TRNTYPE='D' and LEAVEPURP=4 "
					+ "and TRNDATE between (SELECT (convert(nvarchar(5), YEAR(getdate()))+'-01-01')) and (SELECT (convert(nvarchar(5), YEAR(getdate()))+'-12-31')))";
			rsleave3 = encashst.executeQuery(EncashBal);
			if (rsleave3.next()) {
				leavebalnce = rsleave3.getInt("DAYS");
			}
			else{
			String encash_days = (" SELECT Ceiling ((SELECT crlim FROM   leavemass "
					+ " WHERE  leavecd = 1 AND status = 'A') - (SELECT CASE when Isnull(Sum(net_amt),0)<24 "
					+ " then 0 else Isnull(Sum(net_amt),0) "
					+ " end as Net_amt FROM   paytran_stage "
					+ " WHERE  trncd IN ( 301, 302 ) "
					+ "AND trndt BETWEEN   '01-Jan-"+ReportDAO.Boy(prdt).substring(7, 11)+"' AND  '31-Dec-"+ReportDAO.Boy(prdt).substring(7, 11)+"' "
					+ " AND empno = " + peno + " ) * 2.5 * 12  / 365)  AS finalDays ");
			System.out.println("creadit old days..." + encash_days);
			rsleave1 = encashst.executeQuery(encash_days);
			if (rsleave1.next()) {
				old_encash_day = rsleave1.getInt("finalDays");
				
			} else {
				old_encash_day = 0;
			}
			System.out.println("TO be creadited=="+old_encash_day);
			// chnged month here.... make it 01-01-year later when creadit leave
			// here taken new encashed days and then minus leaves taken in that current year
			/*String checkbal = ("if exists (select EMPNO,DAYS as encash_days from leavetran "
					+ "where LEAVECD=1 and TRNDATE between '"
					+ ReportDAO.Boy(prdt).substring(7, 11)
					+ "-01-01' and '"
					+ ReportDAO.Boy(prdt).substring(7, 11)
					+ "-12-31' "
					+ "and LEAVEPURP=4 and STATUS='ENCASHED' and TRNTYPE='D' and empno="
					+ peno
					+ ") "
					+ " select EMPNO,DAYS-(SELECT Isnull(Sum(days), 0) AS usedleave " + 
					"FROM   leavetran " + 
					"WHERE  empno = "+peno+" " + 
					"AND TRNDATE between '"+ReportDAO.Boy(prdt).substring(7, 11)+"-01-01' and '"+ReportDAO.Boy(prdt).substring(7, 11)+"-12-31' " +
					"AND leavecd = 1 " + 
					"AND status = 'SANCTION' " + 
					"AND trntype = 'D' ) as encash_days from leavetran "
					+ " where LEAVECD=1 and TRNDATE between '"
					+ ReportDAO.Boy(prdt).substring(7, 11)
					+ "-01-01' and '"
					+ ReportDAO.Boy(prdt).substring(7, 11)
					+ "-12-31' "
					+ " and LEAVEPURP=4 and STATUS='ENCASHED' and TRNTYPE='D' and empno="
					+ peno + " " + " else " + " (select 0 as encash_days)");*/
			/*
			 * String checkbal=(
			 * "select isnull(SUM(bal),0) as leavebalance from leavebal where EMPNO="
			 * +peno+" and SRNO = "+
			 * "(select MAX(SRNO) from leavebal where EMPNO="
			 * +peno+" and LEAVECD=1 ) and  "+
			 * " BALDT between '"+ReportDAO.BoFinancialy
			 * (prdt)+"' and '"+ReportDAO.EoFinancialy(prdt)+"' and LEAVECD=1");
			 */
			/*System.out.println("checking leavebalnce...." + checkbal);
			ResultSet leavebal = stleave.executeQuery(checkbal);
			if (leavebal.next()) {
				leavebalnce = leavebal.getInt("encash_days") > 90 ? leavebal
						.getInt("encash_days") - 90 : leavebal
						.getInt("encash_days");

				if (leavebalnce == 0) {*/
					String oldbalcheck = ("SELECT bal+" + old_encash_day
							+ " AS encash_days " + "FROM   leavebal "
							+ " WHERE  empno = " + peno + " "
							+ " AND leavecd = 1 "
							+ "and srno = ( SELECT top 1 (srno) "
							+ "FROM   leavebal " + "WHERE  empno = " + peno
							+ " " + " AND leavecd = 1 order by baldt desc,srno desc)");
					rsleave2 = encashst1.executeQuery(oldbalcheck);
					if (rsleave2.next()) {
						leavebalnce = rsleave2.getInt("encash_days") > 90 ? rsleave2
								.getInt("encash_days") - 90 : 0;
					} else {
						leavebalnce = 0;
					}
			}
				/*} else if(leavebalnce>0) {
					leavebalnce = leavebal.getInt("encash_days") > 90 ? leavebal
							.getInt("encash_days") - 90 : leavebal
							.getInt("encash_days");
				}else {
					leavebalnce = 0;
				}

			} else {
				leavebalnce = 0;
			}*/
			//System.out.println("Encash leavebalnce......" + leavebalnce);
			//System.out.println("system year..." + ReportDAO.getServerDate());
			//System.out.println("prdt year..."+ (Integer.parseInt(prdt.substring(7, 11))));
			String format = "dd-MM-yyyy";
			String curdt = ReportDAO.getServerDate();
			String month1[] = curdt.split("-");
			//System.out.println("month...." + month1[1]);
			int monthno = ReportDAO.getMonth(ReportDAO.getServerDate());
			//System.out.println("month no...." + monthno);
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date dateObj3 = sdf.parse(month1[0] + "-" + String.valueOf(monthno)
					+ "-" + month1[2]);
			Date dateObj4 = sdf.parse("31-03-" + prdt.substring(7, 11));
			String bsicdavda_year = "";
			if (dateObj3.getTime() > dateObj4.getTime()) {
				bsicdavda_year = String.valueOf((Integer.parseInt(prdt
						.substring(7, 11))));
			} else {
				bsicdavda_year = String.valueOf((Integer.parseInt(prdt
						.substring(7, 11)) - 1));
			}
			// this is for taking basic da vda of mar of acadmic start
			// year...@ni//this is logic is also working but above makes it
			// simple
			/*
			 * String bsicdavda_year =
			 * String.valueOf((Integer.parseInt(ReportDAO
			 * .getServerDate().substring(7, 11))) > (Integer
			 * .parseInt(prdt.substring(7, 11))) ? (Integer
			 * .parseInt(ReportDAO.getServerDate().substring(7, 11)) - 1) :
			 * (Integer.parseInt(prdt.substring(7, 11))));
			 */
			
			//System.out.println("this is the encash date....." + "31-MAR-"+ bsicdavda_year);
			
			String leave_encsh = ("if exists (select * from paytran where TRNCD in(101,102,138) and EMPNO="
					+ peno
					+ " and trndt = '"
					+ ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"
							+ bsicdavda_year))
					+ "' ) "
					+ "(select round(((SUM(INP_AMT)/30)*"
					+ leavebalnce
					+ "),0) as encash from paytran where TRNCD in(101,102,138) and EMPNO="
					+ peno
					+ " and trndt = '"
					+ ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"
							+ bsicdavda_year))
					+ "') "
					+ "else (select round(((SUM(INP_AMT)/30)*"
					+ leavebalnce
					+ "),0) as encash from PAYTRAN_STAGE where TRNCD in(101,102,138) and trndt = '"
					+ ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"
							+ bsicdavda_year)) + "' and EMPNO=" + peno + ")");

			// System.out.println("this is date@#@#@#@#@#@@#@#@.."+prdt+"..."+ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"+prdt.substring(7,11))));
			//System.out.println("query leaveencash 3..." + leave_encsh);
			Connection con = ConnectionManager.getConnection();

			ResultSet rsleave = stleave.executeQuery(leave_encsh);
			if (rsleave.next()) {
				leaveencashamt = rsleave.getInt("encash");
				/*
				 * System.out.println("this march date encashment..."+leaveencashamt
				 * );
				 * 
				 * //take 28-FEB_YEAR's BASIC ,DA, VDA if 31-MAR-YEAR's
				 * BASIC,DA,VDA is 0... in case if emp is absent all month
				 * if(leaveencashamt==0) { System.out.println(
				 * "may be no salalry of mar.. so checking for ffeb..."
				 * +leaveencashamt); String leave_encsh1=(
				 * "if exists (select * from paytran where TRNCD in(101,102,138) and EMPNO="
				 * +
				 * peno+" and trndt = ( select CONVERT(varchar,dateadd(d,-(day('"
				 * +
				 * ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"+bsicdavda_year
				 * ))+"')),'"+ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"+
				 * bsicdavda_year))+"'),106) ) "+
				 * "(select round(((SUM(Net_amt)/30)*"+leavebalnce+
				 * "),0) as encash from paytran where TRNCD in(101,102,138) and EMPNO="
				 * +
				 * peno+" and trndt = ( select CONVERT(varchar,dateadd(d,-(day('"
				 * +
				 * ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"+bsicdavda_year
				 * ))+"')),'"+ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"+
				 * bsicdavda_year))+"'),106) )) "+
				 * "else (select round(((SUM(Net_amt)/30)*"+leavebalnce+
				 * "),0) as encash from PAYTRAN_STAGE where TRNCD in(101,102,138) and trndt = ( select CONVERT(varchar,dateadd(d,-(day('"
				 * +
				 * ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"+bsicdavda_year
				 * ))+"')),'"+ReportDAO.EOM(ReportDAO.EoFinancialy("01-Feb-"+
				 * bsicdavda_year))+"'),106) ) and EMPNO="+peno+")");
				 * System.out.println(
				 * "this taking basic+da+vda from feb-28...may be bcz march is empty sal.."
				 * +leave_encsh1);
				 * rsleave1=stleavefeb.executeQuery(leave_encsh1);
				 * if(rsleave1.next()) { leaveencashamt=
				 * rsleave1.getInt("encash"); } else { leaveencashamt= 0; } }
				 */
			}

			String checkleave = ("if exists(select * from YTDTRAN where TRNCD=344 "
					+ "and TRNDT between '"
					+ ReportDAO.BoFinancialy(prdt)
					+ "' and '"
					+ ReportDAO.EoFinancialy(prdt)
					+ "' and EMPNO="
					+ peno
					+ ") "
					+ "update ytdtran set inp_amt="
					+ leaveencashamt
					+ ",cal_amt="
					+ leaveencashamt
					+ ",net_amt="
					+ leaveencashamt
					+ ",upddt=(select CONVERT(date,GETDATE())) "
					+ "where EMPNO="
					+ peno
					+ " and TRNCD=344 "
					+ "else insert into YTDTRAN values((select MAX(trndt) from YTDTRAN where EMPNO="
					+ peno
					+ "), "
					+ " "
					+ peno
					+ ",344,0,"
					+ leaveencashamt
					+ "," + leaveencashamt + ",0,0," + leaveencashamt + ",'','',(select CONVERT(date,GETDATE())),'F' )");
			Statement stleave1 = con.createStatement();
			
			//System.out.println("this in update or insert into ytdtran for 344...."+ checkleave);
			
			stleave1.executeUpdate(checkleave);

			/*
			 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
			 * float[]{10,30,20,20,20},new Rectangle(100,100)); subcell = new
			 * PdfPCell(new Phrase("145",F10Normal));
			 * subcell.setBorderWidthLeft(0);
			 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("			LEAVE SALARY ",F10Normal));
			 * subcell.setBorderWidthRight(0); subcell.setColspan(3);
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase(UtilityDAO.trans(leaveencashamt, "999,99,999.99", "",
			 * false, false),F10Normal));
			 * 
			 * subcell.setBorderWidthRight(0);
			 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tab1.addCell(subcell);
			 */
			totinc += leaveencashamt;
			taxRepBean.getDoc().add(tab1);
			
			//System.out.println("total leaveencash..." + leaveencashamt);

			//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$....LEAVEENCASH"+ leaveencashamt + "......");

			Statement yearearn = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			String leaveentry = ("IF EXISTS(SELECT * FROM YEARLYEARNING WHERE EMPNO="
					+ peno
					//+ " AND TRNCD=344 AND TRNDT between '"
					+ " AND TRNCD=145 AND TRNDT between '"
					+ ReportDAO.BoFinancialy(prdt)
					+ "' and '"
					+ ReportDAO.EoFinancialy(prdt)
					+ "' ) "
					+ "UPDATE YEARLYEARNING SET RELEASE_AMT='"
					+ leaveencashamt
					+ "',PROJ_AMT=0,TOTAL_AMT="
					+ leaveencashamt
					+ " WHERE EMPNO="
					+ peno
					//+ " AND TRNCD=344 AND TRNDT between '"
					+ " AND TRNCD=145 AND TRNDT between '"
					+ ReportDAO.BoFinancialy(prdt)
					+ "' and '"
					+ ReportDAO.EoFinancialy(prdt)
					+ "' "
					+ "ELSE	INSERT INTO YEARLYEARNING VALUES("
					+ peno
					//+ ",344,'"
					+ ",145,'"
					+ leaveencashamt
					+ "',0,"
					+ leaveencashamt
					+ ",'" + ReportDAO.EoFinancialy(prdt) + "' )");
			//System.out.println(leaveentry);
			yearearn.executeUpdate(leaveentry);

			Statement st1 = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			buildsqlsumofquery(peno, prdt, "A2%", taxRepBean); // this is for
																// bonus aND
																// ARREARS....ADDING
																// INCOME TO
																// TOTAL INCOME
			// yearlrearningprintgrp(peno, prdt,repBean,taxRepBean);
			ResultSet rs = st1.executeQuery(taxRepBean.getYtdstr());

			while (rs.next()) {
				/*
				 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
				 * float[]{10,30,20,20,20},new Rectangle(100,100)); subcell =
				 * new PdfPCell(new Phrase(rs.getString("TRNCD"),F10Normal));
				 * subcell.setBorderWidthLeft(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * tab1.addCell(subcell); subcell = new PdfPCell(new
				 * Phrase("    		"+rs.getString("DISC"),F10Normal));
				 * subcell.setBorderWidthRight(0); subcell.setColspan(3);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell); subcell = new PdfPCell(new
				 * Phrase(UtilityDAO.trans(rs.getInt("NET_AMT") ,
				 * "999,99,999.99", "", false, false),F10Normal));
				 * //System.out.println(" asd"+totinc+" fesf"+ totded);
				 * subcell.setBorderWidthRight(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * tab1.addCell(subcell); tot499 =rs.getInt("NET_AMT");
				 * //System.out.println("tot this is bonus..."+tot499);
				 * //System.out.println("tot this is before bonus..."+totinc);
				 * totinc+=tot499;
				 * //System.out.println("tot this is after bonus..."+totinc);
				 * taxRepBean.getDoc().add(tab1);
				 */
				
				//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$...."+ rs.getString("DISC") + " ...." + rs.getInt("NET_AMT")+ "......");

				Statement yearearn1 = repBean.getCn().createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);

				String bonusarrentry = ("IF EXISTS(SELECT * FROM YEARLYEARNING WHERE EMPNO="
						+ peno
						+ " AND TRNCD="
						+ rs.getInt("TRNCD")
						+ " AND TRNDT between '"
						+ ReportDAO.BoFinancialy(prdt)
						+ "' and '"
						+ ReportDAO.EoFinancialy(prdt)
						+ "' ) "
						+ "UPDATE YEARLYEARNING SET RELEASE_AMT='"
						+ rs.getInt("NET_AMT")
						+ "',PROJ_AMT=0,TOTAL_AMT="
						+ rs.getInt("NET_AMT")
						+ " WHERE EMPNO="
						+ peno
						+ " AND TRNCD="
						+ rs.getInt("TRNCD")
						+ " AND TRNDT between '"
						+ ReportDAO.BoFinancialy(prdt)
						+ "' and '"
						+ ReportDAO.EoFinancialy(prdt)
						+ "' "
						+ "ELSE	INSERT INTO YEARLYEARNING VALUES("
						+ peno
						+ ","
						+ rs.getInt("TRNCD")
						+ ",'"
						+ rs.getInt("NET_AMT")
						+ "',0,"
						+ rs.getInt("NET_AMT")
						+ ",'"
						+ ReportDAO.EoFinancialy(prdt) + "' )");
			//	System.out.println(bonusarrentry);
				yearearn1.executeUpdate(bonusarrentry);
				tot499 = rs.getInt("NET_AMT");
				totinc += tot499;
				if (!rs.next()) {
					break;
				} else {
					rs.previous();
					continue;
				}
			}

			tab1 = new PdfPTable(5);
			tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
					new Rectangle(100, 100));
			/*
			 * subcell = new PdfPCell(new Phrase("",F10Normal));
			 * subcell.setBorderWidthLeft(0);
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("       TOTAL  :-       ",F10Normal));
			 * subcell.setBorderWidthRight(0); subcell.setColspan(3);
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * tab1.addCell(subcell);
			 * 
			 * subcell = new PdfPCell(new Phrase(UtilityDAO.trans( totinc ,
			 * "999,99,999.99", "", false, false),F10Normal));
			 * 
			 * subcell.setBorderWidthRight(0);
			 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * tab1.addCell(subcell);
			 * 
			 * 
			 * taxRepBean.getDoc().add(tab1);
			 * 
			 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
			 * float[]{10,30,20,20,20},new Rectangle(100,100)); subcell = new
			 * PdfPCell(new Phrase("",F10Normal));
			 * subcell.setBorderWidthLeft(0);
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * tab1.addCell(subcell); subcell = new PdfPCell(new
			 * Phrase("        Less : Deductions U/S 16 :",F10Normal));
			 * subcell.setBorderWidthRight(0); subcell.setColspan(4);
			 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			 * tab1.addCell(subcell); taxRepBean.getDoc().add(tab1);
			 */
			taxRepBean.setC4tot(0);
			taxRepBean.setC5tot(0);
			taxRepBean.setC3tot(0);
			taxRepBean.setTotamt(0);
			buildsql(peno, prdt, "A5%", taxRepBean);
			
			// why to check slAB FOR deduction..@ni
			// taxRepBean.setC5tot((int)Calculate.checkSlab(500 , prdt,
			// taxRepBean.getC5tot(), 1, peno,repBean.getCn()));
			//System.out.println("0022====" + taxRepBean.getC5tot());
			//System.out.println("total ded..." + taxRepBean.getTotamt());
			//if (taxRepBean.getC5tot() > 0) {
			//	taxRepBean.setC5tot(0);
				/*
				 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
				 * float[]{10,30,20,20,20},new Rectangle(100,100)); subcell =
				 * new PdfPCell(new Phrase("500",F10Normal));
				 * subcell.setBorderWidthLeft(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * tab1.addCell(subcell); subcell = new PdfPCell(new
				 * Phrase("STANDARD DEDUCTION",F10Normal));
				 * subcell.setBorderWidthRight(0); subcell.setColspan(3);
				 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * tab1.addCell(subcell);
				 * 
				 * subcell = new PdfPCell(new
				 * Phrase(UtilityDAO.trans(taxRepBean.getC5(), "999,99,999.99",
				 * "", false, false),F10Normal));
				 * subcell.setBorderWidthRight(0);
				 * 
				 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * tab1.addCell(subcell); taxRepBean.getDoc().add(tab1);
				 * taxRepBean.setC4tot(0); taxRepBean.setC3tot(0);
				 * taxRepBean.setC5tot(0); taxRepBean.setTotamt(0);
				 */
				yearlrearningprintgrp(peno, prdt, repBean, taxRepBean);
				totded += taxRepBean.getC5tot();
				//System.out.println("std ddedd...." + totded);
			//}
			taxRepBean.setC4tot(0);
			taxRepBean.setC3tot(0);
			taxRepBean.setC5tot(0);
			taxRepBean.setTotamt(0);

			// commented bcz... conveyance is not to be consider in
			// deduction....instead have 40000 std deduction
			/*
			 * buildsql(peno, prdt, "A1U%",taxRepBean);
			 * yearlrearningprintgrp(peno,prdt,repBean,taxRepBean);
			 * System.out.println("std ddedd111...."+taxRepBean.getTotamt());
			 */

			// here is the std deduction taken
		//	taxRepBean.setC5(totinc > 40000 ? 40000 : totinc);
			taxRepBean.setC5(totinc > 40000 ? 50000 : totinc);

			//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$....STD DEDUCTION ....500......");

			Statement a3std = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			String a3std_ded = ("IF EXISTS(SELECT * FROM YEARLYEARNING WHERE EMPNO="
					+ peno
					+ " AND TRNCD=500 AND TRNDT between '"
					+ ReportDAO.BoFinancialy(prdt)
					+ "' and '"
					+ ReportDAO.EoFinancialy(prdt)
					+ "' ) "
					+ "UPDATE YEARLYEARNING SET RELEASE_AMT='"
					+ taxRepBean.getC5()
					+ "',PROJ_AMT=0,TOTAL_AMT="
					+ taxRepBean.getC5()
					+ " WHERE EMPNO="
					+ peno
					+ " AND TRNCD=500 AND TRNDT between '"
					+ ReportDAO.BoFinancialy(prdt)
					+ "' and '"
					+ ReportDAO.EoFinancialy(prdt)
					+ "' "
					+ "ELSE	INSERT INTO YEARLYEARNING VALUES("
					+ peno
					+ ",500,'"
					+ taxRepBean.getC5()
					+ "',0,"
					+ taxRepBean.getC5() + ",'" + ReportDAO.EoFinancialy(prdt) + "' )");
			//System.out.println(a3std_ded);
			a3std.executeUpdate(a3std_ded);

			//totded += taxRepBean.getTotamt() + taxRepBean.getC5();
			totded += taxRepBean.getTotamt();
			
			//System.out.println("this is total after STD DEDUCTION AND PT=="+totded);
			
			/*
			 * buildsql(peno, prdt, "A3U%",taxRepBean);
			 * yearlrearningprintgrp(peno,prdt,repBean,taxRepBean);
			 */
			// ' Total Income From all
			try {
				taxRepBean.setC4tot(0);
				taxRepBean.setC3tot(0);
				taxRepBean.setC5tot(0);
				taxRepBean.setTotamt(0);
				int amt522 = 0;
				int amt523 = 0;
				int amt524 = 0;
				int amt525 = 0;
				int amt565 = 0;
				int amt588 = 0;
				String A3U = "SELECT * FROM CDMAST WHERE ITGRP LIKE 'A3U%' order by trncd";
				Statement stA3 = repBean.getCn().createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ResultSet rsA3111 = stA3.executeQuery(A3U);
				if (rsA3111.next()) {
					rsA3111.previous();
					while (rsA3111.next()) {
						/*
						 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
						 * float[]{10,30,20,20,20},new Rectangle(100,100));
						 * subcell = new PdfPCell(new Phrase("",F10Normal));
						 * subcell.setBorderWidthLeft(0);
						 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						 * tab1.addCell(subcell); subcell = new PdfPCell(new
						 * Phrase("		"+rsA3111.getString("DISC"),F10Normal));
						 * subcell.setBorderWidthRight(0);
						 * subcell.setColspan(3);
						 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						 * tab1.addCell(subcell);
						 */
						Statement stA32 = repBean.getCn().createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);

						buildsqlsumofquery(peno, prdt,
								rsA3111.getString("ITGRP"), taxRepBean);
						ResultSet rs11A3 = stA32.executeQuery(taxRepBean
								.getYtdstr());

						if (rs11A3.next()) {
							rs11A3.previous();

							while (rs11A3.next()) {

								/*
								 * subcell = new PdfPCell(new
								 * Phrase(UtilityDAO.trans
								 * (rs11A3.getInt("NET_AMT") , "999,99,999.99",
								 * "", false, false),F10Normal));
								 * //System.out.println(" asd"+totinc+" fesf"+
								 * totded); subcell.setBorderWidthRight(0);
								 * subcell
								 * .setHorizontalAlignment(Element.ALIGN_CENTER
								 * ); tab1.addCell(subcell);
								 */
								if (rsA3111.getInt("TRNCD") == 522)// if
																	// (522)child
																	// edu allow
																	// >2400
																	// then
																	// deduct
																	// 2400
																	// only..so
																	// added..@ni
								{
									amt522 += rs11A3.getInt("NET_AMT");
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ amt522 > 2400 ? 2400 : amt522);
								} else if (rsA3111.getInt("TRNCD") == 523)// if
																			// (523)child
																			// hostel
																			// alllow
																			// >7200
																			// then
																			// deduct
																			// 7200
																			// only..so
																			// added..@ni
								{
									amt523 += rs11A3.getInt("NET_AMT");
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ amt523 > 7200 ? 7200 : amt523);
								} else if (rsA3111.getInt("TRNCD") == 525)// if
																			// (525)LTA
																			// >19200
																			// then
																			// deduct
																			// 19200
																			// only..so
																			// added..@ni
								{
									amt525 += rs11A3.getInt("NET_AMT");
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ amt525 > 19200 ? 19200 : amt525);
								} else if (rsA3111.getInt("TRNCD") == 524)// curently
																			// no
																			// limit
																			// inserted...MEDICAL
																			// BILLS
																			// REIMBURSEMENT
								{
									amt524 += rs11A3.getInt("NET_AMT");
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ amt524);
								} else if (rsA3111.getInt("TRNCD") == 565)// curently
																			// no
																			// limit
																			// inserted...RENT
																			// PAID
								{
									amt565 += rs11A3.getInt("NET_AMT");
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ amt565);
								} else if (rsA3111.getInt("TRNCD") == 588)// curently
																			// no
																			// limit
																			// inserted....SODEXO
																			// COUPONS
																			// REIMBURSEMENT
								{
									amt588 += rs11A3.getInt("NET_AMT");
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ amt588);
								} else {
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ rs11A3.getInt("NET_AMT"));
								}

								//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$...."+ rsA3111.getString("DISC")+ " ...."+ taxRepBean.getC5tot()+ "......");

								Statement a3u = repBean
										.getCn()
										.createStatement(
												ResultSet.TYPE_SCROLL_INSENSITIVE,
												ResultSet.CONCUR_READ_ONLY);

								String a3extrnal = ("IF EXISTS(SELECT * FROM YEARLYEARNING WHERE EMPNO="
										+ peno
										+ " AND TRNCD="
										+ rsA3111.getInt("TRNCD")
										+ " AND TRNDT between '"
										+ ReportDAO.BoFinancialy(prdt)
										+ "' and '"
										+ ReportDAO.EoFinancialy(prdt)
										+ "' ) "
										+ "UPDATE YEARLYEARNING SET RELEASE_AMT='"
										+ taxRepBean.getC5tot()
										+ "',PROJ_AMT=0,TOTAL_AMT="
										+ taxRepBean.getC5tot()
										+ " WHERE EMPNO="
										+ peno
										+ " AND TRNCD="
										+ rsA3111.getInt("TRNCD")
										+ " AND TRNDT between '"
										+ ReportDAO.BoFinancialy(prdt)
										+ "' and '"
										+ ReportDAO.EoFinancialy(prdt)
										+ "' "
										+ "ELSE	INSERT INTO YEARLYEARNING VALUES("
										+ peno
										+ ","
										+ rsA3111.getInt("TRNCD")
										+ ",'"
										+ taxRepBean.getC5tot()
										+ "',0,"
										+ taxRepBean.getC5tot()
										+ ",'"
										+ ReportDAO.EoFinancialy(prdt) + "' )");
								//System.out.println(a3extrnal);
								a3u.executeUpdate(a3extrnal);
								// System.out.println("tot this is bonus..."+tot499);
								// System.out.println("tot this is before bonus..."+totinc);
								totded += taxRepBean.getC5tot();
								taxRepBean.setC5tot(0);
								// System.out.println("tot this is after bonus..."+totinc);
								taxRepBean.getDoc().add(tab1);

								if (!rs11A3.next()) {

									break;
								} else {

									rs11A3.previous();
									continue;
								}
							}
						} else {
							/*
							 * subcell = new PdfPCell(new
							 * Phrase(UtilityDAO.trans(0 , "999,99,999.99", "",
							 * false, false),F10Normal));
							 * //System.out.println(" asd"+totinc+" fesf"+
							 * totded); subcell.setBorderWidthRight(0);
							 * subcell.setHorizontalAlignment
							 * (Element.ALIGN_CENTER); tab1.addCell(subcell);
							 * taxRepBean.getDoc().add(tab1);
							 */
						}
					}
				}

				Statement st = repBean.getCn().createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				int cnt = st.executeUpdate("UPDATE F16 SET GROSS_INC = "
						+ totinc + " WHERE EMPNO = " + peno);
				// Total Deduction from alll
				// totded+= taxRepBean.getTotamt();
				System.out.println("003" + taxRepBean.getC5tot());
				tab1 = new PdfPTable(5);
				tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
						new Rectangle(100, 100));
				/*
				 * subcell = new PdfPCell(new Phrase("",F10Normal));
				 * subcell.setBorderWidthLeft(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell); subcell = new PdfPCell(new
				 * Phrase("       Net Salary Income  :-       ",F10Normal));
				 * subcell.setBorderWidthRight(0); subcell.setColspan(3);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell);
				 * 
				 * subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
				 * totinc-totded , "999,99,999.99", "", false,
				 * false),F10Normal)); System.out.println(" asd"+totinc+" fesf"+
				 * totded); subcell.setBorderWidthRight(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * tab1.addCell(subcell);
				 * 
				 * 
				 * taxRepBean.getDoc().add(tab1);
				 */
				// carry frwd new total after deducting ded from total income
				// salary
				totinc = (totinc - totded);
				//System.out.println("afetr dedectuion......std" + totinc);
				taxRepBean.setC4tot(0);
				taxRepBean.setC5tot(0);
				taxRepBean.setC3tot(0);
				taxRepBean.setC5tot(0);
				taxRepBean.setTotamt(0);
				// buildsql( peno, prdt, "A2A%",taxRepBean);

				// carry forwrd new total by adding bonus value... A2 itgrp type
				// is given for bonus trncd...by @ni
				// System.out.println("tot this is bonus..."+tot499);

				// '------------------------------------------------
				totded = taxRepBean.getC5tot();
				tab1 = new PdfPTable(5);
				tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
						new Rectangle(100, 100));
				/*
				 * subcell = new PdfPCell(new Phrase("",F10Normal));
				 * subcell.setBorderWidthLeft(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell); subcell = new PdfPCell(new
				 * Phrase(" ( II )ANY OTHER INCOME REPORTED BY THE EMPLOYEE :"
				 * ,F10Normal)); subcell.setBorderWidthRight(0);
				 * subcell.setColspan(4);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell);
				 * 
				 * taxRepBean.getDoc().add(tab1);
				 */

				taxRepBean.setC4tot(0);
				taxRepBean.setC5tot(0);
				taxRepBean.setC3tot(0);
				buildsql(peno, prdt, "AA%", taxRepBean);
				yearlrearningprintgrp(peno, prdt, repBean, taxRepBean);
				OTHER_DED = taxRepBean.getC5tot();

				//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$....house intrrest...."+ OTHER_DED + "......");

				Statement houseintextrnala3u = repBean.getCn().createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);

				String houseintextrnal = ("IF EXISTS(SELECT * FROM YEARLYEARNING WHERE EMPNO="
						+ peno
						+ " AND TRNCD=570 AND TRNDT between '"
						+ ReportDAO.BoFinancialy(prdt)
						+ "' and '"
						+ ReportDAO.EoFinancialy(prdt)
						+ "' ) "
						+ "UPDATE YEARLYEARNING SET RELEASE_AMT='"
						+ OTHER_DED
						+ "',PROJ_AMT=0,TOTAL_AMT='"
						+ OTHER_DED
						+ "' WHERE EMPNO="
						+ peno
						+ " AND TRNCD=570 AND TRNDT between '"
						+ ReportDAO.BoFinancialy(prdt)
						+ "' and '"
						+ ReportDAO.EoFinancialy(prdt)
						+ "' "
						+ "ELSE	INSERT INTO YEARLYEARNING VALUES("
						+ peno
						+ ",570,'"
						+ OTHER_DED
						+ "',0,'"
						+ OTHER_DED
						+ "','"
						+ ReportDAO.EoFinancialy(prdt) + "' )");
				//System.out.println(houseintextrnal);
				houseintextrnala3u.executeUpdate(houseintextrnal);
				// totamt = (totinc - totded + OTHER_DED + tot499); //bcz bonus
				// is alredy added in totinc.... and agin here adding tot499
				// with totalinc .....so commented it....@ni
				totamt = (totinc - (totded + OTHER_DED));
				
				//System.out.println("hrent dedcted....." + totamt+ "..............." + totinc);
				
				totded = taxRepBean.getC5tot();
				tab1 = new PdfPTable(5);
				tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
						new Rectangle(100, 100));
				/*
				 * subcell = new PdfPCell(new Phrase("",F10Normal));
				 * subcell.setBorderWidthLeft(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell); subcell = new PdfPCell(new
				 * Phrase(" ( III ) AGGREGATE GROSS TAXABLE INCOME         :"
				 * ,F10Normal)); subcell.setBorderWidthRight(0);
				 * subcell.setColspan(3);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell); subcell = new PdfPCell(new
				 * Phrase(UtilityDAO.trans(totamt, "999,99,999.99", "", false,
				 * false),F10Normal)); subcell.setBorderWidthRight(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * tab1.addCell(subcell); taxRepBean.getDoc().add(tab1); tab1 =
				 * new PdfPTable(5); tab1.setWidthPercentage(new
				 * float[]{10,30,20,20,20},new Rectangle(100,100)); subcell =
				 * new PdfPCell(new Phrase("",F10Normal));
				 * subcell.setBorderWidthLeft(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell); subcell = new PdfPCell(new
				 * Phrase("            (i.e. (I + II  )            "
				 * ,F10Normal)); subcell.setBorderWidthRight(0);
				 * subcell.setColspan(4);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell); taxRepBean.getDoc().add(tab1); tab1 =
				 * new PdfPTable(5); tab1.setWidthPercentage(new
				 * float[]{10,30,20,20,20},new Rectangle(100,100)); subcell =
				 * new PdfPCell(new Phrase("",F10Normal));
				 * subcell.setBorderWidthLeft(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell); subcell = new PdfPCell(new
				 * Phrase(" ( IV ) DEDUCTIONS UNDER CHAPTER  VI-A  :"
				 * ,F10Normal)); subcell.setBorderWidthRight(0);
				 * subcell.setColspan(4);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell); taxRepBean.getDoc().add(tab1);
				 */
				taxRepBean.setC4tot(0);
				taxRepBean.setC5tot(0);
				taxRepBean.setC3tot(0);
				totded = 0;
				/*
				 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
				 * float[]{10,30,20,20,20},new Rectangle(100,100)); subcell =
				 * new PdfPCell(new Phrase("",F10Normal));
				 * subcell.setBorderWidthLeft(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell); subcell = new PdfPCell(new
				 * Phrase("     a) DEDUCTIONS U/S 80 C     ",F10Normal));
				 * subcell.setBorderWidthRight(0); subcell.setColspan(4);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell); taxRepBean.getDoc().add(tab1);
				 */
				taxRepBean.setC5tot(0);
				/* buildsql(peno, prdt, "AC%",taxRepBean); */// here need to
																// print
																// deduction
																// under 80c...
																// so cmnt it
																// and new line
																// added with
																// itgrp AJ----
																// these trncd
																// are undet
																// 80c....@ni
				/*
				 * buildsql(peno, prdt, "80C%",taxRepBean);//worked it...but
				 * need to display evrycode even having 0 amount..@ani
				 * yearlrearningprintgrp(peno, prdt,repBean,taxRepBean);
				 */

				String US80c = "SELECT * FROM CDMAST WHERE ITGRP LIKE '80C%' order by trncd";
				Statement st3 = repBean.getCn().createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ResultSet rs111 = st3.executeQuery(US80c);
				if (rs111.next()) {

					rs111.previous();

					while (rs111.next()) {
						/*
						 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
						 * float[]{10,30,20,20,20},new Rectangle(100,100));
						 * subcell = new PdfPCell(new Phrase("",F10Normal));
						 * subcell.setBorderWidthLeft(0);
						 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						 * tab1.addCell(subcell); subcell = new PdfPCell(new
						 * Phrase("		"+rs111.getString("DISC"),F10Normal));
						 * subcell.setBorderWidthRight(0);
						 * subcell.setColspan(3);
						 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						 * tab1.addCell(subcell);
						 */

						Statement st2 = repBean.getCn().createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);
						Statement st22 = repBean.getCn().createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);

						buildsqlsumofquery(peno, prdt,
								rs111.getString("ITGRP"), taxRepBean);
						ResultSet rs11 = st2.executeQuery(taxRepBean
								.getYtdstr());

						if (rs11.next()) {
							rs11.previous();

							while (rs11.next()) {

								int cdamt = 0;
								// int cdamt1=rs11.getInt("NET_AMT");
								if (rs111.getInt("TRNCD") == 201
										|| rs111.getInt("TRNCD") == 205
										|| rs111.getInt("TRNCD") == 207) {

									ResultSet licgiproj = st22
											.executeQuery("select net_amt from paytran_stage "
													+ "where trndt =(select max(trndt) from paytran_stage where empno="
													+ peno
													+ " "
													+ "and trncd ="
													+ rs111.getInt("TRNCD")
													+ ") and empno ="
													+ peno
													+ " "
													+ "and trncd="
													+ rs111.getInt("TRNCD")
													+ " ");
									if (licgiproj.next()) {
										cdamt = licgiproj.getInt("net_amt");
									}
									int month = taxRepBean.getProjMonth();
									if (rs111.getInt("TRNCD") == 201) {
										//add leaveencashamt insted of 0 if they want to calculate pf on leaveencashamt
										cdamt = ((cdamt * month) + ((0 * 12) / 100));
									
										//System.out.println("this is PF on leave salary..added..."+ ((0 * 12) / 100));
										
									} else {
										cdamt = (cdamt * month);
									}
									
									//System.out.println("!@#$%^@@@@@@@@this is projected for LIC OR GI....."+ cdamt);
									
									// subcell = new PdfPCell(new
									// Phrase(UtilityDAO.trans(rs11.getInt("NET_AMT")+cdamt
									// , "999,99,999.99", "", false,
									// false),F10Normal));
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ rs11.getInt("NET_AMT") + cdamt);
								} else {
									// subcell = new PdfPCell(new
									// Phrase(UtilityDAO.trans(rs11.getInt("NET_AMT")
									// , "999,99,999.99", "", false,
									// false),F10Normal));
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ rs11.getInt("NET_AMT"));
								}// System.out.println(" asd"+totinc+" fesf"+
									// totded);
									// subcell.setBorderWidthRight(0);
									// subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
									// tab1.addCell(subcell);

								//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$....80C DEDUCTION.....trncd..."+ rs111.getInt("TRNCD")+ " ....."+ rs11.getInt("NET_AMT")+ "......");

								Statement ded80csub = repBean
										.getCn()
										.createStatement(
												ResultSet.TYPE_SCROLL_INSENSITIVE,
												ResultSet.CONCUR_READ_ONLY);

								String ded80csubentry = ("IF EXISTS(SELECT * FROM YEARLYEARNING WHERE EMPNO="
										+ peno
										+ " AND TRNCD="
										+ rs111.getInt("TRNCD")
										+ " AND TRNDT between '"
										+ ReportDAO.BoFinancialy(prdt)
										+ "' and '"
										+ ReportDAO.EoFinancialy(prdt)
										+ "' ) "
										+ "UPDATE YEARLYEARNING SET RELEASE_AMT='"
										+ (rs11.getInt("NET_AMT") + cdamt)
										+ "',PROJ_AMT=0,TOTAL_AMT='"
										+ (rs11.getInt("NET_AMT") + cdamt)
										+ "' WHERE EMPNO="
										+ peno
										+ " AND TRNCD="
										+ rs111.getInt("TRNCD")
										+ " AND TRNDT between '"
										+ ReportDAO.BoFinancialy(prdt)
										+ "' and '"
										+ ReportDAO.EoFinancialy(prdt)
										+ "' "
										+ "ELSE	INSERT INTO YEARLYEARNING VALUES("
										+ peno
										+ ","
										+ rs111.getInt("TRNCD")
										+ ",'"
										+ (rs11.getInt("NET_AMT") + cdamt)
										+ "',0,'"
										+ (rs11.getInt("NET_AMT") + cdamt)
										+ "','" + ReportDAO.EoFinancialy(prdt) + "' )");
								//System.out.println(ded80csubentry);
								ded80csub.executeUpdate(ded80csubentry);
								// System.out.println("tot this is bonus..."+tot499);
								// System.out.println("tot this is before bonus..."+totinc);

								// System.out.println("tot this is after bonus..."+totinc);
								taxRepBean.getDoc().add(tab1);

								if (!rs11.next()) {

									break;
								} else {

									rs11.previous();
									continue;
								}
							}
						} else {
							// subcell = new PdfPCell(new
							// Phrase(UtilityDAO.trans(0 , "999,99,999.99", "",
							// false, false),F10Normal));
							// System.out.println(" asd"+totinc+" fesf"+
							// totded);
							// subcell.setBorderWidthRight(0);
							// subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
							// tab1.addCell(subcell);
							// taxRepBean.getDoc().add(tab1);
						}
					}
				}
			
				//System.out.println("this is 80c total...."+ taxRepBean.getC5tot());
			
				st.executeUpdate("UPDATE F16 SET TOT_DED = "
						+ taxRepBean.getC5tot() + " WHERE EMPNO = " + peno);
				/*
				 * if( taxRepBean.getC5tot() > 100000 ) {
				 * taxRepBean.setC5tot(100000); }
				 */// new limit for under 80C..... so Changed....@ni
				if (taxRepBean.getC5tot() > 150000) {
					taxRepBean.setC5tot(150000);
				}
				totded += taxRepBean.getC5tot();// here taken section 80C ded
												// amt in totded...& minus max
												// limit if it exceeds

				/*
				 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
				 * float[]{10,30,20,20,20},new Rectangle(100,100)); subcell =
				 * new PdfPCell(new Phrase("",F10Normal));
				 * subcell.setBorderWidthLeft(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell); subcell = new PdfPCell(new
				 * Phrase("       Qualifying Deduction u/s 80 C ",F10Normal));
				 * subcell.setBorderWidthRight(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell); subcell = new PdfPCell(new
				 * Phrase(UtilityDAO.trans(taxRepBean.getC5tot(),
				 * "999,99,999.99", "", false, false),F10Normal));
				 * subcell.setBorderWidthRight(0); subcell.setColspan(3);
				 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * tab1.addCell(subcell);
				 */

				//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$....80C DEDUCTION"+ taxRepBean.getC5tot() + "......");

				Statement ded80c = repBean.getCn().createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);

				String ded80centry = ("IF EXISTS(SELECT * FROM YEARLYEARNING WHERE EMPNO="
						+ peno
						+ " AND TRNCD=800 AND TRNDT between '"
						+ ReportDAO.BoFinancialy(prdt)
						+ "' and '"
						+ ReportDAO.EoFinancialy(prdt)
						+ "') "
						+ "UPDATE YEARLYEARNING SET RELEASE_AMT='"
						+ taxRepBean.getC5tot()
						+ "',PROJ_AMT=0,TOTAL_AMT="
						+ taxRepBean.getC5tot()
						+ " WHERE EMPNO="
						+ peno
						+ " AND TRNCD=800 AND TRNDT between '"
						+ ReportDAO.BoFinancialy(prdt)
						+ "' and '"
						+ ReportDAO.EoFinancialy(prdt)
						+ "' "
						+ "ELSE	INSERT INTO YEARLYEARNING VALUES("
						+ peno
						+ ",800,'"
						+ taxRepBean.getC5tot()
						+ "',0,"
						+ taxRepBean.getC5tot()
						+ ",'"
						+ ReportDAO.EoFinancialy(prdt) + "' )");
				//System.out.println(ded80centry);
				ded80c.executeUpdate(ded80centry);
				taxRepBean.setC5tot(0);// here again reset to checking next max
										// limitr amt
				/*
				 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
				 * float[]{10,30,20,20,20},new Rectangle(100,100)); subcell =
				 * new PdfPCell(new Phrase("",F10Normal));
				 * subcell.setBorderWidthLeft(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell);
				 * 
				 * subcell = new PdfPCell(new
				 * Phrase("     b) Infrastructure Investment U/s 80CCF"
				 * ,F10Normal)); subcell.setBorderWidthRight(0);
				 * subcell.setColspan(4);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell);
				 * 
				 * taxRepBean.getDoc().add(tab1);
				 */

				/*
				 * buildsql(peno, prdt, "AK%",taxRepBean);//it works proper but
				 * to display 0 amt evenif not there in acdmic year for that emp
				 * yearlrearningprintgrp(peno, prdt,repBean,taxRepBean);
				 */

				String US80CCF = "SELECT * FROM CDMAST WHERE ITGRP LIKE 'AK%' order by trncd";
				Statement st4 = repBean.getCn().createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ResultSet rs1111 = st4.executeQuery(US80CCF);
				if (rs1111.next()) {
					rs1111.previous();
					while (rs1111.next()) {
						/*
						 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
						 * float[]{10,30,20,20,20},new Rectangle(100,100));
						 * subcell = new PdfPCell(new Phrase("",F10Normal));
						 * subcell.setBorderWidthLeft(0);
						 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						 * tab1.addCell(subcell); subcell = new PdfPCell(new
						 * Phrase("		"+rs1111.getString("DISC"),F10Normal));
						 * subcell.setBorderWidthRight(0);
						 * subcell.setColspan(3);
						 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						 * tab1.addCell(subcell);
						 */

						Statement st2 = repBean.getCn().createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);

						buildsqlsumofquery(peno, prdt,
								rs1111.getString("ITGRP"), taxRepBean);
						ResultSet rs11 = st2.executeQuery(taxRepBean
								.getYtdstr());

						if (rs11.next()) {
							rs11.previous();

							while (rs11.next()) {

								/*
								 * subcell = new PdfPCell(new
								 * Phrase(UtilityDAO.trans
								 * (rs11.getInt("NET_AMT") , "999,99,999.99",
								 * "", false, false),F10Normal));
								 * //System.out.println(" asd"+totinc+" fesf"+
								 * totded); subcell.setBorderWidthRight(0);
								 * subcell
								 * .setHorizontalAlignment(Element.ALIGN_CENTER
								 * ); tab1.addCell(subcell);
								 */
								taxRepBean.setC5tot(taxRepBean.getC5tot()
										+ rs11.getInt("NET_AMT"));
								// System.out.println("tot this is bonus..."+tot499);
								// System.out.println("tot this is before bonus..."+totinc);

								// System.out.println("tot this is after bonus..."+totinc);
								// taxRepBean.getDoc().add(tab1);
							
								//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$....INFRASTUCTURE INVESTMENT 80CCF DEDUCTION.....trncd..."+ rs1111.getInt("TRNCD")+ " ....."+ rs11.getInt("NET_AMT")+ "......");

								Statement infra = repBean
										.getCn()
										.createStatement(
												ResultSet.TYPE_SCROLL_INSENSITIVE,
												ResultSet.CONCUR_READ_ONLY);

								String infrasubentry = ("IF EXISTS(SELECT * FROM YEARLYEARNING WHERE EMPNO="
										+ peno
										+ " AND TRNCD="
										+ rs1111.getInt("TRNCD")
										+ " AND TRNDT between '"
										+ ReportDAO.BoFinancialy(prdt)
										+ "' and '"
										+ ReportDAO.EoFinancialy(prdt)
										+ "') "
										+ "UPDATE YEARLYEARNING SET RELEASE_AMT='"
										+ rs11.getInt("NET_AMT")
										+ "',PROJ_AMT=0,TOTAL_AMT="
										+ rs11.getInt("NET_AMT")
										+ " WHERE EMPNO="
										+ peno
										+ " AND TRNCD="
										+ rs1111.getInt("TRNCD")
										+ " AND TRNDT between '"
										+ ReportDAO.BoFinancialy(prdt)
										+ "' and '"
										+ ReportDAO.EoFinancialy(prdt)
										+ "'"
										+ "ELSE	INSERT INTO YEARLYEARNING VALUES("
										+ peno
										+ ","
										+ rs1111.getInt("TRNCD")
										+ ",'"
										+ rs11.getInt("NET_AMT")
										+ "',0,"
										+ rs11.getInt("NET_AMT")
										+ ",'" + ReportDAO.EoFinancialy(prdt) + "' )");
								//System.out.println(infrasubentry);
								infra.executeUpdate(infrasubentry);
								if (!rs11.next()) {

									break;
								} else {

									rs11.previous();
									continue;
								}
							}
						} else {
							/*
							 * subcell = new PdfPCell(new
							 * Phrase(UtilityDAO.trans(0 , "999,99,999.99", "",
							 * false, false),F10Normal));
							 * //System.out.println(" asd"+totinc+" fesf"+
							 * totded); subcell.setBorderWidthRight(0);
							 * subcell.setHorizontalAlignment
							 * (Element.ALIGN_CENTER); tab1.addCell(subcell);
							 * taxRepBean.getDoc().add(tab1);
							 */
						}
					}
				}

				//System.out.println("Infrastructure Investment..."+ taxRepBean.getC5tot());

				if (taxRepBean.getC5tot() > 20000) {
					taxRepBean.setC5tot(20000);
				}
				totded += taxRepBean.getC5tot();// here taken section 80CCF ded
												// amt in totded...& minus max
												// limit if it exceeds

				taxRepBean.setC5tot(0);// here reset for 80D

				/*
				 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
				 * float[]{10,30,20,20,20},new Rectangle(100,100)); subcell =
				 * new PdfPCell(new Phrase("",F10Normal));
				 * subcell.setBorderWidthLeft(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell);
				 * 
				 * subcell = new PdfPCell(new
				 * Phrase("     c) Medical Insurance U/S 80 D     ",F10Normal));
				 * subcell.setBorderWidthRight(0); subcell.setColspan(4);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell);
				 * 
				 * taxRepBean.getDoc().add(tab1);
				 */
				int medcl = 0;
				/*
				 * buildsql(peno, prdt, "AD%",taxRepBean);//same here above
				 * reason yearlrearningprintgrp(peno, prdt,repBean,taxRepBean);
				 */

				String US80D = "SELECT * FROM CDMAST WHERE ITGRP LIKE '80D%' order by trncd";
				Statement st5 = repBean.getCn().createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ResultSet rs11111 = st5.executeQuery(US80D);
				if (rs11111.next()) {
					rs11111.previous();
					int age = 20;
					Statement stt = repBean.getCn().createStatement(
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					ResultSet rss = stt
							.executeQuery("select datediff(year,(select DOB from empmast where empcode='"
									+ empcode
									+ "'),(select convert(date,GETDATE()))) AGE ");
					if (rss.next()) {
						age = rss.getString("AGE") == null ? 20 : rss
								.getInt("AGE");
					}
					while (rs11111.next()) {
						/*
						 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
						 * float[]{10,30,20,20,20},new Rectangle(100,100));
						 * subcell = new PdfPCell(new Phrase("",F10Normal));
						 * subcell.setBorderWidthLeft(0);
						 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						 * tab1.addCell(subcell); subcell = new PdfPCell(new
						 * Phrase("		"+rs11111.getString("DISC"),F10Normal));
						 * subcell.setBorderWidthRight(0);
						 * subcell.setColspan(3);
						 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						 * tab1.addCell(subcell);
						 */

						Statement st2 = repBean.getCn().createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);

						buildsqlsumofquery(peno, prdt,
								rs11111.getString("ITGRP"), taxRepBean);
						ResultSet rs11 = st2.executeQuery(taxRepBean
								.getYtdstr());

						if (rs11.next()) {
							rs11.previous();

							while (rs11.next()) {
								int amt550 = 0;
								int amt552 = 0;
								int amt553 = 0;
								/*
								 * subcell = new PdfPCell(new
								 * Phrase(UtilityDAO.trans
								 * (rs11.getInt("NET_AMT") , "999,99,999.99",
								 * "", false, false),F10Normal));
								 * //System.out.println(" asd"+totinc+" fesf"+
								 * totded); subcell.setBorderWidthRight(0);
								 * subcell
								 * .setHorizontalAlignment(Element.ALIGN_CENTER
								 * ); tab1.addCell(subcell);
								 */
								if (rs11111.getInt("TRNCD") == 550
										|| rs11111.getInt("TRNCD") == 549) {
									// section 80D limit check
									if (age > 65) {
										amt550 = rs11.getInt("NET_AMT") > 30000 ? 30000
												: rs11.getInt("NET_AMT");
										taxRepBean.setC5tot(taxRepBean
												.getC5tot() + amt550);
									} else {
										amt550 = rs11.getInt("NET_AMT") > 25000 ? 25000
												: rs11.getInt("NET_AMT");
										taxRepBean.setC5tot(taxRepBean
												.getC5tot() + amt550);

									}
									medcl += amt550;

								} else if (rs11111.getInt("TRNCD") == 552) {
									// section 80DD limit check
									amt552 = rs11.getInt("NET_AMT") > 75000 ? 75000
											: rs11.getInt("NET_AMT");
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ amt552);
								}

								else if (rs11111.getInt("TRNCD") == 553) {
									// section 80D limit check
									if (age < 65) {
										amt553 = rs11.getInt("NET_AMT") > 40000 ? 40000
												: rs11.getInt("NET_AMT");
										taxRepBean.setC5tot(taxRepBean
												.getC5tot() + amt553);
									} else if (age > 65 && age < 80) {
										amt553 = rs11.getInt("NET_AMT") > 60000 ? 60000
												: rs11.getInt("NET_AMT");
										taxRepBean.setC5tot(taxRepBean
												.getC5tot() + amt553);
									} else {
										amt553 = rs11.getInt("NET_AMT") > 80000 ? 80000
												: rs11.getInt("NET_AMT");
										taxRepBean.setC5tot(taxRepBean
												.getC5tot() + amt553);

									}
								} else {
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ rs11.getInt("NET_AMT"));
								}
								// System.out.println("tot this is bonus..."+tot499);
								// System.out.println("tot this is before bonus..."+totinc);

								//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$....MEDICLAIM ...TRNCD...."+ rs11111.getInt("TRNCD")+ "....."+ taxRepBean.getC5tot()+ "......");

								Statement mediclaim = repBean
										.getCn()
										.createStatement(
												ResultSet.TYPE_SCROLL_INSENSITIVE,
												ResultSet.CONCUR_READ_ONLY);

								String mediclaimentry = ("IF EXISTS(SELECT * FROM YEARLYEARNING WHERE EMPNO="
										+ peno
										+ " AND TRNCD="
										+ rs11111.getInt("TRNCD")
										+ " AND TRNDT between '"
										+ ReportDAO.BoFinancialy(prdt)
										+ "' and '"
										+ ReportDAO.EoFinancialy(prdt)
										+ "') "
										+ "UPDATE YEARLYEARNING SET RELEASE_AMT='"
										+ taxRepBean.getC5tot()
										+ "',PROJ_AMT=0,TOTAL_AMT="
										+ taxRepBean.getC5tot()
										+ " WHERE EMPNO="
										+ peno
										+ " AND TRNCD="
										+ rs11111.getInt("TRNCD")
										+ " AND TRNDT between '"
										+ ReportDAO.BoFinancialy(prdt)
										+ "' and '"
										+ ReportDAO.EoFinancialy(prdt)
										+ "' "
										+ "ELSE	INSERT INTO YEARLYEARNING VALUES("
										+ peno
										+ ","
										+ rs11111.getInt("TRNCD")
										+ ",'"
										+ taxRepBean.getC5tot()
										+ "',0,"
										+ taxRepBean.getC5tot()
										+ ",'"
										+ ReportDAO.EoFinancialy(prdt) + "' )");
								//System.out.println(mediclaimentry);
								mediclaim.executeUpdate(mediclaimentry);

								if (rs11111.getInt("TRNCD") == 550
										|| rs11111.getInt("TRNCD") == 549) {
									taxRepBean.setC5tot(0);
								}
								if (rs11111.getInt("TRNCD") != 550
										|| rs11111.getInt("TRNCD") != 549) {

									totded += taxRepBean.getC5tot();// section
																	// wise
																	// limit
																	// added to
																	// totded....
									taxRepBean.setC5tot(0);
								}// again reset for new section
									// System.out.println("tot this is after bonus..."+totinc);
								taxRepBean.getDoc().add(tab1);

								if (!rs11.next()) {

									break;
								} else {

									rs11.previous();
									continue;
								}
							}
						} else {
							/*
							 * subcell = new PdfPCell(new
							 * Phrase(UtilityDAO.trans(0 , "999,99,999.99", "",
							 * false, false),F10Normal));
							 * //System.out.println(" asd"+totinc+" fesf"+
							 * totded); subcell.setBorderWidthRight(0);
							 * subcell.setHorizontalAlignment
							 * (Element.ALIGN_CENTER); tab1.addCell(subcell);
							 * taxRepBean.getDoc().add(tab1);
							 */
						}
					}
				}

				//System.out.println("mediclaim..." + taxRepBean.getC5tot());
				totded += medcl > 30000 ? 30000 : medcl;// here taken sum of two
														// code..mediclaim for
														// self and
														// parent..(549) and
														// (550) if>30000..then
														// deduct max 30000

				taxRepBean.setC5tot(0);

				String US80e = "SELECT * FROM CDMAST WHERE ITGRP LIKE '80E1' order by trncd";
				Statement st6_80e = repBean.getCn().createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ResultSet rs11111180e = st6_80e.executeQuery(US80e);
				if (rs11111180e.next()) {
					rs11111180e.previous();
					while (rs11111180e.next()) {
						/*
						 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
						 * float[]{10,30,20,20,20},new Rectangle(100,100));
						 * subcell = new PdfPCell(new Phrase("",F10Normal));
						 * subcell.setBorderWidthLeft(0);
						 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						 * tab1.addCell(subcell); subcell = new PdfPCell(new
						 * Phrase
						 * ("		"+rs11111180e.getString("DISC"),F10Normal));
						 * subcell.setBorderWidthRight(0);
						 * subcell.setColspan(3);
						 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						 * tab1.addCell(subcell);
						 */

						Statement st2_80e = repBean.getCn().createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);

						buildsqlsumofquery(peno, prdt,
								rs11111180e.getString("ITGRP"), taxRepBean);
						ResultSet rs1180e = st2_80e.executeQuery(taxRepBean
								.getYtdstr());

						if (rs1180e.next()) {
							rs1180e.previous();

							while (rs1180e.next()) {

								/*
								 * subcell = new PdfPCell(new
								 * Phrase(UtilityDAO.trans
								 * (rs1180e.getInt("NET_AMT") , "999,99,999.99",
								 * "", false, false),F10Normal));
								 * //System.out.println(" asd"+totinc+" fesf"+
								 * totded); subcell.setBorderWidthRight(0);
								 * subcell
								 * .setHorizontalAlignment(Element.ALIGN_CENTER
								 * ); tab1.addCell(subcell);
								 */
								taxRepBean.setC5tot(taxRepBean.getC5tot()
										+ rs1180e.getInt("NET_AMT"));
								// System.out.println("tot this is bonus..."+tot499);
								// System.out.println("tot this is before bonus..."+totinc);

								// System.out.println("tot this is after bonus..."+totinc);

								//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$....section 80e ...TRNCD...."+ rs11111180e.getInt("TRNCD")+ "....."+ taxRepBean.getC5tot()+ "......");

								Statement section_80e = repBean
										.getCn()
										.createStatement(
												ResultSet.TYPE_SCROLL_INSENSITIVE,
												ResultSet.CONCUR_READ_ONLY);

								String section_80eentry = ("IF EXISTS(SELECT * FROM YEARLYEARNING WHERE EMPNO="
										+ peno
										+ " AND TRNCD="
										+ rs11111180e.getInt("TRNCD")
										+ " AND TRNDT between '"
										+ ReportDAO.BoFinancialy(prdt)
										+ "' and '"
										+ ReportDAO.EoFinancialy(prdt)
										+ "') "
										+ "UPDATE YEARLYEARNING SET RELEASE_AMT='"
										+ taxRepBean.getC5tot()
										+ "',PROJ_AMT=0,TOTAL_AMT="
										+ taxRepBean.getC5tot()
										+ " WHERE EMPNO="
										+ peno
										+ " AND TRNCD="
										+ rs11111180e.getInt("TRNCD")
										+ " AND TRNDT between '"
										+ ReportDAO.BoFinancialy(prdt)
										+ "' and '"
										+ ReportDAO.EoFinancialy(prdt)
										+ "'"
										+ "ELSE	INSERT INTO YEARLYEARNING VALUES("
										+ peno
										+ ","
										+ rs11111180e.getInt("TRNCD")
										+ ",'"
										+ taxRepBean.getC5tot()
										+ "',0,"
										+ taxRepBean.getC5tot()
										+ ",'"
										+ ReportDAO.EoFinancialy(prdt) + "' )");
								//System.out.println(section_80eentry);
								section_80e.executeUpdate(section_80eentry);
								taxRepBean.getDoc().add(tab1);
								totded += taxRepBean.getC5tot();
								taxRepBean.setC5tot(0);
								if (!rs1180e.next()) {

									break;
								} else {

									rs1180e.previous();
									continue;
								}
							}
						} else {
							/*
							 * subcell = new PdfPCell(new
							 * Phrase(UtilityDAO.trans(0 , "999,99,999.99", "",
							 * false, false),F10Normal));
							 * //System.out.println(" asd"+totinc+" fesf"+
							 * totded); subcell.setBorderWidthRight(0);
							 * subcell.setHorizontalAlignment
							 * (Element.ALIGN_CENTER); tab1.addCell(subcell);
							 * taxRepBean.getDoc().add(tab1);
							 */
		
							//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$....section 80e ...TRNCD...."+ rs11111180e.getInt("TRNCD")+ "....."+ taxRepBean.getC5tot()+ "......");

							Statement section_80e = repBean.getCn()
									.createStatement(
											ResultSet.TYPE_SCROLL_INSENSITIVE,
											ResultSet.CONCUR_READ_ONLY);

							String section_80eentry = ("IF EXISTS(SELECT * FROM YEARLYEARNING WHERE EMPNO="
									+ peno
									+ " AND TRNCD="
									+ rs11111180e.getInt("TRNCD")
									+ " AND TRNDT between '"
									+ ReportDAO.BoFinancialy(prdt)
									+ "' and '"
									+ ReportDAO.EoFinancialy(prdt)
									+ "' ) "
									+ "UPDATE YEARLYEARNING SET RELEASE_AMT='"
									+ taxRepBean.getC5tot()
									+ "',PROJ_AMT=0,TOTAL_AMT="
									+ taxRepBean.getC5tot()
									+ " WHERE EMPNO="
									+ peno
									+ " AND TRNCD="
									+ rs11111180e.getInt("TRNCD")
									+ " AND TRNDT between '"
									+ ReportDAO.BoFinancialy(prdt)
									+ "' and '"
									+ ReportDAO.EoFinancialy(prdt)
									+ "' "
									+ "ELSE	INSERT INTO YEARLYEARNING VALUES("
									+ peno
									+ ","
									+ rs11111180e.getInt("TRNCD")
									+ ",'"
									+ taxRepBean.getC5tot()
									+ "',0,"
									+ taxRepBean.getC5tot()
									+ ",'"
									+ ReportDAO.EoFinancialy(prdt) + "' )");
							//System.out.println(section_80eentry);
							section_80e.executeUpdate(section_80eentry);
						}
					}
				}

				taxRepBean.setC5tot(0);
				// here reset again.....
				/*
				 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
				 * float[]{10,30,20,20,20},new Rectangle(100,100)); subcell =
				 * new PdfPCell(new Phrase("",F10Normal));
				 * subcell.setBorderWidthLeft(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell);
				 * 
				 * subcell = new PdfPCell(new
				 * Phrase("     d) DONATION U/S 80 G (CM Relief fund)"
				 * ,F10Normal)); subcell.setBorderWidthRight(0);
				 * subcell.setColspan(4);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell); taxRepBean.getDoc().add(tab1);
				 */

				/*
				 * buildsql(peno, prdt, "AE1%",taxRepBean);//same here above
				 * reason yearlrearningprintgrp(peno, prdt,repBean,taxRepBean);
				 */

				String US80G = "SELECT * FROM CDMAST WHERE ITGRP LIKE 'AE1%' order by trncd";
				Statement st6 = repBean.getCn().createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ResultSet rs111111 = st6.executeQuery(US80G);
				if (rs111111.next()) {
					rs111111.previous();
					while (rs111111.next()) {
						/*
						 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
						 * float[]{10,30,20,20,20},new Rectangle(100,100));
						 * subcell = new PdfPCell(new Phrase("",F10Normal));
						 * subcell.setBorderWidthLeft(0);
						 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						 * tab1.addCell(subcell); subcell = new PdfPCell(new
						 * Phrase("		"+rs111111.getString("DISC"),F10Normal));
						 * subcell.setBorderWidthRight(0);
						 * subcell.setColspan(3);
						 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						 * tab1.addCell(subcell);
						 */

						Statement st2 = repBean.getCn().createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);

						buildsqlsumofquery(peno, prdt,
								rs111111.getString("ITGRP"), taxRepBean);
						ResultSet rs11 = st2.executeQuery(taxRepBean
								.getYtdstr());

						if (rs11.next()) {
							rs11.previous();

							while (rs11.next()) {

								/*
								 * subcell = new PdfPCell(new
								 * Phrase(UtilityDAO.trans
								 * (rs11.getInt("NET_AMT") , "999,99,999.99",
								 * "", false, false),F10Normal));
								 * //System.out.println(" asd"+totinc+" fesf"+
								 * totded); subcell.setBorderWidthRight(0);
								 * subcell
								 * .setHorizontalAlignment(Element.ALIGN_CENTER
								 * ); tab1.addCell(subcell);
								 */
								taxRepBean.setC5tot(taxRepBean.getC5tot()
										+ rs11.getInt("NET_AMT"));
								// System.out.println("tot this is bonus..."+tot499);
								// System.out.println("tot this is before bonus..."+totinc);

								// System.out.println("tot this is after bonus..."+totinc);
								taxRepBean.getDoc().add(tab1);
								totded += taxRepBean.getC5tot();
								taxRepBean.setC5tot(0);
								if (!rs11.next()) {

									break;
								} else {

									rs11.previous();
									continue;
								}
							}
						} else {
							/*
							 * subcell = new PdfPCell(new
							 * Phrase(UtilityDAO.trans(0 , "999,99,999.99", "",
							 * false, false),F10Normal));
							 * //System.out.println(" asd"+totinc+" fesf"+
							 * totded); subcell.setBorderWidthRight(0);
							 * subcell.setHorizontalAlignment
							 * (Element.ALIGN_CENTER); tab1.addCell(subcell);
							 * taxRepBean.getDoc().add(tab1);
							 */
						}
					}
				}

				//System.out.println("DONATION U/S 80 G..."+ taxRepBean.getC5tot());
				
				taxRepBean.setC5tot(0);
				/*
				 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
				 * float[]{10,30,20,20,20},new Rectangle(100,100)); subcell =
				 * new PdfPCell(new Phrase("",F10Normal));
				 * subcell.setBorderWidthLeft(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell);
				 * 
				 * subcell = new PdfPCell(new
				 * Phrase("     e) DONATION U/S 80 G (Others)",F10Normal));
				 * subcell.setBorderWidthRight(0); subcell.setColspan(4);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell); taxRepBean.getDoc().add(tab1);
				 */

				/*
				 * buildsql(peno, prdt, "AEA%",taxRepBean);//same here above
				 * reason yearlrearningprintgrp(peno, prdt,repBean,taxRepBean);
				 */

				int amt551 = 0;
				int amt555 = 0;
				int amt556 = 0;
				int amt557 = 0;
				String US80GOthers = "SELECT * FROM CDMAST WHERE ITGRP LIKE '80G%' order by trncd";
				Statement st7 = repBean.getCn().createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ResultSet rs1111111 = st7.executeQuery(US80GOthers);
				if (rs1111111.next()) {
					rs1111111.previous();
					while (rs1111111.next()) {
						/*
						 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
						 * float[]{10,30,20,20,20},new Rectangle(100,100));
						 * subcell = new PdfPCell(new Phrase("",F10Normal));
						 * subcell.setBorderWidthLeft(0);
						 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						 * tab1.addCell(subcell); subcell = new PdfPCell(new
						 * Phrase("		"+rs1111111.getString("DISC"),F10Normal));
						 * subcell.setBorderWidthRight(0);
						 * subcell.setColspan(3);
						 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						 * tab1.addCell(subcell);
						 */

						Statement st2 = repBean.getCn().createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);

						buildsqlsumofquery(peno, prdt,
								rs1111111.getString("ITGRP"), taxRepBean);
						ResultSet rs11 = st2.executeQuery(taxRepBean
								.getYtdstr());

						if (rs11.next()) {
							rs11.previous();

							while (rs11.next()) {

								/*
								 * subcell = new PdfPCell(new
								 * Phrase(UtilityDAO.trans
								 * (rs11.getInt("NET_AMT") , "999,99,999.99",
								 * "", false, false),F10Normal));
								 * //System.out.println(" asd"+totinc+" fesf"+
								 * totded); subcell.setBorderWidthRight(0);
								 * subcell
								 * .setHorizontalAlignment(Element.ALIGN_CENTER
								 * ); tab1.addCell(subcell);
								 */
								if (rs1111111.getInt("TRNCD") == 551) {
									// donation 80G limit (currently no)
									amt551 += rs11.getInt("NET_AMT");
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ amt551);

								} else if (rs1111111.getInt("TRNCD") == 555) {
									// section 80GG limit 60000
									amt555 += rs11.getInt("NET_AMT");
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ amt555 > 60000 ? 60000 : amt555);
								} else if (rs1111111.getInt("TRNCD") == 556) {
									// section 80GGA limit you may insert
									// here(currently no)
									amt556 += rs11.getInt("NET_AMT");
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ amt556);
								} else if (rs1111111.getInt("TRNCD") == 557) {
									// section 80GGC limit you may insert
									// here(currently no)
									amt557 += rs11.getInt("NET_AMT");
									taxRepBean.setC5tot(taxRepBean.getC5tot()
											+ amt557);
								}
								// System.out.println("tot this is bonus..."+tot499);
								// System.out.println("tot this is before bonus..."+totinc);

								//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$....80G ...TRNCD...."+ rs1111111.getInt("TRNCD")+ "....."+ taxRepBean.getC5tot()+ "......");

								Statement ded80g = repBean
										.getCn()
										.createStatement(
												ResultSet.TYPE_SCROLL_INSENSITIVE,
												ResultSet.CONCUR_READ_ONLY);

								String dedg = ("IF EXISTS(SELECT * FROM YEARLYEARNING WHERE EMPNO="
										+ peno
										+ " AND TRNCD="
										+ rs1111111.getInt("TRNCD")
										+ " AND TRNDT between '"
										+ ReportDAO.BoFinancialy(prdt)
										+ "' and '"
										+ ReportDAO.EoFinancialy(prdt)
										+ "') "
										+ "UPDATE YEARLYEARNING SET RELEASE_AMT='"
										+ taxRepBean.getC5tot()
										+ "',PROJ_AMT=0,TOTAL_AMT="
										+ taxRepBean.getC5tot()
										+ " WHERE EMPNO="
										+ peno
										+ " AND TRNCD="
										+ rs1111111.getInt("TRNCD")
										+ " AND TRNDT between '"
										+ ReportDAO.BoFinancialy(prdt)
										+ "' and '"
										+ ReportDAO.EoFinancialy(prdt)
										+ "' "
										+ "ELSE	INSERT INTO YEARLYEARNING VALUES("
										+ peno
										+ ","
										+ rs1111111.getInt("TRNCD")
										+ ",'"
										+ taxRepBean.getC5tot()
										+ "',0,"
										+ taxRepBean.getC5tot()
										+ ",'"
										+ ReportDAO.EoFinancialy(prdt) + "' )");
								//System.out.println(dedg);
								ded80g.executeUpdate(dedg);
								totded += taxRepBean.getC5tot();// section wise
																// added.....to
																// totded
								taxRepBean.setC5tot(0);
								// System.out.println("tot this is after bonus..."+totinc);
								taxRepBean.getDoc().add(tab1);

								if (!rs11.next()) {

									break;
								} else {

									rs11.previous();
									continue;
								}
							}
						} else {
							/*
							 * subcell = new PdfPCell(new
							 * Phrase(UtilityDAO.trans(0 , "999,99,999.99", "",
							 * false, false),F10Normal));
							 * //System.out.println(" asd"+totinc+" fesf"+
							 * totded); subcell.setBorderWidthRight(0);
							 * subcell.setHorizontalAlignment
							 * (Element.ALIGN_CENTER); tab1.addCell(subcell);
							 * taxRepBean.getDoc().add(tab1);
							 */
						}
					}
				}

				//System.out.println("DONATION U/S 80 G (Others)..."+ taxRepBean.getC5tot());
				
				taxRepBean.setC5tot(0);// here reset again......
				/*
				 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
				 * float[]{10,30,20,20,20},new Rectangle(100,100)); subcell =
				 * new PdfPCell(new Phrase("",F10Normal));
				 * subcell.setBorderWidthLeft(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell);
				 * 
				 * subcell = new PdfPCell(new
				 * Phrase("     f) Income of Handicapped U/S 80 U",F10Normal));
				 * subcell.setBorderWidthRight(0); subcell.setColspan(4);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell); taxRepBean.getDoc().add(tab1);
				 */

				/*
				 * buildsql(peno, prdt, "AF1%",taxRepBean);//same here above
				 * reason yearlrearningprintgrp(peno, prdt,repBean,taxRepBean);
				 */

				String HandicappedUS80U = "SELECT * FROM CDMAST WHERE ITGRP LIKE '80U%' order by trncd";
				Statement st8 = repBean.getCn().createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ResultSet rs11111111 = st8.executeQuery(HandicappedUS80U);
				if (rs11111111.next()) {
					rs11111111.previous();
					while (rs11111111.next()) {
						/*
						 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
						 * float[]{10,30,20,20,20},new Rectangle(100,100));
						 * subcell = new PdfPCell(new Phrase("",F10Normal));
						 * subcell.setBorderWidthLeft(0);
						 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						 * tab1.addCell(subcell); subcell = new PdfPCell(new
						 * Phrase("		"+rs11111111.getString("DISC"),F10Normal));
						 * subcell.setBorderWidthRight(0);
						 * subcell.setColspan(3);
						 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						 * tab1.addCell(subcell);
						 */

						Statement st2 = repBean.getCn().createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);

						buildsqlsumofquery(peno, prdt,
								rs11111111.getString("ITGRP"), taxRepBean);
						ResultSet rs11 = st2.executeQuery(taxRepBean
								.getYtdstr());

						if (rs11.next()) {
							rs11.previous();

							while (rs11.next()) {

								/*
								 * subcell = new PdfPCell(new
								 * Phrase(UtilityDAO.trans
								 * (rs11.getInt("NET_AMT") , "999,99,999.99",
								 * "", false, false),F10Normal));
								 * //System.out.println(" asd"+totinc+" fesf"+
								 * totded); subcell.setBorderWidthRight(0);
								 * subcell
								 * .setHorizontalAlignment(Element.ALIGN_CENTER
								 * ); tab1.addCell(subcell);
								 */
								taxRepBean.setC5tot(taxRepBean.getC5tot()
										+ rs11.getInt("NET_AMT"));
								// System.out.println("tot this is bonus..."+tot499);
								// System.out.println("tot this is before bonus..."+totinc);
								totded += taxRepBean.getC5tot();// section wise
																// added.....to
																// totded
								taxRepBean.setC5tot(0);
								// System.out.println("tot this is after bonus..."+totinc);
								// taxRepBean.getDoc().add(tab1);
								
								//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$....80U ...TRNCD...."+ rs11111111.getInt("TRNCD")+ "....."+ rs11.getInt("NET_AMT")+ "......");

								Statement u80 = repBean
										.getCn()
										.createStatement(
												ResultSet.TYPE_SCROLL_INSENSITIVE,
												ResultSet.CONCUR_READ_ONLY);

								String u80dedg = ("IF EXISTS(SELECT * FROM YEARLYEARNING WHERE EMPNO="
										+ peno
										+ " AND TRNCD="
										+ rs11111111.getInt("TRNCD")
										+ " AND TRNDT between '"
										+ ReportDAO.BoFinancialy(prdt)
										+ "' and '"
										+ ReportDAO.EoFinancialy(prdt)
										+ "' ) "
										+ "UPDATE YEARLYEARNING SET RELEASE_AMT='"
										+ rs11.getInt("NET_AMT")
										+ "',PROJ_AMT=0,TOTAL_AMT="
										+ rs11.getInt("NET_AMT")
										+ " WHERE EMPNO="
										+ peno
										+ " AND TRNCD="
										+ rs11111111.getInt("TRNCD")
										+ " AND TRNDT between '"
										+ ReportDAO.BoFinancialy(prdt)
										+ "' and '"
										+ ReportDAO.EoFinancialy(prdt)
										+ "' "
										+ "ELSE	INSERT INTO YEARLYEARNING VALUES("
										+ peno
										+ ","
										+ rs11111111.getInt("TRNCD")
										+ ",'"
										+ rs11.getInt("NET_AMT")
										+ "',0,"
										+ rs11.getInt("NET_AMT")
										+ ",'"
										+ ReportDAO.EoFinancialy(prdt) + "' )");
								//System.out.println(u80dedg);
								u80.executeUpdate(u80dedg);

								if (!rs11.next()) {

									break;
								} else {

									rs11.previous();
									continue;
								}
							}
						} else {
							/*
							 * subcell = new PdfPCell(new
							 * Phrase(UtilityDAO.trans(0 , "999,99,999.99", "",
							 * false, false),F10Normal));
							 * //System.out.println(" asd"+totinc+" fesf"+
							 * totded); subcell.setBorderWidthRight(0);
							 * subcell.setHorizontalAlignment
							 * (Element.ALIGN_CENTER); tab1.addCell(subcell);
							 * taxRepBean.getDoc().add(tab1);
							 */
						}
					}
				}
				
				//System.out.println("ttotoototot.........." + totinc + "....."+ totamt);
				//System.out.println("ddddddddddddddddddddddd..."+ taxRepBean.getC5tot());
				
				taxRepBean.setC5tot(0);
				/*
				 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
				 * float[]{10,30,20,20,20},new Rectangle(100,100)); subcell =
				 * new PdfPCell(new Phrase("",F10Normal));
				 * subcell.setBorderWidthLeft(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell); subcell = new PdfPCell(new
				 * Phrase("   Qualifying Deduction under chapter VI-A "
				 * ,F10Normal)); subcell.setBorderWidthRight(0);
				 * subcell.setColspan(3);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell); subcell = new PdfPCell(new
				 * Phrase(UtilityDAO.trans(totded, "999,99,999.99", "", false,
				 * false),F10Normal));
				 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * subcell.setBorderWidthRight(0); tab1.addCell(subcell);
				 * taxRepBean.getDoc().add(tab1);
				 */

				// DED80 = taxRepBean.getC5tot();//new totded by section wise
				DED80 = totded;
				
				//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$....totdeduction ...TRNCD....888....."+ totded + "......");

				Statement totdedamt = repBean.getCn().createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);

				String totdedg = ("IF EXISTS(SELECT * FROM YEARLYEARNING WHERE EMPNO="
						+ peno
						+ " AND TRNCD=888 AND TRNDT between '"
						+ ReportDAO.BoFinancialy(prdt)
						+ "' and '"
						+ ReportDAO.EoFinancialy(prdt)
						+ "' ) "
						+ "UPDATE YEARLYEARNING SET RELEASE_AMT='"
						+ totded
						+ "',PROJ_AMT=0,TOTAL_AMT="
						+ totded
						+ " WHERE EMPNO="
						+ peno
						+ " AND TRNCD=888 AND TRNDT between '"
						+ ReportDAO.BoFinancialy(prdt)
						+ "' and '"
						+ ReportDAO.EoFinancialy(prdt)
						+ "'"
						+ "ELSE	INSERT INTO YEARLYEARNING VALUES("
						+ peno
						+ ",888,'"
						+ totded
						+ "',0,"
						+ totded
						+ ",'"
						+ ReportDAO.EoFinancialy(prdt) + "' )");
				//System.out.println(totdedg);
				totdedamt.executeUpdate(totdedg);
				/*
				 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
				 * float[]{10,30,20,20,20},new Rectangle(100,100)); subcell =
				 * new PdfPCell(new Phrase("",F10Normal));
				 * subcell.setBorderWidthLeft(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell);
				 * 
				 * subcell = new PdfPCell(new Phrase(
				 * " ( V ) NET TAXABLE INCOME <a>before W.O.(i.e. III - IV ) :- "
				 * ,F10Normal)); subcell.setBorderWidthRight(0);
				 * subcell.setColspan(3);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell);
				 */

				totamt = totamt - DED80;

				/*
				 * subcell = new PdfPCell(new Phrase(UtilityDAO.trans(totamt,
				 * "999,99,999.99", "", false, false),F10Normal));
				 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * subcell.setBorderWidthRight(0); tab1.addCell(subcell);
				 * taxRepBean.getDoc().add(tab1);
				 */

				/*
				 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
				 * float[]{10,30,20,20,20},new Rectangle(100,100)); subcell =
				 * new PdfPCell(new Phrase("",F10Normal));
				 * subcell.setBorderWidthLeft(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell);
				 * 
				 * subcell = new PdfPCell(new Phrase(
				 * "  (Rounded off to nearest Rs.10) <b>after W.O.           :- "
				 * ,F10Normal)); subcell.setBorderWidthRight(0);
				 * subcell.setColspan(3);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell); subcell = new PdfPCell(new
				 * Phrase(UtilityDAO.trans(Math.round(totamt), "999,99,999.99",
				 * "", false, false),F10Normal));
				 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * subcell.setBorderWidthRight(0); tab1.addCell(subcell);
				 * taxRepBean.getDoc().add(tab1);
				 */

				int totchkamt = 0;
				gross_tax_amt = totamt;
				if (sex.equalsIgnoreCase("F")) // if total income is > 350000
												// then tax is minimum of 2500
												// or tax on income otherwise it
												// would be 5 % if income is
												// <500000....@ni
				{
					// totamt = Math.round(Chk_Slab1(575, prdt, totamt,
					// 2,repBean)); for female /male by emp_category we can diff
					// in slab
					totchkamt = Math.round(Chk_Slab1(599, prdt, totamt, 0,
							repBean)); // so 1st checked income is greather or
										// not ... according it takes slab...
					if (totamt <= totchkamt) {
						totamt = Math.round(Chk_Slab1(575, prdt, totamt, 1,
								repBean));
					} else {
						totamt = Math.round(Chk_Slab1(575, prdt, totamt, 0,
								repBean));
					}
				} else {
					totchkamt = Math.round(Chk_Slab1(599, prdt, totamt, 0,
							repBean));
					if (totamt <= totchkamt) {
						totamt = Math.round(Chk_Slab1(575, prdt, totamt, 1,
								repBean));
					} else {
						totamt = Math.round(Chk_Slab1(575, prdt, totamt, 0,
								repBean));
					}
				}

				// for rebate 2500... dedcted if tax>2500 otherwise 0 tax..
				if (gross_tax_amt <= totchkamt) {
					// totamt=totamt>2500?totamt-2500:0;
				}
				/*
				 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
				 * float[]{10,30,20,20,20},new Rectangle(100,100)); subcell =
				 * new PdfPCell(new Phrase("",F10Normal));
				 * subcell.setBorderWidthLeft(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell);
				 * 
				 * subcell = new PdfPCell(new Phrase(
				 * "  ( VI ) INCOME TAX PAYABLE ON [ (V)(b) ] ABOVE          :- "
				 * ,F10Normal)); subcell.setBorderWidthRight(0);
				 * subcell.setColspan(3);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell); subcell = new PdfPCell(new
				 * Phrase(UtilityDAO.trans(totamt, "999,99,999.99", "", false,
				 * false),F10Normal));
				 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * subcell.setBorderWidthRight(0); tab1.addCell(subcell);
				 * taxRepBean.getDoc().add(tab1);
				 */

				taxRepBean.setTax_cal(totamt);
				
				//System.out.println("this tax....." + taxRepBean.getTax_cal()+ "................." + totamt);

				/*
				 * totamt = Math.round(Calculate.checkSlab(498, prdt,
				 * gross_tax_amt, 1, peno,repBean.getCn())); if( totamt == 100 )
				 * { totamt = taxRepBean.getTax_cal(); } if( totamt == 2240) {
				 * totamt = taxRepBean.getTax_cal() - (gross_tax_amt - 100000);
				 * }
				 */

				taxRepBean.setC4tot(0);
				taxRepBean.setC5tot(0);
				taxRepBean.setC3tot(0);
				/*
				 * buildsql(peno, prdt, "AJ%",taxRepBean);
				 * yearlrearningprintgrp(peno, prdt,repBean,taxRepBean);
				 */// not display here ... needs to deduct from total income...
					// so included under 80c.... itgrp chng AC1,AC2 ect..then
					// will be there in 80C.....@ni

				// taxRepBean.setNet_tax(taxRepBean.getTax_cal() - reb88);
				taxRepBean.setNet_tax(taxRepBean.getTax_cal());
				if (taxRepBean.getNet_tax() < 0) {
					taxRepBean.setNet_tax(0);
				}
				/*
				 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
				 * float[]{10,30,20,20,20},new Rectangle(100,100)); subcell =
				 * new PdfPCell(new Phrase("",F10Normal));
				 * subcell.setBorderWidthLeft(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell);
				 * 
				 * subcell = new PdfPCell(new
				 * Phrase("  ( X ) NET TAX PAYABLE(i.e. VI - IX  )  "
				 * ,F10Normal)); subcell.setBorderWidthRight(0);
				 * subcell.setColspan(3);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell); subcell = new PdfPCell(new
				 * Phrase(UtilityDAO.trans(taxRepBean.getNet_tax(),
				 * "999,99,999.99", "", false, false),F10Normal));
				 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * subcell.setBorderWidthRight(0); tab1.addCell(subcell);
				 * taxRepBean.getDoc().add(tab1);
				 */
				st.executeUpdate("UPDATE F16 SET TOT_TAX = "
						+ taxRepBean.getNet_tax() + " WHERE EMPNO = " + peno);
				// '---------------------------------------------------------
				taxRepBean.setSercharge(0);
				if (taxRepBean.getNet_tax() > 0) {
					if (gross_tax_amt <= totchkamt) {
						taxRepBean
								.setNet_tax(taxRepBean.getNet_tax() > 12500 ? taxRepBean
										.getNet_tax() - 12500 : 0);
					}
					// education cess is 3% on total tax payble...so cmnt old
					// line & add new line..to calc edu cess by 3%
					// taxRepBean.setSercharge(Math.round(Calculate.checkSlab(901,
					// prdt, taxRepBean.getNet_tax(), 1,
					// peno,repBean.getCn())));
					taxRepBean.setSercharge(((int) Math.round(taxRepBean
							.getNet_tax() * 0.02)));
					taxRepBean
							.setSercharge(taxRepBean.getSercharge()
									+ ((int) Math.round(taxRepBean.getNet_tax() * 0.01)));
					taxRepBean
							.setSercharge(taxRepBean.getSercharge()
									+ ((int) Math.round(taxRepBean.getNet_tax() * 0.01)));
				}
				/*
				 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
				 * float[]{10,30,20,20,20},new Rectangle(100,100)); subcell =
				 * new PdfPCell(new Phrase("",F10Normal));
				 * subcell.setBorderWidthLeft(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell); subcell = new PdfPCell(new
				 * Phrase("          Add Education Cess@3%  ",F10Normal));
				 * subcell.setBorderWidthRight(0); subcell.setColspan(3);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell); subcell = new PdfPCell(new
				 * Phrase(UtilityDAO.trans(taxRepBean.getSercharge(),
				 * "999,99,999.99", "", false, false),F10Normal));
				 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * subcell.setBorderWidthRight(0); tab1.addCell(subcell);
				 * taxRepBean.getDoc().add(tab1);
				 */

				st.executeUpdate("UPDATE F16 SET ECESS = "
						+ taxRepBean.getSercharge() + " WHERE EMPNO = " + peno);

				if (taxRepBean.getSercharge() > 0) {
					/*
					 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
					 * float[]{10,30,20,20,20},new Rectangle(100,100)); subcell
					 * = new PdfPCell(new Phrase("", new Font(Font.TIMES_ROMAN,
					 * 10, Font.NORMAL))); subcell.setBorderWidthLeft(0);
					 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 * tab1.addCell(subcell);
					 * 
					 * subcell = new PdfPCell(new
					 * Phrase("              Total    ", new
					 * Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
					 * subcell.setBorderWidthRight(0); subcell.setColspan(3);
					 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 * tab1.addCell(subcell); subcell = new PdfPCell(new
					 * Phrase(UtilityDAO.trans(taxRepBean.getNet_tax()+
					 * taxRepBean.getSercharge(), "999,99,999.99", "", false,
					 * false), new Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
					 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 * subcell.setBorderWidthRight(0); tab1.addCell(subcell);
					 * taxRepBean.getDoc().add(tab1);
					 */
					// new total tax with education cess and deducting rebate
					// amount here
					taxRepBean.setNet_tax((taxRepBean.getNet_tax() + taxRepBean
							.getSercharge()));

					// rebate

				}

				tax = taxRepBean.getNet_tax();
				
				//System.out.println("ayyyyyy this is total tax......"+ taxRepBean.getNet_tax() + "..............." + totamt);
				
				/*
				 * if(taxRepBean.getNet_tax()>0){ tab1 = new PdfPTable(5);
				 * tab1.setWidthPercentage(new float[]{10,30,20,20,20},new
				 * Rectangle(100,100)); subcell = new PdfPCell(new Phrase("",
				 * new Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
				 * subcell.setBorderWidthLeft(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell);
				 * 
				 * subcell = new PdfPCell(new Phrase(" Section 87A Rebate", new
				 * Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
				 * subcell.setBorderWidthRight(0); subcell.setColspan(3);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell);
				 * taxRepBean.setNet_tax(taxRepBean.getNet_tax
				 * ()<=2500?0:taxRepBean.getNet_tax()-2500); subcell = new
				 * PdfPCell(new Phrase(UtilityDAO.trans(2500, "999,99,999.99",
				 * "", false, false), new Font(Font.TIMES_ROMAN, 10,
				 * Font.NORMAL)));
				 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * subcell.setBorderWidthRight(0); tab1.addCell(subcell);
				 * taxRepBean.getDoc().add(tab1); }
				 */
			
				//System.out.println("taxRepBean.setNet_tax()........."+ taxRepBean.getNet_tax());
				
				taxRepBean.setC4tot(0);
				taxRepBean.setC5tot(0);
				taxRepBean.setC3tot(0);
				/*
				 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
				 * float[]{10,30,20,20,20},new Rectangle(100,100)); subcell =
				 * new PdfPCell(new Phrase("", new Font(Font.TIMES_ROMAN, 10,
				 * Font.NORMAL))); subcell.setBorderWidthLeft(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell); subcell = new PdfPCell(new
				 * Phrase("  ( XI ) TDS FROM SALARY / TAX PAID ", new
				 * Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
				 * subcell.setBorderWidthRight(0); subcell.setColspan(3);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell);
				 */
				int tds = 0;
				buildsqlsumofquery(peno, prdt, "TDS", taxRepBean); // this is
																	// for tds
																	// calculation
																	// in
																	// acadmic
																	// year...228
																	// sum
				// yearlrearningprintgrp(peno, prdt,repBean,taxRepBean);
				ResultSet rs1 = st.executeQuery(taxRepBean.getYtdstr());
				if (rs1.next()) {

					rs1.previous();
					while (rs1.next()) {
						tds += rs1.getInt("NET_AMT");
					}

					/*
					 * subcell = new PdfPCell(new Phrase(UtilityDAO.trans(tds ,
					 * "999,99,999.99", "", false, false),F10Normal));
					 * //System.out.println(" asd"+totinc+" fesf"+ totded);
					 * subcell.setBorderWidthRight(0);
					 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 * tab1.addCell(subcell);
					 */

					taxRepBean.setTAXPAID(tds);
					// System.out.println("this is tax paid now........"+taxRepBean.getTAXPAID());
					taxRepBean.setNet_tax(taxRepBean.getNet_tax()
							- taxRepBean.getTAXPAID());
					// System.out.println("this is final tax........"+taxRepBean.getNet_tax());

					// taxRepBean.getDoc().add(tab1);
				} else {
					/*
					 * subcell = new PdfPCell(new Phrase(UtilityDAO.trans(tds ,
					 * "999,99,999.99", "", false, false), new
					 * Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
					 * subcell.setBorderWidthRight(0);
					 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					 * tab1.addCell(subcell);
					 */

					taxRepBean.setTAXPAID(tds);
					// System.out.println("this is tax paid now........"+taxRepBean.getTAXPAID());
					taxRepBean.setNet_tax(taxRepBean.getNet_tax()
							- taxRepBean.getTAXPAID());
					// System.out.println("this is final tax........"+taxRepBean.getNet_tax());

					// taxRepBean.getDoc().add(tab1);
				}

				taxRepBean.getDoc().add(tab1);

				buildsql(peno, prdt, "BB%", taxRepBean);
				yearlrearningprintgrp(peno, prdt, repBean, taxRepBean);

				taxRepBean.setTAXPAID(taxRepBean.getC5tot());

				st.executeUpdate("UPDATE F16 SET tds = "
						+ taxRepBean.getTAXPAID() + " WHERE EMPNO = " + peno);

				/*
				 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
				 * float[]{10,30,20,20,20},new Rectangle(100,100)); subcell =
				 * new PdfPCell(new Phrase("", new Font(Font.TIMES_ROMAN, 10,
				 * Font.NORMAL))); subcell.setBorderWidthLeft(0);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell); subcell = new PdfPCell(new
				 * Phrase(" ( XII ) BALANCE TAX PAYABLE /REFUNDABLE ( X - XI ) :"
				 * , new Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
				 * subcell.setBorderWidthRight(0); subcell.setColspan(3);
				 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * tab1.addCell(subcell);
				 * taxRepBean.setTAXPAID(taxRepBean.getNet_tax() -
				 * taxRepBean.getTAXPAID() + taxRepBean.getTAXPAID()); subcell =
				 * new PdfPCell(new
				 * Phrase(UtilityDAO.trans(taxRepBean.getNet_tax
				 * (),"999,99,999.99", "", false, false), new
				 * Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
				 * subcell.setColspan(3);
				 * subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				 * subcell.setBorderWidthRight(0); tab1.addCell(subcell);
				 * taxRepBean.getDoc().add(tab1);
				 */

				st.executeUpdate("UPDATE F16 SET bal_tax = "
						+ taxRepBean.getNet_tax() + " WHERE EMPNO = " + peno);

				/*
				 * para = new Paragraph(new Phrase("* * *  N O T E S * * *",new
				 * Font(Font.TIMES_ROMAN,10)));
				 * para.setAlignment(Element.ALIGN_CENTER);
				 * para.setSpacingBefore(5); taxRepBean.getDoc().add(para); para
				 * = new Paragraph(new Phrase(
				 * "1) Please send details of investment made or projected along with Xerox copies of the same for the period"
				 * ,new Font(Font.TIMES_ROMAN,10)));
				 * para.setAlignment(Element.ALIGN_LEFT);
				 * taxRepBean.getDoc().add(para);
				 */

				//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$......TDS...."+ tax + "......");

				System.out.println("tax :"+tax+"tds :"+tds);
				
				Statement tdsyearearn1 = repBean.getCn().createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);

				String bonusarrentry = ("IF EXISTS(SELECT * FROM YEARLYEARNING WHERE EMPNO="
						+ peno
						+ " AND TRNCD=228 AND TRNDT between '"
						+ ReportDAO.BoFinancialy(prdt)
						+ "' and '"
						+ ReportDAO.EoFinancialy(prdt)
						+ "') "
						+ "UPDATE YEARLYEARNING SET RELEASE_AMT="
						+ tax
						+ ",PROJ_AMT="
						+ tds
						+ ",TOTAL_AMT="
						+ (tax - tds)
						+ " WHERE EMPNO="
						+ peno
						+ " AND TRNCD=228 AND TRNDT between '"
						+ ReportDAO.BoFinancialy(prdt)
						+ "' and '"
						+ ReportDAO.EoFinancialy(prdt)
						+ "'"
						+ "ELSE	INSERT INTO YEARLYEARNING VALUES("
						+ peno
						+ ",228,"
						+ tax
						+ ","
						+ tds
						+ ","
						+ (tax - tds)
						+ ",'"
						+ ReportDAO.EoFinancialy(prdt) + "' )");
				System.out.println("bonusarrentry : "+bonusarrentry);
				tdsyearearn1.executeUpdate(bonusarrentry);

				if (taxRepBean.getNet_tax() > 0) {
					/*
					 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
					 * float[]{10,30,20,20,20},new Rectangle(100,100)); subcell
					 * = new PdfPCell(new Phrase("", new Font(Font.TIMES_ROMAN,
					 * 10, Font.NORMAL))); subcell.setBorderWidthLeft(0);
					 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 * tab1.addCell(subcell);
					 * 
					 * subcell = new PdfPCell(new Phrase(
					 * "   Otherwise Tax will be deducted from your salary as under :"
					 * , new Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
					 * subcell.setBorderWidthRight(0); subcell.setColspan(4);
					 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 * tab1.addCell(subcell); taxRepBean.getDoc().add(tab1);
					 * 
					 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
					 * float[]{10,30,20,20,20},new Rectangle(100,100)); subcell
					 * = new PdfPCell(new Phrase("", new Font(Font.TIMES_ROMAN,
					 * 10, Font.NORMAL))); subcell.setBorderWidthLeft(0);
					 * subcell.setColspan(2);
					 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 * tab1.addCell(subcell);
					 * 
					 * subcell = new PdfPCell(new
					 * Phrase("  Total Tax Payable Rs.", new
					 * Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
					 * subcell.setBorderWidthRight(0);
					 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 * tab1.addCell(subcell);
					 * 
					 * subcell = new PdfPCell(new Phrase("No of Months", new
					 * Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
					 * subcell.setBorderWidthRight(0);
					 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 * tab1.addCell(subcell);
					 * 
					 * subcell = new PdfPCell(new
					 * Phrase("Tax to be deducted per Month (Rs.)", new
					 * Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
					 * subcell.setBorderWidthRight(0);
					 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 * tab1.addCell(subcell); taxRepBean.getDoc().add(tab1);
					 * 
					 * tab1 = new PdfPTable(5); tab1.setWidthPercentage(new
					 * float[]{10,30,20,20,20},new Rectangle(100,100)); subcell
					 * = new PdfPCell(new Phrase("", new Font(Font.TIMES_ROMAN,
					 * 10, Font.NORMAL))); subcell.setBorderWidthLeft(0);
					 * subcell.setColspan(2);
					 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 * tab1.addCell(subcell);
					 * 
					 * subcell = new PdfPCell(new
					 * Phrase(UtilityDAO.trans(taxRepBean.getNet_tax(),
					 * "999,99,999.99", "", false, false), new
					 * Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
					 * subcell.setBorderWidthRight(0);
					 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 * tab1.addCell(subcell);
					 * 
					 * subcell = new PdfPCell(new
					 * Phrase(UtilityDAO.trans(taxRepBean.getProjMonth(),
					 * "999,99,999.99", "", false, false), new
					 * Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
					 * subcell.setBorderWidthRight(0);
					 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 * tab1.addCell(subcell);
					 * 
					 * 
					 * if( taxRepBean.getProjMonth() > 0 ) { subcell = new
					 * PdfPCell(new
					 * Phrase(UtilityDAO.trans((taxRepBean.getNet_tax() /
					 * taxRepBean.getProjMonth()), "999,99,999.99", "", false,
					 * false), new Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
					 * subcell.setBorderWidthRight(0);
					 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 * tab1.addCell(subcell);
					 * 
					 * }else{ subcell = new PdfPCell(new Phrase("", new
					 * Font(Font.TIMES_ROMAN, 10, Font.NORMAL)));
					 * subcell.setBorderWidthRight(0);
					 * subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					 * tab1.addCell(subcell); } taxRepBean.getDoc().add(tab1);
					 */
				}

				/*
				 * para = new Paragraph(new Phrase(
				 * "IF YOU HAVE ALREADY SENT DETAILS OF INVESTMENT, PLEASE IGNORE THIS LETTER.        "
				 * ,new Font(Font.TIMES_ROMAN,10)));
				 * para.setAlignment(Element.ALIGN_LEFT);
				 * taxRepBean.getDoc().add(para); para = new Paragraph(new
				 * Phrase(
				 * "2) There may be increase in amount of tax payable due to DA arrears,"
				 * +
				 * "Leave Encashment, Leave surrender,Closing allowance etc.",new
				 * Font(Font.TIMES_ROMAN,10)));
				 * para.setAlignment(Element.ALIGN_LEFT);
				 * taxRepBean.getDoc().add(para); para = new Paragraph(new
				 * Phrase(
				 * "THE FIG. OF HOUSING INT.IS ON ACCRUED BASIS & IT WORKSHEET IS TENTATIVE."
				 * ,new Font(Font.TIMES_ROMAN,10)));
				 * para.setAlignment(Element.ALIGN_LEFT);
				 * taxRepBean.getDoc().add(para); para = new Paragraph(new
				 * Phrase("AGM (PERSONNEL)",new Font(Font.TIMES_ROMAN,10)));
				 * para.setSpacingBefore(5);
				 * para.setAlignment(Element.ALIGN_RIGHT);
				 * taxRepBean.getDoc().add(para);
				 * 
				 * 
				 * 
				 * 
				 * 
				 * taxRepBean.getDoc().newPage();
				 */
				//FinalTaxCalculation(empcode, peno, prdt, pename, sex, repBean, taxRepBean);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {

		}

	}
	
	
	/***********************************************START NEWLY TAX WORKING********************************************************************************************************/
	public static void FinalTaxCalculation(String empcode, int peno, String prdt,
			String pename, String sex, RepoartBean repBean,
			TaxReportBean taxRepBean) {
		
		System.out.println("WELCOME FinalTaxCalculation.................");
		System.out.println("CHECK VALUES : EMPCODE : "+empcode+"  EMPNO : "+peno+"  PRDT : "+prdt+"  PENAME : "+pename+"  SEX : "+sex+"  REPBEAN :"+repBean+"  TAXREPBEAN : "+taxRepBean);
		
	}

	/***********************************************END NEWLY TAX WORKING********************************************************************************************************/
	
	public static void yearlrearningprintgrp(int ppeno, String pprdt,
			RepoartBean repBean, TaxReportBean taxRepBean) {

		String pitgrp = "";
		String pitgrp2 = "";
		ResultSet slb = null;
		ResultSet ytd = null;
		String slbstr = "";
		int reftot = 0;
		int ActlAmt = 0;
		int ProjAmt = 0;
		try {
			Statement st = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ytd = st.executeQuery(taxRepBean.getYtdstr());
			PdfPCell subcell = null;
			PdfPTable tab1 = new PdfPTable(5);
			tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
					new Rectangle(100, 100));

			while (ytd.next()) {
				pitgrp = ytd.getString("ITGRP") != null ? ytd
						.getString("ITGRP") : "";
				pitgrp2 = ytd.getString("ITGRP2") != null ? ytd
						.getString("ITGRP2") : "";
				taxRepBean.setTotamt(0);
				while (ytd.getString("ITGRP").equals(pitgrp)) {
					subcell = new PdfPCell(new Phrase("" + ytd.getInt("TRNCD"),
							F10Normal));
					subcell.setBorderWidthLeft(0);
					subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tab1.addCell(subcell);
					if (ytd.getString("PLUSMINUS").equalsIgnoreCase("M")) {
						subcell = new PdfPCell(new Phrase("Less "
								+ ytd.getString("DISC"), F10Normal));
						subcell.setBorderWidthRight(0);
						subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tab1.addCell(subcell);
					} else {
						subcell = new PdfPCell(new Phrase(
								ytd.getString("DISC"), F10Normal));
						subcell.setBorderWidthRight(0);
						subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tab1.addCell(subcell);
					}
					if (ytd.getInt("TRNCD") == 202) {
						if ((ytd.getString("NET_AMT") != null ? ytd
								.getInt("NET_AMT") : 0)
								+ (ytd.getString("NET_AMT1") != null ? ytd
										.getInt("NET_AMT1") : 0) == 2400) {
							ActlAmt = (ytd.getString("NET_AMT") != null ? ytd
									.getInt("NET_AMT") : 0);
							ProjAmt = (ytd.getString("NET_AMT1") != null ? ytd
									.getInt("NET_AMT1") : 0) + 100;
							subcell = new PdfPCell(new Phrase("" + ActlAmt,
									F10Normal));
							subcell.setBorderWidthRight(0);
							subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
							tab1.addCell(subcell);
							subcell = new PdfPCell(new Phrase("" + ProjAmt,
									F10Normal));
							subcell.setBorderWidthRight(0);
							subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
							tab1.addCell(subcell);
							taxRepBean.setTotamt(taxRepBean.getTotamt()
									+ ActlAmt + ProjAmt);
							if (ytd.getString("PLUSMINUS")
									.equalsIgnoreCase("M")) {
								taxRepBean.setC3tot(taxRepBean.getC3tot()
										- ActlAmt);
								taxRepBean.setC4tot(taxRepBean.getC4tot()
										- ProjAmt);
							} else {
								taxRepBean.setC3tot(taxRepBean.getC3tot()
										+ ActlAmt);
								taxRepBean.setC4tot(taxRepBean.getC4tot()
										+ ProjAmt);
							}

						} else {
							ActlAmt = (ytd.getString("NET_AMT") != null ? ytd
									.getInt("NET_AMT") : 0);
							ProjAmt = (ytd.getString("NET_AMT1") != null ? ((taxRepBean
									.getProjMonth() <= 1) ? ((ytd
									.getInt("NET_AMT1") - 100) < 0 ? 0 : (ytd
									.getInt("NET_AMT1") - 100)) : (taxRepBean
									.getProjMonth() == 2 ? (ytd
									.getInt("NET_AMT1") - 100) : ytd
									.getInt("NET_AMT1"))) : 0);// +
																// (taxRepBean.getProjMonth()<=1?0:100);
							ProjAmt = ((ActlAmt + ProjAmt) == 2400) ? ProjAmt + 100
									: ProjAmt;
							subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
									ytd.getString("NET_AMT") != null ? ActlAmt
											: 0, "99,99,999.99", "", false,
									false), F10Normal));
							subcell.setBorderWidthRight(0);
							subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
							tab1.addCell(subcell);
							subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
									ytd.getString("NET_AMT1") != null ? ProjAmt
											: 0, "99,99,999.99", "", false,
									false), F10Normal));
							subcell.setBorderWidthRight(0);
							subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
							tab1.addCell(subcell);

							taxRepBean
									.setTotamt(taxRepBean.getTotamt()
											+ (ytd.getString("NET_AMT") != null ? ActlAmt
													: 0)
											+ (ytd.getString("NET_AMT1") != null ? ProjAmt
													: 0));
							if (ytd.getString("PLUSMINUS")
									.equalsIgnoreCase("M")) {
								taxRepBean
										.setC3tot(taxRepBean.getC3tot()
												- (ytd.getString("NET_AMT") != null ? ActlAmt
														: 0));
								taxRepBean
										.setC4tot(taxRepBean.getC4tot()
												- (ytd.getString("NET_AMT1") != null ? ProjAmt
														: 0));
							} else {
								taxRepBean
										.setC3tot(taxRepBean.getC3tot()
												+ (ytd.getString("NET_AMT") != null ? ActlAmt
														: 0));
								taxRepBean
										.setC4tot(taxRepBean.getC4tot()
												+ (ytd.getString("NET_AMT1") != null ? ProjAmt
														: 0));
							}
						}
						Statement st1 = repBean.getCn().createStatement();
						st1.executeUpdate("UPDATE F16 SET PT = "
								+ ytd.getInt("NET_AMT") + " WHERE EMPNO = "
								+ ppeno);
						st1.close();
					} else {

						subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
								ytd.getString("NET_AMT") != null ? ytd
										.getInt("NET_AMT") : 0, "99,99,999.99",
								"", false, false), F10Normal));
						subcell.setBorderWidthRight(0);
						subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tab1.addCell(subcell);
						subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
								ytd.getString("NET_AMT1") != null ? ytd
										.getInt("NET_AMT1") : 0,
								"99,99,999.99", "", false, false), F10Normal));
						subcell.setBorderWidthRight(0);
						subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tab1.addCell(subcell);

						if (ytd.getString("PLUSMINUS").equalsIgnoreCase("M")) {
							taxRepBean.setC3tot(taxRepBean.getC3tot()
									- (ytd.getString("NET_AMT") != null ? ytd
											.getInt("NET_AMT") : 0));
							taxRepBean.setC4tot(taxRepBean.getC4tot()
									- (ytd.getString("NET_AMT1") != null ? ytd
											.getInt("NET_AMT1") : 0));
							taxRepBean.setTotamt(taxRepBean.getTotamt()
									- (ytd.getString("NET_AMT") != null ? ytd
											.getInt("NET_AMT") : 0)
									- (ytd.getString("NET_AMT1") != null ? ytd
											.getInt("NET_AMT1") : 0));
						} else {
							if (ytd.getInt("TRNCD") == 535) {// if (535)home
																// loan interest
																// >200000 then
																// deduct 200000
																// only..so
																// added..@ni
								taxRepBean
										.setC3tot(taxRepBean.getC3tot()
												+ (ytd.getString("NET_AMT") != null ? ytd
														.getInt("NET_AMT") > 200000 ? 200000
														: ytd.getInt("NET_AMT")
														: 0));
								taxRepBean
										.setC4tot(taxRepBean.getC4tot()
												+ (ytd.getString("NET_AMT1") != null ? ytd
														.getInt("NET_AMT1") > 200000 ? 200000
														: ytd.getInt("NET_AMT1")
														: 0));
								taxRepBean
										.setTotamt(taxRepBean.getTotamt()
												+ (ytd.getString("NET_AMT") != null ? ytd
														.getInt("NET_AMT") > 200000 ? 200000
														: ytd.getInt("NET_AMT")
														: 0)
												+ (ytd.getString("NET_AMT1") != null ? ytd
														.getInt("NET_AMT1") > 200000 ? 200000
														: ytd.getInt("NET_AMT1")
														: 0));

							} else {
								taxRepBean
										.setC3tot(taxRepBean.getC3tot()
												+ (ytd.getString("NET_AMT") != null ? ytd
														.getInt("NET_AMT") : 0));
								taxRepBean
										.setC4tot(taxRepBean.getC4tot()
												+ (ytd.getString("NET_AMT1") != null ? ytd
														.getInt("NET_AMT1") : 0));
								taxRepBean
										.setTotamt(taxRepBean.getTotamt()
												+ (ytd.getString("NET_AMT") != null ? ytd
														.getInt("NET_AMT") : 0)
												+ (ytd.getString("NET_AMT1") != null ? ytd
														.getInt("NET_AMT1") : 0));
							}

						}
					}

					if (ytd.getString("ITGRP").equalsIgnoreCase(pitgrp)) {
						ReportDAO.println("", taxRepBean.getC5(), 0, false,
								"BANK", repBean);
					}
					if (!ytd.next()) {
						break;
					}

				}

				ytd.previous();
				if (ytd.getInt("TRNCD") == 811) {
					taxRepBean.setTotamt(calhlint(taxRepBean.getTotamt(),
							ppeno, repBean, taxRepBean));
				} else {
					if ((ytd.getString("CHKSLB") == null ? "N" : ytd
							.getString("CHKSLB")).equalsIgnoreCase("Y")) {
						slbstr = "SELECT * FROM SLAB WHERE TRNCD = "
								+ ytd.getInt("TRNCD");
						Statement st1 = repBean.getCn().createStatement();
						slb = st1.executeQuery(slbstr);
						if (slb.next()) {
							reftot = taxRepBean.getTotamt();
							taxRepBean.setTotamt((int) Calculate.checkSlab(
									ytd.getInt("TRNCD"), pprdt, reftot, 1,
									ppeno, repBean.getCn()));
						}
						st1.close();
					}
				}
				taxRepBean.setC5tot(taxRepBean.getC5tot()
						+ taxRepBean.getTotamt());
				subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
						taxRepBean.getTotamt(), "99,99,999.99", "", false,
						false), F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tab1.addCell(subcell);

				if (ytd.getString("ITGRP").contains("A1")
						|| ytd.getString("ITGRP").contains("A1U")) {

					//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$...."+ ytd.getString("NET_AMT") + "......"+ ytd.getString("NET_AMT1"));

					Statement a1yearearn = repBean.getCn().createStatement(
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);

					String checkbal = ("IF EXISTS(SELECT * FROM YEARLYEARNING WHERE EMPNO="
							+ ppeno
							+ " AND TRNCD="
							+ ytd.getInt("TRNCD")
							+ " AND TRNDT between '"
							+ ReportDAO.BoFinancialy(pprdt)
							+ "' and '"
							+ ReportDAO.EoFinancialy(pprdt)
							+ "') "
							+ "UPDATE YEARLYEARNING SET RELEASE_AMT='"
							+ (ytd.getString("NET_AMT")!= null?ytd.getString("NET_AMT"):0)
							+ "',PROJ_AMT='"
							+ (ytd.getString("NET_AMT1")!= null?ytd.getString("NET_AMT1"):0)
							+ "',TOTAL_AMT='"
							+ ((ytd.getString("NET_AMT")!= null?ytd.getFloat("NET_AMT"):0) + (ytd
									.getString("NET_AMT1")!= null?ytd.getFloat("NET_AMT1"):0))
							+ "' WHERE EMPNO="
							+ ppeno
							+ " AND TRNCD="
							+ ytd.getString("TRNCD")
							+ " AND TRNDT between '"
							+ ReportDAO.BoFinancialy(pprdt)
							+ "' and '"
							+ ReportDAO.EoFinancialy(pprdt)
							+ "' "
							+ "ELSE	INSERT INTO YEARLYEARNING VALUES("
							+ ppeno
							+ ","
							+ ytd.getString("TRNCD")
							+ ",'"
							+ (ytd.getString("NET_AMT")!= null?ytd.getString("NET_AMT"):0)
							+ "','"
							+ (ytd.getString("NET_AMT1")!= null?ytd.getString("NET_AMT1"):0)
							+ "','"
							+ ((ytd.getString("NET_AMT")!= null?ytd.getFloat("NET_AMT"):0) + (ytd
									.getString("NET_AMT1")!= null?ytd.getFloat("NET_AMT1"):0))
							+ "','"
							+ ReportDAO.EoFinancialy(pprdt) + "' )");
					//System.out.println(checkbal);
					a1yearearn.executeUpdate(checkbal);
				} else if (ytd.getString("ITGRP").contains("A5")) {
					
					//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$...."+ ytd.getString("NET_AMT") + "......"+ ytd.getString("NET_AMT1"));

					Statement a1yearearn = repBean.getCn().createStatement(
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);

					String checkbal = ("IF EXISTS(SELECT * FROM YEARLYEARNING WHERE EMPNO="
							+ ppeno
							+ " AND TRNCD="
							+ ytd.getInt("TRNCD")
							+ " AND TRNDT between '"
							+ ReportDAO.BoFinancialy(pprdt)
							+ "' and '"
							+ ReportDAO.EoFinancialy(pprdt)
							+ "') "
							+ "UPDATE YEARLYEARNING SET RELEASE_AMT='"
							+ ActlAmt
							+ "',PROJ_AMT='"
							+ ProjAmt
							+ "',TOTAL_AMT='"
							+ (ActlAmt + ProjAmt)
							+ "' WHERE EMPNO="
							+ ppeno
							+ " AND TRNCD="
							+ ytd.getString("TRNCD")
							+ " AND TRNDT between '"
							+ ReportDAO.BoFinancialy(pprdt)
							+ "' and '"
							+ ReportDAO.EoFinancialy(pprdt)
							+ "' "
							+ "ELSE	INSERT INTO YEARLYEARNING VALUES("
							+ ppeno
							+ ","
							+ ytd.getString("TRNCD")
							+ ",'"
							+ ActlAmt
							+ "','"
							+ ProjAmt
							+ "','"
							+ (ActlAmt + ProjAmt)
							+ "','" + ReportDAO.EoFinancialy(pprdt) + "' )");
					//System.out.println(checkbal);
					a1yearearn.executeUpdate(checkbal);
				}
			}
			taxRepBean.getDoc().add(tab1);
			st.close();

			tab1 = new PdfPTable(5);
			tab1.setWidthPercentage(new float[] { 10, 30, 20, 20, 20 },
					new Rectangle(100, 100));
			if (taxRepBean.getGnm().equalsIgnoreCase("A1")) {
				subcell = new PdfPCell(new Phrase("", F10Normal));
				subcell.setBorderWidthLeft(0);
				subcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tab1.addCell(subcell);
				subcell = new PdfPCell(new Phrase("     GROSS SALARY INCOME ",
						F10Normal));
				subcell.setBorderWidthRight(0);
				subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				tab1.addCell(subcell);

			}
			subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
					taxRepBean.getC3tot(), "99,99,999.99", "", false, false),
					F10Normal));
			subcell.setBorderWidthRight(0);
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
					taxRepBean.getC4tot(), "99,99,999.99", "", false, false),
					F10Normal));
			subcell.setBorderWidthRight(0);
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);
			subcell = new PdfPCell(new Phrase(UtilityDAO.trans(
					taxRepBean.getC5tot(), "99,99,999.99", "", false, false),
					F10Normal));
			subcell.setBorderWidthRight(0);
			subcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab1.addCell(subcell);

			taxRepBean.getDoc().add(tab1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String setprojectedmonth(TaxReportBean taxRepBean,
			RepoartBean repBean, String rdt, int empno, String f16orwsheet) {
		String maxtrndt = "";
		String maxdt = "";
		try {
			Statement dtst1 = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			// this is for calculating the projected_number_of_month values...so
			// no_of_month is cal then cal is done....@ni
			ResultSet dtchk = dtst1
					.executeQuery("select max(TRNDT) TRNDT from ytdtran where empno="
							+ empno + " and trncd=101 ");

			if (dtchk.next()) {
				maxtrndt = dtchk.getString("TRNDT");

			}
			String mname = getmonthname(Integer.parseInt(maxtrndt.substring(5,
					7)));
			maxdt = maxtrndt.substring(8, 10) + "-" + mname + "-"
					+ maxtrndt.substring(0, 4);
			//System.out.println("maxdt..." + maxdt);
			// System.out.println("max dat....monnthno..."+maxtrndt.substring(5,
			// 7)+"..its name...."+mname+"...this is date..."+maxdt);
			// System.out.println("YTDTRAN maxtrndtmonthno....date is..."+maxtrndt+"...."+String.valueOf(ReportDAO.getMonth(maxdt)+1));
			String userdate_month = (String
					.valueOf((ReportDAO.getMonth(maxdt) + 1) == 12 ? 12
							: (ReportDAO.getMonth(maxdt) + 1)));
			//System.out.println("USERMONTH...." + userdate_month);
			rdt = maxdt;
			String u_date[] = rdt.split("-");

			String rsteom = ReportDAO.EOM(rdt);
			// System.out.println("EOM(rdt)...."+rsteom);
			// String u_EOM=ReportDAO.EOM(usrdate);
			String lastdate[] = rsteom.split("-");
			userdate_month = Integer.parseInt(userdate_month) > 9 ? userdate_month
					: "0" + userdate_month;
			String usrdate = u_date[2] + "-" + userdate_month + "-"
					+ lastdate[0];
			String cyear[] = maxtrndt.split("-");
			// System.out.println("EOM(USERDATE)...."+usrdate);

			if (!f16orwsheet.equalsIgnoreCase("F16")
					|| f16orwsheet.equalsIgnoreCase("F16")) {
				/*
				 * System.out.println("this is date.."+maxtrndt);
				 * System.out.println
				 * ("udate[2]..."+Integer.parseInt(u_date[2])+"..cyear[0]..."
				 * +Integer.parseInt(cyear[0]));
				 * System.out.println("usermonth..."
				 * +Integer.parseInt(userdate_month));
				 */
				String eoy = ReportDAO.EoFinancialy(rdt);
				if (usrdate.equalsIgnoreCase(maxtrndt)) {

					// System.out.println((" Taxsheet date are equls ..."));
					//System.out.println("here....");

					/*
					 * if(Integer.parseInt(u_date[2])>Integer.parseInt(cyear[2]))
					 */
					if (Integer.parseInt(u_date[2]) >= Integer
							.parseInt(cyear[0])) {

						/*
						 * System.out.println("this is usrdate..."+usrdate);
						 * System.out.println("eoy..."+eoy);
						 * System.out.println(" year greater ...");
						 */

						/*
						 * eoy=eoy.replace("/-/g","/"); Date d1= new Date(eoy);
						 */

						if (eoy.equalsIgnoreCase(rdt)) {
							taxRepBean.setProjMonth(0);
						} else if (Integer.parseInt(userdate_month) > 3) {

							taxRepBean.setProjMonth(((12 - Integer
									.parseInt(cyear[1])) + 3));
						} else if (Integer.parseInt(userdate_month) <= 3) {
							taxRepBean.setProjMonth(((3 - Integer
									.parseInt(cyear[1]))));
						}
					} else if (eoy.equalsIgnoreCase(rdt)) {
						taxRepBean.setProjMonth(0);
					} else if (Integer.parseInt(userdate_month) > 3) {

						taxRepBean.setProjMonth(((12 - Integer
								.parseInt(cyear[1])) + 3));
					} else if (Integer.parseInt(userdate_month) <= 3) {
						taxRepBean.setProjMonth(((3 - Integer
								.parseInt(cyear[1]))));
					}
				} else {
					// System.out.println((" Taxsheet date are  not equls ..."));

					String setdate[] = maxtrndt.split("-");
					String monthname = getmonthname(Integer
							.parseInt(setdate[1]));

					// rdt="01-"+monthname+"-"+setdate[0];
					// taxRepBean.setProjMonth(((12 -
					// Integer.parseInt(userdate_month))+3));
					if (Integer.parseInt(u_date[2]) >= Integer
							.parseInt(cyear[0])) {

						/*
						 * System.out.println("111111111this is usrdate..."+usrdate
						 * ); System.out.println("111111111eoy..."+eoy);
						 * System.out.println(" 111111111year greater ...");
						 */

						/*
						 * eoy=eoy.replace("/-/g","/"); Date d1= new Date(eoy);
						 */

						if (eoy.equalsIgnoreCase(rdt)) {
							taxRepBean.setProjMonth(0);
						} else if (Integer.parseInt(userdate_month) > 3) {

							taxRepBean.setProjMonth(((12 - Integer
									.parseInt(cyear[1])) + 3));
						} else if (Integer.parseInt(userdate_month) <= 3) {
							taxRepBean.setProjMonth(((3 - Integer
									.parseInt(cyear[1]))));
						}
					} else if (eoy.equalsIgnoreCase(rdt)) {
						taxRepBean.setProjMonth(0);
					} else if (Integer.parseInt(userdate_month) > 3) {

						taxRepBean.setProjMonth(((12 - Integer
								.parseInt(cyear[1])) + 3));
					} else if (Integer.parseInt(userdate_month) <= 3) {
						taxRepBean.setProjMonth(((3 - Integer
								.parseInt(cyear[1]))));
					}
				}

			}

			/*
			 * else { //System.out.println(("date are  not equls ...")); //
			 * rdt=maxtrndt;
			 * //System.out.println(("date are  not equls so date become ..."
			 * +rdt));
			 * //taxRepBean.setProjMonth((ReportDAO.getMonth(ReportDAO.BoFinancialy
			 * (rdt))-1) - Math.abs(ReportDAO.getMonth(rdt)));
			 * 
			 * 
			 * if(usrdate.equalsIgnoreCase(maxtrndt)) {
			 * 
			 * //System.out.println((" Taxsheet date are equls ..."));
			 * 
			 * 
			 * if(Integer.parseInt(u_date[2])>Integer.parseInt(cyear[0])) {
			 * //System.out.println(" year greater ...");
			 * taxRepBean.setProjMonth(((12 - Integer.parseInt(cyear[1]))+3)); }
			 * else { //System.out.println(" current year  ...");
			 * taxRepBean.setProjMonth(((12 -
			 * Integer.parseInt(userdate_month))+3)); } } else {
			 * //System.out.println((" Taxsheet date are  not equls ..."));
			 * String setdate[]=maxtrndt.split("-"); String
			 * monthname=getmonthname(Integer.parseInt(setdate[1]));
			 * //rdt="01-"+monthname+"-"+setdate[0];
			 * //taxRepBean.setProjMonth(((12 -
			 * Integer.parseInt(userdate_month))+3));
			 * if((Integer.parseInt(u_date[2])<=Integer.parseInt(cyear[0])) &&
			 * (Integer.parseInt(userdate_month)<=3)) {
			 * //System.out.println(" year greater ...");
			 * //taxRepBean.setProjMonth(((12 - Integer.parseInt(cyear[1]))+3));
			 * taxRepBean.setProjMonth((3-(Integer.parseInt(userdate_month))));
			 * } else { //System.out.println(" current year  ...");
			 * //taxRepBean.setProjMonth(((12 -
			 * Integer.parseInt(userdate_month))+3));
			 * taxRepBean.setProjMonth(((12-
			 * Integer.parseInt(userdate_month)+3))); } } }
			 */
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return maxdt;
	}

	public static ResultSet printgrpF16Result(int ppeno, String pprdt,
			RepoartBean repBean, TaxReportBean taxRepBean) {
		// System.out.println("printgrpF16");
		String pitgrp = "";
		String pitgrp2 = "";
		ResultSet slb = null;
		ResultSet ytd = null;
		String slbstr = "";
		int reftot = 0;
		Statement st;
		try {
			st = repBean.getCn().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			// System.out.println("hrishi "+taxRepBean.getYtdstr());
			ytd = st.executeQuery(taxRepBean.getYtdstr());
			while (ytd.next()) {
				pitgrp = ytd.getString("itgrp") != null ? ytd
						.getString("itgrp") : "";
				taxRepBean.setTotamt(0);
				while (ytd.getString("ITGRP").equalsIgnoreCase(pitgrp)) {
					taxRepBean.setTotamt(taxRepBean.getTotamt()
							+ ytd.getString("NET_AMT") != null ? ytd
							.getInt("NET_AMT") : 0);

					if (ytd.getString("PLUSMINUS").equalsIgnoreCase("M")) {
						taxRepBean.setC3tot(taxRepBean.getC3tot()
								- (ytd.getString("NET_AMT") != null ? ytd
										.getInt("NET_AMT") : 0));
					} else {
						taxRepBean.setC3tot(taxRepBean.getC3tot()
								+ (ytd.getString("NET_AMT") != null ? ytd
										.getInt("NET_AMT") : 0));
					}
					if (!ytd.next()) {
						break;
					}
				}
				ytd.previous();
				if (ytd.getInt("TRNCD") == 811) {
					taxRepBean.setTotamt(calhlint(taxRepBean.getTotamt(),
							ppeno, repBean, taxRepBean));
				} else {

					if ((ytd.getString("CHKSLB") == null ? "N" : ytd
							.getString("CHKSLB")).equalsIgnoreCase("Y")) {
						slbstr = "SELECT * FROM SLAB WHERE TRNCD = "
								+ ytd.getInt("TRNCD");

						Statement st1 = repBean.getCn().createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);
						slb = st1.executeQuery(slbstr);

						if (slb.next()) {
							reftot = taxRepBean.getTotamt();
							if (ytd.getInt("TRNCD") != 803
									&& ytd.getInt("TRNCD") != 209) {
								taxRepBean.setTotamt(Math.round(Calculate
										.checkSlab(ytd.getInt("TRNCD"), pprdt,
												reftot, 1, ppeno,
												repBean.getCn()))); // ***************REMAINING***********

							}
						}
						st1.close();
					}
				}
				if (ytd.getString("PLUSMINUS").equalsIgnoreCase("M")) {
					taxRepBean.setC5tot(taxRepBean.getC5tot()
							- taxRepBean.getTotamt());
				} else {
					taxRepBean.setC5tot(taxRepBean.getC5tot()
							+ taxRepBean.getTotamt());
				}
			}
			// st.close();
			ytd.beforeFirst();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ytd;
	}
	// Code Wise Salary Report...
	public void codeWiseSalaryReort(String frmdate, String todate,int trncd, String imgpath,
			String filepath) {
		
		

		float aprTotal=0.0f;
		float mayTotal=0.0f;
		float juneTotal=0.0f;
		float julyTotal=0.0f;
		float augTotal=0.0f;
		float sepTotal=0.0f;
		float octTotal=0.0f;
		float novTotal=0.0f;
		float decTotal=0.0f;
		float janTotal=0.0f;
		float febTotal=0.0f;
		float marTotal=0.0f;

		
		System.out.println("I am in EmployeeRetiringList " );
		long millis=System.currentTimeMillis();  
		 java.sql.Date date1=new java.sql.Date(millis);  
		 System.out.println("date..."+date1);
		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		 String reportDate = df.format(date1);
		RepoartBean repBean  = new RepoartBean();
		try
		{
			Connection Cn = null;
			Statement st = null;
			ResultSet rs = null;

			ReportDAO.OpenCon("", "", "",repBean);
			Cn = repBean.getCn();
			
			int totalEmployee =0;
			String Query1 = "";
			
			Rectangle rec = new  Rectangle(285,100);
			Document doc = new Document();
			PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filepath));
			
			/*UtilityDAO dao = new UtilityDAO();
			UtilityDAO.lable1="Date : ";
			Footer ftr = dao.new Footer(lable1);
			writer.setPageEvent(ftr);*/
			
			
			doc.open();
			doc.setPageSize(PageSize.A4);
			
			Font fbold = new Font(Font.TIMES_ROMAN,8,Font.BOLD);
			Font fsmall = new Font(Font.TIMES_ROMAN,7);
			Font FONT = new Font(Font.TIMES_ROMAN, 54, Font.NORMAL, new GrayColor(0.75f));
			Font  fontcolor = FontFactory.getFont(FontFactory.HELVETICA, 9, new Color(6, 8, 193));
			
			
			
			ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Ch.Shahu Hospital",FONT), 297.5f, 421, 45);
			Image image1 = Image.getInstance(imgpath);
			Phrase title = new Phrase("Rajeshri Chatrapati Shahu Maharaj hospital,Jalgaon",new Font(FONT.TIMES_ROMAN,8,Font.BOLD));
			Paragraph para = new Paragraph(title);
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingBefore(0);

			image1.scaleAbsolute(60f,50f);
			image1.setAbsolutePosition(40f, 750f);
			doc.add(image1);
			doc.add(para);
			para = new Paragraph(new Phrase("Rajeshri Chatrapati Shahu Maharaj hospital, Shahu Nagar, Jalgaon-425001",new Font(Font.TIMES_ROMAN,8)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);

			doc.add(para);
			para = new Paragraph(new Phrase("Tel : 0257 2223301",new Font(Font.TIMES_ROMAN,8)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);

			doc.add(para);
//			para = new Paragraph(new Phrase("Email : hrd@namcobank.in",new Font(Font.TIMES_ROMAN,8)));
//			para.setAlignment(Element.ALIGN_CENTER);
//			para.setSpacingAfter(0);
//
//			doc.add(para);
			para = new Paragraph(new Phrase(" " ,new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);
			doc.add(para);
			
			String phara = "SELECT LKP_DISC,LKP_SRNO FROM LOOKUP WHERE LKP_CODE='CDTYPE' AND LKP_SRNO!=0 and LKP_SRNO = (select CDTYPE from CDMAST where TRNCD = "+trncd+") order by LKP_SRNO";
			Statement pa = Cn.createStatement();
			ResultSet par = pa.executeQuery(phara);
			String disp = "";
			int Srno = 0;
			if(par.next()){
				disp = par.getString(1);
				Srno = par.getInt(2);
			}
			if(Srno!=1)
				disp = "DEDUCTION";
			para = new Paragraph(new Phrase("Code Wise Employee Yearly List For: "+CodeMasterHandler.getCDesc(trncd)+" "+disp,new Font(Font.TIMES_ROMAN,9, Font.BOLD)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);
			doc.add(para);
			
			para = new Paragraph(new Phrase(" ",new Font(Font.TIMES_ROMAN,9)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);
			doc.add(para);
			
			
			int srno = 1;
				 Query1 =  ""
						    +"       SELECT y.trncd, disc, rtrim(e.lname)+' '+rtrim(e.fname)+' '+rtrim(e.mname) name,e.EMPCODE,"
							+ "       Sum(CASE Datepart(mm, trndt) WHEN 04 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) apr, "
							+ "       Sum(CASE Datepart(mm, trndt) WHEN 05 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) may, "
							+ "       Sum(CASE Datepart(mm, trndt) WHEN 06 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) jun, "
							+ "       Sum(CASE Datepart(mm, trndt) WHEN 07 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) jul, "
							+ "       Sum(CASE Datepart(mm, trndt) WHEN 08 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) aug, "
							+ "       Sum(CASE Datepart(mm, trndt) WHEN 09 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) sep, "
							+ "       Sum(CASE Datepart(mm, trndt) WHEN 10 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) oct, "
							+ "       Sum(CASE Datepart(mm, trndt) WHEN 11 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) nov, "
							+ "       Sum(CASE Datepart(mm, trndt) WHEN 12 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) dec, "
							+ "       Sum(CASE Datepart(mm, trndt) WHEN 01 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) jan, "
							+ "       Sum(CASE Datepart(mm, trndt) WHEN 02 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) feb, "
							+ "       Sum(CASE Datepart(mm, trndt) WHEN 03 THEN ( CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END ) ELSE 0 END) mar, "
							+ "       Sum(CASE WHEN Y.trncd IN ( 198, 199 ) THEN cal_amt ELSE net_amt END) AS Total "
							+ "       FROM   empmast e, cdmast c, ytdtran y "
							+ "       WHERE   " 
							+ "        e.empno = y.empno "
							+ "       AND y.trncd = c.trncd "
							+ "       AND y.trndt BETWEEN '"+frmdate+"' AND '"+todate+"'  and y.TRNCD="+trncd+""
			//				+ "       AND ( y.trncd < 300 OR y.trncd = 999 ) "
							+ "       AND y.trncd <> 199   "
							//+ "and e.STATUS='A'"
							+ "GROUP  BY e.empno, e.fname,e.lname,e.mname, e.EMPCODE, disc, y.trncd ORDER  BY y.trncd";
//					}	
				 System.out.println("-----"+Query1);
			st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(Query1);
			
			
			if(rs.next()){
			/*if(!rs.next()){
				System.out.println("Recordset EMpty");
			}*/
			PdfPTable main = new PdfPTable(17);
			main.setSpacingBefore(10);
			
			float totalMonthaly=0.0f;
			
			String str[] = new String[17];
			
			str[0]="Srno";			str[1]="Description";			str[2]="Empcode";
			str[3]="Name";	str[4]="Apr";	str[5]="May"; str[6]="Jun";
			str[7]="Jul";	str[8]="Aug";	str[9]="Sep"; str[10]="Oct";
			str[11]="Nov";str[12]="Dec"; str[13]="Jan"; str[14]="Feb"; str[15]="Mar"; str[16]="Total";
			
			main.setWidthPercentage(new float[]{7,25,18,40, 15,15, 15, 15, 15, 15,15,15, 15, 15, 15,15, 15}, rec);
			main.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell maincell ;	
			
			for(int i=0; i<str.length;i++ )
			{					
				maincell = new PdfPCell(new Phrase(str[i],fbold));
				maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
				main.addCell(maincell);
			}	
			doc.add(main);
			rs.beforeFirst();
			int cnt =0;
			
	//		if(rs.next()){
			while(rs.next())
			{
				PdfPTable main1 = new PdfPTable(17);
				main1.setWidthPercentage(new float[]{7,25,18,40, 15,15, 15, 15, 15, 15,15,15, 15, 15, 15,15, 15}, rec);
				main1.setHorizontalAlignment(Element.ALIGN_CENTER);	
				
				PdfPCell cell1 = new PdfPCell(new Phrase(""+srno,fsmall));  
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				main1.addCell(cell1);
				
				PdfPCell cell2 = new PdfPCell(new Phrase((rs.getString("disc")),fsmall));	
				cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
				main1.addCell(cell2);
				
				PdfPCell cell3 = new PdfPCell(new Phrase(""+rs.getString("empcode"),fsmall));
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				main1.addCell(cell3);
				
				PdfPCell cell4 = new PdfPCell(new Phrase((rs.getString("name")),fsmall));
				cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
				main1.addCell(cell4);
				
				aprTotal=aprTotal+Float.parseFloat(rs.getString("apr"));
				PdfPCell cell5 = new PdfPCell(new Phrase(""+Math.round(Float.parseFloat((rs.getString("apr")))),fsmall));
				cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
				main1.addCell(cell5);
				
				mayTotal=mayTotal+Float.parseFloat(rs.getString("may"));
				PdfPCell cell7 = new PdfPCell(new Phrase(""+Math.round(Float.parseFloat((rs.getString("may")))),fsmall));
				cell7.setHorizontalAlignment(Element.ALIGN_RIGHT);
				main1.addCell(cell7);
				
				juneTotal=juneTotal+Float.parseFloat(rs.getString("jun"));
				PdfPCell cell71 = new PdfPCell(new Phrase(""+Math.round(Float.parseFloat((rs.getString("jun")))),fsmall));
				cell71.setHorizontalAlignment(Element.ALIGN_RIGHT);
				main1.addCell(cell71);
				
				julyTotal=julyTotal+Float.parseFloat(rs.getString("jul"));
				PdfPCell cell61 = new PdfPCell(new Phrase(""+Math.round(Float.parseFloat((rs.getString("jul")))),fsmall));
				cell61.setHorizontalAlignment(Element.ALIGN_RIGHT);
				main1.addCell(cell61);
				
				augTotal=augTotal+Float.parseFloat(rs.getString("aug"));
				PdfPCell cell51 = new PdfPCell(new Phrase(""+Math.round(Float.parseFloat((rs.getString("aug")))),fsmall));
				cell51.setHorizontalAlignment(Element.ALIGN_RIGHT);
				main1.addCell(cell51);
				
				sepTotal=sepTotal+Float.parseFloat(rs.getString("sep"));
				PdfPCell cell41 = new PdfPCell(new Phrase(""+Math.round(Float.parseFloat((rs.getString("sep")))),fsmall));
				cell41.setHorizontalAlignment(Element.ALIGN_RIGHT);
				main1.addCell(cell41);
				
				octTotal=octTotal+Float.parseFloat(rs.getString("oct"));				
				PdfPCell cell411 = new PdfPCell(new Phrase(""+Math.round(Float.parseFloat((rs.getString("oct")))),fsmall));
				cell411.setHorizontalAlignment(Element.ALIGN_RIGHT);
				main1.addCell(cell411);
				
				novTotal=novTotal+Float.parseFloat(rs.getString("nov"));
				PdfPCell cell412 = new PdfPCell(new Phrase(""+Math.round(Float.parseFloat((rs.getString("nov")))),fsmall));
				cell412.setHorizontalAlignment(Element.ALIGN_RIGHT);
				main1.addCell(cell412);
				
				decTotal=decTotal+Float.parseFloat(rs.getString("dec"));
				PdfPCell cell413 = new PdfPCell(new Phrase(""+Math.round(Float.parseFloat((rs.getString("dec")))),fsmall));
				cell413.setHorizontalAlignment(Element.ALIGN_RIGHT);
				main1.addCell(cell413);
				
				janTotal=janTotal+Float.parseFloat(rs.getString("jan"));
				PdfPCell cell414 = new PdfPCell(new Phrase(""+Math.round(Float.parseFloat((rs.getString("jan")))),fsmall));
				cell414.setHorizontalAlignment(Element.ALIGN_RIGHT);
				main1.addCell(cell414);
				
				febTotal=febTotal+Float.parseFloat(rs.getString("feb"));
				PdfPCell cell415 = new PdfPCell(new Phrase(""+Math.round(Float.parseFloat((rs.getString("feb")))),fsmall));
				cell415.setHorizontalAlignment(Element.ALIGN_RIGHT);
				main1.addCell(cell415);
				
				marTotal=marTotal+Float.parseFloat(rs.getString("mar"));
				PdfPCell cell416 = new PdfPCell(new Phrase(""+Math.round(Float.parseFloat((rs.getString("mar")))),fsmall));
				cell416.setHorizontalAlignment(Element.ALIGN_RIGHT);
				main1.addCell(cell416);
				
				PdfPCell cell6 = new PdfPCell(new Phrase(""+Math.round(Float.parseFloat((rs.getString("Total")))),fsmall));
				cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
				main1.addCell(cell6);
				
				doc.add(main1);
				srno++;
				totalEmployee+=1;
				
			}
			
			PdfPTable main11 = new PdfPTable(17);
			main11.setWidthPercentage(new float[]{7,25,18,40, 15,15, 15, 15, 15, 15,15,15, 15, 15, 15,15, 15}, rec);
			main11.setHorizontalAlignment(Element.ALIGN_CENTER);	
			

			/*PdfPCell celle1 = new PdfPCell(new Phrase("",fsmall));  
			celle1.setHorizontalAlignment(Element.ALIGN_CENTER);
			main11.addCell(celle1);
			
			PdfPCell celle2 = new PdfPCell(new Phrase("",fsmall));  
			celle2.setHorizontalAlignment(Element.ALIGN_CENTER);
			main11.addCell(celle2);
			
			PdfPCell celle3 = new PdfPCell(new Phrase("",fsmall));  
			celle3.setHorizontalAlignment(Element.ALIGN_CENTER);
			main11.addCell(celle3);*/
			
			PdfPCell celle4 = new PdfPCell(new Phrase("MONTHLY TOTAL",new Font(Font.TIMES_ROMAN,8,FONT.BOLD)));  
			celle4.setHorizontalAlignment(Element.ALIGN_RIGHT);
			celle4.setColspan(4);
			main11.addCell(celle4);
			
			
			
			PdfPCell cellapr = new PdfPCell(new Phrase(""+Math.round(aprTotal),new Font(Font.TIMES_ROMAN,8,FONT.BOLD)));  
			cellapr.setHorizontalAlignment(Element.ALIGN_RIGHT);
			main11.addCell(cellapr);
			//totalMonthaly=+aprTotal;
			
			PdfPCell cellmay = new PdfPCell(new Phrase(""+Math.round(mayTotal),new Font(Font.TIMES_ROMAN,8,FONT.BOLD)));  
			cellmay.setHorizontalAlignment(Element.ALIGN_RIGHT);
			main11.addCell(cellmay);
			
			PdfPCell celljun = new PdfPCell(new Phrase(""+Math.round(juneTotal),new Font(Font.TIMES_ROMAN,8,FONT.BOLD)));  
			celljun.setHorizontalAlignment(Element.ALIGN_RIGHT);
			main11.addCell(celljun);
			
			PdfPCell celljul = new PdfPCell(new Phrase(""+Math.round(julyTotal),new Font(Font.TIMES_ROMAN,8,FONT.BOLD)));  
			celljul.setHorizontalAlignment(Element.ALIGN_RIGHT);
			main11.addCell(celljul);
			
			PdfPCell cellaug = new PdfPCell(new Phrase(""+Math.round(augTotal),new Font(Font.TIMES_ROMAN,8,FONT.BOLD)));  
			cellaug.setHorizontalAlignment(Element.ALIGN_RIGHT);
			main11.addCell(cellaug);
			
			PdfPCell cellsep = new PdfPCell(new Phrase(""+Math.round(sepTotal),new Font(Font.TIMES_ROMAN,8,FONT.BOLD)));  
			cellsep.setHorizontalAlignment(Element.ALIGN_RIGHT);
			main11.addCell(cellsep);
			
			
			PdfPCell celloct = new PdfPCell(new Phrase(""+Math.round(octTotal),new Font(Font.TIMES_ROMAN,8,FONT.BOLD)));  
			celloct.setHorizontalAlignment(Element.ALIGN_RIGHT);
			main11.addCell(celloct);
			
			PdfPCell cellnov = new PdfPCell(new Phrase(""+Math.round(novTotal),new Font(Font.TIMES_ROMAN,8,FONT.BOLD)));  
			cellnov.setHorizontalAlignment(Element.ALIGN_RIGHT);
			main11.addCell(cellnov);
			
			PdfPCell celldec = new PdfPCell(new Phrase(""+Math.round(decTotal),new Font(Font.TIMES_ROMAN,8,FONT.BOLD)));  
			celldec.setHorizontalAlignment(Element.ALIGN_RIGHT);
			main11.addCell(celldec);
			
			PdfPCell celljan = new PdfPCell(new Phrase(""+Math.round(janTotal),new Font(Font.TIMES_ROMAN,8,FONT.BOLD)));  
			celljan.setHorizontalAlignment(Element.ALIGN_RIGHT);
			main11.addCell(celljan);
			
			PdfPCell cellfeb = new PdfPCell(new Phrase(""+Math.round(febTotal),new Font(Font.TIMES_ROMAN,8,FONT.BOLD)));  
			cellfeb.setHorizontalAlignment(Element.ALIGN_RIGHT);
			main11.addCell(cellfeb);
			
			PdfPCell cellmar = new PdfPCell(new Phrase(""+Math.round(marTotal),new Font(Font.TIMES_ROMAN,8,FONT.BOLD)));  
			cellmar.setHorizontalAlignment(Element.ALIGN_RIGHT);
			main11.addCell(cellmar);
			
			totalMonthaly=aprTotal+mayTotal+juneTotal+julyTotal+augTotal+sepTotal+octTotal+novTotal+decTotal+janTotal+febTotal+marTotal;
			PdfPCell cellTM = new PdfPCell(new Phrase(""+Math.round(totalMonthaly),new Font(Font.TIMES_ROMAN,8,FONT.BOLD)));  
			cellTM.setHorizontalAlignment(Element.ALIGN_RIGHT);
			main11.addCell(cellTM);
			
			doc.add(main11);
			
			
			
			
			para = new Paragraph(new Phrase("Total Employee's : "+totalEmployee,new Font(Font.TIMES_ROMAN,8,Font.BOLD)));  
			para.setAlignment(Element.ALIGN_LEFT);
			para.setSpacingAfter(10);
			doc.add(para);
              }else{
				System.out.println("no record found");
				para = new Paragraph(new Phrase("Record not found",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(5);
				doc.add(para);
			}
			doc.close();	
			Cn.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}

	
	}

	public void printCtcChangeReport(String frmDate, String toDate, String imgpath, String filePath) {
		
	        
	    int lastyr=0,lastMon=0,lday=0;
		 long millis=System.currentTimeMillis();  
		 java.sql.Date date=new java.sql.Date(millis);  
		 System.out.println("date..."+date);
		 DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		 String reportDate = df.format(date);
		 

		Document doc = new Document();
		try{
		
			int totalemp=0;
			int count=1;
						
		
			PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filePath));
			
			doc.setPageSize(PageSize.A4);
			
			doc.open();
			
			Font FONT = new Font(Font.HELVETICA, 52, Font.NORMAL, new GrayColor(0.75f));
			ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Ch.Shahu Hospital",FONT), 297.5f, 421, 45);
			Image image1 = Image.getInstance(imgpath);
			Phrase title = new Phrase("Rajeshri Chatrapati Shahu Maharaj hospital,Jalgaon",new Font(FONT.TIMES_ROMAN,8,Font.BOLD));
			Paragraph para = new Paragraph(title);
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingBefore(0);

			image1.scaleAbsolute(60f,50f);
			image1.setAbsolutePosition(40f, 750f);

			doc.add(image1);
			doc.add(para);


			para = new Paragraph(new Phrase("Rajeshri Chatrapati Shahu Maharaj hospital, Shahu Nagar, Jalgaon-425001",new Font(Font.TIMES_ROMAN,8)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);

			doc.add(para);
			para = new Paragraph(new Phrase("Tel : 0257 2223301",new Font(Font.TIMES_ROMAN,8)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);

			doc.add(para);
//			para = new Paragraph(new Phrase("Email : hrd@namcobank.in",new Font(Font.TIMES_ROMAN,8)));
//			para.setAlignment(Element.ALIGN_CENTER);
//			para.setSpacingAfter(0);
//
//			doc.add(para);
			para = new Paragraph(new Phrase("Employees CTC Change List " ,new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);
			doc.add(para);
			para = new Paragraph(new Phrase("Date:-" + reportDate,new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);
			doc.add(para);
			/*para = new Paragraph(new Phrase("Employees Transfer List For :-[" + fromdate+"] - ["+todate+"]",new Font(Font.TIMES_ROMAN,8)));
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(0);
			doc.add(para);*/
			
			String colName[]={"SR NO","EMP CODE","EMP NAME","CURRENT DESIGNATION","CURRENT BASIC",
					"OLD DESIGNATION","OLD BASIC","REMARK"};
			

			//Rectangle rec = new  Rectangle(131,100);
			/*Rectangle rec = new  Rectangle(150,115);*/
			Rectangle rec = new  Rectangle(200,110);
			
			PdfPTable headtab = null;

			headtab= new PdfPTable(9);	
			headtab.setSpacingBefore(0);
			headtab.setWidthPercentage(new float[]{7,10,30,20,20,20,20,20,24}, rec);
			headtab.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			PdfPTable datatab=new PdfPTable(9);
			datatab.setSpacingBefore(0);
			//7,7,28,17,16,18,24,24
			datatab.setWidthPercentage(new float[]{7,10,30,20,20,20,20,20,24}, rec);
			datatab.setHorizontalAlignment(Element.ALIGN_LEFT);
		
			Connection conn=ConnectionManager.getConnection();
			Statement st=st = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			String EmpSql="";
			
			  
			 /* EmpSql="SELECT m.empcode,RTRIM( m.lname)+' '+RTRIM(m.fname)+' '+RTRIM(m.mname) AS NAME ,"
						+"e.empno, l.lkp_disc   AS  currdesig,p1.INP_AMT as curr_Basic,e.effdate  AS curreffdate,"
						+"(SELECT LKP_DISC FROM LOOKUP WHERE LKP_CODE='DESIG' "
						+"AND LKP_SRNO= (SELECT DESIG FROM   emptran et WHERE  et.empno = M.EMPNO AND srno = (SELECT Max(srno) "
						+"FROM   emptran d WHERE  d.empno = ET.EMPNO AND d.trncd = 50 AND d.srno  IN(SELECT Max(srno) "
						+"FROM   emptran u WHERE  u.empno = D.EMPNO AND u.trncd =50)))) AS OLDDESIG,"
						+"P2.INP_AMT AS OLD_BASIC,p.site_name    AS new_site, "
						+"(SELECT effdate FROM   emptran et WHERE  et.empno = M.EMPNO AND srno = (SELECT Max(srno) FROM   emptran d "
						+"WHERE  d.empno =ET.EMPNO AND d.trncd = 50 AND d.srno  IN(SELECT Max(srno) FROM   emptran u "
						+"WHERE  u.empno = D.EMPNO AND u.trncd =50))) AS OLDEFFDT "
						+"FROM   empmast m,paytran p1,PAYTRAN_STAGE P2,emptran e, project_sites p, emptran k, project_sites h, lookup l, "
						+"emptran a, lookup lk WHERE  m.empno = e.empno  and p1.empno=e.EMPNO and p1.TRNCD=101and p2.empno=e.EMPNO and p2.TRNCD=101 AND P2.TRNDT='2018-08-31' "
						+"AND e.prj_srno = p.site_id AND k.empno = e.empno AND h.site_id = k.prj_srno AND k.srno = (SELECT Max(srno) "
						+"FROM   emptran c WHERE  c.empno = m.empno AND c.trncd = 80) AND e.srno = (SELECT Max(srno) FROM   emptran b "
						+"WHERE  b.empno = m.empno AND b.trncd = 80) AND m.status = 'A' AND a.empno = e.empno AND a.srno = (SELECT Max(srno) "
						+"FROM   emptran g WHERE  g.empno = m.empno) AND l.lkp_code = 'DESIG' AND l.lkp_srno = a.desig AND lk.lkp_code = 'DEPT' AND e.effdate between '"+frmDate+"' and '"+toDate+"' "
						+"AND lk.lkp_srno = a.dept ORDER  BY a.EMPNO";*/

			  EmpSql = "SELECT m.empcode, "
					  + "       Rtrim( m.lname) + ' ' + Rtrim(m.fname) + ' ' "
					  + "       + Rtrim(m.mname)                                                    AS "
					  + "       NAME, "
					  + "       e.empno, "
					  + "       l.lkp_disc                                                          AS "
					  + "       currdesig, "
					  + "       p1.inp_amt                                                          AS "
					  + "       curr_Basic, "
					  + "       e.effdate                                                           AS "
					  + "       curreffdate, "
					  + "       (SELECT lkp_disc "
					  + "        FROM   lookup "
					  + "        WHERE  lkp_code = 'DESIG' "
					  + "               AND lkp_srno = (SELECT desig "
					  + "                               FROM   emptran et "
					  + "                               WHERE  et.empno = M.empno "
					  + "                                      AND srno = (SELECT Max(srno) "
					  + "                                                  FROM   emptran d "
					  + "                                                  WHERE  d.empno = ET.empno "
					  + "                                                         AND d.trncd = 50 "
					  + "                                                         AND d.srno IN(SELECT "
					  + "                                                             Max(srno) "
					  + "                                                                       FROM "
					  + "                                                             emptran u "
					  + "                                                                       WHERE "
					  + "                                                             u.empno = D.empno "
					  + "                                                             AND u.trncd = 50) "
					  + "                                                 )))                       AS "
					  + "       OLDDESIG, "
					  + "       P2.inp_amt                                                          AS "
					  + "       OLD_BASIC, "
					  + "       p.site_name                                                         AS "
					  + "       new_site, "
					  + "       (SELECT effdate "
					  + "        FROM   emptran et "
					  + "        WHERE  et.empno = M.empno "
					  + "               AND srno = (SELECT Max(srno) "
					  + "                           FROM   emptran d "
					  + "                           WHERE  d.empno = ET.empno "
					  + "                                  AND d.trncd = 50 "
					  + "                                  AND d.srno IN(SELECT Max(srno) "
					  + "                                                FROM   emptran u "
					  + "                                                WHERE  u.empno = D.empno "
					  + "                                                       AND u.trncd = 50))) AS "
					  + "       OLDEFFDT "
					  + "FROM   empmast m, "
					  + "      PAYTRAN_STAGE p1, "
					  + "       paytran_stage P2, "
					  + "       emptran e, "
					  + "       project_sites p, "
					  + "       emptran k, "
					  + "       project_sites h, "
					  + "       lookup l, "
					  + "       emptran a, "
					  + "       lookup lk "
					  + "WHERE  m.empno = e.empno "
					  + "       AND p1.empno = e.empno "
					  + "       AND p1.trncd = 101"
					  + " 		AND P1.TRNDT=(SELECT MAX(TRNDT) FROM PAYTRAN_STAGE) "
					  + "       AND p2.empno = e.empno "
					  + "       AND p2.trncd = 101 "
					 
					  + "        and p2.TRNDT = (SELECT DATEADD(d, -1, DATEADD(m, DATEDIFF(m, 0, DATEADD(month, -1,effdate )) + 1, 0)) "
					  + "        FROM   emptran et "
					  + "        WHERE  et.empno = m.empno "
					  + "               AND srno = (SELECT Max(srno) "
					  + "                           FROM   emptran d "
					  + "                           WHERE  d.empno = ET.empno "
					  + "                                  AND d.trncd = 80 "
					  + "                                  AND d.srno IN(SELECT Max(srno) "
					  + "                                                FROM   emptran u "
					  + "                                                WHERE  u.empno = D.empno "
					  + "                                                       AND u.trncd = 80))) "
					  + "       AND e.prj_srno = p.site_id "
					  + "       AND k.empno = e.empno "
					  + "       AND h.site_id = k.prj_srno "
					  + "       AND k.srno = (SELECT Max(srno) "
					  + "                     FROM   emptran c "
					  + "                     WHERE  c.empno = m.empno "
					  + "                            AND c.trncd = 80) "
					  + "       AND e.srno = (SELECT Max(srno) "
					  + "                     FROM   emptran b "
					  + "                     WHERE  b.empno = m.empno "
					  + "                            AND b.trncd = 80) "
					  + "       AND m.status = 'A' "
					  + "       AND a.empno = e.empno "
					  + "       AND a.srno = (SELECT Max(srno) "
					  + "                     FROM   emptran g "
					  + "                     WHERE  g.empno = m.empno) "
					  + "       AND l.lkp_code = 'DESIG' "
					  + "       AND l.lkp_srno = a.desig "
					  + "       AND lk.lkp_code = 'DEPT' "
					  + "       AND e.effdate between '"+frmDate+"' and '"+toDate+"' "
					  + "       AND lk.lkp_srno = a.dept "
					  + "ORDER  BY a.empno";
		
			 	System.out.println("CTC Change List:"+EmpSql);
			ResultSet emp = st.executeQuery(EmpSql);

			if(!emp.next())
			{
				System.out.println("no record found");
				
				
				PdfPTable datatab1=new PdfPTable(1);
				datatab1.setSpacingBefore(2);
				datatab1.setWidthPercentage(new float[]{107}, rec);
				PdfPCell c11 = new PdfPCell(new Phrase("Record Not found ",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));  
				datatab1.addCell(c11);
				doc.add(datatab1);
				
			}else{

				
				emp.previous();
				headtab.setSpacingBefore(10);
				/*PdfPCell sr[] = null;
				for(int i=0;i<colName.length;i++)
				{
					System.out.println("col name"+colName[i]);
					sr[i] = new PdfPCell(new Phrase(""+colName[i],new Font(Font.TIMES_ROMAN,8,Font.BOLD)));  
					headtab.addCell(sr[i]);
				}*/
				
				PdfPCell sr = new PdfPCell(new Phrase("Sr No",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));  
				headtab.addCell(sr);
				
				PdfPCell c1 = new PdfPCell(new Phrase("Emp Code",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));  
				headtab.addCell(c1);
				
				PdfPCell c2 = new PdfPCell(new Phrase("EMP NAME",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
				headtab.addCell(c2);
				
				PdfPCell c5 = new PdfPCell(new Phrase("CURRENT DESIGNATION",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
				headtab.addCell(c5);
				
				PdfPCell c3 = new PdfPCell(new Phrase("CURRENT BASIC",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
				headtab.addCell(c3);
				
				PdfPCell c6 = new PdfPCell(new Phrase("DATE OF REVERSION",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
				headtab.addCell(c6);
				
				PdfPCell c31 = new PdfPCell(new Phrase("OLD DESIGNATION",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
				headtab.addCell(c31);
				
			/*	PdfPCell c4 = new PdfPCell(new Phrase("DESIGNATION",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
				headtab.addCell(c4);*/
				
				PdfPCell c9 = new PdfPCell(new Phrase("OLD BASIC",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
				headtab.addCell(c9);
				
				PdfPCell c10 = new PdfPCell(new Phrase("REMARK",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
				headtab.addCell(c10);
				
				

				doc.add(headtab);
			
				while(emp.next())
				{
					    Double current_Basic = Double.parseDouble(emp.getString("curr_basic"));							
						Double	old_Basic = Double.parseDouble(emp.getString("OLD_BASIC"));
																					
					//if(emp.getString("currdesig").equalsIgnoreCase(emp.getString("OLDDESIG")))
					/*if(emp.getString("curr_basic").equalsIgnoreCase(emp.getString("OLD_BASIC")))
					{
						System.out.println(emp.getString("curr_basic")+"  curr_basic "+emp.getString("OLD_BASIC")+"  OLD_BASIC");
					}
					else{*/
						
					
				//System.out.println("empcode..."+emp.getString("EMPCODE"));
						totalemp++;
						PdfPCell srno = new PdfPCell(new Phrase(""+totalemp,new Font(Font.TIMES_ROMAN,8,Font.BOLD)));  
						datatab.addCell(srno);
						PdfPCell cell1 = new PdfPCell(new Phrase(""+emp.getString("EMPCODE"),new Font(Font.TIMES_ROMAN,8,Font.BOLD)));  
						datatab.addCell(cell1);
						/*PdfPCell cell2 = new PdfPCell(new Phrase(""+emp.getString("LNAME")+" "+emp.getString("FNAME")+" "+emp.getString("MNAME"),new Font(Font.TIMES_ROMAN,6,Font.BOLD)));
						datatab.addCell(cell2);*/
						
						PdfPCell cell2 = new PdfPCell(new Phrase(""+emp.getString("NAME"),new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
						datatab.addCell(cell2);
						
						PdfPCell cell3 = new PdfPCell(new Phrase(""+emp.getString("currdesig"),new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
						datatab.addCell(cell3);
						PdfPCell cell31 = new PdfPCell(new Phrase(""+emp.getString("curr_basic"),new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
						datatab.addCell(cell31);
						PdfPCell cell4 = new PdfPCell(new Phrase(""+emp.getString("curreffdate"),new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
						datatab.addCell(cell4);
						
						PdfPCell cell5 = new PdfPCell(new Phrase(""+emp.getString("OLDDESIG"),new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
						datatab.addCell(cell5);
						
						PdfPCell cellQ5 = new PdfPCell(new Phrase(""+emp.getString("OLD_BASIC"),new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
						datatab.addCell(cellQ5);
						
						
						if(current_Basic>old_Basic){
						
						PdfPCell cellW5 = new PdfPCell(new Phrase("Increment ",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
						datatab.addCell(cellW5);
						}
						else{
							PdfPCell cellW5 = new PdfPCell(new Phrase("Decrement ",new Font(Font.TIMES_ROMAN,8,Font.BOLD)));
							datatab.addCell(cellW5);
						}
						/*PdfPCell cellE5 = new PdfPCell(new Phrase(""+emp.getString("currdesig"),new Font(Font.TIMES_ROMAN,6,Font.BOLD)));
						datatab.addCell(cellE5);*/
						/*}
						else
						{
							PdfPCell cell5 = new PdfPCell(new Phrase(""+emp.getString("old_site")+"--"+ emp.getString("olddept"),new Font(Font.TIMES_ROMAN,6,Font.NORMAL)));
							datatab.addCell(cell5);
						}
						if(emp.getString("dept")==null)
						{
						PdfPCell cell6 = new PdfPCell(new Phrase(""+emp.getString("new_site"),new Font(Font.TIMES_ROMAN,6,Font.NORMAL)));
						datatab.addCell(cell6);
						}
						else
						{
							PdfPCell cell6 = new PdfPCell(new Phrase(""+emp.getString("new_site")+"--"+ emp.getString("dept"),new Font(Font.TIMES_ROMAN,6,Font.NORMAL)));
							datatab.addCell(cell6);
						}
						*/
						
					//}
					}
				doc.add(datatab);
				PdfPTable datatab1=new PdfPTable(1);
				datatab1.setSpacingBefore(10);
				datatab1.setWidthPercentage(new float[]{107}, rec);
				PdfPCell c11 = new PdfPCell(new Phrase("Total Employees Promotion:- "+totalemp,new Font(Font.TIMES_ROMAN,10,Font.BOLD)));  
				datatab1.addCell(c11);
				doc.add(datatab1);
				
			
			}
		
			doc.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
