<%@page import="payroll.Controller.ReportServlet"%>
<%@page import="java.awt.Desktop.Action"%>
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
<script>
	jQuery(function() {
		$("#EMPNO").autocomplete("list.jsp");
	});
</script>

<script>
	jQuery(function() {
		$("#EMPNO1").autocomplete("list.jsp");
	});
</script>

 
 <script type="text/javascript">
function TakeCustId() {
	var EMPNO = document.getElementById("EMPNO").value;
     
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
	}
	
function getTravelCode(code)
{
	document.getElementById("trvCode").value=code;
	}
</script>
 
<script type="text/javascript">

<% 
TransactionBean trbn  = new TransactionBean();
%>	

function validate()
{
	var EMPNO = document.getElementById("EMPNO").value;
    
	if (document.getElementById("EMPNO").value == "") {
		alert("Please Insert Employee Name");
		document.getElementById("EMPNO").focus();
		return false;
	}
	var atpos=EMPNO.indexOf(":");
	if (atpos<1)
	  {
	  alert("Please Select Correct Employee Name");
	  document.getElementById("EMPNO").focus();
	  return false;
	  }
	
	var EMPNO1 = document.getElementById("EMPNO1").value;
    
	if (document.getElementById("EMPNO1").value == "") {
		alert("Please Insert Employee Name");
		document.getElementById("EMPNO1").focus();
		return false;
	}
	var atpos=EMPNO1.indexOf(":");
	if (atpos<1)
	  {
	  alert("Please Select Correct Employee Name");
	  document.getElementById("EMPNO1").focus();
	  return false;
	  }

 var emp_no = [];
 var emp_no1 = [];
	 emp_no=EMPNO.split(":");
	 emp_no1=EMPNO1.split(":");
	 if(emp_no1 > emp_no)
		 {
		 
		 alert("Please Select Proper  Employee Number");
		 return false;
		 }
	 
}


</script>



</head>
<body style="overflow: hidden;"> 

<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Travel Report </h1>
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
			<form action="TravelReportServlet?action=showinfo" method="post" >
				
			<table class="customers">
			<tr class="alt">
						<td>Enter Employee Name / Id &nbsp;&nbsp;</td>
						<td colspan="10"><input type="text" name="EMPNO" size="30" id="EMPNO" onclick="showHide()" title="Enter Employee Name or No." >
						<input type="Submit" class="myButton" value="Search" onclick="return TakeCustId()"/>
						</td>
					</tr>
					
					</table>
			</form></br>
				
			<form  name="travelreportform" action = "ReportServlet?action=travelreport" method ="post">  
				<div style="height: 450px; overflow-y: scroll; width: auto" id="div1">			
			 <table  id="customers"  align="center" >
			
			 
			 <%String action=request.getParameter("action")!=null?request.getParameter("action"):"";
					if(action.equalsIgnoreCase("getdata"))
					{
						trbn = (TransactionBean) request.getAttribute("trbn");
				 %>	
			 
				 <tr class="alt">
						<td>Employee No</td>
						<td><input type="text" name="EMPNO" id="EMPNO" readonly="readonly" value="<%=trbn.getEmpno() %>" ></td>
						<td>Employee Name</td>
						<td colspan="2" ><input type="text" name="EMPNO1" id="EMPNO1" size="45" readonly="readonly" value="<%=trbn.getEmpname() %>" ></td>
					</tr>
					<tr align="center" valign="middle" bgcolor="#CCCCCC" height="auto">
					  <th>Travel.Code</th>
					  <th>Tour Report Name</th>
					    <th>From Date </th>
					    <th>To date</th>
					    <th>Reports</th>
					    
		      </tr>
	    
	 
					<%
						ArrayList<TripBean> result=new ArrayList<TripBean>();
						result = (ArrayList<TripBean>)request.getAttribute("list");
						if(result.size()!=0){
						for(TripBean hb:result)
						{%>
						 
						<tr class="alt" align="center">
						  <%-- <td align="center"  name="trcode" id="trcode" valign="middle"><%=hb.getTRCODE() %></td> --%>
						 <td > <%=hb.getTRCODE() %></td>
						  <td ><%=hb.getTOURRPT()%></td>
						    <td> <%=hb.getSTARTDATE() %></td>
						    <td><%=hb.getENDDATE() %></td>
						     <td ><input type="submit" class="myButton" value="GetReport" onclick="getTravelCode('<%=hb.getTRCODE()%>')" />
						     	<input type="hidden" value="" id="trvCode" name="trvCode">
						     </td>
															    
						    </tr>
					 <%
						}
						}
						else{%>
						<tr class="alt"><td colspan="5">No Records Found For <%=trbn.getEmpname()%> </td></tr>
						<%}
						}
						
					%> 
					
			  </table>
			</div>
       </form>
			
			
			
			
			
			
			
			  </center>
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