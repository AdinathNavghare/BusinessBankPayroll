<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.DAO.*"%>
<%@page import="payroll.Model.*"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
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
	function validate() {

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
		  document.getElementById("EMPNO").focus();
		  return false;
		  }
		
		var EMPNO1 = document.getElementById("EMPNO1").value;
	    
		if (document.getElementById("EMPNO1").value == "") {
			alert("Please Insert Employee Name");
			document.getElementById("EMPNO1").focus();
			return false;
		}
		var atpos=EMPNO1.indexOf(":");
		if (atpos<1)
		  {
		  alert("Please Select Correct Employee Name");
		  document.getElementById("EMPNO1").focus();
		  return false;
		  }
		if (document.leaveLedgerform.date.value == "") {
			alert("Please Select Date");
			document.leaveLedgerform.date.focus();
			return false;

		}

	}
</script>



</head>
<body style="overflow: hidden;">
<%
	String pageName = "ANN_12BA_Report.jsp";
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

	<%@include file="mainHeader.jsp"%>
	<!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp"%>

	<!-- start content-outer ........................................................................................................................START -->
	<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
		<!-- start content -->
		<div id="content">

			<!--  start page-heading -->
			<div id="page-heading">
				<h1>ANN_12BA Report</h1>
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



									<form name="leaveLedgerform" action="ReportServlet"
										method="get" onSubmit="return validate()">
										<table id="customers" width="553" align="center">

											<tr>
												<th colspan="4">ANN_12BA Report</th>
											</tr>
											<tr class="alt">
												<td>From Empno</td>
												<td><input type="text" name="EMPNO" id="EMPNO"
													onClick="showHide()" title="Enter Employee No"></td>
												<td>To Empno</td>
												<td><input type="text" name="EMPNO1" id="EMPNO1"
													onClick="showHide()" title="Enter Employee No"></td>
											</tr>
											<tr class="alt">
												<td>Date</td>
												<td><input name="date" size="20" id="date"
													value="<%=ReportDAO.getSysDate()%>" type="text"
													onBlur="if(value=='')">&nbsp;<img
													src="images/cal.gif" align="absmiddle"
													style="vertical-align: middle; cursor: pointer;"
													onClick="javascript:NewCssCal('date', 'ddmmmyyyy')" /></td>
												<td></td>
												<td></td>
											</tr>

											<tr class="alt">
												<td colspan="4" align="center"><input type="submit"
													value="GetReport" /> &nbsp;&nbsp;<input type="button"
													value="Cancel" /> <input type="hidden" name="action"
													value="ANN_12BAreport"></td>
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