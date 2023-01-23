package payroll.DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import payroll.Core.ReportDAO;
import payroll.Model.ShiftBean;
import payroll.Model.extra_duty_paymentBean;

public class ShiftHandler 
{
	Connection conn;
	public void  insertValue(ShiftBean sb)
	{
		try
		{
			conn= ConnectionManager.getConnection();
			Statement st = conn.createStatement();
			int srno=0;
			ResultSet rs= st.executeQuery("select max(SRNO) from SHIFTMAST");
			while(rs.next())
			{
				srno=rs.getString(1)!=null?rs.getInt(1):0;
			}
			srno=srno+1;
			st.execute("insert into SHIFTMAST values("+srno+",'"+sb.getShift()+"','"+sb.getStartTime()+"','"+sb.getEndTime()+"','"+sb.getStatus()+"')");
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public ArrayList<ShiftBean> getshiftvalues()
	{
		ArrayList<ShiftBean> shiftlist = new ArrayList<ShiftBean>();
		ShiftBean sb ;
		try
		{
			conn = ConnectionManager.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = null;
			rs= st.executeQuery("select * from SHIFTMAST order by srno asc");
			while(rs.next())
			{
				sb = new ShiftBean();
				sb.setSrno(rs.getString(1)!=null?rs.getInt(1):0);
				sb.setShift(rs.getString(2)!=null?rs.getString(2):"");
				sb.setStartTime(rs.getString(3)!=null?rs.getString(3):"");
				sb.setEndTime(rs.getString(4)!=null?rs.getString(4):"");
				sb.setStatus(rs.getString(5)!=null?rs.getString(5):"");
				shiftlist.add(sb);
			}
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return shiftlist;
	}
	
	public boolean update(ShiftBean shbean) 
	{
		boolean flag=false;
		conn = ConnectionManager.getConnection();
		String shiftUpdate="UPDATE SHIFTMAST SET shiftcode='"+shbean.getShift()+"',starttime='"+shbean.getStartTime()+"',endtime='"+shbean.getEndTime()+"',status='"+shbean.getStatus()+"' where srno='"+shbean.getSrno()+"'";
		try
		{
			Statement st = conn.createStatement();
			st.executeUpdate(shiftUpdate);
			flag=true;
			conn.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean insertOvertime(ShiftBean shbean1)
	{
		boolean flag=false;
		Connection conn=ConnectionManager.getConnection();
		try
		{
			int srno=0;
			Statement st2=conn.createStatement();
			Statement st3=conn.createStatement();
			ResultSet rs= st2.executeQuery("select max(SRNO) from OVERTIME");
		    ResultSet rs1=st3.executeQuery("select * from overtime where srno="+shbean1.getSrno()+"");
			if(rs.next())
			{
				srno=rs.getString(1)!=null?rs.getInt(1):0;
			}
			srno=srno+1;
			if(!rs1.next())
			{
				String query="INSERT INTO OVERTIME VALUES('"+srno+"','"+shbean1.getEmptype()+"','"+shbean1.getGrade()+"','"+shbean1.getShiftcode()+"','"+shbean1.getRate()+"')";
				st2.executeUpdate(query);
				flag=true;
			}
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}
	
	public ArrayList<ShiftBean> getList() 
	{
		ArrayList<ShiftBean> list=new ArrayList<>();
		Connection conn=ConnectionManager.getConnection();
		String listQUERY="SELECT * FROM OVERTIME";
		try
		{
			Statement st=conn.createStatement();
			ResultSet rs=st.executeQuery(listQUERY);
			while(rs.next())
			{
				ShiftBean OVTbean = new ShiftBean();
				OVTbean.setSrno(rs.getString(1)!=null?rs.getInt(1):0);
				OVTbean.setEmptype(rs.getString(2)!=null?rs.getInt(2):0);
				OVTbean.setGrade(rs.getString(3)!=null?rs.getInt(3):0);
				OVTbean.setShiftcode(rs.getString(4)!=null?rs.getInt(4):0);
				OVTbean.setRate(rs.getString(5)!=null?rs.getInt(5):0);
				list.add(OVTbean);
			}
			conn.close();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean UpdateOvertime(ShiftBean shbean)
	{
       boolean flag=false;
       Connection conn=ConnectionManager.getConnection();
       String updateOverT="UPDATE OVERTIME SET emptype='"+shbean.getEmptype()+"',grade='"+shbean.getGrade()+"',shiftcode='"+shbean.getShiftcode()+"',rate='"+shbean.getRate()+"' WHERE srno='"+shbean.getSrno()+"'";
       try
       {
			Statement st=conn.createStatement();
			flag=true;
			st.executeUpdate(updateOverT);
			st.close();
			conn.close();
       }
       catch (SQLException e) 
       {
    	   e.printStackTrace();
       }
       return flag;
	}
	
	public ArrayList<ShiftBean> getCalmastList() 
	{
		ArrayList<ShiftBean> calList=new ArrayList<>();
		Connection conn=ConnectionManager.getConnection();
		String query="SELECT * FROM CALMAST";
		try
		{
			Statement st=conn.createStatement();
			ResultSet rslt=st.executeQuery(query);
			while(rslt.next())
			{
				ShiftBean shbean1=new ShiftBean();
				shbean1.setEmptype(rslt.getString(1)!=null?rslt.getInt(1):0);
				shbean1.setDay(rslt.getString(2)!=null?rslt.getString(2):"");
				shbean1.setDaytype(rslt.getString(3)!=null?rslt.getString(3):"");
				shbean1.setDaydate(rslt.getString(4)!=null?dateFormat(rslt.getDate(4)):"");
				shbean1.setHoliday(rslt.getString(5)!=null?rslt.getString(5):"");
				shbean1.setDesc(rslt.getString(6)!=null?rslt.getString(6):"");
				calList.add(shbean1);
			}
			st.close();
			conn.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return calList;
	}
	
	public boolean addCallmast(ShiftBean shbean2) 
	{
		boolean flag=false;
		int emptype=shbean2.getEmptype();
		Connection conn=ConnectionManager.getConnection();
		String query="INSERT INTO CALMAST VALUES('"+emptype+"','"+shbean2.getDay()+"','"+shbean2.getDaytype()+"',CAST('"+shbean2.getDaydate()+"' AS DATETIME),'"+shbean2.getHoliday()+"', '"+shbean2.getDesc()+"')";
		try
		{
			Statement st=conn.createStatement();
			st.executeUpdate(query);
			flag=true;
			conn.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return flag;
	}
	
	public static String dateFormat(Date dates)
	{
		String result="";
		if(dates==null)
		{
			result="";
			return result;
		}
		SimpleDateFormat format= new SimpleDateFormat("dd-MMM-yyyy");
		result=format.format(dates);
		return result;
	}
	
	public boolean updateCalmast(ShiftBean shbean3) 
	{
		boolean flag=false;
		Connection conn=ConnectionManager.getConnection();
		String updateovtime="UPDATE CALMAST SET DAY='"+shbean3.getDay()+"', DAYTYPE='"+shbean3.getDaytype()+"', DAYDATE='"+shbean3.getDaydate()+"', HOLIDAY='"+shbean3.getHoliday()+"',DISC='"+shbean3.getDesc()+"' " +
				" EMPTYPE='"+shbean3.getEmptype()+"' ";
		try 
		{
			Statement stmt=conn.createStatement();
			stmt.executeUpdate(updateovtime);
			flag=true;
			conn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return flag;
	}
	
	public ArrayList<ShiftBean> getTimesheet()
	{
		ArrayList<ShiftBean> list=new ArrayList<ShiftBean>();
		Connection conn=ConnectionManager.getConnection();
		String timesheet="SELECT * FROM TIMESHEET";
		ResultSet rslt=null;
		try
		{
			Statement stmt1=conn.createStatement();
			rslt=stmt1.executeQuery(timesheet);
			while(rslt.next())
			{
				ShiftBean shftbean=new ShiftBean();
				shftbean.setEMPNO(rslt.getString(1)!=null?Integer.parseInt(rslt.getString(1)):0);
				shftbean.setShift(rslt.getString(2)!=null?rslt.getString(2):"");
				shftbean.setDaydate(rslt.getString(3)!=null?dateFormat(rslt.getDate(3)):"");
				shftbean.setCheckin(rslt.getString(4)!=null?rslt.getString(4):"");
				shftbean.setCheckout(rslt.getString(5)!=null?rslt.getString(5):"");
				shftbean.setTotal(rslt.getString(6)!=null?rslt.getString(6):"");
				shftbean.setSrno(rslt.getString(7)!=null?Integer.parseInt(rslt.getString(7)):0);
				list.add(shftbean);
			}
			conn.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean addTimesheet(ShiftBean shfbean)
	{
		boolean flag=false;
		Connection conn=ConnectionManager.getConnection();
		String maxsrno="SELECT max(SRNO) FROM TIMESHEET ";
		int srno=1;
		try
		{
			Statement stmt2=conn.createStatement();
			ResultSet max=stmt2.executeQuery(maxsrno);
			if(max.next())
			{
				srno=max.getInt(1)+1;
			}
			String timesheet="INSERT INTO TIMESHEET VALUES('"+shfbean.getEMPNO()+"','"+shfbean.getShift()+"','"+shfbean.getDaydate()+"','"+shfbean.getCheckin()+"','"+shfbean.getCheckout()+"','"+shfbean.getTotal()+"','"+srno+"')";
			stmt2.executeUpdate(timesheet);
			flag=true;
			conn.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean UpdateTimesheet(ShiftBean shfbean2) 
	{
	     boolean flag=false;
	     Connection conn=ConnectionManager.getConnection();
	     String updatesheet="UPDATE TIMESHEET SET SHIFTCODE='"+shfbean2.getShift()+"',DAYDATE='"+shfbean2.getDaydate()+"',CHECKIN='"+shfbean2.getCheckin()+"',CHECKOUT='"+shfbean2.getCheckout()+"',TOTAL='"+shfbean2.getTotal()+"' WHERE EMPNO='"+shfbean2.getEMPNO()+"'AND SRNO='"+shfbean2.getSrno()+"'";
	     try
	     {
	    	 Statement stmt=conn.createStatement();
	    	 stmt.executeUpdate(updatesheet);
	    	 flag=true;
	    	 conn.close();
	     }
	     catch (SQLException e) 
	     {
	    	 e.printStackTrace();
	     }
	     return flag;
	}
	
	
// Get Extra Payment For Employee..	
	
	public  ArrayList<extra_duty_paymentBean> getExtraPaymentList(String date,String empno,String totalDays,String date2,String date3)
	{
		
		ArrayList<extra_duty_paymentBean> extraPaymentlist=new ArrayList<extra_duty_paymentBean>();
		Connection con=ConnectionManager.getConnection();
		Statement st=null;
		Statement st1=null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3=null;
		Statement st2= null;
		String PaytranDate="";
		date2="1-"+date2;
		date2=ReportDAO.EOM(date2);
		date3="1-"+date3;
		date3=ReportDAO.EOM(date3);
		float monthday=1.0f;
		System.out.println("i am in getExtraPaymentList handler...for jsp date..."+date2);
		double total_inp_amt = 0,DayAmount,empTotalAmt ;
		try
     	{
			
			
			st1 = con.createStatement();
			
			rs1= st1.executeQuery("select max( TRNDT) TRNDT from paytran WHERE  trncd = 101 and EMPNO ="+empno);
			if(rs1.next())
			{
				PaytranDate = rs1.getString("TRNDT");
				
			}
			else
			{
				rs1= st1.executeQuery("select max( TRNDT) TRNDT from paytran_stage WHERE  trncd = 101 and EMPNO ="+empno);
				if(rs1.next())
				{
					PaytranDate = rs1.getString("TRNDT");
					
				}
			}
			
			st2 = con.createStatement();
			
			/*if(date.equalsIgnoreCase(PaytranDate)||date.contentEquals(PaytranDate))
			{*/
				System.out.println("PaytranDate .................................."+PaytranDate);
				rs2= st2.executeQuery("SELECT amt.EMPNO,amt.EMPCODE,amt.NAME,amt.TRNDT,amt.[101] as BASIC,"+
									"amt.[102] as DA,amt.[138] as VDA "+
									"FROM   (SELECT e.empno,"+ 
									"e.empcode, "+
									"Rtrim(e.fname) + ' ' + Rtrim(e.mname) + ' ' "+
									"+ Rtrim(e.lname) AS NAME, "+
									"sum(p.INP_AMT) amt, p.trncd ,p.trndt FROM   empmast e, PAYTRAN_stage p "+	
									"WHERE  e.empno = p.empno AND e.status = 'A' "+
									"AND p.trncd IN ( 101, 102,138 ) and TRNDT='"+date3+"' "+
									"GROUP  BY e.empno,"+
									"e.empcode, e.fname, e.mname, e.lname, p.trncd,"+
									"p.TRNDT  )AS em "+
									"PIVOT( Max([amt]) "+
									"FOR trncd IN([101], "+
									"[102], [138])) AS amt "+
                                    "where EMPNO ="+empno);
				System.out.println("SELECT amt.EMPNO,amt.EMPCODE,amt.NAME,amt.TRNDT,amt.[101] as BASIC,"+
						"amt.[102] as DA,amt.[138] as VDA "+
						"FROM   (SELECT e.empno,"+ 
						"e.empcode, "+
						"Rtrim(e.fname) + ' ' + Rtrim(e.mname) + ' ' "+
						"+ Rtrim(e.lname) AS NAME, "+
						"sum(p.INP_AMT) amt, p.trncd ,p.trndt FROM   empmast e, PAYTRAN_stage p "+	
						"WHERE  e.empno = p.empno AND e.status = 'A' "+
						"AND p.trncd IN ( 101, 102,138 ) and TRNDT='"+date3+"' "+
						"GROUP  BY e.empno,"+
						"e.empcode, e.fname, e.mname, e.lname, p.trncd,"+
						"p.TRNDT  )AS em "+
						"PIVOT( Max([amt]) "+
						"FOR trncd IN([101], "+
						"[102], [138])) AS amt "+
                        "where EMPNO ="+empno);
				System.out.println("akk"+date2);
				System.out.println("akk"+Integer.parseInt(PaytranDate.substring(8,10)));
				monthday=Integer.parseInt(date2.substring(0,2)); 

			
			System.out.println("akk"+monthday);
			rs2.next();
			
			
			
			//usefull 
			/*rs3=st1.executeQuery("SELECT * FROM Extra_Duty_Payment WHERE  "+
											" EMPNO="+empno+"  AND STATUS='C' ");
			if(rs3.next())
			{*/
											//no use
											//System.out.println("NO CALCULATE AGAIN AND NO SAVE>>>");
											/*extra_duty_paymentBean bean=new extra_duty_paymentBean();
											bean.setBasic("0");
											bean.setDA("0");
											bean.setVDA("0");
											bean.setTotal(0);
											bean.setTRNDT(date);
											bean.setSTATUS("C".toString());
											
											extraPaymentlist.add(bean);*/
				
				//usefull if code
				/*total_inp_amt = (rs2.getDouble("BASIC")) + (rs2.getDouble("DA")) + (rs2.getDouble("VDA"));
				
				
				
				System.out.println("total_inp_amt "+total_inp_amt);
				extra_duty_paymentBean bean=new extra_duty_paymentBean();
				bean.setBasic(rs2.getString("BASIC"));
				bean.setDA(rs2.getString("DA"));
				bean.setVDA(rs2.getString("VDA"));
				
				DayAmount = (total_inp_amt/(int)monthday);
				empTotalAmt = (DayAmount)*(Integer.parseInt(totalDays));
				bean.setTotal(((float)empTotalAmt+(float)rs3.getFloat("CALCULATED_AMOUNT")));
				bean.setTRNDT(rs3.getString("TRNDT").concat(","+date));
				bean.setSTATUS("C".toString());
				extraPaymentlist.add(bean);
			}
			else
			{*/
				total_inp_amt = (rs2.getDouble("BASIC")) + (rs2.getDouble("DA")) + (rs2.getDouble("VDA"));
				
				
				
				System.out.println("total_inp_amt "+total_inp_amt);
				extra_duty_paymentBean bean=new extra_duty_paymentBean();
				bean.setBasic(rs2.getString("BASIC"));
				bean.setDA(rs2.getString("DA"));
				bean.setVDA(rs2.getString("VDA"));
				
				DayAmount = (total_inp_amt/(int)monthday);
				empTotalAmt = (DayAmount)*(Integer.parseInt(totalDays));
				bean.setTotal((float)empTotalAmt);
				bean.setTRNDT(date);
				bean.setSTATUS("C".toString());
				bean.setMonth(date2);
				extraPaymentlist.add(bean);
				
			//	}
				/*else
				{
					//System.out.println("PaytranStageDate ..................................");
					rs2= st2.executeQuery("SELECT amt.EMPNO,amt.EMPCODE,amt.NAME,amt.TRNDT,amt.[101] as BASIC,"+
							"amt.[102] as DA,amt.[138] as VDA "+
							"FROM   (SELECT e.empno,"+ 
							"e.empcode, "+
							"Rtrim(e.fname) + ' ' + Rtrim(e.mname) + ' ' "+
							"+ Rtrim(e.lname) AS NAME, "+
							"sum(p.INP_AMT) amt, p.trncd ,p.trndt FROM   empmast e, PAYTRAN_STAGE p "+	
							"WHERE  e.empno = p.empno AND e.status = 'A' "+
							"AND p.trncd IN ( 101, 102,138 )  and TRNDT='"+date+"' "+
							"GROUP  BY e.empno,"+
							"e.empcode, e.fname, e.mname, e.lname, p.trncd,"+
							"p.TRNDT  )AS em "+
							"PIVOT( Max([amt]) "+
							"FOR trncd IN([101], "+
							"[102], [138])) AS amt "+
	                        "where EMPNO ="+empno);
					
					monthday=Calculate.getDays(date);
				
				}*/
					/*if(rs2.next())	
					{
						total_inp_amt = (rs2.getDouble("BASIC")) + (rs2.getDouble("DA")) + (rs2.getDouble("VDA"));
					
						
						
						System.out.println("total_inp_amt "+total_inp_amt);
						extra_duty_paymentBean bean=new extra_duty_paymentBean();
						bean.setBasic(rs2.getString("BASIC"));
						bean.setDA(rs2.getString("DA"));
						bean.setVDA(rs2.getString("VDA"));
						
						DayAmount = (total_inp_amt/(int)monthday);
						empTotalAmt = (DayAmount)*(Integer.parseInt(totalDays));
						bean.setTotal((float)empTotalAmt);
						bean.setTRNDT(rs2.getString("TRNDT"));
						bean.setSTATUS("C".toString());
						extraPaymentlist.add(bean);
					}
					else
					{
						
						extra_duty_paymentBean bean=new extra_duty_paymentBean();
						bean.setBasic("0");
						bean.setDA("0");
						bean.setVDA("0");
						bean.setTotal(0);
						bean.setTRNDT(date);
						bean.setSTATUS("C".toString());
						
						extraPaymentlist.add(bean);
					}
			*/		
					 st2.close();
					rs2.close();
			
			}
			
     	catch (Exception e) 
     	{
			e.printStackTrace();
		}
		return extraPaymentlist;
		
	}	
	
	
	// Insert extra payment into Extra_Duty_Payment..
	
	public String  save_Extra_Payment(String fordate,String empno,String basic,String da,String vda,String days,String cal_amount,String month)  throws ParseException
	{
		 //System.out.println("In save_Extra_Payment");
		 String flags = "";
	   try
	   {
	    	 Connection conn=ConnectionManager.getConnection();
	    	 Statement stmt=conn.createStatement();
	    	
	    	 
	    	 String sql1="IF EXISTS(SELECT * FROM Extra_Duty_Payment WHERE "+
	    			 							" EMPNO="+empno+" AND STATUS='C' ) "+
	    			 							"UPDATE  Extra_Duty_Payment SET TRNDT='"+fordate+"',EMPNO="+empno+", "+
	    			 							"BASIC="+basic+",DA="+da+",VDA="+vda+",TOTAL_DAYS='"+days+"', "+
	    			 							"CALCULATED_AMOUNT='"+cal_amount+"',STATUS='C',MONTH='"+month+"' WHERE  "+
	    			 							" EMPNO="+empno+" AND STATUS='C' " +
	    			 			"ELSE INSERT INTO Extra_Duty_Payment(TRNDT,EMPNO, BASIC ,DA ,VDA ,TOTAL_DAYS ,CALCULATED_AMOUNT,STATUS,CREATED_DATE,MONTH)" +
	    			 			"VALUES('"+fordate+"','"+empno+"', '"+basic+"', '"+da+"','"+vda+"','"+days+"','"+cal_amount+"','C',(select convert(date,GETDATE()) as date),'"+month+"' ) ";
				
				//System.out.println(sql1);
				//System.out.println("i am in Insert into ");
				stmt.execute(sql1);
				flags="save";
			 conn.close();
	    } 
	   	catch (SQLException e) 
	   	{
		   e.printStackTrace();
		}
	return flags;
	}
	
	
	public ArrayList<extra_duty_paymentBean>  getAllExtraPayment(String empno)  throws ParseException
	{
		ArrayList<extra_duty_paymentBean> result = new ArrayList<extra_duty_paymentBean>();
		 System.out.println("In getAllExtraPayment");
		 String flags = "";
		 String sql1="";
	   try
	   {
	    	 Connection conn=ConnectionManager.getConnection();
	    	 Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	    	 ResultSet rs=null;
	    	 if(empno.equalsIgnoreCase("ALL"))
	    	 {
	    		 sql1=("SELECT e1.EMPCODE,Rtrim(e1.fname) + ' ' + Rtrim(e1.mname) + ' ' "+
						"+ Rtrim(e1.lname) AS NAME,e.* FROM EMPMAST e1,Extra_Duty_Payment e WHERE  e1.empno=e.empno AND e.STATUS='C' ORDER BY e1.EMPCODE ");
	    	 }
	    	 else
	    	 {
	    		// String empno1[]=empno.split(":");
	    		 System.out.println("empno...........splitedddd"+empno);
	    		 sql1=("SELECT e1.EMPCODE,Rtrim(e1.fname) + ' ' + Rtrim(e1.mname) + ' ' "+
						"+ Rtrim(e1.lname) AS NAME,e.* FROM EMPMAST e1,Extra_Duty_Payment e WHERE  e1.empno=e.empno  AND e.STATUS='C'  "+ 
	    				 	"and e.empno="+empno+" ");
		    	 	 
	    	 }
				//System.out.println("selecting record for.........................."+sql1);
				
				rs=stmt.executeQuery(sql1);
				if(rs.next())
				{
					
					rs.previous();
					while(rs.next())
					{
						extra_duty_paymentBean eb= new extra_duty_paymentBean();
						eb.setEMPNO(Integer.parseInt(rs.getString("EMPNO")));
						eb.setTRNDT(rs.getString("TRNDT"));
						eb.setNAME(rs.getString("NAME"));
						eb.setEMPCODE(rs.getString("EMPCODE"));
						eb.setBasic(rs.getString("BASIC"));
						eb.setDA(rs.getString("DA"));
						eb.setVDA(rs.getString("VDA"));
						eb.setTOTALDAYS(rs.getString("TOTAL_DAYS"));
						eb.setTotal(rs.getDouble("CALCULATED_AMOUNT"));
						eb.setSTATUS(rs.getString("STATUS"));
						eb.setMonth(rs.getString("MONTH"));
						result.add(eb);
					}
				}
				else{
					
				}
				
			 conn.close();
	    } 
	   	catch (SQLException e) 
	   	{
		   e.printStackTrace();
		}
	return result;
	}
	
	
	public String  Post_Extra_Payment (String checkempno1,ArrayList<extra_duty_paymentBean> emplist,String uid)  throws ParseException
	{
		ArrayList<extra_duty_paymentBean> postextrapayment = new ArrayList<extra_duty_paymentBean>();
		// System.out.println("In Post_Extra_Payment");
		 String flags = "";
		 String sql1="";
		 int cnt=0;
		
	   try
	   {
	    	 Connection conn=ConnectionManager.getConnection();
	    	 Statement stmt=conn.createStatement();
	    	 ResultSet rs=null;
	    	 String date="";
	    	// System.out.println("list of empno...."+checkempno1+"...");
	    		 for(extra_duty_paymentBean eb1:emplist)
		    	 {
		    		 
	    		
	    			 
		    			if(checkempno1.contains((String.valueOf(eb1.getEMPNO())))) 
		    			{
		    				/* rs=stmt.executeQuery("SELECT MAX(TRNDT) TRNDT FROM PAYTRAN WHERE EMPNO="+eb1.getEMPNO()+" AND TRNCD=101 and  TRNDT='"+eb1.getMonth()+"' ");
		    				 //System.out.println("partical list contain..."+String.valueOf(eb1.getEMPNO()));
		    				 //System.out.println("empno..."+eb1.getEMPNO()+"...post.....dt..."+eb1.getTRNDT());
		    				
		    				 if(rs.next()) //for curent month
		    				 {
		    					 ResultSet rs1=stmt.executeQuery("SELECT * FROM PAYTRAN WHERE TRNDT='"+eb1.getMonth()+"' "+
		    			 					"AND EMPNO="+eb1.getEMPNO()+" AND TRNCD=111");
		    				 
		    				     if(rs1.next())
		    				       {
		    					 String oldtotal=rs1.getString("INP_AMT");
		    				
		    					 String finaltotal=String.valueOf(((Double.parseDouble(oldtotal))+((eb1.getTotal()))));
		    				     stmt.execute("UPDATE PAYTRAN SET INP_AMT="+finaltotal+",CAL_AMT="+finaltotal+",NET_AMT="+finaltotal+",USRCODE= "+uid+",UPDDT='"+ReportDAO.getServerDate()+"' " +
		    					 					"WHERE TRNDT='"+eb1.getMonth()+"' AND EMPNO="+eb1.getEMPNO()+" AND TRNCD=111");
		    					
		    				      }
		    				   else
		    				      {
		    					 stmt.execute( "INSERT INTO PAYTRAN (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,"+
		    					 			"NET_AMT,CF_SW,USRCODE,UPDDT,STATUS) VALUES ("+
		    					 			" '"+eb1.getMonth()+"',"+eb1.getEMPNO()+",111,0,"+eb1.getTotal()+","+eb1.getTotal()+", "+
		    					 			" "+eb1.getTotal()+",'0',"+uid+",'"+ReportDAO.getServerDate()+"','N' )");
		    			          }
		    				   }
		    				 else // for release month
		    				 {*/
		    				 
		    				 ResultSet rs1=stmt.executeQuery("SELECT * FROM PAYTRAN_stage WHERE TRNDT='"+eb1.getMonth()+"' "+
		    			 					"AND EMPNO="+eb1.getEMPNO()+" AND TRNCD=111");
		    				 	
		    				 	if(rs1.next())
		    				 		{
		    					 String oldtotal=rs1.getString("INP_AMT");
		    					 //System.out.println("old total..."+oldtotal);
		    					// System.out.println("new total..."+eb1.getTotal());
		    					 
		    					 String finaltotal=String.valueOf(((Double.parseDouble(oldtotal))+((eb1.getTotal()))));
		    					 
		    					// System.out.println("final total..."+finaltotal);
		    					 
		    					 stmt.execute("UPDATE PAYTRAN_STAGE SET INP_AMT="+finaltotal+",CAL_AMT="+finaltotal+",NET_AMT="+finaltotal+",USRCODE= "+uid+",UPDDT='"+ReportDAO.getServerDate()+"' " +
		    					 					"WHERE TRNDT='"+eb1.getMonth()+"' AND EMPNO="+eb1.getEMPNO()+" AND TRNCD=111");
		    					 stmt.execute("UPDATE YTDTRAN SET INP_AMT="+finaltotal+",CAL_AMT="+finaltotal+",NET_AMT="+finaltotal+",USRCODE= "+uid+",UPDDT='"+ReportDAO.getServerDate()+"' " +
 					 					"WHERE TRNDT='"+eb1.getMonth()+"' AND EMPNO="+eb1.getEMPNO()+" AND TRNCD=111");
		    					 
		    					// System.out.println("paytran updated for rmpno...111"+eb1.getEMPNO());
		    					 
		    					 
		    				 		}
		    				      else
		    				       {
		    					 stmt.execute( "INSERT INTO PAYTRAN_STAGE (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,"+
		    					 			"NET_AMT,CF_SW,USRCODE,UPDDT,STATUS) VALUES ("+
		    					 			" '"+eb1.getMonth()+"',"+eb1.getEMPNO()+",111,0,"+eb1.getTotal()+","+eb1.getTotal()+", "+
		    					 			" "+eb1.getTotal()+",'0',"+uid+",'"+ReportDAO.getServerDate()+"','F' )");
		    					 stmt.execute( "INSERT INTO YTDTRAN (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,"+
		    					 			"NET_AMT,CF_SW,USRCODE,UPDDT,STATUS) VALUES ("+
		    					 			" '"+eb1.getMonth()+"',"+eb1.getEMPNO()+",111,0,"+eb1.getTotal()+","+eb1.getTotal()+", "+
		    					 			" "+eb1.getTotal()+",'0',"+uid+",'"+ReportDAO.getServerDate()+"','F' )");
		    				       
		    				 }
		    				 
		    				/* stmt.execute("IF EXISTS(SELECT * FROM PAYTRAN WHERE TRNDT='"+eb1.getTRNDT()+"' "+
		    			 					"AND EMPNO="+eb1.getEMPNO()+" AND TRNCD=111) " +
		    			 					"DELETE FROM PAYTRAN WHERE TRNDT='"+eb1.getTRNDT()+"' "+
		    			 					"AND EMPNO="+eb1.getEMPNO()+" AND TRNCD=111 " +
		    			 					
		    			 					"INSERT INTO PAYTRAN (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,"+
		    					 			"NET_AMT,CF_SW,USRCODE,UPDDT,STATUS) VALUES ("+
		    					 			" '"+eb1.getTRNDT()+"',"+eb1.getEMPNO()+",111,0,"+eb1.getTotal()+","+eb1.getTotal()+", "+
		    					 			" "+eb1.getTotal()+",'0',"+uid+",'"+ReportDAO.getServerDate()+"','P' )");*/
		    			 
		    			 
		    				 stmt.execute("UPDATE Extra_Duty_Payment SET STATUS='P' WHERE EMPNO="+eb1.getEMPNO()+" "+
		    					 			"AND TRNDT='"+eb1.getTRNDT()+"'  AND STATUS='C' ");
		    			 
		    			 	cnt++;
		    			}
		    	 
	    		 
	    	 }
	    	 
	    	 if(cnt>0)
	    	 {
	    		 
	    		 //System.out.println("in flag..........records updated to paytran for 111..."+cnt);
	    		 flags="POST";
	    	 }
	    	 else
	    	 {
	    		 flags="FAIL";
	    	 }
				
			 conn.close();
	    } 
	   	catch (SQLException e) 
	   	{
		   e.printStackTrace();
		}
	return flags;
	}
	
	public  ArrayList<extra_duty_paymentBean> LWPAMOUNT(String date3,String empno,String totalDays,String action_value)
	{
		
		ArrayList<extra_duty_paymentBean> extraPaymentlist=new ArrayList<extra_duty_paymentBean>();
		Connection con=ConnectionManager.getConnection();
		Statement st=null;
		Statement st1=null;
		ResultSet rs1=null;
		ResultSet rs2=null;
		ResultSet rs3=null;
		Statement st2=null;
		String query="";
		String PaytranDate="";
	/*date2="1-"+date2;
		date2=ReportDAO.EOM(date2);*/
		date3="1-"+date3;
		date3=ReportDAO.EOM(date3);
		float monthday=1.0f;
		//System.out.println("i am in getExtraPaymentList handler...for jsp date..."+date2);
		double total_inp_amt = 0,DayAmount,empTotalAmt,pf_amount  ;
		float basic,da,vda ;
		try
     	{
			
			
			st1 = con.createStatement();
			
			rs1= st1.executeQuery("select max( TRNDT) TRNDT from paytran WHERE  trncd = 101 and EMPNO ="+empno);
			if(rs1.next())
			{
				PaytranDate = rs1.getString("TRNDT");
				
			}
		/*	else
			{
				rs1= st1.executeQuery("select max( TRNDT) TRNDT from paytran_stage WHERE  trncd = 101 and EMPNO ="+empno);
				if(rs1.next())
				{
					PaytranDate = rs1.getString("TRNDT");
					
				}
			}
			*/
			st2 = con.createStatement();
			
				System.out.println("PaytranDate .................................."+PaytranDate);
				
				if(Integer.parseInt(totalDays)<5)
				{   
					System.out.println("akk if "+ Integer.parseInt(totalDays));
					query="SELECT amt.EMPNO,amt.EMPCODE,amt.NAME,amt.TRNDT,amt.[101] as BASIC,"+
							"amt.[102] as DA,amt.[138] as VDA, 0 AS  HRA,0 as MEDALLOW,0  as EDUALLOW ,0 as CONVEYANCE,0 as SPECIAL  "+
							"FROM   (SELECT e.empno,"+ 
							"e.empcode, "+
							"Rtrim(e.fname) + ' ' + Rtrim(e.mname) + ' ' "+
							"+ Rtrim(e.lname) AS NAME, "+
							"sum(p.INP_AMT) amt, p.trncd ,p.trndt FROM   empmast e, PAYTRAN_stage p "+	
							"WHERE  e.empno = p.empno AND e.status = 'A' "+
							"AND p.trncd IN ( 101, 102,138 ) and TRNDT='"+date3+"' "+
							"GROUP  BY e.empno,"+
							"e.empcode, e.fname, e.mname, e.lname, p.trncd,"+
							"p.TRNDT  )AS em "+
							"PIVOT( Max([amt]) "+
							"FOR trncd IN([101], "+
							"[102], [138])) AS amt "+
                            "where EMPNO ="+empno;
				}else
				{
					System.out.println("akk else");
				       query="SELECT amt.EMPNO,amt.EMPCODE,amt.NAME,amt.TRNDT,amt.[101] as BASIC,"+
									"amt.[102] as DA,amt.[138] as VDA, amt.[103]  AS  HRA,amt.[104] as MEDALLOW,amt.[105] as EDUALLOW ,amt.[108] as CONVEYANCE,amt.[129]as SPECIAL "+
									"FROM   (SELECT e.empno,"+ 
									"e.empcode, "+
									"Rtrim(e.fname) + ' ' + Rtrim(e.mname) + ' ' "+
									"+ Rtrim(e.lname) AS NAME, "+
									"sum(p.INP_AMT) amt, p.trncd ,p.trndt FROM   empmast e, PAYTRAN_stage p "+	
									"WHERE  e.empno = p.empno AND e.status = 'A' "+
									"AND p.trncd IN ( 101, 102,138,103,104,105,108,129 ) and TRNDT='"+date3+"' "+
									"GROUP  BY e.empno,"+
									"e.empcode, e.fname, e.mname, e.lname, p.trncd,"+
									"p.TRNDT  )AS em "+
									"PIVOT( Max([amt]) "+
									"FOR trncd IN([101], "+
									"[102], [138],[103],[104],[105],[108],[129])) AS amt "+
                                    "where EMPNO ="+empno;
				}
				rs2= st2.executeQuery(query);
				System.out.println("akshay 1"+query);
				monthday=Integer.parseInt(date3.substring(0,2));

			
			System.out.println("akk"+monthday);
			rs2.next();
			
			
 
	   

				total_inp_amt = (rs2.getDouble("BASIC")) + (rs2.getDouble("DA")) + (rs2.getDouble("VDA"))+(rs2.getDouble("HRA"))+(rs2.getDouble("MEDALLOW"))+(rs2.getDouble("EDUALLOW"))+(rs2.getDouble("CONVEYANCE"))+(rs2.getDouble("SPECIAL"));
				
				
				
				System.out.println("total_inp_amt 0"+total_inp_amt);
				extra_duty_paymentBean bean=new extra_duty_paymentBean();
			/*	bean.setBasic(rs2.getString("BASIC"));
				bean.setDA(rs2.getString("DA"));
				bean.setVDA(rs2.getString("VDA"));*/
				basic=(Math.round(((rs2.getFloat("BASIC"))/((int)monthday))*(Integer.parseInt(totalDays))));
				da=(Math.round(((rs2.getFloat("DA"))/((int)monthday))*(Integer.parseInt(totalDays))));
				vda=(Math.round(((rs2.getFloat("VDA"))/((int)monthday))*(Integer.parseInt(totalDays))));
				bean.setBasic_lwp(basic);
				bean.setDA_lwp(da);
				bean.setVDA_lwp(vda); 
				//HRA  MEDALLOW EDUALLOW   CONVEYANCE SPECIAL
				bean.setHRA_lwp(Math.round((((rs2.getFloat("HRA"))/((int)monthday))*(Integer.parseInt(totalDays)))));
				bean.setMEDALLOW_lwp(Math.round((((rs2.getFloat("MEDALLOW"))/((int)monthday))*(Integer.parseInt(totalDays)))));
				bean.setEDUALLOW_lwp(Math.round((((rs2.getFloat("EDUALLOW"))/((int)monthday))*(Integer.parseInt(totalDays)))));
				bean.setCONVEYANCE_lwp(Math.round((((rs2.getFloat("CONVEYANCE"))/((int)monthday))*(Integer.parseInt(totalDays)))));
				bean.setSPECIAL_lwp(Math.round((((rs2.getFloat("SPECIAL"))/((int)monthday))*(Integer.parseInt(totalDays)))));
				DayAmount = (total_inp_amt/(int)monthday);
				empTotalAmt = (DayAmount)*(Integer.parseInt(totalDays));
				pf_amount=((empTotalAmt*12)/100);
				bean.setTotal(Math.round((float)empTotalAmt));
				bean.setPf_amount((int)pf_amount);
				/*bean.setTRNDT(date);
				bean.setSTATUS("C".toString());*/
				bean.setMonth(date3);
				extraPaymentlist.add(bean);
				System.out.println("total_inp_amt 1"+rs2.getDouble("HRA"));
				System.out.println("total_inp_amt 2"+rs2.getDouble("BASIC"));
				System.out.println("total_inp_amt 3"+bean.getHRA_lwp());
				System.out.println("total_inp_amt 4"+bean.getBasic_lwp());
				
					 st2.close();
					rs2.close();
					
			}
			
     	catch (Exception e) 
     	{
			e.printStackTrace();
		}
		return extraPaymentlist;
		
	}	
	
	public String  save_lwpamount(String empno,String basic,String da,String vda,String days,String cal_amount,String month,String pfcalamt,String trntype,String reason,String HRA ,String MEDALLOW,String EDUALLOW ,String CONVEYANCE,String SPECIAL)  throws ParseException
	{
		 System.out.println("In save_lwp"+pfcalamt+""+cal_amount);
		 String flags = "";
	   try
	   {
	    	Connection conn=ConnectionManager.getConnection();
	    	Statement stmt=conn.createStatement();
	    	ResultSet rs1 =null;
	    	int srno=0;
	    	int applno=0;
	    	rs1= stmt.executeQuery("select isnull(max(srno),0) srno from lwp_Amt_Payment where EMPNO="+empno);
			if(rs1.next())
			{
				 srno= rs1.getInt("srno");
				
			}               
			srno=srno+1;																	//HRA  MEDALLOW EDUALLOW   CONVEYANCE SPECIAL
	    	 String sql1="INSERT INTO lwp_Amt_Payment  (SRNO,TRNTYPE,EMPNO, BASIC ,DA ,VDA,HRA ,MEDALLOW,EDUALLOW,CONVEYANCE,SPECIAL,TOTAL_DAYS ,total_amount,PF_calamount,STATUS,CREATED_DATE,BASICMONTH,reason)" +
	    			 			"VALUES('"+srno+"','"+trntype+"','"+empno+"', '"+basic+"', '"+da+"','"+vda+"','"+HRA+"','"+MEDALLOW+"','"+EDUALLOW+"','"+CONVEYANCE+"','"+SPECIAL+"','"+days+"','"+cal_amount+"','"+pfcalamt+"','C',(select convert(date,GETDATE()) as date),'"+month+"','"+reason+"' ) ";
	    	
	    	 
	    	 /* "IF EXISTS(SELECT * FROM Extra_Duty_Payment WHERE "+
				" EMPNO="+empno+" AND STATUS='C' ) "+
				"UPDATE  Extra_Duty_Payment SET TRNDT='"+fordate+"',EMPNO="+empno+", "+
				"BASIC="+basic+",DA="+da+",VDA="+vda+",TOTAL_DAYS='"+days+"', "+
				"CALCULATED_AMOUNT='"+cal_amount+"',STATUS='C',MONTH='"+month+"' WHERE  "+
				" EMPNO="+empno+" AND STATUS='C' " +
"ELSE INSERT INTO lwp_Amt_Payment  (TRNTYPE,EMPNO, BASIC ,DA ,VDA ,TOTAL_DAYS ,total_amount,PF_calamount,STATUS,CREATED_DATE,BASICMONTH)" +
"VALUES('"+fordate+"','"+empno+"', '"+basic+"', '"+da+"','"+vda+"','"+days+"','"+cal_amount+"','C',(select convert(date,GETDATE()) as date),'"+month+"' ) ";
*/
				//System.out.println(sql1);
				//System.out.println("i am in Insert into ");
				stmt.execute(sql1);
				if(trntype.equalsIgnoreCase("credit"))
				{
				stmt.execute("UPDATE PAYTRAN SET ADJ_AMT ='"+basic+"' WHERE EMPNO='"+empno+"' AND TRNCD=101");
				stmt.execute("UPDATE PAYTRAN SET ADJ_AMT = '"+da+"' WHERE EMPNO='"+empno+"' AND TRNCD=102");
				stmt.execute("UPDATE PAYTRAN SET ADJ_AMT ='"+vda+"' WHERE EMPNO='"+empno+"' AND TRNCD=138");
				stmt.execute("UPDATE PAYTRAN SET ADJ_AMT ='"+pfcalamt+"' WHERE EMPNO='"+empno+"' AND TRNCD=201");
				}
				else if(trntype.equalsIgnoreCase("debit"))
				{ System.out.println("UPDATE PAYTRAN SET ADJ_AMT ='-"+basic+"' WHERE  EMPNO='"+empno+"' AND TRNCD=101");
				System.out.println("UPDATE PAYTRAN SET ADJ_AMT =-'"+basic+"' WHERE  EMPNO='"+empno+"' AND TRNCD=101");
				if(Integer.parseInt(days)<5)
				{
					stmt.execute("UPDATE PAYTRAN SET ADJ_AMT ='-"+basic+"' WHERE EMPNO='"+empno+"' AND TRNCD=101");
					stmt.execute("UPDATE PAYTRAN SET ADJ_AMT ='-"+da+"' WHERE EMPNO='"+empno+"' AND TRNCD=102");
					stmt.execute("UPDATE PAYTRAN SET ADJ_AMT ='-"+vda+"' WHERE EMPNO='"+empno+"' AND TRNCD=138");
					stmt.execute("UPDATE PAYTRAN SET ADJ_AMT ='-"+pfcalamt+"' WHERE EMPNO='"+empno+"' AND TRNCD=201");
					
				}
				else
				{   System.out.println("akshay handler else "+days);
					stmt.execute("UPDATE PAYTRAN SET ADJ_AMT ='-"+basic+"' WHERE EMPNO='"+empno+"' AND TRNCD=101");
					stmt.execute("UPDATE PAYTRAN SET ADJ_AMT ='-"+da+"' WHERE EMPNO='"+empno+"' AND TRNCD=102");
					stmt.execute("UPDATE PAYTRAN SET ADJ_AMT ='-"+vda+"' WHERE EMPNO='"+empno+"' AND TRNCD=138");
					stmt.execute("UPDATE PAYTRAN SET ADJ_AMT ='-"+pfcalamt+"' WHERE EMPNO='"+empno+"' AND TRNCD=201");
					//HRA  MEDALLOW EDUALLOW   CONVEYANCE SPECIAL//,[103],[104],[105],[108],[129]
					stmt.execute("UPDATE PAYTRAN SET ADJ_AMT ='-"+HRA+"' WHERE EMPNO='"+empno+"' AND TRNCD=103");
					stmt.execute("UPDATE PAYTRAN SET ADJ_AMT ='-"+MEDALLOW+"' WHERE EMPNO='"+empno+"' AND TRNCD=104");
					stmt.execute("UPDATE PAYTRAN SET ADJ_AMT ='-"+EDUALLOW+"' WHERE EMPNO='"+empno+"' AND TRNCD=105");
					stmt.execute("UPDATE PAYTRAN SET ADJ_AMT ='-"+CONVEYANCE+"' WHERE EMPNO='"+empno+"' AND TRNCD=108");
					stmt.execute("UPDATE PAYTRAN SET ADJ_AMT ='-"+SPECIAL+"' WHERE EMPNO='"+empno+"' AND TRNCD=129");
					
				}
			
				ResultSet rs2 = null;
				rs2 = stmt.executeQuery("SELECT MAX(APPLNO)+1 FROM LEAVETRAN");
				 applno = 0;
				if(rs2.next()){
					applno = rs2.getInt(1);
				}
				
				String insertquery="INSERT INTO LEAVETRAN (EMPNO,LEAVECD,TRNDATE,TRNTYPE,APPLNO,FRMDT,TODT,LEAVEPURP," +
						"LREASON,LTELNO,DAYS,STATUS,APPLDT)" +
						" values('"+empno+"',7,(select convert(date,GETDATE()) as date),'D','"+applno+"','"+month+"','"+month+"',84,'LWP DEBIT',0,'"+days+"','SANCTION',(select convert(date,GETDATE()) as date))";
				System.out.println("Insert q1  "+insertquery);
				PreparedStatement Pstat = conn.prepareStatement(insertquery);
		       Pstat.executeUpdate();
			    Pstat.close();
			
				}
				
				
				flags="save";
			 conn.close();
	    } 
	   	catch (SQLException e) 
	   	{
		   e.printStackTrace();
		}
	return flags;
	}
	
	
}
