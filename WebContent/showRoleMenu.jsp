<%@page import="java.util.Iterator"%>
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
	
	function loadList () {
		var role = document.getElementById("rolename").value;
		window.location.href="RoleServlet?action=showRoleMenu&rolename="+role;
	}
	
	
	
</script>

<%
String action = request.getParameter("action")==null?"":request.getParameter("action");
int rid =0;
ArrayList<RoleBean> Mlist=null;
if(action.equalsIgnoreCase("menulist"))
{
	Mlist = (ArrayList<RoleBean>)request.getAttribute("mlist");
	rid = Integer.parseInt(request.getAttribute("rid").toString());
}
%>

</head>
<body style="overflow: hidden;"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer " style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1> Show RoleMenu </h1>
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
			 
			
			
		
				<table id="customers" width="447" align="center">
				
				  <th colspan="3">RoleMenu</th>
				 
				
				 
				<tr class="alt"> 
					
					<td width="89">Role Name</td>
					<td width="254" colspan="2">
					<select name="rolename" id="rolename" onchange="loadList()">
                      <option value="Select" selected>Select</option>
                      <%
							RoleDAO RDAO = new RoleDAO();
                      		ArrayList<RoleBean> roles = RDAO.getAllRole();
                      		for(RoleBean RB : roles)
                      		{
                      			if(rid == RB.getROLEID())
                      			{
                      				 %>
                                     <option value="<%=RB.getROLEID()%>" selected="selected"><%=RB.getROLENAME()%></option>
                                     <%		
                      			}
                      			else
                      			{
			                      %>
			                      <option value="<%=RB.getROLEID()%>"><%=RB.getROLENAME()%></option>
			                      <%
                      			}
                      		}
                      %>
                    
                    </select></td>
				</tr>
				
				<%
			if(action.equals("menulist"))
			{
				 %>
				 
			<tr><td colspan="3">	 
			<table width="416" id="customers" >
				<tr>
				 <th></th>
				<th colspan="6">Menu Name</th>
					 
				</tr>
			 
			<%
			
			Iterator<RoleBean> it = Mlist.iterator();
			   while(it.hasNext())
			   {
				   RoleBean bean1 = it.next();
			%>
			
			<tr>
				<td><input type="checkbox" checked="checked" name="menu" value="<%=bean1.getMENUID()%>"/> </td>
				<td> <%=bean1.getMENU_NAME()%> </td> 
			 	<%	if(it.hasNext())
			 		{
			 			bean1 = it.next();
			 	%>
						<td><input type="checkbox" checked="checked" name="menu" value="<%=bean1.getMENUID()%>"/> </td>
						<td> <%=bean1.getMENU_NAME()%> </td> 
				<%
						
						if(it.hasNext())
		 				{
		 					bean1 = it.next();
		 					%>
							<td><input type="checkbox" checked="checked" name="menu" value="<%=bean1.getMENUID()%>"/> </td>
							<td> <%=bean1.getMENU_NAME()%> </td> 
					<%
			 			}
			 		}
				%>
				</tr>
			 <%
			   } 
			
			 %>
				<tr class="alt"><th colspan="7"></th></tr>
			
			</table>
		</td></tr>
		 <%} %>
		 
		 
		 
		 
		 
				
			 
				</table>
		
			
			
			
			
			
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