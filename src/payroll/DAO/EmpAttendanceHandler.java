package payroll.DAO;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import payroll.Core.Calculate;
import payroll.Core.ReportDAO;
import payroll.Model.AttendStatusBean;
import payroll.Model.CompBean;
import payroll.Model.TranBean;
import payroll.Model.UploadAttendanceBean;
import payroll.Model.AttendanceBean;
import payroll.Model.EmpOffBean;
import payroll.Model.TranBean;

import com.ibm.icu.util.StringTokenizer;

public class EmpAttendanceHandler {
	SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
	
	
	public ArrayList getEmpAttend(String date, int Lateempno) {

		ArrayList Emp_al = new ArrayList();
		EmpOffHandler eoffhdlr = new EmpOffHandler();
		EmpOffBean eoffbn = new EmpOffBean();
		ArrayList<TranBean> tran = new ArrayList<TranBean>();

	
			tran = eoffhdlr.getLateEmpList(Integer.toString(Lateempno));
	
		float days = Calculate.getDays(date);

		// System.out.println("into EmpAttendanceHandler");

		Connection conn = ConnectionManager.getConnection();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = null;
			for (TranBean tbean : tran) {
				ArrayList<TranBean> Emp_bean = new ArrayList<TranBean>();
				int count = 0;
				String date1 = ReportDAO.BOM(date);
				rs = st.executeQuery("select * from Emp_attendance where empno="
						+ tbean.getEMPNO()
						
						+ " and attd_date between '"
						+ ReportDAO.BOM(date)
						+ "' and  '"
						+ ReportDAO.EOM(date)
						+ "' order by attd_date");
				while (rs.next()) {
					TranBean ab = new TranBean();
					ab.setEMPNO(rs.getInt("EMPNO"));
					ab.setSite_id(rs.getString("site_id"));
					ab.setDate(rs.getString("attd_date"));
					ab.setVal(rs.getString("attd_val"));
					ab.setSTATUS(rs.getString("emp_status"));
					Emp_bean.add(ab);
					count++;
				}
				if (count < 1) {
					for (int x = 1; x <= days; x++) {
						TranBean ab = new TranBean();
						ab.setEMPNO(tbean.getEMPNO());
					

						try {

							ab.setDate(date1);
							ab.setVal("");
							ab.setSTATUS(rs.getString("emp_status"));
							Emp_bean.add(ab);

							SimpleDateFormat format = new SimpleDateFormat(
									"dd-MMM-yyyy");
							Calendar c = Calendar.getInstance();
							c.setTime(format.parse(date1));
							c.add(Calendar.DATE, 1);
							date1 = format.format(c.getTime());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}

				Emp_al.add(Emp_bean);
			}
			// //System.out.println("Size of Emp_all in handlr= "+Emp_al.size());
			conn.close();

		} catch (SQLException e) {
			// //System.out.println("error into EmpAttendanceHandler");
			e.printStackTrace();
		}

		return Emp_al;
	}

	
	
	
	public ArrayList getEmpAttend(String date, int site_id, int mgr,	String state) {

		ArrayList Emp_al = new ArrayList();
		EmpOffHandler eoffhdlr = new EmpOffHandler();
		EmpOffBean eoffbn = new EmpOffBean();
		ArrayList<TranBean> tran = new ArrayList<TranBean>();

		if (state.equalsIgnoreCase("edit")) {
			eoffbn = eoffhdlr.getEmpOfficAddInfo(Integer.toString(mgr));
			tran = eoffhdlr.getEmpList(Integer.toString(eoffbn.getPrj_srno()));
		} else if (state.equalsIgnoreCase("view"))

		{
			tran = eoffhdlr.getEmpList(Integer.toString(site_id));
		}
		float days = Calculate.getDays(date);

		// System.out.println("into EmpAttendanceHandler");

		Connection conn = ConnectionManager.getConnection();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = null;
			for (TranBean tbean : tran) {
				ArrayList<TranBean> Emp_bean = new ArrayList<TranBean>();
				int count = 0;
				String date1 = ReportDAO.BOM(date);
				rs = st.executeQuery("select * from Emp_attendance where empno="
						+ tbean.getEMPNO()
						+ " and site_id="
						+ site_id
						+ " and attd_date between '"
						+ ReportDAO.BOM(date)
						+ "' and  '"
						+ ReportDAO.EOM(date)
						+ "' order by attd_date");
				while (rs.next()) {
					TranBean ab = new TranBean();
					ab.setEMPNO(rs.getInt(1));
					ab.setSite_id(rs.getString(2));
					ab.setDate(rs.getString(3));
					ab.setVal(rs.getString(4));
					Emp_bean.add(ab);
					count++;
				}
				if (count < 1) {
					for (int x = 1; x <= days; x++) {
						TranBean ab = new TranBean();
						ab.setEMPNO(tbean.getEMPNO());
						ab.setSite_id(Integer.toString(site_id));

						try {

							ab.setDate(date1);
							ab.setVal("");
							Emp_bean.add(ab);

							SimpleDateFormat format = new SimpleDateFormat(
									"dd-MMM-yyyy");
							Calendar c = Calendar.getInstance();
							c.setTime(format.parse(date1));
							c.add(Calendar.DATE, 1);
							date1 = format.format(c.getTime());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}

				Emp_al.add(Emp_bean);
			}
			// //System.out.println("Size of Emp_all in handlr= "+Emp_al.size());
			conn.close();

		} catch (SQLException e) {
			// //System.out.println("error into EmpAttendanceHandler");
			e.printStackTrace();
		}

		return Emp_al;
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public boolean insertEmpAttendance(ArrayList emp_al, int eno) {
		boolean flag = false;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		Calendar c1 = Calendar.getInstance(); // today
	
		String date=sdf.format(c1.getTime());
		String BomDt = ReportDAO.BOM(date);
		String EomDt= ReportDAO.EOM(date);
		
		
		
		EmpAttendanceHandler EAH=new EmpAttendanceHandler();
		String currentdate=EAH.getServerDate();
		// System.out.println("into insertEmpAttendance");
		try {
			Connection conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);

			Statement st = conn.createStatement();
			Statement ad= conn.createStatement();
			ResultSet rs = null;
			for (int j = 0; j < emp_al.size(); j++) {
				ArrayList<TranBean> Emp_bean = (ArrayList<TranBean>) emp_al.get(j);

				for (int c = 0; c < Emp_bean.size(); c++) {
					TranBean ab = new TranBean();
					ab = Emp_bean.get(c);

					
					/*System.out.println("IF EXISTS (SELECT empno FROM  Emp_attendance WHERE empno="
							+ ab.getEMPNO()
							+ " and site_id="
							+ ab.getSite_id()
							+ " and attd_date ='"
							+ ab.getDate()
							+ "')"
							+ "UPDATE  Emp_attendance SET empno="
							+ ab.getEMPNO()
							+ ", attd_date ='"
							+ ab.getDate()
							+ "', ATTD_VAL='"
							+ ab.getVal()
							+ "' where empno="
							+ ab.getEMPNO()
							+ " and site_id="
							+ ab.getSite_id()
							+ " and attd_date ='"
							+ ab.getDate()
							+ "' else"
							+ " INSERT INTO Emp_attendance (empno,  attd_date, attd_val, created_date, created_by) VALUES("
							+ ab.getEMPNO()
							+ ","
							/*+ ab.getSite_id()
							+ ",'"
							+ ab.getDate() + "','" + ab.getVal() + "','"+currentdate+"',"+eno+") ");*/
					st.executeUpdate("IF EXISTS (SELECT empno FROM  Emp_attendance WHERE empno="
							+ ab.getEMPNO()
							/*+ " and site_id="
							+ ab.getSite_id()*/
							+ " and attd_date ='"
							+ ab.getDate()
							+ "')"
							+ "UPDATE  Emp_attendance SET empno="
							+ ab.getEMPNO()
							+ ", attd_date ='"
							+ ab.getDate()
							+ "', ATTD_VAL='"
							+ ab.getVal()
							+ "' where empno="
							+ ab.getEMPNO()
							/*+ " and site_id="
							+ ab.getSite_id()*/
							+ " and attd_date ='"
							+ ab.getDate()
							+ "' else"
							+ " INSERT INTO Emp_attendance (empno,  attd_date, attd_val, created_date, created_by) VALUES("
							+ ab.getEMPNO()
							/*+ ","
							+ ab.getSite_id()*/
							+ ",'"
							+ ab.getDate() + "','" + ab.getVal() + "','"+currentdate+"',"+eno+") ");
					ad.executeUpdate("update emp_attendance set updated_date= '"+currentdate+"', updated_by="+eno+" ");
				
				}
					

			}
		/*	Statement st11=conn.createStatement();
			st11.executeUpdate("update EMP_ATTENDANCE_HIST  set BATCH_NO=(select (case when (SELECT MAX(BATCH_NO) FROM EMP_ATTENDANCE_HIST) IS NULL " 
					+" then  1 else MAX(BATCH_NO+1) end) from EMP_ATTENDANCE_HIST) where BATCH_NO IS NULL");*/
			conn.commit();
			
				
			flag = true;

		} catch (SQLException e) {
			// System.out.println("error into insertEmpAttendance");
			e.printStackTrace();
		}
		return flag;
	}

	public String setAttendanceStatus(String date, int site_id, String status,int eno) {

		// System.out.println("into attendanceApproval");
		// System.out.println(site_id);
		// System.out.println(date);
		// System.out.println(status);
		EmpAttendanceHandler EAH=new EmpAttendanceHandler();
		String currentdate=EAH.getServerDate();
		Connection conn = ConnectionManager.getConnection();
		try {
			conn.setAutoCommit(false);
			if (status.equalsIgnoreCase("approved")) {

				String[] salMonth = null;
				String dt[] = date.split("-");
				Statement st5 = conn.createStatement();
				ResultSet rs4 = st5
						.executeQuery("select SALDATE from SALARY_MONTH ");
				if (rs4.next()) {
					salMonth = rs4.getString("SALDATE").split("-");
				}
				if (Integer.parseInt(salMonth[1]) == Calculate.getMonth(date)
						&& salMonth[0].equals(dt[2])) {

					Statement st1 = conn.createStatement();
					Statement st2 = conn.createStatement();

					ResultSet rs = st2
							.executeQuery("select distinct empno from EMP_ATTENDANCE where ATTD_DATE between '"
									+ ReportDAO.BOM(date)
									+ "' and '"
									+ ReportDAO.EOM(date)
									+ "' and site_id="
									+ site_id + " order by EMPNO ");
					while (rs.next()) {
						float totDed = 0;
						float totPl = 0;
						float totCr = 0;
						float bal = 0;
						ResultSet rs1 = st1
								.executeQuery("SELECT EMPNO,ATTD_DATE,ATTD_VAL FROM EMP_ATTENDANCE where ATTD_DATE between '"
										+ ReportDAO.BOM(date)
										+ "' and '"
										+ ReportDAO.EOM(date)
										+ "' and EMPNO="
										+ rs.getInt("empno")
										+ " order by ATTD_DATE");
						while (rs1.next()) {
							if (rs1.getString("ATTD_VAL").equalsIgnoreCase("A")) {
								totDed++;

							} else if (rs1.getString("ATTD_VAL")
									.equalsIgnoreCase("L")) {

								Statement st = conn.createStatement();
								ResultSet rs5 = null;
								rs5 = st.executeQuery("SELECT TOP 1 * FROM LEAVEBAL WHERE LEAVECD=1 AND EMPNO="
										+ rs1.getInt("EMPNO")
										+ " ORDER BY BALDT DESC");
								if (rs5.next()) {
									Statement st4 = conn.createStatement();
									String leave = "insert into LEAVETRAN (LEAVECD,EMPNO,TRNDATE,TRNTYPE,APPLDT,FRMDT,TODT,STATUS,DAYS) VALUES (1,"
											+ rs1.getInt("EMPNO")
											+ ",'"
											+ rs1.getString("ATTD_DATE")
											+ "','D','"
											+ rs1.getString("ATTD_DATE")
											+ "','"
											+ rs1.getString("ATTD_DATE")
											+ "','"
											+ rs1.getString("ATTD_DATE")
											+ "','SANCTION',1)";
									st4.execute(leave);
									totPl++;
									bal = rs5.getFloat("BAL");
									totCr = rs5.getFloat("TOTCR");
									// String leave2
									// ="insert into LEAVEBAL VALUES ('"+rs1.getString("ATTD_DATE")+"',"+rs5.getInt("EMPNO")+",1,"+rs5.getFloat("BAL")+"-1,"+rs5.getFloat("TOTCR")+",1)";
									// st4.execute(leave2);
								} else {
									Statement st4 = conn.createStatement();
									String leave = "insert into LEAVETRAN (LEAVECD,EMPNO,TRNDATE,TRNTYPE,APPLDT,FRMDT,TODT,STATUS,DAYS) VALUES (1,"
											+ rs1.getInt("EMPNO")
											+ ",'"
											+ rs1.getString("ATTD_DATE")
											+ "','D','"
											+ rs1.getString("ATTD_DATE")
											+ "','"
											+ rs1.getString("ATTD_DATE")
											+ "','"
											+ rs1.getString("ATTD_DATE")
											+ "','SANCTION',1)";
									st4.execute(leave);
									totPl++;
								}
							}
						}
						Statement st3 = conn.createStatement();
						if (totPl != 0) {
							bal = bal - totPl;
							String leave2 = "insert into LEAVEBAL VALUES ('25-"
									+ dt[1] + "-" + dt[2] + "',"
									+ rs.getInt("EMPNO") + ",1," + bal + ","
									+ totCr + "," + totPl + ")";
							st3.execute(leave2);
						}
						if (totDed != 0) {

							
							String sql="if exists(select * from PAYTRAN where TRNCD=301 and EMPNO="+ rs.getString("empno") +"  and  TRNDT between '"+ReportDAO.BOM(date)+"' and  '"+ReportDAO.EOM(date)+"' ) "
										+"update PAYTRAN  set  INP_AMT="+totDed+"   where  TRNCD=301 and EMPNO="+ rs.getString("empno") +"  and  TRNDT between '"+ReportDAO.BOM(date)+"' and  '"+ReportDAO.EOM(date)+"'"
										+" else  "
							+ "INSERT INTO PAYTRAN VALUES('25-"
									+ dt[1] + "-" + dt[2] + "',"
									+ rs.getString("empno") + ",301,0,"
									+ totDed + ",0,0,0," + totDed
									+ ",'0','','25-" + dt[1] + "-" + dt[2]
									+ "','P')";
							
							st3.execute(sql);
						}

					}

					Statement st = conn.createStatement();
			//		System.out.println("EMPO"+eno);
					st.executeUpdate("IF EXISTS (SELECT * from Attendance_status where site_id="
							+ site_id
							+ " and att_month between '"
							+ ReportDAO.BOM(date)
							+ "' and '"
							+ ReportDAO.EOM(date)
							+ "')"
							+ " update  Attendance_status set appr_status='"
							+ status
							+ "', updated_by= "+eno+" , updated_date= '"+currentdate+"' ,submit_date='"
							+ ReportDAO.getSysDate()
							+ "' where site_id="
							+ site_id
							+ " and att_month between '"
							+ ReportDAO.BOM(date)
							+ "' and '"
							+ ReportDAO.EOM(date)
							+ "' "
							+ " ELSE insert into Attendance_status  (site_id, appr_date, appr_status, att_month, submit_date, created_by, created_date) values("
							+ site_id
							+ ",'"
							+ ReportDAO.getSysDate()
							+ "','"
							+ status
							+ "','"
							+ date
							+ "','"
							+ ReportDAO.getSysDate() + "', "+eno+" , '"+currentdate+"')");
					
					
					
				} else {
					return status = "notMatch";
				}
				Statement pre = conn.createStatement();
				String Pr = "update EMP_ATTENDANCE set ATTD_VAL='P' where ATTD_VAL='' and ATTD_DATE between '"
						+ ReportDAO.BOM(date)
						+ "' and '"
						+ ReportDAO.EOM(date)
						+ "'";
				pre.execute(Pr);
			} else {

				Statement st = conn.createStatement();
				st.executeUpdate("IF EXISTS (SELECT * from Attendance_status where site_id="
						+ site_id
						+ " and att_month between '"
						+ ReportDAO.BOM(date)
						+ "' and '"
						+ ReportDAO.EOM(date)
						+ "')"
						+ " update  Attendance_status set appr_status='"
						+ status
						+ "', updated_by= "+eno+" , updated_date= '"+currentdate+"', submit_date='"
						+ ReportDAO.getSysDate()
						+ "' where site_id="
						+ site_id
						+ " and att_month between '"
						+ ReportDAO.BOM(date)
						+ "' and '"
						+ ReportDAO.EOM(date)
						+ "' "
						+ " ELSE insert into Attendance_status (site_id, appr_date, appr_status, att_month, submit_date, created_by, created_date) values("
						+ site_id
						+ ",'"
						+ ReportDAO.getSysDate()
						+ "','"
						+ status
						+ "','"
						+ date
						+ "' ,'"
						+ ReportDAO.getSysDate() + "' ,"+eno+", '"+currentdate+"' )");

			}

			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				System.out.println("Error in closing connection");
				e.printStackTrace();
			}
		}
		return status;
	}

	public String getAttendanceStatus(String date, int site_id) {

		// System.out.println("into getAttendanceStatus");
		ResultSet rs = null;
		int count = 0;
		String status = "";
		try {
			Connection conn = ConnectionManager.getConnection();
			Statement st = conn.createStatement();
			rs = st.executeQuery("SELECT appr_status from Attendance_status where site_id="
					+ site_id
					+ " and att_month between '"
					+ ReportDAO.BOM(date)
					+ "' and '"
					+ ReportDAO.EOM(date)
					+ "'");
			while (rs.next()) {
				status = rs.getString(1);
				count++;
			}
			if (count < 1) {
				status = "saved";
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	public ArrayList<AttendStatusBean> getAllProjectAttendanceStatus(
			ArrayList<EmpOffBean> projlist, String status) {
		ArrayList<AttendStatusBean> proj_attend_state = new ArrayList<AttendStatusBean>();
		ResultSet rs = null;

		try {
			Connection conn = ConnectionManager.getConnection();
			Statement st = conn.createStatement();
			/*
			 * for(EmpOffBean lkb :projlist) {
			 */int count = 0;
			if (status.equalsIgnoreCase("all")) {
				rs = st.executeQuery("SELECT * from Attendance_status where appr_status<>'saved' order by att_month,appr_status ");// and
																										// att_month
																										// between
																										// '"+ReportDAO.BOM(date)+"'
																										// and
																										// '"+ReportDAO.EOM(date)+"'
																										// ");
			} else {

				rs = st.executeQuery("SELECT * from Attendance_status where  appr_status='"
						+ status + "' order by att_month ");// and att_month
															// between
															// '"+ReportDAO.BOM(date)+"'
															// and
															// '"+ReportDAO.EOM(date)+"'
															// ");
			}
			while (rs.next()) {
				AttendStatusBean asb = new AttendStatusBean();
				asb.setSite_id(rs.getInt(1));
				asb.setAppr_DATE(format.format(rs.getDate(2)));
				asb.setAppr_status(rs.getString(3));
				asb.setAtt_month(format.format(rs.getDate(4)));
				proj_attend_state.add(asb);
				count++;
			}
			/*
			 * if(count<1) // if value not found in table place status 0 default
			 * { AttendStatusBean asb=new AttendStatusBean();
			 * asb.setSite_id(lkb.getPrj_srno()); asb.setAppr_DATE("");
			 * asb.setAppr_status(""); asb.setAtt_month("");
			 * proj_attend_state.add(asb); }
			 */

			/* } */
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return proj_attend_state;
	}

	
	public ArrayList<AttendStatusBean> getAllAttendanceStatus(ArrayList<EmpOffBean> projlist, String status) {
		ArrayList<AttendStatusBean> proj_attend_state = new ArrayList<AttendStatusBean>();
		ResultSet rs = null;

		try {
			Connection conn = ConnectionManager.getConnection();
			Statement st = conn.createStatement();
			int count = 0;
			
			if (status.equalsIgnoreCase("all")) {
				rs = st.executeQuery("SELECT * from Attendance_status order by att_month,appr_status ");
			} 
			else if(status.equalsIgnoreCase("left")) {
				rs = st.executeQuery("SELECT * from Attendance_status where tranevent='"+status+"'  order by empno");
			}
			else if(status.equalsIgnoreCase("transfer")) {
				rs = st.executeQuery("SELECT * from Attendance_status where tranevent='"+status+"'  order by empno");
			}
			else {
				rs = st.executeQuery("SELECT * from Attendance_status where  appr_status='"
						+ status + "' and empno is  null order by att_month ");
			}
			while (rs.next()) {
				AttendStatusBean asb = new AttendStatusBean();
				asb.setSite_id(rs.getInt(1));
				asb.setAppr_DATE(format.format(rs.getDate(2)));
				asb.setAppr_status(rs.getString(3));
				asb.setAtt_month(format.format(rs.getDate(4)));
				asb.setSubmit_date(format.format(rs.getDate(5)));
				asb.setEmpno(rs.getInt(10));
				asb.setTranEvent(rs.getString(11));
				proj_attend_state.add(asb);
				count++;
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
   /* System.out.println("size of proj_attend_state "+proj_attend_state.size());*/
		return proj_attend_state;
	}
	
	
	
	
	
	
	
	
	public String getServerDate() {

		Connection conn = ConnectionManager.getConnection();

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		String today = "";
		try {
			conn = ConnectionManager.getConnection();
			Statement st = conn.createStatement();
			String s = "select getdate() as date";
			ResultSet rslt = st.executeQuery(s);
			if (rslt.next()) {
				today = sdf.format(rslt.getDate("date"));
			}

			conn.close();
		}

		catch (Exception e) {
			today=ReportDAO.getSysDate();
			e.printStackTrace();
		}
		return today;
	}

	
	
	public ArrayList<AttendStatusBean> getAllAttendanceStatus(ArrayList<EmpOffBean> projlist, String status,int eno,int site) {
		ArrayList<AttendStatusBean> proj_attend_state = new ArrayList<AttendStatusBean>();
		ResultSet rs = null;

		try {
			Connection conn = ConnectionManager.getConnection();
			Statement st = conn.createStatement();
			int count = 0;
			
			if (status.equalsIgnoreCase("all")) {
				rs = st.executeQuery("select * from ATTENDANCE_STATUS where site_id="+site+" " +
						"or site_id in( select site_id from ATTENDANCE_SITE_RIGHTS where EMPNO="+eno+")" +
						"order by att_month,appr_status ");
			} 
			else if(status.equalsIgnoreCase("left")) {
				rs = st.executeQuery("SELECT * from Attendance_status where tranevent='"+status+"'  order by empno");
			}
			else if(status.equalsIgnoreCase("transfer")) {
				rs = st.executeQuery("SELECT * from Attendance_status where tranevent='"+status+"'  order by empno");
			}
			else {
				/*rs = st.executeQuery("SELECT * from Attendance_status where  appr_status='"
						+ status + "' and empno is  null order by att_month ");*/
				rs = st.executeQuery("select * from ATTENDANCE_STATUS where appr_status='"+status+"' and empno is null and site_id="+site+" " +
						"or site_id in( select site_id from ATTENDANCE_SITE_RIGHTS where EMPNO="+eno+")" +
						"order by att_month,appr_status ");
			}
			while (rs.next()) {
				AttendStatusBean asb = new AttendStatusBean();
				asb.setSite_id(rs.getInt(1));
				asb.setAppr_DATE(format.format(rs.getDate(2)));
				asb.setAppr_status(rs.getString(3));
				asb.setAtt_month(format.format(rs.getDate(4)));
				asb.setSubmit_date(format.format(rs.getDate(5)));
				asb.setEmpno(rs.getInt(10));
				asb.setTranEvent(rs.getString(11));
				proj_attend_state.add(asb);
				count++;
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
   /* System.out.println("size of proj_attend_state "+proj_attend_state.size());*/
		return proj_attend_state;
	}

	public ArrayList<TranBean> getAssignedSitesList(int empNo) throws SQLException {
		ArrayList<TranBean> Emp_bean = new ArrayList<TranBean>();
		Connection conn = ConnectionManager.getConnection();
		Statement st = conn.createStatement();
		ResultSet rs = null;
		
		rs=st.executeQuery("select site_id from ATTENDANCE_SITE_RIGHTS where empno="+empNo+ " ");
		while (rs.next()) {
			TranBean ab = new TranBean();
		
			ab.setSite_id(rs.getString("site_id"));
			
			Emp_bean.add(ab);
		}
		return Emp_bean;
	}
	
	
	public ArrayList<CompBean> getAssignweek(String Project) throws SQLException {
		ArrayList<CompBean> Emp_bean = new ArrayList<CompBean>();
		Connection conn = ConnectionManager.getConnection();
		Statement st = conn.createStatement();
		ResultSet rs = null;
		
		rs=st.executeQuery("select RepeatHold, HTEXT from HOLDMAST where BRANCH= '"+Project+"' ");
		while (rs.next()) {
			CompBean ab = new CompBean();
		
			ab.setWoffday((rs.getString("RepeatHold")));
			ab.setAltsat((rs.getString("HTEXT")));
			
			Emp_bean.add(ab);
		}
		return Emp_bean;
	}
	
	public boolean deleteAssignedSitesList(int empNo) throws SQLException {
		boolean flag = false;
		Connection conn = ConnectionManager.getConnection();
		Statement st = conn.createStatement();
		
		
		st.executeUpdate("delete from ATTENDANCE_SITE_RIGHTS where empno="+empNo+ " ");
		conn.commit();
		
		
		flag = true;
		return true;
	}
	
	
	public boolean assignSite(ArrayList siteSelectedList, int empNo)
	{
		
		
		boolean flag = false;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		Calendar c1 = Calendar.getInstance(); // today

		
		
		
		EmpAttendanceHandler EAH=new EmpAttendanceHandler();
		String currentdate=EAH.getServerDate();

		try {
			
			Connection conn = ConnectionManager.getConnection();
			conn.setAutoCommit(false);

			Statement st = conn.createStatement();
			Statement ad= conn.createStatement();
			ResultSet rs = null;
			
			ArrayList<TranBean> tranList =	 (ArrayList<TranBean>) siteSelectedList.get(0);
			TranBean tranBean1 = tranList.get(0);
int empnum=tranBean1.getEMPNO();
 
 st.executeUpdate("delete  ATTENDANCE_SITE_RIGHTS where empno="+empnum);
 
			for (int j = 0; j < siteSelectedList.size(); j++) {
			
			 tranList = (ArrayList<TranBean>) siteSelectedList.get(j);

				for (int c = 0; c < tranList.size(); c++) {
					TranBean tranBean = new TranBean();
					tranBean = tranList.get(c);
	  
					
			st.executeUpdate("insert into ATTENDANCE_SITE_RIGHTS (empno, site_id , created_by, created_date)" +
					" values("+tranBean.getEMPNO()+" , "+tranBean.getSite_id()+" , "+empNo+", '"+currentdate+"' ) ");
			
		

				}
					

			}
		
			conn.commit();
			
				
			flag = true;

		} catch (SQLException e) {
			// System.out.println("error into insertEmpAttendance");
			e.printStackTrace();
		}
		return flag;
}
	 public boolean weekoff(CompBean cb) throws IOException, SQLException{

			boolean result = true;
			Connection con = null;
		
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

	con= ConnectionManager.getConnection();
	Statement dt = con.createStatement();


	dt.execute(" delete from HOLDMAST  where branch = '"+cb.getCname()+"' " +
		"and  RepeatHold= 'sun' or RepeatHold='mon' or RepeatHold='tue'  "+
		"	 or RepeatHold= 'wed' or RepeatHold= 'thu' or RepeatHold= 'fri' "+
		"	 or RepeatHold= 'sat'  "+
		"	and  htext='1' or htext ='2' or htext='3' or htext='4' or htext='5'  ");

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
//		System.out.println("in addsunday mast method");
	st.execute("insert into HOLDMAST(HOLDNAME,FDATE,TDATE,BRANCH,HTEXT,HTYPE,RepeatHold,HDAY,HATTENDENCES,HSMS,OptionalHold)" +
			" values('Week OFF','"+frm+"' ,'"+frm+"' ,'"+cb.getCname()+"','Weekly OFF','Weekly Off','sun','No','No','No','No')");

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

	st.execute("insert into HOLDMAST(HOLDNAME,FDATE,TDATE,BRANCH,HTEXT,HTYPE,RepeatHold,HDAY,HATTENDENCES,HSMS,OptionalHold) " +
			"values('Week OFF','"+frm+"' ,'"+frm+"' ,'"+cb.getCname()+"','Weekly OFF','Weekly Off','mon','No','No','No','No')");

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

	st.execute("insert into HOLDMAST(HOLDNAME,FDATE,TDATE,BRANCH,HTEXT,HTYPE,RepeatHold,HDAY,HATTENDENCES,HSMS,OptionalHold) " +
			"values('Week OFF','"+frm+"' ,'"+frm+"' ,'"+cb.getCname()+"','Weekly OFF','Weekly Off','tue','No','No','No','No')");

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



	st.execute("insert into HOLDMAST(HOLDNAME,FDATE,TDATE,BRANCH,HTEXT,HTYPE,RepeatHold,HDAY,HATTENDENCES,HSMS,OptionalHold) " +
			"values('Week OFF','"+frm+"' ,'"+frm+"' ,'"+cb.getCname()+"','Weekly OFF','Weekly Off','wed','No','No','No','No')");

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

	st.execute("insert into HOLDMAST(HOLDNAME,FDATE,TDATE,BRANCH,HTEXT,HTYPE,RepeatHold,HDAY,HATTENDENCES,HSMS,OptionalHold) " +
			"values('Week OFF','"+frm+"' ,'"+frm+"' ,'"+cb.getCname()+"','Weekly OFF','Weekly Off','thu','No','No','No','No')");

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

	st.execute("insert into HOLDMAST(HOLDNAME,FDATE,TDATE,BRANCH,HTEXT,HTYPE,RepeatHold,HDAY,HATTENDENCES,HSMS,OptionalHold) " +
			"values('Week OFF','"+frm+"' ,'"+frm+"' ,'"+cb.getCname()+"','Weekly OFF','Weekly Off','fri','No','No','No','No')");

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

	st.execute("insert into HOLDMAST(HOLDNAME,FDATE,TDATE,BRANCH,HTEXT,HTYPE,RepeatHold,HDAY,HATTENDENCES,HSMS,OptionalHold) " +
			"values('Week OFF','"+frm+"' ,'"+frm+"' ,'"+cb.getCname()+"','Weekly OFF','Weekly Off','sat','No','No','No','No')");

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

	st.execute("insert into HOLDMAST(HOLDNAME,FDATE,TDATE,BRANCH,HTEXT,HTYPE,RepeatHold,HDAY,HATTENDENCES,HSMS,OptionalHold) " +
			"values('Week OFF','"+frm+"' ,'"+frm+"' ,'"+cb.getCname()+"','1','Weekly Off','No','No','No','No','No')");
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

	st.execute("insert into HOLDMAST(HOLDNAME,FDATE,TDATE,BRANCH,HTEXT,HTYPE,RepeatHold,HDAY,HATTENDENCES,HSMS,OptionalHold)" +
			" values('Week OFF','"+frm+"' ,'"+frm+"' ,'"+cb.getCname()+"','2','Weekly Off','No','No','No','No','No')");
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

	st.execute("insert into HOLDMAST(HOLDNAME,FDATE,TDATE,BRANCH,HTEXT,HTYPE,RepeatHold,HDAY,HATTENDENCES,HSMS,OptionalHold)" +
			" values('Week OFF','"+frm+"' ,'"+frm+"' ,'"+cb.getCname()+"','3','Weekly Off','No','No','No','No','No')");
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

	st.execute("insert into HOLDMAST(HOLDNAME,FDATE,TDATE,BRANCH,HTEXT,HTYPE,RepeatHold,HDAY,HATTENDENCES,HSMS,OptionalHold) " +
			"values('Week OFF','"+frm+"' ,'"+frm+"' ,'"+cb.getCname()+"','4','Weekly Off','No','No','No','No','No')");
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

	st.execute("insert into HOLDMAST(HOLDNAME,FDATE,TDATE,BRANCH,HTEXT,HTYPE,RepeatHold,HDAY,HATTENDENCES,HSMS,OptionalHold) " +
			"values('Week OFF','"+frm+"' ,'"+frm+"' ,'"+cb.getCname()+"','5','Weekly Off','No','No','No','No','No')");
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



			return result;
			
		
	 }
	 
	 
		public ArrayList<UploadAttendanceBean> getEmpAttendance1(String act) 
		{
			System.out.println("into EmpAttendanceHandler");
				
			/*	ArrayList Emp_al = new ArrayList();*/
				EmpOffHandler eoffhdlr = new EmpOffHandler();
				EmpOffBean eoffbn = new EmpOffBean();
				/*date ="1-"+date;*/
				System.out.println("into EmpAttendanceHandler :");
				
				ArrayList<UploadAttendanceBean> Emp_bean = new ArrayList<UploadAttendanceBean>();

				Connection conn = ConnectionManager.getConnection();
				try {
					Statement st = conn.createStatement();
					ResultSet rs = null;
					
					Statement stdate = conn.createStatement();
					//ResultSet  rsdate = stdate.executeQuery("select max(attend_date) from employee_attendance");
					
					ResultSet  rsdate = stdate.executeQuery("select max(trndt) from paytran");
					String date ="";
					if(rsdate.next())
					{
						date = rsdate.getString(1);
					}
						int count = 0;
						System.out.println("into EmpAttendanceHandler emp attend : "+date);
						
						String SQL = "";
						
					/*	if(act.equalsIgnoreCase("allAttend"))
						{
						System.out.println("Check 1");
						  SQL = "select E.EMPCODE, E.EMPNO,RTRIM(E.FNAME)+' '+RTRIM(E.MNAME)+' '+ RTRIM(E.LNAME) AS NAME , EA.* from Employee_Attendance EA,empmast E " 
									+" where e.EMPCODE = EA.EMPCODE " 
									+" and EA.ATTEND_DATE = '"+date  +"' "
							//	+" AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM1(date)+"')  or (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM1(date)+"' )) "
										+" AND (( E.STATUS='A' AND E.DOJ <= '"+date+"')  or (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM1(date)+"' )) "
									+" order by empcode";
						}
						else if(act.equalsIgnoreCase("spAttend"))
						{
							System.out.println("Check 2");
							  SQL = "select E.EMPCODE, E.EMPNO,RTRIM(E.FNAME)+' '+RTRIM(E.MNAME)+' '+ RTRIM(E.LNAME) AS NAME , EA.* from Employee_Attendance EA,empmast E " 
									+" where e.EMPCODE = EA.EMPCODE " 
									+" and EA.ATTEND_DATE = '"+date  +"' "
									+"  and ea.status = 'PROCESS'  "
							//	+" AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM1(date)+"')  or (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM1(date)+"' )) "
							+" AND (( E.STATUS='A' AND E.DOJ <= '"+date+"')  or (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM1(date)+"' )) "
									+" order by empcode";
						}
					*/	
						
						if(act.equalsIgnoreCase("allAttend"))
						{
						System.out.println("Check 1");
						  SQL = "select E.EMPCODE, E.EMPNO,RTRIM(E.FNAME)+' '+RTRIM(E.MNAME)+' '+ RTRIM(E.LNAME) AS NAME , EA.* from Employee_Attendance_OLD EA,empmast E " 
									+" where (( E.STATUS='A' AND E.DOJ <= '"+date+"')  or (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM1(date)+"' )) "
									+" order by empcode";
						}
						else if(act.equalsIgnoreCase("spAttend"))
						{
							System.out.println("Check 2");
							  SQL = "select E.EMPCODE, E.EMPNO,RTRIM(E.FNAME)+' '+RTRIM(E.MNAME)+' '+ RTRIM(E.LNAME) AS NAME , EA.* from Employee_Attendance_OLD EA,empmast E " 
									+" where ea.status = 'PROCESS'  "
							//	+" AND (( E.STATUS='A' AND E.DOJ <= '"+ReportDAO.EOM1(date)+"')  or (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM1(date)+"' )) "
							+" AND (( E.STATUS='A' AND E.DOJ <= '"+date+"')  or (E.STATUS ='N' And  E.DOL>='"+ReportDAO.BOM1(date)+"' )) "
									+" order by empcode";
						}
					
						
						System.out.println("sqlfff ::"+SQL);
						rs = st.executeQuery(SQL);
						while (rs.next()) {
							
							UploadAttendanceBean ab = new UploadAttendanceBean();
							/*System.out.println("rs.getString(day1) :"+rs.getString("day1"));*/
							
							ab.setEMPCODE(rs.getString("EMPCODE"));
							ab.setEMPNO(rs.getString("EMPNO"));
							ab.setName(rs.getString("NAME"));
							ab.setDays1(rs.getString("day1"));
							ab.setDays2(rs.getString("day2"));
							ab.setDays3(rs.getString("day3"));
							ab.setDays4(rs.getString("day4"));
							ab.setDays5(rs.getString("day5"));
							ab.setDays6(rs.getString("day6"));
							ab.setDays7(rs.getString("day7"));
							ab.setDays8(rs.getString("day8"));
							ab.setDays9(rs.getString("day9"));
							ab.setDays10(rs.getString("day10"));
							ab.setDays11(rs.getString("day11"));
							ab.setDays12(rs.getString("day12"));
							ab.setDays13(rs.getString("day13"));
							ab.setDays14(rs.getString("day14"));
							ab.setDays15(rs.getString("day15"));
							ab.setDays16(rs.getString("day16"));
							ab.setDays17(rs.getString("day17"));
							ab.setDays18(rs.getString("day18"));
							ab.setDays19(rs.getString("day19"));
							ab.setDays20(rs.getString("day20"));
							ab.setDays21(rs.getString("day21"));
							ab.setDays22(rs.getString("day22"));
							ab.setDays23(rs.getString("day23"));
							ab.setDays24(rs.getString("day24"));
							ab.setDays25(rs.getString("day25"));
							ab.setDays26(rs.getString("day26"));
							ab.setDays27(rs.getString("day27"));
							ab.setDays28(rs.getString("day28"));
							ab.setDays29(rs.getString("day29"));
							ab.setDays30(rs.getString("day30"));
							ab.setDays31(rs.getString("day31"));
							ab.setDateG(rs.getString("ATTEND_DATE"));
							ab.setStatus(rs.getString("STATUS"));



							
							Emp_bean.add(ab);
							count++;
						}
						

						
					
					// //System.out.println("Size of Emp_all in handlr= "+Emp_al.size());
					conn.close();

				} catch (SQLException e) {
					// //System.out.println("error into EmpAttendanceHandler");
					e.printStackTrace();
				}

				return Emp_bean;
			}
		
		
		
		
		public static float getEmpAbsentDay(String empcode,String date)  
		{
			float abDays = 0;
			float  hdDays= 0.5f;
			try
			   {
					Connection conn = ConnectionManager.getConnection();
					Statement ST = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					ResultSet rs = null;
					String sql ="select * from employee_attendance where empcode='"+empcode+"' and Attend_Date='"+date+"'";
				 System.out.println("sql for ab days : "+sql); 
					rs = ST.executeQuery(sql);
					ResultSetMetaData rsmd = rs.getMetaData();
					int columnCount = rsmd.getColumnCount();//count coulumn
					int k = 1;
					String res ="";
					while(rs.next())
					{
						for(int i= 0;i<columnCount;i++)
						{
							res = rs.getString(rsmd.getColumnName(k++));
						/*	System.out.println(" res : "+res);*/
							if(res.equalsIgnoreCase("A") || res.equalsIgnoreCase(" A") || res.equalsIgnoreCase("A ") || res.equalsIgnoreCase(" A "))
							{
							/*	 System.out.println(" abDays");*/
								abDays++;
							}else  if(res.equalsIgnoreCase("HD") || res.equalsIgnoreCase(" HD") || res.equalsIgnoreCase("HD ") || res.equalsIgnoreCase(" HD "))
							{
								abDays+=hdDays;
							}
						}
					}
					
			    System.out.println(" abDays : "+abDays); 
			   }catch (SQLException e) 
			   	{
				   e.printStackTrace();
				}
			return abDays;
		}

}
