<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Leave reports</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
	<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
	<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>

<script src="js/jquery/jquery.autocomplete.js"></script>
<style type="text/css">
.row:hover {
	background-color: #CCCCCC;
	cursor: pointer;
}

.style1 {
	color: #FF0000
}
</style>


<script>
	jQuery(function() {
		$("#EMPNO").autocomplete("list.jsp");
	});
</script>
<script type="text/javascript">
function validate()
{
  var fromDate=document.LeaveReport.fromdate.value;
   var toDate=document.LeaveReport.todate.value;
   fromDate = fromDate.replace(/-/g,"/");
	toDate = toDate.replace(/-/g,"/");
   
   
  
 if(document.getElementById("fromdate").value=="")
	 {
	 alert("please enter the fromdate");
	 document.getElementById("fromdate").focus();
     return false;
	 
	 }
    
   if( document.getElementById("todate").value=="")
   {
	   	alert("please enter the todate");
	   	document.getElementById("todate").focus();
	      return false;
	   }
   if(document.LeaveReport.empbrn.value=="")
	   {
	   alert("Please Enter Emp Brn");
	   document.LeaveReport.empbrn.focus();
	   return false;
	   }
   if(document.LeaveReport.days.value=="")
   {
	   alert("Please Enter Number of Days");
	   document.LeaveReport.days.focus();
	   return false;
   }
   
   var d1 = new Date(fromDate);
 	
 	var d2 =new  Date(toDate);
 	
 if (d1.getTime() > d2.getTime())
      {
	   alert("Invalid Date Range!\n fromdate Date cannot greater than TODate!");
	   document.LeaveReport.todate.focus();
	   return false;
	   }
  var emp_no = [];
 var emp_no1 = [];
	 emp_no=EMPNO.split(":");
	 emp_no1=EMPNO1.split(":");
	 if(emp_no1 > emp_no)
		 {
		 
		 alert("Please Select Proper  Employee Number");
		 return false;
		 }
}


</script>
</script>


</head>
<body> 
<%
	String pageName = "leaveReport.jsp";
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

<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		
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
			<center>
			<h2>Employees Leave Report</h2>
			<form name="LeaveReport" action="ReportServlet" onsubmit="return  validate()">
<table border="1" id="customers">
	<tr>
		<th width="530">Leave Report</th>
		<tr>
			<tr class="alt">
				<td height="171" align="center">
				<table>
					<tr class="alt" height="30">
					
						<td>FromDate</td>
						<input type="hidden" id="action" name="action" value="leaveReport"></input>
						<td bgcolor="#FFFFFF"><input name="fromdate" size="20"
							id="fromdate" type="text" value=""
							onFocus="if(value=='dd-mmm-yyyy') {value=''}"
							onBlur="if(value=='') {value='';}">&nbsp;<img
							src="images/cal.gif" align="absmiddle"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('fromdate', 'ddmmmyyyy')" /></td>
						<td>Todate</td>
						<td bgcolor="#FFFFFF"><input name="todate" size="20"
							id="todate" type="text" value=""
							onFocus="if(value=='dd-mmm-yyyy') {value=''}"
							onBlur="if(value=='') {value='';}">&nbsp;<img
							src="images/cal.gif" align="absmiddle"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('todate', 'ddmmmyyyy')" /></td>

					</tr>
					<tr>
						<td>Emp Brn</td>
						<td><input type="text" size="20" name="empbrn" id="empbrn"
							title="Enter Employee No" /></td>
						<td>Days</td>
						<td><input type="text" size="20" name="days" id="days"/></td>
					</tr>
					<tr>
						<td colspan="4" align="center"><input type="submit"
							value="Go" /></td>
					</tr>
				</table>

			  </td>
			</tr>
</table>
</form>
</center>
			
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