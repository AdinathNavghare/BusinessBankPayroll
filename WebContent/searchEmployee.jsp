<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />

<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript">

<%
EmployeeHandler eh = new EmployeeHandler();
EmployeeBean ebean = new EmployeeBean();
ebean = eh.getMaxEmployeeInformation();
%>

	function TakeCustId() {
		var EMPNO = document.getElementById("EMPNO").value;
         
		if (document.getElementById("EMPNO").value == "") {
			alert("Please Insert Employee Name");
			document.getElementById("EMPNO").focus();
			return false;
		}
		var atpos=EMPNO.indexOf(":");
		if (atpos<1)
		  {
		  alert("Please Select Correct Employee Name");
		  return false;
		  }
		}
	
	function focus(){
		
		document.getElementById("EMPNO").focus();
		
	}
</script>
<script>
	jQuery(function() {
          $("#EMPNO").autocomplete("searchList.jsp");
	});
</script>
<%
String pageName = "searchEmployee.jsp";
try
{
	ArrayList<String> urls = (ArrayList<String>)session.getAttribute("urls");
	if(!urls.contains(pageName))
	{
		response.sendRedirect("NotAvailable.jsp");
	}
}
catch(Exception e)
{
	//response.sendRedirect("login.jsp?action=0");
}

%>

<%

Date date = new Date();
	SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
	String dt = format.format(date);
	
	SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd");
	fromformat.setLenient(false);%>
</head>
<body onload="focus()" > 
<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Employee</h1>
	</div>
	<!-- end page-heading -->

	<table border="0" width="100%" cellpadding="0" cellspacing="0" id="content-table">
	<tr>
		<th rowspan="3" class="sized"><img src="images/shared/side_shadowleft.jpg" width="20" height="300" alt="" /></th>
		<th class="topleft"></th>
		<td id="tbl-border-top">&nbsp;</td>
		<th class="topright"></th>
		<th rowspan="3" class="sized"><img src="images/shared/side_shadowright.jpg" width="20" height="300" alt="" /></th>
	</tr>
	<tr>
		<td id="tbl-border-left"></td>
		<td>
		<!--  start content-table-inner ...................................................................... START -->
		<div id="content-table-inner">
		
			<!--  start table-content  -->
			<div id="table-content">
			<form  action="EmployeeServlet?action=emp" method="Post" onSubmit="return TakeCustId()">
			<table>
				<tr><td>	<font size="3"><b>Enter Employee Name or Emp-Id </b></font>&nbsp;&nbsp;
						<input class="form-control" type="text" name="EMPNO" size="41" id="EMPNO"  onclick="showHide()"
 placeholder="Enter Employee Name or Emp-Code"  title="Enter Employee Name" >
			         &nbsp;&nbsp;   <input type="submit" class="myButton" value="Submit" >
					  &nbsp;&nbsp; 	<a href="employee.jsp?action=addemp">&nbsp;<b>Add Employee</b></a>
	</td></tr>
	</table>
</form>


</br></br></br></br></br></br>
<div align="center">
<table id="customers">
 <tr><th colspan="4">Your last Added Employee Is : -</th></tr>
<tr>
<th width="70" bgcolor="1F5FA7">EMPNO</th><th width="300">EMP NAME</th><th width="175">EMP JOINING DATE</th>
<th width="90" >EMP CODE</th>
</tr>
<tr class="alt">
<td align="center"><%=ebean.getEMPNO() %></td>
<td align="center"><%=ebean.getFNAME() %>&nbsp;<%=ebean.getMNAME() %>&nbsp;<%=ebean.getLNAME() %></td>
<td align="center"><%=ebean.getDOJ() %></td><td align="center"><%=ebean.getEMPCODE() %></td>

</tr>
	
</table>
			
</div>

			</div>
			<!--  end table-content  -->
	
			<div class="clear"></div>
		 
		</div>
		<!--  end content-table-inner ............................................END  -->
		</td>
		<td id="tbl-border-right"></td>
	</tr>
	<tr>
		<th class="sized bottomleft"></th>
		<td id="tbl-border-bottom">&nbsp;</td>
		<th class="sized bottomright"></th>
	</tr>
	</table>
	<div class="clear">&nbsp;</div>

</div>
<!--  end content -->
<div class="clear">&nbsp;</div>
</div>
<!--  end content-outer........................................................END -->

<div class="clear">&nbsp;</div>
    
       
</body>
</html>