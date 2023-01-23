<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Reason</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<style type="text/css">

body,td,th {
	font-family: Georgia;
	font-size: 14px;
	color:#000000;
}

</style>
<script type="text/javascript">

function getClose()
{
	window.close();
}
function checkVal()
{
	var flag = document.getElementById("chek").value;
	if(flag == 1)
	{
		alert("Check Out Successfully....!");
		
		window.close();
	}
	
}

</script>
<%

int check=0;
try
{
	String action = request.getParameter("action");
	if(action.equalsIgnoreCase("close"))
	{
		check=1; //Record Modified
	}
	
}
catch(Exception e)
{
	
}

%>
</head>
<body onLoad="checkVal()">
<center>
<form action="AttendanceServlet?action=ealryCheckOut" method="post"  >
	
	<table width="549" align="center" id="customers">
		<tr class="alt">
			<td width="251">Reason For Early Leave 
		  </td>
			<td width="286"><textarea  name="reason" id="reason"></textarea>
			<input type="hidden" name="outtime" id="outime" value="<%=session.getAttribute("outtime")%>">
		  </td>	
			
		</tr>
		
		<tr class="alt"><td colspan="2" align="center"><input type="submit" value="Save" ><input type="button" value="Cancel" onClick="getClose()"></td></tr>
	</table>
</form>
<input type="hidden" value="<%=check%>" name="chek" id="chek">

</center>
</body>
</html>