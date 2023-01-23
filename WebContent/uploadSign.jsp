<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DTS3</title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript">
function checkVal()
{
	var flag = document.getElementById("chek").value;
	
	if(flag == 1)
	{
		alert("Upload Successfully!");
		alert("Please Refresh Page");
		window.close();
		
	}
	else if(flag == 2)
	{
		alert("Error in Uploadding file");
	}
	else if(flag == 3)
	{
		alert("File Already Exists Please Choose Other File or Rename FileName ");
	}
	
	
}
function validation()
{
	var nBytes = 0;
    oFiles = document.getElementById("SIGN").files;
    nFiles = oFiles.length;
for (var nFileId = 0; nFileId < nFiles; nFileId++) {
  nBytes += oFiles[nFileId].size;
}
 
 if(nBytes > 15360)
	{
	 alert("Image Size Must Be less than 15kb ");
	 return false;
	} 
	
	var fup = document.getElementById("SIGN");
	var fileName = fup.value;
	
	var ext = fileName.substring(fileName.lastIndexOf('.') + 1);
	if(fileName=="")
		{
			alert("Please Select Image File ");
			fup.focus();
			return false;
		}
	if(ext == "gif" || ext == "GIF" || ext == "JPEG" || ext == "jpeg" || ext == "jpg" || ext == "JPG" || ext == "png" || ext == "PNG")
	{
		return true;
	} 
	else
	{
		alert("Upload  GIF or JGP or PNG Format photo image only");
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
<body onLoad="checkVal()" >
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





%>
<center>
<form enctype="multipart/form-data" ACTION="AddPhotoServlet" method="post" onSubmit="return validation()" >
	<table id="customers" >
			<tr class="alt">
                <th>Employee Id :</th>
              	<th><input  type="text"  name="id" readonly="readonly" value="<%=session.getAttribute("empno")%>"/></th>
      		</tr>
            <tr class="alt">
                 <td><input  type="hidden"  name="type" value="SIGN"/></td>
            </tr>
            <tr class="alt">
                 <td ><img  src="images/sign.jpg" height="50" width="180"></td>
                 <td >Select Photo <input type="file"  name="SIGN" id="SIGN" /></td> 
            </tr>
            <tr class="alt">
                <td colspan="2" align="center"><input type="submit" value=" Save Images"></td>
            </tr>
	 
	 
</table>
<input type="hidden" value="<%=check%>" name="chek" id="chek">


</form>
</center>
</body >


</html>