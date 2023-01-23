<%@page import="payroll.Model.PolicyBean"%>
<%@page import="payroll.DAO.datefun"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="payroll.Model.TranBean"%>
<%@page import="payroll.Model.TransactionBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />

<script src="js/jquery/jquery-1.4.1.min.js" type="text/javascript"></script>


<!--  date picker script -->
<link rel="stylesheet" href="css/datePicker.css" type="text/css" />
<script src="js/jquery/date.js" type="text/javascript"></script>
<script src="js/jquery/jquery.datePicker.js" type="text/javascript"></script>
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
		var atpos = EMPNO.indexOf(":");
		if (atpos < 1) {
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
.row:hover {
	background-color: #CCCCCC;
	cursor: pointer;
}

.style1 {
	color: #FF0000;
}
</style>
<script type="text/javascript">
	function addNewPolicy() {
		var no = document.getElementById("empno1").value;

		window
				.showModalDialog(
						"addNewPolicy.jsp?no=" + no,
						null,
						"dialogWidth:730px; dialogHeight:210px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
		window.location.href = "PolicyMasterServlet?no=" + no;
	}

	function modifyRec(key) {
		var no = document.getElementById("empno1").value;
		alert(key);
		window
				.showModalDialog(
						"updatepolicy.jsp?action=update&str=" + key,
						null,
						"dialogWidth:730px; dialogHeight:210px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
		window.location.href = "PolicyMasterServlet?no=" + no;

	}
</script>
<%
	String empno = "";
	empno = (String) request.getAttribute("empno") == null ? "" : request.getAttribute("empno").toString();
%>
</head>

<body style="overflow: hidden;">
	<%@include file="mainHeader.jsp"%>
	<!--  start nav-outer-repeat................................................................................................. START -->
	<%@include file="subHeader.jsp"%>

	<!-- start content-outer ........................................................................................................................START -->
	<div id="content-outer" style="overflow-y: scroll; max-height: 82%;">
		<!-- start content -->
		<div id="content">

			<!--  start page-heading -->
			<div id="page-heading">
				<h1>Policy</h1>
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

									<%
										String action = request.getParameter("action") == null ? "" : request.getParameter("action");
										ArrayList<PolicyBean> policylist = (ArrayList<PolicyBean>) session.getAttribute("policylist");
									%>
									<center>
										<%
											if (action.equalsIgnoreCase("getdata")) {
												TransactionBean trbn = new TransactionBean();
												trbn = (TransactionBean) request.getAttribute("empinfobean");
										%>
										<center>
											<form action="PolicyMasterServlet?action=policyinfo"
												method="Post" onSubmit="return TakeCustId()" name="form1"
												id="form1">
												<table border="1">
													<tr>
														<td>Enter Employee Name Or Emp-Id<input type="text"
															name="EMPNO" size="30" id="EMPNO" onClick="showHide()"
															title="Enter Employee Id / Name "> &nbsp;
														</td>
														<td valign="top"><input type="Submit" value=""
															class="form-submit" /></td>

													</tr>
													<tr></tr>
												</table>
											</form>
											<br>

											<table width="765" id="customers">
												<tr class="alt">
													<th colspan="4"></th>
												</tr>
												<tr class="alt">
													<td width="155">Employee Detail</td>
													<td colspan="3"><input type="text" size="10"
														name="empno1" id="empno1" value="<%=trbn.getEmpno()%>"
														readonly="readonly"> <input type="text" size="30"
														value="<%=trbn.getEmpname()%>"></td>
												</tr>
												<tr class="alt">
													<td width="155">SB A/c No</td>
													<td width="233"><input type="text" name="accno"
														id="accno" value="<%=trbn.getAccno()%>"
														readonly="readonly"></td>
													<td width="125">Department</td>
													<td><select name="Depart" id="Depart"
														disabled="disabled">
															<option value="0">Select</option>
															<%
																ArrayList<Lookup> getresult1 = new ArrayList<Lookup>();
																	LookupHandler lkhp1 = new LookupHandler();
																	getresult1 = lkhp1.getSubLKP_DESC("DEPT");
																	for (Lookup lkbean : getresult1) {
																		if (lkbean.getLKP_SRNO() == trbn.getDept()) {
															%>
															<option value="<%=lkbean.getLKP_SRNO()%>"
																selected="selected"><%=lkbean.getLKP_DESC()%></option>
															<%
																} else {
															%>
															<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>
															<%
																}
																	}
															%>
													</select></td>
												</tr>

												<tr class="alt">
													<td>Project</td>
													<td width="233"><select name="select" id="select"
														disabled="disabled">
															<option value="0">Select</option>
															<%
																ArrayList<Lookup> resultb = new ArrayList<Lookup>();
																	EmpOffHandler ekhp1 = new EmpOffHandler();
																	resultb = ekhp1.getGradeBranchList("PRJ");
																	for (Lookup lkbean : resultb) {

																		if (lkbean.getLKP_SRNO() == trbn.getBranch()) {
															%>
															<option value="<%=lkbean.getLKP_SRNO()%>"
																selected="selected"><%=lkbean.getLKP_DESC()%></option>
															<%
																} else {
															%>
															<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>
															<%
																}
																	}
															%>
													</select></td>
													<td width="125">Designation</td>
													<td width="232"><select name="Desgn" id="Desgn"
														disabled="disabled" style="width: 150px">
															<option value="none">Select</option>
															<%
																ArrayList<Lookup> getresult = new ArrayList<Lookup>();
																	LookupHandler lkhp = new LookupHandler();
																	getresult = lkhp.getSubLKP_DESC("DESIG");
																	for (Lookup lkbean : getresult) {
																		if (lkbean.getLKP_SRNO() == trbn.getDesg()) {
															%>
															<option value="<%=lkbean.getLKP_SRNO()%>"
																selected="selected"><%=lkbean.getLKP_DESC()%></option>
															<%
																} else {
															%>
															<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>
															<%
																}
																	}
															%>
													</select></td>
												</tr>
												<tr class="alt">
													<td>Grade</td>
													<td><select name="grade" id="grade"
														disabled="disabled">
															<option value="none">Select</option>
															<%
																ArrayList<Lookup> result2 = new ArrayList<Lookup>();
																	EmpOffHandler ekhp2 = new EmpOffHandler();
																	result2 = ekhp2.getGradeBranchList("GRADE");
																	for (Lookup lkbean : result2) {
																		if (lkbean.getLKP_SRNO() == trbn.getGrade()) {
															%>
															<option value="<%=lkbean.getLKP_SRNO()%>"
																selected="selected"><%=lkbean.getLKP_DESC()%></option>
															<%
																} else {
															%>
															<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>
															<%
																}
																	}
															%>
													</select></td>
													<td>PAN No</td>
													<td><input type="text" name="panno" id="panno"
														value="<%=trbn.getPanno()%>" readonly="readonly"></td>
												</tr>
												<tr class="alt">
													<td>PF No</td>
													<td colspan="3"><input type="text" name="pfno"
														id="pfno" value="<%=trbn.getPfno()%>" readonly="readonly"></td>
												</tr>


											</table>
										</center>
										</form>
										<br>
										<table width="1171" align="center" id="customers">
											<tr>
												<td colspan="10" align="center">Policy Information</td>
											</tr>
											<tr>
												<td width="1171">
													<table>

														<tr class="alt" style="font-size: 2;">
															<th width="10">S.No</th>
															<th width="144">Name</th>
															<th width="91">Type</th>
															<th width="90">Amount</th>
															<th width="87">Date</th>
															<th width="77">Premium Status</th>
															<th width="107">Premium Amount</th>
															<th width="108">Deduction Month</th>
															<th width="114">Maturity Date</th>
															<th width="114">Maturity Amount</th>
															<th width="132">Company Name</th>
															<th width="80">Update</th>

														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td>
													<div style="height: 200px; overflow-y: scroll; width: auto"
														id="div1">
														<table border="1" id="customers">
															<%
																for (PolicyBean pb : policylist) {

																		String str = pb.getEMPNO() + ":" + pb.getSRNO();
															%>
															<tr class="row" bgcolor="#FFFFFF" align="center">
																<td width="30"><%=pb.getSRNO()%></td>
																<td width="150"><%=pb.getPOLICY_NAME()%></td>
																<td width="91"><%=pb.getPOLICY_TYPE()%></td>
																<td width="90"><%=pb.getPOLICY_AMOUNT()%></td>
																<td width="80"><%=pb.getPOLICY_DATE()%></td>
																<td width="81"><%=pb.getPREMIUM_STATUS()%></td>
																<td width="114"><%=pb.getPREMIUM_AMOUNT()%></td>
																<td width="114"><%=pb.getDEDUCTION_MONTH()%></td>
																<td width="114"><%=pb.getMATURITY_DATE()%></td>
																<td width="114"><%=pb.getMATURITY_AMOUNT()%></td>

																<td width="114"><%=pb.getINS_COMP_NAME()%></td>
																<td width="80"><input type="button" value="Update"
																	onClick="modifyRec('<%=str%>')" /></td>


															</tr>
															<%
																}
															%>

														</table>
													</div>

												</td>
											</tr>
											<tr bgcolor="#2f747e">
												<td>
													<table id="customers">
														<tr align="right">
															<td width="1150" align="right" colspan="11"><input
																type="button" value="Add New Policy"
																onClick="addNewPolicy()"></td>
														</tr>
													</table>
												</td>
											</tr>
										</table>


										<%
											} else {
										%>
										<center>
											<form action="PolicyMasterServlet?action=policyinfo"
												method="Post" onSubmit="return TakeCustId()" name="form1"
												id="form1">
												<table border="1">
													<tr>
														<td>&nbsp;Enter Employee Name Or Emp-Id &nbsp;<input
															type="text" name="EMPNO" size="40" id="EMPNO"
															onClick="showHide()" title="Enter Employee Id / Name ">
														</td>
														<td valign="top"><input type="Submit" value=""
															class="form-submit" /></td>

													</tr>
													<tr></tr>
												</table>
											</form>
											<br>
											<form>
												<table width="773" id="customers">
													<tr class="alt">
														<th colspan="4">Salary Transaction</th>
													</tr>
													<tr class="alt">
														<td width="144">Employee Detail</td>
														<td colspan="3"><input type="text" size="10" value="">
															<input type="text"></td>
													</tr>
													<tr class="alt">
														<td width="144">SB A/c No</td>
														<td width="224"><input type="text"></td>
														<td width="119">Department</td>
														<td><select name="Depart" id="Depart">
																<option value="0">Select</option>
																<%
																	ArrayList<Lookup> getresult1 = new ArrayList<Lookup>();
																		LookupHandler lkhp1 = new LookupHandler();
																		getresult1 = lkhp1.getSubLKP_DESC("DEPT");
																		for (Lookup lkbean : getresult1) {
																%>
																<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>
																<%
																	}
																%>
														</select></td>
													</tr>
													<tr class="alt">
														<td height="32">Project</td>
														<td width="224"><select name="branch" id="branch">
																<option value="0">Select</option>
																<%
																	ArrayList<Lookup> resultb = new ArrayList<Lookup>();
																		EmpOffHandler ekhp1 = new EmpOffHandler();
																		resultb = ekhp1.getGradeBranchList("PRJ");
																		for (Lookup lkbean : resultb) {
																%>
																<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>
																<%
																	}
																%>
														</select></td>
														<td width="119">Designation</td>
														<td width="266"><select name="Desgn" id="Desgn"
															style="width: 80px">
																<option value="none">Select</option>
																<%
																	ArrayList<Lookup> getresult = new ArrayList<Lookup>();
																		LookupHandler lkhp = new LookupHandler();
																		getresult = lkhp.getSubLKP_DESC("DESIG");
																		for (Lookup lkbean : getresult) {
																%>
																<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>
																<%
																	}
																%>
														</select></td>

													</tr>
													<tr class="alt">
														<td>Grade</td>
														<td><select name="grade" id="grade">
																<option value="none">Select</option>
																<%
																	ArrayList<Lookup> result2 = new ArrayList<Lookup>();
																		EmpOffHandler ekhp2 = new EmpOffHandler();
																		result2 = ekhp2.getGradeBranchList("GRADE");
																		for (Lookup lkbean : result2) {
																%>
																<option value="<%=lkbean.getLKP_SRNO()%>"><%=lkbean.getLKP_DESC()%></option>
																<%
																	}
																%>
														</select></td>
														<td>PAN No</td>
														<td><input type="text"></td>


													</tr>
													<tr class="alt">
														<td>PF No</td>
														<td colspan="3"><input type="text"></td>


													</tr>

													<tr class="alt">

													</tr>
												</table>

											</form>
											<br>
											<form>
												<table width="1171" align="center" id="customers">

													<tr>
														<td width="1171">
															<table>

																<tr class="alt" style="font-size: 2;">
																	<th>S.No</th>
																	<th width="193">Name</th>
																	<th width="85">Type</th>
																	<th width="59">Amount</th>
																	<th width="52">Date</th>
																	<th width="105">Premium Status</th>
																	<th width="116">Premium Amount</th>
																	<th width="112">Deduction Month</th>
																	<th width="94">Maturity Date</th>
																	<th width="117">Maturity Amount</th>
																	<th width="116">Company Name</th>

																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td>
															<div
																style="height: 122px; overflow-y: scroll; width: auto"
																id="div1">
																<table border="1" id="customers">
																</table>
															</div>
														</td>
													</tr>
													<tr bgcolor="#2f747e">
														<td width="800" align="right">&nbsp;</td>
													</tr>
												</table>
											</form>
										</center>

										<%
											}
										%>

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