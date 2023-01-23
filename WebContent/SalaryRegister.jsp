<%@page import="payroll.Core.ReportDAO"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy SalaryRegister</title>
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

	function validation()
	{
		if(document.getElementById("type").value=="0")
			{
			alert("Please Select Report Type");
			document.getElementById("type").focus();
			return false;
			}
		
	}
</script>

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
		<h1>Salary Register </h1>
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
			
			<form name="SalaryRegister" action="ReportServlet?action=salaryreg" method="get">
			<table border="1" id="customers" align="center">
			<tr>
				<th width="355">Salary Register</th>
			<tr>
			<tr class="alt">
				<td height="158" align="center">
				
				<table align="center">
					<tr class="alt" height="30" align="center">
                     <input type="hidden" id="action" name="action" value="salaryreg"></input>
						<td width="87">Select Date</td>
						<td width="159" align="left"><input name="date" size="15" id="date"
							type="text" value="<%=ReportDAO.getSysDate() %>" readonly="readonly"
							onFocus="if(value=='dd-mmm-yyyy') {value=''}"
							onBlur="if(value=='') {value='dd-mmm-yyyy';}">&nbsp;<img
							src="images/cal.gif" align="absmiddle"
							style="vertical-align: middle; cursor: pointer;"
							onClick="javascript:NewCssCal('date', 'ddmmmyyyy')" /></td>

					</tr>
					<tr>
						<td width="87">Report Type</td><td align="left">
						<select id="type" name="type">
						 <option value="0">Select</option>
						<option value="A">All Employee</option>
<!-- 						<option value="B">Branch Wise</option>
						<option value="D">Designation Wise</option> -->
						</select>
			         <td/>	
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