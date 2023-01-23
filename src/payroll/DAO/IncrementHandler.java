
package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import payroll.Core.ReportDAO;
import payroll.Model.IncrementBean;

public class IncrementHandler {

	
	// To get employee details to show on page
	public ArrayList<IncrementBean> getEmployeeDetailsByGrade(int grade){
		IncrementBean  bean = null;
		EmpAttendanceHandler empAttendanceHandler=new EmpAttendanceHandler();
		String currentdate = empAttendanceHandler.getServerDate();
		ArrayList<IncrementBean> list = new ArrayList<IncrementBean>();
	
		try
		{
			String year = currentdate.substring(7,11);
			Connection connection = ConnectionManager.getConnection();
			connection= ConnectionManager.getConnection();
			Statement statement=connection.createStatement();
			ResultSet resultSet = null;
					
			/*String qurey = " select * from ( select e.empno,e.empcode, rtrim(e.fname)+' '+rtrim(e.mname)+' '+rtrim(e.lname) as name, c.INP_AMT, c.trncd ,g.SRNO as stage " +
						   " from empmast e, CTCDISPLAY c, EMPTRAN t ,GRADE_MASTER g" +
						   " where c.EMPNO = t.EMPNO and e.EMPNO = t.EMPNO and T.EMPNO " +
						   " In(select empno from EMPTRAN where DESIG ="+grade+" ) and e.STATUS= 'A' and c.TRNCD in (101, 102, 103,138) and g.GRADE_CODE =4  " +
						   " group by e.empno ,e.empcode,e.fname,e.mname,e.lname,c.INP_AMT,c.trncd ,g.SRNO) as em 	 " +
						   " PIVOT( max([INP_AMT])  for trncd IN([101],[102],[103],[138])   ) as amt";*/
			
			String qurey = " select em.*,g.SRNO,g.basic,g2.INCREMENT,g2.BASIC as newBasic " +
						   " from (select * from ( select e.empno,e.empcode, rtrim(e.fname)+' '+rtrim(e.mname)+' '+rtrim(e.lname) as name,	 c.INP_AMT, c.trncd  	" +
						   " from empmast e, CTCDISPLAY c, EMPTRAN t   	" +
						   " where c.EMPNO = t.EMPNO and e.EMPNO = t.EMPNO  and t.DESIG = "+grade+"  and 	T.SRNO  " +
						   " In(SELECT max(SRNO) FROM   emptran ee WHERE  ee.empno = t.empno  ) and t.EMPNO NOT in(select empno from increment_history where ISPROCESED = 1 and INCREMENTDATE <= '01-APR-"+year+"' )		" +
						   " and t.EMPNO in (select distinct EMPNO from paytran where TRNDT between '01-APR-"+year+"' and '30-APR-"+year+"' )   " +

						   //						   " and t.EMPNO in (select distinct EMPNO from paytran where TRNDT between '01-apr-"+year+"' and '30-apr-"+year+"' )   " +
						   " and  e.STATUS= 'A' and c.TRNCD in (101, 102, 103,138)  " +
						   " group by e.empno ,e.empcode,e.fname,e.mname,e.lname,c.INP_AMT,c.trncd )as em 	" +
						   " PIVOT( max([INP_AMT])  for trncd IN([101],[102],[103],[138])) as amt ) as em  ,GRADE_MASTER g  ,GRADE_MASTER g2	" +
						   " where g.BASIC = em.\"101\" and g.GRADE_CODE = "+grade+" and g2.SRNO=g.SRNO + 1 	and g2.GRADE_CODE = "+grade+" and g.GRADE_STATUS =0 and g2.GRADE_STATUS =1  "  +
		                   "  and g2. END_DATE = ( select MAX(END_DATE) from GRADE_MASTER g3   where g3.GRADE_CODE = g2.GRADE_CODE  and g3.grade_status = 1) " ;
		                            
		       
			
		       
		 
			
			
			
			System.out.println(qurey);
			resultSet = statement.executeQuery(qurey);
			
			while(resultSet.next())
			{	
				bean = new IncrementBean();
				
				 bean.setEmpno(resultSet.getInt("empno"));
				 bean.setEmpCode(resultSet.getString("empcode"));
				 bean.setEmpName(resultSet.getString("name"));
				 bean.setBasic(resultSet.getFloat("101"));
				 bean.setDa(resultSet.getFloat("102"));
				 bean.setHra(resultSet.getFloat("103"));
				 bean.setVda(resultSet.getFloat("138"));
				 bean.setGrade(grade);
				 bean.setStage(resultSet.getInt("srno"));
				 bean.setNewBasic(resultSet.getFloat("newBasic"));
				 bean.setNewstage(1);  // set 1 default increment... see qurey SRNO+1
				 //bean.setNewGrade(0);
				 //bean.setNewBasic(0);
				 
				 list.add(bean);
	   		}
			
			
			connection.close();

		}
		catch(Exception e){e.printStackTrace();}
		
		return list;
	}

