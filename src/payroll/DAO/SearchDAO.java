package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import payroll.Core.ReportDAO;
import payroll.Model.SearchBean;

public class SearchDAO {
	Connection con=ConnectionManager.getConnection();
	public ArrayList<SearchBean> getRecords(int month,String year){
		ResultSet rs=null; 
		ResultSet rs1=null; 
		ArrayList<SearchBean> list = new ArrayList<SearchBean>();
		Statement st=null;
		Statement st1=null;
		SearchBean sb= null;
     	try
     	{
				st = con.createStatement();
				rs=st.executeQuery("SELECT MON_DAY,SPR_CNT,ABS_CNT,WO_CNT,EO_CNT,EO_HRS,OT_CNT,OT_HRS,WRK_HRS,ID,mon_th,paid_days FROM Dbfimport_Data WHERE mon_th="+month+" and year='"+year+"' order by ID");
				while(rs.next())
				{
					st1 = con.createStatement();
					rs1=st1.executeQuery("select empno from otherdetail where f4='"+rs.getString(10)+"'");
					int empno =0;
					if(rs1.next()){
						empno = rs1.getInt(1);
					}
					sb = new SearchBean();
					sb.setMON_DAY(rs.getString(1));
					sb.setSPR_CNT(rs.getString(2));
					sb.setABS_CNT(rs.getString(3));
					sb.setWO_CNT(rs.getString(4));
					sb.setEO_CNT(rs.getString(5));
					sb.setEO_HRS(rs.getString(6));
					sb.setOT_CNT(rs.getString(7));
					sb.setOT_HRS(rs.getString(8));
					sb.setWRK_HRS(rs.getString(9));
					sb.setID(rs.getString(10));
					sb.setMonth(rs.getString(11));
					sb.setPAID_DAYS(rs.getString(12));
					sb.setEmpno(empno);
					list.add(sb);
				}
				rs.close();
				st.close();
			
			con.close();
		}
     	catch (Exception e) 
     	{
     		UsrHandler.senderrormail(e, "SearchDAO.getRecords");
			e.printStackTrace();
		}
	return list;
	}

	public ArrayList<SearchBean> getAttendRecords(String month,String year,String date){
		ResultSet rs=null;  
		ArrayList<SearchBean> list = new ArrayList<SearchBean>();
		Statement st=null;
		Statement st3=null;
		Statement stmt=null;
		SearchBean sb= null;
		String date1[]=date.split("-");
     	try
     	{
     			st = con.createStatement();
     			stmt = con.createStatement();
     			st3 = con.createStatement(); 
     			
			 	String sql ="select d.* ,o.*,e.doj,(select SUM(days)  from LEAVETRAN t where t.FRMDT between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' " +
			 		"and t.EMPNO = e.EMPNO and t.TRNTYPE ='D' and t.STATUS ='SANCTION' ) as leavedays , " +
			 		"( select COUNT(*) from HOLDMAST where FDATE between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' ) as holidays " +
			 		"from Dbfimport_Data d,OTHERDETAIL o,EMPMAST e,Sal_details s where d.ID= o.F4 and e.EMPNO=o.EMPNO	and e.STATUS='A' " +
			 		"and d.year = '"+year+"' and d.mon_th = '"+month+"' and s.empno=e.empno and s.sal_status <> 'FINALIZED' and s.sal_month='"+date1[1]+"-"+year+"' order by d.id";

				rs=st.executeQuery(sql);
				while(rs.next())
				{
					String sql2="Select doj from empmast where empno="+rs.getInt("empno")+" and doj between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"'";
					ResultSet rst = stmt.executeQuery(sql2);
					if(rst.next()){
						String doj = rst.getString(1);
						String dates[] = doj.split("-");
						String joinday = dates[2];
						String sql3="select a.ID ,a.mon_th,a.year,a.attribute,a.value from Dbfimport_Data unpivot" +
								"( value for attribute in (day1,day2,day3,day4,day5,day6,day7,day8,day9,day10,day11,day12,day13," +
								"day14,day15,day16,day17,day18,day19,day20,day21,day22,day23,day24,day25,day26,day27,day28,day29," +
								"day30,day31)) a	where id = '"+rs.getString("ID")+"' and year = '"+year+"' and" +
										" mon_th = '"+month+"'";
						
						int rctr = 0;
						int woctr = 0;
						int pctr = 0;
						int actr = 0;
						float hctr = 0;
						ResultSet rst1=st3.executeQuery(sql3);
						while(rst1.next())
						{
							rctr = rctr + 1;    
							if (Integer.parseInt(joinday) <= rctr) 
							{
								if ( rst1.getString("value").equalsIgnoreCase("WO") ){
									woctr++;
								}
								else if ( rst1.getString("value").equalsIgnoreCase("P ") ){
									pctr++;
								}
								else if ( rst1.getString("value").equalsIgnoreCase("A ") ){
									actr++;
								}
								else if ( rst1.getString("value").equalsIgnoreCase("HO") ){
									hctr++;
								}
							}
					    }
						sb = new SearchBean();
						sb.setEmpno(rs.getInt("empno"));
						sb.setDOJ(rs.getString("doj"));
						sb.setLVDAYS(rs.getString("leavedays"));
						sb.setID(rs.getString("ID"));
						sb.setMON_DAY(rs.getString("MON_DAY"));
						sb.setSPR_CNT(Integer.toString(pctr));
						sb.setWO_CNT(Integer.toString(woctr));
						sb.setABS_CNT(Integer.toString(actr));
						sb.setLOP(actr-(rs.getFloat("leavedays")+rs.getFloat("holidays")));
						sb.setHOL_DAYS(rs.getFloat("holidays"));
					} else {
						sb = new SearchBean();
						sb.setEmpno(rs.getInt("empno"));
						sb.setDOJ(rs.getString("doj"));
						sb.setLVDAYS(rs.getString("leavedays"));
						sb.setID(rs.getString("ID"));
						sb.setMON_DAY(rs.getString("MON_DAY"));
						sb.setSPR_CNT(rs.getString("SPR_CNT"));
						sb.setWO_CNT(rs.getString("WO_CNT"));
						sb.setABS_CNT(rs.getString("ABS_CNT"));
						sb.setLOP(rs.getFloat("ABS_CNT")-(rs.getFloat("leavedays")+rs.getFloat("holidays")));
						sb.setHOL_DAYS(rs.getFloat("holidays"));
					}
					
					list.add(sb);
				}
			
			con.close();
		}
     	catch (Exception e) 
     	{
     		UsrHandler.senderrormail(e, "SearchDAO.getAttendRecords");
			e.printStackTrace();
		}
	return list;
	}
	
