package payroll.Core;

import java.awt.Color;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.jfree.ui.Align;

import payroll.DAO.ConnectionManager;
import payroll.Model.RepoartBean;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;

public class PfChallanDAO {

	public static Paragraph createPara(String value, Font f, int alignment) {
		Paragraph para = new Paragraph(value, f);
		para.setAlignment(alignment);
		para.setSpacingAfter(0);
		para.setSpacingBefore(0);
		return para;

	}

	public static PdfPCell createCell(String value, Font f, int alignment,
			float w) {
		PdfPCell cell = new PdfPCell(new Phrase(value, f));
		cell.addElement(createPara(value, f, alignment));
		cell.setBorderWidth(w);

		return cell;
	}

	public static void PfChallan(String rdt, String type, String filepath,
			String imagepath) {
		// System.out.println("in pf cahallan DAO");
		RepoartBean repBean = new RepoartBean();
		Connection con = null, conn = null;
		Statement st = null;
		ResultSet rs = null;
		boolean flag = ("A").equalsIgnoreCase(type) ? true : false;
		// ResultSet trn = null;

		try {

			int total_subs = 0;
			float total_Amt = 0.0f;
			float emp_shr = 0;
			float empr_shr = 0, ac_1 = 0, ac_2 = 0, ac_10 = 0, ac_21 = 0, ac_22 = 0;
			if (type.equalsIgnoreCase("A")) {

				conn = ConnectionManager.getConnection();
				st = conn.createStatement();
				rs = st.executeQuery("select Count(Distinct(empno)) from paytran_stage where trncd=201 and trndt between '"
						+ ReportDAO.BOM(rdt)
						+ "' and '"
						+ ReportDAO.EOM(rdt)
						+ "' ");
				while (rs.next()) {
					total_subs = rs.getInt(1);
				}

				ResultSet rs2 = st
						.executeQuery("select Sum(net_amt) from paytran_stage where trncd in(101,102) and trndt between '"
								+ ReportDAO.BOM(rdt)
								+ "' and '"
								+ ReportDAO.EOM(rdt)
								+ "' and empno in(select DISTINCT(empno) from paytran_stage where trncd=201 and trndt between '"
								+ ReportDAO.BOM(rdt)
								+ "' and '"
								+ ReportDAO.EOM(rdt) + "') ");
				while (rs2.next()) {
					total_Amt = rs2.getInt(1);
					System.out.println("Total amount =" + total_Amt);
				}

				// Calculations for PF Challan

				// A/c No. 1 Employees Share 12% of Basic PF Employers Share
				// 3.67% of Basic EPF

				emp_shr = (float) (total_Amt / 100 * 12);
				empr_shr = (float) (total_Amt / 100 * 3.67);

				ac_1 = (float) (emp_shr + empr_shr);
				ac_2 = (float) (total_Amt / 100 * 0.65);
				ac_10 = (float) (total_Amt / 100 * 8.33);
				ac_21 = (float) (total_Amt / 100 * 0.50);
				ac_22 = (float) (total_Amt / 100 * 0.01);
			}
			if (type.equalsIgnoreCase("B")) {

			}

			/*
			 * System.out.println("Amt= 6500"); System.out.println("Employee ="
			 * +emp_shr); System.out.println("Employer="+empr_shr);
			 * System.out.println("A/c no1 ="+ac_1);
			 * System.out.println("A/c no2 ="+ac_2);
			 * System.out.println("A/c no10 ="+ac_10);
			 * System.out.println("A/c no21 ="+ac_21);
			 * System.out.println("A/c no22 ="+ac_22);
			 */

			ReportDAO.OpenCon("", "", "", repBean);
			con = repBean.getCn();

			// SimpleDateFormat sdf = new
			// SimpleDateFormat("yyyy-MM-dd");//yyyy-MM-dd'T'HH:mm:ss
			// SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");

			Document doc = new Document();
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(
					filepath));
			doc.open();
			doc.setPageSize(PageSize.A4);
			Font f1 = new Font(Font.HELVETICA, 9);
			Font f2 = new Font(Font.HELVETICA, 9, Font.BOLD);
			Font f3 = new Font(Font.HELVETICA, 7, Font.BOLD);
			Font f4 = new Font(Font.HELVETICA, 7, Font.NORMAL);

			PdfPTable tab = new PdfPTable(2);
			tab.setWidthPercentage(new float[] { 85, 15 }, new Rectangle(100,
					100));
			tab.addCell(createCell(
					"               COMBINED CHALLAN OF A/C. NO. 1,2,10,21 & 22",
					f1, Element.ALIGN_CENTER, 0));
			tab.addCell(createCell("ORIGINAL", f3, Element.ALIGN_JUSTIFIED, 0));
			tab.addCell(createCell("               (STATE BANK OF INDIA)", f2,
					Element.ALIGN_CENTER, 0));
			tab.addCell(createCell("DUPLICATE", f3, Element.ALIGN_JUSTIFIED, 0));
			tab.addCell(createCell(
					"               EMPLOYEE'S PROVIDENT FUND ORGANISATION",
					f2, Element.ALIGN_CENTER, 0));
			tab.addCell(createCell("TRIPLICATE", f3, Element.ALIGN_JUSTIFIED, 0));
			tab.addCell(createCell(
					"               (USE SAPERATE CHALLAN FOR EACH MONTH)", f2,
					Element.ALIGN_CENTER, 0));
			tab.addCell(createCell("QUADRIPLICATE", f3,
					Element.ALIGN_JUSTIFIED, 0));
			tab.setSpacingAfter(15);
			doc.add(tab);

			PdfPTable tab2 = new PdfPTable(3);
			tab2.setWidthPercentage(new float[] { 35, 30, 35 }, new Rectangle(
					100, 100));
			tab2.addCell(createCell("ESTABLISHMENT CODE NO.: MH / 51492", f4,
					Element.ALIGN_JUSTIFIED, 0));
			tab2.addCell(createCell("ACCOUNT GROUP NO.: III", f4,
					Element.ALIGN_JUSTIFIED, 0));
			tab2.addCell(createCell("PAID BY CASH/CHEQUE /D.D : Cash", f4,
					Element.ALIGN_JUSTIFIED, 0));
			tab2.setSpacingAfter(15);
			doc.add(tab2);

			PdfPTable t1 = new PdfPTable(2);
			t1.setSpacingAfter(10);
			PdfPCell t1_c1 = new PdfPCell(new Phrase("", f4));
			t1_c1.setBorder(0);

			PdfPCell t1_c2 = new PdfPCell(new Phrase("", f4));
			t1_c2.setBorder(0);

			t1.setWidthPercentage(new float[] { 50, 50 }, new Rectangle(100,
					100));

			PdfPTable t1_st1 = new PdfPTable(3);
			// PdfCell ccc = null;
			t1_st1.setWidthPercentage(new float[] { 40, 30, 30 },
					new Rectangle(100, 100));

			PdfPCell cell = new PdfPCell(new Phrase("", f4));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setVerticalAlignment(Align.BOTTOM);
			t1_st1.addCell(cell);

			cell = new PdfPCell(new Phrase("Employer's Share", f4));
			// cell.setBorder(Rectangle.NO_BORDER);
			cell.setLeft(5);
			t1_st1.addCell(cell);

			PdfPTable yr = new PdfPTable(2);
			yr.addCell(new Phrase(flag ? "" + rdt.subSequence(3, 6) : "     ",
					f4));
			yr.addCell(new Phrase(flag ? "" + rdt.subSequence(7, 11) : "     ",
					f4));

			cell = new PdfPCell(yr);
			cell.setBorder(Rectangle.NO_BORDER);
			t1_st1.addCell(cell);

			cell = new PdfPCell(new Phrase("DUES FOR THE MONTH OF ", f4));
			// cell.setBorder(Rectangle.NO_BORDER);
			// cell.setBorder(5);
			cell.setRight(5);

			cell.setVerticalAlignment(Align.BOTTOM);
			t1_st1.addCell(cell);
			cell = new PdfPCell(new Phrase("", f4));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setVerticalAlignment(Align.BOTTOM);
			t1_st1.addCell(cell);
			cell = new PdfPCell(new Phrase("", f4));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setVerticalAlignment(Align.BOTTOM);
			t1_st1.addCell(cell);

			cell = new PdfPCell(new Phrase(" ", f4));
			cell.setBorder(Rectangle.NO_BORDER);
			t1_st1.addCell(cell);

			cell = new PdfPCell(new Phrase("Employee's Share", f4));
			// cell.setBorder(Rectangle.NO_BORDER);
			cell.setLeft(5);
			t1_st1.addCell(cell);

			yr = new PdfPTable(2);
			yr.addCell(new Phrase(flag ? "" + rdt.subSequence(3, 6) : "     ",
					f4));
			yr.addCell(new Phrase(flag ? "" + rdt.subSequence(7, 11) : "     ",
					f4));

			cell = new PdfPCell(yr);
			cell.setBorder(Rectangle.NO_BORDER);
			t1_st1.addCell(cell);

			t1_c1.addElement(t1_st1);

			yr = new PdfPTable(3);
			PdfPCell eg = new PdfPCell(new Phrase("", f4));
			eg.setBorder(0);
			yr.addCell(eg);
			eg = new PdfPCell(new Phrase("Date of Payment :", f4));
			eg.setBorder(0);
			yr.addCell(eg);

			eg = new PdfPCell(new Phrase(flag ? rdt : "     ", f4));

			yr.addCell(eg);

			// t1_c2.setVerticalAlignment(Align.CENTER);
			t1_c2.addElement(yr);

			t1.addCell(t1_c1);
			t1.addCell(t1_c2);

			doc.add(t1);

			PdfPTable tab4 = new PdfPTable(7);
			PdfPCell p1 = new PdfPCell(new Phrase("", f4));
			tab4.setWidthPercentage(new float[] { 25, 5, 20, 5, 20, 5, 20 },
					new Rectangle(100, 100));

			p1 = new PdfPCell(new Phrase("A/C Number ", f4));
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase("", f4));
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase("A/C No. 1  ", f4));
			p1.setHorizontalAlignment(Element.ALIGN_CENTER);
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase("", f4));
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase("A/C NO. 10 ", f4));
			p1.setHorizontalAlignment(Element.ALIGN_CENTER);
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase("", f4));
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase("A/C No. 21", f4));
			p1.setHorizontalAlignment(Element.ALIGN_CENTER);
			p1.setBorder(0);
			tab4.addCell(p1);

			p1 = new PdfPCell(new Phrase("Total No. of Subscribers:", f4));
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase("", f4));
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase(flag ? "" + total_subs : "     ", f4));
			p1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase("", f4));
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase(flag ? "" + total_subs : "     ", f4));
			p1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase("", f4));
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase(flag ? "" + total_subs : "     ", f4));
			p1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tab4.addCell(p1);

			p1 = new PdfPCell(new Phrase("Total Wages Due:", f4));
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase("", f4));
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase(flag ? "" + total_Amt : "     ", f4));
			p1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase("", f4));
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase(flag ? "" + total_Amt : "     ", f4));
			p1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase("", f4));
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase(flag ? "" + total_Amt : "     ", f4));
			p1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tab4.addCell(p1);
			tab4.setSpacingAfter(10);
			doc.add(tab4);

			// DRAW HORIZONTAL LINE
			Chunk CONNECT = new Chunk(new LineSeparator(0.3f, 100, Color.BLACK,
					Element.ALIGN_LEFT, 1.0f));
			Paragraph p = new Paragraph("");

			p.add(CONNECT);
			doc.add(p);

			PdfPTable tab51 = new PdfPTable(8);
			tab51.setWidthPercentage(new float[] { 6, 19, 12, 12, 12, 12, 12,
					15 }, new Rectangle(100, 100));
			PdfPCell pp = new PdfPCell(new Phrase("", f4));

			pp = new PdfPCell(new Phrase("SR.NO", f3));
			pp.setHorizontalAlignment(Element.ALIGN_CENTER);
			pp.setBorder(0);
			tab51.addCell(pp);

			pp = new PdfPCell(new Phrase("PARTICULARS", f3));
			pp.setHorizontalAlignment(Element.ALIGN_CENTER);
			pp.setBorder(0);
			tab51.addCell(pp);

			pp = new PdfPCell(new Phrase("A/C NO. 1", f3));
			pp.setHorizontalAlignment(Element.ALIGN_CENTER);
			pp.setBorder(0);
			tab51.addCell(pp);

			pp = new PdfPCell(new Phrase("A/C NO. 2", f3));
			pp.setHorizontalAlignment(Element.ALIGN_CENTER);
			pp.setBorder(0);
			tab51.addCell(pp);
			pp = new PdfPCell(new Phrase("A/C NO. 10", f3));
			pp.setHorizontalAlignment(Element.ALIGN_CENTER);
			pp.setBorder(0);
			tab51.addCell(pp);

			pp = new PdfPCell(new Phrase("A/C NO. 21", f3));
			pp.setHorizontalAlignment(Element.ALIGN_CENTER);
			pp.setBorder(0);
			tab51.addCell(pp);

			pp = new PdfPCell(new Phrase("A/C NO. 22", f3));
			pp.setHorizontalAlignment(Element.ALIGN_CENTER);
			pp.setBorder(0);
			tab51.addCell(pp);

			pp = new PdfPCell(new Phrase("TOTAL", f3));
			pp.setHorizontalAlignment(Element.ALIGN_CENTER);
			pp.setBorder(0);
			tab51.addCell(pp);

			doc.add(tab51);

			p.add(CONNECT);
			doc.add(p);

			PdfPTable tab5 = new PdfPTable(8);

			tab5.setWidthPercentage(
					new float[] { 5, 20, 12, 12, 12, 12, 12, 15 },
					new Rectangle(100, 100));

			PdfPTable eg11 = new PdfPTable(1);
			PdfPCell eg12 = new PdfPCell(new Phrase("", f4));

			pp = new PdfPCell(new Phrase("1", f4));

			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("Employer's Share of Count.", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? "" + empr_shr : "     ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? "" + ac_10 : "     ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? "" + ac_21 : "     ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? ""
					+ (empr_shr + ac_10 + ac_21) : "     ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("2", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("Employee's Share of Count.", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? "" + emp_shr : "     ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase("    ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? "" + (emp_shr) : "     ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("3", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("Adm. Charges", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? "" + (ac_2) : "     ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);
			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? "" + (ac_22) : "     ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(
					flag ? "" + (ac_2 + ac_22) : "     ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("4", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("Insp. Charges", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase("  ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase("  ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);
			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase("  ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("5", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("Penal Charges", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase("  ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase("  ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase("  ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase("  ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase("  ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase("  ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("6", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("Misc. Payments", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase("  ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase("  ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase("  ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			/*
			 * pp=new PdfPCell(new Phrase("7",f4)); pp.setBorder(0);
			 * tab5.addCell(pp);
			 * 
			 * pp=new PdfPCell(new Phrase("Interest on Securities",f4));
			 * pp.setBorder(0); tab5.addCell(pp);
			 * 
			 * pp=new PdfPCell(new Phrase("",f4)); pp.setBorder(0);
			 * tab5.addCell(pp);
			 * 
			 * pp=new PdfPCell(new Phrase("",f4)); pp.setBorder(0);
			 * tab5.addCell(pp); pp=new PdfPCell(new Phrase("",f4));
			 * pp.setBorder(0); tab5.addCell(pp);
			 * 
			 * pp=new PdfPCell(new Phrase("",f4)); pp.setBorder(0);
			 * tab5.addCell(pp);
			 * 
			 * pp=new PdfPCell(new Phrase("",f4)); pp.setBorder(0);
			 * tab5.addCell(pp);
			 * 
			 * pp=new PdfPCell(new Phrase("",f4)); pp.setBorder(0);
			 * tab5.addCell(pp);
			 * 
			 * 
			 * pp=new PdfPCell(new Phrase("",f4)); pp.setBorder(0);
			 * tab5.addCell(pp);
			 */
			doc.add(tab5);

			p.add(CONNECT);
			doc.add(p);

			PdfPTable tab521 = new PdfPTable(8);
			tab521.setWidthPercentage(new float[] { 5, 20, 12, 12, 12, 12, 12,
					15 }, new Rectangle(100, 100));

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab521.addCell(pp);

			pp = new PdfPCell(new Phrase("Total", f3));
			pp.setBorder(0);
			tab521.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? "" + (emp_shr + empr_shr)
					: "     ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab521.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? "" + ac_2 : "     ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab521.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? "" + ac_10 : "     ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab521.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? "" + ac_21 : "     ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab521.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? "" + ac_22 : "     ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab521.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? ""
					+ (ac_1 + ac_2 + ac_10 + ac_21 + ac_22) : "     ", f4));
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab521.addCell(pp);

			doc.add(tab521);

			doc.add(createPara(
					flag ? "TOTAL AMOUNT IN WORDS: "
							+ originalNumToLetter
									.getInWords(""
											+ (int) (ac_1 + ac_2 + ac_10
													+ ac_21 + ac_22))
							: " TOTAL AMOUNT IN WORDS: ", f3,
					Element.ALIGN_LEFT));

			p.add(CONNECT);
			doc.add(p);

			PdfPTable tab6 = new PdfPTable(1);
			tab6.setWidthPercentage(new float[] { 100 },
					new Rectangle(100, 100));
			// PdfPCell cel = new PdfPCell();
			cell.setBorderWidth(0);
			cell.addElement(createMainInfoTab());
			tab6.addCell(cell);
			tab6.setSpacingBefore(5);
			doc.add(tab6);
			doc.add(createPara("( TO BE FILLED IN BY EMPLOYER)", f2,
					Element.ALIGN_CENTER));

			PdfPTable tab7 = new PdfPTable(3);
			tab7.setWidthPercentage(new float[] { 40, 40, 20 }, new Rectangle(
					100, 100));
			tab7.addCell(createCell("NAME OF THE BANK", f1, Element.ALIGN_LEFT,
					0));
			tab7.addCell(createCell("CHEQUE NO./D.D NO. :", f1,
					Element.ALIGN_LEFT, 0));
			tab7.addCell(createCell(" DATE :", f1, Element.ALIGN_LEFT, 0));
			tab7.setSpacingAfter(15);
			doc.add(tab7);

			PdfPTable tab8 = new PdfPTable(1);
			tab8.setWidthPercentage(new float[] { 100 },
					new Rectangle(100, 100));
			PdfPCell subcell2 = new PdfPCell();
			subcell2 = new PdfPCell(new Phrase(
					"FOR USE IN ASPECT OF PAST ACCUMULATION ONLY", f2));
			subcell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			subcell2.setBorderWidthLeft(0);
			subcell2.setBorderWidthRight(0);
			subcell2.setBorderWidthTop(1);
			subcell2.setBorderWidthBottom(1);
			tab8.addCell(subcell2);
			doc.add(tab8);

			PdfPTable tab9 = new PdfPTable(7);
			tab9.setWidthPercentage(new float[] { 28, 12, 12, 12, 12, 12, 12 },
					new Rectangle(100, 100));
			PdfPCell subcell3 = new PdfPCell();
			tab9.addCell(createCell("PART - 02", f2, Element.ALIGN_LEFT, 0));
			tab9.addCell(createCell("A/C NO. 1", f2, Element.ALIGN_CENTER, 0));
			tab9.addCell(createCell("A/C NO. 2", f2, Element.ALIGN_CENTER, 0));
			tab9.addCell(createCell("A/C NO. 10", f2, Element.ALIGN_CENTER, 0));
			tab9.addCell(createCell("A/C NO. 21", f2, Element.ALIGN_CENTER, 0));
			tab9.addCell(createCell("A/C NO. 22", f2, Element.ALIGN_CENTER, 0));
			tab9.addCell(createCell("TOTAL", f2, Element.ALIGN_RIGHT, 0));

			subcell3 = new PdfPCell(new Phrase("AMOUNT (IN RUPEES)", f2));
			subcell3.setColspan(7);
			subcell3.setBorderWidth(0);
			subcell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			tab9.addCell(subcell3);

			/*
			 * tab9.addCell(createCell("1 EER'S SHARE OF PAST ACCUM.", f3,
			 * Element.ALIGN_LEFT, 0));
			 */
			subcell3 = new PdfPCell(new Phrase("1 EER'S SHARE OF PAST ACCUM.",
					f3));
			subcell3.setBorderWidthBottom(0);
			subcell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab9.addCell(subcell3);
			tab9.addCell(createCell("", f1, Element.ALIGN_CENTER, 0.5f));
			tab9.addCell(createCell("", f1, Element.ALIGN_CENTER, 0.5f));
			tab9.addCell(createCell("", f1, Element.ALIGN_CENTER, 0.5f));
			tab9.addCell(createCell("", f1, Element.ALIGN_CENTER, 0.5f));
			tab9.addCell(createCell("", f1, Element.ALIGN_CENTER, 0.5f));
			tab9.addCell(createCell("", f1, Element.ALIGN_CENTER, 0.5f));

			subcell3 = new PdfPCell(new Phrase("2 ES'S SHARE OF PAST ACCUM.",
					f3));
			subcell3.setBorderWidthBottom(0);
			subcell3.setBorderWidthTop(0);
			subcell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab9.addCell(subcell3);
			tab9.addCell(createCell("", f1, Element.ALIGN_CENTER, 0.5f));
			tab9.addCell(createCell("", f1, Element.ALIGN_CENTER, 0.5f));
			tab9.addCell(createCell("", f1, Element.ALIGN_CENTER, 0.5f));
			tab9.addCell(createCell("", f1, Element.ALIGN_CENTER, 0.5f));
			tab9.addCell(createCell("", f1, Element.ALIGN_CENTER, 0.5f));
			tab9.addCell(createCell("", f1, Element.ALIGN_CENTER, 0.5f));

			subcell3 = new PdfPCell(new Phrase("3 INT. NO. SECURITIES.", f3));
			subcell3.setBorderWidthBottom(0);
			subcell3.setBorderWidthTop(0);
			subcell3.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab9.addCell(subcell3);
			tab9.addCell(createCell("", f1, Element.ALIGN_CENTER, 0.5f));
			tab9.addCell(createCell("", f1, Element.ALIGN_CENTER, 0.5f));
			tab9.addCell(createCell("", f1, Element.ALIGN_CENTER, 0.5f));
			tab9.addCell(createCell("", f1, Element.ALIGN_CENTER, 0.5f));
			tab9.addCell(createCell("", f1, Element.ALIGN_CENTER, 0.5f));
			tab9.addCell(createCell("", f1, Element.ALIGN_CENTER, 0.5f));

			subcell3 = new PdfPCell(new Phrase(" TOTAL.", f3));
			subcell3.setBorderWidthTop(0);
			subcell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tab9.addCell(subcell3);
			tab9.addCell(createCell("", f1, Element.ALIGN_CENTER, 0.5f));
			tab9.addCell(createCell("", f1, Element.ALIGN_CENTER, 0.5f));
			tab9.addCell(createCell("", f1, Element.ALIGN_CENTER, 0.5f));
			tab9.addCell(createCell("", f1, Element.ALIGN_CENTER, 0.5f));
			tab9.addCell(createCell("", f1, Element.ALIGN_CENTER, 0.5f));
			tab9.addCell(createCell("", f1, Element.ALIGN_CENTER, 0.5f));

			doc.add(tab9);

			con.close();
			doc.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static PdfPTable createMainInfoTab() {
		PdfPTable tab = new PdfPTable(2);
		Font f1 = new Font(Font.HELVETICA, 8);
		Connection con = ConnectionManager.getConnection();
		try {
			tab.setWidthPercentage(new float[] { 65, 35 }, new Rectangle(100,
					100));

			PdfPCell cell = new PdfPCell();
			cell.setBorder(0);
			// cell.addElement(createPara("NAME OF ESTABLISHMENT: HARSH CONSTRUCTION PVT. LTD.",
			// f1, Element.ALIGN_LEFT));
			cell.addElement(createPara(
					"Name & Address of the Establishment : ", f1,
					Element.ALIGN_LEFT));
			PdfPTable tab3 = new PdfPTable(2);
			tab3.setWidthPercentage(new float[] { 15, 50 }, new Rectangle(65,
					100));
			PdfPCell c1 = new PdfPCell();
			c1.setBorderWidth(0);
			// c1.addElement(createPara("Address: ", f1,Element.ALIGN_LEFT));
			cell.addElement(createPara("THE BUSINESS CO.OP BANK LTD, Nashik",
					new Font(Font.HELVETICA, 9, Font.BOLD), Element.ALIGN_LEFT));
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			tab3.addCell(c1);
			c1 = new PdfPCell();
			c1.setBorderWidth(0);
			// c1.addElement(createPara("name", new
			// Font(Font.HELVETICA,8,Font.BOLD), Element.ALIGN_LEFT));
			tab3.addCell(c1);
			cell.addElement(tab3);

			// cell.addElement(createPara("3. Father / Husband's Name: ", f1,
			// Element.ALIGN_LEFT));
			cell.addElement(createPara(
					"Flat No. A/7, Suryalok Nagari,",
					f1, Element.ALIGN_LEFT));
			cell.addElement(createPara(" Waiduwadi, Hadapsar, Pune - 411013", f1,
					Element.ALIGN_LEFT));
			cell.addElement(createPara(
					"Tel : 0253-2317143, Fax : 0253-2317621", f1,
					Element.ALIGN_LEFT));
			cell.addElement(createPara("Email : info@hcplnsk.com", f1,
					Element.ALIGN_LEFT));
			cell.addElement(createPara(
					"NAME OF THE DEPOSITER :_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _",
					f1, Element.ALIGN_LEFT));
			cell.addElement(createPara("SIGNATURE OF THE DEPOSITER :", f1,
					Element.ALIGN_LEFT));
			/*
			 * cell.addElement(createPara("NAME OF THE BANK ", f1,
			 * Element.ALIGN_LEFT));
			 */
			tab.addCell(cell);

			cell = new PdfPCell();
			cell.setBorder(0);
			cell.addElement(createPara("(For Banks use only)", f1,
					Element.ALIGN_LEFT));
			cell.addElement(createPara(
					"Amount Received Rs_____________________", f1,
					Element.ALIGN_LEFT));
			cell.addElement(createPara(
					"For Cheques /D.D only____________________", f1,
					Element.ALIGN_LEFT));
			cell.addElement(createPara(
					"Date of Realisation_______________________", f1,
					Element.ALIGN_LEFT));
			cell.addElement(createPara(
					"Date of Presentation______________________", f1,
					Element.ALIGN_LEFT));
			cell.addElement(createPara(
					"Branch Name___________________________", f1,
					Element.ALIGN_LEFT));
			cell.addElement(createPara(
					"Branch Code No_________________________", f1,
					Element.ALIGN_LEFT));

			tab.addCell(cell);

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tab;
	}

	public static void PfChallanNew(String rdt, String type, String filepath,String imagepath,String table) {
		// System.out.println("in pf cahallan DAO");
		RepoartBean repBean = new RepoartBean();
		Connection con = null, conn = null;
		Statement st = null;
		ResultSet rs = null;
		String table_name=table.equalsIgnoreCase("before")?"PAYTRAN":"PAYTRAN_STAGE";
		boolean flag = ("A").equalsIgnoreCase(type) ? true : false;
		// ResultSet trn = null;

		try {

			int total_subs = 0;
			// float total_Amt=0.0f;
			float emp_shr = 0;
			int ac_1 = 0, ac_2 = 0, ac_10 = 0, ac_21 = 0;
			int tot_wages = 0, empr_shr = 0, empr_eps = 0, empr_edli = 0, empr_epfadmin = 0, empr_edliadmin = 0, ac_22 = 0, total_wages_ac10=0;
			if (type.equalsIgnoreCase("A")) {

				conn = ConnectionManager.getConnection();
				st = conn.createStatement();
				rs = st.executeQuery("select Count(Distinct(empno)) from "+table_name+" where trncd=201 and cal_amt!=0 and trndt between '"
						+ ReportDAO.BOM(rdt)
						+ "' and '"
						+ ReportDAO.EOM(rdt)
						+ "' ");
				while (rs.next()) {
					total_subs = rs.getInt(1);
				}
				
				ResultSet rs2 = st
						.executeQuery("select trncd,Sum(cal_amt) from "+table_name+" where trncd in(136,137,201,231,232,233,234,235) and trndt between '"
								+ ReportDAO.BOM(rdt)
								+ "'"
								+ " and '"
								+ ReportDAO.EOM(rdt)
								+ "' and empno in(select DISTINCT(empno) from "+table_name+" where trncd=201 and trndt between '"
								+ ReportDAO.BOM(rdt)
								+ "' and '"
								+ ReportDAO.EOM(rdt) + "' AND NET_AMT<>0) group by TRNCD ");
				System.out.println("select trncd,Sum(cal_amt) from "+table_name+" where trncd in(136,137,201,231,232,233,234,235) and trndt between '"
						+ ReportDAO.BOM(rdt)
						+ "'"
						+ " and '"
						+ ReportDAO.EOM(rdt)
						+ "' and empno in(select DISTINCT(empno) from "+table_name+" where trncd=201 and trndt between '"
						+ ReportDAO.BOM(rdt)
						+ "' and '"
						+ ReportDAO.EOM(rdt) + "' AND NET_AMT<>0) group by TRNCD ");
				
				while (rs2.next()) {

					if (rs2.getInt("trncd") == 136) {
						tot_wages += (int) rs2.getFloat(2);
					}
					else if (rs2.getInt("trncd") == 137) {
						total_wages_ac10 += (int) rs2.getFloat(2);
					}else if (rs2.getInt("trncd") == 201) {
						emp_shr += (int) rs2.getFloat(2);
					} else if (rs2.getInt("trncd") == 231) {
						empr_shr += (int) rs2.getFloat(2);
					} else if (rs2.getInt("trncd") == 232) {
						empr_eps += (int) rs2.getFloat(2);
					} else if (rs2.getInt("trncd") == 233) {
						empr_edli += (int) rs2.getFloat(2);
					} else if (rs2.getInt("trncd") == 234) {
						empr_epfadmin += (int) rs2.getFloat(2);
					} else if (rs2.getInt("trncd") == 235) {
						empr_edliadmin += (int) rs2.getFloat(2);
					}

				}

				// Calculations for PF Challan

				// A/c No. 1 Employees Share 12% of Basic PF Employers Share
				// 3.67% of Basic EPF

				/*
				 * emp_shr=(int)(total_Amt/100*12);
				 * empr_shr=(int)(total_Amt/100*3.67);
				 */
				ac_1 = (int) (emp_shr + empr_shr);
				ac_2 = (int) (empr_epfadmin);
				ac_10 = (int) (empr_eps);
				ac_21 = (int) (empr_edli);
	//			ac_22 = (int) (empr_edliadmin<200?200:empr_edliadmin);
				ac_22 = (int) (empr_edliadmin<200?0:empr_edliadmin);
			}
			if (type.equalsIgnoreCase("B")) {

			}

			/*
			 * System.out.println("Amt= 6500"); System.out.println("Employee ="
			 * +emp_shr); System.out.println("Employer="+empr_shr);
			 * System.out.println("A/c no1 ="+ac_1);
			 * System.out.println("A/c no2 ="+ac_2);
			 * System.out.println("A/c no10 ="+ac_10);
			 * System.out.println("A/c no21 ="+ac_21);
			 * System.out.println("A/c no22 ="+ac_22);
			 */

			ReportDAO.OpenCon("", "", "", repBean);
			con = repBean.getCn();

			// SimpleDateFormat sdf = new
			// SimpleDateFormat("yyyy-MM-dd");//yyyy-MM-dd'T'HH:mm:ss
			// SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");

			Document doc = new Document();
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(
					filepath));
			doc.open();
			doc.setPageSize(PageSize.A4);
			Font f1 = new Font(Font.HELVETICA, 9);
			Font f2 = new Font(Font.HELVETICA, 9, Font.BOLD);
			Font f3 = new Font(Font.HELVETICA, 7, Font.BOLD);
			Font f4 = new Font(Font.HELVETICA, 7, Font.NORMAL);

			PdfPTable tab = new PdfPTable(2);
			tab.setWidthPercentage(new float[] { 85, 15 }, new Rectangle(100,
					100));
			tab.addCell(createCell(
					"               COMBINED CHALLAN OF A/C. NO. 1,2,10,21 & 22",
					f1, Element.ALIGN_CENTER, 0));
			tab.addCell(createCell("", f3, Element.ALIGN_JUSTIFIED, 0));
			tab.addCell(createCell("               (STATE BANK OF INDIA)", f2,
					Element.ALIGN_CENTER, 0));
			tab.addCell(createCell("", f3, Element.ALIGN_JUSTIFIED, 0));
			tab.addCell(createCell(
					"               EMPLOYEE'S PROVIDENT FUND ORGANISATION",
					f2, Element.ALIGN_CENTER, 0));
			tab.addCell(createCell("", f3, Element.ALIGN_JUSTIFIED, 0));
			// tab.addCell(createCell("               (USE SAPERATE CHALLAN FOR EACH MONTH)",
			// f2, Element.ALIGN_CENTER, 0));
			tab.addCell(createCell("", f3, Element.ALIGN_JUSTIFIED, 0));
			tab.setSpacingAfter(15);
			doc.add(tab);

			PdfPTable tab2 = new PdfPTable(2);
			tab2.setWidthPercentage(new float[] { 70, 30 }, new Rectangle(100,
					100));
			tab2.addCell(createCell(
					"ESTABLISHMENT CODE & NAME.:      THE BUSINESS CO.OP BANK LTD.",
					f4, Element.ALIGN_JUSTIFIED, 0));
			tab2.addCell(createCell("", f4, Element.ALIGN_JUSTIFIED, 0));
			tab2.addCell(createCell(
					"Addresss : Rajan Complex Arcade, Datta Mandir, Nashik Road, Nashik - 422101",
					f4, Element.ALIGN_JUSTIFIED, 0));
			tab2.addCell(createCell("", f4, Element.ALIGN_JUSTIFIED, 0));
			tab2.setSpacingAfter(15);
			doc.add(tab2);

			PdfPTable t1 = new PdfPTable(2);
			t1.setSpacingAfter(10);
			PdfPCell t1_c1 = new PdfPCell(new Phrase("", f4));
			t1_c1.setBorder(0);

			PdfPCell t1_c2 = new PdfPCell(new Phrase("", f4));
			t1_c2.setBorder(0);

			t1.setWidthPercentage(new float[] { 50, 50 }, new Rectangle(100,
					100));

			PdfPTable t1_st1 = new PdfPTable(3);
			// PdfCell ccc = null;
			t1_st1.setWidthPercentage(new float[] { 40, 30, 30 },
					new Rectangle(100, 100));

			PdfPCell cell = new PdfPCell(new Phrase("", f4));

			cell = new PdfPCell(new Phrase("DUES FOR THE MONTH : ", f4));
			cell.setBorder(0);
			cell.setRight(5);

			cell.setVerticalAlignment(Align.BOTTOM);
			t1_st1.addCell(cell);
			cell = new PdfPCell(new Phrase(""
					+ (flag ? "" + rdt.subSequence(3, 6) : "     ")
					+ (flag ? "-" + rdt.subSequence(7, 11) : "     "), f4));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setVerticalAlignment(Align.BOTTOM);
			t1_st1.addCell(cell);
			cell = new PdfPCell(new Phrase("", f4));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setVerticalAlignment(Align.BOTTOM);
			t1_st1.addCell(cell);

			t1_c1.addElement(t1_st1);

			t1.addCell(t1_c1);
			t1.addCell(t1_c2);

			doc.add(t1);

			PdfPTable tab4 = new PdfPTable(7);
			PdfPCell p1 = new PdfPCell(new Phrase("", f4));
			tab4.setWidthPercentage(new float[] { 25, 5, 20, 5, 20, 5, 20 },
					new Rectangle(100, 100));

			p1 = new PdfPCell(new Phrase("A/C Number ", f4));
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase("", f4));
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase("A/C No. 1  ", f4));
			p1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase("", f4));
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase("A/C NO. 10 ", f4));
			p1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase("", f4));
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase("A/C No. 21", f4));
			p1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			p1.setBorder(0);
			tab4.addCell(p1);

			p1 = new PdfPCell(new Phrase("Total No. of Subscribers:", f4));
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase("", f4));
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase(flag ? "" + total_subs : "     ", f4));
			p1.setBorder(0);
			p1.setBorder(0);
			p1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase("", f4));
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase(flag ? "" + total_subs : "     ", f4));
			p1.setBorder(0);
			p1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase("", f4));
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase(flag ? "" + total_subs : "     ", f4));
			p1.setBorder(0);
			p1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tab4.addCell(p1);

			p1 = new PdfPCell(new Phrase("Total Wages Due:", f4));
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase("", f4));
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase(flag ? "" + (float) (int) tot_wages
					: "     ", f4));
			p1.setBorder(0);
			p1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase("", f4));
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase(flag ? "" + (float) (int) total_wages_ac10
					: "     ", f4));
			p1.setBorder(0);
			p1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase("", f4));
			p1.setBorder(0);
			tab4.addCell(p1);
			p1 = new PdfPCell(new Phrase(flag ? "" + (float) (int) total_wages_ac10
					: "     ", f4));
			p1.setBorder(0);
			p1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tab4.addCell(p1);
			tab4.setSpacingAfter(10);
			doc.add(tab4);

			// DRAW HORIZONTAL LINE
			Chunk CONNECT = new Chunk(new LineSeparator(0.3f, 100, Color.BLACK,
					Element.ALIGN_LEFT, 1.0f));
			Paragraph p = new Paragraph("");

			p.add(CONNECT);
			doc.add(p);

			PdfPTable tab51 = new PdfPTable(8);
			tab51.setWidthPercentage(new float[] { 6, 19, 12, 12, 12, 12, 12,
					15 }, new Rectangle(100, 100));
			PdfPCell pp = new PdfPCell(new Phrase("", f4));

			pp = new PdfPCell(new Phrase("SR.NO", f3));
			pp.setHorizontalAlignment(Element.ALIGN_CENTER);
			pp.setBorder(0);
			tab51.addCell(pp);

			pp = new PdfPCell(new Phrase("PARTICULARS", f3));
			pp.setHorizontalAlignment(Element.ALIGN_CENTER);
			pp.setBorder(0);
			tab51.addCell(pp);

			pp = new PdfPCell(new Phrase("A/C NO. 1", f3));
			pp.setHorizontalAlignment(Element.ALIGN_CENTER);
			pp.setBorder(0);
			tab51.addCell(pp);

			pp = new PdfPCell(new Phrase("A/C NO. 2", f3));
			pp.setHorizontalAlignment(Element.ALIGN_CENTER);
			pp.setBorder(0);
			tab51.addCell(pp);
			pp = new PdfPCell(new Phrase("A/C NO. 10", f3));
			pp.setHorizontalAlignment(Element.ALIGN_CENTER);
			pp.setBorder(0);
			tab51.addCell(pp);

			pp = new PdfPCell(new Phrase("A/C NO. 21", f3));
			pp.setHorizontalAlignment(Element.ALIGN_CENTER);
			pp.setBorder(0);
			tab51.addCell(pp);

			pp = new PdfPCell(new Phrase("A/C NO. 22", f3));
			pp.setHorizontalAlignment(Element.ALIGN_CENTER);
			pp.setBorder(0);
			tab51.addCell(pp);

			pp = new PdfPCell(new Phrase("TOTAL", f3));
			pp.setHorizontalAlignment(Element.ALIGN_CENTER);
			pp.setBorder(0);
			tab51.addCell(pp);

			doc.add(tab51);

			p.add(CONNECT);
			doc.add(p);

			PdfPTable tab5 = new PdfPTable(8);

			tab5.setWidthPercentage(
					new float[] { 5, 20, 12, 12, 12, 12, 12, 15 },
					new Rectangle(100, 100));

			PdfPTable eg11 = new PdfPTable(1);
			PdfPCell eg12 = new PdfPCell(new Phrase("", f4));

			pp = new PdfPCell(new Phrase("1", f4));

			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("Employer's Share of Count.", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? "" + (float) (int) empr_shr
					: "     ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? "" + (float) (int) ac_10
					: "     ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? "" + (float) (int) ac_21
					: "     ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? ""
					+ ((float) (int) empr_shr + ac_10 + ac_21) : "     ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("2", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("Employee's Share of Count.", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? "" + (float) (int) emp_shr
					: "     ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase("    ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? "" + ((float) (int) emp_shr)
					: "     ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("3", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("Adm. Charges", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? "" + ((float) (int) ac_2)
					: "     ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);
			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? "" + ((float) (int) ac_22)
					: "     ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? ""
					+ ((float) (int) ac_2 + ac_22) : "     ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("4", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("Insp. Charges", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase("  ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase("  ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);
			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase("  ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("5", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("Penal Charges", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase("  ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase("  ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase("  ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase("  ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase("  ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase("  ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("6", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("Misc. Payments \n(INTEREST U/S 7Q)",
					f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase("  ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase("  ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase("  ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab5.addCell(pp);

			doc.add(tab5);

			p.add(CONNECT);
			doc.add(p);

			PdfPTable tab521 = new PdfPTable(8);
			tab521.setWidthPercentage(new float[] { 5, 20, 12, 12, 12, 12, 12,
					15 }, new Rectangle(100, 100));

			pp = new PdfPCell(new Phrase("", f4));
			pp.setBorder(0);
			tab521.addCell(pp);

			pp = new PdfPCell(new Phrase("Total", f3));
			pp.setBorder(0);
			tab521.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? ""
					+ ((float) (int) emp_shr + empr_shr) : "     ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab521.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? "" + (float) (int) ac_2
					: "     ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab521.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? "" + (float) (int) ac_10
					: "     ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab521.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? "" + (float) (int) ac_21
					: "     ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab521.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? "" + (float) (int) ac_22
					: "     ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab521.addCell(pp);

			pp = new PdfPCell();
			pp.setBorder(0);
			eg11 = new PdfPTable(1);
			eg12 = new PdfPCell(new Phrase(flag ? ""
					+ ((float) (int) ac_1 + ac_2 + ac_10 + ac_21 + ac_22)
					: "     ", f4));
			eg12.setBorder(0);
			eg12.setHorizontalAlignment(Element.ALIGN_RIGHT);
			eg11.addCell(eg12);
			pp.addElement(eg11);
			tab521.addCell(pp);

			doc.add(tab521);

			doc.add(createPara(
					flag ? "TOTAL AMOUNT IN WORDS: "
							+ originalNumToLetter
									.getInWords(""
											+ (int) (ac_1 + ac_2 + ac_10
													+ ac_21 + ac_22))
							: " TOTAL AMOUNT IN WORDS: ", f3,
					Element.ALIGN_LEFT));

			p.add(CONNECT);
			doc.add(p);

			PdfPTable tab6 = new PdfPTable(1);
			tab6.setWidthPercentage(new float[] { 100 },
					new Rectangle(100, 100));
			// PdfPCell cel = new PdfPCell();
			cell.setBorderWidth(0);
			cell.addElement(createMainInfoTab1());
			tab6.addCell(cell);
			tab6.setSpacingBefore(5);
			doc.add(tab6);

			con.close();
			doc.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static PdfPTable createMainInfoTab1() {
		PdfPTable tab = new PdfPTable(2);
		Font f1 = new Font(Font.HELVETICA, 8);
		Connection con = ConnectionManager.getConnection();
		try {
			tab.setWidthPercentage(new float[] { 45, 55 }, new Rectangle(100,
					100));

			PdfPCell cell = new PdfPCell();

			cell = new PdfPCell();
			cell.setBorder(0);
			cell.addElement(createPara("(FOR BANKS USE ONLY)", f1,
					Element.ALIGN_LEFT));
			cell.addElement(createPara(
					"Amount Received Rs_____________________", f1,
					Element.ALIGN_LEFT));
			cell.addElement(createPara(
					"For Cheques /D.D only____________________", f1,
					Element.ALIGN_LEFT));
			cell.addElement(createPara(
					"Date of Realisation_______________________", f1,
					Element.ALIGN_LEFT));
			cell.addElement(createPara(
					"Date of Presentation______________________", f1,
					Element.ALIGN_LEFT));
			cell.addElement(createPara(
					"Branch Name___________________________", f1,
					Element.ALIGN_LEFT));
			cell.addElement(createPara(
					"Branch Code No_________________________", f1,
					Element.ALIGN_LEFT));

			tab.addCell(cell);
			cell = new PdfPCell();
			cell.setBorder(0);
			cell.addElement(createPara(
					"FOR ESTABLISHMENT USE ONLY (To be manually filled by Employer)",
					f1, Element.ALIGN_LEFT));
			cell.addElement(createPara(
					"Cheques /D.D No.________________________________________",
					f1, Element.ALIGN_LEFT));
			cell.addElement(createPara(
					"Cheque/DD drawn bank & Branch :___________________________",
					f1, Element.ALIGN_LEFT));
			cell.addElement(createPara(
					"Date :__________________________________________________",
					f1, Element.ALIGN_LEFT));
			cell.addElement(createPara(
					"Name of the Depositer :____________________________________",
					f1, Element.ALIGN_LEFT));
			cell.addElement(createPara(
					"Date of Deposit :__________________________________________",
					f1, Element.ALIGN_LEFT));
			cell.addElement(createPara(
					"Mobile No. :_____________________________________________",
					f1, Element.ALIGN_LEFT));
			cell.addElement(createPara(
					"Signature of the Depositer :_________________________________",
					f1, Element.ALIGN_LEFT));
			cell.addElement(createPara("\n", f1, Element.ALIGN_LEFT));
			PdfPTable tab11 = new PdfPTable(1);
			tab11.setWidthPercentage(new float[] { 100 }, new Rectangle(100,
					100));
			PdfPCell c = new PdfPCell();
			c.addElement(createPara(
					"(KINDLY SUBMIT CHEQUE/DEMAND DRAFT & CHALLAN AT SBI COUNTER ONLY)",
					new Font(Font.HELVETICA, 6), Element.ALIGN_LEFT));
			tab11.addCell(c);
			cell.addElement(tab11);
			tab.addCell(cell);

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tab;
	}

}
