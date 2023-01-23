package payroll.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import payroll.Model.PolicyBean;

public class PolicyHandler 
{

	public ArrayList<PolicyBean> getPolicyInfo(int empno)
	{
		ArrayList<PolicyBean> policylist = new ArrayList<PolicyBean>();
		ResultSet rs=null;
		PolicyBean pb;
		try
		{
			Connection conn = ConnectionManager.getConnection();
			Statement st = conn.createStatement();
			rs = st.executeQuery("Select * from POLICY where EMPNO="+empno);
			while(rs.next())
			{
				pb = new PolicyBean();
				pb.setSRNO(rs.getString("SRNO")!=null?rs.getInt("SRNO"):0);
				pb.setEMPNO(rs.getString("EMPNO")!=null?rs.getInt("EMPNO"):0);
				pb.setPOLICY_NAME(rs.getString("POLICY_NAME")!=null?rs.getString("POLICY_NAME"):"");
				pb.setPOLICY_TYPE(rs.getString("POLICY_TYPE")!=null?rs.getString("POLICY_TYPE"):"");
				pb.setPOLICY_AMOUNT(rs.getString("POLICY_AMOUNT")!=null?rs.getInt("POLICY_AMOUNT"):0);
				pb.setPOLICY_DATE(rs.getString("POLICY_DATE")!=null?EmpFamHandler.dateFormat((rs.getDate("POLICY_DATE"))):"");
				pb.setPREMIUM_STATUS(rs.getString("PREMIUM_STATUS")!=null?rs.getString("PREMIUM_STATUS"):"");
				pb.setPREMIUM_AMOUNT(rs.getString("PREMIUM_AMOUNT")!=null?rs.getInt("PREMIUM_AMOUNT"):0);
				pb.setDEDUCTION_MONTH(rs.getString("DEDUCTION_MONTH")!=null?rs.getInt("DEDUCTION_MONTH"):0);
				pb.setMATURITY_AMOUNT(rs.getString("MATURITY_AMOUNT")!=null?rs.getInt("MATURITY_AMOUNT"):0);
				pb.setMATURITY_DATE(rs.getString("MATURITY_DATE")!=null?EmpFamHandler.dateFormat(rs.getDate("MATURITY_DATE")):"");
				pb.setINS_COMP_NAME(rs.getString("INS_COMP_NAME")!=null?rs.getString("INS_COMP_NAME"):"");
				policylist.add(pb);
			}
			conn.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return policylist;
		
	}
	public PolicyBean getSinglePolicy(int empno,int srno)
	{
		ResultSet rs=null;
		PolicyBean pb  = new PolicyBean();;
		try
		{
			Connection conn = ConnectionManager.getConnection();
			Statement st = conn.createStatement();
			rs = st.executeQuery("Select * from POLICY where EMPNO="+empno+" and SRNO="+srno+" ");
			while(rs.next())
			{
				
				pb.setSRNO(rs.getString("SRNO")!=null?rs.getInt("SRNO"):0);
				pb.setEMPNO(rs.getString("EMPNO")!=null?rs.getInt("EMPNO"):0);
				pb.setPOLICY_NAME(rs.getString("POLICY_NAME")!=null?rs.getString("POLICY_NAME"):"");
				pb.setPOLICY_TYPE(rs.getString("POLICY_TYPE")!=null?rs.getString("POLICY_TYPE"):"");
				pb.setPOLICY_AMOUNT(rs.getString("POLICY_AMOUNT")!=null?rs.getInt("POLICY_AMOUNT"):0);
				pb.setPOLICY_DATE(rs.getString("POLICY_DATE")!=null?EmpFamHandler.dateFormat((rs.getDate("POLICY_DATE"))):"");
				pb.setPREMIUM_STATUS(rs.getString("PREMIUM_STATUS")!=null?rs.getString("PREMIUM_STATUS"):"");
				pb.setPREMIUM_AMOUNT(rs.getString("PREMIUM_AMOUNT")!=null?rs.getInt("PREMIUM_AMOUNT"):0);
				pb.setDEDUCTION_MONTH(rs.getString("DEDUCTION_MONTH")!=null?rs.getInt("DEDUCTION_MONTH"):0);
				pb.setMATURITY_AMOUNT(rs.getString("MATURITY_AMOUNT")!=null?rs.getInt("MATURITY_AMOUNT"):0);
				pb.setMATURITY_DATE(rs.getString("MATURITY_DATE")!=null?EmpFamHandler.dateFormat(rs.getDate("MATURITY_DATE")):"");
				pb.setINS_COMP_NAME(rs.getString("INS_COMP_NAME")!=null?rs.getString("INS_COMP_NAME"):"");
				
			}
			conn.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return pb;
	}
	public int insertPolicy(PolicyBean pb)
	{
		int result = 0;
		int srno =0;
		try
		{
			Connection conn = ConnectionManager.getConnection();
			Statement st = conn.createStatement();
			
			ResultSet rs =st.executeQuery("select max(SRNO) from POLICY where EMPNO="+pb.getEMPNO()+"");
			while(rs.next())
			{
				srno = rs.getString(1)!=null?rs.getInt(1):0;
			}
			srno = srno + 1;
			PreparedStatement prst = conn.prepareStatement("insert into POLICY(EMPNO,POLICY_NAME,POLICY_TYPE,POLICY_AMOUNT,POLICY_DATE,"+
					"PREMIUM_STATUS,PREMIUM_AMOUNT,DEDUCTION_MONTH,MATURITY_DATE,MATURITY_AMOUNT,INS_COMP_NAME,SRNO) values(?,?,?,?,?,?,?,?,?,?,?,?)");
			prst.setInt(1, pb.getEMPNO());
			prst.setString(2,pb.getPOLICY_NAME());
			prst.setString(3,pb.getPOLICY_TYPE());
			prst.setLong(4,pb.getPOLICY_AMOUNT());
			prst.setString(5,pb.getPOLICY_DATE());
			prst.setString(6, pb.getPREMIUM_STATUS());
			prst.setLong(7,pb.getPREMIUM_AMOUNT());
			prst.setInt(8,pb.getDEDUCTION_MONTH());
			prst.setString(9,pb.getMATURITY_DATE());
			prst.setLong(10,pb.getMATURITY_AMOUNT());
			prst.setString(11,pb.getINS_COMP_NAME());
			prst.setInt(12,srno);
			
			result=prst.executeUpdate();
			conn.close();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	public boolean updatePolicy(PolicyBean pb)
	{
		boolean flag = false;
		try 
		{
			Connection conn = ConnectionManager.getConnection();
			Statement st = conn.createStatement();
			st.executeUpdate("update POLICY set POLICY_NAME='"+pb.getPOLICY_NAME()+"',POLICY_TYPE='"+pb.getPOLICY_TYPE()+"',POLICY_AMOUNT="+pb.getPOLICY_AMOUNT()+",POLICY_DATE='"+pb.getPOLICY_DATE()+"',PREMIUM_STATUS='"+pb.getPREMIUM_STATUS()+"',PREMIUM_AMOUNT="+pb.getPREMIUM_AMOUNT()+",DEDUCTION_MONTH="+pb.getDEDUCTION_MONTH()+",MATURITY_DATE='"+pb.getMATURITY_DATE()+"',MATURITY_AMOUNT="+pb.getMATURITY_AMOUNT()+",INS_COMP_NAME='"+pb.getINS_COMP_NAME()+"' where EMPNO="+pb.getEMPNO()+" and SRNO="+pb.getSRNO()+" ");
			flag=true;
			conn.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}
}
