<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.TranHandler"%>
<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=iso-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Finalize Transactions &copy DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script src="js/filter.js"></script>
<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->

<script src="js/jquery.js" type="text/javascript"></script>

<script src="js/jquery.treeview.js" type="text/javascript"></script>

<style type="text/css">
<!--
body,tr,td,th {
	font-family: Times New Roman;
	font-size: 14px;
}
-->
</style>
<script type="text/javascript">


</script>
<script type="text/javascript">
var month="";
	function toggle()
	{
		var flag = document.getElementById("finalizecheckbox").value;
		if(flag=="true")
		{
			document.getElementById("finalizecheckbox").value="false";
			document.getElementById("save").disabled = false;			
		}else if(flag=="on"){
			document.getElementById("finalizecheckbox").value="false";
			document.getElementById("save").disabled = false;
		}
		else
		{
			document.getElementById("finalizecheckbox").value="true";
			document.getElementById("save").disabled = "disabled";
		}
	}
	
	function Confirm()
	{
		//var month = document.getElementById("month").value +"-"+document.getElementById("year").value;
		
		if(numList != "")
		{
			var flag = confirm("Are you Sure to Finalize Calculations of these Employees \n"+numList+" for the Month of "+month);
			if(flag)
			{
				document.getElementById("process").hidden=false;
				document.getElementById("empList").value=numList;
				document.getElementById("sancDate").value = month;
				return true;	
			}
			else
				return false;
		}
		else
		{
			alert("No Employees Selected");
			return false;
		}
	}

	function showEditMonth() 
	{
		if(month !="")
		{
			if(showList == "")
			{
				initAll();
				document.getElementById("editDate").style.display="block";
			}
			else
			{	var flag = confirm("Currently Selected Employees will be removed?");
				if(flag)
				{
					initAll();
					document.getElementById("editDate").style.display="block";
				}
			}
		}
		else
		{
			document.getElementById("editDate").style.display="block";
		}
	}
	function setMonth()
	{
		if(document.getElementById("month").value=="NA")
		{
			alert("Not a Valid Month");
		}
		else
		{
			month = document.getElementById("month").value +"-"+document.getElementById("year").value;
			document.getElementById("sancDate").value=month;
			document.getElementById("editDate").style.display="none";
		}
	}
	function closeEditMonth()
	{
		document.getElementById("editDate").style.display="none";
	}
	
	function checkFlag()
	{
		
		var f=document.getElementById("flag").value;
		
		if(f==1)
			{
			
			alert("Employee Finalized Successfully !");
			document.getElementById("process").hidden=true;
			}
		if(f==2)
		{
		
		alert("Employee Not Finalized ! \n\t Please Recalculate and Finalize agian !");
		document.getElementById("process").hidden=true;
		}
	}
	
</script>
<%
	String pageName = "FinalizeTran.jsp";
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

<%
	Date dt = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	String[] date = sdf.format(dt).split("-");
	int year = Integer.parseInt(date[2]);
	
	String flag=request.getParameter("flag")==null?"":request.getParameter("flag");
	
%>
</head>
<body style="overflow: hidden;" onload="checkFlag()"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Finalize Transactions </h1>
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
			<div id="table-content"><center>
			<form action="TransactionServlet?action=finalize" method="post" onsubmit="return Confirm()">
			<input type="hidden" id="empList" name="empList">
			  <table  align="center" id="customers">
                <tr> <th>Finalize Payroll Calculation</th></tr>
                <tr>
                  <td align="center" bgcolor="#FFFFFF">
	                  For the Month of <input name="sancDate" id="sancDate" readonly="readonly" size="10"/>
	                  	
	                   <input type="button"  class="myButton" value="Set Month" onclick="showEditMonth()">
	                   <div style="background-color: silver; padding-top:10%;
	                   width: 100%;height: 100%;z-index: 100; display: none;margin: 0;top: 17%;left: 0;position: fixed;opacity:0.6; " id="editDate">
						<fieldset style="width: 200px;height: 100px;background-color: #2f747e;">
						<legend>Salary Month</legend>
						<img src="images/Close.png" style="float: right;top: 0;cursor: pointer; " onclick="closeEditMonth()">
						<br/>
		                   	<select id = "month" name ="month">
		                   		<option value="NA">Select</option>
		                   		<option value="Jan">Jan</option>
		                   		<option value="Feb">Feb</option>
		                   		<option value="Mar">Mar</option>
		                   		<option value="Apr">Apr</option>
		                   		<option value="May">May</option>
		                   		<option value="Jun">Jun</option>
		                   		<option value="Jul">Jul</option>
		                   		<option value="Aug">Aug</option>
		                   		<option value="Sep">Sep</option>
		                   		<option value="Oct">Oct</option>
		                   		<option value="Nov">Nov</option>
		                   		<option value="Dec">Dec</option>
	                 		</select>
	                 		-
	                 		<select id = "year" name="year">
	                 		<%
	                 			for(int i= 2010;i<2050;i++)
	                 			{
	                 				if(i == year )
	                 				{
	                 		%>
	                 					<option value="<%=i %>" selected="selected"><%=i %></option>
	                 		<%
	                 				}
	                 				else
	                 				{
	                 		%>
	                 					<option value="<%=i %>"><%=i %></option>
	                 		<%
	                 				}
	                 			}
	                 		%>
	                 		</select><br/><br/>
	                 		<input type="button" value=" &nbsp; &nbsp;OK &nbsp; &nbsp;" onclick="setMonth()">
	                 		</fieldset>
	                 		</div>
	                   
                  </td>
                  </tr>
                  <tr>
                  <td align="center">
                  	<div id="displayDiv" style="height: 300px"></div>
					<div id="countEMP">0 Employees Selected</div>
					<input type="button" class="myButton" value="Cancel All" onclick="cancelAll()">
					<input type="button" class="myButton" value="Select Employees" onclick="getFilt('toFinalizeCal')">
					<!-- <input type="button" class="myButton" value="Add More Employee" onclick="addFilt()"> -->
                  </td>
                </tr>
                <tr>
                <td>
                	<input type="checkbox" name="finalizecheckbox" id="finalizecheckbox" onClick="toggle()">
	                    &nbsp; I am agree to Finalize the Transactions, and Post it to History Table.
                </td>
                </tr>
                <tr>
                	<td align="center">
                		<input type="submit" class="myButton"  id="save" value="Finalize Calculations" disabled="disabled">
                	</td>
                </tr>
              </table>
              
              <input type="hidden" id="flag" value='<%=flag%>'>
              </form>
			</center>
			
			</div>
			<!--  end table-content  -->
			
			<div id="process"  style="z-index: 1000;background-color:white;height: 100%;width: 100%;top: 0px;left: 0px;position: fixed;opacity:0.8;" hidden=true; >
				<div align="center" style="padding-top: 20%;">
				<img alt="" src="images/process.gif">
				</div>
			</div>
			
			
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
