package payroll.DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import payroll.Model.CodeMasterBean;


public class CodeMasterHandler {

	public ArrayList<CodeMasterBean> getCDMAST(String key)
	{
		ArrayList<CodeMasterBean> CMBList=new ArrayList<CodeMasterBean>();
		int cdtype=Integer.parseInt(key);
		Connection con=null;
		try
		{
			con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM CDMAST WHERE CDTYPE="+cdtype+" and TRNCD not in(260,261,262,257,249,256) ORDER BY TRNCD");
			while(rs.next())
			{
				CodeMasterBean CMB=new CodeMasterBean();
				CMB.setTRNCD(rs.getInt("TRNCD"));
				CMB.setDISC(rs.getString("DISC"));
				CMB.setSDESC(decode(rs.getString("SDESC")!=null?rs.getString("SDESC"):"NA"));
				CMB.setPSLIPYN(decode(rs.getString("PSLIPYN")!=null?rs.getString("PSLIPYN"):"NA"));
				CMB.setTAXABLE(decode(rs.getString("TAXABLE")!=null?rs.getString("TAXABLE"):"NA"));
				CMB.setAUTOPOST(decode(rs.getString("AUTOPOST")!=null?rs.getString("AUTOPOST"):"NA"));
				CMB.setACNO(rs.getInt("ACNO"));
				CMB.setSUBSYS(decode(rs.getString("SUBSYS")!=null?rs.getString("SUBSYS"):"NA"));
				CMB.setGROSS_YN(decode(rs.getString("GROSS_YN")!=null?rs.getString("GROSS_YN"):"NA"));
				CMB.setSRNO(rs.getString("SRNO")==null?0:rs.getInt("SRNO"));
				CMB.setEMPTYPE(rs.getString("EMPTYPE")==null?-1:rs.getInt("EMPTYPE"));
				CMBList.add(CMB);
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
			System.out.println("Error in CodeMasterHandler getCDMAST(String key) method "+e);
		}
		return CMBList;
	}
	
	public CodeMasterBean getCodeDetails(int trncd)
	{
		Connection con=null;
		CodeMasterBean CMB=new CodeMasterBean();
		try
		{
			con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM CDMAST WHERE TRNCD="+trncd+"");
			if(rs.next())
			{
				
				CMB.setTRNCD(rs.getInt("TRNCD"));
				CMB.setDISC(rs.getString("DISC"));
				CMB.setSDESC(decode(rs.getString("SDESC")!=null?rs.getString("SDESC"):"NA"));
				CMB.setPSLIPYN(decode(rs.getString("PSLIPYN")!=null?rs.getString("PSLIPYN"):"NA"));
				CMB.setTAXABLE(decode(rs.getString("TAXABLE")!=null?rs.getString("TAXABLE"):"NA"));
				CMB.setAUTOPOST(decode(rs.getString("AUTOPOST")!=null?rs.getString("AUTOPOST"):"NA"));
				CMB.setACNO(rs.getInt("ACNO"));
				CMB.setSUBSYS(decode(rs.getString("SUBSYS")!=null?rs.getString("SUBSYS"):"NA"));
				CMB.setGROSS_YN(decode(rs.getString("GROSS_YN")!=null?rs.getString("GROSS_YN"):"NA"));
				CMB.setPrintsequnce(rs.getString("PRNTSEQNCE")==null?0:rs.getInt("PRNTSEQNCE"));
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
		return CMB;
	}
	
	public ArrayList<CodeMasterBean> getAllCDMASTList()
	{
		ArrayList<CodeMasterBean> CMBList=new ArrayList<CodeMasterBean>();
		
		Connection con=null;
		try
		{
			con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			//ResultSet rs=st.executeQuery("SELECT * FROM CDMAST order by CDTYPE,TRNCD");
			ResultSet rs=st.executeQuery("SELECT * FROM CDMAST order by DISC,CDTYPE,TRNCD");
			while(rs.next())
			{
				CodeMasterBean CMB=new CodeMasterBean();
				CMB.setTRNCD(rs.getString("TRNCD")==null?0:rs.getInt("TRNCD"));
				CMB.setDISC(rs.getString("DISC")==null?"":rs.getString("DISC"));
				/*CMB.setPSLIPYN(decode(rs.getString("PSLIPYN")!=null?rs.getString("PSLIPYN"):"NA"));
				CMB.setTAXABLE(decode(rs.getString("TAXABLE")!=null?rs.getString("TAXABLE"):"NA"));
				CMB.setAUTOPOST(decode(rs.getString("AUTOPOST")!=null?rs.getString("AUTOPOST"):"NA"));
				CMB.setACNO(rs.getInt("ACNO"));
				CMB.setSUBSYS(decode(rs.getString("SUBSYS")!=null?rs.getString("SUBSYS"):"NA"));*/
				CMBList.add(CMB);
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
			System.out.println("Error in CodeMasterHandler getCDMAST(String key) method "+e);
		}
		return CMBList;
	}
	
	public ArrayList<CodeMasterBean> getLoancdList()
	{
		ArrayList<CodeMasterBean> CMBList=new ArrayList<CodeMasterBean>();
		
		Connection con=null;
		try
		{
			con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM CDMAST where TRNCD in( 226 ,227) order by CDTYPE,TRNCD");
			while(rs.next())
			{
				CodeMasterBean CMB=new CodeMasterBean();
				CMB.setTRNCD(rs.getString("TRNCD")==null?0:rs.getInt("TRNCD"));
				CMB.setDISC(rs.getString("DISC")==null?"":rs.getString("DISC"));
				/*CMB.setPSLIPYN(decode(rs.getString("PSLIPYN")!=null?rs.getString("PSLIPYN"):"NA"));
				CMB.setTAXABLE(decode(rs.getString("TAXABLE")!=null?rs.getString("TAXABLE"):"NA"));
				CMB.setAUTOPOST(decode(rs.getString("AUTOPOST")!=null?rs.getString("AUTOPOST"):"NA"));
				CMB.setACNO(rs.getInt("ACNO"));
				CMB.setSUBSYS(decode(rs.getString("SUBSYS")!=null?rs.getString("SUBSYS"):"NA"));*/
				CMBList.add(CMB);
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
			System.out.println("Error in CodeMasterHandler getCDMAST(String key) method "+e);
		}
		return CMBList;
	}
	
	//No auto calculate code list
	
	public ArrayList<CodeMasterBean> getNoAutocalCDList()
	{
		ArrayList<CodeMasterBean> CMBList=new ArrayList<CodeMasterBean>();
		
		Connection con=null;
		try
		{
			con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			/*ResultSet rs=st.executeQuery("SELECT * FROM CDMAST where AUTOPOST='N' order by CDTYPE,TRNCD");*/
			//ResultSet rs=st.executeQuery("SELECT  * FROM CDMAST where TRNCD not in (101,103,104,105,107,108,126,127,128,135,201,199,999,225,202,260,261,262,257,256) order by DISC");
			//ResultSet rs=st.executeQuery("SELECT  * FROM CDMAST where TRNCD not in (101,103,104,105,108,126,127,128,135,201,199,999,225,202,260,261,262,257,256) order by DISC");
			ResultSet rs=st.executeQuery("SELECT  * FROM CDMAST where TRNCD not in (101,103,104,105,108,201,202,999,225) AND PSLIPYN='Y' order by DISC, TRNCD");
			while(rs.next())
			{
				CodeMasterBean CMB=new CodeMasterBean();
				CMB.setTRNCD(rs.getString("TRNCD")==null?0:rs.getInt("TRNCD"));
				CMB.setDISC(rs.getString("DISC")==null?"":rs.getString("DISC"));
				/*CMB.setPSLIPYN(decode(rs.getString("PSLIPYN")!=null?rs.getString("PSLIPYN"):"NA"));
				CMB.setTAXABLE(decode(rs.getString("TAXABLE")!=null?rs.getString("TAXABLE"):"NA"));
				CMB.setAUTOPOST(decode(rs.getString("AUTOPOST")!=null?rs.getString("AUTOPOST"):"NA"));
				CMB.setACNO(rs.getInt("ACNO"));
				CMB.setSUBSYS(decode(rs.getString("SUBSYS")!=null?rs.getString("SUBSYS"):"NA"));*/
				CMBList.add(CMB);
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
			System.out.println("Error in CodeMasterHandler getCDMAST(String key) method "+e);
		}
		return CMBList;
	}
	public ArrayList<CodeMasterBean> getNoAutocalCDListReport()
	{
		ArrayList<CodeMasterBean> CMBList=new ArrayList<CodeMasterBean>();
		
		Connection con=null;
		try
		{
			con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM CDMAST where TRNCD not between 1 and 100 and TRNCD not in(260,261,262,257,249,256) order by DISC, TRNCD");
			//ResultSet rs=st.executeQuery("SELECT  * FROM CDMAST where TRNCD not in (101,103,104,105,107,108,126,127,128,135,201,199,999,225,202) order by DISC");
			while(rs.next())
			{
				CodeMasterBean CMB=new CodeMasterBean();
				CMB.setTRNCD(rs.getString("TRNCD")==null?0:rs.getInt("TRNCD"));
				CMB.setDISC(rs.getString("DISC")==null?"":rs.getString("DISC"));
				/*CMB.setPSLIPYN(decode(rs.getString("PSLIPYN")!=null?rs.getString("PSLIPYN"):"NA"));
				CMB.setTAXABLE(decode(rs.getString("TAXABLE")!=null?rs.getString("TAXABLE"):"NA"));
				CMB.setAUTOPOST(decode(rs.getString("AUTOPOST")!=null?rs.getString("AUTOPOST"):"NA"));
				CMB.setACNO(rs.getInt("ACNO"));
				CMB.setSUBSYS(decode(rs.getString("SUBSYS")!=null?rs.getString("SUBSYS"):"NA"));*/
				CMBList.add(CMB);
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
			System.out.println("Error in CodeMasterHandler getCDMAST(String key) method "+e);
		}
		return CMBList;
	}
	
	
	public ArrayList<CodeMasterBean> SalaryCodeListReport()
	{
		ArrayList<CodeMasterBean> CMBList=new ArrayList<CodeMasterBean>();
		Connection con=null;
		try
		{
			con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			//ResultSet rs=st.executeQuery("SELECT * FROM CDMAST where TRNCD not between 1 and 100 and TRNCD not in(260,261,262,257,249,256) order by DISC, TRNCD");
			ResultSet rs=st.executeQuery("SELECT DISTINCT(C.TRNCD), C.DISC FROM CDMAST C, PAYTRAN P, PAYTRAN_STAGE PS "
					+ "WHERE C.TRNCD=P.TRNCD AND C.TRNCD=PS.TRNCD AND C.PSLIPYN='Y'");

			while(rs.next())
			{
				CodeMasterBean CMB=new CodeMasterBean();
				CMB.setTRNCD(rs.getString("TRNCD")==null?0:rs.getInt("TRNCD"));
				CMB.setDISC(rs.getString("DISC")==null?"":rs.getString("DISC"));
				CMBList.add(CMB);
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
			System.out.println("Error in CodeMasterHandler getCDMAST(String key) method "+e);
		}
		return CMBList;
	}
	
	
	public boolean addCDMast(CodeMasterBean CMB)
	{
		boolean result=false;
		Connection con=null;
		try
		{
			con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			String str="INSERT INTO CDMAST(TRNCD,DISC,PSLIPYN,TAXABLE,AUTOPOST,SUBSYS,ACNO,MAXLIM,MINLIM," +
										   "GROSS_YN,BANK_LOAN,FREQUENCY,FREQNO,BRSUBSYS,BRACNO,DRSUBSYS,DRACNO," +
										   "CF_SW, SDESC,CUMU_YN,CONVYN,ITGRP,PROJYN,PLUSMINUS,ITGRP2,CHKSLB,CDTYPE,PRNTSEQNCE) VALUES("
						+CMB.getTRNCD()
						+",'"+CMB.getDISC()+"'"
						+",'"+CMB.getPSLIPYN()+"'"
						+",'"+CMB.getTAXABLE()+"'"
						+",'"+CMB.getAUTOPOST()+"'"
						+",'"+CMB.getSUBSYS()+"'"
						+","+CMB.getACNO()
						+","+CMB.getMAXLIM()
						+","+CMB.getMINLIM()
						+",'"+CMB.getGROSS_YN()+"'"
						+",'"+CMB.getBANK_LOAN()+"'"
						+",'"+CMB.getFREQUENCY()+"'"
						+","+CMB.getFREQNO()
						+",'"+CMB.getBRSUBSYS()+"'"
						+","+CMB.getBRACNO()
						+",'"+CMB.getDRSUBSYS()+"'"
						+","+CMB.getDRACNO()
						+",'"+CMB.getCF_SW()+"'"
						+",'"+CMB.getSDESC()+"'"
						+",'"+CMB.getCUMU_YN()+"'"
						+",'"+CMB.getCONVYN()+"'"
						+",'"+CMB.getITGRP()+"'"
						+",'"+CMB.getPROJYN()+"'"
						+",'"+CMB.getPLUSMINUS()+"'"
						+",'"+CMB.getITGRP2()+"'"
						+",'"+CMB.getCHKSLB()+"'"
						+","+CMB.getCDTYPE()
						+","+CMB.getPrintsequnce()
						+")";
						
			st.execute(str);
			con.close();
			result=true;
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
			System.out.println("Error in CodeMasterHandler addCDMast(CodeMasterBean CMB) "+e);
		}
		return result;
	}
	
	public static String decode(String code)
	{		
			String Decode= new String();
			if(code.equalsIgnoreCase("Y"))
				Decode="YES";
			else if(code.equalsIgnoreCase("N"))
				Decode="NO";
			else if(code.equalsIgnoreCase("NA"))
				Decode="---";
			else 
				Decode=code;
			//System.out.println(Decode);
			return Decode;
	}
	
	public boolean deleteCode(int trncd)
	{
		Connection con=null;
		boolean result=false;
		try
		{
			con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
		
			st.execute("DELETE FROM CDMAST WHERE TRNCD="+trncd+"");
			result = true;
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
			System.out.println("Error in CodeMasterHandler getCDesc(int trncd) method "+e);
		}
		return result;
	}
	
	public static String getCDesc(int trncd)
	{
		Connection con=null;
		String result="";
		try
		{
			con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
		
			ResultSet rs=st.executeQuery("SELECT DISC FROM CDMAST WHERE TRNCD="+trncd+"");
			while(rs.next())
			{
				result=rs.getString(1);
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
			System.out.println("Error in CodeMasterHandler getCDesc(int trncd) method "+e);
		}
		return result;
	}
	
	
	public ArrayList<CodeMasterBean> gettrancd()
	{
		Connection con=null;
		String result="";
		 ArrayList<CodeMasterBean> list=new ArrayList<>();
		try
		{
			 
			//ConnectionManager CM=new ConnectionManager();
			con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
		
			ResultSet rs=st.executeQuery("SELECT  * FROM CDMAST where TRNCD not in (101,103,104,105,107,108,126,127,128,135,201,199,999,202,257,249,256) order by DISC");
			while(rs.next())
			{
				CodeMasterBean bean=new CodeMasterBean();
				
				bean.setTRNCD(Integer.parseInt(rs.getString(1)));
				bean.setDISC(rs.getString(2));
				//System.out.println("trancode"+rs.getString(1));
				//System.out.println("desc"+rs.getString(2));
				list.add(bean);
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
			System.out.println("Error in CodeMasterHandler getCDesc(int trncd) method "+e);
		}
		return list;
	}
	
	public boolean updateCdmast(CodeMasterBean CMB)
	{
		boolean flag = false;
		Connection con =null;
		try
		{
			con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			st.executeUpdate("UPDATE CDMAST SET " 
						+" DISC ='"+CMB.getDISC()+"'," 
						+" SDESC ='"+CMB.getSDESC()+"'," 
						+" PSLIPYN='"+CMB.getPSLIPYN()+"'," 
						+" TAXABLE='"+CMB.getTAXABLE()+"'," 
						+" SUBSYS='"+CMB.getSUBSYS()+"'," 
						+" AUTOPOST='"+CMB.getAUTOPOST()+"'," 
						+" ACNO="+CMB.getACNO()+", "
						+" GROSS_YN='"+CMB.getGROSS_YN()+"'"
						+",PRNTSEQNCE="+CMB.getPrintsequnce()+" "
						+" WHERE TRNCD = "+CMB.getTRNCD()
						+"");
			flag = true;
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
			System.out.println("Error in CodeMasterHandler getCDesc(int trncd) method "+e);
		}
		
		return flag;
	}
	
	public int  getCDMASTEmpType(int trancd)
	{
		int result=-1;
		Connection con = null;
		try
		{
			con=ConnectionManager.getConnection();
			Statement st=con.createStatement();
			ResultSet res = st.executeQuery("SELECT ISNULL(EMPTYPE,-1) FROM CDMAST WHERE TRNCD=" +trancd);
			if(res.next()){
				result= res.getString(1)==null?-1:res.getInt(1);
			}
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
}