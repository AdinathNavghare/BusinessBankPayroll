<%@page import="payroll.Model.ShiftBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />

<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->


<%
String action = request.getParameter("action")==null?"":request.getParameter("action");
String srno = request.getParameter("srno")==null?"":request.getParameter("srno");

ArrayList<ShiftBean> shiftlist =  (ArrayList<ShiftBean>)session.getAttribute("shiftlist");
%>
<script type="text/javascript">

function validate()	
{

        
	if(document.shiftform.shiftcode.value=="Default")
          {   alert("please enter shift");
              document.shiftform.shiftcode.focus();
              return false;
          }
		  if(document.shiftform.status.value=="Default")
		  {
		  alert("please select status");
		   document.shiftform.status.focus();
		   return false;
		  }
		   if(document.shiftform.starttime.value=="")
		  {
		  alert("please enter starttime");
		   document.shiftform.starttime.focus();
		   return false;
		  }
		   if(document.shiftform.endtime.value=="")
			  {
			  alert("please enter endtime");
			   document.shiftform.endtime.focus();
			   return false;
			  }
	var  y = document.forms["shiftform"]["starttime"].value;
	  if(!y.match(/^\d+/))
	   {
	        alert("Please Start Time enter only numeric ");
	        document.shiftform.starttime.focus();
		      return false;
		        }
	  var  z = document.forms["shiftform"]["endtime"].value;
	  if(!z.match(/^\d+/))
	   {
	        alert("Please End Time enter only numeric ");
	        document.shiftform.endtime.focus();
		      return false;
		        }

	
   }




