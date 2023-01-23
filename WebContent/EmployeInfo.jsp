<%@page import="payroll.Model.EmployeeBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee Information</title>

<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script src="JS/jquery.autocomplete.js"></script>
<script type="text/javascript">
	function TakeCustId() {
		var EMPNO = document.getElementById("EMPNO").value;
         
		if (document.getElementById("EMPNO").value == "") {
			alert("Please Insert Employee Name");
			document.getElementById("serachEmp").focus();
			return false;
		}
		var atpos=EMPNO.indexOf(":");
		if (atpos<1)
		  {
		  alert("Please Select Correct Employee Name");
		  return false;
		  }
		}
	function showHide()
	{
document.getElementById("empdiv").hidden=false;
		}
</script>
<script>
	jQuery(function() {
          $("#EMPNO").autocomplete("list.jsp");
	});
</script>
<style type="text/css">
body {
	//background-color: #FFFFFF;
	font: normal 11pt Trebuchet MS, Arial, sans-serif;
}

.box11 {
	background-color: #ddd;
	padding: 20px;
	position: absolute;
	-moz-border-radius: 15px;
	-webkit-border-radius: 15px;
	border-radius: 15px;
	behavior: url(border-radius.htc);
}

.rel1 {
	margin: 0px 0 0 20px;
	Horizontal-alig: center;
	padding: 25px;
	position: absolute;
	z-index: inherit;
	zoom: 1; /* For IE6 */
	width: -32px;
	height: -25px;
}

.ac_results {
	padding: 0px;
	border: 1px solid #DADADA;
	background-color: #606060;
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
	background-color: #DADADA;
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

background-color:#DADADA;
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
	text-shadow: #DADADA;
	 
}
#accordion ul li.submenu
{
background-color: #DADADA;
}
#accordion ul li.submenu:hover
{
background-color: #DADADA;
}
</style>


<%EmployeeBean empbean=(EmployeeBean)request.getAttribute("empbean"); 
	String EmpName=empbean.getFNAME()+" "+empbean.getLNAME();
	session.setAttribute("empname",EmpName);
%>
</head>
<body style="overflow:hidden;">
<div id style="overflow-y:scroll; max-height:78%; ">
<form  
	method="Get" onSubmit="return TakeCustId()">
<table>
	<tr>
		<td>Enter Employee Name Or Emp-Id<input type="text" name="EMPNO" size="40"
			id="EMPNO" onclick='showHide()'
			;" title="Enter Employee Id/Name"> <input type="submit"
			class="button blue" value="Submit"></td>
			<td><a href="employee.jsp">Add Employee</a></td><td></td>
	</tr>
	<tr></tr>
</table>
</form><br>




<form action="EmployeeServlet?action=employee" method="Post">
		<div id="empdiv">
			<table width="861" border="1" bordercolor="#000000">
					<tr class="alt">
						<th width="144" bgcolor="#999999">Employeee Number</th>
						<th width="230" bgcolor="#999999">Employee Name</th>
						<th width="175" bgcolor="#999999">PF Number</th>
						<th width="183" bgcolor="#999999">Date Of Joing</th>
						<th width="105" bgcolor="#999999">GetDetail</th>
						<tr bordercolor="#CCCCCC">
					<td><%=empbean.getEMPNO() %></td>
					  <td><%=EmpName %>"</td>
					  <td><%=empbean.getPFNO() %>"</td>
					  <td><%=empbean.getDOJ() %></td>
				  	  <td align="center" ><input type="submit"  value="Detail"/></td>
					</tr>
					
					
		  </table>	
			
		
		
		</div>
</form>
</div>
</body>
</html>