	// To save employee details. e.g. in INCREMENT TABLE AS 0
	public boolean insertIncrementSaved(IncrementBean bean,int loggedEmployeeNo){
		
		EmpAttendanceHandler empAttendanceHandler=new EmpAttendanceHandler();
		String currentdate = empAttendanceHandler.getServerDate();
		boolean result = false;
		try{			
			Connection connection = ConnectionManager.getConnection();
			int i=0; 
			
			System.out.println("insertIncrementSaved @@@"+bean.getEmpCode());
			
			String qurey = " IF EXISTS (Select * from Increment where empno ="+bean.getEmpno()+" and basic ="+bean.getBasic()+" ) " +
						   " update Increment set newstage ="+bean.getNewstage()+", newbasic ="+bean.getNewBasic()+" , " +
						   " CURRENTDATE ='"+currentdate+"' , userid="+loggedEmployeeNo+" , ISPROCESED  = 0    " +
						   " where  empno ="+bean.getEmpno()+"                " +
					       " ELSE " +
					       " insert into Increment(EMPNO,EMPCODE,GRADE,STAGE,NEWSTAGE,BASIC,NEWBASIC,DA,HRA,VDA,CURRENTDATE,USERID,ISPROCESED,NAME,INCREMENTDATE)" +
						   " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
			System.out.println(qurey);
			java.sql.PreparedStatement pst =  connection.prepareStatement(qurey);
			
			pst.setInt(1, bean.getEmpno());
			pst.setString(2, bean.getEmpCode());
			pst.setFloat(3, bean.getGrade());
			pst.setFloat(4, bean.getStage());
			pst.setFloat(5, bean.getNewstage());
			pst.setFloat(6, bean.getBasic());
			pst.setFloat(7, bean.getNewBasic());
			pst.setFloat(8, bean.getDa());
			pst.setFloat(9, bean.getHra());
			pst.setFloat(10,bean.getVda() );
			pst.setString(11, currentdate);
			pst.setInt(12, loggedEmployeeNo);
			pst.setInt(13, 0);  //ISPROCESED (0 FOR SAVED & 1 FOR PROCESSED).
			pst.setString(14, bean.getEmpName());
			pst.setString(15, "01-APR-"+currentdate.substring(7,11));
			//pst.setString(15, "01-APR-"+currentdate.substring(7,11));
			
			System.out.println(" @@@@@@@@@@@@@@@  loggedEmployeeNo @@@@@@@@@@@@@@@  "+ loggedEmployeeNo);
			i = pst.executeUpdate();
			
			if(i>0){result = true;}
			connection.close();

		}catch(Exception e){e.printStackTrace();}
		return result;	
	}
	
	// To get saved employee details for processing increment
	public ArrayList<IncrementBean> getSavedEmployeeDetails(int grade /*int cStage, int nStage, int empno*/){
		IncrementBean  bean = null;
		ArrayList<IncrementBean> list = new ArrayList<IncrementBean>();
		try
		{
			Connection connection = ConnectionManager.getConnection();
			connection= ConnectionManager.getConnection();
			Statement statement=connection.createStatement();
			ResultSet resultSet = null;
					
			String qurey = "select * from increment where  ISPROCESED = 0 ";
			System.out.println(qurey);
			resultSet = statement.executeQuery(qurey);
			
			while(resultSet.next())
			{	
				bean = new IncrementBean();
				
				bean.setEmpno(resultSet.getInt("empno"));
				bean.setEmpCode(resultSet.getString("empcode"));
				bean.setGrade(resultSet.getInt("grade"));
				bean.setStage(resultSet.getInt("stage"));
				bean.setNewstage(resultSet.getInt("newstage"));
				bean.setBasic(resultSet.getFloat("basic"));
				bean.setNewBasic(resultSet.getFloat("newbasic"));
				bean.setDa(resultSet.getFloat("da"));
				bean.setHra(resultSet.getFloat("hra"));
				bean.setVda(resultSet.getFloat("vda"));
				bean.setEmpName(resultSet.getString("name"));
				
				list.add(bean);
	   		}		
			connection.close();

		}
		catch(Exception e){e.printStackTrace();}		
		return list;
	}
	