function Update(srno)
{
	window.location.href="shift.jsp?action=update&srno="+srno;
}
	function Showhide()
	{
		document.getElementById("update").hidden=true;
	} 
	function  UpdateValidation()
	{
		/* if(document.getElementById("shiftcode").selectedIndex==0)
			{
			 alert("please enter shift");
			 document.getElementById("shiftcode").focus();
             return false;
			}
		if(document.getElementById("status").selectedIndex==0)
			{
			 alert("please select status");
			 document.getElementById("status").focus();
			   return false;
			}
		if(document.getElementById("starttime").value == "") 
			{
				alert("please enter start time");
				 document.getElementById("starttime").focus();
				   return false;
			}
		if(document.getElementById("endttime").value == "") 
		{
			alert("please enter start time");
			 document.getElementById("endtime").focus();
			   return false;
		} */
		if(document.editshift.shiftcode.value=="Default")
        {   alert("please enter shift");
            document.editshift.shiftcode.focus();
            return false;
        }
		  if(document.editshift.status.value=="Default")
		  {
		  alert("please select status");
		   document.editshift.status.focus();
		   return false;
		  }
		   if(document.editshift.starttime.value=="")
		  {
		  alert("please enter starttime");
		   document.editshift.starttime.focus();
		   return false;
		  }
		   if(document.editshift.endtime.value=="")
			  {
			  alert("please enter endtime");
			   document.editshift.endtime.focus();
			   return false;
			  }
		var  y = document.forms["editshift"]["starttime"].value;	
		  if(!y.match(/^\d+/))
		   {
		        alert("Please Start Time enter only numeric ");
		        document.editshift.starttime.focus();
			      return false;
			        }
		  var  z = document.forms["editshift"]["endtime"].value;
		  if(!z.match(/^\d+/))
		   {
		        alert("Please End Time enter only numeric ");
		        document.editshift.endtime.focus();
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
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1> Shift Management  </h1>
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
			<form  name="shiftform"action="ShiftServlet" method="post" onSubmit=" return validate()">
				<table id="customers" align="center">
				<tr>
				  <th colspan="4">Shift Maintenance Form</th>
				</tr>	
				<tr class="alt"> 
					<td width="71">Shift </td>
					<td width="158">
					<input type="hidden" id="action" name="action" value="insert"/>
					<select name="shiftcode" id="shiftcode">
						<option selected="" value="Default">Select shift</option>
						<option value="First">First</option>
						<option value="Second">Second</option>
						<option value="General">General</option>
						<option value="Night">Night</option>
					</select>					</td>
					<td width="64">Status</td>
					<td width="199">
					
					<select name="status" id="status">
						<option selected="" value="Default">Select Status</option>
						<option value="A">ON</option>
						<option value="D">OFF</option>
					</select>					</td>
				</tr>
				<tr class="alt">
					<td>Start Time</td><td><input type="text" name="starttime" id="starttime"/></td>
					<td>End Time</td><td><input type="text" name="endtime" id="endtime"/></td>
				</tr>
				<tr class="alt" align="center"><td colspan="4"> 
				<input type="submit" value="Save"> &nbsp; &nbsp;
				<input name="reset" type="reset" value="Reset"></td>
				</tr>
				</table>
			
			</form>
			<br>
			
			<table width="600" id="customers">
				<tr>
					<th width="70">SrNo</th>
					<th width="80">Shift</th>
					<th width="100">Start Time</th>
					<th width="100">End Time</th>
					<th width="50">Status</th>
					<th width="51">Edit</th>
				</tr>
			<%
			if(!(shiftlist== null))
				{
				

				for(ShiftBean sb : shiftlist)
				{
				%>
				
				<tr><td><%=sb.getSrno() %></td><td><%=sb.getShift() %></td><td><%=sb.getStartTime() %></td><td><%=sb.getEndTime() %></td><td width="60"><%=sb.getStatus() %></td>
				<td><input type="button" value="Edit" onClick="Update(<%=sb.getSrno() %>)"/></td>
				</tr>
				<%
				}
				} 
				else
				{%>
				
				<tr class="alt"><th colspan="4"></th></tr>
				<%} %>
			</table>
			<br>					
			
			<%
			if(action.equalsIgnoreCase("update"))
			{
				for(ShiftBean sb : shiftlist)
				{
				if(srno.equalsIgnoreCase(Integer.toString(sb.getSrno())))
				{
			%>
			<form name="editshift" action="ShiftServlet?action=updateshift" method="post" onsubmit="return UpdateValidation()">
				<div  id="update" name="update" >
				
				<table id="customers" align="center">
				<tr>
				  <th colspan="4">Edit Shift Maintenance Form </th>
				 
				</tr>
				<tr class="alt"> 
					<td width="85">Shift </td>
					<td width="172">
					<%
						String First = "notselected";
						String Second = "notselected";
						String General = "notselected";
						String Night = "notselected";
						
						if(sb.getShift().equalsIgnoreCase("First")){
							First = "selected";
						}else if(sb.getShift().equalsIgnoreCase("Second")){
							Second = "selected";
						}else if(sb.getShift().equalsIgnoreCase("General")){
							General = "selected";
						}else if(sb.getShift().equalsIgnoreCase("Night")){
							Night = "selected";
						}
						
						%>
					<select name="shiftcode" id="shiftcode">
						<option value="0" selected="selected">Select</option>
						<option value="First" <%=First %>>First</option>
						<option value="Second" <%=Second %>>Second</option>
						<option value="General" <%=General %>>General</option>
						<option value="Night" <%=Night %>>Night</option>
					</select>
					</td>
					<td width="78">Status</td>
					<td width="166">
					<%
						String A = "notselected";
						String D = "notselected";
						
						
						if(sb.getStatus().equalsIgnoreCase("A")){
							A = "selected";
						}else if(sb.getStatus().equalsIgnoreCase("D")){
							D= "selected";
						}
						
						%>
					<select name="status" id="status">
						<option value="0" selected="selected">Select</option>
						<option value="A" <%=A %>>ON</option>
						<option value="D" <%=D %>>OFF</option>
					</select>
					<input type="hidden" name="srno" value="<%=sb.getSrno()%>">
					</td>
     				
				</tr>
				<tr class="alt">
					<td>Start Time</td><td><input type="text" name="starttime" id="starttime" value="<%=sb.getStartTime()%>"/></td>
					<td>End Time</td><td><input type="text" name="endtime" id="endtime" value="<%=sb.getEndTime()%>"/></td>
				</tr>
				<tr class="alt" align="center"><td colspan="4"> 
				<input type="submit" value="Save"> &nbsp; &nbsp;<input type="button" value="Cancel" onClick="Showhide()"></td></tr>
				
				</table>
			<%}} }%>
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