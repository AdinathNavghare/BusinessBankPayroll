<%@page import="java.util.ArrayList"%>
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
	else if(flag==10)
	{
		alert("Please Provide only .DBF files ");
	}
	else if(flag==11)
	{
		alert("Saved successfully..");
	}
	else if(flag==12)
	{
		alert("Records Already Present In The Data Base..");
	}
	else if(flag==13)
	{
		alert("Records Does Not Exists For Selected Month IN given File..");
	}
	else if(flag==14)
	{
		alert("Salary Already Finalized For The Month");
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
function AttachFile()
{
	
	var name =document.getElementById("docname").value;
	var desc =document.getElementById("docdesc").value;
	if(name== ""  || desc=="")
		{
		alert("Please Insert Document Information");
		return false;
		}
	
	
	window.showModalDialog("uploadDbf_attach.jsp?name="+name+"&desc="+desc,null,"dialogWidth:600px; dialogHeight:155px; scroll:off; status:off;dialogLeft:400px;dialogTop:300px; ");
	//window.location.href="attachment?list=show";
}
</script>
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
	else if(action.equalsIgnoreCase("finalized"))
	{
		check=14;	//salary already finalized
	}
}
catch(Exception e)
{
	System.out.println("First Time Loading dialog");
}


%>
<%-- <%
	String pageName = "attach_Dbf.jsp";
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
	
%> --%>
</head>
<style >
#headerlist
{
color:#CCCCCC;
}
</style>
<body style="overflow:hidden; " onload="checkVal()" > 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" >
<!-- start content -->
<div id="content" >

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Punch Card Attendance Upload</h1>
	</div>
	<!-- end page-heading -->
	<div align="center">
	
    </div>

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
			<div id="table-content" >
			
						<center><br/>
							<%-- <form ENCTYPE="multipart/form-data" ACTION="Dbf_StoreServlet" METHOD=POST onsubmit="return validation()" >
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
									<center><b><a href="SearchRecords.jsp">Click</a> to Search</b></center>
									<%@include file="viewDbf_Files.jsp" %>
								</form> --%>
				<form action="attach_Dbf.jsp"  onsubmit="return AttachFile()" method="post">
				<table id = "customers" >
					<th colspan="2" align="center"><B>UPLOAD THE FILE</B></th>
					<tr><td>Document Name</td><td><input type="text" name="docname" id="docname"></td></tr>
					<tr><td valign="top">Document Description</td><td><textarea name="docdesc" id="docdesc" cols="30"></textarea></td></tr>
					
					
					
					<tr><td colspan="2" align="center"><input type="submit" value="Attach File" >&nbsp;&nbsp;&nbsp;<input type="reset" value="Cancel"></td></tr>
					
				</table>
				<center><b><a href="SearchRecords.jsp">Click</a> to Search</b></center>
				<%@include file="viewDbf_Files.jsp" %>
				<input type="hidden" value="<%=check%>" name="chek" id="chek">
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
