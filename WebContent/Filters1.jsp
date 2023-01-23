<%@page import="payroll.Core.UtilityDAO"%>
<%@page import="payroll.DAO.EmpOffHandler"%>
<%@page import="payroll.Model.EmpOffBean"%>
<%@page import="payroll.Model.Lookup"%>
<%@page import="payroll.DAO.LookupHandler"%>
<%@page import="payroll.Core.Filters"%>
<%@page import="payroll.Model.FilterValues"%>
<%@page import="java.util.ArrayList"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<script src="js/filter.js"></script>
<title>Employee Selection Filters</title>
<script type="text/javascript">

function closediv() {
	document.getElementById("myModal").style.display = "none";
	$('.nav-outer').fadeTo("slow", 1).css('pointer-events', 'auto');
}
</script>

</head>
<body onload="showSelected()" >
<!-- <div class="modal-content" id="myModal" > -->
<img src='images/Close.png' style='float:right;' title='Remove' onclick="closediv()"><br>
<!-- <span class="close" >&times;</span> -->

<%
String act=request.getParameter("act");

String docname=request.getParameter("docname");
String doctype=request.getParameter("doctype");
String docdesc=request.getParameter("docdesc");

session.setAttribute("doc_name",request.getParameter("docname"));
session.setAttribute("doc_type",request.getParameter("doctype"));
session.setAttribute("doc_desc",request.getParameter("docdesc")); 
session.setAttribute("key",request.getParameter("key"));

%>

<center>

<%

if(act.equalsIgnoreCase("attachfile"))
{
%>
<form ENCTYPE="multipart/form-data"
				ACTION="AttachFile?action=attachFile" METHOD=POST
				onsubmit="return validation()">
				<table id="customers">
					<tr class="alt">
						<th colspan="2" align="center"><B>UPLOAD THE FILE</B></th>
						
						<%-- <input type="hidden" name="docname" id="docname" value="<%=docname %>">
						<input type="hidden" name="doctype" id="doctype" value="<%=doctype %>">
						<input type="hidden" name="docdesc" id="docdesc" value="<%=docdesc %>"> --%>
					</tr>
					<tr class="alt">
						<td colspan="2" align="center"></td>
					</tr>
					<tr class="alt">
						<td><b>Choose the file To Upload:</b></td>
						<td><INPUT NAME="file" TYPE="file" name="filename"
							id="filename"></td>
					</tr>
					<tr class="alt">
						<td colspan="2" align="center"></td>
					</tr>
					<tr class="alt">
						<td colspan="2" align="center"><input type="submit"
							class="myButton" value="Upload File"> <input
							type="button" class="myButton" value="Cancel"
							onClick="closediv()"></td>
					</tr>
				</table>
			</form>
	<%
	}
else if(act.equalsIgnoreCase("editimg"))
{

%>
<form id="form1" enctype="multipart/form-data" action="AddPhotoServlet" method="post" onsubmit="return ImageValidation()">
							 <table width="448" border="1" >
                <tr>
                    <td>Employee Code :</td>
                    <td><%=session.getAttribute("empcode") %><input  type="hidden"  name="id" id="id" readonly="readonly" value="<%= session.getAttribute("empno")==null?"":session.getAttribute("empno")%>"/>
                    
                    </td>
                </tr>
                <tr>
                    
                    <td><input  type="hidden"  name="title"/></td>
                </tr>
                <tr>
                	<td width="216"><img  src="images/img.jpg" height="80" width="100"></td> 
                	<td valign="bottom" width="216"><img alt="" src="images/sign.jpg" height="38" width="192"></td> 
                </tr>
                <tr>
                    <td>Select Photo <input type="file"  name="photo" id="photo" />  </td>
                    <td>Select Signature Image<input type="file"  name="sign" id="sign" /></td>
                </tr>
                <tr><td colspan="2" align="center"><input type="submit"  class="myButton"  value=" Save Images"></td></tr>
            </table>
            
					</form>


<%} %>
	
</center>
</body>
</html>