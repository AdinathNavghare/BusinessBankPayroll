package payroll.DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import payroll.Model.CompBean;

public class CompanyHandler {
	
	Connection con = null;
	Statement stmt = null;
	int i;
	
	public int store(CompBean cb) throws IOException{
		
		try{
					
		con = ConnectionManager.getConnection();
		stmt = con.createStatement();
		String query = "INSERT INTO COMP_DETAILS VALUES ('"+cb.getCname()+"','"
														+cb.getCadd()+"','"
														+cb.getCity()+"','"
														+cb.getCountry()+"','"
														+cb.getPhno()+"','"
														+cb.getPincode()+"','"
														+cb.getEmail()+"','"
														+cb.getWebsite()+"','"
														+cb.getFrmdate()+"','"
														+cb.getPfno()+"','"
														+cb.getEsicno()+"','"
														+cb.getPanno()+"','"
														+cb.getTanno()+"','"
														+cb.getDomainname()+"','"
														+cb.getCompcode()+"','"
														+cb.getWoffday()+"','"
														+cb.getAltsat()+"')";
		System.out.println(query);
		i = stmt.executeUpdate(query);

		getweekdays(cb);
	
		con.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return i;
	}	


	public ArrayList<CompBean> getDetails(){
		
		ArrayList<CompBean> list = new ArrayList<CompBean>();
		
		try{
			con = ConnectionManager.getConnection();
			stmt = con.createStatement();
			ResultSet rs = stmt.getResultSet();
			rs = stmt.executeQuery("SELECT * FROM COMP_DETAILS");
			
			
				while(rs.next()){
					CompBean cbn = new CompBean();		
					cbn.setCname(rs.getString(1));
					cbn.setCadd(rs.getString(2));
					cbn.setCity(rs.getString(3));
					cbn.setCountry(rs.getString(4));
					cbn.setPhno(rs.getString(5));
					cbn.setPincode(rs.getInt(6));
					cbn.setEmail(rs.getString(7));
					cbn.setWebsite(rs.getString(8));
					cbn.setFrmdate(rs.getString(9));
					cbn.setPfno(rs.getString(10));
					cbn.setEsicno(rs.getString(11));
					cbn.setPanno(rs.getString(12));
					cbn.setTanno(rs.getString(13));
					cbn.setDomainname(rs.getString(14));
					cbn.setCompcode(rs.getInt(15));
					cbn.setWoffday(rs.getString(16));
					cbn.setAltsat(rs.getString(17));
					
					list.add(cbn);
									
				}
		
			stmt.close();
			con.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return list;
	}
	
	
public boolean updateDetails(CompBean cb){
			
		boolean flag = false;
		String update = "UPDATE COMP_DETAILS SET COMPANY_NAME='"+cb.getCname()
													+"', ADDRESS='"+cb.getCadd()
													+"', CITY='"+cb.getCity()
													+"', COUNTRY='"+cb.getCountry()
													+"', PHONE_NO='"+cb.getPhno()
													+"', PIN_CODE='"+cb.getPincode()
													+"', EMAIL='"+cb.getEmail()
													+"', WEBSITE='"+cb.getWebsite()
													+"', FROM_DATE='"+cb.getFrmdate()
													+"', PF_NO='"+cb.getPfno()
													+"', ESIC_NO='"+cb.getEsicno()
													+"', TAN_NO='"+cb.getTanno()
													+"', PAN_NO='"+cb.getPanno()
													+"', DOMAIN_NAME='"+cb.getDomainname()
													+"', WEEK_OFF='"+cb.getWoffday()
													+"', ALTER_SAT='"+cb.getAltsat()
													+"' WHERE COMP_CODE='"+cb.getCompcode()
													+"' ";
	
		String delete="DELETE FROM HOLDMAST where HTYPE='Weekly Off' and HOLDNAME='Week OFF'";
		try{
			con = ConnectionManager.getConnection();
			stmt = con.createStatement();		
			
			stmt.executeUpdate(update);
			flag = true;
			
			stmt.close();
			
			con.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		try{
			con = ConnectionManager.getConnection();
			stmt = con.createStatement();		
			
			stmt.executeUpdate(delete);
			flag = true;
			
			stmt.close();
			
			con.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		getweekdays(cb);
		return flag;
	}

	public boolean getweekdays(CompBean cb)
		{	
													String d=cb.getWoffday();	
													String day1="";
													String day2="";
										
											if(d.length()==8)
											{
											 day1= cb.getWoffday().substring(1,4);
											 day2= cb.getWoffday().substring(5,8);
											}
											if(d.length()==4)
											{
												day1= cb.getWoffday().substring(1,4);
											}
			//System.out.println("value of the day"+day1+"  value of the day 2"+day2);
			
			if(day1.equals("sun")|| day2.equals("sun"))
			{
				SimpleDateFormat format =new SimpleDateFormat("dd-MMM-yyyy");
				String DATE_FORMAT = "yyyy MM dd";
				
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
				Calendar c1 = Calendar.getInstance(); // today
				
				String y=sdf.format(c1.getTime());
				String years=y.substring(0,4);
				int year=Integer.parseInt(years);
				//Connection con=null;

				Calendar cal = new GregorianCalendar(year, Calendar.JANUARY, 1);
				for (int i = 0, inc = 1; i <366 && cal.get(Calendar.YEAR) == year; i+=inc) {
				    if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				        // this is a sunday
				    	String frm="";
				    	frm=format.format(cal.getTime());
			    	//System.out.println("From	:"+frm);
				    	try
						{
							con= ConnectionManager.getConnection();
							Statement st = con.createStatement();
						//	System.out.println("in addsunday mast method");
						st.execute("insert into HOLDMAST(HOLDNAME,FDATE,TDATE,BRANCH,HTEXT,HTYPE,RepeatHold,HDAY,HATTENDENCES,HSMS,OptionalHold) values('Week OFF','"+frm+"' ,'"+frm+"' ,'All','Weekly OFF','Weekly Off','No','No','No','No','No')");
							
							con.close();
						}	
					catch(Exception e)
						{
							e.printStackTrace();
						}
				    	  System.out.println("the value of the "+i+" sunday is "+format.format(cal.getTime()));
				        cal.add(Calendar.DAY_OF_MONTH, 7);   
				    } else {
				        cal.add(Calendar.DAY_OF_MONTH, 1);
				    }
				}
			
			}
			
			
			if(day1.equals("mon")|| day2.equals("mon"))
			{
				
				SimpleDateFormat format =new SimpleDateFormat("dd-MMM-yyyy");
				String DATE_FORMAT = "yyyy MM dd";
				
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
				Calendar c1 = Calendar.getInstance(); // today
				
				String y=sdf.format(c1.getTime());
				String years=y.substring(0,4);
				
				
				int year=Integer.parseInt(years);
				

				Calendar cal = new GregorianCalendar(year, Calendar.JANUARY, 1);
				for (int i = 0, inc = 1; i <366 && cal.get(Calendar.YEAR) == year; i+=inc) {
				    if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
				       
				    	String frm="";
				    	frm=format.format(cal.getTime());
			    
				    	try
						{
							con= ConnectionManager.getConnection();
							Statement st = con.createStatement();
							
						st.execute("insert into HOLDMAST(HOLDNAME,FDATE,TDATE,BRANCH,HTEXT,HTYPE,RepeatHold,HDAY,HATTENDENCES,HSMS,OptionalHold) values('Week OFF','"+frm+"' ,'"+frm+"' ,'All','Weekly OFF','Weekly Off','No','No','No','No','No')");
							
							con.close();
						}	
						
						
						catch(Exception e)
						{
							e.printStackTrace();
						}
				    	  System.out.println("the value of the "+i+" monday is "+format.format(cal.getTime()));
				        cal.add(Calendar.DAY_OF_MONTH, 7);   
				    } else {
				        cal.add(Calendar.DAY_OF_MONTH, 1);
				    }
				}
		
			}
			
			if(day1.equals("tue")|| day2.equals("tue"))
			{
				
				SimpleDateFormat format =new SimpleDateFormat("dd-MMM-yyyy");
				String DATE_FORMAT = "yyyy MM dd";
				
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
				Calendar c1 = Calendar.getInstance(); // today
				
				String y=sdf.format(c1.getTime());
				String years=y.substring(0,4);
				
				
				int year=Integer.parseInt(years);
				//Connection con=null;

				Calendar cal = new GregorianCalendar(year, Calendar.JANUARY, 1);
				for (int i = 0, inc = 1; i <366 && cal.get(Calendar.YEAR) == year; i+=inc) {
				    if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
				       
				    	String frm="";
				    	frm=format.format(cal.getTime());
			    
				    	try
						{
							con= ConnectionManager.getConnection();
							Statement st = con.createStatement();
							
						st.execute("insert into HOLDMAST(HOLDNAME,FDATE,TDATE,BRANCH,HTEXT,HTYPE,RepeatHold,HDAY,HATTENDENCES,HSMS,OptionalHold) values('Week OFF','"+frm+"' ,'"+frm+"' ,'All','Weekly OFF','Weekly Off','No','No','No','No','No')");
							
							con.close();
						}	
						
						
						catch(Exception e)
						{
							e.printStackTrace();
						}
				    	  System.out.println("the value of the "+i+" monday is "+format.format(cal.getTime()));
				        cal.add(Calendar.DAY_OF_MONTH, 7);   
				    } else {
				        cal.add(Calendar.DAY_OF_MONTH, 1);
				    }
				}
		
			}
			
			if(day1.equals("wed")|| day2.equals("wed"))
			{
				
				SimpleDateFormat format =new SimpleDateFormat("dd-MMM-yyyy");
				String DATE_FORMAT = "yyyy MM dd";
				
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
				Calendar c1 = Calendar.getInstance(); // today
				
				String y=sdf.format(c1.getTime());
				String years=y.substring(0,4);
				
				
				int year=Integer.parseInt(years);
				//Connection con=null;

				Calendar cal = new GregorianCalendar(year, Calendar.JANUARY, 1);
				for (int i = 0, inc = 1; i <366 && cal.get(Calendar.YEAR) == year; i+=inc) {
				    if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
				       
				    	String frm="";
				    	frm=format.format(cal.getTime());
			    	
				    	try
						{
							con= ConnectionManager.getConnection();
							Statement st = con.createStatement();
							
						st.execute("insert into HOLDMAST(HOLDNAME,FDATE,TDATE,BRANCH,HTEXT,HTYPE,RepeatHold,HDAY,HATTENDENCES,HSMS,OptionalHold) values('Week OFF','"+frm+"' ,'"+frm+"' ,'All','Weekly OFF','Weekly Off','No','No','No','No','No')");
							
							con.close();
						}	
						
						
						catch(Exception e)
						{
							e.printStackTrace();
						}
				    	  
				        cal.add(Calendar.DAY_OF_MONTH, 7);   
				    } else {
				        cal.add(Calendar.DAY_OF_MONTH, 1);
				    }
				}
			
			}
		 
			if(day1.equals("thu")|| day2.equals("thu"))
			{
				
				SimpleDateFormat format =new SimpleDateFormat("dd-MMM-yyyy");
				String DATE_FORMAT = "yyyy MM dd";
				
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
				Calendar c1 = Calendar.getInstance(); 
				
				String y=sdf.format(c1.getTime());
				String years=y.substring(0,4);
				
				
				int year=Integer.parseInt(years);
				//Connection con=null;

				Calendar cal = new GregorianCalendar(year, Calendar.JANUARY, 1);
				for (int i = 0, inc = 1; i <366 && cal.get(Calendar.YEAR) == year; i+=inc) {
				    if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
				      
				    	String frm="";
				    	frm=format.format(cal.getTime());
			    	
				    	try
						{
							con= ConnectionManager.getConnection();
							Statement st = con.createStatement();
							
						st.execute("insert into HOLDMAST(HOLDNAME,FDATE,TDATE,BRANCH,HTEXT,HTYPE,RepeatHold,HDAY,HATTENDENCES,HSMS,OptionalHold) values('Week OFF','"+frm+"' ,'"+frm+"' ,'All','Weekly OFF','Weekly Off','No','No','No','No','No')");
							
							con.close();
						}	
					
						catch(Exception e)
						{
							e.printStackTrace();
						}
				    	  
				        cal.add(Calendar.DAY_OF_MONTH, 7);   
				    } else {
				        cal.add(Calendar.DAY_OF_MONTH, 1);
				    }
				}
		
			}
			if(day1.equals("fri")|| day2.equals("fri"))
			{
				
				SimpleDateFormat format =new SimpleDateFormat("dd-MMM-yyyy");
				String DATE_FORMAT = "yyyy MM dd";
				
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
				Calendar c1 = Calendar.getInstance(); // today
				
				String y=sdf.format(c1.getTime());
				String years=y.substring(0,4);
				
				
				int year=Integer.parseInt(years);
				//Connection con=null;

				Calendar cal = new GregorianCalendar(year, Calendar.JANUARY, 1);
				for (int i = 0, inc = 1; i <366 && cal.get(Calendar.YEAR) == year; i+=inc) {
				    if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
				        // this is a sunday
				    	String frm="";
				    	frm=format.format(cal.getTime());
			    	//System.out.println("From	:"+frm);
				    	try
						{
							con= ConnectionManager.getConnection();
							Statement st = con.createStatement();
							
						st.execute("insert into HOLDMAST(HOLDNAME,FDATE,TDATE,BRANCH,HTEXT,HTYPE,RepeatHold,HDAY,HATTENDENCES,HSMS,OptionalHold) values('Week OFF','"+frm+"' ,'"+frm+"' ,'All','Weekly OFF','Weekly Off','No','No','No','No','No')");
							
							con.close();
						}	
						catch(Exception e)
						{
							e.printStackTrace();
						}
				    	  System.out.println("the value of the "+i+" monday is "+format.format(cal.getTime()));
				        cal.add(Calendar.DAY_OF_MONTH, 7);   
				    } else {
				        cal.add(Calendar.DAY_OF_MONTH, 1);
				    }
				}
		
			}
			if(day1.equals("sat")|| day2.equals("sat"))
			{
				
				SimpleDateFormat format =new SimpleDateFormat("dd-MMM-yyyy");
				String DATE_FORMAT = "yyyy MM dd";
				
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
				Calendar c1 = Calendar.getInstance(); // today
				
				String y=sdf.format(c1.getTime());
				String years=y.substring(0,4);
				int year=Integer.parseInt(years);
				//Connection con=null;

				Calendar cal = new GregorianCalendar(year, Calendar.JANUARY, 1);
				for (int i = 0, inc = 1; i <366 && cal.get(Calendar.YEAR) == year; i+=inc) {
				    if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
				   
				    	String frm="";
				    	frm=format.format(cal.getTime());
			    	
				    	try
						{
							con= ConnectionManager.getConnection();
							Statement st = con.createStatement();
							
						st.execute("insert into HOLDMAST(HOLDNAME,FDATE,TDATE,BRANCH,HTEXT,HTYPE,RepeatHold,HDAY,HATTENDENCES,HSMS,OptionalHold) values('Week OFF','"+frm+"' ,'"+frm+"' ,'All','Weekly OFF','Weekly Off','No','No','No','No','No')");
							
							con.close();
						}	
						catch(Exception e)
						{
							e.printStackTrace();
						}
				    	  
				        cal.add(Calendar.DAY_OF_MONTH, 7);   
				    } else {
				        cal.add(Calendar.DAY_OF_MONTH, 1);
				    }
				}
			}
		
			//for inserting alternate saturdays
			
			String s=cb.getAltsat(); 
			String s1="";
			String s2="";
			String s3="";
			String s4="";
			String s5="";

			if(s.length()==10)
			{
				s1=cb.getAltsat().substring(1,2);
				s2=cb.getAltsat().substring(3,4);
				s3=cb.getAltsat().substring(5,6);
				s4=cb.getAltsat().substring(7,8);
				s5=cb.getAltsat().substring(9,10);
			}

			if(s.length()==8)
			{
				s1=cb.getAltsat().substring(1,2);
				s2=cb.getAltsat().substring(3,4);
				s3=cb.getAltsat().substring(5,6);
				s4=cb.getAltsat().substring(7,8);				
			}
			
			if(s.length()==6)
			{
				s1=cb.getAltsat().substring(1,2);
				s2=cb.getAltsat().substring(3,4);
				s3=cb.getAltsat().substring(5,6);
			}
			
			if(s.length()==4)
			{
				s1=cb.getAltsat().substring(1,2);
				s2=cb.getAltsat().substring(3,4);
			}
			if(s.length()==2)
			{
				s1=cb.getAltsat().substring(1,2);
			}

			if(s1.equals("1") )
			{

				SimpleDateFormat format =new SimpleDateFormat("dd-MMM-yyyy");
				String DATE_FORMAT = "yyyy MM dd";
				
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
				Calendar c1 = Calendar.getInstance(); 
				
				String y=sdf.format(c1.getTime());
				String years=y.substring(0,4);
				int year=Integer.parseInt(years);

				Calendar cal = new GregorianCalendar(year, Calendar.JANUARY, 1);
				for (int i = 0, inc = 1; i <366 && cal.get(Calendar.YEAR) == year; i+=inc) {
				    if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
				        
				    	String frm="";
				    	frm=format.format(cal.getTime());
			    
				    	try
						{
							con= ConnectionManager.getConnection();
							Statement st = con.createStatement();
							
						st.execute("insert into HOLDMAST(HOLDNAME,FDATE,TDATE,BRANCH,HTEXT,HTYPE,RepeatHold,HDAY,HATTENDENCES,HSMS,OptionalHold) values('Week OFF','"+frm+"' ,'"+frm+"' ,'All','Weekly OFF','Weekly Off','No','No','No','No','No')");
						con.close();
						}	
						catch(Exception e)
						{
							e.printStackTrace();
						}
				    	
				    	 // System.out.println("the value of the "+i+" saturday is "+format.format(cal.getTime()));
				    	  cal.add(Calendar.MONTH,1);
				    	  cal.set(Calendar.DAY_OF_MONTH, 1);
				    } 
				    
				    else {
				        cal.add(Calendar.DAY_OF_MONTH, 1);
				    	 }
				}
			}
			
			if(s1.equals("2")|| s2.equals("2"))
			{
				SimpleDateFormat format =new SimpleDateFormat("dd-MMM-yyyy");
				String DATE_FORMAT = "yyyy MM dd";
				
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
				Calendar c1 = Calendar.getInstance(); 
				
				String y=sdf.format(c1.getTime());
				String years=y.substring(0,4);
				int year=Integer.parseInt(years);
				

				Calendar cal = new GregorianCalendar(year, Calendar.JANUARY, 1);
				for (int i = 0, inc = 1; i <366 && cal.get(Calendar.YEAR) == year; i+=inc) {
				    if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
				        
				    	String frm="";
				    	cal.add(Calendar.DATE, 7);
				    	frm=format.format(cal.getTime());
				    	 
				    	try
						{
							con= ConnectionManager.getConnection();
							Statement st = con.createStatement();
							
						st.execute("insert into HOLDMAST(HOLDNAME,FDATE,TDATE,BRANCH,HTEXT,HTYPE,RepeatHold,HDAY,HATTENDENCES,HSMS,OptionalHold) values('Week OFF','"+frm+"' ,'"+frm+"' ,'All','Weekly OFF','Weekly Off','No','No','No','No','No')");
						con.close();
						}	
						catch(Exception e)
						{
							e.printStackTrace();
						}
				    	
				    	 // System.out.println("the value of the "+i+" saturday is "+format.format(cal.getTime()));
				    	  cal.add(Calendar.MONTH,1);
				    	  cal.set(Calendar.DAY_OF_MONTH, 1);
				    } 
				    
				    else {
				        cal.add(Calendar.DAY_OF_MONTH, 1);
				    	 }
				}
			}
			
			
			if(s1.equals("3")|| s2.equals("3")|| s3.equals("3"))
			{
				SimpleDateFormat format =new SimpleDateFormat("dd-MMM-yyyy");
				String DATE_FORMAT = "yyyy MM dd";
				
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
				Calendar c1 = Calendar.getInstance(); 
				
				String y=sdf.format(c1.getTime());
				String years=y.substring(0,4);
				int year=Integer.parseInt(years);
				

				Calendar cal = new GregorianCalendar(year, Calendar.JANUARY, 1);
				for (int i = 0, inc = 1; i <366 && cal.get(Calendar.YEAR) == year; i+=inc) {
				    if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
				        
				    	String frm="";
				    	cal.add(Calendar.DATE, 14);
				    	frm=format.format(cal.getTime());
						    	 
				    	try
						{
							con= ConnectionManager.getConnection();
							Statement st = con.createStatement();
							
						st.execute("insert into HOLDMAST(HOLDNAME,FDATE,TDATE,BRANCH,HTEXT,HTYPE,RepeatHold,HDAY,HATTENDENCES,HSMS,OptionalHold) values('Week OFF','"+frm+"' ,'"+frm+"' ,'All','Weekly OFF','Weekly Off','No','No','No','No','No')");
						con.close();
						}	
						catch(Exception e)
						{
							e.printStackTrace();
						}
				    	
				    	 // System.out.println("the value of the "+i+" saturday is "+format.format(cal.getTime()));
				    	  cal.add(Calendar.MONTH,1);
				    	  cal.set(Calendar.DAY_OF_MONTH, 1);
				    } 
				    
				    else {
				        cal.add(Calendar.DAY_OF_MONTH, 1);
				    	 }
				}
			}
			
			if(s1.equals("4")|| s2.equals("4")|| s3.equals("4")||s4.equals("4"))
			{
				SimpleDateFormat format =new SimpleDateFormat("dd-MMM-yyyy");
				String DATE_FORMAT = "yyyy MM dd";
				
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
				Calendar c1 = Calendar.getInstance(); 
				
				String y=sdf.format(c1.getTime());
				String years=y.substring(0,4);
				int year=Integer.parseInt(years);
		
				Calendar cal = new GregorianCalendar(year, Calendar.JANUARY, 1);
				for (int i = 0, inc = 1; i <366 && cal.get(Calendar.YEAR) == year; i+=inc) {
				    if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
				        
				    	String frm="";
				    	cal.add(Calendar.DATE, 21);
				    	frm=format.format(cal.getTime());
			 	    	 
				    	try
						{
							con= ConnectionManager.getConnection();
							Statement st = con.createStatement();
							
						st.execute("insert into HOLDMAST(HOLDNAME,FDATE,TDATE,BRANCH,HTEXT,HTYPE,RepeatHold,HDAY,HATTENDENCES,HSMS,OptionalHold) values('Week OFF','"+frm+"' ,'"+frm+"' ,'All','Weekly OFF','Weekly Off','No','No','No','No','No')");
						con.close();
						}	
						catch(Exception e)
						{
							e.printStackTrace();
						}
				    	
				    	 // System.out.println("the value of the "+i+" saturday is "+format.format(cal.getTime()));
				    	  cal.add(Calendar.MONTH,1);
				    	  cal.set(Calendar.DAY_OF_MONTH, 1);
				    } 
				    
				    else {
				        cal.add(Calendar.DAY_OF_MONTH, 1);
				    	 }
				}
			}
			if(s1.equals("5")|| s2.equals("5")|| s3.equals("5")||s4.equals("5")|| s5.equals("5"))
			{
				SimpleDateFormat format =new SimpleDateFormat("dd-MMM-yyyy");
				String DATE_FORMAT = "yyyy MM dd";
				
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
				Calendar c1 = Calendar.getInstance(); 
				
				String y=sdf.format(c1.getTime());
				String years=y.substring(0,4);
				int year=Integer.parseInt(years);

				Calendar cal = new GregorianCalendar(year, Calendar.JANUARY, 1);
				for (int i = 0, inc = 1; i <366 && cal.get(Calendar.YEAR) == year; i+=inc) {
				    if ((cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) && (cal.get(Calendar.WEEK_OF_MONTH)==5)) {
				        
				    	String frm="";
				    
				    	frm=format.format(cal.getTime());
				    	try
						{
							con= ConnectionManager.getConnection();
							Statement st = con.createStatement();
							
						st.execute("insert into HOLDMAST(HOLDNAME,FDATE,TDATE,BRANCH,HTEXT,HTYPE,RepeatHold,HDAY,HATTENDENCES,HSMS,OptionalHold) values('Week OFF','"+frm+"' ,'"+frm+"' ,'All','Weekly OFF','Weekly Off','No','No','No','No','No')");
						con.close();
						}	
						catch(Exception e)
						{
							e.printStackTrace();
						}
				    	 // System.out.println("the value of the "+i+" saturday is "+format.format(cal.getTime()));
				    	  cal.add(Calendar.MONTH,1);
				    	  cal.set(Calendar.DAY_OF_MONTH, 1);
				    } 
				    
				    else {
				        cal.add(Calendar.DAY_OF_MONTH, 1);
				    	 }
				}
			}
			return false;
		
	}
}

