package payroll.Core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.icu.util.StringTokenizer;

import payroll.DAO.ConnectionManager;
import payroll.DAO.LookupHandler;
import payroll.Model.EmployeeBean;
import payroll.Model.Lookup;

public class OraToSqlCoversion {

	static Connection conn;
	LookupHandler lkhp = new LookupHandler();
	Lookup lookupBean = new Lookup() ;
	public static void main(String args[])
	{
		
		
		//System.out.println("emp no is"+EMPNO);
		conn=ConnectionManager.getConnection();
		ResultSet rs;
		int Wrk_Days =0;
		float Wrk_Amt=0.00f;
		float onAmt1=0.00f;
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
		String dt = format.format(date);
		Connection conhc=null;
		try
		{
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		
		conhc = DriverManager.getConnection("jdbc:sqlserver://SWAPNIL-PC:1433;instanceName=SQLEXPRESS;databaseName=HCPAYROLL;integratedSecurity=true");
		}
		catch (Exception ex)
		{
			System.out.println("Error in Connection Manager "+ex);
		}
		PreparedStatement prst=null;
		SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd");
		fromformat.setLenient(false);
		EmployeeBean empbean =new EmployeeBean();
		
		try
		{
			Statement stmt=conn.createStatement();
			Statement st3=conhc.createStatement();
			rs=stmt.executeQuery("select * from employee_master");
			System.out.println("--- IN ----");
			while(rs.next())
			{
				int empno = Integer.parseInt(rs.getString("emp_srno"));
				
				System.out.println(rs.getString("emp_name"));
				
				
				
				String name[]  = new String[4];
				StringTokenizer tok = new StringTokenizer(rs.getString("emp_name"), ". ");
				System.out.println("No of Tkens "+tok.countTokens());
				int i=0;
				if(tok.countTokens()>=4)
				{
					while(tok.hasMoreTokens())
					{
						name[i]=tok.nextToken().toString();
						i++;
					}
				}
				else
				{
					while(tok.hasMoreTokens())
					{
						name[i]=tok.nextToken().toString();
						i++;
					}
					String temp = name[i-1];
					name[i-1] ="";
					name[i]=temp;
				}
				
				Statement st = conhc.createStatement();
				st.executeUpdate("update EMPMAST set SALUTE='"+name[0]+".' where EMPNO = "+empno);
			/*	prst = conhc
						.prepareStatement("insert into EMPMAST(EMPNO,SALUTE,FNAME,MNAME,LNAME,GENDER,DOB," +
								"DOJ,DOL,PANNO,PFNO,PFNOMINEE,PFNOMIREL,BGRP,FHNAME) values" +
								"("+empno+",'"+name[0]+".','"+name[1]+"','"+name[2]+"','"+name[3]+"','"+rs.getString("emp_sex").substring(0,1)+"'," +
								"'"+rs.getDate("birth_date")+"','"+rs.getDate("joining_date")+"','"+rs.getDate("termination_date")+"'," +
								"'"+rs.getString("PAN_NO")+"','"+rs.getString("pf_no")+"','"+rs.getString("nominee")+"','"+rs.getString("nominee_relation")+"'," +
								"'"+rs.getString("blood_group")+"','"+rs.getString("Father_Husband_Name")+"')");
				
				
				prst.execute();
				
				
				try
				{
				prst = conhc
						.prepareStatement("insert into EMPAUX(EMPNO,ADDR1,ADDR3,TELNO,ADDRTYPE) values" +
								"("+empno+",'"+rs.getString("postal_address")+"','"+rs.getString("emp_id")+"'," +
							       ""+Long.parseLong(rs.getString("contact_no")==null?"0":rs.getString("contact_no"))+",'CA'");
				}
				catch(NumberFormatException e)
				{
					prst = conhc
							.prepareStatement("insert into EMPAUX(EMPNO,ADDR1,ADDR3,TELNO,ADDRTYPE" +
									") values" +
									"("+empno+",'"+rs.getString("postal_address")+"','"+rs.getString("emp_id")+"',0,'CA')");
				}
				prst.execute();
				prst = conhc
						.prepareStatement("insert into EMPAUX(EMPNO,ADDR2,ADDR3,ADDRTYPE) values" +
								"("+empno+",'"+rs.getString("permanent_address")+"','"+rs.getString("emp_id")+"','PA')");
				prst.execute();
				
				prst = conhc
						.prepareStatement("insert into EMPTRAN(EMPNO,EFFDATE,ACNO" +
								") values" +
								"("+empno+",'"+rs.getDate("joining_date")+"','"+rs.getString("acc_no")+"')");
				
				prst.execute();
				
				//*****************For LOOKUP DONE*********************
				/*String str = "";
				Statement st = conn.createStatement();
				str = "  select distinct(department) from Civil_ERP_Proj.dbo.employee_master where department <> '' order by department";
				ResultSet rs1 = st.executeQuery(str);
				while(rs1.next()){
					Statement st1 = conhc.createStatement();
					String max = "(select max(lkp_srno)+1 from lookup where lkp_code = 'DEPT')";
					str = "insert into lookup values('DEPT',"+max+",'"+rs1.getString(1)+"',0)";
					st1.executeUpdate(str);
				}*/
				//.********************END LOOKUP******************
				
			   //For basic
				/*    Wrk_Amt = rs.getInt("gross_salary") * rs.getInt("basic_per") / 100;
				    System.out.println("INSERT INTO PAYTRAN (TRNDT, EMPNO, TRNCD,INP_AMT, USRCODE, UPDDT, ARR_AMT, ADJ_AMT, NET_AMT, SRNO, CF_SW) values(" +
											   "Cast('01-Apr-2013' as Date),"+empno+",101,"+Wrk_Amt+",'Developer',Cast('01-Apr-2013' as Date),0,0,0,0,'')");
					st3.executeUpdate("INSERT INTO PAYTRAN (TRNDT, EMPNO, TRNCD,INP_AMT, USRCODE, UPDDT, ARR_AMT, ADJ_AMT, NET_AMT, SRNO, CF_SW) values(" +
											   "Cast('01-Apr-2013' as Date),"+empno+",101,"+Wrk_Amt+",'Developer',Cast('01-Apr-2013' as Date),0,0,0,0,'')");
					
			   //For da
					Wrk_Amt = rs.getInt("gross_salary") * rs.getInt("da_per") / 100;
					st3.executeUpdate("INSERT INTO PAYTRAN (TRNDT, EMPNO, TRNCD,INP_AMT, USRCODE, UPDDT, ARR_AMT, ADJ_AMT, NET_AMT, SRNO, CF_SW) values(" +
											   "Cast('01-Apr-2013' as Date),"+empno+",102,"+Wrk_Amt+",'Developer',Cast('01-Apr-2013' as Date),0,0,0,0,'')");
			   //For hra	
					Wrk_Amt = rs.getInt("gross_salary") * rs.getInt("hra_per") / 100;
					st3.executeUpdate("INSERT INTO PAYTRAN (TRNDT, EMPNO, TRNCD,INP_AMT, USRCODE, UPDDT, ARR_AMT, ADJ_AMT, NET_AMT, SRNO, CF_SW) values(" +
											   "Cast('01-Apr-2013' as Date),"+empno+",103,"+Wrk_Amt+",'Developer',Cast('01-Apr-2013' as Date),0,0,0,0,'')");
				//For medical allowance		
					Wrk_Amt = rs.getInt("gross_salary") * rs.getInt("medical") / 100;
					st3.executeUpdate("INSERT INTO PAYTRAN (TRNDT, EMPNO, TRNCD,INP_AMT, USRCODE, UPDDT, ARR_AMT, ADJ_AMT, NET_AMT, SRNO, CF_SW) values(" +
											   "Cast('01-Apr-2013' as Date),"+empno+",104,"+Wrk_Amt+",'Developer',Cast('01-Apr-2013' as Date),0,0,0,0,'')");
				//For conveyance allowance		
				Wrk_Amt = rs.getInt("gross_salary") * rs.getInt("conveyance") / 100;
				st3.executeUpdate("INSERT INTO PAYTRAN (TRNDT, EMPNO, TRNCD,INP_AMT, USRCODE, UPDDT, ARR_AMT, ADJ_AMT, NET_AMT, SRNO, CF_SW) values(" +
											   "Cast('01-Apr-2013' as Date),"+empno+",108,"+Wrk_Amt+",'Developer',Cast('01-Apr-2013' as Date),0,0,0,0,'')");
				
				//For education allowance		
				Wrk_Amt = rs.getInt("gross_salary") * rs.getInt("education") / 100;
				st3.executeUpdate("INSERT INTO PAYTRAN (TRNDT, EMPNO, TRNCD,INP_AMT, USRCODE, UPDDT, ARR_AMT, ADJ_AMT, NET_AMT, SRNO, CF_SW) values(" +
											   "Cast('01-Apr-2013' as Date),"+empno+",105,"+Wrk_Amt+",'Developer',Cast('01-Apr-2013' as Date),0,0,0,0,'')");
				

				//For PF allowance		
				Wrk_Amt = rs.getInt("gross_salary") * rs.getInt("pf") / 100;
				st3.executeUpdate("INSERT INTO PAYTRAN (TRNDT, EMPNO, TRNCD,INP_AMT, USRCODE, UPDDT, ARR_AMT, ADJ_AMT, NET_AMT, SRNO, CF_SW) values(" +
											   "Cast('01-Apr-2013' as Date),"+empno+",201,"+Wrk_Amt+",'Developer',Cast('01-Apr-2013' as Date),0,0,0,0,'')");
				*/	
				
			//==============================
			// Query for updating Qual table
				/*
				 
				insert into HCPAYROLL.dbo.QUAL(EMPNO,SRNO,DEGREE) 
				(select e1.emp_srno,0,l.LKP_SRNO from Civil_ERP_Proj.dbo.employee_master e1, HCPAYROLL.dbo.LOOKUP l
					where e1.qualification=l.LKP_DISC and l.LKP_CODE='ED' and e1.qualification is not null 
				  */
				
				
			}
			System.out.println("--- OUT ----");
			conn.close();		
		}catch(Exception e)
		{
			System.out.println("In Employee Handler getEmployeeInformation"+e);
			e.printStackTrace();
		}
		
		
	}
	
	
	public static String dateFormat(Date date)
	{
		String result="";
		if(date==null)
		{
			return "";
		}
		{
		SimpleDateFormat format=new SimpleDateFormat("dd-MMM-yyyy");
		//SimpleDateFormat fromFormat=new SimpleDateFormat("yyyy-MM-dd");
		result=format.format(date);
		
		return result;
		}
	}
	
}
