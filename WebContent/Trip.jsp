<%@page import="payroll.DAO.*"%>
<%@page import="payroll.Model.*"%>
<%@page import="java.util.ArrayList"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>


<%
String action = request.getParameter("action")==null?"":request.getParameter("action");
LookupHandler lh = new LookupHandler();
TransactionBean trbn  = new TransactionBean();
%>
<script type="text/javascript">

function TakeCustId() {
	
	var EMPNO = document.getElementById("EMPNO").value;
     
	if (document.getElementById("EMPNO").value == "") {
		alert("Please Enter Employee Name");
		document.getElementById("EMPNO").focus();
		return false;
	}
	var atpos=EMPNO.indexOf(":");
	if (atpos<1)
	  {
	  alert("Please Select Correct Employee Name");
	  return false;
	  }
	
	}
	
function valid(){
	
	if (document.getElementById("EMPNO").value == "") {
		alert("Please Enter Employee Name");
		document.getElementById("EMPNO").focus();
		return false;
	}
}

function validate()
{
		if(document.tripform.trname.value==""){
			 alert("Please Enter Tour Name");
    		 document.tripform.trname.focus();
     		 return false;
		}
   		var appDate=document.tripform.appdate.value;
   		var startDate=document.tripform.startdate.value;
   		var endDate=document.tripform.enddate.value;
   		appDate = appDate.replace(/-/g,"/");
   		startDate = startDate.replace(/-/g,"/");
		endDate = endDate.replace(/-/g,"/");
   
		if(document.tripform.appdate.value=="")
		{
			 alert("Please Enter The Application Date");
    		 document.tripform.appdate.focus();
     		return false;
		}
    
   		if( document.tripform.startdate.value=="")
    	{
			  alert("Please Enter The Journey Start Date");
	      	  document.tripform.startdate.focus();
	      	return false;
		}
   
   		if( document.tripform.enddate.value=="")
    	{
			  alert("Please Enter The Journey End Date");
	      	  document.tripform.enddate.focus();
	      	return false;
		}
   		
   		var d1 = new Date(startDate);
 		var d2 =new  Date(endDate);
 	
 		if (d1.getTime() > d2.getTime())
    	{
	   		alert("Invalid Date Range!\n Journey Start Date cannot greater than  End Date!");
	   		document.tripform.enddate.focus();
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
	color: #FF0000
}

</style>

</head>
<body style="overflow:hidden"> 

<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1> Travel Master </h1>
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
		<div id="table-content" >
		
			<!--  start table-content  -->
			<div id="trip">
			
			<center>
			<form action="TripMasterServlet?action=tripEmpInfo" name="searchEmp" method="post" >
			<table class="customers">
			<tr class="alt">
						<td><font style="font-weight: bold;">Enter Employee Name / Id &nbsp;&nbsp;</font></td>
						<td colspan="10"><input type="text" name="EMPNO" size="40" id="EMPNO" onclick="showHide()" title="Enter Employee Name or No." >
						<input type="Submit" value="Submit" onclick="return TakeCustId()" class="myButton"/>
						</td>
					</tr>
					</table>
			</form>
			<br>
			
			<%if(action.equalsIgnoreCase("getdata"))
		 				{
		 				// TransactionBean trbn = new TransactionBean();
		 				 trbn = (TransactionBean) request.getAttribute("trbn");
		 				 String dept = new LookupHandler().getLKP_Desc("dept", trbn.getDept());
		 				 String desg = new LookupHandler().getLKP_Desc("desig", trbn.getDesg());
		 				// System.out.println("emp no issss jsp"+request.getAttribute("empno1"));
		 				%>
		 				
			<form name="tripform"  action="TripMasterServlet?action=addtraveladmin" method="post" onSubmit="return validate()">
			
				<table id="customers">
				
					<tr align="center"><th colspan="4">Travel Details & Expenses Form</th></tr>
					
					<tr class="alt">
						<td>Employee No</td>
						<td><input type="text" name="treno" id="treno" readonly="readonly" value="<%=trbn.getEmpno() %>" ></td>
						<td>Employee Name</td>
						<td><input type="text" name="trename" id="trename" readonly="readonly" value="<%=trbn.getEmpname() %>" size="35" ></td>
					</tr>
					
					<tr class="alt" > 
						<td>Department</td>
						<td><input type="text" name="dept" readonly="readonly" value="<%=dept %>" size="25"></td>
					    <td>Designation</td>
						<td><input type="text" name="desg" readonly="readonly" value="<%=desg %>" size="35" ></td>	 
			    	</tr>
			    	
					<tr class="alt">
						<td>Tour Name&nbsp; <font color="red"><b>*</b></font></td>
						<td colspan="3"><input type="text" name="trname" size="28"/></td>
					</tr>
					
					<tr class="alt" > 
						<td>Application Date&nbsp; <font color="red"><b>*</b></font></td>
						<td colspan="5"><input name="appdate"  id="appdate" type="text" onBlur="if(value=='')" readonly="readonly" >&nbsp;<img src="images/cal.gif" align="middle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('appdate', 'ddmmmyyyy')" title="Enter Application Date"/></td>
					</tr>
					
					<tr class="alt" > 
						<td>Start Date&nbsp; <font color="red"><b>*</b></font></td>
						<td><input name="startdate"  id="startdate" type="text" onBlur="if(value=='')" readonly="readonly" >&nbsp;<img src="images/cal.gif" align="middle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('startdate', 'ddmmmyyyy')" title="Enter Journey Start Date"/></td>
						<td>End Date&nbsp; <font color="red"><b>*</b></font></td>
						<td><input name="enddate"  id="enddate" type="text" onBlur="if(value=='')" readonly="readonly" >&nbsp;<img src="images/cal.gif" align="middle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('enddate', 'ddmmmyyyy')" title="Enter Journey End Date"/></td>
					</tr>
					
					<tr class="alt">
						<td>Start Time</td>
						<td><input type="text" name="starttime" id="starttime" title="Journey Start Time">AM/PM</td>
						<td>End Time</td>
						<td><input type="text" name="endtime" id="endtime" title="Journey End Time">AM/PM</td>
					</tr>
			    	
			    	<tr class="alt" align="center">
				    	<td colspan="4">		
				    		<input  type="submit" value="Submit"  class="myButton"> &nbsp; &nbsp;
				    		<input  type="reset" value="Clear"  class="myButton">
			    		</td>
			    	</tr>
				</table>
			</form>	
			<% }
			else {%>
			
			<form  name="tripform" action="TripMasterServlet?action=addtraveladmin" method="post" onSubmit="return valid()">

				<table id="customers">
				
					<tr align="center"><th colspan="4">Travel Details & Expenses Form</th></tr>
					
					<tr class="alt">
						<td>Employee No</td>
						<td><input type="text" name="treno" readonly="readonly" ></td>
						<td>Employee Name</td>
						<td><input type="text" name="trename" readonly="readonly" ></td>
					</tr>
					
					<tr class="alt" > 
						<td>Department</td>
						<td><input type="text" name="dept" readonly="readonly" ></td>
					    <td>Designation</td>
						<td><input type="text" name="desg" readonly="readonly"></td>   
			    	</tr>
			    	
					<tr class="alt">
						<td>Tour Name</td>
						<td colspan="3"><input type="text" name="trname"/></td>
					</tr>
					
					<tr class="alt" > 
						<td>Application Date</td>
						<td colspan="5"><input name="appdate"  id="appdate" type="text" onBlur="if(value=='')" readonly="readonly">&nbsp;
										<img src="images/cal.gif" align="middle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('appdate', 'ddmmmyyyy')" title="Enter Application Date"/>
						</td>
					</tr>
					
					<tr class="alt" > 
						<td>Start Date</td>
						<td><input name="startdate"  id="startdate" type="text" onBlur="if(value=='')" readonly="readonly">&nbsp;
							<img src="images/cal.gif" align="middle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('startdate', 'ddmmmyyyy')" title="Enter Journey Start Date"/>
						</td>
						<td>End Date</td>
						<td><input name="enddate"  id="enddate" type="text" onBlur="if(value=='')" readonly="readonly">&nbsp;
							<img src="images/cal.gif" align="middle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('enddate', 'ddmmmyyyy')" title="Enter Journey End Date"/>
						</td>
					</tr>
					
					<tr class="alt">
						<td>Start Time</td>
						<td><input type="text" name="starttime" id="starttime" title="Enter Journey Start Time">AM/PM</td>
						<td>End Time</td>
						<td><input type="text" name="endtime" id="endtime" title="Enter Journey End Time">AM/PM</td>
					</tr>
			    	
			    	<tr class="alt" align="center">
				    	<td colspan="4">	
				    			<input  type="submit" value="Submit" class="myButton"> &nbsp; &nbsp;
				    			 <input  type="reset" value="Clear"  class="myButton">	    		
			    		</td>
			    	</tr>
				</table>
			</form>	
			<% }%>
			</center>
			</div>
			<br><br><br><br><br>
	 		<div>
	 		<center>
	 			<input type="button" value="Click" onClick="window.location.href='TravelDetails.jsp'" class="myButton"><b> To Search For Travel Report.</b>
	 		</center>
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