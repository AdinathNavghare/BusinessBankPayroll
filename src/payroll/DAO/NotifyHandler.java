package payroll.DAO;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import payroll.Model.DeductBean;
import payroll.Model.NotificationBean;
import payroll.Core.ReportDAO;
import payroll.Core.Utility;

public class NotifyHandler 
{

	public static SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
	
	public static boolean getNotify(String uid)
	{
		boolean flag= false;
		Connection con=ConnectionManager.getConnection();
		try
		{
					
			Statement st = con.createStatement();
			ResultSet result=st.executeQuery("select * from notification where status='A' and  empno="+uid);
			if(result.next())
			{
				flag= true;
			}
			else
			{
				flag= false;
			}
	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	
	
	
	public static ArrayList<NotificationBean> getEMPNotifications(String uid)
	{
		ArrayList<NotificationBean> list=new ArrayList<NotificationBean>();
		Connection con=ConnectionManager.getConnection();
		try
		{
			NotificationBean bean=null;	
			Statement st = con.createStatement();
			ResultSet result=st.executeQuery("select ROW_NUMBER() OVER(partition BY empno,created_date order by empno,created_date) as srno,* from notification where status='A' and  empno="+uid);
			while(result.next())
			{
				bean=new NotificationBean();
				bean.setDisc(result.getString("disc"));
				bean.setCreated_by(result.getString("created_by"));
				bean.setCreated_date(sdf.format(result.getDate("created_date")));
				list.add(bean);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	
	
	
}
