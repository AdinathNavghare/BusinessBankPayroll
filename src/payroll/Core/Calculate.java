
package payroll.Core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import payroll.DAO.ConnectionManager;
import payroll.DAO.Sal_DetailsHandler;
import payroll.DAO.TranHandler;

public class Calculate {

	public static int[][] TotAmt = new int[1000][301];
	/*public static int[][] TotAmt = new int[101][301];
	public static int[][] TotAmt1 = new int[101][1000];
	*/
	public static int[][] TotAmt1 = new int[1000][1000];
	public static String[][] pAcNo = new String[301][4];
	static int cnt = 0;
	public static String v_gender="";
	private static ErrorLog EL=new ErrorLog();
	public static float suspendDays =0f;
	public static float lopDays=0f;
	public static int pay_cal(String BgnDate, String empLst, String G_UserId) {
/*for(int i=0;i<=999;i++)
	for(int j=0;i<301;i++)
		TotAmt[i][j]=0;
*/				int neg_emp_no=0;

		Connection Cn01 = ConnectionManager.getConnection();
		
		Statement st67;
		try {
			st67 = Cn01.createStatement();
			 
			 st67.executeUpdate("TRUNCATE TABLE PAYTRAN_BUFFER");
		        
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
       
		String emplist1[] =new String[1];
		if(empLst.contains(","))
		{
				 emplist1=empLst.split(",");
		}
		else
		{
			 emplist1[0]=empLst;
		}
		for(int c=0;c<emplist1.length;c++)
		{
		String	empList=emplist1[c];	
		
		String EmpStr = "";
		Statement st = null;
		ResultSet emp = null;
		float Wrk_Days = 0.0f;
		float Wrk_Amt = 0.0f;
		float onAmt1 = 0.0f;
		// System.out.println(empList);
		EmpStr = "select emp.*, t.acno, t.branch, t.grade, t.desig from empmast emp, emptran t where t.empno = emp.empno and "
				+ "t.effdate = ( SELECT MAX(E2.EFFDATE) FROM EMPTRAN E2 WHERE e2.empno = emp.empno and E2.EFFDATE <= '"
				+ BgnDate
				+ "')"
				+ "and t.SRNO =(select max (e1.SRNO) from emptran e1 where e1.empno = emp.empno) "
				+ "and emp.empno in ("
				+ empList
				+ ") "
				// +"and emp.status = 'A' "
				+ "and ( dol is null or ( DATEPART(MM, dol) >= "
				+ getMonth(BgnDate)
				+ " and datepart(year,dol) >= "
				+ getYear(BgnDate) + "))" + " order by emp.empno";

		System.out.println("ak0001  "+EmpStr);
		Connection Cn = ConnectionManager.getConnection();
		try {
			
			
			
			st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Statement st1 = Cn.createStatement();
			Statement st2 = Cn.createStatement();
			Statement st3 = Cn.createStatement();
			Statement st4 = Cn.createStatement();
			Statement del = Cn.createStatement();
			
			emp = st.executeQuery(EmpStr);
	//		System.out.println("Calculation Started....");
			if (!emp.next()) {
				//System.out.println("NO employees found");
				return -1; // NO employees found
			}

			// Wrk_Days = getDays(BgnDate); // get maximum number of days in
			// month
			// ***** Chk date of joining
			emp.beforeFirst();

			/*
			 * if(emp.getString("DOJ") && ReportDAO.BOM(BgnDate) &&
			 * emp.getString("DOJ") <= ReportDAO.EOM(BgnDate)){ Wrk_Days =
			 * Wrk_Days - Integer.parseInt(emp.getString("DOJ").substring(0,1));
			 * }
			 */
			while (emp.next()) {
				
				
				Wrk_Days = getDays(BgnDate);
				//System.out.println(" Wrk_Days 1 "+Wrk_Days );
				
				// System.out.println("1"+Wrk_Days);
				Statement st5 = Cn.createStatement();
				ResultSet rs7 = null;
			v_gender=emp.getString("gender").equalsIgnoreCase("M")?"MALE":"FEMALE";
				rs7 = st5.executeQuery("select " + Wrk_Days   // work_days-day(DOJ)
						+ " - DAY(DOJ)+1 from empmast where empno ="
						+ emp.getInt("empno") + " and doj between cast('"
						+ ReportDAO.BOM(BgnDate) + "' as DATE) and cast('"
						+ ReportDAO.EOM(BgnDate) + "' as DATE)");
				if (rs7.next()) {
					/*
					 * int day = rs7.getInt(1); Wrk_Days = day;
					 */
					Wrk_Days = rs7.getFloat(1);
					// System.out.println(rs7.getFloat(1));
					// System.out.println("2"+Wrk_Days);
				}
				//System.out.println(" Wrk_Days 2 "+Wrk_Days );
				// === For Date of Leaving in same Month
				rs7 = st5.executeQuery("select " + Wrk_Days + " - (DAY(cast('"
						+ ReportDAO.EOM(BgnDate)
						+ "' as DATE))- DAY(DOL)) from empmast where empno ="
						+ emp.getInt("empno") + " and dol between cast('"
						+ ReportDAO.BOM(BgnDate) + "' as DATE) and cast('"
						+ ReportDAO.EOM(BgnDate) + "' as DATE)");
				if (rs7.next()) {
					/*
					 * int day = rs7.getInt(1); Wrk_Days = day;
					 */
					Wrk_Days = rs7.getFloat(1);
				}

				//System.out.println(" Wrk_Days 3 "+Wrk_Days );
				
				int empno = emp.getInt("EMPNO");
				neg_emp_no=empno;
			

				 deduction_insert(empno, G_UserId, BgnDate, Cn);

				String TrnStr = "SELECT * FROM PAYTRAN WHERE TRNCD=301 AND EMPNO="
						+ empno;

				ResultSet trn = st1.executeQuery(TrnStr);
				if (trn.next()) {
					 lopDays = trn.getFloat("INP_AMT");
				//	 System.out.println("ak wrk days00  "+lopDays);
				//	 System.out.println("ak wrk days01  "+Wrk_Days);
					Wrk_Days = Wrk_Days - lopDays;
		//			 System.out.println("ak wrk days02  "+Wrk_Days);
					
					
					
					st2.executeUpdate("UPDATE PAYTRAN SET CAL_AMT=" + lopDays
							+ ",ARR_AMT=0, ADJ_AMT=0, NET_AMT=" + lopDays
							+ ",TRNDT='" + BgnDate + "',UPDDT='" + BgnDate
							+ "',STATUS='P' WHERE TRNCD=301 AND EMPNO=" + empno);
				}
				
				
				
				 TrnStr = "SELECT * FROM PAYTRAN WHERE TRNCD=302 AND EMPNO="
						+ empno;

				 trn = st1.executeQuery(TrnStr);
				if (trn.next()) {
					 suspendDays = trn.getFloat("INP_AMT");
		//			 System.out.println("ak wrk days04"+suspendDays);
					Wrk_Days = Wrk_Days - suspendDays;
				//	System.out.println("ak wrk days05"+Wrk_Days);
					
					st2.executeUpdate("UPDATE PAYTRAN SET CAL_AMT=" + suspendDays
							+ ",ARR_AMT=0, ADJ_AMT=0, NET_AMT=" + suspendDays
							+ ",TRNDT='" + BgnDate + "',UPDDT='" + BgnDate
							+ "',STATUS='P' WHERE TRNCD=302 AND EMPNO=" + empno);
				}
				
				
				
				
				TrnStr = "SELECT PAYTRAN.* FROM PAYTRAN WHERE TRNCD=999 AND EMPNO="
						+ empno;
				trn = st1.executeQuery(TrnStr);
				if (trn.next()) {
					st2.executeUpdate("UPDATE PAYTRAN SET CAL_AMT=0, INP_AMT=0, ARR_AMT=0, ADJ_AMT=0, NET_AMT=0,STATUS='P' WHERE TRNCD=999 AND EMPNO="
							+ empno);
				}
				// String CdmStr =
				// "SELECT * FROM CDMAST WHERE GROSS_YN = 'Y' AND ISNULL(FREQUENCY,' ') <> 'C' ORDER BY TRNCD";
				String CdmStr = "(SELECT *,j='1' FROM CDMAST WHERE GROSS_YN = 'Y' AND ISNULL(FREQUENCY,' ') <> 'C' and TRNCD=199) "
						+ "union SELECT *,j='2' FROM CDMAST WHERE GROSS_YN = 'Y' AND ISNULL(FREQUENCY,' ') <> 'C' and TRNCD <> 199 order by j,SRNO,TRNCD";
//System.out.println("heloo cdmast"+CdmStr);
				ResultSet CDM = st1.executeQuery(CdmStr);
				while (CDM.next()) {
					int trncd = CDM.getInt("TRNCD");

					int etype = CDM.getString("EMPTYPE") == null ? 0 : CDM
							.getInt("EMPTYPE");

					String naStr = "SELECT * FROM EMP_NA_CODE WHERE EMPNO = "
							+ empno + " AND TRNCD = " + trncd;
					ResultSet naRs = st3.executeQuery(naStr);
					if (naRs.next()) {
						if (naRs.getInt("FIXAMT") == 0) {
							Wrk_Amt = 0;
							TrnStr = "SELECT  PAYTRAN.* FROM PAYTRAN WHERE TRNCD = "
									+ trncd + " AND EMPNO = " + empno;
							trn = st4.executeQuery(TrnStr);
							if (trn.next()) {
								st2.executeUpdate("update PAYTRAN set cal_amt = "
										+ Wrk_Amt
										+ ", usrcode = '"
										+ G_UserId
										+ "',"
										+ " upddt = '"
										+ BgnDate
										+ "', trndt = '"
										+ BgnDate
										+ "',"
										+ "STATUS='P' "
										+ "where TRNCD = "
										+ trncd + " AND EMPNO = " + empno);
								
								System.out.println("CHECK QUERY UPDATE : update PAYTRAN set cal_amt = "+ Wrk_Amt+ ", usrcode = '"+ G_UserId+ "',"+ " upddt = '"
										+ BgnDate+ "', trndt = '"+ BgnDate+ "',"+ "STATUS='P' "+ "where TRNCD = "+ trncd + " AND EMPNO = " + empno);
								
								
							}
						} else {
							Wrk_Amt = naRs.getInt("FIXAMT");
						}
					} else {
						/*
						 * onAmt1=onAmount(trncd, empno, BgnDate,
						 * emp.getInt("DA_SCHEME"), Wrk_Days);
						 * Wrk_Amt=checkSlab(trncd, BgnDate, onAmt1,
						 * emp.getInt("DA_SCHEME"));
						 */
						
						//System.out.println("before onamout emptype " + etype);
						float totaldays=lopDays+suspendDays;
						
				//		System.out.println("trncd trncd trncd"+trncd);
				
						
					//	if(totaldays<5)//4
						if(totaldays<0)//4
						{
								onAmt1 = onAmount(trncd, empno, BgnDate, etype,
								Wrk_Days,lopDays,suspendDays, Cn);
								System.out.println("trncd from onamt totaldays<5 "+trncd);
						}
						else
						{
								onAmt1 = onAmount(trncd, empno, BgnDate, etype,
								Wrk_Days, Cn);
							//	System.out.println("trncd from onamt "+trncd);
						}
						
						if(trncd==201)
						{
							Wrk_Amt = checkSlab(trncd, BgnDate, onAmt1,
									etype, empno, Cn);
							//@ksh chnges by ph sir ;
							Wrk_Amt=Math.round(Wrk_Amt);
							//Wrk_Amt=Math.round(Wrk_Amt+0.09);
						//	System.out.println("trncd from slab in if "+trncd);
						//	System.out.println("Wrk_Amt from slab in if "+Wrk_Amt);
						}
						else
						{
							if(empno==732){
								System.out.println("for checkSlab");
							}
						Wrk_Amt = Math.round(checkSlab(trncd, BgnDate, onAmt1,
								etype, empno, Cn));
						if(empno==732){
						System.out.println("trncd from slab "+trncd);
						System.out.println("Wrk_Amt from slab "+Wrk_Amt);
						}
						}
					}
					/*System.out.println("heyyy "+emp.getString("DOJ"));
					System.out.println("heyyy "+getMonth(BgnDate));
					System.out.println("heyyy "+trncd);*/
					///// for 220 deduction may month
			/*	if ((getMonth(BgnDate) == 5 && trncd==220  ))//&& Wrk_Amt>=342
				{ 
					           String dob=	emp.getString("DOB");
					           int agecount= 0;
					           agecount= agecal(dob);
					          // select * from PAYTRAN_STAGE where TRNCD=220 and EMPNO=  and TRNDT ='2017-05-31' 
					           System.out.println("heyyyagecount "+agecount);
					           if(agecount>50)
					           {
					        	   Wrk_Amt = 12;
					           }else
					           {
					        	   Wrk_Amt = 342;
					           }
							    // Wrk_Amt = 0;
						
					}
					else
					{
						if(trncd==220)
						{
						Wrk_Amt = 0;
						}
					}*/
					
					//akshay nikam added for mlwf desig 6 to 9 
					
					         int desig=-2;
					         desig=Integer.parseInt(emp.getString("desig"));      
					//         System.out.println("ak11   "+Wrk_Amt);
						//	 if((desig>9 || desig<6) && (trncd==211))
					    	 if((desig==1 || desig==2 || desig==10 || desig==11) && (trncd==211))
					        {
						//		  System.out.println("ak11.2   "+Wrk_Amt);
						     Wrk_Amt = 0;
						//     System.out.println("ak11.3   "+Wrk_Amt);
					        }
					  //       System.out.println("ak12   "+Wrk_Amt);

					   if ((CDM.getString("FREQUENCY") == null ? "" : (CDM
							.getString("FREQUENCY"))).equalsIgnoreCase("H")) {
						if (!(getMonth(BgnDate) == 6 || getMonth(BgnDate) == 12)) {
							Wrk_Amt = 0;
						}
					}
				  //       System.out.println("ak13   "+Wrk_Amt);

					if ((CDM.getString("FREQUENCY") == null ? "" : (CDM
							.getString("FREQUENCY"))).equalsIgnoreCase("B")) {
						TotAmt[emp.getInt("BRANCH")][CDM.getInt("TRNCD")] = TotAmt[emp
								.getInt("BRANCH")][CDM.getInt("TRNCD")]
								+ Math.round(Wrk_Amt);
						Wrk_Amt = 0;
					}
					
					System.out.println("work amount in calulation for trncd= " +trncd +" is "+Wrk_Amt);
					
					if (Wrk_Amt != 0) {
						TrnStr = "SELECT PAYTRAN.* FROM PAYTRAN WHERE TRNCD = "
								+ trncd + " AND  EMPNO = " + empno;
						trn = st4.executeQuery(TrnStr);
						if (trn.next()) {
							
							/*System.out.println(" IFUPDATE PAYTRAN SET CAL_AMT = "
									+ Wrk_Amt + ", USRCODE = '" + G_UserId
									+ "', UPDDT = '" + BgnDate
									+ "' , TRNDT = '" + BgnDate
									+ "' ,STATUS='P' " + "WHERE  TRNCD = "
									+ trncd + " AND EMPNO = " + empno);*/
							
							st3.executeUpdate("UPDATE PAYTRAN SET CAL_AMT = "
									+ Wrk_Amt + ", USRCODE = '" + G_UserId
									+ "', UPDDT = '" + BgnDate
									+ "' , TRNDT = '" + BgnDate
									+ "' ,STATUS='P' " + "WHERE  TRNCD = "
									+ trncd + " AND EMPNO = " + empno);
							
							System.out.println("Calculate TRNCD : "+trncd);
						
							if(trncd==101){
								
								System.out.println("Payroll Calculation vda_Calc_latest");
								String DaCalculateDate = "01"+BgnDate.substring(2, 11);
								VDA_Calculation.vda_Calc_latest(DaCalculateDate);	
							}
						
						/*	System.out.println("CHECK QUERY 123 : UPDATE PAYTRAN SET CAL_AMT = "+ Wrk_Amt + ", USRCODE = '" + G_UserId+ "', UPDDT = '" + BgnDate
									+ "' , TRNDT = '" + BgnDate+ "' ,STATUS='P' " + "WHERE  TRNCD = "+ trncd + " AND EMPNO = " + empno);   */
							
						} else {
							
						st3.executeUpdate("INSERT INTO PAYTRAN (TRNDT, EMPNO, TRNCD, CAL_AMT, USRCODE, UPDDT, ARR_AMT, ADJ_AMT, NET_AMT, SRNO, CF_SW,STATUS) values("
									+ "'"
									+ BgnDate
									+ "',"
									+ empno
									+ ","
									+ trncd
									+ ","
									+ Wrk_Amt
									+ ",'"
									+ G_UserId
									+ "','" + BgnDate + "',0,0,0,0,'','P')");
						}
						TrnStr = "SELECT  PAYTRAN.* FROM PAYTRAN WHERE TRNCD = 999 AND EMPNO = "
								+ empno;
						if (trncd < 200) {
							trn = st4.executeQuery(TrnStr);
							if (trn.next()) {
								st3.executeUpdate("UPDATE PAYTRAN SET CAL_AMT = CAL_AMT + "
										+ Wrk_Amt
										+ " WHERE TRNCD = 999 AND EMPNO = "
										+ empno);
							} else {
								st3.executeUpdate("INSERT INTO PAYTRAN (TRNDT, EMPNO, TRNCD, CAL_AMT, USRCODE, UPDDT, ARR_AMT, ADJ_AMT, NET_AMT, SRNO, CF_SW,STATUS) values("
										+ "'"
										+ BgnDate
										+ "',"
										+ empno
										+ ",999,"
										+ Wrk_Amt
										+ ",'"
										+ G_UserId
										+ "','" + BgnDate + "',0,0,0,0,'','P')");
							} // else close
						} // if(trncd < 200)
					} // if(Wrk_Amt!=0)
					else {
						
						TrnStr = "SELECT  PAYTRAN.* FROM PAYTRAN WHERE TRNCD = "
								+ trncd + " AND  EMPNO = " + empno;
						ResultSet strn = st4.executeQuery(TrnStr);
						if (strn.next()) {
							
							/*System.out.println("ELSE UPDATE PAYTRAN SET CAL_AMT = "
									+ Wrk_Amt + ", NET_AMT = " + Wrk_Amt
									+ ", USRCODE = '" + G_UserId
									+ "', UPDDT = '" + BgnDate
									+ "' , TRNDT = '" + BgnDate
									+ "',STATUS='P'" + "  WHERE   TRNCD = "
									+ trncd + " AND  EMPNO = " + empno);*/
							
							
							st3.executeUpdate("UPDATE PAYTRAN SET CAL_AMT = "
									+ Wrk_Amt + ", NET_AMT = " + Wrk_Amt
									+ ", USRCODE = '" + G_UserId
									+ "', UPDDT = '" + BgnDate
									+ "' , TRNDT = '" + BgnDate
									+ "',STATUS='P'" + "  WHERE   TRNCD = "
									+ trncd + " AND  EMPNO = " + empno);
							
						
						/*	System.out.println("CHECK QUERY 1234  UPDATE PAYTRAN SET CAL_AMT = "+ Wrk_Amt + ", NET_AMT = " + Wrk_Amt+ ", USRCODE = '" + G_UserId+ "', UPDDT = '" + BgnDate
									+ "' , TRNDT = '" + BgnDate+ "',STATUS='P'" + "  WHERE   TRNCD = "+ trncd + " AND  EMPNO = " + empno);  */
						
						}
						
					
						if(trncd==101){
							
							System.out.println("In ELSE Payroll Calculation vda_Calc_latest");
							String DaCalculateDate = "01"+BgnDate.substring(2, 11);
							VDA_Calculation.vda_Calc_latest(DaCalculateDate);	
						}
			
						
					}

					if (trncd > 200 && trncd < 300) {
						pAcNo[trncd][1] = CDM.getString("SUBSYS");
						pAcNo[trncd][2] = "" + CDM.getInt("ACNO");
					}
					del.executeUpdate("update paytran set status = 'P',TRNDT='"
							+ BgnDate + "',UPDDT='" + BgnDate
							+ "' where trncd in (127,135) and empno = " + empno);
					//del.executeUpdate("delete from PAYTRAN where INP_AMT= 0 and CAL_AMT= 0 and NET_AMT = 0 and CF_SW <> '*'and trncd <> 999 ");
				} // while 2 close
				
				// for harsh construnction EPF calculation change for 15000 
				
				
				
				Statement epf_cal=Cn.createStatement();
				String epf_cal_query="update PAYTRAN 	set " +
									"CAL_AMT=((select p1.cal_amt from PAYTRAN p1 where p1.EMPNO=st.EMPNO and  p1.TRNCD=201 and  p1.TRNDT between '"+ReportDAO.BOM(BgnDate)+"' and '"+ReportDAO.EOM(BgnDate)+"')" +
									" - ( select p2.cal_amt from PAYTRAN p2 where p2.EMPNO=st.EMPNO and p2.TRNCD=232  and  p2.TRNDT between '"+ReportDAO.BOM(BgnDate)+"' and '"+ReportDAO.EOM(BgnDate)+"'))   	" +
											"from PAYTRAN st	  where  TRNCD=231  and TRNDT between '"+ReportDAO.BOM(BgnDate)+"' and '"+ReportDAO.EOM(BgnDate)+"'	" +
											//"and EMPNO in(select EMPNO from PAYTRAN 	where  EMPNO="+emp.getInt("EMPNO")+" and  TRNCD=201  and TRNDT between '"+ReportDAO.BOM(BgnDate)+"' and '"+ReportDAO.EOM(BgnDate)+"')";
											"and EMPNO in(select EMPNO from PAYTRAN 	where  EMPNO>181 and  TRNCD=201  and TRNDT between '"+ReportDAO.BOM(BgnDate)+"' and '"+ReportDAO.EOM(BgnDate)+"')";
				
				System.out.println( "Nikhil PF Check : "+epf_cal_query);
				epf_cal.executeUpdate(epf_cal_query);
				// changes end
			
				Statement st66 = Cn.createStatement();
				
			
				st66.executeUpdate("INSERT INTO PAYTRAN_BUFFER (EMPNO) values(" 
						    +  emp.getInt("empno")+ ")");
				if(empno==732){
						System.out.println("paytran buffer==  INSERT INTO PAYTRAN_BUFFER (EMPNO) values(" 
						    +  emp.getInt("empno")+ ") ");
				}	
		// Add 	suspendDays and lopDays = 0 
					suspendDays = 0;
					lopDays = 0;
			} // while 1 close
			
				
		
		
			st2.close();
			st3.close();
			st4.close();
		
		} catch (Exception e) {
			e.printStackTrace();
			EL.errorLog("Calculate.java -- pay_cal()", e.toString());
			return -2; // Some Error or Exception has occurred
		}
		System.out.println("Calculate Check 1");
		int flag =Update_Tran(emp, G_UserId, BgnDate, Cn);
		System.out.println("Calculate Check 2");
		//int flag=0;
		if (flag != 0) 
		{
			
			if(flag==-3)		//to continue the calculation even if emp goes negative sal...@
			{
				try 
				{
					Statement neg_sal=Cn01.createStatement();
					
					neg_sal.executeUpdate("IF EXISTS(select * from negative_paytran where empno="+neg_emp_no+" and trndt='"+BgnDate+"' ) " +
										    " begin delete from  negative_paytran where empno="+neg_emp_no+" and trndt='"+BgnDate+"' end" +
											" insert into negative_paytran select * from paytran where empno="+neg_emp_no+" and trndt='"+BgnDate+"' ");	
				
				} catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
			}
			else
			{
			return flag; // Salary of empno= flag has gone negative
			}
		}System.out.println("Calculate Check 7");
		TranHandler TH = new TranHandler();
		TH.updateFinalizeStatus(BgnDate);
//		System.out.println("Process of Payroll Calculation is completed for emp="+empList);
		try {
			Cn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		return 0;
	}

	// ------------- Method to check for ChkSlab ------------------------------
	public static float checkSlab(int trncd, String dt, float WrkAmt,
			int empType, int empno, Connection Cn) {
		float result = 0.00f;
		float slabAmt = 0.00f;
		int yearFeb=Integer.parseInt(dt.substring(7,11));

		/*
		 *  if(trncd == 202 || trncd == 201 || trncd == 221) { empType = 0; }
		 */
	
		String vempType = "" + empType;
		String SlbStr = "SELECT * FROM SLAB WHERE EMP_CAT = " + empno
				+ " AND TRNCD = " + trncd + " AND EFFDATE = "
				+ "( SELECT MIN(S.EFFDATE) FROM SLAB S WHERE S.EMP_CAT = "
				+ empno + " AND TRNCD = " + trncd + " and effdate >= '" + dt
				+ "' ) and " + +WrkAmt
				+ " BETWEEN  FRMAMT AND TOAMT ORDER BY SRNO ";
		try {
			Statement st0 = Cn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st0.executeQuery(SlbStr);
			if (!rs.next()) {
				st0.close();

			} else {
				vempType = "" + empno;
	//			System.out.println("i am in checkSlab 01  "+empno);
			}
		} catch (Exception e) {
			e.printStackTrace();
			EL.errorLog("Calculate.java -- checkSlab()", e.toString());
		}

		SlbStr = "SELECT * FROM SLAB WHERE EMP_CAT = " + vempType
				+ " AND TRNCD = " + trncd + " AND EFFDATE = "
				+ "( SELECT MIN(S.EFFDATE) FROM SLAB S WHERE S.EMP_CAT = "
				+ vempType + " AND TRNCD = " + trncd + " and effdate >= '" + dt
				+ "' ) and " + WrkAmt
				+ " BETWEEN  FRMAMT AND TOAMT ORDER BY SRNO ";

		if (trncd == 199 || trncd == 127) {
			result = WrkAmt;
			return result;
		}

		if (WrkAmt == 0) {
			return 0;
		}
		
		
		
		try {
			Statement st = Cn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet Slb = st.executeQuery(SlbStr);
			if (!Slb.next()) {
	//			System.out.println("No Slab Found into chk slab for trncd="+trncd);
				return WrkAmt;
			}
			Slb.beforeFirst();
			if (Slb.next()) {
			
				
				if ((Slb.getString("FIXAMT") == null ? 0 : Slb.getInt("FIXAMT")) == 0
						&& (Slb.getString("PER") == null ? 0 : Slb
								.getFloat("PER")) == 0
						&& (Slb.getString("MINAMT") == null ? 0 : Slb
								.getInt("MINAMT")) == 0
						&& (Slb.getString("MAXAMT") == null ? 0 : Slb
								.getInt("MAXAMT")) == 0) {
					return 0;
				}
				if (Slb.getInt("FIXAMT") != 0) {
					//System.out.println("FIXED AMT +++++++====="+Slb.getInt("FIXAMT"));
					result = Slb.getInt("FIXAMT");
					/*if (Slb.getInt("TRNCD") == 202
							&& dt.equalsIgnoreCase("25-Feb-2016")
							&& result > 175)
						result = 300;*/
					String monthFeb=dt.substring(3,6);
					
					if(monthFeb.equalsIgnoreCase("Feb")){
				    	if (Slb.getInt("TRNCD") == 202
							
							&& result > 175)
						result = 300;
					}
					
					java.util.Date convertedDate=null;
					java.util.Date startdate=null;
					
					SimpleDateFormat sourceFormat = new SimpleDateFormat("dd-MMM-yyyy");
					
			        convertedDate = sourceFormat.parse(dt);
			        startdate= sourceFormat.parse("01-Mar-2016");

					if (v_gender.equalsIgnoreCase("FEMALE") && trncd==202 && WrkAmt<=10000 && convertedDate.after(startdate))
					{
						result=0;
					}
					//System.out.println("into slab= ");
					//System.out.println("trncd ="+trncd);
					//System.out.println("RETURN FIXED AMT======"+result);
					return result;
				}
				slabAmt = WrkAmt;

				if ((Slb.getString("PER") == null ? 0 : Slb.getFloat("PER")) != 0) {
					
					slabAmt = WrkAmt * Slb.getFloat("PER") / 100;
					if(trncd==201||trncd==202)
					{
		//			System.out.println("helooo don take tension "+slabAmt+" \n " + SlbStr+"\n"+empno);
					}
					 
					
				}
				if ((Slb.getString("MINAMT") == null ? 0 : Slb.getInt("MINAMT")) != 0) {
					if (slabAmt < Slb.getInt("MINAMT")) {
						slabAmt = Slb.getInt("MINAMT");
					}
				}
				if ((Slb.getString("MAXAMT") == null ? 0 : Slb.getInt("MAXAMT")) != 0) {
					if (slabAmt > Slb.getInt("MAXAMT")) {
						slabAmt = Slb.getInt("MAXAMT");
					}
				}
				
				
				

				result = slabAmt;
			}
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
			EL.errorLog("Calculate.java -- chkSlab()", e.toString());
		}
		
		
		return result;
		
	}

	// ------------- Method to check for Onamt ------------------------------
	public static float onAmount(int trncd, int empno, String BgnDate,
			int empType, float WrkDays, Connection Cn) // added one parameter suspend
														// WrkDays
	{
		//System.out.println("Welcome ONAMT Method");
		
		float WrkAmt = 0.00f;
		float Basic = 0.00f;
		float result = 0.00f;
		Statement st = null,st11 = null;
		ResultSet rs = null,rs1 = null;
		
		//System.out.println("i am in onAmount1");
		
		
		
		if (trncd == 199 || trncd == 127) {
			try {
				st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);

				if (trncd == 199) {
					rs = st.executeQuery("SELECT  INP_AMT FROM PAYTRAN WHERE TRNCD  = 199 AND EMPNO = "
							+ empno);
				} else {
					rs = st.executeQuery("SELECT  INP_AMT FROM PAYTRAN WHERE TRNCD  = 127 AND EMPNO = "
							+ empno);
				}

				if (rs.next()) {
					Basic = rs.getFloat("INP_AMT") * WrkDays / getDays(BgnDate);
					result = Basic;
	//				System.out.println("i am in onAmount1 Basic  "+Basic);
					st.close();
					return result;
				}
				st.close();
				
				
				
				
	
			} catch (Exception e) {
				e.printStackTrace();
				EL.errorLog("Calculate.java -- onAmount()", e.toString());
			}
		}
		
		try
		{
			if(trncd==201||trncd==202||trncd==221 ||trncd==231||trncd==232||trncd==233||trncd==234||trncd==235||trncd==236)
			{
		st11 = Cn.createStatement();
		rs1 = st11.executeQuery("SELECT  * FROM CTCDISPLAY WHERE TRNCD  = 101 AND EMPNO = "
				+ empno);
		
		int pf=0,pt=0,esic=0;
		while(rs1.next())
		{
		pf=rs1.getInt("pf");
		esic=rs1.getInt("esic");
		pt=rs1.getInt("pt");
		System.out.println("PT : "+pt);
		}
		if((trncd==201 && pf==0)||(trncd==231 && pf==0)||(trncd==232 && pf==0)||(trncd==233 && pf==0)
||(trncd==234 && pf==0)||(trncd==235 && pf==0))		{
			result = 0.00f;
			st11.close();
			return result;
		}
		else if(trncd==202 && pt==0)
		{
			result = 0.00f;
			st11.close();
			System.out.println("PT : "+result);
			return result;
			
		}
		else if((trncd==221 && esic==0)||(trncd==236 && esic==0))
		{
			result = 0.00f;
			st11.close();
			return result;
		}
			}
		} catch (Exception e) {
			e.printStackTrace();
			EL.errorLog("Calculate.java -- onAmount()", e.toString());
		}
		String OmtStr = "";
		String vempType = "" + empType;
		OmtStr = "";

		OmtStr = "SELECT * FROM ONAMT WHERE EMP_CAT = " + empno
				+ " AND TRNCD = " + trncd;

		try {
			Statement st0 = Cn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = st0.executeQuery(OmtStr);
			if (!rs.next()) {
				// System.out.println("into !rs.next() ");
				st0.close();

			} else {
				// System.out.println("into !rs.next() else ");
				vempType = "" + empno;
			}
		} catch (Exception e) {
			e.printStackTrace();
			EL.errorLog("Calculate.java -- OnAmount()", e.toString());
		}

		OmtStr = "SELECT * FROM ONAMT WHERE EMP_CAT = " + vempType
				+ " AND TRNCD = " + trncd;
		try {
			
			//System.out.println("ONAMT : "+OmtStr);
			
			Statement st0 = Cn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = st0.executeQuery(OmtStr);
			if (!rs.next()) {
				st0.close();
				return 0;
			}
			rs.beforeFirst();
			ResultSet trn = null;
			while (rs.next()) {
				Statement st1 = Cn.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				trn = st1.executeQuery("SELECT * FROM PAYTRAN WHERE TRNCD="
						+ rs.getInt("ONAMTCD") + " AND EMPNO=" + empno);
				if (trn.next()) {
					/*
					 * WrkAmt = WrkAmt + (trn.getString("CAL_AMT") == null ? trn
					 * .getFloat("INP_AMT") : trn.getInt("CAL_AMT"));
					 */
					if (trncd == rs.getInt("ONAMTCD")) {
						// System.out.println("bgn"+getDays(BgnDate));
						//WrkAmt = WrkAmt	+ (trn.getString("INP_AMT") == null ? trn.getInt("CAL_AMT"):trn.getString("CAL_AMT")==null?trn.getInt("INP_AMT"):trn.getInt("cal_amt"));
						if(rs.getString("AMT_TYPE").equalsIgnoreCase("C"))
						{
						WrkAmt = WrkAmt	+ (trn.getString("INP_AMT") == null	? trn.getInt("CAL_AMT") : trn.getInt("INP_AMT"));
						WrkAmt = (WrkAmt * WrkDays) / getDays(BgnDate);
		//				System.out.println("i am in onAmount1 WrkAmt  "+WrkAmt);
						}
						else
						{
							WrkAmt = WrkAmt	+ (trn.getString("INP_AMT") == null	? trn.getInt("CAL_AMT") : trn.getInt("INP_AMT"));
							
						}
						
					}
					/*
					 * else if(trncd == 202) { WrkAmt = WrkAmt +
					 * trn.getInt("INP_AMT"); }
					 */
					else {
						
						/*WrkAmt = WrkAmt
								+ (trn.getString("CAL_AMT") == null
										|| trn.getInt("CAL_AMT") == 0 ? trn
										.getInt("INP_AMT") : trn
										.getInt("CAL_AMT"));*/
						
						PreparedStatement pst=Cn.prepareStatement("select PLUSMINUS from CDMAST where trncd="+rs.getInt("ONAMTCD"));
						ResultSet pst_rs=pst.executeQuery();
							
						while(pst_rs.next())
						{
						
							if(pst_rs.getString("PLUSMINUS").equalsIgnoreCase("P"))
							{
							WrkAmt = WrkAmt	+ (trn.getString("CAL_AMT") == null	? trn.getInt("INP_AMT") : trn.getInt("CAL_AMT"));
							}
							else if(pst_rs.getString("PLUSMINUS").equalsIgnoreCase("M"))
							{
							WrkAmt = WrkAmt	- (trn.getString("CAL_AMT") == null	? trn.getInt("INP_AMT") : trn.getInt("CAL_AMT")==0?trn.getInt("INP_AMT"):trn.getInt("CAL_AMT"));
							}
							
						
							
						}
						
						
						// WrkAmt = (WrkAmt * WrkDays)/getDays(BgnDate);
						
						
							
					 }					
				}				
				st1.close();
			}
			

			
			
			
			
			result = WrkAmt;
			st0.close();
		} catch (Exception e) {
			e.printStackTrace();
			EL.errorLog("Calculate.java -- OnAmount()", e.toString());
		}
		//System.out.println("work amt" + result);
		return result;
	}
	
	// ------------- Method to check for Onamt for suspendd and other by hrishi ------------------------------
		public static float onAmount(int trncd, int empno, String BgnDate,	int empType, float WrkDays, float lopDays, float suspendDays, Connection Cn) // added one parameter
															// WrkDays
		{
			System.out.println("in first onamt........");
			float WrkAmt = 0.00f;
			float WrkDaysn = 0.00f;
			float Basic = 0.00f;
			float result = 0.00f;
			Statement st = null,st11 = null;
			ResultSet rs = null,rs1 = null;
			
		//THE CODE IS USED TO DEDUCT SALARY ON BASIS OF SUSPEND DAYS STARTS HERE
			float toaldays=suspendDays+lopDays;
			if(toaldays<=5)
			{
				
				if (trncd == 101 || trncd==102|| trncd==138 ) {
				
			//	if (trncd == 101 || trncd==102|| trncd==138 || trncd==108) {
					
					
					
					
					WrkDaysn = WrkDays  ;
		//			System.out.println("WrkDaysaaaaaaaa for  "+trncd+" "+WrkDays );
		//			System.out.println("lopDays for  "+trncd+" "+lopDays );
		//			System.out.println("WrkDaysn for  "+trncd+" "+WrkDaysn );
					
					
					
					
				}
				
				
				
				else{
					
					if(suspendDays!=0)
					{
						WrkDaysn = (WrkDays+toaldays)-suspendDays ;
					}
					else{
					WrkDaysn = WrkDays+toaldays ;
					if(empno==732){
					
					System.out.println("i am in onAmount  01   "+WrkDays);
					System.out.println("i am in onAmount  02   "+toaldays);
					System.out.println("i am in onAmount  03   "+WrkDaysn);
					System.out.println("WrkDaysn inside else  for "+trncd+" "+WrkDaysn );
					}
				}	
					
				}
			}
			
			if(suspendDays==31 || toaldays==31)
			{
				WrkDaysn=0;
			}
			//THE CODE IS USED TO DEDUCT SALARY ON BASIS OF SUSPEND DAYS END HERE
			 
			
			if (trncd == 199 || trncd == 127) {
				try {
					st = Cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);

					if (trncd == 199) {
						 
						rs = st.executeQuery("SELECT  INP_AMT FROM PAYTRAN WHERE TRNCD  in(199)  AND EMPNO = "
								+ empno);
					} else {
			//			System.out.println("i am in onAmount  05  trncd == 127 ");
						rs = st.executeQuery("SELECT  INP_AMT FROM PAYTRAN WHERE TRNCD  in(127)  AND EMPNO = "
								+ empno);
					}
					if (rs.next()) {
						Basic = rs.getFloat("INP_AMT") * WrkDays / getDays(BgnDate);
						result = Basic;
						st.close();
					//	System.out.println("i am in onAmount  06  "+rs.getFloat("INP_AMT"));
						 
						return result;
					}
					st.close();
					
					
					
					
		
				} catch (Exception e) {
					e.printStackTrace();
					EL.errorLog("Calculate.java -- onAmount()", e.toString());
				}
			}
			
			try
			{
				if(trncd==201||trncd==202||trncd==221 ||trncd==231||trncd==232||trncd==233||trncd==234||trncd==235||trncd==236)
				{
			st11 = Cn.createStatement();
			rs1 = st11.executeQuery("SELECT  * FROM CTCDISPLAY WHERE TRNCD  = 101 AND EMPNO = "
					+ empno);
			
			int pf=0,pt=0,esic=0;
			while(rs1.next())
			{
			pf=rs1.getInt("pf");
			esic=rs1.getInt("esic");
			pt=rs1.getInt("pt");
			}
			if((trncd==201 && pf==0)||(trncd==231 && pf==0)||(trncd==232 && pf==0)||(trncd==233 && pf==0)
	||(trncd==234 && pf==0)||(trncd==235 && pf==0))		{
				result = 0.00f;
				st11.close();
				return result;
			}
			else if(trncd==202 && pt==0)
			{
				result = 0.00f;
				st11.close();
				return result;
			}
			else if((trncd==221 && esic==0)||(trncd==236 && esic==0))
			{
				result = 0.00f;
				st11.close();
				return result;
			}
				}
			} catch (Exception e) {
				e.printStackTrace();
				EL.errorLog("Calculate.java -- onAmount()", e.toString());
			}
			String OmtStr = "";
			String vempType = "" + empType;
			OmtStr = "";

			OmtStr = "SELECT * FROM ONAMT WHERE EMP_CAT = " + empno
					+ " AND TRNCD = " + trncd;

			try {
				Statement st0 = Cn.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				rs = st0.executeQuery(OmtStr);
				if (!rs.next()) {
					// System.out.println("into !rs.next() ");
					st0.close();

				} else {
					// System.out.println("into !rs.next() else ");
					vempType = "" + empno;
		//			System.out.println("i am in onAmount  09   "+vempType);
				}
			} catch (Exception e) {
				e.printStackTrace();
				EL.errorLog("Calculate.java -- OnAmount()", e.toString());
			}

			OmtStr = "SELECT * FROM ONAMT WHERE EMP_CAT = " + vempType
					+ " AND TRNCD = " + trncd;
			try {
				Statement st0 = Cn.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				rs = st0.executeQuery(OmtStr);
				if (!rs.next()) {
					st0.close();
					return 0;
				}
				rs.beforeFirst();
				ResultSet trn = null;
				while (rs.next()) {
					Statement st1 = Cn.createStatement(
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					trn = st1.executeQuery("SELECT * FROM PAYTRAN WHERE TRNCD="
							+ rs.getInt("ONAMTCD") + " AND EMPNO=" + empno);
					if (trn.next()) {
						
						
						// COMMENTED  to workdays if null there 
						// WrkAmt = WrkAmt + (trn.getString("CAL_AMT") == null ? trn.getFloat("INP_AMT") : trn.getInt("CAL_AMT"));
						
						if (trncd == rs.getInt("ONAMTCD")) {
		//					System.out.println("i am in onAmount  10   "+rs.getInt("ONAMTCD"));
							// System.out.println("bgn"+getDays(BgnDate));
							//WrkAmt = WrkAmt	+ (trn.getString("INP_AMT") == null ? trn.getInt("CAL_AMT"):trn.getString("CAL_AMT")==null?trn.getInt("INP_AMT"):trn.getInt("cal_amt"));
							if(rs.getString("AMT_TYPE").equalsIgnoreCase("C"))
							{
							WrkAmt = WrkAmt	+ (trn.getString("INP_AMT") == null	? trn.getInt("CAL_AMT") : trn.getInt("INP_AMT"));
							WrkAmt = (WrkAmt * WrkDaysn) / getDays(BgnDate);
		//					System.out.println("i am in onAmount  11   "+WrkDaysn);
		//					System.out.println("i am in onAmount  12   "+WrkAmt);
							}
						else
							{
								WrkAmt = WrkAmt	+ (trn.getString("INP_AMT") == null	? trn.getInt("CAL_AMT") : trn.getInt("INP_AMT"));
								
							}
						
						}
						/*
						 * else if(trncd == 202) { WrkAmt = WrkAmt +
						 * trn.getInt("INP_AMT"); }
						 */
						else {
							
							/*WrkAmt = WrkAmt
									+ (trn.getString("CAL_AMT") == null
											|| trn.getInt("CAL_AMT") == 0 ? trn
											.getInt("INP_AMT") : trn
											.getInt("CAL_AMT"));*/
							
							PreparedStatement pst=Cn.prepareStatement("select PLUSMINUS from CDMAST where trncd="+rs.getInt("ONAMTCD"));
							ResultSet pst_rs=pst.executeQuery();
								
							while(pst_rs.next())
							{
							
								if(pst_rs.getString("PLUSMINUS").equalsIgnoreCase("P"))
								{
								WrkAmt = WrkAmt	+ (trn.getString("CAL_AMT") == null	? trn.getInt("INP_AMT") : trn.getInt("CAL_AMT"));
								}
								else if(pst_rs.getString("PLUSMINUS").equalsIgnoreCase("M"))
								{
								WrkAmt = WrkAmt	- (trn.getString("CAL_AMT") == null	? trn.getInt("INP_AMT") : trn.getInt("CAL_AMT")==0?trn.getInt("INP_AMT"):trn.getInt("CAL_AMT"));
								}
								
							
								
							}
							
							
							// WrkAmt = (WrkAmt * WrkDays)/getDays(BgnDate);
							
							
								
						 }					
					}				
					st1.close();
				}
				

				
				
				
				
				result = WrkAmt;
				st0.close();
			} catch (Exception e) {
				e.printStackTrace();
				EL.errorLog("Calculate.java -- OnAmount()", e.toString());
			}
			//System.out.println("work amt" + result);
			return result;
		}
	
	
	

	public static int Update_Tran(ResultSet emp, String G_UserId,
			String BgnDate, Connection Cn) {
		
	/*	String DaCalculateDate = "01"+BgnDate.substring(2, 11);
		VDA_Calculation.vda_Calc_latest(DaCalculateDate);
	*/	
		
		ResultSet ps = null;
		ResultSet sos_ded = null;
		String psstr = "";
		int NetTot = 0;
		int WrkAmt = 0;
		String Str1 = "";
		int Tot_allow = 0;
		int Tot_Ded = 0;
		float suspendDays1 =0f;
		float lopDays1=0f;
		boolean negFlag=false;
		try {
			Statement stUP = Cn.createStatement();
			Statement st = Cn.createStatement();
			Statement st1 = Cn.createStatement();
			Statement st2 = Cn.createStatement();
			Statement st3 = Cn.createStatement();
			Statement st4 = Cn.createStatement();
			Sal_DetailsHandler SDH = new Sal_DetailsHandler();
			emp.beforeFirst();
		//	System.out.println("i am in Update_Tran");
			
			while (emp.next()) {
				Tot_allow = 0;
				Tot_Ded = 0;
				String TrnStr = "select  P.*,c.CF_SW from PAYTRAN p,cdmast c  where p.empno = "
						+ emp.getInt("EMPNO")
						+ " and p.trncd > 100 and p.trncd <= 295 and c.trncd = p.trncd and c.gross_yn='Y' "
						+ "and c.TRNCD not in(198,136,137,231,232,233,234,235,236,237)" +
						" " +
						" ORDER BY TRNCD";

				
		//		System.out.println("update tran quer "+TrnStr);
				
				ResultSet trn = st.executeQuery(TrnStr);
				while (trn.next()) {
	//				System.out.println("HELLO AK 00");
					
					int trncd = trn.getInt("TRNCD");
					WrkAmt = 0;
					psstr = "select trncd  from slab where trncd = " + trncd;
					ps = st1.executeQuery(psstr);
					if (ps.next()) {
	//					System.out.println("HELLO AK 01  "+psstr);
						
						if ((trn.getString("CAL_AMT") == null ? 0 : trn
								.getInt("CAL_AMT")) == 0
								&& (trn.getString("INP_AMT") == null ? 0 : trn
										.getInt("INP_AMT")) != 0) {
			//				System.out.println("HELLO AK 02  ");
							if (trncd != 5000) {
			//					System.out.println("HELLO AK 03  ");
								WrkAmt = (trn.getString("INP_AMT") == null ? 0
										: trn.getInt("INP_AMT"))
										+ (trn.getString("ADJ_AMT") == null ? 0
												: trn.getInt("ADJ_AMT"))
										+ (trn.getString("ARR_AMT") == null ? 0
												: trn.getInt("ARR_AMT"));
								
								if(trn.getString("CF_SW").equalsIgnoreCase("*") && trn.getInt("CAL_AMT")<=0)
								{
									WrkAmt=0;
									stUP.executeUpdate("update PAYTRAN set cal_amt =0, net_amt =0  where  TRNCD = "
											+ trncd
											+ " AND  EMPNO = "
											+ emp.getInt("EMPNO"));
									/*System.out.println("HELLO AK 04  update PAYTRAN set cal_amt =0, net_amt =0  where  TRNCD = "
											+ trncd
											+ " AND  EMPNO = "
											+ emp.getInt("EMPNO"));*/
								}
								else
								{
								stUP.executeUpdate("update PAYTRAN set cal_amt = "
										+ (trn.getString("INP_AMT") == null ? 0
												: trn.getInt("INP_AMT"))
										+ ", net_amt = "
										+ Math.round(WrkAmt)
										+ " where  TRNCD = "
										+ trncd
										+ " AND  EMPNO = "
										+ emp.getInt("EMPNO"));
								/*System.out.println("HELLO AK 05 update PAYTRAN set cal_amt = "
										+ (trn.getString("INP_AMT") == null ? 0
												: trn.getInt("INP_AMT"))
										+ ", net_amt = "
										+ Math.round(WrkAmt)
										+ " where  TRNCD = "
										+ trncd
										+ " AND  EMPNO = "
										+ emp.getInt("EMPNO"));*/
								
								}
							} else {
								WrkAmt = 0;
								stUP.executeUpdate("update PAYTRAN set cal_amt = 0 , net_amt = "
										+ Math.round(WrkAmt)
										+ " where  TRNCD = "
										+ trncd
										+ " AND  EMPNO = "
										+ emp.getInt("EMPNO"));
								/*System.out.println("HELLO AK 06 update PAYTRAN set cal_amt = 0 , net_amt = "
										+ Math.round(WrkAmt)
										+ " where  TRNCD = "
										+ trncd
										+ " AND  EMPNO = "
										+ emp.getInt("EMPNO"));*/
							}
						} else {
							WrkAmt = (trn.getString("CAL_AMT") == null ? 0
									: trn.getInt("CAL_AMT"))
									+ (trn.getString("ADJ_AMT") == null ? 0
											: trn.getInt("ADJ_AMT"))
									+ (trn.getString("ARR_AMT") == null ? 0
											: trn.getInt("ARR_AMT"));
							stUP.executeUpdate("update PAYTRAN set net_amt = "
									+ WrkAmt + " where  TRNCD = " + trncd
									+ " AND  EMPNO = " + emp.getInt("EMPNO"));
							/*System.out.println("HELLO AK 06   update PAYTRAN set net_amt = "
									+ WrkAmt + " where  TRNCD = " + trncd
									+ " AND  EMPNO = " + emp.getInt("EMPNO"));*/
						}

						if (trncd < 200 && (trncd != 199 && trncd != 127)) {
							Tot_allow = Tot_allow + WrkAmt;
						} else {
							if (trncd != 199 && trncd != 127) {
						//		System.out.println("HELLO AK 07 " + Tot_Ded);
								Tot_Ded = Tot_Ded + WrkAmt;
								TotAmt[emp.getInt("BRANCH")][trn
										.getInt("trncd")] = TotAmt[emp
										.getInt("BRANCH")][trn.getInt("trncd")]
										+ WrkAmt;
	//							System.out.println("HELLO AK 08 " + Tot_Ded +" "+ Tot_allow +" "+WrkAmt);	
							}
						}
					} else {
		//				System.out.println("HELLO AK 09 ");	
						
						if ((trn.getString("CAL_AMT") == null ? 0 : trn
								.getInt("CAL_AMT")) == 0
								&& (trn.getString("INP_AMT") == null ? 0 : trn
										.getInt("INP_AMT")) != 0) {  // commented for suspend update
							WrkAmt = (trn.getString("INP_AMT") == null ? 0
									: trn.getInt("INP_AMT"))
									+ (trn.getString("ADJ_AMT") == null ? 0
											: trn.getInt("ADJ_AMT"))
									+ (trn.getString("ARR_AMT") == null ? 0
											: trn.getInt("ARR_AMT"));
		//					System.out.println("heloo akshay x0.x0  "+WrkAmt);
						
		//					System.out.println("heloo akshay x0.0.0"+trn.getString("CAL_AMT")+"  "+ (trn.getString("INP_AMT")));
							
							//
							String lopDays_string = "SELECT * FROM PAYTRAN WHERE TRNCD=301 AND EMPNO="
									+ emp.getInt("EMPNO");
							ResultSet trn0 = st1.executeQuery(lopDays_string);
							if (trn0.next()) {
								 lopDays1 = trn0.getFloat("INP_AMT");
	//							 System.out.println("Update_Tran lopdays   "+lopDays1+"  "+lopDays_string);
									}
							
							
							
						String 	susDays_string = "SELECT * FROM PAYTRAN WHERE TRNCD=302 AND EMPNO="
								+ emp.getInt("EMPNO");
							     trn0 = st1.executeQuery(susDays_string);
							if (trn0.next()) {
								 suspendDays1 = trn0.getFloat("INP_AMT");
		//						 System.out.println("ak wrk suspendDays1 "+suspendDays1+"  "+susDays_string );
							}
							
							if(suspendDays1>=Calculate.getDays(BgnDate) || lopDays1>=Calculate.getDays(BgnDate) )
							{
								//
								//
		//						System.out.println("heloo akshay x0.0.1"+suspendDays1+" "+Calculate.getDays(BgnDate)+"  "+lopDays1+"  "+ Calculate.getDays(BgnDate));
		//						System.out.println("heloo akshay x0.0"+WrkAmt);
								WrkAmt=0;
							}
		//					System.out.println("heloo akshay x0.1"+WrkAmt);
							stUP.executeUpdate("update PAYTRAN set cal_amt = "
									+ (trn.getString("INP_AMT") == null ? 0
											: trn.getInt("INP_AMT"))
											+ ", net_amt = " + Math.round(WrkAmt)
											/*+ ", net_amt = " + (trn.getString("INP_AMT") == null ? 0
													: trn.getInt("INP_AMT"))*/
								           	+ " where TRNCD = " + trncd
									        + " AND  EMPNO = " + emp.getInt("EMPNO"));
		//					System.out.println("heloo akshay x0.2"+Math.round(WrkAmt));
							/*System.out.println("HELLO AK 09.0 update PAYTRAN set cal_amt = "
									+ (trn.getString("INP_AMT") == null ? 0
											: trn.getInt("INP_AMT"))
									+ ", net_amt = " + Math.round(WrkAmt)
									+ " where TRNCD = " + trncd
									+ " AND  EMPNO = " + emp.getInt("EMPNO"));*/	

							
							
						} else {
							WrkAmt = (trn.getString("CAL_AMT") == null ? 0
									: trn.getInt("CAL_AMT"))
									+ (trn.getString("ADJ_AMT") == null ? 0
											: trn.getInt("ADJ_AMT"))
									+ (trn.getString("ARR_AMT") == null ? 0
											: trn.getInt("ARR_AMT"));
		//					System.out.println("heloo akshay x0.3"+WrkAmt);
							stUP.executeUpdate("update PAYTRAN set net_amt = "
									+ WrkAmt + " where  TRNCD = " + trncd
									+ " AND  EMPNO = " + emp.getInt("EMPNO"));
						/*	System.out.println("HELLO AK 10 update PAYTRAN set net_amt = "
									+ WrkAmt + " where  TRNCD = " + trncd
									+ " AND  EMPNO = " + emp.getInt("EMPNO"));	*/
						}
						if (trncd < 200 && (trncd != 199 && trncd != 127)) {
							Tot_allow = Tot_allow + WrkAmt;
						} else {
							if (trncd != 199 && trncd != 127) {
		//						System.out.println("HELLO AK 11 " + Tot_Ded );
								Tot_Ded = Tot_Ded + WrkAmt;
								TotAmt[emp.getInt("BRANCH")][trn
										.getInt("trncd")] = TotAmt[emp
										.getInt("BRANCH")][trn.getInt("trncd")]
										+ WrkAmt;
			//					System.out.println("HELLO AK 12 " + Tot_Ded +" "+ Tot_allow +" "+WrkAmt);
							}
						}
					}

				}
				
				
				
				
				
				TrnStr = "select  PAYTRAN.* from PAYTRAN where trncd = 999 and empno = "
						+ emp.getInt("empno");
				trn = st.executeQuery(TrnStr);
				
				if (trn.next()) {
					if ((Tot_allow - Tot_Ded) < 0) {
						System.out
								.println("Employee Number "
										+ emp.getInt("empno")
										+ " salary goes Negative Please check it and process paycal again");
						// return emp.getInt("empno");
						negFlag = true;
					}
					Str1 = "update PAYTRAN set inp_amt = " + Tot_allow
							+ ", cal_amt = " + Tot_Ded + ", adj_amt = "
							+ (Tot_allow - Tot_Ded) + ", net_amt = "
							+ (Tot_allow - Tot_Ded) + ", usrcode = '"
							+ G_UserId + "', upddt = '" + BgnDate
							+ "' , TRNDT = '" + BgnDate
							+ "' ,STATUS='P' where  trncd = 999 and empno = "
							+ emp.getInt("empno");

					stUP.executeUpdate(Str1);
					if (emp.getInt("BRANCH") == 1) {
						NetTot = NetTot + Tot_allow - Tot_Ded;
					}
				} else {
					if ((Tot_allow - Tot_Ded) < 0) {
						/*System.out
								.println("Employee Number "
										+ emp.getInt("empno")
										+ " salary goes Negative Please check it and process paycal again");*/
						// return emp.getInt("empno");
						negFlag = true;
					}

					Str1 = "insert into PAYTRAN (trndt, empno, trncd, inp_amt, cal_amt,adj_amt, net_amt, usrcode, upddt,arr_amt,srno,cf_sw,STATUS) values ("
							+ "'"
							+ BgnDate
							+ "',"
							+ emp.getInt("empno")
							+ ", 999,"
							+ Tot_allow
							+ " ,"
							+ Tot_Ded
							+ " ,"
							+ (Tot_allow - Tot_Ded)
							+ " ,"
							+ (Tot_allow - Tot_Ded)
							+ " , "
							+ " '"
							+ G_UserId
							+ "','" + BgnDate + "',0,0,'','P')";
					stUP.executeUpdate(Str1);
					if (emp.getInt("BRANCH") == 1) {
						NetTot = NetTot + Tot_allow - Tot_Ded;
					}
				}
				if (negFlag)
					SDH.addSalDetails(emp.getInt("empno"),
							BgnDate.substring(3), ReportDAO.EOM(BgnDate),
							"NEGATIVE", Cn);
				else{
					// for society deduction rules .
					/*int net999=Tot_allow-Tot_Ded;
					sos_ded=st4.executeQuery("select * from paytran where trncd=206 and empno="+emp.getInt("empno")+" ");
					if(sos_ded.next())
					{
						int sosded=sos_ded.getInt("INP_AMT");
						int amt25=(int) Math.round(((Tot_allow*0.25)));
						if(net999>0&&net999<amt25)
						{
							int dedamt=amt25-net999;
							if(sosded>=dedamt)
							{
								System.out.println("society deducted..again");
								st4.executeUpdate("update paytran set CAL_AMT=CAL_AMT-"+dedamt+",NET_AMT=NET_AMT-"+dedamt+" where trncd=206 and empno="+emp.getInt("empno")+" ");
								st4.executeUpdate("update paytran set CAL_AMT=CAL_AMT-"+dedamt+",ADJ_AMT =ADJ_AMT +"+dedamt+",NET_AMT=NET_AMT+"+dedamt+" where trncd=999 and empno="+emp.getInt("empno")+" ");
	
							}
						}
					}*/
					// end here society deduction.
					SDH.addSalDetails(emp.getInt("empno"),
							BgnDate.substring(3), ReportDAO.EOM(BgnDate),
							"PROCESSED", Cn);
				}
				
			
			} // Closed Outer While
			int i = 0;
			String CdmStr = "select * from cdmast where frequency = 'C' ";

			ResultSet CDM = st1.executeQuery(CdmStr);
			while (CDM.next()) {
				for (i = 0; i <= 20; i++) {
					String OmtStr = "select * from onamt where trncd = "
							+ CDM.getInt("trncd");

					ResultSet Omt = st2.executeQuery(OmtStr);
					if (Omt.next()) {
						String SlbStr = "select * from slab where trncd = "
								+ CDM.getInt("trncd")
								+ " and effdate = ( select min(effdate) from slab where trncd = "
								+ CDM.getInt("trncd") + " and effdate >= '"
								+ BgnDate + "') and " + WrkAmt
								+ " between  frmamt and toamt order by srno";

						ResultSet Slb = st3.executeQuery(SlbStr);
						if (Slb.next()) {
							if ((Slb.getString("PER") == null ? 0 : Slb
									.getInt("PER")) != 0
									&& TotAmt[i][Omt.getInt("ONAMTCD")] != 0) {
								TotAmt[i][CDM.getInt("TRNCD")] = Math
										.round(TotAmt[i][Omt.getInt("ONAMTCD")]
												* Slb.getFloat("PER") / 100);
							}
						}

					}
				}
				pAcNo[CDM.getInt("TRNCD")][1] = CDM.getString("SUBSYS");
				pAcNo[CDM.getInt("TRNCD")][2] = CDM.getString("acno");
			}

			if (NetTot != 0) {
				String HopStr = "select * from hopost where trncd = 999 and brcd = 1 ";
				ResultSet Hop = st3.executeQuery(HopStr);
				if (Hop.next()) {
					stUP.executeUpdate("update hopost set subsys_cd = 'MB', ac_no = 0 , amount = "
							+ NetTot);
				} else {
					stUP.executeUpdate("insert into hopost values (1,999,0,'MB',0,"
							+ NetTot + ",'C')");
				}	
			}System.out.println("Check 5");
			st.close();
			st1.close();
			st2.close();
			st3.close();
			stUP.close();
		 //	Ho_Post(Cn);
			//Br_Post(BgnDate, Cn);
			//Deduction_Cal(BgnDate, Cn);
			
		
		} catch (Exception e) {
			e.printStackTrace();
			EL.errorLog("Calculate.java -- update_Tran()", e.toString());
		}System.out.println("Check 6");
		return negFlag==true?-3:0;
	}

/*	public static void Ho_Post(Connection Cn) {
		try {
			Statement st = Cn.createStatement();
			st.executeUpdate("truncate table autopost");
			String CdmStr = "select * from cdmast where trncd > 200 and trncd < 300 order by trncd ";
			ResultSet CDM = st.executeQuery(CdmStr);
			while (CDM.next()) {
				if (!(CDM.getString("SUBSYS") == null ? "" : CDM
						.getString("SUBSYS")).equals("")) {
					pAcNo[CDM.getInt("TRNCD")][1] = CDM.getString("SUBSYS");
					pAcNo[CDM.getInt("TRNCD")][2] = CDM.getString("acno");
					pAcNo[CDM.getInt("TRNCD")][3] = CDM.getString("FREQUENCY");
				}
			}
			Statement stUP = Cn.createStatement();
			for (int i = 1; i <= 999; i++) {
				for (int i = 1; i <= 100; i++) {
				
				for (int j = 201; j <= 300; j++) {
					if (TotAmt[i][j] != 0) {
						String HopStr = "select  h.* from hopost h where trncd = "
								+ j + " and brcd = " + i;
						ResultSet Hop = st.executeQuery(HopStr);
						if (Hop.next()) {
							stUP.executeUpdate("update hopost set subsys_cd = '"
									+ (pAcNo[j][1] == null ? "" : pAcNo[j][1])
									+ "', ac_no = "
									+ Integer
											.parseInt((pAcNo[j][2] == null ? "0"
													: pAcNo[j][2]))
									+ ", amount = "
									+ TotAmt[i][j]
									+ " where trncd = "
									+ j
									+ " and brcd = "
									+ i);
							System.out.println("update hopost set subsys_cd = '"
									+ (pAcNo[j][1] == null ? "" : pAcNo[j][1])
									+ "', ac_no = "
									+ Integer
											.parseInt((pAcNo[j][2] == null ? "0"
													: pAcNo[j][2]))
									+ ", amount = "
									+ TotAmt[i][j]
									+ " where trncd = "
									+ j
									+ " and brcd = "
									+ i);
						} else {
							
							System.out.println("insert into hopost values ("
									+ i
									+ ", "
									+ j
									+ ", 0, '"
									+ (pAcNo[j][1] == null ? "" : pAcNo[j][1])
									+ "', "
									+ Integer
											.parseInt((pAcNo[j][2] == null ? "0"
													: pAcNo[j][2])) + ", "
									+ TotAmt[i][j] + ", 'C')");
							stUP.executeUpdate("insert into hopost values ("
									+ i
									+ ", "
									+ j
									+ ", 0, '"
									+ (pAcNo[j][1] == null ? "" : pAcNo[j][1])
									+ "', "
									+ Integer
											.parseInt((pAcNo[j][2] == null ? "0"
													: pAcNo[j][2])) + ", "
									+ TotAmt[i][j] + ", 'C')");
						}

						if ((pAcNo[j][3] == null ? "" : pAcNo[j][3])
								.equalsIgnoreCase("C")) {
							stUP.executeUpdate("insert into AUTOPOST  values (0,"
									+ i
									+ ", "
									+ j
									+ ", 0, '"
									+ (pAcNo[j][1] == null ? "" : pAcNo[j][1])
									+ "', "
									+ Integer
											.parseInt((pAcNo[j][2] == null ? "0"
													: pAcNo[j][2]))
									+ ", "
									+ TotAmt[i][j] + ", 'D',0)");
						}
					}
				}
			}
			st.close();
			stUP.close();
		} catch (Exception e) {
			e.printStackTrace();
			EL.errorLog("Calculate.java -- HOPOST()", e.toString());
		}
	}

	public static void Br_Post(String BgnDate, Connection Cn) {
		String TrnStr = "select trn.*, t.branch, t.acno from PAYTRAN trn, emptran t where trn.trncd > 200 and trn.trncd < 1000 and t.empno = trn.empno and "
				+ " t.effdate = ( SELECT MAX(E2.EFFDATE) FROM EMPTRAN E2 WHERE e2.empno = trn.empno and E2.EFFDATE <= '"
				+ BgnDate + "')" + " order by trn.empno, trn.trncd";

		try {
			Statement st = Cn.createStatement();
			Statement stUP = Cn.createStatement();
			ResultSet trn = st.executeQuery(TrnStr);
			while (trn.next()) {
				TotAmt1[trn.getInt("BRANCH")][trn.getInt("TRNCD")] = TotAmt1[trn
						.getInt("BRANCH")][trn.getInt("TRNCD")]
						+ trn.getInt("NET_AMT");
				if (trn.getInt("TRNCD") == 999) {
					String Str1 = "insert into autopost values("
							+ trn.getInt("empno") + "," + trn.getInt("BRANCH")
							+ ",999,0,'SB','" + trn.getString("acno") + "',"
							+ trn.getInt("NET_AMT") + ",'C',0 )";
					stUP.executeUpdate(Str1);
				}
			}

			for (int i = 0; i <= 20; i++) {
				for (int j = 201; j <= 999; j++) {
					if (TotAmt1[i][j] != 0) {
						ResultSet CDM = st
								.executeQuery("select * from cdmast where trncd = "										+ j);
						if (CDM.next()) {
							stUP.executeUpdate("insert into autopost values (0,"
									+ i
									+ ","
									+ j
									+ ",0 ,'"
									+ CDM.getString("BRSUBSYS")
									+ "' , "
									+ (CDM.getString("BRACNO") == null ? 0
											: CDM.getInt("BRACNO"))
									+ ","
									+ TotAmt1[i][j] + ", 'D',0)");
						}

					}
				}
			}
			st.close();
			stUP.close();
		} catch (Exception e) {
			e.printStackTrace();
			EL.errorLog("Calculate.java -- BRPOST()", e.toString());
		}
	}
*/
	/*public static void Deduction_Cal(String BgnDate, Connection Cn) {
		int total = 0;
		String dedstr = "";
		ResultSet ded = null;
		ResultSet trn = null;
		try {
 			Statement st = Cn.createStatement();
			Statement stUP = Cn.createStatement();
			Statement st1 = Cn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			dedstr = "select DISTINCT SUBSYS_cd,TRNCD from dedmast  WHERE ACTYN = 'Y' order by trncd";
			ded = st.executeQuery(dedstr);
			while (ded.next()) {
 				String TrnStr = "select * from PAYTRAN t,emptran e  where t.trncd = "
						+ ded.getInt("trncd")
						+ " and e.empno  = t.empno  and  "
						+ " e.effdate = ( SELECT MAX(E2.EFFDATE) FROM EMPTRAN E2 WHERE e2.empno = t.empno and E2.EFFDATE <= '"
						+ BgnDate
						+ "')"
						+ " and inp_amt <> (select sum(amount) from dedmast where empno = t.empno and trncd = t.trncd AND ACTYN = 'Y' )";

				trn = st1.executeQuery(TrnStr);
				if (trn.next()) {
	//				System.out.println("Few standard Deductions you entered are not matched with deduction master");
					trn.beforeFirst();
					while (trn.next()) {
						System.out.println(trn.getInt("EMPNO") + "\t"
								+ trn.getInt("TRNCD"));
					}
				}

				trn = null;
				TrnStr = "select * from PAYTRAN t, emptran e where t.trncd = "
						+ ded.getInt("trncd")
						+ "  and e.empno = t.empno and "
						+ "e.effdate = ( SELECT MAX(E2.EFFDATE) FROM EMPTRAN E2 WHERE e2.empno = t.empno and E2.EFFDATE <= '"
						+ BgnDate
						+ "')"
						+ " and net_amt = (select sum(amount) from dedmast where empno = t.empno and trncd = t.trncd AND ACTYN = 'Y')";

				trn = st1.executeQuery(TrnStr);
				if (trn.next()) {
					trn.beforeFirst();
					while (trn.next()) {
						total = total + trn.getInt("NET_AMT");
					}
				} else {
					trn = null;
					TrnStr = "select sum(inp_amt) from PAYTRAN where trncd = "
							+ ded.getInt("trncd");
					trn = st1.executeQuery(TrnStr);
					if (trn.next()) {
						total = (trn.getString(1) == null ? 0 : trn.getInt(1));
					}

				}

				String CdmStr = "select * from cdmast where trncd = "
						+ ded.getInt("trncd");
				ResultSet CDM = st1.executeQuery(CdmStr);
				if (CDM.next()) {
					if (total != 0) {
						if (CDM.getInt("DRACNO") != 0) {
							stUP.executeUpdate("insert into autopost values (0,999,"
									+ ded.getInt("trncd")
									+ ",0 ,'"
									+ (CDM.getString("DRSUBSYS") == null ? ""
											: CDM.getString("DRSUBSYS"))
									+ "',"
									+ (CDM.getString("DRACNO") == null ? 0
											: CDM.getInt("DRACNO"))
									+ ","
									+ total + ", 'C',0)");
						} else {
							stUP.executeUpdate("insert into autopost values (0,999,"
									+ ded.getInt("trncd")
									+ ",0 ,'"
									+ (CDM.getString("DRSUBSYS") == null ? ""
											: CDM.getString("DRSUBSYS"))
									+ "',"
									+ (CDM.getString("DRACNO") == null ? 0
											: CDM.getInt("DRACNO"))
									+ ","
									+ total + ", 'D',0)");
						}
					}
				}

			} // End While

			dedstr = "SELECT DED.*, EMP.BRANCH FROM DEDMAST DED, EMPTRAN EMP , PAYTRAN t WHERE t.empno = ded.empno and t.trncd = ded.trncd and  EMP.EMPNO = DED.EMPNO AND DED.ACTYN = 'Y' and "
					+ " EMP.effdate = ( SELECT MAX(E2.EFFDATE) FROM EMPTRAN E2 WHERE e2.empno = DED.empno and E2.EFFDATE <= '"
					+ BgnDate + "')" + "ORDER BY ded.EMPNO,ded.TRNCD";
			ded = st.executeQuery(dedstr);
			while (ded.next()) {
				stUP.executeUpdate("insert into autopost values("
						+ ded.getInt("empno") + "," + ded.getInt("BRANCH")
						+ "," + ded.getInt("trncd") + "," + ded.getInt("srno")
						+ ",'" + ded.getString("subsys_cd") + "', "
						+ ded.getInt("Ac_no") + "," + ded.getInt("amount")
						+ ", 'C',0)");
			}
			st.close();
			st1.close();
			stUP.close();
		} catch (Exception e) {
			e.printStackTrace();
			EL.errorLog("Calculate.java -- Deduction_Cal()", e.toString());
		}

	}*/

	// ------------- Method to get Number of Days in a month ----------------
	public static float getDays(String date) {
		float result = 0.0f;
		String[] dt = date.split("-"); // [0]->dd [1]->mmm [2]->yyyy
		if (dt[1].equalsIgnoreCase("Jan"))
			result = 31;
		else if (dt[1].equalsIgnoreCase("Feb")) {
			if (Integer.parseInt(dt[2]) % 4 == 0) {
				result = 29;
			} else
				result = 28;
		} else if (dt[1].equalsIgnoreCase("Mar"))
			result = 31;
		else if (dt[1].equalsIgnoreCase("Apr"))
			result = 30;
		else if (dt[1].equalsIgnoreCase("May"))
			result = 31;
		else if (dt[1].equalsIgnoreCase("Jun"))
			result = 30;
		else if (dt[1].equalsIgnoreCase("Jul"))
			result = 31;
		else if (dt[1].equalsIgnoreCase("Aug"))
			result = 31;
		else if (dt[1].equalsIgnoreCase("Sep"))
			result = 30;
		else if (dt[1].equalsIgnoreCase("Oct"))
			result = 31;
		else if (dt[1].equalsIgnoreCase("Nov"))
			result = 30;
		else if (dt[1].equalsIgnoreCase("Dec"))
			result = 31;
		return result;
	}

	public static int getMonth(String date) {
		int result = 0;
		String[] dt = date.split("-"); // [0]->dd [1]->mmm [2]->yyyy
		if (dt[1].equalsIgnoreCase("Jan"))
			result = 1;
		else if (dt[1].equalsIgnoreCase("Feb"))
			result = 2;
		else if (dt[1].equalsIgnoreCase("Mar"))
			result = 3;
		else if (dt[1].equalsIgnoreCase("Apr"))
			result = 4;
		else if (dt[1].equalsIgnoreCase("May"))
			result = 5;
		else if (dt[1].equalsIgnoreCase("Jun"))
			result = 6;
		else if (dt[1].equalsIgnoreCase("Jul"))
			result = 7;
		else if (dt[1].equalsIgnoreCase("Aug"))
			result = 8;
		else if (dt[1].equalsIgnoreCase("Sep"))
			result = 9;
		else if (dt[1].equalsIgnoreCase("Oct"))
			result = 10;
		else if (dt[1].equalsIgnoreCase("Nov"))
			result = 11;
		else if (dt[1].equalsIgnoreCase("Dec"))
			result = 12;
		return result;
	}

	public static int getYear(String date) {
		int result = 0;
		String[] dt = date.split("-");
		result = Integer.parseInt(dt[2]);
		return result;
	}

	public static String getEmpRange() {
		String result = "";
		try {
			Connection Con = ConnectionManager.getConnection();
			Statement st = Con.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT MIN(EMPNO), MAX(EMPNO) FROM EMPMAST");

			if (rs.next()) {
				result = rs.getInt(1) + "-" + rs.getInt(2);
			}
			rs.close();
			st.close();

			Con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static float overTimeCalc(int trncd, int empno, String BgnDate,
			Connection Cn) {
		float result = 0.0f;
		try {
			Statement st = Cn.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT SUM(HOURS) FROM OVERTIMEMTN WHERE EMPNO="
							+ empno
							+ " AND SHIFTCODE="
							+ trncd
							+ " AND OTDATE BETWEEN '"
							+ ReportDAO.BOM(BgnDate)
							+ "' AND '" + ReportDAO.EOM(BgnDate) + "'");
			if (rs.next()) {
				result = (rs.getString(1) == null ? 0.0f : rs.getFloat(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	 public static void deduction_insert(int empno,String G_UserId, String BgnDate,Connection Cn)
	  {
	  try
	  {
	  Statement dedsttr = Cn.createStatement();
	  Statement ded = Cn.createStatement();
	  Statement dedinsrt = Cn.createStatement(); 
	  Statement select = Cn.createStatement(); 
	  Statement	  inst_update = Cn.createStatement();
	  Statement delete = Cn.createStatement(); 
	  String deductiontr =	  "select distinct trncd from dedmast where empno = "+empno+" and ACTYN='Y'"; 
	  ResultSet	  dedtr = dedsttr.executeQuery(deductiontr);
	  
	  while(dedtr.next())
	  {
	 String deduction = "select * from DEDMAST where EMPNO ="+empno+" and " +
	 		"REPAY_START < = '"+ReportDAO.EOM(BgnDate)+"'  and TOTAL_INSTLMNTS >=0 and ACTUAL_TOTAL_AMT_PAID < SANC_AMT and ACTYN='Y' and  trncd = "+dedtr.getInt(1)+"  order by srno  ";
	  //System.out.println(deduction); 
	  
	  ResultSet dedt = ded.executeQuery(deduction);
	  while(dedt.next())
	  {	  
		//  System.out.println(" INTO IF OF DEDMAST ");
	  String sel_paytran = "SELECT * FROM PAYTRAN WHERE EMPNO ="+empno+" AND TRNCD = "+dedtr.getInt("TRNCD");
	  ResultSet sel = select.executeQuery(sel_paytran);
	  
	  if(sel.next())
	  { 
		//  System.out.println("dedt.getFloat('AMOUNT') ="+dedt.getFloat("AMOUNT"));
		//  System.out.println(" dedt.getFloat('SANC_AMT')="+dedt.getFloat("SANC_AMT"));
		//  System.out.println(" dedt.getFloat('ACTUAL_TOTAL_AMT_PAID')="+dedt.getFloat("ACTUAL_TOTAL_AMT_PAID"));
		 
	  String update_paytran = "UPDATE PAYTRAN SET cf_sw='*',INP_AMT = "+(dedt.getFloat("AMOUNT") <= (dedt.getFloat("SANC_AMT")-dedt.getFloat("ACTUAL_TOTAL_AMT_PAID"))?dedt.getFloat("AMOUNT"):(dedt.getFloat("SANC_AMT")-dedt.getFloat("ACTUAL_TOTAL_AMT_PAID")) ) +" where EMPNO = "+empno+" AND TRNCD = "+dedtr.getInt("TRNCD");
	  
	  dedinsrt.executeUpdate(update_paytran);
	  
	 // System.out.println(" update into paytran "+update_paytran);
	  }
	  else
	  {
	 // System.out.println("into insert deduction................"); 
	  String insert = "INSERT INTO PAYTRAN " +
	  "VALUES('"+ReportDAO.EOM(BgnDate)+"',"+empno+","+dedtr.getInt("TRNCD")+","+0+","+(dedt.getFloat("AMOUNT") <= (dedt.getFloat("SANC_AMT")-dedt.getFloat("ACTUAL_TOTAL_AMT_PAID"))?dedt.getFloat("AMOUNT"):(dedt.getFloat("SANC_AMT")-dedt.getFloat("ACTUAL_TOTAL_AMT_PAID")) )+","+0+","+0+","+0+","+0+",'*',"+G_UserId+",'"+BgnDate+"','A')";
	  dedinsrt.executeUpdate(insert);
	  //System.out.println(" insert into paytran "+insert);
	  
	  //String install="UPDATE DEDMAST SET No_Of_Installment = "+(dedt.getInt("No_Of_Installment")-1)+" " +
	  //		" where empno = "+empno+"and trncd = "+dedtr.getInt("TRNCD");
	  //inst_update.executeUpdate(install);
	  }
	  
	  }
	  
	  String deduction_delete = "select * from DEDMAST where EMPNO ="+empno+"  and " +
	  		"  ACTUAL_TOTAL_AMT_PAID >= SANC_AMT and ACTYN='Y' and  trncd = "+dedtr.getInt(1)+" order by srno ";
	//	  System.out.println(deduction_delete); 
		  
		  ResultSet dedt_delete  = delete.executeQuery(deduction_delete);
		  while(dedt_delete.next())
		  {	
			 // System.out.println("INTO LEVELE 000000000000000000");
			 Statement st=Cn.createStatement();
			 Statement st1=Cn.createStatement();
			 st.execute("update DEDMAST set ACTYN='N' where EMPNO ="+empno+"  and  trncd = "+dedtr.getInt(1)+" and  SRNO ="+dedt_delete.getInt("SRNO"));
			 st1.execute(" UPDATE PAYTRAN SET cf_sw='' ,inp_AMt=0,Cal_AMt=0,Net_AMt=0 where EMPNO ="+empno+" and  trncd = "+dedtr.getInt(1)); 
			  
	      }
	  
	  
	  
	  }
	  }catch (Exception e) 
	  {
		  e.printStackTrace();
		  EL.errorLog("Calculate.java -- Deduction_insert()", e.toString());
		  
		  
	  }
	  
	  }
	 
	
	public static String getMonth_DBF(String date)
	{
		String result=null;
		if(date.equalsIgnoreCase("Jan"))
			result="01";
		else if(date.equalsIgnoreCase("Feb"))
			result="02";
		else if(date.equalsIgnoreCase("Mar"))
			result="03";
		else if(date.equalsIgnoreCase("Apr"))
			result="04";
		else if(date.equalsIgnoreCase("May"))
			result="05";
		else if(date.equalsIgnoreCase("Jun"))
			result="06";
		else if(date.equalsIgnoreCase("Jul"))
			result="07";
		else if(date.equalsIgnoreCase("Aug"))
			result="08";
		else if(date.equalsIgnoreCase("Sep"))
			result="09";
		else if(date.equalsIgnoreCase("Oct"))
			result="10";
		else if(date.equalsIgnoreCase("Nov"))
			result="11";
		else if(date.equalsIgnoreCase("Dec"))
			result="12";
		return result;
	}
	// for calculating age by akshay 
	public static int agecal(String dob )
	{
 	    // dob = "1972-03-13";  1998-01-09
	    //TAKE SUBSTRINGS OF THE DOB SO SPLIT OUT YEAR, MONTH AND DAY
	    //INTO SEPERATE VARIABLES
	    int yearDOB = Integer.parseInt(dob.substring(0, 4));
	    int monthDOB = Integer.parseInt(dob.substring(5, 7));
	    int dayDOB = Integer.parseInt(dob.substring(8, 10));
 
	    //CALCULATE THE CURRENT YEAR, MONTH AND DAY
	    //INTO SEPERATE VARIABLES
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
	    java.util.Date date = new java.util.Date();
	    int thisYear = Integer.parseInt(dateFormat.format(date));
 	    dateFormat = new SimpleDateFormat("MM");
	    date = new java.util.Date();
	    int thisMonth = Integer.parseInt(dateFormat.format(date));

	    dateFormat = new SimpleDateFormat("dd");
	    date = new java.util.Date();
	    int thisDay = Integer.parseInt(dateFormat.format(date));
 
	    //CREATE AN AGE VARIABLE TO HOLD THE CALCULATED AGE
	    //TO START WILL – SET THE AGE EQUEL TO THE CURRENT YEAR MINUS THE YEAR
	    //OF THE DOB
	    int age = thisYear-yearDOB;
	    //IF THE CURRENT MONTH IS LESS THAN THE DOB MONTH
	    //THEN REDUCE THE DOB BY 1 AS THEY HAVE NOT HAD THEIR
	    //BIRTHDAY YET THIS YEAR
	    if(thisMonth < monthDOB){
 
	    age = age - 1;
	    }
	    //IF THE MONTH IN THE DOB IS EQUEL TO THE CURRENT MONTH
	    //THEN CHECK THE DAY TO FIND OUT IF THEY HAVE HAD THEIR
	    //BIRTHDAY YET. IF THE CURRENT DAY IS LESS THAN THE DAY OF THE DOB
	    //THEN REDUCE THE DOB BY 1 AS THEY HAVE NOT HAD THEIR
	    //BIRTHDAY YET THIS YEAR
	    if(thisMonth == monthDOB && thisDay < dayDOB){
 	    age = age -1;
	    }
	    //THE AGE VARIBALE WILL NOW CONTAIN THE CORRECT AGE
	    //DERIVED FROMTHE GIVEN DOB
 	    return age;
	
	}
	
	
}
