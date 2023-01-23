package payroll.Core;

import java.io.File;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

import payroll.DAO.ConnectionManager;
import payroll.DAO.HolidayMasterHandler;
import payroll.DAO.LookupHandler;
import payroll.Model.RepoartBean;

import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.util.Calendar;
import com.lowagie.text.Rectangle;

public class ExcelDAO {
	
	static String lable1="";
	
	//public static void newpayreg11(String PAYREGDT, String imgpath, String filepath, String type)
	//public static void newpayreg11(String PAYREGDT, String imgpath, String filepath, String type, String emptype)
	public static void newpayreg11(String PAYREGDT, String imgpath, String filepath, String type, String emptype, String branchCode)
	{

		System.out.println("in new pay regdao");
		
		System.out.println("newpayreg11 PAYREGDT: "+PAYREGDT);
		System.out.println("newpayreg11 type: "+type);
		System.out.println("newpayreg11 emptype: "+emptype);

		

		// this code is for constant property see constant.properties
		Properties prop = new Properties();
		try {

			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream stream = classLoader.getResourceAsStream("constant.properties");
			prop.load(stream);
		} catch (Exception e) {
			System.out.println("Error in constant properties Manager " + e);
		}

		RepoartBean repBean = new RepoartBean();

		Connection con = null;
		String BomDt = "";
		String EomDt = "";
		String StartDt = "";
		StartDt = "01-Dec-1900";
		int lastdat = 0;
		// String dt = PAYREGDT;
		String table_name = null;
		/*
		 * if(TName.equals("before") ) { table_name = "PAYTRAN";
		 * lable1="Before Finalize"; } else if(TName.equals("after") ) {
		 * table_name="YTDTRAN"; lable1="Final After Release"; } else {
		 * table_name="PAYTRAN_STAGE"; lable1="Pending for Release"; }
		 */
		// System.out.println(dt);
		lastdat = (int) Calculate.getDays(PAYREGDT);
		System.out.println("maxdt" + lastdat);
		BomDt = ReportDAO.BOM(PAYREGDT);
		// System.out.println("bomdt "+BomDt);
		EomDt = ReportDAO.EOM(PAYREGDT);
		// System.out.println("eomdt"+EomDt);

		String temp = PAYREGDT.substring(3);
		ResultSet emp = null;
		String EmpSql = "";
		String pBrcd1 = "";
		int tot_no_emp = 0;
		int br_tot_no_emp = 0;
		float tot_absents = 0.0f;
		float totmthsal1 = 0.0f;
		float totearning1 = 0.0f;
		float totearning2 = 0.0f;
		float totactualpay = 0.0f;
		float totmobded = 0.0f;
		float totadvanc = 0.0f;
		float totloan = 0.0f;
		float tottds = 0.0f;

		try {
			if (type.equalsIgnoreCase("G")) {
				ReportDAO.OpenCon("", "", "", repBean);
				con = repBean.getCn();
				Statement st = con.createStatement();

				FileOutputStream out1 = new FileOutputStream(new File(filepath));
				HSSFWorkbook hwb = new HSSFWorkbook();
				HSSFSheet sheet = hwb.createSheet("PayRegister");
				Calendar currentMonth = Calendar.getInstance();

				HSSFCellStyle my_style = hwb.createCellStyle();
				HSSFFont my_font = hwb.createFont();
				my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				my_style.setFont(my_font);

				HSSFRow rowtitle = sheet.createRow((short) 0);
				HSSFCell cell = rowtitle.createCell((short) 5);
				cell.setCellValue(prop.getProperty("companyName"));
				cell.setCellStyle(my_style);
				HSSFRow rowtitle1 = sheet.createRow((short) 1);
				HSSFCell cell1 = rowtitle1.createCell((short) 3);
				cell1.setCellValue(prop.getProperty("addressForReport"));
				cell1.setCellStyle(my_style);
				HSSFRow rowtitle2 = sheet.createRow((short) 2);
				HSSFCell cell2 = rowtitle2.createCell((short) 5);
				cell2.setCellValue(prop.getProperty("contactForReport"));
				cell2.setCellStyle(my_style);
				HSSFRow rowtitle31 = sheet.createRow((short) 3);
				cell2 = rowtitle31.createCell((short) 5);
				cell2.setCellValue(prop.getProperty("mailForReport"));
				cell2.setCellStyle(my_style);
				HSSFRow rowtitle3 = sheet.createRow((short) 4);
				cell2 = rowtitle3.createCell((short) 4);
				cell2.setCellValue("Salary Sheet For The Month Of :- " + PAYREGDT);
				cell2.setCellStyle(my_style);
				HSSFRow rowtitle4 = sheet.createRow((short) 5);
				rowtitle4.createCell((short) 0).setCellValue("");
				HSSFRow rowtitle5 = sheet.createRow((short) 6);
				rowtitle5.createCell((short) 0).setCellValue("");

				HSSFFont blueFont = hwb.createFont();
				blueFont.setColor(HSSFColor.BLUE.index);

				HSSFCellStyle style = hwb.createCellStyle();
				// style.setFont(blueFont);
				style.setFillForegroundColor(HSSFColor.BLUE.index);

				HSSFRow head = sheet.createRow((short) 7);
				head.createCell((short) 0).setCellValue("");
				HSSFRow heading = sheet.createRow((short) 8);
				HSSFCell cell3 = heading.createCell((short) 0);
				cell3.setCellValue("");
				cell3.setCellStyle(my_style);
				sheet.setColumnWidth((short) 0, (short) 3000);
				sheet.setColumnWidth((short) 1, (short) 7000);
				sheet.setColumnWidth((short) 4, (short) 4000);
				sheet.setColumnWidth((short) 5, (short) 3000);
				sheet.setColumnWidth((short) 6, (short) 4000);
				sheet.setColumnWidth((short) 7, (short) 3000);
				sheet.setColumnWidth((short) 8, (short) 4000);
				sheet.setColumnWidth((short) 9, (short) 3000);
				sheet.setColumnWidth((short) 10, (short) 3000);
				sheet.setColumnWidth((short) 11, (short) 3000);
				sheet.setColumnWidth((short) 12, (short) 3000);
				sheet.setColumnWidth((short) 13, (short) 3000);

				HSSFRow head1 = sheet.createRow((short) 9);
				head1.createCell((short) 0).setCellValue("");
				HSSFRow rowhead = sheet.createRow((short) 10);
				sheet.createFreezePane(0, 11, 0, 11);

				rowhead.createCell((short) 0).setCellValue("Emp Code.");
				rowhead.createCell((short) 1).setCellValue("Employee Name");
				rowhead.createCell((short) 4).setCellValue("CTC");
				rowhead.createCell((short) 5).setCellValue("LOP Days");
				rowhead.createCell((short) 6).setCellValue("Earning 1");
				rowhead.createCell((short) 7).setCellValue("Earning 2");
				rowhead.createCell((short) 8).setCellValue("Mobile Deduction");
				rowhead.createCell((short) 9).setCellValue("Advance Given");
				rowhead.createCell((short) 10).setCellValue("Loan");
				rowhead.createCell((short) 11).setCellValue("TDS");
				rowhead.createCell((short) 12).setCellValue("Net Pay");
				rowhead.createCell((short) 13).setCellValue("");

				// EmpSql = "select distinct p.empno,e.empcode from
				// "+table_name+" p right join EMPMAST e on e.EMPNO = p.EMPNO
				// where TRNDT BETWEEN '" + BomDt + "' AND '" + EomDt + "' order
				// by p.EMPNO";
				EmpSql = "select distinct p.empno,CONVERT(INT, e.empcode) as empcode,t.PRJ_SRNO,t.PRJ_CODE from paytran p right join EMPMAST e on e.EMPNO = p.EMPNO join EMPTRAN t on p.EMPNO = t.EMPNO where TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' and e.STATUS = 'A'"
						+ "and T.EFFDATE = (SELECT E2.EFFDATE FROM EMPTRAN E2 WHERE E2.EMPNO = e.EMPNO AND E2.EFFDATE <= '"
						+ ReportDAO.EOM(PAYREGDT)
						+ "' and E2.srno=(SELECT MAX(E2.SRNO) FROM EMPTRAN E2 WHERE E2.EMPNO = e.EMPNO AND E2.EFFDATE <= '"
						+ ReportDAO.EOM(PAYREGDT) + "')) order by t.PRJ_CODE,empcode";
				System.out.println(EmpSql);
				emp = st.executeQuery(EmpSql);

				List<Integer> results = new ArrayList<Integer>();

				while (emp.next()) {

					results.add(emp.getInt("empno"));
					// System.out.println(emp.getInt("empno"));
				}

				int i = 11;
				for (int empp : results) {

					EmpSql = "select empmast.EMPCODE ,rtrim(empmast.fname)+' '+rtrim(empmast.lname)as  name, doj,t.PRJ_CODE,t.PRJ_SRNO, "
							+ "(select inp_AMT from paytran where trncd = 199 and TRNDT BETWEEN '" + BomDt + "'and '"
							+ EomDt + "' and empno=" + empp + ") ctc, "
							+ "(select cal_amt from paytran where trncd = 301 and TRNDT BETWEEN '" + BomDt + "'and '"
							+ EomDt + "' and empno=" + empp + ") abs_cnt,"
							+ "(select net_amt as earning1 from paytran where trncd = 130 and TRNDT BETWEEN '" + BomDt
							+ "'and '" + EomDt + "' and empno=" + empp + ") earning1,"
							+ "(select net_amt as earning2 from paytran where trncd = 131 and TRNDT BETWEEN '" + BomDt
							+ "'and '" + EomDt + "' and empno=" + empp + ")earning2,"
							+ "(select net_amt as mobded from paytran where trncd = 223 and TRNDT BETWEEN '" + BomDt
							+ "'and '" + EomDt + "' and empno=" + empp + ")mobded,"
							+ "(select net_amt as added from paytran where trncd = 225 and TRNDT BETWEEN '" + BomDt
							+ "'and '" + EomDt + "' and empno=" + empp + ")added,"
							+ "(select net_amt as loan from paytran where trncd = 227 and TRNDT BETWEEN '" + BomDt
							+ "'and '" + EomDt + "' and empno=" + empp + ")loan,"
							+ "(select net_amt as tds from paytran where trncd = 228 and TRNDT BETWEEN '" + BomDt
							+ "'and '" + EomDt + "' and empno=" + empp + ")tds,"
							+ "(select net_amt as payable from paytran where trncd = 999 and TRNDT BETWEEN '" + BomDt
							+ "'and '" + EomDt + "' and empno=" + empp + ")payable "
							+ "from empmast,EMPTRAN t  where empmast.empno=" + empp + " and t.empno = " + empp + " " +

							"  UNION  "
							+ "select empmast.EMPCODE ,rtrim(empmast.fname)+' '+rtrim(empmast.lname)as  name, doj,t.PRJ_CODE,t.PRJ_SRNO, "
							+ "(select inp_AMT from paytran_stage where trncd = 199 and TRNDT BETWEEN '" + BomDt
							+ "'and '" + EomDt + "' and empno=" + empp + ") ctc, "
							+ "(select cal_amt from paytran_stage where trncd = 301 and TRNDT BETWEEN '" + BomDt
							+ "'and '" + EomDt + "' and empno=" + empp + ") abs_cnt,"
							+ "(select net_amt as earning1 from paytran_stage where trncd = 130 and TRNDT BETWEEN '"
							+ BomDt + "'and '" + EomDt + "' and empno=" + empp + ") earning1,"
							+ "(select net_amt as earning2 from paytran_stage where trncd = 131 and TRNDT BETWEEN '"
							+ BomDt + "'and '" + EomDt + "' and empno=" + empp + ")earning2,"
							+ "(select net_amt as mobded from paytran_stage where trncd = 223 and TRNDT BETWEEN '"
							+ BomDt + "'and '" + EomDt + "' and empno=" + empp + ")mobded,"
							+ "(select net_amt as added from paytran_stage where trncd = 225 and TRNDT BETWEEN '"
							+ BomDt + "'and '" + EomDt + "' and empno=" + empp + ")added,"
							+ "(select net_amt as loan from paytran_stage where trncd = 227 and TRNDT BETWEEN '" + BomDt
							+ "'and '" + EomDt + "' and empno=" + empp + ")loan,"
							+ "(select net_amt as tds from paytran_stage where trncd = 228 and TRNDT BETWEEN '" + BomDt
							+ "'and '" + EomDt + "' and empno=" + empp + ")tds,"
							+ "(select net_amt as payable from paytran_stage where trncd = 999 and TRNDT BETWEEN '"
							+ BomDt + "'and '" + EomDt + "' and empno=" + empp + ")payable "
							+ "from empmast,EMPTRAN t  where empmast.empno=" + empp + " and t.empno = " + empp;

					// System.out.println(EmpSql);
					// System.out.println(empp);
					emp = st.executeQuery(EmpSql);

					while (emp.next()) {

						HSSFRow row = sheet.createRow((short) i++);
						row.createCell((short) 0).setCellValue("" + emp.getString("empcode"));
						row.createCell((short) 1).setCellValue("");

						row.createCell((short) 1).setCellValue("" + emp.getString("name"));

						float mthsal = 0.0f;
						float absent = 0.0f;

						/*
						 * EmpSql="select inp_AMT from "
						 * +table_name+" where trncd = 199 and TRNDT BETWEEN '"
						 * +BomDt+"'and '"+EomDt+"' and empno="+empp; emp =
						 * st.executeQuery(EmpSql);
						 * //System.out.println(EmpSql); while(emp.next()){
						 * mthsal = emp.getFloat("inp_AMT");
						 * 
						 * //System.out.println("mth salary "+emp.getString(
						 * "inp_AMT")); }
						 */
						mthsal = emp.getFloat("ctc");
						if (mthsal == 0) {
							mthsal = 0;

							// mthsal = 0.0f;
						} else {

							totmthsal1 = totmthsal1 + mthsal;
						}
						// ctc
						row.createCell((short) 4).setCellValue(mthsal);

						/*
						 * EmpSql = "select cal_amt from "
						 * +table_name+" where trncd = 301 and TRNDT BETWEEN '"
						 * +BomDt+"'and '"+EomDt+"' and empno="+empp; emp =
						 * st.executeQuery(EmpSql); if(emp.next()){
						 * 
						 * absent = emp.getFloat("cal_AMT"); }
						 */
						absent = emp.getFloat("abs_cnt");
						// L.O.P days
						row.createCell((short) 5).setCellValue(absent);
						tot_absents += absent;
						float ear1 = 0.0f;
						/*
						 * EmpSql="select net_amt as earning1 from "
						 * +table_name+" where trncd = 130 and TRNDT BETWEEN '"
						 * +BomDt+"'and '"+EomDt+"' and empno="+empp;
						 * //System.out.println(EmpSql); emp =
						 * st.executeQuery(EmpSql); while(emp.next()){ ear1 =
						 * emp.getFloat("earning1");
						 * 
						 * //System.out.println("earning1"+emp.getString(
						 * "earning1")); }
						 */
						ear1 = emp.getFloat("earning1");
						if (ear1 == 0.0) {
							ear1 = 0;

						} else {
							// earning1 = ear1;
							totearning1 = totearning1 + ear1;
						}

						// earning 1
						row.createCell((short) 6).setCellValue(ear1);

						float ear2 = 0.0f;
						/*
						 * EmpSql="select net_amt as earning2 from "
						 * +table_name+" where trncd = 131 and TRNDT BETWEEN '"
						 * +BomDt+"'and '"+EomDt+"' and empno="+empp;
						 * //System.out.println(EmpSql); emp =
						 * st.executeQuery(EmpSql); while(emp.next()){ ear2 =
						 * emp.getFloat("earning2");
						 * 
						 * //System.out.println("earning2"+emp.getString(
						 * "earning2")); }
						 */
						ear2 = emp.getFloat("earning2");
						if (ear2 == 0.0) {
							ear2 = 0;

						} else {

							totearning2 = totearning2 + ear2;
						}

						// earning 2

						row.createCell((short) 7).setCellValue(ear2);

						float mobded = 0.0f;
						/*
						 * EmpSql="select net_amt as mobded from "
						 * +table_name+" where trncd = 223 and TRNDT BETWEEN '"
						 * +BomDt+"'and '"+EomDt+"' and empno="+empp;
						 * //System.out.println(EmpSql); emp =
						 * st.executeQuery(EmpSql); while(emp.next()){ mobded =
						 * emp.getFloat("mobded");
						 * 
						 * //System.out.println("mobded"+emp.getString("mobded")
						 * ); }
						 */
						mobded = emp.getFloat("mobded");
						if (mobded == 0.0) {
							mobded = 0;

						} else {

							totmobded = totmobded + mobded;
						}

						// mobile ded

						row.createCell((short) 8).setCellValue(mobded);

						float advanc = 0.0f;
						/*
						 * EmpSql="select net_amt as added from "
						 * +table_name+" where trncd = 225 and TRNDT BETWEEN '"
						 * +BomDt+"'and '"+EomDt+"' and empno="+empp;
						 * //System.out.println(EmpSql); emp =
						 * st.executeQuery(EmpSql); while(emp.next()){ advanc =
						 * emp.getFloat("added");
						 * 
						 * //System.out.println("advance"+emp.getString("added")
						 * ); }
						 */
						advanc = emp.getFloat("added");
						if (advanc == 0.0) {
							advanc = 0;

						} else {

							totadvanc = totadvanc + advanc;
						}

						// advance given

						row.createCell((short) 9).setCellValue(advanc);

						float loan = 0.0f;
						/*
						 * EmpSql="select net_amt as loan from "
						 * +table_name+" where trncd = 227 and TRNDT BETWEEN '"
						 * +BomDt+"'and '"+EomDt+"' and empno="+empp;
						 * //System.out.println(EmpSql); emp =
						 * st.executeQuery(EmpSql); while(emp.next()){ loan =
						 * emp.getFloat("loan");
						 * 
						 * //System.out.println("loan "+emp.getString("loan"));
						 * }
						 */
						loan = emp.getFloat("loan");
						if (loan == 0.0) {
							loan = 0;

						} else {

							totloan = totloan + loan;
						}

						// loan

						row.createCell((short) 10).setCellValue(loan);

						float tds = 0.0f;
						/*
						 * EmpSql="select net_amt as tds from "
						 * +table_name+" where trncd = 228 and TRNDT BETWEEN '"
						 * +BomDt+"'and '"+EomDt+"' and empno="+empp;
						 * //System.out.println(EmpSql); emp =
						 * st.executeQuery(EmpSql); while(emp.next()){ tds =
						 * emp.getFloat("tds");
						 * 
						 * //System.out.println("tds "+emp.getString("tds")); }
						 */
						tds = emp.getFloat("tds");
						if (tds == 0.0) {
							tds = 0;

						} else {

							tottds = tottds + tds;
						}

						// tds

						row.createCell((short) 11).setCellValue(tds);

						int payable = 0;
						/*
						 * EmpSql="select net_amt as payable from "
						 * +table_name+" where trncd = 999 and TRNDT BETWEEN '"
						 * +BomDt+"'and '"+EomDt+"' and empno="+empp; emp =
						 * st.executeQuery(EmpSql);
						 * //System.out.println(EmpSql); while(emp.next()){
						 * payable = emp.getInt("payable");
						 * 
						 * //System.out.println("payable "+emp.getString(
						 * "payable")); }
						 */
						payable = emp.getInt("payable");
						if (payable == 0) {
							payable = 0;
						} else {

							totactualpay = totactualpay + payable;

						}
						// actual pay

						row.createCell((short) 12).setCellValue(payable);

					}

				}

				NumberFormat format = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));
				String totpay = format.format(totmthsal1).substring(4);
				String eard1 = format.format(totearning1).substring(4);
				String eard2 = format.format(totearning2).substring(4);
				String totnetpay = format.format(totactualpay).substring(4);
				String tomobded = format.format(totmobded).substring(4);
				String toadv = format.format(totadvanc).substring(4);
				String toloan = format.format(totloan).substring(4);
				String totds = format.format(tottds).substring(4);

				rowhead = sheet.createRow((short) i++);
				rowhead.createCell((short) 0).setCellValue("");
				rowhead.createCell((short) 1).setCellValue("");
				rowhead.createCell((short) 2).setCellValue("TOTAL PAY");
				rowhead.createCell((short) 4).setCellValue(totpay);
				rowhead.createCell((short) 5).setCellValue(tot_absents);
				rowhead.createCell((short) 6).setCellValue(eard1);
				rowhead.createCell((short) 7).setCellValue(eard2);
				rowhead.createCell((short) 8).setCellValue(tomobded);
				rowhead.createCell((short) 9).setCellValue(toadv);
				rowhead.createCell((short) 10).setCellValue(toloan);
				rowhead.createCell((short) 11).setCellValue(totds);
				rowhead.createCell((short) 12).setCellValue(totnetpay);
				rowhead.createCell((short) 14).setCellValue("");

				DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

				Calendar calobj = Calendar.getInstance();
				HSSFRow row = sheet.createRow((short) i);
				row.createCell((short) 0).setCellValue(" ");
				row = sheet.createRow((short) i + 1);
				row.createCell((short) 0).setCellValue("Report Date And Time " + df.format(calobj.getTime()));

				hwb.write(out1);
				out1.close();
				/*
				 * final int BUFSIZE = 4096; File file = new File(filepath); int
				 * length = 0;
				 */

				System.out.println("Your excel file has been generated!");

				st.close();
				con.close();
			}

			else if (type.equalsIgnoreCase("I")) {

				ReportDAO.OpenCon("", "", "", repBean);
				con = repBean.getCn();
				Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

				System.out.println("in income head");
				FileOutputStream out1 = new FileOutputStream(new File(filepath));

				HSSFWorkbook hwb = new HSSFWorkbook();
				HSSFSheet sheet = hwb.createSheet("PayRegister");
				Calendar currentMonth = Calendar.getInstance();

				HSSFCellStyle my_style = hwb.createCellStyle();
				HSSFCellStyle my_style1 = hwb.createCellStyle();

				HSSFFont my_font = hwb.createFont();
				my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				my_style.setFont(my_font);

				HSSFRow rowtitle = sheet.createRow((short) 0);
				HSSFCell cell = rowtitle.createCell((short) 8);
				cell.setCellValue(prop.getProperty("companyName"));
				cell.setCellStyle(my_style);
				HSSFRow rowtitle1 = sheet.createRow((short) 1);
				HSSFCell cell1 = rowtitle1.createCell((short) 6);
				cell1.setCellValue(prop.getProperty("addressForReport"));
				cell1.setCellStyle(my_style);
				HSSFRow rowtitle2 = sheet.createRow((short) 2);
				HSSFCell cell2 = rowtitle2.createCell((short) 8);
				cell2.setCellValue(prop.getProperty("contactForReport"));
				cell2.setCellStyle(my_style);
				HSSFRow rowtitle31 = sheet.createRow((short) 3);
				cell2 = rowtitle31.createCell((short) 8);
				cell2.setCellValue(prop.getProperty("mailForReport"));
				cell2.setCellStyle(my_style);
				HSSFRow rowtitle3 = sheet.createRow((short) 4);
				cell2 = rowtitle3.createCell((short) 7);
				cell2.setCellValue("Salary Sheet For The Month Of :- " + PAYREGDT);
				cell2.setCellStyle(my_style);
				HSSFRow rowtitle4 = sheet.createRow((short) 5);
				rowtitle4.createCell((short) 0).setCellValue("");
				HSSFRow rowtitle5 = sheet.createRow((short) 6);
				rowtitle5.createCell((short) 0).setCellValue("");

				HSSFFont blueFont = hwb.createFont();
				blueFont.setColor(HSSFColor.BLUE.index);

				HSSFCellStyle style = hwb.createCellStyle();
				// style.setFont(blueFont);
				style.setFillForegroundColor(HSSFColor.BLUE.index);

				/*
				 * if(TName.equalsIgnoreCase("after")) { HSSFRow headin =
				 * sheet.createRow((short)8); HSSFCell cell4 = headin
				 * .createCell((short) 6); cell4.
				 * setCellValue("Salary Sheet For The Income Heads for the Month Of :- "
				 * +PAYREGDT+" "); cell4.setCellStyle(my_style);
				 * 
				 * } else if(TName.equalsIgnoreCase("before")) { HSSFRow headin
				 * = sheet.createRow((short)8); HSSFCell cell4 = headin
				 * .createCell((short) 6); cell4.
				 * setCellValue("Salary Sheet For The Income Heads for the Month Of :-  "
				 * +PAYREGDT+" (Before Finalize) ");
				 * cell4.setCellStyle(my_style);
				 * 
				 * } else { HSSFRow headin = sheet.createRow((short)8); HSSFCell
				 * cell4 = headin .createCell((short) 6); cell4.
				 * setCellValue("Salary Sheet For The Income Heads for the Month Of :- "
				 * +PAYREGDT+" (Pending for Release)");
				 * cell4.setCellStyle(my_style);
				 * 
				 * }
				 */

				int i = 10;
				float basic_total = 0.0f;
				float lstbasic_total = 0.0f;
				float currda_total = 0.0f;
				float lstcurrda_total = 0.0f;
				float hra_total = 0.0f;
				float lsthra_total = 0.0f;
				float medical_total = 0.0f;
				float lstmedical_total = 0.0f;
				float edu_total = 0.0f;
				float lstedu_total = 0.0f;
				float splall_total = 0.0f;
				float lstsplall_total = 0.0f;
				float convall_total = 0.0f;
				float lstconvall_total = 0.0f;
				float washingall_total = 0.0f;
				float lstwashingall_total = 0.0f;
				float bonus_total = 0.0f;
				float lstbonus_total = 0.0f;
				float minins_total = 0.0f;
				float lstminins_total = 0.0f;
				float addlss_total = 0.0f;
				float lstaddlss_total = 0.0f;
				float col_total = 0.0f;
				float lstcol_total = 0.0f;
				float special_total = 0.0f;
				float lstspecial_total = 0.0f;
				float addinc_total = 0.0f;
				float lstaddinc_total = 0.0f;
				float totinc_total = 0.0f;
				float lsttotinc_total = 0.0f;
				float totded_total = 0.0f;
				float lsttotded_total = 0.0f;
				float netpay_total = 0.0f;
				float lstnetpay_total = 0.0f;
				float eepf_total = 0.0f;

				EmpSql = "select CONVERT(INT, empmast.empcode) as EMPCODE ,rtrim(empmast.fname)+' '+rtrim(empmast.mname)+' '+rtrim(empmast.lname)as name,empmast.empno,t.PRJ_SRNO,"
						+ "p1.NET_AMT as basic,p2.NET_AMT as payable,p3.NET_AMT as hra,p4.NET_AMT as medical,"
						+ "p5.NET_AMT as education,p6.NET_AMT as convall,p7.NET_AMT as min_ins," + "p10.NET_AMT as col,"
						+ "(select net_amt from  PAYTRAN where TRNCD = 127 and trndt BETWEEN '" + BomDt + "'and '"
						+ EomDt + "' and EMPNO = empmast.EMPNO) as addless," +
						// "(select net_amt from PAYTRAN where TRNCD = 129 and
						// trndt BETWEEN '"+BomDt+"'and '"+EomDt+"' and EMPNO =
						// empmast.EMPNO) as special, " +
						"(select net_amt from  PAYTRAN where TRNCD = 107 and trndt BETWEEN '" + BomDt + "'and '" + EomDt
						+ "' and EMPNO = empmast.EMPNO) as splall,"
						+ "(select net_amt from  PAYTRAN where TRNCD = 102 and trndt BETWEEN '" + BomDt + "'and '"
						+ EomDt + "' and EMPNO = empmast.EMPNO) as curda,"
						+ "(select net_amt from  PAYTRAN where TRNCD = 115 and trndt BETWEEN '" + BomDt + "'and '"
						+ EomDt + "' and EMPNO = empmast.EMPNO) as washall,"
						+ "(select net_amt from  PAYTRAN where TRNCD = 135 and trndt BETWEEN '" + BomDt + "'and '"
						+ EomDt + "' and EMPNO = empmast.EMPNO) as bonusall,"
						+ "(select SUM(net_amt) from  PAYTRAN where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198) and TRNCD not in(101,103,104,105,108,126,127,128,107,102,115,135) and trndt BETWEEN '"
						+ BomDt + "'and '" + EomDt + "' and EMPNO = empmast.EMPNO) as addinc,"
						+ "(select SUM(net_amt) from  PAYTRAN where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 200 and 299) and trndt BETWEEN '"
						+ BomDt + "'and '" + EomDt + "' and EMPNO = empmast.EMPNO) as totded, "
						+ "(select SUM(net_amt) from  PAYTRAN where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198) and trndt BETWEEN '"
						+ BomDt + "'and '" + EomDt + "' and EMPNO = empmast.EMPNO) as totear "
						+ "from EMPMAST empmast join "
						+ " PAYTRAN p1 on empmast.EMPNO = p1.EMPNO join  PAYTRAN p2 on empmast.EMPNO = p2.EMPNO "
						+ "join  PAYTRAN p3 on empmast.EMPNO = p3.EMPNO "
						+ "join  PAYTRAN p4 on empmast.EMPNO = p4.EMPNO "
						+ "join  PAYTRAN p5 on empmast.EMPNO = p5.EMPNO "
						+ "join  PAYTRAN p6 on empmast.EMPNO = p6.EMPNO "
						+ "join  PAYTRAN p7 on empmast.EMPNO = p7.EMPNO "
						+ "join  PAYTRAN p10 on empmast.EMPNO = p10.EMPNO "
						+ "join EMPTRAN t on t.EMPNO = empmast.EMPNO where " + "p1.TRNCD = 101 and p1.TRNDT BETWEEN '"
						+ BomDt + "'and '" + EomDt + "' " + "and p2.TRNCD = 999 and p2.TRNDT BETWEEN '" + BomDt
						+ "'and '" + EomDt + "' " + "and p3.TRNCD = 103 and p3.TRNDT BETWEEN '" + BomDt + "'and '"
						+ EomDt + "' " + "and p4.TRNCD = 104 and p4.TRNDT BETWEEN '" + BomDt + "'and '" + EomDt + "' "
						+ "and p5.TRNCD = 105 and p5.TRNDT BETWEEN '" + BomDt + "'and '" + EomDt + "' "
						+ "and p6.TRNCD = 108 and p6.TRNDT BETWEEN '" + BomDt + "'and '" + EomDt + "' "
						+ "and p7.TRNCD = 126 and p7.TRNDT BETWEEN '" + BomDt + "'and '" + EomDt + "' "
						+ "and p10.TRNCD = 128 and p10.TRNDT BETWEEN '" + BomDt + "'and '" + EomDt + "' " +
						// "and empmast.STATUS = 'A'" +
						" and T.srno =(SELECT MAX(E2.SRNO) FROM EMPTRAN E2 WHERE E2.EMPNO = empmast.EMPNO AND E2.EFFDATE <= '"
						+ ReportDAO.EOM(PAYREGDT) + "') " +

						" UNION  select CONVERT(INT, empmast.empcode) as EMPCODE ,rtrim(empmast.fname)+' '+rtrim(empmast.mname)+' '+rtrim(empmast.lname)as name,empmast.empno,t.PRJ_SRNO,"
						+ "p1.NET_AMT as basic,p2.NET_AMT as payable,p3.NET_AMT as hra,p4.NET_AMT as medical,"
						+ "p5.NET_AMT as education,p6.NET_AMT as convall,p7.NET_AMT as min_ins," + "p10.NET_AMT as col,"
						+ "(select net_amt from   paytran_stage  where TRNCD = 127 and trndt BETWEEN '" + BomDt
						+ "'and '" + EomDt + "' and EMPNO = empmast.EMPNO) as addless," +
						// "(select net_amt from paytran_stage where TRNCD = 129
						// and trndt BETWEEN '"+BomDt+"'and '"+EomDt+"' and
						// EMPNO = empmast.EMPNO) as special, " +
						"(select net_amt from   paytran_stage  where TRNCD = 107 and trndt BETWEEN '" + BomDt + "'and '"
						+ EomDt + "' and EMPNO = empmast.EMPNO) as splall,"
						+ "(select net_amt from   paytran_stage  where TRNCD = 102 and trndt BETWEEN '" + BomDt
						+ "'and '" + EomDt + "' and EMPNO = empmast.EMPNO) as curda,"
						+ "(select net_amt from   paytran_stage  where TRNCD = 115 and trndt BETWEEN '" + BomDt
						+ "'and '" + EomDt + "' and EMPNO = empmast.EMPNO) as washall,"
						+ "(select net_amt from   paytran_stage  where TRNCD = 135 and trndt BETWEEN '" + BomDt
						+ "'and '" + EomDt + "' and EMPNO = empmast.EMPNO) as bonusall,"
						+ "(select SUM(net_amt) from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198) and TRNCD not in(101,103,104,105,108,126,127,128,107,102,115,135) and trndt BETWEEN '"
						+ BomDt + "'and '" + EomDt + "' and EMPNO = empmast.EMPNO) as addinc,"
						+ "(select SUM(net_amt) from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 200 and 299) and trndt BETWEEN '"
						+ BomDt + "'and '" + EomDt + "' and EMPNO = empmast.EMPNO) as totded, "
						+ "(select SUM(net_amt) from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198) and trndt BETWEEN '"
						+ BomDt + "'and '" + EomDt + "' and EMPNO = empmast.EMPNO) as totear "
						+ "from EMPMAST empmast join "
						+ "  paytran_stage  p1 on empmast.EMPNO = p1.EMPNO join   paytran_stage  p2 on empmast.EMPNO = p2.EMPNO "
						+ "join   paytran_stage  p3 on empmast.EMPNO = p3.EMPNO "
						+ "join   paytran_stage  p4 on empmast.EMPNO = p4.EMPNO "
						+ "join   paytran_stage  p5 on empmast.EMPNO = p5.EMPNO "
						+ "join   paytran_stage  p6 on empmast.EMPNO = p6.EMPNO "
						+ "join   paytran_stage  p7 on empmast.EMPNO = p7.EMPNO "
						+ "join   paytran_stage  p10 on empmast.EMPNO = p10.EMPNO "
						+ "join EMPTRAN t on t.EMPNO = empmast.EMPNO where " + "p1.TRNCD = 101 and p1.TRNDT BETWEEN '"
						+ BomDt + "'and '" + EomDt + "' " + "and p2.TRNCD = 999 and p2.TRNDT BETWEEN '" + BomDt
						+ "'and '" + EomDt + "' " + "and p3.TRNCD = 103 and p3.TRNDT BETWEEN '" + BomDt + "'and '"
						+ EomDt + "' " + "and p4.TRNCD = 104 and p4.TRNDT BETWEEN '" + BomDt + "'and '" + EomDt + "' "
						+ "and p5.TRNCD = 105 and p5.TRNDT BETWEEN '" + BomDt + "'and '" + EomDt + "' "
						+ "and p6.TRNCD = 108 and p6.TRNDT BETWEEN '" + BomDt + "'and '" + EomDt + "' "
						+ "and p7.TRNCD = 126 and p7.TRNDT BETWEEN '" + BomDt + "'and '" + EomDt + "' "
						+ "and p10.TRNCD = 128 and p10.TRNDT BETWEEN '" + BomDt + "'and '" + EomDt + "' " +
						// "and empmast.STATUS = 'A'" +
						" and T.srno =(SELECT MAX(E2.SRNO) FROM EMPTRAN E2 WHERE E2.EMPNO = empmast.EMPNO AND E2.EFFDATE <= '"
						+ ReportDAO.EOM(PAYREGDT) + "') " +

						"order by t.PRJ_SRNO,EMPCODE";
				System.out.println(EmpSql);
				ResultSet rs = st.executeQuery(EmpSql);

				while (rs.next()) {

					String prj_name = null;
					String prj_code = null;
					Connection conn = ConnectionManager.getConnectionTech();
					Statement stmt = conn.createStatement();
					String prjquery = "select Site_Name,Project_Code from Project_Sites where SITE_ID = '"
							+ rs.getString("PRJ_SRNO") + "'";
					// System.out.println(prjquery);
					ResultSet prj = stmt.executeQuery(prjquery);
					if (prj.next()) {
						prj_name = prj.getString("Site_Name");
						prj_code = prj.getString("Project_Code");
					}
					pBrcd1 = rs.getString("PRJ_SRNO");
					br_tot_no_emp = 0;

					HSSFRow head1 = sheet.createRow((short) i++);
					HSSFCell cell4 = head1.createCell((short) 0);
					cell4.setCellValue(
							" Employee's Salary List For Project Site : " + prj_name + " (" + prj_code + ")");
					cell4.setCellStyle(my_style);

					sheet.setColumnWidth((short) 0, (short) 3000);
					sheet.setColumnWidth((short) 1, (short) 7000);
					sheet.setColumnWidth((short) 4, (short) 4000);
					sheet.setColumnWidth((short) 5, (short) 4000);
					sheet.setColumnWidth((short) 6, (short) 4000);
					sheet.setColumnWidth((short) 7, (short) 4000);
					sheet.setColumnWidth((short) 8, (short) 4000);
					sheet.setColumnWidth((short) 9, (short) 4000);
					sheet.setColumnWidth((short) 10, (short) 4000);
					sheet.setColumnWidth((short) 11, (short) 4000);
					sheet.setColumnWidth((short) 12, (short) 4000);
					sheet.setColumnWidth((short) 13, (short) 4000);
					sheet.setColumnWidth((short) 14, (short) 4000);
					sheet.setColumnWidth((short) 15, (short) 4000);
					sheet.setColumnWidth((short) 16, (short) 4000);
					sheet.setColumnWidth((short) 17, (short) 4000);
					sheet.setColumnWidth((short) 18, (short) 4000);
					sheet.setColumnWidth((short) 19, (short) 4000);

					prj.close();
					stmt.close();

					/*
					 * my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
					 * my_style1.setFont(my_font);
					 */

					head1 = sheet.createRow((short) i++);
					cell4 = head1.createCell((short) 0);
					cell4.setCellValue("EMP CODE");
					cell4.setCellStyle(my_style1);
					cell4 = head1.createCell((short) 1);
					cell4.setCellValue("EMPNAME");
					cell4.setCellStyle(my_style1);
					cell4 = head1.createCell((short) 4);
					cell4.setCellValue("Basic");
					cell4.setCellStyle(my_style1);
					cell4 = head1.createCell((short) 5);
					cell4.setCellValue("Current D.A");
					cell4.setCellStyle(my_style1);
					cell4 = head1.createCell((short) 6);
					cell4.setCellValue("H.R.A");
					cell4.setCellStyle(my_style1);
					cell4 = head1.createCell((short) 7);
					cell4.setCellValue("Medical");
					cell4.setCellStyle(my_style1);
					cell4 = head1.createCell((short) 8);
					cell4.setCellValue("Education");
					cell4.setCellStyle(my_style1);
					cell4 = head1.createCell((short) 9);
					cell4.setCellValue("Spl All");
					cell4.setCellStyle(my_style1);
					cell4 = head1.createCell((short) 10);
					cell4.setCellValue("Conv All");
					cell4.setCellStyle(my_style1);
					cell4 = head1.createCell((short) 11);
					cell4.setCellValue("Washing All");
					cell4.setCellStyle(my_style1);
					cell4 = head1.createCell((short) 12);
					cell4.setCellValue("Bonus");
					cell4.setCellStyle(my_style1);
					cell4 = head1.createCell((short) 13);
					cell4.setCellValue("Min Insurance");
					cell4.setCellStyle(my_style1);
					cell4 = head1.createCell((short) 14);
					cell4.setCellValue("Add less Amt");
					cell4.setCellStyle(my_style1);
					cell4 = head1.createCell((short) 15);
					cell4.setCellValue("Col");
					cell4.setCellStyle(my_style1);
					cell4 = head1.createCell((short) 16);
					cell4.setCellValue("Add income");
					cell4.setCellStyle(my_style1);
					cell4 = head1.createCell((short) 17);
					cell4.setCellValue("Tot income");
					cell4.setCellStyle(my_style1);
					cell4 = head1.createCell((short) 18);
					cell4.setCellValue("Tot deduction");
					cell4.setCellStyle(my_style1);
					cell4 = head1.createCell((short) 19);
					cell4.setCellValue("Net pay");
					cell4.setCellStyle(my_style1);

					while (pBrcd1.equals(rs.getString("PRJ_SRNO"))) {
						basic_total = basic_total + rs.getFloat("basic");

						head1 = sheet.createRow((short) i++);
						cell4 = head1.createCell((short) 0);
						cell4.setCellValue("" + rs.getString("EMPCODE"));
						cell4 = head1.createCell((short) 1);
						cell4.setCellValue("" + rs.getString("name"));
						cell4 = head1.createCell((short) 4);
						cell4.setCellValue(rs.getFloat("basic"));

						if (rs.getFloat("curda") == 0.0) {
							cell4 = head1.createCell((short) 5);
							cell4.setCellValue("");
						} else {
							float curda = rs.getFloat("curda");
							currda_total = currda_total + curda;
							cell4 = head1.createCell((short) 5);
							cell4.setCellValue(curda);
						}
						hra_total = hra_total + rs.getFloat("hra");

						cell4 = head1.createCell((short) 6);
						cell4.setCellValue(rs.getFloat("hra"));

						medical_total = medical_total = rs.getFloat("medical");
						cell4 = head1.createCell((short) 7);
						cell4.setCellValue(rs.getFloat("medical"));

						edu_total = edu_total = rs.getFloat("education");
						cell4 = head1.createCell((short) 8);
						cell4.setCellValue(rs.getFloat("education"));

						if (rs.getFloat("splall") == 0.0) {
							cell4 = head1.createCell((short) 9);
							cell4.setCellValue("");

						} else {
							float splall1 = rs.getFloat("splall");
							splall_total = splall_total + splall1;
							cell4 = head1.createCell((short) 9);
							cell4.setCellValue(splall1);
						}

						convall_total = convall_total + rs.getFloat("convall");
						cell4 = head1.createCell((short) 10);
						cell4.setCellValue(rs.getFloat("convall"));

						if (rs.getFloat("washall") == 0.0) {
							cell4 = head1.createCell((short) 11);
							cell4.setCellValue("");
						} else {
							float washing = rs.getFloat("washall");
							washingall_total = washingall_total = rs.getFloat("washall");
							cell4 = head1.createCell((short) 11);
							cell4.setCellValue(washing);
						}

						if (rs.getFloat("bonusall") == 0.0) {

							cell4 = head1.createCell((short) 12);
							cell4.setCellValue("");
						} else {
							float bonus = rs.getFloat("bonusall");
							bonus_total = bonus_total + rs.getFloat("bonusall");

							cell4 = head1.createCell((short) 12);
							cell4.setCellValue(bonus);
						}

						minins_total = minins_total + +rs.getFloat("min_ins");
						cell4 = head1.createCell((short) 13);
						cell4.setCellValue(rs.getFloat("min_ins"));

						if (rs.getFloat("addless") == 0.0) {
							cell4 = head1.createCell((short) 14);
							cell4.setCellValue("");
						} else {
							float addless = rs.getFloat("addless");
							addlss_total = addlss_total + addless;
							cell4 = head1.createCell((short) 14);
							cell4.setCellValue(addless);
						}

						col_total = col_total + +rs.getFloat("col");
						cell4 = head1.createCell((short) 15);
						cell4.setCellValue(rs.getFloat("col"));

						/*
						 * if(rs.getFloat("special")==0.0){ PdfPCell cell15 =
						 * new PdfPCell(new Phrase("",f1));
						 * datatab.addCell(cell15); } else{ float spcl =
						 * rs.getFloat("special"); special_total = special_total
						 * + spcl; PdfPCell cell15 = new PdfPCell(new
						 * Phrase(spcl,f1));
						 * cell15.setHorizontalAlignment(Element.ALIGN_RIGHT);
						 * datatab.addCell(cell15); }
						 */

						if (rs.getFloat("addinc") == 0.0) {
							cell4 = head1.createCell((short) 16);
							cell4.setCellValue("");
						} else {
							float add_inc = rs.getFloat("addinc");
							addinc_total = addinc_total + add_inc;
							cell4 = head1.createCell((short) 16);
							cell4.setCellValue(add_inc);
						}

						totinc_total = totinc_total + rs.getFloat("totear");
						cell4 = head1.createCell((short) 17);
						cell4.setCellValue(rs.getFloat("totear"));

						totded_total = totded_total + rs.getFloat("totded");
						cell4 = head1.createCell((short) 18);
						cell4.setCellValue(rs.getFloat("totded"));

						netpay_total = netpay_total + rs.getFloat("payable");
						cell4 = head1.createCell((short) 19);
						cell4.setCellValue(rs.getFloat("payable"));

						tot_no_emp = tot_no_emp + 1;
						br_tot_no_emp = br_tot_no_emp + 1;
						if (!rs.next()) {
							break;
						}
						if (!pBrcd1.equals(rs.getString("PRJ_SRNO"))) {
							rs.previous();
							break;
						}
					}

					head1 = sheet.createRow((short) i++);
					cell4 = head1.createCell((short) 0);
					cell4.setCellValue("");
					cell4 = head1.createCell((short) 1);
					cell4.setCellValue("");
					cell4 = head1.createCell((short) 2);
					cell4.setCellValue("TOTAL :");
					cell4.setCellStyle(my_style);
					cell4 = head1.createCell((short) 4);
					cell4.setCellValue(basic_total);

					lstbasic_total = lstbasic_total + basic_total;
					basic_total = 0.0f;

					cell4 = head1.createCell((short) 5);
					cell4.setCellValue(currda_total);
					lstcurrda_total = lstcurrda_total + currda_total;
					currda_total = 0.0f;

					cell4 = head1.createCell((short) 6);
					cell4.setCellValue(hra_total);
					lsthra_total = lsthra_total + hra_total;
					hra_total = 0.0f;

					cell4 = head1.createCell((short) 7);
					cell4.setCellValue(medical_total);
					lstmedical_total = lstmedical_total + medical_total;
					medical_total = 0.0f;

					cell4 = head1.createCell((short) 8);
					cell4.setCellValue(edu_total);
					lstedu_total = lstedu_total + edu_total;
					edu_total = 0.0f;

					cell4 = head1.createCell((short) 9);
					cell4.setCellValue(splall_total);
					lstsplall_total = lstsplall_total = splall_total;
					splall_total = 0.0f;

					cell4 = head1.createCell((short) 10);
					cell4.setCellValue(convall_total);
					lstconvall_total = lstconvall_total = convall_total;
					convall_total = 0.0f;

					cell4 = head1.createCell((short) 11);
					cell4.setCellValue(washingall_total);
					lstwashingall_total = lstwashingall_total + washingall_total;
					washingall_total = 0.0f;

					cell4 = head1.createCell((short) 12);
					cell4.setCellValue(bonus_total);
					lstbonus_total = lstbonus_total + bonus_total;
					bonus_total = 0.0f;

					cell4 = head1.createCell((short) 13);
					cell4.setCellValue(minins_total);
					lstminins_total = lstminins_total + minins_total;
					minins_total = 0.0f;

					cell4 = head1.createCell((short) 14);
					cell4.setCellValue(addlss_total);
					lstaddlss_total = lstaddlss_total + addlss_total;
					addlss_total = 0.0f;

					cell4 = head1.createCell((short) 15);
					cell4.setCellValue(col_total);
					lstcol_total = lstcol_total + col_total;
					col_total = 0.0f;

					/*
					 * PdfPCell cell15 = new PdfPCell(new
					 * Phrase(special_total,f1));
					 * cell15.setHorizontalAlignment(Element.ALIGN_RIGHT);
					 * datatot.addCell(cell15); lstspecial_total =
					 * lstspecial_total + special_total ; special_total = 0.0f;
					 */

					cell4 = head1.createCell((short) 16);
					cell4.setCellValue(addinc_total);
					lstaddinc_total = lstaddinc_total + addinc_total;
					addinc_total = 0.0f;

					cell4 = head1.createCell((short) 17);
					cell4.setCellValue(totinc_total);
					lsttotinc_total = lsttotinc_total + totinc_total;
					totinc_total = 0.0f;

					cell4 = head1.createCell((short) 18);
					cell4.setCellValue(totded_total);
					lsttotded_total = lsttotded_total + totded_total;
					totded_total = 0.0f;

					cell4 = head1.createCell((short) 19);
					cell4.setCellValue(netpay_total);
					lstnetpay_total = lstnetpay_total + netpay_total;
					netpay_total = 0.0f;

					head1 = sheet.createRow((short) i++);
					head1.createCell((short) 0).setCellValue("Total Employee(s) : " + br_tot_no_emp);
					head1 = sheet.createRow((short) i++);
					head1.createCell((short) 0).setCellValue("");
					head1 = sheet.createRow((short) i++);
					head1.createCell((short) 0).setCellValue("");
				}

				HSSFRow head1 = sheet.createRow((short) i++);
				HSSFCell cell4;
				head1.createCell((short) 0).setCellValue("Total Employee(s) : " + tot_no_emp);

				System.out.println("Total No Of Employee :- " + tot_no_emp);

				head1 = sheet.createRow((short) i++);
				head1 = sheet.createRow((short) i++);
				cell4 = head1.createCell((short) 2);
				cell4.setCellValue("SUMMARY");
				cell4.setCellStyle(my_style);
				cell4 = head1.createCell((short) 4);
				cell4.setCellValue("Basic");
				cell4.setCellStyle(my_style);
				cell4 = head1.createCell((short) 5);
				cell4.setCellValue("Current D.A");
				cell4.setCellStyle(my_style);
				cell4 = head1.createCell((short) 6);
				cell4.setCellValue("H.R.A");
				cell4.setCellStyle(my_style);
				cell4 = head1.createCell((short) 7);
				cell4.setCellValue("Medical");
				cell4.setCellStyle(my_style);
				cell4 = head1.createCell((short) 8);
				cell4.setCellValue("Education");
				cell4.setCellStyle(my_style);
				cell4 = head1.createCell((short) 9);
				cell4.setCellValue("Spl All");
				cell4.setCellStyle(my_style);
				cell4 = head1.createCell((short) 10);
				cell4.setCellValue("Conv All");
				cell4.setCellStyle(my_style);
				cell4 = head1.createCell((short) 11);
				cell4.setCellValue("Washing All");
				cell4.setCellStyle(my_style);
				cell4 = head1.createCell((short) 12);
				cell4.setCellValue("Bonus");
				cell4.setCellStyle(my_style);
				cell4 = head1.createCell((short) 13);
				cell4.setCellValue("Min Insurance");
				cell4.setCellStyle(my_style);
				cell4 = head1.createCell((short) 14);
				cell4.setCellValue("Add less Amt");
				cell4.setCellStyle(my_style);
				cell4 = head1.createCell((short) 15);
				cell4.setCellValue("Col");
				cell4.setCellStyle(my_style);
				cell4 = head1.createCell((short) 16);
				cell4.setCellValue("Add income");
				cell4.setCellStyle(my_style);
				cell4 = head1.createCell((short) 17);
				cell4.setCellValue("Tot income");
				cell4.setCellStyle(my_style);
				cell4 = head1.createCell((short) 18);
				cell4.setCellValue("Tot deduction");
				cell4.setCellStyle(my_style);
				cell4 = head1.createCell((short) 19);
				cell4.setCellValue("Net pay");
				cell4.setCellStyle(my_style);

				head1 = sheet.createRow((short) i++);
				cell4 = head1.createCell((short) 2);
				cell4.setCellValue("Gross Total:");
				cell4.setCellStyle(my_style);
				cell4 = head1.createCell((short) 4);
				cell4.setCellValue(lstbasic_total);
				cell4 = head1.createCell((short) 5);
				cell4.setCellValue(lstcurrda_total);
				cell4 = head1.createCell((short) 6);
				cell4.setCellValue(lsthra_total);
				cell4 = head1.createCell((short) 7);
				cell4.setCellValue(lstmedical_total);
				cell4 = head1.createCell((short) 8);
				cell4.setCellValue(lstedu_total);
				cell4 = head1.createCell((short) 9);
				cell4.setCellValue(lstsplall_total);
				cell4 = head1.createCell((short) 10);
				cell4.setCellValue(lstconvall_total);
				cell4 = head1.createCell((short) 11);
				cell4.setCellValue(lstwashingall_total);
				cell4 = head1.createCell((short) 12);
				cell4.setCellValue(lstbonus_total);
				cell4 = head1.createCell((short) 13);
				cell4.setCellValue(lstminins_total);
				cell4 = head1.createCell((short) 14);
				cell4.setCellValue(lstaddlss_total);
				cell4 = head1.createCell((short) 15);
				cell4.setCellValue(lstcol_total);
				cell4 = head1.createCell((short) 16);
				cell4.setCellValue(lstaddinc_total);
				cell4 = head1.createCell((short) 17);
				cell4.setCellValue(lsttotinc_total);
				cell4 = head1.createCell((short) 18);
				cell4.setCellValue(lsttotded_total);
				cell4 = head1.createCell((short) 19);
				cell4.setCellValue(lstnetpay_total);

				DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

				Calendar calobj = Calendar.getInstance();
				HSSFRow row = sheet.createRow((short) i);
				row.createCell((short) 0).setCellValue(" ");
				row = sheet.createRow((short) i + 1);
				row.createCell((short) 0).setCellValue("Report Date And Time " + df.format(calobj.getTime()));

				hwb.write(out1);
				out1.close();
				/*
				 * final int BUFSIZE = 4096; File file = new File(filepath); int
				 * length = 0;
				 */

				System.out.println("last hra = " + lsthra_total);
				System.out.println("last medical = " + lstmedical_total);
				System.out.println("last education = " + lstedu_total);
				System.out.println("last splall = " + lstsplall_total);
				System.out.println("last convall = " + lstconvall_total);
				System.out.println("last washing = " + lstwashingall_total);
				System.out.println("last bonus = " + lstbonus_total);
				System.out.println("last minins = " + lstminins_total);
				System.out.println("last addless = " + lstaddlss_total);
				System.out.println("last col = " + lstcol_total);
				// System.out.println("last special = "+lstspecial_total);
				System.out.println("last addinc = " + lstaddinc_total);
				System.out.println("last totinc = " + lsttotinc_total);
				System.out.println("last totded = " + lsttotded_total);
				System.out.println("last totnetpay = " + lstnetpay_total);

				st.close();
				con.close();

			} 
			else if (type.equalsIgnoreCase("ID")) {

				ReportDAO.OpenCon("", "", "", repBean);
				con = repBean.getCn();
				Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				String report_type[] = { "t.PRJ_SRNO", "t.DEPT" };

				FileOutputStream out1 = new FileOutputStream(new File(filepath));

				HSSFWorkbook hwb = new HSSFWorkbook();
				for (int x = 0; x < report_type.length; x++) {
					tot_no_emp = 0;
					HSSFSheet sheet = hwb.createSheet(x == 0 ? "Project" : "Depertment");
					Calendar currentMonth = Calendar.getInstance();

					HSSFCellStyle my_style = hwb.createCellStyle();
					HSSFFont my_font = hwb.createFont();
					my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
					my_style.setFont(my_font);

					HSSFRow rowtitle = sheet.createRow((short) 0);
					HSSFCell cell = rowtitle.createCell((short) 10);
					cell.setCellValue(prop.getProperty("companyName"));
					cell.setCellStyle(my_style);
					HSSFRow rowtitle1 = sheet.createRow((short) 1);
					HSSFCell cell1 = rowtitle1.createCell((short) 9);
					cell1.setCellValue(prop.getProperty("addressForReport"));
					cell1.setCellStyle(my_style);
					HSSFRow rowtitle2 = sheet.createRow((short) 2);
					HSSFCell cell2 = rowtitle2.createCell((short) 11);
					cell2.setCellValue(prop.getProperty("contactForReport"));
					cell2.setCellStyle(my_style);
					HSSFRow rowtitle31 = sheet.createRow((short) 3);
					cell2 = rowtitle31.createCell((short) 11);
					cell2.setCellValue(prop.getProperty("mailForReport"));
					cell2.setCellStyle(my_style);
					HSSFRow rowtitle3 = sheet.createRow((short) 4);
					cell2 = rowtitle3.createCell((short) 10);
					cell2.setCellValue("Salary Sheet For The Month Of :- " + PAYREGDT);
					cell2.setCellStyle(my_style);
					HSSFRow rowtitle4 = sheet.createRow((short) 5);
					rowtitle4.createCell((short) 0).setCellValue("");
					HSSFRow rowtitle5 = sheet.createRow((short) 6);
					rowtitle5.createCell((short) 0).setCellValue("");

					HSSFFont blueFont = hwb.createFont();
					blueFont.setColor(HSSFColor.BLUE.index);

					HSSFCellStyle style = hwb.createCellStyle();
					// style.setFont(blueFont);
					style.setFillForegroundColor(HSSFColor.BLUE.index);

					int i = 10;

					Rectangle rec = new Rectangle(100, 100);
					
					
					float lstother_total = 0.0f;
					float other_total = 0.0f;
					float basic_total = 0.0f;
					float lstbasic_total = 0.0f;
					float lic_total = 0.0f;
					float lstlic_total = 0.0f;
					float hra_total = 0.0f;
					float lsthra_total = 0.0f;
					float convall_total = 0.0f;
					float lstconvall_total = 0.0f;
					float pf_total = 0.0f;
					float lstpf_total = 0.0f;
					float pt_total = 0.0f;
					float lstpt_total = 0.0f;
					float mlwf_total = 0.0f;
					float lstmlwf_total = 0.0f;
					float tds_total = 0.0f;
					float lsttds_total = 0.0f;
					float addinc_total = 0.0f;
					float lstaddinc_total = 0.0f;
					float totinc_total = 0.0f;
					float lsttotinc_total = 0.0f;
					float addded_total = 0.0f;
					float lstaddded_total = 0.0f;
					float totded_total = 0.0f;
					float lsttotded_total = 0.0f;
					float netpay_total = 0.0f;
					float lstnetpay_total = 0.0f;
					float eepf_total = 0.0f;
					float lsteepf_total = 0.0f;
					float eeps_total = 0.0f;
					float lsteeps_total = 0.0f;
					float eedli_total = 0.0f;
					float lsteedli_total = 0.0f;
					float eepfadmin_total = 0.0f;
					float lsteepfadmin_total = 0.0f;
					float eedliadmin_total = 0.0f;
					float lsteedliadmin_total = 0.0f;
					float eesic = 0.0f;
					float lsteesic_total = 0.0f;
					float absentdays_total = 0.0f;

					float da_total = 0.0f;
					float lstda_total = 0.0f;
					float deptall_total = 0.0f;
					float lstdeptall_total = 0.0f;
					float othersplall_total = 0.0f;
					float lstothersplall_total = 0.0f;
					float mediclaim_total = 0.0f;
					float lstmediclaim_total = 0.0f;
					float otherded_total = 0.0f;
					float lstotherded_total = 0.0f;
					float memded_total = 0.0f;
					float lstmemded_total = 0.0f;
					float bankloan_total = 0.0f;
					float lstbankloan_total = 0.0f;
					float housingloan_total = 0.0f;
					float lsthousingloan_total = 0.0f;
					float vehicleloan_total = 0.0f;
					float lstvehicleloan_total = 0.0f;
					float wvehicleloan_total = 0.0f;
					float lstwvehicleloan_total = 0.0f;
					float personalloan_total = 0.0f;
					float lstpersonalloan_total = 0.0f;
					float otherloan_total = 0.0f;
					float lstotherloan_total = 0.0f;
					
					
					if(emptype.equalsIgnoreCase("4"))
					{
						emptype="1,2,3";
					}
					else if(emptype.equalsIgnoreCase("5")){
						emptype="0,1,2,3";
					}
				

					String BranchCodeList="";
					if(branchCode.equalsIgnoreCase("0"))
					{
						BranchCodeList = "";
					}
					else 
					{
						BranchCodeList = " AND t.PRJ_SRNO IN ("+branchCode+")";
					}

					
					EmpSql = "         SELECT  empcode , RTRIM(EMPMAST.FNAME)+' '+RTRIM(EMPMAST.MNAME)+' '+RTRIM(EMPMAST.LNAME)AS name, "
							+ "     	EMPMAST.empno,  empmast.DOL, empmast.STATUS, T.PRJ_SRNO,T.DEPT,"
							
							+ "       CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 101 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)   "
							+ "		THEN	(SELECT NET_AMT AS basic  FROM   PAYTRAN WHERE TRNCD = 101 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "		ELSE	(SELECT 0 AS basic) END AS 'basic',"

							+ "       CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 102 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)   "
							+ "		THEN	(SELECT NET_AMT AS da  FROM   PAYTRAN WHERE TRNCD = 102 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "		ELSE	(SELECT 0 AS da) END AS 'da',"
							
							+ "       CASE  WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 103 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "		THEN			(SELECT NET_AMT AS hra  FROM   PAYTRAN WHERE TRNCD = 103 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "		ELSE		(SELECT 0 AS hra) END AS 'hra',"

							+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 107 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "				THEN (SELECT NET_AMT AS deptall  FROM   PAYTRAN WHERE TRNCD = 107  AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)    			"
							+ "	ELSE (SELECT 0 AS deptall) END AS 'deptall'," 
							
							+" CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 108 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	    THEN			(SELECT NET_AMT AS convall  FROM   PAYTRAN WHERE TRNCD = 108 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "		ELSE		(SELECT 0 AS convall) END AS 'convall',"
														
							+ "       CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 129 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "		THEN		(SELECT NET_AMT AS othersplall  FROM   PAYTRAN WHERE TRNCD = 129 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "		ELSE		(SELECT 0 AS othersplall) END AS 'othersplall',"
							
							+ "	    CASE  WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 999 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "		THEN			(SELECT NET_AMT AS payable  FROM   PAYTRAN WHERE TRNCD = 999 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "		ELSE		(SELECT 0 AS payable) END AS 'payable',"
							
							+"			 CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 201 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "			THEN		(SELECT NET_AMT AS pf  FROM   PAYTRAN WHERE TRNCD = 201 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)    			"
							+ "		ELSE		(SELECT 0 AS er2) END AS 'pf',	 "
							
							+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 202 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	THEN	(SELECT NET_AMT AS pt  FROM   PAYTRAN WHERE TRNCD = 202 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt
							+ "' AND empno = EMPMAST.empno)		ELSE			(SELECT 0 AS pt) END AS 'pt', "
							
							+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 205 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	THEN	(SELECT NET_AMT AS lic  FROM   PAYTRAN WHERE TRNCD = 205 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt
							+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS lic) END AS 'lic', "
							
							+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 212 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	THEN	(SELECT NET_AMT AS mediclaim  FROM   PAYTRAN WHERE TRNCD = 212 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt
							+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS mediclaim) END AS 'mediclaim', "
							
							+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 213 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	THEN	(SELECT NET_AMT AS otherded  FROM   PAYTRAN WHERE TRNCD = 213 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt
							+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS otherded) END AS 'otherded', "
							
							+ "		 CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 223 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "		THEN		(SELECT NET_AMT AS memded  FROM   PAYTRAN WHERE TRNCD = 223 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "		ELSE  (SELECT 0 AS memded) END AS 'memded',"
							
							+ "	 CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 228 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	THEN		(SELECT NET_AMT AS tds  FROM   PAYTRAN WHERE TRNCD = 228 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	ELSE		(SELECT 0 AS tds) END AS 'tds'," 

							+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 211 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	THEN	(SELECT NET_AMT AS mlwf  FROM   PAYTRAN WHERE TRNCD = 211 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt
							+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS mlwf) END AS 'mlwf', "
							
							+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 244 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	THEN	(SELECT NET_AMT AS housingloan  FROM   PAYTRAN WHERE TRNCD = 244 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt
							+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS housingloan) END AS 'housingloan', "

							+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 245 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	THEN	(SELECT NET_AMT AS vehicleloan  FROM   PAYTRAN WHERE TRNCD = 245 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt
							+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS vehicleloan) END AS 'vehicleloan', "

							+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 246 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	THEN	(SELECT NET_AMT AS wvehicleloan  FROM   PAYTRAN WHERE TRNCD = 246 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt
							+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS wvehicleloan) END AS 'wvehicleloan', "

							+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 247 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	THEN	(SELECT NET_AMT AS personalloan  FROM   PAYTRAN WHERE TRNCD = 247 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt
							+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS personalloan) END AS 'personalloan', "

							
							+ "CASE WHEN EXISTS(SELECT SUM(NET_AMT) FROM   PAYTRAN WHERE TRNCD IN (248, 249) AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	THEN	(SELECT SUM(NET_AMT) AS otherloan  FROM   PAYTRAN WHERE TRNCD IN (248, 249) AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt
							+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS otherloan) END AS 'otherloan', "
							
							+ "    case when EXISTS(select SUM(net_amt) from   PAYTRAN  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198)"
							+ "    and TRNCD not in(101,102,103,107,108,129)"
							+ "    and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno =  EMPMAST.empno )"
							+ "	 THEN	(select SUM(net_amt) as addinc from   PAYTRAN  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198)"
							+ "    and TRNCD not in(101,102,103,107,108,129)"
							+ "    and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno =  EMPMAST.empno) "
							+ "	 ELSE		(SELECT 0 AS addinc) END AS 'addinc',"
							
							+ "      case when EXISTS(select SUM(net_amt)   from   PAYTRAN  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 201 and 299)"
							+ "     and TRNCD not in(201,202,205,212,213,223,228,211,248,249, 244, 245, 246, 247) and trndt BETWEEN '"
							+ BomDt + "' and '" + EomDt + "' and empno = empmast.empno)"
							+ "	  THEN	(select SUM(net_amt)  as other from   PAYTRAN  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 201 and 299)"
							+ "     and TRNCD not in(201,202,205,212,213,223,228,211,248,249, 244, 245, 246, 247) and trndt BETWEEN '"
							+ BomDt + "' and '" + EomDt + "' and empno = empmast.empno)"
							+ "	  ELSE	(SELECT 0 AS other) END AS 'other' ,"
							
							+ "	  case when EXISTS(select SUM(net_amt) from   PAYTRAN  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 201 and 299)"
							+ "     and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno = empmast.empno)"
							+ "	  THEN			(select SUM(net_amt) as totded from   PAYTRAN  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 201 and 299) "
							+ "     and trndt BETWEEN '" + BomDt + "' and '" + EomDt
							+ "' and empno = empmast.empno)	" + "	  ELSE 	(SELECT 0 AS totded) END AS 'totded' ,"
							
							+ "	  case when EXISTS(select SUM(net_amt) from   PAYTRAN  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198)"
							+ "     and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno = empmast.empno) "
							+ "	  THEN	(select SUM(net_amt) from   PAYTRAN  where TRNCD in    (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198)"
							+ "     and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno = empmast.empno)"
							+ "	  ELSE		(SELECT 0 AS totear) END AS 'totear',"
							
							+ " 	 CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 301 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	THEN	(SELECT NET_AMT AS absentdays  FROM   PAYTRAN WHERE TRNCD = 301 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	  ELSE		(SELECT 0 AS absentdays) END AS 'absentdays'"
							
							
							+ "     from EMPMAST empmast     join   EMPTRAN t on t.empno = empmast.empno"
							+ "     where      T.srno =(SELECT MAX(E2.SRNO) FROM EMPTRAN E2 WHERE E2.empno = empmast.empno AND E2.EFFDATE <= '"
							+ EomDt + "'" + "     and (( empmast.STATUS='A' AND	empmast.DOJ <= '" + EomDt
							+ "') or  (empmast.STATUS ='N' And empmast.DOL>='" + BomDt + "' )) )"
							+ "     AND T.empno in (SELECT DISTINCT empno FROM PAYTRAN WHERE TRNDT BETWEEN '" + BomDt
							//+ "' and '" + EomDt + "') " +
							+ "' and '" + EomDt + "') AND EMPLOYEE_TYPE IN("+emptype+") "+BranchCodeList +

						
							
							"    UNION "
							+ "         SELECT  empcode , RTRIM(EMPMAST.FNAME)+' '+RTRIM(EMPMAST.MNAME)+' '+RTRIM(EMPMAST.LNAME)AS name,"
							+ "     	EMPMAST.empno, empmast.DOL, empmast.STATUS, T.PRJ_SRNO,T.DEPT,"

						
							+ "       CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 101 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "		THEN	(SELECT NET_AMT AS basic  FROM   PAYTRAN_STAGE WHERE TRNCD = 101 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "		ELSE	(SELECT 0 AS basic) END AS 'basic',"
							
							+ "       CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 102 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "		THEN	(SELECT NET_AMT AS da  FROM   PAYTRAN_STAGE WHERE TRNCD = 102 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "		ELSE	(SELECT 0 AS da) END AS 'da',"
						
							+ "       CASE  WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 103 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "		THEN			(SELECT NET_AMT AS hra  FROM   PAYTRAN_STAGE WHERE TRNCD = 103 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "		ELSE		(SELECT 0 AS hra) END AS 'hra',"
														
							+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 107 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "				THEN (SELECT NET_AMT AS deptall  FROM   PAYTRAN_STAGE WHERE TRNCD = 107  AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)    			"
							+ "	ELSE (SELECT 0 AS deptall) END AS 'deptall',"
														
							+"          CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 108 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	    THEN			(SELECT NET_AMT AS convall  FROM   PAYTRAN_STAGE WHERE TRNCD = 108 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "		ELSE		(SELECT 0 AS convall) END AS 'convall',"
							
							+ "       CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 129 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "		THEN		(SELECT NET_AMT AS othersplall  FROM   PAYTRAN_STAGE WHERE TRNCD = 129 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "		ELSE		(SELECT 0 AS othersplall) END AS 'othersplall',"
							
							+ "	    CASE  WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 999 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "		THEN			(SELECT NET_AMT AS payable  FROM   PAYTRAN_STAGE WHERE TRNCD = 999 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "		ELSE		(SELECT 0 AS payable) END AS 'payable',"
							
							+"			 CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 201 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "			THEN		(SELECT NET_AMT AS pf  FROM   PAYTRAN_STAGE WHERE TRNCD = 201 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)    			"
							+ "		ELSE		(SELECT 0 AS er2) END AS 'pf',	 "
							
							+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 202 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	THEN	(SELECT NET_AMT AS pt  FROM   PAYTRAN_STAGE WHERE TRNCD = 202 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt
							+ "' AND empno = EMPMAST.empno)		ELSE			(SELECT 0 AS pt) END AS 'pt', "
							

							+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 205 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	THEN	(SELECT NET_AMT AS lic  FROM   PAYTRAN_STAGE WHERE TRNCD = 205 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt
							+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS lic) END AS 'lic', "
								
							+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 212 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	THEN	(SELECT NET_AMT AS mediclaim  FROM   PAYTRAN_STAGE WHERE TRNCD = 212 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt
							+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS mediclaim) END AS 'mediclaim', "
								
							+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 213 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	THEN	(SELECT NET_AMT AS otherded  FROM   PAYTRAN_STAGE WHERE TRNCD = 213 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt
							+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS otherded) END AS 'otherded', "
								
							+ "		 CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 223 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "		THEN		(SELECT NET_AMT AS memded  FROM   PAYTRAN_STAGE WHERE TRNCD = 223 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "		ELSE  (SELECT 0 AS memded) END AS 'memded',"
							
							+ "	 CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 228 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	THEN		(SELECT NET_AMT AS tds  FROM   PAYTRAN_STAGE WHERE TRNCD = 228 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	ELSE		(SELECT 0 AS tds) END AS 'tds',"

							+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 211 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	THEN	(SELECT NET_AMT AS mlwf  FROM   PAYTRAN_STAGE WHERE TRNCD = 211 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt
							+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS mlwf) END AS 'mlwf', "
								
							+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 244 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	THEN	(SELECT NET_AMT AS housingloan  FROM   PAYTRAN_STAGE WHERE TRNCD = 244 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt
							+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS housingloan) END AS 'housingloan', "

							+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 245 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	THEN	(SELECT NET_AMT AS vehicleloan  FROM   PAYTRAN_STAGE WHERE TRNCD = 245 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt
							+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS vehicleloan) END AS 'vehicleloan', "

							+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 246 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	THEN	(SELECT NET_AMT AS wvehicleloan  FROM   PAYTRAN_STAGE WHERE TRNCD = 246 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt
							+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS wvehicleloan) END AS 'wvehicleloan', "

							+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 247 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	THEN	(SELECT NET_AMT AS personalloan  FROM   PAYTRAN_STAGE WHERE TRNCD = 247 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt
							+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS personalloan) END AS 'personalloan', "

							
							+ "CASE WHEN EXISTS(SELECT SUM(NET_AMT) FROM   PAYTRAN_STAGE WHERE TRNCD IN (248, 249) AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	THEN	(SELECT SUM(NET_AMT) AS otherloan  FROM   PAYTRAN_STAGE WHERE TRNCD IN (248, 249) AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt
							+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS otherloan) END AS 'otherloan', "
							
							
							+ "    case when EXISTS(select SUM(net_amt) from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198)"
							+ "    and TRNCD not in(101,102,103,107,108,129)"
							+ "    and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno =  EMPMAST.empno )"
							+ "	 THEN	(select SUM(net_amt) as addinc from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198)"
							+ "    and TRNCD not in(101,102,103,107,108,129)"
							+ "    and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno =  EMPMAST.empno) "
							+ "	 ELSE		(SELECT 0 AS addinc) END AS 'addinc',"
							
							+ "      case when EXISTS(select SUM(net_amt)   from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 200 and 299)"
							+ "     and TRNCD not in(201,202,205,212,213,223,228,211,248,249, 244, 245, 246, 247) and trndt BETWEEN '"
							+ BomDt + "' and '" + EomDt + "' and empno = empmast.empno)"
							+ "	  THEN	(select SUM(net_amt)  as other from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 200 and 299)"
							+ "     and TRNCD not in(201,202,205,212,213,223,228,211,248,249, 244, 245, 246, 247) and trndt BETWEEN '"
							+ BomDt + "' and '" + EomDt + "' and empno = empmast.empno)"
							+ "	  ELSE	(SELECT 0 AS other) END AS 'other' ,"
							
							+ "	  case when EXISTS(select SUM(net_amt) from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 200 and 299)"
							+ "     and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno = empmast.empno)"
							+ "	  THEN			(select SUM(net_amt) as totded from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 200 and 299) "
							+ "     and trndt BETWEEN '" + BomDt + "' and '" + EomDt
							+ "' and empno = empmast.empno)	" + "	  ELSE 	(SELECT 0 AS totded) END AS 'totded' ,"
							
							+ "	  case when EXISTS(select SUM(net_amt) from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198)"
							+ "     and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno = empmast.empno) "
							+ "	  THEN	(select SUM(net_amt) from   paytran_stage  where TRNCD in    (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198)"
							+ "     and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno = empmast.empno)"
							+ "	  ELSE		(SELECT 0 AS totear) END AS 'totear',"
							
							+ " 	 CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 301 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	THEN	(SELECT NET_AMT AS absentdays  FROM   PAYTRAN_STAGE WHERE TRNCD = 301 AND TRNDT BETWEEN '"
							+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
							+ "	  ELSE		(SELECT 0 AS absentdays) END AS 'absentdays'"
							
							
							+ "     from EMPMAST empmast     join   EMPTRAN t on t.empno = empmast.empno"
							+ "     where      T.srno =(SELECT MAX(E2.SRNO) FROM EMPTRAN E2 WHERE E2.empno = empmast.empno AND E2.EFFDATE <= '"
							+ EomDt + "'" + "     and (( empmast.STATUS='A' AND	empmast.DOJ <= '" + EomDt
							+ "') or  (empmast.STATUS ='N' And empmast.DOL>='" + BomDt + "' )) )"
							+ "     AND T.empno in (SELECT DISTINCT empno FROM PAYTRAN_STAGE WHERE TRNDT BETWEEN '"
							//+ BomDt + "' and '" + EomDt + "') " + "     order by   t.PRJ_SRNO,empno  ";
							+ BomDt + "' and '" + EomDt + "') AND EMPLOYEE_TYPE IN("+emptype+") " +BranchCodeList+ "     order by   t.PRJ_SRNO,empno  ";
				

					System.out.println("Excel SQL : "+EmpSql);
					ResultSet rs = st.executeQuery(EmpSql);

					while (rs.next()) {
						i++;
						String prj_name = null;
						String prj_code = null;
						int dept = 0;
						Connection conn = ConnectionManager.getConnectionTech();

						if (report_type[x].equalsIgnoreCase("t.prj_srno")) {
							// System.out.println("into if");
							String prjquery = "select Site_Name,Project_Code from Project_Sites where SITE_ID = '"
									+ rs.getString("PRJ_SRNO") + "'";
							// System.out.println(prjquery);
							Statement stmt = conn.createStatement();
							ResultSet prj = stmt.executeQuery(prjquery);
							if (prj.next()) {
								prj_name = prj.getString("Site_Name");
								prj_code = prj.getString("Project_Code");
							}
							pBrcd1 = rs.getString("PRJ_SRNO");
							br_tot_no_emp = 0;

							HSSFRow head1 = sheet.createRow((short) i++);
							HSSFCell cell4 = head1.createCell((short) 0);
							cell4.setCellValue(
									" Employee's Salary List For Project Site : " + prj_name + " (" + prj_code + ")");
							cell4.setCellStyle(my_style);
							prj.close();
						} else {

							Connection ccn = ConnectionManager.getConnection();
							Statement sts = ccn.createStatement();
							String stmt = "select distinct(lkp_srno),lkp_disc from lookup where lkp_code='DEPT' and lkp_srno="
									+ rs.getInt("DEPT") + "";
							ResultSet rrs = sts.executeQuery(stmt);
							// System.out.println(stmt);
							if (rrs.next()) {
								dept = rrs.getInt("lkp_srno");
								prj_name = rrs.getString("lkp_disc");
							}
							pBrcd1 = Integer.toString(rs.getInt("DEPT"));
							br_tot_no_emp = 0;

							HSSFRow head1 = sheet.createRow((short) i++);
							HSSFCell cell4 = head1.createCell((short) 0);
							cell4.setCellValue(" Employee's Salary List For Deptartment : " + prj_name + "");
							cell4.setCellStyle(my_style);

							ccn.close();
						}

						sheet.setColumnWidth((short) 0, (short) 3000);
						sheet.setColumnWidth((short) 1, (short) 7000);
						sheet.setColumnWidth((short) 4, (short) 4000);
						sheet.setColumnWidth((short) 5, (short) 4000);
						sheet.setColumnWidth((short) 6, (short) 4000);
						sheet.setColumnWidth((short) 7, (short) 4000);
						sheet.setColumnWidth((short) 8, (short) 4000);
						sheet.setColumnWidth((short) 9, (short) 4000);
						sheet.setColumnWidth((short) 10, (short) 4000);
						sheet.setColumnWidth((short) 11, (short) 4000);
						sheet.setColumnWidth((short) 12, (short) 4000);
						sheet.setColumnWidth((short) 13, (short) 4000);
						sheet.setColumnWidth((short) 14, (short) 4000);
						sheet.setColumnWidth((short) 15, (short) 4000);
						sheet.setColumnWidth((short) 16, (short) 4000);
						sheet.setColumnWidth((short) 17, (short) 4000);
						sheet.setColumnWidth((short) 18, (short) 4000);
						sheet.setColumnWidth((short) 19, (short) 4000);
						sheet.setColumnWidth((short) 20, (short) 4000);
						sheet.setColumnWidth((short) 21, (short) 4000);
						sheet.setColumnWidth((short) 22, (short) 4000);
						sheet.setColumnWidth((short) 23, (short) 4000);
						sheet.setColumnWidth((short) 24, (short) 4000);
						sheet.setColumnWidth((short) 25, (short) 4000);
						sheet.setColumnWidth((short) 26, (short) 4000);
						sheet.setColumnWidth((short) 27, (short) 4000);
						sheet.setColumnWidth((short) 28, (short) 4000);
						sheet.setColumnWidth((short) 29, (short) 4000);
						
						conn.close();

						HSSFRow rowhead = sheet.createRow((short) i++);

						
						rowhead.createCell((short) 0).setCellValue("EMP CODE.");
						rowhead.createCell((short) 1).setCellValue("EMPLOYEE NAME");
						rowhead.createCell((short) 4).setCellValue("BASIC");
						rowhead.createCell((short) 5).setCellValue("DA");
						rowhead.createCell((short) 6).setCellValue("HRA");
						rowhead.createCell((short) 7).setCellValue("DEPT ALL");
						rowhead.createCell((short) 8).setCellValue("CONV ALL");
						rowhead.createCell((short) 9).setCellValue("OTHER SPL ALL");
						rowhead.createCell((short) 10).setCellValue("ADD INCOME");
						rowhead.createCell((short) 11).setCellValue("TOTAL INCOME");
						rowhead.createCell((short) 12).setCellValue("PF");
						rowhead.createCell((short) 13).setCellValue("PT");
						rowhead.createCell((short) 14).setCellValue("LIC");
						rowhead.createCell((short) 15).setCellValue("MEDICLAIM");
						rowhead.createCell((short) 16).setCellValue("OTHER DED");
						rowhead.createCell((short) 17).setCellValue("MEMBER DED");
						rowhead.createCell((short) 18).setCellValue("TDS");
						rowhead.createCell((short) 19).setCellValue("MLWF");
						rowhead.createCell((short) 20).setCellValue("HOUSING LOAN");
						rowhead.createCell((short) 21).setCellValue("VEHICLE LOAN");
						rowhead.createCell((short) 22).setCellValue("WOMENS VEHICLE LOAN");
						rowhead.createCell((short) 23).setCellValue("PERSONAL LOAN");
						rowhead.createCell((short) 24).setCellValue("OTHER LOAN");
						rowhead.createCell((short) 25).setCellValue("OTHER");
						rowhead.createCell((short) 26).setCellValue("Tot DED");
						rowhead.createCell((short) 27).setCellValue("NET PAY");
						rowhead.createCell((short) 28).setCellValue("EMP STATUS");
						rowhead.createCell((short) 29).setCellValue("ABSENT DAYS");
						
					
						while (report_type[x].equalsIgnoreCase("t.prj_srno") ? pBrcd1.equals(rs.getString("PRJ_SRNO"))
								: dept == rs.getInt("DEPT")) {

							rowhead = sheet.createRow((short) i++);
							rowhead.createCell((short) 0).setCellValue("" + rs.getString("EMPCODE"));

							rowhead.createCell((short) 1).setCellValue("" + rs.getString("name"));

							basic_total = basic_total + rs.getFloat("basic");
							rowhead.createCell((short) 4).setCellValue((int) rs.getFloat("basic"));

							da_total = da_total + rs.getFloat("da");
							rowhead.createCell((short) 5).setCellValue((int) rs.getFloat("da"));
							
							hra_total = hra_total + rs.getFloat("hra");
							rowhead.createCell((short) 6).setCellValue((int) rs.getFloat("hra"));

							deptall_total = deptall_total + +rs.getFloat("deptall");
							rowhead.createCell((short) 7).setCellValue((int) rs.getFloat("deptall"));

							convall_total = convall_total + rs.getFloat("convall");
							rowhead.createCell((short) 8).setCellValue((int) rs.getFloat("convall"));

							othersplall_total = othersplall_total + rs.getFloat("othersplall");
							rowhead.createCell((short) 9).setCellValue((int) rs.getFloat("othersplall"));

							
							if (rs.getFloat("addinc") == 0.0) {
								rowhead.createCell((short) 10).setCellValue(0.00);

							} else {
								float add_inc = rs.getFloat("addinc");
								addinc_total = addinc_total + add_inc;
								rowhead.createCell((short) 10).setCellValue((int) add_inc);
							}


							totinc_total = totinc_total + rs.getFloat("totear");
							rowhead.createCell((short) 11).setCellValue((int) rs.getFloat("totear"));

							if (rs.getFloat("pf") == 0.0) {
								rowhead.createCell((short) 12).setCellValue(0.00);

							} else {
								float pf = rs.getFloat("pf");
								pf_total = pf_total + pf;
								rowhead.createCell((short) 12).setCellValue((int) pf);
							}

							if (rs.getFloat("pt") == 0.0) {
								rowhead.createCell((short) 13).setCellValue(0.00);

							} else {
								float pt = rs.getFloat("pt");
								pt_total = pt_total + pt;
								rowhead.createCell((short) 13).setCellValue((int) pt);
							}

							if (rs.getFloat("lic") == 0.0f) {
								rowhead.createCell((short) 14).setCellValue(0.00);

							} else {
								float lic = rs.getFloat("lic");
								lic_total = lic_total + lic;
								rowhead.createCell((short) 14).setCellValue((int) lic);
							}

							if (rs.getFloat("mediclaim") == 0.0f) {
								rowhead.createCell((short) 15).setCellValue(0.00);

							} else {
								float mediclaim = rs.getFloat("mediclaim");
								mediclaim_total = mediclaim_total + mediclaim;
								rowhead.createCell((short) 15).setCellValue((int) mediclaim);
							}

							if (rs.getFloat("otherded") == 0.0f) {
								rowhead.createCell((short) 16).setCellValue(0.00);
							} else {
								float otherded = rs.getFloat("otherded");
								otherded_total = otherded_total + otherded;
								rowhead.createCell((short) 16).setCellValue((int) otherded);
							}
							
							if (rs.getFloat("memded") == 0.0f) {
								rowhead.createCell((short) 17).setCellValue(0.00);
							} else {
								float memded = rs.getFloat("memded");
								memded_total = memded_total + memded;
								rowhead.createCell((short) 17).setCellValue((int) memded);
							}
							
							if (rs.getFloat("tds") == 0.0f) {
								rowhead.createCell((short) 18).setCellValue(0.00);
							} else {
								float tds = rs.getFloat("tds");
								tds_total = tds_total + tds;
								rowhead.createCell((short) 18).setCellValue((int) tds);
							}

							if (rs.getFloat("mlwf") == 0.0) {
								rowhead.createCell((short) 19).setCellValue(0.00);
							} else {
								float mlwf = rs.getFloat("mlwf");
								mlwf_total = mlwf_total + mlwf;
								rowhead.createCell((short) 19).setCellValue((int) mlwf);
							}
							
							if (rs.getFloat("housingloan") == 0.0) {
								rowhead.createCell((short) 20).setCellValue(0.00);

							} else {
								float housingloan = rs.getFloat("housingloan");
								housingloan_total = housingloan_total + housingloan;
								rowhead.createCell((short) 20).setCellValue((int) housingloan);
							}

							if (rs.getFloat("vehicleloan") == 0.0) {
								rowhead.createCell((short) 21).setCellValue(0.00);

							} else {
								float vehicleloan = rs.getFloat("vehicleloan");
								vehicleloan_total = vehicleloan_total + vehicleloan;
								rowhead.createCell((short) 21).setCellValue((int) vehicleloan);
							}

							if (rs.getFloat("wvehicleloan") == 0.0) {
								rowhead.createCell((short) 22).setCellValue(0.00);

							} else {
								float wvehicleloan = rs.getFloat("wvehicleloan");
								wvehicleloan_total = wvehicleloan_total + wvehicleloan;
								rowhead.createCell((short) 22).setCellValue((int) wvehicleloan);
							}


							if (rs.getFloat("personalloan") == 0.0) {
								rowhead.createCell((short) 23).setCellValue(0.00);

							} else {
								float personalloan = rs.getFloat("personalloan");
								personalloan_total = personalloan_total + personalloan;
								rowhead.createCell((short) 23).setCellValue((int) personalloan);
							}

							
							
							if (rs.getFloat("otherloan") == 0.0) {
								rowhead.createCell((short) 24).setCellValue(0.00);

							} else {
								float otherloan = rs.getFloat("otherloan");
								otherloan_total = otherloan_total + otherloan;
								rowhead.createCell((short) 24).setCellValue((int) otherloan);
							}
							

							if (rs.getFloat("other") == 0.0) {
								rowhead.createCell((short) 25).setCellValue(0.00);
							} else {
								float other = rs.getFloat("other");
								other_total = other_total + other;
								rowhead.createCell((short) 25).setCellValue((int) other);

							}

							
							totded_total = totded_total + rs.getFloat("totded");
							rowhead.createCell((short) 26).setCellValue((int) rs.getFloat("totded"));

							netpay_total = netpay_total + rs.getFloat("payable");
							rowhead.createCell((short) 27).setCellValue((int) rs.getFloat("payable"));


							if (rs.getString("DOL") != null)
							{
								rowhead.createCell((short) 28).setCellValue("Non Active");
							}
							else {
								rowhead.createCell((short) 28).setCellValue("Active");
							}
							
							rowhead.createCell((short) 29).setCellValue((int) rs.getFloat("absentdays"));

							
							tot_no_emp = tot_no_emp + 1;
							br_tot_no_emp = br_tot_no_emp + 1;
							if (!rs.next()) {
								break;
							}
							if (report_type[x].equalsIgnoreCase("t.prj_srno")) {

								if (!pBrcd1.equals(rs.getString("PRJ_SRNO"))) {
									rs.previous();
									break;
								}
							} else {
								if (dept != rs.getInt("DEPT")) {
									rs.previous();

									break;
								}
							}

						}
						rowhead = sheet.createRow((short) i++);
						rowhead.createCell((short) 0).setCellValue("");

						rowhead.createCell((short) 1).setCellValue("");
						rowhead.createCell((short) 3).setCellValue("TOTAL :--");

						rowhead.createCell((short) 4).setCellValue((int) basic_total);
						rowhead.createCell((short) 5).setCellValue(da_total);
						rowhead.createCell((short) 6).setCellValue(hra_total);
						rowhead.createCell((short) 7).setCellValue(deptall_total);
						rowhead.createCell((short) 8).setCellValue((int) convall_total);
						rowhead.createCell((short) 9).setCellValue((int) othersplall_total);
						rowhead.createCell((short) 10).setCellValue((int) addinc_total);
						rowhead.createCell((short) 11).setCellValue((int) totinc_total);


						lstbasic_total = lstbasic_total + basic_total;
						basic_total = 0.0f;
						
						lstda_total = lstda_total + da_total;
						da_total = 0.0f;

						lsthra_total = lsthra_total + hra_total;
						hra_total = 0.0f;
						
						lstdeptall_total = lstdeptall_total + deptall_total;
						deptall_total = 0.0f;
						
						lstconvall_total = lstconvall_total + convall_total;
						convall_total = 0.0f;

						lstothersplall_total = lstothersplall_total + othersplall_total;
						othersplall_total = 0.0f;

						lstaddinc_total = lstaddinc_total + addinc_total;
						addinc_total = 0.0f;

						lsttotinc_total = lsttotinc_total + totinc_total;
						totinc_total = 0.0f;
						
						rowhead.createCell((short) 12).setCellValue((int) pf_total);
						rowhead.createCell((short) 13).setCellValue((int) pt_total);
						rowhead.createCell((short) 14).setCellValue((int) lic_total);
						rowhead.createCell((short) 15).setCellValue((int) mediclaim_total);
						rowhead.createCell((short) 16).setCellValue((int) otherded_total);
						rowhead.createCell((short) 17).setCellValue((int) memded_total);
						rowhead.createCell((short) 18).setCellValue((int) tds_total);
						rowhead.createCell((short) 19).setCellValue((int) mlwf_total);
						rowhead.createCell((short) 20).setCellValue((int) housingloan_total);
						rowhead.createCell((short) 21).setCellValue((int) vehicleloan_total);
						rowhead.createCell((short) 22).setCellValue((int) wvehicleloan_total);
						rowhead.createCell((short) 23).setCellValue((int) personalloan_total);
						rowhead.createCell((short) 24).setCellValue((int) otherloan_total);
						rowhead.createCell((short) 25).setCellValue((int) other_total);
						rowhead.createCell((short) 26).setCellValue((int) totded_total);
						rowhead.createCell((short) 27).setCellValue((int) netpay_total);

						
						lstpf_total = lstpf_total + pf_total;

						lstpt_total = lstpt_total + pt_total;
						pt_total = 0.0f;

						lstlic_total = lstlic_total + lic_total;
						lic_total = 0.0f;
						
						lstmediclaim_total = lstmediclaim_total + mediclaim_total;
						mediclaim_total = 0.0f;
						
						lstotherded_total = lstotherded_total + otherded_total;
						otherded_total = 0.0f;
						
						lstmemded_total = lstmemded_total + memded_total;
						memded_total = 0.0f;
						
						lsttds_total = lsttds_total + tds_total;
						tds_total = 0.0f;

						lstmlwf_total = lstmlwf_total + mlwf_total;
						mlwf_total = 0.0f;
						
						/*lstbankloan_total = lstbankloan_total + bankloan_total;
						bankloan_total = 0.0f;*/
						
						lsthousingloan_total = lsthousingloan_total + housingloan_total;
						housingloan_total = 0.0f;
						
						lstvehicleloan_total = lstvehicleloan_total + vehicleloan_total;
						vehicleloan_total = 0.0f;
						
						lstwvehicleloan_total = lstwvehicleloan_total + wvehicleloan_total;
						wvehicleloan_total = 0.0f;
						
						lstpersonalloan_total = lstpersonalloan_total + personalloan_total;
						personalloan_total = 0.0f;

						lstotherloan_total = lstotherloan_total + otherloan_total;
						otherloan_total = 0.0f;

						lstother_total = lstother_total + other_total;
						other_total = 0.0f;

						lsttotded_total = lsttotded_total + totded_total;
						totded_total = 0.0f;

						lstnetpay_total = lstnetpay_total + netpay_total;
						netpay_total = 0.0f;

						
						pf_total = 0.0f;
						//esic_total = 0.0f;
						lsteepf_total += eepf_total;
						eepf_total = 0;

						lsteeps_total += eeps_total;
						eeps_total = 0;

						lsteedli_total += eedli_total;
						eedli_total = 0;

						lsteepfadmin_total += eepfadmin_total;
						eepfadmin_total = 0;

						lsteedliadmin_total += eedliadmin_total;
						eedliadmin_total = 0;

						lsteesic_total += eesic;
						eesic = 0;

						rowhead = sheet.createRow((short) i++);

						rowhead.createCell((short) 0).setCellValue("Total Employee(s) : " + br_tot_no_emp);

						rowhead = sheet.createRow((short) i++);

						rowhead.createCell((short) 0).setCellValue("");
					}

					HSSFRow rowhead = sheet.createRow((short) i++);

					rowhead.createCell((short) 0).setCellValue("Total Employee(s) : " + tot_no_emp);

					
					rowhead = sheet.createRow((short) i++);
					HSSFCell cell4;
					rowhead.createCell((short) 10).setCellValue("  ");
					rowhead = sheet.createRow((short) i++);

					cell4 = rowhead.createCell((short) 11);
					cell4.setCellValue("------- SUMMARY -------");
					cell4.setCellStyle(my_style);

					rowhead = sheet.createRow((short) i++);

					rowhead.createCell((short) 10).setCellValue("  ");
					rowhead = sheet.createRow((short) i++);

					rowhead.createCell((short) 10).setCellValue("  ");

					cell4 = rowhead.createCell((short) 1);
					cell4.setCellValue("BASIC Total:");
					cell4.setCellStyle(my_style);
					rowhead.createCell((short) 3).setCellValue((int) lstbasic_total);

					cell4 = rowhead.createCell((short) 6);
					cell4.setCellValue("DA Total:");
					cell4.setCellStyle(my_style);
					rowhead.createCell((short) 8).setCellValue(lstda_total);
					
					cell4 = rowhead.createCell((short) 11);
					cell4.setCellValue("HRA Total:");
					cell4.setCellStyle(my_style);
					rowhead.createCell((short) 13).setCellValue(lsthra_total);
					
					cell4 = rowhead.createCell((short) 16);
					cell4.setCellValue("DEPT ALL Total:");
					cell4.setCellStyle(my_style);
					rowhead.createCell((short) 18).setCellValue(lstdeptall_total);
					
					cell4 = rowhead.createCell((short) 21);
					cell4.setCellValue("CONV ALL Total:");
					cell4.setCellStyle(my_style);
					rowhead.createCell((short) 23).setCellValue((int) lstconvall_total);
					

					rowhead = sheet.createRow((short) i++);

					cell4 = rowhead.createCell((short) 1);
					cell4.setCellValue("OTHER SPECIAL ALLOW Total:");
					cell4.setCellStyle(my_style);
					rowhead.createCell((short) 3).setCellValue((int) lstothersplall_total);
					
					cell4 = rowhead.createCell((short) 6);
					cell4.setCellValue("ADD Income Total:");
					cell4.setCellStyle(my_style);
					rowhead.createCell((short) 8).setCellValue((int) lstaddinc_total);
					
					cell4 = rowhead.createCell((short) 11);
					cell4.setCellValue("PF Total:");
					cell4.setCellStyle(my_style);
					rowhead.createCell((short) 13).setCellValue((int) lstpf_total);
					
					cell4 = rowhead.createCell((short) 16);
					cell4.setCellValue("PT Total:");
					cell4.setCellStyle(my_style);
					rowhead.createCell((short) 18).setCellValue((int) lstpt_total);
					
					cell4 = rowhead.createCell((short) 21);
					cell4.setCellValue("LIC Total:");
					cell4.setCellStyle(my_style);
					rowhead.createCell((short) 23).setCellValue((int) lstlic_total);
					
					
					rowhead = sheet.createRow((short) i++);
					
					cell4 = rowhead.createCell((short) 1);
					cell4.setCellValue("MEDICLAIM Total:");
					cell4.setCellStyle(my_style);
					rowhead.createCell((short) 3).setCellValue((int) lstmediclaim_total);
					
					cell4 = rowhead.createCell((short) 6);
					cell4.setCellValue("OTHER DED Total:");
					cell4.setCellStyle(my_style);
					rowhead.createCell((short) 8).setCellValue((int) lstotherded_total);
					
					cell4 = rowhead.createCell((short) 11);
					cell4.setCellValue("MEMBER DED Total:");
					cell4.setCellStyle(my_style);
					rowhead.createCell((short) 13).setCellValue((int) lstmemded_total);
					
					cell4 = rowhead.createCell((short) 16);
					cell4.setCellValue("T.D.S Total:");
					cell4.setCellStyle(my_style);
					rowhead.createCell((short) 18).setCellValue((int) lsttds_total);
					
					cell4 = rowhead.createCell((short) 21);
					cell4.setCellValue("MLWF Total:");
					cell4.setCellStyle(my_style);
					rowhead.createCell((short) 23).setCellValue((int) lstmlwf_total);
					
					
					rowhead = sheet.createRow((short) i++);
					
					cell4 = rowhead.createCell((short) 1);
					cell4.setCellValue("BANK LOAN Total:");
					cell4.setCellStyle(my_style);
					rowhead.createCell((short) 3).setCellValue((int) lstbankloan_total);
					
					cell4 = rowhead.createCell((short) 6);
					cell4.setCellValue("OTHER LOAN Total:");
					cell4.setCellStyle(my_style);
					rowhead.createCell((short) 8).setCellValue((int) lstotherloan_total);
					
					cell4 = rowhead.createCell((short) 11);
					cell4.setCellValue("Total INCOME:");
					cell4.setCellStyle(my_style);
					rowhead.createCell((short) 13).setCellValue((int) lsttotinc_total);

					cell4 = rowhead.createCell((short) 16);
					cell4.setCellValue("ADDED Total:");
					cell4.setCellStyle(my_style);
					rowhead.createCell((short) 18).setCellValue((int) lstaddded_total);
					
					cell4 = rowhead.createCell((short) 21);
					cell4.setCellValue("Total Deduction:");
					cell4.setCellStyle(my_style);
					rowhead.createCell((short) 23).setCellValue((int) lsttotded_total);
					
					rowhead = sheet.createRow((short) i++);
					
					
					cell4 = rowhead.createCell((short) 1);
					cell4.setCellValue("HOUSING LOAN:");
					cell4.setCellStyle(my_style);
					rowhead.createCell((short) 3).setCellValue((int) lsthousingloan_total);
					
					cell4 = rowhead.createCell((short) 6);
					cell4.setCellValue("VEHICLE LOAN:");
					cell4.setCellStyle(my_style);
					rowhead.createCell((short) 8).setCellValue((int) lstvehicleloan_total);
					
					cell4 = rowhead.createCell((short) 11);
					cell4.setCellValue("WOMENS VEHICLE LOAN:");
					cell4.setCellStyle(my_style);
					rowhead.createCell((short) 13).setCellValue((int) lstwvehicleloan_total);
					
					cell4 = rowhead.createCell((short) 16);
					cell4.setCellValue("PERSONAL LOAN:");
					cell4.setCellStyle(my_style);
					rowhead.createCell((short) 18).setCellValue((int) lstpersonalloan_total);
					
					
					cell4 = rowhead.createCell((short) 21);
					cell4.setCellValue("NET PAY Total:");
					cell4.setCellStyle(my_style);

					float tttt = 0.0f;
					try {
						// System.out.println("date===="+date);
						Statement sttt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);
		/*				rs = st.executeQuery("select sum(net_amt) from paytran where trndt between '"
								+ ReportDAO.BOM(PAYREGDT) + "' and '" + ReportDAO.EOM(PAYREGDT)
								+ "'  and trncd=999  union  "
								+ "select sum(net_amt) from paytran_stage where trndt between '"
								+ ReportDAO.BOM(PAYREGDT) + "' and '" + ReportDAO.EOM(PAYREGDT) + "'  and trncd=999 ");
*/

						rs = st.executeQuery("select sum(net_amt) from paytran p, empmast e, EMPTRAN T where e.empno=p.empno and trndt between '"
								+ ReportDAO.BOM(PAYREGDT) + "' and '" + ReportDAO.EOM(PAYREGDT)
								+ "'  and P.trncd=999 and employee_type IN("+emptype+")  "
										+ "AND E.EMPNO=T.EMPNO AND T.SRNO=(SELECT MAX(SRNO) FROM EMPTRAN ETA WHERE T.EMPNO=ETA.EMPNO)"+BranchCodeList
										+ "union  "
								+ "select sum(net_amt) from paytran_stage p, empmast e, EMPTRAN T where e.empno=p.empno and trndt between '"
								+ ReportDAO.BOM(PAYREGDT) + "' and '" + ReportDAO.EOM(PAYREGDT) + "'  and P.trncd=999 and employee_type IN("+emptype+") "
										+ "AND E.EMPNO=T.EMPNO AND T.SRNO=(SELECT MAX(SRNO) FROM EMPTRAN ETA WHERE T.EMPNO=ETA.EMPNO)"+BranchCodeList);

						
						
						System.out.println("select sum(net_amt) from paytran p, empmast e, EMPTRAN T where e.empno=p.empno and trndt between '"
								+ ReportDAO.BOM(PAYREGDT) + "' and '" + ReportDAO.EOM(PAYREGDT)
								+ "'  and P.trncd=999 and employee_type IN("+emptype+")  "
										+ "AND E.EMPNO=T.EMPNO AND T.SRNO=(SELECT MAX(SRNO) FROM EMPTRAN ETA WHERE T.EMPNO=ETA.EMPNO)"+BranchCodeList
										+ "union  "
								+ "select sum(net_amt) from paytran_stage p, empmast e, EMPTRAN T where e.empno=p.empno and trndt between '"
								+ ReportDAO.BOM(PAYREGDT) + "' and '" + ReportDAO.EOM(PAYREGDT) + "'  and P.trncd=999 and employee_type IN("+emptype+") "
										+ "AND E.EMPNO=T.EMPNO AND T.SRNO=(SELECT MAX(SRNO) FROM EMPTRAN ETA WHERE T.EMPNO=ETA.EMPNO)"+BranchCodeList);
						
						while (rs.next()) {
							tttt += rs.getFloat(1);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					rowhead.createCell((short) 23).setCellValue((int) tttt);
					DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

					Calendar calobj = Calendar.getInstance();
					HSSFRow row = sheet.createRow((short) i);
					row.createCell((short) 0).setCellValue(" ");
					row = sheet.createRow((short) i + 1);
					row.createCell((short) 0).setCellValue("Report Date And Time " + df.format(calobj.getTime()));

				}
				hwb.write(out1);
				out1.close();
				st.close();
				con.close();
			}
		} catch (Exception e) {
			System.out.println("into excel type");
			e.printStackTrace();
		}
	
	}
	
	
	
	public static void sequentialFile_Branches(String PAYREGDT, String imgpath, String filepath, String type, String emptype, String branchCode)
	{


		System.out.println("in new ExcelDao sequentialFile_Branches");
		
		System.out.println("newpayreg11 PAYREGDT: "+PAYREGDT);
		System.out.println("newpayreg11 type: "+type);
		System.out.println("newpayreg11 emptype: "+emptype);

		

		// this code is for constant property see constant.properties
		Properties prop = new Properties();
		try {

			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream stream = classLoader.getResourceAsStream("constant.properties");
			prop.load(stream);
		} catch (Exception e) {
			System.out.println("Error in constant properties Manager " + e);
		}

		RepoartBean repBean = new RepoartBean();

		Connection con = null;
		String BomDt = "";
		String EomDt = "";
		int lastdat = 0;
		lastdat = (int) Calculate.getDays(PAYREGDT);
		System.out.println("maxdt" + lastdat);
		BomDt = ReportDAO.BOM(PAYREGDT);
		EomDt = ReportDAO.EOM(PAYREGDT);
	
		String EmpSql = "";
		String pBrcd1 = "";
		int tot_no_emp = 0;
		int br_tot_no_emp = 0;
	
		try {
				if (type.equalsIgnoreCase("ID")) {

				ReportDAO.OpenCon("", "", "", repBean);
				con = repBean.getCn();
				Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			//	String report_type[] = { "t.PRJ_SRNO", "t.DEPT" };
				String report_type[] = { "t.BRANCH", "t.DEPT" };

				FileOutputStream out1 = new FileOutputStream(new File(filepath));

				HSSFWorkbook hwb = new HSSFWorkbook();
				for (int x = 0; x < report_type.length; x++) {
					tot_no_emp = 0;
					HSSFSheet sheet = hwb.createSheet(x == 0 ? "Project" : "Depertment");
	
					HSSFCellStyle my_style = hwb.createCellStyle();
					HSSFFont my_font = hwb.createFont();
					my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
					my_style.setFont(my_font);

					HSSFRow rowtitle = sheet.createRow((short) 0);
					HSSFCell cell = rowtitle.createCell((short) 10);
					cell.setCellValue(prop.getProperty("companyName"));
					cell.setCellStyle(my_style);
					HSSFRow rowtitle1 = sheet.createRow((short) 1);
					HSSFCell cell1 = rowtitle1.createCell((short) 9);
					cell1.setCellValue(prop.getProperty("addressForReport"));
					cell1.setCellStyle(my_style);
					HSSFRow rowtitle2 = sheet.createRow((short) 2);
					HSSFCell cell2 = rowtitle2.createCell((short) 11);
					cell2.setCellValue(prop.getProperty("contactForReport"));
					cell2.setCellStyle(my_style);
					HSSFRow rowtitle31 = sheet.createRow((short) 3);
					cell2 = rowtitle31.createCell((short) 11);
					cell2.setCellValue(prop.getProperty("mailForReport"));
					cell2.setCellStyle(my_style);
					HSSFRow rowtitle3 = sheet.createRow((short) 4);
					cell2 = rowtitle3.createCell((short) 10);
					cell2.setCellValue("Salary Sheet For The Month Of :- " + PAYREGDT);
					cell2.setCellStyle(my_style);
					HSSFRow rowtitle4 = sheet.createRow((short) 5);
					rowtitle4.createCell((short) 0).setCellValue("");
					HSSFRow rowtitle5 = sheet.createRow((short) 6);
					rowtitle5.createCell((short) 0).setCellValue("");

					HSSFFont blueFont = hwb.createFont();
					blueFont.setColor(HSSFColor.BLUE.index);

					HSSFCellStyle style = hwb.createCellStyle();
					// style.setFont(blueFont);
					style.setFillForegroundColor(HSSFColor.BLUE.index);

					int i = 10;

					float basic_total = 0.0f;
					float lstbasic_total = 0.0f;
					float netpay_total = 0.0f;
					float lstnetpay_total = 0.0f;
	
					float da_total = 0.0f;
					float lstda_total = 0.0f;
					
					
					if(emptype.equalsIgnoreCase("4"))
					{
						emptype="1,2,3";
					}
					else if(emptype.equalsIgnoreCase("5")){
						emptype="0,1,2,3";
					}
				

					String BranchCodeList="";
					if(branchCode.equalsIgnoreCase("0"))
					{
						BranchCodeList = "";
					}
					else 
					{
						//BranchCodeList = " AND t.PRJ_SRNO IN ("+branchCode+")";
						BranchCodeList = " AND t.BRANCH IN ("+branchCode+")";
					}

					
					EmpSql = "select e.EMPCODE, (e.FNAME+' '+e.MNAME+' '+e.LNAME) as NAME, t.acno, t.branch, t.dept, p.NET_AMT "
							+ "from PAYTRAN_STAGE p,EMPTRAN T,empmast E where  p.trncd=999 and p.trndt between '"+ReportDAO.BOM(PAYREGDT)+"' and '"+ReportDAO.EOM(PAYREGDT)+"' "
							+ "and p.EMPNO=T.EMPNO and E.EMPNO = T.EMPNO and t.acno is not null   "
							+ "AND T.SRNO = (SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO) "
							+ "AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM(PAYREGDT)+"') or (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(PAYREGDT)+"' )) "
							+ "and p.Net_amt!=0  order by ACNO ";
				

					System.out.println("Excel SQL : "+EmpSql);
					ResultSet rs = st.executeQuery(EmpSql);

					while (rs.next()) {
						i++;
						String prj_name = null;
						String prj_code = null;
						int dept = 0;
						Connection conn = ConnectionManager.getConnectionTech();

						if (report_type[x].equalsIgnoreCase("t.BRANCH")) {
							// System.out.println("into if");
							String prjquery = "select Site_Name,Project_Code from Project_Sites where SITE_ID = '"
							//		+ rs.getString("PRJ_SRNO") + "'";
									+ rs.getInt("BRANCH") + "'";
							Statement stmt = conn.createStatement();
							ResultSet prj = stmt.executeQuery(prjquery);
							if (prj.next()) {
								prj_name = prj.getString("Site_Name");
								prj_code = prj.getString("Project_Code");
								//prj_code = prj.getString("BRANCH");
							}
							//pBrcd1 = rs.getString("PRJ_SRNO");
							pBrcd1 = rs.getString("BRANCH");
							br_tot_no_emp = 0;

							HSSFRow head1 = sheet.createRow((short) i++);
							HSSFCell cell4 = head1.createCell((short) 0);
							cell4.setCellValue(
									" Employee's Salary List For Project Site : " + prj_name + " (" + prj_code + ")");
							cell4.setCellStyle(my_style);
							prj.close();
						} else {

							Connection ccn = ConnectionManager.getConnection();
							Statement sts = ccn.createStatement();
							String stmt = "select distinct(lkp_srno),lkp_disc from lookup where lkp_code='DEPT' and lkp_srno="
									+ rs.getInt("BRANCH") + "";
							ResultSet rrs = sts.executeQuery(stmt);
							// System.out.println(stmt);
							if (rrs.next()) {
								dept = rrs.getInt("lkp_srno");
								prj_name = rrs.getString("lkp_disc");
							}
							pBrcd1 = Integer.toString(rs.getInt("DEPT"));
							br_tot_no_emp = 0;

							HSSFRow head1 = sheet.createRow((short) i++);
							HSSFCell cell4 = head1.createCell((short) 0);
							cell4.setCellValue(" Employee's Salary List For Deptartment : " + prj_name + "");
							cell4.setCellStyle(my_style);

							ccn.close();
						}

						sheet.setColumnWidth((short) 0, (short) 3000);
						sheet.setColumnWidth((short) 1, (short) 7000);
						sheet.setColumnWidth((short) 4, (short) 4000);
						sheet.setColumnWidth((short) 5, (short) 4000);
						sheet.setColumnWidth((short) 6, (short) 4000);
							
						conn.close();

						HSSFRow rowhead = sheet.createRow((short) i++);

						
						rowhead.createCell((short) 0).setCellValue("EMP CODE.");
						rowhead.createCell((short) 1).setCellValue("EMPLOYEE NAME");
						rowhead.createCell((short) 4).setCellValue("BRANCH");
						rowhead.createCell((short) 5).setCellValue("ACNO");
						rowhead.createCell((short) 6).setCellValue("NET_AMT");
							
					
					//	while (report_type[x].equalsIgnoreCase("t.prj_srno") ? pBrcd1.equals(rs.getString("PRJ_SRNO"))
						while (report_type[x].equalsIgnoreCase("t.BRANCH") ? pBrcd1.equals(rs.getString("BRANCH"))
								: dept == rs.getInt("DEPT")) {

							rowhead = sheet.createRow((short) i++);
							rowhead.createCell((short) 0).setCellValue("" + rs.getString("EMPCODE"));

							rowhead.createCell((short) 1).setCellValue("" + rs.getString("name"));

							basic_total = basic_total + rs.getFloat("BRANCH");
							rowhead.createCell((short) 4).setCellValue((int) rs.getFloat("BRANCH"));

							da_total = da_total + rs.getFloat("ACNO");
							rowhead.createCell((short) 5).setCellValue((int) rs.getFloat("ACNO"));
							
							netpay_total = netpay_total + rs.getFloat("NET_AMT");
							rowhead.createCell((short) 6).setCellValue((int) rs.getFloat("NET_AMT"));

							
							tot_no_emp = tot_no_emp + 1;
							br_tot_no_emp = br_tot_no_emp + 1;
							if (!rs.next()) {
								break;
							}
							if (report_type[x].equalsIgnoreCase("t.BRANCH")) {

								if (!pBrcd1.equals(rs.getString("BRANCH"))) {
									rs.previous();
									break;
								}
							} else {
								if (dept != rs.getInt("DEPT")) {
									rs.previous();

									break;
								}
							}

						}
						rowhead = sheet.createRow((short) i++);
						rowhead.createCell((short) 0).setCellValue("");

						rowhead.createCell((short) 1).setCellValue("");
						rowhead.createCell((short) 3).setCellValue("TOTAL :--");

						rowhead.createCell((short) 4).setCellValue((int) basic_total);
						rowhead.createCell((short) 5).setCellValue(da_total);


						lstbasic_total = lstbasic_total + basic_total;
						basic_total = 0.0f;
						
						lstda_total = lstda_total + da_total;
						da_total = 0.0f;

						rowhead.createCell((short) 27).setCellValue((int) netpay_total);

						

						lstnetpay_total = lstnetpay_total + netpay_total;
						netpay_total = 0.0f;

						

						rowhead = sheet.createRow((short) i++);

						rowhead.createCell((short) 0).setCellValue("Total Employee(s) : " + br_tot_no_emp);

						rowhead = sheet.createRow((short) i++);

						rowhead.createCell((short) 0).setCellValue("");
					}

					HSSFRow rowhead = sheet.createRow((short) i++);

					rowhead.createCell((short) 0).setCellValue("Total Employee(s) : " + tot_no_emp);

					
					rowhead = sheet.createRow((short) i++);
					HSSFCell cell4;
					rowhead.createCell((short) 10).setCellValue("  ");
					rowhead = sheet.createRow((short) i++);

					cell4 = rowhead.createCell((short) 11);
					cell4.setCellValue("------- SUMMARY -------");
					cell4.setCellStyle(my_style);

					rowhead = sheet.createRow((short) i++);

					rowhead.createCell((short) 10).setCellValue("  ");
					rowhead = sheet.createRow((short) i++);

					rowhead.createCell((short) 10).setCellValue("  ");

					cell4 = rowhead.createCell((short) 1);
					cell4.setCellValue("BASIC Total:");
					cell4.setCellStyle(my_style);
					rowhead.createCell((short) 3).setCellValue((int) lstbasic_total);

					cell4 = rowhead.createCell((short) 6);
					cell4.setCellValue("DA Total:");
					cell4.setCellStyle(my_style);
					rowhead.createCell((short) 8).setCellValue(lstda_total);
					
					
					cell4 = rowhead.createCell((short) 21);
					cell4.setCellValue("NET PAY Total:");
					cell4.setCellStyle(my_style);

					float tttt = 0.0f;
			
					rowhead.createCell((short) 23).setCellValue((int) tttt);
					DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

					Calendar calobj = Calendar.getInstance();
					HSSFRow row = sheet.createRow((short) i);
					row.createCell((short) 0).setCellValue(" ");
					row = sheet.createRow((short) i + 1);
					row.createCell((short) 0).setCellValue("Report Date And Time " + df.format(calobj.getTime()));

				}
				hwb.write(out1);
				out1.close();
				st.close();
				con.close();
			}
		} catch (Exception e) {
			System.out.println("into excel type");
			e.printStackTrace();
		}
	
	
	}
	
	public static void salaryCodeWiseRegister(String PAYREGDT, String imgpath, String filepath, String TransactionCode){
	
		System.out.println("in new pay regdao");
		
			// this code is for constant property see constant.properties
		Properties prop = new Properties();
		try {

			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream stream = classLoader.getResourceAsStream("constant.properties");
			prop.load(stream);
		} catch (Exception e) {
			System.out.println("Error in constant properties Manager " + e);
		}

		RepoartBean repBean = new RepoartBean();

		Connection con = null;
		String BomDt = "";
		String EomDt = "";
		BomDt = ReportDAO.BOM(PAYREGDT);
		EomDt = ReportDAO.EOM(PAYREGDT);
		
		String EmpSql = "";
		
		
		try {
			
				if (TransactionCode.equalsIgnoreCase("LOANEMI")) {

				ReportDAO.OpenCon("", "", "", repBean);
				con = repBean.getCn();
				Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
				FileOutputStream out1 = new FileOutputStream(new File(filepath));

				HSSFWorkbook hwb = new HSSFWorkbook();
				
					HSSFSheet sheet = hwb.createSheet("SalaryCodeWise_List");
		
					HSSFCellStyle my_style = hwb.createCellStyle();
					HSSFFont my_font = hwb.createFont();
					my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
					my_style.setFont(my_font);
					my_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
					
					HSSFRow rowtitle = sheet.createRow((short) 0);
					HSSFCell cell = rowtitle.createCell((short) 0);
					cell.setCellValue(prop.getProperty("companyName"));
					cell.setCellStyle(my_style);
					sheet.addMergedRegion(new Region(0,(short)0,0,(short)5));
					
					HSSFRow rowtitle1 = sheet.createRow((short) 1);
					HSSFCell cell1 = rowtitle1.createCell((short) 0);
					cell1.setCellValue(prop.getProperty("addressForReport"));
					cell1.setCellStyle(my_style);
					sheet.addMergedRegion(new Region(1,(short)0,1,(short)5));
					
					HSSFRow rowtitle2 = sheet.createRow((short) 2);
					HSSFCell cell2 = rowtitle2.createCell((short) 0);
					cell2.setCellValue(prop.getProperty("contactForReport"));
					cell2.setCellStyle(my_style);
					sheet.addMergedRegion(new Region(2,(short)0,2,(short)5));
					
					HSSFRow rowtitle31 = sheet.createRow((short) 3);
					cell2 = rowtitle31.createCell((short) 0);
					cell2.setCellValue(prop.getProperty("mailForReport"));
					cell2.setCellStyle(my_style);
					sheet.addMergedRegion(new Region(3,(short)0,3,(short)5));
					
					HSSFRow rowtitle3 = sheet.createRow((short) 4);
					cell2 = rowtitle3.createCell((short) 0);
					cell2.setCellValue("All Loan List For The Month Of :- " + PAYREGDT);
					cell2.setCellStyle(my_style);
					sheet.addMergedRegion(new Region(4,(short)0,4,(short)5));
		
					HSSFFont blueFont = hwb.createFont();
					blueFont.setColor(HSSFColor.BLUE.index);

					HSSFCellStyle style = hwb.createCellStyle();
					style.setFillForegroundColor(HSSFColor.BLUE.index);

					int i = 10;
		
					sheet.setColumnWidth((short) 0, (short) 4000);
					sheet.setColumnWidth((short) 1, (short) 4000);
					sheet.setColumnWidth((short) 2, (short) 8000);
					sheet.setColumnWidth((short) 3, (short) 4000);
					sheet.setColumnWidth((short) 4, (short) 4000);
					sheet.setColumnWidth((short) 5, (short) 4000);
					
					HSSFRow rowhead = sheet.createRow((short) i++);

					int Final_count=0;
					float Final_emiTotalValue=0;
					float emiValue=0;
					float emiTotalValue=0;
					int count=0;
					String TransactCode = "";
					String Trantype = "";
					 
					String ProjectName="SELECT * FROM Project_Sites WHERE SITE_ID IN (SELECT DISTINCT(PRJ_SRNO) FROM DEDMAST) ORDER BY SITE_ID";
					ResultSet rs1 = st1.executeQuery(ProjectName);
					
					while (rs1.next()){
						
						int checkProject= rs1.getInt("SITE_ID");
						String Branch_Name = rs1.getString("SITE_NAME");
				
						rowhead = sheet.createRow((short) i++);
						sheet.addMergedRegion(new Region(i-1,(short)0,i-1,(short)2));
						sheet.addMergedRegion(new Region(i-1,(short)3,i-1,(short)5));
						sheet.setHorizontallyCenter(true);
						
						rowhead.createCell((short) 0).setCellValue("                  "+"BRANCH NAME      :-      "+Branch_Name);
						rowhead.createCell((short) 3).setCellValue("                  "+"BRANCH CODE      :-      "+checkProject);
						
						rowhead = sheet.createRow((short) i++);
						rowhead.createCell((short) 0).setCellValue("SRNO");
						rowhead.createCell((short) 1).setCellValue("EMP CODE.");
						rowhead.createCell((short) 2).setCellValue("EMPLOYEE NAME");
						rowhead.createCell((short) 3).setCellValue("ACCOUNT TYPE");
						rowhead.createCell((short) 4).setCellValue("ACCOUNT NO");
						rowhead.createCell((short) 5).setCellValue("EMI");
						
						System.out.println("bomdt : "+BomDt);
						System.out.println("Eomdt : "+EomDt);
						EmpSql="SELECT E.EMPCODE AS EMPCODE, (E.FNAME+' '+E.MNAME+' '+E.LNAME) AS NAME, D.TRNCD AS TYPE, D.AC_NO AS ACNO, D.AMOUNT AS NETAMT, "
								+ "D.PRJ_SRNO AS BRANCH FROM empmast E, PAYTRAN P, DEDMAST D WHERE E.EMPNO=P.EMPNO AND E.EMPNO=D.EMPNO "
								+ "AND P.TRNCD=D.TRNCD AND D.ACTYN='Y' AND D.PRJ_SRNO="+checkProject+" AND P.TRNDT BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
								+ "UNION "
								+ "SELECT E.EMPCODE AS EMPCODE, (E.FNAME+' '+E.MNAME+' '+E.LNAME) AS NAME, D.TRNCD AS TYPE, D.AC_NO AS ACNO, D.AMOUNT AS NETAMT, "
								+ "D.PRJ_SRNO AS BRANCH FROM empmast E, PAYTRAN_STAGE P, DEDMAST D WHERE E.EMPNO=P.EMPNO AND E.EMPNO=D.EMPNO "
								+ "AND P.TRNCD=D.TRNCD AND D.ACTYN='Y' AND D.PRJ_SRNO="+checkProject+" AND P.TRNDT BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
								+ " ORDER BY D.AC_NO";
						
					ResultSet rs = st.executeQuery(EmpSql);

					emiValue=0;
					emiTotalValue=0;
					count=0;
					
					while (rs.next()) {
						count++;
						 emiValue = Float.parseFloat(rs.getString("NETAMT"));
						 TransactCode = rs.getString("TYPE");

						 System.out.println("TransactCode : "+TransactCode);
						 if(TransactCode.equalsIgnoreCase("244")){
							 Trantype = "HSG";
						}
						 else if(TransactCode.equalsIgnoreCase("245")){
							 Trantype = "VL";  
						 }
						 else if(TransactCode.equalsIgnoreCase("246")){
							 Trantype = "WVL";
						 }
						 else if(TransactCode.equalsIgnoreCase("247")){
							 Trantype = "PLN";
						}
						 else{
							 Trantype="";
						 }
						 
						 	rowhead = sheet.createRow((short) i++);
							rowhead.createCell((short) 0).setCellValue("" + count);
							rowhead.createCell((short) 1).setCellValue("" + rs.getString("EMPCODE"));
							rowhead.createCell((short) 2).setCellValue("" + rs.getString("NAME"));
							rowhead.createCell((short) 3).setCellValue("" + Trantype);
							rowhead.createCell((short) 4).setCellValue("" + rs.getString("ACNO"));
							rowhead.createCell((short) 5).setCellValue("" + emiValue);
				
							emiTotalValue=emiTotalValue+emiValue;
						}
						rowhead = sheet.createRow((short) i++);
						rowhead.createCell((short) 0).setCellValue("TOTAL EMPLOYEE");
						rowhead.createCell((short) 2).setCellValue(""+count);
						rowhead.createCell((short) 5).setCellValue(""+emiTotalValue);
						
						rowhead = sheet.createRow((short) i++);
						rowhead.createCell((short) 0).setCellValue("");
					
						Final_count = Final_count+count; 
						Final_emiTotalValue = Final_emiTotalValue+emiTotalValue;
					}
							
					rowhead = sheet.createRow((short) i++);
					rowhead.createCell((short) 0).setCellValue("TOTAL EMPLOYEE");
					rowhead.createCell((short) 2).setCellValue(""+Final_count);
					rowhead.createCell((short) 5).setCellValue(""+Final_emiTotalValue);
					
					hwb.write(out1);
					out1.close();
					st.close();
				}
				
				
				else{

					ReportDAO.OpenCon("", "", "", repBean);
					con = repBean.getCn();
					
					Statement stnew = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					String TransactionCodeQuery ="SELECT DISC FROM CDMAST WHERE TRNCD="+TransactionCode;
					ResultSet rsnew = stnew.executeQuery(TransactionCodeQuery);
					String CodeDisc = "";
					while(rsnew.next()){
						CodeDisc=rsnew.getString("DISC");	
					}

					Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
					FileOutputStream out1 = new FileOutputStream(new File(filepath));

					HSSFWorkbook hwb = new HSSFWorkbook();
						HSSFSheet sheet = hwb.createSheet("SalaryCodeWise_List");
						
						HSSFCellStyle my_style = hwb.createCellStyle();
						HSSFFont my_font = hwb.createFont();
						my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
						my_style.setFont(my_font);
						my_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
						
						HSSFRow rowtitle = sheet.createRow((short) 0);
						HSSFCell cell = rowtitle.createCell((short) 0);
						cell.setCellValue(prop.getProperty("companyName"));
						cell.setCellStyle(my_style);
						sheet.addMergedRegion(new Region(0,(short)0,0,(short)3));
						
						HSSFRow rowtitle1 = sheet.createRow((short) 1);
						HSSFCell cell1 = rowtitle1.createCell((short) 0);
						cell1.setCellValue(prop.getProperty("addressForReport"));
						cell1.setCellStyle(my_style);
						sheet.addMergedRegion(new Region(1,(short)0,1,(short)3));
						
						HSSFRow rowtitle2 = sheet.createRow((short) 2);
						HSSFCell cell2 = rowtitle2.createCell((short) 0);
						cell2.setCellValue(prop.getProperty("contactForReport"));
						cell2.setCellStyle(my_style);
						sheet.addMergedRegion(new Region(2,(short)0,2,(short)3));
						
						HSSFRow rowtitle31 = sheet.createRow((short) 3);
						cell2 = rowtitle31.createCell((short) 0);
						cell2.setCellValue(prop.getProperty("mailForReport"));
						cell2.setCellStyle(my_style);
						sheet.addMergedRegion(new Region(3,(short)0,3,(short)3));
						
						HSSFRow rowtitle3 = sheet.createRow((short) 4);
						cell2 = rowtitle3.createCell((short) 0);
						cell2.setCellValue("Salary Code Wise Sheet For The Month Of :- " + PAYREGDT);
						cell2.setCellStyle(my_style);
						sheet.addMergedRegion(new Region(4,(short)0,4,(short)3));
						
			
						HSSFFont blueFont = hwb.createFont();
						blueFont.setColor(HSSFColor.BLUE.index);

						HSSFCellStyle style = hwb.createCellStyle();
						style.setFillForegroundColor(HSSFColor.BLUE.index);

						int i = 10;
			
						sheet.setColumnWidth((short) 0, (short) 4000);
						sheet.setColumnWidth((short) 1, (short) 4000);
						sheet.setColumnWidth((short) 2, (short) 8000);
						sheet.setColumnWidth((short) 3, (short) 5000);
						
						HSSFRow rowhead = sheet.createRow((short) i++);

						int Final_count=0;
						float Final_netAmtTotalValue=0;
						float netAmtValue=0;
						float netAmtTotalValue=0;
						int count=0;
						String TransactCode = "";
						 
						String ProjectName="SELECT * FROM Project_Sites ORDER BY SITE_ID";
						ResultSet rs1 = st1.executeQuery(ProjectName);
						
						while (rs1.next()){
						
							int checkProject= rs1.getInt("SITE_ID");
							String Branch_Name = rs1.getString("SITE_NAME");
					
							rowhead = sheet.createRow((short) i++);
							sheet.addMergedRegion(new Region(i-1,(short)0,i-1,(short)2));
							sheet.addMergedRegion(new Region(i-1,(short)3,i-1,(short)3));
							sheet.setHorizontallyCenter(true);
							
							rowhead.createCell((short) 0).setCellValue("                  "+"BRANCH NAME      :-      "+Branch_Name);
							rowhead.createCell((short) 3).setCellValue("          "+"BRANCH CODE    :-  "+checkProject);
							
							rowhead = sheet.createRow((short) i++);
							rowhead.createCell((short) 0).setCellValue("SRNO");
							rowhead.createCell((short) 1).setCellValue("EMP CODE.");
							rowhead.createCell((short) 2).setCellValue("EMPLOYEE NAME");
							rowhead.createCell((short) 3).setCellValue("ACTUAL_"+CodeDisc);
							
							
							EmpSql="SELECT E.EMPNO, E.EMPCODE AS EMPCODE, (E.FNAME+' '+E.MNAME+' '+E.LNAME) AS NAME, P.INP_AMT AS INPAMT, P.NET_AMT AS NETAMT "
									+ "FROM empmast E, PAYTRAN P, EMPTRAN ET WHERE E.EMPNO=P.EMPNO AND E.EMPNO=ET.EMPNO AND ET.EMPNO=P.EMPNO "
									+ "AND P.TRNDT BETWEEN '"+BomDt+"' AND '"+EomDt+"' AND ET.SRNO=(SELECT MAX(SRNO) FROM EMPTRAN ETA WHERE ET.EMPNO=ETA.EMPNO) "
									+ "AND P.TRNCD="+TransactionCode+" AND ET.PRJ_SRNO="+checkProject+" "
									+ "UNION "
									+ "SELECT E.EMPNO, E.EMPCODE AS EMPCODE, (E.FNAME+' '+E.MNAME+' '+E.LNAME) AS NAME, P.INP_AMT AS INPAMT, P.NET_AMT AS NETAMT "
									+ "FROM empmast E, PAYTRAN_STAGE P, EMPTRAN ET WHERE E.EMPNO=P.EMPNO AND E.EMPNO=ET.EMPNO AND ET.EMPNO=P.EMPNO "
									+ "AND P.TRNDT BETWEEN '"+BomDt+"' AND '"+EomDt+"' AND ET.SRNO=(SELECT MAX(SRNO) FROM EMPTRAN ETA WHERE ET.EMPNO=ETA.EMPNO) "
									+ "AND P.TRNCD="+TransactionCode+" AND ET.PRJ_SRNO="+checkProject+" ORDER BY E.EMPCODE";
		
						ResultSet rs = st.executeQuery(EmpSql);

						netAmtValue=0;
						netAmtTotalValue=0;
						count=0;
						
						while (rs.next()) {
							count++;
							netAmtValue = Float.parseFloat(rs.getString("NETAMT"));
													 
							 	rowhead = sheet.createRow((short) i++);
								rowhead.createCell((short) 0).setCellValue("" + count);
								rowhead.createCell((short) 1).setCellValue("" + rs.getString("EMPCODE"));
								rowhead.createCell((short) 2).setCellValue("" + rs.getString("NAME"));
								rowhead.createCell((short) 3).setCellValue("" + netAmtValue);
						
								netAmtTotalValue=netAmtTotalValue+netAmtValue;
							}
							rowhead = sheet.createRow((short) i++);
							rowhead.createCell((short) 0).setCellValue("TOTAL EMPLOYEE");
							rowhead.createCell((short) 2).setCellValue(""+count);
							rowhead.createCell((short) 3).setCellValue(""+netAmtTotalValue);
							
							rowhead = sheet.createRow((short) i++);
							rowhead.createCell((short) 0).setCellValue("");
						
							Final_count = Final_count+count; 
							Final_netAmtTotalValue = Final_netAmtTotalValue+netAmtTotalValue;
						}
								
						rowhead = sheet.createRow((short) i++);
						rowhead.createCell((short) 0).setCellValue("TOTAL EMPLOYEE");
						rowhead.createCell((short) 2).setCellValue(""+Final_count);
						rowhead.createCell((short) 3).setCellValue(""+Final_netAmtTotalValue);
						
						hwb.write(out1);
						out1.close();
						st.close();
				}
				con.close();
			
		} catch (Exception e) {
			System.out.println("into excel type");
			e.printStackTrace();
		}
	}

	public static void EMPLIST(String date, String date1, String type,String filepath, String imagepath)
	{
		
		try
		{
			System.out.println("Into Generalised_EmpListDAO.....");
			RepoartBean repBean  = new RepoartBean();
			
			LookupHandler lkh=new LookupHandler();
			Connection con =null;
			System.out.println( filepath);
		FileOutputStream out1 = new FileOutputStream(new File(filepath));
		HSSFWorkbook hwb=new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("Generalise Emplist");
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
		sheet.setColumnWidth((short)0, (short)3000);
		sheet.setColumnWidth((short)1, (short)3000);
		sheet.setColumnWidth((short)4, (short)6000);
		sheet.setColumnWidth((short)5, (short)3000);
		sheet.setColumnWidth((short)6, (short)4000);
		sheet.setColumnWidth((short)7, (short)4000);
		sheet.setColumnWidth((short)8, (short)4000);
		sheet.setColumnWidth((short)9, (short)3000);
		sheet.setColumnWidth((short)10, (short)4000);
		sheet.setColumnWidth((short)11, (short)4000);
		sheet.setColumnWidth((short)12, (short)5000);
		sheet.setColumnWidth((short)13, (short)20000);
		sheet.setColumnWidth((short)14, (short)4000);
		sheet.setColumnWidth((short)15, (short)7000);
		sheet.setColumnWidth((short)16, (short)7000);
		sheet.setColumnWidth((short)17, (short)7000);
		sheet.setColumnWidth((short)18, (short)5000);
		sheet.setColumnWidth((short)19, (short)4000);
		sheet.setColumnWidth((short)20, (short)5000);




        HSSFCellStyle my_style = hwb.createCellStyle();
        HSSFCellStyle my_style1 = hwb.createCellStyle();

        HSSFFont my_font=hwb.createFont();
        my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        my_style.setFont(my_font);
      
        
		HSSFRow rowtitle=   sheet.createRow((short)0);
		HSSFCell cell = rowtitle.createCell((short) 9);
		
		cell.setCellValue("THE BUSINESS CO.OP BANK LTD.");
		cell.setCellStyle(my_style);
		HSSFRow rowtitle1=   sheet.createRow((short)1);
		HSSFCell cell1 = rowtitle1.createCell((short) 7);
		cell1.setCellValue("Rajan Complex Arcade, Datta Mandir, Nashik Road, Nashik - 422101");
		cell1.setCellStyle(my_style);
		HSSFRow rowtitle2=   sheet.createRow((short)2);
		HSSFCell cell2 = rowtitle2.createCell((short) 9);
		cell2.setCellValue("Tel : 0253-2406100, 2469545");
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle31=   sheet.createRow((short)3);
		cell2 = rowtitle31.createCell((short) 9);
				cell2.setCellValue("Email :");
				cell2.setCellStyle(my_style);
		HSSFRow rowtitle3=   sheet.createRow((short)4);
		cell2=rowtitle3.createCell((short) 2);
		cell2.setCellValue("");
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle4=   sheet.createRow((short)5);
		rowtitle4.createCell((short) 0).setCellValue("");
		HSSFRow rowtitle5=   sheet.createRow((short)6);
		rowtitle5.createCell((short) 0).setCellValue("");
		
		HSSFFont blueFont = hwb.createFont();
		blueFont.setColor(HSSFColor.BLUE.index);
		
		HSSFCellStyle style = hwb.createCellStyle();
		//style.setFont(blueFont);
		style.setFillForegroundColor(HSSFColor.BLUE.index);
		
		
		
		
		HSSFRow head=   sheet.createRow((short)7);
		head.createCell((short) 0).setCellValue("");
		HSSFRow heading=   sheet.createRow((short)8);
		HSSFCell cell3 = heading.createCell((short) 0); 

		cell3.setCellValue("");
		cell3.setCellStyle(my_style1);
		HSSFRow rowhead=   sheet.createRow((short)9);
        sheet.createFreezePane( 0, 10, 0, 10 );
       
        my_style1.setAlignment((short) 2);
        my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        my_style1.setFont(my_font);
        
		cell3=rowhead.createCell((short) 0);
		cell3.setCellValue("SR.NO");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 1);
		cell3.setCellValue("Emp Code");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 3);
		cell3.setCellValue("Employee Name");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 5);
		cell3.setCellValue("Gender");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 6);
		cell3.setCellValue("DOB");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 7);
		cell3.setCellValue("DOJ");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 8);
		cell3.setCellValue("DOL");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 9);
		cell3.setCellValue("AGE");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short)10);
		cell3.setCellValue("Super Ann");
		cell3.setCellStyle(my_style1);

		cell3=rowhead.createCell((short) 11);
		cell3.setCellValue("PAN NO.");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 12);
		cell3.setCellValue("PF NO.");
		cell3.setCellStyle(my_style1);
		
		cell3=rowhead.createCell((short) 13);
		cell3.setCellValue("Project name");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 14);
		cell3.setCellValue("Project Code");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 15);
		cell3.setCellValue("Designation");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 16);
		cell3.setCellValue("Department");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 17);
		cell3.setCellValue("BANK");
		cell3.setCellStyle(my_style1);
		
		cell3=rowhead.createCell((short) 18);
		cell3.setCellValue("Account No");
		cell3.setCellStyle(my_style1);
		
		cell3=rowhead.createCell((short) 19);
		cell3.setCellValue("Status");
		cell3.setCellStyle(my_style1);
		
		cell3=rowhead.createCell((short) 20);
		cell3.setCellValue(" Monthly CTC");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 21);
		cell3.setCellValue("EMPNO");
		cell3.setCellStyle(my_style1);
		
		
		
		
		
		ReportDAO.OpenCon("", "", "",repBean);
		con = repBean.getCn();	
		Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		int i=12;
		int count=1;
		ResultSet rs=st.executeQuery("SELECT distinct E.EMPNO,E.EMPCODE,E.FNAME,E.MNAME,E.LNAME,E.SALUTE,E.DOJ,E.DOL,	DATEADD(YEAR,60,E.DOB) as DATE60,DATEDIFF(DAY, E.DOB, GetDate()) / 365.25 as DATEYEAR,E.DOB,E.gender,E.PANNO,E.PFNO,E.STATUS," +
				"				T.PRJ_SRNO,T.DEPT,T.DESIG,T.BANK_NAME,T.ACNO,P.INP_AMT as CTC FROM EMPMAST E,emptran T  " +
				"  left join PAYTRAN P on P.EMPNO=T.EMPNO  Where E.EMPNO = T.EMPNO" +
				"				 and T.SRNO = (select MAX(et.SRNO) from emptran et where et.empno =E.EMPNO)	and P.trncd=199			order by E.EMPNO ");
		
		while(rs.next())
		{
			HSSFRow row = sheet.createRow((short)i++);
			
			row.createCell((short) 0).setCellValue(""+(count++));
			row.createCell((short) 1).setCellValue(""+rs.getString("empcode"));
			row.createCell((short) 2).setCellValue(""+rs.getString("FNAME")+" "+rs.getString("MNAME")+" "+rs.getString("LNAME"));
			
			row.createCell((short) 5).setCellValue(""+(rs.getString("gender").equalsIgnoreCase("m")?"MALE":"FEMALE"));
			row.createCell((short) 6).setCellValue(""+(rs.getDate("DOB")==null?"":sdf.format(rs.getDate("DOB"))));
		
			row.createCell((short) 7).setCellValue(""+sdf.format(rs.getDate("DOJ")));
			row.createCell((short) 8).setCellValue(""+rs.getString("DOL")==null?"":rs.getString("DOL"));
			row.createCell((short) 9).setCellValue(rs.getInt("DATEYEAR"));
			row.createCell((short)10).setCellValue(""+(rs.getDate("DATE60")==null?"":sdf.format(rs.getDate("DATE60"))));
			row.createCell((short) 11).setCellValue(""+rs.getString("PANNO")==null?"":rs.getString("PANNO"));
			row.createCell((short) 12).setCellValue(""+rs.getString("PFNO")==null?"":rs.getString("PFNO"));
			String prjquery = "select Site_Name,Project_Code from Project_Sites where SITE_ID = '"+rs.getString("PRJ_SRNO")+"'";
			Connection conn = ConnectionManager.getConnectionTech();
			Statement stmt = conn.createStatement();
			ResultSet prj = stmt.executeQuery(prjquery);
			String prj_name="";
			String prj_code="";
			
			if(prj.next()){
				prj_name = prj.getString("Site_Name");
				prj_code = prj.getString("Project_Code");
			}
			
			
			row.createCell((short) 13).setCellValue(prj_name);
			row.createCell((short) 14).setCellValue(prj_code);
			row.createCell((short) 15).setCellValue(lkh.getLKP_Desc("DESIG",rs.getInt("DESIG")));
			row.createCell((short)16).setCellValue(lkh.getLKP_Desc("DEPT",rs.getInt("DEPT")));
			row.createCell((short)17).setCellValue(lkh.getLKP_Desc("BANK",rs.getInt("BANK_NAME")));
			row.createCell((short)18).setCellValue(rs.getString("ACNO"));


			row.createCell((short)19).setCellValue((rs.getString("status")).equalsIgnoreCase("A")?"Active":"Non-Active");
			
			row.createCell((short)20).setCellValue(rs.getString("CTC"));
			row.createCell((short)21).setCellValue(rs.getString("EMPNO"));
			
			
			
			
			
			
			
		}
		
		hwb.write(out1);
		out1.close();
		st.close();
	    con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// For Bonus List Details...28.08
	
	public static void BonusListExcel(String type,String reportType,String branch,String desig,String Year,String filepath, String imagepath)
	{
		
		try
		{
			System.out.println("Into BonusListExcel.....");
			RepoartBean repBean  = new RepoartBean();
			
			LookupHandler lkh=new LookupHandler();
			Connection con =null;
		//	System.out.println( filepath);
		FileOutputStream out1 = new FileOutputStream(new File(filepath));
		HSSFWorkbook hwb=new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("Bonus Details");
		
		sheet.setColumnWidth((short)0, (short)3000);
		sheet.setColumnWidth((short)1, (short)4000);
		sheet.setColumnWidth((short)2, (short)11000);
		sheet.setColumnWidth((short)3, (short)4000);
		sheet.setColumnWidth((short)4, (short)4000);
		sheet.setColumnWidth((short)5, (short)4000);
		sheet.setColumnWidth((short)6, (short)4000);
		sheet.setColumnWidth((short)7, (short)4000);
		sheet.setColumnWidth((short)8, (short)4000);
		sheet.setColumnWidth((short)9, (short)4000);
		sheet.setColumnWidth((short)10, (short)4000);
		sheet.setColumnWidth((short)11, (short)4000);
		sheet.setColumnWidth((short)12, (short)4000);
		sheet.setColumnWidth((short)13, (short)4000);
		sheet.setColumnWidth((short)14, (short)4000);
		sheet.setColumnWidth((short)15, (short)4000);
		sheet.setColumnWidth((short)16, (short)4000);
		sheet.setColumnWidth((short)17, (short)4000);




        HSSFCellStyle my_style = hwb.createCellStyle();
        HSSFCellStyle my_style1 = hwb.createCellStyle();

        HSSFFont my_font=hwb.createFont();
        my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        my_style.setFont(my_font);
        
        my_style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
      //  my_style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
       
        
		HSSFRow rowtitle=   sheet.createRow((short)0);
		HSSFCell cell = rowtitle.createCell((short) 9);
		
		cell.setCellValue("THE BUSINESS CO.OP BANK LTD.");
		cell.setCellStyle(my_style);
		HSSFRow rowtitle1=   sheet.createRow((short)1);
		HSSFCell cell1 = rowtitle1.createCell((short) 7);
		cell1.setCellValue("Rajan Complex Arcade, Datta Mandir, Nashik Road, Nashik - 422101");
		cell1.setCellStyle(my_style);
		HSSFRow rowtitle2=   sheet.createRow((short)2);
		HSSFCell cell2 = rowtitle2.createCell((short) 9);
		cell2.setCellValue("Tel : 0253-2406100, 2469545");
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle31=   sheet.createRow((short)3);
		cell2 = rowtitle31.createCell((short) 9);
				cell2.setCellValue("Email :");
				cell2.setCellStyle(my_style);
		HSSFRow rowtitle3=   sheet.createRow((short)4);
		cell2=rowtitle3.createCell((short) 2);
		cell2.setCellValue("");
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle4=   sheet.createRow((short)5);
		rowtitle4.createCell((short) 0).setCellValue("");
		HSSFRow rowtitle5=   sheet.createRow((short)6);
		rowtitle5.createCell((short) 0).setCellValue("");
		
		HSSFFont blueFont = hwb.createFont();
		blueFont.setColor(HSSFColor.BLUE.index);
		
		HSSFCellStyle style = hwb.createCellStyle();
		style.setFillForegroundColor(HSSFColor.BLUE.index);
		
		
		
		
		HSSFRow head=   sheet.createRow((short)7);
		head.createCell((short) 0).setCellValue("");
		HSSFRow heading=   sheet.createRow((short)8);
		HSSFCell cell3 = heading.createCell((short) 0); 

		cell3.setCellValue("");
		cell3.setCellStyle(my_style1);
		HSSFRow rowhead=   sheet.createRow((short)9);
        sheet.createFreezePane( 0, 10, 0, 10 );
       
        my_style1.setAlignment((short) 2);
        my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        my_style1.setFont(my_font);
        my_style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
		cell3=rowhead.createCell((short) 0);
		cell3.setCellValue("SR.NO");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 1);
		cell3.setCellValue("EMP CODE");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 2);
		cell3.setCellValue("EMPLOYEE NAME");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 3);
		cell3.setCellValue("APR AMT");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 4);
		cell3.setCellValue("MAY AMT");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 5);
		cell3.setCellValue("JUN AMT");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 6);
		cell3.setCellValue("JUL AMT");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 7);
		cell3.setCellValue("AUG AMT");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short)8);
		cell3.setCellValue("SEP AMT");
		cell3.setCellStyle(my_style1);

		cell3=rowhead.createCell((short) 9);
		cell3.setCellValue("OCT AMT");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 10);
		cell3.setCellValue("NOV AMT");
		cell3.setCellStyle(my_style1);
		
		cell3=rowhead.createCell((short) 11);
		cell3.setCellValue("DEC AMT");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 12);
		cell3.setCellValue("JAN AMT");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 13);
		cell3.setCellValue("FEB AMT");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 14);
		cell3.setCellValue("MAR AMT");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 15);
		cell3.setCellValue("PERCENT");
		cell3.setCellStyle(my_style1);
		
		cell3=rowhead.createCell((short) 16);
		cell3.setCellValue("AMT FOR BONUS");
		cell3.setCellStyle(my_style1);
		
		cell3=rowhead.createCell((short) 17);
		cell3.setCellValue("BONUS");
		cell3.setCellStyle(my_style1);
		
		cell3=rowhead.createCell((short) 18);
		cell3.setCellValue(" STATUS");
		cell3.setCellStyle(my_style1);
		
		
		ReportDAO.OpenCon("", "", "",repBean);
		con = repBean.getCn();	
		Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = null;
		String SQL = "";
		int i=12;
		int count=0;
		/*ResultSet rs=st.executeQuery("SELECT e.empno,e.empcode,Rtrim(e.fname) + ' ' + Rtrim(e.mname) + ' '+ Rtrim(e.lname) "+ 
									 "AS NAME,b.* FROM   empmast e,bonuscal b "+
							         "WHERE  b.empno = e.empno AND b.status = 'P' order by b.empno");*/
		if(reportType.equals("1")){
			System.out.println("in ALL");
				SQL = ""
				+ "SELECT e.empcode,Rtrim(e.fname) + ' ' + Rtrim(e.mname) + ' '+ Rtrim(e.lname) "
				+ "AS NAME,b.* FROM   empmast e,bonuscal b "
				+ "WHERE  b.empno = e.empno and year='"+Year+"'  and b.status='P' "
				+ "order by e.EMPCODE";
		}else if(reportType.equals("2")){
			System.out.println("in BRANCHWISE");
			 SQL = ""
					+ "SELECT e.empcode,Rtrim(e.fname) + ' ' + Rtrim(e.mname) + ' '+ Rtrim(e.lname) "
					+ "AS NAME,b.* FROM   empmast e,bonuscal b ,emptran t "
					+ "WHERE  b.empno = e.empno and year='"+Year+"'   and b.status='P' "
					+ "and e.EMPNO=t.EMPNO and t.BRANCH="+branch+" "
					+ "and t.SRNO=(select MAX(t1.srno) from EMPTRAN t1 where t1.EMPNO=t.EMPNO) "
					+ "order by e.EMPCODE";
			
		}
		else if(reportType.equals("3")){
			System.out.println("in DESIGWISE");
			SQL = ""
					+ "SELECT e.empcode,Rtrim(e.fname) + ' ' + Rtrim(e.mname) + ' '+ Rtrim(e.lname) "
					+ "AS NAME,b.* FROM   empmast e,bonuscal b ,emptran t "
					+ "WHERE  b.empno = e.empno and year='"+Year+"'  and b.status='P' "
					+ "and e.EMPNO=t.EMPNO  "
					+ "and t.DESIG = "+desig+" "
					+ "and t.SRNO=(select MAX(t1.srno) from EMPTRAN t1 where t1.EMPNO=t.EMPNO) "
					+ "order by e.EMPCODE";
			
		}
				rs = st.executeQuery(SQL);
		System.out.println("query for details  "+SQL);
		int srno=0;
		Integer totatlBonus = 0;
		while(rs.next())
		{
			count++;
			srno++;
			HSSFRow row = sheet.createRow((short)i++);
			
			row.createCell((short) 0).setCellValue(""+srno);
			row.createCell((short) 1).setCellValue(""+rs.getString("empcode"));
			row.createCell((short) 2).setCellValue(""+rs.getString("NAME"));
			
		//	row.createCell((short) 3).setCellValue(""+(rs.getString("site_id")));
			row.createCell((short) 3).setCellValue(""+(rs.getString("apramt")));
		
			row.createCell((short) 4).setCellValue(""+rs.getString("mayamt"));
			row.createCell((short) 5).setCellValue(""+rs.getString("junamt"));
			row.createCell((short) 6).setCellValue(rs.getString("julamt"));
			row.createCell((short) 7).setCellValue(""+(rs.getString("augamt")));
			row.createCell((short) 8).setCellValue(""+rs.getString("sepamt"));
			row.createCell((short) 9).setCellValue(""+rs.getString("octamt"));
			
			
			row.createCell((short) 10).setCellValue(""+rs.getString("novamt"));
			row.createCell((short) 11).setCellValue(""+rs.getString("decamt"));
			row.createCell((short) 12).setCellValue(""+rs.getString("janamt"));
			row.createCell((short) 13).setCellValue(""+rs.getString("febamt"));
			row.createCell((short) 14).setCellValue(""+rs.getString("maramt"));
			row.createCell((short) 15).setCellValue(rs.getString("bonpercent"));


			row.createCell((short) 16).setCellValue((rs.getString("amtforbonus")));
			
			row.createCell((short) 17).setCellValue(rs.getInt("bonus"));
			totatlBonus = totatlBonus + rs.getInt("bonus");
			row.createCell((short) 18).setCellValue(rs.getString("status"));
			
		}
		HSSFRow row = sheet.createRow((short) i);
		row.createCell((short) 0).setCellValue(" ");
		  row = sheet.createRow((short) i+1);
		
		row.createCell((short) 0).setCellValue("Total Employee : =  " +(count++) );
		row.createCell((short) 16).setCellValue("Bonus Total : = ");
		row.createCell((short) 17).setCellValue(""+totatlBonus);
		
		hwb.write(out1);
		out1.close();
		st.close();
	    con.close();

		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	public static void pflistexcel(String PAYREGDT,String type,String filepath,String imgpath)
	{
		Properties prop = new Properties();
		int brtot[] = new int[15];
		 int ALLbrtot[] = new int[15];
		 int PBRCD = 0;
		 int gross = 0;
		
	     try
	     {
		
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream stream = classLoader.getResourceAsStream("constant.properties");
			prop.load(stream);
	     }
	     catch (Exception e) {System.out.println("Error in constant properties Manager "+e);}
	     
		try
		{
			System.out.println("Into Generalised_EmpListDAO.....");
			RepoartBean repBean  = new RepoartBean();
			
			LookupHandler lkh=new LookupHandler();
			Connection con =null;
			System.out.println( filepath);
		FileOutputStream out1 = new FileOutputStream(new File(filepath));
		HSSFWorkbook hwb=new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("EmpPFlist");
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
		System.out.println("1111.....");
		
		sheet.setColumnWidth((short)0, (short)2000);
		sheet.setColumnWidth((short)1, (short)10000);
		sheet.setColumnWidth((short)2, (short)7000);
		sheet.setColumnWidth((short)3, (short)4000);
		sheet.setColumnWidth((short)4, (short)4000);
		sheet.setColumnWidth((short)5, (short)4000);
		sheet.setColumnWidth((short)6, (short)5000);
		sheet.setColumnWidth((short)7, (short)5000);
		sheet.setColumnWidth((short)8, (short)4000);
		sheet.setColumnWidth((short)9, (short)5000);
		sheet.setColumnWidth((short)10, (short)5000);
		sheet.setColumnWidth((short)11, (short)4000);
		sheet.setColumnWidth((short)12, (short)4000);
		sheet.setColumnWidth((short)13, (short)6000);
		sheet.setColumnWidth((short)14, (short)5000);
		sheet.setColumnWidth((short)15, (short)5000);
		/*sheet.setColumnWidth((short)17, (short)7000);
		sheet.setColumnWidth((short)18, (short)5000);
		sheet.setColumnWidth((short)19, (short)4000);
		sheet.setColumnWidth((short)20, (short)5000);
*/



       HSSFCellStyle my_style = hwb.createCellStyle();
       HSSFCellStyle my_style1 = hwb.createCellStyle();

       HSSFFont my_font=hwb.createFont();
       my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
       my_style.setFont(my_font);
     
       
		HSSFRow rowtitle=   sheet.createRow((short)0);
		HSSFCell cell = rowtitle.createCell((short) 9);
		
	//	System.out.println("2222.....");
		
		cell.setCellValue(prop.getProperty("bankName1"));
		cell.setCellStyle(my_style);
		HSSFRow rowtitle1=   sheet.createRow((short)1);
		HSSFCell cell1 = rowtitle1.createCell((short) 7);
		cell1.setCellValue(prop.getProperty("addressForReport"));
		cell1.setCellStyle(my_style);
		HSSFRow rowtitle2=   sheet.createRow((short)2);
		HSSFCell cell2 = rowtitle2.createCell((short) 9);
		cell2.setCellValue(prop.getProperty("contactForReport"));
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle31=   sheet.createRow((short)3);
		cell2 = rowtitle31.createCell((short) 9);
		cell2.setCellValue(	prop.getProperty("mailForReport"));
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle3=   sheet.createRow((short)4);
		cell2=rowtitle3.createCell((short) 2);
		cell2.setCellValue("");
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle4=   sheet.createRow((short)5);
		rowtitle4.createCell((short) 0).setCellValue("");
		HSSFRow rowtitle5=   sheet.createRow((short)6);
		rowtitle5.createCell((short) 0).setCellValue("");
		
		
		HSSFFont blueFont = hwb.createFont();
		blueFont.setColor(HSSFColor.BLUE.index);
		
		HSSFCellStyle style = hwb.createCellStyle();
		//style.setFont(blueFont);
		style.setFillForegroundColor(HSSFColor.BLUE.index);
		
		
		HSSFRow head=   sheet.createRow((short)7);
		head.createCell((short) 0).setCellValue("");
		HSSFRow heading=   sheet.createRow((short)8);
		HSSFCell cell3 = heading.createCell((short) 0); 

		cell3.setCellValue("");
		cell3.setCellStyle(my_style1);
		HSSFRow rowhead=   sheet.createRow((short)9);
       sheet.createFreezePane( 0, 10, 0, 10 );
      
       my_style1.setAlignment((short) 2);
       my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
       my_style1.setFont(my_font);
       
		cell3=rowhead.createCell((short) 0);
		cell3.setCellValue("SR NO");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 1);
		cell3.setCellValue("EMPNAME");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 2);
		cell3.setCellValue("FATHER/HUSBAND'S NAME");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 3);
		cell3.setCellValue("DOB");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 4);
		cell3.setCellValue("DOJ");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 5);
		cell3.setCellValue("UAN NO");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 6);
		cell3.setCellValue("P.F No");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 7);
		cell3.setCellValue("ADHAAR CARDNO");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short)8);
		cell3.setCellValue("PAN CARD NO");
		cell3.setCellStyle(my_style1);

		cell3=rowhead.createCell((short) 9);
		cell3.setCellValue("BANK A/C  NO");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 10);
		cell3.setCellValue("IFSC CODE NO");
		cell3.setCellStyle(my_style1);
		
		/*cell3=rowhead.createCell((short) 11);
		cell3.setCellValue("MOBILE NO");*/
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 12);
		cell3.setCellValue("NCP DAYS");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 13);
		cell3.setCellValue("BASIC +DA");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 14);
		cell3.setCellValue("PF DED. AMT");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 15);
		cell3.setCellValue("GROSS. AMT");
		cell3.setCellStyle(my_style1);
		/*cell3=rowhead.createCell((short) 17);
		cell3.setCellValue("BANK");
		cell3.setCellStyle(my_style1);
		
		cell3=rowhead.createCell((short) 18);
		cell3.setCellValue("Account No");
		cell3.setCellStyle(my_style1);
		
		cell3=rowhead.createCell((short) 19);
		cell3.setCellValue("Status");
		cell3.setCellStyle(my_style1);
		
		cell3=rowhead.createCell((short) 20);
		cell3.setCellValue(" Monthly CTC");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 21);
		cell3.setCellValue("EMPNO");
		cell3.setCellStyle(my_style1);*/
		
		
		
		
		
		ReportDAO.OpenCon("", "", "",repBean);
		con = repBean.getCn();	
		Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		System.out.println("3333.....");
		
		int i=16;
		int count=1;
		
		ResultSet rs=st.executeQuery("select e.salute ,e.dob, e.empno, e.empcode,e.fname,e.mname,e.lname,e.pfno, e.DOJ,  "+
				"e.AADHAARNUM,e.PANNO,t.ACNO,t.PRJ_SRNO, e.IDENTY, (SELECT ( INP_AMT )  FROM   paytran_stage  WHERE  trncd IN ( 999 )  AND TRNDT BETWEEN '"+ReportDAO.BOM(PAYREGDT)+ "' and '"+ReportDAO.EOM(PAYREGDT)+ "' "+
	              " AND empno = e.empno) AS gross,  case when exists (select f3 from OTHERDETAIL where EMPNO=e.empno) then  "+
					"(select f3 from OTHERDETAIL where EMPNO=e.empno) else (SELECT '' AS ifsc) END AS 'ifsc', "+
					" CASE WHEN  "+
					"EXISTS(SELECT sum(NET_AMT) FROM   paytran WHERE TRNCD in (101,102) AND TRNDT BETWEEN '"+ReportDAO.BOM(PAYREGDT)+ "' and '"+ReportDAO.EOM(PAYREGDT)+ "' "+ 
					"AND EMPNO = e.EMPNO)   	THEN	(SELECT sum(NET_AMT) FROM   paytran WHERE TRNCD in (101,102) AND TRNDT BETWEEN "+ 
					" '"+ReportDAO.BOM(PAYREGDT)+ "' and '"+ReportDAO.EOM(PAYREGDT)+ "' AND EMPNO = e.EMPNO) ELSE	(SELECT 0 AS BASIC_DA) END AS 'BASIC_DA', (SELECT (NET_AMT) "+ 
					 "FROM   paytran WHERE TRNCD in (201) AND TRNDT BETWEEN '"+ReportDAO.BOM(PAYREGDT)+ "' and '"+ReportDAO.EOM(PAYREGDT)+ "' AND EMPNO = e.EMPNO) AS PF , "+
					 " CASE WHEN EXISTS(SELECT (NET_AMT) FROM   paytran WHERE TRNCD in (301) AND TRNDT BETWEEN '"+ReportDAO.BOM(PAYREGDT)+ "' and '"+ReportDAO.EOM(PAYREGDT)+ "' "+
					  " AND EMPNO = e.EMPNO)  	THEN	(SELECT sum(NET_AMT) FROM   paytran WHERE TRNCD in (301) AND TRNDT BETWEEN '"+ReportDAO.BOM(PAYREGDT)+ "' "+ 
					   "and '"+ReportDAO.EOM(PAYREGDT)+ "' AND EMPNO = e.EMPNO) "+ 
					   "ELSE	(SELECT 0 AS absentDays) END AS 'absentDays' from EMPMAST e join EMPTRAN t on e.EMPNO=t.EMPNO "+
					    "join paytran p on p.EMPNO=e.empno and p.TRNDT between '"+ReportDAO.BOM(PAYREGDT)+ "' and '"+ReportDAO.EOM(PAYREGDT)+ "' and "+ 
					    "p.TRNCD=201  and p.NET_AMT>0 and t.SRNO=(select max(srno) from EMPTRAN where EMPNO=t.EMPNO) "+ 
					    "group by e.SALUTE ,e.dob, e.empno,  e.empcode,e.fname,e.mname,e.lname,e.pfno,e.DOJ,e.AADHAARNUM,e.PANNO,t.ACNO,t.PRJ_SRNO,e.IDENTY "+ 
					  "union "+
					   "select e.salute ,e.dob, e.empno, e.empcode,e.fname,e.mname,e.lname,e.pfno, e.DOJ, "+
					"e.AADHAARNUM,e.PANNO,t.ACNO,t.PRJ_SRNO,e.IDENTY, (SELECT ( INP_AMT )  FROM   paytran_stage  WHERE  trncd IN ( 999 )  AND TRNDT BETWEEN '"+ReportDAO.BOM(PAYREGDT)+ "' and '"+ReportDAO.EOM(PAYREGDT)+ "' "+
	              " AND empno = e.empno) AS gross, case when exists (select f3 from OTHERDETAIL where EMPNO=e.empno) then "+ 
					"(select f3 from OTHERDETAIL where EMPNO=e.empno) else (SELECT '' AS ifsc) END AS 'ifsc', "+
	              " CASE WHEN "+ 
					"EXISTS(SELECT sum(NET_AMT) FROM   paytran_stage WHERE TRNCD in (101,102) AND TRNDT BETWEEN '"+ReportDAO.BOM(PAYREGDT)+ "' and '"+ReportDAO.EOM(PAYREGDT)+ "' "+  
					"AND EMPNO = e.EMPNO)   	THEN	(SELECT sum(NET_AMT) FROM   paytran_stage WHERE TRNCD in (101,102) AND TRNDT BETWEEN "+ 
					" '"+ReportDAO.BOM(PAYREGDT)+ "' and '"+ReportDAO.EOM(PAYREGDT)+ "' AND EMPNO = e.EMPNO) ELSE	(SELECT 0 AS BASIC_DA) END AS 'BASIC_DA', "+ 
					 "(SELECT (NET_AMT) FROM   paytran_stage WHERE TRNCD in (201) AND TRNDT BETWEEN '"+ReportDAO.BOM(PAYREGDT)+ "' and '"+ReportDAO.EOM(PAYREGDT)+ "' AND EMPNO = e.EMPNO) AS PF , "+
					  "CASE WHEN EXISTS(SELECT (NET_AMT) FROM   paytran_stage WHERE TRNCD in (301) AND TRNDT BETWEEN '"+ReportDAO.BOM(PAYREGDT)+ "' and '"+ReportDAO.EOM(PAYREGDT)+ "' "+
					  " AND EMPNO = e.EMPNO)  	THEN	(SELECT sum(NET_AMT) FROM   paytran_stage WHERE TRNCD in (301) AND TRNDT BETWEEN '"+ReportDAO.BOM(PAYREGDT)+ "' "+  
					  " and '"+ReportDAO.EOM(PAYREGDT)+ "' AND EMPNO = e.EMPNO) "+ 
					  " ELSE	(SELECT 0 AS absentDays) END AS 'absentDays' from EMPMAST e join EMPTRAN t on e.EMPNO=t.EMPNO "+
					   " join paytran_stage p on p.EMPNO=e.empno and p.TRNDT between '"+ReportDAO.BOM(PAYREGDT)+ "' and '"+ReportDAO.EOM(PAYREGDT)+ "' and "+ 
					   " p.TRNCD=201  and p.NET_AMT>0 and t.SRNO=(select max(srno) from EMPTRAN where EMPNO=t.EMPNO) "+ 
					  " group by e.SALUTE ,e.dob, e.empno,  e.empcode,e.fname,e.mname,e.lname,e.pfno,e.DOJ,e.AADHAARNUM,e.PANNO,t.ACNO,t.PRJ_SRNO,e.IDENTY "+ 
					   "union "+
					   
					"select e.salute ,e.dob, e.empno, e.empcode, "+ 
					"e.fname,e.mname,e.lname,e.pfno, "+
					"e.DOJ,e.AADHAARNUM,e.PANNO,t.ACNO,t.PRJ_SRNO,e.IDENTY, (SELECT ( INP_AMT )  FROM   paytran_stage  WHERE  trncd IN ( 999 )  AND TRNDT BETWEEN '"+ReportDAO.BOM(PAYREGDT)+ "' and '"+ReportDAO.EOM(PAYREGDT)+ "' "+
	              " AND empno = e.empno) AS gross, "+
					"case when exists (select f3 from OTHERDETAIL where EMPNO=e.empno) "+
					"then (select f3 from OTHERDETAIL where EMPNO=e.empno) "+
					"else (SELECT '' AS ifsc) END AS 'ifsc', "+
					"CASE WHEN EXISTS(SELECT sum(NET_AMT) FROM   ytdtran WHERE TRNCD in (101,102) "+
					 "AND TRNDT BETWEEN '"+ReportDAO.BOM(PAYREGDT)+"' and '"+ReportDAO.EOM(PAYREGDT)+"' "+
					 "AND EMPNO = e.EMPNO)   "+
						"	THEN	(SELECT sum(NET_AMT) FROM   ytdtran WHERE TRNCD in (101,102) "+
					 "AND TRNDT BETWEEN '"+ReportDAO.BOM(PAYREGDT)+"' and '"+ReportDAO.EOM(PAYREGDT)+"' "+
					 "AND EMPNO = e.EMPNO) ELSE	(SELECT 0 AS BASIC_DA) END AS 'BASIC_DA', "+
					  "(SELECT (NET_AMT)  FROM   ytdtran WHERE TRNCD in (201) "+
					 "AND TRNDT BETWEEN '"+ReportDAO.BOM(PAYREGDT)+"' and '"+ReportDAO.EOM(PAYREGDT)+"' "+
					 "AND EMPNO = e.EMPNO) AS PF , "+
					  "CASE WHEN EXISTS(SELECT (NET_AMT) FROM   ytdtran WHERE TRNCD in (301) "+
					 "AND TRNDT BETWEEN '"+ReportDAO.BOM(PAYREGDT)+"' and '"+ReportDAO.EOM(PAYREGDT)+"' "+
					 "AND EMPNO = e.EMPNO)  " + 
						"	THEN	(SELECT sum(NET_AMT) FROM   ytdtran WHERE TRNCD in (301) "+
					 "AND TRNDT BETWEEN '"+ReportDAO.BOM(PAYREGDT)+"' and '"+ReportDAO.EOM(PAYREGDT)+"' "+
					 "AND EMPNO = e.EMPNO) ELSE	(SELECT 0 AS absentDays) END AS 'absentDays' "+
					"from EMPMAST e "+ 
					"join EMPTRAN t "+ 
					"on e.EMPNO=t.EMPNO "+ 
					"join ytdtran p "+
					"on p.EMPNO=e.empno "+
					"and p.TRNDT between '"+ReportDAO.BOM(PAYREGDT)+"' and '"+ReportDAO.EOM(PAYREGDT)+"' "+
					"and p.TRNCD=201  and p.NET_AMT>0 "+
					"and t.SRNO=(select max(srno) from EMPTRAN where EMPNO=t.EMPNO) "+
					"group by e.SALUTE ,e.dob, e.empno,  e.empcode,e.fname,e.mname,e.lname,e.pfno,e.DOJ,e.AADHAARNUM,e.PANNO,t.ACNO,t.PRJ_SRNO,e.IDENTY "+
					"order by empcode ");
		

		int k = 0;
		
		while(rs.next())
		{
			k++;
		//	System.out.println("5555.....");
			HSSFRow row = sheet.createRow((short)i++);
			//System.out.println("9999.....");
			
			row.createCell((short) 0).setCellValue(""+(count++));
			row.createCell((short) 1).setCellValue(""+rs.getString("FNAME")+" "+rs.getString("MNAME")+" "+rs.getString("LNAME"));
			row.createCell((short) 2).setCellValue(""+rs.getString("MNAME")+" "+rs.getString("LNAME"));
			
	//		System.out.println("10101.....");
			row.createCell((short) 3).setCellValue(""+(rs.getString("DOB")));
			row.createCell((short) 4).setCellValue(""+sdf.format(rs.getDate("DOJ")));
			row.createCell((short) 5).setCellValue(""+(rs.getString("IDENTY")==null?"0":(rs.getString("IDENTY"))));
//			row.createCell((short) 5).setCellValue(""+rs.getDate("UANNO"));
			
	//		System.out.println("2020.....");
			
			row.createCell((short) 6).setCellValue(""+rs.getString("PFNO"));
			row.createCell((short) 7).setCellValue(rs.getString("AADHAARNUM"));
			row.createCell((short) 8).setCellValue(""+rs.getString("PANNO")==null?"":rs.getString("PANNO"));
			row.createCell((short) 9).setCellValue(""+rs.getString("ACNO"));
			String ifsc= ""+rs.getString("ifsc");/*!=null?""+emp.getString("ifsc"):"";*/
            if(ifsc.equalsIgnoreCase("null"))
            {
         //	System.out.println("ifsc Record Found    "+ifsc);
            	row.createCell((short) 10).setCellValue("");

            }else
            {
            	row.createCell((short) 10).setCellValue(""+rs.getString("ifsc"));
            }
		//	row.createCell((short) 10).setCellValue(""+rs.getString("ifsc"));
            /*String mob= ""+rs.getString("TELNO");!=null?""+emp.getString("ifsc"):"";
            if(mob.equalsIgnoreCase("null"))
            {
            	row.createCell((short) 11).setCellValue("");

            }else{
            	row.createCell((short) 11).setCellValue(""+rs.getString("TELNO"));
            } 
*/           
		//	row.createCell((short) 11).setCellValue(""+rs.getString("TELNO"));
			row.createCell((short) 12).setCellValue(""+rs.getString("absentDays"));
			row.createCell((short) 13).setCellValue(""+rs.getString("BASIC_DA"));
			
			brtot[1] = brtot[1] + Math.round(rs.getInt("BASIC_DA"));
			gross = (rs.getString("BASIC_DA")!= null?rs.getInt("BASIC_DA"):14);
			
			row.createCell((short) 14).setCellValue(""+rs.getString("PF"));
			brtot[2] = brtot[2] + Math.round(rs.getInt("PF"));
			row.createCell((short) 15).setCellValue(""+rs.getInt("gross"));
			brtot[3] = brtot[3] + Math.round(rs.getInt("gross"));
			//row.createCell((short) 16).setCellValue(""+rs.getString("TELNO"));
			/*String prjquery = "select Site_Name,Project_Code from Project_Sites where SITE_ID = '"+rs.getString("PRJ_SRNO")+"'";
			Connection conn = ConnectionManager.getConnectionTech();
			Statement stmt = conn.createStatement();
			ResultSet prj = stmt.executeQuery(prjquery);
			String prj_name="";
			String prj_code="";
			
			if(prj.next()){
				prj_name = prj.getString("Site_Name");
				prj_code = prj.getString("Project_Code");
			}*/
			
			
			/*row.createCell((short) 13).setCellValue(prj_name);
			row.createCell((short) 14).setCellValue(prj_code);
			row.createCell((short) 15).setCellValue(lkh.getLKP_Desc("DESIG",rs.getInt("DESIG")));
			row.createCell((short)16).setCellValue(lkh.getLKP_Desc("DEPT",rs.getInt("DEPT")));
			row.createCell((short)17).setCellValue(lkh.getLKP_Desc("BANK",rs.getInt("BANK_NAME")));
			row.createCell((short)18).setCellValue(rs.getString("ACNO"));


			row.createCell((short)19).setCellValue((rs.getString("status")).equalsIgnoreCase("A")?"Active":"Non-Active");
			
			row.createCell((short)20).setCellValue(rs.getString("CTC"));
			row.createCell((short)21).setCellValue(rs.getString("EMPNO"));
			
			*/
			
			
			
		//	System.out.println("0000.....");
			
		}
		
		
		
	//	System.out.println("6666.....");
		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		
		/*if(rs.getInt("PF")>0)
			k++;
		int eps = 0;
		int epf = 0;*/
		 
		Calendar calobj = Calendar.getInstance();
		
		HSSFRow row = sheet.createRow((short) i);
		row.createCell((short) 0).setCellValue(" ");
		  row = sheet.createRow((short) i+1);
	//	row.createCell((short) 0).setCellValue("Report Date And Time= " +df.format(calobj.getTime()));
		
		row.createCell((short) 1).setCellValue("Total Employee : =  " +k );
		row.createCell((short) 12).setCellValue("Total : = ");
		row.createCell((short) 13).setCellValue(""+trans(brtot[1],"","",true,true));
		row.createCell((short) 14).setCellValue(""+trans(brtot[2],"","",true,true));
		row.createCell((short) 15).setCellValue(""+trans(brtot[3],"","",true,true));
		
		hwb.write(out1);
		out1.close();
		st.close();
	    con.close();
	    
		System.out.println("Result OK.....");
		
		}
	//	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static String trans(int p_num,String p_pic,String p_lzc,boolean p_sgn,boolean blankif0)
	{
		NumberFormat format = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));
		String result = format.format(p_num).substring(4);
		int Reslength = result.length();
		int p_picLength = p_pic.length();
		
		if(Reslength < p_picLength)
		{
			result = ReportDAO.addSpaces("", p_picLength-Reslength) + result;
		}
		return result;
	}
	
	
	public static void leavetolicexcel(String type,String filepath,String imgpath)
	{
		Properties prop = new Properties();
		int brtot[] = new int[17];
		
	     try
	     {
		
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream stream = classLoader.getResourceAsStream("constant.properties");
			prop.load(stream);
	     }
	     catch (Exception e) {System.out.println("Error in constant properties Manager "+e);}
	     
		try
		{
			
			RepoartBean repBean  = new RepoartBean();
			Connection con =null;
			
		FileOutputStream out1 = new FileOutputStream(new File(filepath));
		HSSFWorkbook hwb=new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("LeaveToLic");
		
		
		
		
		sheet.setColumnWidth((short)0, (short)2000);
		sheet.setColumnWidth((short)1, (short)4000);
		sheet.setColumnWidth((short)2, (short)2000);
		sheet.setColumnWidth((short)3, (short)4000);
		sheet.setColumnWidth((short)4, (short)3000);
		sheet.setColumnWidth((short)5, (short)10000);
		sheet.setColumnWidth((short)6, (short)3000);
		sheet.setColumnWidth((short)7, (short)4000);
		sheet.setColumnWidth((short)8, (short)3000);
		sheet.setColumnWidth((short)9, (short)5000);
		sheet.setColumnWidth((short)10, (short)4000);
		sheet.setColumnWidth((short)11, (short)5000);
		sheet.setColumnWidth((short)12, (short)5000);
		sheet.setColumnWidth((short)13, (short)3000);
		sheet.setColumnWidth((short)14, (short)5000);
		sheet.setColumnWidth((short)15, (short)5000);
		sheet.setColumnWidth((short)16, (short)7000);
		/*sheet.setColumnWidth((short)18, (short)5000);
		sheet.setColumnWidth((short)19, (short)4000);
		sheet.setColumnWidth((short)20, (short)5000);*/




       HSSFCellStyle my_style = hwb.createCellStyle();
       HSSFCellStyle my_style1 = hwb.createCellStyle();

       HSSFFont my_font=hwb.createFont();
       my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
       my_style.setFont(my_font);
     
       
		HSSFRow rowtitle=   sheet.createRow((short)0);
		HSSFCell cell = rowtitle.createCell((short) 9);
		
	//	System.out.println("2222.....");
		
		cell.setCellValue(prop.getProperty("bankName1"));
		cell.setCellStyle(my_style);
		HSSFRow rowtitle1=   sheet.createRow((short)1);
		HSSFCell cell1 = rowtitle1.createCell((short) 7);
		cell1.setCellValue(prop.getProperty("addressForReport"));
		cell1.setCellStyle(my_style);
		HSSFRow rowtitle2=   sheet.createRow((short)2);
		HSSFCell cell2 = rowtitle2.createCell((short) 9);
		cell2.setCellValue(prop.getProperty("contactForReport"));
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle31=   sheet.createRow((short)3);
		cell2 = rowtitle31.createCell((short) 9);
		cell2.setCellValue(	prop.getProperty("mailForReport"));
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle3=   sheet.createRow((short)4);
		cell2=rowtitle3.createCell((short) 2);
		cell2.setCellValue("");
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle4=   sheet.createRow((short)5);
		rowtitle4.createCell((short) 0).setCellValue("");
		HSSFRow rowtitle5=   sheet.createRow((short)6);
		rowtitle5.createCell((short) 0).setCellValue("");
		
		
		HSSFFont blueFont = hwb.createFont();
		blueFont.setColor(HSSFColor.BLUE.index);
		
		HSSFCellStyle style = hwb.createCellStyle();
		//style.setFont(blueFont);
		style.setFillForegroundColor(HSSFColor.BLUE.index);
		
		
		HSSFRow head=   sheet.createRow((short)7);
		head.createCell((short) 0).setCellValue("");
		HSSFRow heading=   sheet.createRow((short)8);
		HSSFCell cell3 = heading.createCell((short) 0); 

		cell3.setCellValue("");
		cell3.setCellStyle(my_style1);
		HSSFRow rowhead=   sheet.createRow((short)9);
       sheet.createFreezePane( 0, 10, 0, 10 );
      
       my_style1.setAlignment((short) 2);
       my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
       my_style1.setFont(my_font);
       
		cell3=rowhead.createCell((short) 0);
		cell3.setCellValue("SR NO");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 1);
		cell3.setCellValue("Policy No");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 2);
		cell3.setCellValue("Lic Id");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 3);
		cell3.setCellValue("Compute");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 4);
		cell3.setCellValue("Emp Code");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 5);
		cell3.setCellValue("Employee Name");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 6);
		cell3.setCellValue("Category");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 7);
		cell3.setCellValue("Dob");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 8);
		cell3.setCellValue("Sex");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short)9);
		cell3.setCellValue("Date of Appointment");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 10);
		cell3.setCellValue("Salary");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 11);
		cell3.setCellValue("P Leave Credit");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 12);
		cell3.setCellValue("M Leave Credit");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 13);
		cell3.setCellValue("Frequency");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 14);
		cell3.setCellValue("Doj Scheme");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 15);
		cell3.setCellValue("Customer Unit Code");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 16);
		cell3.setCellValue("Remark");
		cell3.setCellStyle(my_style1);

		/*cell3=rowhead.createCell((short) 10);
		cell3.setCellValue("JOIN_DATE (MM/DD/YYYY)");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 11);
		cell3.setCellValue("BIRTH_DT (MM/DD/YYYY)");
		cell3.setCellStyle(my_style1);*/
		
		/*cell3=rowhead.createCell((short) 11);
		cell3.setCellValue("MOBILE NO");*/
		/*cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 12);
		cell3.setCellValue("NCP DAYS");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 13);
		cell3.setCellValue("BASIC +DA");
		cell3.setCellStyle(my_style1);*/
		/*cell3=rowhead.createCell((short) 14);
		cell3.setCellValue("PF DED. AMT");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 15);
		cell3.setCellValue("GROSS. AMT");
		cell3.setCellStyle(my_style1);*/
		/*cell3=rowhead.createCell((short) 17);
		cell3.setCellValue("BANK");
		cell3.setCellStyle(my_style1);
		
		cell3=rowhead.createCell((short) 18);
		cell3.setCellValue("Account No");
		cell3.setCellStyle(my_style1);
		
		cell3=rowhead.createCell((short) 19);
		cell3.setCellValue("Status");
		cell3.setCellStyle(my_style1);
		
		cell3=rowhead.createCell((short) 20);
		cell3.setCellValue(" Monthly CTC");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 21);
		cell3.setCellValue("EMPNO");
		cell3.setCellStyle(my_style1);*/
		
		
		
		
		
		ReportDAO.OpenCon("", "", "",repBean);
		con = repBean.getCn();	
		Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		
		int i=11;
		int count=1;
		int k = 0;
		String year=ReportDAO.getServerDate();
		System.out.println("this is year..."+year.substring(7,11));
		int years =Integer.parseInt(year.substring(7,11));
		System.out.println("previous year..."+(years-1));
		 
		/*String empquery=("select Distinct e.empno from empmast e where "+
				" e.status='A' and "+
				" (select DATEDIFF(MONTH,(select doj from empmast where empno=e.empno and status='A' ),GETDATE()))<696"+  
				" order by e.empno ");
		System.out.println("this is emp selection...."+empquery);
		ResultSet emp=st.executeQuery(empquery);
		
		while(emp.next())
		{
			
		int empno=emp.getInt("empno");*/
		System.out.println("with a as( select e.empno,e.EMPCODE,e.gender,e.LIC_LEAVE_ENCASH_NO,LIC_LEAVE_ENCASH_POLICY_NO,convert(nvarchar,e.DOJ,103) as DOJ,convert(nvarchar,e.DOB,103) as DOB,RTRIM(e.LNAME)+''+RTRIM(e.FNAME)+' '+RTRIM(e.MNAME) AS NAME, lkp_disc as DESIG,"+  
				   " (select isnull((select ISNULL(p.INP_AMT,0) from PAYTRAN p "+ 
					" where p.EMPNO=e.EMPNO and trncd=101 and TRNDT between "+ 
					" '"+ReportDAO.BOM("01-JAN-"+year.substring(7,11))+"'  and '"+ReportDAO.EOM("01-JAN-"+year.substring(7,11))+"' ),0)) as BASIC, "+  
					" (select isnull((select ISNULL(p.INP_AMT,0) from PAYTRAN p "+ 
					" where p.EMPNO=e.EMPNO and trncd=102 and TRNDT between "+ 
					" '"+ReportDAO.BOM("01-JAN-"+year.substring(7,11))+"'  and '"+ReportDAO.EOM("01-JAN-"+year.substring(7,11))+"'),0)) as DA,  "+
					" (select isnull((select ISNULL(p.INP_AMT,0) from PAYTRAN p "+ 
					" where p.EMPNO=e.EMPNO and trncd=138 and TRNDT between "+ 
					" '"+ReportDAO.BOM("01-JAN-"+year.substring(7,11))+"'  and '"+ReportDAO.EOM("01-JAN-"+year.substring(7,11))+"'),0)) as VDA, "+
					" (select top 1 isnull( l.BAL,0) from leavebal l where l.EMPNO=e.EMPNO and l.LEAVECD=1 order by BALDT desc,srno desc) as DAYS "+
					
					" from empmast e,emptran t,lookup l where  e.EMPNO=e.EMPNO and e.EMPNO=t.EMPNO and "+
					" t.srno=(select max(t1.srno) from emptran t1 where t1.empno=e.EMPNO ) and "+
					" l.lkp_srno=t.desig and l.lkp_code='DESIG' and "+
					" ((e.STATUS='A' and e.DOJ<='"+ReportDAO.EOM("01-MAR-"+year.substring(7,11))+"' )) and " +
					" (select DATEDIFF(MONTH,(select doj from empmast where empno=e.empno and status='A' ),GETDATE())) < 696 "+
						/*	"or "+
					" (e.DOL <='"+ReportDAO.EOM("01-MAR-"+year.substring(7,11))+"' and(e.STATUS='N' and e.DOL<>''))) "+*/  
					" UNION "+ 
			   		" select e.empno,e.EMPCODE,e.gender,e.LIC_LEAVE_ENCASH_NO,LIC_LEAVE_ENCASH_POLICY_NO,convert(nvarchar,e.DOJ,103) as DOJ,convert(nvarchar,e.DOB,103) as DOB,RTRIM(e.LNAME)+''+RTRIM(e.FNAME)+' '+RTRIM(e.MNAME) AS NAME, lkp_disc as DESIG, "+  
			   		" (select isnull((select ISNULL(p.INP_AMT,0) from PAYTRAN_STAGE p "+
			   		" where p.EMPNO=e.EMPNO and trncd=101 and TRNDT between "+
			   		" '"+ReportDAO.BOM("01-JAN-"+year.substring(7,11))+"'  and '"+ReportDAO.EOM("01-JAN-"+year.substring(7,11))+"'),0)) as BASIC, "+  
			   		" (select isnull((select ISNULL(p.INP_AMT,0) from PAYTRAN_STAGE p "+
			   		" where p.EMPNO=e.EMPNO and trncd=102 and TRNDT between "+
			   		" '"+ReportDAO.BOM("01-JAN-"+year.substring(7,11))+"'  and '"+ReportDAO.EOM("01-JAN-"+year.substring(7,11))+"'),0)) as DA, "+  
			   		" (select isnull((select ISNULL(p.INP_AMT,0) from PAYTRAN_STAGE p "+
			   		" where p.EMPNO=e.EMPNO and trncd=138 and TRNDT between "+
			   		" '"+ReportDAO.BOM("01-JAN-"+year.substring(7,11))+"'  and '"+ReportDAO.EOM("01-JAN-"+year.substring(7,11))+"'),0)) as VDA, "+
			   		" (select top 1 isnull( l.BAL,0) from leavebal l where l.EMPNO=e.EMPNO and l.LEAVECD=1 order by BALDT desc,srno desc) as DAYS "+
			
					" from empmast e,emptran t,lookup l where  e.EMPNO=e.EMPNO and e.EMPNO=t.EMPNO and "+
					" t.srno=(select max(t1.srno) from emptran t1 where t1.empno=e.EMPNO ) and "+
					" l.lkp_srno=t.desig and l.lkp_code='DESIG' and "+
					" ((e.STATUS='A' and e.DOJ<='"+ReportDAO.EOM("01-MAR-"+year.substring(7,11))+"' )) and " +
					" (select DATEDIFF(MONTH,(select doj from empmast where empno=e.empno and status='A' ),GETDATE()))<696"+
					/*" ((e.DOL>='"+ReportDAO.BoFinancialy("01-MAR-"+year.substring(7,11))+"' ) and "+
			   		" (e.DOL <='"+ReportDAO.EOM("01-MAR-"+year.substring(7,11))+"' and (e.STATUS='N' and e.DOL<>''))) "+ */
			   		"  )" +
			   		" select * from a where a.VDA >0 order by a.EMPCODE,a.DESIG " );
		ResultSet rs=st1.executeQuery("with a as( select e.empno,e.EMPCODE,e.gender,e.LIC_LEAVE_ENCASH_NO,LIC_LEAVE_ENCASH_POLICY_NO,convert(nvarchar,e.DOJ,103) as DOJ,convert(nvarchar,e.DOB,103) as DOB,RTRIM(e.LNAME)+''+RTRIM(e.FNAME)+' '+RTRIM(e.MNAME) AS NAME, lkp_disc as DESIG,"+  
										   " (select isnull((select ISNULL(p.INP_AMT,0) from PAYTRAN p "+ 
											" where p.EMPNO=e.EMPNO and trncd=101 and TRNDT between "+ 
											" '"+ReportDAO.BOM("01-JAN-"+year.substring(7,11))+"'  and '"+ReportDAO.EOM("01-JAN-"+year.substring(7,11))+"' ),0)) as BASIC, "+  
											" (select isnull((select ISNULL(p.INP_AMT,0) from PAYTRAN p "+ 
											" where p.EMPNO=e.EMPNO and trncd=102 and TRNDT between "+ 
											" '"+ReportDAO.BOM("01-JAN-"+year.substring(7,11))+"'  and '"+ReportDAO.EOM("01-JAN-"+year.substring(7,11))+"'),0)) as DA,  "+
											" (select isnull((select ISNULL(p.INP_AMT,0) from PAYTRAN p "+ 
											" where p.EMPNO=e.EMPNO and trncd=138 and TRNDT between "+ 
											" '"+ReportDAO.BOM("01-JAN-"+year.substring(7,11))+"'  and '"+ReportDAO.EOM("01-JAN-"+year.substring(7,11))+"'),0)) as VDA, "+
											" (select top 1 isnull( l.BAL,0) from leavebal l where l.EMPNO=e.EMPNO and l.LEAVECD=1 order by BALDT desc,srno desc) as DAYS "+
											
											" from empmast e,emptran t,lookup l where  e.EMPNO=e.EMPNO and e.EMPNO=t.EMPNO and "+
											" t.srno=(select max(t1.srno) from emptran t1 where t1.empno=e.EMPNO ) and "+
											" l.lkp_srno=t.desig and l.lkp_code='DESIG' and "+
											" ((e.STATUS='A' and e.DOJ<='"+ReportDAO.EOM("01-MAR-"+year.substring(7,11))+"' )) and " +
											" (select DATEDIFF(MONTH,(select doj from empmast where empno=e.empno and status='A' ),GETDATE())) < 696 "+
												/*	"or "+
											" (e.DOL <='"+ReportDAO.EOM("01-MAR-"+year.substring(7,11))+"' and(e.STATUS='N' and e.DOL<>''))) "+*/  
											" UNION "+ 
									   		" select e.empno,e.EMPCODE,e.gender,e.LIC_LEAVE_ENCASH_NO,LIC_LEAVE_ENCASH_POLICY_NO,convert(nvarchar,e.DOJ,103) as DOJ,convert(nvarchar,e.DOB,103) as DOB,RTRIM(e.LNAME)+''+RTRIM(e.FNAME)+' '+RTRIM(e.MNAME) AS NAME, lkp_disc as DESIG, "+  
									   		" (select isnull((select ISNULL(p.INP_AMT,0) from PAYTRAN_STAGE p "+
									   		" where p.EMPNO=e.EMPNO and trncd=101 and TRNDT between "+
									   		" '"+ReportDAO.BOM("01-JAN-"+year.substring(7,11))+"'  and '"+ReportDAO.EOM("01-JAN-"+year.substring(7,11))+"'),0)) as BASIC, "+  
									   		" (select isnull((select ISNULL(p.INP_AMT,0) from PAYTRAN_STAGE p "+
									   		" where p.EMPNO=e.EMPNO and trncd=102 and TRNDT between "+
									   		" '"+ReportDAO.BOM("01-JAN-"+year.substring(7,11))+"'  and '"+ReportDAO.EOM("01-JAN-"+year.substring(7,11))+"'),0)) as DA, "+  
									   		" (select isnull((select ISNULL(p.INP_AMT,0) from PAYTRAN_STAGE p "+
									   		" where p.EMPNO=e.EMPNO and trncd=138 and TRNDT between "+
									   		" '"+ReportDAO.BOM("01-JAN-"+year.substring(7,11))+"'  and '"+ReportDAO.EOM("01-JAN-"+year.substring(7,11))+"'),0)) as VDA, "+
									   		" (select top 1 isnull( l.BAL,0) from leavebal l where l.EMPNO=e.EMPNO and l.LEAVECD=1 order by BALDT desc,srno desc) as DAYS "+
									
											" from empmast e,emptran t,lookup l where  e.EMPNO=e.EMPNO and e.EMPNO=t.EMPNO and "+
											" t.srno=(select max(t1.srno) from emptran t1 where t1.empno=e.EMPNO ) and "+
											" l.lkp_srno=t.desig and l.lkp_code='DESIG' and "+
											" ((e.STATUS='A' and e.DOJ<='"+ReportDAO.EOM("01-MAR-"+year.substring(7,11))+"' )) and " +
											" (select DATEDIFF(MONTH,(select doj from empmast where empno=e.empno and status='A' ),GETDATE()))<696"+
											/*" ((e.DOL>='"+ReportDAO.BoFinancialy("01-MAR-"+year.substring(7,11))+"' ) and "+
									   		" (e.DOL <='"+ReportDAO.EOM("01-MAR-"+year.substring(7,11))+"' and (e.STATUS='N' and e.DOL<>''))) "+ */
									   		"  )" +
									   		" select * from a where a.VDA >0 order by a.EMPCODE,a.DESIG " );
		

	
		double TotLeaveSal=0;
		int days=0;
		double Totdays=0;
		while(rs.next())
		{
			k++;
		double  grosssal=0;
		int  leavesal=0;
			HSSFRow row = sheet.createRow((short)i++);
			
			
			row.createCell((short) 0).setCellValue(""+k);
			row.createCell((short) 1).setCellValue(""+rs.getString("LIC_LEAVE_ENCASH_POLICY_NO")==null?"":rs.getString("LIC_LEAVE_ENCASH_POLICY_NO"));
			row.createCell((short) 2).setCellValue(""+rs.getString("LIC_LEAVE_ENCASH_NO")==null?"":rs.getString("LIC_LEAVE_ENCASH_NO"));
			row.createCell((short) 3).setCellValue("01-03-"+""+year.substring(7,11));
			row.createCell((short) 4).setCellValue(""+rs.getString("EMPCODE"));
			row.createCell((short) 5).setCellValue(""+rs.getString("NAME"));
			row.createCell((short) 6).setCellValue("1");
			row.createCell((short) 7).setCellValue(""+(rs.getString("DOB")));
			row.createCell((short) 8).setCellValue(""+rs.getString("gender"));
			row.createCell((short) 9).setCellValue(""+rs.getString("DOJ"));
			grosssal=Math.round(rs.getInt("BASIC")+rs.getInt("DA")+rs.getInt("VDA"));
			row.createCell((short) 10).setCellValue(""+UtilityDAO.transLeavetoLIC((grosssal), "9,99,999","", false, false));
			row.createCell((short) 11).setCellValue(""+rs.getInt("DAYS"));
			row.createCell((short) 12).setCellValue("0");
			row.createCell((short) 13).setCellValue("M");
			row.createCell((short) 14).setCellValue("01-03-"+""+(years-1));
			row.createCell((short) 15).setCellValue("");
			row.createCell((short) 16).setCellValue("");
			
	
			/*row.createCell((short) 3).setCellValue(""+(rs.getString("DESIG")));
			row.createCell((short) 4).setCellValue(""+rs.getString("BASIC"));
			row.createCell((short) 5).setCellValue(""+rs.getString("DA"));
			grosssal=rs.getInt("BASIC")+rs.getInt("DA")+rs.getInt("VDA");
			
			row.createCell((short) 6).setCellValue(""+rs.getString("VDA"));
			row.createCell((short) 7).setCellValue(""+UtilityDAO.trans(Math.round(grosssal), "9,99,999","", false, false));
			row.createCell((short) 8).setCellValue(""+rs.getInt("DAYS"));
			
			leavesal=(grosssal/30)*rs.getInt("DAYS");
			row.createCell((short) 9).setCellValue(""+UtilityDAO.trans(Math.round(leavesal), "9,99,999","", false, false));*/
	//		row.createCell((short) 10).setCellValue(""+rs.getString("DOJ"));
	//		row.createCell((short) 11).setCellValue(""+rs.getString("DOB"));
			TotLeaveSal = TotLeaveSal+ grosssal;
			days= rs.getInt("DAYS");
			Totdays = Totdays + days;
			grosssal=0;
			days=0;
		}
			
		//}
		
		HSSFRow row1 = sheet.createRow((short) i);
		row1.createCell((short) 0).setCellValue(" ");
		row1 = sheet.createRow((short) i+1);
	//	row.createCell((short) 0).setCellValue("Report Date And Time= " +df.format(calobj.getTime()));
		
		row1.createCell((short) 1).setCellValue("Total Employee : =  " +k);
		row1.createCell((short) 9).setCellValue("Total Salary : =  ");
		row1.createCell((short) 10).setCellValue(""+UtilityDAO.transLeavetoLIC(TotLeaveSal, "999,999.99", "", false, false));
		row1.createCell((short) 11).setCellValue("Total Days : =  "+Totdays);
		/*row1.createCell((short) 12).setCellValue("Total : = ");
		row1.createCell((short) 13).setCellValue(""+trans(brtot[1],"","",true,true));
		row1.createCell((short) 14).setCellValue(""+trans(brtot[2],"","",true,true));
		row1.createCell((short) 15).setCellValue(""+trans(brtot[3],"","",true,true));*/
		
		hwb.write(out1);
		out1.close();
		st.close();
	    con.close();
	    
		System.out.println("Result OK.....");
		
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	public static void GratuityReportexcel(String emptype,String type,String PolicyNum,String filepath,String imgpath)
//	public static void GratuityReportexcel(String emptype,String type,String filepath,String imgpath)
	{
		Properties prop = new Properties();
		int brtot[] = new int[15];
	//	System.out.println("PolicyNum in dao:  "+PolicyNum);
	     try
	     {
		
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream stream = classLoader.getResourceAsStream("constant.properties");
			prop.load(stream);
	     }
	     catch (Exception e) {System.out.println("Error in constant properties Manager "+e);}
	     
		try
		{
			
			RepoartBean repBean  = new RepoartBean();
			Connection con =null;
			
		FileOutputStream out1 = new FileOutputStream(new File(filepath));
		HSSFWorkbook hwb=new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("GRATUITY_REPORT");
		
		
		
		
		sheet.setColumnWidth((short)0, (short)2000);
		sheet.setColumnWidth((short)1, (short)3000);
		sheet.setColumnWidth((short)2, (short)10000);
		sheet.setColumnWidth((short)3, (short)7000);
		sheet.setColumnWidth((short)4, (short)4000);
		sheet.setColumnWidth((short)5, (short)4000);
		sheet.setColumnWidth((short)6, (short)4000);
		sheet.setColumnWidth((short)7, (short)5000);
		sheet.setColumnWidth((short)8, (short)5000);
		sheet.setColumnWidth((short)9, (short)7000);
		sheet.setColumnWidth((short)10, (short)7000);
		sheet.setColumnWidth((short)11, (short)7000);
		sheet.setColumnWidth((short)12, (short)7000);
		sheet.setColumnWidth((short)13, (short)7000);
	
       HSSFCellStyle my_style = hwb.createCellStyle();
       HSSFCellStyle my_style1 = hwb.createCellStyle();

       HSSFFont my_font=hwb.createFont();
       my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
       my_style.setFont(my_font);
     
        HSSFRow rowtitle=   sheet.createRow((short)0);
		HSSFCell cell = rowtitle.createCell((short) 9);
		cell.setCellValue(prop.getProperty("bankName1"));
		cell.setCellStyle(my_style);
		HSSFRow rowtitle1=   sheet.createRow((short)1);
		HSSFCell cell1 = rowtitle1.createCell((short) 7);
		cell1.setCellValue(prop.getProperty("addressForReport"));
		cell1.setCellStyle(my_style);
		HSSFRow rowtitle2=   sheet.createRow((short)2);
		HSSFCell cell2 = rowtitle2.createCell((short) 9);
		cell2.setCellValue(prop.getProperty("contactForReport"));
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle31=   sheet.createRow((short)3);
		cell2 = rowtitle31.createCell((short) 9);
		cell2.setCellValue(	prop.getProperty("mailForReport"));
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle3=   sheet.createRow((short)4);
		cell2=rowtitle3.createCell((short) 2);
		cell2.setCellValue("");
		cell2.setCellStyle(my_style);
		 long millis=System.currentTimeMillis();  
		 java.sql.Date date=new java.sql.Date(millis);  
		 DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		 String reportDate = df.format(date);
		HSSFRow rowtitle4=   sheet.createRow((short)5);
		cell2 = rowtitle4.createCell((short) 9);
		cell2.setCellValue("GRATUITY REPORT :- "+reportDate);
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle5=   sheet.createRow((short)6);
		rowtitle5.createCell((short) 0).setCellValue("");
		
		
		HSSFFont blueFont = hwb.createFont();
		blueFont.setColor(HSSFColor.BLUE.index);
		
		HSSFCellStyle style = hwb.createCellStyle();
		//style.setFont(blueFont);
		style.setFillForegroundColor(HSSFColor.BLUE.index);
		
		
		HSSFRow head=   sheet.createRow((short)7);
		head.createCell((short) 0).setCellValue("");
		HSSFRow heading=   sheet.createRow((short)8);
		HSSFCell cell3 = heading.createCell((short) 0); 

		cell3.setCellValue("");
		cell3.setCellStyle(my_style1);
		HSSFRow rowhead=   sheet.createRow((short)9);
       sheet.createFreezePane( 0, 10, 0, 10 );
      
       my_style1.setAlignment((short) 2);
       my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
       my_style1.setFont(my_font);
       
		cell3=rowhead.createCell((short) 0);
		cell3.setCellValue("SR NO");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 1);
		cell3.setCellValue("EMP CODE");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 2);
		cell3.setCellValue("EMP NAME");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 3);
		cell3.setCellValue("DESIGNATION");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 4);
		cell3.setCellValue("BASIC");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 5);
		cell3.setCellValue("DA");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 6);
		cell3.setCellValue("VDA");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 7);
		cell3.setCellValue("GROSS SALARY");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 8);
		cell3.setCellValue("SERVICE YEAR");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short)9);
		cell3.setCellValue("GRATUITY AMOUNT");
		cell3.setCellStyle(my_style1);

		cell3=rowhead.createCell((short) 10);
		cell3.setCellValue("JOIN_DATE (MM/DD/YYYY)");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 11);
		cell3.setCellValue("BIRTH_DT (MM/DD/YYYY)");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 12);
		cell3.setCellValue("POLICY NO.");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 13);
		cell3.setCellValue("LIC ID");
		cell3.setCellStyle(my_style1);
		
		
		ReportDAO.OpenCon("", "", "",repBean);
		con = repBean.getCn();	
		Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		Statement stdt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		
		int i=11;
		int count=1;
		int k = 0;
		//String querydt=""; 
		//String yr ="";
		String empquery="";
		String year=ReportDAO.getServerDate();
		System.out.println("this is year..."+year.substring(7,11));
		if(emptype.equalsIgnoreCase("active"))
		{
		 /*empquery=("select Distinct e.empno,e.EMPCODE,DOJ,replace(convert(NVARCHAR, DOL , 106), ' ', '-') DOL from empmast e where "+
				" e.status='A' and "+
				" (select DATEDIFF(MONTH,(select doj from empmast where empno=e.empno and status='A' ),GETDATE()))>60"+  
				" order by e.EMPCODE ");*/
			empquery=("select Distinct e.empno,e.EMPCODE,DOJ,replace(convert(NVARCHAR, DOL , 106), ' ', '-') DOL from empmast e where "+
					" e.status='A' "+
					" order by e.EMPCODE ");
		}
		else
		{
			/*empquery=("select Distinct e.empno,e.EMPCODE,DOJ,replace(convert(NVARCHAR, DOL , 106), ' ', '-') DOL from empmast e where "+
					" e.status='N' and "+
					" (select DATEDIFF(MONTH,(select doj from empmast where empno=e.empno and status='N' ),(select dol from empmast where empno=e.empno and status='N' )))>60"+  
					" order by e.EMPCODE ");*/
			empquery=("select Distinct e.empno,e.EMPCODE,DOJ,replace(convert(NVARCHAR, DOL , 106), ' ', '-') DOL from empmast e where "+
					" e.status='N' "+
					" order by e.EMPCODE ");
		}
		System.out.println("this is emp selection...."+empquery);
		ResultSet emp=st.executeQuery(empquery);
		//String thisdate="30-JUN-"+year.substring(7,11);
		//thisdate=thisdate.replace("-","/");
		 //SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		//Date d1= (Date) formatter.parse(thisdate);
		//Date d2=null;
		while(emp.next())
		{
			
			String record_query="";
		int empno=emp.getInt("empno");
		String dol=emp.getString("DOL");
		
	//	if(emptype.equalsIgnoreCase("active") && PolicyNum.equalsIgnoreCase("20180"))
		if((emptype.equalsIgnoreCase("active") && PolicyNum.equalsIgnoreCase("20180")) ||(emptype.equalsIgnoreCase("active") && PolicyNum.equalsIgnoreCase("708000245")) )
		{ 
		record_query=("select e.empno,e.EMPCODE,convert(nvarchar,e.DOJ,103) as DOJ,convert(nvarchar,e.DOB,103) as DOB,RTRIM(e.LNAME)+''+RTRIM(e.FNAME)+' '+RTRIM(e.MNAME) AS NAME, lkp_disc as DESIG," +
				" (SELECT  DATEDIFF( mm, (SELECT DOJ FROM EMPMAST WHERE EMPNO="+empno+"), GETDATE()) / 12) AS years," +
				" (SELECT DATEDIFF( mm, (SELECT DOJ FROM EMPMAST WHERE EMPNO="+empno+"), GETDATE()) % 12 )AS months ," +
				" (SELECT DATEDIFF( dd, DATEADD( mm, DATEDIFF( mm, (SELECT DOJ FROM EMPMAST WHERE EMPNO="+empno+"), GETDATE()), (SELECT DOJ FROM EMPMAST WHERE EMPNO="+empno+")), GETDATE())) AS Days , "+  
				" (select isnull((select ISNULL(p.INP_AMT,0) from PAYTRAN p "+ 
				" where p.EMPNO="+empno+" and trncd=101),0)) as BASIC, "+  
				" (select isnull((select ISNULL(p.INP_AMT,0) from PAYTRAN p "+ 
				" where p.EMPNO="+empno+" and trncd=102),0)) as DA,  "+
				" (select isnull((select ISNULL(p.INP_AMT,0) from PAYTRAN p "+ 
				" where p.EMPNO="+empno+" and trncd=138),0)) as VDA, "+
				" e.LIC_GRAT_NO,e.LIC_GRAT_POLICY_NO  "+
				" from empmast e,emptran t,lookup l where  e.EMPNO="+empno+" and e.EMPNO=t.EMPNO and "+
				" t.srno=(select max(t1.srno) from emptran t1 where t1.empno=e.EMPNO ) and "+
				" l.lkp_srno=t.desig and l.lkp_code='DESIG' and "+
				" ((e.STATUS='A' and e.DOJ<=(select MAX(TRNDT) from paytran where empno="+empno+" and trncd=101) )"+
				" and e.LIC_GRAT_POLICY_NO="+PolicyNum+" )  " );
		}
		
		if((emptype.equalsIgnoreCase("nonactive") && PolicyNum.equalsIgnoreCase("20180")) || (emptype.equalsIgnoreCase("nonactive") && PolicyNum.equalsIgnoreCase("708000245")))
		{
			record_query=("IF EXISTS(SELECT * FROM PAYTRAN WHERE EMPNO="+empno+" AND TRNDT BETWEEN '"+ReportDAO.BOM(dol)+"' AND '"+ReportDAO.EOM(dol)+"' AND TRNCD=101 AND INP_AMT>0) " +
					"select e.empno,e.EMPCODE,convert(nvarchar,e.DOJ,103) as DOJ,convert(nvarchar,e.DOB,103) as DOB,RTRIM(e.LNAME)+''+RTRIM(e.FNAME)+' '+RTRIM(e.MNAME) AS NAME, lkp_disc as DESIG, "+  
			   		" (SELECT  DATEDIFF( mm, (SELECT DOJ FROM EMPMAST WHERE EMPNO="+empno+"), GETDATE()) / 12) AS years," +
					" (SELECT DATEDIFF( mm, (SELECT DOJ FROM EMPMAST WHERE EMPNO="+empno+"), GETDATE()) % 12 )AS months ," +
					" (SELECT DATEDIFF( dd, DATEADD( mm, DATEDIFF( mm, (SELECT DOJ FROM EMPMAST WHERE EMPNO="+empno+"), GETDATE()), (SELECT DOJ FROM EMPMAST WHERE EMPNO="+empno+")), GETDATE())) AS Days , "+  
					" (select isnull((select ISNULL(p.INP_AMT,0) from PAYTRAN p "+
			   		" where p.EMPNO="+empno+" and trncd=101 and TRNDT BETWEEN '"+ReportDAO.BOM(dol)+"' AND '"+ReportDAO.EOM(dol)+"' ),0)) as BASIC, "+  
			   		" (select isnull((select ISNULL(p.INP_AMT,0) from PAYTRAN p "+
			   		" where p.EMPNO="+empno+" and trncd=102 and TRNDT BETWEEN '"+ReportDAO.BOM(dol)+"' AND '"+ReportDAO.EOM(dol)+"' ),0)) as DA, "+  
			   		" (select isnull((select ISNULL(p.INP_AMT,0) from PAYTRAN p "+
			   		" where p.EMPNO="+empno+" and trncd=138 and TRNDT BETWEEN '"+ReportDAO.BOM(dol)+"' AND '"+ReportDAO.EOM(dol)+"' ),0)) as VDA, "+
			   		" e.LIC_GRAT_NO,e.LIC_GRAT_POLICY_NO "+
			   		
					" from empmast e,emptran t,lookup l where  e.EMPNO="+empno+" and e.EMPNO=t.EMPNO and "+
					" t.srno=(select max(t1.srno) from emptran t1 where t1.empno="+empno+" ) and "+
					" l.lkp_srno=t.desig and l.lkp_code='DESIG' and "+
					" ((e.STATUS='N' and e.DOJ<='"+ReportDAO.EOM(dol)+"' )) "+ 
					"  and e.LIC_GRAT_POLICY_NO="+PolicyNum+" "+
					
					"ELSE "+
					" IF EXISTS(SELECT * FROM PAYTRAN_STAGE WHERE EMPNO="+empno+" AND TRNDT BETWEEN '"+ReportDAO.BOM(dol)+"' AND '"+ReportDAO.EOM(dol)+"' AND TRNCD=101 AND INP_AMT>0) "+
					" select e.empno,e.EMPCODE,convert(nvarchar,e.DOJ,103) as DOJ,convert(nvarchar,e.DOB,103) as DOB,RTRIM(e.LNAME)+''+RTRIM(e.FNAME)+' '+RTRIM(e.MNAME) AS NAME, lkp_disc as DESIG, "+  
			   		" (SELECT  DATEDIFF( mm, (SELECT DOJ FROM EMPMAST WHERE EMPNO="+empno+"), GETDATE()) / 12) AS years," +
					" (SELECT DATEDIFF( mm, (SELECT DOJ FROM EMPMAST WHERE EMPNO="+empno+"), GETDATE()) % 12 )AS months ," +
					" (SELECT DATEDIFF( dd, DATEADD( mm, DATEDIFF( mm, (SELECT DOJ FROM EMPMAST WHERE EMPNO="+empno+"), GETDATE()), (SELECT DOJ FROM EMPMAST WHERE EMPNO="+empno+")), GETDATE())) AS Days , "+  
					" (select isnull((select ISNULL(p.INP_AMT,0) from PAYTRAN_STAGE p "+
			   		" where p.EMPNO="+empno+" and trncd=101 and TRNDT BETWEEN '"+ReportDAO.BOM(dol)+"' AND '"+ReportDAO.EOM(dol)+"' ),0)) as BASIC, "+  
			   		" (select isnull((select ISNULL(p.INP_AMT,0) from PAYTRAN_STAGE p "+
			   		" where p.EMPNO="+empno+" and trncd=102 and TRNDT BETWEEN '"+ReportDAO.BOM(dol)+"' AND '"+ReportDAO.EOM(dol)+"' ),0)) as DA, "+  
			   		" (select isnull((select ISNULL(p.INP_AMT,0) from PAYTRAN_STAGE p "+
			   		" where p.EMPNO="+empno+" and trncd=138 and TRNDT BETWEEN '"+ReportDAO.BOM(dol)+"' AND '"+ReportDAO.EOM(dol)+"' ),0)) as VDA, "+
			   		" e.LIC_GRAT_NO,e.LIC_GRAT_POLICY_NO "+
			   		
					" from empmast e,emptran t,lookup l where  e.EMPNO="+empno+" and e.EMPNO=t.EMPNO and "+
					" t.srno=(select max(t1.srno) from emptran t1 where t1.empno="+empno+" ) and "+
					" l.lkp_srno=t.desig and l.lkp_code='DESIG' and "+
					" ((e.STATUS='N' and e.DOJ<='"+ReportDAO.EOM(dol)+"' ))  " +
					"  and e.LIC_GRAT_POLICY_NO="+PolicyNum+" "+
							
					"ELSE " +
					" select e.empno,e.EMPCODE,convert(nvarchar,e.DOJ,103) as DOJ,convert(nvarchar,e.DOB,103) as DOB,RTRIM(e.LNAME)+''+RTRIM(e.FNAME)+' '+RTRIM(e.MNAME) AS NAME, lkp_disc as DESIG, "+  
			   		" (SELECT  DATEDIFF( mm, (SELECT DOJ FROM EMPMAST WHERE EMPNO="+empno+"), GETDATE()) / 12) AS years," +
					" (SELECT DATEDIFF( mm, (SELECT DOJ FROM EMPMAST WHERE EMPNO="+empno+"), GETDATE()) % 12 )AS months ," +
					" (SELECT DATEDIFF( dd, DATEADD( mm, DATEDIFF( mm, (SELECT DOJ FROM EMPMAST WHERE EMPNO="+empno+"), GETDATE()), (SELECT DOJ FROM EMPMAST WHERE EMPNO="+empno+")), GETDATE())) AS Days , "+  
					" (select isnull((select ISNULL(p.INP_AMT,0) from PAYTRAN_STAGE p "+
			   		" where p.EMPNO="+empno+" and trncd=101 and TRNDT=(SELECT MAX(TRNDT) FROM PAYTRAN_STAGE WHERE EMPNO="+empno+" AND TRNCD=101 AND INP_AMT>0) ),0)) as BASIC, "+  
			   		" (select isnull((select ISNULL(p.INP_AMT,0) from PAYTRAN_STAGE p "+
			   		" where p.EMPNO="+empno+" and trncd=102 and TRNDT=(SELECT MAX(TRNDT) FROM PAYTRAN_STAGE WHERE EMPNO="+empno+" AND TRNCD=101 AND INP_AMT>0) ),0)) as DA, "+  
			   		" (select isnull((select ISNULL(p.INP_AMT,0) from PAYTRAN_STAGE p "+
			   		" where p.EMPNO="+empno+" and trncd=138 and TRNDT=(SELECT MAX(TRNDT) FROM PAYTRAN_STAGE WHERE EMPNO="+empno+" AND TRNCD=101 AND INP_AMT>0) ),0)) as VDA, "+
			   		" e.LIC_GRAT_NO,e.LIC_GRAT_POLICY_NO "+
			   		
					" from empmast e,emptran t,lookup l where  e.EMPNO="+empno+" and e.EMPNO=t.EMPNO and "+
					" t.srno=(select max(t1.srno) from emptran t1 where t1.empno="+empno+" ) and "+
					" l.lkp_srno=t.desig and l.lkp_code='DESIG' and "+
					" ((e.STATUS='N' and e.DOJ<='"+ReportDAO.EOM(dol)+"' ))  and e.LIC_GRAT_POLICY_NO="+PolicyNum+" ");
		}
		
		System.out.println(record_query);
		ResultSet rs=st1.executeQuery(record_query);
		
		while(rs.next())
		{
			k++;
		int  grosssal=0;
		int  servicesal=0;
			HSSFRow row = sheet.createRow((short)i++);
			
			
			row.createCell((short) 0).setCellValue(""+(count++));
			row.createCell((short) 1).setCellValue(""+rs.getString("EMPCODE"));
			row.createCell((short) 2).setCellValue(""+rs.getString("NAME"));
			
	
			row.createCell((short) 3).setCellValue(""+(rs.getString("DESIG")));
			row.createCell((short) 4).setCellValue(""+rs.getString("BASIC"));
			row.createCell((short) 5).setCellValue(""+rs.getString("DA"));
			grosssal=rs.getInt("BASIC")+rs.getInt("DA")+rs.getInt("VDA");
			
			row.createCell((short) 6).setCellValue(""+rs.getString("VDA"));
			row.createCell((short) 7).setCellValue(""+UtilityDAO.trans(Math.round(grosssal), "9,99,999","", false, false));
			 String yearofsrvice=String.valueOf(rs.getInt("years"));
			 //String month=String.valueOf(rs.getInt("months"));
			 
			 yearofsrvice= (((rs.getInt("months")==6 && rs.getInt("Days")>=1)||rs.getInt("months")>6)?String.valueOf((Integer.parseInt(yearofsrvice)+1)):yearofsrvice);
			 
			 if(Integer.parseInt(yearofsrvice)<=0){
				 row.createCell((short) 8).setCellValue(""+rs.getInt("months")+" Month ");
			 }else{
			row.createCell((short) 8).setCellValue(""+yearofsrvice+" Year ");
			 }
			servicesal=(((grosssal*(Integer.parseInt(yearofsrvice)))*15)/26);
			 if((Integer.parseInt(yearofsrvice)<=0)){
				 row.createCell((short) 9).setCellValue("--"); 
				 System.out.println("empno : "+rs.getString("empno"));
			 }
			 else{
				 row.createCell((short) 9).setCellValue(""+UtilityDAO.trans(Math.round(servicesal), "9,99,999","", false, false));
			 }
			row.createCell((short) 10).setCellValue(""+rs.getString("DOJ"));
			row.createCell((short) 11).setCellValue(""+rs.getString("DOB"));
			row.createCell((short) 12).setCellValue(""+rs.getString("LIC_GRAT_POLICY_NO")==null?"":rs.getString("LIC_GRAT_POLICY_NO"));
			row.createCell((short) 13).setCellValue(""+rs.getString("LIC_GRAT_NO")==null?"":rs.getString("LIC_GRAT_NO"));
			
		}
			
		}
		
		HSSFRow row1 = sheet.createRow((short) i);
		row1.createCell((short) 0).setCellValue(" ");
		  row1 = sheet.createRow((short) i+1);
	//	row.createCell((short) 0).setCellValue("Report Date And Time= " +df.format(calobj.getTime()));
		
		row1.createCell((short) 1).setCellValue("Total Employee : =  " +k );
		hwb.write(out1);
		out1.close();
		st.close();
	    con.close();
	    
		System.out.println("Result OK.....");
		
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	// employee List..
	public static void employeelistexcel(String[] results,String BranchType,String SelectedBranch,String desigtype,String RangeFrom,String RangeTo,String filepath,String imgpath)
	{
		System.out.println("i am employeelistexcel");
	System.out.println(""+results.length);
	int i = 0;
	Properties prop = new Properties();
	int brtot[] = new int[results.length];
	 int ALLbrtot[] = new int[results.length];
	 int PBRCD = 0;
	 int gross = 0;
	 int rownum=10;
     try
     {
    	 ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream stream = classLoader.getResourceAsStream("constant.properties");
		prop.load(stream);
     }
     catch (Exception e) {System.out.println("Error in constant properties Manager "+e);}
     
	try
	{
		System.out.println("Into Generalised_EmpListDAO.....");
		RepoartBean repBean  = new RepoartBean();
		
		
		LookupHandler lkh=new LookupHandler();
		System.out.println( filepath);
	FileOutputStream out1 = new FileOutputStream(new File(filepath));
	HSSFWorkbook hwb=new HSSFWorkbook();
	HSSFSheet sheet =  hwb.createSheet("EmpNewlist");
	
	SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
	System.out.println("1111.....");
	
	
   HSSFCellStyle my_style = hwb.createCellStyle();
   HSSFCellStyle my_style1 = hwb.createCellStyle();

   HSSFFont my_font=hwb.createFont();
   my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
   my_style.setFont(my_font);
 
   
	HSSFRow rowtitle=   sheet.createRow((short)0);
	HSSFCell cell = rowtitle.createCell((short) 1);
	cell.setCellValue(prop.getProperty("bankName1"));
	cell.setCellStyle(my_style);
	HSSFRow rowtitle1=   sheet.createRow((short)1);
	HSSFCell cell1 = rowtitle1.createCell((short) 1);
	cell1.setCellValue(prop.getProperty("addressForReport"));
	cell1.setCellStyle(my_style);
	HSSFRow rowtitle2=   sheet.createRow((short)2);
	HSSFCell cell2 = rowtitle2.createCell((short) 1);
	cell2.setCellValue(prop.getProperty("contactForReport"));
	cell2.setCellStyle(my_style);
	HSSFRow rowtitle31=   sheet.createRow((short)3);
	cell2 = rowtitle31.createCell((short) 1);
	cell2.setCellValue(	prop.getProperty("mailForReport"));
	cell2.setCellStyle(my_style);
	HSSFRow rowtitle3=   sheet.createRow((short)4);
	cell2=rowtitle3.createCell((short) 1);
	cell2.setCellValue("");
	cell2.setCellStyle(my_style);
	HSSFRow rowtitle4=   sheet.createRow((short)5);
	rowtitle4.createCell((short) 0).setCellValue("");
	HSSFRow rowtitle5=   sheet.createRow((short)6);
	rowtitle5.createCell((short) 0).setCellValue("");
	
	
	HSSFFont blueFont = hwb.createFont();
	blueFont.setColor(HSSFColor.BLUE.index);
	
	HSSFCellStyle style = hwb.createCellStyle();
	//style.setFont(blueFont);
	style.setFillForegroundColor(HSSFColor.BLUE.index);
	
	
	/*HSSFRow head=   sheet.createRow((short)7);
	head.createCell((short) 0).setCellValue("");
	HSSFRow heading=   sheet.createRow((short)8);
	HSSFCell cell3 = heading.createCell((short) 0); 

	cell3.setCellValue("");
	cell3.setCellStyle(my_style1);
	HSSFRow rowhead=   sheet.createRow((short)9);*/
    sheet.createFreezePane( 0, 10, 0, 10 );
  
   my_style1.setAlignment((short) 2);
   my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
   my_style1.setFont(my_font);
   
    Connection con =null;
    ReportDAO.OpenCon("", "", "",repBean);
    con = repBean.getCn();	
//	Statement st=con.createStatement();
//	Statement st1=con.createStatement();
	int count=1,srno = 0;
	ResultSet rs = null;
	ResultSet rs1 = null;
	Statement st = null;
	Statement st1 = null;
	st = con.createStatement(
			ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_READ_ONLY);
	st1 = con.createStatement(
			ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_READ_ONLY);
	
	String Query="";
	String Query1="";
	int totalEmployee =0;
	int Employee = 0;
	String site_id ="";
	String site_name ="";
	String Project_Code ="";
	
	if(BranchType.equalsIgnoreCase("1"))
	{
		Query1 = "select * from Project_Sites order by SITE_ID";
	}
	else if(BranchType.equalsIgnoreCase("2")){
		Query1 = "select * from Project_Sites where SITE_ID =" +SelectedBranch ;
	}
	/*else if(BranchType.equalsIgnoreCase("3")){
     	Query1 = "select * from Project_Sites order by SITE_ID";
	}*/
	else if(BranchType.equalsIgnoreCase("4")){
		Query1 = "select * from Project_Sites where SITE_ID  between "+RangeFrom +"and "+RangeTo;
	}
	System.out.println("Query is@@@ " +Query1);
	st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
//	System.out.println("query is: "+Query);
	rs1 = st1.executeQuery(Query1);
	int cnt=0;
	while(rs1.next())
	{
		site_id = rs1.getString("Site_ID");
		site_name = rs1.getString("site_name");
		Project_Code = rs1.getString("Project_Code");
	if(BranchType.equalsIgnoreCase("1"))
	{
	Query = ""
			+ "SELECT e1.*, ps.site_name AS BRANCH_NANE, t.acno,  p.net_amt, t.prj_srno, t.prj_code as BRANCH_CODE,"
			+ "       ( e1.fname + '   ' + e1.mname + '   ' + e1.lname )    AS EMPNAME, l.lkp_disc AS Designation, "
			+ "       l7.lkp_disc    AS BloodGrp, l9.lkp_disc AS DEPARTMENT,"
			+ "       (SELECT TOP 1 lkp_disc  FROM   lookup lkp WHERE  lkp.lkp_srno = (SELECT TOP 1 q1.degree FROM   qual q1 WHERE  q1.srno = (SELECT Max(q2.srno) FROM   qual q2 WHERE  q2.empno = q1.empno) "
			+ "        AND q1.empno = e1.empno)  AND lkp.lkp_code = 'ED')    AS Qualification, "
			+ "       (SELECT Isnull((SELECT Isnull(( ex.addr1 + '   ' + ex.addr2 ), ' ') FROM   empaux ex  WHERE  ex.empno = e1.empno  AND ex.addrtype = 'PA' AND id = (SELECT Min(id) FROM   empaux WHERE  empno = e1.empno)), ''))AS PERMANENT_ADDRESS, "
			+ "       (SELECT Isnull((SELECT Isnull(( ee.addr1 + '   ' + ee.addr2 ), ' ') FROM   empaux ee WHERE  ee.empno = e1.empno AND ee.addrtype = 'CA' AND id = (SELECT Max(id) FROM   empaux WHERE  empno = e1.empno)), ''))AS CURRENT_ADDRESS, "
			+ "       (SELECT Isnull((SELECT Isnull(( xa.telno ), ' ') FROM   empaux xa WHERE  xa.empno = e1.empno  AND xa.addrtype = 'PA' AND id = (SELECT Min(id) FROM   empaux WHERE  empno = e1.empno)), ''))AS PERMANENT_TELNO, "
			+ "       (SELECT Isnull((SELECT Isnull(( xa.telno ), ' ') FROM   empaux xa WHERE  xa.empno = e1.empno AND xa.addrtype = 'CA' AND id = (SELECT Max(id) FROM   empaux WHERE  empno = e1.empno)), ''))AS CURRENT_TELNO, "
			+ "       (SELECT Isnull((SELECT Isnull(( l1.lkp_disc), ' ') FROM   lookup l1,EMPAUX ep WHERE  l1.lkp_srno = ep.city AND l1.lkp_code = 'city' and ep.ADDRTYPE='PA' AND id = (SELECT Min(id) FROM   empaux WHERE  empno = e1.empno)), ''))as PERMANENT_CITY, "
			+ "       (SELECT Isnull((SELECT Isnull(( l2.lkp_disc), ' ') FROM   lookup l2,EMPAUX eep WHERE  l2.lkp_srno = eep.city AND l2.lkp_code = 'city' and eep.ADDRTYPE='CA' AND id = (SELECT Max(id) FROM   empaux WHERE  empno = e1.empno)), ''))as CURRENT_CITY, "
			+ "       (SELECT Isnull((SELECT Isnull(( pp.PIN ), ' ') FROM   empaux pp WHERE  pp.empno = e1.empno AND pp.addrtype = 'PA' AND id = (SELECT Min(id) FROM   empaux WHERE  empno = e1.empno)), ''))AS PERMANENT_PINCODE, "
			+ "       (SELECT Isnull((SELECT Isnull(( cp.PIN ), ' ') FROM   empaux cp WHERE  cp.empno = e1.empno AND cp.addrtype = 'CA' AND id = (SELECT Max(id) FROM   empaux WHERE  empno = e1.empno)), ''))AS CURRENT_PINCODE,e1.GENDER as GENDER "
			+ "       FROM   empmast e1 "
			+ "       LEFT JOIN paytran_stage p ON p.trncd = 999 "
			+ "       LEFT JOIN emptran t  ON ( e1.empno = t.empno AND e1.empno = p.empno ) "
			+ "       LEFT JOIN lookup l ON l.lkp_code = 'DESIG' "
			+ "       LEFT JOIN lookup l7 ON l7.lkp_code = 'BLOOD_GRP' "
			+ "       LEFT JOIN project_sites ps ON t.prj_srno = ps.site_id "
			+ "       LEFT JOIN lookup l9  ON l9.lkp_code = 'DEPT'"
			+ "       WHERE  e1.status = 'A' "
			+ "       AND p.trndt = (SELECT Max(p1.trndt) FROM   paytran_stage p1  WHERE  p.empno = p1.empno AND trncd = 101) "
			+ "       AND t.srno = (SELECT Max(e2.srno) FROM   emptran e2 WHERE  e2.empno = e1.empno) "
			+ "       AND ( l.lkp_code = 'DESIG' AND l.lkp_srno = (SELECT e3.desig FROM   emptran e3 WHERE  e3.srno = (SELECT Max(e2.srno) FROM   emptran e2 WHERE  e2.empno = e1.empno) AND e3.empno = e1.empno) ) "
			+ "       AND T.prj_srno = "+site_id+" "
			+ "       AND l7.lkp_code = 'BLOOD_GRP' "
			+ "       AND ( CONVERT(NVARCHAR(20), l7.lkp_srno) = (SELECT CASE WHEN bgrp = '' THEN 0 ELSE Isnull(bgrp, '0')  END AS bgrp FROM   empmast WHERE  empno = e1.empno) ) "
			+ "       AND ( l9.lkp_code = 'DEPT' AND l9.lkp_srno = (SELECT e9.DEPT FROM   emptran e9  WHERE  e9.srno = (SELECT Max(e2.srno) FROM   emptran e2 WHERE  e2.empno = e9.empno) AND e9.empno = e1.empno) ) "
			+ "       ORDER  BY t.prj_srno";
	}
	 if(BranchType.equalsIgnoreCase("2"))
	 {
		 Query = ""
				 + "SELECT e1.*, ps.site_name AS BRANCH_NANE, t.acno,  p.net_amt, t.prj_srno,t.prj_code as BRANCH_CODE, "
				 + "       ( e1.fname + '   ' + e1.mname + '   ' + e1.lname )    AS EMPNAME, l.lkp_disc AS Designation, "
				 + "       l7.lkp_disc    AS BloodGrp,l9.lkp_disc AS DEPARTMENT, "
				 + "       (SELECT TOP 1 lkp_disc  FROM   lookup lkp WHERE  lkp.lkp_srno = (SELECT TOP 1 q1.degree FROM   qual q1 WHERE  q1.srno = (SELECT Max(q2.srno) FROM   qual q2 WHERE  q2.empno = q1.empno) "
				 + "        AND q1.empno = e1.empno)  AND lkp.lkp_code = 'ED')    AS Qualification, "
				 + "       (SELECT Isnull((SELECT Isnull(( ex.addr1 + '   ' + ex.addr2 ), ' ') FROM   empaux ex  WHERE  ex.empno = e1.empno  AND ex.addrtype = 'PA' AND id = (SELECT Min(id) FROM   empaux WHERE  empno = e1.empno)), ''))AS PERMANENT_ADDRESS, "
				 + "       (SELECT Isnull((SELECT Isnull(( ee.addr1 + '   ' + ee.addr2 ), ' ') FROM   empaux ee WHERE  ee.empno = e1.empno AND ee.addrtype = 'CA' AND id = (SELECT Max(id) FROM   empaux WHERE  empno = e1.empno)), ''))AS CURRENT_ADDRESS, "
				 + "       (SELECT Isnull((SELECT Isnull(( xa.telno ), ' ') FROM   empaux xa WHERE  xa.empno = e1.empno  AND xa.addrtype = 'PA' AND id = (SELECT Min(id) FROM   empaux WHERE  empno = e1.empno)), ''))AS PERMANENT_TELNO, "
				 + "       (SELECT Isnull((SELECT Isnull(( xa.telno ), ' ') FROM   empaux xa WHERE  xa.empno = e1.empno AND xa.addrtype = 'CA' AND id = (SELECT Max(id) FROM   empaux WHERE  empno = e1.empno)), ''))AS CURRENT_TELNO, "
				 + "       (SELECT Isnull((SELECT Isnull(( l1.lkp_disc), ' ') FROM   lookup l1,EMPAUX ep WHERE  l1.lkp_srno = ep.city AND l1.lkp_code = 'city' and ep.ADDRTYPE='PA' AND id = (SELECT Min(id) FROM   empaux WHERE  empno = e1.empno)), ''))as PERMANENT_CITY, "
				 + "       (SELECT Isnull((SELECT Isnull(( l2.lkp_disc), ' ') FROM   lookup l2,EMPAUX eep WHERE  l2.lkp_srno = eep.city AND l2.lkp_code = 'city' and eep.ADDRTYPE='CA' AND id = (SELECT Max(id) FROM   empaux WHERE  empno = e1.empno)), ''))as CURRENT_CITY, "
				 + "       (SELECT Isnull((SELECT Isnull(( pp.PIN ), ' ') FROM   empaux pp WHERE  pp.empno = e1.empno AND pp.addrtype = 'PA' AND id = (SELECT Min(id) FROM   empaux WHERE  empno = e1.empno)), ''))AS PERMANENT_PINCODE, "
				 + "       (SELECT Isnull((SELECT Isnull(( cp.PIN ), ' ') FROM   empaux cp WHERE  cp.empno = e1.empno AND cp.addrtype = 'CA' AND id = (SELECT Max(id) FROM   empaux WHERE  empno = e1.empno)), ''))AS CURRENT_PINCODE,e1.GENDER as GENDER "
				 + "       FROM   empmast e1 "
				 + "       LEFT JOIN paytran_stage p ON p.trncd = 999 "
				 + "       LEFT JOIN emptran t  ON ( e1.empno = t.empno AND e1.empno = p.empno ) "
				 + "       LEFT JOIN lookup l ON l.lkp_code = 'DESIG' "
				 + "       LEFT JOIN lookup l7 ON l7.lkp_code = 'BLOOD_GRP' "
				 + "       LEFT JOIN project_sites ps ON t.prj_srno = ps.site_id "
				 + "       LEFT JOIN lookup l9  ON l9.lkp_code = 'DEPT'"
				 + "       WHERE  e1.status = 'A' "
				 + "       AND p.trndt = (SELECT Max(p1.trndt) FROM   paytran_stage p1  WHERE  p.empno = p1.empno AND trncd = 101) "
				 + "       AND t.srno = (SELECT Max(e2.srno) FROM   emptran e2 WHERE  e2.empno = e1.empno) "
				 + "       AND ( l.lkp_code = 'DESIG' AND l.lkp_srno = (SELECT e3.desig FROM   emptran e3 WHERE  e3.srno = (SELECT Max(e2.srno) FROM   emptran e2 WHERE  e2.empno = e1.empno) AND e3.empno = e1.empno) ) "
				 + "      and T.prj_srno = "+site_id+" "
				 + "       AND l7.lkp_code = 'BLOOD_GRP' "
				 + "       AND ( CONVERT(NVARCHAR(20), l7.lkp_srno) = (SELECT CASE WHEN bgrp = '' THEN 0 ELSE Isnull(bgrp, '0')  END AS bgrp FROM   empmast WHERE  empno = e1.empno) ) "
				 + "       AND ( l9.lkp_code = 'DEPT' AND l9.lkp_srno = (SELECT e9.DEPT FROM   emptran e9  WHERE  e9.srno = (SELECT Max(e2.srno) FROM   emptran e2 WHERE  e2.empno = e9.empno) AND e9.empno = e1.empno) ) "
				 + "       ORDER  BY e1.empcode";
	 }
	/* if(BranchType.equalsIgnoreCase("3"))
	 {
		 Query = ("SELECT  e1.*,ps.SITE_NAME as BRANCH_NANE,t.ACNO,"+
			       " p.net_amt,t.prj_srno, "+
			       " ( e1.fname + '   ' + e1.mname + '   ' + e1.lname )AS EMPNAME, "+
			       " l.lkp_disc AS Designation, " +
			       " l7.lkp_disc  AS BloodGrp,"+
			       " (select top 1 lkp_disc from lookup lkp where lkp.LKP_SRNO = "+
			       " (select top 1 q1.DEGREE from QUAL q1 where q1.SRNO =(select max(q2.srno) from qual q2 where q2.empno=q1.empno) "+
			       " and q1.EMPNO=e1.EMPNO) and lkp.lkp_code ='ED') as Qualification,  "+
					"(select isnull((select ISNULL(( ex.addr1 + '   ' + ex.addr2 ),' ') from EMPAUX ex  where ex.empno=e1.empno and ex.ADDRTYPE='PA' and ID=(select min(id) from empaux where empno=e1.empno)),''))AS PERMANENT_ADDRESS, "+    
					"(select isnull((select ISNULL(( ee.addr1 + '   ' + ee.addr2 ),' ') from EMPAUX ee  where ee.empno=e1.empno and ee.ADDRTYPE='CA' and ID=(select min(id) from empaux where empno=e1.empno)),''))AS CURRENT_ADDRESS,   "+
					"(select isnull((select ISNULL(( xa.TELNO ),' ') from EMPAUX xa  where xa.empno=e1.empno and xa.ADDRTYPE='PA' and ID=(select min(id) from empaux where empno=e1.empno)),''))AS PERMANENT_TELNO, "+
					"(select isnull((select ISNULL(( xa.TELNO ),' ') from EMPAUX xa  where xa.empno=e1.empno and xa.ADDRTYPE='CA' and ID=(select Max(id) from empaux where empno=e1.empno)),''))AS CURRENT_TELNO "+ 
				   " FROM   empmast e1  "+
			       " left join PAYTRAN_STAGE p "+
			       " on p.trncd = 999 "+
			       " left join emptran t  "+
			       " on (e1.empno = t.empno and  e1.empno = p.empno) "+
			       " left join lookup l "+
			       " on l.lkp_code ='DESIG' " +
			       " LEFT JOIN lookup l7 ON l7.lkp_code = 'BLOOD_GRP' "+
			       " left join  Project_Sites ps "+
			       " on t.prj_srno = ps.SITE_ID "+
			       " WHERE  e1.status = 'A' " +
			       " and p.TRNDT=(select max(p1.trndt) from paytran_stage p1 where p.empno=p1.empno and TRNCD=101) "+
			       " AND t.srno = (SELECT Max(e2.srno) "+
			       " FROM   emptran e2 "+
			       " WHERE  e2.empno = e1.empno) "+
			       " AND (l.lkp_code ='DESIG' and l.lkp_srno = (SELECT e3.desig "+
			       "   FROM   emptran e3 "+
			       "  WHERE  e3.srno = (SELECT Max(e2.srno) "+
			       "  FROM   emptran e2 "+
			       "  WHERE  e2.empno = e1.empno) "+
			       " AND e3.empno = e1.empno)) "+
			       " and T.DESIG = "+desigtype+
			//       " and  t.PRJ_SRNO ="+site_id+" "+
			       "and   l7.lkp_code = 'BLOOD_GRP'   and (convert(nvarchar(20),l7.LKP_SRNO)= (select case when BGRP='' then 0 else isnull(BGRP,'0') end as bgrp from empmast where EMPNO=e1.empno) ) "+
			       " ORDER  BY e1.empcode ");
	 }*/
	 if(BranchType.equalsIgnoreCase("4"))
	 {
		 Query = ""
				 + "SELECT e1.*, ps.site_name AS BRANCH_NANE, t.acno,  p.net_amt, t.prj_srno, t.prj_code as BRANCH_CODE,"
				 + "       ( e1.fname + '   ' + e1.mname + '   ' + e1.lname )    AS EMPNAME, l.lkp_disc AS Designation, "
				 + "       l7.lkp_disc    AS BloodGrp, l9.lkp_disc AS DEPARTMENT,"
				 + "       (SELECT TOP 1 lkp_disc  FROM   lookup lkp WHERE  lkp.lkp_srno = (SELECT TOP 1 q1.degree FROM   qual q1 WHERE  q1.srno = (SELECT Max(q2.srno) FROM   qual q2 WHERE  q2.empno = q1.empno) "
				 + "        AND q1.empno = e1.empno)  AND lkp.lkp_code = 'ED')    AS Qualification, "
				 + "       (SELECT Isnull((SELECT Isnull(( ex.addr1 + '   ' + ex.addr2 ), ' ') FROM   empaux ex  WHERE  ex.empno = e1.empno  AND ex.addrtype = 'PA' AND id = (SELECT Min(id) FROM   empaux WHERE  empno = e1.empno)), ''))AS PERMANENT_ADDRESS, "
				 + "       (SELECT Isnull((SELECT Isnull(( ee.addr1 + '   ' + ee.addr2 ), ' ') FROM   empaux ee WHERE  ee.empno = e1.empno AND ee.addrtype = 'CA' AND id = (SELECT Max(id) FROM   empaux WHERE  empno = e1.empno)), ''))AS CURRENT_ADDRESS, "
				 + "       (SELECT Isnull((SELECT Isnull(( xa.telno ), ' ') FROM   empaux xa WHERE  xa.empno = e1.empno  AND xa.addrtype = 'PA' AND id = (SELECT Min(id) FROM   empaux WHERE  empno = e1.empno)), ''))AS PERMANENT_TELNO, "
				 + "       (SELECT Isnull((SELECT Isnull(( xa.telno ), ' ') FROM   empaux xa WHERE  xa.empno = e1.empno AND xa.addrtype = 'CA' AND id = (SELECT Max(id) FROM   empaux WHERE  empno = e1.empno)), ''))AS CURRENT_TELNO, "
				 + "       (SELECT Isnull((SELECT Isnull(( l1.lkp_disc), ' ') FROM   lookup l1,EMPAUX ep WHERE  l1.lkp_srno = ep.city AND l1.lkp_code = 'city' and ep.ADDRTYPE='PA' AND id = (SELECT Min(id) FROM   empaux WHERE  empno = e1.empno)), ''))as PERMANENT_CITY, "
				 + "       (SELECT Isnull((SELECT Isnull(( l2.lkp_disc), ' ') FROM   lookup l2,EMPAUX eep WHERE  l2.lkp_srno = eep.city AND l2.lkp_code = 'city' and eep.ADDRTYPE='CA' AND id = (SELECT Max(id) FROM   empaux WHERE  empno = e1.empno)), ''))as CURRENT_CITY, "
				 + "       (SELECT Isnull((SELECT Isnull(( pp.PIN ), ' ') FROM   empaux pp WHERE  pp.empno = e1.empno AND pp.addrtype = 'PA' AND id = (SELECT Min(id) FROM   empaux WHERE  empno = e1.empno)), ''))AS PERMANENT_PINCODE, "
				 + "       (SELECT Isnull((SELECT Isnull(( cp.PIN ), ' ') FROM   empaux cp WHERE  cp.empno = e1.empno AND cp.addrtype = 'CA' AND id = (SELECT Max(id) FROM   empaux WHERE  empno = e1.empno)), ''))AS CURRENT_PINCODE,e1.GENDER as GENDER "
				 + "       FROM   empmast e1 "
				 + "       LEFT JOIN paytran_stage p ON p.trncd = 999 "
				 + "       LEFT JOIN emptran t  ON ( e1.empno = t.empno AND e1.empno = p.empno ) "
				 + "       LEFT JOIN lookup l ON l.lkp_code = 'DESIG' "
				 + "       LEFT JOIN lookup l7 ON l7.lkp_code = 'BLOOD_GRP' "
				 + "       LEFT JOIN project_sites ps ON t.prj_srno = ps.site_id "
				 + "       LEFT JOIN lookup l9  ON l9.lkp_code = 'DEPT'"
				 + "       WHERE  e1.status = 'A' "
				 + "       AND p.trndt = (SELECT Max(p1.trndt) FROM   paytran_stage p1  WHERE  p.empno = p1.empno AND trncd = 101) "
				 + "       AND t.srno = (SELECT Max(e2.srno) FROM   emptran e2 WHERE  e2.empno = e1.empno) "
				 + "       AND ( l.lkp_code = 'DESIG' AND l.lkp_srno = (SELECT e3.desig FROM   emptran e3 WHERE  e3.srno = (SELECT Max(e2.srno) FROM   emptran e2 WHERE  e2.empno = e1.empno) AND e3.empno = e1.empno) ) "
				 + "       AND T.prj_srno = "+site_id+" "
				 +"        AND T.prj_srno between "+RangeFrom+" and "+RangeTo+ ""
				 + "       AND l7.lkp_code = 'BLOOD_GRP' "
				 + "       AND ( CONVERT(NVARCHAR(20), l7.lkp_srno) = (SELECT CASE WHEN bgrp = '' THEN 0 ELSE Isnull(bgrp, '0')  END AS bgrp FROM   empmast WHERE  empno = e1.empno) ) "
				 + "       AND ( l9.lkp_code = 'DEPT' AND l9.lkp_srno = (SELECT e9.DEPT FROM   emptran e9  WHERE  e9.srno = (SELECT Max(e2.srno) FROM   emptran e2 WHERE  e2.empno = e9.empno) AND e9.empno = e1.empno) ) "
				 + "       ORDER  BY e1.empcode";
	 }
	    st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		rs = st.executeQuery(Query);
		System.out.println("query is: "+Query);
		
		
		
	    				HSSFRow rowtitle6=   sheet.createRow((short)(rownum++)+1);
	    				HSSFCell cell31 = rowtitle6.createCell((short) 1); 
	    				cell31.setCellValue("Employee List For Project Site is :- "+(site_name + Project_Code));
	    				cell31.setCellStyle(my_style);
	    				
					//	rowtitle6.createCell((short) 1).setCellValue("Employee List For Project Site is :- "+(site_name + Project_Code));

						HSSFRow heading1=   sheet.createRow((short)(rownum++)+1);
						for(i=0;i<results.length;i++)
						{
							sheet.setColumnWidth((short)i, (short)11000);
							//heading1.createCell((short) i).setCellValue(""+results[i]);
					
							HSSFCell cell21 = heading1.createCell((short) i);
							cell21.setCellValue(""+results[i]);
							cell21.setCellStyle(my_style);
				}	
				
				while(rs.next())
				{
					
					int k=0;
					count++;
					srno++;
					HSSFRow row = sheet.createRow((short)(rownum++)+1);
					for(int j=0;j<results.length;j++)
					{
					
						String ex="";
					    ex = rs.getString(results[j])==null?" ":rs.getString(results[j]); 
					    row.createCell((short) k).setCellValue(""+ex);
					    k++;
					}
				
					Employee+=1;
					}
				//	HSSFRow row1 = sheet.createRow((short) (rownum++));
				    HSSFRow row1 = sheet.createRow((short) (rownum++)+1);
					row1.createCell((short) 0).setCellValue(" ");
					row1 = sheet.createRow((short) (rownum++));
					row1.createCell((short) 1).setCellValue("Number Of Employee :-  " +Employee );
					/*HSSFCell cell32 = rowtitle6.createCell((short) 1); 
					cell32.setCellValue("Number Of Employee :-  " +Employee );
					cell32.setCellStyle(my_style);*/
					
		//			row1.createCell((short) 1).setCellValue("Number Of Employee :-  " +Employee );
					totalEmployee = totalEmployee+ Employee;	
					Employee = 0;
				}	
				
				HSSFRow row2 = sheet.createRow((short) (rownum++)+1);
				row2.createCell((short) 0).setCellValue(" ");
				row2 = sheet.createRow((short) (rownum++)+1);
				row2.createCell((short) 1).setCellValue("Total Number Of Employee :- " +totalEmployee );
				hwb.write(out1);
				out1.close();
				st.close();
				st1.close();
				con.close();
    
				System.out.println("Result OK.....");
	
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
	}
		
		// Emplist Desig Wise...
		public static void employee_desigWiselistexcel(String[] results,String BranchType,String SelectedBranch,String desigtype,String DeptType,String filepath,String imgpath)
		{
			System.out.println("i am employee_desigWiselistexcel");
		System.out.println(""+results.length);
		int i = 0;
		Properties prop = new Properties();
		int brtot[] = new int[results.length];
		 int ALLbrtot[] = new int[results.length];
		 int PBRCD = 0;
		 int gross = 0;
		
	     try
	     {
	    	 ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream stream = classLoader.getResourceAsStream("constant.properties");
			prop.load(stream);
	     }
	     catch (Exception e) {System.out.println("Error in constant properties Manager "+e);}
	     
		try
		{
			RepoartBean repBean  = new RepoartBean();
			LookupHandler lkh=new LookupHandler();
			System.out.println( filepath);
		FileOutputStream out1 = new FileOutputStream(new File(filepath));
		HSSFWorkbook hwb=new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("EmpNewlist");
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
		System.out.println("1111.....");
		
		
	   HSSFCellStyle my_style = hwb.createCellStyle();
	   HSSFCellStyle my_style1 = hwb.createCellStyle();

	   HSSFFont my_font=hwb.createFont();
	   my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	   my_style.setFont(my_font);
	 
	   
		HSSFRow rowtitle=   sheet.createRow((short)0);
		HSSFCell cell = rowtitle.createCell((short) 1);
		cell.setCellValue(prop.getProperty("bankName1"));
		cell.setCellStyle(my_style);
		HSSFRow rowtitle1=   sheet.createRow((short)1);
		HSSFCell cell1 = rowtitle1.createCell((short) 1);
		cell1.setCellValue(prop.getProperty("addressForReport"));
		cell1.setCellStyle(my_style);
		HSSFRow rowtitle2=   sheet.createRow((short)2);
		HSSFCell cell2 = rowtitle2.createCell((short) 1);
		cell2.setCellValue(prop.getProperty("contactForReport"));
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle31=   sheet.createRow((short)3);
		cell2 = rowtitle31.createCell((short) 1);
		cell2.setCellValue(	prop.getProperty("mailForReport"));
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle3=   sheet.createRow((short)4);
		cell2=rowtitle3.createCell((short) 1);
		cell2.setCellValue("");
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle4=   sheet.createRow((short)5);
		rowtitle4.createCell((short) 0).setCellValue("");
		HSSFRow rowtitle5=   sheet.createRow((short)6);
		rowtitle5.createCell((short) 0).setCellValue("");
		
		
		HSSFFont blueFont = hwb.createFont();
		blueFont.setColor(HSSFColor.BLUE.index);
		
		HSSFCellStyle style = hwb.createCellStyle();
		//style.setFont(blueFont);
		style.setFillForegroundColor(HSSFColor.BLUE.index);
	    sheet.createFreezePane( 0, 10, 0, 10 );
	  
	   my_style1.setAlignment((short) 2);
	   my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	   my_style1.setFont(my_font);
	   
	    Connection con =null;
	    ReportDAO.OpenCon("", "", "",repBean);
	    con = repBean.getCn();	
		Statement st=con.createStatement();
		int count=1,srno = 0;
		ResultSet rs = null;
		String Query="";
		String Constant="";
		if(BranchType.equals("3")){System.out.println("in type 3");
			Constant=" and T.DESIG = "+desigtype+" ";
		}
		else if(BranchType.equals("5")){System.out.println("in type 5");
			Constant=" and T.DEPT = "+DeptType+" ";
		}
//		 if(BranchType.equalsIgnoreCase("3"))
//		 {
			 Query = ""
					 + "SELECT e1.*, ps.site_name AS BRANCH_NANE, t.acno,  p.net_amt, t.prj_srno,t.prj_code as BRANCH_CODE, "
					 + "       ( e1.fname + '   ' + e1.mname + '   ' + e1.lname )    AS EMPNAME, l.lkp_disc AS Designation, "
					 + "       l7.lkp_disc    AS BloodGrp,l9.lkp_disc AS DEPARTMENT, "
					 + "       (SELECT TOP 1 lkp_disc  FROM   lookup lkp WHERE  lkp.lkp_srno = (SELECT TOP 1 q1.degree FROM   qual q1 WHERE  q1.srno = (SELECT Max(q2.srno) FROM   qual q2 WHERE  q2.empno = q1.empno) "
					 + "        AND q1.empno = e1.empno)  AND lkp.lkp_code = 'ED')    AS Qualification, "
					 + "       (SELECT Isnull((SELECT Isnull(( ex.addr1 + '   ' + ex.addr2 ), ' ') FROM   empaux ex  WHERE  ex.empno = e1.empno  AND ex.addrtype = 'PA' AND id = (SELECT Min(id) FROM   empaux WHERE  empno = e1.empno)), ''))AS PERMANENT_ADDRESS, "
					 + "       (SELECT Isnull((SELECT Isnull(( ee.addr1 + '   ' + ee.addr2 ), ' ') FROM   empaux ee WHERE  ee.empno = e1.empno AND ee.addrtype = 'CA' AND id = (SELECT Max(id) FROM   empaux WHERE  empno = e1.empno)), ''))AS CURRENT_ADDRESS, "
					 + "       (SELECT Isnull((SELECT Isnull(( xa.telno ), ' ') FROM   empaux xa WHERE  xa.empno = e1.empno  AND xa.addrtype = 'PA' AND id = (SELECT Min(id) FROM   empaux WHERE  empno = e1.empno)), ''))AS PERMANENT_TELNO, "
					 + "       (SELECT Isnull((SELECT Isnull(( xa.telno ), ' ') FROM   empaux xa WHERE  xa.empno = e1.empno AND xa.addrtype = 'CA' AND id = (SELECT Max(id) FROM   empaux WHERE  empno = e1.empno)), ''))AS CURRENT_TELNO, "
					 + "       (SELECT Isnull((SELECT Isnull(( l1.lkp_disc), ' ') FROM   lookup l1,EMPAUX ep WHERE  l1.lkp_srno = ep.city AND l1.lkp_code = 'city' and ep.ADDRTYPE='PA' AND id = (SELECT Min(id) FROM   empaux WHERE  empno = e1.empno)), ''))as PERMANENT_CITY, "
					 + "       (SELECT Isnull((SELECT Isnull(( l2.lkp_disc), ' ') FROM   lookup l2,EMPAUX eep WHERE  l2.lkp_srno = eep.city AND l2.lkp_code = 'city' and eep.ADDRTYPE='CA' AND id = (SELECT Max(id) FROM   empaux WHERE  empno = e1.empno)), ''))as CURRENT_CITY, "
					 + "       (SELECT Isnull((SELECT Isnull(( pp.PIN ), ' ') FROM   empaux pp WHERE  pp.empno = e1.empno AND pp.addrtype = 'PA' AND id = (SELECT Min(id) FROM   empaux WHERE  empno = e1.empno)), ''))AS PERMANENT_PINCODE, "
					 + "       (SELECT Isnull((SELECT Isnull(( cp.PIN ), ' ') FROM   empaux cp WHERE  cp.empno = e1.empno AND cp.addrtype = 'CA' AND id = (SELECT Max(id) FROM   empaux WHERE  empno = e1.empno)), ''))AS CURRENT_PINCODE,e1.GENDER as GENDER "
					 + "		FROM   empmast e1 "
					 + "       LEFT JOIN paytran_stage p ON p.trncd = 999 "
					 + "       LEFT JOIN emptran t  ON ( e1.empno = t.empno AND e1.empno = p.empno ) "
					 + "       LEFT JOIN lookup l ON l.lkp_code = 'DESIG' "
					 + "       LEFT JOIN lookup l7 ON l7.lkp_code = 'BLOOD_GRP' "
					 + "       LEFT JOIN project_sites ps ON t.prj_srno = ps.site_id "
					 + "       LEFT JOIN lookup l9  ON l9.lkp_code = 'DEPT'"
					 + "	   WHERE  e1.status = 'A' "
					 + "       AND p.trndt = (SELECT Max(p1.trndt) FROM   paytran_stage p1  WHERE  p.empno = p1.empno AND trncd = 101) "
					 + "       AND t.srno = (SELECT Max(e2.srno) FROM   emptran e2 WHERE  e2.empno = e1.empno) "
					 + "       AND ( l.lkp_code = 'DESIG' AND l.lkp_srno = (SELECT e3.desig FROM   emptran e3 WHERE  e3.srno = (SELECT Max(e2.srno) FROM   emptran e2 WHERE  e2.empno = e1.empno) AND e3.empno = e1.empno) ) " +Constant+""
			//		 + "       "+Constant+" "
					 + "       AND l7.lkp_code = 'BLOOD_GRP' "
					 + "       AND ( CONVERT(NVARCHAR(20), l7.lkp_srno) = (SELECT CASE WHEN bgrp = '' THEN 0 ELSE Isnull(bgrp, '0')  END AS bgrp FROM   empmast WHERE  empno = e1.empno) ) "
					 + "       AND ( l9.lkp_code = 'DEPT' AND l9.lkp_srno = (SELECT e9.DEPT FROM   emptran e9  WHERE  e9.srno = (SELECT Max(e2.srno) FROM   emptran e2 WHERE  e2.empno = e9.empno) AND e9.empno = e1.empno) ) "
					 + "       ORDER  BY e1.empcode";
	//	 }
			 System.out.println("AJ: "+Query);
				rs = st.executeQuery(Query);
				System.out.println("query is: "+Query);
					int minIndex = 0;
					HSSFRow heading1=   sheet.createRow((short)8);
					for(i=0;i<results.length;i++)
					{
						sheet.setColumnWidth((short)i, (short)11000);
						//heading1.createCell((short) i).setCellValue(""+results[i]);
						
						HSSFCell cell21 = heading1.createCell((short) i);
						cell21.setCellValue(""+results[i]);
						cell21.setCellStyle(my_style);
					}	
					i=10;
					while(rs.next())
					{
						int k=0;
						count++;
						srno++;
						HSSFRow row = sheet.createRow((short)i++);
						for(int j=0;j<results.length;j++)
						{
							String ex="";
						    ex = rs.getString(results[j])==null?" ":rs.getString(results[j]); 
						row.createCell((short) k).setCellValue(""+ex);
						k++;
						}
					}
					hwb.write(out1);
					out1.close();
					st.close();
					con.close();
	    
					System.out.println("Result OK.....");
		
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
		}
		
		
		public static void YearlyEarDedReportexcel(String dt,String employeetype,String rpttype,String filepath,String imgpath)
		{
			
			String EOY=ReportDAO.EoFinancialy(dt);
			String BOY=ReportDAO.BoFinancialy(dt);
			
			Properties prop = new Properties();
			//int brtot[] = new int[15];
			String query="";
		     try
		     {
			
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				InputStream stream = classLoader.getResourceAsStream("constant.properties");
				prop.load(stream);
		     }
		     catch (Exception e) {System.out.println("Error in constant properties Manager "+e);}
		     
			try
			{
				
				RepoartBean repBean  = new RepoartBean();
				Connection con =null;
				
			FileOutputStream out1 = new FileOutputStream(new File(filepath));
			HSSFWorkbook hwb=new HSSFWorkbook();
			HSSFSheet sheet =  hwb.createSheet("Yearly_"+rpttype);
			
			HSSFCellStyle my_style = hwb.createCellStyle();
		    HSSFCellStyle my_style1 = hwb.createCellStyle();

		       HSSFFont my_font=hwb.createFont();
		       my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		       my_style.setFont(my_font);
		     
		       
				HSSFRow rowtitle=   sheet.createRow((short)0);
				HSSFCell cell = rowtitle.createCell((short) 9);
				
			//	System.out.println("2222.....");
				
				cell.setCellValue(prop.getProperty("bankName1"));
				cell.setCellStyle(my_style);
				HSSFRow rowtitle1=   sheet.createRow((short)1);
				HSSFCell cell1 = rowtitle1.createCell((short) 7);
				cell1.setCellValue(prop.getProperty("addressForReport"));
				cell1.setCellStyle(my_style);
				HSSFRow rowtitle2=   sheet.createRow((short)2);
				HSSFCell cell2 = rowtitle2.createCell((short) 9);
				cell2.setCellValue(prop.getProperty("contactForReport"));
				cell2.setCellStyle(my_style);
				HSSFRow rowtitle31=   sheet.createRow((short)3);
				cell2 = rowtitle31.createCell((short) 9);
				cell2.setCellValue(	prop.getProperty("mailForReport"));
				cell2.setCellStyle(my_style);
				HSSFRow rowtitle3=   sheet.createRow((short)4);
				cell2=rowtitle3.createCell((short) 2);
				cell2.setCellValue("");
				cell2.setCellStyle(my_style);
				HSSFRow rowtitle4=   sheet.createRow((short)5);
				rowtitle4.createCell((short) 0).setCellValue("");
				HSSFRow rowtitle5=   sheet.createRow((short)6);
				rowtitle5.createCell((short) 0).setCellValue("");
				
				
				HSSFFont blueFont = hwb.createFont();
				blueFont.setColor(HSSFColor.BLUE.index);
				
				HSSFCellStyle style = hwb.createCellStyle();
				//style.setFont(blueFont);
				style.setFillForegroundColor(HSSFColor.BLUE.index);
				
				
				HSSFRow head=   sheet.createRow((short)7);
				head.createCell((short) 0).setCellValue("");
				HSSFRow heading=   sheet.createRow((short)8);
				HSSFCell cell3 = heading.createCell((short) 0); 

				cell3.setCellValue("");
				cell3.setCellStyle(my_style1);
				HSSFRow rowhead=   sheet.createRow((short)9);
		       sheet.createFreezePane( 0, 10, 0, 10 );
		      
		       my_style1.setAlignment((short) 2);
		       my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		       my_style1.setFont(my_font);
			
			
			
			if(rpttype.equalsIgnoreCase("Earning"))
			{
				if(employeetype.equalsIgnoreCase("active"))
				{
					query=("SELECT   EMPCODE, (select  top 1  FNAME +' '+ MNAME +' '+LNAME from empmast em where em.EMPNO=sss.empno) as NAME, "+
                            " EMPNO,Sum(CASE WHEN trncd in(101,102,103,104,105,107,108,129,134,138,142,135,344,140,118) THEN Total_amt "+ 
                            " ELSE 0 END) AS TOTAL_EARNING,Sum(CASE WHEN trncd in (800) THEN Total_amt ELSE 0 END) AS DED_80C, "+
                            " ((((Sum(CASE WHEN trncd in (549,550,552,553) THEN Total_amt ELSE 0 END)+ "+
                            " Sum(CASE WHEN trncd in (551,554,555,556,500,557) THEN Total_amt ELSE 0 END))+ Sum(CASE "+ 
                            " WHEN trncd in (202) THEN CASE WHEN Total_amt<>2400 THEN  Total_amt ELSE Total_amt+100 END "+
                            " ELSE 0 END)))+Sum(CASE "+ 
                            " WHEN trncd in (570) THEN Total_amt ELSE 0 END)) AS OTHER_DED, "
                            + "((Sum(CASE WHEN trncd in (888) THEN Total_amt ELSE 0 END))+"
                            + "(Sum(CASE WHEN trncd in (570) THEN Total_amt ELSE 0 END))+(Sum(CASE WHEN trncd in (500) THEN Total_amt ELSE 0 END))+(Sum(CASE WHEN trncd in (202) THEN CASE WHEN Total_amt<>2400 THEN  Total_amt ELSE Total_amt+100 END ELSE 0 END))) AS TOTAL_DED,"
                            + " ( (Sum(CASE WHEN trncd IN ( 101, 102, 103, 104,105, 107, 108, 129, " + 
							   " 134, 138, 142, 135, 344,140,118 ) THEN total_amt ELSE 0 END)) - " + 
							   " ( ( Sum(CASE WHEN trncd IN (888) THEN total_amt ELSE 0 END) )+ " + 
							   " ((Sum(CASE WHEN trncd IN ( 202 ) THEN " + 
							   " CASE WHEN total_amt <> 2400 THEN total_amt ELSE total_amt + 100 END ELSE 0 END)))+ " + 
							   " (Sum(CASE WHEN trncd IN ( 500 ) THEN total_amt ELSE 0 END))+(Sum(CASE WHEN trncd IN ( 570 ) THEN total_amt ELSE 0 END)))  ) AS TAXABLE_INCOME, "+
                            " Sum(CASE WHEN sss.trncd = 228 THEN release_amt ELSE 0 END) AS ITAX, "+ 
                            " Sum(CASE WHEN sss.trncd = 228 THEN proj_amt ELSE 0 END) AS PAID_ITAX, "+ 
                            " Sum(CASE WHEN sss.trncd = 228 THEN Total_amt ELSE 0 END) AS ITAX_REMAIN_OR_REFUND "+
                            " FROM   (SELECT p.trncd   AS trncd,Sum(convert(numeric,p.release_amt)) AS release_amt,"+ 
                            " Sum(convert(numeric,p.proj_amt)) AS proj_amt,Sum(convert(numeric,p.Total_amt)) AS Total_amt, "+
                            " e.EMPNO AS empno,e.empcode as empcode FROM   YEARLYEARNING p, "+ 
                            " emptran et,empmast e WHERE  e.empno = et.empno AND et.empno = p.empno "+ 
                            " AND et.srno = (SELECT Max(srno) FROM   emptran WHERE  empno = E.empno) "+ 
                            " AND ( ( E.status = 'A' AND E.doj <= '"+EOY+"' ) ) "+ 
                            " AND p.trncd NOT IN( 301, 302 ) AND p.TRNDT = '"+EOY+"' "+ 
                            " GROUP  BY p.trncd,et.EMPNO,e.EMPNO,e.empcode) AS sss "+ 
                            " GROUP  BY empno,empcode order by empcode ");
				}
				else if(employeetype.equalsIgnoreCase("nonactive"))
				{
					query=("SELECT   EMPCODE, (select  top 1  FNAME +' '+ MNAME +' '+LNAME from empmast em where em.EMPNO=sss.empno) as NAME, "+
                            " EMPNO,Sum(CASE WHEN trncd in(101,102,103,104,105,107,108,129,134,138,142,135,344,140,118) THEN Total_amt "+ 
                            " ELSE 0 END) AS TOTAL_EARNING,Sum(CASE WHEN trncd in (800) THEN Total_amt ELSE 0 END) AS DED_80C, "+
                            " ((((Sum(CASE WHEN trncd in (549,550,552,553) THEN Total_amt ELSE 0 END)+ "+
                            " Sum(CASE WHEN trncd in (551,554,555,556,500,557) THEN Total_amt ELSE 0 END))+ Sum(CASE "+ 
                            " WHEN trncd in (202) THEN CASE WHEN Total_amt<>2400 THEN  Total_amt ELSE Total_amt+100 END "+
                            //" ELSE 0 END))+ Sum(CASE WHEN trncd in (108) THEN Total_amt ELSE 0 END))+Sum(CASE "+
                            " ELSE 0 END)))+Sum(CASE "+
                            " WHEN trncd in (570) THEN Total_amt ELSE 0 END)) AS OTHER_DED, "
                            + "((Sum(CASE WHEN trncd in (888) THEN Total_amt ELSE 0 END))+"
                            + "(Sum(CASE WHEN trncd in (570) THEN Total_amt ELSE 0 END))+(Sum(CASE WHEN trncd in (500) THEN Total_amt ELSE 0 END))+(Sum(CASE WHEN trncd in (202) THEN CASE WHEN Total_amt<>2400 THEN  Total_amt ELSE Total_amt+100 END ELSE 0 END))) AS TOTAL_DED,"
                            + " ( (Sum(CASE WHEN trncd IN ( 101, 102, 103, 104,105, 107, 108, 129, " + 
							   " 134, 138, 142,135, 344,140,118 ) THEN total_amt ELSE 0 END)) - " + 
							   " ( ( Sum(CASE WHEN trncd IN (888) THEN total_amt ELSE 0 END) )+ " + 
							   " ((Sum(CASE WHEN trncd IN ( 202 ) THEN " + 
							   " CASE WHEN total_amt <> 2400 THEN total_amt ELSE total_amt + 100 END ELSE 0 END)))+ " + 
							   " (Sum(CASE WHEN trncd IN ( 500 ) THEN total_amt ELSE 0 END))+(Sum(CASE WHEN trncd IN ( 570 ) THEN total_amt ELSE 0 END)))  ) AS TAXABLE_INCOME, "+
                            " Sum(CASE WHEN sss.trncd = 228 THEN release_amt ELSE 0 END) AS ITAX, "+ 
                            " Sum(CASE WHEN sss.trncd = 228 THEN proj_amt ELSE 0 END) AS PAID_ITAX, "+ 
                            " Sum(CASE WHEN sss.trncd = 228 THEN Total_amt ELSE 0 END) AS ITAX_REMAIN_OR_REFUND "+
                            " FROM   (SELECT p.trncd   AS trncd,Sum(convert(numeric,p.release_amt)) AS release_amt,"+ 
                            " Sum(convert(numeric,p.proj_amt)) AS proj_amt,Sum(convert(numeric,p.Total_amt)) AS Total_amt, "+
                            " e.EMPNO AS empno,e.empcode as empcode FROM   YEARLYEARNING p, "+ 
                            " emptran et,empmast e WHERE  e.empno = et.empno AND et.empno = p.empno "+ 
                            " AND et.srno = (SELECT Max(srno) FROM   emptran WHERE  empno = E.empno) "+ 
                            " AND ( ( E.status = 'N' OR E.dol >= '"+BOY+"' ) ) "+ 
                            " AND p.trncd NOT IN( 301, 302 ) AND p.TRNDT ='"+EOY+"' "+ 
                            " GROUP  BY p.trncd,et.EMPNO,e.EMPNO,e.empcode) AS sss "+ 
                            " GROUP  BY empno,empcode order by empcode ");
				}
				else
				{
					query=("SELECT   EMPCODE, (select  top 1  FNAME +' '+ MNAME +' '+LNAME from empmast em where em.EMPNO=sss.empno) as NAME, "+
                               " EMPNO,Sum(CASE WHEN trncd in(101,102,103,104,105,107,108,129,134,138,142,135,344,140,118) THEN Total_amt "+ 
                               " ELSE 0 END) AS TOTAL_EARNING,Sum(CASE WHEN trncd in (800) THEN Total_amt ELSE 0 END) AS DED_80C, "+
                               " ((((Sum(CASE WHEN trncd in (549,550,552,553) THEN Total_amt ELSE 0 END)+ "+
                               " Sum(CASE WHEN trncd in (551,554,555,556,500,557) THEN Total_amt ELSE 0 END))+ Sum(CASE "+ 
                               " WHEN trncd in (202) THEN CASE WHEN Total_amt<>2400 THEN  Total_amt ELSE Total_amt+100 END "+
                               //" ELSE 0 END))+ Sum(CASE WHEN trncd in (108) THEN Total_amt ELSE 0 END))+Sum(CASE "+ // here take STD DED
                               " ELSE 0 END)))+Sum(CASE "+
                               " WHEN trncd in (570) THEN Total_amt ELSE 0 END)) AS OTHER_DED, "
                               + "((Sum(CASE WHEN trncd in (888) THEN Total_amt ELSE 0 END))+"
                               + "	(Sum(CASE WHEN trncd in (570) THEN Total_amt ELSE 0 END))+(Sum(CASE WHEN trncd in (500) THEN Total_amt ELSE 0 END))+(Sum(CASE WHEN trncd in (202) THEN CASE WHEN Total_amt<>2400 THEN  Total_amt ELSE Total_amt+100 END ELSE 0 END))) AS TOTAL_DED,"
                               + " ( (Sum(CASE WHEN trncd IN ( 101, 102, 103, 104,105, 107, 108, 129, " + 
   							   " 134, 138, 142,135, 344,140,118 ) THEN total_amt ELSE 0 END)) - " + 
   							   " ( ( Sum(CASE WHEN trncd IN (888) THEN total_amt ELSE 0 END) )+ " + 
   							   " ((Sum(CASE WHEN trncd IN ( 202 ) THEN " + 
   							   " CASE WHEN total_amt <> 2400 THEN total_amt ELSE total_amt + 100 END ELSE 0 END)))+ " + 
   							   " (Sum(CASE WHEN trncd IN ( 500 ) THEN total_amt ELSE 0 END))+(Sum(CASE WHEN trncd IN ( 570 ) THEN total_amt ELSE 0 END)))  ) AS TAXABLE_INCOME, "+
                               " Sum(CASE WHEN sss.trncd = 228 THEN release_amt ELSE 0 END) AS ITAX, "+ 
                               " Sum(CASE WHEN sss.trncd = 228 THEN proj_amt ELSE 0 END) AS PAID_ITAX, "+ 
                               " Sum(CASE WHEN sss.trncd = 228 THEN Total_amt ELSE 0 END) AS ITAX_REMAIN_OR_REFUND "+
                               " FROM   (SELECT p.trncd   AS trncd,Sum(convert(numeric,p.release_amt)) AS release_amt,"+ 
                               " Sum(convert(numeric,p.proj_amt)) AS proj_amt,Sum(convert(numeric,p.Total_amt)) AS Total_amt, "+
                               " e.EMPNO AS empno,e.empcode as empcode FROM   YEARLYEARNING p, "+ 
                               " emptran et,empmast e WHERE  e.empno = et.empno AND et.empno = p.empno "+ 
                               " AND et.srno = (SELECT Max(srno) FROM   emptran WHERE  empno = E.empno) "+ 
                               " AND ( ( E.status = 'A' AND E.doj <= '"+EOY+"' ) "+ 
                               " OR ( E.status = 'N' AND E.dol >= '"+BOY+"' ) ) "+ 
                               " AND p.trncd NOT IN( 301, 302 ) AND p.TRNDT ='"+EOY+"' "+ 
                               " GROUP  BY p.trncd,et.EMPNO,e.EMPNO,e.empcode) AS sss "+ 
                               " GROUP  BY empno,empcode order by empcode ");
				}
					
				
			}
			else
			{
				if(employeetype.equalsIgnoreCase("active"))
				{
					query = ("SELECT   EMPCODE, (select  top 1  FNAME +' '+ MNAME +' '+LNAME from empmast em where em.EMPNO=sss.empno) as NAME, "
							+ "               EMPNO, "
							+ "               Sum(CASE WHEN trncd in(101,102,103,104,105,107,108,129,134,138,142,135,344,140,118) THEN Total_amt ELSE 0 END) AS TOTAL_EARNING, "
							+ "               Sum(CASE WHEN trncd in (202) THEN CASE WHEN Total_amt<>2400 THEN  Total_amt ELSE Total_amt+100 END ELSE 0 END) AS PT, "
							//+ "               Sum(CASE WHEN trncd in (108) THEN Total_amt ELSE 0 END) AS DED_COV_ALLOW, "
							+ "               Sum(CASE WHEN trncd in (500) THEN Total_amt ELSE 0 END) AS STD_DEDUCTION, "
							+ "               Sum(CASE WHEN trncd in (522) THEN Total_amt ELSE 0 END) AS CHILDREN_EDU_ALLOW, "
							+ "               Sum(CASE WHEN trncd in (523) THEN Total_amt ELSE 0 END) AS CHILDREN_HOSTEL_ALLOW, "
							+ "               Sum(CASE WHEN trncd in (524) THEN Total_amt ELSE 0 END) AS MEDICAL_BILLS_REIMBURSEMENT, "
							+ "               Sum(CASE WHEN trncd in (565) THEN Total_amt ELSE 0 END) AS RENT_PAID, "
							+ "               Sum(CASE WHEN trncd in (525) THEN Total_amt ELSE 0 END) AS LTA, "
							+ "               Sum(CASE WHEN trncd in (588) THEN Total_amt ELSE 0 END) AS SODEXO_COUPONS_REIMBURSEMENT, "
							+ "               Sum(CASE WHEN trncd in (570) THEN Total_amt ELSE 0 END) AS INT_ON_HOUSE_LOAN, "
							+ "               Sum(CASE WHEN trncd in (201) THEN Total_amt ELSE 0 END) AS DED_80C_PF, "
							+ "               Sum(CASE WHEN trncd in (205) THEN Total_amt ELSE 0 END) AS DED_80C_LIC, "
							+ "               Sum(CASE WHEN trncd in (207) THEN Total_amt ELSE 0 END) AS DED_80C_GI, "
							+ "               Sum(CASE WHEN trncd in (501) THEN Total_amt ELSE 0 END) AS DED_80C_PPF, "
							+ "               Sum(CASE WHEN trncd in (508) THEN Total_amt ELSE 0 END) AS DED_80C_NSS, "
							+ "               Sum(CASE WHEN trncd in (539) THEN Total_amt ELSE 0 END) AS DED_80C_HOME_LOAN_PRINCIPAL, "
							+ "               Sum(CASE WHEN trncd in (540) THEN Total_amt ELSE 0 END) AS DED_80C_HOME_TUTION_FEE_2_CHILD, "
							+ "               Sum(CASE WHEN trncd in (542) THEN Total_amt ELSE 0 END) AS DED_80C_FD, "
							+ "               Sum(CASE WHEN trncd in (543) THEN Total_amt ELSE 0 END) AS DED_80C_TAX_SAVING_INFRA, "
							+ "               Sum(CASE WHEN trncd in (544) THEN Total_amt ELSE 0 END) AS DED_80C_OTH_INVEST, "
							+ "               Sum(CASE WHEN trncd in (546) THEN Total_amt ELSE 0 END) AS DED_80C_NEW_PENSION_SECHEME_EMPLOYEE, "
							+ "               Sum(CASE WHEN trncd in (548) THEN Total_amt ELSE 0 END) AS DED_80C_RAJIV_GANDHI_EQUITY_SCHEME, "
							+ "               Sum(CASE WHEN trncd in (537) THEN Total_amt ELSE 0 END) AS DED_80C_LIFE_INSURANCE_PREMIUMS, "
							+ "               Sum(CASE WHEN trncd in (538) THEN Total_amt ELSE 0 END) AS DED_80C_NSC, "
							+ "               Sum(CASE WHEN trncd in (541) THEN Total_amt ELSE 0 END) AS DED_80C_ELSS, "
							+ "               Sum(CASE WHEN trncd in (545) THEN Total_amt ELSE 0 END) AS DED_80C_PENSION_PLAN, "
							+ "               Sum(CASE WHEN trncd in (547) THEN Total_amt ELSE 0 END) AS DED_80C_NEW_PENSION_SCHEME_EMPLOYER, "
							+ "               Sum(CASE WHEN trncd in (513) THEN Total_amt ELSE 0 END) AS DED_80C_SUKANYA_SAMRIDDHI_ACCOUNT, "
							+ "               Sum(CASE WHEN trncd in (512) THEN Total_amt ELSE 0 END) AS DED_80C_DEPOSIT, "
							+ "               Sum(CASE WHEN trncd in (511) THEN Total_amt ELSE 0 END) AS DED_80C_ULIP, "
							+ "               Sum(CASE WHEN trncd in (800) THEN Total_amt ELSE 0 END) AS DED_80C, "
							+ "               Sum(CASE WHEN trncd in (510) THEN Total_amt ELSE 0 END) AS INFRA_INVEST, "
							+ "               Sum(CASE WHEN trncd in (549) THEN Total_amt ELSE 0 END) AS MED_INS_SELF, "
							+ "               Sum(CASE WHEN trncd in (550) THEN Total_amt ELSE 0 END) AS MED_INS_PARENTS, "
							+ "               Sum(CASE WHEN trncd in (552) THEN Total_amt ELSE 0 END) AS SECTION_80DD, "
							+ "               Sum(CASE WHEN trncd in (553) THEN Total_amt ELSE 0 END) AS SECTION_80DDB, "
							+ "               Sum(CASE WHEN trncd in (549,550,552,553) THEN Total_amt ELSE 0 END) AS TOTAL_MEDICLAIM, "
							+ "               Sum(CASE WHEN trncd in (554) THEN Total_amt ELSE 0 END) AS SECTION_80E, "
							+ "               Sum(CASE WHEN trncd in (551) THEN Total_amt ELSE 0 END) AS DED_80G_DONATION_APPROVED_FUND, "
							+ "               Sum(CASE WHEN trncd in (555) THEN Total_amt ELSE 0 END) AS SECTION_80GG, "
							+ "               Sum(CASE WHEN trncd in (556) THEN Total_amt ELSE 0 END) AS SECTION_80GGA, "
							+ "               Sum(CASE WHEN trncd in (557) THEN Total_amt ELSE 0 END) AS SECTION_80GGC, "
							+ "               Sum(CASE WHEN trncd in (551,555,556,557) THEN Total_amt ELSE 0 END) AS DED_TOTAL_80G, "
							+ "               Sum(CASE WHEN trncd in (558) THEN Total_amt ELSE 0 END) AS SECTION_80U, "
							+ " 				((Sum(CASE WHEN trncd in (888) THEN Total_amt ELSE 0 END))+"
                            + "				(Sum(CASE WHEN trncd in (570) THEN Total_amt ELSE 0 END))+(Sum(CASE WHEN trncd in (500) THEN Total_amt ELSE 0 END))+(Sum(CASE WHEN trncd in (202) THEN CASE WHEN Total_amt<>2400 THEN  Total_amt ELSE Total_amt+100 END ELSE 0 END))) AS TOTAL_DED,"
							+ "               ( (Sum(CASE WHEN trncd IN ( 101, 102, 103, 104,105, 107,108, 129, " +//add 108 later whenever required ..i.e conveience  
							"                  134, 138, 142,135, 344,140,118 ) THEN total_amt ELSE 0 END)) - " + 
							"                   ( ( Sum(CASE WHEN trncd IN (888) THEN total_amt ELSE 0 END) )+ " + 
							"                   ((Sum(CASE WHEN trncd IN ( 202 ) THEN " + 
							"                   CASE WHEN total_amt <> 2400 THEN total_amt ELSE total_amt + 100 END ELSE 0 END)))+ " + 
							"                  (Sum(CASE WHEN trncd IN ( 500 ) THEN total_amt ELSE 0 END))+(Sum(CASE WHEN trncd IN ( 570 ) THEN total_amt ELSE 0 END)))  ) AS TAXABLE_INCOME, "
							+ "               Sum(CASE WHEN sss.trncd = 228 THEN release_amt ELSE 0 END) AS ITAX, "
							+ "               Sum(CASE WHEN sss.trncd = 228 THEN proj_amt ELSE 0 END) AS PAID_ITAX, "
							+ "               Sum(CASE WHEN sss.trncd = 228 THEN Total_amt ELSE 0 END) AS ITAX_REMAIN_OR_REFUND "
							+ "               FROM   (SELECT p.trncd   AS trncd,Sum(convert(numeric,p.release_amt)) AS release_amt, "
							+ "					   Sum(convert(numeric,p.proj_amt)) AS proj_amt,Sum(convert(numeric,p.Total_amt)) AS Total_amt, "
							+ "                       e.EMPNO    AS empno,e.empcode as empcode FROM   YEARLYEARNING p,emptran et,empmast e "
							+ "					   WHERE  e.empno = et.empno AND et.empno = p.empno AND et.srno = (SELECT Max(srno) FROM   emptran WHERE  empno = E.empno) "
							+ "                       AND ( ( E.status = 'A' AND E.doj <= '"+EOY+"' ) ) "
							+ "                       AND p.trncd NOT IN( 301, 302 ) AND p.TRNDT ='"+EOY+"' "
							+ "                GROUP  BY p.trncd, et.EMPNO,e.EMPNO,e.empcode) AS sss "
							+ "			   GROUP  BY empno,empcode "
							+ "			   ORDER BY empcode" );
				}
				else if(employeetype.equalsIgnoreCase("nonactive"))
				{
					query = ("SELECT   EMPCODE, (select  top 1  FNAME +' '+ MNAME +' '+LNAME from empmast em where em.EMPNO=sss.empno) as NAME, "
							+ "               EMPNO, "
							+ "               Sum(CASE WHEN trncd in(101,102,103,104,105,107,108,129,134,138,142,135,344,140,118) THEN Total_amt ELSE 0 END) AS TOTAL_EARNING, "
							+ "               Sum(CASE WHEN trncd in (202) THEN CASE WHEN Total_amt<>2400 THEN  Total_amt ELSE Total_amt+100 END ELSE 0 END) AS PT, "
							//+ "               Sum(CASE WHEN trncd in (108) THEN Total_amt ELSE 0 END) AS DED_COV_ALLOW, "
							+ "               Sum(CASE WHEN trncd in (500) THEN Total_amt ELSE 0 END) AS STD_DEDUCTION, "
							+ "               Sum(CASE WHEN trncd in (522) THEN Total_amt ELSE 0 END) AS CHILDREN_EDU_ALLOW, "
							+ "               Sum(CASE WHEN trncd in (523) THEN Total_amt ELSE 0 END) AS CHILDREN_HOSTEL_ALLOW, "
							+ "               Sum(CASE WHEN trncd in (524) THEN Total_amt ELSE 0 END) AS MEDICAL_BILLS_REIMBURSEMENT, "
							+ "               Sum(CASE WHEN trncd in (565) THEN Total_amt ELSE 0 END) AS RENT_PAID, "
							+ "               Sum(CASE WHEN trncd in (525) THEN Total_amt ELSE 0 END) AS LTA, "
							+ "               Sum(CASE WHEN trncd in (588) THEN Total_amt ELSE 0 END) AS SODEXO_COUPONS_REIMBURSEMENT, "
							+ "               Sum(CASE WHEN trncd in (570) THEN Total_amt ELSE 0 END) AS INT_ON_HOUSE_LOAN, "
							+ "               Sum(CASE WHEN trncd in (201) THEN Total_amt ELSE 0 END) AS DED_80C_PF, "
							+ "               Sum(CASE WHEN trncd in (205) THEN Total_amt ELSE 0 END) AS DED_80C_LIC, "
							+ "               Sum(CASE WHEN trncd in (207) THEN Total_amt ELSE 0 END) AS DED_80C_GI, "
							+ "               Sum(CASE WHEN trncd in (501) THEN Total_amt ELSE 0 END) AS DED_80C_PPF, "
							+ "               Sum(CASE WHEN trncd in (508) THEN Total_amt ELSE 0 END) AS DED_80C_NSS, "
							+ "               Sum(CASE WHEN trncd in (539) THEN Total_amt ELSE 0 END) AS DED_80C_HOME_LOAN_PRINCIPAL, "
							+ "               Sum(CASE WHEN trncd in (540) THEN Total_amt ELSE 0 END) AS DED_80C_HOME_TUTION_FEE_2_CHILD, "
							+ "               Sum(CASE WHEN trncd in (542) THEN Total_amt ELSE 0 END) AS DED_80C_FD, "
							+ "               Sum(CASE WHEN trncd in (543) THEN Total_amt ELSE 0 END) AS DED_80C_TAX_SAVING_INFRA, "
							+ "               Sum(CASE WHEN trncd in (544) THEN Total_amt ELSE 0 END) AS DED_80C_OTH_INVEST, "
							+ "               Sum(CASE WHEN trncd in (546) THEN Total_amt ELSE 0 END) AS DED_80C_NEW_PENSION_SECHEME_EMPLOYEE, "
							+ "               Sum(CASE WHEN trncd in (548) THEN Total_amt ELSE 0 END) AS DED_80C_RAJIV_GANDHI_EQUITY_SCHEME, "
							+ "               Sum(CASE WHEN trncd in (537) THEN Total_amt ELSE 0 END) AS DED_80C_LIFE_INSURANCE_PREMIUMS, "
							+ "               Sum(CASE WHEN trncd in (538) THEN Total_amt ELSE 0 END) AS DED_80C_NSC, "
							+ "               Sum(CASE WHEN trncd in (541) THEN Total_amt ELSE 0 END) AS DED_80C_ELSS, "
							+ "               Sum(CASE WHEN trncd in (545) THEN Total_amt ELSE 0 END) AS DED_80C_PENSION_PLAN, "
							+ "               Sum(CASE WHEN trncd in (547) THEN Total_amt ELSE 0 END) AS DED_80C_NEW_PENSION_SCHEME_EMPLOYER, "
							+ "               Sum(CASE WHEN trncd in (513) THEN Total_amt ELSE 0 END) AS DED_80C_SUKANYA_SAMRIDDHI_ACCOUNT, "
							+ "               Sum(CASE WHEN trncd in (512) THEN Total_amt ELSE 0 END) AS DED_80C_DEPOSIT, "
							+ "               Sum(CASE WHEN trncd in (511) THEN Total_amt ELSE 0 END) AS DED_80C_ULIP, "
							+ "               Sum(CASE WHEN trncd in (800) THEN Total_amt ELSE 0 END) AS DED_80C, "
							+ "               Sum(CASE WHEN trncd in (510) THEN Total_amt ELSE 0 END) AS INFRA_INVEST, "
							+ "               Sum(CASE WHEN trncd in (549) THEN Total_amt ELSE 0 END) AS MED_INS_SELF, "
							+ "               Sum(CASE WHEN trncd in (550) THEN Total_amt ELSE 0 END) AS MED_INS_PARENTS, "
							+ "               Sum(CASE WHEN trncd in (552) THEN Total_amt ELSE 0 END) AS SECTION_80DD, "
							+ "               Sum(CASE WHEN trncd in (553) THEN Total_amt ELSE 0 END) AS SECTION_80DDB, "
							+ "               Sum(CASE WHEN trncd in (549,550,552,553) THEN Total_amt ELSE 0 END) AS TOTAL_MEDICLAIM, "
							+ "               Sum(CASE WHEN trncd in (554) THEN Total_amt ELSE 0 END) AS SECTION_80E, "
							+ "               Sum(CASE WHEN trncd in (551) THEN Total_amt ELSE 0 END) AS DED_80G_DONATION_APPROVED_FUND, "
							+ "               Sum(CASE WHEN trncd in (555) THEN Total_amt ELSE 0 END) AS SECTION_80GG, "
							+ "               Sum(CASE WHEN trncd in (556) THEN Total_amt ELSE 0 END) AS SECTION_80GGA, "
							+ "               Sum(CASE WHEN trncd in (557) THEN Total_amt ELSE 0 END) AS SECTION_80GGC, "
							+ "               Sum(CASE WHEN trncd in (551,555,556,557) THEN Total_amt ELSE 0 END) AS DED_TOTAL_80G, "
							+ "               Sum(CASE WHEN trncd in (558) THEN Total_amt ELSE 0 END) AS SECTION_80U, "
							+ " 				((Sum(CASE WHEN trncd in (888) THEN Total_amt ELSE 0 END))+"
                            + "				(Sum(CASE WHEN trncd in (570) THEN Total_amt ELSE 0 END))+(Sum(CASE WHEN trncd in (500) THEN Total_amt ELSE 0 END))+(Sum(CASE WHEN trncd in (202) THEN CASE WHEN Total_amt<>2400 THEN  Total_amt ELSE Total_amt+100 END ELSE 0 END))) AS TOTAL_DED,"
							+ "               ( (Sum(CASE WHEN trncd IN ( 101, 102, 103, 104,105, 107,108, 129, " + //add 108 whenever required 
							"                  134, 138, 142,135, 344,140,118 ) THEN total_amt ELSE 0 END)) - " + 
							"                   ( ( Sum(CASE WHEN trncd IN (888) THEN total_amt ELSE 0 END) )+ " + 
							"                   ((Sum(CASE WHEN trncd IN ( 202 ) THEN " + 
							"                   CASE WHEN total_amt <> 2400 THEN total_amt ELSE total_amt + 100 END ELSE 0 END)))+ " + 
							"                  (Sum(CASE WHEN trncd IN ( 500 ) THEN total_amt ELSE 0 END))+(Sum(CASE WHEN trncd IN ( 570 ) THEN total_amt ELSE 0 END)))  ) AS TAXABLE_INCOME, "
							+ "               Sum(CASE WHEN sss.trncd = 228 THEN release_amt ELSE 0 END) AS ITAX, "
							+ "               Sum(CASE WHEN sss.trncd = 228 THEN proj_amt ELSE 0 END) AS PAID_ITAX, "
							+ "               Sum(CASE WHEN sss.trncd = 228 THEN Total_amt ELSE 0 END) AS ITAX_REMAIN_OR_REFUND "
							+ "               FROM   (SELECT p.trncd   AS trncd,Sum(convert(numeric,p.release_amt)) AS release_amt, "
							+ "					   Sum(convert(numeric,p.proj_amt)) AS proj_amt,Sum(convert(numeric,p.Total_amt)) AS Total_amt, "
							+ "                       e.EMPNO    AS empno,e.empcode as empcode FROM   YEARLYEARNING p,emptran et,empmast e "
							+ "					   WHERE  e.empno = et.empno AND et.empno = p.empno AND et.srno = (SELECT Max(srno) FROM   emptran WHERE  empno = E.empno) "
							+ "                       AND (  ( E.status = 'N' AND E.dol >= '"+BOY+"' ) ) "
							+ "                       AND p.trncd NOT IN( 301, 302 ) AND p.TRNDT ='"+EOY+"' "
							+ "                GROUP  BY p.trncd, et.EMPNO,e.EMPNO,e.empcode) AS sss "
							+ "			   GROUP  BY empno,empcode "
							+ "			   ORDER BY empcode" );
				}
				else
				{
					query = ("SELECT   EMPCODE, (select  top 1  FNAME +' '+ MNAME +' '+LNAME from empmast em where em.EMPNO=sss.empno) as NAME, "
							+ "               EMPNO, "
							+ "               Sum(CASE WHEN trncd in(101,102,103,104,105,107,108,129,134,138,142,135,344,140,118) THEN Total_amt ELSE 0 END) AS TOTAL_EARNING, "
							+ "               Sum(CASE WHEN trncd in (202) THEN CASE WHEN Total_amt<>2400 THEN  Total_amt ELSE Total_amt+100 END ELSE 0 END) AS PT, "
							//+ "               Sum(CASE WHEN trncd in (108) THEN Total_amt ELSE 0 END) AS DED_COV_ALLOW, "
							+ "               Sum(CASE WHEN trncd in (500) THEN Total_amt ELSE 0 END) AS STD_DEDUCTION, "
							+ "               Sum(CASE WHEN trncd in (522) THEN Total_amt ELSE 0 END) AS CHILDREN_EDU_ALLOW, "
							+ "               Sum(CASE WHEN trncd in (523) THEN Total_amt ELSE 0 END) AS CHILDREN_HOSTEL_ALLOW, "
							+ "               Sum(CASE WHEN trncd in (524) THEN Total_amt ELSE 0 END) AS MEDICAL_BILLS_REIMBURSEMENT, "
							+ "               Sum(CASE WHEN trncd in (565) THEN Total_amt ELSE 0 END) AS RENT_PAID, "
							+ "               Sum(CASE WHEN trncd in (525) THEN Total_amt ELSE 0 END) AS LTA, "
							+ "               Sum(CASE WHEN trncd in (588) THEN Total_amt ELSE 0 END) AS SODEXO_COUPONS_REIMBURSEMENT, "
							+ "               Sum(CASE WHEN trncd in (570) THEN Total_amt ELSE 0 END) AS INT_ON_HOUSE_LOAN, "
							+ "               Sum(CASE WHEN trncd in (201) THEN Total_amt ELSE 0 END) AS DED_80C_PF, "
							+ "               Sum(CASE WHEN trncd in (205) THEN Total_amt ELSE 0 END) AS DED_80C_LIC, "
							+ "               Sum(CASE WHEN trncd in (207) THEN Total_amt ELSE 0 END) AS DED_80C_GI, "
							+ "               Sum(CASE WHEN trncd in (501) THEN Total_amt ELSE 0 END) AS DED_80C_PPF, "
							+ "               Sum(CASE WHEN trncd in (508) THEN Total_amt ELSE 0 END) AS DED_80C_NSS, "
							+ "               Sum(CASE WHEN trncd in (539) THEN Total_amt ELSE 0 END) AS DED_80C_HOME_LOAN_PRINCIPAL, "
							+ "               Sum(CASE WHEN trncd in (540) THEN Total_amt ELSE 0 END) AS DED_80C_HOME_TUTION_FEE_2_CHILD, "
							+ "               Sum(CASE WHEN trncd in (542) THEN Total_amt ELSE 0 END) AS DED_80C_FD, "
							+ "               Sum(CASE WHEN trncd in (543) THEN Total_amt ELSE 0 END) AS DED_80C_TAX_SAVING_INFRA, "
							+ "               Sum(CASE WHEN trncd in (544) THEN Total_amt ELSE 0 END) AS DED_80C_OTH_INVEST, "
							+ "               Sum(CASE WHEN trncd in (546) THEN Total_amt ELSE 0 END) AS DED_80C_NEW_PENSION_SECHEME_EMPLOYEE, "
							+ "               Sum(CASE WHEN trncd in (548) THEN Total_amt ELSE 0 END) AS DED_80C_RAJIV_GANDHI_EQUITY_SCHEME, "
							+ "               Sum(CASE WHEN trncd in (537) THEN Total_amt ELSE 0 END) AS DED_80C_LIFE_INSURANCE_PREMIUMS, "
							+ "               Sum(CASE WHEN trncd in (538) THEN Total_amt ELSE 0 END) AS DED_80C_NSC, "
							+ "               Sum(CASE WHEN trncd in (541) THEN Total_amt ELSE 0 END) AS DED_80C_ELSS, "
							+ "               Sum(CASE WHEN trncd in (545) THEN Total_amt ELSE 0 END) AS DED_80C_PENSION_PLAN, "
							+ "               Sum(CASE WHEN trncd in (547) THEN Total_amt ELSE 0 END) AS DED_80C_NEW_PENSION_SCHEME_EMPLOYER, "
							+ "               Sum(CASE WHEN trncd in (513) THEN Total_amt ELSE 0 END) AS DED_80C_SUKANYA_SAMRIDDHI_ACCOUNT, "
							+ "               Sum(CASE WHEN trncd in (512) THEN Total_amt ELSE 0 END) AS DED_80C_DEPOSIT, "
							+ "               Sum(CASE WHEN trncd in (511) THEN Total_amt ELSE 0 END) AS DED_80C_ULIP, "
							+ "               Sum(CASE WHEN trncd in (800) THEN Total_amt ELSE 0 END) AS DED_80C, "
							+ "               Sum(CASE WHEN trncd in (510) THEN Total_amt ELSE 0 END) AS INFRA_INVEST, "
							+ "               Sum(CASE WHEN trncd in (549) THEN Total_amt ELSE 0 END) AS MED_INS_SELF, "
							+ "               Sum(CASE WHEN trncd in (550) THEN Total_amt ELSE 0 END) AS MED_INS_PARENTS, "
							+ "               Sum(CASE WHEN trncd in (552) THEN Total_amt ELSE 0 END) AS SECTION_80DD, "
							+ "               Sum(CASE WHEN trncd in (553) THEN Total_amt ELSE 0 END) AS SECTION_80DDB, "
							+ "               Sum(CASE WHEN trncd in (549,550,552,553) THEN Total_amt ELSE 0 END) AS TOTAL_MEDICLAIM, "
							+ "               Sum(CASE WHEN trncd in (554) THEN Total_amt ELSE 0 END) AS SECTION_80E, "
							+ "               Sum(CASE WHEN trncd in (551) THEN Total_amt ELSE 0 END) AS DED_80G_DONATION_APPROVED_FUND, "
							+ "               Sum(CASE WHEN trncd in (555) THEN Total_amt ELSE 0 END) AS SECTION_80GG, "
							+ "               Sum(CASE WHEN trncd in (556) THEN Total_amt ELSE 0 END) AS SECTION_80GGA, "
							+ "               Sum(CASE WHEN trncd in (557) THEN Total_amt ELSE 0 END) AS SECTION_80GGC, "
							+ "               Sum(CASE WHEN trncd in (551,555,556,557) THEN Total_amt ELSE 0 END) AS DED_TOTAL_80G, "
							+ "               Sum(CASE WHEN trncd in (558) THEN Total_amt ELSE 0 END) AS SECTION_80U, "
							+ " 				((Sum(CASE WHEN trncd in (888) THEN Total_amt ELSE 0 END))+"
                            + "				(Sum(CASE WHEN trncd in (570) THEN Total_amt ELSE 0 END))+(Sum(CASE WHEN trncd in (500) THEN Total_amt ELSE 0 END))+(Sum(CASE WHEN trncd in (202) THEN CASE WHEN Total_amt<>2400 THEN  Total_amt ELSE Total_amt+100 END ELSE 0 END))) AS TOTAL_DED,"
							+ "               ( (Sum(CASE WHEN trncd IN ( 101, 102, 103, 104,105, 107, 108, 129, " + 
							"                  134, 138, 142,135, 344,140,118 ) THEN total_amt ELSE 0 END)) - " + 
							"                   ( ( Sum(CASE WHEN trncd IN (888) THEN total_amt ELSE 0 END) )+ " + 
							"                   ((Sum(CASE WHEN trncd IN ( 202 ) THEN " + 
							"                   CASE WHEN total_amt <> 2400 THEN total_amt ELSE total_amt + 100 END ELSE 0 END)))+ " + 
							"                  (Sum(CASE WHEN trncd IN ( 500 ) THEN total_amt ELSE 0 END))+(Sum(CASE WHEN trncd IN ( 570 ) THEN total_amt ELSE 0 END)))  ) AS TAXABLE_INCOME, "
							+ "               Sum(CASE WHEN sss.trncd = 228 THEN release_amt ELSE 0 END) AS ITAX, "
							+ "               Sum(CASE WHEN sss.trncd = 228 THEN proj_amt ELSE 0 END) AS PAID_ITAX, "
							+ "               Sum(CASE WHEN sss.trncd = 228 THEN Total_amt ELSE 0 END) AS ITAX_REMAIN_OR_REFUND "
							+ "               FROM   (SELECT p.trncd   AS trncd,Sum(convert(numeric,p.release_amt)) AS release_amt, "
							+ "					   Sum(convert(numeric,p.proj_amt)) AS proj_amt,Sum(convert(numeric,p.Total_amt)) AS Total_amt, "
							+ "                       e.EMPNO    AS empno,e.empcode as empcode FROM   YEARLYEARNING p,emptran et,empmast e "
							+ "					   WHERE  e.empno = et.empno AND et.empno = p.empno AND et.srno = (SELECT Max(srno) FROM   emptran WHERE  empno = E.empno) "
							+ "                       AND ( ( E.status = 'A' AND E.doj <= '"+EOY+"' ) "
							+ "                              OR ( E.status = 'N' AND E.dol >= '"+BOY+"' ) ) "
							+ "                       AND p.trncd NOT IN( 301, 302 ) AND p.TRNDT ='"+EOY+"' "
							+ "                GROUP  BY p.trncd, et.EMPNO,e.EMPNO,e.empcode) AS sss "
							+ "			   GROUP  BY empno,empcode "
							+ "			   ORDER BY empcode" );

				}
			}
			
			ReportDAO.OpenCon("", "", "",repBean);
			con = repBean.getCn();	
			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			/*Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Statement stdt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);*/
			
			
			int i=11;
			int j=0;
			//int count=1;
			int k = 0;
			/*String querydt=""; 
			String yr ="";
			String year=ReportDAO.getServerDate();
			System.out.println("this is year..."+year.substring(7,11));
			String empquery=("select Distinct e.empno from empmast e where "+
					" e.status='A' and "+
					" (select DATEDIFF(MONTH,(select doj from empmast where empno=e.empno and status='A' ),GETDATE()))<696"+  
					" order by e.empno ");*/
			System.out.println("this is ...."+query);
			ResultSet emp=st.executeQuery(query);
			java.sql.ResultSetMetaData rsmd = emp.getMetaData();

			int columnsNumber = rsmd.getColumnCount();
			System.out.println("columnsNumber..."+columnsNumber+"....."+rsmd.getColumnName(4));
			for ( j=1;j<=columnsNumber;j++)
			{
				sheet.setColumnWidth((short)j, (short)10000);
				
				cell3=rowhead.createCell((short) j);
				cell3.setCellValue(""+rsmd.getColumnName(j));
				cell3.setCellStyle(my_style1);
			}
			
			while(emp.next())
			{
				k++;
				
				HSSFRow row = sheet.createRow((short)i++);
				
				for (j=1;j<=columnsNumber;j++)
				{
				  row.createCell((short) j).setCellValue(""+emp.getString(rsmd.getColumnName(j)));
				}
				
			}
				
			
			
			HSSFRow row1 = sheet.createRow((short) i);
			row1.createCell((short) 0).setCellValue(" ");
			  row1 = sheet.createRow((short) i+1);
		//	row.createCell((short) 0).setCellValue("Report Date And Time= " +df.format(calobj.getTime()));
			
			row1.createCell((short) 1).setCellValue("Total Employee : =  " +k );
			/*row1.createCell((short) 12).setCellValue("Total : = ");
			row1.createCell((short) 13).setCellValue(""+trans(brtot[1],"","",true,true));
			row1.createCell((short) 14).setCellValue(""+trans(brtot[2],"","",true,true));
			row1.createCell((short) 15).setCellValue(""+trans(brtot[3],"","",true,true));*/
			
			hwb.write(out1);
			out1.close();
			st.close();
		    con.close();
		    
			System.out.println("Result OK.....");
			
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		// 14-03-18..
		
		public static void YearlyEarningReport_WithMonthly_BreakUp(String frmdate,String todate,String filepath,String imgpath)
		{
			System.out.println("i am YearlyEarningReport_WithMonthly_BreakUp");
		int i = 12;
		Properties prop = new Properties();
		try
	     {
		
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream stream = classLoader.getResourceAsStream("constant.properties");
			prop.load(stream);
	     }
	     catch (Exception e) {System.out.println("Error in constant properties Manager "+e);}
	     
		try
		{
			System.out.println("Into Generalised_EmpListDAO.....");
			RepoartBean repBean  = new RepoartBean();
			
			LookupHandler lkh=new LookupHandler();
			Connection con =null;
			System.out.println( filepath);
		FileOutputStream out1 = new FileOutputStream(new File(filepath));
		HSSFWorkbook hwb=new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("EmpPFlist");
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
		System.out.println("1111.....");
		
		sheet.setColumnWidth((short)0, (short)2000);
		sheet.setColumnWidth((short)1, (short)10000);
		sheet.setColumnWidth((short)2, (short)7000);
		sheet.setColumnWidth((short)3, (short)4000);
		sheet.setColumnWidth((short)4, (short)4000);
		sheet.setColumnWidth((short)5, (short)4000);
		sheet.setColumnWidth((short)6, (short)5000);
		sheet.setColumnWidth((short)7, (short)5000);
		sheet.setColumnWidth((short)8, (short)4000);
		sheet.setColumnWidth((short)9, (short)5000);
		sheet.setColumnWidth((short)10, (short)5000);
		sheet.setColumnWidth((short)11, (short)4000);
		sheet.setColumnWidth((short)12, (short)4000);
		sheet.setColumnWidth((short)13, (short)6000);
		sheet.setColumnWidth((short)14, (short)5000);
	//	sheet.setColumnWidth((short)15, (short)5000);
		/*sheet.setColumnWidth((short)17, (short)7000);
		sheet.setColumnWidth((short)18, (short)5000);
		sheet.setColumnWidth((short)19, (short)4000);
		sheet.setColumnWidth((short)20, (short)5000);
*/



      HSSFCellStyle my_style = hwb.createCellStyle();
      HSSFCellStyle my_style1 = hwb.createCellStyle();

      HSSFFont my_font=hwb.createFont();
      my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
      my_style.setFont(my_font);
    
      
		HSSFRow rowtitle=   sheet.createRow((short)0);
		HSSFCell cell = rowtitle.createCell((short) 9);
		
	//	System.out.println("2222.....");
		
		cell.setCellValue(prop.getProperty("bankName1"));
		cell.setCellStyle(my_style);
		HSSFRow rowtitle1=   sheet.createRow((short)1);
		HSSFCell cell1 = rowtitle1.createCell((short) 7);
		cell1.setCellValue(prop.getProperty("addressForReport"));
		cell1.setCellStyle(my_style);
		HSSFRow rowtitle2=   sheet.createRow((short)2);
		HSSFCell cell2 = rowtitle2.createCell((short) 9);
		cell2.setCellValue(prop.getProperty("contactForReport"));
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle31=   sheet.createRow((short)3);
		cell2 = rowtitle31.createCell((short) 9);
		cell2.setCellValue(	prop.getProperty("mailForReport"));
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle3=   sheet.createRow((short)4);
		cell2=rowtitle3.createCell((short) 2);
		cell2.setCellValue("");
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle4=   sheet.createRow((short)5);
		rowtitle4.createCell((short) 0).setCellValue("");
		HSSFRow rowtitle5=   sheet.createRow((short)6);
		rowtitle5.createCell((short) 0).setCellValue("");
		
		
		HSSFFont blueFont = hwb.createFont();
		blueFont.setColor(HSSFColor.BLUE.index);
		
		HSSFCellStyle style = hwb.createCellStyle();
		//style.setFont(blueFont);
		style.setFillForegroundColor(HSSFColor.BLUE.index);
		
		
		HSSFRow head=   sheet.createRow((short)7);
		head.createCell((short) 0).setCellValue("");
		HSSFRow heading=   sheet.createRow((short)8);
		HSSFCell cell3 = heading.createCell((short) 0); 

		cell3.setCellValue("");
		cell3.setCellStyle(my_style1);
		HSSFRow rowhead=   sheet.createRow((short)9);
      sheet.createFreezePane( 0, 10, 0, 10 );
     
      my_style1.setAlignment((short) 2);
      my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
      my_style1.setFont(my_font);
      
		cell3=rowhead.createCell((short) 0);
		cell3.setCellValue("SR NO");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 1);
		cell3.setCellValue("EARNING");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 2);
		cell3.setCellValue("JAN ");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 3);
		cell3.setCellValue("FEB");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 4);
		cell3.setCellValue("MAR");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 5);
		cell3.setCellValue("APR");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 6);
		cell3.setCellValue("MAY");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 7);
		cell3.setCellValue("JUN");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short)8);
		cell3.setCellValue("JUL ");
		cell3.setCellStyle(my_style1);

		cell3=rowhead.createCell((short) 9);
		cell3.setCellValue("AUG ");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 10);
		cell3.setCellValue("SEP ");
		cell3.setCellStyle(my_style1);
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 11);
		cell3.setCellValue("OCT");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 12);
		cell3.setCellValue("NOV");
		cell3.setCellStyle(my_style1);
		
		cell3=rowhead.createCell((short) 13);
		cell3.setCellValue("DEC");
		cell3.setCellStyle(my_style1);
		
		cell3=rowhead.createCell((short) 14);
		cell3.setCellValue("TOTAL");
		cell3.setCellStyle(my_style1);
		
		ReportDAO.OpenCon("", "", "",repBean);
		con = repBean.getCn();	
		Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		System.out.println("3333.....");
		
		System.out.println(" select c.DISC, pvt.*  from ( "+
									"	SELECT p.trncd        AS trncd,"+
									"	Sum(p.net_amt) AS net_amt,"+
									"	substring( DATEname(MONTH, max(p.trndt)),1,3)   AS month "+
									"	FROM   paytran_stage p "+
									"	WHERE  p.trndt BETWEEN '"+frmdate+ "' AND '"+todate+ "' "+
									"	AND p.trncd NOT IN( 301, 302 ) "+
									"	GROUP  BY p.trndt,"+
									"	p.trncd "+
									"			) as p1 "+
									"			PIVOT "+
									"			("+
										"				SUM(net_Amt) "+
										"				FOR [month] IN (jan, feb, mar, apr, "+
										"						may, jun, jul, aug, sep, oct, nov, dec) "+
										"				)AS pvt ,cdmast c "+
										"				where pvt.trncd between 101 and 197  "+
										"				and pvt.trncd not in(136,137,139,140) "+
										"				and pvt.trncd = c.TRNCD");
		
		ResultSet rs=st.executeQuery(" select c.DISC, pvt.*  from ( "+
									"	SELECT p.trncd        AS trncd,"+
									"	Sum(p.net_amt) AS net_amt,"+
									"	substring( DATEname(MONTH, max(p.trndt)),1,3)   AS month "+
									"	FROM   paytran_stage p "+
									"	WHERE  p.trndt BETWEEN '"+frmdate+ "' AND '"+todate+ "' "+
									"	AND p.trncd NOT IN( 301, 302 ) "+
									"	GROUP  BY p.trndt,"+
									"	p.trncd "+
									"			) as p1 "+
									"			PIVOT "+
									"			("+
										"				SUM(net_Amt) "+
										"				FOR [month] IN (jan, feb, mar, apr, "+
										"						may, jun, jul, aug, sep, oct, nov, dec) "+
										"				)AS pvt ,cdmast c "+
										"				where pvt.trncd between 101 and 197  "+
										"				and pvt.trncd not in(136,137,139,140) "+
										"				and pvt.trncd = c.TRNCD");
		
		String TotMonthWise = "";
		int cnt =0;
		long totaltrncd=0;
		long TotIncomeJan=0,TotIncomeFeb=0,TotIncomeMar=0,TotIncomeApr=0,TotIncomeMay=0,TotIncomeJun=0,TotIncomeJul=0,
				TotIncomeAug=0,TotIncomeSept=0,TotIncomeOct=0,TotIncomeNov=0,TotIncomeDec=0,TotYearIncome=0;
		while(rs.next())
		{
			long totalamt=0;
			cnt++;
			TotIncomeJan = TotIncomeJan + rs.getInt("jan");
			TotIncomeFeb = TotIncomeFeb + rs.getInt("feb");
			TotIncomeMar = TotIncomeMar + rs.getInt("mar");
			TotIncomeApr = TotIncomeApr + rs.getInt("apr");
			TotIncomeMay = TotIncomeMay + rs.getInt("may");
			TotIncomeJun = TotIncomeJun + rs.getInt("jun");
			TotIncomeJul = TotIncomeJul + rs.getInt("jul");
			TotIncomeAug = TotIncomeAug + rs.getInt("aug");
			TotIncomeSept = TotIncomeSept + rs.getInt("sep");
			TotIncomeOct = TotIncomeOct + rs.getInt("oct");
			TotIncomeNov = TotIncomeNov + rs.getInt("nov");
			TotIncomeDec = TotIncomeDec + rs.getInt("dec");
			
			HSSFRow row = sheet.createRow((short)i++);
			row.createCell((short) 0).setCellValue(cnt);
			if(rs.getString("DISC").equalsIgnoreCase("OTHER ALLOWNCES SPECIAL")){
				row.createCell((short) 1).setCellValue("SPECIAL ALLOWANCE");
			}
			else{
			row.createCell((short) 1).setCellValue(""+rs.getString("DISC"));
			}
			row.createCell((short) 2).setCellValue(rs.getInt("jan"));
			
			System.out.println("10101....."+rs.getInt("feb"));
			row.createCell((short) 3).setCellValue((rs.getInt("feb")));
			row.createCell((short) 4).setCellValue((rs.getInt("mar")));
			row.createCell((short) 5).setCellValue((rs.getInt("apr")));
//			row.createCell((short) 5).setCellValue(""+rs.getDate("UANNO"));
			
	//		System.out.println("2020.....");
			
			row.createCell((short) 6).setCellValue(rs.getInt("may"));
			row.createCell((short) 7).setCellValue(rs.getInt("jun"));
			row.createCell((short) 8).setCellValue(rs.getInt("jul"));
			row.createCell((short) 9).setCellValue(rs.getInt("aug"));
            row.createCell((short) 10).setCellValue(rs.getInt("sep"));
          
            row.createCell((short) 11).setCellValue(rs.getInt("oct"));
			row.createCell((short) 12).setCellValue(rs.getInt("nov"));
			row.createCell((short) 13).setCellValue(rs.getInt("dec"));
			
			totalamt= rs.getInt("jan") + rs.getInt("feb") + rs.getInt("mar") + rs.getInt("apr") + rs.getInt("may") +
					rs.getInt("jun") + rs.getInt("jul") + rs.getInt("Aug") + rs.getInt("sep") + rs.getInt("oct") +
					rs.getInt("nov") + rs.getInt("dec");
			row.createCell((short) 14).setCellValue(totalamt);
	//		row.createCell((short) 13).setCellValue(""+rs.getString("net_amt"));
		
			System.out.println("0000....."+TotMonthWise);
			
			TotYearIncome = TotYearIncome + totalamt;
		}
		HSSFRow row = sheet.createRow((short) i);
		cell3=row.createCell((short) 1);
		cell3.setCellValue(" ");
		cell3.setCellStyle(my_style1);
		row = sheet.createRow((short) i+1);
		
		cell3=row.createCell((short) 1);
		cell3.setCellValue("TOTAL  : =  ");
		cell3.setCellStyle(my_style1);
		
		cell3=row.createCell((short) 2);
		cell3.setCellValue(TotIncomeJan);
		cell3.setCellStyle(my_style1);
		
		cell3=row.createCell((short) 3);
		cell3.setCellValue(TotIncomeFeb);
		cell3.setCellStyle(my_style1);
		
		cell3=row.createCell((short) 4);
		cell3.setCellValue(TotIncomeMar);
		cell3.setCellStyle(my_style1);
		
		cell3=row.createCell((short) 5);
		cell3.setCellValue(TotIncomeApr);
		cell3.setCellStyle(my_style1);
		
		cell3=row.createCell((short) 6);
		cell3.setCellValue(TotIncomeMay);
		cell3.setCellStyle(my_style1);
		
		cell3=row.createCell((short) 7);
		cell3.setCellValue(TotIncomeJun);
		cell3.setCellStyle(my_style1);
		
		cell3=row.createCell((short) 8);
		cell3.setCellValue(TotIncomeJul);
		cell3.setCellStyle(my_style1);
		
		cell3=row.createCell((short) 9);
		cell3.setCellValue(TotIncomeAug);
		cell3.setCellStyle(my_style1);
		
		cell3=row.createCell((short) 10);
		cell3.setCellValue(TotIncomeSept);
		cell3.setCellStyle(my_style1);
		
		cell3=row.createCell((short) 11);
		cell3.setCellValue(TotIncomeOct);
		cell3.setCellStyle(my_style1);
		
		cell3=row.createCell((short) 12);
		cell3.setCellValue(TotIncomeNov);
		cell3.setCellStyle(my_style1);
		
		cell3=row.createCell((short) 13);
		cell3.setCellValue(TotIncomeDec);
		cell3.setCellStyle(my_style1);
		
		cell3=row.createCell((short) 14);
		cell3.setCellValue(TotYearIncome);
		cell3.setCellStyle(my_style1);
		
					hwb.write(out1);
					out1.close();
					st.close();
					con.close();
	    
					System.out.println("Result OK.....");
		
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
		}
		
		public static void Retirement_ext_Credit_Leave(String frmdate,String todate,String filepath,String imgpath)
		{
		int i = 12;
		Properties prop = new Properties();
		try
	     {
		
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream stream = classLoader.getResourceAsStream("constant.properties");
			prop.load(stream);
	     }
	     catch (Exception e) {System.out.println("Error in constant properties Manager "+e);}
	     
		try
		{
			RepoartBean repBean  = new RepoartBean();
			
			LookupHandler lkh=new LookupHandler();
			Connection con =null;
			System.out.println( filepath);
		FileOutputStream out1 = new FileOutputStream(new File(filepath));
		HSSFWorkbook hwb=new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("CreditLeavelist");
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
		System.out.println("1111.....");
		
		sheet.setColumnWidth((short)0, (short)2000);
		sheet.setColumnWidth((short)1, (short)5000);
		/*sheet.setColumnWidth((short)2, (short)5000);*/
		sheet.setColumnWidth((short)2, (short)13000);
		sheet.setColumnWidth((short)3, (short)10000);
		sheet.setColumnWidth((short)4, (short)7000);
		sheet.setColumnWidth((short)5, (short)3000);
		sheet.setColumnWidth((short)6, (short)4000);
		sheet.setColumnWidth((short)7, (short)4000);
		sheet.setColumnWidth((short)8, (short)6000);
		sheet.setColumnWidth((short)9, (short)7000);
		sheet.setColumnWidth((short)10, (short)5000);
		sheet.setColumnWidth((short)11, (short)5000);
	
      HSSFCellStyle my_style = hwb.createCellStyle();
      HSSFCellStyle my_style1 = hwb.createCellStyle();

      HSSFFont my_font=hwb.createFont();
      my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
      my_style.setFont(my_font);
    
      
		HSSFRow rowtitle=   sheet.createRow((short)0);
		HSSFCell cell = rowtitle.createCell((short) 9);
		
		cell.setCellValue(prop.getProperty("bankName1"));
		cell.setCellStyle(my_style);
		HSSFRow rowtitle1=   sheet.createRow((short)1);
		HSSFCell cell1 = rowtitle1.createCell((short) 7);
		cell1.setCellValue(prop.getProperty("addressForReport"));
		cell1.setCellStyle(my_style);
		HSSFRow rowtitle2=   sheet.createRow((short)2);
		HSSFCell cell2 = rowtitle2.createCell((short) 9);
		cell2.setCellValue(prop.getProperty("contactForReport"));
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle31=   sheet.createRow((short)3);
		cell2 = rowtitle31.createCell((short) 9);
		cell2.setCellValue(	prop.getProperty("mailForReport"));
		cell2.setCellStyle(my_style);
		 long millis=System.currentTimeMillis();  
		 java.sql.Date date=new java.sql.Date(millis);  
		 DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		 String reportDate = df.format(date);
		 HSSFRow rowtitle32=   sheet.createRow((short)4);
		 cell2 = rowtitle32.createCell((short) 9);
		 cell2.setCellValue("Report Date:  "+reportDate);
		 cell2.setCellStyle(my_style);
		
		HSSFRow rowtitle3=   sheet.createRow((short)4);
		cell2=rowtitle3.createCell((short) 2);
		cell2.setCellValue("");
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle4=   sheet.createRow((short)5);
		rowtitle4.createCell((short) 0).setCellValue("");
		HSSFRow rowtitle5=   sheet.createRow((short)6);
		rowtitle5.createCell((short) 0).setCellValue("");
		
		
		HSSFFont blueFont = hwb.createFont();
		blueFont.setColor(HSSFColor.BLUE.index);
		
		HSSFCellStyle style = hwb.createCellStyle();
		//style.setFont(blueFont);
		style.setFillForegroundColor(HSSFColor.BLUE.index);
		
		
		HSSFRow head=   sheet.createRow((short)7);
		head.createCell((short) 0).setCellValue("");
		HSSFRow heading=   sheet.createRow((short)8);
		HSSFCell cell3 = heading.createCell((short) 0); 

		cell3.setCellValue("");
		cell3.setCellStyle(my_style1);
		HSSFRow rowhead=   sheet.createRow((short)9);
      sheet.createFreezePane( 0, 10, 0, 10 );
     
      my_style1.setAlignment((short) 2);
      my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
      my_style1.setFont(my_font);
      
		cell3=rowhead.createCell((short) 0);
		cell3.setCellValue("SR NO");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 1);
		cell3.setCellValue("EMPCODE");
		cell3.setCellStyle(my_style1);
		/*cell3=rowhead.createCell((short) 2);
		cell3.setCellValue("DATE ");
		cell3.setCellStyle(my_style1);*/
		cell3=rowhead.createCell((short) 2);
		cell3.setCellValue("EMP NAME");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 3);
		cell3.setCellValue("BRANCH");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 4);
		cell3.setCellValue("DESIG");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 5);
		cell3.setCellValue("STATUS");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 6);
		cell3.setCellValue("DOB");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short)7);
		cell3.setCellValue("DOJ ");
		cell3.setCellStyle(my_style1);

		cell3=rowhead.createCell((short) 8);
		cell3.setCellValue("RETIREMENT DATE ");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 9);
		cell3.setCellValue("RETIREMENT EXT. DATE ");
		cell3.setCellStyle(my_style1);
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 10);
		cell3.setCellValue("CATEGORY");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 11);
		cell3.setCellValue("CASTE");
		cell3.setCellStyle(my_style1);
		
		ReportDAO.OpenCon("", "", "",repBean);
		con = repBean.getCn();	
		Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
		ResultSet.CONCUR_READ_ONLY);
		
		String SQL = ""
				+ "select e.EMPNO ,e.EMPCODE,rtrim(e.FNAME)+' '+rtrim(e.MNAME)+' '+rtrim(e.LNAME) as NAME ,e.STATUS ,e.DOB , "
				+ "e.DOJ ,e.retirementdate ,e.RETIREMENT_EXT_PERIOD "
				+ " ,(select LKP_DISC  from LOOKUP where LKP_CODE ='CASTE' and LKP_SRNO =  e.CASTCD) as caste "
				+ ",(select LKP_DISC  from LOOKUP where LKP_CODE ='CATE' and LKP_SRNO = e.CATEGORYCD) as CATE "
				+ " ,(select LKP_DISC  from LOOKUP where LKP_CODE ='DESIG' and LKP_SRNO = e1.DESIG) as DESIG "
				+ " ,p.SITE_NAME  "
				+ "from empmast e, emptran e1 ,Project_Sites p where e.empno  =e1.EMPNO "
				+ " and e1.SRNO =(select MAX(srno) from EMPTRAN where EMPNO =e.EMPNO ) "
				+ " and p.SITE_ID =e1.PRJ_SRNO  and e.retirementdate < e.RETIREMENT_EXT_PERIOD "
				+ " and e.retirementdate between '"+frmdate+"' and  '"+todate+"' ";
		
		ResultSet rs=st.executeQuery(SQL);
		System.out.println("SQL:: "+SQL);
		int cnt =0;
		
		while(rs.next())
		{
			cnt++;
			
			HSSFRow row = sheet.createRow((short)i++);
			row.createCell((short) 0).setCellValue(cnt);
			row.createCell((short) 1).setCellValue(rs.getString("EMPCODE"));
		/*	row.createCell((short) 2).setCellValue(""+rs.getString("retirementdate"));*/
			row.createCell((short) 2).setCellValue(rs.getString("NAME"));
			row.createCell((short) 3).setCellValue((rs.getString("SITE_NAME")));
			row.createCell((short) 4).setCellValue((rs.getString("DESIG")));
			row.createCell((short) 5).setCellValue((rs.getString("STATUS")));
			row.createCell((short) 6).setCellValue(rs.getString("DOB"));
			row.createCell((short) 7).setCellValue(rs.getString("DOJ"));
			row.createCell((short) 8).setCellValue(rs.getString("retirementdate"));
			row.createCell((short) 9).setCellValue(rs.getString("RETIREMENT_EXT_PERIOD"));
            row.createCell((short) 10).setCellValue(rs.getString("cate"));
            row.createCell((short) 11).setCellValue(rs.getString("caste"));
			
			System.out.println("0000....."+cnt);
			
		}
		HSSFRow	head1=   sheet.createRow((short)i++);
		HSSFCell cell4;
		head1=   sheet.createRow((short)i++);
	 	cell4 = head1 .createCell((short) 0); 
		cell4.setCellValue("");
		cell4 = head1 .createCell((short) 1); 
		cell4.setCellValue("");
		cell4 = head1 .createCell((short) 2); 
		cell4.setCellValue("TOTAL EMPLOYEE(S) :    " +cnt);
		cell4.setCellStyle(my_style);
		
					hwb.write(out1);
					out1.close();
					st.close();
					con.close();
	    
					System.out.println("Result OK.....");
		
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
		}
		public static void BonusTentativeReport(String type,int year,int nextYear,float percent,String filepath, String imagepath)
		{
			
			try
			{
				System.out.println("Into BonusTentativeReport.....");
				int Fyear = year%1000;
				int FnextYr = nextYear%1000;
				RepoartBean repBean  = new RepoartBean();
				
				LookupHandler lkh=new LookupHandler();
				Connection con =null;
			FileOutputStream out1 = new FileOutputStream(new File(filepath));
			HSSFWorkbook hwb=new HSSFWorkbook();
			HSSFSheet sheet =  hwb.createSheet("Bonus Details");
			
			sheet.setColumnWidth((short)0, (short)3000);
			sheet.setColumnWidth((short)1, (short)4000);
			sheet.setColumnWidth((short)2, (short)11000);
			sheet.setColumnWidth((short)3, (short)4000);
			sheet.setColumnWidth((short)4, (short)4000);
			sheet.setColumnWidth((short)5, (short)4000);
			sheet.setColumnWidth((short)6, (short)4000);
			sheet.setColumnWidth((short)7, (short)4000);
			sheet.setColumnWidth((short)8, (short)4000);
			sheet.setColumnWidth((short)9, (short)4000);
			sheet.setColumnWidth((short)10, (short)4000);
			sheet.setColumnWidth((short)11, (short)4000);
			sheet.setColumnWidth((short)12, (short)4000);
			sheet.setColumnWidth((short)13, (short)4000);
			sheet.setColumnWidth((short)14, (short)4000);
			sheet.setColumnWidth((short)15, (short)4000);
			sheet.setColumnWidth((short)16, (short)4000);
			sheet.setColumnWidth((short)17, (short)4000);
	
	        HSSFCellStyle my_style = hwb.createCellStyle();
	        HSSFCellStyle my_style1 = hwb.createCellStyle();

	        HSSFFont my_font=hwb.createFont();
	        my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        my_style.setFont(my_font);
	        
	        my_style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        
			HSSFRow rowtitle=   sheet.createRow((short)0);
			HSSFCell cell = rowtitle.createCell((short) 9);
			
			cell.setCellValue("THE BUSINESS CO.OP BANK LTD.");
			cell.setCellStyle(my_style);
			HSSFRow rowtitle1=   sheet.createRow((short)1);
			HSSFCell cell1 = rowtitle1.createCell((short) 7);
			cell1.setCellValue("Rajan Complex Arcade, Datta Mandir, Nashik Road, Nashik - 422101");
			cell1.setCellStyle(my_style);
			HSSFRow rowtitle2=   sheet.createRow((short)2);
			HSSFCell cell2 = rowtitle2.createCell((short) 9);
			cell2.setCellValue("Tel : 0253-2406100, 2469545");
			cell2.setCellStyle(my_style);
			HSSFRow rowtitle31=   sheet.createRow((short)3);
			cell2 = rowtitle31.createCell((short) 9);
					cell2.setCellValue("Email :");
					cell2.setCellStyle(my_style);
			HSSFRow rowtitle3=   sheet.createRow((short)4);
			cell2=rowtitle3.createCell((short) 2);
			cell2.setCellValue("");
			cell2.setCellStyle(my_style);
			HSSFRow rowtitle4=   sheet.createRow((short)5);
			rowtitle4.createCell((short) 0).setCellValue("");
			HSSFRow rowtitle5=   sheet.createRow((short)6);
			rowtitle5.createCell((short) 0).setCellValue("");
			
			HSSFFont blueFont = hwb.createFont();
			blueFont.setColor(HSSFColor.BLUE.index);
			
			HSSFCellStyle style = hwb.createCellStyle();
			style.setFillForegroundColor(HSSFColor.BLUE.index);
			
			
			
			
			HSSFRow head=   sheet.createRow((short)7);
			head.createCell((short) 0).setCellValue("");
			HSSFRow heading=   sheet.createRow((short)8);
			HSSFCell cell3 = heading.createCell((short) 0); 

			cell3.setCellValue("");
			cell3.setCellStyle(my_style1);
			HSSFRow rowhead=   sheet.createRow((short)9);
	        sheet.createFreezePane( 0, 10, 0, 10 );
	       
	        my_style1.setAlignment((short) 2);
	        my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        my_style1.setFont(my_font);
	        my_style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        
			cell3=rowhead.createCell((short) 0);
			cell3.setCellValue("SR.NO");
			cell3.setCellStyle(my_style1);
			cell3=rowhead.createCell((short) 1);
			cell3.setCellValue("EMP CODE");
			cell3.setCellStyle(my_style1);
			cell3=rowhead.createCell((short) 2);
			cell3.setCellValue("EMPLOYEE NAME");
			cell3.setCellStyle(my_style1);
			cell3=rowhead.createCell((short) 3);
			cell3.setCellValue("APR AMT-"+Fyear);
			cell3.setCellStyle(my_style1);
			cell3=rowhead.createCell((short) 4);
			cell3.setCellValue("MAY AMT-"+Fyear);
			cell3.setCellStyle(my_style1);
			cell3=rowhead.createCell((short) 5);
			cell3.setCellValue("JUN AMT-"+Fyear);
			cell3.setCellStyle(my_style1);
			cell3=rowhead.createCell((short) 6);
			cell3.setCellValue("JUL AMT_"+Fyear);
			cell3.setCellStyle(my_style1);
			cell3=rowhead.createCell((short) 7);
			cell3.setCellValue("AUG AMT-"+Fyear);
			cell3.setCellStyle(my_style1);
			cell3=rowhead.createCell((short)8);
			cell3.setCellValue("SEP AMT-"+Fyear);
			cell3.setCellStyle(my_style1);

			cell3=rowhead.createCell((short) 9);
			cell3.setCellValue("OCT AMT-"+Fyear);
			cell3.setCellStyle(my_style1);
			cell3=rowhead.createCell((short) 10);
			cell3.setCellValue("NOV AMT-"+Fyear);
			cell3.setCellStyle(my_style1);
			
			cell3=rowhead.createCell((short) 11);
			cell3.setCellValue("DEC AMT-"+Fyear);
			cell3.setCellStyle(my_style1);
			cell3=rowhead.createCell((short) 12);
			cell3.setCellValue("JAN AMT-"+FnextYr);
			cell3.setCellStyle(my_style1);
			cell3=rowhead.createCell((short) 13);
			cell3.setCellValue("FEB AMT-"+FnextYr);
			cell3.setCellStyle(my_style1);
			cell3=rowhead.createCell((short) 14);
			cell3.setCellValue("MAR AMT-"+FnextYr);
			cell3.setCellStyle(my_style1);
			cell3=rowhead.createCell((short) 15);
			cell3.setCellValue("PERCENT");
			cell3.setCellStyle(my_style1);
			
			cell3=rowhead.createCell((short) 16);
			cell3.setCellValue("AMT FOR BONUS");
			cell3.setCellStyle(my_style1);
			
			cell3=rowhead.createCell((short) 17);
			cell3.setCellValue("BONUS");
			cell3.setCellStyle(my_style1);
			
			ReportDAO.OpenCon("", "", "",repBean);
			con = repBean.getCn();	
			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			ResultSet rs1 = null;
			Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String BOY =ReportDAO.BoFinancialy("01-Apr-"+year);
  			int next_Year=year+1;
  			String EOY =ReportDAO.EoFinancialy("31-Mar-"+nextYear);
  			System.out.println(BOY);
  			System.out.println(EOY);
			int i=12;
			int count=0;
			String SQL = "";
			String qurey ="with bonus as(select e.empno, "+
 					 "sum(case datepart(MM,trndt) when 04 then NET_AMT else  0 end) apr, "+
 					 "sum(case datepart(MM,trndt) when 05 then NET_AMT else  0 end) may,"+
 					 "sum(case datepart(MM,trndt) when 06 then NET_AMT else  0 end) jun, "+
 					 "sum(case datepart(MM,trndt) when 07 then NET_AMT else  0 end) jul,"+
 					 "sum(case datepart(MM,trndt) when 08 then NET_AMT else  0 end) aug,"+
 					 "sum(case datepart(MM,trndt) when 09 then NET_AMT else  0 end) sep,"+
 					 "sum(case datepart(MM,trndt) when 10 then NET_AMT else  0 end) oct,"+ 
 					 "sum(case datepart(MM,trndt) when 11 then NET_AMT else  0 end) nov,"+
 					 "sum(case datepart(MM,trndt) when 12 then NET_AMT else  0 end) dec,"+
 					 "sum(case datepart(MM,trndt) when 01 then NET_AMT else  0 end) jan,"+
 					 "sum(case datepart(MM,trndt) when 02 then NET_AMT else  0 end) feb,"+
 					 "sum(case datepart(MM,trndt) when 03 then NET_AMT else  0 end) mar"+
 					 " from empmast e, cdmast c, paytran_stage y where e.empno = y.empno "+
 					 " and y.trncd = c.trncd  and y.trndt between '"+BOY+"' and '"+EOY+"' " +
 					 " and y.trncd in(101,102,138,147,142) group by e.empno)  "+
 					  " select b.empno,rtrim(e.fname)+' '+rtrim(e.lname) name,t.prj_srno as site_id , "+
 					   "E.DOJ,E.empcode, (b.apr ) as apramt , (b.may) as mayamt, "+
 					   "(b.jun) as junamt,  (b.jul) as julamt," +
 					   " (b.aug) as augamt, (b.sep) as sepamt , (b.oct) "+
 					  " as octamt , (b.nov) as novamt , (b.dec) as decamt , " +
 					  " (b.jan) as janamt, (b.feb) as febamt, (b.mar)" +
 					  " as maramt, " +
 					  " (b.apr+b.may+b.jun+b.jul+b.aug+b.sep+b.oct+b.nov+b.dec+b.jan+b.feb+b.mar "+
 					   " ) as amtForBonus, "+
 					     "(((b.apr+b.may+b.jun+b.jul+b.aug+b.sep+b.oct+b.nov+b.dec+b.jan+b.feb+b.mar "+
 					    "))/100*"+percent+") "+
 					    " as bonusamt from bonus  b ,EMPMAST E ,emptran t where b.EMPNO=E.empno" +
 					    " and E.EMPNO=t.EMPNO and  T.SRNO = (SELECT MAX(E2.SRNO)" +
 					    " FROM EMPTRAN E2 WHERE E2.EMPNO =" +
 					    " E.EMPNO AND E2.EFFDATE <= '"+EOY+"')" +
 					    "  order by E.empcode  "; 
			
			  
 			System.out.println(qurey);
 			rs = st.executeQuery(qurey);
			int srno=0;
			double totatlBonus = 0.0f;
			float amtforbonus = 0.0f;
			String numberAsString="";
			double number =0.0f;
			while(rs.next())
			{
				count++;
				srno++;
				HSSFRow row = sheet.createRow((short)i++);
				
				row.createCell((short) 0).setCellValue(""+srno);
				row.createCell((short) 1).setCellValue(""+rs.getString("empcode"));
				row.createCell((short) 2).setCellValue(""+rs.getString("NAME"));
				row.createCell((short) 3).setCellValue(""+(rs.getFloat("apramt")));
				row.createCell((short) 4).setCellValue(""+rs.getFloat("mayamt"));
				row.createCell((short) 5).setCellValue(""+rs.getFloat("junamt"));
				row.createCell((short) 6).setCellValue(rs.getFloat("julamt"));
				row.createCell((short) 7).setCellValue(""+(rs.getFloat("augamt")));
				row.createCell((short) 8).setCellValue(""+rs.getFloat("sepamt"));
				row.createCell((short) 9).setCellValue(""+rs.getFloat("octamt"));
				row.createCell((short) 10).setCellValue(""+rs.getFloat("novamt"));
				row.createCell((short) 11).setCellValue(""+rs.getFloat("decamt"));
				row.createCell((short) 12).setCellValue(""+rs.getFloat("janamt"));
				row.createCell((short) 13).setCellValue(""+rs.getFloat("febamt"));
				row.createCell((short) 14).setCellValue(""+rs.getFloat("maramt"));
				row.createCell((short) 15).setCellValue(""+percent);
				
				amtforbonus = rs.getFloat("amtforbonus");
				
				float hraamt = 0.0f;
				float hraamt1 = 0.0f;
				String TotBonusAMT ="";
			//	279,173,383,800
				if(rs.getString("empno").equals("279") || rs.getString("empno").equals("173") || rs.getString("empno").equals("383") || rs.getString("empno").equals("800")
					||rs.getString("empno").equals("559") || rs.getString("empno").equals("630") || rs.getString("empno").equals("376") || rs.getString("empno").equals("420")	
					||rs.getString("empno").equals("566") || rs.getString("empno").equals("631") || rs.getString("empno").equals("378") || rs.getString("empno").equals("421")	
					||rs.getString("empno").equals("586") || rs.getString("empno").equals("632") || rs.getString("empno").equals("387") || rs.getString("empno").equals("422")
					||rs.getString("empno").equals("610") || rs.getString("empno").equals("643") || rs.getString("empno").equals("407") || rs.getString("empno").equals("428")
					||rs.getString("empno").equals("611") || rs.getString("empno").equals("276") || rs.getString("empno").equals("408") || rs.getString("empno").equals("432")
					||rs.getString("empno").equals("614") || rs.getString("empno").equals("303") || rs.getString("empno").equals("409") || rs.getString("empno").equals("457")
					||rs.getString("empno").equals("627") || rs.getString("empno").equals("311") || rs.getString("empno").equals("411") || rs.getString("empno").equals("470")
					||rs.getString("empno").equals("629") || rs.getString("empno").equals("323") || rs.getString("empno").equals("413")
					)
				{
				 SQL = ""
						+ "select case when (select ISNULL( max(HRA) ,0) as HRA from Increment where EMPNO ="+rs.getString("empno")+" and ISPROCESED =1) =0 "
						+ "then 0 else "
						+ " (isnull((abs ( "
						+ "(select inp_amt  from PAYTRAN_STAGE  where EMPNO ="+rs.getString("empno")+" and  TRNCD =103 "
						+ "and TRNDT =(select  convert(date,DATEADD(day, -1, '"+BOY+"'),105))) "
						+ "- "
						+ "(select ISNULL( max(HRA) ,0) as HRA from Increment where EMPNO ="+rs.getString("empno")+" and ISPROCESED =1) "
						+ " "
						+ ")*2),0)) end  as amt";
				}
				else{
				 SQL = ""
						+ "select case when isnull( (select isnull(net_amt, 0) from paytran_stage where TRNDT ='2017-06-30' and TRNCD =142 and empno = "+rs.getString("empno")+" "
						+ "and (isnull(NET_AMT,0) !=0)),0) =0 "
						+ "then 0 else "
						+ " (isnull((abs ( ( ( select top 1 hra  from GRADE_MASTER where BASIC = ( "
						+ "select top 1 BASIC+increment  from grade_master where BASIC = ( select inp_amt "
						+ "	from paytran_stage "
						+ "	where empno = "+rs.getString("empno")+" and TRNCD = 101 and "
						+ "	TRNDT='2017-03-31'   )	)) "
						+ "- ( select inp_amt "
						+ "from paytran_stage "
						+ "where empno = "+rs.getString("empno")+" and TRNCD = 103 and "
						+ "TRNDT='2017-03-31'   )) "
						+ "				 "
						+ ")*2),0)) end  as amt";
				}
				
				
				rs1 = st1.executeQuery(SQL);
				if(rs1.next())
				{
					hraamt = rs1.getFloat("amt");
				}
				if(rs.getString("empno").equals("816")){
					hraamt = 0;
				}
				hraamt1 = amtforbonus - hraamt;
				row.createCell((short) 16).setCellValue((hraamt1));
				hraamt1 =(hraamt1/100)*(percent);
			//	System.out.println("hraamt1:  "+hraamt1);
			//	System.out.println("amtforbonus:  "+amtforbonus);
				
				if(rs.getString("empno").equals("173")){
					System.out.println("SQL:  "+SQL);
					System.out.println("hraamt:  "+hraamt);
					System.out.println("hraamt1:  "+hraamt1);
					System.out.println("amtforbonus:  "+amtforbonus);
				}
				
			//	row.createCell((short) 17).setCellValue(Math.round(rs.getFloat("bonusamt")));
				row.createCell((short) 17).setCellValue(Math.round(hraamt1));
				//totatlBonus = totatlBonus + rs.getInt("bonusamt");
				totatlBonus = totatlBonus + Math.round(hraamt1);
			//	System.out.println("totatlBonus:  "+totatlBonus);
				
				TotBonusAMT = String.valueOf(totatlBonus);
			//	System.out.println("totatlBonus 123 :  "+TotBonusAMT);
				
				number = totatlBonus;
				DecimalFormat decimalFormat = new DecimalFormat("0.00");
			    numberAsString = decimalFormat.format(number);
			//	System.out.println("AJ.. "+numberAsString);
				
			}
			HSSFRow row = sheet.createRow((short) i);
			row.createCell((short) 0).setCellValue(" ");
			  row = sheet.createRow((short) i+1);
			
			row.createCell((short) 0).setCellValue("Total Employee : =  " +(count++) );
			row.createCell((short) 16).setCellValue("Bonus Total : = ");
			if(number <0 ){
			  row.createCell((short) 17).setCellValue("0");
			}else{
				row.createCell((short) 17).setCellValue(""+numberAsString);	
			}
			number =0;
			
			hwb.write(out1);
			out1.close();
			st.close();
		    con.close();

			}
			
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		public static void Ex_Gracia(String type,String reportType,String branch,String desig,String Year,String Month,String MonthDT,float percent,String filepath, String imagepath)
		{
			
			try
			{
				System.out.println("Into Ex_Gracia.....");
				RepoartBean repBean  = new RepoartBean();
				LookupHandler lkh=new LookupHandler();
				Connection con =null;
			FileOutputStream out1 = new FileOutputStream(new File(filepath));
			HSSFWorkbook hwb=new HSSFWorkbook();
			HSSFSheet sheet =  hwb.createSheet("Bonus Ex_Gracia");
			
			sheet.setColumnWidth((short)0, (short)3000);
			sheet.setColumnWidth((short)1, (short)4000);
			sheet.setColumnWidth((short)2, (short)11000);
			sheet.setColumnWidth((short)3, (short)4000);
			sheet.setColumnWidth((short)4, (short)7000);
			sheet.setColumnWidth((short)5, (short)7000);
			sheet.setColumnWidth((short)6, (short)4000);
			sheet.setColumnWidth((short)7, (short)4000);
			sheet.setColumnWidth((short)8, (short)4000);
			
	        HSSFCellStyle my_style = hwb.createCellStyle();
	        HSSFCellStyle my_style1 = hwb.createCellStyle();

	        HSSFFont my_font=hwb.createFont();
	        my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        my_style.setFont(my_font);
	        my_style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        HSSFRow rowtitle=   sheet.createRow((short)0);
			HSSFCell cell = rowtitle.createCell((short) 4);
			
			cell.setCellValue("THE BUSINESS CO.OP BANK LTD.");
			cell.setCellStyle(my_style);
			HSSFRow rowtitle1=   sheet.createRow((short)1);
			HSSFCell cell1 = rowtitle1.createCell((short) 3);
			cell1.setCellValue("Rajan Complex Arcade, Datta Mandir, Nashik Road, Nashik - 422101");
			cell1.setCellStyle(my_style);
			HSSFRow rowtitle2=   sheet.createRow((short)2);
			HSSFCell cell2 = rowtitle2.createCell((short) 4);
			cell2.setCellValue("Tel : 0253-2406100, 2469545");
			cell2.setCellStyle(my_style);
			HSSFRow rowtitle31=   sheet.createRow((short)3);
			cell2 = rowtitle31.createCell((short) 4);
					cell2.setCellValue("Email :");
					cell2.setCellStyle(my_style);
			HSSFRow rowtitle3=   sheet.createRow((short)4);
			cell2=rowtitle3.createCell((short) 4);
			cell2.setCellValue("");
			cell2.setCellStyle(my_style);
			HSSFRow rowtitle4=   sheet.createRow((short)5);
			rowtitle4.createCell((short) 0).setCellValue("");
			HSSFRow rowtitle5=   sheet.createRow((short)6);
			rowtitle5.createCell((short) 0).setCellValue("");
			
			HSSFFont blueFont = hwb.createFont();
			blueFont.setColor(HSSFColor.BLUE.index);
			
			HSSFCellStyle style = hwb.createCellStyle();
			style.setFillForegroundColor(HSSFColor.BLUE.index);
			
			HSSFRow head=   sheet.createRow((short)7);
			head.createCell((short) 0).setCellValue("");
			HSSFRow heading=   sheet.createRow((short)8);
			HSSFCell cell3 = heading.createCell((short) 0); 

			cell3.setCellValue("");
			cell3.setCellStyle(my_style1);
			HSSFRow rowhead=   sheet.createRow((short)9);
	        sheet.createFreezePane( 0, 10, 0, 10 );
	       
	        my_style1.setAlignment((short) 2);
	        my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        my_style1.setFont(my_font);
	        my_style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        
			cell3=rowhead.createCell((short) 0);
			cell3.setCellValue("SR.NO");
			cell3.setCellStyle(my_style1);
			cell3=rowhead.createCell((short) 1);
			cell3.setCellValue("EMP CODE");
			cell3.setCellStyle(my_style1);
			cell3=rowhead.createCell((short) 2);
			cell3.setCellValue("EMPLOYEE NAME");
			cell3.setCellStyle(my_style1);
			cell3=rowhead.createCell((short) 3);
			cell3.setCellValue(Month+" AMT");
			cell3.setCellStyle(my_style1);
			cell3=rowhead.createCell((short) 4);
			cell3.setCellValue("GROSS AMT FOR BONUS");
			cell3.setCellStyle(my_style1);
			
			cell3=rowhead.createCell((short) 5);
			cell3.setCellValue("Calculated Bonus Amount ");
			cell3.setCellStyle(my_style1);
			
			cell3=rowhead.createCell((short) 6);
			cell3.setCellValue(" DED AMT");
			cell3.setCellStyle(my_style1);
			cell3=rowhead.createCell((short) 7);
			cell3.setCellValue("NET BONUS AMT");
			cell3.setCellStyle(my_style1);
			
			ReportDAO.OpenCon("", "", "",repBean);
			con = repBean.getCn();	
			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			String SQL = "";
			int i=12;
			int count=0;
			float inpamt = 0.0f;
			float calamt = 0.0f;
			float Dedamt = 0.0f;
			float Netamt = 0.0f;
			float Calamt = 0.0f;
			float amtforbonus = 0.0f;
			float totatlBonus = 0.0f;
			
			if(reportType.equals("1")){
				System.out.println("in ALL");
					SQL = ""
							+ "select  sum(p.INP_AMT)as INP_AMT,sum(p.CAL_AMT)as CAL_AMT,e.EMPCODE,(select Rtrim(ee.fname) + ' ' + Rtrim(ee.mname) + ' ' "
							+ "       + Rtrim(ee.lname) from empmast ee where ee.EMPNO = p.EMPNO) AS NAME "
							+ "from PAYTRAN_STAGE p,empmast e "
							+ "where   p.TRNCD in ( 101,102,138,147,142) "
							+ "and p.trndt = '"+ReportDAO.EOM(MonthDT)+"' and p.EMPNO=e.EMPNO "
							+ "group by p.EMPNO,e.EMPCODE";
							
							
							/*+ "SELECT e.empcode, "
							+ "       Rtrim(e.fname) + ' ' + Rtrim(e.mname) + ' ' "
							+ "       + Rtrim(e.lname) AS NAME, "
							+ "b.amtforbonus,b.bonus,b."+Month+" ,"
							+ "ISNULL((select p.NET_AMT from PAYTRAN_STAGE p where p.TRNCD=301 "
							+ "       and p.TRNDT='"+ReportDAO.EOM(MonthDT)+"' and e.empno=p.EMPNO),0)as lwpAmt  "
							+ "FROM   empmast e, "
							+ "       bonuscal b "
							+ "WHERE  b.empno = e.empno "
							+ "       AND year = '"+Year+"' "
							+ "       AND b.status = 'P' "
							+ "ORDER  BY e.empcode";*/
					
					/*String varname1 = ""
							+ "SELECT e.empcode, "
							+ "       Rtrim(e.fname) + ' ' + Rtrim(e.mname) + ' ' "
							+ "       + Rtrim(e.lname) AS NAME, "
							+ "       b.amtforbonus, "
							+ "       b.bonus, "
							+ "       b.janamt,ISNULL((select p.NET_AMT from PAYTRAN_STAGE p where p.TRNCD=301 "
							+ "       and p.TRNDT='"+ReportDAO.EOM(date)+"' and e.empno=p.EMPNO),0)as lwpAmt "
							+ "FROM   empmast e, "
							+ "       bonuscal b "
							+ "WHERE  b.empno = e.empno "
							+ "       AND b.year = '2017-2018' "
							+ "       AND b.status = 'P' "
							+ "     ORDER  BY e.empcode";*/
					
			}else if(reportType.equals("2")){
				System.out.println("in BRANCHWISE");
				 SQL = ""
						 + "select  sum(p.INP_AMT)as INP_AMT,sum(p.CAL_AMT)as CAL_AMT,e.EMPCODE,(select Rtrim(ee.fname) + ' ' + Rtrim(ee.mname) + ' ' "
						 + "       + Rtrim(ee.lname) from empmast ee where ee.EMPNO = p.EMPNO) AS NAME "
						 + "from PAYTRAN_STAGE p,empmast e, emptran t "
						 + "where   p.TRNCD in ( 101,102,138,147,142) "
						 + "and p.trndt = '"+ReportDAO.EOM(MonthDT)+"' and p.EMPNO=e.EMPNO "
						 + "AND e.empno = t.empno "
						 + " AND t.branch = "+branch+" "
						 + " AND t.srno = (SELECT Max(t1.srno) "
						 + "  FROM   emptran t1 "
						 + "  WHERE  t1.empno = t.empno) "
						 + "group by p.EMPNO,e.EMPCODE";
						 
						/*+ "SELECT e.empcode,Rtrim(e.fname) + ' ' + Rtrim(e.mname) + ' '+ Rtrim(e.lname) "
						+ "AS NAME,b.amtforbonus,b.bonus,b."+Month+" ," 
						+ "ISNULL((select p.NET_AMT from PAYTRAN_STAGE p where p.TRNCD=301 "
						+ " and p.TRNDT='"+ReportDAO.EOM(MonthDT)+"' and e.empno=p.EMPNO),0)as lwpAmt  "
						+"FROM   empmast e,bonuscal b ,emptran t "
						+ "WHERE  b.empno = e.empno and year='"+Year+"'   and b.status='P' "
						+ "and e.EMPNO=t.EMPNO and t.BRANCH="+branch+" "
						+ "and t.SRNO=(select MAX(t1.srno) from EMPTRAN t1 where t1.EMPNO=t.EMPNO) "
						+ "order by e.EMPCODE";*/
				
			}
			else if(reportType.equals("3")){
				System.out.println("in DESIGWISE");
				SQL = ""
						+ "select  sum(p.INP_AMT)as INP_AMT,sum(p.CAL_AMT)as CAL_AMT,e.EMPCODE,(select Rtrim(ee.fname) + ' ' + Rtrim(ee.mname) + ' ' "
						+ " + Rtrim(ee.lname) from empmast ee where ee.EMPNO = p.EMPNO) AS NAME "
						+ "from PAYTRAN_STAGE p,empmast e, emptran t "
						+ "where   p.TRNCD in ( 101,102,138,147,142) "
						+ "and p.trndt = '"+ReportDAO.EOM(MonthDT)+"' and p.EMPNO=e.EMPNO "
						+ "AND e.empno = t.empno "
						+ " AND t.desig = "+desig+" "
						+ " AND t.srno = (SELECT Max(t1.srno) "
						+ "  FROM   emptran t1 "
						+ "  WHERE  t1.empno = t.empno) "
						+ "group by p.EMPNO,e.EMPCODE";
						/*+ "SELECT e.empcode,Rtrim(e.fname) + ' ' + Rtrim(e.mname) + ' '+ Rtrim(e.lname) "
						+ "AS NAME,b.amtforbonus,b.bonus,b."+Month+" ,"
						+ "ISNULL((select p.NET_AMT from PAYTRAN_STAGE p where p.TRNCD=301 "
						+ "       and p.TRNDT='"+ReportDAO.EOM(MonthDT)+"' and e.empno=p.EMPNO),0)as lwpAmt  "
						+"FROM   empmast e,bonuscal b ,emptran t "
						+ "WHERE  b.empno = e.empno and year='"+Year+"'  and b.status='P' "
						+ "and e.EMPNO=t.EMPNO  "
						+ "and t.DESIG = "+desig+" "
						+ "and t.SRNO=(select MAX(t1.srno) from EMPTRAN t1 where t1.EMPNO=t.EMPNO) "
						+ "order by e.EMPCODE";*/
				
			}
					rs = st.executeQuery(SQL);
			System.out.println("query for details  "+SQL);
			int srno=0;
			
			while(rs.next())
			{
				count++;
				srno++;
				HSSFRow row = sheet.createRow((short)i++);
				
				row.createCell((short) 0).setCellValue(""+srno);
				row.createCell((short) 1).setCellValue(""+rs.getString("empcode"));
				row.createCell((short) 2).setCellValue(""+rs.getString("NAME"));
			    row.createCell((short) 3).setCellValue((rs.getString("INP_AMT")));
				row.createCell((short) 4).setCellValue(rs.getInt("INP_AMT"));
				inpamt = rs.getInt("INP_AMT");
				calamt = rs.getInt("CAL_AMT");
				Calamt = (inpamt/100)*(percent);
				Dedamt = inpamt - calamt;
				Dedamt = Math.round((Dedamt/100)*(percent));
				Netamt = Calamt - Dedamt;
				totatlBonus = totatlBonus+Netamt;
				row.createCell((short) 5).setCellValue(Math.round(Calamt));
				row.createCell((short) 6).setCellValue(Dedamt);
				row.createCell((short) 7).setCellValue(Math.round(Netamt));
			}
			HSSFRow row = sheet.createRow((short) i);
			row.createCell((short) 0).setCellValue(" ");
			row = sheet.createRow((short) i+1);
			
			row.createCell((short) 0).setCellValue("Total Employee : =  " +(count++) );
			row.createCell((short) 6).setCellValue("Bonus Total : = ");
			row.createCell((short) 7).setCellValue(Math.round(totatlBonus));
			
			hwb.write(out1);
			out1.close();
			st.close();
		    con.close();

			}
			
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		public static void Left_Employee_Report(String frmdate,String todate,String type,String Branch,String Desig,String LeftRepType,String filepath,String imgpath)
		{
		int i = 12;
		Properties prop = new Properties();
		try
	     {
		System.out.println("frmdate:  "+frmdate+"  todate:  "+todate);
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream stream = classLoader.getResourceAsStream("constant.properties");
			prop.load(stream);
	     }
	     catch (Exception e) {System.out.println("Error in constant properties Manager "+e);}
	     
		try
		{
			RepoartBean repBean  = new RepoartBean();
			
			LookupHandler lkh=new LookupHandler();
			Connection con =null;
			System.out.println( filepath);
		FileOutputStream out1 = new FileOutputStream(new File(filepath));
		HSSFWorkbook hwb=new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("LeftEmployeeReport");
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
		
		sheet.setColumnWidth((short)0, (short)2000);
		sheet.setColumnWidth((short)1, (short)3000);
		sheet.setColumnWidth((short)2, (short)13000);
		sheet.setColumnWidth((short)3, (short)10000);
		sheet.setColumnWidth((short)4, (short)6000);
		sheet.setColumnWidth((short)5, (short)5000);
		sheet.setColumnWidth((short)6, (short)5000);
		sheet.setColumnWidth((short)7, (short)6000);
		sheet.setColumnWidth((short)8, (short)6000);
		sheet.setColumnWidth((short)9, (short)7000);
	
      HSSFCellStyle my_style = hwb.createCellStyle();
      HSSFCellStyle my_style1 = hwb.createCellStyle();

      HSSFFont my_font=hwb.createFont();
      my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
      my_style.setFont(my_font);
    
      
		HSSFRow rowtitle=   sheet.createRow((short)0);
		HSSFCell cell = rowtitle.createCell((short) 3);
		
		cell.setCellValue(prop.getProperty("bankName1"));
		cell.setCellStyle(my_style);
		HSSFRow rowtitle1=   sheet.createRow((short)1);
		HSSFCell cell1 = rowtitle1.createCell((short) 3);
		cell1.setCellValue(prop.getProperty("addressForReport"));
		cell1.setCellStyle(my_style);
		HSSFRow rowtitle2=   sheet.createRow((short)2);
		HSSFCell cell2 = rowtitle2.createCell((short) 3);
		cell2.setCellValue(prop.getProperty("contactForReport"));
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle31=   sheet.createRow((short)3);
		cell2 = rowtitle31.createCell((short) 3);
		cell2.setCellValue(	prop.getProperty("mailForReport"));
		cell2.setCellStyle(my_style);
		 long millis=System.currentTimeMillis();  
		 java.sql.Date date=new java.sql.Date(millis);  
		 DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		 String reportDate = df.format(date);
		 HSSFRow rowtitle32=   sheet.createRow((short)4);
		 cell2 = rowtitle32.createCell((short) 3);
		 cell2.setCellValue("Report Date:  "+reportDate);
		 cell2.setCellStyle(my_style);
		
		 HSSFRow rowtitle311=   sheet.createRow((short)3);
		 cell2 = rowtitle311.createCell((short) 3);
		 cell2.setCellValue("");
		 cell2.setCellStyle(my_style);
		 
		HSSFRow rowtitle3=   sheet.createRow((short)4);
		cell2=rowtitle3.createCell((short) 3);
		cell2.setCellValue("Left Employee List:-  "+reportDate);
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle4=   sheet.createRow((short)5);
		rowtitle4.createCell((short) 0).setCellValue("");
		HSSFRow rowtitle5=   sheet.createRow((short)6);
		rowtitle5.createCell((short) 0).setCellValue("");
		
		
		HSSFFont blueFont = hwb.createFont();
		blueFont.setColor(HSSFColor.BLUE.index);
		
		HSSFCellStyle style = hwb.createCellStyle();
		//style.setFont(blueFont);
		style.setFillForegroundColor(HSSFColor.BLUE.index);
		
		
		HSSFRow head=   sheet.createRow((short)7);
		head.createCell((short) 0).setCellValue("");
		HSSFRow heading=   sheet.createRow((short)8);
		HSSFCell cell3 = heading.createCell((short) 0); 

		cell3.setCellValue("");
		cell3.setCellStyle(my_style1);
		HSSFRow rowhead=   sheet.createRow((short)9);
      sheet.createFreezePane( 0, 10, 0, 10 );
     
      my_style1.setAlignment((short) 2);
      my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
      my_style1.setFont(my_font);
      
		cell3=rowhead.createCell((short) 0);
		cell3.setCellValue("SR NO");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 1);
		cell3.setCellValue("EMPCODE");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 2);
		cell3.setCellValue("EMP NAME");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 3);
		cell3.setCellValue("BRANCH");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 4);
		cell3.setCellValue("DESIG");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 5);
		cell3.setCellValue("RETIREMENT DATE");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 6);
		cell3.setCellValue("LEFT DATE");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short)7);
		cell3.setCellValue("P FILE ");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 8);
		cell3.setCellValue("CATEGORY");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 9);
		cell3.setCellValue("CASTE");
		cell3.setCellStyle(my_style1);
		cell3=rowhead.createCell((short) 10);
		cell3.setCellValue("REASON");
		cell3.setCellStyle(my_style1);
		
		ReportDAO.OpenCon("", "", "",repBean);
		con = repBean.getCn();	
		Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
		ResultSet.CONCUR_READ_ONLY);
		String SQL = "";
		String constant="";
		
		if(LeftRepType.equals("1")){
			constant =" and e1.prj_srno="+Branch;
		}else if(LeftRepType.equals("2")){
			constant =" and e1.DESIG="+Desig;
		}else if(LeftRepType.equals("0")){
			constant =" ";
		}
		if(type.equals("8")){
			System.out.println("i am in type 1");
			 SQL = ""
			+ "SELECT e.empno, "
			+ "       e.empcode, "
			+ "       Rtrim(e.fname) + ' ' + Rtrim(e.mname) + ' ' "
			+ "       + Rtrim(e.lname)                     AS NAME, "
			+ "       e.status, "
			+ "	   e.retirementdate, "
			+ "	   (SELECT lkp_disc "
			+ "        FROM   lookup "
			+ "        WHERE  lkp_code = 'CASTE' "
			+ "               AND lkp_srno = e.castcd)     AS caste, "
			+ "       (SELECT lkp_disc "
			+ "        FROM   lookup "
			+ "        WHERE  lkp_code = 'CATE' "
			+ "               AND lkp_srno = e.categorycd) AS CATE, "
			+ "       (SELECT lkp_disc "
			+ "        FROM   lookup "
			+ "        WHERE  lkp_code = 'DESIG' "
			+ "               AND lkp_srno = e1.desig)     AS DESIG, "
			+ "        p.site_name, "
			+ "        (SELECT lkp_disc FROM   lookup WHERE  lkp_code = 'LFT_PURP' AND lkp_srno = ri.LEFT_REASON) AS LEFT_REASON ,e.FILENUMBER,ri.LEFT_DATE,e.retirementdate  "
			+ "FROM   empmast e, "
			+ "       emptran e1, "
			+ "       project_sites p ,relieveinfo ri "
			+ "WHERE  e.empno = e1.empno "
			+ "       AND e1.srno = (SELECT Max(srno) "
			+ "                      FROM   emptran "
			+ "                      WHERE  empno = e.empno) "
			+ "       AND p.site_id = e1.prj_srno "
			+ "      AND ri.LEFT_DATE BETWEEN '"+frmdate+"' and  '"+todate+"' "
			+ "      AND ri.EMPNO = e.EMPNO  "+constant+"  order by e1.desig";
		}
		else if(type.equals("9")){
		 SQL = ""
						+ "SELECT e.empno, "
						+ "       e.empcode, "
						+ "       Rtrim(e.fname) + ' ' + Rtrim(e.mname) + ' ' "
						+ "       + Rtrim(e.lname)                     AS NAME, "
						+ "       e.status, "
						+ "	   e.retirementdate, "
						+ "	   (SELECT lkp_disc "
						+ "        FROM   lookup "
						+ "        WHERE  lkp_code = 'CASTE' "
						+ "               AND lkp_srno = e.castcd)     AS caste, "
						+ "       (SELECT lkp_disc "
						+ "        FROM   lookup "
						+ "        WHERE  lkp_code = 'CATE' "
						+ "               AND lkp_srno = e.categorycd) AS CATE, "
						+ "       (SELECT lkp_disc "
						+ "        FROM   lookup "
						+ "        WHERE  lkp_code = 'DESIG' "
						+ "               AND lkp_srno = e1.desig)     AS DESIG, "
						+ "        p.site_name, "
						+ "        (SELECT lkp_disc FROM   lookup WHERE  lkp_code = 'LFT_PURP' AND lkp_srno = ri.LEFT_REASON) AS LEFT_REASON,e.FILENUMBER,ri.LEFT_DATE,e.retirementdate  "
						+ "FROM   empmast e, "
						+ "       emptran e1, "
						+ "       project_sites p ,relieveinfo ri "
						+ "WHERE  e.empno = e1.empno "
						+ "       AND e1.srno = (SELECT Max(srno) "
						+ "                      FROM   emptran "
						+ "                      WHERE  empno = e.empno) "
						+ "       AND p.site_id = e1.prj_srno "
						+ "      AND ri.LEFT_DATE BETWEEN '"+frmdate+"' and  '"+todate+"' "
						+ "      AND ri.EMPNO = e.EMPNO  "+constant+"  order by e1.desig";
		              }
		else{
			 SQL = ""
					 + "SELECT e.empno, "
						+ "       e.empcode, "
						+ "       Rtrim(e.fname) + ' ' + Rtrim(e.mname) + ' ' "
						+ "       + Rtrim(e.lname)                     AS NAME, "
						+ "       e.status, "
						+ "	   e.retirementdate, "
						+ "	   (SELECT lkp_disc "
						+ "        FROM   lookup "
						+ "        WHERE  lkp_code = 'CASTE' "
						+ "               AND lkp_srno = e.castcd)     AS caste, "
						+ "       (SELECT lkp_disc "
						+ "        FROM   lookup "
						+ "        WHERE  lkp_code = 'CATE' "
						+ "               AND lkp_srno = e.categorycd) AS CATE, "
						+ "       (SELECT lkp_disc "
						+ "        FROM   lookup "
						+ "        WHERE  lkp_code = 'DESIG' "
						+ "               AND lkp_srno = e1.desig)     AS DESIG, "
						+ "        p.site_name, "
						+ "        (SELECT lkp_disc FROM   lookup WHERE  lkp_code = 'LFT_PURP' AND lkp_srno = ri.LEFT_REASON) AS LEFT_REASON,e.FILENUMBER,ri.LEFT_DATE,e.retirementdate  "
						+ "FROM   empmast e, "
						+ "       emptran e1, "
						+ "       project_sites p ,relieveinfo ri "
						+ "WHERE  e.empno = e1.empno "
						+ "       AND e1.srno = (SELECT Max(srno) "
						+ "                      FROM   emptran "
						+ "                      WHERE  empno = e.empno) "
						+ "       AND p.site_id = e1.prj_srno "
						+ "      AND ri.EMPNO = e.EMPNO"
						+ "      AND ri.LEFT_DATE BETWEEN '"+frmdate+"' and  '"+todate+"' "
			 			+ "  AND ri.LEFT_REASON= "+type+" "+constant+"  order by e1.desig";
		}	
		ResultSet rs=st.executeQuery(SQL);
		System.out.println("SQL:: "+SQL);
		int cnt =0;
		
		while(rs.next())
		{
			cnt++;
			
			HSSFRow row = sheet.createRow((short)i++);
			row.createCell((short) 0).setCellValue(cnt);
			row.createCell((short) 1).setCellValue(rs.getString("EMPCODE"));
			row.createCell((short) 2).setCellValue(rs.getString("NAME"));
			row.createCell((short) 3).setCellValue((rs.getString("SITE_NAME")));
			row.createCell((short) 4).setCellValue((rs.getString("DESIG")));
			row.createCell((short) 5).setCellValue(rs.getString("retirementdate"));
			row.createCell((short) 6).setCellValue(rs.getString("LEFT_DATE"));
			row.createCell((short) 7).setCellValue(rs.getString("FILENUMBER"));
			row.createCell((short) 8).setCellValue(rs.getString("caste"));
            row.createCell((short) 9).setCellValue(rs.getString("cate"));
			row.createCell((short) 10).setCellValue(rs.getString("LEFT_REASON"));
			
			System.out.println("0000....."+cnt);
		}
		HSSFRow	head1=   sheet.createRow((short)i++);
		HSSFCell cell4;
		head1=   sheet.createRow((short)i++);
	 	cell4 = head1 .createCell((short) 0); 
		cell4.setCellValue("");
		cell4 = head1 .createCell((short) 1); 
		cell4.setCellValue("");
		cell4 = head1 .createCell((short) 2); 
		cell4.setCellValue("TOTAL EMPLOYEE(S) :    " +cnt);
		cell4.setCellStyle(my_style);
		
					hwb.write(out1);
					out1.close();
					st.close();
					con.close();
	    
					System.out.println("Result OK.....");
		
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
		}
		
		@SuppressWarnings("null")
		public static void leaveEncashReport(String frmdate,String filepath,String prjCode)
		{
		int i = 12;
		Properties prop = new Properties();
		try
	     {
			System.out.println("frmdate:  "+frmdate);
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream stream = classLoader.getResourceAsStream("constant.properties");
			prop.load(stream);
	     }
	     catch (Exception e) {System.out.println("Error in constant properties Manager "+e);}
	     
		try
		{
			RepoartBean repBean  = new RepoartBean();
			
			LookupHandler lkh=new LookupHandler();
	//		Connection con =null;
			System.out.println( filepath);
		FileOutputStream out1 = new FileOutputStream(new File(filepath));
		HSSFWorkbook hwb=new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("LeftEmployeeReport");
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
		
		sheet.setColumnWidth((short)0, (short)2000);
		sheet.setColumnWidth((short)1, (short)3000);
		sheet.setColumnWidth((short)2, (short)13000);
		sheet.setColumnWidth((short)3, (short)10000);
		sheet.setColumnWidth((short)4, (short)6000);
		sheet.setColumnWidth((short)5, (short)5000);
		sheet.setColumnWidth((short)6, (short)5000);
		sheet.setColumnWidth((short)7, (short)6000);
		sheet.setColumnWidth((short)8, (short)6000);
		sheet.setColumnWidth((short)9, (short)7000);
	
      HSSFCellStyle my_style = hwb.createCellStyle();
      HSSFCellStyle my_style1 = hwb.createCellStyle();

      HSSFFont my_font=hwb.createFont();
      my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
      my_style.setFont(my_font);
    
      
		HSSFRow rowtitle=   sheet.createRow((short)0);
		HSSFCell cell = rowtitle.createCell((short) 3);
		
		cell.setCellValue(prop.getProperty("bankName1"));
		cell.setCellStyle(my_style);
		HSSFRow rowtitle1=   sheet.createRow((short)1);
		HSSFCell cell1 = rowtitle1.createCell((short) 3);
		cell1.setCellValue(prop.getProperty("addressForReport"));
		cell1.setCellStyle(my_style);
		HSSFRow rowtitle2=   sheet.createRow((short)2);
		HSSFCell cell2 = rowtitle2.createCell((short) 3);
		cell2.setCellValue(prop.getProperty("contactForReport"));
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle31=   sheet.createRow((short)3);
		cell2 = rowtitle31.createCell((short) 3);
		cell2.setCellValue(	prop.getProperty("mailForReport"));
		cell2.setCellStyle(my_style);
		 long millis=System.currentTimeMillis();  
		 java.sql.Date date=new java.sql.Date(millis);  
		 DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		 String reportDate = df.format(date);
		 HSSFRow rowtitle32=   sheet.createRow((short)4);
		 cell2 = rowtitle32.createCell((short) 3);
		 cell2.setCellValue("Report Date:  "+reportDate);
		 cell2.setCellStyle(my_style);
		
		 HSSFRow rowtitle311=   sheet.createRow((short)3);
		 cell2 = rowtitle311.createCell((short) 3);
		 cell2.setCellValue("");
		 cell2.setCellStyle(my_style);
		 
		HSSFRow rowtitle3=   sheet.createRow((short)4);
		cell2=rowtitle3.createCell((short) 3);
		cell2.setCellValue("Leave Encashment Employee List:-  "+reportDate);
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle4=   sheet.createRow((short)5);
		rowtitle4.createCell((short) 0).setCellValue("");
		HSSFRow rowtitle5=   sheet.createRow((short)6);
		rowtitle5.createCell((short) 0).setCellValue("");
		
		
		HSSFFont blueFont = hwb.createFont();
		blueFont.setColor(HSSFColor.BLUE.index);
		
		HSSFCellStyle style = hwb.createCellStyle();
		//style.setFont(blueFont);
		style.setFillForegroundColor(HSSFColor.BLUE.index);
		
		
		HSSFRow head=   sheet.createRow((short)7);
		head.createCell((short) 0).setCellValue("");
		HSSFRow heading=   sheet.createRow((short)8);
		HSSFCell cell3 = heading.createCell((short) 0); 

		cell3.setCellValue("");
		cell3.setCellStyle(my_style1);
		HSSFRow rowhead=   sheet.createRow((short)i++);
        sheet.createFreezePane( 0, 10, 0, 10 );
     
      my_style1.setAlignment((short) 2);
      my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
      my_style1.setFont(my_font);
      
      Connection con =null;
      ReportDAO.OpenCon("", "", "",repBean);
	  con = repBean.getCn();
      int count=1,srno = 0;
		ResultSet rs = null;
		ResultSet rs1 = null;
		Statement st = null;
		Statement st1 = null;
		/*st = con.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		st1 = con.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);*/
		st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      String Query1="";
      if(prjCode.equalsIgnoreCase("1000"))
		{
			Query1 = "select * from Project_Sites order by SITE_ID";
		}
		else {
			Query1 = "select * from Project_Sites where SITE_ID =" +prjCode ;
		}
      
 		System.out.println("Query is@@@ " +Query1);
		
		rs1 = st.executeQuery(Query1);
		int cnt=0;
		String site_id="";
		String site_name="";
		String Project_Code="";
		while(rs1.next())
		{
			site_id = rs1.getString("Site_ID");
			site_name = rs1.getString("site_name");
			Project_Code = rs1.getString("Project_Code");
		
		
		ReportDAO.OpenCon("", "", "",repBean);
		con = repBean.getCn();	
		Statement st2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
		ResultSet.CONCUR_READ_ONLY);
		String SQL = "";
		String[] parts = frmdate.split("-");
		String day = parts[0]; // 004
		String month = parts[1];
		String year = parts[2];
		int curYear=Integer.parseInt(year);
		int previousYear=Integer.parseInt(year);
		previousYear = previousYear-1;
		
	if(prjCode.equalsIgnoreCase("1000"))
	{
			 SQL = ""
					 + "WITH a "
					 + "     AS (SELECT E.empno, t.PRJ_SRNO,"
					 + "                E.empcode, "
					 + "                CONVERT(NVARCHAR(100), E.lname + ' ' + E.fname + ' ' + E.mname) "
					 + "                AS "
					 + "                NAME, "
					 + "                (SELECT  CASE "
					 + "                                WHEN ( ( ( l.bal + 30 ) - 90 ) - (SELECT "
					 + "                                       Isnull(CASE "
					 + "                                                WHEN "
					 + "                                       Sum(days) >= 25 THEN "
					 + "                                                                 Round( "
					 + "                                                ( Sum(days) / 12 ), 0) "
					 + "                                                                 ELSE 0 "
					 + "                                              END, "
					 + "                                       0) "
					 + "                                       AS "
					 + "                                       usedleave "
					 + "                                                                  FROM "
					 + "                                       leavetran "
					 + "                                                                  WHERE "
					 + "                                       empno = E.empno "
					 + "                                       AND trndate BETWEEN "
					 + "                                       '"+(year)+"-01-01' AND "
					 + "                                       '"+(year)+"-12-31' "
					 + "                                       AND leavecd = 7 "
					 + "                                       AND status = 'SANCTION' "
					 + "                                       AND trntype = 'D') ) < "
					 + "                                     0 THEN 0 "
					 + "                                ELSE (( ( ( l.bal + 30 ) - 90 ) - (SELECT "
					 + "                                        Isnull(CASE "
					 + "                                                                  WHEN "
					 + "                                               Sum(days) >= 25 "
					 + "                                               THEN "
					 + "                                                                  Round( "
					 + "                                               ( Sum(days) / 12 ), 0 "
					 + "                                                                  ) "
					 + "                                                                  ELSE 0 "
					 + "                                               END, "
					 + "                                        0) "
					 + "                                        AS "
					 + "                                        usedleave "
					 + "                                                                   FROM "
					 + "                                        leavetran "
					 + "                                                                   WHERE "
					 + "                                        empno = E.empno "
					 + "                                        AND trndate BETWEEN "
					 + "                                            '"+(year)+"-01-01' AND "
					 + "                                            '"+(year)+"-12-31' "
					 + "                                        AND leavecd = 7 "
					 + "                                        AND status = 'SANCTION' "
					 + "                                        AND trntype = 'D') )) "
					 + "                              END AS Encash_Days "
					 + "                 FROM   leavebal l "
					 + "                 WHERE  l.empno = E.empno "
					 + "                        AND l.leavecd = 1 "
					 + "                        AND baldt=(select top 1 BALDT from leavebal where empno=E.EMPNO and LEAVECD=1 and BALDT<'"+(year)+"-01-01' order by BALDT desc,srno desc) "
					 + "                        AND srno=(select top 1 srno from leavebal where empno=E.EMPNO and LEAVECD=1 and BALDT<'"+(year)+"-01-01' order by BALDT desc,srno desc) "
					 + "						 ) "
					 + "                AS "
					 + "                   Encash_Days, "
					 + "                (SELECT inp_amt "
					 + "                 FROM   paytran_stage "
					 + "                 WHERE  empno = E.empno "
					 + "                        AND trncd = 101 "
					 + "                        AND trndt = '"+(previousYear)+"-03-31') "
					 + "                AS "
					 + "                BASIC "
					 + "                   , "
					 + "                (SELECT inp_amt "
					 + "                 FROM   paytran_stage "
					 + "                 WHERE  empno = E.empno "
					 + "                        AND trncd = 102 "
					 + "                        AND trndt = '"+(previousYear)+"-03-31') "
					 + "                AS DA "
					 + "                , "
					 + "                (SELECT inp_amt "
					 + "                 FROM   paytran_stage "
					 + "                 WHERE  empno = E.empno "
					 + "                        AND trncd = 138 "
					 + "                        AND trndt = '"+(previousYear)+"-03-31') "
					 + "                AS "
					 + "                VDA "
					 + "         FROM   empmast E, "
					 + "                emptran t "
					 + "         WHERE  E.empno = t.empno "
					 + "                AND t.srno = (SELECT Max(srno) "
					 + "                              FROM   emptran T1 "
					 + "                              WHERE  T1.empno = t.empno) "
					 + "                AND ( ( E.status = 'A' "
					 + "                        AND E.doj <= '"+(year)+"-12-31' ) "
					 + "                       OR ( E.status = 'N' "
					 + "                            AND E.dol >= '"+(year)+"-01-01' ) ) ) "
					 + "SELECT "
					 + "*, "
					 + "Round(CONVERT(NUMERIC(12, 2), ( "
					 + "      ( ( a.basic + a.da + a.vda ) / 30 ) * a.encash_days )), 2) "
					 + "AS TOTAL_AMT, "
					 + "Round(CONVERT(NUMERIC(12, 2), ( "
					 + "( ( (( "
					 + "      ( ( a.basic + a.da + a.vda ) / 30 ) * a.encash_days )) / 100 ) * 12 ))), 2)                    AS PF_AMT, "
					 + "Round(CONVERT(NUMERIC(12, 2), ( "
					 + "            ( ( ( a.basic + a.da + a.vda ) / 30 ) * a.encash_days ) - (( "
					 + "            ( "
					 + "( ( "
					 + "            ( "
					 + "                            a.basic + a.da + a.vda ) / 30 ) * a.encash_days ) / 100 ) * 12 )) )), 2) AS NET_PAY, "
					 +" Round(CONVERT(NUMERIC(12, 2), ( ( ( a.basic) / 30 ) *  a.encash_days )), 2) AS Basic_f,"
					 +" Round(CONVERT(NUMERIC(12, 2), ( ( ( a.da) / 30 ) *  a.encash_days )), 2) AS Da_f ,"
					 +" Round(CONVERT(NUMERIC(12, 2), ( ( ( a.vda) / 30 ) *  a.encash_days )), 2) AS Vda_f  "
					 + "FROM   a "
					 + "ORDER  BY a.empcode";
					 
	}		 
	else{
		SQL = ""
				+ "WITH a "
				 + "     AS (SELECT E.empno, t.PRJ_SRNO,"
				 + "                E.empcode, "
				 + "                CONVERT(NVARCHAR(100), E.lname + ' ' + E.fname + ' ' + E.mname) "
				 + "                AS "
				 + "                NAME, "
				 + "                (SELECT  CASE "
				 + "                                WHEN ( ( ( l.bal + 30 ) - 90 ) - (SELECT "
				 + "                                       Isnull(CASE "
				 + "                                                WHEN "
				 + "                                       Sum(days) >= 25 THEN "
				 + "                                                                 Round( "
				 + "                                                ( Sum(days) / 12 ), 0) "
				 + "                                                                 ELSE 0 "
				 + "                                              END, "
				 + "                                       0) "
				 + "                                       AS "
				 + "                                       usedleave "
				 + "                                                                  FROM "
				 + "                                       leavetran "
				 + "                                                                  WHERE "
				 + "                                       empno = E.empno "
				 + "                                       AND trndate BETWEEN "
				 + "                                       '"+(year)+"-01-01' AND "
				 + "                                       '"+(year)+"-12-31' "
				 + "                                       AND leavecd = 7 "
				 + "                                       AND status = 'SANCTION' "
				 + "                                       AND trntype = 'D') ) < "
				 + "                                     0 THEN 0 "
				 + "                                ELSE (( ( ( l.bal + 30 ) - 90 ) - (SELECT "
				 + "                                        Isnull(CASE "
				 + "                                                                  WHEN "
				 + "                                               Sum(days) >= 25 "
				 + "                                               THEN "
				 + "                                                                  Round( "
				 + "                                               ( Sum(days) / 12 ), 0 "
				 + "                                                                  ) "
				 + "                                                                  ELSE 0 "
				 + "                                               END, "
				 + "                                        0) "
				 + "                                        AS "
				 + "                                        usedleave "
				 + "                                                                   FROM "
				 + "                                        leavetran "
				 + "                                                                   WHERE "
				 + "                                        empno = E.empno "
				 + "                                        AND trndate BETWEEN "
				 + "                                            '"+(year)+"-01-01' AND "
				 + "                                            '"+(year)+"-12-31' "
				 + "                                        AND leavecd = 7 "
				 + "                                        AND status = 'SANCTION' "
				 + "                                        AND trntype = 'D') )) "
				 + "                              END AS Encash_Days "
				 + "                 FROM   leavebal l "
				 + "                 WHERE  l.empno = E.empno "
				 + "                        AND l.leavecd = 1 "
				 + "                        AND baldt=(select top 1 BALDT from leavebal where empno=E.EMPNO and LEAVECD=1 and BALDT<'"+(year)+"-01-01' order by BALDT desc,srno desc) "
				 + "                        AND srno=(select top 1 srno from leavebal where empno=E.EMPNO and LEAVECD=1 and BALDT<'"+(year)+"-01-01' order by BALDT desc,srno desc) "
				 + "						 ) "
				 + "                AS "
				 + "                   Encash_Days, "
				 + "                (SELECT inp_amt "
				 + "                 FROM   paytran_stage "
				 + "                 WHERE  empno = E.empno "
				 + "                        AND trncd = 101 "
				 + "                        AND trndt = '"+(previousYear)+"-03-31') "
				 + "                AS "
				 + "                BASIC "
				 + "                   , "
				 + "                (SELECT inp_amt "
				 + "                 FROM   paytran_stage "
				 + "                 WHERE  empno = E.empno "
				 + "                        AND trncd = 102 "
				 + "                        AND trndt = '"+(previousYear)+"-03-31') "
				 + "                AS DA "
				 + "                , "
				 + "                (SELECT inp_amt "
				 + "                 FROM   paytran_stage "
				 + "                 WHERE  empno = E.empno "
				 + "                        AND trncd = 138 "
				 + "                        AND trndt = '"+(previousYear)+"-03-31') "
				 + "                AS "
				 + "                VDA "
				 + "         FROM   empmast E, "
				 + "                emptran t "
				 + "         WHERE  E.empno = t.empno "
				 + "                AND t.srno = (SELECT Max(srno) "
				 + "                              FROM   emptran T1 "
				 + "                              WHERE  T1.empno = t.empno) "
				 + "                AND ( ( E.status = 'A' "
				 + "                        AND E.doj <= '"+(year)+"-12-31' ) "
				 + "                       OR ( E.status = 'N' "
				 + "                            AND E.dol >= '"+(year)+"-01-01' ) )  and t.PRJ_SRNO="+prjCode+") "
				 + "SELECT "
				 + "*, "
				 + "Round(CONVERT(NUMERIC(12, 2), ( "
				 + "      ( ( a.basic + a.da + a.vda ) / 30 ) * a.encash_days )), 2) "
				 + "AS TOTAL_AMT, "
				 + "Round(CONVERT(NUMERIC(12, 2), ( "
				 + "( ( (( "
				 + "      ( ( a.basic + a.da + a.vda ) / 30 ) * a.encash_days )) / 100 ) * 12 ))), 2)                    AS PF_AMT, "
				 + "Round(CONVERT(NUMERIC(12, 2), ( "
				 + "            ( ( ( a.basic + a.da + a.vda ) / 30 ) * a.encash_days ) - (( "
				 + "            ( "
				 + "( ( "
				 + "            ( "
				 + "                            a.basic + a.da + a.vda ) / 30 ) * a.encash_days ) / 100 ) * 12 )) )), 2) AS NET_PAY ,"
				 +" Round(CONVERT(NUMERIC(12, 2), ( ( ( a.basic) / 30 ) *  a.encash_days )), 2) AS Basic_f,"
				 +" Round(CONVERT(NUMERIC(12, 2), ( ( ( a.da) / 30 ) *  a.encash_days )), 2) AS Da_f ,"
				 +" Round(CONVERT(NUMERIC(12, 2), ( ( ( a.vda) / 30 ) *  a.encash_days )), 2) AS Vda_f  "	
				 + "FROM   a "
				 + "ORDER  BY a.empcode";
				
				
	}
			
		ResultSet rs2=st1.executeQuery(SQL);
		System.out.println("SQL:: "+SQL);
		int totalEmployee=0;
	  if(rs1.next()){
	//	  HSSFRow head1=   sheet.createRow((short)i++);
		
		
		int cnt1 =0;
		double basic=0,da=0,vda=0,days=0;
		double pf=0;
		double totamount=0;
		double netamount=0;
		int prj_srno=0;
		
		rs2.beforeFirst();
		while(rs2.next())
		{
			prj_srno = rs2.getInt("PRJ_SRNO");
			
			if(site_id.equals(prj_srno))
			{
				HSSFRow rowtitle333=   sheet.createRow((short)i++);
				cell2=rowhead.createCell((short) 3);
				cell2.setCellValue("Leave Encashment Employee List:-  "+(site_name + Project_Code));
				cell2.setCellStyle(my_style);
				
				HSSFRow rowtitle33=   sheet.createRow((short)i++);
				cell3=rowhead.createCell((short) 0);
				cell3.setCellValue("SR NO");
				cell3.setCellStyle(my_style1);
				cell3=rowhead.createCell((short) 1);
				cell3.setCellValue("EMPCODE");
				cell3.setCellStyle(my_style1);
				cell3=rowhead.createCell((short) 2);
				cell3.setCellValue("EMP NAME");
				cell3.setCellStyle(my_style1);
				cell3=rowhead.createCell((short) 3);
				cell3.setCellValue("BRANCH");
				cell3.setCellStyle(my_style1);
				cell3=rowhead.createCell((short) 4);
				cell3.setCellValue("DESIG");
				cell3.setCellStyle(my_style1);
				cell3=rowhead.createCell((short) 5);
				cell3.setCellValue("Basic");
				cell3.setCellStyle(my_style1);
				cell3=rowhead.createCell((short) 6);
				cell3.setCellValue("DA");
				cell3.setCellStyle(my_style1);
				cell3=rowhead.createCell((short)7);
				cell3.setCellValue("VDA ");
				cell3.setCellStyle(my_style1);
				cell3=rowhead.createCell((short) 8);
				cell3.setCellValue("Day's");
				cell3.setCellStyle(my_style1);
				cell3=rowhead.createCell((short) 9);
				cell3.setCellValue("Pf");
				cell3.setCellStyle(my_style1);
				cell3=rowhead.createCell((short) 10);
				cell3.setCellValue("Net pay");
				cell3.setCellStyle(my_style1);
			}
			cnt1++;
			
			HSSFRow row = sheet.createRow((short)i++);
			row.createCell((short) 0).setCellValue(cnt1);
			row.createCell((short) 1).setCellValue(rs2.getString("EMPCODE"));
			row.createCell((short) 2).setCellValue(rs2.getString("NAME"));
			row.createCell((short) 3).setCellValue("");
			row.createCell((short) 4).setCellValue("");
			row.createCell((short) 5).setCellValue(rs2.getString("Basic_f"));
			row.createCell((short) 6).setCellValue(rs2.getString("Da_f"));
			row.createCell((short) 7).setCellValue(rs2.getString("Vda_f"));
			row.createCell((short) 8).setCellValue(rs2.getString("Encash_Days"));
			/*basic=Double.parseDouble(rs2.getString("BASIC"));
			da=Double.parseDouble(rs2.getString("DA"));
			vda=Double.parseDouble(rs2.getString("VDA"));
			days=Double.parseDouble(rs2.getString("days"));
			pf=Math.round((((((( basic + da + vda ) / 30 ) * days )) / 100 ) * 12 ));
			totamount =Math.round(((( basic + da + vda ) / 30 ) * days));
			netamount =Math.round(totamount-pf);*/
            row.createCell((short) 9).setCellValue(rs2.getString("PF_AMT"));
			row.createCell((short) 10).setCellValue(rs2.getString("NET_PAY"));
			
			System.out.println("0000....."+cnt);
		}
		HSSFRow	head1=   sheet.createRow((short)i++);
		HSSFCell cell4;
		head1=   sheet.createRow((short)i++);
	 	cell4 = head1 .createCell((short) 0); 
		cell4.setCellValue("");
		cell4 = head1 .createCell((short) 1); 
		cell4.setCellValue("");
		cell4 = head1 .createCell((short) 2); 
		cell4.setCellValue("TOTAL EMPLOYEE :    " +cnt1);
		cell4.setCellStyle(my_style);
		
		totalEmployee = totalEmployee + cnt1;	
		cnt1 = 0;
		
	  }
		HSSFRow	head1=   sheet.createRow((short)i++);
		HSSFCell cell4;
		head1=   sheet.createRow((short)i++);
	 	cell4 = head1 .createCell((short) 0); 
		cell4.setCellValue("");
		cell4 = head1 .createCell((short) 1); 
		cell4.setCellValue("");
		cell4 = head1 .createCell((short) 2); 
		cell4.setCellValue("TOTAL EMPLOYEE(S) :    " +totalEmployee);
		cell4.setCellStyle(my_style);
		
	}
					hwb.write(out1);
					out1.close();
					st.close();
					con.close();
	    
					System.out.println("Result OK.....");
		
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
		}
		
		
		public static void encahlistbranchwise(String date,String[] results,String BranchType,String SelectedBranch,String desigtype,String RangeFrom,String RangeTo,String SiteName,String filepath,String imgpath)
		{

		System.out.println(""+results.length);
		int i = 0;
		Properties prop = new Properties();
		int brtot[] = new int[results.length];
		 int ALLbrtot[] = new int[results.length];
		 int PBRCD = 0;
		 int gross = 0;
		 int rownum=10;
		 System.out.println("BranchType: "+BranchType);
		 System.out.println("SelectedBranch: "+SelectedBranch);
	     try
	     {
	    	 ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream stream = classLoader.getResourceAsStream("constant.properties");
			prop.load(stream);
	     }
	     catch (Exception e) {System.out.println("Error in constant properties Manager "+e);}
	     
		try
		{
			RepoartBean repBean  = new RepoartBean();
			
			String SQL = "";
			String[] parts = date.split("-");
			String day = parts[0]; // 004
			String month = parts[1];
			String year = parts[2];
			int curYear=Integer.parseInt(year);
			int previousYear=Integer.parseInt(year);
			previousYear = previousYear-1;
			int nextYear = curYear+1;
			 System.out.println("day: "+day+"  month: "+month+"  year:  "+year+"  curYear: "+curYear+" previousYear: "+previousYear);
			 System.out.println("SiteName AJ : "+SiteName);
			LookupHandler lkh=new LookupHandler();
			System.out.println( filepath);
		FileOutputStream out1 = new FileOutputStream(new File(filepath));
		HSSFWorkbook hwb=new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("EmpNewlist");
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
		System.out.println("1111.....");
		
		
	   HSSFCellStyle my_style = hwb.createCellStyle();
	   HSSFCellStyle my_style1 = hwb.createCellStyle();

	   HSSFFont my_font=hwb.createFont();
	   my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	   my_style.setFont(my_font);
	 
	   
		HSSFRow rowtitle=   sheet.createRow((short)0);
		HSSFCell cell = rowtitle.createCell((short) 1);
		cell.setCellValue(prop.getProperty("bankName1"));
		cell.setCellStyle(my_style);
		HSSFRow rowtitle1=   sheet.createRow((short)1);
		HSSFCell cell1 = rowtitle1.createCell((short) 1);
		cell1.setCellValue(prop.getProperty("addressForReport"));
		cell1.setCellStyle(my_style);
		HSSFRow rowtitle2=   sheet.createRow((short)2);
		HSSFCell cell2 = rowtitle2.createCell((short) 1);
		cell2.setCellValue(prop.getProperty("contactForReport"));
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle31=   sheet.createRow((short)3);
		cell2 = rowtitle31.createCell((short) 1);
		cell2.setCellValue(	prop.getProperty("mailForReport"));
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle3=   sheet.createRow((short)4);
		cell2=rowtitle3.createCell((short) 1);
		cell2.setCellValue("");
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle4=   sheet.createRow((short)5);
		rowtitle4.createCell((short) 0).setCellValue("");
		
		long millis=System.currentTimeMillis();  
		 java.sql.Date date1=new java.sql.Date(millis);  
		 DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		 String reportDate = df.format(date1);
		
		HSSFRow rowtitle5=   sheet.createRow((short)6);
	//	rowtitle5.createCell((short) 1).setCellValue("Branchwise Encashment List:-  ");
		HSSFCell cell22 = rowtitle5.createCell((short) 1);
		cell22 = rowtitle5.createCell((short) 1);
		cell22.setCellValue(("Branchwise Encashment List:- "+reportDate));
		cell22.setCellStyle(my_style);
		
		
		HSSFFont blueFont = hwb.createFont();
		blueFont.setColor(HSSFColor.BLUE.index);
		
		HSSFCellStyle style = hwb.createCellStyle();
		//style.setFont(blueFont);
		style.setFillForegroundColor(HSSFColor.BLUE.index);
		
		
		/*HSSFRow head=   sheet.createRow((short)7);
		head.createCell((short) 0).setCellValue("");
		HSSFRow heading=   sheet.createRow((short)8);
		HSSFCell cell3 = heading.createCell((short) 0); 

		cell3.setCellValue("");
		cell3.setCellStyle(my_style1);
		HSSFRow rowhead=   sheet.createRow((short)9);*/
	    sheet.createFreezePane( 0, 10, 0, 10 );
	  
	   my_style1.setAlignment((short) 2);
	   my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	   my_style1.setFont(my_font);
	   
	    Connection con =null;
	    ReportDAO.OpenCon("", "", "",repBean);
	    con = repBean.getCn();	
	//	Statement st=con.createStatement();
	//	Statement st1=con.createStatement();
		int count=1,srno = 0;
		ResultSet rs = null;
		ResultSet rs1 = null;
		Statement st = null;
		Statement st1 = null;
		/*st = con.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		st1 = con.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);*/
		
		String Query="";
		String Query1="";
		int totalEmployee =0;
		int Employee = 0;
		String site_id ="";
		String site_name ="";
		String Project_Code ="";
		float totbasic = 0.0f;
		float totda = 0.0f;
		float totvda = 0.0f;
		float totpf = 0.0f;
		float totnetpay = 0.0f;
		float totamt = 0.0f;
		float basic = 0.0f;
		float da = 0.0f;
		float vda = 0.0f;
		float pf = 0.0f;
		float amt = 0.0f;
		float netpay = 0.0f;
		float ftotbasic = 0.0f;
		float ftotda = 0.0f;
		float ftotvda = 0.0f;
		float ftotpf = 0.0f;
		float ftotnetpay = 0.0f;
		float ftotamt = 0.0f;
		float days=0;
		float tdays=0;
		
		if(BranchType.equalsIgnoreCase("1"))
		{
			Query1 = "select * from Project_Sites order by SITE_ID";
			System.out.println("in all Project_Sites ");
		}
		else {
			Query1 = "select * from Project_Sites where SITE_ID =" +SelectedBranch ;
			System.out.println("in selected Project_Sites ");
		}
		
		System.out.println("Query is@@@ " +Query1);
		st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	//	System.out.println("query is: "+Query);
		rs1 = st1.executeQuery(Query1);
		int cnt=0;
	/*	while(rs1.next())
		{
			site_id = rs1.getString("Site_ID");
			site_name = rs1.getString("site_name");
			Project_Code = rs1.getString("Project_Code");*/
		if(BranchType.equalsIgnoreCase("1"))
		{System.out.println("in all Project_Sites query");
			SQL = ""
					 + "WITH a "
					 + "     AS (SELECT E.empno, t.PRJ_SRNO,"
					 + "                E.empcode, "
					 + "                CONVERT(NVARCHAR(100), E.lname + ' ' + E.fname + ' ' + E.mname) "
					 + "                AS "
					 + "                NAME, "
					 + "                (SELECT  CASE "
					 + "                                WHEN ( ( ( l.bal + 30 ) - 90 ) - (SELECT "
					 + "                                       Isnull(CASE "
					 + "                                                WHEN "
					 + "                                       Sum(days) >= 25 THEN "
					 + "                                                                 Round( "
					 + "                                                ( Sum(days) / 12 ), 0) "
					 + "                                                                 ELSE 0 "
					 + "                                              END, "
					 + "                                       0) "
					 + "                                       AS "
					 + "                                       usedleave "
					 + "                                                                  FROM "
					 + "                                       leavetran "
					 + "                                                                  WHERE "
					 + "                                       empno = E.empno "
					 + "                                       AND trndate BETWEEN "
					 + "                                       '"+(year)+"-01-01' AND "
					 + "                                       '"+(year)+"-12-31' "
					 + "                                       AND leavecd = 7 "
					 + "                                       AND status = 'SANCTION' "
					 + "                                       AND trntype = 'D') ) < "
					 + "                                     0 THEN 0 "
					 + "                                ELSE (( ( ( l.bal + 30 ) - 90 ) - (SELECT "
					 + "                                        Isnull(CASE "
					 + "                                                                  WHEN "
					 + "                                               Sum(days) >= 25 "
					 + "                                               THEN "
					 + "                                                                  Round( "
					 + "                                               ( Sum(days) / 12 ), 0 "
					 + "                                                                  ) "
					 + "                                                                  ELSE 0 "
					 + "                                               END, "
					 + "                                        0) "
					 + "                                        AS "
					 + "                                        usedleave "
					 + "                                                                   FROM "
					 + "                                        leavetran "
					 + "                                                                   WHERE "
					 + "                                        empno = E.empno "
					 + "                                        AND trndate BETWEEN "
					 + "                                            '"+(year)+"-01-01' AND "
					 + "                                            '"+(year)+"-12-31' "
					 + "                                        AND leavecd = 7 "
					 + "                                        AND status = 'SANCTION' "
					 + "                                        AND trntype = 'D') )) "
					 + "                              END AS Encash_Days "
					 + "                 FROM   leavebal l "
					 + "                 WHERE  l.empno = E.empno "
					 + "                        AND l.leavecd = 1 "
					 + "                        AND baldt=(select top 1 BALDT from leavebal where empno=E.EMPNO and LEAVECD=1 and BALDT<'"+(nextYear)+"-01-01' order by BALDT desc,srno desc) "
					 + "                        AND srno=(select top 1 srno from leavebal where empno=E.EMPNO and LEAVECD=1 and BALDT<'"+(nextYear)+"-01-01' order by BALDT desc,srno desc) "
					 + "						 ) "
					 + "                AS "
					 + "                   Encash_Days, "
					 + "                (SELECT inp_amt "
					 + "                 FROM   paytran_stage "
					 + "                 WHERE  empno = E.empno "
					 + "                        AND trncd = 101 "
					 + "                        AND trndt = '"+(year)+"-03-31') "
					 + "                AS "
					 + "                BASIC "
					 + "                   , "
					 + "                (SELECT inp_amt "
					 + "                 FROM   paytran_stage "
					 + "                 WHERE  empno = E.empno "
					 + "                        AND trncd = 102 "
					 + "                        AND trndt = '"+(year)+"-03-31') "
					 + "                AS DA "
					 + "                , "
					 + "                (SELECT inp_amt "
					 + "                 FROM   paytran_stage "
					 + "                 WHERE  empno = E.empno "
					 + "                        AND trncd = 138 "
					 + "                        AND trndt = '"+(year)+"-03-31') "
					 + "                AS "
					 + "                VDA "
					 + "         FROM   empmast E, "
					 + "                emptran t "
					 + "         WHERE  E.empno = t.empno "
					 + "                AND t.srno = (SELECT Max(srno) "
					 + "                              FROM   emptran T1 "
					 + "                              WHERE  T1.empno = t.empno) "
					 + "                AND ( ( E.status = 'A' "
					 + "                        AND E.doj <= '"+(year)+"-12-31' ) "
					 + "                       OR ( E.status = 'N' "
					 + "                            AND E.dol >= '"+(nextYear)+"-01-01' ) ) ) "
					 + "SELECT "
					 + "a.EMPCODE,a.NAME,a.Encash_Days, "
				//	 + "Round(CONVERT(NUMERIC(12, 2), ( "
				//	 + "      ( ( a.basic + a.da + a.vda ) / 30 ) * a.encash_days )), 2) "
				//	 + "AS TOTAL_AMT, "
					 +" Round(CONVERT(NUMERIC(12, 0), ( ( ( a.basic) / 30 ) *  a.encash_days )), 2) AS Basic_f,"
					 +" Round(CONVERT(NUMERIC(12, 0), ( ( ( a.da) / 30 ) *  a.encash_days )), 2) AS Da ,"
					 +" Round(CONVERT(NUMERIC(12, 0), ( ( ( a.vda) / 30 ) *  a.encash_days )), 2) AS Vda,  "
					 +" Round(CONVERT(NUMERIC(12, 0),(((a.basic)/ 30)* a.encash_days)+ "
					 +"	 ( ( ( a.da) / 30 ) *  a.encash_days )+( ( ( a.vda) / 30 ) *  a.encash_days )), 2) "
					 +"	       AS TOTAL_AMT,"
					 /*+ "Round(CONVERT(NUMERIC(12, 0), ( "
					 + "( ( (( "
					 + "      ( ( a.basic + a.da + a.vda ) / 30 ) * a.encash_days )) / 100 ) * 12 ))), 2)  AS PF_AMT, "*/
					 + "Round(CONVERT(NUMERIC(12, 0), ( "
					 + "            ( ( ( a.basic + a.da + a.vda ) / 30 ) * a.encash_days ))), 2) " 
					/* +"- (( "
					 + "            ( "
					 + "( ( "
					 + "            ( "
					 + "                            a.basic + a.da + a.vda ) / 30 ) * a.encash_days ) / 100 ) * 12 )) )), 2) " */
					 +" AS NET_PAY "
		//			 +" Round(CONVERT(NUMERIC(12, 2), ( ( ( a.basic) / 30 ) *  a.encash_days )), 2) AS Basic_f,"
		//			 +" Round(CONVERT(NUMERIC(12, 2), ( ( ( a.da) / 30 ) *  a.encash_days )), 2) AS Da_f ,"
		//			 +" Round(CONVERT(NUMERIC(12, 2), ( ( ( a.vda) / 30 ) *  a.encash_days )), 2) AS Vda_f  "
					 + "FROM   a "
					 + "ORDER  BY a.empcode";
					
					
					/* + "WITH a "
					 + "     AS (SELECT E.empno, t.PRJ_SRNO,"
					 + "                E.empcode, "
					 + "                CONVERT(NVARCHAR(100), E.lname + ' ' + E.fname + ' ' + E.mname) "
					 + "                AS "
					 + "                NAME, "
					 + "                (SELECT  CASE "
					 + "                                WHEN ( ( ( l.bal + 30 ) - 90 ) - (SELECT "
					 + "                                       Isnull(CASE "
					 + "                                                WHEN "
					 + "                                       Sum(days) >= 25 THEN "
					 + "                                                                 Round( "
					 + "                                                ( Sum(days) / 12 ), 0) "
					 + "                                                                 ELSE 0 "
					 + "                                              END, "
					 + "                                       0) "
					 + "                                       AS "
					 + "                                       usedleave "
					 + "                                                                  FROM "
					 + "                                       leavetran "
					 + "                                                                  WHERE "
					 + "                                       empno = E.empno "
					 + "                                       AND trndate BETWEEN "
					 + "                                       '"+(year)+"-01-01' AND "
					 + "                                       '"+(year)+"-12-31' "
					 + "                                       AND leavecd = 7 "
					 + "                                       AND status = 'SANCTION' "
					 + "                                       AND trntype = 'D') ) < "
					 + "                                     0 THEN 0 "
					 + "                                ELSE (( ( ( l.bal + 30 ) - 90 ) - (SELECT "
					 + "                                        Isnull(CASE "
					 + "                                                                  WHEN "
					 + "                                               Sum(days) >= 25 "
					 + "                                               THEN "
					 + "                                                                  Round( "
					 + "                                               ( Sum(days) / 12 ), 0 "
					 + "                                                                  ) "
					 + "                                                                  ELSE 0 "
					 + "                                               END, "
					 + "                                        0) "
					 + "                                        AS "
					 + "                                        usedleave "
					 + "                                                                   FROM "
					 + "                                        leavetran "
					 + "                                                                   WHERE "
					 + "                                        empno = E.empno "
					 + "                                        AND trndate BETWEEN "
					 + "                                            '"+(year)+"-01-01' AND "
					 + "                                            '"+(year)+"-12-31' "
					 + "                                        AND leavecd = 7 "
					 + "                                        AND status = 'SANCTION' "
					 + "                                        AND trntype = 'D') )) "
					 + "                              END AS Encash_Days "
					 + "                 FROM   leavebal l "
					 + "                 WHERE  l.empno = E.empno "
					 + "                        AND l.leavecd = 1 "
					 + "                        AND baldt=(select top 1 BALDT from leavebal where empno=E.EMPNO and LEAVECD=1 and BALDT<'"+(year)+"-01-01' order by BALDT desc,srno desc) "
					 + "                        AND srno=(select top 1 srno from leavebal where empno=E.EMPNO and LEAVECD=1 and BALDT<'"+(year)+"-01-01' order by BALDT desc,srno desc) "
					 + "						 ) "
					 + "                AS "
					 + "                   Encash_Days, "
					 + "                (SELECT inp_amt "
					 + "                 FROM   paytran_stage "
					 + "                 WHERE  empno = E.empno "
					 + "                        AND trncd = 101 "
					 + "                        AND trndt = '"+(previousYear)+"-03-31') "
					 + "                AS "
					 + "                BASIC "
					 + "                   , "
					 + "                (SELECT inp_amt "
					 + "                 FROM   paytran_stage "
					 + "                 WHERE  empno = E.empno "
					 + "                        AND trncd = 102 "
					 + "                        AND trndt = '"+(previousYear)+"-03-31') "
					 + "                AS DA "
					 + "                , "
					 + "                (SELECT inp_amt "
					 + "                 FROM   paytran_stage "
					 + "                 WHERE  empno = E.empno "
					 + "                        AND trncd = 138 "
					 + "                        AND trndt = '"+(previousYear)+"-03-31') "
					 + "                AS "
					 + "                VDA "
					 + "         FROM   empmast E, "
					 + "                emptran t "
					 + "         WHERE  E.empno = t.empno "
					 + "                AND t.srno = (SELECT Max(srno) "
					 + "                              FROM   emptran T1 "
					 + "                              WHERE  T1.empno = t.empno) "
					 + "                AND ( ( E.status = 'A' "
					 + "                        AND E.doj <= '"+(year)+"-12-31' ) "
					 + "                       OR ( E.status = 'N' "
					 + "                            AND E.dol >= '"+(year)+"-01-01' ) ) and t.PRJ_SRNO="+site_id+") "
					 + "SELECT "
					 + "a.EMPCODE,a.NAME,a.Encash_Days, "
				//	 + "Round(CONVERT(NUMERIC(12, 2), ( "
				//	 + "      ( ( a.basic + a.da + a.vda ) / 30 ) * a.encash_days )), 2) "
				//	 + "AS TOTAL_AMT, "
					 +" Round(CONVERT(NUMERIC(12, 2), ( ( ( a.basic) / 30 ) *  a.encash_days )), 2) AS Basic_f,"
					 +" Round(CONVERT(NUMERIC(12, 2), ( ( ( a.da) / 30 ) *  a.encash_days )), 2) AS Da ,"
					 +" Round(CONVERT(NUMERIC(12, 2), ( ( ( a.vda) / 30 ) *  a.encash_days )), 2) AS Vda,  "
					 +" Round(CONVERT(NUMERIC(12, 2),(((a.basic)/ 30)* a.encash_days)+ "
					 +"	 ( ( ( a.da) / 30 ) *  a.encash_days )+( ( ( a.vda) / 30 ) *  a.encash_days )), 2) "
					 +"	       AS TOTAL_AMT,"
					 + "Round(CONVERT(NUMERIC(12, 2), ( "
					 + "( ( (( "
					 + "      ( ( a.basic + a.da + a.vda ) / 30 ) * a.encash_days )) / 100 ) * 12 ))), 2)                    AS PF_AMT, "
					 + "Round(CONVERT(NUMERIC(12, 2), ( "
					 + "            ( ( ( a.basic + a.da + a.vda ) / 30 ) * a.encash_days ) - (( "
					 + "            ( "
					 + "( ( "
					 + "            ( "
					 + "                            a.basic + a.da + a.vda ) / 30 ) * a.encash_days ) / 100 ) * 12 )) )), 2) AS NET_PAY "
		//			 +" Round(CONVERT(NUMERIC(12, 2), ( ( ( a.basic) / 30 ) *  a.encash_days )), 2) AS Basic_f,"
		//			 +" Round(CONVERT(NUMERIC(12, 2), ( ( ( a.da) / 30 ) *  a.encash_days )), 2) AS Da_f ,"
		//			 +" Round(CONVERT(NUMERIC(12, 2), ( ( ( a.vda) / 30 ) *  a.encash_days )), 2) AS Vda_f  "
					 + "FROM   a "
					 + "ORDER  BY a.empcode";*/
		}
		else 
		 {System.out.println("i am in brwise");
			 SQL = ""
					 + "WITH a "
					 + "     AS (SELECT E.empno, t.PRJ_SRNO,"
					 + "                E.empcode, "
					 + "                CONVERT(NVARCHAR(100), E.lname + ' ' + E.fname + ' ' + E.mname) "
					 + "                AS "
					 + "                NAME, "
					 + "                (SELECT  CASE "
					 + "                                WHEN ( ( ( l.bal + 30 ) - 90 ) - (SELECT "
					 + "                                       Isnull(CASE "
					 + "                                                WHEN "
					 + "                                       Sum(days) >= 25 THEN "
					 + "                                                                 Round( "
					 + "                                                ( Sum(days) / 12 ), 0) "
					 + "                                                                 ELSE 0 "
					 + "                                              END, "
					 + "                                       0) "
					 + "                                       AS "
					 + "                                       usedleave "
					 + "                                                                  FROM "
					 + "                                       leavetran "
					 + "                                                                  WHERE "
					 + "                                       empno = E.empno "
					 + "                                       AND trndate BETWEEN "
					 + "                                       '"+(year)+"-01-01' AND "
					 + "                                       '"+(year)+"-12-31' "
					 + "                                       AND leavecd = 7 "
					 + "                                       AND status = 'SANCTION' "
					 + "                                       AND trntype = 'D') ) < "
					 + "                                     0 THEN 0 "
					 + "                                ELSE (( ( ( l.bal + 30 ) - 90 ) - (SELECT "
					 + "                                        Isnull(CASE "
					 + "                                                                  WHEN "
					 + "                                               Sum(days) >= 25 "
					 + "                                               THEN "
					 + "                                                                  Round( "
					 + "                                               ( Sum(days) / 12 ), 0 "
					 + "                                                                  ) "
					 + "                                                                  ELSE 0 "
					 + "                                               END, "
					 + "                                        0) "
					 + "                                        AS "
					 + "                                        usedleave "
					 + "                                                                   FROM "
					 + "                                        leavetran "
					 + "                                                                   WHERE "
					 + "                                        empno = E.empno "
					 + "                                        AND trndate BETWEEN "
					 + "                                            '"+(year)+"-01-01' AND "
					 + "                                            '"+(year)+"-12-31' "
					 + "                                        AND leavecd = 7 "
					 + "                                        AND status = 'SANCTION' "
					 + "                                        AND trntype = 'D') )) "
					 + "                              END AS Encash_Days "
					 + "                 FROM   leavebal l "
					 + "                 WHERE  l.empno = E.empno "
					 + "                        AND l.leavecd = 1 "
					 + "                        AND baldt=(select top 1 BALDT from leavebal where empno=E.EMPNO and LEAVECD=1 and BALDT<'"+(nextYear)+"-01-01' order by BALDT desc,srno desc) "
					 + "                        AND srno=(select top 1 srno from leavebal where empno=E.EMPNO and LEAVECD=1 and BALDT<'"+(nextYear)+"-01-01' order by BALDT desc,srno desc) "
					 + "						 ) "
					 + "                AS "
					 + "                   Encash_Days, "
					 + "                (SELECT inp_amt "
					 + "                 FROM   paytran_stage "
					 + "                 WHERE  empno = E.empno "
					 + "                        AND trncd = 101 "
					 + "                        AND trndt = '"+(year)+"-03-31') "
					 + "                AS "
					 + "                BASIC "
					 + "                   , "
					 + "                (SELECT inp_amt "
					 + "                 FROM   paytran_stage "
					 + "                 WHERE  empno = E.empno "
					 + "                        AND trncd = 102 "
					 + "                        AND trndt = '"+(year)+"-03-31') "
					 + "                AS DA "
					 + "                , "
					 + "                (SELECT inp_amt "
					 + "                 FROM   paytran_stage "
					 + "                 WHERE  empno = E.empno "
					 + "                        AND trncd = 138 "
					 + "                        AND trndt = '"+(year)+"-03-31') "
					 + "                AS "
					 + "                VDA "
					 + "         FROM   empmast E, "
					 + "                emptran t "
					 + "         WHERE  E.empno = t.empno "
					 + "                AND t.srno = (SELECT Max(srno) "
					 + "                              FROM   emptran T1 "
					 + "                              WHERE  T1.empno = t.empno) "
					 + "                AND ( ( E.status = 'A' "
					 + "                        AND E.doj <= '"+(year)+"-12-31' ) "
					 + "                       OR ( E.status = 'N' "
					 + "                            AND E.dol >= '"+(nextYear)+"-01-01' ) )  and t.PRJ_SRNO="+SelectedBranch+") "
					 + "SELECT "
					 + "a.EMPCODE,a.NAME, a.Encash_Days,"
		 			 +" Round(CONVERT(NUMERIC(12, 0), ( ( ( a.basic) / 30 ) *  a.encash_days )), 2) AS Basic_f,"
		 			 +" Round(CONVERT(NUMERIC(12, 0), ( ( ( a.da) / 30 ) *  a.encash_days )), 2) AS Da ,"
		 			 +" Round(CONVERT(NUMERIC(12, 0), ( ( ( a.vda) / 30 ) *  a.encash_days )), 2) AS Vda,  "
		 			 +" Round(CONVERT(NUMERIC(12, 0),(((a.basic)/ 30)* a.encash_days)+  "
		 			 +"	 ( ( ( a.da) / 30 ) *  a.encash_days )+( ( ( a.vda) / 30 ) *  a.encash_days )), 2) "
		 			 +"	 AS TOTAL_AMT,  "
					/* + " Round(CONVERT(NUMERIC(12, 0), ( "
					 + "( ( (( "
					 + "      ( ( a.basic + a.da + a.vda ) / 30 ) * a.encash_days )) / 100 ) * 12 ))), 2) AS PF_AMT, "*/
					 + "Round(CONVERT(NUMERIC(12, 0), ( "
					 + "            ( ( ( a.basic + a.da + a.vda ) / 30 ) * a.encash_days ))), 2) " 
					 /*+"- (( "
					 + "            ( "
					 + "( ( "
					 + "            ( "
					 + "                            a.basic + a.da + a.vda ) / 30 ) * a.encash_days ) / 100 ) * 12 )) )), 2) " */
					 +"AS NET_PAY "
	//				 +" Round(CONVERT(NUMERIC(12, 2), ( ( ( a.basic) / 30 ) *  a.encash_days )), 2) AS Basic_f,"
	//				 +" Round(CONVERT(NUMERIC(12, 2), ( ( ( a.da) / 30 ) *  a.encash_days )), 2) AS Da_f ,"
	//				 +" Round(CONVERT(NUMERIC(12, 2), ( ( ( a.vda) / 30 ) *  a.encash_days )), 2) AS Vda_f  "	
					 + "FROM   a "
					 + "ORDER  BY a.empcode";
					 
					 
						/*+ "WITH a "
						 + "     AS (SELECT E.empno, t.PRJ_SRNO,"
						 + "                E.empcode, "
						 + "                CONVERT(NVARCHAR(100), E.lname + ' ' + E.fname + ' ' + E.mname) "
						 + "                AS "
						 + "                NAME, "
						 + "                (SELECT  CASE "
						 + "                                WHEN ( ( ( l.bal + 30 ) - 90 ) - (SELECT "
						 + "                                       Isnull(CASE "
						 + "                                                WHEN "
						 + "                                       Sum(days) >= 25 THEN "
						 + "                                                                 Round( "
						 + "                                                ( Sum(days) / 12 ), 0) "
						 + "                                                                 ELSE 0 "
						 + "                                              END, "
						 + "                                       0) "
						 + "                                       AS "
						 + "                                       usedleave "
						 + "                                                                  FROM "
						 + "                                       leavetran "
						 + "                                                                  WHERE "
						 + "                                       empno = E.empno "
						 + "                                       AND trndate BETWEEN "
						 + "                                       '"+(year)+"-01-01' AND "
						 + "                                       '"+(year)+"-12-31' "
						 + "                                       AND leavecd = 7 "
						 + "                                       AND status = 'SANCTION' "
						 + "                                       AND trntype = 'D') ) < "
						 + "                                     0 THEN 0 "
						 + "                                ELSE (( ( ( l.bal + 30 ) - 90 ) - (SELECT "
						 + "                                        Isnull(CASE "
						 + "                                                                  WHEN "
						 + "                                               Sum(days) >= 25 "
						 + "                                               THEN "
						 + "                                                                  Round( "
						 + "                                               ( Sum(days) / 12 ), 0 "
						 + "                                                                  ) "
						 + "                                                                  ELSE 0 "
						 + "                                               END, "
						 + "                                        0) "
						 + "                                        AS "
						 + "                                        usedleave "
						 + "                                                                   FROM "
						 + "                                        leavetran "
						 + "                                                                   WHERE "
						 + "                                        empno = E.empno "
						 + "                                        AND trndate BETWEEN "
						 + "                                            '"+(year)+"-01-01' AND "
						 + "                                            '"+(year)+"-12-31' "
						 + "                                        AND leavecd = 7 "
						 + "                                        AND status = 'SANCTION' "
						 + "                                        AND trntype = 'D') )) "
						 + "                              END AS Encash_Days "
						 + "                 FROM   leavebal l "
						 + "                 WHERE  l.empno = E.empno "
						 + "                        AND l.leavecd = 1 "
						 + "                        AND baldt=(select top 1 BALDT from leavebal where empno=E.EMPNO and LEAVECD=1 and BALDT<'"+(year)+"-01-01' order by BALDT desc,srno desc) "
						 + "                        AND srno=(select top 1 srno from leavebal where empno=E.EMPNO and LEAVECD=1 and BALDT<'"+(year)+"-01-01' order by BALDT desc,srno desc) "
						 + "						 ) "
						 + "                AS "
						 + "                   Encash_Days, "
						 + "                (SELECT inp_amt "
						 + "                 FROM   paytran_stage "
						 + "                 WHERE  empno = E.empno "
						 + "                        AND trncd = 101 "
						 + "                        AND trndt = '"+(previousYear)+"-03-31') "
						 + "                AS "
						 + "                BASIC "
						 + "                   , "
						 + "                (SELECT inp_amt "
						 + "                 FROM   paytran_stage "
						 + "                 WHERE  empno = E.empno "
						 + "                        AND trncd = 102 "
						 + "                        AND trndt = '"+(previousYear)+"-03-31') "
						 + "                AS DA "
						 + "                , "
						 + "                (SELECT inp_amt "
						 + "                 FROM   paytran_stage "
						 + "                 WHERE  empno = E.empno "
						 + "                        AND trncd = 138 "
						 + "                        AND trndt = '"+(previousYear)+"-03-31') "
						 + "                AS "
						 + "                VDA "
						 + "         FROM   empmast E, "
						 + "                emptran t "
						 + "         WHERE  E.empno = t.empno "
						 + "                AND t.srno = (SELECT Max(srno) "
						 + "                              FROM   emptran T1 "
						 + "                              WHERE  T1.empno = t.empno) "
						 + "                AND ( ( E.status = 'A' "
						 + "                        AND E.doj <= '"+(year)+"-12-31' ) "
						 + "                       OR ( E.status = 'N' "
						 + "                            AND E.dol >= '"+(year)+"-01-01' ) )  and t.PRJ_SRNO="+SelectedBranch+") "
						 + "SELECT "
						 + "a.EMPCODE,a.NAME, a.Encash_Days,"
			 			 +" Round(CONVERT(NUMERIC(12, 2), ( ( ( a.basic) / 30 ) *  a.encash_days )), 2) AS Basic_f,"
			 			 +" Round(CONVERT(NUMERIC(12, 2), ( ( ( a.da) / 30 ) *  a.encash_days )), 2) AS Da ,"
			 			 +" Round(CONVERT(NUMERIC(12, 2), ( ( ( a.vda) / 30 ) *  a.encash_days )), 2) AS Vda,  "
			 			 +" Round(CONVERT(NUMERIC(12, 2),(((a.basic)/ 30)* a.encash_days)+  "
			 			 +"	 ( ( ( a.da) / 30 ) *  a.encash_days )+( ( ( a.vda) / 30 ) *  a.encash_days )), 2) "
			 			 +"	 AS TOTAL_AMT,  "
						 + " Round(CONVERT(NUMERIC(12, 2), ( "
						 + "( ( (( "
						 + "      ( ( a.basic + a.da + a.vda ) / 30 ) * a.encash_days )) / 100 ) * 12 ))), 2)                    AS PF_AMT, "
						 + "Round(CONVERT(NUMERIC(12, 2), ( "
						 + "            ( ( ( a.basic + a.da + a.vda ) / 30 ) * a.encash_days ) - (( "
						 + "            ( "
						 + "( ( "
						 + "            ( "
						 + "                            a.basic + a.da + a.vda ) / 30 ) * a.encash_days ) / 100 ) * 12 )) )), 2) AS NET_PAY "
		//				 +" Round(CONVERT(NUMERIC(12, 2), ( ( ( a.basic) / 30 ) *  a.encash_days )), 2) AS Basic_f,"
		//				 +" Round(CONVERT(NUMERIC(12, 2), ( ( ( a.da) / 30 ) *  a.encash_days )), 2) AS Da_f ,"
		//				 +" Round(CONVERT(NUMERIC(12, 2), ( ( ( a.vda) / 30 ) *  a.encash_days )), 2) AS Vda_f  "	
						 + "FROM   a "
						 + "ORDER  BY a.empcode";*/
		 }
		
		System.out.println("query is: "+SQL);
		    st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(SQL);
			
			
			
			
		    				HSSFRow rowtitle6=   sheet.createRow((short)(rownum++)+1);
		    				HSSFCell cell31 = rowtitle6.createCell((short) 1); 
		    				cell31.setCellValue("Employee List For Project Site is :- "+(SiteName));
		    				cell31.setCellStyle(my_style);
		    				
						//	rowtitle6.createCell((short) 1).setCellValue("Employee List For Project Site is :- "+(site_name + Project_Code));

							HSSFRow heading1=   sheet.createRow((short)(rownum++)+1);
							for(i=0;i<results.length;i++)
							{
								sheet.setColumnWidth((short)i, (short)9000);
								//heading1.createCell((short) i).setCellValue(""+results[i]);
						
								HSSFCell cell21 = heading1.createCell((short) i);
								cell21.setCellValue(""+results[i]);
								cell21.setCellStyle(my_style);
					}	
					
					while(rs.next())
					{
						
						int k=0;
						count++;
						srno++;
						
						
						HSSFRow row = sheet.createRow((short)(rownum++)+1);
						for(int j=0;j<results.length;j++)
						{
						
							String ex="";
						    ex = rs.getString(results[j])==null?" ":rs.getString(results[j]);
						    row.createCell((short) k).setCellValue(""+ex);
						    k++;
						}
					
						Employee+=1;
						basic=rs.getFloat("Basic_f");
						da=rs.getFloat("Da");
						vda=rs.getFloat("Vda");
		//				pf= rs.getFloat("PF_AMT");
						netpay=rs.getFloat("NET_PAY");
						amt = rs.getFloat("TOTAL_AMT");
						//System.out.println("ak: "+rs.getString("Encash_Days"));
						/*
						 * if(rs.getString("Encash_Days").equalsIgnoreCase("0.0")){ days+=1; }
						 */
						 ftotbasic =ftotbasic+basic ;
						 ftotda =ftotda+da ;
						 ftotvda =ftotvda+vda ;
			//			 ftotpf =ftotpf+pf ;
						 ftotnetpay = ftotnetpay+netpay;
						 ftotamt =ftotamt+amt;
						tdays=tdays+days;
						totbasic = totbasic + basic;
						
						totda=totda+da;
						
						totvda=totvda+vda;
						
	//					totpf=totpf+pf;
						
						totnetpay=totnetpay+netpay;
						
						basic = 0.0f;
						da=0;
						vda=0;
//						pf=0;
						netpay=0;
						amt=0;
						days=0;
						totbasic = 0;
						totda=0;
						totvda=0;
						totpf=0;
						totnetpay=0;
						 
						}
					//	HSSFRow row1 = sheet.createRow((short) (rownum++));
					    /*HSSFRow row1 = sheet.createRow((short) (rownum++)+1);
						row1.createCell((short) 0).setCellValue(" ");
						row1 = sheet.createRow((short) (rownum++));
						row1.createCell((short) 1).setCellValue("Number Of Employee :-  " +Employee );*/
						/*row1.createCell((short) 2).setCellValue("TOTAL BASIC :-  " +Math.round(totbasic ));
						row1.createCell((short) 3).setCellValue("TOTAL DA :-  " +Math.round(totda ));
						row1.createCell((short) 4).setCellValue("TOTAL VDA :-  " +Math.round(totvda ));
						row1.createCell((short) 6).setCellValue("TOTAL PF:-  " +Math.round(totpf ));
						row1.createCell((short) 7).setCellValue("TOTAL NET Pay:-  " +Math.round(totnetpay ));*/
					
						/*HSSFCell cell32 = rowtitle6.createCell((short) 1); 
						cell32.setCellValue("Number Of Employee :-  " +Employee );
						cell32.setCellStyle(my_style);*/
						
			//			row1.createCell((short) 1).setCellValue("Number Of Employee :-  " +Employee );
						totalEmployee = totalEmployee+ Employee;	
						Employee = 0;
						/*basic = 0.0f;
						da=0;
						vda=0;
						pf=0;
						netpay=0;
						amt=0;
						days=0;
						totbasic = 0;
						totda=0;
						totvda=0;
						totpf=0;
						totnetpay=0;*/
//					}	
				    HSSFRow row22 = sheet.createRow((short) (rownum++)+1);
					row22.createCell((short) 0).setCellValue(" ");
					row22 = sheet.createRow((short) (rownum++)+1);
					
					HSSFCell cell311 = row22.createCell((short) 1); 
					cell311.setCellValue("SUMMARY :- ");
					cell311.setCellStyle(my_style);
					HSSFCell cell3111 = row22.createCell((short) 3); 
					cell3111.setCellValue("Basic :- "+Math.round(ftotbasic  ));
					cell3111.setCellStyle(my_style);
					HSSFCell cell31111 = row22.createCell((short) 4); 
					cell31111.setCellValue("DA :- "+Math.round(ftotda  ));
					cell31111.setCellStyle(my_style);
					HSSFCell cell311111 = row22.createCell((short) 5); 
					cell311111.setCellValue("VDA :- "+Math.round(ftotvda  ));
					cell311111.setCellStyle(my_style);
					HSSFCell cell3111112 = row22.createCell((short) 6); 
					cell3111112.setCellValue("TOTAL AMT :- "+Math.round(ftotamt));
					cell3111112.setCellStyle(my_style);
					/*HSSFCell cell3111111 = row22.createCell((short) 7); 
					cell3111111.setCellValue("PF_AMT :- "+Math.round(ftotpf  ));
					cell3111111.setCellStyle(my_style);*/
					HSSFCell cell31111111 = row22.createCell((short) 7); 
					cell31111111.setCellValue("NET_PAY :- "+Math.round(ftotnetpay  ));
					cell31111111.setCellStyle(my_style);
					
				
					/*HSSFCell cell311111111 = row22.createCell((short) 8); 
					cell311111111.setCellValue("Zero(0) day Enacash Employee :- "+tdays);
					cell311111111.setCellStyle(my_style);*/
					
					/*row22.createCell((short) 1).setCellValue("SUMMARY "  );  days
					row22.createCell((short) 2).setCellValue("Basic :- "+Math.round(totbasic  ));
					row22.createCell((short) 3).setCellValue("D.A :- "+Math.round(totda  ));
					row22.createCell((short) 4).setCellValue("VDA :- "+Math.round(totvda  ));
					row22.createCell((short) 6).setCellValue("PF_AMT :- "+Math.round(totpf  ));
					row22.createCell((short) 7).setCellValue("NET_PAY :- "+Math.round(totnetpay));*/
				 
				 
				 	
					
				
					HSSFRow row2 = sheet.createRow((short) (rownum++)+1);
					row2.createCell((short) 0).setCellValue(" ");
					row2 = sheet.createRow((short) (rownum++)+1);
					row2.createCell((short) 1).setCellValue("Total Number Of Employee :- " +totalEmployee );
					hwb.write(out1);
					out1.close();
					st.close();
					st1.close();
					con.close();
	    
					System.out.println("Result OK.....");
		
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
		
		}
		
		
/***************************************************Prasad Start**********************************************************************/
		
		public static void encahlistbranchwiseNew(String date,String[] results,String BranchType,String SelectedBranch,String desigtype,String RangeFrom,String RangeTo,String SiteName,String filepath,String imgpath)
		{

		System.out.println(""+results.length);
		int i = 0;
		Properties prop = new Properties();
		int brtot[] = new int[results.length];
		 int ALLbrtot[] = new int[results.length];
		 int PBRCD = 0;
		 int gross = 0;
		 int rownum=10;
		 System.out.println("BranchType: "+BranchType);
		 System.out.println("SelectedBranch: "+SelectedBranch);
	     try
	     {
	    	 ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream stream = classLoader.getResourceAsStream("constant.properties");
			prop.load(stream);
	     }
	     catch (Exception e) {System.out.println("Error in constant properties Manager "+e);}
	     
		try
		{
			RepoartBean repBean  = new RepoartBean();
			
			String SQL = "";
			String[] parts = date.split("-");
			String day = parts[0]; // 004
			String month = parts[1];
			String year = parts[2];
			int curYear=Integer.parseInt(year);
			int previousYear=Integer.parseInt(year);
			previousYear = previousYear-1;
			int nextYear = curYear+1;
			 System.out.println("day: "+day+"  month: "+month+"  year:  "+year+"  curYear: "+curYear+" previousYear: "+previousYear);
			 System.out.println("SiteName AJ : "+SiteName);
			LookupHandler lkh=new LookupHandler();
			System.out.println( filepath);
		FileOutputStream out1 = new FileOutputStream(new File(filepath));
		HSSFWorkbook hwb=new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("EmpNewlist");
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
		System.out.println("1111.....");
		
		
	   HSSFCellStyle my_style = hwb.createCellStyle();
	   HSSFCellStyle my_style1 = hwb.createCellStyle();

	   HSSFFont my_font=hwb.createFont();
	   my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	   my_style.setFont(my_font);
	 
	   
		HSSFRow rowtitle=   sheet.createRow((short)0);
		HSSFCell cell = rowtitle.createCell((short) 1);
		cell.setCellValue(prop.getProperty("bankName1"));
		cell.setCellStyle(my_style);
		HSSFRow rowtitle1=   sheet.createRow((short)1);
		HSSFCell cell1 = rowtitle1.createCell((short) 1);
		cell1.setCellValue(prop.getProperty("addressForReport"));
		cell1.setCellStyle(my_style);
		HSSFRow rowtitle2=   sheet.createRow((short)2);
		HSSFCell cell2 = rowtitle2.createCell((short) 1);
		cell2.setCellValue(prop.getProperty("contactForReport"));
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle31=   sheet.createRow((short)3);
		cell2 = rowtitle31.createCell((short) 1);
		cell2.setCellValue(	prop.getProperty("mailForReport"));
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle3=   sheet.createRow((short)4);
		cell2=rowtitle3.createCell((short) 1);
		cell2.setCellValue("");
		cell2.setCellStyle(my_style);
		HSSFRow rowtitle4=   sheet.createRow((short)5);
		rowtitle4.createCell((short) 0).setCellValue("");
		
		long millis=System.currentTimeMillis();  
		 java.sql.Date date1=new java.sql.Date(millis);  
		 DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		 String reportDate = df.format(date1);
		
		HSSFRow rowtitle5=   sheet.createRow((short)6);
	
		HSSFCell cell22 = rowtitle5.createCell((short) 1);
		cell22 = rowtitle5.createCell((short) 1);
		cell22.setCellValue(("Branchwise Encashment List:- "+reportDate));
		cell22.setCellStyle(my_style);
		
		
		HSSFFont blueFont = hwb.createFont();
		blueFont.setColor(HSSFColor.BLUE.index);
		
		HSSFCellStyle style = hwb.createCellStyle();
		//style.setFont(blueFont);
		style.setFillForegroundColor(HSSFColor.BLUE.index);
		
		
	sheet.createFreezePane( 0, 10, 0, 10 );
	  
	   my_style1.setAlignment((short) 2);
	   my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	   my_style1.setFont(my_font);
	   
	    Connection con =null;
	    ReportDAO.OpenCon("", "", "",repBean);
	    con = repBean.getCn();	
		int count=1,srno = 0;
		ResultSet rs = null;
		ResultSet rs1 = null;
		Statement st = null;
		Statement st1 = null;
		
		String Query="";
		String Query1="";
		int totalEmployee =0;
		int Employee = 0;
		String site_id ="";
		String site_name ="";
		String Project_Code ="";
		float totbasic = 0.0f;
		float totda = 0.0f;
		float totvda = 0.0f;
		float totpf = 0.0f;
		float totnetpay = 0.0f;
		float totamt = 0.0f;
		float basic = 0.0f;
		float da = 0.0f;
		float vda = 0.0f;
		float pf = 0.0f;
		float amt = 0.0f;
		float netpay = 0.0f;
		float ftotbasic = 0.0f;
		float ftotda = 0.0f;
		float ftotvda = 0.0f;
		float ftotpf = 0.0f;
		float ftotnetpay = 0.0f;
		float ftotamt = 0.0f;
		float days=0;
		float tdays=0;
		
		if(BranchType.equalsIgnoreCase("1"))
		{
			Query1 = "select * from Project_Sites order by SITE_ID";
			System.out.println("in all Project_Sites ");
		}
		else {
			Query1 = "select * from Project_Sites where SITE_ID =" +SelectedBranch ;
			System.out.println("in selected Project_Sites ");
		}
		
		System.out.println("Query is@@@ " +Query1);
		st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	
		rs1 = st1.executeQuery(Query1);
		int cnt=0;
		if(BranchType.equalsIgnoreCase("1"))
		{System.out.println("in all Project_Sites query");
			SQL = ""
					 + "WITH a "
					 + "     AS (SELECT E.empno AS emptotal, t.PRJ_SRNO,"
					 + "                E.empcode, "
					 + "                (SELECT  CASE "
					 + "                                WHEN ( ( ( l.bal + 30 ) - 90 ) - (SELECT "
					 + "                                       Isnull(CASE "
					 + "                                                WHEN "
					 + "                                       Sum(days) >= 25 THEN "
					 + "                                                                 Round( "
					 + "                                                ( Sum(days) / 12 ), 0) "
					 + "                                                                 ELSE 0 "
					 + "                                              END, "
					 + "                                       0) "
					 + "                                       AS "
					 + "                                       usedleave "
					 + "                                                                  FROM "
					 + "                                       leavetran "
					 + "                                                                  WHERE "
					 + "                                       empno = E.empno "
					 + "                                       AND trndate BETWEEN "
					 + "                                       '"+(year)+"-01-01' AND "
					 + "                                       '"+(year)+"-12-31' "
					 + "                                       AND leavecd = 7 "
					 + "                                       AND status = 'SANCTION' "
					 + "                                       AND trntype = 'D') ) < "
					 + "                                     0 THEN 0 "
					 + "                                ELSE (( ( ( l.bal + 30 ) - 90 ) - (SELECT "
					 + "                                        Isnull(CASE "
					 + "                                                                  WHEN "
					 + "                                               Sum(days) >= 25 "
					 + "                                               THEN "
					 + "                                                                  Round( "
					 + "                                               ( Sum(days) / 12 ), 0 "
					 + "                                                                  ) "
					 + "                                                                  ELSE 0 "
					 + "                                               END, "
					 + "                                        0) "
					 + "                                        AS "
					 + "                                        usedleave "
					 + "                                                                   FROM "
					 + "                                        leavetran "
					 + "                                                                   WHERE "
					 + "                                        empno = E.empno "
					 + "                                        AND trndate BETWEEN "
					 + "                                            '"+(year)+"-01-01' AND "
					 + "                                            '"+(year)+"-12-31' "
					 + "                                        AND leavecd = 7 "
					 + "                                        AND status = 'SANCTION' "
					 + "                                        AND trntype = 'D') )) "
					 + "                              END AS Encash_Days "
					 + "                 FROM   leavebal l "
					 + "                 WHERE  l.empno = E.empno "
					 + "                        AND l.leavecd = 1 "
					 + "                        AND baldt=(select top 1 BALDT from leavebal where empno=E.EMPNO and LEAVECD=1 and BALDT<'"+(nextYear)+"-01-01' order by BALDT desc,srno desc) "
					 + "                        AND srno=(select top 1 srno from leavebal where empno=E.EMPNO and LEAVECD=1 and BALDT<'"+(nextYear)+"-01-01' order by BALDT desc,srno desc) "
					 + "						 ) "
					 + "                AS "
					 + "                   Encash_Days, "
					 + "                (SELECT inp_amt "
					 + "                 FROM   paytran_stage "
					 + "                 WHERE  empno = E.empno "
					 + "                        AND trncd = 101 "
					 + "                        AND trndt = '"+(year)+"-03-31') "
					 + "                AS "
					 + "                BASIC "
					 + "                   , "
					 + "                (SELECT inp_amt "
					 + "                 FROM   paytran_stage "
					 + "                 WHERE  empno = E.empno "
					 + "                        AND trncd = 102 "
					 + "                        AND trndt = '"+(year)+"-03-31') "
					 + "                AS DA "
					 + "                , "
					 + "                (SELECT inp_amt "
					 + "                 FROM   paytran_stage "
					 + "                 WHERE  empno = E.empno "
					 + "                        AND trncd = 138 "
					 + "                        AND trndt = '"+(year)+"-03-31') "
					 + "                AS "
					 + "                VDA "
					 + "         FROM   empmast E, "
					 + "                emptran t "
					 + "         WHERE  E.empno = t.empno "
					 + "                AND t.srno = (SELECT Max(srno) "
					 + "                              FROM   emptran T1 "
					 + "                              WHERE  T1.empno = t.empno) "
					 + "                AND ( ( E.status = 'A' "
					 + "                        AND E.doj <= '"+(year)+"-12-31' ) "
					 + "                       OR ( E.status = 'N' "
					 + "                            AND E.dol >= '"+(nextYear)+"-01-01' ) ) ) "
					 + "SELECT "
					 + "Count(a.emptotal) AS No_of_Emp, pst.SITE_ID AS Branch_Code, pst.SITE_NAME AS Branch_Name, "
					 +" Sum(Round(CONVERT(NUMERIC(12, 0), ( ( ( a.basic) / 30 ) *  a.encash_days )), 2)) AS Basic_f,"
					 +" Sum(Round(CONVERT(NUMERIC(12, 0), ( ( ( a.da) / 30 ) *  a.encash_days )), 2)) AS Da ,"
					 +" Sum(Round(CONVERT(NUMERIC(12, 0), ( ( ( a.vda) / 30 ) *  a.encash_days )), 2)) AS Vda,  "
					 +" Sum(Round(CONVERT(NUMERIC(12, 0),(((a.basic)/ 30)* a.encash_days)+ "
					 +"	 ( ( ( a.da) / 30 ) *  a.encash_days )+( ( ( a.vda) / 30 ) *  a.encash_days )), 2)) "
					 +"	       AS TOTAL_AMT,"
					 + "Sum(Round(CONVERT(NUMERIC(12, 0), ( "
					 + "            ( ( ( a.basic + a.da + a.vda ) / 30 ) * a.encash_days ))), 2)) " 
					 +" AS NET_PAY "
					 + "FROM   a, Project_Sites pst where a.PRJ_SRNO=pst.SITE_ID GROUP BY pst.SITE_ID, pst.SITE_NAME  ";
					
				
				
		}
		
		System.out.println("query is: "+SQL);
		    st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = st.executeQuery(SQL);
			
			
							HSSFRow rowtitle6=   sheet.createRow((short)(rownum++)+1);
		    				HSSFCell cell31 = rowtitle6.createCell((short) 1); 
		    				cell31.setCellValue("Employee List For Project Site is :- "+(SiteName));
		    				cell31.setCellStyle(my_style);
		    				
							HSSFRow heading1=   sheet.createRow((short)(rownum++)+1);
							for(i=0;i<results.length;i++)
							{
								sheet.setColumnWidth((short)i, (short)9000);
						
								HSSFCell cell21 = heading1.createCell((short) i);
								cell21.setCellValue(""+results[i]);
								cell21.setCellStyle(my_style);
					}	
					
					while(rs.next())
					{
						
						int k=0;
						count++;
						srno++;
						
						HSSFRow row = sheet.createRow((short)(rownum++)+1);
						for(int j=0;j<results.length;j++)
						{
						
							String ex="";
						    ex = rs.getString(results[j])==null?" ":rs.getString(results[j]);
						    row.createCell((short) k).setCellValue(""+ex);
						    k++;
						}
					
						Employee+=1;
						basic=rs.getFloat("Basic_f");
						da=rs.getFloat("Da");
						vda=rs.getFloat("Vda");
						netpay=rs.getFloat("NET_PAY");
						amt = rs.getFloat("TOTAL_AMT");
						
						/*if(rs.getString("Encash_Days").equalsIgnoreCase("0.0")){
							days+=1;	
							}*/
						
						 ftotbasic =ftotbasic+basic ;
						 ftotda =ftotda+da ;
						 ftotvda =ftotvda+vda ;
						 ftotnetpay = ftotnetpay+netpay;
						 ftotamt =ftotamt+amt;
						tdays=tdays+days;
						totbasic = totbasic + basic;
						
						totda=totda+da;
						
						totvda=totvda+vda;
						
						totnetpay=totnetpay+netpay;
						
						basic = 0.0f;
						da=0;
						vda=0;
						netpay=0;
						amt=0;
						days=0;
						totbasic = 0;
						totda=0;
						totvda=0;
						totpf=0;
						totnetpay=0;
						 
						}
				
						totalEmployee = totalEmployee+ Employee;	
						Employee = 0;

						HSSFRow row22 = sheet.createRow((short) (rownum++)+1);
					row22.createCell((short) 0).setCellValue(" ");
					row22 = sheet.createRow((short) (rownum++)+1);
					
					HSSFCell cell311 = row22.createCell((short) 1); 
					cell311.setCellValue("SUMMARY :- ");
					cell311.setCellStyle(my_style);
					HSSFCell cell3111 = row22.createCell((short) 3); 
					cell3111.setCellValue("Basic :- "+Math.round(ftotbasic  ));
					cell3111.setCellStyle(my_style);
					HSSFCell cell31111 = row22.createCell((short) 4); 
					cell31111.setCellValue("DA :- "+Math.round(ftotda  ));
					cell31111.setCellStyle(my_style);
					HSSFCell cell311111 = row22.createCell((short) 5); 
					cell311111.setCellValue("VDA :- "+Math.round(ftotvda  ));
					cell311111.setCellStyle(my_style);
					HSSFCell cell3111112 = row22.createCell((short) 6); 
					cell3111112.setCellValue("TOTAL AMT :- "+Math.round(ftotamt));
					cell3111112.setCellStyle(my_style);
					HSSFCell cell31111111 = row22.createCell((short) 7); 
					cell31111111.setCellValue("NET_PAY :- "+Math.round(ftotnetpay  ));
					cell31111111.setCellStyle(my_style);
					
					//HSSFRow row2 = sheet.createRow((short) (rownum++)+1);
					//row2.createCell((short) 0).setCellValue(" ");
					//row2 = sheet.createRow((short) (rownum++)+1);
					//row2.createCell((short) 1).setCellValue("Total Number Of Employee :- " +totalEmployee );
					hwb.write(out1);
					out1.close();
					st.close();
					st1.close();
					con.close();
	    
					System.out.println("Result OK.....");
		
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
		
		}

public static void NextIncrementReport(String type,String Desig,String Branch,String Reptype, String filePath, String imagepath) {
									  
	try
	{
		System.out.println("Into NextIncrementReport.....");
		RepoartBean repBean  = new RepoartBean();
		
		LookupHandler lkh=new LookupHandler();
		Connection con =null;
	//	System.out.println( filepath);
	FileOutputStream out1 = new FileOutputStream(new File(filePath));
	HSSFWorkbook hwb=new HSSFWorkbook();
	HSSFSheet sheet =  hwb.createSheet("Next_Increment_Report");
	
	sheet.setColumnWidth((short)0, (short)3000);
	sheet.setColumnWidth((short)1, (short)4000);
	sheet.setColumnWidth((short)2, (short)11000);
	sheet.setColumnWidth((short)3, (short)4000);
	sheet.setColumnWidth((short)4, (short)4000);
	sheet.setColumnWidth((short)5, (short)4000);
	sheet.setColumnWidth((short)6, (short)4000);
	sheet.setColumnWidth((short)7, (short)4000);
	sheet.setColumnWidth((short)8, (short)4000);
	sheet.setColumnWidth((short)9, (short)4000);
	sheet.setColumnWidth((short)10, (short)4000);
	sheet.setColumnWidth((short)11, (short)4000);
	sheet.setColumnWidth((short)12, (short)4000);
	
    HSSFCellStyle my_style = hwb.createCellStyle();
    HSSFCellStyle my_style1 = hwb.createCellStyle();

    HSSFFont my_font=hwb.createFont();
    my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    my_style.setFont(my_font);
    
    my_style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
  //  my_style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	
	HSSFRow rowtitle=   sheet.createRow((short)0);
	HSSFCell cell = rowtitle.createCell((short) 9);
	
	cell.setCellValue("THE BUSINESS CO.OP BANK LTD.");
	cell.setCellStyle(my_style);
	HSSFRow rowtitle1=   sheet.createRow((short)1);
	HSSFCell cell1 = rowtitle1.createCell((short) 7);
	cell1.setCellValue("Rajan Complex Arcade, Datta Mandir Circle, Nashik Road, Nashik - 422101");
	cell1.setCellStyle(my_style);
	HSSFRow rowtitle2=   sheet.createRow((short)2);
	HSSFCell cell2 = rowtitle2.createCell((short) 9);
	cell2.setCellValue("Tel : 0253-2406100, 2469545");
	cell2.setCellStyle(my_style);
	HSSFRow rowtitle31=   sheet.createRow((short)3);
	cell2 = rowtitle31.createCell((short) 9);
			cell2.setCellValue("");
			cell2.setCellStyle(my_style);
	HSSFRow rowtitle3=   sheet.createRow((short)4);
	cell2=rowtitle3.createCell((short) 2);
	cell2.setCellValue("");
	cell2.setCellStyle(my_style);
	HSSFRow rowtitle4=   sheet.createRow((short)5);
	rowtitle4.createCell((short) 0).setCellValue("");
	HSSFRow rowtitle5=   sheet.createRow((short)6);
	rowtitle5.createCell((short) 9).setCellValue("Next Increment Report:- ");
	/*HSSFCell cell22 = rowtitle5.createCell((short) 9);
	cell22 = rowtitle5.createCell((short) 1);
	cell22.setCellValue(("Employee Appraisal List:- "+incrtype));
	cell22.setCellStyle(my_style);*/
	
	HSSFFont blueFont = hwb.createFont();
	blueFont.setColor(HSSFColor.BLUE.index);
	
	HSSFCellStyle style = hwb.createCellStyle();
	style.setFillForegroundColor(HSSFColor.BLUE.index);
	
	HSSFRow head=   sheet.createRow((short)7);
	head.createCell((short) 0).setCellValue("");
	HSSFRow heading=   sheet.createRow((short)8);
	HSSFCell cell3 = heading.createCell((short) 0); 

	cell3.setCellValue("");
	cell3.setCellStyle(my_style1);
	HSSFRow rowhead=   sheet.createRow((short)9);
    sheet.createFreezePane( 0, 10, 0, 10 );
   
    my_style1.setAlignment((short) 2);
    my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    my_style1.setFont(my_font);
    my_style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    
	cell3=rowhead.createCell((short) 0);
	cell3.setCellValue("SR.NO");
	cell3.setCellStyle(my_style1);
	cell3=rowhead.createCell((short) 1);
	cell3.setCellValue("EMP CODE");
	cell3.setCellStyle(my_style1);
	cell3=rowhead.createCell((short) 2);
	cell3.setCellValue("EMPLOYEE NAME");
	cell3.setCellStyle(my_style1);
	cell3=rowhead.createCell((short) 3);
	cell3.setCellValue("Desig");
	cell3.setCellStyle(my_style1);
	cell3=rowhead.createCell((short) 4);
	cell3.setCellValue("Site Name");
	cell3.setCellStyle(my_style1);
	
	cell3=rowhead.createCell((short) 5);
	cell3.setCellValue("Curr Basic");
	cell3.setCellStyle(my_style1);
	cell3=rowhead.createCell((short) 6);
	cell3.setCellValue("Curr DA");
	cell3.setCellStyle(my_style1);
	cell3=rowhead.createCell((short) 7);
	cell3.setCellValue("Curr HRA");
	cell3.setCellStyle(my_style1);
	
	cell3=rowhead.createCell((short)8);
	cell3.setCellValue("Curr VDA");
	cell3.setCellStyle(my_style1);

	cell3=rowhead.createCell((short) 9);
	cell3.setCellValue("New Basic");
	cell3.setCellStyle(my_style1);
	cell3=rowhead.createCell((short) 10);
	cell3.setCellValue("New DA");
	cell3.setCellStyle(my_style1);
	
	cell3=rowhead.createCell((short) 11);
	cell3.setCellValue("New HRA");
	cell3.setCellStyle(my_style1);
	
	cell3=rowhead.createCell((short) 12);
	cell3.setCellValue("New VDA");
	cell3.setCellStyle(my_style1);
	cell3=rowhead.createCell((short) 13);
	cell3.setCellValue("Basic Diff");
	cell3.setCellStyle(my_style1);
	
	cell3=rowhead.createCell((short) 14);
	cell3.setCellValue("DA Diff");
	cell3.setCellStyle(my_style1);
	cell3=rowhead.createCell((short) 15);
	cell3.setCellValue("HRA Diff");
	cell3.setCellStyle(my_style1);
	cell3=rowhead.createCell((short) 16);
	cell3.setCellValue("VDA Diff");
	cell3.setCellStyle(my_style1);
	
	ReportDAO.OpenCon("", "", "",repBean);
	con = repBean.getCn();	
	Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	ResultSet rs = null;
	Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	ResultSet rs1 = null;
	
		String SQL = "";
	int i=12;
	String constant="";
	if(type.equalsIgnoreCase("0")){
		constant="";
	}else if(type.equalsIgnoreCase("1")){
		constant=" and t.PRJ_SRNO='"+Branch+"'";
	}else if(type.equalsIgnoreCase("2")){
		constant=" and t.DESIG='"+Desig+"'";
	}
	int count=0;
	/*SQL = ""
			+ "with a as( "
			+ "select e.EMPNO, e.EMPCODE, "
			+ "e.SALUTE,RTRIM(e.FNAME)+' '+RTRIM(e.MNAME)+' '+RTRIM(e.lname) AS NAME, "
			+ "t.DESIG,t.GRADE ,l.LKP_DISC as desi ,p.SITE_NAME, "
			+ "(select INP_AMT from paytran p where trncd=101 and p.EMPNO=e.EMPNO)as basic, "
			+ "(select INP_AMT from paytran p where trncd=102 and p.EMPNO=e.EMPNO)as da, "
			+ "(select INP_AMT from paytran p where trncd=103 and p.EMPNO=e.EMPNO)as hra, "
			+ "(select INP_AMT from paytran p where trncd=138 and p.EMPNO=e.EMPNO)as vda "
//			+ "ea.INCREMENT_RELEASE "
//			+ "from empmast e ,emptran t,EMP_APPARAISAL ea, "
			+ "from empmast e ,emptran t, "
			+ "LOOKUP l,Project_Sites p "
			+ "where e.STATUS='A' "
			+ "and e.EMPNO=t.EMPNO "
			+ "and t.SRNO=(select MAX(SRNO) from emptran a where a.EMPNO=e.EMPNO) "
//			+ "and ea.EMPNO=e.EMPNO "
			+ "and t.DESIG=l.LKP_SRNO "
			+ "and l.LKP_CODE='desig' "
			+ "and t.PRJ_SRNO=p.SITE_ID "
//			+ "and ea.APPARAISAL_DATE between '"+frmdate+"' and '"+todate+"' "+constant+" "
//			+ "and ea.INCREMENT_RELEASE='"+incrtype+"'"
			+ " "+constant+" "
			+ ") "
			+ "select a.*,g.SRNO,g.START_DATE,g.GRADE_CODE from a,GRADE_MASTER g "
			+ "where a.GRADE=g.GRADE_CODE "
			+ "and g.START_DATE=(select MAX(START_DATE) from GRADE_MASTER s where s.GRADE_CODE=g.GRADE_CODE) "
			+ "and a.basic=g.BASIC "
			+ "order by a.EMPCODE";
			eeeeeeeeeeeeeeeeeeeeeeeee*/
			
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat dateOnly = new SimpleDateFormat("yyyy-MM-dd");
	String date=dateOnly.format(cal.getTime());
	String [] year = date.split("-");

	
	
	 SQL = ""
			+ "WITH a "
			+ "     AS (SELECT e.empno, "
			+ "                e.empcode, "
			+ "                e.salute, "
			+ "                Rtrim(e.fname) + ' ' + Rtrim(e.mname) + ' ' "
			+ "                + Rtrim(e.lname)              AS NAME, "
			+ "                t.desig, "
			+ "                t.grade, "
			+ "                l.lkp_disc                    AS desi, "
			+ "                p.site_name, "
			+ "                (SELECT inp_amt "
			+ "                 FROM   paytran p "
			+ "                 WHERE  trncd = 101 "
			+ "                        AND p.empno = e.empno)AS basic, "
			+ "                (SELECT inp_amt "
			+ "                 FROM   paytran p "
			+ "                 WHERE  trncd = 102 "
			+ "                        AND p.empno = e.empno)AS da, "
			+ "                (SELECT inp_amt "
			+ "                 FROM   paytran p "
			+ "                 WHERE  trncd = 103 "
			+ "                        AND p.empno = e.empno)AS hra, "
			+ "                (SELECT inp_amt "
			+ "                 FROM   paytran p "
			+ "                 WHERE  trncd = 138 "
			+ "                        AND p.empno = e.empno)AS vda, "
			+ "                  (SELECT inp_amt "
			+ "                 FROM   YTDTRAN y "
			+ "                 WHERE  trncd = 101 "
			+ "                        AND y.empno = e.empno and y.trndt='"+year[0]+"-04-30')as oldbasic, "
			+ "                 (SELECT inp_amt "
			+ "                 FROM    YTDTRAN y "
			+ "                 WHERE  trncd = 102 "
			+ "                        AND y.empno = e.empno and y.trndt='"+year[0]+"-04-30')AS oldda, "
			+ "                 (SELECT inp_amt "
			+ "                 FROM     YTDTRAN y "
			+ "                 WHERE  trncd = 103 "
			+ "                        AND y.empno = e.empno and y.trndt='"+year[0]+"-04-30')AS oldhra, "
			+ "                 (SELECT inp_amt "
			+ "                 FROM    YTDTRAN y "
			+ "                 WHERE  trncd = 138 "
			+ "                        AND y.empno = e.empno and y.trndt='"+year[0]+"-04-30')AS oldvda "
			+ "         FROM   empmast e, "
			+ "                emptran t, "
			+ "                lookup l, "
			+ "                project_sites p "
			+ "         WHERE  e.status = 'A' "
			+ "                AND e.empno = t.empno "
			+ "                AND t.srno = (SELECT Max(srno) "
			+ "                              FROM   emptran a "
			+ "                              WHERE  a.empno = e.empno) "
			+ "                AND t.desig = l.lkp_srno "
			+ "                AND l.lkp_code = 'desig' "
			+ "                AND t.prj_srno = p.site_id "
			+ " 				"+constant+" "
			+ ") "
			+ "SELECT a.*, "
			+ "       g.srno, "
			+ "       g.start_date, "
			+ "       g.grade_code "
			+ "FROM   a, "
			+ "       grade_master g "
			+ "WHERE  a.grade = g.grade_code "
			+ "       AND g.start_date = (SELECT Max(start_date) "
			+ "                           FROM   grade_master s "
			+ "                           WHERE  s.grade_code = g.grade_code) "
			+ "       AND a.basic = g.basic "
			+ "ORDER  BY a.empcode";

	System.out.println("query for details  "+SQL);
	st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	rs = st.executeQuery(SQL);
	String StartDT="";
	int SRNO=0,Grade=0;
	int srno=0;
	float  nbasic=0,nda=0,nhra=0,nvda=0;
	float basicDiff=0, daDiff=0,hraDiff=0,vdaDiff=0;
	float  currtbasic=0,currtda=0,currthra=0,currtvda=0;
	float  newtbasic=0,newtda=0,newthra=0,newtvda=0;
	float totbasicDiff=0, totdaDiff=0,tothraDiff=0,totvdaDiff=0;
	Integer totatlBonus = 0;
	String Query1="";
	while(rs.next())
	{
		StartDT=rs.getString("START_DATE");
		
		/*nbasic=rs1.getFloat("basic");
		nda=rs1.getFloat("da");
		nvda=rs1.getFloat("vda");
		nhra=rs1.getFloat("hra");*/
		/*SRNO=rs.getInt("srno");
		SRNO=SRNO+1;
		Grade=rs.getInt("GRADE_CODE");
		Query1=""
		+ "select * from GRADE_MASTER "
		+ "where GRADE_CODE="+Grade+" and START_DATE='"+StartDT+"' and SRNO="+SRNO+"";
		st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		System.out.println("Query1: "+Query1);
		rs1 = st1.executeQuery(Query1);
		while (rs1.next())
		{
			nbasic=rs1.getFloat("basic");
			nda=rs1.getFloat("da");
			nvda=rs1.getFloat("vda");
			nhra=rs1.getFloat("hra");
		}*/
		
		count++;
		srno++;
		HSSFRow row = sheet.createRow((short)i++);
		
		row.createCell((short) 0).setCellValue(""+srno);
		row.createCell((short) 1).setCellValue(""+rs.getString("empcode"));
		row.createCell((short) 2).setCellValue(""+rs.getString("NAME"));
		row.createCell((short) 3).setCellValue(""+(rs.getString("desi")));
		row.createCell((short) 4).setCellValue(""+rs.getString("SITE_NAME"));
		row.createCell((short) 5).setCellValue(""+rs.getString("oldbasic"));
		row.createCell((short) 6).setCellValue(""+(rs.getString("oldda")));
		row.createCell((short) 7).setCellValue(""+(rs.getString("oldhra")));
		row.createCell((short) 8).setCellValue(""+rs.getString("oldvda"));
		row.createCell((short) 9).setCellValue(""+ rs.getFloat("basic"));
		row.createCell((short) 10).setCellValue(""+ rs.getFloat("da"));
		row.createCell((short) 11).setCellValue(""+ rs.getFloat("hra"));
		row.createCell((short) 12).setCellValue(""+ rs.getFloat("vda"));
		
		
		 basicDiff= ( rs.getFloat("basic") - rs.getFloat("oldbasic"));
		 daDiff= (rs.getFloat("da")- rs.getFloat("oldda"));
		 hraDiff= ( rs.getFloat("hra") - rs.getFloat("oldhra"));
		 vdaDiff= (rs.getFloat("vda") - rs.getFloat("oldvda"));
		
		
		
		
			row.createCell((short) 13).setCellValue(""+basicDiff);
			row.createCell((short) 14).setCellValue(""+daDiff);
			row.createCell((short) 15).setCellValue(""+hraDiff);
			row.createCell((short) 16).setCellValue(""+vdaDiff);
		
		SRNO=0;
		currtbasic =currtbasic+ rs.getFloat("oldbasic");
		currtda = currtda + rs.getFloat("oldda");
		currthra = currthra + rs.getFloat("oldhra");
		currtvda = currtvda + rs.getFloat("oldvda");
		
		newtbasic =newtbasic + rs.getFloat("basic") ;
		newtda = newtda + rs.getFloat("da");
		newthra = newthra + rs.getFloat("hra");
		newtvda = newtvda + rs.getFloat("vda");
		
		totbasicDiff =totbasicDiff + basicDiff;
		totdaDiff = totdaDiff + daDiff ;
		tothraDiff = tothraDiff + hraDiff;
		totvdaDiff =totvdaDiff+ vdaDiff;
		
	}
	HSSFRow row = sheet.createRow((short) i);
	cell3=row.createCell((short) 1);
	cell3.setCellValue(" ");
	cell3.setCellStyle(my_style1);
	row = sheet.createRow((short) i+1);
	
	cell3=row.createCell((short) 4);
	cell3.setCellValue("TOTAL  : =  ");
	cell3.setCellStyle(my_style1);
	
	cell3=row.createCell((short) 5);
	cell3.setCellValue(currtbasic);
	cell3.setCellStyle(my_style1);
	
	cell3=row.createCell((short) 6);
	cell3.setCellValue(currtda);
	cell3.setCellStyle(my_style1);
	
	cell3=row.createCell((short) 7);
	cell3.setCellValue(currthra);
	cell3.setCellStyle(my_style1);
	
	cell3=row.createCell((short) 8);
	cell3.setCellValue(currtvda);
	cell3.setCellStyle(my_style1);
	
	cell3=row.createCell((short) 9);
	cell3.setCellValue(newtbasic);
	cell3.setCellStyle(my_style1);
	
	cell3=row.createCell((short) 10);
	cell3.setCellValue(newtda);
	cell3.setCellStyle(my_style1);
	
	cell3=row.createCell((short) 11);
	cell3.setCellValue(newthra);
	cell3.setCellStyle(my_style1);
	
	cell3=row.createCell((short) 12);
	cell3.setCellValue(newtvda);
	cell3.setCellStyle(my_style1);
	
	cell3=row.createCell((short) 13);
	cell3.setCellValue(totbasicDiff);
	cell3.setCellStyle(my_style1);
	
	cell3=row.createCell((short) 14);
	cell3.setCellValue(totdaDiff);
	cell3.setCellStyle(my_style1);
	
	cell3=row.createCell((short) 15);
	cell3.setCellValue(tothraDiff);
	cell3.setCellStyle(my_style1);
	
	cell3=row.createCell((short) 16);
	cell3.setCellValue(totvdaDiff);
	cell3.setCellStyle(my_style1);
	
	
	
	HSSFRow row1 = sheet.createRow((short) i);
	row1.createCell((short) 0).setCellValue(" ");
	  row1 = sheet.createRow((short) i+1);
	
	row1.createCell((short) 0).setCellValue("Total Employee : =  " +(count++) );
	
	
	hwb.write(out1);
	out1.close();
	st.close();
    con.close();

	}
	
	catch(Exception e)
	{
		e.printStackTrace();
	}
}


//Govi
public static void payregWithBranchXls(String PAYREGDT, String imagepath,String filepath, String type) {
	//vrushali
	
	int i = 1;
	int BranchTotalempno = 0;
	int totalempno = 0;

	// income variables
	float basic = 0;
	float da = 0;
	float vda = 0;
	float hra = 0;
	float Allowances = 0;
	float Arrears = 0;
	float Arrears_HRA = 0;
	float Arrears_BDV = 0;
	float totalEarn = 0;

	// total income variables
	long daTotal = 0;
	long vdaTotal = 0;
	long basicTotal = 0;
	long hraTotal = 0;
	long allowtotal = 0;
	long arreartotal = 0;
	long arrearHRAtotal = 0;
	long arrearBDVtotal = 0;
	long total = 0;

	// deduction variables
	float stamp = 0;
	float union = 0;
	float gi = 0;
	float pt = 0;
	float lic = 0;
	float pf = 0;
	float society = 0;
	float loan = 0;
	float itax = 0;
	float MLWF = 0;
	float sdf = 0;
	float deduction = 0;
	float netpay = 0;

	// total deduction variables
	long stamptotal = 0;
	long uniontotal = 0;
	long gitotal = 0;
	long pttotal = 0;
	long lictotal = 0;
	long pftotal = 0;
	long societytotal = 0;
	long loantotal = 0;
	long itaxtotal = 0;
	long MLWFtotal = 0;
	long sdftotal = 0;
	long deductiontotal = 0;
	long netpaytotal = 0;
	RepoartBean repBean  = new RepoartBean();
	
	Connection con =null;
	String BomDt = "";
	String EomDt = "";
	int lastdat = 0;
	//String dt = PAYREGDT;
	String table_name = null;
	/*if(TName.equals("before") )
	{
	table_name = "PAYTRAN";
	lable1="Before Finalize";
	}
	else if(TName.equals("after") )
	{
		table_name="YTDTRAN";
		lable1="Final After Release";
	}
	else
	{
		table_name="PAYTRAN_STAGE";
		lable1="Pending for Release";
	}*/
	
	lastdat = (int) Calculate.getDays(PAYREGDT);
	System.out.println("maxdt"+lastdat);
	BomDt = ReportDAO.BOM(PAYREGDT);
	//System.out.println("bomdt "+BomDt);
	EomDt = ReportDAO.EOM(PAYREGDT);
	//System.out.println("eomdt"+EomDt);
	
	String temp = PAYREGDT.substring(3);
	ResultSet emp = null;
	String EmpSql = ""; 
	String pBrcd1 = "";
	
		
	float basic_total = 0.0f;
	float lstbasic_total = 0.0f;
	float vda_total = 0.0f;
	float lstvda_total = 0.0f;
	float currda_total = 0.0f;
	float lstcurrda_total = 0.0f;
	float hra_total = 0.0f;
	float lsthra_total = 0.0f;
	float medical_total = 0.0f;
	float lstmedical_total = 0.0f;
	float edu_total = 0.0f;
	float lstedu_total = 0.0f;
	float splall_total = 0.0f;
	float lstsplall_total = 0.0f;
	float convall_total = 0.0f;
	float lstconvall_total = 0.0f;	
	float arrears_HRA_total = 0.0f;
	float lstarrears_HRA_total = 0.0f;	
	float arrears_BDV_total = 0.0f;
	float lstarrears_BDV_total = 0.0f;
	float Earning_total = 0.0f;
	float lstEarning_total = 0.0f;   
	float subUnion_total = 0.0f;
	float lstsubUnion_total = 0.0f;
	float PF_total = 0.0f;
	float lstPF_total = 0.0f;
	float special_total = 0.0f;
	float lstspecial_total = 0.0f;
	float GI_total = 0.0f;
	float lstGI_total = 0.0f;
	float PT_total = 0.0f;
	float lstPT_total = 0.0f;
	float totded_total = 0.0f;
	float lsttotded_total = 0.0f;
	float Society_total = 0.0f;
	float lstSociety_total = 0.0f;
	float LIC_total=0.0f;
	float lstLIC_total=0.0f;
	float LOANS_total = 0.0f;
	float lstLOANS_total = 0.0f;
	float sdf_total=0.0f;
	float lstsdf_total=0.0f;	
	float ITAX_total = 0.0f;
	float lstITAX_total = 0.0f;
	float MLWF_total = 0.0f;
	float lstMLWF_total = 0.0f;
	float netPay_total = 0.0f;
	float lstnetPay_total = 0.0f;
	float deduction_total = 0.0f;
	float lstdeduction_total = 0.0f;
	
	float dept_total = 0.0f;
	float lstdept_total = 0.0f;
	
	float otherall_total = 0.0f;
	float lstotherall_total = 0.0f;
	
	float addincome_total = 0.0f;
	float lstaddincome_total = 0.0f;
	
	float MEDICLAIM_total = 0.0f;
	float lstMEDICLAIM_total = 0.0f;
	
	float OtherDed_total = 0.0f;
	float lstOtherDed_total = 0.0f;
	
	float MembDed_total = 0.0f;
	float lstMembDed_total = 0.0f;
	
	float tds_total = 0.0f;
	float lsttds_total = 0.0f;
	
	float BankInst_total = 0.0f;
	float lstBankInst_total = 0.0f;
	
	float OtherLoan_total = 0.0f;
	float lstOtherLoan_total = 0.0f;
	
	float otherded_total = 0.0f;
	float lstotherded_total = 0.0f;


	int sr=0;
	int totalemp=0;
	int etot=0;
	
	try
	{

			ReportDAO.OpenCon("", "", "",repBean);
			con = repBean.getCn();	
			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			
			System.out.println("in income head");
			FileOutputStream out1 = new FileOutputStream(new File(filepath));
			
			HSSFWorkbook hwb=new HSSFWorkbook();
			HSSFSheet sheet =  hwb.createSheet("PayRegister");
			Calendar currentMonth = Calendar.getInstance();
	        
	      
	        HSSFCellStyle my_style = hwb.createCellStyle();
	        HSSFCellStyle my_style1 = hwb.createCellStyle();

	        HSSFFont my_font=hwb.createFont();
	        my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        my_style.setFont(my_font);        
	        
	       
	        HSSFRow rowtitle=   sheet.createRow((short)0);
			HSSFCell cell = rowtitle.createCell((short) 5);
			cell.setCellValue("THE BUSINESS CO.OP BANK LTD.");
			cell.setCellStyle(my_style);
			
			HSSFRow rowtitle1=   sheet.createRow((short)1);
			HSSFCell cell1 = rowtitle1.createCell((short) 3);
			cell1.setCellValue("Rajan Complex Arcade, Datta Mandir, Nashik Road, Nashik - 422101");
			cell1.setCellStyle(my_style);
			
			HSSFRow rowtitle2=   sheet.createRow((short)2);
			HSSFCell cell2 = rowtitle2.createCell((short) 5);
			cell2.setCellValue("Tel : +91-20 26812190");
			cell2.setCellStyle(my_style);
			HSSFRow rowtitle31=   sheet.createRow((short)3);
			cell2 = rowtitle31.createCell((short) 5);
					cell2.setCellValue("Email : ");
					cell2.setCellStyle(my_style);
			HSSFRow rowtitle3=   sheet.createRow((short)4);
			cell2=rowtitle3.createCell((short) 5);
			cell2.setCellValue("Salary Sheet For The Month Of :- "+PAYREGDT);
			cell2.setCellStyle(my_style);
			HSSFRow rowtitle4=   sheet.createRow((short)5);
			rowtitle4.createCell((short) 0).setCellValue("");
			HSSFRow rowtitle5=   sheet.createRow((short)6);
			rowtitle5.createCell((short) 0).setCellValue("");
			
			HSSFFont blueFont = hwb.createFont();
			blueFont.setColor(HSSFColor.BLUE.index);
			
			HSSFCellStyle style = hwb.createCellStyle();
			
			style.setFillForegroundColor(HSSFColor.BLUE.index);
			
		
	
			
			/*EmpSql = "			with empcount as ( SELECT count(e.EMPNO) as emptotal,t.site_name,  et.prj_srno    AS siteId  "
					+ "			FROM   emptran et, empmast e,  project_sites t  WHERE  e.empno = et.empno  AND t.site_id = et.prj_srno "
					+ "			AND et.srno = (SELECT Max(srno)  FROM   emptran  WHERE  empno = E.empno)  AND ( ( E.status = 'A'  "
					+ "			AND E.doj <= '" + ReportDAO.EOM(PAYREGDT)
					+ "' )   OR ( E.status = 'N'  AND E.dol >= '" + ReportDAO.BOM(PAYREGDT)
					+ "' ) )   group by t.site_name, "
					+ "              et.prj_srno ) select * from ( select site_name,cnt.siteid,cnt.emptotal,"
					+ "			 SUM( case trncd when 101 then net_amt else 0 end ) as basic,"
					+ "			 SUM( case when trncd = 102 then net_amt else 0 end ) as da,"
					+ "       	 SUM( case when trncd = 138 then net_amt else 0 end ) as vda,"
					+ "			 SUM( case when trncd = 103 then net_amt else 0 end ) as hra,"
					+ "			 SUM( case when trncd IN (105,104,107,108,129)  then net_amt else 0  end )as allowances,"
					+
					// " SUM( case when trncd IN (142,118,140) then net_amt
					// else 0 end ) as Arrears," +
					"			 SUM( case when trncd IN (140)  then net_amt else 0  end ) as Arrears_HRA,"
					+ "			 SUM( case when trncd IN (142)  then net_amt else 0  end ) as Arrears_BDV,"
					+ "			 SUM( case when trncd= 999 THEN inp_amt else 0  end ) as Earning," +
					// " SUM( case when trncd =250 then net_amt else 0 end )
					// as SDep," +
					"			 SUM( case when trncd =242    then net_amt else 0  end ) as subUnion,"
					+ "			 SUM( case when trncd =207    then net_amt else 0  end ) as GI,"
					+ "			 SUM( case when trncd =202    then net_amt else 0  end ) as PT,"
					+ "			 SUM( case when trncd =205    then net_amt else 0  end ) as LIC,"
					+ "			 SUM( case when trncd =201    then net_amt else 0  end ) as PF,"
					+ "			 SUM( case when trncd =206    then net_amt else 0  end ) as Society,"
					+ "			 SUM( case when trncd in  (212,226,255,216,247,248,245,246,244,249)    then net_amt else 0  end ) as LOANS,"
					+ "			 SUM( case when trncd =228    then net_amt else 0  end ) as ITAX,"
					+ "			 SUM( case when trncd =(CASE  WHEN  month('" + ReportDAO.EOM(PAYREGDT)
					+ "') = 5 THEN 220 ELSE 211 END)    then net_amt else 0  end ) as MLWF,"
					+ "			 SUM( case when trncd =230    then net_amt else 0  end ) as sdf,"
					+ "			 SUM( case when trncd= 999 THEN cal_amt   else 0  end ) as deduction,"
					+ "			 SUM( case when trncd=  999  then net_amt else 0  end ) as netPay"
					+ "			from (	select p.trncd as trncd,SUM(p.net_amt) as net_amt , Sum(p.INP_AMT ) AS inp_amt,Sum(p.CAL_AMT ) AS cal_amt,et.prj_srno as siteId"
					+ "       	from " + type + " p, 	EMPTRAN et,empmast e ,Project_Sites t where		"
					+ "    	e.EMPNO=et.EMPNO and et.EMPNO=p.empno and t.SITE_ID=et.PRJ_SRNO"
					+ "		and p.TRNdt between '" + ReportDAO.BOM(PAYREGDT) + "' and '" + ReportDAO.EOM(PAYREGDT)
					+ "' "
					+ "   	AND et.SRNO=(SELECT MAX(SRNO) FROM EMPTRAN WHERE EMPNO=E.EMPNO) AND (( E.STATUS='A' AND E.DOJ <= '"
					+ ReportDAO.EOM(PAYREGDT) + "') " + "		 or (E.STATUS ='N' And  E.DOL>='"
					+ ReportDAO.BOM(PAYREGDT) + "' )) and p.TRNCD not in(301,302) "
					+ "		group by p.trncd, et.PRJ_SRNO, t.SITE_NAME	) as sss  ,empcount cnt where  cnt.siteId = sss.siteId   	group by cnt.siteId,site_name,emptotal  ) cnt where cnt.basic!=0.00";
*/
			
				EmpSql="with empcount AS "
						+ "( "
						+ "         SELECT   count(e.empno) AS emptotal, "
						+ "                  t.site_name, "
						+ "                  et.prj_srno AS siteid "
						+ "         FROM     emptran et, "
						+ "                  empmast e, "
						+ "                  project_sites t "
						+ "         WHERE    e.empno = et.empno "
						+ "         AND      t.site_id = et.prj_srno "
						+ "         AND      et.srno = "
						+ "                  ( "
						+ "                         SELECT max(srno) "
						+ "                         FROM   emptran "
						+ "                         WHERE  empno = e.empno) "
						+ "         AND      ( ( "
						+ "                                    e.status = 'A' "
						+ "                           AND      e.doj <= '"+ ReportDAO.EOM(PAYREGDT)+"' ) "
						+ "                  OR       ( "
						+ "                                    e.status = 'N' "
						+ "                           AND      e.dol >= '"+ ReportDAO.BOM(PAYREGDT)+"' ) ) "
						+ "         GROUP BY t.site_name, "
						+ "                  et.prj_srno )SELECT * "
						+ "FROM   ( "
						+ "                SELECT   site_name, "
						+ "                         cnt.siteid, "
						+ "                         cnt.emptotal, "
						+ "                         Sum( "
						+ "                         CASE trncd "
						+ "                                  WHEN 101 THEN net_amt "
						+ "                                  ELSE 0 "
						+ "                         END ) AS basic, "
						+ "                         Sum( "
						+ "                         CASE "
						+ "                                  WHEN trncd = 102 THEN net_amt "
						+ "                                  ELSE 0 "
						+ "                         END ) AS da, "
						+ "                          "
						+ "                         Sum( "
						+ "                         CASE "
						+ "                                  WHEN trncd = 103 THEN net_amt "
						+ "                                  ELSE 0 "
						+ "                         END ) AS hra, "
						+ "                          "
						+ "                          Sum( "
						+ "                         CASE "
						+ "                                  WHEN trncd = 107 THEN net_amt "
						+ "                                  ELSE 0 "
						+ "                         END ) AS Dept_Allw, "
						+ "                          "
						+ "                           Sum( "
						+ "                         CASE "
						+ "                                  WHEN trncd = 108 THEN net_amt "
						+ "                                  ELSE 0 "
						+ "                         END ) AS Convy_Allw, "
						+ "                          "
						+ "                           Sum( "
						+ "                         CASE "
						+ "                                  WHEN trncd = 129 THEN net_amt "
						+ "                                  ELSE 0 "
						+ "                         END ) AS Other_Allw, "
						+ "                         "
						+ "                          Sum( "
						+ "                         CASE "
						+ "                                  WHEN trncd = 132 THEN net_amt "
						+ "                                  ELSE 0 "
						+ "                         END ) AS AnyOther_Allw, "
						+ "                         "
						+ "                         "
						+ "                        "
						+ "                          Sum( "
						+ "                         CASE "
						+ "                                  WHEN trncd = 999 THEN net_amt "
						+ "                                  ELSE 0 "
						+ "                         END ) AS TotalEarning, "
						+ "                         "
						+ "                         "
						+ "                        "
						+ "                       Sum( "
						+ "                         CASE "
						+ "                                  WHEN trncd =201 THEN net_amt "
						+ "                                  ELSE 0 "
						+ "                         END ) AS PF, "
						+ "                        "
						+ "                         Sum( "
						+ "                         CASE "
						+ "                                  WHEN trncd =202 THEN net_amt "
						+ "                                  ELSE 0 "
						+ "                         END ) AS PT, "
						+ "                         Sum( "
						+ "                         CASE "
						+ "                                  WHEN trncd =205 THEN net_amt "
						+ "                                  ELSE 0 "
						+ "                         END ) AS LIC, "
						+ "                        "
						+ "                       Sum( "
						+ "                         CASE "
						+ "                                  WHEN trncd =212 THEN net_amt "
						+ "                                  ELSE 0 "
						+ "                         END ) AS MEDICLAIM, "
						+ "                          "
						+ "                          Sum( "
						+ "                         CASE "
						+ "                                  WHEN trncd =213 THEN net_amt "
						+ "                                  ELSE 0 "
						+ "                         END ) AS Other_Ded, "
						+ "                          "
						+ "                          Sum( "
						+ "                         CASE "
						+ "                                  WHEN trncd =223 THEN net_amt "
						+ "                                  ELSE 0 "
						+ "                         END ) AS Memb_Ded, "
						+ "                          "
						+ "                          Sum( "
						+ "                         CASE "
						+ "                                  WHEN trncd =228 THEN net_amt "
						+ "                                  ELSE 0 "
						+ "                         END ) AS TDS, "
						+ "                        "
						+ "                         Sum( "
						+ "                         CASE "
						+ "                                  WHEN trncd =( "
						+ "                                           CASE "
						+ "                                                    WHEN Month('"+ReportDAO.EOM(PAYREGDT)+"') = 5 THEN 220 "
						+ "                                                    ELSE 211 "
						+ "                                           END) THEN net_amt "
						+ "                                  ELSE 0 "
						+ "                         END ) AS MLWF, "
						+ "                          "
						+ "                          "
						+ "                           Sum( "
						+ "                         CASE "
						+ "                                  WHEN trncd =248 THEN net_amt "
						+ "                                  ELSE 0 "
						+ "                         END ) AS Bank_Inst, "
						+ "                          "
						+ "                         Sum( "
						+ "                         CASE "
						+ "                                  WHEN trncd =226 THEN net_amt "
						+ "                                  ELSE 0 "
						+ "                         END ) AS Other_Loan, "
						+ "                         Sum( "
						+ "                         CASE "
						+ "                                  WHEN trncd NOT IN ( 201, "
						+ "                                                     202, "
						+ "                                                     205, "
						+ "                                                     206, "
						+ "                                                     207, "
						+ "                                                     211, "
						+ "                                                     212, "
						+ "                                                     216, "
						+ "                                                     226, "
						+ "                                                     228, "
						+ "                                                     230, "
						+ "                                                     242, "
						+ "                                                     243, "
						+ "                                                     244, "
						+ "                                                     245, "
						+ "                                                     246, "
						+ "                                                     247, "
						+ "                                                     248, "
						+ "                                                     250 ) THEN net_amt "
						+ "                                  ELSE 0 "
						+ "                         END) AS other, "
						+ "                          "
						+ "                          Sum( "
						+ "                         CASE "
						+ "                                  WHEN trncd = 999 THEN cal_amt "
						+ "                                  ELSE 0 "
						+ "                         END) AS Total_Deduction, "
						+ "                       "
						+ "                         Sum( "
						+ "                         CASE "
						+ "                                  WHEN trncd= 999 THEN net_amt "
						+ "                                  ELSE 0 "
						+ "                         END ) AS netPay "
						+ "                FROM     ( "
						+ "                                  SELECT   p.trncd         AS trncd, "
						+ "                                           Sum(p.net_amt)  AS net_amt , "
						+ "                                           Sum(p.inp_amt ) AS inp_amt, "
						+ "                                           Sum(p.cal_amt ) AS cal_amt, "
						+ "                                           et.prj_srno     AS siteId "
						+ "                                  FROM     "+type+" p, "
						+ "                                           emptran et, "
						+ "                                           empmast e , "
						+ "                                           project_sites t "
						+ "                                  WHERE    e.empno=et.empno "
						+ "                                  AND      et.empno=p.empno "
						+ "                                  AND      t.site_id=et.prj_srno "
						+ "                                  AND      p.trndt BETWEEN '"+ReportDAO.BOM(PAYREGDT)+"' AND      '"+ ReportDAO.EOM(PAYREGDT)+"' "
						+ "                                  AND      et.srno= "
						+ "                                           ( "
						+ "                                                  SELECT Max(srno) "
						+ "                                                  FROM   emptran "
						+ "                                                  WHERE  empno=E.empno) "
						+ "                                  AND      (( "
						+ "                                                             E.status='A' "
						+ "                                                    AND      E.doj <= '"+ReportDAO.EOM(PAYREGDT)+"') "
						+ "                                           OR       ( "
						+ "                                                             E.status ='N' "
						+ "                                                    AND      E.dol>='"+ ReportDAO.BOM(PAYREGDT)+"' )) "
						+ "                                  AND      p.trncd NOT IN(301,302) "
						+ "                                  GROUP BY p.trncd, "
						+ "                                           et.prj_srno, "
						+ "                                           t.site_name ) AS sss , "
						+ "                         empcount cnt "
						+ "                WHERE    cnt.siteid = sss.siteid "
						+ "                GROUP BY cnt.siteid, "
						+ "                         site_name, "
						+ "                         emptotal ) cnt "
						+ "WHERE  cnt.basic!=0.00";
				System.out.println(EmpSql);
			ResultSet rs = st.executeQuery(EmpSql);
			
				while(rs.next()){
				
		    	 Connection conn = ConnectionManager.getConnectionTech();
				Statement stmt = conn.createStatement();
								
		    	 HSSFRow head1=   sheet.createRow((short)i++);
		    	 head1 = sheet.createRow((short)i++);
		    	 head1=   sheet.createRow((short)i++);
		    	 head1=   sheet.createRow((short)i++);
		    	 head1=   sheet.createRow((short)i++);
		    	 head1=   sheet.createRow((short)i++);
		    	 
					HSSFCell cell4 = head1 .createCell((short) 0); 
					cell4.setCellStyle(my_style);
		    	 
					sheet.setColumnWidth((short)0, (short)7000);
					sheet.setColumnWidth((short)1, (short)2000);
					sheet.setColumnWidth((short)4, (short)3000);
					sheet.setColumnWidth((short)5, (short)3000);
					sheet.setColumnWidth((short)6, (short)3000);
					sheet.setColumnWidth((short)7, (short)3000);
					sheet.setColumnWidth((short)8, (short)3000);
					sheet.setColumnWidth((short)9, (short)3000);
					sheet.setColumnWidth((short)10, (short)3000);
					sheet.setColumnWidth((short)11, (short)3000);
					sheet.setColumnWidth((short)12, (short)3000);
					sheet.setColumnWidth((short)13, (short)3000);
					sheet.setColumnWidth((short)14, (short)3000);
					sheet.setColumnWidth((short)15, (short)3000);
					sheet.setColumnWidth((short)16, (short)3000);
					sheet.setColumnWidth((short)17, (short)3000);
					sheet.setColumnWidth((short)18, (short)3000);
					sheet.setColumnWidth((short)19, (short)3000);
					sheet.setColumnWidth((short)20, (short)3000);
					sheet.setColumnWidth((short)21, (short)3000);
					//sheet.setColumnWidth((short)22, (short)3000);

		    	
		    	 stmt.close();
		    	 
		     	 	head1=   sheet.createRow((short)i++);
		     	 	
		     	 	cell4 = head1 .createCell((short) 0);   
					cell4.setCellValue("SR NO");
					cell4.setCellStyle(my_style);
		     	 	
				 	cell4 = head1 .createCell((short) 1);   
					cell4.setCellValue("Branch Name");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 2); 
					cell4.setCellValue("No.Of Emp ");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 3); 
					cell4.setCellValue("Basic");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 4); 
					cell4.setCellValue("D.A");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 5); 
					cell4.setCellValue("Dept Allw");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 6); 
					cell4.setCellValue("Conv Allw");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 7); 
					cell4.setCellValue("Other Spcl Allw");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 8); 
					cell4.setCellValue("Add Income");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 9); 
					/*cell4.setCellValue("Total Earn");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 9); */
					cell4.setCellValue("Total Earn");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 10); 
					cell4.setCellValue("P.F");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 11); 
					cell4.setCellValue("P.T");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 12); 
					cell4.setCellValue("L.I.C");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 13); 
					cell4.setCellValue("Mediclaim");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 14); 
					cell4.setCellValue("Other Ded");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 15); 
					cell4.setCellValue("Memb Ded");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 16); 
					cell4.setCellValue("TDS");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 17); 
					cell4.setCellValue("MLWF/PMJJY");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 18); 
					cell4.setCellValue("Bank Loan");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 19); 
					cell4.setCellValue("Other Loan");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 20); 
					cell4.setCellValue("Other");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 21); 
					cell4.setCellValue("Total Ded");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 22); 
					cell4.setCellValue("Net Pay");
					cell4.setCellStyle(my_style1);
				

					rs.previous();
		    	 while (rs.next())
		    	 {
					basic_total = basic_total + rs.getFloat("basic");
					
					sr=sr+1;
					head1=   sheet.createRow((short)i++);             
				 	cell4 = head1 .createCell((short) 0); 
				 	cell4.setCellValue(""+sr);
				 	
					cell4.setCellValue(""+rs.getString("site_name"));
					cell4 = head1 .createCell((short) 1); 
					
					etot=rs.getInt("emptotal");				
					cell4.setCellValue(""+rs.getString("emptotal"));
					totalemp = totalemp + etot;
					
					cell4 = head1 .createCell((short) 2); 
					cell4.setCellValue(rs.getFloat("basic"));
					
					if(rs.getFloat("da")==0.0){
						cell4 = head1 .createCell((short) 3); 
						cell4.setCellValue("");
					}
					else{
						float curda = rs.getFloat("da");
						currda_total = currda_total +curda;
						cell4 = head1 .createCell((short) 3); 
						cell4.setCellValue(curda);
					}
					
					
					
					
					/*cell4 = head1 .createCell((short) 4); 
					cell4.setCellValue(rs.getFloat("vda"));*/
					
					hra_total = hra_total + rs.getFloat("hra");
					cell4 = head1 .createCell((short) 4); 
					cell4.setCellValue(rs.getFloat("hra"));
					
					
					if(rs.getFloat("Dept_Allw")==0.0){
						cell4 = head1 .createCell((short) 5); 
						cell4.setCellValue("");
					}
					else{
						float dept = rs.getFloat("Dept_Allw");
						dept_total = dept_total +dept;
						cell4 = head1 .createCell((short) 5); 
						cell4.setCellValue(dept);
					}
					
					convall_total = convall_total + rs.getFloat("Convy_Allw");
					cell4 = head1 .createCell((short) 6); 
					cell4.setCellValue(rs.getFloat("Convy_Allw"));
					
					
					
					
					otherall_total = otherall_total + rs.getFloat("Other_Allw");
					cell4 = head1 .createCell((short) 7); 
					cell4.setCellValue(rs.getFloat("Other_Allw"));
					
					
					
					addincome_total = addincome_total + rs.getFloat("AnyOther_Allw");
					cell4 = head1 .createCell((short) 8); 
					cell4.setCellValue(rs.getFloat("AnyOther_Allw"));
					
					
					
					Earning_total = Earning_total + rs.getFloat("TotalEarning");
					cell4 = head1 .createCell((short) 9); 
					cell4.setCellValue(rs.getFloat("TotalEarning"));
					
					PF_total = PF_total + rs.getFloat("PF");
					cell4 = head1 .createCell((short) 10); 
					cell4.setCellValue(rs.getFloat("PF"));
					
					PT_total = PT_total + rs.getFloat("PT");
					cell4 = head1 .createCell((short) 11); 
					cell4.setCellValue(rs.getFloat("PT"));
					
					LIC_total = LIC_total + rs.getFloat("LIC");
					cell4 = head1 .createCell((short) 12); 
					cell4.setCellValue(rs.getFloat("LIC"));
					
					
					
					MEDICLAIM_total = MEDICLAIM_total + rs.getFloat("MEDICLAIM");
					cell4 = head1 .createCell((short) 13); 
					cell4.setCellValue(rs.getFloat("MEDICLAIM"));
					
					
					
					OtherDed_total = OtherDed_total + rs.getFloat("Other_Ded");
					cell4 = head1 .createCell((short) 14); 
					cell4.setCellValue(rs.getFloat("Other_Ded"));
					
					
					
					MembDed_total = MembDed_total + rs.getFloat("Memb_Ded");
					cell4 = head1 .createCell((short) 15); 
					cell4.setCellValue(rs.getFloat("Memb_Ded"));
					
					tds_total = tds_total + rs.getFloat("TDS");
					cell4 = head1 .createCell((short) 16); 
					cell4.setCellValue(rs.getFloat("TDS"));
					
					
					
					MLWF_total = MLWF_total + rs.getFloat("MLWF");
					cell4 = head1 .createCell((short) 17); 
					cell4.setCellValue(rs.getFloat("MLWF"));
					
					BankInst_total = BankInst_total + rs.getFloat("Bank_Inst");
					cell4 = head1 .createCell((short) 18); 
					cell4.setCellValue(rs.getFloat("Bank_Inst"));
					
					
					
					OtherLoan_total = OtherLoan_total + rs.getFloat("Other_Loan");
					cell4 = head1 .createCell((short) 19); 
					cell4.setCellValue(rs.getFloat("Other_Loan"));
					
					otherded_total = otherded_total + rs.getFloat("other");
					cell4 = head1 .createCell((short) 20); 
					cell4.setCellValue(rs.getFloat("other"));
					
					
					deduction_total = deduction_total + rs.getFloat("Total_Deduction");
					cell4 = head1 .createCell((short) 21); 
					cell4.setCellValue(rs.getFloat("Total_Deduction"));
					
					netPay_total = netPay_total + rs.getFloat("netPay");
					cell4 = head1 .createCell((short) 22); 
					cell4.setCellValue(rs.getFloat("netPay"));
					
		    	 }
		    	  		    	 			    	 
		    	    head1=   sheet.createRow((short)i++);
		    	    head1=   sheet.createRow((short)i++);
		    	    head1=   sheet.createRow((short)i++);
		    	    
		    	//heading start
		    	    /*head1=   sheet.createRow((short)i++);
				 	head1=   sheet.createRow((short)i++);*/
				 	cell4 = head1 .createCell((short) 0);
				 	cell4.setCellValue("SUMMARY");
				 	cell4.setCellStyle(my_style);
				 	cell4 = head1 .createCell((short) 1); 
					cell4.setCellValue("Total Emp");
					cell4.setCellStyle(my_style);
				 	cell4 = head1 .createCell((short) 2); 
					cell4.setCellValue("Basic");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 3); 
					cell4.setCellValue("D.A");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 4); 
					cell4.setCellValue("H.R.A");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 5); 
					cell4.setCellValue("Dept Allw");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 6); 
					cell4.setCellValue("Convy Allw");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 7); 
					cell4.setCellValue("Other Scpcl Allw");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 8); 
					cell4.setCellValue("Add Income");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 9); 
					cell4.setCellValue("Total Earn");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 10); 
					cell4.setCellValue("P.F");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 11); 
					/*cell4.setCellValue("P.F");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 12); */
					cell4.setCellValue("P.Tax");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 12); 
					cell4.setCellValue("L.I.C");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 13); 
					cell4.setCellValue("Mediclaim");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 14); 
					cell4.setCellValue("Other Ded");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 15); 
					cell4.setCellValue("Memb Ded");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 16); 
					cell4.setCellValue("TDS");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 17); 
					cell4.setCellValue("MLWF/PMJJY");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 18); 
					cell4.setCellValue("Bank Loan");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 19); 
					cell4.setCellValue("Other Loan");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 20); 
					cell4.setCellValue("Other");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 21); 
					cell4.setCellValue("Total Deduction");
					cell4.setCellStyle(my_style);
					cell4 = head1 .createCell((short) 22); 
					cell4.setCellValue("Net Pay");
					cell4.setCellStyle(my_style);
					
					//heading end 
					
					head1=   sheet.createRow((short)i++);		    	   
		    	    
					cell4.setCellStyle(my_style);
					
					cell4 = head1 .createCell((short) 1); 
					cell4.setCellValue(totalemp);
					
					cell4 = head1 .createCell((short) 2); 
					cell4.setCellValue(basic_total);
					
					lstbasic_total = lstbasic_total+basic_total;
					basic_total = 0.0f;
					
					cell4 = head1 .createCell((short) 3); 
					cell4.setCellValue(currda_total);
					lstcurrda_total = lstcurrda_total+currda_total;
					currda_total = 0.0f;
					
					/*cell4 = head1 .createCell((short) 4); 
					cell4.setCellValue(vda_total);
					lstvda_total = lstvda_total+vda_total;
					vda_total = 0.0f;*/
					
					cell4 = head1 .createCell((short) 4); 
					cell4.setCellValue(hra_total);
					lsthra_total = lsthra_total + hra_total;
					hra_total = 0.0f;
										
					cell4 = head1 .createCell((short) 5); 
					cell4.setCellValue(dept_total);
					lstdept_total = lstdept_total + dept_total;
					dept_total = 0.0f;
					
					cell4 = head1 .createCell((short) 6); 
					cell4.setCellValue(convall_total);
					lstconvall_total = lstconvall_total + convall_total;
					convall_total = 0.0f;
					
					cell4 = head1 .createCell((short) 7); 
					cell4.setCellValue(otherall_total);
					lstotherall_total = lstotherall_total +otherall_total;
					otherall_total = 0.0f;
					
					cell4 = head1 .createCell((short)8); 
					cell4.setCellValue(addincome_total);
					lstaddincome_total = lstaddincome_total + addincome_total;
					addincome_total = 0.0f;
					
					cell4 = head1 .createCell((short)9); 
					cell4.setCellValue(Earning_total);
					lstEarning_total = lstEarning_total+ Earning_total ;
					Earning_total = 0.0f;
					
					cell4 = head1 .createCell((short) 10); 
					cell4.setCellValue(PF_total);
					lstPF_total = lstPF_total + PF_total ;
					PF_total = 0.0f;
					
					cell4 = head1 .createCell((short) 11); 
					cell4.setCellValue(PT_total);
					lstPT_total = lstPT_total + PT_total ;
					PT_total = 0.0f;

					cell4 = head1 .createCell((short) 12); 
					cell4.setCellValue(LIC_total);
					lstLIC_total = lstLIC_total + LIC_total ;
					LIC_total = 0.0f;
					
					
					cell4 = head1 .createCell((short) 13); 
					cell4.setCellValue(MEDICLAIM_total);
					lstMEDICLAIM_total = lstMEDICLAIM_total + MEDICLAIM_total ;
					MEDICLAIM_total = 0.0f;
					
					cell4 = head1 .createCell((short) 14); 
					cell4.setCellValue(OtherDed_total);
					lstOtherDed_total = lstOtherDed_total + OtherDed_total ;
					OtherDed_total = 0.0f;
					
					cell4 = head1 .createCell((short) 15); 
					cell4.setCellValue(MembDed_total);
					lstMembDed_total = lstMembDed_total + MembDed_total ;
					MembDed_total = 0.0f;

					cell4 = head1 .createCell((short) 16); 
					cell4.setCellValue(tds_total);
					lsttds_total = lsttds_total + tds_total ;
					tds_total = 0.0f;
					
					cell4 = head1 .createCell((short) 17);
					cell4.setCellValue(MLWF_total);
					lstMLWF_total = lstMLWF_total + MLWF_total ;
					MLWF_total = 0.0f;
					
					cell4 = head1 .createCell((short) 18); 
					cell4.setCellValue(BankInst_total);
					lstBankInst_total = lstBankInst_total + BankInst_total ;
					BankInst_total = 0.0f;
					
					cell4 = head1 .createCell((short) 19);
					cell4.setCellValue(OtherLoan_total);
					lstOtherLoan_total = lstOtherLoan_total + OtherLoan_total ;
					OtherLoan_total = 0.0f;
					
					cell4 = head1 .createCell((short) 20);
					cell4.setCellValue(otherded_total);
					lstotherded_total = lstotherded_total + otherded_total ;
					otherded_total = 0.0f;
					
					cell4 = head1 .createCell((short) 21);
					cell4.setCellValue(deduction_total);
					lstdeduction_total = lstdeduction_total + deduction_total ;
					deduction_total = 0.0f;
					
					cell4 = head1 .createCell((short) 22);
					cell4.setCellValue(netPay_total);
					lstnetPay_total = lstnetPay_total + netPay_total ;
					netPay_total = 0.0f;
					
					
					
			}
				
			HSSFRow	head1=   sheet.createRow((short)i++);
			HSSFCell cell4;
			cell4 = head1.createCell((short) 0);
			cell4.setCellStyle(my_style);
			cell4.setCellValue("Total Employee(s) : "+totalemp);
								
						hwb.write(out1);
						out1.close();
												
				
			
			st.close();
		    con.close();	
	
	}
	catch(Exception e){
		System.out.println("into excel type");
		e.printStackTrace();
	}
	

}

public static void newpayregExcelG(String PAYREGDT, String imagepath, String filePath, String salaryType, String Branch,
		String Desgn, String deptartment) {



	System.out.println("in new pay regdao");

	// this code is for constant property see constant.properties
	Properties prop = new Properties();
	try {

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream stream = classLoader.getResourceAsStream("constant.properties");
		prop.load(stream);
	} catch (Exception e) {
		System.out.println("Error in constant properties Manager " + e);
	}

	RepoartBean repBean = new RepoartBean();

	Connection con = null;
	String BomDt = "";
	String EomDt = "";
	String StartDt = "";
	StartDt = "01-Dec-1900";
	int lastdat = 0;
	// String dt = PAYREGDT;
	String table_name = null;
	/*
	 * if(TName.equals("before") ) { table_name = "PAYTRAN";
	 * lable1="Before Finalize"; } else if(TName.equals("after") ) {
	 * table_name="YTDTRAN"; lable1="Final After Release"; } else {
	 * table_name="PAYTRAN_STAGE"; lable1="Pending for Release"; }
	 */
	// System.out.println(dt);
	lastdat = (int) Calculate.getDays(PAYREGDT);
	System.out.println("maxdt" + lastdat);
	BomDt = ReportDAO.BOM(PAYREGDT);
	// System.out.println("bomdt "+BomDt);
	EomDt = ReportDAO.EOM(PAYREGDT);
	// System.out.println("eomdt"+EomDt);

	String temp = PAYREGDT.substring(3);
	ResultSet emp = null;
	String EmpSql = "";
	String pBrcd1 = "";
	int tot_no_emp = 0;
	int br_tot_no_emp = 0;
	float tot_absents = 0.0f;
	float totmthsal1 = 0.0f;
	float totearning1 = 0.0f;
	float totearning2 = 0.0f;
	float totactualpay = 0.0f;
	float totmobded = 0.0f;
	float totadvanc = 0.0f;
	float totloan = 0.0f;
	float tottds = 0.0f;
	String constant="";
	String orderBY="";

	try {
		
		

			if ((!"Select".equals(Branch) && (!"0".equals(Desgn)))){	
				constant="and t.PRJ_SRNO="+Branch;
			}else{
				constant=" ";
			}
			ReportDAO.OpenCon("", "", "", repBean);
			con = repBean.getCn();
			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String report_type[] = { "t.PRJ_SRNO", "t.DEPT" };

			FileOutputStream out1 = new FileOutputStream(new File(filePath));

			HSSFWorkbook hwb = new HSSFWorkbook();
			for (int x = 0; x < report_type.length; x++) {
				tot_no_emp = 0;
				HSSFSheet sheet = hwb.createSheet(x == 0 ? "Project" : "Depertment");
				Calendar currentMonth = Calendar.getInstance();

				HSSFCellStyle my_style = hwb.createCellStyle();
				HSSFFont my_font = hwb.createFont();
				my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				my_style.setFont(my_font);

				HSSFRow rowtitle = sheet.createRow((short) 0);
				HSSFCell cell = rowtitle.createCell((short) 10);
				cell.setCellValue(prop.getProperty("companyName"));
				cell.setCellStyle(my_style);
				HSSFRow rowtitle1 = sheet.createRow((short) 1);
				HSSFCell cell1 = rowtitle1.createCell((short) 9);
				cell1.setCellValue(prop.getProperty("addressForReport"));
				cell1.setCellStyle(my_style);
				HSSFRow rowtitle2 = sheet.createRow((short) 2);
				HSSFCell cell2 = rowtitle2.createCell((short) 11);
				cell2.setCellValue(prop.getProperty("contactForReport"));
				cell2.setCellStyle(my_style);
				HSSFRow rowtitle31 = sheet.createRow((short) 3);
				cell2 = rowtitle31.createCell((short) 11);
				cell2.setCellValue(prop.getProperty("mailForReport"));
				cell2.setCellStyle(my_style);
				HSSFRow rowtitle3 = sheet.createRow((short) 4);
				cell2 = rowtitle3.createCell((short) 10);
				cell2.setCellValue("Salary Sheet For The Month Of :- " + PAYREGDT);
				cell2.setCellStyle(my_style);
				HSSFRow rowtitle4 = sheet.createRow((short) 5);
				rowtitle4.createCell((short) 0).setCellValue("");
				HSSFRow rowtitle5 = sheet.createRow((short) 6);
				rowtitle5.createCell((short) 0).setCellValue("");

				HSSFFont blueFont = hwb.createFont();
				blueFont.setColor(HSSFColor.BLUE.index);

				HSSFCellStyle style = hwb.createCellStyle();
				// style.setFont(blueFont);
				style.setFillForegroundColor(HSSFColor.BLUE.index);

				int i = 10;

				Rectangle rec = new Rectangle(100, 100);
				
				
				float lstother_total = 0.0f;
				float other_total = 0.0f;
				float basic_total = 0.0f;
				float lstbasic_total = 0.0f;
				float lic_total = 0.0f;
				float lstlic_total = 0.0f;
				float hra_total = 0.0f;
				float lsthra_total = 0.0f;
				float convall_total = 0.0f;
				float lstconvall_total = 0.0f;
				float pf_total = 0.0f;
				float lstpf_total = 0.0f;
				float pt_total = 0.0f;
				float lstpt_total = 0.0f;
				float mlwf_total = 0.0f;
				float lstmlwf_total = 0.0f;
				float tds_total = 0.0f;
				float lsttds_total = 0.0f;
				float addinc_total = 0.0f;
				float lstaddinc_total = 0.0f;
				float totinc_total = 0.0f;
				float lsttotinc_total = 0.0f;
				float addded_total = 0.0f;
				float lstaddded_total = 0.0f;
				float totded_total = 0.0f;
				float lsttotded_total = 0.0f;
				float netpay_total = 0.0f;
				float lstnetpay_total = 0.0f;
				float eepf_total = 0.0f;
				float lsteepf_total = 0.0f;
				float eeps_total = 0.0f;
				float lsteeps_total = 0.0f;
				float eedli_total = 0.0f;
				float lsteedli_total = 0.0f;
				float eepfadmin_total = 0.0f;
				float lsteepfadmin_total = 0.0f;
				float eedliadmin_total = 0.0f;
				float lsteedliadmin_total = 0.0f;
				float eesic = 0.0f;
				float lsteesic_total = 0.0f;
				float absentdays_total = 0.0f;

				float da_total = 0.0f;
				float lstda_total = 0.0f;
				float deptall_total = 0.0f;
				float lstdeptall_total = 0.0f;
				float othersplall_total = 0.0f;
				float lstothersplall_total = 0.0f;
				float mediclaim_total = 0.0f;
				float lstmediclaim_total = 0.0f;
				float otherded_total = 0.0f;
				float lstotherded_total = 0.0f;
				float memded_total = 0.0f;
				float lstmemded_total = 0.0f;
				float bankloan_total = 0.0f;
				float lstbankloan_total = 0.0f;
				float otherloan_total = 0.0f;
				float lstotherloan_total = 0.0f;
				
				if(Desgn.equalsIgnoreCase("0")){
				
				EmpSql = "         SELECT  empcode , RTRIM(EMPMAST.FNAME)+' '+RTRIM(EMPMAST.MNAME)+' '+RTRIM(EMPMAST.LNAME)AS name, "
						+ "     	EMPMAST.empno,  empmast.DOL, empmast.STATUS, T.PRJ_SRNO,T.DEPT,"
						
						+ "       CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 101 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)   "
						+ "		THEN	(SELECT NET_AMT AS basic  FROM   PAYTRAN WHERE TRNCD = 101 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE	(SELECT 0 AS basic) END AS 'basic',"

						+ "       CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 102 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)   "
						+ "		THEN	(SELECT NET_AMT AS da  FROM   PAYTRAN WHERE TRNCD = 102 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE	(SELECT 0 AS da) END AS 'da',"
						
						+ "       CASE  WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 103 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		THEN			(SELECT NET_AMT AS hra  FROM   PAYTRAN WHERE TRNCD = 103 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE		(SELECT 0 AS hra) END AS 'hra',"

						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 107 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "				THEN (SELECT NET_AMT AS DeptAllw  FROM   PAYTRAN WHERE TRNCD = 107  AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)    			"
						+ "	ELSE (SELECT 0 AS DeptAllw) END AS 'DeptAllw'," 
						
						+" CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 108 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	    THEN			(SELECT NET_AMT AS ConvyAllw  FROM   PAYTRAN WHERE TRNCD = 108 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE		(SELECT 0 AS ConvyAllw) END AS 'ConvyAllw',"
													
						+ "       CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 129 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		THEN		(SELECT NET_AMT AS OtherSpclAllw  FROM   PAYTRAN WHERE TRNCD = 129 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE		(SELECT 0 AS OtherSpclAllw) END AS 'OtherSpclAllw',"
						
						+ "	    CASE  WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 999 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		THEN			(SELECT NET_AMT AS payable  FROM   PAYTRAN WHERE TRNCD = 999 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE		(SELECT 0 AS payable) END AS 'payable',"
						
						+"			 CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 201 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "			THEN		(SELECT NET_AMT AS pf  FROM   PAYTRAN WHERE TRNCD = 201 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)    			"
						+ "		ELSE		(SELECT 0 AS er2) END AS 'pf',	 "
						
						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 202 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS pt  FROM   PAYTRAN WHERE TRNCD = 202 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)		ELSE			(SELECT 0 AS pt) END AS 'pt', "
						
						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 205 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS lic  FROM   PAYTRAN WHERE TRNCD = 205 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS lic) END AS 'lic', "
						
						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 212 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS mediclaim  FROM   PAYTRAN WHERE TRNCD = 212 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS mediclaim) END AS 'mediclaim', "
						
						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 213 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS otherded  FROM   PAYTRAN WHERE TRNCD = 213 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS otherded) END AS 'otherded', "
						
						+ "		 CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 223 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		THEN		(SELECT NET_AMT AS membDed  FROM   PAYTRAN WHERE TRNCD = 223 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE  (SELECT 0 AS membDed) END AS 'membDed',"
						
						+ "	 CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 228 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN		(SELECT NET_AMT AS tds  FROM   PAYTRAN WHERE TRNCD = 228 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	ELSE		(SELECT 0 AS tds) END AS 'tds'," 

						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 207 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS mlwf  FROM   PAYTRAN WHERE TRNCD = 207 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS mlwf) END AS 'mlwf', "
						
						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 248 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS bankLoan  FROM   PAYTRAN WHERE TRNCD = 248 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS bankLoan) END AS 'bankLoan', "
						
						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 249 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS otherLoan  FROM   PAYTRAN WHERE TRNCD = 249 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS otherLoan) END AS 'otherLoan', "
						
						+ "    case when EXISTS(select SUM(net_amt) from   PAYTRAN  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198)"
						+ "    and TRNCD not in(101,102,103,107,108,129)"
						+ "    and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno =  EMPMAST.empno )"
						+ "	 THEN	(select SUM(net_amt) as addinc from   PAYTRAN  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198)"
						+ "    and TRNCD not in(101,102,103,107,108,129)"
						+ "    and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno =  EMPMAST.empno) "
						+ "	 ELSE		(SELECT 0 AS addinc) END AS 'addinc',"
						
						+ "      case when EXISTS(select SUM(net_amt)   from   PAYTRAN  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 201 and 299)"
						+ "     and TRNCD not in(201,202,205,212,213,223,228,207,248,249) and trndt BETWEEN '"
						+ BomDt + "' and '" + EomDt + "' and empno = empmast.empno)"
						+ "	  THEN	(select SUM(net_amt)  as other from   PAYTRAN  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 201 and 299)"
						+ "     and TRNCD not in(201,202,205,212,213,223,228,207,248,249) and trndt BETWEEN '"
						+ BomDt + "' and '" + EomDt + "' and empno = empmast.empno)"
						+ "	  ELSE	(SELECT 0 AS other) END AS 'other' ,"
						
						+ "	  case when EXISTS(select SUM(net_amt) from   PAYTRAN  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 201 and 299)"
						+ "     and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno = empmast.empno)"
						+ "	  THEN			(select SUM(net_amt) as totded from   PAYTRAN  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 201 and 299) "
						+ "     and trndt BETWEEN '" + BomDt + "' and '" + EomDt
						+ "' and empno = empmast.empno)	" + "	  ELSE 	(SELECT 0 AS totded) END AS 'totded' ,"
						
						+ "	  case when EXISTS(select SUM(net_amt) from   PAYTRAN  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198)"
						+ "     and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno = empmast.empno) "
						+ "	  THEN	(select SUM(net_amt) from   PAYTRAN  where TRNCD in    (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198)"
						+ "     and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno = empmast.empno)"
						+ "	  ELSE		(SELECT 0 AS totear) END AS 'totear',"
						
						+ " 	 CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 301 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS absentdays  FROM   PAYTRAN WHERE TRNCD = 301 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	  ELSE		(SELECT 0 AS absentdays) END AS 'absentdays'"
						
						
						+ "     from EMPMAST empmast     join   EMPTRAN t on t.empno = empmast.empno"
						+ "     where      T.srno =(SELECT MAX(E2.SRNO) FROM EMPTRAN E2 WHERE E2.empno = empmast.empno AND E2.EFFDATE <= '"
						+ EomDt + "'" + "     and (( empmast.STATUS='A' AND	empmast.DOJ <= '" + EomDt
						+ "') or  (empmast.STATUS ='N' And empmast.DOL>='" + BomDt + "' )) )"
						+ "     AND T.empno in (SELECT DISTINCT empno FROM PAYTRAN WHERE TRNDT BETWEEN '" + BomDt
						+ "' and '" + EomDt + "') " +

					
						
						"    UNION "
						+ "         SELECT  empcode , RTRIM(EMPMAST.FNAME)+' '+RTRIM(EMPMAST.MNAME)+' '+RTRIM(EMPMAST.LNAME)AS name,"
						+ "     	EMPMAST.empno, empmast.DOL, empmast.STATUS, T.PRJ_SRNO,T.DEPT,"

					
						+ "       CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 101 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		THEN	(SELECT NET_AMT AS basic  FROM   PAYTRAN_STAGE WHERE TRNCD = 101 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE	(SELECT 0 AS basic) END AS 'basic',"
						
						+ "       CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 102 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		THEN	(SELECT NET_AMT AS da  FROM   PAYTRAN_STAGE WHERE TRNCD = 102 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE	(SELECT 0 AS da) END AS 'da',"
					
						+ "       CASE  WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 103 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		THEN			(SELECT NET_AMT AS hra  FROM   PAYTRAN_STAGE WHERE TRNCD = 103 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE		(SELECT 0 AS hra) END AS 'hra',"
													
						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 107 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "				THEN (SELECT NET_AMT AS deptall  FROM   PAYTRAN_STAGE WHERE TRNCD = 107  AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)    			"
						+ "	ELSE (SELECT 0 AS deptall) END AS 'deptall',"
													
						+"          CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 108 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	    THEN			(SELECT NET_AMT AS convall  FROM   PAYTRAN_STAGE WHERE TRNCD = 108 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE		(SELECT 0 AS convall) END AS 'convall',"
						
						+ "       CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 129 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		THEN		(SELECT NET_AMT AS othersplall  FROM   PAYTRAN_STAGE WHERE TRNCD = 129 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE		(SELECT 0 AS othersplall) END AS 'othersplall',"
						
						+ "	    CASE  WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 999 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		THEN			(SELECT NET_AMT AS payable  FROM   PAYTRAN_STAGE WHERE TRNCD = 999 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE		(SELECT 0 AS payable) END AS 'payable',"
						
						+"			 CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 201 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "			THEN		(SELECT NET_AMT AS pf  FROM   PAYTRAN_STAGE WHERE TRNCD = 201 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)    			"
						+ "		ELSE		(SELECT 0 AS er2) END AS 'pf',	 "
						
						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 202 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS pt  FROM   PAYTRAN_STAGE WHERE TRNCD = 202 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)		ELSE			(SELECT 0 AS pt) END AS 'pt', "
						

						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 205 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS lic  FROM   PAYTRAN_STAGE WHERE TRNCD = 205 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS lic) END AS 'lic', "
							
						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 212 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS mediclaim  FROM   PAYTRAN_STAGE WHERE TRNCD = 212 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS mediclaim) END AS 'mediclaim', "
							
						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 213 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS otherded  FROM   PAYTRAN_STAGE WHERE TRNCD = 213 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS otherded) END AS 'otherded', "
							
						+ "		 CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 223 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		THEN		(SELECT NET_AMT AS memded  FROM   PAYTRAN_STAGE WHERE TRNCD = 223 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE  (SELECT 0 AS memded) END AS 'memded',"
						
						+ "	 CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 228 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN		(SELECT NET_AMT AS tds  FROM   PAYTRAN_STAGE WHERE TRNCD = 228 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	ELSE		(SELECT 0 AS tds) END AS 'tds',"

						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 207 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS mlwf  FROM   PAYTRAN_STAGE WHERE TRNCD = 207 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS mlwf) END AS 'mlwf', "
							
						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 248 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS bankloan  FROM   PAYTRAN_STAGE WHERE TRNCD = 248 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS bankloan) END AS 'bankloan', "
							
						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 249 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS otherloan  FROM   PAYTRAN_STAGE WHERE TRNCD = 249 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS otherloan) END AS 'otherloan', "
						
						
						+ "    case when EXISTS(select SUM(net_amt) from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198)"
						+ "    and TRNCD not in(101,102,103,107,108,129)"
						+ "    and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno =  EMPMAST.empno )"
						+ "	 THEN	(select SUM(net_amt) as addinc from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198)"
						+ "    and TRNCD not in(101,102,103,107,108,129)"
						+ "    and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno =  EMPMAST.empno) "
						+ "	 ELSE		(SELECT 0 AS addinc) END AS 'addinc',"
						
						+ "      case when EXISTS(select SUM(net_amt)   from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 200 and 299)"
						+ "     and TRNCD not in(201,202,205,212,213,223,228,207,248,249) and trndt BETWEEN '"
						+ BomDt + "' and '" + EomDt + "' and empno = empmast.empno)"
						+ "	  THEN	(select SUM(net_amt)  as other from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 200 and 299)"
						+ "     and TRNCD not in(201,202,205,212,213,223,228,207,248,249) and trndt BETWEEN '"
						+ BomDt + "' and '" + EomDt + "' and empno = empmast.empno)"
						+ "	  ELSE	(SELECT 0 AS other) END AS 'other' ,"
						
						+ "	  case when EXISTS(select SUM(net_amt) from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 200 and 299)"
						+ "     and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno = empmast.empno)"
						+ "	  THEN			(select SUM(net_amt) as totded from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 200 and 299) "
						+ "     and trndt BETWEEN '" + BomDt + "' and '" + EomDt
						+ "' and empno = empmast.empno)	" + "	  ELSE 	(SELECT 0 AS totded) END AS 'totded' ,"
						
						+ "	  case when EXISTS(select SUM(net_amt) from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198)"
						+ "     and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno = empmast.empno) "
						+ "	  THEN	(select SUM(net_amt) from   paytran_stage  where TRNCD in    (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198)"
						+ "     and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno = empmast.empno)"
						+ "	  ELSE		(SELECT 0 AS totear) END AS 'totear',"
						
						+ " 	 CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 301 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS absentdays  FROM   PAYTRAN_STAGE WHERE TRNCD = 301 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	  ELSE		(SELECT 0 AS absentdays) END AS 'absentdays'"
						
						
						+ "     from EMPMAST empmast     join   EMPTRAN t on t.empno = empmast.empno"
						+ "     where      T.srno =(SELECT MAX(E2.SRNO) FROM EMPTRAN E2 WHERE E2.empno = empmast.empno AND E2.EFFDATE <= '"
						+ EomDt + "'" + "     and (( empmast.STATUS='A' AND	empmast.DOJ <= '" + EomDt
						+ "') or  (empmast.STATUS ='N' And empmast.DOL>='" + BomDt + "' )) )"
						+ "     AND T.empno in (SELECT DISTINCT empno FROM PAYTRAN_STAGE WHERE TRNDT BETWEEN '"
						+ BomDt + "' and '" + EomDt + "') " + "     order by   t.PRJ_SRNO,empno  ";
			
				}
				else{
				if(salaryType.equalsIgnoreCase("paytran")){

					EmpSql="SELECT empcode, "
							+ "       Rtrim(empmast.fname)+' '+rtrim(empmast.mname)+' '+rtrim(empmast.lname) AS NAME, "
							+ "       empmast.empno, "
							+ "       T.prj_srno, "
							+ "       T.dept, "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran "
							+ "                     WHERE  trncd = 101 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS BASIC "
							+ "          FROM   paytran "
							+ "          WHERE  trncd = 101 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' "
							+ "                     AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS BASIC) "
							+ "       END                 AS 'BASIC', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran "
							+ "                     WHERE  trncd = 999 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS PAYABLE "
							+ "          FROM   paytran "
							+ "          WHERE  trncd = 999 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS PAYABLE) "
							+ "       END                 AS 'PAYABLE', "
							+ "        "
							+ "         "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran "
							+ "                     WHERE  trncd = 102 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS DA "
							+ "          FROM   paytran "
							+ "          WHERE  trncd = 102 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS DA) "
							+ "       END                 AS 'DA', "
							+ "        "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran "
							+ "                     WHERE  trncd = 103 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS HRA "
							+ "          FROM   paytran "
							+ "          WHERE  trncd = 103 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS HRA) "
							+ "       END                 AS 'HRA', "
							+ "        "
							+ "        "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran "
							+ "                     WHERE  trncd = 107 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS DeptAllw "
							+ "          FROM   paytran "
							+ "          WHERE  trncd = 107 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS DeptAllw) "
							+ "       END                 AS 'DeptAllw', "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran "
							+ "                     WHERE  trncd = 108 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS ConvyAllw "
							+ "          FROM   paytran "
							+ "          WHERE  trncd = 108 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS ConvyAllw ) "
							+ "       END                 AS 'ConvyAllw', "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran "
							+ "                     WHERE  trncd = 129 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS OtherSpclAllw "
							+ "          FROM   paytran "
							+ "          WHERE  trncd = 129 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS OtherSpclAllw) "
							+ "       END                 AS 'OtherSpclAllw', "
							+ "        CASE "
							+ "         WHEN EXISTS(SELECT Sum(net_amt) "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd IN (SELECT trncd "
							+ "                                      FROM   cdmast "
							+ "                                      WHERE  pslipyn = 'Y' "
							+ "                                             AND trncd BETWEEN 100 AND 198) "
							+ "                            AND trncd NOT IN( 101,102, 103, "
							+ "                                              108, 129) "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN (SELECT "
							+ "         Sum(net_amt) AS addinc FROM paytran_stage WHERE "
							+ "         trncd IN (SELECT trncd "
							+ "                   FROM   cdmast "
							+ "                   WHERE  pslipyn = 'Y' "
							+ "                          AND trncd BETWEEN 100 AND 198) "
							+ "         AND trncd NOT IN( 101, 102, 103, "
							+ "                           129, 108, "
							+ "                           107) "
							+ "         AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "         AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS addinc) "
							+ "       END                 AS 'addinc', "
							+ "        "
							+ "        "
							+ "        CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran "
							+ "                     WHERE  trncd = 999 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS TotalIncome "
							+ "          FROM   paytran "
							+ "          WHERE  trncd = 999 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS TotalIncome) "
							+ "       END                 AS 'TotalIncome', "
							+ "        "
							+ "        "
							+ "     "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran "
							+ "                     WHERE  trncd = 201 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS pf "
							+ "          FROM   paytran "
							+ "          WHERE  trncd = 201 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS pf) "
							+ "       END                 AS 'pf', "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran "
							+ "                     WHERE  trncd = 202 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS pt "
							+ "          FROM   paytran "
							+ "          WHERE  trncd = 202 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS pt) "
							+ "       END                 AS 'pt', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran "
							+ "                     WHERE  trncd = 205 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS lic "
							+ "          FROM   paytran "
							+ "          WHERE  trncd = 205 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS lic) "
							+ "       END                 AS 'lic', "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran "
							+ "                     WHERE  trncd = 212 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS mediclaim "
							+ "          FROM   paytran "
							+ "          WHERE  trncd = 212 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS mediclaim) "
							+ "       END                 AS 'mediclaim', "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran "
							+ "                     WHERE  trncd = 213 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS otherDed "
							+ "          FROM   paytran "
							+ "          WHERE  trncd = 213 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS otherDed) "
							+ "       END                 AS 'otherDed', "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran "
							+ "                     WHERE  trncd = 223 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS membDed "
							+ "          FROM   paytran "
							+ "          WHERE  trncd = 223 "
							+ "                 AND trndt BETWEEN "
							+ "                    '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS membDed) "
							+ "       END                 AS 'membDed', "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran "
							+ "                     WHERE  trncd = 228 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS tds "
							+ "          FROM   paytran "
							+ "          WHERE  trncd = 228 "
							+ "                 AND trndt BETWEEN "
							+ "                    '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS tds) "
							+ "       END                 AS 'tds', "
							
							
						
							
							
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran "
							+ "                     WHERE  trncd in (220,221) "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS mlwf "
							+ "          FROM   paytran "
							+ "          WHERE  trncd = 211 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS mlwf) "
							+ "       END                 AS 'mlwf', "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran "
							+ "                     WHERE  trncd = 248 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS bankLoan "
							+ "          FROM   paytran "
							+ "          WHERE  trncd = 248 "
							+ "                 AND trndt BETWEEN "
							+ "                    '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS bankLoan) "
							+ "       END                 AS 'bankLoan', "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran "
							+ "                     WHERE  trncd = 226 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS otherLoan "
							+ "          FROM   paytran "
							+ "          WHERE  trncd = 226 "
							+ "                 AND trndt BETWEEN "
							+ "                    '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS otherLoan) "
							+ "       END                 AS 'otherLoan', "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT Sum(net_amt) "
							+ "                     FROM   paytran "
							+ "                     WHERE  trncd IN (SELECT trncd "
							+ "                                      FROM   cdmast "
							+ "                                      WHERE  pslipyn = 'Y' "
							+ "                                             AND trncd BETWEEN 201 AND 299) "
							+ "                            AND trncd NOT IN( 201, 202, 205, 221, "
							+ "                                              223, 228,220,248,212, "
							+ "                                               226, 213 "
							+ "                                               ) "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN (SELECT "
							+ "         Sum(net_amt) AS other  FROM   paytran "
							+ "                          WHERE "
							+ "         trncd IN (SELECT trncd "
							+ "                   FROM   cdmast "
							+ "                   WHERE  pslipyn = 'Y' "
							+ "                          AND trncd BETWEEN 201 AND 299) "
							+ "         AND trncd NOT IN( 201, 202, 205, 221,212 , "
							+ "                           223, 228,  220, "
							+ "                           226,  213,248 ) "
							+ "         AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "         AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS other) "
							+ "       END                 AS 'other', "
							+ "       CASE "
							+ "        "
							+ "         WHEN EXISTS(SELECT Sum(net_amt) "
							+ "                     FROM   paytran "
							+ "                     WHERE  trncd IN (SELECT trncd "
							+ "                                      FROM   cdmast "
							+ "                                      WHERE  pslipyn = 'Y' "
							+ "                                             AND trncd BETWEEN 201 AND 299) "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN (SELECT "
							+ "         Sum(net_amt) AS totded "
							+ "                                                             FROM   paytran "
							+ "                                                             WHERE "
							+ "         trncd IN (SELECT trncd "
							+ "                   FROM   cdmast "
							+ "                   WHERE  pslipyn = 'Y' "
							+ "                          AND trncd BETWEEN 201 AND 299) "
							+ "         AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "         AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS totded) "
							+ "       END                 AS 'totded', "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT Sum(net_amt) "
							+ "                     FROM   paytran "
							+ "                     WHERE  trncd IN (SELECT trncd "
							+ "                                      FROM   cdmast "
							+ "                                      WHERE  pslipyn = 'Y' "
							+ "                                             AND trncd BETWEEN 100 AND 198) "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN (SELECT Sum(net_amt) "
							+ "                                                             FROM   paytran "
							+ "                                                             WHERE "
							+ "         trncd IN (SELECT trncd "
							+ "                   FROM   cdmast "
							+ "                   WHERE  pslipyn = 'Y' "
							+ "                          AND trncd BETWEEN 100 AND 198) "
							+ "         AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "         AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS totear) "
							+ "       END                 AS 'totear' "
							+ "        "
							+ "        "
							+ "         "
							+ "FROM   empmast empmast "
							+ "       JOIN emptran t "
							+ "         ON t.empno = empmast.empno "
							+ "WHERE  T.srno = (SELECT Max(E2.srno) "
							+ "                 FROM   emptran E2 "
							+ "                 WHERE  E2.empno = empmast.empno "
							+ "                        AND E2.effdate <= '"+EomDt+"' "
							+ "                        AND ( ( empmast.status = 'A' "
							+ "                                AND empmast.doj <= '"+EomDt+"' ) "
							+ "                               OR ( empmast.status = 'N' "
							+ "                                    AND empmast.dol >= '"+BomDt+"' ) )) "
							+ "       AND T.empno IN (SELECT DISTINCT empno "
							+ "                       FROM   paytran "
							+ "                       WHERE  trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"')"					 
							+constant+" "
							//+ "     order by  empno  "
					
							+ " UNION "
							+ " SELECT empcode, "
							+ "       Rtrim(empmast.fname)AS NAME, "
							+ "       empmast.empno, "
							+ "       T.prj_srno, "
							+ "       T.dept, "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 101 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS BASIC "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 101 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS BASIC) "
							+ "       END                 AS 'BASIC', "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 999 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS PAYABLE "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 999 "
							+ "                 AND trndt BETWEEN "
							+ "                    '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS PAYABLE) "
							+ "       END                 AS 'PAYABLE', "
							+ "        "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 102 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS DA "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 102 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS DA) "
							+ "       END                 AS 'DA', "
							+ "        "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 103 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS HRA "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 103 "
							+ "                 AND trndt BETWEEN "
							+ "                    '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS HRA) "
							+ "       END                 AS 'HRA', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 107 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS			DeptAllw "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 107 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS DeptAllw) "
							+ "       END                 AS 'DeptAllw', "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 108 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS ConvyAllw "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 108 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS ConvyALllw) "
							+ "       END                 AS 'ConvyAllw', "
							+ "        "
							+ "         "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 129 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS OtherAllw "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 129 "
							+ "                 AND trndt BETWEEN "
							+ "                    '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS OtherAllw) "
							+ "       END                 AS 'OtherAllw', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT Sum(net_amt) "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd IN (SELECT trncd "
							+ "                                      FROM   cdmast "
							+ "                                      WHERE  pslipyn = 'Y' "
							+ "                                             AND trncd BETWEEN 100 AND 198) "
							+ "                            AND trncd NOT IN( 101,102, 103,107, "
							+ "                                              108, 129) "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN (SELECT "
							+ "         Sum(net_amt) AS addinc FROM paytran_stage WHERE "
							+ "         trncd IN (SELECT trncd "
							+ "                   FROM   cdmast "
							+ "                   WHERE  pslipyn = 'Y' "
							+ "                          AND trncd BETWEEN 100 AND 198) "
							+ "         AND trncd NOT IN( 101, 102, 103, "
							+ "                           129, 108, "
							+ "                           107) "
							+ "         AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "         AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS addinc) "
							+ "       END                 AS 'addinc', "
							+ "        "
							+ "        "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 999 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS TotalIncome "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 999 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS TotalIncome) "
							+ "       END                 AS 'TotalIncome', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 201 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS pf "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 201 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS pf) "
							+ "       END                 AS 'pf', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 202 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS pt "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 202 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS pt) "
							+ "       END                 AS 'pt', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 205 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS lic "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 205 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS lic) "
							+ "       END                 AS 'lic', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 212 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS Mediclaim "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 212 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS Mediclaim) "
							+ "       END                 AS 'Mediclaim', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 213 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS Mediclaim "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 213 "
							+ "                 AND trndt BETWEEN "
							+ "                    '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS OtherDed) "
							+ "       END                 AS 'OtherDed', "
							+ "        "
							+ "        CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 223 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS MembDed "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 223 "
							+ "                 AND trndt BETWEEN "
							+ "                    '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS MembDed) "
							+ "       END                 AS 'MembDed', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 228 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS tds "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 228 "
							+ "                 AND trndt BETWEEN "
							+ "                    '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS tds) "
							+ "       END                 AS 'tds', "
							+ "        "
							+ "        CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran "
							+ "                     WHERE  trncd in (220,221) "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS mlwf "
							+ "          FROM   paytran "
							+ "          WHERE  trncd = 211 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS mlwf) "
							+ "       END                 AS 'mlwf', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 248 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS bankLoan "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 248 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS bankLoan) "
							+ "       END                 AS 'bankLoan', "
							+ "       "
							+ "       "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 248 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS otherLoan "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 248 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS otherLoan) "
							+ "       END                 AS 'otherLoan', "
							+ "      "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT Sum(net_amt) "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd IN (SELECT trncd "
							+ "                                      FROM   cdmast "
							+ "                                      WHERE  pslipyn = 'Y' "
							+ "                                             AND trncd BETWEEN 200 AND 299) "
							+ "                            AND trncd NOT IN( 201, 202, 205, 221, 213,212, "
							+ "                                              223, 248,226, 228, 220 ) "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN (SELECT "
							+ "         Sum(net_amt) AS other FROM paytran_stage "
							+ "                      WHERE "
							+ "         trncd IN (SELECT trncd "
							+ "                   FROM   cdmast "
							+ "                   WHERE  pslipyn = 'Y' "
							+ "                          AND trncd BETWEEN 200 AND 299) "
							+ "         AND trncd NOT IN( 201, 202, 205, 221,220, "
							+ "                           223,  228, 248, "
							+ "                            226, 211,213 ) "
							+ "         AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "         AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS other) "
							+ "       END                 AS 'other', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT Sum(net_amt) "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd IN (SELECT trncd "
							+ "                                      FROM   cdmast "
							+ "                                      WHERE  pslipyn = 'Y' "
							+ "                                             AND trncd BETWEEN 200 AND 299) "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN (SELECT "
							+ "         Sum(net_amt) AS totded FROM  paytran_stage "
							+ "                    WHERE "
							+ "         trncd IN (SELECT trncd "
							+ "                   FROM   cdmast "
							+ "                   WHERE  pslipyn = 'Y' "
							+ "                          AND trncd BETWEEN 200 AND 299) "
							+ "         AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "         AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS totded) "
							+ "       END                 AS 'totded', "
							+ "         "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT Sum(net_amt) "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd IN (SELECT trncd "
							+ "                                      FROM   cdmast "
							+ "                                      WHERE  pslipyn = 'Y' "
							+ "                                             AND trncd BETWEEN 100 AND 198) "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN (SELECT Sum(net_amt) "
							+ "                    FROM   paytran_stage "
							+ "                     WHERE "
							+ "         trncd IN (SELECT trncd "
							+ "                   FROM   cdmast "
							+ "                   WHERE  pslipyn = 'Y' "
							+ "                          AND trncd BETWEEN 100 AND 198) "
							+ "         AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "         AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS totear) "
							+ "       END                 AS 'totear' "
							+ "        "
							+ "FROM   empmast empmast "
							+ "       JOIN emptran t "
							+ "         ON t.empno = empmast.empno "
							+ "WHERE  T.srno = (SELECT Max(E2.srno) "
							+ "                 FROM   emptran E2 "
							+ "                 WHERE  E2.empno = empmast.empno "
							+ "                        AND E2.effdate <= '"+EomDt+"' "
							+ "                        AND ( ( empmast.status = 'A' "
							+ "                                AND empmast.doj <= '"+EomDt+"' ) "
							+ "                               OR ( empmast.status = 'N' "
							+ "                                    AND empmast.dol >= '"+BomDt+"' ) )) "
							+ "       AND T.empno IN (SELECT DISTINCT empno "
							+ "                       FROM   paytran_stage "
							+ "                       WHERE  trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"') "
							+constant+" "
							+ "     order by  empno  ";
						//	+ "          empno";

				      System.out.println("aj:  "+EmpSql);
				}


				else if(salaryType.equalsIgnoreCase("ytdtran")){

					
					
					EmpSql= "SELECT empcode, "
							+ "       Rtrim(empmast.fname)+' '+Rtrim(empmast.mname)+' '+Rtrim(empmast.lname)AS NAME, "
							+ "       empmast.empno, "
							+ "       T.prj_srno, "
							+ "       T.dept, "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   ytdtran "
							+ "                     WHERE  trncd = 101 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS BASIC "
							+ "          FROM   ytdtran "
							+ "          WHERE  trncd = 101 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS BASIC) "
							+ "       END                 AS 'BASIC', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   ytdtran "
							+ "                     WHERE  trncd = 999 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS PAYABLE "
							+ "          FROM   ytdtran "
							+ "          WHERE  trncd = 999 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS PAYABLE) "
							+ "       END                 AS 'PAYABLE', "
							+ "        "
							+ "         "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   ytdtran "
							+ "                     WHERE  trncd = 102 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS DA "
							+ "          FROM   ytdtran "
							+ "          WHERE  trncd = 102 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS DA) "
							+ "       END                 AS 'DA', "
							+ "        "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   ytdtran "
							+ "                     WHERE  trncd = 103 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS HRA "
							+ "          FROM   ytdtran "
							+ "          WHERE  trncd = 103 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS HRA) "
							+ "       END                 AS 'HRA', "
							+ "        "
							+ "        "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   ytdtran "
							+ "                     WHERE  trncd = 107 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS DeptAllw "
							+ "          FROM   ytdtran "
							+ "          WHERE  trncd = 107 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS DeptAllw) "
							+ "       END                 AS 'DeptAllw', "
							+ "        "
							+ "        "
							+ "       "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   ytdtran "
							+ "                     WHERE  trncd = 108 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS ConvyAllw "
							+ "          FROM   ytdtran "
							+ "          WHERE  trncd = 108 "
							+ "                 AND trndt BETWEEN "
							+ "                    '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS ConvyAllw ) "
							+ "       END                 AS 'ConvyAllw', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   ytdtran "
							+ "                     WHERE  trncd = 129 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS OtherSpclAllw "
							+ "          FROM   ytdtran "
							+ "          WHERE  trncd = 129 "
							+ "                 AND trndt BETWEEN "
							+ "                    '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS OtherSpclAllw) "
							+ "       END                 AS 'OtherSpclAllw', "
							+ "        "
							+ "        "
							+ "        "
							+ "        CASE "
							+ "         WHEN EXISTS(SELECT Sum(net_amt) "
							+ "                     FROM   ytdtran "
							+ "                     WHERE  trncd IN (SELECT trncd "
							+ "                                      FROM   cdmast "
							+ "                                      WHERE  pslipyn = 'Y' "
							+ "                                             AND trncd BETWEEN 100 AND 198) "
							+ "                            AND trncd NOT IN( 101,102, 103, 104, 105, "
							+ "                                               108, 107,129 ) "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN (SELECT "
							+ "         Sum(net_amt) AS addinc FROM ytdtran WHERE "
							+ "         trncd IN (SELECT trncd "
							+ "                   FROM   cdmast "
							+ "                   WHERE  pslipyn = 'Y' "
							+ "                          AND trncd BETWEEN 100 AND 198) "
							+ "         AND trncd NOT IN( 101, 102, 103, "
							+ "                           129, 108, "
							+ "                           107) "
							+ "         AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "         AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS addinc) "
							+ "       END                 AS 'addinc', "
							+ "        "
							+ "        "
							+ "        CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   ytdtran "
							+ "                     WHERE  trncd = 999 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS TotalIncome "
							+ "          FROM   ytdtran "
							+ "          WHERE  trncd = 999 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS TotalIncome) "
							+ "       END                 AS 'TotalIncome', "
							+ "        "
							+ "        "
							+ "     "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   ytdtran "
							+ "                     WHERE  trncd = 201 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS pf "
							+ "          FROM   ytdtran "
							+ "          WHERE  trncd = 201 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS pf) "
							+ "       END                 AS 'pf', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   ytdtran "
							+ "                     WHERE  trncd = 202 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS pt "
							+ "          FROM   ytdtran "
							+ "          WHERE  trncd = 202 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS pt) "
							+ "       END                 AS 'pt', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   ytdtran "
							+ "                     WHERE  trncd = 205 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS lic "
							+ "          FROM   ytdtran "
							+ "          WHERE  trncd = 205 "
							+ "                 AND trndt BETWEEN "
							+ "                    '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS lic) "
							+ "       END                 AS 'lic', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   ytdtran "
							+ "                     WHERE  trncd = 212 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS mediclaim "
							+ "          FROM   ytdtran "
							+ "          WHERE  trncd = 212 "
							+ "                 AND trndt BETWEEN "
							+ "                    '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS mediclaim) "
							+ "       END                 AS 'mediclaim', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   ytdtran "
							+ "                     WHERE  trncd = 213 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS otherDed "
							+ "          FROM   ytdtran "
							+ "          WHERE  trncd = 213 "
							+ "                 AND trndt BETWEEN "
							+ "                    '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS otherDed) "
							+ "       END                 AS 'otherDed', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   ytdtran "
							+ "                     WHERE  trncd = 223 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS membDed "
							+ "          FROM   ytdtran "
							+ "          WHERE  trncd = 223 "
							+ "                 AND trndt BETWEEN "
							+ "                    '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS membDed) "
							+ "       END                 AS 'membDed', "
							+ "       "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   ytdtran "
							+ "                     WHERE  trncd = 228 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS tds "
							+ "          FROM   ytdtran "
							+ "          WHERE  trncd = 228 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS tds) "
							+ "       END                 AS 'tds', "
							+ "        "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   ytdtran "
							+ "                     WHERE  trncd in (220,221) "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS mlwf "
							+ "          FROM   ytdtran "
							+ "          WHERE  trncd = 211 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS mlwf) "
							+ "       END                 AS 'mlwf', "
							+ "        "
							+ "        "
							+ "        "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   ytdtran "
							+ "                     WHERE  trncd = 248 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS bankLoan "
							+ "          FROM   ytdtran "
							+ "          WHERE  trncd = 248 "
							+ "                 AND trndt BETWEEN "
							+ "                    '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS bankLoan) "
							+ "       END                 AS 'bankLoan', "
							+ "        "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   ytdtran "
							+ "                     WHERE  trncd = 226 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS otherLoan "
							+ "          FROM   ytdtran "
							+ "          WHERE  trncd = 226 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS otherLoan) "
							+ "       END                 AS 'otherLoan', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT Sum(net_amt) "
							+ "                     FROM   ytdtran "
							+ "                     WHERE  trncd IN (SELECT trncd "
							+ "                                      FROM   cdmast "
							+ "                                      WHERE  pslipyn = 'Y' "
							+ "                                             AND trncd BETWEEN 201 AND 299) "
							+ "                            AND trncd NOT IN( 201, 202, 205, 221, "
							+ "                                              223, 228,220,248,212, "
							+ "                                               226, 213 "
							+ "                                               ) "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN (SELECT "
							+ "         Sum(net_amt) AS other  FROM   ytdtran "
							+ "                          WHERE "
							+ "         trncd IN (SELECT trncd "
							+ "                   FROM   cdmast "
							+ "                   WHERE  pslipyn = 'Y' "
							+ "                          AND trncd BETWEEN 201 AND 299) "
							+ "         AND trncd NOT IN( 201, 202, 205, 221,212 , "
							+ "                           223, 228,  220, "
							+ "                           226,  213,248 ) "
							+ "         AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "         AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS other) "
							+ "       END                 AS 'other', "
							+ "       CASE "
							+ "        "
							+ "         WHEN EXISTS(SELECT Sum(net_amt) "
							+ "                     FROM   ytdtran "
							+ "                     WHERE  trncd IN (SELECT trncd "
							+ "                                      FROM   cdmast "
							+ "                                      WHERE  pslipyn = 'Y' "
							+ "                                             AND trncd BETWEEN 201 AND 299) "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN (SELECT "
							+ "         Sum(net_amt) AS totded "
							+ "                                                             FROM   ytdtran "
							+ "                                                             WHERE "
							+ "         trncd IN (SELECT trncd "
							+ "                   FROM   cdmast "
							+ "                   WHERE  pslipyn = 'Y' "
							+ "                          AND trncd BETWEEN 201 AND 299) "
							+ "         AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "         AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS totded) "
							+ "       END                 AS 'totded', "
							+ "       CASE "
							+ "        "
							+ "        "
							+ "         WHEN EXISTS(SELECT Sum(net_amt) "
							+ "                     FROM   ytdtran "
							+ "                     WHERE  trncd IN (SELECT trncd "
							+ "                                      FROM   cdmast "
							+ "                                      WHERE  pslipyn = 'Y' "
							+ "                                             AND trncd BETWEEN 100 AND 198) "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN (SELECT Sum(net_amt) "
							+ "                                                             FROM   ytdtran "
							+ "                                                             WHERE "
							+ "         trncd IN (SELECT trncd "
							+ "                   FROM   cdmast "
							+ "                   WHERE  pslipyn = 'Y' "
							+ "                          AND trncd BETWEEN 100 AND 198) "
							+ "         AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "         AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS totear) "
							+ "       END                 AS 'totear' "
							+ "        "
							+ "         "
							+ "FROM   empmast empmast "
							+ "       JOIN emptran t "
							+ "         ON t.empno = empmast.empno "
							+ "WHERE  T.srno = (SELECT Max(E2.srno) "
							+ "                 FROM   emptran E2 "
							+ "                 WHERE  E2.empno = empmast.empno "
							+ "                        AND E2.effdate <= '"+EomDt+"' "
							+ "                        AND ( ( empmast.status = 'A' "
							+ "                                AND empmast.doj <= '"+EomDt+"' ) "
							+ "                               OR ( empmast.status = 'N' "
							+ "                                    AND empmast.dol >= '"+BomDt+"' ) )) "
							+ "       AND T.empno IN (SELECT DISTINCT empno "
							+ "                       FROM   ytdtran "
							+ "                       WHERE  trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"')"
							 +constant+" "
							+ "     order by  empno  ";
				     System.out.println(EmpSql);
				}

				else if(salaryType.equalsIgnoreCase("paytran_stage")){

					
					EmpSql= "SELECT empcode, "
							+ "       Rtrim(empmast.fname)+' '+Rtrim(empmast.mname)+' '+Rtrim(empmast.lname)AS NAME, "
							+ "       empmast.empno, "
							+ "       T.prj_srno, "
							+ "       T.dept, "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 101 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS BASIC "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 101 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS BASIC) "
							+ "       END                 AS 'BASIC', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 999 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS PAYABLE "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 999 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS PAYABLE) "
							+ "       END                 AS 'PAYABLE', "
							+ "        "
							+ "         "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 102 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS DA "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 102 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS DA) "
							+ "       END                 AS 'DA', "
							+ "        "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 103 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS HRA "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 103 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS HRA) "
							+ "       END                 AS 'HRA', "
							+ "        "
							+ "        "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 107 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS DeptAllw "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 107 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS DeptAllw) "
							+ "       END                 AS 'DeptAllw', "
							+ "        "
							+ "        "
							+ "       "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 108 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS ConvyAllw "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 108 "
							+ "                 AND trndt BETWEEN "
							+ "                    '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS ConvyAllw ) "
							+ "       END                 AS 'ConvyAllw', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 129 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS OtherSpclAllw "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 129 "
							+ "                 AND trndt BETWEEN "
							+ "                    '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS OtherSpclAllw) "
							+ "       END                 AS 'OtherSpclAllw', "
							+ "        "
							+ "        "
							+ "        "
							+ "        CASE "
							+ "         WHEN EXISTS(SELECT Sum(net_amt) "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd IN (SELECT trncd "
							+ "                                      FROM   cdmast "
							+ "                                      WHERE  pslipyn = 'Y' "
							+ "                                             AND trncd BETWEEN 100 AND 198) "
							+ "                            AND trncd NOT IN( 101,102, 103, 104, 105, "
							+ "                                               108, 107,129 ) "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN (SELECT "
							+ "         Sum(net_amt) AS addinc FROM paytran_stage WHERE "
							+ "         trncd IN (SELECT trncd "
							+ "                   FROM   cdmast "
							+ "                   WHERE  pslipyn = 'Y' "
							+ "                          AND trncd BETWEEN 100 AND 198) "
							+ "         AND trncd NOT IN( 101, 102, 103, "
							+ "                           129, 108, "
							+ "                           107) "
							+ "         AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "         AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS addinc) "
							+ "       END                 AS 'addinc', "
							+ "        "
							+ "        "
							+ "        CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 999 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS TotalIncome "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 999 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS TotalIncome) "
							+ "       END                 AS 'TotalIncome', "
							+ "        "
							+ "        "
							+ "     "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 201 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS pf "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 201 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS pf) "
							+ "       END                 AS 'pf', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 202 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS pt "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 202 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS pt) "
							+ "       END                 AS 'pt', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 205 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS lic "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 205 "
							+ "                 AND trndt BETWEEN "
							+ "                    '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS lic) "
							+ "       END                 AS 'lic', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 212 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS mediclaim "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 212 "
							+ "                 AND trndt BETWEEN "
							+ "                    '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS mediclaim) "
							+ "       END                 AS 'mediclaim', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 213 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS otherDed "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 213 "
							+ "                 AND trndt BETWEEN "
							+ "                    '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS otherDed) "
							+ "       END                 AS 'otherDed', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 223 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS membDed "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 223 "
							+ "                 AND trndt BETWEEN "
							+ "                    '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS membDed) "
							+ "       END                 AS 'membDed', "
							+ "       "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 228 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS tds "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 228 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS tds) "
							+ "       END                 AS 'tds', "
							+ "        "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd in (220,221) "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS mlwf "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 211 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS mlwf) "
							+ "       END                 AS 'mlwf', "
							+ "        "
							+ "        "
							+ "        "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 248 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS bankLoan "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 248 "
							+ "                 AND trndt BETWEEN "
							+ "                    '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS bankLoan) "
							+ "       END                 AS 'bankLoan', "
							+ "        "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT net_amt "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd = 226 "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN "
							+ "         (SELECT net_amt AS otherLoan "
							+ "          FROM   paytran_stage "
							+ "          WHERE  trncd = 226 "
							+ "                 AND trndt BETWEEN "
							+ "                     '"+BomDt+"' AND '"+EomDt+"' "
							+ "                 AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS otherLoan) "
							+ "       END                 AS 'otherLoan', "
							+ "        "
							+ "       CASE "
							+ "         WHEN EXISTS(SELECT Sum(net_amt) "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd IN (SELECT trncd "
							+ "                                      FROM   cdmast "
							+ "                                      WHERE  pslipyn = 'Y' "
							+ "                                             AND trncd BETWEEN 201 AND 299) "
							+ "                            AND trncd NOT IN( 201, 202, 205, 221, "
							+ "                                              223, 228,220,248,212, "
							+ "                                               226, 213 "
							+ "                                               ) "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN (SELECT "
							+ "         Sum(net_amt) AS other  FROM   paytran_stage "
							+ "                          WHERE "
							+ "         trncd IN (SELECT trncd "
							+ "                   FROM   cdmast "
							+ "                   WHERE  pslipyn = 'Y' "
							+ "                          AND trncd BETWEEN 201 AND 299) "
							+ "         AND trncd NOT IN( 201, 202, 205, 221,212 , "
							+ "                           223, 228,  220, "
							+ "                           226,  213,248 ) "
							+ "         AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "         AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS other) "
							+ "       END                 AS 'other', "
							+ "       CASE "
							+ "        "
							+ "         WHEN EXISTS(SELECT Sum(net_amt) "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd IN (SELECT trncd "
							+ "                                      FROM   cdmast "
							+ "                                      WHERE  pslipyn = 'Y' "
							+ "                                             AND trncd BETWEEN 201 AND 299) "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN (SELECT "
							+ "         Sum(net_amt) AS totded "
							+ "                                                             FROM   paytran_stage "
							+ "                                                             WHERE "
							+ "         trncd IN (SELECT trncd "
							+ "                   FROM   cdmast "
							+ "                   WHERE  pslipyn = 'Y' "
							+ "                          AND trncd BETWEEN 201 AND 299) "
							+ "         AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "         AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS totded) "
							+ "       END                 AS 'totded', "
							+ "       CASE "
							+ "        "
							+ "        "
							+ "         WHEN EXISTS(SELECT Sum(net_amt) "
							+ "                     FROM   paytran_stage "
							+ "                     WHERE  trncd IN (SELECT trncd "
							+ "                                      FROM   cdmast "
							+ "                                      WHERE  pslipyn = 'Y' "
							+ "                                             AND trncd BETWEEN 100 AND 198) "
							+ "                            AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "                            AND empno = empmast.empno) THEN (SELECT Sum(net_amt) "
							+ "                                                             FROM   paytran_stage "
							+ "                                                             WHERE "
							+ "         trncd IN (SELECT trncd "
							+ "                   FROM   cdmast "
							+ "                   WHERE  pslipyn = 'Y' "
							+ "                          AND trncd BETWEEN 100 AND 198) "
							+ "         AND trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"' "
							+ "         AND empno = empmast.empno) "
							+ "         ELSE (SELECT 0 AS totear) "
							+ "       END                 AS 'totear' "
							+ "        "
							+ "         "
							+ "FROM   empmast empmast "
							+ "       JOIN emptran t "
							+ "         ON t.empno = empmast.empno "
							+ "WHERE  T.srno = (SELECT Max(E2.srno) "
							+ "                 FROM   emptran E2 "
							+ "                 WHERE  E2.empno = empmast.empno "
							+ "                        AND E2.effdate <= '"+EomDt+"' "
							+ "                        AND ( ( empmast.status = 'A' "
							+ "                                AND empmast.doj <= '"+EomDt+"' ) "
							+ "                               OR ( empmast.status = 'N' "
							+ "                                    AND empmast.dol >= '"+BomDt+"' ) )) "
							+ "       AND T.empno IN (SELECT DISTINCT empno "
							+ "                       FROM   paytran_stage "
							+ "                       WHERE  trndt BETWEEN '"+BomDt+"' AND '"+EomDt+"')"
							+constant+" "
								+ "     order by  empno  ";
							
						System.out.println(EmpSql);
					}
				}

				System.out.println(EmpSql);
				ResultSet rs = st.executeQuery(EmpSql);

				while (rs.next()) {
					i++;
					String prj_name = null;
					String prj_code = null;
					int dept = 0;
					Connection conn = ConnectionManager.getConnectionTech();

					if (report_type[x].equalsIgnoreCase("t.prj_srno")) {
						// System.out.println("into if");
						String prjquery = "select Site_Name,Project_Code from Project_Sites where SITE_ID = '"
								+ rs.getString("PRJ_SRNO") + "'";
						// System.out.println(prjquery);
						Statement stmt = conn.createStatement();
						ResultSet prj = stmt.executeQuery(prjquery);
						if (prj.next()) {
							prj_name = prj.getString("Site_Name");
							prj_code = prj.getString("Project_Code");
						}
						pBrcd1 = rs.getString("PRJ_SRNO");
						br_tot_no_emp = 0;

						HSSFRow head1 = sheet.createRow((short) i++);
						HSSFCell cell4 = head1.createCell((short) 0);
						cell4.setCellValue(
								" Employee's Salary List For Project Site : " + prj_name + " (" + prj_code + ")");
						cell4.setCellStyle(my_style);
						prj.close();
					} else {

						Connection ccn = ConnectionManager.getConnection();
						Statement sts = ccn.createStatement();
						String stmt = "select distinct(lkp_srno),lkp_disc from lookup where lkp_code='DEPT' and lkp_srno="
								+ rs.getInt("DEPT") + "";
						ResultSet rrs = sts.executeQuery(stmt);
						// System.out.println(stmt);
						if (rrs.next()) {
							dept = rrs.getInt("lkp_srno");
							prj_name = rrs.getString("lkp_disc");
						}
						pBrcd1 = Integer.toString(rs.getInt("DEPT"));
						br_tot_no_emp = 0;

						HSSFRow head1 = sheet.createRow((short) i++);
						HSSFCell cell4 = head1.createCell((short) 0);
						cell4.setCellValue(" Employee's Salary List For Deptartment : " + prj_name + "");
						cell4.setCellStyle(my_style);

						ccn.close();
					}

					sheet.setColumnWidth((short) 0, (short) 3000);
					sheet.setColumnWidth((short) 1, (short) 7000);
					sheet.setColumnWidth((short) 4, (short) 4000);
					sheet.setColumnWidth((short) 5, (short) 4000);
					sheet.setColumnWidth((short) 6, (short) 4000);
					sheet.setColumnWidth((short) 7, (short) 4000);
					sheet.setColumnWidth((short) 8, (short) 4000);
					sheet.setColumnWidth((short) 9, (short) 4000);
					sheet.setColumnWidth((short) 10, (short) 4000);
					sheet.setColumnWidth((short) 11, (short) 4000);
					sheet.setColumnWidth((short) 12, (short) 4000);
					sheet.setColumnWidth((short) 13, (short) 4000);
					sheet.setColumnWidth((short) 14, (short) 4000);
					sheet.setColumnWidth((short) 15, (short) 4000);
					sheet.setColumnWidth((short) 16, (short) 4000);
					sheet.setColumnWidth((short) 17, (short) 4000);
					sheet.setColumnWidth((short) 18, (short) 4000);
					sheet.setColumnWidth((short) 19, (short) 4000);
					sheet.setColumnWidth((short) 20, (short) 4000);
					sheet.setColumnWidth((short) 21, (short) 4000);
					sheet.setColumnWidth((short) 22, (short) 4000);
					sheet.setColumnWidth((short) 23, (short) 4000);
					sheet.setColumnWidth((short) 24, (short) 4000);
					sheet.setColumnWidth((short) 25, (short) 4000);
					sheet.setColumnWidth((short) 26, (short) 4000);
					
					conn.close();

					HSSFRow rowhead = sheet.createRow((short) i++);

					
					rowhead.createCell((short) 0).setCellValue("EMP CODE.");
					rowhead.createCell((short) 1).setCellValue("EMPLOYEE NAME");
					rowhead.createCell((short) 4).setCellValue("BASIC");
					rowhead.createCell((short) 5).setCellValue("DA");
					rowhead.createCell((short) 6).setCellValue("HRA");
					rowhead.createCell((short) 7).setCellValue("DEPT ALL");
					rowhead.createCell((short) 8).setCellValue("CONV ALL");
					rowhead.createCell((short) 9).setCellValue("OTHER SPL ALL");
					rowhead.createCell((short) 10).setCellValue("ADD INCOME");
					rowhead.createCell((short) 11).setCellValue("TOTAL INCOME");
					rowhead.createCell((short) 12).setCellValue("PF");
					rowhead.createCell((short) 13).setCellValue("PT");
					rowhead.createCell((short) 14).setCellValue("LIC");
					rowhead.createCell((short) 15).setCellValue("MEDICLAIM");
					rowhead.createCell((short) 16).setCellValue("OTHER DED");
					rowhead.createCell((short) 17).setCellValue("MEMBER DED");
					rowhead.createCell((short) 18).setCellValue("TDS");
					rowhead.createCell((short) 19).setCellValue("MLWF");
					rowhead.createCell((short) 20).setCellValue("BANK LOAN");
					rowhead.createCell((short) 21).setCellValue("OTHER LOAN");
					rowhead.createCell((short) 22).setCellValue("OTHER");
					rowhead.createCell((short) 23).setCellValue("Tot DED");
					rowhead.createCell((short) 24).setCellValue("NET PAY");
					rowhead.createCell((short) 25).setCellValue("EMP STATUS");
					rowhead.createCell((short) 26).setCellValue("ABSENT DAYS");
					
					
					
				
					while (report_type[x].equalsIgnoreCase("t.prj_srno") ? pBrcd1.equals(rs.getString("PRJ_SRNO"))
							: dept == rs.getInt("DEPT")) {

						rowhead = sheet.createRow((short) i++);
						rowhead.createCell((short) 0).setCellValue("" + rs.getString("EMPCODE"));

						rowhead.createCell((short) 1).setCellValue("" + rs.getString("name"));

						basic_total = basic_total + rs.getFloat("basic");
						rowhead.createCell((short) 4).setCellValue((int) rs.getFloat("basic"));

						da_total = da_total + rs.getFloat("da");
						rowhead.createCell((short) 5).setCellValue((int) rs.getFloat("da"));
						
						hra_total = hra_total + rs.getFloat("hra");
						rowhead.createCell((short) 6).setCellValue((int) rs.getFloat("hra"));

						deptall_total = deptall_total + +rs.getFloat("DeptAllw");
						rowhead.createCell((short) 7).setCellValue((int) rs.getFloat("DeptAllw"));

						convall_total = convall_total + rs.getFloat("ConvyAllw");
						rowhead.createCell((short) 8).setCellValue((int) rs.getFloat("ConvyAllw"));

						othersplall_total = othersplall_total + rs.getFloat("OtherSpclAllw");
						rowhead.createCell((short) 9).setCellValue((int) rs.getFloat("OtherSpclAllw"));

						
						if (rs.getFloat("addinc") == 0.0) {
							rowhead.createCell((short) 10).setCellValue(0.00);

						} else {
							float add_inc = rs.getFloat("addinc");
							addinc_total = addinc_total + add_inc;
							rowhead.createCell((short) 10).setCellValue((int) add_inc);
						}


						totinc_total = totinc_total + rs.getFloat("totear");
						rowhead.createCell((short) 11).setCellValue((int) rs.getFloat("totear"));

						if (rs.getFloat("pf") == 0.0) {
							rowhead.createCell((short) 12).setCellValue(0.00);

						} else {
							float pf = rs.getFloat("pf");
							pf_total = pf_total + pf;
							rowhead.createCell((short) 12).setCellValue((int) pf);
						}

						if (rs.getFloat("pt") == 0.0) {
							rowhead.createCell((short) 13).setCellValue(0.00);

						} else {
							float pt = rs.getFloat("pt");
							pt_total = pt_total + pt;
							rowhead.createCell((short) 13).setCellValue((int) pt);
						}

						if (rs.getFloat("lic") == 0.0f) {
							rowhead.createCell((short) 14).setCellValue(0.00);

						} else {
							float lic = rs.getFloat("lic");
							lic_total = lic_total + lic;
							rowhead.createCell((short) 14).setCellValue((int) lic);
						}

						if (rs.getFloat("mediclaim") == 0.0f) {
							rowhead.createCell((short) 15).setCellValue(0.00);

						} else {
							float mediclaim = rs.getFloat("mediclaim");
							mediclaim_total = mediclaim_total + mediclaim;
							rowhead.createCell((short) 15).setCellValue((int) mediclaim);
						}

						if (rs.getFloat("otherded") == 0.0f) {
							rowhead.createCell((short) 16).setCellValue(0.00);
						} else {
							float otherded = rs.getFloat("otherded");
							otherded_total = otherded_total + otherded;
							rowhead.createCell((short) 16).setCellValue((int) otherded);
						}
						
						if (rs.getFloat("membDed") == 0.0f) {
							rowhead.createCell((short) 17).setCellValue(0.00);
						} else {
							float memded = rs.getFloat("membDed");
							memded_total = memded_total + memded;
							rowhead.createCell((short) 17).setCellValue((int) memded);
						}
						
						if (rs.getFloat("tds") == 0.0f) {
							rowhead.createCell((short) 18).setCellValue(0.00);
						} else {
							float tds = rs.getFloat("tds");
							tds_total = tds_total + tds;
							rowhead.createCell((short) 18).setCellValue((int) tds);
						}

						if (rs.getFloat("mlwf") == 0.0) {
							rowhead.createCell((short) 19).setCellValue(0.00);
						} else {
							float mlwf = rs.getFloat("mlwf");
							mlwf_total = mlwf_total + mlwf;
							rowhead.createCell((short) 19).setCellValue((int) mlwf);
						}
						
						if (rs.getFloat("bankLoan") == 0.0) {
							rowhead.createCell((short) 20).setCellValue(0.00);

						} else {
							float bankloan = rs.getFloat("bankLoan");
							bankloan_total = bankloan_total + bankloan;
							rowhead.createCell((short) 20).setCellValue((int) bankloan);
						}

						if (rs.getFloat("otherLoan") == 0.0) {
							rowhead.createCell((short) 21).setCellValue(0.00);

						} else {
							float otherloan = rs.getFloat("otherLoan");
							otherloan_total = otherloan_total + otherloan;
							rowhead.createCell((short) 21).setCellValue((int) otherloan);
						}
						

						if (rs.getFloat("other") == 0.0) {
							rowhead.createCell((short) 22).setCellValue(0.00);
						} else {
							float other = rs.getFloat("other");
							other_total = other_total + other;
							rowhead.createCell((short) 22).setCellValue((int) other);

						}

						
						totded_total = totded_total + rs.getFloat("totded");
						rowhead.createCell((short) 23).setCellValue((int) rs.getFloat("totded"));

						netpay_total = netpay_total + rs.getFloat("payable");
						rowhead.createCell((short) 24).setCellValue((int) rs.getFloat("payable"));


						/*if (rs.getString("DOL") != null)
						{
							rowhead.createCell((short) 25).setCellValue("Non Active");
						}
						else {
							rowhead.createCell((short) 25).setCellValue("Active");
						}
						
						rowhead.createCell((short) 26).setCellValue((int) rs.getFloat("absentdays"));*/

						
						tot_no_emp = tot_no_emp + 1;
						br_tot_no_emp = br_tot_no_emp + 1;
						if (!rs.next()) {
							break;
						}
						if (report_type[x].equalsIgnoreCase("t.prj_srno")) {

							if (!pBrcd1.equals(rs.getString("PRJ_SRNO"))) {
								rs.previous();
								break;
							}
						} else {
							if (dept != rs.getInt("DEPT")) {
								rs.previous();

								break;
							}
						}

					}
					rowhead = sheet.createRow((short) i++);
					rowhead.createCell((short) 0).setCellValue("");

					rowhead.createCell((short) 1).setCellValue("");
					rowhead.createCell((short) 3).setCellValue("TOTAL :--");

					rowhead.createCell((short) 4).setCellValue((int) basic_total);
					rowhead.createCell((short) 5).setCellValue(da_total);
					rowhead.createCell((short) 6).setCellValue(hra_total);
					rowhead.createCell((short) 7).setCellValue(deptall_total);
					rowhead.createCell((short) 8).setCellValue((int) convall_total);
					rowhead.createCell((short) 9).setCellValue((int) othersplall_total);
					rowhead.createCell((short) 10).setCellValue((int) addinc_total);
					rowhead.createCell((short) 11).setCellValue((int) totinc_total);


					lstbasic_total = lstbasic_total + basic_total;
					basic_total = 0.0f;
					
					lstda_total = lstda_total + da_total;
					da_total = 0.0f;

					lsthra_total = lsthra_total + hra_total;
					hra_total = 0.0f;
					
					lstdeptall_total = lstdeptall_total + deptall_total;
					deptall_total = 0.0f;
					
					lstconvall_total = lstconvall_total + convall_total;
					convall_total = 0.0f;

					lstothersplall_total = lstothersplall_total + othersplall_total;
					othersplall_total = 0.0f;

					lstaddinc_total = lstaddinc_total + addinc_total;
					addinc_total = 0.0f;

					lsttotinc_total = lsttotinc_total + totinc_total;
					totinc_total = 0.0f;
					
					rowhead.createCell((short) 12).setCellValue((int) pf_total);
					rowhead.createCell((short) 13).setCellValue((int) pt_total);
					rowhead.createCell((short) 14).setCellValue((int) lic_total);
					rowhead.createCell((short) 15).setCellValue((int) mediclaim_total);
					rowhead.createCell((short) 16).setCellValue((int) otherded_total);
					rowhead.createCell((short) 17).setCellValue((int) memded_total);
					rowhead.createCell((short) 18).setCellValue((int) tds_total);
					rowhead.createCell((short) 19).setCellValue((int) mlwf_total);
					rowhead.createCell((short) 20).setCellValue((int) bankloan_total);
					rowhead.createCell((short) 21).setCellValue((int) otherloan_total);
					rowhead.createCell((short) 22).setCellValue((int) other_total);
					rowhead.createCell((short) 23).setCellValue((int) totded_total);
					rowhead.createCell((short) 24).setCellValue((int) netpay_total);

					
					lstpf_total = lstpf_total + pf_total;

					lstpt_total = lstpt_total + pt_total;
					pt_total = 0.0f;

					lstlic_total = lstlic_total + lic_total;
					lic_total = 0.0f;
					
					lstmediclaim_total = lstmediclaim_total + mediclaim_total;
					mediclaim_total = 0.0f;
					
					lstotherded_total = lstotherded_total + otherded_total;
					otherded_total = 0.0f;
					
					lstmemded_total = lstmemded_total + memded_total;
					memded_total = 0.0f;
					
					lsttds_total = lsttds_total + tds_total;
					tds_total = 0.0f;

					lstmlwf_total = lstmlwf_total + mlwf_total;
					mlwf_total = 0.0f;
					
					lstbankloan_total = lstbankloan_total + bankloan_total;
					bankloan_total = 0.0f;

					lstotherloan_total = lstotherloan_total + otherloan_total;
					otherloan_total = 0.0f;

					lstother_total = lstother_total + other_total;
					other_total = 0.0f;

					lsttotded_total = lsttotded_total + totded_total;
					totded_total = 0.0f;

					lstnetpay_total = lstnetpay_total + netpay_total;
					netpay_total = 0.0f;

					
					pf_total = 0.0f;
					//esic_total = 0.0f;
					lsteepf_total += eepf_total;
					eepf_total = 0;

					lsteeps_total += eeps_total;
					eeps_total = 0;

					lsteedli_total += eedli_total;
					eedli_total = 0;

					lsteepfadmin_total += eepfadmin_total;
					eepfadmin_total = 0;

					lsteedliadmin_total += eedliadmin_total;
					eedliadmin_total = 0;

					lsteesic_total += eesic;
					eesic = 0;

					rowhead = sheet.createRow((short) i++);

					rowhead.createCell((short) 0).setCellValue("Total Employee(s) : " + br_tot_no_emp);

					rowhead = sheet.createRow((short) i++);

					rowhead.createCell((short) 0).setCellValue("");
				}

				HSSFRow rowhead = sheet.createRow((short) i++);

				rowhead.createCell((short) 0).setCellValue("Total Employee(s) : " + tot_no_emp);

				
				rowhead = sheet.createRow((short) i++);
				HSSFCell cell4;
				rowhead.createCell((short) 10).setCellValue("  ");
				rowhead = sheet.createRow((short) i++);

				cell4 = rowhead.createCell((short) 11);
				cell4.setCellValue("------- SUMMARY -------");
				cell4.setCellStyle(my_style);

				rowhead = sheet.createRow((short) i++);

				rowhead.createCell((short) 10).setCellValue("  ");
				rowhead = sheet.createRow((short) i++);

				rowhead.createCell((short) 10).setCellValue("  ");

				cell4 = rowhead.createCell((short) 1);
				cell4.setCellValue("BASIC Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 3).setCellValue((int) lstbasic_total);

				cell4 = rowhead.createCell((short) 6);
				cell4.setCellValue("DA Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 8).setCellValue(lstda_total);
				
				cell4 = rowhead.createCell((short) 11);
				cell4.setCellValue("HRA Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 13).setCellValue(lsthra_total);
				
				cell4 = rowhead.createCell((short) 16);
				cell4.setCellValue("DEPT ALL Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 18).setCellValue(lstdeptall_total);
				
				cell4 = rowhead.createCell((short) 21);
				cell4.setCellValue("CONV ALL Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 23).setCellValue((int) lstconvall_total);
				

				rowhead = sheet.createRow((short) i++);

				cell4 = rowhead.createCell((short) 1);
				cell4.setCellValue("OTHER SPECIAL ALLOW Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 3).setCellValue((int) lstothersplall_total);
				
				cell4 = rowhead.createCell((short) 6);
				cell4.setCellValue("ADD Income Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 8).setCellValue((int) lstaddinc_total);
				
				cell4 = rowhead.createCell((short) 11);
				cell4.setCellValue("PF Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 13).setCellValue((int) lstpf_total);
				
				cell4 = rowhead.createCell((short) 16);
				cell4.setCellValue("PT Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 18).setCellValue((int) lstpt_total);
				
				cell4 = rowhead.createCell((short) 21);
				cell4.setCellValue("LIC Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 23).setCellValue((int) lstlic_total);
				
				
				rowhead = sheet.createRow((short) i++);
				
				cell4 = rowhead.createCell((short) 1);
				cell4.setCellValue("MEDICLAIM Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 3).setCellValue((int) lstmediclaim_total);
				
				cell4 = rowhead.createCell((short) 6);
				cell4.setCellValue("OTHER DED Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 8).setCellValue((int) lstotherded_total);
				
				cell4 = rowhead.createCell((short) 11);
				cell4.setCellValue("MEMBER DED Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 13).setCellValue((int) lstmemded_total);
				
				cell4 = rowhead.createCell((short) 16);
				cell4.setCellValue("T.D.S Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 18).setCellValue((int) lsttds_total);
				
				cell4 = rowhead.createCell((short) 21);
				cell4.setCellValue("MLWF Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 23).setCellValue((int) lstmlwf_total);
				
				
				rowhead = sheet.createRow((short) i++);
				
				cell4 = rowhead.createCell((short) 1);
				cell4.setCellValue("BANK LOAN Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 3).setCellValue((int) lstbankloan_total);
				
				cell4 = rowhead.createCell((short) 6);
				cell4.setCellValue("OTHER LOAN Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 8).setCellValue((int) lstotherloan_total);
				
				cell4 = rowhead.createCell((short) 11);
				cell4.setCellValue("Total INCOME:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 13).setCellValue((int) lsttotinc_total);

				cell4 = rowhead.createCell((short) 16);
				cell4.setCellValue("ADDED Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 18).setCellValue((int) lstaddded_total);
				
				cell4 = rowhead.createCell((short) 21);
				cell4.setCellValue("Total Deduction:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 23).setCellValue((int) lsttotded_total);
				
				rowhead = sheet.createRow((short) i++);
				
				cell4 = rowhead.createCell((short) 21);
				cell4.setCellValue("NET PAY Total:");
				cell4.setCellStyle(my_style);

				float tttt = 0.0f;
				try {
					// System.out.println("date===="+date);
					Statement sttt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					rs = st.executeQuery("select sum(net_amt) from paytran where trndt between '"
							+ ReportDAO.BOM(PAYREGDT) + "' and '" + ReportDAO.EOM(PAYREGDT)
							+ "'  and trncd=999  union  "
							+ "select sum(net_amt) from paytran_stage where trndt between '"
							+ ReportDAO.BOM(PAYREGDT) + "' and '" + ReportDAO.EOM(PAYREGDT) + "'  and trncd=999 ");

					while (rs.next()) {
						tttt += rs.getFloat(1);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("Govi Net Pay 1::"+lsttotinc_total+" lsttotded_total"+lsttotded_total);
				System.out.println("Govi Net Pay ::"+((int)lsttotinc_total-lsttotded_total));
				rowhead.createCell((short) 23).setCellValue((int) lsttotinc_total-lsttotded_total);
				DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

				Calendar calobj = Calendar.getInstance();
				HSSFRow row = sheet.createRow((short) i);
				row.createCell((short) 0).setCellValue(" ");
				row = sheet.createRow((short) i + 1);
				row.createCell((short) 0).setCellValue("Report Date And Time " + df.format(calobj.getTime()));

			}
			hwb.write(out1);
			out1.close();
			st.close();
			con.close();
		
	} catch (Exception e) {
		System.out.println("into excel type");
		e.printStackTrace();
	}

}



/*
public static void getAttendanceAllEMPLIST(String type, String date, String filepath) {

	System.out.println("getAttendanceAllEMPLIST........................");
	Properties prop = new Properties();
	int brtot[] = new int[17];

	try {

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream stream = classLoader.getResourceAsStream("constant.properties");
		prop.load(stream);
	} catch (Exception e) {
		System.out.println("Error in constant properties Manager " + e);
	}

	try {

		RepoartBean repBean = new RepoartBean();
		Connection con = null;

		FileOutputStream out1 = new FileOutputStream(new File(filepath));
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet(type + "_" + date);

		date = "01-" + date;
		String date1 = ReportDAO.EOM(date);
		int days = Integer.parseInt(date1.substring(0, 2));
		System.out.println(" date F:" + days);
		int i = 0, j = 0;
		for (i = 0; i < days + 4; i++) {
			if (i == 1 || i == 2) {
				sheet.setColumnWidth((short) i, (short) 4000);
			} else {
				sheet.setColumnWidth((short) i, (short) 2000);
			}

		}

		sheet.setColumnWidth((short) i, (short) 4000);

		HSSFCellStyle my_style = hwb.createCellStyle();
		HSSFCellStyle my_style1 = hwb.createCellStyle();

		HSSFFont my_font = hwb.createFont();
		my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		my_style.setFont(my_font);

		HSSFRow rowhead = sheet.createRow((short) 0);
		HSSFCell cell3 = rowhead.createCell((short) 0);
		// sheet.createFreezePane( 0, 10, 0, 10 );

		my_style1.setAlignment((short) 2);
		my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		my_style1.setFont(my_font);

		// cell3=rowhead.createCell((short) 0);
		cell3.setCellValue("SR.NO");
		cell3.setCellStyle(my_style1);

		cell3 = rowhead.createCell((short) 1);
		cell3.setCellValue("EMP_CODE");
		cell3.setCellStyle(my_style1);

		cell3 = rowhead.createCell((short) 2);
		cell3.setCellValue("EMP_NAME");
		cell3.setCellStyle(my_style1);

		cell3 = rowhead.createCell((short) 3);
		cell3.setCellValue("DATE");
		cell3.setCellStyle(my_style1);

		for (i = 4, j = 1; i < days + 4; i++, j++) {
			cell3 = rowhead.createCell((short) i);
			cell3.setCellValue("day" + j);

			cell3.setCellStyle(my_style1);
		}

		
		 * cell3=rowhead.createCell((short) i);
		 * cell3.setCellValue("TOTAL DAYS"); cell3.setCellStyle(my_style1);
		 

		ReportDAO.OpenCon("", "", "", repBean);
		con = repBean.getCn();
		Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		Statement stdoj = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		// int i=1;

		int k = 0;
		String filterstring = "";
		String change = "";
		ResultSet rs = null;

		ResultSet rs1 = null;
		ResultSet rsdoj = null;
		
		 * String sqlg="";
		 

		
		 * sqlg =
		 * "select E.EMPNO,E.EMPCODE,RTRIM(E.FNAME)+' '+RTRIM(E.MNAME)+' '+RTRIM(E.LNAME) AS NAME, from empmast E where (( E.STATUS='A' AND E.DOJ <= '"
		 * +ReportDAO.EOM(date)+"') or" +
		 * " (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM(date)+"' ))";
		 

		String fromEmpmast = " select E.EMPNO,E.EMPCODE,RTRIM(E.FNAME)+' '+RTRIM(E.MNAME)+' '+RTRIM(E.LNAME) AS NAME,E.DOJ ,e.dol from "
				+ " empmast E where  (( E.STATUS='A' AND  E.DOJ <= '"
				+ ReportDAO.EOM(date) + "') or (E.STATUS ='N' And  E.DOL>='" + ReportDAO.BOM(date)
				+ "' )) ORDER BY E.EMPCODE";

		
		 * String sqlg =
		 * " select E.EMPNO,E.EMPCODE,RTRIM(E.FNAME)+' '+RTRIM(E.MNAME)+' '+RTRIM(E.LNAME) AS NAME,E.DOJ ,e.dol,EA.* "
		 * + " from empmast E, Employee_Attendance EA where E.EMPCODE = EA.EMPCODE and "
		 * + "	ATTEND_DATE='" + ReportDAO.EOM(date) + "' " +
		 * " AND (( E.STATUS='A' AND  E.DOJ <= '" + ReportDAO.EOM(date) +
		 * "') or (E.STATUS ='N' And  E.DOL>='" + ReportDAO.BOM(date) + "' ))    " // +
		 * " AND EA.STATUS='PROCESS' " + " ORDER BY E.EMPCODE ";
		 
		
		
		String sqlg = " select E.EMPNO,E.EMPCODE,RTRIM(E.FNAME)+' '+RTRIM(E.MNAME)+' '+RTRIM(E.LNAME) AS NAME,EA.* "
				+ " from empmast E, Employee_Attendance EA where E.EMPCODE = EA.EMPCODE and " + "	ATTEND_DATE='"
				+ ReportDAO.EOM(date) + "' " + " AND (( E.STATUS='A' AND  E.DOJ <= '" + ReportDAO.EOM(date)
				+ "') or (E.STATUS ='N' And  E.DOL>='" + ReportDAO.BOM(date) + "' ))    "
				// + " AND EA.STATUS='PROCESS' "
				+ " ORDER BY E.EMPCODE ";
		
		
		
		

		Statement fEmpmast = con.createStatement();

		System.out.println("this is my queryy UID==>" + sqlg);
		int rowCount = 1;
		float allLeave = 0.0f;
		rs = st1.executeQuery(sqlg);

		HolidayMasterHandler hmh = new HolidayMasterHandler();
		ArrayList<String> weekOff = new ArrayList<String>();
		ArrayList<String> holiday = new ArrayList<String>();
		weekOff = hmh.getweekoff(date);
		int s11[] = new int[8];
		for (i = 0; i < weekOff.size(); i++) {
			s11[i] = Integer.parseInt(weekOff.get(i).substring(0, 2));
			System.out.println("S[] "+s11[i]);
		}
		
		holiday = hmh.getholiday(date);
		int h11[] = new int[32];
		
		System.out.println("holiday.size():"+holiday.size());
		
		for (i = 0; i < holiday.size(); i++) {
			h11[i] = Integer.parseInt(holiday.get(i).substring(0, 2));
			System.out.println("h[] "+h11[i]);
		}
		
		
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();// count coulumn
		int wo = 1;
		int ho = 1;
		int jj = 0;
		int holdYN =0;
		if (rs.next()) {
			rs.previous();
			while (rs.next()) {
				k++;
				
				String doj = "";
				String dol = "";
				String dojDol ="select doj,dol from empmast where empcode='"+rs.getString("EMPCODE")+"'";
				rsdoj =stdoj.executeQuery(dojDol);
				while(rsdoj.next())
				{
					doj=rsdoj.getString(1);
					dol=rsdoj.getString(2);
				}
				
				HSSFRow row = sheet.createRow((short) rowCount++);

				row.createCell((short) 0).setCellValue(k);
				row.createCell((short) 1).setCellValue(rs.getString("EMPCODE"));
				row.createCell((short) 2).setCellValue(rs.getString("NAME"));
				row.createCell((short) 3).setCellValue(ReportDAO.EOM(date));

				int l = 1;
				
				// for NJ and LFT
				int dayDate = 0;
				int dayDatedol = 0;
				// for joining
				if ((doj.substring(0, 4)).equalsIgnoreCase(date.substring(7))) {
					if (ReportDAO.getMonthinString1(doj.substring(5, 7)).equalsIgnoreCase(date.substring(3, 6))) {
						dayDate = Integer.parseInt(doj.substring(8));
					}
				}

				// for leaving
				 System.out.println(" dol : " + dol); 
				if (dol == null) {

				} else {
					if ((dol.substring(0, 4)).equalsIgnoreCase(date.substring(7))) {
						if (ReportDAO.getMonthinString1(dol.substring(5, 7))
								.equalsIgnoreCase(date.substring(3, 6))) {
							dayDatedol = Integer.parseInt(dol.substring(8));
							System.out.println(" dayDate : " + dayDatedol);
						}
					}
				}
				
				
				
				int g = 5;
				int flag = 0;
				for (i = 4; i < days + 4; i++) {
					wo = i - 3;
					ho = i - 3;
					if (dayDate > wo) {
						row.createCell((short) i).setCellValue("NJ");
					} else if (dayDatedol < wo && dayDatedol != 0) {
						row.createCell((short) i).setCellValue("LFT");

					} else if (s11[0] == wo || s11[1] == wo || s11[2] == wo || s11[3] == wo || s11[4] == wo
							|| s11[5] == wo || s11[6] == wo || s11[7] == wo) {
						row.createCell((short) i).setCellValue("WO");
					} else if(h11[0]==ho ||h11[1]==ho || h11[2]==ho || h11[3]==ho ||  h11[4]==ho ||	h11[5]==ho ||	h11[6]==ho ||	h11[7]==ho ||	h11[8]==ho ||  h11[9]==ho || h11[10]==ho
								 ||h11[11]==ho || h11[12]==ho || h11[13]==ho || h11[14]==ho ||	h11[15]==ho ||	h11[16]==ho ||	h11[17]==ho ||	h11[18]==ho ||  h11[19]==ho || h11[20]==ho
								 ||h11[21]==ho || h11[22]==ho || h11[23]==ho || h11[24]==ho ||	h11[25]==ho ||	h11[26]==ho ||	h11[27]==ho ||	h11[28]==ho ||  h11[29]==ho || h11[30]==ho|| h11[31]==ho){
							 row.createCell((short) i).setCellValue("HO");
					}	
					else
					{
					 row.createCell((short) i).setCellValue(rs.getString(rsmd.getColumnName(g)));
					}
					g++;
				}
			}
		} else {
			rs1 = fEmpmast.executeQuery(fromEmpmast);

			while (rs1.next()) {
				k++;
				String doj = "";
				String dol = "";
				HSSFRow row = sheet.createRow((short) rowCount++);
				
				

				row.createCell((short) 0).setCellValue(k);
				row.createCell((short) 1).setCellValue(rs1.getString("EMPCODE"));
				row.createCell((short) 2).setCellValue(rs1.getString("NAME"));
				row.createCell((short) 3).setCellValue(ReportDAO.EOM(date));

				// for joining
				int dayDate = 0;
				int dayDatedol = 0;
				doj = rs1.getString("DOJ");
				if ((doj.substring(0, 4)).equalsIgnoreCase(date.substring(7))) {
					if (ReportDAO.getMonthinString1(doj.substring(5, 7)).equalsIgnoreCase(date.substring(3, 6))) {
						dayDate = Integer.parseInt(doj.substring(8));
					}
				}

				// for leaving

				dol = rs1.getString("dol");
				System.out.println(" dol : " + dol);
				if (dol == null) {

				} else {
					if ((dol.substring(0, 4)).equalsIgnoreCase(date.substring(7))) {
						if (ReportDAO.getMonthinString1(dol.substring(5, 7))
								.equalsIgnoreCase(date.substring(3, 6))) {
							dayDatedol = Integer.parseInt(dol.substring(8));
							System.out.println(" dayDate : " + dayDatedol);
						}
					}
				}
				int g = 5;
				 rs.getString(rsmd.getColumnName(k++)); 
				for (i = 4; i < days + 4; i++) {
					wo = i - 3;
					if (dayDate > wo) {
						row.createCell((short) i).setCellValue("NJ");
					} else if (dayDatedol < wo && dayDatedol != 0) {
						row.createCell((short) i).setCellValue("LFT");

					} else if (s11[0] == wo || s11[1] == wo || s11[2] == wo || s11[3] == wo || s11[4] == wo
							|| s11[5] == wo) {
						row.createCell((short) i).setCellValue("WO");
					} else {
						row.createCell((short) i).setCellValue(" ");
					}

				}

				 row.createCell((short)i).setCellValue("total"); 

				dayDate = 0;
				dayDatedol = 0;
				wo = 0;
			}

		}

		hwb.write(out1);
		out1.close();
		st.close();
		con.close();

		System.out.println("Result OK.....");

	} catch (Exception e) {
		e.printStackTrace();
	}

}

*/



//public static void getAttendanceAllEMPLIST(String PAYREGDT, String imgpath, String filepath, String type, String emptype)
public static void getAttendanceAllEMPLIST(String PAYREGDT, String imgpath, String filepath, String type, String emptype)
{

	System.out.println("in new pay regdao");
	
	System.out.println("newpayreg11 PAYREGDT: "+PAYREGDT);
	System.out.println("newpayreg11 type: "+type);
	System.out.println("newpayreg11 emptype: "+emptype);

	

	// this code is for constant property see constant.properties
	Properties prop = new Properties();
	try {

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream stream = classLoader.getResourceAsStream("constant.properties");
		prop.load(stream);
	} catch (Exception e) {
		System.out.println("Error in constant properties Manager " + e);
	}

	RepoartBean repBean = new RepoartBean();

	Connection con = null;
	String BomDt = "";
	String EomDt = "";
	String StartDt = "";
	StartDt = "01-Dec-1900";
	int lastdat = 0;
	// String dt = PAYREGDT;
	String table_name = null;
	/*
	 * if(TName.equals("before") ) { table_name = "PAYTRAN";
	 * lable1="Before Finalize"; } else if(TName.equals("after") ) {
	 * table_name="YTDTRAN"; lable1="Final After Release"; } else {
	 * table_name="PAYTRAN_STAGE"; lable1="Pending for Release"; }
	 */
	// System.out.println(dt);
	lastdat = (int) Calculate.getDays(PAYREGDT);
	System.out.println("maxdt" + lastdat);
	BomDt = ReportDAO.BOM(PAYREGDT);
	// System.out.println("bomdt "+BomDt);
	EomDt = ReportDAO.EOM(PAYREGDT);
	// System.out.println("eomdt"+EomDt);

	String temp = PAYREGDT.substring(3);
	ResultSet emp = null;
	String EmpSql = "";
	String pBrcd1 = "";
	int tot_no_emp = 0;
	int br_tot_no_emp = 0;
	float tot_absents = 0.0f;
	float totmthsal1 = 0.0f;
	float totearning1 = 0.0f;
	float totearning2 = 0.0f;
	float totactualpay = 0.0f;
	float totmobded = 0.0f;
	float totadvanc = 0.0f;
	float totloan = 0.0f;
	float tottds = 0.0f;

	try {
		if (type.equalsIgnoreCase("G")) {
			ReportDAO.OpenCon("", "", "", repBean);
			con = repBean.getCn();
			Statement st = con.createStatement();

			FileOutputStream out1 = new FileOutputStream(new File(filepath));
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet("PayRegister");
			Calendar currentMonth = Calendar.getInstance();

			HSSFCellStyle my_style = hwb.createCellStyle();
			HSSFFont my_font = hwb.createFont();
			my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			my_style.setFont(my_font);

			HSSFRow rowtitle = sheet.createRow((short) 0);
			HSSFCell cell = rowtitle.createCell((short) 5);
			cell.setCellValue(prop.getProperty("companyName"));
			cell.setCellStyle(my_style);
			HSSFRow rowtitle1 = sheet.createRow((short) 1);
			HSSFCell cell1 = rowtitle1.createCell((short) 3);
			cell1.setCellValue(prop.getProperty("addressForReport"));
			cell1.setCellStyle(my_style);
			HSSFRow rowtitle2 = sheet.createRow((short) 2);
			HSSFCell cell2 = rowtitle2.createCell((short) 5);
			cell2.setCellValue(prop.getProperty("contactForReport"));
			cell2.setCellStyle(my_style);
			HSSFRow rowtitle31 = sheet.createRow((short) 3);
			cell2 = rowtitle31.createCell((short) 5);
			cell2.setCellValue(prop.getProperty("mailForReport"));
			cell2.setCellStyle(my_style);
			HSSFRow rowtitle3 = sheet.createRow((short) 4);
			cell2 = rowtitle3.createCell((short) 4);
			cell2.setCellValue("Salary Sheet For The Month Of :- " + PAYREGDT);
			cell2.setCellStyle(my_style);
			HSSFRow rowtitle4 = sheet.createRow((short) 5);
			rowtitle4.createCell((short) 0).setCellValue("");
			HSSFRow rowtitle5 = sheet.createRow((short) 6);
			rowtitle5.createCell((short) 0).setCellValue("");

			HSSFFont blueFont = hwb.createFont();
			blueFont.setColor(HSSFColor.BLUE.index);

			HSSFCellStyle style = hwb.createCellStyle();
			// style.setFont(blueFont);
			style.setFillForegroundColor(HSSFColor.BLUE.index);

			HSSFRow head = sheet.createRow((short) 7);
			head.createCell((short) 0).setCellValue("");
			HSSFRow heading = sheet.createRow((short) 8);
			HSSFCell cell3 = heading.createCell((short) 0);
			cell3.setCellValue("");
			cell3.setCellStyle(my_style);
			sheet.setColumnWidth((short) 0, (short) 3000);
			sheet.setColumnWidth((short) 1, (short) 7000);
			sheet.setColumnWidth((short) 4, (short) 4000);
			sheet.setColumnWidth((short) 5, (short) 3000);
			sheet.setColumnWidth((short) 6, (short) 4000);
			sheet.setColumnWidth((short) 7, (short) 3000);
			sheet.setColumnWidth((short) 8, (short) 4000);
			sheet.setColumnWidth((short) 9, (short) 3000);
			sheet.setColumnWidth((short) 10, (short) 3000);
			sheet.setColumnWidth((short) 11, (short) 3000);
			sheet.setColumnWidth((short) 12, (short) 3000);
			sheet.setColumnWidth((short) 13, (short) 3000);

			HSSFRow head1 = sheet.createRow((short) 9);
			head1.createCell((short) 0).setCellValue("");
			HSSFRow rowhead = sheet.createRow((short) 10);
			sheet.createFreezePane(0, 11, 0, 11);

			rowhead.createCell((short) 0).setCellValue("Emp Code.");
			rowhead.createCell((short) 1).setCellValue("Employee Name");
			rowhead.createCell((short) 4).setCellValue("CTC");
			rowhead.createCell((short) 5).setCellValue("LOP Days");
			rowhead.createCell((short) 6).setCellValue("Earning 1");
			rowhead.createCell((short) 7).setCellValue("Earning 2");
			rowhead.createCell((short) 8).setCellValue("Mobile Deduction");
			rowhead.createCell((short) 9).setCellValue("Advance Given");
			rowhead.createCell((short) 10).setCellValue("Loan");
			rowhead.createCell((short) 11).setCellValue("TDS");
			rowhead.createCell((short) 12).setCellValue("Net Pay");
			rowhead.createCell((short) 13).setCellValue("");

			// EmpSql = "select distinct p.empno,e.empcode from
			// "+table_name+" p right join EMPMAST e on e.EMPNO = p.EMPNO
			// where TRNDT BETWEEN '" + BomDt + "' AND '" + EomDt + "' order
			// by p.EMPNO";
			EmpSql = "select distinct p.empno,CONVERT(INT, e.empcode) as empcode,t.PRJ_SRNO,t.PRJ_CODE from paytran p right join EMPMAST e on e.EMPNO = p.EMPNO join EMPTRAN t on p.EMPNO = t.EMPNO where TRNDT BETWEEN '"
					+ BomDt + "' AND '" + EomDt + "' and e.STATUS = 'A'"
					+ "and T.EFFDATE = (SELECT E2.EFFDATE FROM EMPTRAN E2 WHERE E2.EMPNO = e.EMPNO AND E2.EFFDATE <= '"
					+ ReportDAO.EOM(PAYREGDT)
					+ "' and E2.srno=(SELECT MAX(E2.SRNO) FROM EMPTRAN E2 WHERE E2.EMPNO = e.EMPNO AND E2.EFFDATE <= '"
					+ ReportDAO.EOM(PAYREGDT) + "')) order by t.PRJ_CODE,empcode";
			System.out.println(EmpSql);
			emp = st.executeQuery(EmpSql);

			List<Integer> results = new ArrayList<Integer>();

			while (emp.next()) {

				results.add(emp.getInt("empno"));
				// System.out.println(emp.getInt("empno"));
			}

			int i = 11;
			for (int empp : results) {

				EmpSql = "select empmast.EMPCODE ,rtrim(empmast.fname)+' '+rtrim(empmast.lname)as  name, doj,t.PRJ_CODE,t.PRJ_SRNO, "
						+ "(select inp_AMT from paytran where trncd = 199 and TRNDT BETWEEN '" + BomDt + "'and '"
						+ EomDt + "' and empno=" + empp + ") ctc, "
						+ "(select cal_amt from paytran where trncd = 301 and TRNDT BETWEEN '" + BomDt + "'and '"
						+ EomDt + "' and empno=" + empp + ") abs_cnt,"
						+ "(select net_amt as earning1 from paytran where trncd = 130 and TRNDT BETWEEN '" + BomDt
						+ "'and '" + EomDt + "' and empno=" + empp + ") earning1,"
						+ "(select net_amt as earning2 from paytran where trncd = 131 and TRNDT BETWEEN '" + BomDt
						+ "'and '" + EomDt + "' and empno=" + empp + ")earning2,"
						+ "(select net_amt as mobded from paytran where trncd = 223 and TRNDT BETWEEN '" + BomDt
						+ "'and '" + EomDt + "' and empno=" + empp + ")mobded,"
						+ "(select net_amt as added from paytran where trncd = 225 and TRNDT BETWEEN '" + BomDt
						+ "'and '" + EomDt + "' and empno=" + empp + ")added,"
						+ "(select net_amt as loan from paytran where trncd = 227 and TRNDT BETWEEN '" + BomDt
						+ "'and '" + EomDt + "' and empno=" + empp + ")loan,"
						+ "(select net_amt as tds from paytran where trncd = 228 and TRNDT BETWEEN '" + BomDt
						+ "'and '" + EomDt + "' and empno=" + empp + ")tds,"
						+ "(select net_amt as payable from paytran where trncd = 999 and TRNDT BETWEEN '" + BomDt
						+ "'and '" + EomDt + "' and empno=" + empp + ")payable "
						+ "from empmast,EMPTRAN t  where empmast.empno=" + empp + " and t.empno = " + empp + " " +

						"  UNION  "
						+ "select empmast.EMPCODE ,rtrim(empmast.fname)+' '+rtrim(empmast.lname)as  name, doj,t.PRJ_CODE,t.PRJ_SRNO, "
						+ "(select inp_AMT from paytran_stage where trncd = 199 and TRNDT BETWEEN '" + BomDt
						+ "'and '" + EomDt + "' and empno=" + empp + ") ctc, "
						+ "(select cal_amt from paytran_stage where trncd = 301 and TRNDT BETWEEN '" + BomDt
						+ "'and '" + EomDt + "' and empno=" + empp + ") abs_cnt,"
						+ "(select net_amt as earning1 from paytran_stage where trncd = 130 and TRNDT BETWEEN '"
						+ BomDt + "'and '" + EomDt + "' and empno=" + empp + ") earning1,"
						+ "(select net_amt as earning2 from paytran_stage where trncd = 131 and TRNDT BETWEEN '"
						+ BomDt + "'and '" + EomDt + "' and empno=" + empp + ")earning2,"
						+ "(select net_amt as mobded from paytran_stage where trncd = 223 and TRNDT BETWEEN '"
						+ BomDt + "'and '" + EomDt + "' and empno=" + empp + ")mobded,"
						+ "(select net_amt as added from paytran_stage where trncd = 225 and TRNDT BETWEEN '"
						+ BomDt + "'and '" + EomDt + "' and empno=" + empp + ")added,"
						+ "(select net_amt as loan from paytran_stage where trncd = 227 and TRNDT BETWEEN '" + BomDt
						+ "'and '" + EomDt + "' and empno=" + empp + ")loan,"
						+ "(select net_amt as tds from paytran_stage where trncd = 228 and TRNDT BETWEEN '" + BomDt
						+ "'and '" + EomDt + "' and empno=" + empp + ")tds,"
						+ "(select net_amt as payable from paytran_stage where trncd = 999 and TRNDT BETWEEN '"
						+ BomDt + "'and '" + EomDt + "' and empno=" + empp + ")payable "
						+ "from empmast,EMPTRAN t  where empmast.empno=" + empp + " and t.empno = " + empp;

				// System.out.println(EmpSql);
				// System.out.println(empp);
				emp = st.executeQuery(EmpSql);

				while (emp.next()) {

					HSSFRow row = sheet.createRow((short) i++);
					row.createCell((short) 0).setCellValue("" + emp.getString("empcode"));
					row.createCell((short) 1).setCellValue("");

					row.createCell((short) 1).setCellValue("" + emp.getString("name"));

					float mthsal = 0.0f;
					float absent = 0.0f;

					/*
					 * EmpSql="select inp_AMT from "
					 * +table_name+" where trncd = 199 and TRNDT BETWEEN '"
					 * +BomDt+"'and '"+EomDt+"' and empno="+empp; emp =
					 * st.executeQuery(EmpSql);
					 * //System.out.println(EmpSql); while(emp.next()){
					 * mthsal = emp.getFloat("inp_AMT");
					 * 
					 * //System.out.println("mth salary "+emp.getString(
					 * "inp_AMT")); }
					 */
					mthsal = emp.getFloat("ctc");
					if (mthsal == 0) {
						mthsal = 0;

						// mthsal = 0.0f;
					} else {

						totmthsal1 = totmthsal1 + mthsal;
					}
					// ctc
					row.createCell((short) 4).setCellValue(mthsal);

					/*
					 * EmpSql = "select cal_amt from "
					 * +table_name+" where trncd = 301 and TRNDT BETWEEN '"
					 * +BomDt+"'and '"+EomDt+"' and empno="+empp; emp =
					 * st.executeQuery(EmpSql); if(emp.next()){
					 * 
					 * absent = emp.getFloat("cal_AMT"); }
					 */
					absent = emp.getFloat("abs_cnt");
					// L.O.P days
					row.createCell((short) 5).setCellValue(absent);
					tot_absents += absent;
					float ear1 = 0.0f;
					/*
					 * EmpSql="select net_amt as earning1 from "
					 * +table_name+" where trncd = 130 and TRNDT BETWEEN '"
					 * +BomDt+"'and '"+EomDt+"' and empno="+empp;
					 * //System.out.println(EmpSql); emp =
					 * st.executeQuery(EmpSql); while(emp.next()){ ear1 =
					 * emp.getFloat("earning1");
					 * 
					 * //System.out.println("earning1"+emp.getString(
					 * "earning1")); }
					 */
					ear1 = emp.getFloat("earning1");
					if (ear1 == 0.0) {
						ear1 = 0;

					} else {
						// earning1 = ear1;
						totearning1 = totearning1 + ear1;
					}

					// earning 1
					row.createCell((short) 6).setCellValue(ear1);

					float ear2 = 0.0f;
					/*
					 * EmpSql="select net_amt as earning2 from "
					 * +table_name+" where trncd = 131 and TRNDT BETWEEN '"
					 * +BomDt+"'and '"+EomDt+"' and empno="+empp;
					 * //System.out.println(EmpSql); emp =
					 * st.executeQuery(EmpSql); while(emp.next()){ ear2 =
					 * emp.getFloat("earning2");
					 * 
					 * //System.out.println("earning2"+emp.getString(
					 * "earning2")); }
					 */
					ear2 = emp.getFloat("earning2");
					if (ear2 == 0.0) {
						ear2 = 0;

					} else {

						totearning2 = totearning2 + ear2;
					}

					// earning 2

					row.createCell((short) 7).setCellValue(ear2);

					float mobded = 0.0f;
					/*
					 * EmpSql="select net_amt as mobded from "
					 * +table_name+" where trncd = 223 and TRNDT BETWEEN '"
					 * +BomDt+"'and '"+EomDt+"' and empno="+empp;
					 * //System.out.println(EmpSql); emp =
					 * st.executeQuery(EmpSql); while(emp.next()){ mobded =
					 * emp.getFloat("mobded");
					 * 
					 * //System.out.println("mobded"+emp.getString("mobded")
					 * ); }
					 */
					mobded = emp.getFloat("mobded");
					if (mobded == 0.0) {
						mobded = 0;

					} else {

						totmobded = totmobded + mobded;
					}

					// mobile ded

					row.createCell((short) 8).setCellValue(mobded);

					float advanc = 0.0f;
					/*
					 * EmpSql="select net_amt as added from "
					 * +table_name+" where trncd = 225 and TRNDT BETWEEN '"
					 * +BomDt+"'and '"+EomDt+"' and empno="+empp;
					 * //System.out.println(EmpSql); emp =
					 * st.executeQuery(EmpSql); while(emp.next()){ advanc =
					 * emp.getFloat("added");
					 * 
					 * //System.out.println("advance"+emp.getString("added")
					 * ); }
					 */
					advanc = emp.getFloat("added");
					if (advanc == 0.0) {
						advanc = 0;

					} else {

						totadvanc = totadvanc + advanc;
					}

					// advance given

					row.createCell((short) 9).setCellValue(advanc);

					float loan = 0.0f;
					/*
					 * EmpSql="select net_amt as loan from "
					 * +table_name+" where trncd = 227 and TRNDT BETWEEN '"
					 * +BomDt+"'and '"+EomDt+"' and empno="+empp;
					 * //System.out.println(EmpSql); emp =
					 * st.executeQuery(EmpSql); while(emp.next()){ loan =
					 * emp.getFloat("loan");
					 * 
					 * //System.out.println("loan "+emp.getString("loan"));
					 * }
					 */
					loan = emp.getFloat("loan");
					if (loan == 0.0) {
						loan = 0;

					} else {

						totloan = totloan + loan;
					}

					// loan

					row.createCell((short) 10).setCellValue(loan);

					float tds = 0.0f;
					/*
					 * EmpSql="select net_amt as tds from "
					 * +table_name+" where trncd = 228 and TRNDT BETWEEN '"
					 * +BomDt+"'and '"+EomDt+"' and empno="+empp;
					 * //System.out.println(EmpSql); emp =
					 * st.executeQuery(EmpSql); while(emp.next()){ tds =
					 * emp.getFloat("tds");
					 * 
					 * //System.out.println("tds "+emp.getString("tds")); }
					 */
					tds = emp.getFloat("tds");
					if (tds == 0.0) {
						tds = 0;

					} else {

						tottds = tottds + tds;
					}

					// tds

					row.createCell((short) 11).setCellValue(tds);

					int payable = 0;
					/*
					 * EmpSql="select net_amt as payable from "
					 * +table_name+" where trncd = 999 and TRNDT BETWEEN '"
					 * +BomDt+"'and '"+EomDt+"' and empno="+empp; emp =
					 * st.executeQuery(EmpSql);
					 * //System.out.println(EmpSql); while(emp.next()){
					 * payable = emp.getInt("payable");
					 * 
					 * //System.out.println("payable "+emp.getString(
					 * "payable")); }
					 */
					payable = emp.getInt("payable");
					if (payable == 0) {
						payable = 0;
					} else {

						totactualpay = totactualpay + payable;

					}
					// actual pay

					row.createCell((short) 12).setCellValue(payable);

				}

			}

			NumberFormat format = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));
			String totpay = format.format(totmthsal1).substring(4);
			String eard1 = format.format(totearning1).substring(4);
			String eard2 = format.format(totearning2).substring(4);
			String totnetpay = format.format(totactualpay).substring(4);
			String tomobded = format.format(totmobded).substring(4);
			String toadv = format.format(totadvanc).substring(4);
			String toloan = format.format(totloan).substring(4);
			String totds = format.format(tottds).substring(4);

			rowhead = sheet.createRow((short) i++);
			rowhead.createCell((short) 0).setCellValue("");
			rowhead.createCell((short) 1).setCellValue("");
			rowhead.createCell((short) 2).setCellValue("TOTAL PAY");
			rowhead.createCell((short) 4).setCellValue(totpay);
			rowhead.createCell((short) 5).setCellValue(tot_absents);
			rowhead.createCell((short) 6).setCellValue(eard1);
			rowhead.createCell((short) 7).setCellValue(eard2);
			rowhead.createCell((short) 8).setCellValue(tomobded);
			rowhead.createCell((short) 9).setCellValue(toadv);
			rowhead.createCell((short) 10).setCellValue(toloan);
			rowhead.createCell((short) 11).setCellValue(totds);
			rowhead.createCell((short) 12).setCellValue(totnetpay);
			rowhead.createCell((short) 14).setCellValue("");

			DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

			Calendar calobj = Calendar.getInstance();
			HSSFRow row = sheet.createRow((short) i);
			row.createCell((short) 0).setCellValue(" ");
			row = sheet.createRow((short) i + 1);
			row.createCell((short) 0).setCellValue("Report Date And Time " + df.format(calobj.getTime()));

			hwb.write(out1);
			out1.close();
			/*
			 * final int BUFSIZE = 4096; File file = new File(filepath); int
			 * length = 0;
			 */

			System.out.println("Your excel file has been generated!");

			st.close();
			con.close();
		}

		else if (type.equalsIgnoreCase("I")) {

			ReportDAO.OpenCon("", "", "", repBean);
			con = repBean.getCn();
			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			System.out.println("in income head");
			FileOutputStream out1 = new FileOutputStream(new File(filepath));

			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet("PayRegister");
			Calendar currentMonth = Calendar.getInstance();

			HSSFCellStyle my_style = hwb.createCellStyle();
			HSSFCellStyle my_style1 = hwb.createCellStyle();

			HSSFFont my_font = hwb.createFont();
			my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			my_style.setFont(my_font);

			HSSFRow rowtitle = sheet.createRow((short) 0);
			HSSFCell cell = rowtitle.createCell((short) 8);
			cell.setCellValue(prop.getProperty("companyName"));
			cell.setCellStyle(my_style);
			HSSFRow rowtitle1 = sheet.createRow((short) 1);
			HSSFCell cell1 = rowtitle1.createCell((short) 6);
			cell1.setCellValue(prop.getProperty("addressForReport"));
			cell1.setCellStyle(my_style);
			HSSFRow rowtitle2 = sheet.createRow((short) 2);
			HSSFCell cell2 = rowtitle2.createCell((short) 8);
			cell2.setCellValue(prop.getProperty("contactForReport"));
			cell2.setCellStyle(my_style);
			HSSFRow rowtitle31 = sheet.createRow((short) 3);
			cell2 = rowtitle31.createCell((short) 8);
			cell2.setCellValue(prop.getProperty("mailForReport"));
			cell2.setCellStyle(my_style);
			HSSFRow rowtitle3 = sheet.createRow((short) 4);
			cell2 = rowtitle3.createCell((short) 7);
			cell2.setCellValue("Salary Sheet For The Month Of :- " + PAYREGDT);
			cell2.setCellStyle(my_style);
			HSSFRow rowtitle4 = sheet.createRow((short) 5);
			rowtitle4.createCell((short) 0).setCellValue("");
			HSSFRow rowtitle5 = sheet.createRow((short) 6);
			rowtitle5.createCell((short) 0).setCellValue("");

			HSSFFont blueFont = hwb.createFont();
			blueFont.setColor(HSSFColor.BLUE.index);

			HSSFCellStyle style = hwb.createCellStyle();
			// style.setFont(blueFont);
			style.setFillForegroundColor(HSSFColor.BLUE.index);

			/*
			 * if(TName.equalsIgnoreCase("after")) { HSSFRow headin =
			 * sheet.createRow((short)8); HSSFCell cell4 = headin
			 * .createCell((short) 6); cell4.
			 * setCellValue("Salary Sheet For The Income Heads for the Month Of :- "
			 * +PAYREGDT+" "); cell4.setCellStyle(my_style);
			 * 
			 * } else if(TName.equalsIgnoreCase("before")) { HSSFRow headin
			 * = sheet.createRow((short)8); HSSFCell cell4 = headin
			 * .createCell((short) 6); cell4.
			 * setCellValue("Salary Sheet For The Income Heads for the Month Of :-  "
			 * +PAYREGDT+" (Before Finalize) ");
			 * cell4.setCellStyle(my_style);
			 * 
			 * } else { HSSFRow headin = sheet.createRow((short)8); HSSFCell
			 * cell4 = headin .createCell((short) 6); cell4.
			 * setCellValue("Salary Sheet For The Income Heads for the Month Of :- "
			 * +PAYREGDT+" (Pending for Release)");
			 * cell4.setCellStyle(my_style);
			 * 
			 * }
			 */

			int i = 10;
			float basic_total = 0.0f;
			float lstbasic_total = 0.0f;
			float currda_total = 0.0f;
			float lstcurrda_total = 0.0f;
			float hra_total = 0.0f;
			float lsthra_total = 0.0f;
			float medical_total = 0.0f;
			float lstmedical_total = 0.0f;
			float edu_total = 0.0f;
			float lstedu_total = 0.0f;
			float splall_total = 0.0f;
			float lstsplall_total = 0.0f;
			float convall_total = 0.0f;
			float lstconvall_total = 0.0f;
			float washingall_total = 0.0f;
			float lstwashingall_total = 0.0f;
			float bonus_total = 0.0f;
			float lstbonus_total = 0.0f;
			float minins_total = 0.0f;
			float lstminins_total = 0.0f;
			float addlss_total = 0.0f;
			float lstaddlss_total = 0.0f;
			float col_total = 0.0f;
			float lstcol_total = 0.0f;
			float special_total = 0.0f;
			float lstspecial_total = 0.0f;
			float addinc_total = 0.0f;
			float lstaddinc_total = 0.0f;
			float totinc_total = 0.0f;
			float lsttotinc_total = 0.0f;
			float totded_total = 0.0f;
			float lsttotded_total = 0.0f;
			float netpay_total = 0.0f;
			float lstnetpay_total = 0.0f;
			float eepf_total = 0.0f;

			EmpSql = "select CONVERT(INT, empmast.empcode) as EMPCODE ,rtrim(empmast.fname)+' '+rtrim(empmast.mname)+' '+rtrim(empmast.lname)as name,empmast.empno,t.PRJ_SRNO,"
					+ "p1.NET_AMT as basic,p2.NET_AMT as payable,p3.NET_AMT as hra,p4.NET_AMT as medical,"
					+ "p5.NET_AMT as education,p6.NET_AMT as convall,p7.NET_AMT as min_ins," + "p10.NET_AMT as col,"
					+ "(select net_amt from  PAYTRAN where TRNCD = 127 and trndt BETWEEN '" + BomDt + "'and '"
					+ EomDt + "' and EMPNO = empmast.EMPNO) as addless," +
					// "(select net_amt from PAYTRAN where TRNCD = 129 and
					// trndt BETWEEN '"+BomDt+"'and '"+EomDt+"' and EMPNO =
					// empmast.EMPNO) as special, " +
					"(select net_amt from  PAYTRAN where TRNCD = 107 and trndt BETWEEN '" + BomDt + "'and '" + EomDt
					+ "' and EMPNO = empmast.EMPNO) as splall,"
					+ "(select net_amt from  PAYTRAN where TRNCD = 102 and trndt BETWEEN '" + BomDt + "'and '"
					+ EomDt + "' and EMPNO = empmast.EMPNO) as curda,"
					+ "(select net_amt from  PAYTRAN where TRNCD = 115 and trndt BETWEEN '" + BomDt + "'and '"
					+ EomDt + "' and EMPNO = empmast.EMPNO) as washall,"
					+ "(select net_amt from  PAYTRAN where TRNCD = 135 and trndt BETWEEN '" + BomDt + "'and '"
					+ EomDt + "' and EMPNO = empmast.EMPNO) as bonusall,"
					+ "(select SUM(net_amt) from  PAYTRAN where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198) and TRNCD not in(101,103,104,105,108,126,127,128,107,102,115,135) and trndt BETWEEN '"
					+ BomDt + "'and '" + EomDt + "' and EMPNO = empmast.EMPNO) as addinc,"
					+ "(select SUM(net_amt) from  PAYTRAN where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 200 and 299) and trndt BETWEEN '"
					+ BomDt + "'and '" + EomDt + "' and EMPNO = empmast.EMPNO) as totded, "
					+ "(select SUM(net_amt) from  PAYTRAN where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198) and trndt BETWEEN '"
					+ BomDt + "'and '" + EomDt + "' and EMPNO = empmast.EMPNO) as totear "
					+ "from EMPMAST empmast join "
					+ " PAYTRAN p1 on empmast.EMPNO = p1.EMPNO join  PAYTRAN p2 on empmast.EMPNO = p2.EMPNO "
					+ "join  PAYTRAN p3 on empmast.EMPNO = p3.EMPNO "
					+ "join  PAYTRAN p4 on empmast.EMPNO = p4.EMPNO "
					+ "join  PAYTRAN p5 on empmast.EMPNO = p5.EMPNO "
					+ "join  PAYTRAN p6 on empmast.EMPNO = p6.EMPNO "
					+ "join  PAYTRAN p7 on empmast.EMPNO = p7.EMPNO "
					+ "join  PAYTRAN p10 on empmast.EMPNO = p10.EMPNO "
					+ "join EMPTRAN t on t.EMPNO = empmast.EMPNO where " + "p1.TRNCD = 101 and p1.TRNDT BETWEEN '"
					+ BomDt + "'and '" + EomDt + "' " + "and p2.TRNCD = 999 and p2.TRNDT BETWEEN '" + BomDt
					+ "'and '" + EomDt + "' " + "and p3.TRNCD = 103 and p3.TRNDT BETWEEN '" + BomDt + "'and '"
					+ EomDt + "' " + "and p4.TRNCD = 104 and p4.TRNDT BETWEEN '" + BomDt + "'and '" + EomDt + "' "
					+ "and p5.TRNCD = 105 and p5.TRNDT BETWEEN '" + BomDt + "'and '" + EomDt + "' "
					+ "and p6.TRNCD = 108 and p6.TRNDT BETWEEN '" + BomDt + "'and '" + EomDt + "' "
					+ "and p7.TRNCD = 126 and p7.TRNDT BETWEEN '" + BomDt + "'and '" + EomDt + "' "
					+ "and p10.TRNCD = 128 and p10.TRNDT BETWEEN '" + BomDt + "'and '" + EomDt + "' " +
					// "and empmast.STATUS = 'A'" +
					" and T.srno =(SELECT MAX(E2.SRNO) FROM EMPTRAN E2 WHERE E2.EMPNO = empmast.EMPNO AND E2.EFFDATE <= '"
					+ ReportDAO.EOM(PAYREGDT) + "') " +

					" UNION  select CONVERT(INT, empmast.empcode) as EMPCODE ,rtrim(empmast.fname)+' '+rtrim(empmast.mname)+' '+rtrim(empmast.lname)as name,empmast.empno,t.PRJ_SRNO,"
					+ "p1.NET_AMT as basic,p2.NET_AMT as payable,p3.NET_AMT as hra,p4.NET_AMT as medical,"
					+ "p5.NET_AMT as education,p6.NET_AMT as convall,p7.NET_AMT as min_ins," + "p10.NET_AMT as col,"
					+ "(select net_amt from   paytran_stage  where TRNCD = 127 and trndt BETWEEN '" + BomDt
					+ "'and '" + EomDt + "' and EMPNO = empmast.EMPNO) as addless," +
					// "(select net_amt from paytran_stage where TRNCD = 129
					// and trndt BETWEEN '"+BomDt+"'and '"+EomDt+"' and
					// EMPNO = empmast.EMPNO) as special, " +
					"(select net_amt from   paytran_stage  where TRNCD = 107 and trndt BETWEEN '" + BomDt + "'and '"
					+ EomDt + "' and EMPNO = empmast.EMPNO) as splall,"
					+ "(select net_amt from   paytran_stage  where TRNCD = 102 and trndt BETWEEN '" + BomDt
					+ "'and '" + EomDt + "' and EMPNO = empmast.EMPNO) as curda,"
					+ "(select net_amt from   paytran_stage  where TRNCD = 115 and trndt BETWEEN '" + BomDt
					+ "'and '" + EomDt + "' and EMPNO = empmast.EMPNO) as washall,"
					+ "(select net_amt from   paytran_stage  where TRNCD = 135 and trndt BETWEEN '" + BomDt
					+ "'and '" + EomDt + "' and EMPNO = empmast.EMPNO) as bonusall,"
					+ "(select SUM(net_amt) from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198) and TRNCD not in(101,103,104,105,108,126,127,128,107,102,115,135) and trndt BETWEEN '"
					+ BomDt + "'and '" + EomDt + "' and EMPNO = empmast.EMPNO) as addinc,"
					+ "(select SUM(net_amt) from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 200 and 299) and trndt BETWEEN '"
					+ BomDt + "'and '" + EomDt + "' and EMPNO = empmast.EMPNO) as totded, "
					+ "(select SUM(net_amt) from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198) and trndt BETWEEN '"
					+ BomDt + "'and '" + EomDt + "' and EMPNO = empmast.EMPNO) as totear "
					+ "from EMPMAST empmast join "
					+ "  paytran_stage  p1 on empmast.EMPNO = p1.EMPNO join   paytran_stage  p2 on empmast.EMPNO = p2.EMPNO "
					+ "join   paytran_stage  p3 on empmast.EMPNO = p3.EMPNO "
					+ "join   paytran_stage  p4 on empmast.EMPNO = p4.EMPNO "
					+ "join   paytran_stage  p5 on empmast.EMPNO = p5.EMPNO "
					+ "join   paytran_stage  p6 on empmast.EMPNO = p6.EMPNO "
					+ "join   paytran_stage  p7 on empmast.EMPNO = p7.EMPNO "
					+ "join   paytran_stage  p10 on empmast.EMPNO = p10.EMPNO "
					+ "join EMPTRAN t on t.EMPNO = empmast.EMPNO where " + "p1.TRNCD = 101 and p1.TRNDT BETWEEN '"
					+ BomDt + "'and '" + EomDt + "' " + "and p2.TRNCD = 999 and p2.TRNDT BETWEEN '" + BomDt
					+ "'and '" + EomDt + "' " + "and p3.TRNCD = 103 and p3.TRNDT BETWEEN '" + BomDt + "'and '"
					+ EomDt + "' " + "and p4.TRNCD = 104 and p4.TRNDT BETWEEN '" + BomDt + "'and '" + EomDt + "' "
					+ "and p5.TRNCD = 105 and p5.TRNDT BETWEEN '" + BomDt + "'and '" + EomDt + "' "
					+ "and p6.TRNCD = 108 and p6.TRNDT BETWEEN '" + BomDt + "'and '" + EomDt + "' "
					+ "and p7.TRNCD = 126 and p7.TRNDT BETWEEN '" + BomDt + "'and '" + EomDt + "' "
					+ "and p10.TRNCD = 128 and p10.TRNDT BETWEEN '" + BomDt + "'and '" + EomDt + "' " +
					// "and empmast.STATUS = 'A'" +
					" and T.srno =(SELECT MAX(E2.SRNO) FROM EMPTRAN E2 WHERE E2.EMPNO = empmast.EMPNO AND E2.EFFDATE <= '"
					+ ReportDAO.EOM(PAYREGDT) + "') " +

					"order by t.PRJ_SRNO,EMPCODE";
			System.out.println(EmpSql);
			ResultSet rs = st.executeQuery(EmpSql);

			while (rs.next()) {

				String prj_name = null;
				String prj_code = null;
				Connection conn = ConnectionManager.getConnectionTech();
				Statement stmt = conn.createStatement();
				String prjquery = "select Site_Name,Project_Code from Project_Sites where SITE_ID = '"
						+ rs.getString("PRJ_SRNO") + "'";
				// System.out.println(prjquery);
				ResultSet prj = stmt.executeQuery(prjquery);
				if (prj.next()) {
					prj_name = prj.getString("Site_Name");
					prj_code = prj.getString("Project_Code");
				}
				pBrcd1 = rs.getString("PRJ_SRNO");
				br_tot_no_emp = 0;

				HSSFRow head1 = sheet.createRow((short) i++);
				HSSFCell cell4 = head1.createCell((short) 0);
				cell4.setCellValue(
						" Employee's Salary List For Project Site : " + prj_name + " (" + prj_code + ")");
				cell4.setCellStyle(my_style);

				sheet.setColumnWidth((short) 0, (short) 3000);
				sheet.setColumnWidth((short) 1, (short) 7000);
				sheet.setColumnWidth((short) 4, (short) 4000);
				sheet.setColumnWidth((short) 5, (short) 4000);
				sheet.setColumnWidth((short) 6, (short) 4000);
				sheet.setColumnWidth((short) 7, (short) 4000);
				sheet.setColumnWidth((short) 8, (short) 4000);
				sheet.setColumnWidth((short) 9, (short) 4000);
				sheet.setColumnWidth((short) 10, (short) 4000);
				sheet.setColumnWidth((short) 11, (short) 4000);
				sheet.setColumnWidth((short) 12, (short) 4000);
				sheet.setColumnWidth((short) 13, (short) 4000);
				sheet.setColumnWidth((short) 14, (short) 4000);
				sheet.setColumnWidth((short) 15, (short) 4000);
				sheet.setColumnWidth((short) 16, (short) 4000);
				sheet.setColumnWidth((short) 17, (short) 4000);
				sheet.setColumnWidth((short) 18, (short) 4000);
				sheet.setColumnWidth((short) 19, (short) 4000);

				prj.close();
				stmt.close();

				/*
				 * my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				 * my_style1.setFont(my_font);
				 */

				head1 = sheet.createRow((short) i++);
				cell4 = head1.createCell((short) 0);
				cell4.setCellValue("EMP CODE");
				cell4.setCellStyle(my_style1);
				cell4 = head1.createCell((short) 1);
				cell4.setCellValue("EMPNAME");
				cell4.setCellStyle(my_style1);
				cell4 = head1.createCell((short) 4);
				cell4.setCellValue("Basic");
				cell4.setCellStyle(my_style1);
				cell4 = head1.createCell((short) 5);
				cell4.setCellValue("Current D.A");
				cell4.setCellStyle(my_style1);
				cell4 = head1.createCell((short) 6);
				cell4.setCellValue("H.R.A");
				cell4.setCellStyle(my_style1);
				cell4 = head1.createCell((short) 7);
				cell4.setCellValue("Medical");
				cell4.setCellStyle(my_style1);
				cell4 = head1.createCell((short) 8);
				cell4.setCellValue("Education");
				cell4.setCellStyle(my_style1);
				cell4 = head1.createCell((short) 9);
				cell4.setCellValue("Spl All");
				cell4.setCellStyle(my_style1);
				cell4 = head1.createCell((short) 10);
				cell4.setCellValue("Conv All");
				cell4.setCellStyle(my_style1);
				cell4 = head1.createCell((short) 11);
				cell4.setCellValue("Washing All");
				cell4.setCellStyle(my_style1);
				cell4 = head1.createCell((short) 12);
				cell4.setCellValue("Bonus");
				cell4.setCellStyle(my_style1);
				cell4 = head1.createCell((short) 13);
				cell4.setCellValue("Min Insurance");
				cell4.setCellStyle(my_style1);
				cell4 = head1.createCell((short) 14);
				cell4.setCellValue("Add less Amt");
				cell4.setCellStyle(my_style1);
				cell4 = head1.createCell((short) 15);
				cell4.setCellValue("Col");
				cell4.setCellStyle(my_style1);
				cell4 = head1.createCell((short) 16);
				cell4.setCellValue("Add income");
				cell4.setCellStyle(my_style1);
				cell4 = head1.createCell((short) 17);
				cell4.setCellValue("Tot income");
				cell4.setCellStyle(my_style1);
				cell4 = head1.createCell((short) 18);
				cell4.setCellValue("Tot deduction");
				cell4.setCellStyle(my_style1);
				cell4 = head1.createCell((short) 19);
				cell4.setCellValue("Net pay");
				cell4.setCellStyle(my_style1);

				while (pBrcd1.equals(rs.getString("PRJ_SRNO"))) {
					basic_total = basic_total + rs.getFloat("basic");

					head1 = sheet.createRow((short) i++);
					cell4 = head1.createCell((short) 0);
					cell4.setCellValue("" + rs.getString("EMPCODE"));
					cell4 = head1.createCell((short) 1);
					cell4.setCellValue("" + rs.getString("name"));
					cell4 = head1.createCell((short) 4);
					cell4.setCellValue(rs.getFloat("basic"));

					if (rs.getFloat("curda") == 0.0) {
						cell4 = head1.createCell((short) 5);
						cell4.setCellValue("");
					} else {
						float curda = rs.getFloat("curda");
						currda_total = currda_total + curda;
						cell4 = head1.createCell((short) 5);
						cell4.setCellValue(curda);
					}
					hra_total = hra_total + rs.getFloat("hra");

					cell4 = head1.createCell((short) 6);
					cell4.setCellValue(rs.getFloat("hra"));

					medical_total = medical_total = rs.getFloat("medical");
					cell4 = head1.createCell((short) 7);
					cell4.setCellValue(rs.getFloat("medical"));

					edu_total = edu_total = rs.getFloat("education");
					cell4 = head1.createCell((short) 8);
					cell4.setCellValue(rs.getFloat("education"));

					if (rs.getFloat("splall") == 0.0) {
						cell4 = head1.createCell((short) 9);
						cell4.setCellValue("");

					} else {
						float splall1 = rs.getFloat("splall");
						splall_total = splall_total + splall1;
						cell4 = head1.createCell((short) 9);
						cell4.setCellValue(splall1);
					}

					convall_total = convall_total + rs.getFloat("convall");
					cell4 = head1.createCell((short) 10);
					cell4.setCellValue(rs.getFloat("convall"));

					if (rs.getFloat("washall") == 0.0) {
						cell4 = head1.createCell((short) 11);
						cell4.setCellValue("");
					} else {
						float washing = rs.getFloat("washall");
						washingall_total = washingall_total = rs.getFloat("washall");
						cell4 = head1.createCell((short) 11);
						cell4.setCellValue(washing);
					}

					if (rs.getFloat("bonusall") == 0.0) {

						cell4 = head1.createCell((short) 12);
						cell4.setCellValue("");
					} else {
						float bonus = rs.getFloat("bonusall");
						bonus_total = bonus_total + rs.getFloat("bonusall");

						cell4 = head1.createCell((short) 12);
						cell4.setCellValue(bonus);
					}

					minins_total = minins_total + +rs.getFloat("min_ins");
					cell4 = head1.createCell((short) 13);
					cell4.setCellValue(rs.getFloat("min_ins"));

					if (rs.getFloat("addless") == 0.0) {
						cell4 = head1.createCell((short) 14);
						cell4.setCellValue("");
					} else {
						float addless = rs.getFloat("addless");
						addlss_total = addlss_total + addless;
						cell4 = head1.createCell((short) 14);
						cell4.setCellValue(addless);
					}

					col_total = col_total + +rs.getFloat("col");
					cell4 = head1.createCell((short) 15);
					cell4.setCellValue(rs.getFloat("col"));

					/*
					 * if(rs.getFloat("special")==0.0){ PdfPCell cell15 =
					 * new PdfPCell(new Phrase("",f1));
					 * datatab.addCell(cell15); } else{ float spcl =
					 * rs.getFloat("special"); special_total = special_total
					 * + spcl; PdfPCell cell15 = new PdfPCell(new
					 * Phrase(spcl,f1));
					 * cell15.setHorizontalAlignment(Element.ALIGN_RIGHT);
					 * datatab.addCell(cell15); }
					 */

					if (rs.getFloat("addinc") == 0.0) {
						cell4 = head1.createCell((short) 16);
						cell4.setCellValue("");
					} else {
						float add_inc = rs.getFloat("addinc");
						addinc_total = addinc_total + add_inc;
						cell4 = head1.createCell((short) 16);
						cell4.setCellValue(add_inc);
					}

					totinc_total = totinc_total + rs.getFloat("totear");
					cell4 = head1.createCell((short) 17);
					cell4.setCellValue(rs.getFloat("totear"));

					totded_total = totded_total + rs.getFloat("totded");
					cell4 = head1.createCell((short) 18);
					cell4.setCellValue(rs.getFloat("totded"));

					netpay_total = netpay_total + rs.getFloat("payable");
					cell4 = head1.createCell((short) 19);
					cell4.setCellValue(rs.getFloat("payable"));

					tot_no_emp = tot_no_emp + 1;
					br_tot_no_emp = br_tot_no_emp + 1;
					if (!rs.next()) {
						break;
					}
					if (!pBrcd1.equals(rs.getString("PRJ_SRNO"))) {
						rs.previous();
						break;
					}
				}

				head1 = sheet.createRow((short) i++);
				cell4 = head1.createCell((short) 0);
				cell4.setCellValue("");
				cell4 = head1.createCell((short) 1);
				cell4.setCellValue("");
				cell4 = head1.createCell((short) 2);
				cell4.setCellValue("TOTAL :");
				cell4.setCellStyle(my_style);
				cell4 = head1.createCell((short) 4);
				cell4.setCellValue(basic_total);

				lstbasic_total = lstbasic_total + basic_total;
				basic_total = 0.0f;

				cell4 = head1.createCell((short) 5);
				cell4.setCellValue(currda_total);
				lstcurrda_total = lstcurrda_total + currda_total;
				currda_total = 0.0f;

				cell4 = head1.createCell((short) 6);
				cell4.setCellValue(hra_total);
				lsthra_total = lsthra_total + hra_total;
				hra_total = 0.0f;

				cell4 = head1.createCell((short) 7);
				cell4.setCellValue(medical_total);
				lstmedical_total = lstmedical_total + medical_total;
				medical_total = 0.0f;

				cell4 = head1.createCell((short) 8);
				cell4.setCellValue(edu_total);
				lstedu_total = lstedu_total + edu_total;
				edu_total = 0.0f;

				cell4 = head1.createCell((short) 9);
				cell4.setCellValue(splall_total);
				lstsplall_total = lstsplall_total = splall_total;
				splall_total = 0.0f;

				cell4 = head1.createCell((short) 10);
				cell4.setCellValue(convall_total);
				lstconvall_total = lstconvall_total = convall_total;
				convall_total = 0.0f;

				cell4 = head1.createCell((short) 11);
				cell4.setCellValue(washingall_total);
				lstwashingall_total = lstwashingall_total + washingall_total;
				washingall_total = 0.0f;

				cell4 = head1.createCell((short) 12);
				cell4.setCellValue(bonus_total);
				lstbonus_total = lstbonus_total + bonus_total;
				bonus_total = 0.0f;

				cell4 = head1.createCell((short) 13);
				cell4.setCellValue(minins_total);
				lstminins_total = lstminins_total + minins_total;
				minins_total = 0.0f;

				cell4 = head1.createCell((short) 14);
				cell4.setCellValue(addlss_total);
				lstaddlss_total = lstaddlss_total + addlss_total;
				addlss_total = 0.0f;

				cell4 = head1.createCell((short) 15);
				cell4.setCellValue(col_total);
				lstcol_total = lstcol_total + col_total;
				col_total = 0.0f;

				/*
				 * PdfPCell cell15 = new PdfPCell(new
				 * Phrase(special_total,f1));
				 * cell15.setHorizontalAlignment(Element.ALIGN_RIGHT);
				 * datatot.addCell(cell15); lstspecial_total =
				 * lstspecial_total + special_total ; special_total = 0.0f;
				 */

				cell4 = head1.createCell((short) 16);
				cell4.setCellValue(addinc_total);
				lstaddinc_total = lstaddinc_total + addinc_total;
				addinc_total = 0.0f;

				cell4 = head1.createCell((short) 17);
				cell4.setCellValue(totinc_total);
				lsttotinc_total = lsttotinc_total + totinc_total;
				totinc_total = 0.0f;

				cell4 = head1.createCell((short) 18);
				cell4.setCellValue(totded_total);
				lsttotded_total = lsttotded_total + totded_total;
				totded_total = 0.0f;

				cell4 = head1.createCell((short) 19);
				cell4.setCellValue(netpay_total);
				lstnetpay_total = lstnetpay_total + netpay_total;
				netpay_total = 0.0f;

				head1 = sheet.createRow((short) i++);
				head1.createCell((short) 0).setCellValue("Total Employee(s) : " + br_tot_no_emp);
				head1 = sheet.createRow((short) i++);
				head1.createCell((short) 0).setCellValue("");
				head1 = sheet.createRow((short) i++);
				head1.createCell((short) 0).setCellValue("");
			}

			HSSFRow head1 = sheet.createRow((short) i++);
			HSSFCell cell4;
			head1.createCell((short) 0).setCellValue("Total Employee(s) : " + tot_no_emp);

			System.out.println("Total No Of Employee :- " + tot_no_emp);

			head1 = sheet.createRow((short) i++);
			head1 = sheet.createRow((short) i++);
			cell4 = head1.createCell((short) 2);
			cell4.setCellValue("SUMMARY");
			cell4.setCellStyle(my_style);
			cell4 = head1.createCell((short) 4);
			cell4.setCellValue("Basic");
			cell4.setCellStyle(my_style);
			cell4 = head1.createCell((short) 5);
			cell4.setCellValue("Current D.A");
			cell4.setCellStyle(my_style);
			cell4 = head1.createCell((short) 6);
			cell4.setCellValue("H.R.A");
			cell4.setCellStyle(my_style);
			cell4 = head1.createCell((short) 7);
			cell4.setCellValue("Medical");
			cell4.setCellStyle(my_style);
			cell4 = head1.createCell((short) 8);
			cell4.setCellValue("Education");
			cell4.setCellStyle(my_style);
			cell4 = head1.createCell((short) 9);
			cell4.setCellValue("Spl All");
			cell4.setCellStyle(my_style);
			cell4 = head1.createCell((short) 10);
			cell4.setCellValue("Conv All");
			cell4.setCellStyle(my_style);
			cell4 = head1.createCell((short) 11);
			cell4.setCellValue("Washing All");
			cell4.setCellStyle(my_style);
			cell4 = head1.createCell((short) 12);
			cell4.setCellValue("Bonus");
			cell4.setCellStyle(my_style);
			cell4 = head1.createCell((short) 13);
			cell4.setCellValue("Min Insurance");
			cell4.setCellStyle(my_style);
			cell4 = head1.createCell((short) 14);
			cell4.setCellValue("Add less Amt");
			cell4.setCellStyle(my_style);
			cell4 = head1.createCell((short) 15);
			cell4.setCellValue("Col");
			cell4.setCellStyle(my_style);
			cell4 = head1.createCell((short) 16);
			cell4.setCellValue("Add income");
			cell4.setCellStyle(my_style);
			cell4 = head1.createCell((short) 17);
			cell4.setCellValue("Tot income");
			cell4.setCellStyle(my_style);
			cell4 = head1.createCell((short) 18);
			cell4.setCellValue("Tot deduction");
			cell4.setCellStyle(my_style);
			cell4 = head1.createCell((short) 19);
			cell4.setCellValue("Net pay");
			cell4.setCellStyle(my_style);

			head1 = sheet.createRow((short) i++);
			cell4 = head1.createCell((short) 2);
			cell4.setCellValue("Gross Total:");
			cell4.setCellStyle(my_style);
			cell4 = head1.createCell((short) 4);
			cell4.setCellValue(lstbasic_total);
			cell4 = head1.createCell((short) 5);
			cell4.setCellValue(lstcurrda_total);
			cell4 = head1.createCell((short) 6);
			cell4.setCellValue(lsthra_total);
			cell4 = head1.createCell((short) 7);
			cell4.setCellValue(lstmedical_total);
			cell4 = head1.createCell((short) 8);
			cell4.setCellValue(lstedu_total);
			cell4 = head1.createCell((short) 9);
			cell4.setCellValue(lstsplall_total);
			cell4 = head1.createCell((short) 10);
			cell4.setCellValue(lstconvall_total);
			cell4 = head1.createCell((short) 11);
			cell4.setCellValue(lstwashingall_total);
			cell4 = head1.createCell((short) 12);
			cell4.setCellValue(lstbonus_total);
			cell4 = head1.createCell((short) 13);
			cell4.setCellValue(lstminins_total);
			cell4 = head1.createCell((short) 14);
			cell4.setCellValue(lstaddlss_total);
			cell4 = head1.createCell((short) 15);
			cell4.setCellValue(lstcol_total);
			cell4 = head1.createCell((short) 16);
			cell4.setCellValue(lstaddinc_total);
			cell4 = head1.createCell((short) 17);
			cell4.setCellValue(lsttotinc_total);
			cell4 = head1.createCell((short) 18);
			cell4.setCellValue(lsttotded_total);
			cell4 = head1.createCell((short) 19);
			cell4.setCellValue(lstnetpay_total);

			DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

			Calendar calobj = Calendar.getInstance();
			HSSFRow row = sheet.createRow((short) i);
			row.createCell((short) 0).setCellValue(" ");
			row = sheet.createRow((short) i + 1);
			row.createCell((short) 0).setCellValue("Report Date And Time " + df.format(calobj.getTime()));

			hwb.write(out1);
			out1.close();
			/*
			 * final int BUFSIZE = 4096; File file = new File(filepath); int
			 * length = 0;
			 */

			System.out.println("last hra = " + lsthra_total);
			System.out.println("last medical = " + lstmedical_total);
			System.out.println("last education = " + lstedu_total);
			System.out.println("last splall = " + lstsplall_total);
			System.out.println("last convall = " + lstconvall_total);
			System.out.println("last washing = " + lstwashingall_total);
			System.out.println("last bonus = " + lstbonus_total);
			System.out.println("last minins = " + lstminins_total);
			System.out.println("last addless = " + lstaddlss_total);
			System.out.println("last col = " + lstcol_total);
			// System.out.println("last special = "+lstspecial_total);
			System.out.println("last addinc = " + lstaddinc_total);
			System.out.println("last totinc = " + lsttotinc_total);
			System.out.println("last totded = " + lsttotded_total);
			System.out.println("last totnetpay = " + lstnetpay_total);

			st.close();
			con.close();

		} 
		else if (type.equalsIgnoreCase("ID")) {

			ReportDAO.OpenCon("", "", "", repBean);
			con = repBean.getCn();
			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String report_type[] = { "t.PRJ_SRNO", "t.DEPT" };

			FileOutputStream out1 = new FileOutputStream(new File(filepath));

			HSSFWorkbook hwb = new HSSFWorkbook();
			for (int x = 0; x < report_type.length; x++) {
				tot_no_emp = 0;
				HSSFSheet sheet = hwb.createSheet(x == 0 ? "Project" : "Depertment");
				Calendar currentMonth = Calendar.getInstance();

				HSSFCellStyle my_style = hwb.createCellStyle();
				HSSFFont my_font = hwb.createFont();
				my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				my_style.setFont(my_font);

				HSSFRow rowtitle = sheet.createRow((short) 0);
				HSSFCell cell = rowtitle.createCell((short) 10);
				cell.setCellValue(prop.getProperty("companyName"));
				cell.setCellStyle(my_style);
				HSSFRow rowtitle1 = sheet.createRow((short) 1);
				HSSFCell cell1 = rowtitle1.createCell((short) 9);
				cell1.setCellValue(prop.getProperty("addressForReport"));
				cell1.setCellStyle(my_style);
				HSSFRow rowtitle2 = sheet.createRow((short) 2);
				HSSFCell cell2 = rowtitle2.createCell((short) 11);
				cell2.setCellValue(prop.getProperty("contactForReport"));
				cell2.setCellStyle(my_style);
				HSSFRow rowtitle31 = sheet.createRow((short) 3);
				cell2 = rowtitle31.createCell((short) 11);
				cell2.setCellValue(prop.getProperty("mailForReport"));
				cell2.setCellStyle(my_style);
				HSSFRow rowtitle3 = sheet.createRow((short) 4);
				cell2 = rowtitle3.createCell((short) 10);
				cell2.setCellValue("Salary Sheet For The Month Of :- " + PAYREGDT);
				cell2.setCellStyle(my_style);
				HSSFRow rowtitle4 = sheet.createRow((short) 5);
				rowtitle4.createCell((short) 0).setCellValue("");
				HSSFRow rowtitle5 = sheet.createRow((short) 6);
				rowtitle5.createCell((short) 0).setCellValue("");

				HSSFFont blueFont = hwb.createFont();
				blueFont.setColor(HSSFColor.BLUE.index);

				HSSFCellStyle style = hwb.createCellStyle();
				// style.setFont(blueFont);
				style.setFillForegroundColor(HSSFColor.BLUE.index);

				int i = 10;

				Rectangle rec = new Rectangle(100, 100);
				
				
				float lstother_total = 0.0f;
				float other_total = 0.0f;
				float basic_total = 0.0f;
				float lstbasic_total = 0.0f;
				float lic_total = 0.0f;
				float lstlic_total = 0.0f;
				float hra_total = 0.0f;
				float lsthra_total = 0.0f;
				float convall_total = 0.0f;
				float lstconvall_total = 0.0f;
				float pf_total = 0.0f;
				float lstpf_total = 0.0f;
				float pt_total = 0.0f;
				float lstpt_total = 0.0f;
				float mlwf_total = 0.0f;
				float lstmlwf_total = 0.0f;
				float tds_total = 0.0f;
				float lsttds_total = 0.0f;
				float addinc_total = 0.0f;
				float lstaddinc_total = 0.0f;
				float totinc_total = 0.0f;
				float lsttotinc_total = 0.0f;
				float addded_total = 0.0f;
				float lstaddded_total = 0.0f;
				float totded_total = 0.0f;
				float lsttotded_total = 0.0f;
				float netpay_total = 0.0f;
				float lstnetpay_total = 0.0f;
				float eepf_total = 0.0f;
				float lsteepf_total = 0.0f;
				float eeps_total = 0.0f;
				float lsteeps_total = 0.0f;
				float eedli_total = 0.0f;
				float lsteedli_total = 0.0f;
				float eepfadmin_total = 0.0f;
				float lsteepfadmin_total = 0.0f;
				float eedliadmin_total = 0.0f;
				float lsteedliadmin_total = 0.0f;
				float eesic = 0.0f;
				float lsteesic_total = 0.0f;
				float absentdays_total = 0.0f;

				float da_total = 0.0f;
				float lstda_total = 0.0f;
				float deptall_total = 0.0f;
				float lstdeptall_total = 0.0f;
				float othersplall_total = 0.0f;
				float lstothersplall_total = 0.0f;
				float mediclaim_total = 0.0f;
				float lstmediclaim_total = 0.0f;
				float otherded_total = 0.0f;
				float lstotherded_total = 0.0f;
				float memded_total = 0.0f;
				float lstmemded_total = 0.0f;
				float bankloan_total = 0.0f;
				float lstbankloan_total = 0.0f;
				float otherloan_total = 0.0f;
				float lstotherloan_total = 0.0f;
				
				
				if(emptype.equalsIgnoreCase("4"))
				{
					emptype="1,2,3";
				}
				else if(emptype.equalsIgnoreCase("5")){
					emptype="0,1,2,3";
				}
				
				
				EmpSql = "         SELECT  empcode , RTRIM(EMPMAST.FNAME)+' '+RTRIM(EMPMAST.MNAME)+' '+RTRIM(EMPMAST.LNAME)AS name, "
						+ "     	EMPMAST.empno,  empmast.DOL, empmast.STATUS, T.PRJ_SRNO,T.DEPT,"
						
						+ "       CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 101 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)   "
						+ "		THEN	(SELECT NET_AMT AS basic  FROM   PAYTRAN WHERE TRNCD = 101 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE	(SELECT 0 AS basic) END AS 'basic',"

						+ "       CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 102 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)   "
						+ "		THEN	(SELECT NET_AMT AS da  FROM   PAYTRAN WHERE TRNCD = 102 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE	(SELECT 0 AS da) END AS 'da',"
						
						+ "       CASE  WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 103 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		THEN			(SELECT NET_AMT AS hra  FROM   PAYTRAN WHERE TRNCD = 103 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE		(SELECT 0 AS hra) END AS 'hra',"

						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 107 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "				THEN (SELECT NET_AMT AS deptall  FROM   PAYTRAN WHERE TRNCD = 107  AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)    			"
						+ "	ELSE (SELECT 0 AS deptall) END AS 'deptall'," 
						
						+" CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 108 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	    THEN			(SELECT NET_AMT AS convall  FROM   PAYTRAN WHERE TRNCD = 108 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE		(SELECT 0 AS convall) END AS 'convall',"
													
						+ "       CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 129 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		THEN		(SELECT NET_AMT AS othersplall  FROM   PAYTRAN WHERE TRNCD = 129 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE		(SELECT 0 AS othersplall) END AS 'othersplall',"
						
						+ "	    CASE  WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 999 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		THEN			(SELECT NET_AMT AS payable  FROM   PAYTRAN WHERE TRNCD = 999 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE		(SELECT 0 AS payable) END AS 'payable',"
						
						+"			 CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 201 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "			THEN		(SELECT NET_AMT AS pf  FROM   PAYTRAN WHERE TRNCD = 201 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)    			"
						+ "		ELSE		(SELECT 0 AS er2) END AS 'pf',	 "
						
						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 202 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS pt  FROM   PAYTRAN WHERE TRNCD = 202 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)		ELSE			(SELECT 0 AS pt) END AS 'pt', "
						
						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 205 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS lic  FROM   PAYTRAN WHERE TRNCD = 205 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS lic) END AS 'lic', "
						
						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 212 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS mediclaim  FROM   PAYTRAN WHERE TRNCD = 212 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS mediclaim) END AS 'mediclaim', "
						
						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 213 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS otherded  FROM   PAYTRAN WHERE TRNCD = 213 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS otherded) END AS 'otherded', "
						
						+ "		 CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 223 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		THEN		(SELECT NET_AMT AS memded  FROM   PAYTRAN WHERE TRNCD = 223 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE  (SELECT 0 AS memded) END AS 'memded',"
						
						+ "	 CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 228 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN		(SELECT NET_AMT AS tds  FROM   PAYTRAN WHERE TRNCD = 228 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	ELSE		(SELECT 0 AS tds) END AS 'tds'," 

						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 211 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS mlwf  FROM   PAYTRAN WHERE TRNCD = 211 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS mlwf) END AS 'mlwf', "
						
						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 248 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS bankloan  FROM   PAYTRAN WHERE TRNCD = 248 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS bankloan) END AS 'bankloan', "
						
						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 249 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS otherloan  FROM   PAYTRAN WHERE TRNCD = 249 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS otherloan) END AS 'otherloan', "
						
						+ "    case when EXISTS(select SUM(net_amt) from   PAYTRAN  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198)"
						+ "    and TRNCD not in(101,102,103,107,108,129)"
						+ "    and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno =  EMPMAST.empno )"
						+ "	 THEN	(select SUM(net_amt) as addinc from   PAYTRAN  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198)"
						+ "    and TRNCD not in(101,102,103,107,108,129)"
						+ "    and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno =  EMPMAST.empno) "
						+ "	 ELSE		(SELECT 0 AS addinc) END AS 'addinc',"
						
						+ "      case when EXISTS(select SUM(net_amt)   from   PAYTRAN  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 201 and 299)"
						+ "     and TRNCD not in(201,202,205,212,213,223,228,211,248,249) and trndt BETWEEN '"
						+ BomDt + "' and '" + EomDt + "' and empno = empmast.empno)"
						+ "	  THEN	(select SUM(net_amt)  as other from   PAYTRAN  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 201 and 299)"
						+ "     and TRNCD not in(201,202,205,212,213,223,228,211,248,249) and trndt BETWEEN '"
						+ BomDt + "' and '" + EomDt + "' and empno = empmast.empno)"
						+ "	  ELSE	(SELECT 0 AS other) END AS 'other' ,"
						
						+ "	  case when EXISTS(select SUM(net_amt) from   PAYTRAN  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 201 and 299)"
						+ "     and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno = empmast.empno)"
						+ "	  THEN			(select SUM(net_amt) as totded from   PAYTRAN  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 201 and 299) "
						+ "     and trndt BETWEEN '" + BomDt + "' and '" + EomDt
						+ "' and empno = empmast.empno)	" + "	  ELSE 	(SELECT 0 AS totded) END AS 'totded' ,"
						
						+ "	  case when EXISTS(select SUM(net_amt) from   PAYTRAN  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198)"
						+ "     and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno = empmast.empno) "
						+ "	  THEN	(select SUM(net_amt) from   PAYTRAN  where TRNCD in    (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198)"
						+ "     and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno = empmast.empno)"
						+ "	  ELSE		(SELECT 0 AS totear) END AS 'totear',"
						
						+ " 	 CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN WHERE TRNCD = 301 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS absentdays  FROM   PAYTRAN WHERE TRNCD = 301 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	  ELSE		(SELECT 0 AS absentdays) END AS 'absentdays'"
						
						
						+ "     from EMPMAST empmast     join   EMPTRAN t on t.empno = empmast.empno"
						+ "     where      T.srno =(SELECT MAX(E2.SRNO) FROM EMPTRAN E2 WHERE E2.empno = empmast.empno AND E2.EFFDATE <= '"
						+ EomDt + "'" + "     and (( empmast.STATUS='A' AND	empmast.DOJ <= '" + EomDt
						+ "') or  (empmast.STATUS ='N' And empmast.DOL>='" + BomDt + "' )) )"
						+ "     AND T.empno in (SELECT DISTINCT empno FROM PAYTRAN WHERE TRNDT BETWEEN '" + BomDt
						//+ "' and '" + EomDt + "') " +
						+ "' and '" + EomDt + "') AND EMPLOYEE_TYPE IN("+emptype+") " +

					
						
						"    UNION "
						+ "         SELECT  empcode , RTRIM(EMPMAST.FNAME)+' '+RTRIM(EMPMAST.MNAME)+' '+RTRIM(EMPMAST.LNAME)AS name,"
						+ "     	EMPMAST.empno, empmast.DOL, empmast.STATUS, T.PRJ_SRNO,T.DEPT,"

					
						+ "       CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 101 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		THEN	(SELECT NET_AMT AS basic  FROM   PAYTRAN_STAGE WHERE TRNCD = 101 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE	(SELECT 0 AS basic) END AS 'basic',"
						
						+ "       CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 102 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		THEN	(SELECT NET_AMT AS da  FROM   PAYTRAN_STAGE WHERE TRNCD = 102 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE	(SELECT 0 AS da) END AS 'da',"
					
						+ "       CASE  WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 103 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		THEN			(SELECT NET_AMT AS hra  FROM   PAYTRAN_STAGE WHERE TRNCD = 103 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE		(SELECT 0 AS hra) END AS 'hra',"
													
						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 107 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "				THEN (SELECT NET_AMT AS deptall  FROM   PAYTRAN_STAGE WHERE TRNCD = 107  AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)    			"
						+ "	ELSE (SELECT 0 AS deptall) END AS 'deptall',"
													
						+"          CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 108 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	    THEN			(SELECT NET_AMT AS convall  FROM   PAYTRAN_STAGE WHERE TRNCD = 108 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE		(SELECT 0 AS convall) END AS 'convall',"
						
						+ "       CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 129 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		THEN		(SELECT NET_AMT AS othersplall  FROM   PAYTRAN_STAGE WHERE TRNCD = 129 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE		(SELECT 0 AS othersplall) END AS 'othersplall',"
						
						+ "	    CASE  WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 999 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		THEN			(SELECT NET_AMT AS payable  FROM   PAYTRAN_STAGE WHERE TRNCD = 999 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE		(SELECT 0 AS payable) END AS 'payable',"
						
						+"			 CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 201 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "			THEN		(SELECT NET_AMT AS pf  FROM   PAYTRAN_STAGE WHERE TRNCD = 201 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)    			"
						+ "		ELSE		(SELECT 0 AS er2) END AS 'pf',	 "
						
						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 202 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS pt  FROM   PAYTRAN_STAGE WHERE TRNCD = 202 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)		ELSE			(SELECT 0 AS pt) END AS 'pt', "
						

						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 205 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS lic  FROM   PAYTRAN_STAGE WHERE TRNCD = 205 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS lic) END AS 'lic', "
							
						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 212 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS mediclaim  FROM   PAYTRAN_STAGE WHERE TRNCD = 212 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS mediclaim) END AS 'mediclaim', "
							
						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 213 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS otherded  FROM   PAYTRAN_STAGE WHERE TRNCD = 213 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS otherded) END AS 'otherded', "
							
						+ "		 CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 223 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		THEN		(SELECT NET_AMT AS memded  FROM   PAYTRAN_STAGE WHERE TRNCD = 223 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "		ELSE  (SELECT 0 AS memded) END AS 'memded',"
						
						+ "	 CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 228 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN		(SELECT NET_AMT AS tds  FROM   PAYTRAN_STAGE WHERE TRNCD = 228 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	ELSE		(SELECT 0 AS tds) END AS 'tds',"

						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 211 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS mlwf  FROM   PAYTRAN_STAGE WHERE TRNCD = 211 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS mlwf) END AS 'mlwf', "
							
						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 248 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS bankloan  FROM   PAYTRAN_STAGE WHERE TRNCD = 248 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS bankloan) END AS 'bankloan', "
							
						+ "CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 249 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS otherloan  FROM   PAYTRAN_STAGE WHERE TRNCD = 249 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt
						+ "' AND empno = EMPMAST.empno)	ELSE	(SELECT 0 AS otherloan) END AS 'otherloan', "
						
						
						+ "    case when EXISTS(select SUM(net_amt) from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198)"
						+ "    and TRNCD not in(101,102,103,107,108,129)"
						+ "    and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno =  EMPMAST.empno )"
						+ "	 THEN	(select SUM(net_amt) as addinc from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198)"
						+ "    and TRNCD not in(101,102,103,107,108,129)"
						+ "    and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno =  EMPMAST.empno) "
						+ "	 ELSE		(SELECT 0 AS addinc) END AS 'addinc',"
						
						+ "      case when EXISTS(select SUM(net_amt)   from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 200 and 299)"
						+ "     and TRNCD not in(201,202,205,212,213,223,228,211,248,249) and trndt BETWEEN '"
						+ BomDt + "' and '" + EomDt + "' and empno = empmast.empno)"
						+ "	  THEN	(select SUM(net_amt)  as other from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 200 and 299)"
						+ "     and TRNCD not in(201,202,205,212,213,223,228,211,248,249) and trndt BETWEEN '"
						+ BomDt + "' and '" + EomDt + "' and empno = empmast.empno)"
						+ "	  ELSE	(SELECT 0 AS other) END AS 'other' ,"
						
						+ "	  case when EXISTS(select SUM(net_amt) from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 200 and 299)"
						+ "     and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno = empmast.empno)"
						+ "	  THEN			(select SUM(net_amt) as totded from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 200 and 299) "
						+ "     and trndt BETWEEN '" + BomDt + "' and '" + EomDt
						+ "' and empno = empmast.empno)	" + "	  ELSE 	(SELECT 0 AS totded) END AS 'totded' ,"
						
						+ "	  case when EXISTS(select SUM(net_amt) from   paytran_stage  where TRNCD in (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198)"
						+ "     and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno = empmast.empno) "
						+ "	  THEN	(select SUM(net_amt) from   paytran_stage  where TRNCD in    (select TRNCD from CDMAST where PSLIPYN = 'Y' and TRNCD between 100 and 198)"
						+ "     and trndt BETWEEN '" + BomDt + "' and '" + EomDt + "' and empno = empmast.empno)"
						+ "	  ELSE		(SELECT 0 AS totear) END AS 'totear',"
						
						+ " 	 CASE WHEN EXISTS(SELECT NET_AMT FROM   PAYTRAN_STAGE WHERE TRNCD = 301 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	THEN	(SELECT NET_AMT AS absentdays  FROM   PAYTRAN_STAGE WHERE TRNCD = 301 AND TRNDT BETWEEN '"
						+ BomDt + "' AND '" + EomDt + "' AND empno = EMPMAST.empno)"
						+ "	  ELSE		(SELECT 0 AS absentdays) END AS 'absentdays'"
						
						
						+ "     from EMPMAST empmast     join   EMPTRAN t on t.empno = empmast.empno"
						+ "     where      T.srno =(SELECT MAX(E2.SRNO) FROM EMPTRAN E2 WHERE E2.empno = empmast.empno AND E2.EFFDATE <= '"
						+ EomDt + "'" + "     and (( empmast.STATUS='A' AND	empmast.DOJ <= '" + EomDt
						+ "') or  (empmast.STATUS ='N' And empmast.DOL>='" + BomDt + "' )) )"
						+ "     AND T.empno in (SELECT DISTINCT empno FROM PAYTRAN_STAGE WHERE TRNDT BETWEEN '"
						//+ BomDt + "' and '" + EomDt + "') " + "     order by   t.PRJ_SRNO,empno  ";
						+ BomDt + "' and '" + EomDt + "') AND EMPLOYEE_TYPE IN("+emptype+") " + "     order by   t.PRJ_SRNO,empno  ";
			

				System.out.println("Excel SQL : "+EmpSql);
				ResultSet rs = st.executeQuery(EmpSql);

				while (rs.next()) {
					i++;
					String prj_name = null;
					String prj_code = null;
					int dept = 0;
					Connection conn = ConnectionManager.getConnectionTech();

					if (report_type[x].equalsIgnoreCase("t.prj_srno")) {
						// System.out.println("into if");
						String prjquery = "select Site_Name,Project_Code from Project_Sites where SITE_ID = '"
								+ rs.getString("PRJ_SRNO") + "'";
						// System.out.println(prjquery);
						Statement stmt = conn.createStatement();
						ResultSet prj = stmt.executeQuery(prjquery);
						if (prj.next()) {
							prj_name = prj.getString("Site_Name");
							prj_code = prj.getString("Project_Code");
						}
						pBrcd1 = rs.getString("PRJ_SRNO");
						br_tot_no_emp = 0;

						HSSFRow head1 = sheet.createRow((short) i++);
						HSSFCell cell4 = head1.createCell((short) 0);
						cell4.setCellValue(
								" Employee's Salary List For Project Site : " + prj_name + " (" + prj_code + ")");
						cell4.setCellStyle(my_style);
						prj.close();
					} else {

						Connection ccn = ConnectionManager.getConnection();
						Statement sts = ccn.createStatement();
						String stmt = "select distinct(lkp_srno),lkp_disc from lookup where lkp_code='DEPT' and lkp_srno="
								+ rs.getInt("DEPT") + "";
						ResultSet rrs = sts.executeQuery(stmt);
						// System.out.println(stmt);
						if (rrs.next()) {
							dept = rrs.getInt("lkp_srno");
							prj_name = rrs.getString("lkp_disc");
						}
						pBrcd1 = Integer.toString(rs.getInt("DEPT"));
						br_tot_no_emp = 0;

						HSSFRow head1 = sheet.createRow((short) i++);
						HSSFCell cell4 = head1.createCell((short) 0);
						cell4.setCellValue(" Employee's Salary List For Deptartment : " + prj_name + "");
						cell4.setCellStyle(my_style);

						ccn.close();
					}

					sheet.setColumnWidth((short) 0, (short) 3000);
					sheet.setColumnWidth((short) 1, (short) 7000);
					sheet.setColumnWidth((short) 4, (short) 4000);
					sheet.setColumnWidth((short) 5, (short) 4000);
					sheet.setColumnWidth((short) 6, (short) 4000);
					sheet.setColumnWidth((short) 7, (short) 4000);
					sheet.setColumnWidth((short) 8, (short) 4000);
					sheet.setColumnWidth((short) 9, (short) 4000);
					sheet.setColumnWidth((short) 10, (short) 4000);
					sheet.setColumnWidth((short) 11, (short) 4000);
					sheet.setColumnWidth((short) 12, (short) 4000);
					sheet.setColumnWidth((short) 13, (short) 4000);
					sheet.setColumnWidth((short) 14, (short) 4000);
					sheet.setColumnWidth((short) 15, (short) 4000);
					sheet.setColumnWidth((short) 16, (short) 4000);
					sheet.setColumnWidth((short) 17, (short) 4000);
					sheet.setColumnWidth((short) 18, (short) 4000);
					sheet.setColumnWidth((short) 19, (short) 4000);
					sheet.setColumnWidth((short) 20, (short) 4000);
					sheet.setColumnWidth((short) 21, (short) 4000);
					sheet.setColumnWidth((short) 22, (short) 4000);
					sheet.setColumnWidth((short) 23, (short) 4000);
					sheet.setColumnWidth((short) 24, (short) 4000);
					sheet.setColumnWidth((short) 25, (short) 4000);
					sheet.setColumnWidth((short) 26, (short) 4000);
					
					conn.close();

					HSSFRow rowhead = sheet.createRow((short) i++);

					
					rowhead.createCell((short) 0).setCellValue("EMP CODE.");
					rowhead.createCell((short) 1).setCellValue("EMPLOYEE NAME");
					rowhead.createCell((short) 4).setCellValue("BASIC");
					rowhead.createCell((short) 5).setCellValue("DA");
					rowhead.createCell((short) 6).setCellValue("HRA");
					rowhead.createCell((short) 7).setCellValue("DEPT ALL");
					rowhead.createCell((short) 8).setCellValue("CONV ALL");
					rowhead.createCell((short) 9).setCellValue("OTHER SPL ALL");
					rowhead.createCell((short) 10).setCellValue("ADD INCOME");
					rowhead.createCell((short) 11).setCellValue("TOTAL INCOME");
					rowhead.createCell((short) 12).setCellValue("PF");
					rowhead.createCell((short) 13).setCellValue("PT");
					rowhead.createCell((short) 14).setCellValue("LIC");
					rowhead.createCell((short) 15).setCellValue("MEDICLAIM");
					rowhead.createCell((short) 16).setCellValue("OTHER DED");
					rowhead.createCell((short) 17).setCellValue("MEMBER DED");
					rowhead.createCell((short) 18).setCellValue("TDS");
					rowhead.createCell((short) 19).setCellValue("MLWF");
					rowhead.createCell((short) 20).setCellValue("BANK LOAN");
					rowhead.createCell((short) 21).setCellValue("OTHER LOAN");
					rowhead.createCell((short) 22).setCellValue("OTHER");
					rowhead.createCell((short) 23).setCellValue("Tot DED");
					rowhead.createCell((short) 24).setCellValue("NET PAY");
					rowhead.createCell((short) 25).setCellValue("EMP STATUS");
					rowhead.createCell((short) 26).setCellValue("ABSENT DAYS");
					
				
					while (report_type[x].equalsIgnoreCase("t.prj_srno") ? pBrcd1.equals(rs.getString("PRJ_SRNO"))
							: dept == rs.getInt("DEPT")) {

						rowhead = sheet.createRow((short) i++);
						rowhead.createCell((short) 0).setCellValue("" + rs.getString("EMPCODE"));

						rowhead.createCell((short) 1).setCellValue("" + rs.getString("name"));

						basic_total = basic_total + rs.getFloat("basic");
						rowhead.createCell((short) 4).setCellValue((int) rs.getFloat("basic"));

						da_total = da_total + rs.getFloat("da");
						rowhead.createCell((short) 5).setCellValue((int) rs.getFloat("da"));
						
						hra_total = hra_total + rs.getFloat("hra");
						rowhead.createCell((short) 6).setCellValue((int) rs.getFloat("hra"));

						deptall_total = deptall_total + +rs.getFloat("deptall");
						rowhead.createCell((short) 7).setCellValue((int) rs.getFloat("deptall"));

						convall_total = convall_total + rs.getFloat("convall");
						rowhead.createCell((short) 8).setCellValue((int) rs.getFloat("convall"));

						othersplall_total = othersplall_total + rs.getFloat("othersplall");
						rowhead.createCell((short) 9).setCellValue((int) rs.getFloat("othersplall"));

						
						if (rs.getFloat("addinc") == 0.0) {
							rowhead.createCell((short) 10).setCellValue(0.00);

						} else {
							float add_inc = rs.getFloat("addinc");
							addinc_total = addinc_total + add_inc;
							rowhead.createCell((short) 10).setCellValue((int) add_inc);
						}


						totinc_total = totinc_total + rs.getFloat("totear");
						rowhead.createCell((short) 11).setCellValue((int) rs.getFloat("totear"));

						if (rs.getFloat("pf") == 0.0) {
							rowhead.createCell((short) 12).setCellValue(0.00);

						} else {
							float pf = rs.getFloat("pf");
							pf_total = pf_total + pf;
							rowhead.createCell((short) 12).setCellValue((int) pf);
						}

						if (rs.getFloat("pt") == 0.0) {
							rowhead.createCell((short) 13).setCellValue(0.00);

						} else {
							float pt = rs.getFloat("pt");
							pt_total = pt_total + pt;
							rowhead.createCell((short) 13).setCellValue((int) pt);
						}

						if (rs.getFloat("lic") == 0.0f) {
							rowhead.createCell((short) 14).setCellValue(0.00);

						} else {
							float lic = rs.getFloat("lic");
							lic_total = lic_total + lic;
							rowhead.createCell((short) 14).setCellValue((int) lic);
						}

						if (rs.getFloat("mediclaim") == 0.0f) {
							rowhead.createCell((short) 15).setCellValue(0.00);

						} else {
							float mediclaim = rs.getFloat("mediclaim");
							mediclaim_total = mediclaim_total + mediclaim;
							rowhead.createCell((short) 15).setCellValue((int) mediclaim);
						}

						if (rs.getFloat("otherded") == 0.0f) {
							rowhead.createCell((short) 16).setCellValue(0.00);
						} else {
							float otherded = rs.getFloat("otherded");
							otherded_total = otherded_total + otherded;
							rowhead.createCell((short) 16).setCellValue((int) otherded);
						}
						
						if (rs.getFloat("memded") == 0.0f) {
							rowhead.createCell((short) 17).setCellValue(0.00);
						} else {
							float memded = rs.getFloat("memded");
							memded_total = memded_total + memded;
							rowhead.createCell((short) 17).setCellValue((int) memded);
						}
						
						if (rs.getFloat("tds") == 0.0f) {
							rowhead.createCell((short) 18).setCellValue(0.00);
						} else {
							float tds = rs.getFloat("tds");
							tds_total = tds_total + tds;
							rowhead.createCell((short) 18).setCellValue((int) tds);
						}

						if (rs.getFloat("mlwf") == 0.0) {
							rowhead.createCell((short) 19).setCellValue(0.00);
						} else {
							float mlwf = rs.getFloat("mlwf");
							mlwf_total = mlwf_total + mlwf;
							rowhead.createCell((short) 19).setCellValue((int) mlwf);
						}
						
						if (rs.getFloat("bankloan") == 0.0) {
							rowhead.createCell((short) 20).setCellValue(0.00);

						} else {
							float bankloan = rs.getFloat("bankloan");
							bankloan_total = bankloan_total + bankloan;
							rowhead.createCell((short) 20).setCellValue((int) bankloan);
						}

						if (rs.getFloat("otherloan") == 0.0) {
							rowhead.createCell((short) 21).setCellValue(0.00);

						} else {
							float otherloan = rs.getFloat("otherloan");
							otherloan_total = otherloan_total + otherloan;
							rowhead.createCell((short) 21).setCellValue((int) otherloan);
						}
						

						if (rs.getFloat("other") == 0.0) {
							rowhead.createCell((short) 22).setCellValue(0.00);
						} else {
							float other = rs.getFloat("other");
							other_total = other_total + other;
							rowhead.createCell((short) 22).setCellValue((int) other);

						}

						
						totded_total = totded_total + rs.getFloat("totded");
						rowhead.createCell((short) 23).setCellValue((int) rs.getFloat("totded"));

						netpay_total = netpay_total + rs.getFloat("payable");
						rowhead.createCell((short) 24).setCellValue((int) rs.getFloat("payable"));


						if (rs.getString("DOL") != null)
						{
							rowhead.createCell((short) 25).setCellValue("Non Active");
						}
						else {
							rowhead.createCell((short) 25).setCellValue("Active");
						}
						
						rowhead.createCell((short) 26).setCellValue((int) rs.getFloat("absentdays"));

						
						tot_no_emp = tot_no_emp + 1;
						br_tot_no_emp = br_tot_no_emp + 1;
						if (!rs.next()) {
							break;
						}
						if (report_type[x].equalsIgnoreCase("t.prj_srno")) {

							if (!pBrcd1.equals(rs.getString("PRJ_SRNO"))) {
								rs.previous();
								break;
							}
						} else {
							if (dept != rs.getInt("DEPT")) {
								rs.previous();

								break;
							}
						}

					}
					rowhead = sheet.createRow((short) i++);
					rowhead.createCell((short) 0).setCellValue("");

					rowhead.createCell((short) 1).setCellValue("");
					rowhead.createCell((short) 3).setCellValue("TOTAL :--");

					rowhead.createCell((short) 4).setCellValue((int) basic_total);
					rowhead.createCell((short) 5).setCellValue(da_total);
					rowhead.createCell((short) 6).setCellValue(hra_total);
					rowhead.createCell((short) 7).setCellValue(deptall_total);
					rowhead.createCell((short) 8).setCellValue((int) convall_total);
					rowhead.createCell((short) 9).setCellValue((int) othersplall_total);
					rowhead.createCell((short) 10).setCellValue((int) addinc_total);
					rowhead.createCell((short) 11).setCellValue((int) totinc_total);


					lstbasic_total = lstbasic_total + basic_total;
					basic_total = 0.0f;
					
					lstda_total = lstda_total + da_total;
					da_total = 0.0f;

					lsthra_total = lsthra_total + hra_total;
					hra_total = 0.0f;
					
					lstdeptall_total = lstdeptall_total + deptall_total;
					deptall_total = 0.0f;
					
					lstconvall_total = lstconvall_total + convall_total;
					convall_total = 0.0f;

					lstothersplall_total = lstothersplall_total + othersplall_total;
					othersplall_total = 0.0f;

					lstaddinc_total = lstaddinc_total + addinc_total;
					addinc_total = 0.0f;

					lsttotinc_total = lsttotinc_total + totinc_total;
					totinc_total = 0.0f;
					
					rowhead.createCell((short) 12).setCellValue((int) pf_total);
					rowhead.createCell((short) 13).setCellValue((int) pt_total);
					rowhead.createCell((short) 14).setCellValue((int) lic_total);
					rowhead.createCell((short) 15).setCellValue((int) mediclaim_total);
					rowhead.createCell((short) 16).setCellValue((int) otherded_total);
					rowhead.createCell((short) 17).setCellValue((int) memded_total);
					rowhead.createCell((short) 18).setCellValue((int) tds_total);
					rowhead.createCell((short) 19).setCellValue((int) mlwf_total);
					rowhead.createCell((short) 20).setCellValue((int) bankloan_total);
					rowhead.createCell((short) 21).setCellValue((int) otherloan_total);
					rowhead.createCell((short) 22).setCellValue((int) other_total);
					rowhead.createCell((short) 23).setCellValue((int) totded_total);
					rowhead.createCell((short) 24).setCellValue((int) netpay_total);

					
					lstpf_total = lstpf_total + pf_total;

					lstpt_total = lstpt_total + pt_total;
					pt_total = 0.0f;

					lstlic_total = lstlic_total + lic_total;
					lic_total = 0.0f;
					
					lstmediclaim_total = lstmediclaim_total + mediclaim_total;
					mediclaim_total = 0.0f;
					
					lstotherded_total = lstotherded_total + otherded_total;
					otherded_total = 0.0f;
					
					lstmemded_total = lstmemded_total + memded_total;
					memded_total = 0.0f;
					
					lsttds_total = lsttds_total + tds_total;
					tds_total = 0.0f;

					lstmlwf_total = lstmlwf_total + mlwf_total;
					mlwf_total = 0.0f;
					
					lstbankloan_total = lstbankloan_total + bankloan_total;
					bankloan_total = 0.0f;

					lstotherloan_total = lstotherloan_total + otherloan_total;
					otherloan_total = 0.0f;

					lstother_total = lstother_total + other_total;
					other_total = 0.0f;

					lsttotded_total = lsttotded_total + totded_total;
					totded_total = 0.0f;

					lstnetpay_total = lstnetpay_total + netpay_total;
					netpay_total = 0.0f;

					
					pf_total = 0.0f;
					//esic_total = 0.0f;
					lsteepf_total += eepf_total;
					eepf_total = 0;

					lsteeps_total += eeps_total;
					eeps_total = 0;

					lsteedli_total += eedli_total;
					eedli_total = 0;

					lsteepfadmin_total += eepfadmin_total;
					eepfadmin_total = 0;

					lsteedliadmin_total += eedliadmin_total;
					eedliadmin_total = 0;

					lsteesic_total += eesic;
					eesic = 0;

					rowhead = sheet.createRow((short) i++);

					rowhead.createCell((short) 0).setCellValue("Total Employee(s) : " + br_tot_no_emp);

					rowhead = sheet.createRow((short) i++);

					rowhead.createCell((short) 0).setCellValue("");
				}

				HSSFRow rowhead = sheet.createRow((short) i++);

				rowhead.createCell((short) 0).setCellValue("Total Employee(s) : " + tot_no_emp);

				
				rowhead = sheet.createRow((short) i++);
				HSSFCell cell4;
				rowhead.createCell((short) 10).setCellValue("  ");
				rowhead = sheet.createRow((short) i++);

				cell4 = rowhead.createCell((short) 11);
				cell4.setCellValue("------- SUMMARY -------");
				cell4.setCellStyle(my_style);

				rowhead = sheet.createRow((short) i++);

				rowhead.createCell((short) 10).setCellValue("  ");
				rowhead = sheet.createRow((short) i++);

				rowhead.createCell((short) 10).setCellValue("  ");

				cell4 = rowhead.createCell((short) 1);
				cell4.setCellValue("BASIC Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 3).setCellValue((int) lstbasic_total);

				cell4 = rowhead.createCell((short) 6);
				cell4.setCellValue("DA Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 8).setCellValue(lstda_total);
				
				cell4 = rowhead.createCell((short) 11);
				cell4.setCellValue("HRA Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 13).setCellValue(lsthra_total);
				
				cell4 = rowhead.createCell((short) 16);
				cell4.setCellValue("DEPT ALL Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 18).setCellValue(lstdeptall_total);
				
				cell4 = rowhead.createCell((short) 21);
				cell4.setCellValue("CONV ALL Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 23).setCellValue((int) lstconvall_total);
				

				rowhead = sheet.createRow((short) i++);

				cell4 = rowhead.createCell((short) 1);
				cell4.setCellValue("OTHER SPECIAL ALLOW Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 3).setCellValue((int) lstothersplall_total);
				
				cell4 = rowhead.createCell((short) 6);
				cell4.setCellValue("ADD Income Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 8).setCellValue((int) lstaddinc_total);
				
				cell4 = rowhead.createCell((short) 11);
				cell4.setCellValue("PF Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 13).setCellValue((int) lstpf_total);
				
				cell4 = rowhead.createCell((short) 16);
				cell4.setCellValue("PT Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 18).setCellValue((int) lstpt_total);
				
				cell4 = rowhead.createCell((short) 21);
				cell4.setCellValue("LIC Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 23).setCellValue((int) lstlic_total);
				
				
				rowhead = sheet.createRow((short) i++);
				
				cell4 = rowhead.createCell((short) 1);
				cell4.setCellValue("MEDICLAIM Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 3).setCellValue((int) lstmediclaim_total);
				
				cell4 = rowhead.createCell((short) 6);
				cell4.setCellValue("OTHER DED Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 8).setCellValue((int) lstotherded_total);
				
				cell4 = rowhead.createCell((short) 11);
				cell4.setCellValue("MEMBER DED Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 13).setCellValue((int) lstmemded_total);
				
				cell4 = rowhead.createCell((short) 16);
				cell4.setCellValue("T.D.S Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 18).setCellValue((int) lsttds_total);
				
				cell4 = rowhead.createCell((short) 21);
				cell4.setCellValue("MLWF Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 23).setCellValue((int) lstmlwf_total);
				
				
				rowhead = sheet.createRow((short) i++);
				
				cell4 = rowhead.createCell((short) 1);
				cell4.setCellValue("BANK LOAN Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 3).setCellValue((int) lstbankloan_total);
				
				cell4 = rowhead.createCell((short) 6);
				cell4.setCellValue("OTHER LOAN Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 8).setCellValue((int) lstotherloan_total);
				
				cell4 = rowhead.createCell((short) 11);
				cell4.setCellValue("Total INCOME:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 13).setCellValue((int) lsttotinc_total);

				cell4 = rowhead.createCell((short) 16);
				cell4.setCellValue("ADDED Total:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 18).setCellValue((int) lstaddded_total);
				
				cell4 = rowhead.createCell((short) 21);
				cell4.setCellValue("Total Deduction:");
				cell4.setCellStyle(my_style);
				rowhead.createCell((short) 23).setCellValue((int) lsttotded_total);
				
				rowhead = sheet.createRow((short) i++);
				
				cell4 = rowhead.createCell((short) 21);
				cell4.setCellValue("NET PAY Total:");
				cell4.setCellStyle(my_style);

				float tttt = 0.0f;
				try {
					// System.out.println("date===="+date);
					Statement sttt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
	/*				rs = st.executeQuery("select sum(net_amt) from paytran where trndt between '"
							+ ReportDAO.BOM(PAYREGDT) + "' and '" + ReportDAO.EOM(PAYREGDT)
							+ "'  and trncd=999  union  "
							+ "select sum(net_amt) from paytran_stage where trndt between '"
							+ ReportDAO.BOM(PAYREGDT) + "' and '" + ReportDAO.EOM(PAYREGDT) + "'  and trncd=999 ");
*/

					rs = st.executeQuery("select sum(net_amt) from paytran p, empmast e where e.empno=p.empno and trndt between '"
							+ ReportDAO.BOM(PAYREGDT) + "' and '" + ReportDAO.EOM(PAYREGDT)
							+ "'  and trncd=999 and employee_type IN("+emptype+")  union  "
							+ "select sum(net_amt) from paytran_stage p, empmast e where e.empno=p.empno and trndt between '"
							+ ReportDAO.BOM(PAYREGDT) + "' and '" + ReportDAO.EOM(PAYREGDT) + "'  and trncd=999 and employee_type IN("+emptype+") ");

					
					System.out.println("select sum(net_amt) from paytran p, empmast e where e.empno=p.empno and trndt between '"
							+ ReportDAO.BOM(PAYREGDT) + "' and '" + ReportDAO.EOM(PAYREGDT)
							+ "'  and trncd=999 and employee_type="+emptype+"  union  "
							+ "select sum(net_amt) from paytran_stage p, empmast e where e.empno=p.empno and trndt between '"
							+ ReportDAO.BOM(PAYREGDT) + "' and '" + ReportDAO.EOM(PAYREGDT) + "'  and trncd=999 and employee_type="+emptype+"  ");
					
					while (rs.next()) {
						tttt += rs.getFloat(1);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				rowhead.createCell((short) 23).setCellValue((int) tttt);
				DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

				Calendar calobj = Calendar.getInstance();
				HSSFRow row = sheet.createRow((short) i);
				row.createCell((short) 0).setCellValue(" ");
				row = sheet.createRow((short) i + 1);
				row.createCell((short) 0).setCellValue("Report Date And Time " + df.format(calobj.getTime()));

			}
			hwb.write(out1);
			out1.close();
			st.close();
			con.close();
		}
	} catch (Exception e) {
		System.out.println("into excel type");
		e.printStackTrace();
	}

}


		
		/***************************************************Prasad End**********************************************************************/

public static void TransferRegionwiseReportexcel(String PAYREGDT,String type,String filepath,String imgpath, String valuetype)
{
	 Properties prop = new Properties();
	 int brtot[] = new int[15];
	 int ALLbrtot[] = new int[15];
	 int PBRCD = 0;
	 int gross = 0;
	
     try
     {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream stream = classLoader.getResourceAsStream("constant.properties");
		prop.load(stream);
     }
     catch (Exception e) {System.out.println("Error in constant properties Manager "+e);}
     
	try
	{
		System.out.println("Into Generalised_TransferWiseReport.....");
		RepoartBean repBean  = new RepoartBean();
		
		LookupHandler lkh=new LookupHandler();
		Connection con =null;
		Connection con1 =null;
		System.out.println( filepath);
	FileOutputStream out1 = new FileOutputStream(new File(filepath));
	HSSFWorkbook hwb=new HSSFWorkbook();
	HSSFSheet sheet =  hwb.createSheet("EmpPFlist");
	
	SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
	
	String date1 = PAYREGDT.substring(0,2);
	String date2="";
	int date2new = ReportDAO.getMonth(PAYREGDT);
	date2new=date2new+1;
	String date3 = PAYREGDT.substring(7,11);
	if(date2new<=9){
		date2 = "0"+date2new;
	}
	else{
		date2=""+date2new;
	}
	
	sheet.setColumnWidth((short)0, (short)2000);
	sheet.setColumnWidth((short)1, (short)10000);
	sheet.setColumnWidth((short)2, (short)7000);
	sheet.setColumnWidth((short)3, (short)4000);
	sheet.setColumnWidth((short)4, (short)4000);
	sheet.setColumnWidth((short)5, (short)4000);
	sheet.setColumnWidth((short)6, (short)5000);
	sheet.setColumnWidth((short)7, (short)5000);
	sheet.setColumnWidth((short)8, (short)4000);

   HSSFCellStyle my_style = hwb.createCellStyle();
   HSSFCellStyle my_style1 = hwb.createCellStyle();

   HSSFFont my_font=hwb.createFont();
   my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
   my_style.setFont(my_font);
 
   	HSSFRow rowtitle=   sheet.createRow((short)0);
	HSSFCell cell = rowtitle.createCell((short)2);
	cell.setCellValue(prop.getProperty("bankName1"));
	cell.setCellStyle(my_style);
	
	HSSFRow rowtitle1=   sheet.createRow((short)1);
	HSSFCell cell1 = rowtitle1.createCell((short)1);
	cell1.setCellValue(prop.getProperty("addressForReport"));
	cell1.setCellStyle(my_style);
	
	HSSFRow rowtitle2=   sheet.createRow((short)2);
	HSSFCell cell2 = rowtitle2.createCell((short)2);
	cell2.setCellValue(prop.getProperty("contactForReport"));
	cell2.setCellStyle(my_style);
	
	HSSFRow rowtitle31=   sheet.createRow((short)3);
	cell2 = rowtitle31.createCell((short)2);
	cell2.setCellValue(	prop.getProperty("mailForReport"));
	cell2.setCellStyle(my_style);
	
	HSSFRow rowtitle3=   sheet.createRow((short)4);
	cell2=rowtitle3.createCell((short)4);
	cell2.setCellValue("");
	cell2.setCellStyle(my_style);
	
	HSSFRow rowtitle4=   sheet.createRow((short)5);
	rowtitle4.createCell((short) 0).setCellValue("");
	HSSFRow rowtitle5=   sheet.createRow((short)6);
	rowtitle5.createCell((short) 0).setCellValue("");
	
	HSSFFont blueFont = hwb.createFont();
	blueFont.setColor(HSSFColor.BLUE.index);
	
	HSSFCellStyle style = hwb.createCellStyle();
	//style.setFont(blueFont);
	style.setFillForegroundColor(HSSFColor.BLUE.index);
	
	HSSFRow head=   sheet.createRow((short)7);
	head.createCell((short) 0).setCellValue("");
	HSSFRow heading=   sheet.createRow((short)8);
	HSSFCell cell3 = heading.createCell((short) 0); 

	cell3.setCellValue("");
	cell3.setCellStyle(my_style1);
	HSSFRow rowhead=   sheet.createRow((short)9);
    sheet.createFreezePane( 0, 10, 0, 10 );
  
   my_style1.setAlignment((short) 2);
   my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
   my_style1.setFont(my_font);
   
	cell3=rowhead.createCell((short) 0);
	cell3.setCellValue("SR NO");
	cell3.setCellStyle(my_style1);
	cell3=rowhead.createCell((short) 1);
	cell3.setCellValue("EMPCODE");
	cell3.setCellStyle(my_style1);
	cell3=rowhead.createCell((short) 2);
	cell3.setCellValue("EMP NAME");
	cell3.setCellStyle(my_style1);
	cell3=rowhead.createCell((short) 3);
	cell3.setCellValue("DOJ");
	cell3.setCellStyle(my_style1);
	cell3=rowhead.createCell((short) 4);
	cell3.setCellValue("Nashik City 1");
	cell3.setCellStyle(my_style1);
	cell3=rowhead.createCell((short) 5);
	cell3.setCellValue("Nashik Area 2");
	cell3.setCellStyle(my_style1);
	cell3=rowhead.createCell((short) 6);
	cell3.setCellValue("Nashik Out Of 3");
	cell3.setCellStyle(my_style1);
	cell3=rowhead.createCell((short) 7);
	cell3.setCellValue("TOTAL");
	cell3.setCellStyle(my_style1);
	
	ReportDAO.OpenCon("", "", "",repBean);
	con = repBean.getCn();	
	con1 = repBean.getCn();
	Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_READ_ONLY);
	Statement st1 = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_READ_ONLY);
	
	int i=16;
	int count=1;
	int k = 0, j = 0;
		
	String firsteffdate="", secondeffdate="";
	String firstdate1 ="", firstdate2 = "", firstdate3 = "";
	String secondtdate1 = "", secondtdate2 = "", secondtdate3 = "";
	
	LocalDate start_date, end_date; 
	 /*
	long difference_In_Years = 0,  difference_In_Months = 0, difference_In_Days = 0; 
	long difference_In_Years_temp1=0, difference_In_Months_temp1=0,	difference_In_Days_temp1=0;
	long difference_In_Years_temp2=0, difference_In_Months_temp2=0, difference_In_Days_temp2=0;
	long difference_In_Years_temp3=0, difference_In_Months_temp3=0, difference_In_Days_temp3=0;
	*/
	//String TransferEmp ="SELECT EMPNO, EMPCODE, FNAME, MNAME, LNAME, DOJ FROM EMPMAST WHERE STATUS='A' ORDER BY EMPNO";
	//String TransferEmp ="SELECT EMPNO, EMPCODE, FNAME, MNAME, LNAME, DOJ FROM EMPMAST WHERE STATUS='A' ORDER BY EMPNO";
	
	System.out.println("valuetype : "+valuetype);
	if(valuetype.equalsIgnoreCase("0")){
		valuetype="";
	}else{
		valuetype="AND "+valuetype;
	}
	
	String TransferEmp ="SELECT E.EMPNO, E.EMPCODE, E.FNAME, E.MNAME, E.LNAME, E.DOJ FROM EMPMAST E, EMPTRAN ET "
			+ "WHERE E.EMPNO=ET.EMPNO AND ET.SRNO=(SELECT MAX(SRNO) FROM EMPTRAN ETA WHERE ET.EMPNO=ETA.EMPNO) AND E.STATUS='A' "+valuetype+" ORDER BY E.EMPCODE";
	System.out.println("TransferEmp : "+TransferEmp);
			
	ResultSet rs1 = st1.executeQuery(TransferEmp);
	
	while(rs1.next())
	{
	int countnew=1;
		
		long difference_In_Years = 0,  difference_In_Months = 0, difference_In_Days = 0; 
		long difference_In_Years_temp1=0, difference_In_Months_temp1=0,	difference_In_Days_temp1=0;
		long difference_In_Years_temp2=0, difference_In_Months_temp2=0, difference_In_Days_temp2=0;
		long difference_In_Years_temp3=0, difference_In_Months_temp3=0, difference_In_Days_temp3=0;
		
		long difference_In_Years_value1=0, difference_In_Months_value1=0, difference_In_Days_value1=0;
		long difference_In_Years_value2=0, difference_In_Months_value2=0, difference_In_Days_value2=0;
		long difference_In_Years_value3=0, difference_In_Months_value3=0, difference_In_Days_value3=0;
		long Additional_Years_value1=0, Additional_Months_value1=0, Additional_Days_value1=0;
		long Actual_Years_value1=0, Actual_Months_value1=0, Actual_Days_value1=0;
		long Additional_Years_value2=0, Additional_Months_value2=0, Additional_Days_value2=0;
		long Actual_Years_value2=0, Actual_Months_value2=0, Actual_Days_value2=0;
		long Additional_Years_value3=0, Additional_Months_value3=0, Additional_Days_value3=0;
		long Actual_Years_value3=0, Actual_Months_value3=0, Actual_Days_value3=0;
	
				
		j++;
		
		String EMPNO = rs1.getString("EMPNO");
		String TransferRegionQuery = "WITH a AS (SELECT Row_number() OVER(ORDER BY srno) AS rn, srno, empno FROM   emptran WHERE  empno = "+EMPNO+") "
				+ "SELECT a1.*, site_name AS new_site, m.doj, m.empcode, m.lname, m.fname, m.mname, e.trncd AS trncd, l.lkp_disc, e.effdate, e.order_dt, p.district, "
				+ "(SELECT effdate FROM   emptran et WHERE  et.empno = "+EMPNO+" AND srno = a1.rn + 1) AS secondEFFDATE FROM   empmast m, emptran e, "
				+ "project_sites p, lookup l, a a1, emptran y, lookup lk WHERE  m.empno = e.empno AND m.empno = a1.empno AND e.srno = a1.srno AND e.prj_srno = p.site_id "
				+ "AND m.empno = "+EMPNO+" AND y.empno = e.empno AND y.srno = (SELECT Max(srno) FROM   emptran g WHERE  g.empno = m.empno) AND l.lkp_code = 'DESIG' "
				+ "AND l.lkp_srno = y.desig "
				//+ "AND e.srno > 1 AND e.trncd = 60 "
				+ "AND lk.lkp_code = 'DEPT' AND lk.lkp_srno = y.dept ORDER  BY e.effdate";

		System.out.println("TransferRegionQuery : "+TransferRegionQuery);
		ResultSet rs = st.executeQuery(TransferRegionQuery);

		while(rs.next())
		{
			
			 firsteffdate = rs.getString("effdate");
			 secondeffdate = rs.getString("secondEFFDATE");
			
			if(secondeffdate==null){
				secondeffdate=date3+"-"+date2+"-"+date1;
			 }
			
			 firstdate1 = firsteffdate.substring(8,10);
			 firstdate2 = firsteffdate.substring(5,7);
			 firstdate3 = firsteffdate.substring(0,4);
			 secondtdate1 = secondeffdate.substring(8,10);
			 secondtdate2 = secondeffdate.substring(5,7);
			 secondtdate3 = secondeffdate.substring(0,4);
			
		int a = Integer.parseInt(firstdate1);
		int b = Integer.parseInt(firstdate2);
		int c = Integer.parseInt(firstdate3);
		
		int p = Integer.parseInt(secondtdate1);
		int q = Integer.parseInt(secondtdate2);
		int r = Integer.parseInt(secondtdate3);
		
			 start_date = LocalDate.of(c,b,a); 
			 end_date= LocalDate.of(r,q,p); 
				Period diff	= Period.between(start_date, end_date); 
		
				difference_In_Years=diff.getYears();
				difference_In_Months=diff.getMonths();
				difference_In_Days=diff.getDays();
					
			if(rs.getString("DISTRICT").equalsIgnoreCase("1"))
			{
				difference_In_Years_temp1=difference_In_Years+difference_In_Years_value1;
				difference_In_Months_temp1=difference_In_Months+difference_In_Months_value1;
				difference_In_Days_temp1=difference_In_Days+difference_In_Days_value1;
			}
			if(rs.getString("DISTRICT").equalsIgnoreCase("2"))
			{
				difference_In_Years_temp2=difference_In_Years+difference_In_Years_value2;
				difference_In_Months_temp2=difference_In_Months+difference_In_Months_value2;
				difference_In_Days_temp2=difference_In_Days+difference_In_Days_value2;
			}
			if(rs.getString("DISTRICT").equalsIgnoreCase("3"))
			{
				difference_In_Years_temp3=difference_In_Years+difference_In_Years_value3;
				difference_In_Months_temp3=difference_In_Months+difference_In_Months_value3;
				difference_In_Days_temp3=difference_In_Days+difference_In_Days_value3;
			}
	
			k++;
			difference_In_Years_value1=difference_In_Years_temp1;
			difference_In_Months_value1=difference_In_Months_temp1;
			difference_In_Days_value1=difference_In_Days_temp1;
			
			difference_In_Years_value2=difference_In_Years_temp2;
			difference_In_Months_value2=difference_In_Months_temp2;
			difference_In_Days_value2=difference_In_Days_temp2;
			
			difference_In_Years_value3=difference_In_Years_temp3;
			difference_In_Months_value3=difference_In_Months_temp3;
			difference_In_Days_value3=difference_In_Days_temp3;
	}
	//close while rs loop
		
		if(difference_In_Days_value1>31 || difference_In_Days_value2>31 || difference_In_Days_value3>31){
			Additional_Months_value1=difference_In_Days_value1/31;
			Actual_Days_value1 = (difference_In_Days_value1-(Additional_Months_value1*31));
			
			Additional_Months_value2=difference_In_Days_value2/31;
			Actual_Days_value2 = (difference_In_Days_value2-(Additional_Months_value2*31));
			
			Additional_Months_value3=difference_In_Days_value3/31;
			Actual_Days_value3 = (difference_In_Days_value3-(Additional_Months_value3*31));
		}
		else{
			
				Actual_Days_value1 = difference_In_Days_value1;
				Actual_Days_value2 = difference_In_Days_value2;
				Actual_Days_value3 = difference_In_Days_value3;
		}
		difference_In_Months_value1=difference_In_Months_value1+Additional_Months_value1;
		difference_In_Months_value2=difference_In_Months_value2+Additional_Months_value2;
		difference_In_Months_value3=difference_In_Months_value3+Additional_Months_value3;
		
		if(difference_In_Months_value1>12 || difference_In_Months_value2>12 || difference_In_Months_value3>12){
			Additional_Years_value1=difference_In_Months_value1/12;
			Actual_Months_value1=(difference_In_Months_value1-(Additional_Years_value1*12));
			
			Additional_Years_value2=difference_In_Months_value2/12;
			Actual_Months_value2=(difference_In_Months_value2-(Additional_Years_value2*12));
			
			Additional_Years_value3=difference_In_Months_value3/12;
			Actual_Months_value3=(difference_In_Months_value3-(Additional_Years_value3*12));
		}
		else{
				Actual_Months_value1= difference_In_Months_value1;
				Actual_Months_value2= difference_In_Months_value2;
				Actual_Months_value3= difference_In_Months_value3;
		}
		Actual_Years_value1=difference_In_Years_value1+Additional_Years_value1;
		Actual_Years_value2=difference_In_Years_value2+Additional_Years_value2;
		Actual_Years_value3=difference_In_Years_value3+Additional_Years_value3;

	long Total_Years = Actual_Years_value1+Actual_Years_value2+Actual_Years_value3;
	long Total_Months = Actual_Months_value1+Actual_Months_value2+Actual_Months_value3;
	long Total_Days = Actual_Days_value1+Actual_Days_value2+Actual_Days_value3;
	
	long Additional_Total_Months =0, Additional_Total_Years =0;
	long Actual_Total_Days =0, Actual_Total_Months =0, Actual_Total_Years =0;
			
	if(Total_Days>31){
		Additional_Total_Months = Total_Days/31;
		Actual_Total_Days = (Total_Days-(Additional_Total_Months*31));
	}
	else{
		Actual_Total_Days=Total_Days;
	}
	Total_Months=Total_Months+Additional_Total_Months;
	
	if(Total_Months>12){
		Additional_Total_Years = Total_Months/12;
		Actual_Total_Months = (Total_Months-(Additional_Total_Years*12));
		
	}
	else{
		Actual_Total_Months = Total_Months;
	}
	Actual_Total_Years=Total_Years+Additional_Total_Years;
	
	String Total_Service = ""+Actual_Total_Years+" years "+Actual_Total_Months+" months "+Actual_Total_Days+" days";
	
	String Nashik_City = ""+Actual_Years_value1+" years "+Actual_Months_value1+" months "+Actual_Days_value1+" days";
	String Nashik_Area = ""+Actual_Years_value2+" years "+Actual_Months_value2+" months "+Actual_Days_value2+" days";
	String Nashik_OutOf = ""+Actual_Years_value3+" years "+Actual_Months_value3+" months "+Actual_Days_value3+" days";
	String NAME = rs1.getString("FNAME")+" "+rs1.getString("MNAME")+" "+rs1.getString("LNAME");
	
	 count = count++;
	//System.out.println("COUNT : "+count);
	
	
	HSSFRow row = sheet.createRow((short)i++);
//	row.createCell((short) 0).setCellValue(""+1);
	
	row.createCell((short) 0).setCellValue(""+countnew);
	row.createCell((short) 1).setCellValue(""+rs1.getString("EMPCODE"));
	row.createCell((short) 2).setCellValue(""+NAME);
	row.createCell((short) 3).setCellValue(""+rs1.getString("DOJ"));
	//row.createCell((short) 4).setCellValue(""+sdf.format(rs.getDate("DOJ")));
	row.createCell((short) 4).setCellValue(""+Nashik_City);
	row.createCell((short) 5).setCellValue(""+Nashik_Area);
	row.createCell((short) 6).setCellValue(""+Nashik_OutOf);
	row.createCell((short) 7).setCellValue(""+Total_Service);
	
	DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	Calendar calobj = Calendar.getInstance();
	
//	HSSFRow row = sheet.createRow((short) i);
	row.createCell((short) 0).setCellValue(" ");
	  row = sheet.createRow((short) i+1);
	
	}
	hwb.write(out1);
	out1.close();
	st.close();
    con.close();
    
	System.out.println("Result OK.....");
	
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}

}
		
}   
