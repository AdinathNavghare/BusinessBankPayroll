<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.DAO.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<script src="js/jquery/jquery-1.4.2.min.js" type="text/javascript"></script>
<!--  checkbox styling script -->

<link rel="stylesheet" type="text/css" media="all" href="jsDatePick_ltr.min.css" />

<title>Deduction Maintainance</title>



</head>
<body onLoad="hideDiv()" style="overflow: hidden;"><center><font face="Georgia"  size="2">
<div >
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
</div>
<div style="height: 500px; overflow-y: scroll; width:100%"" id="div1">
<div id="content-table-inner"><!--  start table-content  -->


<div id="table-content">

<br>

<br>
<div style="border:thick">

<form  name="userForm" action="UserMasterServlet?action=add" method="post" onSubmit="return validate()">
     <h3>DEDUCTION MAINTAINANCE TABLE</h3>
      <table width="696" cellpadding="0" cellspacing="0" id="customers"align="center">
	<tr>
		<th width="694" height="22">Deduction Maintainance</th>
    </tr>
</table>
<table width="645"  cellpadding="0" cellspacing="0"
	 align="center" height="339" border="0" id="customers">
	<tr class="alt">
		<td width="135" height="42">Employee No</td>
		<td width="229"><input type="text" name="empno" /></td>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr class="alt">
		<td height="38">Name of Employee</td>
		<td colspan="3"><input type="text" name="fname" />&nbsp;
		<input type="text" name="midlename" />&nbsp;
		<input type="text" name="lastname" /></td>
	</tr>
	
    <tr class="alt"><td height="50" colspan="4"><table>
	  <tr><th height="9"></th>
	  <th></th><th></th>
	  </tr>
	  <tr>
	  <td width="108" height="23">&nbsp;</td> 
	  <td width="109">&nbsp;</td>
	  <td width="126">&nbsp;</td>
	  </tr>
	</table>
	</td>
	</tr>
	
	<tr class="alt">
		<td height="44">Transaction Code</td>
		<td><input type="text" name="transcode" /></td>
		<td width="121" >Account NO</td>
		<td width="160" ><input type="text" name="accno" /></td>
	</tr>
	<tr class="alt">
		<td height="32">Sr.NO</td>
		<td><input type="text" name="srno" /></td>
		<td >Sanction Date</td>
		<td ><input type="text" name="accno" /></td>
	</tr>
	<tr class="alt">
		<td height="38">Installment Amount</td>
		<td><input type="text" name="installmentamt" /></td>
		<td >Interest Rate</td>
		<td ><input type="text" name="interest" /></td>
	</tr>
	
	<tr class="alt">
		<td height="29">Start Date</td>
		<td><input type="text"  id="inputField" /></td>
		<td >End Date</td>
		<td ><input type="text" name="endDate" /></td>
	</tr>
	<tr class="alt">
		<td height="35">Cummilative</td>
		<td><input type="radio" name="cumty"/>Yes <input type="radio" name="cumty"/> No </td>
		<td >Active</td>
		<td ><input type="radio" name="cumty"/>Yes <input type="radio" name="cumty"/> No</td>
	</tr>
	
	<tr class="alt">
		<td height="31" colspan="4" align="center">
		<input type="submit" value="Add"/>  <input type="button" value="Modify"/> <input type="button" value="Delete"/><input type="reset"   value="Exit" />
		</td>
	</tr>
</table>
<div style=" width:710px;background-color:#1F5FA7">&nbsp;</div>

</form>
</div>
</div>
</div>
</div>

</font>

</center>
</body>
</html>