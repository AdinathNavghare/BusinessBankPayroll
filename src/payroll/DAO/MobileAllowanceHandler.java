package payroll.DAO;

import payroll.Core.ReportDAO;
import payroll.Model.LoanBean;
import payroll.Model.MobileBean;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class MobileAllowanceHandler 
{

	public MobileBean setEmpMobAllowance(MobileBean mb)  // insert record of employee into MOBILE_RANGE and MOBILE_DEDUCT 
	{
		Connection con= ConnectionManager.getConnection();
		boolean flag=false;
		try
		{
			Statement st = con.createStatement();
			Statement st1 = con.createStatement();
			Statement st2=con.createStatement();
			
			System.out.println("IF EXISTS (SELECT * FROM Mobile_Range WHERE mobile_no='"+mb.getMobile_no()+"' AND empno="+mb.getEmp_no()+")" +
					"	begin " +
					" UPDATE MOBILE_RANGE SET	" +
					"mobile_no='"+mb.getMobile_no()+"',"+
					"connection_type="+mb.getConnection_type()+","+
					"effective_date='"+mb.getEff_date()+"',"+
					"ser_provider="+mb.getService_provider()+","+
					"charges="+mb.getCharges()+""+
			" where	mobile_no='"+mb.getMobile_no()+"'" +
			" " +
			" UPDATE MOBILE_DEDUCT set " +
					"mobile_no='"+mb.getMobile_no()+"',"+
					"effective_date='"+mb.getEff_date()+"'," +
							"bill_month='"+mb.getEff_date()+"'" +
					" where mobile_no='"+mb.getMobile_no()+"'" +
					
				    	
					"   end  " +
					"else" +
					"	begin " +
					"	update mobile_range	set status='N'	where	mobile_no='"+mb.getMobile_no()+"' AND empno!="+mb.getEmp_no()+"" +
					"	update mobile_deduct	set status='N'	where	mobile_no='"+mb.getMobile_no()+"' AND empno!="+mb.getEmp_no()+"" +
					"	INSERT INTO mobile_range VALUES (" +
						""+mb.getEmp_no()+","+
						"'"+mb.getMobile_no()+"',"+
						""+mb.getConnection_type()+","+
						"'"+mb.getEff_date()+"',"+
						""+mb.getPrj_srno()+","+
						""+mb.getService_provider()+","+
						""+mb.getCharges()+","+
						"'"+mb.getStatus()+"'"+
						")	" +
					"   INSERT INTO mobile_deduct VALUES (" +
						""+mb.getEmp_no()+","+
						"'"+mb.getMobile_no()+"',"+
						"'"+mb.getEff_date()+"',"+
						"0,"+
						"'"+mb.getStatus()+"', " +
						"'"+mb.getEff_date()+"'"+
						")		" +
					"	end		");
			
			
			st2.execute("IF EXISTS (SELECT * FROM Mobile_Range WHERE mobile_no='"+mb.getMobile_no()+"' AND empno="+mb.getEmp_no()+")" +
					"	begin " +
					" UPDATE MOBILE_RANGE SET	" +
					"mobile_no='"+mb.getMobile_no()+"',"+
					"connection_type="+mb.getConnection_type()+","+
					"effective_date='"+mb.getEff_date()+"',"+
					"ser_provider="+mb.getService_provider()+","+
					"charges="+mb.getCharges()+""+
			" where	mobile_no='"+mb.getMobile_no()+"'" +
			" " +
			" UPDATE MOBILE_DEDUCT set " +
					"mobile_no='"+mb.getMobile_no()+"',"+
					"effective_date='"+mb.getEff_date()+"'," +
					"bill_month='"+mb.getEff_date()+"'" +
					" where mobile_no='"+mb.getMobile_no()+"'" +
					
				    	
					"   end  " +
					"else" +
					"	begin " +
					"	update mobile_range	set status='N'	where	mobile_no='"+mb.getMobile_no()+"' AND empno!="+mb.getEmp_no()+"" +
					"	update mobile_deduct	set status='N'	where	mobile_no='"+mb.getMobile_no()+"' AND empno!="+mb.getEmp_no()+"" +
					"	INSERT INTO mobile_range  VALUES (" +
						""+mb.getEmp_no()+","+
						"'"+mb.getMobile_no()+"',"+
						""+mb.getConnection_type()+","+
						"'"+mb.getEff_date()+"',"+
						""+mb.getPrj_srno()+","+
						""+mb.getService_provider()+","+
						""+mb.getCharges()+","+
						"'"+mb.getStatus()+"'"+
						")	" +
					"   INSERT INTO mobile_deduct VALUES (" +
						""+mb.getEmp_no()+","+
						"'"+mb.getMobile_no()+"',"+
						"'"+mb.getEff_date()+"',"+
						"0,"+
						"'"+mb.getStatus()+"', " +
						"'"+mb.getEff_date()+"'"+")		" +
					"	end		");
			
			
			
			
			/*st.execute("INSERT INTO MOBILE_RANGE VALUES(" +
					""+mb.getEmp_no()+","+
					"'"+mb.getMobile_no()+"',"+
					""+mb.getConnection_type()+","+
					"'"+mb.getEff_date()+"',"+
					""+mb.getPrj_srno()+","+
					""+mb.getService_provider()+","+
					""+mb.getCharges()+","+
					"'"+mb.getStatus()+"'"+
					")");
			
			
			st1.execute("INSERT INTO MOBILE_DEDUCT VALUES(" +
					""+mb.getEmp_no()+","+
					"'"+mb.getMobile_no()+"',"+
					"'"+mb.getEff_date()+"',"+
					"0,"+
					"'"+mb.getStatus()+"'"+
					")");*/
			flag=true;
			st2.close();
			con.close();	
		}
		catch(Exception e)
		{
			flag=false;
			e.printStackTrace();
			try {
				con.close();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
		}
		if(flag)
		{
			return mb;
		}
		else
		{
			 mb=new MobileBean();
			 mb.setEmp_no(0);
				mb.setEff_date("");
				mb.setPrj_srno(0);
				mb.setMobile_no("");
				mb.setService_provider(0);
				mb.setConnection_type(0);
				mb.setCharges(0);
			 return mb;
		}
		
	}

	
public ArrayList<MobileBean> getAllMobileAllowance() //get allowance amount details for all employee
{
	ArrayList<MobileBean> list=new ArrayList<MobileBean>();
	Connection con= ConnectionManager.getConnection();
	SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
	TranHandler th=new TranHandler();
	String sal_date=th.getSalaryDate();
	boolean flag=false;
	try
	{
		Statement st = con.createStatement();
		ResultSet rs=null;
		rs=st.executeQuery("Select  * from MOBILE_RANGE  order by effective_date");
		while (rs.next())
		{
			MobileBean mb=new MobileBean();
		
				mb.setEmp_no(rs.getInt("empno"));
				mb.setMobile_no(rs.getString("mobile_no"));
				mb.setConnection_type(rs.getInt("connection_type"));
				mb.setEff_date(sdf.format(rs.getDate("effective_date")));
				mb.setPrj_srno(rs.getInt("prj_srno"));
				mb.setService_provider(rs.getInt("ser_provider"));
				mb.setCharges(rs.getFloat("charges"));
				
				
				list.add(mb);
				
		}
		
		st.close();
		con.close();	
	}
	catch(Exception e)
	{
		
		e.printStackTrace();
		try {
			con.close();
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
	}
	
	
	return list;
}
	
	
public ArrayList<MobileBean> getMobileAllownceDetail(String sal_date) //get allowance and deduction bill amount details for all employee
{
	
	
		ArrayList<MobileBean> list= new ArrayList<MobileBean>();
		try
		{
			TranHandler th=new TranHandler();
			//String sal_date=th.getSalaryDate();
SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");		
Connection con= ConnectionManager.getConnection();
Statement st = con.createStatement();
ResultSet rs= null;
Statement st1 = con.createStatement();
ResultSet rs1=null;

	
	
	
	 st = con.createStatement();
	 rs=null;
	/* System.out.println("SELECT MBRMST.*, MBDMST.ded_charges	FROM MOBILE_RANGE MBRMST	" +
				"inner JOIN MOBILE_DEDUCT MBDMST ON MBDMST.EFFECTIVE_DATE <= '"+ReportDAO.EOM(sal_date)+"' " +
				"AND MBDMST.EMPNO=MBRMST.EMPNO and MBDMST.STATUS=MBRMST.STATUS	WHERE MBRMST.EFFECTIVE_DATE <= '"+ReportDAO.EOM(sal_date)+"'" +
						" and MBDMST.mobile_no=MBRMST.mobile_no and MBDMST.STATUS='A' and 'F'<>(select  status from paytran where trncd=199 and  empno=MBRMST.empno ) ");
		
	rs=st.executeQuery("SELECT MBRMST.*, MBDMST.ded_charges	FROM MOBILE_RANGE MBRMST	" +
			"inner JOIN MOBILE_DEDUCT MBDMST ON MBDMST.EFFECTIVE_DATE <= '"+ReportDAO.EOM(sal_date)+"' " +
			"AND MBDMST.EMPNO=MBRMST.EMPNO and MBDMST.STATUS=MBRMST.STATUS	WHERE MBRMST.EFFECTIVE_DATE <= '"+ReportDAO.EOM(sal_date)+"'" +
					" and MBDMST.mobile_no=MBRMST.mobile_no and MBDMST.STATUS='A' and 'F'<>(select  status from paytran where  trncd=199 and empno=MBRMST.empno )");
	
	*/
	
	
	 System.out.println("SELECT  MR.*,  CASE WHEN MD.DED_CHARGES IS NOT NULL THEN MD.DED_CHARGES ELSE '0' END AS 'DEDUCAT_CHARGES' FROM MOBILE_RANGE MR LEFT OUTER JOIN MOBILE_DEDUCT MD ON MD.EMPNO=MR.EMPNO "+
" AND MD.MOBILE_NO=MR.MOBILE_NO AND MD.BILL_MONTH BETWEEN '"+ReportDAO.BOM(sal_date)+"' and '"+ReportDAO.EOM(sal_date)+"' "+
" and MR.effective_date <='"+ReportDAO.EOM(sal_date)+"'"+
 "WHERE MR.STATUS='A' and 'F'<>(select  status from paytran where trncd=199 and  empno=MR.empno ) and MR.effective_date <='"+ReportDAO.EOM(sal_date)+"' ");
		
	rs=st.executeQuery("SELECT  MR.*,  CASE WHEN MD.DED_CHARGES IS NOT NULL THEN MD.DED_CHARGES ELSE '0' END AS 'DEDUCAT_CHARGES' FROM MOBILE_RANGE MR LEFT OUTER JOIN MOBILE_DEDUCT MD ON MD.EMPNO=MR.EMPNO "+
" AND MD.MOBILE_NO=MR.MOBILE_NO AND MD.BILL_MONTH BETWEEN '"+ReportDAO.BOM(sal_date)+"' and '"+ReportDAO.EOM(sal_date)+"' "+
" and MR.effective_date <='"+ReportDAO.EOM(sal_date)+"'"+
 "WHERE MR.STATUS='A' and 'F'<>(select  status from paytran where trncd=199 and  empno=MR.empno ) and MR.effective_date <='"+ReportDAO.EOM(sal_date)+"' order by MR.EMPNO");
	
	
	
	
	while (rs.next())
	{
		MobileBean mb=new MobileBean();
	
			mb.setEmp_no(rs.getInt("empno"));
			mb.setMobile_no(rs.getString("mobile_no"));
			mb.setConnection_type(rs.getInt("connection_type"));
			mb.setEff_date(sdf.format(rs.getDate("effective_date")));
			mb.setPrj_srno(rs.getInt("prj_srno"));
			mb.setService_provider(rs.getInt("ser_provider"));
			mb.setCharges(rs.getFloat("charges"));
			mb.setBillcharges(rs.getFloat("DEDUCAT_CHARGES"));
			
			
			list.add(mb);
			
	}
	
	
	
	

	
	
	
	
	con.close();
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return list;
}


public boolean setMobileBillAmt(String[] empno, Float[] bill, String[] mob) 
{

	TranHandler th=new TranHandler();
	String sal_date=th.getSalaryDate();
	Connection con= ConnectionManager.getConnection();
	boolean flag=false;
	
	try
	{
		Statement st = con.createStatement();
		
		for(int k=0;k<empno.length;k++)
		{
		
		st.execute(" IF EXISTS (SELECT * FROM MOBILE_DEDUCT WHERE EMPNO="+empno[k]+" AND MOBILE_NO='"+mob[k]+"' AND  EFFECTIVE_DATE <= '"+ReportDAO.EOM(sal_date)+"' and bill_month between '"+ReportDAO.BOM(sal_date)+"' and '"+ReportDAO.EOM(sal_date)+"' )" +
				"UPDATE MOBILE_DEDUCT SET DED_CHARGES="+bill[k]+" WHERE EMPNO="+empno[k]+" AND MOBILE_NO='"+mob[k]+"' AND EFFECTIVE_DATE <= '"+ReportDAO.EOM(sal_date)+"' and bill_month between '"+ReportDAO.BOM(sal_date)+"' and '"+ReportDAO.EOM(sal_date)+"' " +
				"ELSE INSERT INTO MOBILE_DEDUCT VALUES("+empno[k]+",'"+mob[k]+"','"+sal_date+"',"+bill[k]+",'A','"+sal_date+"')");
		
		}
		
		
		/*
		IF EXISTS (SELECT * FROM Mobile_Range WHERE mobile_no='9423980968' AND empno!=49)	
	begin 
	update mobile_range
	set status='N'
	where 
	mobile_no='9423980968'
	
	update mobile_deduct
	set status='N'
	where 
	mobile_no='9423980968'
	INSERT INTO mobile_range VALUES(0,'00000',0,'1-APR-1990',0,0,00,'A')
	INSERT INTO mobile_deduct VALUES(0,'00000','1-APR-1990',00,'A')
	
	end
	
	else
	begin
 
	
	end
		*/
		flag=true;
		st.close();
		con.close();	
	}
	catch(Exception e)
	{
		flag=false;
		e.printStackTrace();
		try {
			con.close();
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
	}
	
	return flag;
}	
	
	


public float getDeductAmt(String empno)
{
	float result=0.0f;
	TranHandler th=new TranHandler();
	String sal_date=th.getSalaryDate();
	Connection con= ConnectionManager.getConnection();
	boolean flag=false;
	
	try
	{
		Statement st = con.createStatement();
		String query="SELECT SUM(MBRMST.charges),	SUM(MBDMST.ded_charges)	FROM Mobile_Range MBRMST LEFT OUTER JOIN mobile_deduct MBDMST ON MBDMST.empno=MBRMST.empno " +
					"and MBDMST.effective_date <='"+ReportDAO.EOM(sal_date)+"'  " +
							" and MBDMST.bill_month between '"+ReportDAO.BOM(sal_date)+"' and '"+ReportDAO.EOM(sal_date)+"' " +
							"and MBRMST.STATUS='A' and MBDMST.bill_month between '"+ReportDAO.BOM(sal_date)+"' and '"+ReportDAO.EOM(sal_date)+"' WHERE MBRMST.effective_date <= '"+ReportDAO.EOM(sal_date)+"' " +
					"AND MBDMST.empno=MBRMST.empno and MBDMST.mobile_no=MBRMST.mobile_no and MBRMST.STATUS='A' and MBDMST.bill_month between '"+ReportDAO.BOM(sal_date)+"' and '"+ReportDAO.EOM(sal_date)+"' and MBRMST.empno="+empno;
		ResultSet rs=st.executeQuery(query);
		if(rs.next())
		{
			
			result=Math.abs((rs.getFloat(1)-rs.getFloat(2))<0? (rs.getFloat(1)-rs.getFloat(2)):0);
			
		}
		
		
		
	con.close();	
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	
	
	return result;
}


public MobileBean getEmpMobileAllow(MobileBean mb1)
{
	Connection con= ConnectionManager.getConnection();
	SimpleDateFormat sdf =new SimpleDateFormat("dd-MMM-yyyy");
	MobileBean mb=new MobileBean();
	try
	{
		Statement st = con.createStatement();
		
		
		ResultSet rs=null;
		rs=st.executeQuery("Select  * from MOBILE_RANGE  where empno="+mb1.getEmp_no()+" and mobile_no='"+mb1.getMobile_no()+"' and effective_date='"+mb1.getEff_date()+"' and prj_srno="+mb1.getPrj_srno());
		while (rs.next())
		{
			
		
				mb.setEmp_no(rs.getInt("empno"));
				mb.setMobile_no(rs.getString("mobile_no"));
				mb.setConnection_type(rs.getInt("connection_type"));
				mb.setEff_date(sdf.format(rs.getDate("effective_date")));
				mb.setPrj_srno(rs.getInt("prj_srno"));
				mb.setService_provider(rs.getInt("ser_provider"));
				mb.setCharges(rs.getFloat("charges"));
				
				
	
				
		}
		
		st.close();
		con.close();	
	}
	catch(Exception e)
	{
		
		e.printStackTrace();
		
	}
	
	return mb;
}


public boolean updateEmpMobileAllow(MobileBean mb1,MobileBean mb2)
{
	Connection con= ConnectionManager.getConnection();
	SimpleDateFormat sdf =new SimpleDateFormat("dd-MMM-yyyy");
	MobileBean mb=new MobileBean();
	boolean flag =false;
	try
	{
		Statement st = con.createStatement();
		Statement st1 = con.createStatement();
		
		
		
		ResultSet rs=null;
		st.execute(" UPDATE MOBILE_RANGE SET	" +
								"mobile_no='"+mb2.getMobile_no()+"',"+
								"connection_type="+mb2.getConnection_type()+","+
								"effective_date='"+mb2.getEff_date()+"',"+
								"ser_provider="+mb2.getService_provider()+","+
								"charges="+mb2.getCharges()+","+
								"status='"+mb2.getStatus()+"'"+
								" where  empno="+mb1.getEmp_no()+" and mobile_no='"+mb1.getMobile_no()+"' and effective_date='"+mb1.getEff_date()+"'");
						
						
						st1.execute("UPDATE MOBILE_DEDUCT set " +
								"mobile_no='"+mb2.getMobile_no()+"',"+
								"effective_date='"+mb2.getEff_date()+"'," +
								"status='"+mb2.getStatus()+"'"+
								" where  empno="+mb1.getEmp_no()+" and mobile_no='"+mb1.getMobile_no()+"' and effective_date='"+mb1.getEff_date()+"' ");
	
		flag=true;
		st.close();
		con.close();	
	}
	catch(Exception e)
	{
		
		e.printStackTrace();
		
	}
	
	return flag;
}


public void getMob_Ded_Reports(String date, String filePath, String imagepath) 
{

	try
	{
	Document doc = new Document();
	PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filePath));
	doc.open();
	doc.setPageSize(PageSize.A4);
	
	
	
	Font FONT = new Font(Font.HELVETICA, 52, Font.NORMAL, new GrayColor(0.75f));
	ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("HARSH CONSTRUCTION",FONT), 297.5f, 421, 45);
	
	
				
	Image image1 = Image.getInstance(imagepath);
	
	
	Phrase title = new Phrase("HARSH CONSTRUCTION PVT.LTD.",new Font(FONT.TIMES_ROMAN,8,Font.BOLD));
	Paragraph para = new Paragraph(title);
	para.setAlignment(Element.ALIGN_CENTER);
	para.setSpacingBefore(0);
	
	
	
	image1.scaleAbsolute(80f, 60f);
	image1.setAbsolutePosition(40f, 750f);
	
	doc.add(image1);
	doc.add(para);
	
	
	para = new Paragraph(new Phrase("1 Sanskruti Apartment Murkute Lane, New Pandit Coloney,Gangapur Road,Nashik -422005",new Font(Font.TIMES_ROMAN,8)));
	para.setAlignment(Element.ALIGN_CENTER);
	para.setSpacingAfter(0);
	
	doc.add(para);
	para = new Paragraph(new Phrase("Tel : 0253-2317143,Fax : 0253-2317621",new Font(Font.TIMES_ROMAN,8)));
	para.setAlignment(Element.ALIGN_CENTER);
	para.setSpacingAfter(0);
	
	doc.add(para);
	para = new Paragraph(new Phrase("Email : info@hcplnsk.com",new Font(Font.TIMES_ROMAN,8)));
	para.setAlignment(Element.ALIGN_CENTER);
	para.setSpacingAfter(0);
	
	doc.add(para);
	para = new Paragraph(new Phrase("Mobile Deduction For "+date.substring(3),new Font(Font.TIMES_ROMAN,10)));
	para.setAlignment(Element.ALIGN_CENTER);
	para.setSpacingAfter(15);
	
	doc.add(para);
	
	
	
	PdfPTable tab = new PdfPTable(9);
    
    tab.setWidthPercentage(new float[]{6,6,30,12,12,10,8,8,8}, new Rectangle(100, 100));
	// tab.setSpacingBefore(5);
	tab.setHorizontalAlignment(Element.ALIGN_CENTER);
	tab.addCell(new Phrase("Sr No",new Font(Font.TIMES_ROMAN,10)));
	tab.addCell(new Phrase("Emp Code",new Font(Font.TIMES_ROMAN,10)));
	tab.addCell(new Phrase("Employee Name",new Font(Font.TIMES_ROMAN,10)));
	tab.addCell(new Phrase("Mobile No.",new Font(Font.TIMES_ROMAN,10)));
	tab.addCell(new Phrase("Service Provider",new Font(Font.TIMES_ROMAN,10)));
	tab.addCell(new Phrase("Connection Type",new Font(Font.TIMES_ROMAN,10)));
	tab.addCell(new Phrase("Allow. Amt",new Font(Font.TIMES_ROMAN,10)));
	tab.addCell(new Phrase("Bill Charges",new Font(Font.TIMES_ROMAN,10)));
	tab.addCell(new Phrase("Deduct. Amt",new Font(Font.TIMES_ROMAN,10)));
//	tab.setHeaderRows(1);
	
	MobileAllowanceHandler mah=new MobileAllowanceHandler();
	LeaveMasterHandler obj=new LeaveMasterHandler();
	LookupHandler lkp=new LookupHandler();
	ArrayList<MobileBean> tran=new ArrayList<MobileBean>();
	String empName;
	
	tran=mah.getMobileAllownceDetail(date);
	int srno=1;
	float allow_total=0.0f;
	float bill_total=0.0f;
	float ded_total=0.0f;
	for (MobileBean tbean : tran) {
		empName = obj.getempName(tbean.getEmp_no());
		
		
		
		tab.addCell(new Phrase(""+(srno++),new Font(Font.TIMES_ROMAN,7)));
		tab.addCell(new Phrase(""+EmployeeHandler.getEmpcode(tbean.getEmp_no()),new Font(Font.TIMES_ROMAN,7)));
		tab.addCell(new Phrase(""+empName,new Font(Font.TIMES_ROMAN,7)));
		tab.addCell(new Phrase(""+tbean.getMobile_no(),new Font(Font.TIMES_ROMAN,7)));
		tab.addCell(new Phrase(""+lkp.getLKP_Desc("SERVICE PROVIDER",tbean.getService_provider()),new Font(Font.TIMES_ROMAN,7)));
		tab.addCell(new Phrase(""+lkp.getLKP_Desc("CONNECTION_TYPE",tbean.getConnection_type()) ,new Font(Font.TIMES_ROMAN,7)));
		tab.addCell(new Phrase(""+tbean.getCharges(),new Font(Font.TIMES_ROMAN,7)));
		tab.addCell(new Phrase(""+tbean.getBillcharges(),new Font(Font.TIMES_ROMAN,7)));
		tab.addCell(new Phrase(""+Math.abs((tbean.getCharges()-tbean.getBillcharges()>=0)?0.0:(tbean.getCharges()-tbean.getBillcharges())),new Font(Font.TIMES_ROMAN,7)));
		
		
		
		
		allow_total+=tbean.getCharges();
		bill_total+=tbean.getBillcharges();
		ded_total+= Math.abs((tbean.getCharges()-tbean.getBillcharges()>=0)?0.0:(tbean.getCharges()-tbean.getBillcharges()));
		
		
	}
	
	
	tab.addCell(new Phrase("",new Font(Font.TIMES_ROMAN,8)));
	tab.addCell(new Phrase("",new Font(Font.TIMES_ROMAN,8)));
	tab.addCell(new Phrase("",new Font(Font.TIMES_ROMAN,8)));
	tab.addCell(new Phrase("",new Font(Font.TIMES_ROMAN,8)));
	tab.addCell(new Phrase("",new Font(Font.TIMES_ROMAN,8)));
	tab.addCell(new Phrase("TOTAL",new Font(Font.TIMES_ROMAN,8)));
	tab.addCell(new Phrase(""+allow_total,new Font(Font.TIMES_ROMAN,8)));
	tab.addCell(new Phrase(""+bill_total,new Font(Font.TIMES_ROMAN,8)));
	tab.addCell(new Phrase(""+ded_total,new Font(Font.TIMES_ROMAN,8)));
	
	
	
	
	
	
	
	doc.add(tab);
	
	doc.close();
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	
}









	
}
