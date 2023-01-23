<%@page import="payroll.DAO.*"%>
<%@page import="payroll.Model.*"%>
<%@page import="java.util.ArrayList"%>
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
	
		
<script type="text/javascript" src="js/jquery.datePicker.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
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


 
<style type="text/css">
.row:hover{
		background-color:#CCCCCC;
		cursor:pointer;
}

.style1 {color: #FF0000}
</style>
<script>
	jQuery(function() {
          $("#EMPNO").autocomplete("list.jsp");
	});
	
	function Showhide()
	{
		document.getElementById("update").hidden=true;
	}
	
	function validate()
	{       var EMPNO = document.getElementById("EMPNO").value;
		     
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
		   
		  
		  if(document.timesheetform.shiftcode.value =="Default")
		  {
		    alert("please select shiftcode");
			document.timesheetform.shiftcode.focus();
			 return false;  
		  }
		 
		  
		   if(document.timesheetform.daydate.value=="")
			{
			       alert("please select the daydate");
			       document.timesheetform.daydate.focus();
			      return false;  
			   
			   
			}
		   if(document.timesheetform.checkin.value=="")
			  {
			    alert("please enter checkIn time");
				document.timesheetform.checkin.focus();
				 return false;  
			  }
			 if(document.timesheetform.checkout.value=="")
			  {
			    alert("please enter checkout");
				document.timesheetform.checkout.focus();
				 return false;  
			  }  
			  if(document.timesheetform.total.value=="")
			  {
			     alert("please enter total time ");
				 document.timesheetform.total.focus();
				 return false;  
			  }	
		  
	}
	
	
	
	function validate1()
	{
	
	  
	  if(document.getElementById("checkin1").value == "")
		  {
		  alert("Please Enter Check In Time");
		  document.getElementById("checkin1").focus();
		  return false;
		  }
	  
	  if(document.getElementById("checkout1").value == "")
		  {
		  alert("Please Enter Check Out Time");
		  document.getElementById("checkout1").focus();
		  return false;
		  }
	  if(document.getElementById("total1").value == "")
	  {
	  alert("Please Enter Total Time");
	  document.getElementById("total1").focus();
	  return false;
	  }
	  
	
		
	}
	
	 
	
</script>


<script type="text/javascript">


<%
String action = request.getParameter("action")==null?"":request.getParameter("action");

ArrayList<ShiftBean> timeshlist=null;
if(action.equalsIgnoreCase("showsheetlist")|| action.equalsIgnoreCase("updatetimesheet"))
{
	timeshlist = (ArrayList<ShiftBean>)session.getAttribute("timesheet");

}
%>

</script>
 


</head>
<body style="overflow: hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%;">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1> Time Sheet </h1>
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
			 
			
			
			<form  name="timesheetform"action="ShiftServlet?action=addtimesheet" method="post" onSubmit="return validate()">
				<table id="customers" width="523" align="center">
				
				<tr><th colspan="4">Time Sheet</th></tr>
				 
				
				 
				<tr class="alt"> 
					<td width="77">EMPNO</td>
					<td width="166">
					  <input type="text" name="EMPNO"  id="EMPNO" onClick="showHide()" title="Enter Employee No">
					
					</td>
					<td width="70">ShiftCode</td>
					<td width="190">
					<select name="shiftcode" id="shiftcode">
						<option value="Default" selected="selected">Select</option>
						<option value="First">First</option>
						<option value="Second">Second</option>
						<option value="General">General</option>
						<option value="Night">Night</option>
					</select>
					</td>
				
				<tr class="alt"><td>DayDate</td> <td><input name="daydate" size="15" id="daydate" type="text" onBlur="if(value=='')" readonly="readonly">&nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('daydate', 'ddmmmyyyy')" /> </td> <td>Check IN </td> <td><input type="text" name="checkin" id="checkin"/></td></tr>
				<tr class="alt"><td>Check Out</td><td><input type="text" name="checkout" id="checkout"/></td><td>Total</td><td><input type="text" name="total" id="total"/></td></tr>
				
				<tr class="alt" align="center"><td colspan="4"><input name="submit" type="submit" value="Save">				  &nbsp; &nbsp;
				  <input type="reset" value="Cancel"></td></tr>
				</table>
			
			</form>
			<br>
		 <table width="475" id="customers">
				<tr>
				 <th width="72">Employee NO</th>
				<th width="73">Shift Code</th>
				   			
					<th width="63">DayDate</th>
					
					<th width="70">Check IN</th>

					<th width="77">Check Out</th>
					<th width="45">Total</th>
					<th width="58">Edit</th>
				</tr>
			 
			<%
			
			if(action.equals("showsheetlist") || action.equalsIgnoreCase("updatetimesheet"))
			{
				
				for(ShiftBean shbean:timeshlist)
				{
				    
					
					
				
			%>
			
			<tr class="row"> <td width="57"> <%=shbean.getEMPNO()%></td>
			<td width="73"><%=shbean.getShift()%></td> 
			<td width="63"><%=shbean.getDaydate() %></td>
			<td width="70"><%=shbean.getCheckin() %></td>
			<td width="77"><%=shbean.getCheckout() %> </td>
			 <td width="60"><%=shbean.getTotal() %></td>
			<td><input type="button" value="Edit" onClick="window.location='timeSheet.jsp?action=updatetimesheet&Empno=<%=shbean.getEMPNO()+":"+shbean.getSrno() %>'"/></td>
				</tr>
			 <%
			   } 
			}
			 %>
				<tr class="alt"><th colspan="7"></th></tr>
			
			</table>
			 
			<br>
			
			
	<%
			if(action.equalsIgnoreCase("updatetimesheet"))
			{
				String Empno=request.getParameter("Empno")==null?"":request.getParameter("Empno");	
				System.out.println("empno is"+Empno);
				String key[] = Empno.split(":");
			    String  empno=( key[0]);
				System.out.println("empno "+empno);
				String  srno=(key[1]);
				System.out.println("srno is"+srno);
				
				for(ShiftBean shbean:timeshlist)
				{
					
						
						
			%>
			
			 
			<form  name="timesheet1" action="ShiftServlet?action=updatetimesht" method="post" onSubmit="return validate1()">
				 
				<%if(empno.equals(Integer.toString(shbean.getEMPNO())) && srno.equals(Integer.toString(shbean.getSrno())))
				{
					 %>
				
				<div  id="update">
				<table id="customers" width="520" align="center">
				
				<th colspan="4"> Edit Time Sheet</th>
				 
				
				 
				<tr class="alt"> 
					<td width="69">EMPNO</td>
					<td width="181">
					<input type="hidden" name="srno" value="<%=shbean.getSrno()%>"> 
					
					<input type="text" name="EMPNO1"  id="EMPNO1" value="<%=shbean.getEMPNO()%>" onClick="showHide()" title="Enter Employee No" readonly="readonly">					</td>
					<td width="67">ShiftCode</td>
					<td width="183">
					<select name="shiftcode1" id="shiftcode1">
						<option value="<%=shbean.getShift()%>" selected="selected"><%=shbean.getShift()%></option>
						<option value="First">First</option>
						<option value="Second">Second</option>
						<option value="General">General</option>
						<option value="Night">Night</option>
					</select>					</td>
				<tr class="alt"><td>DayDate</td> <td><input name="daydate1" size="16" id="daydate1" type="text" readonly="readonly" value="<%=shbean.getDaydate() %>" onBlur="if(value=='')">&nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('daydate1', 'ddmmmyyyy')" /> </td> <td>Check IN </td> 
				<td><input type="text" name="checkin1" id="checkin1" value="<%=shbean.getCheckin() %>" /></td></tr>
				<tr class="alt"><td>Check Out</td>
				  <td><input type="text" name="checkout1" id="checkout1" value="<%=shbean.getCheckout()%>"/></td>
				  <td>Total</td><td><input type="text" name="total1" id="total1" value="<%=shbean.getTotal()%>"/></td></tr>
				
				<tr class="alt" align="center"><td colspan="4"><input name="submit" type="submit" value="Save">				  &nbsp; &nbsp;
				 <input type="button" value="Cancel" onClick="Showhide()"></td></tr>
				</table>
				</div>
			<%} %>
			 
			</form>
			 
			
			
			<%
					
				}
			}
					%>  
			
			 
			
			
			<br>
			
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