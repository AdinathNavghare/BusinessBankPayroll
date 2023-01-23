<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
<%@page import="payroll.Model.CodeMasterBean"%>
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
			alert("Please Insert Employee Id/Name ");
			document.getElementById("EMPNO").focus();
			return false;
		}
		var atpos = EMPNO.indexOf(":");
		if (atpos < 1) {
			alert("Please Select Correct Employee Id/Name");
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
	
	function showDiv() {

		document.getElementById("addInvest").hidden = false;
	}
	function Validate()
	{
	if(document.getElementById("trancd").selectedIndex == 0)
		{
		alert("Please Select Investment Detail Information");
		document.getElementById("trancd").focus;
		return false;
		
		}
	if(document.getElementById("invstamnt").value == 0)
		{
		alert("Please Enter Investment Amount");
		document.getElementById("invstamnt").focus;
		return false;
		
		}
		 
	}
</script>
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
				<h1>Investment Details</h1>
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
										String action = request.getParameter("action") == null ? ""
												: request.getParameter("action");
										ArrayList<TranBean> trlist = (ArrayList<TranBean>) session
												.getAttribute("trlist");
										Date dt1 = new Date();
										SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
										
									%>
									<center>
										<%
											if (action.equalsIgnoreCase("getdata")) {
												TransactionBean trbn = new TransactionBean();
												trbn = (TransactionBean) request.getAttribute("trbn");
												
										%>
										<center>
											<form action="InvestDetailServlet" method="Post"
												onSubmit="return TakeCustId()" name="form1" id="form1">
												<table>
													<tr>
														<td>Enter Employee Name Or Emp-Id<input type="text"
															name="EMPNO" size="40" id="EMPNO" onClick="showHide()"
															title="Enter Employee Id / Name ">
														</td>
														<td valign="top"><input type="Submit" value=""
															class="form-submit" /></td>

													</tr>
													<tr></tr>
												</table>
											</form>
											<br>

											<table width="768" id="customers">
												<tr class="alt">
													<td width="105">Employee Detail</td>
													<td colspan="3"><input type="text" size="10"
														name="empno1" id="empno1" 
														value="<%=trbn.getEmpno()%>" readonly="readonly" >
														<input type="text" size="30"
														value="<%=trbn.getEmpname()%>"  readonly="readonly"></td>
												</tr>
												<tr class="alt">
													<td width="84">SB A/c No</td>
													<td width="158"><input type="text" name="accno"
														id="accno" value="<%=trbn.getAccno()%>"
														readonly="readonly"></td>
													<td width="84">Department</td>
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
													<td width="158"><select name="select" id="select"
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
													<td width="77">Designation</td>
													<td width="158"><select name="Desgn" id="Desgn"
														disabled="disabled">
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
										<table width="595" align="center" id="customers">
											<tr>
												<td>Investment Detail Information</td>
											</tr>
											<tr>
												<td width="595">
													<table>

														<tr class="alt" style="font-size: 2;">
															<th>SRNO</th>
															<th width="139">Transaction Date</th>
															<th width="302">Investment Detail</th>
															<th width="181">Invest Amount</th>
															<th width="154">Action</th>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td>
													<div style="height: 199px; overflow-y: scroll; width: auto"
														id="div1">
														<table border="1" id="customers">
															<%
															String str = "";
																for (TranBean tb : trlist) {

																	str = tb.getEMPNO()+":"+tb.getSRNO();
															%>
															<tr class="row" bgcolor="#FFFFFF">
																<td width="48"><%=tb.getSRNO()%></td>
																<td width="129"><%=tb.getTRNDT()%></td>
																<td width="259"><%=CodeMasterHandler.getCDesc(tb.getTRNCD())%></td>
																<td width="169"><%=tb.getINP_AMT()%></td>
																<td width="89"><input type="button" value="Delete" onClick="window.location='InvestDetailServlet?str=<%=str%>'" />
																<input type="hidden" name="" id="" value="<%=str%>"></td>
															</tr>
															<%
																}
															%>

														</table>
													</div>

												</td>
											</tr>
											<tr bgcolor="#2f747e">
												<td width="595" align="right"><input type="button"
													value="Add Investment" onClick="showDiv()"></td>
											</tr>
										</table>


										<%
											} else {
										%>
										<center>
											<form action="InvestDetailServlet" method="Post"
												onSubmit="return TakeCustId()" name="form1" id="form1">
												<table>
													<tr>
														<td>&nbsp;Enter Employee Name Or Emp-Id &nbsp;<input type="text"
															name="EMPNO" size="40" id="EMPNO" onClick="showHide()"
															title="Enter Employee Id / Name ">
														</td>
														<td valign="top"><input type="Submit" value=""
															class="form-submit" /></td>

													</tr>
													<tr></tr>
												</table>
											</form>
											<br>
											<form>
												<table width="768" id="customers">
													<tr class="alt">
														<td width="105">Employee Detail</td>
														<td colspan="3"><input type="text" size="10" value="">
															<input type="text" size="30"></td>
													</tr>
													<tr class="alt">
														<td width="84">SB A/c No</td>
														<td width="158"><input type="text"></td>
														<td width="84">Department</td>
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
														<td>Project</td>
														<td width="158"><select name="branch" id="branch">
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
														<td width="77">Designation</td>
														<td width="158"><select name="Desgn" id="Desgn">
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
											<table width="595" align="center" id="customers">

												<tr>
													<td width="595">
														<table>

															<tr class="alt" style="font-size: 2;">
															<tr class="alt" style="font-size: 2;">
																<th>Sr.No.</th>
																<th width="117">Transaction Date</th>
																<th width="192">Investment Detail</th>
																<th width="136">Investment Amount</th>
																<th width="88">Action</th>
															</tr>


														</table>
													</td>
												</tr>
												<tr>
													<td>
														<div
															style="height: 199px; overflow-y: scroll; width: auto"
															id="div1">
															<table border="1" id="customers">


															</table>
														</div>

													</td>
												</tr>
												<tr bgcolor="#2f747e">
													<td>&nbsp;</td>
												</tr>
											</table>
										</center>

										<%
											}
										%>
									
							</div>
							<div id="addInvest" hidden="true">
								<center>
									<form action="InvestDetailServlet" method="post" onSubmit="return Validate()" >
										<table id="customers">
											<tr class="alt">
												<td>InvestMent In</td>
												<td><select name="trancd" id="trancd">  
														<option value="0">Select</option>
														<%
											ArrayList<CodeMasterBean> list=new ArrayList<CodeMasterBean>();
										    CodeMasterHandler CMH = new CodeMasterHandler();
										    list = CMH.gettrancd();
										for(CodeMasterBean CMHbean : list)
											{
										%>
														<option value="<%=CMHbean.getTRNCD()%>"><%=CMHbean.getDISC()%></option>
														<%} %>
												</select></td>
												<td>Amount</td>
												<td><input type="text" name="invstamnt" id="invstamnt"></td>
												<td>Transaction Date</td>
												<td><input type="text" value="<%=format.format(dt1) %>"
													name="trndate" id="trndate" readonly="readonly"></td>
											<tr align="center" class="alt">
												<td align="center" colspan="6"><input type="submit"
													value="Add Investment" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
													type="button" value="Cancel"
													onclick="document.getElementById('addInvest').hidden=true; return false;" /></td>
											</tr>
										</table>
										<input type="hidden" name="empno2" id="empno2" value="<%=request.getAttribute("empno1")%>">
										<input type="hidden" name="action" id="action" value="add">
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