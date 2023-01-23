<%@page import="payroll.DAO.LookupHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
	<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.LeaveMasterHandler"%>
<%@page import="payroll.Model.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Leave Table</title>
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />
<link rel="stylesheet" type="text/css" href="datepickr.css" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

<script language="javascript">


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


 function showDiv1()
	 {
      document.getElementById("hide2").style.display = "none";
      
      	
	}
 function getCancel(key)
 {
	
	 var resp=confirm("Are you sure to cancel this Leave?");
		if(resp==true)
		{
			window.location.href="leave?action=statusChange&cancelString="+key;
			
		} 
	}
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


<script>
	jQuery(function() {
          $("#EMPNO").autocomplete("list.jsp");
	});
</script>
<style type="text/css">
.ac_results {
	padding: 0px;
	border: 1px solid #84a10b;
	background-color: #84a10b;
	overflow: hidden;
	
}

.ac_results ul {
	width: 100%;
	list-style-position: outside;
	list-style: none;
	padding: 0;
	margin: 0;
}

.ac_results li {
	margin: 0px;
	padding: 2px 5px;
	cursor: default;
	display: block;
	color: #fff;
	font-family: verdana;
	cursor: pointer;
	font-size: 12px;
	line-height: 16px;
	overflow: hidden;
}

.ac_loading {
	background: white url('../images/indicator.gif') right center no-repeat;
}

.ac_odd {
	background-color: #84a10b;
	color: #ffffff;
}

.ac_over {
	background-color: #5a6b13;
	color: #ffffff;
}

.input_text {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	border: 1px solid #84a10b;
	padding: 2px;
	width: 150px;
	color: #000;
	background: white url(../images/search.png) no-repeat 3px 2px;
	padding-left: 17px;
}

#accordion {
	list-style: none;
	padding: 0 0 0 0;
	width: 170px;
}

#accordion li {
	display: block;
	background-color: #2c6da0;
	font-weight: bold;
	margin: 1px;
	cursor: pointer;
	padding: 35 35 35 7px;
	list-style: circle;
	-moz-border-radius: 10px;
	-webkit-border-radius: 10px;
	border-radius: 10px;
	height:30px;
	text-decoration: none;

	
}
#accordion li:hover
{

background-color:#74ACFF;
}

#accordion ul {
	list-style: none;
	padding: 0 0 0 0;
	display: none;
}

#accordion ul li {
	font-weight: normal;
	cursor: auto;
	padding: 0 0 0 7px;
	
}

#accordion a {
	text-decoration: none;
	color: #FFFFFF;
	
}

#accordion a:hover {
	text-decoration: underline;
	text-shadow: #FF9927;
	 
}
#accordion ul li.submenu
{
background-color: #66CCFF;
}
#accordion ul li.submenu:hover
{
background-color: #74ACFF;
}

</style>




<% 
  
String action=request.getParameter("action")==null?"":request.getParameter("action");
LookupHandler lh=new  LookupHandler(); 
ArrayList<LeaveMasterBean> listcancel=null;
String empno=request.getParameter("empno")==null?"":request.getParameter("empno");
String leavecd=request.getParameter("leavecd")==null?"":request.getParameter("leavecd");
String applno=request.getParameter("applno")==null?"":request.getParameter("applno");
if(action.equals("listleave")||action.equals("showdetails"))
{
 
	
	listcancel =(ArrayList<LeaveMasterBean>)session.getAttribute("listcancel");
 
}
  
  %>




</head>
<body style="overflow: hidden"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:78%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<div id="page-heading">
		<div id="content"  ><!--  start page-heading -->
			<div id="step-holder">
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">1</div>
			<div class="step-light-left"><a href="searchTransaction.jsp?action=search" >Search Leave </a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no-off">2</div>
			<div class="step-light-left"><a href="showLeaveDetails.jsp?action=show">Leave Apply</a></div>
			<div class="step-light-right">&nbsp;</div>
			<div class="step-no">3</div>
			<div class="step-dark-left"><a href="leaveCancel.jsp?action=first">Leave Cancel</a></div>
			<div class="step-light-right">&nbsp;</div>
			
			<div class="step-no">4</div>
			<div class="step-light-left"><a href="sanction.jsp?action=first">Sanction leave</a></div>
			<div class="step-light-round">&nbsp;</div>
			<div class="clear"></div>
		
		</div>
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
					<h3>Employee Cancel Leave Transaction</h3>
					<% 
					  if(action.equalsIgnoreCase("first"))
					  {
					%>
<form  name="searchform" action="LeaveMasterServlet?action=cancel" method="Post" onSubmit="return TakeCustId()">
			 
			 <table width="443" cellpadding="0" cellspacing="0" id="customers"align="center">
	                
             </table>
			 <table border="1" id="customers">
					<tr>
	                  <th colspan="3"> Employee  Leave Cancel</th>
	                </tr>
					<tr >
						<td width="356">Enter Employee Number
					  <input type="text" name="EMPNO" size="30" id="EMPNO" onClick="showHide()" title="Enter Employee Number" ></td>
					  <td width="2" valign="top"> <input type="submit"class="form-submit" value="Submit" ></td>
						
						
	</tr>
	<tr></tr>
</table>
			</form>
			<%} %>
	

<!--  end table-content  --> <br />
<br />

