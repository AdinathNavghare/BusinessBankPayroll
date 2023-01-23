<%@page import="payroll.DAO.EmpAttendanceHandler"%>
<%@page import="payroll.Model.LoanAppBean"%>
<%@page import="payroll.Model.LoanBean"%>
<%@page import="payroll.DAO.LoanAppHandler"%>
<%@page import="payroll.DAO.LoanHandler"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.Model.TranBean"%>
<%@page import="payroll.DAO.RoleDAO"%>
<%@page import="java.util.Calendar"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="payroll.Model.ProjectBean"%>
<%@page import="payroll.DAO.ProjectListDAO"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy Loan Application </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript" src="js/Loan_Cal_Outer.js"></script>


<script>
$(function()
		{
	jQuery(function() {
		
		$("#imag").click(function(){
			document.getElementById("calBorder").style.display='block';
		});
		
		
		$("#imag2").click(function(){
			document.getElementById("calBorder").style.display='block';
		});
	});
		});
	
	
	
</script>

<script type="text/javascript">


function getTranDetails() {
	
	var proj=document.getElementById("pp").value;
	
		
			var res = proj.indexOf(":"); 
			if(proj=="")
				{			
				alert("Please Select Project !");
				}
			else
				{
				if(res<0)
					{
					//alert("Please Select Project !");
					document.getElementById("pp").value="";
					document.getElementById("pp").focus();
					}
				else
					{
			var p=proj.split(":");
			var prjCode = p[3];
			
				if(prjCode == ""){
					
				}
			
			else{
				
		proj=proj.replace(/ & /g," and ");
				window.location.href = "LoanApp.jsp?action=getdetails&prj="+prjCode;

			}
				}
			}
			
		}

