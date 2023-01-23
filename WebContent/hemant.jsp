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
<title>Hemant</title>

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
 

//for insert into EMP_MAST to EMPMAST
  try {
	Connection con=ConnectionManager.getConnection();
	Statement st1=con.createStatement();
	Statement st2=con.createStatement();
	Statement st3=con.createStatement();
	ResultSet rs=null;
	ResultSet rs1=null;
	int employee=0;
	int leaveCode=0;
	int days=0;
	String applicationDate="";
	int count=1;
/* 	rs1=st3.executeQuery("select * from leavetran "+
			//"WHERE EMPNO=55 AND LEAVECD=5 order by empno");
			"where LEAVECD not in (4,8,9) order by frmdt,applno "); */
			
 	rs1=st3.executeQuery("select * from leavetran where APPLNO > 16846 "+
 		/* 	FRMDT>'31-dec-2015' "+ */
	//" LREASON!='Leave Encash' AND
	" and LEAVECD not in (4,8,9)"+
			" and TRNTYPE='D'  order by trndate");		
			
    
			 
	while (rs1.next()) {
		employee=rs1.getInt("empno");
		leaveCode=rs1.getInt("leavecd");
		days=rs1.getInt("days");
		applicationDate=rs1.getString("trndate");
		//System.out.println(employee+"  "+leaveCode+"  "+days+" "+applicationDate);
		

		
	String sqlQuery="insert into  leavebal " +
				"([BALDT],[EMPNO],[LEAVECD],[BAL],[TOTCR],[TOTDR])"+
				" VALUES('"+applicationDate+"',"+employee+","+leaveCode+","+
						"(select max(bal)-"+days+" from "+
						"leavebal"+ 
						" where empno="+employee+" and leavecd="+leaveCode+" and srno= "+
						   "(select MAX(srno) from leavebal where empno="+employee+" and LEAVECD="+leaveCode+" )) "+
						",(select max(totcr) from leavebal"+
						" where empno="+employee+" and leavecd="+leaveCode+" and srno= "+
								   "(select MAX(srno) from leavebal where empno="+employee+" and LEAVECD="+leaveCode+" ))"+
						",(select max(totdr)+"+days+""+
						" from leavebal "+
						" where empno="+employee+" and leavecd="+leaveCode+" and srno= "+
		 "(select MAX(srno) from leavebal where empno="+employee+" and LEAVECD="+leaveCode+" ))  )";
	
	System.out.println(applicationDate+"   "+count++);
	//System.out.println(sqlQuery);
	st1.executeUpdate(sqlQuery);
	}
	
  } catch (SQLException e) {
		
		e.printStackTrace();
		ErrorLog el=new ErrorLog();
		el.errorLog("EMP_MAST to EMPMAST trial.jsp",e.toString());
	}
%>
</body>
</html>