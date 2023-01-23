package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import payroll.Core.ReportDAO;
import payroll.Model.Form16Bean;



public class Form16Handler {
	
	public String addValue(Form16Bean FB)
	{
		String result= "false";
		Connection con= ConnectionManager.getConnection();
		try
		{
			Statement st1 = con.createStatement();
			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String check = "select trncd from YTDTRAN where trncd = "+FB.getTRNCD()+
					" and empno = "+FB.getEMPNO()+" and trndt='"+FB.getTRNDT()+"'";
			String sql="INSERT INTO YTDTRAN VALUES("
						+"'"+FB.getTRNDT()+"',"
						+FB.getEMPNO()+","
						+FB.getTRNCD()+","
						+FB.getSRNO()+","
						+FB.getINP_AMT()+","
						+FB.getCAL_AMT()+","
						+FB.getADJ_AMT()+","
						+FB.getARR_AMT()+","
						+FB.getNET_AMT()+","
						+"'"+FB.getCF_SW()+"',"
						+"'"+FB.getUSRCODE()+"',"
						+"'"+FB.getUPDDT()+"',"
						+"'"+FB.getSTATUS()+"'"
						+")"; 
			System.out.println(sql);
			ResultSet chk = st.executeQuery(check);
			if(chk.first()){
				updateValue(FB,chk.getInt(1) );
				result = "present";
			}
			else{
				st1.execute(sql);
				System.out.println("hi");
				result = "true";
			}
			con.close();
		}
		catch(SQLException e)
		{
			try
			{
				con.close();
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return result;		
	}

	
	public boolean getAvailability(int empno, int trncd)
	{
		Connection con= ConnectionManager.getConnection();
		boolean result=false;
		try
		{
			Statement st = con.createStatement();
			String sql = "SELECT * FROM YTDTRAN WHERE EMPNO="+empno+" AND TRNCD ="+trncd;
			ResultSet rs = st.executeQuery(sql);
			if(rs.next())
			{
				result = true;
			}
			con.close();
		}	
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	public boolean updateValue(Form16Bean fb1,int trncd)
	{
		Connection con= ConnectionManager.getConnection();
		boolean result=false;
		try
		{
			Statement st = con.createStatement();
			String update="UPDATE YTDTRAN set INP_AMT="+fb1.getINP_AMT()+", CAL_AMT="+fb1.getCAL_AMT()+"," +
							" NET_AMT="+fb1.getNET_AMT()+", UPDDT = GETDATE() ,CF_SW='"+fb1.getCF_SW()+"'" +
							"where EMPNO="+fb1.getEMPNO()+" and TRNCD="+trncd+" and TRNDT='"+fb1.getTRNDT()+"' ";
			st.executeUpdate(update);
			result=true;
			con.close();
		}	
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}



	public Map<Integer,Form16Bean> getForm16Value(String EMPNO,String year)
	{
		ResultSet rs=null;
		Statement st = null;
		Form16Bean fbn = null;
		Map<Integer,Form16Bean> map = new HashMap<Integer, Form16Bean>(); 
		year="30-Apr-"+year;
		try
		{
			Connection con;
			con = ConnectionManager.getConnection();
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			int[] codes = {0,564,565,522,523,524,525,526,527,528,
				       529,530,531,569,571,532,533,534,535,500,501,537,538,511,512,513,539,540,541,542,543,
				       544,545,546,547,548,549,550,551,552,553,554,555,556,557,558,559,570,587,588};
			int max = 50;
			
			for(int i=1; i<max; i++)
			{
			//	System.out.println("select * from YTDTRAN where EMPNO="+EMPNO+ " AND TRNCD="+codes[i]+"AND TRNDT='"+year+"'");
				rs=st.executeQuery("select * from YTDTRAN where EMPNO="+EMPNO+ " AND TRNCD="+codes[i]+" AND TRNDT='"+year+"'");
				
				if(rs.next())
				{
					
				rs.previous();
				while(rs.next())
				{
					fbn = new Form16Bean();
					fbn.setTRNDT(rs.getString("TRNDT")!=null?EmpOffHandler.dateFormat(rs.getDate("TRNDT")):"");
					fbn.setEMPNO(rs.getString("EMPNO")!=null?rs.getInt("EMPNO"):0);
					fbn.setTRNCD(rs.getString("TRNCD")!=null?rs.getInt("TRNCD"):0);
					fbn.setSRNO(rs.getString("SRNO")!=null?rs.getInt("SRNO"):0);
					fbn.setINP_AMT(rs.getString("INP_AMT")!=null?rs.getFloat("INP_AMT"):0);
					fbn.setCAL_AMT(rs.getString("CAL_AMT")!=null?rs.getFloat("CAL_AMT"):0);
					fbn.setADJ_AMT(rs.getString("ADJ_AMT")!=null?rs.getFloat("ADJ_AMT"):0);
					fbn.setARR_AMT(rs.getString("ARR_AMT")!=null?rs.getFloat("ARR_AMT"):0);
					fbn.setNET_AMT(rs.getString("NET_AMT")!=null?rs.getFloat("NET_AMT"):0);
					fbn.setCF_SW(rs.getString("CF_SW")==null?"--":rs.getString("CF_SW"));
					fbn.setUSRCODE(rs.getString("USRCODE")!=null?rs.getString("USRCODE"):"");
					fbn.setUPDDT(rs.getString("UPDDT")!=null?EmpOffHandler.dateFormat(rs.getDate("UPDDT")):"");
					fbn.setSTATUS(rs.getString("STATUS")!=null?rs.getString("STATUS"):"");
					map.put(fbn.getTRNCD(), fbn);
				}	
				}
				else
				{
					fbn = new Form16Bean();
					fbn.setTRNDT(year);
					fbn.setEMPNO(Integer.parseInt(EMPNO));
					fbn.setTRNCD(codes[i]);
					fbn.setSRNO(0);
					fbn.setINP_AMT(0);
					fbn.setCAL_AMT(0);
					fbn.setADJ_AMT(0);
					fbn.setARR_AMT(0);
					fbn.setNET_AMT(0);
					fbn.setCF_SW("--");
					fbn.setUSRCODE("");
					fbn.setUPDDT(ReportDAO.getSysDate());
					fbn.setSTATUS("F");
					map.put(fbn.getTRNCD(), fbn);
				}
			}
				con.close();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return map;
		
	}
	public Map<Integer,Integer> getSetupParams(String year,String EMPNO)
	{
		ResultSet rs=null;
		Statement st = null;
		Map<Integer,Integer> map = new HashMap<Integer, Integer>(); 
		try
		{
			Connection con;
			con = ConnectionManager.getConnection();
			st = con.createStatement();
			rs=st.executeQuery("select * from YTDTRAN where EMPNO="+EMPNO+"" +
					" AND TRNCD IN(564,571) and TRNDT='"+year+"' ");	
			while(rs.next())
			{
				map.put(rs.getInt("trncd"), rs.getInt("inp_amt"));
			}	
		con.close();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return map;	
	}
	public Map<Integer,Integer> taxComputation(String EMPNO,String year)
	{
		ResultSet rs=null;
		Statement st = null;
		Map<Integer,Integer> map = new HashMap<Integer, Integer>();
		EmpAttendanceHandler empAttendanceHandler=new EmpAttendanceHandler();
		String serverDate=empAttendanceHandler.getServerDate();
		String beginningDate=ReportDAO.BoFinancialy(serverDate);
		//System.out.println(beginningDate);
		String endDate=ReportDAO.EoFinancialy(serverDate);
		//System.out.println(endDate);
		year="30-Apr-"+year;
		try
		{
			Connection con;
			con = ConnectionManager.getConnection(); 
			
			
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			System.out.println("select trncd, inp_amt, (select sum(inp_amt) from ytdtran where empno="+EMPNO+" and trncd=108 and trndt between '"+beginningDate+"' and '"+endDate+"') as TA_EXEMP" +
							" from YTDTRAN where EMPNO="+EMPNO+" AND TRNCD in(565,521,586,524,522,523,525,582,570,535,526," +
								"581,549,550,552,553,554,551,555,559,558,546,538,501,536,540,539,537,548,570)");
		
			rs=st.executeQuery("select trncd, inp_amt, (select sum(inp_amt) from ytdtran where empno="+EMPNO+" and trncd=108 and trndt between '"+beginningDate+"' and '"+endDate+"') as TA_EXEMP" +
								" from YTDTRAN where EMPNO="+EMPNO+" AND TRNCD in(565,521,586,524,522,523,525,582,570,535,526," +
								"581,549,550,552,553,554,551,555,559,558,546,538,501,536,540,539,537,548,570) AND TRNDT='"+year+"'");	
			while(rs.next())
			{ 
				if(rs.isFirst()){
					map.put(108, rs.getInt("TA_EXEMP"));
					map.put(rs.getInt("trncd"), rs.getInt("inp_amt"));
				} else {
					map.put(rs.getInt("trncd"), rs.getInt("inp_amt"));
				}
			}	
		con.close();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return map;	
	}
}

