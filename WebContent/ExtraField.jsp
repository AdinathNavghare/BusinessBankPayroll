<%@page import="payroll.DAO.ExtraFieldHandler"%>
<%@page import="payroll.Model.ExtraFieldBean"%>
<%@page import="java.util.ArrayList"%>
<%@page errorPage="error.jsp" isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>&copy DTS3 </title>
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
<![endif]-->

<%
	String pageName = "ExtraField.jsp";
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
	
%>

<script language="javascript" type="text/javascript"> 
var xmlhttp;
var url="";

if(window.XMLHttpRequest)
{
	xmlhttp=new XMLHttpRequest;
}
else //if(window.ActivXObject)
{   
	xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
}
function updateData(key)
{
	var fname=document.getElementById("fname"+key).value;
	var desc=document.getElementById("fdisc"+key).value;
	
	url="ExtraFieldServlet?action=updateFieldtab&fname="+fname+"&desc="+desc+"&key="+key;
	xmlhttp.onreadystatechange=callback;
	xmlhttp.open("POST", url, true);
	xmlhttp.send();
}
function callback()
{
	if(xmlhttp.readyState==4)
	{
    	var response=xmlhttp.responseText;
    	if(response == "true")
    	{
			alert("Value inserted");
			
        }
    	else
    	{
    		alert("Error occured while Ending Slab");
        }        	
	}
}

</script>
<%
ExtraFieldHandler efh = new ExtraFieldHandler();
ArrayList<ExtraFieldBean> exflist = efh.getlist();

%>
</head>
<body style="overflow:hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1>Extra Fields</h1>
	</div>
	<!-- end page-heading -->

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
			<div id="table-content">
					<center>
					<form action="ExtraFieldServlet" method="post">
					<table width="547" border="1" align="center" id="customers">
  <tr class="alt">
    <th width="98" align="center">Field Number</th>
    <th width="156">Field Name </th>
    <th colspan="2">Field Description </th>
  </tr>
  <% 
  int i=0;
  for(ExtraFieldBean exfbean :exflist)
	  {
	  i++;
	  %>
  <tr class="alt">
    <td align="center"><%=exfbean.getFieldNo() %></td>
    <td><input type="text" name="fname<%=i %>" id="fname<%=i %>" value="<%=exfbean.getFieldName()%>" ></td>
    <td width="156"><input name="fdisc<%=i %>" type="text" id="fdisc<%=i %>" value="<%=exfbean.getFieldDesc() %>"  ></td>
	 <td width="80"><input  name="update<%=i %>" type="button" id="update<%=i %>" value="Update" onClick=" return updateData('<%=i %>')" class="myButton" ></td>
  </tr>
  <%
  		if(i==10)
  		{
  			%>
  			<tr><th colspan="4">For Numeric Data Type</th></tr>
  			<%
  		}
	  if(i==15)
		{
			%>
			<tr><th colspan="4">For Date</th></tr>
			<%
		}
	  } %>
</table>
</form>
			</center>
			</div>
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