<% if(action.equals("listleave") || action.equals("showdetails"))
   {
	 %>
	 
	 
	 <form  name="searchform" action="LeaveMasterServlet?action=cancel" method="Post" onSubmit="return TakeCustId()">
			 
			 <table width="443" cellpadding="0" cellspacing="0" id="customers"align="center">
	                
             </table>
			 <table border="1" id="customers">
					<tr>
	                  <th colspan="3"> Employee  Leave Cancel</th>
	                </tr>
					<tr >
						<td width="356">Enter Employee NO
					  <input type="text" name="EMPNO" size="30" id="EMPNO" onClick="showHide()" title="Enter Employee No"  value="<%=session.getAttribute("empno")%>"></td>
					  <td width="2" valign="top"> <input type="submit"class="form-submit" value="Submit" ></td>
						
						
	</tr>
	<tr></tr>
</table>
			</form>
			 
	 
<div>
<br>

 
<table border="1" id="customers">
	<tr class="alt" style="font-size: 2">
		<th width="70">EmpNO</th>
		<th width="67" height="20">LeaveCD</th>
			<th width="81">TransDate</th>

		<th width="54">Appl No</th>
		<th width="63">TeleNo</th>

		<th width="57">From Date</th>
		<th width="48">To Date</th>
		<th width="48">Days</th>
		
		<th width="71">SancAth</th>
		 
		<th width="54">Action</th>
	</tr>
	<% 
	if(listcancel.size()!=0)
	{
	
	for(LeaveMasterBean leave:listcancel)
		{
		String cancelparam =leave.getEMPNO()+":"+leave.getLEAVECD()+":"+leave.getAPPLNO()+":"+leave.getTRNDATE()+":"+leave.getFRMDT()+":"+leave.getTODT();
		%>
	
	   <tr class="row" bgcolor="#FFFFFF">
		<td width="85"><%=leave.getEMPNO() %></td>
		<td width="75"><%=lh. getLKP_Desc("LEAVE",leave.getLEAVECD())  %></td>
		
		<td width="90"><%=leave.getTRNDATE() %></td>

		<td width="75"><%=leave.getAPPLNO() %></td>
		<td width="75"><%=leave.getLTELNO() %></td>

		<td width="70"><%=leave.getFRMDT() %></td>
		<td width="70"><%=leave.getTODT() %></td>
		<td width="70"><%=leave.getNODAYS() %></td>
		<td width="75"><%=leave.getSANCAUTH() %></td>
		 
		<td width="60" align="center">
		<input type="button" Value="cancel"
			onClick="getCancel('<%=cancelparam%>')"/>
			</td>
	</tr>
	<% }
	
	}
	else
	{
	%>
	<tr align="left"><td colspan="11">No Records Found</td></tr>
<% 
  }

 %>
	<tr bgcolor="#92b22c">
		<td colspan="10">&nbsp;</td>
	</tr>

</table>
 


</div>
<br> 
 <%
}
%>
<!-- this  is form for update status -->
<%
   if(action.equals("showdetails"))
   {
	   for(LeaveMasterBean leavebean2 :listcancel)
	   {
	    if(applno.equalsIgnoreCase(String.valueOf(leavebean2.getAPPLNO())) && leavecd.equalsIgnoreCase(String.valueOf(leavebean2.getLEAVECD())))
	    {
	  
%>
  
  <div id="hide2" style="display: block;">
  <form  name="leaveForm " action="leave?action=statusChange" method="post" >
  <table  id="customers" align="center">
	 <tr>
		<th>Leave Transaction</th>
		
    </tr>
	<tr class="alt">
		<td width="421"  align="center">
		         <table >
				 <tr> <td colspan="2"> Cancel Leave Transaction</td></tr>
				 <tr ><td>Date</td><td><input type="text" name="tdate" value="<%=leavebean2.getTRNDATE()%>" readOnly="readonly"/></td></tr>
				
				 
				      <tr class="alt"><td width="109">Employee NO</td>
				      <td width="250"><input type="text" name="empno" value="<%=leavebean2.getEMPNO() %>"  /></td> </tr>
				    
					  <tr><td>Leave code</td><td>
					  <input type="text" name="leavecode" value="<%=lh.getLKP_Desc("LEAVE",leavebean2.getLEAVECD())%>"/>
					  
					  </td></tr>
					   <tr><td>From Date</td><td><input type="text" name="frmdate" value="<%=leavebean2.getFRMDT() %>" /></td></tr>
					   
					    <tr><td>To Date</td><td> <input type="text" name="todate" value="<%=leavebean2.getTODT() %>" /></td></tr>
					    <tr><td>Applno</td><td> <input type="text" name="applno" value="<%=leavebean2.getAPPLNO() %>" /></td></tr>
						 <tr><td width="109">Sanctioned By</td>
			            <td width="250"><input type="text" name="sanctionby" value="<%=leavebean2.getSANCAUTH() %>" /></td></tr>
						 <tr><td width="109">Status</td>
			            <td width="250"><input type="text" name="status" value="98"/></td></tr>
						<tr><td width="109" height="29" ></td>
						<td width="250"> <input type="submit" value="Save"/>  <input type="button" value="Cancel" onClick="showDiv1()"/></td>
						</tr>
		      </table>	   </td>
	  
	   
	   
	</tr>
		
	<tr bgcolor="#92b22c"><td>&nbsp;</td></tr>
</table>


</form>

   </div> 


   <%
	    }
	   }
    }
   %>
			
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