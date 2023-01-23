<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>LWP Credit Debit Report</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
function validation() {
	var fd = document.getElementById("fromdate").value;
	var td = document.getElementById("tdate").value;
	var type = document.getElementById("reporttyp").value;
	var transactionType = document.getElementById("transtype").value;
	if(type == "0"){
		alert("Please Select Report Type");
		document.getElementById("reporttyp").focus();
		return false;
	}
	else if (transactionType == "0"){
		alert("Please Select transactionType");
		document.getElementById("transtype").focus();
		return false;
	}
	else  if (type == "2") {
	if (document.getElementById("fromdate").value == "") {
		alert("Please Select From Date");
		document.getElementById("fromdate").focus();
		return false;
	} else if (document.getElementById("tdate").value == "") {
		alert("Please Select To Date");
		document.getElementById("tdate").focus();
		return false;
	}
	fd = fd.replace(/-/g,"/");
	td = td.replace(/-/g,"/");
	var d1 = new Date(fd);
	var d2 = new Date(td);
	if (d1.getTime() > d2.getTime()) {
		alert("Invalid Date Range! fromdate Date cannot greater than TODate!");
		 document.getElementById("tdate").focus();
		return false;
	}
	 }
	
}

function selectReport() {
		var type = document.getElementById("reporttyp").value;
		if(type == 0){
			document.getElementById('frmdate').style.display = 'none';
			document.getElementById('todate').style.display = 'none';
		}
		if (type == "1") {
			document.getElementById('frmdate').style.display = 'none';
			document.getElementById('todate').style.display = 'none';
		} 
		else if(type == "2"){
			document.getElementById('frmdate').style = 'table-row';
			document.getElementById('todate').style = 'table-row';
		}
	}
</script>


</head>
<body>
<%@include file="mainHeader.jsp"%>
	<!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp"%>
		<div id="content-outer" style="overflow-y: scroll; max-height: 78%;">
		<!-- start content -->
		<div id="content">


			<!--  start page-heading -->
			<div id="page-heading">
				<h1>LWP Credit Debit Report</h1>
			</div>
			<!-- end page-heading -->

			<table border="0" width="100%" cellpadding="0" cellspacing="0"
				id="content-table">
				<tr>
					<td id="tbl-border-left"></td>
					<td>
						<div id="content-table-inner">

							<div id="table-content">
	<form id=LWPCreditDebitReport name="LWPCreditReport" action="ReportServlet?action=LWPCreditDebitReport"
										method="post" style="text-align: -moz-center;" onsubmit="return  validation()">
										<table border="1" id="customers" align="center"
											style="width: 30%;">
											<tr>
												<th style="font-size: unset;">LWP Credit Debit Report</th>
											<tr>
											<tr class="alt">
												<td align="center"><input type="hidden" id="action"
													name="action" value="emplist"></input>
													<table align="center" style="width: 100%;">

														<tr class="alt" height="30" align="center" id="branches">
															<td align="left" style="font-weight: 400; font-size: unset;">Select
																Report Type</td>
															<td align="left"><select id="reporttyp"
																name="reporttyp" onchange="selectReport()"
																style="width: 145px; height: 30px; font-size: 14px;">
																	<option value="0">Select</option>
																	<option value="1">ALL</option>
																	<option value="2">Date_Wise</option>
															</select></td>
														</tr>
														<tr class="alt" height="30" align="center" id="frmdate"
															style="display: none">
															<td align="left" style="font-weight: 400; font-size: unset;">Select
																From Date:-</td>
															<td align="left"><input name="fromdate"
																id="fromdate"
																style="width: 145px; height: 30px; font-size: 14px;"
																type="text" onBlur="if(value=='')" readonly="readonly">
																&nbsp;<img src="images/cal.gif" align="absmiddle"
																style="vertical-align: middle; cursor: pointer;"
																onClick="javascript:NewCssCal('fromdate', 'ddmmmyyyy')" />
															</td>

														</tr>
														<tr class="alt" height="30" align="left" id="todate"
															style="display: none">
															<td align="left" style="font-weight: 400; font-size: unset;">Select To
																Date:-</td>
															<td align="left"><input name="tdate" id="tdate"
																style="width: 145px; height: 30px; font-size: 14px;"
																type="text" onBlur="if(value=='')" readonly="readonly">&nbsp;<img
																src="images/cal.gif" align="absmiddle"
																style="vertical-align: middle; cursor: pointer;"
																onClick="javascript:NewCssCal('tdate', 'ddmmmyyyy')" />
															</td>

														</tr>
														<tr class="alt" height="30" align="center" id="trntype">
															<td align="left" style="font-weight: 400; font-size: unset;">Select Type</td>
															<td align="left"><select id="transtype"
																name="transtype" 
																style="width: 145px; height: 30px; font-size: 14px;">
																    <option value="0">Select</option>
																	<option value="1">Credit</option>
																	<option value="2">Debit</option>
															</select></td>
														</tr>
														<tr>
															<td colspan="2" align="center"><input  type="submit"
																id="getreport" class="myButton" value="Get Report"
																style="height: 30px; width: 100px;" /></td>
														</tr>
													</table>
													</td>
											</tr>
										</table>
									</form>
							</div>

							<div class="clear"></div>

						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>

	<div id="viewPdf" hidden="true" align="center"></div>
	<div id="process"
		style="z-index: 1000; background-color: white; height: 100%; width: 100%; top: 0px; left: 0px; position: fixed; opacity: 0.8;"
		hidden=true;>
		<div align="center" style="padding-top: 20%;">
			<img alt="" src="images/process.gif">
		</div>
	</div>
	<div class="clear">&nbsp;</div>
	<div class="clear">&nbsp;</div>
	<div class="clear">&nbsp;</div>
	
</body>
</html>
