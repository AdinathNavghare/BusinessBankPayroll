<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />

<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->




<style type="text/css">
.row:hover {
	background-color: #CCCCCC;
	cursor: pointer;
}

.style1 {
	color: #FF0000;
}
</style>
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
	}
	else if(flag==3)
	{
		alert("File Already Exists Please Choose Other File or Rename FileName ");
	}
	else if(flag==7)
	{
		
		window.close();
		window.open("login.jsp?action=0","_self");
	}
	else if(flag==10)
	{
		alert("Please Provide only .DBF files ");
	}
	else if(flag==11)
	{
		alert("Saved successfully..");
		window.close();
	}
	else if(flag==12)
	{
		alert("Records Already Present In The Data Base..");
	}
	else if(flag==13)
	{
		alert("Records Does Not Exists For Selected Month IN given File..");
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
	if(session.getAttribute("EMPNO")==null||session.getAttribute("EMPNO")=="")
	{
		check=7;
	}
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
	else if(action.equalsIgnoreCase("np"))
	{
		check=10;	// Extension does not match
	}
	else if(action.equalsIgnoreCase("saved"))
	{
		check=11;	// saved in database
	}
	else if(action.equalsIgnoreCase("present"))
	{
		check=12;	// present in database
	}
	else if(action.equalsIgnoreCase("notpresent"))
	{
		check=13;	//record not present in file
	}
	else if(action.equalsIgnoreCase("out"))
	{
		check=7;	// session
	}
}
catch(Exception e)
{
	System.out.println("First Time Loading dialog");
}


session.setAttribute("doc_name",request.getParameter("name"));
session.setAttribute("doc_desc",request.getParameter("desc")); 

%>
<center>
<form ENCTYPE="multipart/form-data" ACTION="Dbf_StoreServlet" METHOD=POST onsubmit="return validation()" >
									<table id = "customers">
										<tr class="alt">
											<th colspan="2" align="center"><B>UPLOAD THE FILE</B></th>
											</tr>
											<tr><td><b>Choose the file To Upload:</b></td>
											<td><INPUT NAME="file" TYPE="file" name="filename" id="filename"></td>
											</tr>
											<tr><td colspan="2" align="center"><input type="submit" value="Upload File">
											 <input type="button" value="Cancel" onClick="getClose()"> </td></tr>
									</table>
									<input type="hidden" value="<%=check%>" name="chek" id="chek">
									<br>
</form> 
</center>
</body>
</html>