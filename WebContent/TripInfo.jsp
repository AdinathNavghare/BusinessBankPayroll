<%@page import="payroll.DAO.TripHandler"%>
<%@page import="payroll.Model.TripBean"%>
<%@page import="payroll.Model.RelieveInfoBean"%>
<%@page import="payroll.DAO.RelieveInfoHandler"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@page import="payroll.DAO.BranchDAO"%>
<%@page import="payroll.Model.BranchBean"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>	
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />

<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script src="js/jquery/jquery-1.4.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/datetimepicker1.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%
String action = request.getParameter("action")==null?"":request.getParameter("action");
String trcode = request.getParameter("trvlcode")==null?"":request.getParameter("trvlcode");

TripBean tb = new TripBean();
TripBean trbn = (TripBean)session.getAttribute("tbean");
TripHandler th = new TripHandler();

%>
<script type="text/javascript">

function getClose()
{
	window.close();
}

function checkVal()
{
	var flag = document.getElementById("chek").value;
	
	if(flag==1)
	{
		alert("Record inserted Successfully!");
		window.close();
	}
	elseif(flag==2)
	{
		alert("Error in  inserting Record");
	}
	datePick();
}

 function calculate(){
	 
	var days = document.getElementById("feNday").value;
	var perDay = document.getElementById("fePerDay").value;
	
	var amt = days*perDay;
	document.getElementById("feAmount").value=amt;
} 
 
 function validateJourney(){
		
	 	if(document.forms["journey"]["jDate"].value == ""){
			alert("Please select journey Date .!");
			document.forms["journey"]["jDate"].focus();
			return false;
		}
	 	if(document.forms["journey"]["jMode"].value == "0"){
			alert("Please select journey Mode .!");
			document.forms["journey"]["jMode"].focus();
			return false;
		}
		var jfrom = document.forms["journey"]["jFrom"].value;
		if(jfrom == null || jfrom == ""){
			alert("Please enter journey From .!");
			document.forms["journey"]["jFrom"].focus();
			return false;
		}
		if(document.forms["journey"]["toFrom"].value == ""){
			alert("Please enter journey To .!");
			document.forms["journey"]["toFrom"].focus();
			return false;
		}
		if(document.forms["journey"]["jClass"].value == "0"){
			alert("Please select journey Class .!");
			document.forms["journey"]["jClass"].focus();
			return false;
		}
		if(document.forms["journey"]["jFair"].value == ""){
			alert("Please enter Fair.!");
			document.forms["journey"]["jFair"].focus();
			return false;
		}
		if(document.forms["journey"]["jAmount"].value == ""){
			alert("Please enter Amount .!");
			document.forms["journey"]["jAmount"].focus();
			return false;
		}
	}
 function validateLocalExp(){
		
		 if(document.forms["localexp"]["lcDate"].value == ""){
			alert("Please select Date .!");
			document.forms["localexp"]["lcDate"].focus();
			return false;
		}
		if(document.forms["localexp"]["lcModeLc"].value == "0"){
				alert("Please select journey Mode .!");
				document.forms["localexp"]["lcModeLc"].focus();
				return false;
		}
		var jfrom = document.forms["localexp"]["lcFrom"].value;
		if(jfrom == null || jfrom == ""){
			alert("Please enter journey From .!");
			document.forms["localexp"]["lcFrom"].focus();
			return false;
		}
		if(document.forms["localexp"]["lcTo"].value == ""){
			alert("Please enter journey To .!");
			document.forms["localexp"]["lcTo"].focus();
			return false;
		}
		if(document.forms["localexp"]["lcDist"].value == ""){
			alert("Please enter Distance.!");
			document.forms["localexp"]["lcDist"].focus();
			return false;
		}
		if(document.forms["localexp"]["lcAmount"].value == ""){
			alert("Please enter Amount .!");
			document.forms["localexp"]["lcAmount"].focus();
			return false;
		}
	}
 function validateFoodExp(){
		
		var jfrom = document.forms["foodexp"]["feFrom"].value;
		if(jfrom == null || jfrom == ""){
			alert("Please enter journey From .!");
			document.forms["foodexp"]["feFrom"].focus();
			return false;
		}
		if(document.forms["foodexp"]["feTo"].value == ""){
			alert("Please enter journey To .!");
			document.forms["foodexp"]["feTo"].focus();
			return false;
		}
		if(document.forms["foodexp"]["feDate"].value == ""){
			alert("Please select Date .!");
			document.forms["foodexp"]["feDate"].focus();
			return false;
		}
		if(document.forms["foodexp"]["fePerDay"].value == ""){
			alert("Please enter PerDay Amount.!");
			document.forms["foodexp"]["fePerDay"].focus();
			return false;
		}
		if(document.forms["foodexp"]["feNday"].value == ""){
			alert("Please enter No of Days.!");
			document.forms["foodexp"]["feNday"].focus();
			return false;
		}
	}
 function validateOtherExp(){
		
	 if(document.forms["otherexp"]["omeDate"].value == ""){
			alert("Please select Date .!");
			document.forms["otherexp"]["omeDate"].focus();
			return false;
		}
		var billno = document.forms["otherexp"]["omeBillNo"].value;
		if(billno == null || billno == ""){
			alert("Please enter Bill No .!");
			document.forms["otherexp"]["omeBillNo"].focus();
			return false;
		}
		if(document.forms["otherexp"]["omeParticulars"].value == ""){
			alert("Please enter Particulars .!");
			document.forms["otherexp"]["omeParticulars"].focus();
			return false;
		}
		if(document.forms["otherexp"]["omePartyName"].value == ""){
			alert("Please enter Party Name.!");
			document.forms["otherexp"]["omePartyName"].focus();
			return false;
		}
		if(document.forms["otherexp"]["omeAmount"].value == ""){
			alert("Please enter Amount .!");
			document.forms["otherexp"]["omeAmount"].focus();
			return false;
		}
	}
 function validateMiscExp(){
	 	
	 if(document.forms["miscexp"]["miscDate"].value == ""){
			alert("Please select Date .!");
			document.forms["miscexp"]["miscDate"].focus();
			return false;
		}
		if(document.forms["miscexp"]["miscParticulars"].value == ""){
			alert("Please enter Particulars .!");
			document.forms["miscexp"]["miscParticulars"].focus();
			return false;
		}
		if(document.forms["miscexp"]["miscLocation"].value == ""){
			alert("Please enter Location .!");
			document.forms["miscexp"]["miscLocation"].focus();
			return false;
		}
		if(document.forms["miscexp"]["miscAmount"].value == ""){
			alert("Please enter Amount .!");
			document.forms["miscexp"]["miscAmount"].focus();
			return false;
		}
	}
