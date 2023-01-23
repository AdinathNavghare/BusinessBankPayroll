package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import payroll.Model.UserMedicalBean;

public class UserMedicalHandler 
{
	public boolean addMedicalbill(UserMedicalBean mbean) 
	{
		String max="select max(TRANID) from medical ";
		boolean f=false;
		Connection con=ConnectionManager.getConnection();
		ResultSet rs=null;
		int tranid=1;
		String addbillquery="";
		try
		{
			  Statement st=con.createStatement();
			  rs=st.executeQuery(max);
			  rs.next();
			  tranid=tranid+rs.getInt(1);
			  addbillquery="INSERT INTO medical VALUES('"+mbean.getEMPNO()+"','"+tranid+"','"+mbean.getTRANDATE()+"','"+mbean.getRELATION()+"','"+mbean.getAMOUNT()+"','"+mbean.getFROMDATE()+"','"+mbean.getTODATE()+"','"+mbean.getDESCRIPTION()+"')";
			  int i=st.executeUpdate(addbillquery);
			  if(i==1)
			  {
				  f=true;
			  }
			  con.close();
		}
		catch (Exception e)
		{
		  e.printStackTrace();
		}
		return f;
	}

	public ArrayList<UserMedicalBean> getMedicalList(UserMedicalBean mbean) 
	{
	   ArrayList<UserMedicalBean>list=new ArrayList<>();
	   Connection con=ConnectionManager.getConnection();
	   ResultSet rs1=null;
	   String record="SELECT * FROM MEDICAL ORDER BY TRANDATE DESC ";
	   try
	   {
			 Statement st1=con.createStatement();
			 rs1=st1.executeQuery(record);
			 while(rs1.next())
			 {
				 UserMedicalBean umbean=new UserMedicalBean();
				 umbean.setEMPNO(rs1.getString(1)!=null?rs1.getInt(1):0);
				 umbean.setTRANID(rs1.getString(2)!=null?rs1.getInt(2):0);
				 umbean.setTRANDATE(rs1.getString(3)!=null?dateFormat(rs1.getDate(3)):"");
				 umbean.setRELATION(rs1.getString(4)!=null?rs1.getString(4):"");
				 umbean.setAMOUNT(rs1.getString(5)!=null?rs1.getInt(5):0);
				 umbean.setFROMDATE(rs1.getString(6)!=null?dateFormat(rs1.getDate(6)):"");
				 umbean.setTODATE(rs1.getString(7)!=null?dateFormat(rs1.getDate(7)):"");
				 umbean.setDESCRIPTION(rs1.getString(8)!=null?rs1.getString(8):"");
				 list.add(umbean);
			 }
			 con.close();
		}
	    catch (Exception e) 
	    {
			e.printStackTrace();
		}
	   return list;
	}
	
	public static String dateFormat(Date dates)
	{
		String result="";
		if(dates==null)
		{
			return result;
		}
		SimpleDateFormat format= new SimpleDateFormat("dd-MMM-yyyy");
		result=format.format(dates);
		return result;
	}
}