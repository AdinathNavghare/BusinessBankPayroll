package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import payroll.Model.TripBean;

public class TripHandler {
	
	int travelcd=0;
	
	public int addTravelAdmin(TripBean tb){
		
		Connection conn;
		conn = ConnectionManager.getConnection();
		try 
		{
			
			Statement st = conn.createStatement();
			ResultSet rs;
			rs=st.executeQuery("select max(TRCODE) from TRAVELMASTER ");
			while(rs.next())
			{
				travelcd=rs.getInt(1);
			}
			travelcd=travelcd+1;
			System.out.println(travelcd);
			
			st.execute("insert into TRAVELMASTER(TRCODE,EMPNO,TOURRPT,APPDATE,STARTDATE,ENDDATE,STARTTIME,ENDTIME) " +
					" values("+travelcd+",'"+tb.getEMPNO()+"','"+tb.getTOURRPT()+"', " +
							"CAST('"+tb.getAPPDATE()+"' AS DATE), CAST('"+tb.getSTARTDATE()+"' AS DATE), " +
											"CAST('"+tb.getENDDATE()+"' AS DATE), '"+tb.getSTARTTIME()+"','"+tb.getENDTIME()+"')");
			
			conn.close();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return travelcd;
		
	}
	
	public TripBean getAdminDetails(int trcode){
		
		Connection conn;
		conn = ConnectionManager.getConnection();
		TripBean trbn = null;
		try 
		{
			
			Statement st = conn.createStatement();
			System.out.println("In getAdminDetails Handler");
			ResultSet rs = st.executeQuery("select * from TRAVELMASTER where TRCODE="+trcode);
			while(rs.next()){
				trbn = new TripBean();
				trbn.setTRCODE(rs.getInt(1));
				trbn.setEMPNO(rs.getInt(2));
				trbn.setTOURRPT(rs.getString(3));
				trbn.setAPPDATE(rs.getString(4));
				trbn.setSTARTDATE(rs.getString(5));
				trbn.setENDDATE(rs.getString(6));
			}
			
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return trbn;
		
	}
	
	public boolean addJourneyDetails(TripBean tb){
		
		Connection conn;
		conn = ConnectionManager.getConnection();
		boolean flag = false;
		int srno = 0;
		try 
		{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(SRNO) FROM TRDETAILS WHERE DTYP='A' AND TRCODE="+tb.getTRCODE());
			while(rs.next()){
				srno = rs.getInt(1);
			}
			srno = srno + 1;
			System.out.println("srno "+srno);
			
			st.execute("insert into TRDETAILS(TRCODE,EMPNO,DTYP,DDATE,DFROM,DTO,DFAIRDP,DLOCATION,DTMODE,DAMT,DCLASS,DPARTI,DBILLNO,DPNAME,noofday,SRNO)" +
					"values("+tb.getTRCODE()+", "+tb.getEMPNO()+" ,'A', CAST('"+tb.getDDATE()+"' AS DATE), '"+tb.getDFROM()+"', '"+tb.getDTO()+"'," +
							""+tb.getDFAIRDP()+",'null',"+tb.getDTMODE()+","+tb.getDAMT()+","+tb.getDCLASS()+",'null','null','null',0,"+srno+") ");
			
			flag = true;
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
		
	}
	
	public boolean addLocalConvExpDetails(TripBean tb){
		
		Connection conn;
		conn = ConnectionManager.getConnection();
		boolean flag = false;
		int srno = 0;
		try 
		{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(SRNO) FROM TRDETAILS WHERE DTYP='B' AND TRCODE="+tb.getTRCODE());
			while(rs.next()){
				srno = rs.getInt(1);
			}
			srno = srno + 1;
			System.out.println("srno "+srno);
			
			st.execute("insert into TRDETAILS(TRCODE,EMPNO,DTYP,DDATE,DFROM,DTO,DFAIRDP,DLOCATION,DTMODE,DAMT,DCLASS,DPARTI,DBILLNO,DPNAME,noofday,SRNO)" +
					"values("+tb.getTRCODE()+", "+tb.getEMPNO()+" ,'B', CAST('"+tb.getDDATE()+"' AS DATE), '"+tb.getDFROM()+"', '"+tb.getDTO()+"'," +
							""+tb.getDFAIRDP()+",'null',"+tb.getDTMODE()+","+tb.getDAMT()+",0,'null','null','null',0,"+srno+") ");
			
			flag = true;	
			conn.close();
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
		
	}
	
	public boolean addFoodExpDetails(TripBean tb){
		
		Connection conn;
		conn = ConnectionManager.getConnection();
		boolean flag = false;
		int srno = 0;
		try 
		{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(SRNO) FROM TRDETAILS WHERE DTYP='C' AND TRCODE="+tb.getTRCODE());
			while(rs.next()){
				srno = rs.getInt(1);
			}
			srno = srno + 1;
			st.execute("insert into TRDETAILS(TRCODE,EMPNO,DTYP,DDATE,DFROM,DTO,DFAIRDP,DLOCATION,DTMODE,DAMT,DCLASS,DPARTI,DBILLNO,DPNAME,noofday,SRNO)" +
					"values("+tb.getTRCODE()+", "+tb.getEMPNO()+" ,'C', CAST('"+tb.getDDATE()+"' AS DATE), '"+tb.getDFROM()+"', '"+tb.getDTO()+"'," +
							""+tb.getDFAIRDP()+",'null',0,"+tb.getDAMT()+",0,'null','null','null',"+tb.getNOFDAY()+","+srno+") ");
		
			flag = true;	
			conn.close();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
		
	}
	
	public boolean addOtherMiscExpDetails(TripBean tb){
		
		Connection conn;
		conn = ConnectionManager.getConnection();
		boolean flag = false;
		int srno = 0;
		try 
		{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(SRNO) FROM TRDETAILS WHERE DTYP='D' AND TRCODE="+tb.getTRCODE());
			while(rs.next()){
				srno = rs.getInt(1);
			}
			srno = srno + 1;
			st.execute("insert into TRDETAILS(TRCODE,EMPNO,DTYP,DDATE,DFROM,DTO,DFAIRDP,DLOCATION,DTMODE,DAMT,DCLASS,DPARTI,DBILLNO,DPNAME,noofday,SRNO)" +
					"values('"+tb.getTRCODE()+"', '"+tb.getEMPNO()+"' ,'D', CAST('"+tb.getDDATE()+"' AS DATE), 'null', 'null'," +
							"0,'null',0,"+tb.getDAMT()+",0,'"+tb.getDPARTI()+"','"+tb.getDBILLNO()+"','"+tb.getDPNAME()+"',0,"+srno+") ");
			
			flag = true;
			conn.close();
		
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
		
	}
	
	public boolean addMiscExpDetails(TripBean tb){
		
		Connection conn;
		conn = ConnectionManager.getConnection();
		boolean flag = false;
		int srno = 0;
		try 
		{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(SRNO) FROM TRDETAILS WHERE DTYP='E' AND TRCODE="+tb.getTRCODE());
			while(rs.next()){
				srno = rs.getInt(1);
			}
			srno = srno + 1;	     		
			st.execute("insert into TRDETAILS(TRCODE,EMPNO,DTYP,DDATE,DFROM,DTO,DFAIRDP,DLOCATION,DTMODE,DAMT,DCLASS,DPARTI,DBILLNO,DPNAME,noofday,SRNO)" +
					"values("+tb.getTRCODE()+", "+tb.getEMPNO()+" ,'E', '"+tb.getDDATE()+"', 'null', 'null'," +
							"0,'"+tb.getDLOCATION()+"',0,'"+tb.getDAMT()+"',0,'"+tb.getDPARTI()+"','null','null',0,"+srno+") ");
			flag = true;
			conn.close();
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
		
	}
	
	public boolean addAcimprestDetails(TripBean tb){
		
		Connection conn;
		conn = ConnectionManager.getConnection();
		boolean flag = false;
		int srno = 0;
		try 
		{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(SRNO) FROM TRDETAILS WHERE DTYP='B' AND TRCODE="+tb.getTRCODE());
			while(rs.next()){
				srno = rs.getInt(1);
			}
			srno = srno + 1;
			st.execute("insert into TRACCOUNT(TRCODE,EMPNO,ACDATE,ACPARTI,ACREPTNO,ACPAYMNT,ACAMT,TOURRPT,CHEKSTATE)" +
					"values("+tb.getTRCODE()+", "+tb.getEMPNO()+", CAST('"+tb.getACDATE()+"' AS DATE), '"+tb.getACPARTI()+"', " +
							"'"+tb.getACREPTNO()+"', "+tb.getACPAYMNT()+", "+tb.getACAMT()+", '"+tb.getTOURRPT()+"',1) ");
			
			flag = true;
			conn.close();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
		
	}
	
	public TripBean getJourneyDetails(int trcode, int srno){
		
		Connection conn;
		conn = ConnectionManager.getConnection();
		TripBean tb = null;
		try 
		{
			
			Statement st = conn.createStatement();
			ResultSet rs;

			rs=st.executeQuery("select DDATE, DFROM, DTO, DTMODE, DCLASS, DFAIRDP, DAMT FROM TRDETAILS WHERE DTYP='A' AND TRCODE = "+trcode+" AND SRNO="+srno);
			while(rs.next()){
				tb = new TripBean();
				tb.setDDATE(rs.getString(1));
				tb.setDFROM(rs.getString(2));
				tb.setDTO(rs.getString(3));
				tb.setDTMODE(rs.getInt(4));
				tb.setDCLASS(rs.getInt(5));
				tb.setDFAIRDP(rs.getInt(6));
				tb.setDAMT(rs.getInt(7));
				
			}
			
			conn.close();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return tb;
		
	}
	
	public TripBean getLocExpDetails(int trcode, int srno){
		
		Connection conn;
		conn = ConnectionManager.getConnection();
		TripBean tb = null;
		try 
		{
			
			Statement st = conn.createStatement();
			ResultSet rs;
			
			rs=st.executeQuery("select DDATE, DFROM, DTO, DTMODE, DFAIRDP, DAMT from TRDETAILS where dtyp='B' AND TRCODE = "+trcode+" AND SRNO="+srno);
			while(rs.next()){
				tb = new TripBean();
				tb.setDDATE(rs.getString(1));
				tb.setDFROM(rs.getString(2));
				tb.setDTO(rs.getString(3));
				tb.setDTMODE(rs.getInt(4));
				tb.setDFAIRDP(rs.getInt(5));
				tb.setDAMT(rs.getInt(6));
	
			}
			conn.close();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return tb;
		
	}
	
	public TripBean getFoodExpDetails(int trcode, int srno){
		
		Connection conn;
		conn = ConnectionManager.getConnection();
		TripBean tb = null;
		try 
		{
			
			Statement st = conn.createStatement();
			ResultSet rs;
			
			rs=st.executeQuery("select DDATE, DFROM, DTO,NOOFDAY, DFAIRDP, DAMT from TRDETAILS where dtyp='C' AND TRCODE = "+trcode+" AND SRNO="+srno);
			while(rs.next()){
				tb = new TripBean();
				tb.setDDATE(rs.getString(1));
				tb.setDFROM(rs.getString(2));
				tb.setDTO(rs.getString(3));
				tb.setNOFDAY(rs.getInt(4));
				tb.setDFAIRDP(rs.getInt(5));
				tb.setDAMT(rs.getInt(6));
			}
			conn.close();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return tb;
		
	}
	
	public TripBean getOtherMiscExpDetails(int trcode, int srno){
		
		Connection conn;
		conn = ConnectionManager.getConnection();
		TripBean tb = null;
		try 
		{
			
			Statement st = conn.createStatement();
			ResultSet rs;
			
			rs=st.executeQuery("select DPARTI, DPNAME, DBILLNO, DDATE, DAMT from TRDETAILS where dtyp='D' AND TRCODE = "+trcode+" AND SRNO="+srno);
			while(rs.next()){
				tb = new TripBean();
				tb.setDPARTI(rs.getString(1));
				tb.setDPNAME(rs.getString(2));
				tb.setDBILLNO(rs.getString(3));
				tb.setDDATE(rs.getString(4));
				tb.setDAMT(rs.getInt(5));
				
			}
			conn.close();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return tb;
		
	}
	
	public TripBean getMiscExpDetails(int trcode, int srno){
		
		Connection conn;
		conn = ConnectionManager.getConnection();
		TripBean tb = null;
		try 
		{
			
			Statement st = conn.createStatement();
			ResultSet rs;
			
			rs=st.executeQuery("select DDATE, DPARTI, DLOCATION, DAMT from TRDETAILS where dtyp='E' AND TRCODE = "+trcode+"AND SRNO="+srno);
			while(rs.next()){
				tb = new TripBean();
				tb.setDDATE(rs.getString(1));
				tb.setDPARTI(rs.getString(2));
				tb.setDLOCATION(rs.getString(3));
				//tb.setDBILLNO(rs.getString(3));
				tb.setDAMT(rs.getInt(4));
		
			}
			conn.close();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return tb;
		
	}
	
	public ArrayList<TripBean> getAcImprestDetails(int trcode){
		
		Connection conn;
		conn = ConnectionManager.getConnection();
		ArrayList<TripBean> list = new ArrayList<TripBean>();
		try 
		{
			
			Statement st = conn.createStatement();		
			ResultSet rs = st.executeQuery("select ACDATE, ACPARTI, ACREPTNO, ACPAYMNT, ACAMT, TOURRPT, CHEKSTATE from TRACCOUNT where TRCODE="+trcode);
			while(rs.next()){
				TripBean tb= new TripBean();
				tb.setACDATE(rs.getString(1));
				tb.setACPARTI(rs.getString(2));
				tb.setACREPTNO(rs.getString(3));
				tb.setACPAYMNT(rs.getInt(4));
				tb.setACAMT(rs.getInt(5));
				tb.setTOURRPT(rs.getString(6));
				tb.setCHECKSTATE(rs.getInt(7));
				list.add(tb);
			}
			
			conn.close();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
		
	}
	
	public TripBean getAcImprestPrtDetails(int trcode, String prtclrs){
		
		Connection conn;
		conn = ConnectionManager.getConnection();
		TripBean tb = null;
		try 
		{	
			Statement st = conn.createStatement();		
			ResultSet rs = st.executeQuery("select ACDATE, ACPARTI, ACREPTNO, ACPAYMNT, ACAMT from TRACCOUNT where TRCODE="+trcode+" AND ACPARTI="+"('"+prtclrs+"')");
			while(rs.next()){
				
				tb= new TripBean();
				tb.setACDATE(rs.getString(1));
				tb.setACPARTI(rs.getString(2));
				tb.setACREPTNO(rs.getString(3));
				tb.setACPAYMNT(rs.getInt(4));
				tb.setACAMT(rs.getInt(5));
			}
			
			conn.close();	
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return tb;
		
	}
	public int getState(int trcode){
		
		Connection conn;
		conn = ConnectionManager.getConnection();
		int status = 0;
		try 
		{	
			Statement st = conn.createStatement();		
			ResultSet rs = null;
			rs = st.executeQuery("SELECT CHEKSTATE FROM TRACCOUNT WHERE TRCODE="+trcode);
			while(rs.next()){
				status = rs.getInt(1);
			}
			conn.close();	
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
		
	public boolean updateJourneyDetails(TripBean tb, int srno){
		
		Connection conn;
		conn = ConnectionManager.getConnection();
		boolean flag = false;
		try 
		{
			
			Statement st = conn.createStatement();
			st.execute("UPDATE TRDETAILS SET DDATE=CAST('"+tb.getDDATE()+"' AS DATE),DFROM='"+tb.getDFROM()+"'" +
											",DTO='"+tb.getDTO()+"',DFAIRDP="+tb.getDFAIRDP()+",DTMODE="+tb.getDTMODE()+"" +
											",DAMT="+tb.getDAMT()+",DCLASS="+tb.getDCLASS()+" WHERE DTYP='A' AND SRNO="+srno);
	
			flag = true;
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;	
	}
	
	public boolean updateLocExpDetails(TripBean tb, int srno){
		
		Connection conn;
		conn = ConnectionManager.getConnection();
		boolean flag = false;
		try 
		{
			
			Statement st = conn.createStatement();
			st.execute("UPDATE TRDETAILS SET DDATE=CAST('"+tb.getDDATE()+"' AS DATE),DFROM='"+tb.getDFROM()+"'" +
											",DTO='"+tb.getDTO()+"',DFAIRDP="+tb.getDFAIRDP()+",DTMODE="+tb.getDTMODE()+"" +
											",DAMT="+tb.getDAMT()+" WHERE DTYP='B' AND SRNO="+srno);
			
			flag = true;
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;	
	}
	
	public boolean updateFoodExpDetails(TripBean tb, int srno){
		
		Connection conn;
		conn = ConnectionManager.getConnection();
		boolean flag = false;
		try 
		{
			
			Statement st = conn.createStatement();
			st.execute("UPDATE TRDETAILS SET DDATE=CAST('"+tb.getDDATE()+"' AS DATE),DFROM='"+tb.getDFROM()+"'" +
											",DTO='"+tb.getDTO()+"',DFAIRDP="+tb.getDFAIRDP()+",noofday="+tb.getNOFDAY()+"" +
											",DAMT="+tb.getDAMT()+" WHERE DTYP='C' AND SRNO="+srno);
			
			flag = true;
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;	
	}
	
	public boolean updateOtherMiscExpDetails(TripBean tb, int srno){
		
		Connection conn;
		conn = ConnectionManager.getConnection();
		boolean flag = false;
		try 
		{
			
			Statement st = conn.createStatement();
			st.execute("UPDATE TRDETAILS SET DDATE=CAST('"+tb.getDDATE()+"' AS DATE),DAMT="+tb.getDAMT()+"," +
											"DPARTI='"+tb.getDPARTI()+"',DBILLNO='"+tb.getDBILLNO()+"'," +
											"DPNAME='"+tb.getDPNAME()+"' WHERE DTYP='D' AND SRNO="+srno);
	
			flag = true;
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;	
	}
	
	public boolean updateMiscExpDetails(TripBean tb, int srno){
		
		Connection conn;
		conn = ConnectionManager.getConnection();
		boolean flag = false;
		try 
		{
			
			Statement st = conn.createStatement();
			st.execute("UPDATE TRDETAILS SET DDATE=CAST('"+tb.getDDATE()+"' AS DATE)," +
								"DAMT="+tb.getDAMT()+",DPARTI='"+tb.getDPARTI()+"',DLOCATION='"+tb.getDLOCATION()+"'" +
								" WHERE DTYP='E' AND SRNO="+srno);
	
			flag = true;
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;	
	}
	
	public boolean updateACImprestDetails(TripBean tb, int trcode, String acparti){
		
		Connection conn;
		conn = ConnectionManager.getConnection();
		boolean flag = false;
		try 
		{
			
			Statement st = conn.createStatement();
			st.execute("UPDATE TRACCOUNT SET ACDATE=CAST('"+tb.getACDATE()+"' AS DATE)," +
											"ACREPTNO='"+tb.getACREPTNO()+"',ACPAYMNT='"+tb.getACPAYMNT()+"'," +
											"ACAMT='"+tb.getACAMT()+"' WHERE TRCODE="+trcode+" AND ACPARTI="+"('"+acparti+"')");
	
			flag = true;
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;	
	}
	
	public boolean deleteRecord(char dtype, String date){
		
		Connection conn;
		conn = ConnectionManager.getConnection();
		boolean flag = false;
		try 
		{
			
			Statement st = conn.createStatement();
			st.execute("DELETE FROM TRDETAILS WHERE DTYP="+dtype+" AND DDATE="+"('"+date+"')");
			
			flag = true;
			conn.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	public ArrayList<TripBean> getTripInfo(int EMPNO){
		Connection conn;
		conn = ConnectionManager.getConnection();
		TripBean tb;
		ArrayList<TripBean> al= new ArrayList<TripBean>();
		try {
			
			Statement st = conn.createStatement();
			ResultSet rs;
			
			rs=st.executeQuery("select TRCODE,TOURRPT,STARTDATE,ENDDATE from TRAVELMASTER where EMPNO = "+EMPNO);
			while(rs.next()){
				tb = new TripBean();
				tb.setTRCODE(rs.getInt(1));
				tb.setTOURRPT(rs.getString(2));
				tb.setSTARTDATE(rs.getString(3));
				tb.setENDDATE(rs.getString(4));
				al.add(tb);
			}
			conn.close();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return al;
	}
	
	public ArrayList<TripBean> getTripDetails(int trcode){
		Connection conn;
		conn = ConnectionManager.getConnection();
		TripBean tb;
		ArrayList<TripBean> list= new ArrayList<TripBean>();
		try {
			
			Statement st = conn.createStatement();
			ResultSet rs;
			
			rs=st.executeQuery("select * from TRDETAILS where TRCODE = "+trcode);
			while(rs.next()){
				tb = new TripBean();
				
				tb.setTRCODE(rs.getInt(1));
				tb.setEMPNO(rs.getInt(2));
				tb.setDTYP(rs.getString(3).charAt(0));
				tb.setDDATE(rs.getString(4));
				tb.setDFROM(rs.getString(5));
				tb.setDTO(rs.getString(6));
				tb.setDFAIRDP(rs.getInt(7));
				tb.setDLOCATION(rs.getString(8));
				tb.setDTMODE(rs.getInt(9));
				tb.setDAMT(rs.getInt(10));
				tb.setDCLASS(rs.getInt(11));
				tb.setDPARTI(rs.getString(12));
				tb.setDBILLNO(rs.getString(13));
				tb.setDPNAME(rs.getString(14));
				tb.setNOFDAY(rs.getInt(15));
				tb.setSRNO(rs.getInt(16));
				list.add(tb);
			}
			conn.close();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
