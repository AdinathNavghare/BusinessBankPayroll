package payroll.DAO;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import java.sql.*;


public class ConnectionManager
{
	static Connection conn;

	public static Connection getConnection()
	{
		try
		{
			 Properties prop = new Properties();
	         try
	         {
					//prop.load(new FileInputStream("src/Config.properties"));
					ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				    InputStream stream = classLoader.getResourceAsStream("Config.properties");
				    prop.load(stream);
			 }
	         catch (Exception e) 
	         {
					// TODO Auto-generated catch block
	        	 System.out.println("Error in Connection Manager "+e);
	         }
	         UsrHandler usrHandler = new UsrHandler();
	         String url = prop.getProperty("db_url");
	         String db_2 = new String(usrHandler.decryptBASE64(prop.getProperty("db_2")));
	         String uname =  new String(usrHandler.decryptBASE64(prop.getProperty("db_uname"))); 
			 String pwd = new String(usrHandler.decryptBASE64(prop.getProperty("db_pwd")));
			// System.out.println("------------"+url+"databaseName="+db_2+";Username="+uname+";Password="+pwd);
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			try
			{
				conn = DriverManager.getConnection(url+"databaseName="+db_2+";Username="+uname+";Password="+pwd);
			}
			catch (SQLException ex)
			{
				System.out.println("Error in Connection Manager "+ex);
			}
			
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Error in Connection Manager "+e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public static Connection getConnectionTech()
	{
		try
		{
			 Properties prop = new Properties();
	         try
	         {
					//prop.load(new FileInputStream("src/Config.properties"));
					ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				    InputStream stream = classLoader.getResourceAsStream("Config.properties");
				    prop.load(stream);
			 }
	         catch (Exception e) 
	         {
					// TODO Auto-generated catch block
	        	 System.out.println("Error in Connection Manager "+e);
	         }
	         UsrHandler usrHandler = new UsrHandler();
	         String url = prop.getProperty("db_url_tech");
	         String db_1 = new String(usrHandler.decryptBASE64(prop.getProperty("db_1")));
	         String uname =  new String(usrHandler.decryptBASE64(prop.getProperty("db_uname"))); 
			 String pwd = new String(usrHandler.decryptBASE64(prop.getProperty("db_pwd")));
		//System.out.println(url+"databaseName="+db_1+";Username="+uname+";Password="+pwd);
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			try
			{

				conn = DriverManager.getConnection(url+"databaseName="+db_1+";Username="+uname+";Password="+pwd);

			}
			catch (SQLException ex)
			{
				System.out.println("Error in Connection Manager "+ex);
			}
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Error in Connection Manager "+e);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	
	public static Connection getConnectionTech_access()
	{
		String url = "jdbc:ucanaccess://D://eTimeTrackLite1.mdb;";
		try 
		{	
				//establishing connection
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			conn = DriverManager.getConnection(url);
	
			if(conn!=null)
			{
					System.out.println("Connection Successful!");
					//conn.close();
			}
				
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return conn;
	}
	
	
	
	
	/* public static Connection getConnection() throws Exception
	    {
	          return getPooledConnection();
	    }

	    public static Connection getPooledConnection() throws Exception{
	       Connection conn = null;

	        try{
	          Context ctx = new InitialContext();
	          if(ctx == null )
	              throw new Exception("No Context");

	         Context envText = (Context)ctx.lookup("java:comp/env");
	         DataSource ds = (DataSource) envText.lookup("jdbc/PooledDB");
	         System.out.println("DS is..."+ds);

	          if (ds != null) {
	             conn = ds.getConnection();
	            return conn;
	          }else{
	              return null;
	          }

	        }catch(Exception e) {
	            e.printStackTrace();
	            throw e;
	        }
	    }*/
	
}