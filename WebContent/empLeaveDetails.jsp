<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ListIterator"%>
<%@page import="payroll.DAO.LeaveMasterHandler"%>
<%@page import="payroll.Model.LeaveMasterBean"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ShowLeave Details</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
<script src="js/jquery/jquery.datePicker.js" type="text/javascript"></script>
<!--  jquery core -->
<script src="js/jquery/jquery-1.4.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>

<% 
String empno = session.getAttribute("EMPNO").toString(); 
String action = request.getParameter("action")==null?"":request.getParameter("action");
LeaveMasterHandler lmh = new LeaveMasterHandler();
ArrayList<LeaveMasterBean> leaveballist = lmh.getList(Integer.parseInt(empno));
%>
</head>
<body style="overflow: hidden;">

<div><%@include file="mainHeader.jsp"%> <!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%></div>
<!-- start content-outer ........................................................................................................................START -->

<!-- start content -->

<div id="content-table-inner"><!--  start table-content  -->
<div style="height: 500px; overflow-y: scroll; width: 100%" id="div1">
<div id="table-content"><br>
<br>

<center>
<!-- this is  form  display first time -->


<br>



<div id="page-heading" align="center">
<h3>Employee Leave Details</h3>
</div>

<form name="empform" action="LeaveMasterServlet?action=leave1"
	method="Post" onSubmit="return TakeCustId()">
<table border="1">
	<tr>
		<td width="350">Enter Employee NO <input type="text" name="EMPNO"
			size="30" id="EMPNO" onclick='showHide()'
			;" title="Enter Customer Name"></td>
		<td width="2" valign="top"><input name="submit" type="submit"
			class="form-submit" value="Submit"></td>
		<td width="76"><a href="LeaveMasterServlet?action=display1"><img src="images/addleave.gif" /></a></td>
		<td width="8"></td>
	</tr>
	<tr></tr>
</table>
</form>


<!--  end table-content  --> <br />
<br />


<div>
<table id="customers" align="center" height="20">
	<tr>
		<td>
		<table>
			<tr class="alt" style="font-size: 2">
				<th width="95">EMPNO</th>
				<th width="100">BALDATE</th>
				<th width="82">LeaveCD</th>
				<th width="90" align="center">BAL</th>
				<th width="89">TOTALCR</th>
				<th width="100">TOTALDR</th>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		<div style="height: 70px; overflow-y: scroll; width: auto" id="div1">
		<table border="1" width="598" id="customers">
			<%
	
	for(LeaveMasterBean leaveBal:leaveballist)
		{
		
		%>
			<tr class="row" bgcolor="#FFFFFF" align="center">
				<td width="95"><%=leaveBal.getEMPNO() %></td>
				<td width="100"><%=leaveBal.getBALDT() %></td>
				<td width="80"><%=leaveBal.getLEAVECD() %></td>
				<td width="90"><%=leaveBal.getBAL() %></td>
				<td width="90"><%=leaveBal.getTOTCR() %></td>
				<td width="90"><%=leaveBal.getTOTDR() %></td>

			</tr>
			<% 
	 } 
	   
	 %>
		</table>
		</div>

		</td>
	</tr>

	<tr bgcolor="#92b22c">
		<td>&nbsp;</td>
	</tr>
</table>


</div>
<!--  end table-content  -->
</center>
</div>
<!--  end content-table-inner ............................................END  -->
</div>
<div class="clear">&nbsp;</div>


<!--  end content --></div>

<!--  end content-outer........................................................END -->




</body>
</html>