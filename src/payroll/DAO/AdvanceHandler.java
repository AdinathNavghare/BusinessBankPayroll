package payroll.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import payroll.Core.Calculate;
import payroll.Core.ReportDAO;
import payroll.Core.UtilityDAO;
import payroll.Core.UtilityDAO.Footer1;
import payroll.Model.AdvanceBean;
import payroll.Model.AdvanceBean;
import payroll.Model.EmpOffBean;
import payroll.Model.EmployeeBean;
import payroll.Model.LeaveBalBean;
import payroll.Model.Lookup;
import payroll.Model.TranBean;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.swing.border.Border;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.jfree.ui.Align;


import payroll.DAO.BankStmntHandler;
import payroll.DAO.CodeMasterHandler;
import payroll.DAO.ConnectionManager;
import payroll.DAO.EmpOffHandler;
import payroll.DAO.GradeHandler;
import payroll.DAO.LookupHandler;
import payroll.DAO.TranHandler;
import payroll.Model.BankStmntBean;
import payroll.Model.PFExcelBean;
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
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.GrayColor;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

public class AdvanceHandler {

	
	public String macAddress(){
		 StringBuilder sb = new StringBuilder();
			try {
			
			    InetAddress ip = InetAddress.getLocalHost();
			//    System.out.println("Current IP address : " + ip.getHostAddress());

			    Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
			    while(networks.hasMoreElements()) {
			      NetworkInterface network = networks.nextElement();
			      byte[] mac = network.getHardwareAddress();

			      if(mac != null) {
			      //  System.out.print("Current MAAAAC address : ");

			   
			        for (int i = 0; i < mac.length; i++) {
			          sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			        }
			 //       System.out.println(sb.toString());
			      }
			    }
			  } catch (UnknownHostException e) {
			    e.printStackTrace();
			  } catch (SocketException e){
			    e.printStackTrace();
			  }
		
			return sb.toString();
	}
	 
	
  
	
	
