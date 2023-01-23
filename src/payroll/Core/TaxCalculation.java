package payroll.Core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import payroll.DAO.GradeHandler;
import payroll.Model.RepoartBean;
import payroll.Model.TaxReportBean;

public class TaxCalculation {

	
	public static void pringapline(RepoartBean repBean,TaxReportBean taxRepBean){
		ReportDAO.println("", 0, 1, false, "BANK",repBean);
	}
	
	public static void print1234bar(RepoartBean repBean,TaxReportBean taxRepBean){
		ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
		ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
		ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
		ReportDAO.println("|", taxRepBean.getC_ul3(), 0, false, "BANK",repBean);
		ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
	}
	
	public static void print234bar(RepoartBean repBean,TaxReportBean taxRepBean){
		
		ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
		ReportDAO.println("|", taxRepBean.getC_ul3(), 0, false, "BANK",repBean);
		ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
	}
	public static void print34bar(RepoartBean repBean,TaxReportBean taxRepBean){
			
			
			ReportDAO.println("|", taxRepBean.getC_ul3(), 0, false, "BANK",repBean);
			ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
		}
	
	public static void pform24(int feno,int teno,String rdt,String filepath){
		TaxReportBean taxRepBean = new TaxReportBean();
		RepoartBean repBean  = new RepoartBean();
		FileWriter Fp;
		ReportDAO.OpenCon("", "", "",repBean);
		taxRepBean.setMaxhlint1(30000);// ' maximum housing loan interest repayment loan taken before 01/4/1999
		taxRepBean.setMaxhlint2(150000);// ' maximum housing loan interest repayment loan taken after 01/4/1999
		taxRepBean.setMin_pay(50000);

				 repBean.setPageLen(2000);
				 repBean.setLineLen(240);
				 String SLINE = ""; 
				 ResultSet emp = null;
				 String empsql= "";
				 String ytdsql  = "";
				 ResultSet yttran = null;
				String ename = "";
				 int grdint = 0;
				 String grd = "";
				 if(feno!= teno){
				    empsql = "select emp.*, t.acno, t.branch, t.grade ,emp.panno from empmast emp, emptran t where t.empno = emp.empno  AND " +
				             " t.effdate = ( SELECT MAX(E2.EFFDATE) FROM EMPTRAN E2 WHERE e2.empno = emp.empno and E2.EFFDATE <= '" +rdt+ "')" +
				             " and emp.status = 'A' and emp.empno between  " + feno + " and " + teno + " order by emp.empno ";
				 }else{
				    empsql = "select emp.*, t.acno, t.branch, t.grade ,emp.panno from empmast emp, emptran t where t.empno = emp.empno  AND " +
				             " t.effdate = ( SELECT MAX(E2.EFFDATE) FROM EMPTRAN E2 WHERE e2.empno = emp.empno and E2.EFFDATE <= '" +rdt+ "')" +
				             " and emp.empno between  " + feno + " and " + teno + " order by emp.empno ";
				 }
				 Statement st;
				try {
					st = repBean.getCn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					emp = st.executeQuery(empsql);
					 if(!emp.next()){
			              System.out.println("EMPLOYEE RECORDSET IS EMPTY");
			              return;
					 }
					 int ctr = 0;
					 ReportDAO.make_prn_file(filepath,repBean);
					 SLINE = UtilityDAO.stringOfSize(240, '-');
					 ReportDAO.println("ORG NAME", 83, 1, false, "BANK",repBean);
					 ReportDAO.println("  ANNEXURE I To Form No. 24 (with Education cess @3%)", 83, 1, false, "BANK",repBean);
					 ReportDAO.println("NOTE :-    To read the figures filled in form 24, we have enclosed herewith this typed Statement", 3, 1, false, "BANK",repBean);
					 ReportDAO.println(" for your convenience.    Please note.  (Fin.year - 2003-2004  AY 2004-05)", 3, 1, false, "BANK",repBean);
					 ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
					 ReportDAO.println("RNO |PAN          |NAME                 |DT.FROM |DT.to   |  205   | 206 |207 | 208 | 209   | 210       | 211  | 212  | 213  | 214 | 215| 216       | 217  | 218  | 219  | 220  | 221 | 222 | 223  | 224| 225  | 226  | 227 | 228  |  229  |", 0, 1, false, "BANK",repBean);
					 ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
					 emp.beforeFirst();
					 while(emp.next()){
						 ename = emp.getString("LNAME") + " " + emp.getString("FNAME").substring(0,0) + ". " + emp.getString("MNAME").substring(0,0) + ".";
						 grdint = emp.getInt("GRADE");
						 grd = GradeHandler.GetGrade(grdint, "Y",repBean);
						 ytdsql = "select * from ytdtran where empno = " + emp.getInt("EMPNO")+ " and trndt between '" +ReportDAO.Boy(rdt)+ "' and '" +ReportDAO.Eoy(rdt)+"' and trncd in (203,601,224)";
						 Statement st1 = repBean.getCn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
						 yttran = st1.executeQuery(ytdsql);
						 if(yttran.next()){
							 System.out.println(emp.getInt("EMPNO"));
							 print_f24(emp.getInt("EMPNO"), rdt, ename, grd, emp.getString("PANNO"), emp.getString("GENDER"), 1, repBean,taxRepBean);
							 //print_f24 emp!EmpNo, rdt, ename, grd, NVAL(emp!panno, ""), emp!sex, 1
							 ctr = ctr + 1;
							
							 if(ctr >= 28){
								 ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
								 ReportDAO.println(""+(char)12, 0, 1, false, "BANK",repBean);
								 ReportDAO.println("ORG NAME", 83, 1, false, "BANK",repBean);
								 ReportDAO.println("  ANNEXURE I To Form No. 24", 83, 1, false, "BANK",repBean);
								 ReportDAO.println("NOTE :-    To read the figures filled in form 24, we have enclosed herewith this typed Statement", 3, 1, false, "BANK",repBean);
								 ReportDAO.println(" for your convenience.    Please note.  (Fin.year - 2003-2004  AY 2004-05)", 3, 1, false, "BANK",repBean);
								 ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
								 ReportDAO.println("RNO |PAN          |NAME                 |DT.FROM |DT.to   |  205   | 206 |207 | 208 | 209   | 210       | 211  | 212  | 213  | 214 | 215| 216       | 217  | 218  | 219  | 220  | 221 | 222 | 223  | 224| 225  | 226  | 227 | 228  |  229  |", 0, 1, false, "BANK",repBean);
								 ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
								 ctr = 1;
								 
							 }
						 }
						  
						 
					 }
					 ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
					 ReportDAO.println("Total :", 206, 0, false, "BANK",repBean);
					 ReportDAO.println(UtilityDAO.trans(taxRepBean.getTot(), "9999999", "", false, false), 220, 1, false, "BANK",repBean);
					 ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
					 Fp = repBean.getFp();
						Fp.close();
						repBean.getCn().close();
						return;
					 
					 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
	}
	
	public static void buildsqlF16(int peno,String prdt,String itgrpcd,TaxReportBean taxRepBean){
		taxRepBean.setYtdstr(" select regular.trncd,c.disc ,net_amt,c.plusminus,c.itgrp,c.itgrp2,C.CHKSLB  from " +
	          " ( SELECT Y.TRNCD, sum(decode(y.trncd,108, (case  when net_Amt > 800 then net_amt - 800 else net_amt end),net_amt)) net_amt " +
	          " FROM YTDTRAN Y,CDMAST C " +
	          " Where Y.empno = " + peno +
	          " AND Y.TRNDT BETWEEN '" +ReportDAO.Boy(prdt)+ "'  AND '"+ ReportDAO.EOM(prdt) +"' "+
	          " AND Y.TRNCD = C.TRNCD AND ( C.ITGRP LIKE '" + itgrpcd + "' or C.ITGRP2 LIKE '" + itgrpcd + "' ) " +
	          " GROUP BY y.TRNCD ) regular , cdmast c " +
	          " where C.trncd = regular.trncd order by c.itgrp");
	}
	
	public static void printgrpF16(int ppeno,String pprdt,RepoartBean repBean,TaxReportBean taxRepBean){
		
	String pitgrp = "";
	String pitgrp2 = "";
	ResultSet slb = null;
	ResultSet ytd = null;
	String slbstr = "";
	int reftot = 0;
	Statement st;
	try
	{
		st = repBean.getCn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		
		ytd = st.executeQuery(taxRepBean.getYtdstr());
		while(ytd.next())
		{
			pitgrp = ytd.getString("itgrp")!=null?ytd.getString("itgrp"):"";
			taxRepBean.setTotamt(0);
			while(ytd.getString("ITGRP").equalsIgnoreCase(pitgrp))
			{
				taxRepBean.setTotamt(taxRepBean.getTotamt() + ytd.getString("NET_AMT")!=null?ytd.getInt("NET_AMT"):0);
				
				if(ytd.getString("PLUSMINUS").equalsIgnoreCase("M"))
				{
					taxRepBean.setC3tot(taxRepBean.getC3tot() - (ytd.getString("NET_AMT")!=null?ytd.getInt("NET_AMT"):0));
				}
				else
				{
					taxRepBean.setC3tot(taxRepBean.getC3tot() + (ytd.getString("NET_AMT")!=null?ytd.getInt("NET_AMT"):0));
				}
				if(!ytd.next())
				{
					break;
				}
			}
			ytd.previous();
			if(ytd.getInt("TRNCD") == 811)
			{
				taxRepBean.setTotamt(calhlint(taxRepBean.getTotamt(), ppeno,repBean,taxRepBean));
			}
			else
			{
				
				if((ytd.getString("CHKSLB")==null?"N":ytd.getString("CHKSLB")).equalsIgnoreCase("Y"))
				{
					slbstr = "SELECT * FROM SLAB WHERE TRNCD = "+ytd.getInt("TRNCD");
					
					Statement st1  = repBean.getCn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					slb = st1.executeQuery(slbstr);
			
					if(slb.next())
					{
						reftot = taxRepBean.getTotamt();
						if(ytd.getInt("TRNCD") != 803 && ytd.getInt("TRNCD") != 209)
						{
							taxRepBean.setTotamt(Math.round(Calculate.checkSlab(ytd.getInt("TRNCD"),pprdt,reftot,1,ppeno,repBean.getCn())));  //***************REMAINING***********
						
						}
					}
					st1.close();
				}
			}
			if(ytd.getString("PLUSMINUS").equalsIgnoreCase("M"))
			{
				taxRepBean.setC5tot(taxRepBean.getC5tot() - taxRepBean.getTotamt());
			}
			else
			{
				taxRepBean.setC5tot(taxRepBean.getC5tot() + taxRepBean.getTotamt());
			}
		}
		st.close();
		
	} 
	catch (SQLException e) 
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	}
	
	public static void printgrp1(int peno, String prdt, String pename , String desigcd , String PAN_NO ,String SEX1, java.sql.Date pdob,RepoartBean repBean,TaxReportBean taxRepBean){
		
		 int ptotamt = 0;
		 int reb88d  = 0;
		 int via  = 0;
		 int  finctot  = 0;
		 int  q80cc  = 0;
		 int  q80d  = 0;
		 int  q80e  = 0;
		 int  q80gPM  = 0;
		 int  q80g  = 0;
		 int  q80gG  = 0;
		 int  q80l  = 0;
		 int  q80u  = 0;
		 
		 int  q80ccf  = 0;
		 int  tmpamt  = 0;
		 int  caltax  = 0;
		 int  ftotinc  = 0;
		 int  inctot  = 0;
		 int  b88  = 0;
		 int  c88  = 0;
		 int  us89  = 0;
		 int  us10  = 0;
		 int  educess  = 0;
		 ResultSet rsTmp = null;
		 ResultSet trs = null;
		 String  vPlace = "";
		 String msql = "select disc from param_tab where code = 'PLACE' and status = 1";
		 try {
			Statement st = repBean.getCn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rsTmp = st.executeQuery(msql);
			if(!rsTmp.next()){
				System.out.println("Place Not Found In Parameter");
				return;
			}
			ReportDAO.println(""+(char)18, 0, 1, false, "BANK",repBean);
			ReportDAO.println(""+(char)218, 0, 1, false, "BANK",repBean);
			ReportDAO.println(UtilityDAO.stringOfSize(78, '-'), 1, 0, false, "BANK",repBean);
			ReportDAO.println(""+(char)191, 79, 1, false, "BANK",repBean);
			ReportDAO.println("|", taxRepBean.getC_ul0(), 1, false, "BANK",repBean);
			ReportDAO.println("1. Gross Salary  ", 2, 0, false, "BANK",repBean);
			print1234bar(repBean, taxRepBean);
			ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
			ReportDAO.println( "   (a) Salary as per provisions    ", 2, 0, false, "BANK",repBean);
			print1234bar(repBean, taxRepBean);
			ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
			ReportDAO.println( "       contained in section 17(1)  ", 2, 0, false, "BANK",repBean);
			ReportDAO.println("Rs.", taxRepBean.getC_rs1(), 0, false, "BANK",repBean);
			taxRepBean.setC4tot(0);
			 taxRepBean.setC5tot(0);
			 taxRepBean.setC3tot(0);
			 taxRepBean.setTotamt(0);
			buildsqlF16(peno, prdt, "A1%",taxRepBean);
			printgrpF16(peno, prdt,repBean,taxRepBean);
			
			taxRepBean.setTotinc(taxRepBean.getC5tot());
			
			buildsqlF16(peno, prdt, "A2%",taxRepBean);
			printgrpF16(peno, prdt,repBean,taxRepBean);
			
			taxRepBean.setTotinc(taxRepBean.getC5tot());
			taxRepBean.setStr1(" SELECT Y.TRNCD,SUM(NET_AMT) net_amt,sum(inp_amt) inp_amt " +
				   " FROM YTDTRAN Y " +
				   " Where y.trncd = 120 and  Y.empno = " + peno +
				   " AND Y.TRNDT BETWEEN '" +ReportDAO.Boy(prdt) + "'  AND '" +ReportDAO.EOM(prdt)+  "' group by trncd");
			taxRepBean.setVperk_amt(0);
			trs = null;
			Statement st3 = repBean.getCn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			trs = st3.executeQuery(taxRepBean.getStr1());
			if(trs.next()){
	
				if(trs.getString("net_amt")==null){
					taxRepBean.setVperk_amt(trs.getInt("net_amt"));
				}else{
					taxRepBean.setVperk_amt(trs.getInt("net_amt"));
				}
			}
			taxRepBean.setTotinc(taxRepBean.getTotinc() - taxRepBean.getVperk_amt());
			ReportDAO.println(UtilityDAO.trans(Math.round(taxRepBean.getTotinc()), "99,99,999", "", false,false), taxRepBean.getC_am1(), 0, false, "BANK",repBean);
			
			print1234bar(repBean, taxRepBean);
			ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
			ReportDAO.println("   (b) Value of perquisites under  ", 2, 0, false, "BANK",repBean);
			print1234bar(repBean, taxRepBean);
			ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
			ReportDAO.println("       section 17(2) (as per Form  ", 2, 0, false, "BANK",repBean);
			print1234bar(repBean, taxRepBean);
			ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
			ReportDAO.println("       No.12BA,wherever applicable)", 2, 0, false, "BANK",repBean);
			ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
			ReportDAO.println( "Rs.", taxRepBean.getC_rs1(), 0, false, "BANK",repBean);
			ReportDAO.println(UtilityDAO.trans(Math.round(taxRepBean.getVperk_amt()), "9,99,999", "", false,false), taxRepBean.getC_am1(), 0, false, "BANK",repBean);
			print234bar(repBean, taxRepBean);
			ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
			ReportDAO.println("   (c) Profits in lieu of salary   ", 2, 0, false, "BANK",repBean);
			print234bar(repBean, taxRepBean);
			ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
			ReportDAO.println("       under section 17(3) (as per ", 2, 0, false, "BANK",repBean);
			print234bar(repBean, taxRepBean);
			ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
			ReportDAO.println("       Form No. 12BA,wherever      ", 2, 0, false, "BANK",repBean);
			print234bar(repBean, taxRepBean);
			ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
			ReportDAO.println("       applicable )                ", 2, 0, false, "BANK",repBean);
			ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
			
			print234bar(repBean, taxRepBean);
			ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
			ReportDAO.println("   (d) Total                       ", 2, 0, false, "BANK",repBean);
			ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
			ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
			ReportDAO.println("Rs.", taxRepBean.getC_rs2(), 0, false, "BANK",repBean);
			taxRepBean.setTotinc(taxRepBean.getTotinc() + taxRepBean.getVperk_amt());
			ReportDAO.println(UtilityDAO.trans(Math.round(taxRepBean.getTotinc()), "99,99,999", "", false,false), taxRepBean.getC_am2(), 0, false, "BANK",repBean);
			print234bar(repBean, taxRepBean);
			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "A3%",taxRepBean);
			printgrpF16(peno, prdt,repBean,taxRepBean);
			us10 = Math.abs(Math.round((taxRepBean.getC5tot())));
			ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
			ReportDAO.println("2. Less : Allowances to the extent ", 2, 0, false, "BANK",repBean);
			ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
			print234bar(repBean, taxRepBean);
			ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
			ReportDAO.println("   exempt under section 10 ", 2, 0, false, "BANK",repBean);
			ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
			ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
			ReportDAO.println("Rs.", taxRepBean.getC_rs2(), 0, false, "BANK",repBean);
			ReportDAO.println(UtilityDAO.trans(Math.round(us10), "9,99,999", "", false,false), taxRepBean.getC_am2(), 0, false, "BANK",repBean);
			print34bar(repBean, taxRepBean);
			pringapline(repBean, taxRepBean);
			taxRepBean.setTotinc(taxRepBean.getTotinc() - us10);
		    ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
			ReportDAO.println("3. Balance (1 - 2) ", 2, 0, false, "BANK",repBean);
			ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
			ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
			ReportDAO.println("Rs.", taxRepBean.getC_rs2(), 0, false, "BANK",repBean);
			ReportDAO.println(UtilityDAO.trans(Math.round(taxRepBean.getTotinc()), "99,99,999", "", false,false), taxRepBean.getC_am2(), 0, false, "BANK",repBean);
			ftotinc = taxRepBean.getTotinc();
			print34bar(repBean, taxRepBean);
			pringapline(repBean, taxRepBean);
			
			ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
			ReportDAO.println("4. Deductions u/s 16 :  ", 2, 0, false, "BANK",repBean);
			print34bar(repBean, taxRepBean);
			ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
			ReportDAO.println("  a)Entertainment allowance:Rs.", 2, 0, false, "BANK",repBean);
			print34bar(repBean, taxRepBean);
			 taxRepBean.setC4tot(0);
			 taxRepBean.setC3tot(0);
			 taxRepBean.setC5tot(0);
			 buildsql(peno, prdt,"A5%",taxRepBean);
			 printgrpF16(peno, prdt,repBean,taxRepBean);
			   
			 taxRepBean.setOTHER_DED(taxRepBean.getC5tot());
			 taxRepBean.setTotamt((taxRepBean.getTotinc() - (taxRepBean.getTotded() + taxRepBean.getOTHER_DED())));
			 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
				ReportDAO.println("  b)Tax on Employment      :Rs." + UtilityDAO.trans(taxRepBean.getOTHER_DED(), "999,999","", false, false), 2, 0, false, "BANK",repBean);
				print34bar(repBean, taxRepBean);
				pringapline(repBean, taxRepBean);
				ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
				ReportDAO.println("5. Aggregate of 4(a) to (b)           ", 2, 0, false, "BANK",repBean);
				ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
				ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
				ReportDAO.println("Rs.", taxRepBean.getC_rs2(), 0, false, "BANK",repBean);
					
				ReportDAO.println(UtilityDAO.trans(Math.round(taxRepBean.getTotded()+taxRepBean.getOTHER_DED()), "99,99,999", "", false,false), taxRepBean.getC_am2(), 0, false, "BANK",repBean);
				print34bar(repBean, taxRepBean);
				pringapline(repBean, taxRepBean);
				ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
				ReportDAO.println("6.  Income chargeble under    ", 2, 0, false, "BANK",repBean);
				ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
				ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
				ReportDAO.println("|", taxRepBean.getC_ul3(), 0, false, "BANK",repBean);
				ReportDAO.println("Rs.", taxRepBean.getC_rs3(), 0, false, "BANK",repBean);
				
				ReportDAO.println(UtilityDAO.trans(Math.round(taxRepBean.getTotamt()), "99,99,999", "", false,false), taxRepBean.getC_am3(), 0, false, "BANK",repBean);
				ReportDAO.println("|", taxRepBean.getC_ul4(), 0, false, "BANK",repBean);
				pringapline(repBean, taxRepBean);
				ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
				ReportDAO.println("    the Head 'Salaries'(3-5)   ", 2, 0, false, "BANK",repBean);
				ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
				print34bar(repBean, taxRepBean);
				pringapline(repBean, taxRepBean);
				ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
				taxRepBean.setTotinc(taxRepBean.getTotamt());
				ReportDAO.println("7. Add :Any other income reported ", 2, 0, false, "BANK",repBean);
				
				ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
				ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
				ReportDAO.println("|", taxRepBean.getC_ul3(), 0, false, "BANK",repBean);
				ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
				taxRepBean.setC5tot(0);
				buildsql(peno, prdt, "AA1%",taxRepBean);
				printgrpF16(peno, prdt,repBean,taxRepBean);
				ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
				ReportDAO.println("        by the employee", 2, 0, false, "BANK",repBean);
				ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
				ReportDAO.println("Rs.", taxRepBean.getC_rs1(), 0, false, "BANK",repBean);
				ReportDAO.println(UtilityDAO.trans(Math.round(Math.abs(taxRepBean.getC5tot())), "9,99,999", "", false,false), taxRepBean.getC_am1(), 0, false, "BANK",repBean);
				ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
				
				print34bar(repBean, taxRepBean);
				
				ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
				ReportDAO.println("   Add : Income From House Property ", 2, 0, false, "BANK",repBean);
				print1234bar(repBean, taxRepBean);
				taxRepBean.setC5tot(0);
				 buildsql(peno, prdt, "AA3%",taxRepBean);
				 printgrpF16(peno, prdt,repBean,taxRepBean);
				 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
				 ReportDAO.println("   Less: Int.charged on Housing Loan", 2, 0, false, "BANK",repBean);
				ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
				ReportDAO.println("Rs.", taxRepBean.getC_rs1(), 0, false, "BANK",repBean);
				ReportDAO.println(UtilityDAO.trans(Math.round(Math.abs(taxRepBean.getC5tot())), "9,99,999", "", false,false), taxRepBean.getC_am1(), 0, false, "BANK",repBean);
				ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
			
				taxRepBean.setC5tot(0);
				 buildsql(peno, prdt, "AA%",taxRepBean);
				 printgrpF16(peno, prdt,repBean,taxRepBean);
				 ReportDAO.println("Rs.", taxRepBean.getC_rs2(), 0, false, "BANK",repBean);
				 ReportDAO.println(UtilityDAO.trans(Math.round(Math.abs(taxRepBean.getC5tot())), "9,99,999", "", false,false), taxRepBean.getC_am2(), 0, false, "BANK",repBean);
				 print34bar(repBean, taxRepBean);
				 pringapline(repBean, taxRepBean);
					ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
					ReportDAO.println("8. Gross Total Income (6 + 7) ", 2, 0, false, "BANK",repBean);
					
					ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
					ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
					ReportDAO.println("|", taxRepBean.getC_ul3(), 0, false, "BANK",repBean);
					 ReportDAO.println("Rs.", taxRepBean.getC_rs3(), 0, false, "BANK",repBean);
					 taxRepBean.setTotinc(taxRepBean.getTotinc() + taxRepBean.getC5tot());
					 ReportDAO.println(UtilityDAO.trans(Math.round(taxRepBean.getTotinc()), "99,99,999", "", false,false), taxRepBean.getC_am3(), 0, false, "BANK",repBean);
					ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
					pringapline(repBean, taxRepBean);
					ReportDAO.println(" ", 1, 1, true, " ",repBean);
					ReportDAO.println(""+(char)218, 0, 0, false, "BANK",repBean);
					ReportDAO.println(UtilityDAO.stringOfSize(78,'-'), 1, 0, false, "BANK",repBean);
					ReportDAO.println(""+(char)191, 79, 1, false, "BANK",repBean);
					
					ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
					ReportDAO.println("9. Deductions (chapter VI-A) GROSS", 2, 0, false, "BANK",repBean);
					ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
					ReportDAO.println( "QUALIFYING", taxRepBean.getC_ul1() + 2, 0, false, "BANK",repBean);
					ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
					ReportDAO.println("DEDUCTED", taxRepBean.getC_ul2() + 2, 0, false, "BANK",repBean);
					ReportDAO.println("|", taxRepBean.getC_ul3(), 0, false, "BANK",repBean);
					ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
					ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
					 ReportDAO.println("                              AMOUNT", 2, 0, false, "BANK",repBean);
					
					 ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
						ReportDAO.println("  AMOUNT  ", taxRepBean.getC_ul1() + 2, 0, false, "BANK",repBean);
						ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
						ReportDAO.println(" AMOUNT ", taxRepBean.getC_ul2() + 2, 0, false, "BANK",repBean);
						
						ReportDAO.println("|", taxRepBean.getC_ul3(), 0, false, "BANK",repBean);
						ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
						ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
						 ReportDAO.println("  A) Section 80C, 80CCC, and 80CCD", 2, 0, false, "BANK",repBean);
						 print1234bar(repBean, taxRepBean);
						 taxRepBean.setC5tot(0);
						   buildsql(peno, prdt, "AC%",taxRepBean);
							 printgrpF16(peno, prdt,repBean,taxRepBean);
							 if(taxRepBean.getC5tot() > 100000){
								 q80d = 100000;
							 }else{
							       q80d = taxRepBean.getC5tot();
							 }
							 
							ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
							ReportDAO.println("     a) U/S 80 C       Rs." + UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false, false), 2, 0, false, "BANK",repBean);
							ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
							ReportDAO.println("Rs.", taxRepBean.getC_rs1(), 0, false, "BANK",repBean);
							 ReportDAO.println(UtilityDAO.trans(Math.round(Math.abs(q80d)), "9,99,999", "", false,false), taxRepBean.getC_am1(), 0, false, "BANK",repBean);
							ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
							ReportDAO.println("Rs.", taxRepBean.getC_rs2(), 0, false, "BANK",repBean);
							ReportDAO.println(UtilityDAO.trans(Math.round(Math.abs(q80d)), "9,99,999", "", false,false), taxRepBean.getC_am2(), 0, false, "BANK",repBean);
							print34bar(repBean, taxRepBean);
							
							taxRepBean.setC5tot(0);
							 buildsql(peno, prdt, "ACA%",taxRepBean);
							 printgrpF16(peno, prdt,repBean,taxRepBean);
							 if(taxRepBean.getC5tot() > 0){
								 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
									ReportDAO.println("       - P.F.             " + UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false, false), 2, 0, false, "BANK",repBean);
									 ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
									ReportDAO.println("   ", taxRepBean.getC_rs1(), 0, false, "BANK",repBean);
									ReportDAO.println("        ", taxRepBean.getC_am1(), 0, false, "BANK",repBean);
									
									 ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
									 ReportDAO.println("   ", taxRepBean.getC_rs2(), 0, false, "BANK",repBean);
								  	ReportDAO.println("        ", taxRepBean.getC_am2(), 0, false, "BANK",repBean);
								  	print34bar(repBean, taxRepBean);
								  	
							 }
							 taxRepBean.setC5tot(0);
								 buildsql(peno, prdt, "ACB%",taxRepBean);
								 printgrpF16(peno, prdt,repBean,taxRepBean);
								 if(taxRepBean.getC5tot() > 0){
									 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										ReportDAO.println("       - P.P.F.           " + UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false, false), 2, 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
										ReportDAO.println("   ", taxRepBean.getC_rs1(), 0, false, "BANK",repBean);
										ReportDAO.println("        ", taxRepBean.getC_am1(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
										 ReportDAO.println("   ", taxRepBean.getC_rs2(), 0, false, "BANK",repBean);
									  	ReportDAO.println("        ", taxRepBean.getC_am2(), 0, false, "BANK",repBean);
									  	print34bar(repBean, taxRepBean);
									  	
								 }
							 
								 taxRepBean.setC5tot(0);
								 buildsql(peno, prdt, "ACC%",taxRepBean);
								 printgrpF16(peno, prdt,repBean,taxRepBean);
								 if(taxRepBean.getC5tot() > 0){
									 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										ReportDAO.println("       - Life Ins.Premium " + UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false, false), 2, 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
										ReportDAO.println("   ", taxRepBean.getC_rs1(), 0, false, "BANK",repBean);
										ReportDAO.println("        ", taxRepBean.getC_am1(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
										 ReportDAO.println("   ", taxRepBean.getC_rs2(), 0, false, "BANK",repBean);
									  	ReportDAO.println("        ", taxRepBean.getC_am2(), 0, false, "BANK",repBean);
									  	print34bar(repBean, taxRepBean);
									  	
								 }
								 
								 taxRepBean.setC5tot(0);
								 buildsql(peno, prdt, "ACD%",taxRepBean);
								 printgrpF16(peno, prdt,repBean,taxRepBean);
								 if(taxRepBean.getC5tot() > 0){
									 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										ReportDAO.println( "       - Hsg.LN Ded.      " + UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false, false), 2, 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
										ReportDAO.println("   ", taxRepBean.getC_rs1(), 0, false, "BANK",repBean);
										ReportDAO.println("        ", taxRepBean.getC_am1(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
										 ReportDAO.println("   ", taxRepBean.getC_rs2(), 0, false, "BANK",repBean);
									  	ReportDAO.println("        ", taxRepBean.getC_am2(), 0, false, "BANK",repBean);
									  	print34bar(repBean, taxRepBean);
									  	
								 }
								 taxRepBean.setC5tot(0);
								 buildsql(peno, prdt, "ACE%",taxRepBean);
								 printgrpF16(peno, prdt,repBean,taxRepBean);
								 if(taxRepBean.getC5tot() > 0){
									 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										ReportDAO.println("       - U.T.I            " + UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false, false), 2, 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
										ReportDAO.println("   ", taxRepBean.getC_rs1(), 0, false, "BANK",repBean);
										ReportDAO.println("        ", taxRepBean.getC_am1(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
										 ReportDAO.println("   ", taxRepBean.getC_rs2(), 0, false, "BANK",repBean);
									  	ReportDAO.println("        ", taxRepBean.getC_am2(), 0, false, "BANK",repBean);
									  	print34bar(repBean, taxRepBean);
									  	
								 }
								 taxRepBean.setC5tot(0);
								 buildsql(peno, prdt, "ACF%",taxRepBean);
								 printgrpF16(peno, prdt,repBean,taxRepBean);
								 if(taxRepBean.getC5tot() > 0){
									 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										ReportDAO.println("       - N.S.C            "+ UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false, false), 2, 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
										ReportDAO.println("   ", taxRepBean.getC_rs1(), 0, false, "BANK",repBean);
										ReportDAO.println("        ", taxRepBean.getC_am1(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
										 ReportDAO.println("   ", taxRepBean.getC_rs2(), 0, false, "BANK",repBean);
									  	ReportDAO.println("        ", taxRepBean.getC_am2(), 0, false, "BANK",repBean);
									  	print34bar(repBean, taxRepBean);
									  	
								 }
								 
								 taxRepBean.setC5tot(0);
								 buildsql(peno, prdt, "ACG%",taxRepBean);
								 printgrpF16(peno, prdt,repBean,taxRepBean);
								 if(taxRepBean.getC5tot() > 0){
									 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										ReportDAO.println("       - N.S.S            " + UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false, false), 2, 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
										ReportDAO.println("   ", taxRepBean.getC_rs1(), 0, false, "BANK",repBean);
										ReportDAO.println("        ", taxRepBean.getC_am1(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
										 ReportDAO.println("   ", taxRepBean.getC_rs2(), 0, false, "BANK",repBean);
									  	ReportDAO.println("        ", taxRepBean.getC_am2(), 0, false, "BANK",repBean);
									  	print34bar(repBean, taxRepBean);
									  	
								 }
								 
								 taxRepBean.setC5tot(0);
								 buildsql(peno, prdt, "ACH%",taxRepBean);
								 printgrpF16(peno, prdt,repBean,taxRepBean);
								 if(taxRepBean.getC5tot() > 0){
									 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										ReportDAO.println("       - F.P.F            " + UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false, false), 2, 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
										ReportDAO.println("   ", taxRepBean.getC_rs1(), 0, false, "BANK",repBean);
										ReportDAO.println("        ", taxRepBean.getC_am1(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
										 ReportDAO.println("   ", taxRepBean.getC_rs2(), 0, false, "BANK",repBean);
									  	ReportDAO.println("        ", taxRepBean.getC_am2(), 0, false, "BANK",repBean);
									  	print34bar(repBean, taxRepBean);
									  	
								 }
								 
								 taxRepBean.setC5tot(0);
								 buildsql(peno, prdt, "ACI%",taxRepBean);
								 printgrpF16(peno, prdt,repBean,taxRepBean);
								 if(taxRepBean.getC5tot() > 0){
									 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										ReportDAO.println("       - NSC Accrued Int. "  + UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false, false), 2, 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
										ReportDAO.println("   ", taxRepBean.getC_rs1(), 0, false, "BANK",repBean);
										ReportDAO.println("        ", taxRepBean.getC_am1(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
										 ReportDAO.println("   ", taxRepBean.getC_rs2(), 0, false, "BANK",repBean);
									  	ReportDAO.println("        ", taxRepBean.getC_am2(), 0, false, "BANK",repBean);
									  	print34bar(repBean, taxRepBean);
									  	
								 }
								 
								 taxRepBean.setC5tot(0);
								 buildsql(peno, prdt, "ACK%",taxRepBean);
								 printgrpF16(peno, prdt,repBean,taxRepBean);
								 if(taxRepBean.getC5tot() > 0){
									 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										ReportDAO.println("       - Tution Fee       "  + UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false, false), 2, 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
										ReportDAO.println("   ", taxRepBean.getC_rs1(), 0, false, "BANK",repBean);
										ReportDAO.println("        ", taxRepBean.getC_am1(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
										 ReportDAO.println("   ", taxRepBean.getC_rs2(), 0, false, "BANK",repBean);
									  	ReportDAO.println("        ", taxRepBean.getC_am2(), 0, false, "BANK",repBean);
									  	print34bar(repBean, taxRepBean);
									  	
								 }
								 
								 taxRepBean.setC5tot(0);
								 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
									ReportDAO.println("     b) Secton 80 CCC       Rs.", 2, 0, false, "BANK",repBean);
									ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
									ReportDAO.println("Rs.", taxRepBean.getC_rs1(), 0, false, "BANK",repBean);
									ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
									ReportDAO.println("Rs.", taxRepBean.getC_rs2(), 0, false, "BANK",repBean);
									print34bar(repBean, taxRepBean);
									 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
									 ReportDAO.println("     c) Secton 80 CCD       Rs.", 2, 0, false, "BANK",repBean);
									 ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
									 ReportDAO.println("Rs.", taxRepBean.getC_rs1(), 0, false, "BANK",repBean);
									 ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
									 ReportDAO.println("Rs.", taxRepBean.getC_rs2(), 0, false, "BANK",repBean);
									 print34bar(repBean, taxRepBean);
                                     
									 taxRepBean.setC5tot(0);
									 buildsql(peno, prdt, "AKA%",taxRepBean);
									 printgrpF16(peno, prdt,repBean,taxRepBean);
									 q80ccf = (int)Calculate.checkSlab(551, prdt, taxRepBean.getC5tot(), 1,peno,repBean.getCn()); 
								     
								    ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
									ReportDAO.println("     d) Secton 80 CCF(InfBond) "  + UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false, false), 2, 0, false, "BANK",repBean);
									ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
									ReportDAO.println("Rs.", taxRepBean.getC_rs1(), 0, false, "BANK",repBean);
									ReportDAO.println(UtilityDAO.trans(Math.round(q80ccf), "9,99,999","", false, false), taxRepBean.getC_am1(), 0, false, "BANK",repBean);
									
									ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
									ReportDAO.println("Rs.", taxRepBean.getC_rs2(), 0, false, "BANK",repBean);
									ReportDAO.println(UtilityDAO.trans(Math.round(q80ccf), "9,99,999","", false, false), taxRepBean.getC_am2(), 0, false, "BANK",repBean);
									print34bar(repBean, taxRepBean); 
									taxRepBean.setC5tot(0);
									 buildsql(peno, prdt, "AD1%",taxRepBean);
									 printgrpF16(peno, prdt,repBean,taxRepBean);
									 tmpamt = q80d;
									 q80ccf = (int)Calculate.checkSlab(801, prdt, taxRepBean.getC5tot(), 1,peno,repBean.getCn()); 
								     
								    ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
									ReportDAO.println("  B) Other Sections(for e.g. 80E, 80G etc) under Chapter VIA", 2, 0, false, "BANK",repBean);
									ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
									print34bar(repBean, taxRepBean);
									ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
									ReportDAO.println("     a) U/S 80 D       Rs."  + UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false, false), 2, 0, false, "BANK",repBean);
									ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
									ReportDAO.println("Rs.", taxRepBean.getC_rs1(), 0, false, "BANK",repBean);
									ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
									ReportDAO.println("Rs.", taxRepBean.getC_rs2(), 0, false, "BANK",repBean);
									print34bar(repBean, taxRepBean);
									
									q80d = q80d + tmpamt;
									
									taxRepBean.setC5tot(0);
									 buildsql(peno, prdt, "AC3%",taxRepBean);
									 printgrpF16(peno, prdt,repBean,taxRepBean);
									 q80e = (int)Calculate.checkSlab(802, prdt, taxRepBean.getC5tot(), 1,peno,repBean.getCn()); 
								     
								    ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
									ReportDAO.println("     b) U/S 80 E       Rs." + UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false, false), 2, 0, false, "BANK",repBean);
									ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
									ReportDAO.println("Rs.", taxRepBean.getC_rs1(), 0, false, "BANK",repBean);
									ReportDAO.println(UtilityDAO.trans(Math.round(q80e), "9,99,999","", false, false), taxRepBean.getC_am1(), 0, false, "BANK",repBean);
									ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
									ReportDAO.println("Rs.", taxRepBean.getC_rs2(), 0, false, "BANK",repBean);
									ReportDAO.println(UtilityDAO.trans(Math.round(q80e), "9,99,999","", false, false), taxRepBean.getC_am2(), 0, false, "BANK",repBean);
									print34bar(repBean, taxRepBean);
									
									taxRepBean.setC5tot(0);
									 buildsql(peno, prdt, "AE1%",taxRepBean);
									 printgrpF16(peno, prdt,repBean,taxRepBean);
									 q80gPM = (int)Calculate.checkSlab(807, prdt, taxRepBean.getC5tot(), 1,peno,repBean.getCn()); 
								     
								    ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
									ReportDAO.println( "     c) U/S 80 G(CMrel)Rs." + UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false, false), 2, 0, false, "BANK",repBean);
									ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
									ReportDAO.println("Rs.", taxRepBean.getC_rs1(), 0, false, "BANK",repBean);
									ReportDAO.println(UtilityDAO.trans(Math.round(q80gPM), "9,99,999","", false, false), taxRepBean.getC_am1(), 0, false, "BANK",repBean);
									ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
									ReportDAO.println("Rs.", taxRepBean.getC_rs2(), 0, false, "BANK",repBean);
									ReportDAO.println(UtilityDAO.trans(Math.round(q80gPM), "9,99,999","", false, false), taxRepBean.getC_am2(), 0, false, "BANK",repBean);
									print34bar(repBean, taxRepBean);
									
									taxRepBean.setC5tot(0);
									 buildsql(peno, prdt, "AEA%",taxRepBean);
									 printgrpF16(peno, prdt,repBean,taxRepBean);
									 q80g = (int)Calculate.checkSlab(803, prdt, taxRepBean.getC5tot(), 1,peno,repBean.getCn()); 
								     
								    ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
									ReportDAO.println("     d) U/S 80 G(Other)Rs." + UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false, false), 2, 0, false, "BANK",repBean);
									ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
									ReportDAO.println("Rs.", taxRepBean.getC_rs1(), 0, false, "BANK",repBean);
									ReportDAO.println(UtilityDAO.trans(Math.round(q80g), "9,99,999","", false, false), taxRepBean.getC_am1(), 0, false, "BANK",repBean);
									ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
									ReportDAO.println("Rs.", taxRepBean.getC_rs2(), 0, false, "BANK",repBean);
									ReportDAO.println(UtilityDAO.trans(Math.round(q80g), "9,99,999","", false, false), taxRepBean.getC_am2(), 0, false, "BANK",repBean);
									print34bar(repBean, taxRepBean);
									
									taxRepBean.setC5tot(0);
									 buildsql(peno, prdt, "AEB%",taxRepBean);
									 printgrpF16(peno, prdt,repBean,taxRepBean);
									 
								     q80gG = taxRepBean.getC5tot();
								    ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
									ReportDAO.println( "     e) U/S 80GG(RentPd)Rs" + UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false, false), 2, 0, false, "BANK",repBean);
									ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
									ReportDAO.println("Rs.", taxRepBean.getC_rs1(), 0, false, "BANK",repBean);
									ReportDAO.println(UtilityDAO.trans(Math.round(q80gG), "9,99,999","", false, false), taxRepBean.getC_am1(), 0, false, "BANK",repBean);
									ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
									ReportDAO.println("Rs.", taxRepBean.getC_rs2(), 0, false, "BANK",repBean);
									ReportDAO.println(UtilityDAO.trans(Math.round(q80gG), "9,99,999","", false, false), taxRepBean.getC_am2(), 0, false, "BANK",repBean);
									print34bar(repBean, taxRepBean);
									
									taxRepBean.setC5tot(0);
									buildsql(peno, prdt, "AF1%",taxRepBean);
									printgrpF16(peno, prdt,repBean,taxRepBean);
									q80u = (int)Calculate.checkSlab(805, prdt, taxRepBean.getC5tot(), 1,peno,repBean.getCn()); 
								    ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
									ReportDAO.println("     f) U/S 80 U       Rs." + UtilityDAO.trans(taxRepBean.getC5tot(), "999,999","", false, false), 2, 0, false, "BANK",repBean);
									ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
									ReportDAO.println("Rs.", taxRepBean.getC_rs1(), 0, false, "BANK",repBean);
									ReportDAO.println(UtilityDAO.trans(Math.round(q80u), "9,99,999","", false, false), taxRepBean.getC_am1(), 0, false, "BANK",repBean);
									ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
									ReportDAO.println("Rs.", taxRepBean.getC_rs2(), 0, false, "BANK",repBean);
									ReportDAO.println(UtilityDAO.trans(Math.round(q80u), "9,99,999","", false, false), taxRepBean.getC_am2(), 0, false, "BANK",repBean);
									print34bar(repBean, taxRepBean);
									pringapline(repBean, taxRepBean);
									 via = q80cc + q80d + q80e + q80gPM + q80g + q80gG + q80l + q80u + q80ccf;
									 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										ReportDAO.println("10.Aggregate of deductible  ", 2, 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul3(), 0, false, "BANK",repBean);
										ReportDAO.println("Rs.", taxRepBean.getC_rs3(), 0, false, "BANK",repBean);
										ReportDAO.println(UtilityDAO.trans(Math.round(via), "9,99,999","", false, false), taxRepBean.getC_am3(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										
										ReportDAO.println("  amount under chapter VI-A ", 2, 0, false, "BANK",repBean);
										print34bar(repBean, taxRepBean);
										
										
										ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										ReportDAO.println("11.Total of Income (8 - 10) ", 2, 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul3(), 0, false, "BANK",repBean);
										ReportDAO.println(UtilityDAO.trans(Math.round(taxRepBean.getTotinc() - via), "9,99,999","",false,false), taxRepBean.getC_am3(), 0, false, "BANK",repBean);//Changed by nakul
										ReportDAO.println("|", taxRepBean.getC_ul4(), 0, false, "BANK",repBean);
										
										ftotinc = taxRepBean.getTotinc();
										inctot = taxRepBean.getTotinc() - via;
										if(SEX1.equalsIgnoreCase("F")){
											caltax = Math.round(TaxCalculation.Chk_Slab1(703, prdt, inctot, 2, repBean));
										}
										
										  // CHECK AGE . IF ABOVE 65, THEN  PASS 3 AS CATEGORY CODE
										int A = 0;
										A = UtilityDAO.dateDiff("yyyy", pdob, java.sql.Date.valueOf("28-Jun-2003"));//********************Second date remainning
										if(A < 65){   //age less than 65           
											caltax = Math.round(TaxCalculation.Chk_Slab1(703, prdt, inctot, 1, repBean));
										}else{   //age greter than 65   
											caltax = Math.round(TaxCalculation.Chk_Slab1(703, prdt, inctot, 3, repBean));
										}
										
										ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										ReportDAO.println("12.Tax on Total Income", 2, 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul3(), 0, false, "BANK",repBean);
										ReportDAO.println("Rs.", taxRepBean.getC_rs3(), 0, false, "BANK",repBean);
										ReportDAO.println(UtilityDAO.trans(Math.round(caltax), "9,99,999","",false,false), taxRepBean.getC_am3(), 0, false, "BANK",repBean);//Changed by nakul
										ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
										ReportDAO.println("|", 0, 0, false, "BANK",repBean);
										
										taxRepBean.setSercharge(0);
										if(Math.round(taxRepBean.getReb88() + us89 + b88 + c88 + reb88d) > caltax ){
											taxRepBean.setNet_tax(0);
										}else{
											taxRepBean.setNet_tax(Math.round(caltax - (taxRepBean.getReb88() + us89 + b88 + c88 + reb88d)));
										}
										
										if(taxRepBean.getNet_tax() > 0){
											taxRepBean.setSercharge((int)Calculate.checkSlab(900, prdt, taxRepBean.getNet_tax(), 1,peno,repBean.getCn()));
										}
										taxRepBean.setNet_tax(taxRepBean.getNet_tax() + taxRepBean.getSercharge());
										
										ReportDAO.println("13.Surcharge (on tax computed at S.No.12)", 2, 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul3(), 0, false, "BANK",repBean);
										ReportDAO.println("Rs.", taxRepBean.getC_rs3(), 0, false, "BANK",repBean);
										ReportDAO.println(UtilityDAO.trans(Math.round(taxRepBean.getSercharge()), "9,99,999","",false,false), taxRepBean.getC_am3(), 0, false, "BANK",repBean);//Changed by nakul
										ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
										ReportDAO.println("|", 0, 0, false, "BANK",repBean);
										
										
										ReportDAO.println("14.Education cess (on tax 12 & 13)", 2, 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul3(), 0, false, "BANK",repBean);
										ReportDAO.println("Rs.", taxRepBean.getC_rs3(), 0, false, "BANK",repBean);
										 educess = (int)(taxRepBean.getNet_tax() * 0.03);
										ReportDAO.println(UtilityDAO.trans(Math.round(educess), "9,99,999","",false,false), taxRepBean.getC_am3(), 0, false, "BANK",repBean);//Changed by nakul
										ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
										pringapline(repBean, taxRepBean);
										
                                        ReportDAO.println("|", 0, 0, false, "BANK",repBean);
																
										ReportDAO.println("15.Tax payable (12+13+14)", 2, 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul3(), 0, false, "BANK",repBean);
										ReportDAO.println("Rs.", taxRepBean.getC_rs3(), 0, false, "BANK",repBean);
										taxRepBean.setNet_tax(taxRepBean.getNet_tax() + educess);
										ReportDAO.println(UtilityDAO.trans(Math.round(taxRepBean.getNet_tax()), "9,99,999","",false,false), taxRepBean.getC_am3(), 0, false, "BANK",repBean);//Changed by nakul
										ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);

										taxRepBean.setC5tot(0);
										buildsql(peno, prdt, "AN3%",taxRepBean);
										printgrpF16(peno, prdt,repBean,taxRepBean);
										us89 = taxRepBean.getC5tot();
										ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										ReportDAO.println("16. Relief u/s 89 (attach details)", 2, 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
										ReportDAO.println("Rs.", taxRepBean.getC_rs2(), 0, false, "BANK",repBean);
										ReportDAO.println(UtilityDAO.trans(Math.round(us89), "9,99,999","", false, false), taxRepBean.getC_am2(), 0, false, "BANK",repBean);
										print34bar(repBean, taxRepBean);
										
										ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										ReportDAO.println("17. Tax Payable   (13 - 14)", 2, 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul3(), 0, false, "BANK",repBean);
										ReportDAO.println("Rs.", taxRepBean.getC_rs3(), 0, false, "BANK",repBean);
										
										
										ReportDAO.println(UtilityDAO.trans(Math.round(taxRepBean.getNet_tax()), "9,99,999","",false,false), taxRepBean.getC_am3(), 0, false, "BANK",repBean);//Changed by nakul
										ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
										
										
										taxRepBean.setC5tot(0);
										buildsql(peno, prdt, "BB%",taxRepBean);
										printgrpF16(peno, prdt,repBean,taxRepBean);
										taxRepBean.setTAXPAID(taxRepBean.getC5tot());
										ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										ReportDAO.println("18. Less : (a) TDS  US 192(1)", 2, 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul3(), 0, false, "BANK",repBean);
										ReportDAO.println("Rs.", taxRepBean.getC_rs3(), 0, false, "BANK",repBean);
										ReportDAO.println(UtilityDAO.trans(Math.round(taxRepBean.getTAXPAID()), "9,99,999","", false, false), taxRepBean.getC_am3(), 0, false, "BANK",repBean);
										ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
										 taxRepBean.setNet_tax(Math.round(taxRepBean.getNet_tax()) - Math.round(taxRepBean.getTAXPAID()));
										 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										 ReportDAO.println("(b) Tax pd.by the empr.on behalf  ", 2, 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul3(), 0, false, "BANK",repBean);
										 ReportDAO.println("Rs.", taxRepBean.getC_rs3(), 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										 ReportDAO.println(" of the emp u/s192(1A)on per.u/s17(2)", 2, 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul3(), 0, false, "BANK",repBean);
										 ReportDAO.println("Rs.", taxRepBean.getC_rs3(), 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
										 pringapline(repBean, taxRepBean);
										 
										 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										 ReportDAO.println("19. Tax payable /refundable (17 - 18)", 2, 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul1(), 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul2(), 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul3(), 0, false, "BANK",repBean);
										 ReportDAO.println("Rs.", taxRepBean.getC_rs3(), 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
										 pringapline(repBean, taxRepBean);
										 
										 ReportDAO.println(""+(char)192, 0, 0, false, "BANK",repBean);
										 ReportDAO.println(UtilityDAO.stringOfSize(78, '-'), 1, 0, false, "BANK",repBean);
										 ReportDAO.println(""+(char)217, 79, 1, false, "BANK",repBean);
										 ReportDAO.println(""+(char)14+(char)15 + "DETAILS OF TAX DEDUCTED AND DEPOSITED INTO CENTRAL GOVT.ACCOUNT ", 1, 1, false, "BANK",repBean);
										 ReportDAO.println(""+(char)18, 0, 1, false, "BANK",repBean);
										 ReportDAO.println(""+(char)218, 0, 0, false, "BANK",repBean);
										 ReportDAO.println(UtilityDAO.stringOfSize(78, '-'), 1, 0, false, "BANK",repBean);
										 ReportDAO.println(""+(char)191, 79, 1, false, "BANK",repBean);
										 
										 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										 ReportDAO.println("  AMOUNT |  DATE OF PAYMENT |  NAME OF BANK & BRANCH WHERE TAX DEPOSITED      ", 1, 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										 ReportDAO.println(UtilityDAO.stringOfSize(78, '-'), 1 , 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										 ReportDAO.println( "      As per Annexure       |       S.B.I.TREASURY OR UNION BANK OF INDIA     ", 1, 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
										 ReportDAO.println(""+(char)192, 0, 0, false, "BANK",repBean);
										 ReportDAO.println(UtilityDAO.stringOfSize(78, '-'), 1 , 0, false, "BANK",repBean);
										 ReportDAO.println(""+(char)217, 79, 1, false, "BANK",repBean);
										 
										 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										 ReportDAO.println(" I "+"Full Name"+"List Index"+"Father Name"+" working in yhe capacity of", 1, 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										 ReportDAO.println(" " +"desiganation"+ " do hereby certify that a sum of Rs."+UtilityDAO.trans(Math.round(taxRepBean.getTAXPAID()), "9,99,999", "", false, false), 1 , 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										 ReportDAO.println(" [ " +originalNumToLetter.getInWords(String.valueOf(taxRepBean.getTAXPAID()))+ ". (in words) ] has been ", 1, 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										 ReportDAO.println(" deducted at source and paid to the credit of the Central Government. I  ", 1 , 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										 ReportDAO.println(" further Certify that the information given above is true and correct based ", 1 , 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										 ReportDAO.println(" on the books of accounts, documents and other available records.  ", 1 , 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										 ReportDAO.println("                      For " +"Orag name" + " " + "City", 1 , 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
										 
										 ReportDAO.println("|", 0, 0, false, "BANK",repBean);
										 ReportDAO.println(UtilityDAO.stringOfSize(57, '-'), 22 , 0, false, "BANK",repBean);
										 ReportDAO.println("|", 79, 1, false, "BANK",repBean);
										 
										 
										 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										 ReportDAO.println(" PLACE : " +"Place"+ "     Signature of the Person responsible for deduction of Tax", 1 , 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										 ReportDAO.println(" DATE : " +"dd/mm/yyyy"+ "   Full Name :" +"ABCDEFG", 1 , 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										 ReportDAO.println("                   Designation :" +"XYZ", 1 , 0, false, "BANK",repBean);
										 repBean.setPageLen(79);
										 ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul0(), 0, false, "BANK",repBean);
										 ReportDAO.println("|", taxRepBean.getC_ul4(), 1, false, "BANK",repBean);
										 ReportDAO.println(""+(char)192, 0, 0, false, "BANK",repBean);
										 ReportDAO.println(UtilityDAO.stringOfSize(78, '-'), 1 , 0, false, "BANK",repBean);
										 ReportDAO.println(""+(char)217, 79, 1, false, "BANK",repBean);
										 ReportDAO.println(""+(char)12, 0, 1, false, "BANK",repBean);
										 repBean.setLnCount(0);
										 repBean.setPageLen(68);
										 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}

	public static int calhlint(int ptotamt,int ppeno,RepoartBean repBean,TaxReportBean taxRepBean ){
		ResultSet ded = null;
		String dedstr = "SELECT * FROM DEDMAST WHERE TRNCD = 209 AND EMPNO ="+ppeno;
		try {
			Statement st = repBean.getCn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ded = st.executeQuery(dedstr);
			if(ded.next()){
				if(ptotamt > taxRepBean.getMaxhlint2()){
					ptotamt = taxRepBean.getMaxhlint2();
				}
			}
			st.executeUpdate("UPDATE F16 SET HSG_INT = "+ptotamt+" WHERE EMPNO = "+ppeno);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ptotamt;
	}
	
	public static void Chk_reb88_Slab(int trncd,String dt,int WrkAmt,int WrkAmt1,int emp_type,RepoartBean repBean,TaxReportBean taxRepBean){
		String slbstr = "";
		ResultSet slb = null;
		int slbamt = 0;
		switch (emp_type) {
		case 4: slbstr = " select * from slab where emp_cat = 0 and trncd = " + trncd + " and effdate = " +
                "( select min(s.effdate) from slab s where s.emp_cat = 0 and  trncd = " + trncd + " and effdate >= '" +dt+ "' ) and " +
                + WrkAmt1 + " between  frmamt and toamt order by srno ";
			 break;
        case 1:
        	slbstr = " select * from slab where emp_cat = 1 and trncd = " + trncd + " and effdate = " +
            "( select min(s.effdate) from slab s where  s.emp_cat = 1 and trncd = " + trncd + " and effdate >= '" +dt+ "' ) and " +
            + WrkAmt1 + " between  frmamt and toamt order by srno ";
		 	break;
        case 2:
        	slbstr = " select * from slab where emp_cat = 2 and trncd = " + trncd + " and effdate = " +
            "( select min(s.effdate) from slab s where  s.emp_cat = 2 and  trncd = " + trncd + " and effdate >= '" +dt+ "' ) and " +
            + WrkAmt1 + " between  frmamt and toamt order by srno ";
	    	break;
        case 3:
        	 slbstr = " select * from slab where emp_cat = 3 and trncd = " + trncd + " and effdate = " +
             "( select min(s.effdate) from slab s where  s.emp_cat = 3 and  trncd = " + trncd + " and effdate >= '" +dt+ "' ) and " +
             + WrkAmt1 + " between  frmamt and toamt order by srno ";
        	break;
		default:
			break;
		}
		try {
			Statement st = repBean.getCn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			slb = st.executeQuery(slbstr);
			if(!slb.next()){
				System.out.println("NO SLAB FOUND FOR "+trncd);
				
			}
			
			taxRepBean.setReb88_per(slb.getString("PER")!=null?slb.getInt("PER"):0);
			if(WrkAmt > 100000){
				if(WrkAmt1 < 100000){
					if(slb.getInt("PER") > 20){
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
		
	public static void printform16(int peno,String prdt,String pename,int desigcd,String PAN_NO,String SEX1,java.sql.Date dob,RepoartBean repBean,TaxReportBean taxRepBean){
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
		 grd = GradeHandler.GetGrade(grdint, "Y",repBean);
          repBean.setHdg1("Form 16");
          repBean.setHdg2("");
         
         //***************REMAIN ING********************
         print_f16_head(peno, prdt, pename, grdint, PAN_NO,repBean,taxRepBean);
         printgrp1(peno, prdt, pename, grd, PAN_NO, SEX1, dob,repBean,taxRepBean);

			
	}
	
	public static void print_f16_head(int peno,String prdt,String pename,int dsigcd,String PAN_NO,RepoartBean repBean,TaxReportBean taxRepBean){
		repBean.setHdg1("Form 16");
		String str1 = "";
		ResultSet rsTmp = null;
		String vITWard = "";
		String Q1_CNO = "";
		String Q2_CNO = "";
		String Q3_CNO = "";
		String Q4_CNO = "";
		String msql = "";
		msql = "select disc from param_tab where code = 'ITWARD' AND STATUS = 1";
		try {
			Statement st = repBean.getCn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rsTmp = st.executeQuery(msql);
			if(rsTmp.next()){
				vITWard  = rsTmp.getString("DISC");
			}else{
				System.out.println("INCOME TAX WARD OF BANK NOT FOUNF IN PARAMTER");
				return;
			}
			msql = "select disc from param_tab where code = 'Q1_CNO' AND STATUS = 1";
			rsTmp = st.executeQuery(msql);
			if(rsTmp.next()){
				Q1_CNO  = rsTmp.getString("DISC");
			}else{
				System.out.println("1ST QUARTER ACKNOWLEDGEMENT NO. not found in parameter");
				return;
			}
			
			msql = "select disc from param_tab where code = 'Q2_CNO' AND STATUS = 1";
			rsTmp = st.executeQuery(msql);
			if(rsTmp.next()){
				Q2_CNO  = rsTmp.getString("DISC");
			}else{
				System.out.println("2ND QUARTER ACKNOWLEDGEMENT NO. not found in parameter");
				return;
			}
			
			msql = "select disc from param_tab where code = 'Q3_CNO' AND STATUS = 1";
			rsTmp = st.executeQuery(msql);
			if(rsTmp.next()){
				Q3_CNO  = rsTmp.getString("DISC");
			}else{
				System.out.println("3RD QUARTER ACKNOWLEDGEMENT NO. not found in parameter");
				return;
			}
			msql = "select disc from param_tab where code = 'Q4_CNO' AND STATUS = 1";
			rsTmp = st.executeQuery(msql);
			if(rsTmp.next()){
				Q4_CNO  = rsTmp.getString("DISC");
			}else{
				System.out.println("4TH QUARTER ACKNOWLEDGEMENT NO. not found in parameter");
				return;
			}
			ReportDAO.println(UtilityDAO.stringOfSize(78, '-'), 1, 1,false, "BANK",repBean);
			ReportDAO.println("|", 0, 0,false, "BANK",repBean);
			ReportDAO.println("F O R M     N O.   16    ", 30, 0,false, "BANK",repBean);
			ReportDAO.println("|", 79, 1,false, "BANK",repBean);
			ReportDAO.println("|", 0, 0,false, "BANK",repBean);
			ReportDAO.println(""+peno, 1, 0,false, "BANK",repBean);
			ReportDAO.println("[See Rule 31 (1)(a)]", 30, 0,false, "BANK",repBean);
			ReportDAO.println("|", 79, 1,false, "BANK",repBean);
			ReportDAO.println("|", 0, 0,false, "BANK",repBean);
			ReportDAO.println((char)14+(char)15 + "Certificate under section 203 of the Income Tax Act,1961 for Tax  " +(char)179, 2, 0,false, "BANK",repBean);
			ReportDAO.println("|", 79, 1,false, "BANK",repBean);
			ReportDAO.println("|", 0, 0,false, "BANK",repBean);
			ReportDAO.println((char)14+(char)15 + "deducted at Source from income chargeable under the Head 'Salaries'" +(char)179, 1, 1,false, "BANK",repBean);
			ReportDAO.println("|", 0, 0,false, "BANK",repBean);
			ReportDAO.println(UtilityDAO.stringOfSize(78, '-'), 1, 0,false, "BANK",repBean);
			ReportDAO.println("|", 79, 1,false, "BANK",repBean);
			ReportDAO.println("|", 0, 0,false, "BANK",repBean);
			ReportDAO.println("Name and address of the Employer :       | Name & Designation of the Employee", 2, 0,false, "BANK",repBean);
			ReportDAO.println("|", 79, 1,false, "BANK",repBean);
			ReportDAO.println("|", 0, 0,false, "BANK",repBean);
			ReportDAO.println("ORG NAME" + " | " + pename, 2, 0, false, "BANK",repBean);
			ReportDAO.println("|", 79, 1,false, "BANK",repBean);
			ReportDAO.println("|", 0, 0,false, "BANK",repBean);
			ReportDAO.println("ADDR1 CITY - " + " | " + dsigcd, 2, 0, false, "BANK",repBean);
			ReportDAO.println("|", 79, 1,false, "BANK",repBean);
			ReportDAO.println("|", 0, 0,false, "BANK",repBean);
			ReportDAO.println(UtilityDAO.stringOfSize(78, '-'), 1, 0,false, "BANK",repBean);
			ReportDAO.println("|", 79, 1,false, "BANK",repBean);
			ReportDAO.println("|", 0, 0,false, "BANK",repBean);
			ReportDAO.println("PAN/GIR NO.    | TAN                     | PAN/GIR NO.       ", 2, 0, false, "BANK",repBean);
			ReportDAO.println("|", 79, 1,false, "BANK",repBean);
			ReportDAO.println("|", 0, 0,false, "BANK",repBean);
			ReportDAO.println("  **********   |   **********            |" + PAN_NO, 2, 0, false, "BANK",repBean);
			ReportDAO.println("|", 79, 1,false, "BANK",repBean);
			ReportDAO.println("|", 0, 0,false, "BANK",repBean);
			ReportDAO.println(UtilityDAO.stringOfSize(78, '-'), 1, 0,false, "BANK",repBean);
			ReportDAO.println("|", 79, 1,false, "BANK",repBean);
			ReportDAO.println("|", 0, 0,false, "BANK",repBean);
			ReportDAO.println("Acknowledgement Nos of all quarterly stat |", 2, 0,false, "BANK",repBean);
			ReportDAO.println("|", 79, 1,false, "BANK",repBean);
			ReportDAO.println("| of TDS under subsection(3) of section 200 |" , 0, 0,false, "BANK",repBean);
			ReportDAO.println("|", 79, 1,false, "BANK",repBean);
			ReportDAO.println("| as provided by TIN Facilitation Centre or |" , 0, 0,false, "BANK",repBean);
			ReportDAO.println("|", 79, 1,false, "BANK",repBean);
			ReportDAO.println("| NSDL Web-site                             |", 0, 0,false, "BANK",repBean);
			ReportDAO.println("|", 79, 1,false, "BANK",repBean);
			ReportDAO.println("|", 0, 0,false, "BANK",repBean);
			ReportDAO.println(UtilityDAO.stringOfSize(78, '-'), 1, 0,false, "BANK",repBean);
			ReportDAO.println("|", 79, 1,false, "BANK",repBean);
			ReportDAO.println("     Quarter       Acknowledgement No.   |        Period       | Assessment ", 0, 0, false, "BANK",repBean);
			ReportDAO.println("|", 79, 1,false, "BANK",repBean);
			ReportDAO.println("|", 0, 0,false, "BANK",repBean);
			ReportDAO.println(Q1_CNO+  "        |    From  |   To     |    Year ", 2, 0, false, "BANK",repBean);
			ReportDAO.println("|", 79, 1,false, "BANK",repBean);
			ReportDAO.println("|", 0, 0,false, "BANK",repBean);
			
			str1 = Q2_CNO + "        | Apr " + (ReportDAO.getMonth(prdt) > 3 ? prdt.substring(7,11) : Integer.parseInt(prdt.substring(7,11))-1) +
					" | " + prdt.substring(3,5) + " " +
					(ReportDAO.getMonth(prdt) < 3 ? prdt.substring(7,11) : Integer.parseInt(prdt.substring(7,11)) + 1) + " | " +
					(ReportDAO.getMonth(prdt) < 3 ? prdt.substring(7,11) : Integer.parseInt(prdt.substring(7,11)) + 1) + " - " +
					(ReportDAO.getMonth(prdt) < 3 ? Integer.parseInt(prdt.substring(7,11)) + 1 : Integer.parseInt(prdt.substring(7,11)) + 2);
			ReportDAO.println(str1, 2, 0,false, "BANK",repBean);	
			ReportDAO.println("|", 79, 1,false, "BANK",repBean);
			ReportDAO.println("|", 0, 0,false, "BANK",repBean);
			ReportDAO.println(Q1_CNO+  "        |          |          |         ", 2, 0, false, "BANK",repBean);
			ReportDAO.println("|", 79, 1,false, "BANK",repBean);
			ReportDAO.println("|", 0, 0,false, "BANK",repBean);
			ReportDAO.println(Q4_CNO, 2, 0,false, "BANK",repBean);
			ReportDAO.println("|", 79, 1,false, "BANK",repBean);
			ReportDAO.println(""+(char)192, 0, 0,false, "BANK",repBean);
			ReportDAO.println(UtilityDAO.stringOfSize(78, '-'), 1, 0,false, "BANK",repBean);
			ReportDAO.println(""+(char)217, 79, 1,false, "BANK",repBean);
			ReportDAO.println((char)14 + (char)15 + "DETAILS OF SALARY PAID AND ANY OTHER INCOME AND TAX DEDUCTED.   ", 4, 1,false, "BANK",repBean);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void buildsql(int peno, String prdt, String itgrpcd,TaxReportBean taxRepBean ){
		
		/****ORACLE QUERY   *******/
		/*taxRepBean.setYtdstr(" select regular.trncd,c.disc ,net_amt,net_amt1,c.plusminus,c.itgrp,c.itgrp2,C.CHKSLB  from " +
	          " (SELECT Y.TRNCD," +
	          " sum(decode(y.trncd,108, (case  when net_Amt > 800 then net_amt - 800 else net_amt end),net_amt)) net_amt " +
	          " FROM YTDTRAN Y,CDMAST C " +
	          " Where Y.empno = "+peno +
	          " AND Y.TRNDT BETWEEN '" + ReportDAO.Boy(prdt)+ "'  AND '" + ReportDAO.EOM(prdt) +"' " + 
	          " AND Y.TRNCD = C.TRNCD AND ( C.ITGRP LIKE '" + itgrpcd + "' or C.ITGRP2 LIKE '" + itgrpcd + "' ) " +
	          " GROUP BY y.TRNCD ) regular , " +
	          " ( SELECT Y.TRNCD, sum(decode(y.trncd,108, (case  when net_Amt > 800 then net_amt - 800 else net_amt end),net_amt))* " + taxRepBean.getProjMonth() + " net_amt1 " +
	          " FROM YTDTRAN Y,CDMAST C " +
	          " Where Y.empno = " + peno +
	          " AND Y.TRNDT BETWEEN '" +ReportDAO.BOM(prdt)+ "'  AND '" +ReportDAO.EOM(prdt) +"' "+
	          " AND Y.TRNCD = C.TRNCD AND ( C.ITGRP LIKE '" + itgrpcd + "' or C.ITGRP2 LIKE '" + itgrpcd + "' ) " +
	          " and projyn = 'Y' " +
	          " GROUP BY y.TRNCD ) projection ,cdmast c " +
	          " where projection.trncd(+) = regular.trncd and " +
	          " C.trncd = regular.trncd  order by c.itgrp");*/
		/****Oracle query End **********/
		
		/******SQL QUERY START*****************/
		taxRepBean.setYtdstr(" SELECT regular.trncd, c.disc, net_amt, net_amt1, c.plusminus, c.itgrp, c.itgrp2, C.CHKSLB FROM " +
				"(	SELECT Y.TRNCD, " +
				" SUM(CONVERT(FLOAT, CONVERT(FLOAT, CASE y.trncd WHEN 108 THEN (case when net_Amt  > 800 then net_amt - 800	 else net_amt end)ELSE net_amt END))) * " + taxRepBean.getProjMonth() + " net_amt1" +
				" FROM  YTDTRAN Y, CDMAST C " +
				"WHERE Y.empno = "+peno +
				" AND Y.TRNDT BETWEEN '" +ReportDAO.BOM(prdt)+ "'  AND '" +ReportDAO.EOM(prdt) +"'" +
				" AND Y.TRNCD = C.TRNCD AND	(C.ITGRP  LIKE '" + itgrpcd + "' OR	C.ITGRP2  LIKE '" + itgrpcd + "')" +
				" AND projyn = 'Y' GROUP BY  y.TRNCD ) projection " +
				" RIGHT OUTER JOIN (SELECT  Y.TRNCD, SUM(CONVERT(FLOAT, CONVERT(FLOAT, CASE y.trncd WHEN 108 " +
				" THEN (case when net_Amt  > 800 then net_amt - 800	 else net_amt end) ELSE net_amt	END))) net_amt " +
				" FROM YTDTRAN Y, CDMAST C WHERE Y.empno = "+peno +" AND Y.TRNDT  BETWEEN '" + ReportDAO.Boy(prdt)+ "'  AND '" + ReportDAO.EOM(prdt) +"' " +
		        "  AND	Y.TRNCD  = C.TRNCD  AND	(C.ITGRP  LIKE '" + itgrpcd + "' OR	C.ITGRP2  LIKE '" + itgrpcd + "') GROUP BY " +
		        " y.TRNCD ) regular  ON  projection.trncd  = regular.trncd ," +
		        " cdmast c 	WHERE	 C.trncd  = regular.trncd	ORDER BY c.itgrp ");
		/********************SQL QUERY END *********************/

	}
	
	public static void printgrp(int ppeno, String pprdt,RepoartBean repBean,TaxReportBean taxRepBean){
		
	String pitgrp = "";
	String pitgrp2 ="";
	ResultSet slb = null;
	ResultSet ytd = null;
	String slbstr = "";
	int reftot = 0;
	int  ActlAmt = 0;
	int ProjAmt = 0;
	try{
	Statement st = repBean.getCn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	ytd = st.executeQuery(taxRepBean.getYtdstr());
	while(ytd.next()){
    pitgrp = ytd.getString("ITGRP")!=null?ytd.getString("ITGRP"):"";
	pitgrp2 = ytd.getString("ITGRP2")!=null?ytd.getString("ITGRP2"):"";
	taxRepBean.setTotamt(0);
	   while(ytd.getString("ITGRP").equals(pitgrp)){
		   ReportDAO.println(""+ytd.getInt("TRNCD"), taxRepBean.getC1(), 0, false, "BANK",repBean);
		   if(ytd.getString("PLUSMINUS").equalsIgnoreCase("M")){
			   ReportDAO.println("Less "+ytd.getString("DISC"), taxRepBean.getC2(), 0, false, "BANK",repBean);
			   
		   }else{
			   ReportDAO.println(ytd.getString("DISC"), taxRepBean.getC2(), 0, false, "BANK",repBean);
		   }
		   if(ytd.getInt("TRNCD") == 202){
			   if((ytd.getString("NET_AMT")!=null?ytd.getInt("NET_AMT"):0)+(ytd.getString("NET_AMT1")!=null?ytd.getInt("NET_AMT1"):0) == 2400){
			      ActlAmt = (ytd.getString("NET_AMT")!=null?ytd.getInt("NET_AMT"):0);
			      ProjAmt = (ytd.getString("NET_AMT1")!=null?ytd.getInt("NET_AMT1"):0)+ 100;
			      ReportDAO.println(UtilityDAO.trans(ActlAmt, "99,99,999.99", "", false, false), taxRepBean.getC3(), 0, false, "BANK",repBean);
			      ReportDAO.println(UtilityDAO.trans(ProjAmt, "99,99,999.99", "", false, false), taxRepBean.getC4(), 0, false, "BANK",repBean);
			      taxRepBean.setTotamt(taxRepBean.getTotamt() + ActlAmt + ProjAmt);
			      if(ytd.getString("PLUSMINUS").equalsIgnoreCase("M")){
			    	  taxRepBean.setC3tot(taxRepBean.getC3tot() - ActlAmt);
			    	  taxRepBean.setC4tot(taxRepBean.getC4tot() - ProjAmt);
			      }else{
			    	  taxRepBean.setC3tot(taxRepBean.getC3tot() + ActlAmt);
			    	  taxRepBean.setC4tot(taxRepBean.getC4tot() + ProjAmt);
			      }
			      
			   }else{
				   ReportDAO.println(UtilityDAO.trans(ytd.getString("NET_AMT")!=null?ytd.getInt("NET_AMT"):0, "99,99,999.99", "", false, false), taxRepBean.getC3(), 0, false, "BANK",repBean);
				   ReportDAO.println(UtilityDAO.trans(ytd.getString("NET_AMT1")!=null?ytd.getInt("NET_AMT1"):0, "99,99,999.99", "", false, false), taxRepBean.getC4(), 0, false, "BANK",repBean);
				   taxRepBean.setTotamt(taxRepBean.getTotamt() + (ytd.getString("NET_AMT")!=null?ytd.getInt("NET_AMT"):0)+(ytd.getString("NET_AMT1")!=null?ytd.getInt("NET_AMT1"):0));
				   if(ytd.getString("PLUSMINUS").equalsIgnoreCase("M")){
					   taxRepBean.setC3tot(taxRepBean.getC3tot() - (ytd.getString("NET_AMT")!=null?ytd.getInt("NET_AMT"):0));
					   taxRepBean.setC4tot(taxRepBean.getC4tot() - (ytd.getString("NET_AMT1")!=null?ytd.getInt("NET_AMT1"):0));
				      }else{
				    	  taxRepBean.setC3tot(taxRepBean.getC3tot() + (ytd.getString("NET_AMT")!=null?ytd.getInt("NET_AMT"):0));
				    	  taxRepBean.setC4tot(taxRepBean.getC4tot() + (ytd.getString("NET_AMT1")!=null?ytd.getInt("NET_AMT1"):0));
				      }
			   }
			   Statement st1=repBean.getCn().createStatement();
			   st1.executeUpdate("UPDATE F16 SET PT = "+ytd.getInt("NET_AMT")+" WHERE EMPNO = "+ppeno);
			   st1.close();
		   }else{
			   ReportDAO.println(UtilityDAO.trans(ytd.getString("NET_AMT")!=null?ytd.getInt("NET_AMT"):0, "99,99,999.99", "", false, false), taxRepBean.getC3(), 0, false, "BANK",repBean);
			   ReportDAO.println(UtilityDAO.trans(ytd.getString("NET_AMT1")!=null?ytd.getInt("NET_AMT1"):0, "99,99,999.99", "", false, false), taxRepBean.getC4(), 0, false, "BANK",repBean);
			   if(ytd.getString("PLUSMINUS").equalsIgnoreCase("M")){
				   taxRepBean.setC3tot(taxRepBean.getC3tot() - (ytd.getString("NET_AMT")!=null?ytd.getInt("NET_AMT"):0));
				   taxRepBean.setC4tot(taxRepBean.getC4tot() - (ytd.getString("NET_AMT1")!=null?ytd.getInt("NET_AMT1"):0));
				   taxRepBean.setTotamt(taxRepBean.getTotamt() - (ytd.getString("NET_AMT")!=null?ytd.getInt("NET_AMT"):0)-(ytd.getString("NET_AMT1")!=null?ytd.getInt("NET_AMT1"):0));
			      }else{
			    	  taxRepBean.setC3tot(taxRepBean.getC3tot() + (ytd.getString("NET_AMT")!=null?ytd.getInt("NET_AMT"):0));
			    	  taxRepBean.setC4tot(taxRepBean.getC4tot() + (ytd.getString("NET_AMT1")!=null?ytd.getInt("NET_AMT1"):0));
			    	  taxRepBean.setTotamt(taxRepBean.getTotamt() + (ytd.getString("NET_AMT")!=null?ytd.getInt("NET_AMT"):0)+(ytd.getString("NET_AMT1")!=null?ytd.getInt("NET_AMT1"):0));
			      }
		     }
		    
			 if(ytd.getString("ITGRP").equalsIgnoreCase(pitgrp)){
				   ReportDAO.println("", taxRepBean.getC5(), 1, false, "BANK",repBean);
			  }
			 if(!ytd.next()){
		    	 break;
		     }
		   }
	      ytd.previous();
	      if(ytd.getInt("TRNCD") == 811){
	    	  taxRepBean.setTotamt(calhlint(taxRepBean.getTotamt(), ppeno,repBean,taxRepBean));
	      }else{
	    	  if((ytd.getString("CHKSLB")==null?"N":ytd.getString("CHKSLB")).equalsIgnoreCase("Y")){
	    		  slbstr = "SELECT * FROM SLAB WHERE TRNCD = "+ytd.getInt("TRNCD");
	    		  Statement st1=repBean.getCn().createStatement();
	    		  slb = st1.executeQuery(slbstr);
	    		  if(slb.next()){
	    			  reftot = taxRepBean.getTotamt();
	    			  taxRepBean.setTotamt((int)Calculate.checkSlab(ytd.getInt("TRNCD"), pprdt,	reftot, 1,ppeno,repBean.getCn()));
	    		  }
	    		  st1.close();
	    	  }
	      }
	      taxRepBean.setC5tot(taxRepBean.getC5tot() + taxRepBean.getTotamt());
	      ReportDAO.println(UtilityDAO.trans(taxRepBean.getTotamt(), "99,99,999.99", "", false, false), taxRepBean.getC5(), 1, false, "BANK",repBean); 
	      
	   }
	   st.close();
		ReportDAO.println(taxRepBean.getHdg_13(), taxRepBean.getC1(), 1, false, "BANK",repBean);
		if(taxRepBean.getGnm().equalsIgnoreCase("A1")){
			ReportDAO.println("     GROSS SALARY INCOME ", taxRepBean.getC1() + 5, 0, false, "BANK",repBean);
		}
		 ReportDAO.println(UtilityDAO.trans(taxRepBean.getC3tot(), "99,99,999.99", "", false, false), taxRepBean.getC3(), 0, false, "BANK",repBean);
		 ReportDAO.println(UtilityDAO.trans(taxRepBean.getC4tot(), "99,99,999.99", "", false, false), taxRepBean.getC4(), 0, false, "BANK",repBean);
		 ReportDAO.println(UtilityDAO.trans(taxRepBean.getC5tot(), "99,99,999.99", "", false, false), taxRepBean.getC5(), 1, false, "BANK",repBean);
		 ReportDAO.println(taxRepBean.getDline(), taxRepBean.getC1(), 1, false, "BANK",repBean);
		 
	
	}catch(Exception e){
		e.printStackTrace();
	}
	}
	
	public static void Incometax_Report(int feno,int teno,String rdt,String f16orwsheet,String filepath,boolean opTaxEmp,boolean optAllEmp){
		TaxReportBean taxRepBean = new TaxReportBean();
		RepoartBean repBean  = new RepoartBean();
		FileWriter Fp;
		SimpleDateFormat frmFormat = new SimpleDateFormat("MM/DD/yyyy");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

		
		String owner = "";
		String MNEXT = "";
		String hdg_g = "";
		String dash_13  = "";
		String empsql = "";
		String CmDSQL = "";
		ResultSet CDM = null;
		ResultSet DTR = null;
		ResultSet emp = null;
		
		String ename = "";
		String str1 = "";
		String PAN_NO = "";
		String dtrsql = "";
		
		taxRepBean.setMaxhlint1(100000);//maximum housing loan interest repayment loan taken before 01/4/1999
		taxRepBean.setMaxhlint2(150000);//maximum housing loan interest repayment loan taken after 01/4/1999
		
		ReportDAO.inithead(repBean);
		
        repBean.setHdg1(String.valueOf((char)14) + String.valueOf((char)15) + "INCOME TAX WORKSHEET AS ON " +rdt+ String.valueOf((char)18));
				//'min_pay = 50000
				//'min_pay = 100000

				//'min_pay = 150000 'tmp. change for printing of f16 of some emp.
        taxRepBean.setMin_pay(50000);
        taxRepBean.setHdg_11("                                ...Actual Amt     Projected Amt  ....Total Amt  ");
        taxRepBean.setHdg_12("                                    (Rs.)             (Rs.)           (Rs.)     ");
        taxRepBean.setHdg_13("                               ----------------- --------------- ---------------");
        taxRepBean.setDline(UtilityDAO.stringOfSize(80, '-'));
        taxRepBean.setC1(0);
        taxRepBean.setC2(4);
		taxRepBean.setC3(35);
		taxRepBean.setC4(51);
		taxRepBean.setC4(67);
		MNEXT = "P";
		ReportDAO.inithead(repBean);
		repBean.setLnCount(0);
		repBean.setPageNo(1);
		repBean.setLineLen(80);
		repBean.setPageLen(65);
		repBean.setFoot_Fl(true);
		repBean.setBrnNo(9999);
		
		
		ReportDAO.OpenCon("", "", "",repBean);
		
				
				
				if(ReportDAO.getMonth(rdt) > 3){
					taxRepBean.setProjMonth(12 - (ReportDAO.getMonth(rdt)-3));
				}else{
					taxRepBean.setProjMonth(12 - Math.abs(ReportDAO.getMonth(rdt)-3));
				}

				if(feno != teno){
					empsql = "select emp.*, t.acno, t.branch, t.grade ,emp.panno from empmast emp, emptran t where t.empno = emp.empno  AND " +
				            " t.effdate = ( SELECT MAX(E2.EFFDATE) FROM EMPTRAN E2 WHERE e2.empno = emp.empno and E2.EFFDATE <= '"+rdt+"')" +
				             " and emp.status = 'A' and emp.empno between  " + feno + " and " + teno +" order by emp.empno ";
				}else{
				    empsql = "select emp.*, t.acno, t.branch, t.grade ,emp.panno from empmast emp, emptran t where t.empno = emp.empno  AND " +
				             " t.effdate = ( SELECT MAX(E2.EFFDATE) FROM EMPTRAN E2 WHERE e2.empno = emp.empno and E2.EFFDATE <= '" +rdt+ "')" +
				             " and emp.empno between  " + feno + " and " + teno + " order by emp.empno ";
				}
				
				
					
				try {
					Statement st = repBean.getCn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					emp = st.executeQuery(empsql);
					if(!emp.next()){
						System.out.println("Employee RecordSet is empty");
						return;
					}
					
					if(!f16orwsheet.equalsIgnoreCase("F16")){
						Statement st1 = repBean.getCn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
						 st1.executeUpdate("DELETE FROM F16");
	     				 ReportDAO.make_prn_file(filepath,repBean);
		      		   ReportDAO.page_head(repBean.getBrnNo(), repBean.getHdg1(), "", "", "", "", "", "",repBean);
					}else{
						 ReportDAO.make_prn_file(filepath,repBean);
					}
				  
				    emp.beforeFirst();
				    while(emp.next()){
				    	/*str1 = "select sum(net_amt) + sum(net_amt1) from " +
				    	          " (SELECT Y.TRNCD,SUM(NET_AMT) net_amt  " +
				    	          " FROM YTDTRAN Y, CDMAST C " +
				    	          " Where Y.empno = " + emp.getInt("EmpNO") +" and " +
				    	          " Y.TRNDT BETWEEN '" +ReportDAO.Boy(rdt)+ "'  AND  '" +ReportDAO.EOM(rdt)+ "' and " +
				    	          " Y.TRNCD = C.TRNCD AND C.ITGRP LIKE 'A1%' " +
				    	          " GROUP BY y.TRNCD) regular, " +
				    	          "(SELECT Y.TRNCD,SUM(NET_AMT) * " + taxRepBean.getProjMonth() + " net_amt1 " +
				    	          " FROM YTDTRAN Y,CDMAST C " +
				    	          " Where Y.empno = " + emp.getInt("EmpNO") + " and " +
				    	          " Y.TRNDT BETWEEN '" +ReportDAO.BOM(rdt)+ "'  AND  '" +ReportDAO.EOM(rdt)+ "' and " +
				    	          " Y.TRNCD = C.TRNCD AND C.ITGRP LIKE 'A1%' and " +
				    	          " c.projyn = 'Y' " +
				    	          " GROUP BY y.TRNCD ) projection " +
				    	          " where projection.trncd(+) = regular.trncd ";*/
				    	
				    	
				    	str1 = "SELECT SUM(CONVERT(FLOAT, net_amt)) + SUM(CONVERT(FLOAT, net_amt1))FROM (SELECT Y.TRNCD, " +
				    			" SUM(CONVERT(FLOAT, CONVERT(FLOAT, NET_AMT))) * " + taxRepBean.getProjMonth() + " net_amt1 FROM  YTDTRAN Y, CDMAST C WHERE Y.empno = " + emp.getInt("EmpNO") + "" +
				    			" AND	Y.TRNDT  BETWEEN '" +ReportDAO.BOM(rdt)+ "'  AND  '" +ReportDAO.EOM(rdt)+ "' AND Y.TRNCD  = C.TRNCD AND C.ITGRP  LIKE 'A1%'" +
				    			" AND	c.projyn  = 'Y'	GROUP BY  y.TRNCD) projection  RIGHT OUTER JOIN (SELECT" +
				    			" Y.TRNCD, SUM(CONVERT(FLOAT, CONVERT(FLOAT, NET_AMT))) net_amt	FROM  YTDTRAN Y, CDMAST C" +
				    			" WHERE	 Y.empno  = " + emp.getInt("EmpNO") +" AND Y.TRNDT  BETWEEN '" +ReportDAO.Boy(rdt)+ "'  AND  '" +ReportDAO.EOM(rdt)+ "' AND Y.TRNCD  = C.TRNCD	" +
				    			" AND C.ITGRP  LIKE 'A1%' GROUP BY  y.TRNCD) regular  ON  projection.trncd  = regular.trncd";  
				    		
				    		
				    		


				    	

				    	
				    	System.out.println(str1);
				    	Statement st1 = repBean.getCn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				    	DTR = st1.executeQuery(str1);
				    	if(!DTR.next()){
				    		MNEXT = "P";
				    	}else{
				    		if(opTaxEmp){
				    			//And (emp!EmpNo <> 2019 And rdt <> "31-mar-2010") is added only for fy 2009-2010 to be remove afterwards
				    			//'If DTR.Fields(0) < min_pay And (emp!EmpNo <> 2019 And rdt <> "31-mar-2010") Then
				    			if(DTR.getInt(1) < taxRepBean.getMin_pay()){
				    				MNEXT = "P";
				    				continue;
				    			}else{
				    				MNEXT = "Q";
				    				ename = emp.getString("LNAME") + " " + emp.getString("FNAME") + " " +emp.getString("MNAME");
				    				if(!f16orwsheet.equalsIgnoreCase("F16")){
				    					Statement st2 = repBean.getCn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				    					
				    					st2.executeUpdate("INSERT INTO F16(EMPNO,NAME,PANNO,DOB,SEX) VALUES ("+emp.getInt("EMPNO")+",'"+ename+"','"+emp.getInt("PANNO")+"','"+dateFormat.format(emp.getDate("DOB"))+"','"+emp.getString("GENDER")+"')");
				    					PrintTaxSheet(emp.getInt("empno"),rdt,ename,emp.getString("GENDER"),repBean,taxRepBean);
				    					st2.close();
				    				}else{
				    					if(emp.getInt("DA_SCHEME") > 0 || emp.getInt("EMPNO") == 2014 || emp.getInt("EMPNO") == 4039){
				    						repBean.setPageLen(68);
				    						taxRepBean.setGENO(emp.getInt("EMPNO"));
				    						PAN_NO = emp.getString("PANNO")==null?"":emp.getString("PANNO");
				    						printform16(emp.getInt("EMPNO"), rdt, ename, emp.getInt("GRADE"), PAN_NO, emp.getString("GENDER"),emp.getDate("DOB"),  repBean,taxRepBean);
				    						
				    					}
				    				}
				    			}
				    		}else if(optAllEmp){
				    			MNEXT = "Q";
				    			ename = emp.getString("LNAME") + " " + emp.getString("FNAME") + " " +emp.getString("MNAME");
			    				if(!f16orwsheet.equalsIgnoreCase("F16")){
			    					Statement st2 = repBean.getCn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			    					st2.executeUpdate("INSERT INTO F16(EMPNO,NAME,PANNO,DOB,SEX) VALUES ('"+emp.getInt("EMPNO")+"','"+ename+"','"+emp.getInt("PANNO")+"','"+emp.getString("DOB")+"','"+emp.getString("GENDER")+"')");
			    					PrintTaxSheet(emp.getInt("empno"),rdt,ename,emp.getString("GENDER"),repBean,taxRepBean);
			    					
			    				}else{
			    					if(emp.getInt("DA_SCHEME") > 0 || emp.getInt("EMPNO") == 2014 || emp.getInt("EMPNO") == 4039){
			    						repBean.setPageLen(68);
			    						taxRepBean.setGENO(emp.getInt("EMPNO"));
			    						PAN_NO = emp.getString("PANNO")==null?"":emp.getString("PANNO");
			    						printform16(emp.getInt("EMPNO"), rdt, ename, emp.getInt("GRADE"), PAN_NO, emp.getString("GENDER"),emp.getDate("DOB"), repBean,taxRepBean);
			    					}
			    				}
				    		}
				    	}
				    	if(!emp.next() && !f16orwsheet.equalsIgnoreCase("F16") && !MNEXT.equalsIgnoreCase("P")){
				    		repBean.setLnCount(68);
				    		ReportDAO.println(" ", 0, 1, false, "BANK",repBean);
				    	}else if(!f16orwsheet.equalsIgnoreCase("F16")){
				    		ReportDAO.println(String.valueOf((char)15), 0, 1, false, "BANK",repBean);
				    	}
				    	
				    }
				    Fp = repBean.getFp();
					Fp.close();
					repBean.getCn().close();
					return;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
	}
	
	public static void PrintTaxSheet(int peno,String prdt,String pename,String sex,RepoartBean repBean,TaxReportBean taxRepBean){
			
		
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
		 
		 
		
		 ReportDAO.println("Financial  Year "+ReportDAO.Boy(prdt).substring(7,11)+" - "+ReportDAO.Eoy(ReportDAO.Boy(prdt)).substring(7,11)+" Assessment Year "+ReportDAO.Boy(ReportDAO.Eoy(prdt) + 10) +" - "+ReportDAO.Eoy(ReportDAO.Eoy(prdt) + 10), taxRepBean.getC1(),1,false,"BANK",repBean);
		 ReportDAO.println("Employee Code : " +peno + " Name :" + pename, taxRepBean.getC1(), 1, false, "BANK",repBean);   
		 ReportDAO.println(taxRepBean.getDline(), taxRepBean.getC1(), 1, false, "BANK",repBean);
		 ReportDAO.println( "(I) INCOME FROM SALARY  :- ", taxRepBean.getC1(), 1, false, "BANK",repBean);
		 ReportDAO.println(taxRepBean.getHdg_11(), taxRepBean.getC1(), 1, false, "BANK",repBean);
		 ReportDAO.println(taxRepBean.getHdg_12(), taxRepBean.getC1(), 1, false, "BANK",repBean);
		 ReportDAO.println(taxRepBean.getDline(), taxRepBean.getC1(), 1, false, "BANK",repBean);
		  
		 taxRepBean.setC4tot(0);
		 taxRepBean.setC5tot(0);
		 taxRepBean.setC3tot(0);
		 taxRepBean.setTotamt(0);
		 taxRepBean.setGnm("A1");
		    buildsql(peno, prdt, "A1%",taxRepBean); 
		    printgrp( peno, prdt,repBean,taxRepBean);
		    //' Total Income From all
		    tax_amtiii = 0;
		    taxRepBean.setGnm("");
		    totinc =  taxRepBean.getC5tot();
		    tax_amtiii = totinc;
		    ReportDAO.println("        Less : Deductions U/S 16 :", taxRepBean.getC1(), 1, false, "BANK",repBean);
		    ReportDAO.println(taxRepBean.getDline(), taxRepBean.getC1(), 1, false, "BANK",repBean);
		    buildsql(peno, prdt, "A5%",taxRepBean);
		    taxRepBean.setC5tot((int)Calculate.checkSlab(500, prdt, taxRepBean.getC5tot(), 1, peno,repBean.getCn()));
		    if(taxRepBean.getC5tot() > 0){
		    	ReportDAO.println("500 STANDARD DEDUCTION", taxRepBean.getC1(), 0, false, "BANK",repBean);
		    	ReportDAO.println(UtilityDAO.trans(taxRepBean.getC5(), "99,99,999.99", "", false, false), taxRepBean.getC5(), 1, false, "BANK",repBean);
		    	taxRepBean.setC4tot(0);
				taxRepBean.setC3tot(0);
				taxRepBean.setTotamt(0);
		    }
		    printgrp(peno,prdt,repBean,taxRepBean);
		    try {
				Statement st = repBean.getCn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				int cnt = st.executeUpdate("UPDATE F16 SET GROSS_INC = "+totinc+" WHERE EMPNO = "+peno);
				//Total Deduction from alll
				totded = taxRepBean.getC5tot();
			    ReportDAO.println("       Net Salary Income  :-       ", taxRepBean.getC1(), 0,false,"BANK",repBean);
			    ReportDAO.println(UtilityDAO.trans(totinc-totded, "999,99,999.99", "", false, false), taxRepBean.getC5(), 1, false, "BANK",repBean);
			    ReportDAO.println(taxRepBean.getDline(), taxRepBean.getC1(), 1, false, "BANK",repBean);
			   // '-------------------------------------------
			    //'println "Less :", c1, Fp, 1
			    taxRepBean.setC4tot(0);
				 taxRepBean.setC5tot(0);
				 taxRepBean.setC3tot(0);
				 taxRepBean.setTotamt(0);
			    buildsql( peno, prdt, "A2%",taxRepBean);
			    printgrp(peno, prdt,repBean,taxRepBean);
			    tot499 = taxRepBean.getC5tot();
			    //'------------------------------------------------
			    ReportDAO.println(taxRepBean.getDline(), taxRepBean.getC1(), 1, false, "BANK",repBean);
			    ReportDAO.println(" ( II )ANY OTHER INCOME REPORTED BY THE EMPLOYEE :", taxRepBean.getC1(), 1, false, "BANK",repBean);
			    ReportDAO.println(taxRepBean.getDline(), taxRepBean.getC1(), 1, false, "BANK",repBean);
			    taxRepBean.setC4tot(0);
				 taxRepBean.setC5tot(0);
				 taxRepBean.setC3tot(0);
			    buildsql(peno, prdt, "AA%",taxRepBean);
			    printgrp(peno, prdt,repBean,taxRepBean);
			    OTHER_DED = taxRepBean.getC5tot();
			    totamt = (totinc - totded + OTHER_DED + tot499);
			    ReportDAO.println(" ( III ) AGGREGATE GROSS TAXABLE INCOME         :", taxRepBean.getC1(), 0, false, "BANK",repBean);
			    ReportDAO.println(UtilityDAO.trans(totamt, "999,99,999.99", "", false, false), taxRepBean.getC5(), 1, false, "BANK",repBean);
			    ReportDAO.println( "            (i.e. (I + II  )            ", taxRepBean.getC1(), 1, false, "BANK",repBean);
			    ReportDAO.println(taxRepBean.getDline(), taxRepBean.getC1(), 1, false, "BANK",repBean);
			    ReportDAO.println(" ( IV ) DEDUCTIONS UNDER CHAPTER  VI-A  :", taxRepBean.getC1(), 1, false, "BANK",repBean);
			    taxRepBean.setC4tot(0);
				 taxRepBean.setC5tot(0);
				 taxRepBean.setC3tot(0);
			    ReportDAO.println("     a) DEDUCTIONS U/S 80 C     ", taxRepBean.getC1(), 1, false, "BANK",repBean);
			    			    
			    buildsql(peno, prdt, "AC%",taxRepBean);
			    printgrp(peno, prdt,repBean,taxRepBean);
				
			    st.executeUpdate("UPDATE F16 SET TOT_DED = "+taxRepBean.getC5tot()+" WHERE EMPNO = "+ peno);
			    if( taxRepBean.getC5tot() > 100000 )
			    {
			    	taxRepBean.setC5tot(100000);
			    }
			    ReportDAO.println("       Qualifying Deduction u/s 80 C ", taxRepBean.getC1(), 0, false, "BANK",repBean);
			    ReportDAO.println(UtilityDAO.trans(taxRepBean.getC5tot(), "999,99,999.99", "", false, false), taxRepBean.getC5(), 1, false, "BANK",repBean);
			    ReportDAO.println(taxRepBean.getDline(), taxRepBean.getC1(), 1, false, "BANK",repBean);
			    ReportDAO.println("     b) Infrastructure Investment U/s 80CCF", taxRepBean.getC1(), 1, false, "BANK",repBean);
			    
			    buildsql(peno, prdt, "AK%",taxRepBean);
			    printgrp(peno, prdt,repBean,taxRepBean);
			   
			    ReportDAO.println("     c) Medical Insurance U/S 80 D     ", taxRepBean.getC1(), 1, false, "BANK",repBean);
			    
			    buildsql(peno, prdt, "AD%",taxRepBean);
			    printgrp(peno, prdt,repBean,taxRepBean);
			    
			    ReportDAO.println("     d) DONATION U/S 80 G (CM Relief fund)", taxRepBean.getC1(), 1, false, "BANK",repBean);
			    
			    buildsql(peno, prdt, "AE1%",taxRepBean);
			    printgrp(peno, prdt,repBean,taxRepBean);
			    
			    ReportDAO.println("     e) DONATION U/S 80 G (Others)", taxRepBean.getC1(),1, false, "BANK",repBean);
			    
			    buildsql(peno, prdt, "AEA%",taxRepBean);
			    printgrp(peno, prdt,repBean,taxRepBean);
			   
			    ReportDAO.println("     f) Income of Handicapped U/S 80 U", taxRepBean.getC1(),1, false, "BANK",repBean);
			   
			    buildsql(peno, prdt, "AF1%",taxRepBean);
			    printgrp(peno, prdt,repBean,taxRepBean);
			    
			    DED80 = taxRepBean.getC5tot();
			    ReportDAO.println("  Qualifying Deduction under chapter VI-A ", taxRepBean.getC1(),0, false, "BANK",repBean);
			    ReportDAO.println(UtilityDAO.trans(taxRepBean.getC5tot(), "999,99,999.99", "", false, false), taxRepBean.getC5(), 1, false, "BANK",repBean);
			    ReportDAO.println(taxRepBean.getDline(), taxRepBean.getC1(), 1, false, "BANK",repBean);
			    ReportDAO.println(" ( V ) NET TAXABLE INCOME <a>before W.O.(i.e. III - IV ) :- ", taxRepBean.getC1(),0, false, "BANK",repBean);
			    
	    	    totamt = totamt - DED80;
	    	    ReportDAO.println(UtilityDAO.trans(totamt, "999,99,999.99", "", false, false), taxRepBean.getC5(), 1, false, "BANK",repBean);
	    	    ReportDAO.println("  (Rounded off to nearest Rs.10) <b>after W.O.           :- ", taxRepBean.getC1(),0, false, "BANK",repBean);
	    	    ReportDAO.println(UtilityDAO.trans(Math.round(totamt), "999,99,999.99", "", false, false), taxRepBean.getC5(), 1, false, "BANK",repBean);
	    	    ReportDAO.println(taxRepBean.getDline(), taxRepBean.getC1(), 1, false, "BANK",repBean);
	    	  
	    	    gross_tax_amt = totamt;
	    	    if( sex.equalsIgnoreCase("F"))
	    	    {
	    	    	totamt = Math.round(Chk_Slab1(703, prdt, totamt, 2,repBean));
	    	    }
	    	    else
	    	    {
	    	    	totamt = Math.round(Chk_Slab1(703, prdt, totamt, 1,repBean));
	    	    }
	    	    ReportDAO.println("  ( VI ) INCOME TAX PAYABLE ON [ (V)(b) ] ABOVE          :- ", taxRepBean.getC1(),0, false, "BANK",repBean);   	   
	    	    ReportDAO.println(UtilityDAO.trans(totamt, "999,99,999.99", "", false, false), taxRepBean.getC5(), 1, false, "BANK",repBean);
	    	    taxRepBean.setTax_cal(totamt);
	    	    ReportDAO.println(taxRepBean.getDline(), taxRepBean.getC1(), 1, false, "BANK",repBean);
	    	    totamt = Math.round(Calculate.checkSlab(498, prdt, gross_tax_amt, 1, peno,repBean.getCn()));
	    	    if( totamt == 100 )
	    	    {
	    	        totamt = taxRepBean.getTax_cal();
	    	    }
	    	    if( totamt == 2240)
	    	    {
	    	    	totamt = taxRepBean.getTax_cal() - (gross_tax_amt - 100000);
	    	    }
	    	    
	    	    taxRepBean.setC4tot(0);
				 taxRepBean.setC5tot(0);
				 taxRepBean.setC3tot(0);
	    	    buildsql(peno, prdt, "AJ%",taxRepBean);
	    	    printgrp(peno, prdt,repBean,taxRepBean);
	    	    
	    	    taxRepBean.setNet_tax(taxRepBean.getTax_cal() - reb88);
	    	    if(taxRepBean.getNet_tax() < 0 )
	    	    {
	    	    	taxRepBean.setNet_tax(0);
	    	    }
	    	    ReportDAO.println("  ( X ) NET TAX PAYABLE(i.e. VI - IX  )  ", taxRepBean.getC1(),0, false, "BANK",repBean);   	   
	    	    ReportDAO.println(UtilityDAO.trans(taxRepBean.getNet_tax(), "999,99,999.99", "", false, false), taxRepBean.getC5(), 1, false, "BANK",repBean);
	    	   
	    	 
	    	    st.executeUpdate("UPDATE F16 SET TOT_TAX = "+taxRepBean.getNet_tax()+" WHERE EMPNO = "+peno);
	    	    //'---------------------------------------------------------
	    	    ReportDAO.println("          Add Education Cess@3%  ", taxRepBean.getC1(),0, false, "BANK",repBean); 
	    	    taxRepBean.setSercharge(0);
	    	    if( taxRepBean.getNet_tax() > 0)
	    	    {
	    	    	 taxRepBean.setSercharge(Math.round(Calculate.checkSlab(901, prdt, taxRepBean.getNet_tax(), 1, peno,repBean.getCn())));
	    	    }
	    	    ReportDAO.println(UtilityDAO.trans(taxRepBean.getSercharge(), "999,99,999.99", "", false, false), taxRepBean.getC5(), 1, false, "BANK",repBean);
	    	    st.executeUpdate("UPDATE F16 SET ECESS = "+taxRepBean.getSercharge()+" WHERE EMPNO = "+peno);
	    	    ReportDAO.println(taxRepBean.getDline(), taxRepBean.getC1(), 1, false, "BANK",repBean);
	    	    if( taxRepBean.getSercharge() > 0)
	    	    {   
	    	    	ReportDAO.println("              Total    ", taxRepBean.getC1(),0, false, "BANK",repBean); 
	    	    	ReportDAO.println(UtilityDAO.trans(taxRepBean.getNet_tax() + taxRepBean.getSercharge(), "999,99,999.99", "", false, false), taxRepBean.getC5(), 2, false, "BANK",repBean);
	    	    }
    	      
	    	    taxRepBean.setC4tot(0);
				 taxRepBean.setC5tot(0);
				 taxRepBean.setC3tot(0);
	    	    ReportDAO.println("  ( XI ) TDS FROM SALARY / TAX PAID ", taxRepBean.getC1(),2, false, "BANK",repBean); 
	    	    
	    	    buildsql(peno, prdt, "BB%",taxRepBean);
	    	    printgrp(peno, prdt,repBean,taxRepBean);
	    	    taxRepBean.setTAXPAID(taxRepBean.getC5tot());
	    	    
	    	    st.executeUpdate("UPDATE F16 SET tds = "+taxRepBean.getTAXPAID()+" WHERE EMPNO = "+peno);
	    	    
	    	    ReportDAO.println(taxRepBean.getDline(), taxRepBean.getC1(), 1, false, "BANK",repBean);
	    	    ReportDAO.println(" ( XII ) BALANCE TAX PAYABLE /REFUNDABLE ( X - XI ) :", taxRepBean.getC1(),0, false, "BANK",repBean);
	    	    taxRepBean.setTAXPAID(taxRepBean.getNet_tax() - taxRepBean.getTAXPAID() + taxRepBean.getTAXPAID());
	    	    ReportDAO.println(UtilityDAO.trans(taxRepBean.getNet_tax(), "999,99,999.99", "", false, false), taxRepBean.getC5(), 1, false, "BANK",repBean);
	    	    ReportDAO.println(taxRepBean.getDline(), taxRepBean.getC1(), 1, false, "BANK",repBean);
	    	    
	    	    st.executeUpdate("UPDATE F16 SET bal_tax = "+taxRepBean.getNet_tax()+" WHERE EMPNO = "+peno);
	    	    ReportDAO.println("* * *  N O T E S * * *", taxRepBean.getC1(), 1, false, "BANK",repBean);
	    	    ReportDAO.println("1) Please send details of investment made or projected along with Xerox ", taxRepBean.getC1(), 1, false, "BANK",repBean);
	    	    ReportDAO.println("   copies of the same for the period "+ReportDAO.Boy(prdt)+" to "+ReportDAO.Eoy(prdt)+" .", taxRepBean.getC1(), 1, false, "BANK",repBean);
	    	    
	    	    if( taxRepBean.getNet_tax() > 0 )
	    	    {
	    	    	ReportDAO.println("   Otherwise Tax will be deducted from your salary as under :", taxRepBean.getC1(), 1, false, "BANK",repBean);
	    	    	ReportDAO.println("   Total Tax Payable Rs.   No of Months   Tax to be deducted per Month (Rs.)", taxRepBean.getC1(), 1, false, "BANK",repBean);    	   
	    	    	ReportDAO.println(UtilityDAO.trans(taxRepBean.getNet_tax(), "999,99,999.99", "", false, false), 7, 0, false, "BANK",repBean);
	    	    	ReportDAO.println(UtilityDAO.trans(taxRepBean.getProjMonth(), "999,99,999.99", "", false, false), 32, 0, false, "BANK",repBean);
    	           
    	            if( taxRepBean.getProjMonth() > 0 )
    	            {
    	            	ReportDAO.println(UtilityDAO.trans((taxRepBean.getNet_tax() /  taxRepBean.getProjMonth()), "999,99,999.99", "", false, false), 55, 1, false, "BANK",repBean);
    	            }
	    	    }
	    	    ReportDAO.println(String.valueOf((char)14) + String.valueOf((char)15), taxRepBean.getC1(), 1, false, "BANK",repBean);
	    	    ReportDAO.println("IF YOU HAVE ALREADY SENT DETAILS OF INVESTMENT, PLEASE IGNORE THIS LETTER.", taxRepBean.getC1(), 1, false, "BANK",repBean);
	    	    ReportDAO.println(String.valueOf((char)18), taxRepBean.getC1(), 1, false, "BANK",repBean);
	    	    ReportDAO.println("2) There may be increase in amount of tax payable due to DA arrears, ", taxRepBean.getC1(), 1, false, "BANK",repBean);
	    	    ReportDAO.println("    Leave Encashment, Leave surrender,Closing allowance etc.", taxRepBean.getC1(), 1, false, "BANK",repBean);
	    	    ReportDAO.println("    THE FIG. OF HOUSING INT.IS ON ACCRUED BASIS & IT WORKSHEET IS TENTATIVE.", taxRepBean.getC1(), 4, false, "BANK",repBean);
	    	    ReportDAO.println("AGM (PERSONNEL)", taxRepBean.getC3(), 1, false, "BANK",repBean);
	    	    
	    	    
	    	    
    	      } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	 }
	 
	public static float Chk_Slab1(int trncd, String dt, int WrkAmt, int emp_type,RepoartBean repBean)
	 {
		 	float result = 0.00F;
		 	ResultSet slb = null;
		 	float slbamt = 0.00F;
		 	String slbstr="";
		 	
		 	if(emp_type == 4)
		 	{
		 		emp_type = 0;
		 	}
		 	
		 	slbstr = " select * from slab where emp_cat = "+emp_type+" and trncd = " + trncd + " and effdate = "+
		             "( select min(s.effdate) from slab s where s.emp_cat = "+emp_type+" and  trncd = " + trncd + " and effdate >= '" + dt + "' )  "+
		             " order by srno ";
		    System.out.println(slbstr);		 	
		 	if(trncd == 101)
		 	{
		 		return WrkAmt;
		 	}
		 	if(WrkAmt == 0)
		 	{
		 		return 0;
		 	}
		 	try
		 	{
				Statement st = repBean.getCn().createStatement();
				
				slb = st.executeQuery(slbstr);
				while(slb.next())
				{
					if(slb.getInt("TOAMT") < WrkAmt)
					{
						slbamt = slbamt + slb.getInt("FIXAMT");
					}
					else
					{
						slbamt =  slb.getInt("FIXAMT") + ((WrkAmt - slb.getInt("FRMAMT")) * slb.getInt("PER") / 100);
					}
				}
				result = slbamt;
			}
		 	catch (SQLException e) 
		 	{
				e.printStackTrace();
			}
		 	
		 	return result;
	 }
	 
	public static void  print_f24(int peno, String prdt, String pename, String desigcd, String PAN_NO, String SEX1, int srno,RepoartBean repBean,TaxReportBean taxRepBean)
	 {
		 
		 int[] Amt = new int[41];
		 int srno2 = 0;
		 String grd = "";
		 int grdint  = 0;
		 int via  = 0;
		 int q80cc  = 0;
		 int q80d  = 0;
		 int q80e  = 0;
		 int q801  = 0;
		 int finctot  = 0;
		 int q80g  = 0;
		 int q80gG  = 0;
		 int q80l  = 0;
		 int q80u  = 0;
		 int caltax  = 0;
		 int ftotinc  = 0;
		 int inctot  = 0;
		 int b88  = 0;
		 int c88  = 0;
		 int us89  = 0;
		 int tfee  = 0;
		 int qtfee  = 0;
		 int pf  = 0;
		 int ppf  = 0;
		 int lic  = 0;
		 int hl  = 0;
		 int uti  = 0;
		 int nsc  = 0;
		 int nss  = 0;
		 int educess  = 0;
		 int fpf  = 0;
		 int nscint  = 0;
		 int infra  = 0;
		 int qpf  = 0;
		 int qppf  = 0;
		 int qlic  = 0;
		 int qhl  = 0;
		 int quti  = 0;
		 int qnsc  = 0;
		 int qnss  = 0;
		 int qfpf  = 0;
		 int qnscint  = 0;
		 int us10  = 0;
		 int qinfra  = 0;
		 int tot88  = 0;
		 int qtot88  = 0;
		 ResultSet trs = null;
		 taxRepBean.setC4tot(0);
		 taxRepBean.setC5tot(0);
		 taxRepBean.setC3tot(0);
		 taxRepBean.setTotamt(0);
		 buildsql(peno, prdt, "A1%",taxRepBean);
		 printgrpF16(peno, prdt,repBean,taxRepBean);
		 taxRepBean.setTotinc(taxRepBean.getC5tot());
		 taxRepBean.setStr1(" SELECT Y.TRNCD,SUM(NET_AMT) net_amt,sum(inp_amt) inp_amt " +
		        " FROM YTDTRAN Y " +
		        " Where y.trncd = 120 and  Y.empno = " + peno +
		        " AND Y.TRNDT BETWEEN '" + ReportDAO.Boy(prdt) + "'  AND '" + ReportDAO.EOM(prdt) + "' group by trncd");
		
		 try
		 {
			Statement st = repBean.getCn().createStatement();
			trs = st.executeQuery(taxRepBean.getStr1());
			taxRepBean.setVperk_amt(0);
			if(trs.next())
			{
				taxRepBean.setVperk_amt((trs.getString("NET_AMT")==null?trs.getInt("INP_AMT"):trs.getInt("NET_AMT")));
			}
			
			taxRepBean.setTotinc(taxRepBean.getTotinc() - taxRepBean.getVperk_amt());
			Amt[1] = taxRepBean.getTotinc();
			Amt[2] = taxRepBean.getVperk_amt();
			Amt[3] = 0;
			taxRepBean.setC5tot(0); 
			
			buildsql(peno, prdt, "A3%",taxRepBean);
			printgrpF16(peno, prdt,repBean,taxRepBean);
			
			us10 = Math.abs(Math.round(taxRepBean.getC5tot()));
			Amt[4] = Math.round(us10);
			Amt[5] = Amt[1] + Amt[2] + Amt[3];
			taxRepBean.setC5tot(taxRepBean.getTotinc());
			taxRepBean.setC5tot(Math.round(Calculate.checkSlab(500, prdt, taxRepBean.getC5tot(), 1, peno,repBean.getCn())));
			Amt[6] = taxRepBean.getC5tot();
			taxRepBean.setTotded(taxRepBean.getC5tot());
			taxRepBean.setC4tot(0);
			taxRepBean.setC3tot(0);
			taxRepBean.setC5tot(0);
			
			buildsql(peno, prdt, "A5%",taxRepBean);
			printgrpF16(peno, prdt,repBean,taxRepBean);
			
			taxRepBean.setOTHER_DED(taxRepBean.getC5tot());
			Amt[7] = taxRepBean.getOTHER_DED();
			taxRepBean.setTotamt(0);
			taxRepBean.setTotamt((taxRepBean.getTotinc() + Amt[2] - (taxRepBean.getTotded() + taxRepBean.getOTHER_DED())));
		    Amt[8] = taxRepBean.getTotamt();
		    taxRepBean.setTotinc(taxRepBean.getTotamt());
			
		    taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "AA%",taxRepBean);
			printgrpF16(peno, prdt,repBean,taxRepBean);
			
			
			Amt[9] = (-1) * Math.round(Math.abs(taxRepBean.getC5tot()));
			Amt[10] = taxRepBean.getTotinc() - Math.abs(Amt[9]);
			
			 taxRepBean.setTotinc(Amt[10]);
			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "AD4%",taxRepBean);
			printgrpF16(peno, prdt,repBean,taxRepBean);
			
			q80g = Math.round(Calculate.checkSlab(803, prdt, taxRepBean.getC5tot(), 1, peno,repBean.getCn()));
			Amt[11] = q80g;
			taxRepBean.setC5tot(0);
			
			buildsql(peno, prdt, "AD2%",taxRepBean);
			printgrpF16(peno, prdt,repBean,taxRepBean);
			
			q80d = Math.round(Calculate.checkSlab(801, prdt, taxRepBean.getC5tot(), 1, peno,repBean.getCn()));
			Amt[12] = q80d;
			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "AD1%",taxRepBean);
			printgrpF16(peno, prdt,repBean,taxRepBean);
			
			q80cc = Math.round(Calculate.checkSlab(806, prdt, taxRepBean.getC5tot(), 1, peno,repBean.getCn()));
			Amt[13] = q80cc;
			
			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "AD5%",taxRepBean);
			printgrpF16(peno, prdt,repBean,taxRepBean);
			
			q80l = Math.round(Calculate.checkSlab(804, prdt, taxRepBean.getC5tot(), 1, peno,repBean.getCn()));
			Amt[14] = q801;
			via = q80cc + q80g + q80gG + q80l + q80d;
			Amt[15] = via;
			Amt[16] = Math.round( taxRepBean.getTotinc() - via);
			inctot = taxRepBean.getTotinc() - via;
			caltax = Math.round((Chk_Slab1(703, prdt, inctot, 1,repBean)));
			Amt[17] = Math.round(caltax);
			taxRepBean.setC5tot(0);
			
			buildsql(peno, prdt, "AJA%",taxRepBean);
			printgrpF16(peno, prdt,repBean,taxRepBean);
			pf = taxRepBean.getC5tot();
			qpf = pf;
			taxRepBean.setC5tot(0);
			
			buildsql(peno, prdt, "AJB%",taxRepBean);
			printgrpF16(peno, prdt,repBean,taxRepBean);
			ppf = taxRepBean.getC5tot();
			qppf = ppf;
			
			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "AJC%",taxRepBean);
			printgrpF16(peno, prdt,repBean,taxRepBean);
			lic = taxRepBean.getC5tot();
			qlic = lic;
			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "AJD%",taxRepBean);
			printgrpF16(peno, prdt,repBean,taxRepBean);
			hl = taxRepBean.getC5tot();
			qhl = Math.round(Calculate.checkSlab(209, prdt, taxRepBean.getC5tot(), 1, peno,repBean.getCn()));
			
			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "AJE%",taxRepBean);
			printgrpF16(peno, prdt,repBean,taxRepBean);
			uti = taxRepBean.getC5tot();
			quti = uti;
					   
			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "AJF%",taxRepBean);
			printgrpF16(peno, prdt,repBean,taxRepBean);
			nsc = taxRepBean.getC5tot();
			qnsc = nsc;
					   
			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "AJG%",taxRepBean);
			printgrpF16(peno, prdt,repBean,taxRepBean);
			nss = taxRepBean.getC5tot();
			qnss = nss;
					      
			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "AJH%",taxRepBean);
			printgrpF16(peno, prdt,repBean,taxRepBean);
			fpf = taxRepBean.getC5tot();
			qfpf = fpf;
					      
			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "AJI%",taxRepBean);
			printgrpF16(peno, prdt,repBean,taxRepBean);
			nscint = taxRepBean.getC5tot();
			qnscint = nscint;
					   
			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "AJK%",taxRepBean);
			printgrpF16(peno, prdt,repBean,taxRepBean);
			tfee = taxRepBean.getC5tot();
			qtfee = taxRepBean.getC5tot();
					   
					   
			tot88 = pf + ppf + lic + hl + uti + nsc + nss + fpf + nscint + tfee;
			qtot88 = qpf + qppf + qlic + qhl + quti + qnsc + qnss + qfpf + qnscint + qtfee;
			
			if(qtot88 > 70000)
			{
				qtot88 = 70000;
			}
			
			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "AK%",taxRepBean);
			printgrpF16(peno, prdt,repBean,taxRepBean);
			infra = taxRepBean.getC5tot();
			qinfra = Math.round(Calculate.checkSlab(551, prdt, taxRepBean.getC5tot(), 1, peno,repBean.getCn()));
			
			Chk_reb88_Slab(880, prdt, ftotinc, inctot, 1,repBean,taxRepBean);
			taxRepBean.setReb88((qtot88 + qinfra) * taxRepBean.getReb88_per() / 100);
			
			if(taxRepBean.getReb88() > taxRepBean.getReb88_max_amt())
			{
				taxRepBean.setReb88(taxRepBean.getReb88_max_amt());
			}
			
			Amt[18] = taxRepBean.getReb88();
			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "AN1%",taxRepBean);
			printgrpF16(peno, prdt,repBean,taxRepBean);
			b88 = taxRepBean.getC5tot();
					   
			Amt[19] = b88;
					   
			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "AN2%",taxRepBean);
			printgrpF16(peno, prdt,repBean,taxRepBean);
			c88 = taxRepBean.getC5tot();
			
			if(SEX1.equalsIgnoreCase("F"))
			{
				c88 = c88 + 5000;
			}
			Amt[20] = c88;
					   
			Amt[21] = Amt[17] - (Amt[18] + Amt[19] + Amt[20]);
					   
			taxRepBean.setC5tot(0);
			buildsql(peno, prdt, "AN3%",taxRepBean);
			printgrpF16(peno, prdt,repBean,taxRepBean);
			us89 = taxRepBean.getC5tot();
			Amt[22] = us89;
			taxRepBean.setSercharge(0);
			
			if(Math.round(taxRepBean.getReb88() + us89 + b88 + c88) > caltax)
			{
				taxRepBean.setNet_tax(0);
			}
			else
			{
				taxRepBean.setNet_tax(Math.round(caltax - (taxRepBean.getReb88() + us89 + b88 + c88)));
			}
			
			educess = Math.round(taxRepBean.getNet_tax() * 0.03F);
			taxRepBean.setNet_tax(taxRepBean.getNet_tax() + educess);
			Amt[23] = taxRepBean.getNet_tax();
			
			if(taxRepBean.getNet_tax() > 0)
			{
				taxRepBean.setSercharge(Math.round(Calculate.checkSlab(900, prdt, taxRepBean.getNet_tax(), 1, peno,repBean.getCn())));
			}
			taxRepBean.setNet_tax(taxRepBean.getNet_tax() + taxRepBean.getSercharge());
			taxRepBean.setC5tot(0);
			
			
			
			
			buildsql(peno, prdt, "BB%",taxRepBean);
			printgrpF16(peno, prdt,repBean,taxRepBean);
			taxRepBean.setTAXPAID(taxRepBean.getC5tot());
			Amt[24] = taxRepBean.getTAXPAID();
			Amt[25] = taxRepBean.getSercharge();
			Amt[26] = Amt[24] + Amt[25];
			Amt[27] = Math.round(taxRepBean.getNet_tax() - taxRepBean.getTAXPAID());
			
			srno2 = srno2 + 1;
			ReportDAO.println(UtilityDAO.trans(srno2, "999", "", false, false), 1, 0, false, "BANK",repBean);
			ReportDAO.println("|", 4, 0, false, "BANK",repBean);
			ReportDAO.println(PAN_NO, 5, 0, false, "BANK",repBean);
			ReportDAO.println("|", 18, 0, false, "BANK",repBean);
			ReportDAO.println(pename, 19, 0, false, "BANK",repBean);
			ReportDAO.println("|", 40, 0, false, "BANK",repBean);
			
			SimpleDateFormat from = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat to = new SimpleDateFormat("dd/MM/yy");
			Date d = from.parse(ReportDAO.Boy(prdt));
			String begin = to.format(d);
			
			ReportDAO.println(begin, 41, 0, false, "BANK",repBean);
			ReportDAO.println("|", 49, 0, false, "BANK",repBean);
			
			d = from.parse(ReportDAO.Eoy(prdt));
			begin = to.format(d);
			ReportDAO.println(begin, 50, 0, false, "BANK",repBean);
			ReportDAO.println("|", 58, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[1], 60, 0, false, "BANK",repBean);
			ReportDAO.println("|", 67, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[2], 68, 0, false, "BANK",repBean);
			ReportDAO.println("|", 73, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[3], 74, 0, false, "BANK",repBean);
			ReportDAO.println("|", 78, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[4], 79, 0, false, "BANK",repBean);
			ReportDAO.println("|", 84, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[5], 85, 0, false, "BANK",repBean);
			ReportDAO.println("|", 92, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[6], 93, 0, false, "BANK",repBean);
			ReportDAO.println("|", 98, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[7], 99, 0, false, "BANK",repBean);
			ReportDAO.println("|", 104, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[8], 105, 0, false, "BANK",repBean);
			ReportDAO.println("|", 111, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[9], 112, 0, false, "BANK",repBean);
			ReportDAO.println("|", 118, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[10], 119, 0, false, "BANK",repBean);
			ReportDAO.println("|", 125, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[11], 126, 0, false, "BANK",repBean);
			ReportDAO.println("|", 131, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[12], 132, 0, false, "BANK",repBean);
			ReportDAO.println("|", 136, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[13], 137, 0, false, "BANK",repBean);
			ReportDAO.println("|", 142, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[14], 143, 0, false, "BANK",repBean);
			ReportDAO.println("|", 148, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[15], 149, 0, false, "BANK",repBean);
			ReportDAO.println("|", 155, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[16], 156, 0, false, "BANK",repBean);
			ReportDAO.println("|", 162, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[17], 163, 0, false, "BANK",repBean);
			ReportDAO.println("|", 169, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[18], 170, 0, false, "BANK",repBean);
			ReportDAO.println("|", 176, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[19], 177, 0, false, "BANK",repBean);
			ReportDAO.println("|", 182, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[20], 183, 0, false, "BANK",repBean);
			ReportDAO.println("|", 188, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[21], 189, 0, false, "BANK",repBean);
			ReportDAO.println("|", 195, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[22], 196, 0, false, "BANK",repBean);
			ReportDAO.println("|", 200, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[23], 201, 0, false, "BANK",repBean);
			ReportDAO.println("|", 207, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[24], 208, 0, false, "BANK",repBean);
			ReportDAO.println("|", 214, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[25], 216, 0, false, "BANK",repBean);
			ReportDAO.println("|", 220, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[26], 221, 0, false, "BANK",repBean);
			ReportDAO.println("|", 227, 0, false, "BANK",repBean);
			ReportDAO.println(""+Amt[27], 228, 0, false, "BANK",repBean);
			ReportDAO.println("|", 235, 1, false, "BANK",repBean);
			
			taxRepBean.setTot(taxRepBean.getTot() + Amt[26]);
			ReportDAO.println("|", 4, 0, false, "BANK",repBean);
			ReportDAO.println("|", 18, 0, false, "BANK",repBean);
			ReportDAO.println("|", 40, 0, false, "BANK",repBean);
			ReportDAO.println("|", 49, 0, false, "BANK",repBean);
			ReportDAO.println("|", 58, 0, false, "BANK",repBean);
			ReportDAO.println("|", 67, 0, false, "BANK",repBean);
			ReportDAO.println("|", 73, 0, false, "BANK",repBean);
			ReportDAO.println("|", 78, 0, false, "BANK",repBean);
			ReportDAO.println("|", 84, 0, false, "BANK",repBean);
			ReportDAO.println("|", 92, 0, false, "BANK",repBean);
			ReportDAO.println("|", 98, 0, false, "BANK",repBean);
			ReportDAO.println("|", 104, 0, false, "BANK",repBean);
			ReportDAO.println("|", 111, 0, false, "BANK",repBean);
			ReportDAO.println("|", 118, 0, false, "BANK",repBean);
			ReportDAO.println("|", 125, 0, false, "BANK",repBean);
			ReportDAO.println("|", 131, 0, false, "BANK",repBean);
			ReportDAO.println("|", 136, 0, false, "BANK",repBean);
			ReportDAO.println("|", 142, 0, false, "BANK",repBean);
			ReportDAO.println("|", 148, 0, false, "BANK",repBean);
			ReportDAO.println("|", 155, 0, false, "BANK",repBean);
			ReportDAO.println("|", 162, 0, false, "BANK",repBean);
			ReportDAO.println("|", 169, 0, false, "BANK",repBean);
			ReportDAO.println("|", 176, 0, false, "BANK",repBean);
			ReportDAO.println("|", 182, 0, false, "BANK",repBean);
			ReportDAO.println("|", 188, 0, false, "BANK",repBean);
			ReportDAO.println("|", 195, 0, false, "BANK",repBean);
			ReportDAO.println("|", 200, 0, false, "BANK",repBean);
			ReportDAO.println("|", 207, 0, false, "BANK",repBean);
			ReportDAO.println("|", 214, 0, false, "BANK",repBean);
			ReportDAO.println("|", 220, 0, false, "BANK",repBean);
			ReportDAO.println("|", 227, 0, false, "BANK",repBean);
			ReportDAO.println("|", 235, 1, false, "BANK",repBean);
 		 }
		 catch (Exception e) 
		 {
			e.printStackTrace();
		 }
		 
	 }
	
	public static void ANN_12BA(int feno, int teno, String rdt, String filePath)
	{
		TaxReportBean taxRepBean = new TaxReportBean();
		RepoartBean repBean  = new RepoartBean();
		FileWriter Fp;
		Connection Cn = null;
		ResultSet emp = null;
		ResultSet trs = null;
		String ename = "";
		String str1 = "";
		String empsql = "";
		String grd = "";
		String PAN_NO = "";
		String dtrsql = "";
		String MNEXT = "";
		int peno = 0;
		String prdt = "";
		
		ReportDAO.inithead(repBean);
		taxRepBean.setDline(UtilityDAO.stringOfSize(80, '-'));
		taxRepBean.setC1(0);
        taxRepBean.setC2(4);
		taxRepBean.setC3(35);
		taxRepBean.setC4(51);
        taxRepBean.setC5(67);
		
		MNEXT = "P";
		
		
		repBean.setLnCount(0);
		repBean.setPageNo(1);
		repBean.setLineLen(80);
		repBean.setPageLen(70);
		repBean.setFoot_Fl(true);
		repBean.setBrnNo(9999);
		
		
		ReportDAO.OpenCon("", "", "",repBean);
		Cn = repBean.getCn();
		if(feno != teno )
		{
			empsql = "select emp.*, t.acno, t.branch, t.grade ,emp.panno from empmast emp, emptran t where t.empno = emp.empno  AND " +
					 " t.effdate = ( SELECT MAX(E2.EFFDATE) FROM EMPTRAN E2 WHERE e2.empno = emp.empno and E2.EFFDATE <= '" + rdt + "')" +
					 " and emp.status = 'A' and emp.empno between  " + feno + " and " + teno + " order by emp.empno ";
		}
		else
		{
			empsql = "select emp.*, t.acno, t.branch, t.grade ,emp.panno from empmast emp, emptran t where t.empno = emp.empno  AND " +
					 " t.effdate = ( SELECT MAX(E2.EFFDATE) FROM EMPTRAN E2 WHERE e2.empno = emp.empno and E2.EFFDATE <= " + rdt + ")" +
					 " and emp.empno between  " + feno + " and " + teno + " order by emp.empno ";
		}
		try
		{
			
			Statement st = repBean.getCn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			emp = st.executeQuery(empsql);
			if(!emp.next())
			{
				System.out.println("Employee Record Set Is Empty");
				return;
			}
			
			ReportDAO.make_prn_file(filePath,repBean);
			emp.beforeFirst();
			while(emp.next())
			{
				peno = emp.getInt("EMPNO");
				prdt = rdt;
				str1 =  " SELECT Y.TRNCD,SUM(NET_AMT) net_amt,sum(inp_amt) inp_amt " +
						" FROM YTDTRAN Y " +
						" Where y.trncd = 120 and  Y.empno = " + peno +
						" AND Y.TRNDT BETWEEN '" + ReportDAO.Boy(prdt) + "'  AND '" + ReportDAO.EOM(prdt) + "' group by trncd";
				trs = null;
				taxRepBean.setVperk_amt(0);
				Statement st1 = repBean.getCn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				trs = st1.executeQuery(str1);
				if(trs.next())
				{
					taxRepBean.setVperk_amt((trs.getString("NET_AMT")==null?trs.getInt("INP_AMT"):trs.getInt("NET_AMT")));
				}
				
				if(taxRepBean.getVperk_amt() <= 0)
				{
					continue;
				}
				
				grd = GradeHandler.GetGrade(emp.getInt("GRADE"), "Y",repBean);
				taxRepBean.setC4tot(0);
				 taxRepBean.setC5tot(0);
				 taxRepBean.setC3tot(0);
				 taxRepBean.setTotamt(0);
				buildsqlF16(peno, prdt, "A1%",taxRepBean);
				printgrpF16(peno, prdt,repBean,taxRepBean);
				
				taxRepBean.setTotinc(taxRepBean.getC5tot());
				buildsqlF16(peno, prdt, "A2%",taxRepBean);
				printgrpF16(peno, prdt,repBean,taxRepBean);
				
				 taxRepBean.setTotinc(taxRepBean.getC5tot());
				 taxRepBean.setTotinc(taxRepBean.getTotinc() - taxRepBean.getVperk_amt());
				ename = emp.getString("LNAME") + " " + emp.getString("FNAME") + " " + emp.getString("MNAME");
				ReportDAO.println("                              FORM NO 12BA", 1, 1, false, "BANK",repBean);
				ReportDAO.println("                            [See Rule 26A(2)]", 1, 1, false, "BANK",repBean);
				ReportDAO.println("    STATEMENT SHOWING PARTICULARS OF PERQUISITIES, OTHER FRINGE BENIFITS OR", 1, 1, false, "BANK",repBean);
				ReportDAO.println("    AMENITIES AND PROFITS IN LIEU OF SALARY WITHH VALUE THEREOF -", 1, 2, false, "BANK",repBean);
				ReportDAO.println("1) Name and address of Employer             ORG NAME", 1, 1, false, "BANK",repBean);
				ReportDAO.println("                                            ORG ADDR1", 1, 1, false, "BANK",repBean);
				ReportDAO.println("                                            ORG ADDR2", 1, 1, false, "BANK",repBean);
				ReportDAO.println("                                            (Maharashtra)", 1, 1, false, "BANK",repBean);
				ReportDAO.println("2) TAN                                      A B C D E F G H I J", 1, 1, false, "BANK",repBean);
				ReportDAO.println("3) TDS Assessment Range of EMPLOYER         ITO WARD 2(3)", 1, 1, false, "BANK",repBean);
				ReportDAO.println("4) Name and Designation and PAN of", 1, 0, false, "BANK",repBean);
				ReportDAO.println(ename, 45, 1, false, "BANK",repBean);
				ReportDAO.println("   Employee", 1, 0, false, "BANK",repBean);
				ReportDAO.println(grd, 45, 1, false, "BANK",repBean);
				ReportDAO.println((emp.getString("PANNO")==null?"":emp.getString("PANNO")), 65, 1, false, "BANK",repBean);
				ReportDAO.println("5) Is the employee a director or a          NOT APPLICABLE", 1, 1, false, "BANK",repBean);
				ReportDAO.println("   person with  substantial interest", 1, 1, false, "BANK",repBean);
				ReportDAO.println("   in the company ( where the", 1, 1, false, "BANK",repBean);
				ReportDAO.println("   employer is a company)", 1, 1, false, "BANK",repBean);
				ReportDAO.println("6) Income under the head Salaries of the", 1, 0, false, "BANK",repBean);
				ReportDAO.println(UtilityDAO.trans(Math.round(taxRepBean.getTotinc()), "9,99,999", "", false, false), 45, 1, false, "BANK",repBean);
				ReportDAO.println("   employee (Other than from perquisites)", 1, 1, false, "BANK",repBean);
				ReportDAO.println("7) Financial Year", 1, 0, false, "BANK",repBean);
				ReportDAO.println((ReportDAO.getMonth(prdt) > 3 ? prdt.substring(7,11) : Integer.parseInt(prdt.substring(7,11)) - 1) +
								" - " + (ReportDAO.getMonth(prdt) < 3 ? prdt.substring(7,11) : Integer.parseInt(prdt.substring(7,11)) + 1), 1, 1, false, "BANK",repBean);
				ReportDAO.println("8) Valuation of  Perquisites", 1, 0, false, "BANK",repBean);
				ReportDAO.println(UtilityDAO.trans(Math.round(taxRepBean.getVperk_amt()), "9,99,999", "", false, false), 45, 1, false, "BANK",repBean);
				ReportDAO.println("--------------------------------------------------------------------------------", 1, 1, false, "BANK",repBean);
				ReportDAO.println("SrNo  Nature of Perquisites                 Value of  Amount if    Amt of Perqu.", 1, 1, false, "BANK",repBean);
				ReportDAO.println("      (See Rule 3)                     Perquisite as  any recover  Chargeable to", 1, 1, false, "BANK",repBean);
				ReportDAO.println("                                            per rule  from theEmp  tax Col3-Col4", 1, 1, false, "BANK",repBean);
				ReportDAO.println("                                              (Rs.)      (Rs.)         (Rs.)", 1, 1, false, "BANK",repBean);
				ReportDAO.println("--------------------------------------------------------------------------------", 1, 1, false, "BANK",repBean);
				ReportDAO.println(" 1.  Accommodation", 1, 1, false, "BANK",repBean);
				ReportDAO.println(" 2.  Car/Other Automotive", 1, 1, false, "BANK",repBean);
				ReportDAO.println(" 3.  Sweeper/Gardner/Watchman/Personal Attendant", 1, 1, false, "BANK",repBean);
				ReportDAO.println(" 4.  Gas, Electricity, Water.", 1, 1, false, "BANK",repBean);
				ReportDAO.println(" 5.  Interest free or concessional loans", 1, 0, false, "BANK",repBean);
				ReportDAO.println(UtilityDAO.trans(Math.round(taxRepBean.getVperk_amt()), "9,99,999", "", false, false), 45, 0, false, "BANK",repBean);
				ReportDAO.println(UtilityDAO.trans(Math.round(taxRepBean.getVperk_amt()), "9,99,999", "", false, false), 70, 1, false, "BANK",repBean);
				ReportDAO.println(" 6.  Holiday expenses.", 1, 1, false, "BANK",repBean);
				ReportDAO.println(" 7.  Free or concessional travel", 1, 1, false, "BANK",repBean);
				ReportDAO.println(" 8.  Free meals", 1, 1, false, "BANK",repBean);
				ReportDAO.println(" 9.  Education", 1, 1, false, "BANK",repBean);
				ReportDAO.println("10.  Gifts, Vouchers etc.", 1, 1, false, "BANK",repBean);
				ReportDAO.println("11.  Credit Card Expenses.", 1, 1, false, "BANK",repBean);
				ReportDAO.println("12.  Club Expenses.", 1, 1, false, "BANK",repBean);
				ReportDAO.println("13.  Use of moveable assets by employees", 1, 1, false, "BANK",repBean);
				ReportDAO.println("14.  Transfer of assets by employees", 1, 1, false, "BANK",repBean);
				ReportDAO.println("15.  Value of any other benefit/amenity/service/privilege", 1, 1, false, "BANK",repBean);
				ReportDAO.println("16.  Stock Option (non-qualified options)", 1, 1, false, "BANK",repBean);
				ReportDAO.println("17.  Other benefits, amenities.", 1, 1, false, "BANK",repBean);
				ReportDAO.println("18.  Total value of perquisites.", 1, 1, false, "BANK",repBean);
				ReportDAO.println("19.  Total value of profits in lieu of salary as per section 17(3)", 1, 1, false, "BANK",repBean);
				ReportDAO.println("--------------------------------------------------------------------------------", 1, 1, false, "BANK",repBean);
				ReportDAO.println("(9)  Details of tax", 1, 1, false, "BANK",repBean);
				ReportDAO.println("(a)  Tax deducted from salary of the employee u/s 192(1)", 1, 0, false, "BANK",repBean);
				
				 taxRepBean.setC5tot(0);
				buildsql(peno, prdt, "BB%",taxRepBean);
				printgrpF16(peno, prdt,repBean,taxRepBean);
				ReportDAO.println(UtilityDAO.trans(Math.round(taxRepBean.getC5tot()), "9,99,999", "", false, false), 60, 1, false, "BANK",repBean);
				
				ReportDAO.println("(b)  Tax paid by employer on behalf of the                      Nil", 1, 1, false, "BANK",repBean);
				ReportDAO.println("     employee under Section 192(1A)", 1, 1, false, "BANK",repBean);
				ReportDAO.println("(c)  Total Tax paid.", 1, 0, false, "BANK",repBean);
				ReportDAO.println(UtilityDAO.trans(Math.round(taxRepBean.getC5tot()), "9,99,999", "", false, false), 60, 1, false, "BANK",repBean);
				ReportDAO.println("(d)  Date of payment into Government treasury.         ( AS PER ANNEXURE F16 )", 1, 1, false, "BANK",repBean);
				ReportDAO.println("--------------------------------------------------------------------------------", 1, 1, false, "BANK",repBean);
				ReportDAO.println("                  : DECLARATION BY EMPLOYER :", 1, 1, false, "BANK",repBean);
				ReportDAO.println("                  ---------------------------", 1, 1, false, "BANK",repBean);
				ReportDAO.println(" I Full Name  working as Designation", 1, 1, false, "BANK",repBean);
				ReportDAO.println("do hereby declare on behalf of ORG NAME", 1, 1, false, "BANK",repBean);
				ReportDAO.println("that the information given above is based on the books of account, documents and", 1, 1, false, "BANK",repBean);
				ReportDAO.println("other relevant records or information available with us and the details of value", 1, 1, false, "BANK",repBean);
				ReportDAO.println("of each perquisite are in accordance with section 17 and rules framed thereunder", 1, 1, false, "BANK",repBean);
				ReportDAO.println("and that such information is true and correct.", 1, 2, false, "BANK",repBean);
				ReportDAO.println("                                            ...................................", 1, 1, false, "BANK",repBean);
				ReportDAO.println("                                            Signature of the person responsible", 1, 1, false, "BANK",repBean);
				ReportDAO.println("                                                    for deduction of tax.", 1, 1, false, "BANK",repBean);
				ReportDAO.println("Place :City                             Full Name   : "+ename, 1, 1, false, "BANK",repBean);
				ReportDAO.println("Date  : ", 1, 0, false, "BANK",repBean);
				ReportDAO.println("Designation :"+(char)12, 45, 2, false, "BANK",repBean);
				
				repBean.setLnCount(0);
			}
			
			 Fp = repBean.getFp();
				Fp.close();
				return;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static void PTForm3_1A(String dt){
		TaxReportBean taxRepBean = new TaxReportBean();
		RepoartBean repBean  = new RepoartBean();
		FileWriter Fp;
		Connection Cn = null;
		ResultSet pt = null;
		ResultSet trs = null;
		String ename = "";
		String str1 = "";
		String dtrsql = "";
		int pt_slab = 0;
		int tot_emp = 0;
		int tot_amt = 0;
		int peno = 0;
		int tax_amt = 0;
		String SLINE = "";
		ReportDAO.inithead(repBean);
		SLINE = UtilityDAO.stringOfSize(80, '-');
		taxRepBean.setDline(UtilityDAO.stringOfSize(80, '-'));
				repBean.setLnCount(0);
		repBean.setPageNo(1);
		repBean.setLineLen(80);
		repBean.setPageLen(70);
		repBean.setFoot_Fl(true);
		repBean.setBrnNo(9999);
		try
		{
				ReportDAO.OpenCon("", "", "",repBean);
				Cn = repBean.getCn();
			    ReportDAO.make_prn_file("D:/form3.txt",repBean);
		
			    Cn = repBean.getCn();
			    str1 = "select DISTINCT(cal_amt) as pt, sum(cal_amt) as tot_amt, COUNT(empno) as tot_emp from ytdtran where trncd =  202 and trndt = '"+dt+"' GROUP BY cal_amt order by PT";
			    Statement st = repBean.getCn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    pt = st.executeQuery(str1);
			    if(!pt.next()){
			    	System.out.println("PT TAX IS NOT CALCULATED OF THIS MONTH");
					return;
			    }
			    pt.beforeFirst();
				ReportDAO.println("                              FORM NO III", 1, 1, false, "BANK",repBean);
				ReportDAO.println("                                   [Part I A]", 1, 1, false, "BANK",repBean);
				ReportDAO.println("                                          or the", 1, 1, false, "BANK",repBean);
				ReportDAO.println("                                             Proffession Tax Officer", 1, 2, false, "BANK",repBean);
				ReportDAO.println("                            Return-cum-Chalan", 1, 1, false, "BANK",repBean);
				ReportDAO.println("B.S.T.R.C. No., if any.                            ", 1, 1, false, "BANK",repBean);
				ReportDAO.println("    The Maharashtra State Tax on Professions, Trades, Callings And Employments", 1, 1, false, "BANK",repBean);
				ReportDAO.println("                    Act, 1975 AND Rule 11,11-A, 11-B, 11-C    ", 1, 2, false, "BANK",repBean);
				ReportDAO.println("     0028, Other Taxes on Income and Expenditure—Taxes on Professions, Trades ,", 1, 1, false, "BANK",repBean);
				ReportDAO.println("                    Callings and Employments-Taxes on Employments    ", 1, 2, false, "BANK",repBean);
				
				
				
				
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
				ReportDAO.println(" Employees whose monthly                      | Rate of Tax | No. of   | Amount of   ", 1, 1, false, "BANK",repBean);
				ReportDAO.println(" Salaries, Wages                              | per Month   | Employees| Tax deducted", 1, 1, false, "BANK",repBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
				while(pt.next()){
					if(pt.getInt("pt")==0){
						ReportDAO.println(" Do not exceed Rs. 2,000                      |  NIL        |"+UtilityDAO.FixStr(""+pt.getInt("tot_emp"), 10, 2) +"|  NIL      ", 1, 1, false, "BANK",repBean);
					}
                    if(pt.getInt("pt")==30){
                    	tax_amt = tax_amt + pt.getInt("tot_amt");
                    	ReportDAO.println(" Exceed Rs. 2,000 but do not exceed Rs. 2,500 | Rs. 30      |"+UtilityDAO.FixStr(""+pt.getInt("tot_emp"), 10, 2) +"|"+UtilityDAO.FixStr(""+pt.getInt("tot_amt"), 7, 2) +"", 1, 1, false, "BANK",repBean);
					}
                    if(pt.getInt("pt")==60){
                    	tax_amt = tax_amt + pt.getInt("tot_amt");
                    	ReportDAO.println(" Exceed Rs. 2,500 but do not exceed Rs. 3,500 | Rs. 60      |"+UtilityDAO.FixStr(""+pt.getInt("tot_emp"), 10, 2) +"|"+UtilityDAO.FixStr(""+pt.getInt("tot_amt"), 7, 2) +"", 1, 1, false, "BANK",repBean);
					}
                    if(pt.getInt("pt")==120){
                    	tax_amt = tax_amt + pt.getInt("tot_amt");
                    	ReportDAO.println(" Exceed Rs. 3,500 but do not exceed Rs. 5,000 | Rs. 120     |"+UtilityDAO.FixStr(""+pt.getInt("tot_emp"), 10, 2) +"|"+UtilityDAO.FixStr(""+pt.getInt("tot_amt"), 7, 2) +"", 1, 1, false, "BANK",repBean);
					}
                    if(pt.getInt("pt")==175){
                    	tax_amt = tax_amt + pt.getInt("tot_amt");
                    	ReportDAO.println(" Exceed Rs. 5,000 but do not exceed Rs. 10,000| Rs. 175     |"+UtilityDAO.FixStr(""+pt.getInt("tot_emp"), 10, 2) +"|"+UtilityDAO.FixStr(""+pt.getInt("tot_amt"), 7, 2) +"", 1, 1, false, "BANK",repBean);
					}
                    if(pt.getInt("pt")==200 || pt.getInt("pt")==300){
                    	tax_amt = tax_amt + pt.getInt("tot_amt");
                    	ReportDAO.println(" Exceeds Rs. 10,000                           | Rs.2,500 per|          |             ", 1, 1, false, "BANK",repBean);
        				ReportDAO.println("                                              | annum to be |          |             ", 1, 1, false, "BANK",repBean);
        				ReportDAO.println("                                              | paid in the |          |             ", 1, 1, false, "BANK",repBean);
        				ReportDAO.println("                                              | Follwing    |          |             ", 1, 1, false, "BANK",repBean);
        				ReportDAO.println("                                              | manner:     |"+UtilityDAO.FixStr(""+pt.getInt("tot_emp"), 10, 2) +"|"+UtilityDAO.FixStr(""+pt.getInt("tot_amt"), 7, 2) +"", 1, 1, false, "BANK",repBean);
        				ReportDAO.println("                                              | a)Rs.200 per|          |             ", 1, 1, false, "BANK",repBean);
        				ReportDAO.println("                                              | month except|          |             ", 1, 1, false, "BANK",repBean);
        				ReportDAO.println("                                              | in the month|          |             ", 1, 1, false, "BANK",repBean);
        				ReportDAO.println("                                              | of february |          |             ", 1, 1, false, "BANK",repBean);
        				ReportDAO.println("                                              | b)Rs.300 per|          |             ", 1, 1, false, "BANK",repBean);
        				ReportDAO.println("                                              |   month of  |          |             ", 1, 1, false, "BANK",repBean);
        				ReportDAO.println("                                              |   february  |          |             ", 1, 1, false, "BANK",repBean);
					}
                    ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
				}
				
				
				
				
				ReportDAO.println(" Tax Amount                                   | Rs. "+UtilityDAO.FixStr(""+tax_amt, 7, 2) +"", 1, 1, false, "BANK",repBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
				ReportDAO.println(" Interest Amount                              | Rs. ", 1, 1, false, "BANK",repBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
				ReportDAO.println(" Less-Excess tax paid, if any, in the previous| Rs. ", 1, 1, false, "BANK",repBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
				ReportDAO.println(" Year/Quarter/Month                           |     ", 1, 1, false, "BANK",repBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
				ReportDAO.println(" Net Amount Payable                           | Rs. "+UtilityDAO.FixStr(""+tax_amt, 7, 2) +"", 1, 1, false, "BANK",repBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
				TaxCalculation.pringapline(repBean, taxRepBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
				ReportDAO.println(" Profession Tax Registration Certificate No. | Period From | Period To", 1, 1, false, "BANK",repBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
				ReportDAO.println("                                             | "+ReportDAO.BOM(dt)+" | "+ReportDAO.EOM(dt)+"", 1, 1, false, "BANK",repBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
				TaxCalculation.pringapline(repBean, taxRepBean);
				ReportDAO.println(" Name and Address: Harsh Construction Pvt LTD   ", 1, 1, false, "BANK",repBean);
				ReportDAO.println("                   Nashik,411061   ", 1, 1, false, "BANK",repBean);
				TaxCalculation.pringapline(repBean, taxRepBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
				ReportDAO.println(" The above statements are true to the best of my knowledge and belief.", 1, 1, false, "BANK",repBean);
				TaxCalculation.pringapline(repBean, taxRepBean);
				ReportDAO.println(" Date:  ", 1, 1, false, "BANK",repBean);
				ReportDAO.println(" Place:                                   Signature        &", 1, 1, false, "BANK",repBean);
				ReportDAO.println(" Designation", 1, 1, false, "BANK",repBean);
				ReportDAO.println(" ___________", 1, 1, false, "BANK",repBean);
				ReportDAO.println(" 1. Form III was substituted by G.N. No. TFT-1100/CR-57/Taxation-3, ", 1, 1, false, "BANK",repBean);
				ReportDAO.println(" dated 11-10-2000, w.e.f. 1.4.2000.", 1, 1, false, "BANK",repBean);
				TaxCalculation.pringapline(repBean, taxRepBean);
				ReportDAO.println("                            For the Treasury Use Only", 1, 1, false, "BANK",repBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean); 
				ReportDAO.println(" Received Rs. (in Words)                                | Rupees (in Figures)                 ", 1, 1, false, "BANK",repBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
				ReportDAO.println(""+originalNumToLetter.getInWords(""+tax_amt)+"| "+tax_amt+"                 ", 1, 1, false, "BANK",repBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean); 
				ReportDAO.println(" Date of Entry :                         |            Chalan No. :             ", 1, 1, false, "BANK",repBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean); 
				TaxCalculation.pringapline(repBean, taxRepBean);
				ReportDAO.println(" Treasurer.                 Accountant.                     Treasury             ", 1, 1, false, "BANK",repBean);
				ReportDAO.println(" Officer/ ", 1, 1, false, "BANK",repBean);
				TaxCalculation.pringapline(repBean, taxRepBean);
				ReportDAO.println("Agent or Manager.", 1, 1, false, "BANK",repBean);
				
			 Fp = repBean.getFp();
				Fp.close();
				return;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static int income_tax(int amount){
		int tax = amount;
			if(tax <= 250000)
				tax = 0;
			else if(tax >= 250001 && tax <= 500000)
				tax = (int)((tax-250000)*0.10);
			else if(tax >= 500001 && tax <= 1000000)
				tax = (int)((tax-500000)*0.20)+25000;
			else if(tax >=1000001)
				tax = (int)((tax-1000000)*0.30)+125000;
		
			return tax;
	}
	
	
	public static void PTForm3_I_B(String dt){
		TaxReportBean taxRepBean = new TaxReportBean();
		RepoartBean repBean  = new RepoartBean();
		FileWriter Fp;
		Connection Cn = null;
		ResultSet pt = null;
		ResultSet trs = null;
		
		int tot_emp = 0;
		int tot_amt = 0;
		int peno = 0;
		int tax_amt = 0;
		String SLINE = "";
		String str1 = "";
		ReportDAO.inithead(repBean);
		SLINE = UtilityDAO.stringOfSize(80, '-');
		taxRepBean.setDline(UtilityDAO.stringOfSize(80, '-'));
				repBean.setLnCount(0);
		repBean.setPageNo(1);
		repBean.setLineLen(80);
		repBean.setPageLen(70);
		
		try
		{
				ReportDAO.OpenCon("", "", "",repBean);
				Cn = repBean.getCn();
			    ReportDAO.make_prn_file("D:/form3_IB.txt",repBean);
		
			    Cn = repBean.getCn();
			    str1 = "select DISTINCT(cal_amt) as pt, sum(cal_amt) as tot_amt, COUNT(empno) as tot_emp from ytdtran where trncd =  202 and trndt = '"+dt+"' GROUP BY cal_amt order by PT";
			    Statement st = repBean.getCn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    pt = st.executeQuery(str1);
			    if(!pt.next()){
			    	System.out.println("PT TAX IS NOT CALCULATED OF THIS MONTH");
					return;
			    }
			    pt.beforeFirst();
				ReportDAO.println("                              FORM NO III", 1, 1, false, "BANK",repBean);
				ReportDAO.println("                              [Part I B]", 1, 1, false, "BANK",repBean);
			
				ReportDAO.println("    The Maharashtra State Tax on Professions, Trades, Callings And Employments", 1, 1, false, "BANK",repBean);
				ReportDAO.println("                    Act, 1975 AND Rule 11,11-A, 11-B, 11-C    ", 1, 2, false, "BANK",repBean);
				ReportDAO.println("     0028, Other Taxes on Income and Expenditure—Taxes on Professions, Trades ,", 1, 1, false, "BANK",repBean);
				ReportDAO.println("                    Callings and Employments-Taxes on Employments    ", 1, 2, false, "BANK",repBean);
				
				
				
				
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
				ReportDAO.println(" Employees whose monthly                      | Rate of Tax | No. of   | Amount of   ", 1, 1, false, "BANK",repBean);
				ReportDAO.println(" Salaries, Wages                              | per Month   | Employees| Tax deducted", 1, 1, false, "BANK",repBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
				while(pt.next()){
					if(pt.getInt("pt")==0){
						ReportDAO.println(" Do not exceed Rs. 2,000                      |  NIL        |"+UtilityDAO.FixStr(""+pt.getInt("tot_emp"), 10, 2) +"|  NIL      ", 1, 1, false, "BANK",repBean);
					}
                    if(pt.getInt("pt")==30){
                    	tax_amt = tax_amt + pt.getInt("tot_amt");
                    	ReportDAO.println(" Exceed Rs. 2,000 but do not exceed Rs. 2,500 | Rs. 30      |"+UtilityDAO.FixStr(""+pt.getInt("tot_emp"), 10, 2) +"|"+UtilityDAO.FixStr(""+pt.getInt("tot_amt"), 7, 2) +"", 1, 1, false, "BANK",repBean);
					}
                    if(pt.getInt("pt")==60){
                    	tax_amt = tax_amt + pt.getInt("tot_amt");
                    	ReportDAO.println(" Exceed Rs. 2,500 but do not exceed Rs. 3,500 | Rs. 60      |"+UtilityDAO.FixStr(""+pt.getInt("tot_emp"), 10, 2) +"|"+UtilityDAO.FixStr(""+pt.getInt("tot_amt"), 7, 2) +"", 1, 1, false, "BANK",repBean);
					}
                    if(pt.getInt("pt")==120){
                    	tax_amt = tax_amt + pt.getInt("tot_amt");
                    	ReportDAO.println(" Exceed Rs. 3,500 but do not exceed Rs. 5,000 | Rs. 120     |"+UtilityDAO.FixStr(""+pt.getInt("tot_emp"), 10, 2) +"|"+UtilityDAO.FixStr(""+pt.getInt("tot_amt"), 7, 2) +"", 1, 1, false, "BANK",repBean);
					}
                    if(pt.getInt("pt")==175){
                    	tax_amt = tax_amt + pt.getInt("tot_amt");
                    	ReportDAO.println(" Exceed Rs. 5,000 but do not exceed Rs. 10,000| Rs. 175     |"+UtilityDAO.FixStr(""+pt.getInt("tot_emp"), 10, 2) +"|"+UtilityDAO.FixStr(""+pt.getInt("tot_amt"), 7, 2) +"", 1, 1, false, "BANK",repBean);
					}
                    if(pt.getInt("pt")==200 || pt.getInt("pt")==300){
                    	tax_amt = tax_amt + pt.getInt("tot_amt");
                    	ReportDAO.println(" Exceeds Rs. 10,000                           | Rs.2,500 per|          |             ", 1, 1, false, "BANK",repBean);
        				ReportDAO.println("                                              | annum to be |          |             ", 1, 1, false, "BANK",repBean);
        				ReportDAO.println("                                              | paid in the |          |             ", 1, 1, false, "BANK",repBean);
        				ReportDAO.println("                                              | Follwing    |          |             ", 1, 1, false, "BANK",repBean);
        				ReportDAO.println("                                              | manner:     |"+UtilityDAO.FixStr(""+pt.getInt("tot_emp"), 10, 2) +"|"+UtilityDAO.FixStr(""+pt.getInt("tot_amt"), 7, 2) +"", 1, 1, false, "BANK",repBean);
        				ReportDAO.println("                                              | a)Rs.200 per|          |             ", 1, 1, false, "BANK",repBean);
        				ReportDAO.println("                                              | month except|          |             ", 1, 1, false, "BANK",repBean);
        				ReportDAO.println("                                              | in the month|          |             ", 1, 1, false, "BANK",repBean);
        				ReportDAO.println("                                              | of february |          |             ", 1, 1, false, "BANK",repBean);
        				ReportDAO.println("                                              | b)Rs.300 per|          |             ", 1, 1, false, "BANK",repBean);
        				ReportDAO.println("                                              |   month of  |          |             ", 1, 1, false, "BANK",repBean);
        				ReportDAO.println("                                              |   february  |          |             ", 1, 1, false, "BANK",repBean);
					}
                    ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
				}
				
				
				
				
				ReportDAO.println(" Tax Amount                                   | Rs. "+UtilityDAO.FixStr(""+tax_amt, 7, 2) +"", 1, 1, false, "BANK",repBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
				ReportDAO.println(" Interest Amount                              | Rs. ", 1, 1, false, "BANK",repBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
				ReportDAO.println(" Less-Excess tax paid, if any, in the previous| Rs. ", 1, 1, false, "BANK",repBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
				ReportDAO.println(" Year/Quarter/Month                           |     ", 1, 1, false, "BANK",repBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
				ReportDAO.println(" Net Amount Payable                           | Rs. "+UtilityDAO.FixStr(""+tax_amt, 7, 2) +"", 1, 1, false, "BANK",repBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
				TaxCalculation.pringapline(repBean, taxRepBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
				ReportDAO.println(" Profession Tax Registration Certificate No. | Period From | Period To", 1, 1, false, "BANK",repBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
				ReportDAO.println("                                             | "+ReportDAO.BOM(dt)+" | "+ReportDAO.EOM(dt)+"", 1, 1, false, "BANK",repBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
				TaxCalculation.pringapline(repBean, taxRepBean);
				ReportDAO.println(" Name and Address: Harsh Construction Pvt LTD   ", 1, 1, false, "BANK",repBean);
				ReportDAO.println("                   Nashik,411061   ", 1, 1, false, "BANK",repBean);
				TaxCalculation.pringapline(repBean, taxRepBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
				ReportDAO.println(" The above statements are true to the best of my knowledge and belief.", 1, 1, false, "BANK",repBean);
				TaxCalculation.pringapline(repBean, taxRepBean);
				ReportDAO.println(" Date:  ", 1, 1, false, "BANK",repBean);
				ReportDAO.println(" Place:                                   Signature        &", 1, 1, false, "BANK",repBean);
				ReportDAO.println(" Designation", 1, 1, false, "BANK",repBean);
				ReportDAO.println(" ___________", 1, 1, false, "BANK",repBean);
				ReportDAO.println(" 1. Form III was substituted by G.N. No. TFT-1100/CR-57/Taxation-3, ", 1, 1, false, "BANK",repBean);
				ReportDAO.println(" dated 11-10-2000, w.e.f. 1.4.2000.", 1, 1, false, "BANK",repBean);
				TaxCalculation.pringapline(repBean, taxRepBean);
				ReportDAO.println("                            For the Treasury Use Only", 1, 1, false, "BANK",repBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean); 
				ReportDAO.println(" Received Rs. (in Words)                                | Rupees (in Figures)                 ", 1, 1, false, "BANK",repBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean);
				ReportDAO.println(""+originalNumToLetter.getInWords(""+tax_amt)+"| "+tax_amt+"                 ", 1, 1, false, "BANK",repBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean); 
				ReportDAO.println(" Date of Entry :                         |            Chalan No. :             ", 1, 1, false, "BANK",repBean);
				ReportDAO.println(SLINE, 0, 1, false, "BANK",repBean); 
				TaxCalculation.pringapline(repBean, taxRepBean);
				ReportDAO.println(" Treasurer.                 Accountant.                     Treasury             ", 1, 1, false, "BANK",repBean);
				ReportDAO.println(" Officer/ ", 1, 1, false, "BANK",repBean);
				TaxCalculation.pringapline(repBean, taxRepBean);
				ReportDAO.println("Agent or Manager.", 1, 1, false, "BANK",repBean);
				
			 Fp = repBean.getFp();
				Fp.close();
				return;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void form24(String year, String quarter, String emptype,String branchtyp, String prj_no,String filepath) {
		

		String from_to_year[] = year.split("-");
		String from_to_date	="";
		String month1[]={"","",""};String month2[]={"","",""};
		String BOM[]={"","",""};String EOM[]={"","",""};
		String Challan_No[] = {"","",""};String Branch_code[] = {"","",""};
		
		
		
		if(quarter.equalsIgnoreCase("quarter1"))
			from_to_date = "01-Apr-"+from_to_year[0]+":"+"30-Jun-"+from_to_year[0];
		else if(quarter.equalsIgnoreCase("quarter2"))
			from_to_date = "01-Jul-"+from_to_year[0]+":"+"30-Sep-"+from_to_year[0];
		else if(quarter.equalsIgnoreCase("quarter3"))
			from_to_date = "01-Oct-"+from_to_year[0]+":"+"31-Dec-"+from_to_year[0];
		else if(quarter.equalsIgnoreCase("quarter4"))
			from_to_date = "01-Jan-"+from_to_year[1]+":"+"30-Mar-"+from_to_year[1];
		String from_to[] = {"","",""};
		from_to = from_to_date.split(":");
		System.out.println("date111111====="+from_to[0]);
		System.out.println("date222222====="+from_to[1]);
		String sql = ""
				+ "SELECT YEAR(y.dt) AS Year,MONTH(y.dt) AS Month,CONVERT(DATE,('01-'+ DATENAME(MONTH, y.dt)+'-'+CONVERT(VARCHAR,YEAR(y.dt)))) AS BOM, "
				+ "CONVERT(DATE,DATEADD(s,-1,DATEADD(mm, DATEDIFF(m,0,CONVERT(DATE,('01-'+ DATENAME(MONTH, y.dt)+'-'+CONVERT(VARCHAR,YEAR(y.dt)))))+1,0))) EOM, "
				+ "CONVERT(varchar(3),UPPER(DATENAME(MONTH, y.dt))) AS MonthName "
				+ "FROM    master.dbo.spt_values x "
				+ "CROSS APPLY (SELECT DATEADD(MONTH, x.number, '"+from_to[0]+"')) AS y(dt) "
				+ "WHERE   x.type = 'P' "
				+ "AND     x.number <= DATEDIFF(MONTH, '"+from_to[0]+"', '"+from_to[1]+"')";
		
		

		
		
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
			ReportDAO.OpenCon("", "", "",repBean);
			con = repBean.getCn();	
			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Statement st2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			System.out.println("date query....."+sql);
			ResultSet date_details=st1.executeQuery(sql);
			if (date_details.next()){
				date_details.previous();
				int i=0;
				while(date_details.next()){
					month1[i] = date_details.getString("MonthName");
					BOM[i]   = date_details.getString("BOM");
					EOM[i]   = date_details.getString("EOM");
					i++;
				}
			}
			
			
			String challndetails = ""
					+ "SELECT  convert(varchar(3),upper(DATENAME(MONTH,c.for_month))) as challan_month,c.challan_no ,t.Branch_Code "
					+ "from Challan_Details c,TDS_Payment_Details t where c.For_Month between '"+BOM[0]+"' AND '"+EOM[2]+"' "
					+ "and t.Financial_Year='"+from_to_year[0]+"' ";
			System.out.println("date query....."+challndetails);
			ResultSet challandetails = st2.executeQuery(challndetails);
			if (challandetails.next()){
				challandetails.previous();
				int i=0;
				while(challandetails.next()){
					month2[i] = challandetails.getString("challan_month");
					Challan_No[i]   = challandetails.getString("challan_no");
					Branch_code[i]   = challandetails.getString("Branch_Code");
					i++;
				}
			}
		FileOutputStream out1 = new FileOutputStream(new File(filepath));
		HSSFWorkbook hwb=new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("FORM-24: "+year);
		
		HSSFCellStyle my_style = hwb.createCellStyle();
	    HSSFCellStyle my_style1 = hwb.createCellStyle();

	       HSSFFont my_font=hwb.createFont();
	       my_font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	       my_style.setFont(my_font);
	     
	       
			HSSFRow rowtitle=   sheet.createRow((short)0);
			HSSFCell cell = rowtitle.createCell((short) 7);
			
		//	System.out.println("2222.....");
			
			cell.setCellValue(prop.getProperty("bankName1"));
			cell.setCellStyle(my_style);
			HSSFRow rowtitle1=   sheet.createRow((short)1);
			HSSFCell cell1 = rowtitle1.createCell((short) 6);
			cell1.setCellValue(prop.getProperty("addressForReport"));
			cell1.setCellStyle(my_style);
			HSSFRow rowtitle2=   sheet.createRow((short)2);
			HSSFCell cell2 = rowtitle2.createCell((short) 7);
			cell2.setCellValue(prop.getProperty("contactForReport"));
			cell2.setCellStyle(my_style);
			HSSFRow rowtitle31=   sheet.createRow((short)3);
			cell2 = rowtitle31.createCell((short) 7);
			cell2.setCellValue(	prop.getProperty("mailForReport"));
			cell2.setCellStyle(my_style);
			HSSFRow rowtitle3=   sheet.createRow((short)4);
			cell2=rowtitle3.createCell((short) 7);
			cell2.setCellValue("Employee Eligible List For Income Tax Cutting for "+from_to_year[0]+" - "+from_to_year[1]);
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
		
		if(emptype.equalsIgnoreCase("active") && branchtyp.equalsIgnoreCase("all"))
			{
				System.out.println("query1");
				query=("WITH A AS ( "
						+ "SELECT  E.EMPNO,E.EMPCODE,RTRIM(E.LNAME)+' '+ RTRIM(E.FNAME) +' '+ RTRIM(E.MNAME) AS EMP_NAME,E.DOJ, "
						+ "L.LKP_DISC AS DESIGNATION,ISNULL(E.PANNO,'--') as PAN_NO, "
						+ "ISNULL((SELECT CONVERT(NUMERIC(10,2),AVG(ISNULL(P.INP_AMT,0))) FROM PAYTRAN_STAGE P WHERE "
						+ "P.EMPNO=E.EMPNO AND P.TRNCD=999 AND P.TRNDT BETWEEN '"+BOM[0]+"' AND '"+EOM[2]+"' ),0) AS MONTHLY_INCOME, "
						+ "ISNULL((SELECT ISNULL(NET_AMT,0) FROM PAYTRAN_STAGE P WHERE "
						+ "P.EMPNO=E.EMPNO AND P.TRNCD=228 AND P.TRNDT BETWEEN '"+BOM[0]+"' AND '"+EOM[0]+"' ),0) AS "+month1[0]+", "
						+ "ISNULL((SELECT ISNULL(NET_AMT,0) FROM PAYTRAN_STAGE P WHERE "
						+ "P.EMPNO=E.EMPNO AND P.TRNCD=228 AND P.TRNDT BETWEEN '"+BOM[1]+"' AND '"+EOM[1]+"' ),0) AS "+month1[1]+", "
						+ "ISNULL((SELECT ISNULL(NET_AMT,0) FROM PAYTRAN_STAGE P WHERE "
						+ "P.EMPNO=E.EMPNO AND P.TRNCD=228 AND P.TRNDT BETWEEN '"+BOM[2]+"' AND '"+EOM[2]+"' ),0) AS "+month1[2]+",ISNULL(RTRIM(U.ADDR1),'')+' '+ISNULL(RTRIM(U.ADDR2),'')+' '+ISNULL(RTRIM(U.PIN),'') AS ADDRESS FROM EMPMAST E,EMPTRAN T,LOOKUP L,EMPAUX U "
						+ "WHERE E.EMPNO = T.EMPNO AND E.STATUS='A' AND "
						+ " T.SRNO=(SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO ) AND "
						+ " U.ID = (SELECT MIN(ID) FROM EMPAUX U1 WHERE U1.EMPNO = E.EMPNO) AND "
						+ " L.LKP_SRNO = T.DESIG AND L.LKP_CODE='DESIG' ) " +
						" SELECT * FROM A WHERE ("+month1[0]+">0 or "+month1[1]+">0 or "+month1[2]+">0) order by A.EMPCODE " );
			}
			else if(emptype.equalsIgnoreCase("non-active") && branchtyp.equalsIgnoreCase("all"))
			{
				System.out.println("query2");
				query=("WITH A AS ( "
						+ "SELECT  E.EMPNO,E.EMPCODE,RTRIM(E.LNAME)+' '+ RTRIM(E.FNAME) +' '+ RTRIM(E.MNAME) AS EMP_NAME,E.DOJ, "
						+ "L.LKP_DISC AS DESIGNATION,ISNULL(E.PANNO,'--') as PAN_NO, "
						+ "ISNULL((SELECT CONVERT(NUMERIC(10,2),AVG(ISNULL(P.INP_AMT,0))) FROM PAYTRAN_STAGE P WHERE "
						+ "P.EMPNO=E.EMPNO AND P.TRNCD=999 AND P.TRNDT BETWEEN '"+BOM[0]+"' AND '"+EOM[2]+"' ),0) AS MONTHLY_INCOME, "
						+ "ISNULL((SELECT ISNULL(NET_AMT,0) FROM PAYTRAN_STAGE P WHERE "
						+ "P.EMPNO=E.EMPNO AND P.TRNCD=228 AND P.TRNDT BETWEEN '"+BOM[0]+"' AND '"+EOM[0]+"' ),0) AS "+month1[0]+", "
						+ "ISNULL((SELECT ISNULL(NET_AMT,0) FROM PAYTRAN_STAGE P WHERE "
						+ "P.EMPNO=E.EMPNO AND P.TRNCD=228 AND P.TRNDT BETWEEN '"+BOM[1]+"' AND '"+EOM[1]+"' ),0) AS "+month1[1]+", "
						+ "ISNULL((SELECT ISNULL(NET_AMT,0) FROM PAYTRAN_STAGE P WHERE "
						+ "P.EMPNO=E.EMPNO AND P.TRNCD=228 AND P.TRNDT BETWEEN '"+BOM[2]+"' AND '"+EOM[2]+"' ),0) AS "+month1[2]+",ISNULL(RTRIM(U.ADDR1),'')+' '+ISNULL(RTRIM(U.ADDR2),'')+' '+ISNULL(RTRIM(U.PIN),'') AS ADDRESS FROM EMPMAST E,EMPTRAN T,LOOKUP L,EMPAUX U "
						+ "WHERE E.EMPNO = T.EMPNO AND E.STATUS='N' AND E.DOL!='' AND "
						+ " T.SRNO=(SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO ) AND "
						+ " U.ID = (SELECT MIN(ID) FROM EMPAUX U1 WHERE U1.EMPNO = E.EMPNO) AND "
						+ " L.LKP_SRNO = T.DESIG AND L.LKP_CODE='DESIG' ) " +
						" SELECT * FROM A WHERE ("+month1[0]+">0 or "+month1[1]+">0 or "+month1[2]+">0) order by A.EMPCODE " );
			}
			else if (emptype.equalsIgnoreCase("all") && branchtyp.equalsIgnoreCase("all"))
			{
				System.out.println("query3");
				query=("WITH A AS ( "
						+ "SELECT  E.EMPNO,E.EMPCODE,RTRIM(E.LNAME)+' '+ RTRIM(E.FNAME) +' '+ RTRIM(E.MNAME) AS EMP_NAME,E.DOJ, "
						+ "L.LKP_DISC AS DESIGNATION,ISNULL(E.PANNO,'--') as PAN_NO, "
						+ "ISNULL((SELECT CONVERT(NUMERIC(10,2),AVG(ISNULL(P.INP_AMT,0))) FROM PAYTRAN_STAGE P WHERE "
						+ "P.EMPNO=E.EMPNO AND P.TRNCD=999 AND P.TRNDT BETWEEN '"+BOM[0]+"' AND '"+EOM[2]+"' ),0) AS MONTHLY_INCOME, "
						+ "ISNULL((SELECT ISNULL(NET_AMT,0) FROM PAYTRAN_STAGE P WHERE "
						+ "P.EMPNO=E.EMPNO AND P.TRNCD=228 AND P.TRNDT BETWEEN '"+BOM[0]+"' AND '"+EOM[0]+"' ),0) AS "+month1[0]+", "
						+ "ISNULL((SELECT ISNULL(NET_AMT,0) FROM PAYTRAN_STAGE P WHERE "
						+ "P.EMPNO=E.EMPNO AND P.TRNCD=228 AND P.TRNDT BETWEEN '"+BOM[1]+"' AND '"+EOM[1]+"' ),0) AS "+month1[1]+", "
						+ "ISNULL((SELECT ISNULL(NET_AMT,0) FROM PAYTRAN_STAGE P WHERE "
						+ "P.EMPNO=E.EMPNO AND P.TRNCD=228 AND P.TRNDT BETWEEN '"+BOM[2]+"' AND '"+EOM[2]+"' ),0) AS "+month1[2]+","
								+ "(select Isnull(Rtrim(U.addr1), '') + ' '" + 
								"                + Isnull(Rtrim(U.addr2), '') + ' '" + 
								"                + Isnull(Rtrim(U.pin), '')" + 
								"                AS ADDRESS from empaux U where U.EMPNO=E.EMPNO and U.id = (SELECT isnull(Min(id),1) " + 
								"                            FROM   empaux U1 " + 
								"                            WHERE  U1.empno = E.empno)) as ADDRESS FROM EMPMAST E,EMPTRAN T,LOOKUP L "
						+ "WHERE E.EMPNO = T.EMPNO AND ( ( E.status = 'A' AND E.doj <= '"+EOM[2]+"' )OR ( E.status = 'N' AND E.dol >= '"+BOM[0]+"' ) ) AND "
						+ " T.SRNO=(SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO ) AND "
						+ " L.LKP_SRNO = T.DESIG AND L.LKP_CODE='DESIG' ) " +
						" SELECT * FROM A WHERE ("+month1[0]+">0 or "+month1[1]+">0 or "+month1[2]+">0) order by A.EMPCODE " );
			}
			else if(emptype.equalsIgnoreCase("active") && (!branchtyp.equalsIgnoreCase("all")))
			{
				System.out.println("query4");
				query = ("WITH A AS ( "
						+ "SELECT  E.EMPNO,E.EMPCODE,RTRIM(E.LNAME)+' '+ RTRIM(E.FNAME) +' '+ RTRIM(E.MNAME) AS EMP_NAME,E.DOJ, "
						+ "L.LKP_DISC AS DESIGNATION,ISNULL(E.PANNO,'--') as PAN_NO, "
						+ "ISNULL((SELECT CONVERT(NUMERIC(10,2),AVG(ISNULL(P.INP_AMT,0))) FROM PAYTRAN_STAGE P WHERE "
						+ "P.EMPNO=E.EMPNO AND P.TRNCD=999 AND P.TRNDT BETWEEN '"+BOM[0]+"' AND '"+EOM[2]+"' ),0) AS MONTHLY_INCOME, "
						+ "ISNULL((SELECT ISNULL(NET_AMT,0) FROM PAYTRAN_STAGE P WHERE "
						+ "P.EMPNO=E.EMPNO AND P.TRNCD=228 AND P.TRNDT BETWEEN '"+BOM[0]+"' AND '"+EOM[0]+"' ),0) AS "+month1[0]+", "
						+ "ISNULL((SELECT ISNULL(NET_AMT,0) FROM PAYTRAN_STAGE P WHERE "
						+ "P.EMPNO=E.EMPNO AND P.TRNCD=228 AND P.TRNDT BETWEEN '"+BOM[1]+"' AND '"+EOM[1]+"' ),0) AS "+month1[1]+", "
						+ "ISNULL((SELECT ISNULL(NET_AMT,0) FROM PAYTRAN_STAGE P WHERE "
						+ "P.EMPNO=E.EMPNO AND P.TRNCD=228 AND P.TRNDT BETWEEN '"+BOM[2]+"' AND '"+EOM[2]+"' ),0) AS "+month1[2]+",ISNULL(RTRIM(U.ADDR1),'')+' '+ISNULL(RTRIM(U.ADDR2),'')+' '+ISNULL(RTRIM(U.PIN),'') AS ADDRESS FROM EMPMAST E,EMPTRAN T,LOOKUP L,EMPAUX U "
						+ "WHERE E.EMPNO = T.EMPNO AND E.STATUS='A' AND "
						+ " T.SRNO=(SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO ) AND "
						+ " U.ID = (SELECT MIN(ID) FROM EMPAUX U1 WHERE U1.EMPNO = E.EMPNO) AND "
						+ " L.LKP_SRNO = T.DESIG AND L.LKP_CODE='DESIG' AND T.PRJ_SRNO='999' ) " +
						" SELECT * FROM A WHERE ("+month1[0]+">0 or "+month1[1]+">0 or "+month1[2]+">0) order by A.EMPCODE " );
			}
			else if(emptype.equalsIgnoreCase("non-active") && (!branchtyp.equalsIgnoreCase("all")))
			{
				System.out.println("query5");
				query = ("WITH A AS ( "
						+ "SELECT  E.EMPNO,E.EMPCODE,RTRIM(E.LNAME)+' '+ RTRIM(E.FNAME) +' '+ RTRIM(E.MNAME) AS EMP_NAME,E.DOJ, "
						+ "L.LKP_DISC AS DESIGNATION,ISNULL(E.PANNO,'--') as PAN_NO, "
						+ "ISNULL((SELECT CONVERT(NUMERIC(10,2),AVG(ISNULL(P.INP_AMT,0))) FROM PAYTRAN_STAGE P WHERE "
						+ "P.EMPNO=E.EMPNO AND P.TRNCD=999 AND P.TRNDT BETWEEN '"+BOM[0]+"' AND '"+EOM[2]+"' ),0) AS MONTHLY_INCOME, "
						+ "ISNULL((SELECT ISNULL(NET_AMT,0) FROM PAYTRAN_STAGE P WHERE "
						+ "P.EMPNO=E.EMPNO AND P.TRNCD=228 AND P.TRNDT BETWEEN '"+BOM[0]+"' AND '"+EOM[0]+"' ),0) AS "+month1[0]+", "
						+ "ISNULL((SELECT ISNULL(NET_AMT,0) FROM PAYTRAN_STAGE P WHERE "
						+ "P.EMPNO=E.EMPNO AND P.TRNCD=228 AND P.TRNDT BETWEEN '"+BOM[1]+"' AND '"+EOM[1]+"' ),0) AS "+month1[1]+", "
						+ "ISNULL((SELECT ISNULL(NET_AMT,0) FROM PAYTRAN_STAGE P WHERE "
						+ "P.EMPNO=E.EMPNO AND P.TRNCD=228 AND P.TRNDT BETWEEN '"+BOM[2]+"' AND '"+EOM[2]+"' ),0) AS "+month1[2]+",ISNULL(RTRIM(U.ADDR1),'')+' '+ISNULL(RTRIM(U.ADDR2),'')+' '+ISNULL(RTRIM(U.PIN),'') AS ADDRESS FROM EMPMAST E,EMPTRAN T,LOOKUP L,EMPAUX U "
						+ "WHERE E.EMPNO = T.EMPNO AND E.STATUS='N' AND E.DOL!='' AND "
						+ " T.SRNO=(SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO ) AND "
						+ " U.ID = (SELECT MIN(ID) FROM EMPAUX U1 WHERE U1.EMPNO = E.EMPNO) AND "
						+ " L.LKP_SRNO = T.DESIG AND L.LKP_CODE='DESIG' AND T.PRJ_SRNO='999' ) " +
						" SELECT * FROM A WHERE ("+month1[0]+">0 or "+month1[1]+">0 or "+month1[2]+">0) order by A.EMPCODE " );
			}
			else if(emptype.equalsIgnoreCase("all") && (!branchtyp.equalsIgnoreCase("all")))
			{
				System.out.println("query6");
				query = ("WITH A AS ( "
						+ "SELECT  E.EMPNO,E.EMPCODE,RTRIM(E.LNAME)+' '+ RTRIM(E.FNAME) +' '+ RTRIM(E.MNAME) AS EMP_NAME,E.DOJ, "
						+ "L.LKP_DISC AS DESIGNATION,ISNULL(E.PANNO,'--') as PAN_NO, "
						+ "ISNULL((SELECT CONVERT(NUMERIC(10,2),AVG(ISNULL(P.INP_AMT,0))) FROM PAYTRAN_STAGE P WHERE "
						+ "P.EMPNO=E.EMPNO AND P.TRNCD=999 AND P.TRNDT BETWEEN '"+BOM[0]+"' AND '"+EOM[2]+"' ),0) AS MONTHLY_INCOME, "
						+ "ISNULL((SELECT ISNULL(NET_AMT,0) FROM PAYTRAN_STAGE P WHERE "
						+ "P.EMPNO=E.EMPNO AND P.TRNCD=228 AND P.TRNDT BETWEEN '"+BOM[0]+"' AND '"+EOM[0]+"' ),0) AS "+month1[0]+", "
						+ "ISNULL((SELECT ISNULL(NET_AMT,0) FROM PAYTRAN_STAGE P WHERE "
						+ "P.EMPNO=E.EMPNO AND P.TRNCD=228 AND P.TRNDT BETWEEN '"+BOM[1]+"' AND '"+EOM[1]+"' ),0) AS "+month1[1]+", "
						+ "ISNULL((SELECT ISNULL(NET_AMT,0) FROM PAYTRAN_STAGE P WHERE "
						+ "P.EMPNO=E.EMPNO AND P.TRNCD=228 AND P.TRNDT BETWEEN '"+BOM[2]+"' AND '"+EOM[2]+"' ),0) AS "+month1[2]+",ISNULL(RTRIM(U.ADDR1),'')+' '+ISNULL(RTRIM(U.ADDR2),'')+' '+ISNULL(RTRIM(U.PIN),'') AS ADDRESS FROM EMPMAST E,EMPTRAN T,LOOKUP L,EMPAUX U "
						+ "WHERE E.EMPNO = T.EMPNO AND ((E.STATUS='A' ) OR (E.STATUS='N' AND E.DOL!='')) AND "
						+ " T.SRNO=(SELECT MAX(SRNO) FROM EMPTRAN T1 WHERE T1.EMPNO = E.EMPNO ) AND "
						+ " U.ID = (SELECT MIN(ID) FROM EMPAUX U1 WHERE U1.EMPNO = E.EMPNO) AND "
						+ " L.LKP_SRNO = T.DESIG AND L.LKP_CODE='DESIG' AND T.PRJ_SRNO='999' ) " +
						" SELECT * FROM A WHERE ("+month1[0]+">0 or "+month1[1]+">0 or "+month1[2]+">0) order by A.EMPCODE " );

			}
		
		
		/*
		Statement stdt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);*/
		
		int total[] = new int[5];
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
			if(j==1)
				cell3.setCellValue(""+"SRNO");
			else
			cell3.setCellValue(""+rsmd.getColumnName(j));
			cell3.setCellStyle(my_style1);
		}
		
		while(emp.next())
		{
			k++;
			
			HSSFRow row = sheet.createRow((short)i++);
			
			for (j=1;j<=columnsNumber;j++)
			{
				if(j==1)
					row.createCell((short) j).setCellValue(""+k);
				else{
					row.createCell((short) j).setCellValue(""+emp.getString(rsmd.getColumnName(j)));
					if(month1[0].equalsIgnoreCase(rsmd.getColumnName(j))){
						total[0]+=emp.getInt(rsmd.getColumnName(j));
					}else if(month1[1].equalsIgnoreCase(rsmd.getColumnName(j))){
						total[1]+=emp.getInt(rsmd.getColumnName(j));
					}else if(month1[2].equalsIgnoreCase(rsmd.getColumnName(j))){
						total[2]+=emp.getInt(rsmd.getColumnName(j));
					}
						
				}
			}
			
		}
			
		
		String columns[]={"Month ("+from_to_year[0]+")","TDS Amount","Challan No","BSR Code"};
		HSSFRow row1 = sheet.createRow((short) i);
		row1.createCell((short) 0).setCellValue(" ");
		
		 		row1 = sheet.createRow((short) i+1);
		 		cell3=row1.createCell((short) 7);
				cell3.setCellValue(""+"TOTAL : ");
				cell3.setCellStyle(my_style1);
			int n=0;
			for(int l=8;l<=10;l++)
			{
				cell3=row1.createCell((short) l);
				cell3.setCellValue(""+total[n]);
				cell3.setCellStyle(my_style1);
				n++;
			}
		  row1 = sheet.createRow((short) i+2);
	//	row.createCell((short) 0).setCellValue("Report Date And Time= " +df.format(calobj.getTime()));
		
		row1.createCell((short) 1).setCellValue("Total Employee : =  " +k );
		int m=4;
		 row1 = sheet.createRow((short) i+4);
		for(j=0;j<columns.length;j++)
		{
			sheet.setColumnWidth((short)m, (short)6000);
			cell3=row1.createCell((short) m);
			cell3.setCellValue(""+columns[j]);
			cell3.setCellStyle(my_style1);
			//row1.createCell((short) m).setCellValue(""+columns[j]);
			//row1 = sheet.createRow((short) i+1);
			//row1.createCell((short) 1).setCellValue(j);
			m++;
		}
		
		
		
		//String summery[][] = new String [5][5];
		int row=i;
		for(j=0;j<month1.length;j++)
		{
			
			row1 = sheet.createRow((short) row+6);
			m=4;
			for(int l=0;l<columns.length;l++)
			{
				
				sheet.setColumnWidth((short)m, (short)6000);
				cell3=row1.createCell((short) m);
				if(l==0)
				cell3.setCellValue(""+month1[j]);
				else if (l==1)
					cell3.setCellValue(""+total[j]);
				else if (l==2 && (month1[j].equalsIgnoreCase(month2[j])))
					cell3.setCellValue(""+Challan_No[j]);
				else
					cell3.setCellValue(""+Branch_code[j]);
				//cell3.setCellStyle(my_style1);
				//row1.createCell((short) m).setCellValue(""+columns[j]);
				//row1 = sheet.createRow((short) i+1);
				//row1.createCell((short) 1).setCellValue(j);
				m++;
			}
			row++;
			
			
		}
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
}
