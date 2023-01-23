package payroll.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import payroll.Core.Calculate;
import payroll.Core.ReportDAO;
import payroll.Model.EmpOffBean;
import payroll.Model.LMB;
import payroll.Model.LeaveBalBean;
import payroll.Model.Lookup;
import payroll.Model.TranBean;

public class LMH {
	
	public static float getLeaveDays(String frmdate, String todate, int lvcode, String halfDay){
		System.out.println("in getLeaveDays:-"+frmdate);
		float TotalDays = 0.0f;
		try{
			Connection con=ConnectionManager.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = null;
		
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		
			Date fromDate = formatter.parse(frmdate);
			Date toDate = formatter.parse(todate);
		
			rs = st.executeQuery("SELECT DATEDIFF(day,'"+frmdate+"','"+todate+"')+1 AS DiffDate");
		
			while(rs.next()){
				TotalDays = Float.parseFloat(rs.getString(1));
			}
			rs.close();
			
			if(halfDay.equalsIgnoreCase("yes")){ // To check for halfday
				TotalDays = 0.5f;
				return TotalDays;
			}
			
			Calendar c = Calendar.getInstance();
			c.setTime(fromDate);

			/*ResultSet rs1 = null;
			rs1 = st.executeQuery("SELECT WEEK_OFF, ALTER_SAT FROM COMP_DETAILS");
			String weekoff = null;
			String alterSat = null;
			if(rs1.next()){
				weekoff = rs1.getString(1);
				alterSat = rs1.getString(2);
			}
			rs1.close();
			
			ResultSet rs2 = null;
			rs2 = st.executeQuery("SELECT CONS_HOLIDAYS, CONS_WEEK_OFF FROM LEAVEMASS WHERE LEAVECD="+lvcode);
			String ConsHol = null;
			String ConsWeekOff = null;
			if(rs2.next()){
				ConsHol = rs2.getString(1);
				ConsWeekOff = rs2.getString(2);
			}
			rs2.close();
			
			ResultSet rs3 = null;
			rs3 = st.executeQuery("SELECT FDATE FROM HOLDMAST");
			List<Date> results = new ArrayList<Date>();
			while(rs3.next()){
				results.add(rs3.getDate(1));
			}
			rs3.close();*/
			
			/*if(ConsHol.charAt(0) == 'N'){ // To check for Holiday
				
				while (c.getTime().before(toDate)) {
					
					for(Date date : results){
						
						if(c.getTime().equals(date)){
							TotalDays = TotalDays - 1.0f;
						}
					}
				c.add(Calendar.DATE, 1);	
				}
			}*/
		/*	
			c.setTime(fromDate);
			if(ConsWeekOff.charAt(0) == 'N'){ // To check for Week Off
				
				if( weekoff.contains("sat") && weekoff.contains("sun")){
					
					if(alterSat.contains("two") && alterSat.contains("four")){ // For second & fourth sat
				
						while (c.getTime().before(toDate)) {
							
							if (c.get(Calendar.WEEK_OF_MONTH) == 2 || c.get(Calendar.WEEK_OF_MONTH) == 4) {
								
								if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
									TotalDays = TotalDays - 1.0f;
									
								}
							}
							if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {							
									TotalDays = TotalDays - 1.0f;
									
							}							
						c.add(Calendar.DATE, 1);
						}
					}
					else if(alterSat.contains("one") && alterSat.contains("three")){ // For first & third sat
						
						while (c.getTime().before(toDate)) {
							
							if (c.get(Calendar.WEEK_OF_MONTH) == 1 || c.get(Calendar.WEEK_OF_MONTH) == 3) {
								
								if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
									TotalDays = TotalDays - 1.0f;
									
								}
							}
							if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
									TotalDays = TotalDays - 1.0f;
									
							}							
						c.add(Calendar.DATE, 1);
						}
					}
					else{
						while (c.getTime().before(toDate)) {
																						
								if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
									TotalDays = TotalDays - 1.0f;
									
								}							
						c.add(Calendar.DATE, 1);
						}
					}
				}
				else if(weekoff.contains("sun")){ // checking for sunday 
					
					while (c.getTime().before(toDate)) {
						
						if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
							TotalDays = TotalDays - 1.0f;
							
						}					
					c.add(Calendar.DATE, 1);
					}
				}			
			}
			*/
			st.close();
			con.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return TotalDays;
	}
	
	@SuppressWarnings("null")
	public  ArrayList<LMB> getList(int empno)
	{
		ResultSet rs=null; 
		ArrayList<LMB> leavelist=new ArrayList<LMB>();
		Connection con=ConnectionManager.getConnection();
		Statement st=null;
		Statement st1=null;
		ResultSet rs1 = null;
		ResultSet rs3 = null;
		Statement st2= null;
		int cancelDays = 0;
		
		//String query="SELECT distinct * from LEAVEBAL a where a.EMPNO='"+empno+"' AND BALDT<=(select distinct max(BALDT) from LEAVEBAL b where EMPNO='"+empno+"' AND LEAVECD=a.LEAVECD)";
     	try
     	{
			st1 = con.createStatement();
			rs1= st1.executeQuery("SELECT DISTINCT(LEAVECD) FROM LEAVEBAL WHERE EMPNO="+empno+" and leavecd in (1,2,3,7,10) ORDER BY LEAVECD");
			while(rs1.next())
			{
				st = con.createStatement();
				st2 = con.createStatement();
				
				/*String sqlleavebal = "SELECT TOP 1 * FROM LEAVEBAL WHERE LEAVECD="+rs1.getInt(1)+" AND EMPNO="+empno+"" +
						//	"and srno=(select MAX(srno)"+
						//	"from leavebal where  EMPNO="+empno+" and LEAVECD="+rs1.getInt(1)+") " +
						"and baldt =(select MAX(baldt) from leavebal where lEAVECD="+rs1.getInt(1)+" " +
								" AND EMPNO="+empno+"   )"+
							" ORDER BY BALDT DESC,srno desc";*/
				
				String sqlleavebal = "SELECT TOP 1 * FROM LEAVEBAL WHERE LEAVECD="+rs1.getInt(1)+" AND EMPNO="+empno+"" +
						//	"and srno=(select MAX(srno)"+
						//	"from leavebal where  EMPNO="+empno+" and LEAVECD="+rs1.getInt(1)+") " +
						"and srno =(select MAX(srno) from leavebal where lEAVECD="+rs1.getInt(1)+" " +
								" AND EMPNO="+empno+"   )"+
							" ORDER BY BALDT DESC,srno desc";
				
				System.out.println("sqlleavebal "+sqlleavebal);
				
				rs=st.executeQuery(sqlleavebal);
				/*
				rs=st.executeQuery("SELECT TOP 1 * FROM LEAVEBAL WHERE LEAVECD="+rs1.getInt(1)+" AND EMPNO="+empno+"" +
								"	ORDER BY leavecd, srno DESC");*/
				
				if(rs.next())
				{
					cancelDays=0;
				//	rs3=st2.executeQuery("select isnull(SUM(DAYS),0) as canceldays from leavetran where STATUS = 'REJECTED' and lEAVECD="+rs1.getInt(1)+" AND EMPNO="+empno+"");
					rs3=st2.executeQuery("select isnull(SUM(DAYS),0) as canceldays from leavetran where STATUS = 'REJECTED' and lEAVECD="+rs1.getInt(1)+" AND EMPNO="+empno+"");
					while(rs3.next())
					{
						 cancelDays = rs3.getInt("canceldays");
					}
					 rs3.close();
					System.out.println("Cancel Day" +cancelDays);
					 
					
					LMB bean=new LMB();
					bean.setBALDT(rs.getDate("BALDT")==null?"":dateFormat(rs.getDate("BALDT")));
					bean.setEMPNO(rs.getString("EMPNO")==null?0:rs.getInt(("EMPNO")));
					bean.setLEAVECD(rs.getString("LEAVECD")==null?0:rs.getInt("LEAVECD"));
					bean.setBAL(rs.getString("BAL")==""?0.0f:rs.getFloat("BAL"));
					bean.setTOTCR(rs.getString("TOTCR")==""?0.0f:rs.getFloat("TOTCR"));
					bean.setTOTDR(rs.getString("TOTDR")==""?0.0f:rs.getFloat("TOTDR"));
					bean.setCANCELLEAVE(cancelDays);
					leavelist.add(bean);
				}
			
				 st2.close();
				rs.close();
				
				
				st.close();
				
			}
			rs1.close();
			st1.close();
			con.close();
		}
     	catch (Exception e) 
     	{
			e.printStackTrace();
		}
		return leavelist;
	}
	
	public  ArrayList<LMB> leaveDisplay(int empno1,String leaveType)
	{
		ResultSet rs=null;  
		ArrayList<LMB> List1 =new ArrayList<LMB>();
		try
		{
			String query = "";
			
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			LookupHandler objDesc=new LookupHandler();
			String Result1;
			if(leaveType.equalsIgnoreCase("PENDING")){
				 query="SELECT * FROM LEAVETRAN   WHERE empno='"+empno1+"'  AND STATUS = 'PENDING' order by trndate desc,frmdt desc";
			}else if(leaveType.equalsIgnoreCase("SANCTION")){
				 query="SELECT * FROM LEAVETRAN   WHERE empno='"+empno1+"'  AND STATUS = 'SANCTION'  order by trndate desc,frmdt desc";
			}else if(leaveType.equalsIgnoreCase("CANCEL")){
				 query="SELECT * FROM LEAVETRAN   WHERE empno='"+empno1+"'  AND STATUS = 'CANCEL' order by trndate desc,frmdt desc";
			}else
			     query="SELECT * FROM LEAVETRAN   WHERE empno='"+empno1+"' AND STATUS !='1' AND  STATUS != 'EditCancel' AND  STATUS != 'PendingCancel' AND STATUS !='ENCASHED' order by trndate desc,frmdt desc";

			     //query="SELECT * FROM LEAVETRAN   WHERE empno='"+empno1+"' AND STATUS !='1' AND  STATUS != 'EditCancel' order by trndate desc,frmdt desc";
			
			
			rs=st.executeQuery(query);
			while(rs.next())
			{
				LMB lbean1=new LMB();
				lbean1.setLEAVECD(rs.getString("leavecd")==null?0:rs.getInt("leavecd"));
				lbean1.setEMPNO(rs.getString("empno")==null?0:rs.getInt("empno"));
				lbean1.setTRNDATE(rs.getDate("trndate")==null?"":dateFormat(rs.getDate("trndate")));
				lbean1.setTRNTYPE(rs.getString("trntype").charAt(0));
				lbean1.setAPPLNO(rs.getString("applno")==null?"":rs.getString("applno"));
			//	lbean1.setBRCODE(rs.getString("brcode")==null?0:rs.getInt("brcode"));
				lbean1.setLEAVEPURP(rs.getString("leavepurp")==null?0:rs.getInt("leavepurp"));
			
				lbean1.setLREASON(rs.getString("LREASON")==null?"--":rs.getString("LREASON"));
				lbean1.setLADDR(rs.getString("LADDR")==null?"--":rs.getString("LADDR"));
				lbean1.setLTELNO(rs.getString("LTELNO")==null?0:rs.getLong("LTELNO"));
				lbean1.setAPPLDT(rs.getDate("APPLDT")==null?"":dateFormat(rs.getDate("APPLDT")));
				lbean1.setFRMDT(rs.getDate("FRMDT")==null?"":dateFormat(rs.getDate("FRMDT")));
				lbean1.setTODT(rs.getDate("TODT")==null?"":dateFormat(rs.getDate("TODT")));
				
				Lookup lkhp = new Lookup();
				lkhp = objDesc.getLookup("SAUTH-"+rs.getInt("SANCAUTH"));
				Result1 = lkhp.getLKP_DESC();
				lbean1.setSANCAUTH(Result1);
				lbean1.setOPR_CD(rs.getString("OPR_CD")==null?"":rs.getString("OPR_CD"));
				lbean1.setOFF_CD(rs.getString("OFF_CD")==null?"":rs.getString("OFF_CD"));
				lbean1.setSTATUS(rs.getString("STATUS")==null?"":rs.getString("STATUS"));
				lbean1.setSUBSTITUTE(rs.getString("SUBSTITUTE")==null?"":rs.getString("SUBSTITUTE"));
				lbean1.setNODAYS(rs.getString("DAYS")==null?0:rs.getFloat("DAYS"));
				List1.add(lbean1);
			}
			rs.close();
			st.close();
			con.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}	
		return List1;
	}
	
	public  String addLeave(LMB lbean,int applno1) 
	{   
		
		
		System.out.println("In addLeave"+applno1);

		String result = "";
		int count=0;
		int count1=0;
		float totDed = 0;
		int lateday=0;
		float totPl = 0;
		float totCr = 0;
		float bal = 0;
		int flag =0;
		int flag1=0;
		float Ldays = 0;
		float finalDays = 0;
		float finalDays_of_PL = 0;
		float CLBal = 0;
		float PLBal = 0;
		try
		{	
			Connection con=ConnectionManager.getConnection();
			Connection conn = ConnectionManager.getConnection();
			float TotalDays = LMH.getLeaveDays(lbean.getFRMDT(), lbean.getTODT(), lbean.getLEAVECD(), lbean.getHALFDAY());
			Statement st = con.createStatement();
			Statement statement = con.createStatement();
			Statement statement1 = con.createStatement();
			ResultSet rs1 = null;
			ResultSet rs01 = null;
			ResultSet resultSetForOverlap=null;
			 System.out.println("leavecode: "+lbean.getLEAVECD());
			rs1=st.executeQuery("SELECT TOP 1 * FROM LEAVEBAL WHERE LEAVECD="+lbean.getLEAVECD()+" AND EMPNO="+lbean.getEMPNO()+" and baldt<='"+ReportDAO.EOM(lbean.getTRNDATE())+"'  ORDER BY BALDT DESC");
			float balance = 0.0f;
			
			float days=0.0f;
			if(rs1.next())
			{
				balance = rs1.getFloat("BAL");
			}
			rs1.close();
			 System.out.println("04: "+balance);
			
			Statement st1 = con.createStatement();
			ResultSet rs2 = null;
			rs2 = st1.executeQuery("SELECT MAX(APPLNO)+1 FROM LEAVETRAN");
			int applno = 0;
			if(rs2.next()){
				applno = rs2.getInt(1);
			}
	//		appno_sanction_cancel =applno;
			/*if(lbean.getLEAVECD()== 8)
			{
			rs01=st.executeQuery("select COUNT(*) as count from empmast where empno="+lbean.getEMPNO()+" and GENDER <>'F'");
			
			if(rs01.next())
			{
				count1 = rs01.getInt("count");
			}
			if(count1>0)
				return "7";
			rs01.close();
			}*/
			
			
			//checking overlap
			/*String overlap="select TODT from LEAVETRAN " +
					"where EMPNO="+lbean.getEMPNO()+"  and TRNTYPE='D' and (STATUS='SANCTION' OR STATUS='PENDING') "  +
					" and APPLNO=(SELECT MAX(APPLNO)" +
					" FROM LEAVETRAN where EMPNO="+lbean.getEMPNO()+" and TRNTYPE='D')";
			//System.out.println("overlap : "+overlap);
			resultSetForOverlap=st.executeQuery(overlap);
			//String fromDate="";
			
			if(resultSetForOverlap.next()){
				//fromDate=rs1.getString("FRMDT");
			Date toDate=resultSetForOverlap.getDate("TODT");
				//System.out.println("todate"+toDate);
				String overlapSecond="select * from leavetran " +
				"where empno="+lbean.getEMPNO()+" and" +
			//		"	'"+lbean.getFRMDT()+"' < '"+toDate+ "' and TRNTYPE='D'";
			"	((frmdt = '"+lbean.getFRMDT()+"') ) " +
					"and TRNTYPE='D'";
				System.out.println("overlapSecond : "+overlapSecond);
			ResultSet resultSetForOverlapSecond=statement.executeQuery(overlapSecond);
				
				while(resultSetForOverlapSecond.next()){
				java.util.Date convertedDate=null;				
				SimpleDateFormat sourceFormat = new SimpleDateFormat("dd-MMM-yyyy");
				
		        convertedDate = (java.util.Date) sourceFormat.parse(lbean.getFRMDT());
				if(toDate.after(convertedDate) || toDate.equals(convertedDate) )
					return "2";
			}
			}*/
			
			
			/*String overlap="select COUNT(*) as count from leavetran where '"+lbean.getFRMDT()+"'" +
					" between FRMDT and TODT and EMPNO="+lbean.getEMPNO()+" " +
							"and status in ('SANCTION','CANCEL','PENDING') AND TRNTYPE='D'    ";*/
			
			//ending overlap
			
			
			///akshay nikam
			
			
			/*String overlap="select COUNT(*) as count from leavetran where (('"+lbean.getFRMDT()+"'" +
					" between FRMDT and TODT OR '"+lbean.getTODT()+"'" +
					 " between FRMDT and TODT ) or (FRMDT "  +
					 " between  '"+lbean.getFRMDT()+"' and '"+lbean.getTODT()+"' OR TODT" +
					 " between  '"+lbean.getFRMDT()+"' and'"+lbean.getTODT()+"'  ))" +
					 " and EMPNO="+lbean.getEMPNO()+"and status in ('SANCTION','PENDING') AND TRNTYPE='D' ";
			System.out.println(overlap);
			resultSetForOverlap=st.executeQuery(overlap);
			if(resultSetForOverlap.next()){
				count=resultSetForOverlap.getInt("count");
			}
			System.out.println("c for count"+count);
			if(count>0)
			{
				System.out.println("y1");
			    flag1 =2;
			    
			    
			    if(lbean.getFlag_editSanction()==468)// 468 for sanctioned edit leave endicat^n buffer
			    {
			    	System.out.println("y2");
			     int EMPNO=lbean.getEMPNO();
			 	 Connection con01=ConnectionManager.getConnection();
				 statement1.executeUpdate("update leavetran set status='SANCTION' where applno="+applno1+" ");
			     LMB list1 =new LMB();
				 LMH leaveMasterHandler = new LMH();
				 list1 = leaveMasterHandler.getLeaveApp1(EMPNO,applno1);
				 String Baldate= ReportDAO.getServerDate();
				 String querybal = "SELECT top 1 bal,totdr,TOTCR FROM LEAVEBAL a where a.EMPNO='"+EMPNO+"' AND a.LEAVECD = '" + list1.getLEAVECD() +"' AND srno=(select  max(srno) from LEAVEBAL b where EMPNO='"+EMPNO+"' AND LEAVECD ='"+list1.getLEAVECD()+"' and (BALDT >=CONVERT(char(10), CONVERT(date,'"+Baldate+"',105),126) or BALDT <=CONVERT(char(10), CONVERT(date,'"+Baldate+"',105),126) ) ) ORDER BY  baldt desc,srno DESC";
				 System.out.println("04:-"+querybal);
				 System.out.println("empno"+EMPNO+"   "+applno);
			    
			     System.out.println("BaldateBaldate12"+Baldate);
		         System.out.println("BaldateBaldate11"+Baldate);
				 Statement stmt6=con01.createStatement();
				 ResultSet rs3;
				 System.out.println("04:-"+querybal);
				 rs3 = stmt6.executeQuery(querybal);
				
				  float balance1 = 0.0f;
				  float totdr = 0.0f;
				  float tcredit = 0.0f;
				  if(rs3.next()){
					balance1 = rs3.getFloat("bal");	
					System.out.println("05: "+balance);
					totdr = rs3.getFloat("totdr");										
					tcredit = rs3.getFloat("TOTCR");	
					
					System.out.println("06: "+totdr);
					System.out.println("07: "+tcredit);
				}
				float totaldays = 0.0f;
				balance1=balance1-list1.getNODAYS();
				totaldays=Math.abs(totdr-list1.getNODAYS()); 
				System.out.println("y3"+totaldays);
				if(Integer.parseInt(leavecd) == 4){
					totaldays = totdr + leaveDays;
					balance = balance - leaveDays;
				}
				else{
					balance = balance - leaveDays;
					totaldays = totdr + leaveDays;	
					
					System.out.println("08: "+balance);
					System.out.println("09: "+totaldays);
				}		                                    
				String query2 = "INSERT INTO LEAVEBAL VALUES((CONVERT(char(10), CONVERT(date,'"+Baldate+"',105),126)),'"+EMPNO+"','"+list1.getLEAVECD()+"','"+balance1+"','"+tcredit+"','"+totaldays+"')";
				stmt6.executeUpdate(query2);
				System.out.println("10: "+query2);
				
			    }
				
				result="8";
				return result;
			}*/

			
			//checking holidays
			/*String holidays="select min(fdate) from HOLDMAST where" +
					" (FDATE='"+lbean.getFRMDT()+"' or FDATE='"+lbean.getTODT()+"')" +
							" and  ( BRANCH='ALL' or BRANCH='"+lbean.getPrj_srno()+"')";
			//System.out.println("holidays : "+holidays);
			ResultSet resultSetForHolidays=st.executeQuery(holidays);
			while(resultSetForHolidays.next()){
				return "3";
			}*/
			//ending holidays
			
			st.close();
			if((lbean.getLEAVECD()!=7) && (lbean.getLEAVECD()!=10 ) && (lbean.getLEAVECD()!=4 )){
			 days = balance - TotalDays;	// To check the leave balance after deduction of applied leave days.
			}
			// commented by P.H.patil Sir's order
			/*if(lbean.getLEAVECD()==3){
			CallableStatement cst1= con.prepareCall("{call CHECKING_CL(?,?,?) }");
			cst1.setString(1,lbean.getFRMDT());
			cst1.setInt(2,lbean.getEMPNO());
			cst1.setInt(3,lbean.getLEAVECD());  
			ResultSet rs = cst1.executeQuery();
			rs.next();

		if(	(!rs.getString("ReturnValue").equals("0")) && 	(!rs.getString("ReturnValue").equals("-1"))){
			return rs.getString("ReturnValue");
		}
			}	*/
			
			
			
			if(lbean.getLEAVECD()==7 || lbean.getLEAVECD()==10 ){
				days=TotalDays;
				System.out.println("the leavecode is 2"+lbean.getLEAVECD()+"days :"+days);
				}
			
			if((TotalDays > 0 || lbean.getLEAVECD() == 4) && lbean.getLEAVECD() != 6 ){
				System.out.println("In COFF Leave 3:---"+lbean.getLEAVECD());
				
				/*Statement st1 = con.createStatement();
				ResultSet rs2 = null;
				rs2 = st1.executeQuery("SELECT MAX(APPLNO)+1 FROM LEAVETRAN");
				int applno = 0;
				if(rs2.next()){
					applno = rs2.getInt(1);
				}*/
				String insertquery="INSERT INTO LEAVETRAN (EMPNO,LEAVECD,TRNDATE,TRNTYPE,APPLNO,FRMDT,TODT,LEAVEPURP," +
						"LADDR,LTELNO,DAYS,STATUS,PRJ_SRNO,APPLDT,LREASON,CREATED_BY,CREATED_DATE)" +
						" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement Pstat = con.prepareStatement(insertquery);
				Pstat.setInt(1,lbean.getEMPNO());
				Pstat.setInt(2,lbean.getLEAVECD());
				Pstat.setString(3, lbean.getTRNDATE());
			    Pstat.setString(4, "D");
			    Pstat.setInt(5, applno);
			    Pstat.setString(6, lbean.getFRMDT());
			    Pstat.setString(7, lbean.getTODT());
			    Pstat.setInt(8, lbean.getLEAVEPURP());
			    Pstat.setString(9,lbean.getLADDR());
			    Pstat.setDouble(10, lbean.getLTELNO());
			    Pstat.setFloat(11, TotalDays);
			    Pstat.setString(12, "PENDING");
			    Pstat.setString(13, lbean.getPrj_srno());
			    Pstat.setString(14, lbean.getTRNDATE());
			    Pstat.setString(15, lbean.getLREASON());
			    Pstat.setInt(16, lbean.getCreated_by());
			    Pstat.setString(17, lbean.getCreated_date());	
			   
			    Pstat.executeUpdate();
			    result = "1";
			    System.out.println("In Apply Leave pending:---"+insertquery);
			    Pstat.close();
			    con.close();
			}
			
			else{
				result = "0";	
			}
			
/*	 For Late Leave Entry...(Leave Code is 6)                     */
			
			if((lbean.getLEAVECD() == 6) ){
				
				Statement st01 = conn.createStatement();
				ResultSet rs5 = null;
				Ldays =lbean.getNODAYS();
				System.out.println("Ldays is  "+Ldays);
				rs5 = st01.executeQuery("SELECT TOP 1 * FROM LEAVEBAL WHERE LEAVECD=3 AND " +
						" EMPNO="	+ lbean.getEMPNO()+"    ORDER BY  baldt DESC,srno desc");
				
				if (rs5.next()) {
					Statement st4 = conn.createStatement();
					CLBal = rs5.getFloat("BAL");
					System.out.println("Leave Bale of CD=3: "+rs5.getFloat("BAL"));
					if(rs5.getFloat("BAL")<=0)
					{
						System.out.println("In Late Leave code=(3) 5:---");
						ResultSet rs6 = null;
						rs6 = st01.executeQuery("SELECT TOP 1 * FROM LEAVEBAL WHERE LEAVECD=1 AND " +
								" EMPNO="	+ lbean.getEMPNO()+"  ORDER BY  baldt DESC,srno desc");	
						if (rs6.next()) {
						Statement st5 = conn.createStatement();
						//PLBal = rs6.getFloat("BAL");
						System.out.println("Leave Bale of CD=1: "+rs6.getFloat("BAL"));
						
						if(rs6.getFloat("BAL")<=0)
						{
							System.out.println("In Late Leave code=(1) 6:---");
							String insertquery1="INSERT INTO LEAVETRAN (EMPNO,LEAVECD,TRNDATE,TRNTYPE,APPLNO,FRMDT,TODT,LEAVEPURP," +
									"LADDR,LTELNO,DAYS,STATUS,PRJ_SRNO,APPLDT,LREASON,CREATED_BY,CREATED_DATE)" +
									" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
							PreparedStatement Pstat11 = conn.prepareStatement(insertquery1);
							Pstat11.setInt(1,lbean.getEMPNO());
							Pstat11.setInt(2, 7);
							Pstat11.setString(3, lbean.getTRNDATE());
						    Pstat11.setString(4, "D");
						    Pstat11.setInt(5, applno);
						    Pstat11.setString(6, lbean.getFRMDT());
						    Pstat11.setString(7, lbean.getTODT());
						    Pstat11.setInt(8, lbean.getLEAVEPURP());
						    Pstat11.setString(9,  "Late Leave");
						    Pstat11.setDouble(10, lbean.getLTELNO());
						    Pstat11.setFloat(11, TotalDays);
						    Pstat11.setString(12, "PENDING");
						    Pstat11.setString(13, lbean.getPrj_srno());
						    Pstat11.setString(14, lbean.getTRNDATE());
						    Pstat11.setString(15, "Late Leave Will be Deducted as CL,PL,LWP");
						    Pstat11.setInt(16, lbean.getCreated_by());
						    Pstat11.setString(17, lbean.getCreated_date());	    
						   Pstat11.executeUpdate();
						    System.out.println("late mark querrrrrrrrrrrrrryyyyyyyyyyyyyy for lwp:--"+insertquery1);
							//st5.execute(insertquery1);
						    result = "1";
						  ///  Pstat1.close();
							
							ResultSet rs7 = null;
							rs7 = st01.executeQuery("SELECT TOP 1 * FROM LEAVEBAL WHERE LEAVECD=7 AND " +
									" EMPNO="	+ lbean.getEMPNO()+"  and bal!=0  ORDER BY srno DESC");	
							if (rs7.next()) {
							
							// to insert lwp in leavebal
							bal = rs7.getString("BAL")==""?0:rs7.getFloat("BAL");
							totCr = rs7.getFloat("TOTCR");
							//String leave2="insert into LEAVEBAL VALUES ('"+rs1.getString("ATTD_DATE")+"',"+rs7.getInt("EMPNO")+",7,"+rs7.getFloat("BAL")+"-1,"+rs7.getFloat("TOTCR")+","+rs7.getFloat("TOTDR")+"+1)";
							//System.out.println("leave2"+ leave2);
							// st4.execute(leave2);
							}
					
						}
						else{
							System.out.println("In Late Leave for pl  7:---");
							String insertquery1="INSERT INTO LEAVETRAN (EMPNO,LEAVECD,TRNDATE,TRNTYPE,APPLNO,FRMDT,TODT,LEAVEPURP," +
									"LADDR,LTELNO,DAYS,STATUS,PRJ_SRNO,APPLDT,LREASON,CREATED_BY,CREATED_DATE)" +
									" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
							PreparedStatement Pstat22 = conn.prepareStatement(insertquery1);
							Pstat22.setInt(1,lbean.getEMPNO());
							Pstat22.setInt(2, 1);
							Pstat22.setString(3, lbean.getTRNDATE());
						    Pstat22.setString(4, "D");
						    Pstat22.setInt(5, applno);
						    Pstat22.setString(6, lbean.getFRMDT());
						    Pstat22.setString(7, lbean.getTODT());
						    Pstat22.setInt(8, lbean.getLEAVEPURP());
						    Pstat22.setString(9, "Late Leave");
						    Pstat22.setDouble(10, lbean.getLTELNO());
						    Pstat22.setFloat(11, TotalDays);
						    Pstat22.setString(12, "PENDING");
						    Pstat22.setString(13, lbean.getPrj_srno());
						    Pstat22.setString(14, lbean.getTRNDATE());
						    Pstat22.setString(15, "Late Leave Will be Deducted as CL,PL,LWP");
						    Pstat22.setInt(16, lbean.getCreated_by());
						    Pstat22.setString(17, lbean.getCreated_date());	    
						    Pstat22.executeUpdate();
						    result = "1";
						   // Pstat1.close();
						    System.out.println("late mark querrrrrrrrrrrrrryyyyyyyyyyyyyy for pl:--"+insertquery1);
						//	st5.execute(insertquery1);
							bal = rs6.getFloat("BAL");
							totCr = rs6.getFloat("TOTCR");
							//String leave2="insert into LEAVEBAL VALUES ('"+rs1.getString("ATTD_DATE")+"',"+rs6.getInt("EMPNO")+",1,"+rs6.getFloat("BAL")+"-1,"+rs6.getFloat("TOTCR")+","+rs6.getFloat("TOTDR")+"+1)";
							//System.out.println("leave2"+ leave2);
							 //st4.execute(leave2);
							
							}
						}
						
				/*Statement st1 = con.createStatement();
				ResultSet rs2 = null;
				rs2 = st1.executeQuery("SELECT MAX(APPLNO)+1 FROM LEAVETRAN");
				int applno = 0;
				if(rs2.next()){
					applno = rs2.getInt(1);
				}*/
				/*String insertquery1="INSERT INTO LEAVETRAN (EMPNO,LEAVECD,TRNDATE,TRNTYPE,APPLNO,FRMDT,TODT,LEAVEPURP," +
						"LTELNO,DAYS,STATUS,PRJ_SRNO,APPLDT,LREASON,CREATED_BY,CREATED_DATE)" +
						" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement Pstat1 = con.prepareStatement(insertquery1);
				Pstat1.setInt(1,lbean.getEMPNO());
				Pstat1.setInt(2,lbean.getLEAVECD());
				Pstat1.setString(3, lbean.getTRNDATE());
			    Pstat1.setString(4, "D");
			    Pstat1.setInt(5, applno);
			    Pstat1.setString(6, lbean.getFRMDT());
			    Pstat1.setString(7, lbean.getTODT());
			    Pstat1.setInt(8, lbean.getLEAVEPURP());
			    Pstat.setString(9, lbean.getLADDR());
			    Pstat1.setDouble(9, lbean.getLTELNO());
			    Pstat1.setFloat(10, TotalDays);
			    Pstat1.setString(11, "PENDING");
			    Pstat1.setString(12, lbean.getPrj_srno());
			    Pstat1.setString(13, lbean.getTRNDATE());
			    Pstat1.setString(14, "Late Leave will Deducted As CL,PL,LWP");
			    Pstat1.setInt(15, lbean.getCreated_by());
			    Pstat1.setString(16, lbean.getCreated_date());	    
			    Pstat1.executeUpdate();
			    result = "1";
			    Pstat1.close();*/
			
			}
		else{
				if(TotalDays > CLBal)
				{
					System.out.println("i am in chk TotalDays ");
					System.out.println("TotalDays is  "+TotalDays);
					System.out.println("CL Bal is  "+rs5.getFloat("BAL"));
					System.out.println("In Late Leave for cl  8:---");
					
					String insertquery5="INSERT INTO LEAVETRAN (EMPNO,LEAVECD,TRNDATE,TRNTYPE,APPLNO,FRMDT,TODT,LEAVEPURP," +
							"LADDR,LTELNO,DAYS,STATUS,PRJ_SRNO,APPLDT,LREASON,CREATED_BY,CREATED_DATE)" +
							" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					PreparedStatement Pstat33 = conn.prepareStatement(insertquery5);
					Pstat33.setInt(1,lbean.getEMPNO());
					Pstat33.setInt(2,3);
					Pstat33.setString(3, lbean.getTRNDATE());
					Pstat33.setString(4, "D");
					Pstat33.setInt(5, applno);
					Pstat33.setString(6, lbean.getFRMDT());
				    Pstat33.setString(7, lbean.getTODT());
				    Pstat33.setInt(8, lbean.getLEAVEPURP());
				    Pstat33.setString(9, "Late Leave");
				    Pstat33.setDouble(10, lbean.getLTELNO());
				    Pstat33.setFloat(11, CLBal);
				    Pstat33.setString(12, "PENDING");
				    Pstat33.setString(13, lbean.getPrj_srno());
				    Pstat33.setString(14, lbean.getTRNDATE());
				    Pstat33.setString(15, "Late Leave Will be Deducted as CL,PL,LWP");
				    Pstat33.setInt(16, lbean.getCreated_by());
				    Pstat33.setString(17, lbean.getCreated_date());	    
				    Pstat33.executeUpdate();
				    System.out.println("late mark querrrrrrrrrrrrrryyyyyyyyyyyyyy for cl:--"+insertquery5);
					//st4.execute(insertquery1);
				    result = "1";
				 //   Pstat1.close();
					
					bal = rs5.getFloat("BAL");
					totCr = rs5.getFloat("TOTCR");
					
					
					
					
					finalDays = TotalDays - CLBal;
					System.out.println("finalDays is  "+finalDays);
					
					ResultSet rs15 = null;
					rs15 = st01.executeQuery("SELECT TOP 1 * FROM LEAVEBAL WHERE LEAVECD=1 AND " +
							" EMPNO="	+ lbean.getEMPNO()+"  ORDER BY  baldt DESC,srno desc");	
					if (rs15.next()) {
					Statement st9 = conn.createStatement();
					
					
					if(rs15.getFloat("BAL")==0)
					{

						Statement st18 = con.createStatement();
						ResultSet rs18 = null;
						rs18 = st18.executeQuery("SELECT MAX(APPLNO)+1 FROM LEAVETRAN");
						int applno2 = 0;
						if(rs18.next()){
							applno2 = rs18.getInt(1);
						}
						
						System.out.println("In Late Leave code=(01) 60:---");
						String insertquery01="INSERT INTO LEAVETRAN (EMPNO,LEAVECD,TRNDATE,TRNTYPE,APPLNO,FRMDT,TODT,LEAVEPURP," +
								"LADDR,LTELNO,DAYS,STATUS,PRJ_SRNO,APPLDT,LREASON,CREATED_BY,CREATED_DATE)" +
								" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						PreparedStatement Pstat11 = conn.prepareStatement(insertquery01);
						Pstat11.setInt(1,lbean.getEMPNO());
						Pstat11.setInt(2, 7);
						Pstat11.setString(3, lbean.getTRNDATE());
					    Pstat11.setString(4, "D");
					    Pstat11.setInt(5, applno2);
					    Pstat11.setString(6, lbean.getFRMDT());
					    Pstat11.setString(7, lbean.getTODT());
					    Pstat11.setInt(8, lbean.getLEAVEPURP());
					    Pstat11.setString(9,  "Late Leave");
					    Pstat11.setDouble(10, lbean.getLTELNO());
					    Pstat11.setFloat(11, finalDays);
					    Pstat11.setString(12, "PENDING");
					    Pstat11.setString(13, lbean.getPrj_srno());
					    Pstat11.setString(14, lbean.getTRNDATE());
					    Pstat11.setString(15, "Late Leave Will be Deducted as CL,PL,LWP");
					    Pstat11.setInt(16, lbean.getCreated_by());
					    Pstat11.setString(17, lbean.getCreated_date());	    
					    Pstat11.executeUpdate();
					    System.out.println("late mark querrrrrrrrrrrrrryyyyyyyyyyyyyy for lwp:--"+insertquery01);
						//st5.execute(insertquery1);
					    result = "1";
					  ///  Pstat1.close();
						
						ResultSet rs77 = null;
						rs77 = st01.executeQuery("SELECT TOP 1 * FROM LEAVEBAL WHERE LEAVECD=7 AND " +
								" EMPNO="	+ lbean.getEMPNO()+"  and bal!=0  ORDER BY srno DESC");	
						if (rs77.next()) {
						
						// to insert lwp in leavebal
						bal = rs77.getString("BAL")==""?0:rs77.getFloat("BAL");
						totCr = rs77.getFloat("TOTCR");
						//String leave2="insert into LEAVEBAL VALUES ('"+rs1.getString("ATTD_DATE")+"',"+rs7.getInt("EMPNO")+",7,"+rs7.getFloat("BAL")+"-1,"+rs7.getFloat("TOTCR")+","+rs7.getFloat("TOTDR")+"+1)";
						//System.out.println("leave2"+ leave2);
						// st4.execute(leave2);
						}
				
					}
					else{
						
						ResultSet rs45 = null;
						rs45 = st01.executeQuery("SELECT TOP 1 * FROM LEAVEBAL WHERE LEAVECD=1 AND " +
								" EMPNO="	+ lbean.getEMPNO()+"  ORDER BY  baldt DESC,srno desc");	
						if (rs45.next()) {
						Statement st5 = conn.createStatement();
						PLBal = rs45.getFloat("BAL");
						}
						
						
						finalDays_of_PL = finalDays - PLBal;
						System.out.println("finalDays_of_PL is  "+finalDays_of_PL);
						System.out.println("finalDays is "+finalDays);
						if(finalDays > PLBal)
						{
							System.out.println("PLBal is "+PLBal);
							Statement st20 = con.createStatement();
							ResultSet rs20 = null;
							rs20 = st1.executeQuery("SELECT MAX(APPLNO)+1 FROM LEAVETRAN");
							int applno4 = 0;
							if(rs20.next()){
								applno4 = rs20.getInt(1);
							}
							
							
							
							System.out.println("In Late Leave for pl  777:---");
							String insertquery27="INSERT INTO LEAVETRAN (EMPNO,LEAVECD,TRNDATE,TRNTYPE,APPLNO,FRMDT,TODT,LEAVEPURP," +
									"LADDR,LTELNO,DAYS,STATUS,PRJ_SRNO,APPLDT,LREASON,CREATED_BY,CREATED_DATE)" +
									" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
							PreparedStatement Pstat27 = conn.prepareStatement(insertquery27);
							Pstat27.setInt(1,lbean.getEMPNO());
							Pstat27.setInt(2, 1);
							Pstat27.setString(3, lbean.getTRNDATE());
							Pstat27.setString(4, "D");
							Pstat27.setInt(5, applno4);
							Pstat27.setString(6, lbean.getFRMDT());
							Pstat27.setString(7, lbean.getTODT());
							Pstat27.setInt(8, lbean.getLEAVEPURP());
							Pstat27.setString(9, "Late Leave");
							Pstat27.setDouble(10, lbean.getLTELNO());
							Pstat27.setFloat(11, PLBal);
							Pstat27.setString(12, "PENDING");
						    Pstat27.setString(13, lbean.getPrj_srno());
						    Pstat27.setString(14, lbean.getTRNDATE());
						    Pstat27.setString(15, "Late Leave Will be Deducted as CL,PL,LWP");
						    Pstat27.setInt(16, lbean.getCreated_by());
						    Pstat27.setString(17, lbean.getCreated_date());	    
						    Pstat27.executeUpdate();
						    result = "1";
						   // Pstat1.close();
						    System.out.println("late mark querrrrrrrrrrrrrryyyyyyyyyyyyyy for pl 2:--"+insertquery27);
						    
						    
						    Statement st21 = con.createStatement();
							ResultSet rs21 = null;
							rs21 = st1.executeQuery("SELECT MAX(APPLNO)+1 FROM LEAVETRAN");
							int applno5 = 0;
							if(rs21.next()){
								applno5 = rs21.getInt(1);
							}
							
							System.out.println("In Late Leave code=(01) 6:---");
							String insertquery01="INSERT INTO LEAVETRAN (EMPNO,LEAVECD,TRNDATE,TRNTYPE,APPLNO,FRMDT,TODT,LEAVEPURP," +
									"LADDR,LTELNO,DAYS,STATUS,PRJ_SRNO,APPLDT,LREASON,CREATED_BY,CREATED_DATE)" +
									" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
							PreparedStatement Pstat11 = conn.prepareStatement(insertquery01);
							Pstat11.setInt(1,lbean.getEMPNO());
							Pstat11.setInt(2, 7);
							Pstat11.setString(3, lbean.getTRNDATE());
						    Pstat11.setString(4, "D");
						    Pstat11.setInt(5, applno5);
						    Pstat11.setString(6, lbean.getFRMDT());
						    Pstat11.setString(7, lbean.getTODT());
						    Pstat11.setInt(8, lbean.getLEAVEPURP());
						    Pstat11.setString(9,  "Late Leave");
						    Pstat11.setDouble(10, lbean.getLTELNO());
						    Pstat11.setFloat(11, finalDays_of_PL);
						    Pstat11.setString(12, "PENDING");
						    Pstat11.setString(13, lbean.getPrj_srno());
						    Pstat11.setString(14, lbean.getTRNDATE());
						    Pstat11.setString(15, "Late Leave Will be Deducted as CL,PL,LWP");
						    Pstat11.setInt(16, lbean.getCreated_by());
						    Pstat11.setString(17, lbean.getCreated_date());	    
						    Pstat11.executeUpdate();
						    System.out.println("late mark querrrrrrrrrrrrrryyyyyyyyyyyyyy for lwp:--"+insertquery01);
							//st5.execute(insertquery1);
						    result = "1";
						  ///  Pstat1.close();
							
							ResultSet rs77 = null;
							rs77 = st01.executeQuery("SELECT TOP 1 * FROM LEAVEBAL WHERE LEAVECD=7 AND " +
									" EMPNO="	+ lbean.getEMPNO()+"  and bal!=0  ORDER BY srno DESC");	
							if (rs77.next()) {
							
							// to insert lwp in leavebal
							bal = rs77.getString("BAL")==""?0:rs77.getFloat("BAL");
							totCr = rs77.getFloat("TOTCR");
							//String leave2="insert into LEAVEBAL VALUES ('"+rs1.getString("ATTD_DATE")+"',"+rs7.getInt("EMPNO")+",7,"+rs7.getFloat("BAL")+"-1,"+rs7.getFloat("TOTCR")+","+rs7.getFloat("TOTDR")+"+1)";
							//System.out.println("leave2"+ leave2);
							// st4.execute(leave2);
						}
						
						}
						else
						{
						
						Statement st19 = con.createStatement();
						ResultSet rs19 = null;
						rs19 = st1.executeQuery("SELECT MAX(APPLNO)+1 FROM LEAVETRAN");
						int applno3 = 0;
						if(rs19.next()){
							applno3 = rs19.getInt(1);
						}
						
						
						System.out.println("In Late Leave for pl  77:---");
						String insertquery11="INSERT INTO LEAVETRAN (EMPNO,LEAVECD,TRNDATE,TRNTYPE,APPLNO,FRMDT,TODT,LEAVEPURP," +
								"LADDR,LTELNO,DAYS,STATUS,PRJ_SRNO,APPLDT,LREASON,CREATED_BY,CREATED_DATE)" +
								" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						PreparedStatement Pstat22 = conn.prepareStatement(insertquery11);
						Pstat22.setInt(1,lbean.getEMPNO());
						Pstat22.setInt(2, 1);
						Pstat22.setString(3, lbean.getTRNDATE());
					    Pstat22.setString(4, "D");
					    Pstat22.setInt(5, applno3);
					    Pstat22.setString(6, lbean.getFRMDT());
					    Pstat22.setString(7, lbean.getTODT());
					    Pstat22.setInt(8, lbean.getLEAVEPURP());
					    Pstat22.setString(9, "Late Leave");
					    Pstat22.setDouble(10, lbean.getLTELNO());
					    Pstat22.setFloat(11, finalDays);
					    Pstat22.setString(12, "PENDING");
					    Pstat22.setString(13, lbean.getPrj_srno());
					    Pstat22.setString(14, lbean.getTRNDATE());
					    Pstat22.setString(15, "Late Leave Will be Deducted as CL,PL,LWP");
					    Pstat22.setInt(16, lbean.getCreated_by());
					    Pstat22.setString(17, lbean.getCreated_date());	    
					    Pstat22.executeUpdate();
					    result = "1";
					   // Pstat1.close();
					    System.out.println("late mark querrrrrrrrrrrrrryyyyyyyyyyyyyy for pl 2:--"+insertquery11);
					//	st5.execute(insertquery1);
					//	bal = rs15.getFloat("BAL");
						//totCr = rs15.getFloat("TOTCR");
						//String leave2="insert into LEAVEBAL VALUES ('"+rs1.getString("ATTD_DATE")+"',"+rs6.getInt("EMPNO")+",1,"+rs6.getFloat("BAL")+"-1,"+rs6.getFloat("TOTCR")+","+rs6.getFloat("TOTDR")+"+1)";
						//System.out.println("leave2"+ leave2);
						 //st4.execute(leave2);
						}
						}
					
				}
			
				}	
				else{	
			System.out.println("In Late Leave for cl  88 :---");
						String insertquery1="INSERT INTO LEAVETRAN (EMPNO,LEAVECD,TRNDATE,TRNTYPE,APPLNO,FRMDT,TODT,LEAVEPURP," +
								"LADDR,LTELNO,DAYS,STATUS,PRJ_SRNO,APPLDT,LREASON,CREATED_BY,CREATED_DATE)" +
								" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						PreparedStatement Pstat33 = conn.prepareStatement(insertquery1);
						Pstat33.setInt(1,lbean.getEMPNO());
						Pstat33.setInt(2,3);
						Pstat33.setString(3, lbean.getTRNDATE());
						Pstat33.setString(4, "D");
						Pstat33.setInt(5, applno);
						Pstat33.setString(6, lbean.getFRMDT());
					    Pstat33.setString(7, lbean.getTODT());
					    Pstat33.setInt(8, lbean.getLEAVEPURP());
					    Pstat33.setString(9, "Late Leave");
					    Pstat33.setDouble(10, lbean.getLTELNO());
					    Pstat33.setFloat(11, TotalDays);
					    Pstat33.setString(12, "PENDING");
					    Pstat33.setString(13, lbean.getPrj_srno());
					    Pstat33.setString(14, lbean.getTRNDATE());
					    Pstat33.setString(15, "Late Leave Will be Deducted as CL,PL,LWP");
					    Pstat33.setInt(16, lbean.getCreated_by());
					    Pstat33.setString(17, lbean.getCreated_date());	    
					    Pstat33.executeUpdate();
					    System.out.println("late mark querrrrrrrrrrrrrryyyyyyyyyyyyyy for cl 2:--"+insertquery1);
						//st4.execute(insertquery1);
					    result = "1";
					 //   Pstat1.close();
						
				//		bal = rs5.getFloat("BAL");
				//		totCr = rs5.getFloat("TOTCR");
						//String leave2="insert into LEAVEBAL VALUES ('"+rs1.getString("ATTD_DATE")+"',"+rs5.getInt("EMPNO")+",3,"+rs5.getFloat("BAL")+"-1,"+rs5.getFloat("TOTCR")+","+rs5.getFloat("TOTDR")+"+1)";
						//System.out.println("leave2"+ leave2);
						//st4.execute(leave2);
						}
				}
			/*else{
				result = "0";	
			}*/
					System.out.println("In Late Leave in  9:---");
		    conn.close();
		}
			}
			if(lbean.getFlag_editSanction()==468 && flag1!=2)
		    {
				System.out.println("067: "+flag1);
				flag =	setSanction(lbean.getEMPNO(), applno);
				result=Integer.toString(flag);
		    }
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return result;
			
	}
	
	/* This method has written to credit or debit the uninformed leaves */
	public boolean addExtraLeave(LMB lbean) 
	{
		boolean result=false;
		try
		{
			Connection con=ConnectionManager.getConnection();
			Statement st = con.createStatement();
			float TotalDays = LMH.getLeaveDays(lbean.getFRMDT(), lbean.getTODT(), lbean.getLEAVECD(), lbean.getHALFDAY());
		    
		    ResultSet rs3;
			String querybal="SELECT top 1 baldt, bal,totdr,TOTCR FROM LEAVEBAL a where a.EMPNO='"+lbean.getEMPNO()+"' AND a.LEAVECD = '" + lbean.getLEAVECD() +"' AND BALDT=(select distinct max(BALDT) from LEAVEBAL b where EMPNO='"+lbean.getEMPNO()+"' AND LEAVECD ='"+lbean.getLEAVECD()+"')";
		    //String querybal = "SELECT TOP 1 baldt, bal, totdr, totcr FROM LEAVEBAL WHERE LEAVECD="+lbean.getLEAVECD()+" AND EMPNO="+lbean.getEMPNO()+"   and   ORDER BY BALDT DESC";
		    rs3 = st.executeQuery(querybal);
		    String balDate = null;
			float balance = 0;
			float totdr = 0;
			float tcredit = 0;
			if(rs3.next()){
				final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				final Date date = format.parse(rs3.getString(1));
				final Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				balDate = format.format(calendar.getTime()); 
				//balDate = lbean.getFRMDT();
				//System.out.println("hello "+balDate);
				
				balance = rs3.getFloat(2);
				totdr = rs3.getFloat(3);						
				tcredit = rs3.getFloat(4);
			}
			rs3.close();
			
			if(balDate==null){
				balDate=lbean.getFRMDT();
			}
			
			
		    if(lbean.getTRNTYPE() == 'C'){ // To credit leaves in account
		    	
		    	/*if(lbean.getLEAVECD() == 4){
		    		
		    		if(totdr == 0){

		    			tcredit = TotalDays;
		    			balance = TotalDays;
		    		
		    		
		    		}
		    		else if (totdr != 0){
		    			tcredit = tcredit + TotalDays;
		    			balance = balance + TotalDays;
		    			
		    		}
		    	}*/
		    	 if(lbean.getLEAVECD() == 5){
		    		
		    		if(tcredit == 0){
		    			tcredit = TotalDays;
		    			balance = TotalDays;
		    		}
		    		else{
		    			tcredit = tcredit + TotalDays;
		    			balance = balance + TotalDays;
		    		}
		    	}
		    	
		    	
		    	
		    	
		    	
		    	else  {	
		    		if(balance!=0){
		    		balance = balance + TotalDays;
		    		tcredit = tcredit + TotalDays;
		    		
		    		}
		    		else{
		    			totdr=0;
		    			tcredit=TotalDays;
		    			balance=TotalDays;
		    		}
		    		if(tcredit == 0){
		    			tcredit =  TotalDays;
		    			
		    		}
		    		
		    		
		    		
		    		//System.out.println("value of tcredit 3 "+totdr);
		    	}
		    	
				String query2 = "INSERT INTO LEAVEBAL VALUES('"+balDate+"','"+lbean.getEMPNO()+"','"+lbean.getLEAVECD()+"','"+balance+"','"+tcredit+"','"+totdr+"')";
				
				
				st.executeUpdate(query2);
				
		    }
		    else if(lbean.getTRNTYPE() == 'D'){ // To debit leaves from account
		    	
		    	if(lbean.getLEAVECD() == 4){
		    		totdr = totdr + TotalDays;
		    	}
		    	else if(lbean.getLEAVECD() == 5){
	    		
	    			totdr = totdr + TotalDays;
	    			balance = balance - TotalDays;
	    		}
		    	else{
		    		balance = balance - TotalDays;
		    		totdr = totdr + TotalDays;	 
		    	}
				
				String query2 = "INSERT INTO LEAVEBAL VALUES('"+balDate+"','"+lbean.getEMPNO()+"','"+lbean.getLEAVECD()+"','"+balance+"','"+tcredit+"','"+totdr+"')";
				st.executeUpdate(query2);
				
		    }
		    Statement st1 = con.createStatement();
			ResultSet rs2 = null;
			rs2 = st1.executeQuery("SELECT MAX(APPLNO)+1 FROM LEAVETRAN");
			int applno = 0;
			if(rs2.next()){
				applno = rs2.getInt(1);
			}
			
		    String insertquery = "INSERT INTO LEAVETRAN (EMPNO,LEAVECD,TRNDATE,TRNTYPE,APPLNO,FRMDT,TODT,LREASON,DAYS,STATUS,PRJ_SRNO,CREATED_BY,CREATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement Pstat = con.prepareStatement(insertquery);
			EmpOffHandler eoh=new EmpOffHandler();
			EmpOffBean eob= eoh.getEmpOfficAddInfo(Integer.toString(lbean.getEMPNO()));
			int prjsrno=eob.getPrj_srno();
			Pstat.setInt(1,lbean.getEMPNO());
			Pstat.setInt(2,lbean.getLEAVECD());
			Pstat.setString(3, lbean.getTRNDATE());
		    Pstat.setString(4, String.valueOf(lbean.getTRNTYPE()));
		    Pstat.setInt(5, applno);
		    Pstat.setString(6, lbean.getFRMDT());
		    Pstat.setString(7, lbean.getTODT());
		    Pstat.setString(8, lbean.getLREASON());
		    Pstat.setFloat(9, TotalDays);
		    Pstat.setString(10, "SANCTION");
		    Pstat.setInt(11, prjsrno);
		    Pstat.setInt(12, lbean.getCreated_by());
		    Pstat.setString(13, lbean.getCreated_date());
		   
		    Pstat.executeUpdate();
		    result=true;
		    Pstat.close();
		    st.close();
		    con.close();
		    
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return result;
	}
	//akshay -ajit
	public String editLeave(LMB lbean,int appno) 
	{  
		
		int count=0;
		int flag1=0;
		String  result="";
		try
		{		
			System.out.println("In editLeave");
			Connection con=ConnectionManager.getConnection();
			float TotalDays = LMH.getLeaveDays(lbean.getFRMDT(), lbean.getTODT(), lbean.getLEAVECD(), lbean.getHALFDAY());
			Statement statement = null;
			Statement st1 = null;
			ResultSet rs1= null;
			ResultSet resultSetForOverlap=null;
			statement = con.createStatement();
			st1 = con.createStatement();
			rs1 = st1.executeQuery("SELECT TOP 1 * FROM LEAVEBAL WHERE LEAVECD="+lbean.getLEAVECD()+" AND EMPNO="+lbean.getEMPNO()+" ORDER BY BALDT DESC");
			float balance = 0.0f;
			System.out.println("TotalDays"+TotalDays);
			if(rs1.next())                            
			{
				balance = rs1.getFloat("BAL");
			}
			rs1.close();
			st1.close();
			System.out.println("balance"+balance);
			float days = balance - TotalDays;
			System.out.println("days"+days);
		
			Statement st = con.createStatement();
			Statement st2 = con.createStatement();
			System.out.println("dssegfd"+lbean.getLEAVECD());
			/////////////////////////////////////
			String overlap="select COUNT(*) as count from leavetran where (('"+lbean.getFRMDT()+"'" +
					" between FRMDT and TODT OR '"+lbean.getTODT()+"'" +
					 " between FRMDT and TODT ) or (FRMDT "  +
					 " between  '"+lbean.getFRMDT()+"' and '"+lbean.getTODT()+"' OR TODT" +
					 " between  '"+lbean.getFRMDT()+"' and'"+lbean.getTODT()+"'  ))" +
					 " and EMPNO="+lbean.getEMPNO()+"and status in ('SANCTION','PENDING') AND TRNTYPE='D' ";
			System.out.println(overlap);
			resultSetForOverlap=st2.executeQuery(overlap);
			if(resultSetForOverlap.next()){
				count=resultSetForOverlap.getInt("count");
				System.out.println("in between  "+count);
			}
			if(count>0)
			{
				System.out.println("In Edit Leave for allreaddy  ");
				statement.executeUpdate("update leavetran set status='PENDING' where applno="+appno+" ");
				System.out.println("update leavetran set status='PENDING' where applno="+appno+" ");
				flag1 =2;
				result="8";
				return result;
			}
			
			///////////////////////////////////////////
			
		
			

		ResultSet rs = st.executeQuery("SELECT DISTINCT(LEAVECD) FROM LEAVETRAN WHERE EMPNO="+lbean.getEMPNO()+"ORDER BY LEAVECD");
			while(rs.next()){
				System.out.println("In Edit Leave for pl  9.112:---");
				if(lbean.getLEAVECD() == rs.getInt(1) && days >= 0.0f || lbean.getLEAVECD() == 4){
					System.out.println(lbean.getLEAVECD()+""+rs.getInt(1));
					System.out.println("In Edit Leave for pl  10:---");
	
					String insertquery="Update LEAVETRAN set EMPNO = "+lbean.getEMPNO()+"," +
															"LEAVECD = "+lbean.getLEAVECD()+"," +
															"TRNDATE = '"+lbean.getTRNDATE()+"'," +
															"TRNTYPE = 'D'," +
															"FRMDT = '"+lbean.getFRMDT()+"'," +
															"TODT = '"+lbean.getTODT()+"'," +
															"LREASON ='"+lbean.getLREASON()+"'," +
			/*												"LADDR = 'UpdateLV' ," + */
															"LTELNO = "+lbean.getLTELNO()+"," +
															"DAYS = "+TotalDays+"," +
															"STATUS ='PENDING'," +
															"CREATED_BY='"+lbean.getCreated_by()+" ' ,  "+
															"CREATED_DATE='"+lbean.getCreated_date()+" ' "+
															"where APPLNO='"+lbean.getAPPLNO()+"'";
					
					Statement stmt=con.createStatement();
					stmt.executeUpdate(insertquery);
					result = "1";
					break;
				}
				else{
					result = "0";
				}
			}
			System.out.println("In Edit Leave for pl  12:---");
			rs.close();
			st.close();
		    con.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return result;
			
	}
	
	/****************************************************************************************/
	
	/*public int editSanctionLeave(LMB lbean) 
	{
		int result=0;
		try
		{
			int appno =Integer.parseInt(lbean.getAPPLNO());
		//boolean result1 = setCancel(appno);
		if(result1)
		{
			  String addEmpleave = addLeave(lbean);
			  
		}
		else
		{
			
		}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return result;
			
	}
	*/
	
	
	
			
	public  LMB getLeaveApp1(int empno,int appno)
	{
		ResultSet rs=null;  
		ArrayList<LMB> List1 =new ArrayList<LMB>();
		  LMB lbean1;
		    lbean1=null;
		try
		{
			String query = "";
			
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			LookupHandler objDesc=new LookupHandler();
			String Result1;
		    query="SELECT * FROM LEAVETRAN WHERE empno="+empno+" and APPLNO = "+appno;
		   
			rs=st.executeQuery(query);
			while(rs.next())
			{
				lbean1=new LMB();
				lbean1.setLEAVECD(rs.getString("leavecd")==null?0:rs.getInt("leavecd"));
				lbean1.setEMPNO(rs.getString("empno")==null?0:rs.getInt("empno"));
				lbean1.setTRNDATE(rs.getDate("trndate")==null?"":dateFormat(rs.getDate("trndate")));
				lbean1.setTRNTYPE(rs.getString("trntype").charAt(0));
				lbean1.setAPPLNO(rs.getString("applno")==null?"":rs.getString("applno"));
			//	lbean1.setBRCODE(rs.getString("brcode")==null?0:rs.getInt("brcode"));
				lbean1.setLEAVEPURP(rs.getString("leavepurp")==null?0:rs.getInt("leavepurp"));
				lbean1.setLREASON(rs.getString("LREASON")==null?"--":rs.getString("LREASON"));
				lbean1.setLADDR(rs.getString("LADDR")==null?"--":rs.getString("LADDR"));
				lbean1.setLTELNO(rs.getString("LTELNO")==null?0:rs.getLong("LTELNO"));
				lbean1.setAPPLDT(rs.getDate("APPLDT")==null?"":dateFormat(rs.getDate("APPLDT")));
				lbean1.setFRMDT(rs.getDate("FRMDT")==null?"":dateFormat(rs.getDate("FRMDT")));
				lbean1.setTODT(rs.getDate("TODT")==null?"":dateFormat(rs.getDate("TODT")));
				
				Lookup lkhp = new Lookup();
				lkhp = objDesc.getLookup("SAUTH-"+rs.getInt("SANCAUTH"));
				Result1 = lkhp.getLKP_DESC();
				lbean1.setSANCAUTH(Result1);
				lbean1.setOPR_CD(rs.getString("OPR_CD")==null?"":rs.getString("OPR_CD"));
				lbean1.setOFF_CD(rs.getString("OFF_CD")==null?"":rs.getString("OFF_CD"));
				lbean1.setSTATUS(rs.getString("STATUS")==null?"":rs.getString("STATUS"));
				lbean1.setSUBSTITUTE(rs.getString("SUBSTITUTE")==null?"":rs.getString("SUBSTITUTE"));
				lbean1.setNODAYS(rs.getString("DAYS")==null?0:rs.getFloat("DAYS"));
				//List1.add(lbean1);
			}
			rs.close();
			st.close();
			con.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}	
		return lbean1;
	}
	public  ArrayList<LMB> getLeaveApp(int empno,int appno)
	{
		ResultSet rs=null;  
		ArrayList<LMB> List1 =new ArrayList<LMB>();
		try
		{
			String query = "";
			
			Connection con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			LookupHandler objDesc=new LookupHandler();
			String Result1;
		    query="SELECT * FROM LEAVETRAN WHERE empno="+empno+" and APPLNO = "+appno;
					
			rs=st.executeQuery(query);
			while(rs.next())
			{
				LMB lbean1=new LMB();
				lbean1.setLEAVECD(rs.getString("leavecd")==null?0:rs.getInt("leavecd"));
				lbean1.setEMPNO(rs.getString("empno")==null?0:rs.getInt("empno"));
				lbean1.setTRNDATE(rs.getDate("trndate")==null?"":dateFormat(rs.getDate("trndate")));
				lbean1.setTRNTYPE(rs.getString("trntype").charAt(0));
				lbean1.setAPPLNO(rs.getString("applno")==null?"":rs.getString("applno"));
			//	lbean1.setBRCODE(rs.getString("brcode")==null?0:rs.getInt("brcode"));
				lbean1.setLEAVEPURP(rs.getString("leavepurp")==null?0:rs.getInt("leavepurp"));
				lbean1.setLREASON(rs.getString("LREASON")==null?"--":rs.getString("LREASON"));
				lbean1.setLADDR(rs.getString("LADDR")==null?"--":rs.getString("LADDR"));
				lbean1.setLTELNO(rs.getString("LTELNO")==null?0:rs.getLong("LTELNO"));
				lbean1.setAPPLDT(rs.getDate("APPLDT")==null?"":dateFormat(rs.getDate("APPLDT")));
				lbean1.setFRMDT(rs.getDate("FRMDT")==null?"":dateFormat(rs.getDate("FRMDT")));
				lbean1.setTODT(rs.getDate("TODT")==null?"":dateFormat(rs.getDate("TODT")));
				
				Lookup lkhp = new Lookup();
				lkhp = objDesc.getLookup("SAUTH-"+rs.getInt("SANCAUTH"));
				Result1 = lkhp.getLKP_DESC();
				lbean1.setSANCAUTH(Result1);
				lbean1.setOPR_CD(rs.getString("OPR_CD")==null?"":rs.getString("OPR_CD"));
				lbean1.setOFF_CD(rs.getString("OFF_CD")==null?"":rs.getString("OFF_CD"));
				lbean1.setSTATUS(rs.getString("STATUS")==null?"":rs.getString("STATUS"));
				lbean1.setSUBSTITUTE(rs.getString("SUBSTITUTE")==null?"":rs.getString("SUBSTITUTE"));
				lbean1.setNODAYS(rs.getString("DAYS")==null?0:rs.getFloat("DAYS"));
				List1.add(lbean1);
			}
			rs.close();
			st.close();
			con.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}	
		return List1;
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
	
	
	public ArrayList<LMB> getLeaveAppList(LMB lBean,String searchType) 
	{
		ArrayList<LMB> list =new ArrayList<>();
		String searchQuery = null;
		Connection con=ConnectionManager.getConnection();
	/*	System.out.println(lBean.getEMPNO());
		System.out.println(lBean.getEMPNO2());
		System.out.println(lBean.getFRMDT());
		System.out.println(lBean.getTODT());
		System.out.println(lBean.getSTATUS());
		*/
		if(searchType.equalsIgnoreCase("customSearch")){
			
			//if(lBean.getSTATUS().equalsIgnoreCase("ALL")){
				//System.out.println("if");
				searchQuery= "SELECT * FROM LEAVETRAN WHERE EMPNO = " +
				 		" '"+lBean.getEMPNO()+"'   AND  TRNDATE " +
				 		" between '"+lBean.getFRMDT()+"' AND '"+lBean.getTODT()+"' AND STATUS='SANCTION'  and LREASON <> 'CREDIT AFTER CANCELLATION'  order by frmdt desc ";
			}
			else{
		//	 status = "AND STATUS = '"+lBean.getSTATUS()+"'";
				searchQuery= "SELECT * FROM LEAVETRAN WHERE STATUS='PENDING' order by TRNDATE desc ";
			 
			
			}
		//}
		//else{
		//	 searchQuery="SELECT * FROM LEAVETRAN WHERE STATUS='"+lBean.getSTATUS()+"' order by frmdt desc" ;	
		//	 System.out.println("if else");
		//}
			 System.out.println(searchQuery);
		try
		{
			ResultSet rslist=null;
			Statement stmt1=con.createStatement();
			
			rslist=stmt1.executeQuery(searchQuery) ; 
			LMB leaveBean= null;
			
			while(rslist.next())
			{
			
				leaveBean=new LMB();
				leaveBean.setLEAVECD(rslist.getString("LEAVECD")==null?0:rslist.getInt("LEAVECD"));
				leaveBean.setEMPNO(rslist.getString("EMPNO")==null?0:rslist.getInt("EMPNO"));
				leaveBean.setTRNDATE(rslist.getDate("TRNDATE")==null?"":dateFormat(rslist.getDate("TRNDATE")));
				leaveBean.setTRNTYPE(rslist.getString("TRNTYPE").charAt(0));
				leaveBean.setAPPLNO(rslist.getString("APPLNO")==null?"":rslist.getString("APPLNO"));
			//	leaveBean.setBRCODE(rslist.getString("BRCODE")==null?0:rslist.getInt("BRCODE"));
				leaveBean.setLEAVEPURP(rslist.getString("leavepurp")==null?0:rslist.getInt("leavepurp"));
				leaveBean.setLREASON(rslist.getString("LREASON")==null?"":rslist.getString("LREASON"));
				leaveBean.setLADDR(rslist.getString("LADDR")==null?"":rslist.getString("LADDR"));
				leaveBean.setLTELNO(rslist.getString("LTELNO")==null?0:rslist.getLong("LTELNO"));
				leaveBean.setAPPLDT(rslist.getDate("APPLDT")==null?"":dateFormat(rslist.getDate("APPLDT")));
				leaveBean.setFRMDT(rslist.getDate("FRMDT")==null?"":dateFormat(rslist.getDate("FRMDT")));
				leaveBean.setTODT(rslist.getDate("TODT")==null?"":dateFormat(rslist.getDate("TODT")));
				String Result1;
				Lookup lkhp = new Lookup();
				LookupHandler objDesc1=new LookupHandler();
				lkhp = objDesc1.getLookup("SAUTH-"+rslist.getInt("SANCAUTH"));
				Result1 = lkhp.getLKP_DESC();
				leaveBean.setSANCAUTH(Result1);
				leaveBean.setSTATUS(rslist.getString("STATUS")==null?"":rslist.getString("STATUS"));
				leaveBean.setNODAYS(rslist.getString("DAYS")==null?0:rslist.getFloat("DAYS"));
				list.add(leaveBean);
			}
			rslist.close();
			stmt1.close();
			con.close();
		}
		catch(Exception e)
		{  
			e.printStackTrace();
		}
		
		return list;
	
	
	}
	/* This method has written for sanctioning leave application.
	   It updates status in leavetran table & make an entry into leavebal table of balance deduction */
	public int setSanction(int empno,int appNo) throws SQLException{
		System.out.println("in setSanction");
		Connection con = ConnectionManager.getConnection();
		String sql = "UPDATE LEAVETRAN SET STATUS='SANCTION' WHERE EMPNO="+empno+" AND APPLNO="+appNo;
		String selectdetail = "SELECT * FROM LEAVETRAN WHERE EMPNO="+empno+" AND APPLNO="+appNo;
		System.out.println("inside set sanction is:-"+sql);
		int flag = 0;
		float bal = 0.0f;
		String leavecd = "";
		float leaveDays = 0.0f;
		String Baldate = null;
		String Trndate ="";
		
		try{
			
			Statement stmt=con.createStatement();
			ResultSet rslt=stmt.executeQuery(selectdetail);
			while(rslt.next()){
				
				leavecd = rslt.getString("LEAVECD");
				leaveDays = rslt.getFloat("DAYS");
				Baldate = rslt.getString("TODT");
				Trndate = rslt.getString("TRNDATE");
				
				System.out.println("00:"+leavecd);
				System.out.println("01:"+leaveDays);
				System.out.println("02:"+Baldate);
			}
			
				
			ResultSet balChkRS = null;
			Statement st = con.createStatement();
			String balChk = "SELECT  bal-"+leaveDays+" FROM LEAVEBAL WHERE LEAVECD="+leavecd+" AND EMPNO="+empno+" ORDER BY baldt DESC,srno desc";
			//String balChk = "SELECT  bal-"+leaveDays+" FROM LEAVEBAL WHERE LEAVECD="+leavecd+" AND EMPNO="+empno+" ORDER BY srno DESC";
			//System.out.println("SELECT TOP 1 bal-"+leaveDays+" FROM LEAVEBAL WHERE LEAVECD="+leavecd+" AND EMPNO="+empno+" ORDER BY srno DESC");
			balChkRS = st.executeQuery(balChk);
			System.out.println("03:"+balChk);
			if(balChkRS.next()){
				
				
			    	bal = balChkRS.getFloat(1);
			    	System.out.println("the balance"+bal);
			}
			
			//if((bal >= 0) || (Integer.parseInt(leavecd) == 4) || (Integer.parseInt(leavecd) == 7) ||(Integer.parseInt(leavecd) == 10)){
			if((bal >= 0) || (Integer.parseInt(leavecd) == 4) || (Integer.parseInt(leavecd) == 7) ||(Integer.parseInt(leavecd) == 10) ||(Integer.parseInt(leavecd) == 9)){
				stmt.executeUpdate(sql);
				Statement stmt6=con.createStatement();
				Statement stmt7=con.createStatement();
				ResultSet rs3;
				
				String querybal = "SELECT top 1 bal,totdr,TOTCR FROM LEAVEBAL a where a.EMPNO='"+empno+"' AND a.LEAVECD = '" + leavecd +"' AND srno=(select top 1 srno from LEAVEBAL b where EMPNO='"+empno+"' AND LEAVECD ='"+leavecd+"' and BALDT <='"+Baldate+"' ORDER BY  baldt desc,srno DESC  ) ORDER BY  baldt desc,srno DESC";
				//String querybal = "SELECT top 1 bal,totdr,TOTCR FROM LEAVEBAL a where a.EMPNO='"+empno+"' AND a.LEAVECD = '" + leavecd +"' AND srno=(select  max(srno) from LEAVEBAL b where EMPNO='"+empno+"' AND LEAVECD ='"+leavecd+"' and BALDT <='"+Baldate+"'  ) ORDER BY  baldt desc,srno DESC";
			/*	String querybal = "SELECT top 1 bal,totdr,TOTCR FROM LEAVEBAL a where a.EMPNO='"+empno+"' AND a.LEAVECD = '" + leavecd +"' AND srno=(select distinct max(srno) from LEAVEBAL b where EMPNO='"+empno+"' AND LEAVECD ='"+leavecd+"' and BALDT <='"+Baldate+"'  ) ORDER BY srno DESC";*/
				System.out.println("04:-"+querybal);
				rs3 = stmt6.executeQuery(querybal);
				
				float balance = 0.0f;
				float totdr = 0.0f;
				float tcredit = 0.0f;
				if(rs3.next()){
					balance = rs3.getFloat("bal");	
					System.out.println("05: "+balance);
					totdr = rs3.getFloat("totdr");										
					tcredit = rs3.getFloat("TOTCR");	
					
					System.out.println("06: "+totdr);
					System.out.println("07: "+tcredit);
				}
				
				float totaldays = 0.0f;
				if(Integer.parseInt(leavecd) == 4){
					totaldays = totdr + leaveDays;
					balance = balance - leaveDays;
				}
				else{
					balance = balance - leaveDays;
					totaldays = totdr + leaveDays;	
					
					System.out.println("08: "+balance);
					System.out.println("09: "+totaldays);
				}
				
				//String Baldate = getDateMethod();				
				String query2 = "INSERT INTO LEAVEBAL VALUES('"+Baldate+"','"+empno+"','"+leavecd+"','"+balance+"','"+tcredit+"','"+totaldays+"')";
			//	System.out.println("INSERT INTO LEAVEBAL VALUES('"+Baldate+"','"+empno+"','"+leavecd+"','"+(balance-totaldays)+"','"+tcredit+"','"+(balance-totaldays)+"')");
				stmt6.executeUpdate(query2);
				System.out.println("10: "+query2);
				
				/*stmt7.execute(" update LEAVEBAL set bal=(bal-"+totdr+"),TOTDR=(TOTDR+"+totdr+") where empno="+empno+" and leavecd="+leavecd+" and baldt>'"+Baldate+"'");
				System.out.println(" update LEAVEBAL set bal=(bal-"+totdr+"),TOTDR=(TOTDR+"+totdr+") where empno="+empno+" and leavecd="+leavecd+" and baldt>'"+Baldate+"'");
				*/
					stmt7.execute(" update LEAVEBAL set bal=(bal-"+leaveDays+"),TOTDR=(TOTDR+"+leaveDays+") where empno="+empno+"  "+
				"and leavecd="+leavecd+" and baldt>'"+Baldate+"'");
					System.out.println("11:-- "+" update LEAVEBAL set bal=(bal-"+leaveDays+"),TOTDR=(TOTDR+"+leaveDays+") where empno="+empno+"  "+
							"and leavecd="+leavecd+" and baldt>'"+Baldate+"'");				
								
				if(!leavecd.equals("7") && !leavecd.equals("10")) {
			//	stmt7.execute(" update LEAVEBAL set bal=(bal-"+leaveDays+"),TOTDR=(TOTDR+"+leaveDays+") where empno="+empno+" and leavecd="+leavecd+" and baldt>='"+Baldate+"'");
				}
				System.out.println(" update LEAVEBAL set bal=(bal-"+leaveDays+"),TOTDR=(TOTDR+"+leaveDays+") where empno="+empno+" and leavecd="+leavecd+" and baldt>='"+Baldate+"'");
				
				

				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MMM-yyyy"); 
				Date tempDate=new Date();
				tempDate=simpleDateFormat.parse(Trndate); 
                 
				Trndate= outputDateFormat.format(tempDate);
				
				// for lwp days 301
				if(leavecd.equals("7")){
					
					String sqlQuery = "if exists (select * from paytran where EMPNO="+empno+" and TRNCD=301) " +
							    "update PAYTRAN set INP_AMT=INP_AMT+"+leaveDays+",CAL_AMT=CAL_AMT+"+leaveDays+"," +
							    		"NET_AMT=NET_AMT+"+leaveDays+" " +
							    "where  EMPNO="+empno+"  and TRNCD=301 and trndt between '"+ReportDAO.BOM(Trndate)+"'	AND	'"+ReportDAO.EOM(Trndate)+"'	" +
							    "else " +
							    "insert into PAYTRAN values ('"+ReportDAO.EOM(Trndate)+"',"+empno+",301,0,"+leaveDays+","+leaveDays+",0,0,"+leaveDays+",'',NULL,'"+ReportDAO.EOM(Trndate)+"','P')";
							
					System.out.println("12"+sqlQuery);
					 stmt6.executeUpdate(sqlQuery);
				}	
					
				
				// for suspend days 302
				if(leavecd.equals("10")){
					System.out.println("leavedays are"+leaveDays);
					String sqlQuery = "if exists (select * from paytran where EMPNO="+empno+" and TRNCD=302) " +
							    "update PAYTRAN set INP_AMT=INP_AMT+"+leaveDays+",CAL_AMT=CAL_AMT+"+leaveDays+"," +
							    		"NET_AMT=NET_AMT+"+leaveDays+" " +
							    "where  EMPNO="+empno+"  and TRNCD=302 and trndt between '"+ReportDAO.BOM(Trndate)+"'	AND	'"+ReportDAO.EOM(Trndate)+"'	" +
							    "else " +
							    "insert into PAYTRAN values ('"+ReportDAO.EOM(Trndate)+"',"+empno+",302,0,"+leaveDays+","+leaveDays+",0,0,"+leaveDays+",'',NULL,'"+ReportDAO.EOM(Trndate)+"','P')";
							
					System.out.println("13"+sqlQuery);
					stmt6.executeUpdate(sqlQuery);
				}	
				
				flag=1;
				// commented by P.H.patil Sir's order 
			/*	if (leavecd.equals("3"))
				{
				CallableStatement cst1= con.prepareCall("{call CLs_To_Pls(?,?) }");
				System.out.println("spc executed");
				cst1.setInt(1,empno);
				cst1.setString(2,Baldate);
				  
				
				ResultSet rs = cst1.executeQuery();
				rs.next();
				System.out.println("the return value is "+rs.getString("ReturnValue"));
				
				if	(rs.getString("ReturnValue").equals("1"))
						{
					flag=2;
					
						}
				
				
				con.commit();
				}*/
				
			}
			else{
				flag = 0;
			}
			con.close();
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("flag"+flag);
		return flag;
	}
	
	public boolean cancelLeaveApp(int empno,int appNo){
		
		boolean flag=false;
		Connection conn=ConnectionManager.getConnection();
		String sql="UPDATE LEAVETRAN SET STATUS='CANCEL' WHERE EMPNO="+empno+" AND APPLNO="+appNo;
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			
			stmt.close();
			conn.close();
			
			flag = true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}	
		return flag;
	}
	
	
	public String getDateMethod()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date date = new Date();
		String yourDate = dateFormat.format(date);
		return yourDate;
	}
	
	public static ArrayList<LeaveBalBean> getEmpLeaves(){
		
		Connection con = ConnectionManager.getConnection();
		Statement st = null;
		ResultSet rs = null;
		Date today = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		String now = format.format(today);
		
		ArrayList<LeaveBalBean> leaveBal = new ArrayList<LeaveBalBean>();
		
		try{
			st = con.createStatement();
			rs = st.executeQuery("select e.EMPNO , EMPCODE, SALUTE, FNAME, MNAME, LNAME, DOJ from EMPMAST e " +
									"where NOT EXISTS(select EMPNO from LEAVEBAL l where E.EMPNO = l.EMPNO ) and STATUS='A' order by empcode");
			while(rs.next()){
				
				Date jdate = rs.getDate(7);
				
				Calendar cal = Calendar.getInstance(); 
				cal.setTime(jdate);
				
				int date = cal.get(Calendar.DATE);
				int month = cal.get(Calendar.MONTH);
				int joinYear = cal.get(Calendar.YEAR);
				
				Calendar cal1 = Calendar.getInstance();
				int Year = cal1.get(Calendar.YEAR);
				
				month = month+4; //why 4 ? 3 for 3 months after joining & 1 to match with 12(months) bcoz calendar month starts with 0(jan) to 11(dec) 
				
				int CalMonth = (12-month)+3;//3 for from jan to mar
				float leaves =0.0f;
				leaves = (float)(1.75f*CalMonth);
				
				if(date > 0 && date <= 10){ // for emp joining date between 1 to 10
					leaves = leaves + 1.75f;
				}
				else if(date > 10 && date <= 20){ // for emp joining date between 10 to 20
					leaves = leaves + 1.0f;
				}
				else if(date > 20){ // for emp joining date after 20
					leaves = leaves + 0.75f;
				}
				
				/* To get the total days from joining to till date */
				Statement stmt = con.createStatement();
				ResultSet rs1 = stmt.executeQuery("SELECT DATEDIFF(day,'"+jdate+"','"+now+"') AS DiffDate");
				int days = 0;
				if(rs1.next()){
					days = Integer.parseInt(rs1.getString(1));
				}
				rs1.close();
				stmt.close();
				
				if(joinYear == Year && days > 90){
					LeaveBalBean lbean = new LeaveBalBean();
					lbean.setEMPNO(rs.getInt(1));
					lbean.setEMPCODE(rs.getString("EMPCODE"));
					lbean.setSALUTE(rs.getInt("SALUTE"));
					lbean.setFNAME(rs.getString("FNAME"));
					lbean.setMNAME(rs.getString("MNAME"));
					lbean.setLNAME(rs.getString("LNAME"));
					lbean.setLeaves(leaves);
				
					leaveBal.add(lbean);
				}
				else if(joinYear != Year){
					LeaveBalBean lbean = new LeaveBalBean();
					lbean.setEMPNO(rs.getInt(1));
					lbean.setEMPCODE(rs.getString("EMPCODE"));
					lbean.setSALUTE(rs.getInt("SALUTE"));
					lbean.setFNAME(rs.getString("FNAME"));
					lbean.setMNAME(rs.getString("MNAME"));
					lbean.setLNAME(rs.getString("LNAME"));
					lbean.setLeaves(21);
				
					leaveBal.add(lbean);
				}
			}
			
		rs.close();
		st.close();
		con.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return leaveBal;
	}

	public static boolean checkTransactions(int empno, int leavecd){
		boolean flag = false;
		Connection con = ConnectionManager.getConnection();
		Statement st = null;
		ResultSet rs = null;
		try{
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM LEAVETRAN WHERE EMPNO="+empno+" AND LEAVECD="+leavecd+" AND TRNTYPE='D'");
			if(rs.next()){
				flag = true;
			}
			else{
				flag = false;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public static float displayEmpLeaves(int empno){
		
		Connection con = ConnectionManager.getConnection();
		Statement st = null;
		ResultSet rs = null;
		Date today = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		String now = format.format(today);
		float leaves =0.0f;
		
		try{
			st = con.createStatement();
			rs = st.executeQuery("select DOJ from EMPMAST where empno = "+empno);

			if(rs.next()){
				
				Date jdate = rs.getDate(1);
				
				Calendar cal = Calendar.getInstance(); 
				cal.setTime(jdate);
				
				int date = cal.get(Calendar.DATE);
				int month = cal.get(Calendar.MONTH);
				int joinYear = cal.get(Calendar.YEAR);
				
				Calendar cal1 = Calendar.getInstance();
				int Year = cal1.get(Calendar.YEAR);
				
				month = month+4; //why 4 ? 3 for 3 months after joining & 1 to match with 12(months) bcoz calendar month starts with 0(jan) to 11(dec) 
				
				int CalMonth = (12-month)+3;//3 for from jan to mar
				
				leaves = (float)(1.75f*CalMonth);
				
				if(date > 0 && date <= 10){ // for emp joining date between 1 to 10
					leaves = leaves + 1.75f;
				}
				else if(date > 10 && date <= 20){ // for emp joining date between 10 to 20
					leaves = leaves + 1.0f;
				}
				else if(date > 20){ // for emp joining date after 20
					leaves = leaves + 0.75f;
				}
				
				/* To get the total days from joining to till date */
				Statement stmt = con.createStatement();
				ResultSet rs1 = stmt.executeQuery("SELECT DATEDIFF(day,'"+jdate+"','"+now+"') AS DiffDate");
				int days = 0;
				if(rs1.next()){
					days = Integer.parseInt(rs1.getString(1));
				}
				rs1.close();
				stmt.close();
				
				if(joinYear == Year && days > 90){
					return leaves;
					
				}
				else if(joinYear != Year){
					leaves = 21;
					
				}
			}
			
		rs.close();
		st.close();
		con.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return leaves;
	}

	
	
	public boolean updateLeaveBal(ArrayList<TranBean> projEmpNolist,Float[] values,String date)
	{
		Connection con = ConnectionManager.getConnection();
		Statement st=null,st1=null;
		ResultSet rs=null;
		boolean result = false;
		int i = 0;
		float days=Calculate.getDays(date);
		try 
		{
			PreparedStatement pst1=null;
			st=con.createStatement();
			st1=con.createStatement();
			
			for(TranBean tbn : projEmpNolist)
			{ 
					
				rs=st.executeQuery("select totdr from leavebal where empno="+tbn.getEMPNO()+" and baldt BETWEEN '"+ReportDAO.BOM(date)+"' AND '"+ReportDAO.EOM(date)+"'");
				if(rs.next())
				{
					if(values[i]==0)
					{
						st1.executeUpdate("delete from leavebal WHERE EMPNO="+tbn.getEMPNO()+" AND BALDT BETWEEN '"+ReportDAO.BOM(date)+"' AND '"+ReportDAO.EOM(date)+"'");
					}
					else
					{
					st1.executeUpdate("update leavebal set totdr="+values[i]+" ,baldt='"+date+"' WHERE EMPNO="+tbn.getEMPNO()+" AND BALDT BETWEEN '"+ReportDAO.BOM(date)+"' AND '"+ReportDAO.EOM(date)+"'");
					}
				}
				
				
				if(values[i]>0)
				{
				pst1 = con.prepareStatement(" IF EXISTS(SELECT * FROM leavebal WHERE EMPNO="+tbn.getEMPNO()+" AND BALDT BETWEEN '"+ReportDAO.BOM(date)+"' AND '"+ReportDAO.EOM(date)+"')" +
									"update leavebal set totdr="+values[i]+" ,baldt='"+date+"' WHERE EMPNO="+tbn.getEMPNO()+" AND BALDT BETWEEN '"+ReportDAO.BOM(date)+"' AND '"+ReportDAO.EOM(date)+"'" +
									" ELSE" +
									" INSERT INTO leavebal VALUES ('"+date+"', "+tbn.getEMPNO()+",1,0,0,"+values[i]+")");
				pst1.execute();
						
				}
				i++;
			}
			result = true;
			
			
			
			con.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	public float getLeaveBal(int empno,String date)
	{
		
		float leaves=0.0f;
		Connection con= ConnectionManager.getConnection();
		
		try
		{
			Statement st = con.createStatement();
			ResultSet rs= null;
			String query="Select totdr from leavebal Where EMPNO="+empno+" AND baldt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"'";
			rs= st.executeQuery(query);
			while(rs.next())
			{
				leaves+=rs.getFloat("totdr");
			}
			con.close();
		} 
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		
		
	return leaves;
	}	
	
	public float getLeaveBalOfEmployeeCodeWise(int empno,int leaveCode)
	{
		
		float leaves=0.0f;
		Connection con= ConnectionManager.getConnection();
		
		try
		{
			Statement st = con.createStatement();
			ResultSet rs= null;
		//	String query="select top 1 bal from LEAVEBAL where EMPNO="+empno+" and LEAVECD="+leaveCode+" and srno=(select MAX(srno) from LEAVEBAL where EMPNO="+empno+" and LEAVECD="+leaveCode+" )";
			String query= "SELECT TOP 1 bal FROM LEAVEBAL WHERE LEAVECD="+leaveCode+" AND EMPNO="+empno+"" +
			//	"and srno=(select MAX(srno)"+
			//	"from leavebal where  EMPNO="+empno+" and LEAVECD="+rs1.getInt(1)+") " +
			"and baldt =(select MAX(baldt) from leavebal where lEAVECD="+leaveCode+" " +
					" AND EMPNO="+empno+"   )"+
				" ORDER BY BALDT DESC,srno desc";
			//System.out.println(query);
			rs= st.executeQuery(query);
			while(rs.next())
			{
				leaves=rs.getFloat("bal");
			}
			con.close();
		} 
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		
		
	return leaves;
	}

	public boolean  setCancel(int applno,int buffer) throws SQLException {
		boolean result=false;
		System.out.println("In setCancel");
		LMB lmb=new LMB();
		Connection connection= ConnectionManager.getConnection();
		Statement statement=connection.createStatement();
		Statement stmt7=connection.createStatement();
		ResultSet resultSet=statement.executeQuery("select * from leavetran where applno="+applno+"");
		System.out.println("08 " +applno);
		if(resultSet.next()){
			lmb.setLEAVECD(resultSet.getString("leavecd")==null?0:resultSet.getInt("leavecd"));
			lmb.setEMPNO(resultSet.getString("empno")==null?0:resultSet.getInt("empno"));
			lmb.setTRNDATE(resultSet.getDate("trndate")==null?"":dateFormat(resultSet.getDate("trndate")));
			lmb.setNODAYS(resultSet.getString("DAYS")==null?0:resultSet.getFloat("DAYS"));
			lmb.setFRMDT(resultSet.getDate("FRMDT")==null?"":dateFormat(resultSet.getDate("FRMDT")));
			lmb.setTODT(resultSet.getDate("TODT")==null?"":dateFormat(resultSet.getDate("TODT")));
			lmb.setLADDR(resultSet.getString("LADDR")==null?"":resultSet.getString("LADDR"));
			if(buffer == 468)   //  this got set at edit button
			{
				System.out.println("09 " +applno);
				statement.executeUpdate("update leavetran set status='EditCancel' where applno="+applno+" ");
				System.out.println("update leavetran set status='EditCancel' where applno="+applno+" ");
			}
			else{
				if(buffer == 469)   //  this got set at edit button
			{
				System.out.println("009 " +applno);
				statement.executeUpdate("update leavetran set status='Cancel' where applno="+applno+" ");
				System.out.println("update leavetran set status='PendingCancel' where applno="+applno+" ");
			}
			
			
			else{
				System.out.println("10 ");
			statement.executeUpdate("update leavetran set status='REJECTED' where applno="+applno+" ");
			System.out.println("update leavetran set status='REJECTED' where applno="+applno+" ");

			
			}
			}
			ResultSet rs2 = null;
			rs2 = statement.executeQuery("SELECT MAX(APPLNO)+1 FROM LEAVETRAN");
			 applno = 0;
			if(rs2.next()){
				applno = rs2.getInt(1);
			}
			
			if(lmb.getLEAVECD()!=7 && lmb.getLEAVECD()!=10&& buffer!=469)// 
			{
				System.out.println("11 ");
			String insertquery="INSERT INTO LEAVETRAN (EMPNO,LEAVECD,TRNDATE,TRNTYPE,APPLNO,FRMDT,TODT,LEAVEPURP," +
					"LADDR,LTELNO,DAYS,STATUS,PRJ_SRNO,APPLDT,LREASON,CREATED_BY,CREATED_DATE)" +
					" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			System.out.println("Insert q1  "+insertquery);
			PreparedStatement Pstat = connection.prepareStatement(insertquery);
			Pstat.setInt(1,lmb.getEMPNO());
			Pstat.setInt(2,lmb.getLEAVECD());
			Pstat.setString(3, lmb.getTRNDATE());
		    Pstat.setString(4, "C");
		    Pstat.setInt(5, applno);
		    Pstat.setString(6, lmb.getFRMDT());
		    Pstat.setString(7, lmb.getTODT());
		    Pstat.setInt(8, lmb.getLEAVEPURP());
		    
		    if(lmb.getLADDR().equalsIgnoreCase("Late Leave"))
		    {
		    	 Pstat.setString(9, "Late LeaveC");
		    }
		    else
		    {
		    	Pstat.setString(9, lmb.getLADDR()==""?null:lmb.getLADDR());
		    }
		 //   Pstat.setString(9, lmb.getLADDR());
		  //  System.out.println("LADDR:--"+lmb.getLADDR);
		    Pstat.setDouble(10, lmb.getLTELNO());
		    Pstat.setFloat(11, lmb.getNODAYS());
		    Pstat.setString(12, "SANCTION");
		    Pstat.setString(13, lmb.getPrj_srno());
		    Pstat.setString(14, lmb.getTRNDATE());
		    Pstat.setString(15, "CREDIT AFTER CANCELLATION");
		    Pstat.setInt(16, lmb.getCreated_by());
		    Pstat.setString(17, lmb.getCreated_date());	    
		    Pstat.executeUpdate();
		    //result = "1";
		    Pstat.close();
		  /*  resultSet=statement.executeQuery("select * from leavebal where EMPNO="+lmb.getEMPNO()+" " +
		    		"and LEAVECD="+lmb.getLEAVECD()+" and srno=(select MAX(srno) from leavebal " +
		    		"where EMPNO="+lmb.getEMPNO()+" and LEAVECD="+lmb.getLEAVECD()+")");*/
		  /*  //ADD by akshay
		    Connection con01=ConnectionManager.getConnection();
			
			 String Baldate= ReportDAO.getServerDate();
	     	 String querybal = "SELECT top 1 bal,totdr,TOTCR FROM LEAVEBAL a where a.EMPNO="+lmb.getEMPNO()+" AND a.LEAVECD = "+lmb.getLEAVECD()+" AND srno=(select  max(srno) from LEAVEBAL b where EMPNO="+lmb.getEMPNO()+" AND LEAVECD ="+lmb.getLEAVECD()+" and  (BALDT >=CONVERT(char(10), CONVERT(date,'"+Baldate+"',105),126) or BALDT <=CONVERT(char(10), CONVERT(date,'"+Baldate+"',105),126) ) ) ORDER BY  baldt desc,srno DESC";
			
			 Statement stmt6=con01.createStatement();
			 ResultSet rs3;
			 System.out.println("04:-"+querybal);
			 rs3 = stmt6.executeQuery(querybal);
			
			  float balance1 = 0.0f;	
			  float totdr = 0.0f;
			  float tcredit = 0.0f;
			  if(rs3.next()){
				balance1 = rs3.getFloat("bal");	
				totdr = rs3.getFloat("totdr");										
				tcredit = rs3.getFloat("TOTCR");	
				System.out.println("05: "+balance1);
				System.out.println("06: "+totdr);
				System.out.println("07: "+tcredit);
			}
		
			 
			
		    
		    ///ADDD end here 
			  */
		    resultSet=statement.executeQuery("select * from leavebal where EMPNO="+lmb.getEMPNO()+" " +
		    		"and LEAVECD="+lmb.getLEAVECD()+" and srno=(select top 1 srno from leavebal " +
		    		"where EMPNO="+lmb.getEMPNO()+" and LEAVECD="+lmb.getLEAVECD()+"and BALDT <='"+lmb.getTODT()+"' order by baldt desc ,srno desc)");
		    
		    System.out.println("select * from leavebal where EMPNO="+lmb.getEMPNO()+" " +
		    		"and LEAVECD="+lmb.getLEAVECD()+" and srno=(select top 1 srno from leavebal " +
		    		"where EMPNO="+lmb.getEMPNO()+" and LEAVECD="+lmb.getLEAVECD()+"and BALDT <='"+lmb.getTODT()+"' order by baldt desc ,srno desc)");
		    //String query2 = "INSERT INTO LEAVEBAL VALUES('"+balDate+"','"+lbean.getEMPNO()+"','"+lbean.getLEAVECD()+"','"+balance+"','"+tcredit+"','"+totdr+"')";
		    if(resultSet.next()){
		    lmb.setBALDT(resultSet.getDate("BALDT")==null?"":dateFormat(resultSet.getDate("BALDT")));
			lmb.setEMPNO(resultSet.getString("EMPNO")==null?0:resultSet.getInt(("EMPNO")));
			lmb.setLEAVECD(resultSet.getString("LEAVECD")==null?0:resultSet.getInt("LEAVECD"));
			lmb.setBAL(resultSet.getString("BAL")==""?0.0f:resultSet.getFloat("BAL"));
			lmb.setTOTCR(resultSet.getString("TOTCR")==""?0.0f:resultSet.getFloat("TOTCR"));
			lmb.setTOTDR(resultSet.getString("TOTDR")==""?0.0f:resultSet.getFloat("TOTDR"));
			lmb.setSRNO(resultSet.getInt("srno"));
		System.out.println("akshay srno "+resultSet.getInt("srno"));
			// By PH Patil Sir..
	//String insertIntoLeavebal = "INSERT INTO LEAVEBAL VALUES('"+lmb.getTODT()+"','"+lmb.getEMPNO()+"','"+lmb.getLEAVECD()+"','"+(lmb.getNODAYS()+lmb.getBAL())+"','"+(lmb.getTOTCR()+lmb.getNODAYS())+"','"+lmb.getTOTDR()+"')";//lmb.getTOTDR()
			
			// By Akshay.. 13-03-18..
	 String insertIntoLeavebal = "INSERT INTO LEAVEBAL VALUES('"+lmb.getTODT()+"','"+lmb.getEMPNO()+"','"+lmb.getLEAVECD()+"','"+(lmb.getBAL())+"','"+(lmb.getTOTCR())+"','"+lmb.getTOTDR()+"')";// getTOTDR-nodays
	 System.out.println("insertIntoLeavebal : "+insertIntoLeavebal);
	 statement.executeUpdate(insertIntoLeavebal);
	 stmt7.executeUpdate(" update LEAVEBAL set bal=(bal+"+lmb.getNODAYS()+"),TOTDR=(TOTDR-"+lmb.getNODAYS()+") where empno="+lmb.getEMPNO()+"  "+
			"and leavecd="+lmb.getLEAVECD()+" and baldt >='"+lmb.getTODT()+"'and  srno != "+lmb.getSRNO() );
	
				System.out.println("11:-- "+" update LEAVEBAL set bal=(bal+"+lmb.getNODAYS()+"),TOTDR=(TOTDR-"+lmb.getNODAYS()+") where empno="+lmb.getEMPNO()+"  "+
			                        "and leavecd="+lmb.getLEAVECD()+" and baldt >='"+lmb.getTODT()+"' and  srno != "+lmb.getSRNO() );
			
/*				balance1=balance1+lmb.getNODAYS();
	String query2 = "INSERT INTO LEAVEBAL VALUES((CONVERT(char(10), CONVERT(date,'"+Baldate+"',105),126)),"+lmb.getEMPNO()+","+lmb.getLEAVECD()+",'"+balance1+"','"+tcredit+"','"+totdr+"')";
					stmt6.executeUpdate(query2);*/
	
		    }
		    }
			else {
				if(lmb.getLEAVECD()==7){
					System.out.println("im im 7");
				statement.executeUpdate("update paytran set inp_amt=inp_amt-"+lmb.getNODAYS()+"," +
						"cal_amt=cal_amt-"+lmb.getNODAYS()+",net_amt=net_amt-"+lmb.getNODAYS()+" "+ 
						"where empno="+lmb.getEMPNO()+" and trncd=301");
				System.out.println("update paytran set inp_amt=inp_amt-"+lmb.getNODAYS()+"," +
						"cal_amt=cal_amt-"+lmb.getNODAYS()+",net_amt=net_amt-"+lmb.getNODAYS()+" "+ 
						"where empno="+lmb.getEMPNO()+" and trncd=301");
				}else {
					if(lmb.getLEAVECD()==10){
					System.out.println("im im 10");
					statement.executeUpdate("update paytran set inp_amt=inp_amt-"+lmb.getNODAYS()+"," +
							"cal_amt=cal_amt-"+lmb.getNODAYS()+",net_amt=net_amt-"+lmb.getNODAYS()+" "+ 
							"where empno="+lmb.getEMPNO()+" and trncd=302");
					System.out.println("update paytran set inp_amt=inp_amt-"+lmb.getNODAYS()+"," +
							"cal_amt=cal_amt-"+lmb.getNODAYS()+",net_amt=net_amt-"+lmb.getNODAYS()+" "+ 
							"where empno="+lmb.getEMPNO()+" and trncd=302");
					}
				}
			}
			
		result=true;
		}
		return result;
		
		
	}	
	
	public Integer  getMatLeave(int empno)  throws ParseException
	{
		System.out.println("In matLeave of LMH");
		 int flag = 0;
	   try
	   {
		 
	    	 Connection conn=ConnectionManager.getConnection();
	    	 String matLvlist=("select COUNT(*) AS COUNT from empmast where empno="+empno+" and GENDER <>'F'");
	    	 Statement stmt=conn.createStatement();
	    	 ResultSet rslt=stmt.executeQuery(matLvlist);
	    	
			 while(rslt.next())
			 {
				 conn.commit();
				 flag=rslt.getInt("COUNT");
			 }
			 conn.close();
	    } 
	   	catch (SQLException e) 
	   	{
		   e.printStackTrace();
		}
	return flag;
	}
	
	
	public Integer  getConsecutive_CL_Leave(int empno,String fromdate,String todate)  throws ParseException
	{
		 System.out.println("In consecutive_CL LMH");
		 int flags = 0;
	   try
	   {
		   
		   System.out.println("In consecutive_CL LMH");
		   System.out.println(fromdate);
		   System.out.println(todate);
	    	 Connection conn=ConnectionManager.getConnection();
	    	 String conLvlist=("select days  as cntleaves   from leavetran where empno ="+empno+" and leavecd = 3" +	
	    			 	"and status in ('SANCTION','PENDING') AND TRNTYPE='D'" + 
	    			 "	and ( '"+fromdate+"' = dateadd(dd, 1, todt )" +
	    			 	"or '"+todate+"' = dateadd(dd, -1, frmdt)  )");
	    	 Statement stmt=conn.createStatement();
	    	 ResultSet rslt=stmt.executeQuery(conLvlist);
	    	System.out.println("con_LV:-"+conLvlist);
			 while(rslt.next())
			 {
				 conn.commit();
				 flags=rslt.getInt("cntleaves");
			 }
			 conn.close();
	    } 
	   	catch (SQLException e) 
	   	{
		   e.printStackTrace();
		}
	return flags;
	}
	

public Integer  checkLeaveYear(String fromdate,String todate)  throws ParseException
	{
		 System.out.println("In checkLeaveYear LMH");
		 int flags = 0;
	   try
	   {
		   
		   System.out.println("In checkLeaveYear LMH");
		   System.out.println(fromdate);
		   System.out.println(todate);
	    	 Connection conn=ConnectionManager.getConnection();
	    	
	    	 String leaveYrlist=("select COUNT(*) AS COUNT1 from LEAVE_YEAR  where '"+fromdate+"' between LEAVE_START_DATE " +
	    	 		"and LEAVE_END_DATE and '"+fromdate+"' <= LEAVE_END_DATE  and STATUS ='OPEN'");
	    	 System.out.println("con_LV:-"+leaveYrlist);
	    	 Statement stmt=conn.createStatement();
	    	 ResultSet rslt=stmt.executeQuery(leaveYrlist);
	    	System.out.println("con_LV:-"+leaveYrlist);
			 while(rslt.next())
			 {
				 conn.commit();
				 flags=rslt.getInt("COUNT1");
				 
			 }
			 conn.close();
	    } 
	   	catch (SQLException e) 
	   	{
		   e.printStackTrace();
		}
	return flags;
	}
	

public String  getOpenYear(String Date)  throws ParseException
{
	 System.out.println("In checkLeaveYear LMH");
	 String flags = "";
   try
   {
	   System.out.println("In OpenYear LMH");
    	 Connection conn=ConnectionManager.getConnection();
    	 String chkyrList="select  distinct STATUS from  leave_year where LEAVE_START_DATE='"+Date+"' and status ='open'";

    	 System.out.println("OpenYear1:-"+chkyrList);
    	 Statement stmt=conn.createStatement();
    	 ResultSet rslt=stmt.executeQuery(chkyrList);
    	System.out.println("chkyrList:-"+chkyrList);
		 while(rslt.next())
		 {
			 conn.commit();
			 flags=rslt.getString("STATUS");
			 
		 }
		 conn.close();
    } 
   	catch (SQLException e) 
   	{
	   e.printStackTrace();
	}
return flags;
}
public String  getEndYear()  throws ParseException
{
	 System.out.println("In getEndYear LMH");
	 String flags = "";
   try
   {
	
    	 Connection conn=ConnectionManager.getConnection();
    	 System.out.println("update LEAVE_YEAR set STATUS ='CLOSE' where STATUS ='OPEN'");
    	 System.out.println("update leavemass set STATUS ='N' where STATUS ='A'");
    	 System.out.println("INSERT INTO  LEAVE_YEAR([LEAVE_TYPE],[LEAVE_START_DATE],[LEAVE_END_DATE], " +
    			" [STATUS],[USERID],[STATUS_UPDATE_DATE])VALUES('ALL','"+ReportDAO.BOYForJanToDec1( ReportDAO.getSysDate())+"' ,'"+ ReportDAO.EOYForJanToDec(ReportDAO.getSysDate())+"','OPEN' ,0,'"+ReportDAO.BOYForJanToDec1( ReportDAO.getSysDate())+"')");
    	 
    	 Statement stmt=conn.createStatement();
    	 stmt.executeUpdate("update LEAVE_YEAR set STATUS ='CLOSE' where STATUS ='OPEN'");
    	 stmt.executeUpdate("update leavemass set STATUS ='N' where STATUS ='A'");
    	 stmt.executeUpdate("INSERT INTO  LEAVE_YEAR([LEAVE_TYPE],[LEAVE_START_DATE],[LEAVE_END_DATE], " +
    			" [STATUS],[USERID],[STATUS_UPDATE_DATE])VALUES('ALL','"+ReportDAO.BOYForJanToDec1( ReportDAO.getSysDate())+"' ,'"+ ReportDAO.EOYForJanToDec(ReportDAO.getSysDate())+"','OPEN' ,0,'"+ReportDAO.BOYForJanToDec1( ReportDAO.getSysDate())+"')");
    	 
    
		 conn.close();
    } 
   	catch (SQLException e) 
   	{
	   e.printStackTrace();
	}
return flags;
}
public Integer  chk_Leave_betDate(int empno,String fromdate,String todate,int appno)  throws ParseException
{
	 System.out.println("In chk_Leave_betDate LMH");
	 int flags = 0;
   try
   {
	   
	   System.out.println("In chk_Leave_betDate LMH");
	   System.out.println(fromdate);
	   System.out.println(todate);
	   System.out.println(appno);
    	 Connection conn=ConnectionManager.getConnection();
    	
    	 String overlap="";
    	 if(appno==0000000)// for apply leave   by akshay
    	 {
    	  overlap="select COUNT(*) as count from leavetran where (('"+fromdate+"'" +
					" between FRMDT and TODT OR '"+todate+"'" +
					 " between FRMDT and TODT ) or (FRMDT "  +
					 " between  '"+fromdate+"' and '"+todate+"' OR TODT" +
					 " between  '"+fromdate+"' and'"+todate+"'  ))" +
					 " and EMPNO="+empno+"and status in ('SANCTION','PENDING') AND TRNTYPE='D' ";
    	 }
    	 else
    	 { // for sanction leave by akshay
    	  overlap="select COUNT(*) as count from leavetran where (('"+fromdate+"'" +
					" between FRMDT and TODT OR '"+todate+"'" +
					 " between FRMDT and TODT ) or (FRMDT "  +
					 " between  '"+fromdate+"' and '"+todate+"' OR TODT" +
					 " between  '"+fromdate+"' and'"+todate+"'  ))" +
					 " and EMPNO="+appno+"and status in ('SANCTION','PENDING') AND TRNTYPE='D' AND APPLNO!="+appno;
   	     }
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

public Integer  chkApplDate(int empno,String trnDate) throws ParseException
{
	 Integer flag = null;
   try
   {
	   	 Connection conn=ConnectionManager.getConnection();
    	 String paytranlist=("select ISNULL( max(EMPNO),0)as flag from paytran where empno="+empno+" and TRNCD=101 and trndt ='"+ReportDAO.EOM(trnDate)+"'");
    	 Statement stmt=conn.createStatement();
    	 System.out.println("paytranlist:: "+paytranlist);
    	 ResultSet rslt=stmt.executeQuery(paytranlist);
    	 
		 while(rslt.next())
		 {
			 conn.commit();
			 flag = rslt.getInt("flag");
		 }
		 conn.close();
    } 
   	catch (SQLException e) 
   	{
	   e.printStackTrace();
	}
    return flag;
}

public boolean checkLeaveStatus(int appno, int empno) {
	 boolean flag = false;
	 String fdate = "";
	   try
	   {
		   	 Connection conn=ConnectionManager.getConnection();
		   	 
		   	 String appDate = "SELECT FRMDT FROM LEAVETRAN WHERE APPLNO="+appno;
	    	 Statement stmt=conn.createStatement();
	    	 System.out.println("paytranlist:: "+appDate);
	    	 ResultSet rslt=stmt.executeQuery(appDate);
			 while(rslt.next())
			 {
				 fdate = rslt.getString("FRMDT");
			 }
			 
			// String checkStatus = "select FRMDT from leavetran where EMPNO = 9 and "
			 String checkStatus = "select FRMDT from leavetran where EMPNO = "+empno+" and "
			 		+ " FRMDT between '"+ReportDAO.BOM1(fdate)+"' and '"+fdate+"' and FRMDT<>'"+fdate+"' and STATUS='pending'";
			 
			 rslt = stmt.executeQuery(checkStatus);	
			 if(rslt.next())
			 {
				 flag = true ;
			 }
			 
			 conn.close();
	    } 
	   	catch (SQLException e) 
	   	{
		   e.printStackTrace();
		}
	    return flag;
}
}
