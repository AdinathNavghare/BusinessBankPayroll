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

//for converting leave table into LEAVETRAN

try {
	  
	 /* 
	    LEAVE TYPE 
	  3=CL
	  4=COFF_L
	  5=ENCSH_L
	  6=LATE_L
	  7=LWP
	  8=MAT_L
	 1= PL
	 2=SL
	  9=TRF_L  ; */
	  
	
	 int count=1;
		Connection con=ConnectionManager.getConnection();
		Statement st1=con.createStatement();
		Statement st2=con.createStatement();
		Statement st3=con.createStatement();
		ResultSet rs= st1.executeQuery("Select * from LEV_DTL_24_05 where"+
				" lev_type='SUSPE_L'   AND APP_DATE "+
				"between '1-dec-2016' and '31-may-2017'"+
				"order by EMP_CODE");
		
		/* System.out.println("Select * from LEV_DTL_March2017 where"+
				" 	lev_type='CL'   AND APP_DATE1 "+
				"between '1-jan-2017' and '31-mar-2017' and"+
				" from_dt1 <='31-mar-2017' order by EMP_CODE"); 
		 */
			while(rs.next())
			{
				int LEAVECD =6;
				   LEAVECD =rs.getString("LEV_TYPE").equalsIgnoreCase("PL")?1:rs.getString("LEV_TYPE").equalsIgnoreCase("SL")?2:rs.getString("LEV_TYPE").equalsIgnoreCase("CL")?3:
				rs.getString("LEV_TYPE").equalsIgnoreCase("covid")?4:rs.getString("LEV_TYPE").equalsIgnoreCase("ENCSH_L")?5:rs.getString("LEV_TYPE").equalsIgnoreCase("LATE_L")?6:
					rs.getString("LEV_TYPE").equalsIgnoreCase("LWP")?7:rs.getString("LEV_TYPE").equalsIgnoreCase("MAT_L")?8:rs.getString("LEV_TYPE").equalsIgnoreCase("TRF_L")?9:10	;// ,[EMPNO],[TRNDATE],[TRNTYPE],[APPLNO],[LREASON],[APPLDT],[FRMDT],[TODT],[SANCAUTH],[STATUS],[DAYS]	 
			
				String empno="";
		ResultSet r=st3.executeQuery("select empno from empmast where empcode like '"+rs.getString("EMP_CODE")+"'");	
		System.out.println("select empno from empmast where empcode like '"+rs.getString("EMP_CODE")+"'");
	
		System.out.println("TRAIL_DISHA");
		while(r.next())
		{
			empno=r.getString("EMPNO");
		}

		  /* st2.execute(" insert into  leavetran_temp " +
		"([LEAVECD],[EMPNO],[TRNDATE],[TRNTYPE],[APPLNO],[LREASON],[APPLDT],[FRMDT],[TODT],[STATUS],[DAYS])"+
		" VALUES("+LEAVECD+","+empno+",'"+rs.getString("APP_DATE")+"','D',(select isnull((select max(applno)+1 from leavetran_temp),1) )"+
		" ,'"+rs.getString("REASON")+"', "+
		//" ,'PL CONVERTED TO LWP', "+
		//" ,'LATE MARK CONVERTED TO PL', "+
		"'"+rs.getString("SAN_DATE")+"','"+rs.getString("FROM_DT")+"','"+rs.getString("TO_DT")+"','"+(rs.getString("SANCTION").equalsIgnoreCase("Y")?"SANCTION":"1")+"',"+rs.getString("LDAYS")+")");		 */
			
		
		st2.execute(" insert into  leavetran_ajit " +
				"([LEAVECD],[EMPNO],[TRNDATE],[TRNTYPE],[APPLNO],[LREASON],[APPLDT],[FRMDT],[TODT],[STATUS],[DAYS])"+
				" VALUES("+LEAVECD+","+empno+",'"+rs.getString("APP_DATE")+"','D',(select isnull((select max(applno)+1 from leavetran_ajit),1) )"+
				" ,'"+rs.getString("REASON")+"', "+
				//" ,'PL CONVERTED TO LWP', "+
				//" ,'LATE MARK CONVERTED TO PL', "+
				"'"+rs.getString("SAN_DATE")+"','"+rs.getString("FROM_DT")+"','"+rs.getString("TO_DT")+"','"+(rs.getString("SANCTION").equalsIgnoreCase("Y")?"SANCTION":"1")+"',"+rs.getString("LDAYS")+")");		
		
			System.out.println("count :  "+count++);  
			
			}	
			
}
catch(Exception e)
{
	  e.printStackTrace();
	  ErrorLog el=new ErrorLog();
		el.errorLog("LEAVE INTO LEAVETRAN trial.jsp",e.toString());
} 



  
%>

</body>
</html>