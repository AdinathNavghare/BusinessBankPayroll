<%@page import="payroll.DAO.LeaveMasterHandler"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.DAO.CodeMasterHandler"%>
<%@page import="payroll.Model.CodeMasterBean"%>
<%@page import="payroll.Model.DeductBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="payroll.DAO.DeductHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Table Allowance Management</title>
	<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />

<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />

<!--  date picker script -->
<link rel="stylesheet" href="css/datePicker.css" type="text/css" />
<script src="js/jquery/date.js" type="text/javascript"></script>
<script src="js/jquery/jquery.datePicker.js" type="text/javascript"></script>
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>

<% String status=request.getParameter("status");
System.out.println("ak1"+status);
%>

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
	if (document.getElementById("amt").value == "") {
		alert("Please Insert Proper Amt");
		document.getElementById("amt").focus();
		return false;
	}
	}
function check1()
{
	var status ="";
	status = document.getElementById("status").value;	
	if(status=="yes")
		{ 
		alert("Successfully update");
		}
}
    
 function check()
 {    
	     var input = document.getElementById('EMPNO').value;
	     if (document.getElementById("EMPNO").value =="") {
				alert("Please Insert Employee Name");
				document.getElementById("EMPNO").focus();
				return false;
			}
			var atpos=input.indexOf(":");
			if (atpos<1)
			  {
			  alert("Please Select Correct Employee Name");
			  return false;
			  }
			
	     var fields=input.split(':');
	    
	     var empno=fields[2];
	   
	    // var street = fields[1];
	     var response2="";
			try
			{
			var xmlhttp=new XMLHttpRequest();
		 	var url="";
		 	url="DeductionServlet?action=check_table_alw&empno="+empno ;
			xmlhttp.onreadystatechange=function()
			{
				if(xmlhttp.readyState==4)
				{
				response2 = xmlhttp.responseText;
				document.getElementById('amt').value = response2;
				
				}
			};
			xmlhttp.open("POST", url, true);
			xmlhttp.send();
			}
			catch(e)
			{
				
			}
			document.getElementById('amt').type = 'text';
			 document.getElementById('sbt').type = 'submit';
			 
 }
	
</script>





<style type="text/css">
.row:hover {
	background-color: #CCCCCC;
	cursor: pointer;
}

.style1 {
	color: #FF0000;
}
</style>
<script>
	jQuery(function() {
          $("#EMPNO").autocomplete("list.jsp");
	});
</script>



</head>
<body style="overflow: hidden;" onload="check1()">
   

<%@include file="mainHeader.jsp"%>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp"%>
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer"  style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1> Table Allowance</h1>
	</div>
	<!-- end page-heading -->

	<table border="0" width="100%" cellpadding="0" cellspacing="0" id="content-table" >
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

<h3>Table Allowance Management</h3>
<table width="950" border="1">
<tr bgcolor="#1F5FA7" ><td align="center" valign="middle">&nbsp;</td></tr>
<tr><td align="center" valign="middle">
   <form action="DeductionServlet?action=addtableallws" method="post" name="form1" id="form1" onsubmit="return TakeCustId()">
	<table  height="26" id="customers" border="1" style="margin-top:10px">
	  <tr class="alt">
		<td width="84" height="20" align="left" bgcolor="#CCCCCC">Employee ID </td>
		<!-- <td align="left" valign="middle" bgcolor="#FFFFFF">
  			<input type="text" name="empno1" id="empno1" />
  			<label><input type="submit" value="Get List"></label>
  		</td> -->
  		<td width="80%"><input type="text" name="EMPNO" size="40" class="form-control" 
						id="EMPNO"  title="Enter Employee Id / Name "  > &nbsp;
						<label><input type="button"  class="myButton" value="Get Amount" style="margin-left: 10px" onClick="check()"></label>
  		
  		<input type="hidden" name="amt" size="10" class="form-control" 
						id="amt"  title="Enter Amt"   > &nbsp;
						<label><input type="hidden" id="sbt"  class="myButton" value="Change Amount" style="margin-left: 10px" ></label></td>
  		</tr>
  
  </table>
  </form>
</td></tr>  
<tr><td align="center" valign="middle" bgcolor="#1F5FA7">&nbsp;</td>
</tr>

</table>
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
    
  <input type="hidden" id='status' name='status'  value='<%=request.getParameter("status")==null?"":request.getParameter("status")%>' >

</body>
</html>
