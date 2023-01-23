<%@page import="payroll.Core.ReportDAO"%>
<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="payroll.DAO.EmployeeHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />

 <script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
 <script src="js/jquery/jquery.autocomplete.js"></script>
 
<script type="text/javascript" src="js/empValidation.js"></script>
<script type="text/javascript" src="js/date.js"></script>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<!-- <script src="js/jquery/jquery-1.4.2.min.js" type="text/javascript"></script>
 -->
 <script type="text/javascript" src="js/datetimepicker_banking.js"></script>

<script type="text/javascript" src="js/datetimepicker.js"></script>

<script type="text/javascript">

<%
/* EmployeeHandler eh = new EmployeeHandler();
EmployeeBean ebean = new EmployeeBean();
ebean = eh.getMaxEmployeeInformation(); */
String date=ReportDAO.getSysDate().substring(7,11);
int dateInInt=Integer.parseInt(date);
%>

var xmlhttp;
var url="";

if(window.XMLHttpRequest)
{
	xmlhttp=new XMLHttpRequest;
}
else //if(window.ActivXObject)
{   
	xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
}


	function validation() {
		
		var EMPNO = document.getElementById("EMPNO").value;
		var toDate=document.getElementById("todate").value; 
		var fromDate=document.getElementById("startdate").value; 
		
		if (document.getElementById("EMPNO").value == "") {
			alert("Please Insert Employee Name");
			document.getElementById("EMPNO").focus();
			return false;
		}
		var atpos=EMPNO.indexOf(":");
		if (atpos<1)
		  {
		  alert("Please Select Correct Employee Name");
		  return false;
		  }
		
		if(toDate=="")
		{
			alert("Please Select Date");
			document.getElementById("todate").focus();
			return false;
		}

		 if(fromDate!=(toDate.substring(7,11))){
			
		alert("Please Select Valid year");
		document.getElementById("startdate").focus();
		document.getElementById("todate").focus();
		return false;
		}
		
		//alert("1"+toDate+fromDate);
		document.getElementById("process").hidden=false;
		url="ReportServlet?action=leaveLedgertxt&empno="+EMPNO+"&frmdate="+fromDate+"&todate="+toDate;
		
		xmlhttp.onreadystatechange=function()
		{
		if(xmlhttp.readyState==4 && xmlhttp.status==200)
			{
				var response=xmlhttp.responseText;
	        	document.getElementById("viewPdf").innerHTML=response;
	        	document.getElementById("process").hidden=true;
	        	document.getElementById("viewPdf").hidden=false;
				initAll();
			}
		};
		
		xmlhttp.open("GET",url, true);
		xmlhttp.send();
	}
	
	
function focus(){
		
		document.getElementById("EMPNO").focus();
		
	}
</script>

<%
String pageName = "Leave_Ledger_Report.jsp";
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
 

   <script type="text/javascript">
   jQuery(function() {
       $("#EMPNO").autocomplete("searchList.jsp");
	});

 
   
</script> 
<%-- <%

Date date = new Date();
	SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
	String dt = format.format(date);
	
	SimpleDateFormat fromformat = new SimpleDateFormat("yyyy-MM-dd");
	fromformat.setLenient(false);%> --%>
</head>
<body onload="focus()" > 
<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Leave Ledger Report</h1>
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
			 <table border="1" id="customers" align="center">
			<tr>
				<th width="100" colspan="2">Leave Ledger Report</th>
			</tr>
			
			<tr class="alt" height="30" >
						<td ><b>Select Year</b></td>
						
							<td > <select style="width: 120px" name="startdate" id="startdate"  >
							
							<!-- <option value="">Select</option> -->	
						<option value="<%=dateInInt+1%>"><%=dateInInt+1%></option>
						<option value="<%=dateInInt%>" selected><%=dateInInt%></option>
						<option value="<%=dateInInt-1%>" ><%=dateInInt-1%></option>	
						<option value="<%=dateInInt-2%>" ><%=dateInInt-2%></option>	
						<option value="<%=dateInInt-3%>" ><%=dateInInt-3%></option>	
						<option value="<%=dateInInt-4%>" ><%=dateInInt-4%></option>	
						<%-- <option value="<%=dateInInt-5%>" ><%=dateInInt-5%></option>	
						<option value="<%=dateInInt-6%>" ><%=dateInInt-61%></option>	
						<option value="<%=dateInInt-7%>" ><%=dateInInt-7%></option>	
						<option value="<%=dateInInt-8%>" ><%=dateInInt-8%></option>	
						<option value="<%=dateInInt-9%>" ><%=dateInInt-9%></option>	 --%>
							</select>
							</td>
						
						

					</tr>
			
			
			<tr class="alt" height="30" >
						<td><b>Select Employee</b></td>
			
			
				<td>	
						<input class="form-control" type="text" name="EMPNO" size="41" id="EMPNO"  onclick="showHide()"
 placeholder="Enter Employee Name or Emp-Code"  title="Enter Employee Name" >
			         
	</td></tr>
	<tr class="alt" height="30" >
                 
						<td ><b>To Date</b></td>
						 <td><input  name="todate" id="todate" type="text" placeholder="Please select the Date" value="">
             &nbsp;<img src="images/cal.gif" style="vertical-align: middle; cursor: pointer;"
			onClick="javascript:NewCssCal('todate', 'ddmmmyyyy')" /></td>
						
						

					</tr>
	<tr>
	<td colspan="2" align="center">
	 <input  type="button" value="Get Report" class="myButton" onclick="validation()" /></td>
		</td>
	</tr>
	
</table>
<br/>
			   <div id="viewPdf"  hidden="true">
			   </div>
			   <div id="process"  style="z-index: 1000;background-color:white;height: 100%;width: 100%;top: 0px;left: 0px;position: fixed;opacity:0.8;" hidden=true; >
				<div align="center" style="padding-top: 20%;">
				<img alt="" src="images/process.gif">
				</div>

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