package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import payroll.Model.ExtraFieldBean;
import payroll.Model.OtherDetailBean;

public class ExtraFieldHandler 
{
	public boolean insertData(ExtraFieldBean efbean)
	{
		boolean flag=false;
		try
		{
			Connection conn = ConnectionManager.getConnection();
			Statement st = conn.createStatement();
			st.executeUpdate("Update FIELDTAB set FIELD_NAME="+(efbean.getFieldName()==""?"null":"'"+efbean.getFieldName()+"'")+",FIELD_DISC="+(efbean.getFieldDesc()==""?"null":"'"+efbean.getFieldDesc()+"'")+" where COLUMNNAME='"+efbean.getColumnName()+"' ");
			flag=true;
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}
	
	public ArrayList<ExtraFieldBean> getlist()
	{
		ArrayList<ExtraFieldBean> exlist = new ArrayList<ExtraFieldBean>();
		ExtraFieldBean exbean;
		try
		{
			Connection conn = ConnectionManager.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("Select * from FIELDTAB Order by FIELDNO");
			while(rs.next())
			{
				exbean = new ExtraFieldBean();
				exbean.setFieldNo(rs.getInt("FIELDNO"));
				exbean.setFieldName(rs.getString("FIELD_NAME")==null?"":rs.getString("FIELD_NAME"));
				exbean.setFieldDesc(rs.getString("FIELD_DISC")==null?"":rs.getString("FIELD_DISC"));
				exbean.setColumnName(rs.getString("COLUMNNAME"));
				exlist.add(exbean);
			}
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return exlist;
	}
	
	
	public ArrayList<ExtraFieldBean> getFieldNumber()
	{
		ArrayList<ExtraFieldBean> exlist = new ArrayList<ExtraFieldBean>();
		ExtraFieldBean exbean;
		try
		{
			Connection conn = ConnectionManager.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from FIELDTAB  where FIELD_NAME IS NOT NULL Order by FIELDNO ");
			while(rs.next())
			{
				exbean = new ExtraFieldBean();
				exbean.setFieldNo(rs.getInt("FIELDNO"));
				exbean.setFieldName(rs.getString("FIELD_NAME"));
				exbean.setFieldDesc(rs.getString("FIELD_DISC"));
				exbean.setType(rs.getInt("type"));
				exbean.setColumnName(rs.getString("COLUMNNAME"));
				exlist.add(exbean);
			}
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return exlist;
	}
	
	public boolean insertOtherDetail(Object[] a,String empno)
	{
		boolean flag=false;
		ArrayList<ExtraFieldBean> exlist = getFieldNumber();
		String cols="";
		String vals="";
		int empno1=Integer.parseInt(empno==null?"0":empno);
		for(ExtraFieldBean exbean : exlist)
		{
			cols=cols+" "+exbean.getColumnName()+",";
			if(exbean.getType()==1||exbean.getType()==3)
			{
				vals=vals+" '"+a[exbean.getFieldNo()]+"',";
			}
			if(exbean.getType()==2)
			{
				vals=vals+" "+Integer.parseInt(a[exbean.getFieldNo()].toString())+",";
			}
			
		}
		cols=cols.substring(0,cols.length()-1);
		vals=vals.substring(0,vals.length()-1);
		try
		{
			Connection conn = ConnectionManager.getConnection();
			Statement st = conn.createStatement();
			String qry = "insert into OTHERDETAIL (EMPNO,"+cols+") values("+empno1+","+vals+")";
			st.execute(qry);
			flag=true;
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}
	
	public int getFieldCountOT()//OT-othertable able
	{
		int count=0;
		try
		{
			Connection conn = ConnectionManager.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select count(*) from FIELDTAB ");
			while(rs.next())
			{
				count=rs.getInt(1)+1;
			}
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return count;
	}
	
	public OtherDetailBean getOtherDetail(String empno)//OT Other Detail Table Values 
	{
		OtherDetailBean otbean = null;
		try
		{
			Connection conn = ConnectionManager.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("Select * from OtherDetail where empno="+empno);
			int count = getFieldCountOT();
			otbean = new OtherDetailBean(count);
			if(rs.next())
			{
				for(int i=1;i<count;i++)
				{
					
					otbean.setField((rs.getString(i+1)==null?"":rs.getString(i+1)), i);
					if(i>15)
					{
						if(rs.getString(i+1)!=null)
						{
						otbean.setField((EmpExperHandler.dateFormat(rs.getDate(i+1))), i);
						}
					}
				}
			}
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return otbean;
	}
	
	public boolean updateOtherDetail(Object[] a,String empno)
	{
		System.out.println("CHECK IT updateOtherDetail");
		boolean flag = false;
		ArrayList<ExtraFieldBean> exlist = getFieldNumber();
		int empno1=Integer.parseInt(empno==null?"0":empno);
		String data="";
		for(ExtraFieldBean exbean : exlist)
		{
			if(exbean.getType()==1||exbean.getType()==3)
			{
				data=data+exbean.getColumnName()+"='"+a[exbean.getFieldNo()].toString()+"',";
			}
			if(exbean.getType()==2)
			{
				data=data+exbean.getColumnName()+"="+Double.parseDouble(a[exbean.getFieldNo()].toString())+",";
			}
		}
		data=data.substring(0,data.length()-1);
		try
		{
			Connection conn = ConnectionManager.getConnection();
			Statement st = conn.createStatement();
			String qrys = "update OTHERDETAIL set "+data+" where empno ="+empno1;
			st.executeUpdate(qrys);
			flag = true;
	//		updateOtherDetailConnection();
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}
	
	
/***************************************************Connection Setup Started**********************************************************************************************/
/*	public boolean updateOtherDetailConnection()
	{
		System.out.println("NEW CONNECTION");
		boolean flag = false;
		try
		{
			Connection conn = ConnectionManager.getConnectionTech_access();
			Statement st = conn.createStatement();
			String qrys = "update AttendanceLogs set EmployeeId=9999 where AttendanceLogId=1466 and EmployeeId=2523";
			
			st.executeUpdate(qrys);
			flag = true;
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}*/
	
/***************************************************Connection Setup Ended*************************************************************************************************/
	
	
}