<%@page import="java.text.SimpleDateFormat"%>
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
int trcode = request.getParameter("trvlcode")==null?0:Integer.parseInt(request.getParameter("trvlcode"));
int srno = request.getParameter("srno")==null?0:Integer.parseInt(request.getParameter("srno"));
String prtclrs = request.getParameter("dparti")==null?"":request.getParameter("dparti");

SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");

TripBean tb = new TripBean();
TripBean trbn = (TripBean)session.getAttribute("tbean");
TripHandler th = new TripHandler();
ArrayList<TripBean> list = null;
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
	else if(flag==2)
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
function validateACDetails(){
	/* if(document.forms["acdetails"]["idreceipt"].value == ""){
		alert("Please enter Receipt .!");
		document.forms["acdetails"]["idreceipt"].focus();
		return false;
	} */
	if(document.forms["acdetails"]["idpayment"].value == ""){
		alert("Please enter payment Rs .!");
		document.forms["acdetails"]["idpayment"].focus();
		return false;
	}
	if(document.forms["acdetails"]["idamount"].value == ""){
		alert("Please enter Amount .!");
		document.forms["acdetails"]["idamount"].focus();
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
<title>Edit Journey Details</title>
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
TripBean tbn = th.getAdminDetails(trcode);

if(action.equalsIgnoreCase("journey")){
		
	TripBean tripbn = th.getJourneyDetails(trcode,srno);
	if(tripbn != null){
	%>	
<br/>
<form action="TripMasterServlet?action=UpdateJourneyDetails" name="journey" method="post" onsubmit="return validateJourney()">

	<table id="customers">
		<tr class="alt"><th colspan="4">Edit Journey Details</th></tr>
		<tr class="alt">
			<td>Employee No</td>
			<td><input type="text" name="jreno" readonly="readonly" value="<%=tbn.getEMPNO() %>"></td>
			<td>Travel Code</td>
			<td><input type="text" name="jrtrcode" readonly="readonly" value="<%=trcode %>"></td>
		</tr>
		
		<tr class="alt">
			<td>Date</td><td><input type="text" name="jDate" id="jDate" value="<%=output.format(sdf.parse(tripbn.getDDATE())) %>" readonly="readonly">
		  			  <img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
					 onClick="javascript:NewCssCal('jDate', 'ddmmmyyyy')" /></td>
			<td>Mode of Travel</td><!-- <td><input type="text" name="jMode" id="jMode"></td> -->
			<td>
				<select name="jMode" id="jMode" >
							<option value="<%=tripbn.getDTMODE()%>"><%=new LookupHandler().getLKP_Desc("TM", tripbn.getDTMODE()) %></option>
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
			<td>From</td><td><input type="text" name="jFrom" id="jFrom" value="<%=tripbn.getDFROM()%>"></td>
			<td>To</td><td><input type="text" name="jTo" id="toFrom" value="<%=tripbn.getDTO()%>"></td>
		</tr>
		
		<tr class="alt">
			<td>Class</td><!-- <td><input type="text" name="jClass" id="jClass"></td> -->
			<td>
				<select name="jClass" id="jClass" >
							<option value="<%=tripbn.getDCLASS()%>"><%=new LookupHandler().getLKP_Desc("TC", tripbn.getDCLASS())%></option>
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
			<td>Fair</td><td><input type="text" name="jFair" id="jFair" value="<%=tripbn.getDFAIRDP()%>"></td>
		</tr>
		
		<tr class="alt">
			<td>Amount</td><td colspan="3"><input type="text" name="jAmount" id="jAmount" value="<%=tripbn.getDAMT()%>"></td>
		</tr>
		
		<tr class="alt">
			<td colspan="5" align="center"><input type="submit" value="Update">&nbsp;&nbsp;
			<input type="button" value="Cancel" onClick="getClose()"/>
			</td>
		</tr>
		
	</table><input type="hidden" value="<%=srno%>" name="srno" id="srno" />
	</form>	

<% }
} 
 else if(action.equalsIgnoreCase("localConv")){
	 
	 TripBean tripbn = th.getLocExpDetails(trcode,srno);
		if(tripbn != null){
%>

<br/>
<form action="TripMasterServlet?action=updateLocExpDetails" method="post" name="localexp" onsubmit="return validateLocalExp()">
	<table id="customers">
	<tr class="alt"><th colspan="4">Edit Local Conv. Exp. Details</th></tr>
	<tr class="alt">
		<td>Employee No</td>
		<td><input type="text" name="lceno" readonly="readonly" value="<%=tbn.getEMPNO() %>"></td>
		<td>Travel Code</td>
		<td><input type="text" name="lctrcode" readonly="readonly" value="<%=trcode %>"></td>
	</tr>
	
	<tr class="alt">
		<td>Date</td><td><input type="text" name="lcDate" id="lcDate" value="<%=output.format(sdf.parse(tripbn.getDDATE())) %>" readonly="readonly">
		  			 <img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
					 onClick="javascript:NewCssCal('lcDate', 'ddmmmyyyy')" /></td>
		<td>Mode of Local Conv.</td><!-- <td><input type="text" name="lcModeLc" id="lcModeLc"></td> -->
		<td>
			<select name="lcModeLc" id="lcModeLc" >
							<option value="<%=tripbn.getDTMODE()%>"><%=new LookupHandler().getLKP_Desc("TM", tripbn.getDTMODE()) %></option>
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
		<td>From</td><td><input type="text" name="lcFrom" id="lcFrom" value="<%=tripbn.getDFROM()%>"></td>
		<td>To</td><td><input type="text" name="lcTo" id="lcTo" value="<%=tripbn.getDTO()%>"></td>
	</tr>
	
	<tr class="alt">
		<td>Distance</td><td><input type="text" name="lcDist" id="lcDist" value="<%=tripbn.getDFAIRDP()%>">KM</td>
		<td>Amount</td><td colspan="3"><input type="text" name="lcAmount" id="lcAmount" value="<%=tripbn.getDAMT()%>"></td>
	</tr>
	
	<tr class="alt"><td colspan="5" align="center"><input type="submit" value="Update" >&nbsp;&nbsp;
		<input type="button" value="Cancel" onClick="getClose()"></td>
	</tr>
	
	</table><input type="hidden" value="<%=srno%>" name="srno" id="srno" />
	</form>	
<%	} 
 }
else if(action.equalsIgnoreCase("foodExp")){
	
	TripBean tripbn = th.getFoodExpDetails(trcode,srno);
	if(tripbn != null){
%>

<br/>
<form action="TripMasterServlet?action=updateFoodExpDetails" method="post" name="foodexp" onsubmit="return validateFoodExp()">
	<table id="customers">
		<tr class="alt"><th colspan="4">Edit Food Exp. Details</th></tr>
		<tr class="alt">
			<td>Employee No</td>
			<td><input type="text" name="feeno" readonly="readonly" value="<%=tbn.getEMPNO() %>"></td>
			<td>Travel Code</td>
			<td><input type="text" name="fetrcode" readonly="readonly" value="<%=tbn.getTRCODE() %>"></td>
		</tr>
		
		<tr class="alt">
			<td>From</td><td><input type="text" name="feFrom" id="feFrom" value="<%=tripbn.getDFROM()%>"></td>
			<td>To</td><td><input type="text" name="feTo" id="feTo" value="<%=tripbn.getDTO()%>"></td>
		</tr>
		
		<tr class="alt">
			<td>Date</td><td><input type="text" name="feDate" id="feDate" value="<%=output.format(sdf.parse(tripbn.getDDATE())) %>" readonly="readonly">
		  			 		<img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
								 onClick="javascript:NewCssCal('feDate', 'ddmmmyyyy')" /></td>
			<td>Per Day</td><td><input type="text" name="fePerDay" id="fePerDay" value="<%=tripbn.getDFAIRDP()%>" onblur="calculate()"></td>
		</tr>
		
		<tr class="alt">
			<td>No. of Day</td><td><input type="text" name="feNday" id="feNday" value="<%=tripbn.getNOFDAY()%>" onblur="calculate()"></td>
			<td>Amount</td><td colspan="3"><input type="text" name="feAmount" id="feAmount" value="<%=tripbn.getDAMT()%>" readonly="readonly"></td>
		</tr>
		
		<tr class="alt"><td colspan="5" align="center"><input type="submit" value="Update" >&nbsp;&nbsp;
			<input type="button" value="Cancel" onClick="getClose()"></td>
		</tr>
		
	</table><input type="hidden" value="<%=srno%>" name="srno" id="srno" />
	</form>	
<%	} 
}
else if(action.equalsIgnoreCase("otherMiscExp")){
	
	TripBean tripbn = th.getOtherMiscExpDetails(trcode,srno);
	if(tripbn != null){
%>

<br/>
<form action="TripMasterServlet?action=updateOtherMiscExpDetails" method="post" name="otherexp" onsubmit="return validateOtherExp()">
	<table id="customers">
		<tr class="alt"><th colspan="4">Edit Other Misc. Exp. Details</th></tr>
		<tr class="alt">
			<td>Employee No</td>
			<td><input type="text" name="omeeno" readonly="readonly" value="<%=tbn.getEMPNO() %>"></td>
			<td>Travel Code</td>
			<td><input type="text" name="ometrcode" readonly="readonly" value="<%=tbn.getTRCODE() %>"></td>
		</tr>
		<tr class="alt">
			<td>Date</td><td><input type="text" name="omeDate" id="omeDate" value="<%=output.format(sdf.parse(tripbn.getDDATE())) %>" readonly="readonly">
		  			 				<img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
										 onClick="javascript:NewCssCal('omeDate', 'ddmmmyyyy')" /></td>
			<td>Bill Number</td><td><input type="text" name="omeBillNo" id="omeBillNo" value="<%=tripbn.getDBILLNO()%>"></td>
		</tr>
		<tr class="alt">
			<td>Particulars</td><td><input type="text" name="omeParticulars" id="omeParticulars" value="<%=tripbn.getDPARTI()%>"></td>
			<td>Name of The Party</td><td><input type="text" name="omePartyName" id="omePartyName" value="<%=tripbn.getDPNAME()%>"></td>
		</tr>
		<tr class="alt">
			<td>Amount</td><td colspan="3"><input type="text" name="omeAmount" id="omeAmount" value="<%=tripbn.getDAMT()%>"></td>
		</tr>
		<tr class="alt"><td colspan="5" align="center"><input type="submit" value="Update" >&nbsp;&nbsp;
			<input type="button" value="Cancel" onClick="getClose()"></td>
		</tr>
		
	</table><input type="hidden" value="<%=srno%>" name="srno" id="srno" />
	</form>	
<%	} 
}
else if(action.equalsIgnoreCase("miscExp")){
	TripBean tripbn = th.getMiscExpDetails(trcode, srno);
	if(tripbn != null){
%>

<br/>
<form action="TripMasterServlet?action=updateMiscExpDetails" method="post" name="miscexp" onsubmit="return validateMiscExp()">
	<table id="customers">
		<tr class="alt"><th colspan="4">Edit Misc. Exp. Details</th></tr>
		<tr class="alt">
			<td>Employee No</td>
			<td><input type="text" name="meeno" readonly="readonly" value="<%=tbn.getEMPNO() %>"></td>
			<td>Travel Code</td>
			<td><input type="text" name="metrcode" readonly="readonly" value="<%=tbn.getTRCODE() %>"></td>
		</tr>
		<tr class="alt">
			<td>Date</td><td><input type="text" name="miscDate" id="miscDate" value="<%=output.format(sdf.parse(tripbn.getDDATE())) %>" readonly="readonly" size="18">
								<img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
										 onClick="javascript:NewCssCal('miscDate', 'ddmmmyyyy')" /></td>
			<td>Particulars</td><td><input type="text" name="miscParticulars" id="miscParticulars" value="<%=tripbn.getDPARTI()%>"></td>
			
		</tr>
		<tr class="alt">
			<td>Location</td><td><input type="text" name="miscLocation" id="miscLocation" value="<%=tripbn.getDLOCATION()%>"></td>
			<td>Amount</td><td colspan="3"><input type="text" name="miscAmount" id="miscAmount" value="<%=tripbn.getDAMT()%>"></td>
		</tr>
		<tr class="alt"><td colspan="5" align="center"><input type="submit" value="Update" >&nbsp;&nbsp;
			<input type="button" value="Cancel" onClick="getClose()"></td>
		</tr>
		
		</table><input type="hidden" value="<%=srno%>" name="srno" id="srno" />
	</form>	
	
<% }
}
else if(action.equalsIgnoreCase("ImprestDetails")){
	
	TripBean tripbn = th.getAcImprestPrtDetails(trcode, prtclrs);
	if(tripbn!= null){
%>

<br/>
<form action="TripMasterServlet?action=updateACImprestDetails" method="post" name="acdetails" onsubmit="return validateACDetails()">
	<table id="customers">
		<tr class="alt"><th colspan="4">Edit A/C Imprest Details</th></tr>
		<tr class="alt">
			<td>Employee No</td>
			<td><input type="text" name="ideno" readonly="readonly" value="<%=tbn.getEMPNO() %>"></td>
			<td>Travel Code</td>
			<td><input type="text" name="idtrcode" readonly="readonly" value="<%=tbn.getTRCODE() %>"></td>
		</tr>
		<tr class="alt">
			<td>Particulars</td><td colspan="3"><input type="text" name="idParticulars" id="idParticulars" value="<%=tripbn.getACPARTI()%>" size="35" readonly="readonly"></td>
		</tr>
		<tr class="alt">
			<td>Date</td><td><input type="text" name="iddate" id="iddate" value="<%=output.format(sdf.parse(tripbn.getACDATE()))%>" readonly="readonly">
								<img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
										 onClick="javascript:NewCssCal('iddate', 'ddmmmyyyy')" /></td>
			<td>Receipt</td><td><input type="text" name="idreceipt" id="idreceipt" value="<%=tripbn.getACREPTNO()%>"></td>
		</tr>
		<tr class="alt">
			<td>Payment</td><td><input type="text" name="idpayment" id="idpayment" value="<%=tripbn.getACPAYMNT()%>"></td>
			<td>Amount</td><td><input type="text" name="idamount" id="idamount" value="<%=tripbn.getACAMT()%>"></td>
		</tr>
		<tr class="alt"><td colspan="5" align="center"><input type="submit" value="Update" >&nbsp;&nbsp;
			<input type="button" value="Cancel" onClick="getClose()"></td>
		</tr>
		
		</table>
	</form>	
<%} 
}
%>	
</center>

</body>
</html>