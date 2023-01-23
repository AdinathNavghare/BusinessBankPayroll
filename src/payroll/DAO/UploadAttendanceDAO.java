package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFRow;

import payroll.Core.ReportDAO;
import payroll.Model.TranBean;
import payroll.Model.UploadAttendanceBean;


public class UploadAttendanceDAO {

	public int uploadAttend(ArrayList<UploadAttendanceBean> list) {

		
		int cnt=0;
	
	try {
		Connection con = ConnectionManager.getConnection();
		Statement st1=con.createStatement();
		Statement st2=con.createStatement();
		
		
		ResultSet rs= null;
		
		
	
		int i=0;
		
		System.out.println("into UploadTransBean HANDLER---------------"+list.size());
		
		//String code[]={"130","129","132","133","142","210","211","213","216","264"};
		
		
		
		for(UploadAttendanceBean bn:list)
		{
			//System.out.println(" HANDLER--------------- i "+bn.getEMPCODE()  +" "+(++i));
			
			String str1="";
			String upPaytran="";
			String insPaytran="";
			
			try{
			// trncd 130
				 str1=" SELECT * FROM EMPLOYEE_ATTENDANCE  WHERE EMPCODE='"+bn.getEMPCODE()+"' AND ATTEND_DATE='"+bn.getDateG()+"'";
			//	System.out.println(" str1 "+str1);
				rs=st1.executeQuery(str1);
				
				if(rs.next())
				{
					upPaytran=" UPDATE EMPLOYEE_ATTENDANCE SET "
							 + " EMPCODE = '"+bn.getEMPCODE()+"',"
							 + " ATTEND_DATE = '"+bn.getDateG()+"',"
							
														 
							 +"DAY1 ='"+bn.getDays1() +"',"
							 +"DAY2 ='"+bn.getDays2() +"',"
							 +"DAY3 ='"+bn.getDays3() +"',"
							 +"DAY4 ='"+bn.getDays4() +"',"
							 +"DAY5 ='"+bn.getDays5() +"',"
							 +"DAY6 ='"+bn.getDays6() +"',"
							 +"DAY7 ='"+bn.getDays7() +"',"
							 +"DAY8 ='"+bn.getDays8() +"',"
							 +"DAY9 ='"+bn.getDays9() +"',"
							 +"DAY10 ='"+bn.getDays10() +"',"
							 +"DAY11 ='"+bn.getDays11() +"',"
							 +"DAY12 ='"+bn.getDays12() +"',"
							 +"DAY13 ='"+bn.getDays13() +"',"
							 +"DAY14 ='"+bn.getDays14() +"',"
							 +"DAY15 ='"+bn.getDays15() +"',"
							 +"DAY16 ='"+bn.getDays16() +"',"
							 +"DAY17 ='"+bn.getDays17() +"',"
							 +"DAY18 ='"+bn.getDays18() +"',"
							 +"DAY19 ='"+bn.getDays19() +"',"
							 +"DAY20 ='"+bn.getDays20() +"',"
							 +"DAY21 ='"+bn.getDays21() +"',"
							 +"DAY22 ='"+bn.getDays22() +"',"
							 +"DAY23 ='"+bn.getDays23() +"',"
							 +"DAY24 ='"+bn.getDays24() +"',"
							 +"DAY25 ='"+bn.getDays25() +"',"
							 +"DAY26 ='"+bn.getDays26() +"',"
							 +"DAY27 ='"+bn.getDays27() +"',"
							 +"DAY28 ='"+bn.getDays28() +"',"
							 +"DAY29 ='"+bn.getDays29() +"',"
							 +"DAY30 ='"+bn.getDays30() +"',"
							 +"DAY31 ='"+bn.getDays31() +"'"
							 + " WHERE EMPCODE='"+bn.getEMPCODE()+"' and "
							 + " ATTEND_DATE = '"+bn.getDateG()+"'";
					
					
					

				System.out.println("upPaytran :"+upPaytran);
					st2.executeUpdate(upPaytran);
					cnt=1;
				}
				else
				{
					insPaytran="INSERT INTO EMPLOYEE_ATTENDANCE (EMPCODE,ATTEND_DATE,STATUS,day1,	day2,	day3,	day4,	day5,	day6,	day7,	day8,	day9,	day10,	day11,	day12,	day13,	day14,	day15,"
							+ "	day16,	day17,	day18,	day19,	day20,	day21,	day22,	day23,	day24,	day25,	day26,	day27,	day28,	day29,	day30,	day31)"
								
								+ "VALUES ("
								+ "'"+
								bn.getEMPCODE()
								+"',"
								+ "'"+
								bn.getDateG()
								+"',"
								+ "'PROCESS'"
								+",'"+
								bn.getDays1()
										+"',"
								+ "'"+
										bn.getDays2()
										+"',"
								+ "'"+
										bn.getDays3() 
										+"',"
								+ "'"+
										bn.getDays4()
										+"',"
								+ "'"+
										bn.getDays5()
										+"',"
								+ "'"+
										bn.getDays6()
										+"',"
								+ "'"+
										bn.getDays7()
										+"',"
								+ "'"+
										bn.getDays8()
										+"',"
								+ "'"+
										bn.getDays9() 
										+"',"
								+ "'"+
										bn.getDays10() 
										+"',"
								+ "'"+
										bn.getDays11()
										+"',"
								+ "'"+
										bn.getDays12()
										+"',"
								+ "'"+
										bn.getDays13()
										+"',"
								+ "'"+
										bn.getDays14()
										+"',"
								+ "'"+
										bn.getDays15()
										+"',"
								 + "'"+
										bn.getDays16()
										 +"',"
								 + "'"+
										 bn.getDays17()
										 +"',"
								 + "'"+
										 bn.getDays18()
										 +"',"
								 + "'"+
										 bn.getDays19()
										 +"',"
								 + "'"+
										 bn.getDays20()
										 +"',"
								 + "'"+
										 bn.getDays21()
										 +"',"
								 + "'"+
										 bn.getDays22()
										 +"',"
								 + "'"+
										 bn.getDays23()
										 +"',"
								 + "'"+
										 bn.getDays24()
										 +"',"
								 + "'"+
										 bn.getDays25()
										 +"',"
								 + "'"+
										 bn.getDays26()
										 +"',"
								 + "'"+
										 bn.getDays27()
										 +"',"
								 + "'"+
										 bn.getDays28()
										 +"',"
								 + "'"+
										 bn.getDays29()
										 +"',"
								 + "'"+
										 bn.getDays30()
										 +"',"
								 + "'"+
										 bn.getDays31()
								+"' )";

			System.out.println("insPaytran:"+insPaytran);
						st2.executeUpdate(insPaytran);
						cnt=1;
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
				
				
		}
		
		
		System.out.println("cnt 2:"+cnt);
		con.close();
	} catch (Exception e) {
		e.printStackTrace();
		cnt=0;
	}
	
	return cnt;
	
	
	}

	public boolean finalizeAttendance(String emplist, String date, String user) {
		Connection con= ConnectionManager.getConnection();
		TranBean tb = null;
		boolean flag = false;
		
		try
		{
			Statement st = con.createStatement();
			///ResultSet rs= null;
			date = "1-"+date;
			System.out.println(" date==>"+date);
			
			 // Select p.*,e.empcode from PAYTRAN p ,empmast e where e.EMPNO=p.EMPNO
			String query="update EMPLOYEE_ATTENDANCE set status='APPROVE' where EMPCODE IN(SELECT EMPCODE FROM empmast WHERE EMPNO IN("+emplist+") AND STATUS ='A') AND ATTEND_DATE='"+ReportDAO.EOM(date)+"'";
		 st.execute(query);
		//	updateAttdPaytran(emplist,date);
			 ///update paytran
			 String empquery=" select * from EMPLOYEE_ATTENDANCE WHERE EMPCODE in(SELECT EMPCODE FROM empmast WHERE EMPNO IN("+emplist+") AND STATUS ='A') AND STATUS='APPROVE' and ATTEND_DATE='"+ReportDAO.EOM(date)+"'";
				//System.out.println("empquery : "+empquery);
				ResultSet rs=st.executeQuery(empquery);
				Statement stg=con.createStatement();
				ResultSet rsg=null; 
				
				//to get db column name/table info
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();//count coulumn
				 System.out.println("col count"+columnCount);
				//String []colValue = {"EMPNO","EMPCODE","FNAME","DOB","DOJ"};
				int j=0,rowIndex=1;
				
				
				int i = 0,k = 0;
				String sqld ="";
				String empcode ="";
						String attend="";
						
				
				while(rs.next())
				{
					float totalLeave =0.0f;
					float P=0;//present
					float A=0;//absent
					float PL=0;//
					float CL=0;//
					float SL=0;
					float WO=0;
					float HD = 0;//half day
				 	float LT = 0;//late mark
					float T =0;;// travel like present
					float HO = 0; //holiday
					float RO = 0;//rotation off like present
					float CO = 0;//compesite off like present
					float NJ = 0; //new joining
					
					String insEmpAtt = "insert into employee_Attendance values('"+rs.getString("EMPCODE")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''," + 
							"(SELECT DATEADD(s,-1,DATEADD(mm, DATEDIFF(m,0,'"+rs.getString("attend_date")+"')+2,0))),'PROCESS')";
					System.out.println(" insEmpAtt : "+insEmpAtt);
				
				  Statement empAttend = con.createStatement(); 
				  empAttend.execute(insEmpAtt);
				 
					
					
					
					System.out.println(" empno code:"+rs.getString("EMPCODE"));
					empcode = rs.getString("EMPCODE");
					for(i=0, k=2;i<=columnCount-2;i++)
					{
						
							
							attend = rs.getString(rsmd.getColumnName(k++));
							//System.out.println(" xzkcv :"+attend);
							if(attend.equalsIgnoreCase("A"))
							{
								A = A+1;
							}
							else if(attend.equalsIgnoreCase("P"))
							{
								P = P+1;
							}
							else if(attend.equalsIgnoreCase("HD"))
							{
								HD = (float) (HD + 0.5);
							}
							else if(attend.equalsIgnoreCase("LT"))
							{
								LT = LT+1;
							}
							else if(attend.equalsIgnoreCase("SL"))
							{
								SL = SL+1;
							}
							else if(attend.equalsIgnoreCase("PL"))
							{
								PL = PL+1;
							}
							else if(attend.equalsIgnoreCase("WO"))
							{
								WO = WO+1;
							}
							else
							{
								//System.out.println("nothing ");
							}
							
					}
					totalLeave = A + HD ;
					System.out.println("totalLeave :"+totalLeave);
					
					//update leavebal CL
					float bal=0f;
					float totcr=0f;
					float totdb=0f;
					String sqlCL = " select * from leavebal where " 
					+" leavecd=3 and empno=  (select empno from empmast where EMPCODE = '"+empcode+"') " 
					+" and srno = (select  max(srno)  from leavebal where leavecd=3 and "
					+ "empno=(select empno from empmast where EMPCODE = '"+empcode+"')  ) ";
					
					System.out.println("sqlCL : "+sqlCL);
					Statement stCL = con.createStatement();
					ResultSet rsCL = stCL.executeQuery(sqlCL);
					while(rsCL.next())
					{
						bal = rsCL.getFloat("bal");
						totcr = rsCL.getFloat("totcr");
						totdb = rsCL.getFloat("totdr");
					}
					String 	sqlLeave = "";
					System.out.println("bal :"+bal );
					if(bal>totalLeave) ///BAL =12 AND TOTLEAVE = 2
					{
						bal = bal - totalLeave;
						totdb  =  totdb + totalLeave;
						totalLeave	= 	0;
					}
					else
					{
						totalLeave = totalLeave - bal;
						totdb = totdb	+ 	bal;
						bal = 0;
					}
					
					
					sqlLeave = "IF EXISTS (SELECT  *  FROM LEAVEBAL  WHERE EMPNO = (select empno from empmast where EMPCODE = '"+empcode+"' ) AND LEAVECD = 3  and BALDT='"+ReportDAO.EOM(date)+"')    "
							+" BEGIN" 
							+" UPDATE LEAVEBAL SET bal = "+bal+"  , totdr ="+totdb+"WHERE EMPNO = (select empno from empmast where EMPCODE = '"+empcode+"' ) AND LEAVECD = 3  AND  BALDT = '"+ReportDAO.EOM(date)+"'" 
							+" end  "
							+ "  ELSE"
							+ "			 INSERT INTO LEAVEBAL VALUES('"+ReportDAO.EOM(date)+"',(select empno from empmast where EMPCODE = '"+empcode+"' ),3 ,"+bal+","+totcr+","+totdb+") ";
				
					System.out.println("sqlLeave : "+sqlLeave);
					Statement stLeave = con.createStatement();
					stLeave.execute(sqlLeave);
					
					
					//update leavebal SL
					
					float bal1=0f;
					float totcr1=0f;
					float totdb1=0f;
					String sqlSL = " select * from leavebal where " 
					+" leavecd= 2  and empno=  (select empno from empmast where EMPCODE = '"+empcode+"') " 
					+" and srno = (select  max(srno)  from leavebal where leavecd = 2 and "
					+ "empno=(select empno from empmast where EMPCODE = '"+empcode+"')  ) ";
					
					System.out.println("sqlCL : "+sqlSL);
					Statement stSL = con.createStatement();
					ResultSet rsSL = stSL.executeQuery(sqlSL);
					while(rsSL.next())
					{
						bal1 = rsSL.getFloat("bal");
						totcr1 = rsSL.getFloat("totcr");
						totdb1= rsSL.getFloat("totdr");
					}
					String 	sqlLeave1 = "";
					System.out.println("bal :"+bal1 );
					if(bal1>SL) ///BAL =12 AND TOTLEAVE = 2
					{
						bal1 = bal1 - SL;
						totdb1  =  totdb1 + SL;
						SL	= 	0 ;
					}
					else
					{
						SL = SL - bal1;
						totdb1 = totdb1	+ 	bal1;
						bal1 = 0;
					}
					totalLeave +=SL;
					sqlLeave1 = "IF EXISTS (SELECT  *  FROM LEAVEBAL  WHERE EMPNO = (select empno from empmast where EMPCODE = '"+empcode+"' ) AND LEAVECD = 2  and BALDT='"+ReportDAO.EOM(date)+"')    "
							+" BEGIN" 
							+" UPDATE LEAVEBAL SET bal = "+bal1+"  , totdr ="+totdb1+"WHERE EMPNO = (select empno from empmast where EMPCODE = '"+empcode+"' ) AND LEAVECD = 2   AND  BALDT = '"+ReportDAO.EOM(date)+"'" 
							+" end  "
							+ "  ELSE"
							+ "			 INSERT INTO LEAVEBAL VALUES('"+ReportDAO.EOM(date)+"',(select empno from empmast where EMPCODE = '"+empcode+"' ),2 ,"+bal1+","+totcr1+","+totdb1+") ";
				
					System.out.println("sqlLeave : "+sqlLeave1);
					Statement stLeave1 = con.createStatement();
					stLeave1.execute(sqlLeave1);
					
					
					//  update paytran
					sqld = "IF EXISTS (SELECT * FROM PAYTRAN WHERE EMPNO = (select empno from empmast where EMPCODE = '"+empcode+"' ) AND TRNCD = 301 and TRNDT='"+ReportDAO.EOM(date)+"')"
							+" BEGIN" 
							+" UPDATE PAYTRAN SET INP_AMT = "+totalLeave+" WHERE EMPNO = (select empno from empmast where EMPCODE = '"+empcode+"' ) AND TRNCD = 301 AND  TRNDT = '"+ReportDAO.EOM(date)+"'" 
							+" end  "
							+ "  ELSE INSERT INTO PAYTRAN VALUES('"+ReportDAO.EOM(date)+"',(select empno from empmast where EMPCODE = '"+empcode+"' ),301,0,"+totalLeave+",0.00,0.00,0.00,0.00,'','System','"+ReportDAO.EOM(date)+"','P') ";
				
					System.out.println("sqld :"+sqld);
					//System.out.println("J :"+(++j));
					totalLeave = 0.0F;
					System.out.println("totalLeave :"+totalLeave);
					stg.execute(sqld);
					
					
				}
			
			
			flag = true;
			System.out.println("flag : "+flag);
			con.close();
			
		}
		catch(Exception e)
		{
			try
			{
				con.close();
			} 
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		return flag;
		
	}
	
	public void updateAttdPaytran(String emplist,String date)
	{
		 ///update paytran
		Connection con = ConnectionManager.getConnection();
		Statement st;
		try {
			st = con.createStatement();
		
		
		 String empquery=" select * from EMPLOYEE_ATTENDANCE WHERE EMPCODE in(SELECT EMPCODE FROM empmast WHERE EMPNO IN("+emplist+") AND STATUS ='A') AND STATUS='APPROVE' and ATTEND_DATE='"+ReportDAO.EOM(date)+"'";
			//System.out.println("empquery : "+empquery);
			ResultSet rs=st.executeQuery(empquery);
			Statement stg=con.createStatement();
			ResultSet rsg=null; 
			
			//to get db column name/table info
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();//count coulumn
			 System.out.println("col count"+columnCount);
			//String []colValue = {"EMPNO","EMPCODE","FNAME","DOB","DOJ"};
			int j=0,rowIndex=1;
			
			
			int i = 0,k = 0;
			String sqld ="";
			String empcode ="";
					String attend="";
					
			
			while(rs.next())
			{
				float totalLeave =0.0f;
				float P=0;//present
				float A=0;//absent
				float PL=0;//
				float CL=0;//
				float SL=0;
				float WO=0;
				float HD = 0;//half day
				float LT = 0;//late mark
				float T =0;;// travel like present
				float HO = 0; //holiday
				float ROFF = 0;//rotation off like present
				float COOFF = 0;//compesite off like present
				float NJ = 0; //new joining
				System.out.println(" empno code:"+rs.getString("EMPCODE"));
				empcode = rs.getString("EMPCODE");
				for(i=0, k=2;i<=columnCount-2;i++)
				{
					
						
						attend = rs.getString(rsmd.getColumnName(k++));
						//System.out.println(" xzkcv :"+attend);
						if(attend.equalsIgnoreCase("A"))
						{
							A = A+1;
						}
						else if(attend.equalsIgnoreCase("P") || attend.equalsIgnoreCase("COFF") || attend.equalsIgnoreCase("T") || attend.equalsIgnoreCase("ROFF"))
						{
							P = P+1;
						}
						else if(attend.equalsIgnoreCase("HD"))
						{
							HD = (float) (HD + 0.5);
						}
						else if(attend.equalsIgnoreCase("LT"))
						{
							LT = LT+1;
						}
						else if(attend.equalsIgnoreCase("HO"))
						{
							HO = HO+1;
						}
						else if(attend.equalsIgnoreCase("SL"))
						{
							SL = SL+1;
						}
						else if(attend.equalsIgnoreCase("PL"))
						{
							PL = PL+1;
						}
						else if(attend.equalsIgnoreCase("CL"))
						{
							CL = CL+1;
						}
						else if(attend.equalsIgnoreCase("WO"))
						{
							WO = WO+1;
						}
						else
						{
							//System.out.println("nothing ");
						}
						
				}
				
				
				System.out.println("A :"+A+"HD :"+HD);
				totalLeave = A + HD ;
				System.out.println("totalLeave :"+totalLeave);
				/*sqld = " UPDATE PAYTRAN SET INP_AMT="+totalLeave+" WHERE EMPNO = (SELECT EMPNO FROM EMPMAST WHERE EMPCODE ='"+empcode+"') and trndt='"+ReportDAO.EOM(date)+"'";*/
				
				
				sqld = "IF EXISTS (SELECT * FROM PAYTRAN WHERE EMPNO = (select empno from empmast where EMPCODE = '"+empcode+"' ) AND TRNCD = 301 and TRNDT='"+ReportDAO.EOM(date)+"')"
						+" BEGIN" 
						+" UPDATE PAYTRAN SET INP_AMT = "+totalLeave+" WHERE EMPNO = (select empno from empmast where EMPCODE = '"+empcode+"' ) AND TRNCD = 301 AND  TRNDT = '"+ReportDAO.EOM(date)+"'" 
						+" end  "
						+ "  ELSE INSERT INTO PAYTRAN VALUES('"+ReportDAO.EOM(date)+"',(select empno from empmast where EMPCODE = '"+empcode+"' ),301,0,"+totalLeave+",0.00,0.00,0.00,0.00,'','System','"+ReportDAO.EOM(date)+"','P') ";
			
				System.out.println("sqld :"+sqld);
				//System.out.println("J :"+(++j));
				totalLeave = 0.0F;
				System.out.println("totalLeave :"+totalLeave);
				stg.execute(sqld);
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int updateEmpAttendance(UploadAttendanceBean ua){


		
		System.out.println("i am in save attendance");
		
		String updtEmp  = "";
		//String empSql = "SELECT * FROM EMPLOYEE_ATTENDANCE WHERE EMPCODE='"+ua.getEMPCODE()+"' AND ATTEND_DATE='"+ua.getDateG()+"' ";
		int  flag =0;
		try
		{
			
		
		Connection con = ConnectionManager.getConnection();
		Statement st =con.createStatement();
		
		//int flag = st.executeUpdate(updtEmp); 
		
		 updtEmp ="UPDATE EMPLOYEE_ATTENDANCE SET "
				+ " DAY1 ='"+ua.getDays1()+"', "
				+ " DAY2 ='"+ua.getDays2()+"', "
				+ " DAY3 ='"+ua.getDays3()+"', "
				+ " DAY4 ='"+ua.getDays4()+"', "
				+ " DAY5 ='"+ua.getDays5()+"', "
				+ " DAY6 ='"+ua.getDays6()+"', "
				+ " DAY7 ='"+ua.getDays7()+"', "
				+ " DAY8 ='"+ua.getDays8()+"', "
				+ " DAY9 ='"+ua.getDays9()+"', "
				+ " DAY10 ='"+ua.getDays10()+"', "
				+ " DAY11 ='"+ua.getDays11()+"', "
				+ " DAY12 ='"+ua.getDays12()+"', "
				+ " DAY13 ='"+ua.getDays13()+"', "
				+ " DAY14 ='"+ua.getDays14()+"', "
				+ " DAY15 ='"+ua.getDays15()+"', "
				+ " DAY16 ='"+ua.getDays16()+"', "
				+ " DAY17 ='"+ua.getDays17()+"', "
				+ " DAY18 ='"+ua.getDays18()+"', "
				+ " DAY19 ='"+ua.getDays19()+"', "
				+ " DAY20 ='"+ua.getDays20()+"', "
				+ " DAY21 ='"+ua.getDays21()+"', "
				+ " DAY22 ='"+ua.getDays22()+"', "
				+ " DAY23 ='"+ua.getDays23()+"', "
				+ " DAY24 ='"+ua.getDays24()+"', "
				+ " DAY25 ='"+ua.getDays25()+"', "
				+ " DAY26 ='"+ua.getDays26()+"', "
				+ " DAY27 ='"+ua.getDays27()+"', "
				+ " DAY28 ='"+ua.getDays28()+"', "
				+ " DAY29 ='"+ua.getDays29()+"', "
				+ " DAY30 ='"+ua.getDays30()+"', "
				+ " DAY31 ='"+ua.getDays31()+"' "
				+ " where empcode='"+ua.getEMPCODE()+"' "
				+ " AND ATTEND_DATE='"+ua.getDateG()+"' ";
		
		  flag = st.executeUpdate(updtEmp);
		
		System.out.println(" updtEmp :"+updtEmp);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	public int chkAttendDate(String date) 
	{

		System.out.println("CHECK IT : chkAttendDate");
		System.out.println("chkAttendDate : "+date);
		System.out.println("pre date : "+date.substring(0, 3));
		String year = "";
		if(date.substring(0, 3).equalsIgnoreCase("Jan"))
		{
			year = date.substring(4);
		
		 String month = ReportDAO.getPreMonth(date.substring(0, 3)); 
		//date = "1-"+date;
		date = "1-"+month+"-"+(Integer.parseInt(year)-1);
		}
		else
		{
			 String month = ReportDAO.getPreMonth(date.substring(0, 3)); 
				//date = "1-"+date;
				date = "1-"+month+"-"+date.substring(4);
		}
		System.out.println("pre date : "+date);
		String checkPS = "select trndt from paytran_stage where trndt='"+ReportDAO.EOM(date)+"'";
		try {
		Connection con =ConnectionManager.getConnection();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(checkPS);
		
		if(rs.next())
		{
			return 1;
		}
		else
		{
			return 0;
		}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	
	
	public int takeAttendance(String date) 
	{
		System.out.println("HI takeAttendance DATE : "+date);

		String Frdt ="01-"+date;
		String	 FrmDate="";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	    SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
	 
		try {
			FrmDate = output.format(sdf.parse(Frdt));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		FrmDate=FrmDate.substring(0,7);
		
	
		String checkPS = "SELECT * FROM AttendanceLogs  WHERE AttendanceDate LIKE '%"+FrmDate+"%' order by AttendanceLogId asc";
		String checkEmpDetails = "SELECT * FROM Employees";
		
		System.out.println("Attendance Table  : "+checkPS);
		
		try {
		Connection con =ConnectionManager.getConnectionTech_access();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(checkPS);
		
		Connection conn =ConnectionManager.getConnection();
		Statement st1 = conn.createStatement();
	
		while(rs.next()){
				int i=0;
		
		String checkPSInsert = "INSERT INTO AttendanceLogs_ByMachine (AttendanceLogId, AttendanceDate, EmployeeId, InTime, InDeviceId, OutTime, OutDeviceId, Duration, "
				+ "LateBy, EarlyBy, IsOnLeave, LeaveType, LeaveDuration, WeeklyOff, Holiday, LeaveRemarks, PunchRecords, ShiftId, Present, Absent, Status, StatusCode, "
				+ "P1Status, P2Status, P3Status, IsonSpecialOff, SpecialOffType, SpecialOffRemark, SpecialOffDuration, OverTime, OverTimeE, MissedOutPunch, MissedInPunch, "
				+ "C1, C2, C3, C4, C5, C6, C7, Remarks, LeaveTypeId) "
				+ "values("
				+rs.getInt("AttendanceLogId")+", '"+rs.getDate("AttendanceDate")+"', "+rs.getInt("EmployeeId")+", '"+rs.getString("InTime")+"', "
				+ "'"+rs.getString("InDeviceId")+"', '"+rs.getString("OutTime")+"', '"+rs.getString("OutDeviceId")+"', "+rs.getInt("Duration")+", "+rs.getInt("LateBy")+", "
				+ ""+rs.getInt("EarlyBy")+", '"+rs.getString("IsOnLeave")+"', '"+rs.getString("LeaveType")+"', "+rs.getInt("LeaveDuration")+", "+rs.getInt("WeeklyOff")+", "
				+ ""+rs.getInt("Holiday")+", '"+rs.getString("LeaveRemarks")+"', '"+rs.getString("PunchRecords")+"', "+rs.getInt("ShiftId")+", "+rs.getInt("Present")+", "
				+ ""+rs.getInt("Absent")+", '"+rs.getString("Status")+"', '"+rs.getString("StatusCode")+"', '"+rs.getString("P1Status")+"', '"+rs.getString("P2Status")+"', "
				+ "'"+rs.getString("P3Status")+"', '"+rs.getString("IsonSpecialOff")+"', '"+rs.getString("SpecialOffType")+"', '"+rs.getString("SpecialOffRemark")+"', "
				+ ""+rs.getInt("SpecialOffDuration")+", "+rs.getInt("OverTime")+", "+rs.getInt("OverTimeE")+", '"+rs.getString("MissedOutPunch")+"', "
				+ "'"+rs.getString("MissedInPunch")+"', '"+rs.getString("C1")+"', '"+rs.getString("C2")+"', '"+rs.getString("C3")+"', '"+rs.getString("C4")+"', "
				+ "'"+rs.getString("C5")+"', '"+rs.getString("C6")+"', '"+rs.getString("C7")+"', '"+rs.getString("Remarks")+"', "+rs.getInt("LeaveTypeId")+")";
		
		
		System.out.println("checkPSInsert : "+checkPSInsert);
		i = st1.executeUpdate(checkPSInsert);

		}
		rs.close();
		st1.close();
		
		Connection connnew =ConnectionManager.getConnection();
		Statement stnew = connnew.createStatement();

		String checkPSInsertAgain = "INSERT INTO Employee_Attendance (Attendance_Log_Id, Attendance_Date, Employee_Id, IN_Time, OUT_Time, Status_Code, Status) "
				+ "SELECT AttendanceLogId, AttendanceDate, EmployeeId, InTime, OutTime, StatusCode, Status  FROM AttendanceLogs_ByMachine";

		int j=0;
		j = stnew.executeUpdate(checkPSInsertAgain);
		
		stnew.close();
		
		Statement stempDetails = con.createStatement();
		ResultSet rstempDetails = stempDetails.executeQuery(checkEmpDetails);

		Connection connemp =ConnectionManager.getConnection();
		Statement stEmployee = connemp.createStatement();
		
		while(rstempDetails.next()){
					int p=0;
		
		String insertEmpDetails = "INSERT INTO EmployeesDetails_ByAttendancesMachine (EmployeeId, EmployeeName, EmployeeCode, StringCode, NumericCode, Gender, CompanyId, DepartmentId, Designation, "
				+ "CategoryId, DOJ, DOR, DOC, EmployeeCodeInDevice, EmployeeRFIDNumber, EmployementType, Status, EmployeeDevicePassword, EmployeeDeviceGroup, FatherName, MotherName, ResidentialAddress, "
				+ "PermanentAddress, ContactNo, Email, DOB, PlaceOfBirth, Nomenee1, Nomenee2, Remarks, RecordStatus, C1, C2, C3, C4, C5, C6, C7, Location, BloodGroup, WorkPlace, ExtensionNo, "
				+ "LoginName, LoginPassword, Grade, Team, IsRecieveNotification, HolidayGroup, ShiftGroupId, ShiftRosterId, LastModifiedBy, AadhaarNumber) "
				+ "values("
				+rstempDetails.getInt("EmployeeId")+", '"+rstempDetails.getString("EmployeeName")+"', '"+rstempDetails.getString("EmployeeCode")+"', '"+rstempDetails.getString("StringCode")+"', "
				+rstempDetails.getInt("NumericCode")+", '"+rstempDetails.getString("Gender")+"', "+rstempDetails.getInt("CompanyId")+","+rstempDetails.getInt("DepartmentId")+",'"
				+rstempDetails.getString("Designation")+"' ,"+rstempDetails.getInt("CategoryId")+", '"+rstempDetails.getDate("DOJ")+"', '"+rstempDetails.getDate("DOR")+"', '"
				+rstempDetails.getDate("DOC")+"', "+rstempDetails.getInt("EmployeeCodeInDevice")+" ,'"+rstempDetails.getString("EmployeeRFIDNumber")+"', '"+rstempDetails.getString("EmployementType")+"' , '"
				+rstempDetails.getString("Status")+"' , '"+rstempDetails.getString("EmployeeDevicePassword")+"' , '"+rstempDetails.getString("EmployeeDeviceGroup")+"' , '"+rstempDetails.getString("FatherName")+"' , '"
				+rstempDetails.getString("MotherName")+"' , '"+rstempDetails.getString("ResidentialAddress")+"' , '"+rstempDetails.getString("PermanentAddress")+"' , '"+rstempDetails.getString("ContactNo")+"' , '"
				+rstempDetails.getString("Email")+"' , '"+rstempDetails.getDate("DOB")+"' , '"+rstempDetails.getString("PlaceOfBirth")+"' , '"+rstempDetails.getString("Nomenee1")+"' , '"
				+rstempDetails.getString("Nomenee2")+"' , '"+rstempDetails.getString("Remarks")+"' , "+rstempDetails.getInt("RecordStatus")+", '"+rstempDetails.getString("C1")+"' , '"
				+rstempDetails.getString("C2")+"' , '"+rstempDetails.getString("C3")+"' , '"+rstempDetails.getString("C4")+"' , '"+rstempDetails.getString("C5")+"' , '"
				+rstempDetails.getString("C6")+"' , '"+rstempDetails.getString("C7")+"' , '"+rstempDetails.getString("Location")+"' , '"+rstempDetails.getString("BloodGroup")+"' , '"
				+rstempDetails.getString("WorkPlace")+"' , '"+rstempDetails.getString("ExtensionNo")+"' , '"+rstempDetails.getString("LoginName")+"' , '"+rstempDetails.getString("LoginPassword")+"' , '"
				+rstempDetails.getString("Grade")+"' , '"+rstempDetails.getString("Team")+"' , "+rstempDetails.getInt("IsRecieveNotification")+","+rstempDetails.getInt("HolidayGroup")+","
				+rstempDetails.getInt("ShiftGroupId")+","+rstempDetails.getInt("ShiftRosterId")+", '"+rstempDetails.getString("LastModifiedBy")+"' , '"+rstempDetails.getString("AadhaarNumber")+"')";
		
		System.out.println("insertEmpDetails : "+insertEmpDetails);
		p = stEmployee.executeUpdate(insertEmpDetails);
		
		}
		rstempDetails.close();
		stEmployee.close();

		con.close();
		conn.close();
		connnew.close();
		connemp.close();
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	
	public Integer  chk_Attendance_month(String fromdate,String todate,int appno)  throws ParseException
	{

		 System.out.println("In chk_Attendance_month");
		 int flags = 0;
	   try
	   {
		   
		   System.out.println("In chk_Attendance_month NEW");
		   System.out.println(fromdate);
		   System.out.println(todate);
		   System.out.println(appno);
		   
		   
		   fromdate =  fromdate.substring(0, 7);
		   System.out.println("NEWLY fromdate : "+fromdate);
		   
	    	 Connection conn=ConnectionManager.getConnection();
	    	
	    	 String overlap="";
	    	 if(appno==0000000)// for apply leave   by akshay
	    	 {
	    		// overlap="SELECT COUNT(*) as count  FROM PAYTRAN_STAGE WHERE TRNDT='2020-07-31' AND TRNCD=999";
	    		 overlap="SELECT COUNT(*) as count  FROM PAYTRAN_STAGE WHERE TRNDT LIKE '%"+fromdate+"%' AND TRNCD=999";
/*	    	  overlap="select COUNT(*) as count from leavetran where (('"+fromdate+"'" +
						" between FRMDT and TODT OR '"+todate+"'" +
						 " between FRMDT and TODT ) or (FRMDT "  +
						 " between  '"+fromdate+"' and '"+todate+"' OR TODT" +
						 " between  '"+fromdate+"' and'"+todate+"'  ))" +
						 " and EMPNO="+empno+"and status in ('SANCTION','PENDING') AND TRNTYPE='D' ";
*/	    	 }
	    	 else
	    	 { // for sanction leave by akshay
	    		 overlap="SELECT COUNT(*) as count  FROM PAYTRAN_STAGE WHERE TRNDT LIKE '%"+fromdate+"%' AND TRNCD=999";
/*	    	  overlap="select COUNT(*) as count from leavetran where (('"+fromdate+"'" +
						" between FRMDT and TODT OR '"+todate+"'" +
						 " between FRMDT and TODT ) or (FRMDT "  +
						 " between  '"+fromdate+"' and '"+todate+"' OR TODT" +
						 " between  '"+fromdate+"' and'"+todate+"'  ))" +
						 " and EMPNO="+appno+"and status in ('SANCTION','PENDING') AND TRNTYPE='D' AND APPLNO!="+appno;
*/	   	     }
	    	 Statement stmt=conn.createStatement();
	    	 ResultSet rslt=stmt.executeQuery(overlap);
	    	System.out.println("chk_Leave_betDate:-"+overlap);
			 while(rslt.next())
			 {
				 conn.commit();
				 flags=rslt.getInt("count");
			 }
			 conn.close();
	    } 
	   	catch (SQLException e) 
	   	{
		   e.printStackTrace();
		}
	return flags;

	}

	

	
	
}
