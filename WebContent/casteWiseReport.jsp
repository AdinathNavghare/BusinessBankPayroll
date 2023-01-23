<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="payroll.DAO.*"%>
<%@page import="payroll.Model.*"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />


<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script>
	jQuery(function() {
		$("#EMPNO").autocomplete("list.jsp");
	});
</script>

<script>
	jQuery(function() {
		$("#EMPNO1").autocomplete("list.jsp");
	});
</script>


<script type="text/javascript">



function validate()
{
 
 if(document.castReportform.date.value=="")
	 {
		 alert("Please Select Date");
	     document.castReportform.date.focus();
	     return false;
	 
	 }
 if(document.castReportform.empbrn.value=="0")
 {
	 alert("Please Select EmpBrn...");
     document.castReportform.empbrn.focus();
     return false;
 
 }
  
 
}


</script>
<%
	String pageName = "casteWiseReport.jsp";
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
Date date =new Date();
SimpleDateFormat frmdate = new SimpleDateFormat("dd-MMM-yyyy");
String dt=frmdate.format(date);
%>


</head>
<body style="overflow: hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Caste/Category Wise Report</h1>
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
			 
			
			
			<form  name="castReportform" action="ReportServlet?action=castwiseReport" method="post" onSubmit="return validate()">
				<table id="customers" width="553" align="center">
				<tr>
				    <th colspan="4">Caste Wise Report</th>
				  </tr>
												<tr class="alt"><td>Select Date</td>
												<td><input name="date" size="20" id="date" value="<%=dt %>" type="text" onBlur="if(value=='')">&nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('date', 'ddmmmyyyy')" /></td>
												<td>EMPBRN</td>
												<td><select name="empbrn" id="empbrn" >
											    <option value="0" selected="selected">Select</option>
												<option value="C">Category Wise</option>
												<option value="D">Caste Wise</option>
												</select></td></tr>
												<tr class="alt"><td colspan="4" align="center"> <input type="submit" value="GetReport" /> &nbsp;&nbsp;<input type="button" value="Cancel"/></td></tr>
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