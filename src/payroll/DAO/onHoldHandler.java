package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import payroll.Model.IncrementBean;
import payroll.Model.onHoldBean;

public class onHoldHandler {

	public ArrayList<onHoldBean> getEmployeeHoldList(){
		
		System.out.println("In getEmployeeHoldList " );
		
		onHoldBean hBean = null;
		EmpAttendanceHandler empAttendanceHandler=new EmpAttendanceHandler();
		String currentdate = empAttendanceHandler.getServerDate();
		ArrayList<onHoldBean> onHoldList = new ArrayList<onHoldBean>();
		
		try
		{
			String year = currentdate.substring(7,11);
			Connection connection = ConnectionManager.getConnection();
			connection= ConnectionManager.getConnection();
			Statement statement=connection.createStatement();
			ResultSet resultSet = null;
			
			String qurey = " select oh.*,rtrim(e.fname)+' '+rtrim(e.mname)+' '+rtrim(e.lname)  name" +
						   " from onhold oh, empmast e " +
					       " where oh.status = 'H' and oh.empno = e.empno  ";
			
			System.out.println(qurey);
			resultSet = statement.executeQuery(qurey);
			
			while(resultSet.next())
			{	
				hBean = new onHoldBean();
				
				hBean.setEmpno(resultSet.getInt("empno"));
				hBean.setEmpcode(resultSet.getString("empcode"));
				hBean.setSalmonth(resultSet.getString("salmonth"));
				hBean.setHoldate(resultSet.getString("holddate"));
				hBean.setHreason(resultSet.getString("hreason"));
				hBean.setStatus(resultSet.getString("status"));
				hBean.setReleasedate(resultSet.getString("releasedate"));
				hBean.setRreason(resultSet.getString("rreason"));
				hBean.setName(resultSet.getString("name"));
				
				onHoldList.add(hBean);
				
	   		}
		
			
			connection.close();
		}
		catch(Exception e){e.printStackTrace();}
		
		return onHoldList;
	}
	
	public static boolean  insertOnHoldList(onHoldBean bean){
		System.out.println("IN Handler");
		boolean result = false;
		try{			
			
			Connection connection = ConnectionManager.getConnection();
			int i=0; 
			
			String qurey = " insert into onhold (empno	,empcode	,salmonth	,holddate,	hreason	,holdby	,status	,	currentdate) " +
						   " values(?,?,?,?,?,?,?,?)";
			
			System.out.println(qurey);
			java.sql.PreparedStatement pst =  connection.prepareStatement(qurey);
			
			pst.setInt(1, bean.getEmpno());
			pst.setString(2, bean.getEmpcode());
			pst.setString(3, bean.getSalmonth());
			pst.setString(4, bean.getHoldate());
			pst.setString(5, bean.getHreason());
			pst.setInt(6, bean.getHoldby());
			pst.setString(7, bean.getStatus());
			pst.setString(8, bean.getCurrentdate());
	
			i = pst.executeUpdate();
			
			if(i>0){result = true;}
			connection.close();

		}catch(Exception e){e.printStackTrace();}
		return result;	
	}
	
	
	public static boolean  releaseOnHoldList(onHoldBean bean){
		System.out.println("IN Handler");
		boolean result = false;
		try{			
			
			Connection connection = ConnectionManager.getConnection();
			int i=0; 
			
			String qurey = " update  onhold  set status	='R',releasedate='"+bean.getReleasedate()+"' ,	" +
						   " releaseby='"+bean.getReleaseby()+"'	,rreason ='"+bean.getRreason()+"',	" +
						   " currentdate = '"+bean.getCurrentdate()+"' " +
						   " where empno ="+bean.getEmpno()+"     "; 
						  
			
			System.out.println(qurey);
			java.sql.PreparedStatement pst =  connection.prepareStatement(qurey);
			
			i = pst.executeUpdate();
			
			if(i>0){result = true;}
			connection.close();

		}catch(Exception e){e.printStackTrace();}
		return result;	
	}
	
	
	
}
