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
<link rel="stylesheet" href="css/dropdown.css" type="text/css" media="screen" title="default" />
<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/datetimepicker.js"></script>
<script src="js/jquery/jquery.autocomplete.js"></script>
<script type="text/javascript">


<%

ArrayList<RoleBean> tran_sort = new ArrayList<RoleBean>();
ArrayList<RoleBean> tran_sort1 = new ArrayList<RoleBean>();




String action = request.getParameter("action")==null?"":request.getParameter("action");

ArrayList<RoleBean> Rolelist=null;
if(action.equalsIgnoreCase("showroleList")||action.equalsIgnoreCase("updateRole"))
{
	Rolelist = (ArrayList<RoleBean>)session.getAttribute("UroleList");

} 

%>


function getInfo()
{
	   
	   if( document.getElementById("username").value=="" )
		{
		alert("Please enter proper details...!");
		}
	  
	else
		{
		//var s=document.getElementById("emn").value;
		var uname=document.getElementById("username").value;
		window.location.href='RoleServlet?action=sort_role&username='+uname;
		
		}
	  
}

function validate()
{
	
  if(document.userroleform.userid.value=="Default")
   {
	  alert(" please Select userName");
	  document.userroleform.userid.focus();
	  return false;
	  
   }
  if(document.userroleform.role.value=="Default")
	  {
	    alert("please select role");
	    document.userroleform.role.focus();
	    return false;
	  
	   
	  }
  if(document.userroleform.status.value=="Default")
  {
    alert("please select status");
    document.userroleform.status.focus();
    return false;
  
   
  }
  var userid = document.getElementById("userid").value;
  if (document.getElementById("userid").value == "") {
		alert("Please Insert Employee Name");
		document.getElementById("userid").focus();
		return false;
	}
	var atpos=userid.indexOf(":");
	if (atpos<1)
	  {
	  alert("Please Select Correct Employee Name");
	  return false;
	  }
}

function Showhide()
	{
		document.getElementById("update").hidden=true;
	}
</script>
<style type="text/css">
.row:hover {
	background-color: #CCCCCC;
	cursor: pointer;
}

.style1 {
	color: #FF0000;
}
</style>
<script>
	jQuery(function() {
          $("#userid").autocomplete("SelectUserId.jsp");
	});
</script>





</head>
<body style="overflow:hidden"> 
<%@include file="mainHeader.jsp" %>
<!--  start nav-outer-repeat................................................................................................. START -->
<%@include file="subHeader.jsp" %>
 