	// To save Processed employee details e.g. in INCREMENT TABLE AS 1
	public boolean insertIncrementProcessed(IncrementBean bean,int loggedEmployeeNo){
		
		EmpAttendanceHandler empAttendanceHandler=new EmpAttendanceHandler();
		String currentdate = empAttendanceHandler.getServerDate();
		boolean result = false;
		try{			
			Connection connection = ConnectionManager.getConnection();
			int i=0; 
			
			String qurey = " IF EXISTS (Select * from Increment where empno ="+bean.getEmpno()+" and basic ="+bean.getBasic()+" ) " +
						   " update Increment set newstage ="+bean.getNewstage()+", newbasic ="+bean.getNewBasic()+" , " +
						   " CURRENTDATE ='"+currentdate+"' , userid="+loggedEmployeeNo+" , ISPROCESED  = 1   " +
						   " where  empno ="+bean.getEmpno()+"                " +
					       " ELSE " +
					       " insert into Increment(EMPNO,EMPCODE,GRADE,STAGE,NEWSTAGE,BASIC,NEWBASIC,DA,HRA,VDA,CURRENTDATE,USERID,ISPROCESED,NAME,INCREMENTDATE)" +
						   " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
			System.out.println(qurey);
			java.sql.PreparedStatement pst =  connection.prepareStatement(qurey);
			
			pst.setInt(1, bean.getEmpno());
			pst.setString(2, bean.getEmpCode());
			pst.setFloat(3, bean.getGrade());
			pst.setFloat(4, bean.getStage());
			pst.setFloat(5, bean.getNewstage());
			pst.setFloat(6, bean.getBasic());
			pst.setFloat(7, bean.getNewBasic());
			pst.setFloat(8, bean.getDa());
			pst.setFloat(9, bean.getHra());
			pst.setFloat(10,bean.getVda() );
			pst.setString(11, currentdate);
			pst.setInt(12, loggedEmployeeNo);
			pst.setInt(13, 1);  //ISPROCESED (0 FOR SAVED & 1 FOR PROCESSED).
			pst.setString(14, bean.getEmpName());
			pst.setString(15, "01-APR-"+currentdate.substring(7,11));
			//pst.setString(15, "01-APR-"+currentdate.substring(7,11));
			i = pst.executeUpdate();
			
			if(i>0){result = true;}
			connection.close();

		}catch(Exception e){e.printStackTrace();}
		return result;	
	}
	
	// To get Incremented data from GRADE_MASTER
	public IncrementBean getProcessedEmpDetails(IncrementBean bean){
		try
		{
			Connection connection = ConnectionManager.getConnection();
			connection = ConnectionManager.getConnection();
			Statement statement=connection.createStatement();
			ResultSet resultSet = null;
			
			// to get data of as per number of Increment(newstage)
			int SRNO = bean.getStage() + bean.getNewstage();						//grade_status = 0 FOR NEW GRADE AND 1 FOR PREVOUS
			
			System.out.println(SRNO+" hello   "+bean.getStage()+"    ak  "+bean.getNewstage());
			String qurey = "select * from GRADE_MASTER where grade_code= "+bean.getGrade()+" and SRNO =  "+SRNO+" and grade_status = 0 ";
			System.out.println(qurey);
			resultSet = statement.executeQuery(qurey);
			
			while(resultSet.next())
			{	
				bean = new IncrementBean();
				
				bean.setNewBasic(resultSet.getFloat("basic"));
				bean.setDa(resultSet.getFloat("da"));
				bean.setHra(resultSet.getFloat("hra"));
				bean.setVda(resultSet.getFloat("vda"));
				bean.setMed(resultSet.getFloat("med_all"));
				bean.setEdu(resultSet.getFloat("edu_all"));
				bean.setConv(resultSet.getFloat("conv_all"));

	   		}
			connection.close();
		}
		catch(Exception e){e.printStackTrace();}
		
		return bean;
	}
	
