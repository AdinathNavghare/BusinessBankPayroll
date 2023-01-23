package payroll.DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import payroll.Model.menuBean;

public class menuDAO 
{
	public ArrayList<menuBean> getUserMenu(int uid)
	{
		Connection con = null;
		ArrayList<menuBean> menuList = new ArrayList<menuBean>();
		try
		{
			con = ConnectionManager.getConnection();
			Statement st= con.createStatement();
			ResultSet rs = st.executeQuery("select * from menu where menuid in" +
					"(select menuid FROM rolemenu where roleid = (SELECT roleid from userroles where userid = "+uid+" )) and menu_status=1 order by MENUID");
			while(rs.next())
			{
				menuBean mBean = new menuBean();
				mBean.setMENUID(rs.getString("MENUID")!=null?rs.getInt("MENUID"):0);
				mBean.setMENU_NAME(rs.getString("MENU_NAME")!=null?rs.getString("MENU_NAME"):"");
				mBean.setMENU_URL(rs.getString("MENU_URL")!=null?rs.getString("MENU_URL"):"");
				mBean.setMENU_PARENT_ID(rs.getString("MENU_PARENT_ID")!=null?rs.getInt("MENU_PARENT_ID"):0);
				mBean.setMENU_TYPE(rs.getString("MENU_TYPE")!=null?rs.getString("MENU_TYPE"):"");
				mBean.setMENU_SEQUENCE(rs.getString("MENU_SEQUENCE")!=null?rs.getString("MENU_SEQUENCE"):"");
				mBean.setMENU_STATUS(rs.getString("MENU_STATUS")!=null?rs.getString("MENU_STATUS"):"");
				menuList.add(mBean);
			}
			con.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return menuList;
	}
	
	public HashMap<Integer,String> GetUserRights(int UID) 
	{
		Connection con = null;
		HashMap<Integer,String> hashmap1 = new HashMap<Integer, String>();
		try
		{
			con = ConnectionManager.getConnection();
			Statement st= con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM USRRIGHTS WHERE USRID ="+UID);
			while (rs.next()) 
			{
				hashmap1.put(rs.getString("MENUID")!=null?rs.getInt("MENUID"):0,rs.getString("RIGHTS")!=null?rs.getString("RIGHTS"):"");
			}
			con.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return hashmap1;
	}
	
	
	
	
	public boolean addMenuinDB(menuBean mb)
	{
	
		boolean flag=false;
	
	try {
		Connection con = ConnectionManager.getConnection();
		Statement st1=con.createStatement();
		System.out.println("into HANDLER---------------");
		st1.execute("insert into menu (MENUID	,MENU_NAME,	MENU_URL	,MENU_PARENT_ID,	MENU_SEQUENCE,	MENU_TYPE,	MENU_STATUS)  values(" +
				"(select max(MENUID)+1 from menu),'"+ mb.getMENU_NAME()+"','"+mb.getMENU_URL()+"',"+mb.getMENU_PARENT_ID()+",(select max(MENUID)+1 from menu),'"+mb.getMENU_TYPE()+"',"+mb.getMENU_STATUS()+")");
		
		
		System.out.println("addeddddddddddddddddddddd");
		flag=true;
		con.close();
	} catch (Exception e) {
		e.printStackTrace();
		flag=false;
	}
	
	return flag;
	
	}

	public boolean updateMenuinDB(menuBean mb) {
		boolean flag=false;
		
		try {
			Connection con = ConnectionManager.getConnection();
			Statement st1=con.createStatement();
			st1.execute("update menu set MENU_STATUS="+mb.getMENU_STATUS()+"    where menuid="+mb.getMENU_NAME()+"");
			flag=true;
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		
		return flag;
	}	
	
	
	
	
	
	
	
	
}