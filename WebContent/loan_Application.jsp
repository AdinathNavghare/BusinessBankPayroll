
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="oracle.sql.OffsetDST"%>
<%@page import="payroll.DAO.LoanHandler"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@page import="payroll.Model.LoanBean"%>
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
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="java.util.*"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy; DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
<script type="text/javascript" src="js/Loan_Print.js"></script>
<script src="js/jquery/jquery-1.4.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/Loan_Cal_Outer.js"></script>

<!--  date picker script -->
<link rel="stylesheet" href="css/datePicker.css" type="text/css" />
<script src="js/jquery/date.js" type="text/javascript"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script src="js/jquery/jquery.datePicker.js" type="text/javascript"></script>
<script type="text/javascript" src="js/datetimepicker_banking.js"></script>



<%
	
 String empno="";
 empno =(String)session.getAttribute("empno")==null?session.getAttribute("EMPNO").toString():session.getAttribute("empno").toString();
 
 String action = request.getParameter("action")== null?"":request.getParameter("action");
 
 String flag=request.getParameter("flag")==null?"0":request.getParameter("flag");
	
 CodeMasterHandler cmh=new CodeMasterHandler(); 
 EmpOffHandler eoh=new EmpOffHandler();
 EmpOffBean eob= eoh.getEmpOfficAddInfo(empno);
 int prjsrno=eob.getPrj_srno();
 LoanHandler lh=new LoanHandler();
 EmployeeHandler eh=new EmployeeHandler();
 session.setAttribute("loan_app", "1");
 try
 {
 if(action.equalsIgnoreCase(""))
 {
	 session.setAttribute("empNameNO",""); 
 }
 
 String str="";
 int emp_no=0;
 int loanno=0;
 int loancode=0;
 ArrayList<LoanBean> trlist=new  ArrayList<LoanBean>(); 
 
 if(action.equalsIgnoreCase(""))
 {
	 action="getdata";
	 trlist=lh.getTranInfo(empno);
	 session.setAttribute("trlist",trlist);
	 session.setAttribute("empno",empno);
	 
	 EmployeeBean eb=eh.getEmployeeInformation(empno);
	 
	 session.setAttribute("empNameNO",eb.getFNAME()+" "+eb.getLNAME()+" : "+eb.getEMPCODE()+" : "+empno);
	 
 }
 
 if(action.equalsIgnoreCase("getdata")||action.equalsIgnoreCase("edit"))
 {
	 trlist = (ArrayList<LoanBean>)session.getAttribute("trlist");
	 System.out.println("Size of list====" + trlist.size());
		
 }
 if(action.equalsIgnoreCase("edit"))
 {
	//String  key=request.getParameter("key")==null?"":request.getParameter("key");
	   String  key[]=request.getParameter("key").split(":");
	   loanno=Integer.parseInt(key[0]);
	   emp_no=Integer.parseInt(key[1]);
	   loancode=Integer.parseInt(key[2]); 
	   System.out.println(loanno+"==" + emp_no+"=="+loancode);
		
 }

%>


<script type="text/javascript">
	function inputLimiter(e, allow) {
		var AllowableCharacters = '';
		if (allow == 'Numbers') {
			AllowableCharacters = '.1234567890';
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

	function parseMyDate(s) {
		var m = [ 'jan', 'feb', 'mar', 'apr', 'may', 'jun', 'jul', 'aug',
				'sep', 'oct', 'nov', 'dec' ];
		var match = s.match(/(\d+)-([^.]+)-(\d+)/);
		var date = match[1];
		var monthText = match[2];
		var year = match[3];
		var month = m.indexOf(monthText.toLowerCase());
		return new Date(year, month, date);
	}

	function monthDiff(d1, d2) {
		var months;
		months = (d2.getFullYear() - d1.getFullYear()) * 12;
		months -= d1.getMonth() + 1;
		months += d2.getMonth();
		return months <= 0 ? 0 : months;
	}

	function calculateMonth(frm) {
		var end = document.forms[frm]["EndDate"].value;
		var start = document.forms[frm]["startDate"].value;
		if (start != "") {
			end = parseMyDate(end);
			start = parseMyDate(start);

			if (end > start) {

				months = end.getMonth() - start.getMonth()
						+ (12 * (end.getFullYear() - start.getFullYear()));

				if (end.getDate() < start.getDate()) {
					months--;
				}
				

				document.forms[frm]["pay_month"].value = months;

			} else {
				alert("Please select valid date !");
				document.forms[frm]["EndDate"].value = "";
				document.forms[frm]["EndDate"].focus();
			}
		} else {
			alert("Please select start date !");
		}

	}

	
	function fun()
	{		
		document.forms["editForm"]["startDate"].value=document.forms["editForm"]["SD"].value;
		document.forms["editForm"]["EndDate"].value=document.forms["editForm"]["ED"].value;
		
	}
	
	
	function count_instalmnt(frm) {

		frm=frm.name;
		
		var amt = parseFloat(document.forms[frm]["loan_amt"].value);

		//var perc=parseFloat(document.getElementById("loan_per").value);
		var perc = parseFloat(document.forms[frm]["loan_per"].value);
		if (perc == 0) {
			document.forms[frm]["month_inst"].value = amt;
		}
		var mon = parseFloat(document.forms[frm]["pay_month"].value);

		var oneDay = 24 * 60 * 60 * 1000; // hours*minutes*seconds*milliseconds
		var end = document.forms[frm]["EndDate"].value;
		var start = document.forms[frm]["startDate"].value;
		
		end = parseMyDate(end);
		start = parseMyDate(start);
		
		var Days = Math.ceil(((end.getTime() - start.getTime()) / (oneDay)));
		
		
			if (mon == 0) {
				mon = 1;
				document.forms[frm]["pay_month"].value = "1";
			}
		 {
			mon = Math.round(Days / 30);
			
			document.forms[frm]["pay_month"].value = mon;
		}

		rate = perc / 100;
		var monthly = rate / 12;

		var payment = ((amt * monthly) / (1 - Math.pow((1 + monthly), -mon)));
		var total = payment * mon;
		
		//var interest=total-amt;
		if (isNaN(payment.toFixed(2))) {
			document.forms[frm]["month_inst"].value = amt;
		} else {
			document.forms[frm]["month_inst"].value = payment.toFixed(2);

		}
		
		if(perc==0){document.forms[frm]["month_inst"].value =Math.ceil(amt/mon);}
		 /* var vr = confirm("Do you want to Edit Record");
			if (vr) { 
		return true;
			}
			else
				{
				return false;
				} */
	}

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

	function addNew() {
		var e = document.getElementById("loan");
		if (e.style.display == 'none') {
			e.style.display = 'block';
		}
		var e1 = document.getElementById("edit");

		e1.style.display = 'none';

	}

	function addnewcancel() {
		var e = document.getElementById("loan");
		e.style.display = 'none';

	}
	function editcancel() {
		var e = document.getElementById("edit");

		e.style.display = 'none';

	}

	function modifyRec(key) {

		 var vr = confirm("Do you want to Edit Record?");
		if (vr) { 
			window.location.href = "loan_Application.jsp?action=edit&key="+key;

		 } 
	}

	function validation() {

		var startdate = document.addForm.startDate.value;
		var enddate = document.addForm.EndDate.value;

		startdate = startdate.replace(/-/g, "/");
		enddate = enddate.replace(/-/g, "/");

		if (document.addForm.loan_code.value == "Default") {
			alert("please Select Loan_no");
			document.addForm.loan_code.focus();
			return false;
		}
		if (document.addForm.loan_no.value == "") {
			alert("please Enter Loan Number");
			document.addForm.loan_no.focus();
			return false;
		}
		if (document.addForm.loan_amt.value == "") {

			alert("please enter Loan Amount");
			document.addForm.loan_amt.focus();
			return false;
		}

		if (document.addForm.startDate.value == "") {

			alert("Please Select Start date");
			document.addForm.startDate.focus();
			return false;

		}
		if (document.addForm.EndDate.value == "") {

			alert("Please Select End Date");
			document.addForm.EndDate.focus();
			return false;
		}

		var d1 = new Date(startdate);

		var d2 = new Date(enddate);

		if (d1.getTime() > d2.getTime()) {
			alert("Invalid Date Range!\n start Date cannot greater than End Date!")
			document.addForm.startDate.focus();
			return false;
		}

		if (document.addForm.month_inst.value == "") {
			alert(" please Enter the Monthly InstallMent");
			document.addForm.month_inst.focus();
			return false;

		}

		if (document.addForm.loan_per.value == "") {

			alert("please enter Loan Percentage");
			document.addForm.loan_per.focus();
			return false;
		}
		if (document.addForm.bank_name.value == "") {

			alert("please enter bank Name");
			document.addForm.bank_name.focus();
			return false;
		}
		/* if (document.addForm.sancBy.value == "") {

			alert("please enter Sanction By Name");
			document.addForm.sancBy.focus();
			return false;
		} */

		 var vr = confirm("Do you want to Save Loan Record");
		if (vr) { 
			return true;
		}
		else
			{
			return false;
			}
		
	
	
	}
	function checkFlag() {
		var f = parseInt(document.getElementById("flag").value);
		if (f ==1) {
			alert("Record Saved Successfully");
		}
		if (f ==2) {
			alert("Selected Loan Code alreay Exist");
		}
		if (f ==3) {
			alert("Record Updated Successfully");
		}
		if (f ==4) {
			alert("Error in Updating Record");
		}
		if(f==(-1))
		{
		alert("Sorry ! Loan already taken for these Dates !");
		}

	}
	function rset() {

		document.getElementById('EMPNO').value = '';

	}
	function loancal() {

		window.location.href = "Loan_calculator.jsp";

	}
	function emploancal() {

		window.location.href = "Emp_Loan_Cal.jsp";

	}
</script>





<script>
	jQuery(function() {
		var prj_srno=document.getElementById("prj_srno").value;
		$("#EMPNO").autocomplete("ProjectEmpList.jsp?prj_srno="+prj_srno);
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

</head>

<body style="overflow: hidden;" onLoad="checkFlag()">
	<%@include file="mainHeader.jsp"%>
	<!--  start nav-outer-repeat................................................................................................. START -->

	<%@include file="subHeader.jsp"%>

	<!-- start content-outer ........................................................................................................................START -->
	<div id="content-outer" style="overflow-y: auto; max-height: 82%;">
		<!-- start content -->
		<div id="content">

			<!--  start page-heading -->
			<div id="page-heading">
				<h2>Loan Application</h2>
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
									<form action="LoanServlet" method="get"
										onSubmit="return TakeCustId()">
										<table border="1">
											<tr>
												<td> &nbsp;Enter Employee Name Or Emp-Id &nbsp;&nbsp;<input
													type="text" name="EMPNO" size="30" id="EMPNO"
													onClick="rset()"
													value="<%=session.getAttribute("empNameNO") == null ? "": session.getAttribute("empNameNO")%>"
													title="Enter Employee Id / Name "> &nbsp; 
												
												<input type="hidden" name="action" value="getdata">
												<td valign="top"><input type="submit" value=""
													class="form-submit" /></td> 

											</tr>
											<tr></tr>
										</table>
									</form>
									<input type="hidden" name="prj_srno" id="prj_srno" value="<%=prjsrno%>">
									<br>



									<table width="750" align="center" id="customers">
										<tr class="alt">
											<td align="right"><input type="button"
												value="Loan Calculator" onclick="loancal()" /></td>
										</tr>
										<tr>
											<td>
												<table bgcolor="#2f747e">

													<tr style="font-size: 2;">
														<!-- <th width="100">Loan Code</th>
					<th width="100">Emp No</th>
					<th width="104">Int. Rate</th>
					<th width="86">Start Date</th>
					<th width="90">End Date</th>
					<th width="80">Install Amt</th>
					<th width="53">Action</th> -->
														<th width="100">Loan Code</th>
														<th width="100">Emp Code</th>
														<th width="100">Int. Rate</th>
														<th width="100">Start Date</th>
														<th width="100">End Date</th>
														<th width="100">Total Months</th>
														<th width="100">Install Amt</th>
														<th width="100">Loan Amt</th>
														<th width="100">Action</th>

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
															if (action.equalsIgnoreCase("getdata")
																		|| action.equalsIgnoreCase("edit")) {
																	for (LoanBean lb : trlist) {
																		System.out.println("Size of list=" + trlist.size());
																		str = lb.getLoan_no() + ":" + lb.getEMPNO() + ":"
																				+ lb.getLoan_code();
														%>
														<tr class="row" bgcolor="#FFFFFF">
															<%-- <td width="100"><%=lb.getLoan_code() %></td>
					<td width="100"><%=lb.getEMPNO() %></td>
					<td width="100"><%=lb.getLoan_per() %></td>
					<td width="90"><%=lb.getStart_date() %></td>
					<td width="90"><%=lb.getEnd_date() %></td>
					<td width="90"><%=lb.getMonthly_install() %></td>
					<td width="81" ><input type="button" value="Edit" onClick="modifyRec('<%=str%>')"></td> --%>
															<td width="100">
																<%
																	CodeMasterBean cmb = cmh.getCodeDetails(lb.getLoan_code());
																%>
																<%=cmb.getDISC()%></td>
															<td width="100"><%=EmployeeHandler.getEmpcode(lb.getEMPNO())%></td>
															<td width="100"><%=lb.getLoan_per()%></td>
															<td width="100"><%=lb.getStart_date()%></td>
															<td width="100"><%=lb.getEnd_date()%></td>
															<td width="100"><%=lb.getTotal_month()%></td>
															<td width="100"><%=lb.getMonthly_install()%></td>
															<td width="100"><%=lb.getLoan_amt()%></td>
															<td width="100" align="center">
															
															<% if(lb.getACTIVE().equalsIgnoreCase("Y")) 
															{%>
															ACTIVE
															<%}else
																{%>
															<input type="button"
																value="Edit" onClick="modifyRec('<%=str%>')">
																
																<%} %>
																</td>
														</tr>
														<%
															}
																}
														%>
													</table>
												</div>
											</td>
										</tr>
										<%
											if (action.equalsIgnoreCase("getdata")
														|| action.equalsIgnoreCase("edit")) {
										%>
										<tr>
											<td align="left" bgcolor="#2f747e"><input type="button"
												value="Add New" onClick="addNew()"></td>
										</tr>
										<%
											} else {
										%>
										<tr>
											<td align="right" bgcolor="#2f747e">&nbsp;</td>
										</tr>
										<%
											}
										%>
									</table>


									<br>

									<div id="loan" style="display: none">
										<table >
											<tr valign="top">
												<td align="center">
													<form name="addForm" action="LoanServlet?action=addTran"
														method="post" onSubmit="return validation()">

														<table id="customers">
															<tr class="alt">
																<th colspan="6">Add New Loan</th>
															</tr>
															<tr>
																<td>Loan Code&nbsp; <font color="red"><b>*</b></font></td>
																<td>
																	<%
																		CodeMasterHandler cdmh = new CodeMasterHandler();
																			ArrayList<CodeMasterBean> cdlist = new ArrayList<CodeMasterBean>();
																			cdlist = cdmh.getLoancdList();
																	%> <select name="loan_code" id="loan_code" style="width: 150px">
																		<option value="Default" selected="selected">Select</option>
																		<%
																			for (CodeMasterBean cd : cdlist) {
																		%>
																		<option value="<%=cd.getTRNCD()%>"><%=cd.getDISC()%></option>

																		<%
																			}
																		%>
																</select>


																</td>
																<td>Emp Code&nbsp; <font color="red"><b>*</b></font></td>
																<td>
																
																<input value="<%=EmployeeHandler.getEmpcode(Integer.parseInt(session.getAttribute("empno").toString()))%>" readonly="readonly">
																
																<input name="empno" id="empno" type="hidden"
																	maxlength="14"
																	value="<%=session.getAttribute("empno").toString()%>"
																	readonly="readonly"></td>
																<td>Loan Amt&nbsp; <font color="red"><b>*</b></font></td>
																<td><input name="loan_amt" id="loan_amt"
																	type="text" maxlength="14"
																	onkeypress="return inputLimiter(event,'Numbers')"
																	onkeyup="count_instalmnt(this.form)"></td>

															</tr>
															<tr>
																<td>Loan Number&nbsp; <font color="red"><b>*</b></font></td>
																<td><input type="text" name="loan_no" id="loan_no" value="<%=lh.getMaxLoanNo()%>" readonly="readonly" /></td>
																<td>Start Date&nbsp; <font color="red"><b>*</b></font></td>
																<td><input size="14" name="startDate"
																	id="startDate" type="text" value=""
																	onchange="calculateMonth('addForm'),count_instalmnt(this.form)"
																	readonly="readonly" onchange="calculateMonth('addForm')">&nbsp;&nbsp;
																	<img src="images/cal.gif"
																	style="vertical-align: middle; cursor: pointer;"
																	onclick="javascript:NewCssCal('startDate', 'ddmmmyyyy')" />
																</td>

																<td>End Date&nbsp; <font color="red"><b>*</b></font></td>
																<td><input size="14" name="EndDate" id="EndDate"
																	type="text" value=""
																	onchange="calculateMonth('addForm'),count_instalmnt(this.form)"
																	readonly="readonly"> &nbsp;&nbsp;<img
																	src="images/cal.gif"
																	style="vertical-align: middle; cursor: pointer;"
																	onClick="javascript:NewCssCal('EndDate', 'ddmmmyyyy'),count_instalmnt(this.form)" />
																</td>


															</tr>
															<tr>
																<td>Loan Per.&nbsp; <font color="red"><b>*</b></font></td>
																<td><input name="loan_per" id="loan_per"
																	type="text" value="0"
																	onkeypress="return inputLimiter(event,'Numbers')"
																	onkeyup="count_instalmnt(this.form)" maxlength="14"></td>
																<td>Number of Payments(in Months):</td>
																<td><input name="pay_month" id="pay_month"
																	type="text" value="0" maxlength="14"
																	readonly="readonly"></td>
																<td>Monthly Installments</td>
																<td><input name="month_inst" id="month_inst"
																	type="text" maxlength="14" readonly="readonly"></td>

															</tr>
															<tr align="center" valign="middle">

																<td>Sanction By&nbsp; <font color="red"><b>*</b></font></td>
																<td align="left"><input name="sancBy" id="sancBy"
																	type="text" maxlength="14"  disabled="disabled"/></td>
																<td>Bank Name&nbsp; <font color="red"><b>*</b></font></td>
																<td><input name="bank_name" id="bank_name"
																	type="text" maxlength="14"  ></td>
																<td>Active&nbsp; <font color="red"><b>*</b></font></td>
																<td> <input type="radio" name="active" value="N" checked="checked"/> No</td>
															</tr>

															<!-- <tr><td>
     <div class="loan_pmt" id="pmt"></div>
			<div class="loan_out" id="det"></div>
      
   </td></tr> -->
															<tr align="center" valign="middle">

																<td colspan="6"><input type="button"
																	value="View Installments" onClick=" return check()" />
																	<input type="submit" name="Submit" value="Save" /> <input
																	type="button" value="Cancel" onClick="addnewcancel()" /></td>
															</tr>
														</table>

														<input type="hidden" name="flag" id="flag"
															value="<%=flag%>">
													</form>
												</td>
											</tr>

											<tr>
												<td align="center">
													<div class="loan_pmt" id="pmt"></div>
													<div class="loan_out" id="det"></div>
												</td>
											</tr>

										</table>
									</div>

									<%
										if (action.equalsIgnoreCase("edit")) {
												int x = 1;
												for (LoanBean lb : trlist) {
													int e = lb.getEMPNO();
													int loannum = lb.getLoan_no();
													if (emp_no == e && loanno == loannum)
													{
														String mode="";
														%>
									
									<div id="edit">

										<table>
											<tr valign="top">
												<td align="center">
													<form name="editForm" id="editForm" action="LoanServlet?action=update"
														method="post">


<%if(lb.getACTIVE().equalsIgnoreCase("Y"))
{
	 mode=" readonly='readonly' ";
%>
<script type="text/javascript">
window.alert("Sorry ! You can't edit activated loan.");
</script>
<%
}
%>


														<table id="customers">
															<tr class="alt">
																<th colspan="6">Edit Loan Entry</th>
															</tr>
															<tr>
																<td>Loan Code</td>
																<td>
																
																<%
																		 cdmh = new CodeMasterHandler();
																			 cdlist = new ArrayList<CodeMasterBean>();
																			cdlist = cdmh.getLoancdList();
																	%> <select name="loan_code" id="loan_code" style="width: 150px">
																		
																		<%
																			for (CodeMasterBean cd : cdlist) {
																				
																				
																				if(lb.getLoan_code()==cd.getTRNCD())
																				{
																					%>
																					<option value="<%=cd.getTRNCD()%>" selected="selected"><%=cd.getDISC()%></option>

																					<%
																				}else
																				{
																		%>
																		<option value="<%=cd.getTRNCD()%>"><%=cd.getDISC()%></option>

																		<%
																				}}
																		%>
																</select>
																
																<%-- <input value="<%= CodeMasterHandler.getCDesc(lb.getLoan_code())%>" readonly="readonly">
																<input name="loan_code" type="hidden"
																	id="loan_code" value="<%=lb.getLoan_code()%>"
																	maxlength="14" readonly="readonly"> --%></td>
																 <td>Emp Code</td>
																<td>
																<input value="<%=EmployeeHandler.getEmpcode(Integer.parseInt(session.getAttribute("empno").toString()))%>" readonly="readonly">
																<input name="empno" id="empno" type="hidden"
																	value="<%=lb.getEMPNO()%>"
																	></td> 
																<td>Loan Amt</td>
																<td><input name="loan_amt" id="loan_amt"
																	type="text" value="<%=lb.getLoan_amt()%>"
																	maxlength="14" <%=mode%>
																	onkeypress="return inputLimiter(event,'Numbers')"
																	onkeyup="count_instalmnt(this.form)"></td>

															</tr>
															<tr>
																<td>Loan Number</td>
																<td><input type="text" name="loan_no" id="loan_no"
																	value="<%=lb.getLoan_no()%>" readonly="readonly" /></td>
																<td>Start Date</td>
																<td><input size="14" name="startDate"
																	id="startDate" type="text" onchange="fun()"
																	value="<%=lb.getStart_date()%>" readonly="readonly">&nbsp;&nbsp; 
																	<input type="hidden" id="SD" value="<%=lb.getStart_date()%>"  onchange="fun(),calculateMonth('editForm')"> 
																	<img src="images/cal.gif"
																	style="vertical-align: middle; cursor: pointer;" 
																	onClick="javascript:NewCssCal('SD', 'ddmmmyyyy'),count_instalmnt(this.form)" />
																	
																</td>

																<td>End Date</td>
																<td><input size="14" name="EndDate" id="EndDate" readonly="readonly" onchange="fun(),count_instalmnt(this.form),calculateMonth('editForm')"
																	type="text" value="<%=lb.getEnd_date()%>">
																	&nbsp;&nbsp;
																	<input type="hidden" id="ED" value="<%=lb.getEnd_date()%>" onchange="fun(),calculateMonth('editForm')">
																	<img src="images/cal.gif" 
																	style="vertical-align: middle; cursor: pointer;" 
																	onClick="javascript:NewCssCal('ED', 'ddmmmyyyy'),count_instalmnt(this.form)" />
																	 
																</td>



															</tr>
															<tr>
																<td>Loan Per.</td>
																<td><input name="loan_per" id="loan_per"
																	type="text" maxlength="14"
																	value="<%=lb.getLoan_per()%>"  <%=mode%>
																	onkeypress="return inputLimiter(event,'Numbers')"
																	onkeyup="count_instalmnt(this.form)"></td>
																<td>Number of Payments(in Months):</td>
																<td><input name="pay_month" id="pay_month"
																	type="text" value="<%=lb.getTotal_month()%>"
																	maxlength="14" readonly="readonly" onchange="count_instalmnt(this.form)"></td>
																<td>Monthly Installments</td>
																<td><input name="month_inst" id="month_inst"
																	type="text" value="<%=lb.getMonthly_install()%>"
																	maxlength="14" readonly="readonly"></td>

															</tr>
															<tr align="center" valign="middle">
																<td>Sanction By</td>
																<td><input name="sancBy" id="sancBy" type="text"
																	value="<%=lb.getSanctionby()%>" disabled="disabled" /></td>
																<td>Bank Name</td>
																<td><input name="bank_name" id="bank_name"
																	type="text" maxlength="14"
																	value="<%=lb.getBank_name()%>" disabled="disabled"></td>
																<td>Active</td>
																<td>
																
																
																
																	<input type="radio" name="active" value="N" <%if(("N").equalsIgnoreCase(lb.getACTIVE())){%> checked="checked"<%} %>/> No</td>
																	
																
															</tr>

															<tr align="center" valign="middle">
																<td colspan="6">
																	<!-- <input type="button" value="View Installments" onClick=" return check()" /> -->
														<%			
																	if(lb.getACTIVE().equalsIgnoreCase("N"))
														{
																		 %>		
																	<input type="submit" name="Submit" value="Save" onclick="count_instalmnt(this.form)"/> 
																	
																	<%
														}
																	%>
																	
																<input	type="button" value="Cancel" onClick="editcancel()" />
																</td>
															</tr>
														</table>

													</form>
												</td>
											</tr>
											
										</table>

									</div>
									
									
									<%
										}

												}
											}
 }
 catch(Exception e)
 {
	 %>
	 <script type="text/javascript">
	 window.location.href="login.jsp";
	 </script>
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