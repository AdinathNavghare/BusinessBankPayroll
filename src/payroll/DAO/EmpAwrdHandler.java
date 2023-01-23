package payroll.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import payroll.Model.EmpAwardBean;
import payroll.Model.Lookup;

public class EmpAwrdHandler
{
	Connection conn;
	LookupHandler lkhp=new LookupHandler();
	Lookup lookupBean=new Lookup() ;
	
	
	public ArrayList<EmpAwardBean> getEmpAwardInfo(String EMPNO)
	{
		ResultSet rs=null;
		Statement st = null;
		ArrayList<EmpAwardBean> empawardlist = new ArrayList<EmpAwardBean>();
		try
		{
			conn = ConnectionManager.getConnection();
			st = conn.createStatement();
			rs=st.executeQuery("select * from EMPAWARD where empno='"+EMPNO+"'");
			EmpAwardBean empawdbean;
			while(rs.next())
			{
				empawdbean= new EmpAwardBean();
				empawdbean.setEMPNO(rs.getString("EMPNO")==null?0:rs.getInt("EMPNO"));
				empawdbean.setEFFDATE(rs.getDate("EFFDATE")==null?"":dateFormat(rs.getDate("EFFDATE")));
				empawdbean.setENT_DT(rs.getDate("ENT_DT")==null?"":dateFormat(rs.getDate("ENT_DT")));
				empawdbean.setMDESC(rs.getString("MDESC")==null?"":rs.getString("MDESC"));
				empawdbean.setSRNO(rs.getString("SRNO")==null?0:rs.getInt("SRNO"));
				empawdbean.setORDER_NO(rs.getString("ORDER_NO")==null?"":rs.getString("ORDER_NO"));
				empawdbean.setTRNCD(rs.getString("TRNCD")==null?0:rs.getInt("TRNCD"));
				empawdbean.setAMOUNT(rs.getString("AMOUNT")==null?"":rs.getString("AMOUNT"));
				empawardlist.add(empawdbean);
			}
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return empawardlist;
	}
	
	
	public void updateAwardInfo(EmpAwardBean empawardbean)
	{
		Statement st = null;
		conn = ConnectionManager.getConnection();
		try
		{
			st = conn.createStatement();
			st.executeUpdate("update EMPAWARD set EFFDATE='"+empawardbean.getEFFDATE()+"', ORDER_NO='"+empawardbean.getORDER_NO()+"',MDESC='"+empawardbean.getMDESC()+"',TRNCD="+empawardbean.getTRNCD()+",AMOUNT='"+empawardbean.getAMOUNT()+"' where EMPNO="+empawardbean.getEMPNO()+" and SRNO="+empawardbean.getSRNO()+"");
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void insertAwardInfo(EmpAwardBean empawardbean)
	{
		
		Statement st=null;
		PreparedStatement prst=null;
		ResultSet rs=null;
		
		int srno=0;
		try
		{
			conn = ConnectionManager.getConnection();
			st = conn.createStatement();
			rs=st.executeQuery("select max(SRNO) from EMPAWARD where EMPNO="+empawardbean.getEMPNO()+"");
			while(rs.next())
			{
				srno=rs.getInt(1);
			}
			srno=srno+1;
			/*prst = conn.prepareStatement("insert into EMPAWARD(EMPNO,EFFDATE,TRNCD,SRNO,ORDER_NO,MDESC,AMOUNT) values(?,?,?,?,?,?,?)");
			prst.setInt(1,empawardbean.getEMPNO() );
			prst.setString(2,empawardbean.getEFFDATE());
			prst.setInt(3,empawardbean.getTRNCD());
			prst.setInt(4,srno);
			prst.setString(5,empawardbean.getORDER_NO());
			prst.setString(6,empawardbean.getMDESC());
			prst.setString(7,empawardbean.getAMOUNT());
			prst.execute();*/
			String str = "insert into EMPAWARD(EMPNO,EFFDATE,TRNCD,SRNO,ORDER_NO,MDESC,AMOUNT) values("+empawardbean.getEMPNO()+"" +
					",CAST('"+empawardbean.getEFFDATE()+"' AS DATE)" +
							","+empawardbean.getTRNCD()+","+srno+"" +
					",'"+empawardbean.getORDER_NO()+"'" +
					",'"+empawardbean.getMDESC()+"'" +
					",'"+empawardbean.getAMOUNT()+"')"; 
			System.out.println(str);
			st.execute(str);
			System.out.println(str);
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static String dateFormat(Date date)
	{
		String result="";
		if(date==null)
		{
			return "";
		}
		SimpleDateFormat format=new SimpleDateFormat("dd-MMM-yyyy");
		result=format.format(date);
		return result;
	}
}
