<%@page import="payroll.Core.ReportDAO"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java"
	import="java.sql.*" errorPage="error.jsp"  isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Yearly Salary Details </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />

<script src="js/filter.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

<script type="text/javascript">
var month="";
/* function getFilt()
{
	var month=document.getElementById("date").value;
	if(month == "")
	{
		alert("Please Select Date between a year");	
	}
	else
	{
		getFilter('toyearlyCTCReport',month);
	}
}
function addFilt()
{
	var month=document.getElementById("date").value;
	if(month == "")
	{
		alert("Please Select Date between a year");	
	}
	else
	{
		addMoreEmp('toyearlyCTCReport',month);
	}
} */
function setmonth()
{
	
	var x=document.getElementById("date");
	var year=x.options[x.selectedIndex].value;
	month=year;
	
	//alert(month);
}
function clearmonth()
{
	month="";
}
function paycal()
{
	
		
		document.getElementById("list").value=numList;
		if(numList==""){
			alert("please select employee !");
			return false;
		}
		else
			{
		 var xmlhttp;
		var url="";
		if(window.XMLHttpRequest)
		{
			xmlhttp=new XMLHttpRequest;
		}
		else //if(window.ActivXObject)
		{   
			xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		}
		 
			 
			
			url="ReportServlet?action=getCtcReport&emplist="+numList;
			xmlhttp.onreadystatechange=function()
			{
			if(xmlhttp.readyState==4)
				{
					var response = xmlhttp.responseText;
					
					initAll();
				}

			};
			
			xmlhttp.open("post",url, true);
			xmlhttp.send();
		  
	}
	
}

</script>
<script>
	jQuery(function() {
		$("#empNo").autocomplete("list.jsp");
	});
</script>
<script>
	jQuery(function() {
		$("#empNo1").autocomplete("list.jsp");
	});
</script>
 </head>
<body style="overflow: hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>CTC Report</h1>
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
			<!-- <h2>Profession Tax Statement</h2> -->
			<form name="form10" action="ReportServlet" method="post" onsubmit="return paycal()">
				<table border="1" id="customers">
					<tr>
						<th>Yearly CTC Report</th>
					<tr>
					<tr class="alt">
						<td height="80" align="center">
							<table>
								<!-- <tr class="alt">
									<td>FROM EMPNO :</td>
									<td bgcolor="#FFFFFF"><input name="empNo" id="empNo" size="25" onClick="showHide()" title="Enter Employee No"></input></td>
									<td>TO EMPNO :</td>
									<td bgcolor="#FFFFFF"><input name="empNo1" id="empNo1" size="25" onClick="showHide()" title="Enter Employee No"></input></td>
									
								</tr> -->
								<tr class="alt">
									<%-- <td align="center">Select Date : <input name="date" size="20"
									id="date" type="text" value="<%=ReportDAO.getSysDate()%>" readonly="readonly"
									onFocus="if(value=='dd-mmm-yyyy') {value=''}"
									onBlur="if(value=='') {value='';}">&nbsp;<img
									src="images/cal.gif" align="middle"
									style="vertical-align: middle; cursor: pointer;"
									onClick="javascript:NewCssCal('date', 'ddmmmyyyy')" />
									<input type="hidden" id="action" name="action" value="getCtcReport"></input>
									<input type="hidden" id="list" name="list">
									</td> --%>
									<input type="hidden" id="action" name="action" value="getCtcReport"></input>
									<input type="hidden" id="list" name="list">
									
									<td align="center">
									Select Year :
									<select name="date" id="date" onchange="setmonth()">
									<%
									String[] yyyy=ReportDAO.getSysDate().split("-");
									for (int yy=2010;yy<=Integer.parseInt(yyyy[2]);yy++)
									{
									%>
									<option value='<%=yy%>'><%=yy%></option>
									<%}%></select>
									</td>
									
								</tr>
					 			
					 			<tr class="alt" align="center">
								<td>
									<div id="displayDiv" style="height: 300px"></div>
									<div id="countEMP">0 Employees Selected</div>
									<input type="button" class="myButton" value="Cancel All" onclick="cancelAll()">
									<input type="button" class="myButton" value="Select Employees" onclick="getFilt('toyearlyCTCReport')">
									<!-- <input type="button" class="myButton" value="Add More Employee" onclick="addFilt()"> -->
								</td>
								</tr>
					 			
					 			
					 			
								<tr>
									<td colspan="4" align="center">
										<input type="submit" class="myButton" value="Get Report" />&nbsp;&nbsp;&nbsp;
										<input type="reset" class="myButton" value="Clear" />
									</td>
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