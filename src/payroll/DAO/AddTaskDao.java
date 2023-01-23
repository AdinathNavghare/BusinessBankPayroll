package payroll.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import payroll.Model.AddProjectBean;
import payroll.Model.AddTaskBean;
import payroll.Model.Lookup;
import payroll.DAO.ConnectionManager;


public class AddTaskDao {
	static Connection con=null;
	static AddProjectBean probean=new AddProjectBean();
	static String condi="Active";
	static String condi1="Inactive";
	static Date st=null;
	static Date du=null;
	/*static AddTaskBean taskbean = new AddTaskBean();
	static String ans=taskbean.getPROJECT();*/
	public static boolean addTask(AddTaskBean taskbean) {
		
		con = ConnectionManager.getConnection();
		ResultSet rs;
		
		Boolean flag = false;
		
		try{
			Statement stmt = con.createStatement();
			
		       /* String stdt=taskbean.getSTART_DATE();
		        String dudt=taskbean.getDUE_DATE();
		        System.out.println("stdt is :"+stdt+" dudt is : "+dudt);*/
		        
		       /* SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd");
				fromformat.setLenient(false);*/
		        //Date date = new Date();
		        
		        /*SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		       
		        Date date= formatter.parse(stdt);
		        du=formatter.parse(dudt);
		        
		        System.out.println("stdt is 1 :"+date+" dudt is : "+du);*/
			
			
			/*
			SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd");
			String parameter = taskbean.getSTART_DATE();
			Date date1 = in.parse(parameter);
			System.out.println("after parseing ....  "+date1);	*/					
		        
		    
		   /* String query="insert into SubTask VALUES"
	     		+ "("
	    		+ "( SELECT TOP(1) TASK_ID FROM Task ORDER BY 1 DESC)"
	     		+ ",'"
	    		+ taskbean.getSUBTASK()
	    		+ "'"
			    +")";   */
		     
		                                 
			String str="insert into Task(PROJECT_NAME,TASK_TYPE,TASK_NAME,TASK_STATUS,TASK_PRIORITY,ESTIMATED_TIME,TASK_START_DATE,DUE_DATE,DISCRIPTION,TCONDITION,ProjectId)VALUES"
					+ "( '" 
					+ taskbean.getPROJECT() 
					+ "','" 
					+ taskbean.getTYPE() 
					+ "','"
					+ taskbean.getNAME() 
					+ "','" 
					+ taskbean.getSTATUS() 
					+ "','"
					+ taskbean.getPRIORITY() 
					+ "','" 
					+ taskbean.getESTIMATED_TIME() 
					+ "','" 
					+ taskbean.getSTART_DATE()
					+ "','" 
					+ taskbean.getDUE_DATE()
					+ "','" 
					+ taskbean.getDISCRIPTION() 
					+ "','"
					+ condi 
					+"',(select ProjectId from AddProject where ProjectName= '"
					+ taskbean.getPROJECT() 
					+ "'))";
								
			String query="insert into SubTask VALUES"
		     		+ "("
		    		+ "( SELECT TOP(1) TASK_ID FROM Task ORDER BY 1 DESC)"
		     		+ ",'"
		    		+ taskbean.getNAME()
		    		+ "'"
				    +")";  
			stmt.execute(str);
			
			stmt.execute(query);
			
			con.close();
			
			System.out.println(query);
			System.out.println(str);
			
			
			flag=true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/*public static boolean addTime(AddTaskBean taskbean) {
			
		con = ConnectionManager.getConnection();
	    ResultSet rs;
		SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd");
		fromformat.setLenient(false);
		
		Boolean flag = false;
		
		try{
			Statement stmt = con.createStatement();
			String str="insert into Time(ESTIMATED_TIME,TASK_START_DATE,DUE_DATE,PROGRESS,TASK_ID,ProjectId)VALUES"
					+ "("
					+ "' "
					+ taskbean.getESTIMATED_TIME()
					+ "', "
					+ "' "
					+ taskbean.getSTART_DATE()
					+ "' "
					+ " ,'"
					+ taskbean.getDUE_DATE()
					+ "' "
					+ " ,'"
					+ taskbean.getPROGRESS()
					+ "'"
					+ ","
					+ "( SELECT TOP(1) TASK_ID FROM Task ORDER BY 1 DESC)" 
					+","
					+"(select projectId from Task where TASK_ID=( SELECT TOP(1) TASK_ID FROM Task ORDER BY 1 DESC))"
					+")";
			
			System.out.println(str);
			stmt.execute(str);
			flag=true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}*/

	public static String dateFormat(Date date) {
		String result = "";
		if (date == null) {
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat("dd-mm-yy");
		result = format.format(date);
		return result;
	}

	

	public static boolean editTask(AddTaskBean taskbean, int tt, String pro,int pid) throws SQLException {
		
		Boolean flag=false;
		Statement st=null,st1=null;
		PreparedStatement st2=null;
		ResultSet rs=null;
		int id;
				
		try{
			
			
			Connection con=ConnectionManager.getConnection();
			st=con.createStatement();
			st1=con.createStatement();
			
			String sql="select ProjectId from Task where TASK_ID="+tt;
			st2=con.prepareStatement(sql);
			
			rs = st2.executeQuery();
			rs.next();
			id=rs.getInt("ProjectId");
			System.out.println("Pro id= "+id);
			
			
			st.executeUpdate("update Task set TCONDITION='" +condi1
			        +"'"
					+ "where TASK_ID="+tt);
			
			
			String str1="insert into Task(PROJECT_NAME,TASK_TYPE,TASK_NAME,TASK_STATUS,TASK_PRIORITY,ESTIMATED_TIME,TASK_START_DATE,DUE_DATE,DISCRIPTION,TCONDITION,ProjectId)VALUES"
					+ "( '" 
					+ pro 
					+ "','" 
					+ taskbean.getTYPE() 
					+ "','" 
					+ taskbean.getNAME() 
					+ "','" 
					+ taskbean.getSTATUS() 
					+ "','"
					+ taskbean.getPRIORITY() 
					+ "','" 
					+ taskbean.getESTIMATED_TIME() 
					+ "','" 
					+ taskbean.getSTART_DATE() 
					+ "','" 
					+ taskbean.getDUE_DATE() 
					+ "','" 
					+ taskbean.getDISCRIPTION()
					+"','"
					+ condi 
					+ "','"
					+ id				
					+ "')";
			
			System.out.println(str1);
			st1.execute(str1);
			
			        flag = true;
				    con.close();
			}catch (Exception e) {
						e.printStackTrace();
					}
					return flag;
	}
	
	

	/*public static boolean updateTime(AddTaskBean taskbean, int tt) throws SQLException {
		Boolean flag=false;
		Statement st=null;
		try{
		Connection con=ConnectionManager.getConnection();
		 st=con.createStatement();
		 
		 st.executeUpdate("update Time set ESTIMATED_TIME='"+ taskbean.getESTIMATED_TIME()
		 +"',TASK_START_DATE='" + taskbean.getSTART_DATE() + "',DUE_DATE='" 
		 + taskbean.getDUE_DATE() + "',PROGRESS='" + taskbean.getPROGRESS() +"'"
		 + "where TASK_ID="+tt);
		 
		 flag = true;
		 con.close();
    	} catch (Exception e)
		     {
			 	e.printStackTrace();
		     }
		return flag;
	}*/ 

	
	
	public static Integer DeleteTask(int tt) {
		
		int flag=0;
		PreparedStatement st=null;
		
		try{
			Connection con=ConnectionManager.getConnection();
			String sql="update task set TCONDITION='Inactive' where TASK_ID="+tt;
			
			//String sql="delete from Task where TASK_ID="+tt;
			st=con.prepareStatement(sql);
			
			st.execute();
			
			flag=1;
			
			con.close();
			
			//st.executeQuery("DELETE FROM Time, Assigner, Task USING Time INNER JOIN Assigner INNER JOIN Task WHERE Time.TASK_ID = +tt AND Assigner.TASK_ID = Time.TASK_ID AND Task.TASK_ID = Time.TASK_ID");
			//st.executeQuery("delete from Time where TASK_ID="+tt+ "inner join Assigner delete from Assigner where TASK_ID="+tt+ "inner join Task delete from Task where TASK_ID="+tt);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean AddSubTask(AddTaskBean taskbean, int tt) {
		
		Connection con=null;
		Statement stmt = null;
		PreparedStatement pst=null;
		String tsk,stsk,Stnm = null,Ttnm;
		int Sid = 0;
		ResultSet rs=null,rs2=null;
		
		tsk=taskbean.getNAME();
		stsk=taskbean.getSUBTASK();
		System.out.println("tsk"+tsk);
		System.out.println("stsk"+stsk);
		
		con=ConnectionManager.getConnection();
		boolean flag=false;
		
		try{
			
			 stmt = con.createStatement();	
			 
			 String sql="select TASK_NAME from Task where TASK_ID="+tt;
			 pst=con.prepareStatement(sql);
			 rs=pst.executeQuery();
			 rs.next();
			 Ttnm=rs.getString("TASK_NAME");
			//System.out.println("Task name from task table is ... "+Ttnm);
			
			 String sql2="select SubTName,Sub_ID from SubTask where TASK_ID="+tt;
			 pst=con.prepareStatement(sql2);
			 rs2=pst.executeQuery();
			 while(rs2.next())
			 {
			 Stnm=rs2.getString("SubTName");
			 Sid=rs2.getInt("Sub_ID");
			
			//System.out.println("Task name from Subtask table is ... "+Stnm);
			if(Ttnm.equals(Stnm)){
				
				String str="update SubTask set SubTName ='"
				        +taskbean.getSUBTASK()
						+"'" 
						+"where TASK_ID="+tt
						+"and Sub_Id="+Sid;
				
				stmt.executeUpdate(str);
				flag=true;
			}
			else{
					String str1="insert into SubTask(TASK_ID,SubTName)values"
							+ "( '"
							+ tt
							+"','"
							+ taskbean.getSUBTASK()
							+ "')";
					
					stmt.execute(str1);
					
					System.out.println(str1);
					flag=true;
					con.close();
				}
			 }
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}

	public static boolean editSubTask(AddTaskBean taskbean, int tt, int Sid) {
		System.out.println("hiiiii");
		Connection con = null;
		Statement st=null;
		ResultSet rs=null;
		boolean flag=false;
		try{
			con=ConnectionManager.getConnection();
			st=con.createStatement();
			
			String sql="update SubTask set SubTName ='"
			        +taskbean.getSUBTASK()
					+"'" 
					+"where TASK_ID="+tt
					+"and Sub_ID="+Sid;
			
			st.executeUpdate(sql);
			System.out.println(sql);
			
		    flag=true;
			con.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean updateTask(AddTaskBean taskbean, int tt) {
		ResultSet rs = null;
		Boolean flag=false;
		Statement st=null;
		try{
			
			
			Connection con=ConnectionManager.getConnection();
			st=con.createStatement();
			
			st.executeUpdate("update Task set TASK_TYPE='" + taskbean.getTYPE()
			+ "',TASK_NAME='" + taskbean.getNAME()
			+ "',TASK_STATUS='" + taskbean.getSTATUS() 
			+ "',TASK_PRIORITY='" + taskbean.getPRIORITY()  
			+ "',ESTIMATED_TIME='" + taskbean.getESTIMATED_TIME()
            +"',TASK_START_DATE='" + taskbean.getSTART_DATE() 
            + "',DUE_DATE='" + taskbean.getDUE_DATE() 
            +"',DISCRIPTION='" + taskbean.getDISCRIPTION() +"'"
            + "where TASK_ID="+tt);
			
			
			        flag = true;
				    con.close();
			}catch (Exception e) {
						e.printStackTrace();
					}
					return flag;
	}

	public ArrayList<AddTaskBean> getTaskDetail()
	{
		ArrayList<AddTaskBean> result = new ArrayList<AddTaskBean>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select * from Task t inner join AddProject p on t.ProjectId=p.ProjectId where t.TCONDITION='Active' and p.PCONDITION='Active'");
			while(rs.next())
			{
				
			//	"TASK_ID",PROJECT_NAME,"TASK_TYPE","TASK_NAME""TASK_STATUS""TASK_PRIORITY""ESTIMATED_TIME""TASK_START_DATE""DUE_DATE"
				AddTaskBean tbin =new AddTaskBean();
				tbin.setTASK_ID((int) (rs.getInt(1)!=0?rs.getInt(1):""));
				tbin.setPROJECT(rs.getString(2)!=null?rs.getString(2):"");
				tbin.setTYPE(rs.getString(3)!=null?rs.getString(3):"");
				tbin.setNAME(rs.getString(4)!=null?rs.getString(4):"");
				tbin.setSTATUS(rs.getString(5)!=null?rs.getString(5):"");
				tbin.setPRIORITY(rs.getString(6)!=null?rs.getString(6):"");
				tbin.setESTIMATED_TIME(rs.getString(7)!=null?rs.getString(7):"");
				tbin.setSTART_DATE(rs.getString(8)!=null?rs.getString(8):"");
				tbin.setDUE_DATE(rs.getString(9)!=null?rs.getString(9):"");
				
				//tbin.setPROJECT_ID((int) (rs.getInt(11)!=0?rs.getInt(11):""));
				
				result.add(tbin);
			}
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
		
	}
	
	public ArrayList<AddTaskBean> getTStatusOpen()
	{
		ArrayList<AddTaskBean> result = new ArrayList<AddTaskBean>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rss=st.executeQuery("select * from Task t inner join AddProject p on t.ProjectId=p.ProjectId where t.TCONDITION='Active' and p.PCONDITION='Active' and t.TASK_STATUS='Open';");
			
			while(rss.next())
			{
				
				AddTaskBean tbin =new AddTaskBean();
				tbin.setTASK_ID((int) (rss.getInt(1)!=0?rss.getInt(1):""));
				tbin.setPROJECT(rss.getString(2)!=null?rss.getString(2):"");
				tbin.setTYPE(rss.getString(3)!=null?rss.getString(3):"");
				tbin.setNAME(rss.getString(4)!=null?rss.getString(4):"");
				tbin.setSTATUS(rss.getString(5)!=null?rss.getString(5):"");
				tbin.setPRIORITY(rss.getString(6)!=null?rss.getString(6):"");
				tbin.setESTIMATED_TIME(rss.getString(7)!=null?rss.getString(7):"");
				tbin.setSTART_DATE(rss.getString(8)!=null?rss.getString(8):"");
				tbin.setDUE_DATE(rss.getString(9)!=null?rss.getString(9):"");
		
				result.add(tbin);
			}
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
		
	}
	public ArrayList<AddTaskBean> getTStatusWait()
	{
		ArrayList<AddTaskBean> result = new ArrayList<AddTaskBean>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rss=st.executeQuery("select * from Task t inner join AddProject p on t.ProjectId=p.ProjectId where t.TCONDITION='Active' and p.PCONDITION='Active' and t.TASK_STATUS='Waiting-Assessmante';");
			
			while(rss.next())
			{
				
				AddTaskBean tbin =new AddTaskBean();
				tbin.setTASK_ID((int) (rss.getInt(1)!=0?rss.getInt(1):""));
				tbin.setPROJECT(rss.getString(2)!=null?rss.getString(2):"");
				tbin.setTYPE(rss.getString(3)!=null?rss.getString(3):"");
				tbin.setNAME(rss.getString(4)!=null?rss.getString(4):"");
				tbin.setSTATUS(rss.getString(5)!=null?rss.getString(5):"");
				tbin.setPRIORITY(rss.getString(6)!=null?rss.getString(6):"");
				tbin.setESTIMATED_TIME(rss.getString(7)!=null?rss.getString(7):"");
				tbin.setSTART_DATE(rss.getString(8)!=null?rss.getString(8):"");
				tbin.setDUE_DATE(rss.getString(9)!=null?rss.getString(9):"");
		
				result.add(tbin);
			}
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
		
	}
	public ArrayList<AddTaskBean> getTStatusReopen()
	{
		ArrayList<AddTaskBean> result = new ArrayList<AddTaskBean>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rss=st.executeQuery("select * from Task t inner join AddProject p on t.ProjectId=p.ProjectId where t.TCONDITION='Active' and p.PCONDITION='Active' and t.TASK_STATUS='Re-Opened';");
			//System.out.println("dao");
			while(rss.next())
			{
				//System.out.println("dao"+rss.getInt(1));
			//	"TASK_ID",PROJECT_NAME,"TASK_TYPE","TASK_NAME""TASK_STATUS""TASK_PRIORITY""ESTIMATED_TIME""TASK_START_DATE""DUE_DATE"
				AddTaskBean tbin =new AddTaskBean();
				tbin.setTASK_ID((int) (rss.getInt(1)!=0?rss.getInt(1):""));
				tbin.setPROJECT(rss.getString(2)!=null?rss.getString(2):"");
				tbin.setTYPE(rss.getString(3)!=null?rss.getString(3):"");
				tbin.setNAME(rss.getString(4)!=null?rss.getString(4):"");
				tbin.setSTATUS(rss.getString(5)!=null?rss.getString(5):"");
				tbin.setPRIORITY(rss.getString(6)!=null?rss.getString(6):"");
				tbin.setESTIMATED_TIME(rss.getString(7)!=null?rss.getString(7):"");
				tbin.setSTART_DATE(rss.getString(8)!=null?rss.getString(8):"");
				tbin.setDUE_DATE(rss.getString(9)!=null?rss.getString(9):"");
		
				result.add(tbin);
			}
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<AddTaskBean> getTStatusDone()
	{
		ArrayList<AddTaskBean> result = new ArrayList<AddTaskBean>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rss=st.executeQuery("select * from Task t inner join AddProject p on t.ProjectId=p.ProjectId where t.TCONDITION='Active' and p.PCONDITION='Active' and t.TASK_STATUS='done';");
			
			while(rss.next())
			{
				
				AddTaskBean tbin =new AddTaskBean();
				tbin.setTASK_ID((int) (rss.getInt(1)!=0?rss.getInt(1):""));
				tbin.setPROJECT(rss.getString(2)!=null?rss.getString(2):"");
				tbin.setTYPE(rss.getString(3)!=null?rss.getString(3):"");
				tbin.setNAME(rss.getString(4)!=null?rss.getString(4):"");
				tbin.setSTATUS(rss.getString(5)!=null?rss.getString(5):"");
				tbin.setPRIORITY(rss.getString(6)!=null?rss.getString(6):"");
				tbin.setESTIMATED_TIME(rss.getString(7)!=null?rss.getString(7):"");
				tbin.setSTART_DATE(rss.getString(8)!=null?rss.getString(8):"");
				tbin.setDUE_DATE(rss.getString(9)!=null?rss.getString(9):"");
		
				result.add(tbin);
			}
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
		
	}
	
	public ArrayList<AddTaskBean> getTStatusCompleted()
	{
		ArrayList<AddTaskBean> result = new ArrayList<AddTaskBean>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rss=st.executeQuery("select * from Task t inner join AddProject p on t.ProjectId=p.ProjectId where t.TCONDITION='Active' and p.PCONDITION='Active' and t.TASK_STATUS='Completed';");
			
			while(rss.next())
			{
				
				AddTaskBean tbin =new AddTaskBean();
				tbin.setTASK_ID((int) (rss.getInt(1)!=0?rss.getInt(1):""));
				tbin.setPROJECT(rss.getString(2)!=null?rss.getString(2):"");
				tbin.setTYPE(rss.getString(3)!=null?rss.getString(3):"");
				tbin.setNAME(rss.getString(4)!=null?rss.getString(4):"");
				tbin.setSTATUS(rss.getString(5)!=null?rss.getString(5):"");
				tbin.setPRIORITY(rss.getString(6)!=null?rss.getString(6):"");
				tbin.setESTIMATED_TIME(rss.getString(7)!=null?rss.getString(7):"");
				tbin.setSTART_DATE(rss.getString(8)!=null?rss.getString(8):"");
				tbin.setDUE_DATE(rss.getString(9)!=null?rss.getString(9):"");
		
				result.add(tbin);
			}
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
		
	}
	public ArrayList<AddTaskBean> getTStatusPaid()
	{
		ArrayList<AddTaskBean> result = new ArrayList<AddTaskBean>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rss=st.executeQuery("select * from Task t inner join AddProject p on t.ProjectId=p.ProjectId where t.TCONDITION='Active' and p.PCONDITION='Active' and t.TASK_STATUS='Paid';");
			
			while(rss.next())
			{
				AddTaskBean tbin =new AddTaskBean();
				tbin.setTASK_ID((int) (rss.getInt(1)!=0?rss.getInt(1):""));
				tbin.setPROJECT(rss.getString(2)!=null?rss.getString(2):"");
				tbin.setTYPE(rss.getString(3)!=null?rss.getString(3):"");
				tbin.setNAME(rss.getString(4)!=null?rss.getString(4):"");
				tbin.setSTATUS(rss.getString(5)!=null?rss.getString(5):"");
				tbin.setPRIORITY(rss.getString(6)!=null?rss.getString(6):"");
				tbin.setESTIMATED_TIME(rss.getString(7)!=null?rss.getString(7):"");
				tbin.setSTART_DATE(rss.getString(8)!=null?rss.getString(8):"");
				tbin.setDUE_DATE(rss.getString(9)!=null?rss.getString(9):"");
		
				result.add(tbin);
			}
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;	
	}
	public ArrayList<AddTaskBean> getTStatusSuspended()
	{
		ArrayList<AddTaskBean> result = new ArrayList<AddTaskBean>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rss=st.executeQuery("select * from Task t inner join AddProject p on t.ProjectId=p.ProjectId where t.TCONDITION='Active' and p.PCONDITION='Active' and t.TASK_STATUS='Suspended';");
			while(rss.next())
			{
				
				AddTaskBean tbin =new AddTaskBean();
				tbin.setTASK_ID((int) (rss.getInt(1)!=0?rss.getInt(1):""));
				tbin.setPROJECT(rss.getString(2)!=null?rss.getString(2):"");
				tbin.setTYPE(rss.getString(3)!=null?rss.getString(3):"");
				tbin.setNAME(rss.getString(4)!=null?rss.getString(4):"");
				tbin.setSTATUS(rss.getString(5)!=null?rss.getString(5):"");
				tbin.setPRIORITY(rss.getString(6)!=null?rss.getString(6):"");
				tbin.setESTIMATED_TIME(rss.getString(7)!=null?rss.getString(7):"");
				tbin.setSTART_DATE(rss.getString(8)!=null?rss.getString(8):"");
				tbin.setDUE_DATE(rss.getString(9)!=null?rss.getString(9):"");
		
				result.add(tbin);
			}
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<AddTaskBean> getTStatusLost()
	{
		ArrayList<AddTaskBean> result = new ArrayList<AddTaskBean>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rss=st.executeQuery("select * from Task t inner join AddProject p on t.ProjectId=p.ProjectId where t.TCONDITION='Active' and p.PCONDITION='Active' and t.TASK_STATUS='Lost';");
			while(rss.next())
			{
				
				AddTaskBean tbin =new AddTaskBean();
				tbin.setTASK_ID((int) (rss.getInt(1)!=0?rss.getInt(1):""));
				tbin.setPROJECT(rss.getString(2)!=null?rss.getString(2):"");
				tbin.setTYPE(rss.getString(3)!=null?rss.getString(3):"");
				tbin.setNAME(rss.getString(4)!=null?rss.getString(4):"");
				tbin.setSTATUS(rss.getString(5)!=null?rss.getString(5):"");
				tbin.setPRIORITY(rss.getString(6)!=null?rss.getString(6):"");
				tbin.setESTIMATED_TIME(rss.getString(7)!=null?rss.getString(7):"");
				tbin.setSTART_DATE(rss.getString(8)!=null?rss.getString(8):"");
				tbin.setDUE_DATE(rss.getString(9)!=null?rss.getString(9):"");
		
				result.add(tbin);
			}
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<AddTaskBean> getPriorityUnknown()
	{
		ArrayList<AddTaskBean> result = new ArrayList<AddTaskBean>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rss=st.executeQuery("select * from Task t inner join AddProject p on t.ProjectId=p.ProjectId where t.TCONDITION='Active' and p.PCONDITION='Active' and t.TASK_PRIORITY='unknown';");
			while(rss.next())
			{
				
				AddTaskBean tbin =new AddTaskBean();
				tbin.setTASK_ID((int) (rss.getInt(1)!=0?rss.getInt(1):""));
				tbin.setPROJECT(rss.getString(2)!=null?rss.getString(2):"");
				tbin.setTYPE(rss.getString(3)!=null?rss.getString(3):"");
				tbin.setNAME(rss.getString(4)!=null?rss.getString(4):"");
				tbin.setSTATUS(rss.getString(5)!=null?rss.getString(5):"");
				tbin.setPRIORITY(rss.getString(6)!=null?rss.getString(6):"");
				tbin.setESTIMATED_TIME(rss.getString(7)!=null?rss.getString(7):"");
				tbin.setSTART_DATE(rss.getString(8)!=null?rss.getString(8):"");
				tbin.setDUE_DATE(rss.getString(9)!=null?rss.getString(9):"");
		
				result.add(tbin);
			}
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<AddTaskBean> getPriorityLow()
	{
		ArrayList<AddTaskBean> result = new ArrayList<AddTaskBean>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rss=st.executeQuery("select * from Task t inner join AddProject p on t.ProjectId=p.ProjectId where t.TCONDITION='Active' and p.PCONDITION='Active' and t.TASK_PRIORITY='Low';");
			while(rss.next())
			{
				
				AddTaskBean tbin =new AddTaskBean();
				tbin.setTASK_ID((int) (rss.getInt(1)!=0?rss.getInt(1):""));
				tbin.setPROJECT(rss.getString(2)!=null?rss.getString(2):"");
				tbin.setTYPE(rss.getString(3)!=null?rss.getString(3):"");
				tbin.setNAME(rss.getString(4)!=null?rss.getString(4):"");
				tbin.setSTATUS(rss.getString(5)!=null?rss.getString(5):"");
				tbin.setPRIORITY(rss.getString(6)!=null?rss.getString(6):"");
				tbin.setESTIMATED_TIME(rss.getString(7)!=null?rss.getString(7):"");
				tbin.setSTART_DATE(rss.getString(8)!=null?rss.getString(8):"");
				tbin.setDUE_DATE(rss.getString(9)!=null?rss.getString(9):"");
		
				result.add(tbin);
			}
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<AddTaskBean> getPriorityMedium()
	{
		ArrayList<AddTaskBean> result = new ArrayList<AddTaskBean>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rss=st.executeQuery("select * from Task t inner join AddProject p on t.ProjectId=p.ProjectId where t.TCONDITION='Active' and p.PCONDITION='Active' and t.TASK_PRIORITY='medium';");
			while(rss.next())
			{
				
				AddTaskBean tbin =new AddTaskBean();
				tbin.setTASK_ID((int) (rss.getInt(1)!=0?rss.getInt(1):""));
				tbin.setPROJECT(rss.getString(2)!=null?rss.getString(2):"");
				tbin.setTYPE(rss.getString(3)!=null?rss.getString(3):"");
				tbin.setNAME(rss.getString(4)!=null?rss.getString(4):"");
				tbin.setSTATUS(rss.getString(5)!=null?rss.getString(5):"");
				tbin.setPRIORITY(rss.getString(6)!=null?rss.getString(6):"");
				tbin.setESTIMATED_TIME(rss.getString(7)!=null?rss.getString(7):"");
				tbin.setSTART_DATE(rss.getString(8)!=null?rss.getString(8):"");
				tbin.setDUE_DATE(rss.getString(9)!=null?rss.getString(9):"");
		
				result.add(tbin);
			}
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<AddTaskBean> getPriorityHigh()
	{
		ArrayList<AddTaskBean> result = new ArrayList<AddTaskBean>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rss=st.executeQuery("select * from Task t inner join AddProject p on t.ProjectId=p.ProjectId where t.TCONDITION='Active' and p.PCONDITION='Active' and t.TASK_PRIORITY='high';");
			while(rss.next())
			{
				
				AddTaskBean tbin =new AddTaskBean();
				tbin.setTASK_ID((int) (rss.getInt(1)!=0?rss.getInt(1):""));
				tbin.setPROJECT(rss.getString(2)!=null?rss.getString(2):"");
				tbin.setTYPE(rss.getString(3)!=null?rss.getString(3):"");
				tbin.setNAME(rss.getString(4)!=null?rss.getString(4):"");
				tbin.setSTATUS(rss.getString(5)!=null?rss.getString(5):"");
				tbin.setPRIORITY(rss.getString(6)!=null?rss.getString(6):"");
				tbin.setESTIMATED_TIME(rss.getString(7)!=null?rss.getString(7):"");
				tbin.setSTART_DATE(rss.getString(8)!=null?rss.getString(8):"");
				tbin.setDUE_DATE(rss.getString(9)!=null?rss.getString(9):"");
		
				result.add(tbin);
			}
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<AddTaskBean> getPriorityUrgent()
	{
		ArrayList<AddTaskBean> result = new ArrayList<AddTaskBean>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rss=st.executeQuery("select * from Task t inner join AddProject p on t.ProjectId=p.ProjectId where t.TCONDITION='Active' and p.PCONDITION='Active' and t.TASK_PRIORITY='urgent';");
			while(rss.next())
			{
				
				AddTaskBean tbin =new AddTaskBean();
				tbin.setTASK_ID((int) (rss.getInt(1)!=0?rss.getInt(1):""));
				tbin.setPROJECT(rss.getString(2)!=null?rss.getString(2):"");
				tbin.setTYPE(rss.getString(3)!=null?rss.getString(3):"");
				tbin.setNAME(rss.getString(4)!=null?rss.getString(4):"");
				tbin.setSTATUS(rss.getString(5)!=null?rss.getString(5):"");
				tbin.setPRIORITY(rss.getString(6)!=null?rss.getString(6):"");
				tbin.setESTIMATED_TIME(rss.getString(7)!=null?rss.getString(7):"");
				tbin.setSTART_DATE(rss.getString(8)!=null?rss.getString(8):"");
				tbin.setDUE_DATE(rss.getString(9)!=null?rss.getString(9):"");
		
				result.add(tbin);
			}
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<AddTaskBean> getTypeCh25()
	{
		ArrayList<AddTaskBean> result = new ArrayList<AddTaskBean>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rss=st.executeQuery("select * from Task t inner join AddProject p on t.ProjectId=p.ProjectId where t.TCONDITION='Active' and p.PCONDITION='Active' and t.TASK_TYPE='ChangePriorityRate(Hourly rate($ 25.00))';");
			while(rss.next())
			{
				
				AddTaskBean tbin =new AddTaskBean();
				tbin.setTASK_ID((int) (rss.getInt(1)!=0?rss.getInt(1):""));
				tbin.setPROJECT(rss.getString(2)!=null?rss.getString(2):"");
				tbin.setTYPE(rss.getString(3)!=null?rss.getString(3):"");
				tbin.setNAME(rss.getString(4)!=null?rss.getString(4):"");
				tbin.setSTATUS(rss.getString(5)!=null?rss.getString(5):"");
				tbin.setPRIORITY(rss.getString(6)!=null?rss.getString(6):"");
				tbin.setESTIMATED_TIME(rss.getString(7)!=null?rss.getString(7):"");
				tbin.setSTART_DATE(rss.getString(8)!=null?rss.getString(8):"");
				tbin.setDUE_DATE(rss.getString(9)!=null?rss.getString(9):"");
		
				result.add(tbin);
			}
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<AddTaskBean> getTypeCh0()
	{
		ArrayList<AddTaskBean> result = new ArrayList<AddTaskBean>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rss=st.executeQuery("select * from Task t inner join AddProject p on t.ProjectId=p.ProjectId where t.TCONDITION='Active' and p.PCONDITION='Active' and t.TASK_TYPE='Defects(Hourly rate($ 0.00))';");
			while(rss.next())
			{
				
				AddTaskBean tbin =new AddTaskBean();
				tbin.setTASK_ID((int) (rss.getInt(1)!=0?rss.getInt(1):""));
				tbin.setPROJECT(rss.getString(2)!=null?rss.getString(2):"");
				tbin.setTYPE(rss.getString(3)!=null?rss.getString(3):"");
				tbin.setNAME(rss.getString(4)!=null?rss.getString(4):"");
				tbin.setSTATUS(rss.getString(5)!=null?rss.getString(5):"");
				tbin.setPRIORITY(rss.getString(6)!=null?rss.getString(6):"");
				tbin.setESTIMATED_TIME(rss.getString(7)!=null?rss.getString(7):"");
				tbin.setSTART_DATE(rss.getString(8)!=null?rss.getString(8):"");
				tbin.setDUE_DATE(rss.getString(9)!=null?rss.getString(9):"");
		
				result.add(tbin);
			}
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<AddTaskBean> getTypeCh15()
	{
		ArrayList<AddTaskBean> result = new ArrayList<AddTaskBean>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rss=st.executeQuery("select * from Task t inner join AddProject p on t.ProjectId=p.ProjectId where t.TCONDITION='Active' and p.PCONDITION='Active' and t.TASK_TYPE='Changes(Hourly rate($ 15.00))';");
			while(rss.next())
			{
				
				AddTaskBean tbin =new AddTaskBean();
				tbin.setTASK_ID((int) (rss.getInt(1)!=0?rss.getInt(1):""));
				tbin.setPROJECT(rss.getString(2)!=null?rss.getString(2):"");
				tbin.setTYPE(rss.getString(3)!=null?rss.getString(3):"");
				tbin.setNAME(rss.getString(4)!=null?rss.getString(4):"");
				tbin.setSTATUS(rss.getString(5)!=null?rss.getString(5):"");
				tbin.setPRIORITY(rss.getString(6)!=null?rss.getString(6):"");
				tbin.setESTIMATED_TIME(rss.getString(7)!=null?rss.getString(7):"");
				tbin.setSTART_DATE(rss.getString(8)!=null?rss.getString(8):"");
				tbin.setDUE_DATE(rss.getString(9)!=null?rss.getString(9):"");
		
				result.add(tbin);
			}
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	
	public ArrayList<AddTaskBean> getTaskEditDetail(int tid)
	{
		ArrayList<AddTaskBean> result = new ArrayList<AddTaskBean>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select * from Task where TASK_ID="+tid);
			while(rs.next())
			{
				
			
				AddTaskBean tbin =new AddTaskBean();
				tbin.setTASK_ID((int) (rs.getInt(1)!=0?rs.getInt(1):""));
				tbin.setPROJECT(rs.getString(2)!=null?rs.getString(2):"");
				tbin.setTYPE(rs.getString(3)!=null?rs.getString(3):"");
				tbin.setNAME(rs.getString(4)!=null?rs.getString(4):"");
				tbin.setSTATUS(rs.getString(5)!=null?rs.getString(5):"");
				tbin.setPRIORITY(rs.getString(6)!=null?rs.getString(6):"");
				tbin.setESTIMATED_TIME(rs.getString(7)!=null?rs.getString(7):"");
				tbin.setSTART_DATE(rs.getString(8)!=null?rs.getString(8):"");
				tbin.setDUE_DATE(rs.getString(9)!=null?rs.getString(9):"");
				tbin.setDISCRIPTION(rs.getString(10)!=null?rs.getString(10):"");
				//tbin.setPROJECT_ID((int) (rs.getInt(11)!=0?rs.getInt(11):""));
				
				result.add(tbin);
			}
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
		
	}
	
	public ArrayList<AddTaskBean> getSubTaskEditDetail(int tid)
	{
		ArrayList<AddTaskBean> result = new ArrayList<AddTaskBean>();
		
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select * from SubTask where TASK_ID="+tid);
			while(rs.next())
			{
				
			//	"TASK_ID",PROJECT_NAME,"TASK_TYPE","TASK_NAME""TASK_STATUS""TASK_PRIORITY""ESTIMATED_TIME""TASK_START_DATE""DUE_DATE"
				AddTaskBean tbin =new AddTaskBean();
				
				tbin.setSUBTASK(rs.getString(2)!=null?rs.getString(2):"");
				
				result.add(tbin);
			}
			con.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return result;
		
	}
	/*public static int removerecordTask(int tt) {
		int flag=0;
		PreparedStatement st=null;
		
		try{
			Connection con=ConnectionManager.getConnection();
			String sql="delete from Task where TASK_ID="+tt;
			st=con.prepareStatement(sql);
			
			st.execute();			
			flag=1;			
			con.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}*/	
}
