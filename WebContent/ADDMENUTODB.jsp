<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="payroll.DAO.ConnectionManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ADD MENU</title>

<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<%
String flag=request.getParameter("flag")==null?"":request.getParameter("flag");

%>

<script type="text/javascript">


function chkflag()
{
	var flag=document.getElementById("flag").value;
	
	if(flag==1)
		{
		alert("Menu Added Successfully....!");
		}
	else if(flag==2)
	{
	alert("Menu Updated Successfully....!");
	}
}

function validate()
{
	var dname=document.getElementById("dname").value;
	var linkname=document.getElementById("linkname").value;
	
	
	if(dname=="")
		{
		alert("Please enter menu name....!");
		return false;
		}
	
	if(linkname=="")
	{
	alert("Please enter menu URL....!");
	return false;
	}
	var fl=confirm("DO you want to add New MENU ?");
	if(fl)
		{
		return true;
		}
	{
		return false;
		}
	
}

function validate1()
{
	var dname=document.getElementById("main1").value;
	if(dname=="")
		{
		alert("Please Select menu name....!");
		return false;
		}
	
	var fl=confirm("DO you want to Update MENU ?");
	if(fl)
		{
		return true;
		}
	{
		return false;
		}
	
}

</script>

 
</head>
<body onload="chkflag()">



<%Connection con=ConnectionManager.getConnection();
Statement st1=con.createStatement();
Statement st2=con.createStatement();
Statement st3=con.createStatement();

ResultSet rs1=null;
ResultSet rs2=null;
ResultSet rs3=null;
%>

<br><br><br><br>
<div align='center'>

<table id="customers">

<tr class="alt">

<td>

<div id="add" align="center">

<form action="ADDMENUTODatabese?action=addmenu" method="post" onsubmit="return validate()">
<table>
<tr class="alt">
<th colspan='2'>
ADD NEW MENU PAGE</th>
</tr>

<tr class="alt">
<td>
SELECT MAIN HEADER
</td>
<td>
<select id="main" name='main' class="form-control" >
<option value="0">MAIN Header</option>
<%

rs1=st1.executeQuery("select menuid,menu_name from MENU where MENU_URL='#'");
while(rs1.next())
{
	%>
	<option value="<%=rs1.getString(1)%>"><%=rs1.getString(2)%></option>
	<%
}

%>
</select>
</td>
</tr>
<tr class="alt">
<td>
Enter Display Name :
</td>
<td>
<input type="text" id="dname" name='dname' size="50" class="form-control"  style="font-size: larger;">
</td>
</tr>
<tr class="alt">
<td>
Enter JSP File :
</td>
<td>
<input type="text" id="linkname" name='linkname' class="form-control" size="50" style="font-size: larger;">
</td>
</tr>
<tr class="alt">

<td>
STATUS
</td>
<td>
<select name='status'  class="form-control"  >
<option value='1'> Active</option>
<option value='2'>Non-Active</option>
</select> 
</td>
</tr>
<tr class="alt">
<td colspan="2" align="center">

<input type='submit'  class="form-control"   value="ADD MENU">
</td>
</tr>

</table>
</form>
</div>
</td>

<td> &nbsp;&nbsp;&nbsp;&nbsp;</td>

<td>
<div id='update' align="center">

<form action="ADDMENUTODatabese?action=updatemenu" method="post" onsubmit="return validate1()">
<table>
<tr class="alt">
<th colspan='2'>
UPDATE MENU PAGE</th>
</tr>

<tr class="alt">
<td>
SELECT MAIN HEADER
</td>
<td>
<select id="main1" name='main1'  class="form-control"   style="font-size: larger;">
<option value=""></option>
<%

rs1=st1.executeQuery("select menuid,menu_name from MENU");
while(rs1.next())
{
	%>
	<option value="<%=rs1.getString(1)%>"><%=rs1.getString(2)%></option>
	<%
}

%>
</select>
</td>
</tr>
<tr class="alt">

<td>
STATUS
</td>
<td>
<select name='status1'  class="form-control"  >
<option value='1'> Active</option>
<option value='2'>Non-Active</option>
</select> 
</td>
</tr>
<tr>
<td colspan="2">
&nbsp;
</td>

</tr>
<tr class="alt">
<td colspan="2" align="center">

<input type='submit'  class="form-control"   value="Update">
</td>
</tr>

</table>
</form>



 </div>

</td>
</tr>
</table>
</div>

<input type="hidden" id='flag' name='flag' value='<%=flag%>'>
</body>
</html>