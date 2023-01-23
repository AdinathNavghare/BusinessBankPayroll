<%@page import="payroll.DAO.*"%>
<%@page import="payroll.Model.*"%>
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
<script type="text/javascript" src="js/datetimepicker.js"></script>




<script type="text/javascript">



function Showhide()
	{
		document.getElementById("update").hidden=true;
	}
	
function validate()
{
	
  if(document.roleform.rolename.value=="")
	{
	  alert("Enter rolename");
	  document.roleform.rolename.focus();
	  return false;
	  
	}
  if(document.roleform.desc.value=="")
	{
	  alert("Enter description");
	  document.roleform.desc.focus();
	  return false;
	  
	}
	
	
}
</script>
<%
	String pageName = "CreateNewRole.jsp";
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


<%
String action = request.getParameter("action")==null?"":request.getParameter("action");
ArrayList<RoleBean> Rolelist=null;
if(action.equalsIgnoreCase("showroleList")||action.equalsIgnoreCase("updateRole"))
{
	Rolelist = (ArrayList<RoleBean>)session.getAttribute("UroleList");

}
%>



</head>
<body style="overflow: hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1> Create New Role </h1>
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
			 
			
			
			<form  name="roleform" action="RoleServlet?action=createNewRole" method="post" onSubmit="return validate()">
				<table id="customers" width="511" align="center">
				
				  <th colspan="4">Create New Role</th>
				 
				
				 
				<tr class="alt"> 
					<td width="63">Role Name</td>
					<td width="158">
					   <input type="text" name="rolename"/>
					
					</td>
					<td width="67">Description</td>
					<td width="203">
					<input type="text" name="desc"/>
					</td>
				
				
				
				
				<tr class="alt" align="center"><td colspan="4"><input name="submit" type="submit" value="Save" class="myButton">&nbsp; &nbsp;
				  <input type="reset" value="Cancel" class="myButton"></td></tr>
				</table>
			
			</form>
			<br>
		
		 
			<%
			
			 if(action.equalsIgnoreCase("showaddesRole"))
			 {
			 %>
			
			<table width="475" id="customers">
				<tr>
				 <th width="90">RollId</th>
				<th width="70">RoleName</th>
				   			
					<th width="65">Description</th>
					<th width="60">Edit</th>
				</tr>
			 
			
			
			<tr> <td width="70">  </td>
			<td width="60">  </td> 
			<td width="65">  </td>
			
			 
			 
				<td><input type="button" value="Edit" onClick="window.location='addUserRole.jsp?action=updateRole&useroleid='"/></td>
		
				<tr class="alt"><th colspan="7"></th></tr>
			
			</table>
			 <%} %>
			<br>
			
			
			<%
			
			 if(action.equalsIgnoreCase("EditRole"))
			 {
			 %>
			<div  id="update" name="update">
			<form action="RoleServlet?action=createNewRole" method="post">
				<table id="customers" width="511" align="center">
				
				  <th colspan="4">Edit Role</th>
				 
				
				 
				<tr class="alt"> 
					<td width="63">RoleName</td>
					<td width="158">
					   <input type="text" name="userid"/>
					
					</td>
					<td width="67">Description</td>
					<td width="203">
					<input type="text" name="desc"/>
					</td>
				
				
				
				
				<tr class="alt" align="center"><td colspan="4"><input name="submit" type="submit" value="Save">				  &nbsp; &nbsp;
				  <input type="reset" value="Cancel"></td></tr>
				</table>
			
			</form>
			<br>
			</div>
			
			<%} %>
		
			
			
			<br>
			
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