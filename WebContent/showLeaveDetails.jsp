<%@page import="payroll.DAO.LookupHandler"%>
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
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->


<title>ShowLeave Details</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
<script src="js/jquery.datePicker.js" type="text/javascript"></script>
<!--  jquery core -->
<script src="js/jquery/jquery-1.4.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>


<script type="text/javascript">
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

	
</script>
<script>
	jQuery(function() {
          $("#EMPNO").autocomplete("list.jsp");
	});
</script>
<style type="text/css">
.ac_results {
	padding: 0px;
	border: 1px solid #84a10b;
	background-color: #84a10b;
	overflow: hidden;
}

.ac_results ul {
	width: 100%;
	list-style-position: outside;
	list-style: none;
	padding: 0;
	margin: 0;
}

.ac_results li {
	margin: 0px;
	padding: 2px 5px;
	cursor: default;
	display: block;
	color: #fff;
	font-family: verdana;
	cursor: pointer;
	font-size: 12px;
	line-height: 16px;
	overflow: hidden;
}

.ac_loading {
	background: white url('../images/indicator.gif') right center no-repeat;
}

.ac_odd {
	background-color: #84a10b;
	color: #ffffff;
}

.ac_over {
	background-color: #5a6b13;
	color: #ffffff;
}

.input_text {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	border: 1px solid #84a10b;
	padding: 2px;
	width: 150px;
	color: #000;
	background: white url(../images/search.png) no-repeat 3px 2px;
	padding-left: 17px;
}

#accordion {
	list-style: none;
	padding: 0 0 0 0;
	width: 170px;
}

#accordion li {
	display: block;
	background-color: #2c6da0;
	font-weight: bold;
	margin: 1px;
	cursor: pointer;
	padding: 35 35 35 7px;
	list-style: circle;
	-moz-border-radius: 10px;
	-webkit-border-radius: 10px;
	border-radius: 10px;
	height: 30px;
	text-decoration: none;
}

#accordion li:hover {
	background-color: #74ACFF;
}

#accordion ul {
	list-style: none;
	padding: 0 0 0 0;
	display: none;
}

#accordion ul li {
	font-weight: normal;
	cursor: auto;
	padding: 0 0 0 7px;
}

#accordion a {
	text-decoration: none;
	color: #FFFFFF;
}

#accordion a:hover {
	text-decoration: underline;
	text-shadow: #FF9927;
}

#accordion ul li.submenu {
	background-color: #66CCFF;
}

#accordion ul li.submenu:hover {
	background-color: #74ACFF;
}
</style>



<% 
 
String action = request.getParameter("action")==null?"":request.getParameter("action");
LookupHandler lh=new  LookupHandler(); 
ArrayList<LeaveMasterBean> leaveballist=null;
if(action.equals("BalLeave"))
{
leaveballist =(ArrayList<LeaveMasterBean>)session.getAttribute("leavevbal");
} 
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
		<div id="content"  ><!--  start page-heading -->
			<div id="step-holder">
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">1</div>
			<div class="step-light-left"><a href="searchTransaction.jsp?action=search" >Search Leave </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no">2</div>
			<div class="step-dark-left"><a href="showLeaveDetails.jsp?action=show">Leave Apply</a></div>
			<div class="step-dark-right">&nbsp;</div>
			<div class="step-no">3</div>
			<div class="step-light-left"><a href="leaveCancel.jsp?action=first">Leave Cancel</a></div>
			<div class="step-light-right">&nbsp;</div>
			
			<div class="step-no">4</div>
			<div class="step-light-left"><a href="sanction.jsp?action=first">Sanction leave</a></div>
			<div class="step-light-round">&nbsp;</div>
			<div class="clear"></div>
			
		
		</div>
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
			
			
<%
   if(action.equals("show"))
   {
%>
<center>
<h3>Employee Leave Details</h3>
<table border="1" bordercolor="#2f747e"><tr><td align="center">
<form name="empform" action="LeaveMasterServlet?action=leave1"
	method="Post" onSubmit="return TakeCustId()">
<table border="1" style="margin-top:20px;">
	<tr>
		<td width="350">Enter Employee NO <input type="text" name="EMPNO"
			size="30" id="EMPNO" onClick="showHide()" title="Enter Customer Name"></td>
		<td width="2" valign="top"><input name="submit" type="submit"
			class="form-submit" value="Submit"></td>
	</tr>
	<tr></tr>
</table>
</form><br><br>
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
		</table>		</td>
	</tr>
	<tr>
		<td>
		<div style="height: 70px; overflow-y: scroll; width: auto" id="div1">
		<table border="1" width="598" id="customers">
			
		</table>
		</div>		</td>
	</tr>

	<tr bgcolor="#2f747e">
		<td>&nbsp;</td>
	</tr>
</table>
</td></tr></table>
</center>
<%
   }
 %>
<br>

<%
if(action.equals("BalLeave"))
{
%>   <center>
	<h3>Employee Leave Details</h3>
	<table border="1" bordercolor="#2f747e"><tr><td align="center">
<form name="empform" action="LeaveMasterServlet?action=leave1"
	method="Post" onSubmit="return TakeCustId()">
<table border="1" style="margin-top:20px;">
	<tr>
		<td width="350">Enter Employee NO <input type="text" name="EMPNO"
			size="30" id="EMPNO" onClick="showHide()" title="Enter Customer Name" value="<%=request.getAttribute("empno")%>"></td>
		<td width="2" valign="top"><input name="submit" type="submit"
			class="form-submit" value="Submit"></td>
		<td width="76"><a href="LeaveMasterServlet?action=display1"><img src="images/addleave.gif" /></a></td>
		<td width="8"></td>
	</tr>
	<tr></tr>
</table>
</form><br><br>
		
</center>
		<center>
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
		</table>		</td>
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
				<td width="80"><%=lh.getLKP_Desc("LEAVE",leaveBal.getLEAVECD()) %></td>
				<td width="90"><%=leaveBal.getBAL() %></td>
				<td width="90"><%=leaveBal.getTOTCR() %></td>
				<td width="90"><%=leaveBal.getTOTDR() %></td>
			</tr>
			<%
	 } 
	   
	 %>
		</table>
		</div>		</td>
	</tr>

	<tr bgcolor="#2f747e">
		<td>&nbsp;</td>
	</tr>
</table>
</td></tr></table>
</center>
			
<%} %>
			</div>
			<!--  end table-content  -->
	
			<div class="clear"></div>
		</div>
		<!--  end content-table-inner ............................................END  -->		</td>
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