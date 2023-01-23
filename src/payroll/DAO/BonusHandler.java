package payroll.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import payroll.Core.ReportDAO;
import payroll.Model.BonusBean;
import payroll.Model.TranBean;

public class BonusHandler extends Thread {
	
	public static String emp[];  //Global variable;
	public static int mod =0;   //Global variable;
	
	private  String empList;
	private String date;
	private String user ;
	private int percent;
	
	public ArrayList<TranBean> getBonusList(){
		
		ArrayList<TranBean> list= new ArrayList<TranBean>();
		Connection con = null;
		ResultSet rs = null;
		
		
		String query = "Select * from  bonustran";
		
		con = ConnectionManager.getConnection();
		try
		{
			Statement st = con.createStatement();
			rs = st.executeQuery(query);
			while(rs.next())
			{
			 TranBean tb = new TranBean();
			 
			 tb.setTRNDT(rs.getString("TRNDT"));
			 tb.setEMPNO(rs.getInt("EMPNO"));
			 tb.setNET_AMT(rs.getFloat("NET_AMT"));
			 tb.setUSRCODE(rs.getString("USRCODE"));
			 tb.setUPDDT(rs.getString("UPDDT"));
			
			 list.add(tb);
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

	// To post bonus into bonustran.....
	public  boolean applyBonus(BonusBean bonusBean, int sessionEmployee,int date) throws SQLException{   	 
	    	boolean result = false;
	    	Connection cn = ConnectionManager.getConnection();
	    	int count=1;
		    try
		    {	
			//  (Basic+DA+VDA-LWP) * 12
		
			
				Statement   statement=cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
          String trndt="30-sep-"+date;
		/*		String getBonusQuery="select ((select SUM (net_amt) as bonusValue from PAYTRAN_STAGE " +
						" where empno = 736 and trndt between '01-apr-2015' and '31-Mar-2016' "+
          "And trncd in (101,102,138))- (select SUM (net_amt) from PAYTRAN_STAGE " +
          " where empno = 736   and trndt between '01-apr-2015' and '31-Mar-2016' "+
    "And trncd=243)) * 20/100";
				
				*/    
          String qury= "update bonuscal set status ='A' where empno="+bonusBean.getEmpno()+" and year ='"+(date-1)+"-"+date+"' ";
          System.out.println(qury);
          statement.execute(qury);	
		String query =	" IF EXISTS(Select * from Bonustran where empno = "+bonusBean.getEmpno()+" and trncd = 135 AND  STATUS = 'P'  )  " +
						" UPDATE Bonustran set trndt ='"+trndt+"', INP_AMT = "+bonusBean.getBonus()+" " +
					 	" where empno = "+bonusBean.getEmpno()+" " +
					 			//" and trndt='"+trndt+"'" +
					 					" else " +
					 	" insert into bonustran (TRNDT,	EMPNO,	TRNCD	,SRNO,	INP_AMT	,CAL_AMT ,NET_AMT,	CF_SW	,USRCODE,	UPDDT,	STATUS) " +
					 	" values ('"+trndt+"',"+bonusBean.getEmpno()+",135,0,"+bonusBean.getBonus()+" "+
						" ,"+bonusBean.getBonus()+","+bonusBean.getBonus()+",0,"+user+",getdate(),'P' ) ";
					
				//System.out.println(count++);
				statement.execute(query);						 	 
			
		    	result = true;
		    }catch(Exception e){
		    	e.printStackTrace();
		    	cn.close();
		    }
	    	
	    	 return result; 
	     }
	     
	public static boolean postBonus(String emp[], String date, String user ) throws SQLException{   	 
    	boolean result = false;
    	Connection cn = ConnectionManager.getConnection();
	    try
	    {	
		//  (Basic+DA+VDA-LWP) * 12
		for (int y=0;y<emp.length;y++)
		{
			String employee = emp[y];
			Statement   statement=cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            //System.out.println(" update bonustran set status='F' where empno = "+employee+"  and trndt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' And trncd = 135 ");;
			String query =	" update bonustran set status='F' where empno = "+employee+"  and trndt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' And trncd = 135 ";
				
			
			statement.execute(query);	
			//System.out.println("update sal_details set sal_status='PROCESSED' where empno="+employee+" and sal_month = '"+ReportDAO.BOM(date).substring(3,11)+"' ");
			String sql="update sal_details set sal_status='PROCESSED' where empno="+employee+" and sal_month = '"+ReportDAO.BOM(date).substring(3,11)+"' ";
			 
			//System.out.println("Inside bonus servlet 2"+sql);
			statement.execute(sql);
		//}
		
		Statement st = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		//System.out.println("insert into paytran Select * from bonustran where empno="+employee+"  and status= 'F'  and trndt  between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"'   ");
		st.execute("insert into paytran Select * from bonustran where empno="+employee+"  and  status= 'F'  and trndt  between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"'   ");
		//System.out.println(" delete from BONUSTRAN  where empno="+employee+"  and STATUS='F'  ");
		st.execute(" delete from BONUSTRAN  where empno="+employee+"  and STATUS='F'  ");
		
		//System.out.println("data "+date);
		//System.out.println("update bonuscal set status ='P' where status='A' and  empno="+employee+" and year= (select SUBSTRING ( CONVERT(VARCHAR(10), DATEADD(year, -1, '"+ReportDAO.BOM(date)+"'), 103),7,14)+'-'+substring('"+ReportDAO.BOM(date)+"',8,14))");
		st.execute("update bonuscal set status ='P' where empno="+employee+" and year= (select SUBSTRING ( CONVERT(VARCHAR(10), DATEADD(year, -1, '"+ReportDAO.BOM(date)+"'), 103),7,14)+'-'+substring('"+ReportDAO.BOM(date)+"',8,14))");
		
		}
	    result = true;
	    
	    }catch(Exception e){
	    	e.printStackTrace();
	    	cn.close();
	    }
    	 return result; 
     }
     
	
    // This Method is Created for Thread Demo....
    /*  public static boolean postBonus(String empList, int percent, String date, String user ){   	 
    	boolean result = false;
	    try
	    {	Connection cn = ConnectionManager.getConnection();
	    	emp = empList.split(",");
	    	int empno = emp.length;
	    	int factor = 0;
	    	mod = 0;
	    	
	    	factor = empno / 60;
	    	mod = empno % 60;
	    	
	    	System.out.println(" factor :" + factor);
	    	System.out.println(" mod :" + mod);
	    	
	    	for(int i = 0; i < factor ; i++)
	    	{ 
				 new BonusHandler(empList, percent, date, user ){
				 }.start();		 
				 System.out.println(" Thread :" + i);
	    	}
	    	
	    	for(int y=0; y< mod; y++)
	    	{     		
	    		String employee = emp[y];
	    		// calling query for number of employee(mod), less than 9
	    		System.out.println(" In Mod loop "+employee);
	    	
				Statement   statement=cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

				String query =	" insert into bonustran (TRNDT,	EMPNO,	TRNCD	,SRNO,	INP_AMT	,	NET_AMT,	CF_SW	,USRCODE,	UPDDT,	STATUS) " +
					 			" values ('"+date+"',"+employee+",135,0,(select ( (sum(net_amt)* 12) * "+percent+" )/100  " +
					 			" from PAYTRAN " +
					 			" where empno = "+employee+"  and trndt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' And trncd in (101,102,138))  " +
								" ,0,0,"+user+",getdate(),'P' ) ";
					 
				//System.out.println("Inside bonus servlet 1"+query);
				statement.execute(query);						 
					
				String sql="update sal_details set sal_status='AutoInst' where empno="+employee+" and sal_month = '"+ReportDAO.BOM(date).substring(3,11)+"' ";
					 
				//System.out.println("Inside bonus servlet 2"+sql);
				statement.execute(sql);
	    		
	   	 	}
	    	
	    	result = true;
	    }catch(Exception e){e.printStackTrace();}
    	
    	 return result; 
     }
     */
	public BonusHandler(){
		
    }
	 public BonusHandler(String empList, int percent, String date, String user ){
    	 this.empList = empList;
    	 this.date = date;
    	 this.user = user;
    	 this.percent = percent;
     }
     
     @Override
	public void run(){
  		for(int i = 0; i < 60; i++){
  			
  			String employee = emp[ (i+mod) ];
  			System.out.println(" task one : "+i+" @@@@@@" +  employee );
  			
    		Connection cn = ConnectionManager.getConnection();
			Statement statement;
			try {
			statement = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String query =	" insert into bonustran (TRNDT,	EMPNO,	TRNCD	,SRNO,	INP_AMT	,	NET_AMT,	CF_SW	,USRCODE,	UPDDT,	STATUS) " +
				 			" values ('"+date+"',"+employee+",135,0,(select ( (sum(net_amt)* 12) * "+percent+" )/100  " +
				 			" from PAYTRAN " +
				 			" where empno = "+employee+"  and trndt between '"+ReportDAO.BOM(date)+"' and '"+ReportDAO.EOM(date)+"' And trncd in (101,102,138))  " +
							" ,0,0,"+user+",getdate(),'P' ) ";
				 
			//System.out.println("Inside bonus servlet 1"+query);
			statement.execute(query);						 
				
			String sql="update sal_details set sal_status='AutoInst' where empno="+employee+" and sal_month = '"+ReportDAO.BOM(date).substring(3,11)+"' ";
				 
		//	System.out.println("Inside bonus servlet 2"+sql);
			statement.execute(sql);
  			
  			
			} catch (SQLException e) { e.printStackTrace(); }
	
  		} 
  	  }
     
     public ArrayList<BonusBean> getEmployeeDetailsByCode(int year){
 		BonusBean  bean = null;
 		EmpAttendanceHandler empAttendanceHandler=new EmpAttendanceHandler();
 		String currentdate = empAttendanceHandler.getServerDate();
 		ArrayList<BonusBean> list = new ArrayList<BonusBean>();
 	
 		try
 		{
 			
 			String BOY =ReportDAO.BoFinancialy("01-Apr-"+year);
 			int nextYear=year+1;
 			String EOY =ReportDAO.EoFinancialy("31-Mar-"+nextYear);
 			System.out.println(BOY);
 			System.out.println(EOY);
 			Connection connection = ConnectionManager.getConnection();
 			connection= ConnectionManager.getConnection();
 			Statement statement=connection.createStatement();
 			ResultSet resultSet = null;
 					
 			
 			
 		/*	String qurey = "select e.empno ,e.empcode,rtrim(e.fname)+' '+rtrim(e.mname)+' '+rtrim(e.lname) as name,"+        
 				" (select SUM (p.net_amt ) from PAYTRAN_STAGE p  where empno = e.empno"+ 
 			    " and p.trndt between '"+BOY+"' and" +
 			    " '"+EOY+"' And p.trncd "+
 			 "   in (101,102,138))- (select SUM (p.net_amt)" +
 			 " from PAYTRAN_STAGE p "+ 
 			" where p.empno = e.empno and p.trndt between" +
 			" '"+BOY+"' and '"+EOY+"' "+
 		" And p.trncd=243) as amtForBonus,(select (select SUM (p.net_amt )" +
 		" from PAYTRAN_STAGE p  where empno = e.empno " +
 		" and p.trndt between '"+BOY+"' and '"+EOY+"' "+
 " And p.trncd in (101,102,103,138))- (select SUM (p.net_amt) from PAYTRAN_STAGE p"+ 
 	" where p.empno = e.empno  and p.trndt between '"+BOY+"' and '"+EOY+"' "+
 	" And p.trncd in (129,140,141,142,243)))*("+percent+")/100 as bonus  from empmast e      where  " +
 	" e.EMPNO in(select distinct empno from PAYTRAN_STAGE where TRNDT between '"+ReportDAO.EOM(BOY)+"' " +
 	"and '"+EOY+"') and e.EMPNO in " +
 			"(select EMPNO from BONUSTRAN where TRNDT='30-sep-"+year+"' and STATUS='P')" +
 			" group by e.empno ," +
 	"e.empcode,e.fname,e.mname,e.lname	order by e.empcode";*/


 			/*String qurey = "select e.empno ,e.empcode,rtrim(e.fname)+' '+rtrim(e.mname)+' '+rtrim(e.lname) as name,"+        
 	 				" (select SUM (p.net_amt ) from PAYTRAN_STAGE p  where empno = e.empno"+ 
 	 			    " and p.trndt between '"+BOY+"' and" +
 	 			    " '"+EOY+"' And p.trncd "+
 	 			 "   in (101,102,138))- (select SUM (p.net_amt)" +
 	 			 " from PAYTRAN_STAGE p "+ 
 	 			" where p.empno = e.empno and p.trndt between" +
 	 			" '"+BOY+"' and '"+EOY+"' "+
 	 		" And p.trncd=243) as amtForBonus,(select (select SUM (p.net_amt )" +
 	 		" from PAYTRAN_STAGE p  where empno = e.empno " +
 	 		" and p.trndt between '"+BOY+"' and '"+EOY+"' "+
 	 " And p.trncd in (101,102,103,138))- (select SUM (p.net_amt) from PAYTRAN_STAGE p"+ 
 	 	" where p.empno = e.empno  and p.trndt between '"+BOY+"' and '"+EOY+"' "+
 	 	" And p.trncd in (129,140,141,142,243)))*("+percent+")/100 as bonus  from empmast e      where  " +
 	 	" e.EMPNO in(select distinct empno from PAYTRAN_STAGE where TRNDT between '"+ReportDAO.EOM(BOY)+"' " +
 	 	"and '"+EOY+"')  group by e.empno ," +
 	 	"e.empcode,e.fname,e.mname,e.lname	order by e.empcode";*/	
 			
 			String qurey = "select e.empno ,e.empcode," +
 					"rtrim(e.fname)+' '+rtrim(e.mname)+' '+rtrim(e.lname) as name," +
 					"b.amtforbonus ,b.bonus "+        
 	 "from empmast e,bonuscal b where b.empno=e.empno and b.status='N' and b.year ='"+year+"-"+nextYear+"' order by e.empcode";
 			
 			System.out.println(qurey);
 			resultSet = statement.executeQuery(qurey);
 			
 			while(resultSet.next())
 			{	
 				bean = new BonusBean();
 				
 				 bean.setEmpno(resultSet.getInt("empno"));
 				 bean.setEmpCode(resultSet.getString("empcode"));
 				 bean.setEmpName(resultSet.getString("name"));
 				 bean.setAmtForBonus(resultSet.getFloat("amtForBonus"));
 				 bean.setBonus(resultSet.getFloat("bonus"));
 				
 				 
 				 list.add(bean);
 	   		}
 			
 			
 			connection.close();

 		}
 		catch(Exception e){e.printStackTrace();}
 		
 		return list;
 	}
     
     public ArrayList<BonusBean> getEmployeeDetailsFromBonusCal(int year,float percent){
  		BonusBean  bean = null;
  	//	EmpAttendanceHandler empAttendanceHandler=new EmpAttendanceHandler();
  	//	String currentdate = empAttendanceHandler.getServerDate();
  		ArrayList<BonusBean> list = new ArrayList<BonusBean>();
  	
  		try
  		{
  			
  			String BOY =ReportDAO.BoFinancialy("01-Apr-"+year);
  			int nextYear=year+1;
  			String EOY =ReportDAO.EoFinancialy("31-Mar-"+nextYear);
  			System.out.println(BOY);
  			System.out.println(EOY);
  			Connection connection = ConnectionManager.getConnection();
  			connection= ConnectionManager.getConnection();
  			Statement statement=connection.createStatement();
  			ResultSet resultSet = null;
  			ResultSet rs1 = null;
			Statement st1 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);		
	
			String SQL = "";
  			
  			String QueryBonus ="with bonus as(select e.empno, "+
  					 "sum(case datepart(MM,trndt) when 04 then NET_AMT else  0 end) apr, "+
  					 "sum(case datepart(MM,trndt) when 05 then NET_AMT else  0 end) may,"+
  					 "sum(case datepart(MM,trndt) when 06 then NET_AMT else  0 end) jun, "+
  					 "sum(case datepart(MM,trndt) when 07 then NET_AMT else  0 end) jul,"+
  					 "sum(case datepart(MM,trndt) when 08 then NET_AMT else  0 end) aug,"+
  					 "sum(case datepart(MM,trndt) when 09 then NET_AMT else  0 end) sep,"+
  					 "sum(case datepart(MM,trndt) when 10 then NET_AMT else  0 end) oct,"+ 
  					 "sum(case datepart(MM,trndt) when 11 then NET_AMT else  0 end) nov,"+
  					 "sum(case datepart(MM,trndt) when 12 then NET_AMT else  0 end) dec,"+
  					 "sum(case datepart(MM,trndt) when 01 then NET_AMT else  0 end) jan,"+
  					 "sum(case datepart(MM,trndt) when 02 then NET_AMT else  0 end) feb,"+
  					 "sum(case datepart(MM,trndt) when 03 then NET_AMT else  0 end) mar"+
  					 " from empmast e, cdmast c, paytran_stage y where e.empno = y.empno "+
  					 " and y.trncd = c.trncd  and y.trndt between '"+BOY+"' and '"+EOY+"' " +
  					 " and y.trncd in(101,102,138,147,142) group by e.empno) , "+
  					" lwp as (select e.empno, "+
  					 "sum(case datepart(MM,trndt) when 04 then NET_AMT else  0 end) aprlwp,"+
  					 "sum(case datepart(MM,trndt) when 05 then NET_AMT else  0 end) maylwp,"+
  					 "sum(case datepart(MM,trndt) when 06 then NET_AMT else  0 end) junlwp, "+
  					 "sum(case datepart(MM,trndt) when 07 then NET_AMT else  0 end) jullwp,"+
  					 "sum(case datepart(MM,trndt) when 08 then NET_AMT else  0 end) auglwp,"+
  					 "sum(case datepart(MM,trndt) when 09 then NET_AMT else  0 end) seplwp,"+
  					 "sum(case datepart(MM,trndt) when 10 then NET_AMT else  0 end) octlwp, "+
  					 "sum(case datepart(MM,trndt) when 11 then NET_AMT else  0 end) novlwp,"+
  					 "sum(case datepart(MM,trndt) when 12 then NET_AMT else  0 end) declwp,"+
  					 "sum(case datepart(MM,trndt) when 01 then NET_AMT else  0 end) janlwp,"+
  					 "sum(case datepart(MM,trndt) when 02 then NET_AMT else  0 end) feblwp,"+
  					 "sum(case datepart(MM,trndt) when 03 then NET_AMT else  0 end) marlwp"+
  					 " from empmast e, cdmast c, paytran_stage y where e.empno = y.empno "+
  					 " and y.trncd = c.trncd  and y.trndt between '"+BOY+"'" +
  					 		" and '"+EOY+"' and y.trncd in (243) group by e.empno )  "+
  					  " select b.empno,rtrim(e.fname)+' '+rtrim(e.lname) name,t.prj_srno as site_id , "+
  					   "E.DOJ,E.empcode, (b.apr-l.aprlwp) as apramt , (b.may-l.maylwp) as mayamt, "+
  					   "(b.jun-l.junlwp) as junamt,  (b.jul-l.jullwp) as julamt," +
  					   " (b.aug-l.auglwp) as augamt, (b.sep-l.seplwp) as sepamt , (b.oct-l.octlwp) "+
  					  " as octamt , (b.nov-l.novlwp) as novamt , (b.dec-l.declwp) as decamt , " +
  					  " (b.jan-l.janlwp) as janamt, (b.feb-l.feblwp) as febamt, (b.mar-l.marlwp)" +
  					  " as maramt, " +
  					  " (b.apr+b.may+b.jun+b.jul+b.aug+b.sep+b.oct+b.nov+b.dec+b.jan+b.feb+b.mar "+
  					   " -l.aprlwp-l.maylwp-l.junlwp-l.jullwp-l.auglwp-l.seplwp- "+
  					   " l.octlwp-l.novlwp-l.declwp-l.janlwp-l.feblwp-l.marlwp) as amtForBonus, "+
  					     "(((b.apr+b.may+b.jun+b.jul+b.aug+b.sep+b.oct+b.nov+b.dec+b.jan+b.feb+b.mar "+
  					    " -l.aprlwp-l.maylwp-l.junlwp-l.jullwp-l.auglwp-l.seplwp- "+
  					    "l.octlwp-l.novlwp-l.declwp-l.janlwp-l.feblwp-l.marlwp))/100*"+percent+") "+
  					    " as bonusamt from bonus  b ,EMPMAST E ,lwp l,emptran t where b.EMPNO=E.empno " +
  					  " and E.EMPNO not in (select EMPNO from bonuscal where STATUS in ('P','A','N') and " + 
  					  " year=(select substring('"+BOY+"',8,14) +'-'+ substring('"+EOY+"',8,14))) " +
  					 " and E.EMPNO=t.EMPNO and  T.SRNO = (SELECT MAX(E2.SRNO)" +
  					    " FROM EMPTRAN E2 WHERE E2.EMPNO =" +
  					    " E.EMPNO AND E2.EFFDATE <= '"+EOY+"')" +
  					    "  and l.empno=E.empno order by E.empcode  "; 
  			
  			String q2=" with bonus as(select e.empno, sum(case datepart(MM,trndt) when 04 then NET_AMT else  0 end) apr, sum(case datepart(MM,trndt) when 05 then NET_AMT else  0 end) may,sum(case datepart(MM,trndt) when 06 then NET_AMT else  0 end) jun, sum(case datepart(MM,trndt) when 07 then NET_AMT else  0 end) jul,sum(case datepart(MM,trndt) when 08 then NET_AMT else  0 end) aug,sum(case datepart(MM,trndt) when 09 then NET_AMT else  0 end) sep,sum(case datepart(MM,trndt) when 10 then NET_AMT else  0 end) oct,sum(case datepart(MM,trndt) when 11 then NET_AMT else  0 end) nov,sum(case datepart(MM,trndt) when 12 then NET_AMT else  0 end) dec,sum(case datepart(MM,trndt) when 01 then NET_AMT else  0 end) jan,sum(case datepart(MM,trndt) when 02 then NET_AMT else  0 end) feb,sum(case datepart(MM,trndt) when 03 then NET_AMT else  0 end) mar from empmast e, cdmast c, paytran_stage y where e.empno = y.empno  and y.trncd = c.trncd  and y.trndt between '01-APR-2020' and '31-MAR-2021'  and y.trncd in(101,102,138,147,142) group by e.empno)  select b.empno,rtrim(e.fname)+' '+rtrim(e.lname) name,t.prj_srno as site_id , E.DOJ,E.empcode, (b.apr) as apramt , (b.may) as mayamt, (b.jun) as junamt,  (b.jul) as julamt, (b.aug) as augamt, (b.sep) as sepamt , (b.oct)  as octamt , (b.nov) as novamt , (b.dec) as decamt ,  (b.jan) as janamt, (b.feb) as febamt, (b.mar) as maramt,  (b.apr+b.may+b.jun+b.jul+b.aug+b.sep+b.oct+b.nov+b.dec+b.jan+b.feb+b.mar) as amtForBonus, (((b.apr+b.may+b.jun+b.jul+b.aug+b.sep+b.oct+b.nov+b.dec+b.jan+b.feb+b.mar ))/100*12.0)  as bonusamt from bonus  b ,EMPMAST E ,emptran t where b.EMPNO=E.empno  and E.EMPNO not in (select EMPNO from bonuscal where STATUS in ('P','A','N') and  year=(select substring('01-APR-2020',8,14) +'-'+ substring('31-MAR-2021',8,14)))  and E.EMPNO=t.EMPNO and  T.SRNO = (SELECT MAX(E2.SRNO) FROM EMPTRAN E2 WHERE E2.EMPNO = E.EMPNO AND E2.EFFDATE <= '31-MAR-2021')  order by E.empcode  ";
  			String q3="with bonus as(select e.empno, sum(case datepart(MM,trndt) when 04 then NET_AMT else  0 end) apr, sum(case datepart(MM,trndt) when 05 then NET_AMT else  0 end) may,sum(case datepart(MM,trndt) when 06 then NET_AMT else  0 end) jun, sum(case datepart(MM,trndt) when 07 then NET_AMT else  0 end) jul,sum(case datepart(MM,trndt) when 08 then NET_AMT else  0 end) aug,sum(case datepart(MM,trndt) when 09 then NET_AMT else  0 end) sep,sum(case datepart(MM,trndt) when 10 then NET_AMT else  0 end) oct,sum(case datepart(MM,trndt) when 11 then NET_AMT else  0 end) nov,sum(case datepart(MM,trndt) when 12 then NET_AMT else  0 end) dec,sum(case datepart(MM,trndt) when 01 then NET_AMT else  0 end) jan,sum(case datepart(MM,trndt) when 02 then NET_AMT else  0 end) feb,sum(case datepart(MM,trndt) when 03 then NET_AMT else  0 end) mar from empmast e, cdmast c, paytran_stage y where e.empno = y.empno  and y.trncd = c.trncd  and y.trndt between '01-APR-2020' and '31-MAR-2021'  and y.trncd in(101,102,138,147,142) group by e.empno)  select b.empno,rtrim(e.fname)+' '+rtrim(e.lname) name,t.prj_srno as site_id , E.DOJ,E.empcode, (b.apr) as apramt , (b.may) as mayamt, (b.jun) as junamt,  (b.jul) as julamt, (b.aug) as augamt, (b.sep) as sepamt , (b.oct)  as octamt , (b.nov) as novamt , (b.dec) as decamt ,  (b.jan) as janamt, (b.feb) as febamt, (b.mar) as maramt,  (b.apr) as amtForBonus, (((b.apr ))/100*12.0)  as bonusamt from bonus  b ,EMPMAST E ,emptran t where b.EMPNO=E.empno  and E.EMPNO not in (select EMPNO from bonuscal where STATUS in ('P','A','N') and  year=(select substring('01-APR-2020',8,14) +'-'+ substring('31-MAR-2021',8,14)))  and E.EMPNO=t.EMPNO and  T.SRNO = (SELECT MAX(E2.SRNO) FROM EMPTRAN E2 WHERE E2.EMPNO = E.EMPNO AND E2.EFFDATE <= '31-MAR-2021')   order by E.empcode";
  			String q4="";
  			System.out.println("q="+QueryBonus);
  			System.out.println("1");
  			resultSet = statement.executeQuery(QueryBonus);
  			float amtforbonus = 0.0f;
  			int ct=0;
  			System.out.println("before while");
  			while(resultSet.next())
  			{
  				System.out.println("ct1:"+resultSet.getInt("empno"));
  			}
  			
  			while(resultSet.next())
  			{	
  				bean = new BonusBean();
  				System.out.println("ct:"+ct);
  				ct++;
  				bean.setEmpno(resultSet.getInt("empno"));
  				bean.setEmpCode(resultSet.getString("empcode"));
  				bean.setEmpName(resultSet.getString("name"));
  			//	bean.setAmtForBonus(resultSet.getFloat("amtForBonus"));
  			//	bean.setBonus(resultSet.getFloat("bonusamt"));
  				bean.setYear(year+"-"+nextYear);
  				bean.setSite_id(resultSet.getInt("site_id"));
  				bean.setApramt(resultSet.getFloat("apramt"));
  				bean.setMayamt(resultSet.getFloat("mayamt"));
  				bean.setJunamt(resultSet.getFloat("junamt"));
  				bean.setJulamt(resultSet.getFloat("julamt"));
  				bean.setAugamt(resultSet.getFloat("augamt"));
  				bean.setSepamt(resultSet.getFloat("sepamt"));
  				bean.setOctamt(resultSet.getFloat("octamt"));
  				bean.setNovamt(resultSet.getFloat("novamt"));
  				bean.setDecamt(resultSet.getFloat("decamt"));
  				bean.setJanamt(resultSet.getFloat("janamt"));
  				bean.setFebamt(resultSet.getFloat("febamt"));
  				bean.setMaramt(resultSet.getFloat("maramt"));
  				bean.setPercent(percent);
  				bean.setStatus("P");
  				/*bean.setAmtForBonus(resultSet.getFloat("amtForBonus"));*/
  				amtforbonus = resultSet.getFloat("amtForBonus");
				
				float hraamt = 0.0f;
				float hraamt1 = 0.0f;
				String TotBonusAMT ="";
				//	279,1		
				
				if(resultSet.getString("empno").equals("279") || resultSet.getString("empno").equals("173") || resultSet.getString("empno").equals("383") || resultSet.getString("empno").equals("800")
						||resultSet.getString("empno").equals("559") || resultSet.getString("empno").equals("630") || resultSet.getString("empno").equals("376") || resultSet.getString("empno").equals("420")	
						||resultSet.getString("empno").equals("566") || resultSet.getString("empno").equals("631") || resultSet.getString("empno").equals("378") || resultSet.getString("empno").equals("421")	
						||resultSet.getString("empno").equals("586") || resultSet.getString("empno").equals("632") || resultSet.getString("empno").equals("387") || resultSet.getString("empno").equals("422")
						||resultSet.getString("empno").equals("610") || resultSet.getString("empno").equals("643") || resultSet.getString("empno").equals("407") || resultSet.getString("empno").equals("428")
						||resultSet.getString("empno").equals("611") || resultSet.getString("empno").equals("276") || resultSet.getString("empno").equals("408") || resultSet.getString("empno").equals("432")
						||resultSet.getString("empno").equals("614") || resultSet.getString("empno").equals("303") || resultSet.getString("empno").equals("409") || resultSet.getString("empno").equals("457")
						||resultSet.getString("empno").equals("627") || resultSet.getString("empno").equals("311") || resultSet.getString("empno").equals("411") || resultSet.getString("empno").equals("470")
						||resultSet.getString("empno").equals("629") || resultSet.getString("empno").equals("323") || resultSet.getString("empno").equals("413")
						)
					{
					 SQL = ""
							+ "select case when (select ISNULL( max(HRA) ,0) as HRA from Increment where EMPNO ="+resultSet.getString("empno")+" and ISPROCESED =1) =0 "
							+ "then 0 else "
							+ " (isnull((abs ( "
							+ "(select inp_amt  from PAYTRAN_STAGE  where EMPNO ="+resultSet.getString("empno")+" and  TRNCD =103 "
							+ "and TRNDT =(select  convert(date,DATEADD(day, -1, '"+BOY+"'),105))) "
							+ "- "
							+ "(select ISNULL( max(HRA) ,0) as HRA from Increment where EMPNO ="+resultSet.getString("empno")+" and ISPROCESED =1) "
							+ " "
							+ ")*2),0)) end  as amt";
					 System.out.println("if");
					}
					else{
						 System.out.println("else");
					 SQL = ""
							+ "select case when isnull( (select isnull(net_amt, 0) from paytran_stage where TRNDT ='2017-06-30' and TRNCD =142 and empno = "+resultSet.getString("empno")+" "
							+ "and (isnull(NET_AMT,0) !=0)),0) =0 "
							+ "then 0 else "
							+ " (isnull((abs ( ( ( select top 1 hra  from GRADE_MASTER where BASIC = ( "
							+ "select top 1 BASIC+increment  from grade_master where BASIC = ( select inp_amt "
							+ "	from paytran_stage "
							+ "	where empno = "+resultSet.getString("empno")+" and TRNCD = 101 and "
							+ "	TRNDT='2017-03-31'   )	)) "
							+ "- ( select inp_amt "
							+ "from paytran_stage "
							+ "where empno = "+resultSet.getString("empno")+" and TRNCD = 103 and "
							+ "TRNDT='2017-03-31'   )) "
							+ "				 "
							+ ")*2),0)) end  as amt";
					}
					
					System.out.println("sql:"+SQL);
					rs1 = st1.executeQuery(SQL);
					if(rs1.next())
					{
						hraamt = rs1.getFloat("amt");
					}
					if(resultSet.getString("empno").equals("816")){
						hraamt = 0;
					}
					hraamt1 = amtforbonus - hraamt;
					bean.setAmtForBonus(Math.round(hraamt1));
					hraamt1 =(hraamt1/100)*(percent);
		//		System.out.println("hraamt1:  "+hraamt1);
  				bean.setBonus(Math.round(hraamt1));
  				list.add(bean);
  	   		}
  			
  			
  			connection.close();

  		}
  		catch(Exception e){e.printStackTrace();}
  		
  		return list;
  	}
      
     public  boolean addToBonusCal(BonusBean bonusBean, int sessionEmployee,int year) throws SQLException{   	 
	    	boolean result = false;
	    	Connection cn = ConnectionManager.getConnection();
	    	int count=1;
		    try
		    {	
			//  (Basic+DA+VDA-LWP) * 12
				Statement   statement=cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				String query ="insert into bonuscal (empno,year,apramt,mayamt,junamt, "+
           "julamt,augamt,sepamt,octamt,novamt,decamt,janamt,febamt,maramt,bonpercent"+
           ",[amtforbonus],[bonus],[status]) VALUES" +
		" ( "+bonusBean.getEmpno()+",'"+(year-1)+"-"+(year)+"',	" +
		""+bonusBean.getApramt()+","+bonusBean.getMayamt()+","+bonusBean.getJunamt()+","+
		""+bonusBean.getJulamt()+","+bonusBean.getAugamt()+","+bonusBean.getSepamt()+","+
		""+bonusBean.getOctamt()+","+bonusBean.getNovamt()+","+bonusBean.getDecamt()+","+
		""+bonusBean.getJanamt()+","+bonusBean.getFebamt()+","+bonusBean.getMaramt()+","+
		""+bonusBean.getPercent()+"," +
		""+bonusBean.getAmtForBonus()+","+bonusBean.getBonus()+",'N')";
					
				//System.out.println(count++);
				statement.execute(query);						 	 
			
		    	result = true;
		    }catch(Exception e){
		    	e.printStackTrace();
		    	cn.close();
		    }
	    	
	    	 return result; 
	     }
	     

}
