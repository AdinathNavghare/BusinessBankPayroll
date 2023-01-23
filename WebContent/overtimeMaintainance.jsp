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
<link rel="stylesheet" href="css/screen.css" type="text/css"
	media="screen" title="default" />
	<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />		
	
		
<script type="text/javascript" src="js/jquery.datePicker.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

<%
String EMPNO = request.getParameter("empno")==null?"0":request.getParameter("empno");
 
int empno=Integer.parseInt(EMPNO);

ArrayList<Overtimebean> ovtlist= new ArrayList<Overtimebean>();
OvertimeHandler ovth=new OvertimeHandler();
ovtlist =ovth.getovertimeList(empno);

int flag=-1;
try
{
	flag = Integer.parseInt(request.getParameter("flag"));
	
}
catch(Exception e)
{
	
}

%>
<script type="text/javascript">
function DelRecord(str)
	{
		var  key1=str;
		var resp=confirm("Are you sure to Delete this Record");
		if(resp==true)
		{
		window.location.href="OverTimeServlet?action=deleteOT&key="+key1;	
		//window.location.href="leave?action=statusChange&cancelString="+key;	
			}
	}
	
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
	  return false;
	  }
	if(document.getElementById("otdate").value=="")
		{
		
		document.getElementById("otdate").focus();
		 return false;
		}
	if(document.getElementById("shiftcode").value=="Default")
	{
	alert("please select shiftcode");
	document.getElementById("shiftcode").focus();
	 return false;
	}
	if(document.getElementById("fromhrs").value=="Default")
		{
		alert("please select fromtime");
		document.getElementById("fromhrs").focus();
		 return false;
		}
	if(document.getElementById("fromMin").value=="Default")
	{
	alert("please select  fromMIn");
	document.getElementById("fromMin").focus();
	 return false;
	}
	if(document.getElementById("tohrs").value=="Default")
	{
	alert("please select ToHours");
	document.getElementById("tohrs").focus();
	 return false;
	}
	if(document.getElementById("tomin").value=="Default")
	{
	alert("please select To Min");
	document.getElementById("tomin").focus();
	 return false;
	}
	
	if(document.getElementById("hours").value=="")
	{
	alert("please Enter Hours");
	document.getElementById("hours").focus();
	 return false;
	}
	 if(isNaN(document.getElementById("hours").value))
	{
		alert("please Enter only numeric value for Hours");
		document.getElementById("hours").focus();
		 return false;
	}
	
   
} 

function displayAll()
{
  
   window.location.href='overtimeMaintainance.jsp';
 }
 function checkFlag()
 {
	 
	 var flag = parseInt(document.getElementById("flag").value);
		if(flag == 1)
		{
			alert("Record Saved Successfully");
		}
		else if(flag == 0)
		{
			alert("Error in Record Saving");
		}
		else if(flag == 3)
			{
			alert("Record Deleted Successfully");
			
			}
		else if(flag == 4)
		{
		alert("Error in deleting Record");
		
		}
		
 }
