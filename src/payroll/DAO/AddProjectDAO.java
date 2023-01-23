package payroll.DAO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import payroll.Core.UtilityDAO;
import payroll.Core.UtilityDAO.Footer;
import payroll.Model.AddProjectBean;
import payroll.Model.AddTaskBean;

public class AddProjectDAO {
	
	static Connection con=null,con1=null;
	static String condition="Active";
	
	public static boolean addProject(AddProjectBean addbean) {
		
		con = ConnectionManager.getConnection();
		ResultSet rs;
		Boolean flag = false;
		PreparedStatement ps = null;	
		try{
			String str="insert into AddProject(PType,Pstatus,PPriority,ProjectName,LiveUrl,TestUrl,StartDate,EndDate,ProDescription,PCONDITION)VALUES"
					+ "("
					+ "' "
					+ addbean.getPROTYPE()
					+ "', "
					+ "' "
					+ addbean.getPROSTATUS()
					+ "' "
					+ " ,'"
					+ addbean.getPROPRIORITY()
					+ "' "
					+ " ,'"
					+ addbean.getPRONAME()
					+ "' "
					+ " ,'"
					+ addbean.getLIVEURL()
					+ "' "
					+ " ,'"
					+ addbean.getTESTURL()
					+ "' "
					+ " ,'"
					+ addbean.getREVIEWDATE()
					+ "' "
					+ " ,'"
					+ addbean.getDESIGNDATE()
					+ "' "
					+ " ,'"
					+ addbean.getPRODESCRIPTION()
					+ "' "
					+ " ,'"
					+condition
					+"')";
			/*
			 String str="insert into AddProject(PType,Pstatus,PPriority,ProjectName,LiveUrl,TestUrl,ReviewDate,DesignDate,DevelopDate,SiteTestDate,UATDate,ProDescription,PCONDITION)VALUES"
					+ "("
					+ "' "
					+ addbean.getPROTYPE()
					+ "', "
					+ "' "
					+ addbean.getPROSTATUS()
					+ "' "
					+ " ,'"
					+ addbean.getPROPRIORITY()
					+ "' "
					+ " ,'"
					+ addbean.getPRONAME()
					+ "' "
					+ " ,'"
					+ addbean.getLIVEURL()
					+ "' "
					+ " ,'"
					+ addbean.getTESTURL()
					+ "' "
					+ " ,'"
					+ addbean.getREVIEWDATE()
					+ "' "
					+ " ,'"
					+ addbean.getDESIGNDATE()
					+ "' "
					+ " ,'"
					+ addbean.getDEVELOPDATE()
					+ "' "
					+ " ,'"
					+ addbean.getSITETESTDATE()
					+ "' "
					+ " ,'"
					+ addbean.getUATDATE()
					+ "' "
					+ " ,'"
					+ addbean.getPRODESCRIPTION()
					+ "' "
					+ " ,'"
					+condition
					+ "')";
			*/
			ps = con.prepareStatement(str);
			ps.executeUpdate();
			
			/*
			 * Statement st=con.createStatement(); st.execute("update ATTACHMENT");
			 */
			System.out.println(str);
			flag=true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	/*public static int SubToEmp(AddProjectBean addbean)
	{
		con = ConnectionManager.getConnection();
		ResultSet rs;
		System.out.println("connection");
		int flag = 0;
		PreparedStatement ps = null;
		
		try {
			String sql1="select Emp_Id from assigner where Sub_Id="+addbean.getSUBTID();
					 
			/*String sql1="insert into Team1(ProjectId,Team_Members) values"
					  + "("
					  + "( SELECT TOP(1) ProjectId FROM AddProject ORDER BY 1 DESC)".trim() 	
					  +",'"
					  + addbean.getPROJECTTEAM()
					  + "')";*/
			/*ps=con.prepareStatement(sql1);
			
			ps.executeUpdate();	
			System.out.println(sql1);
			flag=1;
			
			//ps.close();
			//con.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
		
	}*/

public static int addTeam(AddProjectBean addbean)
{
	con = ConnectionManager.getConnection();
	ResultSet rs;
	System.out.println("connection");
	int flag = 0;
	PreparedStatement ps = null;
	
	try {
		String sql1="insert into Assigner(Sub_Id,Emp_Id) values"
				  + "("
				  + addbean.getSUBTID() 	
				  +","
				  + addbean.getEMPID()
				  + ")";
		/*String sql1="insert into Team1(ProjectId,Team_Members) values"
				  + "("
				  + "( SELECT TOP(1) ProjectId FROM AddProject ORDER BY 1 DESC)".trim() 	
				  +",'"
				  + addbean.getPROJECTTEAM()
				  + "')";*/
		ps=con.prepareStatement(sql1);
		
		ps.executeUpdate();	
		System.out.println(sql1);
		flag=1;
		
		//ps.close();
		//con.close();
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return flag;
	
}

public static void genPDF(String filePath, String imagepath,String OfferDate, String Empname, String JoinDate, String ma, String ca, String ea
		, String cca, String pt, String profund1, String profund2, String esi1, String esi2, String AnumSal, String gender, String EmpContri, String net, String totaldeduc
		, String ctcMon, String ctcAnnum, String desig, String incentive, String gross, String skill, String hra, String basic, String ProbPeriod, String chkBox)
		throws DocumentException, MalformedURLException, IOException, SQLException {
	
	System.out.println("i am in handler.."+filePath);
	

	System.out.println("i am in BonusList 01");
	OutputStream file = new FileOutputStream(new File(filePath));
	Document document = new Document();
	
	
	String lable1="Report date : ";
	
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
		Phrase title = new Phrase("Business Bank",new Font(Font.TIMES_ROMAN,9,Font.BOLD));
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
		
		
		para = new Paragraph(new Phrase("Date :"+OfferDate+"",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
		para.setAlignment(Element.ALIGN_LEFT);
		para.setSpacingAfter(0);
		document.add(para);
		
		System.out.println(desig+"helooooooooo");
		
		para = new Paragraph(new Phrase("To,Mr./Ms. :'"+Empname+"'",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
		para.setAlignment(Element.ALIGN_LEFT);
		para.setSpacingAfter(0);
		document.add(para);
		
		para = new Paragraph(new Phrase("OFFER OF EMPLOYMENT",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
		para.setAlignment(Element.ALIGN_LEFT);
		para.setSpacingAfter(0);
		document.add(para);
		
		para = new Paragraph(new Phrase("Dear '"+Empname+"'",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
		para.setAlignment(Element.ALIGN_LEFT);
		para.setSpacingAfter(0);
		document.add(para);
		
		para = new Paragraph(new Phrase("With reference to your application and the subsequent discussions that you had with us, we are pleased to offer you employment in our organization as '"+desig+"'based at'"+skill+"'.However, the position may be transferable to any other location / office / site of the company or Subsidiary / Associate companies of SysTech.",new Font(Font.TIMES_ROMAN,9,Font.BOLD)));
		para.setAlignment(Element.ALIGN_JUSTIFIED);
		para.setSpacingAfter(0);
		document.add(para);
				
		
		document.close();
		file.close();
		//con.close();

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

public ArrayList<AddProjectBean> getTypes(String types)
{
	ArrayList<AddProjectBean> result=new ArrayList<AddProjectBean>();
	String sql1="";
	System.out.println("gettypes"+types);
	try
	{
		if(types.equals("Pstatus"))
		{
			
			sql1="select distinct Pstatus from AddProject;";
			System.out.println(sql1);
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(sql1);
			while(rs.next())
			{
				AddProjectBean apb=new AddProjectBean();
				apb.setPROSTATUS(rs.getString("Pstatus")!=null?rs.getString("Pstatus"):"");
				result.add(apb);
			}
		}
		else if(types.equals("PPriority"))
		{
			sql1="select distinct PPriority from AddProject;";
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(sql1);
			while(rs.next())
			{
				AddProjectBean apb=new AddProjectBean();
				apb.setPROPRIORITY(rs.getString("PPriority")!=null?rs.getString("PPriority"):"");
				result.add(apb);
			}
		}
		
		else
		{
				/*
				 * sql1="select distinct Pstatus PPriority from AddProject;"; Connection
				 * con=ConnectionManager.getConnection(); Statement st=con.createStatement();
				 * ResultSet rs=st.executeQuery(sql1); while(rs.next()) { AddProjectBean apb=new
				 * AddProjectBean();
				 * apb.setPROSTATUS(rs.getString("Pstatus")!=null?rs.getString("Pstatus"):"");
				 * apb.setPROPRIORITY(rs.getString("PPriority")!=null?rs.getString("PPriority"):
				 * ""); result.add(apb); } sql1="select distinct PPriority from AddProject;";
				 * con=ConnectionManager.getConnection(); Statement st1=con.createStatement();
				 * ResultSet rs1=st1.executeQuery(sql1); while(rs1.next()) { AddProjectBean
				 * apb=new AddProjectBean();
				 * apb.setPROSTATUS(rs.getString("Pstatus")!=null?rs.getString("Pstatus"):"");
				 * apb.setPROPRIORITY(rs.getString("PPriority")!=null?rs.getString("PPriority"):
				 * ""); result.add(apb); }
				 */
			System.out.println("You select All Option");
		}
		
	}
	catch(SQLException e)
	{
		e.printStackTrace();
	}
	return result;
}

public ArrayList<AddProjectBean> viewProject()
{
	
	String sql1="select * from AddProject where  PCONDITION='Active'";
			
	PreparedStatement pst=null;
	ResultSet rs=null;
	try
	{
		con=ConnectionManager.getConnection();
		pst=con.prepareStatement(sql1);
	ArrayList<AddProjectBean> li=new ArrayList<AddProjectBean>();
		rs=pst.executeQuery();
		
		while(rs.next())
		{
			AddProjectBean apb=new AddProjectBean();
			apb.setPROID(rs.getInt("ProjectId"));
			apb.setPRONAME(rs.getString("ProjectName"));
			apb.setPROTYPE(rs.getString("PType"));
			apb.setPROSTATUS(rs.getString("Pstatus"));
			apb.setPROPRIORITY(rs.getString("PPriority"));
			apb.setREVIEWDATE(rs.getString("StartDate"));
			apb.setDESIGNDATE(rs.getString("EndDate"));
			apb.setLIVEURL(rs.getString("LiveUrl"));
			
			li.add(apb);
		}
		
		pst.close();
		rs.close();
		con.close();
		return li;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;
}
public ArrayList<AddProjectBean> open()
{
	
	String sql1="   select * from AddProject where  Pstatus=' Open' or Pstatus='Open'  and PCONDITION='Active'";
			
	PreparedStatement pst=null;
	ResultSet rs=null;
	try
	{
		con=ConnectionManager.getConnection();
		pst=con.prepareStatement(sql1);
	ArrayList<AddProjectBean> li=new ArrayList<AddProjectBean>();
		rs=pst.executeQuery();
		
		while(rs.next())
		{
			AddProjectBean apb=new AddProjectBean();
			apb.setPROID(rs.getInt("ProjectId"));
			apb.setPRONAME(rs.getString("ProjectName"));
			apb.setPROTYPE(rs.getString("PType"));
			apb.setPROSTATUS(rs.getString("Pstatus"));
			apb.setPROPRIORITY(rs.getString("PPriority"));
			apb.setREVIEWDATE(rs.getString("StartDate"));
			apb.setDESIGNDATE(rs.getString("EndDate"));
			apb.setLIVEURL(rs.getString("LiveUrl"));
			
			li.add(apb);
		}
		
		pst.close();
		rs.close();
		con.close();
		return li;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;
}
public ArrayList<AddProjectBean> cancel()
{
	
	String sql1="select * from AddProject where  PCONDITION='Active' and Pstatus=' Cancelled' or Pstatus='Cancelled'";
			
	PreparedStatement pst=null;
	ResultSet rs=null;
	try
	{
		con=ConnectionManager.getConnection();
		pst=con.prepareStatement(sql1);
	ArrayList<AddProjectBean> li=new ArrayList<AddProjectBean>();
		rs=pst.executeQuery();
		
		while(rs.next())
		{
			AddProjectBean apb2=new AddProjectBean();
			apb2.setPROID(rs.getInt("ProjectId"));
			apb2.setPRONAME(rs.getString("ProjectName"));
			apb2.setPROTYPE(rs.getString("PType"));
			apb2.setPROSTATUS(rs.getString("Pstatus"));
			System.out.println(rs.getString("Pstatus"));
			apb2.setPROPRIORITY(rs.getString("PPriority"));
			apb2.setREVIEWDATE(rs.getString("StartDate"));
			apb2.setDESIGNDATE(rs.getString("EndDate"));
			apb2.setLIVEURL(rs.getString("LiveUrl"));
			
			li.add(apb2);
		}
		
		pst.close();
		rs.close();
		con.close();
		return li;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;
}
public ArrayList<AddProjectBean> it()
{
	
	String sql1=" select * from AddProject where  PType='It' or PType=' It' and PCONDITION='Active'";
			
	PreparedStatement pst=null;
	ResultSet rs=null;
	try
	{
		con=ConnectionManager.getConnection();
		pst=con.prepareStatement(sql1);
	ArrayList<AddProjectBean> li=new ArrayList<AddProjectBean>();
		rs=pst.executeQuery();
		
		while(rs.next())
		{
			AddProjectBean apb2=new AddProjectBean();
			apb2.setPROID(rs.getInt("ProjectId"));
			apb2.setPRONAME(rs.getString("ProjectName"));
			apb2.setPROTYPE(rs.getString("PType"));
			apb2.setPROSTATUS(rs.getString("Pstatus"));
			System.out.println(rs.getString("Pstatus"));
			apb2.setPROPRIORITY(rs.getString("PPriority"));
			apb2.setREVIEWDATE(rs.getString("StartDate"));
			apb2.setDESIGNDATE(rs.getString("EndDate"));
			apb2.setLIVEURL(rs.getString("LiveUrl"));
			
			li.add(apb2);
		}
		System.out.println("ar4545 : "+li); 
		pst.close();
		rs.close();
		con.close();
		return li;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;
}
public ArrayList<AddProjectBean> support()
{
	
	String sql1=" select * from AddProject where  PType='Support' or PType=' Support' and PCONDITION='Active'";
			
	PreparedStatement pst=null;
	ResultSet rs=null;
	try
	{
		con=ConnectionManager.getConnection();
		pst=con.prepareStatement(sql1);
	ArrayList<AddProjectBean> li=new ArrayList<AddProjectBean>();
		rs=pst.executeQuery();
		
		while(rs.next())
		{
			AddProjectBean apb2=new AddProjectBean();
			apb2.setPROID(rs.getInt("ProjectId"));
			apb2.setPRONAME(rs.getString("ProjectName"));
			apb2.setPROTYPE(rs.getString("PType"));
			apb2.setPROSTATUS(rs.getString("Pstatus"));
			System.out.println(rs.getString("Pstatus"));
			apb2.setPROPRIORITY(rs.getString("PPriority"));
			apb2.setREVIEWDATE(rs.getString("StartDate"));
			apb2.setDESIGNDATE(rs.getString("EndDate"));
			apb2.setLIVEURL(rs.getString("LiveUrl"));
			
			li.add(apb2);
		}
		System.out.println("ar4545 : "+li); 
		pst.close();
		rs.close();
		con.close();
		return li;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;
}
public ArrayList<AddProjectBean> newsite()
{
	
	String sql1=" select * from AddProject where  PType='NewSite' or PType=' NewSite' and PCONDITION='Active'";
			
	PreparedStatement pst=null;
	ResultSet rs=null;
	try
	{
		con=ConnectionManager.getConnection();
		pst=con.prepareStatement(sql1);
	ArrayList<AddProjectBean> li=new ArrayList<AddProjectBean>();
		rs=pst.executeQuery();
		
		while(rs.next())
		{
			AddProjectBean apb2=new AddProjectBean();
			apb2.setPROID(rs.getInt("ProjectId"));
			apb2.setPRONAME(rs.getString("ProjectName"));
			apb2.setPROTYPE(rs.getString("PType"));
			apb2.setPROSTATUS(rs.getString("Pstatus"));
			System.out.println(rs.getString("Pstatus"));
			apb2.setPROPRIORITY(rs.getString("PPriority"));
			apb2.setREVIEWDATE(rs.getString("StartDate"));
			apb2.setDESIGNDATE(rs.getString("EndDate"));
			apb2.setLIVEURL(rs.getString("LiveUrl"));
			
			li.add(apb2);
		}
		System.out.println("ar4545 : "+li); 
		pst.close();
		rs.close();
		con.close();
		return li;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;
}
public ArrayList<AddProjectBean> internal()
{
	
	String sql1=" select * from AddProject where  PType='Internal' or PType=' Internal' and PCONDITION='Active'";
			
	PreparedStatement pst=null;
	ResultSet rs=null;
	try
	{
		con=ConnectionManager.getConnection();
		pst=con.prepareStatement(sql1);
	ArrayList<AddProjectBean> li=new ArrayList<AddProjectBean>();
		rs=pst.executeQuery();
		
		while(rs.next())
		{
			AddProjectBean apb2=new AddProjectBean();
			apb2.setPROID(rs.getInt("ProjectId"));
			apb2.setPRONAME(rs.getString("ProjectName"));
			apb2.setPROTYPE(rs.getString("PType"));
			apb2.setPROSTATUS(rs.getString("Pstatus"));
			System.out.println(rs.getString("Pstatus"));
			apb2.setPROPRIORITY(rs.getString("PPriority"));
			apb2.setREVIEWDATE(rs.getString("StartDate"));
			apb2.setDESIGNDATE(rs.getString("EndDate"));
			apb2.setLIVEURL(rs.getString("LiveUrl"));
			
			li.add(apb2);
		}
		System.out.println("ar4545 : "+li); 
		pst.close();
		rs.close();
		con.close();
		return li;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;
}
public ArrayList<AddProjectBean> onhold()
{
	
	String sql1=" select * from AddProject where  PCONDITION='Active' and Pstatus=' OnHold' or Pstatus='OnHold'";
			
	PreparedStatement pst=null;
	ResultSet rs=null;
	try
	{
		con=ConnectionManager.getConnection();
		pst=con.prepareStatement(sql1);
	ArrayList<AddProjectBean> li=new ArrayList<AddProjectBean>();
		rs=pst.executeQuery();
		
		while(rs.next())
		{
			AddProjectBean apb2=new AddProjectBean();
			apb2.setPROID(rs.getInt("ProjectId"));
			apb2.setPRONAME(rs.getString("ProjectName"));
			apb2.setPROTYPE(rs.getString("PType"));
			apb2.setPROSTATUS(rs.getString("Pstatus"));
			System.out.println(rs.getString("Pstatus"));
			apb2.setPROPRIORITY(rs.getString("PPriority"));
			apb2.setREVIEWDATE(rs.getString("StartDate"));
			apb2.setDESIGNDATE(rs.getString("EndDate"));
			apb2.setLIVEURL(rs.getString("LiveUrl"));
			
			li.add(apb2);
		}
		System.out.println("ar4545 : "+li); 
		pst.close();
		rs.close();
		con.close();
		return li;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;
}
public ArrayList<AddProjectBean> close()
{
	
	String sql1=" select * from AddProject where  PCONDITION='Active' and Pstatus=' Closed' or Pstatus='Closed'";
			
	PreparedStatement pst=null;
	ResultSet rs=null;
	try
	{
		con=ConnectionManager.getConnection();
		pst=con.prepareStatement(sql1);
	ArrayList<AddProjectBean> li=new ArrayList<AddProjectBean>();
		rs=pst.executeQuery();
		
		while(rs.next())
		{
			AddProjectBean apb2=new AddProjectBean();
			apb2.setPROID(rs.getInt("ProjectId"));
			apb2.setPRONAME(rs.getString("ProjectName"));
			apb2.setPROTYPE(rs.getString("PType"));
			apb2.setPROSTATUS(rs.getString("Pstatus"));
			System.out.println(rs.getString("Pstatus"));
			apb2.setPROPRIORITY(rs.getString("PPriority"));
			apb2.setREVIEWDATE(rs.getString("StartDate"));
			apb2.setDESIGNDATE(rs.getString("EndDate"));
			apb2.setLIVEURL(rs.getString("LiveUrl"));
			
			li.add(apb2);
		}
		System.out.println("ar4545 : "+li); 
		pst.close();
		rs.close();
		con.close();
		return li;
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return null;
}
public ArrayList<AddProjectBean> getProjectDetail()
{
	ArrayList<AddProjectBean> result = new ArrayList<AddProjectBean>();
	
	try{
		Connection con=ConnectionManager.getConnection();
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery("select ProjectId,PType,Pstatus,ProjectName,PPriority ,StartDate from AddProject where PCONDITION='Active'");
		while(rs.next())
		{
			
			AddProjectBean addbean =new AddProjectBean();
			addbean.setPROID((int) (rs.getInt("ProjectId")!=0?rs.getInt("ProjectId"):""));
			addbean.setPROTYPE(rs.getString("PType")!=null?rs.getString("PType"):"");
			addbean.setPROSTATUS(rs.getString("Pstatus")!=null?rs.getString("Pstatus"):"");
			addbean.setPRONAME(rs.getString("ProjectName")!=null?rs.getString("ProjectName"):"");
			addbean.setPROPRIORITY(rs.getString("PPriority")!=null?rs.getString("PPriority"):"");
			addbean.setREVIEWDATE(rs.getString("StartDate")!=null?rs.getString("StartDate"):"");
			
			result.add(addbean);
		}
		con.close();
	}catch(SQLException e)
	{
		e.printStackTrace();
	}
	return result;
	
}
public ArrayList<AddProjectBean> getProjectDetailEdit(int pid)
{
	ArrayList<AddProjectBean> result = new ArrayList<AddProjectBean>();
	
	try{
		Connection con=ConnectionManager.getConnection();
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery("select * from AddProject where ProjectId="+pid);
		while(rs.next())
		{
			
			AddProjectBean addbean =new AddProjectBean();
			//addbean.setPROID((int) (rs.getInt(1)!=0?rs.getInt(1):""));
			addbean.setPROTYPE(rs.getString("PType")!=null?rs.getString("PType"):"");
			addbean.setPROSTATUS(rs.getString("Pstatus")!=null?rs.getString("Pstatus"):"");
			addbean.setPROPRIORITY(rs.getString("PPriority")!=null?rs.getString("PPriority"):"");
			addbean.setPRONAME(rs.getString("ProjectName")!=null?rs.getString("ProjectName"):"");
			addbean.setLIVEURL(rs.getString("LiveUrl")!=null?rs.getString("LiveUrl"):"");
			addbean.setTESTURL(rs.getString("TestUrl")!=null?rs.getString("TestUrl"):"");
			addbean.setREVIEWDATE(rs.getString("StartDate")!=null?rs.getString("StartDate"):"");
			addbean.setDESIGNDATE(rs.getString("EndDate")!=null?rs.getString("EndDate"):"");
			addbean.setPRODESCRIPTION(rs.getString("ProDescription")!=null?rs.getString("ProDescription"):"");
			
			
			result.add(addbean);
		}
		con.close();
	}catch(SQLException e)
	{
		e.printStackTrace();
	}
	return result;
	
}
public static boolean updateTeam(AddProjectBean addbean)
{
	con = ConnectionManager.getConnection();
	ResultSet rs;
	System.out.println("connection");
	Boolean flag = false;
	PreparedStatement ps = null;
	
	try {
		String sql1="insert into Team1(Sub_Id,Emp_Id) values"
				  + "("
				  + addbean.getSUBTID() 	
				  +",'"
				  + addbean.getEMPID()
				  + "')";
		ps=con.prepareStatement(sql1);
		
		ps.executeUpdate();	
		System.out.println(sql1);
		flag=true;
		
		System.out.println(flag);
		//ps.close();
		//con.close();
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return flag;
	
}
public static boolean addUpload(AddProjectBean addbean)
{
	con = ConnectionManager.getConnection();
		/* ResultSet rs; */
	Boolean flag = false;
	PreparedStatement ps = null;
	try
	{
	String sql="insert into ATTACHMENT1(ProjectId,FILE_NAME,DESCRIPTION) values"
			  + "("
			  + "( SELECT TOP(1) ProjectId FROM AddProject ORDER BY 1 DESC)" 	
			  +",'"
			  + addbean.getFILE_NAME()
			  +",'"
			  + addbean.getPRODESCRIPTION()
			  + "')";
	
	ps=con.prepareStatement(sql);
	System.out.println("peruuuuuuu");
	ps.executeUpdate();
	
	System.out.println(sql);
	flag=true;
	
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return flag;
	
}
	public static boolean updateProject(AddProjectBean addbean,int id)  //For Edit 
	{
			con = ConnectionManager.getConnection();
			con1 = ConnectionManager.getConnection();
			//ResultSet rs,rs1;
			Boolean flag = false;
			PreparedStatement ps = null,ps1=null;
			Statement st=null;
			
			
			try
			{
				String sql="update AddProject set PType='" + addbean.getPROTYPE()
				+ "',ProjectName='" + addbean.getPRONAME()
				+ "',Pstatus='"+ addbean.getPROSTATUS() 
				+ "',PPriority='"+ addbean.getPROPRIORITY() 
				+ "',LiveUrl='" + addbean.getLIVEURL()
				+ "',ProDescription='" + addbean.getPRODESCRIPTION() 
				+ "',TestUrl='"+ addbean.getTESTURL() 
				+ "',StartDate='"+ addbean.getREVIEWDATE()
				+ "',EndDate='"+ addbean.getDESIGNDATE()
				+ "',ATTACHPATH='"+ addbean.getATTACHMENT_PATH()
				+ "',FILENAME='"+ addbean.getFILE_NAME()
				+"'where ProjectId="+id;
				
				ps=con.prepareStatement(sql);
				ps.executeUpdate();
				ps.close();
				
				
				
				/*st=con1.createStatement();
				String sql="update AddProject set PCONDITION='Inactive' where ProjectId="+id;
				ps=con.prepareStatement(sql);
				ps.executeUpdate();
				ps.close();
				
				String str="insert into AddProject(PType,Pstatus,PPriority,ProjectName,LiveUrl,TestUrl,StartDate,EndDate,ProDescription,PCONDITION)VALUES"
						+ "("
						+ "' "
						+ addbean.getPROTYPE()
						+ "', "
						+ "' "
						+ addbean.getPROSTATUS()
						+ "' "
						+ " ,'"
						+ addbean.getPROPRIORITY()
						+ "' "
						+ " ,'"
						+ addbean.getPRONAME()
						+ "' "
						+ " ,'"
						+ addbean.getLIVEURL()
						+ "' "
						+ " ,'"
						+ addbean.getTESTURL()
						+ "' "
						+ " ,'"
						+ addbean.getREVIEWDATE()
						+ "' "
						+ " ,'"
						+ addbean.getDESIGNDATE()
						+ "' "
						+ " ,'"
						+ addbean.getPRODESCRIPTION()
						+ "' "
						+ " ,'"
						+condition
						+ "')";
				 
				 
				 st.execute(str);
				System.out.println("First"+flag);*/
				flag=true;

				System.out.println("second"+flag);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		return flag;		
	}
	public static int DeleteProject(int pid) {
		System.out.println(pid);
		int flag=0;
		Statement st=null;
		try{
			Connection con=ConnectionManager.getConnection();
			st=con.createStatement();
			
			/*st.executeQuery("update AddProject set PCONDITION='Inactive' where ProjectId="+pid);*/
			
			st.executeQuery("delete from AddProject where ProjectId="+pid);
			
			/*
			 * +"delete from Team1 where ProjectId="+pid+"
			 */			
			flag=1;
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	public static int DeleteProject1(int pid) {
		System.out.println(pid);
		int flag=0;
		Statement st=null;
		try{
			Connection con=ConnectionManager.getConnection();
			st=con.createStatement();
			
			st.executeQuery("update AddProject set PCONDITION='Inactive' where ProjectId="+pid);
			
			//st.executeQuery("delete from AddProject where ProjectId="+pid);
			
			/*
			 * +"delete from Team1 where ProjectId="+pid+"
			 */			
			flag=1;
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public ArrayList<AddProjectBean> getProjectName()
	{
		ArrayList<AddProjectBean> result=new ArrayList<AddProjectBean>();
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select ProjectName,ProjectId from AddProject");
			while(rs.next())
			{
				AddProjectBean pro=new AddProjectBean();
				 pro.setPRONAME(rs.getString(1)!=null?rs.getString(1):"");
				 pro.setPROID(rs.getString(2)!=null?rs.getInt(2):0);
				   /*lkp.setLKP_CODE(rs.getString(1)!=null?rs.getString(1):"");
					lkp.setLKP_SRNO(rs.getString(2)!=null?rs.getInt(2):0);
					lkp.setLKP_DESC(rs.getString(3)!=null?rs.getString(3):"");
					lkp.setLKP_RECR(rs.getString(4)!=null?rs.getInt(4):0);   */
				
				result.add(pro);
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<AddProjectBean> getProjectDesig()
	{
		ArrayList<AddProjectBean> result=new ArrayList<AddProjectBean>();
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select Distinct Designation from Employee_PM");
			while(rs.next())
			{
				AddProjectBean pro=new AddProjectBean();
				 pro.setDESIGNATION(rs.getString(1)!=null?rs.getString(1):"");
				// pro.setPROID(rs.getString(2)!=null?rs.getInt(2):0);
				   /*lkp.setLKP_CODE(rs.getString(1)!=null?rs.getString(1):"");
					lkp.setLKP_SRNO(rs.getString(2)!=null?rs.getInt(2):0);
					lkp.setLKP_DESC(rs.getString(3)!=null?rs.getString(3):"");
					lkp.setLKP_RECR(rs.getString(4)!=null?rs.getInt(4):0);   */
				
				result.add(pro);
			}
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	

	public ArrayList<AddProjectBean> getReport(String type, String subtype, String fromDate, String toDate) {
		
		AddProjectBean apb=new AddProjectBean();
		if(type.equals("All"))
		{
			
		}
		else
		{
			try
			{
					Connection con=ConnectionManager.getConnection();
					String sql1="select * from AddProject where StartDate between '"+fromDate+"' and '"+toDate+"' and "+type+"='"+subtype+"'";
					PreparedStatement pst=con.prepareStatement(sql1);
					ResultSet rs=pst.executeQuery();
					while(rs.next())
					{
						apb.setPROID(rs.getInt("ProjectId"));
						apb.setPRONAME(rs.getString("ProjectName"));
						apb.setPROTYPE(rs.getString("PType"));
						apb.setPROSTATUS(rs.getString("Pstatus"));
						apb.setPROPRIORITY(rs.getString("PPriority"));
						apb.setREVIEWDATE(rs.getString("StartDate"));
						apb.setDESIGNDATE(rs.getString("EndDate"));
						apb.setLIVEURL(rs.getString("LiveUrl"));
					}
			
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			
			
			
			
		}
		return null;
	}
}