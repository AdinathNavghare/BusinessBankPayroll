<%@page import="payroll.DAO.Sal_DetailsHandler"%>
<%@page import="java.util.Locale"%>
<%@page import="com.ibm.icu.text.NumberFormat"%>
<%@page import="payroll.DAO.PostingHandler"%>
<%@page import="payroll.Model.AutopostBean"%>
<%@page import="java.util.ArrayList"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=iso-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Account Settings &copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->

<%
	String empno ="";
	String action ="";
	try
	{
		empno = session.getAttribute("EMPNO").toString();
		action = request.getParameter("action");
	}
	catch(Exception e)
	{
		//response.sendRedirect("login.jsp?action=0");
	}
	
%>

<script type="text/javascript">
	
	function showMsg()
	{
		var action = document.getElementById("act").value;
		if(action!="")
		{
			if(action=="1")
			{
				alert("Password changed Successfully, \n Please Log IN again!");
				window.location.href="Logout.jsp";
			}
			else if(action == "2")
			{
				alert("!! Current Password you have entered is Wrong !!");
			}
			else if(action == "3")
			{
				alert("!! Error,Please try again");
			}
		}
	}
	
	
	function validate()
	{	
		if(document.getElementById("newPass").value != document.getElementById("confPass").value)
		{	
			alert("New Password do not Match with Confirm Password");
			document.getElementById("newPass").value="";
			document.getElementById("confPass").value="";
			return false;
		}
		var flag=confirm("Are you Sure to change your Password?");
		return flag;
	}

</script>
<style type="text/css">
<!--
body,td,th {
	font-family: Times New Roman;
	font-size: 16px;
}
.style3 {font-size: 1.5em}
-->
</style></head>
<body style="overflow: hidden;" onload="showMsg()"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer"  >
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Change Password</h1>
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
		<td align="center">
		<!--  start content-table-inner ...................................................................... START -->
		<div id="content-table-inner">
		
			<!--  start table-content  -->
			<div id="table-content" >
			<form action="UserServlet?action=changePass" method="post" onsubmit="return validate()">
			<table id="customers" width="553" align="center">
			<tr> <th colspan="4">Change Password</th></tr>
			<input type="hidden" id="act" value="<%=action%>">
			  
			  <input type="hidden" id="empno" value="<%=empno%>">
			 
			  <tr><td>Current Password &nbsp;</td><td><input type="password" id="curPass" name="curPass" required></td></tr>
			   <tr><td>New Password &nbsp;</td><td><input type="password" id="newPass" name="newPass" required></td></tr>
			  <tr><td>Confirm New Password &nbsp;</td><td><input type="password" id="confPass" name="confPass" required></td></tr>
			  
			  <tr><td>&nbsp;</td><td><input type="submit" value="OK" class="myButton">&nbsp;&nbsp;&nbsp;
			  							<input type="reset" value="Cancel" class="myButton"></td></tr>
			 
			  </table>
			 </form>
			</div>
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