</script>
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
</script>
</head>
<body onLoad="checkFlag()" style="overflow: hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%;">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1> OverTime Maintenance  </h1>
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
			 
			
			
			<form  name="overtimeform"action="OverTimeServlet?action=addovertime" method="post"  onSubmit="return validate()">
				<input type="hidden" name="flag" id="flag" value="<%=flag%>"/>
				<table id="customers" align="center" width="883">
				
				<tr><th colspan="5" align="right"> OverTime Maintenance</th><th align="right"><input type="button" value="View ALL" onClick="displayAll()"/></th></tr>
				 
				
				 
				<tr class="alt"> 
					<td width="76">Employee No</td>
					<td width="87"><input type="text" name="EMPNO"  id="EMPNO" onClick="showHide()" title="Enter Employee No">
					</td>
					 <td>Date</td><td><input name="otdate" size="20" id="otdate" type="text" onBlur="if(value=='')" readonly="readonly">&nbsp;<img src="images/cal.gif" align="absmiddle" style="vertical-align:middle; cursor:pointer;" onClick="javascript:NewCssCal('otdate', 'ddmmmyyyy')" />
					 </td><td width="67">ShiftCode</td>
					<td width="167">
					<select name="shiftcode" id="shiftcode">
						 <option value="Default" selected="selected">Select</option>
						 <option value="127">First</option>
						 <option value="128">Second</option>
						 <option value="129">Third</option>
						
					</select>
					</td>
					
     				
				</tr>
				 
				<tr class="alt">
					<td>FromTime</td><td>
					
					 <select name="fromhrs" id="fromhrs" >  
      					 <option value="Default" selected="selected" >HH</option>  
    					 <option value="01">01</option>
						 <option value="02">02</option>	
						 <option value="03">03</option>	
						 <option value="04">04</option>	
						 <option value="05">05</option>	
						 <option value="06">06</option>	
						 <option value="07">07</option>	
						 <option value="08">08</option>	
						 <option value="09">09</option>	
						 <option value="10">10</option>	
						 <option value="11">11</option>	
						 <option value="12">12</option>	
						 <option value="13">13</option>	
						 <option value="14">14</option>	
						  <option value="15">15</option>	
						  <option value="16">16</option>	
						  <option value="17">17</option>	
						  <option value="18">18</option>	
						  <option value="19">19</option>	
						  <option value="20">20</option>	
						  <option value="21">21</option>	
						  <option value="22">22</option>		
						  <option value="23">23</option>		
     			 </select>
				 <select name="fromMin" id="fromMin">
				        <option value="Default" selected="selected" >MM</option>  
    					 <option value="00">00</option>
						 <option value="10">10</option>
						 <option value="20">20</option>	
						 <option value="30">30</option>	
						 <option value="40">40</option>	
						 <option value="50">50</option>	
				 </select>
					
					</td>
					<td>ToTime</td><td>
					
					 <select name="tohrs" id="tohrs" >  
      					 <option value="Default" selected="selected" >HH</option>  
    					 <option value="01">01</option>
						 <option value="02">02</option>	
						 <option value="03">03</option>	
						 <option value="04">04</option>	
						 <option value="05">05</option>	
						 <option value="06">06</option>	
						 <option value="07">07</option>	
						 <option value="08">08</option>	
						 <option value="09">09</option>	
						 <option value="10">10</option>	
						 <option value="11">11</option>	
						 <option value="12">12</option>	
						 <option value="13">13</option>	
						 <option value="14">14</option>	
						 <option value="15">15</option>	
						 <option value="16">16</option>	
						 <option value="17">17</option>	
						 <option value="18">18</option>	
						 <option value="19">19</option>	
						 <option value="20">20</option>	
						 <option value="21">21</option>	
					     <option value="22">22</option>		
						 <option value="23">23</option>		
     			 </select>
				 <select name="tomin" id="tomin">
				         <option value="Default" selected="selected" >MM</option>  
    					 <option value="00">00</option>
						 <option value="10">10</option>
						 <option value="20">20</option>	
						 <option value="30">30</option>	
						 <option value="40">40</option>	
						 <option value="50">50</option>	
				 </select></td> <td>Total Hours</td><td><input type="text" name="hours" id="hours" size="20"/></td>
				</tr>
				<tr class="alt" align="center"><td colspan="6"> 
				<input type="submit" value="Save"> &nbsp; &nbsp;<input type="reset" value="Reset"></td></tr>
				
				</table>
			
			</form>
			<br>
			
			</center>
			<center>
			
			<table width="883" id="customers">
				 <tr><td> <table width="725">
				   <tr>
					<th width="90">Employee NO</th>
					<th width="70">Date</th>
					<th width="90">ShiftCode</th>
					<th width="90">FromTime</th>
					<th width="90">ToTime</th>
					<th width="80">TotalHours</th>
					<th width="51">Edit</th>

				   </tr>
				   </table></td></tr>
				   <tr><td>
				   <div style="height:340px; overflow-y: scroll; width:auto" id="div1" >
				   <table>
			 <% 
			 
				 String shiftcode="";
				if(ovtlist.size()!=0)
				{
				 for(Overtimebean otbean:ovtlist)
				 {
					 String str=otbean.getEMPNO()+":"+otbean.getOtdate()+":"+otbean.getShiftcode();
				   if(otbean.getShiftcode().equals("127"))
				   {
					   shiftcode="First";
				   }
				   if(otbean.getShiftcode().equals("128"))
				   {
					   shiftcode="Second";
				   }
				   if(otbean.getShiftcode().equals("129"))
				   {
					   shiftcode="Third";
				   }
				 %>
			
			    <tr class="row"><td width="99"><%=otbean.getEMPNO() %> </td>
			    <td width="78"><%=otbean.getOtdate() %> </td>
			    <td width="99"> <%=shiftcode %></td>
			    <td width="99"><%=otbean.getFromtime() %> </td>
			    <td width="99"><%=otbean.getTotime() %> </td>
			    <td width="89"><%=otbean.getHOURS() %></td>
				<td><input type="button" value="Delete" onClick="DelRecord('<%=str%>') "/></td>
				</tr>
			<%}
				  
			}
				else
				{	
			%>
			 <tr><td colspan="7" align="center"> Their is no record found</td></tr>
			<% 	
			 }
			
		    %>
			 </table>
			 </div>
			 </td></tr>
		 <tr class="alt"><th colspan="7"></th></tr>
				
			</table>
			</center>
			<br>
			
		 
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