	public boolean addToCalculate (ArrayList<SearchBean> list, String date, int uid){
		Connection con=ConnectionManager.getConnection();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Statement st=null;
		Statement stmt=null;
		boolean flag = false;
     	try
     	{
				st = con.createStatement();
				stmt = con.createStatement();
				String tdate = format.format(sdf.parse(date)); 
				
				for(SearchBean sb : list){
					ResultSet rs = stmt.executeQuery("select * from ytdtran where empno="+sb.getEmpno()+" and trndt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"'");
					if(rs.next()){
							flag=false;
					}else{
						ResultSet rs1 = stmt.executeQuery("select * from paytran where empno="+sb.getEmpno()+" and trncd=301");
						if(rs1.next()){
							flag = st.execute("Update paytran set TRNDT='"+tdate+"', INP_AMT='"+sb.getLOP()+"', UPDDT='"+tdate+"', USRCODE='"+uid+"' " +
									"where empno="+sb.getEmpno()+" and trncd=301");
						}else{
							String sql = "insert into paytran values('"+tdate+"',"+sb.getEmpno()+",301,0,"+sb.getLOP()+",0,0,0,0,'s',"+uid+",'"+tdate+"')";
							flag = st.execute(sql);
						}
					}
				}
				st.close();
			con.close();
		}
     	catch (Exception e) 
     	{
     		UsrHandler.senderrormail(e, "SearchDAO.addToCalculate");
			e.printStackTrace();
		}
	return flag;
	}
	
	public static boolean checkTran (String date, int trncd, String tname){
		Connection conn = ConnectionManager.getConnection();
		Statement st=null;
		ResultSet rs=null;  
		boolean flag = false;
     	try
     	{
				st = conn.createStatement(); 
				String sql = "select * from "+tname+" where trncd=301 and trndt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"'";
				rs = st.executeQuery(sql);
				if(rs.next()){
					flag = true;
				}else{
					flag = false;
				}
				st.close();
			
			conn.close();
		}
     	catch (Exception e) 
     	{
     		UsrHandler.senderrormail(e, "SearchDAO.checkTran");
			e.printStackTrace();
		}
	return flag;
	}
}
