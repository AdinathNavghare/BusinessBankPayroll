package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import payroll.Core.ReportDAO;
import payroll.Model.EmployeeBean;
import payroll.Model.Lookup;
import payroll.Model.Temp_Emp_Sal_Detail;

public class EmployeeHandler {
	Connection conn;
	LookupHandler lkhp = new LookupHandler();
	Lookup lookupBean = new Lookup();

	public EmployeeBean getEmployeeInformation(String EMPNO) {
		conn = ConnectionManager.getConnection();
		ResultSet rs;
		SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd");
		fromformat.setLenient(false);
		EmployeeBean empbean = new EmployeeBean();
		try {
			
			Statement stmt = conn.createStatement();

			String query = "select * from  empmast  where EMPNO=" + EMPNO + "";

			/*
			 * String query = " select E.*, ET.DESIG,ET.BRANCH , " +
			 * " isnull( (select top 1 SRNO from GRADE_MASTER where BASIC =( select top 1 inp_amt from CTCDISPLAY where EMPNO ="
			 * +EMPNO+
			 * " and TRNCD = 101) and GRADE_CODE = ET.DESIG and GRADE_STATUS =0 ),0) as stage "
			 * + " from EMPMAST E, EMPTRAN ET where E.EMPNO="+EMPNO+
			 * "  AND E.EMPNO =ET.EMPNO" +
			 * " and ET.srno=(select max(srno) from emptran where EMPNO="
			 * +EMPNO+" ) ";
			 */

			// rs=stmt.executeQuery("select E.*, ET.DESIG from EMPMAST E, EMPTRAN ET where ET.EMPNO = "+EMPNO+"  AND ET.EMPNO =E.EMPNO");

			rs = stmt.executeQuery(query);
			// System.out.println(query);

			while (rs.next()) {
				
			
				System.out.println("blood group    : "+rs.getString("BGRP") == null ? "" : rs
						.getString("BGRP"));
				
				
				empbean.setEMPNO(rs.getString("EMPNO") == null ? 0 : rs
						.getInt("EMPNO"));
				empbean.setFNAME(rs.getString("FNAME") == null ? "" : rs
						.getString("FNAME"));
				empbean.setLNAME(rs.getString("LNAME") == null ? "" : rs
						.getString("LNAME"));
				empbean.setPFNO((rs.getString("PFNO") == null ? "--" : rs
						.getString("PFNO")));
				empbean.setDOJ(rs.getDate("DOJ") == null ? "" : dateFormat(rs
						.getDate("DOJ")));
				
				empbean.setBGRP(rs.getString("BGRP") == null ? "0" : rs
						.getString("BGRP"));
				
				empbean.setBPLACE((rs.getString("BPLACE") == null ? "--" 
						: rs.getString("BPLACE")));
				empbean.setCASTCD(rs.getString("CASTCD") == null ? 0 : rs
						.getInt("CASTCD"));
				empbean.setCATEGORYCD(rs.getString("CATEGORYCD") == null ? 0
						: rs.getInt("CATEGORYCD"));
				empbean.setDA_SCHEME(rs.getString("DA_SCHEME") == null ? ""
						: rs.getString("DA_SCHEME"));
				empbean.setDEPAMT((int) Double.parseDouble((rs
						.getString("DEPAMT") == null ? "0" : rs
						.getString("DEPAMT"))));
				empbean.setDISABILPER(rs.getString("DISABILPER") == null ? "--"
						: rs.getString("DISABILPER"));
				empbean.setDISABILYN(rs.getString("DISABILYN") == null ? "N"
						: rs.getString("DISABILYN"));
				empbean.setDOB(rs.getDate("DOB") == null ? "" : dateFormat(rs
						.getDate("DOB")));
				empbean.setDOL(rs.getDate("DOL") == null ? "" : dateFormat(rs
						.getDate("DOL")));
				empbean.setDRVLISCNNO((rs.getString("DRVLISCNNO") == null ? "--"
						: rs.getString("DRVLISCNNO")));
				empbean.setFHNAME(rs.getString("FHNAME") == null ? "" : rs
						.getString("FHNAME"));
				empbean.setHEIGHT(rs.getString("HEIGHT") == null ? 0 : Double
						.parseDouble(rs.getString("HEIGHT")));
				empbean.setHOBBYCD(rs.getString("HOBBYCD") == null ? "" : rs
						.getString("HOBBYCD"));
				empbean.setIDENTITY((rs.getString("IDENTY") == null ? "--"
						: (rs.getString("IDENTY"))));
				empbean.setMARRIED(rs.getString("MARRIED") == null ? "U" : rs
						.getString("MARRIED"));
				empbean.setMARRIEDDT(rs.getDate("MARRIEDDT") == null ? ""
						: dateFormat(rs.getDate("MARRIEDDT")));
				empbean.setMLWFNO((rs.getString("MLWFNO") == null ? "--" : (rs
						.getString("MLWFNO"))));
				empbean.setMNAME(rs.getString("MNAME") == null ? "-" : rs
						.getString("MNAME"));
				empbean.setOTHERDTL((rs.getString("OTHERDTL") == null ? "----"
						: (rs.getString("OTHERDTL"))));
				empbean.setPANNO((rs.getString("PANNO")) == null ? "--" : (rs
						.getString("PANNO")));
				empbean.setPASSPORTNO((rs.getString("PASSPORTNO")) == null ? "--"
						: rs.getString("PASSPORTNO"));
				empbean.setPFNOMINEE(rs.getString("PFNOMINEE") == null ? "--"
						: rs.getString("PFNOMINEE"));
				empbean.setPFNOMIREL(rs.getString("PFNOMIREL") == null ? "--"
						: rs.getString("PFNOMIREL"));
				empbean.setPFOPENDT(rs.getDate("PFOPENDT") == null ? ""
						: dateFormat(rs.getDate("PFOPENDT")));
				empbean.setPFNO(rs.getString("PFNO") == null ? "--" : rs
						.getString("PFNO"));
				empbean.setRESIDSTAT(rs.getString("RESIDSTAT") == null ? 0 : rs
						.getInt("RESIDSTAT"));
				empbean.setRELEGENCD(rs.getString("RELEGENCD") == null ? 0 : rs
						.getInt("RELEGENCD"));
				empbean.setRESNLFTCD(Integer
						.parseInt(rs.getString("RESNLFTCD") == null ? "0" : rs
								.getString("RESNLFTCD")));
				empbean.setSALUTE(rs.getString("SALUTE") == null ? 0 : rs
						.getInt("SALUTE"));
				empbean.setSBOND(rs.getString("SBOND") == null ? "N" : rs
						.getString("SBOND"));
				empbean.setGENDER(rs.getString("GENDER") == null ? "" : rs
						.getString("GENDER"));
				empbean.setSTATUS(rs.getString("STATUS") == null ? "" : rs
						.getString("STATUS"));
				empbean.setVEHICLEDES((rs.getString("VEHICLEDES") == null ? "--"
						: rs.getString("VEHICLEDES")));
				empbean.setWEIGHT(rs.getString("WEIGHT") == null ? 0.0 : Double
						.parseDouble(rs.getString("WEIGHT")));
				empbean.setAADHAARNUM(rs.getString("AADHAARNUM") == null ? "--"
						: rs.getString("AADHAARNUM"));
				
				empbean.setEMPTYPE(rs.getString("EMPLOYEE_TYPE") == null ? 0
						: rs.getInt("EMPLOYEE_TYPE"));
				empbean.setEMPCODE(rs.getString("EMPCODE"));
				empbean.setLEAVEDATE(rs.getDate("leavecnfdate") == null ? "": dateFormat(rs.getDate("leavecnfdate")));
				/*empbean.setGRADE(rs.getInt("desig"));
				empbean.setSTAGE(rs.getInt("stage"));
				empbean.setSite_id(rs.getInt("BRANCH"));*/
				empbean.setCONFIRM_DATE(rs.getDate("CONFIRMATIONDATE") == null ? "":dateFormat(rs.getDate("CONFIRMATIONDATE")));
				empbean.setRETIREMENT_EXT_PERIOD(rs.getDate("RETIREMENT_EXT_PERIOD") == null ? "":dateFormat(rs.getDate("RETIREMENT_EXT_PERIOD")));
				empbean.setFILENUMBER(rs.getString("FILENUMBER") == null ? " ":(rs.getString("FILENUMBER")));
				empbean.setPROB_START_DATE(rs.getString("prob_start_date") == null ? " ":(rs.getString("prob_start_date")));
				empbean.setPROB_END_DATE(rs.getString("prob_end_date") == null ? " ":(rs.getString("prob_end_date")));
				empbean.setLIC_GRAT_NO(Integer
						.parseInt(rs.getString("LIC_GRAT_NO") ==null ? "0":(rs.getString("LIC_GRAT_NO"))));
				empbean.setLIC_LEAVE_ENCASH_NO(Integer
						.parseInt(rs.getString("LIC_lEAVE_ENCASH_NO") ==null ? "0":(rs.getString("LIC_lEAVE_ENCASH_NO"))));
				empbean.setLIC_GROUP_INSURANCE_NO(Integer
						.parseInt(rs.getString("LIC_GROUP_INSURANCE_NO") ==null ?"0":(rs.getString("LIC_GROUP_INSURANCE_NO"))));
				empbean.setLIC_LEAVE_ENCASH_POLICY_NO(Integer
						.parseInt(rs.getString("LIC_LEAVE_ENCASH_POLICY_NO") ==null ? "0":(rs.getString("LIC_LEAVE_ENCASH_POLICY_NO"))));
				empbean.setLIC_GRAT_POLICY_NO(Integer
						.parseInt(rs.getString("LIC_GRAT_POLICY_NO") ==null ? "0":(rs.getString("LIC_GRAT_POLICY_NO"))));
				
		
				
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return empbean;
	}

	public static String getSalaryMonth(int empno) {

		String salaryDate = "";
		try {
			Connection conn = null;
			conn = ConnectionManager.getConnection();
			Statement stmt = conn.createStatement();
			String query = "select replace((convert(varchar,  max(trndt), 106)), ' ', '-') from PAYTRAN_STAGE where EMPNO="
					+ empno + "";
			System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);

			if (rs.next()) {
				salaryDate = rs.getString(1);
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return salaryDate;

	}

	public static String getEmpcode(int empno) {
		String empcode = "";
		try {
			Connection conn = null;
			conn = ConnectionManager.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("select empcode from EMPMAST where EMPNO="
							+ empno);

			if (rs.next()) {
				empcode = rs.getString(1);
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return empcode;

	}

	public boolean updateEmployeeInfo(EmployeeBean empbean) {
		System.out.println("IN updateEmployeeInfo.... ");
		Boolean flag = false;
		Statement st = null;
		try {
			System.out
					.println("update EMPMAST set GENDER='"
							+ empbean.getGENDER()
							+ "',DOB='"
							+ empbean.getDOB()
							+ "',BPLACE='"
							+ empbean.getBPLACE()
							+ "',DOJ='"
							+ empbean.getDOJ()
							+ "',RESNLFTCD="
							+ empbean.getRESNLFTCD()
							+ ",PANNO='"
							+ empbean.getPANNO()
							+ "',PFNO='"
							+ empbean.getPFNO()
							+ "',PFOPENDT=CAST("
							+ empbean.getPFOPENDTQUERY()
							+ " AS DATE),PFNOMINEE='"
							+ empbean.getPFNOMINEE()
							+ "',PFNOMIREL='"
							+ empbean.getPFNOMIREL()
							+ "',MARRIED='"
							+ empbean.getMARRIED()
							+ "',MARRIEDDT='"
							+ empbean.getMARRIEDDT()
							+ "',CASTCD="
							+ empbean.getCASTCD()
							+ ",CATEGORYCD="
							+ empbean.getCATEGORYCD()
							+ empbean.getRELEGENCD() 
							+ ",BGRP="+empbean.getBGRP()
							+ ",IDENTY='"
							+ empbean.getIDENTITY()
							+ "',HEIGHT="
							+ empbean.getHEIGHT()
							+ ",WEIGHT="
							+ empbean.getWEIGHT()
							+ ",OTHERDTL='"
							+ empbean.getOTHERDTL()
							+ "',MLWFNO='"
							+ empbean.getMLWFNO()
							+ "',DISABILYN='"
							+ empbean.getDISABILYN()
							+ "',RESIDSTAT="
							+ empbean.getRESIDSTAT()
							+ ",VEHICLEDES='"
							+ empbean.getVEHICLEDES()
							+ "',DRVLISCNNO='"
							+ empbean.getDRVLISCNNO()
							+ "',PASSPORTNO='"
							+ empbean.getPASSPORTNO()
							+ "',SALUTE="
							+ empbean.getSALUTE()
							+ ", STATUS='"
							+ empbean.getSTATUS()
							+ "',SBOND='"
							+ empbean.getSBOND()
							+ "',DEPAMT="
							+ empbean.getDEPAMT()
							+ ",AADHAARNUM='"
							+ empbean.getAADHAARNUM()
							+ "',EMPLOYEE_TYPE="
							+ empbean.getEMPTYPE()
							+ ",UMODDATE = (select GETDATE()) ,leavecnfdate = '"
							+ empbean.getLEAVEDATE() 
							+ "',CONFIRMATIONDATE= (CASE WHEN '"+ empbean.getCONFIRM_DATE()+"'='1' then null else '"+empbean.getCONFIRM_DATE()+"' end )"
						//	+ empbean.getCONFIRM_DATE()
							+",RETIREMENT_EXT_PERIOD ='"+ empbean.getRETIREMENT_EXT_PERIOD()
					//		+ empbean.getRETIREMENT_EXT_PERIOD()
							+"',FILENUMBER = '"
							+ empbean.getFILENUMBER()
                            +"',LIC_grat_NO = '"
							+ empbean.getLIC_GRAT_NO()
							+ "',LIC_LEAVE_ENCASH_NO = '"
							+ empbean.getLIC_LEAVE_ENCASH_NO()
							+ "',LIC_GROUP_INSURANCE_NO = '"
							+ empbean.getLIC_GROUP_INSURANCE_NO()	
							+ "',LIC_LEAVE_ENCASH_POLICY_NO = '"
							+ empbean.getLIC_LEAVE_ENCASH_POLICY_NO()
							+ "',LIC_GRAT_POLICY_NO = '"
							+ empbean.getLIC_GRAT_POLICY_NO()	
							+ "'  where EMPNO ="
							+ empbean.getEMPNO() + "");
			conn = ConnectionManager.getConnection();
			st = conn.createStatement();
			st.executeUpdate("update EMPMAST set GENDER='"
					+ empbean.getGENDER()
					+ "',DOB='"
					+ empbean.getDOB()
					+ "',BPLACE='"
					+ empbean.getBPLACE()
					+ "',DOJ='"
					+ empbean.getDOJ()
					+ "',RESNLFTCD="
					+ empbean.getRESNLFTCD()
					+ ",PANNO='"
					+ empbean.getPANNO()
					+ "',PFNO='"
					+ empbean.getPFNO()
					+ "',PFOPENDT=CAST("
					+ empbean.getPFOPENDTQUERY()
					+ " AS DATE),PFNOMINEE='"
					+ empbean.getPFNOMINEE()
					+ "',PFNOMIREL='"
					+ empbean.getPFNOMIREL()
					+ "',MARRIED='"
					+ empbean.getMARRIED()
					+ "',MARRIEDDT='"
					+ empbean.getMARRIEDDT()
					+ "',CASTCD="
					+ empbean.getCASTCD()
					+ ",CATEGORYCD="
					+ empbean.getCATEGORYCD()
					+ ",RELEGENCD="
					+ empbean.getRELEGENCD() 
					+ ",BGRP="+empbean.getBGRP()
					+ ",IDENTY='"
					+ empbean.getIDENTITY()
					+ "',HEIGHT="
					+ empbean.getHEIGHT()
					+ ",WEIGHT="
					+ empbean.getWEIGHT()
					+ ",OTHERDTL='"
					+ empbean.getOTHERDTL()
					+ "',MLWFNO='"
					+ empbean.getMLWFNO()
					+ "',DISABILYN='"
					+ empbean.getDISABILYN()
					+ "',RESIDSTAT="
					+ empbean.getRESIDSTAT()
					+ ",VEHICLEDES='"
					+ empbean.getVEHICLEDES()
					+ "',DRVLISCNNO='"
					+ empbean.getDRVLISCNNO()
					+ "',PASSPORTNO='"
					+ empbean.getPASSPORTNO()
					+ "',SALUTE="
					+ empbean.getSALUTE()
					+ ", STATUS='"
					+ empbean.getSTATUS()
					+ "',SBOND='"
					+ empbean.getSBOND()
					+ "',DEPAMT="
					+ empbean.getDEPAMT()
					+ ",AADHAARNUM='"
					+ empbean.getAADHAARNUM()
					+ "',EMPLOYEE_TYPE="
					+ empbean.getEMPTYPE()
					+ ",UMODDATE = (select GETDATE()) "
					+ ",leavecnfdate= (CASE WHEN '"+ empbean.getLEAVEDATE()+"'='1' then null else '"+empbean.getLEAVEDATE()+"' end )"
					+ ",CONFIRMATIONDATE= (CASE WHEN '"+ empbean.getCONFIRM_DATE()+"'='1' then null else '"+empbean.getCONFIRM_DATE()+"' end )"
//					+ ",UMODDATE = (select GETDATE()) ,leavecnfdate = '"
//					+ empbean.getLEAVEDATE() 
//					+ "',CONFIRMATIONDATE= (CASE WHEN '"+ empbean.getCONFIRM_DATE()+"'='1' then null else '"+empbean.getCONFIRM_DATE()+"' end )"
				//	+ empbean.getCONFIRM_DATE()
					+",RETIREMENT_EXT_PERIOD ='"+ empbean.getRETIREMENT_EXT_PERIOD()
			//		+ empbean.getRETIREMENT_EXT_PERIOD()
					+"',FILENUMBER = '"
					+ empbean.getFILENUMBER()
                    +"',LIC_grat_NO = '"
					+ empbean.getLIC_GRAT_NO()
					+ "',LIC_LEAVE_ENCASH_NO = '"
					+ empbean.getLIC_LEAVE_ENCASH_NO()
					+ "',LIC_GROUP_INSURANCE_NO = '"
					+ empbean.getLIC_GROUP_INSURANCE_NO()	
					+ "',LIC_LEAVE_ENCASH_POLICY_NO = '"
					+ empbean.getLIC_LEAVE_ENCASH_POLICY_NO()
					+ "',LIC_GRAT_POLICY_NO = '"
					+ empbean.getLIC_GRAT_POLICY_NO()	
					+ "'  where EMPNO ="
					+ empbean.getEMPNO() + "");
			flag = true;
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static int getAutoIncrement() {
		Connection con = null;
		Statement st = null;
		int EMPNO = 1;
		try {
			con = ConnectionManager.getConnection();
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select max(EMPNO)+1 from EMPMAST");
			if (rs.next()) {
				EMPNO = rs.getInt(1);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EMPNO;
	}

	/*
	 * public static int getcode() { Connection con = null; Statement st = null;
	 * int EMPCODE=1; try { con = ConnectionManager.getConnection(); st =
	 * con.createStatement(); ResultSet rs =
	 * st.executeQuery("select MAX(CAST(EMPCODE AS INT)+1 ) from EMPMAST");
	 * if(rs.next()) { EMPCODE = rs.getInt(1);
	 * System.out.println("in method"+EMPCODE); } con.close(); } catch(Exception
	 * e) { e.printStackTrace(); } return EMPCODE; }
	 */
	public static String getcode() {
		Connection con = null;
		Statement st = null;
		int EMPCODE = 1;
		String temp_empcode = "";
		try {
			con = ConnectionManager.getConnection();
			st = con.createStatement();
			/*
			 * ResultSet rs =
			 * st.executeQuery("select MAX(CAST(EMPCODE AS INT)+1 ) from EMPMAST"
			 * ); if(rs.next()) { EMPCODE = rs.getInt(1);
			 * System.out.println("in method"+EMPCODE); }
			 */

			ResultSet rs = st
					.executeQuery("Select isnull((select MAX(EMPCODE) from EMPMAST),'A001')");
			if (rs.next()) {
				temp_empcode = rs.getString(1);
			}
			String letter = "";
			char[] chars = temp_empcode.toCharArray();
			String str = temp_empcode.replaceAll("[^\\d.]", "");

			int result = 0;

			char res;
			for (char c : chars) {
				if (Character.isLetter(c)) {
					letter += c;
					result = c;

				}

			}

			EMPCODE = Integer.parseInt(str.trim());
			EMPCODE++;

			if (EMPCODE <= 9) {
				letter = letter + "00" + EMPCODE;
			} else if (EMPCODE > 9 && EMPCODE <= 99) {

				letter = letter + "0" + EMPCODE;

			}

			else if (EMPCODE > 99 && EMPCODE <= 999) {

				letter = letter + "" + EMPCODE;

			}

			else if (EMPCODE > 999) {
				EMPCODE = 1;

				res = (char) (result + 1);
				letter = res + "00" + EMPCODE;

			}

			temp_empcode = letter;

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return temp_empcode;
	}

	public static String getcodeserv() {
		Connection con = null;
		Statement st = null;
		String EMPCODE = "";
		try {
			con = ConnectionManager.getConnection();
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select MAX(EMPCODE) from EMPMAST");
			if (rs.next()) {
				EMPCODE = rs.getString(1);
				System.out.println("in method" + EMPCODE);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EMPCODE;
	}

	/*
	 * public static int getcodeserv() { Connection con = null; Statement st =
	 * null; int EMPCODE=1; try { con = ConnectionManager.getConnection(); st =
	 * con.createStatement(); ResultSet rs =
	 * st.executeQuery("select MAX(CAST(EMPCODE AS INT) ) from EMPMAST");
	 * if(rs.next()) { EMPCODE = rs.getInt(1);
	 * System.out.println("in method"+EMPCODE); } con.close(); } catch(Exception
	 * e) { e.printStackTrace(); } return EMPCODE; }
	 */
	public String insertEmployeeInfo(EmployeeBean empbean) {
		Connection con = null;
		// PreparedStatement prst=null;
		LookupHandler lh = new LookupHandler();
		System.out.println("Blood Group in handler in insert emp::  "
				+ empbean.getBGRP());
		Lookup lkhp = new Lookup();
		lkhp = lh.getLookup("SALUTE-" + empbean.getSALUTE());
		String salute = lkhp.getLKP_DESC();
		
		lkhp = lh.getLookup("BLOOD_GRP-" + empbean.getBGRP());
		String bldgrp = lkhp.getLKP_DESC();
		
		int empno = 0;
		String empcode = "V001";
		try {
			empno = getAutoIncrement();
			// empcode = getcode();
	
			System.out.println("Employee Type :"+empbean.getEMPTYPE());
			System.out.println("in query " + empcode);
			System.out.println("confirm dt " + empbean.getCONFIRM_DATE()+"  ext..");
			con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			Statement stmt = con.createStatement();
			String desc = salute + "" + empbean.getFNAME() + " "
					+ empbean.getMNAME() + " " + empbean.getLNAME();
			String insertLookup = "INSERT INTO LOOKUP VALUES('ET'," + empno
					+ ",'" + desc + "',0)";
			String str = "insert into EMPMAST(EMPNO,SALUTE,FNAME,MNAME,LNAME,GENDER,DOB,BPLACE,DOJ,RESNLFTCD,"
					+ " PANNO,PFNOMINEE,PFNOMIREL,MARRIED,MARRIEDDT,CASTCD,CATEGORYCD,RELEGENCD,BGRP,IDENTY,"
					+ " HEIGHT,WEIGHT,OTHERDTL,MLWFNO,DISABILYN,RESIDSTAT,VEHICLEDES,DRVLISCNNO,PASSPORTNO,STATUS,"
					+ " SBOND,DEPAMT,AADHAARNUM,EMPLOYEE_TYPE,PFNO,EMPCODE,UCREATEDATE,UMODDATE,PFOPENDT, " 
					+ "leavecnfdate,retirementdate,CONFIRMATIONDATE,RETIREMENT_EXT_PERIOD,FILENUMBER,prob_start_date,prob_end_date,LIC_GRAT_NO,LIC_lEAVE_ENCASH_NO,LIC_GROUP_INSURANCE_NO,LIC_LEAVE_ENCASH_POLICY_NO,LIC_GRAT_POLICY_NO) values "
					+
					/* " ( (select max(EMPNO)+1 from EMPMAST) " + */
					"("
					+ empno
					+ " , "
					+ empbean.getSALUTE()
					+ " "
					+ " , '"
					+ empbean.getFNAME()
					+ "' "
					+ " , '"
					+ empbean.getMNAME()
					+ "' "
					+ " , '"
					+ empbean.getLNAME()
					+ "' "
					+ " , '"
					+ empbean.getGENDER()
					+ "' "
					+ " , '"
					+ empbean.getDOB()
					+ "' "
					+ " , '"
					+ empbean.getBPLACE()
					+ "' "
					+ " , '"
					+ empbean.getDOJ()
					+ "' "
					+ " , "
					+ empbean.getRESNLFTCD()
					+ " "
					+ " , '"
					+ empbean.getPANNO()
					+ "' "
					+ " , '"
					+ empbean.getPFNOMINEE()
					+ "' "
					+ " , '"
					+ empbean.getPFNOMIREL()
					+ "' "
					+ " , '"
					+ empbean.getMARRIED()
					+ "' "
					+ " , '"
					+ empbean.getMARRIEDDT()
					+ "' "
					+ " , "
					+ empbean.getCASTCD()
					+ " "
					+ " , "
					+ empbean.getCATEGORYCD()
					+ " "
					+ " , "
					+ empbean.getRELEGENCD()
					+ " ,(select LKP_SRNO from lookup where LKP_SRNO <> 0  and LKP_CODE='BLOOD_GRP' and LKP_SRNO = '"
					+ empbean.getBGRP()
					+ "' "
					+ "), '"
					+ empbean.getIDENTITY()
					+ "' "
					+ " , "
					+ empbean.getHEIGHT()
					+ " "
					+ " , "
					+ empbean.getWEIGHT()
					+ " "
					+ " , '"
					+ empbean.getOTHERDTL()
					+ "' "
					+ " , '"
					+ empbean.getMLWFNO()
					+ "' "
					+ " , '"
					+ empbean.getDISABILYN()
					+ "' "
					+ " , "
					+ empbean.getRESIDSTAT()
					+ " "
					+ " , '"
					+ empbean.getVEHICLEDES()
					+ "' "
					+ " , '"
					+ empbean.getDRVLISCNNO()
					+ "' "
					+ " , '"
					+ empbean.getPASSPORTNO()
					+ "' "
					+ " , 'A'"
					+ " , '"
					+ empbean.getSBOND()
					+ "' "
					+ " , "
					+ empbean.getDEPAMT()
					+ " "
					+ " , '"
					+ empbean.getAADHAARNUM()
					+ "',"
					//+ empno
					+ empbean.getEMPTYPE()
					+ " , '"
					+ empbean.getPFNO()
					+ "'"
					+ " , '"
					+ empbean.getEMPCODE()
					+ "' , (select GETDATE()), (select GETDATE()), CAST("
					+ empbean.getPFOPENDTQUERY()
					+ " AS DATE),"
					+ " (CASE WHEN '"+ empbean.getLEAVEDATE()+"'='1' then null else '"+empbean.getLEAVEDATE()+"' end )"
					+ ",'"
					+ empbean.getRETIREMENTDATE()
					+ "',(CASE WHEN '"+ empbean.getCONFIRM_DATE()+"'='1' then null else '"+empbean.getCONFIRM_DATE()+"' end )"
					+ ",'"+ empbean.getRETIREMENTDATE()
					+"','"
					+ empbean.getFILENUMBER()
					+ "','"+empbean.getPROB_START_DATE() 
					+"','"
					+empbean.getPROB_END_DATE() 
					+ "' "
					+ " , '"
					+ empbean.getLIC_GRAT_NO()
					+ "' "
					+ " , '"
					+ empbean.getLIC_LEAVE_ENCASH_NO()
					+ "' "
					+ " , '"
					+ empbean.getLIC_GROUP_INSURANCE_NO()
					+ "' "
					+ " , '"
					+ empbean.getLIC_LEAVE_ENCASH_POLICY_NO()
					+ "' "
					+ " , '"
					+ empbean.getLIC_GRAT_POLICY_NO()	
					+"')";
			System.out.println(str);
			stmt.execute(str);
			st.executeUpdate(insertLookup);
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Integer.toString(empno);
	}

	public static String dateFormat(Date date) {
		String result = "";
		if (date == null) {
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
		result = format.format(date);
		return result;
	}

	// BY param
	public ArrayList<EmployeeBean> getBirthEmplist() {
		Connection con = ConnectionManager.getConnection();
		java.util.Date d = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		String month = sdf.format(d);
		ArrayList<EmployeeBean> alist = new ArrayList<EmployeeBean>();
		try {
			Statement st = con.createStatement();
			ResultSet rs = null;
			rs = st.executeQuery("select EMPNO,DOB,CONVERT(NVARCHAR(100),LNAME+' '+FNAME+' '+MNAME) as name from empmast where DOB like '%-"
					+ month
					+ "-%' and STATUS='A' order by  CONVERT(NVARCHAR, CAST(dob AS DATETIME), 105)");
			System.out.println("Month: select EMPNO,DOB,CONVERT(NVARCHAR(100),LNAME+' '+FNAME+' '+MNAME) as name from empmast where DOB like '%-"
					+ month
					+ "-%' and STATUS='A' order by  CONVERT(NVARCHAR, CAST(dob AS DATETIME), 105)");
			while (rs.next()) {
				EmployeeBean empb = new EmployeeBean();
				empb.setEMPNO(rs.getInt("EMPNO"));
				empb.setDOB(rs.getString("DOB"));
				empb.setFNAME(rs.getString("name"));
				alist.add(empb);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}

	/*
	 * public String getday(String dt) throws ParseException { SimpleDateFormat
	 * dtn = new SimpleDateFormat("yyyy-MM-dd"); Date dtp=dtn.parse(dt);
	 * SimpleDateFormat sdf = new SimpleDateFormat("dd"); String MyDate1 =
	 * sdf.format(dtp); return MyDate1;
	 * 
	 * }
	 */

	public ArrayList<EmployeeBean> getday() {
		Connection con = ConnectionManager.getConnection();
		ArrayList<EmployeeBean> alist = new ArrayList<EmployeeBean>();
		java.util.Date d = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dt = sdf.format(d);
		try {
			System.out.println(dt);
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select EMPNO,DOB,CONVERT(NVARCHAR(100),LNAME+' '+FNAME+' '+MNAME) as name"
							+ " from empmast where DATEPART(day,dob)= DATEPART(day, '"
							+ dt
							+ "' ) "
							+ "and DATEPART(mm,dob) =DATEPART(mm,'"
							+ dt
							+ "') and STATUS='A'");
			while (rs.next()) {
				EmployeeBean empb = new EmployeeBean();
				empb.setDOB(rs.getString("DOB"));
				empb.setFNAME(rs.getString("name"));
				alist.add(empb);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return alist;

	}

	public ArrayList<EmployeeBean> getTombd() {
		java.util.Date d = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dt = sdf.format(d);
		SimpleDateFormat date = new SimpleDateFormat("dd");
		String prdate = date.format(d);
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
		String month = sdf1.format(d);
		Connection con = ConnectionManager.getConnection();
		ArrayList<EmployeeBean> alist = new ArrayList<EmployeeBean>();
		try {
			Statement st = con.createStatement();
			ResultSet rs = null;
			Calendar calendar = Calendar.getInstance();
			int lDate = calendar.getActualMaximum(Calendar.DATE);
			String lastDate = Integer.toString(lDate);
			if (!lastDate.equals(prdate)) {
				/*
				 * rs=st.executeQuery(
				 * "select EMPNO,DOB,CONVERT(NVARCHAR(100),LNAME+' '+FNAME+' '+MNAME) as name"
				 * +
				 * " from empmast where DATEPART(day,dob)= DATEPART(day,  DATEADD(DAY, 1, GETDATE()) ) "
				 * + "and DATEPART(mm,dob) =DATEPART(mm,GETDATE())");
				 */
				System.out.println("today date is " + prdate + " month is "
						+ month);
				rs = st.executeQuery("select EMPNO,DOB,CONVERT(NVARCHAR(100),LNAME+' '+FNAME+' '+MNAME) as name"
						+ " from empmast where DATEPART(day,dob)= DATEPART(day,  DATEADD(DAY, 1, '"
						+ dt
						+ "') ) "
						+ "and DATEPART(mm,dob) =DATEPART(mm,'"
						+ dt + "') and STATUS='A'");
			} else {
				System.out.println("last date of " + month + " is " + lastDate);
				rs = st.executeQuery("select EMPNO,DOB,CONVERT(NVARCHAR(100),LNAME+' '+FNAME+' '+MNAME) as name"
						+ " from empmast where DATEPART(day,dob)= DATEPART(day,  DATEADD(DAY, 1, '"
						+ dt
						+ "') ) "
						+ "and DATEPART(mm,dob) =DATEPART(mm,DATEADD(MONTH, 1, '"
						+ dt + "') ) and STATUS='A'");

			}
			while (rs.next()) {
				EmployeeBean empb = new EmployeeBean();
				empb.setDOB(rs.getString("DOB"));
				empb.setFNAME(rs.getString("name"));
				alist.add(empb);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return alist;
	}

	public EmployeeBean getMaxEmployeeInformation() {
		conn = ConnectionManager.getConnection();
		ResultSet rs;
		SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd");
		fromformat.setLenient(false);
		EmployeeBean empbean = new EmployeeBean();

		try {
			Statement stmt = conn.createStatement();
			rs = stmt
					.executeQuery("select * from EMPMAST where EMPNO = (select MAX(EMPNO) from EMPMAST)");
			System.out.println(rs);
			String empmaxname = "";
			if (rs.next()) {

				empbean.setEMPNO(rs.getInt("EMPNO"));
				empbean.setFNAME(rs.getString("FNAME"));
				empbean.setLNAME(rs.getString("LNAME"));
				empbean.setMNAME(rs.getString("MNAME"));
				empbean.setDOJ(rs.getString("DOJ"));
				empbean.setEMPCODE(rs.getString("EMPCODE"));
				// System.out.println("hiii empno dob"+rs.getString("DOJ"));
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return empbean;
	}

	public String getpaytrandate(int empno) {
		String dt = "";
		try {
			Connection con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = null;

			rs = st.executeQuery("select max(trndt) as trndt from paytran where empno="
					+ empno + " ");
			if (rs.next()) {
				dt = rs.getString("trndt");
			} else {
				dt = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dt;
	}

	public static int getAutoIncrementtempemp() {
		Connection con = null;
		Statement st = null;
		int EMPNO = 1;
		try {
			con = ConnectionManager.getConnection();
			st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select isnull(max(EMPNO)+1,1) from TEMPEMPMAST");
			if (rs.next()) {
				EMPNO = rs.getInt(1);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EMPNO;
	}

	public String inserttempempEmployeeInfo(EmployeeBean empbean) {
		Connection con = null;
		// PreparedStatement prst=null;
		LookupHandler lh = new LookupHandler();

		Lookup lkhp = new Lookup();
		lkhp = lh.getLookup("SALUTE-" + empbean.getSALUTE());
		String salute = lkhp.getLKP_DESC();
		int empno = 0;
		String empcode = "V001";
		try {
			empno = getAutoIncrementtempemp();
			// empcode = getcode();
			System.out.println("in query " + empcode);
			con = ConnectionManager.getConnection();
			Statement st = con.createStatement();
			Statement stmt = con.createStatement();
			String desc = salute + "" + empbean.getFNAME() + " "
					+ empbean.getMNAME() + " " + empbean.getLNAME();
			// String
			// insertLookup="INSERT INTO LOOKUP VALUES('ET',"+empno+",'"+desc+"',0)";
			String str = "insert into TEMPEMPMAST(EMPNO,SALUTE,FNAME,MNAME,LNAME,GENDER,DOB,BPLACE,DOJ,RESNLFTCD,"
					+ " PANNO,PFNOMINEE,PFNOMIREL,MARRIED,MARRIEDDT,CASTCD,CATEGORYCD,RELEGENCD,BGRP,IDENTY,"
					+ " HEIGHT,WEIGHT,OTHERDTL,MLWFNO,DISABILYN,RESIDSTAT,VEHICLEDES,DRVLISCNNO,PASSPORTNO,STATUS,"
					+ " SBOND,DEPAMT,AADHAARNUM,EMPLOYEE_TYPE,PFNO,EMPCODE,UCREATEDATE,UMODDATE,PFOPENDT, leavecnfdate,retirementdate) values "
					+
					/* " ( (select max(EMPNO)+1 from EMPMAST) " + */
					"("
					+ empno
					+ " , "
					+ empbean.getSALUTE()
					+ " "
					+ " , '"
					+ empbean.getFNAME()
					+ "' "
					+ " , '"
					+ empbean.getMNAME()
					+ "' "
					+ " , '"
					+ empbean.getLNAME()
					+ "' "
					+ " , '"
					+ empbean.getGENDER()
					+ "' "
					+ " , '"
					+ empbean.getDOB()
					+ "' "
					+ " , '"
					+ empbean.getBPLACE()
					+ "' "
					+ " , '"
					+ empbean.getDOJ()
					+ "' "
					+ " , "
					+ empbean.getRESNLFTCD()
					+ " "
					+ " , '"
					+ empbean.getPANNO()
					+ "' "
					+ " , '"
					+ empbean.getPFNOMINEE()
					+ "' "
					+ " , '"
					+ empbean.getPFNOMIREL()
					+ "' "
					+ " , '"
					+ empbean.getMARRIED()
					+ "' "
					+ " , '"
					+ empbean.getMARRIEDDT()
					+ "' "
					+ " , "
					+ empbean.getCASTCD()
					+ " "
					+ " , "
					+ empbean.getCATEGORYCD()
					+ " "
					+ " , "
					+ empbean.getRELEGENCD()
					+ " "
					+ " , '"
					+ empbean.getBGRP()
					+ "' "
					+ " , '"
					+ empbean.getIDENTITY()
					+ "' "
					+ " , "
					+ empbean.getHEIGHT()
					+ " "
					+ " , "
					+ empbean.getWEIGHT()
					+ " "
					+ " , '"
					+ empbean.getOTHERDTL()
					+ "' "
					+ " , '"
					+ empbean.getMLWFNO()
					+ "' "
					+ " , '"
					+ empbean.getDISABILYN()
					+ "' "
					+ " , "
					+ empbean.getRESIDSTAT()
					+ " "
					+ " , '"
					+ empbean.getVEHICLEDES()
					+ "' "
					+ " , '"
					+ empbean.getDRVLISCNNO()
					+ "' "
					+ " , '"
					+ empbean.getPASSPORTNO()
					+ "' "
					+ " , 'A'"
					+ " , '"
					+ empbean.getSBOND()
					+ "' "
					+ " , "
					+ empbean.getDEPAMT()
					+ " "
					+ " , '"
					+ empbean.getAADHAARNUM()
					+ "',"
					+ empno
					+ " , '"
					+ empbean.getPFNO()
					+ "'"
					+ " , '"
					+ "T"
					+ empno
					+ "' , (select GETDATE()), (select GETDATE()), CAST("
					+ empbean.getPFOPENDTQUERY()
					+ " AS DATE),"
					+ " '"
					+ empbean.getLEAVEDATE()
					+ "','"
					+ empbean.getRETIREMENTDATE() + "')";
			System.out.println(str);
			stmt.execute(str);
			// st.executeUpdate(insertLookup);
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Integer.toString(empno);
	}

	public boolean updateTempEmployeeInfo(EmployeeBean empbean) {
		Boolean flag = false;
		Statement st = null;
		try {
			conn = ConnectionManager.getConnection();
			st = conn.createStatement();
			st.executeUpdate("update EMPMAST set GENDER='"
					+ empbean.getGENDER() + "',DOB='" + empbean.getDOB()
					+ "',BPLACE='" + empbean.getBPLACE() + "',DOJ='"
					+ empbean.getDOJ() + "',RESNLFTCD="
					+ empbean.getRESNLFTCD() + ",PANNO='" + empbean.getPANNO()
					+ "',PFNO='" + empbean.getPFNO() + "',PFOPENDT=CAST("
					+ empbean.getPFOPENDTQUERY() + " AS DATE),PFNOMINEE='"
					+ empbean.getPFNOMINEE() + "',PFNOMIREL='"
					+ empbean.getPFNOMIREL() + "',MARRIED='"
					+ empbean.getMARRIED() + "',MARRIEDDT='"
					+ empbean.getMARRIEDDT() + "',CASTCD="
					+ empbean.getCASTCD() + ",CATEGORYCD="
					+ empbean.getCATEGORYCD() + ",RELEGENCD='"
					+ empbean.getRELEGENCD() + "',BGRP='" + empbean.getBGRP()
					+ "',IDENTY='" + empbean.getIDENTITY() + "',HEIGHT="
					+ empbean.getHEIGHT() + ",WEIGHT=" + empbean.getWEIGHT()
					+ ",OTHERDTL='" + empbean.getOTHERDTL() + "',MLWFNO='"
					+ empbean.getMLWFNO() + "',DISABILYN='"
					+ empbean.getDISABILYN() + "',RESIDSTAT="
					+ empbean.getRESIDSTAT() + ",VEHICLEDES='"
					+ empbean.getVEHICLEDES() + "',DRVLISCNNO='"
					+ empbean.getDRVLISCNNO() + "',PASSPORTNO='"
					+ empbean.getPASSPORTNO() + "',SALUTE="
					+ empbean.getSALUTE() + ", STATUS='" + empbean.getSTATUS()
					+ "',SBOND='" + empbean.getSBOND() + "',DEPAMT="
					+ empbean.getDEPAMT() + ",AADHAARNUM='"
					+ empbean.getAADHAARNUM() + "', EMPLOYEE_TYPE="
					+ empbean.getEMPTYPE()
					+ ",UMODDATE = (select GETDATE()) ,leavecnfdate = '"
					+ empbean.getLEAVEDATE() + "' where EMPNO ="
					+ empbean.getEMPNO() + "");
			flag = true;
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public EmployeeBean getTempEmployeeInformation(String EMPNO) {
		conn = ConnectionManager.getConnection();
		ResultSet rs;
		SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd");
		fromformat.setLenient(false);
		EmployeeBean empbean = new EmployeeBean();
		try {
			Statement stmt = conn.createStatement();

			String query = "select * from  tempempmast  where EMPNO=" + EMPNO
					+ "";

			/*
			 * String query = " select E.*, ET.DESIG,ET.BRANCH , " +
			 * " isnull( (select top 1 SRNO from GRADE_MASTER where BASIC =( select top 1 inp_amt from CTCDISPLAY where EMPNO ="
			 * +EMPNO+
			 * " and TRNCD = 101) and GRADE_CODE = ET.DESIG and GRADE_STATUS =0 ),0) as stage "
			 * + " from EMPMAST E, EMPTRAN ET where E.EMPNO="+EMPNO+
			 * "  AND E.EMPNO =ET.EMPNO" +
			 * " and ET.srno=(select max(srno) from emptran where EMPNO="
			 * +EMPNO+" ) ";
			 */

			// rs=stmt.executeQuery("select E.*, ET.DESIG from EMPMAST E, EMPTRAN ET where ET.EMPNO = "+EMPNO+"  AND ET.EMPNO =E.EMPNO");

			rs = stmt.executeQuery(query);
			// System.out.println(query);

			while (rs.next()) {
				empbean.setEMPNO(rs.getString("EMPNO") == null ? 0 : rs
						.getInt("EMPNO"));
				empbean.setFNAME(rs.getString("FNAME") == null ? "" : rs
						.getString("FNAME"));
				empbean.setLNAME(rs.getString("LNAME") == null ? "" : rs
						.getString("LNAME"));
				empbean.setPFNO((rs.getString("PFNO") == null ? "--" : rs
						.getString("PFNO")));
				empbean.setDOJ(rs.getDate("DOJ") == null ? "" : dateFormat(rs
						.getDate("DOJ")));
				empbean.setBGRP((rs.getString("BGRP") == null ? "--" : rs
						.getString("BGRP")));
				empbean.setBPLACE((rs.getString("BPLACE") == null ? "--" : (rs
						.getString("BPLACE"))));
				empbean.setCASTCD(rs.getString("CASTCD") == null ? 0 : rs
						.getInt("CASTCD"));
				empbean.setCATEGORYCD(rs.getString("CATEGORYCD") == null ? 0
						: rs.getInt("CATEGORYCD"));
				empbean.setDA_SCHEME(rs.getString("DA_SCHEME") == null ? ""
						: rs.getString("DA_SCHEME"));
				empbean.setDEPAMT((int) Double.parseDouble((rs
						.getString("DEPAMT") == null ? "0" : rs
						.getString("DEPAMT"))));
				empbean.setDISABILPER(rs.getString("DISABILPER") == null ? "--"
						: rs.getString("DISABILPER"));
				empbean.setDISABILYN(rs.getString("DISABILYN") == null ? "N"
						: rs.getString("DISABILYN"));
				empbean.setDOB(rs.getDate("DOB") == null ? "" : dateFormat(rs
						.getDate("DOB")));
				empbean.setDOL(rs.getDate("DOL") == null ? "" : dateFormat(rs
						.getDate("DOL")));
				empbean.setDRVLISCNNO((rs.getString("DRVLISCNNO") == null ? "--"
						: rs.getString("DRVLISCNNO")));
				empbean.setFHNAME(rs.getString("FHNAME") == null ? "" : rs
						.getString("FHNAME"));
				empbean.setHEIGHT(rs.getString("HEIGHT") == null ? 0 : Double
						.parseDouble(rs.getString("HEIGHT")));
				empbean.setHOBBYCD(rs.getString("HOBBYCD") == null ? "" : rs
						.getString("HOBBYCD"));
				empbean.setIDENTITY((rs.getString("IDENTY") == null ? "--"
						: (rs.getString("IDENTY"))));
				empbean.setMARRIED(rs.getString("MARRIED") == null ? "U" : rs
						.getString("MARRIED"));
				empbean.setMARRIEDDT(rs.getDate("MARRIEDDT") == null ? ""
						: dateFormat(rs.getDate("MARRIEDDT")));
				empbean.setMLWFNO((rs.getString("MLWFNO") == null ? "--" : (rs
						.getString("MLWFNO"))));
				empbean.setMNAME(rs.getString("MNAME") == null ? "-" : rs
						.getString("MNAME"));
				empbean.setOTHERDTL((rs.getString("OTHERDTL") == null ? "----"
						: (rs.getString("OTHERDTL"))));
				empbean.setPANNO((rs.getString("PANNO")) == null ? "--" : (rs
						.getString("PANNO")));
				empbean.setPASSPORTNO((rs.getString("PASSPORTNO")) == null ? "--"
						: rs.getString("PASSPORTNO"));
				empbean.setPFNOMINEE(rs.getString("PFNOMINEE") == null ? "--"
						: rs.getString("PFNOMINEE"));
				empbean.setPFNOMIREL(rs.getString("PFNOMIREL") == null ? "--"
						: rs.getString("PFNOMIREL"));
				empbean.setPFOPENDT(rs.getDate("PFOPENDT") == null ? ""
						: dateFormat(rs.getDate("PFOPENDT")));
				empbean.setPFNO(rs.getString("PFNO") == null ? "--" : rs
						.getString("PFNO"));
				empbean.setRESIDSTAT(rs.getString("RESIDSTAT") == null ? 0 : rs
						.getInt("RESIDSTAT"));
				empbean.setRELEGENCD(rs.getString("RELEGENCD") == null ? 0 : rs
						.getInt("RELEGENCD"));
				empbean.setRESNLFTCD(Integer
						.parseInt(rs.getString("RESNLFTCD") == null ? "0" : rs
								.getString("RESNLFTCD")));
				empbean.setSALUTE(rs.getString("SALUTE") == null ? 0 : rs
						.getInt("SALUTE"));
				empbean.setSBOND(rs.getString("SBOND") == null ? "N" : rs
						.getString("SBOND"));
				empbean.setGENDER(rs.getString("GENDER") == null ? "" : rs
						.getString("GENDER"));
				empbean.setSTATUS(rs.getString("STATUS") == null ? "" : rs
						.getString("STATUS"));
				empbean.setVEHICLEDES((rs.getString("VEHICLEDES") == null ? "--"
						: rs.getString("VEHICLEDES")));
				empbean.setWEIGHT(rs.getString("WEIGHT") == null ? 0.0 : Double
						.parseDouble(rs.getString("WEIGHT")));
				empbean.setAADHAARNUM(rs.getString("AADHAARNUM") == null ? "--"
						: rs.getString("AADHAARNUM"));
				empbean.setEMPTYPE(rs.getString("EMPLOYEE_TYPE") == null ? 0
						: rs.getInt("EMPLOYEE_TYPE"));
				empbean.setEMPCODE(rs.getString("EMPCODE"));
				empbean.setLEAVEDATE(rs.getDate("leavecnfdate") == null ? ""
						: dateFormat(rs.getDate("leavecnfdate")));
				// empbean.setGRADE(rs.getInt("desig"));
				// empbean.setSTAGE(rs.getInt("stage"));
				// empbean.setSite_id(rs.getInt("BRANCH"));

			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return empbean;
	}

	public EmployeeBean getMaxTempEmployeeInformation() {
		conn = ConnectionManager.getConnection();
		ResultSet rs;
		SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd");
		fromformat.setLenient(false);
		EmployeeBean empbean = new EmployeeBean();

		try {
			Statement stmt = conn.createStatement();
			rs = stmt
					.executeQuery("select * from TEMPEMPMAST where EMPNO = (select MAX(EMPNO) from TEMPEMPMAST)");
			String empmaxname = "";
			if (rs.next()) {

				empbean.setEMPNO(rs.getInt("EMPNO"));
				empbean.setFNAME(rs.getString("FNAME"));
				empbean.setLNAME(rs.getString("LNAME"));
				empbean.setMNAME(rs.getString("MNAME"));
				empbean.setDOJ(rs.getString("DOJ"));
				empbean.setEMPCODE(rs.getString("EMPCODE"));
				// System.out.println("hiii empno dob"+rs.getString("DOJ"));
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return empbean;
	}

	public String InsertSalaryDetails(int empno, String sal, int days,
			String ded, String ptamt, String month, String itax, String netamt) {
		String flag = "";
		conn = ConnectionManager.getConnection();
		Statement stmt = null;
		ResultSet rs;
		try {

			stmt = conn.createStatement();
			System.out
					.println("IF EXISTS(SELECT * FROM TEMP_EMP_SALARY WHERE EMPNO="
							+ empno
							+ " AND TRNDT=(select convert(date,'"
							+ month
							+ "') ) ) "
							+ " UPDATE TEMP_EMP_SALARY SET SALARY="
							+ sal
							+ ",ABSENTDAY="
							+ days
							+ ",DEDUCTION="
							+ ded
							+ ",PTAMT="
							+ ptamt
							+ ",ITAX="
							+ itax
							+ ",NET_AMT="
							+ netamt
							+ " WHERE EMPNO="
							+ empno
							+ " AND TRNDT=(select convert(date,'"
							+ month
							+ "') ) "
							+ " ELSE "
							+ " INSERT INTO TEMP_EMP_SALARY VALUES("
							+ empno
							+ ",(select convert(date,'"
							+ month
							+ "') ),"
							+ sal
							+ ","
							+ days
							+ ","
							+ ded
							+ ","
							+ ptamt
							+ ","
							+ itax
							+ "," + netamt + ") ");
			stmt.executeUpdate("IF EXISTS(SELECT * FROM TEMP_EMP_SALARY WHERE EMPNO="
					+ empno
					+ " AND TRNDT=(select convert(date,'"
					+ month
					+ "') ) ) "
					+ " UPDATE TEMP_EMP_SALARY SET SALARY="
					+ sal
					+ ",ABSENTDAY="
					+ days
					+ ",DEDUCTION="
					+ ded
					+ ",PTAMT="
					+ ptamt
					+ ",ITAX="
					+ itax
					+ ",NET_AMT="
					+ netamt
					+ " WHERE EMPNO="
					+ empno
					+ " AND TRNDT=(select convert(date,'"
					+ month
					+ "') ) "
					+ " ELSE "
					+ " INSERT INTO TEMP_EMP_SALARY VALUES("
					+ empno
					+ ",(select convert(date,'"
					+ month
					+ "') ),"
					+ sal
					+ ","
					+ days
					+ ","
					+ ded
					+ ","
					+ ptamt
					+ ","
					+ itax
					+ ","
					+ netamt + ") ");
			flag = "true";
			conn.close();
		} catch (Exception e) {
			flag = "false";

			e.printStackTrace();
		}
		return flag;
	}

	public ArrayList<Temp_Emp_Sal_Detail> getsalarydetails(int empno) {
		String salary = "";
		conn = ConnectionManager.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Temp_Emp_Sal_Detail> saldetails = new ArrayList<Temp_Emp_Sal_Detail>();
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			System.out.println("SELECT * FROM TEMP_EMP_SALARY WHERE EMPNO="
					+ empno + " AND TRNDT<=(select convert(date,'"
					+ ReportDAO.EOM(ReportDAO.getSysDate())
					+ "') ) ORDER BY TRNDT DESC ");
			rs = stmt.executeQuery("SELECT * FROM TEMP_EMP_SALARY WHERE EMPNO="
					+ empno + " AND TRNDT<=(select convert(date,'"
					+ ReportDAO.EOM(ReportDAO.getSysDate())
					+ "') ) ORDER BY TRNDT DESC ");
			if (rs.next()) {
				rs.previous();
				while (rs.next()) {
					Temp_Emp_Sal_Detail sal = new Temp_Emp_Sal_Detail();
					sal.setEmpno(rs.getInt(1));
					sal.setTrndt(rs.getString(2));
					sal.setSalary(rs.getString(3));
					sal.setAbsentDay(rs.getInt(4));
					sal.setDeduction(rs.getString(5));
					sal.setPTAmt(rs.getString(6));
					sal.setITax(rs.getString(7));
					sal.setNet_Amt(rs.getString(8));
					saldetails.add(sal);
				}
			} else {
				Temp_Emp_Sal_Detail sal = new Temp_Emp_Sal_Detail();
				sal.setEmpno(0);
				sal.setTrndt("0");
				sal.setSalary("0");
				sal.setAbsentDay(0);
				sal.setDeduction("0");
				sal.setPTAmt("0");
				sal.setITax("0");
				sal.setNet_Amt("0");
				saldetails.add(sal);
			}

			conn.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return saldetails;
	}
	public Integer  checkRetiredate(String retirementdate,int empno)  throws ParseException
	{
		 System.out.println("In checkRetiredate retirementdate");
		 int flags = 0;
	   try
	   {
		   
		     System.out.println(retirementdate);
	    	 Connection conn=ConnectionManager.getConnection();
	    	 String retcount=("select ISNULL((COUNT(*)),0)AS RETCOUNT from empmast where EMPNO="+empno+" and (SELECT CAST(GETDATE() AS DATE)) >='"+retirementdate+"'");
	    	 Statement stmt=conn.createStatement();
	    	 ResultSet rslt=stmt.executeQuery(retcount);
	    	System.out.println("RETCOUNT:-"+retcount);
			 while(rslt.next())
			 {
				 conn.commit();
				 flags=rslt.getInt("RETCOUNT");
			 }
			 conn.close();
	    } 
	   	catch (SQLException e) 
	   	{
		   e.printStackTrace();
		}
	return flags;
	}
	public Integer checkRetireExtensiondate(int Empno) throws ParseException
	{
		 int flags = 0;
	   try
	   {
	    	 Connection conn=ConnectionManager.getConnection();
	    	 String retExtcount=("select ISNULL((COUNT(*)),0)AS rdate from empmast where EMPNO="+Empno+" and RETIREMENT_EXT_PERIOD > retirementdate");
	    	 Statement stmt=conn.createStatement();
	    	 ResultSet rslt=stmt.executeQuery(retExtcount);
	    	System.out.println("RETExt.COUNT:-"+retExtcount);
			 while(rslt.next())
			 {
				 conn.commit();
				 flags=rslt.getInt("rdate");
			 }
			 conn.close();
	    } 
	   	catch (SQLException e) 
	   	{
		   e.printStackTrace();
		}
	return flags;
	}
	public ArrayList<EmployeeBean> getRetirteEmplist(String currDate,String NextMonthDT) {
		Connection con = ConnectionManager.getConnection();
		ArrayList<EmployeeBean> alist = new ArrayList<EmployeeBean>();
		try {
			Statement st = con.createStatement();
			ResultSet rs = null;
			String SQL = ("select EMPNO,retirementdate,CONVERT(NVARCHAR(100),LNAME+' '+FNAME+' '+MNAME) as name from empmast "
								+"	where retirementdate between '"+ReportDAO.BOM(currDate)+"' and '"+ReportDAO.EOM(NextMonthDT)+"' "
								+"	and STATUS='A' order by  retirementdate");
			System.out.println("AJ1..  "+SQL);
			rs = st.executeQuery(SQL);
			
			while (rs.next()) {
				EmployeeBean empb = new EmployeeBean();
				empb.setEMPNO(rs.getInt("EMPNO"));
				empb.setRETIREMENTDATE(rs.getString("retirementdate"));
				empb.setFNAME(rs.getString("name"));
				alist.add(empb);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	public ArrayList<EmployeeBean> getTodayRetirteEmplist() {
		Connection con = ConnectionManager.getConnection();
		java.util.Date d = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		String date = sdf.format(d);
		System.out.println("Aj..  "+date);//'"+ReportDAO.EOM(PAYREGDT)+"'
		
		
		
		ArrayList<EmployeeBean> alist = new ArrayList<EmployeeBean>();
		try {
			Statement st = con.createStatement();
			ResultSet rs = null;
			String SQL = ("select EMPNO,retirementdate,CONVERT(NVARCHAR(100),LNAME+' '+FNAME+' '+MNAME) as name from empmast "
								+"	where retirementdate =  '"+date+"' "
								+"	and STATUS='A' order by  CONVERT(NVARCHAR, CAST(retirementdate AS DATETIME), 105)");
			System.out.println("AJ2..  "+SQL);
			rs = st.executeQuery(SQL);
			
			while (rs.next()) {
				EmployeeBean empb = new EmployeeBean();
				empb.setEMPNO(rs.getInt("EMPNO"));
				empb.setRETIREMENTDATE(rs.getString("retirementdate"));
				empb.setFNAME(rs.getString("name"));
				alist.add(empb);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	public String chkEffectbetDate(int Empno) throws ParseException
	{
		 String flags = "";
	   try
	   {
	    	 Connection conn=ConnectionManager.getConnection();
	    	 String retExtcount=("select t.EFFDATE from emptran t where t.EMPNO="+Empno+" and t.SRNO=(select MAX(e.SRNO) from emptran e where e.EMPNO="+Empno+")");
	    	 Statement stmt=conn.createStatement();
	    	 ResultSet rslt=stmt.executeQuery(retExtcount);
	    	System.out.println("RETExt.COUNT:-"+retExtcount);
			 while(rslt.next())
			 {
				 conn.commit();
				 flags=rslt.getString("EFFDATE");
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