</script>

<%
int check=0;
	try
	{
		if(action.equalsIgnoreCase("close"))
		{
			check=1; //Record inserted
			System.out.print("Record inserted");
		}
		else if(action.equalsIgnoreCase("keep"))
		{
			check=2;	// Error Record not inserted
		}
	}
	catch(Exception e)
	{
		
	}
%>
<title>Add Journey Details</title>
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
</head>
<body onLoad="checkVal()">
 <input type="hidden" value="<%=check%>" name="chek" id="chek"> 
<center>
<input type="hidden" name="trvlcode" id="trvlcode" value="<%=trcode%>">

<%
if(action.equalsIgnoreCase("journey")){
	
%>

<br/>
<form action="TripMasterServlet?action=addjourneydetails" name="journey" method="post" onsubmit="return validateJourney()">

	<table id="customers">
		<tr class="alt"><th colspan="4">Add Journey Details</th></tr>
		<tr class="alt">
			<td>Employee No</td>
			<td><input type="text" name="jreno" readonly="readonly" value="<%=trbn.getEMPNO() %>"></td>
			<td>Travel Code</td>
			<td><input type="text" name="jrtrcode" readonly="readonly" value="<%=trbn.getTRCODE() %>"></td>
		</tr>
		
		<tr class="alt">
			<td>Date</td><td><input type="text" name="jDate" id="jDate" value="" readonly="readonly">
		  			 <img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
					 onClick="javascript:NewCssCal('jDate', 'ddmmmyyyy')" /></td>
			<td>Mode of Travel</td><!-- <td><input type="text" name="jMode" id="jMode"></td> -->
			<td>
				<select name="jMode" id="jMode" >
							<option value="0">Select</option>
							<%
								ArrayList<Lookup> result = new ArrayList<Lookup>();
								LookupHandler lkh = new LookupHandler();
								result = lkh.getSubLKP_DESC("TM");
								for(Lookup lp : result){
							%>
							<option value="<%=lp.getLKP_SRNO()%>"><%=lp.getLKP_DESC() %></option>		
								<% }%>
				</select>
			</td>			    
		</tr>
		
		<tr class="alt">
			<td>From</td><td><input type="text" name="jFrom" id="jFrom"></td>
			<td>To</td><td><input type="text" name="jTo" id="toFrom"></td>
		</tr>
		
		<tr class="alt">
			<td>Class</td><!-- <td><input type="text" name="jClass" id="jClass"></td> -->
			<td>
				<select name="jClass" id="jClass" >
							<option value="0">Select</option>
							<%
								ArrayList<Lookup> result1 = new ArrayList<Lookup>();
								LookupHandler lkh1 = new LookupHandler();
								result1 = lkh1.getSubLKP_DESC("TC");
								for(Lookup lp1 : result1){
							%>
							<option value="<%=lp1.getLKP_SRNO()%>"><%=lp1.getLKP_DESC() %></option>		
								<% }%>
				</select>
			</td>				    
			<td>Fair</td><td><input type="text" name="jFair" id="jFair"></td>
		</tr>
		
		<tr class="alt">
			<td>Amount</td><td colspan="3"><input type="text" name="jAmount" id="jAmount"></td>
		</tr>
		
		<tr class="alt">
			<td colspan="5" align="center"><input type="submit" value="Submit">&nbsp;&nbsp;
			<input type="button" value="Cancel" onClick="getClose()"/>
			</td>
		</tr>
		
	</table>
	</form>	
	<input type="hidden" value="<%=check%>" name="chek" id="chek" />

<% } 
 else if(action.equalsIgnoreCase("localConv")){
%>
<br/>
<form action="TripMasterServlet?action=addlocexpdetails" method="post" name="localexp" onsubmit="return validateLocalExp()">
	<table id="customers">
	<tr class="alt"><th colspan="4">Add Local Conv. Exp. Details</th></tr>
	<tr class="alt">
		<td>Employee No</td>
		<td><input type="text" name="lceno" readonly="readonly" value="<%=trbn.getEMPNO() %>"></td>
		<td>Travel Code</td>
		<td><input type="text" name="lctrcode" readonly="readonly" value="<%=trbn.getTRCODE() %>"></td>
	</tr>
	
	<tr class="alt">
		<td>Date</td><td><input type="text" name="lcDate" id="lcDate" value="" readonly="readonly">
		  			 <img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
					 onClick="javascript:NewCssCal('lcDate', 'ddmmmyyyy')" /></td>
		<td>Mode of Local Conv.</td><!-- <td><input type="text" name="lcModeLc" id="lcModeLc"></td> -->
		<td>
			<select name="lcModeLc" id="lcModeLc" >
							<option value="0">Select</option>
							<%
								ArrayList<Lookup> result = new ArrayList<Lookup>();
								LookupHandler lkh = new LookupHandler();
								result = lkh.getSubLKP_DESC("TM");
								for(Lookup lp : result){
							%>
							<option value="<%=lp.getLKP_SRNO()%>"><%=lp.getLKP_DESC() %></option>		
								<% }%>
			</select>
		</td>	
	</tr>
	
	<tr class="alt">
		<td>From</td><td><input type="text" name="lcFrom" id="lcFrom"></td>
		<td>To</td><td><input type="text" name="lcTo" id="lcTo"></td>
	</tr>
	
	<tr class="alt">
		<td>Distance</td><td><input type="text" name="lcDist" id="lcDist">KM</td>
		<td>Amount</td><td colspan="3"><input type="text" name="lcAmount" id="lcAmount"></td>
	</tr>
	
	<tr class="alt"><td colspan="5" align="center"><input type="submit" value="Submit" >&nbsp;&nbsp;
		<input type="button" value="Cancel" onClick="getClose()"></td>
	</tr>
	
	</table>
	</form>	
<%} 
else if(action.equalsIgnoreCase("foodExp")){
%>
<br/>
<form action="TripMasterServlet?action=addfoodexpdetails" method="post" name="foodexp" onsubmit="return validateFoodExp()">
	<table id="customers">
		<tr class="alt"><th colspan="4">Add Food Exp. Details</th></tr>
		<tr class="alt">
			<td>Employee No</td>
			<td><input type="text" name="feeno" readonly="readonly" value="<%=trbn.getEMPNO() %>"></td>
			<td>Travel Code</td>
			<td><input type="text" name="fetrcode" readonly="readonly" value="<%=trbn.getTRCODE() %>"></td>
		</tr>
		
		<tr class="alt">
			<td>From</td><td><input type="text" name="feFrom" id="feFrom"></td>
			<td>To</td><td><input type="text" name="feTo" id="feTo"></td>
		</tr>
		
		<tr class="alt">
			<td>Date</td><td><input type="text" name="feDate" id="feDate" value="" readonly="readonly">
		  			 <img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
					 onClick="javascript:NewCssCal('feDate', 'ddmmmyyyy')" /></td>
			<td>Per Day</td><td><input type="text" name="fePerDay" id="fePerDay" onblur="calculate()"></td>
		</tr>
		
		<tr class="alt">
			<td>No. of Day</td><td><input type="text" name="feNday" id="feNday" onblur="calculate()"></td>
			<td>Amount</td><td colspan="3"><input type="text" name="feAmount" id="feAmount" readonly="readonly"></td>
		</tr>
		
		<tr class="alt"><td colspan="5" align="center"><input type="submit" value="Submit" >&nbsp;&nbsp;
			<input type="button" value="Cancel" onClick="getClose()"></td>
		</tr>
		
	</table>
	</form>	
<%} 
else if(action.equalsIgnoreCase("otherMiscExp")){
%>
<br/>
<form action="TripMasterServlet?action=addothermiscexpdetails" method="post" name="otherexp" onsubmit="return validateOtherExp()">
	<table id="customers">
		<tr class="alt"><th colspan="4">Add Other Misc. Exp. Details</th></tr>
		<tr class="alt">
			<td>Employee No</td>
			<td><input type="text" name="omeeno" readonly="readonly" value="<%=trbn.getEMPNO() %>"></td>
			<td>Travel Code</td>
			<td><input type="text" name="ometrcode" readonly="readonly" value="<%=trbn.getTRCODE() %>"></td>
		</tr>
		<tr class="alt">
			<td>Date</td><td><input type="text" name="omeDate" id="omeDate" value="" readonly="readonly">
		  			 <img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
					 onClick="javascript:NewCssCal('omeDate', 'ddmmmyyyy')" /></td>
			<td>Bill Number</td><td><input type="text" name="omeBillNo" id="omeBillNo"></td>
		</tr>
		<tr class="alt">
			<td>Particulars</td><td><input type="text" name="omeParticulars" id="omeParticulars"></td>
			<td>Name of The Party</td><td><input type="text" name="omePartyName" id="omePartyName"></td>
		</tr>
		<tr class="alt">
			<td>Amount</td><td colspan="3"><input type="text" name="omeAmount" id="omeAmount"></td>
		</tr>
		<tr class="alt"><td colspan="5" align="center"><input type="submit" value="Submit" >&nbsp;&nbsp;
			<input type="button" value="Cancel" onClick="getClose()"></td>
		</tr>
		
	</table>
	</form>	
<%} 
else if(action.equalsIgnoreCase("miscExp")){
%>
<br/>
<form action="TripMasterServlet?action=addmiscexpdetails" method="post" name="miscexp" onsubmit="return validateMiscExp()">
	<table id="customers">
		<tr class="alt"><th colspan="4">Add Misc. Exp. Details</th></tr>
		<tr class="alt">
			<td>Employee No</td>
			<td><input type="text" name="meeno" readonly="readonly" value="<%=trbn.getEMPNO() %>"></td>
			<td>Travel Code</td>
			<td><input type="text" name="metrcode" readonly="readonly" value="<%=trbn.getTRCODE() %>"></td>
		</tr>
		<tr class="alt">
			<td>Date</td><td><input type="text" name="miscDate" id="miscDate" value="" readonly="readonly">
		  			 <img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
					 onClick="javascript:NewCssCal('miscDate', 'ddmmmyyyy')" /></td>
			<td>Particulars</td><td><input type="text" name="miscParticulars" id="miscParticulars"></td>
		</tr>
		<tr class="alt">
			<td>Location</td><td><input type="text" name="miscLocation" id="miscLocation"></td>
			<td>Amount</td><td colspan="3"><input type="text" name="miscAmount" id="miscAmount"></td>
		</tr>
		<tr class="alt"><td colspan="5" align="center"><input type="submit" value="Submit" >&nbsp;&nbsp;
			<input type="button" value="Cancel" onClick="getClose()"></td>
		</tr>
		
		</table>
	</form>	
	
<% } %>

</center>

</body>
</html>