	// To update Salary Structure here......
	public boolean processIncrementDetails(IncrementBean bean,int loggedEmployeeNo){
		
		EmpAttendanceHandler empAttendanceHandler=new EmpAttendanceHandler();
		String currentdate = empAttendanceHandler.getServerDate();
		String year = currentdate.substring(7,11);
		int[] codes = {101,102,103,104,105,108,138,107,129,140,141};
		//int[] codes = {101,102,103,104,105,108,138};
		boolean result = false;
		try{			
			
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			Statement statement1 = connection.createStatement();
			Statement statement2 = connection.createStatement();
			Statement statement3 = connection.createStatement();
			int i=0; 
			int j=0;
			int length = codes.length;
			for(int z = 0; z < length ; z++){	
				
				float value = (z==0)?bean.getNewBasic():(z==1)?bean.getDa():
							  (z==2)? bean.getHra():(z==3)?bean.getMed():
							  (z==4)?bean.getEdu():(z==5)?bean.getConv():
							  (z==6)?bean.getVda():0;  // Zero for (107,129,140,141)
				System.out.println("  ak   UPDATE CTCDISPLAY SET  TRNCD ="+codes[z]+" , VALUE ="+value+" ,INP_AMT =  "+value+"   " +
						   " WHERE EMPNO = "+bean.getEmpno()+"  and TRNCD !=129  and TRNCD = "+codes[z]+"  ");
				String qurey = " UPDATE CTCDISPLAY SET  TRNCD ="+codes[z]+" , VALUE ="+value+" ,INP_AMT =  "+value+"   " +
							   " WHERE EMPNO = "+bean.getEmpno()+"  and TRNCD !=129  and TRNCD = "+codes[z]+"  ";
				System.out.println(" UPDATE PAYTRAN SET TRNDT ='"+ReportDAO.EOM("01-APR-"+year)+"' , INP_AMT = "+value+" , CAL_AMT = 0 ,ADJ_AMT = 0,ARR_AMT = 0,NET_AMT = 0," +
								" USRCODE ="+loggedEmployeeNo+", STATUS ='N' " +
								" WHERE  EMPNO = "+bean.getEmpno()+"  and TRNCD !=129 AND  TRNCD = "+codes[z]+" and TRNDT between  '"+ReportDAO.BOM("01-APR-"+year)+"'  and  '"+ReportDAO.EOM("01-APR-"+year)+"' "
				);
				String qurey1 = " UPDATE PAYTRAN SET TRNDT ='"+ReportDAO.EOM("01-APR-"+year)+"' , INP_AMT = "+value+" , CAL_AMT = 0 ,ADJ_AMT = 0,ARR_AMT = 0,NET_AMT = 0," +
								" USRCODE ="+loggedEmployeeNo+", STATUS ='N' " +
								" WHERE  EMPNO = "+bean.getEmpno()+" AND  TRNCD = "+codes[z]+" and TRNCD !=129 and TRNDT between  '"+ReportDAO.BOM("01-APR-"+year)+"'   and  '"+ReportDAO.EOM("01-APR-"+year)+"' ";
				
				
				i = statement.executeUpdate(qurey);
				j = statement1.executeUpdate(qurey1);
				   
				System.out.println("ctcdispay qurey "+qurey);
				System.out.println("Paytran qurey "+qurey1);
			}
			String qurey2 = "insert   into Increment_history  select * from  Increment  where empno="+bean.getEmpno()+" ";
			String qurey3 = "delete from  Increment  where empno="+bean.getEmpno()+" ";
			 statement2.executeUpdate(qurey2);
			    statement3.executeUpdate(qurey3);
			
			
			System.out.println("Value of i is: "+ i);
			System.out.println("Value of j is: "+ j);
			if(i>0 && j>0){result = true;}
			connection.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	
		return result;	
	}

	
	
	
}
