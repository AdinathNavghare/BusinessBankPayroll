<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DTS3</title>
<script type="text/javascript">
function checkVal()
{
	var flag = document.getElementById("chek").value;
	
	if(flag == 1)
	{
		alert("Upload Successfully!");
		window.close();
	}
	else if(flag==2)
	{
		alert("Error in Uploadding file");
		window.close();
	}
	else if(flag==3)
	{
		alert("File Already Exists Please Choose Other File or Rename FileName ");
		window.close();
	}
	
}
function validation()
{
	var fup = document.getElementById("filename");
	var fileName = fup.value;
	
	
	if(fileName=="")
		{
			alert("Please Select Document File ");
			fup.focus();
			return false;
		}	
}
function getClose()
{
	window.close();
}
</script>

</head>
<body onload="checkVal()" >
<%

int check = 0;
try
{
	String action = request.getParameter("action");
	System.out.println("action...is "+action);
	if(action.equalsIgnoreCase("close"))
	{
		check=1; //Record Modified
	}
	else if(action.equalsIgnoreCase("exists"))
	{
		check=3;//File ALready exists
	}
	else if(action.equalsIgnoreCase("keep"))
	{
		check=2;	// Error Record not Modified
	}
}
catch(Exception e)
{
	System.out.println("First Time Loading dialog");
}


session.setAttribute("doc_name",request.getParameter("name"));
session.setAttribute("doc_type",request.getParameter("type"));
session.setAttribute("doc_desc",request.getParameter("desc")); 

String filetype = request.getParameter("upfile");
System.out.println("upatt"+filetype);
%>
<center>
<form ENCTYPE="multipart/form-data" ACTION="attachment?act=<%= filetype %>" METHOD=POST onsubmit="return validation()" >
	<table border="0" bgcolor="#6fb4be">
	<tr>
		<td colspan="2" align="center"><B>UPLOAD THE FILE</B></td>
	</tr>
	<tr><td colspan="2" align="center"> </td></tr>
	<tr><td><b>Choose the file To Upload:</b></td>
	<td><INPUT NAME="file" TYPE="file" name="filename" id="filename"></td>
	</tr>
	<tr><td colspan="2" align="center"> </td></tr>
	<tr><td colspan="2" align="center"><input type="submit" value="Upload File">
	 <input type="button" value="Cancel" onClick="getClose()"> </td></tr>
</table>
<input type="hidden" value="<%=check%>" name="chek" id="chek">

</form>
</center>
</body >


</html>