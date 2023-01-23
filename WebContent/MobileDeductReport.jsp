<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.Core.Calculate"%>
<%@page import="java.awt.Desktop.Action"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.Model.*"%>
<%@page import="payroll.DAO.*"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy; DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>



<script type="text/javascript">


function validate() {
	
	var month=document.getElementById("month").value;
	var year=document.getElementById("year").value;
	
	if(month=="0")
		{
		alert("Please select month!");
		return false;
		}
	
	if(year=="0")
	{
	alert("Please select year!");
	return false;
	}
	
}


</script>


</head>
<body onLoad="checkFlag()" style="overflow: hidden;">
	<%@include file="mainHeader.jsp"%>
	<!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp"%>

	<!-- start content-outer ........................................................................................................................START -->
	<div id="content-outer" style="overflow-y: scroll; max-height: 80%;">
		<!-- start content -->
		<div id="content">

			<!--  start page-heading -->
			<div id="page-heading">
				<h1> Mobile Deduction Report</h1>
			</div>
			<!-- end page-heading -->

			<table border="0" width="100%" cellpadding="0" cellspacing="0"
				id="content-table">
				<tr>
					<th rowspan="3" class="sized"><img
						src="images/shared/side_shadowleft.jpg" width="20" height="300"
						alt="" /></th>
					<th class="topleft"></th>
					<td id="tbl-border-top">&nbsp;</td>
					<th class="topright"></th>
					<th rowspan="3" class="sized"><img
						src="images/shared/side_shadowright.jpg" width="20" height="300"
						alt="" /></th>
				</tr>
				<tr>
					<td id="tbl-border-left"></td>
					<td>
						<!--  start content-table-inner ...................................................................... START -->
						<div id="content-table-inner">

							<!--  start table-content  -->
							<div id="table-content">
								<center>
								
			<form action="MobileAllowanceServlet?action=deductionReport" method="post"  onsubmit="return validate()">
							<table id="customers">
							<tr> 
							<th colspan="3">MOBILE DEDUCTION REPORT </th>
							</tr>
							
							<tr>
							<td> Select Month : </td>
							
							<td>
							<select name='month' id='month'>
									<option value="0" selected >Select</option>
									<option value='Jan'> January </option>
									<option value='Feb'> February </option>
									<option value='Mar'> March </option>
									<option value='Apr'> April </option>
									<option value='May'> May </option>
									<option value='Jun'> June</option>
									<option value='Jul'> July</option>
									<option value='Aug'> August</option>
									<option value='Sep'> September</option>
									<option value='Oct'> October </option>
									<option value='Nov'> November</option>
									<option value='Dec'> December </option>
									
									</select>
						&nbsp;&nbsp;&nbsp;
									<select name="year" id="year">
									<option value="0" selected >Select</option>
									<%
									String[] yyyy=ReportDAO.getSysDate().split("-");
									int yr=Integer.parseInt(yyyy[2])-1;
									for (int yy=yr;yy<=yr+2;++yy)
									{
									%>
									<option value='<%=yy%>'><%=yy%></option>
									<%}%>
									</select>
							
							</td>
							</tr>
							<tr>
							<td colspan="2" > &nbsp;
							</td>
							</tr>
							<tr>
							
							<td colspan="2" align="center"> <input type="submit" value="Get Report"></td>
							</tr>
							</table>
								
								
								</form>
								
							</center>
							</div>
							<!--  end table-content  -->
							<div class="clear"></div>
						</div> <!--  end content-table-inner ............................................END  -->
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
								