<%@page import="payroll.Model.EmployeeBean"%>
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css"
	media="screen" title="default" />		

<title>Edit Employee Name</title>
<script type="text/javascript" src="js/empValidation.js"></script>
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
				alert("Record inserted Successfully!");
				window.close();
			}
			else if(flag==2)
			{
				alert("Error in  inserting Record");
				window.close();
			}
		} 
	  
	 function validate()
		{
		/* 	 if(document.getElementById("EMPNO").value == "") {
					alert("please enter Employee No");
					setTimeout("document.getElementById('EMPNO').focus()", 50);
					//document.eName.Fname.focus();
					return false;
				}  */
				
				var fname=document.getElementById("Fname").value;
				var lname=document.getElementById("Lname").value;
			if(fname=="") {
				alert("please enter First Name..");
				setTimeout("document.getElementById('Fname').focus()", 50);
				//document.eName.Fname.focus();
				return false;
			    }
			
			if(lname==""){
				alert("please enter Last Name");
				setTimeout("document.getElementById('Lname').focus()", 50);
				//document.eName.Lname.focus();
				return false;
			    } 
		    var resp=confirm("Do you want to Change Name ?");
			if(resp!=true){
				getClose();
			} 
		}
</script>


<%
SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
Date date = new Date();
String currentdate = dateFormat.format(date); 

String empname = request.getParameter("fname")==null?"":request.getParameter("fname");
String empno = request.getParameter("no")==null?"":request.getParameter("no");
String salute = request.getParameter("salu")==null?"":request.getParameter("salu");
%>

<%
int check=0;
	try
	{
		String action = request.getParameter("action");
		if(action.equalsIgnoreCase("close"))
		{
			check=1; //Record inserted
		}
		else if(action.equalsIgnoreCase("keep"))
		{
			check=2;	// Error Record not inserted
		}
	}
	catch(Exception e)
	{
	 	e.printStackTrace();		
	} 
%>
</head>
<body onLoad=" checkVal()">
<br/>
	<form id="eName" name="eName" action="EditNameServlet?action=editName" method="post" onSubmit="return validate()">
		<table width="700" border="0" align="center" id="customers">
			  <tr class="alt">
			    	<th colspan="6" align="center">Edit Name </th>
			  </tr>
			  <tr class="alt">
				    <td width="139" align="right" valign="middle" >Date </td>
				    <td colspan="2" width="194" bgcolor="#FFFFFF"><input name="cdate" id="cdate" type="text" value="<%=currentdate%>" readonly="readonly" ></td>
				    <td width="129" align="right" bgcolor="#FFFFFF">EMPNO</td>
				    <td colspan="2" width="165" bgcolor="#FFFFFF"> <input type="text" name="EMPNO"  id="EMPNO" value="<%=empno%>" readonly="readonly"  ></td>
			  </tr>
			  
			  <tr class="alt">
				  	<td  width="129" align="right" bgcolor="#FFFFFF">Emp Name</td>
				    <td colspan="5" width="165" bgcolor="#FFFFFF"><input size="40" type="text" name="EMPNAME"  id="EMPNAME" value="<%=empname%>"  readonly="readonly"></td>
			  </tr>
			 
			  <tr class="alt">
				  	<td width="129" align="right" bgcolor="#FFFFFF">Fname </td>
				    <td width="165" bgcolor="#FFFFFF"><input type="text" name="Fname" id="Fname" maxlength="20" value = "" onBlur="TextCheck(this.id)"> </td>
				    <td align="right" valign="middle" bgcolor="#FFFFFF">Mname</td>
				    <td bgcolor="#FFFFFF"><input type="text" name="Mname" id="Mname" maxlength="20" value = "" onBlur="TextCheck(this.id)"> </td>
				    <td align="right" valign="middle" bgcolor="#FFFFFF">Lname</td>
				    <td bgcolor="#FFFFFF"><input type="text" name="Lname" id="Lname" maxlength="20" value = "" onBlur="TextCheck(this.id)" > </td>
			  </tr>
			 
			  <tr align="center"  class="alt">
				    <td colspan="6" valign="middle">
				      <label><input type="submit" name="save" value="Save" />
				      </label>
				      <label><input type="button" name="clear" value="Cancel" onClick="getClose()"/>
				      </label>
				    </td>
			  </tr>
		</table>
		<input type="hidden" value="<%=salute%>" id ="salute" name="salute">
	</form>
<input type="hidden" value="<%=check%>" name="chek" id="chek">
</body>
</html>


