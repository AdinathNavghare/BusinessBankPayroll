package payroll.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import payroll.Core.Calculate;
import payroll.Core.ErrorLog;
import payroll.Core.ReportDAO;
import payroll.Model.LeaveEncashmentBean;
import payroll.Model.RetirementBean;;

public class RetirementHandler {

	public ArrayList<RetirementBean> getRetirementList(String date){	
		
		Statement st;
		ResultSet rs = null;	
		Connection con = null;
		RetirementBean rBean = new RetirementBean(); 
		
		ArrayList<RetirementBean> list= new ArrayList<RetirementBean>();
		
		String query =	" select * from (select e.empno ,CONVERT(NVARCHAR(100),E.LNAME+' '+ E.FNAME+' '+ E.MNAME)as name, e.retirementdate,    " +
						" l.bal  , p.INP_AMT ,p.trncd  from EMPMAST e , leavebal l, paytran p where e.retirementDate " +
						" between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' and  l.leavecd = 1 and e.EMPNO = l.empno and l.srno = " +
						" (select MAX(srno) from leavebal where  EMPNO = e.empno and LEAVECD = 1 )and e.EMPNO " +
						" not in( select empno from leavetran where LEAVEPURP = 4 and EMPNO = e.empno and TRNTYPE ='d' and " +
						" STATUS ='sanction' and LEAVECD = 1 and TRNDATE between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' ) and" +
						" p.EMPNO = e.empno and TRNDT  between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' and TRNCD in(101,102,138) " +
						" group by e.empno, E.FNAME,E.LNAME,E.MNAME,l.bal,p.INP_AMT ,p.trncd ,e.retirementdate) as Amount " +
						" PIVOT( max([INP_AMT]) FOR trncd IN ([101],[102],[138])) as am ";
		
		System.out.println("query"+query);
		try
		{
			con = ConnectionManager.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);
			
			float amount = 0;
			float days = 0;
			
			while(rs.next())
			{
				rBean.setEmpno(rs.getInt("empno"));
				rBean.setName(rs.getString("name"));
				rBean.setDays(rs.getFloat("bal"));
				rBean.setBasic(rs.getFloat("101"));
				rBean.setDa(rs.getFloat("102"));
				rBean.setVda(rs.getFloat("138"));
				
				System.out.println("days is @ " + (rs.getFloat("138")) );
				
				amount =rs.getFloat("101") +rs.getFloat("102")+rs.getFloat("138");
				float monthdays = Calculate.getDays(date);	
				days = rs.getFloat("bal");
		
				if(amount!=0)
				{
					amount = amount/monthdays; 			
					amount = amount * days;
				}
	
				rBean.setAmount(amount);
				rBean.setRetirmentDate(rs.getString("retirementdate"));
				
				list.add(rBean);
			}
			
			rs.close();
			st.close();
			con.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return list;
	}

	
	// To Encash Employee Amount in Paytran... for 146 By harshal
	public boolean encashEmpListforRetirement(RetirementBean LEB,int loggedEmployeeNo) {
			
			boolean result = false;
		    boolean first;
		    ResultSet rs = null;
		    int applno = 0;
		    String prj_srno ="";
		    
			Connection connection = ConnectionManager.getConnection();
			EmpAttendanceHandler EAH = new EmpAttendanceHandler();
			String srvDate = EAH.getServerDate();
			String year = srvDate.substring(7,11);
			try
			{	
				//insert encash record to Encashment table for histroy
				String EncashQuery ="insert into encashment (empno,basic,DA,vda, days , amount ,encashdate, currentdate , user_login) values (?,?,?,?,?,?,?,?,?)";
				System.out.println("insertQuery"+EncashQuery);
				
				PreparedStatement insertSt1 = connection.prepareStatement(EncashQuery);
				insertSt1.setInt(1,LEB.getEmpno());
				insertSt1.setFloat(2,LEB.getBasic());
				insertSt1.setFloat(3, LEB.getDa());
				insertSt1.setFloat(4, LEB.getVda());
				insertSt1.setFloat(5, LEB.getDays());
				insertSt1.setFloat(6, LEB.getAmount());
				insertSt1.setString(7, LEB.getRetirmentDate());
				insertSt1.setString(8, srvDate);
				insertSt1.setInt(9, loggedEmployeeNo);
				
				insertSt1.executeUpdate();
				
				
				//insert Into Paytran for encash amt by code 146
				String insertQuery = "INSERT INTO PAYTRAN (TRNDT,EMPNO,TRNCD,SRNO,INP_AMT,CAL_AMT,ADJ_AMT,ARR_AMT,NET_AMT,CF_SW,USRCODE,UPDDT,STATUS) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
				
				PreparedStatement insertSt = connection.prepareStatement(insertQuery);
				//	insertSt.setString(1,ReportDAO.EOM("01-jan-"+year));
				insertSt.setString(1,srvDate);
				insertSt.setInt(2,LEB.getEmpno()); 
				insertSt.setInt(3,146); // transaction code for Retirement Encash
				insertSt.setInt(4,0);
				insertSt.setFloat(5,LEB.getAmount());			
				insertSt.setFloat(6,0);
				insertSt.setFloat(7,0);				
				insertSt.setFloat(8,0);	
				insertSt.setFloat (9, LEB.getAmount());
				insertSt.setString (10,"");		
				insertSt.setInt (11,loggedEmployeeNo );
				insertSt.setString (12,srvDate);
				insertSt.setString (13,"P");	
				
				System.out.println("insertQuery"+insertQuery);
				
				insertSt.executeUpdate();
				
				
				// select applno from leavetran and prjrsno from emptran
				
				String qurey =	"	select e.PRJ_SRNO , MAX (l.APPLNO) as applno from EMPTRAN e ,leavetran l where e.EMPNO = 820 and e.SRNO = " +
								"  (select MAX (SRNO) FROM EMPTRAN where EMPNO = "+LEB.getEmpno()+" )group by e.PRJ_SRNO ";
				
				PreparedStatement st = connection.prepareStatement(qurey);
				rs = st.executeQuery();
				while(rs.next()){
					applno = rs.getInt("APPLNO");
					prj_srno = rs.getString("PRJ_SRNO");
				}
				
				// insert Into Leavtran for encash amt by code 146		
				
				String leavtranQuery =	" INSERT INTO leavetran (LEAVECD	,EMPNO	,TRNDATE	,TRNTYPE,	APPLNO	,LEAVEPURP	,LREASON  ,APPLDT	,FRMDT	,TODT ,STATUS ,DAYS	,PRJ_SRNO)" +
										" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
				PreparedStatement LeavtranSt = connection.prepareStatement(leavtranQuery);
				
				LeavtranSt.setInt(1,1);
				LeavtranSt.setInt(2,LEB.getEmpno()); 
				LeavtranSt.setString(3,srvDate); // transaction Date
				LeavtranSt.setString(4,"D");
				LeavtranSt.setInt(5,(applno+1)); // always increase by 1 when insert			
				LeavtranSt.setInt(6,4); // leave Purpose
				LeavtranSt.setString(7,"Retirement Encash");				
				LeavtranSt.setString (8,srvDate);	// appdt	
				LeavtranSt.setString (9,ReportDAO.BOM(srvDate) ); // frmdt
				LeavtranSt.setString (10,ReportDAO.EOM(srvDate)); //todt
				LeavtranSt.setString (11,"SANCTION");
				LeavtranSt.setFloat(12,LEB.getDays());
				LeavtranSt.setString (13,prj_srno);
					
				System.out.println("leavtranQuery"+leavtranQuery);
					
				LeavtranSt.executeUpdate();
				
				
				// insert Into leavbal for encash amt by code 146		
				
				String leavbalQuery =	" INSERT INTO leavebal (BALDT,EMPNO,LEAVECD,BAL,TOTCR,TOTDR )VALUES(?,?,?,?,?,?)";
				
				PreparedStatement leavbalSt = connection.prepareStatement(leavbalQuery);
				
				leavbalSt.setString(1,srvDate);
				leavbalSt.setInt(2,LEB.getEmpno()); 
				leavbalSt.setInt(3,1); // leave for retirement encash leave against pl
				leavbalSt.setFloat(4,0);			
				leavbalSt.setFloat (5,LEB.getDays());	
				leavbalSt.setFloat (6,LEB.getDays() ); 
				
					
				System.out.println("leavbalQuery"+leavbalQuery);
					
				leavbalSt.executeUpdate();
				
				/*//update status in after encash(insert) into paytran Leavtran 
				String updatequery = "update LEAVETRAN set STATUS = 'SANCTION' " +
							   		 "where EMPNO = "+LEB.getEmpno()+" and STATUS = '1' and TRNTYPE ='D' and LEAVEPURP= '4' and " +
							         "TRNDATE between '1-jan-"+year+"' and '31-jan-"+year+"' ";
				System.out.println("updatequery"+updatequery);
				
				PreparedStatement updateSt = connection.prepareStatement(updatequery);
				updateSt.executeUpdate();
				*/
				
				first = true;
				
				insertSt.close();
				LeavtranSt.close();
				leavbalSt.close();
				st.close();
				
				rs.close();
				connection.close();
				
				if(first){
					result = true;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				ErrorLog errorLog=new ErrorLog();
				errorLog.errorLog("LeaveEncashmentHandler: ERROR INSERTING IN paytran METHOD: encashEmpList. FOR PAGE: Encashmentlist.jsp", e.toString());
			}
			return result;
			
		}
		
		

	
	
}
