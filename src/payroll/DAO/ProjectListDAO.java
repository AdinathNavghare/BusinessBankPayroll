package payroll.DAO;

import java.sql.*;
import java.sql.Statement;
import payroll.DAO.*;



import payroll.Model.ProjectBean;

public class ProjectListDAO 
{
	
	
	public ProjectBean getProjectInfo(int prjno)
	{
		ProjectBean pb=new ProjectBean();
		try
		{
			java.sql.Connection con = null;
			 con =ConnectionManager.getConnectionTech();
			 Statement st = con.createStatement(); 
			 ResultSet rs = st.executeQuery("select Site_ID, Project_Code,Site_City,Site_Name from Project_Sites where Site_ID="+prjno+" ");
			
			 while(rs.next()) 
	 			{ 	
					pb.setSite_ID(rs.getInt("Site_ID"));
					pb.setSite_Name( rs.getString("Site_Name"));
					
					pb.setProject_Code(rs.getString("Project_Code"));
					pb.setSite_City(rs.getString("Site_City"));
	 			    
	 			} 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return pb;
	}

}