<!-- start content-outer ........................................................................................................................START -->
<div id="content-outer" style="overflow-y:scroll; max-height:80%; ">
<!-- start content -->
<div id="content">

	<!--  start page-heading -->
	<div id="page-heading">
		<h1> Add User Role </h1>
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
			 
			
			
			<form  name="userroleform" action="RoleServlet?action=adduserRole" method="post"onsubmit="return validate()">
				<table id="customers" width="511" align="center">
				
				<th colspan="4">Add User Role</th>
				 
				
				 
				<tr class="alt"> 
					<td width="63">User ID</td>
					<td width="158">
					<input type="text" name="userid"  id="userid" onClick="showHide()" title="Enter USER ID"></td>
					<td width="67">Role ID</td>
					<td width="203">
					<select name="role" id="role" style="width:150px;">
                      <option value="Default" selected="selected"  >Select</option>
                      <%
							RoleDAO RDAO = new RoleDAO();
                      		ArrayList<RoleBean> roles = RDAO.getAllRole();
                      		for(RoleBean RB : roles)
                      		{
                      %>
                      <option value="<%=RB.getROLEID()%>"><%=RB.getROLENAME()%></option>
                      <%
                      		}
                      %>
                    
                    </select>			</td>
				
				<tr class="alt"><td> Status</td> <td><select name="status" id="status" style="width:100px">
						<option value="Default" selected="selected">Select</option>
						<option value="Active">Active</option>
						<option value="InActive">InActive</option>
					</select> </td> <td colspan="2"> </td></tr>
				
				
				<tr class="alt" align="center"><td colspan="4"><input name="submit" type="submit" value="Save" class="myButton" >				  &nbsp; &nbsp;
				  <input type="reset" value="Cancel" class="myButton"></td></tr>
				</table>
			
			</form>
			<br>
		
		 
			
			<table id="customers">
			<tr align="center"> <td>			<table width="700" id="customers" style="margin-top:10px">
			<tr>
			<td width="700" >User Name:<input type="text" id="username" name="username" style="width: 90%;" autofocus onClick="this.select();"/></td>
				
			<!--  <td width="385">Role Name:<input type="text" id="emn" name="emn" style="width: 80%;" autofocus onClick="this.select();"/></td>
			 --><td align="center" ><input type="Button" value="Search" onclick="getInfo()" class="myButton" /></td>
				</tr></table></td></tr>
					
			 <tr align="center"> <td>
			<table width="700" id="customers"  style="margin-top:7px">
				        <tr>
				             <th width="70">Sr No</th>
				             <th width="205">User Name</th>
				              <th width="100">User ID</th>
				   			
					           <th width="90">Role Name</th>
					
					           <th width="90">Status</th>
					            <th width="110">Last Modify Date</th>
					            <th width="70">Edit</th>
				         </tr>
			 </table>
			 </td></tr>
			 <tr><td>
			  <div style="height: 145px; overflow-y: scroll; width: 850px;margin-left: 22px;" id="div1"  align="center">
			 <table id="customers" width="700" style="margin-top:7px" align="center">
			
			<%
			
			if(action.equals("showroleList")||action.equalsIgnoreCase("updateRole"))
			{
				String rolename;
				RoleDAO roledao=new RoleDAO();
				int i=0;
				int u=0;
				for(RoleBean Rlbean:Rolelist)
				{
				    i=Rlbean.getROLEID();
					 rolename=roledao.getroleName(i);
					
				
				
			%>
			
			<tr> <td width="70" align="center"><%=Rlbean.getUSER_ROLE_ID() %>  </td>
			
			<% 
			int userid=Rlbean.getUSERID();
			 ArrayList<UsrMast> list1=new ArrayList<UsrMast>();
						 UsrHandler usmhandler1=new UsrHandler();
				 	 list1=usmhandler1.getUserList();
						
							 %>
			<td width="205"><%=Rlbean.getUser_name()%>  </td> 
			<td width="100" align="center"><%=Rlbean.getUSERID() %>  </td> 
			<td width="90" align="center"><%=rolename %> </td>
			<td width="90" align="center"><%=Rlbean.getSTATUS() %></td>
			<td width="110" align="center"><%=Rlbean.getLASTMOD_DATE() %></td>
			 
			 
				<td width="70" align="center"><input type="button" value="Edit" class="myButton" onClick="window.location='addUserRole.jsp?action=updateRole&useroleid=<%=Rlbean.getUSER_ROLE_ID()%>'"/></td>
				</tr>
				 
			 <%
			   } 
			}
			 %>
				 
			
			</table>
			</div>
			 </td></tr>
			<tr class="alt"><th></th></tr>
			</table>
			 
			 
			<br>
			<%
			if(action.equalsIgnoreCase("updateRole"))
			{
				String userid=request.getParameter("useroleid")==null?"":request.getParameter("useroleid");
				for(RoleBean Rlbean:Rolelist)
				{
					if(userid.equalsIgnoreCase(Integer.toString(Rlbean.getUSER_ROLE_ID())))
					{
						
			%>
			
			<div  id="update" name="update">
			<form action="RoleServlet?action=EdituserRole" method="post">
				<table id="customers" align="center">
				
				<th colspan="4">Edit UserRole</th>
				 
				
				 
				<tr class="alt"> 
					<td width="63">UserID</td>
					<td width="158">
					   <input type="text" name="userid" value="<%=Rlbean.getUSERID()%>"/>
					
					</td>
					<td width="67">Role ID</td>
					<td width="210">
					<select name="role" id="role" style="width:100px;">
                       
                      <%
							RoleDAO RDAO1 = new RoleDAO();
                      		ArrayList<RoleBean> roles1 = RDAO.getAllRole();
                      		for(RoleBean RB : roles1)
                      		{
                      			                      			
		                      if(RB.getROLEID() == Rlbean.getROLEID())	
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
		                   %>
                      	<%
                      		}
                      %>
                    
                    </select>			</td>
				
				<tr class="alt"><td> Status</td> <td><select name="status" id="status" style="width:100px;">
						
						<%if(Rlbean.getSTATUS().equalsIgnoreCase("Active")) 
						{
						%>
						<option value="Active" selected="selected">Active</option>
						<option value="InActive">InActive</option>
						<%}
						else
						{%>
						<option value="InActive" selected="selected">InActive</option>
						<option value="Active">Active</option>
						<%} %>
					</select> </td> <td colspan="2"> </td>
					
					<input type="hidden" name="uid" value="<%=Rlbean.getUSER_ROLE_ID()%>"/>
					</tr>
				
				
				<tr class="alt" align="center"><td colspan="4"><input name="submit" type="submit" value="Save" class="myButton">	 &nbsp; &nbsp;
				  <input type="button" value="Cancel" onClick="Showhide()" class="myButton"></td></tr>
				</table>
			
			</form>
			</div>
			
			
			<%}
					
				}
			}
					%>
			
			
			
			
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