	static String lable1="";

public	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
EmpAttendanceHandler EAH=new EmpAttendanceHandler();
String currentdate=EAH.getServerDate();
	
public boolean addAdvance(AdvanceBean bean) {
		boolean result = true;
	    
		
		
		
		try {
			Connection con = ConnectionManager.getConnection();
			/*String requestMacId=macAddress();*/
			
           /*System.out.println("on method call  "+requestMacId);*/
			String insertquery = "INSERT INTO ADV_PAY_REQUESTED (EMPNO,REQUEST_DATE,ADV_AMT_REQUESTED,REQUEST_STATUS," +
					                          "PRJ_SRNO,FOR_MONTH,CREATED_BY,CREATED_DATE) values(?,?,?,?,?,?,?,?)";
			PreparedStatement Pstat = con.prepareStatement(insertquery);
			Pstat.setInt(1, bean.getEmpNo());
			Pstat.setString(2, bean.getRequestDate());
			Pstat.setInt(3, bean.getAdvanceAmtRequested());
			Pstat.setString(4, "PENDING");
			Pstat.setInt(5,bean.getSite_id());
			Pstat.setString(6, bean.getForMonth());
			Pstat.setInt(7, bean.getSanctionBy());
			Pstat.setString(8, currentdate);
			/*Pstat.setString(9, requestMacId);*/
			Pstat.executeUpdate();
			result = true;
			Pstat.close();

	
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			 result=false;
		}
		return result;

	}
	
	
	public ArrayList<AdvanceBean> advanceDisplay(int empno1, String advanceType,String site,String roleId) {
		ResultSet rs = null;
		//System.out.println("SITE ID    :"+site_id);
		ArrayList<AdvanceBean> List1 = new ArrayList<AdvanceBean>();
		try {
			String query = "";
			int name = 0;
			int site_id=Integer.parseInt(site);
			ResultSet rslist1 = null;
			float payable=0.0f;
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			Statement stmt2 = con.createStatement();
			LookupHandler objDesc = new LookupHandler();
			String Result1;
		
		{
			if (advanceType.equalsIgnoreCase("PENDING")) {
				query = "SELECT * FROM ADV_PAY_REQUESTED   WHERE REQUEST_STATUS = 'PENDING' and " +
						"PRJ_SRNO ="+site_id+" order by APPLNO desc";
			}
			
			
			else if (advanceType.equalsIgnoreCase("SANCTION")) {
				query = "SELECT * FROM ADV_PAY_REQUESTED   WHERE  REQUEST_STATUS = 'SANCTION' and " +
						"PRJ_SRNO ="+site_id+" order by APPLNO desc";
			} 
			else if (advanceType.equalsIgnoreCase("CANCEL")) {
				query = "SELECT * FROM ADV_PAY_REQUESTED   WHERE  REQUEST_STATUS = 'CANCEL' and " +
						"PRJ_SRNO ="+site_id+" order by APPLNO desc";
			}
			else if (advanceType.equalsIgnoreCase("APPROVED")) {
				query = "SELECT * FROM ADV_PAY_REQUESTED   WHERE REQUEST_STATUS = 'APPROVED' and " +
						"PRJ_SRNO ="+site_id+" order by APPLNO desc";
			}
			else
				query = "SELECT * FROM ADV_PAY_REQUESTED   WHERE "
						
						+ " REQUEST_STATUS !='1' and PRJ_SRNO ="+site_id+" order by APPLNO desc";
		}		
			rs = st.executeQuery(query);

			while (rs.next()) {
				AdvanceBean abean1 = new AdvanceBean();
				
				abean1.setEmpNo(rs.getInt("EMPNO"));

				abean1.setRequestDate(rs.getString("REQUEST_DATE") == null ? "" : sdf.format( rs.getDate("REQUEST_DATE")));
				abean1.setAdvanceAmtRequested(rs.getInt("ADV_AMT_REQUESTED"));
				abean1.setSanctionBy(rs.getInt("SANCTION_BY"));
				abean1.setSanctionDate(rs.getString("SANCTION_DATE") == null ? "" : sdf.format(rs.getDate("SANCTION_DATE")));
				abean1.setPostDateInAcc(rs.getString("POST_DATE_IN_ACC") == null ? "" : rs.getString("POST_DATE_IN_ACC"));
				abean1.setRequestStatus(rs.getString("REQUEST_STATUS") == null ? "" : rs.getString("REQUEST_STATUS"));
				abean1.setApplNo(rs.getInt("APPLNO"));
			   abean1.setSanctionAmt(rs.getInt("SANCTION_AMT"));
			   abean1.setForMonth(rs.getString("FOR_MONTH") == null ? "" : rs.getString("FOR_MONTH"));
			  
			   String mnth="";
			   ResultSet rs1=null;
			mnth=sdf.format(rs.getDate(11));
			 // name = (rs.getInt(3));
				rslist1=stmt2.executeQuery("select SUM(NET_AMT) as PAYABLE from paytran_stage where empno="+rs.getInt(1)+" and TRNCD=999 and   status='F' and trndt between '"+ReportDAO.BOM(mnth)+"' and '"+ReportDAO.EOM(mnth)+"'");
				if(rslist1.next()){
					ResultSet rs11=null;
					int Sanction_amt=0;
					Statement stmt3 = con.createStatement();
					
					rs11=stmt3.executeQuery("select SUM(SANCTION_AMT) as sanction_amt from ADV_PAY_REQUESTED where EMPNO="+rs.getInt(1)+" and REQUEST_STATUS='SANCTION' and FOR_MONTH between '"+ReportDAO.BOM(mnth)+"' and '"+ReportDAO.EOM(mnth)+"'");
					while(rs11.next())
					{
					Sanction_amt+=	rs11.getInt("sanction_amt");
					}
					
					
					payable=( rslist1.getFloat("PAYABLE"))-Sanction_amt;
					
				}
					

			abean1.setPayable(payable);
			   
			   
			   
			   
			   abean1.setVoucher_No(rs.getInt(13));
				name = (rs.getInt(3));
	//			System.out.println("!!!!!!!__________________" + name);
			
				List1.add(abean1);

			}

			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return List1;
	}

	
	public static String dateFormat(Date dates) {
		String result = "";
		if (dates == null) {
			result = "";
			return result;
		}
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
		result = format.format(dates);
		return result;
	}

	public ArrayList<AdvanceBean> getAdvanceAppList(AdvanceBean lBean,String searchType) {
		
		ArrayList<AdvanceBean> list = new ArrayList<>();
		String searchQuery = null;
		Connection con = ConnectionManager.getConnection();

	//	System.out.println("TYPE"   +searchType);
		  if(searchType.equalsIgnoreCase("custom")){ 
			  String status = "";
		 
		  if(lBean.getRequestStatus().equalsIgnoreCase("ALL")){
		 
		//	  System.out.println("if"); 
		
		   searchQuery= "SELECT  *  FROM ADV_PAY_REQUESTED WHERE EMPNO = " +
		  " '"+lBean.getEmpNo()+"'   AND  REQUEST_DATE " +
		  " between '"+lBean.getRequestDate()+"' AND '"+lBean.getRequestDate2()+"' order by APPLNO desc ";
		   }
		 
		  else
		  {
			  
		  status = " '"+lBean.getRequestStatus()+"'"; 
		  searchQuery=  "SELECT * FROM ADV_PAY_REQUESTED WHERE " +
		  				"EMPNO ="+lBean.getEmpNo()+" " +
		  				" AND  REQUEST_DATE  between '"+lBean.getRequestDate()+"' AND '"+lBean.getRequestDate2()+"' and REQUEST_STATUS="+status+"  order by APPLNO desc ";
		  
	//	  System.out.println("else"); System.out.println(searchQuery);
		  }
		  }
		 else {
			  
		//	  System.out.println("if else part of the custom advance search"); 
		  searchQuery="SELECT * FROM ADV_PAY_REQUESTED WHERE REQUEST_STATUS='"+lBean.getRequestStatus()+"' order by APPLNO desc " ;
		   

		  }
		   
		try {
			ResultSet rslist = null;
			Statement stmt1 = con.createStatement();
			Statement stmt2 = con.createStatement();
			ResultSet rslist1 = null;
			float payable=0.0f;
	//		  System.out.println("if else part after the query"+searchQuery); 
			rslist = stmt1.executeQuery(searchQuery);
			AdvanceBean aBean = null;

			while (rslist.next()) {
				aBean = new AdvanceBean();
				
				
				aBean.setEmpNo(rslist.getInt("EMPNO"));
				

				aBean.setRequestDate(rslist.getString("REQUEST_DATE") == null ? "" :sdf.format( rslist.getDate("REQUEST_DATE")));
				aBean.setAdvanceAmtRequested(rslist.getInt("ADV_AMT_REQUESTED"));
				
				aBean.setSanctionDate(rslist.getString("SANCTION_DATE")==null?"":sdf.format(rslist.getDate("SANCTION_DATE")));
				aBean.setRequestStatus(rslist.getString("REQUEST_STATUS") == null ? "" : rslist.getString("REQUEST_STATUS"));
				aBean.setApplNo(rslist.getInt("APPLNO"));
				aBean.setSanctionAmt(rslist.getInt("SANCTION_AMT"));
				aBean.setForMonth(rslist.getString("FOR_MONTH") == null ? "" : rslist.getString("FOR_MONTH"));
			    

				   String mnth="";
				   ResultSet rs=null;
				mnth=sdf.format(rslist.getDate("FOR_MONTH"));
				 // name = (rs.getInt(3));
					rslist1=stmt2.executeQuery("select SUM(NET_AMT) as PAYABLE from paytran_stage where empno="+rslist.getInt("EMPNO")+" and TRNCD=999 and   status='F' and trndt between '"+ReportDAO.BOM(mnth)+"' and '"+ReportDAO.EOM(mnth)+"'");
					if(rslist1.next()){
						ResultSet rs11=null;
						int Sanction_amt=0;
						Statement stmt3 = con.createStatement();
						
						rs11=stmt3.executeQuery("select SUM(SANCTION_AMT) as sanction_amt from ADV_PAY_REQUESTED where EMPNO="+rslist.getInt("EMPNO")+" and REQUEST_STATUS='SANCTION' and FOR_MONTH between '"+ReportDAO.BOM(mnth)+"' and '"+ReportDAO.EOM(mnth)+"'");
						while(rs11.next())
						{
						Sanction_amt+=	rs11.getInt("sanction_amt");
						}
						
						
						payable=( rslist1.getFloat("PAYABLE"))-Sanction_amt;
						
					}
						

				aBean.setPayable(payable);
				aBean.setVoucher_No(rslist.getInt(13));				 

				list.add(aBean);
				payable=0.0f;
			}
			rslist.close();
			stmt1.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/*
	 * This method has written for sanctioning advance application. It updates
	 * status in adv_pay_requested table
	 */
	public boolean setSanction(int empno, int appNo, int amount,int eno) throws SQLException {
		
		
	/*	String requestMacId=macAddress();
*/
		Connection con = ConnectionManager.getConnection();
		/*String sql = "UPDATE ADV_PAY_REQUESTED SET REQUEST_STATUS='SANCTION' WHERE EMPNO="
				+ empno + " AND APPLNO=" + appNo;*/

		String sql = "UPDATE ADV_PAY_REQUESTED SET REQUEST_STATUS='SANCTION' ,SANCTION_BY="+eno+", " +
				"SANCTION_DATE= '"+currentdate+"', Sanction_Amt = "+amount+"  ,  " +
				"SANC_SESSION_ID = "+eno+"  WHERE APPLNO=" + appNo;
		
		
		boolean flag = false;

		try {
			Statement stmt = con.createStatement();
				stmt.executeUpdate(sql);
				flag = true;
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("value of flag in haNDLER "+flag);
		return flag;
	}

	public boolean cancelAdvanceApp(int empno, int appNo,int eno) {

		boolean flag = false;
	/*	String requestMacId=macAddress();*/
		 SimpleDateFormat dateFormat;
		 dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		 Date date = new Date();
		 String currentdate = dateFormat.format(date);  
		Connection conn = ConnectionManager.getConnection();
		String sql = "UPDATE ADV_PAY_REQUESTED SET REQUEST_STATUS='CANCEL' ,SANCTION_BY="+eno+", " +
				"SANCTION_DATE= '"+currentdate+"', SANC_SESSION_ID = "+eno+" " +
				" WHERE APPLNO=" + appNo;
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);

			stmt.close();
			conn.close();

			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public String getDateMethod() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date date = new Date();
		String yourDate = dateFormat.format(date);
		return yourDate;
	}

	


	
public ArrayList<AdvanceBean> getEmpList(String prjCode){
		
		ArrayList<AdvanceBean> list= new ArrayList<AdvanceBean>();
		Connection con = null;
		ResultSet rs = null;
		
		//String query = "SELECT EMPNO FROM EMPTRAN WHERE PRJ_CODE='"+prjCode+"'";
		//String query = "select emp.EMPNO from EMPMAST emp inner join EMPTRAN etn on emp.EMPNO = etn.EMPNO where emp.STATUS = 'A'  and etn.PRJ_SRNO="+prjCode;
		
//		String query = "select emp.EMPNO from EMPMAST emp inner join EMPTRAN etn on emp.EMPNO = etn.EMPNO where emp.STATUS = 'A' and etn.PRJ_SRNO='"+prjCode+"' and etn.SRNO = (select MAX(en.SRNO) from EMPTRAN en where en.PRJ_SRNO = '"+prjCode+"' and etn.EMPNO = en.EMPNO )";
		String query = "select emp.EMPNO,emp.EMPCODE, emp.FNAME+' '+emp.MNAME+' '+emp.LNAME as NAME from EMPMAST emp inner join EMPTRAN etn on emp.EMPNO = etn.EMPNO where emp.STATUS = 'A' and etn.PRJ_SRNO="+prjCode+"  AND etn.SRNO=(SELECT MAX(ET.SRNO) FROM EMPTRAN ET WHERE ET.EMPNO=emp.EMPNO) order by emp.EMPNO";
		
		con = ConnectionManager.getConnection();
		try
		{
			Statement st = con.createStatement();
			rs = st.executeQuery(query);
			while(rs.next())
			{
				AdvanceBean adbn = new AdvanceBean();
				adbn.setEmpNo(rs.getInt("EMPNO"));
				adbn.setEmpcode(rs.getInt("EMPCODE"));
				adbn.setName(rs.getString("NAME"));
				
			
				list.add(adbn);
			}
			rs.close();
			st.close();
			con.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return list;
	}
	


public float getEmpAdvance(String empno,String date)
{
	float ADVANCE=0.0f;
	
	try
	{
	Connection con = ConnectionManager.getConnection();
	ResultSet rs = null;
	
	
		String query="SELECT SUM(SANCTION_AMT) AS ADVANCE FROM ADV_PAY_REQUESTED WHERE EMPNO="+empno+" AND REQUEST_STATUS='SANCTION' And VOUCHER_ID !=0 AND FOR_MONTH BETWEEN '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"'";
		Statement st = con.createStatement();
		rs = st.executeQuery(query);
		if(rs.next())
		{
			ADVANCE=rs.getString("ADVANCE")==null?0.0f:rs.getFloat("ADVANCE");
		}
		rs.close();
		st.close();
		con.close();
	}	
	catch (SQLException e) 
	{
		e.printStackTrace();
	}
	
	
	return ADVANCE;
}


public String [] getPayble(String empno)
{
	SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
	String [] payable = {"","","",""};
	try
	{
		String month="";
		payable[0]="";
	Connection con = ConnectionManager.getConnection();
	ResultSet rslist1 = null;
	Statement stmt1=con.createStatement();
	Statement stmt2=con.createStatement();
	rslist1=stmt2.executeQuery("select NET_AMT as PAYABLE,trndt from paytran_stage where empno="+empno+" and TRNCD=999 and   status='F' order by trndt");
	int i=0;
	
	while(rslist1.next()){
		month=sdf.format(rslist1.getDate("trndt"));
	//	ResultSet rs = stmt1.executeQuery("select SUM(SANCTION_AMT) as sanction_amt from ADV_PAY_REQUESTED where EMPNO="+empno+" and REQUEST_STATUS<'SANCTION' and FOR_MONTH between '"+ReportDAO.BOM(month)+"' and '"+ReportDAO.EOM(month)+"'");
		
		ResultSet rs = stmt1.executeQuery(" select(select  case when  SUM(ADV_AMT_REQUESTED) is null then 0 else" +
				" SUM(ADV_AMT_REQUESTED) end   from ADV_PAY_REQUESTED " +
				" where EMPNO= "+empno+" and REQUEST_STATUS='pending' and FOR_MONTH between '"+ReportDAO.BOM(month)+"' and '"+ReportDAO.EOM(month)+"' ) + " +
				"(select case when  SUM(SANCTION_AMT) is null then 0 else SUM(SANCTION_AMT) end  " +
				"  from ADV_PAY_REQUESTED where EMPNO= "+empno+" and" +
				" REQUEST_STATUS='sanction' and FOR_MONTH between '"+ReportDAO.BOM(month)+"' and '"+ReportDAO.EOM(month)+"' ) as TOTAL  ");
	
		int Sanction_amt = 0;
		while(rs.next())
		{
		Sanction_amt+=	rs.getInt("TOTAL");
		}
		
		int pay = rslist1.getInt("PAYABLE") - Sanction_amt;
		
		payable[i++]=month.substring(3)+" : "+pay+".00";
	}
	
	
	
	
	rslist1.close();
	stmt2.close();
	con.close();
	}
	catch (SQLException e) 
	{
		e.printStackTrace();
	}
	
	return payable;
}


		public static void newAdvance(String PAYREGDT,String TName,String imgpath,String filepath,String type)
		{

			


		//	System.out.println("in new Advance Report generation method");
			SimpleDateFormat format=new SimpleDateFormat("dd-MMM-yyyy");
			RepoartBean repBean  = new RepoartBean();
			UtilityDAO dao = new UtilityDAO();
			Connection con =null;
			String BomDt = "";
			String EomDt = "";
			int lastdat = 0;
			//String dt = PAYREGDT;
			String table_name = null;
			
			
			//System.out.println(dt);
			lastdat = (int) Calculate.getDays(PAYREGDT);
			
			BomDt = ReportDAO.BOM(PAYREGDT);
			//System.out.println("bomdt "+BomDt);
			EomDt = ReportDAO.EOM(PAYREGDT);
			//System.out.println("eomdt"+EomDt);
			
			String temp = PAYREGDT.substring(3);
			ResultSet emp = null;
			String EmpSql = ""; 
			String pBrcd1 = "";
			int tot_no_emp = 0;
			int br_tot_no_emp = 0;
			int srno=0;
		
			
			
			EmployeeHandler emph = new EmployeeHandler();
			EmployeeBean ebean1 = new EmployeeBean();
			LookupHandler lookuph= new LookupHandler();
			
			try
			{
				
				
			
			 		
			
			if(type.equalsIgnoreCase("ID")){
				ReportDAO.OpenCon("", "", "",repBean);
				con = repBean.getCn();	
				Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				
				
				
				Document doc = new Document(new Rectangle(2384,3370));
				PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filepath));
				Footer1 ftr = dao.new Footer1(lable1);
				writer.setPageEvent(ftr);
				doc.open();
				Font FONT = new Font(Font.HELVETICA,190, Font.NORMAL, new GrayColor(0.85f));
				ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("THE NASIK MARCHANT'S CO-OP BANK LTD. ",FONT), 1300f, 1830f, 45);			
				Image image1 = Image.getInstance(imgpath);		
				Phrase title = new Phrase("THE NASIK MARCHANT'S CO-OP BANK LTD. ",new Font(FONT.TIMES_ROMAN,38,Font.BOLD));
				Paragraph para = new Paragraph(title);
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingBefore(0);
				image1.scaleAbsolute(310f, 120f);
				image1.setAbsolutePosition(60f, 3200f);
				doc.add(image1);
				doc.add(para);
				para = new Paragraph(new Phrase("Rajan Complex Arcade, Datta Mandir, Nashik Road, Nashik - 422101",new Font(Font.TIMES_ROMAN,28)));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(0);
				
				doc.add(para);
				para = new Paragraph(new Phrase("Tel : +91-20 26812190",new Font(Font.TIMES_ROMAN,28)));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(0);
				
				doc.add(para);
				para = new Paragraph(new Phrase("Email : adm@namcobank.in",new Font(Font.TIMES_ROMAN,28)));
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(0);
				
				doc.add(para);
				
				Font f = new Font(Font.HELVETICA,30);
				Font f1 = new Font(Font.HELVETICA,25);
				Font f2 = new Font(Font.HELVETICA,32);
				Font f3=new Font(Font.HELVETICA,40,Font.BOLDITALIC);
			
					para = new Paragraph(new Phrase("Advance Report for the Month Of :- "+temp+" ",new Font(Font.TIMES_ROMAN,25,Font.BOLD)));
				
				para.setAlignment(Element.ALIGN_CENTER);
				para.setSpacingAfter(40);
				
				doc.add(para);
				
				Rectangle rec = new  Rectangle(100,100);
				 
			
					float advance = 0.0f;
					
					float advance2=0.0f;
					
		
	       		EmpSql="select em.EMPCODE,a.empno,rtrim(em.fname)+' '+rtrim(em.mname)+' '+rtrim(em.lname) as  NAME," +
	       				"a.PRJ_SRNO as PRJ_SRNO,a.FOR_MONTH as ADVANCEMONTH,a.sanction_amt as ADVANCEAMOUNT," +
	       				"a.SANCTION_DATE as SanctionDate,a.SANCTION_BY as SANCTIONBY,a.VOUCHER_NO as VOUCHERNO " +
	       				"from ADV_PAY_REQUESTED a join EMPMAST em on em.EMPNO=a.EMPNO  " +
	       				"where a.FOR_MONTH between '"+BomDt+"'and '"+EomDt+"' " +
	       			//	"where a.SANCTION_DATE between '"+BomDt+"'and '"+EomDt+"' " +
	       						"and a.REQUEST_STATUS='sanction'";
					
					
	    //   System.out.println(EmpSql);
				ResultSet rs = st.executeQuery(EmpSql);
			//	System.out.println("resultset"+rs);
			
					while(rs.next()){
						ebean1 = emph.getEmployeeInformation(rs.getString("SANCTIONBY"));
						
						String name=lookuph.getLKP_Desc("SALUTE", ebean1.getSALUTE())+" "+ ebean1.getFNAME()+" "+ebean1.getLNAME();
						int size=name.length();
						
					String sanction_by="";
							
							sanction_by=name.substring(3,size);
					String prj_name = null;
					String prj_code = null;
			    	 Connection conn = ConnectionManager.getConnectionTech();
					Statement stmt = conn.createStatement();
				
					String prjquery = "select Site_Name,Project_Code from Project_Sites where SITE_ID = '"+rs.getString("PRJ_SRNO")+"'";
	//				System.out.println(prjquery);
					ResultSet prj = stmt.executeQuery(prjquery);
					if(prj.next()){
						prj_name = prj.getString("Site_Name");
						prj_code = prj.getString("Project_Code");
					}
			    	 pBrcd1 = rs.getString("PRJ_SRNO");
			    	 br_tot_no_emp = 0;
			    	 srno=0;
			    	 para = new Paragraph(new Phrase("Employee's Advance Report List For Project Site : "+prj_name+ " ("+prj_code+")",f));
					para.setAlignment(Element.ALIGN_CENTER);
					para.setSpacingAfter(15);
					doc.add(para);
			    	 prj.close();
			    	 stmt.close();
			    	 conn.close();
			    	 PdfPTable datatab;
			    	 PdfPTable datatot;
						PdfPTable main = new PdfPTable(8);
						main.setSpacingBefore(10);
						main.setWidthPercentage(new float[]{6,8,20,12,15,12,15,7f}, rec);
						
						PdfPCell maincell ;
						
						maincell = new PdfPCell(new Phrase("SR NO.",f));
						
						maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
						main.addCell(maincell);
						
						maincell = new PdfPCell(new Phrase("EMP CODE",f));
			
						maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
						main.addCell(maincell);
						
						maincell = new PdfPCell(new Phrase("EMPLOYEE NAME",f));
						maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
						main.addCell(maincell);
						
						maincell = new PdfPCell(new Phrase("ADVANCE MONTH",f));
						maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
						main.addCell(maincell);
						
						maincell = new PdfPCell(new Phrase("ADVANCE AMOUNT",f));
						maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
						main.addCell(maincell);
						
						maincell = new PdfPCell(new Phrase("SANCTION DATE",f));
						maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
						main.addCell(maincell);
						
						maincell = new PdfPCell(new Phrase("SANCTION BY",f));
						maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
						main.addCell(maincell);
					
						maincell = new PdfPCell(new Phrase("VOUCHER NO.",f));
						maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
						main.addCell(maincell);
					
						
						doc.add(main);
			    	 while (pBrcd1.equals(rs.getString("PRJ_SRNO")))
			    	 {
			    		float date=Calculate.getDays(PAYREGDT);
			    		 datatab = new PdfPTable(8);
			    	
				    	 datatab.setWidthPercentage(new float[]{6,8,20,12,15,12,15,7f}, rec);
				    	 
				    	 datatab.setHorizontalAlignment(Element.ALIGN_CENTER);
				    	 
				    	 srno=srno+1;
				    		PdfPCell cell = new PdfPCell(new Phrase(""+srno,f1));
				    		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				    		cell.setFixedHeight(50);
				    		datatab.addCell(cell);
			    		 
			    		PdfPCell cell1 = new PdfPCell(new Phrase(""+rs.getString("EMPCODE"),f1));
			    		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			    		cell1.setFixedHeight(50);
			    		datatab.addCell(cell1);
						
						PdfPCell cell2 = new PdfPCell(new Phrase(""+rs.getString("NAME"),f1));
						cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
						datatab.addCell(cell2);
						
						String x=format.format(rs.getDate("ADVANCEMONTH"));
						String y=x.substring(3,11);
						
						PdfPCell cell3 = new PdfPCell(new Phrase(""+y,f1));
						cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
						datatab.addCell(cell3);
						
						
						advance=  advance+ rs.getFloat("ADVANCEAMOUNT");
						PdfPCell cell4 = new PdfPCell(new Phrase(""+rs.getFloat("ADVANCEAMOUNT"),f1));
						cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
						datatab.addCell(cell4);
						
						
						PdfPCell cell5 = new PdfPCell(new Phrase(""+format.format(rs.getDate("SanctionDate")),f1));
						cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
						datatab.addCell(cell5);
						
			
						PdfPCell cell6 = new PdfPCell(new Phrase(""+sanction_by,f1));
						cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
						datatab.addCell(cell6);
						
						
					//	PdfPCell cell7 = new PdfPCell(new Phrase(""+rs.getFloat("VOUCHERNO"),f1));
						PdfPCell cell7 = new PdfPCell(new Phrase("",f1));
						cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
						datatab.addCell(cell7);
						
						
						doc.add(datatab);
						
						tot_no_emp = tot_no_emp + 1;
						br_tot_no_emp = br_tot_no_emp + 1;
						if (!rs.next()) {
							break;
						}
						if(!pBrcd1.equals(rs.getString("PRJ_SRNO"))){
						   	rs.previous();
						   	break;
					    }
			    	 }

			    	 	datatot = new PdfPTable(8);
						datatot.setWidthPercentage(new float[]{6,8,20,12,15,12,15,7f}, rec);
						datatot.setHorizontalAlignment(Element.ALIGN_CENTER);
			    		 
			    		PdfPCell cell1 = new PdfPCell(new Phrase("Total:--",f2));
			    		cell1.setColspan(4);
			    		cell1.setFixedHeight(50);
			    		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			    		datatot.addCell(cell1);
						
						PdfPCell cell2 = new PdfPCell(new Phrase(""+advance,f2));
						cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						datatot.addCell(cell2);
						 advance2 = advance2 + advance;
						 advance = 0.0f;
		
						PdfPCell cell5 = new PdfPCell(new Phrase("",f1));
						cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell5.setColspan(3);
						datatot.addCell(cell5);
						
						doc.add(datatot);
			    	 
					   para = new Paragraph(new Phrase("No Of Employee :- "+br_tot_no_emp,f));
					   para.setAlignment(Element.ALIGN_CENTER);
					   para.setSpacingAfter(70);
					   doc.add(para);
				 						
				}
						
					
					
					
					
					  /*  para = new Paragraph(new Phrase("Total No Of Employees :- "+tot_no_emp,f3));
						 para.setAlignment(Element.ALIGN_CENTER);
						 para.setSpacingAfter(10);
						 doc.add(para);
						 
						   para = new Paragraph(new Phrase("Total Advance Rs. :- "+advance2,f3));
							 para.setAlignment(Element.ALIGN_CENTER);
							 para.setSpacingAfter(10);
							 doc.add(para);*/
							 
						 
					//	 System.out.println("Total No Of Employee :- "+tot_no_emp);
					 
						 
					
					para = new Paragraph(new Phrase("---SUMMARY---",f3));
					 para.setAlignment(Element.ALIGN_CENTER);
					 para.setSpacingAfter(10);
					 doc.add(para);
					 
							
							 PdfPTable main = new PdfPTable(2);
							 main.setSpacingBefore(10);
							main.setWidthPercentage(new float[]{30,10}, rec);
							PdfPCell maincell ;
							
							maincell = new PdfPCell(new Phrase("Total No of Employees. :-",f2));
							maincell.setFixedHeight(50);
							maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
							main.addCell(maincell);
							
							maincell = new PdfPCell(new Phrase(""+tot_no_emp,f2));
							maincell.setFixedHeight(50);
							maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
							main.addCell(maincell);
							
							maincell = new PdfPCell(new Phrase("Total Advance Rs. ",f2));
							maincell.setFixedHeight(50);
							maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
							main.addCell(maincell);
							
							maincell = new PdfPCell(new Phrase(""+advance2,f2));
							maincell.setFixedHeight(50);
							maincell.setHorizontalAlignment(Element.ALIGN_CENTER);
							main.addCell(maincell);
						
						doc.add(main);
				
				st.close();
			    con.close();
				doc.close();
	
				}
				
			}
			 
			 catch(Exception e)
			{
				e.printStackTrace();
			}
		
		}
}

	