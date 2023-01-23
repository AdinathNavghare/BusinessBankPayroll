package payroll.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.itextpdf.text.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import payroll.Model.DemoEmpBean;

public class DemoEmpDAO {

		static Connection con=null;
		static PreparedStatement pst=null;
		static ResultSet rs=null;
	  public ArrayList<DemoEmpBean> getDetails(String desig) 
	  {
		  String sql="select * from Employee_PM where Designation=?";
		  ArrayList<DemoEmpBean> li=new ArrayList<DemoEmpBean>();
		  try {
	  con=ConnectionManager.getConnection();
	  pst=con.prepareStatement(sql);
	  pst.setString(1, desig);
	  rs=pst.executeQuery(); 
	  while(rs.next())
	  {
		  DemoEmpBean deb=new DemoEmpBean(); 
		  		deb.setEMPID(rs.getInt("Emp_Id"));
		  		deb.setEMPNAME(rs.getString("Name"));
		  		deb.setEMPDESIGNATION(rs.getString("Designation")); 
	  			li.add(deb);
	  }
	  pst.close(); 
	  rs.close();
	  con.close();
	  } catch(Exception e) { e.printStackTrace(); }
	  return li;
	  }
		public JSONArray getDesigDetaile(String desig)
		{
			JSONArray result=new JSONArray();
			try
			{
				String sql2="select * from Employee_PM where Designation="+desig;
				Connection con=ConnectionManager.getConnection();
				Statement st=con.createStatement();
				ResultSet rs=st.executeQuery(sql2);
				while(rs.next())
				{
					JSONObject desgEmp = new JSONObject();
					desgEmp.put("Emp_Id",rs.getString(1)!=null?rs.getInt(1):"");
					desgEmp.put("Name",rs.getString(2)!=null?rs.getString(2):"");
					desgEmp.put("Designation",rs.getString(3)!=null?rs.getString(3):"");

					result.add(desgEmp);
				}
				con.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			return result;
		}
	  public ArrayList<DemoEmpBean> getDistEmpDesignation() 
	  {
		  String sql="select distinct Designation from Employee_PM";
		  ArrayList<DemoEmpBean> li=new ArrayList<DemoEmpBean>();
		  try {
	  con=ConnectionManager.getConnection();
	  pst=con.prepareStatement(sql);
	  rs=pst.executeQuery(); 
	  while(rs.next())
	  {
		  DemoEmpBean deb=new DemoEmpBean(); 
		  		deb.setEMPDESIGNATION(rs.getString("Designation")); 
	  			li.add(deb);
	  }
	  
	  pst.close(); 
	  rs.close();
	  con.close();
	  } catch(Exception e) { e.printStackTrace(); }
	  return li;
	  }
	  public ArrayList<DemoEmpBean> getAllEmpInfo() 
	  {
		  String sql="select * from Employee_PM";
		  ArrayList<DemoEmpBean> li=new ArrayList<DemoEmpBean>();
		  try {
	  con=ConnectionManager.getConnection();
	  pst=con.prepareStatement(sql);
	  rs=pst.executeQuery(); 
	  while(rs.next())
	  {
		  DemoEmpBean deb=new DemoEmpBean(); 
		  deb.setEMPID(rs.getInt("Emp_Id"));
		  	deb.setEMPNAME(rs.getString("Name"));
		  		deb.setEMPDESIGNATION(rs.getString("Designation")); 
	  			li.add(deb);
	  }
	  pst.close(); 
	  rs.close();
	  con.close();
	  } catch(Exception e) { e.printStackTrace(); }
	  return li;
	  }
}
