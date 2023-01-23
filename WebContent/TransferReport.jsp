<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@ page import ="java.util.ArrayList"%>

<%@ page contentType="text/html; charset=iso-8859-1" language="java"
	import="java.sql.*" errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TransferReport</title>
<!-- <script src="js/MONTHPICK/jquery.js"></script>
<script type="text/javascript" src="js/MONTHPICK/jquery-ui.min.js"></script>
<link rel="stylesheet" type="text/css" media="screen"
	href="css/MONTHPICK/jquery-ui.css"> -->

<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
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
		$("#EMPNO").autocomplete("searchList.jsp");
	});
	</script>
<%
  EmpOffHandler eh = new EmpOffHandler();
  ArrayList<EmpOffBean> ebn = new ArrayList<EmpOffBean>();
  ebn = eh.getprojectCode();
  %> 
<script type="text/javascript">
function focus() {

		document.getElementById("EMPNO").focus();

	}
	function validation() {
		var fd = document.getElementById("fromdate").value;
		var td = document.getElementById("tdate").value;
		var EMPNO = document.getElementById("EMPNO").value;
		var type = document.getElementById("reporttyp").value;
		var subBranch = document.getElementById("branchtyp").value;
		var url ="";
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest;
		} else //if(window.ActivXObject)
		{
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}

		var xmlhttp;
		 
		 if (type == "2") {
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
		 if (type == "3") {
		  if (document.getElementById("EMPNO").value == "") {
				alert("Please Insert Employee Name");
			}
		 var atpos=EMPNO.indexOf(":");
			if (atpos<1)
			  {
			  alert("Please Select Correct Employee Name");
			  return false;
			  }
			    
		 }
		 if (type == "4") {
			 if (document.getElementById("transferYear").value =="") {
					alert("Please insert Year..");
					document.getElementById("transferYear").focus();
					return false;
				}
			 if (document.getElementById("branchtyp").value =="0") {
					alert("Please select Branch");
					document.getElementById("branchtyp").focus();
					return false;
				}
			 if (document.getElementById("branchtyp").value =="2") {
				 if(document.getElementById("Branch").value=="Select"){
						alert("Please select Branch");
						document.getElementById("Branch").focus();
						return false;
					}
				}
			 
			 if(document.getElementById("branchtyp").value=="3"){
				 RangeFrom = document.getElementById("rangeFrom").value;
				 RangeTo =document.getElementById("rangeTo").value;
					if(document.getElementById("rangeFrom").value=="Select"){
						alert("Please select Branch");
						document.getElementById("rangeFrom").focus();
						return false;
					}
				if(document.getElementById("rangeTo").value=="Select"){
					alert("Please select Branch");
					document.getElementById("rangeTo").focus();
					return false;
				}
				else if(RangeFrom > RangeTo){
					alert("Please select Proper Branch");
					document.getElementById("rangeFrom").focus();
					return false;
				}
				}
		 }
		 
		 
		 if (type == "1") {
		   url = "ReportServlet?action=checktransferlist&type=" + type;
		 }
		 if (type == "2") {
			  url = "ReportServlet?action=checktransferlist&fromdate=" + fd+ "&tdate=" + td + "&type=" + type;
		 }
		 if (type == "3") {
			  url = "ReportServlet?action=checktransferlist&type=" + type+ "&empno=" +EMPNO;
		 }
		 if (type == "4") {
			  url = "ReportServlet?action=checktransferlist&type=" + type;
		 }
	//	var url = "ReportServlet?action=checktransferlist&fromdate1=" + fd+ "&todate1=" + td + "&type1=" + type;
	
	
		document.getElementById("process").hidden = false;
		var response = "";
		var fg = "";
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4) {
				response = xmlhttp.responseText;
				
				// document.getElementById("viewPdf").innerHTML=response;
				document.getElementById("process").hidden = true;
				document.getElementById("viewPdf").hidden = false;
				if (response == "false") {
					alert("No Record Found For This Month..!!!");
					return false;
				} else {
					document.getElementById("formtransfer").submit();
					/* document.getElementById("getreport").type="submit";
					return true; */
				}
			}

		};
		xmlhttp.open("POST", url, true);
		xmlhttp.send();

	}
	function selectReport() {
		var type = document.getElementById("reporttyp").value;
		if (type == "1") {
			document.getElementById('frmdate').style.display = 'none';
			document.getElementById('todate').style.display = 'none';
			document.getElementById('empname').style.display = 'none';
			document.getElementById('forbranches').style.display = 'none';
			document.getElementById('branc').style.display = 'none';
			document.getElementById('specific').style.display = 'none';
			document.getElementById('forSelectYear').style.display = 'none';
		} else if (type == "2") {
			document.getElementById('empname').style.display = 'none';
			document.getElementById('frmdate').style = 'table-row';
			document.getElementById('todate').style = 'table-row';
			document.getElementById('forbranches').style.display = 'none';
			document.getElementById('branc').style.display = 'none';
			document.getElementById('specific').style.display = 'none';
			document.getElementById('forSelectYear').style.display = 'none';
		} else if (type == "3") {
			document.getElementById('frmdate').style.display = 'none';
			document.getElementById('todate').style.display = 'none';
			document.getElementById('empname').style = 'table-row';
			document.getElementById('forbranches').style.display = 'none';
			document.getElementById('branc').style.display = 'none';
			document.getElementById('specific').style.display = 'none';
			document.getElementById('forSelectYear').style.display = 'none';
		}
		else if(type == "4") {
			document.getElementById('frmdate').style.display = 'none';
			document.getElementById('todate').style.display = 'none';
			document.getElementById('empname').style.display = 'none';
			//document.getElementById('specific').style.display = 'none';
			//document.getElementById('branc').style.display = 'none';
			document.getElementById('forbranches').style = 'table-row';
			document.getElementById('forSelectYear').style = 'table-row';
		}
	}
	function selectbranch()
	{
		var type =document.getElementById("branchtyp").value;
	   
		if(type =="1")
		{
	     document.getElementById('branc').style.display='none';
		 document.getElementById('specific').style.display='none';
		}
		else if(type =="2")
		{
	    document.getElementById('specific').style.display='none';
		document.getElementById('branc').style='table-row';
			
		}
		else if(type =="0")
			{
			document.getElementById('branc').style.display='none';
			document.getElementById('specific').style.display='none';
			}
		else if(type =="3")
		{
		document.getElementById('specific').style='table-row';	
		document.getElementById('branc').style.display='none';
		}
	}
	function inputLimiter(e, allow) {
		var AllowableCharacters = '';
		if (allow == 'Numbers') {
			AllowableCharacters = '1234567890.';
		}
		var k;
		k = document.all ? parseInt(e.keyCode) : parseInt(e.which);
		if (k != 13 && k != 8 && k != 0) {
			if ((e.ctrlKey == false) && (e.altKey == false)) {
				return (AllowableCharacters.indexOf(String.fromCharCode(k)) != -1);
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
</script>
<script type="text/javascript">
</script>
<style>
.ui-datepicker-calendar {
	display: none;
}
</style>

</head>
<body style="overflow: hidden;" onload="focus()">
	<%@include file="mainHeader.jsp"%>
	<!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp"%>

	<!-- start content-outer ........................................................................................................................START -->
	<div id="content-outer" style="overflow-y: scroll; max-height: 80%;">
		<!-- start content -->
		<div id="content">

			<!--  start page-heading -->
			<div id="page-heading">
				<h1>Employees Transfer Report</h1>
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

									<form id="formtransfer" name="EmployeesTransferReport"
										action="ReportServlet?action=transferreport" method="post">
										<table border="1" id="customers" align="center"
											style="width: 60%;">
											<tr>
												<th>Employees Transfer Report</th>
											<tr>
											<tr class="alt">
												<td align="center"><input type="hidden" id="action"
													name="action" value="emplist"></input>
													<table align="center" style="width: 100%;">
														<!-- 	<tr class="alt" height="30" align="center"> 
						<td>Select From Date:-</td>
						<td align="left">
							<input name="fromdate" id="fromdate" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
						</td>
							
					</tr> -->
														<!-- <tr class="alt" height="30" align="left"> 
						<td>Select To Date:-</td>
						<td align="left">
							<input name="todate" id="todate" readonly="readonly" class="date-picker" placeholder="Click here for Calender"/>
						</td>
							
					</tr> -->
														<!-- <tr>
						<td >Select Report For:-</td>
						<td  align="left">
							<select id="type" name="type" style="width:145px">
								<option value="0">Select</option>
						
							 	<option value="Transfer">Transfer</option>
							
							
								
							</select>
			         </td>	
					</tr> -->
														<tr class="alt" height="30" align="center" id="branches">
															<td align="left" style="font-weight: 900;">Select
																Report Type</td>
															<td align="left"><select id="reporttyp"
																name="reporttyp" onchange="selectReport()"
																style="width: 145px; height: 30px; font-size: 15px;">
																	<option value="1">ALL</option>
																	<option value="2">Date_Wise</option>
																	<option value="3">Specific</option>
																	<option value="4">Branch Wise</option>
															</select></td>
														</tr>
														<tr class="alt" height="30" align="center" id="frmdate"
															style="display: none">
															<td align="left" style="font-weight: 900;">Select
																From Date:-</td>
															<td align="left">
															<!-- <input name="fromdate"
																id="fromdate" readonly="readonly" class="date-picker"
																placeholder="Click here for Calender"
																style="width: 145px; height: 30px; font-size: 15px;" /> -->
																<input name="fromdate" id="fromdate" style="width: 145px; height: 30px; font-size: 15px;" 
													type="text" onBlur="if(value=='')" readonly="readonly"> &nbsp;<img
													src="images/cal.gif" align="absmiddle"
													style="vertical-align: middle; cursor: pointer;"
													onClick="javascript:NewCssCal('fromdate', 'ddmmmyyyy')" />
															</td>

														</tr>
														<tr class="alt" height="30" align="left" id="todate"
															style="display: none">
															<td align="left" style="font-weight: 900;">Select To
																Date:-</td>
															<td align="left">
															<!-- <input name="tdate" id="tdate"
																readonly="readonly" class="date-picker"
																placeholder="Click here for Calender"
																style="width: 145px; height: 30px; font-size: 15px;" /> -->
																<input name="tdate" id="tdate" style="width: 145px; height: 30px; font-size: 15px;"
													type="text" onBlur="if(value=='')" readonly="readonly">&nbsp;<img
													src="images/cal.gif" align="absmiddle"
													style="vertical-align: middle; cursor: pointer;"
													onClick="javascript:NewCssCal('tdate', 'ddmmmyyyy')" />
															</td>

														</tr>
														<tr class="alt" height="30" align="left" id="empname" style="display: none">
															<td align="left" style="font-weight: 900;">Enter
																Employee Name or Emp-Id</td>
															<td align="left">
																<!-- 	<input class="form-control" type="text" name="EMPNO" id="EMPNO"  onclick="showHide()"
 						placeholder="Enter Employee Name or Emp-Code"  title="Enter Employee Name"  style="width:145px;height: 30px;font-size: 15px;"> -->


																<input class="form-control" type="text" name="EMPNO"
																id="EMPNO" title="Enter Employee Name"
																style="width: 265px; height: 30px; font-size: 15px;">


															</td>
															
														</tr>
											
											<tr class="alt" height="30" id="forbranches" style="display:none">
											<td><b>Select Branch</b></td>
											<td>	
											<select id="branchtyp" name="branchtyp"  onchange="selectbranch()" style="width: 145px; height: 30px;"> 
											<option value="0" selected="selected">SELECT</option>
											<option value="1">ALL</option>
											<option value="2">Branch_Wise</option>
											<option value="3">RANGE</option>
											</select>
											</td></tr>
											<tr class="alt" id="branc" style="display:none">
					   						<td><b>Select Branch Wise</b> </td>
     			       						<td>
					   						<select name="Branch" id="Branch" style="width:145px; height: 30px;" >  
						      				   <option value="Select">Select</option>  
						    				   <%
												for(EmpOffBean eopbn : ebn)
												{
												%>
												<option value="<%=eopbn.getPrj_srno()%>"><%=eopbn.getSite_name()%></option>
												<%		}
												%>
						     			      	</select>
						     			      	</td>
						     			      	<td style="height:25px;"></td>
						     			      	</tr>
						     			      	
						     			      	<tr class="alt" id="branc" style="display:none">
    				
						        				<tr class="alt" height="30"  id="specific" style="display:none">
												<td ><b>Range From</b></td>
												<td  align="left">
												<select name="rangeFrom" id="rangeFrom" style="width:145px; height: 30px;">  
      					  						<option value="Select">Select</option>  
    											
    											<%
														for(EmpOffBean eopbn1 : ebn)
														{
												%>
														<option value="<%=eopbn1.getSite_id()%>"><%=eopbn1.getSite_id()%></option>
												<%		}
												%>
     			      							</select>
     			      							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<b>Range To</b>&nbsp;&nbsp;&nbsp;&nbsp;
												<select name="rangeTo" id="rangeTo" style="width:145px; height: 30px;">  
      					  						<option value="Select">Select</option>  
    											<%
    											for(EmpOffBean eopbn2 : ebn)
														{
												%>
														<option value="<%=eopbn2.getSite_id()%>"><%=eopbn2.getSite_id()%></option>
												<%		}
												%>
     			      							</select>
												</td>			
												</tr>
						     			    <tr class="alt" id="forSelectYear" style="display:none">
											<td><b>Please Add Year</b></td>
											<td><input class="form-control"  id="transferYear" name="transferYear" placeholder="Enter Year" style="height: 30px;" onkeypress="return inputLimiter(event,'Numbers')">	
											</td></tr>
											<tr>
												<td colspan="2" align="center"><input type="button"
													id="getreport" class="myButton" value="Get Report"
													onclick="validation()" style="height: 40px; width: 125px;" />
												</td>
											</tr>
										</table>
									</form>
								</center>
					</td>
				</tr>
			</table>

		</div>
		<!--  end table-content  -->

		<div class="clear"></div>

	</div>
	<%-- 		<input type="hidden" id="fg" value="<%=fg %>"> --%>
	<div id="viewPdf" hidden="true" align="center"></div>
	<div id="process"
		style="z-index: 1000; background-color: white; height: 100%; width: 100%; top: 0px; left: 0px; position: fixed; opacity: 0.8;"
		hidden=true;>
		<div align="center" style="padding-top: 20%;">
			<img alt="" src="images/process.gif">
		</div>
	</div>
	<!--  end content-table-inner ............................................END  -->
	<!-- </td>
	<td id="tbl-border-right"></td>
	</tr>
	<tr>
		<th class="sized bottomleft"></th>
		<td id="tbl-border-bottom">&nbsp;</td>
		<th class="sized bottomright"></th>
	</tr>
	</table> -->
	<div class="clear">&nbsp;</div>



	
	<!--  end content -->
	<div class="clear">&nbsp;</div>
	
	<!--  end content-outer........................................................END -->

	<div class="clear">&nbsp;</div>


</body>
</html>