function count_instalmnt(frm) {

	frm=frm.name;
	
	var amt = parseFloat(document.forms[frm]["loan_amt"].value);

	
	var perc = parseFloat(document.forms[frm]["loan_per"].value);
	if (perc == 0) {
		document.forms[frm]["month_inst"].value = amt;
	}
	var mon = parseFloat(document.forms[frm]["pay_month"].value);
	if (mon == "0") {
		mon = 1;
		document.forms[frm]["pay_month"].value = "1";
	}
	var oneDay = 24 * 60 * 60 * 1000; // hours*minutes*seconds*milliseconds
	var end = document.forms[frm]["EndDate"].value;
	var start = document.forms[frm]["startDate"].value;
	
	end = parseMyDate(end);
	start = parseMyDate(start);
	
	var Days = Math.ceil(((end.getTime() - start.getTime()) / (oneDay)));

		
	 {
		mon = Math.round(Days / 30);
		
		document.forms[frm]["pay_month"].value = mon;
		if(mon=="0"){
			mon = 1;
		document.forms[frm]["pay_month"].value = "1";	
		}
	}

	rate = perc / 100;
	var monthly = rate / 12;

	var payment = ((amt * monthly) / (1 - Math.pow((1 + monthly), -mon)));
	var total = payment * mon;
//	alert("amt    "+amt+"month   "+mon+"result     "+amt/mon);
	
	
	//var interest=total-amt;
	if (isNaN(payment.toFixed(2))) {
		document.forms[frm]["month_inst"].value = amt;
	} else {
		document.forms[frm]["month_inst"].value = Math.ceil( payment.toFixed(2));

	}
	
	if(perc==0)
	{document.forms[frm]["month_inst"].value = Math.ceil(amt/mon);}
	
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
	
	/* var amt = parseFloat(document.forms[frm]["loan_amt"].value);
	if(amt=="")
		{
		alert("Please enter The Loan Amount ");
		return false;
		} */
	
	var end = document.forms[frm]["EndDate"].value;
	var start = document.forms[frm]["startDate"].value;
	
	if(end=="")
		{
		document.getElementById("calBorder").style.display='none';
		}
	else
		{
		document.getElementById("calBorder").style.display='block';
		}
	
	
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
			 var mon = parseFloat(document.forms[frm]["pay_month"].value);
				if (mon == "0") {
					mon = 1;
					document.forms[frm]["pay_month"].value = "1";
				}	
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

function inputLimiter(e,allow) {
	  var AllowableCharacters = '';
	  if (allow == 'Numbers'){AllowableCharacters='1234567890';}
	  var k;
	  k=document.all?parseInt(e.keyCode): parseInt(e.which);
	  if (k!=13 && k!=8 && k!=0){
	    if ((e.ctrlKey==false) && (e.altKey==false)) {
	      return (AllowableCharacters.indexOf(String.fromCharCode(k))!=-1);
	    } else {
	      return true;
	    }
	  } else {			  
	    return true;
	  }
	}	



function redirect()
{
	 var type= document.getElementById("type").value;
       var proj = document.getElementById("project").value;
    
       var res = proj.indexOf(":"); 
		if(proj=="")
			{			
			//alert("Please Select Project !");
			}
		else
			{
			if(res<0)
				{
				//alert("Please Select Project !");
				document.getElementById("project").value="";
				document.getElementById("project").focus();
				}
			else
				{
		var p=proj.split("::");
		
		var prjCode = p[1];
		
			if(prjCode == ""){
				
			}
		
		else{
			
	proj=proj.replace(/ & /g," and ");
			window.location.href = "LoanApp.jsp?action=getdetails&prj="+prjCode+"&sList="+type;

		}
			}
		}
       

}

function redirect_url(type )
{  
	
	
	
	if(type=="PENDING"){
	
		window.location = "LoanApp.jsp?sList="+type;
	}else if(type=="SANCTION"){
		window.location = "LoanApp.jsp?sList="+type;
	}else if(type=="CANCEL"){
		window.location = "LoanApp.jsp?sList="+type;
	}else if(type=="NIL"){
		window.location = "LoanApp.jsp?sList="+type;
	}
	else 
		window.location = "LoanApp.jsp?sList="+type;

}


function validation() {

	var startdate = document.addForm.startDate.value;
	var enddate = document.addForm.EndDate.value;

	startdate = startdate.replace(/-/g, "/");
	enddate = enddate.replace(/-/g, "/");
	
	var typ = document.getElementById("emplist").value;
	if(typ==0){
		alert("please Select Employee");
		document.addForm.emplist.focus();
		return false;
	}

	/* if (document.addForm.loan_code.value == "Default") {
		alert("please Select Loan Code");
		document.addForm.loan_code.focus();
		return false;
	} */

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
		alert("Invalid Date Range!\n start Date cannot greater than End Date!");
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
	

	var empName=document.addForm.emplist.value;
	var e=empName.split(":");
	var empDate= e[1];
	empDate=parseMyDate(empDate);
	var start=document.addForm.startDate.value; 
	start = parseMyDate(start);
	
	if(empDate.getTime()>start.getTime())
		{
		alert("Start date can not be earlier than start of current salary month");
		return false;
		}
	
//	return false;
	//alert("Requested amount : "+document.addForm.loan_amt.value+"  Total Pay including Interest : "+(document.addForm.month_inst.value*document.addForm.pay_month.value));
	
	var check= confirm("Do you want to apply for loan?");
	
	if (check) { 
		return true;
	}
	
	else
		{
		return false;
		}
	


}


</script>

<script>
	jQuery(function() {
		$("#pp").autocomplete("projlist.jsp");
	});
	
	function checkLoan() {
		
		 var proj = document.getElementById("project").value;
		
			var p=proj.split("::");
			
			var prjCode = p[1];
		
		var f = parseInt(document.getElementById("flag").value);

		if (f == 1) {
			alert("Tranaction Saved Successfully");

		}
		if (f == 2) {
			alert("Employee Already Exist");
		}
		if (f == 3) {
			alert("Loan applied Successfully");
			window.location.href = "LoanApp.jsp?action=getdetails&prj="+prjCode;
		}
		if(f==4){
			alert("Record is not added.Please apply again");
			
			
		}
	
	}

	
	
	<%
	LoanAppHandler loanAppHandler=new LoanAppHandler();
	String emp_no=request.getParameter("emp_info")==null?"":request.getParameter("emp_info");
	String proj=request.getParameter("prj")==null?"":request.getParameter("prj");
	EmpOffBean eoffbn = new EmpOffBean();

	LoanHandler la=new LoanHandler();
	
	String status="ALL";
	status=request.getParameter("sList")==null?"ALL":request.getParameter("sList");
	
	
	
	RoleDAO obj1=new RoleDAO();
	String roleId=obj1.getrole(Integer.parseInt(session.getAttribute("UID").toString()));
	LookupHandler lookuph= new LookupHandler();

	ArrayList<LoanAppBean> LoanList = new ArrayList<LoanAppBean>();
	LoanAppHandler loanHandler = new LoanAppHandler();
	SimpleDateFormat dateFormat;

	ArrayList<TranBean> projEmpNolist = new ArrayList<TranBean>();
	ArrayList<TranBean> projEmpNmlist = new ArrayList<TranBean>();
	

	EmployeeHandler emph = new EmployeeHandler();
	EmployeeBean ebean = new EmployeeBean();
	EmployeeBean ebean1 = new EmployeeBean();
	String [] payable=null;
	ArrayList<LoanAppBean> list= new ArrayList<LoanAppBean>();
	if(!emp_no.equalsIgnoreCase(""))
	{
		
		 
	}
	
	int eno = (Integer)session.getAttribute("EMPNO");
	EmpOffHandler eoffhdlr = new EmpOffHandler();
	eoffbn = eoffhdlr.getEmpOfficAddInfo(Integer.toString(eno));

	
	int site_id=(eoffbn.getPrj_srno());
	String employeeNumberString=session.getAttribute("EMPNO").toString();
	int empNo = Integer.parseInt(session.getAttribute("EMPNO").toString());
	ebean = emph.getEmployeeInformation(session.getAttribute("EMPNO").toString());
	if(proj=="")
	{
	 proj=Integer.toString(eoffbn.getPrj_srno());
	 //System.out.println("project"+proj);
	}

	String searchList = request.getParameter("sList")==null?"ALL":request.getParameter("sList");
	String action = request.getParameter("action")==null?"":request.getParameter("action");
	String error = request.getParameter("error")==null?"":request.getParameter("error");

	if(searchList.equalsIgnoreCase("PENDING")){
		LoanList = loanHandler.loanDisplay(empNo,"PENDING",proj,roleId);

		}else if(searchList.equalsIgnoreCase("SANCTION")){
			LoanList = loanHandler.loanDisplay(empNo,"SANCTION",proj,roleId);
		}else if(searchList.equalsIgnoreCase("CANCEL")){
			LoanList = loanHandler.loanDisplay(empNo,"CANCEL",proj,roleId);
		}else if(searchList.equalsIgnoreCase("NIL")){
			LoanList = loanHandler.loanDisplay(empNo,"NIL",proj,roleId);
		}else{
			LoanList = loanHandler.loanDisplay(empNo,"ALL",proj,roleId);
	}

	
	int flag=-1;
	int prjCode = 0;
	 
		
		try
		{
		flag=Integer.parseInt(request.getParameter("flag")==null?"":request.getParameter("flag")); 
		}catch(Exception e)
		{
	//		System.out.println("no flag value"+flag);
			if( request.getParameter("prj")==null)
			{
			session.setAttribute("prjCode", "");
			}
		}
		if(action.equalsIgnoreCase("getdetails"))
		{
			
			
			 prjCode = Integer.parseInt(request.getParameter("prj"));
			// System.out.println("thr firssssssssssssssssst prjcode"+prjCode);
		    session.setAttribute("prjCode", prjCode);
		  
		    session.setAttribute("projEmpNolist", projEmpNolist);
		    int i=0;
		    
		   
		    ArrayList<TranBean> arl=new ArrayList<TranBean>();
		    for(TranBean tbn : projEmpNolist){
		    	TranBean trbn = new TranBean();
		    	
		    	projEmpNmlist.add(trbn);
	
		    } session.setAttribute("prjCode", prjCode);
		//    System.out.println("the employee are for selected project code as:"+prjCode+ " "+projEmpNmlist.size());
		}


	%>
	function loancal() {

		window.location.href = "Loan_calculator.jsp";

	}
	
	
	
	function getEmpInfo()
	{
		
		var emp_info=document.getElementById("emplist").value;
		
		var proj=document.getElementById("proj").value;
		window.location.href = "LoanApp.jsp?action=getdetails&prj="+proj+"&emp_info="+emp_info;
	}
	
</script>
<style type="text/css">

.ac_results {
	padding: 0px;
	border: 1px solid #cacaca;
	background-color: #f5f6fa;
	overflow: hidden;
	width: 35% !important;
}

</style>


</head>
<body onload="checkLoan()"  >

<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" >
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>LOAN Application</h1>
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
			<div id="table-content" align="center" style=" max-height:520px; ">
					
			<form name="addForm" action="LoanAppServlet?action=addTran" method="post" onSubmit="return validation()">
  <table style="width:60%"  id="customers">
  	<tr style="width:100%"><th colspan="2"><font color="white" size="3">LOAN  DETAILS</font></th></tr> 
   
   		<tr>
	
		<% if(request.getParameter("prj")==null)
		{
		
			%>
				<td>Project Name : 
			<input type="text" id="project" name="project" readonly="readonly" style="width: 80%; border: none;"  value="<%=eoffbn.getPrj_name()%>::<%=eoffbn.getPrj_srno() %>">
		
			
		<%
		}else
		{
			 
		ProjectListDAO  pl=new ProjectListDAO();
		ProjectBean pb=new ProjectBean();
		pb=pl.getProjectInfo(Integer.parseInt(request.getParameter("prj")));
		%>
			<td>Current Project  : 
		<input  class="form-control"   type="text" id="project" name="project" readonly="readonly" style="width: 80%; border: none;" value="<%= pb.getSite_Name()%>::<%=pb.getSite_ID() %>">

		
		<%
		}
	   %></td>
	   <%
	   				 dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
					 Date date = new Date();
					 String currentdate = dateFormat.format(date);  
					%>
	   
	    </tr>
			<%if(roleId.equals("1"))
												{%>
												<tr>
												<td colspan="3">Project :
												<input class="form-control"   type="text" id="pp" name="pp" style="width: 80%;" value="<%=request.getParameter("proj")==null?"":request.getParameter("proj").replaceAll(" and ", " & ")%>" placeholder="Enter a character to view the available project list (E.g - %) ">
												 <input type="Button" class="myButton" value="Submit" title="To change Employees Of particular Project Click here" onclick="getTranDetails()" />
										</td>		 </tr>
												
											<%}
											%>
  </table>
<br/>

	<div align="center">
		
		
		
		
									<div id="loan" >
										<table >
											<tr valign="top">
												<td align="center">

														<table id="customers">
															<tr class="alt">
											<td colspan="6" align="right"><input type="button" class="myButton"
												value="Loan Calculator" onclick="loancal()" /></td>
										</tr>
															<tr class="alt">
																<th colspan="6">Add New Loan</th>
															</tr>
															<tr>
															<td>Select Employee&nbsp;<font color="red"><b>*</b></font></td>
																<td >
												
												 <input type="hidden" name="proj" id="proj" value="<%=request.getParameter("prj")==null? Integer.toString(eoffbn.getPrj_srno()):request.getParameter("prj")%>">
												
												<select class="form-control" 
													style="width: 300px" name="emplist" id="emplist" >
														<option value="0" selected >Select</option>
														<%
													
												    	 list=loanAppHandler.getEmpList(request.getParameter("prj")==null? Integer.toString(eoffbn.getPrj_srno()):request.getParameter("prj"));
														%>
												    	
												    	 <%
												    		for(LoanAppBean lkb :list)
				 				 						{   %> 						 															 								 
				 				     		
				 				      						<option value="<%=lkb.getEMPNO()+":"+lkb.getCurrentSalaryMonth()%>"  title="<%=lkb.getEMPNO()%>"  ><%=lkb.getName()%>-<%=lkb.getEmpcode() %></option>
				 				     					<%																				
				 				     					//System.out.println("empname====="+lkb.getEMPNO()+lkb.getEmpcode() + lkb.getName() );													
																												
				 				 						}																
														    						 										
														%>
												</select>
												</td>
																<td>Loan Code&nbsp; <font color="red"><b>*</b></font></td>
																<td>
																	<%
																	
																		CodeMasterHandler cdmh = new CodeMasterHandler();
																			ArrayList<CodeMasterBean> cdlist = new ArrayList<CodeMasterBean>();
																			cdlist = cdmh.getLoancdList();
																			
																	%> <select class="form-control"  name="loan_code" id="loan_code" style="width: 150px">
																	
																		<%
																			for (CodeMasterBean cd : cdlist) {
																		if(cd.getTRNCD()!=227){
																		%>
																		<option value="<%=cd.getTRNCD()%>"><%=cd.getDISC()%></option>

																		<%
																		}	}
																		%>
																</select>


																</td>
																
															
																<td>Loan Amt&nbsp; <font color="red"><b>*</b></font></td>
																<td><input name="loan_amt" id="loan_amt" class="form-control" 
																	type="text" maxlength="14"
																	onkeypress="return inputLimiter(event,'Numbers')"
																	onkeyup="count_instalmnt(this.form)"></td>

															</tr>
															<tr>
																
																<td>Start Date&nbsp; <font color="red"><b>*</b></font></td>
																<td><input size="14" name="startDate" class="form-control" 
																	id="startDate" type="text" 
																	onchange="calculateMonth('addForm'),count_instalmnt(this.form)"
																	readonly="readonly">&nbsp;&nbsp;
															 	<img src="images/cal.gif"  id="imag2"
																	style="vertical-align: middle; cursor: pointer;" 
																	onClick="javascript:NewCssCal('startDate', 'ddmmmyyyy'),count_instalmnt(this.form)"	 /> 
																</td>

																<td>End Date&nbsp; <font color="red"><b>*</b></font></td>
																<td><input size="14" name="EndDate" id="EndDate" class="form-control" 
																	type="text" value=""
																	onchange="calculateMonth('addForm'),count_instalmnt(this.form)"
																	readonly="readonly"> &nbsp;&nbsp;<img
																	src="images/cal.gif"  id="imag"
																	style="vertical-align: middle; cursor: pointer;"
																	onClick="javascript:NewCssCal('EndDate', 'ddmmmyyyy'),count_instalmnt(this.form)" />
																</td>
																<td>Loan Per.&nbsp; <font color="red"><b>*</b></font></td>
																<td><input name="loan_per" id="loan_per" class="form-control" 
																	type="text" value="0"
																	onkeypress="return inputLimiter(event,'Numbers')"
																	onkeyup="count_instalmnt(this.form)" maxlength="14"></td>

															</tr>
															<tr>
																
																<td>Number of Payments(in Months):</td>
																<td><input name="pay_month" id="pay_month" class="form-control" 
																	type="text" value="0" maxlength="14"
																	readonly="readonly"></td>
																<td>Monthly Installments</td>
																<td><input name="month_inst" id="month_inst" class="form-control" 
																	type="text" maxlength="14" readonly="readonly"></td>
																<td>Bank Name&nbsp; <font color="red"><b>*</b></font></td>
																<td><input name="bank_name" id="bank_name" class="form-control" 
																	type="text" maxlength="14"  ></td>
															</tr>
													
															<tr align="center" valign="middle">

																<td colspan="6"><input type="button" class="myButton"
																	value="View Installments" onClick=" return check()" />
																	<input type="submit" class="myButton" name="Submit" value="Save" /> <input
																	type="reset" class="myButton" value="Cancel" onClick="" /></td>
															</tr>
															
														</table>
														

														<input type="hidden" name="flag" id="flag"
															value="<%=flag%>">
												
												
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
	  
	</div>
													</form>
			
			<input type="hidden" name="flag1" id="flag1" value="<%=flag%>">
  

<div align="center">


<div>
<table id="customers" style="float: none;">

<tr><th>Select List</th><td><select name="type" id="type" onchange="redirect()">

<option value="SELECT" selected >SELECT</option>
<option value="ALL" <%=status.equalsIgnoreCase("ALL")?"Selected":"" %>>ALL</option>
<option value="PENDING" <%=status.equalsIgnoreCase("PENDING")?"Selected":"" %>>PENDING</option>
<option value="SANCTION" <%=status.equalsIgnoreCase("SANCTION")?"Selected":"" %>>SANCTION</option>
<option value="CANCEL" <%=status.equalsIgnoreCase("CANCEL")?"Selected":"" %>>CANCELED</option>
<option value="NIL" <%=status.equalsIgnoreCase("NIL")?"Selected":"" %>>NIL</option>
</select></td></tr>
</table>

</div>

								<div align="center" class="imptable" style="overflow-y:hidden; width: 100%;">
										
										<table style="position:static; max-width: 100%" >
											<tr style="position:static;"align="center" bgcolor="#1F5FA7">
													
														<td width="70" style="color: white;">EMP CODE</td>
														<td width="200" style="color: white;">EMP NAME</td>
														<td width="90" style="color: white;">START DATE</td>
														<td width="90" style="color: white;">END DATE</td>
														<td width="90" style="color: white;">LOAN AMOUNT</td>
														<td width="110" style="color: white;">MONTHLY INSTALLMENT</td>
														<!-- <td width="90" style="color: white;"> SALARY PAYABLE</td> -->
														<td width="90" style="color: white;">SANCTION AMOUNT</td>
														<td width="110" style="color: white;">SANCTION BY</td>
														<td width="90" style="color: white;">SANCTION DATE </td>
      												
													
														<td width="90" style="color: white;">STATUS</td>
											</tr>
											</table>
											</div>
   <div align="center" class="imptable" style="overflow-y:auto; height:200px; width: 100%;">
  <%if(LoanList.size()<=5)
	 {%>
   <table style="margin-left: 0px ; position:static;"  >
    <% }else{%>
    <table style="margin-left: 16px ; position:static;"  >
    <%} %>
    <%
    if(LoanList.size()!=0){
    	for(LoanAppBean lBean : LoanList){
    		
    		
    		ebean = emph.getEmployeeInformation(Integer.toString(lBean.getEMPNO()));
    		ebean1 = emph.getEmployeeInformation((lBean.getSanctionby()));    		
    	//	ebean1 = emph.getEmployeeInformation(lBean.getSanctionby());  	
    	%>
    	
    
    	<tr align="center" style="position:static;">	
    	
    	<td width="70"><%=ebean.getEMPCODE()%></td>
		<td width="200" ><%=lookuph.getLKP_Desc("SALUTE", ebean.getSALUTE())+" "+ ebean.getFNAME()+" "+ebean.getLNAME() %></td>
    	
    	
    	<td width="90"><%=lBean.getStart_date()%></td>
    	<td width="90"><%=lBean.getEnd_date()%></td>
    	<td width="90"><%=lBean.getLoan_amt()%></td>
    	<%if(lBean.getEMPNO()==6)
    	 System.out.println("installment on jsp  is :"+lBean.getMonthly_install());%>
  	    <td width="110"><%=lBean.getMonthly_install()%></td>
    	<%-- <td width="90"> <%=advanceBean.getPayable() %> </td> --%>
    	
    	<%if(lBean.getACTIVE().equalsIgnoreCase("SANCTION")){ %>
    	<td width="90"><%=lBean.getLoan_amt()%></td>
    	<%}
    	else if(lBean.getACTIVE().equalsIgnoreCase("CANCEL")){ %>
    	<td width="90">Not Sanctioned</td>
    	<%}
    	else if(lBean.getACTIVE().equalsIgnoreCase("NIL")){ %>
    	<td width="90"><%=lBean.getLoan_amt()%></td>
    	<%}
    	else
    		{%>
    		<td width="90">YET TO SANCTION</td>
    		<%} %>
    	
    	<%if(lBean.getACTIVE().equalsIgnoreCase("SANCTION")){ %>
    	<td width="110" ><%=lookuph.getLKP_Desc("SALUTE", ebean1.getSALUTE())+" "+ ebean1.getFNAME()+" "+ebean1.getLNAME() %></td>
    	<%}
    	else if(lBean.getACTIVE().equalsIgnoreCase("CANCEL")){ %>
    	<td width="110"><%=lookuph.getLKP_Desc("SALUTE", ebean1.getSALUTE())+" "+ ebean1.getFNAME()+" "+ebean1.getLNAME() %></td>
    	<%}
    	else if(lBean.getACTIVE().equalsIgnoreCase("NIL")){ %>
    	<td width="110"><%=lookuph.getLKP_Desc("SALUTE", ebean1.getSALUTE())+" "+ ebean1.getFNAME()+" "+ebean1.getLNAME() %></td>
    	<%}
    	else
    		{%>
    		<td width="110">YET TO SANCTION</td>
    		<%} %>
    		
    		
    	<%if(lBean.getACTIVE().equalsIgnoreCase("SANCTION")){ %>
    	<td width="90"><%=lBean.getSanctiondate()%></td>
    	<%}
    	else if(lBean.getACTIVE().equalsIgnoreCase("CANCEL")){ %>
    	<td width="90"><%=lBean.getSanctiondate() %></td>
    	<%}
    	else if(lBean.getACTIVE().equalsIgnoreCase("NIL")){ %>
    	<td width="90"><%=lBean.getSanctiondate() %></td>
    	<%}
    	else
    		{%>
    		<td width="90">YET TO SANCTION</td>
    		<%} %>	
    	
    	
    	
    	

    	<td width="90"><%=lBean.getACTIVE() %></td>
    </tr>
   
    <%}
    } else  {%>
    <tr ><td colspan="10" height="20">No Records Found</td></tr>
    <%}%>
    
  </table>
	
</div>
</div>
</div>
</div>
			<!--  end table-content  -->
	
			<div class="clear"></div>
		 
		
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