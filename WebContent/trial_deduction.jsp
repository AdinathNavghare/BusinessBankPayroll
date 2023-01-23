<%@page import="java.text.SimpleDateFormat"%>
<%@page import="payroll.DAO.BranchDAO"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%-- <%@page import="org.apache.jasper.tagplugins.jstl.core.Catch"%> --%>
<%@page import="payroll.DAO.ConnectionManager"%>
<%@page import="java.sql.*"%>
<%@page import="payroll.Core.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DATA CONVERSION</title>
 <script type="text/javascript">
    $(function () {
        $(document).keydown(function (e) {
            return (e.which || e.keyCode) != 116;
        });
    });
</script>
</head>
<body>
<%


 // for  LOAN DEDUCTION MASTER to DEDMAST 


try {
	System.out.println("i am in LOAN DEDUCTION ");
		 int count=1;
			Connection con=ConnectionManager.getConnection();
			Statement st1=con.createStatement();
			Statement st2=con.createStatement();
			Statement st3=con.createStatement();
			
			
			String table =" [NAMCO_DBF].[dbo].[SAL1504] ";
			String dt="30-Apr-2015";
			String mnth="Apr-2015";
			String empcode="";
			String empno="";
			String trncd="";
			String col="";
/* 		 	
DEDMAST=========== 			
[EMPNO],[TRNCD],[SRNO],[AMOUNT],[SUBSYS_CD],[AC_NO],[BODSANCNO],[SANC_DATE],
[SANC_AMT],[INT_RATE],[REPAY_START],[END_DATE],[CUMUYN],[ACTYN],[TOTAL_INSTLMNTS],[ACTUAL_TOTAL_AMT] 			
================================== 
first ask what to do for  br_cd=0 in  [NAMCO_DBF].[dbo].[LOAN_MST]
second add trncd column with respect to loan types in [NAMCO_DBF].[dbo].[LOAN_MST]

----------------------------TRNCD
LNHYP-S :Hypothecation Loan 208  update NAME to HYPOTHECATION LOAN
ADVMED-S:medical			240 update name to MEDICAL loan INTEREST
LASOBL  :bank interest      248 update NAME to OTHER BANK LOAN interest
LNOTH-S :other              226
CCINST-S:CASH CREDIT		247 update NAME to CASH CREDIT interest
REHSG-S :housing			244
ADVFST-S:festival			246
ADVGS-S: GOld				253
ADVMRG-S:Mortgage			257

update [NAMCO_PAYROLL].[dbo].cdmast set DISC='HYPOTHECATION LOAN' , sdesc='HYP Loan' where TRNCD =208
update [NAMCO_PAYROLL].[dbo].cdmast set DISC='MEDICAL INST' , sdesc='MEDICAL INST' where TRNCD =209
update [NAMCO_PAYROLL].[dbo].cdmast set DISC='OTHER BANK INST' , sdesc='OTH BNK INST' where TRNCD =210 
update [NAMCO_PAYROLL].[dbo].cdmast set DISC='CASH CREDIT INST' , sdesc='CC INST' where TRNCD =227 

 */
		 ResultSet rs= st1.executeQuery("select * from  LOAN_MST order by EDATE ");
				while(rs.next())
				{
				   //System.out.println("i am in LOAN DEDUCTION 1 ");
					     if(rs.getInt("trncd")==1){trncd="208";}
					else if(rs.getInt("trncd")==2){trncd="240";}
					else if(rs.getInt("trncd")==3){trncd="248";} 
					else if(rs.getInt("trncd")==4){trncd="226";}
					else if(rs.getInt("trncd")==5){trncd="247";}
					else if(rs.getInt("trncd")==6){trncd="244";}					
					else if(rs.getInt("trncd")==7){trncd="246";}
					else if(rs.getInt("trncd")==8){trncd="253";}
					else if(rs.getInt("trncd")==9){trncd="257";}
					
				ResultSet rs1=st3.executeQuery("select empcode,empno from empmast where empcode LIKE '"+rs.getString("EMP_CD")+"'");	
				while(rs1.next())
					{
					 empno=rs1.getString("empno");
					}
				
				if(empno.equalsIgnoreCase(""))
				{}
				else
				{
					System.out.println("i am in LOAN DEDUCTION 2");
				String edate= rs.getString("EDATE1")==null?"31-Dec-2099":rs.getString("EDATE1");
				System.out.println("i am in LOAN DEDUCTION 2  "+edate);
				
				System.out.println("insert into DEDMAST  ([EMPNO],[TRNCD],[SRNO],[AMOUNT],[SUBSYS_CD],[AC_NO],[BODSANCNO],[SANC_DATE],"+
						"[SANC_AMT],[INT_RATE],[REPAY_START],[END_DATE],[CUMUYN],[ACTYN],[TOTAL_INSTLMNTS],[ACTUAL_TOTAL_AMT],[TYPE],[DESIG],[PRJ_SRNO])"+
						" VALUES ("+empno+
								","+trncd+",((select isnull((select max(SRNO) from dedmast),0)+1 )),"+
								""+rs.getString("LINST")+",'"+rs.getString("LTYPE")+"',"+rs.getString("L_ACCNO")+","+
								"(select isnull((select max(SRNO) from dedmast),0)+1),"+
								"'"+edate+"',"+
								""+rs.getString("LINST")+","+
								"0,"+
								"'"+edate+"',"+
								"'"+edate+"',"+
								"'Y','Y',0,0,"+
								""+trncd+","+
								""+rs.getString("DESG_CD")+","+
								""+rs.getString("BR_CD")+" )");
				
				
				st2.execute("insert into DEDMAST  ([EMPNO],[TRNCD],[SRNO],[AMOUNT],[SUBSYS_CD],[AC_NO],[BODSANCNO],[SANC_DATE],"+
							"[SANC_AMT],[INT_RATE],[REPAY_START],[END_DATE],[CUMUYN],[ACTYN],[TOTAL_INSTLMNTS],[ACTUAL_TOTAL_AMT],[TYPE],[DESIG],[PRJ_SRNO])"+
							" VALUES ("+empno+
									","+trncd+",((select isnull((select max(SRNO) from dedmast),0)+1 )),"+
									""+rs.getString("LINST")+",'"+rs.getString("LTYPE")+"',"+rs.getString("L_ACCNO")+","+
									"(select isnull((select max(SRNO) from dedmast),0)+1),"+
									"'"+edate+"',"+
									""+rs.getString("LINST")+","+
									"0,"+
									"'"+edate+"',"+
									"'"+edate+"',"+
									"'Y','Y',0,0,"+
									""+trncd+","+
									""+rs.getString("DESG_CD")+","+
									""+rs.getString("BR_CD")+" )");	
				
				System.out.println("i am in LOAN DEDUCTION 3");
				System.out.println("count ==== :  "+count++);		
/*   
EMP_CD	EMP_NAME	DESG_CD	BR_CD	LTYPE	L_ACCNO	LINST	LSTAT	AUTH_FLG	USER	EDATE	trncd


EMP_CD	EMP_NAME	TYPE	DESG_CD	BR_CD	PSR_NO	POL_NO	POL_AMT	POL_STAT	USER	EDATE
  */
System.out.println (empno+"......completed ");
				}	
				}
				System.out.println ("ALL EMP......completed ");
}
catch(Exception e)
{
	   e.printStackTrace();
	   ErrorLog el=new ErrorLog();
		el.errorLog("LOAN DEDMAST trial.jsp",e.toString());
}  



  
%>

</body>
</html>