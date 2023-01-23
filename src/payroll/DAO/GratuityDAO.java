package payroll.DAO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.jfree.ui.Align;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.GrayColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import payroll.Core.ErrorLog;
import payroll.Core.ReportDAO;
import payroll.Core.UtilityDAO;
import payroll.Core.UtilityDAO.Footer;
import payroll.Model.EmployeeBean;
import payroll.Model.OnAmtBean;
import payroll.Model.SlabBean;

public class GratuityDAO 
{

	
	ErrorLog el=new ErrorLog();
	SimpleDateFormat format=new SimpleDateFormat("dd-MMM-yyyy"); 
	
public void getGratuityAMt(String empno ,String status)		
{
	
	
	
	
	
	String action = "first";
	String[] employee = null;
	CodeMasterHandler CMH = new CodeMasterHandler();
	OnAmtHandler OAH = new OnAmtHandler();
	EmployeeHandler EH = new EmployeeHandler();
	SlabHandler SH = new SlabHandler();
	EmployeeBean emp = new EmployeeBean();
	ArrayList<Integer> values = new ArrayList<Integer>();
	ArrayList<OnAmtBean> codes = new ArrayList<OnAmtBean>();
	String today="";
	String years="";
	String diff="";
	String minYears="";
	String daysPerYear="";
	String maxamount="";
	int diffYear=0;
	int month=0;
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	Date dt = new Date();
	Date dt1 = new Date();
	try
	{	
		Connection con=ConnectionManager.getConnection();
		PreparedStatement pst1=con.prepareStatement("Select * from GratuityDetails where empno=? and status<>'P'");
		pst1.setString(1, empno);
		ResultSet rs3=pst1.executeQuery();
		if (!rs3.next())
		{
		
			emp = EH.getEmployeeInformation(empno);
			
			values = OAH.getOnAmtValues(Integer.parseInt(empno), 144);	// replaced trncd 9 to 	144 for Gratuity
			codes = OAH.getOnAmtList(0, 144);
			
			ArrayList<SlabBean> SlabList = SH.getSlabs(0, 144);	//	replaced trncd 9 to 	 144 for Gratuity
			SlabBean SB = SlabList.get(0);
			
			minYears = String.valueOf(SB.getMINAMT());
			daysPerYear = String.valueOf(SB.getFIXAMT());
			maxamount=String.valueOf(SB.getMAXAMT());
			
			today = emp.getDOL()==null || emp.getDOL()==""?sdf.format(dt):emp.getDOL();
			
			String S_Date="";
			
			PreparedStatement pst2=con.prepareStatement("select * from GratuityDetails where empno="+empno+" and " +
					"trndt =(select max(trndt) from GratuityDetails where empno="+empno+" )");
          	ResultSet rs=pst2.executeQuery();
          	if(rs.next())
          	{
          		S_Date= sdf.format(rs.getDate("trndt"));
          	}
          	else
          	{
          		S_Date=emp.getDOJ();
          	}
			dt = sdf.parse(S_Date);
			dt1 = sdf.parse(today);
			
			float dateDiff = (dt1.getTime() - dt.getTime())/(1000*60*60*24*365F);
			diff=String.valueOf((dt1.getTime() - dt.getTime())/(1000*60*60*24*365F));
			diffYear =(int)Math.floor(dateDiff);
			
			month =(int) Math.floor((dateDiff-diffYear)*12);
			
			int i=0;
          	int total = 0;
          	for(OnAmtBean OAB:codes)
          	{
         
          		total+= values.get(i);
        
      		i++;
          	}
	
          	if(Float.parseFloat(diff) >= Integer.parseInt(minYears))
        	{
   
    			total=(int) ((total/30)*(Integer.parseInt(daysPerYear))*Float.parseFloat(diff));
    
    		}
			
			
		
          	
          	
          	//System.out.println("sasas============="+total);
         
          
          	con.setAutoCommit(false);
          	PreparedStatement pst=con.prepareStatement("insert into gratuityDetails(EMPNO,trndt,upddt,amount,update_by,status) " +
          			" values("+empno+",GETDATE() ,GETDATE(),"+total+",'','"+status+"')");
          	pst.execute();
          	
          	//TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,STATUS	 	
          	
          	/*PreparedStatement pst1=con.prepareStatement(" IF EXISTS ( select * from PAYTRAN where empno="+empno+" and trncd =144 )" +
          			"  update paytran set inp_amt="+total+" where empno="+empno+" and trncd =144 " +
          			"   ELSE " +
          			"	 insert into paytran values(GETDATE(),"+empno+",144,0,"+total+","+total+",0,0,"+total+",'','N'");
          	pst1.execute();*/
          	
			con.commit();
			
			pst.close();
		}
		con.close();
		}
		
	
	catch(Exception e)
	{
		
		e.printStackTrace();
		el.errorLog("Error in GratuityDAO- getGratuityAMt() -------", e.toString());
	}	
	
	
	
	
	
	
	
}




public void setGratuityAMtforAll(String filePath,String imagepath)
{

	try
	{
	Connection Cn=ConnectionManager.getConnection();
	Cn.setAutoCommit(false);
	PreparedStatement pst=Cn.prepareStatement("select empno from empmast");
	ResultSet rs1=pst.executeQuery();
	
	while (rs1.next())
	{
	
	//getGratuityAMt(rs1.getString("empno"),"");
	//System.out.println("Gratuity for "+rs1.getString("empno")+" is processing.....");

	}

	Cn.commit();
	
	
	//Code for PDF GENERATION 
	
	OutputStream file = new FileOutputStream(new File(filePath));
	Document doc = new Document();
	String lable1="Report date : ";
	try {
		UtilityDAO dao = new UtilityDAO();
		PdfWriter writer =PdfWriter.getInstance(doc, new FileOutputStream(filePath));

		Footer ftr = dao.new Footer(lable1);
		writer.setPageEvent(ftr);
		doc.open();
		
		
		
		Font FONT = new Font(Font.HELVETICA, 52, Font.NORMAL, new GrayColor(0.75f));
		ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER, new Phrase("Namco Bank",FONT), 297.5f, 421, 45);



		Image image1 = Image.getInstance(imagepath);


		Phrase title = new Phrase("THE BUSINESS CO.OP BANK LTD.",new Font(FONT.TIMES_ROMAN,10,Font.BOLD));
		Paragraph para = new Paragraph(title);
		para.setAlignment(Element.ALIGN_CENTER);
		para.setSpacingBefore(0);

		Paragraph parab = new Paragraph(title);

		Paragraph param = new Paragraph(title);

		Paragraph paraw = new Paragraph(title);


		image1.scaleAbsolute(80f, 80f);
		image1.setAbsolutePosition(40f, 730f);

		doc.add(image1);
		doc.add(para);


		para = new Paragraph(new Phrase("Rajan Complex Arcade, Datta Mandir, Nashik Road, Nashik - 422101",new Font(Font.TIMES_ROMAN,10)));
		para.setAlignment(Element.ALIGN_CENTER);
		para.setSpacingAfter(0);

		doc.add(para);
		
		para = new Paragraph(new Phrase("411013",new Font(Font.TIMES_ROMAN,10)));
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
		para = new Paragraph(new Phrase("Gratuity Details : "+ReportDAO.getServerDate(),new Font(Font.TIMES_ROMAN,10)));
		para.setAlignment(Element.ALIGN_CENTER);
		para.setSpacingAfter(0);

		doc.add(para);
		
		
		
		String sql1= "select distinct e.empno,e.EMPCODE,e.fname,e.mname,e.lname,e.DOJ,isnull(e.DOL,'31-Dec-2099') AS DOL,"+
				" (DATEDIFF(MONTH, e.DOJ, isnull(e.DOL,(select GETDATE())))/12) as years, "+
				" (DATEDIFF(MONTH, e.DOJ, isnull(e.DOL,(select GETDATE())))%12)  as months ,AMOUNT "+ 
				"from EMpmast e  join GRATUITYDETAILS g on g.EMPNO=e.EMPNO ";
		
		
		PreparedStatement pst4=Cn.prepareStatement(sql1);
		ResultSet rs4=pst4.executeQuery();
		
		PdfPTable table1=new PdfPTable(7);
		table1.setSpacingBefore(10.0f);
		table1.setWidthPercentage(new float[]{6,10,35,12,13,12,10},new Rectangle(100, 100));
		PdfPCell cellhead1=new PdfPCell(new Phrase("SRNO",new Font(Font.TIMES_ROMAN,10)));
		table1.addCell(cellhead1);
		PdfPCell cellhead2=new PdfPCell(new Phrase("CODE ",new Font(Font.TIMES_ROMAN,10)));
		table1.addCell(cellhead2);
		PdfPCell cellhead3=new PdfPCell(new Phrase("EMPLOYEE NAME ",new Font(Font.TIMES_ROMAN,10)));
		table1.addCell(cellhead3);
		PdfPCell cellhead4=new PdfPCell(new Phrase("JOIN DATE",new Font(Font.TIMES_ROMAN,10)));
		table1.addCell(cellhead4);
		PdfPCell cellhead5=new PdfPCell(new Phrase("LEAVE DATE",new Font(Font.TIMES_ROMAN,10)));
		table1.addCell(cellhead5);		
		PdfPCell cellhead6=new PdfPCell(new Phrase("DURATION",new Font(Font.TIMES_ROMAN,10)));
		table1.addCell(cellhead6);
		PdfPCell cellhead7=new PdfPCell(new Phrase("AMOUNT",new Font(Font.TIMES_ROMAN,10)));
		table1.addCell(cellhead7);
		int Srno=1;
		int Count=0;
		double totcount=0;
		while(rs4.next())
		{
		Count++;
		String dt="";
			if(rs4.getString("DOL").equals("2099-12-31"))
				{
				dt="";
				}
			else
				{
				dt=""+(format.format(rs4.getDate("DOL")));
				}
			
			
			
		 cellhead1=new PdfPCell(new Phrase(""+(Srno++),new Font(Font.TIMES_ROMAN,10)));
			table1.addCell(cellhead1);
		 cellhead2=new PdfPCell(new Phrase(""+rs4.getString("EMPCODE"),new Font(Font.TIMES_ROMAN,10)));
			table1.addCell(cellhead2);
		 cellhead3=new PdfPCell(new Phrase(""+rs4.getString("FNAME")+" "+ (rs4.getString("MNAME").equals("")?"":rs4.getString("MNAME").substring(0, 1))+". "+rs4.getString("LNAME"),new Font(Font.TIMES_ROMAN,10)));
			table1.addCell(cellhead3);
		 cellhead4=new PdfPCell(new Phrase(""+rs4.getString("DOJ")==null?"":format.format(rs4.getDate("DOJ")),new Font(Font.TIMES_ROMAN,10)));
			table1.addCell(cellhead4);
		cellhead5=new PdfPCell(new Phrase(""+dt,new Font(Font.TIMES_ROMAN,10)));
			table1.addCell(cellhead5);		
		cellhead6=new PdfPCell(new Phrase(""+(rs4.getInt("YEARS")>1?(rs4.getInt("YEARS")+ "YEARS"):((rs4.getInt("YEARS")==0?"":(rs4.getInt("YEARS")+ "YEAR"))))+" "+
				(rs4.getInt("MONTHS")>1?(rs4.getInt("MONTHS")+ "MONTHS"):((rs4.getInt("MONTHS")==0?"":(rs4.getInt("MONTHS")+ "MONTH")))),new Font(Font.TIMES_ROMAN,10)));
			table1.addCell(cellhead6);
		cellhead7=new PdfPCell(new Phrase(""+rs4.getDouble("AMOUNT"),new Font(Font.TIMES_ROMAN,10)));
			table1.addCell(cellhead7);
	   
			totcount+=rs4.getDouble("AMOUNT");
		
		
		
		
		}
		
		if(Count>0)
		{
			cellhead6=new PdfPCell(new Phrase("TOTAL = ",new Font(Font.TIMES_ROMAN,10)));
			cellhead6.setColspan(6);
			cellhead6.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table1.addCell(cellhead6);
			cellhead7=new PdfPCell(new Phrase(""+totcount,new Font(Font.TIMES_ROMAN,10)));
			table1.addCell(cellhead7);
		}
		else
		{
					
			cellhead6=new PdfPCell(new Phrase("No Record Found !",new Font(Font.TIMES_ROMAN,11)));
			cellhead6.setColspan(7);
			
		}
		
		doc.add(table1);
		doc.close();
		file.close();

		
	}
	catch (Exception e)
	{
		e.printStackTrace();
		el.errorLog("Error in GratuityDAO- setGratuityAMtforAll() Report Generation-------", e.toString());
	}
	doc.open();
	
	Cn.close();
	pst.close();
	}
	catch(Exception e)
	{
		System.out.println("Error in GratuityDAO- setGratuityAMtforAll() -------\n"+e);
		el.errorLog("Error in GratuityDAO- setGratuityAMtforAll() -------", e.toString());
		
	}
	}
}